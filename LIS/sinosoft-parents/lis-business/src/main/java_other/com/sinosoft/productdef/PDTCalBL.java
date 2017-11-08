

package com.sinosoft.productdef;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCGetBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LCPremBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolOtherFieldDescDB;
import com.sinosoft.lis.db.PD_LMDutyDB;
import com.sinosoft.lis.db.PD_LMDutyGetDB;
import com.sinosoft.lis.db.PD_LMPolDutyEdorCalDB;
import com.sinosoft.lis.db.PD_LMRiskDB;
import com.sinosoft.lis.db.PD_LMRiskSortDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.PD_LMDutyCtrlSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetAliveSchema;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.schema.PD_LMDutyPaySchema;
import com.sinosoft.lis.schema.PD_LMDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskDutySchema;
import com.sinosoft.lis.schema.PD_LMRiskSortSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCGrpPolBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolOtherFieldDescSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.PD_LMDutyCtrlSet;
import com.sinosoft.lis.vschema.PD_LMDutyGetAliveSet;
import com.sinosoft.lis.vschema.PD_LMDutyGetRelaSet;
import com.sinosoft.lis.vschema.PD_LMDutyPayRelaSet;
import com.sinosoft.lis.vschema.PD_LMDutySet;
import com.sinosoft.lis.vschema.PD_LMPolDutyEdorCalSet;
import com.sinosoft.lis.vschema.PD_LMRiskDutySet;
import com.sinosoft.lis.vschema.PD_LMRiskSet;
import com.sinosoft.lis.vschema.PD_LMRiskSortSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;

/*
 * <p>Title: 保费计算类 </p>
 * <p>Description: 通过传入的保单信息和责任信息构建出保费信息和领取信息 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft</p>
 * @author HST
 * @version 1.0
 * @date 2002-07-01
 */

public class PDTCalBL {
	public static void main(String[] args) {
		
	}

	private boolean xb_flag = false;  //续保特殊处理标记，默认不需要
	private boolean autoCalFloatRateFlag = false; // 是否自动计算浮动费率,如果界面传入浮动费率=
	// ConstRate，自动计算
	private String calType;
	private final double ConstRate = -1; // 如果标明需要从输入的保费保额计算浮动费率，那么界面传入的浮动费率值为常量
	private String currCalMode = "";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        
	// @Field
	private FDate fDate = new FDate();
	/* 处理浮动费率的变量 */
	private double FloatRate = 0; // 浮动费率
	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private String FRateFORMATMODOL = "0.000000000000"; // 浮动费率计算出来后的精确位数
	private double InputAmnt = 0; // 保存界面录入的保额
	private double InputMult = 0; // 保存界面录入的份数
	private double InputPrem = 0; // 保存界面录入的保费
	private CalBase mCalBase; // 计算基础数据

	private PDTCachedRiskInfo mCRI = PDTCachedRiskInfo.getInstance();

	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象
	public CErrors mErrors = new CErrors(); // 错误信息
	private boolean mFlag = false; // 记录Duty的传入方式，true传入没有DutyCode的DutySet
	private DecimalFormat mFRDecimalFormat = new DecimalFormat(FRateFORMATMODOL); // 数字转换对象

	private boolean mGetFlag = false; // 记录Get的传入方式，true传入没有GetDutyCode的GetSet
	boolean mISPlanRiskFlag = false;

	private LCDutySchema mLCDutyBLIn; // 录入的与责任相关的信息
	private LCDutyBLSet mLCDutyBLSet; // 责任信息
	private LCGetSchema mLCGetBLIn; // 录入的与领取相关的信息
	private LCGetBLSet mLCGetBLSet = new LCGetBLSet(); // 领取项信息

	private LCGetBLSet mLCGetSetIn; // 传入的领取信息
	private LCPolBL mLCPolBL = new LCPolBL(); // 保单信息
	private LCPolOtherSchema mLCPolOtherSchema = new LCPolOtherSchema(); // 险种其他信息
	private LCPremBLSet mLCPremBLSet = new LCPremBLSet(); // 保费项信息
	private PD_LMDutyCtrlSet mPD_LMDutyCtrlSet; // 录入控制信息
	private String mOperator = "";
	// 特殊信息传值 add by zhangtao 2005-10-21
	private TransferData mTransferData = new TransferData();

	private boolean noCalFlag = false; // 不需要计算保费保额的标记
	//tongmeng 2010-11-17 modify
	//投连险开发
	private String mAmntCalType = "2"; // 保额计算方法：1，取值为0；2，进行保额计算
	//tongmeng 2010-11029 modify
	//保费多币种标记
	//private String mPremNeedCurrency = "0";//保费多币种标记 0-不需要 1-需要
	//tongmeng 2010-12-02 modify
	//币种折算
	LDExch mLDExch = new LDExch();
	private final double OriginPrem = 0; // 更新时保存原先保单的实际保费

	private final double OriginStandPrem = 0; // 更新时保存原先保单的标准保费
	/* 处理浮动费率的变量 */
	private double tempPrem = 0;
	private String mSpecCalRule = "";
	public PDTCalBL(LCPolBL tLCPolBL, LCDutyBLSet tLCDutyBLSet,
			LCGetBLSet tLCGetBLSet, String calFlag) {
		mLCPolBL.setSchema(tLCPolBL); // 保存界面录入的投保单信息

		// 责任
		if (tLCDutyBLSet.size() == 1
				&& (tLCDutyBLSet.get(1).getDutyCode() == null || tLCDutyBLSet
						.get(1).getDutyCode().equals(""))) {
			mLCDutyBLIn = tLCDutyBLSet.get(1); // 保存界面录入的责任信息-单条
		} else {
			mLCDutyBLIn = new LCDutySchema(); // 无数据
			mLCDutyBLSet = new LCDutyBLSet();
			mLCDutyBLSet.set(tLCDutyBLSet); // 保存界面录入的责任信息-多条
			mFlag = true; // 可选责任为真
		}

		// 领取项
		if (tLCGetBLSet.size() == 1
				&& StrTool.cTrim(tLCGetBLSet.get(1).getGetDutyCode())
						.equals("")) {
			mLCGetBLIn = tLCGetBLSet.get(1); // 保存界面录入的领取项信息-其它字段为空，
			// GetDutyKind字段有值
		} else {
			mLCGetBLIn = new LCGetSchema();
			mLCGetSetIn = new LCGetBLSet();
			mLCGetSetIn.set(tLCGetBLSet);
			mGetFlag = true; // 领取项为真
		}

		calType = calFlag;
	}

	public PDTCalBL(LCPolBL tLCPolBL, LCDutyBLSet tLCDutyBLSet,
			LCGetBLSet tLCGetBLSet, TransferData pTransferData, String calFlag) { // 保全重算专用
		// add
		// by
		// zhangtao
		// 2005
		// -
		// 10
		// -
		// 21
		mTransferData = pTransferData;
		mLCPolBL.setSchema(tLCPolBL); // 保存界面录入的投保单信息

		// 责任
		if (tLCDutyBLSet.size() == 1
				&& (tLCDutyBLSet.get(1).getDutyCode() == null || tLCDutyBLSet
						.get(1).getDutyCode().equals(""))) {
			mLCDutyBLIn = tLCDutyBLSet.get(1); // 保存界面录入的责任信息-单条
		} else {
			mLCDutyBLIn = new LCDutySchema(); // 无数据
			mLCDutyBLSet = new LCDutyBLSet();
			mLCDutyBLSet.set(tLCDutyBLSet); // 保存界面录入的责任信息-多条
			mFlag = true; // 可选责任为真
		}

		// 领取项
		if (tLCGetBLSet.size() == 1
				&& StrTool.cTrim(tLCGetBLSet.get(1).getGetDutyCode())
						.equals("")) {
			mLCGetBLIn = tLCGetBLSet.get(1); // 保存界面录入的领取项信息-其它字段为空，
			// GetDutyKind字段有值
		} else {
			mLCGetBLIn = new LCGetSchema();
			mLCGetSetIn = new LCGetBLSet();
			mLCGetSetIn.set(tLCGetBLSet);
			mGetFlag = true; // 领取项为真
		}

		calType = calFlag;
	}

	public PDTCalBL(LCPolBL tLCPolBL, LCDutyBLSet tLCDutyBLSet, String calFlag) {
		mLCPolBL.setSchema(tLCPolBL); // 保存界面录入的投保单信息
		mLCGetBLIn = new LCGetSchema();

		if (tLCDutyBLSet.size() == 1
				&& (tLCDutyBLSet.get(1).getDutyCode() == null || tLCDutyBLSet
						.get(1).getDutyCode().equals(""))) {
			mLCDutyBLIn = tLCDutyBLSet.get(1); // 保存界面录入的责任信息-单条
		} else {
			mLCDutyBLIn = new LCDutySchema(); // 无数据
			mLCDutyBLSet = new LCDutyBLSet();
			mLCDutyBLSet.set(tLCDutyBLSet); // 保存界面录入的责任信息-多条
			mFlag = true;
			System.out.println("" + mLCDutyBLSet.get(1).getDutyCode());
		}
		calType = calFlag;
	}

	public PDTCalBL(LCPolBL tLCPolBL, LCDutyBLSet tLCDutyBLSet,
			TransferData pTransferData, String calFlag) {
		mTransferData = pTransferData;
		mLCPolBL.setSchema(tLCPolBL); // 保存界面录入的投保单信息
		mLCGetBLIn = new LCGetSchema();

		if (tLCDutyBLSet.size() == 1
				&& (tLCDutyBLSet.get(1).getDutyCode() == null || tLCDutyBLSet
						.get(1).getDutyCode().equals(""))) {
			mLCDutyBLIn = tLCDutyBLSet.get(1); // 保存界面录入的责任信息-单条
		} else {
			mLCDutyBLIn = new LCDutySchema(); // 无数据
			mLCDutyBLSet = new LCDutyBLSet();
			mLCDutyBLSet.set(tLCDutyBLSet); // 保存界面录入的责任信息-多条
			mFlag = true;
			//System.out.println("" + mLCDutyBLSet.get(1).getDutyCode());
		}
		calType = calFlag;
	}

	public PDTCalBL(LCPolBL tLCPolBL, LCGetBLSet tLCGetBLSet, String calFlag) {
		mLCPolBL.setSchema(tLCPolBL);
		mLCDutyBLIn = new LCDutySchema();

		if (tLCGetBLSet.size() == 1
				&& StrTool.cTrim(tLCGetBLSet.get(1).getGetDutyCode())
						.equals("")) {
			mLCGetBLIn = tLCGetBLSet.get(1);
		} else {
			mLCGetBLIn = new LCGetSchema();
			mLCGetSetIn = new LCGetBLSet();
			mLCGetSetIn.set(tLCGetBLSet);
			mGetFlag = true;
		}
		calType = calFlag;
	}

	// @Constructor
	public PDTCalBL(LCPolBL aLCPolBL, String calFlag) {
		mLCPolBL.setSchema(aLCPolBL);
		mLCDutyBLIn = new LCDutySchema();
		mLCGetBLIn = new LCGetSchema();
		calType = calFlag;
	}

