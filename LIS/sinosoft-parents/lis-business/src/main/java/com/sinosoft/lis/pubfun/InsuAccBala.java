package com.sinosoft.lis.pubfun;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMAccGuratRateDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMInsuAccRateDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMAccGuratRateSchema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMAccGuratRateSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMInsuAccRateSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
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
 * Title: 万能险保单账户结算
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author zhangtao pst
 * @version 1.0 2.0
 * @CreateDate：2005-07-28 2007-11-13
 */
public class InsuAccBala {
	private static Logger logger = Logger.getLogger(InsuAccBala.class);
	/** 全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 保险帐户号码，通常情况下为险种编码 万能上次 */
	private String mInsuAccNo;

	/** 保单险种编码 */
	private String mRiskCode;

	/** 保单险种号码 */
	private String mPolNo;

	/** 结算日 */
	private String mBalaDate;

	/** 前一个结算日 */
	private String mPreBalaDate;

	/** 保单生效日 */
	private String mCValiDate;

	/** 保单本年对应生效日 */
	private String tCValiDate;

	/** 保单累计借款金额 */
	private double mSumLoanMoney = 0.0;
	/** 保单累计借款利息 */
	private double mSumPreIntrest = 0.0;
	/** 本次结算利率类型 */
	private String mRateIntv = "";
	/** 本次结算利率 */
	private double mRate = 0.0;
	/** 帐户累积金额 */
	private double mAccValue = 0.0;
	/** 本月累积发生的领取 */
	private double mSumGetMoney;

	/** 保存本次结算的所有利息 */
	double tInterest = 0.0;
	/** 月风险保费 */
	private double mRiskFeeAdmin;

	/** 月保单管理费以及风保 */
	private double mAccFeeAdmin;
	/** 持续奖金 */
	double tAddMoney = 0.0;
	/** 保证利差 */
	private double dClassInterest_ADD = 0.0;;
	/** 结算报告书打印记录 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 结算轨迹记录 */
	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

	/** 管理费轨迹记录 */
	private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

	/** 子管理费帐户表 */
	private LCInsureAccClassFeeSet updLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();

	/** 管理费帐户表 */
	private LCInsureAccFeeSet updLCInsureAccFeeSet = new LCInsureAccFeeSet();

	/** 子帐户表 */
	private LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();

	/** 帐户表 */
	private LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();

	private LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();

	private LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();

	private LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
	/** 险种表 */
	private LCPolSchema tLCPolSchema = new LCPolSchema();

	/** 保单状态表 */
	private LCContStateSet mLCContStateSet = new LCContStateSet();

	/** 结算类用于计息 */
	private AccountManage tAccountManage = new AccountManage();
	// 系统当前时间
	private String mCurrentTime = PubFun.getCurrentTime();

	private String mCurrentDate = PubFun.getCurrentDate();

	private Reflections tReflections = new Reflections();

	private LPInsureAccSet bakLPInsureAccSet = new LPInsureAccSet(); // 用做备份的表
	private LPInsureAccClassSet bakLPInsureAccClassSet = new LPInsureAccClassSet(); // 用做备份的表
	private LCInsureAccTraceSet LXLCInsureAccTraceSet = new LCInsureAccTraceSet();

	private MMap map = new MMap();

	public InsuAccBala() {
	}

	public InsuAccBala(String sPolNo, String sInsuAccNo, String sBalaDate) {
		mPolNo = sPolNo;
		mInsuAccNo = sInsuAccNo;
		mBalaDate = sBalaDate;
	}

	/**
	 * submitData
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!getInputData()) {
			return false;
		}

		if (mOperate != null && !mOperate.equals("NonUniversal")) {
			// *******************************************************************
			// mOperate 操作字符串说明：
			// Service - 批处理结算
			// CashValue - 现金价值计算
			// *******************************************************************

			if (mOperate.equals("Service")) {
				if (!getLMInsuAccRate()) // 批处理结算，取出公布的结算利率
				{
					return false;
				}
			} else if (mOperate.equals("CashValue")) {
				if (!getLMAccGuratRate()) // 现金价值计算，取出帐户保证利率
				{
					return false;
				}
			} else // 默认走批处理分支
			{
				if (!getLMInsuAccRate()) // 批处理每月结算，取出当月公布的结算利率
				{
					return false;
				}
			}

			if (!dealData()) {
				return false;
			}

		}
		// add by jiaqiangli 2008-11-14 非万能险账户型结算通用处理 移植生命人寿程序
		else if (mOperate.equals("NonUniversal")) {
			if (!dealDataNonUniversal()) {
				return false;
			}
		}

		if (mOperate.equals("CashValue")) {
			return true; // 不用提交数据库，直接返回
		}

		// add by jiaqiangli 2008-11-14 非万能险账户型结算
		if (mOperate.equals("NonUniversal")) {
			return true; // 不用提交数据库，直接返回
		}
		// add by jiaqiangli 2008-11-14 非万能险账户型结算

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			buildError("InsureAccSubmit", "数据提交失败！");
			return false;
		}

		return true;
	}

	private boolean dealDataNonUniversal() {
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(mPolNo);
		tLCInsureAccDB.setInsuAccNo(mInsuAccNo);
		if (!tLCInsureAccDB.getInfo()) {
			CError.buildErr(this, "险种保单：" + mPolNo + "下没有需要处理账户！" + mInsuAccNo);
			return false;
		}
		LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccDB.getSchema();

		// 先对帐户表进行备份
		BackUpAcc(tLCInsureAccSchema);
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(mPolNo);
		tLCInsureAccClassDB.setInsuAccNo(mInsuAccNo);
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		LCInsureAccClassSet newLCInsureAccClassSet = new LCInsureAccClassSet(); // 存放结算后的class
		for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
			LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet
					.get(i);
			BackUpAccClass(tLCInsureAccClassSchema);
			try {
				LCInsureAccClassSchema newLCInsureAccClassSchema = calAccountCommon(
						tLCInsureAccClassSchema, mBalaDate);
				newLCInsureAccClassSet.add(newLCInsureAccClassSchema);
			} catch (Exception e) {
				CError.buildErr(this, "结息失败！" + e.toString());
				return false;
			}
		}
		if (newLCInsureAccClassSet.size() <= 0) {
			CError.buildErr(this, "没有结息的数据！");
			return false;
		}
		double accmoney = 0;
		for (int i = 1; i <= newLCInsureAccClassSet.size(); i++) {
			accmoney += newLCInsureAccClassSet.get(i).getInsuAccBala();
		}
		// 上次金额 lis5.3中无此字段
		tLCInsureAccSchema.setLastAccBala(tLCInsureAccSchema.getInsuAccBala());

		tLCInsureAccSchema.setInsuAccBala(accmoney);
		tLCInsureAccSchema.setBalaDate(newLCInsureAccClassSet.get(1)
				.getBalaDate());
		// modify by sunsx 2010-02-01
		// tLCInsureAccSchema.setBalaTime(mCurrentTime);
		tLCInsureAccSchema.setBalaTime("00:00:00");
		tLCInsureAccSchema.setModifyDate(mCurrentDate);
		tLCInsureAccSchema.setModifyTime(mCurrentTime);

		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		tLCInsureAccSet.add(tLCInsureAccSchema);
		mResult.add(tLCInsureAccSet);
		mResult.add(newLCInsureAccClassSet);
		mResult.add(LXLCInsureAccTraceSet);

		map.put(tLCInsureAccSchema, "UPDATE");
		map.put(newLCInsureAccClassSet, "UPDATE");
		map.put(LXLCInsureAccTraceSet, "INSERT");
		map.put(bakLPInsureAccClassSet, "INSERT");
		mResult.add(map);
		return true;
	}

	/*
	 * 备份处理
	 */

	private void BackUpAcc(LCInsureAccSchema tLCInsureAccSchema) {
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema(); // 备份
		tReflections.transFields(tLPInsureAccSchema, tLCInsureAccSchema);
		tLPInsureAccSchema.setMakeDate(mCurrentDate);
		tLPInsureAccSchema.setModifyDate(mCurrentDate);
		tLPInsureAccSchema.setEdorNo(PubFun1.CreateMaxNo("InsuAcc", 20));
		tLPInsureAccSchema.setEdorType("99"); // 代表帐户的备份,和保全项目编码区分开来
		bakLPInsureAccSet.add(tLPInsureAccSchema);
		map.put(tLPInsureAccSchema, "INSERT");
	}

	/*
	 * 备份处理
	 */
	private void BackUpAccClass(LCInsureAccClassSchema tLCInsureAccClassSchema) {
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		tReflections.transFields(tLPInsureAccClassSchema,
				tLCInsureAccClassSchema);
		tLPInsureAccClassSchema.setMakeDate(mCurrentDate);
		tLPInsureAccClassSchema.setModifyDate(mCurrentDate);
		tLPInsureAccClassSchema.setEdorNo(PubFun1.CreateMaxNo("InsuAccClass",
				20));
		tLPInsureAccClassSchema.setEdorType("99"); // 代表帐户的备份,和保全项目编码区分开来
		bakLPInsureAccClassSet.add(tLPInsureAccClassSchema);
	}

