package com.sinosoft.lis.bq;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

//import utils.system;

import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.lis.claim.LLClaimExternalInterfaceBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCPremToAccDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMEdorBonusZTDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMEdorZT1DB;
import com.sinosoft.lis.db.LMEdorZTDutyDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LMRiskPayDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCPremToAccSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LMEdorBonusZTSchema;
import com.sinosoft.lis.schema.LMEdorZT1Schema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCPremToAccSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMEdorBonusZTSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMEdorZT1Set;
import com.sinosoft.lis.vschema.LMEdorZTDutySet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.lis.vschema.LMRiskPaySet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * 保全退保计算类.
 * <p>
 * Title: 保全退保计算类
 * </p>
 * <p>
 * Description: 通过传入的保单信息和保费项目信息计算出交退费变动信息，或保额变动信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 * @CreateDate：2005-06-28
 */
public class EdorCalZT {
	private static Logger logger = Logger.getLogger(EdorCalZT.class);
	public EdorCalZT() {
	}

	// @Field
	// private final int Year = 365;
	// private final int Month = 30;
	// private final int CriticalYearInterval = 2;
	// ==================new===zhangtao===========================BGN================
	private String mPolNo = ""; // 保单号

	private String mCValiDate_Pol = ""; // 险种生效日期

	private String mEndDate_Pol = ""; // 险种终止日期

	private String mCValiDate = ""; // 保单生效日期

	private int mInterval = 0; // 保单年度

	private String mEdorAppDate = ""; // 退保申请日期

	private String mZTPoint = ""; // 退保时点

	private String mSumPremFlag = ""; // 累计保费计算标志（如果以责任项为入口计算现金价值，则累计保费直接用传入值）

	private String mCTType = ""; // 退保类型 1-犹豫期内 2-犹豫期外

	private String mCalDateType = ""; // 计算时点类型 (LP-理赔、CF-催付 影响累计红利计算时点)
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private boolean mFinaBonusFlag = true; // 计算终了红利时有效保额是否包含累计红利保额标志（293、294理赔计算时特殊情况）

	private boolean mNextYearFlag = false; // 下一年度分红标记（不用判断红利是否公布以及是否已到红利公布日期）

	private double mBaseCashValue = 0.0; // 基本保额现金价值

	private double mBonusCashValue = 0.0; // 累计红利保额现金价值

	private double mTBFee = 0.0; // 万能险退保手续费

	private double mAddPremH = 0.0; // 健康加费应退金额

	private double mAddPremO = 0.0; // 职业加费应退金额

	private double mLoanCorpus = 0.0; // 贷款未清偿本金

	private double mLoanInterest = 0.0; // 贷款未清偿利息

	private double mAutoPayPrem = 0.0; // 自垫未清偿本金

	private double mAutoPayPremInterest = 0.0; // 自垫未清偿利息

	private boolean mHasDisPatched = false; // 强制分红（只能调一次）

	private double mDisPatchBonus = 0.0; // 强制分红结果

	private double mInBonusAmnt = -1; // 计算红利保额现价时，可以采用累计红利保额传入的方式

	/**
	 * 退保算法选择标记，依据保单的生效日进行判断，时间指定点见BQCode的BQ_CT_SPecDate 1--新算法 0--旧算法
	 */
	private String tCTCodeFLag = "";

	private VData mResult = new VData();

	public CErrors mErrors = new CErrors(); // 错误信息

	private GlobalInput mGlobalInput = new GlobalInput();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LPPolSchema mLPPolSchema = null; // 由外层调用传入,不能从C表中查询得到-

	// 要件变更重算累计红利保额现价时用到 add by
	// zhangtao 2006-09-13

	private LPDutySet mLPDutySet = null; // 由外层调用传入,不能从C表中查询得到-

	// 要件变更重算累计红利保额现价时用到 add by zhangtao
	// 2006-09-13

	public LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();// 批改补退费记录

	private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");

	private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

	private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();

	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	private List mBomList = new ArrayList();

	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();

	private boolean mBomListFlag = false;
	private BqCalBase mBqCalBase = new BqCalBase();

	private LCContSchema mLCContSchema = new LCContSchema();

	// ==================new===zhangtao===========================END================
	// private BqCalBase mBqCalBase = new BqCalBase();
	//
	//
	// private LPPolSchema mLPPolSchema = new LPPolSchema();
	// public LPDutySchema mLPDutySchema = null;
	// public LPPremSchema mLPPremSchema = null;
	//
	// public LMEdorZTAccSchema mLMEdorZTAccSchema = null;
	// public LPInsureAccSchema mLPInsureAccSchema = null;

	//
	//
	// //ZTFlag－－'1' ：生存退保；'2' ：死亡退保；'3' ：外部转移；
	// private String mZTType = "";
	//
	// //退保计算类型，是按保费还是保额计算－－'0'：按保费；'1'：按保额；'2'：按帐户
	// private String mCalType = "";
	//
	// //退保保单年度
	// private int mZTYear = 0;
	//
	// //计算用保单交至日期
	// private String mPayToDate = "";
	//
	// //计算用保单交费间隔
	// private int mPayIntv = 0;
	//
	// //计算用保单交费间隔
	// private String mPolCValiDate = "";
	//
	// //计算用保单交费间隔
	// private String mRiskCode = "";
	//
	// //计算用退保点距保单生效对应日的天数
	// private int mZTDays = 0;
	//
	// //计算要素免交保费
	// private double mOwePrem = 0;
	//
	// //计算要素预交保费
	// private double mPrepayPrem = 0;
	//
	// //计算要素未经过保费
	// private double mNetPrem = 0;
	//
	// //计算要素垫交保费
	// private double mTrayPrem = 0;
	//
	// //计算要素垫交保费利息
	// private double mTrayPremInterest = 0;
	//
	// //计算要素当年应交未交保费
	// private double mUnfillPrem = 0;
	//
	// //年初生存金
	// private double mGetAliveBegin = 0;
	//
	// //年末生存金
	// private double mGetAliveEnd = 0;
	//
	// //年初现金价值
	// private double mCashValueBegin = 0;
	//
	// //年末现金价值
	// private double mCashValueEnd = 0;
	//
	// //退保金额
	// private double mTBMoney = 0;
	//
	// //退费金额（预交保费）
	// private double mTFMoney = 0;
	//
	// //退帐户金额
	// private double mAccTBMoney = 0;
	//
	// //计算次数
	// private int mCalTimes = 0;
	//
	// //计算比率
	// private double mCalRate = 0;
	//
	// //借款金额
	// private double mLoanMoney = 0;
	//
	// //保全生效日
	// private String mEdorValidate = "";

	public EdorCalZT(GlobalInput tGlobalInput) {
		mGlobalInput = tGlobalInput;
	}

	/**
	 * 保全信息接口
	 * 
	 * @param aLPEdorItemSchema
	 */
	public void setEdorInfo(LPEdorItemSchema aLPEdorItemSchema) {
		mLPEdorItemSchema.setSchema(aLPEdorItemSchema);
	}

	public LPEdorItemSchema getEdorInfo() {
		return mLPEdorItemSchema;
	}

	// public void setZTPoint(String aZTPoint)
	// {
	// mZTPoint = aZTPoint;
	// }
	//
	// public String getZTPoint()
	// {
	// return mZTPoint;
	// }
	//
	// public void setEdorValidate(String aEdorValidate)
	// {
	// mEdorValidate = aEdorValidate;
	// }
	//
	// public String getEdorValidate()
	// {
	// return mEdorValidate;
	// }
	//
	// public void setCalType(String aCalType)
	// {
	// mCalType = aCalType;
	// }
	//
	// public String getCalType()
	// {
	// return mCalType;
	// }
	//
	// private void setPayToDate(String aPayToDate)
	// {
	// mPayToDate = aPayToDate;
	// }
	//
	// private String getPayToDate()
	// {
	// return mPayToDate;
	// }
	//
	// private void setZTDays(int aZTDays)
	// {
	// mZTDays = aZTDays;
	// }
	//
	// private int getZTDays()
	// {
	// return mZTDays;
	// }
	//
	// private void setOwePrem(double aOwePrem)
	// {
	// mOwePrem = aOwePrem;
	// }
	//
	// private double getOwePrem()
	// {
	// return mOwePrem;
	// }
	//
	// private void setPrepayPrem(double aPrepayPrem)
	// {
	// mPrepayPrem = aPrepayPrem;
	// }
	//
	// private double getPrepayPrem()
	// {
	// return mPrepayPrem;
	// }
	//
	// private void setNetPrem(double aNetPrem)
	// {
	// mNetPrem = aNetPrem;
	// }
	//
	// private double getNetPrem()
	// {
	// return mNetPrem;
	// }
	//
	// private void setTBMoney(double aTBMoney)
	// {
	// mTBMoney = aTBMoney;
	// }
	//
	// public double getTBMoney()
	// {
	// return mTBMoney;
	// }
	//
	// private void setTFMoney(double aTFMoney)
	// {
	// mTFMoney = aTFMoney;
	// }
	//
	// public double getTFMoney()
	// {
	// return mTFMoney;
	// }
	//
	// private void setAccTBMoney(double aAccTBMoney)
	// {
	// mAccTBMoney = aAccTBMoney;
	// }
	//
	// public double getAccTBMoney()
	// {
	// return mAccTBMoney;
	// }
	//
	// private void setTrayPrem(double aTrayPrem)
	// {
	// mTrayPrem = aTrayPrem;
	// }
	//
	// public double getTrayPrem()
	// {
	// return mTrayPrem;
	// }
	//
	// private void setTrayPremInterest(double aTrayPremInterest)
	// {
	// mTrayPremInterest = aTrayPremInterest;
	// }
	//
	// public double getTrayPremInterest()
	// {
	// return mTrayPremInterest;
	// }
	//
	// private void setUnfillPrem(double aUnfillPrem)
	// {
	// mUnfillPrem = aUnfillPrem;
	// }
	//
	// public double getUnfillPrem()
	// {
	// return mUnfillPrem;
	// }
	//
	// private void setZTYear(int aZTYear)
	// {
	// mZTYear = aZTYear;
	// }
	//
	// public int getZTYear()
	// {
	// return mZTYear;
	// }
	//
	// /**
	// * 计算退保点，交至日期，欠交保费，退保年度
	// * @param pLPEdorItemSchema
	// * @param pLPDutySchema
	// */
	// public void calZTPoint()
	// {
	// //按保额
	// if (mCalType.equals("1"))
	// {
	// getZTPoint(mLPEdorItemSchema.getEdorValiDate(),
	// mLPPolSchema.getCValiDate(), mLPDutySchema.getPaytoDate(),
	// mLPDutySchema.getPayEndDate(), mLPPolSchema.getRiskCode(),
	// mLPDutySchema.getPayIntv(), mLPDutySchema.getPrem(),
	// mLPDutySchema.getFreeFlag(), mLPDutySchema.getFreeRate());
	// }
	//
	// //按保费
	// else if (mCalType.equals("0"))
	// {
	// getZTPoint(mLPEdorItemSchema.getEdorValiDate(),
	// mLPPolSchema.getCValiDate(), mLPPremSchema.getPaytoDate(),
	// mLPPremSchema.getPayEndDate(), mLPPolSchema.getRiskCode(),
	// mLPPremSchema.getPayIntv(), mLPPremSchema.getPrem(),
	// mLPDutySchema.getFreeFlag(), mLPDutySchema.getFreeRate());
	// }
	// }
	//
	// // /**
	// // * 计算退保点，交至日期，欠交保费，退保年度，在自动垫交（AutoPay）中调用，所有用LCPol表数据
	// // * @param pPolNo
	// // * @param pEdorValiDate
	// // * @return
	// // */
	// // public String getZTPoint(String pPolNo, String pEdorValiDate)
	// // {
	// // //获取保单信息
	// // LCPolDB tLCPolDB = new LCPolDB();
	// // tLCPolDB.setPolNo(pPolNo);
	// //
	// // if (!tLCPolDB.getInfo())
	// // {
	// // CError.buildErr(this, "获取保单信息失败");
	// // }
	// //
	// // getZTPoint(pEdorValiDate, tLCPolDB.getCValiDate(),
	// // tLCPolDB.getPaytoDate(), tLCPolDB.getPayEndDate(),
	// // tLCPolDB.getRiskCode(), tLCPolDB.getPayIntv(), tLCPolDB.getPrem(),
	// // "",0);
	// //// 暂时注掉 by PQ tLCPolDB.getFreeFlag(), tLCPolDB.getFreeRate());
	// //
	// // return this.getZTPoint();
	// // }
	//
	// /**
	// * 计算退保点，交至日期，欠交保费，退保年度，退保点距保单生效对应日的天数
	// * @param pEdorValiDate 原保全生效日
	// * @param pPolCValiDate 保单生效日
	// * @param pPayToDate 原交至日期
	// * @param pPayEndDate 交费止期
	// * @param pRiskCode 险种代码
	// * @param pPayIntv 交费间隔
	// * @param pPrem 保费
	// * @param pFreeFlag 免交标记
	// * @param pFreeRate 免交比例
	// */
	// public void getZTPoint(String pEdorValiDate, String pPolCValiDate,
	// String pPayToDate, String pPayEndDate, String pRiskCode, int pPayIntv,
	// double pPrem, String pFreeFlag, double pFreeRate)
	// {
	// try
	// {
	// FDate tFDate = new FDate();
	//
	// //宽限期止期
	// String tLapseDate = CalLapseDate(pRiskCode, pPayToDate);
	//
	// //责任终止期（失效日期）= 宽限期止期 + 1天
	// String tEndDate = transCalDate(tLapseDate, 1, "D", null);
	// logger.debug("责任终止期（失效日期）:" + tEndDate);
	// logger.debug("交至日期:" + pPayToDate);
	// logger.debug("交费间隔:" + pPayIntv);
	//
	// //计算退保开始年度（批改生效日－保单生效日）
	// int tZTYear = PubFun.calInterval(pPolCValiDate, pEdorValiDate, "Y");
	// logger.debug("退保开始年度:" + tZTYear);
	//
	// //趸交和满期处理，退保点为保全生效日
	// if ((pPayIntv == 0) || (pPayIntv == -1)
	// || pPayToDate.equals(pPayEndDate))
	// {
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(pEdorValiDate);
	// this.setOwePrem(0);
	// logger.debug("满期退保");
	// }
	//
	// //两年内退保
	// else if (tZTYear < CriticalYearInterval)
	// {
	// //1.保全生效日大于、等于交至日期，退保点为交至日期的前一天
	// if (pEdorValiDate.compareTo(pPayToDate) >= 0)
	// {
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(transCalDate(pPayToDate, -1, "D", null));
	// this.setOwePrem(0);
	// logger.debug("两年内保全生效日大于、等于交至日期退保");
	// }
	//
	// //2.保全生效日小于交至日期，退保点为保全生效日
	// else
	// {
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(pEdorValiDate);
	// this.setOwePrem(0);
	// logger.debug("两年内保全生效日小于交至日期退保");
	// }
	// }
	//
	// //两年以后退保
	// else
	// {
	// //交保费的年数，用于判断是否交足第三年保费
	// int payYear = PubFun.calInterval(pPolCValiDate, pPayToDate, "Y");
	//
	// //3.未交足第三年保费，退保点为交至日期的前一天
	// if (payYear < 3)
	// {
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(transCalDate(pPayToDate, -1, "D", null));
	// this.setOwePrem(0);
	// logger.debug("两年以后未交足第三年保费退保");
	// }
	//
	// //4.已交三年保费，保全生效日大于等于宽限止期
	// else if (pEdorValiDate.compareTo(tLapseDate) >= 0)
	// {
	// //交至日期小于等于宽限止期，补交N期保费至大于宽限止期，并记入免交保费
	// while (pPayToDate.compareTo(tLapseDate) <= 0)
	// {
	// pPayToDate = this.transCalDate(pPayToDate, pPayIntv,
	// "M", null);
	// this.setOwePrem(this.getOwePrem() + pPrem);
	// }
	//
	// //判断免交标志，1为免交，欠交保费要去除免交的部分
	// if ((pFreeFlag != null) && pFreeFlag.equals("1"))
	// {
	// this.setOwePrem(this.getOwePrem() * (1 - pFreeRate));
	// }
	//
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(tEndDate);
	// logger.debug("两年以后保全生效日大于等于宽限止期退保");
	// }
	//
	// //5.保全生效日大于交至日期，并且小于宽限止期
	// else if ((pEdorValiDate.compareTo(pPayToDate) > 0)
	// && (pEdorValiDate.compareTo(tLapseDate) < 0))
	// {
	// //交至日期小于等于保全生效日，补交N期保费至大于保全生效日，并记入免交保费
	// while (pPayToDate.compareTo(pEdorValiDate) <= 0)
	// {
	// pPayToDate = this.transCalDate(pPayToDate, pPayIntv,
	// "M", null);
	// this.setOwePrem(this.getOwePrem() + pPrem);
	// }
	//
	// //判断免交标志，1为免交，欠交保费要去除免交的部分
	// if ((pFreeFlag != null) && pFreeFlag.equals("1"))
	// {
	// this.setOwePrem(this.getOwePrem() * (1 - pFreeRate));
	// }
	//
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(pEdorValiDate);
	// logger.debug("两年以后保全生效日大于等于交至日，并且小于宽限止期退保");
	// }
	//
	// //6.保全生效日在交至日期当天
	// else if (pEdorValiDate.compareTo(pPayToDate) == 0)
	// {
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(pEdorValiDate);
	// this.setOwePrem(0);
	// logger.debug("两年以后保全生效日在交至日期当天退保");
	// }
	//
	// //其他正常退保日
	// else
	// {
	// this.setPayToDate(pPayToDate);
	// this.setZTPoint(pEdorValiDate);
	// this.setOwePrem(0);
	// }
	// }
	//
	// //用退保点来重新计算退保年度
	// tZTYear = PubFun.calInterval(pPolCValiDate, this.getZTPoint(), "Y");
	// this.setZTYear(tZTYear);
	// logger.debug("重新计算退保年度:" + mZTYear);
	//
	// //计算退保点距保单生效对应日的天数
	// this.setZTDays(PubFun.calInterval(this.transCalDate(pPolCValiDate,
	// PubFun.calInterval(pPolCValiDate, this.getZTPoint(), "Y"),
	// "Y", null), this.getZTPoint(), "D"));
	//
	// // 设置交费间隔
	// this.mPayIntv = pPayIntv;
	// }
	// catch (Exception ex)
	// {
	// CError.buildErr(this, "计算退保点失败");
	// }
	// }
	//
	// /**
	// * 计算当年应交未交保费
	// * @param pLPDutySchema
	// */
	// public void calUnfillPrem()
	// {
	// //按保额
	// if (mCalType.equals("1"))
	// {
	// getUnfillPrem(mLPDutySchema.getPaytoDate(),
	// mLPDutySchema.getPayIntv(), mLPDutySchema.getPrem(),
	// mLPPolSchema.getCValiDate(), this.getZTYear());
	// }
	//
	// //按保费
	// else if (mCalType.equals("0"))
	// {
	// getUnfillPrem(mLPPremSchema.getPaytoDate(),
	// mLPPremSchema.getPayIntv(), mLPPremSchema.getPrem(),
	// mLPPolSchema.getCValiDate(), this.getZTYear());
	// }
	// }
	//
	// /**
	// * 计算当年应交未交保费
	// * @param pPayToDate
	// * @param pPayIntv
	// * @param pPrem
	// * @param pPolCValiDate
	// * @param pZTYear 退保年度
	// */
	// public void getUnfillPrem(String pPayToDate, int pPayIntv, double pPrem,
	// String pPolCValiDate, int pZTYear)
	// {
	// //得到下一保险单周年日
	// FDate tFDate = new FDate();
	// String nextYear = transCalDate(pPolCValiDate, (pZTYear + 1), "Y", null);
	//
	// //计算当年应交未交保费的月数
	// int intvMonth = PubFun.calInterval(pPayToDate, nextYear, "M");
	//
	// //计算当年应交未交保费
	// double unfillPrem = 0.0;
	//
	// if ((pPayIntv != 0) && (pPayIntv != -1))
	// {
	// unfillPrem = intvMonth / pPayIntv * pPrem;
	// }
	//
	// this.setUnfillPrem(unfillPrem);
	// }
	//
	// /**
	// * 计算未经过保费
	// * @param pLPDutySchema
	// */
	// public void calNetPrem()
	// {
	// //按保额
	// if (mCalType.equals("1"))
	// {
	// getNetPrem(mLPDutySchema.getPaytoDate(),
	// mLPDutySchema.getPayIntv(), mLPDutySchema.getPrem(),
	// this.getZTPoint(), this.getZTYear());
	// }
	//
	// //按保费
	// else if (mCalType.equals("0"))
	// {
	// getNetPrem(mLPPremSchema.getPaytoDate(),
	// mLPPremSchema.getPayIntv(), mLPPremSchema.getPrem(),
	// this.getZTPoint(), this.getZTYear());
	// }
	// }
	//
	// /**
	// * 计算未经过保费，MS公式为：（12/交费间隔）×（没有预交的交至日期－退保生效日期）/365×每期保费×比例
	// * @param pPayToDate
	// * @param pPayIntv
	// * @param pPrem
	// */
	// public void getNetPrem(String pPayToDate, int pPayIntv, double pPrem,
	// String pZTPoint, int pZTYear)
	// {
	// try
	// {
	// //没有预交的交至日期－退保生效日期＝未经过保费天数
	// int tInterval = PubFun.calInterval(pZTPoint, pPayToDate, "D");
	// logger.debug("未经过保费天数:" + tInterval);
	//
	// if (tInterval > 0)
	// {
	// //获取折算比例
	// LMEdorNetDB tLMEdorNetDB = new LMEdorNetDB();
	// String strSql = "select * from LMEdorNet where StartYear<="
	// + pZTYear + " and EndYear>" + pZTYear;
	// LMEdorNetSet tLMEdorNetSet = tLMEdorNetDB.executeQuery(strSql);
	//
	// if (tLMEdorNetSet.size() == 0)
	// {
	// CError.buildErr(this, "获取未经过保费折算比例失败");
	// throw new Exception("获取未经过保费折算比例失败");
	// }
	//
	// //趸交和不定期交暂不处理
	// if ((pPayIntv != 0) && (pPayIntv != -1))
	// {
	// double netPrem = ((double) (12 / pPayIntv) * tInterval) / this.Year *
	// pPrem * tLMEdorNetSet.get(1)
	// .getNetRate();
	// this.setNetPrem(netPrem);
	// logger.debug("未经过保费:" + netPrem);
	// }
	// }
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// }
	// }
	//
	// /**
	// * 计算垫交保费（不同时段），现在未处理，因为退保入口控制了必须先还垫交和借款
	// * @return
	// */
	// public void calTrayPrem()
	// {
	// //计算垫交保费金额
	// this.setTrayPrem(0);
	//
	// //计算垫交保费利息
	// this.setTrayPremInterest(0);
	// }
	//
	// /**
	// * 准备计算要素，按保费
	// * @param pLPDutySchema
	// */
	// private void initBqCalBase(LPPremSchema pLPPremSchema)
	// {
	// mBqCalBase.setPayIntv(pLPPremSchema.getPayIntv());
	// mBqCalBase.setPrem(pLPPremSchema.getPrem());
	// mBqCalBase.setSumPrem(pLPPremSchema.getSumPrem());
	// mBqCalBase.setGetMoney(pLPPremSchema.getStandPrem());
	// }
	//
	// /**
	// * 准备计算要素，按保额
	// * @param pLPDutySchema
	// */
	// private void initBqCalBase(LPDutySchema pLPDutySchema)
	// {
	// mBqCalBase.setPayIntv(pLPDutySchema.getPayIntv());
	// mBqCalBase.setPayEndYear(pLPDutySchema.getPayEndYear());
	// mBqCalBase.setYears(pLPDutySchema.getYears());
	// mBqCalBase.setPayEndYearFlag(pLPDutySchema.getPayEndYearFlag());
	// mBqCalBase.setGetStartFlag(pLPDutySchema.getGetYearFlag());
	// mBqCalBase.setPrem(pLPDutySchema.getPrem());
	// mBqCalBase.setSumPrem(pLPDutySchema.getSumPrem());
	// mBqCalBase.setGet(pLPDutySchema.getAmnt());
	// mBqCalBase.setGetMoney(pLPDutySchema.getStandPrem());
	// mBqCalBase.setInsuYear(pLPDutySchema.getInsuYear());
	// mBqCalBase.setInsuYearFlag(pLPDutySchema.getInsuYearFlag());
	// mBqCalBase.setGetStartYear(pLPDutySchema.getGetYear());
	// }
	//
	// /**
	// * 准备计算要素
	// * @return
	// */
	// private void initBqCalBase()
	// {
	// mBqCalBase = new BqCalBase();
	//
	// //退保年度
	// mBqCalBase.setInterval(this.getZTYear());
	//
	// //得到投保年龄，性别
	// LPInsuredBL tLPInsuredBL = new LPInsuredBL();
	// LPInsuredSet tLPInsuredSet =
	// tLPInsuredBL.queryAllLPInsured(mLPEdorItemSchema);
	//
	// for (int j = 1; j <= tLPInsuredSet.size(); j++)
	// {
	//
	// mBqCalBase.setAppAge(PubFun.calInterval(
	// tLPInsuredSet.get(j).getBirthday(),
	// mLPPolSchema.getCValiDate(), "Y"));
	// mBqCalBase.setSex(tLPInsuredSet.get(j).getSex());
	//
	// }
	// mBqCalBase.setGrpContNo(mLPPolSchema.getGrpContNo());
	// mBqCalBase.setCValiDate(mLPPolSchema.getCValiDate());
	// mBqCalBase.setPolNo(mLPPolSchema.getPolNo());
	// mBqCalBase.setMult(mLPPolSchema.getMult());
	//
	// mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
	// mBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
	// mBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
	//
	// LCPolDB tLCPolDB = new LCPolDB();
	// LCPolSet tLCPolSet = new LCPolSet();
	// tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
	// tLCPolSet = tLCPolDB.query();
	// if (tLCPolSet.size() > 0)
	// {
	// mBqCalBase.setStandByFlag1(tLCPolSet.get(1).getStandbyFlag1());
	// logger.debug("StandByFlag1= " + mBqCalBase.getStandByFlag1());
	// }
	// else
	// {
	// logger.debug("==> 查询保单失败!");
	// }
	//
	// if (mCalType.equals("0"))
	// {
	// initBqCalBase(mLPPremSchema);
	// }
	// else if (mCalType.equals("1"))
	// {
	// initBqCalBase(mLPDutySchema);
	// }
	// }
	//
	/**
	 * 转换计算日期类型
	 * 
	 * @param baseDate
	 * @param interval
	 * @param unit
	 * @param compareDate
	 * @return
	 */
	private static String transCalDate(String baseDate, int interval,
			String unit, String compareDate) {
		FDate tFDate = new FDate();
		Date tEndDate = PubFun.calDate(tFDate.getDate(baseDate), interval,
				unit, tFDate.getDate(compareDate));

		return tFDate.getString(tEndDate);
	}

	/**
	 * 判断失效的新方法
	 * 
	 * @param aRiskCode
	 *            保单险种
	 * @param aPayToDate
	 *            交至日期
	 * @param aCurDate
	 *            当前日期
	 * @param aPayIntv
	 *            交费间隔
	 * @param aEndDate
	 *            保单终止日期
	 * @return
	 */
	public static boolean JudgeLapse(String aRiskCode, String aPayToDate,
			String aCurDate, String aPayIntv, String aEndDate) {
		// 如果交费方式是不定期交或者是趸交:判断保单终止日期是否小于当天
		if (aPayIntv.equals("-1") || aPayIntv.equals("0")) {
			FDate tFDate = new FDate();

			if (tFDate.getDate(aEndDate).before(tFDate.getDate(aCurDate))) {
				return true;
			}

			return false;
		}

		// 否则判断交至日期是否小于当天
		else {
			String tLapseDate = CalLapseDate(aRiskCode, aPayToDate);
			FDate tFDate = new FDate();

			if (tFDate.getDate(tLapseDate).before(tFDate.getDate(aCurDate))) {
				return true;
			}

			return false;
		}
	}

