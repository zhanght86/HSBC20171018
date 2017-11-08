package com.sinosoft.lis.finfee;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContHangUpStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GetPayType;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.ExeSQL;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class NewPolFeeWithdrawBL {
private static Logger logger = Logger.getLogger(NewPolFeeWithdrawBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 数据操作字符串 */
	private GlobalInput mGI = new GlobalInput();

	private String tLimit = "";

	private String serNo = "";// 流水号

	private String getNo = "";// 给付收据号码

	private String prtSeqNo = "";// 印刷流水号

	private boolean specWithDrawFlag = false; // 特殊的退费标记。该标记为真时，不考虑outpayflag标志

	private TransferData tTransferData = new TransferData();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private LJAGetSchema mLJAGetSchema = new LJAGetSchema();// 实付总表

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();// 打印管理表

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();// 个人保单表

	private LCPolSet mLCPolSet = new LCPolSet();

	private LCPolSchema mNewLCPolSchema = new LCPolSchema();// 个人保单表

	private LCPolSet mNewLCPolSet = new LCPolSet();

	private LCPolSet mSaveLCPolSet = new LCPolSet();

	private LJAGetOtherSchema mLJAGetOtherShema = new LJAGetOtherSchema();// 其它其他退费实付表

	private LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet();// 其它其他退费实付表
	
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	public NewPolFeeWithdrawBL() {
	}

	public static void main(String[] args) {
		NewPolFeeWithdrawBL NewPolFeeWithdrawBL1 = new NewPolFeeWithdrawBL();
		VData tVData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "8600";
		tGI.Operator = "hzm";
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		// tLCPolSchema.setProposalNo("86110020030110000065");
		tLCPolSchema.setPolNo("86110020030210000128");
		tLCPolSet.add(tLCPolSchema);
		tVData.add(tLCPolSet);
		tVData.add(tGI);
		NewPolFeeWithdrawBL1.submitData(tVData);
	}

	// 传输数据的公共方法
	public boolean submitData(VData tVData) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tVData))
			return false;
		logger.debug("After getInputData");

		if (!checkData())
			return false;
		logger.debug("After checkData！");
		
		try
		{
			//增加并发控制,不允许和催收并发  LR0004 续期退还余额
			String LockNo = "";
			LockNo= mLCPolSet.get(1).getContNo();
			if (!mPubLock.lock(LockNo, "LR0004", mGI.Operator)) 
			{
				CError tError = new CError(mPubLock.mErrors.getLastError());
				this.mErrors.addOneError(tError);
				return false;
			}
			
	
			// 进行业务处理
			if (!dealData())
				return false;
			logger.debug("After dealData！");
	
			// 准备往后台的数据
			if (!prepareOutputData())
				return false;
			logger.debug("After prepareOutputData");
	
			logger.debug("Start NewPolFeeWithdrawBLS.submitData...");
			NewPolFeeWithdrawBLS tNewPolFeeWithdrawBLS = new NewPolFeeWithdrawBLS();
			tNewPolFeeWithdrawBLS.submitData(mInputData);
			if (tNewPolFeeWithdrawBLS.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tNewPolFeeWithdrawBLS.mErrors);
			}
			logger.debug("End NewPolFeeWithdrawBLS.submitData...");
	
			mPubLock.unLock();// 解锁
			mInputData = null;
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} 
		finally 
		{
			mPubLock.unLock();// 解锁
		}
	}

	/**
	 * 执行溢交保费退费和暂交费退费的共用模块
	 * 
	 * @param tVData
	 * @return
	 */
	public boolean submitDataAll(VData tVData) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tVData))
			return false;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealDataForMainRisk())
			return false;
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("After prepareOutputData");

		// 准备暂交费退费数据
		VData tempfeeData = prepareTempfeeData();
		if (tempfeeData != null)// 如果有数据
		{
			mInputData.add(tempfeeData);
		}

		logger.debug("Start NewPolFeeWithdrawBL BL Submit...");

		NewPolFeeWithdrawBLS tNewPolFeeWithdrawBLS = new NewPolFeeWithdrawBLS();
		tNewPolFeeWithdrawBLS.submitData(mInputData);

		logger.debug("End NewPolFeeWithdrawBL BL Submit...");

		// 如果有需要处理的错误，则返回
		if (tNewPolFeeWithdrawBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tNewPolFeeWithdrawBLS.mErrors);
		}

		mInputData = null;
		return true;
	}

	/**
	 * 执行溢交保费退费和暂交费退费的共用模块 修改自submitDataAll,只是准备数据，并且返回数据
	 * 
	 * @param tVData
	 *            VData
	 * @return MMap
	 */
	public MMap submitDataAllNew(VData tVData) {

		boolean tResult = dealSubmitDataAll(tVData);
		if (tResult == false)
			return null;

		MMap tmpMap = new MMap();
		// tmpMap.put( (LCPolSet) mInputData.getObjectByObjectName("LCPolSet",
		// 0),
		// "UPDATE"); //个人保单表
		tmpMap.put(
				(LJAGetSet) mInputData.getObjectByObjectName("LJAGetSet", 0),
				"INSERT"); // 实付总表
		tmpMap.put((LJAGetOtherSet) mInputData.getObjectByObjectName(
				"LJAGetOtherSet", 0), "INSERT"); // 实付子表
		tmpMap.put((LOPRTManagerSet) mInputData.getObjectByObjectName(
				"LOPRTManagerSet", 0), "INSERT");
		/** 打印管理表 */
		// 暂交费退费
		VData tempfeeVData = new VData();
		tempfeeVData = (VData) mInputData.getObjectByObjectName("VData", 0);
		if (tempfeeVData != null) {
			tmpMap.add((MMap) tempfeeVData.getObjectByObjectName("MMap", 0));

		}
		return tmpMap;
	}

	private boolean dealSubmitDataAll(VData tVData) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tVData))
			return false;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealDataForMainRisk())
			return false;
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		logger.debug("After prepareOutputData");

		// 准备暂交费退费数据
		VData tempfeeData = prepareTempfeeData();
		if (tempfeeData != null)// 如果有数据
		{
			mInputData.add(tempfeeData);
		}
		return true;
	}

	// 退余额必须撤销应收
	private boolean checkData() {
		ExeSQL xExeSQL = new ExeSQL();
		String mContNo="";
		if (mLCPolSet.size() > 0) {
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setProposalNo(mLCPolSet.get(1).getPolNo());
			tLCPolDB.setAppFlag("1");// 1 - 承保
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet.size() > 0) {
				String tContNo = tLCPolSet.get(1).getContNo();
				mContNo = tContNo;
				LJSPaySet tLJSPaySet = new LJSPaySet();
				LJSPayDB tLJSPayDB = new LJSPayDB();
				tLJSPayDB.setOtherNo(tContNo);// 续期催收保存的是个单contno
				tLJSPaySet = tLJSPayDB.query();
				if (tLJSPaySet.size() > 0) {
					CError tError = new CError();
					tError.moduleName = "NewPolFeeWithdrawBL";
					tError.functionName = "checkData";
					tError.errorMessage = "请先撤销该保单对应印刷号下的应收记录!";
					this.mErrors.addOneError(tError);
					return false;
				}
			} else {
				CError tError = new CError();
				tError.moduleName = "NewPolFeeWithdrawBL";
				tError.functionName = "checkData";
				tError.errorMessage = "没有得到足够的数据，请您确认!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "NewPolFeeWithdrawBL";
			tError.functionName = "checkData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		//增加保全挂起则不允许退费校验
		String BQCHECK_sql="";
		BQCHECK_sql = " select * from lcconthangupstate where contno='?mContNo?' and  hanguptype in ('3','2') and  rnflag='1' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(BQCHECK_sql);
		sqlbv.put("mContNo", mContNo);
		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB.executeQuery(sqlbv);
		if(tLCContHangUpStateSet.size()>0)
		{
			String sHangUpType2 = tLCContHangUpStateSet.get(1).getHangUpType();
			String sql = " select * from ldcode where code = '?sHangUpType2?'"
					+ " and codetype = 'conthanguptype'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("sHangUpType2", sHangUpType2);
			LDCodeDB tLDCodeDB = new LDCodeDB();
			LDCodeSet tLDCodeSet = new LDCodeSet();
			tLDCodeSet = tLDCodeDB.executeQuery(sqlbv1);
			if (tLDCodeDB.mErrors.needDealError() || tLDCodeSet == null
					|| tLDCodeSet.size() < 1) {
				CError.buildErr(this, "保单挂起状态描述查询失败!");
				return false;
			}
			String XXName2 = tLDCodeSet.get(1).getCodeAlias(); // 模块名

			// 返回错误信息，告知该保单的XX状态被什么模块挂起，并提示挂起号码
			CError.buildErr(this, "保单" + mContNo + "被"
					+ XXName2 + "挂起，相关号码："
					+ tLCContHangUpStateSet.get(1).getHangUpNo());
			return false;
		}
		
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		// 产生流水号
		tLimit = PubFun.getNoLimit(mGI.ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		LCPolSet tLCPolSet = new LCPolSet();
		for (int index = 1; index <= mLCPolSet.size(); index++) {
			double mLeavingMoney = 0.00;
			mLCPolSchema = mLCPolSet.get(index);
			mLeavingMoney = mLCPolSchema.getLeavingMoney();// 页面传入退费金额
			if (mLeavingMoney <= 0) {
				continue;
			}

			// 1-查询投保单号对应的已签单的保单，如果没有则跳过,否则保存查询到的纪录
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setProposalNo(mLCPolSchema.getPolNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() == 0)
				continue;
			mNewLCPolSchema = new LCPolSchema();
			mNewLCPolSchema = tLCPolSet.get(1);
			if (mNewLCPolSchema.getAppFlag() == null
					|| !mNewLCPolSchema.getAppFlag().equals("1")) // 如果没有签单返回
				continue;

			mSaveLCPolSet.add(mNewLCPolSchema);// 保存查出的投保单，后面暂交退费用--只需要主险

			// if (mNewLCPolSchema.getOutPayFlag() == null) {
			// mNewLCPolSchema.setOutPayFlag("1");
			// }
			// if (mNewLCPolSchema.getPayIntv() == 0)// 如果交费方式是趸交，退费
			// {
			// mNewLCPolSchema.setOutPayFlag("1");
			// }

			if (specWithDrawFlag)// 如果特殊退费标记为真，那么默认退费
			{
				logger.debug("mNewLCPolSchema.getLeavingMoney():"
						+ mNewLCPolSchema.getLeavingMoney());
				logger.debug("mLeavingMoney:" + mLeavingMoney);
				if (mNewLCPolSchema.getLeavingMoney() <= 0
						|| mNewLCPolSchema.getLeavingMoney() < mLeavingMoney)
					continue;
			} else {
				if (mNewLCPolSchema.getLeavingMoney() <= 0)// update by ck
					// if(!(mNewLCPolSchema.getLeavingMoney()>0)||!mNewLCPolSchema.getOutPayFlag().equals("1"))//如果没有余额或者溢交处理方式不是退费,返回
					continue;
			}

			// 2-准备实付表纪录,产生实付号
			tLimit = PubFun.getNoLimit(mNewLCPolSchema.getManageCom());// 主附险共用一个给付通知书号
			getNo = PubFun1.CreateMaxNo("GETNO", tLimit);
			String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 产生给付通知书号

			// 3-准备打印数据,生成印刷流水号
			tLimit = PubFun.getNoLimit(mNewLCPolSchema.getManageCom());
			prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(prtSeqNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GET);// 04，实付收据号
			mLOPRTManagerSchema.setOtherNo(getNo);// 实付号码
			mLOPRTManagerSchema.setMakeDate(CurrentDate);
			mLOPRTManagerSchema.setMakeTime(CurrentTime);
			mLOPRTManagerSchema.setManageCom(mNewLCPolSchema.getManageCom());
			mLOPRTManagerSchema.setAgentCode(mNewLCPolSchema.getAgentCode());
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_REFUND);
			mLOPRTManagerSchema.setReqCom(mGI.ManageCom);
			mLOPRTManagerSchema.setReqOperator(mGI.Operator);
			mLOPRTManagerSchema.setPrtType("0");
			mLOPRTManagerSchema.setStateFlag("0");
			VData tempVData = new VData();
			tempVData.add(mLOPRTManagerSchema);
			tempVData.add(mGI);
			PrintManagerBL tPrintManagerBL = new PrintManagerBL();
			if (tPrintManagerBL.submitData(tempVData, "REQ"))// 打印数据处理
			{
				tempVData = tPrintManagerBL.getResult();
				mLOPRTManagerSchema = (LOPRTManagerSchema) tempVData
						.getObjectByObjectName("LOPRTManagerSchema", 0);
				mLOPRTManagerSet.add(mLOPRTManagerSchema);
			} else {
				continue;
			}

			// 4-查询暂收费银行信息
			String sql = "select * from ljtempfeeclass where tempfeeno in (select tempfeeno from ljtempfee where (otherno='?otherno1?' or otherno='?otherno?' ) and confflag='1')";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("otherno1", mNewLCPolSchema.getPolNo());
			sqlbv2.put("otherno2", mNewLCPolSchema.getPrtNo());
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
					.executeQuery(sqlbv2);
			String payMode = "";
			String BankCode = "";
			String BankAccNo = "";
			String AccName = "";

//			if (tLJTempFeeClassSet != null) {
//				if (tLJTempFeeClassSet.size() > 0) {
//					LJTempFeeClassSchema tLJTemp = tLJTempFeeClassSet.get(1);
//					payMode = tLJTemp.getPayMode();
//					if (payMode.equals("4")) {
//						if (tLJTemp.getBankAccNo() == null
//								|| tLJTemp.getBankCode() == null) {// 如果银行编号，账号为空那么认为是现金付费
//							payMode = "1";
//						} else {
//							BankCode = tLJTemp.getBankCode();
//							BankAccNo = tLJTemp.getBankAccNo();
//							AccName = tLJTemp.getAccName();
//						}
//					}
//				}
//			}
			//zy 2009-06-23 调整付费方式
	        for (int i=1;i<=tLJTempFeeClassSet.size();i++)
	        {
	        	if("4".equals(tLJTempFeeClassSet.get(i).getPayMode()))
	        	{
	        		payMode="4";
	        		BankCode =tLJTempFeeClassSet.get(i).getBankCode();
	        		BankAccNo =tLJTempFeeClassSet.get(i).getBankAccNo();
	        		AccName = tLJTempFeeClassSet.get(i).getAccName();
	        		break;
	        	}
	            else if(tLJTempFeeClassSet.get(i).getPayMode().equals("2")||tLJTempFeeClassSet.get(i).getPayMode().equals("3"))
	            {
	              payMode=tLJTempFeeClassSet.get(i).getPayMode();
	              BankCode=tLJTempFeeClassSet.get(i).getBankCode();
	              BankAccNo=tLJTempFeeClassSet.get(i).getChequeNo();
	              break;
	            }
	            else
	            {
	                payMode="1";
	            }
	        }

			// 5-实付总表
			mLJAGetSchema = new LJAGetSchema();
			mLJAGetSchema.setActuGetNo(getNo);
			mLJAGetSchema.setPayMode(payMode);
			mLJAGetSchema.setShouldDate(CurrentDate);
			mLJAGetSchema.setStartGetDate(CurrentDate);
			mLJAGetSchema.setBankAccNo(BankAccNo);
			mLJAGetSchema.setBankCode(BankCode);
			mLJAGetSchema.setAccName(AccName);
			mLJAGetSchema.setOtherNo(mNewLCPolSchema.getContNo());
			mLJAGetSchema.setOtherNoType("6");// 其它类型退费(溢交保费退费)
			mLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
			mLJAGetSchema.setAppntNo(mNewLCPolSchema.getAppntNo());
			mLJAGetSchema.setSumGetMoney(mLeavingMoney);
			mLJAGetSchema.setSaleChnl(mNewLCPolSchema.getSaleChnl());
			mLJAGetSchema.setSerialNo(serNo);
			mLJAGetSchema.setOperator(mGI.Operator);
			mLJAGetSchema.setManageCom(mNewLCPolSchema.getManageCom());
			mLJAGetSchema.setAgentCode(mNewLCPolSchema.getAgentCode());
			mLJAGetSchema.setAgentCom(mNewLCPolSchema.getAgentCom());
			mLJAGetSchema.setAgentGroup(mNewLCPolSchema.getAgentGroup());
			mLJAGetSchema.setCurrency(mNewLCPolSchema.getCurrency());
			mLJAGetSchema.setShouldDate(CurrentDate);
			mLJAGetSchema.setStartGetDate(CurrentDate);
			mLJAGetSchema.setMakeDate(CurrentDate);
			mLJAGetSchema.setMakeTime(CurrentTime);
			mLJAGetSchema.setModifyDate(CurrentDate);
			mLJAGetSchema.setModifyTime(CurrentTime);
			mLJAGetSchema.setPolicyCom(mNewLCPolSchema.getManageCom());
			mLJAGetSet.add(mLJAGetSchema);

			// 6-实付子表
			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet mLMRiskAppSet = new LMRiskAppSet();
			mLMRiskAppDB.setRiskCode(mNewLCPolSchema.getRiskCode());
			mLMRiskAppSet = mLMRiskAppDB.query();

			mLJAGetOtherShema = new LJAGetOtherSchema();
			mLJAGetOtherShema.setActuGetNo(getNo);
			mLJAGetOtherShema.setOtherNo(mNewLCPolSchema.getContNo());
			mLJAGetOtherShema.setOtherNoType("0");// 其它类型退费(溢交保费退费)
			mLJAGetOtherShema.setGetNoticeNo(tGetNoticeNo);
			mLJAGetOtherShema.setPayMode(payMode);
			mLJAGetOtherShema.setGetMoney(mLeavingMoney);
			mLJAGetOtherShema.setGetDate(CurrentDate);
			mLJAGetOtherShema.setFeeOperationType("YJTF");// 溢交退费
			if (mNewLCPolSchema.getContType().equals("2")
					&& mLMRiskAppSet != null
					&& "1".equals(mLMRiskAppSet.get(1).getHealthType())) {// 为了财务凭证记账方便,如果lmriskapp表中的HealthType字段为1时，feefinatype赋值为CM
				mLJAGetOtherShema.setFeeFinaType("CM");// 溢交退费
			} else {
				mLJAGetOtherShema.setFeeFinaType("YJTF");// 溢交退费
			}
			mLJAGetOtherShema.setManageCom(mNewLCPolSchema.getManageCom());
			mLJAGetOtherShema.setAgentCom(mNewLCPolSchema.getAgentCom());
			mLJAGetOtherShema.setAgentType(mNewLCPolSchema.getAgentType());
			mLJAGetOtherShema.setAPPntName(mNewLCPolSchema.getAppntName());
			mLJAGetOtherShema.setAgentGroup(mNewLCPolSchema.getAgentGroup());
			mLJAGetOtherShema.setAgentCode(mNewLCPolSchema.getAgentCode());
			mLJAGetOtherShema.setSerialNo(serNo);
			mLJAGetOtherShema.setCurrency(mNewLCPolSchema.getCurrency());
			mLJAGetOtherShema.setOperator(mGI.Operator);
			mLJAGetOtherShema.setMakeTime(CurrentTime);
			mLJAGetOtherShema.setMakeDate(CurrentDate);
			mLJAGetOtherShema.setModifyDate(CurrentDate);
			mLJAGetOtherShema.setModifyTime(CurrentTime);
			mLJAGetOtherSet.add(mLJAGetOtherShema);

			// 7-更新个人保单数据
			mNewLCPolSchema.setLeavingMoney(mNewLCPolSchema.getLeavingMoney()
					- mLeavingMoney);
			mNewLCPolSchema.setModifyDate(CurrentDate);
			mNewLCPolSchema.setModifyTime(CurrentTime);
			mNewLCPolSet.add(mNewLCPolSchema);
		}
		return true;
	}

	/**
	 * 根据集合中主险投保单号查出同印刷号的主附险,使用同一个给付通知书号
	 * 
	 * @return
	 */
	private boolean dealDataForMainRisk() {
		// 产生流水号
		tLimit = PubFun.getNoLimit(mGI.ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		LCPolSet tLCPolSet = new LCPolSet();
		for (int index = 1; index <= mLCPolSet.size(); index++) {
			mNewLCPolSchema = new LCPolSchema();
			mLCPolSchema = mLCPolSet.get(index);
			// 1-查询投保单号对应的已签单的保单，如果没有则跳过,否则保存查询到的纪录
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setProposalNo(mLCPolSchema.getPolNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null)
				continue;
			if (tLCPolSet.size() == 0)
				continue;
			mNewLCPolSchema = tLCPolSet.get(1);
			if (mNewLCPolSchema.getAppFlag() == null
					|| !mNewLCPolSchema.getAppFlag().equals("1")) // 如果没有签单返回
				continue;

			mSaveLCPolSet.add(mNewLCPolSchema);// 保存查出的投保单，后面暂交退费用

			tLCPolDB = new LCPolDB();
			tLCPolDB.setPrtNo(mNewLCPolSchema.getPrtNo());
			tLCPolSet = tLCPolDB.query();
			tLimit = PubFun.getNoLimit(mNewLCPolSchema.getManageCom());// 主附险共用一个给付通知书号
			String tGetNoticeNo = ""; // 通知书号
			String payMode = ""; // 交费方式
			String BankCode = "";// 银行编码
			String BankAccNo = "";// 银行账号
			String AccName = ""; // 户名

			for (int n = 1; n <= tLCPolSet.size(); n++)// 处理主附险
			{
				mNewLCPolSchema = tLCPolSet.get(n);
				// if (mNewLCPolSchema.getOutPayFlag() == null) {
				// mNewLCPolSchema.setOutPayFlag("1");
				// }
				// if (mNewLCPolSchema.getPayIntv() == 0)// 如果交费方式是趸交，退费
				// {
				// mNewLCPolSchema.setOutPayFlag("1");
				// }
				if (specWithDrawFlag)// 如果特殊退费标记为真，那么默认退费
				{
					// mNewLCPolSchema.setOutPayFlag("1");
					if (!(mNewLCPolSchema.getLeavingMoney() > 0))
						continue;
				} else {
					// if (!(mNewLCPolSchema.getLeavingMoney() > 0)
					// || !mNewLCPolSchema.getOutPayFlag().equals("1"))//
					// 如果没有余额或者溢交处理方式不是退费,返回
					// continue;
				}
				// 2-准备实付表纪录,产生实付号码
				getNo = PubFun1.CreateMaxNo("GETNO", tLimit);

				// 如果通知书号已经产生，那么就不用产生打印数据了
				if (tGetNoticeNo == null || tGetNoticeNo.equals("")) {
					tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 产生即付通知书号

					// 3-准备打印数据,生成印刷流水号
					mLOPRTManagerSchema = new LOPRTManagerSchema();
					tLimit = PubFun.getNoLimit(mNewLCPolSchema.getManageCom());
					prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
					mLOPRTManagerSchema.setPrtSeq(prtSeqNo);
					mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GET);//
					mLOPRTManagerSchema.setOtherNo(tGetNoticeNo);// 给付通知书号码
					mLOPRTManagerSchema.setMakeDate(CurrentDate);
					mLOPRTManagerSchema.setMakeTime(CurrentTime);
					mLOPRTManagerSchema.setManageCom(mNewLCPolSchema
							.getManageCom());
					mLOPRTManagerSchema.setAgentCode(mNewLCPolSchema
							.getAgentCode());
					mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_REFUND);
					mLOPRTManagerSchema.setReqCom(mGI.ManageCom);
					mLOPRTManagerSchema.setReqOperator(mGI.Operator);
					mLOPRTManagerSchema.setPrtType("0");
					mLOPRTManagerSchema.setStateFlag("0");
					mLOPRTManagerSchema.setStandbyFlag1(mNewLCPolSchema
							.getMainPolNo());
					PrintManagerBL tPrintManagerBL = new PrintManagerBL();
					VData tempVData = new VData();
					tempVData.add(mLOPRTManagerSchema);
					tempVData.add(mGI);
					if (tPrintManagerBL.submitData(tempVData, "REQ"))// 打印数据处理
					{
						tempVData = tPrintManagerBL.getResult();
						mLOPRTManagerSchema = (LOPRTManagerSchema) tempVData
								.getObjectByObjectName("LOPRTManagerSchema", 0);
						mLOPRTManagerSet.add(mLOPRTManagerSchema);
					} else {
						continue;
					}

					// 如果通知书号不为空，找出退费方式（优先级依次为支票，银行，现金）
					GetPayType tGetPayType = new GetPayType();
					if (tGetPayType.getPayTypeForLCPol(mLCPolSchema.getPrtNo()) == false) {
						this.mErrors.copyAllErrors(tGetPayType.mErrors);
						return false;
					} else {
						payMode = tGetPayType.getPayMode(); // 交费方式
						BankCode = tGetPayType.getBankCode();// 银行编码
						BankAccNo = tGetPayType.getBankAccNo();// 银行账号
						AccName = tGetPayType.getAccName(); // 户名
					}

				}

				// 实付总表
				mLJAGetSchema = new LJAGetSchema();
				mLJAGetSchema.setActuGetNo(getNo);
				mLJAGetSchema.setPayMode(payMode);
				mLJAGetSchema.setShouldDate(CurrentDate);
				mLJAGetSchema.setStartGetDate(CurrentDate);

				mLJAGetSchema.setBankAccNo(BankAccNo);
				mLJAGetSchema.setBankCode(BankCode);
				mLJAGetSchema.setAccName(AccName);
				mLJAGetSchema.setOtherNo(mNewLCPolSchema.getPolNo());
				mLJAGetSchema.setOtherNoType("6");// 其它类型退费(溢交保费退费)
				mLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
				mLJAGetSchema.setAppntNo(mNewLCPolSchema.getAppntNo());
				mLJAGetSchema.setSumGetMoney(mNewLCPolSchema.getLeavingMoney());
				mLJAGetSchema.setSaleChnl(mNewLCPolSchema.getSaleChnl());
				mLJAGetSchema.setSerialNo(serNo);
				mLJAGetSchema.setOperator(mGI.Operator);
				mLJAGetSchema.setManageCom(mNewLCPolSchema.getManageCom());
				mLJAGetSchema.setAgentCode(mNewLCPolSchema.getAgentCode());
				mLJAGetSchema.setAgentCom(mNewLCPolSchema.getAgentCom());
				mLJAGetSchema.setAgentGroup(mNewLCPolSchema.getAgentGroup());
				mLJAGetSchema.setShouldDate(CurrentDate);
				mLJAGetSchema.setStartGetDate(CurrentDate);
				mLJAGetSchema.setMakeDate(CurrentDate);
				mLJAGetSchema.setMakeTime(CurrentTime);
				mLJAGetSchema.setModifyDate(CurrentDate);
				mLJAGetSchema.setModifyTime(CurrentTime);
				mLJAGetSet.add(mLJAGetSchema);
				// 实付子表
				mLJAGetOtherShema = new LJAGetOtherSchema();
				mLJAGetOtherShema.setActuGetNo(getNo);
				mLJAGetOtherShema.setOtherNo(mNewLCPolSchema.getPolNo());
				mLJAGetOtherShema.setOtherNoType("6");// 其它类型退费(溢交保费退费)
				mLJAGetOtherShema.setPayMode(payMode);
				mLJAGetOtherShema
						.setGetMoney(mNewLCPolSchema.getLeavingMoney());
				mLJAGetOtherShema.setGetDate(CurrentDate);
				mLJAGetOtherShema.setFeeOperationType("YJTF");// 溢交退费
				mLJAGetOtherShema.setFeeFinaType("YJTF");// 溢交退费
				mLJAGetOtherShema.setManageCom(mNewLCPolSchema.getManageCom());
				mLJAGetOtherShema.setAgentCom(mNewLCPolSchema.getAgentCom());
				mLJAGetOtherShema.setAgentType(mNewLCPolSchema.getAgentType());
				mLJAGetOtherShema.setAPPntName(mNewLCPolSchema.getAppntName());
				mLJAGetOtherShema
						.setAgentGroup(mNewLCPolSchema.getAgentGroup());
				mLJAGetOtherShema.setAgentCode(mNewLCPolSchema.getAgentCode());
				mLJAGetOtherShema.setSerialNo(serNo);
				mLJAGetOtherShema.setOperator(mGI.Operator);
				;
				mLJAGetOtherShema.setMakeTime(CurrentTime);
				mLJAGetOtherShema.setMakeDate(CurrentDate);
				mLJAGetOtherShema.setModifyDate(CurrentDate);
				mLJAGetOtherShema.setModifyTime(CurrentTime);
				mLJAGetOtherSet.add(mLJAGetOtherShema);

				// 4-更新个人保单数据
				mNewLCPolSchema.setLeavingMoney(0);
				mNewLCPolSchema.setModifyDate(CurrentDate);
				mNewLCPolSchema.setModifyTime(CurrentTime);
				mNewLCPolSet.add(mNewLCPolSchema);
			}
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData tVData) {
		mGI = (GlobalInput) tVData.getObjectByObjectName("GlobalInput", 0);
		mLCPolSet = (LCPolSet) tVData.getObjectByObjectName("LCPolSet", 0);
		tTransferData = (TransferData) tVData.getObjectByObjectName(
				"TransferData", 0);

		if (mLCPolSet == null || mGI == null) {
			CError tError = new CError();
			tError.moduleName = "NewPolFeeWithdrawBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tTransferData != null) {
			String inStr = (String) tTransferData
					.getValueByName("SpecWithDraw");
			if (inStr != null) {
				if (inStr.equals("1")) {
					specWithDrawFlag = true;
				}
			}
		}

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			mInputData.add(mNewLCPolSet);
			mInputData.add(mLOPRTManagerSet);
			TaxCalculator.calBySchemaSet(mLJAGetOtherSet);
			mInputData.add(mLJAGetOtherSet);
			//营改增字表可以直接计算主表不能直接计算根据以上逻辑子表和主表的退款金额一样，税额和净值直接使用单号赋值
			//根据币种和实付号
			for (int i = 0; i < mLJAGetOtherSet.size(); i++) {
				LJAGetOtherSchema ljaOtherObj = mLJAGetOtherSet.get(i);
				for (int j = 0; j < mLJAGetSet.size(); j++) {
					LJAGetSchema ljaObj = mLJAGetSet.get(j);
					if(ljaOtherObj.getActuGetNo().equals(ljaObj.getActuGetNo()) || ljaOtherObj.getCurrency().equals(ljaObj.getCurrency())){
						ljaObj.setTax(ljaOtherObj.getTax());
						ljaObj.setTaxAmount(ljaOtherObj.getTaxAmount());
						ljaObj.setNetAmount(ljaOtherObj.getNetAmount());
					}
					
				}
				
			}
			mInputData.add(mLJAGetSet);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "NewPolFeeWithdrawBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备暂交费退费的数据(针对主险已签单但是附加险撤单的情况)
	 * 
	 * @return
	 */
	private VData prepareTempfeeData() {
		LJAGetTempFeeSet preLJAGetTempFeeSet = new LJAGetTempFeeSet();
		LJTempFeeSet preLJTempFeeSet = new LJTempFeeSet();
		LJAGetSet preLJAGetSet = new LJAGetSet();
		LJTempFeeClassSet preLJTempFeeClassSet = new LJTempFeeClassSet();

		VData ruturnData = new VData();
		String strSql = "";
		LCPolSchema tLCPolSchema = new LCPolSchema();
		for (int index = 1; index <= mSaveLCPolSet.size(); index++) {
			String payMode = ""; // 交费方式
			String BankCode = "";// 银行编码
			String BankAccNo = "";// 银行账号
			String AccName = ""; // 户名
			boolean tAddPrtFlag = false;// 是否需要重新添加打印数据
			tLCPolSchema = mSaveLCPolSet.get(index);
			if (!tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())) {
				continue;// 如果不是主险，跳到下一循环
			}

			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
			LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
			// 查出主险对应的暂加费纪录（可能多条：譬如金额不足，交两次费）
			strSql = "select * from ljtempfee where EnterAccDate is not null  ";
			strSql = strSql + " and otherno in('?otherno?')";
			strSql = strSql + " and riskcode='?riskcode?'";
			ArrayList<String> strArr=new ArrayList<String>();
			strArr.add(tLCPolSchema.getPrtNo());
			strArr.add(tLCPolSchema.getPolNo());
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("otherno", strArr);
			sqlbv.put("riskcode", tLCPolSchema.getRiskCode());
			tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
			if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
				continue;// 如果没有暂交费，跳到下一循环
			}
			for (int n = 1; n <= tLJTempFeeSet.size(); n++) {
				// 找出在暂交费中对应附加险投保单撤单的纪录(已经到帐,如果是扣款送途中，不容许撤单)
				strSql = "select * from ljtempfee where EnterAccDate is not null and confflag='0' ";
				strSql = strSql
						+ " and riskcode not in( select riskcode from lcpol where prtno='?prtno?')";
				strSql = strSql + " and tempfeeno='?tempfeeno?'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("prtno", tLCPolSchema.getPrtNo());
				sqlbv1.put("tempfeeno", tLJTempFeeSet.get(n).getTempFeeNo());
				LJTempFeeSet tempLJTempFeeSet = tLJTempFeeDB
						.executeQuery(sqlbv1);
				if (tempLJTempFeeSet == null || tempLJTempFeeSet.size() == 0) {
					continue;// 如果没有暂交费，跳到下一循环
				}
				outLJTempFeeSet.add(tempLJTempFeeSet);
			}
			if (outLJTempFeeSet.size() == 0) // 如果没有找到这样的纪录
			{
				continue;
			}

			// 如果通知书号不为空，找出退费方式（优先级依次为支票，银行，现金）
			GetPayType tGetPayType = new GetPayType();
			if (tGetPayType.getPayTypeForLCPol(tLCPolSchema.getPrtNo()) == false) {
				this.mErrors.copyAllErrors(tGetPayType.mErrors);
				return ruturnData;
			} else {
				payMode = tGetPayType.getPayMode(); // 交费方式
				BankCode = tGetPayType.getBankCode();// 银行编码
				BankAccNo = tGetPayType.getBankAccNo();// 银行账号
				AccName = tGetPayType.getAccName(); // 户名
			}

			TransferData sTansferData = new TransferData();
			sTansferData.setNameAndValue("PayMode", payMode);
			if (payMode.equals("1")) {
				sTansferData.setNameAndValue("BankFlag", "0");
			} else {
				sTansferData.setNameAndValue("BankCode", BankCode);
				sTansferData.setNameAndValue("AccNo", BankAccNo);
				sTansferData.setNameAndValue("AccName", AccName);
				sTansferData.setNameAndValue("BankFlag", "1");
			}

			String tGetNoticeNo = "";
			// 从溢交保费产生的实付表中查找是否有相同的投保单号，共用一个给付通知书号
			for (int i = 1; i <= mLJAGetSet.size(); i++) {
				if (mLJAGetSet.get(i).getOtherNo().equals(
						tLCPolSchema.getPolNo())) {
					tGetNoticeNo = mLJAGetSet.get(i).getGetNoticeNo();
					break;
				}
			}
			// 和溢交保费相同的给付通知书号，如果没有溢交保费，即该号是空的,需要判断
			if (tGetNoticeNo == null || tGetNoticeNo.equals("")) {
				tAddPrtFlag = true;
				tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
				tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 产生即付通知书号

				// 3-准备打印数据,生成印刷流水号
				mLOPRTManagerSchema = new LOPRTManagerSchema();
				tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
				prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
				mLOPRTManagerSchema.setPrtSeq(prtSeqNo);
				mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GET);//
				mLOPRTManagerSchema.setOtherNo(tGetNoticeNo);// 给付通知书号码
				mLOPRTManagerSchema.setMakeDate(CurrentDate);
				mLOPRTManagerSchema.setMakeTime(CurrentTime);
				mLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
				mLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
				mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_REFUND);
				mLOPRTManagerSchema.setReqCom(mGI.ManageCom);
				mLOPRTManagerSchema.setReqOperator(mGI.Operator);
				mLOPRTManagerSchema.setPrtType("0");
				mLOPRTManagerSchema.setStateFlag("0");
				mLOPRTManagerSchema
						.setStandbyFlag1(tLCPolSchema.getMainPolNo());
				PrintManagerBL tPrintManagerBL = new PrintManagerBL();
				VData tempVData = new VData();
				tempVData.add(mLOPRTManagerSchema);
				tempVData.add(mGI);
				if (tPrintManagerBL.submitData(tempVData, "REQ"))// 打印数据处理
				{
					tempVData = tPrintManagerBL.getResult();
					mLOPRTManagerSchema = (LOPRTManagerSchema) tempVData
							.getObjectByObjectName("LOPRTManagerSchema", 0);
				} else {
					continue;
				}

			}
			sTansferData.setNameAndValue("GetNoticeNo", tGetNoticeNo);
			// 生成暂交费退费实付表
			LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
			for (int n = 1; n <= outLJTempFeeSet.size(); n++) {
				LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
				tLJAGetTempFeeSchema.setGetReasonCode("99");
				tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
			}

			sTansferData.setNameAndValue("NotBLS", "1");// 暂交费退费程序需要
			sTansferData.setNameAndValue("NewPolSignFlag", "1");// 签单后关联退费标志
			VData tVData = new VData();
			tVData.add(outLJTempFeeSet);
			tVData.add(tLJAGetTempFeeSet);
			tVData.add(sTansferData);
			tVData.add(mGI);
			// 调用暂交费退费的接口
			TempFeeWithdrawBL tTempFeeWithdrawBL = new TempFeeWithdrawBL();
			tTempFeeWithdrawBL.submitData(tVData, "INSERT");
			tVData = tTempFeeWithdrawBL.getResult();
			if (tVData != null && tVData.size() > 0) {
				preLJAGetTempFeeSet.add((LJAGetTempFeeSet) tVData
						.getObjectByObjectName("LJAGetTempFeeSet", 0));
				preLJTempFeeSet.add((LJTempFeeSet) tVData
						.getObjectByObjectName("LJTempFeeSet", 0));
				preLJAGetSet.add((LJAGetSet) tVData.getObjectByObjectName(
						"LJAGetSet", 0));
				preLJTempFeeClassSet.add((LJTempFeeClassSet) tVData
						.getObjectByObjectName("LJTempFeeClassSet", 0));
				// 如果需要添加打印数据，则在添加退费
				if (tAddPrtFlag) {
					mLOPRTManagerSet.add(mLOPRTManagerSchema);
				}
			}
		}
		ruturnData.add(preLJAGetTempFeeSet);
		ruturnData.add(preLJTempFeeSet);
		ruturnData.add(preLJAGetSet);
		ruturnData.add(preLJTempFeeClassSet);

		return ruturnData;
	}

}
