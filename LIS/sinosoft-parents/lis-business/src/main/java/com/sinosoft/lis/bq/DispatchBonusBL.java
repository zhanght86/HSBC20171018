package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LMCheckFieldDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOEngBonusPolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LOEngBonusPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 红利分配
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @version 1.0
 */
public class DispatchBonusBL {
private static Logger logger = Logger.getLogger(DispatchBonusBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 业务数据 */
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private String mPolNo = "";
	private String mForceDVFlag = "0"; // 是否强制分红标志，0：正常分红；1：强制分红
	// 如果在调用DispatchBonusBL时没有set此标志，
	// 将默认为0
	private String mDispatchType = "0"; // 分红派发类型，0-年度分红；1-补派分红 2-强制分红
	private String mEdorValiDate = mCurrentDate;
	private String mBonusMakeDate = ""; // 红利公布日
	private boolean mIsSubmit = true; // 是否提交
	private String mForceDate = ""; // 强制分红最晚日期（针对进入领取期不能再分红的需特殊处理）
	private String mContNo = "";
	private String mGrpContNo = "";
	private String mValiDate = ""; // 险种生效日期
	private double mAmnt = 0; // 基本保额
	private int mPayEndYear = 0; // 缴费终止年期或年龄
	private int mPayIntv = 0; // 险种生效日期
	private String mManageCom = ""; // 管理机构
	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
	private LPEdorItemSchema mLPEdorItemSchema=new LPEdorItemSchema();
//	private boolean mBomListFlag = false;
	private BqCalBase mBqCalBase = new BqCalBase();
	private List mBomList = new ArrayList();
	private String mAgentCode = ""; // 代理人编码
	private int mInterval = 0; // 保单年度
	private String mCoValiDate = ""; // 本年度生效对应日
	private String mLastBonusCValiDate = "";
	private int curFiscalYear = 0; // 当前会计年度
	private int preFiscalYear = 0; // 当前会计年度
	private String mOperator = "000";
	private String mCom = "86";
	private String mRiskCode = ""; // 险种编码
	private double mBonusAmnt = 0; // 年度红利保额
	private double mAvailableAmnt = 0; // 有效保额
	private double mBonusRate;
	private String mSDispatchDate; // 红利应该分配时间
	private String mADispatchDate; // 实际红利分配时间
	private boolean mCFFlag = false; // 催付标记
	private boolean mNextYearFlag = false; // 下一年度分红标记（不用判断红利是否公布以及是否已到红利公布日期）
	private LOEngBonusPolSet mLOEngBonusPolSet = new LOEngBonusPolSet(); // 本次分红产生的分红记录
	private LOEngBonusPolSet mINLOEngBonusPolSet = new LOEngBonusPolSet(); // 传入的分红记录
	private LOEngBonusPolSet mGetLOEngBonusPolSet = new LOEngBonusPolSet(); // 领取的红利记录
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet(); // 分红业绩报告书打印记录
	// private LOEngBonusPolSchema mLOEngBonusPolSchema = new
	// LOEngBonusPolSchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LJABonusGetSet mLJABonusGetSet = new LJABonusGetSet();
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private MMap updMap_GetFlag = new MMap();

	public DispatchBonusBL() {
	}

	/**
	 * 对险种进行红利分配
	 * 
	 * @param: 无
	 * @return: 1：操作成功 0：操作失败 -1：当前不能分红
	 */
	public boolean dispatchBonus() {
		if (!checkData()) {
			return false;
		}

		if (this.mForceDVFlag.trim().equals("1")) // 强制分红
		{
			if (mInterval < 1) // 未满一保单年度没有分红
			{
				return true;
			}

			// 判断是否进入领取期，如果领取期不可以分红的，强制分红日期为领取期起期
			mForceDate = getForceDate(mPolNo, mRiskCode, mEdorValiDate);
			if (mForceDate == null) {
				return false;
			}

		}

		String sStartGetFlag = ""; // 领取期分红标志
		if (this.mForceDVFlag.trim().equals("0")) {
			if (!canDispatch()) {
				return false;
			}
			// 校验是否进入领取期或进入领取期是否可以分红（用生效对应日）
			sStartGetFlag = checkStartGet(mPolNo, mRiskCode, mCoValiDate);
			if (sStartGetFlag == null) {
				return false;
			}
		}

		if (!sStartGetFlag.equals("Y")) {
			// 获取分红率
			mBonusRate = gemBonusRate();
			if (mBonusRate == -1) {
				return false;
			}

			if (!dispatch()) {
				mErrors.addOneError(new CError(mPolNo + "分红失败！"));
				return false;
			}
		}

		// 准备输出数据
		if (!prepareOutputData()) {
			return false;
		}

		if (mIsSubmit) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mInputData, "")) // 数据提交
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "DispatchBonusBL";
				tError.functionName = "normalDispatch";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			mInputData = null;
		}

		return true;
	}

	/**
	 * 进行保单的分红
	 * 
	 * @return boolean
	 */
	private boolean dispatch() {
		if (this.mDispatchType.trim().equals("0")) // 年度分红
		{
			if (this.mForceDVFlag.trim().equals("0")) // 正常分红
			{
				if (!normalDispatch()) {
					return false;
				}
			} else if (this.mForceDVFlag.trim().equals("1")) // 强制分红
			{
				if (!forceDispatch()) {
					return false;
				}
			}
		} else {
			// 补派分红
			if (!patchDispatch()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 进行强制分红
	 * 
	 * @param: mBonusRate 该险种的分红率
	 * @return: true：操作成功 false：操作失败
	 */
	private boolean forceDispatch() {
		logger.debug(mPolNo + "强制红利分配进行中........");

		int startFiscalYear = 0;
		String strSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		double sumBonusAmnt = 0; // 累计红利保额
		FDate mFDate = new FDate();
		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(mFDate.getDate(mValiDate));
		startFiscalYear = mCalendar.get(Calendar.YEAR); // 得到该保单最早可能分红的会计年度
		if (preFiscalYear == startFiscalYear) {
			sumBonusAmnt = mAmnt; // 如果是第一次分红，则采用其基本保额
		} else {
			logger.debug("start" + mInterval);
			DispatchBonusBL mDispatchBonusBL;
			logger.debug("startFiscalYear" + startFiscalYear);
			logger.debug("preFiscalYear" + preFiscalYear);
			String patchDate = "";
			for (int i = startFiscalYear; i <= preFiscalYear - 1; i++) {

				strSql = "select availableamnt from loengbonuspol where polno = '"
						+ "?mPolNo?" + "' and fiscalyear = " + "?i?";
				sqlbv.sql(strSql);
				sqlbv.put("mPolNo", mPolNo);
				sqlbv.put("i", i);
				tssrs = q_exesql.execSQL(sqlbv);
				if (tssrs == null || tssrs.getMaxRow() < 1) {
					patchDate = String.valueOf(i + 1) + "-12-31";
					mDispatchBonusBL = new DispatchBonusBL();
					mDispatchBonusBL.setPolNo(mPolNo);
					mDispatchBonusBL.setADispatchDate(mEdorValiDate);
					mDispatchBonusBL.setEdorValiDate(patchDate);
					mDispatchBonusBL.setDispatchType("1");
					// mDispatchBonusBL.setForceDVFlag("1"); 2006-12-08 与630相关
					mDispatchBonusBL.setIsSubmit(this.mIsSubmit); // 此标志递归继承
					mDispatchBonusBL.setNextYearFlag(mNextYearFlag);
					mDispatchBonusBL.setLOEngBonusPolSet(mLOEngBonusPolSet);
					if (!mDispatchBonusBL.dispatchBonus()) {
						logger.debug("第" + i + "年度补派红利分配失败");
						mErrors.copyAllErrors(mDispatchBonusBL.mErrors);
						return false;
					} else {
						logger.debug("第" + i + "年度补派红利分配成功");
						if (!this.mIsSubmit) {
							mLOEngBonusPolSet.add(mDispatchBonusBL
									.getLOEngBonusPolSet());
							mLJABonusGetSet.add(mDispatchBonusBL
									.getLJABonusGetSet());
							mLJAGetSet.add(mDispatchBonusBL.getLJAGetSet());
						}
					}
				}
			}
			sqlbv=new SQLwithBindVariables();
			
			if (mCFFlag) // 催付
			{
				strSql = " select sum(BonusAmnt) from LOEngBonusPol "
						+ " where (getflag is null or getflag <> '1') and polno = '"
						+ "?mPolNo?" + "' and sdispatchdate < '" + "?mEdorValiDate?"
						+ "' ";
				
				sqlbv.put("mPolNo", mPolNo);
				sqlbv.put("mEdorValiDate", mEdorValiDate);
				
			} else {
				strSql = " select sum(BonusAmnt) from LOEngBonusPol "
						+ " where (getflag is null or getflag <> '1') and polno = '"
						+ "?mPolNo?" + "' and sdispatchdate <= '" + "?mEdorValiDate?"
						+ "' ";
				sqlbv.put("mPolNo", mPolNo);
				sqlbv.put("mEdorValiDate", mEdorValiDate);
				// "' and fiscalyear between "+startFiscalYear+" and
				// "+(preFiscalYear-1);
			}
			sqlbv.sql(strSql);
			tssrs = q_exesql.execSQL(sqlbv);
			logger.debug("sum:" + tssrs.GetText(1, 1));
			if (tssrs != null && tssrs.getMaxRow() == 1) {
				String sumAmnt = tssrs.GetText(1, 1);
				if (sumAmnt != null && !sumAmnt.trim().equals("")) {
					try {
						sumBonusAmnt = Double.parseDouble(sumAmnt) + mAmnt;
					} catch (Exception e) {
						sumBonusAmnt = mAmnt;
					}
				} else {
					mErrors.addOneError(new CError(mPolNo + "计算累计红利保额失败！"));
					return false;
				}

			}
		}

		sumBonusAmnt += this.sumBonus();

		// 减去理赔冲减保额
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "000";
		tGlobalInput.ManageCom = "86110000";
		EdorCalZT tEdorCalZT = new EdorCalZT(tGlobalInput);
		double dPolReduceAmnt = tEdorCalZT.getReduceAmnt(mContNo, mPolNo,
				mEdorValiDate);
		if (dPolReduceAmnt == -1) {
			CError.buildErr(this, "冲减保额计算失败!");
			return false;
		}
		sumBonusAmnt -= dPolReduceAmnt;

		String sValiDate = "";
		// =======del=====zhangtao========2005-10-16=================BGN=================
		// strSql = "select availableamnt from loengbonuspol where polno = '" + mPolNo +
		// "' and fiscalyear = " + String.valueOf(preFiscalYear - 1) +
		// " and sdispatchdate <= '" + mEdorValiDate + "'";
		// tssrs = q_exesql.execSQL(strSql);
		// if (tssrs == null || tssrs.getMaxRow() < 1)
		// {
		// if (noBonus(preFiscalYear - 1, mEdorValiDate))
		// {
		// sValiDate = PubFun.calDate(mLastBonusCValiDate, -1, "Y", null);
		// if (PubFun.calInterval(mValiDate, sValiDate, "D") < 0)
		// {
		// sValiDate = mLastBonusCValiDate;
		// }
		// }
		// else
		// {
		// sValiDate = mLastBonusCValiDate;
		// }
		// }
		// else
		// {
		// sValiDate = mLastBonusCValiDate;
		// }
		// =======del=====zhangtao========2005-10-16=================END=================

		// =======ADD=====zhangtao========2005-10-16=================BGN=================
		sqlbv=new SQLwithBindVariables();
		if (mCFFlag) // 催付
		{
			strSql = "select max(sdispatchdate) from loengbonuspol where polno = '"
					+ "?mPolNo?" + "' and sdispatchdate < '" + "?mEdorValiDate?" + "'";
	
			
			sqlbv.put("mPolNo", mPolNo);
			sqlbv.put("mEdorValiDate", mEdorValiDate);
		} else {
			strSql = "select max(sdispatchdate) from loengbonuspol where polno = '"
					+ "?mPolNo?" + "' and sdispatchdate <= '" + "?mEdorValiDate?" + "'";

			sqlbv.put("mPolNo", mPolNo);
			sqlbv.put("mEdorValiDate", mEdorValiDate);
		}
		sqlbv.sql(strSql);
		String sDispatchdate = q_exesql.getOneValue(sqlbv);
		if (q_exesql.mErrors.needDealError()) {
			CError.buildErr(this, "分红记录查询失败!");
			return false;
		}
		if (sDispatchdate == null || sDispatchdate.trim().equals("")) {
			// 没有分红记录
			sDispatchdate = mValiDate;
		}
		if (sDispatchdate.length() > 10) {
			sDispatchdate = sDispatchdate.substring(0, 10);
		}
		sValiDate = getMaxSDispatchdate(sDispatchdate);
		// =======ADD=====zhangtao========2005-10-16=================END=================
		int h = PubFun.calInterval(sValiDate, mForceDate, "M");
		if (h < 0) {
			h = 0;
		}
		if (h > 12) {
			mBonusAmnt = sumBonusAmnt * mBonusRate;
			sumBonusAmnt += mBonusAmnt;
			h -= 12;
		}
		String sBonusWholeFlag = ""; // 零头月分红标志
		// 校验是否零头月允许分红
		sBonusWholeFlag = checkBonusWhole(mPolNo, mRiskCode);
		if (!sBonusWholeFlag.equals("Y") || h == 12) {
			mBonusAmnt += sumBonusAmnt * (h * mBonusRate / 12);
		} else {
			mBonusAmnt = 0;
		}
		mAvailableAmnt = sumBonusAmnt + mBonusAmnt;

		mSDispatchDate = calSDispatchDate(mBonusMakeDate, mCoValiDate);
		mADispatchDate = mEdorValiDate;
		if (mBonusAmnt > 0) {
			setSchemas();
		}
		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLOEngBonusPolSet, "INSERT");
		// ===add===zhangtao===2006-08-30====判断如果红利处理方式为领取，则生成红利领取记录，并且将分红记录置为已领取===BGN===
		if (mLJABonusGetSet != null && mLJABonusGetSet.size() > 0) {
			mMap.put(mLJABonusGetSet, "INSERT");
		}
		if (mLJAGetSet != null && mLJAGetSet.size() > 0) {
			mMap.put(mLJAGetSet, "INSERT");
		}
		if (mGetLOEngBonusPolSet != null && mGetLOEngBonusPolSet.size() > 0) {
			mMap.put(mGetLOEngBonusPolSet, "UPDATE");
		}
		// ===add===zhangtao===2006-08-30====判断如果红利处理方式为领取，则生成红利领取记录，并且将分红记录置为已领取===END===
		if (this.mForceDVFlag.trim().equals("0")) {
			mMap.put(mLOPRTManagerSet, "INSERT");
		}
		mInputData.clear();
		mInputData.add(mMap);

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mLOEngBonusPolSet);

		return true;
	}

	/**
	 * 得到某险种当年的分红率
	 * 
	 * @return boolean
	 */
	public double gemBonusRate() {
		double mBonusRate = 0;
		String tPolNo = this.mPolNo;
		if (tPolNo != null && !tPolNo.trim().equals("")) {
			String sGetBonusRateDate = mEdorValiDate; // 取红利率日期
			String sForceDVFlag = mForceDVFlag; // 是否强制取分红率

			// 校验是否进入领取期或进入领取期是否可以分红（传入日期）
			String sStartGetFlag = checkStartGet(mPolNo, mRiskCode,
					mEdorValiDate);
			if (sStartGetFlag == null) {
				return -1;
			}
			if (sStartGetFlag.equals("Y")) {
				// 进入领取期不可以分红的，按领取期起期强制取分红率
				sForceDVFlag = "1";
				sGetBonusRateDate = getForceDate(mPolNo, mRiskCode,
						mEdorValiDate);
				if (sGetBonusRateDate == null) {
					return -1;
				}
			}
            
			String strSql = " select distinct(a.calcode) from lmedorcal a,lcpol b where a.riskcode = b.riskcode "
					+ " and a.riskver = b.riskversion and b.polno = '"
					+ "?tPolNo?"
					+ "' and a.caltype = 'BonusRate' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tPolNo", tPolNo);
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			tssrs = q_exesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
				if (mNextYearFlag) // 计算下一年度分红标志
				{
					sForceDVFlag = "1";
				}
				mBqCalBase.setPolNo(tPolNo);
				mBqCalBase.setEdorValiDate(sGetBonusRateDate);
				mBqCalBase.setPayEndYear(mPayEndYear);
				mBqCalBase.setForceDVFlag(sForceDVFlag);
				mBqCalBase.setPayIntv(mPayIntv);
				mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				String mCalCode = tssrs.GetText(1, 1);
				Calculator tCalculator = new Calculator();
				tCalculator.setCalCode(mCalCode);
				tCalculator.addBasicFactor("EdorValiDate", sGetBonusRateDate);
				tCalculator.addBasicFactor("PayEndYear", String
						.valueOf(mPayEndYear));
				tCalculator.addBasicFactor("ForceDVFlag", sForceDVFlag);
				tCalculator.addBasicFactor("PayIntv", String.valueOf(mPayIntv));
				tCalculator.addBasicFactor("PolNo", tPolNo);
				String tStr = tCalculator.calculate();
				if (tCalculator.mErrors.needDealError()) {
					CError.buildErr(this, "查询年度红利率失败 !");
					return -1;
				}
				if (tStr == null || tStr.trim().equals("")) {
					CError.buildErr(this, "未查到年度红利率!");
					return -1;
				}
				if (tStr != null && !tStr.trim().equals("")) {
					try {
						mBonusRate = Double.parseDouble(tStr);
					} catch (Exception e) {
						mErrors.addOneError(new CError("年度分红率计算结果错误!" + "错误结果："
								+ tStr));
						return -1;
					}
				}

				logger.debug("Cal Result: BonusRate of " + tPolNo + " ="
						+ mBonusRate);
			}
		}

		return mBonusRate;
	}

	/**
	 * 正常红利分配
	 * 
	 * @return boolean
	 */
	private boolean normalDispatch() {
		logger.debug(mPolNo + "正常红利分配进行中........");

		int startFiscalYear = 0;
		String strSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		double sumBonusAmnt = 0; // 累计红利保额
		FDate mFDate = new FDate();
		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(mFDate.getDate(mValiDate));
		startFiscalYear = mCalendar.get(Calendar.YEAR); // 得到该保单最早应该分红的会计年度
		if (preFiscalYear == startFiscalYear) {
			sumBonusAmnt = mAmnt; // 如果是第一次分红，则采用其基本保额
		} else {
			strSql = "select sum(bonusamnt) from loengbonuspol where (getflag is null or getflag <> '1') and polno = '"
					+ "?mPolNo?"
					+ "' and fiscalyear between "
					+ "?startFiscalYear?"
					+ " and " + "?preFiscalYear?";
			sqlbv.sql(strSql);
			sqlbv.put("mPolNo", mPolNo);
			sqlbv.put("startFiscalYear", startFiscalYear);
			sqlbv.put("preFiscalYear", preFiscalYear - 1);
			tssrs = q_exesql.execSQL(sqlbv);
			logger.debug("sum:" + tssrs.GetText(1, 1));
			if (tssrs != null && tssrs.getMaxRow() == 1) {
				String sumAmnt = tssrs.GetText(1, 1);
				if (sumAmnt != null && !sumAmnt.trim().equals("")) {
					try {
						sumBonusAmnt = Double.parseDouble(sumAmnt) + mAmnt;
					} catch (Exception e) {
						sumBonusAmnt = mAmnt;
					}
				}
			}
		}

		// 累加传入值（传入的分红记录尚未提交到数据库）
		sumBonusAmnt += this.sumBonus();

		// 减去理赔冲减保额
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "000";
		tGlobalInput.ManageCom = "86110000";
		EdorCalZT tEdorCalZT = new EdorCalZT(tGlobalInput);
		double dPolReduceAmnt = tEdorCalZT.getReduceAmnt(mContNo, mPolNo,
				mCoValiDate);
		if (dPolReduceAmnt == -1) {
			CError.buildErr(this, "冲减保额计算失败!");
			return false;
		}
		sumBonusAmnt -= dPolReduceAmnt;

		mBonusAmnt = sumBonusAmnt * mBonusRate;
		mAvailableAmnt = sumBonusAmnt + mBonusAmnt;

		mSDispatchDate = calSDispatchDate(mBonusMakeDate, mCoValiDate);
		mADispatchDate = mEdorValiDate;
		setSchemas();

		return true;
	}

	/**
	 * 补派分红
	 * 
	 * @return boolean
	 */
	private boolean patchDispatch() {
		logger.debug(mPolNo + "补派红利分配进行中........");

		int startFiscalYear = 0;
		String strSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		double sumBonusAmnt = 0; // 累计红利保额
		FDate mFDate = new FDate();
		GregorianCalendar mCalendar = new GregorianCalendar();
		mCalendar.setTime(mFDate.getDate(mValiDate));
		startFiscalYear = mCalendar.get(Calendar.YEAR); // 得到该保单最早应该分红的会计年度
		if (mInterval == 1) {
			sumBonusAmnt = mAmnt; // 如果是第一次分红，则采用其基本保额
		} else {
			strSql = "select sum(bonusamnt) from loengbonuspol where (getflag is null or getflag <> '1') and polno = '"
					+ "?mPolNo?"
					+ "' and fiscalyear between "
					+ "?startFiscalYear?"
					+ " and " + "?preFiscalYear?";
			sqlbv.sql(strSql);
			sqlbv.put("mPolNo", mPolNo);
			sqlbv.put("startFiscalYear", startFiscalYear);
			sqlbv.put("preFiscalYear", preFiscalYear - 1);
			tssrs = q_exesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() == 1) {
				String sumAmnt = tssrs.GetText(1, 1);
				if (sumAmnt != null && !sumAmnt.trim().equals("")) {
					try {
						sumBonusAmnt = Double.parseDouble(sumAmnt) + mAmnt;
					} catch (Exception e) {
						sumBonusAmnt = mAmnt;
					}
				}
			}
		}

		// 累加传入值（传入的分红记录尚未提交到数据库）
		sumBonusAmnt += this.sumBonus();

		// 减去理赔冲减保额
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "000";
		tGlobalInput.ManageCom = "86110000";
		EdorCalZT tEdorCalZT = new EdorCalZT(tGlobalInput);
		double dPolReduceAmnt = tEdorCalZT.getReduceAmnt(mContNo, mPolNo,
				mCoValiDate);
		if (dPolReduceAmnt == -1) {
			CError.buildErr(this, "冲减保额计算失败!");
			return false;
		}
		sumBonusAmnt -= dPolReduceAmnt;

		mBonusAmnt = sumBonusAmnt * mBonusRate;
		mAvailableAmnt = sumBonusAmnt + mBonusAmnt;

		mSDispatchDate = calSDispatchDate(mBonusMakeDate, mCoValiDate);

		setSchemas();

		return true;
	}

	public String getBonusMakeDate() {
		String tBonusMakeDate = "";

		String preEdorValiDate = PubFun.calDate(mEdorValiDate, -1, "Y", null);
		String tPolNo = this.mPolNo;
		if (tPolNo != null && !tPolNo.trim().equals("")) {
			String strSql = "select distinct(a.calcode) from lmedorcal a,lcpol b where a.riskcode = b.riskcode "
					+ "and a.riskver = b.riskversion and b.polno = '"
					+ "?tPolNo?"
					+ "' and a.caltype = 'DvDate' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tPolNo", tPolNo);
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			tssrs = q_exesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				mBqCalBase.setPolNo(tPolNo);
				mBqCalBase.setEdorValiDate(preEdorValiDate);
				mBqCalBase.setPayEndYear(mPayEndYear);
				mBqCalBase.setForceDVFlag(mForceDVFlag);
				mBqCalBase.setPayIntv(mPayIntv);
				mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				
				String mCalCode = tssrs.GetText(1, 1);
				Calculator tCalculator = new Calculator();
				tCalculator.setBOMList(mBomList);
				tCalculator.setCalCode(mCalCode);
				tCalculator.addBasicFactor("EdorValiDate", preEdorValiDate);
				tCalculator.addBasicFactor("PayEndYear", String
						.valueOf(mPayEndYear));
				tCalculator.addBasicFactor("ForceDVFlag", mForceDVFlag);
				tCalculator.addBasicFactor("PayIntv", String.valueOf(mPayIntv));
				tCalculator.addBasicFactor("PolNo", tPolNo);
				String tStr = tCalculator.calculate();
				if (tCalculator.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCalculator.mErrors);
					return tBonusMakeDate;
				}

				if (tStr != null && !tStr.trim().equals("")) {
					try {
						tBonusMakeDate = tStr.substring(0, 10);// 返回结果中含有时间，只取日期
					} catch (Exception e) {
						mErrors.addOneError(new CError("红利公布日计算结果错误!" + "错误结果："
								+ tStr));
						return "";
					}
				}

				logger.debug("Cal Result: BonusMakeDate of " + tPolNo
						+ " =" + tBonusMakeDate);
			}
		}
		return tBonusMakeDate;
	}

	public String getPolNo() {
		return this.mPolNo;
	}

	public void setPolNo(String tPolNo) {
		this.mPolNo = tPolNo;
	}

	public String getForceDVFlag() {
		return this.mForceDVFlag;
	}

	public void setForceDVFlag(String tForceDVFlag) {
		
		this.mForceDVFlag = tForceDVFlag;
	}

	public String getEdorValiDate() {
		return this.mEdorValiDate;
	}

	public void setEdorValiDate(String tEdorValiDate) {
		this.mEdorValiDate = tEdorValiDate;
	}

	public void setADispatchDate(String tADispatchDate) {
		this.mADispatchDate = tADispatchDate;
	}

	public void setIsSubmit(boolean tIsSubmit) {
		this.mIsSubmit = tIsSubmit;
	}
	public void setLPEdorItemSchema(LPEdorItemSchema tLPEdorItemSchema) {
		this.mLPEdorItemSchema = tLPEdorItemSchema;
	}

	public void setDispatchType(String tDispatchType) {
		this.mDispatchType = tDispatchType;
	}

	public void setCFFlag(String tCFFlag) {
		if (tCFFlag != null && tCFFlag.equals("CF")) {
			mCFFlag = true;
		} else {
			mCFFlag = false;
		}
	}

	public void setNextYearFlag(boolean bNextYearFlag) {
		mNextYearFlag = bNextYearFlag;
	}

	public String getDispatchType() {
		return this.mDispatchType;
	}

	public String getCoValiDate() {
		return this.mCoValiDate;
	}

	public void clear() {
		this.mPolNo = "";
		this.mContNo = "";
		this.mGrpContNo = "";
		this.mForceDVFlag = "0";
		this.mDispatchType = "0";
		this.mValiDate = "";
		this.mAmnt = 0;
		this.mEdorValiDate = mCurrentDate;
		this.mPayEndYear = 0;
		this.mBonusAmnt = 0;
		this.mAvailableAmnt = 0;
		this.mManageCom = "";
		this.mAgentCode = "";
		this.mBonusMakeDate = "";
		this.mManageCom = "";
		this.mOperator = "000";
		this.mAgentCode = "";
		this.mCom = "86";
		this.mInterval = 0;
		this.mCoValiDate = "";
	}

	public VData getResult() {
		return mResult;
	}

	public void setBonusAmnt(double tBonusAmnt) {
		this.mBonusAmnt = tBonusAmnt;
	}

	public double getBonusAmnt() {
		return mBonusAmnt;
	}

	public void setAvailableAmnt(double tAvailableAmnt) {
		this.mAvailableAmnt = tAvailableAmnt;
	}

	public double getAvailableAmnt() {
		return mAvailableAmnt;
	}

	// 计算红利应该分配日期
	private String calSDispatchDate(String tBonusMakeDate, String tCoValiDate) {
		String mSDispatchDate = "";// 红利应该分配日期

		if (tBonusMakeDate != null && !tBonusMakeDate.trim().equals("")
				&& tCoValiDate != null && !tCoValiDate.trim().equals("")) {
			FDate fDate = new FDate();
			if (fDate.getDate(tBonusMakeDate)
					.before(fDate.getDate(tCoValiDate))) {
				mSDispatchDate = tCoValiDate;
			} else {
				mSDispatchDate = tBonusMakeDate;
			}
		}
		return mSDispatchDate;
	}

	/**
	 * 基本校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		if (mPolNo == null || mPolNo.trim().equals("")) {
			mErrors.addOneError(new CError("险种号不能为空！"));
			return false;
		}
		// ExeSQL q_exesql = new ExeSQL();
		// SSRS tssrs = new SSRS();
		// String strSql = " select cvalidate, amnt, payendyear, contno, grpcontno, " +
		// " managecom, agentcode, payintv, riskcode from lcpol " +
		// " where polno = '" + mPolNo + "'";
		// tssrs = q_exesql.execSQL(strSql);

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError() || tLCPolSet == null
				|| tLCPolSet.size() < 1) {
			CError.buildErr(this, "保单信息查询失败!");
		}
		mLCPolSchema = tLCPolSet.get(1);

		this.mValiDate = mLCPolSchema.getCValiDate();
		this.mAmnt = mLCPolSchema.getAmnt();
		this.mPayEndYear = mLCPolSchema.getPayEndYear();
		this.mContNo = mLCPolSchema.getContNo();
		this.mGrpContNo = mLCPolSchema.getGrpContNo();
		this.mManageCom = mLCPolSchema.getManageCom();
		this.mAgentCode = mLCPolSchema.getAgentCode();
		mPayIntv = mLCPolSchema.getPayIntv();
		mRiskCode = mLCPolSchema.getRiskCode();

		// 保单年度计算类型 0--舍弃法(向下撇) 1--约进法(向上撇)
		String calYearsType = "0";
		if (calYearsType.equals("0")) // 0--舍弃法(向下撇)
		{
			mInterval = PubFun.calInterval(mValiDate, mEdorValiDate, "Y");
		} else if (calYearsType.equals("1")) // 1--约进法(向上撇)
		{
			mInterval = PubFun.calInterval2(mValiDate, mEdorValiDate, "Y");
		}

		if (mInterval < 1 && !mForceDVFlag.trim().equals("1")) {
			logger.debug(mPolNo + "未满一个保单年度，不能参与分红。保单年度：" + mInterval);
			mErrors.addOneError(new CError(mPolNo + "未满一个保单年度，不能参与分红"));
			return false;
		}

		curFiscalYear = Integer.parseInt(mEdorValiDate.substring(0, 4));
		// 计算生效对应日
		if (mValiDate != null && !mValiDate.trim().equals("")) {
			mCoValiDate = calDate(curFiscalYear, mValiDate);
		} else {
			mErrors.addOneError(new CError(mPolNo + "生效日期为空！"));
			return false;
		}

		// 得到红利公布日
		mBonusMakeDate = getBonusMakeDate();

		if (this.mForceDVFlag.trim().equals("1")) // 强制分红
		{
			int iIntv = 0;
			if (mBonusMakeDate == null || mBonusMakeDate.trim().equals("")) {
				iIntv = -1; // 红利公布日为空,默认为未到红利公布日
			} else {
				int iIntv2 = PubFun
						.calInterval(mCoValiDate, mEdorValiDate, "D");
				int iIntv1 = PubFun.calInterval(mBonusMakeDate, mEdorValiDate,
						"D");
				iIntv = Math.min(iIntv1, iIntv2); // 既要过红利公布日又要过保单生效对应日
			}
			if (iIntv >= 0) {
				preFiscalYear = curFiscalYear;
			} else {
				preFiscalYear = curFiscalYear - 1;
			}
		} else {
			preFiscalYear = curFiscalYear - 1;
		}

		mLastBonusCValiDate = calDate(preFiscalYear, mValiDate);
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		String strSql = "select adispatchdate, BonusMakeDate from loengbonuspol where polno = '"
				+ "?mPolNo?" + "' and fiscalyear = " + "?preFiscalYear?";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("mPolNo", mPolNo);
		sqlbv.put("preFiscalYear", preFiscalYear);
		tssrs = q_exesql.execSQL(sqlbv);
		if (tssrs != null && tssrs.getMaxRow() > 0) {
			if (mForceDVFlag.trim().equals("1")) {
				// 强制分红继续强制分红
			} else {
				logger.debug("保单" + mPolNo + "已于" + tssrs.GetText(1, 1)
						+ "进行过" + preFiscalYear + "会计年度的红利分配,不能再进行分红！");
				mErrors.addOneError(new CError("保单" + mPolNo + "已于"
						+ tssrs.GetText(1, 1) + "进行过" + preFiscalYear
						+ "会计年度的红利分配,不能再进行分红！"));
				return false;
			}
		}

		logger.debug("EdorValiDate:" + mEdorValiDate);

		return true;
	}

	/**
	 * 判断是否可以分红
	 * 
	 * @return boolean
	 */
	private boolean canDispatch() {
		if (mForceDVFlag.trim().equals("0")) {
			// 对于正常或补派分红，先去补派以前遗漏的分红，不管今年能不能分
			int startFiscalYear = 0;
			FDate mFDate = new FDate();
			GregorianCalendar mCalendar = new GregorianCalendar();
			mCalendar.setTime(mFDate.getDate(mValiDate));
			startFiscalYear = mCalendar.get(Calendar.YEAR); // 得到该保单最早应该分红的会计年度
			DispatchBonusBL mDispatchBonusBL;
			String patchDate = "";
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			if (preFiscalYear > startFiscalYear) {
				String strSql = "";
				
				for (int i = startFiscalYear; i <= preFiscalYear - 1; i++) {
					strSql = " select availableamnt from loengbonuspol "
							+ " where polno = '" + "?mPolNo?"
							+ "' and fiscalyear = " + "?i?";
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					sqlbv.sql(strSql);
					sqlbv.put("mPolNo", mPolNo);
					sqlbv.put("i", i);
					tssrs = q_exesql.execSQL(sqlbv);
					if (tssrs == null || tssrs.getMaxRow() < 1) {
						if (hasFiscalyear(i)) {
							continue;
						}
						patchDate = String.valueOf(i + 1) + "-12-31";
						mDispatchBonusBL = new DispatchBonusBL();
						mDispatchBonusBL.setPolNo(mPolNo);
						mDispatchBonusBL.setADispatchDate(mEdorValiDate);
						mDispatchBonusBL.setEdorValiDate(patchDate);
						mDispatchBonusBL.setDispatchType("1");
						mDispatchBonusBL.setIsSubmit(this.mIsSubmit);
						mDispatchBonusBL.setNextYearFlag(mNextYearFlag);
						LOEngBonusPolSet allLOEngBonusPolSet = new LOEngBonusPolSet();
						allLOEngBonusPolSet.add(mINLOEngBonusPolSet);
						allLOEngBonusPolSet.add(mLOEngBonusPolSet);
						mDispatchBonusBL
								.setLOEngBonusPolSet(allLOEngBonusPolSet);
						if (mDispatchBonusBL.dispatchBonus()) {
							logger.debug("第" + i + "年度补派分红成功！");
							if (!this.mIsSubmit) {
								mLOEngBonusPolSet.add(mDispatchBonusBL
										.getLOEngBonusPolSet());
								mLOPRTManagerSet.add(mDispatchBonusBL
										.getLOPRTManagerSet());
								mLJABonusGetSet.add(mDispatchBonusBL
										.getLJABonusGetSet());
								mLJAGetSet.add(mDispatchBonusBL.getLJAGetSet());
							}
						} else {
							logger.debug("第"
											+ i
											+ "年度补派红利分配失败："
											+ mDispatchBonusBL.mErrors
													.getError(0).errorMessage);
							mErrors.copyAllErrors(mDispatchBonusBL.mErrors);
							return false;
						}
					}
				}
			}

			if (!mNextYearFlag) // 计算下一年度红利不用判断是否已公布红利
			{
				if (mBonusMakeDate == null || mBonusMakeDate.trim().equals("")) {
					CError.buildErr(this, "红利公布日为空");
					return false;
				}

				logger.debug(mPolNo + "已过" + mInterval + "个保单年度");
				if (mFDate.getDate(mBonusMakeDate).after(
						mFDate.getDate(mEdorValiDate))) {
					CError.buildErr(this, "未到红利公布日");
					return false;
				} else {
					if (mCoValiDate == null || mCoValiDate.trim().equals("")) {
						CError.buildErr(this, mPolNo + "生效对应日计算失败!");
						return false;
					}
					if (mFDate.getDate(mCoValiDate).after(
							mFDate.getDate(mEdorValiDate))) {
						CError.buildErr(this, "未到生效对应日" + mCoValiDate);
						return false;
					}
					logger.debug(mPolNo + "已到生效对应日:" + mCoValiDate
							+ "，将开始分红........");
				}
			}
		}

		return true;
	}

	private boolean isLeap(String mYear) {
		int tYear = Integer.parseInt(mYear);
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}

	private boolean isLeap(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}

	private String getPreEdorValiDate(String tEdorValiDate) {
		String preEdorValiDate = "";
		if (tEdorValiDate != null && !tEdorValiDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tEdorValiDate));
			int tYear = tCalendar.get(Calendar.YEAR) - 1;
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 如果tEdorValiDate是2月29日，而其上一年不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 3;
				tDay = 1;
			}
			preEdorValiDate = String.valueOf(tYear) + "-"
					+ String.valueOf(tMonth) + "-" + String.valueOf(tDay);
		}

		return preEdorValiDate;
	}

	// 得到tDate在tYear这一年的对应日
	private String calDate(int tYear, String tDate) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 如果tEdorValiDate是2月29日，而其上一年不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 3;
				tDay = 1;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}

		return coDate;
	}

	// 得到终了分红率
	public double getTBRate(String tDate) {
		double tTBRate = 0;
		String tPolNo = this.mPolNo;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (tPolNo != null && !tPolNo.trim().equals("")) {
			String strSql = "select distinct(a.calcode) from lmedorcal a,lcpol b where a.riskcode = b.riskcode "
					+ "and a.riskver = b.riskversion and b.polno = '"
					+ "?tPolNo?"
					+ "' and a.caltype = 'TBRate' ";
			sqlbv.sql(strSql);
			sqlbv.put("tPolNo", tPolNo);
			ExeSQL q_exesql = new ExeSQL();
			SSRS tssrs = new SSRS();
			tssrs = q_exesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() >= 1) {
				String sForceDVFlag = mForceDVFlag;
				if (mNextYearFlag) // 计算下一年度分红标志
				{
					sForceDVFlag = "1";
				}
				String mCalCode = tssrs.GetText(1, 1);
				mBqCalBase.setInterval(mInterval);
				mBqCalBase.setEdorValiDate(tDate);
				mBqCalBase.setPayEndYear(mPayEndYear);
				mBqCalBase.setForceDVFlag(sForceDVFlag);
				mBqCalBase.setPayIntv(mPayIntv);
				mBqCalBase.setPolNo(tPolNo);
				mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
				mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				Calculator tCalculator = new Calculator();
				tCalculator.setBOMList(mBomList);
				tCalculator.setCalCode(mCalCode);
				tCalculator.addBasicFactor("Interval", // 保单年度
						String.valueOf(mInterval));
				tCalculator.addBasicFactor("EdorValiDate", tDate);
				tCalculator.addBasicFactor("PayEndYear", String
						.valueOf(mPayEndYear));
				tCalculator.addBasicFactor("ForceDVFlag", sForceDVFlag);
				tCalculator.addBasicFactor("PayIntv", String.valueOf(mPayIntv));
				tCalculator.addBasicFactor("PolNo", tPolNo);
				String tStr = tCalculator.calculate();
				if (tCalculator.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCalculator.mErrors);
					return tTBRate;
				}

				if (tStr != null && !tStr.trim().equals("")) {
					try {
						tTBRate = Double.parseDouble(tStr);
					} catch (Exception e) {
						mErrors.addOneError(new CError("终了分红率计算结果错误!" + "错误结果："
								+ tStr));
						return -1;
					}
				}

				logger.debug("Cal Result: TBRate of " + tPolNo + " ="
						+ tTBRate);
			}
		}
		return tTBRate;
	}

	private void setSchemas() {
		// 计算自红利公布日后，至下一红利公布日之前，该保单适用的终了分红率
		String afterBonusMakeDate = "";
		double tTBRate = 0.0;
		if (mBonusMakeDate == null || mBonusMakeDate.trim().equals("")) {
			afterBonusMakeDate = "";
		} else {
			afterBonusMakeDate = PubFun.calDate(mBonusMakeDate, 1, "D", null);
			tTBRate = getTBRate(afterBonusMakeDate);
		}

		LOEngBonusPolSchema mLOEngBonusPolSchema = new LOEngBonusPolSchema();

		mLOEngBonusPolSchema.setPolNo(mPolNo);
		mLOEngBonusPolSchema.setFiscalYear(preFiscalYear);
		mLOEngBonusPolSchema.setContNo(mContNo);
		mLOEngBonusPolSchema.setGrpContNo(mGrpContNo);
		mLOEngBonusPolSchema.setBonusRate(mBonusRate);
		mLOEngBonusPolSchema.setTerminalBonusRate(tTBRate);
		mLOEngBonusPolSchema.setBaseAmnt(mAmnt);
		mLOEngBonusPolSchema.setBonusAmnt(mBonusAmnt);
		mLOEngBonusPolSchema.setAvailableAmnt(mAvailableAmnt);
		mLOEngBonusPolSchema.setBonusMakeDate(mBonusMakeDate);
		mLOEngBonusPolSchema.setSDispatchDate(mCoValiDate); // 生效对应日
		mLOEngBonusPolSchema.setADispatchDate(mADispatchDate);
		mLOEngBonusPolSchema.setDispatchType(mDispatchType);
		if (this.mForceDVFlag.trim().equals("1")) {
			mLOEngBonusPolSchema.setDispatchType("2"); // 2-强制分红
		}
		mLOEngBonusPolSchema.setOperator(mOperator);
		mLOEngBonusPolSchema.setMakeDate(mCurrentDate);
		mLOEngBonusPolSchema.setMakeTime(mCurrentTime);
		mLOEngBonusPolSchema.setModifyDate(mCurrentDate);
		mLOEngBonusPolSchema.setModifyTime(mCurrentTime);
		mLOEngBonusPolSchema.setGetFlag("0");
		mLOEngBonusPolSet.add(mLOEngBonusPolSchema);
		// 准备分红业绩报告书数据
		if (this.mForceDVFlag.trim().equals("0")) {
			String mLimit = PubFun.getNoLimit(mCom);
			String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
			LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
			mLOPRTManagerSchema.setPrtSeq(serNo);
			mLOPRTManagerSchema.setOtherNo(mPolNo);
			mLOPRTManagerSchema.setOtherNoType("00"); // 个人保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_BONUSPAY); // 个人红利派发通知书
			mLOPRTManagerSchema.setManageCom(mManageCom);
			mLOPRTManagerSchema.setAgentCode(mAgentCode);
			mLOPRTManagerSchema.setReqCom(mCom);
			mLOPRTManagerSchema.setReqOperator(mOperator);
			mLOPRTManagerSchema.setPrtType("0"); // 前台打印
			mLOPRTManagerSchema.setStateFlag("0"); // 提交打印
			mLOPRTManagerSchema.setMakeDate(mCurrentDate);
			mLOPRTManagerSchema.setMakeTime(mCurrentTime);
			mLOPRTManagerSchema.setStandbyFlag1(String.valueOf(preFiscalYear));
			mLOPRTManagerSchema.setStandbyFlag2(mCoValiDate); // 生效对应日
			mLOPRTManagerSet.add(mLOPRTManagerSchema);
			// ===add===========liuxiaosong=========2007-1-4=====团体分红业绩报告书/人名清单打印记录==========start=================
			// 团单只发一条分红业绩报告书记录
			if (!"00000000000000000000".equals(mGrpContNo)) {
				String tSQL = " select count(*) from loprtmanager where code='34' and standbyflag1 = '"
						+ "?preFiscalYear?"
						+ "' and otherno = '"
						+ "?mGrpContNo?"
						+ "'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSQL);
				sqlbv.put("preFiscalYear", preFiscalYear);
				sqlbv.put("mGrpContNo", mGrpContNo);
				ExeSQL tExeSQL = new ExeSQL();
				int count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
				if (count == 0) {
					// 若有记录则不必再生成
					String tLimit = PubFun.getNoLimit(mCom);
					String tSerNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
					LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
					tLOPRTManagerSchema.setPrtSeq(tSerNo);
					tLOPRTManagerSchema.setOtherNo(mGrpContNo);
					tLOPRTManagerSchema.setOtherNoType("01"); // 团单号
					tLOPRTManagerSchema
							.setCode(PrintManagerBL.CODE_GRPBONUSPAY); // code=34
					// 团体红利派发通知书
					tLOPRTManagerSchema.setManageCom(mManageCom);
					tLOPRTManagerSchema.setAgentCode(mAgentCode);
					tLOPRTManagerSchema.setReqCom(mCom);
					tLOPRTManagerSchema.setReqOperator(mOperator);
					tLOPRTManagerSchema.setPrtType("0"); // 前台打印
					tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
					tLOPRTManagerSchema.setMakeDate(mCurrentDate);
					tLOPRTManagerSchema.setMakeTime(mCurrentTime);
					tLOPRTManagerSchema.setStandbyFlag1(String
							.valueOf(preFiscalYear));// 会计年度
					tLOPRTManagerSchema.setStandbyFlag2(mCoValiDate); // 生效对应日
					tLOPRTManagerSchema.setStandbyFlag3("bqnotice");
					mLOPRTManagerSet.add(tLOPRTManagerSchema);
					// 人名清单打印记录
					String tLimit2 = PubFun.getNoLimit(mCom);
					String tSerNo2 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit2);
					LOPRTManagerSchema rLOPRTManagerSchema = new LOPRTManagerSchema();
					rLOPRTManagerSchema.setPrtSeq(tSerNo2);
					rLOPRTManagerSchema.setOtherNo(mGrpContNo);
					rLOPRTManagerSchema.setOtherNoType("01"); // 团单号
					rLOPRTManagerSchema
							.setCode(PrintManagerBL.CODE_GRPBONUSPAYBILL); // code=35
					// 红利业绩通知书人名清单
					rLOPRTManagerSchema.setManageCom(mManageCom);
					rLOPRTManagerSchema.setAgentCode(mAgentCode);
					rLOPRTManagerSchema.setReqCom(mCom);
					rLOPRTManagerSchema.setReqOperator(mOperator);
					rLOPRTManagerSchema.setPrtType("0"); // 前台打印
					rLOPRTManagerSchema.setStateFlag("0"); // 提交打印
					rLOPRTManagerSchema.setMakeDate(mCurrentDate);
					rLOPRTManagerSchema.setMakeTime(mCurrentTime);
					rLOPRTManagerSchema.setStandbyFlag1(String
							.valueOf(preFiscalYear));// 会计年度
					rLOPRTManagerSchema.setStandbyFlag2(mCoValiDate); // 生效对应日
					rLOPRTManagerSchema.setStandbyFlag3("bqnotice");
					mLOPRTManagerSet.add(rLOPRTManagerSchema);
				}

			}
			// ===add===========liuxiaosong=========2007-1-4=====团体分红业绩报告书打印记录==========END=================
			// ===add===zhangtao===2006-08-30====判断如果红利处理方式为领取，则生成红利领取记录，并且将分红记录置为已领取===BGN===
			String sql = " select * from lmriskapp where riskcode = (select riskcode from lcpol where polno = '"
					+ "?mPolNo?" + "')";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("mPolNo", mPolNo);
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.executeQuery(sqlbv);
			if (tLMRiskAppDB.mErrors.needDealError() || tLMRiskAppSet == null
					|| tLMRiskAppSet.size() < 1) {
				CError.buildErr(this, "红利处理方式查询失败!");
				return;
			}
			String sBonusMode = tLMRiskAppSet.get(1).getBonusMode();
			if (sBonusMode != null && sBonusMode.trim().equals("1")) // 红利处理方式为现金领取
			{
				// 判断如果本期可以领取，则将之前所有未领取的分红记录全部生成相应的红利领取记录
				String sCanGetFlag = "0";
				LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
				tLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
				tLMEdorCalDB.setCalType("BonusGet");
				LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query();
				if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
					sCanGetFlag = "0";// 没有红利领取算法，自然不能领取
				} else {
					String sCalCode = tLMEdorCalSet.get(1).getCalCode();
					if (sCalCode == null || sCalCode.trim().equals("")) {
						sCanGetFlag = "0";// 没有红利领取算法，自然不能领取
					} else {
						Calculator tCalculator = new Calculator();
						tCalculator.setCalCode(sCalCode);
						tCalculator.addBasicFactor("Interval", String
								.valueOf(mInterval));
						mBqCalBase.setInterval(mInterval);
						mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
						mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
						tCalculator.setBOMList(mBomList);
						sCanGetFlag = tCalculator.calculate();
						if (sCanGetFlag == null
								|| sCanGetFlag.trim().equals("")) {
							sCanGetFlag = "0";
						}
					}
				}
				if (sCanGetFlag.equals("1"))// 结果返回为1代表本期可以领取红利
				{
					// 计算累计红利保额现金价值
					LOEngBonusPolDB tLOEngBonusPolDB = new LOEngBonusPolDB();
					tLOEngBonusPolDB.setPolNo(mPolNo);
					tLOEngBonusPolDB.setGetFlag("0"); // 领取标志为未领取
					LOEngBonusPolSet tLOEngBonusPolSet = tLOEngBonusPolDB
							.query();
					if (tLOEngBonusPolDB.mErrors.needDealError()) {
						CError.buildErr(this, "未领取分红记录查询失败!");
						return;
					}

					for (int i = 1; i <= mLOEngBonusPolSet.size(); i++) {
						if (mLOEngBonusPolSet.get(i).getGetFlag() == null
								|| mLOEngBonusPolSet.get(i).getGetFlag()
										.equals("0"))
							; // 领取标志为未领取
						{
							mLOEngBonusPolSet.get(i).setGetFlag("1"); // 置为已领取
							tLOEngBonusPolSet.add(mLOEngBonusPolSet.get(i));
						}
					}
					for (int i = 1; i <= mINLOEngBonusPolSet.size(); i++) {
						if (mINLOEngBonusPolSet.get(i).getGetFlag() == null
								|| mINLOEngBonusPolSet.get(i).getGetFlag()
										.equals("0"))
							; // 领取标志为未领取
						{
							mINLOEngBonusPolSet.get(i).setGetFlag("1"); // 置为已领取
							tLOEngBonusPolSet.add(mINLOEngBonusPolSet.get(i));
						}
					}
					double dSumGetMoney = 0.0;
					   //营改增 add zhangyingfeng 2016-07-14
			          //计算税额 净额 税率(考虑业务不同税率不同，暂取一条)
					double dSumNetAm=0.0;
					double dSumTaxAm=0.0;
					double dSumTax=0.0;
			          //end zhangyingfeng 2016-07-14
					String sNoLimit = PubFun.getNoLimit(mManageCom);
					String sGetNoticeNo = PubFun1.CreateMaxNo("GetNoticeNo",
							sNoLimit);
					String sActuGetNo = PubFun1.CreateMaxNo("GETNO", sNoLimit);
					String sSerialNo = PubFun1
							.CreateMaxNo("SERIALNO", sNoLimit);

					LDCode1DB tLDCode1DB = new LDCode1DB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					tLDCode1DB.setCodeType("getform");
					tLDCode1DB.setCodeName("FK");
					tLDCode1DB.setCode(mLCPolSchema.getGetForm());
					tLDCode1Set = tLDCode1DB.query();
					if (tLDCode1DB.mErrors.needDealError()
							|| tLDCode1Set == null || tLDCode1Set.size() < 1) {
						CError.buildErr(this, "领取方式查询失败!");
						return;
					}
					String sPayMode = tLDCode1Set.get(1).getCode1();
					for (int i = 1; i <= tLOEngBonusPolSet.size(); i++) {
						// 逐条计算红利保额现价
						tLOEngBonusPolSet.get(i).setGetFlag("1"); // 将本次领取的分红记录领取标志置为1-已领取
						tLOEngBonusPolSet.get(i).setModifyDate(mCurrentDate);
						tLOEngBonusPolSet.get(i).setModifyTime(mCurrentTime);
						mGetLOEngBonusPolSet.add(tLOEngBonusPolSet.get(i));

						GlobalInput tGlobalInput = new GlobalInput();
						tGlobalInput.Operator = "000";
						tGlobalInput.ManageCom = "86";
						EdorCalZT tEdorCalZT = new EdorCalZT(tGlobalInput);
						tEdorCalZT.setInBonusAmnt(tLOEngBonusPolSet.get(i)
								.getBonusAmnt());
						double dBonusCashValue = tEdorCalZT.getBonusCashValue(
								mPolNo, mCoValiDate); // 本次分红对应生效对应日
						if (dBonusCashValue == -1) {
							CError.buildErr(this, "红利现价计算失败!");
							return;
						}

						// 生成红利领取记录
						LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
						tLJABonusGetSchema.setGetNoticeNo(sGetNoticeNo);
						tLJABonusGetSchema.setActuGetNo(sActuGetNo);
						tLJABonusGetSchema.setOtherNo(mPolNo);
						tLJABonusGetSchema.setOtherNoType("3");
						tLJABonusGetSchema.setBonusYear(String
								.valueOf(tLOEngBonusPolSet.get(i)
										.getFiscalYear()));
						tLJABonusGetSchema.setPayMode(sPayMode);
						tLJABonusGetSchema.setGetMoney(dBonusCashValue); // 领取金额为红利保额现金价值
						tLJABonusGetSchema.setGetDate(mCoValiDate);
						// tLJABonusGetSchema.setEnterAccDate("");
						// tLJABonusGetSchema.setConfDate(mCurrentDate);
						tLJABonusGetSchema.setManageCom(mManageCom);
						tLJABonusGetSchema.setAgentCode(mAgentCode);
						tLJABonusGetSchema.setAgentCom(mLCPolSchema
								.getAgentCom());
						tLJABonusGetSchema.setAgentGroup(mLCPolSchema
								.getAgentGroup());
						tLJABonusGetSchema.setAgentType(mLCPolSchema
								.getAgentType());
						tLJABonusGetSchema.setFeeOperationType("TF");
						tLJABonusGetSchema.setFeeFinaType("YF"); // 财务科目算生存领取
						tLJABonusGetSchema.setState("0");
						tLJABonusGetSchema.setSerialNo(sSerialNo);
						tLJABonusGetSchema.setOperator(mOperator);
						tLJABonusGetSchema.setMakeDate(mCurrentDate);
						tLJABonusGetSchema.setMakeTime(mCurrentTime);
						tLJABonusGetSchema.setModifyDate(mCurrentDate);
						tLJABonusGetSchema.setModifyTime(mCurrentTime);
				          //营改增 add zhangyingfeng 2016-07-14
				          //价税分离 计算器
				          TaxCalculator.calBySchema(tLJABonusGetSchema);
				          //end zhangyingfeng 2016-07-14
						mLJABonusGetSet.add(tLJABonusGetSchema);
						dSumGetMoney += dBonusCashValue;
						   //营改增 add zhangyingfeng 2016-07-14
				          //计算税额 净额 税率
						dSumNetAm+=tLJABonusGetSchema.getNetAmount();
						dSumTaxAm+=tLJABonusGetSchema.getTaxAmount();
						dSumTax=tLJABonusGetSchema.getTax();
				          //end zhangyingfeng 2016-07-14
					}
					// 生成领取总表记录
					LJAGetSchema tLJAGetSchema = new LJAGetSchema();
					tLJAGetSchema.setActuGetNo(sActuGetNo);
					tLJAGetSchema.setGetNoticeNo(sGetNoticeNo);
					tLJAGetSchema.setSerialNo(sSerialNo);
					tLJAGetSchema.setOtherNo(mLCPolSchema.getContNo());
					tLJAGetSchema.setOtherNoType("2");
					tLJAGetSchema.setPayMode(sPayMode);
					tLJAGetSchema.setBankOnTheWayFlag("0");
					tLJAGetSchema.setSendBankCount(0);
					tLJAGetSchema.setAgentCode(mLCPolSchema.getAgentCode());
					tLJAGetSchema.setAgentCom(mLCPolSchema.getAgentCom());
					tLJAGetSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
					tLJAGetSchema.setAgentType(mLCPolSchema.getAgentType());
					tLJAGetSchema.setManageCom(mLCPolSchema.getManageCom());
					tLJAGetSchema.setAccName(mLCPolSchema.getGetAccName());
					tLJAGetSchema.setBankAccNo(mLCPolSchema.getGetBankAccNo());
					tLJAGetSchema.setBankCode(mLCPolSchema.getGetBankCode());
					tLJAGetSchema.setStartGetDate(mCurrentDate);
					tLJAGetSchema.setSumGetMoney(dSumGetMoney);
					   //营改增 add zhangyingfeng 2016-07-14
			          //计算税额 净额 税率
					tLJAGetSchema.setNetAmount(dSumNetAm);
					tLJAGetSchema.setTaxAmount(dSumTaxAm);
					tLJAGetSchema.setTax(dSumTax);
			          //end zhangyingfeng 2016-07-14
					tLJAGetSchema.setSaleChnl(mLCPolSchema.getSaleChnl());
					tLJAGetSchema.setShouldDate(mCoValiDate);
					tLJAGetSchema.setAppntNo(mLCPolSchema.getAppntNo());
					// tLJAGetSchema.setEnterAccDate();
					// tLJAGetSchema.setConfDate(mCurrentDate);
					tLJAGetSchema.setApproveCode(mLCPolSchema.getApproveCode());
					tLJAGetSchema.setApproveDate(mLCPolSchema.getApproveDate());
					// tLJAGetSchema.setDrawer(mLCPolSchema.getInsuredName());
					// tLJAGetSchema.setDrawerID();
					tLJAGetSchema.setOperator(mOperator);
					tLJAGetSchema.setMakeDate(mCurrentDate);
					tLJAGetSchema.setMakeTime(mCurrentTime);
					tLJAGetSchema.setModifyDate(mCurrentDate);
					tLJAGetSchema.setModifyTime(mCurrentTime);
					mLJAGetSet.add(tLJAGetSchema);
				}
			}
			// ===add===zhangtao===2006-08-30====判断如果红利处理方式为领取，则生成红利领取记录，并且将分红记录置为已领取===END===
		}
	}

	public boolean callPatchBonus(LCPolSchema tLCPolSchema, String tREDate) {
		String tPolNo = tLCPolSchema.getPolNo();
		String tValiDate = tLCPolSchema.getCValiDate();
		int startYear = getYear(tValiDate);
		int endYear = getYear(tREDate) - 1;
		String patchDate = "";
		String strSql = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		DispatchBonusBL mDispatchBonusBL;
		for (int i = startYear; i <= endYear; i++) {
			strSql = "select availableamnt from loengbonuspol where polno = '"
					+ "?tPolNo?" + "' and fiscalyear = " + "?i?";
			sqlbv.sql(strSql);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("i", i);
			tssrs = q_exesql.execSQL(sqlbv);
			if (tssrs == null || tssrs.getMaxRow() < 1) {
				if (i == endYear) {
					patchDate = tREDate;
				} else {
					patchDate = String.valueOf(i + 1) + "-12-31";
				}
				mDispatchBonusBL = new DispatchBonusBL();
				mDispatchBonusBL.setPolNo(tPolNo);
				mDispatchBonusBL.setADispatchDate(tREDate);
				mDispatchBonusBL.setEdorValiDate(patchDate);
				mDispatchBonusBL.setDispatchType("1");
				mDispatchBonusBL.setNextYearFlag(mNextYearFlag);
				if (!mDispatchBonusBL.dispatchBonus()) {
					continue;
				} else {
					logger.debug("第" + i + "年度补派红利分配成功");
				}

			}
		}
		return true;
	}

	public boolean callPatchBonus(String tPolNo, String tREDate) {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tPolNo);
		if (!tLCPolDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "DispatchBonusPrintBL";
			tError.functionName = "callPatchBonus";
			tError.errorMessage = "无保单数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
		callPatchBonus(tLCPolSchema, tREDate);
		return true;
	}

	private int getYear(String tDate) {
		int tYear = 0;
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			tYear = tCalendar.get(Calendar.YEAR);
		}
		return tYear;
	}

	/**
	 * 校验是否可以分红（某些险种进入领取期后不允许分红）
	 * 
	 * @param sPolNo
	 * @param sRiskCode
	 * @param sCurValidate
	 * @return boolean
	 */
	public String checkStartGet(String sPolNo, String sRiskCode,
			String sCurValidate) {
		String sql = " select * from lmcheckfield where fieldname = 'Bonus' "
				+ " and riskcode = '" + "?sRiskCode?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sRiskCode", sRiskCode);
		LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
		LMCheckFieldSet tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv);
		if (tLMCheckFieldDB.mErrors.needDealError()) {
			CError.buildErr(this, "领取期分红校验规则查询失败!");
			return null;
		}
		if (tLMCheckFieldSet == null || tLMCheckFieldSet.size() < 1) {
			// 该险种不需要校验领取期分红
			return "N";
		}
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMCheckFieldSet.get(1).getCalCode());
		tCalculator.setBOMList(mBomList);
		tCalculator.addBasicFactor("PolNo", sPolNo);
		tCalculator.addBasicFactor("CURValidate", sCurValidate);
		String sCalResultValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "领取期分红校验规则执行失败!");
			return null;
		}
		if (sCalResultValue != null && sCalResultValue.equals("1")) {
			// 没有进入领取期，可以分红
			return "N";
		} else {
			// 进入领取期，不可以分红
			logger.debug("险种号：" + sPolNo
					+ tLMCheckFieldSet.get(1).getMsg());
			// CError.buildErr(this, tLMCheckFieldSet.get(1).getMsg());
			return "Y";
		}
	}

	/**
	 * 确定强制分红日期（如果领取期不可以分红的，强制分红日期为领取期起期）
	 * 
	 * @param sPolNo
	 * @param sRiskCode
	 * @param sEdorValidate
	 * @return boolean
	 */
	public String getForceDate(String sPolNo, String sRiskCode,
			String sEdorValidate) {
		String sql = " select * from lmcheckfield where fieldname = 'ForceDate' "
				+ " and riskcode = '" + "?sRiskCode?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sRiskCode", sRiskCode);
		LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
		LMCheckFieldSet tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv);
		if (tLMCheckFieldDB.mErrors.needDealError()) {
			CError.buildErr(this, "领取期分红校验规则查询失败!");
			return null;
		}
		if (tLMCheckFieldSet == null || tLMCheckFieldSet.size() < 1) {
			// 该险种不需要校验领取期分红，强制分红日期为传入日期
			return sEdorValidate;
		}
		mBqCalBase.setPolNo(sPolNo);
		mBqCalBase.setCURValidate(sEdorValidate);
		mPrepareBOMBQEdorBL.setBqCalBase(mBqCalBase);
		mBomList=mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);

		Calculator tCalculator = new Calculator();
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(tLMCheckFieldSet.get(1).getCalCode());
		tCalculator.addBasicFactor("PolNo", sPolNo);
		tCalculator.addBasicFactor("CURValidate", sEdorValidate);
		String sForceDate = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "领取期分红校验规则执行失败!");
			return null;
		}
		if (sForceDate == null || sForceDate.equals("")) {
			CError.buildErr(this, "强制分红日期确定失败!");
			return null;
		}
		if (sForceDate.length() > 10) {
			sForceDate = sForceDate.substring(0, 10);
		}
		return sForceDate;
	}

	/**
	 * 返回本次分红记录
	 * 
	 * @return LOEngBonusPolSet
	 */
	public LOEngBonusPolSet getLOEngBonusPolSet() {
		return mLOEngBonusPolSet;
	}

	// ===add===zhangtao===2006-08-30====判断如果红利处理方式为领取，则生成红利领取记录，并且将分红记录置为已领取===BGN===
	/**
	 * 返回本次分红产生的红利领取总表记录
	 * 
	 * @return LJAGetSet
	 */
	public LJAGetSet getLJAGetSet() {
		return mLJAGetSet;
	}

	/**
	 * 返回本次分红产生的红利领取记录
	 * 
	 * @return LJABonusGetSet
	 */
	public LJABonusGetSet getLJABonusGetSet() {
		return mLJABonusGetSet;
	}

	// ===add===zhangtao===2006-08-30====判断如果红利处理方式为领取，则生成红利领取记录，并且将分红记录置为已领取===BGN===
	/**
	 * 传入尚未提交数据库的分红记录
	 * 
	 * @param pLOEngBonusPolSet
	 */
	public void setLOEngBonusPolSet(LOEngBonusPolSet pLOEngBonusPolSet) {
		mINLOEngBonusPolSet.set(pLOEngBonusPolSet);
	}

	/**
	 * 返回本次分红业绩报告书打印记录
	 * 
	 * @return LOEngBonusPolSet
	 */
	public LOPRTManagerSet getLOPRTManagerSet() {
		return mLOPRTManagerSet;
	}

	/**
	 * 判断传入的分红记录里是否包含该年度
	 * 
	 * @param iFiscalyear
	 * @return boolean
	 */
	private boolean hasFiscalyear(int iFiscalyear) {
		if (mINLOEngBonusPolSet == null || mINLOEngBonusPolSet.size() < 1) {
			return false;
		}

		for (int i = 1; i <= mINLOEngBonusPolSet.size(); i++) {
			if (mINLOEngBonusPolSet.get(i).getFiscalYear() == iFiscalyear) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 未提交数据库的分红记录红利求和
	 * 
	 * @return double
	 */
	private double sumBonus() {
		LOEngBonusPolSet allLOEngBonusPolSet = new LOEngBonusPolSet();
		allLOEngBonusPolSet.add(mINLOEngBonusPolSet);
		allLOEngBonusPolSet.add(mLOEngBonusPolSet);
		if (allLOEngBonusPolSet == null || allLOEngBonusPolSet.size() < 1) {
			return 0;
		}

		double dSumBonus = 0.0;
		for (int i = 1; i <= allLOEngBonusPolSet.size(); i++) {
			dSumBonus += allLOEngBonusPolSet.get(i).getBonusAmnt();
		}
		return dSumBonus;
	}

	/**
	 * 
	 * @return double
	 */
	private boolean noBonus(int iFiscalYear, String sDispatchDate) {
		LOEngBonusPolSet allLOEngBonusPolSet = new LOEngBonusPolSet();
		allLOEngBonusPolSet.add(mINLOEngBonusPolSet);
		allLOEngBonusPolSet.add(mLOEngBonusPolSet);
		if (allLOEngBonusPolSet == null || allLOEngBonusPolSet.size() < 1) {
			return true;
		}

		for (int i = 1; i <= allLOEngBonusPolSet.size(); i++) {
			int intv = PubFun.calInterval(allLOEngBonusPolSet.get(i)
					.getSDispatchDate(), sDispatchDate, "D");
			if (allLOEngBonusPolSet.get(i).getFiscalYear() == iFiscalYear
					&& intv >= 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 取出最近的一次分至日期
	 * 
	 * @return double
	 */
	private String getMaxSDispatchdate(String sDispatchDate) {
		LOEngBonusPolSet allLOEngBonusPolSet = new LOEngBonusPolSet();
		allLOEngBonusPolSet.add(mINLOEngBonusPolSet);
		allLOEngBonusPolSet.add(mLOEngBonusPolSet);
		if (allLOEngBonusPolSet == null || allLOEngBonusPolSet.size() < 1) {
			return sDispatchDate;
		}
		int iMaxIntv = 0;
		String sMaxDispatchDate = sDispatchDate;
		for (int i = 1; i <= allLOEngBonusPolSet.size(); i++) {
			int intv = PubFun.calInterval(sDispatchDate, allLOEngBonusPolSet
					.get(i).getSDispatchDate(), "D");
			if (intv > iMaxIntv) {
				iMaxIntv = intv;
				sMaxDispatchDate = allLOEngBonusPolSet.get(i)
						.getSDispatchDate();
			}

		}
		return sMaxDispatchDate;
	}

	/**
	 * 校验是否可以分红（某些险种零头月不允许分红）
	 * 
	 * @param sPolNo
	 * @param sRiskCode
	 * @return boolean
	 */
	public String checkBonusWhole(String sPolNo, String sRiskCode) {
		String sql = " select * from lmcheckfield where fieldname = 'BonusWhole' "
				+ " and riskcode = '" + "?sRiskCode?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sRiskCode", sRiskCode);
		LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
		LMCheckFieldSet tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv);
		if (tLMCheckFieldDB.mErrors.needDealError()) {
			CError.buildErr(this, "零头月分红校验规则查询失败!");
			return "N";
		}
		if (tLMCheckFieldSet == null || tLMCheckFieldSet.size() < 1) {
			// 该险种不需要校验零头月分红
			return "N";
		}
		Calculator tCalculator = new Calculator();
		tCalculator.setBOMList(mBomList);
		tCalculator.setCalCode(tLMCheckFieldSet.get(1).getCalCode());
		tCalculator.addBasicFactor("PolNo", sPolNo);
		String sCalResultValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "零头月分红校验规则执行失败!");
			return "N";
		}
		if (sCalResultValue != null && sCalResultValue.equals("1")) {
			// 该险种零头月不可以分红
			logger.debug("险种号：" + sPolNo
					+ tLMCheckFieldSet.get(1).getMsg());
			// CError.buildErr(this, tLMCheckFieldSet.get(1).getMsg());
			return "Y";
		} else {
			// 该险种零头月可以分红
			return "N";
		}
	}

	public static void main(String[] args) {
		logger.debug("test start:");
		String mCurrentDate = PubFun.getCurrentDate();
		// String mCurrentTime = PubFun.getCurrentTime();
		// logger.debug("current date:"+mCurrentDate);
		//
		//
		//
		// DispatchBonusBL tDispatchBonusBL = new DispatchBonusBL();
		// tDispatchBonusBL.setPolNo("110110000023887");
		// double tr = tDispatchBonusBL.gemBonusRate();
		// logger.debug("tr:"+tr);
		// if (!tDispatchBonusBL.dispatchBonus())
		// {
		// logger.debug(tDispatchBonusBL.mErrors.getError(0).errorMessage);
		// }
		//
		// DispatchBonusBL tDispatchBonusBL = new DispatchBonusBL();
		// double fbonusamnt =
		// tDispatchBonusBL.calBonusAmnt("210110000002424","1",mCurrentDate);
		// logger.debug("f bonus amnt:"+fbonusamnt);
		// tDispatchBonusBL.setPolNo("210110000002236");
		// tDispatchBonusBL.setForceDVFlag("1");
		// tDispatchBonusBL.setEdorValiDate(mCurrentDate);
		// tDispatchBonusBL.setIsSubmit(false); //不提交标志
		// double dAvailableAmnt = 0.0;
		// if (tDispatchBonusBL.dispatchBonus())
		// {
		// dAvailableAmnt += tDispatchBonusBL.getBonusAmnt();
		// logger.debug("amnt:"+dAvailableAmnt);
		// }
		// else
		// {
		// logger.debug("红利分配失败：" +
		// tDispatchBonusBL.mErrors.
		// getError(0).errorMessage);
		// }
		DispatchBonusBL tDispatchBonusBL = new DispatchBonusBL();
		tDispatchBonusBL.setPolNo("210210000001862");
		tDispatchBonusBL.setForceDVFlag("0");
		// tDispatchBonusBL.setDispatchType("0");
		// tDispatchBonusBL.setIsSubmit(false);
		tDispatchBonusBL.setEdorValiDate("2003-03-21");
		tDispatchBonusBL.dispatchBonus();
		// tDispatchBonusBL.callPatchBonus("110110000032982","2005-08-16");
		logger.debug("test end");
	}

}