	/**
	 * 计算风险保额和基本保额
	 * 
	 * @param tLCDutySchema
	 *            LCDutySchema
	 * @param tPD_LMDutySchema
	 *            PD_LMDutySchema
	 * @return LCDutySchema
	 */
	private LCDutySchema calAmnt(LCDutySchema tLCDutySchema,
			PD_LMDutySchema tPD_LMDutySchema) {
		double tAmnt = -1;
		double tRiskAmnt = -1;
		String tBasicCalCode = tPD_LMDutySchema.getBasicCalCode();
		String tRiskCalCode = tPD_LMDutySchema.getRiskCalCode();
		String tStr = "";

		// 计算
		Calculator mCalculator = new Calculator();
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("GetYearFlag", mCalBase.getGetYearFlag());
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("RnewFlag", mCalBase.getRnewFlag());
		mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("Amnt", mCalBase.getAmnt()); // 计算出来的保额
		mCalculator.addBasicFactor("FRate", mCalBase.getFloatRate());
		mCalculator.addBasicFactor("StandbyFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("StandbyFlag2", mCalBase.getStandbyFlag2());
		mCalculator.addBasicFactor("StandbyFlag3", mCalBase.getStandbyFlag3());
		mCalculator.addBasicFactor("GrpPolNo", mCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("GrpContNo", mCalBase.getGrpContNo());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());
		mCalculator.addBasicFactor("GetRate", mCalBase.getGetRate());
		mCalculator.addBasicFactor("SSFlag", mCalBase.getSSFlag());
		mCalculator.addBasicFactor("PeakLine", mCalBase.getPeakLine());
		mCalculator.addBasicFactor("CalType", mCalBase.getCalType());
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("SecondInsuredNo", mCalBase
				.getSecondInsuredNo());
		mCalculator.addBasicFactor("InsuredNo", mCalBase.getInsuredNo());
		mCalculator.addBasicFactor("DutyCode", mCalBase.getDutyCode()
				.substring(0, 6));
		mCalculator.addBasicFactor("MainPolNo", mCalBase.getMainPolNo());
		mCalculator.addBasicFactor("MAmnt", mCalBase.getMAmnt());
		mCalculator.addBasicFactor("AppAge2", mCalBase.getAppAge2());
		mCalculator.addBasicFactor("ManageCom", mCalBase.getManageCom());
		mCalculator.addBasicFactor("GetStartType", mCalBase.getGetStartType());
		mCalculator.addBasicFactor("PolTypeFlag", mCalBase.getPolTypeFlag());

		// 险种其他信息
		System.out.println("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());
		for (int i = 0; i < mCalBase.getAllOtherParmCount(); i++) {
			String tOtherParmName = mCalBase.getOtherParmName(i);
			String tOtherParmValue = mCalBase
					.getOtherParmVlaueByName(tOtherParmName);
			System.out.println("tOtherParmName==" + tOtherParmName
					+ " tOtherParmValue ==" + tOtherParmValue);
			mCalculator.addBasicFactor(tOtherParmName, tOtherParmValue);
		}
		System.out.println("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());

		// 计算基本保额
		mCalculator.setCalCode(tBasicCalCode);

		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			tAmnt = 0;
		} else {
			tAmnt = Double.parseDouble(tStr);
		}

		// 计算风险保额
		mCalculator.setCalCode(tRiskCalCode);

		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			tRiskAmnt = 0;
		} else {
			tRiskAmnt = Double.parseDouble(tStr);
		}
		if (tLCDutySchema.getCalRule().equals("3")
				|| tLCDutySchema.getCalRule().equals("1")) {
		} else {
			tLCDutySchema.setAmnt(tAmnt);
		}
		if (tLCDutySchema.getCalRule().equals("3")
				|| tLCDutySchema.getCalRule().equals("1")) {
			tLCDutySchema.setRiskAmnt(tLCDutySchema.getAmnt());
		} else {

			tLCDutySchema.setRiskAmnt(tRiskAmnt);
		}
		// System.out.println( tAmnt );
		// System.out.println( tRiskAmnt );
		return tLCDutySchema;
	}

	/**
	 * 从领取项表中取得责任表中部分信息
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 * @param tPD_LMDutySchema
	 *            PD_LMDutySchema
	 * @return LCDutySchema
	 */
	private LCDutySchema calDutyFromGet(LCDutySchema mLCDutySchema,
			PD_LMDutySchema tPD_LMDutySchema) {
		String dutyCode = mLCDutySchema.getDutyCode();

		double sumAmnt = 0;
		Date minGetStartDate = fDate.getDate("3000-01-01");
		int n = mLCGetBLSet.size();
		for (int i = 1; i <= n; i++) {
			if (mLCGetBLSet.get(i).getDutyCode().equals(dutyCode)) {
				LCGetBL mLCGetBL = (LCGetBL) mLCGetBLSet.get(i);

				if (mLCGetBL.getState().substring(0, 1).equals("Y")) {
					sumAmnt += mLCGetBL.getStandMoney();
				}

				Date getStartDate = fDate.getDate(mLCGetBL.getGetStartDate());
				if (minGetStartDate.after(getStartDate)) {
					minGetStartDate = getStartDate;
				}
			}
		}
		mCalBase.setAmnt(sumAmnt);

		// 计算基本保额和风险保额
		mLCDutySchema = this.calAmnt(mLCDutySchema, tPD_LMDutySchema);

		mLCDutySchema.setGetStartDate(fDate.getString(minGetStartDate));

		return mLCDutySchema;
	}

	/**
	 * 从保费项表中取得责任表中部分信息
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 * @param tPD_LMDutySchema
	 *            PD_LMDutySchema
	 * @return LCDutySchema
	 */
	private LCDutySchema calDutyFromPrem(LCDutySchema mLCDutySchema,
			PD_LMDutySchema tPD_LMDutySchema) {
		String dutyCode = mLCDutySchema.getDutyCode();

		double sumPrem = 0;
		Date maxPayEndDate = fDate.getDate("1900-01-01");
		int payIntv = -8;
		int n = mLCPremBLSet.size();
		String tTransDate = this.mLCPolBL.getCValiDate()==null?this.mLCPolBL.getPolApplyDate():this.mLCPolBL.getCValiDate();

		for (int i = 1; i <= n; i++) {
			LCPremBL mLCPremBL = (LCPremBL) mLCPremBLSet.get(i);
			
			if (StrTool.cTrim(mLCPremBL.getDutyCode()).equals(dutyCode)&&mLCPremBL.getPayStartDate()!=null&&mLCPolBL.getCValiDate().equals(mLCPremBL.getPayStartDate())) {
				payIntv = mLCPremBL.getPayIntv();
				//tongmeng 2010-12-02 duty的prem需要从lcprem 的币种折算到险种的币种后再累加.
				double newSumPrem = 0;
				/*
				 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
				 * 如果返回值小于0，则有错误数据
				 * orgitype 传入的币种
				 * destype 需要转换的币种
				 * transdate 转换日期
				 * amnt 转换金额
				 */
				String tCurrency = mLCPremBL.getCurrency()==null?this.mLCPolBL.getCurrency():mLCPremBL.getCurrency();
				
						newSumPrem = this.mLDExch.toOtherCur(tCurrency, mLCPolBL.getCurrency(), tTransDate, mLCPremBL.getStandPrem());

				//sumPrem += mLCPremBL.getStandPrem();
				sumPrem = PubFun.round(sumPrem,2) + PubFun.round(newSumPrem,2);
				Date payEndDate = fDate.getDate(mLCPremBL.getPayEndDate());
				if (maxPayEndDate.before(payEndDate)) {
					maxPayEndDate = payEndDate;
				}
			}
		}

		// 计算缴费年期
		Date birthday = fDate.getDate(mLCPolBL.getInsuredBirthday());
		Date startDate = fDate.getDate(mLCPolBL.getCValiDate());
		int payYears = PubFun.calInterval(startDate, maxPayEndDate, "Y");
		mLCDutySchema.setPayYears(payYears);

		mLCDutySchema.setPayIntv(payIntv);
		// 如果是1和3的话,在proposalBL中处理.注:目前只有1的处理.需要补充3的

		/*
		 * 2008-09-08 zhangzheng 补充对于3-约定费率的处理，此处将保费保存到lcduty中，后面在计算浮动费率时用到
		 */
		if (//mLCDutySchema.getCalRule().equals("3")||
				mLCDutySchema.getCalRule().equals("1")) {

		} else {
			mLCDutySchema.setStandPrem(sumPrem);
			mLCDutySchema.setPrem(sumPrem);
		}

		System.out.println("#####################################");
		System.out.println("#####################################");
		System.out.println("fDate.getString(maxPayEndDate)=="
				+ fDate.getString(maxPayEndDate));
		mLCDutySchema.setPayEndDate(fDate.getString(maxPayEndDate));

		return mLCDutySchema;
	}

	/**
	 * 计算责任表中的其他相关信息
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 * @return LCDutySchema
	 */
	private LCDutySchema calDutyOther(LCDutySchema mLCDutySchema) {

		PD_LMDutySchema tPD_LMDutySchema = mCRI.findDutyByDutyCodeClone(mLCDutySchema
				.getDutyCode().substring(0, 6));

		if (tPD_LMDutySchema == null) {
			// @@错误处理
			this.mErrors.copyAllErrors(mCRI.mErrors);
			mCRI.mErrors.clearErrors();

			CError.buildErr(this, "PD_PD_LMDuty表查询失败!");
			return null;
		}

		if (mLCDutyBLIn.getGetStartType() != null) {
			mLCDutySchema.setGetStartType(mLCDutyBLIn.getGetStartType());
		}

		Date baseDate = null;
		Date compDate = null;
		int interval = 0;
		String unit = null;

		int insuYearIn = mLCDutyBLIn.getInsuYear();
		String insuYearFlagIn = mLCDutyBLIn.getInsuYearFlag();
		int payEndYearIn = mLCDutyBLIn.getPayEndYear();
		String payEndYearFlagIn = mLCDutyBLIn.getPayEndYearFlag();
		int getYearIn = mLCDutyBLIn.getGetYear();
		String getYearFlagIn = mLCDutyBLIn.getGetYearFlag();

		// 保险期限
		Date endDate = null;
		int insuYear = 0;
		int years = 0;
		String insuYearFlag = null;

		// 判断 InsuYear 的取值位置
		// 0 - 录入值
		if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearRela()).equals("0")) { // 取录入值
			if (mLCDutyBLIn.getInsuYear() == 0) {
				// @@错误处理
				CError.buildErr(this, "必须录入保险终止年期!");
				return null;
			}
			if (StrTool.cTrim(mLCDutyBLIn.getInsuYearFlag()).equals("")
					&& StrTool.cTrim(tPD_LMDutySchema.getInsuYearFlag())
							.equals("")) {
				// @@错误处理
				CError.buildErr(this, "必须录入保险终止年期及年期单位!");
				return null;
			}
			insuYear = insuYearIn;
			if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearFlag()).equals("")) {
				insuYearFlag = insuYearFlagIn;
			} else {
				insuYearFlag = tPD_LMDutySchema.getInsuYearFlag();
			}
		}
		// 1 和缴费终止期间的值相同
		if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearRela()).equals("1")) { // 和缴费终止期间的值相同
			if (payEndYearIn != 0) {
				insuYear = payEndYearIn;
			} else {
				insuYear = tPD_LMDutySchema.getPayEndYear();
			}
			if (!StrTool.cTrim(payEndYearFlagIn).equals("")) {
				insuYearFlag = payEndYearFlagIn;
			} else {
				insuYearFlag = tPD_LMDutySchema.getPayEndYearFlag();
			}
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearRela()).equals("2")) { // 和起领期间的值相同
			if (getYearIn != 0) {
				insuYear = getYearIn;
			} else {
				insuYear = tPD_LMDutySchema.getGetYear();
			}
			if (!StrTool.cTrim(getYearFlagIn).equals("")) {
				insuYearFlag = getYearFlagIn;
			} else {
				insuYearFlag = tPD_LMDutySchema.getGetYearFlag();
			}
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearRela()).equals("4")) { // 从描述表取其默认值
			insuYear = tPD_LMDutySchema.getInsuYear();
			insuYearFlag = tPD_LMDutySchema.getInsuYearFlag();
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearRela()).equals("5")) { //更据算法，
			// 将缴费终止期间和默认值相加
			insuYear = tPD_LMDutySchema.getInsuYear() + payEndYearIn;
			insuYearFlag = payEndYearFlagIn;
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getInsuYearRela()).equals("6")) { //更据算法，
			// 将起领期间和默认值相加
			insuYear = tPD_LMDutySchema.getInsuYear() + getYearIn;
			insuYearFlag = getYearFlagIn;
		}
		// 没有差异
		// 计算
		// modify by zhangxing 因为签单时EndDate没有重算。

		// if (mLCDutySchema.getEndDate() == null ||
		// mLCDutySchema.getEndDate().equals(""))
		// {
		baseDate = fDate.getDate(mLCPolBL.getCValiDate());
		unit = insuYearFlag;
		interval = insuYear;
		compDate = null;
		if (unit == null && interval == 0) {
			endDate = fDate.getDate("3000-01-01");
		} else {
			if (StrTool.cTrim(unit).equals("A")) {
				baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
				unit = "Y";
				compDate = fDate.getDate(mLCPolBL.getCValiDate());
			} else {
				baseDate = fDate.getDate(mLCPolBL.getCValiDate());
			}

//			// 根据产品定义判断enddate的取值 0-以计算为准（默认值为0） 1-取计算后加一天
//			// 如果baseDate为空,取团单的生效时间
//			if (baseDate == null || baseDate.equals("null")) {
//				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
//				LCGrpContSet tLCGrpContSet = new LCGrpContSet();
//				String sql = "select * from lcgrpcont where grpcontno='"
//						+ mLCPolBL.getGrpContNo() + "'";
//				tLCGrpContSet = tLCGrpContDB.executeQuery(sql);
//				baseDate = fDate.getDate(tLCGrpContSet.get(1).getCValiDate());
//			}
			/*增加对于续期续保的特殊处理,lcpol.enddate=lcduty.enddate */
			if(xb_flag)
			{
				endDate = PubFun.calxbEndDate(baseDate, interval, unit, compDate);
			}
			else
			{
				endDate = PubFun.calDate(baseDate, interval, unit, compDate);
			}
			
			// 止期算法
			if (StrTool.cTrim(tPD_LMDutySchema.getEndDateCalMode()).equals("0")) { // 更据算法
				// ，
				// 将起领期间和默认值相加
				//endDate = PubFun.calDate(baseDate, interval, unit, compDate);
			}
			if (StrTool.cTrim(tPD_LMDutySchema.getEndDateCalMode()).equals("1")) { // 更据算法
				// ，
				// 将起领期间和默认值相加
				endDate = PubFun.calDate(endDate, 1, "D", compDate);
				System.out.println("%%%%%%%%%1111111111endDate==" + endDate);
			}
			System.out.println("%%%%%%%%%2222222222endDate==" + endDate);

			if (StrTool.cTrim(insuYearFlag).equals("A")) {
				years = PubFun.calInterval(fDate.getDate(mLCPolBL
						.getCValiDate()), endDate, "Y");
			} else {
				years = insuYear;
			}
			mLCDutySchema.setYears(years);
		}

		mLCDutySchema.setEndDate(fDate.getString(endDate));

		// }
		mLCDutySchema.setInsuYearFlag(insuYearFlag.trim());
		mLCDutySchema.setInsuYear(insuYear);

		// 意外责任期限
		Date acciEndDate = null;
		int acciYear = 0;
		String acciYearFlag = null;

		// 判断 acciYear 的取值位置
		if (chkInput(mLCDutySchema.getDutyCode().substring(0, 6), "D",
				"AcciYear")) {
			if (mLCDutyBLIn.getAcciYear() == 0) {
				// @@错误处理
				CError.buildErr(this, "必须录入意外责任终止年期及单位!");
				return null;
			}
			acciYear = mLCDutyBLIn.getAcciYear();
		} else {
			acciYear = tPD_LMDutySchema.getAcciYear();
		}

		// 判断 AcciYearFlag 的取值位置
		if (chkInput(mLCDutySchema.getDutyCode().substring(0, 6), "D",
				"AcciYearFlag")) {
			if (mLCDutyBLIn.getAcciYearFlag() == null) {
				// @@错误处理
				CError.buildErr(this, "必须录入意外责任终止年期标志!");
				return null;
			}
			acciYearFlag = mLCDutyBLIn.getAcciYearFlag();
		} else {
			acciYearFlag = tPD_LMDutySchema.getAcciYearFlag();
		}

		// 计算
		if (StrTool.cTrim(mLCDutySchema.getAcciEndDate()).equals("")
				&& acciYear != 0) {
			baseDate = fDate.getDate(mLCPolBL.getCValiDate());
			unit = acciYearFlag;
			interval = acciYear;
			compDate = null;
			if (!(unit == null && interval == 0)) {
				if (StrTool.cTrim(unit).equals("A")) {
					baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
					unit = "Y";
					compDate = fDate.getDate(mLCPolBL.getCValiDate());
				} else {
					baseDate = fDate.getDate(mLCPolBL.getCValiDate());
				}

				acciEndDate = PubFun
						.calDate(baseDate, interval, unit, compDate);

				mLCDutySchema.setAcciEndDate(fDate.getString(acciEndDate));
			}
		}
		mLCDutySchema.setAcciYearFlag(acciYearFlag);
		mLCDutySchema.setAcciYear(acciYear);

		// 判断 PayEndYear 的取值位置
		int payEndYear = 0;
		String payEndYearFlag = null;
		if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearRela()).equals("0")) { // 取录入值
			if (mLCDutyBLIn.getPayEndYear() == 0) {
				// @@错误处理
				CError.buildErr(this, "必须录入缴费终止年期!");
				return null;
			}
			if (StrTool.cTrim(mLCDutyBLIn.getPayEndYearFlag()).equals("")
					&& StrTool.cTrim(tPD_LMDutySchema.getPayEndYearFlag()).equals(
							"")) {
				CError.buildErr(this, "必须录入缴费终止年期单位!");
				return null;
			}

			payEndYear = payEndYearIn;
			if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearFlag()).equals("")) {
				payEndYearFlag = payEndYearFlagIn;
			} else {
				payEndYearFlag = tPD_LMDutySchema.getPayEndYearFlag();
			}
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearRela()).equals("3")) { // 和保险期间的值相同
			if (insuYearIn != 0) {
				payEndYear = insuYearIn;
			} else {
				payEndYear = tPD_LMDutySchema.getInsuYear();
			}
			if (!StrTool.cTrim(insuYearFlagIn).equals("")) {
				payEndYearFlag = insuYearFlagIn;
			} else {
				payEndYearFlag = tPD_LMDutySchema.getInsuYearFlag();
			}
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearRela()).equals("2")) { // 和起领期间的值相同
			if (getYearIn != 0) {
				payEndYear = getYearIn;
			} else {
				payEndYear = tPD_LMDutySchema.getGetYear();
			}
			if (!StrTool.cTrim(getYearFlagIn).equals("")) {
				payEndYearFlag = getYearFlagIn;
			} else {
				payEndYearFlag = tPD_LMDutySchema.getGetYearFlag();
			}
			// payEndYear = getYearIn;
			// payEndYearFlag = getYearFlagIn;
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearRela()).equals("4")) { // 从描述表取其默认值
			payEndYear = tPD_LMDutySchema.getPayEndYear();
			payEndYearFlag = tPD_LMDutySchema.getPayEndYearFlag();
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearRela()).equals("7")) { // 更据算法
			// ，
			// 将保险期间和默认值相加
			payEndYear = tPD_LMDutySchema.getPayEndYear() + insuYearIn;
			payEndYearFlag = insuYearFlagIn;
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getPayEndYearRela()).equals("6")) { // 更据算法
			// ，
			// 将起领期间和默认值相加
			payEndYear = tPD_LMDutySchema.getPayEndYear() + getYearIn;
			payEndYearFlag = getYearFlagIn;
		}
		mLCDutySchema.setPayEndYearFlag(payEndYearFlag);
		mLCDutySchema.setPayEndYear(payEndYear);

		// 判断 GetYear 的取值位置
		int getYear = 0;
		String getYearFlag = null;
		if (StrTool.cTrim(tPD_LMDutySchema.getGetYearRela()).equals("0")) { // 取录入值

			if (mLCDutyBLIn.getGetYear() == 0) {
				// @@错误处理
				CError.buildErr(this, "必须录入起领年期!");
				return null;
			}
			if (StrTool.cTrim(tPD_LMDutySchema.getGetYearFlag()).equals("")
					&& StrTool.cTrim(mLCDutyBLIn.getGetYearFlag()).equals("")) {
				CError.buildErr(this, "必须录入起领年期单位!");
				return null;
			}
			if (StrTool.cTrim(mLCDutyBLIn.getGetYearFlag()).equals("")) {
				getYearFlag = tPD_LMDutySchema.getGetYearFlag();
			} else {
				getYearFlag = getYearFlagIn;
			}
			getYear = getYearIn;

		}
		if (StrTool.cTrim(tPD_LMDutySchema.getGetYearRela()).equals("3")) { // 和保险期间的值相同
			if (insuYearIn != 0) {
				getYear = insuYearIn;
			} else {
				getYear = tPD_LMDutySchema.getInsuYear();
			}
			if (!StrTool.cTrim(insuYearFlagIn).equals("")) {
				getYearFlag = insuYearFlagIn;
			} else {
				getYearFlag = tPD_LMDutySchema.getInsuYearFlag();
			}

			// getYear = insuYearIn;
			// getYearFlag = insuYearFlagIn;
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getGetYearRela()).equals("1")) { // 和交费终止期间的值相同
			if (payEndYearIn != 0) {
				getYear = payEndYearIn;
			} else {
				getYear = tPD_LMDutySchema.getInsuYear();
			}
			if (!StrTool.cTrim(payEndYearFlagIn).equals("")) {
				getYearFlag = payEndYearFlagIn;
			} else {
				getYearFlag = tPD_LMDutySchema.getPayEndYearFlag();
			}

			// getYear = payEndYearIn;
			// getYearFlag = payEndYearFlagIn;
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getGetYearRela()).equals("4")) { // 从描述表取其默认值
			getYear = tPD_LMDutySchema.getGetYear();
			getYearFlag = tPD_LMDutySchema.getGetYearFlag();
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getGetYearRela()).equals("7")) { //更据算法，
			// 将保险期间和默认值相加
			getYear = tPD_LMDutySchema.getGetYear() + insuYearIn;
			getYearFlag = insuYearFlagIn;
		}
		if (StrTool.cTrim(tPD_LMDutySchema.getGetYearRela()).equals("5")) { //更据算法，
			// 将交费终止期间和默认值相加
			getYear = tPD_LMDutySchema.getGetYear() + payEndYearIn;
			getYearFlag = payEndYearFlagIn;
		}
		mLCDutySchema.setGetYearFlag(getYearFlag);
		mLCDutySchema.setGetYear(getYear);

		return mLCDutySchema;
	}

	/*续保件，如果主险生效日期为2-29,当前为2-28,且当前交至年度为闰年的情况下，需要特殊处理*/
	private boolean get_xbflag(LCPolBL xLCPolBL)
	{
		
			return false;
	}
	/**
	 * 判断是否为闰年 XinYQ added on 2006-09-25
	 */
	public static boolean isLeapYear(int nYear) {
		boolean ResultLeap = false;
		ResultLeap = (nYear % 400 == 0) | (nYear % 100 != 0) & (nYear % 4 == 0);
		return ResultLeap;
	}
	/**
	 * 计算领取项表中的其他值
	 * 
	 * @param mLCGetSchema
	 *            LCGetSchema
	 * @param tPD_LMDutyGetSchema
	 *            PD_LMDutyGetSchema
	 * @param tLCDutySchema
	 *            LCDutySchema
	 * @return LCGetSchema
	 */
	private LCGetSchema calGetOther(LCGetSchema mLCGetSchema,
			PD_LMDutyGetSchema tPD_LMDutyGetSchema, LCDutySchema tLCDutySchema) {
		mLCGetSchema.setLiveGetType(tPD_LMDutyGetSchema.getType());
		mLCGetSchema.setNeedAcc(tPD_LMDutyGetSchema.getNeedAcc());
		mLCGetSchema.setCanGet(tPD_LMDutyGetSchema.getCanGet());
		mLCGetSchema.setNeedCancelAcc(tPD_LMDutyGetSchema.getNeedCancelAcc());

		// DiscntFlag 1-只能现金领取, 0-可以选择。
		if (tPD_LMDutyGetSchema.getDiscntFlag() == null) { // 如果是空，默认是0
			tPD_LMDutyGetSchema.setDiscntFlag("0");
		}
		if (tPD_LMDutyGetSchema.getDiscntFlag().equals("1")) { // 如果是1，则领取项必须是现金
			// 可以查找该行，前面已使用,如果用于接收界面输入的领取方式，若描述表中是1，则将该值覆盖。
			mLCGetSchema.setGetMode("1");
		} else {
			mLCGetSchema.setGetMode(mLCPolBL.getLiveGetMode());
		}

		PD_LMDutyGetAliveSchema tPD_LMDutyGetAliveSchema = new PD_LMDutyGetAliveSchema();
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getType()).equals("0")) { // 生存领取类型
			// 给付责任类型GetDutyKind
			if (chkInput(mLCGetSchema.getGetDutyCode(), "G", "GetDutyKind")
					&& mLCGetBLIn.getGetDutyKind() != null
					&& !"".equals(mLCGetBLIn.getGetDutyKind())) {
				mLCGetSchema.setGetDutyKind(mLCGetBLIn.getGetDutyKind());
				PD_LMDutyGetAliveSchema tempPD_LMDutyGetAliveSchema = mCRI
						.findDutyGetAliveClone(mLCGetSchema.getGetDutyCode(),
								mLCGetSchema.getGetDutyKind());

				if (tempPD_LMDutyGetAliveSchema == null) {
					// @@错误处理
					this.mErrors.copyAllErrors(mCRI.mErrors);
					mCRI.mErrors.clearErrors();

					CError.buildErr(this, "PD_LMDutyGetAlive表查询失败!");
					return null;
				}
				tPD_LMDutyGetAliveSchema.setSchema(tempPD_LMDutyGetAliveSchema);
			} else
			// tongmeng 2008-08-18 注释
			// 没有意义
			 if (!chkInput(mLCGetSchema.getGetDutyCode(), "G",
			 "GetDutyKind"))
			{
				PD_LMDutyGetAliveSet tPD_LMDutyGetAliveSet = mCRI
						.findDutyGetAliveByGetDutyCodeClone(mLCGetSchema
								.getGetDutyCode());

				if (tPD_LMDutyGetAliveSet == null) {
					// @@错误处理
					this.mErrors.copyAllErrors(mCRI.mErrors);
					mCRI.mErrors.clearErrors();

					CError.buildErr(this, "PD_LMDutyGetAlive表查询失败!");
					return null;
				}
				tPD_LMDutyGetAliveSchema = tPD_LMDutyGetAliveSet.get(1);
				mLCGetSchema.setGetDutyKind(tPD_LMDutyGetAliveSchema
						.getGetDutyKind());
			} // end of if

			// 复制描述数据
			if (mLCGetSchema.getGetDutyKind() != null
					&& !"".equals(mLCGetSchema.getGetDutyKind())) {
				tPD_LMDutyGetSchema.setGetIntv(tPD_LMDutyGetAliveSchema.getGetIntv());
				tPD_LMDutyGetSchema.setDefaultVal(tPD_LMDutyGetAliveSchema
						.getDefaultVal());
				tPD_LMDutyGetSchema.setCalCode(tPD_LMDutyGetAliveSchema.getCalCode());
				tPD_LMDutyGetSchema.setCnterCalCode(tPD_LMDutyGetAliveSchema
						.getCnterCalCode());
				tPD_LMDutyGetSchema.setOthCalCode(tPD_LMDutyGetAliveSchema
						.getOthCalCode());
				tPD_LMDutyGetSchema.setGetYear(tPD_LMDutyGetAliveSchema
						.getGetStartPeriod());
				tPD_LMDutyGetSchema.setGetYearFlag(tPD_LMDutyGetAliveSchema
						.getGetStartUnit());
				tPD_LMDutyGetSchema.setStartDateCalRef(tPD_LMDutyGetAliveSchema
						.getStartDateCalRef());
				tPD_LMDutyGetSchema.setStartDateCalMode(tPD_LMDutyGetAliveSchema
						.getStartDateCalMode());
				tPD_LMDutyGetSchema.setMinGetStartPeriod(tPD_LMDutyGetAliveSchema
						.getMinGetStartPeriod());
				tPD_LMDutyGetSchema.setGetEndPeriod(tPD_LMDutyGetAliveSchema
						.getGetEndPeriod());
				tPD_LMDutyGetSchema.setGetEndUnit(tPD_LMDutyGetAliveSchema
						.getGetEndUnit());
				tPD_LMDutyGetSchema.setEndDateCalRef(tPD_LMDutyGetAliveSchema
						.getEndDateCalRef());
				tPD_LMDutyGetSchema.setEndDateCalMode(tPD_LMDutyGetAliveSchema
						.getEndDateCalMode());
				tPD_LMDutyGetSchema.setMaxGetEndPeriod(tPD_LMDutyGetAliveSchema
						.getMaxGetEndPeriod());
				tPD_LMDutyGetSchema.setAddFlag(tPD_LMDutyGetAliveSchema.getAddFlag());
				tPD_LMDutyGetSchema.setUrgeGetFlag(tPD_LMDutyGetAliveSchema
						.getUrgeGetFlag());
				// tPD_LMDutyGetSchema.setDiscntFlag(
				// tPD_LMDutyGetAliveSchema.getDiscntFlag() );
			}
		}
		// end of if

		mLCGetSchema.setUrgeGetFlag(tPD_LMDutyGetSchema.getUrgeGetFlag());
		if (mLCGetSchema.getGetIntv() == 0) {
			mLCGetSchema.setGetIntv(tPD_LMDutyGetSchema.getGetIntv());
		}
		if (mLCGetSchema.getGetLimit() == 0.0) {
			mLCGetSchema.setGetLimit(tPD_LMDutyGetSchema.getGetLimit());
		}
		if (mLCGetSchema.getGetRate() == 0.0) {
			mLCGetSchema.setGetRate(tPD_LMDutyGetSchema.getGetRate());
		}
		String s = mLCGetSchema.getState();
		if (s != null) {
			s = tPD_LMDutyGetSchema.getAddAmntFlag() + s.substring(1, s.length());
		} else {
			s = tPD_LMDutyGetSchema.getAddAmntFlag();
		}

		mLCGetSchema.setState(s);
		mLCGetSchema.setGetEndState("0");

		// 递增率
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getAddFlag()).equals("Y")) {
			if (chkInput(mLCGetSchema.getGetDutyCode(), "G", "AddRate")) {
				mLCGetSchema.setAddRate(mLCGetBLIn.getAddRate());
			} else {
				mLCGetSchema.setAddRate(tPD_LMDutyGetAliveSchema.getAddValue());
			}
		}

		// 准备计算基础数据部分
		mCalBase.setGetDutyKind(mLCGetSchema.getGetDutyKind());
		mCalBase.setAddRate(mLCGetSchema.getAddRate());

		// 计算日期
		Date baseDate = null;
		Date compDate = null;
		int interval = 0;
		String unit = null;
		Date getStartDate = null;
		Date getEndDate = null;

		// 计算领取开始日期
		String getStartType = null;
		String getYearFlag = null;
		int getYear = 0;

		// 判断 getYear 的取值位置
		if (chkInput(mLCGetSchema.getGetDutyCode(), "G", "GetYear")) {
			if (mLCDutyBLIn.getGetYear() == 0) {
				// @@错误处理
				CError.buildErr(this, "必须录入开始领取年期!");
				return null;
			}
			getYear = mLCDutyBLIn.getGetYear();
		} else {
			getYear = tPD_LMDutyGetSchema.getGetYear();
		}

		// 判断 getYearFlag 的取值位置
		if (chkInput(mLCGetSchema.getGetDutyCode(), "G", "GetYearFlag")) {
			if (mLCDutyBLIn.getGetYearFlag() == null) {
				// @@错误处理
				CError.buildErr(this, "必须录入开始领取年期标志!");
				return null;
			}
			getYearFlag = mLCDutyBLIn.getGetYearFlag();
		} else {
			getYearFlag = tPD_LMDutyGetSchema.getGetYearFlag();
		}

		// 判断 getStartType 的取值位置
		if (chkInput(mLCGetSchema.getGetDutyCode(), "G", "GetStartType")) {
			if (mLCDutyBLIn.getGetStartType() == null) {
				//特殊处理 modify by liuqh 2008-12-11
				//mLCDutyBLIn.setGetStartType("");
				// modify by sunsx 08-12-16 放开对领取日期类型的数据库的校验,保全时提供领取类型的选择
				// @@错误处理
				CError.buildErr(this, "必须录入领取日期计算类型!");
				return null;
			}
			getStartType = mLCDutyBLIn.getGetStartType();
		} else {
			if(mLCDutyBLIn.getGetStartType() == null){
				
				getStartType = tPD_LMDutyGetSchema.getStartDateCalRef();
				
			}else {
				getStartType = mLCDutyBLIn.getGetStartType();
			}
		}
		//tongmeng 2008-11-12 modify
		//lcduty 保存生存领取的 GetStartType
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getType()).equals("0")) {
			tLCDutySchema.setGetStartType(getStartType);
		}
		// 计算
		if (StrTool.cTrim(getYearFlag).equals("A")) {
			baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			unit = "Y";

			if (StrTool.cTrim(getStartType).equals("S")) { // 起保日期对应日
				compDate = fDate.getDate(mLCPolBL.getCValiDate());
			}
			if (StrTool.cTrim(getStartType).equals("B")) { // 生日对应日
				compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			}
			if (StrTool.cTrim(getStartType).equals("F")) { // 生日对应日 后的1月1日
				compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			}
		} else {
			baseDate = fDate.getDate(mLCPolBL.getCValiDate());
			unit = getYearFlag;
		}
		interval = getYear;

		if (baseDate == null || baseDate.equals("null")) {
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			LCGrpContSet tLCGrpContSet = new LCGrpContSet();
			String sql = "select * from lcgrpcont where grpcontno='"
					+ mLCPolBL.getGrpContNo() + "'";
			tLCGrpContSet = tLCGrpContDB.executeQuery(sql);
			baseDate = fDate.getDate(tLCGrpContSet.get(1).getCValiDate());
		}
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getStartDateCalMode()).equals("3")) { // 取保险终止日期
			mLCGetSchema.setGetStartDate(tLCDutySchema.getEndDate());
		}
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getStartDateCalMode()).equals("0")) { // 取计算结果
			getStartDate = PubFun.calDate(baseDate, interval, unit, compDate);
			mLCGetSchema.setGetStartDate(fDate.getString(getStartDate));
			if (StrTool.cTrim(getStartType).equals("F")) {
				System.out.println("getstartdate:"
						+ fDate.getString(getStartDate));
				System.out.println("getstartdate:"
						+ fDate.getString(getStartDate).substring(4));
				if (!fDate.getString(getStartDate).substring(5).equals("01-01")) {
					getStartDate = PubFun.calDate(baseDate, interval + 1, unit,
							compDate);
					mLCGetSchema.setGetStartDate(fDate.getString(getStartDate)
							.substring(0, 4)
							+ "-01-01");
				}
			}
		}

		mLCGetSchema.setGettoDate(mLCGetSchema.getGetStartDate());
		// System.out.println("getStartDate: " + mLCGetSchema.getGetStartDate()
		// );
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getType()).equals("0")) { // 只有生存领取年金类型准备该计算要素
			// 准备计算基础数据部分
			mCalBase.setGetYear(getYear);
			mCalBase.setGetYearFlag(getYearFlag);
		}
		// 计算领取终止日期
		System.out.println("********tPD_LMDutyGetSchema.getEndDateCalMode()=="
				+ tPD_LMDutyGetSchema.getEndDateCalMode());
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalMode()).equals("3")) { // 取保险终止日期
			mLCGetSchema.setGetEndDate(tLCDutySchema.getEndDate());
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("%%%%%%%%%%%%%%mLCGetSchema.getGetEndDate()");
		}
		System.out.println("1111%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("2222%%%%%%%%%%%%%%mLCGetSchema.getGetEndDate()");

		if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalMode()).equals("0")) { // 取计算的值
			if (StrTool.cTrim(tPD_LMDutyGetSchema.getGetEndUnit()).equals("A")) {
				baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
				unit = "Y";
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"B")) {
					compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
				}
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"S")) {
					compDate = fDate.getDate(mLCPolBL.getCValiDate());
				}
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"C")) { // 止领日期计算,起始参照为领取日期
					compDate = fDate.getDate(mLCGetSchema.getGetStartDate());
				}
			} else {
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"C")) { // 止领日期计算,起始参照为领取日期
					baseDate = fDate.getDate(mLCGetSchema.getGetStartDate());
					unit = tPD_LMDutyGetSchema.getGetEndUnit();
				} else {
					baseDate = fDate.getDate(mLCPolBL.getCValiDate());
					unit = tPD_LMDutyGetSchema.getGetEndUnit();
				}
			}
			interval = tPD_LMDutyGetSchema.getGetEndPeriod();
			getEndDate = PubFun.calDate(baseDate, interval, unit, compDate);
			mLCGetSchema.setGetEndDate(fDate.getString(getEndDate));
		}
		System.out.println("#############################################");
		System.out.println("getEndDate: " + mLCGetSchema.getGetEndDate());
		// 计算领取终止日期
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalMode()).equals("4")) { // 取保险终止日期
			if (StrTool.cTrim(tPD_LMDutyGetSchema.getGetEndUnit()).equals("A")) {
				baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
				unit = "Y";
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"B")) {
					compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
				}
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"S")) {
					compDate = fDate.getDate(mLCPolBL.getCValiDate());
				}
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"C")) { // 止领日期计算,起始参照为领取日期
					compDate = fDate.getDate(mLCGetSchema.getGetStartDate());
				}
			} else {
				if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalRef()).equals(
						"C")) { // 止领日期计算,起始参照为领取日期
					baseDate = fDate.getDate(mLCGetSchema.getGetStartDate());
					unit = tPD_LMDutyGetSchema.getGetEndUnit();
				} else {
					baseDate = fDate.getDate(mLCPolBL.getCValiDate());
					unit = tPD_LMDutyGetSchema.getGetEndUnit();
				}
				interval = tLCDutySchema.getGetYear();
				getEndDate = PubFun.calDate(baseDate, interval, unit, compDate);
				mLCGetSchema.setGetEndDate(fDate.getString(getEndDate));
			}

			interval = tLCDutySchema.getGetYear();
			getEndDate = PubFun.calDate(baseDate, interval, unit, compDate);
			mLCGetSchema.setGetEndDate(fDate.getString(getEndDate));
		}
		// 增加第五算法
		if (StrTool.cTrim(tPD_LMDutyGetSchema.getEndDateCalMode()).equals("5")) { // 取保险终止日期

			baseDate = fDate.getDate(mLCGetSchema.getGetStartDate());
			unit = tPD_LMDutyGetSchema.getGetEndUnit();

			interval = tPD_LMDutyGetSchema.getGetEndPeriod();
			getEndDate = PubFun.calDate(baseDate, interval, unit, compDate);
			mLCGetSchema.setGetEndDate(fDate.getString(getEndDate));
		}

		return mLCGetSchema;
	}

	/**
	 * 计算领取项表中实际领取的值
	 * 
	 * @param mLCGetSchema
	 *            LCGetSchema
	 * @param tLCDutySchema
	 *            LCDutySchema
	 * @param tPD_LMDutyGetSchema
	 *            PD_LMDutyGetSchema
	 * @param calFlag
	 *            String
	 * @return double
	 */
	private double calGetValue(LCGetSchema mLCGetSchema,
			LCDutySchema tLCDutySchema, PD_LMDutyGetSchema tPD_LMDutyGetSchema,
			String calFlag) {
		double mValue = -1;

		String mCalCode = null;
		if (StrTool.cTrim(calFlag).equals("P")) {
			mCalCode = tPD_LMDutyGetSchema.getCalCode(); // 保费算保额
		}
		if (StrTool.cTrim(calFlag).equals("G")) {
			mCalCode = tPD_LMDutyGetSchema.getCnterCalCode(); // 保额算保费
		}
		if (StrTool.cTrim(calFlag).equals("O")) {
			mCalCode = tPD_LMDutyGetSchema.getOthCalCode(); // 其他算保费
		}
		if (StrTool.cTrim(calFlag).equals("A")
				&& tLCDutySchema.getStandPrem() != 0.0) {
			mCalCode = tPD_LMDutyGetSchema.getCalCode(); // 保费、保额互算
		}
		if (StrTool.cTrim(calFlag).equals("A")
				&& tLCDutySchema.getAmnt() != 0.0) {
			mCalCode = tPD_LMDutyGetSchema.getCnterCalCode(); // 保费、保额互算
		}
		if (StrTool.cTrim(calFlag).equals("I")) {
			mCalCode = tPD_LMDutyGetSchema.getCnterCalCode(); // 录入保费保额
		}

		// 取默认值
		if (StrTool.cTrim(mCalCode).equals("")) {
			mValue = tPD_LMDutyGetSchema.getDefaultVal();
			return mValue;
		}

		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("GetYearFlag", mCalBase.getGetYearFlag());
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("RnewFlag", mCalBase.getRnewFlag());
		mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("FRate", mCalBase.getFloatRate());
		mCalculator.addBasicFactor("GetDutyKind", mCalBase.getGetDutyKind());
		mCalculator.addBasicFactor("StandbyFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("StandbyFlag2", mCalBase.getStandbyFlag2());
		mCalculator.addBasicFactor("StandbyFlag3", mCalBase.getStandbyFlag3());
		mCalculator.addBasicFactor("GrpPolNo", mCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("GrpContNo", mCalBase.getGrpContNo());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());
		mCalculator.addBasicFactor("GetRate", mCalBase.getGetRate());
		mCalculator.addBasicFactor("SSFlag", mCalBase.getSSFlag());
		mCalculator.addBasicFactor("PeakLine", mCalBase.getPeakLine());
		mCalculator.addBasicFactor("CalType", mCalBase.getCalType());
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("SecondInsuredNo", mCalBase
				.getSecondInsuredNo());
		mCalculator.addBasicFactor("InsuredNo", mCalBase.getInsuredNo());
		mCalculator.addBasicFactor("DutyCode", mCalBase.getDutyCode()
				.substring(0, 6));
		mCalculator.addBasicFactor("MainPolNo", mCalBase.getMainPolNo());
		mCalculator.addBasicFactor("MAmnt", mCalBase.getMAmnt());
		mCalculator.addBasicFactor("AppAge2", mCalBase.getAppAge2());
		mCalculator.addBasicFactor("ManageCom", mCalBase.getManageCom());
		mCalculator.addBasicFactor("GetStartType", mCalBase.getGetStartType());
		mCalculator.addBasicFactor("PolTypeFlag", mCalBase.getPolTypeFlag());
		mCalculator.addBasicFactor("AppntAppAge", mCalBase.getAppntAppAge());

		System.out.println("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());
		for (int i = 0; i < mCalBase.getAllOtherParmCount(); i++) {
			String tOtherParmName = mCalBase.getOtherParmName(i);
			String tOtherParmValue = mCalBase
					.getOtherParmVlaueByName(tOtherParmName);
			System.out.println("tOtherParmName==" + tOtherParmName
					+ " tOtherParmValue ==" + tOtherParmValue);
			mCalculator.addBasicFactor(tOtherParmName, tOtherParmValue);
		}
		System.out.println("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());

		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Double.parseDouble(tStr);
		}

		// System.out.println(mValue);
		return mValue;
	}

	/**
	 * 计算一个责任下的所有信息
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 * @return LCDutySchema
	 */
	private LCDutySchema calOneDuty(LCDutySchema mLCDutySchema) {
		String dutyCode = mLCDutySchema.getDutyCode();
		String calMode = "";

		// 如果界面没有录入浮动费率，此时mLCDutySchema的FloatRate=0，那么在后面的程序中，
		// 会将mLCPolBL的FloatRate字段置为1
		// 保存浮动费率,录入的保费，保额:用于后面的浮动费率的计算
		FloatRate = mLCDutySchema.getFloatRate();

		InputPrem = mLCDutySchema.getPrem();
		InputAmnt = mLCDutySchema.getAmnt();
		InputMult = mLCDutySchema.getMult();

	

		// 保存计算方向
		String calRule = mLCDutySchema.getCalRule();
		// 0-表定费率1-约定费率2-表定费率折扣3-约定保费保额
		if (calRule == null || calRule.equals("")) {
			// 默认为表定费率
			mLCDutySchema.setCalRule("0");
		}
		// 如果是统一费率，需要去默认值里取出统一费率的值,算出保费保额
		else if (calRule.equals("1")) {
			autoCalFloatRateFlag = true;
		} else if (calRule.equals("2")) {
			// mLCDutySchema.setCalRule("2");//
		} else if (calRule.equals("3")) {
			if (mLCDutySchema.getPrem() <= 0) {
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError.buildErr(this, "您选择的计算规则是约定费率，请输入保费！");
				return mLCDutySchema;

			}
			autoCalFloatRateFlag = true;
			FloatRate = -1;
		}

		// 如果界面录入浮动费率=-1，则计算浮动费率的标记为真
		if (FloatRate == ConstRate) {
			autoCalFloatRateFlag = true;

			// 将保单中的该子段设为1，后面描述算法中计算
			mLCDutySchema.setFloatRate(1);
		}
		//tongmeng 2009-05-13 modify
		//对团单特殊浮动费率的处理
		//商补医疗
		mSpecCalRule = calRule;
		// 浮动费率处理END

		// 准备计算基础数据部分
		mCalBase = new CalBase();
		mCalBase.setCalType(calType);
		// ????泰康用的getPrem,需要看一下差异再做合并
		mCalBase.setPrem(mLCDutySchema.getStandPrem());
		// mCalBase.setPrem( mLCDutySchema.getPrem() );
		mCalBase.setGet(mLCDutySchema.getAmnt());
		mCalBase.setMult(mLCDutySchema.getMult());
		// 份数：按份卖的险种保存份数，其他险种存0。为了保证000301、000302算法的正确性，当份数为0时，该字段赋值为1。
		if (mLCDutySchema.getMult() == 0) {
			mCalBase.setMult(1);
		}
		mCalBase.setYears(mLCDutySchema.getYears());
		mCalBase.setInsuYear(mLCDutySchema.getInsuYear());
		mCalBase.setInsuYearFlag(mLCDutySchema.getInsuYearFlag());
		mCalBase.setAppAge(mLCPolBL.getInsuredAppAge());
		mCalBase.setSex(mLCPolBL.getInsuredSex());
		mCalBase.setJob(mLCPolBL.getOccupationType());
		mCalBase.setCount(mLCPolBL.getInsuredPeoples());
		mCalBase.setPolNo(mLCPolBL.getPolNo());
		mCalBase.setGrpContNo(mLCPolBL.getGrpContNo());
		mCalBase.setCurrency(mLCPolBL.getCurrency());//币种信息
	
		mCalBase.setContNo(mLCPolBL.getContNo());
		// System.out.println("合同号===="+mLCPolBL.getContNo());
		mCalBase.setStandbyFlag1(mLCDutySchema.getStandbyFlag1());
		mCalBase.setStandbyFlag2(mLCDutySchema.getStandbyFlag2());
		mCalBase.setStandbyFlag3(mLCDutySchema.getStandbyFlag3());
		mCalBase.setGrpPolNo(mLCPolBL.getGrpPolNo());
		mCalBase.setGrpContNo(mLCPolBL.getGrpContNo());
		mCalBase.setPolTypeFlag(mLCPolBL.getPolTypeFlag());
		mCalBase.setGetStartType(mLCDutySchema.getGetStartType());
		mCalBase.setGetLimit(mLCDutySchema.getGetLimit());
		mCalBase.setGetRate(mLCDutySchema.getGetRate());
		mCalBase.setSSFlag(mLCDutySchema.getSSFlag());
		mCalBase.setPeakLine(mLCDutySchema.getPeakLine());
		mCalBase.setCValiDate(mLCPolBL.getCValiDate());
		mCalBase.setDutyCode(mLCDutySchema.getDutyCode().substring(0, 6));
		mCalBase.setInsuredNo(mLCPolBL.getInsuredNo());
		mCalBase.setMainPolNo(mLCPolBL.getMainPolNo());
		mCalBase.setManageCom(mLCPolBL.getManageCom());

		System.out.println("---------getlimit:" + mLCDutySchema.getGetLimit()
				+ "  getrate:" + mLCDutySchema.getGetRate());

		if (mLCDutySchema.getFloatRate() == 0.0) {
			mLCDutySchema.setFloatRate(1);
		}
		System.out.println("现在的浮动费率是： "+mLCDutySchema.getFloatRate());
		FloatRate = mLCDutySchema.getFloatRate(); // 20050926
		//mCalBase.setFloatRate(mLCDutySchema.getFloatRate());
		mCalBase.setFloatRate(1);

		int tRnewFlag = -8;
		if (mLCPolBL.getRenewCount() == 0) {
			tRnewFlag = 0;
		}
		if (mLCPolBL.getRenewCount() > 0) {
			tRnewFlag = 1;
		}
		mCalBase.setRnewFlag(tRnewFlag);

		PD_LMDutySchema tPD_LMDutySchema = mCRI.findDutyByDutyCodeClone(dutyCode
				.substring(0, 6));

		if (tPD_LMDutySchema == null) {
			// @@错误处理
			this.mErrors.copyAllErrors(mCRI.mErrors);
			mCRI.mErrors.clearErrors();

			CError.buildErr(this, "PD_LMDuty表查询失败!");
			return mLCDutySchema;
		}
		mCalBase.setVPU("" + tPD_LMDutySchema.getVPU());
		if (mLCDutySchema.getPayEndYearFlag() == null) {
			mLCDutySchema.setPayEndYearFlag(tPD_LMDutySchema.getPayEndYearFlag());
		}
		if (mLCDutySchema.getGetYearFlag() == null) {
			mLCDutySchema.setGetYearFlag(tPD_LMDutySchema.getGetYearFlag());
		}
		if (mLCDutySchema.getInsuYearFlag() == null) {
			mLCDutySchema.setInsuYearFlag(tPD_LMDutySchema.getInsuYearFlag());
		}
		if (mLCDutySchema.getAcciYearFlag() == null) {
			mLCDutySchema.setAcciYearFlag(tPD_LMDutySchema.getAcciYearFlag());
		}

		if (calType == null || calType.equals("")) {
			if (tPD_LMDutySchema.getCalMode() == null) {
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError.buildErr(this, "PD_LMDuty表CalMode没有定义!");
				return mLCDutySchema;

			}
			calMode = tPD_LMDutySchema.getCalMode();
		} else {
			PD_LMPolDutyEdorCalDB tPD_LMPolDutyEdorCalDB = new PD_LMPolDutyEdorCalDB();
			tPD_LMPolDutyEdorCalDB.setRiskCode(mLCPolBL.getRiskCode());
			tPD_LMPolDutyEdorCalDB.setEdorType(calType);
			PD_LMPolDutyEdorCalSet tPD_LMPolDutyEdorCalSet = tPD_LMPolDutyEdorCalDB
					.query();
			if (tPD_LMPolDutyEdorCalDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(tPD_LMPolDutyEdorCalDB.mErrors);
				CError.buildErr(this, "查询险种责任保全计算信息失败！");
				return null;
			}
			if (tPD_LMPolDutyEdorCalSet.size() > 0) {
				calMode = tPD_LMPolDutyEdorCalSet.get(1).getCalMode(); // 获取保全重算的计算方式
			} else {
				tPD_LMPolDutyEdorCalDB.setRiskCode("000000");
				tPD_LMPolDutyEdorCalDB.setEdorType(calType);
				tPD_LMPolDutyEdorCalSet = tPD_LMPolDutyEdorCalDB.query();
				if (tPD_LMPolDutyEdorCalDB.mErrors.needDealError()) {
					mErrors.copyAllErrors(tPD_LMPolDutyEdorCalDB.mErrors);
					CError.buildErr(this, "查询险种责任保全计算信息失败！");
					return null;
				}
				if (tPD_LMPolDutyEdorCalSet.size() > 0) {
					calMode = tPD_LMPolDutyEdorCalSet.get(1).getCalMode(); // 获取保全重算的计算方式
				} else {
					calMode = tPD_LMDutySchema.getCalMode(); // 若不指定保全重算的计算方式，
					// 则默认承保的计算方式
				}
			}
		}
		// 之前合并完毕
		// 保费项结构部分
		if (!getPremStructure(mLCDutySchema)) {
			// @@错误处理
			CError.buildErr(this, "getPremStructure方法返回失败!");
			return mLCDutySchema;
		}
		// 从保单信息或责任信息中获取保费项表中需要计算的以外的信息
		// 合并完
		this.getPremOtherData(mLCDutySchema);

		int n = mLCPremBLSet.size();
		for (int i = 1; i <= n; i++) {
			LCPremSchema tLCPremSchema = mLCPremBLSet.get(i);

			if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
				PD_LMDutyPaySchema tPD_LMDutyPaySchema = mCRI
						.findDutyPayByPayPlanCodeClone(tLCPremSchema
								.getPayPlanCode());

				if (tPD_LMDutyPaySchema == null) {
					// @@错误处理
					CError.buildErr(this, "PD_LMDutyPay表查询失败!");
					return mLCDutySchema;
				}

				// 计算保费项中除保费外的其他信息
				// 合并完
				tLCPremSchema = this.calPremOther(tLCPremSchema,
						tPD_LMDutyPaySchema, mLCDutySchema);
				if (mOperator != null && !mOperator.equals("")) {
					tLCPremSchema.setOperator(mOperator);
					tLCPremSchema.setMakeDate(PubFun.getCurrentDate());
					tLCPremSchema.setMakeTime(PubFun.getCurrentTime());
				}
				mLCPremBLSet.set(i, tLCPremSchema);
			} // end of if
		} // end of for

		// 领取项结构部分
		// 合并完
		if (!getGetStructure(mLCDutySchema)) {
			// @@错误处理
			CError.buildErr(this, "getGetStructure方法返回失败!");
			return mLCDutySchema;
		}
		// 合并完
		this.getGetOtherData();

		int m = mLCGetBLSet.size();
		for (int i = 1; i <= m; i++) {
			LCGetSchema tLCGetSchema = mLCGetBLSet.get(i);
			System.out.println("mLCGetBLSet.size()" + m);
			if (tLCGetSchema.getDutyCode().equals(dutyCode)) {
				PD_LMDutyGetSchema tPD_LMDutyGetSchema = mCRI
						.findDutyGetByGetDutyCodeClone(tLCGetSchema
								.getGetDutyCode());

				if (tPD_LMDutyGetSchema == null) {
					// @@错误处理
					CError.buildErr(this, "PD_LMDutyGet表查询失败!");
					return mLCDutySchema;
				}

				// 取得相对应的传入的领取项的信息
				if (mGetFlag) {
					// 传入的是多条的GetSet
					int inpGetCount = mLCGetSetIn.size();
					for (int j = 1; j <= inpGetCount; j++) {
						LCGetSchema tLCGetSchema1 = mLCGetSetIn.get(j);
						if (tLCGetSchema1.getGetDutyCode().equals(
								tLCGetSchema.getGetDutyCode())) {
							mLCGetBLIn = tLCGetSchema1.getSchema();
							break;
						}
						// end of if
					}
					// end of for
				}
				// end of if
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println("@@@@@@@@@@@@@@@@@@@@tPD_LMDutyGetSchema=="
						+ tPD_LMDutyGetSchema.getEndDateCalMode());
				// 计算领取项中除保费外的其他信息
				// 合并完
				tLCGetSchema = this.calGetOther(tLCGetSchema, tPD_LMDutyGetSchema,
						mLCDutySchema);
				if (this.mErrors.needDealError()) {
					return null;
				}
				// 注释以上部分。lcget的操作人等信息赋值方式同lcprem.20071224
				if (mOperator != null && !mOperator.equals("")) {
					tLCGetSchema.setOperator(mOperator);
					tLCGetSchema.setMakeDate(PubFun.getCurrentDate());
					tLCGetSchema.setMakeTime(PubFun.getCurrentTime());
				}
				mLCGetBLSet.set(i, tLCGetSchema);
				System.out.println("calGetOther end of if");
			} // end of if
		} // end of for

		if (!noCalFlag) { // 如果需要计算保费,该标记由承保程序传入
			// 计算保费
			int j = mLCPremBLSet.size();
			for (int i = 1; i <= j; i++) {
				LCPremSchema tLCPremSchema = mLCPremBLSet.get(i);

				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					PD_LMDutyPaySchema tPD_LMDutyPaySchema = mCRI
							.findDutyPayByPayPlanCodeClone(tLCPremSchema
									.getPayPlanCode());

					if (tPD_LMDutyPaySchema == null) {
						// @@错误处理
						CError.buildErr(this, "PD_LMDutyPay表查询失败!");
						return mLCDutySchema;
					}

					// 准备计算基础数据部分
					mCalBase.setPayIntv(tLCPremSchema.getPayIntv());

					if (mLCGetBLIn.getGetDutyKind() == null) {
						// 此时保全会从后台查询出领取项，可能是多条，循环找出GetDutyKind不为空的一条赋值
						for (int t = 1; t <= mLCGetBLSet.size(); t++) {
							if (mLCGetBLSet.get(t).getGetDutyKind() != null) {
								mCalBase.setGetDutyKind(mLCGetBLSet.get(t)
										.getGetDutyKind());
							}
						}

					} else {
						// houzm add(将界面录入的GetDutyKind保存)
						mCalBase.setGetDutyKind(mLCGetBLIn.getGetDutyKind());
					}
					// 可以添加从界面传入的要素，该要素只是用于计算
					if (mLCDutySchema.getGetYear() != 0) {
						mCalBase.setGetYear(mLCDutySchema.getGetYear());
					}

					// 计算保费值
					double calResult = 0;
					// 合并完
					calResult = calPremValue(tLCPremSchema, mLCDutySchema,
							tPD_LMDutyPaySchema, calMode);
					mLCDutySchema.setPremToAmnt(currCalMode);
					
					//tongmeng 2009-03-30 modify
					//如果是约定费率,直接设置标准保费.
					if (mLCDutySchema.getCalRule()!=null&&
							mLCDutySchema.getCalRule().equals("1")
							//||mLCDutySchema.getCalRule().equals("3")
							) {
						tLCPremSchema.setStandPrem(mLCDutySchema.getPrem());
					} else {

						if (StrTool.cTrim(tPD_LMDutyPaySchema.getZeroFlag())
								.equals("N")) {
							if (calResult <= 0.0) {
								// @@错误处理
								CError.buildErr(this, "保费计算失败");
								return mLCDutySchema;
							} else {
								if (
										//tongmeng 2009-03-30 modify
										//如果是约定保额保费,标准保费使用算法计算出来的结果
										//mLCDutySchema.getCalRule().equals("3")
										//|| 
										mLCDutySchema.getCalRule().equals(
												"1")) {
									tLCPremSchema.setStandPrem(mLCDutySchema
											.getPrem());
								} else {
									tLCPremSchema.setStandPrem(calResult);
								}
							}
						}
						if (StrTool.cTrim(tPD_LMDutyPaySchema.getZeroFlag())
								.equals("Y")) {
							if (calResult < 0.0) {
								// @@错误处理
								CError.buildErr(this, "保费计算失败");
								return mLCDutySchema;
							} else {
								if (
										//tongmeng 2009-03-30 modify
										//如果是约定保额保费,标准保费使用算法计算出来的结果

										//mLCDutySchema.getCalRule().equals("3")
										//|| 
										mLCDutySchema.getCalRule().equals(
												"1")
												) {
									tLCPremSchema.setStandPrem(mLCDutySchema
											.getPrem());
								} else {
									tLCPremSchema.setStandPrem(calResult);
								}
							}
						}
					}
					tLCPremSchema.setPrem(tLCPremSchema.getStandPrem());
					mLCPremBLSet.set(i, tLCPremSchema);
				} // end of if
			} // end of for

			// 计算保额
			int k = mLCGetBLSet.size();
			for (int i = 1; i <= k; i++) {
				LCGetSchema tLCGetSchema = mLCGetBLSet.get(i);

				if (tLCGetSchema.getDutyCode().equals(dutyCode)) {
					PD_LMDutyGetDB tPD_LMDutyGetDB = new PD_LMDutyGetDB();
					tPD_LMDutyGetDB.setSchema(mCRI
							.findDutyGetByGetDutyCodeClone(tLCGetSchema
									.getGetDutyCode()));
					// 准备计算基础数据部分
					// 此句会覆盖calPremValue里面的赋值
					mCalBase.setGetIntv(tLCGetSchema.getGetIntv());
					mCalBase.setGDuty(tLCGetSchema.getGetDutyCode());
					mCalBase.setAddRate(tLCGetSchema.getAddRate());

					// 计算保额值
					double calResult = 0;
					calResult = calGetValue(tLCGetSchema, mLCDutySchema,
							tPD_LMDutyGetDB, calMode);
					// null认为是N
					// tongmeng 2009-03-30 modify
					// 如果是约定费率,直接设置标准保费.
					if (mLCDutySchema.getCalRule() != null
							&& mLCDutySchema.getCalRule().equals("1")
					// ||mLCDutySchema.getCalRule().equals("3")
					) {
						tLCGetSchema.setStandMoney(mLCDutySchema.getAmnt());
						tLCGetSchema.setActuGet(mLCDutySchema.getAmnt());
					} else {
						if (tPD_LMDutyGetDB.getZeroFlag() == null
								|| tPD_LMDutyGetDB.getZeroFlag().equals("")) {
							tPD_LMDutyGetDB.setZeroFlag("N");
						}
						if (StrTool.cTrim(tPD_LMDutyGetDB.getZeroFlag()).equals(
								"N")) {
							if (calResult <= 0.0) {
								// @@错误处理
								CError.buildErr(this, "保额计算失败!");
								return mLCDutySchema;
							} else {
								tLCGetSchema.setStandMoney(calResult);
								tLCGetSchema.setActuGet(calResult);
							}
						}
						if (StrTool.cTrim(tPD_LMDutyGetDB.getZeroFlag()).equals(
								"Y")) {
							if (calResult < 0.0) {
								// @@错误处理
								CError.buildErr(this, "保额计算失败!");
								return mLCDutySchema;
							} else {
								tLCGetSchema.setStandMoney(calResult);
								tLCGetSchema.setActuGet(calResult);
							}
						}
						// mLCGetBLSet.set(i, tLCGetSchema);
					}
				}
				// end of if
			}
			// end of for
		}

		//tongmeng 2010-11-17 modify
		//投连险处理
		// 如果需要计算保额则进行保额计算
		if (this.mAmntCalType != null && "2".equals(mAmntCalType))
		{
			int k = mLCGetBLSet.size();
			for (int i = 1; i <= k; i++)
			{
				LCGetSchema tLCGetSchema = mLCGetBLSet.get(i);

				if (tLCGetSchema.getDutyCode().equals(dutyCode))
				{
					PD_LMDutyGetDB tPD_LMDutyGetDB = new PD_LMDutyGetDB();
					tPD_LMDutyGetDB.setSchema(mCRI.findDutyGetByGetDutyCodeClone(tLCGetSchema.getGetDutyCode()));

					// 准备计算基础数据部分
					mCalBase.setGetIntv(tLCGetSchema.getGetIntv());
					mCalBase.setGDuty(tLCGetSchema.getGetDutyCode());
					mCalBase.setAddRate(tLCGetSchema.getAddRate());

					// 计算保额值
					double calResult = 0;
					calResult = calGetValue(tLCGetSchema, mLCDutySchema, tPD_LMDutyGetDB, calMode);
					// null认为是N
					if (tPD_LMDutyGetDB.getZeroFlag() == null || tPD_LMDutyGetDB.getZeroFlag().equals(""))
					{
						tPD_LMDutyGetDB.setZeroFlag("N");
					}
					if (StrTool.cTrim(tPD_LMDutyGetDB.getZeroFlag()).equals("N"))
					{
						if (calResult <= 0.0)
						{
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "CalBL";
							tError.functionName = "calOneDuty";
							tError.errorMessage = "保额计算失败";
							this.mErrors.addOneError(tError);
							return mLCDutySchema;
						}
						else
						{
							tLCGetSchema.setStandMoney(calResult);
							tLCGetSchema.setActuGet(calResult);
						}
					}
					if (StrTool.cTrim(tPD_LMDutyGetDB.getZeroFlag()).equals("Y"))
					{
						if (calResult < 0.0)
						{
							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "CalBL";
							tError.functionName = "calOneDuty";
							tError.errorMessage = "保额计算失败";
							this.mErrors.addOneError(tError);
							return mLCDutySchema;
						}
						else
						{
							tLCGetSchema.setStandMoney(calResult);
							tLCGetSchema.setActuGet(calResult);
						}
					}
				}
			}
		}
		
		// 责任总体计算
		mLCDutySchema = this.calDutyFromPrem(mLCDutySchema, tPD_LMDutySchema);
		mLCDutySchema = this.calDutyFromGet(mLCDutySchema, tPD_LMDutySchema);
		if (this.mErrors.needDealError()) {
			// @@错误处理
			CError.buildErr(this, "责任总体计算失败!");
			return mLCDutySchema;
		}
		
		/*
		 * 2008-09-08 zhangzheng 补充上对3-约定保额保费的处理
		 * 2008-09-11 在ProposalBL中只是从保险计划中取得了约定的费率，没有其他处理，所以这里对于1-约定费率也必须处理
		 */
		if (//!mLCDutySchema.getCalRule().equals("3")&& 
		  !mLCDutySchema.getCalRule().equals("1")&&!mLCDutySchema.getCalRule().equals("4")) { 
			if (!dealFloatRate(mLCPremBLSet, mLCGetBLSet, mLCDutySchema)) {
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError tError = new CError();
				tError.moduleName = "CalBL";
				tError.functionName = "calOneDuty";
				tError.errorMessage = "处理浮动费率失败!";
				this.mErrors.addOneError(tError);
				return mLCDutySchema;

			}
		}
		
		if (mLCPolBL.getPolTypeFlag() != null
				&& mLCPolBL.getPolTypeFlag().equals("1")) {
			// 首先判断是不是建工险
			PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();
			PD_LMRiskSet tPD_LMRiskSet = new PD_LMRiskSet();
//			String sql = "select * from lmrisk where riskcode='"
//					+ mLCPolBL.getRiskCode() + "'";
//			System.out.println("&&:" + sql);
//			tPD_LMRiskSet = tPD_LMRiskDB.executeQuery(sql);
			//从缓存中取，提升效率   modify by weikai 
			tPD_LMRiskSet.add(this.mCRI.findRiskByRiskCodeClone(mLCPolBL.getRiskCode()));
			String edorflag = "0";
			if (tPD_LMRiskSet != null && tPD_LMRiskSet.size() != 0) {
				edorflag = tPD_LMRiskSet.get(1).getEdorFlag();
			}
			if (edorflag.equals("1")) {
				if (!dealPeople(mLCPremBLSet, mLCGetBLSet, mLCDutySchema,
						mLCPolBL)) {
					this.mErrors.copyAllErrors(mCRI.mErrors);
					mCRI.mErrors.clearErrors();

					CError tError = new CError();
					tError.moduleName = "CalBL";
					tError.functionName = "calOneDuty";
					tError.errorMessage = "处理无名单失败!";
					this.mErrors.addOneError(tError);
					return mLCDutySchema;

				}
			} else {
				// 保额也要乘以人数的,建工险如果与人数无关应该做这样的处理
				for (int j = 1; j <= mLCGetBLSet.size(); j++) {
					// 做同样的修改

					LCGetSchema tLCGetSchema = mLCGetBLSet.get(j);
					if (tLCGetSchema.getDutyCode().equals(
							mLCDutySchema.getDutyCode())) {
						tLCGetSchema.setActuGet(tLCGetSchema.getActuGet()
								* mLCPolBL.getInsuredPeoples());
						tLCGetSchema.setStandMoney(tLCGetSchema.getStandMoney()
								* mLCPolBL.getInsuredPeoples());
					}
					mLCGetBLSet.set(j, tLCGetSchema);
				}

				mLCDutySchema.setAmnt(mLCDutySchema.getAmnt()
						* mLCPolBL.getInsuredPeoples());
				mLCDutySchema.setRiskAmnt(mLCDutySchema.getRiskAmnt()
						* mLCPolBL.getInsuredPeoples());

			}

		}
		return mLCDutySchema;
	}

	// @Method
	/**
	 * 计算函数
	 * 
	 * @return boolean
	 */
	public boolean calPol() {
		/*
		 * 由于存在闰年2-29变到2-28后又变会闰年2-29的问题，需要做特殊处理
		 * 例如:保单A下主险a，续保件b生效日期为2008-2-29,对于续保件来说，首期paytodate=payenddate=enddate=2009-2-28
		 * 2009年续保的时候，b的生效日期为2009-2-28,paytodate=payenddate=enddate=2010-2-28，
		 * 以此类推到2010年，b的生效日期为2010-2-28,paytodate=payenddate=enddate=2011-2-28，
		 * 但是当2012年时，b的生效日期为2011-2-28,paytodate=payenddate=enddate=2012-2-29，需要变会闰年的2-29
		 * */
		xb_flag = false;  //续保特殊处理标记，默认不需要
		
		
		//tongmeng 2010-11-17 modify 
		//投连险处理
		
		// 查询是否计算保额
		// 默认计算，
		PD_LMRiskSortDB tPD_LMRiskSortDB = new PD_LMRiskSortDB();
		PD_LMRiskSortSet tPD_LMRiskSortSet = new PD_LMRiskSortSet();
		PD_LMRiskSortSchema tPD_LMRiskSortSchema = new PD_LMRiskSortSchema();
		tPD_LMRiskSortSet = tPD_LMRiskSortDB.executeQuery("select * from pd_lmrisksort where riskcode='"
				+ mLCPolBL.getRiskCode() + "' and risksorttype='90'");
		if (tPD_LMRiskSortSet != null && tPD_LMRiskSortSet.size() > 0)
		{
			tPD_LMRiskSortSchema = tPD_LMRiskSortSet.get(1);
			if ("1".equals(tPD_LMRiskSortSchema.getRiskSortValue()))
			{
				mAmntCalType = "1"; // 不进行计算，取值为0
			}
			else
			{
				mAmntCalType = "2"; // 进行计算
			}
		}
		else
		{
			mAmntCalType = "2"; // 进行计算
		}

		
		
		// 准备描述的相关信息(查询责任录入控制信息-mPD_LMDutyCtrlSet)
		if (!this.preDefineInfo()) {
			return false;
		}

		System.out.println("after preDefineInfo()");

		if (mLCDutyBLSet == null) {
			// 取得必选的责任-通过描述取得责任信息的结构,并将界面录入的责任信息存入-mLCDutyBLSet
			if (!this.getDutyStructure()) {
				// @@错误处理
				CError.buildErr(this, "getDutyStructure方法返回失败!");
				return false;
			}
		}

		// 从传入的保单信息中获取责任表中需要计算的以外的信息
		// 合并完
		this.getDutyOtherData();

		System.out.println("after getDutyOtherData()");
		if(this.mLCPolBL.getRiskCode()!=null&&this.mLCPolBL.getRiskCode().equals("211801"))
		{
			//mSpecCalRule = "4";
			for(int i=1;i<=mLCDutyBLSet.size();i++)
			{
				mLCDutyBLSet.get(i).setCalRule("4");
				mLCDutyBLSet.get(i).setFloatRate("1");
			}
		}
		int n = mLCDutyBLSet.size();
		for (int i = 1; i <= n; i++) {
			LCDutySchema tLCDutySchema = mLCDutyBLSet.get(i);
			System.out.println("######mFlag==" + mFlag);
			// 准备录入部分的信息
			if (mFlag) {
				mLCDutyBLIn.setSchema(tLCDutySchema);
			}
			// 计算责任表中的部分信息(保险期间，交费期间，领取期间等)
			// 合并完
			tLCDutySchema = this.calDutyOther(tLCDutySchema);
			if (tLCDutySchema == null) {
				return false;
			}
			System.out.println("计算责任表下的保费项和领取项信息");
			// 计算责任表下的保费项和领取项信息
			tLCDutySchema = this.calOneDuty(tLCDutySchema);
			if (this.mErrors.needDealError()) {
				return false;
			}

			System.out.println("prem" + tLCDutySchema.getPrem());
			System.out.println("StandPrem" + tLCDutySchema.getStandPrem());
			// 增加浮动费率处理
			mLCDutyBLSet.set(i, tLCDutySchema);
		}
		//tongmeng 2009-05-13 modify
		//增加团单统一折扣费率算法
		//商补医疗 
		//
	/*	if(this.mLCPolBL.getRiskCode()!=null&&this.mLCPolBL.getRiskCode().equals("211801"))
		{
			mSpecCalRule = "4";
		}
		*/
		if(mSpecCalRule!=null&&!mSpecCalRule.equals("")&&mSpecCalRule.equals("4"))
		{
			if(!this.dealSpecFloatRate(mLCPremBLSet, mLCGetBLSet, mLCDutyBLSet))
			{
				return false;
			}
			//dealSpecFloatRate(this.m)
		}
		// 通过责任信息计算保单信息
		this.calPolFromDuty();

		return true;
	}

	/**
	 * 当不需要计算保费保额时调用该函数，增加了对保费保额的处理
	 * 
	 * @param pLCPremSet
	 *            LCPremSet
	 * @return boolean
	 */
	public boolean calPol2(LCPremSet pLCPremSet) {
		if (!calPol()) {
			return false;
		}

		double sumPrem = 0;
		double sumDutyPrem = 0;
		//tongmeng 2010-11-17 modify
		//投连险合并处理
		for (int m = 1; m <= mLCDutyBLSet.size(); m++)
		{ // 找到对应的责任项累积金额
			//if (mAmntCalType != null && "2".equals(mAmntCalType))
			{
				mLCDutyBLSet.get(m).setPrem(0);
			}
		}
		
		for (int i = 1; i <= pLCPremSet.size(); i++) {
			//tongmeng 2010-12-02 modify
			//折算到保单币种
			
			
			sumPrem += pLCPremSet.get(i).getStandPrem();
			for (int n = 1; n <= mLCPremBLSet.size(); n++) { // 找到对应的保费项修改金额
				if (pLCPremSet.get(i).getDutyCode().equals(
						mLCPremBLSet.get(n).getDutyCode())
						&& pLCPremSet.get(i).getPayPlanCode().equals(
								mLCPremBLSet.get(n).getPayPlanCode())) {

					mLCPremBLSet.get(n).setPrem(pLCPremSet.get(i).getPrem());
					mLCPremBLSet.get(n).setSumPrem(
							mLCPremBLSet.get(n).getSumPrem()
									+ mLCPremBLSet.get(n).getPrem());
					mLCPremBLSet.get(n).setRate(pLCPremSet.get(i).getRate());
					// 对于不定期交费，其标准保费置为0，实际保费置为实际缴纳金额
					// 但是后续处理需要标准保费，所以--后改为在承包描述完毕后，将这种类型的相关表的其标准保费置为0
					mLCPremBLSet.get(n).setStandPrem(
							mLCPremBLSet.get(n).getPrem());

					// 保全人工核保处数据的需要add by sxy 2004-03-16
					// mLCPremBLSet.get(n).setState("0") ;
					
					mLCPremBLSet.get(n).setOperator(
							pLCPremSet.get(i).getOperator());
					mLCPremBLSet.get(n).setMakeDate(
							pLCPremSet.get(i).getMakeDate());
					mLCPremBLSet.get(n).setMakeTime(
							pLCPremSet.get(i).getMakeTime());

					mLCPremBLSet.get(n).setPayTimes("1");
					/*
					mLCPremBLSet.get(n).setCurrency(
							pLCPremSet.get(i).getCurrency());
							*/
					break;
				}

			}
//			for (int m = 1; m <= mLCDutyBLSet.size(); m++) { // 找到对应的责任项累积金额
//				mLCDutyBLSet.get(m).setAmnt(0); // 保额置0
//				mLCDutyBLSet.get(m).setRiskAmnt(0); // 风险保额
//				if (pLCPremSet.get(i).getDutyCode().equals(
//						mLCDutyBLSet.get(m).getDutyCode())) {
//					mLCDutyBLSet.get(m).setPrem(
//							mLCDutyBLSet.get(m).getPrem()
//									+ pLCPremSet.get(i).getPrem());
//					mLCDutyBLSet.get(m).setSumPrem(
//							mLCDutyBLSet.get(m).getPrem());
//					// 对于不定期交费，其标准保费置为0，实际保费置为实际缴纳金额
//					// 但是后续处理需要标准保费，所以--后改为在承包描述完毕后，将这种类型的相关表的其标准保费置为0
//					mLCDutyBLSet.get(m).setStandPrem(
//							mLCDutyBLSet.get(m).getPrem());
//
//					break;
//				}
//			}
			
			String tTransDate = this.mLCPolBL.getCValiDate()==null?this.mLCPolBL.getPolApplyDate():this.mLCPolBL.getCValiDate();

			//tongmeng 2010-11-17 modify
			//投连险合并处理
			for (int m = 1; m <= mLCDutyBLSet.size(); m++)
			{ // 找到对应的责任项累积金额
				if (mAmntCalType != null && "1".equals(mAmntCalType))
				{
					mLCDutyBLSet.get(m).setAmnt(0); // 保额置0
					mLCDutyBLSet.get(m).setRiskAmnt(0); // 风险保额
				}
				//tongmeng 2010-11-17 此处不做处理
				//mLCDutyBLSet.get(m).setPrem(0);
				if (pLCPremSet.get(i).getDutyCode().equals(mLCDutyBLSet.get(m).getDutyCode())&&pLCPremSet.get(i).getPayStartDate()!=null&&pLCPremSet.get(i).getPayStartDate().equals(mLCPolBL.getCValiDate()))
				{
					
					//tongmeng 2010-12-02 modify
					double newSumPrem = 0;
					/*
					 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
					 * 如果返回值小于0，则有错误数据
					 * orgitype 传入的币种
					 * destype 需要转换的币种
					 * transdate 转换日期
					 * amnt 转换金额
					 */
					String tCurrency = mLCPremBLSet.get(i).getCurrency();
					
					newSumPrem = this.mLDExch.toOtherCur(tCurrency, mLCDutyBLSet.get(m).getCurrency(), tTransDate, mLCPremBLSet.get(i).getPrem());

					
					mLCDutyBLSet.get(m).setPrem(PubFun.round(mLCDutyBLSet.get(m).getPrem(),2) + PubFun.round(newSumPrem, 2));
					mLCDutyBLSet.get(m).setSumPrem(mLCDutyBLSet.get(m).getPrem());
					//sumDutyPrem = PubFun.round(sumDutyPrem,2) + PubFun.round(mLCDutyBLSet.get(m).getPrem(),2);
					// 对于不定期交费，其标准保费置为0，实际保费置为实际缴纳金额
					// 但是后续处理需要标准保费，所以--后改为在承包描述完毕后，将这种类型的相关表的其标准保费置为0
					mLCDutyBLSet.get(m).setStandPrem(mLCDutyBLSet.get(m).getPrem());

					break;
				}
			}
		}
		
		for (int m = 1; m <= mLCDutyBLSet.size(); m++)
		{ // 找到对应的责任项累积金额
			//if (mAmntCalType != null && "2".equals(mAmntCalType))
			sumDutyPrem = PubFun.round(sumDutyPrem,2) + PubFun.round(mLCDutyBLSet.get(m).getPrem(),2);
		}
	
		mLCPolBL.setPrem(sumDutyPrem);
		// 对于不定期交费，其标准保费置为0，实际保费置为实际缴纳金额
		// 但是后续处理需要标准保费，所以--后改为在承包描述完毕后，将这种类型的相关表的其标准保费置为0
		mLCPolBL.setStandPrem(sumDutyPrem);
		mLCPolBL.setSumPrem(sumDutyPrem);
//		mLCPolBL.setAmnt(0); // 保额置0
//		mLCPolBL.setRiskAmnt(0); // 风险保额
		if (mAmntCalType != null && "1".equals(mAmntCalType))
		{
			for (int x = 1; x <= mLCGetBLSet.size(); x++)
			{
				mLCGetBLSet.get(x).setStandMoney(0); // 保额置0
				mLCGetBLSet.get(x).setActuGet(0); // 保额置0
			}
			mLCPolBL.setAmnt(0); // 保额置0
			mLCPolBL.setRiskAmnt(0); // 风险保额
		}

		for (int x = 1; x <= mLCGetBLSet.size(); x++) {
			mLCGetBLSet.get(x).setStandMoney(0); // 保额置0
			mLCGetBLSet.get(x).setActuGet(0); // 保额置0
		}

		return true;
	}

	/**
	 * 从责任表中取得保单表中部分信息
	 * 
	 * @return boolean
	 */
	private boolean calPolFromDuty() {

		double sumAmnt = 0;
		double sumRiskAmnt = 0;
		double sumPrem = 0;
		double sumMult = 0;
		double sumStandPrem = 0;

		int payYears = 0;
		int years = 0;
		Date minPayEndDate = fDate.getDate("3000-01-01");
		Date minGetStartDate = fDate.getDate("3000-01-01");
		Date maxEndDate = fDate.getDate("1900-01-01");
		Date maxAcciEndDate = fDate.getDate("1900-01-01");

		int n = mLCDutyBLSet.size();
		for (int i = 1; i <= n; i++) {
			LCDutySchema mLCDutySchema = mLCDutyBLSet.get(i);

			PD_LMDutyDB tPD_LMDutyDB = new PD_LMDutyDB();
			PD_LMDutySet tPD_LMDutySet = new PD_LMDutySet();
			String sql = "select * From pd_lmduty where dutycode='"
					+ mLCDutySchema.getDutyCode() + "'";
			tPD_LMDutySet = tPD_LMDutyDB.executeQuery(sql);
			if (tPD_LMDutySet != null && tPD_LMDutySet.size() != 0
					&& tPD_LMDutySet.get(1).getAddAmntFlag() != null) { // 累加标志AddAmntFlag为1时各项责任保额累加
																		// ，
																		// 为2时取各项责任中保额最大者作为保单的保额
																		// add
				if (tPD_LMDutySet.get(1).getAddAmntFlag().equals("1")) {
					sumAmnt += mLCDutySchema.getAmnt();
				} else if (tPD_LMDutySet.get(1).getAddAmntFlag().equals("2")) {
					if (sumAmnt < mLCDutySchema.getAmnt()) {
						sumAmnt = mLCDutySchema.getAmnt();
					}
				}
			}

			sumRiskAmnt += mLCDutySchema.getRiskAmnt();
			sumPrem += mLCDutySchema.getPrem();
			// sumMult += mLCDutySchema.getMult();
			sumMult = mLCDutySchema.getMult();// 份数不需累加
			sumStandPrem += mLCDutySchema.getStandPrem();

			Date payEndDate = fDate.getDate(mLCDutySchema.getPayEndDate());
			if (payEndDate != null) {
				if (minPayEndDate.after(payEndDate)) {
					minPayEndDate = payEndDate;
				}
			}

			Date getStartDate = fDate.getDate(mLCDutySchema.getGetStartDate());
			if (getStartDate != null) {
				if (minGetStartDate.after(getStartDate)) {
					minGetStartDate = getStartDate;
				}
			}

			Date endDate = fDate.getDate(mLCDutySchema.getEndDate());
			if (endDate != null) {
				if (maxEndDate.before(endDate)) {
					maxEndDate = endDate;
				}
			}

			Date acciEndDate = fDate.getDate(mLCDutySchema.getAcciEndDate());
			if (acciEndDate != null) {
				if (maxAcciEndDate.before(acciEndDate)) {
					maxAcciEndDate = acciEndDate;
				}
			} else {
				maxAcciEndDate = null;
			}

			payYears = mLCDutySchema.getPayYears();
			// System.out.println("CalBL:calPolFromDuty:payYears:"+payYears);
			years = mLCDutySchema.getYears();
		}

		mLCPolBL.setStandPrem(sumStandPrem);
		mLCPolBL.setPrem(sumPrem);
		mLCPolBL.setAmnt(sumAmnt);
		mLCPolBL.setRiskAmnt(sumRiskAmnt);

		System.out.println("###################################");
		System.out.println("----fDate.getString(minPayEndDate)="
				+ fDate.getString(minPayEndDate));
		mLCPolBL.setPayEndDate(fDate.getString(minPayEndDate));
		mLCPolBL.setGetStartDate(fDate.getString(minGetStartDate));
		mLCPolBL.setEndDate(fDate.getString(maxEndDate));
		mLCPolBL.setAcciEndDate(fDate.getString(maxAcciEndDate));
		mLCPolBL.setPayYears(payYears);
		mLCPolBL.setYears(years);
		mLCPolBL.setMult(sumMult);
		mLCPolBL.setFloatRate(FloatRate);//2008-09-09 zhangzheng 加浮动费率

		return true;
	}

	/**
	 * 计算保费项表中的其他值
	 * 
	 * @param mLCPremSchema
	 *            LCPremSchema
	 * @param tPD_LMDutyPaySchema
	 *            PD_LMDutyPaySchema
	 * @param tLCDutySchema
	 *            LCDutySchema
	 * @return LCPremSchema
	 */
	private LCPremSchema calPremOther(LCPremSchema mLCPremSchema,
			PD_LMDutyPaySchema tPD_LMDutyPaySchema, LCDutySchema tLCDutySchema) {
		mLCPremSchema.setPayPlanType(tPD_LMDutyPaySchema.getType());
		mLCPremSchema.setUrgePayFlag(tPD_LMDutyPaySchema.getUrgePayFlag());
		mLCPremSchema.setNeedAcc(tPD_LMDutyPaySchema.getNeedAcc());
		mLCPremSchema.setState("0");
		mLCPremSchema.setPayTimes("1");
		PD_LMDutySchema tPD_LMDutySchema = mCRI.findDutyByDutyCodeClone(tLCDutySchema
				.getDutyCode().substring(0, 6));

		if (tPD_LMDutySchema == null) {
			// @@错误处理
			this.mErrors.copyAllErrors(mCRI.mErrors);
			mCRI.mErrors.clearErrors();

			CError.buildErr(this, "PD_LMDuty表查询失败!");
			return null;
		}

		// 判断 PayIntv 的取值位置
		if (chkInput(mLCPremSchema.getPayPlanCode(), "P", "PayIntv")) {
			mLCPremSchema.setPayIntv(mLCDutyBLIn.getPayIntv());
		} else {
			mLCPremSchema.setPayIntv(tPD_LMDutyPaySchema.getPayIntv());
		}

		//tongmeng 2011-01-19 modify
		//计算交费起始日期
		
		
		
		//end add 交费起始日期
		////////////////////////////////////////////////////////
		// 计算缴费终止日期
		Date baseDate = null;
		Date compDate = null;
		int interval = 0;
		String unit = null;
		//////////////////////////////////////////////////////////
		Date payStartDate = null;
		//Date getEndDate = null;

		// 计算交费开始日期
		String PayStartDataCalRef = null;
		String PayStartYearFlag = null;
		String PayStartDateCalMode = null;
		int PayStartYear = 0;
		
		//描述了起始日期计算才使用以下的逻辑
		if(tPD_LMDutyPaySchema.getPayStartYearFlag()!=null&&!tPD_LMDutyPaySchema.getPayStartYearFlag().equals(""))
		{
			
			PayStartYear = tPD_LMDutyPaySchema.getPayStartYear();
			PayStartYearFlag = tPD_LMDutyPaySchema.getPayStartYearFlag();
			PayStartDataCalRef = tPD_LMDutyPaySchema.getPayStartDateCalRef();
			PayStartDateCalMode = tPD_LMDutyPaySchema.getPayStartDateCalMode();
			
//			// 计算
//			if (StrTool.cTrim(PayStartYearFlag).equals("A")) {
//				baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
//				unit = "Y";
//
//				if (StrTool.cTrim(PayStartDataCalRef).equals("S")) { // 起保日期对应日
//					compDate = fDate.getDate(mLCPolBL.getCValiDate());
//				}
//				if (StrTool.cTrim(PayStartDataCalRef).equals("B")) { // 生日对应日
//					compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
//				}
//				if (StrTool.cTrim(PayStartDataCalRef).equals("C")) { // 参考保单选择?
//					//compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
//				}
//			} else {
//				baseDate = fDate.getDate(mLCPolBL.getCValiDate());
//				unit = PayStartYearFlag;
//			}
//			interval = PayStartYear;
//
//			if (baseDate == null || baseDate.equals("null")) {
//				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
//				LCGrpContSet tLCGrpContSet = new LCGrpContSet();
//				String sql = "select * from lcgrpcont where grpcontno='"
//						+ mLCPolBL.getGrpContNo() + "'";
//				tLCGrpContSet = tLCGrpContDB.executeQuery(sql);
//				baseDate = fDate.getDate(tLCGrpContSet.get(1).getCValiDate());
//			}

			//if (StrTool.cTrim(PayStartDateCalMode).equals("0")) 
			{ 
				//先计算,之后再按照计算结果定
				if (StrTool.cTrim(PayStartYearFlag).equals("A")) {
					baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
					unit = "Y";
					if (StrTool.cTrim(PayStartDataCalRef).equals(
							"B")) {
						compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
					}
					if (StrTool.cTrim(PayStartDataCalRef).equals(
							"S")) {
						compDate = fDate.getDate(mLCPolBL.getCValiDate());
					}
					if (StrTool.cTrim(PayStartDataCalRef).equals(
							"C")) { // 起始日期为界面选择的
						compDate = fDate.getDate(mLCPremSchema.getPayStartDate());
					}
				} else {
					if (StrTool.cTrim(PayStartDataCalRef).equals(
							"C")) { // 止领日期计算,起始参照为领取日期
						baseDate = fDate.getDate(mLCPremSchema.getPayStartDate());
						unit = PayStartYearFlag;
					} else {
						baseDate = fDate.getDate(mLCPolBL.getCValiDate());
						unit = PayStartYearFlag;
					}
				}
				interval = PayStartYear;
				
				payStartDate = PubFun.calDate(baseDate, interval, unit, compDate);
				
				if (StrTool.cTrim(PayStartDateCalMode).equals("0"))
				{
					//以计算为准
					
				}
				else if(StrTool.cTrim(PayStartDateCalMode).equals("1"))
				{
					//取计算后当月1号
					   Calendar lastDate = Calendar.getInstance(); 
				       lastDate.setTime(payStartDate);
				       lastDate.set(Calendar.DATE,1);//设为当前月的1号    
				       
				       payStartDate = fDate.getDate(sdf.format(lastDate.getTime()));
				}
				else if(StrTool.cTrim(PayStartDateCalMode).equals("2"))
				{
					//取计算后当年1号
					Calendar lastDate = Calendar.getInstance(); 
				    lastDate.setTime(payStartDate);
				    lastDate.set(Calendar.DAY_OF_YEAR,1);//设为当前月的1号     
				    payStartDate = fDate.getDate(sdf.format(lastDate.getTime()));
				}
				
				mLCPremSchema.setPayStartDate(fDate.getString(payStartDate));
			}
						
		}
		
		///////////////////////////////////////////////////////////
		Date payEndDate = null;
		int payEndYear = 0;
		String payEndYearFlag = null;

		// 判断 payEndYear 的取值位置
		if (chkInput(mLCPremSchema.getPayPlanCode(), "P", "PayEndYear")) {
			if (mLCDutyBLIn.getPayEndYear() == 0) {
				// @@错误处理
				CError.buildErr(this, "必须录入缴费终止年期!");
			}
			payEndYear = mLCDutyBLIn.getPayEndYear();
		} else {
			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals(
					"3")) {
				// 取保险终止日期
				payEndYear = tLCDutySchema.getInsuYear();
			}

			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals(
					"4")) {
				// 取领取开始日期
				payEndYear = tLCDutySchema.getGetYear();
			}

			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals(
					"0")) {
				// 以计算为准
				// 当DutyPay上为空的时候取Duty上的默认值
				if (tPD_LMDutyPaySchema.getPayEndYear() == 0) {
					payEndYear = tPD_LMDutySchema.getPayEndYear();
				} else {
					payEndYear = tPD_LMDutyPaySchema.getPayEndYear();
				}
			}
		}

		// 判断 payEndYearFlag 的取值位置
		if (chkInput(mLCPremSchema.getPayPlanCode(), "P", "PayEndYearFlag")) {
			if (mLCDutyBLIn.getPayEndYearFlag() == null) {
				// @@错误处理
				CError.buildErr(this, "必须录入缴费终止年期标志!");
				System.out.println("必须录入缴费终止年期标志:请检查责任项的payendyearflag标记");
			}
			payEndYearFlag = mLCDutyBLIn.getPayEndYearFlag();
		} else {
			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals(
					"3")) {
				// 取保险终止日期
				payEndYearFlag = tLCDutySchema.getInsuYearFlag();
			}

			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals(
					"4")) {
				// 取领取开始日期
				payEndYearFlag = tLCDutySchema.getGetYearFlag();
			}

			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals(
					"0")) {
				// 以计算为准
				// 当DutyPay上为空的时候取Duty上的默认值
				if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndYearFlag()).equals(
						"")) {
					payEndYearFlag = tPD_LMDutySchema.getPayEndYearFlag();
				} else {
					payEndYearFlag = tPD_LMDutyPaySchema.getPayEndYearFlag();
				}
			}
		}

		// 计算
		if (StrTool.cTrim(payEndYearFlag).equals("A")) {
			baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			unit = "Y";

			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalRef()).equals(
					"S")) {
				// 起保日期对应日
				compDate = fDate.getDate(mLCPolBL.getCValiDate());
			}
			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalRef()).equals(
					"B")) {
				// 生日对应日
				compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			}
			
			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalRef()).equals("P")) {
				// 缴费起期对应日
				compDate = fDate.getDate(mLCPremSchema.getPayStartDate());
			}
			
		} else {
			
			baseDate = fDate.getDate(mLCPolBL.getCValiDate());
			unit = payEndYearFlag;
		}
		interval = payEndYear;

		if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals("0")) {
			// 取领取开始日期
			if (StrTool.cTrim(tLCDutySchema.getGetStartType()).equals("B")) {
				baseDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			}

			if (StrTool.cTrim(tLCDutySchema.getGetStartType()).equals("S")) {
				baseDate = fDate.getDate(mLCPolBL.getCValiDate());
			}
			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalRef()).equals("P")) {
				// 缴费起期对应日
				baseDate = fDate.getDate(mLCPremSchema.getPayStartDate());
			}
		}
		
		if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalMode()).equals("4")) {
			// 取领取开始日期
			if (StrTool.cTrim(tLCDutySchema.getGetStartType()).equals("B")) {
				compDate = fDate.getDate(mLCPolBL.getInsuredBirthday());
			}

			if (StrTool.cTrim(tLCDutySchema.getGetStartType()).equals("S")) {
				compDate = fDate.getDate(mLCPolBL.getCValiDate());
			}
			if (StrTool.cTrim(tPD_LMDutyPaySchema.getPayEndDateCalRef()).equals("P")) {
				// 缴费起期对应日
				compDate = fDate.getDate(mLCPremSchema.getPayStartDate());
			}
		}

		if (baseDate == null || baseDate.equals("null")) {
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			LCGrpContSet tLCGrpContSet = new LCGrpContSet();
			String sql = "select * from lcgrpcont where grpcontno='"
					+ mLCPolBL.getGrpContNo() + "'";
			tLCGrpContSet = tLCGrpContDB.executeQuery(sql);
			baseDate = fDate.getDate(tLCGrpContSet.get(1).getCValiDate());
		}
		if(xb_flag)
		{
			payEndDate = PubFun.calxbEndDate(baseDate, interval, unit, compDate);
		}
		else
		{
			payEndDate = PubFun.calDate(baseDate, interval, unit, compDate);
		}
		mLCPremSchema.setPayEndDate(fDate.getString(payEndDate));

		System.out.println("payEndDate###############################");
		System.out.println("payEndDate###############################");
		System.out.println("payEndDate###############################");
		System.out.println("LCPrem payEndDate: "
				+ mLCPremSchema.getPayEndDate());

		// 准备计算基础数据部分
		mCalBase.setPayEndYear(payEndYear);
		mCalBase.setPayEndYearFlag(payEndYearFlag);

		return mLCPremSchema;
	}

	/**
	 * 计算保费项表中保费的值
	 * 
	 * @param mLCPremSchema
	 *            LCPremSchema
	 * @param tLCDutySchema
	 *            LCDutySchema
	 * @param tPD_LMDutyPaySchema
	 *            PD_LMDutyPaySchema
	 * @param calFlag
	 *            String
	 * @return double
	 */
	private double calPremValue(LCPremSchema mLCPremSchema,
			LCDutySchema tLCDutySchema, PD_LMDutyPaySchema tPD_LMDutyPaySchema,
			String calFlag) {
		double mValue = -1;
		currCalMode = calFlag;
		String mCalCode = null;
		if (StrTool.cTrim(calFlag).equals("P")) {
			mCalCode = tPD_LMDutyPaySchema.getCnterCalCode(); // 保费算保额
		}
		if (StrTool.cTrim(calFlag).equals("G")) {
			mCalCode = tPD_LMDutyPaySchema.getCalCode(); // 保额算保费
		}
		if (StrTool.cTrim(calFlag).equals("O")) {
			mCalCode = tPD_LMDutyPaySchema.getOthCalCode(); // 其他算保费
		}
		// 根据实际保费算保额情况记录保费保额计算方向，在保单重算时使用
		if (StrTool.cTrim(calFlag).equals("A")
				&& tLCDutySchema.getStandPrem() != 0.0) {
			mCalCode = tPD_LMDutyPaySchema.getCnterCalCode(); // 保费、保额互算{
			currCalMode = "P";
		}
		if (StrTool.cTrim(calFlag).equals("A")
				&& tLCDutySchema.getAmnt() != 0.0) {
			mCalCode = tPD_LMDutyPaySchema.getCalCode(); // 保费、保额互算{
			currCalMode = "G";
		}
		if (StrTool.cTrim(calFlag).equals("I")) {
			mCalCode = tPD_LMDutyPaySchema.getCnterCalCode(); // 录入保费保额
		}
		// tongmeng 2008-07-21
		// for 泰康团险
		// DefaultVal字段类型修改/
		// 取默认值
		//tongmeng 2011-06-20 modify
		//直接就是number 
		if (StrTool.cTrim(mCalCode).equals("")) {
			double tDefaultValue = 0;
			tDefaultValue = tPD_LMDutyPaySchema.getDefaultVal();
//			if(tDefaultValue==null||tDefaultValue.equals(""))
//			{
//				tDefaultValue = "0";
//			}
			mValue = tDefaultValue;
			return mValue;
		}
		mCalBase.setGetIntv(tLCDutySchema.getGetIntv()); // 2006-4-19

		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetStartType", mCalBase.getGetStartType());
		mCalculator.addBasicFactor("PolTypeFlag", mCalBase.getPolTypeFlag());
		mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("GetYearFlag", mCalBase.getGetYearFlag());
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("RnewFlag", mCalBase.getRnewFlag());
		mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("FRate", mCalBase.getFloatRate());
		mCalculator.addBasicFactor("GetDutyKind", mCalBase.getGetDutyKind());
		mCalculator.addBasicFactor("StandbyFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("StandbyFlag2", mCalBase.getStandbyFlag2());
		mCalculator.addBasicFactor("StandbyFlag3", mCalBase.getStandbyFlag3());
		mCalculator.addBasicFactor("GrpPolNo", mCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("GrpContNo", mCalBase.getGrpContNo());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());
		mCalculator.addBasicFactor("GetRate", mCalBase.getGetRate());
		mCalculator.addBasicFactor("SSFlag", mCalBase.getSSFlag());
		mCalculator.addBasicFactor("PeakLine", mCalBase.getPeakLine());
		mCalculator.addBasicFactor("CalType", mCalBase.getCalType());
		mCalculator.addBasicFactor("Occupation", mCalBase.getOccupation());
		mCalculator.addBasicFactor("EdorNo", mCalBase.getEdorNo());
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("SecondInsuredNo", mCalBase
				.getSecondInsuredNo());
		mCalculator.addBasicFactor("InsuredNo", mCalBase.getInsuredNo());
		mCalculator.addBasicFactor("MainPolNo", mCalBase.getMainPolNo());
		mCalculator.addBasicFactor("MAmnt", mCalBase.getMAmnt());
		mCalculator.addBasicFactor("DutyCode", mCalBase.getDutyCode()
				.substring(0, 6));
		mCalculator.addBasicFactor("AppAge2", mCalBase.getAppAge2());
		mCalculator.addBasicFactor("AppAg2", mCalBase.getAppAge2()); // 防止算法取错
		//tongmeng 2009-02-21 add
		//增加投保人年龄算法
		mCalculator.addBasicFactor("AppntAppAge", mCalBase.getAppntAppAge());
		System.out.println("mCalBase.getManageCom()=="
				+ mCalBase.getManageCom());
		mCalculator.addBasicFactor("ManageCom", mCalBase.getManageCom());
		mCalculator.addBasicFactor("Currency", mCalBase.getCurrency());//币种信息

		// 险种其他信息
		System.out.println("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());
		for (int i = 0; i < mCalBase.getAllOtherParmCount(); i++) {
			String tOtherParmName = mCalBase.getOtherParmName(i);
			String tOtherParmValue = mCalBase
					.getOtherParmVlaueByName(tOtherParmName);
			System.out.println("tOtherParmName==" + tOtherParmName
					+ " tOtherParmValue ==" + tOtherParmValue);
			mCalculator.addBasicFactor(tOtherParmName, tOtherParmValue);
		}
		System.out.println("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());

		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("")) {
			mValue = 0;
		} else {
			mValue = Double.parseDouble(tStr);
		}

		return mValue;
	}

	/**
	 * 判断该字段的内容的取值位置是否为录入值 录入项代码、录入位置（责任、领取项、保费项）、字段名称
	 * 
	 * @param code
	 *            String
	 * @param ctrl
	 *            String
	 * @param fieldName
	 *            String
	 * @return boolean
	 */
	private boolean chkInput(String code, String ctrl, String fieldName) {
		int n = mPD_LMDutyCtrlSet.size();
		for (int i = 1; i <= n; i++) {
			PD_LMDutyCtrlSchema tPD_LMDutyCtrlSchema = mPD_LMDutyCtrlSet.get(i);
			if (StrTool.cTrim(ctrl).equals("D")) {
				if (StrTool.cTrim(code).equals(
						StrTool.cTrim(tPD_LMDutyCtrlSchema.getDutyCode()))
						&& StrTool.cTrim(fieldName).toLowerCase().equals(
								StrTool.cTrim(tPD_LMDutyCtrlSchema.getFieldName())
										.toLowerCase())
						&& StrTool.cTrim(tPD_LMDutyCtrlSchema.getCtrlType())
								.equals("D")) {
					if (StrTool.cTrim(tPD_LMDutyCtrlSchema.getInpFlag()).equals(
							"Y")) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if (StrTool.cTrim(code).equals(
						StrTool.cTrim(tPD_LMDutyCtrlSchema.getOtherCode()))
						&& StrTool.cTrim(fieldName).toLowerCase().equals(
								StrTool.cTrim(tPD_LMDutyCtrlSchema.getFieldName())
										.toLowerCase())
						&& StrTool.cTrim(ctrl).equals(
								StrTool.cTrim(tPD_LMDutyCtrlSchema.getCtrlType()))) {
					if (StrTool.cTrim(tPD_LMDutyCtrlSchema.getInpFlag()).equals(
							"Y")) {
						return true;
					} else {
						return false;
					}
				}
			}
			// end of if
		}
		// end of for
		return false;
	}

	//tongmeng 2009-05-13 add
	//针对商补医疗特殊处理
	private boolean dealSpecFloatRate(LCPremBLSet pLCPremBLSet,
			LCGetBLSet pLCGetBLSet, LCDutySet pLCDutySet) {
		boolean tFlag = true;
		//mLCPolBL
		
		double tInputPrem = mLCPolBL.getPrem();//界面录入保费
		
		double tDutySumPrem = 0;//责任汇总保费
		int tDutySize = pLCDutySet.size();
		if(tDutySize==0)
		{
			return false;
		}
		for(int i=1;i<=tDutySize;i++)
		{
			double tempPrem = 0;
			tempPrem = pLCDutySet.get(i).getStandPrem();
			tDutySumPrem = tDutySumPrem + tempPrem;
		}
		double  sumPrem = 0;
		if(tDutySumPrem==0)
		{
			return false;
		}
		double tFloatRate = tInputPrem/tDutySumPrem ;
		//tongmeng 2009-07-03 四舍五入问题处理
		//tFloatRate = Double.parseDouble(mDecimalFormat.format(tFloatRate));
		tFloatRate = PubFun.round(tFloatRate, 2);
		
		for(int i=1;i<=pLCPremBLSet.size();i++)
		{
			double tPrem = pLCPremBLSet.get(i).getStandPrem();
			//tPrem = Double.parseDouble(mDecimalFormat.format((tPrem*tInputPrem)/tDutySumPrem));
			tPrem = PubFun.round((tPrem*tInputPrem)/tDutySumPrem,2);
			//sumPrem = sumPrem + tPrem;
			pLCPremBLSet.get(i).setPrem(tPrem);
		}
		for(int i=1;i<=pLCDutySet.size();i++)
		{
			double tPrem = pLCDutySet.get(i).getStandPrem();
			//tPrem = Double.parseDouble(mDecimalFormat.format((tPrem*tInputPrem)/tDutySumPrem));
			tPrem = PubFun.round((tPrem*tInputPrem)/tDutySumPrem,2);
			pLCDutySet.get(i).setPrem(tPrem);
			pLCDutySet.get(i).setFloatRate(tFloatRate);
		}
		
		//因为累计和可能存在进位误差（可能会有0.01元的），所以需要调整
        double sumDutyPrem = 0.0;
        
        for (int j = 1; j <= pLCDutySet.size(); j++)
        {
            LCDutySchema tLCDutySchema = (LCDutySchema) pLCDutySet.get(j);
            sumDutyPrem = sumDutyPrem + tLCDutySchema.getPrem();
        }

        if (sumDutyPrem != tInputPrem)
        {
            double diffMoney = sumDutyPrem - tInputPrem;

            //调整责任表
            for (int j = 1; j <= pLCDutySet.size(); j++)
            {
                if (pLCDutySet.get(j).getPrem() > 0.0)
                {
                	pLCDutySet.get(j).setPrem(pLCDutySet.get(j)
                                                                 .getPrem() -
                        diffMoney);

                    break;
                }
            }

            //调整保费表
            for (int j = 1; j <= pLCPremBLSet.size(); j++)
            {
                if (pLCPremBLSet.get(j).getPrem() > 0)
                {
                    pLCPremBLSet.get(j).setPrem(pLCPremBLSet.get(j)
                                                                 .getPrem() -
                        diffMoney);

                    break;
                }
            }
        }
        
		
		return tFlag;
	}
	/**
	 * 判断是否需要处理浮动附率：如果需要，将相关数据变更
	 * 只处理页面录入保费，保额而没有录入浮动费率的情况：录入浮动费率的情况已经在险种计算公式描述中完成
	 * 在此之前，如果传入浮动费率，计算公式中已经计算过浮动费率
	 * 
	 * @param pLCPremBLSet
	 *            LCPremBLSet 保费项
	 * @param pLCGetBLSet
	 *            LCGetBLSet 领取项
	 * @param pLCDutyShema
	 *            LCDutySchema 责任项
	 * @return boolean
	 */
	private boolean dealFloatRate(LCPremBLSet pLCPremBLSet,
			LCGetBLSet pLCGetBLSet, LCDutySchema pLCDutyShema) {
		String dutyCode = pLCDutyShema.getDutyCode();
		String rule = pLCDutyShema.getCalRule();
		// 如果界面上录入保费和保额：且浮动费率==0(不计算浮动费率,校验录入的保费保额是否和计算得到的数据匹配
		if (FloatRate == 0 && InputPrem > 0 && InputAmnt > 0) {
			dealFormatModol(pLCPremBLSet, pLCGetBLSet, pLCDutyShema); // 转换精度
			if ((InputPrem != pLCDutyShema.getPrem())
					|| (InputAmnt != pLCDutyShema.getAmnt())) {
				CError.buildErr(this, "您录入的保费保额（"
//						+ mDecimalFormat.format(InputPrem) + ","
//						+ mDecimalFormat.format(InputAmnt) + "）与计算得到的保费保额（"
//						+ mDecimalFormat.format(pLCDutyShema.getPrem()) + ","
//						+ mDecimalFormat.format(pLCDutyShema.getAmnt())
						+ PubFun.round(InputPrem,2) + ","
						+ PubFun.round(InputAmnt,2) + "）与计算得到的保费保额（"
						+ PubFun.round(pLCDutyShema.getPrem(),2) + ","
						+ PubFun.round(pLCDutyShema.getAmnt(),2)
						+ "）不符合！");

				return false;
			}

			return true;
		}

		// Add By 2006.10.24:对于按份卖的险种，如果界面上只录入份数和保费或（只录入份数和保额）：
		// 且浮动费率==0(不计算浮动费率,校验录入的保费或保额是否和计算得到的数据匹配
		if (FloatRate == 0 && InputMult > 0 && (InputPrem > 0 || InputAmnt > 0)) {
			dealFormatModol(pLCPremBLSet, pLCGetBLSet, pLCDutyShema); // 转换精度
			if ((InputPrem > 0 && InputPrem != pLCDutyShema.getPrem())
					|| (InputAmnt > 0 && InputAmnt != pLCDutyShema.getAmnt())) {
				CError tError = new CError();
				tError.moduleName = "ProposalBL";
				tError.functionName = "dealFloatRate";
				if (InputPrem > 0) {
					tError.errorMessage = "您录入的保费（"
							+ PubFun.round(InputPrem,2) + "）与计算得到的保费（"
							+ PubFun.round(pLCDutyShema.getPrem(),2)
							+ "）不符合！";
				} else {
					tError.errorMessage = "您录入的保额（"
							+ PubFun.round(InputAmnt,2) + "）与计算得到的保额（"
							+ PubFun.round(pLCDutyShema.getAmnt(),2)
							+ "）不符合！";
				}
				this.mErrors.addOneError(tError);
				return false;
			}
			return true;
		}

		// 如果界面上录入保费和保额：且浮动费率==规定的常量,那么认为该浮动费率需要自动计算得到
		// 如果界面上录入保费和保额：且浮动费率<>规定的常量,那么认为浮动费率已经给出，且在前面的保费保额计算中用到。此处不用处理
		// 该种险种的计算公式需要描述
		if ((autoCalFloatRateFlag) && (InputPrem > 0) && (InputAmnt > 0)) {
			String strCalPrem = String.valueOf(PubFun.round(pLCDutyShema.getPrem(),2)); // 转换计算后的保费
			// (
			// 规定的精度
			// )
			String strCalAmnt = String.valueOf(PubFun.round(pLCDutyShema.getAmnt(),2)); // 转换计算后的保额
			double calPrem = Double.parseDouble(strCalPrem);
			double calAmnt = Double.parseDouble(strCalAmnt);

			// 如果界面录入的保费保额与计算出来的保费保额相等，则认为浮动费率=1,不做任何处理返回
			if ((calPrem == InputPrem) && (calAmnt == InputAmnt)) {
				return true;
			}

			if (pLCDutyShema.getPrem() == 0) {
				CError.buildErr(this, "计算得到的保费为0,不能做浮动费率计算!");

				return false;
			}
			// 浮动费率的计算公式为：P(现)*A(原)/(A(现)*P(原))
			double formatFloatRate = 0;
			if (InputAmnt == 0 || pLCDutyShema.getPrem() == 0) {
				formatFloatRate = 0.00;
			} else {
				FloatRate = (InputPrem * pLCDutyShema.getAmnt())
						/ (InputAmnt * pLCDutyShema.getPrem());

				formatFloatRate = Double.parseDouble(mFRDecimalFormat
						.format(FloatRate));
			}
			// 更新责任项
			/**
			 * @todo 约定费率，Prem中保存实际保费值，StandPrem中保存根据表定费率计算得到值,edit by chenrong
			 */
			System.out.println("LCDuty 折扣前保费:"+pLCDutyShema.getPrem());
			pLCDutyShema.setPrem(pLCDutyShema.getPrem() * FloatRate);
			System.out.println("LCDuty 折扣后保费:"+pLCDutyShema.getPrem());
			
			
			// yaory 只有计算规则为3时才会回写费率
			if (pLCDutyShema.getCalRule().equals("3")) {
				pLCDutyShema.setFloatRate(formatFloatRate); // 存储转换后的浮动费率精度
			}
			if (pLCDutyShema.getCalRule().equals("1")) {
				pLCDutyShema.setFloatRate(pLCDutyShema.getPrem()
						/ pLCDutyShema.getAmnt()); // 存储转换后的浮动费率精度
			}
			dutyCode = pLCDutyShema.getDutyCode();
			// 更新保费项
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {

				LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals(
							"000000")) {
						tLCPremSchema.setPrem(tLCPremSchema.getPrem()
								* FloatRate);
						//tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem(
						// ) * FloatRate);
					}

					pLCPremBLSet.set(j, tLCPremSchema);
				}
			}
			dealFormatModol(pLCPremBLSet, pLCGetBLSet, pLCDutyShema); // 转换精度

			// 因为累计和可能存在进位误差（可能会有0.01元的），所以需要调整
			double sumDutyPrem = 0.0;
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {
				LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					sumDutyPrem += tLCPremSchema.getStandPrem();
				}
			}

			if (sumDutyPrem != InputPrem) {
				double diffMoney = sumDutyPrem - InputPrem;

				// 调整保费表
				for (int j = 1; j <= pLCPremBLSet.size(); j++) {
					if (pLCPremBLSet.get(j).getDutyCode().equals(dutyCode)) {
						if (pLCPremBLSet.get(j).getStandPrem() > 0) {
							pLCPremBLSet.get(j).setStandPrem(
									pLCPremBLSet.get(j).getStandPrem()
											- diffMoney);

							break;
						}
					}
				}
			}

			return true;
		}

		// 如果界面上录入保费.而保额为0：且浮动费率==规定的常量,那么认为录入的保费即为经过计算后得到的保费，
		// 此时，需要自动计算得到该浮动费率
		if ((autoCalFloatRateFlag) && (InputPrem > 0) && (InputAmnt == 0)) {
			System.out.println("####");
			String strCalPrem = String.valueOf(PubFun.round(pLCDutyShema.getPrem(),2)); // 转换计算后的保费
			// (
			// 规定的精度
			// )
			double calPrem = Double.parseDouble(strCalPrem);

			// 如果界面录入的保费与计算出来的保费相等，则认为浮动费率=1,不做任何处理返回
			if (calPrem == InputPrem) {
				return true;
			}

			if (pLCDutyShema.getPrem() == 0) {
				CError.buildErr(this, "计算得到的保费为0,不能做浮动费率计算!");

				return false;
			}

			// 浮动费率的计算公式为：P(现)/P(原)
			double formatFloatRate = 0;
			FloatRate = InputPrem / (pLCDutyShema.getPrem());
			formatFloatRate = Double.parseDouble(mFRDecimalFormat
					.format(FloatRate));

			// 更新责任项
			/**
			 * @todo 计算费率，Prem中保存实际保费值，StandPrem中保存根据表定费率计算得到值,edit by chenrong
			 */
			pLCDutyShema.setPrem(InputPrem);
			pLCDutyShema.setStandPrem(InputPrem);
			if (pLCDutyShema.getCalRule().equals("3")) {
				pLCDutyShema.setFloatRate(formatFloatRate); // 存储转换后的浮动费率精度
			}
			if (pLCDutyShema.getCalRule().equals("1")) {
				pLCDutyShema.setFloatRate(pLCDutyShema.getPrem()
						/ pLCDutyShema.getAmnt()); // 存储转换后的浮动费率精度
			}

			// 更新保费项
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {
				LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals(
							"000000")) {
						tLCPremSchema.setPrem(tLCPremSchema.getPrem()
								* FloatRate);
						//tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem(
						// ) * FloatRate);
					}
					pLCPremBLSet.set(j, tLCPremSchema);
				}
			}

			dealFormatModol(pLCPremBLSet, pLCGetBLSet, pLCDutyShema); // 转换精度

			// 因为累计和可能存在进位误差（可能会有0.01元的），所以需要调整
			double sumDutyPrem = 0.0;
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {
				LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					sumDutyPrem += tLCPremSchema.getStandPrem();
				}
			}

			if (sumDutyPrem != InputPrem) {
				double diffMoney = sumDutyPrem - InputPrem;
				// 调整保费表
				for (int j = 1; j <= pLCPremBLSet.size(); j++) {
					if (pLCPremBLSet.get(j).getDutyCode().equals(dutyCode)) {
						if (pLCPremBLSet.get(j).getStandPrem() > 0) {
							pLCPremBLSet.get(j).setStandPrem(
									pLCPremBLSet.get(j).getStandPrem()
											- diffMoney);

							break;
						}
					}
				}
			}

			return true;
		}
		
		//
		if (!(autoCalFloatRateFlag) && (FloatRate < 1) && (FloatRate > 0)) {

			System.out.print("后台FloatRate:" + FloatRate);
			// 浮动费率的计算公式为：P(现)*A(原)/(A(现)*P(原))
			double formatFloatRate = 0;

			formatFloatRate = Double.parseDouble(mFRDecimalFormat
					.format(FloatRate));

			// 更新责任项
			/**
			 * @todo 经讨论Prem字段存实际保费，StandPrem字段存标准保费 add by heyq,pengqian
			 *       2006-5-20
			 */
			pLCDutyShema.setPrem(pLCDutyShema.getPrem() * FloatRate);
			// pLCDutyShema.setStandPrem(pLCDutyShema.getStandPrem() *
			// FloatRate);
			pLCDutyShema.setFloatRate(formatFloatRate); // 存储转换后的浮动费率精度
			System.out.println(pLCDutyShema.getPrem());

			//add by jiaqiangli 2009-03-17 保全不做校验 浮动费率的重算出错 新契约必须与投保单上的保费保额一致
			//add by xiongzh 2009-06-30 续期续保重算不作校验
			System.out.println("appflag:"+mLCPolBL.getAppFlag());
			if ((calType == null || calType.equals(""))&&(mLCPolBL.getAppFlag()!=null)&&(!mLCPolBL.getAppFlag().equals("9"))) {
			// 将录入的值与计算得到的值进行比较
			// if ( (InputPrem > 0 && InputPrem != pLCDutyShema.getPrem())
			// || (InputAmnt > 0 && InputAmnt != pLCDutyShema.getAmnt()))
			//
			if ((InputPrem > 0 && PubFun.round(InputPrem,2)!=
					PubFun.round(pLCDutyShema.getPrem(),2))
					|| (InputAmnt > 0 && PubFun.round(InputAmnt,2)!=
						PubFun.round(pLCDutyShema
											.getAmnt(),2))) {
				String tErrMess = "";
				if (PubFun.round(InputPrem,2)!=PubFun.round(pLCDutyShema.getPrem(),2)) {
					tErrMess = "您录入的保费（" + PubFun.round(InputPrem,2)
							+ "）与计算得到的保费（"
							+ PubFun.round(pLCDutyShema.getPrem(),2)
							+ "）不符合！";
				} else {
					tErrMess = "您录入的保额（" + PubFun.round(InputAmnt,2)
							+ "）与计算得到的保额（"
							+ PubFun.round(pLCDutyShema.getAmnt(),2)
							+ "）不符合！";
				}
				CError.buildErr(this, tErrMess);
				return false;
			}
		}
			if (pLCDutyShema.getCalRule().equals("3")) {
				pLCDutyShema.setFloatRate(formatFloatRate); // 存储转换后的浮动费率精度
			}

			if (pLCDutyShema.getCalRule().equals("1")) {
				pLCDutyShema.setFloatRate(pLCDutyShema.getPrem()
						/ pLCDutyShema.getAmnt()); // 存储转换后的浮动费率精度
			}

			// 更新保费项
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {

				LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals(
							"000000")) {
						/**
						 * @todo 经讨论Prem字段存实际保费，StandPrem字段存标准保费 add by
						 *       heyq,pengqian 2006-5-20
						 */
						//tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem(
						// ) *
						// FloatRate);
						tLCPremSchema.setPrem(tLCPremSchema.getPrem()
								* FloatRate);
					}

					pLCPremBLSet.set(j, tLCPremSchema);
				}
			}

			dealFormatModol(pLCPremBLSet, pLCGetBLSet, pLCDutyShema); // 转换精度

			// 因为累计和可能存在进位误差（可能会有0.01元的），所以需要调整
			double sumDutyPrem = 0.0;
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {
				LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
				if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
					sumDutyPrem += tLCPremSchema.getStandPrem();
				}
			}

			if (sumDutyPrem != InputPrem) {
				double diffMoney = sumDutyPrem - InputPrem;

				// 调整保费表
				for (int j = 1; j <= pLCPremBLSet.size(); j++) {
					if (pLCPremBLSet.get(j).getDutyCode().equals(dutyCode)) {
						if (pLCPremBLSet.get(j).getStandPrem() > 0) {
							pLCPremBLSet.get(j).setStandPrem(
									pLCPremBLSet.get(j).getStandPrem()
											- diffMoney);
							break;
						}
					}
				}
			}

			return true;
		}
		// 否则，直接转换精度
		dealFormatModol(pLCPremBLSet, pLCGetBLSet, pLCDutyShema);

		return true;
	}

	/**
	 * 处理传入的数据，转换合适的精度
	 * 
	 * @param pLCPremBLSet
	 *            LCPremBLSet
	 * @param pLCGetBLSet
	 *            LCGetBLSet
	 * @param pLCDutyShema
	 *            LCDutySchema
	 * @return boolean
	 */
	private boolean dealFormatModol(LCPremBLSet pLCPremBLSet,
			LCGetBLSet pLCGetBLSet, LCDutySchema pLCDutyShema) {
		String dutyCode = pLCDutyShema.getDutyCode();
		String strCalPrem = String.valueOf(PubFun.round(pLCDutyShema.getPrem(),2)); // 转换计算后的保费
		// (
		// 规定的精度
		// )
		String strCalAmnt = String.valueOf(PubFun.round(pLCDutyShema.getAmnt(),2)); // 转换计算后的保额
		double calPrem = Double.parseDouble(strCalPrem);
		double calAmnt = Double.parseDouble(strCalAmnt);

		// 更新个人保单
		pLCDutyShema.setPrem(calPrem);
		/** @todo 经讨论Prem字段存实际保费，StandPrem字段存标准保费 add by heyq,pengqian 2006-5-20 */
		// pLCDutyShema.setStandPrem(calPrem);
		pLCDutyShema.setAmnt(calAmnt);

		// 更新保费项
		double tempPrem = 0;

		// double tempAmnt=0;
		for (int j = 1; j <= pLCPremBLSet.size(); j++) {
			LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);
			if (tLCPremSchema.getDutyCode().equals(dutyCode)) {
				tempPrem = PubFun.round(tLCPremSchema.getStandPrem(),2);
				tLCPremSchema.setStandPrem(tempPrem);
				pLCPremBLSet.set(j, tLCPremSchema);
			}
		}

		// 更新责任项

		tempPrem = PubFun.round(pLCDutyShema
				.getStandPrem(),2);
		pLCDutyShema.setStandPrem(tempPrem);

		return true;
	}

	/**
	 * 无名单处理人数
	 * 
	 * @return boolean
	 */
	private boolean dealPeople(LCPremBLSet pLCPremBLSet,
			LCGetBLSet pLCGetBLSet, LCDutySchema pLCDutyShema, LCPolBL mLCPolBL) {
		int tPeoples = mLCPolBL.getInsuredPeoples();
		if (tPeoples == 0) {
			// 查询合同的人数
			LCContDB tLCContDB = new LCContDB();
			LCContSet tLCContSet = new LCContSet();
			tLCContDB.setContNo(mLCPolBL.getContNo());
			tLCContSet = tLCContDB.query();
			if (tLCContSet != null && tLCContSet.size() > 0) {
				tPeoples = tLCContSet.get(1).getPeoples();
			}
		}
		for (int j = 1; j <= pLCPremBLSet.size(); j++) {
			LCPremSchema tLCPremSchema = pLCPremBLSet.get(j);

			if (tLCPremSchema.getDutyCode().equals(pLCDutyShema.getDutyCode())) {
				tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem()
						* tPeoples);
				tLCPremSchema.setPrem(tLCPremSchema.getPrem() * tPeoples);
				pLCPremBLSet.set(j, tLCPremSchema);
			}
		}
		for (int j = 1; j <= pLCGetBLSet.size(); j++) {
			LCGetSchema tLCGetSchema = pLCGetBLSet.get(j);

			if (tLCGetSchema.getDutyCode().equals(pLCDutyShema.getDutyCode())) {
				tLCGetSchema.setStandMoney(tLCGetSchema.getStandMoney()
						* tPeoples);
				tLCGetSchema.setActuGet(tLCGetSchema.getActuGet() * tPeoples);
				pLCGetBLSet.set(j, tLCGetSchema);
			}
		}
		pLCDutyShema.setStandPrem(pLCDutyShema.getStandPrem() * tPeoples);
		pLCDutyShema.setPrem(pLCDutyShema.getPrem() * tPeoples);
		pLCDutyShema.setAmnt(pLCDutyShema.getAmnt() * tPeoples);
		pLCDutyShema.setRiskAmnt(pLCDutyShema.getRiskAmnt() * tPeoples);
		return true;
	}

	/**
	 * 从保单信息中获取责任表中需要计算的以外的信息
	 */
	private void getDutyOtherData() {
		int n = mLCDutyBLSet.size();
		for (int i = 1; i <= n; i++) {
			LCDutySchema tLCDutySchema = mLCDutyBLSet.get(i);

			// 从保单带过来的信息
			// PQ1 需要设一些从LCCont带过来的值
			if (tLCDutySchema.getPolNo() == null) {
				tLCDutySchema.setPolNo(mLCPolBL.getPolNo());
			}
			if (tLCDutySchema.getContNo() == null) {
				tLCDutySchema.setContNo(mLCPolBL.getContNo());
			}
			if (tLCDutySchema.getMult() == 0.0) {
				tLCDutySchema.setMult(mLCPolBL.getMult());
			}
			if (tLCDutySchema.getFirstPayDate() == null) {
				tLCDutySchema.setFirstPayDate(mLCPolBL.getCValiDate());
			}
			if (tLCDutySchema.getStandPrem() == 0.0) {
				if (tLCDutySchema.getPrem() == 0.0) {
					tLCDutySchema.setPrem(mLCPolBL.getPrem());
					tLCDutySchema.setStandPrem(mLCPolBL.getStandPrem());
				} else {
					tLCDutySchema.setStandPrem(tLCDutySchema.getPrem());
				}
			}

			if (tLCDutySchema.getAmnt() == 0.0) {
				tLCDutySchema.setAmnt(mLCPolBL.getAmnt());
			}
			if (tLCDutySchema.getRiskAmnt() == 0.0) {
				tLCDutySchema.setRiskAmnt(mLCPolBL.getRiskAmnt());
			}
			if (tLCDutySchema.getLiveGetMode() == null) {
				tLCDutySchema.setLiveGetMode(mLCPolBL.getLiveGetMode());
			}
			if (tLCDutySchema.getDeadGetMode() == null) {
				tLCDutySchema.setDeadGetMode(mLCPolBL.getDeadGetMode());
			}
			if (tLCDutySchema.getBonusGetMode() == null) {
				tLCDutySchema.setBonusGetMode(mLCPolBL.getBonusGetMode());
			}
			if (tLCDutySchema.getCurrency() == null) {
				tLCDutySchema.setCurrency(mLCPolBL.getCurrency());
			}//币种信息
			tLCDutySchema.setContNo(mLCPolBL.getContNo());
			tLCDutySchema.setOperator(mLCPolBL.getOperator());
			tLCDutySchema.setMakeDate(PubFun.getCurrentDate());
			tLCDutySchema.setMakeTime(PubFun.getCurrentTime());
			
			mLCDutyBLSet.set(i, tLCDutySchema);
		}
	}

	/**
	 * 通过描述取得责任信息的结构
	 * 
	 * @return boolean
	 */
	private boolean getDutyStructure() {
		// 从险种责任表中取出责任的结构
		PD_LMRiskDutySet tPD_LMRiskDutySet = mCRI
				.findRiskDutyByRiskCodeClone(mLCPolBL.getRiskCode());

		if (tPD_LMRiskDutySet == null) {
			// @@错误处理
			this.mErrors.copyAllErrors(mCRI.mErrors);
			mCRI.mErrors.clearErrors();
			CError.buildErr(this, "PD_LMRiskDuty表查询失败!");
			return false;
		}

		// 把数据装入mLCDutyBLSet中
		mLCDutyBLSet = new LCDutyBLSet();
		int n = tPD_LMRiskDutySet.size();
		for (int i = 1; i <= n; i++) {
			if (!StrTool.cTrim(tPD_LMRiskDutySet.get(i).getChoFlag()).equals("M")) {
				// 非必选责任排除.
				continue;
			}

			LCDutyBL tLCDutyBL = new LCDutyBL();
			tLCDutyBL.setDutyCode(tPD_LMRiskDutySet.get(i).getDutyCode());

			if (mLCDutyBLIn != null) { // 录入优先
				tLCDutyBL.setPayIntv(mLCDutyBLIn.getPayIntv());
				tLCDutyBL.setInsuYearFlag(mLCDutyBLIn.getInsuYearFlag());
				tLCDutyBL.setInsuYear(mLCDutyBLIn.getInsuYear());
				tLCDutyBL.setAcciYearFlag(mLCDutyBLIn.getAcciYearFlag());
				tLCDutyBL.setAcciYear(mLCDutyBLIn.getAcciYear());
				tLCDutyBL.setPayEndYear(mLCDutyBLIn.getPayEndYear());
				tLCDutyBL.setPayEndYearFlag(mLCDutyBLIn.getPayEndYearFlag());
				tLCDutyBL.setGetYear(mLCDutyBLIn.getGetYear());
				tLCDutyBL.setGetYearFlag(mLCDutyBLIn.getGetYearFlag());
				tLCDutyBL.setStandbyFlag1(mLCDutyBLIn.getStandbyFlag1());
				tLCDutyBL.setStandbyFlag2(mLCDutyBLIn.getStandbyFlag2());
				tLCDutyBL.setStandbyFlag3(mLCDutyBLIn.getStandbyFlag3());
				tLCDutyBL.setMult(mLCDutyBLIn.getMult());
				tLCDutyBL.setCalRule(mLCDutyBLIn.getCalRule());
				tLCDutyBL.setFloatRate(mLCDutyBLIn.getFloatRate());
				tLCDutyBL.setPremToAmnt(mLCDutyBLIn.getPremToAmnt());
				tLCDutyBL.setGetLimit(mLCDutyBLIn.getGetLimit());
				tLCDutyBL.setGetRate(mLCDutyBLIn.getGetRate());
				tLCDutyBL.setSSFlag(mLCDutyBLIn.getSSFlag());
				tLCDutyBL.setPeakLine(mLCDutyBLIn.getPeakLine());
				tLCDutyBL.setCurrency(mLCDutyBLIn.getCurrency());//币种信息
			}

			mLCDutyBLSet.add(tLCDutyBL);
		}

		return true;
	}

	/**
	 * 从保单信息或责任信息中获取领取项表中需要计算的以外的信息
	 */
	private void getGetOtherData() {
		int n = mLCGetBLSet.size();
		for (int i = 1; i <= n; i++) {
			LCGetSchema tLCGetSchema = mLCGetBLSet.get(i);
			// PQ1 需要设一些从LCCont获得的值
			// 从保单获得的信息
			tLCGetSchema.setPolNo(mLCPolBL.getPolNo());
			tLCGetSchema.setContNo(mLCPolBL.getContNo());
			tLCGetSchema.setGrpContNo(mLCPolBL.getGrpContNo());
			// tLCGetSchema.setAppntNo( mLCPolBL.getAppntNo() );
			// tLCGetSchema.setAppntType( mLCPolBL.getContType() );

			tLCGetSchema.setInsuredNo(mLCPolBL.getInsuredNo());

			tLCGetSchema.setGetMode(mLCPolBL.getLiveGetMode());
			tLCGetSchema.setManageCom(mLCPolBL.getManageCom());
			tLCGetSchema.setCurrency(mLCPolBL.getCurrency());//币种信息 GET与保单险种币种一致

			mLCGetBLSet.set(i, tLCGetSchema);
		}
	}

	/**
	 * 通过描述取得保费项信息的结构
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 * @return boolean
	 */
	private boolean getGetStructure(LCDutySchema mLCDutySchema) {
		PD_LMDutyGetRelaSet tPD_LMDutyGetRelaSet = mCRI
				.findDutyGetRelaByDutyCodeClone(mLCDutySchema.getDutyCode()
						.substring(0, 6));

		if (tPD_LMDutyGetRelaSet == null) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据!");
			return false;
		}

		// 把数据装入mLCGetBLSet中
		int n = tPD_LMDutyGetRelaSet.size();
		for (int i = 1; i <= n; i++) {
			LCGetBL tLCGetBL = new LCGetBL();
			tLCGetBL.setDutyCode(mLCDutySchema.getDutyCode());
			tLCGetBL.setGetRate(mLCDutySchema.getGetRate());
			tLCGetBL.setGetDutyCode(tPD_LMDutyGetRelaSet.get(i).getGetDutyCode());

			mLCGetBLSet.add(tLCGetBL);
		}
		return true;
	}

	public LCDutyBLSet getLCDuty() {
		return mLCDutyBLSet;
	}

	public LCGetBLSet getLCGet() {
		return mLCGetBLSet;
	}

	public LCPolBL getLCPol() {
		System.out.println(mLCPolBL.getPrem());

		return mLCPolBL;
	}

	public LCPremBLSet getLCPrem() {
		return mLCPremBLSet;
	}

	/**
	 * 从保单信息或责任信息中获取保费项表中需要计算的以外的信息
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 */
	private void getPremOtherData(LCDutySchema mLCDutySchema) {
		int n = mLCPremBLSet.size();
		ExeSQL tExeSQL = new ExeSQL();
		for (int i = 1; i <= n; i++) {
			LCPremSchema tLCPremSchema = mLCPremBLSet.get(i);
			//tongmeng 2010-11-17 modify
			//对多责任的处理
			if(!mLCDutySchema.getDutyCode().equals(tLCPremSchema.getDutyCode()))
			{
				continue;
			}
			
			
			// PQ1 设置从LCCont中获得的值
			// 从保单获得的信息
			if (tLCPremSchema.getPolNo() == null) {
				tLCPremSchema.setPolNo(mLCPolBL.getPolNo());
			}
			if (tLCPremSchema.getContNo() == null) {
				tLCPremSchema.setContNo(mLCPolBL.getContNo());
			}
			if (tLCPremSchema.getGrpContNo() == null) {
				tLCPremSchema.setGrpContNo(mLCPolBL.getGrpContNo());
			}

			if (tLCPremSchema.getAppntNo() == null) {
				tLCPremSchema.setAppntNo(mLCPolBL.getAppntNo());
			}
			if (tLCPremSchema.getAppntType() == null) {
				tLCPremSchema.setAppntType(mLCPolBL.getContType());
			}

			if (tLCPremSchema.getPayStartDate() == null) {
				tLCPremSchema.setPayStartDate(mLCPolBL.getCValiDate());
			}
			if (tLCPremSchema.getManageCom() == null) {
				tLCPremSchema.setManageCom(mLCPolBL.getManageCom());
			}
			
//			String tPremCurrency = "";
//			String tSQL_currency = " select distinct currency from lmriskinsuacc where insuaccno in "
//					   			 + " (select insuaccno from lmriskaccpay where payplancode='"+tLCPremSchema.getPayPlanCode()+"')";
//			tPremCurrency = tExeSQL.getOneValue(tSQL_currency);
//			if (tLCPremSchema.getCurrency() == null) {
//				//tongmeng 2010-11-30 modify
//				
//				if(tPremCurrency!=null&&!tPremCurrency.equals(""))
//				{
//					tLCPremSchema.setCurrency(tPremCurrency);
//				}
//				else
//				{
//					tLCPremSchema.setCurrency(mLCPolBL.getCurrency());//币种信息
//					
//				}
//			}
			////////////////////////////////////////////////////////////////////
			// //////////////////////////
			// 保费项表暂时处理管理费比例的方法是：从保单表的管理费比例（暂时存储）中取出
			// 1。民生常青基业的处理方式是保单的管理费比例和保费项上的管理费比例是一样的，并且保单表和保费表是一一对应的
			// 2。众悦团体年金的处理方式是个单的管理费比例取于团单的管理费比例，故每一张个单的管理费比例相同
			// add by guoxiang at 2004-9-7
			////////////////////////////////////////////////////////////////////
			// /////////////////////////
			// 从责任表获得的信息
			if (!(tLCPremSchema.getDutyCode().equals("610001"))&&!(tLCPremSchema.getDutyCode().equals("610002"))&&
					!(tLCPremSchema.getDutyCode().equals("610003"))&&!(tLCPremSchema.getDutyCode().equals("610004"))&&
					!(tLCPremSchema.getDutyCode().equals("610005"))&&!(tLCPremSchema.getDutyCode().equals("610006"))&&
					!(tLCPremSchema.getDutyCode().equals("610007"))&&!(tLCPremSchema.getDutyCode().equals("610008"))&&
					!(tLCPremSchema.getDutyCode().equals("610009")))
			{
			   if (tLCPremSchema.getStandPrem() == 0.0)   {
				  tLCPremSchema.setStandPrem(mLCDutySchema.getStandPrem());
			   }
			}

			// 保全保全人工核保的需要add by sxy 2004-03-16
			tLCPremSchema.setState("0");
			tLCPremSchema.setPayIntv(mLCDutySchema.getPayIntv());
			tLCPremSchema.setPayTimes("1");
			mLCPremBLSet.set(i, tLCPremSchema);
		}
	}

	/**
	 * 通过描述取得保费项信息的结构
	 * 
	 * @param mLCDutySchema
	 *            LCDutySchema
	 * @return boolean
	 */
	private boolean getPremStructure(LCDutySchema mLCDutySchema) {
		PD_LMDutyPayRelaSet tPD_LMDutyPayRelaSet = mCRI
				.findDutyPayRelaByDutyCodeClone(mLCDutySchema.getDutyCode()
						.substring(0, 6));

		if (tPD_LMDutyPayRelaSet == null) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据!");
			return false;
		}

		// 把数据装入mLCPremBLSet中
		int n = tPD_LMDutyPayRelaSet.size();
		for (int i = 1; i <= n; i++) {
			LCPremBL tLCPremBL = new LCPremBL();
			tLCPremBL.setDutyCode(mLCDutySchema.getDutyCode());
			tLCPremBL.setPayPlanCode(tPD_LMDutyPayRelaSet.get(i).getPayPlanCode());

			mLCPremBLSet.add(tLCPremBL);
		}
		return true;
	}

	/**
	 * 准备描述数据--准备mPD_LMDutyCtrlSet数据
	 * 
	 * @return boolean
	 */
	private boolean preDefineInfo() {
		// 责任录入控制信息
		mPD_LMDutyCtrlSet = new PD_LMDutyCtrlSet();
		PD_LMRiskDutySet tPD_LMRiskDutySet = mCRI
				.findRiskDutyByRiskCodeClone(mLCPolBL.getRiskCode());

		if (tPD_LMRiskDutySet == null) {
			// @@错误处理
			this.mErrors.copyAllErrors(mCRI.mErrors);
			mCRI.mErrors.clearErrors();
			CError.buildErr(this, "PD_LMRiskDuty表查询失败!");
			return false;
		}

		System.out.println("in preDefineInfo()");

		int n = tPD_LMRiskDutySet.size();
		for (int i = 1; i <= n; i++) {
			PD_LMRiskDutySchema tPD_LMRiskDutySchema = tPD_LMRiskDutySet.get(i);
			PD_LMDutyCtrlSet tPD_LMDutyCtrlSet = mCRI
					.findDutyCtrlByDutyCodeClone(tPD_LMRiskDutySchema
							.getDutyCode());

			if (tPD_LMDutyCtrlSet == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError.buildErr(this, "PD_LMDutyCtrl表查询失败!");
				return false;
			}
			mPD_LMDutyCtrlSet.add(tPD_LMDutyCtrlSet);
		}

		return true;
	}

	//
	public boolean SetLCPolOther(LCPolOtherSchema tLCPolOtherSchema) {
		mLCPolOtherSchema.setSchema(tLCPolOtherSchema);
		return true;
	}

	/**
	 * 从外部给标志赋值
	 * 
	 * @param parmFlag
	 *            boolean
	 */
	public void setNoCalFalg(boolean parmFlag) {
		noCalFlag = parmFlag;
	}

	public void setOperator(String tOperator) {
		mOperator = tOperator;
	}

}
