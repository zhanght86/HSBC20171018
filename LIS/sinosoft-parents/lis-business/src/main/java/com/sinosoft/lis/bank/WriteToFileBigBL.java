package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 银行数据转换到文件模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author yangyf
 * @version 1.0
 */

public class WriteToFileBigBL {
private static Logger logger = Logger.getLogger(WriteToFileBigBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private LYSendToBankSchema inLYSendToBankSchema = new LYSendToBankSchema();
	private TransferData inTransferData = new TransferData();
	private String fileName = "";
	private GlobalInput inGlobalInput = new GlobalInput();
	private String bankCode = "";

	public WriteToFileBigBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"WRITE"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 生成银行文件数据
		if (!mOperate.equals("WRITE")) {
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * ===================将外部传入的数据分解到本类的属性中=====================
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLYSendToBankSchema = (LYSendToBankSchema) mInputData
					.getObjectByObjectName("LYSendToBankSchema", 0);
			inGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * ================根据前面的输入数据，进行逻辑处理 ==========================
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 打标志是否提交生成文件的步骤
		boolean mCreateLybanklog = true;
		/** *********此处设置提交文件名到lybanklog表中********** */
		LYBankLogSet tLYBankLogSet = getLYBankLog(inLYSendToBankSchema);
		if (tLYBankLogSet.size() == 0) {
			throw new NullPointerException("无银行日志数据！");
		}

		// 生成发送文件全路径
		fileName = getFilePath() + fileName;
		bankCode = tLYBankLogSet.get(1).getBankCode();
		inTransferData.setNameAndValue("fileName", fileName);
		inTransferData.setNameAndValue("bankCode", bankCode);
		VData tVData = new VData();
		MMap map = new MMap();
		map.put(tLYBankLogSet, "UPDATE");
		tVData.add(map);

		LYSendToBankSet tLYSendToBankSet = new LYSendToBankSet();
		/** 开始调用WriterToFileBL 作生成文件的操作并需先写入一些必须值* */
		WriteToFileBigBLS tWriteToFileBigBLS = new WriteToFileBigBLS();
		if (!tWriteToFileBigBLS.setInputData(inTransferData, inGlobalInput)) {
			throw new NullPointerException("初始化数据失败");
		}
		// 得到这个批次的一些初始化值
		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		// 得到这个批次发生的总金额
		tSql = "select sum(paymoney) from lysendtobank where serialno = '"
				+ "?serialno?" + "'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("serialno", inLYSendToBankSchema.getSerialNo());
		tSSRS = tExeSQL.execSQL(sqlbv1);
		String tPayMoney = tSSRS.GetText(1, 1);
		tWriteToFileBigBLS.setSerialnoMoney(tPayMoney);
		// 得到这个批次的总的条数
		tSql = "select count(*) from lysendtobank where serialno = '"
				+ "?serialno1?" + "'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("serialno1", inLYSendToBankSchema.getSerialNo());
		tSSRS = tExeSQL.execSQL(sqlbv2);
		tWriteToFileBigBLS.setSerialnoNum(tSSRS.GetText(1, 1));
		// 得到此次处理的文件的扣划类型
		tSql = "select logtype from lybanklog where serialno =  '"
				+ "?serialno2?" + "'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("serialno2", inLYSendToBankSchema.getSerialNo());
		tSSRS = tExeSQL.execSQL(sqlbv3);
		tWriteToFileBigBLS.setDealType(tSSRS.GetText(1, 1));
		// 写入文件头
		if (!tWriteToFileBigBLS.WriterXMLHead()) {

			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "写入文件失败!!";
			this.mErrors.addOneError(tError);

			return false;

		}
		try {
			tSql = "select * from lysendtobank where serialno = '"
					+ "?serialno3?" + "' order by serno";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("serialno3", inLYSendToBankSchema.getSerialNo());
			RSWrapper rsWrapper = new RSWrapper();
			if (!rsWrapper.prepareData(tLYSendToBankSet, sqlbv4)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				logger.debug(rsWrapper.mErrors.getFirstError());
				return false;
			}

			do {
				logger.debug("Start getData....");
				try {
					rsWrapper.getData();
				} catch (Exception e) {
					e.printStackTrace();
				}
				for (int i = 0; i < tLYSendToBankSet.size(); i++) {
					mCreateLybanklog = true;
					tWriteToFileBigBLS.WriterXMLBody(tLYSendToBankSet
							.get(i + 1).getSchema());
				}
			} while (tLYSendToBankSet.size() > 0);
			tWriteToFileBigBLS.WriterXMLEnd();

			if (!tWriteToFileBigBLS.xmlTransform()) {
				throw new NullPointerException("Xml处理失败,无法转换!!");
			}

			if (mCreateLybanklog) {
				PubSubmit p = new PubSubmit();
				if (!p.submitData(tVData, "")) {

				}
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "WriteToFileBigBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 生成银行日志数据
	 * 
	 * @param tLYSendToBankSchema
	 * @return
	 */
	private LYBankLogSet getLYBankLog(LYSendToBankSchema tLYSendToBankSchema) {
		LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();

		// 获取日志记录
		tLYBankLogSchema.setSerialNo(tLYSendToBankSchema.getSerialNo());
		tLYBankLogSet.set(tLYBankLogSchema.getDB().query());

		if (tLYBankLogSet.size() > 0) {
			tLYBankLogSchema.setSchema(tLYBankLogSet.get(1));

			// 构建文件名，未加后缀名,代收文件名加"S",代付wenjianming加"F"
			fileName = "B" + tLYBankLogSchema.getBankCode()
					+ tLYBankLogSchema.getLogType()
					+ tLYBankLogSchema.getSerialNo() + "("
					+ PubFun.getCurrentDate() + ")";

			// 修改日志
			tLYBankLogSchema.setSendDate(PubFun.getCurrentDate());
			tLYBankLogSchema.setSendOperator(inGlobalInput.Operator);
			tLYBankLogSchema.setOutFile(fileName + ".z");

			// 查询该批次是否有头文件的信息
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "select bankselfcode from ldbank  where bankcode = '"
					+ "?bankcode?" + "'";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("bankcode", tLYBankLogSchema.getBankCode());
			tSSRS = tExeSQL.execSQL(sqlbv5);
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug("=================================================");
			logger.debug(tSql);
			// if(tLYBankLogSchema.getBankCode().equals("320101")||tLYBankLogSchema.getBankCode().equals("320105")
			// ||tLYBankLogSchema.getBankCode().equals("5108010")||tLYBankLogSchema.getBankCode().equals("2100110")
			// || tLYBankLogSchema.getBankCode().equals("3100010")
			// ||tLYBankLogSchema.getBankCode().equals("3100090"))
			// {
			// tLYBankLogSchema.setOutFileB(fileName + "b.z");
			// }

			if (tSSRS.GetText(1, 1) != null
					&& !tSSRS.GetText(1, 1).equals("null")
					&& !tSSRS.GetText(1, 1).equals("")
					&& tSSRS.GetText(1, 1).equals("Y")) {
				logger.debug("+++++++++++++++++生成了头文件插入lybanklog++++++++++++++++++++++++++");
				tLYBankLogSchema.setOutFileB(fileName + "b.z");
			}

			tLYBankLogSet.clear();
			tLYBankLogSet.add(tLYBankLogSchema);
		}

		return tLYBankLogSet;
	}

	private String getFilePath() {
		LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();

		tLDSysVarSchema.setSysVar("SendToBankFilePath");
		tLDSysVarSchema = tLDSysVarSchema.getDB().query().get(1);

		return tLDSysVarSchema.getSysVarValue();
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		mResult.add(fileName);
		return mResult;
	}

	public static void main(String[] args) {
		WriteToFileBigBL WriteToFileBigBL1 = new WriteToFileBigBL();
	}
}
