/*
 * <p>ClassName: BqCalBase </p>
 * <p>Description: 保全计算基础要素类文件 </p>
 * <p>Description: 所有方法均是为内部成员变量存取值，set开头为存，get开头为取,方式源于CalBase,要素描述属于保全 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: TJJ
 * @CreateDate：2002-10-02
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

public class BqCalBase {
private static Logger logger = Logger.getLogger(BqCalBase.class);
	// @Field
	/** 保费 */
	private double Prem;
	/** 累计保费 */
	private double SumPrem;
	/** 保额 */
	private double Get;
	/** 份数 */
	private double Mult;
	/** 缴费间隔 */
	private int PayIntv;
	/** 领取间隔 */
	private int GetIntv;
	/** 领取开始年龄年期标志 */
	private String GetStartFlag;
	/** 交费止期年龄年期标志 */
	private String PayEndYearFlag;
	/** 缴费终止年期或年龄 */
	private int PayEndYear;
	/** 领取开始年期 */
	private int GetStartYear;
	/** 领取开始年龄 */
	private int GetStartAge;
	/** 领取时投保年期 */
	private int GetAppYear;
	/** 领取时领取年期 */
	private int GetYear;
	/** 领取时年龄 */
	private int GetAge;
	/** 领取次数 */
	private int GetTimes;
	/** 保险期间 */
	private int Years;
	/** 被保人投保年龄 */
	private int AppAge;
	/** 被保人性别 */
	private String Sex;
	/** 被保人工种 */
	private String Job;
	/** 责任给付编码 */
	private String GDuty;
	/** 投保人数 */
	private int Count;
	/** 递增率 */
	private double AddRate;
	/** 险种保单号 */
	private String PolNo;
	/** 集体险种保单号 */
	private String GrpPolNo;
	/** 合同保单号 */
	private String ContNo;
	/** 集体合同保单号 */
	private String GrpContNo;
	/** 投保人号 */
	private String AppntNo;
	/** 被保人号 */
	private String InsuredNo;
	/** 连带被保人号* */
	private String SecondInsuredNo;
	/** 时间间隔 */
	private int Interval;
	/** 时间间隔月 */
	private int IntervalM;
	/** 应交已交 */
	private String PayNextFlag;
	/** 限制时间间隔 */
	private int LimitDay;
	/** 保单失效标志 */
	private int PolValiFlag; // 1无效 ；0有效
	/** 借款金额 */
	private double LoanMoney;
	/** 限制时间间隔 */
	private double TrayMoney;
	/** 操作员编码* */
	private String Operator;
	/** 保全申请号 */
	private String EdorAcceptNo;
	/** 保全批单号 */
	private String EdorNo;
	/** 保全类型 */
	private String EdorType;
	/** 批改生效日期 */
	private String EdorValiDate;
	/** 交退费金额 */
	private double GetMoney;
	/** 起领日期 */
	private String GetStartDate;
	/** 起保日期 */
	private String CValiDate;
	/** 帐户转年金金额 */
	private double GetBalance;
	/** 险种编码 */
	private String RiskCode;
	/**冲减保额*/
	private double ReduceAmnt;
	/** 浮动费率 */
	private double FloatRate;
	/** 保险期间 */
	private int InsuYear;
	/** 保险期间标志（单位） */
	private String InsuYearFlag;
	/** 保单通用字段 */
	private String StandByFlag1;

	/** 舍弃法保单年度* */
	private String DownPolYears;
	/** 约进法保单年度* */
	private String UpPolYears;
	/** 最近一期保费 */
	private double LastPrem;
	/** 退保点* */
	private String ZTPoint;
	/** 交至日期* */
	private String PayToDate;
	/** 终止日期* */
	private String EndDate;
	/** 保全申请日期* */
	private String EdorAppDate;
	/** 保全重算后保费* */
	private double NewPrem;
	/** 计算类型 */
	private String CalType;
	/** 保险剩余期限 */
	private int RemainYear;
	/** 利率开始时间 */
	private String StartDate;
	/** 附加险加保年限 */
	private int AAYears;
	/** 风险保额 */
	private double SumDangerAmnt;
	/** 失效保单风险保额 */
	private double SumInvaliAmnt;
	/**当期保费 */
	private double NextPrem;
	/** 给付责任代码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付次数 */
	private int GetCount;
	/** 生调间隔周期内累计领取生存金（含本次到期续期领取金额） */
	private double SumGet;
	/** 续期领取时的现金价值 */
	private double CashValue;
	/** 有效保险金额（基本保险金额＋累计红利保险金额） */
	private double AvailableAmnt;
	/** 终了红利 */
	private double FinalBonus;
	/** 剩余月数 */
	private int IntvMonth;
	/** 责任编码 */
	private String DutyCode;
	/** 保额 */
	private double Amnt;
	/** 单位保额 */
	private double VPU;
	/**第t个保单年度现价*/
	private double CVt;
	/**第t+1个保单年度现价*/
	private double CVt1;
	/** 累计红利保险金额 */
	private double SumAmntBonus;
	/** 保费（包括健康加费和职业加费） */
	private double TotalPrem;
	/** 缴费次数 */
	private int PayTimes;
	/** 新增附加险年限 */
	private String NSMonths;
	/** 分红率 */
	private double BonusRate;
	/** 强制分红标志 */
	private String ForceDVFlag;
	/** 是否具有现金价值（Y/N） */
	private String CashFlag;
	/** 是否在犹豫期 */
	private String HesitateFlag;
	/** 保单借款总和（贷款加自垫本息和） */
	private double ContSumLoan;
	/** 保险期限 */
	private int InsuYearT1;
	/** 剩余保险期限 */
	private int InsuYearT2;
	/** 主险保单号* */
	private String MainPolNo;
	/** 管理机构 */
	private String ManageCom;
	/** 管理机构 */
	private int Intv;
	/** 保全算法 */
	private String EdorTypeCal;
	/** 受益人类别 */
	private String BnfType;
	/* 交费收据号 */
	private String PayNo;
	/* 保全申请日期 */
	private String EdorItemAppDate;
	/* 卡单标记1－保单，0－卡单 */
	private String CardFlag;
	/** 团单生效日 */
	private String GrpCValiDate;
	/** 帐户型产品退保金额*/
    private double ZTMoneyByAcc;
    /**基本保额对应生存领取金额*/
    private double AliveGet;
    /**生存领取金占有效保额的比例*/
    private double AliveGetRate;
    /**第k+1保单年度内退保前累计的基本保额对应生存领取金额*/
    private double SumYearGet;
    /**第k+1保单年度内退保前累计的生存领取金占有效保额的比例*/
    private double SumYearGetRate;
    /** 退保原因*/
    private String EdorReasonCode;
    /**2006-8-10,为险种630添加不需要经过计算比率的基本保额对应生存领取金额*/
    private double liveGet;
	/** 第二被保险人年龄 */
	private String AppAg2;
	
	private double TBRate;
	
	private String Birthday;
	//保全对应周年日
	private String StrSDate;
	
	private int Duration;
	/**首创安泰投连计算*/
	private int AppntAge;
	/** 起付线* */
	private double GetLimit;
	/** 赔付比例* */
	private double GetRate;
	/**扣除上年年末生存领取*/
	private double CVtGetDraw;
	/**扣除上年年末生存领取*/
	private double CVt1GetDraw;
	/** 退保点所在保单年度的经过天数*/
	private int ThroughDay;
	/**期初时刻点所在保单年度的经过天数*/
	private int ThroughDays;
	/**期末的时刻点所在保单年度的经过天数*/
	private int ThroughDays1;
	/**已交保费次数（月/半年/季）/退保点所在年度末的应缴费次数（月/半年/季）*/
	private double MonsRate;
	/**签单日期*/
	private String SignDate;
	/**第t+1个保单年度末单位红利保额的现金价值*/
	private double GVt1;
	/**第t个保单年度末单位红利保额的现金价值*/
	private double GVt;
	/**当前日期*/
	private String CURValidate;
	private String LoanDate;
	private String QHFlag;
	
	private String PayEndDate;
	// @Constructor
	public BqCalBase() {
	}
	public void setLoanDate(String tLoanDate) {
		LoanDate = tLoanDate;
	}

	public String getLoanDate() {
		return LoanDate;
	}
	public void setQHFlag(String tQHFlag) {
		QHFlag = tQHFlag;
	}

	public String getQHFlag() {
		return QHFlag;
	}
	
	public void setBirthday(String tBirthday) {
		Birthday = tBirthday;
	}

	public String getBirthday() {
		return Birthday;
	}
	public void setPayEndDate(String tPayEndDate) {
		PayEndDate = tPayEndDate;
	}
	

	public String getStrSDate() {
		return StrSDate;
	}
	public void setStrSDate(String tStrSDate) {
		StrSDate = tStrSDate;
	}
	public String getPayEndDate() {
		return PayEndDate;
	}
	public void setCURValidate(String tCURValidate) {
		CURValidate = tCURValidate;
	}

	public String getCURValidate() {
		return CURValidate;
	}
	
	public void setGVt1(Double tGVt1) {
		GVt1 = tGVt1;
	}

	public String getGVt1() {
		return String.valueOf(GVt1);
	}
	
	public void setGVt(Double tGVt) {
		GVt = tGVt;
	}
	public String getGVt() {
		return String.valueOf(GVt);
	}

	public void setTBRate(Double tTBRate) {
		TBRate = tTBRate;
	}
	public String getTBRate() {
		return String.valueOf(TBRate);
	}
	
	public void setSignDate(String tSignDate) {
		SignDate = tSignDate;
	}

	public String getSignDate() {
		return SignDate;
	}
	
	public void setThroughDay(int tThroughDay) {
		ThroughDay = tThroughDay;
	}

	public String getThroughDay() {
		return String.valueOf(ThroughDay);
	}
	public void setThroughDays(int tThroughDays) {
		ThroughDays = tThroughDays;
	}

	public String getThroughDays() {
		return String.valueOf(ThroughDays);
	}
	public void setThroughDays1(int tThroughDays1) {
		ThroughDays1 = tThroughDays1;
	}

	public String getThroughDays1() {
		return String.valueOf(ThroughDays1);
	}
	
	public void setMonsRate(Double tMonsRate) {
		MonsRate = tMonsRate;
	}

	public String getMonsRate() {
		return String.valueOf(MonsRate);
	}
	
	
	public void setGetRate(double tGetRate) {
		GetRate = tGetRate;
	}

	public String getGetRate() {
		return String.valueOf(GetRate);
	}
	
	public void setCVtGetDraw(double tCVtGetDraw) {
		CVtGetDraw = tCVtGetDraw;
	}

	public String getCVtGetDraw() {
		return String.valueOf(CVtGetDraw);
	}
	
	public void setCVt1GetDraw(double tCVt1GetDraw) {
		CVt1GetDraw = tCVt1GetDraw;
	}

	public String getCVt1GetDraw() {
		return String.valueOf(CVt1GetDraw);
	}

	public void setGetLimit(double tGetLimit) {
		GetLimit = tGetLimit;
	}

	public String getGetLimit() {
		return String.valueOf(GetLimit);
	}

	public void setAppAg2(String tAppAg2) {
		AppAg2 = tAppAg2;
	}

	public String getAppAg2() {
		return AppAg2;
	}
	public void setAppntAge(int tAppntAge) {
		AppntAge = tAppntAge;
	}

	public String getAppntAge() {
		return String.valueOf(AppntAge);
	}

	public void setDuration(int tDuration) {
		Duration = tDuration;
	}

	public String getDuration() {
		return String.valueOf(Duration);
	}
	public void setAliveGet(Double tAliveGet) {
		AliveGet = tAliveGet;
	}

	public String getAliveGet() {
		return String.valueOf(AliveGet);
	}
	
	public void setAliveGetRate(Double tAliveGetRate) {
		AliveGetRate = tAliveGetRate;
	}

	public String getAliveGetRate() {
		return String.valueOf(AliveGetRate);
	}
	public void setSumYearGet(Double tSumYearGet) {
		SumYearGet = tSumYearGet;
	}

	public String getSumYearGet() {
		return String.valueOf(SumYearGet);
	}
	public void setEdorReasonCode(String tEdorReasonCode) {
		EdorReasonCode = tEdorReasonCode;
	}

	public String getEdorReasonCode() {
		return String.valueOf(EdorReasonCode);
	}
	public void setliveGet(Double tliveGet) {
		liveGet = tliveGet;
	}

	public String getliveGet() {
		return String.valueOf(liveGet);
	}
	
	public void setSumYearGetRate(Double tSumYearGetRate) {
		SumYearGetRate = tSumYearGetRate;
	}

	public String getSumYearGetRate() {
		return String.valueOf(SumYearGetRate);
	}
	
	
	
	
	public void setEdorAppDate(String tEdorAppDate) {
		EdorAppDate = tEdorAppDate;
	}

	public String getEdorAppDate() {
		return EdorAppDate;
	}

	public void setDownPolYears(String tDownPolYears) {
		DownPolYears = tDownPolYears;
	}

	public String getDownPolYears() {
		return DownPolYears;
	}

	public void setUpPolYears(String tUpPolYears) {
		UpPolYears = tUpPolYears;
	}

	public String getUpPolYears() {
		return UpPolYears;
	}

	public void setZTPoint(String tZTPoint) {
		ZTPoint = tZTPoint;
	}

	public String getZTPoint() {
		return ZTPoint;
	}

	public void setPayToDate(String tPayToDate) {
		PayToDate = tPayToDate;
	}

	public String getPayToDate() {
		return PayToDate;
	}

	public void setPayNextFlag(String tPayNextFlag) {
		PayNextFlag = tPayNextFlag;
	}

	public String getPayNextFlag() {
		return PayNextFlag;
	}

	// @Method
	public void setInsuYear(int tInsuYear) {
		InsuYear = tInsuYear;
	}

	public String getInsuYear() {
		return String.valueOf(InsuYear);
	}

	public void setInsuYearFlag(String tInsuYearFlag) {
		InsuYearFlag = tInsuYearFlag;
	}

	public String getInsuYearFlag() {
		return InsuYearFlag;
	}


	public void  setZTMoneyByAcc(Double mZTMoneyByAcc)
	{
		ZTMoneyByAcc=mZTMoneyByAcc;
	}
	public String getZTMoneyByAcc()
	{
		return String.valueOf(ZTMoneyByAcc);
	}
	
	public void setIntervalM(int tIntervalM) {
		IntervalM = tIntervalM;
	}
	public String getIntervalM() {
		return String.valueOf(IntervalM);
	}
	public void setInterval(int tInterval) {
		Interval = tInterval;
	}
	public String getInterval() {
		return String.valueOf(Interval);
	}

	public void setLimitDay(int tLimitDay) {
		LimitDay = tLimitDay;
	}

	public String getLimitDay() {
		return String.valueOf(LimitDay);
	}

	public void setPolValiFlag(int tPolValiFlag) {
		PolValiFlag = tPolValiFlag;
	}

	public String getPolValiFlag() {
		return String.valueOf(PolValiFlag);
	}

	public String getLoanMoney() {
		return String.valueOf(LoanMoney);
	}

	public void setLoanMoney(double tLoanMoney) {
		LoanMoney = tLoanMoney;
	}

	public String getContSumLoan() {
		return String.valueOf(ContSumLoan);
	}

	public void setContSumLoan(double tContSumLoan) {
		ContSumLoan = tContSumLoan;
	}

	public String getTrayMoney() {
		return String.valueOf(TrayMoney);
	}

	public void setTrayMoney(double tTrayMoney) {
		TrayMoney = tTrayMoney;
	}

	public String getOperator() {
		return Operator;
	}

	public void setOperator(String tOperator) {
		Operator = tOperator;
	}

	public void setPrem(double tPrem) {
		Prem = tPrem;
	}

	public String getPrem() {
		return String.valueOf(Prem);
	}

	public void setSumPrem(double tSumPrem) {
		SumPrem = tSumPrem;
	}

	public String getSumPrem() {
		return String.valueOf(SumPrem);
	}

	public void setGet(double tGet) {
		Get = tGet;
	}

	public String getGet() {
		return String.valueOf(Get);
	}

	public void setMult(double tMult) {
		Mult = tMult;
	}

	public String getMult() {
		return String.valueOf(Mult);
	}

	public void setAddRate(double tAddRate) {
		AddRate = tAddRate;
	}

	public String getAddRate() {
		return String.valueOf(AddRate);
	}

	public void setPayIntv(int tPayIntv) {
		PayIntv = tPayIntv;
	}

	public String getPayIntv() {
		return String.valueOf(PayIntv);
	}

	public void setGetIntv(int tGetIntv) {
		GetIntv = tGetIntv;
	}

	public String getGetIntv() {
		return String.valueOf(GetIntv);
	}

	public void setPayEndYear(int tPayEndYear) {
		PayEndYear = tPayEndYear;
	}

	public String getPayEndYear() {
		return String.valueOf(PayEndYear);
	}

	public void setGetYear(int tGetYear) {
		GetYear = tGetYear;
	}

	public String getGetYear() {
		return String.valueOf(GetYear);
	}

	public void setGetAppYear(int tGetAppYear) {
		GetAppYear = tGetAppYear;
	}

	public String getGetAppYear() {
		return String.valueOf(GetAppYear);
	}

	public void setGetAge(int tGetAge) {
		GetAge = tGetAge;
	}

	public String getGetAge() {
		return String.valueOf(GetAge);
	}

	public void setGetStartYear(int tGetStartYear) {
		GetStartYear = tGetStartYear;
	}

	public String getGetStartYear() {
		return String.valueOf(GetStartYear);
	}

	public void setGetStartAge(int tGetStartAge) {
		GetStartAge = tGetStartAge;
	}

	public String getGetStartAge() {
		return String.valueOf(GetStartAge);
	}

	public void setGetTimes(int tGetTimes) {
		GetTimes = tGetTimes;
	}

	public String getGetTimes() {
		return String.valueOf(GetTimes);
	}

	public void setYears(int tYears) {
		Years = tYears;
	}

	public String getYears() {
		return String.valueOf(Years);
	}

	public void setAppAge(int tAppAge) {
		AppAge = tAppAge;
	}

	public String getAppAge() {
		return String.valueOf(AppAge);
	}

	public void setCount(int tCount) {
		Count = tCount;
	}

	public String getCount() {
		return String.valueOf(Count);
	}

	public void setSex(String tSex) {
		Sex = tSex;
	}

	public String getSex() {
		return Sex;
	}

	public void setJob(String tJob) {
		Job = tJob;
	}

	public String getJob() {
		return Job;
	}

	public void setGDuty(String tGDuty) {
		GDuty = tGDuty;
	}

	public String getGDuty() {
		return GDuty;
	}

	public void setPolNo(String tPolNo) {
		PolNo = tPolNo;
	}

	public String getPolNo() {
		return PolNo;
	}

	public void setGrpPolNo(String tGrpPolNo) {
		GrpPolNo = tGrpPolNo;
	}

	public String getGrpPolNo() {
		return GrpPolNo;
	}

	public void setContNo(String tContNo) {
		ContNo = tContNo;
	}

	public String getContNo() {
		return ContNo;
	}

	public void setGrpContNo(String tGrpContNo) {
		GrpContNo = tGrpContNo;
	}

	public String getGrpContNo() {
		return GrpContNo;
	}

	public void setAppntNo(String tAppntNo) {
		AppntNo = tAppntNo;
	}

	public String getAppntNo() {
		return AppntNo;
	}

	public void setInsuredNo(String tInsuredNo) {
		InsuredNo = tInsuredNo;
	}

	public String getInsuredNo() {
		return InsuredNo;
	}

	public void setSecondInsuredNo(String tSecondInsuredNo) {
		SecondInsuredNo = tSecondInsuredNo;
	}

	public String getSecondInsuredNo() {
		return (SecondInsuredNo);
	}

	public void setEdorAcceptNo(String tEdorAcceptNo) {
		EdorAcceptNo = tEdorAcceptNo;
	}

	public String getEdorAcceptNo() {
		return EdorAcceptNo;
	}

	public void setEdorNo(String tEdorNo) {
		EdorNo = tEdorNo;
	}

	public String getEdorNo() {
		return EdorNo;
	}

	public void setEdorType(String tEdorType) {
		EdorType = tEdorType;
	}

	public String getEdorType() {
		return EdorType;
	}

	public void setGetMoney(double tGetMoney) {
		GetMoney = tGetMoney;
	}

	public String getGetMoney() {
		return String.valueOf(GetMoney);
	}

	public void setCValiDate(String tCValiDate) {
		CValiDate = tCValiDate;
	}

	public String getCValiDate() {
		return CValiDate;
	}

	public void setEdorValiDate(String tEdorValiDate) {
		EdorValiDate = tEdorValiDate;
	}

	public String getEdorValiDate() {
		return EdorValiDate;
	}

	public void setGetStartDate(String tGetStartDate) {
		GetStartDate = tGetStartDate;
	}

	public String getGetStartDate() {
		return GetStartDate;
	}

	public void setGetBalance(double tGetBalance) {
		GetBalance = tGetBalance;
	}

	public String getGetBalance() {
		return String.valueOf(GetBalance);
	}
	
	public void setNextPrem(double tNextPrem) {
		NextPrem = tNextPrem;
	}

	public String getNextPrem() {
		return String.valueOf(NextPrem);
	}
	
	public void setRiskCode(String tRiskCode) {
		RiskCode = tRiskCode;
	}

	public String getRiskCode() {
		return RiskCode;
	}

	public void setPayEndYearFlag(String tPayEndYearFlag) {
		PayEndYearFlag = tPayEndYearFlag;
	}

	public String getPayEndYearFlag() {
		return PayEndYearFlag;
	}

	public void setGetStartFlag(String tGetStartFlag) {
		GetStartFlag = tGetStartFlag;
	}

	public String getGetStartFlag() {
		return GetStartFlag;
	}

	public void setFloatRate(double tFloatRate) {
		FloatRate = tFloatRate;
	}

	public String getFloatRate() {
		return String.valueOf(FloatRate);
	}

	public void setStandByFlag1(String tStandByFlag1) {
		StandByFlag1 = tStandByFlag1;
	}

	public String getStandByFlag1() {
		return StandByFlag1;
	}

	public void setNewPrem(double tNewPrem) {
		NewPrem = tNewPrem;
	}

	public String getNewPrem() {
		return String.valueOf(NewPrem);
	}
	
	public void setReduceAmnt(double tReduceAmnt) {
		ReduceAmnt = tReduceAmnt;
	}

	public String getReduceAmnt() {
		return String.valueOf(ReduceAmnt);
	}
	
	public void setCalType(String tCalType) {
		CalType = tCalType;
	}

	public String getCalType() {
		return CalType;
	}

	public void setRemainYear(int tRemainYear) {
		RemainYear = tRemainYear;
	}

	public String getRemainYear() {
		return String.valueOf(RemainYear);
	}

	public void setStartDate(String tStartDate) {
		StartDate = tStartDate;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setAAYears(int tAAYears) {
		AAYears = tAAYears;
	}

	public String getAAYears() {
		return String.valueOf(AAYears);
	}

	public void setSumDangerAmnt(double tSumDangerAmnt) {
		SumDangerAmnt = tSumDangerAmnt;
	}

	public String getSumDangerAmnt() {
		return String.valueOf(SumDangerAmnt);
	}

	public void setSumInvaliAmnt(double tSumInvaliAmnt) {
		SumInvaliAmnt = tSumInvaliAmnt;
	}

	public String getSumInvaliAmnt() {
		return String.valueOf(SumInvaliAmnt);
	}

	public void setGetDutyCode(String tGetDutyCode) {
		GetDutyCode = tGetDutyCode;
	}

	public String getGetDutyCode() {
		return GetDutyCode;
	}

	public void setGetCount(int tGetCount) {
		GetCount = tGetCount;
	}

	public String getGetCount() {
		return String.valueOf(GetCount);
	}

	public void setSumGet(double tSumGet) {
		SumGet = tSumGet;
	}

	public String getSumGet() {
		return String.valueOf(SumGet);
	}

	public void setCashValue(double tCashValue) {
		CashValue = tCashValue;
	}

	public String getCashValue() {
		return String.valueOf(CashValue);
	}

	public void setAvailableAmnt(double tAvailableAmnt) {
		AvailableAmnt = tAvailableAmnt;
	}

	public String getAvailableAmnt() {
		return String.valueOf(AvailableAmnt);
	}

	public void setFinalBonus(double tFinalBonus) {
		FinalBonus = tFinalBonus;
	}

	public String getFinalBonus() {
		return String.valueOf(FinalBonus);
	}

	public void setDutyCode(String tDutyCode) {
		DutyCode = tDutyCode;
	}

	public String getDutyCode() {
		return DutyCode;
	}

	public void setAmnt(double tAmnt) {
		Amnt = tAmnt;
	}

	public String getAmnt() {
		return String.valueOf(Amnt);
	}

	public void setVPU(double tVPU) {
		VPU = tVPU;
	}

	public String getVPU() {
		return String.valueOf(VPU);
	}
	public void setCVt(double tCVt) {
		CVt = tCVt;
	}

	public String getCVt() {
		return String.valueOf(CVt);
	}
	public void setCVt1(double tCVt1) {
		CVt1 = tCVt1;
	}

	public String getCVt1() {
		return String.valueOf(CVt1);
	}
	
	public void setSumAmntBonus(double tSumAmntBonus) {
		SumAmntBonus = tSumAmntBonus;
	}

	public String getSumAmntBonus() {
		return String.valueOf(SumAmntBonus);
	}

	public void setTotalPrem(double tTotalPrem) {
		TotalPrem = tTotalPrem;
	}

	public String getTotalPrem() {
		return String.valueOf(TotalPrem);
	}

	public void setGetDutyKind(String tGetDutyKind) {
		GetDutyKind = tGetDutyKind;
	}

	public String getGetDutyKind() {
		return GetDutyKind;
	}

	public void setPayTimes(int tPayTimes) {
		PayTimes = tPayTimes;
	}

	public String getPayTimes() {
		return String.valueOf(PayTimes);
	}

	public void setNSMonths(int aNSMonths) {
		NSMonths = String.valueOf(aNSMonths);
	}

	public String getNSMonths() {
		return NSMonths;
	}

	public void setBonusRate(double aBonusRate) {
		BonusRate = aBonusRate;
	}

	public String getBonusRate() {
		return String.valueOf(BonusRate);
	}

	public void setForceDVFlag(String aForceDVFlag) {
		ForceDVFlag = aForceDVFlag;
	}

	public String getForceDVFlag() {
		return ForceDVFlag;
	}

	public void setCashFlag(String tCashFlag) {
		CashFlag = tCashFlag;
	}

	public String getCashFlag() {
		return CashFlag;
	}

	public void setHesitateFlag(String aHesitateFlag) {
		HesitateFlag = aHesitateFlag;
	}

	public String getHesitateFlag() {
		return HesitateFlag;
	}

	public void setIntvMonth(int aIntvMonth) {
		IntvMonth = aIntvMonth;
	}

	public String getIntvMonth() {
		return String.valueOf(IntvMonth);
	}

	public void setInsuYearT1(int aInsuYearT1) {
		InsuYearT1 = aInsuYearT1;
	}

	public String getInsuYearT1() {
		return String.valueOf(InsuYearT1);
	}

	public void setInsuYearT2(int aInsuYearT2) {
		InsuYearT2 = aInsuYearT2;
	}

	public String getInsuYearT2() {
		return String.valueOf(InsuYearT2);
	}

	public void setLastPrem(double aLastPrem) {
		LastPrem = aLastPrem;
	}

	public String getLastPrem() {
		return String.valueOf(LastPrem);
	}

	public void setMainPolNo(String tMainPolNo) {
		MainPolNo = tMainPolNo;
	}

	public String getMainPolNo() {
		return (MainPolNo);
	}

	public void setManageCom(String tManageCom) {
		ManageCom = tManageCom;
	}

	public String getManageCom() {
		return ManageCom;
	}

	public String getIntv() {
		return String.valueOf(Intv);
	}

	public void setIntv(int intv) {
		Intv = intv;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getEdorTypeCal() {
		return EdorTypeCal;
	}

	public void setEdorTypeCal(String edorTypeCal) {
		EdorTypeCal = edorTypeCal;
	}

	public String getBnfType() {
		return BnfType;
	}

	public void setBnfType(String bnfType) {
		BnfType = bnfType;
	}

	public String getPayNo() {
		return PayNo;
	}

	public void setPayNo(String payNo) {
		PayNo = payNo;
	}

	public String getEdorItemAppDate() {
		return EdorItemAppDate;
	}

	public void setEdorItemAppDate(String edorItemAppDate) {
		EdorItemAppDate = edorItemAppDate;
	}

	public String getCardFlag() {
		return CardFlag;
	}

	public void setCardFlag(String cardFlag) {
		CardFlag = cardFlag;
	}

	public void setNSMonths(String months) {
		NSMonths = months;
	}
	
	public String getGrpCValiDate() {
		return GrpCValiDate;
	}

	public void setGrpCValiDate(String grpCValiDate) {
		GrpCValiDate = grpCValiDate;
	}

}