	//
	// /**
	// * 获取单个保单的帐户退保数据
	// * @param pLPEdorItemSchema
	// * @return
	// */
	// public boolean getPolAccZTData(LPEdorItemSchema pLPEdorItemSchema)
	// {
	// //暂时注掉 by PQ
	// // try
	// // {
	// // //获取保单的所有帐户信息
	// // LPInsureAccBL tLPInsureAccBL = new LPInsureAccBL();
	// // LPInsureAccSet tLPInsureAccSet =
	// tLPInsureAccBL.queryAllLPInsureAcc(pLPEdorItemSchema);
	// //
	// // for (int i = 0; i < tLPInsureAccSet.size(); i++)
	// // {
	// // getAccZTData(pLPEdorItemSchema, tLPInsureAccSet.get(i + 1));
	// // }
	// // }
	// // catch (Exception ex)
	// // {
	// // ex.printStackTrace();
	// // return false;
	// // }
	//
	// return true;
	// }
	//
	// public double getAccZTData(LPEdorItemSchema pLPEdorItemSchema,
	// LPInsureAccSchema pLPInsureAccSchema) throws Exception
	// {
	// try
	// {
	// double totalMoney = 0;
	//
	// LMEdorZTAccDB tLMEdorZTAccDB = new LMEdorZTAccDB();
	// tLMEdorZTAccDB.setRiskCode(pLPInsureAccSchema.getRiskCode());
	// tLMEdorZTAccDB.setInsuAccNo(pLPInsureAccSchema.getInsuAccNo());
	//
	// //只有部分险种才有帐户退保描述，所有没有描述也正常
	// if (!tLMEdorZTAccDB.getInfo())
	// {
	// return 0;
	// }
	//
	// //获取保单的险种信息
	// LPPolBL tLPPolBL = new LPPolBL();
	// if (!tLPPolBL.queryLPPol(pLPEdorItemSchema))
	// {
	// CError.buildErr(this, "获取保单数据失败");
	// throw new Exception("获取保单数据失败");
	// }
	//
	// //准备退保计算要素
	// this.mLMEdorZTAccSchema = tLMEdorZTAccDB.getSchema();
	// this.mLPPolSchema = tLPPolBL.getSchema();
	// this.mLPEdorItemSchema = pLPEdorItemSchema;
	// this.mLPInsureAccSchema = pLPInsureAccSchema;
	// this.setCalType("2"); //按帐户
	//
	// //计算退保金
	// totalMoney = getAccTBJ();
	//
	// //设置补退费表
	// //mLJSGetEndorseSet.add(this.getLJSGetEndorseSet());
	//
	// //用退保点作为保全生效日
	// mLPEdorItemSchema.setEdorValiDate(mLPEdorItemSchema
	// .getEdorValiDate());
	// mLPEdorItemSchema.setEdorValiDate(this.getZTPoint());
	//
	// return totalMoney;
	// }
	// catch (Exception ex)
	// {
	// throw ex;
	// }
	// }
	//
	// /**
	// * 计算退保金，mTransFlag在构造函数中进行付值
	// * @param pTransFlag LMEdorZT1中的计算代码类型CalCodeType
	// * @return
	// */
	// public double getAccTBJ() throws Exception
	// {
	// try
	// {
	// //简单计算退保
	// if (mLMEdorZTAccSchema.getCalCodeType().equals("6"))
	// {
	// if (!this.calAccZTMoney())
	// {
	// throw new Exception("简单计算退保失败");
	// }
	// }
	// else if (mLMEdorZTAccSchema.getCalCodeType().equals("7"))
	// {
	// //健康险个人帐户退保,扣除管理费
	// if (!this.calAccZTMoney(mLMEdorZTAccSchema))
	// {
	// throw new Exception("健康险扣除解约费退保");
	// }
	// }
	// }
	// catch (Exception ex)
	// {
	// throw ex;
	// }
	//
	// return this.getAccTBMoney();
	// }
	//
	// /**
	// * 健康险个人帐户退保
	// * @return
	// */
	// private boolean calAccZTMoney(LMEdorZTAccSchema pLMEdorZTAccSchema)
	// {
	// //计算当前帐户利息
	// // String InsuAccNo = pLMEdorZTAccSchema.getInsuAccNo();
	// // LMRiskInsuAccDB tLMRiskInsuAccDB=new LMRiskInsuAccDB();
	// // tLMRiskInsuAccDB.setInsuAccNo(InsuAccNo);
	// // if(tLMRiskInsuAccDB.getInfo() == false)
	// // {
	// //
	// CError.buildErr(this,"账户描述表查询失败:"+InsuAccNo+tLMRiskInsuAccDB.mErrors.getFirstError());
	// // return false;
	// // }
	// // logger.debug("==> EdorCalZT : Begin to get interest");
	// // AccountManage tAccountManage = new AccountManage();
	// // double dMoney =
	// tAccountManage.getMultiAccInterest(InsuAccNo,tLMRiskInsuAccDB.getSchema(),
	// // mLPInsureAccSchema.getInsuAccBala(),mLPInsureAccSchema.getBalaDate(),
	// // PubFun.getCurrentDate(),"C","D");
	// //
	// // logger.debug("保单" + mLPInsureAccSchema.getPolNo() +
	// // "下的" + mLPInsureAccSchema.getInsuAccNo() +
	// // "帐户本金为" + mLPInsureAccSchema.getInsuAccBala() +
	// // "利息为：" + dMoney);
	// //取得趸交、期交算法编码；扣除2%的解约费
	// String tCalCode = pLMEdorZTAccSchema.getCalCode();
	//
	// //取得帐户现金余额(本金+利息)
	// mBqCalBase = new BqCalBase();
	// mBqCalBase.setGetMoney(mLPInsureAccSchema.getInsuAccBala());
	//
	// //扣除2%的解约费后全额退保
	// BqCalBL tBqCalBL = new BqCalBL(mLPEdorItemSchema, mBqCalBase, tCalCode,
	// "");
	// double tAccValue = tBqCalBL.calGetEndorse(tCalCode, mBqCalBase);
	// logger.debug("End 计算，结息后扣除2%的解约费后全额退保算法，tAccValue: " + tAccValue);
	//
	// this.setAccTBMoney(tAccValue);
	//
	// return true;
	// }
	//
	// /**
	// * 简单计算退保
	// * @return
	// */
	// private boolean calAccZTMoney()
	// {
	// // getZTPoint(mLPInsureAccSchema.getPolNo(),
	// // mLPEdorItemSchema.getEdorValiDate());
	//
	// return calAccZTMoney(mLMEdorZTAccSchema,
	// mLPEdorItemSchema.getEdorValiDate(), mLPPolSchema.getCValiDate(),
	// getZTPoint());
	// }
	//
	// /**
	// * 简单计算退保
	// * @return
	// */
	// private boolean calAccZTMoney(LMEdorZTAccSchema pLMEdorZTAccSchema,
	// String pEdorValiDate, String pPolCValiDate, String pZTPoint)
	// {
	// //取得趸交、期交算法编码，用约进法重新计算退保年度，设置退保点
	// String tCalCode = pLMEdorZTAccSchema.getCalCode();
	//
	// //退保年度，舍弃法
	// int tZTYear = PubFun.calInterval(pPolCValiDate, pZTPoint, "Y");
	//
	// //初始化计算要素
	// initBqCalBase();
	//
	// //退保年度
	// mBqCalBase.setInterval(tZTYear);
	// mBqCalBase.setGetMoney(mLPInsureAccSchema.getInsuAccBala());
	//
	// //按照比例法计算退保
	// BqCalBL tBqCalBL = new BqCalBL(mLPEdorItemSchema, mBqCalBase, tCalCode,
	// "");
	// double tAccValue = tBqCalBL.calGetEndorse(tCalCode, mBqCalBase);
	// logger.debug("End 计算，按帐户比例退保算法，tAccValue: " + tAccValue);
	//
	// this.setAccTBMoney(tAccValue);
	//
	// return true;
	// }
	//
	// /**
	// * 为保单个人帐户中单位交费部分结息，同时减去手续费，适用于团体众悦年金，add by Minim at 2004-2-20
	// * 团体借款在使用
	// * @param PolNo
	// */
	// public static double getGrpUnderPersonAcc(String polNo, String ztPoint,
	// int ztYear)
	// {
	// try
	// {
	// //获取所有的帐户操作轨迹，包括可能正在操作的资金帐户调整内容
	// LPInsureAccTraceBL tLPInsureAccTraceBL = new LPInsureAccTraceBL();
	// LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceBL
	// .queryAllTrace(polNo);
	//
	// //把非单位交费部分去除掉
	// LPInsureAccTraceSet outLPInsureAccTraceSet = new LPInsureAccTraceSet();
	// for (int i = 0; i < tLPInsureAccTraceSet.size(); i++)
	// {
	// if (tLPInsureAccTraceSet.get(i + 1).getInsuAccNo().equals("000002"))
	// {
	// outLPInsureAccTraceSet.add(tLPInsureAccTraceSet.get(i + 1));
	// }
	// }
	//
	// return getAccCashValue(outLPInsureAccTraceSet, ztPoint, ztYear);
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	//
	// return 0;
	// }
	// }
	//
	// /**
	// * 为保单个人帐户结息，同时减去手续费，适用于团体众悦年金，add by Minim at 2004-2-16
	// * @param PolNo
	// */
	// public static double getAllPersonAcc(String polNo, String ztPoint,
	// int ztYear)
	// {
	// try
	// {
	// LPInsureAccTraceBL tLPInsureAccTraceBL = new LPInsureAccTraceBL();
	//
	// //获取所有的帐户操作轨迹，包括可能正在操作的资金帐户调整内容
	// LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceBL
	// .queryAllTrace(polNo);
	//
	// return getAccCashValue(tLPInsureAccTraceSet, ztPoint, ztYear);
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// return 0;
	// }
	// }
	//
	// /**
	// * 获取帐户现价价值，add by Minim at 2004-2-20
	// * @param tLPInsureAccTraceSet
	// * @param ztPoint 退保点
	// * @param ztYear 退保年度
	// * @return
	// */
	// public static double getAccCashValue(
	// LPInsureAccTraceSet tLPInsureAccTraceSet, String ztPoint, int ztYear)
	// {
	// double totalMoney = 0;
	// ExeSQL e = new ExeSQL();
	//
	// for (int i = 0; i < tLPInsureAccTraceSet.size(); i++)
	// {
	// String strSql = "select * from rateedor601 where startYear<="
	// + (ztYear) + " and endYear>" + (ztYear);
	// logger.debug("众悦年金 Sql: " + strSql);
	//
	// SSRS s = e.execSQL(strSql);
	// logger.debug("众悦年金 Rate: " + s.GetText(1, 3) + "轨迹金额："
	// + tLPInsureAccTraceSet.get(i + 1).getMoney() + "银行分段利息："
	// + AccountManage.getMultiAccInterest("0",
	// tLPInsureAccTraceSet.get(i + 1).getMoney(),
	// tLPInsureAccTraceSet.get(i + 1).getMakeDate(), ztPoint,
	// "C", "Y"));
	// totalMoney = totalMoney
	// + ((tLPInsureAccTraceSet.get(i + 1).getMoney()
	// + AccountManage.getMultiAccInterest("0",
	// tLPInsureAccTraceSet.get(i + 1).getMoney(),
	// tLPInsureAccTraceSet.get(i + 1).getMakeDate(), ztPoint,
	// "C", "Y")) * Double.parseDouble(s.GetText(1, 3)));
	// }
	//
	// logger.debug("众悦年金 Money: " + totalMoney);
	//
	// return totalMoney;
	// }
	//
	// /**
	// * 为保单的公共帐户结息，适用于团体众悦年金，add by Minim at 2004-2-18
	// * @param PolNo
	// * @param ztPoint
	// */
	// public static double getGrpAccInterest(String polNo, String ztPoint)
	// {
	// try
	// {
	// LPInsureAccTraceBL tLPInsureAccTraceBL = new LPInsureAccTraceBL();
	//
	// //获取所有的帐户操作轨迹，包括可能正在操作的资金帐户调整内容
	// LPInsureAccTraceSet tLPInsureAccTraceSet = tLPInsureAccTraceBL
	// .queryAllTrace(polNo);
	//
	// double totalMoney = 0;
	//
	// for (int i = 0; i < tLPInsureAccTraceSet.size(); i++)
	// {
	// totalMoney = totalMoney
	// + (tLPInsureAccTraceSet.get(i + 1).getMoney()
	// + AccountManage.getMultiAccInterest("0",
	// tLPInsureAccTraceSet.get(i + 1).getMoney(),
	// tLPInsureAccTraceSet.get(i + 1).getMakeDate(), ztPoint,
	// "C", "Y"));
	// }
	//
	// return totalMoney;
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	//
	// return 0;
	// }
	// }
	// ============程序分隔线==========以下程序是新做=====以上是保留原有部分程序============

	/**
	 * 计算险种保单的退保金
	 * 
	 * @param pLPEdorItemSchema
	 *            包含批单号、批改类型（创建批改补退费用）保单号、险种号（计算用）
	 * @return boolean
	 */
	public double calZTData(LPEdorItemSchema pLPEdorItemSchema) {
		// add by HuangLiang BOMLIST需要
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCPolSchema.getContNo());
		if (tLCContDB.getInfo()) {
			tLCContSchema.setSchema(tLCContDB.getSchema());
		}
		this.mLCContSchema = tLCContSchema;

		mLPEdorItemSchema = pLPEdorItemSchema;

		if (!initVar(pLPEdorItemSchema.getPolNo(),
				pLPEdorItemSchema.getEdorAppDate())) {
			return -1;
		}

		// 校验是否有应领未领保险金
//		if (!checkGetDrew(mLPEdorItemSchema.getPolNo(),
//				pLPEdorItemSchema.getEdorAppDate())) {
//			return -1; // del for test
//		}

		// String sMainRiskCode;
		// if (!mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo()))
		// {
		// //查询主险险种代码
		// LCPolDB tLCPolDB = new LCPolDB();
		// tLCPolDB.setPolNo(mLCPolSchema.getMainPolNo());
		// LCPolSet mainLCPolSet = tLCPolDB.query();
		// if (tLCPolDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单主险信息查询失败!");
		// return -1;
		// }
		// if (mainLCPolSet == null || mainLCPolSet.size() != 1)
		// {
		// CError.buildErr(this, "没有查到保单主险信息!");
		// return -1;
		// }
		// sMainRiskCode = mainLCPolSet.get(1).getRiskCode();
		// }
		// else
		// {
		// sMainRiskCode = mLCPolSchema.getRiskCode();
		// }
		//
		//
		// //根据PolNo查询出签收日期
		// String sql = " select CustomGetPolDate from lccont " +
		// " where contno = " +
		// " (select contno from lcpol where polno = '" + mPolNo + "')";
		// ExeSQL tExeSQL = new ExeSQL();
		// String sCustomGetPolDate = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单签收日期查询失败!");
		// return -1;
		// }
		// if (sCustomGetPolDate == null || sCustomGetPolDate.equals(""))
		// {
		// CError.buildErr(this, "保单尚未签收!");
		// return -1;
		// }
		// if (sCustomGetPolDate.length() > 10)
		// {
		// sCustomGetPolDate = sCustomGetPolDate.substring(0, 10);
		// }
		//
		// String sEdorAppDate = pLPEdorItemSchema.getEdorAppDate(); //保全项目申请日期
		// //暂时用保单生效日期 zhangtao 2005-07-21
		// int iCValiDays = PubFun.calInterval(sCustomGetPolDate, sEdorAppDate,
		// "D");
		//
		// //判断保单处于犹豫期内还是犹豫期外 （保单生效日期与保全项目申请日期）
		// LMEdorWTDB tLMEdorWTDB = new LMEdorWTDB();
		// tLMEdorWTDB.setRiskCode(sMainRiskCode);
		// tLMEdorWTDB.setHesitateType("D");
		// LMEdorWTSet tLMEdorWTSet = tLMEdorWTDB.query();
		// if (tLMEdorWTDB.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单犹豫期查询失败!");
		// return -1;
		// }
		// //险种没有犹豫期描叙，则按犹豫期外退保计算
		//
		// if (tLMEdorWTSet == null || tLMEdorWTSet.size() < 1)
		// {
		// tLMEdorWTDB.setRiskCode("000000");
		// tLMEdorWTDB.setHesitateType("D");
		// tLMEdorWTSet = tLMEdorWTDB.query();
		// logger.debug("== 该险种犹豫期 == 险种编码 ==" +
		//
		// mLCPolSchema.getRiskCode());
		// if(tLMEdorWTSet == null || tLMEdorWTSet.size() < 1)
		// {
		// CError.buildErr(this, "保单犹豫期描叙查询失败!");
		// return -1;
		// }
		// }
		//
		// int iHesitateStart = tLMEdorWTSet.get(1).getHesitateStart();
		// int iHesitateEnd = tLMEdorWTSet.get(1).getHesitateEnd();
		//
		// if ((iCValiDays >= iHesitateStart && iCValiDays <= iHesitateEnd)
		// )//|| "1".equals(sGetFlag
		// 只有犹豫期撤单就进行犹豫期内退保
		if ("WT".equals(pLPEdorItemSchema.getEdorType())) {
			// 犹豫期内退保计算
			mCTType = "1";
			return getPolZTDataIn(pLPEdorItemSchema);
		} else {
			// 犹豫期外退保计算
			mCTType = "2";
			return getPolZTData(pLPEdorItemSchema);
		}

	}

	/**
	 * 保单犹豫期内退保计算
	 * 
	 * @param pLPEdorItemSchema
	 *            包含批单号、批改类型、保单号、险种号
	 * @return double
	 */
	private double getPolZTDataIn(LPEdorItemSchema pLPEdorItemSchema) {
		double dZTMoney = 0.0; // 犹豫期内退保金额
		double dWorkNoteFee = 0.0; // 工本费 计算由产品定义描述
		double dSumPrem = 0.0;// = format(mLCPolSchema.getSumPrem());
		double dPrem = 0.0;

		// 查询保单交费记录(基本保费)
		String sql = " select sum(SumActuPayMoney) from ljapayperson "
				+ " where paytype in ('ZC','RE','AA','NS','NI') and polno = '"
				+ "?polno?"
				+ "' and trim(payplancode) not like '000000__' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sPrem = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单基本保费交费记录查询失败!");
			return -1;
		}
		if (sPrem == null || sPrem.equals("")) {
			sPrem = "0";
		}
		try {
			dPrem = Double.parseDouble(sPrem);
		} catch (Exception e) {
			CError.buildErr(this, "保单基本保费交费记录查询结果错误!" + "错误结果：" + sPrem);
			return -1;
		}

		double dEdorPrem = 0.0;
		// 查询保全变更中涉及到的补交保费与退费（基本保费）
		sql = " select sum(getmoney) from ljagetendorse " + " where polno = '"
				+ "?polno?"
				+ "' and feefinatype in ('TF', 'BF') "
				+ " and subfeeoperationtype not like concat('"
				+ "?subfeeoperationtype1?" + "','%') "
				+ " and subfeeoperationtype not like concat('"
				+ "?subfeeoperationtype2?" + "','%') "
				+ " and subfeeoperationtype not like concat('"
				+ "?subfeeoperationtype3?" + "','%') "
				+ " and subfeeoperationtype not like concat('"
				+ "?subfeeoperationtype4?" + "','%') "
				+ " and subfeeoperationtype not like concat('"
				+ "?subfeeoperationtype5?" + "','%') "
				+ " and subfeeoperationtype not like concat('"
				+ "?subfeeoperationtype6?" + "','%') ";
		// " and subfeeoperationtype not in " +
		// " ('" + BqCode.Pay_AppntAddPremHealth +
		// "', '" + BqCode.Pay_AppntAddPremOccupation +
		// "', '" + BqCode.Pay_InsurAddPremHealth +
		// "', '" + BqCode.Pay_InsurAddPremOccupation +
		// "', '" + BqCode.Get_AddPremOccupation +
		// "', '" + BqCode.Get_AddPremHealth + "')";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		sqlbv.put("subfeeoperationtype1", BqCode.Pay_AppntAddPremHealth);
		sqlbv.put("subfeeoperationtype2", BqCode.Pay_AppntAddPremOccupation);
		sqlbv.put("subfeeoperationtype3", BqCode.Pay_InsurAddPremHealth);
		sqlbv.put("subfeeoperationtype4", BqCode.Pay_InsurAddPremOccupation);
		sqlbv.put("subfeeoperationtype5", BqCode.Get_AddPremOccupation);
		sqlbv.put("subfeeoperationtype6", BqCode.Get_AddPremHealth);
		tExeSQL = new ExeSQL();
		String sEdorPrem = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全基本保费补退历史查询失败!");
			return -1;
		}
		if (sEdorPrem == null || sEdorPrem.equals("")) {
			sEdorPrem = "0";
		}
		try {
			dEdorPrem = Double.parseDouble(sEdorPrem);
		} catch (Exception e) {
			CError.buildErr(this, "保全基本保费补退历史查询结果错误!" + "错误结果：" + sEdorPrem);
			return -1;
		}

		dPrem += dEdorPrem;

		// 插入批改补退费记录（基本保费退费）
		if (!createLJSGetEndorse(dPrem, BqCode.Get_Prem, "TF", null)) // MS只有正值，changed
		// by
		// pst
		{
			return -1;
		}

		double dAddPremH = 0.0;
		// 查询保单交费记录（健康加费）
		sql = " select sum(SumActuPayMoney) from ljapayperson a "
				+ " where a.paytype in ('ZC','RE','AA','NS','NI') and polno = '"
				+ "?polno?"
				+ "' and trim(payplancode) in (select trim(payplancode) from lcprem m where m.polno = a.polno and trim(m.payplantype) in ('01','03'))";
		tExeSQL = new ExeSQL();
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		String sAddPremH = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单健康加费交费记录查询失败!");
			return -1;
		}
		if (sAddPremH == null || sAddPremH.equals("")) {
			sAddPremH = "0";
		}
		try {
			dAddPremH = Double.parseDouble(sAddPremH);
		} catch (Exception e) {
			CError.buildErr(this, "保单健康加费交费记录查询结果错误!" + "错误结果：" + sAddPremH);
			return -1;
		}

		double dEdorAddPremH = 0.0;
		// 查询保全变更中涉及到的补交保费与退费（健康加费）
		sql = " select sum(getmoney) from ljagetendorse " + " where polno = '"
				+ "?polno?"
				+ "' and feefinatype in ('TF', 'BF') "
				+ " and (subfeeoperationtype like concat('"
				+ "?subfeeoperationtype1?" + "','%') "
				+ " or subfeeoperationtype like concat('"
				+ "?subfeeoperationtype2?" + "','%') "
				+ " or subfeeoperationtype like concat('" + "?subfeeoperationtype3?"
				+ "','%') )";
		// " and subfeeoperationtype in " +
		// " ('" + BqCode.Pay_AppntAddPremHealth +
		// "', '" + BqCode.Pay_InsurAddPremHealth +
		// "', '" + BqCode.Get_AddPremHealth + "')";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		sqlbv.put("subfeeoperationtype1", BqCode.Pay_AppntAddPremHealth);
		sqlbv.put("subfeeoperationtype2", BqCode.Pay_InsurAddPremHealth);
		sqlbv.put("subfeeoperationtype3", BqCode.Get_AddPremHealth);
		tExeSQL = new ExeSQL();
		String sEdorAddPremH = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全健康加费补退历史查询失败!");
			return -1;
		}
		if (sEdorAddPremH == null || sEdorAddPremH.equals("")) {
			sEdorAddPremH = "0";
		}
		try {
			dEdorAddPremH = Double.parseDouble(sEdorAddPremH);
		} catch (Exception e) {
			CError.buildErr(this, "保全健康加费补退历史查询结果错误!" + "错误结果：" + sEdorAddPremH);
			return -1;
		}
		dAddPremH += dEdorAddPremH;
		// 插入批改补退费记录（健康加费退费）
		if (!createLJSGetEndorse(dAddPremH, BqCode.Get_AddPremHealth, "TF",
				null)) {
			return -1;
		}

		double dAddPremO = 0.0;
		// 查询保单交费记录（职业加费）
		sql = " select sum(SumActuPayMoney) from ljapayperson a "
				+ " where a.paytype in ('ZC','RE','AA','NS','NI') and polno = '"
				+ "?polno?"
				+ "' and trim(payplancode) in (select trim(payplancode) from lcprem m where m.polno = a.polno and trim(m.payplantype) in ('02','04'))";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		tExeSQL = new ExeSQL();
		String sAddPremO = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单职业加费交费记录查询失败!");
			return -1;
		}
		if (sAddPremO == null || sAddPremO.equals("")) {
			sAddPremO = "0";
		}
		try {
			dAddPremO = Double.parseDouble(sAddPremO);
		} catch (Exception e) {
			CError.buildErr(this, "保单职业加费交费记录查询结果错误!" + "错误结果：" + sAddPremO);
			return -1;
		}
		double dEdorAddPremO = 0.0;
		// 查询保全变更中涉及到的补交保费与退费（职业加费）
		sql = " select sum(getmoney) from ljagetendorse " + " where polno = '?polno?' and feefinatype in ('TF', 'BF') "
				+ " and (subfeeoperationtype like concat('"
				+ "?subfeeoperationtype1?" + "','%') "
				+ " or subfeeoperationtype like concat('"
				+ "?subfeeoperationtype2?" + "','%') "
				+ " or subfeeoperationtype like concat('"
				+ "?subfeeoperationtype3?" + "','%')) ";
		// " and subfeeoperationtype in " +
		// " ('" + BqCode.Pay_AppntAddPremOccupation +
		// "', '" + BqCode.Pay_InsurAddPremOccupation +
		// "', '" + BqCode.Get_AddPremOccupation + "')";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		sqlbv.put("subfeeoperationtype1", BqCode.Pay_AppntAddPremOccupation);
		sqlbv.put("subfeeoperationtype2", BqCode.Pay_InsurAddPremOccupation);
		sqlbv.put("subfeeoperationtype3", BqCode.Get_AddPremOccupation);
		tExeSQL = new ExeSQL();
		String sEdorAddPremO = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全职业加费补退历史查询失败!");
			return -1;
		}
		if (sEdorAddPremO == null || sEdorAddPremO.equals("")) {
			sEdorAddPremO = "0";
		}
		try {
			dEdorAddPremO = Double.parseDouble(sEdorAddPremO);
		} catch (Exception e) {
			CError.buildErr(this, "保全职业加费补退历史查询结果错误!" + "错误结果：" + sEdorAddPremO);
			return -1;
		}
		dAddPremO += dEdorAddPremO;
		// 插入批改补退费记录（职业加费退费）
		if (!createLJSGetEndorse(dAddPremO, BqCode.Get_AddPremOccupation, "TF",
				null)) {
			return -1;
		}
		// *********折扣保费退保修改 gaoph 20110127 ***start********************
		// 查询保单保费折扣记录
		sql = " select (case when sum(SumActuPayMoney) is not null then sum(SumActuPayMoney) else 0 end) from ljapayperson "
				+ " where paytype in (select code from ldcode where codetype = 'discounttype') and polno = '"
				+ "?polno?" + "'";
		sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		String zkPrem = tExeSQL.getOneValue(sqlbv);
		double dZKPrem = 0.00;
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单基本保费交费记录查询失败!");
			return -1;
		}
		if (zkPrem == null || zkPrem.equals("")) {
			zkPrem = "0";
		}
		try {
			dZKPrem = Double.parseDouble(zkPrem);
		} catch (Exception e) {
			CError.buildErr(this, "保单折扣保费交费记录查询结果错误!" + "错误结果：" + zkPrem);
			return -1;
		}

		// 查询保全变更中涉及到的补交保费与退费的折扣保费
		sql = " select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljagetendorse "
				+ " where polno = '" + "?polno?"
				+ "' and feefinatype = 'ZK' ";
		sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("polno", pLPEdorItemSchema.getPolNo());
		tExeSQL = new ExeSQL();
		double dEdorZKPrem = 0.00;
		String sEdorZKPrem = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全补退费折扣历史查询失败!");
			return -1;
		}
		if (sEdorZKPrem == null || sEdorZKPrem.equals("")) {
			sEdorZKPrem = "0";
		}
		try {
			dEdorZKPrem = Double.parseDouble(sEdorZKPrem);
		} catch (Exception e) {
			CError.buildErr(this, "保全补退折扣历史查询结果错误!" + "错误结果：" + sEdorAddPremO);
			return -1;
		}
		dZKPrem += dEdorZKPrem;

		// 插入批改补退费记录（暂记为基本保费退费）
		if (!createLJSGetEndorse(dZKPrem, BqCode.Get_Prem, "ZK", null)) {
			return -1;
		}
		// *********折扣保费退保修改 gaoph 20110127 ***end********************
		// 工本费的收取应该只在保单层，不在险种层面
		if (mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo())) // 主险
		{
			// 是否扣除工本费，从前台页面传入
			logger.debug("********" + mLPEdorItemSchema.getStandbyFlag1());
			if ("1".equals(mLPEdorItemSchema.getStandbyFlag1())) {
//				dWorkNoteFee = format(getWorkNoteMoney(
//						mLPEdorItemSchema.getEdorType(),
//						mLCPolSchema.getManageCom(), mLCPolSchema.getRiskCode()));
				dWorkNoteFee = 0;
				if (dWorkNoteFee == -1) {
//					return -1;
				}
				// 插入批改补退费记录
				if (!createLJSGetEndorse(dWorkNoteFee, BqCode.Pay_WorkNoteFee,
						"GB", null)) {
					return -1;
				}
			}
		}
		// 退还保单帐户余额 add by pst on 2009-07-03 犹豫期退保也要清算保单保单余额
		double dLeavingMoney = mLCPolSchema.getLeavingMoney();
		if (dLeavingMoney > 0) {
			if (!createLJSGetEndorse(dLeavingMoney, BqCode.Get_Prem, "YE", null)) {
				return -1;
			}
		}

		// 工本费从退保金额中扣除
		dSumPrem = dPrem + dAddPremH + dAddPremO + dLeavingMoney;
		dZTMoney = dSumPrem - dWorkNoteFee;
		logger.debug("== 犹豫期内退保金额 ==" + dZTMoney);
		return format(dZTMoney);
	}

	/**
	 * 保单犹豫期外退保计算
	 * 
	 * @param pLPEdorItemSchema
	 *            包含批单号、批改类型、保单号、险种号
	 * @return double
	 */
	private double getPolZTData(LPEdorItemSchema pLPEdorItemSchema) {

		// 计算保单现金价值(基本保额现金价值 + 累计红利保额现金价值)
		double dCashValue = getCashValue();
		if (dCashValue == -1) {
			return -1;
		}

		double dFinalBonus = 0.0;
		// 判断险种是否是分红险
		String sBonusFlag = getBonusFlag(mLCPolSchema.getRiskCode());
		if (sBonusFlag == null) {
			return -1;
		} else if (sBonusFlag.equals("Y")) {
			// 计算终了红利
			// dFinalBonus = format(getFinalBonus());
			dFinalBonus = 0;
			if (dFinalBonus == -1) {
				return -1;
			}
			if (!createLJSGetEndorse(dFinalBonus, BqCode.Get_FinaBonus, "TB",
					null)) {
				return -1;
			}
		} else if (sBonusFlag.equals("M")) {
			// 处理 累计生息红利
			dFinalBonus = format(getBonusLJSX());
			if (dFinalBonus == -1) {
				return -1;
			}
			// //财务子类型可能会修改
			// if (!createLJSGetEndorse( dFinalBonus, BqCode.Get_FinaBonus,
			// "CB", null))
			// {
			// return -1;
			// }
		}
		// 结算生存金
		double getDrawmoney = 0.0;
		getDrawmoney = format(getGetDraw());
		if (getDrawmoney == -1) {
			return -1;
		}
		// 财务子类型可能会修改
		// if (!createLJSGetEndorse( getDrawmoney, BqCode.Get_GetDraw, "YF",
		// null))
		// {
		// return -1;
		// }

		// 计算健康（职业）加费应退金额 MS加费都是0
		double dAddPrem = getAddPrem();
		if (dAddPrem == -1) {
			return -1;
		}
		if (!createLJSGetEndorse(mAddPremH, BqCode.Get_AddPremHealth, "TB",
				null)) {
			return -1;
		}
		if (!createLJSGetEndorse(mAddPremO, BqCode.Get_AddPremOccupation, "TB",
				null)) {
			return -1;
		}

		// 退还多交保费
		// MS没有多交保费，直接屏蔽
		// double dPayMorePrem = getPayMorePrem(mPolNo, mZTPoint);
		// if(dPayMorePrem == -1)
		// {
		// return -1;
		// }
		// if (!createLJSGetEndorse(dPayMorePrem, BqCode.Get_MorePrem, "TF",
		// null))
		// {
		// return -1;
		// }

		// 退还保单帐户余额
		double dLeavingMoney = mLCPolSchema.getLeavingMoney();
		if (!createLJSGetEndorse(dLeavingMoney, BqCode.Get_Prem, "YE", null)) {
			return -1;
		}

		double dSZTMoney = 0.0; // 应退金额
		double dAZTMoney = 0.0; // 实退金额

		// **********************************************************************
		// 应退金额 = 基本保额现金价值 + 累计红利保额现金价值 + 终了红利
		// + 健康（职业）加费应退金额 + 多交保费 + 保单帐户余额
		// - 已领取应扣除金额（本项仅适用于108险种）
		// **********************************************************************
		dSZTMoney = dCashValue + dFinalBonus + getDrawmoney + dAddPrem
				+ dLeavingMoney;

		// 利息计算时点的确定（如果保单已经终止，只计算到终止时点）
		String sInterestCalDate = mEdorAppDate;
		if (mLCPolSchema.getAppFlag().trim().equals("4")) {
			String sql = "select min(startdate) from ( "
					+ " select startdate from lccontstate where statetype = 'Terminate' and state = '1' "
					+ "  and enddate is null and polno = '"
					+ "?polno?" + "' " + " union select to_date('"
					+ "?mEdorAppDate?" + "','yyyy-mm-dd') from dual where 1=1 ) g";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("polno", mLCPolSchema.getPolNo());
			sqlbv.put("mEdorAppDate", mEdorAppDate);
			ExeSQL tExeSQL = new ExeSQL();
			sInterestCalDate = tExeSQL.getOneValue(sqlbv);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "保单终止日期查询失败!");
				return -1;
			}
			if (sInterestCalDate == null || sInterestCalDate.equals("")) {
				CError.buildErr(this, "保单终止日期查询失败!");
				return -1;
			}
			if (sInterestCalDate.length() > 10) {
				sInterestCalDate = sInterestCalDate.substring(0, 10);
			}
		}

		double dLoanInterest = 0.0; // 贷款未清偿本金及利息
		double dAutoPayPremAddInterest = 0.0; // 自垫未清偿本金及利息
		if (mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo())) // 只针对主险计算
		{
			dLoanInterest = getLoanAddInterest(mLCPolSchema.getContNo(),
					sInterestCalDate);
			if (dLoanInterest == -1) {
				return -1;
			}
			if (!createLJSGetEndorse(mLoanCorpus, BqCode.Pay_LoanCorpus, "HK",
					null)) {
				return -1;
			}
			if (!createLJSGetEndorse(mLoanInterest,
					BqCode.Pay_LoanCorpusInterest, "LX", null)) {
				return -1;
			}

			dAutoPayPremAddInterest = getAutoPayPremAddInterest(
					mLCPolSchema.getMainPolNo(), sInterestCalDate);
			if (dAutoPayPremAddInterest == -1) {
				return -1;
			}
			if (!createLJSGetEndorse(mAutoPayPrem, BqCode.Pay_AutoPayPrem,
					"HD", null)) {
				return -1;
			}
			if (!createLJSGetEndorse(mAutoPayPremInterest,
					BqCode.Pay_AutoPayPremInterest, "LX", null)) {
				return -1;
			}

		}

		// 校验是否有应领未领保险金
