

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIPolRecordBakeDB;

/*
 * <p>ClassName: RIPolRecordBakeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIPolRecordBakeSchema implements Schema, Cloneable
{
	// @Field
	/** 批次号 */
	private String BatchNo;
	/** 事件编号 */
	private String EventNo;
	/** 事件类型 */
	private String EventType;
	/** 记录类型 */
	private String RecordType;
	/** 工作流节点状态 */
	private String NodeState;
	/** 数据明细标记 */
	private String DataFlag;
	/** 事件数据联合主键 */
	private String UnionKey;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 总单投保单号码 */
	private String ProposalGrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体投保单险种号码 */
	private String GrpProposalNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 投保单号码 */
	private String ProposalNo;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 计划别 */
	private String PlanType;
	/** 长短险标志 */
	private String RiskPeriod;
	/** 险种类型 */
	private String RiskType;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 保险年期 */
	private int Years;
	/** 保单年度 */
	private int InsuredYear;
	/** 销售渠道 */
	private String SaleChnl;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 保险责任终止日期 */
	private Date EndDate;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人生日 */
	private Date InsuredBirthday;
	/** 被保人年龄 */
	private int InsuredAge;
	/** 当前年龄 */
	private int CurrentAge;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 被保人职业类别 */
	private String OccupationType;
	/** 次健1 */
	private String HealthTime1;
	/** 次健2 */
	private String HealthTime2;
	/** 职业代码 */
	private String OccupationCode;
	/** 弱体等级 */
	private String SuppRiskScore;
	/** 是否吸烟 */
	private String SmokeFlag;
	/** 加费评点 */
	private double AdditionalRate;
	/** 加费方式 */
	private String ExtType;
	/** 加费金额 */
	private double ExtPrem;
	/** 月初账户价值 */
	private double BegAccountValue;
	/** 月末投资单位数 */
	private int EndUnit;
	/** 月末投资单位价值 */
	private double EndPrice;
	/** 月末账户价值 */
	private double EndAccountValue;
	/** 费用类型 */
	private String FeeType;
	/** 费用金额 */
	private double FeeMoney;
	/** 是否非现金领取 */
	private String NonCashFlag;
	/** 标准保费 */
	private double StandPrem;
	/** 实际保费 */
	private double Prem;
	/** 基本保额 */
	private double Amnt;
	/** 份数 */
	private double Mult;
	/** 准备金 */
	private double Reserve;
	/** 年缴化保费 */
	private double APE;
	/** 现金价值 */
	private double CSV;
	/** 自留额 */
	private double Retention;
	/** 核心风险保额 */
	private double Suminsured;
	/** 核心面额 */
	private double Faceamount;
	/** 币别 */
	private String Currency;
	/** 风险保额 */
	private double RiskAmnt;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费年期 */
	private int PayYears;
	/** 终交年龄年期标志 */
	private String PayEndYearFlag;
	/** 终交年龄年期 */
	private int PayEndYear;
	/** 领取年龄年期标志 */
	private String GetYearFlag;
	/** 领取年龄年期 */
	private int GetYear;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 保险年龄年期 */
	private int InsuYear;
	/** 意外年龄年期标志 */
	private String AcciYearFlag;
	/** 意外年龄年期 */
	private int AcciYear;
	/** 意外责任终止日期 */
	private Date AcciEndDate;
	/** 起领日期 */
	private Date GetStartDate;
	/** 起领日期计算类型 */
	private String GetStartType;
	/** 封顶线 */
	private double PeakLine;
	/** 起付限 */
	private double GetLimit;
	/** 领取间隔 */
	private int GetIntv;
	/** 交费收据号码 */
	private String PayNo;
	/** 交费期数 */
	private int PayCount;
	/** 交费金额 */
	private double PayMoney;
	/** 原交至日期 */
	private Date LastPayToDate;
	/** 现交至日期 */
	private Date CurPayToDate;
	/** 批单号 */
	private String EdorNo;
	/** 批改业务类型 */
	private String FeeOperationType;
	/** 批改财务类型 */
	private String FeeFinaType;
	/** 转换汇率 */
	private double ChangeRate;
	/** 累计币种 */
	private String AccCurrency;
	/** 累计风险保额 */
	private double AccAmnt;
	/** 变更前标准保费 */
	private double PreStandPrem;
	/** 变更前实际保费 */
	private double PrePrem;
	/** 变更前风险保额 */
	private double PreRiskAmnt;
	/** 变更前基本保额 */
	private double PreAmnt;
	/** 赔案号 */
	private String ClmNo;
	/** 赔案业务类型 */
	private String ClmFeeOperationType;
	/** 赔案财务类型 */
	private String ClmFeeFinaType;
	/** 标准赔款 */
	private double StandGetMoney;
	/** 赔付比例 */
	private double GetRate;
	/** 实际赔款 */
	private double ClmGetMoney;
	/** 出险日期 */
	private Date AccDate;
	/** 累计风险编号 */
	private String AccumulateDefNO;
	/** 再保合同号 */
	private String RIContNo;
	/** 再保方案编号 */
	private String RIPreceptNo;
	/** 临分标记 */
	private String ReinsreFlag;
	/** 业务日期 */
	private Date GetDate;
	/** 备用字符串属性1 */
	private String StandbyString1;
	/** 备用字符串属性2 */
	private String StandbyString2;
	/** 备用字符串属性3 */
	private String StandbyString3;
	/** 备用数字属性1 */
	private double StandbyDouble1;
	/** 备用数字属性2 */
	private double StandbyDouble2;
	/** 备用数字属性3 */
	private double StandbyDouble3;
	/** 备用日期1 */
	private Date StandbyDate1;
	/** 备用日期2 */
	private Date StandbyDate2;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 变更前风险保额转换 */
	private double PreChRiskAmnt;
	/** 风险保额转换 */
	private double ChRiskAmnt;
	/** 变更前汇率 */
	private double PreChangeRate;
	/** 主险保单号 */
	private String MainPolNo;
	/** 再保方案类型 */
	private String RIPreceptType;
	/** 业财标记 */
	private String BFFlag;
	/** 变更保费 */
	private double EdorPrem;
	/** 变更保费转换 */
	private double ChEdorPrem;
	/** 保费转换 */
	private double ChPrem;
	/** 周年红利 */
	private double Bonus1;
	/** 特别红利 */
	private double Bonus2;
	/** 满期红利 */
	private double Bonus3;
	/** 加费评点1 */
	private double ExtRate1;
	/** 加费评点2 */
	private double ExtRate2;
	/** 加费评点3 */
	private double ExtRate3;
	/** 加费金额1 */
	private double ExtPrem1;
	/** 加费金额2 */
	private double ExtPrem2;
	/** 加费金额3 */
	private double ExtPrem3;
	/** 备用字符串属性4 */
	private String StandbyString4;
	/** 备用字符串属性5 */
	private String StandbyString5;
	/** 备用字符串属性6 */
	private String StandbyString6;
	/** 备用字符串属性7 */
	private String StandbyString7;
	/** 备用字符串属性8 */
	private String StandbyString8;
	/** 备用字符串属性9 */
	private String StandbyString9;
	/** 备用数字属性4 */
	private double StandbyDouble4;
	/** 备用数字属性5 */
	private double StandbyDouble5;
	/** 备用数字属性6 */
	private double StandbyDouble6;
	/** 备用数字属性7 */
	private double StandbyDouble7;
	/** 备用数字属性8 */
	private double StandbyDouble8;
	/** 备用数字属性9 */
	private double StandbyDouble9;
	/** 备用日期3 */
	private Date StandbyDate3;
	/** 备用日期4 */
	private Date StandbyDate4;
	/** 备用日期5 */
	private Date StandbyDate5;

	public static final int FIELDNUM = 148;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIPolRecordBakeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "EventNo";

		PK = pk;
	}

	/**
	* Schema克隆
	* @return Object
	* @throws CloneNotSupportedException
	*/
	public Object clone()
		throws CloneNotSupportedException
	{
		RIPolRecordBakeSchema cloned = (RIPolRecordBakeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getEventNo()
	{
		return EventNo;
	}
	public void setEventNo(String aEventNo)
	{
		EventNo = aEventNo;
	}
	/**
	* 01-新单数据 <p>
	* 02-续期数据<p>
	* 03-保全数据 <p>
	* 04-理赔数据
	*/
	public String getEventType()
	{
		return EventType;
	}
	public void setEventType(String aEventType)
	{
		EventType = aEventType;
	}
	/**
	* 01--再保<p>
	* 02--共保
	*/
	public String getRecordType()
	{
		return RecordType;
	}
	public void setRecordType(String aRecordType)
	{
		RecordType = aRecordType;
	}
	/**
	* 01－业务数据提取<p>
	* 02－数据校验<p>
	* 03－风险计算<p>
	* 04－再保方分配<p>
	* 05－数据修改<p>
	* 08－分保数据生成<p>
	* 11－计算项计算<p>
	* 99－处理完成
	*/
	public String getNodeState()
	{
		return NodeState;
	}
	public void setNodeState(String aNodeState)
	{
		NodeState = aNodeState;
	}
	/**
	* 01-个人险种  02-个人险种责任
	*/
	public String getDataFlag()
	{
		return DataFlag;
	}
	public void setDataFlag(String aDataFlag)
	{
		DataFlag = aDataFlag;
	}
	public String getUnionKey()
	{
		return UnionKey;
	}
	public void setUnionKey(String aUnionKey)
	{
		UnionKey = aUnionKey;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		ProposalGrpContNo = aProposalGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		GrpProposalNo = aGrpProposalNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	/**
	* 计划别
	*/
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
	}
	/**
	* 长期险短期险标志<p>
	* 1-“短期险”<p>
	* 2-“长期险”
	*/
	public String getRiskPeriod()
	{
		return RiskPeriod;
	}
	public void setRiskPeriod(String aRiskPeriod)
	{
		RiskPeriod = aRiskPeriod;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 填写责任编码，如果计算为险种级别，此字段填写000000
	*/
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	/**
	* 保险责任区间
	*/
	public int getYears()
	{
		return Years;
	}
	public void setYears(int aYears)
	{
		Years = aYears;
	}
	public void setYears(String aYears)
	{
		if (aYears != null && !aYears.equals(""))
		{
			Integer tInteger = new Integer(aYears);
			int i = tInteger.intValue();
			Years = i;
		}
	}

	public int getInsuredYear()
	{
		return InsuredYear;
	}
	public void setInsuredYear(int aInsuredYear)
	{
		InsuredYear = aInsuredYear;
	}
	public void setInsuredYear(String aInsuredYear)
	{
		if (aInsuredYear != null && !aInsuredYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuredYear);
			int i = tInteger.intValue();
			InsuredYear = i;
		}
	}

	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	/**
	* 为责任表中最晚的终止日期
	*/
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	public String getInsuredSex()
	{
		return InsuredSex;
	}
	public void setInsuredSex(String aInsuredSex)
	{
		InsuredSex = aInsuredSex;
	}
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
	}

	public int getInsuredAge()
	{
		return InsuredAge;
	}
	public void setInsuredAge(int aInsuredAge)
	{
		InsuredAge = aInsuredAge;
	}
	public void setInsuredAge(String aInsuredAge)
	{
		if (aInsuredAge != null && !aInsuredAge.equals(""))
		{
			Integer tInteger = new Integer(aInsuredAge);
			int i = tInteger.intValue();
			InsuredAge = i;
		}
	}

	public int getCurrentAge()
	{
		return CurrentAge;
	}
	public void setCurrentAge(int aCurrentAge)
	{
		CurrentAge = aCurrentAge;
	}
	public void setCurrentAge(String aCurrentAge)
	{
		if (aCurrentAge != null && !aCurrentAge.equals(""))
		{
			Integer tInteger = new Integer(aCurrentAge);
			int i = tInteger.intValue();
			CurrentAge = i;
		}
	}

	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
	}
	/**
	* 定义为再保编号
	*/
	public String getHealthTime1()
	{
		return HealthTime1;
	}
	public void setHealthTime1(String aHealthTime1)
	{
		HealthTime1 = aHealthTime1;
	}
	public String getHealthTime2()
	{
		return HealthTime2;
	}
	public void setHealthTime2(String aHealthTime2)
	{
		HealthTime2 = aHealthTime2;
	}
	public String getOccupationCode()
	{
		return OccupationCode;
	}
	public void setOccupationCode(String aOccupationCode)
	{
		OccupationCode = aOccupationCode;
	}
	/**
	* 健康加费
	*/
	public String getSuppRiskScore()
	{
		return SuppRiskScore;
	}
	public void setSuppRiskScore(String aSuppRiskScore)
	{
		SuppRiskScore = aSuppRiskScore;
	}
	public String getSmokeFlag()
	{
		return SmokeFlag;
	}
	public void setSmokeFlag(String aSmokeFlag)
	{
		SmokeFlag = aSmokeFlag;
	}
	public double getAdditionalRate()
	{
		return AdditionalRate;
	}
	public void setAdditionalRate(double aAdditionalRate)
	{
		AdditionalRate = aAdditionalRate;
	}
	public void setAdditionalRate(String aAdditionalRate)
	{
		if (aAdditionalRate != null && !aAdditionalRate.equals(""))
		{
			Double tDouble = new Double(aAdditionalRate);
			double d = tDouble.doubleValue();
			AdditionalRate = d;
		}
	}

	public String getExtType()
	{
		return ExtType;
	}
	public void setExtType(String aExtType)
	{
		ExtType = aExtType;
	}
	public double getExtPrem()
	{
		return ExtPrem;
	}
	public void setExtPrem(double aExtPrem)
	{
		ExtPrem = aExtPrem;
	}
	public void setExtPrem(String aExtPrem)
	{
		if (aExtPrem != null && !aExtPrem.equals(""))
		{
			Double tDouble = new Double(aExtPrem);
			double d = tDouble.doubleValue();
			ExtPrem = d;
		}
	}

	public double getBegAccountValue()
	{
		return BegAccountValue;
	}
	public void setBegAccountValue(double aBegAccountValue)
	{
		BegAccountValue = aBegAccountValue;
	}
	public void setBegAccountValue(String aBegAccountValue)
	{
		if (aBegAccountValue != null && !aBegAccountValue.equals(""))
		{
			Double tDouble = new Double(aBegAccountValue);
			double d = tDouble.doubleValue();
			BegAccountValue = d;
		}
	}

	public int getEndUnit()
	{
		return EndUnit;
	}
	public void setEndUnit(int aEndUnit)
	{
		EndUnit = aEndUnit;
	}
	public void setEndUnit(String aEndUnit)
	{
		if (aEndUnit != null && !aEndUnit.equals(""))
		{
			Integer tInteger = new Integer(aEndUnit);
			int i = tInteger.intValue();
			EndUnit = i;
		}
	}

	public double getEndPrice()
	{
		return EndPrice;
	}
	public void setEndPrice(double aEndPrice)
	{
		EndPrice = aEndPrice;
	}
	public void setEndPrice(String aEndPrice)
	{
		if (aEndPrice != null && !aEndPrice.equals(""))
		{
			Double tDouble = new Double(aEndPrice);
			double d = tDouble.doubleValue();
			EndPrice = d;
		}
	}

	public double getEndAccountValue()
	{
		return EndAccountValue;
	}
	public void setEndAccountValue(double aEndAccountValue)
	{
		EndAccountValue = aEndAccountValue;
	}
	public void setEndAccountValue(String aEndAccountValue)
	{
		if (aEndAccountValue != null && !aEndAccountValue.equals(""))
		{
			Double tDouble = new Double(aEndAccountValue);
			double d = tDouble.doubleValue();
			EndAccountValue = d;
		}
	}

	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		FeeType = aFeeType;
	}
	public double getFeeMoney()
	{
		return FeeMoney;
	}
	public void setFeeMoney(double aFeeMoney)
	{
		FeeMoney = aFeeMoney;
	}
	public void setFeeMoney(String aFeeMoney)
	{
		if (aFeeMoney != null && !aFeeMoney.equals(""))
		{
			Double tDouble = new Double(aFeeMoney);
			double d = tDouble.doubleValue();
			FeeMoney = d;
		}
	}

	public String getNonCashFlag()
	{
		return NonCashFlag;
	}
	public void setNonCashFlag(String aNonCashFlag)
	{
		NonCashFlag = aNonCashFlag;
	}
	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	/**
	* 被保人险种中份数的冗余。??
	*/
	public double getMult()
	{
		return Mult;
	}
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}

	/**
	* 计划别
	*/
	public double getReserve()
	{
		return Reserve;
	}
	public void setReserve(double aReserve)
	{
		Reserve = aReserve;
	}
	public void setReserve(String aReserve)
	{
		if (aReserve != null && !aReserve.equals(""))
		{
			Double tDouble = new Double(aReserve);
			double d = tDouble.doubleValue();
			Reserve = d;
		}
	}

	/**
	* Life,BMLA 加费评点
	*/
	public double getAPE()
	{
		return APE;
	}
	public void setAPE(double aAPE)
	{
		APE = aAPE;
	}
	public void setAPE(String aAPE)
	{
		if (aAPE != null && !aAPE.equals(""))
		{
			Double tDouble = new Double(aAPE);
			double d = tDouble.doubleValue();
			APE = d;
		}
	}

	/**
	* 理赔赔付比例
	*/
	public double getCSV()
	{
		return CSV;
	}
	public void setCSV(double aCSV)
	{
		CSV = aCSV;
	}
	public void setCSV(String aCSV)
	{
		if (aCSV != null && !aCSV.equals(""))
		{
			Double tDouble = new Double(aCSV);
			double d = tDouble.doubleValue();
			CSV = d;
		}
	}

	public double getRetention()
	{
		return Retention;
	}
	public void setRetention(double aRetention)
	{
		Retention = aRetention;
	}
	public void setRetention(String aRetention)
	{
		if (aRetention != null && !aRetention.equals(""))
		{
			Double tDouble = new Double(aRetention);
			double d = tDouble.doubleValue();
			Retention = d;
		}
	}

	public double getSuminsured()
	{
		return Suminsured;
	}
	public void setSuminsured(double aSuminsured)
	{
		Suminsured = aSuminsured;
	}
	public void setSuminsured(String aSuminsured)
	{
		if (aSuminsured != null && !aSuminsured.equals(""))
		{
			Double tDouble = new Double(aSuminsured);
			double d = tDouble.doubleValue();
			Suminsured = d;
		}
	}

	public double getFaceamount()
	{
		return Faceamount;
	}
	public void setFaceamount(double aFaceamount)
	{
		Faceamount = aFaceamount;
	}
	public void setFaceamount(String aFaceamount)
	{
		if (aFaceamount != null && !aFaceamount.equals(""))
		{
			Double tDouble = new Double(aFaceamount);
			double d = tDouble.doubleValue();
			Faceamount = d;
		}
	}

	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public double getRiskAmnt()
	{
		return RiskAmnt;
	}
	public void setRiskAmnt(double aRiskAmnt)
	{
		RiskAmnt = aRiskAmnt;
	}
	public void setRiskAmnt(String aRiskAmnt)
	{
		if (aRiskAmnt != null && !aRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aRiskAmnt);
			double d = tDouble.doubleValue();
			RiskAmnt = d;
		}
	}

	/**
	* 交费间隔<p>
	* -1 -- 不定期交,<p>
	* 0  -- 趸交,<p>
	* 1  -- 月交<p>
	* 3  -- 季交<p>
	* 6  -- 半年交<p>
	* 12 -- 年交
	*/
	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* 对于终交年期标志为“年”：  表示需要交费的年数。<p>
	* 对于终交年期标志为“月”：  表示需要交费的月数<p>
	* 对于终交年期标志为“日”：  表示需要交费的天数<p>
	* 对于终交年期标志为“年龄”：该字段存放将根据年龄折算成的需要交费的年数。
	*/
	public int getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(int aPayYears)
	{
		PayYears = aPayYears;
	}
	public void setPayYears(String aPayYears)
	{
		if (aPayYears != null && !aPayYears.equals(""))
		{
			Integer tInteger = new Integer(aPayYears);
			int i = tInteger.intValue();
			PayYears = i;
		}
	}

	/**
	* A－年龄，M－月，D－日，Y－年
	*/
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
	}
	public int getPayEndYear()
	{
		return PayEndYear;
	}
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/**
	* A－年龄，M－月，D－日，Y－年
	*/
	public String getGetYearFlag()
	{
		return GetYearFlag;
	}
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
	}
	public int getGetYear()
	{
		return GetYear;
	}
	public void setGetYear(int aGetYear)
	{
		GetYear = aGetYear;
	}
	public void setGetYear(String aGetYear)
	{
		if (aGetYear != null && !aGetYear.equals(""))
		{
			Integer tInteger = new Integer(aGetYear);
			int i = tInteger.intValue();
			GetYear = i;
		}
	}

	/**
	* Y －－ 年 <p>
	* M －－ 月<p>
	* D －－ 日<p>
	* A －－ 岁
	*/
	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	public int getInsuYear()
	{
		return InsuYear;
	}
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
	}

	/**
	* 备用
	*/
	public String getAcciYearFlag()
	{
		return AcciYearFlag;
	}
	public void setAcciYearFlag(String aAcciYearFlag)
	{
		AcciYearFlag = aAcciYearFlag;
	}
	public int getAcciYear()
	{
		return AcciYear;
	}
	public void setAcciYear(int aAcciYear)
	{
		AcciYear = aAcciYear;
	}
	public void setAcciYear(String aAcciYear)
	{
		if (aAcciYear != null && !aAcciYear.equals(""))
		{
			Integer tInteger = new Integer(aAcciYear);
			int i = tInteger.intValue();
			AcciYear = i;
		}
	}

	public String getAcciEndDate()
	{
		if( AcciEndDate != null )
			return fDate.getString(AcciEndDate);
		else
			return null;
	}
	public void setAcciEndDate(Date aAcciEndDate)
	{
		AcciEndDate = aAcciEndDate;
	}
	public void setAcciEndDate(String aAcciEndDate)
	{
		if (aAcciEndDate != null && !aAcciEndDate.equals("") )
		{
			AcciEndDate = fDate.getDate( aAcciEndDate );
		}
		else
			AcciEndDate = null;
	}

	public String getGetStartDate()
	{
		if( GetStartDate != null )
			return fDate.getString(GetStartDate);
		else
			return null;
	}
	public void setGetStartDate(Date aGetStartDate)
	{
		GetStartDate = aGetStartDate;
	}
	public void setGetStartDate(String aGetStartDate)
	{
		if (aGetStartDate != null && !aGetStartDate.equals("") )
		{
			GetStartDate = fDate.getDate( aGetStartDate );
		}
		else
			GetStartDate = null;
	}

	/**
	* 生日对应日或者保单生效对应日<p>
	* 0 --生日对应<p>
	* 1 --起保对应
	*/
	public String getGetStartType()
	{
		return GetStartType;
	}
	public void setGetStartType(String aGetStartType)
	{
		GetStartType = aGetStartType;
	}
	public double getPeakLine()
	{
		return PeakLine;
	}
	public void setPeakLine(double aPeakLine)
	{
		PeakLine = aPeakLine;
	}
	public void setPeakLine(String aPeakLine)
	{
		if (aPeakLine != null && !aPeakLine.equals(""))
		{
			Double tDouble = new Double(aPeakLine);
			double d = tDouble.doubleValue();
			PeakLine = d;
		}
	}

	public double getGetLimit()
	{
		return GetLimit;
	}
	public void setGetLimit(double aGetLimit)
	{
		GetLimit = aGetLimit;
	}
	public void setGetLimit(String aGetLimit)
	{
		if (aGetLimit != null && !aGetLimit.equals(""))
		{
			Double tDouble = new Double(aGetLimit);
			double d = tDouble.doubleValue();
			GetLimit = d;
		}
	}

	/**
	* 0 -- 趸领<p>
	* 1 －－ 月领<p>
	* 3 －－ 季领<p>
	* 12－－ 年龄<p>
	* 36－－ 每3年领
	*/
	public int getGetIntv()
	{
		return GetIntv;
	}
	public void setGetIntv(int aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	public void setGetIntv(String aGetIntv)
	{
		if (aGetIntv != null && !aGetIntv.equals(""))
		{
			Integer tInteger = new Integer(aGetIntv);
			int i = tInteger.intValue();
			GetIntv = i;
		}
	}

	public String getPayNo()
	{
		return PayNo;
	}
	public void setPayNo(String aPayNo)
	{
		PayNo = aPayNo;
	}
	public int getPayCount()
	{
		return PayCount;
	}
	public void setPayCount(int aPayCount)
	{
		PayCount = aPayCount;
	}
	public void setPayCount(String aPayCount)
	{
		if (aPayCount != null && !aPayCount.equals(""))
		{
			Integer tInteger = new Integer(aPayCount);
			int i = tInteger.intValue();
			PayCount = i;
		}
	}

	public double getPayMoney()
	{
		return PayMoney;
	}
	public void setPayMoney(double aPayMoney)
	{
		PayMoney = aPayMoney;
	}
	public void setPayMoney(String aPayMoney)
	{
		if (aPayMoney != null && !aPayMoney.equals(""))
		{
			Double tDouble = new Double(aPayMoney);
			double d = tDouble.doubleValue();
			PayMoney = d;
		}
	}

	public String getLastPayToDate()
	{
		if( LastPayToDate != null )
			return fDate.getString(LastPayToDate);
		else
			return null;
	}
	public void setLastPayToDate(Date aLastPayToDate)
	{
		LastPayToDate = aLastPayToDate;
	}
	public void setLastPayToDate(String aLastPayToDate)
	{
		if (aLastPayToDate != null && !aLastPayToDate.equals("") )
		{
			LastPayToDate = fDate.getDate( aLastPayToDate );
		}
		else
			LastPayToDate = null;
	}

	public String getCurPayToDate()
	{
		if( CurPayToDate != null )
			return fDate.getString(CurPayToDate);
		else
			return null;
	}
	public void setCurPayToDate(Date aCurPayToDate)
	{
		CurPayToDate = aCurPayToDate;
	}
	public void setCurPayToDate(String aCurPayToDate)
	{
		if (aCurPayToDate != null && !aCurPayToDate.equals("") )
		{
			CurPayToDate = fDate.getDate( aCurPayToDate );
		}
		else
			CurPayToDate = null;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getFeeOperationType()
	{
		return FeeOperationType;
	}
	public void setFeeOperationType(String aFeeOperationType)
	{
		FeeOperationType = aFeeOperationType;
	}
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		FeeFinaType = aFeeFinaType;
	}
	public double getChangeRate()
	{
		return ChangeRate;
	}
	public void setChangeRate(double aChangeRate)
	{
		ChangeRate = aChangeRate;
	}
	public void setChangeRate(String aChangeRate)
	{
		if (aChangeRate != null && !aChangeRate.equals(""))
		{
			Double tDouble = new Double(aChangeRate);
			double d = tDouble.doubleValue();
			ChangeRate = d;
		}
	}

	public String getAccCurrency()
	{
		return AccCurrency;
	}
	public void setAccCurrency(String aAccCurrency)
	{
		AccCurrency = aAccCurrency;
	}
	public double getAccAmnt()
	{
		return AccAmnt;
	}
	public void setAccAmnt(double aAccAmnt)
	{
		AccAmnt = aAccAmnt;
	}
	public void setAccAmnt(String aAccAmnt)
	{
		if (aAccAmnt != null && !aAccAmnt.equals(""))
		{
			Double tDouble = new Double(aAccAmnt);
			double d = tDouble.doubleValue();
			AccAmnt = d;
		}
	}

	public double getPreStandPrem()
	{
		return PreStandPrem;
	}
	public void setPreStandPrem(double aPreStandPrem)
	{
		PreStandPrem = aPreStandPrem;
	}
	public void setPreStandPrem(String aPreStandPrem)
	{
		if (aPreStandPrem != null && !aPreStandPrem.equals(""))
		{
			Double tDouble = new Double(aPreStandPrem);
			double d = tDouble.doubleValue();
			PreStandPrem = d;
		}
	}

	public double getPrePrem()
	{
		return PrePrem;
	}
	public void setPrePrem(double aPrePrem)
	{
		PrePrem = aPrePrem;
	}
	public void setPrePrem(String aPrePrem)
	{
		if (aPrePrem != null && !aPrePrem.equals(""))
		{
			Double tDouble = new Double(aPrePrem);
			double d = tDouble.doubleValue();
			PrePrem = d;
		}
	}

	public double getPreRiskAmnt()
	{
		return PreRiskAmnt;
	}
	public void setPreRiskAmnt(double aPreRiskAmnt)
	{
		PreRiskAmnt = aPreRiskAmnt;
	}
	public void setPreRiskAmnt(String aPreRiskAmnt)
	{
		if (aPreRiskAmnt != null && !aPreRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aPreRiskAmnt);
			double d = tDouble.doubleValue();
			PreRiskAmnt = d;
		}
	}

	public double getPreAmnt()
	{
		return PreAmnt;
	}
	public void setPreAmnt(double aPreAmnt)
	{
		PreAmnt = aPreAmnt;
	}
	public void setPreAmnt(String aPreAmnt)
	{
		if (aPreAmnt != null && !aPreAmnt.equals(""))
		{
			Double tDouble = new Double(aPreAmnt);
			double d = tDouble.doubleValue();
			PreAmnt = d;
		}
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getClmFeeOperationType()
	{
		return ClmFeeOperationType;
	}
	public void setClmFeeOperationType(String aClmFeeOperationType)
	{
		ClmFeeOperationType = aClmFeeOperationType;
	}
	public String getClmFeeFinaType()
	{
		return ClmFeeFinaType;
	}
	public void setClmFeeFinaType(String aClmFeeFinaType)
	{
		ClmFeeFinaType = aClmFeeFinaType;
	}
	public double getStandGetMoney()
	{
		return StandGetMoney;
	}
	public void setStandGetMoney(double aStandGetMoney)
	{
		StandGetMoney = aStandGetMoney;
	}
	public void setStandGetMoney(String aStandGetMoney)
	{
		if (aStandGetMoney != null && !aStandGetMoney.equals(""))
		{
			Double tDouble = new Double(aStandGetMoney);
			double d = tDouble.doubleValue();
			StandGetMoney = d;
		}
	}

	public double getGetRate()
	{
		return GetRate;
	}
	public void setGetRate(double aGetRate)
	{
		GetRate = aGetRate;
	}
	public void setGetRate(String aGetRate)
	{
		if (aGetRate != null && !aGetRate.equals(""))
		{
			Double tDouble = new Double(aGetRate);
			double d = tDouble.doubleValue();
			GetRate = d;
		}
	}

	public double getClmGetMoney()
	{
		return ClmGetMoney;
	}
	public void setClmGetMoney(double aClmGetMoney)
	{
		ClmGetMoney = aClmGetMoney;
	}
	public void setClmGetMoney(String aClmGetMoney)
	{
		if (aClmGetMoney != null && !aClmGetMoney.equals(""))
		{
			Double tDouble = new Double(aClmGetMoney);
			double d = tDouble.doubleValue();
			ClmGetMoney = d;
		}
	}

	public String getAccDate()
	{
		if( AccDate != null )
			return fDate.getString(AccDate);
		else
			return null;
	}
	public void setAccDate(Date aAccDate)
	{
		AccDate = aAccDate;
	}
	public void setAccDate(String aAccDate)
	{
		if (aAccDate != null && !aAccDate.equals("") )
		{
			AccDate = fDate.getDate( aAccDate );
		}
		else
			AccDate = null;
	}

	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	public String getReinsreFlag()
	{
		return ReinsreFlag;
	}
	public void setReinsreFlag(String aReinsreFlag)
	{
		ReinsreFlag = aReinsreFlag;
	}
	/**
	* 业务日期
	*/
	public String getGetDate()
	{
		if( GetDate != null )
			return fDate.getString(GetDate);
		else
			return null;
	}
	public void setGetDate(Date aGetDate)
	{
		GetDate = aGetDate;
	}
	public void setGetDate(String aGetDate)
	{
		if (aGetDate != null && !aGetDate.equals("") )
		{
			GetDate = fDate.getDate( aGetDate );
		}
		else
			GetDate = null;
	}

	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	/**
	* caseno
	*/
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	/**
	* 共保退保手续费返还比例备用字段
	*/
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	public double getStandbyDouble1()
	{
		return StandbyDouble1;
	}
	public void setStandbyDouble1(double aStandbyDouble1)
	{
		StandbyDouble1 = aStandbyDouble1;
	}
	public void setStandbyDouble1(String aStandbyDouble1)
	{
		if (aStandbyDouble1 != null && !aStandbyDouble1.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble1);
			double d = tDouble.doubleValue();
			StandbyDouble1 = d;
		}
	}

	public double getStandbyDouble2()
	{
		return StandbyDouble2;
	}
	public void setStandbyDouble2(double aStandbyDouble2)
	{
		StandbyDouble2 = aStandbyDouble2;
	}
	public void setStandbyDouble2(String aStandbyDouble2)
	{
		if (aStandbyDouble2 != null && !aStandbyDouble2.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble2);
			double d = tDouble.doubleValue();
			StandbyDouble2 = d;
		}
	}

	public double getStandbyDouble3()
	{
		return StandbyDouble3;
	}
	public void setStandbyDouble3(double aStandbyDouble3)
	{
		StandbyDouble3 = aStandbyDouble3;
	}
	public void setStandbyDouble3(String aStandbyDouble3)
	{
		if (aStandbyDouble3 != null && !aStandbyDouble3.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble3);
			double d = tDouble.doubleValue();
			StandbyDouble3 = d;
		}
	}

	public String getStandbyDate1()
	{
		if( StandbyDate1 != null )
			return fDate.getString(StandbyDate1);
		else
			return null;
	}
	public void setStandbyDate1(Date aStandbyDate1)
	{
		StandbyDate1 = aStandbyDate1;
	}
	public void setStandbyDate1(String aStandbyDate1)
	{
		if (aStandbyDate1 != null && !aStandbyDate1.equals("") )
		{
			StandbyDate1 = fDate.getDate( aStandbyDate1 );
		}
		else
			StandbyDate1 = null;
	}

	public String getStandbyDate2()
	{
		if( StandbyDate2 != null )
			return fDate.getString(StandbyDate2);
		else
			return null;
	}
	public void setStandbyDate2(Date aStandbyDate2)
	{
		StandbyDate2 = aStandbyDate2;
	}
	public void setStandbyDate2(String aStandbyDate2)
	{
		if (aStandbyDate2 != null && !aStandbyDate2.equals("") )
		{
			StandbyDate2 = fDate.getDate( aStandbyDate2 );
		}
		else
			StandbyDate2 = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	public double getPreChRiskAmnt()
	{
		return PreChRiskAmnt;
	}
	public void setPreChRiskAmnt(double aPreChRiskAmnt)
	{
		PreChRiskAmnt = aPreChRiskAmnt;
	}
	public void setPreChRiskAmnt(String aPreChRiskAmnt)
	{
		if (aPreChRiskAmnt != null && !aPreChRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aPreChRiskAmnt);
			double d = tDouble.doubleValue();
			PreChRiskAmnt = d;
		}
	}

	public double getChRiskAmnt()
	{
		return ChRiskAmnt;
	}
	public void setChRiskAmnt(double aChRiskAmnt)
	{
		ChRiskAmnt = aChRiskAmnt;
	}
	public void setChRiskAmnt(String aChRiskAmnt)
	{
		if (aChRiskAmnt != null && !aChRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aChRiskAmnt);
			double d = tDouble.doubleValue();
			ChRiskAmnt = d;
		}
	}

	public double getPreChangeRate()
	{
		return PreChangeRate;
	}
	public void setPreChangeRate(double aPreChangeRate)
	{
		PreChangeRate = aPreChangeRate;
	}
	public void setPreChangeRate(String aPreChangeRate)
	{
		if (aPreChangeRate != null && !aPreChangeRate.equals(""))
		{
			Double tDouble = new Double(aPreChangeRate);
			double d = tDouble.doubleValue();
			PreChangeRate = d;
		}
	}

	public String getMainPolNo()
	{
		return MainPolNo;
	}
	public void setMainPolNo(String aMainPolNo)
	{
		MainPolNo = aMainPolNo;
	}
	public String getRIPreceptType()
	{
		return RIPreceptType;
	}
	public void setRIPreceptType(String aRIPreceptType)
	{
		RIPreceptType = aRIPreceptType;
	}
	/**
	* 01-业务标记，02-财务标记
	*/
	public String getBFFlag()
	{
		return BFFlag;
	}
	public void setBFFlag(String aBFFlag)
	{
		BFFlag = aBFFlag;
	}
	public double getEdorPrem()
	{
		return EdorPrem;
	}
	public void setEdorPrem(double aEdorPrem)
	{
		EdorPrem = aEdorPrem;
	}
	public void setEdorPrem(String aEdorPrem)
	{
		if (aEdorPrem != null && !aEdorPrem.equals(""))
		{
			Double tDouble = new Double(aEdorPrem);
			double d = tDouble.doubleValue();
			EdorPrem = d;
		}
	}

	public double getChEdorPrem()
	{
		return ChEdorPrem;
	}
	public void setChEdorPrem(double aChEdorPrem)
	{
		ChEdorPrem = aChEdorPrem;
	}
	public void setChEdorPrem(String aChEdorPrem)
	{
		if (aChEdorPrem != null && !aChEdorPrem.equals(""))
		{
			Double tDouble = new Double(aChEdorPrem);
			double d = tDouble.doubleValue();
			ChEdorPrem = d;
		}
	}

	public double getChPrem()
	{
		return ChPrem;
	}
	public void setChPrem(double aChPrem)
	{
		ChPrem = aChPrem;
	}
	public void setChPrem(String aChPrem)
	{
		if (aChPrem != null && !aChPrem.equals(""))
		{
			Double tDouble = new Double(aChPrem);
			double d = tDouble.doubleValue();
			ChPrem = d;
		}
	}

	public double getBonus1()
	{
		return Bonus1;
	}
	public void setBonus1(double aBonus1)
	{
		Bonus1 = aBonus1;
	}
	public void setBonus1(String aBonus1)
	{
		if (aBonus1 != null && !aBonus1.equals(""))
		{
			Double tDouble = new Double(aBonus1);
			double d = tDouble.doubleValue();
			Bonus1 = d;
		}
	}

	public double getBonus2()
	{
		return Bonus2;
	}
	public void setBonus2(double aBonus2)
	{
		Bonus2 = aBonus2;
	}
	public void setBonus2(String aBonus2)
	{
		if (aBonus2 != null && !aBonus2.equals(""))
		{
			Double tDouble = new Double(aBonus2);
			double d = tDouble.doubleValue();
			Bonus2 = d;
		}
	}

	public double getBonus3()
	{
		return Bonus3;
	}
	public void setBonus3(double aBonus3)
	{
		Bonus3 = aBonus3;
	}
	public void setBonus3(String aBonus3)
	{
		if (aBonus3 != null && !aBonus3.equals(""))
		{
			Double tDouble = new Double(aBonus3);
			double d = tDouble.doubleValue();
			Bonus3 = d;
		}
	}

	public double getExtRate1()
	{
		return ExtRate1;
	}
	public void setExtRate1(double aExtRate1)
	{
		ExtRate1 = aExtRate1;
	}
	public void setExtRate1(String aExtRate1)
	{
		if (aExtRate1 != null && !aExtRate1.equals(""))
		{
			Double tDouble = new Double(aExtRate1);
			double d = tDouble.doubleValue();
			ExtRate1 = d;
		}
	}

	public double getExtRate2()
	{
		return ExtRate2;
	}
	public void setExtRate2(double aExtRate2)
	{
		ExtRate2 = aExtRate2;
	}
	public void setExtRate2(String aExtRate2)
	{
		if (aExtRate2 != null && !aExtRate2.equals(""))
		{
			Double tDouble = new Double(aExtRate2);
			double d = tDouble.doubleValue();
			ExtRate2 = d;
		}
	}

	public double getExtRate3()
	{
		return ExtRate3;
	}
	public void setExtRate3(double aExtRate3)
	{
		ExtRate3 = aExtRate3;
	}
	public void setExtRate3(String aExtRate3)
	{
		if (aExtRate3 != null && !aExtRate3.equals(""))
		{
			Double tDouble = new Double(aExtRate3);
			double d = tDouble.doubleValue();
			ExtRate3 = d;
		}
	}

	public double getExtPrem1()
	{
		return ExtPrem1;
	}
	public void setExtPrem1(double aExtPrem1)
	{
		ExtPrem1 = aExtPrem1;
	}
	public void setExtPrem1(String aExtPrem1)
	{
		if (aExtPrem1 != null && !aExtPrem1.equals(""))
		{
			Double tDouble = new Double(aExtPrem1);
			double d = tDouble.doubleValue();
			ExtPrem1 = d;
		}
	}

	public double getExtPrem2()
	{
		return ExtPrem2;
	}
	public void setExtPrem2(double aExtPrem2)
	{
		ExtPrem2 = aExtPrem2;
	}
	public void setExtPrem2(String aExtPrem2)
	{
		if (aExtPrem2 != null && !aExtPrem2.equals(""))
		{
			Double tDouble = new Double(aExtPrem2);
			double d = tDouble.doubleValue();
			ExtPrem2 = d;
		}
	}

	public double getExtPrem3()
	{
		return ExtPrem3;
	}
	public void setExtPrem3(double aExtPrem3)
	{
		ExtPrem3 = aExtPrem3;
	}
	public void setExtPrem3(String aExtPrem3)
	{
		if (aExtPrem3 != null && !aExtPrem3.equals(""))
		{
			Double tDouble = new Double(aExtPrem3);
			double d = tDouble.doubleValue();
			ExtPrem3 = d;
		}
	}

	public String getStandbyString4()
	{
		return StandbyString4;
	}
	public void setStandbyString4(String aStandbyString4)
	{
		StandbyString4 = aStandbyString4;
	}
	public String getStandbyString5()
	{
		return StandbyString5;
	}
	public void setStandbyString5(String aStandbyString5)
	{
		StandbyString5 = aStandbyString5;
	}
	public String getStandbyString6()
	{
		return StandbyString6;
	}
	public void setStandbyString6(String aStandbyString6)
	{
		StandbyString6 = aStandbyString6;
	}
	public String getStandbyString7()
	{
		return StandbyString7;
	}
	public void setStandbyString7(String aStandbyString7)
	{
		StandbyString7 = aStandbyString7;
	}
	public String getStandbyString8()
	{
		return StandbyString8;
	}
	public void setStandbyString8(String aStandbyString8)
	{
		StandbyString8 = aStandbyString8;
	}
	public String getStandbyString9()
	{
		return StandbyString9;
	}
	public void setStandbyString9(String aStandbyString9)
	{
		StandbyString9 = aStandbyString9;
	}
	public double getStandbyDouble4()
	{
		return StandbyDouble4;
	}
	public void setStandbyDouble4(double aStandbyDouble4)
	{
		StandbyDouble4 = aStandbyDouble4;
	}
	public void setStandbyDouble4(String aStandbyDouble4)
	{
		if (aStandbyDouble4 != null && !aStandbyDouble4.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble4);
			double d = tDouble.doubleValue();
			StandbyDouble4 = d;
		}
	}

	public double getStandbyDouble5()
	{
		return StandbyDouble5;
	}
	public void setStandbyDouble5(double aStandbyDouble5)
	{
		StandbyDouble5 = aStandbyDouble5;
	}
	public void setStandbyDouble5(String aStandbyDouble5)
	{
		if (aStandbyDouble5 != null && !aStandbyDouble5.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble5);
			double d = tDouble.doubleValue();
			StandbyDouble5 = d;
		}
	}

	public double getStandbyDouble6()
	{
		return StandbyDouble6;
	}
	public void setStandbyDouble6(double aStandbyDouble6)
	{
		StandbyDouble6 = aStandbyDouble6;
	}
	public void setStandbyDouble6(String aStandbyDouble6)
	{
		if (aStandbyDouble6 != null && !aStandbyDouble6.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble6);
			double d = tDouble.doubleValue();
			StandbyDouble6 = d;
		}
	}

	public double getStandbyDouble7()
	{
		return StandbyDouble7;
	}
	public void setStandbyDouble7(double aStandbyDouble7)
	{
		StandbyDouble7 = aStandbyDouble7;
	}
	public void setStandbyDouble7(String aStandbyDouble7)
	{
		if (aStandbyDouble7 != null && !aStandbyDouble7.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble7);
			double d = tDouble.doubleValue();
			StandbyDouble7 = d;
		}
	}

	public double getStandbyDouble8()
	{
		return StandbyDouble8;
	}
	public void setStandbyDouble8(double aStandbyDouble8)
	{
		StandbyDouble8 = aStandbyDouble8;
	}
	public void setStandbyDouble8(String aStandbyDouble8)
	{
		if (aStandbyDouble8 != null && !aStandbyDouble8.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble8);
			double d = tDouble.doubleValue();
			StandbyDouble8 = d;
		}
	}

	public double getStandbyDouble9()
	{
		return StandbyDouble9;
	}
	public void setStandbyDouble9(double aStandbyDouble9)
	{
		StandbyDouble9 = aStandbyDouble9;
	}
	public void setStandbyDouble9(String aStandbyDouble9)
	{
		if (aStandbyDouble9 != null && !aStandbyDouble9.equals(""))
		{
			Double tDouble = new Double(aStandbyDouble9);
			double d = tDouble.doubleValue();
			StandbyDouble9 = d;
		}
	}

	public String getStandbyDate3()
	{
		if( StandbyDate3 != null )
			return fDate.getString(StandbyDate3);
		else
			return null;
	}
	public void setStandbyDate3(Date aStandbyDate3)
	{
		StandbyDate3 = aStandbyDate3;
	}
	public void setStandbyDate3(String aStandbyDate3)
	{
		if (aStandbyDate3 != null && !aStandbyDate3.equals("") )
		{
			StandbyDate3 = fDate.getDate( aStandbyDate3 );
		}
		else
			StandbyDate3 = null;
	}

	public String getStandbyDate4()
	{
		if( StandbyDate4 != null )
			return fDate.getString(StandbyDate4);
		else
			return null;
	}
	public void setStandbyDate4(Date aStandbyDate4)
	{
		StandbyDate4 = aStandbyDate4;
	}
	public void setStandbyDate4(String aStandbyDate4)
	{
		if (aStandbyDate4 != null && !aStandbyDate4.equals("") )
		{
			StandbyDate4 = fDate.getDate( aStandbyDate4 );
		}
		else
			StandbyDate4 = null;
	}

	public String getStandbyDate5()
	{
		if( StandbyDate5 != null )
			return fDate.getString(StandbyDate5);
		else
			return null;
	}
	public void setStandbyDate5(Date aStandbyDate5)
	{
		StandbyDate5 = aStandbyDate5;
	}
	public void setStandbyDate5(String aStandbyDate5)
	{
		if (aStandbyDate5 != null && !aStandbyDate5.equals("") )
		{
			StandbyDate5 = fDate.getDate( aStandbyDate5 );
		}
		else
			StandbyDate5 = null;
	}


	/**
	* 使用另外一个 RIPolRecordBakeSchema 对象给 Schema 赋值
	* @param: aRIPolRecordBakeSchema RIPolRecordBakeSchema
	**/
	public void setSchema(RIPolRecordBakeSchema aRIPolRecordBakeSchema)
	{
		this.BatchNo = aRIPolRecordBakeSchema.getBatchNo();
		this.EventNo = aRIPolRecordBakeSchema.getEventNo();
		this.EventType = aRIPolRecordBakeSchema.getEventType();
		this.RecordType = aRIPolRecordBakeSchema.getRecordType();
		this.NodeState = aRIPolRecordBakeSchema.getNodeState();
		this.DataFlag = aRIPolRecordBakeSchema.getDataFlag();
		this.UnionKey = aRIPolRecordBakeSchema.getUnionKey();
		this.GrpContNo = aRIPolRecordBakeSchema.getGrpContNo();
		this.ProposalGrpContNo = aRIPolRecordBakeSchema.getProposalGrpContNo();
		this.GrpPolNo = aRIPolRecordBakeSchema.getGrpPolNo();
		this.GrpProposalNo = aRIPolRecordBakeSchema.getGrpProposalNo();
		this.ContNo = aRIPolRecordBakeSchema.getContNo();
		this.PolNo = aRIPolRecordBakeSchema.getPolNo();
		this.ProposalNo = aRIPolRecordBakeSchema.getProposalNo();
		this.ContPlanCode = aRIPolRecordBakeSchema.getContPlanCode();
		this.PlanType = aRIPolRecordBakeSchema.getPlanType();
		this.RiskPeriod = aRIPolRecordBakeSchema.getRiskPeriod();
		this.RiskType = aRIPolRecordBakeSchema.getRiskType();
		this.RiskCode = aRIPolRecordBakeSchema.getRiskCode();
		this.DutyCode = aRIPolRecordBakeSchema.getDutyCode();
		this.Years = aRIPolRecordBakeSchema.getYears();
		this.InsuredYear = aRIPolRecordBakeSchema.getInsuredYear();
		this.SaleChnl = aRIPolRecordBakeSchema.getSaleChnl();
		this.CValiDate = fDate.getDate( aRIPolRecordBakeSchema.getCValiDate());
		this.EndDate = fDate.getDate( aRIPolRecordBakeSchema.getEndDate());
		this.InsuredNo = aRIPolRecordBakeSchema.getInsuredNo();
		this.InsuredName = aRIPolRecordBakeSchema.getInsuredName();
		this.InsuredSex = aRIPolRecordBakeSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aRIPolRecordBakeSchema.getInsuredBirthday());
		this.InsuredAge = aRIPolRecordBakeSchema.getInsuredAge();
		this.CurrentAge = aRIPolRecordBakeSchema.getCurrentAge();
		this.IDType = aRIPolRecordBakeSchema.getIDType();
		this.IDNo = aRIPolRecordBakeSchema.getIDNo();
		this.OccupationType = aRIPolRecordBakeSchema.getOccupationType();
		this.HealthTime1 = aRIPolRecordBakeSchema.getHealthTime1();
		this.HealthTime2 = aRIPolRecordBakeSchema.getHealthTime2();
		this.OccupationCode = aRIPolRecordBakeSchema.getOccupationCode();
		this.SuppRiskScore = aRIPolRecordBakeSchema.getSuppRiskScore();
		this.SmokeFlag = aRIPolRecordBakeSchema.getSmokeFlag();
		this.AdditionalRate = aRIPolRecordBakeSchema.getAdditionalRate();
		this.ExtType = aRIPolRecordBakeSchema.getExtType();
		this.ExtPrem = aRIPolRecordBakeSchema.getExtPrem();
		this.BegAccountValue = aRIPolRecordBakeSchema.getBegAccountValue();
		this.EndUnit = aRIPolRecordBakeSchema.getEndUnit();
		this.EndPrice = aRIPolRecordBakeSchema.getEndPrice();
		this.EndAccountValue = aRIPolRecordBakeSchema.getEndAccountValue();
		this.FeeType = aRIPolRecordBakeSchema.getFeeType();
		this.FeeMoney = aRIPolRecordBakeSchema.getFeeMoney();
		this.NonCashFlag = aRIPolRecordBakeSchema.getNonCashFlag();
		this.StandPrem = aRIPolRecordBakeSchema.getStandPrem();
		this.Prem = aRIPolRecordBakeSchema.getPrem();
		this.Amnt = aRIPolRecordBakeSchema.getAmnt();
		this.Mult = aRIPolRecordBakeSchema.getMult();
		this.Reserve = aRIPolRecordBakeSchema.getReserve();
		this.APE = aRIPolRecordBakeSchema.getAPE();
		this.CSV = aRIPolRecordBakeSchema.getCSV();
		this.Retention = aRIPolRecordBakeSchema.getRetention();
		this.Suminsured = aRIPolRecordBakeSchema.getSuminsured();
		this.Faceamount = aRIPolRecordBakeSchema.getFaceamount();
		this.Currency = aRIPolRecordBakeSchema.getCurrency();
		this.RiskAmnt = aRIPolRecordBakeSchema.getRiskAmnt();
		this.PayIntv = aRIPolRecordBakeSchema.getPayIntv();
		this.PayYears = aRIPolRecordBakeSchema.getPayYears();
		this.PayEndYearFlag = aRIPolRecordBakeSchema.getPayEndYearFlag();
		this.PayEndYear = aRIPolRecordBakeSchema.getPayEndYear();
		this.GetYearFlag = aRIPolRecordBakeSchema.getGetYearFlag();
		this.GetYear = aRIPolRecordBakeSchema.getGetYear();
		this.InsuYearFlag = aRIPolRecordBakeSchema.getInsuYearFlag();
		this.InsuYear = aRIPolRecordBakeSchema.getInsuYear();
		this.AcciYearFlag = aRIPolRecordBakeSchema.getAcciYearFlag();
		this.AcciYear = aRIPolRecordBakeSchema.getAcciYear();
		this.AcciEndDate = fDate.getDate( aRIPolRecordBakeSchema.getAcciEndDate());
		this.GetStartDate = fDate.getDate( aRIPolRecordBakeSchema.getGetStartDate());
		this.GetStartType = aRIPolRecordBakeSchema.getGetStartType();
		this.PeakLine = aRIPolRecordBakeSchema.getPeakLine();
		this.GetLimit = aRIPolRecordBakeSchema.getGetLimit();
		this.GetIntv = aRIPolRecordBakeSchema.getGetIntv();
		this.PayNo = aRIPolRecordBakeSchema.getPayNo();
		this.PayCount = aRIPolRecordBakeSchema.getPayCount();
		this.PayMoney = aRIPolRecordBakeSchema.getPayMoney();
		this.LastPayToDate = fDate.getDate( aRIPolRecordBakeSchema.getLastPayToDate());
		this.CurPayToDate = fDate.getDate( aRIPolRecordBakeSchema.getCurPayToDate());
		this.EdorNo = aRIPolRecordBakeSchema.getEdorNo();
		this.FeeOperationType = aRIPolRecordBakeSchema.getFeeOperationType();
		this.FeeFinaType = aRIPolRecordBakeSchema.getFeeFinaType();
		this.ChangeRate = aRIPolRecordBakeSchema.getChangeRate();
		this.AccCurrency = aRIPolRecordBakeSchema.getAccCurrency();
		this.AccAmnt = aRIPolRecordBakeSchema.getAccAmnt();
		this.PreStandPrem = aRIPolRecordBakeSchema.getPreStandPrem();
		this.PrePrem = aRIPolRecordBakeSchema.getPrePrem();
		this.PreRiskAmnt = aRIPolRecordBakeSchema.getPreRiskAmnt();
		this.PreAmnt = aRIPolRecordBakeSchema.getPreAmnt();
		this.ClmNo = aRIPolRecordBakeSchema.getClmNo();
		this.ClmFeeOperationType = aRIPolRecordBakeSchema.getClmFeeOperationType();
		this.ClmFeeFinaType = aRIPolRecordBakeSchema.getClmFeeFinaType();
		this.StandGetMoney = aRIPolRecordBakeSchema.getStandGetMoney();
		this.GetRate = aRIPolRecordBakeSchema.getGetRate();
		this.ClmGetMoney = aRIPolRecordBakeSchema.getClmGetMoney();
		this.AccDate = fDate.getDate( aRIPolRecordBakeSchema.getAccDate());
		this.AccumulateDefNO = aRIPolRecordBakeSchema.getAccumulateDefNO();
		this.RIContNo = aRIPolRecordBakeSchema.getRIContNo();
		this.RIPreceptNo = aRIPolRecordBakeSchema.getRIPreceptNo();
		this.ReinsreFlag = aRIPolRecordBakeSchema.getReinsreFlag();
		this.GetDate = fDate.getDate( aRIPolRecordBakeSchema.getGetDate());
		this.StandbyString1 = aRIPolRecordBakeSchema.getStandbyString1();
		this.StandbyString2 = aRIPolRecordBakeSchema.getStandbyString2();
		this.StandbyString3 = aRIPolRecordBakeSchema.getStandbyString3();
		this.StandbyDouble1 = aRIPolRecordBakeSchema.getStandbyDouble1();
		this.StandbyDouble2 = aRIPolRecordBakeSchema.getStandbyDouble2();
		this.StandbyDouble3 = aRIPolRecordBakeSchema.getStandbyDouble3();
		this.StandbyDate1 = fDate.getDate( aRIPolRecordBakeSchema.getStandbyDate1());
		this.StandbyDate2 = fDate.getDate( aRIPolRecordBakeSchema.getStandbyDate2());
		this.ManageCom = aRIPolRecordBakeSchema.getManageCom();
		this.MakeDate = fDate.getDate( aRIPolRecordBakeSchema.getMakeDate());
		this.MakeTime = aRIPolRecordBakeSchema.getMakeTime();
		this.PreChRiskAmnt = aRIPolRecordBakeSchema.getPreChRiskAmnt();
		this.ChRiskAmnt = aRIPolRecordBakeSchema.getChRiskAmnt();
		this.PreChangeRate = aRIPolRecordBakeSchema.getPreChangeRate();
		this.MainPolNo = aRIPolRecordBakeSchema.getMainPolNo();
		this.RIPreceptType = aRIPolRecordBakeSchema.getRIPreceptType();
		this.BFFlag = aRIPolRecordBakeSchema.getBFFlag();
		this.EdorPrem = aRIPolRecordBakeSchema.getEdorPrem();
		this.ChEdorPrem = aRIPolRecordBakeSchema.getChEdorPrem();
		this.ChPrem = aRIPolRecordBakeSchema.getChPrem();
		this.Bonus1 = aRIPolRecordBakeSchema.getBonus1();
		this.Bonus2 = aRIPolRecordBakeSchema.getBonus2();
		this.Bonus3 = aRIPolRecordBakeSchema.getBonus3();
		this.ExtRate1 = aRIPolRecordBakeSchema.getExtRate1();
		this.ExtRate2 = aRIPolRecordBakeSchema.getExtRate2();
		this.ExtRate3 = aRIPolRecordBakeSchema.getExtRate3();
		this.ExtPrem1 = aRIPolRecordBakeSchema.getExtPrem1();
		this.ExtPrem2 = aRIPolRecordBakeSchema.getExtPrem2();
		this.ExtPrem3 = aRIPolRecordBakeSchema.getExtPrem3();
		this.StandbyString4 = aRIPolRecordBakeSchema.getStandbyString4();
		this.StandbyString5 = aRIPolRecordBakeSchema.getStandbyString5();
		this.StandbyString6 = aRIPolRecordBakeSchema.getStandbyString6();
		this.StandbyString7 = aRIPolRecordBakeSchema.getStandbyString7();
		this.StandbyString8 = aRIPolRecordBakeSchema.getStandbyString8();
		this.StandbyString9 = aRIPolRecordBakeSchema.getStandbyString9();
		this.StandbyDouble4 = aRIPolRecordBakeSchema.getStandbyDouble4();
		this.StandbyDouble5 = aRIPolRecordBakeSchema.getStandbyDouble5();
		this.StandbyDouble6 = aRIPolRecordBakeSchema.getStandbyDouble6();
		this.StandbyDouble7 = aRIPolRecordBakeSchema.getStandbyDouble7();
		this.StandbyDouble8 = aRIPolRecordBakeSchema.getStandbyDouble8();
		this.StandbyDouble9 = aRIPolRecordBakeSchema.getStandbyDouble9();
		this.StandbyDate3 = fDate.getDate( aRIPolRecordBakeSchema.getStandbyDate3());
		this.StandbyDate4 = fDate.getDate( aRIPolRecordBakeSchema.getStandbyDate4());
		this.StandbyDate5 = fDate.getDate( aRIPolRecordBakeSchema.getStandbyDate5());
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: rs ResultSet
	* @param: i int
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("EventNo") == null )
				this.EventNo = null;
			else
				this.EventNo = rs.getString("EventNo").trim();

			if( rs.getString("EventType") == null )
				this.EventType = null;
			else
				this.EventType = rs.getString("EventType").trim();

			if( rs.getString("RecordType") == null )
				this.RecordType = null;
			else
				this.RecordType = rs.getString("RecordType").trim();

			if( rs.getString("NodeState") == null )
				this.NodeState = null;
			else
				this.NodeState = rs.getString("NodeState").trim();

			if( rs.getString("DataFlag") == null )
				this.DataFlag = null;
			else
				this.DataFlag = rs.getString("DataFlag").trim();

			if( rs.getString("UnionKey") == null )
				this.UnionKey = null;
			else
				this.UnionKey = rs.getString("UnionKey").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			if( rs.getString("RiskPeriod") == null )
				this.RiskPeriod = null;
			else
				this.RiskPeriod = rs.getString("RiskPeriod").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			this.Years = rs.getInt("Years");
			this.InsuredYear = rs.getInt("InsuredYear");
			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			this.InsuredAge = rs.getInt("InsuredAge");
			this.CurrentAge = rs.getInt("CurrentAge");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("HealthTime1") == null )
				this.HealthTime1 = null;
			else
				this.HealthTime1 = rs.getString("HealthTime1").trim();

			if( rs.getString("HealthTime2") == null )
				this.HealthTime2 = null;
			else
				this.HealthTime2 = rs.getString("HealthTime2").trim();

			if( rs.getString("OccupationCode") == null )
				this.OccupationCode = null;
			else
				this.OccupationCode = rs.getString("OccupationCode").trim();

			if( rs.getString("SuppRiskScore") == null )
				this.SuppRiskScore = null;
			else
				this.SuppRiskScore = rs.getString("SuppRiskScore").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			this.AdditionalRate = rs.getDouble("AdditionalRate");
			if( rs.getString("ExtType") == null )
				this.ExtType = null;
			else
				this.ExtType = rs.getString("ExtType").trim();

			this.ExtPrem = rs.getDouble("ExtPrem");
			this.BegAccountValue = rs.getDouble("BegAccountValue");
			this.EndUnit = rs.getInt("EndUnit");
			this.EndPrice = rs.getDouble("EndPrice");
			this.EndAccountValue = rs.getDouble("EndAccountValue");
			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			this.FeeMoney = rs.getDouble("FeeMoney");
			if( rs.getString("NonCashFlag") == null )
				this.NonCashFlag = null;
			else
				this.NonCashFlag = rs.getString("NonCashFlag").trim();

			this.StandPrem = rs.getDouble("StandPrem");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.Mult = rs.getDouble("Mult");
			this.Reserve = rs.getDouble("Reserve");
			this.APE = rs.getDouble("APE");
			this.CSV = rs.getDouble("CSV");
			this.Retention = rs.getDouble("Retention");
			this.Suminsured = rs.getDouble("Suminsured");
			this.Faceamount = rs.getDouble("Faceamount");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.PayIntv = rs.getInt("PayIntv");
			this.PayYears = rs.getInt("PayYears");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			this.GetYear = rs.getInt("GetYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("AcciYearFlag") == null )
				this.AcciYearFlag = null;
			else
				this.AcciYearFlag = rs.getString("AcciYearFlag").trim();

			this.AcciYear = rs.getInt("AcciYear");
			this.AcciEndDate = rs.getDate("AcciEndDate");
			this.GetStartDate = rs.getDate("GetStartDate");
			if( rs.getString("GetStartType") == null )
				this.GetStartType = null;
			else
				this.GetStartType = rs.getString("GetStartType").trim();

			this.PeakLine = rs.getDouble("PeakLine");
			this.GetLimit = rs.getDouble("GetLimit");
			this.GetIntv = rs.getInt("GetIntv");
			if( rs.getString("PayNo") == null )
				this.PayNo = null;
			else
				this.PayNo = rs.getString("PayNo").trim();

			this.PayCount = rs.getInt("PayCount");
			this.PayMoney = rs.getDouble("PayMoney");
			this.LastPayToDate = rs.getDate("LastPayToDate");
			this.CurPayToDate = rs.getDate("CurPayToDate");
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("FeeOperationType") == null )
				this.FeeOperationType = null;
			else
				this.FeeOperationType = rs.getString("FeeOperationType").trim();

			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			this.ChangeRate = rs.getDouble("ChangeRate");
			if( rs.getString("AccCurrency") == null )
				this.AccCurrency = null;
			else
				this.AccCurrency = rs.getString("AccCurrency").trim();

			this.AccAmnt = rs.getDouble("AccAmnt");
			this.PreStandPrem = rs.getDouble("PreStandPrem");
			this.PrePrem = rs.getDouble("PrePrem");
			this.PreRiskAmnt = rs.getDouble("PreRiskAmnt");
			this.PreAmnt = rs.getDouble("PreAmnt");
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("ClmFeeOperationType") == null )
				this.ClmFeeOperationType = null;
			else
				this.ClmFeeOperationType = rs.getString("ClmFeeOperationType").trim();

			if( rs.getString("ClmFeeFinaType") == null )
				this.ClmFeeFinaType = null;
			else
				this.ClmFeeFinaType = rs.getString("ClmFeeFinaType").trim();

			this.StandGetMoney = rs.getDouble("StandGetMoney");
			this.GetRate = rs.getDouble("GetRate");
			this.ClmGetMoney = rs.getDouble("ClmGetMoney");
			this.AccDate = rs.getDate("AccDate");
			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("ReinsreFlag") == null )
				this.ReinsreFlag = null;
			else
				this.ReinsreFlag = rs.getString("ReinsreFlag").trim();

			this.GetDate = rs.getDate("GetDate");
			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			this.StandbyDouble1 = rs.getDouble("StandbyDouble1");
			this.StandbyDouble2 = rs.getDouble("StandbyDouble2");
			this.StandbyDouble3 = rs.getDouble("StandbyDouble3");
			this.StandbyDate1 = rs.getDate("StandbyDate1");
			this.StandbyDate2 = rs.getDate("StandbyDate2");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.PreChRiskAmnt = rs.getDouble("PreChRiskAmnt");
			this.ChRiskAmnt = rs.getDouble("ChRiskAmnt");
			this.PreChangeRate = rs.getDouble("PreChangeRate");
			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("RIPreceptType") == null )
				this.RIPreceptType = null;
			else
				this.RIPreceptType = rs.getString("RIPreceptType").trim();

			if( rs.getString("BFFlag") == null )
				this.BFFlag = null;
			else
				this.BFFlag = rs.getString("BFFlag").trim();

			this.EdorPrem = rs.getDouble("EdorPrem");
			this.ChEdorPrem = rs.getDouble("ChEdorPrem");
			this.ChPrem = rs.getDouble("ChPrem");
			this.Bonus1 = rs.getDouble("Bonus1");
			this.Bonus2 = rs.getDouble("Bonus2");
			this.Bonus3 = rs.getDouble("Bonus3");
			this.ExtRate1 = rs.getDouble("ExtRate1");
			this.ExtRate2 = rs.getDouble("ExtRate2");
			this.ExtRate3 = rs.getDouble("ExtRate3");
			this.ExtPrem1 = rs.getDouble("ExtPrem1");
			this.ExtPrem2 = rs.getDouble("ExtPrem2");
			this.ExtPrem3 = rs.getDouble("ExtPrem3");
			if( rs.getString("StandbyString4") == null )
				this.StandbyString4 = null;
			else
				this.StandbyString4 = rs.getString("StandbyString4").trim();

			if( rs.getString("StandbyString5") == null )
				this.StandbyString5 = null;
			else
				this.StandbyString5 = rs.getString("StandbyString5").trim();

			if( rs.getString("StandbyString6") == null )
				this.StandbyString6 = null;
			else
				this.StandbyString6 = rs.getString("StandbyString6").trim();

			if( rs.getString("StandbyString7") == null )
				this.StandbyString7 = null;
			else
				this.StandbyString7 = rs.getString("StandbyString7").trim();

			if( rs.getString("StandbyString8") == null )
				this.StandbyString8 = null;
			else
				this.StandbyString8 = rs.getString("StandbyString8").trim();

			if( rs.getString("StandbyString9") == null )
				this.StandbyString9 = null;
			else
				this.StandbyString9 = rs.getString("StandbyString9").trim();

			this.StandbyDouble4 = rs.getDouble("StandbyDouble4");
			this.StandbyDouble5 = rs.getDouble("StandbyDouble5");
			this.StandbyDouble6 = rs.getDouble("StandbyDouble6");
			this.StandbyDouble7 = rs.getDouble("StandbyDouble7");
			this.StandbyDouble8 = rs.getDouble("StandbyDouble8");
			this.StandbyDouble9 = rs.getDouble("StandbyDouble9");
			this.StandbyDate3 = rs.getDate("StandbyDate3");
			this.StandbyDate4 = rs.getDate("StandbyDate4");
			this.StandbyDate5 = rs.getDate("StandbyDate5");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIPolRecordBake表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIPolRecordBakeSchema getSchema()
	{
		RIPolRecordBakeSchema aRIPolRecordBakeSchema = new RIPolRecordBakeSchema();
		aRIPolRecordBakeSchema.setSchema(this);
		return aRIPolRecordBakeSchema;
	}

	public RIPolRecordBakeDB getDB()
	{
		RIPolRecordBakeDB aDBOper = new RIPolRecordBakeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIPolRecordBake描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EventNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EventType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RecordType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DataFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnionKey)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurrentAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthTime1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SuppRiskScore)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdditionalRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExtType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BegAccountValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndAccountValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NonCashFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Reserve));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(APE));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CSV));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Retention));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Suminsured));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Faceamount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AcciYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AcciEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PeakLine));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChangeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCurrency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreStandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrePrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreRiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsreFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreChRiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChRiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreChangeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BFFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EdorPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChEdorPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Bonus1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Bonus2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Bonus3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtRate1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtRate2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtRate3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtPrem1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtPrem2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExtPrem3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandbyDouble9));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate3 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate4 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StandbyDate5 )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIPolRecordBake>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EventNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EventType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RecordType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NodeState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DataFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UnionKey = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			RiskPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			InsuredYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			InsuredAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).intValue();
			CurrentAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			HealthTime1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			HealthTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SuppRiskScore = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			AdditionalRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			ExtType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			ExtPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			BegAccountValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			EndUnit= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).intValue();
			EndPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			EndAccountValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			FeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			NonCashFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).doubleValue();
			Reserve = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).doubleValue();
			APE = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			CSV = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).doubleValue();
			Retention = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).doubleValue();
			Suminsured = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).doubleValue();
			Faceamount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).doubleValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).intValue();
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).intValue();
			AcciYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			AcciYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).intValue();
			AcciEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72,SysConst.PACKAGESPILTER));
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73,SysConst.PACKAGESPILTER));
			GetStartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			PeakLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).doubleValue();
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).intValue();
			PayNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,79,SysConst.PACKAGESPILTER))).intValue();
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).doubleValue();
			LastPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81,SysConst.PACKAGESPILTER));
			CurPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82,SysConst.PACKAGESPILTER));
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			FeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			ChangeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,86,SysConst.PACKAGESPILTER))).doubleValue();
			AccCurrency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			AccAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,88,SysConst.PACKAGESPILTER))).doubleValue();
			PreStandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,89,SysConst.PACKAGESPILTER))).doubleValue();
			PrePrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,90,SysConst.PACKAGESPILTER))).doubleValue();
			PreRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,91,SysConst.PACKAGESPILTER))).doubleValue();
			PreAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,92,SysConst.PACKAGESPILTER))).doubleValue();
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			ClmFeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			ClmFeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			StandGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,96,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,97,SysConst.PACKAGESPILTER))).doubleValue();
			ClmGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,98,SysConst.PACKAGESPILTER))).doubleValue();
			AccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99,SysConst.PACKAGESPILTER));
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			ReinsreFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			GetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104,SysConst.PACKAGESPILTER));
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			StandbyDouble1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,108,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,109,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,110,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111,SysConst.PACKAGESPILTER));
			StandbyDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 113, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			PreChRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,116,SysConst.PACKAGESPILTER))).doubleValue();
			ChRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,117,SysConst.PACKAGESPILTER))).doubleValue();
			PreChangeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,118,SysConst.PACKAGESPILTER))).doubleValue();
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 119, SysConst.PACKAGESPILTER );
			RIPreceptType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 120, SysConst.PACKAGESPILTER );
			BFFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 121, SysConst.PACKAGESPILTER );
			EdorPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,122,SysConst.PACKAGESPILTER))).doubleValue();
			ChEdorPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,123,SysConst.PACKAGESPILTER))).doubleValue();
			ChPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,124,SysConst.PACKAGESPILTER))).doubleValue();
			Bonus1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,125,SysConst.PACKAGESPILTER))).doubleValue();
			Bonus2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,126,SysConst.PACKAGESPILTER))).doubleValue();
			Bonus3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,127,SysConst.PACKAGESPILTER))).doubleValue();
			ExtRate1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,128,SysConst.PACKAGESPILTER))).doubleValue();
			ExtRate2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,129,SysConst.PACKAGESPILTER))).doubleValue();
			ExtRate3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,130,SysConst.PACKAGESPILTER))).doubleValue();
			ExtPrem1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,131,SysConst.PACKAGESPILTER))).doubleValue();
			ExtPrem2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,132,SysConst.PACKAGESPILTER))).doubleValue();
			ExtPrem3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,133,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyString4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 134, SysConst.PACKAGESPILTER );
			StandbyString5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 135, SysConst.PACKAGESPILTER );
			StandbyString6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 136, SysConst.PACKAGESPILTER );
			StandbyString7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 137, SysConst.PACKAGESPILTER );
			StandbyString8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 138, SysConst.PACKAGESPILTER );
			StandbyString9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 139, SysConst.PACKAGESPILTER );
			StandbyDouble4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,140,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,141,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,142,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,143,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,144,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDouble9 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,145,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyDate3 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 146,SysConst.PACKAGESPILTER));
			StandbyDate4 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 147,SysConst.PACKAGESPILTER));
			StandbyDate5 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 148,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode String 希望取得的字段名
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventNo));
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventType));
		}
		if (FCode.equalsIgnoreCase("RecordType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecordType));
		}
		if (FCode.equalsIgnoreCase("NodeState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeState));
		}
		if (FCode.equalsIgnoreCase("DataFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataFlag));
		}
		if (FCode.equalsIgnoreCase("UnionKey"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnionKey));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPeriod));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredYear));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
		}
		if (FCode.equalsIgnoreCase("InsuredAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAge));
		}
		if (FCode.equalsIgnoreCase("CurrentAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrentAge));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("HealthTime1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthTime1));
		}
		if (FCode.equalsIgnoreCase("HealthTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthTime2));
		}
		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationCode));
		}
		if (FCode.equalsIgnoreCase("SuppRiskScore"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuppRiskScore));
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equalsIgnoreCase("AdditionalRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdditionalRate));
		}
		if (FCode.equalsIgnoreCase("ExtType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtType));
		}
		if (FCode.equalsIgnoreCase("ExtPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtPrem));
		}
		if (FCode.equalsIgnoreCase("BegAccountValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BegAccountValue));
		}
		if (FCode.equalsIgnoreCase("EndUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndUnit));
		}
		if (FCode.equalsIgnoreCase("EndPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndPrice));
		}
		if (FCode.equalsIgnoreCase("EndAccountValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndAccountValue));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("FeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMoney));
		}
		if (FCode.equalsIgnoreCase("NonCashFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NonCashFlag));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("Reserve"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reserve));
		}
		if (FCode.equalsIgnoreCase("APE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APE));
		}
		if (FCode.equalsIgnoreCase("CSV"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CSV));
		}
		if (FCode.equalsIgnoreCase("Retention"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Retention));
		}
		if (FCode.equalsIgnoreCase("Suminsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Suminsured));
		}
		if (FCode.equalsIgnoreCase("Faceamount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Faceamount));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmnt));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearFlag));
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("AcciYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYearFlag));
		}
		if (FCode.equalsIgnoreCase("AcciYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcciYear));
		}
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartType));
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeakLine));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("PayNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayNo));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeOperationType));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("ChangeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangeRate));
		}
		if (FCode.equalsIgnoreCase("AccCurrency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCurrency));
		}
		if (FCode.equalsIgnoreCase("AccAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccAmnt));
		}
		if (FCode.equalsIgnoreCase("PreStandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreStandPrem));
		}
		if (FCode.equalsIgnoreCase("PrePrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrePrem));
		}
		if (FCode.equalsIgnoreCase("PreRiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreRiskAmnt));
		}
		if (FCode.equalsIgnoreCase("PreAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreAmnt));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("ClmFeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeOperationType));
		}
		if (FCode.equalsIgnoreCase("ClmFeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeFinaType));
		}
		if (FCode.equalsIgnoreCase("StandGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandGetMoney));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("ClmGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmGetMoney));
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("ReinsreFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsreFlag));
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble1));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble2));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble3));
		}
		if (FCode.equalsIgnoreCase("StandbyDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate1()));
		}
		if (FCode.equalsIgnoreCase("StandbyDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate2()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("PreChRiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreChRiskAmnt));
		}
		if (FCode.equalsIgnoreCase("ChRiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChRiskAmnt));
		}
		if (FCode.equalsIgnoreCase("PreChangeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreChangeRate));
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptType));
		}
		if (FCode.equalsIgnoreCase("BFFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BFFlag));
		}
		if (FCode.equalsIgnoreCase("EdorPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPrem));
		}
		if (FCode.equalsIgnoreCase("ChEdorPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChEdorPrem));
		}
		if (FCode.equalsIgnoreCase("ChPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChPrem));
		}
		if (FCode.equalsIgnoreCase("Bonus1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bonus1));
		}
		if (FCode.equalsIgnoreCase("Bonus2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bonus2));
		}
		if (FCode.equalsIgnoreCase("Bonus3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bonus3));
		}
		if (FCode.equalsIgnoreCase("ExtRate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtRate1));
		}
		if (FCode.equalsIgnoreCase("ExtRate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtRate2));
		}
		if (FCode.equalsIgnoreCase("ExtRate3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtRate3));
		}
		if (FCode.equalsIgnoreCase("ExtPrem1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtPrem1));
		}
		if (FCode.equalsIgnoreCase("ExtPrem2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtPrem2));
		}
		if (FCode.equalsIgnoreCase("ExtPrem3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExtPrem3));
		}
		if (FCode.equalsIgnoreCase("StandbyString4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString4));
		}
		if (FCode.equalsIgnoreCase("StandbyString5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString5));
		}
		if (FCode.equalsIgnoreCase("StandbyString6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString6));
		}
		if (FCode.equalsIgnoreCase("StandbyString7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString7));
		}
		if (FCode.equalsIgnoreCase("StandbyString8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString8));
		}
		if (FCode.equalsIgnoreCase("StandbyString9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString9));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble4));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble5));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble6));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble7));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble8));
		}
		if (FCode.equalsIgnoreCase("StandbyDouble9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyDouble9));
		}
		if (FCode.equalsIgnoreCase("StandbyDate3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate3()));
		}
		if (FCode.equalsIgnoreCase("StandbyDate4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate4()));
		}
		if (FCode.equalsIgnoreCase("StandbyDate5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate5()));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex int 指定的字段索引值
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EventNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EventType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RecordType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NodeState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DataFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UnionKey);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(RiskPeriod);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 20:
				strFieldValue = String.valueOf(Years);
				break;
			case 21:
				strFieldValue = String.valueOf(InsuredYear);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 29:
				strFieldValue = String.valueOf(InsuredAge);
				break;
			case 30:
				strFieldValue = String.valueOf(CurrentAge);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(HealthTime1);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(HealthTime2);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SuppRiskScore);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 39:
				strFieldValue = String.valueOf(AdditionalRate);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(ExtType);
				break;
			case 41:
				strFieldValue = String.valueOf(ExtPrem);
				break;
			case 42:
				strFieldValue = String.valueOf(BegAccountValue);
				break;
			case 43:
				strFieldValue = String.valueOf(EndUnit);
				break;
			case 44:
				strFieldValue = String.valueOf(EndPrice);
				break;
			case 45:
				strFieldValue = String.valueOf(EndAccountValue);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 47:
				strFieldValue = String.valueOf(FeeMoney);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(NonCashFlag);
				break;
			case 49:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 50:
				strFieldValue = String.valueOf(Prem);
				break;
			case 51:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 52:
				strFieldValue = String.valueOf(Mult);
				break;
			case 53:
				strFieldValue = String.valueOf(Reserve);
				break;
			case 54:
				strFieldValue = String.valueOf(APE);
				break;
			case 55:
				strFieldValue = String.valueOf(CSV);
				break;
			case 56:
				strFieldValue = String.valueOf(Retention);
				break;
			case 57:
				strFieldValue = String.valueOf(Suminsured);
				break;
			case 58:
				strFieldValue = String.valueOf(Faceamount);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 60:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 61:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 62:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 64:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 66:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 68:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(AcciYearFlag);
				break;
			case 70:
				strFieldValue = String.valueOf(AcciYear);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(GetStartType);
				break;
			case 74:
				strFieldValue = String.valueOf(PeakLine);
				break;
			case 75:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 76:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(PayNo);
				break;
			case 78:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 79:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(FeeOperationType);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 85:
				strFieldValue = String.valueOf(ChangeRate);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(AccCurrency);
				break;
			case 87:
				strFieldValue = String.valueOf(AccAmnt);
				break;
			case 88:
				strFieldValue = String.valueOf(PreStandPrem);
				break;
			case 89:
				strFieldValue = String.valueOf(PrePrem);
				break;
			case 90:
				strFieldValue = String.valueOf(PreRiskAmnt);
				break;
			case 91:
				strFieldValue = String.valueOf(PreAmnt);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeOperationType);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeFinaType);
				break;
			case 95:
				strFieldValue = String.valueOf(StandGetMoney);
				break;
			case 96:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 97:
				strFieldValue = String.valueOf(ClmGetMoney);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccDate()));
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(ReinsreFlag);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 107:
				strFieldValue = String.valueOf(StandbyDouble1);
				break;
			case 108:
				strFieldValue = String.valueOf(StandbyDouble2);
				break;
			case 109:
				strFieldValue = String.valueOf(StandbyDouble3);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate1()));
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate2()));
				break;
			case 112:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 115:
				strFieldValue = String.valueOf(PreChRiskAmnt);
				break;
			case 116:
				strFieldValue = String.valueOf(ChRiskAmnt);
				break;
			case 117:
				strFieldValue = String.valueOf(PreChangeRate);
				break;
			case 118:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 119:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptType);
				break;
			case 120:
				strFieldValue = StrTool.GBKToUnicode(BFFlag);
				break;
			case 121:
				strFieldValue = String.valueOf(EdorPrem);
				break;
			case 122:
				strFieldValue = String.valueOf(ChEdorPrem);
				break;
			case 123:
				strFieldValue = String.valueOf(ChPrem);
				break;
			case 124:
				strFieldValue = String.valueOf(Bonus1);
				break;
			case 125:
				strFieldValue = String.valueOf(Bonus2);
				break;
			case 126:
				strFieldValue = String.valueOf(Bonus3);
				break;
			case 127:
				strFieldValue = String.valueOf(ExtRate1);
				break;
			case 128:
				strFieldValue = String.valueOf(ExtRate2);
				break;
			case 129:
				strFieldValue = String.valueOf(ExtRate3);
				break;
			case 130:
				strFieldValue = String.valueOf(ExtPrem1);
				break;
			case 131:
				strFieldValue = String.valueOf(ExtPrem2);
				break;
			case 132:
				strFieldValue = String.valueOf(ExtPrem3);
				break;
			case 133:
				strFieldValue = StrTool.GBKToUnicode(StandbyString4);
				break;
			case 134:
				strFieldValue = StrTool.GBKToUnicode(StandbyString5);
				break;
			case 135:
				strFieldValue = StrTool.GBKToUnicode(StandbyString6);
				break;
			case 136:
				strFieldValue = StrTool.GBKToUnicode(StandbyString7);
				break;
			case 137:
				strFieldValue = StrTool.GBKToUnicode(StandbyString8);
				break;
			case 138:
				strFieldValue = StrTool.GBKToUnicode(StandbyString9);
				break;
			case 139:
				strFieldValue = String.valueOf(StandbyDouble4);
				break;
			case 140:
				strFieldValue = String.valueOf(StandbyDouble5);
				break;
			case 141:
				strFieldValue = String.valueOf(StandbyDouble6);
				break;
			case 142:
				strFieldValue = String.valueOf(StandbyDouble7);
				break;
			case 143:
				strFieldValue = String.valueOf(StandbyDouble8);
				break;
			case 144:
				strFieldValue = String.valueOf(StandbyDouble9);
				break;
			case 145:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate3()));
				break;
			case 146:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate4()));
				break;
			case 147:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandbyDate5()));
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
	}

	/**
	* 设置对应传入参数的String形式的字段值
	* @param: FCode String 需要赋值的对象
	* @param: FValue String 要赋的值
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventNo = FValue.trim();
			}
			else
				EventNo = null;
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventType = FValue.trim();
			}
			else
				EventType = null;
		}
		if (FCode.equalsIgnoreCase("RecordType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RecordType = FValue.trim();
			}
			else
				RecordType = null;
		}
		if (FCode.equalsIgnoreCase("NodeState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeState = FValue.trim();
			}
			else
				NodeState = null;
		}
		if (FCode.equalsIgnoreCase("DataFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataFlag = FValue.trim();
			}
			else
				DataFlag = null;
		}
		if (FCode.equalsIgnoreCase("UnionKey"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnionKey = FValue.trim();
			}
			else
				UnionKey = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpProposalNo = FValue.trim();
			}
			else
				GrpProposalNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanType = FValue.trim();
			}
			else
				PlanType = null;
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskPeriod = FValue.trim();
			}
			else
				RiskPeriod = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredBirthday = fDate.getDate( FValue );
			}
			else
				InsuredBirthday = null;
		}
		if (FCode.equalsIgnoreCase("InsuredAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("CurrentAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurrentAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
		}
		if (FCode.equalsIgnoreCase("HealthTime1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthTime1 = FValue.trim();
			}
			else
				HealthTime1 = null;
		}
		if (FCode.equalsIgnoreCase("HealthTime2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthTime2 = FValue.trim();
			}
			else
				HealthTime2 = null;
		}
		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationCode = FValue.trim();
			}
			else
				OccupationCode = null;
		}
		if (FCode.equalsIgnoreCase("SuppRiskScore"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SuppRiskScore = FValue.trim();
			}
			else
				SuppRiskScore = null;
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SmokeFlag = FValue.trim();
			}
			else
				SmokeFlag = null;
		}
		if (FCode.equalsIgnoreCase("AdditionalRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdditionalRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExtType = FValue.trim();
			}
			else
				ExtType = null;
		}
		if (FCode.equalsIgnoreCase("ExtPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("BegAccountValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BegAccountValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndUnit = i;
			}
		}
		if (FCode.equalsIgnoreCase("EndPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EndPrice = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndAccountValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EndAccountValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		if (FCode.equalsIgnoreCase("FeeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("NonCashFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NonCashFlag = FValue.trim();
			}
			else
				NonCashFlag = null;
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reserve"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Reserve = d;
			}
		}
		if (FCode.equalsIgnoreCase("APE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				APE = d;
			}
		}
		if (FCode.equalsIgnoreCase("CSV"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CSV = d;
			}
		}
		if (FCode.equalsIgnoreCase("Retention"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Retention = d;
			}
		}
		if (FCode.equalsIgnoreCase("Suminsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Suminsured = d;
			}
		}
		if (FCode.equalsIgnoreCase("Faceamount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Faceamount = d;
			}
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayEndYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetYearFlag = FValue.trim();
			}
			else
				GetYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("AcciYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcciYearFlag = FValue.trim();
			}
			else
				AcciYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("AcciYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AcciYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AcciEndDate = fDate.getDate( FValue );
			}
			else
				AcciEndDate = null;
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetStartDate = fDate.getDate( FValue );
			}
			else
				GetStartDate = null;
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartType = FValue.trim();
			}
			else
				GetStartType = null;
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PeakLine = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayNo = FValue.trim();
			}
			else
				PayNo = null;
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastPayToDate = fDate.getDate( FValue );
			}
			else
				LastPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurPayToDate = fDate.getDate( FValue );
			}
			else
				CurPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeOperationType = FValue.trim();
			}
			else
				FeeOperationType = null;
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeFinaType = FValue.trim();
			}
			else
				FeeFinaType = null;
		}
		if (FCode.equalsIgnoreCase("ChangeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChangeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccCurrency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCurrency = FValue.trim();
			}
			else
				AccCurrency = null;
		}
		if (FCode.equalsIgnoreCase("AccAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PreStandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreStandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PrePrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrePrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PreRiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreRiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PreAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeOperationType = FValue.trim();
			}
			else
				ClmFeeOperationType = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeFinaType = FValue.trim();
			}
			else
				ClmFeeFinaType = null;
		}
		if (FCode.equalsIgnoreCase("StandGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClmGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccDate = fDate.getDate( FValue );
			}
			else
				AccDate = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("ReinsreFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsreFlag = FValue.trim();
			}
			else
				ReinsreFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetDate = fDate.getDate( FValue );
			}
			else
				GetDate = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDouble1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDate1"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate1 = fDate.getDate( FValue );
			}
			else
				StandbyDate1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate2 = fDate.getDate( FValue );
			}
			else
				StandbyDate2 = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("PreChRiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreChRiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChRiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChRiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PreChangeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreChangeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolNo = FValue.trim();
			}
			else
				MainPolNo = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptType = FValue.trim();
			}
			else
				RIPreceptType = null;
		}
		if (FCode.equalsIgnoreCase("BFFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BFFlag = FValue.trim();
			}
			else
				BFFlag = null;
		}
		if (FCode.equalsIgnoreCase("EdorPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EdorPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChEdorPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChEdorPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Bonus1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Bonus1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Bonus2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Bonus2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Bonus3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Bonus3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtRate1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtRate1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtRate2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtRate2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtRate3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtRate3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtPrem1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtPrem1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtPrem2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtPrem2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExtPrem3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExtPrem3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyString4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString4 = FValue.trim();
			}
			else
				StandbyString4 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString5 = FValue.trim();
			}
			else
				StandbyString5 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString6 = FValue.trim();
			}
			else
				StandbyString6 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString7 = FValue.trim();
			}
			else
				StandbyString7 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString8 = FValue.trim();
			}
			else
				StandbyString8 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString9 = FValue.trim();
			}
			else
				StandbyString9 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDouble4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDouble9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandbyDouble9 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyDate3"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate3 = fDate.getDate( FValue );
			}
			else
				StandbyDate3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDate4"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate4 = fDate.getDate( FValue );
			}
			else
				StandbyDate4 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyDate5"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandbyDate5 = fDate.getDate( FValue );
			}
			else
				StandbyDate5 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIPolRecordBakeSchema other = (RIPolRecordBakeSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& EventNo.equals(other.getEventNo())
			&& EventType.equals(other.getEventType())
			&& RecordType.equals(other.getRecordType())
			&& NodeState.equals(other.getNodeState())
			&& DataFlag.equals(other.getDataFlag())
			&& UnionKey.equals(other.getUnionKey())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& PlanType.equals(other.getPlanType())
			&& RiskPeriod.equals(other.getRiskPeriod())
			&& RiskType.equals(other.getRiskType())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& Years == other.getYears()
			&& InsuredYear == other.getInsuredYear()
			&& SaleChnl.equals(other.getSaleChnl())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredAge == other.getInsuredAge()
			&& CurrentAge == other.getCurrentAge()
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& OccupationType.equals(other.getOccupationType())
			&& HealthTime1.equals(other.getHealthTime1())
			&& HealthTime2.equals(other.getHealthTime2())
			&& OccupationCode.equals(other.getOccupationCode())
			&& SuppRiskScore.equals(other.getSuppRiskScore())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& AdditionalRate == other.getAdditionalRate()
			&& ExtType.equals(other.getExtType())
			&& ExtPrem == other.getExtPrem()
			&& BegAccountValue == other.getBegAccountValue()
			&& EndUnit == other.getEndUnit()
			&& EndPrice == other.getEndPrice()
			&& EndAccountValue == other.getEndAccountValue()
			&& FeeType.equals(other.getFeeType())
			&& FeeMoney == other.getFeeMoney()
			&& NonCashFlag.equals(other.getNonCashFlag())
			&& StandPrem == other.getStandPrem()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& Mult == other.getMult()
			&& Reserve == other.getReserve()
			&& APE == other.getAPE()
			&& CSV == other.getCSV()
			&& Retention == other.getRetention()
			&& Suminsured == other.getSuminsured()
			&& Faceamount == other.getFaceamount()
			&& Currency.equals(other.getCurrency())
			&& RiskAmnt == other.getRiskAmnt()
			&& PayIntv == other.getPayIntv()
			&& PayYears == other.getPayYears()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& GetYear == other.getGetYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& AcciYearFlag.equals(other.getAcciYearFlag())
			&& AcciYear == other.getAcciYear()
			&& fDate.getString(AcciEndDate).equals(other.getAcciEndDate())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& GetStartType.equals(other.getGetStartType())
			&& PeakLine == other.getPeakLine()
			&& GetLimit == other.getGetLimit()
			&& GetIntv == other.getGetIntv()
			&& PayNo.equals(other.getPayNo())
			&& PayCount == other.getPayCount()
			&& PayMoney == other.getPayMoney()
			&& fDate.getString(LastPayToDate).equals(other.getLastPayToDate())
			&& fDate.getString(CurPayToDate).equals(other.getCurPayToDate())
			&& EdorNo.equals(other.getEdorNo())
			&& FeeOperationType.equals(other.getFeeOperationType())
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& ChangeRate == other.getChangeRate()
			&& AccCurrency.equals(other.getAccCurrency())
			&& AccAmnt == other.getAccAmnt()
			&& PreStandPrem == other.getPreStandPrem()
			&& PrePrem == other.getPrePrem()
			&& PreRiskAmnt == other.getPreRiskAmnt()
			&& PreAmnt == other.getPreAmnt()
			&& ClmNo.equals(other.getClmNo())
			&& ClmFeeOperationType.equals(other.getClmFeeOperationType())
			&& ClmFeeFinaType.equals(other.getClmFeeFinaType())
			&& StandGetMoney == other.getStandGetMoney()
			&& GetRate == other.getGetRate()
			&& ClmGetMoney == other.getClmGetMoney()
			&& fDate.getString(AccDate).equals(other.getAccDate())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& RIContNo.equals(other.getRIContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& ReinsreFlag.equals(other.getReinsreFlag())
			&& fDate.getString(GetDate).equals(other.getGetDate())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& StandbyDouble1 == other.getStandbyDouble1()
			&& StandbyDouble2 == other.getStandbyDouble2()
			&& StandbyDouble3 == other.getStandbyDouble3()
			&& fDate.getString(StandbyDate1).equals(other.getStandbyDate1())
			&& fDate.getString(StandbyDate2).equals(other.getStandbyDate2())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& PreChRiskAmnt == other.getPreChRiskAmnt()
			&& ChRiskAmnt == other.getChRiskAmnt()
			&& PreChangeRate == other.getPreChangeRate()
			&& MainPolNo.equals(other.getMainPolNo())
			&& RIPreceptType.equals(other.getRIPreceptType())
			&& BFFlag.equals(other.getBFFlag())
			&& EdorPrem == other.getEdorPrem()
			&& ChEdorPrem == other.getChEdorPrem()
			&& ChPrem == other.getChPrem()
			&& Bonus1 == other.getBonus1()
			&& Bonus2 == other.getBonus2()
			&& Bonus3 == other.getBonus3()
			&& ExtRate1 == other.getExtRate1()
			&& ExtRate2 == other.getExtRate2()
			&& ExtRate3 == other.getExtRate3()
			&& ExtPrem1 == other.getExtPrem1()
			&& ExtPrem2 == other.getExtPrem2()
			&& ExtPrem3 == other.getExtPrem3()
			&& StandbyString4.equals(other.getStandbyString4())
			&& StandbyString5.equals(other.getStandbyString5())
			&& StandbyString6.equals(other.getStandbyString6())
			&& StandbyString7.equals(other.getStandbyString7())
			&& StandbyString8.equals(other.getStandbyString8())
			&& StandbyString9.equals(other.getStandbyString9())
			&& StandbyDouble4 == other.getStandbyDouble4()
			&& StandbyDouble5 == other.getStandbyDouble5()
			&& StandbyDouble6 == other.getStandbyDouble6()
			&& StandbyDouble7 == other.getStandbyDouble7()
			&& StandbyDouble8 == other.getStandbyDouble8()
			&& StandbyDouble9 == other.getStandbyDouble9()
			&& fDate.getString(StandbyDate3).equals(other.getStandbyDate3())
			&& fDate.getString(StandbyDate4).equals(other.getStandbyDate4())
			&& fDate.getString(StandbyDate5).equals(other.getStandbyDate5());
	}

	/**
	* 取得Schema拥有字段的数量
       * @return: int
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("EventNo") ) {
			return 1;
		}
		if( strFieldName.equals("EventType") ) {
			return 2;
		}
		if( strFieldName.equals("RecordType") ) {
			return 3;
		}
		if( strFieldName.equals("NodeState") ) {
			return 4;
		}
		if( strFieldName.equals("DataFlag") ) {
			return 5;
		}
		if( strFieldName.equals("UnionKey") ) {
			return 6;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 7;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 10;
		}
		if( strFieldName.equals("ContNo") ) {
			return 11;
		}
		if( strFieldName.equals("PolNo") ) {
			return 12;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 13;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 14;
		}
		if( strFieldName.equals("PlanType") ) {
			return 15;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return 16;
		}
		if( strFieldName.equals("RiskType") ) {
			return 17;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 18;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 19;
		}
		if( strFieldName.equals("Years") ) {
			return 20;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return 21;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 22;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 23;
		}
		if( strFieldName.equals("EndDate") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 25;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 26;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 27;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 28;
		}
		if( strFieldName.equals("InsuredAge") ) {
			return 29;
		}
		if( strFieldName.equals("CurrentAge") ) {
			return 30;
		}
		if( strFieldName.equals("IDType") ) {
			return 31;
		}
		if( strFieldName.equals("IDNo") ) {
			return 32;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 33;
		}
		if( strFieldName.equals("HealthTime1") ) {
			return 34;
		}
		if( strFieldName.equals("HealthTime2") ) {
			return 35;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 36;
		}
		if( strFieldName.equals("SuppRiskScore") ) {
			return 37;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 38;
		}
		if( strFieldName.equals("AdditionalRate") ) {
			return 39;
		}
		if( strFieldName.equals("ExtType") ) {
			return 40;
		}
		if( strFieldName.equals("ExtPrem") ) {
			return 41;
		}
		if( strFieldName.equals("BegAccountValue") ) {
			return 42;
		}
		if( strFieldName.equals("EndUnit") ) {
			return 43;
		}
		if( strFieldName.equals("EndPrice") ) {
			return 44;
		}
		if( strFieldName.equals("EndAccountValue") ) {
			return 45;
		}
		if( strFieldName.equals("FeeType") ) {
			return 46;
		}
		if( strFieldName.equals("FeeMoney") ) {
			return 47;
		}
		if( strFieldName.equals("NonCashFlag") ) {
			return 48;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 49;
		}
		if( strFieldName.equals("Prem") ) {
			return 50;
		}
		if( strFieldName.equals("Amnt") ) {
			return 51;
		}
		if( strFieldName.equals("Mult") ) {
			return 52;
		}
		if( strFieldName.equals("Reserve") ) {
			return 53;
		}
		if( strFieldName.equals("APE") ) {
			return 54;
		}
		if( strFieldName.equals("CSV") ) {
			return 55;
		}
		if( strFieldName.equals("Retention") ) {
			return 56;
		}
		if( strFieldName.equals("Suminsured") ) {
			return 57;
		}
		if( strFieldName.equals("Faceamount") ) {
			return 58;
		}
		if( strFieldName.equals("Currency") ) {
			return 59;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 60;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 61;
		}
		if( strFieldName.equals("PayYears") ) {
			return 62;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 63;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 64;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 65;
		}
		if( strFieldName.equals("GetYear") ) {
			return 66;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 67;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 68;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return 69;
		}
		if( strFieldName.equals("AcciYear") ) {
			return 70;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return 71;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 72;
		}
		if( strFieldName.equals("GetStartType") ) {
			return 73;
		}
		if( strFieldName.equals("PeakLine") ) {
			return 74;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 75;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 76;
		}
		if( strFieldName.equals("PayNo") ) {
			return 77;
		}
		if( strFieldName.equals("PayCount") ) {
			return 78;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 79;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return 80;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return 81;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 82;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return 83;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 84;
		}
		if( strFieldName.equals("ChangeRate") ) {
			return 85;
		}
		if( strFieldName.equals("AccCurrency") ) {
			return 86;
		}
		if( strFieldName.equals("AccAmnt") ) {
			return 87;
		}
		if( strFieldName.equals("PreStandPrem") ) {
			return 88;
		}
		if( strFieldName.equals("PrePrem") ) {
			return 89;
		}
		if( strFieldName.equals("PreRiskAmnt") ) {
			return 90;
		}
		if( strFieldName.equals("PreAmnt") ) {
			return 91;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 92;
		}
		if( strFieldName.equals("ClmFeeOperationType") ) {
			return 93;
		}
		if( strFieldName.equals("ClmFeeFinaType") ) {
			return 94;
		}
		if( strFieldName.equals("StandGetMoney") ) {
			return 95;
		}
		if( strFieldName.equals("GetRate") ) {
			return 96;
		}
		if( strFieldName.equals("ClmGetMoney") ) {
			return 97;
		}
		if( strFieldName.equals("AccDate") ) {
			return 98;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 99;
		}
		if( strFieldName.equals("RIContNo") ) {
			return 100;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 101;
		}
		if( strFieldName.equals("ReinsreFlag") ) {
			return 102;
		}
		if( strFieldName.equals("GetDate") ) {
			return 103;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 104;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 105;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 106;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return 107;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return 108;
		}
		if( strFieldName.equals("StandbyDouble3") ) {
			return 109;
		}
		if( strFieldName.equals("StandbyDate1") ) {
			return 110;
		}
		if( strFieldName.equals("StandbyDate2") ) {
			return 111;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 112;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 113;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 114;
		}
		if( strFieldName.equals("PreChRiskAmnt") ) {
			return 115;
		}
		if( strFieldName.equals("ChRiskAmnt") ) {
			return 116;
		}
		if( strFieldName.equals("PreChangeRate") ) {
			return 117;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 118;
		}
		if( strFieldName.equals("RIPreceptType") ) {
			return 119;
		}
		if( strFieldName.equals("BFFlag") ) {
			return 120;
		}
		if( strFieldName.equals("EdorPrem") ) {
			return 121;
		}
		if( strFieldName.equals("ChEdorPrem") ) {
			return 122;
		}
		if( strFieldName.equals("ChPrem") ) {
			return 123;
		}
		if( strFieldName.equals("Bonus1") ) {
			return 124;
		}
		if( strFieldName.equals("Bonus2") ) {
			return 125;
		}
		if( strFieldName.equals("Bonus3") ) {
			return 126;
		}
		if( strFieldName.equals("ExtRate1") ) {
			return 127;
		}
		if( strFieldName.equals("ExtRate2") ) {
			return 128;
		}
		if( strFieldName.equals("ExtRate3") ) {
			return 129;
		}
		if( strFieldName.equals("ExtPrem1") ) {
			return 130;
		}
		if( strFieldName.equals("ExtPrem2") ) {
			return 131;
		}
		if( strFieldName.equals("ExtPrem3") ) {
			return 132;
		}
		if( strFieldName.equals("StandbyString4") ) {
			return 133;
		}
		if( strFieldName.equals("StandbyString5") ) {
			return 134;
		}
		if( strFieldName.equals("StandbyString6") ) {
			return 135;
		}
		if( strFieldName.equals("StandbyString7") ) {
			return 136;
		}
		if( strFieldName.equals("StandbyString8") ) {
			return 137;
		}
		if( strFieldName.equals("StandbyString9") ) {
			return 138;
		}
		if( strFieldName.equals("StandbyDouble4") ) {
			return 139;
		}
		if( strFieldName.equals("StandbyDouble5") ) {
			return 140;
		}
		if( strFieldName.equals("StandbyDouble6") ) {
			return 141;
		}
		if( strFieldName.equals("StandbyDouble7") ) {
			return 142;
		}
		if( strFieldName.equals("StandbyDouble8") ) {
			return 143;
		}
		if( strFieldName.equals("StandbyDouble9") ) {
			return 144;
		}
		if( strFieldName.equals("StandbyDate3") ) {
			return 145;
		}
		if( strFieldName.equals("StandbyDate4") ) {
			return 146;
		}
		if( strFieldName.equals("StandbyDate5") ) {
			return 147;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
       * @param: nFieldIndex int
       * @return: String
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "EventNo";
				break;
			case 2:
				strFieldName = "EventType";
				break;
			case 3:
				strFieldName = "RecordType";
				break;
			case 4:
				strFieldName = "NodeState";
				break;
			case 5:
				strFieldName = "DataFlag";
				break;
			case 6:
				strFieldName = "UnionKey";
				break;
			case 7:
				strFieldName = "GrpContNo";
				break;
			case 8:
				strFieldName = "ProposalGrpContNo";
				break;
			case 9:
				strFieldName = "GrpPolNo";
				break;
			case 10:
				strFieldName = "GrpProposalNo";
				break;
			case 11:
				strFieldName = "ContNo";
				break;
			case 12:
				strFieldName = "PolNo";
				break;
			case 13:
				strFieldName = "ProposalNo";
				break;
			case 14:
				strFieldName = "ContPlanCode";
				break;
			case 15:
				strFieldName = "PlanType";
				break;
			case 16:
				strFieldName = "RiskPeriod";
				break;
			case 17:
				strFieldName = "RiskType";
				break;
			case 18:
				strFieldName = "RiskCode";
				break;
			case 19:
				strFieldName = "DutyCode";
				break;
			case 20:
				strFieldName = "Years";
				break;
			case 21:
				strFieldName = "InsuredYear";
				break;
			case 22:
				strFieldName = "SaleChnl";
				break;
			case 23:
				strFieldName = "CValiDate";
				break;
			case 24:
				strFieldName = "EndDate";
				break;
			case 25:
				strFieldName = "InsuredNo";
				break;
			case 26:
				strFieldName = "InsuredName";
				break;
			case 27:
				strFieldName = "InsuredSex";
				break;
			case 28:
				strFieldName = "InsuredBirthday";
				break;
			case 29:
				strFieldName = "InsuredAge";
				break;
			case 30:
				strFieldName = "CurrentAge";
				break;
			case 31:
				strFieldName = "IDType";
				break;
			case 32:
				strFieldName = "IDNo";
				break;
			case 33:
				strFieldName = "OccupationType";
				break;
			case 34:
				strFieldName = "HealthTime1";
				break;
			case 35:
				strFieldName = "HealthTime2";
				break;
			case 36:
				strFieldName = "OccupationCode";
				break;
			case 37:
				strFieldName = "SuppRiskScore";
				break;
			case 38:
				strFieldName = "SmokeFlag";
				break;
			case 39:
				strFieldName = "AdditionalRate";
				break;
			case 40:
				strFieldName = "ExtType";
				break;
			case 41:
				strFieldName = "ExtPrem";
				break;
			case 42:
				strFieldName = "BegAccountValue";
				break;
			case 43:
				strFieldName = "EndUnit";
				break;
			case 44:
				strFieldName = "EndPrice";
				break;
			case 45:
				strFieldName = "EndAccountValue";
				break;
			case 46:
				strFieldName = "FeeType";
				break;
			case 47:
				strFieldName = "FeeMoney";
				break;
			case 48:
				strFieldName = "NonCashFlag";
				break;
			case 49:
				strFieldName = "StandPrem";
				break;
			case 50:
				strFieldName = "Prem";
				break;
			case 51:
				strFieldName = "Amnt";
				break;
			case 52:
				strFieldName = "Mult";
				break;
			case 53:
				strFieldName = "Reserve";
				break;
			case 54:
				strFieldName = "APE";
				break;
			case 55:
				strFieldName = "CSV";
				break;
			case 56:
				strFieldName = "Retention";
				break;
			case 57:
				strFieldName = "Suminsured";
				break;
			case 58:
				strFieldName = "Faceamount";
				break;
			case 59:
				strFieldName = "Currency";
				break;
			case 60:
				strFieldName = "RiskAmnt";
				break;
			case 61:
				strFieldName = "PayIntv";
				break;
			case 62:
				strFieldName = "PayYears";
				break;
			case 63:
				strFieldName = "PayEndYearFlag";
				break;
			case 64:
				strFieldName = "PayEndYear";
				break;
			case 65:
				strFieldName = "GetYearFlag";
				break;
			case 66:
				strFieldName = "GetYear";
				break;
			case 67:
				strFieldName = "InsuYearFlag";
				break;
			case 68:
				strFieldName = "InsuYear";
				break;
			case 69:
				strFieldName = "AcciYearFlag";
				break;
			case 70:
				strFieldName = "AcciYear";
				break;
			case 71:
				strFieldName = "AcciEndDate";
				break;
			case 72:
				strFieldName = "GetStartDate";
				break;
			case 73:
				strFieldName = "GetStartType";
				break;
			case 74:
				strFieldName = "PeakLine";
				break;
			case 75:
				strFieldName = "GetLimit";
				break;
			case 76:
				strFieldName = "GetIntv";
				break;
			case 77:
				strFieldName = "PayNo";
				break;
			case 78:
				strFieldName = "PayCount";
				break;
			case 79:
				strFieldName = "PayMoney";
				break;
			case 80:
				strFieldName = "LastPayToDate";
				break;
			case 81:
				strFieldName = "CurPayToDate";
				break;
			case 82:
				strFieldName = "EdorNo";
				break;
			case 83:
				strFieldName = "FeeOperationType";
				break;
			case 84:
				strFieldName = "FeeFinaType";
				break;
			case 85:
				strFieldName = "ChangeRate";
				break;
			case 86:
				strFieldName = "AccCurrency";
				break;
			case 87:
				strFieldName = "AccAmnt";
				break;
			case 88:
				strFieldName = "PreStandPrem";
				break;
			case 89:
				strFieldName = "PrePrem";
				break;
			case 90:
				strFieldName = "PreRiskAmnt";
				break;
			case 91:
				strFieldName = "PreAmnt";
				break;
			case 92:
				strFieldName = "ClmNo";
				break;
			case 93:
				strFieldName = "ClmFeeOperationType";
				break;
			case 94:
				strFieldName = "ClmFeeFinaType";
				break;
			case 95:
				strFieldName = "StandGetMoney";
				break;
			case 96:
				strFieldName = "GetRate";
				break;
			case 97:
				strFieldName = "ClmGetMoney";
				break;
			case 98:
				strFieldName = "AccDate";
				break;
			case 99:
				strFieldName = "AccumulateDefNO";
				break;
			case 100:
				strFieldName = "RIContNo";
				break;
			case 101:
				strFieldName = "RIPreceptNo";
				break;
			case 102:
				strFieldName = "ReinsreFlag";
				break;
			case 103:
				strFieldName = "GetDate";
				break;
			case 104:
				strFieldName = "StandbyString1";
				break;
			case 105:
				strFieldName = "StandbyString2";
				break;
			case 106:
				strFieldName = "StandbyString3";
				break;
			case 107:
				strFieldName = "StandbyDouble1";
				break;
			case 108:
				strFieldName = "StandbyDouble2";
				break;
			case 109:
				strFieldName = "StandbyDouble3";
				break;
			case 110:
				strFieldName = "StandbyDate1";
				break;
			case 111:
				strFieldName = "StandbyDate2";
				break;
			case 112:
				strFieldName = "ManageCom";
				break;
			case 113:
				strFieldName = "MakeDate";
				break;
			case 114:
				strFieldName = "MakeTime";
				break;
			case 115:
				strFieldName = "PreChRiskAmnt";
				break;
			case 116:
				strFieldName = "ChRiskAmnt";
				break;
			case 117:
				strFieldName = "PreChangeRate";
				break;
			case 118:
				strFieldName = "MainPolNo";
				break;
			case 119:
				strFieldName = "RIPreceptType";
				break;
			case 120:
				strFieldName = "BFFlag";
				break;
			case 121:
				strFieldName = "EdorPrem";
				break;
			case 122:
				strFieldName = "ChEdorPrem";
				break;
			case 123:
				strFieldName = "ChPrem";
				break;
			case 124:
				strFieldName = "Bonus1";
				break;
			case 125:
				strFieldName = "Bonus2";
				break;
			case 126:
				strFieldName = "Bonus3";
				break;
			case 127:
				strFieldName = "ExtRate1";
				break;
			case 128:
				strFieldName = "ExtRate2";
				break;
			case 129:
				strFieldName = "ExtRate3";
				break;
			case 130:
				strFieldName = "ExtPrem1";
				break;
			case 131:
				strFieldName = "ExtPrem2";
				break;
			case 132:
				strFieldName = "ExtPrem3";
				break;
			case 133:
				strFieldName = "StandbyString4";
				break;
			case 134:
				strFieldName = "StandbyString5";
				break;
			case 135:
				strFieldName = "StandbyString6";
				break;
			case 136:
				strFieldName = "StandbyString7";
				break;
			case 137:
				strFieldName = "StandbyString8";
				break;
			case 138:
				strFieldName = "StandbyString9";
				break;
			case 139:
				strFieldName = "StandbyDouble4";
				break;
			case 140:
				strFieldName = "StandbyDouble5";
				break;
			case 141:
				strFieldName = "StandbyDouble6";
				break;
			case 142:
				strFieldName = "StandbyDouble7";
				break;
			case 143:
				strFieldName = "StandbyDouble8";
				break;
			case 144:
				strFieldName = "StandbyDouble9";
				break;
			case 145:
				strFieldName = "StandbyDate3";
				break;
			case 146:
				strFieldName = "StandbyDate4";
				break;
			case 147:
				strFieldName = "StandbyDate5";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: strFieldName String
       * @return: int
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RecordType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnionKey") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuredAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CurrentAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthTime1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthTime2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SuppRiskScore") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdditionalRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExtPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BegAccountValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndUnit") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndAccountValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NonCashFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reserve") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("APE") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CSV") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Retention") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Suminsured") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Faceamount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcciYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeakLine") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChangeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccCurrency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PreStandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PrePrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PreRiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PreAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsreFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyDouble1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDate1") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandbyDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreChRiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChRiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PreChangeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BFFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChEdorPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Bonus1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Bonus2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Bonus3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtRate1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtRate2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtRate3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtPrem1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtPrem2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExtPrem3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyString4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyDouble4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDouble9") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyDate3") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandbyDate4") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandbyDate5") ) {
			return Schema.TYPE_DATE;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
       * @param: nFieldIndex int
       * @return: int
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_INT;
				break;
			case 30:
				nFieldType = Schema.TYPE_INT;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_INT;
				break;
			case 44:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 45:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 53:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 54:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 57:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 58:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 61:
				nFieldType = Schema.TYPE_INT;
				break;
			case 62:
				nFieldType = Schema.TYPE_INT;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_INT;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_INT;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_INT;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_INT;
				break;
			case 71:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 75:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 76:
				nFieldType = Schema.TYPE_INT;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_INT;
				break;
			case 79:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 80:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 81:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 88:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 89:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 90:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 91:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 95:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 96:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 97:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 98:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 99:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 106:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 107:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 108:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 109:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 110:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 111:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 112:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 113:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 114:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 115:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 116:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 117:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 118:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 119:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 120:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 121:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 122:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 123:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 124:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 125:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 126:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 127:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 128:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 129:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 130:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 131:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 132:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 133:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 134:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 135:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 136:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 137:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 138:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 139:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 140:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 141:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 142:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 143:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 144:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 145:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 146:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 147:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}

