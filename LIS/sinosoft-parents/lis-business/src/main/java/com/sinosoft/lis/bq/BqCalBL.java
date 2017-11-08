package com.sinosoft.lis.bq;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LMEdorZT1DB;
import com.sinosoft.lis.db.LMLoanDB;
import com.sinosoft.lis.get.PrepareBOMAliveBL;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LMEdorZT1Set;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/*
 * <p>Title: 保全计算类 </p>
 * <p>Description: 通过传入的保单信息和保费项目信息计算出交退费变动信息，或保额变动信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft</p>
 * @author TJJ
 * @version 1.0
 * @date 2002-07-01
 */

public class BqCalBL {
private static Logger logger = Logger.getLogger(BqCalBL.class);
	// @Field
	private FDate fDate = new FDate();
	public CErrors mErrors = new CErrors(); // 错误信息

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private Boolean mBomListFlag=false;
	private String mCalCode;
	private String mFlag;
	private BqCalBase mBqCalBase = new BqCalBase();
	private List mBomList = new ArrayList();
	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
	public BqCalBL() {
	}

	public BqCalBL(LPEdorItemSchema aLPEdorItemSchema, BqCalBase aBqCalBase,
			String aCalCode, String aFlag) {
		mLPEdorItemSchema.setSchema(aLPEdorItemSchema);
		mBqCalBase = aBqCalBase;
		mCalCode = aCalCode;
		mFlag = aFlag;
	}

	public BqCalBL(BqCalBase aBqCalBase, String aCalCode, String aFlag) {
		mBqCalBase = aBqCalBase;
		mCalCode = aCalCode;
		mFlag = aFlag;
	}

