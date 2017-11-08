package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.db.LMDutyPayAddFeeDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LMDutyPayAddFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 计算加费金额
 * </p>
 * <p>
 * Description: 计算被保险人健康加费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */

public class AddPremCalBL {
private static Logger logger = Logger.getLogger(AddPremCalBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 险种加费表
	private LMDutyPayAddFeeSchema mLMDutyPayAddFeeSchema = new LMDutyPayAddFeeSchema();
	// 保单表
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	// 被保险人信息表
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	// 保费项表
	private LCPremSchema mLCPremSchema = new LCPremSchema();
	// 责任表
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private CalBase mCalBase = new CalBase();

	// 时间
	private FDate fDate = new FDate();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	String tStr = "";
	private String mCalCode;// 计算编码
	double mValue = 0.0;
	private String mInsuredNo;

	public AddPremCalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		logger.debug(tStr);
		mTransferData.setNameAndValue("mValue", tStr);
		mResult.add(mTransferData);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		double FirstScore = mLCPremSchema.getSuppRiskScore();
		double SecondScore = mLCPremSchema.getSecInsuAddPoint();
		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		tLDUWUserDB.setUserCode(mOperator);
		tLDUWUserDB.setUWType("1");
		if (!tLDUWUserDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LDUWUser表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		double tAddPoint = tLDUWUserDB.getAddPoint();
		if ((FirstScore > tAddPoint) || (SecondScore > tAddPoint)) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "加费评点过高，超过该核保师的权限范围!";
			this.mErrors.addOneError(tError);
			return false;

		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLMDutyPayAddFeeSchema = (LMDutyPayAddFeeSchema) cInputData
				.getObjectByObjectName("LMDutyPayAddFeeSchema", 0);
		mLCPolSchema = (LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0);
		mLCPremSchema = (LCPremSchema) cInputData.getObjectByObjectName(
				"LCPremSchema", 0);
		mLCDutySchema = (LCDutySchema) cInputData.getObjectByObjectName(
				"LCDutySchema", 0);

		mInsuredNo = mLCPolSchema.getInsuredNo();

		mInputData = cInputData;
		if (mInsuredNo == null || mInsuredNo.equals("")) {
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输被保险人mInsuredNo失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!prepareCalBase())
			return false;

		if (!prepareCalculate())
			return false;

		return true;
	}

	private boolean prepareCalBase() {
		// 准备保单险种表的信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLCPolSchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AddPremCalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LCPol表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolSchema.getPolNo());
		tLCDutyDB.setDutyCode(mLCDutySchema.getDutyCode());
		if (!tLCDutyDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AddPremCalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LCPol表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCDutySchema = tLCDutyDB.getSchema();

		// 准备被保险人表的信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLCPolSchema.getContNo());

		logger.debug("*************************");
		logger.debug("mInsuredNo==" + mInsuredNo);

		tLCInsuredDB.setInsuredNo(mInsuredNo);
		if (!tLCInsuredDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AddPremCalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询LCInsured表失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCInsuredSchema = tLCInsuredDB.getSchema();

		double dVPU = getVPU(mLCDutySchema.getDutyCode());

		// 准备基本要素信息
		mCalBase = new CalBase();
		mCalBase.setAppAge(mLCPolSchema.getInsuredAppAge());
		mCalBase.setAppAg2(getAppAg2(mLCPolSchema.getPolNo(), mLCPolSchema
				.getInsuredNo()));
		mCalBase.setSex(mLCInsuredSchema.getSex());
		mCalBase.setPayEndYear(mLCPolSchema.getPayEndYear());
		mCalBase.setSuppRiskScore(mLCPremSchema.getSuppRiskScore());
		mCalBase.setContNo(mLCPolSchema.getContNo());
		mCalBase.setGet(mLCPolSchema.getAmnt());
		mCalBase.setDutyCode(mLCDutySchema.getDutyCode());
		mCalBase.setPayIntv(mLCPolSchema.getPayIntv());
		mCalBase.setPolNo(mLCPolSchema.getPolNo());
		mCalBase.setInsuYear(mLCPolSchema.getInsuYear());
		mCalBase.setGetYear(mLCPolSchema.getGetYear());
		mCalBase.setFirstScore(mLCPremSchema.getSuppRiskScore());
		mCalBase.setSecondScore(mLCPremSchema.getSecInsuAddPoint());
		mCalBase.setMult(mLCDutySchema.getMult());
		mCalBase.setGetLimit(mLCDutySchema.getGetLimit());

		// 关于职业加费类别
		mCalBase.setJob(mLCInsuredSchema.getOccupationType());
		mCalBase.setVPU(String.valueOf(dVPU));
		mCalBase.setStandbyFlag1(mLCPolSchema.getStandbyFlag1());
		mCalBase.setCValiDate(mLCPolSchema.getCValiDate());

		logger.debug("mLCPolSchema.getInsuredSex()=="
				+ mLCPolSchema.getInsuredSex());
		// 针对险种144
		if (mLCPolSchema.getInsuredSex() != null
				&& mLCPolSchema.getInsuredSex().equals("0")) {
			mCalBase.setHusbandScore(mLCPremSchema.getSuppRiskScore());
			mCalBase.setWifeScore(mLCPremSchema.getSecInsuAddPoint());
		} else {
			mCalBase.setHusbandScore(mLCPremSchema.getSecInsuAddPoint());
			mCalBase.setWifeScore(mLCPremSchema.getSuppRiskScore());

		}

		return true;
	}

	private boolean prepareCalculate() {
		// 从LMDutyPayAddFee表中获取calcode字段值
		LMDutyPayAddFeeDB tLMDutyPayAddFeeDB = new LMDutyPayAddFeeDB();

		tLMDutyPayAddFeeDB
				.setAddFeeType(mLMDutyPayAddFeeSchema.getAddFeeType());
		tLMDutyPayAddFeeDB.setRiskCode(mLCPolSchema.getRiskCode());
		tLMDutyPayAddFeeDB.setDutyCode(mLCDutySchema.getDutyCode());
		// 如果加费对象为投保人,那么加费对象算法为01
		if (mLMDutyPayAddFeeSchema.getAddFeeObject().equals("01")) {
			tLMDutyPayAddFeeDB.setAddFeeObject("01");
		} else {
			String tSql = " select AddFeeObject from LMDutyPayAddFee where 1=1 "
					+ " and RiskCode = '"
					+ mLCPolSchema.getRiskCode()
					+ "'"
					+ " and DutyCode = '"
					+ mLCDutySchema.getDutyCode()
					+ "'"
					+ " and AddFeeType = '"
					+ mLMDutyPayAddFeeSchema.getAddFeeType()
					+ "'"
					+ " and AddFeeObject <> '01'";
			ExeSQL tExeSQl = new ExeSQL();
			String tSqlResult = tExeSQl.getOneValue(tSql);
			if (tSqlResult.equals("0") || tSqlResult.trim().equals("")
					|| tSqlResult.equals("null")) {
				CError tError = new CError();
				tError.moduleName = "AddPremCalBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "此险种无需进行加费，或者该险种没有此加费类型!";
				this.mErrors.addOneError(tError);
				return false;

			}

			tLMDutyPayAddFeeDB.setAddFeeObject(tSqlResult);

		}

		if (!tLMDutyPayAddFeeDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AddPremCalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "界面录入信息有误!";
			this.mErrors.addOneError(tError);
			return false;

		}
		mCalCode = tLMDutyPayAddFeeDB.getAddFeeCalCode();

		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator
				.addBasicFactor("SuppRiskScore", mCalBase.getSuppRiskScore());
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("DutyCode", mCalBase.getDutyCode());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("FirstScore", mCalBase.getFirstScore());
		mCalculator.addBasicFactor("SecondScore", mCalBase.getSecondScore());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("StandByFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("CValidate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("AppAg2", mCalBase.getAppAg2());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());

		// 针对险种144
		mCalculator.addBasicFactor("HusbandScore", mCalBase.getHusbandScore());
		mCalculator.addBasicFactor("WifeScore", mCalBase.getWifeScore());
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("")) {
			tStr = "0";
		}
		return true;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 查询VPU
	 * 
	 * @param sDutyCode
	 * @return String
	 */
	private double getVPU(String sDutyCode) {
		double dVPU = 0.0;
		String sql = " select VPU from lmduty " + " where dutycode = '"
				+ sDutyCode + "'";
		ExeSQL tExeSQL = new ExeSQL();
		String sVPU = tExeSQL.getOneValue(sql);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询VPU失败!");
			return -1;
		}
		if (sVPU == null || sVPU.trim().equals("")) {
			CError.buildErr(this, "VPU为空!");
			return -1;
		}
		try {
			dVPU = Double.parseDouble(sVPU);
		} catch (Exception e) {
			CError.buildErr(this, "VPU查询结果错误!" + "错误结果：" + sVPU);
			return -1;
		}

		return dVPU;
	}

	private String getAppAg2(String tPolNo, String tInsuredNo) {
		String AppAg2 = "";
		String tSql = " select Birthday from LCInsuredRelated where 1=1 "
				+ " and polno = '" + tPolNo + "'" + " and MainCustomerNo = '"
				+ tInsuredNo + "'";
		ExeSQL tExeSQL = new ExeSQL();
		AppAg2 = tExeSQL.getOneValue(tSql);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询AppAg2失败!");
			return "";
		}
		if (AppAg2 == null || AppAg2.trim().equals("")) {
			return "";
		}
		return AppAg2;

	}

}