//		if (!checkGetDrew(mLPEdorItemSchema.getPolNo(),
//				pLPEdorItemSchema.getEdorAppDate())) {
//			return -1; // del for test
//		}

		// **********************************************************************
		// 实退金额 = 应退金额 - 贷款未清偿本金及利息 - 自垫未清偿本金及利息
		// + 利差帐户余额 + 应领未领年金
		// **********************************************************************
		dAZTMoney = dSZTMoney - dLoanInterest - dAutoPayPremAddInterest;

		logger.debug("== 犹豫期外退保金额 ==" + dAZTMoney);
		return format(dAZTMoney);
	}

	public double getGetDraw(String sPolNo, String sCalDate) {

		if (!initVar(sPolNo, sCalDate)) {
			return -1;
		}

//		if (!checkGetDrew(sPolNo, sCalDate)) {
//			//CError.buildErr(this, "有应领未领保险金尚未入生存金帐户，不能退保。请应先进行催付!");
//			//return -1;
//		}

		return getGetDraw();
	}

	private double getGetDraw() {
		double getDraw = 0.0;
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		// String strSql = "select * from LCInsureAcc where PolNo ='" +
		// mLCPolSchema.getPolNo() + "' and insuaccno in ('000005','000009')";
		// 去除满期金，退保只清算年金
		String strSql = "select * from LCInsureAcc where PolNo ='"
				+ "?PolNo?" + "' and insuaccno in ('000005')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("PolNo", mLCPolSchema.getPolNo());
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单帐户查询失败!");
			return -1;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			// CError.buildErr(this, "保单没有帐户数据!");
			return 0;
		}

		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccSet.get(i).getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSet.get(i)
					.getInsuAccNo());
			LCInsureAccClassSet LCInsureAccClassSet_temp = tLCInsureAccClassDB
					.query();
			// 还没有入帐户的生存金，不用进行结息
			if (LCInsureAccClassSet_temp.size() <= 0) {
				continue;
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSet.get(i)
					.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo", tLCInsureAccSet.get(i)
					.getPolNo());
			tTransferData.setNameAndValue("BalaDate",
					mLPEdorItemSchema.getEdorValiDate());
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			// 非万能险的账户型结算
			InsuAccBala tInsuAccBala = new InsuAccBala();
			if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
				CError.buildErr(this, "生存金结算失败！");
				return -1;
			}
			VData tResult = new VData();
			tResult = tInsuAccBala.getResult();
			LCInsureAccSet tnewLCInsureAccSet = (LCInsureAccSet) tResult
					.getObjectByObjectName("LCInsureAccSet", 0);

			for (int j = 1; j <= tnewLCInsureAccSet.size(); j++) {
				getDraw += tnewLCInsureAccSet.get(j).getInsuAccBala();
				String trace_finType = BqCalBL.getFinType_HL_SC("SC",
						tnewLCInsureAccSet.get(j).getInsuAccNo(), "LQ");
				if (trace_finType.equals("")) {
					CError.buildErr(this, "生存金领取时的财务类型获取失败");
					return -1;
				}
				if (!createLJSGetEndorse(tnewLCInsureAccSet.get(j)
						.getInsuAccBala(), BqCode.Get_GetDraw, trace_finType,
						null)) {
					return -1;
				}
			}

			LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) tResult
					.getObjectByObjectName("LCInsureAccClassSet", 0);
			LCInsureAccTraceSet tLCInsureAccTraceSet = (LCInsureAccTraceSet) tResult
					.getObjectByObjectName("LCInsureAccTraceSet", 0);

			this.mLCInsureAccSet.add(tnewLCInsureAccSet);
			this.mLCInsureAccClassSet.add(tLCInsureAccClassSet);
			this.mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
			if (!this.mResult.contains(mLCInsureAccSet)) {
				this.mResult.add(mLCInsureAccSet);
				this.mResult.add(mLCInsureAccClassSet);
				this.mResult.add(mLCInsureAccTraceSet);
			}
		}
		return getDraw;
	}

	public double getBonusLJSX(String sPolNo, String sCalDate) {

		if (!initVar(sPolNo, sCalDate)) {
			return -1;
		}

		return getBonusLJSX();
	}

	private double getBonusLJSX() {
		// 处理 累计生息红利、现金红利、抵交保费红利
		double dFinalBonus = 0.0;
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		String strSql = "select * from LCInsureAcc where PolNo ='"
				+ "?PolNo?"
				+ "' and insuaccno in ('000001','000007','000008')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("PolNo", mLCPolSchema.getPolNo());
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单帐户查询失败!");
			return -1;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			// CError.buildErr(this, "保单没有帐户数据!");
			return 0;
		}

		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccSet.get(i).getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSet.get(i)
					.getInsuAccNo());
			LCInsureAccClassSet LCInsureAccClassSet_temp = tLCInsureAccClassDB
					.query();
			// 还没有入帐户的红利，不用进行结息
			if (LCInsureAccClassSet_temp.size() <= 0) {
				continue;
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSet.get(i)
					.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo", tLCInsureAccSet.get(i)
					.getPolNo());
			tTransferData.setNameAndValue("BalaDate",
					mLPEdorItemSchema.getEdorValiDate());
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			// 非万能险的账户型结算
			InsuAccBala tInsuAccBala = new InsuAccBala();
			if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
				CError.buildErr(this, "分红结算失败！");
				return -1;
			}
			VData tResult = new VData();
			tResult = tInsuAccBala.getResult();
			LCInsureAccSet tnewLCInsureAccSet = (LCInsureAccSet) tResult
					.getObjectByObjectName("LCInsureAccSet", 0);

			for (int j = 1; j <= tnewLCInsureAccSet.size(); j++) {
				dFinalBonus += tnewLCInsureAccSet.get(j).getInsuAccBala();
				String trace_finType = BqCalBL.getFinType_HL_SC("HL",
						tnewLCInsureAccSet.get(j).getInsuAccNo(), "LQ");
				if (trace_finType.equals("")) {
					CError.buildErr(this, "红利领取时的财务类型获取失败");
					return -1;
				}
				if (!createLJSGetEndorse(tnewLCInsureAccSet.get(j)
						.getInsuAccBala(), BqCode.Get_FinaBonus, trace_finType,
						null)) {
					return -1;
				}
			}

			LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) tResult
					.getObjectByObjectName("LCInsureAccClassSet", 0);
			LCInsureAccTraceSet tLCInsureAccTraceSet = (LCInsureAccTraceSet) tResult
					.getObjectByObjectName("LCInsureAccTraceSet", 0);
			this.mLCInsureAccSet.add(tnewLCInsureAccSet);
			this.mLCInsureAccClassSet.add(tLCInsureAccClassSet);
			this.mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
			if (!this.mResult.contains(mLCInsureAccSet)) {
				this.mResult.add(mLCInsureAccSet);
				this.mResult.add(mLCInsureAccClassSet);
				this.mResult.add(mLCInsureAccTraceSet);
			}
		}

		return dFinalBonus;
	}

	// 为了兼容，暂留
	public double getCashValue(String sPolNo, LCDutySchema pLCDutySchema,
			String sCalDate) {
		return getCashValue(sPolNo, sCalDate);
	}

	/**
	 * 计算保单现金价值公用接口 - 按保单计算 (险种和责任信息从库中查) 基本保额现金价值+累计红利保额现金价值
	 * 
	 * @param sPolNo
	 *            保单号
	 * @param sCalDate
	 *            保单结算时点
	 * @return double 返回-1表示错误
	 */
	public double getCashValue(String sPolNo, String sCalDate) {

		if (!initVar(sPolNo, sCalDate)) {
			return -1;
		}

		return getCashValue();
	}

	// 重载函数 contno层
	public double getContCashValue(LPEdorItemSchema tLPEdorItemSchema) {
		double tContCashValue = 0.00;
		String tAllPolno = "select * from lcpol where contno = '"
				+ "?contno?" + "' and appflag='1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tAllPolno);
		sqlbv.put("contno", tLPEdorItemSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		int tSize = tLCPolSet.size();
		for (int i = 1; i <= tSize; i++) {
			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			tContCashValue += getCashValue(tLCPolSchema.getPolNo(),
					tLPEdorItemSchema.getEdorValiDate());
		}
		return PubFun.round(tContCashValue, 2);
	}

	/**
	 * 计算团体保单现金价值公用接口 - 按保单计算 (险种和责任信息从库中查) 基本保额现金价值+累计红利保额现金价值 Added by XinYQ
	 * on 2006-06-01
	 * 
	 * @param sGrpContNo
	 *            保单号
	 * @param sCalDate
	 *            保单结算时点
	 * @return double 返回-1表示错误
	 */
	public double getGrpCashValue(String sGrpContNo, String sCalDate) {
		double dReturnValue = 0.0;

		if (sGrpContNo == null || sGrpContNo.trim().equals("")
				|| sCalDate == null || sCalDate.trim().equals("")) {
			CError.buildErr(this, "团体合同号或保单生效日期不能为空！");
			return -1;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setGrpContNo(sGrpContNo);
		tLCPolDB.setAppFlag("1");
		LCPolSet tLCPolSet = new LCPolSet();
		try {
			tLCPolSet = tLCPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询新契约险种表以获取险种号码异常！");
			return -1;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			CError.buildErr(this, "查询新契约险种表以获取险种号码失败！");
			return -1;
		} else {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				double dTempCash = 0.0;
				dTempCash = getCashValue(tLCPolSchema.getPolNo(), sCalDate);
				if (dTempCash == -1) {
					CError.buildErr(this, "计算分单险种 " + tLCPolSchema.getPolNo()
							+ " 现金价值失败！");
					return -1;
				} else {
					dReturnValue += dTempCash;
				}
				tLCPolSchema = null;
			}
		}
		tLCPolSet = null;
		tLCPolDB = null;

		return dReturnValue;
	} // function getGrpCashValue end

	/**
	 * 计算红利保额现金价值公用接口
	 * 
	 * @param sPolNo
	 *            保单号
	 * @param sCalDate
	 *            保单结算时点
	 * @return double 返回-1表示错误
	 */
	public double getBonusCashValue(String sPolNo, String sCalDate) {

		if (!initVar(sPolNo, sCalDate)) {
			return -1;
		}

		double dBonusCashValue = 0.0;
		double dBonusDutyCashValue = 0.0;

		// 查询出该保单下所有的责任信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mPolNo);
		LCDutySet tLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询险种责任失败!");
			return -1;
		}
		if (tLCDutySet == null || tLCDutySet.size() < 1) {
			CError.buildErr(this, "查询险种责任失败!");
			return -1;
		}
		// ===add===zhangtao===2006-09-13=====要件变更后计算要素采用传入方式,不直接从数据库中提取===BGN====
		if (mLPPolSchema != null) {
			Reflections tRef = new Reflections();
			tRef.transFields(mLCPolSchema, mLPPolSchema);
			tRef.transFields(tLCDutySet, mLPDutySet);
		}
		// ===add===zhangtao===2006-09-13=====要件变更后计算要素采用传入方式,不直接从数据库中提取===END====
		String mainDutyCode = "";
		for (int i = 1; i <= tLCDutySet.size(); i++) // 循环保单的责任项计算
		{
			if ((tLCDutySet.get(i).getDutyCode().length() == 8)) {
				mainDutyCode = tLCDutySet.get(i).getDutyCode().substring(0, 6);
			}
			LMEdorBonusZTSchema tLMEdorBonusZTSchema = new LMEdorBonusZTSchema();
			tLMEdorBonusZTSchema.setRiskCode(mLCPolSchema.getRiskCode());
			tLMEdorBonusZTSchema.setDutyCode(mainDutyCode);
			tLMEdorBonusZTSchema.setCycPayIntvType(String.valueOf(mLCPolSchema
					.getPayIntv()));
			dBonusDutyCashValue = getBonusCashValue(tLMEdorBonusZTSchema,
					tLCDutySet.get(i));
			if (dBonusDutyCashValue == -1) {
				return -1;
			} else {
				dBonusCashValue += dBonusDutyCashValue;
			}
		}

		return format(dBonusCashValue);
	}

	/**
	 * 计算保单现金价值 - 按保单计算 (险种和责任信息从库中查) 基本保额现金价值+累计红利保额现金价值（MS只处理增额交清 modify by wk
	 * ）
	 * 
	 * @return double 返回-1表示错误
	 */
	private double getCashValue() {
		double dCashValue = 0.0;
		double dDutyCashValue = 0.0;

		// 查询出该保单下所有的责任信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		/*
		 * tLCDutyDB.setPolNo(mPolNo); LCDutySet tLCDutySet = tLCDutyDB.query();
		 */
		// 查询责任编码长度为6或8的，即约定责任及附加责任
		// 增额交清退保算法和正常责任一样
		String DutySql = "select * from lcduty where polno='"
				+ "?mPolNo?"
				+ "' and (char_length(trim(dutycode))=6 or char_length(trim(dutycode))=8 or(char_length(trim(dutycode))=10 and substr(dutycode,7,1)='1'))";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DutySql);
		sqlbv.put("mPolNo", mPolNo);
		LCDutySet tLCDutySet = tLCDutyDB.executeQuery(sqlbv);
		logger.debug(DutySql);
		if (tLCDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询险种责任失败!");
			return -1;
		}
		if (tLCDutySet == null || tLCDutySet.size() < 1) {
			CError.buildErr(this, "查询险种责任失败!");
			return -1;
		}

		for (int i = 1; i <= tLCDutySet.size(); i++) // 循环保单的责任项计算
		{
			this.mBqCalBase.setDutyCode(tLCDutySet.get(i).getDutyCode());
			dDutyCashValue = getDutyCashValue(tLCDutySet.get(i), mZTPoint);
			if (dDutyCashValue == -1) {
				return -1;
			} else {
				dCashValue += dDutyCashValue;
			}
		}

		return dCashValue;
	}

	/**
	 * 计算保单现金价值公用接口 - 按责任计算 (险种和责任信息由外层传入) 基本保额现金价值+累计红利保额现金价值
	 * 
	 * @param pLCPolSchema
	 *            传入完整的保单信息
	 * @param pLCDutySchema
	 *            传入完整的责任信息
	 * @param sCalDate
	 *            保单结算时点
	 * @return double 返回-1表示错误
	 */
	public double getCashValue(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, String sCalDate) {
		// mSumPremFlag = "Y";
		if (!initVar(pLCPolSchema.getPolNo(), sCalDate)) {
			return -1;
		}

		// 保单详细信息由外层传入
		mLCPolSchema.setSchema(pLCPolSchema);

		double dDutyCashValue = getDutyCashValue(pLCDutySchema, mZTPoint);

		// 按比例退保

		return dDutyCashValue;
	}

	/**
	 * 按责任项计算现金价值
	 * 
	 * @param pLCDutySchema
	 * @param sZTDate
	 * @return double
	 */
	private double getDutyCashValue(LCDutySchema pLCDutySchema, String sZTDate) {
		// 如果保险责任终止，不用再计算
		if (pLCDutySchema.getEndDate().compareTo(sZTDate) <= 0
				&& !mCalDateType.equals("CF")) {
			logger.debug("责任项已终止，不进行退保计算" + "保单号：" + pLCDutySchema.getPolNo()
					+ "责任编码：" + pLCDutySchema.getDutyCode());
			// 即使为零也增加一条记录，便于协议退保时修改
			if (!createLJSGetEndorse(0, BqCode.Get_BaseCashValue, "TB",
					pLCDutySchema)) {
				return -1;
			}
			return 0;
		}

		double dBaseCashValue = 0.0; // 基本保额现金价值

		// ========对责任编码的判断截取处理==================BGN============================
		// 疑问：为何要这样处理？给个理由 zhangtao 2005-07-01
		// 回答：有附加责任的按照主责任编码去查询产品定义信息
		boolean exDuty = false;
		String mainDutyCode = pLCDutySchema.getDutyCode();
		if ((pLCDutySchema.getDutyCode().length() == 8)
				|| (pLCDutySchema.getDutyCode().length() == 10 && pLCDutySchema
						.getDutyCode().substring(6, 7).equals("1"))) {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(pLCDutySchema.getPolNo());
			tLCDutyDB.setDutyCode(pLCDutySchema.getDutyCode().substring(0, 6));
			if (!tLCDutyDB.getInfo()) {
				CError.buildErr(this, "获取保单主责任数据失败");
				return -1;
			}

			pLCDutySchema.setPayEndYear(tLCDutyDB.getPayEndYear());
			pLCDutySchema.setInsuYear(tLCDutyDB.getInsuYear());
			pLCDutySchema.setPayYears(tLCDutyDB.getPayYears());

			// 保留附加责任的编码
			exDuty = true;
			mainDutyCode = pLCDutySchema.getDutyCode().substring(0, 6);
			// pLCDutySchema.setDutyCode(pLCDutySchema.getDutyCode().substring(0,
			// 6));
		}
		// ========对责任编码的判断截取处理==================END============================

		// 获取生存退保计算责任描述
		LMEdorZTDutyDB tLMEdorZTDutyDB = new LMEdorZTDutyDB();
		tLMEdorZTDutyDB.setRiskCode(mLCPolSchema.getRiskCode());
		tLMEdorZTDutyDB.setDutyCode(mainDutyCode);
		LMEdorZTDutySet tLMEdorZTDutySet = tLMEdorZTDutyDB.query();
		if (tLMEdorZTDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "获取生存退保计算责任描述信息失败");
			return -1;
		}
		if (tLMEdorZTDutySet == null || tLMEdorZTDutySet.size() != 1) {
			CError.buildErr(this, "获取生存退保计算责任描述信息失败");
			return -1;
		}
		// 是否按账户退保标志 1-帐户退保 0-非帐户退保
		String sPayByAcc = tLMEdorZTDutySet.get(1).getPayByAcc();
		// 是按责任还是保费项计算标志 1-责任 0-保费项 2-投连计算标志
		String sPayCalType = tLMEdorZTDutySet.get(1).getPayCalType();

		if (sPayByAcc.equals("1")) // 1-帐户退保
		{
			if (IsTLRisk(tLMEdorZTDutySet.get(1).getRiskCode()))// sPayCalType.equals("2"))//投连险不做处理
			{
				// if(!dealAcc(mLCPolSchema.getPolNo(),mZTPoint))
				// {
				// return -1;
				// }
			} else {
				double dZTMoneyByAcc = getZTMoneyByAcc(mLCPolSchema.getPolNo(),
						mZTPoint);
				if (dZTMoneyByAcc == -1) {
					return -1;
				}
				
				// 减保比例
				double dRate = getDutyRate(pLCDutySchema);
				dZTMoneyByAcc = dZTMoneyByAcc * dRate;
				// 万能险协议退保新规则 MAX(最初帐户，现价) and by pst on 2007-12-25
				if ("XT".equals(mLPEdorItemSchema.getEdorType())
						&& "Y".equals(BqNameFun.isAccRisk(mLCPolSchema
								.getRiskCode()))) {
					String tSQL = "select case  "
						+"         when ((SELECT SUM(money) "
						+"                  FROM lcinsureacctrace "
						+"                 WHERE moneytype IN ('BF', 'TB', 'TBFY') "
						+"                   AND polno = '?polno1?') + "
						+"              (SELECT SUM(fee) "
						+"                  FROM lcinsureaccFeetrace "
						+"                 WHERE paydate = '?paydate?' "
						+"                   AND polno = '?polno2?')) is null then "
						+"          1 "
						+"         else "
						+"          ((SELECT SUM(money) "
						+"              FROM lcinsureacctrace "
						+"             WHERE moneytype IN ('BF', 'TB', 'TBFY') "
						+"               AND polno = '?polno1?') + "
						+"          (SELECT SUM(fee) "
						+"              FROM lcinsureaccFeetrace "
						+"             WHERE paydate = '?paydate?' "
						+"               AND polno = '?polno2?')) "
						+"       end "
						+"  from dual; ";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(tSQL);
					sqlbv.put("polno1", mLCPolSchema.getPolNo());
					sqlbv.put("paydate", mLCPolSchema.getCValiDate());
					sqlbv.put("polno2", mLCPolSchema.getPolNo());
					ExeSQL tExeSQL = new ExeSQL();
					String tMoney = tExeSQL.getOneValue(sqlbv);
					double rMoney = 0.0;
					if ("".equals(tMoney)) {
						return -1;
					} else {
						rMoney = Double.parseDouble(tMoney);
						double tIntv = rMoney - dZTMoneyByAcc;
						if (tIntv >= 0) {
							dZTMoneyByAcc = rMoney;
						}
					}

				}

				// 插入批改补退费记录（扣除手续费之前的退保金）
				if (!createLJSGetEndorse(dZTMoneyByAcc,
						BqCode.Get_BaseCashValue, "TB", pLCDutySchema)) {
					return -1;
				}
				this.mBqCalBase.setZTMoneyByAcc(dZTMoneyByAcc);
				this.mBqCalBase.setEdorValiDate("");
				this.mBqCalBase.setIntervalM(0);
				// 退保扣除手续费后的金额
				double dZTMoneyByAccK = getActuMoney(
						mLCPolSchema.getRiskCode(), dZTMoneyByAcc, mInterval,
						"GetFee");
				if (dZTMoneyByAccK == -1) {
					return -1;
				}

				// 插入批改补退费记录（手续费单独记一笔冲减的退保金）
				if (!createLJSGetEndorse(dZTMoneyByAcc - dZTMoneyByAccK,
						BqCode.Pay_TBFee, "TB", pLCDutySchema)) {
					return -1;
				}

				mBaseCashValue += dZTMoneyByAccK;

				mTBFee += dZTMoneyByAcc - dZTMoneyByAccK;

				// 理赔计算不扣除手续费
				if (mCalDateType != null && mCalDateType.equals("LP")) {
					return dZTMoneyByAcc; // 返回扣除手续费之前的退保金
				}

				return dZTMoneyByAccK; // 返回扣除手续费之后的退保金
			}
		} else // 0-非帐户退保
		{
			// 根据LMEdorCal描述表中描述SurrCalType的计算代码计算出实际值
			String sSurrCalType = calSurrCalType(mLCPolSchema.getRiskCode(),
					mainDutyCode);

			// add by jiaqiangli 2008-12-18 增额缴清的dutycode特殊处理
			// 增额缴清重新确定一下保单年度：两年内还是两年外
			if (pLCDutySchema.getDutyCode().length() == 10
					&& pLCDutySchema.getDutyCode().substring(6, 7).equals("1")) {
				// pLCDutySchema.getFirstPayDate与lcpol.cvalidate相差年份数
				int tZEJQInterval = PubFun.calInterval2(
						pLCDutySchema.getFirstPayDate(), mZTPoint, "Y");
				sSurrCalType = calSurrCalType4ZEJQ(mLCPolSchema.getRiskCode(),
						mainDutyCode, tZEJQInterval);
			}

			if (sSurrCalType == null) {
				return -1;
			}
			if (sPayCalType.equals("1")) // 1-按责任计算
			{
				LMEdorZT1Schema tLMEdorZT1Schema = new LMEdorZT1Schema();
				tLMEdorZT1Schema.setRiskCode(mLCPolSchema.getRiskCode());
				tLMEdorZT1Schema.setDutyCode(mainDutyCode);
				tLMEdorZT1Schema.setPayPlanCode("000000");

				if ("1".equals(this.tCTCodeFLag)) {
					// 新退保算法不再区分两年时间间隔，所以置上特殊标记“0”
					tLMEdorZT1Schema.setSurrCalType("0");
				} else {
					tLMEdorZT1Schema.setSurrCalType(sSurrCalType); // 区分[两年内][两年外]
				}
				// tLMEdorZT1Schema.setZTYearType(this.tCTCodeFLag);
				tLMEdorZT1Schema.setZTYearType("1");
				// 对增额缴清做特殊处理 add by jiaqiangli 2008-12-18 其他要素lcduty中没有的都取lcpol
				// 特殊处理：保单年度和交费间隔
				if (pLCDutySchema.getDutyCode().length() == 10
						&& pLCDutySchema.getDutyCode().substring(6, 7)
								.equals("1"))
					// 这里等效于置0
					tLMEdorZT1Schema.setCycPayIntvType(String
							.valueOf(pLCDutySchema.getPayIntv()));
				else
					tLMEdorZT1Schema.setCycPayIntvType(String
							.valueOf(mLCPolSchema.getPayIntv()));

				if (pLCDutySchema.getDutyCode().length() == 10
						&& pLCDutySchema.getDutyCode().substring(6, 7)
								.equals("1")) {
					// 注意－－ 与蒋莱确认过，MS红利(增额交清红利)在减保及协议减保中不做处理 。
					if (mLPEdorItemSchema.getEdorType() != null
							&& (mLPEdorItemSchema.getEdorType().equals("PT") || mLPEdorItemSchema
									.getEdorType().equals("XS"))) {
						dBaseCashValue = 0;
						return dBaseCashValue;
					}
				}

				// 计算基本保额现金价值
				dBaseCashValue = format(getBaseCashValue(tLMEdorZT1Schema,
						pLCDutySchema));
				//先写死了，不好改。
//				dBaseCashValue = 10000 * (double)(Math.random()*100.56);
//				dBaseCashValue = Double.valueOf(String.format("%.2f",dBaseCashValue)).doubleValue();

				if (dBaseCashValue == -1) {
					return -1;
				}
				if (pLCDutySchema.getDutyCode().length() == 10
						&& pLCDutySchema.getDutyCode().substring(6, 7)
								.equals("1"))// 增额交清
				{
					// 增额交清 插入批改补退费记录 是明细记录
					if (!createLJSGetEndorse(dBaseCashValue,
							BqCode.Get_BonusCashValue, "TB", pLCDutySchema)) {
						return -1;
					}
					mBonusCashValue += dBaseCashValue;
				} else {
					// 插入批改补退费记录 基本现金价值
					if (!createLJSGetEndorse(dBaseCashValue,
							BqCode.Get_BaseCashValue, "TB", pLCDutySchema)) {
						return -1;
					}
					mBaseCashValue += dBaseCashValue;
				}

				double dBonusCashValue = 0.0; // 红利保额现金价值
				// 判断险种是否是分红险
				String sBonusFlag = getBonusFlag(mLCPolSchema.getRiskCode());
				if (sBonusFlag == null) {
					return -1;
				} else if (sBonusFlag.equals("Y")) {
					// 分红险，计算红利保额现金价值
					LMEdorBonusZTSchema tLMEdorBonusZTSchema = new LMEdorBonusZTSchema();
					tLMEdorBonusZTSchema
							.setRiskCode(mLCPolSchema.getRiskCode());
					tLMEdorBonusZTSchema.setDutyCode(mainDutyCode);
					tLMEdorBonusZTSchema.setCycPayIntvType(String
							.valueOf(mLCPolSchema.getPayIntv()));

					dBonusCashValue = format(getBonusCashValue(
							tLMEdorBonusZTSchema, pLCDutySchema));
					if (dBonusCashValue == -1) {
						return -1;
					}

					// 插入批改补退费记录
					if (!createLJSGetEndorse(dBonusCashValue,
							BqCode.Get_BonusCashValue, "TB", pLCDutySchema)) {
						return -1;
					}
					mBonusCashValue += dBonusCashValue;
				} else if (sBonusFlag.equals("M")) {
					// 美式分红 2008-9 计算临时红利,累计生息红利；交清增额红利 ；
					// 抵缴保费红利通过余额退费处理;
					logger.debug("注意：红利还未处理。。。");
					// 缴清增额红利现金价值计算
					// 注意－－ 与蒋莱确认过，MS红利在减保及协议减保中不做处理 。
					// if(!mLPEdorItemSchema.equals("PT") &&
					// !mLPEdorItemSchema.equals("XS"))
					// {
					//
					// }
				}

				dBaseCashValue += dBonusCashValue;

				// //恢复附加责任的编码，否则插入批改补退费表中会出错(关键字重复)
				// if (exDuty)
				// {
				// pLCDutySchema.setDutyCode(oldDutyCode);
				// }
			}
		}

		return dBaseCashValue;
	}

	/**
	 * 根据LMEdorCal描述表中描述SurrCalType的计算代码计算出实际值
	 * 
	 * @param sRiskCode
	 * @param sDutyCode
	 */
	private String calSurrCalType(String sRiskCode, String sDutyCode) {
		// 从LMEdorCal描述表中查询出SurrCalType的计算代码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(sRiskCode);
		// tLMEdorCalDB.setRiskVer("2002");
		tLMEdorCalDB.setDutyCode(sDutyCode);
		tLMEdorCalDB.setEdorType("CT"); // 退保
		tLMEdorCalDB.setCalType("SurCalType");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "退保区分类型计算代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			// CError.buildErr(this, "没有查到退保区分类型计算代码!");
			return "0";
			// return null;
		}

		// 计算SurrCalType的实际值 区分两年内两年外，只是起到算法序号的作用
		Calculator tCalculator = new Calculator();

		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		mBomListFlag = false;
		tCalculator.addBasicFactor("Interval",
				String.valueOf(this.mBqCalBase.getInterval()));// 保单年度
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return null;
		}
		String SurrCalTypeValue = tCalculator.calculate();
		if (SurrCalTypeValue == null || SurrCalTypeValue.equals("")) {
			SurrCalTypeValue = "0";
		}
		// if (tCalculator.mErrors.needDealError()) {
		// CError.buildErr(this, "区分类型计算失败!");
		// return null;
		// }

		return SurrCalTypeValue;
	}

	/**
	 * 重载处理增额缴清的Interval=ztpoint-lcduty.firstpaydate
	 * 
	 * @param sRiskCode
	 * @param sDutyCode
	 * @return
	 */
	private String calSurrCalType4ZEJQ(String sRiskCode, String sDutyCode,
			int tInterval) {
		// 从LMEdorCal描述表中查询出SurrCalType的计算代码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(sRiskCode);
		mBqCalBase.setInterval(tInterval);
		// tLMEdorCalDB.setRiskVer("2002");
		tLMEdorCalDB.setDutyCode(sDutyCode);
		tLMEdorCalDB.setEdorType("CT"); // 退保
		tLMEdorCalDB.setCalType("SurCalType");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "退保区分类型计算代码查询失败!");
			return null;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			// CError.buildErr(this, "没有查到退保区分类型计算代码!");
			return "0";
			// return null;
		}

		// 计算SurrCalType的实际值 区分两年内两年外，只是起到算法序号的作用
		Calculator tCalculator = new Calculator();
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return null;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		tCalculator.addBasicFactor("Interval",
				String.valueOf(mBqCalBase.getInterval()));// 保单年度
		String SurrCalTypeValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "区分类型计算失败!");
			return null;
		}

		return SurrCalTypeValue;
	}

	/**
	 * 计算基本保额现金价值
	 * 
	 * @param pLMEdorZT1Schema
	 * @param pLCDutySchema
	 * @return double
	 */
	private double getBaseCashValue(LMEdorZT1Schema pLMEdorZT1Schema,
			LCDutySchema pLCDutySchema) {
		// 获取基本保额计算代码
		LMEdorZT1DB tLMEdorZT1DB = new LMEdorZT1DB();
		tLMEdorZT1DB.setSchema(pLMEdorZT1Schema); // 查询条件由外层传入
		LMEdorZT1Set tLMEdorZT1Set = tLMEdorZT1DB.query();
		if (tLMEdorZT1DB.mErrors.needDealError()) {
			CError.buildErr(this, "获取基本保额现金价值计算代码失败!");
			return -1;
		}
		if (tLMEdorZT1Set == null || tLMEdorZT1Set.size() != 1) {
			CError.buildErr(this, "没有查到基本保额现金价值计算代码!");
			return -1;
		}
		String sCalCodeType = tLMEdorZT1Set.get(1).getCalCodeType();
		String sCycPayCalCode = tLMEdorZT1Set.get(1).getCycPayCalCode();
		Calculator tCalculator = new Calculator();

		tCalculator.setCalCode(sCycPayCalCode);

		// double dCashValueCV0 = 0.0;
		double dCashValueCVt = 0.0;
		double dCashValueCVt1 = 0.0;
		double dNextPrem = 0.0;
		double dPrem = 0.0;
		double dSumPrem = 0.0;
		double dAliveGet = 0.0;
		double dliveGet = 0.0;
		double dAliveGetRate = 0.0;
		double dSumYearGet = 0.0;
		double dSumYearGetRate = 0.0;
		double dAmnt = pLCDutySchema.getAmnt();
		double dMult = pLCDutySchema.getMult();
		double dSumAmntBonus = 0.0;
		int iPayTimes = 0;
		String sGetIntv;
		String sPayNextFlag = "";
		String sCValiDate_Duty = pLCDutySchema.getCValiDate();
		if (sCValiDate_Duty == null || sCValiDate_Duty.trim().equals("")) {
			sCValiDate_Duty = mCValiDate_Pol;
		}

		String sEndDate_Duty = pLCDutySchema.getEndDate();
		if (sEndDate_Duty == null || sEndDate_Duty.trim().equals("")) {
			sEndDate_Duty = mEndDate_Pol;
		}

		double dVPU = getVPU(pLCDutySchema.getDutyCode());
		if (dVPU == -1) {
			return -1;
		}

		double dReduceAmnt = getReduceAmnt(pLCDutySchema.getContNo(), mPolNo,
				pLCDutySchema.getDutyCode(), mZTPoint);
		if (dReduceAmnt == -1) {
			return -1;
		}

		this.mBqCalBase.setReduceAmnt(dReduceAmnt);
		this.mBqCalBase.setCValiDate(sCValiDate_Duty);
		this.mBqCalBase.setEndDate(sEndDate_Duty);
		this.mBqCalBase.setVPU(dVPU);
		this.mBqCalBase.setMult(dMult);
		this.mBqCalBase.setAmnt(dAmnt);
		int x = (int) Math.floor(exToYear(pLCDutySchema.getPayEndYear(),
				pLCDutySchema.getPayEndYearFlag()));
		this.mBqCalBase.setPayEndYear(x);
		this.mBqCalBase.setPayEndYearFlag(mLCPolSchema.getPayEndYearFlag());
		this.mBqCalBase.setPayIntv(pLCDutySchema.getPayIntv());
		this.mBqCalBase.setPrem(dPrem);
		this.mBqCalBase.setPayTimes(iPayTimes);
		this.mBqCalBase.setPayNextFlag(sPayNextFlag);
		this.mBqCalBase.setCVt(dCashValueCVt);
		this.mBqCalBase.setCVt1(dCashValueCVt1);
		this.mBqCalBase.setNextPrem(dNextPrem);
		this.mBqCalBase.setSumPrem(dSumPrem);
		this.mBqCalBase.setliveGet(dliveGet);
		this.mBqCalBase.setAliveGet(dAliveGet);
		this.mBqCalBase.setAliveGetRate(dAliveGetRate);
		this.mBqCalBase.setSumYearGet(dSumYearGet);
		this.mBqCalBase.setSumYearGetRate(dSumYearGetRate);
		this.mBqCalBase.setSumAmntBonus(dSumAmntBonus);
		this.mBqCalBase
				.setEdorReasonCode(mLPEdorItemSchema.getEdorReasonCode());
		this.mBqCalBase.setSignDate(mLCPolSchema.getSignDate());
		this.mBqCalBase.setSex(mLCPolSchema.getInsuredSex());
		this.mBqCalBase.setAppAge(mLCPolSchema.getInsuredAppAge());
		this.mBqCalBase.setInsuYear(pLCDutySchema.getInsuYear());
		this.mBqCalBase.setInsuYearFlag(pLCDutySchema.getInsuYearFlag());
		this.mBqCalBase.setGetStartAge(pLCDutySchema.getGetYear());
		this.mBqCalBase.setGetYear(pLCDutySchema.getGetYear());
		this.mBqCalBase.setStandByFlag1(mLCPolSchema.getStandbyFlag1());

		logger.debug("=== 基本保额现金价值====第" + sCalCodeType + "类计算类型");
		// 根据不同的计算类型，准备不同的计算要素
		if (sCalCodeType.equals("1")) {
			sPayNextFlag = getPayNextFlag(mLCPolSchema, mZTPoint);
			if (sPayNextFlag == null) {
				return -1;
			}
			dSumPrem = getSumPrem(pLCDutySchema);
			if (dSumPrem == -1) {
				return -1;
			}
			iPayTimes = getPayTimes(pLCDutySchema, mZTPoint);
			this.mBqCalBase.setSumPrem(dSumPrem);
			this.mBqCalBase.setPayTimes(iPayTimes);
			this.mBqCalBase.setPayNextFlag(sPayNextFlag);

			if (iPayTimes == -1) {
				return -1;
			}
			tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
			tCalculator.addBasicFactor("ZTPoint", mBqCalBase.getZTPoint()); // 退保点
			tCalculator.addBasicFactor("SumPrem", // 累计保费
					String.valueOf(mBqCalBase.getSumPrem()));
			tCalculator.addBasicFactor("RiskCode", // 险种编码
					mBqCalBase.getRiskCode());
			tCalculator.addBasicFactor("PayEndYear", // 缴费终止年期
					String.valueOf(mBqCalBase.getPayEndYear()));
			tCalculator.addBasicFactor("Interval", // 保单年度
					String.valueOf(mBqCalBase.getInterval()));
			tCalculator.addBasicFactor("PayTimes", // 缴费次数
					String.valueOf(mBqCalBase.getPayTimes()));
			tCalculator.addBasicFactor("PayNextFlag",
					mBqCalBase.getPayNextFlag());// 1-应交已交
			// 0-应交未交，
			tCalculator.addBasicFactor("Mult", // 份数
					String.valueOf(mBqCalBase.getMult()));
			tCalculator.addBasicFactor("Amnt", // 保额
					String.valueOf(mBqCalBase.getAmnt()));
			tCalculator.addBasicFactor("PayIntv", // 缴费间隔
					String.valueOf(mBqCalBase.getPayIntv()));
		} else if (sCalCodeType.equals("2")) {
			dCashValueCVt = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval, sCValiDate_Duty, mZTPoint);
			if (dCashValueCVt == -1) {
				return -1;
			}
			sPayNextFlag = getPayNextFlag(mLCPolSchema, mZTPoint);
			if (sPayNextFlag == null) {
				return -1;
			}
			dPrem = getDutyPrem(pLCDutySchema);
			if (dPrem == -1) {
				return -1;
			}
			sGetIntv = getGetIntv(pLCDutySchema);
			if (sGetIntv == null) {
				return -1;
			}

			this.mBqCalBase.setPrem(dPrem);
			this.mBqCalBase.setPayTimes(iPayTimes);
			this.mBqCalBase.setPayNextFlag(sPayNextFlag);
			this.mBqCalBase.setGetIntv(new Integer(sGetIntv));
			this.mBqCalBase.setCVt(dCashValueCVt);

			tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
			tCalculator.addBasicFactor("ZTPoint", mBqCalBase.getZTPoint()); // 退保点
			tCalculator.addBasicFactor("Prem", // 保费
					String.valueOf(mBqCalBase.getPrem())); // 不含加费
			tCalculator.addBasicFactor("GetIntv", // 领取间隔
					mBqCalBase.getGetIntv());
			tCalculator.addBasicFactor("Interval", // 保单年度
					String.valueOf(mBqCalBase.getInterval()));
			tCalculator.addBasicFactor("PayNextFlag", // 1-应交已交 0-应交未交
					mBqCalBase.getPayNextFlag());
			tCalculator.addBasicFactor("CVt", // 第t个保单年度的现金价值
					String.valueOf(mBqCalBase.getCVt()));
			tCalculator.addBasicFactor("Mult", // 份数
					String.valueOf(mBqCalBase.getMult()));
			tCalculator.addBasicFactor("Amnt", // 保额
					String.valueOf(mBqCalBase.getAmnt()));
			tCalculator.addBasicFactor("ReduceAmnt", // 冲减保额
					String.valueOf(mBqCalBase.getReduceAmnt()));
			tCalculator.addBasicFactor("VPU", // VPU
					String.valueOf(mBqCalBase.getVPU()));
			// add begin by yinhl 2005-10-23
			tCalculator.addBasicFactor("PayEndYear", // 缴费终止年期
					mBqCalBase.getPayEndYear());
			// add end by yinhl 2005-10-23
		} else if (sCalCodeType.equals("3")) {
			// dCashValueCV0 = getCashValueTable(mLCPolSchema, pLCDutySchema,
			// mInterval -1);
			// if (dCashValueCV0 == -1)
			// {
			// return -1;
			// }
			dCashValueCVt = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval, sCValiDate_Duty, mZTPoint);
			if (dCashValueCVt == -1) {
				return -1;
			}
			dCashValueCVt1 = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval + 1, sCValiDate_Duty, mZTPoint);
			if (dCashValueCVt1 == -1) {
				return -1;
			}
			iPayTimes = getPayTimes(pLCDutySchema, mZTPoint);
			if (iPayTimes == -1) {
				return -1;
			}
			dNextPrem = getDutyPrem(pLCDutySchema);
			if (dNextPrem == -1) {
				return -1;
			}

			dPrem = dNextPrem;
			sPayNextFlag = getPayNextFlag(mLCPolSchema, mZTPoint);
			if (sPayNextFlag == null) {
				return -1;
			}
			dSumPrem = getSumPrem(pLCDutySchema);
			if (dSumPrem == -1) {
				return -1;
			}

			dSumAmntBonus = getSumAmntBonus(mLCPolSchema.getPolNo(),
					mEdorAppDate);
			if (dSumAmntBonus == -1) {
				return -1;
			}

			// 判断是否已经进入领取期
			String sSatrtGet = startGet(mLCPolSchema, pLCDutySchema, mZTPoint);
			if (sSatrtGet == null) {
				return -1;
			} else if (sSatrtGet.equals("Y")) {
				String sNetCvalidate = PubFun.calDate(mCValiDate,
						mInterval + 1, "Y", null);
				// String sNextAge =
				// String.valueOf(PubFun.calInterval(mLCPolSchema.getInsuredBirthday(),
				// sNetCvalidate, "Y"));
				dAliveGet = getAliveGet(mLCPolSchema, pLCDutySchema,
						sNetCvalidate, true);
				dliveGet = dAliveGet;
				if (dAliveGet == -1) {
					return -1;
				}
				double dAvailableAmnt = 0.0;

				// 为了避免修改全局变量引起其他错误，故创建新的实例调用
				EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
				tEdorCalZT.setNextYearFlag(true);
				dAvailableAmnt = tEdorCalZT.getAvailableAmnt(mPolNo,
						sNetCvalidate); // 下一交费对应日
				if (dAvailableAmnt == -1) {
					mErrors.copyAllErrors(tEdorCalZT.mErrors);
					return -1;
				}
				dAliveGetRate = dAliveGet / dAvailableAmnt;
				dAliveGet = dAliveGetRate * mLCPolSchema.getAmnt(); // 基本保额对应的生存领取金

				// 为了避免修改全局变量引起其他错误，故创建新的实例调用
				tEdorCalZT = new EdorCalZT(mGlobalInput);
				dAvailableAmnt = tEdorCalZT.getAvailableAmnt(mPolNo, mZTPoint); // 退保时点
				if (dAvailableAmnt == -1) {
					mErrors.copyAllErrors(tEdorCalZT.mErrors);
					return -1;
				}
				// SumYearRate的值与AliveGetRate的值相同，直接赋值即可--modify begin by yinhl
				// 2005-10-11
				// dSumYearGet = getSumYearGet(mLCPolSchema.getPolNo(),
				// pLCDutySchema.getDutyCode(), mZTPoint);
				// if (dSumYearGet == -1)
				// {
				// return -1;
				// }
				// dSumYearGetRate = dSumYearGet / dAvailableAmnt;
				dSumYearGetRate = dAliveGetRate;
				// modify end by yinhl 2005-10-11
				dSumYearGet = dSumYearGetRate * mLCPolSchema.getAmnt(); // 基本保额对应的生存领取金

			}
			this.mBqCalBase.setPrem(dPrem);
			this.mBqCalBase.setPayTimes(iPayTimes);
			this.mBqCalBase.setPayNextFlag(sPayNextFlag);
			this.mBqCalBase.setCVt(dCashValueCVt);
			this.mBqCalBase.setCVt1(dCashValueCVt1);
			this.mBqCalBase.setNextPrem(dNextPrem);
			this.mBqCalBase.setSumPrem(dSumPrem);
			this.mBqCalBase.setliveGet(dliveGet);
			this.mBqCalBase.setAliveGet(dAliveGet);
			this.mBqCalBase.setAliveGetRate(dAliveGetRate);
			this.mBqCalBase.setSumYearGet(dSumYearGet);
			this.mBqCalBase.setSumYearGetRate(dSumYearGetRate);
			this.mBqCalBase.setSumAmntBonus(dSumAmntBonus);
			this.mBqCalBase.setEdorReasonCode(mLPEdorItemSchema
					.getEdorReasonCode());

			tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
			tCalculator.addBasicFactor("ZTPoint", mBqCalBase.getZTPoint()); // 退保点
			tCalculator.addBasicFactor("Prem", // 保费
					String.valueOf(mBqCalBase.getPrem())); // 不含加费
			tCalculator.addBasicFactor("SumPrem", // 累计保费
					String.valueOf(mBqCalBase.getSumPrem()));
			tCalculator.addBasicFactor("PayTimes", // 缴费次数
					String.valueOf(mBqCalBase.getPayTimes()));
			tCalculator.addBasicFactor("PayEndYear",// 缴费终止年期
					mBqCalBase.getPayEndYear());
			tCalculator.addBasicFactor("Interval", // 保单年度
					String.valueOf(mBqCalBase.getInterval()));
			tCalculator.addBasicFactor("CValiDate", // 起保日期
					mBqCalBase.getCValiDate());
			tCalculator.addBasicFactor("EndDate", // 终止日期
					mBqCalBase.getEndDate());
			tCalculator.addBasicFactor("NextPrem", // 当期应交保费
					String.valueOf(mBqCalBase.getNextPrem()));
			tCalculator.addBasicFactor("PayNextFlag", // 1-应交已交 0-应交未交 2-交费期满
					mBqCalBase.getPayNextFlag());
			// tCalculator.addBasicFactor("CV0", //第t-1个保单年度末的现金价值
			// String.valueOf(dCashValueCV0));
			tCalculator.addBasicFactor("CVt", // 第t个保单年度末的现金价值
					String.valueOf(mBqCalBase.getCVt()));
			tCalculator.addBasicFactor("CVt1", // 第t+1个保单年度末现金价值
					String.valueOf(mBqCalBase.getCVt1()));
			tCalculator.addBasicFactor("AliveGet", // 基本保额对应生存领取金额
					String.valueOf(mBqCalBase.getAliveGet()));
			tCalculator.addBasicFactor("AliveGetRate", // 生存领取金占有效保额的比例
					String.valueOf(mBqCalBase.getAliveGetRate()));
			tCalculator.addBasicFactor("SumYearGet", // 第k+1保单年度内退保前累计的基本保额对应生存领取金额
					String.valueOf(mBqCalBase.getSumYearGet()));
			tCalculator.addBasicFactor("SumYearGetRate", // 第k+1保单年度内退保前累计的生存领取金占有效保额的比例
					String.valueOf(mBqCalBase.getSumYearGetRate()));
			tCalculator.addBasicFactor("Mult", // 份数
					String.valueOf(mBqCalBase.getMult()));
			tCalculator.addBasicFactor("Amnt", // 保额
					String.valueOf(mBqCalBase.getAmnt()));
			tCalculator.addBasicFactor("ReduceAmnt", // 冲减保额
					String.valueOf(mBqCalBase.getReduceAmnt()));
			tCalculator.addBasicFactor("VPU", // VPU.
					String.valueOf(mBqCalBase.getVPU()));
			tCalculator.addBasicFactor("SumAmntBonus", // 累计红利金额
					String.valueOf(mBqCalBase.getSumAmntBonus()));
			tCalculator.addBasicFactor("EdorReasonCode", // 退保原因
					String.valueOf(mBqCalBase.getEdorReasonCode()));
			tCalculator.addBasicFactor("EdorType", // 保全项目
					mBqCalBase.getEdorType());
			tCalculator.addBasicFactor("liveGet", // 2006-8-10,为险种630添加不需要经过计算比率的基本保额对应生存领取金额
					String.valueOf(mBqCalBase.getliveGet()));
		} else if (sCalCodeType.equals("4")) {
			dCashValueCVt = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval, sCValiDate_Duty, mZTPoint);
			if (dCashValueCVt == -1) {
				return -1;
			}
			sPayNextFlag = getPayNextFlag(mLCPolSchema, mZTPoint);
			if (sPayNextFlag == null) {
				return -1;
			}
			dNextPrem = getDutyPrem(pLCDutySchema);
			if (dNextPrem == -1) {
				return -1;
			}
			dSumPrem = getSumPrem(pLCDutySchema);
			if (dSumPrem == -1) {
				return -1;
			}
			this.mBqCalBase.setPrem(dPrem);
			this.mBqCalBase.setPayNextFlag(sPayNextFlag);
			this.mBqCalBase.setCVt(dCashValueCVt);
			this.mBqCalBase.setNextPrem(dNextPrem);
			this.mBqCalBase.setSumPrem(dSumPrem);

			tCalculator.addBasicFactor("SumPrem", // 累计保费
					String.valueOf(mBqCalBase.getSumPrem()));
			tCalculator.addBasicFactor("Interval", // 保单年度
					String.valueOf(mBqCalBase.getInterval()));
			tCalculator.addBasicFactor("NextPrem", // 当期应交保费
					String.valueOf(mBqCalBase.getNextPrem()));
			tCalculator.addBasicFactor("PayNextFlag", // 1-应交已交 0-应交未交
					mBqCalBase.getPayNextFlag());
			tCalculator.addBasicFactor("CVt", // 第t个保单年度末的现金价值
					String.valueOf(mBqCalBase.getCVt()));
			tCalculator.addBasicFactor("Mult", // 份数
					String.valueOf(mBqCalBase.getMult()));
			tCalculator.addBasicFactor("Amnt", // 保额
					String.valueOf(mBqCalBase.getAmnt()));
			tCalculator.addBasicFactor("ReduceAmnt", // 冲减保额
					String.valueOf(mBqCalBase.getReduceAmnt()));
			tCalculator.addBasicFactor("VPU", // VPU
					String.valueOf(mBqCalBase.getVPU()));
			// add begin by yinhl 2005-10-23
			tCalculator.addBasicFactor("PayEndYear", // 缴费终止年期
					mBqCalBase.getPayEndYear());
			// add end by yinhl 2005-10-23
		} else if (sCalCodeType.equals("5")) {
			// 注意mInterval 传入的是 约近 的保单年度 简化产品定义，都使用此分支。
			mBqCalBase.setDuration(mInterval);
			dCashValueCVt = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval - 1, sCValiDate_Duty, mZTPoint); // 上1年度末现金价值
			if (dCashValueCVt == -1) {
				return -1;
			}
			// add by weikai 上年年末生存领取
			String getDate = FinFeePubFun.calOFDate(
					mLCPolSchema.getCValiDate(), mInterval - 1, "Y",
					mLCPolSchema.getCValiDate());

			// 对于生存金产生的帐户轨迹信息都记录责任信息
			String getdraw_sql = "select (case when sum(a) is not null then sum(a) else 0 end ) from ( select (case when sum(money) is not null then sum(money) else 0 end ) a from lcinsureacctrace where polno='"
					+ "?polno1?"
					+ "' and paydate='"
					+ "?getDate1?"
					+ "' and dutycode='"
					+ "?dutycode1?"
					+ "' "
					+ " and insuaccno in ('000005','000009') and moneytype in ('YF','EF') "
					+ " union all "
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) a from ljaGetDraw where PolNo='"
					+ "?PolNo2?"
					+ "' "
					+ " and GetDate='"
					+ "?getDate2?"
					+ "' and feefinatype in ('YF','EF') and  dutycode='"
					+ "?dutycode2?" + "') b";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(getdraw_sql);
            sqlbv.put("polno1", mLCPolSchema.getPolNo());
            sqlbv.put("getDate", getDate);
            sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
            sqlbv.put("PolNo2", mLCPolSchema.getPolNo());
            sqlbv.put("getDate2", getDate);
            sqlbv.put("dutycode2", pLCDutySchema.getDutyCode());
			ExeSQL tExeSQL = new ExeSQL();
			// dCashValueCVt = dCashValueCVt -
			// Double.parseDouble(tExeSQL.getOneValue(getdraw_sql));
			double dCashValueCVt_getdraw = Double.parseDouble(tExeSQL
					.getOneValue(sqlbv));
			// 扣除上年年末生存领取
			dCashValueCVt1 = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval, sCValiDate_Duty, mZTPoint); // 本年度末现金价值
			if (dCashValueCVt1 == -1) {
				return -1;
			}

			getDate = FinFeePubFun.calOFDate(mLCPolSchema.getCValiDate(),
					mInterval, "Y", mLCPolSchema.getCValiDate());

			// 对于生存金产生的帐户轨迹信息都记录责任信息
			// getdraw_sql = "select nvl(sum(money),0) from lcinsureacctrace
			// where polno='"+mLCPolSchema.getPolNo()
			// +"' and paydate='"+getDate+"' and
			// dutycode='"+pLCDutySchema.getDutyCode()+"' "
			// +" and insuaccno in ('000005','000009') and moneytype in
			// ('YF','EF') ";
			getdraw_sql = "select (case when sum(a) is not null then sum(a) else 0 end ) from ( select (case when sum(money) is not null then sum(money) else 0 end ) a from lcinsureacctrace where polno='"
					+ "?PolNo1?"
					+ "' and paydate='"
					+ "?getDate1?"
					+ "' and dutycode='"
					+ "?dutycode1?"
					+ "' "
					+ " and insuaccno in ('000005','000009') and moneytype in ('YF','EF') "
					+ " union all "
					+ " select (case when sum(GetMoney) is not null then sum(GetMoney) else 0 end) a from ljaGetDraw where PolNo='"
					+ "?PolNo2?"
					+ "' "
					+ " and GetDate='"
					+ "?getDate2?"
					+ "' and feefinatype in ('YF','EF') and  dutycode='"
					+ "?dutycode2?" + "') b";
			sqlbv=new SQLwithBindVariables();
            sqlbv.sql(getdraw_sql);
            sqlbv.put("polno1", mLCPolSchema.getPolNo());
            sqlbv.put("getDate", getDate);
            sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
            sqlbv.put("PolNo2", mLCPolSchema.getPolNo());
            sqlbv.put("getDate2", getDate);
            sqlbv.put("dutycode2", pLCDutySchema.getDutyCode());
			// 扣除年末生存领取
			// dCashValueCVt1 = dCashValueCVt1 -
			// Double.parseDouble(tExeSQL.getOneValue(getdraw_sql));
			double dCashValueCVt1_getdraw = Double.parseDouble(tExeSQL
					.getOneValue(sqlbv));
			sPayNextFlag = getPayNextFlag(mLCPolSchema, mZTPoint);
			if (sPayNextFlag == null) {
				return -1;
			}
			iPayTimes = getPayTimes(pLCDutySchema, mZTPoint);
			if (iPayTimes == -1) {
				return -1;
			}
			dNextPrem = getDutyPrem(pLCDutySchema);
			if (dNextPrem == -1) {
				return -1;
			}

			dPrem = dNextPrem;

			// add by jiaqiangli 2009-06-22 MS退保算法中没有用到sumprem的
			// comment by jiaqiangli 2009-06-22 长乐增额缴清报错
			// dSumPrem = getSumPrem(pLCDutySchema);
			if (dSumPrem == -1) {
				return -1;
			}
			String sCurCvalidate = PubFun.calDate(mCValiDate, mInterval - 1,
					"Y", null);
			// modify by jiaqiangli 保单年度末经过的天数与lis5.3保持一致 上午交续期保费，下午退保算经过一天
			// 此种算法的考虑是为了paytodate当天来退保时客户要求等于现价而不是小于现价
			// [2008-10-25,2009-10-25]2009-10-25及以后来退保
			// 退保点='2009-10-24'(paytodate-1为交至日期)
			// 经过天数为'2009-10-24'-'2008-10-25'+1=365
			// 若此时经过时间跨闰年的话 则经过天数有可能为366
			// select date'2009-01-31'-date'2008-02-01'+1 from dual;
			// 暂不考虑闰年的问题 ThroughDay=366使得365-ThroughDay出现负数
			int ThroughDay = PubFun.calInterval(sCurCvalidate, mZTPoint, "D") + 1;
			double MonsRate = getRate(mLCPolSchema, mZTPoint);

			// 退保点所在交费期对应的期初和期末的时刻点
			String rCurPaytoDate = mLCPolSchema.getPaytoDate();
			String rLastPaytoDate = PubFun.calDate(mLCPolSchema.getPaytoDate(),
					-mLCPolSchema.getPayIntv(), "M", null);
			;
			// 期初和期末的时刻点所在保单年度的经过天数
			// modify by jiaqiangli 2009-09-15 张丽娜与钱慧确认期初为0时生效 直接进行日期相减
			// 期末是24时，但这个日期是rCurPaytoDate-1，相当于也是做日期相减
			int ThroughDays = PubFun.calInterval(sCurCvalidate, rLastPaytoDate,
					"D");

			// modify by jiaqiangli 2009-09-11 参见新退保算法版本的期初期末时刻点的举例 此处就不做减一处理
			int ThroughDays1 = PubFun.calInterval(sCurCvalidate, rCurPaytoDate,
					"D");
			this.mBqCalBase.setAppAge(ThroughDays);
			this.mBqCalBase.setAppAg2(String.valueOf(ThroughDays1));
			this.mBqCalBase.setPrem(dPrem);
			this.mBqCalBase.setPayTimes(iPayTimes);
			this.mBqCalBase.setPayNextFlag(sPayNextFlag);
			this.mBqCalBase.setCVt(dCashValueCVt);
			this.mBqCalBase.setCVt1(dCashValueCVt1);
			this.mBqCalBase.setNextPrem(dNextPrem);
			this.mBqCalBase.setSumPrem(dSumPrem);
			this.mBqCalBase.setliveGet(dliveGet);
			this.mBqCalBase.setAliveGet(dAliveGet);
			this.mBqCalBase.setAliveGetRate(dAliveGetRate);
			this.mBqCalBase.setSumYearGet(dSumYearGet);
			this.mBqCalBase.setSumYearGetRate(dSumYearGetRate);
			this.mBqCalBase.setSumAmntBonus(dSumAmntBonus);
			this.mBqCalBase.setEdorReasonCode(mLPEdorItemSchema
					.getEdorReasonCode());
			this.mBqCalBase.setPayIntv(mLCPolSchema.getPayIntv());
			this.mBqCalBase.setAppntAge(mInterval);
			this.mBqCalBase.setGetLimit(dCashValueCVt);
			this.mBqCalBase.setGetRate(dCashValueCVt1);
			this.mBqCalBase.setCVtGetDraw(dCashValueCVt_getdraw);
			this.mBqCalBase.setCVt1GetDraw(dCashValueCVt1_getdraw);
			this.mBqCalBase.setThroughDay(ThroughDay);
			this.mBqCalBase.setThroughDays(ThroughDays);
			this.mBqCalBase.setThroughDays1(ThroughDays1);
			this.mBqCalBase.setMonsRate(MonsRate);
			this.mBqCalBase.setPayEndYear((int) exToYear(
					pLCDutySchema.getPayEndYear(),
					pLCDutySchema.getPayEndYearFlag()));
			this.mBqCalBase.setAmnt(dAmnt);

			tCalculator.addBasicFactor("ThroughDays",
					String.valueOf(mBqCalBase.getThroughDays()));// 期初时刻点所在保单年度的经过天数
			tCalculator.addBasicFactor("ThroughDays1",
					String.valueOf(mBqCalBase.getThroughDays1()));// 期末的时刻点所在保单年度的经过天数
			tCalculator.addBasicFactor("ThroughDay",
					String.valueOf(mBqCalBase.getThroughDay()));// 退保点所在保单年度的经过天数
			tCalculator.addBasicFactor("MonsRate",
					String.valueOf(mBqCalBase.getMonsRate()));// [已交保费次数（月/半年/季）/退保点所在年度末的应缴费次数（月/半年/季）]
			tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
			tCalculator.addBasicFactor("ZTPoint", mBqCalBase.getZTPoint()); // 退保点
			tCalculator.addBasicFactor("PayIntv",
					String.valueOf(mBqCalBase.getPayIntv())); // 交费方式
			tCalculator.addBasicFactor("Prem", // 保费
					String.valueOf(mBqCalBase.getPrem())); // 不含加费
			tCalculator.addBasicFactor("SumPrem", // 累计保费
					String.valueOf(mBqCalBase.getSumPrem()));
			tCalculator.addBasicFactor("PayEndYear",// 缴费终止年期
					String.valueOf(mBqCalBase.getPayEndYear()));
			tCalculator.addBasicFactor("Interval", // 保单年度
					String.valueOf(mBqCalBase.getInterval()));
			tCalculator.addBasicFactor("CValiDate", // 起保日期
					mBqCalBase.getCValiDate());
			tCalculator.addBasicFactor("NextPrem", // 当期应交保费
					String.valueOf(mBqCalBase.getNextPrem()));
			tCalculator.addBasicFactor("PayNextFlag", // 1-应交已交 0-应交未交 2-交费期满
					mBqCalBase.getPayNextFlag());
			tCalculator.addBasicFactor("PayTimes", // 缴费次数
					String.valueOf(mBqCalBase.getPayTimes()));
			tCalculator.addBasicFactor("CVt", // 第t个保单年度末的现金价值
					String.valueOf(mBqCalBase.getCVt()));
			tCalculator.addBasicFactor("CVtGetDraw",
					String.valueOf(mBqCalBase.getCVtGetDraw()));
			tCalculator.addBasicFactor("CVt1", // 第t+1个保单年度末现金价值
					String.valueOf(mBqCalBase.getCVt1()));
			tCalculator.addBasicFactor("CVt1GetDraw",
					String.valueOf(mBqCalBase.getCVt1GetDraw()));
			tCalculator.addBasicFactor("Mult", // 份数
					String.valueOf(mBqCalBase.getMult()));
			tCalculator.addBasicFactor("Amnt", // 保额
					String.valueOf(mBqCalBase.getAmnt()));

			tCalculator.addBasicFactor("ReduceAmnt", // 冲减保额
					String.valueOf(mBqCalBase.getReduceAmnt()));
			tCalculator.addBasicFactor("VPU", // VPU
					String.valueOf(mBqCalBase.getVPU()));
			tCalculator.addBasicFactor("EdorType", // 保全项目
					mBqCalBase.getEdorType());

			// add by jiaqiangli 2008-12-17 退保金额有的险种是千元保费，同时对于员工内部打折单
			double tFloatRate = mLCPolSchema.getFloatRate();

			if (tFloatRate < 1 && tFloatRate > 0) {
				this.mBqCalBase.setFloatRate(tFloatRate);
				tCalculator.addBasicFactor("FloatRate",
						String.valueOf(mBqCalBase.getFloatRate()));

			} else {
				this.mBqCalBase.setFloatRate(1);
				tCalculator.addBasicFactor("FloatRate",
						mBqCalBase.getFloatRate());

			}
			// add by jiaqiangli 2008-12-17 退保金额有的险种是千元保费，同时对于员工内部打折单
		} else if (sCalCodeType.equals("6")) {
			// dCashValueCV0 = getCashValueTable(mLCPolSchema, pLCDutySchema,
			// mInterval - 1);
			// if (dCashValueCV0 == -1)
			// {
			// return -1;
			// }
			dCashValueCVt = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval, sCValiDate_Duty, mZTPoint);
			if (dCashValueCVt == -1) {
				return -1;
			}
			dCashValueCVt1 = getCashValueTable(mLCPolSchema, pLCDutySchema,
					mInterval + 1, sCValiDate_Duty, mZTPoint);
			if (dCashValueCVt1 == -1) {
				return -1;
			}
			iPayTimes = getPayTimes(pLCDutySchema, mZTPoint);
			if (iPayTimes == -1) {
				return -1;
			}
			dPrem = getDutyPrem(pLCDutySchema);
			if (dPrem == -1) {
				return -1;
			}
			this.mBqCalBase.setPrem(dPrem);
			this.mBqCalBase.setPayTimes(iPayTimes);
			this.mBqCalBase.setPayNextFlag(sPayNextFlag);
			this.mBqCalBase.setCVt(dCashValueCVt);
			this.mBqCalBase.setCVt1(dCashValueCVt1);
			this.mBqCalBase.setNextPrem(dNextPrem);
			this.mBqCalBase.setSumPrem(dSumPrem);
			this.mBqCalBase.setliveGet(dliveGet);
			this.mBqCalBase.setAliveGet(dAliveGet);
			this.mBqCalBase.setAliveGetRate(dAliveGetRate);
			this.mBqCalBase.setSumYearGet(dSumYearGet);
			this.mBqCalBase.setSumYearGetRate(dSumYearGetRate);
			this.mBqCalBase.setSumAmntBonus(dSumAmntBonus);
			this.mBqCalBase.setEdorReasonCode(mLPEdorItemSchema
					.getEdorReasonCode());
			this.mBqCalBase.setAppntAge(mInterval);
			this.mBqCalBase.setPayEndYear((int) exToYear(
					pLCDutySchema.getPayEndYear(),
					pLCDutySchema.getPayEndYearFlag()));

			tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
			tCalculator.addBasicFactor("ZTPoint", mBqCalBase.getZTPoint()); // 退保点
			tCalculator.addBasicFactor("Prem", // 保费
					String.valueOf(mBqCalBase.getPrem())); // 不含加费
			tCalculator.addBasicFactor("PayIntv", // 缴费间隔
					String.valueOf(mBqCalBase.getPayIntv()));
			tCalculator.addBasicFactor("PayTimes", // 缴费次数
					String.valueOf(mBqCalBase.getPayTimes()));
			// tCalculator.addBasicFactor("CV0", //第t-1个保单年度末的现金价值
			// String.valueOf(dCashValueCV0));
			tCalculator.addBasicFactor("CVt", // 第t个保单年度末的现金价值
					String.valueOf(mBqCalBase.getCVt()));
			tCalculator.addBasicFactor("CVt1", // 第t+1个保单年度末现金价值
					String.valueOf(mBqCalBase.getCVt1()));
			tCalculator.addBasicFactor("Mult", // 份数
					String.valueOf(mBqCalBase.getMult()));
			tCalculator.addBasicFactor("Amnt", // 保额
					String.valueOf(mBqCalBase.getAmnt()));
			tCalculator.addBasicFactor("ReduceAmnt", // 冲减保额
					String.valueOf(mBqCalBase.getReduceAmnt()));
			tCalculator.addBasicFactor("VPU", // VPU
					String.valueOf(mBqCalBase.getVPU()));
		} else if (sCalCodeType.equals("8")) {
			// 算法尚未描述
		} else {
			CError.buildErr(this, "算法类型描述错误! 错误算法类型：" + sCalCodeType);
			return -1;
		}

		// this.mPrepareBOMZTBL.setCalBase(this.mCalBase);
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCPolSchema.getContNo());
		if (tLCContDB.getInfo()) {
			tLCContSchema.setSchema(tLCContDB.getSchema());
		}
		this.mLCContSchema = tLCContSchema;
		this.mBomListFlag = false;
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		String sCalResultValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "基本保额现金价值计算失败!");
			return -1;
		}
		if (sCalResultValue == null || sCalResultValue.equals("")) {
			sCalResultValue = "0";
		}
		double dCalResultValue = 0.0;
		try {
			dCalResultValue = Double.parseDouble(sCalResultValue);
		} catch (Exception e) {
			CError.buildErr(this, "基本保额现金价值计算结果错误!" + "错误结果：" + sCalResultValue);
			return -1;
		}

		return dCalResultValue;
	}

	/*
	 * [已交保费次数（月/半年/季）/退保点所在年度末的应缴费次数（月/半年/季）]
	 */
	private double getRate(LCPolSchema tLCPolSchema, String mZTPoint) {
		int day1;
		int day2;
		if (tLCPolSchema.getPayIntv() <= 0) {
			day1 = 1;
			day2 = 1;
		} else {
			day1 = PubFun.calInterval(tLCPolSchema.getCValiDate(),
					tLCPolSchema.getPaytoDate(), "M")
					/ tLCPolSchema.getPayIntv();
			// modify by jiaqiangli 2009-05-19 犹豫期内的退保放开导致的问题
			// modify by jiaqiangli 2009-05-19 当保单生效的当天进行退保导致分母为0 采用退保点的约进规则
			// day2 = PubFun.calInterval2(mCValiDate, mZTPoint, "Y")*12 /
			// tLCPolSchema.getPayIntv();
			day2 = (PubFun.calInterval(mCValiDate, mZTPoint, "Y") + 1) * 12
					/ tLCPolSchema.getPayIntv();
		}
		// bug fixed by jiaqiangli 2008-12-17 返回浮点数
		return (double) day1 / day2 * 1.00;
	}

	/**
	 * 计算红利保额现金价值(第七类算法)
	 * 
	 * @param pLMEdorBonusZTSchema
	 * @param pLCDutySchema
	 * @return double
	 */
	private double getBonusCashValue(LMEdorBonusZTSchema pLMEdorBonusZTSchema,
			LCDutySchema pLCDutySchema) {
		// 红利保额计算代码
		LMEdorBonusZTDB tLMEdorBonusZTDB = new LMEdorBonusZTDB();
		tLMEdorBonusZTDB.setSchema(pLMEdorBonusZTSchema);
		LMEdorBonusZTSet tLMEdorBonusZTSet = tLMEdorBonusZTDB.query();
		if (tLMEdorBonusZTDB.mErrors.needDealError()) {
			CError.buildErr(this, "获取红利保额现金价值计算代码失败!");
			return -1;
		}
		if (tLMEdorBonusZTSet == null || tLMEdorBonusZTSet.size() < 1) {
			CError.buildErr(this, "获取红利保额现金价值计算代码失败!");
			return -1;
		}
		String sCycPayCalCode = tLMEdorBonusZTSet.get(1).getCycPayCalCode();
		Calculator tCalculator = new Calculator();
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(sCycPayCalCode);
		// 准备计算要素
		double dBonusCashValueGVt1 = 0.0;
		double dBonusCashValueGVt = 0.0;
		double dSumAmntBonus = 0.0;
		double dAvailableAmnt = 0.0;
		double dAliveGet = 0.0;
		double dAliveGetRate = 0.0;
		double dSumYearGet = 0.0;
		double dSumYearGetRate = 0.0;
		String sCValiDate_Duty = pLCDutySchema.getCValiDate();
		if (sCValiDate_Duty == null || sCValiDate_Duty.trim().equals("")) {
			sCValiDate_Duty = mCValiDate_Pol;
		}

		dBonusCashValueGVt1 = getBonusCashValueTable(mLCPolSchema,
				pLCDutySchema, mInterval + 1);
		if (dBonusCashValueGVt1 == -1) {
			return -1;
		}
		dBonusCashValueGVt = getBonusCashValueTable(mLCPolSchema,
				pLCDutySchema, mInterval);
		if (dBonusCashValueGVt == -1) {
			return -1;
		}
		// 累计红利金额 处理
		dSumAmntBonus = getSumAmntBonus(mLCPolSchema.getPolNo(), mEdorAppDate);
		if (dSumAmntBonus == -1) {
			return -1;
		}

		// 判断是否已经进入领取期
		String sSatrtGet = startGet(mLCPolSchema, pLCDutySchema, mZTPoint);
		if (sSatrtGet == null) {
			return -1;
		} else if (sSatrtGet.equals("Y")) {
			String sNetCvalidate = PubFun.calDate(mCValiDate, mInterval + 1,
					"Y", null);
			// String sNextAge =
			// String.valueOf(PubFun.calInterval(mLCPolSchema.getInsuredBirthday(),
			// sNetCvalidate, "Y"));
			dAliveGet = getAliveGet(mLCPolSchema, null, sNetCvalidate, true);
			if (dAliveGet == -1) {
				return -1;
			}
			// 为了避免修改全局变量引起其他错误，故创建新的实例调用
			EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
			tEdorCalZT.setNextYearFlag(true);
			dAvailableAmnt = tEdorCalZT.getAvailableAmnt(mPolNo, sNetCvalidate); // 下一生效对应日
			if (dAvailableAmnt == -1) {
				mErrors.copyAllErrors(tEdorCalZT.mErrors);
				return -1;
			}
			dAliveGetRate = dAliveGet / dAvailableAmnt;
			dAliveGet = dAliveGetRate * mLCPolSchema.getAmnt();
			// 为了避免修改全局变量引起其他错误，故创建新的实例调用
			tEdorCalZT = new EdorCalZT(mGlobalInput);
			dAvailableAmnt = tEdorCalZT.getAvailableAmnt(mPolNo, mZTPoint); // 退保时点
			if (dAvailableAmnt == -1) {
				mErrors.copyAllErrors(tEdorCalZT.mErrors);
				return -1;
			}
			// //modify begin by yinhl
			// 2005-10-11:SumYearRate的值与AliveGetRate的值相同，直接赋值即可
			// dSumYearGet = getSumYearGet(mLCPolSchema.getPolNo(),
			// pLCDutySchema.getDutyCode(), mZTPoint);
			// if (dSumYearGet == -1)
			// {
			// return -1;
			// }
			// dSumYearGetRate = dSumYearGet / dAvailableAmnt;
			dSumYearGetRate = dAliveGetRate;
			// modify end by yinhl 2005-10-11
			dSumYearGet = dSumYearGetRate * mLCPolSchema.getAmnt(); // 基本保额对应的生存领取金
		}
		this.mBqCalBase.setSumAmntBonus(dSumAmntBonus);
		this.mBqCalBase.setGVt(dBonusCashValueGVt);
		this.mBqCalBase.setGVt1(dBonusCashValueGVt1);
		this.mBqCalBase.setAliveGet(dAliveGet);
		this.mBqCalBase.setAliveGetRate(dAliveGetRate);
		this.mBqCalBase.setSumYearGet(dSumYearGet);
		this.mBqCalBase.setSumYearGetRate(dSumYearGetRate);
		tCalculator.addBasicFactor("SumAmntBonus", // 累计红利金额
				String.valueOf(mBqCalBase.getSumAmntBonus()));
		tCalculator.addBasicFactor("ZTPoint", mBqCalBase.getZTPoint()); // 退保点
		tCalculator.addBasicFactor("CValiDate", mBqCalBase.getCValiDate()); // 起保日期
		tCalculator.addBasicFactor("GVt1", // 第t-1个保单年度末单位红利保额的现金价值
				String.valueOf(mBqCalBase.getGVt1()));
		tCalculator.addBasicFactor("GVt", // 第t个保单年度末单位红利保额的现金价值
				String.valueOf(mBqCalBase.getGVt()));
		tCalculator.addBasicFactor("AliveGet", // 基本保额对应生存领取金额
				String.valueOf(mBqCalBase.getAliveGet()));
		tCalculator.addBasicFactor("AliveGetRate", // 生存领取金占有效保额的比例
				String.valueOf(mBqCalBase.getAliveGetRate()));
		tCalculator.addBasicFactor("SumYearGet", // 第k+1保单年度内退保前累计的基本保额对应生存领取金额
				String.valueOf(mBqCalBase.getSumYearGet()));
		tCalculator.addBasicFactor("SumYearGetRate", // 第k+1保单年度内退保前累计的生存领取金占有效保额的比例
				String.valueOf(mBqCalBase.getSumYearGetRate()));
		tCalculator.addBasicFactor("Interval", // 保单年度
				String.valueOf(mBqCalBase.getInterval()));
		tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号

		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		String sBonusCashValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "红利保额现金价值计算失败!");
			return -1;
		}

		double dBonusCashValue = 0.0;
		try {
			dBonusCashValue = Double.parseDouble(sBonusCashValue);
		} catch (Exception e) {
			CError.buildErr(this, "红利保额现金价值计算结果错误!" + "错误结果：" + sBonusCashValue);
			return -1;
		}

		// 红利保额按比例分配
		double dRate = getDutyRate(pLCDutySchema);
		logger.debug("红利保额现金价值比例：" + dRate);

		dBonusCashValue = dBonusCashValue * dRate;

		return dBonusCashValue;
	}

	/**
	 * 万能险退保金额按账户退保(第八类算法)
	 * 
	 * @param sPolNo
	 * @param sBalaDate
	 * @return double
	 */
	public double getZTMoneyByAcc(String sPolNo, String sBalaDate) {
		double dZTMoneyByAcc = 0.0;

		if (mLCPolSchema.getPolTypeFlag() != null
				&& !mLCPolSchema.getPolTypeFlag().equals("0")) {
			// 团体账户结算
			TransferData tTransferData = getPolBalance(sPolNo, sBalaDate);
			if (tTransferData == null) {
				return -1;
			}
			Double dAccInterest = (Double) tTransferData
					.getValueByName("AccInterest");
			Double dAccBala = (Double) tTransferData.getValueByName("AccBala");
			return dAccBala.doubleValue();
		}

		// 个险万能账户结算
		// ===add===zhangtao===2006-11-16====帐户退保计算直接调用帐户结算类=====BGN===========
		// 查出该保单的所有帐户
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		String strSql = "select * from LCInsureAcc where PolNo ='" + "?sPolNo?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("sPolNo", sPolNo);
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "保单帐户查询失败!");
			return -1;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			CError.buildErr(this, "保单没有帐户数据!");
			return -1;
		}

		InsuAccBala tInsuAccBala = new InsuAccBala();
		String sZTBalaDate = sBalaDate; // 退保当天不计息,已经在帐户结算这样计算了
		LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet instLCInsureAccTraceSet = new LCInsureAccTraceSet();
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSet.get(i)
					.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo", tLCInsureAccSet.get(i)
					.getPolNo());
			tTransferData.setNameAndValue("BalaDate", sZTBalaDate);

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);

			if (!tInsuAccBala.submitData(tVData, "CashValue")) {
				mErrors.copyAllErrors(tInsuAccBala.mErrors);
				return -1;
			}

			tVData = tInsuAccBala.getResult();
			LCInsureAccSchema tLCInsureAccSchema = (LCInsureAccSchema) tVData
					.getObjectByObjectName("LCInsureAccSchema", 0);
			LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) tVData
					.getObjectByObjectName("LCInsureAccClassSet", 0);
			LCInsureAccTraceSet tLCInsureAccTraceSet = (LCInsureAccTraceSet) tVData
					.getObjectByObjectName("LCInsureAccTraceSet", 0);

			dZTMoneyByAcc += tLCInsureAccSchema.getInsuAccBala();

			for (int j = 1; j <= tLCInsureAccClassSet.size(); j++) {
				// 按子帐户增加一条负值退保轨迹记录
				Reflections ref = new Reflections();

				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				ref.transFields(tLCInsureAccTraceSchema,
						tLCInsureAccClassSet.get(j));
				String tLimit = PubFun.getNoLimit(tLCInsureAccClassSet.get(j)
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
				tLCInsureAccTraceSchema.setSerialNo(serNo);
				tLCInsureAccTraceSchema.setMoneyType("TB"); // 金额类型为 TB-退保金
				tLCInsureAccTraceSchema.setMoney(tLCInsureAccClassSet.get(j)
						.getInsuAccBala());
				tLCInsureAccTraceSchema.setFeeCode("000000");
				tLCInsureAccTraceSchema.setPayDate(sZTBalaDate);
				tLCInsureAccTraceSchema.setMakeDate(sZTBalaDate);
				tLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
				tLCInsureAccTraceSchema.setModifyDate(sZTBalaDate);
				tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
				tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				instLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
			updLCInsureAccSet.add(tLCInsureAccSchema);
			updLCInsureAccClassSet.add(tLCInsureAccClassSet);
			instLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
		}
		mResult.add(updLCInsureAccSet);
		mResult.add(updLCInsureAccClassSet);
		mResult.add(instLCInsureAccTraceSet);

		return dZTMoneyByAcc;
		// ===add===zhangtao===2006-11-16====帐户退保计算直接调用帐户结算类=====END===========
	}

	/**
	 * 判断该保单该账户有没有过结算历史纪录
	 * 
	 * @param sPolNo
	 * @return LCPremSet
	 */
	private String getJSFlag(String sPolNo, String sInsuAccNo) {
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		tLCInsureAccTraceDB.setPolNo(sPolNo);
		tLCInsureAccTraceDB.setInsuAccNo(sInsuAccNo);
		tLCInsureAccTraceDB.setMoneyType("LX");
		LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		if (tLCInsureAccTraceDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户表记价履历信息失败!");
			return null;
		}
		if (tLCInsureAccTraceSet == null || tLCInsureAccTraceSet.size() < 1) {
			return "N";
		}

		return "Y";
	}

	/**
	 * 查询保单下所有保费项
	 * 
	 * @param tPolNo
	 * @return LCPremSet
	 */
	private LCPremSet getLCPrem(String tPolNo) {
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(tPolNo);
		LCPremSet tLCPremSet = tLCPremDB.query();
		if (tLCPremDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保单保费项信息失败!");
			return null;
		}
		if (tLCPremSet == null || tLCPremSet.size() < 1) {
			CError.buildErr(this, "该保单下没有保费项信息!");
			return null;
		}

		return tLCPremSet;
	}

	/**
	 * 查询管理费表
	 * 
	 * @param tLCInsureAccTraceSchema
	 *            LCInsureAccTraceSchema
	 * @return LMRiskFeeSchema
	 */
	private LMRiskFeeSchema queryLMRiskFee(
			LCInsureAccTraceSchema tLCInsureAccTraceSchema) {
		LMRiskFeeSet tLMRiskFeeSet = new LMRiskFeeSet();
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		String sqlStr = "select * from lmriskfee where 1=1"
				+ " and InsuAccNo = '" + "?InsuAccNo?"
				+ "'" + " and payplancode = '"
				+ "?payplancode?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlStr);
		sqlbv.put("InsuAccNo", tLCInsureAccTraceSchema.getInsuAccNo());
		sqlbv.put("payplancode", tLCInsureAccTraceSchema.getPayPlanCode());
		logger.debug(sqlStr);
		tLMRiskFeeSet = tLMRiskFeeDB.executeQuery(sqlbv);
		if (tLMRiskFeeDB.mErrors.needDealError() == true) {
			CError.buildErr(this, "险种保险帐户管理费表查询失败!");
			return null;
		}
		if (tLMRiskFeeSet == null || tLMRiskFeeSet.size() == 0) {
			CError.buildErr(this, "险种保险帐户管理费表没有查询到相关数据!");
			return null;
		}

		return tLMRiskFeeSet.get(1);
	}

	// ====add====zhangtao====2006-08-03===账户价值计算==============BGN=================
	/**
	 * 计算险种下所有帐户余额（团体年金险）
	 * 
	 * @param sPolNo
	 *            险种号
	 * @param pBalaDate
	 *            结算日期
	 * @return TransferData 返回账户余额及本次结算利息
	 */
	public TransferData getPolBalance(String sPolNo, String sCurBalaDate) {
		return getAccBalance(sPolNo, null, sCurBalaDate);
	}

	/**
	 * 计算险种下单个账户余额（团体年金险）
	 * 
	 * @param sPolNo
	 *            险种号
	 * @param sInsuAccNo
	 *            账户号
	 * @param pBalaDate
	 *            结算日期
	 * @return TransferData 返回账户余额及本次结算利息
	 */
	public TransferData getAccBalance(String sPolNo, String sInsuAccNo,
			String sCurBalaDate) {
		String sBalaDate = PubFun.calDate(sCurBalaDate, -1, "D", null); // 退保当日不计息，往前推一天

		Reflections ref = new Reflections();
		// 结算后
		LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet instLCInsureAccTraceSet = new LCInsureAccTraceSet();

		double dZTInterest = 0.0;
		double dZTBala = 0.0;

		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(sPolNo);
		if (sInsuAccNo != null) // 计算险种下单个账户余额
		{
			tLCInsureAccDB.setInsuAccNo(sInsuAccNo);
		}
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();

		LCInsureAccSchema tLCInsureAcc;
		for (int k = 1; k <= tLCInsureAccSet.size(); k++) {
			double dAccInterest = 0.0;
			double dAccBala = 0.0;

			tLCInsureAcc = tLCInsureAccSet.get(k);

			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(sPolNo);
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAcc.getInsuAccNo());
			LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
					.query();

			LCInsureAccClassSchema tLCInsureAccClass;
			for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
				tLCInsureAccClass = tLCInsureAccClassSet.get(i);
				AccountManage tAccountManage = new AccountManage();
				TransferData tTransferData = tAccountManage.getAccClassBalance(
						tLCInsureAccClass, sBalaDate);
				if (tTransferData == null) {
					return null;
				}
				Double tAccInterest = (Double) tTransferData
						.getValueByName("AccInterest");
				Double tAccBala = (Double) tTransferData
						.getValueByName("AccBala");
				dAccBala += tAccBala.doubleValue();
				dAccInterest += tAccInterest.doubleValue();

				// 更新子账户结算信息
				tLCInsureAccClass.setBalaDate(sCurBalaDate);
				tLCInsureAccClass.setBalaTime(mCurrentTime);
				tLCInsureAccClass.setLastAccBala(tLCInsureAccClass
						.getInsuAccBala());
				tLCInsureAccClass.setInsuAccBala(tAccBala.doubleValue());
				tLCInsureAccClass.setModifyDate(mCurrentDate);
				tLCInsureAccClass.setModifyTime(mCurrentTime);
				updLCInsureAccClassSet.add(tLCInsureAccClass);

				// 记录本次结算LX轨迹
				LCInsureAccTraceSchema tLCInsureAccTrace = new LCInsureAccTraceSchema();
				ref.transFields(tLCInsureAccTrace, tLCInsureAccClass);
				String sLimit = PubFun.getNoLimit(tLCInsureAccClass
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
				tLCInsureAccTrace.setSerialNo(serNo);
				tLCInsureAccTrace.setMoney(tAccInterest.doubleValue());
				tLCInsureAccTrace.setMoneyType("LX");
				tLCInsureAccTrace.setPayDate(sCurBalaDate);
				tLCInsureAccTrace.setFeeCode("000000"); // 暂时置为六个0
				tLCInsureAccTrace.setMakeDate(mCurrentDate);
				tLCInsureAccTrace.setMakeTime(mCurrentTime);
				tLCInsureAccTrace.setModifyDate(mCurrentDate);
				tLCInsureAccTrace.setModifyTime(mCurrentTime);
				tLCInsureAccTrace.setOperator(mGlobalInput.Operator);
				instLCInsureAccTraceSet.add(tLCInsureAccTrace);

				// 记录本次出账TB轨迹
				tLCInsureAccTrace = new LCInsureAccTraceSchema();
				ref.transFields(tLCInsureAccTrace, tLCInsureAccClass);
				serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
				tLCInsureAccTrace.setSerialNo(serNo);
				tLCInsureAccTrace.setMoney(-tAccBala.doubleValue());
				tLCInsureAccTrace.setMoneyType("TB"); // 待定 zhangtao
				// 2006-08-03
				tLCInsureAccTrace.setPayDate(sCurBalaDate);
				tLCInsureAccTrace.setFeeCode("000000"); // 暂时置为六个0
				tLCInsureAccTrace.setMakeDate(mCurrentDate);
				tLCInsureAccTrace.setMakeTime(mCurrentTime);
				tLCInsureAccTrace.setModifyDate(mCurrentDate);
				tLCInsureAccTrace.setModifyTime(mCurrentTime);
				tLCInsureAccTrace.setOperator(mGlobalInput.Operator);
				instLCInsureAccTraceSet.add(tLCInsureAccTrace);

				if (!tLCInsureAccClass.getAccAscription().equals("0")) {
					// 归属个人部分,退还被保险人本人
					dZTBala += tAccBala.doubleValue();
					dZTInterest += tAccInterest.doubleValue();
				} else {
					// 未归属部分转入企业公共帐户
					LCPolDB aLCPolDB = new LCPolDB();
					aLCPolDB.setGrpPolNo(tLCInsureAccClass.getGrpPolNo());
					aLCPolDB.setPolTypeFlag("2");
					LCPolSet aLCPolSet = aLCPolDB.query();
					if (aLCPolSet.size() < 1) {
						CError.buildErr(this, "该保单下未查询到公共帐户!");
						return null;
					}
					// 公共账户只有一个
					LCPolSchema aLCPolSchema = aLCPolSet.get(1);
					tLCInsureAccClassDB = new LCInsureAccClassDB();
					tLCInsureAccClassDB.setPolNo(aLCPolSchema.getPolNo());
					tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccClass
							.getInsuAccNo());
					LCInsureAccClassSet pubLCInsureAccClassSet = tLCInsureAccClassDB
							.query();
					if (tLCInsureAccClassDB.mErrors.needDealError()) {
						CError.buildErr(this, "公共帐户信息查询失败!");
						return null;
					}

					tLCInsureAccTrace = new LCInsureAccTraceSchema();
					ref.transFields(tLCInsureAccTrace,
							pubLCInsureAccClassSet.get(1));
					serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);
					tLCInsureAccTrace.setSerialNo(serNo);
					tLCInsureAccTrace.setMoney(tAccBala.doubleValue());
					// tLCInsureAccTrace.setOtherNo(tLCInsureAccClass.getPolNo());
					tLCInsureAccTrace.setAccAlterNo(tLCInsureAccClass
							.getPolNo());
					tLCInsureAccTrace.setMoneyType("PG");
					tLCInsureAccTrace.setPayDate(sCurBalaDate);
					tLCInsureAccTrace.setFeeCode("000000"); // 暂时置为六个0
					tLCInsureAccTrace.setAccAscription("1"); // 转入公共帐户后为已归属
					tLCInsureAccTrace.setMakeDate(mCurrentDate);
					tLCInsureAccTrace.setMakeTime(mCurrentTime);
					tLCInsureAccTrace.setModifyDate(mCurrentDate);
					tLCInsureAccTrace.setModifyTime(mCurrentTime);
					tLCInsureAccTrace.setOperator(mGlobalInput.Operator);
					instLCInsureAccTraceSet.add(tLCInsureAccTrace);
				}
			}

			// 更新账户结算信息
			tLCInsureAcc.setBalaDate(sCurBalaDate);
			tLCInsureAcc.setBalaTime(mCurrentTime);
			tLCInsureAcc.setLastAccBala(tLCInsureAcc.getInsuAccBala());
			tLCInsureAcc.setInsuAccBala(dAccBala);
			tLCInsureAcc.setModifyDate(mCurrentDate);
			tLCInsureAcc.setModifyTime(mCurrentTime);
			updLCInsureAccSet.add(tLCInsureAcc);
		}

		mResult.add(updLCInsureAccSet);
		mResult.add(updLCInsureAccClassSet);
		mResult.add(instLCInsureAccTraceSet);

		// 将计算结果打包返回
		TransferData rTransferData = new TransferData();
		rTransferData.setNameAndValue("AccInterest", dZTInterest);
		rTransferData.setNameAndValue("AccBala", dZTBala);
		return rTransferData;
	}

	// ====add====zhangtao====2006-08-03===账户价值计算==============END=================

	/**
	 * 根据传入的保单结算时点计算退保点
	 * 
	 * @param sCalDate
	 * @return String
	 */
	private String getZTPoint(LCPolSchema pLCPolSchema, String sCalDate) {
		// 理赔计算累计红利
		if (mCalDateType != null && mCalDateType.equals("LP")) {
			return sCalDate;
		}

		if (mLCPolSchema.getAppFlag().trim().equals("4")) {
			String sql = "select min(startdate) from ( "
					+ " select startdate from lccontstate where statetype = 'Terminate' and state = '1' "
					+ "  and enddate is null and polno = '"
					+ "?polno?" + "' " + " union select to_date('"
					+ "?sCalDate?" + "','yyyy-mm-dd') from dual where 1=1 ) g";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("polno", mLCPolSchema.getPolNo());
			sqlbv.put("sCalDate", sCalDate);
			ExeSQL tExeSQL = new ExeSQL();
			sCalDate = tExeSQL.getOneValue(sqlbv);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "保单终止日期查询失败!");
				return null;
			}
			if (sCalDate == null || sCalDate.equals("")) {
				CError.buildErr(this, "保单终止日期查询失败!");
				return null;
			}
			if (sCalDate.length() > 10) {
				sCalDate = sCalDate.substring(0, 10);
			}
		} // 到此，sCalDate 为退保时点与终止时点较早者

		String sZTPoint = "";

		if (pLCPolSchema.getPayIntv() == 0 || pLCPolSchema.getPayIntv() == -1) {
			return sCalDate; // 趸交、不定期交
		}
		int intv = PubFun.calInterval(pLCPolSchema.getPayEndDate(),
				pLCPolSchema.getPaytoDate(), "D");
		if (intv >= 0) // 交费年期已满
		{
			return sCalDate;
		}
		// 判断保单是否失效
		String sql = " select count(*) from lccontstate "
				+ " where statetype in('Available')  "
				+ " and state = '1' and " + " ( (startdate <= '" + "?startdate1?"
				+ "' and enddate >= '" + "?enddate?" + "' ) or (startdate <= '"
				+ "?startdate2?" + "' and enddate is null ))  " + " and polno = '"
				+ "?polno?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("startdate1", sCalDate);
		sqlbv.put("enddate", sCalDate);
		sqlbv.put("startdate2", sCalDate);
		sqlbv.put("polno", pLCPolSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sdisAvailable = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询保单状态失败!");
			return null;
		}
		if (sdisAvailable == null || sdisAvailable.trim().equals("")) {
			CError.buildErr(this, "查询保单状态失败!");
			return null;
		}
		if (sdisAvailable.equals("0")) // 保单有效
		{
			// 判断是否交费
			String sPayNextFlag = getPayNextFlag(pLCPolSchema, sCalDate);
			if (sPayNextFlag == null) {
				return null;
			}
			if (sPayNextFlag.equals("1")) // 1-应交已交
			{
				sZTPoint = sCalDate;
			} else if (sPayNextFlag.equals("0")) // 0-应交未交
			{
				// 宽限期起期（交费对应日次日）前一日
				// modify by jiaqiangli 2008-12-18 交至日期前一天
				sZTPoint = PubFun.calDate(pLCPolSchema.getPaytoDate(), -1, "D",
						null);
			}
		} else // 保单失效
		{
			// 279险种特殊处理
			String is279 = isRisk(mLCPolSchema.getRiskCode(), "279");
			if (is279 == null) {
				return null;
			} else if (is279.equals("Y")) {
				// 退保计算时点应该为申请时点
				sZTPoint = sCalDate;
				// sZTPoint = CalLapseDate(mLCPolSchema.getRiskCode(),
				// pLCPolSchema.getPaytoDate());
				// if (sZTPoint == null)
				// {
				// return null;
				// }
			} else {
				// 宽限期起期（交费对应日次日）前一日
				// modify by jiaqiangli 2008-12-18 交至日期前一天
				sZTPoint = PubFun.calDate(pLCPolSchema.getPaytoDate(), -1, "D",
						null);

				intv = PubFun.calInterval(sCalDate, sZTPoint, "D");
				if (intv >= 0) // 交至比计算时点还晚，直接返回计算时点（失效后又做复效的情况）
				{
					return sCalDate;
				}
			}
		}

		// comment by jiaqiangli 2008-12-18 退保生效日规则点总结
		// 保险期内趸交或交费期满 ztpoint=edorappdate
		// 保险期内期交且未交费满期 ztpoint=paytodate-1(约进算法)
		// comment by jiaqiangli 2008-12-18 退保生效日规则点总结

		logger.debug("== 现金价值计算时点：" + sZTPoint);
		return sZTPoint;
	}

	/**
	 * 查询基本保额现金价值表
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @param pLCDutySchema
	 *            责任项信息
	 * @param pInterval
	 *            保单年度
	 * @param sCValidate
	 *            保单生效日期
	 * @param sZTPoint
	 *            退保日期
	 * @return double
	 */
	public double getCashValueTable(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, int pInterval, String sCValidate,
			String sZTPoint) {
		String sGetIntv = getGetIntv(pLCDutySchema);
		if (sGetIntv == null) {
			return -1;
		}
		if (sGetIntv.equals("") || sGetIntv == null) {

		} else {
			this.mBqCalBase.setGetIntv(new Integer(sGetIntv));
		}
		return getCashValueTable(pLCPolSchema, pLCDutySchema, pInterval,
				sCValidate, sZTPoint, sGetIntv);
	}

	/**
	 * 查询基本保额现金价值表
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @param pLCDutySchema
	 *            责任项信息
	 * @param pInterval
	 *            保单年度
	 * @param sCValidate
	 *            保单生效日期
	 * @param sZTPoint
	 *            退保日期
	 * @return double
	 */
	public double getCashValueTable(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, int pInterval, String sCValidate,
			String sZTPoint, String sGetIntv) {
		double dCashValue = 0.0;
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===BGN=====
		// if (pInterval == -1)
		// {
		// CError.buildErr(this, "保单年度不能为负值!");
		// return -1;
		// }
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===END=====
		String sDutyCode = pLCDutySchema.getDutyCode();
		if (sDutyCode.length() == 8) // 支持正常增额 modify by wk 2008-10-22
		{
			sDutyCode = sDutyCode.substring(0, 6);
		} else if (sDutyCode.length() == 10
				&& (sDutyCode.substring(6, 7).equals("1")))// 支持红利增额交清 modify
		// by wk 2008-10-22
		{
			sDutyCode = sDutyCode.substring(0, 7);
		}
		// 从LMEdorCal表中查询出查现金价值表的公式
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(pLCPolSchema.getRiskCode());
		tLMEdorCalDB.setDutyCode(sDutyCode);
		tLMEdorCalDB.setCalType("CashValue");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "现金价值表查询代码查询失败!");
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			CError.buildErr(this, "没有查到现金价值表查询代码!");
			return -1;
		}

		int iPayTimes = 0;
		iPayTimes = getPayTimes(pLCDutySchema, mZTPoint);
		if (iPayTimes == -1) {
			return -1;
		}
		this.mBqCalBase.setPayTimes(iPayTimes);
		this.mBqCalBase.setInterval(pInterval);
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		// 准备计算要素
		tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
		tCalculator.addBasicFactor("SignDate", mBqCalBase.getSignDate()); // 签单日期
		tCalculator.addBasicFactor("Interval", // 保单年度
				String.valueOf(mBqCalBase.getInterval()));
		tCalculator.addBasicFactor("Sex", // 被保人性别
				mBqCalBase.getSex());
		tCalculator.addBasicFactor("AppAge", // 投保年龄
				String.valueOf(mBqCalBase.getAppAge()));
		tCalculator.addBasicFactor("PayEndYear",// 缴费终止年期
				String.valueOf(mBqCalBase.getPayEndYear()));
		tCalculator.addBasicFactor("PayEndYearFlag", // 缴费终止年期单位
				mBqCalBase.getPayEndYearFlag());
		tCalculator.addBasicFactor("InsuYear", // 保险期间
				String.valueOf(mBqCalBase.getInsuYear()));
		tCalculator.addBasicFactor("InsuYearFlag", // 保险期间单位
				mBqCalBase.getInsuYearFlag());
		tCalculator.addBasicFactor("GetStartAge", // 起领年龄
				String.valueOf(mBqCalBase.getGetYear()));
		tCalculator.addBasicFactor("PayIntv", // 缴费间隔
				String.valueOf(mBqCalBase.getPayIntv()));
		tCalculator.addBasicFactor("GetIntv", // 领取间隔
				mBqCalBase.getGetIntv());
		tCalculator.addBasicFactor("GetYear", // 领取年龄年期
				String.valueOf(mBqCalBase.getGetYear()));
		tCalculator.addBasicFactor("Standbyflag1", // 连带被保人客户号
				mBqCalBase.getStandByFlag1());
		tCalculator.addBasicFactor("ContNo", // 合同保单号
				mBqCalBase.getContNo());
		tCalculator.addBasicFactor("CValiDate", // 生效日
				String.valueOf(mBqCalBase.getCValiDate()));
		tCalculator.addBasicFactor("ZTPoint", sZTPoint); // 退保点
		tCalculator.addBasicFactor("PayTimes", // 缴费次数
				String.valueOf(mBqCalBase.getPayTimes()));
		tCalculator.addBasicFactor("RZTPoint", // 系统当前日期,115退保点
				PubFun.getCurrentDate());
		tCalculator.addBasicFactor("Duration", mBqCalBase.getDuration());
		// 计算要素尚不完全确定
		this.mBqCalBase.setAmnt(0);// 为了避免出现空指针
		this.mBqCalBase.setSex(pLCPolSchema.getInsuredSex());
		this.mBqCalBase.setAppAge(pLCPolSchema.getInsuredAppAge());
		this.mBqCalBase.setAppAg2(String.valueOf(pLCPolSchema
				.getInsuredAppAge()));// 为了避免出现空指针
		this.mBqCalBase.setPayIntv(pLCDutySchema.getPayIntv());
		this.mBqCalBase.setPayEndYear(pLCDutySchema.getPayEndYear());
		this.mBqCalBase.setAppntAge(pInterval);// 保单年度
		// this.mPrepareBOMZTBL.setCalBase(this.mBqCalBase);
		/* 以下可能重复定义了schema */
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLCPolSchema.getContNo());
		if (tLCContDB.getInfo()) {
			tLCContSchema.setSchema(tLCContDB.getSchema());
		}
		this.mLCContSchema = tLCContSchema;
		mBomListFlag = false;
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 使用规则引擎BOMLIST
		String sCashValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "现金价值表查询失败!");
			return -1;
		}
		if (sCashValue == null || sCashValue.trim().equals("")) {
			// CError.buildErr(this, "现金价值表查询结果为空!");
			// return -1;
			sCashValue = "0";
		}
		try {
			dCashValue = Double.parseDouble(sCashValue);
		} catch (Exception e) {
			CError.buildErr(this, "现金价值表查询结果错误!" + "错误结果：" + sCashValue);
			return -1;
		}
		return dCashValue;
	}

	/**
	 * 查询红利保额现金价值表
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @param pInterval
	 *            保单年度
	 * @return double
	 */
	public double getBonusCashValueTable(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, int pInterval) {

		String sDutyCode = pLCDutySchema.getDutyCode();
		if (sDutyCode.length() > 6) {
			sDutyCode = sDutyCode.substring(0, 6);
		}
		this.mBqCalBase.setInterval(pInterval);
		double dBonusCashValue = 0.0;
		// 从LMEdorCal表中查询出查红利现金价值表的公式
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(pLCPolSchema.getRiskCode());
		tLMEdorCalDB.setDutyCode(sDutyCode);
		tLMEdorCalDB.setCalType("CV-D");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "红利保额现金价值表查询代码查询失败!");
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			CError.buildErr(this, "没有查到红利保额现金价值表查询代码!");
			return -1;
		}

		if (pInterval == 0) // 第０保单年度没有分红
		{
			logger.debug("== 第０保单年度没有分红 ==");
			return 0.0;
		}

		Calculator tCalculator = new Calculator();
		this.mBomListFlag = false;
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		// 准备计算要素
		tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo()); // 保单号
		tCalculator.addBasicFactor("Interval", // 保单年度
				String.valueOf(mBqCalBase.getInterval()));
		tCalculator.addBasicFactor("Sex", // 被保人性别
				mBqCalBase.getSex());
		tCalculator.addBasicFactor("AppAge", // 投保年龄
				String.valueOf(mBqCalBase.getAppAge()));
		tCalculator.addBasicFactor("PayEndYear",// 缴费终止年期
				String.valueOf(mBqCalBase.getPayEndYear()));
		tCalculator.addBasicFactor("InsuYear", // 保险期间
				String.valueOf(mBqCalBase.getInsuYear()));
		tCalculator.addBasicFactor("GetStartAge", // 起领年龄
				String.valueOf(mBqCalBase.getGetYear()));
		tCalculator.addBasicFactor("GetYear", // 领取年龄年期
				String.valueOf(mBqCalBase.getGetYear()));
		tCalculator.addBasicFactor("PayIntv", // 缴费间隔
				String.valueOf(mBqCalBase.getPayIntv()));
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		String sBonusCashValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "红利保额现金价值表查询失败!");
			return -1;
		}
		if (sBonusCashValue == null || sBonusCashValue.trim().equals("")) {
			CError.buildErr(this, "红利保额现金价值表查询结果为空!");
			return -1;
		}
		try {
			dBonusCashValue = Double.parseDouble(sBonusCashValue);
		} catch (Exception e) {
			CError.buildErr(this, "保单红利保额现金价值表查询结果错误!" + "错误结果："
					+ sBonusCashValue);
			return -1;
		}
		return dBonusCashValue;
	}

	/**
	 * 判断当期保费是否已交 1-应交已交 0-应交未交
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @return String
	 */
	private String getPayNextFlag(LCPolSchema pLCPolSchema, String sCalDate) {
		// 暂时只是判断交至日期与退保点的关系-- 以及是否交费期满
		// 疑问：是否要去判断应收实收和暂交费？ 如果有暂交费没有核销算不算已交 zhangtao 2005-07-11
		// 确认：暂交费未核销的不算已交
		int intv = PubFun.calInterval(pLCPolSchema.getPayEndDate(),
				pLCPolSchema.getPaytoDate(), "D");
		if (intv >= 0) // 交费年期已满
		{
			return "2";
		}
		String sPayNextFlag = "";
		if (pLCPolSchema.getPaytoDate() == null
				|| pLCPolSchema.getPaytoDate().equals("")) {
			CError.buildErr(this, "保单交费对应日为空!");
			return null;
		}

		if (pLCPolSchema.getPayIntv() == 0) // 趸交
		{
			sPayNextFlag = "2"; // 1-应交已交
			return sPayNextFlag;
		}

		int intval = PubFun.calInterval(sCalDate, pLCPolSchema.getPaytoDate(),
				"D");

		if (intval > 0) {
			sPayNextFlag = "1"; // 1-应交已交
		} else {
			sPayNextFlag = "0"; // 0-应交未交
		}

		return sPayNextFlag;
	}

	/**
	 * 交费次数
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @return int
	 */
	public int getPayTimes(LCDutySchema pLCDutySchema, String sCalDate) {
		int iPayTimes = 0;
		// 交费次数只能从实收表里取，不能从应收表里取
		String sql = " select max(paycount) from LJAPayPerson "
				+ " where paytype in ('ZC','RE','AA','NS','NI') and polno = '"
				+ "?polno?" + "' and dutycode = '"
				+ "?dutycode?" + "' and lastpaytodate <= '"
				+ "?sCalDate?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLCDutySchema.getPolNo());
		sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
		sqlbv.put("sCalDate", sCalDate);
		ExeSQL tExeSQL = new ExeSQL();
		String sPayTimes = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "交费次数查询失败!");
			return -1;
		}
		if (sPayTimes == null || sPayTimes.equals("")) {
			sPayTimes = "0";
		}
		try {
			iPayTimes = Integer.parseInt(sPayTimes);
		} catch (Exception e) {
			CError.buildErr(this, "交费次数查询结果错误!" + "错误结果：" + sPayTimes);
			return -1;
		}

		return iPayTimes;
	}

	/**
	 * 累计保费（不含加费）
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	private double getSumPrem(LCDutySchema pLCDutySchema) {
		double dSumPrem = 0.0;

		if (mSumPremFlag.equals("Y")) {
			// 累计保费计算标志（如果以责任项为入口计算现金价值，则累计保费直接用传入值）
			return pLCDutySchema.getSumPrem();
		}
		// =======del=====zhangtao========2005-09-23======================BGN============
		// double dSumPay = 0.0;
		// //查询保单责任项交费记录
		// String sql = " select sum(SumActuPayMoney) from ljapayperson " +
		// " where polno = '" + pLCDutySchema.getPolNo() +
		// "' and dutycode = '" + pLCDutySchema.getDutyCode() +
		// "' and trim(payplancode) not like '000000%' "; //不含加费
		// ExeSQL tExeSQL = new ExeSQL();
		// String sSumPay = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保单责任项交费记录查询失败!");
		// return -1;
		// }
		// if (sSumPay == null || sSumPay.equals(""))
		// {
		// sSumPay = "0";
		// }
		// try
		// {
		// dSumPay = Double.parseDouble(sSumPay);
		// }
		// catch (Exception e)
		// {
		// CError.buildErr(this, "保单责任项交费记录查询结果错误!" +
		// "错误结果：" + sSumPay);
		// return -1;
		// }
		//
		// double dSumEdor = 0.0;
		// //查询保全变更中涉及到的补交保费与退费（不含加费）
		// sql = " select sum(getmoney) from ljagetendorse " +
		// " where polno = '" + pLCDutySchema.getPolNo() +
		// "' and dutycode = '" + pLCDutySchema.getDutyCode() +
		// "' and feefinatype in ('TF', 'BF') " +
		// " and subfeeoperationtype not in " +
		// " ('" + BqCode.Pay_AppntAddPremHealth +
		// "', '" + BqCode.Pay_AppntAddPremOccupation +
		// "', '" + BqCode.Pay_InsurAddPremHealth +
		// "', '" + BqCode.Pay_InsurAddPremOccupation + "')";
		// tExeSQL = new ExeSQL();
		// String sSumEdor = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "保全保费补退历史查询失败!");
		// return -1;
		// }
		// if (sSumEdor == null || sSumEdor.equals(""))
		// {
		// sSumEdor = "0";
		// }
		// try
		// {
		// dSumEdor = Double.parseDouble(sSumEdor);
		// }
		// catch (Exception e)
		// {
		// CError.buildErr(this, "保全保费补退历史查询结果错误!" +
		// "错误结果：" + sSumEdor);
		// return -1;
		// }
		//
		// dSumPrem = dSumPay + dSumEdor;
		// =======del=====zhangtao========2005-09-23======================END============
		// =======add=====zhangtao========2005-09-23======================BGN============
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (!tBqPolBalBL.getBasicPremPerDuty2(pLCDutySchema.getPolNo(),
				pLCDutySchema.getDutyCode(), pLCDutySchema.getPaytoDate())) {
			CError.buildErr(this, "累计保费计算失败!");
			return -1;
		}
		dSumPrem = tBqPolBalBL.getCalResult();
		// =======add=====zhangtao========2005-09-23======================END============
		return dSumPrem;
	}

	/**
	 * 当期保费
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	private double getDutyPrem(LCDutySchema pLCDutySchema) {
		double dNextPrem = 0.0;
		String sNextPrem = "";
		String sql = "";
		// String sTable = "";
		//
		// String sPayNextFlag = getPayNextFlag(pLCDutySchema);
		// if (sPayNextFlag.equals("1"))
		// {
		// //当期保费已交，去实收表里取
		// sTable = "LJAPayPerson";
		// }
		// else if (sPayNextFlag.equals("0"))
		// {
		// //当期保费未交，去应收表里取
		// sTable = "LJSPayPerson";
		// }
		// else
		// {
		// CError.buildErr(this, "是否交费不明确!");
		// return -1;
		// }
		//
		// sql = " select SumDuePayMoney from " + sTable +
		// " where polno = '" + pLCDutySchema.getPolNo() +
		// "' and dutycode = '" + pLCDutySchema.getDutyCode() +
		// "' and LastPayToDate < '" + mZTPoint +
		// "' and CurPayToDate >= '" + mZTPoint + "'";
		if (mSumPremFlag.equals("Y")) {
			// 如果以责任项为入口计算现金价值，则保费直接用传入值
			return pLCDutySchema.getPrem();
		}
		// 直接从保费项表里统计
		sql = " select sum(prem) from lcprem " + " where polno = '"
				+ "?polno?" + "' and dutycode = '"
				+ "?dutycode?" + "' and paystartdate <= '?mZTPoint?' and payenddate >= '?mZTPoint?' and trim(payplantype) = '0' "; // 不包含加费
		// Q：豁免保费是否应该除去？（根据免交起领日期） zhangtao 2005-07-14
		// A：不用考虑，即使豁免了这里也应该算 zhangtao 2005-12-03
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLCDutySchema.getPolNo());
		sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
		sqlbv.put("mZTPoint", mZTPoint);
		ExeSQL tExeSQL = new ExeSQL();
		sNextPrem = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "当期保费查询失败!");
			return -1;
		}
		if (sNextPrem == null || sNextPrem.equals("")) {
			return 0;
		}
		try {
			dNextPrem = Double.parseDouble(sNextPrem);
		} catch (Exception e) {
			CError.buildErr(this, "当期保费查询结果错误!" + "错误结果：" + sNextPrem);
			return -1;
		}

		return dNextPrem;
	}

	/**
	 * 计算理赔冲减保额 （按险种计算）
	 * 
	 * @param sPolNo
	 * @param sRiskCode
	 * @return double 不需要冲减的险种返回 0
	 */
	public double getReduceAmnt(String sContNo, String sPolNo, String sDate) {
		return getReduceAmnt(sContNo, sPolNo, null, sDate);
	}

	/**
	 * 计算理赔冲减保额 （按责任计算）
	 * 
	 * @param sContNo
	 * @param sPolNo
	 * @param sDutyCode
	 * @param sDate
	 * @return double 不需要冲减的险种返回 0
	 */
	public double getReduceAmnt(String sContNo, String sPolNo,
			String sDutyCode, String sDate) {
		double dReduceAmnt = 0.0;

		String sDealType = "D"; // Duty 按责任计算
		if (sDutyCode == null) {
			sDealType = "P"; // Pol 按险种计算
		}
		LLClaimExternalInterfaceBL tLLClaimExternalInterfaceBL = new LLClaimExternalInterfaceBL();
		dReduceAmnt = tLLClaimExternalInterfaceBL.getPosAddUpPay(sContNo,
				sPolNo, sDutyCode, sDate, sDealType);
		if (dReduceAmnt == -1) {
			return -1;
		}
		return dReduceAmnt;
		// =====DEL=======zhangtao===2005-11-28====暂时不计算，等理赔确定========BGN========
		// String sql =
		// " select sum(SumMoney) from lcget where LiveGetType not in ('0') " +
		// " and polno = '" + sPolNo + "'" ;
		// if (sDutyCode != null)
		// {
		// sql += " and dutycode = '" + sDutyCode + "' "; //按责任计算
		// }
		// ExeSQL tExeSQL = new ExeSQL();
		// String sReduceAmnt = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "冲减保额查询失败!");
		// return -1;
		// }
		// if (sReduceAmnt == null || sReduceAmnt.equals(""))
		// {
		// sReduceAmnt = "0";
		// }
		// try
		// {
		// dReduceAmnt = Double.parseDouble(sReduceAmnt);
		// }
		// catch (Exception e)
		// {
		// CError.buildErr(this, "冲减保额查询结果错误!" +
		// "错误结果：" + sReduceAmnt);
		// return -1;
		// }
		// return dReduceAmnt;
		// =====DEL=======zhangtao===2005-11-28====暂时不计算，等理赔确定========END========

		// =====DEL=======zhangtao===2005-11-25====由于理赔变动，改变计算方法========BGN====
		// //判断该险种是否需要冲减
		// String sql = " select 1 from LMRiskSort where RiskSortType = '14' " +
		// " and riskcode = '" + sRiskCode + "'";
		// ExeSQL tExeSQL = new ExeSQL();
		// String sRiskSortType = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "险种定义查询失败!");
		// return -1;
		// }
		// if (sRiskSortType != null && sRiskSortType.equals("1")) //需要冲减
		// {
		// sql = " select b.realpay from llclaim a, llclaimdetail b " +
		// " where a.clmno = b.clmno and a.givetype = 0 and b.givetype = 0 " +
		// " and a.clmstate in ('50','60') and b.polno = '" + sPolNo + "' ";
		// if (sDutyCode != null)
		// {
		// sql += " and b.dutycode = '" + sDutyCode + "' "; //按责任计算
		// }
		// tExeSQL = new ExeSQL();
		// String sReduceAmnt = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "冲减保额查询失败!");
		// return -1;
		// }
		// if (sReduceAmnt == null || sReduceAmnt.equals(""))
		// {
		// sReduceAmnt = "0";
		// }
		// try
		// {
		// dReduceAmnt = Double.parseDouble(sReduceAmnt);
		// }
		// catch (Exception e)
		// {
		// CError.buildErr(this, "冲减保额查询结果错误!" +
		// "错误结果：" + sReduceAmnt);
		// return -1;
		// }
		// return dReduceAmnt;
		// }
		// else
		// {
		// return 0; //不需要冲减
		// }
		// =====DEL=======zhangtao===2005-11-25====由于理赔变动，改变计算方法========END====
	}

	/**
	 * 查询责任保额
	 * 
	 * @param sDutyCode
	 * @return String
	 */
	private double getDutyRate(LCDutySchema pLCDutySchema) {
		double dDutyRate = 0.0;
		String sql = "";
		double dDuty = 0.0;
		double dAllDuty = 0.0;

		// 判断该险种责任是按保额卖还是按份数卖
		String sDutyFlag = "";
		sql = " select AmntFlag from lmduty " + " where dutycode = '"
				+ "?dutycode?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
		ExeSQL tExeSQL = new ExeSQL();
		sDutyFlag = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "责任定义信息查询失败!");
			return -1;
		}
		if (sDutyFlag == null || sDutyFlag.equals("")) {
			CError.buildErr(this, "责任保额标记为空!");
			return -1;
		}
		sqlbv=new SQLwithBindVariables();
		if (sDutyFlag.equals("1")) // 保额
		{
			sql = " select amnt from lcduty " + " where dutycode = '"
					+ "?dutycode?" + "' and polno = '"
					+ "?polno?" + "'";
			sqlbv.sql(sql);
			sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
			sqlbv.put("polno", pLCDutySchema.getPolNo());
			dAllDuty = pLCDutySchema.getAmnt();
		} else if (sDutyFlag.equals("2")) // 份数
		{
			sql = " select mult from lcduty " + " where dutycode = '"
					+ "?dutycode?" + "' and polno = '"
					+ "?polno?" + "'";
			sqlbv.sql(sql);
			sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
			sqlbv.put("polno", pLCDutySchema.getPolNo());
			dAllDuty = pLCDutySchema.getMult();
		} else {
			CError.buildErr(this, "责任保额标记定义错误! 错误值:" + sDutyFlag);
			return -1;
		}
		

		tExeSQL = new ExeSQL();
		String sDuty = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this,
					"查询责任项保额或份数失败!" + " 保单号：" + pLCDutySchema.getPolNo()
							+ " 责任编码：" + pLCDutySchema.getDutyCode());
			return -1;
		}
		if (sDuty == null || sDuty.trim().equals("")) {
			CError.buildErr(this,
					"责任项保额或份数为空!" + " 保单号：" + pLCDutySchema.getPolNo()
							+ " 责任编码：" + pLCDutySchema.getDutyCode());
			return -1;
		}
		try {
			dDuty = Double.parseDouble(sDuty);
		} catch (Exception e) {
			CError.buildErr(
					this,
					"责任项保额查询结果错误!" + " 错误结果：" + sDuty + " 保单号："
							+ pLCDutySchema.getPolNo() + " 责任编码："
							+ pLCDutySchema.getDutyCode());
			return -1;
		}

		if (dAllDuty == 0) // 团体账户险没有保额和份数的概念
		{
			dDutyRate = 1;
		} else {
			dDutyRate = dDuty / dAllDuty;
		}

		return dDutyRate;
	}

	/**
	 * 查询领取间隔
	 * 
	 * @param pLCDutySchema
	 * @return String
	 */
	public String getGetIntv(LCDutySchema pLCDutySchema) {
		String sql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		sql = " select getintv from lcget where polno = '"
				+ "?polno?"
				+ "' and dutycode = '"
				+ "?dutycode?"
				+ "' and getdutycode in (select getdutycode from lmdutyget where type = '0')  and rownum = 1 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			sql = " select getintv from lcget where polno = '"
					+ "?polno?"
					+ "' and dutycode = '"
					+ "?dutycode?"
					+ "' and getdutycode in (select getdutycode from lmdutyget where type = '0')  limit 0,1";	
		}
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("polno", pLCDutySchema.getPolNo());
		sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
		ExeSQL tExeSQL = new ExeSQL();
		String sGetIntv = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询领取间隔失败!");
			return null;
		}
		if (sGetIntv == null || sGetIntv.trim().equals("")) {
			logger.debug("领取间隔为空");
			sGetIntv = "";
		}
		return sGetIntv;
	}

	/**
	 * 查询VPU
	 * 
	 * @param sDutyCode
	 * @return String
	 */
	private double getVPU(String sDutyCode) {
		if (sDutyCode.length() > 6) {
			sDutyCode = sDutyCode.substring(0, 6);
		}
		double dVPU = 0.0;
		String sql = " select VPU from lmduty " + " where dutycode = '"
				+ "?sDutyCode?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sDutyCode", sDutyCode);
		ExeSQL tExeSQL = new ExeSQL();
		String sVPU = tExeSQL.getOneValue(sqlbv);
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

	/**
	 * 计算有效保额 有效保额 = 基本保额 + 累计红利金额
	 * 
	 * @param sPolNo
	 * @param sCalDate
	 * @return String
	 */
	public double getAvailableAmnt(String sPolNo, String sCalDate) {
		double dAvailableAmnt = 0.0;
		// 查询保单基本保额
		double dBaseAmnt = 0.0;
		String sql = " select amnt from lcpol " + " where polno = '" + "?sPolNo?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sPolNo", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sBaseAmnt = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "基本保额查询失败!");
			return -1;
		}
		try {
			dBaseAmnt = Double.parseDouble(sBaseAmnt);
		} catch (Exception e) {
			CError.buildErr(this, "基本保额查询结果错误!" + "错误结果：" + sBaseAmnt);
			return -1;
		}

		if (!mFinaBonusFlag) // 计算终了红利时有效保额是否包含累计红利保额标志
		{
			return dBaseAmnt; // 直接返回基本保额
		}

		// 计算累计红利保额
		double dSumAmntBonus = getSumAmntBonus(sPolNo, sCalDate);
		if (dSumAmntBonus == -1) {
			return -1;
		}

		dAvailableAmnt = dBaseAmnt + dSumAmntBonus;
		return dAvailableAmnt;

	}

	/**
	 * 计算累计红利保额
	 * 
	 * @param sPolNo
	 * @param sCalDate
	 *            退保申请日期
	 * @return String
	 */
	public double getSumAmntBonus(String sPolNo, String sCalDate) {
		if (mInBonusAmnt != -1) {
			return mInBonusAmnt; // 计算红利保额现价时，可以采用分红记录传入方式
		}

		initVar(sPolNo, sCalDate);

		String sql;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ExeSQL tExeSQL = new ExeSQL();
		double dSumBonusAmnt = 0.0;

		// 查询出保单详细信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(sPolNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种信息查询失败!");
			return -1;
		}
		if (tLCPolSet == null || tLCPolSet.size() != 1) {
			CError.buildErr(this, "没有查到险种信息!");
			return -1;
		}
		mLCPolSchema.setSchema(tLCPolSet.get(1));

		// 判断是否为分红险
		String sBonusFlag = getBonusFlag(mLCPolSchema.getRiskCode());
		if (sBonusFlag == null) {
			return -1;
		} else if (sBonusFlag.equals("Y")) {
			// 查询累计红利保额
			if (mCalDateType != null && mCalDateType.equals("CF")) {
				// 催付时点系统该笔分红不算(在红利公布日之前)
				sql = " select sum(BonusAmnt) from LOEngBonusPol "
						+ " where (getflag is null or getflag <> '1') and polno = '"
						+ "?sPolNo?" + // 已经领取的红利不再累计
						"' and sdispatchdate < '?sCalDate?'"; // 操作时点
			} else {
				// 退保时点系统该笔分红算
				sql = " select sum(BonusAmnt) from LOEngBonusPol "
						+ " where (getflag is null or getflag <> '1') and polno = '"
						+ "?sPolNo?" + // 已经领取的红利不再累计
						"' and sdispatchdate <= '?sCalDate?'"; // 操作时点
			}
			tExeSQL = new ExeSQL();
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("sPolNo", sPolNo);
			sqlbv.put("sCalDate", sCalDate);
			String sSumBonusAmnt = tExeSQL.getOneValue(sqlbv);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "分红查询失败!");
				return -1;
			}
			if (sSumBonusAmnt == null || sSumBonusAmnt.equals("")) {
				dSumBonusAmnt = 0.0;
			} else {
				try {
					dSumBonusAmnt = Double.parseDouble(sSumBonusAmnt);
				} catch (Exception e) {
					CError.buildErr(this, "保单累计红利保额查询结果错误!" + "错误结果："
							+ sSumBonusAmnt);
					return -1;
				}
			}
			sqlbv=new SQLwithBindVariables();
			if (!mHasDisPatched) // 第一次调用强制分红
			{
				// 判断退保点与保全申请日期之间有无分红，如果没有，则强制分红
				if (mCalDateType != null && mCalDateType.equals("CF")) {
					sql = " select count(*) from LOEngBonusPol "
							+ " where polno = '" + "?sPolNo?"
							+ "' and sdispatchdate > '?mZTPoint?' and sdispatchdate <= '" + "?sCalDate?" + "'"; // 操作时点

					sqlbv.sql(sql);
					sqlbv.put("sPolNo", sPolNo);
					sqlbv.put("mZTPoint", mZTPoint);
					sqlbv.put("sCalDate", sCalDate);
				} else {
					sql = " select count(*) from LOEngBonusPol "
							+ " where polno = '" + "?sPolNo?"
							+ "' and sdispatchdate >= '?mZTPoint?' and sdispatchdate <= '" + "?sCalDate?" + "'"; // 操作时点

					sqlbv.sql(sql);
					sqlbv.put("sPolNo", sPolNo);
					sqlbv.put("mZTPoint", mZTPoint);
					sqlbv.put("sCalDate", sCalDate);
				}


				tExeSQL = new ExeSQL();
				String sCount = tExeSQL.getOneValue(sqlbv);
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "分红查询失败!");
					return -1;
				}
				if (sCount != null && sCount.trim().equals("1")) {
					// 有分红，不需要调强制分红
					dSumBonusAmnt += 0;
					mDisPatchBonus = 0;
					mHasDisPatched = true;
					return dSumBonusAmnt;
				}
				try {
					// 调用分红处理 zhangtao 2005-07-11
					DispatchBonusBL tDispatchBonusBL = new DispatchBonusBL();
					tDispatchBonusBL.setPolNo(sPolNo);
					tDispatchBonusBL.setForceDVFlag("1"); // 强制分红
					tDispatchBonusBL.setEdorValiDate(mZTPoint);
					tDispatchBonusBL.setIsSubmit(false); // 不提交标志
					tDispatchBonusBL.setCFFlag(mCalDateType); // 催付标志
					tDispatchBonusBL.setNextYearFlag(mNextYearFlag); // 计算下一年度红利标志
					if (!tDispatchBonusBL.dispatchBonus()) {
						mErrors.copyAllErrors(tDispatchBonusBL.mErrors);
						return -1;
					} else {
						// ======调用分红处理后重新查表统计=====zhangtao===2005-08-31=======BGN=============
						// sql = " select sum(BonusAmnt) from LOEngBonusPol " +
						// " where polno = '" + sPolNo +
						// "' and sdispatchdate <= '" + sCalDate + "'"; //操作时点
						// tExeSQL = new ExeSQL();
						// sSumBonusAmnt = tExeSQL.getOneValue(sql);
						// if (tExeSQL.mErrors.needDealError())
						// {
						// CError.buildErr(this, "分红查询失败!");
						// return -1;
						// }
						// if (sSumBonusAmnt == null ||
						// sSumBonusAmnt.equals(""))
						// {
						// dSumBonusAmnt = 0.0;
						// }
						// else
						// {
						// try
						// {
						// dSumBonusAmnt = Double.parseDouble(sSumBonusAmnt);
						// }
						// catch (Exception e)
						// {
						// CError.buildErr(this, "保单累计红利保额查询结果错误!" +
						// "错误结果：" + sSumBonusAmnt);
						// return -1;
						// }
						// }
						// ======调用分红处理后重新查表统计=====zhangtao===2005-08-31=======BGN=============

						LOEngBonusPolSet tLOEngBonusPolSet = tDispatchBonusBL
								.getLOEngBonusPolSet();
						// 不能直接获取map提交，应该存入P表 zhangtao 2005-07-29
						mResult.add(tLOEngBonusPolSet);

						double dBonusAmnt = this.sumBonus(tLOEngBonusPolSet);

						mDisPatchBonus = dBonusAmnt;
						mHasDisPatched = true;

						dSumBonusAmnt += dBonusAmnt;
						return dSumBonusAmnt;
					}
				} catch (Exception e) {
					CError.buildErr(this, "强制分红处理失败!");
					return -1;
				}
			} else // 已经调用过强制分红
			{
				dSumBonusAmnt += mDisPatchBonus;
			}
		}

		return dSumBonusAmnt;
	}

	/**
	 * 校验是否有应领未领保险金
	 * 
	 * @return boolean
	 */
	private boolean checkGetDrew(String sPolNo, String sDate) {
		// String sql = " select (" +
		// " (select count(1) from ljsgetdraw where polno = '" + sPolNo + "' " +
		// " and LastGettoDate <= '" + sDate + "' ) " +
		// " + " +
		// " (select count(1) from ljagetdraw where polno = '" + sPolNo + "' " +
		// " and LastGettoDate <= '" + sDate + "' and (EnterAccDate is null and
		// ConfDate is null)) " +
		// " ) from dual ";
		// comment by wk 2009-01-19
		// String sql = " select count(1) from ljsgetdraw where polno = '" +
		// sPolNo + "' " +
		// " and LastGettoDate <= '" + sDate + "' " ;
		// ExeSQL tExeSQL = new ExeSQL();
		// String sGetDrew = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "查询应领未领保险金失败!");
		// return false;
		// }
		// int iGetDrew = 0;
		// try
		// {
		// iGetDrew = Integer.parseInt(sGetDrew);
		// }
		// catch (Exception e)
		// {
		// CError.buildErr(this, "查询应领未领保险金失败!");
		// return false;
		// }
		//
		// if (iGetDrew > 0)
		// {
		// CError.buildErr(this, "有应领未领保险金，不能退保。请应先进行领取!");
		// return false;
		// }
		String lcget_sql =
		// -- 1- 缴费期满，期领
		" select 1 from lcget a,lcpol b where a.polno = b.polno and b.paytodate=b.payenddate "
				+ " and a.getintv>0 and a.gettodate<=a.getenddate and a.livegettype = '0' and a.gettodate<='"
				+ "?gettodate1?"
				+ "' and a.polno='"
				+ "?polno1?"
				+ "' "
				+ " union "
				// --2- 缴费期满，趸领
				+ " select 1 from lcget a,lcpol b where a.polno=b.polno and b.paytodate=b.payenddate "
				+ " and a.getintv=0 and a.summoney<=0 and a.livegettype = '0' and a.gettodate<='"
				+ "?gettodate2?"
				+ "' and a.polno='"
				+ "?polno2?"
				+ "' "
				+ " union "
				// --3-缴费期未满，期领
				+ " select 1 from lcget a,lcpol b where a.polno=b.polno and b.paytodate<b.payenddate "
				+ " and a.getintv>0 and a.gettodate<=b.paytodate and a.livegettype = '0' and a.gettodate<=a.getenddate and a.gettodate<='"
				+ "?gettodate3?"
				+ "' and a.polno='"
				+ "?polno3?"
				+ "' "
				+ " union "
				// --4-缴费期未满，趸领
				+ " select 1 from lcget a,lcpol b where a.polno=b.polno and b.paytodate<b.payenddate "
				+ " and a.getintv=0 and a.gettodate<=b.paytodate and a.livegettype = '0' and a.summoney<=0 and a.gettodate<='"
				+ "?gettodate4?" + "' and a.polno='" + "?polno4?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(lcget_sql);
		sqlbv.put("gettodate1", this.mZTPoint);
		sqlbv.put("polno1", sPolNo);
		sqlbv.put("gettodate2", this.mZTPoint);
		sqlbv.put("polno2", sPolNo);
		sqlbv.put("gettodate3", this.mZTPoint);
		sqlbv.put("polno3", sPolNo);
		sqlbv.put("gettodate4", this.mZTPoint);
		sqlbv.put("polno4", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String getdraw_flag = tExeSQL.getOneValue(sqlbv);
		if (getdraw_flag != null && getdraw_flag.equals("1")) {
			CError.buildErr(this,
					"有应领未领保险金尚未入生存金帐户，不能退保。请应先撤销本保全项后进行催付处理最后进行保全退保!");
			return false;
		}
		return true;
	}

	/**
	 * 计算工本费（支持按机构、项目、险种计算）
	 * 
	 * @param sEdorType
	 * @param sManageCom
	 * @param sRiskCode
	 * @return double
	 */
	public double getWorkNoteMoney(String sEdorType, String sManageCom,
			String sRiskCode) {
		double dWorkNoteMoney = 0.0;

		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setCalType("NoteMoney");
		// tLMEdorCalDB.setRiskCode("000000");
		// modified by sunsx 根据险种定义查询工本费 2008-08-19
		this.mBqCalBase.setRiskCode(sRiskCode);
		this.mBqCalBase.setManageCom(sManageCom);
		this.mBqCalBase.setEdorType(sEdorType);
		tLMEdorCalDB.setRiskCode(sRiskCode);
		tLMEdorCalDB.setEdorType(sEdorType);
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "工本费计算代码查询失败!");
			return -1;
		}

		// 如果没有记录,查询默认的该保全项目的工本费
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			tLMEdorCalDB.setRiskCode("000000");
			tLMEdorCalSet = tLMEdorCalDB.query();
			if (tLMEdorCalDB.mErrors.needDealError()) {
				CError.buildErr(this, "工本费计算代码查询失败!");
				return -1;
			}
			if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
				// 如果仍没有纪录,则该项目不需要计算工本费
				return 0;
			}
		}

		Calculator tCalculator = new Calculator();
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		tCalculator.addBasicFactor("ManageCom", sManageCom);
		tCalculator.addBasicFactor("RiskCode", sRiskCode);
		tCalculator.addBasicFactor("InsuAccNo", sRiskCode);
		String sWorkNoteMoney = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "工本费计算失败!");
			return -1;
		}
		try {
			dWorkNoteMoney = Double.parseDouble(sWorkNoteMoney);
		} catch (Exception e) {
			CError.buildErr(this, "工本费计算结果错误!" + "错误结果：" + sWorkNoteMoney);
			return -1;
		}
		return dWorkNoteMoney;
	}

	/**
	 * 帐户险相关费用计算（保留原来接口-万能险使用）
	 * 
	 * @param sRiskCode
	 *            险种代码/账户编号
	 * @param dSMoney
	 *            原始金额
	 * @param sInterval
	 * @param sCalTYpe
	 *            CalFee-根据保单价值计算扣除手续费之后的现金价值 UnCalFee-根据现金价值计算扣除手续费之前的保单价值
	 *            CalDiedFee-根据保单价值计算死亡保险金
	 * @return double
	 */
	public double getActuMoney(String sRiskCode, double dMoney, int sInterval,
			String sCalTYpe) {
		return getActuMoney(dMoney, sRiskCode, "CT", "", "", sInterval, 0,
				sCalTYpe);
	}

	/**
	 * 帐户险相关费用计算（投连产品增加了计算要素）
	 * 
	 * @param sRiskCode
	 *            险种代码/账户编号
	 * @param dSMoney
	 *            原始金额
	 * @param sInterval
	 * @param iIntervalM
	 * @param sCalTYpe
	 *            CalFee-根据保单价值计算扣除手续费之后的现金价值 UnCalFee-根据现金价值计算扣除手续费之前的保单价值
	 *            CalDiedFee-根据保单价值计算死亡保险金
	 * @return double
	 */
	public double getActuMoney(double dMoney, String sRiskCode,
			String sEdorType, String sPolNo, String sEdorValidate,
			int iInterval, int iIntervalM, String sCalTYpe) {
		double dAMoney = 0.0;

		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setCalType(sCalTYpe);
		tLMEdorCalDB.setRiskCode(sRiskCode);
		tLMEdorCalDB.setEdorType(sEdorType);
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "账户险退保扣除手续费计算代码查询失败!");
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			return dMoney; // 没有退保扣除手续费算法,返回原值
		}

		Calculator tCalculator = new Calculator();
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());
		tCalculator
				.addBasicFactor("EdorValidate", mBqCalBase.getEdorValiDate());
		tCalculator.addBasicFactor("GetMoney",
				String.valueOf(mBqCalBase.getZTMoneyByAcc()));
		tCalculator.addBasicFactor("Money",
				String.valueOf(mBqCalBase.getZTMoneyByAcc()));
		tCalculator.addBasicFactor("Interval",
				String.valueOf(mBqCalBase.getInterval()));// 保单年度
		tCalculator.addBasicFactor("IntervalM",
				String.valueOf(mBqCalBase.getIntervalM()));// 保单经过月份
		String sAMoney = tCalculator.calculate(); // 返回本次退保的退保费用 changde by
		// pst on 2007-12-2
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "账户费用计算失败!");
			return -1;
		}
		try {
			dAMoney = dMoney - Double.parseDouble(sAMoney);
		} catch (Exception e) {
			CError.buildErr(this, "账户费用计算结果错误!" + "错误结果：" + sAMoney);
			return -1;
		}
		return dAMoney;
	}

	/**
	 * 计算终了红利-公用接口
	 * 
	 * @param sPolNo
	 * @param sCalDate
	 * @return double
	 */
	public double getFinalBonus(String sPolNo, String sCalDate) {
		if (!initVar(sPolNo, sCalDate)) {
			return -1;
		}

		if(getBonusFlag(mBqCalBase.getRiskCode()).equals("Y"))
			return getFinalBonus();
		else
			return 0.0;
	}

	/**
	 * 计算终了红利
	 * 
	 * @return double
	 */
	private double getFinalBonus() {
		double dFinalBonus = 0.0;

		if (mInterval < 1) {
			return 0.0;
		}

		// 计算有效保额
		dFinalBonus = getAvailableAmnt(mPolNo, mEdorAppDate);
		if (dFinalBonus == -1) {
			return -1;
		}

		// 获取终了红利率
		double dTBRate = getTBRate(mLCPolSchema);

		if (dTBRate == -1) {
			return -1;
		}

		// 计算理赔冲减保额
		double dReduceAmnt = getReduceAmnt(mLCPolSchema.getContNo(), mPolNo,
				mZTPoint);
		if (dReduceAmnt == -1) {
			return -1;
		}

		// 获取终了红利计算代码
		String sql = " select * from lmedorcal where riskcode in ('"
				+ "?riskcode?" + "', '000000') "
				+ " and caltype = 'TBonus' order by riskcode desc ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("riskcode", mLCPolSchema.getRiskCode());
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv);
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "终了红利计算代码查询失败!");
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			CError.buildErr(this, "终了红利计算代码查询失败!");
			return -1;
		}
		String sFinaCalCode = tLMEdorCalSet.get(1).getCalCode();

		// 计算终了红利
		Calculator tCalculator = new Calculator();
		PrepareBOMBQEdorBL tPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setAmnt(dFinalBonus);
		tBqCalBase.setReduceAmnt(dReduceAmnt);
		tBqCalBase.setTBRate(Double.valueOf(dTBRate));
		tPrepareBOMBQEdorBL.setBqCalBase(tBqCalBase);
		ArrayList tBomList = new ArrayList();
		this.mBomList = tPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
		tCalculator.setBOMList(tBomList);// 添加BOMList
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(sFinaCalCode);
		tCalculator.addBasicFactor("Amnt", String.valueOf(dFinalBonus)); // 有效保额
		tCalculator.addBasicFactor("ReduceAmnt", String.valueOf(dReduceAmnt)); // 冲减保额
		tCalculator.addBasicFactor("TBRate", String.valueOf(dTBRate)); // 终了红利率
		String sCalResultValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "终了红利计算失败!");
			return -1;
		}
		try {
			dFinalBonus = Double.parseDouble(sCalResultValue);
		} catch (Exception e) {
			CError.buildErr(this, "终了红利计算结果错误!" + "错误结果：" + sCalResultValue);
			return -1;
		}

		logger.debug("== 终了红利 ==" + dFinalBonus);
		return dFinalBonus;
	}

	/**
	 * 查询终了红利率
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @param pInterval
	 *            保单年度
	 * @return double
	 */
	private double getTBRate(LCPolSchema pLCPolSchema) {
		double dTBRate = 0.0;
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(pLCPolSchema.getRiskCode());
		tLMEdorCalDB.setCalType("TBRate");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "终了红利率计算代码查询失败!");
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() < 1) {
			CError.buildErr(this, "终了红利率计算代码查询失败!");
			return -1;
		}

		Calculator tCalculator = new Calculator();
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setInterval(mInterval);
		tBqCalBase.setPayEndYear(pLCPolSchema.getPayEndYear());
		tBqCalBase.setEdorValiDate(mZTPoint);
		tBqCalBase.setForceDVFlag("1");
		tBqCalBase.setPayIntv(pLCPolSchema.getPayIntv());
		tBqCalBase.setPolNo(pLCPolSchema.getPolNo());
		PrepareBOMBQEdorBL tPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
		tPrepareBOMBQEdorBL.setBqCalBase(tBqCalBase);
		ArrayList tBomList = new ArrayList();
		this.mBomList = tPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
		tCalculator.setBOMList(tBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		// 准备计算要素
		tCalculator.addBasicFactor("Interval", // 保单年度
				String.valueOf(mInterval));
		tCalculator.addBasicFactor("PayEndYear",// 缴费终止年期
				String.valueOf(pLCPolSchema.getPayEndYear()));
		tCalculator.addBasicFactor("EdorValiDate", // 退保时点
				mZTPoint);
		tCalculator.addBasicFactor("ForceDVFlag", // 强制分红标志
				"1");
		tCalculator.addBasicFactor("PayIntv", // 缴费间隔
				String.valueOf(pLCPolSchema.getPayIntv()));
		tCalculator.addBasicFactor("PolNo", pLCPolSchema.getPolNo());

		String sTBRate = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "终了红利率查询失败!");
			return -1;
		}
		if (sTBRate == null || sTBRate.trim().equals("")) {
			CError.buildErr(this, "没有查到终了红利率!");
			return -1;
		}
		try {
			dTBRate = Double.parseDouble(sTBRate);
		} catch (Exception e) {
			CError.buildErr(this, "终了红利率查询结果错误!" + "错误结果：" + sTBRate);
			return -1;
		}
		return dTBRate;
	}

	/**
	 * 计算健康（职业）加费应退金额-公用接口
	 * 
	 * @return double
	 */
	public double getAddPrem(String sPolNo, String sCalDate) {
		if (!initVar(sPolNo, sCalDate)) {
			return -1;
		}

		return getAddPrem();
	}

	/**
	 * 计算健康（职业）加费应退金额
	 * 
	 * @return double
	 */
	private double getAddPrem() {

		double dSumAddPrem = 0.0;

		String sql = " select * from lcprem "
				+ " where payplancode like '000000%' " + " and polno = '"
				+ "?mPolNo?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mPolNo", mPolNo);
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet = tLCPremDB.executeQuery(sqlbv);
		if (tLCPremDB.mErrors.needDealError()) {
			CError.buildErr(this, "加费信息查询失败!");
			return -1;
		}
		if (tLCPremSet == null || tLCPremSet.size() < 1) {
			// 没有加费
			return 0.0;
		}
		double dAddPrem = 0.0;
		String sPayPlanType; // 交费计划类型
		String sPayPlanCode; // 交费计划编码
		for (int i = 1; i <= tLCPremSet.size(); i++) {
			sPayPlanType = tLCPremSet.get(i).getPayPlanType(); // 交费计划类型
			sPayPlanCode = tLCPremSet.get(i).getPayPlanCode(); // 交费计划编码
			// =====DEL======zhangtao=========2005-10-16=================BGN=================
			// //已交加费总额
			// //dAddPrem = tLCPremSet.get(i).getPrem() *
			// tLCPremSet.get(i).getPayTimes();
			// //查询保单交费记录
			// // sql = " select sum(SumActuPayMoney) from ljapayperson " +
			// // " where polno = '" + mPolNo +
			// // "' and trim(payplancode) = '" + sPayPlanCode + "' ";
			// sql = " select ( " +
			// " (select nvl(sum(SumActuPayMoney),0) from ljapayperson where
			// trim(payplancode) = '" + sPayPlanCode + "' and polno = '" +
			// mPolNo + "')" +
			// " + " +
			// " (select nvl(sum(getmoney), 0) from ljagetendorse j where
			// trim(payplancode) = '" + sPayPlanCode + "' and feefinatype in
			// ('TF', 'BF') and polno = '" + mPolNo + "' )" +
			// " ) from dual ";
			// ExeSQL tExeSQL = new ExeSQL();
			// String sPrem = tExeSQL.getOneValue(sql);
			// if (tExeSQL.mErrors.needDealError())
			// {
			// CError.buildErr(this, "保单基本保费交费记录查询失败!");
			// return -1;
			// }
			// if (sPrem == null || sPrem.equals(""))
			// {
			// sPrem = "0";
			// }
			// try
			// {
			// dAddPrem = Double.parseDouble(sPrem);
			// }
			// catch (Exception e)
			// {
			// CError.buildErr(this, "保单基本保费交费记录查询结果错误!" +
			// "错误结果：" + sPrem);
			// return -1;
			// }
			// =====DEL======zhangtao=========2005-10-16=================END=================

			// =====ADD======zhangtao=========2005-10-16=================BGN=================
			BqPolBalBL tBqPolBalBL = new BqPolBalBL();
			if (!tBqPolBalBL.getSumPremPerPrem(tLCPremSet.get(i).getPolNo(),
					tLCPremSet.get(i).getPayPlanCode(), tLCPremSet.get(i)
							.getPaytoDate())) {
				CError.buildErr(this, "累计加费计算失败!");
				return -1;
			}

			dAddPrem = tBqPolBalBL.getCalResult();
			// =====ADD======zhangtao=========2005-10-16=================END=================
			// 退保比例
			/* double dAddPremRate = getAddPremRate(tLCPremSet.get(i)); */
			double dAddPremRate = 0; // MS加费不退
			/*
			 * if (dAddPremRate == -1) { return -1; }
			 */

			dAddPrem = dAddPrem * dAddPremRate;
			if (sPayPlanType.equals("01") || sPayPlanType.equals("03")) {
				mAddPremH += dAddPrem; // 健康加费应退金额
			}
			if (sPayPlanType.equals("02") || sPayPlanType.equals("04")) {
				mAddPremO += dAddPrem; // 职业加费应退金额
			}
		}
		mAddPremH = format(mAddPremH);
		mAddPremO = format(mAddPremO);
		dSumAddPrem = format(mAddPremH + mAddPremO);
		logger.debug("== 健康（职业）加费应退金额 ==" + dSumAddPrem);
		return dSumAddPrem;
	}

	/**
	 * 计算健康（职业）加费退保比例
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @return double
	 */
	private double getAddPremRate(LCPremSchema pLCPremSchema) {
		double dAddPremRate = 0.0; // 退保比例
		double dInsuYear = 0.0; // 保险期限
		String sPayPlanType = pLCPremSchema.getPayPlanType(); // 交费计划类型
		String sAddFeeDirect = pLCPremSchema.getAddFeeDirect();// 加费指向标记
		double dSecInsuAddPoint = pLCPremSchema.getSecInsuAddPoint(); // 第二被保人加费评点
		double dSuppRiskScore = pLCPremSchema.getSuppRiskScore(); // 额外风险评分

		if (mLCPolSchema.getRiskCode().equals("00607000")
				&& mLPEdorItemSchema.getEdorReasonCode().equals("13")) // 607死亡退保加费全额退还
		{
			return 1;
		}

		ExeSQL tExeSQL = new ExeSQL();
		String sql = " select InsuYear, InsuYearFlag from lcduty "
				+ " where dutycode = '" + "?dutycode?"
				+ "' and polno = '" + "?polno?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("dutycode", pLCPremSchema.getDutyCode());
		sqlbv.put("polno", pLCPremSchema.getPolNo());
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保险期限查询失败!");
			return -1;
		}
		String sInsuYear = tSSRS.GetText(1, 1);
		String sInsuYearFlag = tSSRS.GetText(1, 2);
		if ("1000".equals(sInsuYear)) // 终身保险
		{
			sql = " select cvalidate from lccont " + " where contno = '"
					+ "?contno?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("contno", mLCPolSchema.getContNo());
			String sCValidate = tExeSQL.getOneValue(sqlbv); // 保单生效日期
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "保单生效日查询失败!");
				return -1;
			}
			double dInsuredAppAge = 0.0; // 投保年龄
			if (sAddFeeDirect == null || sAddFeeDirect.equals("")) {
				// 如果加费指向标记为空，则默认为被保人
				sAddFeeDirect = "02";
			}
			if (sAddFeeDirect.equals("01")) // 投保人
			{
				// 少儿终身保障险，投保人健康加费退还时，投保年龄使用被保险人的
				String is108 = isRisk(mLCPolSchema.getRiskCode(), "108");
				if (is108 == null) {
					return -1;
				} else if (is108.equals("Y")) {
					dInsuredAppAge = mLCPolSchema.getInsuredAppAge();
					dInsuYear = 106.0 - dInsuredAppAge; // 终身保险期限
				} else {
					// 取投保人年龄
					sql = " select AppntBirthday from lcappnt "
							+ " where contno = '" + "?contno?"
							+ "'";
					sqlbv=new SQLwithBindVariables();
					sqlbv.sql(sql);
					sqlbv.put("contno", mLCPolSchema.getContNo());
					String sAppntBirthday = tExeSQL.getOneValue(sqlbv);
					if (tExeSQL.mErrors.needDealError()) {
						CError.buildErr(this, "投保人生日查询失败!");
						return -1;
					}
					if (sAppntBirthday == null || sAppntBirthday.equals("")) {
						CError.buildErr(this, "投保人生日为空!");
						return -1;
					}
					dInsuredAppAge = PubFun.calInterval(sAppntBirthday,
							sCValidate, "Y");
					dInsuYear = 106.0 - dInsuredAppAge; // 终身保险期限
				}
			} else if (sAddFeeDirect.equals("02")) // 第一被保人
			{
				dInsuredAppAge = mLCPolSchema.getInsuredAppAge();
				dInsuYear = 106.0 - dInsuredAppAge; // 终身保险期限
			} else if (sAddFeeDirect.equals("04")) // 第二被保人
			{
				// 取连身被保人年龄
				sql = " select Birthday from LCInsuredRelated "
						+ " where polno = '" + "?polno?"
						+ "' and MainCustomerNo = '"
						+ "?MainCustomerNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("polno", mLCPolSchema.getPolNo());
				sqlbv.put("MainCustomerNo", mLCPolSchema.getInsuredNo());
				String sRelatedBirthday = tExeSQL.getOneValue(sqlbv);
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "连身被保人生日查询失败!");
					return -1;
				}
				if (sRelatedBirthday == null || sRelatedBirthday.equals("")) {
					CError.buildErr(this, "连身被保人生日为空!");
					return -1;
				}
				dInsuredAppAge = PubFun.calInterval(sRelatedBirthday,
						sCValidate, "Y");
				dInsuYear = 106.0 - dInsuredAppAge; // 终身保险期限
			} else if (sAddFeeDirect.equals("03")) // 多被保人
			{
				// 取最大年龄
				sql = " select Birthday from LCInsuredRelated "
						+ " where polno = '" + "?polno?"
						+ "' and MainCustomerNo = '"
						+ "?MainCustomerNo?" + "'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.put("polno", mLCPolSchema.getPolNo());
				sqlbv.put("MainCustomerNo", mLCPolSchema.getInsuredNo());
				String sRelatedBirthday = tExeSQL.getOneValue(sqlbv);
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "连身被保人生日查询失败!");
					return -1;
				}
				if (sRelatedBirthday == null || sRelatedBirthday.equals("")) {
					CError.buildErr(this, "连身被保人生日为空!");
					return -1;
				}
				double dRelatedAge = PubFun.calInterval(sRelatedBirthday,
						sCValidate, "Y");
				dInsuredAppAge = Math.max(dRelatedAge,
						mLCPolSchema.getInsuredAppAge());
				dInsuYear = 106.0 - dInsuredAppAge; // 终身保险期限
			}
		} else {

			try {
				dInsuYear = Double.parseDouble(sInsuYear);
			} catch (Exception e) {
				CError.buildErr(this, "保险期限查询结果错误!" + "错误结果：" + sInsuYear);
				return -1;
			}
			// 保险期限单位与保单年度单位应该一致，都转算成年 zhangtao 2005-07-18
			dInsuYear = exToYear(dInsuYear, sInsuYearFlag);
			if (dInsuYear == -1) {
				return -1;
			}

		}

		if (mLCPolSchema.getPayIntv() == 0) // 趸交
		{
			dAddPremRate = Math.max(1 - (mInterval) / (0.8 * dInsuYear), 0);// +
																			// 1
			// 改为约近
		} else // 期交
		{
			if (sPayPlanType.equals("02") || sPayPlanType.equals("04")) {
				return 0.0; // 不退还期交职业加费
			}
			dAddPremRate = Math.max(1 - (mInterval) / (0.5 * dInsuYear), 0);// +
																			// 1
			// 改为约近
		}

		return dAddPremRate;
	}

	/**
	 * 判断险种是否是*险
	 * 
	 * @return String
	 */
	private String isRisk(String sRiskCode, String sRisk) {
		ExeSQL tExeSQL = new ExeSQL();
		String sql = " select 1 from dual where '"
				+ sRiskCode
				+ "' in (select trim(riskcode) from lmrisk where trim(riskcode) like concat('%00',concat("
				+ "'?sRisk?'" + ",'%')) ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sRisk", sRisk);
		String sResult = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "险种定义查询失败!");
			return null;
		}
		if (sResult != null && sResult.equals("1")) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 将保险期限单位转算成年
	 * 
	 * @return double
	 */
	private double exToYear(double dInsuYear, String sInsuYearFlag) {
		double dYear = 0.0;
		if (sInsuYearFlag.equals("Y")) {
			if (dInsuYear == 1000) {
				double dInsuredAppAge = mLCPolSchema.getInsuredAppAge();
				dYear = 106.0 - dInsuredAppAge;
			} else {
				dYear = dInsuYear;
			}
		} else if (sInsuYearFlag.equals("M")) {
			dYear = dInsuYear / 12;
		} else if (sInsuYearFlag.equals("D")) {
			dYear = dInsuYear / 365;
		} else if (sInsuYearFlag.equals("A")) {
			double dInsuredAppAge = mLCPolSchema.getInsuredAppAge();
			if (dInsuYear == 1000) {
				dYear = 106.0 - dInsuredAppAge;
			} else {
				dYear = dInsuYear - dInsuredAppAge;
			}
		} else {
			CError.buildErr(this, "保险年期标志错误!");
			return -1;
		}
		return dYear;
	}

	/**
	 * 查询多交保费
	 * 
	 * @return double
	 */
	private double getPayMorePrem(String sPolNo, String sCalDate) {
		double dPayMorePrem = 0.0;
		// 查询多交保费
		String sql = " select sum(SumActuPayMoney) from ljapayperson where paytype in ('ZC','RE','AA','NS','NI','RB','PA') and polno = '"
				+ "?sPolNo?" + "' and lastpaytodate > '" + "?sCalDate?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sPolNo", sPolNo);
		sqlbv.put("sCalDate", sCalDate);
		ExeSQL tExeSQL = new ExeSQL();
		String sPayMorePrem = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "多交保费查询失败!");
			return -1;
		}
		if (sPayMorePrem == null || sPayMorePrem.trim().equals("")) {
			sPayMorePrem = "0";
		}
		try {
			// MS不存在多交保费
			sPayMorePrem = "0";
			dPayMorePrem = Double.parseDouble(sPayMorePrem);
		} catch (Exception e) {
			CError.buildErr(this, "多交保费查询结果错误!" + "错误结果：" + sPayMorePrem);
			return -1;
		}

		return dPayMorePrem;
	}

	/**
	 * 贷款未清偿本金及利息
	 * 
	 * @return double
	 */
	private double getLoanAddInterest(String pContNo, String pCountDate) {
		double dLoanAddInterest = 0.0;

		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calLoanCorpus(pContNo, pCountDate)) {
			mLoanCorpus = format(tBqPolBalBL.getCalResult());
		} else {
			CError.buildErr(this, "贷款未清偿本金及利息计算失败!");
			return -1;
		}

		tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calLoanInterest(pContNo, pCountDate)) {
			mLoanInterest = format(tBqPolBalBL.getCalResult());
		} else {
			CError.buildErr(this, "贷款未清偿本金及利息计算失败!");
			return -1;
		}

		dLoanAddInterest = format(mLoanCorpus + mLoanInterest);

		return dLoanAddInterest;
	}

	/**
	 * 自垫未清偿本金及利息
	 * 
	 * @return double
	 */
	private double getAutoPayPremAddInterest(String tPolNo, String pCountDate) {
		double dAutoPayPremAddInterest = 0.0;
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calAutoPayPrem(tPolNo, pCountDate)) {
			mAutoPayPrem = format(tBqPolBalBL.getCalResult());
		} else {
			CError.buildErr(this, "自垫未清偿本金及利息计算失败!");
			return -1;
		}

		tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calAutoPayInterest(tPolNo, pCountDate)) {
			mAutoPayPremInterest = format(tBqPolBalBL.getCalResult());
		} else {
			CError.buildErr(this, "自垫未清偿本金及利息计算失败!");
			return -1;
		}
		dAutoPayPremAddInterest = format(mAutoPayPrem + mAutoPayPremInterest);

		return dAutoPayPremAddInterest;
	}

	/**
	 * 计算累计生存领取金
	 * 
	 * @param pLCPolSchema
	 * @param pLCDutySchema
	 * @param sNetCvalidate
	 *            下一生效对应日
	 * @return double
	 */
	private double getAliveGet(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, String sNetCvalidate,
			boolean bNextYearFlag) {
		String sql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		// //先判断该险种或责任有没有养老金领取项
		// if (pLCDutySchema == null)
		// {
		// sql = " select count(*) from LMDutyGetRela " +
		// " where dutycode in (select dutycode from lcduty where polno = '" +
		// pLCPolSchema.getPolNo() +
		// "') and getdutycode in (select getdutycode from lmdutyget where
		// gettype2 = '1')";
		// }
		// else
		// {
		// sql = " select count(*) from LMDutyGetRela " +
		// " where dutycode = '" + pLCDutySchema.getDutyCode() +
		// "' and getdutycode in (select getdutycode from lmdutyget where
		// gettype2 = '1')";
		// }
		// ExeSQL tExeSQL = new ExeSQL();
		// String sCount = tExeSQL.getOneValue(sql);
		// if (tExeSQL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "查询养老金标志失败!");
		// return -1;
		// }
		// if (sCount == null || sCount.equals("0"))
		// {
		// return 0.0;
		// }

		// 查询领取项中养老金相关的领取项计算生存领取金
		if (pLCDutySchema == null) {
			// sql = " select * from lcget " +
			// " where polno = '" + pLCPolSchema.getPolNo() +
			// "' and getdutycode in " +
			// " (select getdutycode from lmdutyget where gettype2 = '1')";
			// sql = " select * from lcget " +
			// " where polno = '" + pLCPolSchema.getPolNo() +
			// "' and getdutycode in " +
			// " ((select getdutycode from lmdutygetalive where GETSTARTPERIOD
			// <='" + sNextAge + "'" +
			// " and GETENDPERIOD>='" + sNextAge + "' and GETINTV <> 0) union" +
			// " (select getdutycode from lmdutygetalive where GETSTARTPERIOD
			// ='" + sNextAge + "' and getintv=0))";
			sql = " select * from lcget "
					+ " where polno = '"
					+ "?polno?"
					+ "' and ((getstartdate <= '"
					+ "?sNetCvalidate1?"
					+ "' and getenddate >= '"
					+ "?sNetCvalidate2?"
					+ "' and getintv <> 0) "
					+ " or (getstartdate = '"
					+ "?sNetCvalidate3?"
					+ "' and getintv = 0)) "
					+ " and getdutycode in (select getdutycode from lmdutyget where type=0)";
			sqlbv.sql(sql);
			sqlbv.put("polno", pLCPolSchema.getPolNo());
			sqlbv.put("sNetCvalidate1", sNetCvalidate);
			sqlbv.put("sNetCvalidate2", sNetCvalidate);
			sqlbv.put("sNetCvalidate3", sNetCvalidate);
			
			
		} else {
			// sql = " select * from lcget " +
			// " where polno = '" + pLCPolSchema.getPolNo() +
			// "' and dutycode = '" + pLCDutySchema.getDutyCode() +
			// "' and getdutycode in " +
			// " (select getdutycode from lmdutyget where gettype2 = '1')";
			// sql = " select * from lcget " +
			// " where polno = '" + pLCPolSchema.getPolNo() +
			// "' and dutycode = '" + pLCDutySchema.getDutyCode() +
			// "' and getdutycode in " +
			// " ((select getdutycode from lmdutygetalive where GETSTARTPERIOD
			// <='" + sNextAge + "'" +
			// " and GETENDPERIOD>='" + sNextAge + "' and GETINTV <> 0) union" +
			// " (select getdutycode from lmdutygetalive where GETSTARTPERIOD
			// ='" + sNextAge + "' and getintv = 0))";
			sql = " select * from lcget "
					+ " where polno = '"
					+ "?polno?"
					+ "' and dutycode = '"
					+ "?dutycode?"
					+ "' and ((getstartdate <= '"
					+ "?sNetCvalidate1?"
					+ "' and getenddate >= '"
					+ "?sNetCvalidate2?"
					+ "' and getintv <> 0) "
					+ " or (getstartdate = '"
					+ "?sNetCvalidate3?"
					+ "' and getintv = 0)) "
					+ " and getdutycode in (select getdutycode from lmdutyget where type=0)";
			sqlbv.sql(sql);
			sqlbv.put("polno", pLCPolSchema.getPolNo());
			sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
			sqlbv.put("sNetCvalidate1", sNetCvalidate);
			sqlbv.put("sNetCvalidate2", sNetCvalidate);
			sqlbv.put("sNetCvalidate3", sNetCvalidate);
		}
		logger.debug(sql);
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv);
		if (tLCGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "生存领取信息查询失败!");
			return -1;
		}
		if (tLCGetSet == null || tLCGetSet.size() < 1) {
			logger.debug("没有生存领取信息!");
			return 0.0;
		}

		double dAliveGet = 0;
		// PayPlanBL tPayPlanBL;
		// for (int i = 1; i <= tLCGetSet.size(); i++)
		// {
		// try
		// {
		// tPayPlanBL = new PayPlanBL(pLCPolSchema, sNetCvalidate);
		// // tPayPlanBL.setNextYearFlag(bNextYearFlag); //计算下一年度分红标志
		// //modify by yinhl 2005-11-1 解决618,619月领退保公式中
		// //计算第k+1保单年度内退保前累计的基本保额对应生存领取金额问题
		// //dAliveGet += (tPayPlanBL.calGetMoney(tLCGetSet.get(1)) * 12 /
		// tLCGetSet.get(i).getGetIntv());
		// if (tLCGetSet.get(i).getGetIntv() >= 12 ||
		// tLCGetSet.get(i).getGetIntv() == 0)
		// {
		// // dAliveGet +=tPayPlanBL.calGetMoney(tLCGetSet.get(1));
		// }
		// if (tLCGetSet.get(i).getGetIntv() < 12 &&
		// tLCGetSet.get(i).getGetIntv() != 0)
		// {
		// // dAliveGet
		// +=tPayPlanBL.calGetMoney(tLCGetSet.get(1))*(12-PubFun.calInterval(mZTPoint,sNetCvalidate,"M")/tLCGetSet.get(i).getGetIntv());
		// }
		// if (tPayPlanBL.mErrors.needDealError())
		// {
		// CError.buildErr(this, "生存领取金计算失败!");
		// return -1;
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// CError.buildErr(this, "生存领取金计算失败!");
		// return -1;
		// }
		// }

		return dAliveGet;
	}

	/**
	 * 判断是否已经进入领取期
	 * 
	 * @param pLCPolSchema
	 * @param pLCDutySchema
	 * @param sCalDate
	 * @return String
	 */
	private String startGet(LCPolSchema pLCPolSchema,
			LCDutySchema pLCDutySchema, String sCalDate) {
		String sql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (pLCDutySchema == null) {
			sql = " select min(getstartdate) from lcget "
					+ " where polno = '"
					+ "?polno?"
					+ "' and getdutycode in (select getdutycode from lmdutyget where type=0)";
			sqlbv.sql(sql);
			sqlbv.put("polno", pLCPolSchema.getPolNo());
		} else {
			sql = " select min(getstartdate) from lcget "
					+ " where polno = '"
					+ "?polno?"
					+ "' and dutycode = '"
					+ "?dutycode?"
					+ "' and getdutycode in (select getdutycode from lmdutyget where type=0)";
			sqlbv.sql(sql);
			sqlbv.put("polno", pLCPolSchema.getPolNo());
			sqlbv.put("dutycode", pLCDutySchema.getDutyCode());
		}
		ExeSQL tExeSQL = new ExeSQL();
		String sMinGetStartDate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "领取日期日期查询失败!");
			return null;
		}
		if (sMinGetStartDate == null || sMinGetStartDate.equals("")) {
			return "N";
		}
		if (sMinGetStartDate.length() > 10) {
			sMinGetStartDate = PubFun.calDate(
					sMinGetStartDate.substring(0, 10), -1, "Y", null);
		}
		int intv = PubFun.calInterval(sMinGetStartDate, sCalDate, "D");
		if (intv > 0) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 第k+1保单年度内退保前累计的生存领取金额
	 * 
	 * @param sPolNo
	 * @param sDutyCode
	 * @return double
	 */
	private double getSumYearGet(String sPolNo, String sDutyCode, String sDate) {
		double dSumYearGet = 0.0;
		String sql = " select GetMoney from ljagetdraw where polno = '"
				+ "?sPolNo?" + "' and dutycode = '" + "?sDutyCode?"
				+ "' and lastgettodate <= '" + "?sDate1?" + "' and curgettodate > '"
				+ "?sDate2?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sPolNo", sPolNo);
		sqlbv.put("sDutyCode", sDutyCode);
		sqlbv.put("sDate1", sDate);
		sqlbv.put("sDate2", sDate);
		ExeSQL tExeSQL = new ExeSQL();
		String sSumYearGet = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "年度累计生存领取查询失败!");
			return -1;
		}
		if (sSumYearGet == null || sSumYearGet.equals("")) {
			return 0;
		}
		try {
			dSumYearGet = Double.parseDouble(sSumYearGet);
		} catch (Exception e) {
			CError.buildErr(this, "年度累计生存领查询结果错误!" + "错误结果：" + sSumYearGet);
			return -1;
		}

		return dSumYearGet;
	}

	/**
	 * 设置全局变量 保单号；退保点；保单详细信息；保单生效日期；保单年度
	 * 
	 * @param sPolNo
	 * @param sCalDate
	 * @return double
	 */
	private boolean initVar(String sPolNo, String sCalDate) {
		mPolNo = sPolNo;

		// 查询出保单详细信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(sPolNo);
		// tLCPolDB.setAppFlag("1");
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种信息查询失败!");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() != 1) {
			CError.buildErr(this, "没有查到险种信息!");
			return false;
		}
		mLCPolSchema.setSchema(tLCPolSet.get(1));

		mEdorAppDate = sCalDate;
		// 根据保单结算时点确定退保时点
		mZTPoint = getZTPoint(mLCPolSchema, mEdorAppDate);
		this.mBqCalBase.setZTPoint(mZTPoint);
		this.mBqCalBase.setRiskCode(mLCPolSchema.getRiskCode());
		this.mBqCalBase.setPolNo(mLCPolSchema.getPolNo());
		this.mBqCalBase.setContNo(mLCPolSchema.getContNo());
		this.mBqCalBase.setCValiDate(mLCPolSchema.getCValiDate().substring(0,
				10));
		this.mBqCalBase.setEdorType(mLPEdorItemSchema.getEdorType());
		if (mZTPoint == null) {
			return false;
		}
		// =====del========zhangtao=======2005-08-14================BGN==================
		// 根据PolNo查询出保单生效日期
		// String sql = " select cvalidate from lccont " +
		// " where contno = " +
		// " (select contno from lcpol where polno = '" + mPolNo + "')";
		String sql = "Select CValiDate from LCPol where polno = '" + "?mPolNo?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mPolNo", mPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		mCValiDate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return false;
		}
		if (mCValiDate == null || mCValiDate.equals("")) {
			CError.buildErr(this, "保单生效日期为空!");
			return false;
		}
		if (mCValiDate.length() > 10) {
			mCValiDate = mCValiDate.substring(0, 10);
		}
		// =====del========zhangtao=======2005-08-14================END==================

		mCValiDate_Pol = mLCPolSchema.getCValiDate(); // 生效日期去险种的 add by
		// zhangtao 2005-08-14
		mEndDate_Pol = mLCPolSchema.getEndDate(); // 险种的终止日期
		Calculator tCalculator = new Calculator();
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode("CTTVer");// CT-Time-Version 依据指定时间确定险种退保算法
		tCalculator.addBasicFactor("RiskCode", mBqCalBase.getRiskCode());
		// add by jiaqiangli 2009-09-11 换成险种的投保日期来判断lcpol.polapplydate
		// 续保不会修改这个字段（续保只会修改lcpol.cvalidate）
		// 新增附加险这个字段=cvalidate 包含追溯与预约
		tCalculator.addBasicFactor("PolNo", mBqCalBase.getPolNo());
		tCalculator.addBasicFactor("ContNo", mBqCalBase.getContNo());
		tCTCodeFLag = tCalculator.calculate(); // 退保算法选择标记
		tCalculator.setBOMList(this.mBomList);
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "退保算法选择标记计算出错!");
			return false;
		}
		try {
			if ("-1".equals(tCTCodeFLag) || "".equals(tCTCodeFLag)) {
				CError.buildErr(this, "退保算法选择标记计算出错");
				return false;
			}

		} catch (Exception e) {
			CError.buildErr(this, "退保算法选择标记计算出错");
			return false;
		}

		// 根据保单生效日期计算保单年度
		String calYearsType = "1";
		if (calYearsType.equals("0")) // 0-舍弃法(向下撇)
		{
			mInterval = PubFun.calInterval(mCValiDate_Pol, mZTPoint, "Y");
		} else if (calYearsType.equals("1")) // 1-约进法(向上撇)
		{
			// mInterval = PubFun.calInterval2(mCValiDate_Pol, mZTPoint, "Y");
			// modify by jiaqiangli 2009-04-22 采用舍弃法再加1的算法
			// PubFun.calInterval2对paytodate当天交续期保费当天退保的保单年度没有做约进
			// cvaildate='2004-10-25' paytodate='2008-10-25'
			// 2008-10-25交2008-10-25至2009-10-25的续期保费时保单年度设置为5
			mInterval = PubFun.calInterval(mCValiDate_Pol, mZTPoint, "Y") + 1;
		}
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===BGN=====
		// if (mInterval < 0)
		// {
		// CError.buildErr(this, "保单年度不符合实际!");
		// return false;
		// }
		// ==del===zhangtao===2007-07-04======支持兼业险种生效日期之前操作保全变更===END=====
		this.mBqCalBase.setInterval(mInterval);
		return true;
	}

	/**
	 * 判断险种是否是分红险
	 * 
	 * @param aRiskCode
	 * @param aPayToDate
	 * @return
	 */
	private String getBonusFlag(String sRiskcode) {
		// 判断险种是否是分红险
		String sql = " select BonusFlag from lmriskapp "
				+ " where riskcode = '" + "?sRiskcode?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sRiskcode", sRiskcode);
		ExeSQL tExeSQL = new ExeSQL();
		String sBonusFlag = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询分红险标志失败!");
			return null;
		}
		if (sBonusFlag == null || sBonusFlag.equals("")
				|| sBonusFlag.equals("0")) {
			// 不是分红险
			sBonusFlag = "N";
		} else if (sBonusFlag != null && sBonusFlag.equals("2"))// 英式分红
		{
			sBonusFlag = "Y";
		} else {
			sBonusFlag = "M";
		}

		return sBonusFlag;
	}

	/**
	 * 计算宽限期止期
	 * 
	 * @param aRiskCode
	 * @param aPayToDate
	 * @return
	 */
	public static String CalLapseDate(String aRiskCode, String aPayToDate) {
		String aLapseDate = "";
		Date tLapseDate = null;
		FDate tFDate = new FDate();
		int nExtendLapseDates;

		if ((aPayToDate == null) || aPayToDate.trim().equals("")) {
			logger.debug("没有交至日期，计算宽限止期失败");

			return aLapseDate;
		}

		// 获取险种交费失效描述
		LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
		tLMRiskPayDB.setRiskCode(aRiskCode);

		LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();

		if (tLMRiskPaySet.size() > 0) {
			if ((tLMRiskPaySet.get(1).getGracePeriodUnit() == null)
					|| tLMRiskPaySet.get(1).getGracePeriodUnit().equals("")) {
				logger.debug("缺少险种交费失效描述!");

				int tLapseInterval = 60;
				aLapseDate = transCalDate(aPayToDate, tLapseInterval, "D", null);
			} else {
				// int tLapseInterval = tLMRiskPaySet.get(1).getGracePeriod()
				// -1;
				// 取宽限期不减一
				int tLapseInterval = tLMRiskPaySet.get(1).getGracePeriod();
				aLapseDate = transCalDate(aPayToDate, tLapseInterval,
						tLMRiskPaySet.get(1).getGracePeriodUnit(), null);
				// =====del====liurx======2005-07-05=============================BGN=============
				// //按月进位，舍弃日精度
				// if (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("1"))
				// {
				// // tLapseDate = tFDate.getDate(aLapseDate);
				// // tLapseDate.setMonth(tLapseDate.getMonth() + 1);
				// // tLapseDate.setDate(1);
				// // aLapseDate = tFDate.getString(tLapseDate);
				//
				// GregorianCalendar tCalendar = new GregorianCalendar();
				// tCalendar.setTime(tFDate.getDate(aLapseDate));
				// //月份进位，舍弃日精度
				// tCalendar.set(tCalendar.get(Calendar.YEAR),
				// tCalendar.get(Calendar.MONTH) + 1, 1);
				// aLapseDate = tFDate.getString(tCalendar.getTime());
				// }
				//
				// //按年进位，只舍弃了日精度，为什么不舍弃月精度？
				// else if
				// (tLMRiskPaySet.get(1).getGraceDateCalMode().equals("2"))
				// {
				// // tLapseDate = tFDate.getDate(aLapseDate);
				// // tLapseDate.setYear(tLapseDate.getYear() + 1);
				// // tLapseDate.setDate(1);
				// // aLapseDate = tFDate.getString(tLapseDate);
				//
				// GregorianCalendar tCalendar = new GregorianCalendar();
				// tCalendar.setTime(tFDate.getDate(aLapseDate));
				// //年份进位，舍弃日精度，不舍弃月精度
				// tCalendar.set(tCalendar.get(Calendar.YEAR) + 1,
				// tCalendar.get(Calendar.MONTH), 1);
				// aLapseDate = tFDate.getString(tCalendar.getTime());
				// }
				// =====del====liurx======2005-07-05=============================END=============
			}
		} else {
			int tLapseInterval = 60;
			aLapseDate = transCalDate(aPayToDate, tLapseInterval, "D", null);
		}

		// add by jiaqiangli 2008-10-15 从PubFun.calLapseDate 宽限期延长期
		// 取得宽限期延长期
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("ExtendLapseDates");
		if (!tLDSysVarDB.getInfo()) {
			nExtendLapseDates = 0;
		} else {
			nExtendLapseDates = Integer.parseInt(tLDSysVarDB.getSchema()
					.getSysVarValue());
			aLapseDate = transCalDate(aLapseDate, nExtendLapseDates, "D", null);
		}

		return aLapseDate;
	}

	/**
	 * tongmeng 2011-01-19 add 支持规则引擎推算宽限期
	 * 
	 * @param tLCPolSchema
	 * @param aRiskCode
	 * @param aPayToDate
	 * @return
	 */
	public static String CalLapseDateNew(LCPolSchema tLCPolSchema,
			String aRiskCode, String aPayToDate) {
		String aLapseDate = "";
		Date tLapseDate = null;
		FDate tFDate = new FDate();
		int nExtendLapseDates;

		if ((aPayToDate == null) || aPayToDate.trim().equals("")) {
			logger.debug("没有交至日期，计算宽限止期失败");

			return aLapseDate;
		}

		// 获取险种交费失效描述
		LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
		tLMRiskPayDB.setRiskCode(aRiskCode);

		LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();

		if (tLMRiskPaySet.size() > 0) {
			if ((tLMRiskPaySet.get(1).getGracePeriodUnit() == null)
					|| tLMRiskPaySet.get(1).getGracePeriodUnit().equals("")) {
				logger.debug("缺少险种交费失效描述!");

				if (tLMRiskPaySet.get(1).getGraceCalCode() == null
						|| tLMRiskPaySet.get(1).getGraceCalCode().equals("")) {
					int tLapseInterval = 60;
					aLapseDate = transCalDate(aPayToDate, tLapseInterval, "D",
							null);
				} else {
					// 如果没有描述,取算法计算结果
					BOMCont cont = new BOMCont();
					cont.setPayIntv(String.valueOf(tLCPolSchema.getPayIntv()));

					List list = new ArrayList();
					list.add(cont);

					RuleUI rule = new RuleUI();

					Calculator tCalculator = new Calculator();
//					tCalculator.setBOMList(prepareBOMListForStatic());// 添加BOMList
					tCalculator.setBOMList(list);
					tCalculator.setCalCode(tLMRiskPaySet.get(1)
							.getGraceCalCode());
					String tCalResult = tCalculator.calculate();

					// 获取间隔和标记
					String tGracePeriod = tCalResult
							.replaceAll("[A-Z,a-z]", "");
					String tGracePreiodUnit = tCalResult
							.replaceAll("[0-9]", "");
					aLapseDate = transCalDate(aPayToDate,
							Integer.parseInt(tGracePeriod), tGracePreiodUnit,
							null);
				}

			} else {
				// int tLapseInterval = tLMRiskPaySet.get(1).getGracePeriod()
				// -1;
				// 取宽限期不减一
				int tLapseInterval = tLMRiskPaySet.get(1).getGracePeriod();
				aLapseDate = transCalDate(aPayToDate, tLapseInterval,
						tLMRiskPaySet.get(1).getGracePeriodUnit(), null);

			}
		} else {
			int tLapseInterval = 60;
			aLapseDate = transCalDate(aPayToDate, tLapseInterval, "D", null);
		}

		// add by jiaqiangli 2008-10-15 从PubFun.calLapseDate 宽限期延长期
		// 取得宽限期延长期
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("ExtendLapseDates");
		if (!tLDSysVarDB.getInfo()) {
			nExtendLapseDates = 0;
		} else {
			nExtendLapseDates = Integer.parseInt(tLDSysVarDB.getSchema()
					.getSysVarValue());
			aLapseDate = transCalDate(aLapseDate, nExtendLapseDates, "D", null);
		}

		return aLapseDate;
	}

	/**
	 * 分红记录红利求和
	 * 
	 * @return double
	 */
	private double sumBonus(LOEngBonusPolSet pLOEngBonusPolSet) {
		if (pLOEngBonusPolSet == null || pLOEngBonusPolSet.size() < 1) {
			return 0;
		}
		double dSumBonus = 0.0;
		for (int i = 1; i <= pLOEngBonusPolSet.size(); i++) {
			dSumBonus += pLOEngBonusPolSet.get(i).getBonusAmnt();
		}
		return dSumBonus;
	}

	/**
	 * 四舍五入保留两位小数
	 * 
	 * @param dInput
	 * @return boolean
	 */
	private double format(double dInput) {
		// double d1 = Math.round(dInput * 100);
		// double dReturn = d1 / 100;
		// double dReturn = Double.parseDouble(mDecimalFormat.format(dInput));
		double dReturn = Arith.round(dInput, 2); // add by tangpei 2006-11-16
		return dReturn;
	}

	/**
	 * 创建批改补退费记录
	 * 
	 * @param dMoneyValue
	 *            补退费金额
	 * @param sSubFeeOperationType
	 *            子业务类型
	 * @return boolean
	 */
	private boolean createLJSGetEndorse(double dMoneyValue,
			String sSubFeeOperationType, String sBQFeeType,
			LCDutySchema pLCDutySchema) {
		// add by jiaqiangli 2009-03-16 金额为0时也生成一条记录，否则xt,ct不能保存且无法调整金额
		// add by jiaqiangli 2009-04-23 ct已作修改（lppol判断） 重新放开，否则垃圾数据过多
		if (Math.abs(dMoneyValue - 0) < 0.01
				&& (this.mLPEdorItemSchema != null && !"XT"
						.equals(this.mLPEdorItemSchema.getEdorType()))) {
			return true;
		}
		String sFeeType = BqCalBL.getFinType(mLPEdorItemSchema.getEdorType(),
				sBQFeeType, mLCPolSchema.getPolNo());

		BqCalBL tBqCalBL = new BqCalBL();
		LJSGetEndorseSchema tLJSGetEndorseSchema;
		tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = tBqCalBL.initLJSGetEndorse(mLPEdorItemSchema,
				mLCPolSchema, pLCDutySchema, sSubFeeOperationType, sFeeType,
				Arith.round(dMoneyValue, 2), mGlobalInput);
		// 用子业务类型区分同一保全项目下不同的费用
		if (tLJSGetEndorseSchema == null) {
			mErrors.copyAllErrors(tBqCalBL.mErrors);
			return false;
		}
		if ("P013".equals(sSubFeeOperationType)) // 退保费用为补费
		{
			tLJSGetEndorseSchema.setGetFlag("0");
		}
		if ("HD".equals(sFeeType) || "HK".equals(sFeeType)
				|| "LX".equals(sFeeType) || "GB".equals(sFeeType)) {
			tLJSGetEndorseSchema.setGetFlag("0");
		}
		mLJSGetEndorseSet.add(tLJSGetEndorseSchema);

		return true;
	}

	/**
	 * 返回基本保额现金价值
	 * 
	 * @return double
	 */
	public double getBaseCashValue() {
		return mBaseCashValue;
	}

	/**
	 * 返回累计红利保额现金价值
	 * 
	 * @return double
	 */
	public double getBonusCashValue() {
		return mBonusCashValue;
	}

	/**
	 * 返回万能险退保手续费
	 * 
	 * @return double
	 */
	public double getTBFee() {
		return mTBFee;
	}

	/**
	 * 返回健加费应退金额
	 * 
	 * @return double
	 */
	public double getAddPremH() {
		return mAddPremH;
	}

	/**
	 * 返回职业加费应退金额
	 * 
	 * @return double
	 */
	public double getAddPremO() {
		return mAddPremO;
	}

	/**
	 * 返回退保类型 1-犹豫期内 2-犹豫期外
	 * 
	 * @return String
	 */
	public String getCTType() {
		return mCTType;
	}

	/**
	 * 计算时点类型 （为理赔计算累计红利保额）（催付计算累计红利保额）
	 * 
	 * @param sCalDateType
	 *            LP-理赔 CF-催付
	 * @return
	 */
	public void setCalDateType(String sCalDateType) {
		mCalDateType = sCalDateType;
	}

	/**
	 * 传入累计红利保额（计算红利保额现金价值时）
	 * 
	 * @param dmInBonusAmnt
	 * @return
	 */
	public void setInBonusAmnt(double dInBonusAmnt) {
		mInBonusAmnt = dInBonusAmnt;
	}

	/**
	 * 传入变更后的险种信息 （保全变更后重算采用传入方式）
	 * 
	 * @param pLPPolSchema
	 * @return
	 */
	public void setLPPolSchema(LPPolSchema pLPPolSchema) {
		mLPPolSchema = pLPPolSchema;
	}

	/**
	 * 传入变更后的责任信息（保全变更后重算采用传入方式）
	 * 
	 * @param pLPDutySet
	 * @return
	 */
	public void setLPDutySet(LPDutySet pLPDutySet) {
		mLPDutySet = pLPDutySet;
	}

	/**
	 * 计算终了红利时有效保额是否包含累计红利保额标志（293、294理赔计算时特殊情况）
	 * 
	 * @param bFinaBonusFlag
	 *            true-包含 false-不包含
	 * @return
	 */
	public void setFinaBonusFlag(boolean bFinaBonusFlag) {
		mFinaBonusFlag = bFinaBonusFlag;
	}

	/**
	 * 累计保费传入标志
	 * 
	 * @param sSumPremFlag
	 * @return
	 */
	public void setSumPremFlag(String sSumPremFlag) {
		mSumPremFlag = sSumPremFlag;
	}

	/**
	 * 下一年度分红标记
	 * 
	 * @param bNextYearFlag
	 * @return
	 */
	public void setNextYearFlag(boolean bNextYearFlag) {
		mNextYearFlag = bNextYearFlag;
	}

	/**
	 * 返回在计算过程中创建的批改补退费记录
	 * 
	 * @return LJSGetEndorseSet
	 */
	public LJSGetEndorseSet getLJSGetEndorseSet() {
		return mLJSGetEndorseSet;
	}

	/**
	 * 返回万能险账户结算信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	// /**
	// * 投连险退保账户操作
	// * @param sPolNo
	// * @param sBalaDate
	// * @return boolean
	// */
	// private boolean dealAcc(String sPolNo, String sBalaDate)
	// {
	// LCInsureAccSet instLCInsureAccSet = new LCInsureAccSet();
	// LCInsureAccClassSet instLCInsureAccClassSet = new LCInsureAccClassSet();
	// LCInsureAccTraceSet instLCInsureAccTraceSet = new LCInsureAccTraceSet();
	//
	// LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
	// String strSql = "select * from LCInsureAcc where PolNo ='" + sPolNo +
	// "'";
	// LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(strSql);
	// if (tLCInsureAccDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "保单帐户查询失败!");
	// return false;
	// }
	// if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1)
	// {
	// CError.buildErr(this, "保单没有帐户数据!");
	// return false;
	// }
	//
	// for (int i = 1; i <= tLCInsureAccSet.size(); i++)
	// {
	// //不能对账户总表做修改，备份到P表即可
	// instLCInsureAccSet.add(tLCInsureAccSet.get(i));
	//
	// strSql = " select * from LCInsureAccclass where PolNo = '" + sPolNo + "'"
	// +
	// " and InsuAccNo = '" + tLCInsureAccSet.get(i).getInsuAccNo() + "' ";
	// LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
	// LCInsureAccClassSet tLCInsureAccClassSet =
	// tLCInsureAccClassDB.executeQuery(strSql);
	// if (tLCInsureAccClassDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "保单子帐户查询失败!");
	// return false;
	// }
	// if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() < 1)
	// {
	// CError.buildErr(this, "保单没有子帐户数据!");
	// return false;
	// }
	//
	// for (int j = 1; j <= tLCInsureAccClassSet.size(); j++)
	// {
	// //不能对账户分类表做修改，备份到P表即可
	// instLCInsureAccClassSet.add(tLCInsureAccClassSet.get(j));
	//
	// //分别针对每个账户分类表增加一条退保轨迹记录
	// Reflections ref = new Reflections();
	//
	// LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
	// LCInsureAccTraceSchema();
	// ref.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSet.get(j));
	// String tLimit =
	// PubFun.getNoLimit(tLCInsureAccClassSet.get(j).getManageCom());
	// String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
	// tLCInsureAccTraceSchema.setSerialNo(serNo);
	// if (mCTType.equals("1"))
	// {
	// tLCInsureAccTraceSchema.setMoneyType("TF"); //犹豫期退保金额类型为 TF-退费
	// }
	// else
	// {
	// tLCInsureAccTraceSchema.setMoneyType("TB"); //金额类型为 TB-退保金
	// }
	// tLCInsureAccTraceSchema.setState("0");
	// tLCInsureAccTraceSchema.setFeeCode("000000");
	// tLCInsureAccTraceSchema.setPayDate(sBalaDate);
	// tLCInsureAccTraceSchema.setMakeDate(sBalaDate);
	// tLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
	// tLCInsureAccTraceSchema.setModifyDate(sBalaDate);
	// tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
	// tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
	// tLCInsureAccTraceSchema.setAccAlterType("1");
	// tLCInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.getEdorNo());
	// tLCInsureAccTraceSchema.setBusyType(mLPEdorItemSchema.getEdorType());
	// tLCInsureAccTraceSchema.setUnitCount(-tLCInsureAccClassSet.get(j).getUnitCount());
	// instLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
	//
	// }
	//
	// }
	//
	// mResult.add(instLCInsureAccSet);
	// mResult.add(instLCInsureAccClassSet);
	// mResult.add(instLCInsureAccTraceSet);
	// return true;
	//
	// }

	/**
	 * 按账户退保
	 * 
	 * @param sPolNo
	 * @param sBalaDate
	 * @return double
	 */
	public double getCTMoneyByAcc(String sPolNo, String sBalaDate) {
		double dZTMoneyByAcc = 0.0;
		// 查询保单下所有保费项
		LCPremSet tLCPremSet = getLCPrem(sPolNo);
		if (tLCPremSet == null) {
			CError.buildErr(this, "保费表查询失败!" + "保单号：" + sPolNo);
			return -1;
		}
		InsuAccBala tInsuAccBala = new InsuAccBala();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

		// 查出该保单的所有帐户分类
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(sPolNo);
		tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() < 1) {
			CError.buildErr(this, "保单下没有帐户!" + "保单号：" + sPolNo);
			return -1;
		}

		LCInsureAccClassSchema tLCInsureAccClassSchema;
		for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);

			double dAccInterest = 0.0; // 本次结算利息
			double dInsuAccBala = 0.0; // 最终价值

			AccountManage tAccountManage = new AccountManage();

			TransferData mReturnData = new TransferData(); // 返回团体帐户金额
			if (!sBalaDate.equals(tLCInsureAccClassSchema.getBalaDate()))

			{
				/*
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.2 团体帐户截息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tRateType = "Y"; // 原始利率类型（）
				String tIntvType = "D"; // 目标利率类型（日利率）
				int tPerio = 0; // 银行利率期间
				String tType = "F"; // 截息计算类型（单利还是复利）
				String tDepst = "D"; // 贷存款标志（贷款还是存款）

				mReturnData = tAccountManage.getAccClassInterestNewMS(
						tLCInsureAccClassSchema, sBalaDate, tRateType,
						tIntvType);

				if (mReturnData != null) {
					String tempmoney = String.valueOf(mReturnData
							.getValueByName("aAccClassSumPay"));
					dZTMoneyByAcc = Double.parseDouble(tempmoney);
					tempmoney = String.valueOf(mReturnData
							.getValueByName("aAccClassInterest"));
					dAccInterest = Double.parseDouble(tempmoney);
					logger.debug("===团体帐户结息金额==" + dZTMoneyByAcc);
					logger.debug("===团体帐户结息利息==" + dAccInterest);
				} else {
					dZTMoneyByAcc = 0.0;
				}
				/*
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.3
				 * 更新团体帐户LCInsureAccClass表信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				tLCInsureAccClassSchema.setBalaDate(sBalaDate);
				tLCInsureAccClassSchema.setBalaTime(PubFun.getCurrentTime());
				tLCInsureAccClassSchema.setLastAccBala(dZTMoneyByAcc); // 团体帐户金额
				tLCInsureAccClassSchema.setInsuAccBala(dZTMoneyByAcc); // 团体帐户金额
				tLCInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				dZTMoneyByAcc = tLCInsureAccClassSchema.getInsuAccBala();
			}

			tLCInsureAccClassSet.get(i).setSchema(tLCInsureAccClassSchema);

			LCPremToAccSchema tLCPremToAccSchema;
			LCInsureAccSchema tLCInsureAccSchema;
			LCInsureAccTraceSchema tLCInsureAccTraceSchema;
			LCInsureAccTraceSchema tLCInsureAccTraceSchemaAdd;
			LCInsureAccTraceSchema tLCInsureAccTraceSchemaZT;
			LCPremToAccDB tLCPremToAccDB = new LCPremToAccDB();
			for (int j = 1; j <= tLCPremSet.size(); j++) {
				tLCPremToAccSchema = new LCPremToAccSchema();
				tLCPremToAccDB.setPolNo(tLCInsureAccClassSchema.getPolNo());
				tLCPremToAccDB.setInsuAccNo(tLCInsureAccClassSchema
						.getInsuAccNo());
				LCPremToAccSet tLCPremToAccSet = tLCPremToAccDB.query();
				if (tLCPremToAccDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询保费账户关联信息失败!");
					return -1;
				}
				if (tLCPremToAccSet == null || tLCPremToAccSet.size() < 1) {
					CError.buildErr(this, "没有查到保费账户关联信息!");
					return -1;
				}
				tLCPremToAccSchema = tLCPremToAccSet.get(1);

				// 更新账户轨迹表数据
				tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				String sLimit = PubFun.getNoLimit(tLCInsureAccClassSchema
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);

				tLCInsureAccTraceSchema.setSerialNo(serNo);
				tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSchema
						.getPolNo());
				tLCInsureAccTraceSchema.setMoneyType("PG");
				tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccClassSchema
						.getRiskCode());
				tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccClassSchema
						.getOtherNo());
				tLCInsureAccTraceSchema.setPayNo(tLCInsureAccClassSchema
						.getOtherNo());
				tLCInsureAccTraceSchema.setOtherType(tLCInsureAccClassSchema
						.getOtherType());
				tLCInsureAccTraceSchema.setMoney(-dZTMoneyByAcc);
				tLCInsureAccTraceSchema.setContNo(tLCInsureAccClassSchema
						.getContNo());
				tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccClassSchema
						.getGrpPolNo());
				tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccClassSchema
						.getInsuAccNo());
				tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSchema
						.getPolNo());
				tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSchema
						.getGrpContNo());
				tLCInsureAccTraceSchema.setState(tLCInsureAccClassSchema
						.getState());
				tLCInsureAccTraceSchema.setManageCom(tLCInsureAccClassSchema
						.getManageCom());
				tLCInsureAccTraceSchema.setOperator(tLCInsureAccClassSchema
						.getOperator());
				tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccClassSchema
						.getGrpContNo());
				tLCInsureAccTraceSchema.setPayPlanCode(tLCPremToAccSchema
						.getPayPlanCode());
				tLCInsureAccTraceSchema.setState("1");
				tLCInsureAccTraceSchema.setAccAscription("1");
				// 默认未归属
				/*------------------查询管理费代码---------start--------*/
				LMRiskFeeSchema tLMRiskFeeSchema = new LMRiskFeeSchema();
				LMRiskFeeDB aLMRiskFeeDB = new LMRiskFeeDB();
				aLMRiskFeeDB.setPayPlanCode(tLCInsureAccTraceSchema
						.getPayPlanCode());
				// tLMRiskFeeSchema = queryLMRiskFee(tLCInsureAccTraceSchema);
				tLMRiskFeeSchema = aLMRiskFeeDB.query().get(1);
				if (tLMRiskFeeSchema == null) {
					return -1;
				}
				tLCInsureAccTraceSchema.setFeeCode(tLMRiskFeeSchema
						.getFeeCode());
				/*------------------查询管理费代码---------end--------*/
				tLCInsureAccTraceSchema.setPayDate(sBalaDate); // modify by
				// wenhuan
				tLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
				tLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
				tLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
				tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
				tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);

			}

		}
		// 账户表
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(sPolNo);
		LCInsureAccSet sLCInsureAccSet = tLCInsureAccDB.query();
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保险帐户信息失败!");
			return -1;
		}
		if (sLCInsureAccSet == null || sLCInsureAccSet.size() < 1) {
			CError.buildErr(this, "没有查到保险帐户信息!");
			return -1;
		}
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		for (int c = 1; c <= sLCInsureAccSet.size(); c++) {
			tLCInsureAccSchema = sLCInsureAccSet.get(c);
			tLCInsureAccSchema.setBalaDate(sBalaDate);
			tLCInsureAccSchema.setInsuAccBala(0); // 退保置为0
			tLCInsureAccSchema.setState("1");
			tLCInsureAccSchema.setModifyDate(mCurrentDate);
			tLCInsureAccSchema.setModifyTime(mCurrentTime);
			tLCInsureAccSet.add(tLCInsureAccSchema);
		}

		// logger.debug(tLCInsureAccClassSet.encode());
		mResult.clear();
		mResult.add(tLCInsureAccSet);
		mResult.add(tLCInsureAccClassSet);
		mResult.add(tLCInsureAccTraceSet);

		return dZTMoneyByAcc;
	}

	// 退保费用计算，根据保单号，全部帐户价值等计算退保费用。
	public double getActuTLTBFee(double dMoney, String sRiskCode,
			String sEdorType, String sContNo, String sEdorValidate,
			int iInterval, int iIntervalM, String sCalTYpe) {
		double dAMoney = 0.0;

		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setCalType(sCalTYpe);
		tLMEdorCalDB.setRiskCode(sRiskCode);
		tLMEdorCalDB.setEdorType(sEdorType);
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
		if (tLMEdorCalDB.mErrors.needDealError()) {
			CError.buildErr(this, "账户险退保扣除手续费计算代码查询失败!");
			return -1;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1) {
			return 0; // 没有退保扣除手续费算法,返回0
		}

		Calculator tCalculator = new Calculator();
		if (!prepareBOMList()) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return -1;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		tCalculator.addBasicFactor("RiskCode", sRiskCode);
		tCalculator.addBasicFactor("ContNo", sContNo);
		tCalculator.addBasicFactor("EdorValidate", sEdorValidate);
		tCalculator.addBasicFactor("Money", String.valueOf(dMoney));
		tCalculator.addBasicFactor("Interval", String.valueOf(iInterval));// 保单年度
		tCalculator.addBasicFactor("IntervalM", String.valueOf(iIntervalM));// 保单经过月份
		String sAMoney = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "账户费用计算失败!");
			return -1;
		}
		try {
			dAMoney = Double.parseDouble(sAMoney);
		} catch (Exception e) {
			CError.buildErr(this, "账户费用计算结果错误!" + "错误结果：" + sAMoney);
			return -1;
		}
		return dAMoney;
	}

	/**
	 * 判断是否为投连险
	 * 
	 * @param RiskCode
	 * @return
	 */
	public boolean IsTLRisk(String RiskCode) {
		String IsSql = "select * from Lmriskapp where riskcode ='" + "?RiskCode?"
				+ "' and risktype3='3' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(IsSql);
		sqlbv.put("RiskCode", RiskCode);
		ExeSQL isTLExeSQL = new ExeSQL();
		SSRS isTLSSRS = new SSRS();
		isTLSSRS = isTLExeSQL.execSQL(sqlbv);
		if (isTLSSRS != null && isTLSSRS.MaxRow > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList() {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(this.mBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL
						.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

}
