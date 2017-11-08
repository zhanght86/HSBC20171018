/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.utility.TransferData;

/**
 * <p>
 * ClassName: CalBase
 * </p>
 * <p>
 * Description: 计算基础要素类文件
 * </p>
 * <p>
 * Description: 所有方法均是为内部成员变量存取值，set开头为存，get开头为取
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: HST
 * @CreateDate：2002-07-30
 */
public class CalBase {
private static Logger logger = Logger.getLogger(CalBase.class);
	// @Field
	/** 保费 */
	private double Prem;

	/** 保额 */
	private double Get;

	/** 份数 */
	private double Mult;

	/** 缴费间隔 */
	private int PayIntv;

	/** 领取间隔 */
	private int GetIntv;

	/** 缴费终止年期或年龄 */
	private int PayEndYear;

	/** 缴费终止年期或年龄标记 */
	private String PayEndYearFlag;

	/** 领取开始年期或年龄 */
	private int GetYear;

	/** 领取开始年期或年龄标记 */
	private String GetYearFlag;

	/** 责任领取类型 */
	private String GetDutyKind;

	/** 起领日期计算参照 */
	private String StartDateCalRef;

	/** 保险期间 */
	private int Years;

	/** 保险期间 */
	private int InsuYear;

	/** 保险期间标记 */
	private String InsuYearFlag;

	/** 被保人投保年龄 */
	private int AppAge;
	/** tongmeng 2009-02-21 add 
	 *  投保人投保年龄
	 *  为MS投保人豁免险计算保费使用
	 * */
	
	private int AppntAppAge;
	
	/**首创安泰投连计算*/
	private int AppntAge;
	
	/** 被保人性别 */
	private String Sex;

	/** 被保人工种 */
	private String Job;

	/** 责任给付编码 */
	private String GDuty;

	/** 投保人数 */
	private int Count;

	/** 续保次数 */
	private int RnewFlag;

	/** 递增率 */
	private double AddRate;

	/** 个单合同号 */
	private String ContNo;

	/** 保单号 */
	private String PolNo;

	/** 原保额 */
	private double Amnt;

	/** 浮动费率 */
	private double FloatRate;

	/** 险种编码 */
	private String RiskCode;

	/** 个人保单表中用来做传递计算变量的公用元素--团体商业补充保险中传递责任选择的组合代码 */
	private String StandbyFlag1;

	/** 待用 */
	private String StandbyFlag2;

	/** 团体商业补充保险中传递在职或退休字段 */
	private String StandbyFlag3;

	/** 集体合同号 */
	private String GrpContNo;

	/** 集体保单号 */
	private String GrpPolNo;

	private String EdorNo;

	/** 计算类型 */
	private String CalType;

	/** 起付线* */
	private double GetLimit;

	/** 赔付比例* */
	private double GetRate;

	/** 社保标记* */
	private String SSFlag;

	/** 封顶线* */
	private double PeakLine;

	/** 保单生效日* */
	private String CValiDate;

	/** 额外风险评分* */
	private double SuppRiskScore;

	/** 第一被保险人加费评点 * */
	private double FirstScore;

	/** 第二被保险人加费评点 * */
	private double SecondScore;

	/** 责任编码* */
	private String DutyCode;

	/** 单位保额* */
	private String VPU;
	/** 连带被保人号* */
	private String SecondInsuredNo;
	/** 被保人号* */
	private String InsuredNo;
	/** 主险保单号* */
	private String MainPolNo;

	/** 第二被保险人年龄 */
	private String AppAg2;
	
	/** 币种信息 */
	private String Currency;

	/** 针对险种144丈夫的加费评点 */
	private double HusbandScore;