	public LCInsureAccClassSchema calAccountCommon(
			LCInsureAccClassSchema tLCInsureAccClassSchema, String tBalaDate) {
		TransferData mReturnData = new TransferData();
		double tMoney = 0; // class的总钱
		double tInterest = 0; // 利息
		// mReturnData =
		// tAccountManage.getAccClassInterestNew(tLCInsureAccClassSchema,tBalaDate,"Y",
		// "D", 2, "F","D");
		mReturnData = tAccountManage.getAccClassInterestNewMS(
				tLCInsureAccClassSchema, tBalaDate, "Y", "D");
		if (mReturnData != null) {
			String tempmoney = String.valueOf(mReturnData
					.getValueByName("aAccClassSumPay"));
			tMoney = Double.parseDouble(tempmoney);
			tMoney = Arith.round(tMoney, 2);
			if (tMoney < 0) {
				tMoney = 0.00;
			}
			tempmoney = String.valueOf(mReturnData
					.getValueByName("aAccClassInterest"));
			tInterest = Double.parseDouble(tempmoney);
			tInterest = Arith.round(tInterest, 2);
			// 生成账户结息轨迹，修改账户缴费项对应明细表。
			// 置上次金额
			tLCInsureAccClassSchema.setLastAccBala(tLCInsureAccClassSchema
					.getInsuAccBala());
			tLCInsureAccClassSchema.setInsuAccBala(tMoney);
			tLCInsureAccClassSchema.setBalaDate(tBalaDate);
			// modify by sunsx 2010-02-10
			// tLCInsureAccClassSchema.setBalaTime(mCurrentTime);
			tLCInsureAccClassSchema.setBalaTime("00:00:00");
			tLCInsureAccClassSchema.setModifyDate(mCurrentDate);
			tLCInsureAccClassSchema.setModifyTime(mCurrentTime);
			// 利息描述
			LCInsureAccTraceSchema LXLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tReflections.transFields(LXLCInsureAccTraceSchema,
					tLCInsureAccClassSchema);
			String mLimit = PubFun.getNoLimit(tLCInsureAccClassSchema
					.getManageCom());
			String tLXSerialNo = PubFun1.CreateMaxNo("SERIALNO", mLimit); // 产生利息流水号码
			LXLCInsureAccTraceSchema.setSerialNo(tLXSerialNo);
			LXLCInsureAccTraceSchema.setMoneyType("LX");
			LXLCInsureAccTraceSchema.setBusyType("LX");
			LXLCInsureAccTraceSchema.setOtherNo(tLCInsureAccClassSchema
					.getPolNo());
			LXLCInsureAccTraceSchema.setOtherType("1");
			LXLCInsureAccTraceSchema.setMoney(tInterest);
			LXLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
			// modify by sunsx 2010-02-10
			// LXLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
			LXLCInsureAccTraceSchema.setMakeTime("00:00:00");
			LXLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
			LXLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
			LXLCInsureAccTraceSchema.setPayDate(tBalaDate);
			LXLCInsureAccTraceSchema.setValueDate(mCurrentDate); // 存交易实际发生日期
			LXLCInsureAccTraceSchema.setShouldValueDate(mBalaDate);
			LXLCInsureAccTraceSchema.setFeeCode("000000");
			LXLCInsureAccTraceSet.add(LXLCInsureAccTraceSchema);
		}
		return tLCInsureAccClassSchema;
	}

