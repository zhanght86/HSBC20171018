package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LKTransStatusDB;
import com.sinosoft.lis.db.LYBankLogDB;
import com.sinosoft.lis.f1print.PremBankPubFun;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
//import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubLock;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LKTransStatusSchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
//import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.xiangwei.FtpClient;
import com.sinosoft.lis.xiangwei.PrintLog;
import com.sinosoft.lis.xiangwei.SHA1;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: PSBCSendToBankBL
 * </p>
 * <p>
 * Description:银邮保通批量代扣代付发盘程序
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */
public class PSBCSendToBankBL
{
private static Logger logger = Logger.getLogger(PSBCSendToBankBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private String mBankSerialNo = ""; // 发送批次号
	private String mBankCode = ""; // 银行编码
	private String mOriginPath = ""; // 原发盘文件路径
	private String mOriginFile = ""; // 原发盘文件名
	private String mTargetPath = ""; // 发送前备份路径
	private String mTargetFile = ""; // 发送邮储的最终文件名
	private String mTargetMessage = ""; // 发送邮储的最终文件名
	private String mCurrentDate = "";
	private byte[] mBankReturnByte;
	private String mOutComCode = "";  //银行外部机构编码
	private String mTransNo = "";     //交易流水号
	private String mSendDealType;
	
	private GlobalInput mGlobalInput = new GlobalInput();
	private LYBankLogSchema mLYBankLogSchema = new LYBankLogSchema();
	private LDBankSchema mLDBankSchema = new LDBankSchema();
	private LKTransStatusSchema mLKTransStatusSchema = new LKTransStatusSchema();
//	private TransLog mTransLog = new TransLog();


	public PSBCSendToBankBL()
	{
		String pattern = "yyyyMMdd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		FDate tFDate = new FDate();
		Date bDate = tFDate.getDate(PubFun.getCurrentDate());
		mCurrentDate = df.format(bDate);
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 增加并发校验，以发盘批次号做为关键号码
		String key = mBankSerialNo;
		PubLock tPubLock = new PubLock();
		if (!tPubLock.lock(key, "发送发盘数据，批次号为"+mBankSerialNo))
		{
			this.mErrors.copyAllErrors(tPubLock.mErrors);
			return false;
		}
		try
		{
			// 校验数据
			if (!checkData())
				return false;
			logger.debug("---End checkData---");

			// 生成发盘的文件名
			if (!generateTargetFileName())
			{
				return false;
			}
			logger.debug("---End generateTargetFileName---");

			// 进行业务处理
			if (!dealData())
			{
				dealSendState("2", "24"); // 出现异常后维护状态
				return false;
			}
			logger.debug("---End dealData---");
		}
		finally
		{
			if (!tPubLock.unLock(key))
			{
				CError.buildErr(this, "银行" + mBankCode + "解锁失败:" + tPubLock.mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验数据
	 * 
	 * @return 布尔值（true--校验成功, false--校验失败）
	 */
	private boolean checkData()
	{
		try
		{
			// 1.查找发盘记录并校验相关状态
			LYBankLogDB tLYBankLogDB = new LYBankLogDB();
			tLYBankLogDB.setSerialNo(mBankSerialNo);
			if (!tLYBankLogDB.getInfo())
			{
				CError.buildErr(this, "传入发盘批次号码错误！");
				return false;
			}
			mLYBankLogSchema = tLYBankLogDB.getSchema();
			mOriginFile = mLYBankLogSchema.getOutFile();
			
			mSendDealType = mLYBankLogSchema.getLogType();
			logger.debug("tSendDealType: " + mSendDealType);
			if ("".equals(mSendDealType))
			{
				CError.buildErr(this, "获取发盘类型失败，SerialNo：" + mLYBankLogSchema.getSerialNo());
				return false;
			}

			if ("".equals(StrTool.cTrim(mOriginFile))) // 如果发盘文件名为空，提示先生成文件
			{
				CError.buildErr(this, "请先生成文件！");
				return false;
			}
			
			// 如果正在发送
			if ("0".equals(StrTool.cTrim(mLYBankLogSchema.getSendBankFileState()))) 
			{
				CError.buildErr(this, "发盘文件正在发送，请不要重复发送!");
				return false;
			}
			
			// 如果“发送成功（银行也反馈成功）”或者“银行回盘成功”，报错
			if ("1".equals(StrTool.cTrim(mLYBankLogSchema.getSendBankFileState()))
					||"4".equals(StrTool.cTrim(mLYBankLogSchema.getSendBankFileState()))) 
			{
				CError.buildErr(this, "发盘文件已经发送成功，无需重复发送!");
				return false;
			}
			
			String tOutComCodeSQL = "select CodeAlias from LDCode1 where CodeType='YBTBatBank' and Code='1200' and Code1='"
					+ "?Code1?" + "' and OtherSign='1'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tOutComCodeSQL);
			sqlbv1.put("Code1", mLYBankLogSchema.getBankCode());
			ExeSQL tExeSQL = new ExeSQL();
			mOutComCode = StrTool.cTrim(tExeSQL.getOneValue(sqlbv1));
			if ("".equals(mOutComCode))
			{
				CError.buildErr(this, "获取外部机构失败，管理机构编码：" + mLYBankLogSchema.getComCode());
				return false;
			}

			// 2.查找银行记录
			LDBankDB tLDBankDB = new LDBankDB();
			tLDBankDB.setBankCode(mLYBankLogSchema.getBankCode());
			if (!tLDBankDB.getInfo())
			{
				CError.buildErr(this, "银行信息查找失败！BankCode: " + mLYBankLogSchema.getBankCode());
				return false;
			}
			mLDBankSchema.setSchema(tLDBankDB.getSchema());

			// 3.校验原发盘文件
			String tOriginPathSQL = "select SysVarValue from LDSysVar where SysVar ='SendToBankFilePath'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tOutComCodeSQL);
			tExeSQL = new ExeSQL();
			mOriginPath = tExeSQL.getOneValue(sqlbv2);
			logger.debug("***OriginBankFile:" + mOriginPath + mOriginFile);

			File tFile = new File(mOriginPath + mOriginFile);
			if (!tFile.exists())
			{
				CError.buildErr(this, "发盘文件查找失败！");
				return false;
			}

			// 4.获取批量数据的路径
			String tTargetPathSQL = "select SysVarValue from LDSysVar where SysVar ='YBTSendToBankFPath'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tTargetPathSQL);
			tExeSQL = new ExeSQL();
			mTargetPath = tExeSQL.getOneValue(sqlbv3);
			String inputDate = PubFun.getCurrentDate();
			mTargetPath = mTargetPath + inputDate.substring(0, 4) + "/" + inputDate.substring(5, 7) + "/"
					+ inputDate.substring(8, 10) + "/";
			
			logger.debug("***YBTSendToBankFilePath:" + mTargetPath);

			File tTargetPathFile = new File(mTargetPath);
			if (!tTargetPathFile.exists())
			{
				logger.debug("发盘文件路径不存在，创建路径："+mTargetPath);
				tTargetPathFile.mkdirs();
			}
			
			
//			mTargetPath = PostpositionConfig.getString("PSBCDataGatherPath");

			// 5.校验发盘记录数
			String tSendCountSQL = "select count(1) from LYSendToBank where SerialNo='"
					+ "?SerialNo?" + "'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSendCountSQL);
			sqlbv4.put("SerialNo", mLYBankLogSchema.getSerialNo());
			String tSendCount = StrTool.cTrim(tExeSQL.getOneValue(sqlbv4));
			logger.debug("tSendCountSQL: " + tSendCountSQL);
			if ("0".equals(tSendCountSQL))
			{
				CError.buildErr(this, "该批次无发盘明细数据，SerialNo：" + mLYBankLogSchema.getSerialNo());
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			CError.buildErr(this, "发盘数据校验出现异常" + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 生成发盘文件名
	 * 
	 * @return
	 */
	private boolean generateTargetFileName()
	{
		try
		{
			if (!"".equals(StrTool.cTrim(mLYBankLogSchema.getSendBankFileName())))
			{
				mTargetFile = mLYBankLogSchema.getSendBankFileName(); // 数据文件名
				mTargetMessage = mTargetFile.substring(0,mTargetFile.indexOf("."));  //截取“.i”之前部分
			}
			else
			{
				// 生成银行当日序号的条件
				VData tDaySerialNoVData = new VData();
				tDaySerialNoVData.add("1200" + mCurrentDate); // 银行编码+日期， 如120020100110
				String tDaySerialNo = PubFun1.CreateMaxNo("YBTBATDAYNO", "SN", tDaySerialNoVData); // 按照银行生成当日流水号

				String tBussType = "PKK"; // 批量扣款
				if ("F".equals(mSendDealType))
				{
					tBussType = "PCK"; // 批量扣款
				}
				logger.debug("tBussType: " + tBussType);
				mTargetMessage = "0013" + tBussType + mCurrentDate + mOutComCode + tDaySerialNo; // 将要发送报文的名称
				mTargetFile = mTargetMessage + ".i"; // 数据文件名
			}
			logger.debug("TargetMessage: "+mTargetMessage);
			logger.debug("TargetFile: "+mTargetFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			CError.buildErr(this, "生成发盘文件名失败" + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 生成发盘文件
	 * 
	 * @return
	 */
	private boolean generateTargetFile()
	{
		try
		{
			// 1.生成新发盘文件
			copyFile(mOriginPath + mOriginFile, mTargetPath + mTargetFile);
			logger.debug("生成发盘文件完毕！");
			// 2.生成报文

			FileInputStream fis = null;
			String digest = "";
			try
			{
				fis = new FileInputStream(mTargetPath + mTargetFile);
				byte[] cs = new byte[fis.available()];
				fis.read(cs);
				digest = new SHA1().getDigestOfString(cs); // 按照SHA-1算法计算出的文件摘要码
				logger.debug("PSBCSendToBankBL: " + digest); // 2E53
																	// A81175E47E8C38E1957EEC87FDCE47C5CFC5
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
				return false;
			}
			finally
			{
				try
				{
					fis.close();
				}
				catch (RuntimeException e)
				{
				}
			}
			// 交易类型码816040 保险公司代码0013 保险公司类型40 保险公司交易日期 保险公司流水号 批量文件名 总笔数 总交易金额
			// 文件校验码
			FileWriter fw1 = null;
			try
			{
				fw1 = new FileWriter(mTargetPath + mTargetMessage);
				BufferedWriter bw = new BufferedWriter(fw1);
				bw.write("816040|0013|40|" + mCurrentDate + "|" + mLYBankLogSchema.getSerialNo() + "|" + mTargetFile
						+ "|" + mLYBankLogSchema.getTotalNum() + "|" + mLYBankLogSchema.getTotalMoney() + "|" + digest
						+ "|");
				bw.write("\r\n");
				bw.flush();// 将数据更新至文件
				fw1.close();// 关闭文件流
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.debug("生成发盘报文失败！");
				CError.buildErr(this, "生成发盘报文失败" + e.toString());
				return false;
			}
			logger.debug("生成报文完毕！");

		}
		catch (Exception e)
		{
			e.printStackTrace();
			CError.buildErr(this, "生成发盘文件失败" + e.toString());
			return false;
		}
		return true;
	}

	private boolean sendTargetFile()
	{
		try
		{
			try
			{
				String[] args =
				{ mTargetPath, mTargetFile };
				logger.debug("tSourcePath: " + args[0]);
				logger.debug("tFileName: " + args[1]);
				FtpClient.main(args);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				CError.buildErr(this, "发盘发送失败");
				logger.debug("send file failed");
				return false;
			}
			logger.debug("send file successful");

		}
		catch (Exception e)
		{
			// TODO: handle exception
		}

		return true;
	}

	/** 给银行发送通知报文并返回银行反馈
	 *
	 * @param tPath 报文路径
	 * @param filename  报文名称
	 * @return 银行反馈信息
	 * @throws Exception
	 */
	private byte[] communicateToBank(String tPath, String filename) throws Exception
	{
		logger.debug("Start Send Message To Bank..."+PubFun.getCurrentDate()+ PubFun.getCurrentTime());
		Socket socket = null;
		byte[] tBankReturnNoSTDbytes = new byte[1];
		try
		{
//			String tPSBCPKKSerMesIP = PostpositionConfig.getString("PSBCPKKSerMesIP"); 
//			int tPSBCPKKSerMesPort = Integer.parseInt(PostpositionConfig.getString("PSBCPKKSerMesPort"));
			ExeSQL tExeSQL = new ExeSQL();
			String tPSBCPKKSerMesIPSQL = "select SysVarValue from LDSysVar where SysVar ='PSBCBankSerMesIP'";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(tPSBCPKKSerMesIPSQL);
			String tPSBCPKKSerMesIP = StrTool.cTrim(tExeSQL.getOneValue(sqlbv6));
			
			tExeSQL = new ExeSQL();
			String tPSBCPKKSerMesPortSQL = "select SysVarValue from LDSysVar where SysVar ='PSBCBankSerMesPort'";
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(tPSBCPKKSerMesPortSQL);
			int tPSBCPKKSerMesPort = Integer.parseInt(StrTool.cTrim(tExeSQL.getOneValue(sqlbv7)));
			logger.debug("邮储银行报文接收前置机IP：" + tPSBCPKKSerMesIPSQL);
			logger.debug("邮储银行报文接收前置机Port：" + tPSBCPKKSerMesPort);
			
			socket = new Socket(tPSBCPKKSerMesIP, tPSBCPKKSerMesPort);
			
			socket.setKeepAlive(true);
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(60000);
			InputStream fis = new FileInputStream(new File(tPath + filename));
			OutputStream outputstream = socket.getOutputStream();
//			InputStream tInputStream = socket.getInputStream();

			// 把文件流变为字节数组
			byte[] bytes = new byte[1];
			ByteArrayOutputStream vInNoSTDByteArrayOutputStream = new ByteArrayOutputStream();
			while (fis.read(bytes) != -1)
			{
				vInNoSTDByteArrayOutputStream.write(bytes);
			}
			vInNoSTDByteArrayOutputStream.flush();
			vInNoSTDByteArrayOutputStream.close();
			byte[] tInNoSTDbytes = vInNoSTDByteArrayOutputStream.toByteArray();

			// 获取文件大小
			int fileLength;
			InputStream d = new ByteArrayInputStream(tInNoSTDbytes);
			for (fileLength = 0; d.read() != -1; fileLength++)
				;
			logger.debug("报文包体长度：" + fileLength);
			// int tPackHeadLength = fileLength+40;
			int tPackHeadLength = fileLength;
			String tPackHeadLengthStr = (new StringBuffer(String.valueOf(tPackHeadLength))).toString();
			if (tPackHeadLengthStr.length() < 6)
			{
				StringBuffer tZeroString = new StringBuffer();
				for (int i = 0; i < 6 - tPackHeadLengthStr.length(); i++)
				{
					tZeroString.append("0");
				}
				tZeroString.append(tPackHeadLengthStr);
				tPackHeadLengthStr = tZeroString.toString();
			}

			// 增加包头信息
			byte array[] = new byte[16];
			int m = 0;
			byte arrayPackFlag[] = "INSU".getBytes();
			logger.debug("arrayPackFlag.length=" + arrayPackFlag.length);
			for (int i = 0; i < arrayPackFlag.length; i++)
			{
				array[m] = arrayPackFlag[i];
				m++;
			}

			byte arrayPackFunctionFlag[] = "816040".getBytes();
			logger.debug("arrayPackFunctionFlag.length=" + arrayPackFunctionFlag.length);
			for (int i = 0; i < arrayPackFunctionFlag.length; i++)
			{
				array[m] = arrayPackFunctionFlag[i];
				m++;
			}
			byte arrayPackHeadLength[] = tPackHeadLengthStr.getBytes();

			for (int i = 0; i < 6; i++)
			{
				if (i < arrayPackHeadLength.length)
				{
					array[m] = arrayPackHeadLength[i];
				}
				m++;
			}

			outputstream.write(array);
			// 增加包体信息
			InputStream InNoSTD = new ByteArrayInputStream(tInNoSTDbytes);
			InputStream InNoSTD2 = new ByteArrayInputStream(tInNoSTDbytes);
			for (; InNoSTD.read(bytes) != -1; outputstream.write(bytes))
				;

			// test
			InputStream p = new ByteArrayInputStream(array);
			FileOutputStream fos1 = new FileOutputStream(new File(tPath + filename + ".xml"));
			// bytes = new byte[1];
			while (p.read(bytes) != -1)
			{
				fos1.write(bytes);
			}
			for (; InNoSTD2.read(bytes) != -1; fos1.write(bytes))
				;
			fos1.flush();
			fos1.close();
			outputstream.flush();
			
//			socket.shutdownOutput();


			/** 输出返回的结果 */
			logger.debug("start response !");
			InputStream tInputStream = null;
			if(socket.isConnected())
			{
				logger.debug("the socket is connecting ");
				tInputStream = socket.getInputStream();
				logger.debug("socket.getInputStream over~ ");
			}

//          方案1. 读取银行返回报文 (原始版本)  这种类型报错 java.net.SocketException: Connection reset
//	        bytes = new byte[1];
//    		ByteArrayOutputStream vBankReturnNoSTDByteArrayOutputStream = new ByteArrayOutputStream();
//    		int i= 1;
//    		while (tInputStream.available()>0 && tInputStream.read(bytes) != -1)
//			{
//    			if(socket.isConnected())
//    			{
//    				logger.debug(i+ "the socket is connecting ");
//    			}
//    			logger.debug( i++ +"  "+bytes);
//    			vBankReturnNoSTDByteArrayOutputStream.write(bytes);
//			}
    		
//    		vBankReturnNoSTDByteArrayOutputStream.flush();
//    		vBankReturnNoSTDByteArrayOutputStream.close();
//    		tBankReturnNoSTDbytes = vBankReturnNoSTDByteArrayOutputStream.toByteArray();
			
//			方案2. 读取银行返回报文 (邮储推荐)，主要思路先读取返回报文长度，再根据报文长度读取报文内容
//    		ByteArrayOutputStream vBankReturnNoSTDByteArrayOutputStream = new ByteArrayOutputStream();
//			DataInputStream dips = null;
//			byte[] byTotal = null;
//			int l = 100;
//			try
//			{
//				if (socket.isConnected())
//				{
//					PrintLog.printLog("start to recive Data from Host...");
//					dips = new DataInputStream(socket.getInputStream());
//					int i = 0;
//
//					byte by;
//					byTotal = new byte[(int) l];
//					while ((by = dips.readByte()) != -1)
//					{
//						byTotal[i] = by;
//						i++;
//						if (i == l)
//						{
//							break;
//						}
//					}
//					vBankReturnNoSTDByteArrayOutputStream.write(byTotal);
//					
//				}
//				else
//				{
//					PrintLog.printLog("connecting is failed!");
//					return null;
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//    		
//    		
//    		vBankReturnNoSTDByteArrayOutputStream.flush();
//    		vBankReturnNoSTDByteArrayOutputStream.close();
//    		tBankReturnNoSTDbytes = vBankReturnNoSTDByteArrayOutputStream.toByteArray();

			// 方案3. 读取银行返回报文 (同前置机开始读取对方请求 报文)

			tBankReturnNoSTDbytes = readSocketData(tInputStream);
			if (tBankReturnNoSTDbytes == null)
			{
				logger.debug("$$$$$$$接收邮储反馈报文为空$$$$$$$$$");
				return null;
			}

			String tTempStr = new String(tBankReturnNoSTDbytes);
			logger.debug("****银行返回报文时间: "+PubFun.getCurrentDate() + " "+PubFun.getCurrentTime());
			logger.debug("****银行返回报文内容: "+tTempStr);
			
    		//保存银行反馈报文
			try
			{
				String str = tPath  + filename + "re"+ getCurrentDate2() + getCurrentTime2() + ".xml";
				FileOutputStream fosresult = new FileOutputStream(str);
				fosresult.write(tBankReturnNoSTDbytes);
				fosresult.flush();
				fosresult.close();

			}
			catch (java.io.IOException e)
			{
				e.printStackTrace();
			}
			socket.close();
			logger.debug("End Send Message To Bank..."+PubFun.getCurrentDate()+ PubFun.getCurrentTime());
			return tBankReturnNoSTDbytes;

		}
		catch(SocketTimeoutException e)  //Socket超时时返回0
		{
			logger.debug("***Bank Send Message Throws SocketTimeoutException"+PubFun.getCurrentDate()+ PubFun.getCurrentTime());
			tBankReturnNoSTDbytes = new byte[1];
			tBankReturnNoSTDbytes[0]= 0 ;
			return tBankReturnNoSTDbytes;
		}
		catch(SocketException e)  //Socket非超时异常
		{
			e.printStackTrace();
			logger.debug("***Bank Send Message Throws SocketException"+PubFun.getCurrentDate()+ PubFun.getCurrentTime());
//			tBankReturnNoSTDbytes = new byte[1];
//			tBankReturnNoSTDbytes[0]= 0 ;
//			return tBankReturnNoSTDbytes;
			logger.debug("e.toString :"+e.toString());
			if(e.toString().indexOf("Connection reset")!=-1)
			{
				logger.debug("e.toString_Connection reset: "+e.toString().indexOf("Connection reset"));
				if(tBankReturnNoSTDbytes!= null && tBankReturnNoSTDbytes.length>0)
				{
					logger.debug("tBankReturnNoSTDbytes.length: "+tBankReturnNoSTDbytes.length);
					return tBankReturnNoSTDbytes;
				}
				else
				{
					return null;
				}
			}
			else
			{
				return null;
			}
		}

		catch (Exception e)
		{
			e.printStackTrace();

			logger.debug("***Bank Send Message Throws Exception"+PubFun.getCurrentDate()+ PubFun.getCurrentTime());
			CError.buildErr(this, "发送报文发生异常" + e.toString());
			return null;
		}
		finally  //如果连接未关闭，先关闭连接，再返回
		{
			try
			{
				if(!socket.isClosed())
				{
					socket.close();
				}
			}
			catch (Exception e2)
			{
				e2.printStackTrace();
				logger.debug("***发送报文时关闭Socket发生异常"+PubFun.getCurrentDate()+ PubFun.getCurrentTime());
			}
		}
	}

	/**
	 * 得到当前系统日期 author: YT
	 * 
	 * @return 当前日期的格式字符串,日期格式为"yyyyMMdd"
	 */
	public String getCurrentDate2() {
		String pattern = "yyyyMMdd";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}

	/**
	 * 得到当前系统时间 author: YT
	 * 
	 * @return 当前时间的格式字符串，时间格式为"HHmmss"
	 */
	public String getCurrentTime2() {
		String pattern = "HHmmss";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Date today = new Date();
		String tString = df.format(today);
		return tString;
	}
	
    // 读入socket数据流
	private byte[] readSocketData(InputStream p_inputstream)
	{
		// 拷贝参数
		InputStream v_inputstream = p_inputstream;
		// 返回参数
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try
		{
			// 读取整个包头的1－16个字节
			String tPackHead = "";
			int m = 0;
			for (int i = 0; i < 16; i++)
			{
				m = v_inputstream.read();
				if (m == -1)
				{
					logger.debug("输入流字节数数小于16");
					throw new Exception("输入流字节数数小于16");
				}
				tPackHead += (char) m;
			}
			logger.debug("包头:" + tPackHead);
			String tPackFlag = tPackHead.substring(0, 4);
			String tPackFunctionFlag = tPackHead.substring(4, 8);
			logger.debug("tPackFunctionFlag: " + tPackFunctionFlag);
			String tPackHeadLength = "0";

			if (!"INSU".equals(tPackFlag) && !"POST".equals(tPackFlag)) // 批量代付代扣为POST
																		// ，
																		// 其他交易为INSU
			{
				logger.debug("包头标志有误" + tPackFlag);
				throw new Exception("包头标志有误！！");
			}

			if (tPackFunctionFlag == null
					|| !(tPackFunctionFlag.equals("1021") || tPackFunctionFlag.equals("1022")
							|| tPackFunctionFlag.equals("1013") || tPackFunctionFlag.equals("1002")
							|| tPackFunctionFlag.equals("1003") || tPackFunctionFlag.equals("1004")
							|| tPackFunctionFlag.equals("1005") || tPackFunctionFlag.equals("8160")) // 批量代收付返回报文
			)
			{
				logger.debug("包头信息中的操作码有误");
				throw new Exception("包头信息中的操作码有误！！");
			}

			if (tPackFunctionFlag.equals("1021") || tPackFunctionFlag.equals("1022")
					|| tPackFunctionFlag.equals("1013") || tPackFunctionFlag.equals("1002")
					|| tPackFunctionFlag.equals("1003") || tPackFunctionFlag.equals("1004")
					|| tPackFunctionFlag.equals("1005"))
			{
				tPackHeadLength = tPackHead.substring(8, 16);
			}
			else
			{
				tPackFunctionFlag = tPackHead.substring(4, 10);
				tPackHeadLength = tPackHead.substring(10, 16);
			}
			logger.debug("tNewPackFunctionFlag: " + tPackFunctionFlag);
			int tPackHeadLengthINT = Integer.parseInt(tPackHeadLength.trim());
			int inputLen = tPackHeadLengthINT;
			logger.debug("从客户端传来的报文长度为：" + tPackHeadLengthINT + 16);
			logger.debug("从客户端传来的纯报文长度为：" + inputLen);
			// baseInfo.setService_id(tPackFunctionFlag);

			// 读取报文主体部分
			int x = 0;
			for (int i = 0; i < inputLen; i++)
			{
				x = v_inputstream.read();
				if (x == -1)
				{
					throw new Exception("读入数据流错误，长度应为" + inputLen + "，而实际长度为" + i);
				}
				baos.write((char) x);
			}

			baos.flush();
			baos.close();
			return baos.toByteArray();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			logger.debug("$$$$readSocketData$$$接收邮储返回报文发生异常" + ex.toString());
			CError.buildErr(this, "接收邮储返回报文发生异常" + ex.toString());
			return null;
		}
	}
	
	public void copyFile(String fromFile, String toFile) throws FileNotFoundException, IOException, Exception
	{
		FileInputStream in = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);

		byte b[] = new byte[1024];
		int len;
		while ((len = in.read(b)) != -1)
		{
			out.write(b, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}
 
	private boolean dealReturnResult()
	{
		String tSendBankFileState = "";  //发盘文件处理状态:1  发送成功（银行也反馈成功）,2  发送失败,3  银行接受成功但反馈异常
		String tSendBankFileSubState = ""; //发盘子状态（主要用于发送失败的明细原因） 发送失败：21 非Socket超时的异常，22 Socket超时的异常，23 银行返回异常,24 其他异常 
		String tBankReturnInfo = "";   //银行返回响应码
		String tBankReturnContent = "";   //银行返回响应内容
		if(mBankReturnByte == null)  //非Socket超时的异常
		{
			tSendBankFileState = "2";
			tSendBankFileSubState = "21";
		}
		
		if (mBankReturnByte != null)
		{
			// Socket超时的异常
			if (mBankReturnByte.length == 1 && mBankReturnByte[0] == 0)
			{
				tSendBankFileState = "2";
				tSendBankFileSubState = "22";
			}
			else //正常返回
			{
				String tBankReturnResult[] = getBankReturnInfo();
				
				//如果返回码为空或者返回异常
				if(tBankReturnResult == null || (tBankReturnResult != null && !"1".equals(tBankReturnResult[2]) ))
				{
					tSendBankFileState = "3";
				}
				//返回处理成功
				if(tBankReturnResult != null && "1".equals(tBankReturnResult[2]))
				{
					tSendBankFileState = "1";
				}
				
				//保存银行的返回码
				if(tBankReturnResult != null )
				{
				  tBankReturnInfo = tBankReturnResult[0];
				  tBankReturnContent = tBankReturnResult[1];
				}
			}
		}
		else
		{
			tSendBankFileState = "2";
			tSendBankFileSubState = "23";
		}

		if(!dealSendState(tSendBankFileState,tSendBankFileSubState,tBankReturnInfo,tBankReturnContent))
		{
			return false;
		}
		return true;
	}
	
	private String[] getBankReturnInfo()
	{
		String[] tBankReturnResult = new String[3];
		String tBankSuccFlag = ""; // 银行返回成功标志
		getBankSuccFlag(tBankReturnResult);
		if (!"-1".equals(tBankReturnResult[0]))  //正常获取返回信息
		{
			if ("S".equals(mSendDealType))
			{
				tBankSuccFlag = mLDBankSchema.getAgentGetSuccFlag();
			}
			else
			{
				tBankSuccFlag = mLDBankSchema.getAgentPaySuccFlag();
			}

			PremBankPubFun tPremBankPubFun = new PremBankPubFun();
			if (tPremBankPubFun.verifyBankSuccFlag(tBankSuccFlag, tBankReturnResult[0])) // 处理成功
			{
				tBankReturnResult[2] = "1";
			}
			else
			{
				tBankReturnResult[2] = "0";
			}
		}
		else  //获取
		{
			tBankReturnResult[2] = "0";
		}

		return tBankReturnResult;
	}
	
	private boolean getBankSuccFlag(String []tBankReturnResult)
	{
		try
		{
			String tReturnStr = new String(mBankReturnByte);
			tBankReturnResult[0] = StrTool.cTrim(getSubStr(tReturnStr, "|", 10));  //响应码
			tBankReturnResult[1] = StrTool.cTrim(getSubStr(tReturnStr, "|", 11));  //响应说明
		}
		catch (Exception e)
		{
			e.printStackTrace();
			tBankReturnResult[0] = "-1";
			tBankReturnResult[1] = "";
			return false;
		}
		
		return true;
	}
	
	private boolean dealSendState(String tSendBankFileState)
	{
		return dealSendState(tSendBankFileState,"");
	}
	
	private boolean dealSendState(String tSendBankFileState,String tSendBankFileSubState)
	{
		return dealSendState(tSendBankFileState,tSendBankFileSubState,"","");
	}
	/**
	 * 	 * 处理相关状态
	 * @param tSendBankFileState  主状态：0 发送在途;1  发送成功（银行也反馈成功）;
	 *        2  发送失败; 3  银行接受成功但反馈异常;4  银行回盘成功;5  银行回盘失败
	 * @param tSendBankFileSubState 发盘子状态（主要用于发送失败的明细原因） 
	 *         发送失败：21 非Socket超时的异常，22 Socket超时的异常，23 银行返回异常,24 其他异常
	 * @param tBankReturnInfo 银行返回码      
	 * @param tBankReturnConttent 银行反馈内容
	 * @return
	 */
	private boolean dealSendState(String tSendBankFileState,String tSendBankFileSubState,String tBankReturnInfo,String tBankReturnConttent)
	{
		if("0".equals(tSendBankFileState))  //初始化
		{
			//1.mLKTransStatusSchema
			mTransNo  = PubFun1.CreateMaxNo("TransrNo", 10);
			mLKTransStatusSchema.setTransCode(mTransNo);
			mLKTransStatusSchema.setBankCode("1200");
			mLKTransStatusSchema.setBankBranch(mOutComCode);
			mLKTransStatusSchema.setBankNode("999999999");
			mLKTransStatusSchema.setBankOperator(mGlobalInput.Operator);
			mLKTransStatusSchema.setTransNo(mTransNo);
			mLKTransStatusSchema.setFuncFlag("18");   //批量代扣/代付取汇总
			mLKTransStatusSchema.setTransDate(PubFun.getCurrentDate());
			mLKTransStatusSchema.setTransTime(PubFun.getCurrentTime());
			mLKTransStatusSchema.setManageCom(mLYBankLogSchema.getComCode());
			mLKTransStatusSchema.setBankAcc(mTargetFile);   //借用此字段存储发盘文件名
			mLKTransStatusSchema.setTempFeeNo(mBankSerialNo); //借用此字段存储发盘批次号
			mLKTransStatusSchema.setTransStatus("1");
			mLKTransStatusSchema.setRCode("0");
			mLKTransStatusSchema.setOutServiceCode(mLYBankLogSchema.getBankCode());  //记录银行编码
			
			//2.mLYBankLogSchema
			mLYBankLogSchema.setSendBankFileName(mTargetFile);
		}
		else if("1".equals(tSendBankFileState)) //1  发送成功（银行也反馈成功）
		{
			mLKTransStatusSchema.setTransStatus("2");
			mLKTransStatusSchema.setRCode("1");
			mLKTransStatusSchema.setTemp(tBankReturnInfo);
			mLKTransStatusSchema.setDescr("发送成功");

		}
		else if("2".equals(tSendBankFileState)) //发送失败异常情况
		{
			mLKTransStatusSchema.setTransStatus("2");
			mLKTransStatusSchema.setRCode("0");
			mLKTransStatusSchema.setTemp(tSendBankFileSubState);
			mLKTransStatusSchema.setDescr(mErrors.getFirstError());
		}
		else if("3".equals(tSendBankFileState)) // 银行接受成功但反馈异常
		{
			mLKTransStatusSchema.setTransStatus("2");
			mLKTransStatusSchema.setRCode("0");
			
			//保存返回码以及相应说明
			mLKTransStatusSchema.setTemp(tBankReturnInfo);
//			PremBankPubFun tPremBankPubFun = new PremBankPubFun();
//			mLKTransStatusSchema.setDescr(tPremBankPubFun.getErrInfo("bankerror", mLDBankSchema.getBankCode(), tBankReturnInfo));
			mLKTransStatusSchema.setDescr(tBankReturnConttent);
		}
		
		DealLog(mLKTransStatusSchema);
		
		mLYBankLogSchema.setSendBankFileState(tSendBankFileState);
		mLYBankLogSchema.setModifyDate(PubFun.getCurrentDate());
		mLYBankLogSchema.setModifyTime(PubFun.getCurrentTime());
		
        MMap mMap = new MMap();
        mMap.put(mLYBankLogSchema, "UPDATE");

        VData tInputData = new VData();
        tInputData.add(mMap);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tInputData, ""))
        {
        	logger.debug("LYBankLog数据提交失败");
        	return false;
        }
		
		return true;
	}
    //状态日志
    public boolean DealLog(LKTransStatusSchema cLKTransStatusSchema)
    {
    	String theCurrentDate = PubFun.getCurrentDate();
		String theCurrentTime = PubFun.getCurrentTime();
		LKTransStatusSchema mLKTransStatusSchema = cLKTransStatusSchema;
		LKTransStatusDB tLKTransStatusDB = new LKTransStatusDB();
        tLKTransStatusDB.setSchema(mLKTransStatusSchema);
        VData tInputData = new VData();
         if(mLKTransStatusSchema.getTransStatus().equals("1"))
         {
             if (!tLKTransStatusDB.getInfo())
             {

                 mLKTransStatusSchema.setMakeDate(theCurrentDate);
                 mLKTransStatusSchema.setMakeTime(theCurrentTime);
                 mLKTransStatusSchema.setModifyDate(theCurrentDate);
                 mLKTransStatusSchema.setModifyTime(theCurrentTime);
                 tLKTransStatusDB.setSchema(mLKTransStatusSchema);
                 if(!tLKTransStatusDB.insert())
                 {
                     return false;
                 }

     //            map.put(mLKTransStatusSchema, "INSERT");
             }

             else
             {
                 return false;
             }
         }
         else if (mLKTransStatusSchema.getTransStatus().equals("2"))
         {
             mLKTransStatusSchema.setMakeDate(theCurrentDate);
             mLKTransStatusSchema.setMakeTime(theCurrentTime);
             mLKTransStatusSchema.setModifyDate(theCurrentDate);
             mLKTransStatusSchema.setModifyTime(theCurrentTime);
             tLKTransStatusDB.setSchema(mLKTransStatusSchema);
             if(!tLKTransStatusDB.update())
             {
                  return false;
             }

         }
         //数据提交
        //         map.put(mLKTransStatusSchema, "DELETE&INSERT");


//        tInputData.add(map);
//        PubSubmit tPubSubmit = new PubSubmit();
//        if (!tPubSubmit.submitData(tInputData, ""))
//        {
//            return false;
//        }

        return true;
    }
	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData()
	{
		try
		{
			TransferData tTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);

			mBankSerialNo = StrTool.cTrim((String) tTransferData.getValueByName("SerialNo"));
			logger.debug("***BankSerialNo : " + mBankSerialNo);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
			
			if ("".equals(mBankSerialNo))
			{
				CError.buildErr(this, "传入发盘批次号码为空！");
				return false;
			}
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PSBCSendToBankBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		try
		{
			// 1.初始化发盘状态
			logger.debug("Start dealSendState ...");
			if (!dealSendState("0"))
			{
				return false;
			}
			
			// 2.生成发盘文件
			logger.debug("Start generateTargetFile ...");
			if (!generateTargetFile())
			{
				return false;
			}
			// 3.发送文件
			logger.debug("Start sendTargetFile ...");
			if (!sendTargetFile())
			{
				return false;
			}
			
			int tWaitSecond = 30;  //发送文件后等待描述
			try
			{
				logger.debug("发送文件后等待"+tWaitSecond+"秒.....");
				Thread.currentThread().sleep(tWaitSecond * 1000);
			}
			catch (Exception e)
			{
				// @@错误处理
				e.printStackTrace();
			}
			
			// 4.处理银行通信
			logger.debug("Start communicateToBank ...");
			mBankReturnByte = communicateToBank(mTargetPath, mTargetMessage);

			// 5.处理反馈结果
			logger.debug("Start dealReturnResult ...");
			if (!dealReturnResult())
			{
				return false;
			}
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PSBCSendToBankBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			
			return false;
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult()
	{
		return mResult;
	}
	public String getSubStr(String tOriginStr,String tSplit,int index)
	{
		String tResultStr = "";
		String []tResultStrArray = PubFun.split(tOriginStr, tSplit);
		if(tResultStrArray.length >= index)
		{
		  tResultStr = tResultStrArray[index-1];
		}
		return tResultStr;
	}

	public static void main(String[] args)
	{
//		GlobalInput tGlobalInput = new GlobalInput();
//		tGlobalInput.ComCode = "8611";
//		tGlobalInput.Operator = "TEST";
//
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("startDate", "2005-06-20");
//		tTransferData.setNameAndValue("endDate", "2005-06-30");
//		tTransferData.setNameAndValue("bankCode", "0101");
//		tTransferData.setNameAndValue("typeFlag", "ALLXQ");
//
//		VData tVData = new VData();
//		tVData.add(tGlobalInput);
//		tVData.add(tTransferData);

		String tStr = "INSU816040000134816040|0013|40|20100125|100023789|0013SJB201001241300002.i|1|0.000000|AD8D59E046B8B8FB955545585A16BB9288DCFED2|00|交易成功:A587025758|";
//		logger.debug(getSendToBankBL1.getSubStr(tStr, "|", 15));
//		String [] tNoStdSTRDate =  tStr.split("\\|");
//		
//		logger.debug(tNoStdSTRDate.length);
		
		String tStr1 = com.sinosoft.lis.pubfun.XmlFun.split(tStr, "|","2");
		logger.debug("123123:   "+tStr1);
		
	}
}