	/** 针对险种144妻子的加费评点 */
	private double WifeScore;
	/** 自核使用的变量其中mainpolno and job have already definine before* */
	private String LSumDangerAmnt; // 同一被保险人下寿险类的累计危险保额
	private String DSumDangerAmnt; // 同一被保险人下重大疾病类的累计危险保额
	private String ASumDangerAmnt; // 同一被保险人下人身意外伤害类的累计危险保额
	private String MSumDangerAmnt; // 同一被保险人下人身意外医疗类的累计危险保额
	private String SSumDangerAmnt; // 同一被保险人下人身意外医疗类的累计危险保额
	private String ManageCom; // 管理机构代码
	private String AppntJob; // 投保人职业类别
	private String MainRiskGet; // 主险保额
	private String RiskSort; // 险种类别
	private String CustomerNo; // 客户号码
	private String Occupation; // 被保险人职业
	private String MAmnt; // 主险保额
	private String AppAge2; // 连生险的第2个被保险人的投保时的年龄。
	private String ContPlanCode;// 保障计划编码
	private String GetStartType;// 领取方式
	private String PolTypeFlag;// 领取方式
	private TransferData mTransferData = new TransferData();// 用于保存传入的其他信息
	
    /**保单红利的会计年度*/
    private int BonusYear;

	// @Constructor
	public CalBase() {
	}

	// @Method，新增方法，用于处理传入的其他要素信息
	public void setOtherParm(String ParmName, String ParmVlaue) {
		mTransferData.setNameAndValue(ParmName, ParmVlaue);
	}

	public String getOtherParmVlaueByName(String ParmName) {
		return (String) mTransferData.getValueByName(ParmName);
	}

	public String getOtherParmName(int Index) {
		return (String) mTransferData.getValueNames().get(Index);
	}

	public int getAllOtherParmCount() {
		return mTransferData.getValueNames().size();
	}

	// @Method
	public void setAppAge2(String tAppAge2) {
		AppAge2 = tAppAge2;
	}

	public String getAppAge2() {
		return String.valueOf(AppAge2);
	}

	// add method for autocheck write by yaory

	public void setContNo(String tContNo) {
		ContNo = tContNo;
	}

	// add method for autocheck write by yaory
	public void setLSumDangerAmnt(String tLSumDangerAmnt) {
		LSumDangerAmnt = tLSumDangerAmnt;
	}

	public String getLSumDangerAmnt() {
		return String.valueOf(LSumDangerAmnt);
	}

	public void setSSumDangerAmnt(String tLSumDangerAmnt) {
		LSumDangerAmnt = tLSumDangerAmnt;
	}

	public String getSSumDangerAmnt() {
		return String.valueOf(LSumDangerAmnt);
	}

	public void setMAmnt(String tMAmnt) {
		MAmnt = tMAmnt;
	}

	public String getMAmnt() {
		return String.valueOf(MAmnt);
	}

	public void setDSumDangerAmnt(String tDSumDangerAmnt) {
		DSumDangerAmnt = tDSumDangerAmnt;
	}

	public String getDSumDangerAmnt() {
		return String.valueOf(DSumDangerAmnt);
	}

	public void setASumDangerAmnt(String tASumDangerAmnt) {
		ASumDangerAmnt = tASumDangerAmnt;
	}

	public String getASumDangerAmnt() {
		return String.valueOf(ASumDangerAmnt);
	}

	public void setMSumDangerAmnt(String tMSumDangerAmnt) {
		MSumDangerAmnt = tMSumDangerAmnt;
	}

	public String getMSumDangerAmnt() {
		return String.valueOf(MSumDangerAmnt);
	}

	public void setManageCom(String tManageCom) {
		ManageCom = tManageCom;
	}

	public String getManageCom() {
		return String.valueOf(ManageCom);
	}

	public void setAppntJob(String tAppntJob) {
		AppntJob = tAppntJob;
	}

	public String getAppntJob() {
		return String.valueOf(AppntJob);
	}

	public void setMainRiskGet(String tMainRiskGet) {
		MainRiskGet = tMainRiskGet;
	}

	public String getMainRiskGet() {
		return String.valueOf(MainRiskGet);
	}

	public void setRiskSort(String tRiskSort) {
		RiskSort = tRiskSort;
	}

	public String getRiskSort() {
		return String.valueOf(RiskSort);
	}

	public void setCustomerNo(String tCustomerNo) {
		CustomerNo = tCustomerNo;
	}

	public String getCustomerNo() {
		return String.valueOf(CustomerNo);
	}