	/**
	 * 业务处理
	 * 
	 * @return boolean
	 */
	public boolean dealData() {
		// 查询保单生效对应日
		mCValiDate = getCValiDate(mPolNo);
		if (mCValiDate == null) {
			return false;
		}

		// MMap map = new MMap();

		// 查询帐户总表信息
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(mPolNo);
		tLCInsureAccDB.setInsuAccNo(mInsuAccNo);
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "帐户信息查询失败");
			return false;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			CError.buildErr(this, "没有帐户信息");
			return false;
		}
		tLCInsureAccSchema = tLCInsureAccSet.get(1);
		double tLastAccBala = tLCInsureAccSchema.getInsuAccBala();

		// 保险帐户管理费信息
		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		tLCInsureAccFeeDB.setPolNo(mPolNo);
		tLCInsureAccFeeDB.setInsuAccNo(mInsuAccNo);
		LCInsureAccFeeSet tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		if (tLCInsureAccFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "帐户管理费信息查询失败");
			return false;
		}
		if (tLCInsureAccFeeSet == null || tLCInsureAccFeeSet.size() < 1) {
			CError.buildErr(this, "没有帐户管理费信息");
			return false;
		}

		// 查询帐户子表信息
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(mPolNo);
		tLCInsureAccClassDB.setInsuAccNo(mInsuAccNo);
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if (tLCInsureAccClassDB.mErrors.needDealError()) {
			CError.buildErr(this, "子帐户信息查询失败");
			return false;
		}
		if (tLCInsureAccClassSet == null || tLCInsureAccClassSet.size() < 1) {
			CError.buildErr(this, "没有子帐户信息");
			return false;
		}

		tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);

		// 查询保险账户管理费分类表
		LCInsureAccFeeSchema tLCInsureAccFeeSchema = tLCInsureAccFeeSet.get(1);
		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		tLCInsureAccClassFeeDB.setPolNo(tLCInsureAccClassSchema.getPolNo());
		tLCInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccClassSchema
				.getInsuAccNo());
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB
				.query();
		if (tLCInsureAccClassFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "账户管理费查询失败!");
			return false;
		}
		if (tLCInsureAccClassFeeSet == null
				|| tLCInsureAccClassFeeSet.size() < 1) {
			CError.buildErr(this, "账户管理费查询失败!");
			return false;
		}
		tLCInsureAccClassSchema.setBalaDate(tLCInsureAccSchema.getBalaDate());

		String sLastBalaDate = tLCInsureAccSchema.getBalaDate();
		// String sBalaDate = tLCInsureAccSchema.getMakeDate();
		String tDate = "";
		String isFirst = checkFirst(tLCInsureAccSchema.getInsuAccNo(),
				tLCInsureAccSchema.getPolNo());

		if ("Y".equals(isFirst)) {
			tDate = mCValiDate;

		} else {
			tDate = sLastBalaDate;
		}

		ExeSQL tExeSQL = new ExeSQL();

		/**
		 * ******************************查询上次的帐户价值并结息***************************
		 * ***********
		 */
	/*	"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
		改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
*/
		double tAccValueInterest = 0.0;
		String tStr = "select (case when Sum(Money) is not null then Sum(Money) else 0 end) from LCInsureAccTrace where polno='"
				+ "?mPolNo?"
				+ "' and  paydate >='"
				+ "?mCValiDate?"
				+ "' and paydate <='" + "?tDate?" + "'";
         SQLwithBindVariables sqlbv=new SQLwithBindVariables();
         sqlbv.sql(tStr);
         sqlbv.put("mPolNo", mPolNo);
         sqlbv.put("mCValiDate", mCValiDate);
         sqlbv.put("tDate", tDate);
		String tMoney = tExeSQL.getOneValue(sqlbv);

		if ("".equals(tMoney) || tMoney == null) {
			CError.buildErr(this, "查询帐户余额失败!");
			return false;
		}
		// 获得上个月的帐户结算价值
		mAccValue = Double.parseDouble(tMoney);

		tAccValueInterest = tAccountManage.getAccInterest(
				tLCInsureAccClassSchema.getInsuAccNo(), mRateIntv, // 利率类型
				mRate, // 本次结算利率
				mAccValue, sLastBalaDate, // 上次结算日期
				mBalaDate, // 本次结算日期
				"D"); // 按天结算

		mAccValue += tAccValueInterest;

		/** ***********获得本月度所有的部分领取金额，并对在不同的领取日到本结算日进行借息******** */

		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		String tSQL = "select * from LCInsureAccTrace where polno='"
				+ "?mPolNo?" + "' and paydate >'"
				+ "?sLastBalaDate?" + "' and paydate <='" + "?mBalaDate?" + "'";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
         sqlbv1.sql(tSQL);
         sqlbv1.put("mPolNo", tLCInsureAccClassSchema.getPolNo());
         sqlbv1.put("sLastBalaDate", sLastBalaDate);
         sqlbv1.put("mBalaDate", mBalaDate);
		logger.debug(tSQL);
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv1);
		// 如果发生了帐户进出则要逐个结息
		if (tLCInsureAccTraceSet.size() < 1) {
			mSumGetMoney = 0.0;
		} else {
			String tLastBalaDate;
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				double tGetMoney = 0.0, tTraceInterest = 0.0;
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
				tGetMoney = tLCInsureAccTraceSchema.getMoney();
				tLastBalaDate = tLCInsureAccTraceSchema.getPayDate();

				tTraceInterest = tAccountManage.getAccInterest(
						tLCInsureAccClassSchema.getInsuAccNo(), mRateIntv, // 利率类型
						mRate, // 本次结算利率
						tGetMoney, tLastBalaDate, // 上次结算日期
						mBalaDate, // 本次结算日期
						"D"); // 按天结算

				tGetMoney += tTraceInterest;
				// 获得所有费用的利息和
				tInterest += tTraceInterest;
				mSumGetMoney += tGetMoney;
			}
		}

		mAccValue += mSumGetMoney;// 本次结算帐户的价值
		tInterest += tAccValueInterest; // 本次结算的利息和 包括本身帐户产生利息和中间发生领取以及其他支出产生的利息
		// 创建本次利息轨迹
		createAccTrace(tLCInsureAccClassSchema, tInterest, "LX", "000000");

		if ("Service".equals(mOperate)) // 如果是月结则需要扣除管理费，其他如算现价时无需扣除管理费
		{
			// 查询这个险种的所有管理费
			String tFeeCode = "", feeKind = ""; // 管理费种类区分标志
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
			tLMRiskFeeDB.setInsuAccNo(tLCInsureAccClassSchema.getInsuAccNo());
			tLMRiskFeeDB.setPayPlanCode(tLCInsureAccClassSchema
					.getPayPlanCode());
			tLMRiskFeeDB.setFeeKind("03"); // 03-个单管理费
			tLMRiskFeeDB.setFeeTakePlace("05"); // 05－每月的计价日
			LMRiskFeeSet tLMRiskFeeSet = tLMRiskFeeDB.query();
			if (tLMRiskFeeDB.mErrors.needDealError()) {
				CError.buildErr(this, "账户管理费查询失败!");
				return false;
			}
			if (tLMRiskFeeSet != null && tLMRiskFeeSet.size() > 0) {

				for (int k = 1; k <= tLMRiskFeeSet.size(); k++) // 循环计算每条管理费
				{
					double dRiskFee = 0.0;
					tFeeCode = tLMRiskFeeSet.get(k).getFeeCode();

					Reflections ref = new Reflections();
					// 获得不同管理费
					LCInsureAccClassFeeSchema pLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
					LCInsureAccClassFeeDB rLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
					rLCInsureAccClassFeeDB.setPolNo(tLCInsureAccClassSchema
							.getPolNo());
					rLCInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccClassSchema
							.getInsuAccNo());
					rLCInsureAccClassFeeDB.setFeeCode(tFeeCode);
					LCInsureAccClassFeeSet rLCInsureAccClassFeeSet = rLCInsureAccClassFeeDB
							.query();
					if (rLCInsureAccClassFeeDB.mErrors.needDealError()) {
						CError.buildErr(this, "账户管理费查询失败!");
						return false;
					}
					if (rLCInsureAccClassFeeSet.size() < 1) // 说明是首次扣除管理费
					{
						ref.transFields(pLCInsureAccClassFeeSchema,
								tLCInsureAccClassSchema);
						pLCInsureAccClassFeeSchema.setFeeCode(tFeeCode);
					} else // 否则为递增管理费
					{
						pLCInsureAccClassFeeSchema = rLCInsureAccClassFeeSet
								.get(1);
					}
					// 为了获得传入此时的帐户价值
					LCInsureAccClassSchema cLCInsureAccClassSchema = new LCInsureAccClassSchema();
					cLCInsureAccClassSchema.setSchema(tLCInsureAccClassSchema);
					cLCInsureAccClassSchema.setInsuAccBala(mAccValue);

					dRiskFee = calRiskFee(cLCInsureAccClassSchema,
							tLMRiskFeeSet.get(k), mCValiDate, mBalaDate);
					if (dRiskFee == -1) {
						return false;
					}

					// 创建帐户扣除轨迹
					createAccTrace(cLCInsureAccClassSchema, -dRiskFee, "GL",
							tLMRiskFeeSet.get(k).getFeeCode());

					// 创建保险帐户管理费轨迹记表
					createFeeTrace(pLCInsureAccClassFeeSchema, dRiskFee, "GL",
							tLMRiskFeeSet.get(k).getFeeCode());

					// 更新子帐户帐户管理费表
					LCInsureAccClassFeeSchema rLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
					rLCInsureAccClassFeeSchema
							.setSchema(pLCInsureAccClassFeeSchema);
					rLCInsureAccClassFeeSchema
							.setFee(pLCInsureAccClassFeeSchema.getFee()
									+ dRiskFee);
					rLCInsureAccClassFeeSchema.setBalaDate(mBalaDate);
					rLCInsureAccClassFeeSchema.setBalaTime(mCurrentTime);
					rLCInsureAccClassFeeSchema.setModifyDate(mCurrentDate);
					rLCInsureAccClassFeeSchema.setModifyTime(mCurrentTime);
					updLCInsureAccClassFeeSet.add(rLCInsureAccClassFeeSchema);
					mAccFeeAdmin += dRiskFee;
				}
			}
			mAccValue = mAccValue - mAccFeeAdmin;
		}

		// //////////////////////////////////////////////////////////////////
		// MS不要再算保证利差
		// 判断是否是保证价值结算日
		// String sNeedCalBZ = needCalBZ(tLCInsureAccClassSchema.getBalaDate(),
		// mBalaDate);
		//
		// if (sNeedCalBZ == null)
		// {
		// return false;
		// }
		// else if (sNeedCalBZ.equals("Y"))
		// {
		// //计算保证价值
		// double dInsuAccBala_BZ = calBZBala(tLCInsureAccClassSchema);
		// if (dInsuAccBala_BZ == -1)
		// {
		// return false;
		// }
		//
		// //取保证价值与保单价值中最大者作为最新的帐户价值
		// if (dInsuAccBala_BZ > mAccValue)
		// {
		// dClassInterest_ADD = dInsuAccBala_BZ - mAccValue;
		// //创建保证利息补充轨迹记录
		// createAccTrace(tLCInsureAccClassSchema, dClassInterest_ADD, "AD",
		// "000000");
		// }else
		// {
		// dClassInterest_ADD=0.0;
		// }
		// }
		// //////////////////////////////////////////////////////////////////
		if ("Service".equals(mOperate)) // 只有在结算日才进行判断
		{
			// 判断保单是否借款（通过查询保单状态表），若借款则提供此时的借款的本息和，以及借款是否会导致保单失效
			LOLoanDB tLOLoanDB = new LOLoanDB();
			LOLoanSet tLOLoanSet = new LOLoanSet();

			// desc-降序，asc-升序
			String tString = "select * from loloan where ContNo='"
					+ "?ContNo?"
					// add by jiaqiangli 2009-10-14 此处肯定是时间的概念限制
					// comment by jiaqiangli 2009-10-14
					// contno='86430520080219000265'
					// lastbalate='2009-08-01' curbaladate='2009-10-01'
					// 先递归结息到2009-09-01 但是借款日期为2009-09-03 导致结息起期比止期小
					+ "' and loandate <= '" + "?loandate?"
					+ "' and payoffflag='0' and "
					+ " LoanType='0' order by LoanDate desc";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(tString);
	        sqlbv2.put("ContNo", tLCInsureAccClassSchema.getContNo());
	        sqlbv2.put("loandate", mBalaDate);
			logger.debug(tString);
			tLOLoanSet = tLOLoanDB.executeQuery(sqlbv2);
			if (tLOLoanSet.size() > 0 && tLOLoanSet != null) {
				for (int i = 1; i <= tLOLoanSet.size(); i++) //
				{
					double tSumLoanMoney = 0.0, tSumPreIntrest = 0.0;
					LOLoanSchema tLOLoanSchema = new LOLoanSchema();
					tLOLoanSchema = tLOLoanSet.get(i);
					tSumLoanMoney = tLOLoanSchema.getLeaveMoney();
					// 分段计息
					double tRate = AccountManage.calMultiRateMS(
							tLOLoanSchema.getNewLoanDate(), mBalaDate,
							"000000", "LN", "L", "C", "Y",
							tLOLoanSchema.getCurrency());
					if (tRate + 1 == 0) {
						CError.buildErr(this, "本息和计算失败！");
						return false;
					}
					// 累计计息
					tSumPreIntrest += tLOLoanSchema.getLeaveMoney() * tRate;
					mSumPreIntrest += tSumPreIntrest; // 本次借款的利息之和
					// tSumLoanMoney+=tSumPreIntrest; //累计本次借款的本息和
					mSumLoanMoney += tSumLoanMoney; // 累计所有借款的本金和
				}
				double tSumLoan = mSumLoanMoney + mSumPreIntrest; // 本金和
				if (tSumLoan >= mAccValue || mAccValue <= 0) // 保单借款导致保单终止，插入保单终止通知书
				{
					// 跟新保单状态，先查询，再跟新,若没有则插入，已保证保单状态保持平行
					MMap tMMap = EndContInfo(tLCInsureAccSchema.getContNo());
					if (tMMap == null) {
						CError.buildErr(this, "终止保单数据失败！");
						return false;
					}
					map.add(tMMap);
					InsertContState(tLCInsureAccSchema);
					// 插入保单终止通知书
					InsertPRT(tLCInsureAccSchema);
					map.put(mLCContStateSet, "DELETE&INSERT");
				}
			}

		}
		if ("Service".equals(mOperate)) {

			String tCountSQL = " select count(1) from lcinsureacctrace where moneytype='LX' and contno='"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	        sqlbv3.sql(tCountSQL);
	        sqlbv3.put("contno", tLCInsureAccClassSchema.getContNo());
			int tIntvm = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
			// 插入万能险结算报告书打印记录
			if ((tIntvm + 1) % 12 == 0 && tIntvm > 0) { // 结算12次发年结报告书，也就是第12次发报告
				InsertPRT(tLCInsureAccSchema, "Y");
			}
			if ((tIntvm) % 12 == 0 && tIntvm > 0) { // 结算12次发年结报告书，也就是第12次发报告
				// 增加持续奖金的操作
				int tYears = (tIntvm) / 12; // 结算12次发持续奖金，也就是第13次发报告
				tAddMoney = getAddMoney(tLCInsureAccSchema, tYears);
				if (tAddMoney == -1) {
					CError.buildErr(this, "计算持续奖金失败!");
					return false;
				} else if (tAddMoney > 0) {
					// 插如奖金生成轨迹
					createAccTrace(tLCInsureAccClassSchema, tAddMoney, "JJ",
							"000000");
				}
			}

			InsertPRT(tLCInsureAccSchema, "M"); // 发月结报告书
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
		}

		LCInsureAccFeeSchema rLCInsureAccFeeSchema = new LCInsureAccFeeSchema();

		rLCInsureAccFeeSchema.setSchema(tLCInsureAccFeeSchema);
		rLCInsureAccFeeSchema.setFee(tLCInsureAccFeeSchema.getFee()
				+ mAccFeeAdmin);
		rLCInsureAccFeeSchema.setBalaDate(mBalaDate);
		rLCInsureAccFeeSchema.setBalaTime(mCurrentTime);
		rLCInsureAccFeeSchema.setModifyDate(mCurrentDate);
		rLCInsureAccFeeSchema.setModifyTime(mCurrentTime);
		updLCInsureAccFeeSet.add(rLCInsureAccFeeSchema);
		// 更新子帐户结算数据
		tLCInsureAccClassSchema.setBalaDate(mBalaDate);
		tLCInsureAccClassSchema.setInsuAccBala(mAccValue + tAddMoney);
		tLCInsureAccClassSchema.setLastAccBala(tLastAccBala);
		tLCInsureAccClassSchema.setModifyDate(mCurrentDate);
		tLCInsureAccClassSchema.setModifyTime(mCurrentTime);
		updLCInsureAccClassSet.add(tLCInsureAccClassSchema);

		// 更新账户总表数据
		tLCInsureAccSchema.setBalaDate(mBalaDate);
		tLCInsureAccSchema.setInsuAccBala(mAccValue + tAddMoney);
		tLCInsureAccSchema.setLastAccBala(tLastAccBala);
		tLCInsureAccSchema.setModifyDate(mCurrentDate);
		tLCInsureAccSchema.setModifyTime(mCurrentTime);

		// 打包返回结果
		mResult.clear();
		mResult.add(tLCInsureAccSchema);
		mResult.add(updLCInsureAccClassSet);
		mResult.add(mLCInsureAccTraceSet);
		mResult.add(mLOPRTManagerSet);

		map.put(updLCInsureAccFeeSet, "UPDATE");
		map.put(updLCInsureAccClassFeeSet, "DELETE&INSERT");

		map.put(tLCInsureAccSchema, "UPDATE");
		map.put(tLCInsureAccClassSchema, "UPDATE");

		map.put(mLCInsureAccTraceSet, "DELETE&INSERT");
		TaxCalculator.calBySchemaSet(mLCInsureAccFeeTraceSet);
		map.put(mLCInsureAccFeeTraceSet, "DELETE&INSERT");

		mResult.add(map);
		return true;
	}

	/**
	 * 传递参数 准备账户号码和账户结算日期
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	public boolean getInputData() {

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			buildError("getInputData", "全局变量传递失败!");
			return false;
		}
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mInsuAccNo = (String) mTransferData.getValueByName("InsuAccNo");
		if (mInsuAccNo == null || mInsuAccNo.equals("")) {
			buildError("getInputData", "获得帐户号码失败!");
			return false;
		}

		mBalaDate = (String) mTransferData.getValueByName("BalaDate");
		if (mBalaDate == null || mBalaDate.equals("")) {
			buildError("getInputData", "获得结算日期失败!");
			return false;
		}

		mPolNo = (String) mTransferData.getValueByName("PolNo");
		if (mPolNo == null || mPolNo.equals("")) {
			buildError("getInputData", "获得保单险种号失败!");
			return false;
		}

		return true;
	}

	/**
	 * 计算管理费，同时创建结算轨迹（管理费扣除）和管理费轨迹
	 * 
	 * @param pLCInsureAccClassSchema
	 *            子帐户信息
	 * @param pLMRiskFeeSchema
	 *            管理费描述信息
	 * @return double
	 */
	private double calRiskFee(LCInsureAccClassSchema pLCInsureAccClassSchema,
			LMRiskFeeSchema pLMRiskFeeSchema, String sCValiDate,
			String sBalaDate) {
		double dRiskFee = 0.0;
		if (pLMRiskFeeSchema.getFeeCalModeType().equals("0")) // 0-直接取值
		{
			if (pLMRiskFeeSchema.getFeeCalMode().equals("01")) // 固定值内扣
			{
				dRiskFee = pLMRiskFeeSchema.getFeeValue();
			} else if (pLMRiskFeeSchema.getFeeCalMode().equals("02")) // 固定比例内扣
			{
				dRiskFee = pLCInsureAccClassSchema.getInsuAccBala()
						* pLMRiskFeeSchema.getFeeValue();
			} else {
				dRiskFee = pLMRiskFeeSchema.getFeeValue(); // 默认情况
			}
		} else if (pLMRiskFeeSchema.getFeeCalModeType().equals("1")) // 1-SQL算法描述
		{
			// 准备计算要素
			int iInterval = PubFun.calInterval(sCValiDate, sBalaDate, "Y");
			Calculator tCalculator = new Calculator();
			tCalculator.setCalCode(pLMRiskFeeSchema.getFeeCalCode());
			// 本次结算前的保单价值（上次结算的保单价值）
			tCalculator.addBasicFactor("LastBalance",
					String.valueOf(pLCInsureAccClassSchema.getInsuAccBala()));
			// 上次结算日期
			tCalculator.addBasicFactor("LastBalaDate",
					pLCInsureAccClassSchema.getBalaDate());
			// 本次结算日期
			tCalculator.addBasicFactor("BalaDate", sBalaDate);
			// 保单生效日期
			tCalculator.addBasicFactor("CValiDate", sCValiDate);
			// 本次结算日期对应的保单年度
			tCalculator.addBasicFactor("Interval", String.valueOf(iInterval));
			// 累计已交保费
			tCalculator.addBasicFactor("SumPrem",
					String.valueOf(pLCInsureAccClassSchema.getSumPay()));

			ExeSQL tExeSQL = new ExeSQL();
			String sql = "select insuredsex from lcpol where polno='"
					+ "?polno?" + "' ";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	        sqlbv4.sql(sql);
	        sqlbv4.put("polno", pLCInsureAccClassSchema.getPolNo());
			String insuredsex = tExeSQL.getOneValue(sqlbv4);
			// 性别
			tCalculator.addBasicFactor("Sex", String.valueOf(insuredsex));

			sql = "select insuredappage from lcpol where polno='"
					+ "?polno1?" + "' ";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        sqlbv5.sql(sql);
	        sqlbv5.put("polno1", pLCInsureAccClassSchema.getPolNo());
			String insuredappage = tExeSQL.getOneValue(sqlbv5);
			// 年龄
			tCalculator.addBasicFactor("AppAge", insuredappage);

			String sCalResultValue = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				CError.buildErr(this, "管理费计算失败!");
				return -1;
			}

			try {
				dRiskFee = Double.parseDouble(sCalResultValue);
			} catch (Exception e) {
				CError.buildErr(this, "管理费计算结果错误!" + "错误结果：" + sCalResultValue);
				return -1;
			}
		}

		return dRiskFee;
	}

	/**
	 * 查询保险帐户公布结算利率
	 * 
	 * @return boolean
	 */
	private boolean getLMInsuAccRate() {
		String sql = " select * from lminsuaccrate "
				+ " where riskcode = (select trim(riskcode) from lcpol where polno = '"
				+ "?mPolNo?" + "') and InsuAccNo = '" + "?mInsuAccNo?" + "' " +
				// "and baladate <= date'"
				// + mBalaDate
				// + "'  and date'"
				// + mBalaDate
				// + "' <ADD_MONTHS(baladate,1) "
				"and rateintv = 'Y'  and ratestate='1'";
		SQLwithBindVariables sqlbv45=new SQLwithBindVariables();
        sqlbv45.sql(sql);
        sqlbv45.put("mPolNo", mPolNo);
        sqlbv45.put("mInsuAccNo", mInsuAccNo);
		logger.debug(sql);
		LMInsuAccRateDB tLMInsuAccRateDB = new LMInsuAccRateDB();
		LMInsuAccRateSet tLMInsuAccRateSet = tLMInsuAccRateDB.executeQuery(sqlbv45);
		if (tLMInsuAccRateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保险帐户结算利率查询失败!");
			return false;
		}
		if (tLMInsuAccRateSet == null || tLMInsuAccRateSet.size() < 1) {
			CError.buildErr(this, "没有查到保险帐户结算利率!");
			return false;
		}

		mRateIntv = tLMInsuAccRateSet.get(1).getRateIntv();
		mRate = tLMInsuAccRateSet.get(1).getRate();

		return true;
	}

	/**
	 * 查询保险帐户保证利率
	 * 
	 * @return boolean
	 */
	private boolean getLMAccGuratRate() {
		LMAccGuratRateSchema tLMAccGuratRateSchema = getLMAccGuratRate(mPolNo,
				mInsuAccNo, mBalaDate);

		mRateIntv = tLMAccGuratRateSchema.getRateIntv();
		mRate = tLMAccGuratRateSchema.getRate();
		return true;
	}

	/**
	 * 查询保险帐户保证利率,支持单险种多帐户
	 * 
	 * @param sInsuAccNo
	 * @param sBalaDate
	 * @return LMInsuAccRateSchema
	 * 
	 */
	public LMAccGuratRateSchema getLMAccGuratRate(String sPolNo,
			String sInsuAccNo, String sBalaDate) {
		String sql = " select * from LMAccGuratRate "
				+ " where ratestate ='1' and riskcode = (select riskcode from lcpol where polno = '"
				+ "?polno?" + "') and InsuAccNo = '" + "?mInsuAccNo?"
				+ "' and ( ratestartdate <= '" + "?sBalaDate?"
				+ "' and rateenddate >= '" + "?sBalaDate?"
				+ "') or (ratestartdate <= '" + "?sBalaDate?"
				+ "' and rateenddate is null) ";
		SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
        sqlbv35.sql(sql);
        sqlbv35.put("polno", sPolNo);
        sqlbv35.put("mInsuAccNo", mInsuAccNo);
        sqlbv35.put("sBalaDate", sBalaDate);
		logger.debug(sql);
		LMAccGuratRateDB tLMAccGuratRateDB = new LMAccGuratRateDB();
		LMAccGuratRateSet tLMAccGuratRateSet = tLMAccGuratRateDB
				.executeQuery(sqlbv35);
		if (tLMAccGuratRateDB.mErrors.needDealError()) {
			CError.buildErr(this, "保险帐户保证利率查询失败!");
			return null;
		}
		if (tLMAccGuratRateSet == null || tLMAccGuratRateSet.size() < 1) {
			CError.buildErr(this, "没有查到保险帐户保证利率!");
			return null;
		}

		return tLMAccGuratRateSet.get(1);
	}

	/**
	 * 判断帐户是否是首月结算
	 * 
	 * @param sCalDate
	 *            当前退保时点
	 * @return String
	 */
	private String checkFirst(String sInsurAccNo, String sPolNo) {
		String sql = " select 1 from lcinsureacc a " + " where a.insuaccno = '"
				+ "?sInsurAccNo?" + "' and polno = '" + "?sPolNo?"
				+ "' and baladate = (select cvalidate from lcpol c "
				+ " where polno = (a.polno))";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
        sqlbv6.sql(sql);
        sqlbv6.put("sInsurAccNo", sInsurAccNo);
        sqlbv6.put("sPolNo", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sFirst = tExeSQL.getOneValue(sqlbv6);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return null;
		}
		if (sFirst != null && sFirst.equals("1")) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 判断是否是保证价值结算日（每个保单年度的第一个保单结算日）
	 * 
	 * @param sLastBalaDate
	 * @return String
	 * @rewriter pst 考虑闰年问题
	 */
	private String needCalBZ(String sLastBalaDate, String sCurBalaDate) {
		// 保单当年生效对应日 没有考虑到闰年的问题？
		// ///////////////////////add by pst on 2007-11-19/////////////
		String tYear = sCurBalaDate.substring(0, 4);
		int sYear = Integer.parseInt(tYear);
		String sCurCValiDate = calDate(sYear, mCValiDate);
		// ///////////////////////end add///////////////
		int intv1 = PubFun.calInterval(sLastBalaDate, sCurCValiDate, "D");
		int intv2 = PubFun.calInterval(sCurCValiDate, sCurBalaDate, "D");
		if (intv1 > 0 && intv2 >= 0) // 需要保证利率结算
		{
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * 查询保单生效日期
	 * 
	 * @param sPolNo
	 * @return String
	 */
	public String getCValiDate(String sPolNo) {
		String sql = " select cvalidate from lcpol where polno = '" + "?polno?"
				+ "'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        sqlbv7.sql(sql);
        sqlbv7.put("polno", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sCValiDate = tExeSQL.getOneValue(sqlbv7);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单生效日期查询失败!");
			return null;
		}
		if (sCValiDate == null || sCValiDate.equals("")) {
			CError.buildErr(this, "保单生效日期为空!");
			return null;
		}
		if (sCValiDate.length() > 10) {
			sCValiDate = sCValiDate.substring(0, 10);
		}
		return sCValiDate;
	}

	/**
	 * 查询最近一次保证价值结算时点
	 * 
	 * @param sPolNo
	 * @param sInsuAccNo
	 * @return String
	 */
	public String getLastBZDate(String sPolNo, String sInsuAccNo) {
		// 查询最近一次保证价值结算时点
		String sql = " select max(PayDate) from lcinsureacctrace where trim(moneytype) = 'AD' "
				+ " and polno = '"
				+ "?polno?"
				+ "' and trim(insuaccno) = '"
				+ "?sInsuAccNo?" + "'";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        sqlbv8.sql(sql);
        sqlbv8.put("polno", sPolNo);
        sqlbv8.put("sInsuAccNo", sInsuAccNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sLastBZDate = tExeSQL.getOneValue(sqlbv8);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单保证结算日期查询失败!");
			return null;
		}
		if (sLastBZDate == null || sLastBZDate.equals("")) {
			// CError.buildErr(this, "保单保证结算日期为空!");
			// return null;
			String sCValiDate = getCValiDate(sPolNo);
			if (sCValiDate == null) {
				return null;
			}
			return sCValiDate;
		}
		if (sLastBZDate.length() > 10) {
			sLastBZDate = sLastBZDate.substring(0, 10);
		}

		return sLastBZDate;
	}

	/**
	 * 进行保证价值结算
	 * 
	 * @param pLCInsureAccClassSchema
	 * @param sInsuAccNo
	 *            帐户号
	 * @param dLastAccBala
	 *            期初帐户金额
	 * @author pst
	 * @return double
	 */
	private double calBZBala(LCInsureAccClassSchema pLCInsureAccClassSchema) {
		String sPolNo = pLCInsureAccClassSchema.getPolNo();
		String sInsuAccNo = pLCInsureAccClassSchema.getInsuAccNo();
		String wLastBalaDate = pLCInsureAccClassSchema.getBalaDate();
		String sCurBalaDate = PubFun.calDate(wLastBalaDate, 1, "M", "");

		// if (pLCInsureAccClassSchema.getInsuAccNo().trim().equals("902000"))
		// //902保证结算比较特殊
		// {
		// return calBZBala902(sPolNo, sInsuAccNo, dLastAccBala);
		// }

		String sPreCValiDate;
		ExeSQL tExeSQL = new ExeSQL();

		// ///////////////////////add by pst on 2007-11-19/////////////
		String tYear = wLastBalaDate.substring(0, 4); // 避免跨年度问题
		int sYear = Integer.parseInt(tYear);
		String sCurCValiDate = calDate(sYear, mCValiDate);
		sPreCValiDate = PubFun.calDate(sCurCValiDate, -1, "Y", ""); // 获得上一个保单生效日
		// ///////////////////////end add///////////////

		// 由于保证利差只是一个判断，即当保证利差大于结算利差时则需要计算，否则为零
		String sql = " select * from lminsuaccrate "
				+ " where riskcode = (select trim(riskcode) from lcpol where polno = '"
				+ "?polno?" + "') and InsuAccNo = '" + "?sInsuAccNo?"
				+ "' and baladate >= '" + "?baladate?" + "' and baladate <='"
				+ "?baladate1?" + "'  and rateintv = 'Y' and ratestate='1' ";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        sqlbv9.sql(sql);
        sqlbv9.put("polno", sPolNo);
        sqlbv9.put("sInsuAccNo", sInsuAccNo);
        sqlbv9.put("baladate", sPreCValiDate);
        sqlbv9.put("baladate1", sCurCValiDate);
		LMInsuAccRateDB tLMInsuAccRateDB = new LMInsuAccRateDB();
		LMInsuAccRateSet tLMInsuAccRateSet = tLMInsuAccRateDB.executeQuery(sqlbv9);
		logger.debug(sql);

		LMAccGuratRateSchema tLMAccGuratRateSchema = getLMAccGuratRate(mPolNo,
				mInsuAccNo, mBalaDate);
		if (tLMAccGuratRateSchema == null) {
			return -1;
		}

		double tGruRate = tLMAccGuratRateSchema.getRate();
		String tRateIntv = tLMAccGuratRateSchema.getRateIntv();
		boolean tCountFlag = false;// 是否需要计算的标志,默认不需要计算

		for (int i = 1; i <= tLMInsuAccRateSet.size(); i++) {
			double tRate = tLMInsuAccRateSet.get(i).getRate();
			if (tGruRate > tRate) {
				tCountFlag = true;
				break;
			}
		}

		if (tCountFlag) {
			// 查询管理费计算描述
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
			tLMRiskFeeDB.setInsuAccNo(sInsuAccNo);
			tLMRiskFeeDB.setPayPlanCode(pLCInsureAccClassSchema
					.getPayPlanCode());
			tLMRiskFeeDB.setFeeKind("03"); // 03-个单管理费
			tLMRiskFeeDB.setFeeTakePlace("05"); // 05－每月的计价日
			LMRiskFeeSet month_LMRiskFeeSet = tLMRiskFeeDB.query();
			if (tLMRiskFeeDB.mErrors.needDealError()) {
				CError.buildErr(this, "账户管理费查询失败!");
				return -1;
			}

			// 查询这一年所有的结算轨迹
			String tSQL = " select * from lcinsureacctrace where moneytype = 'LX' "
					+ " and insuaccno = '"
					+ "?insuaccno?"
					+ "' "
					+ " and polno = '"
					+ "?polno?"
					+ "' and paydate>='"
					+ "?paydate?"
					+ "' and paydate <='"
					+ "?baladate1?"
					+ "' order by paydate "; // order
			// by
			// 经典！
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
	        sqlbv10.sql(sql);
	        sqlbv10.put("insuaccno", sInsuAccNo);
	        sqlbv10.put("polno", sPolNo);
	        sqlbv10.put("paydate", sPreCValiDate);
	        sqlbv10.put("baladate1", sCurCValiDate);
			LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
			LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB
					.executeQuery(sqlbv10);
			logger.debug(tSQL);
			if (tLCInsureAccTraceDB.mErrors.needDealError()) {
				CError.buildErr(this, "保单结算轨迹查询失败!");
				return -1;
			}
			if (tLCInsureAccTraceSet == null) {
				CError.buildErr(this, "保单结算轨迹查询失败!");
				return -1;
			}

			// 计算本月初的帐户价值，作为月末的本金,且第一个起点值为上生效日时的值，以后就时每年度生效日对应日时的值
			String tStr = "select (case when Sum(Money) is not null then Sum(Money) else 0 end) from LCInsureAccTrace where polno='"
					+ "?polno?"
					+ "' and  paydate >='"
					+ "?paydate?"
					+ "' and paydate <='" + "?paydate1?" + "'";
			
			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
	        sqlbv11.sql(tStr);
	        sqlbv11.put("polno", mPolNo);
	        sqlbv11.put("paydate", mCValiDate);
	        sqlbv11.put("paydate1", sPreCValiDate);
	        
			String tMoney = tExeSQL.getOneValue(sqlbv11);
			if ("".equals(tMoney) || tMoney == null) {
				CError.buildErr(this, "查询帐户余额失败!");
				return -1;
			}
			double tAccValue = Double.parseDouble(tMoney);

			// 逐月按保证利息计算保证帐户价值,注意:这时只算到本次结算日之前一次的保证帐户价值，算完后还需要再算一次本次结算时的帐户价值
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				double dRiskFee = 0.0;
				double dClassFee = 0.0;

				// 关键步骤，递增按每月的结算日进行结算
				// 获得本个月的计算日
				String rCurBalaDate = tLCInsureAccTraceSet.get(i).getPayDate();
				// 获得下个月的计算日
				String rNextBalaDate = PubFun.calDate(rCurBalaDate, 1, "M", "");
				// 获得得上个结算日 可能为某一个月的首日，也可能是保单的生效日
				String rPreBalaDate = PubFun.calDate(rCurBalaDate, -1, "M", "");

				String tFlag = "";
				int intv1 = PubFun.calInterval(rPreBalaDate, mCValiDate, "D");
				int intv2 = PubFun.calInterval(mCValiDate, rCurBalaDate, "D");
				if (intv1 > 0 && intv2 >= 0) {
					tFlag = "Y";
				} else {
					tFlag = "N";
				}

				if ("Y".equals(tFlag)) // 说明保证利差的计算起点为保单的生效日
				{
					rNextBalaDate = rCurBalaDate;
					rCurBalaDate = mCValiDate;
				} else {
					rNextBalaDate = rCurBalaDate;
					rCurBalaDate = PubFun.calDate(rCurBalaDate, -1, "M", "");

				}

				double tAccBala = 0.0;

				// 获得前一个月的计算本金，如过是首月则为保单生效日或者每年生效对应日时的帐户价值
				tAccBala = tAccValue;

				// 计算下个月本息和
				double tInterest = tAccountManage.getAccInterest(
						pLCInsureAccClassSchema.getInsuAccNo(), tRateIntv, // 利率类型
						tGruRate, // 本次结算利率
						tAccBala, rCurBalaDate, // 上次结算日期
						rNextBalaDate, // 下次结算日期
						"D"); // 按天结算

				// 计算下个月的管理费 其中最后一个月由于没有trace记录实际类似是试算
				if (month_LMRiskFeeSet != null && month_LMRiskFeeSet.size() > 0) {
					for (int k = 1; k <= month_LMRiskFeeSet.size(); k++) // 循环计算每条管理费
					{
						// 计算管理费金额
						dRiskFee = calRiskFee(pLCInsureAccClassSchema,
								month_LMRiskFeeSet.get(k), mCValiDate,
								rNextBalaDate);
						if (dRiskFee == -1) {
							return -1;
						}
						dClassFee += dRiskFee;
					}
				}

				// 计算下个月发生的领取
				double tSumGetMoney = 0.0;
				LCInsureAccTraceSet rLCInsureAccTraceSet = new LCInsureAccTraceSet();
				LCInsureAccTraceDB rLCInsureAccTraceDB = new LCInsureAccTraceDB();
				String tSql = "select * from LCInsureAccTrace where polno='"
						+ "?polno?"
						+ "' and paydate >'" + "?paydate?"
						+ "' and paydate <'" + "?paydate1?" + "'";
				SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		        sqlbv12.sql(tSql);
		        sqlbv12.put("polno", tLCInsureAccClassSchema.getPolNo());
		        sqlbv12.put("paydate", rCurBalaDate);
		        sqlbv12.put("paydate1", rNextBalaDate);
				rLCInsureAccTraceSet = rLCInsureAccTraceDB.executeQuery(sqlbv12);
				logger.debug(tSql);
				// 如果发生了帐户进出则要逐个结息
				if (rLCInsureAccTraceSet.size() < 1) {
					tSumGetMoney = 0.0;
				} else {
					String cLastBalaDate;
					for (int j = 1; j <= rLCInsureAccTraceSet.size(); j++) {
						double tGetMoney = 0.0, tTraceInterest = 0.0;
						LCInsureAccTraceSchema cLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
						cLCInsureAccTraceSchema = rLCInsureAccTraceSet.get(j);
						tGetMoney = cLCInsureAccTraceSchema.getMoney();
						cLastBalaDate = cLCInsureAccTraceSchema.getPayDate();

						tTraceInterest = tAccountManage.getAccInterest(
								pLCInsureAccClassSchema.getInsuAccNo(),
								tRateIntv, // 利率类型
								tGruRate, // 本次结算利
								tGetMoney, cLastBalaDate, // 上次结算日期
								rNextBalaDate, // 本次结算日期
								"D"); // 按天结算
						tGetMoney += tTraceInterest;

						tSumGetMoney += tGetMoney;
					}
				}
				// 月末汇总本息和-管理费-领取金额产生的本息和
				tAccValue = tAccValue + tInterest - dClassFee - tSumGetMoney;
			}
			// 试算到本次结算日的保单保证帐户价值
			tAccValue = countAccGruMoney(tAccValue, pLCInsureAccClassSchema);

			return tAccValue;
		} else {
			return 0.0;
		}

	}

	/**
	 * 试算到本次结算日的保单保证帐户价值
	 */
	private double countAccGruMoney(double tMoney,
			LCInsureAccClassSchema pLCInsureAccClassSchema) {
		double dRiskFee = 0.0;
		double dClassFee = 0.0;
		double tAccMoney = 0.0;
		String sInsuAccNo = pLCInsureAccClassSchema.getInsuAccNo();

		LMAccGuratRateSchema tLMAccGuratRateSchema = getLMAccGuratRate(mPolNo,
				mInsuAccNo, mBalaDate);
		if (tLMAccGuratRateSchema == null) {
			return -1;
		}

		double tGruRate = tLMAccGuratRateSchema.getRate();
		String tRateIntv = tLMAccGuratRateSchema.getRateIntv();

		// 关键步骤，递增按每月的结算日进行结算
		// 获得本个月的计算日
		String rCurBalaDate = pLCInsureAccClassSchema.getBalaDate();
		// 获得下个月的计算日
		String rNextBalaDate = PubFun.calDate(rCurBalaDate, 1, "M", "");
		// 获得得上个结算日 可能为某一个月的首日，也可能是保单的生效日
		String rPreBalaDate = PubFun.calDate(rCurBalaDate, -1, "M", "");

		// 计算下个月本息和
		double tInterest = tAccountManage.getAccInterest(
				pLCInsureAccClassSchema.getInsuAccNo(), tRateIntv, // 利率类型
				tGruRate, // 本次结算利率
				tMoney, rCurBalaDate, // 上次结算日期
				rNextBalaDate, // 下次结算日期
				"D"); // 按天结算

		// 查询管理费计算描述
		LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB();
		tLMRiskFeeDB.setInsuAccNo(sInsuAccNo);
		tLMRiskFeeDB.setPayPlanCode(pLCInsureAccClassSchema.getPayPlanCode());
		tLMRiskFeeDB.setFeeKind("03"); // 03-个单管理费
		tLMRiskFeeDB.setFeeTakePlace("05"); // 05－每月的计价日
		LMRiskFeeSet month_LMRiskFeeSet = tLMRiskFeeDB.query();
		if (tLMRiskFeeDB.mErrors.needDealError()) {
			CError.buildErr(this, "账户管理费查询失败!");
			return -1;
		}

		// 计算下个月的管理费 其中最后一个月由于没有trace记录实际类似是试算
		if (month_LMRiskFeeSet != null && month_LMRiskFeeSet.size() > 0) {
			for (int k = 1; k <= month_LMRiskFeeSet.size(); k++) // 循环计算每条管理费
			{
				// 计算管理费金额
				dRiskFee = calRiskFee(pLCInsureAccClassSchema,
						month_LMRiskFeeSet.get(k), mCValiDate, rNextBalaDate);
				if (dRiskFee == -1) {
					return -1;
				}
				dClassFee += dRiskFee;
			}
		}

		// 计算下个月发生的领取
		double tSumGetMoney = 0.0;
		LCInsureAccTraceSet rLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceDB rLCInsureAccTraceDB = new LCInsureAccTraceDB();
		String tSql = "select * from LCInsureAccTrace where polno='"
				+ "?polno?" + "' and paydate >'"
				+ "?paydate?" + "' and paydate <'" + "?paydate1?" + "'";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
        sqlbv12.sql(tSql);
        sqlbv12.put("polno", tLCInsureAccClassSchema.getPolNo());
        sqlbv12.put("paydate", rCurBalaDate);
        sqlbv12.put("paydate1", rNextBalaDate);
		rLCInsureAccTraceSet = rLCInsureAccTraceDB.executeQuery(sqlbv12);
		logger.debug(tSql);
		// 如果发生了帐户进出则要逐个结息
		if (rLCInsureAccTraceSet.size() < 1) {
			tSumGetMoney = 0.0;
		} else {
			String cLastBalaDate;
			for (int j = 1; j <= rLCInsureAccTraceSet.size(); j++) {
				double tGetMoney = 0.0, tTraceInterest = 0.0;
				LCInsureAccTraceSchema cLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				cLCInsureAccTraceSchema = rLCInsureAccTraceSet.get(j);
				tGetMoney = cLCInsureAccTraceSchema.getMoney();
				cLastBalaDate = cLCInsureAccTraceSchema.getPayDate();

				tTraceInterest = tAccountManage.getAccInterest(
						pLCInsureAccClassSchema.getInsuAccNo(), tRateIntv, // 利率类型
						tGruRate, // 本次结算利率
						tGetMoney, cLastBalaDate, // 上次结算日期
						rNextBalaDate, // 本次结算日期
						"D"); // 按天结算
				tGetMoney += tTraceInterest;

				tSumGetMoney += tGetMoney;
			}
		}
		// 月末汇总本息和-管理费-领取金额产生的本息和
		tAccMoney = tMoney + tInterest - dClassFee - tSumGetMoney;

		return tAccMoney;
	}

	/**
	 * 进行保证价值结算（902每年结算一次，没有管理费扣除）
	 * 
	 * @param sPolNo
	 *            险种号
	 * @param sInsuAccNo
	 *            帐户号
	 * @param dLastAccBala
	 *            期初帐户金额
	 * @return double
	 */
	private double calBZBala902(String sPolNo, String sInsuAccNo,
			double dLastAccBala) {
		double dBZBala = dLastAccBala;

		String sPreCValiDate = getCValiDate(sPolNo); // 保单生效对应日
		sPreCValiDate = PubFun.calDate(sPreCValiDate, -1, "D", null); // 首次保证结算，既算头也算尾；
		String sCurtBalaDate = "";
		// 查询保证价值历史结算时点
		String sql = " select * from lcinsureacctrace where moneytype = 'AD' "
				+ " and insuaccno = '" + "?sInsuAccNo?" + "' " + " and polno = '"
				+ "?sPolNo?" + "' order by paydate ";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
        sqlbv13.sql(sql);
        sqlbv13.put("sInsuAccNo", sInsuAccNo);
        sqlbv13.put("sPolNo", sPolNo);
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB
				.executeQuery(sqlbv13);
		if (tLCInsureAccTraceDB.mErrors.needDealError()) {
			CError.buildErr(this, "保证价值结算查询失败!");
			return -1;
		}
		if (tLCInsureAccTraceSet != null && tLCInsureAccTraceSet.size() > 0) {
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				sCurtBalaDate = tLCInsureAccTraceSet.get(i).getPayDate();

				// 查询该保证价值结算时点保证结算利率
				LMAccGuratRateSchema tLMAccGuratRateSchema = getLMAccGuratRate(
						sPolNo, sInsuAccNo, sCurtBalaDate);
				if (tLMAccGuratRateSchema == null) {
					return -1;
				}

				// 根据保证利率计算上次保证结算日到本次保证时点的保证利息
				AccountManage tAccountManage = new AccountManage();
				double dAccInterestBZ = tAccountManage.getAccInterest(
						sInsuAccNo, tLMAccGuratRateSchema.getRateIntv(),
						tLMAccGuratRateSchema.getRate(), dBZBala, // 最近一次保证价值
						sPreCValiDate, // 最近一次保证结算日期
						sCurtBalaDate, // 本次结算时点
						"D"); // 按天结算
				dBZBala += dAccInterestBZ;

				sPreCValiDate = sCurtBalaDate;
			}
		}

		// 查询该保证价值结算时点保证结算利率
		LMAccGuratRateSchema tLMAccGuratRateSchema = getLMAccGuratRate(sPolNo,
				sInsuAccNo, mBalaDate);
		if (tLMAccGuratRateSchema == null) {
			return -1;
		}

		// 根据保证利率计算上次保证结算日到本次保证时点的保证利息
		AccountManage tAccountManage = new AccountManage();
		double dAccInterestBZ = tAccountManage.getAccInterest(sInsuAccNo,
				tLMAccGuratRateSchema.getRateIntv(),
				tLMAccGuratRateSchema.getRate(), dBZBala, // 最近一次保证价值
				sPreCValiDate, // 最近一次保证结算日期
				mBalaDate, // 本次结算时点
				"D"); // 按天结算
		dBZBala += dAccInterestBZ;

		return dBZBala;
	}

	/**
	 * 创建结算轨迹记录
	 * 
	 * @param pLCInsureAccClassSchema
	 * @param dMoney
	 *            结算金额
	 * @param sMoneyType
	 *            金额类型
	 * @param sFeeCode
	 *            管理费编码
	 * @return boolean
	 */
	private boolean createAccTrace(
			LCInsureAccClassSchema pLCInsureAccClassSchema, double dMoney,
			String sMoneyType, String sFeeCode) {
		Reflections ref = new Reflections();
		// 创建帐户轨迹记录
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		ref.transFields(tLCInsureAccTraceSchema, pLCInsureAccClassSchema);
		String tLimit = PubFun.getNoLimit(pLCInsureAccClassSchema
				.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		tLCInsureAccTraceSchema.setSerialNo(serNo);

		tLCInsureAccTraceSchema.setMoneyType(sMoneyType);
		tLCInsureAccTraceSchema.setMoney(dMoney);
		tLCInsureAccTraceSchema.setPayDate(mBalaDate);
		tLCInsureAccTraceSchema.setFeeCode(sFeeCode);

		tLCInsureAccTraceSchema.setMakeDate(mCurrentDate);
		tLCInsureAccTraceSchema.setMakeTime(mCurrentTime);
		tLCInsureAccTraceSchema.setModifyDate(mCurrentDate);
		tLCInsureAccTraceSchema.setModifyTime(mCurrentTime);
		tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		return true;
	}

	/**
	 * 创建结算轨迹记录
	 * 
	 * @param pLCInsureAccClassFeeSchema
	 * @param dMoney
	 *            结算金额
	 * @param sMoneyType
	 *            金额类型
	 * @param sFeeCode
	 *            管理费编码
	 * @return boolean
	 */
	private boolean createFeeTrace(
			LCInsureAccClassFeeSchema pLCInsureAccClassFeeSchema,
			double dMoney, String sMoneyType, String sFeeCode) {
		Reflections ref = new Reflections();
		// 创建帐户轨迹记录
		LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
		ref.transFields(tLCInsureAccFeeTraceSchema, pLCInsureAccClassFeeSchema);
		String tLimit = PubFun.getNoLimit(pLCInsureAccClassFeeSchema
				.getManageCom());
		String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		tLCInsureAccFeeTraceSchema.setSerialNo(serNo);

		tLCInsureAccFeeTraceSchema.setMoneyType(sMoneyType);
		tLCInsureAccFeeTraceSchema.setFee(dMoney);
		tLCInsureAccFeeTraceSchema.setPayDate(mBalaDate);
		tLCInsureAccFeeTraceSchema.setFeeCode(sFeeCode);

		tLCInsureAccFeeTraceSchema.setMakeDate(mCurrentDate);
		tLCInsureAccFeeTraceSchema.setMakeTime(mCurrentTime);
		tLCInsureAccFeeTraceSchema.setModifyDate(mCurrentDate);
		tLCInsureAccFeeTraceSchema.setModifyTime(mCurrentTime);
		tLCInsureAccFeeTraceSchema.setOperator(mGlobalInput.Operator);
		mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
		return true;
	}

	/**
	 * 往打印管理表插入万能险结算报告书打印记录
	 * 
	 * @param pLCInsureAccSchema
	 * @param tPrintType
	 * @return boolean
	 */
	private boolean InsertPRT(LCInsureAccSchema pLCInsureAccSchema,
			String tPrintType) {
		LOPRTManagerSchema tLOPRTManagerSchema;
		try {
			String tAgentCode = "";
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			String strsql = "select agentcode from lcpol where polno = '"
					+ "?polno?" + "'";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
	        sqlbv14.sql(strsql);
	        sqlbv14.put("polno", pLCInsureAccSchema.getPolNo());
			tssrs = texesql.execSQL(sqlbv14);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				tAgentCode = tssrs.GetText(1, 1);
			}
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(pLCInsureAccSchema
					.getManageCom());
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(pLCInsureAccSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			if ("Y".equals(tPrintType)) // 年结报告
			{
				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_EdorINSUACCY); // 打印类型
				tLOPRTManagerSchema.setStandbyFlag1(pLCInsureAccSchema
						.getPolNo());
				tLOPRTManagerSchema.setStandbyFlag2(mBalaDate); // 本次结算日期
				tLOPRTManagerSchema.setStandbyFlag3(pLCInsureAccSchema
						.getInsuAccNo());
				tLOPRTManagerSchema.setStandbyFlag4(Double
						.toString(mSumPreIntrest));// 帐户累计借款利息
				tLOPRTManagerSchema.setStandbyFlag5(Double
						.toString(mSumLoanMoney)); // 帐户累计本金
				tLOPRTManagerSchema.setStandbyFlag6(Double
						.toString(dClassInterest_ADD));
				tLOPRTManagerSchema.setStandbyFlag7(Double.toString(mAccValue));
			} else if ("M".equals(tPrintType)) // 月结报告
			{

				tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_EdorINSUACCM); // 打印类型
				tLOPRTManagerSchema.setStandbyFlag1(pLCInsureAccSchema
						.getPolNo());
				tLOPRTManagerSchema.setStandbyFlag2(mBalaDate); // 本次结算日期
				tLOPRTManagerSchema.setStandbyFlag3(Double.toString(mRate)); // 存放结算利率
				tLOPRTManagerSchema.setStandbyFlag4(Double
						.toString(mSumPreIntrest));// 帐户累计借款利息
				tLOPRTManagerSchema.setStandbyFlag5(Double
						.toString(mSumLoanMoney)); // 帐户累计本金
				// tLOPRTManagerSchema.setStandbyFlag6(Double.toString(mAccValue*2));//即使保额即当时帐户价值的两倍
				tLOPRTManagerSchema.setStandbyFlag7(Double
						.toString(pLCInsureAccSchema.getLastAccBala()));// 上期帐户价值
			}

			tLOPRTManagerSchema.setManageCom(pLCInsureAccSchema.getManageCom()); // 管理机构
			tLOPRTManagerSchema.setAgentCode(tAgentCode); // 代理人编码
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			tLOPRTManagerSchema.setMakeDate(mCurrentDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);

			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		} catch (Exception e) {
			CError.buildErr(this, "插入万能险结算报告书失败!");
		}
		return true;
	}

	/**
	 * 往打印管理表插入万能险终止通知书打印记录
	 * 
	 * @param pLCInsureAccSchema
	 * @return boolean
	 */
	private boolean InsertPRT(LCInsureAccSchema pLCInsureAccSchema) {
		LOPRTManagerSchema tLOPRTManagerSchema;
		try {
			String tAgentCode = "";
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			String strsql = "select agentcode from lcpol where polno = '"
					+ "?polno1?" + "'";
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
	        sqlbv15.sql(strsql);
	        sqlbv15.put("polno1", pLCInsureAccSchema.getPolNo());
			tssrs = texesql.execSQL(sqlbv15);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				tAgentCode = tssrs.GetText(1, 1);
			}
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(pLCInsureAccSchema
					.getManageCom());
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(pLCInsureAccSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号

			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_EdorINSUACCTEND); // 打印类型
			tLOPRTManagerSchema.setStandbyFlag1(pLCInsureAccSchema.getPolNo());
			tLOPRTManagerSchema.setStandbyFlag2(mBalaDate); // 本次结算日期
			tLOPRTManagerSchema.setManageCom(pLCInsureAccSchema.getManageCom()); // 管理机构
			tLOPRTManagerSchema.setAgentCode(tAgentCode); // 代理人编码
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			tLOPRTManagerSchema.setMakeDate(mCurrentDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		} catch (Exception e) {
			CError.buildErr(this, "插入万能险终止通知书失败!");
		}
		return true;
	}

	/**
	 * 获取帐户的持续奖金
	 * 
	 * @author PST
	 * @param LCInsureAccSchema
	 *            tLCInsureAccSchema
	 * @param int tYears
	 */
	private double getAddMoney(LCInsureAccSchema tLCInsureAccSchema, int tYears) {
		double tMoney = 0.0;
		// 准备计算要素

		// 查询算法编码
		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(tLCInsureAccSchema.getRiskCode());
		tLMEdorCalDB.setEdorType("00");
		tLMEdorCalDB.setCalType("CalMoney");
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			return tMoney;
		}

		// 计算帐户持续奖金的为保费扣除部分领取的金额
	/*	"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
		改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
*/
		String tSQL = "select (case when (select sum(money) from lcinsureacctrace  where moneytype in ('BF','TB','TBFY') and polno='"
				+ "?polno3?"
				+ "'"
				+ ")+(select sum(fee) from lcinsureaccFeetrace  where feecode like '%01' and polno='"
				+ "?polno3?" + "'" + ") is not null then (select sum(money) from lcinsureacctrace  where moneytype in ('BF','TB','TBFY') and polno='"
				+ "?polno3?"
				+ "'"
				+ ")+(select sum(fee) from lcinsureaccFeetrace  where feecode like '%01' and polno='"
				+ "?polno3?" + "'" + ") else null end) from dual";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
        sqlbv16.sql(tSQL);
        sqlbv16.put("polno3", tLCInsureAccSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		String cMoney = tExeSQL.getOneValue(sqlbv16);
		if ("".equals(cMoney)) {
			return -1;
		}
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
		// 险种
		tCalculator
				.addBasicFactor("RiskCode", tLCInsureAccSchema.getRiskCode());
		// 本次结算日期对应的保单年度
		tCalculator.addBasicFactor("Interval", String.valueOf(tYears));
		// 累计已交保费
		tCalculator.addBasicFactor("SumPrem", String.valueOf(cMoney));

		String sCalResultValue = tCalculator.calculate();
		if (tCalculator.mErrors.needDealError()) {
			CError.buildErr(this, "持续奖金计算失败!");
			return -1;
		}

		try {
			if ("".equals(sCalResultValue)) {
				return 0;
			} else {
				tMoney = Double.parseDouble(sCalResultValue);
			}
		} catch (Exception e) {
			CError.buildErr(this, "持续奖金计算结果错误!" + "错误结果：" + sCalResultValue);
			return -1;
		}
		return tMoney;
	}

	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "InsuAccBala";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * getErrors
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 对保单状态进行操作，是ContNo层的
	 * 
	 * @param tLCInsureAccSchema
	 * @return boolean
	 * @author PST 跟新Avaliable状态，插入保单终止状态
	 */
	private boolean InsertContState(LCInsureAccSchema tLCInsureAccSchema) {
		try {
			LCContStateSchema tLCContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='"
					+ "?ContNo?" + "'"
					+ " and StateType='Terminate'" + " and State='1'"
					+ " and EndDate is null";
			SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
	        sqlbv17.sql(tSql);
	        sqlbv17.put("ContNo", tLCInsureAccSchema.getPolNo());
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv17);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}
			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setStartDate(mBalaDate); // 状态在前一天结束
			tLCContStateSchema.setContNo(tLCInsureAccSchema.getContNo());
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setPolNo("000000");
			tLCContStateSchema.setStateType("Terminate");
			tLCContStateSchema.setState("1");
			tLCContStateSchema.setStateReason("08"); // 其他终止
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(mCurrentDate);
			tLCContStateSchema.setMakeTime(mCurrentTime);
			tLCContStateSchema.setModifyDate(mCurrentDate);
			tLCContStateSchema.setModifyTime(mCurrentTime);
			mLCContStateSet.add(tLCContStateSchema);

			// 先查询当前状态是否是已经失效状态
			String tsql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='"
					+ "?ContNo1?" + "'" + " and PolNo='"
					+ "?PolNo?" + "'"
					+ " and StateType='Available'" + " and State='1'"
					+ " and EndDate is  null";
			SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
	        sqlbv18.sql(tsql);
	        sqlbv18.put("ContNo1", tLCInsureAccSchema.getContNo());
	        sqlbv18.put("PolNo", tLCInsureAccSchema.getPolNo());
			tSSRS = tExeSQL.execSQL(sqlbv18);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}
			// 更新保单的有效状态结束日期
			String tUpstr = "update LCContState set EndDate='"
					+ "?mBalaDate?"
					+ "' where contno='"
					+ "?contno?"
					+ "'"
					+ " and polno='"
					+ "?polno?"
					+ "' and state='0' and StateType='Available' and EndDate is null ";
			SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
	        sqlbv19.sql(tUpstr);
	        sqlbv19.put("mBalaDate", mBalaDate);
	        sqlbv19.put("contno", tLCInsureAccSchema.getContNo());
	        sqlbv19.put("polno", tLCInsureAccSchema.getPolNo());
			map.put(sqlbv19, "UPDATE");
			// 新状态信息
			LCContStateSchema rLCContStateSchema = new LCContStateSchema();
			rLCContStateSchema.setContNo(tLCInsureAccSchema.getContNo());
			rLCContStateSchema.setInsuredNo("000000");
			rLCContStateSchema.setPolNo(tLCInsureAccSchema.getPolNo());
			rLCContStateSchema.setStateType("Available");
			rLCContStateSchema.setState("1");
			rLCContStateSchema.setStartDate(mBalaDate);
			rLCContStateSchema.setOperator(mGlobalInput.Operator);
			rLCContStateSchema.setMakeDate(mCurrentDate);
			rLCContStateSchema.setMakeTime(mCurrentTime);
			rLCContStateSchema.setModifyDate(mCurrentDate);
			rLCContStateSchema.setModifyTime(mCurrentTime);
			mLCContStateSet.add(rLCContStateSchema);
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "InsuAccBala";
			tError.functionName = "InsertContState";
			tError.errorMessage = "修改保单状态时产生错误！保单号："
					+ tLCInsureAccSchema.getContNo();
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * getResult
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 查询帐户在某个结算时点的本息和
	 * 
	 * @param sPolNo
	 *            保单号
	 * @param sInsuAccNo
	 *            帐户号
	 * @param sBalaDate
	 *            结算时点
	 * @return double
	 */
	public double getSumMoneyOneDate(String sPolNo, String sInsuAccNo,
			String sBalaDate) {
		double dLastInsuAccBalaBZ = 0.0;
		String sql = " select sum(Money) from LCInsureAccTrace "
				+ " where polno = '" + "?sPolNo?" + "' and InsuAccNo = '"
				+ "?sInsuAccNo?" + "' and PayDate <= '" + "?sBalaDate?" + "'";
		SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
        sqlbv20.sql(sql);
        sqlbv20.put("sPolNo", sPolNo);
        sqlbv20.put("sInsuAccNo", sInsuAccNo);
        sqlbv20.put("sBalaDate", sBalaDate);

		ExeSQL tExeSQL = new ExeSQL();
		String sLastInsuAccBalaBZ = tExeSQL.getOneValue(sqlbv20);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询帐户结算金额失败!");
			return -1;
		}
		if (sLastInsuAccBalaBZ == null || sLastInsuAccBalaBZ.trim().equals("")) {
			// 没有过保证结算
			sLastInsuAccBalaBZ = "0";
		}
		try {
			dLastInsuAccBalaBZ = Double.parseDouble(sLastInsuAccBalaBZ);
		} catch (Exception e) {
			CError.buildErr(this, "帐户结算金额查询结果错误!" + "错误结果："
					+ sLastInsuAccBalaBZ);
			return -1;
		}

		return dLastInsuAccBalaBZ;
	}

	/** 终止保单的数据 */
	private MMap EndContInfo(String tContNo) {

		MMap tMap = new MMap();
		LCContSchema tLCContSchmea = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "获取保单数据失败!");
			return null;
		}
		tLCContSchmea = tLCContDB.getSchema();
		tLCContSchmea.setAppFlag("4");
		tLCContSchmea.setModifyDate(mCurrentDate);
		tLCContSchmea.setModifyTime(mCurrentTime);
		tMap.put(tLCContSchmea, "UPDATE");

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSet rLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(tContNo);
		tLCPolSet = tLCPolDB.query();

		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int k = 1; k <= tLCPolSet.size(); k++) {
				LCPolSchema rLCPolSchema = new LCPolSchema();
				rLCPolSchema = tLCPolSet.get(k);
				rLCPolSchema.setAppFlag("4");
				rLCPolSchema.setModifyDate(mCurrentDate);
				rLCPolSchema.setModifyTime(mCurrentTime);
				rLCPolSet.add(rLCPolSchema);
			}
		} else {
			CError.buildErr(this, "获取保单数据失败!");
			return null;
		}
		tMap.put(rLCPolSet, "UPDATE");
		return tMap;
	}

	/**
	 * #########################################################################
	 * ################################# 取出满保单年度的日期
	 * 
	 * @param add
	 *            by tangpei 2006-07-31
	 * @param sPolNo
	 *            String
	 * @param sInsuAccNo
	 *            String
	 * @param sBalaDate
	 *            String
	 * @return double
	 */
	public String getOneYearDate(String mPolNo, String eDate) {
		String CValiDateSql = "select CValiDate from lcpol where PolNo='"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
        sqlbv21.sql(CValiDateSql);
        sqlbv21.put("PolNo", mPolNo);

		ExeSQL tExeSQLCValiDate = new ExeSQL();
		String aCValiDate = tExeSQLCValiDate.getOneValue(sqlbv21);

		int tMonth = this.getManyMonth(mPolNo, eDate);
		String tOnedate = PubFun.calDate(aCValiDate, tMonth, "M", "");

		return tOnedate;
	}

	/**
	 * #########################################################################
	 * ################################# 判断是否满一个保单年度的方法
	 * 
	 * @param add
	 *            by tangpei 2006-07-31
	 * @param sPolNo
	 *            String
	 * @param sInsuAccNo
	 *            String
	 * @param sBalaDate
	 *            String
	 * @return double
	 */
	public int getManyMonth(String mPolNo, String eDate) {
		int tOneyear = 0;
		int tMonth = 0;
		String CValiDateSql = "select CValiDate from lcpol where PolNo='"
				+ "?PolNo1?" + "'";
		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
        sqlbv22.sql(CValiDateSql);
        sqlbv22.put("PolNo1", mPolNo);

		ExeSQL tExeSQLCValiDate = new ExeSQL();
		String aCValiDate = tExeSQLCValiDate.getOneValue(sqlbv22);
		tOneyear = PubFun.calInterval(aCValiDate, eDate, "M");
		// 对计算出来的数值进行判断，方法用该数被12整除是否为整数
		int count = tOneyear / 12;
		logger.debug("========被12整除以后的结果========" + count);
		tMonth = count * 12;
		logger.debug("========比较后的结果========" + tMonth);

		return tMonth;
	}

	/**
	 * #########################################################################
	 * ################################# 判断是否满一个保单年度的方法
	 * 
	 * @param add
	 *            by tangpei 2006-07-31
	 * @param sPolNo
	 *            String
	 * @param sInsuAccNo
	 *            String
	 * @param sBalaDate
	 *            String
	 * @return double
	 */
	public String getOneYearFlag(String mPolNo, String eDate) {
		int tOneyear = 0;
		String tFlag = "N";
		String CValiDateSql = "select CValiDate from lcpol where PolNo='"
				+ "?PolNo2?" + "'";
		SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
        sqlbv23.sql(CValiDateSql);
        sqlbv23.put("PolNo2", mPolNo);

		ExeSQL tExeSQLCValiDate = new ExeSQL();
		String aCValiDate = tExeSQLCValiDate.getOneValue(sqlbv23);
		tOneyear = PubFun.calInterval(aCValiDate, eDate, "M");
		if (tOneyear == 0) {
			tFlag = "N";
		} else { // 对计算出来的数值进行判断，方法用该数被12整除是否为整数
			double count = tOneyear / 12;
			double count_a = Math.floor(count);
			logger.debug("========被12整除以后的结果========" + count);
			double sumcount = tOneyear - count_a * 12;
			logger.debug("========比较后的结果========" + sumcount);

			if (sumcount == 0) {
				tFlag = "Y";
			}
			logger.debug("========满保单年度标志========" + tFlag);
		}
		return tFlag;
	}

	/**
	 * 得到tDate在tYear这一年的对应日
	 * 
	 * @param tYear
	 *            所在年度
	 * @param tDate
	 *            日期
	 * @return String : tDate在tYear这一年的对应日
	 */
	private String calDate(int tYear, String tDate) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 如果是2月29日，而其上一年不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 3;
				tDay = 1;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}

		return coDate;
	}

	/**
	 * 闰年校验
	 * 
	 * @param mYear
	 *            年度
	 * @return boolean 闰年:true 平年：false
	 */
	private boolean isLeap(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}

	public static void main(String arg[]) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ManageCom = "86110000";

		InsuAccBala tInsuAccBala = new InsuAccBala();
		VData tVData = new VData();

		// tTransferData.setNameAndValue("BalaDate", "2008-11-30");
		//
		// tVData.add(mGlobalInput);
		// tVData.add(tTransferData);
		//
		//
		//
		// if (!tInsuAccBala.submitData(tVData, ""))
		// {
		// logger.debug("error is==" +
		// tInsuAccBala.mErrors.getErrContent());
		// }

		// 循环进行帐户结算
		String sStartDate = "2008-03-01";
		String sEndDate = "2008-03-25";
		String sPolNo = "210110000000076";
		String sInsuAccNo = "000006";

		String sql = " select * from lminsuaccrate "
				+ " where riskcode = (select trim(riskcode) from lcpol where polno = '"
				+ "?PolNo2?" + "') and InsuAccNo = '" + "?sInsuAccNo?"
				+ "' and baladate >= '" + "?sStartDate?"
				+ "' and rateintv = 'Y' and ratestate='1' ";
		SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
        sqlbv24.sql(sql);
        sqlbv24.put("PolNo2", sPolNo);
        sqlbv24.put("sInsuAccNo", sInsuAccNo);
        sqlbv24.put("sStartDate", sStartDate);

		logger.debug(sql);
		LMInsuAccRateDB tLMInsuAccRateDB = new LMInsuAccRateDB();

		LMInsuAccRateSet tLMInsuAccRateSet = tLMInsuAccRateDB.executeQuery(sqlbv24);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("InsuAccNo", sInsuAccNo);
		tTransferData.setNameAndValue("PolNo", sPolNo);

		String sBalaDate;
		tVData.add(mGlobalInput);
		for (int i = 1; i <= tLMInsuAccRateSet.size(); i++) {
			tInsuAccBala = new InsuAccBala();
			sBalaDate = tLMInsuAccRateSet.get(i).getBalaDate();

			// 判断该账户当天是否已经结算过（防止重复结算插入为0的记录）
			String sql0 = " select 1 from dual where exists "
					+ "(select 'X' from lcinsureacctrace where 1=1"
					+ " and moneytype = 'LX'" + " and paydate = '" + "?sInsuAccNo1?"
					+ "'" + " and InsuAccNo ='" + "?sStartDate1?" + "'"
					+ " and PolNo ='" + "?PolNo3?" + "')";
			SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
	        sqlbv25.sql(sql0);
	        sqlbv25.put("sInsuAccNo1", sInsuAccNo);
	        sqlbv25.put("sStartDate1", sStartDate);
	        sqlbv25.put("PolNo3", sPolNo);
			ExeSQL tExeSQL = new ExeSQL();
			String sHasBala = tExeSQL.getOneValue(sqlbv25);
			if (sHasBala != null && sHasBala.equals("1")) {
				continue; // 当天已经结算过
			}
			if (PubFun.calInterval(sBalaDate, sEndDate, "D") <= 0) // 结算截止日期
			{
				break;
			}

			tTransferData.removeByName("BalaDate");
			tTransferData.setNameAndValue("BalaDate", sBalaDate);
			tVData.add(tTransferData);
			if (!tInsuAccBala.submitData(tVData, "Service")) {
				logger.debug("error is=="
						+ tInsuAccBala.mErrors.getErrContent());
			}
		}
	}
}