	/**
	 * 判断自动核保是否通过
	 * 
	 * @return
	 */
	public boolean calUWEndorse() {
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Interval", mBqCalBase.getInterval());
		mCalculator.addBasicFactor("GetMoney", mBqCalBase.getGetMoney());
		mCalculator.addBasicFactor("Get", mBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", mBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mBqCalBase.getGetYear());
		mCalculator.addBasicFactor("Years", mBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", mBqCalBase.getCValiDate());
		mCalculator.addBasicFactor("Count", mBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", mBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mBqCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());

		mCalculator.addBasicFactor("EdorNo", mBqCalBase.getEdorNo());
		mCalculator.addBasicFactor("EdorType", mBqCalBase.getEdorType());
		mCalculator.addBasicFactor("GrpContNo", mBqCalBase.getGrpContNo());
		mCalculator.addBasicFactor("NSMonths", mBqCalBase.getNSMonths());
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		String tStr = "";
		tStr = mCalculator.calculate();
		logger.debug("---str" + tStr);
		if (tStr == null || tStr.trim().equals("") || tStr.trim().equals("0")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	public LJSGetEndorseSchema calGetEndorse(double pValue) {
		LJSGetEndorseSchema aLJSGetEndorseSchema = new LJSGetEndorseSchema();

		initGetEndorse();

		mLJSGetEndorseSchema.setGetMoney(pValue);
		aLJSGetEndorseSchema.setSchema(mLJSGetEndorseSchema);

		return aLJSGetEndorseSchema;
	}

	/**
	 * 按照比例法计算退保
	 * 
	 * @return
	 */
	public double calRateEndorse() {
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);

		// 增加基本要素
		mCalculator.addBasicFactor("Interval", mBqCalBase.getInterval());
		mCalculator.addBasicFactor("Get", mBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", mBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mBqCalBase.getGetYear());
		mCalculator.addBasicFactor("Years", mBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", mBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mBqCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());
		mCalculator.addBasicFactor("GetBalance", mBqCalBase.getGetBalance());
		mCalculator.addBasicFactor("GetMoney", mBqCalBase.getGetMoney());
		mCalculator.addBasicFactor("PayEndYearFlag", mBqCalBase
				.getPayEndYearFlag());
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return 0;
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		String tStr = mCalculator.calculate();
		logger.debug("按照比例法计算退保: " + tStr);

		if (tStr == null || tStr.trim().equals("")) {
			return 0;
		} else {
			return Double.parseDouble(tStr);
		}
	}

	/**
	 * 只计算保全金额
	 * 
	 * @param aFlag
	 * @return
	 */
	public double calGetEndorse(String aFlag) {
		LJSGetEndorseSchema aLJSGetEndorseSchema = new LJSGetEndorseSchema();
		double mValue = -1;
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Interval", mBqCalBase.getInterval());
		mCalculator.addBasicFactor("Get", mBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", mBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mBqCalBase.getGetYear());
		mCalculator.addBasicFactor("Years", mBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", mBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mBqCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());
		mCalculator.addBasicFactor("GetBalance", mBqCalBase.getGetBalance());
		mCalculator.addBasicFactor("GetMoney", mBqCalBase.getGetMoney());
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return 0;
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		logger.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<");
		logger.debug("Prem : " + mBqCalBase.getPrem());
		logger.debug("get: " + mBqCalBase.getGet());
		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Double.parseDouble(tStr);
		}
		logger.debug("----------cal:" + mValue);

		return mValue;
	}

	/**
	 * 只计算保全金额
	 * 
	 * @param pCalCode
	 *            计算编码
	 * @param pBqCalBase
	 *            计算要素类
	 * @return
	 */
	public double calGetEndorse(String pCalCode, BqCalBase pBqCalBase) {
		// 设置计算编码
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		// 增加基本要素
		tCalculator.addBasicFactor("Interval", pBqCalBase.getInterval());
		tCalculator.addBasicFactor("Get", pBqCalBase.getGet());
		tCalculator.addBasicFactor("Mult", pBqCalBase.getMult());
		tCalculator.addBasicFactor("Prem", pBqCalBase.getPrem());
		tCalculator.addBasicFactor("SumPrem", pBqCalBase.getSumPrem());
		tCalculator.addBasicFactor("PayIntv", pBqCalBase.getPayIntv());
		tCalculator.addBasicFactor("GetIntv", pBqCalBase.getGetIntv());
		tCalculator.addBasicFactor("AppAge", pBqCalBase.getAppAge());
		tCalculator.addBasicFactor("Sex", pBqCalBase.getSex());
		tCalculator.addBasicFactor("Job", pBqCalBase.getJob());
		tCalculator.addBasicFactor("PayEndYear", pBqCalBase.getPayEndYear());
		tCalculator.addBasicFactor("PayEndYearFlag", pBqCalBase
				.getPayEndYearFlag());
		tCalculator.addBasicFactor("GetStartDate", "");
		tCalculator.addBasicFactor("GetYear", pBqCalBase.getGetYear());
		tCalculator.addBasicFactor("Years", pBqCalBase.getYears());
		tCalculator.addBasicFactor("Grp", "");
		tCalculator.addBasicFactor("GetFlag", "");
		tCalculator.addBasicFactor("ValiDate", "");
		tCalculator.addBasicFactor("Count", pBqCalBase.getCount());
		tCalculator.addBasicFactor("FirstPayDate", "");
		tCalculator.addBasicFactor("AddRate", pBqCalBase.getAddRate());
		tCalculator.addBasicFactor("GDuty", pBqCalBase.getGDuty());
		tCalculator.addBasicFactor("PolNo", pBqCalBase.getPolNo());
		tCalculator.addBasicFactor("EdorNo", pBqCalBase.getEdorNo());
		tCalculator.addBasicFactor("EdorType", pBqCalBase.getEdorType());
		tCalculator
				.addBasicFactor("EdorValiDate", pBqCalBase.getEdorValiDate());
		tCalculator.addBasicFactor("EdorAppDate", pBqCalBase.getEdorAppDate());
		tCalculator.addBasicFactor("GetBalance", pBqCalBase.getGetBalance());
		tCalculator.addBasicFactor("GetMoney", pBqCalBase.getGetMoney());
		tCalculator.addBasicFactor("GrpContNo", pBqCalBase.getGrpContNo());
		tCalculator.addBasicFactor("CValiDate", pBqCalBase.getCValiDate());

		tCalculator.addBasicFactor("InsuYear", pBqCalBase.getInsuYear());
		tCalculator
				.addBasicFactor("InsuYearFlag", pBqCalBase.getInsuYearFlag());
		tCalculator
				.addBasicFactor("GetStartYear", pBqCalBase.getGetStartYear());
		tCalculator
				.addBasicFactor("StandbyFlag1", pBqCalBase.getStandByFlag1());

		logger.debug("Prem : " + pBqCalBase.getPrem());
		logger.debug("get: " + pBqCalBase.getGet());

		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return 0;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		
		// 进行计算
		String tStr = tCalculator.calculate();
		double tValue = 0;
		if (tCalculator.mErrors.needDealError()) {
			mErrors.copyAllErrors(tCalculator.mErrors);
			return tValue;
		}

		if (tStr == null || tStr.trim().equals("")) {
			tValue = 0;
		} else {
			tValue = Double.parseDouble(tStr);
		}

		logger.debug("Cal Result:" + tValue);
		return tValue;
	}

	/**
	 * 得到校验结果
	 * 
	 * @param aFlag
	 * @return
	 */
	public boolean calValiEndorse(String aFlag) {
		boolean aValiFlag = true;
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Interval", mBqCalBase.getInterval());
		mCalculator.addBasicFactor("LimitDay", mBqCalBase.getLimitDay());
		mCalculator.addBasicFactor("PolValiFlag", mBqCalBase.getLimitDay());
		mCalculator.addBasicFactor("LoanMoney", mBqCalBase.getLoanMoney());
		mCalculator.addBasicFactor("TrayMoney", mBqCalBase.getTrayMoney());
		mCalculator.addBasicFactor("Operator", mBqCalBase.getOperator());
		mCalculator
				.addBasicFactor("EdorValiDate", mBqCalBase.getEdorValiDate());
		mCalculator.addBasicFactor("Get", mBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", mBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mBqCalBase.getGetYear());
		mCalculator.addBasicFactor("Years", mBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", mBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mBqCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());
		mCalculator.addBasicFactor("GetBalance", mBqCalBase.getGetBalance());
		mCalculator.addBasicFactor("GetMoney", mBqCalBase.getGetMoney());
		mCalculator.addBasicFactor("RiskCode", mBqCalBase.getRiskCode());
		mCalculator.addBasicFactor("EdorNo", mBqCalBase.getEdorNo());
		mCalculator
				.addBasicFactor("EdorAcceptNo", mBqCalBase.getEdorAcceptNo());
		mCalculator.addBasicFactor("ContNo", mBqCalBase.getContNo());
		mCalculator.addBasicFactor("EdorType", mBqCalBase.getEdorType());
		mCalculator.addBasicFactor("GrpContNo", mBqCalBase.getGrpContNo());
		String tStr = "";
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		tStr = mCalculator.calculate();
		int mValue = 0;
		if (tStr == null || tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Integer.parseInt(tStr);
		}

		if (aFlag == "N") {
			if (mValue == 0) {
				aValiFlag = true;
			} else {
				aValiFlag = false;
			}
		} else {
			if (mValue == 0) {
				aValiFlag = false;
			} else {
				aValiFlag = true;
			}
		}

		logger.debug("----------ValiFlag:" + aValiFlag);

		return aValiFlag;
	}

	/**
	 * 生存领取计算
	 * 
	 * @return
	 */
	public double calGetDraw() {
		LJSGetDrawSchema aLJSGetDrawSchema = new LJSGetDrawSchema();
		double mValue = -1;
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Interval", mBqCalBase.getInterval());
		mCalculator.addBasicFactor("Get", mBqCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mBqCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mBqCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mBqCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mBqCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mBqCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mBqCalBase.getSex());
		mCalculator.addBasicFactor("Job", mBqCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mBqCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mBqCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mBqCalBase.getGetYear());
		mCalculator.addBasicFactor("InsuYear", mBqCalBase.getInsuYear());
		mCalculator.addBasicFactor("Years", mBqCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("ValiDate", "");
		mCalculator.addBasicFactor("Count", mBqCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("AddRate", mBqCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mBqCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());
		mCalculator.addBasicFactor("FRate", "1");

		mCalculator.addBasicFactor("GetBalance", mBqCalBase.getGetBalance());

		// 新增------------2005-07-20----------->
		mCalculator.addBasicFactor("DutyCode", mBqCalBase.getDutyCode());
		mCalculator.addBasicFactor("GetAge", mBqCalBase.getGetAge());
		mCalculator.addBasicFactor("Amnt", mBqCalBase.getAmnt());
		mCalculator.addBasicFactor("VPU", mBqCalBase.getVPU());
		mCalculator.addBasicFactor("FinalBonus", mBqCalBase.getFinalBonus());
		mCalculator
				.addBasicFactor("SumAmntBonus", mBqCalBase.getSumAmntBonus());
		mCalculator.addBasicFactor("GetStartAge", mBqCalBase.getGetStartAge());
		mCalculator.addBasicFactor("SumPrem", mBqCalBase.getSumPrem());
		mCalculator.addBasicFactor("TotalPrem", mBqCalBase.getTotalPrem());
		mCalculator.addBasicFactor("PayTimes", mBqCalBase.getPayTimes());
		mCalculator.addBasicFactor("GetDutyKind", mBqCalBase.getGetDutyKind());
		mCalculator.addBasicFactor("TAFlag", "CF");// 2006-2-23 tp
													// modify,催108险种(做过TA的)
		mCalculator.addBasicFactor("GetDutyCode", mBqCalBase.getGetDutyCode());// 2006-2-23
																				// tp
																				// modify,催108险种
		mCalculator.addBasicFactor("GetTimes", mBqCalBase.getGetTimes());
		mCalculator.addBasicFactor("CashValue", mBqCalBase.getCashValue());// 2006-4
		LCGetSchema tLCGetSchema=new LCGetSchema();
		LCGetDB tLCGetDB=new LCGetDB();
		tLCGetDB.setPolNo(mBqCalBase.getPolNo());
		tLCGetDB.setGetDutyCode(mBqCalBase.getGetDutyCode());
		tLCGetSchema=tLCGetDB.query().get(1);
		PrepareBOMAliveBL mPrepareBOMAliveBL=new PrepareBOMAliveBL();
		LCPolDB tLCPolDB=new LCPolDB();
		LCPolSchema tLCPolSchema=new LCPolSchema();
		tLCPolDB.setContNo(tLCGetSchema.getContNo());
		tLCPolDB.setPolNo(tLCGetSchema.getPolNo());
		tLCPolSchema=tLCPolDB.query().get(1);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("GetAge", mBqCalBase.getGetAge());
		tTransferData.setNameAndValue("GetTimes",  mBqCalBase.getGetTimes());
		try{
		this.mBomList = mPrepareBOMAliveBL.dealData(tLCPolSchema,tLCGetSchema,tTransferData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mCalculator.setBOMList(this.mBomList);// 添加BOMList
		String tStr = "";
		tStr = mCalculator.calculate();
		logger.debug("tStr :" + tStr);
		if (tStr == null || tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Double.parseDouble(tStr);
		}
		logger.debug("----------cal:" + mValue);

		return mValue;
	}

	/**
	 * 生成交退费记录(查表）
	 * 
	 * @return
	 */
	private boolean initGetEndorse() {
		LCPolDB tLCPolDB = new LCPolDB();
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		mLJSGetEndorseSchema = new LJSGetEndorseSchema();

		tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());

		if (!tLCPolDB.getInfo()) {
			return false;
		}

		// 生成批改交退费表
		tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSGetEndorseSchema.setContNo(tLCPolDB.getContNo());
		tLJSGetEndorseSchema.setGrpContNo(tLCPolDB.getGrpContNo());
		tLJSGetEndorseSchema.setPolNo(tLCPolDB.getPolNo());
		tLJSGetEndorseSchema.setAppntNo(tLCPolDB.getAppntNo());
		tLJSGetEndorseSchema.setInsuredNo(tLCPolDB.getInsuredNo());
		tLJSGetEndorseSchema.setAppntNo(tLCPolDB.getAppntNo());
		tLJSGetEndorseSchema.setGetMoney(tLCPolDB.getSumPrem());
		tLJSGetEndorseSchema.setKindCode(tLCPolDB.getKindCode());
		tLJSGetEndorseSchema.setRiskCode(tLCPolDB.getRiskCode());
		tLJSGetEndorseSchema.setRiskVersion(tLCPolDB.getRiskVersion());
		tLJSGetEndorseSchema.setAgentCom(tLCPolDB.getAgentCom());
		tLJSGetEndorseSchema.setAgentCode(tLCPolDB.getAgentCode());
		tLJSGetEndorseSchema.setAgentType(tLCPolDB.getAgentType());
		tLJSGetEndorseSchema.setAgentGroup(tLCPolDB.getAgentGroup());
		tLJSGetEndorseSchema.setDutyCode(" ");
		tLJSGetEndorseSchema.setSubFeeOperationType("000000"); // 暂设待定！！
		mLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);

		return true;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @param aLPEdorItemSchema
	 * @param aLPPolSchema
	 * @param aLPDutySchema
	 * @param aOperationType
	 * @param aFeeType
	 * @param aGetMoney
	 * @param aGlobalInput
	 * @return LJSGetEndorseSchema
	 */
	public LJSGetEndorseSchema initLJSGetEndorse(
			LPEdorItemSchema aLPEdorItemSchema, LPPolSchema aLPPolSchema,
			String aDutyCode, String aPayPlanCode, String aOperationType,
			String aFeeType, double aGetMoney, GlobalInput aGlobalInput) {
		try {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			mLJSGetEndorseSchema = new LJSGetEndorseSchema();

			// 生成批改交退费表
			tLJSGetEndorseSchema.setGetNoticeNo(aLPEdorItemSchema.getEdorNo()); // 给付通知书号码
			tLJSGetEndorseSchema
					.setEndorsementNo(aLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setGrpContNo(aLPPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setGrpPolNo(aLPPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setContNo(aLPEdorItemSchema.getContNo());
			if (aLPPolSchema != null && aLPPolSchema.getPolNo() != null
					&& !aLPPolSchema.getPolNo().equals("")) {
				tLJSGetEndorseSchema.setPolNo(aLPPolSchema.getPolNo());
			} else {
				tLJSGetEndorseSchema.setPolNo(aLPEdorItemSchema.getPolNo());
			}
			tLJSGetEndorseSchema
					.setGetDate(aLPEdorItemSchema.getEdorValiDate());
			tLJSGetEndorseSchema.setGetMoney(aGetMoney);
			tLJSGetEndorseSchema.setFeeOperationType(aLPEdorItemSchema
					.getEdorType());
			tLJSGetEndorseSchema.setSubFeeOperationType(aOperationType); // 补退费子业务类型
			tLJSGetEndorseSchema.setFeeFinaType(aFeeType); // 补退费财务类型
			tLJSGetEndorseSchema.setDutyCode(aDutyCode);
			tLJSGetEndorseSchema.setPayPlanCode(aPayPlanCode);
			tLJSGetEndorseSchema.setOtherNo(aLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
			tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJSGetEndorseSchema.setGetFlag("0");
			tLJSGetEndorseSchema.setAgentCode(aLPPolSchema.getAgentCode());
			tLJSGetEndorseSchema.setAgentCom(aLPPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentGroup(aLPPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setAgentType(aLPPolSchema.getAgentType());
			tLJSGetEndorseSchema.setInsuredNo(aLPPolSchema.getInsuredNo());
			tLJSGetEndorseSchema.setKindCode(aLPPolSchema.getKindCode());
			tLJSGetEndorseSchema.setAppntNo(aLPPolSchema.getAppntNo());
			tLJSGetEndorseSchema.setRiskCode(aLPPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(aLPPolSchema.getRiskVersion());
			tLJSGetEndorseSchema.setHandler(aLPPolSchema.getHandler());
			tLJSGetEndorseSchema.setApproveCode(aLPPolSchema.getApproveCode());
			tLJSGetEndorseSchema.setApproveDate(aLPPolSchema.getApproveDate());
			tLJSGetEndorseSchema.setApproveTime(aLPPolSchema.getApproveTime());
			tLJSGetEndorseSchema.setManageCom(aLPPolSchema.getManageCom());
			tLJSGetEndorseSchema.setOperator(aLPPolSchema.getOperator());
			tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setCurrency(aLPPolSchema.getCurrency());
			tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
			mLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
		} catch (Exception ex) {
			mErrors.addOneError(new CError("建立批改补退费信息异常！"));
			return null;
		}
		return mLJSGetEndorseSchema;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @param aLPEdorItemSchema
	 * @param aLPPolSchema
	 * @param aLPDutySchema
	 * @param aOperationType
	 * @param aFeeType
	 * @param aGetMoney
	 * @param aGlobalInput
	 * @return LJSGetEndorseSchema
	 */
	public LJSGetEndorseSchema initLJSGetEndorse(
			LPEdorItemSchema aLPEdorItemSchema, LPPolSchema aLPPolSchema,
			LPDutySchema aLPDutySchema, String aOperationType, String aFeeType,
			double aGetMoney, GlobalInput aGlobalInput) {
		try {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			mLJSGetEndorseSchema = new LJSGetEndorseSchema();

			// 生成批改交退费表
			tLJSGetEndorseSchema.setGetNoticeNo(aLPEdorItemSchema.getEdorNo()); // 给付通知书号码
			tLJSGetEndorseSchema
					.setEndorsementNo(aLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setGrpContNo(aLPPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setGrpPolNo(aLPPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setContNo(aLPEdorItemSchema.getContNo());
			if (aLPPolSchema != null && aLPPolSchema.getPolNo() != null
					&& !aLPPolSchema.getPolNo().equals("")) {
				tLJSGetEndorseSchema.setPolNo(aLPPolSchema.getPolNo());
			} else {
				tLJSGetEndorseSchema.setPolNo(aLPEdorItemSchema.getPolNo());
			}
			tLJSGetEndorseSchema
					.setGetDate(aLPEdorItemSchema.getEdorValiDate());
			tLJSGetEndorseSchema.setGetMoney(aGetMoney);
			tLJSGetEndorseSchema.setFeeOperationType(aLPEdorItemSchema
					.getEdorType());
			tLJSGetEndorseSchema.setSubFeeOperationType(aOperationType); // 补退费子业务类型
			tLJSGetEndorseSchema.setFeeFinaType(aFeeType); // 补退费财务类型
			tLJSGetEndorseSchema.setPayPlanCode("0"); // 无作用
			if (aLPDutySchema == null || aLPDutySchema.getDutyCode() == null
					|| aLPDutySchema.getDutyCode().equals("")) {
				tLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
			} else {
				tLJSGetEndorseSchema.setDutyCode(aLPDutySchema.getDutyCode());
			}
			tLJSGetEndorseSchema.setOtherNo(aLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
			tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJSGetEndorseSchema.setGetFlag("0");
			tLJSGetEndorseSchema.setAgentCode(aLPPolSchema.getAgentCode());
			tLJSGetEndorseSchema.setAgentCom(aLPPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentGroup(aLPPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setAgentType(aLPPolSchema.getAgentType());
			tLJSGetEndorseSchema.setInsuredNo(aLPPolSchema.getInsuredNo());
			tLJSGetEndorseSchema.setKindCode(aLPPolSchema.getKindCode());
			tLJSGetEndorseSchema.setAppntNo(aLPPolSchema.getAppntNo());
			tLJSGetEndorseSchema.setRiskCode(aLPPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(aLPPolSchema.getRiskVersion());
			tLJSGetEndorseSchema.setHandler(aLPPolSchema.getHandler());
			tLJSGetEndorseSchema.setApproveCode(aLPPolSchema.getApproveCode());
			tLJSGetEndorseSchema.setApproveDate(aLPPolSchema.getApproveDate());
			tLJSGetEndorseSchema.setApproveTime(aLPPolSchema.getApproveTime());
			tLJSGetEndorseSchema.setCurrency(aLPPolSchema.getCurrency());
			tLJSGetEndorseSchema.setManageCom(aLPPolSchema.getManageCom());
			tLJSGetEndorseSchema.setOperator(aLPPolSchema.getOperator());
			tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
			mLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
		} catch (Exception ex) {
			mErrors.addOneError(new CError("建立批改补退费信息异常！"));
			return null;
		}
		return mLJSGetEndorseSchema;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @param aLPEdorItemSchema
	 * @param aLCPolSchema
	 * @param aLCDutySchema
	 * @param aOperationType
	 * @param aFeeType
	 * @param aGetMoney
	 * @param aGlobalInput
	 * @return
	 */
	public LJSGetEndorseSchema initLJSGetEndorse(
			LPEdorItemSchema aLPEdorItemSchema, LCPolSchema aLCPolSchema,
			LCDutySchema aLCDutySchema, String aOperationType, String aFeeType,
			double aGetMoney, GlobalInput aGlobalInput) {
		try {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			mLJSGetEndorseSchema = new LJSGetEndorseSchema();

			// 生成批改交退费表
			tLJSGetEndorseSchema.setGetNoticeNo(aLPEdorItemSchema.getEdorNo()); // 给付通知书号码
			tLJSGetEndorseSchema
					.setEndorsementNo(aLPEdorItemSchema.getEdorNo());
			tLJSGetEndorseSchema.setGrpContNo(aLCPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setGrpPolNo(aLCPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setContNo(aLPEdorItemSchema.getContNo());
			if (aLCPolSchema != null && aLCPolSchema.getPolNo() != null
					&& !aLCPolSchema.getPolNo().equals("")) {
				tLJSGetEndorseSchema.setPolNo(aLCPolSchema.getPolNo());
			} else {
				tLJSGetEndorseSchema.setPolNo(aLPEdorItemSchema.getPolNo());
			}
			tLJSGetEndorseSchema
					.setGetDate(aLPEdorItemSchema.getEdorValiDate());
			tLJSGetEndorseSchema.setGetMoney(aGetMoney);
			tLJSGetEndorseSchema.setFeeOperationType(aLPEdorItemSchema
					.getEdorType());
			tLJSGetEndorseSchema.setSubFeeOperationType(aOperationType); // 补退费子业务类型
			tLJSGetEndorseSchema.setFeeFinaType(aFeeType); // 补退费财务类型
			tLJSGetEndorseSchema.setPayPlanCode("0"); // 无作用
			if (aLCDutySchema == null || aLCDutySchema.getDutyCode() == null
					|| aLCDutySchema.getDutyCode().equals("")) {
				tLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
			} else {
				tLJSGetEndorseSchema.setDutyCode(aLCDutySchema.getDutyCode());
			}
			tLJSGetEndorseSchema.setOtherNo(aLPEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
			tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJSGetEndorseSchema.setGetFlag("1"); // "0"，补费 "1"，退费，默认退费
			tLJSGetEndorseSchema.setAgentCode(aLCPolSchema.getAgentCode());
			tLJSGetEndorseSchema.setAgentCom(aLCPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentGroup(aLCPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setAgentType(aLCPolSchema.getAgentType());
			tLJSGetEndorseSchema.setInsuredNo(aLCPolSchema.getInsuredNo());
			tLJSGetEndorseSchema.setKindCode(aLCPolSchema.getKindCode());
			tLJSGetEndorseSchema.setAppntNo(aLCPolSchema.getAppntNo());
			tLJSGetEndorseSchema.setRiskCode(aLCPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(aLCPolSchema.getRiskVersion());
			tLJSGetEndorseSchema.setHandler(aLCPolSchema.getHandler());
			tLJSGetEndorseSchema.setApproveCode(aLCPolSchema.getApproveCode());
			tLJSGetEndorseSchema.setApproveDate(aLCPolSchema.getApproveDate());
			tLJSGetEndorseSchema.setApproveTime(aLCPolSchema.getApproveTime());
			tLJSGetEndorseSchema.setCurrency(aLCPolSchema.getCurrency());
			tLJSGetEndorseSchema.setManageCom(aLCPolSchema.getManageCom());
			tLJSGetEndorseSchema.setOperator(aLCPolSchema.getOperator());
			tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
			mLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
		} catch (Exception ex) {
			mErrors.addOneError(new CError("建立批改补退费信息异常！"));
			return null;
		}
		return mLJSGetEndorseSchema;
	}

	/**
	 * 生成交退费记录
	 * 
	 * @param aLPGrpEdorItemSchema
	 * @param aLCGrpPolSchema
	 * @param aOperationType
	 * @param aFeeType
	 * @param aGetMoney
	 * @param aGlobalInput
	 * @return
	 */
	public LJSGetEndorseSchema initLJSGetEndorse(
			LPGrpEdorItemSchema aLPGrpEdorItemSchema,
			LCGrpPolSchema aLCGrpPolSchema, String aOperationType,
			String aFeeType, double aGetMoney, GlobalInput aGlobalInput) {

		try {
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			mLJSGetEndorseSchema = new LJSGetEndorseSchema();

			// 生成批改交退费表
			tLJSGetEndorseSchema.setGetNoticeNo(aLPGrpEdorItemSchema
					.getEdorNo()); // 给付通知书号码
			tLJSGetEndorseSchema.setEndorsementNo(aLPGrpEdorItemSchema
					.getEdorNo());
			tLJSGetEndorseSchema.setGrpContNo(aLCGrpPolSchema.getGrpContNo());
			tLJSGetEndorseSchema.setGrpPolNo(aLCGrpPolSchema.getGrpPolNo());
			tLJSGetEndorseSchema.setPolNo("000000");
			tLJSGetEndorseSchema.setGetDate(aLPGrpEdorItemSchema
					.getEdorValiDate());
			tLJSGetEndorseSchema.setGetMoney(aGetMoney);
			tLJSGetEndorseSchema.setFeeOperationType(aLPGrpEdorItemSchema
					.getEdorType()); // 补退费业务类型
			tLJSGetEndorseSchema.setSubFeeOperationType(aOperationType); // 补退费子业务类型
			tLJSGetEndorseSchema.setFeeFinaType(aFeeType); // 补退费财务类型
			tLJSGetEndorseSchema.setPayPlanCode("0"); // 无作用
			tLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
			tLJSGetEndorseSchema.setOtherNo(aLPGrpEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
			tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
			tLJSGetEndorseSchema.setGetFlag("0");
			tLJSGetEndorseSchema.setAgentCode(aLCGrpPolSchema.getAgentCode());
			tLJSGetEndorseSchema.setAgentCom(aLCGrpPolSchema.getAgentCom());
			tLJSGetEndorseSchema.setAgentGroup(aLCGrpPolSchema.getAgentGroup());
			tLJSGetEndorseSchema.setAgentType(aLCGrpPolSchema.getAgentType());
			tLJSGetEndorseSchema.setKindCode(aLCGrpPolSchema.getKindCode());
			tLJSGetEndorseSchema.setAppntNo(aLCGrpPolSchema.getCustomerNo());
			tLJSGetEndorseSchema.setRiskCode(aLCGrpPolSchema.getRiskCode());
			tLJSGetEndorseSchema.setRiskVersion(aLCGrpPolSchema
					.getRiskVersion());
			tLJSGetEndorseSchema.setApproveCode(aLCGrpPolSchema
					.getApproveCode());
			tLJSGetEndorseSchema.setApproveDate(aLCGrpPolSchema
					.getApproveDate());
			tLJSGetEndorseSchema.setApproveTime(aLCGrpPolSchema
					.getApproveTime());
			tLJSGetEndorseSchema.setCurrency(aLCGrpPolSchema.getCurrency());
			tLJSGetEndorseSchema.setManageCom(aLCGrpPolSchema.getManageCom());
			tLJSGetEndorseSchema.setOperator(aLCGrpPolSchema.getOperator());
			tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
			tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
			tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
			mLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
		} catch (Exception ex) {
			mErrors.addOneError(new CError("建立批改补退费信息异常！"));
			return null;
		}
		return mLJSGetEndorseSchema;
	}

	/**
	 * 获取转换财务接口的描述
	 * 
	 * @param edorType
	 * @param sysType
	 * @param riskCode
	 * @return
	 */
	public static String getFinType(String edorType, String sysType,
			String polNo) {
		// 根据保单号获取险种代码
		String riskCode = "";

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(polNo);
		if (!tLCPolDB.getInfo()) {
			if (tLCPolDB.mErrors.needDealError()) {
				return "";
			}
			// 如果个单查询失败，则可能是团单
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(polNo);
			tLCGrpContDB.getInfo();
			if (tLCGrpContDB.mErrors.needDealError()) {
				return "";

			} else {
				riskCode = "000000";
			}
		} else {
			riskCode = tLCPolDB.getRiskCode();
		}

		// 根据险种代码获取对应的财务类型编码
		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCodeType(edorType);
		tLDCode1DB.setCode(sysType);
		tLDCode1DB.setCode1(riskCode);
		if (tLDCode1DB.getInfo()) {
			return tLDCode1DB.getCodeName();
		}

		// 如果该险种未描述，则该项目的通用财务类型
		tLDCode1DB.setCode1("000000");
		if (tLDCode1DB.getInfo()) {
			return tLDCode1DB.getCodeName();
		}

		return "";
	}
	
	
	public static String getFinType_HL_SC(String codetype, String insureaccno,
			String feetype) {
	 
		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCodeType(codetype);
		tLDCode1DB.setCode(insureaccno);
		tLDCode1DB.setCode1(feetype);
		if (tLDCode1DB.getInfo()) {
			return tLDCode1DB.getCodeName();
		}
		return "";
	}

	public TransferData calChgAllPrem(LPPolSchema aLPPolSchema, double aNewPrem) {
		// 按照最新交费间隔计算交费期数
		int premNum = 0;

		if (aLPPolSchema.getPayIntv() == 0) {
			premNum = 1;
		} else {
			premNum = PubFun.calInterval(aLPPolSchema.getCValiDate(),
					aLPPolSchema.getPaytoDate(), "M")
					/ aLPPolSchema.getPayIntv();
		}

		double intervalMoney = aLPPolSchema.getPrem() - aNewPrem; // 保费差额
		double bfMoney = 0; // 补费本金
		double interestMoney = 0; // 利息
		// 实交保费少于应交保费
		if (intervalMoney > 0) {
			LMLoanDB tLMLoanDB = new LMLoanDB();
			tLMLoanDB.setRiskCode(aLPPolSchema.getRiskCode());

			if (!tLMLoanDB.getInfo()) {
				CError.buildErr(this, "查找利率失败！", tLMLoanDB.mErrors);
				return null;
			}

			AccountManage tAccountManage = new AccountManage();

			// 计算利息
			double interest = 0;

			for (int j = 0; j < premNum; j++) {
				if (j == 0) {
					interest = tAccountManage.getInterest(intervalMoney,
							aLPPolSchema.getCValiDate(), PubFun
									.getCurrentDate(), tLMLoanDB
									.getInterestRate(), tLMLoanDB
									.getInterestMode(), tLMLoanDB
									.getInterestType(), "D");
				} else {
					interest = tAccountManage.getInterest(intervalMoney, PubFun
							.calDate(aLPPolSchema.getCValiDate(), j
									* aLPPolSchema.getPayIntv(), "M",
									aLPPolSchema.getCValiDate()), PubFun
							.getCurrentDate(), tLMLoanDB.getInterestRate(),
							tLMLoanDB.getInterestMode(), tLMLoanDB
									.getInterestType(), "D");
				}

				bfMoney = bfMoney + intervalMoney; // 补费本金

				if (interest > 0) {
					interestMoney = interestMoney + interest; // 利息
				}

				bfMoney = Double.parseDouble(new DecimalFormat("0.00")
						.format(bfMoney));
				interestMoney = Double.parseDouble(new DecimalFormat("0.00")
						.format(interestMoney));
			}
		}

		TransferData tTD = new TransferData();
		tTD.setNameAndValue("ChgPrems", bfMoney);
		tTD.setNameAndValue("Interest", interestMoney);
		return tTD;
	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase aBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(aBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}
	
	public void setLPEdorItemSchema(LPEdorItemSchema tLPEdorItemSchema)
	{
		mLPEdorItemSchema=tLPEdorItemSchema;
	}

	public double calChgMoney(String aCalCode, BqCalBase aBqCalBase) {
		if (aCalCode == null || aCalCode.equals("")) {
			// mErrors.addOneError(new CError("计算代码为空，无法计算！"));
			return 0;
		}
		// 设置计算编码
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(aCalCode);
		if (!prepareBOMList(aBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return 0;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		// 增加基本要素
		if (!setBasicFactor(tCalculator, aBqCalBase)) {
			mErrors.addOneError(new CError("设置计算要素失败！"));
			return 0;
		}
		// 进行计算
		String tStr = tCalculator.calculate();
		double tValue = 0;
		if (tStr == null || tStr.trim().equals("")) {
			tValue = 0;
		} else {
			tValue = Double.parseDouble(tStr);
		}

		return tValue;
	}

	public double calShortRate(String aCalCode, BqCalBase aBqCalBase) {
		if (aCalCode == null || aCalCode.equals("")) {
			// mErrors.addOneError(new CError("计算代码为空，无法计算！"));
			return 0;
		}
		// 设置计算编码
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(aCalCode);

		// 增加基本要素

		// 进行计算
		tCalculator.addBasicFactor("Interval", aBqCalBase.getInterval());
		tCalculator.addBasicFactor("Get", aBqCalBase.getGet());
		tCalculator.addBasicFactor("Mult", aBqCalBase.getMult());
		tCalculator.addBasicFactor("Prem", aBqCalBase.getPrem());
		logger.debug("----------------------dd----"
				+ aBqCalBase.getPrem());
		tCalculator.addBasicFactor("SumPrem", aBqCalBase.getSumPrem());
		tCalculator.addBasicFactor("NewPrem", aBqCalBase.getNewPrem());
		logger.debug("----------------------PayIntv----"
				+ aBqCalBase.getPayIntv());
		tCalculator.addBasicFactor("PayIntv", aBqCalBase.getPayIntv());
		tCalculator.addBasicFactor("GetIntv", aBqCalBase.getGetIntv());
		tCalculator.addBasicFactor("AppAge", aBqCalBase.getAppAge());
		tCalculator.addBasicFactor("Sex", aBqCalBase.getSex());
		tCalculator.addBasicFactor("Job", aBqCalBase.getJob());
		tCalculator.addBasicFactor("PayEndYear", aBqCalBase.getPayEndYear());
		tCalculator.addBasicFactor("PayEndYearFlag", aBqCalBase
				.getPayEndYearFlag());
		tCalculator.addBasicFactor("GetStartDate", "");
		tCalculator.addBasicFactor("GetYear", aBqCalBase.getGetYear());
		tCalculator.addBasicFactor("Years", aBqCalBase.getYears());
		tCalculator.addBasicFactor("Grp", "");
		tCalculator.addBasicFactor("GetFlag", "");
		tCalculator.addBasicFactor("ValiDate", aBqCalBase.getEdorValiDate());
		tCalculator.addBasicFactor("CValiDate", aBqCalBase.getCValiDate());
		tCalculator.addBasicFactor("EndDate", aBqCalBase.getEndDate());
		tCalculator.addBasicFactor("Count", aBqCalBase.getCount());
		tCalculator.addBasicFactor("FirstPayDate", "");
		tCalculator.addBasicFactor("AddRate", aBqCalBase.getAddRate());
		tCalculator.addBasicFactor("GDuty", aBqCalBase.getGDuty());
		tCalculator.addBasicFactor("PolNo", aBqCalBase.getPolNo());
		tCalculator.addBasicFactor("EdorNo", aBqCalBase.getEdorNo());
		tCalculator.addBasicFactor("EdorType", aBqCalBase.getEdorType());
		tCalculator
				.addBasicFactor("EdorValiDate", aBqCalBase.getEdorValiDate());
		tCalculator.addBasicFactor("EdorAppDate", aBqCalBase.getEdorAppDate());
		tCalculator.addBasicFactor("GetBalance", aBqCalBase.getGetBalance());
		tCalculator.addBasicFactor("GetMoney", aBqCalBase.getGetMoney());
		tCalculator.addBasicFactor("InsuYear", aBqCalBase.getInsuYear());
		tCalculator
				.addBasicFactor("InsuYearFlag", aBqCalBase.getInsuYearFlag());
		tCalculator
				.addBasicFactor("GetStartYear", aBqCalBase.getGetStartYear());
		tCalculator
				.addBasicFactor("StandbyFlag1", aBqCalBase.getStandByFlag1());
		tCalculator.addBasicFactor("RemainYear", aBqCalBase.getRemainYear());
		tCalculator.addBasicFactor("StartDate", aBqCalBase.getStartDate());
		tCalculator.addBasicFactor("NSMonths", aBqCalBase.getNSMonths());
		tCalculator.addBasicFactor("BonusRate", aBqCalBase.getBonusRate());
		tCalculator.addBasicFactor("ForceDVFlag", aBqCalBase.getForceDVFlag());
		tCalculator.addBasicFactor("AAYears", aBqCalBase.getAAYears());
		tCalculator.addBasicFactor("InsuYearT1", aBqCalBase.getInsuYearT1());
		tCalculator.addBasicFactor("InsuYearT2", aBqCalBase.getInsuYearT2());
		tCalculator.addBasicFactor("IntvMonth", aBqCalBase.getIntvMonth());
		tCalculator.addBasicFactor("EdorTypeCal", aBqCalBase.getEdorTypeCal());
		tCalculator.addBasicFactor("ContNo", aBqCalBase.getContNo());
		tCalculator.addBasicFactor("PayToDate", aBqCalBase.getPayToDate());
		tCalculator.addBasicFactor("GrpPolNo", aBqCalBase.getGrpPolNo());
		tCalculator.addBasicFactor("GrpCValiDate", aBqCalBase.getGrpCValiDate());
		logger.debug("----------------------PayToDate----"
				+ aBqCalBase.getPayToDate());

		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return 0;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		String tStr = tCalculator.calculate();
		double tValue = 0;
		if (tStr == null || tStr.trim().equals("")) {
			tValue = 0;
		} else {
			tValue = Double.parseDouble(tStr);
		}

		return tValue;
	}

	private boolean setBasicFactor(Calculator aCalculator, BqCalBase aBqCalBase) {
		aCalculator.addBasicFactor("Interval", aBqCalBase.getInterval());
		aCalculator.addBasicFactor("Get", aBqCalBase.getGet());
		aCalculator.addBasicFactor("Mult", aBqCalBase.getMult());
		aCalculator.addBasicFactor("Prem", aBqCalBase.getPrem());
		aCalculator.addBasicFactor("SumPrem", aBqCalBase.getSumPrem());
		aCalculator.addBasicFactor("NewPrem", aBqCalBase.getNewPrem());
		aCalculator.addBasicFactor("PayIntv", aBqCalBase.getPayIntv());
		aCalculator.addBasicFactor("GetIntv", aBqCalBase.getGetIntv());
		aCalculator.addBasicFactor("AppAge", aBqCalBase.getAppAge());
		aCalculator.addBasicFactor("Sex", aBqCalBase.getSex());
		aCalculator.addBasicFactor("Job", aBqCalBase.getJob());
		aCalculator.addBasicFactor("PayEndYear", aBqCalBase.getPayEndYear());
		aCalculator.addBasicFactor("PayEndYearFlag", aBqCalBase
				.getPayEndYearFlag());
		aCalculator.addBasicFactor("GetStartDate", "");
		aCalculator.addBasicFactor("GetYear", aBqCalBase.getGetYear());
		aCalculator.addBasicFactor("Years", aBqCalBase.getYears());
		aCalculator.addBasicFactor("Grp", "");
		aCalculator.addBasicFactor("GetFlag", "");
		aCalculator.addBasicFactor("ValiDate", "");
		aCalculator.addBasicFactor("Count", aBqCalBase.getCount());
		aCalculator.addBasicFactor("FirstPayDate", "");
		aCalculator.addBasicFactor("AddRate", aBqCalBase.getAddRate());
		aCalculator.addBasicFactor("GDuty", aBqCalBase.getGDuty());
		aCalculator.addBasicFactor("PolNo", aBqCalBase.getPolNo());
		aCalculator.addBasicFactor("EdorNo", aBqCalBase.getEdorNo());
		aCalculator.addBasicFactor("EdorType", aBqCalBase.getEdorType());
		aCalculator
				.addBasicFactor("EdorValiDate", aBqCalBase.getEdorValiDate());
		aCalculator.addBasicFactor("EdorAppDate", aBqCalBase.getEdorAppDate());
		aCalculator.addBasicFactor("GetBalance", aBqCalBase.getGetBalance());
		aCalculator.addBasicFactor("GetMoney", aBqCalBase.getGetMoney());
		aCalculator.addBasicFactor("InsuYear", aBqCalBase.getInsuYear());
		aCalculator
				.addBasicFactor("InsuYearFlag", aBqCalBase.getInsuYearFlag());
		aCalculator
				.addBasicFactor("GetStartYear", aBqCalBase.getGetStartYear());
		aCalculator
				.addBasicFactor("StandbyFlag1", aBqCalBase.getStandByFlag1());
		aCalculator.addBasicFactor("RemainYear", aBqCalBase.getRemainYear());
		aCalculator.addBasicFactor("StartDate", aBqCalBase.getStartDate());
		aCalculator.addBasicFactor("NSMonths", aBqCalBase.getNSMonths());
		aCalculator.addBasicFactor("BonusRate", aBqCalBase.getBonusRate());
		aCalculator.addBasicFactor("ForceDVFlag", aBqCalBase.getForceDVFlag());
		aCalculator.addBasicFactor("AAYears", aBqCalBase.getAAYears());
		aCalculator.addBasicFactor("InsuYearT1", aBqCalBase.getInsuYearT1());
		aCalculator.addBasicFactor("InsuYearT2", aBqCalBase.getInsuYearT2());
		aCalculator.addBasicFactor("IntvMonth", aBqCalBase.getIntvMonth());
		aCalculator.addBasicFactor("GrpPolNo", aBqCalBase.getGrpPolNo());
		return true;
	}

	// =====ADD=====zhangtao=======2005-06-01========================BGN=============
	/**
	 * 初始化计算要素
	 * 
	 * @param tCalculator
	 * @param tBqCalBase
	 * @return boolean
	 */
	private boolean initBasicFactor(Calculator tCalculator, BqCalBase tBqCalBase) {
		Class obj = tBqCalBase.getClass();
		Field[] Fields = obj.getDeclaredFields();
		String sFieldName;
		String sFieldValue;
		for (int i = 0; i < Fields.length; i++) {
			sFieldName = Fields[i].getName();
			// logger.debug("==FieldName =="+ i + " " + sFieldName);
			try {
				Method method = obj.getMethod("get" + sFieldName, null);
				sFieldValue = (String) method.invoke(tBqCalBase, null);
				tCalculator.addBasicFactor(sFieldName, sFieldValue);
			} catch (Exception ex) {
				logger.debug("NoSuchMeThod");
				CError tError = new CError();
				tError.errorMessage = "计算要素初始化失败!" + ex.toString();
				mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 计算保单现金价值
	 * 
	 * @param aLPEdorItemSchema
	 * @param tLPPolSchema
	 * @return double
	 */
	public static double calCashValue(LPEdorItemSchema aLPEdorItemSchema,
			LPPolSchema tLPPolSchema) {
		logger.debug("Start Get Cash Value...");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLCPolSchema, tLPPolSchema);

		BqCalBase tBqCalBase = new BqCalBase();

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setSchema(tLCPolSchema);

		// 得到交费年期
		tBqCalBase.setPayEndYear(tLCPolDB.getPayEndYear());

		// 退保年度
		tBqCalBase.setInterval(PubFun.calInterval(tLCPolDB.getCValiDate(),
				aLPEdorItemSchema.getEdorValiDate(), "Y"));
		tBqCalBase.setYears(tLCPolDB.getYears());

		// 得到投保年龄，性别
		LPInsuredBL tLPInsuredBL = new LPInsuredBL();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setInsuredNo(tLCPolDB.getInsuredNo());
		tLPInsuredSchema.setContNo(aLPEdorItemSchema.getContNo());
		tLPInsuredBL.queryLastLPInsured(aLPEdorItemSchema, tLPInsuredSchema);

		tBqCalBase.setAppAge(PubFun.calInterval(tLPInsuredBL.getBirthday(),
				tLCPolDB.getCValiDate(), "Y"));
		tBqCalBase.setSex(tLPInsuredBL.getSex());

		tBqCalBase.setCValiDate(aLPEdorItemSchema.getEdorValiDate());
		tBqCalBase.setPolNo(tLCPolDB.getPolNo());
		tBqCalBase.setEdorValiDate(aLPEdorItemSchema.getEdorValiDate());
		tBqCalBase.setMult(tLCPolDB.getMult());
		tBqCalBase.setPayEndYearFlag(tLCPolDB.getPayEndYearFlag());
		tBqCalBase.setGetStartFlag(tLCPolDB.getGetYearFlag());
		tBqCalBase.setPrem(tLCPolDB.getPrem());
		tBqCalBase.setGet(tLCPolDB.getAmnt());
		tBqCalBase.setPayIntv(tLCPolDB.getPayIntv());

		tBqCalBase.setInsuYear(tLCPolDB.getInsuYear()); // 保险年龄年期
		tBqCalBase.setInsuYearFlag(tLCPolDB.getInsuYearFlag()); // 保险年龄年期标志
		tBqCalBase.setGetStartYear(tLCPolDB.getGetYear()); // 领取年龄年期

		double aCashValue = 0;
		String tCalCode = "";

		LMEdorZT1Set tLMEdorZT1Set;
		LMEdorZT1DB tLMEdorZT1DB = new LMEdorZT1DB();
		tLMEdorZT1DB.setRiskCode(tLCPolDB.getRiskCode());
		tLMEdorZT1Set = tLMEdorZT1DB.query();

		if (tLMEdorZT1Set == null || tLMEdorZT1Set.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "BqCalBL";
			tError.functionName = "calCashValue";
			tError.errorMessage = "缺少险种现金价值描述!";
			return 0;
		}

		// for (int j = 1; j <= tLMEdorZT1Set.size(); j++)
		// {
		// if ((tLMEdorZT1Set.get(j).getCashValueCode() != null)
		// && !tLMEdorZT1Set.get(j).getCashValueCode().equals(""))
		// {
		// tCalCode = tLMEdorZT1Set.get(j).getCashValueCode();
		//
		// if ((tCalCode == null) || tCalCode.trim().equals(""))
		// {
		// CError tError = new CError();
		// tError.moduleName = "PEdorGADetailBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "缺少险种现金价值描述编码!";
		// return 0;
		// }
		//
		// BqCalBL tBqCalBL = new BqCalBL(tBqCalBase, tCalCode, "");
		// aCashValue = aCashValue
		// + tBqCalBL.calGetEndorse(tCalCode, tBqCalBase);
		// if (tBqCalBL.mErrors.needDealError())
		// {
		// CError tError = new CError();
		// tError.moduleName = "PEdorGADetailBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "计算保单现金价值失败!";
		// return 0;
		// }
		//
		// }
		// }

		logger.debug("CashValue:" + aCashValue + "\nEnd Get Cash Value");
		return aCashValue;
	}

	/**
	 * 执行保全项目明细变更保存校验
	 * 
	 * @return boolean
	 */
	public boolean calEdorItemDetailValidate() {
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(mCalCode);
		// 增加计算要素
		initBasicFactor(tCalculator, mBqCalBase);
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		String sResult = tCalculator.calculate();

		if (sResult != null && sResult.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	// =====ADD=====zhangtao=======2005-06-01========================END=============

	// =====ADD=====zhangtao=======2006-01-16=====更新续期应收公用方法========BGN=======
	/**
	 * 更新续期应收
	 * 
	 * @param sUpdFlag
	 *            更新标志（C-保单 P-险种）
	 * @param pLPPremSet
	 *            传入新的保费标准（sUpdFlag-C 传入整个保单的保费项；sUpdFlag-P 传入险种的保费项）
	 * @return MMap
	 */
	public MMap updLJSPay(String sUpdFlag, LPPremSet pLPPremSet) {
		MMap rMMap = new MMap();

		if (pLPPremSet == null || pLPPremSet.size() < 1) {
			CError.buildErr(this, "传入保费项为空!");
			return null;
		}
		String sContNo = pLPPremSet.get(1).getContNo();
		String sPolNo = pLPPremSet.get(1).getPolNo();
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String selSQL = " select * from ljspay where othernotype in ('2','3') "
				+ " and otherno = '" + "?sContNo?" + "' ";
		sqlbv.sql(selSQL);
		sqlbv.put("sContNo", sContNo);
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);
		if (tLJSPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "续期应收信息查询失败!");
			return null;
		}
		if (tLJSPaySet != null && tLJSPaySet.size() > 0) // 有续期应收
		{
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setGetNoticeNo(tLJSPaySet.get(1).getGetNoticeNo());
			if (sUpdFlag.equals("P")) // 只更新某险种的保费项
			{
				tLJSPayPersonDB.setPolNo(sPolNo);
			}
			LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();
			if (tLJSPayPersonDB.mErrors.needDealError()) {
				CError.buildErr(this, "续期应收信息查询失败!");
				return null;
			}
			if (tLJSPayPersonSet == null || tLJSPayPersonSet.size() < 1) {
				// 该险种没有续期应收明细需要更新
				return rMMap;
			}

			double dSumChgPrem = 0.0; // 保费变动值
			double dPrem = 0.0; // 保费（保费项表）
			double dPayMoney = 0.0; // 应收保费（续期应收分表）
	          //营改增 add zhangyingfeng 2016-07-14
	          //净额 税额 税率
			double dPayNetAm = 0.0;    //净额分表
			double dSumNetAm = 0.0;  //净额变动值
			double dNetAm = 0.0;   //净额
			double dPayTaxAm = 0.0;    //税额分表
			double dSumTaxAm = 0.0;  //税额变动值
			double dTaxAm = 0.0;   //税额
			double tax=0.0;   //税率
	          //end zhangyingfeng 2016-07-14
			String sPolNo_Prem; // 险种号（保费项表）
			String sDutyCode_Prem; // 责任编码（保费项表）
			String sPayPlanCode_Prem; // 交费计划编码（保费项表）
			String sPolNo_Pay; // 险种号（续期应收分表）
			String sDutyCode_Pay; // 责任编码（续期应收分表）
			String sPayPlanCode_Pay; // 交费计划编码（续期应收分表）
			String sPayStartDate; // 交费期起（保费项表）
			String sPayEndDate; // 交费止期（保费项表）
			String sLastPayToDate; // 上次交至日期（续期应收分表）
			String sCurPayToDate; // 本次交至日期（续期应收分表）
			LJSPayPersonSet delLJSPayPersonSet = new LJSPayPersonSet(); // 需要删除的续期加费
			LJSPayPersonSet insLJSPayPersonSet = new LJSPayPersonSet(); // 需要新增的续期加费
			LJSPayPersonSet updLJSPayPersonSet = new LJSPayPersonSet(); // 需要更新的续期加费
			for (int j = 1; j <= pLPPremSet.size(); j++) {
				sPayStartDate = pLPPremSet.get(j).getPayStartDate();
				sPayEndDate = pLPPremSet.get(j).getPayEndDate();
				sLastPayToDate = tLJSPayPersonSet.get(1).getLastPayToDate();
				sCurPayToDate = tLJSPayPersonSet.get(1).getCurPayToDate();
				boolean blIsCurPrem = isCurPrem(sPayStartDate, sPayEndDate,
						sLastPayToDate, sCurPayToDate);
				if (!blIsCurPrem) // 该保费项本期无效
				{
					continue;
				}
				sPolNo_Prem = pLPPremSet.get(j).getPolNo();
				sDutyCode_Prem = pLPPremSet.get(j).getDutyCode();
				sPayPlanCode_Prem = pLPPremSet.get(j).getPayPlanCode();
				dPrem = pLPPremSet.get(j).getPrem();
				dPayMoney = -1;
				int iUpdIndex = 1;
				for (int k = 1; k <= tLJSPayPersonSet.size(); k++) {
					sPolNo_Pay = tLJSPayPersonSet.get(k).getPolNo();
					sDutyCode_Pay = tLJSPayPersonSet.get(k).getDutyCode();
					sPayPlanCode_Pay = tLJSPayPersonSet.get(k).getPayPlanCode();
					if (sPolNo_Prem.equals(sPolNo_Pay)
							&& sDutyCode_Prem.equals(sDutyCode_Pay)
							&& sPayPlanCode_Prem.equals(sPayPlanCode_Pay)) {
						dPayMoney = tLJSPayPersonSet.get(k).getSumDuePayMoney();
				          //营改增 add zhangyingfeng 2016-07-14
				          //净额 税额 税率
						dPayNetAm = tLJSPayPersonSet.get(k).getNetAmount();   //净额
						dPayTaxAm = tLJSPayPersonSet.get(k).getTaxAmount();    //税额
						tax = tLJSPayPersonSet.get(k).getTax();    //税率  
				          //end zhangyingfeng 2016-07-14
						iUpdIndex = k;
						break;
					}
				}
				if (dPayMoney == -1) // 新增加费
				{
					// 根据PolNo查出RiskCode
					LCPolDB tLCPolDB = new LCPolDB();
					tLCPolDB.setPolNo(pLPPremSet.get(j).getPolNo());
					LCPolSet riskLCPolSet = tLCPolDB.query();
					if (tLCPolDB.mErrors.needDealError()) {
						CError.buildErr(this, "险种信息查询失败!");
						return null;
					}
					if (riskLCPolSet == null || riskLCPolSet.size() < 1) {
						// 该保费项在险种表里没有对应的险种号(续期抽档撤销漏删遗留下的垃圾数据)
						// DoNothing
					} else {
						String sRiskCode = riskLCPolSet.get(1).getRiskCode();

						dSumChgPrem += dPrem;
						Reflections tRef = new Reflections();
						LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
						tRef.transFields(tLJSPayPersonSchema, tLJSPayPersonSet
								.get(1));

						tLJSPayPersonSchema.setPolNo(pLPPremSet.get(j)
								.getPolNo());
						tLJSPayPersonSchema.setRiskCode(sRiskCode);
						tLJSPayPersonSchema.setDutyCode(pLPPremSet.get(j)
								.getDutyCode());
						tLJSPayPersonSchema.setPayPlanCode(pLPPremSet.get(j)
								.getPayPlanCode());
						tLJSPayPersonSchema.setSumDuePayMoney(dPrem);
						tLJSPayPersonSchema.setSumActuPayMoney(dPrem);
						tLJSPayPersonSchema.setOperator("bqOperator");
						tLJSPayPersonSchema
								.setMakeDate(PubFun.getCurrentDate());
						tLJSPayPersonSchema
								.setMakeTime(PubFun.getCurrentTime());
						tLJSPayPersonSchema.setModifyDate(PubFun
								.getCurrentDate());
						tLJSPayPersonSchema.setModifyTime(PubFun
								.getCurrentTime());
				          //营改增 add zhangyingfeng 2016-07-14
				          //价税分离 计算器
				          TaxCalculator.calBySchema(tLJSPayPersonSchema);
				          //借鉴  dSumChgPrem += dPrem;   取净额 税额 变动值
				          dSumNetAm+=tLJSPayPersonSchema.getNetAmount(); 
				          dSumTaxAm+=tLJSPayPersonSchema.getTaxAmount();   
				          tax=tLJSPayPersonSchema.getTax();   
				          //end zhangyingfeng 2016-07-14
						insLJSPayPersonSet.add(tLJSPayPersonSchema);
					}
				} else if (dPrem != dPayMoney) // 计算保费变化
				{
					dSumChgPrem += dPrem - dPayMoney;
					tLJSPayPersonSet.get(iUpdIndex).setSumDuePayMoney(dPrem);
					tLJSPayPersonSet.get(iUpdIndex).setSumActuPayMoney(dPrem);
			          //营改增 add zhangyingfeng 2016-07-14
			          //价税分离 计算器
			          TaxCalculator.calBySchema(tLJSPayPersonSet.get(iUpdIndex));
			          //借鉴  dSumChgPrem += dPrem - dPayMoney;   取净额 税额 变动值
			          dSumNetAm+=tLJSPayPersonSet.get(iUpdIndex).getNetAmount()-dPayNetAm; 
			          dSumTaxAm+=tLJSPayPersonSet.get(iUpdIndex).getTaxAmount()-dPayTaxAm;  
			          tax=tLJSPayPersonSet.get(iUpdIndex).getTax();
			          //end zhangyingfeng 2016-07-14
					updLJSPayPersonSet.add(tLJSPayPersonSet.get(iUpdIndex));
				}
			}

			for (int j = 1; j <= tLJSPayPersonSet.size(); j++) {
				sLastPayToDate = tLJSPayPersonSet.get(j).getLastPayToDate();
				sCurPayToDate = tLJSPayPersonSet.get(j).getCurPayToDate();
				sPolNo_Pay = tLJSPayPersonSet.get(j).getPolNo();
				sDutyCode_Pay = tLJSPayPersonSet.get(j).getDutyCode();
				sPayPlanCode_Pay = tLJSPayPersonSet.get(j).getPayPlanCode();
				dPayMoney = tLJSPayPersonSet.get(j).getSumDuePayMoney();
		          //营改增 add zhangyingfeng 2016-07-14
		          //借鉴  dPayMoney = tLJSPayPersonSet.get(j).getSumDuePayMoney();   取净额 税额 变动值
		          dPayNetAm=tLJSPayPersonSet.get(j).getNetAmount(); 
		          dPayTaxAm=tLJSPayPersonSet.get(j).getTaxAmount();   
		          //end zhangyingfeng 2016-07-14
				dPrem = -1;
				for (int k = 1; k <= pLPPremSet.size(); k++) {
					sPayStartDate = pLPPremSet.get(k).getPayStartDate();
					sPayEndDate = pLPPremSet.get(k).getPayEndDate();
					boolean blIsCurPrem = isCurPrem(sPayStartDate, sPayEndDate,
							sLastPayToDate, sCurPayToDate);
					if (!blIsCurPrem) // 该保费项本期无效
					{
						continue;
					}
					sPolNo_Prem = pLPPremSet.get(k).getPolNo();
					sDutyCode_Prem = pLPPremSet.get(k).getDutyCode();
					sPayPlanCode_Prem = pLPPremSet.get(k).getPayPlanCode();
					if (sPolNo_Prem.equals(sPolNo_Pay)
							&& sDutyCode_Prem.equals(sDutyCode_Pay)
							&& sPayPlanCode_Prem.equals(sPayPlanCode_Pay)) {
						dPrem = pLPPremSet.get(k).getPrem();
						break;
					}
				}
				if (dPrem == -1) // 删除加费
				{
					dSumChgPrem -= dPayMoney;
					delLJSPayPersonSet.add(tLJSPayPersonSet.get(j));
			          //营改增 add zhangyingfeng 2016-07-14
			          //借鉴  dSumChgPrem -= dPayMoney;   取净额 税额 变动值
			          dSumNetAm-=dPayNetAm; 
			          dSumTaxAm-=dPayTaxAm;   
			          tax=tLJSPayPersonSet.get(j).getTax();
			          //end zhangyingfeng 2016-07-14
				}
			}

			if (insLJSPayPersonSet != null && insLJSPayPersonSet.size() > 0) {
				rMMap.put(insLJSPayPersonSet, "INSERT");
			}
			if (updLJSPayPersonSet != null && updLJSPayPersonSet.size() > 0) {
				rMMap.put(updLJSPayPersonSet, "UPDATE");
			}
			if (delLJSPayPersonSet != null && delLJSPayPersonSet.size() > 0) {
				rMMap.put(delLJSPayPersonSet, "DELETE");
			}

			if (dSumChgPrem != 0) // 更新应收总表
			{
				double dNewPayMoney = tLJSPaySet.get(1).getSumDuePayMoney()
						+ dSumChgPrem;
				tLJSPaySet.get(1).setSumDuePayMoney(dNewPayMoney);
		          //营改增 add zhangyingfeng 2016-07-14
		          //借鉴上面 更新 税额 税率
				double dNewNetAm=tLJSPaySet.get(1).getNetAmount()+dSumNetAm;
				double dNewTaxAm=tLJSPaySet.get(1).getNetAmount()+dSumNetAm;
				tLJSPaySet.get(1).setNetAmount(dNewNetAm);
				tLJSPaySet.get(1).setTaxAmount(dNewTaxAm);
				tLJSPaySet.get(1).setTax(tax);   //不能统一 暂取一条
		          //end zhangyingfeng 2016-07-14
				if (dNewPayMoney == 0) {
					rMMap.put(tLJSPaySet.get(1), "DELETE");
				} else {
					rMMap.put(tLJSPaySet.get(1), "UPDATE");
				}
			}
		}

		return rMMap;
	}

	/**
	 * 判断该保费项是否是本期交费标准
	 * 
	 * @param sPayStartDate
	 *            交费期起
	 * @param sPayEndDate
	 *            交费止期
	 * @param sLastPayToDate
	 *            上次交至日期
	 * @param sCurPayToDate
	 *            本次交至日期
	 * @return boolean
	 */
	private boolean isCurPrem(String sPayStartDate, String sPayEndDate,
			String sLastPayToDate, String sCurPayToDate) {
		int iIntvStart = PubFun.calInterval(sPayStartDate, sLastPayToDate, "D");
		int iIntvEnd = PubFun.calInterval(sPayEndDate, sLastPayToDate, "D");
		if (iIntvStart >= 0 && iIntvEnd < 0) {
			return true;
		} else {
			return false;
		}
	}

	// =====ADD=====zhangtao=======2006-01-16=====更新续期应收公用方法========END=======

	/**
	 * 测试函数
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		// =====ADD=====zhangtao=======2006-01-16====更新续期应收测试======BGN=============
		LPPremSet tLPPremSet = new LPPremSet();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		tLPPremSet.add(tLPPremSchema);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = " select * from lcprem where polno = '210210000084579' "
				+ " order by payplancode";
		sqlbv.sql(sql);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		Reflections tRef = new Reflections();
		tRef.transFields(tLPPremSet, tLCPremSet);
		// tLPPremSchema = new LPPremSchema();
		// tRef.transFields(tLPPremSchema, tLPPremSet.get(1));
		// tLPPremSchema.setPayPlanCode("00000002");
		// tLPPremSchema.setPrem(521);
		// tLPPremSet.add(tLPPremSchema);
		// tLPPremSet.get(2).setPrem(tLPPremSet.get(2).getPrem() + 1314);
		// tLPPremSet.remove(tLPPremSet.get(1));

		BqCalBL tBqCalBL = new BqCalBL();
		MMap tMMap = tBqCalBL.updLJSPay("P", tLPPremSet);
		if (tMMap == null) {
			logger.debug(tBqCalBL.mErrors.getFirstError());
		}
		VData tVData = new VData();
		tVData.add(tMMap);
		PubSubmit tSubmit = new PubSubmit();
		tSubmit.submitData(tVData, "");

		// =====ADD=====zhangtao=======2006-01-16====更新续期应收测试======END=============

	}
}