	public void setOccupation(String tOccupation) {
		Occupation = tOccupation;
	}

	public String getOccupation() {
		return String.valueOf(Occupation);
	}

	// end add
	public void setGrpContNo(String tGrpContNo) {
		GrpContNo = tGrpContNo;
	}

	public void setPrem(double tPrem) {
		Prem = tPrem;
	}

	public String getPrem() {
		return String.valueOf(Prem);
	}

	public void setGet(double tGet) {
		Get = tGet;
	}

	public String getGet() {
		return String.valueOf(Get);
	}

	public void setAmnt(double tAmnt) {
		Amnt = tAmnt;
	}

	public String getAmnt() {
		return String.valueOf(Amnt);
	}

	public void setMult(double tMult) {
		Mult = tMult;
	}

	public String getMult() {
		return String.valueOf(Mult);
	}

	public void setFloatRate(double tFloatRate) {
		FloatRate = tFloatRate;
	}

	public String getFloatRate() {
		return String.valueOf(FloatRate);
	}

	public void setAddRate(double tAddRate) {
		AddRate = tAddRate;
	}

	public String getContNo() {
		return ContNo;
	}

	public String getGrpContNo() {
		return GrpContNo;
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

	public void setYears(int tYears) {
		Years = tYears;
	}

	public String getYears() {
		return String.valueOf(Years);
	}

	public void setInsuYear(int tInsuYear) {
		InsuYear = tInsuYear;
	}

	public String getInsuYear() {
		return String.valueOf(InsuYear);
	}

	public void setAppAge(int tAppAge) {
		AppAge = tAppAge;
	}

	public String getAppAge() {
		return String.valueOf(AppAge);
	}
	
	public void setAppntAppAge(int tAppntAppAge) {
		AppntAppAge = tAppntAppAge;
	}

	public String getAppntAppAge() {
		return String.valueOf(AppntAppAge);
	}

	public void setCount(int tCount) {
		Count = tCount;
	}

	public String getCount() {
		return String.valueOf(Count);
	}

	public void setRnewFlag(int tRnewFlag) {
		RnewFlag = tRnewFlag;
	}

	public String getRnewFlag() {
		return String.valueOf(RnewFlag);
	}

	public void setSex(String tSex) {
		Sex = tSex;
	}

	public String getSex() {
		return Sex;
	}

	public void setInsuYearFlag(String tInsuYearFlag) {
		InsuYearFlag = tInsuYearFlag;
	}

	public String getInsuYearFlag() {
		return InsuYearFlag;
	}

	public void setPayEndYearFlag(String tPayEndYearFlag) {
		PayEndYearFlag = tPayEndYearFlag;
	}

	public String getPayEndYearFlag() {
		return PayEndYearFlag;
	}

	public void setGetYearFlag(String tGetYearFlag) {
		GetYearFlag = tGetYearFlag;
	}

	public String getGetYearFlag() {
		return GetYearFlag;
	}

	public void setGetDutyKind(String tGetDutyKind) {
		GetDutyKind = tGetDutyKind;
	}

	public String getGetDutyKind() {
		return GetDutyKind;
	}

	public void setStartDateCalRef(String tStartDateCalRef) {
		StartDateCalRef = tStartDateCalRef;
	}

	public String getStartDateCalRef() {
		return StartDateCalRef;
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

	public void setRiskCode(String tRiskCode) {
		RiskCode = tRiskCode;
	}

	public String getRiskCode() {
		return RiskCode;
	}

	public void setStandbyFlag1(String tStandbyFlag1) {
		StandbyFlag1 = tStandbyFlag1;
	}

	public String getStandbyFlag1() {
		return StandbyFlag1;
	}

	public void setStandbyFlag2(String tStandbyFlag2) {
		StandbyFlag2 = tStandbyFlag2;
	}

	public String getStandbyFlag2() {
		return StandbyFlag2;
	}

	public void setStandbyFlag3(String tStandbyFlag3) {
		StandbyFlag3 = tStandbyFlag3;
	}

	public String getStandbyFlag3() {
		return StandbyFlag3;
	}

	public void setGrpPolNo(String tGrpPolNo) {
		GrpPolNo = tGrpPolNo;
	}

	public String getGrpPolNo() {
		return GrpPolNo;
	}

	public void setEdorNo(String tEdorNo) {
		EdorNo = tEdorNo;
	}

	public String getEdorNo() {
		return EdorNo;
	}

	public void setCalType(String tCalType) {
		CalType = tCalType;
	}

	public String getCalType() {
		return CalType;
	}

	public void setGetLimit(double tGetLimit) {
		GetLimit = tGetLimit;
	}

	public String getGetLimit() {
		return String.valueOf(GetLimit);
	}

	public void setGetRate(double tGetRate) {
		GetRate = tGetRate;
	}

	public String getGetRate() {
		return String.valueOf(GetRate);
	}

	public void setSSFlag(String tSSFlag) {
		SSFlag = tSSFlag;
	}

	public String getSSFlag() {
		return SSFlag;
	}

	public void setPeakLine(double tPeakLine) {
		PeakLine = tPeakLine;
	}

	public String getPeakLine() {
		return String.valueOf(PeakLine);
	}

	public void setCValiDate(String tCValiDate) {
		CValiDate = tCValiDate;
	}

	public String getCValiDate() {
		return CValiDate;
	}

	public void setSuppRiskScore(double tSuppRiskScore) {
		SuppRiskScore = tSuppRiskScore;
	}

	public String getSuppRiskScore() {
		return String.valueOf(SuppRiskScore);
	}

	public String getDutyCode() {
		return DutyCode;
	}

	public void setDutyCode(String tDutyCode) {
		DutyCode = tDutyCode;
	}

	public void setFirstScore(double tFirstScore) {
		FirstScore = tFirstScore;
	}

	public String getFirstScore() {

		return String.valueOf(FirstScore);
	}

	public void setSecondScore(double tSecondScore) {
		SecondScore = tSecondScore;
	}

	public String getSecondScore() {
		return String.valueOf(SecondScore);
	}

	public void setVPU(String tVPU) {
		VPU = tVPU;
	}

	public void setSecondInsuredNo(String tSecondInsuredNo) {
		SecondInsuredNo = tSecondInsuredNo;
	}

	public String getSecondInsuredNo() {
		return (SecondInsuredNo);
	}

	public void setInsuredNo(String tInsuredNo) {
		InsuredNo = tInsuredNo;
	}

	public String getInsuredNo() {
		return (InsuredNo);
	}

	public String getVPU() {
		return (VPU);
	}

	public void setMainPolNo(String tMainPolNo) {
		MainPolNo = tMainPolNo;
	}

	public String getMainPolNo() {
		return (MainPolNo);
	}

	public void setAppAg2(String tAppAg2) {
		AppAg2 = tAppAg2;
	}

	public String getAppAg2() {
		return (AppAg2);
	}

	public void setHusbandScore(double tHusbandScore) {
		HusbandScore = tHusbandScore;
	}

	public String getHusbandScore() {

		return String.valueOf(HusbandScore);
	}

	public void setWifeScore(double tWifeScore) {
		WifeScore = tWifeScore;
	}

	public String getWifeScore() {

		return String.valueOf(WifeScore);
	}

	public String getContPlanCode() {
		return ContPlanCode;
	}

	public void setContPlanCode(String contPlanCode) {
		ContPlanCode = contPlanCode;
	}

	public String getGetStartType() {
		return GetStartType;
	}

	public void setGetStartType(String getStartType) {
		GetStartType = getStartType;
	}

	public String getPolTypeFlag() {
		return PolTypeFlag;
	}

	public void setPolTypeFlag(String polTypeFlag) {
		PolTypeFlag = polTypeFlag;
	}
    public void setBonusYear(int tBonusYear)
    {
        BonusYear = tBonusYear;
    }
    public int getBonusYear()
    {
        return BonusYear;
    }

	public String getCurrency() 
	{
		return Currency;
	}
	public void setCurrency(String tCurrency) {
		Currency = tCurrency;
	}

	public int getAppntAge() {
		return AppntAge;
	}

	public void setAppntAge(int appntAge) {
		AppntAge = appntAge;
	}
}
