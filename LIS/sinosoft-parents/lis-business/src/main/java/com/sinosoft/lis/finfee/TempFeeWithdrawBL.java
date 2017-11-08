package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 暂交费退费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim motify by G
 * @version 1.0
 */

public class TempFeeWithdrawBL
{
private static Logger logger = Logger.getLogger(TempFeeWithdrawBL.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap outMap = new MMap();
	/** 数据操作字符串 */
	private String tLimit = "";
	private String tNo = ""; // 生成的实付号码
	private String tSo = ""; // 生成的流水号
	private String mOperate;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private Reflections mReflections = new Reflections();
	private double SaveMoney = 0;

	// 为核保撤单增加
	private String mBankCode = "";
	private String mAccNo = "";
	private String mAccName = "";
	private String mUWFlag = "";
	private String mGetNoticeNo = "";
	private String mNotBLS = "";
	private String mPayMode = "";
	private String mSubmitFlag = "";
	private double mGetMoney = 0;
	private String mActuGetNo = "";
	private String mOtherNoFlag = "";

	// 为零现金增加
	private String tBankCode = "";
	private String tAccNo = "";
	private String tAccName = "";
	boolean bankFlag = false;
	private GlobalInput tG = new GlobalInput();
	private LJTempFeeSet inLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();

	private LJAGetTempFeeSet inLJAGetTempFeeSet = new LJAGetTempFeeSet();

	private LJAGetSet outLJAGetSet = new LJAGetSet();

	private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
	private LJAGetTempFeeSet outLJAGetTempFeeSet = new LJAGetTempFeeSet();
	private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private VData cResult = new VData();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();
	private MMap nmap = new MMap();
	private String ReleaseContNo="N";

	public TempFeeWithdrawBL()
	{
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		try
		{
			// 得到外部传入的数据,将数据备份到本类中
			if(!getInputData(cInputData))
				return false;
			logger.debug("---End getInputData---");
			if(!prepareTempFee())
				return false;
			// 进行业务处理
			if(!dealData())
				return false;
			logger.debug("---End dealData---");

			// 准备往后台的数据
			if(!prepareOutputData())
				return false;

			logger.debug("---End prepareOutputData---");

			// 为外部调用提供接口，外部传入该参数后，程序将不自&&动调用BLS，外部可通过getResult方法获取准备好的数据VData
			if(((mNotBLS != null && mNotBLS.length() > 0)) && !mNotBLS.equals(""))
			{
				logger.debug("---------1---heyq-testt===");
				mResult = mInputData;
			}
			else
			{
				logger.debug("---------2---heyq-testt===");
				// logger.debug("Start TempFeeWithdraw BLS Submit...");
				// TempFeeWithdrawBLS tTempFeeWithdrawBLS = new
				// TempFeeWithdrawBLS();
				// tTempFeeWithdrawBLS.submitData(mInputData, cOperate);
				// logger.debug("End TempFeeWithdraw BLS Submit...");
				//
				// //如果有需要处理的错误，则返回
				// if (tTempFeeWithdrawBLS.mErrors.needDealError()) {
				// this.mErrors.copyAllErrors(tTempFeeWithdrawBLS.mErrors);
				// mResult.clear();
				// } else {
				// mResult = tTempFeeWithdrawBLS.getResult();
				// }
				//
				// mInputData = null;
			}
		}
		catch(Exception ex)
		{
			CError.buildErr(this, ex.toString());
			return false;
		}
		finally
		{
			mLock.unLock();
		}
		return true;
	}

	/**
	 * 校验该险种是否退费
	 * 
	 * @param tLJTempFeeSchema
	 * @return
	 */
	private boolean verifyLJAGetTempFee(LJTempFeeSchema tLJTempFeeSchema)
	{
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();

		// 确认该险种未退费
		tLJAGetTempFeeSchema.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
		tLJAGetTempFeeSchema.setTempFeeType(tLJTempFeeSchema.getTempFeeType());
		// tLJAGetTempFeeSchema.setRiskCode(tLJTempFeeSchema.getRiskCode());

		// 判断暂交费退费实付表是否有数据和暂交费表数据是否已核销
		if((tLJAGetTempFeeSchema.getDB().query()).size() != 0)
		{
			// @@错误处理
			CError.buildErr(this, "收据号为"+tLJTempFeeSchema.getTempFeeNo()+",存在已退的暂收退费，请核实!");
			return false;
		}

		return true;
	}

	/**
	 * 设置暂交费退费实付表
	 * 
	 * @param tLJAGetTempFeeSchema
	 * @param tLJTempFeeSchema
	 * @return
	 */
	private void setLJAGetTempFee(LJAGetTempFeeSchema tLJAGetTempFeeSchema, LJTempFeeSchema tLJTempFeeSchema, int i)
	{
		// 将暂交费表的内容复制到暂交费退费实付表
		mReflections.transFields(tLJAGetTempFeeSchema, tLJTempFeeSchema);
		if(mGetMoney > 0)
		{
			tLJAGetTempFeeSchema.setGetMoney(mGetMoney);
		}
		else
		{
			tLJAGetTempFeeSchema.setGetMoney(tLJTempFeeSchema.getPayMoney());
		}
		tLJAGetTempFeeSchema.setOperator(tG.Operator);
		// logger.debug("inLJAGetTempFeeSchema:"+inLJAGetTempFeeSchema.encode());

		tLJAGetTempFeeSchema.setManageCom(mLJTempFeeSet.get(1).getManageCom());

		tLJAGetTempFeeSchema.setEnterAccDate("");
		tLJAGetTempFeeSchema.setConfDate("");
		tLJAGetTempFeeSchema.setMakeDate(CurrentDate); // 入机时间
		tLJAGetTempFeeSchema.setMakeTime(CurrentTime); // 入机日期
		tLJAGetTempFeeSchema.setModifyDate(CurrentDate); // 最后一次修改日期
		tLJAGetTempFeeSchema.setModifyTime(CurrentTime); // 最后一次修改时间
		logger.debug("实付号码:" + tNo);
		tLJAGetTempFeeSchema.setActuGetNo(tNo); // 实付号码
		tLJAGetTempFeeSchema.setGetReasonCode((inLJAGetTempFeeSet.get(1)).getGetReasonCode()); // 退费原因编码
		// 如果为健康委托产品，则财务类型为特殊的财务类型CM
		if("1".equals(tLJTempFeeSchema.getRiskType()))
			tLJAGetTempFeeSchema.setFeeFinaType("CM");
		outLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
	}

	/**
	 * 设置实收总表
	 * 
	 * @param tLJTempFeeSchema
	 */
	private boolean setLJAGet(LJTempFeeSet mLJTempFeeSet)
	{
		// 添加一条实付总表纪录
		tSo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		LJAGetSchema outLJAGetSchema = new LJAGetSchema();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema = mLJTempFeeSet.get(1);
		outLJAGetSchema.setActuGetNo(tNo);
		// 2007-12-29 zy 将暂交费号传入到实付表中的otherno
		outLJAGetSchema.setOtherNo(tLJTempFeeSchema.getTempFeeNo());
		outLJAGetSchema.setOtherNoType("4");// 所有的暂收退费的类型都为4，表示暂收退费的收据号
		// if (mOtherNoFlag.equals("1") || mOtherNoFlag.equals("9"))// mod by
		// // gaoht
		// // 结算业务暂收费退费
		// {
		// outLJAGetSchema.setOtherNoType("4");
		// } else if (mOtherNoFlag.equals("YC")) {
		// outLJAGetSchema.setOtherNoType("YC");
		// } else if (mOtherNoFlag.equals("2")) {
		// outLJAGetSchema.setOtherNoType("3"); // 续期暂收退费
		// } else {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "TempFeeWithdrawBL";
		// tError.functionName = "setLJAGet";
		// tError.errorMessage = "该业务类型不能进行暂收退费!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		outLJAGetSchema.setPayMode("1");
		if(mGetMoney > 0)
		{
			outLJAGetSchema.setSumGetMoney(mGetMoney);
		}
		else
		{
			outLJAGetSchema.setSumGetMoney(SaveMoney);
		}
		outLJAGetSchema.setSaleChnl(tLJTempFeeSchema.getSaleChnl());
//		outLJAGetSchema.setManageCom(tLJTempFeeSchema.getPolicyCom());
		outLJAGetSchema.setManageCom(tLJTempFeeSchema.getManageCom());
		if("".equals(tLJTempFeeSchema.getPolicyCom()) || tLJTempFeeSchema.getPolicyCom()==null)
		tLJTempFeeSchema.setPolicyCom(tLJTempFeeSchema.getManageCom());
		else
			tLJTempFeeSchema.setPolicyCom(tLJTempFeeSchema.getPolicyCom());
		outLJAGetSchema.setAgentCom(tLJTempFeeSchema.getAgentCom());
		outLJAGetSchema.setAgentType(tLJTempFeeSchema.getAgentType());

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(tLJTempFeeSchema.getOtherNo());
		 if(tLCAppntDB.getInfo())
		 {
			outLJAGetSchema.setDrawer(tLCAppntDB.getAppntName());
			outLJAGetSchema.setDrawerID(tLCAppntDB.getIDNo());
		 }
		 else
		 {
			 outLJAGetSchema.setDrawer(tLJTempFeeSchema.getAPPntName());
		 }

		// yt提供的sql
		// String strSql = "select appntNo from lcpol where proposalNo in (
		// select otherNo from ljtempfee where tempfeeNo='"
		// + outLJAGetSchema.getOtherNo() + "') or prtNo in ( select otherNo
		// from ljtempfee where tempfeeNo='"
		// + outLJAGetSchema.getOtherNo() + "')";
		// hezy效率调整20041022
		// zy 暂收费表中的otherno存放的是印刷号或者是合同号，或者是家庭单大合同号
		String strSql = "select appntNo,appntname from lccont where  EXISTS ( select otherNo from ljtempfee where "
				      + "tempfeeNo='?tempfeeNo?' and lccont.contno=otherNo) union "
				      + " select appntNo,appntname from lccont where  EXISTS( select otherNo from ljtempfee where " 
				      + "tempfeeNo='?tempfeeNo?' and lccont.prtno=otherNo) union "
				      + "select appntno,appntname from lccont where EXISTS( select otherNo from ljtempfee where " 
				      + "tempfeeNo='?tempfeeNo?' and lccont.familycontno=otherNo )";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("tempfeeNo", outLJAGetSchema.getOtherNo());

		logger.debug("Query appno:" + strSql);
		ExeSQL mExeSQL = new ExeSQL();

		SSRS tSSRS = mExeSQL.execSQL(sqlbv);
		if(tSSRS.getMaxNumber() > 0)
		{
			outLJAGetSchema.setAppntNo(tSSRS.GetText(1, 1));
		}

		outLJAGetSchema.setSaleChnl(tLJTempFeeSchema.getSaleChnl());
		outLJAGetSchema.setShouldDate(PubFun.getCurrentDate());
		// outLJAGetSchema.setAgentCode(tLJTempFeeSchema.getAgentCode());
		// if(mOtherNoFlag.equals("9"))
		// {
		// LXbalanceSubDB tLXbalanceSubDB = new LXbalanceSubDB();
		// LXbalanceSubSet tLXbalanceSubSet = new LXbalanceSubSet();
		// String tSql = "select * from LXbalanceSub where balanceno =
		// '"+tLJTempFeeSchema.getTempFeeNo()+"'";
		// tLXbalanceSubSet = tLXbalanceSubDB.executeQuery(tSql);
		// outLJAGetSchema.setAgentCode(tLXbalanceSubSet.get(1).getAgentCode());
		// }
		outLJAGetSchema.setAgentCode(tLJTempFeeSchema.getAgentCode());// 取缴费的业务员modify
		// by
		// haopan
		// -20070704
		outLJAGetSchema.setAgentGroup(tLJTempFeeSchema.getAgentGroup());
		outLJAGetSchema.setStartGetDate(PubFun.getCurrentDate());

		outLJAGetSchema.setSerialNo(tSo);
		outLJAGetSchema.setOperator(tG.Operator);
		outLJAGetSchema.setMakeDate(CurrentDate);
		outLJAGetSchema.setMakeTime(CurrentTime);
		outLJAGetSchema.setModifyDate(CurrentDate);
		outLJAGetSchema.setModifyTime(CurrentTime);
		// 设置给付通知书号
		outLJAGetSchema.setGetNoticeNo(mGetNoticeNo);
		//2011-5-11 插入ljaget数据报错，currency不能为空
		outLJAGetSchema.setCurrency(tLJTempFeeSchema.getCurrency());

		// 银行退费判断
		// 判断是否是核保进行的退费

		if(((mUWFlag != null && mUWFlag.length() > 0)) && mUWFlag.equals("1"))
		{
			outLJAGetSchema.setPayMode(mPayMode);
			outLJAGetSchema.setBankCode(mBankCode);
			outLJAGetSchema.setBankAccNo(mAccNo);
			outLJAGetSchema.setAccName(mAccName);
		}
		else
		// 正常进行的退费
		{
			// 银行转账最优先
			if(bankFlag)
			{
				outLJAGetSchema.setBankCode(tBankCode);
				outLJAGetSchema.setBankAccNo(tAccNo);
				outLJAGetSchema.setAccName(tAccName);
				outLJAGetSchema.setPayMode("4");
			}
			else
			{

				LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
				tLJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
				LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
				if(tLJTempFeeClassSet.size() == 0)
					return false;
				boolean chequeFlag = false;
				LJTempFeeClassSchema tLJTempFeeClassSchema = null;

				for(int i = 0; i < tLJTempFeeClassSet.size(); i++)
				{

					if(tLJTempFeeClassSet.get(i + 1).getPayMode().equals("2")
							|| tLJTempFeeClassSet.get(i + 1).getPayMode().equals("3"))
					{
						chequeFlag = true;
						tLJTempFeeClassSchema = tLJTempFeeClassSet.get(i + 1);
						break;
					}
				}

				// 支票，优先级相对现金高
				if(chequeFlag)
				{
					outLJAGetSchema.setPayMode(tLJTempFeeClassSchema.getPayMode());
					outLJAGetSchema.setBankCode(tLJTempFeeClassSchema.getBankCode());
					outLJAGetSchema.setBankAccNo(tLJTempFeeClassSchema.getChequeNo());
				}
				else
				{
					outLJAGetSchema.setPayMode("1"); // 现金付费
					outLJAGetSchema.setBankAccNo("");
					outLJAGetSchema.setBankCode("");
					outLJAGetSchema.setAccName("");
				}
			}
		}
		outLJAGetSet.add(outLJAGetSchema);
		return true;
	}

	// 打印管理表述据
	private boolean setLOPrtManager(LJTempFeeSet tLJTempFeeSet)
	{
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema = tLJTempFeeSet.get(1);
		if(tLJTempFeeSchema.getTempFeeNo() != null)
		{
			/** @todo 准备打印表数据 * */
			LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
			String prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			try
			{
				tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
				tLOPRTManagerSchema.setOtherNo(tLJTempFeeSchema.getOtherNo());
				tLOPRTManagerSchema.setOtherNoType("00");
				tLOPRTManagerSchema.setCode("90");
				tLOPRTManagerSchema.setManageCom(tLJTempFeeSchema.getPolicyCom());
				tLOPRTManagerSchema.setAgentCode(tLJTempFeeSchema.getAgentCode());
				tLOPRTManagerSchema.setReqCom(tG.ManageCom);
				tLOPRTManagerSchema.setReqOperator(tG.Operator);
				tLOPRTManagerSchema.setPrtType("0");
				tLOPRTManagerSchema.setStateFlag("0");
				tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
				tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
				tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
				tLOPRTManagerSchema.setStandbyFlag6(tLJTempFeeSchema.getTempFeeNo());
				tLOPRTManagerSchema.setStandbyFlag7(tNo);
				tLOPRTManagerSchema.setPatchFlag("0");
				mLOPRTManagerSet.add(tLOPRTManagerSchema);
			}
			catch(Exception ex)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 核销处理
	 * 
	 * @param tLJTempFeeSchema
	 */
	private void confTempFee(LJTempFeeSchema tLJTempFeeSchema)
	{
		// 核销暂交费表
		tLJTempFeeSchema.setConfFlag("1");
		tLJTempFeeSchema.setConfDate(PubFun.getCurrentDate());
		outLJTempFeeSet.add(tLJTempFeeSchema);

		// 核销暂交费分类表
		LJTempFeeClassDB LJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
		LJTempFeeClassSet tLJTempFeeClassSet = LJTempFeeClassDB.query();

		for(int i = 0; i < tLJTempFeeClassSet.size(); i++)
		{
			tLJTempFeeClassSet.get(i + 1).setConfFlag("1");
			tLJTempFeeClassSet.get(i + 1).setConfDate(PubFun.getCurrentDate());
		}

		outLJTempFeeClassSet.add(tLJTempFeeClassSet);
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{

		tLimit = PubFun.getNoLimit(mLJTempFeeSet.get(1).getManageCom());

		// 如果是从溢缴退费中传入实付号码就不用生成的的号码
		if(mActuGetNo != null && !mActuGetNo.equals(""))
		{
			tNo = mActuGetNo;
		}
		else
		{
			tNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		}

		// 处理暂收表和暂收退费表
		HashSet  LockSet = new HashSet ();
		for(int i = 1; i <= mLJTempFeeSet.size(); i++)
		{ // SET是从1开始记数
			// 取出一行，并查询出该行的全部信息
			LJTempFeeSchema tLJTempFeeSchema = mLJTempFeeSet.get(i);

			// 判断暂交费是否已到帐
			if(tLJTempFeeSchema.getEnterAccDate() == null)
			{
				CError.buildErr(this, "该暂交费还未到帐，不能退费！");
				return false;
			}
			// 判断暂收是否核销
			if(tLJTempFeeSchema.getConfDate() != null)
			{
				CError.buildErr(this, "该笔暂收已经被核销！");
				return false;
			}
			

			// 校验该险种是否退费
			if(!verifyLJAGetTempFee(tLJTempFeeSchema))
			{
				CError.buildErr(this, tLJTempFeeSchema.getTempFeeNo() + "的该险种已经退费!");
				return false;
			}

			// 增加并发控制，zy
			String[] tOperatedNo = new String[2];
			tOperatedNo[0] = (String)tLJTempFeeSchema.getTempFeeNo();
			tOperatedNo[1] = (String)tLJTempFeeSchema.getOtherNo();
			
			LockSet.add(tOperatedNo[0]);
			//ZY 如果是承保调用暂收退费则不进行印刷号的并发
			if(!"Y".equals(ReleaseContNo))
			LockSet.add(tOperatedNo[1]);
		}


		String[] LockNo = new String[LockSet.size()];
		int k=0;
		for(Iterator iterator = LockSet.iterator(); iterator.hasNext();)
		{
			LockNo[k++] = (String)iterator.next();
		}
		  //进行并发组的控制
		if(!mLock.lock(LockNo, "LF0001", tG.Operator))
		{
		   CError tError = new CError(mLock.mErrors.getLastError());
		   this.mErrors.addOneError(tError);
		   return false;
		 
		 }
		logger.debug("End verifyLJAGetTempFee");

		for(int i = 1; i <= mLJTempFeeSet.size(); i++)
		{ 
			LJTempFeeSchema tLJTempFeeSchema = mLJTempFeeSet.get(i);
			// 为零现金增加；如果该收据号下有一笔交费方式为银行转账，则该收据号下的所有的付费都为银行转账且银行相应的信息为第一条的银行信息
			LJTempFeeClassDB tempFeeClassDB = new LJTempFeeClassDB();
			tempFeeClassDB.setTempFeeNo(tLJTempFeeSchema.getTempFeeNo());
			LJTempFeeClassSet tempFeeClassSet = tempFeeClassDB.query();
			for(int j = 1; j <= tempFeeClassSet.size(); j++)
			{
				if("4".equals(tempFeeClassSet.get(j).getPayMode()))
				{
					tBankCode = tempFeeClassSet.get(j).getBankCode();
					tAccNo = tempFeeClassSet.get(j).getBankAccNo();
					tAccName = tempFeeClassSet.get(j).getAccName();
					bankFlag = true;
					break;
				}
			}

			// 设置暂交费退费实付表
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			setLJAGetTempFee(tLJAGetTempFeeSchema, tLJTempFeeSchema, i);
			logger.debug("End setLJAGetTempFee");

			// 核销处理
			confTempFee(tLJTempFeeSchema);
		}
		// 设置实付总表
		if(!setLJAGet(mLJTempFeeSet))
		{
			return false;
		}

		// 生成打印管理表,MS暂不处理该功能
//		if(mOperate.endsWith("WITHDRAW"))
//			setLOPrtManager(mLJTempFeeSet);

		logger.debug("End setLJAGet");

		/** @todo 如果为续期暂收退费，需要将抽档撤销 直接调用续期抽档撤销程序 */
		if("2".equals(mLJTempFeeSet.get(1).getTempFeeType()))
		{
			//zy 2009-06-11 针对系统切换续期未结业务处理，查询应收是否存在，如果不存在，则不进行续期抽档撤销，且上线业务保留该逻辑
			boolean xFlag=true;
			for(int j=1;j<=mLJTempFeeSet.size();j++)
			{
				LJSPayDB tLJSPayDB = new LJSPayDB();
				tLJSPayDB.setGetNoticeNo(mLJTempFeeSet.get(j).getTempFeeNo());
				if(!tLJSPayDB.getInfo())
				{
					xFlag=false;
					break;
				}		
			}
			if(xFlag)
			{
				LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
				tLCContSchema.setContNo(mLJTempFeeSet.get(1).getOtherNo());
				VData tVData = new VData();
				tVData.add(tLCContSchema);
				tVData.add(tG);
				IndiDueFeeCancelBL IndiDueFeeCancelBL = new IndiDueFeeCancelBL();
				if(!IndiDueFeeCancelBL.submitData(tVData, "TempDelete"))
				{
					mErrors.copyAllErrors(IndiDueFeeCancelBL.mErrors);
					CError.buildErr(this, "续期抽档撤销失败！");
					return false;
				}
				else
				{
					cResult = IndiDueFeeCancelBL.getResult();
				}
	
	//			mInputData.add(cResult);
				//zy 2009-05-19 获取催收撤销的map对象
				if(cResult.size()>0)
					nmap =(MMap)cResult.get(0);
			}

			// String Ssql = "select * from ljspay where getnoticeno = '"+
			// mLJTempFeeSet.get(1).getTempFeeNo() + "'";
			// LJSPayDB tLJSPayDB = new LJSPayDB();
			// LJSPaySet tLJSPaySet = new LJSPaySet();
			// tLJSPaySet = tLJSPayDB.executeQuery(Ssql);
			// if (tLJSPaySet.size() > 0) {
			// if (!prepareCancelIndi()) {
			// return false;
			// }
			// }
		}

		return true;
	}

	// private boolean prepareCancelIndi() {
	// // LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
	// // tLCContSchema.setContNo(mLJTempFeeSet.get(1).getOtherNo());
	// // TransferData tTransferData = new TransferData();
	// // tTransferData.setNameAndValue("SubmitFlag", "noSubmit");
	// // VData tVData = new VData();
	// // tVData.add(tLCContSchema);
	// // tVData.add(tG);
	// // tVData.add(tTransferData);
	// // IndiDueFeeCancelBL tIndiDueFeeCancelUI = new IndiDueFeeCancelBL();
	// // if (!tIndiDueFeeCancelUI.submitData(tVData, "FinFee"))
	// // {
	// // this.mErrors.copyAllErrors(tIndiDueFeeCancelUI.mErrors);
	// // return false;
	// // }
	// MMap mCertMap = new MMap();
	// LJSPayDB tLJSPayDB = new LJSPayDB();
	// tLJSPayDB.setOtherNo(mLJTempFeeSet.get(1).getOtherNo());
	// LJSPaySet tLJSPaySet = new LJSPaySet();
	// tLJSPaySet = tLJSPayDB.query();
	// if (tLJSPaySet.size() > 0) {
	// if (tLJSPaySet.get(1).getOtherNoType().equals("2")
	// || tLJSPaySet.get(1).getOtherNoType().equals("3")
	// || tLJSPaySet.get(1).getOtherNoType().equals("8")) {
	// mCertMap.put("delete from ljspay where otherno='"
	// + mLJTempFeeSet.get(1).getOtherNo()
	// + "' and othernotype in ('2','3','8','1')", "DELETE");
	// mCertMap.put("delete from ljspayperson where contno='"
	// + mLJTempFeeSet.get(1).getOtherNo()
	// + "' and paycount>1 and paytype='ZC'", "DELETE");
	//
	// String tSql = "select polno from ljspayperson where contno='"
	// + mLJTempFeeSet.get(1).getOtherNo()
	// + "' and polno in (select polno from lcpol where appflag='9' and
	// contno='"
	// + mLJTempFeeSet.get(1).getOtherNo() + "')";
	// SSRS nSSRS = new SSRS();
	// ExeSQL tExeSQL = new ExeSQL();
	// nSSRS = tExeSQL.execSQL(tSql);
	// for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
	// String tPolNo = nSSRS.GetText(i, 1);
	// mCertMap.put("delete from lcpol where polno='" + tPolNo
	// + "'", "DELETE");
	// mCertMap.put("delete from lcduty where polno='" + tPolNo
	// + "'", "DELETE");
	// mCertMap.put("delete from lcget where polno='" + tPolNo
	// + "'", "DELETE");
	// mCertMap.put("delete from lcprem where polno='" + tPolNo
	// + "'", "DELETE");
	// }
	// } else if (tLJSPaySet.get(1).getOtherNoType().equals("1")) {
	// mCertMap.put("delete from ljspay where otherno='"
	// + mLJTempFeeSet.get(1).getOtherNo()
	// + "' and othernotype in ('1')", "DELETE");
	// mCertMap.put("delete from ljspaygrp where grpcontno='"
	// + mLJTempFeeSet.get(1).getOtherNo()
	// + "' and paycount>1 and paytype='ZC'", "DELETE");
	// mCertMap.put("delete from ljspayperson where grpcontno='"
	// + mLJTempFeeSet.get(1).getOtherNo()
	// + "' and paycount>1 and paytype='ZC'", "DELETE");
	// } else {
	//
	// }
	// }
	// outMap.add(mCertMap);
	//
	// return true;
	//
	// }

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData)
	{
		// 暂交费表
		inLJTempFeeSet.set((LJTempFeeSet)mInputData.getObjectByObjectName("LJTempFeeSet", 0));
		inLJAGetTempFeeSet.set((LJAGetTempFeeSet)mInputData.getObjectByObjectName("LJAGetTempFeeSet", 0));
		tG = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);

		try
		{
			TransferData tTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
			mBankCode = (String)tTransferData.getValueByName("BankCode");
			mAccNo = (String)tTransferData.getValueByName("AccNo");
			mAccName = (String)tTransferData.getValueByName("AccName");
			mUWFlag = (String)tTransferData.getValueByName("BankFlag");
			mGetNoticeNo = (String)tTransferData.getValueByName("GetNoticeNo");
			mNotBLS = (String)tTransferData.getValueByName("NotBLS");
			mPayMode = (String)tTransferData.getValueByName("PayMode");
			// ========ADD======zhangtao====2005-04-14=================BGN=================
			mSubmitFlag = (String)tTransferData.getValueByName("SubmitFlag");
			// ========ADD======zhangtao====2005-04-14=================END=================

			/** @todo --------------ADD FOR 客户预存----------------gaoht----- * */
			// 由于增加续期的暂收退费所以其它号码类型为必录项
			// 调整，判断续期收费通过暂收费类型
			mOtherNoFlag = (String)tTransferData.getValueByName("OtherNoFlag");
			// if (mOtherNoFlag == null || mOtherNoFlag.length() == 0) {
			// // @@错误处理
			// CError.buildErr(this, "没有得到退费业务类型!");
			// return false;
			//
			// }
			// ========================================================END
			String tempMoney = tTransferData.getValueByName("GetMoney")==null?"0":(String)tTransferData.getValueByName("GetMoney");
			mGetMoney = Double.parseDouble(tempMoney);
			mActuGetNo = (String)tTransferData.getValueByName("ActuGetNo");
			//ZY 获取承保传递的标志
			ReleaseContNo = (String)tTransferData.getValueByName("ReleaseContNo");
			// 容错处理
			if(mGetNoticeNo == null)
				mGetNoticeNo = "";
			if(mNotBLS == null)
				mNotBLS = "";
		}
		catch(Exception e)
		{
			logger.debug("正常退费，非核保退费！");
		}

		// if(inLJTempFeeSet == null || inLJAGetTempFeeSet == null||tG==null) {
		if(inLJTempFeeSet == null || tG == null)
		{
			// @@错误处理
			CError.buildErr(this, "没有得到足够的数据，请您确认!");
			return false;
		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData()
	{

		logger.debug("----before submit----");
		mInputData = new VData();
		outMap.put(outLJAGetTempFeeSet, "INSERT");
		outMap.put(outLJTempFeeSet, "UPDATE");
		outMap.put(outLJTempFeeClassSet, "UPDATE");
		outMap.put(outLJAGetSet, "INSERT");
//		if(mOperate.endsWith("WITHDRAW"))
//			outMap.put(mLOPRTManagerSet, "INSERT");
//		mInputData.add(outMap);
		nmap.add(outMap);
		mInputData.add(nmap);
		// ========UPD======zhangtao====2005-04-14=================BGN=================
		if(mSubmitFlag != null && mSubmitFlag.equals("NoSubmit"))
		{
			// DoNothing
		}
		else
		{
			PubSubmit tPubSubmit = new PubSubmit();
			if(!tPubSubmit.submitData(mInputData, ""))
			{
				CError.buildErr(this, "暂交退费数据更新有误", tPubSubmit.mErrors);
				return false;
			}

			logger.debug("----after submit----");
		}
		// MS暂屏蔽该功能
		// if (outLJTempFeeSet.get(1).getTempFeeType().equals("2")) {
		// if (!outLJTempFeeSet.get(1).getOtherNoType().equals("1")) {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("Contno",
		// outLJTempFeeClassSet.get(1).getOtherNo());
		// RnDealBLF tRnDealBLF = new RnDealBLF();
		// VData tv = new VData();
		// tv.add(tTransferData);
		// tv.add(tG);
		// tRnDealBLF.submitData(tv, "");
		// } else {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("Contno",
		// outLJTempFeeClassSet.get(1).getOtherNo()); // autorenew
		// VData tv = new VData();
		// tv.add(tTransferData);
		// tv.add(tG);
		// logger.debug("准备好了数据");
		// // tv.add(tTransferData);
		//
		// RNGrpDealBL tRNGrpDealBL = new RNGrpDealBL();
		// if (tRNGrpDealBL.submitData(tv, "Confirm")) {
		// logger.debug("个单批处理催收完成");
		// } else {
		// logger.debug("个单批处理催收信息提示："
		// + tRNGrpDealBL.mErrors.getFirstError());
		// }
		//
		// }
		// }
		// ========UPD======zhangtao====2005-04-14=================END=================

		return true;
	}

	// 获取暂交费信息
	public boolean prepareTempFee()
	{
		/* 准备此次暂交费钱数 */
		SaveMoney = 0;

		String feeNo = "";
		for(int i = 1; i <= inLJTempFeeSet.size(); i++)
		{
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema = inLJTempFeeSet.get(i);
			String tTempFeeNo = tLJTempFeeSchema.getTempFeeNo();
			//判断是否有重复的收据号
			if(feeNo.equals(tTempFeeNo))
				continue;
			else
				feeNo=tTempFeeNo;
//		
//			if(!veriryLJSPAY(tLJTempFeeSchema))
//			{
//				CError.buildErr(this, tLJTempFeeSchema.getTempFeeNo() + "的暂収正处银行发盘在途,暂不能进行退费操作!");
//				return false;
//			}
			
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			String tSql = "select * from ljtempfee where enteraccdate is not null and enteraccdate <> '3000-1-1' and confdate is null and "
					   + "tempfeeno='?tTempFeeNo?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tTempFeeNo", tTempFeeNo);
			logger.debug("TempFee::::::::::::::" + tSql);
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv1);
			if(tLJTempFeeSet== null ||tLJTempFeeSet.size()<=0)
			{
				CError.buildErr(this, "不存在收据号为"+tTempFeeNo+"需要退费的暂收记录，请核实！");
				return false;
			}
			mLJTempFeeSet.add(tLJTempFeeSet);
			if(mOtherNoFlag != null && mOtherNoFlag.equals("YC"))
			{
				mLJTempFeeSet.clear();
				mLJTempFeeSet.add(inLJTempFeeSet);
				mUWFlag = "9";
				mNotBLS = "YC";
			}
		}
		for(int n = 1; n <= mLJTempFeeSet.size(); n++)
		{
			LJTempFeeSchema nLJTempFeeSchema = new LJTempFeeSchema();
			nLJTempFeeSchema = mLJTempFeeSet.get(n);
			SaveMoney = SaveMoney + nLJTempFeeSchema.getPayMoney();
		}

		logger.debug("SumMoney=========" + SaveMoney);

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

	
	/**
	 * 校验该险种是否退费
	 * 
	 * @param tLJTempFeeSchema
	 * @return
	 */
//	private boolean veriryLJSPAY(LJTempFeeSchema tLJTempFeeSchema)
//	{
//		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
//
//		// 确认应收是否处于银行在途状态
//		tLJSPaySchema.setGetNoticeNo(tLJTempFeeSchema.getTempFeeNo());
//		tLJSPaySchema.setBankOnTheWayFlag("1");
//
//
//		// 判断暂交费退费实付表是否有数据和暂交费表数据是否已核销
//		if((tLJSPaySchema.getDB().query()).size() != 0)
//		{
//			// @@错误处理
//			CError.buildErr(this, "保单" + tLJTempFeeSchema.getTempFeeNo() + "下有收费记录处于银行在途状态,请核实!");
//			return false;
//		}
//
//		return true;
//	}
	public static void main(String[] args)
	{
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSchema.setTempFeeNo("97000000142322");
		tLJTempFeeSchema.setTempFeeType("9");
		tLJTempFeeSet.add(tLJTempFeeSchema);

		LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		tLJAGetTempFeeSchema.setGetReasonCode("2");
		tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("OtherNoFlag", "9");
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.Operator = "001";
		tGI.ManageCom = "86";

		logger.debug("tLJTempFeeSet:" + tLJTempFeeSet.encode());

		VData tVData = new VData();
		tVData.add(tLJTempFeeSet);
		tVData.add(tLJAGetTempFeeSet);
		tVData.add(tTransferData);
		tVData.add(tGI);
		TempFeeWithdrawUI tTempFeeWithdrawUI = new TempFeeWithdrawUI();
		tTempFeeWithdrawUI.submitData(tVData, "WITHDRAW");

	}
}
