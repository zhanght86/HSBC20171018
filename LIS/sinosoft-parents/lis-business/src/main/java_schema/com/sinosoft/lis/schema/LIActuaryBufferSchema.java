/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LIActuaryBufferDB;

/*
 * <p>ClassName: LIActuaryBufferSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIActuaryBufferSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIActuaryBufferSchema.class);

	// @Field
	/** 合同号码 */
	private String ContNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 险种代码 */
	private String RiskCode;
	/** 主、附险标记 */
	private String MainFlag;
	/** 业务类型 */
	private String ContType;
	/** 人名序号 */
	private String PeopleNo;
	/** 生成日期 */
	private Date MakeDate;
	/** 评估日期 */
	private Date CalDate;
	/** 预定利率 */
	private double DestRate;
	/** 预定费用率 */
	private double ForeFeeRate;
	/** 保单状态 */
	private String PolState;
	/** 利差返还 */
	private String InterestDifFlag;
	/** 借款 */
	private String LoanFlag;
	/** 借款本息 */
	private double LoanInterest;
	/** 豁免 */
	private String FreeFlag;
	/** 自垫 */
	private String AutoPayFlag;
	/** 自垫本息 */
	private double AutoPayInterest;
	/** 临终关怀 */
	private String DeathSolicitude;
	/** 分入分出 */
	private String InOutFlag;
	/** 分入或分出保额 */
	private int InOutAmnt;
	/** 保障类别 */
	private String ContPlanCode;
	/** 分公司代码 */
	private String SubMangeCom;
	/** 中心支公司代码 */
	private String CenterManageCom;
	/** 支公司代码 */
	private String BranchManageCom;
	/** 承保年度 */
	private int PolYearCount;
	/** 承保月数 */
	private int PolMonthCount;
	/** 承保日数 */
	private int PolDayCount;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 出单日期 */
	private Date SignDate;
	/** 缴费对应日 */
	private Date PayToDate;
	/** 缴费方式 */
	private int PayIntv;
	/** 缴费期限 */
	private int PayYears;
	/** 已缴费次数 */
	private int PayCount;
	/** 本次是否已交费 */
	private String IfThisTimePay;
	/** 保费 */
	private double Prem;
	/** 份数 */
	private double Mult;
	/** 有效保额 */
	private int CvalidAmnt;
	/** 保单保额 */
	private double Amnt;
	/** 累计红利保额 */
	private double TotalBonus;
	/** 健康加费 */
	private double HealthPrem;
	/** 职业加费 */
	private double OccPrem;
	/** 定价保费 */
	private double RatedPrem;
	/** 保险起期 */
	private Date PolStrDate;
	/** 保险止期 */
	private Date PolEndDate;
	/** 保险期限 */
	private int PoLYears;
	/** 开始领取年龄 */
	private int GetStartAge;
	/** 领取方式 */
	private String GetIntv;
	/** 领取标准 */
	private double GetCriterion;
	/** 领取次数 */
	private int GetTimes;
	/** 领取年度 */
	private int GetYearsNum;
	/** 第1被保人姓名 */
	private String FirstName;
	/** 第1被保人年龄 */
	private int FirstAge;
	/** 第1被保人生日 */
	private Date FirstBirthday;
	/** 第1被保人性别 */
	private String FirstSex;
	/** 第1被保人身份证 */
	private String FirstIdNo;
	/** 第1被保人职业类别 */
	private String FirstOccupationType;
	/** 第1被保人客户号 */
	private String FirstInsuredNo;
	/** 第2被保人姓名 */
	private String SecondName;
	/** 第2被保人年龄 */
	private int SecondAge;
	/** 第2被保人生日 */
	private Date SecondBirthday;
	/** 第2被保人性别 */
	private String SecondSex;
	/** 第2被保人身份证 */
	private String SecondIdNo;
	/** 第2被保人客户号 */
	private String SecondInsuredNo;
	/** 第2被保人职业类别 */
	private String SecondOccupationType;
	/** 现金价值 */
	private double CashValue;
	/** 出险人序号 */
	private int CustomerSerNum;
	/** 错误标记 */
	private int ErrorFlag;
	/** 险种号码 */
	private String PolNo;
	/** 主险代码 */
	private String MainPolNo;
	/** 管理机构 */
	private String ManageCom;
	/** 终交年龄年期标志 */
	private String PayEndYearFlag;
	/** 保险年期 */
	private int InsuYear;
	/** 保险年期标志 */
	private String InsuYearFlag;
	/** 开始领取日期 */
	private Date GetStartDate;
	/** 领取年期 */
	private int GetYear;
	/** 领取年期标志 */
	private String GetYearFlag;
	/** 客户内部号码 */
	private String SequenceNo;

	public static final int FIELDNUM = 77;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIActuaryBufferSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ContNo";
		pk[1] = "GrpContNo";
		pk[2] = "RiskCode";
		pk[3] = "PeopleNo";

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
		LIActuaryBufferSchema cloned = (LIActuaryBufferSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getMainFlag()
	{
		return MainFlag;
	}
	public void setMainFlag(String aMainFlag)
	{
		MainFlag = aMainFlag;
	}
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		ContType = aContType;
	}
	public String getPeopleNo()
	{
		return PeopleNo;
	}
	public void setPeopleNo(String aPeopleNo)
	{
		PeopleNo = aPeopleNo;
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

	public String getCalDate()
	{
		if( CalDate != null )
			return fDate.getString(CalDate);
		else
			return null;
	}
	public void setCalDate(Date aCalDate)
	{
		CalDate = aCalDate;
	}
	public void setCalDate(String aCalDate)
	{
		if (aCalDate != null && !aCalDate.equals("") )
		{
			CalDate = fDate.getDate( aCalDate );
		}
		else
			CalDate = null;
	}

	public double getDestRate()
	{
		return DestRate;
	}
	public void setDestRate(double aDestRate)
	{
		DestRate = aDestRate;
	}
	public void setDestRate(String aDestRate)
	{
		if (aDestRate != null && !aDestRate.equals(""))
		{
			Double tDouble = new Double(aDestRate);
			double d = tDouble.doubleValue();
			DestRate = d;
		}
	}

	public double getForeFeeRate()
	{
		return ForeFeeRate;
	}
	public void setForeFeeRate(double aForeFeeRate)
	{
		ForeFeeRate = aForeFeeRate;
	}
	public void setForeFeeRate(String aForeFeeRate)
	{
		if (aForeFeeRate != null && !aForeFeeRate.equals(""))
		{
			Double tDouble = new Double(aForeFeeRate);
			double d = tDouble.doubleValue();
			ForeFeeRate = d;
		}
	}

	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
	}
	public String getInterestDifFlag()
	{
		return InterestDifFlag;
	}
	public void setInterestDifFlag(String aInterestDifFlag)
	{
		InterestDifFlag = aInterestDifFlag;
	}
	public String getLoanFlag()
	{
		return LoanFlag;
	}
	public void setLoanFlag(String aLoanFlag)
	{
		LoanFlag = aLoanFlag;
	}
	public double getLoanInterest()
	{
		return LoanInterest;
	}
	public void setLoanInterest(double aLoanInterest)
	{
		LoanInterest = aLoanInterest;
	}
	public void setLoanInterest(String aLoanInterest)
	{
		if (aLoanInterest != null && !aLoanInterest.equals(""))
		{
			Double tDouble = new Double(aLoanInterest);
			double d = tDouble.doubleValue();
			LoanInterest = d;
		}
	}

	public String getFreeFlag()
	{
		return FreeFlag;
	}
	public void setFreeFlag(String aFreeFlag)
	{
		FreeFlag = aFreeFlag;
	}
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		AutoPayFlag = aAutoPayFlag;
	}
	public double getAutoPayInterest()
	{
		return AutoPayInterest;
	}
	public void setAutoPayInterest(double aAutoPayInterest)
	{
		AutoPayInterest = aAutoPayInterest;
	}
	public void setAutoPayInterest(String aAutoPayInterest)
	{
		if (aAutoPayInterest != null && !aAutoPayInterest.equals(""))
		{
			Double tDouble = new Double(aAutoPayInterest);
			double d = tDouble.doubleValue();
			AutoPayInterest = d;
		}
	}

	public String getDeathSolicitude()
	{
		return DeathSolicitude;
	}
	public void setDeathSolicitude(String aDeathSolicitude)
	{
		DeathSolicitude = aDeathSolicitude;
	}
	public String getInOutFlag()
	{
		return InOutFlag;
	}
	public void setInOutFlag(String aInOutFlag)
	{
		InOutFlag = aInOutFlag;
	}
	public int getInOutAmnt()
	{
		return InOutAmnt;
	}
	public void setInOutAmnt(int aInOutAmnt)
	{
		InOutAmnt = aInOutAmnt;
	}
	public void setInOutAmnt(String aInOutAmnt)
	{
		if (aInOutAmnt != null && !aInOutAmnt.equals(""))
		{
			Integer tInteger = new Integer(aInOutAmnt);
			int i = tInteger.intValue();
			InOutAmnt = i;
		}
	}

	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getSubMangeCom()
	{
		return SubMangeCom;
	}
	public void setSubMangeCom(String aSubMangeCom)
	{
		SubMangeCom = aSubMangeCom;
	}
	public String getCenterManageCom()
	{
		return CenterManageCom;
	}
	public void setCenterManageCom(String aCenterManageCom)
	{
		CenterManageCom = aCenterManageCom;
	}
	public String getBranchManageCom()
	{
		return BranchManageCom;
	}
	public void setBranchManageCom(String aBranchManageCom)
	{
		BranchManageCom = aBranchManageCom;
	}
	public int getPolYearCount()
	{
		return PolYearCount;
	}
	public void setPolYearCount(int aPolYearCount)
	{
		PolYearCount = aPolYearCount;
	}
	public void setPolYearCount(String aPolYearCount)
	{
		if (aPolYearCount != null && !aPolYearCount.equals(""))
		{
			Integer tInteger = new Integer(aPolYearCount);
			int i = tInteger.intValue();
			PolYearCount = i;
		}
	}

	public int getPolMonthCount()
	{
		return PolMonthCount;
	}
	public void setPolMonthCount(int aPolMonthCount)
	{
		PolMonthCount = aPolMonthCount;
	}
	public void setPolMonthCount(String aPolMonthCount)
	{
		if (aPolMonthCount != null && !aPolMonthCount.equals(""))
		{
			Integer tInteger = new Integer(aPolMonthCount);
			int i = tInteger.intValue();
			PolMonthCount = i;
		}
	}

	public int getPolDayCount()
	{
		return PolDayCount;
	}
	public void setPolDayCount(int aPolDayCount)
	{
		PolDayCount = aPolDayCount;
	}
	public void setPolDayCount(String aPolDayCount)
	{
		if (aPolDayCount != null && !aPolDayCount.equals(""))
		{
			Integer tInteger = new Integer(aPolDayCount);
			int i = tInteger.intValue();
			PolDayCount = i;
		}
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

	public String getSignDate()
	{
		if( SignDate != null )
			return fDate.getString(SignDate);
		else
			return null;
	}
	public void setSignDate(Date aSignDate)
	{
		SignDate = aSignDate;
	}
	public void setSignDate(String aSignDate)
	{
		if (aSignDate != null && !aSignDate.equals("") )
		{
			SignDate = fDate.getDate( aSignDate );
		}
		else
			SignDate = null;
	}

	public String getPayToDate()
	{
		if( PayToDate != null )
			return fDate.getString(PayToDate);
		else
			return null;
	}
	public void setPayToDate(Date aPayToDate)
	{
		PayToDate = aPayToDate;
	}
	public void setPayToDate(String aPayToDate)
	{
		if (aPayToDate != null && !aPayToDate.equals("") )
		{
			PayToDate = fDate.getDate( aPayToDate );
		}
		else
			PayToDate = null;
	}

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

	public String getIfThisTimePay()
	{
		return IfThisTimePay;
	}
	public void setIfThisTimePay(String aIfThisTimePay)
	{
		IfThisTimePay = aIfThisTimePay;
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

	public int getCvalidAmnt()
	{
		return CvalidAmnt;
	}
	public void setCvalidAmnt(int aCvalidAmnt)
	{
		CvalidAmnt = aCvalidAmnt;
	}
	public void setCvalidAmnt(String aCvalidAmnt)
	{
		if (aCvalidAmnt != null && !aCvalidAmnt.equals(""))
		{
			Integer tInteger = new Integer(aCvalidAmnt);
			int i = tInteger.intValue();
			CvalidAmnt = i;
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

	public double getTotalBonus()
	{
		return TotalBonus;
	}
	public void setTotalBonus(double aTotalBonus)
	{
		TotalBonus = aTotalBonus;
	}
	public void setTotalBonus(String aTotalBonus)
	{
		if (aTotalBonus != null && !aTotalBonus.equals(""))
		{
			Double tDouble = new Double(aTotalBonus);
			double d = tDouble.doubleValue();
			TotalBonus = d;
		}
	}

	public double getHealthPrem()
	{
		return HealthPrem;
	}
	public void setHealthPrem(double aHealthPrem)
	{
		HealthPrem = aHealthPrem;
	}
	public void setHealthPrem(String aHealthPrem)
	{
		if (aHealthPrem != null && !aHealthPrem.equals(""))
		{
			Double tDouble = new Double(aHealthPrem);
			double d = tDouble.doubleValue();
			HealthPrem = d;
		}
	}

	public double getOccPrem()
	{
		return OccPrem;
	}
	public void setOccPrem(double aOccPrem)
	{
		OccPrem = aOccPrem;
	}
	public void setOccPrem(String aOccPrem)
	{
		if (aOccPrem != null && !aOccPrem.equals(""))
		{
			Double tDouble = new Double(aOccPrem);
			double d = tDouble.doubleValue();
			OccPrem = d;
		}
	}

	public double getRatedPrem()
	{
		return RatedPrem;
	}
	public void setRatedPrem(double aRatedPrem)
	{
		RatedPrem = aRatedPrem;
	}
	public void setRatedPrem(String aRatedPrem)
	{
		if (aRatedPrem != null && !aRatedPrem.equals(""))
		{
			Double tDouble = new Double(aRatedPrem);
			double d = tDouble.doubleValue();
			RatedPrem = d;
		}
	}

	public String getPolStrDate()
	{
		if( PolStrDate != null )
			return fDate.getString(PolStrDate);
		else
			return null;
	}
	public void setPolStrDate(Date aPolStrDate)
	{
		PolStrDate = aPolStrDate;
	}
	public void setPolStrDate(String aPolStrDate)
	{
		if (aPolStrDate != null && !aPolStrDate.equals("") )
		{
			PolStrDate = fDate.getDate( aPolStrDate );
		}
		else
			PolStrDate = null;
	}

	public String getPolEndDate()
	{
		if( PolEndDate != null )
			return fDate.getString(PolEndDate);
		else
			return null;
	}
	public void setPolEndDate(Date aPolEndDate)
	{
		PolEndDate = aPolEndDate;
	}
	public void setPolEndDate(String aPolEndDate)
	{
		if (aPolEndDate != null && !aPolEndDate.equals("") )
		{
			PolEndDate = fDate.getDate( aPolEndDate );
		}
		else
			PolEndDate = null;
	}

	public int getPoLYears()
	{
		return PoLYears;
	}
	public void setPoLYears(int aPoLYears)
	{
		PoLYears = aPoLYears;
	}
	public void setPoLYears(String aPoLYears)
	{
		if (aPoLYears != null && !aPoLYears.equals(""))
		{
			Integer tInteger = new Integer(aPoLYears);
			int i = tInteger.intValue();
			PoLYears = i;
		}
	}

	public int getGetStartAge()
	{
		return GetStartAge;
	}
	public void setGetStartAge(int aGetStartAge)
	{
		GetStartAge = aGetStartAge;
	}
	public void setGetStartAge(String aGetStartAge)
	{
		if (aGetStartAge != null && !aGetStartAge.equals(""))
		{
			Integer tInteger = new Integer(aGetStartAge);
			int i = tInteger.intValue();
			GetStartAge = i;
		}
	}

	/**
	* 在LCGet表，通过PolNo查出。
	*/
	public String getGetIntv()
	{
		return GetIntv;
	}
	public void setGetIntv(String aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	public double getGetCriterion()
	{
		return GetCriterion;
	}
	public void setGetCriterion(double aGetCriterion)
	{
		GetCriterion = aGetCriterion;
	}
	public void setGetCriterion(String aGetCriterion)
	{
		if (aGetCriterion != null && !aGetCriterion.equals(""))
		{
			Double tDouble = new Double(aGetCriterion);
			double d = tDouble.doubleValue();
			GetCriterion = d;
		}
	}

	public int getGetTimes()
	{
		return GetTimes;
	}
	public void setGetTimes(int aGetTimes)
	{
		GetTimes = aGetTimes;
	}
	public void setGetTimes(String aGetTimes)
	{
		if (aGetTimes != null && !aGetTimes.equals(""))
		{
			Integer tInteger = new Integer(aGetTimes);
			int i = tInteger.intValue();
			GetTimes = i;
		}
	}

	public int getGetYearsNum()
	{
		return GetYearsNum;
	}
	public void setGetYearsNum(int aGetYearsNum)
	{
		GetYearsNum = aGetYearsNum;
	}
	public void setGetYearsNum(String aGetYearsNum)
	{
		if (aGetYearsNum != null && !aGetYearsNum.equals(""))
		{
			Integer tInteger = new Integer(aGetYearsNum);
			int i = tInteger.intValue();
			GetYearsNum = i;
		}
	}

	public String getFirstName()
	{
		return FirstName;
	}
	public void setFirstName(String aFirstName)
	{
		FirstName = aFirstName;
	}
	public int getFirstAge()
	{
		return FirstAge;
	}
	public void setFirstAge(int aFirstAge)
	{
		FirstAge = aFirstAge;
	}
	public void setFirstAge(String aFirstAge)
	{
		if (aFirstAge != null && !aFirstAge.equals(""))
		{
			Integer tInteger = new Integer(aFirstAge);
			int i = tInteger.intValue();
			FirstAge = i;
		}
	}

	public String getFirstBirthday()
	{
		if( FirstBirthday != null )
			return fDate.getString(FirstBirthday);
		else
			return null;
	}
	public void setFirstBirthday(Date aFirstBirthday)
	{
		FirstBirthday = aFirstBirthday;
	}
	public void setFirstBirthday(String aFirstBirthday)
	{
		if (aFirstBirthday != null && !aFirstBirthday.equals("") )
		{
			FirstBirthday = fDate.getDate( aFirstBirthday );
		}
		else
			FirstBirthday = null;
	}

	public String getFirstSex()
	{
		return FirstSex;
	}
	public void setFirstSex(String aFirstSex)
	{
		FirstSex = aFirstSex;
	}
	public String getFirstIdNo()
	{
		return FirstIdNo;
	}
	public void setFirstIdNo(String aFirstIdNo)
	{
		FirstIdNo = aFirstIdNo;
	}
	public String getFirstOccupationType()
	{
		return FirstOccupationType;
	}
	public void setFirstOccupationType(String aFirstOccupationType)
	{
		FirstOccupationType = aFirstOccupationType;
	}
	public String getFirstInsuredNo()
	{
		return FirstInsuredNo;
	}
	public void setFirstInsuredNo(String aFirstInsuredNo)
	{
		FirstInsuredNo = aFirstInsuredNo;
	}
	public String getSecondName()
	{
		return SecondName;
	}
	public void setSecondName(String aSecondName)
	{
		SecondName = aSecondName;
	}
	public int getSecondAge()
	{
		return SecondAge;
	}
	public void setSecondAge(int aSecondAge)
	{
		SecondAge = aSecondAge;
	}
	public void setSecondAge(String aSecondAge)
	{
		if (aSecondAge != null && !aSecondAge.equals(""))
		{
			Integer tInteger = new Integer(aSecondAge);
			int i = tInteger.intValue();
			SecondAge = i;
		}
	}

	public String getSecondBirthday()
	{
		if( SecondBirthday != null )
			return fDate.getString(SecondBirthday);
		else
			return null;
	}
	public void setSecondBirthday(Date aSecondBirthday)
	{
		SecondBirthday = aSecondBirthday;
	}
	public void setSecondBirthday(String aSecondBirthday)
	{
		if (aSecondBirthday != null && !aSecondBirthday.equals("") )
		{
			SecondBirthday = fDate.getDate( aSecondBirthday );
		}
		else
			SecondBirthday = null;
	}

	public String getSecondSex()
	{
		return SecondSex;
	}
	public void setSecondSex(String aSecondSex)
	{
		SecondSex = aSecondSex;
	}
	public String getSecondIdNo()
	{
		return SecondIdNo;
	}
	public void setSecondIdNo(String aSecondIdNo)
	{
		SecondIdNo = aSecondIdNo;
	}
	public String getSecondInsuredNo()
	{
		return SecondInsuredNo;
	}
	public void setSecondInsuredNo(String aSecondInsuredNo)
	{
		SecondInsuredNo = aSecondInsuredNo;
	}
	public String getSecondOccupationType()
	{
		return SecondOccupationType;
	}
	public void setSecondOccupationType(String aSecondOccupationType)
	{
		SecondOccupationType = aSecondOccupationType;
	}
	public double getCashValue()
	{
		return CashValue;
	}
	public void setCashValue(double aCashValue)
	{
		CashValue = aCashValue;
	}
	public void setCashValue(String aCashValue)
	{
		if (aCashValue != null && !aCashValue.equals(""))
		{
			Double tDouble = new Double(aCashValue);
			double d = tDouble.doubleValue();
			CashValue = d;
		}
	}

	public int getCustomerSerNum()
	{
		return CustomerSerNum;
	}
	public void setCustomerSerNum(int aCustomerSerNum)
	{
		CustomerSerNum = aCustomerSerNum;
	}
	public void setCustomerSerNum(String aCustomerSerNum)
	{
		if (aCustomerSerNum != null && !aCustomerSerNum.equals(""))
		{
			Integer tInteger = new Integer(aCustomerSerNum);
			int i = tInteger.intValue();
			CustomerSerNum = i;
		}
	}

	public int getErrorFlag()
	{
		return ErrorFlag;
	}
	public void setErrorFlag(int aErrorFlag)
	{
		ErrorFlag = aErrorFlag;
	}
	public void setErrorFlag(String aErrorFlag)
	{
		if (aErrorFlag != null && !aErrorFlag.equals(""))
		{
			Integer tInteger = new Integer(aErrorFlag);
			int i = tInteger.intValue();
			ErrorFlag = i;
		}
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getMainPolNo()
	{
		return MainPolNo;
	}
	public void setMainPolNo(String aMainPolNo)
	{
		MainPolNo = aMainPolNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		PayEndYearFlag = aPayEndYearFlag;
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
	* Y －－ 年<p>
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

	public String getGetYearFlag()
	{
		return GetYearFlag;
	}
	public void setGetYearFlag(String aGetYearFlag)
	{
		GetYearFlag = aGetYearFlag;
	}
	/**
	* 此号码标志该被保人是第几被保人。
	*/
	public String getSequenceNo()
	{
		return SequenceNo;
	}
	public void setSequenceNo(String aSequenceNo)
	{
		SequenceNo = aSequenceNo;
	}

	/**
	* 使用另外一个 LIActuaryBufferSchema 对象给 Schema 赋值
	* @param: aLIActuaryBufferSchema LIActuaryBufferSchema
	**/
	public void setSchema(LIActuaryBufferSchema aLIActuaryBufferSchema)
	{
		this.ContNo = aLIActuaryBufferSchema.getContNo();
		this.GrpContNo = aLIActuaryBufferSchema.getGrpContNo();
		this.RiskCode = aLIActuaryBufferSchema.getRiskCode();
		this.MainFlag = aLIActuaryBufferSchema.getMainFlag();
		this.ContType = aLIActuaryBufferSchema.getContType();
		this.PeopleNo = aLIActuaryBufferSchema.getPeopleNo();
		this.MakeDate = fDate.getDate( aLIActuaryBufferSchema.getMakeDate());
		this.CalDate = fDate.getDate( aLIActuaryBufferSchema.getCalDate());
		this.DestRate = aLIActuaryBufferSchema.getDestRate();
		this.ForeFeeRate = aLIActuaryBufferSchema.getForeFeeRate();
		this.PolState = aLIActuaryBufferSchema.getPolState();
		this.InterestDifFlag = aLIActuaryBufferSchema.getInterestDifFlag();
		this.LoanFlag = aLIActuaryBufferSchema.getLoanFlag();
		this.LoanInterest = aLIActuaryBufferSchema.getLoanInterest();
		this.FreeFlag = aLIActuaryBufferSchema.getFreeFlag();
		this.AutoPayFlag = aLIActuaryBufferSchema.getAutoPayFlag();
		this.AutoPayInterest = aLIActuaryBufferSchema.getAutoPayInterest();
		this.DeathSolicitude = aLIActuaryBufferSchema.getDeathSolicitude();
		this.InOutFlag = aLIActuaryBufferSchema.getInOutFlag();
		this.InOutAmnt = aLIActuaryBufferSchema.getInOutAmnt();
		this.ContPlanCode = aLIActuaryBufferSchema.getContPlanCode();
		this.SubMangeCom = aLIActuaryBufferSchema.getSubMangeCom();
		this.CenterManageCom = aLIActuaryBufferSchema.getCenterManageCom();
		this.BranchManageCom = aLIActuaryBufferSchema.getBranchManageCom();
		this.PolYearCount = aLIActuaryBufferSchema.getPolYearCount();
		this.PolMonthCount = aLIActuaryBufferSchema.getPolMonthCount();
		this.PolDayCount = aLIActuaryBufferSchema.getPolDayCount();
		this.CValiDate = fDate.getDate( aLIActuaryBufferSchema.getCValiDate());
		this.SignDate = fDate.getDate( aLIActuaryBufferSchema.getSignDate());
		this.PayToDate = fDate.getDate( aLIActuaryBufferSchema.getPayToDate());
		this.PayIntv = aLIActuaryBufferSchema.getPayIntv();
		this.PayYears = aLIActuaryBufferSchema.getPayYears();
		this.PayCount = aLIActuaryBufferSchema.getPayCount();
		this.IfThisTimePay = aLIActuaryBufferSchema.getIfThisTimePay();
		this.Prem = aLIActuaryBufferSchema.getPrem();
		this.Mult = aLIActuaryBufferSchema.getMult();
		this.CvalidAmnt = aLIActuaryBufferSchema.getCvalidAmnt();
		this.Amnt = aLIActuaryBufferSchema.getAmnt();
		this.TotalBonus = aLIActuaryBufferSchema.getTotalBonus();
		this.HealthPrem = aLIActuaryBufferSchema.getHealthPrem();
		this.OccPrem = aLIActuaryBufferSchema.getOccPrem();
		this.RatedPrem = aLIActuaryBufferSchema.getRatedPrem();
		this.PolStrDate = fDate.getDate( aLIActuaryBufferSchema.getPolStrDate());
		this.PolEndDate = fDate.getDate( aLIActuaryBufferSchema.getPolEndDate());
		this.PoLYears = aLIActuaryBufferSchema.getPoLYears();
		this.GetStartAge = aLIActuaryBufferSchema.getGetStartAge();
		this.GetIntv = aLIActuaryBufferSchema.getGetIntv();
		this.GetCriterion = aLIActuaryBufferSchema.getGetCriterion();
		this.GetTimes = aLIActuaryBufferSchema.getGetTimes();
		this.GetYearsNum = aLIActuaryBufferSchema.getGetYearsNum();
		this.FirstName = aLIActuaryBufferSchema.getFirstName();
		this.FirstAge = aLIActuaryBufferSchema.getFirstAge();
		this.FirstBirthday = fDate.getDate( aLIActuaryBufferSchema.getFirstBirthday());
		this.FirstSex = aLIActuaryBufferSchema.getFirstSex();
		this.FirstIdNo = aLIActuaryBufferSchema.getFirstIdNo();
		this.FirstOccupationType = aLIActuaryBufferSchema.getFirstOccupationType();
		this.FirstInsuredNo = aLIActuaryBufferSchema.getFirstInsuredNo();
		this.SecondName = aLIActuaryBufferSchema.getSecondName();
		this.SecondAge = aLIActuaryBufferSchema.getSecondAge();
		this.SecondBirthday = fDate.getDate( aLIActuaryBufferSchema.getSecondBirthday());
		this.SecondSex = aLIActuaryBufferSchema.getSecondSex();
		this.SecondIdNo = aLIActuaryBufferSchema.getSecondIdNo();
		this.SecondInsuredNo = aLIActuaryBufferSchema.getSecondInsuredNo();
		this.SecondOccupationType = aLIActuaryBufferSchema.getSecondOccupationType();
		this.CashValue = aLIActuaryBufferSchema.getCashValue();
		this.CustomerSerNum = aLIActuaryBufferSchema.getCustomerSerNum();
		this.ErrorFlag = aLIActuaryBufferSchema.getErrorFlag();
		this.PolNo = aLIActuaryBufferSchema.getPolNo();
		this.MainPolNo = aLIActuaryBufferSchema.getMainPolNo();
		this.ManageCom = aLIActuaryBufferSchema.getManageCom();
		this.PayEndYearFlag = aLIActuaryBufferSchema.getPayEndYearFlag();
		this.InsuYear = aLIActuaryBufferSchema.getInsuYear();
		this.InsuYearFlag = aLIActuaryBufferSchema.getInsuYearFlag();
		this.GetStartDate = fDate.getDate( aLIActuaryBufferSchema.getGetStartDate());
		this.GetYear = aLIActuaryBufferSchema.getGetYear();
		this.GetYearFlag = aLIActuaryBufferSchema.getGetYearFlag();
		this.SequenceNo = aLIActuaryBufferSchema.getSequenceNo();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("MainFlag") == null )
				this.MainFlag = null;
			else
				this.MainFlag = rs.getString("MainFlag").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("PeopleNo") == null )
				this.PeopleNo = null;
			else
				this.PeopleNo = rs.getString("PeopleNo").trim();

			this.MakeDate = rs.getDate("MakeDate");
			this.CalDate = rs.getDate("CalDate");
			this.DestRate = rs.getDouble("DestRate");
			this.ForeFeeRate = rs.getDouble("ForeFeeRate");
			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			if( rs.getString("InterestDifFlag") == null )
				this.InterestDifFlag = null;
			else
				this.InterestDifFlag = rs.getString("InterestDifFlag").trim();

			if( rs.getString("LoanFlag") == null )
				this.LoanFlag = null;
			else
				this.LoanFlag = rs.getString("LoanFlag").trim();

			this.LoanInterest = rs.getDouble("LoanInterest");
			if( rs.getString("FreeFlag") == null )
				this.FreeFlag = null;
			else
				this.FreeFlag = rs.getString("FreeFlag").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			this.AutoPayInterest = rs.getDouble("AutoPayInterest");
			if( rs.getString("DeathSolicitude") == null )
				this.DeathSolicitude = null;
			else
				this.DeathSolicitude = rs.getString("DeathSolicitude").trim();

			if( rs.getString("InOutFlag") == null )
				this.InOutFlag = null;
			else
				this.InOutFlag = rs.getString("InOutFlag").trim();

			this.InOutAmnt = rs.getInt("InOutAmnt");
			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("SubMangeCom") == null )
				this.SubMangeCom = null;
			else
				this.SubMangeCom = rs.getString("SubMangeCom").trim();

			if( rs.getString("CenterManageCom") == null )
				this.CenterManageCom = null;
			else
				this.CenterManageCom = rs.getString("CenterManageCom").trim();

			if( rs.getString("BranchManageCom") == null )
				this.BranchManageCom = null;
			else
				this.BranchManageCom = rs.getString("BranchManageCom").trim();

			this.PolYearCount = rs.getInt("PolYearCount");
			this.PolMonthCount = rs.getInt("PolMonthCount");
			this.PolDayCount = rs.getInt("PolDayCount");
			this.CValiDate = rs.getDate("CValiDate");
			this.SignDate = rs.getDate("SignDate");
			this.PayToDate = rs.getDate("PayToDate");
			this.PayIntv = rs.getInt("PayIntv");
			this.PayYears = rs.getInt("PayYears");
			this.PayCount = rs.getInt("PayCount");
			if( rs.getString("IfThisTimePay") == null )
				this.IfThisTimePay = null;
			else
				this.IfThisTimePay = rs.getString("IfThisTimePay").trim();

			this.Prem = rs.getDouble("Prem");
			this.Mult = rs.getDouble("Mult");
			this.CvalidAmnt = rs.getInt("CvalidAmnt");
			this.Amnt = rs.getDouble("Amnt");
			this.TotalBonus = rs.getDouble("TotalBonus");
			this.HealthPrem = rs.getDouble("HealthPrem");
			this.OccPrem = rs.getDouble("OccPrem");
			this.RatedPrem = rs.getDouble("RatedPrem");
			this.PolStrDate = rs.getDate("PolStrDate");
			this.PolEndDate = rs.getDate("PolEndDate");
			this.PoLYears = rs.getInt("PoLYears");
			this.GetStartAge = rs.getInt("GetStartAge");
			if( rs.getString("GetIntv") == null )
				this.GetIntv = null;
			else
				this.GetIntv = rs.getString("GetIntv").trim();

			this.GetCriterion = rs.getDouble("GetCriterion");
			this.GetTimes = rs.getInt("GetTimes");
			this.GetYearsNum = rs.getInt("GetYearsNum");
			if( rs.getString("FirstName") == null )
				this.FirstName = null;
			else
				this.FirstName = rs.getString("FirstName").trim();

			this.FirstAge = rs.getInt("FirstAge");
			this.FirstBirthday = rs.getDate("FirstBirthday");
			if( rs.getString("FirstSex") == null )
				this.FirstSex = null;
			else
				this.FirstSex = rs.getString("FirstSex").trim();

			if( rs.getString("FirstIdNo") == null )
				this.FirstIdNo = null;
			else
				this.FirstIdNo = rs.getString("FirstIdNo").trim();

			if( rs.getString("FirstOccupationType") == null )
				this.FirstOccupationType = null;
			else
				this.FirstOccupationType = rs.getString("FirstOccupationType").trim();

			if( rs.getString("FirstInsuredNo") == null )
				this.FirstInsuredNo = null;
			else
				this.FirstInsuredNo = rs.getString("FirstInsuredNo").trim();

			if( rs.getString("SecondName") == null )
				this.SecondName = null;
			else
				this.SecondName = rs.getString("SecondName").trim();

			this.SecondAge = rs.getInt("SecondAge");
			this.SecondBirthday = rs.getDate("SecondBirthday");
			if( rs.getString("SecondSex") == null )
				this.SecondSex = null;
			else
				this.SecondSex = rs.getString("SecondSex").trim();

			if( rs.getString("SecondIdNo") == null )
				this.SecondIdNo = null;
			else
				this.SecondIdNo = rs.getString("SecondIdNo").trim();

			if( rs.getString("SecondInsuredNo") == null )
				this.SecondInsuredNo = null;
			else
				this.SecondInsuredNo = rs.getString("SecondInsuredNo").trim();

			if( rs.getString("SecondOccupationType") == null )
				this.SecondOccupationType = null;
			else
				this.SecondOccupationType = rs.getString("SecondOccupationType").trim();

			this.CashValue = rs.getDouble("CashValue");
			this.CustomerSerNum = rs.getInt("CustomerSerNum");
			this.ErrorFlag = rs.getInt("ErrorFlag");
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.GetStartDate = rs.getDate("GetStartDate");
			this.GetYear = rs.getInt("GetYear");
			if( rs.getString("GetYearFlag") == null )
				this.GetYearFlag = null;
			else
				this.GetYearFlag = rs.getString("GetYearFlag").trim();

			if( rs.getString("SequenceNo") == null )
				this.SequenceNo = null;
			else
				this.SequenceNo = rs.getString("SequenceNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIActuaryBuffer表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIActuaryBufferSchema getSchema()
	{
		LIActuaryBufferSchema aLIActuaryBufferSchema = new LIActuaryBufferSchema();
		aLIActuaryBufferSchema.setSchema(this);
		return aLIActuaryBufferSchema;
	}

	public LIActuaryBufferDB getDB()
	{
		LIActuaryBufferDB aDBOper = new LIActuaryBufferDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIActuaryBuffer描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PeopleNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ForeFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestDifFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoanFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LoanInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FreeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AutoPayInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeathSolicitude)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InOutFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InOutAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubMangeCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CenterManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolYearCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolMonthCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolDayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IfThisTimePay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CvalidAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HealthPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OccPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RatedPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolStrDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PoLYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetStartAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetCriterion));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYearsNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FirstAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstIdNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstOccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecondName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SecondAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SecondBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecondSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecondIdNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecondInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SecondOccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CashValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CustomerSerNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ErrorFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SequenceNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIActuaryBuffer>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MainFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PeopleNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			CalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			DestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			ForeFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InterestDifFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			LoanFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			LoanInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			FreeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AutoPayInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			DeathSolicitude = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InOutFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			InOutAmnt= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			SubMangeCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			CenterManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BranchManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PolYearCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			PolMonthCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			PolDayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			PayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).intValue();
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).intValue();
			IfThisTimePay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			CvalidAmnt= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).intValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			TotalBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			HealthPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			OccPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			RatedPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).doubleValue();
			PolStrDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			PolEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			PoLYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).intValue();
			GetStartAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).intValue();
			GetIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			GetCriterion = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			GetTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).intValue();
			GetYearsNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).intValue();
			FirstName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			FirstAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			FirstBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53,SysConst.PACKAGESPILTER));
			FirstSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			FirstIdNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			FirstOccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			FirstInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			SecondName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			SecondAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).intValue();
			SecondBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60,SysConst.PACKAGESPILTER));
			SecondSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			SecondIdNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			SecondInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			SecondOccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			CashValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			CustomerSerNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,66,SysConst.PACKAGESPILTER))).intValue();
			ErrorFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).intValue();
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74,SysConst.PACKAGESPILTER));
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).intValue();
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			SequenceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("MainFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainFlag));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("PeopleNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeopleNo));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
		}
		if (FCode.equalsIgnoreCase("DestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestRate));
		}
		if (FCode.equalsIgnoreCase("ForeFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForeFeeRate));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("InterestDifFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestDifFlag));
		}
		if (FCode.equalsIgnoreCase("LoanFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoanFlag));
		}
		if (FCode.equalsIgnoreCase("LoanInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoanInterest));
		}
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreeFlag));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("AutoPayInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayInterest));
		}
		if (FCode.equalsIgnoreCase("DeathSolicitude"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeathSolicitude));
		}
		if (FCode.equalsIgnoreCase("InOutFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InOutFlag));
		}
		if (FCode.equalsIgnoreCase("InOutAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InOutAmnt));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("SubMangeCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubMangeCom));
		}
		if (FCode.equalsIgnoreCase("CenterManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CenterManageCom));
		}
		if (FCode.equalsIgnoreCase("BranchManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchManageCom));
		}
		if (FCode.equalsIgnoreCase("PolYearCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolYearCount));
		}
		if (FCode.equalsIgnoreCase("PolMonthCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMonthCount));
		}
		if (FCode.equalsIgnoreCase("PolDayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolDayCount));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("IfThisTimePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IfThisTimePay));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("CvalidAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CvalidAmnt));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("TotalBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalBonus));
		}
		if (FCode.equalsIgnoreCase("HealthPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthPrem));
		}
		if (FCode.equalsIgnoreCase("OccPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccPrem));
		}
		if (FCode.equalsIgnoreCase("RatedPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RatedPrem));
		}
		if (FCode.equalsIgnoreCase("PolStrDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolStrDate()));
		}
		if (FCode.equalsIgnoreCase("PolEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolEndDate()));
		}
		if (FCode.equalsIgnoreCase("PoLYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PoLYears));
		}
		if (FCode.equalsIgnoreCase("GetStartAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartAge));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("GetCriterion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetCriterion));
		}
		if (FCode.equalsIgnoreCase("GetTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetTimes));
		}
		if (FCode.equalsIgnoreCase("GetYearsNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearsNum));
		}
		if (FCode.equalsIgnoreCase("FirstName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstName));
		}
		if (FCode.equalsIgnoreCase("FirstAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstAge));
		}
		if (FCode.equalsIgnoreCase("FirstBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstBirthday()));
		}
		if (FCode.equalsIgnoreCase("FirstSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstSex));
		}
		if (FCode.equalsIgnoreCase("FirstIdNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstIdNo));
		}
		if (FCode.equalsIgnoreCase("FirstOccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstOccupationType));
		}
		if (FCode.equalsIgnoreCase("FirstInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstInsuredNo));
		}
		if (FCode.equalsIgnoreCase("SecondName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondName));
		}
		if (FCode.equalsIgnoreCase("SecondAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondAge));
		}
		if (FCode.equalsIgnoreCase("SecondBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSecondBirthday()));
		}
		if (FCode.equalsIgnoreCase("SecondSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondSex));
		}
		if (FCode.equalsIgnoreCase("SecondIdNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondIdNo));
		}
		if (FCode.equalsIgnoreCase("SecondInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondInsuredNo));
		}
		if (FCode.equalsIgnoreCase("SecondOccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SecondOccupationType));
		}
		if (FCode.equalsIgnoreCase("CashValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CashValue));
		}
		if (FCode.equalsIgnoreCase("CustomerSerNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerSerNum));
		}
		if (FCode.equalsIgnoreCase("ErrorFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorFlag));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equalsIgnoreCase("GetYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYearFlag));
		}
		if (FCode.equalsIgnoreCase("SequenceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SequenceNo));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MainFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PeopleNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
				break;
			case 8:
				strFieldValue = String.valueOf(DestRate);
				break;
			case 9:
				strFieldValue = String.valueOf(ForeFeeRate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InterestDifFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(LoanFlag);
				break;
			case 13:
				strFieldValue = String.valueOf(LoanInterest);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(FreeFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 16:
				strFieldValue = String.valueOf(AutoPayInterest);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(DeathSolicitude);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InOutFlag);
				break;
			case 19:
				strFieldValue = String.valueOf(InOutAmnt);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(SubMangeCom);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(CenterManageCom);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BranchManageCom);
				break;
			case 24:
				strFieldValue = String.valueOf(PolYearCount);
				break;
			case 25:
				strFieldValue = String.valueOf(PolMonthCount);
				break;
			case 26:
				strFieldValue = String.valueOf(PolDayCount);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
				break;
			case 30:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 31:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 32:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(IfThisTimePay);
				break;
			case 34:
				strFieldValue = String.valueOf(Prem);
				break;
			case 35:
				strFieldValue = String.valueOf(Mult);
				break;
			case 36:
				strFieldValue = String.valueOf(CvalidAmnt);
				break;
			case 37:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 38:
				strFieldValue = String.valueOf(TotalBonus);
				break;
			case 39:
				strFieldValue = String.valueOf(HealthPrem);
				break;
			case 40:
				strFieldValue = String.valueOf(OccPrem);
				break;
			case 41:
				strFieldValue = String.valueOf(RatedPrem);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolStrDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolEndDate()));
				break;
			case 44:
				strFieldValue = String.valueOf(PoLYears);
				break;
			case 45:
				strFieldValue = String.valueOf(GetStartAge);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(GetIntv);
				break;
			case 47:
				strFieldValue = String.valueOf(GetCriterion);
				break;
			case 48:
				strFieldValue = String.valueOf(GetTimes);
				break;
			case 49:
				strFieldValue = String.valueOf(GetYearsNum);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(FirstName);
				break;
			case 51:
				strFieldValue = String.valueOf(FirstAge);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstBirthday()));
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(FirstSex);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(FirstIdNo);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(FirstOccupationType);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(FirstInsuredNo);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(SecondName);
				break;
			case 58:
				strFieldValue = String.valueOf(SecondAge);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSecondBirthday()));
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(SecondSex);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(SecondIdNo);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(SecondInsuredNo);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(SecondOccupationType);
				break;
			case 64:
				strFieldValue = String.valueOf(CashValue);
				break;
			case 65:
				strFieldValue = String.valueOf(CustomerSerNum);
				break;
			case 66:
				strFieldValue = String.valueOf(ErrorFlag);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 71:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 74:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(SequenceNo);
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

		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("MainFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainFlag = FValue.trim();
			}
			else
				MainFlag = null;
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("PeopleNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PeopleNo = FValue.trim();
			}
			else
				PeopleNo = null;
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
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CalDate = fDate.getDate( FValue );
			}
			else
				CalDate = null;
		}
		if (FCode.equalsIgnoreCase("DestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ForeFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ForeFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
		}
		if (FCode.equalsIgnoreCase("InterestDifFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestDifFlag = FValue.trim();
			}
			else
				InterestDifFlag = null;
		}
		if (FCode.equalsIgnoreCase("LoanFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoanFlag = FValue.trim();
			}
			else
				LoanFlag = null;
		}
		if (FCode.equalsIgnoreCase("LoanInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LoanInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FreeFlag = FValue.trim();
			}
			else
				FreeFlag = null;
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayFlag = FValue.trim();
			}
			else
				AutoPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("AutoPayInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AutoPayInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("DeathSolicitude"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeathSolicitude = FValue.trim();
			}
			else
				DeathSolicitude = null;
		}
		if (FCode.equalsIgnoreCase("InOutFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InOutFlag = FValue.trim();
			}
			else
				InOutFlag = null;
		}
		if (FCode.equalsIgnoreCase("InOutAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InOutAmnt = i;
			}
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
		if (FCode.equalsIgnoreCase("SubMangeCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubMangeCom = FValue.trim();
			}
			else
				SubMangeCom = null;
		}
		if (FCode.equalsIgnoreCase("CenterManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CenterManageCom = FValue.trim();
			}
			else
				CenterManageCom = null;
		}
		if (FCode.equalsIgnoreCase("BranchManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchManageCom = FValue.trim();
			}
			else
				BranchManageCom = null;
		}
		if (FCode.equalsIgnoreCase("PolYearCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolYearCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PolMonthCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolMonthCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PolDayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolDayCount = i;
			}
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
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayToDate = fDate.getDate( FValue );
			}
			else
				PayToDate = null;
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
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("IfThisTimePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IfThisTimePay = FValue.trim();
			}
			else
				IfThisTimePay = null;
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
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		if (FCode.equalsIgnoreCase("CvalidAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CvalidAmnt = i;
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
		if (FCode.equalsIgnoreCase("TotalBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalBonus = d;
			}
		}
		if (FCode.equalsIgnoreCase("HealthPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				HealthPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("OccPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OccPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("RatedPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RatedPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PolStrDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PolStrDate = fDate.getDate( FValue );
			}
			else
				PolStrDate = null;
		}
		if (FCode.equalsIgnoreCase("PolEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PolEndDate = fDate.getDate( FValue );
			}
			else
				PolEndDate = null;
		}
		if (FCode.equalsIgnoreCase("PoLYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PoLYears = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetStartAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetStartAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetIntv = FValue.trim();
			}
			else
				GetIntv = null;
		}
		if (FCode.equalsIgnoreCase("GetCriterion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetCriterion = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetYearsNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetYearsNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("FirstName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstName = FValue.trim();
			}
			else
				FirstName = null;
		}
		if (FCode.equalsIgnoreCase("FirstAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FirstAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("FirstBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstBirthday = fDate.getDate( FValue );
			}
			else
				FirstBirthday = null;
		}
		if (FCode.equalsIgnoreCase("FirstSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstSex = FValue.trim();
			}
			else
				FirstSex = null;
		}
		if (FCode.equalsIgnoreCase("FirstIdNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstIdNo = FValue.trim();
			}
			else
				FirstIdNo = null;
		}
		if (FCode.equalsIgnoreCase("FirstOccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstOccupationType = FValue.trim();
			}
			else
				FirstOccupationType = null;
		}
		if (FCode.equalsIgnoreCase("FirstInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstInsuredNo = FValue.trim();
			}
			else
				FirstInsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("SecondName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecondName = FValue.trim();
			}
			else
				SecondName = null;
		}
		if (FCode.equalsIgnoreCase("SecondAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SecondAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("SecondBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SecondBirthday = fDate.getDate( FValue );
			}
			else
				SecondBirthday = null;
		}
		if (FCode.equalsIgnoreCase("SecondSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecondSex = FValue.trim();
			}
			else
				SecondSex = null;
		}
		if (FCode.equalsIgnoreCase("SecondIdNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecondIdNo = FValue.trim();
			}
			else
				SecondIdNo = null;
		}
		if (FCode.equalsIgnoreCase("SecondInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecondInsuredNo = FValue.trim();
			}
			else
				SecondInsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("SecondOccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SecondOccupationType = FValue.trim();
			}
			else
				SecondOccupationType = null;
		}
		if (FCode.equalsIgnoreCase("CashValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CashValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CustomerSerNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CustomerSerNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("ErrorFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ErrorFlag = i;
			}
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
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolNo = FValue.trim();
			}
			else
				MainPolNo = null;
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
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
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
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
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
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetYear = i;
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
		if (FCode.equalsIgnoreCase("SequenceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SequenceNo = FValue.trim();
			}
			else
				SequenceNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIActuaryBufferSchema other = (LIActuaryBufferSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& MainFlag.equals(other.getMainFlag())
			&& ContType.equals(other.getContType())
			&& PeopleNo.equals(other.getPeopleNo())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& fDate.getString(CalDate).equals(other.getCalDate())
			&& DestRate == other.getDestRate()
			&& ForeFeeRate == other.getForeFeeRate()
			&& PolState.equals(other.getPolState())
			&& InterestDifFlag.equals(other.getInterestDifFlag())
			&& LoanFlag.equals(other.getLoanFlag())
			&& LoanInterest == other.getLoanInterest()
			&& FreeFlag.equals(other.getFreeFlag())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& AutoPayInterest == other.getAutoPayInterest()
			&& DeathSolicitude.equals(other.getDeathSolicitude())
			&& InOutFlag.equals(other.getInOutFlag())
			&& InOutAmnt == other.getInOutAmnt()
			&& ContPlanCode.equals(other.getContPlanCode())
			&& SubMangeCom.equals(other.getSubMangeCom())
			&& CenterManageCom.equals(other.getCenterManageCom())
			&& BranchManageCom.equals(other.getBranchManageCom())
			&& PolYearCount == other.getPolYearCount()
			&& PolMonthCount == other.getPolMonthCount()
			&& PolDayCount == other.getPolDayCount()
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& fDate.getString(PayToDate).equals(other.getPayToDate())
			&& PayIntv == other.getPayIntv()
			&& PayYears == other.getPayYears()
			&& PayCount == other.getPayCount()
			&& IfThisTimePay.equals(other.getIfThisTimePay())
			&& Prem == other.getPrem()
			&& Mult == other.getMult()
			&& CvalidAmnt == other.getCvalidAmnt()
			&& Amnt == other.getAmnt()
			&& TotalBonus == other.getTotalBonus()
			&& HealthPrem == other.getHealthPrem()
			&& OccPrem == other.getOccPrem()
			&& RatedPrem == other.getRatedPrem()
			&& fDate.getString(PolStrDate).equals(other.getPolStrDate())
			&& fDate.getString(PolEndDate).equals(other.getPolEndDate())
			&& PoLYears == other.getPoLYears()
			&& GetStartAge == other.getGetStartAge()
			&& GetIntv.equals(other.getGetIntv())
			&& GetCriterion == other.getGetCriterion()
			&& GetTimes == other.getGetTimes()
			&& GetYearsNum == other.getGetYearsNum()
			&& FirstName.equals(other.getFirstName())
			&& FirstAge == other.getFirstAge()
			&& fDate.getString(FirstBirthday).equals(other.getFirstBirthday())
			&& FirstSex.equals(other.getFirstSex())
			&& FirstIdNo.equals(other.getFirstIdNo())
			&& FirstOccupationType.equals(other.getFirstOccupationType())
			&& FirstInsuredNo.equals(other.getFirstInsuredNo())
			&& SecondName.equals(other.getSecondName())
			&& SecondAge == other.getSecondAge()
			&& fDate.getString(SecondBirthday).equals(other.getSecondBirthday())
			&& SecondSex.equals(other.getSecondSex())
			&& SecondIdNo.equals(other.getSecondIdNo())
			&& SecondInsuredNo.equals(other.getSecondInsuredNo())
			&& SecondOccupationType.equals(other.getSecondOccupationType())
			&& CashValue == other.getCashValue()
			&& CustomerSerNum == other.getCustomerSerNum()
			&& ErrorFlag == other.getErrorFlag()
			&& PolNo.equals(other.getPolNo())
			&& MainPolNo.equals(other.getMainPolNo())
			&& ManageCom.equals(other.getManageCom())
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& InsuYear == other.getInsuYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& GetYear == other.getGetYear()
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& SequenceNo.equals(other.getSequenceNo());
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("MainFlag") ) {
			return 3;
		}
		if( strFieldName.equals("ContType") ) {
			return 4;
		}
		if( strFieldName.equals("PeopleNo") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("CalDate") ) {
			return 7;
		}
		if( strFieldName.equals("DestRate") ) {
			return 8;
		}
		if( strFieldName.equals("ForeFeeRate") ) {
			return 9;
		}
		if( strFieldName.equals("PolState") ) {
			return 10;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return 11;
		}
		if( strFieldName.equals("LoanFlag") ) {
			return 12;
		}
		if( strFieldName.equals("LoanInterest") ) {
			return 13;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return 14;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 15;
		}
		if( strFieldName.equals("AutoPayInterest") ) {
			return 16;
		}
		if( strFieldName.equals("DeathSolicitude") ) {
			return 17;
		}
		if( strFieldName.equals("InOutFlag") ) {
			return 18;
		}
		if( strFieldName.equals("InOutAmnt") ) {
			return 19;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 20;
		}
		if( strFieldName.equals("SubMangeCom") ) {
			return 21;
		}
		if( strFieldName.equals("CenterManageCom") ) {
			return 22;
		}
		if( strFieldName.equals("BranchManageCom") ) {
			return 23;
		}
		if( strFieldName.equals("PolYearCount") ) {
			return 24;
		}
		if( strFieldName.equals("PolMonthCount") ) {
			return 25;
		}
		if( strFieldName.equals("PolDayCount") ) {
			return 26;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 27;
		}
		if( strFieldName.equals("SignDate") ) {
			return 28;
		}
		if( strFieldName.equals("PayToDate") ) {
			return 29;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 30;
		}
		if( strFieldName.equals("PayYears") ) {
			return 31;
		}
		if( strFieldName.equals("PayCount") ) {
			return 32;
		}
		if( strFieldName.equals("IfThisTimePay") ) {
			return 33;
		}
		if( strFieldName.equals("Prem") ) {
			return 34;
		}
		if( strFieldName.equals("Mult") ) {
			return 35;
		}
		if( strFieldName.equals("CvalidAmnt") ) {
			return 36;
		}
		if( strFieldName.equals("Amnt") ) {
			return 37;
		}
		if( strFieldName.equals("TotalBonus") ) {
			return 38;
		}
		if( strFieldName.equals("HealthPrem") ) {
			return 39;
		}
		if( strFieldName.equals("OccPrem") ) {
			return 40;
		}
		if( strFieldName.equals("RatedPrem") ) {
			return 41;
		}
		if( strFieldName.equals("PolStrDate") ) {
			return 42;
		}
		if( strFieldName.equals("PolEndDate") ) {
			return 43;
		}
		if( strFieldName.equals("PoLYears") ) {
			return 44;
		}
		if( strFieldName.equals("GetStartAge") ) {
			return 45;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 46;
		}
		if( strFieldName.equals("GetCriterion") ) {
			return 47;
		}
		if( strFieldName.equals("GetTimes") ) {
			return 48;
		}
		if( strFieldName.equals("GetYearsNum") ) {
			return 49;
		}
		if( strFieldName.equals("FirstName") ) {
			return 50;
		}
		if( strFieldName.equals("FirstAge") ) {
			return 51;
		}
		if( strFieldName.equals("FirstBirthday") ) {
			return 52;
		}
		if( strFieldName.equals("FirstSex") ) {
			return 53;
		}
		if( strFieldName.equals("FirstIdNo") ) {
			return 54;
		}
		if( strFieldName.equals("FirstOccupationType") ) {
			return 55;
		}
		if( strFieldName.equals("FirstInsuredNo") ) {
			return 56;
		}
		if( strFieldName.equals("SecondName") ) {
			return 57;
		}
		if( strFieldName.equals("SecondAge") ) {
			return 58;
		}
		if( strFieldName.equals("SecondBirthday") ) {
			return 59;
		}
		if( strFieldName.equals("SecondSex") ) {
			return 60;
		}
		if( strFieldName.equals("SecondIdNo") ) {
			return 61;
		}
		if( strFieldName.equals("SecondInsuredNo") ) {
			return 62;
		}
		if( strFieldName.equals("SecondOccupationType") ) {
			return 63;
		}
		if( strFieldName.equals("CashValue") ) {
			return 64;
		}
		if( strFieldName.equals("CustomerSerNum") ) {
			return 65;
		}
		if( strFieldName.equals("ErrorFlag") ) {
			return 66;
		}
		if( strFieldName.equals("PolNo") ) {
			return 67;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 68;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 69;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 70;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 71;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 72;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 73;
		}
		if( strFieldName.equals("GetYear") ) {
			return 74;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 75;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return 76;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "MainFlag";
				break;
			case 4:
				strFieldName = "ContType";
				break;
			case 5:
				strFieldName = "PeopleNo";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "CalDate";
				break;
			case 8:
				strFieldName = "DestRate";
				break;
			case 9:
				strFieldName = "ForeFeeRate";
				break;
			case 10:
				strFieldName = "PolState";
				break;
			case 11:
				strFieldName = "InterestDifFlag";
				break;
			case 12:
				strFieldName = "LoanFlag";
				break;
			case 13:
				strFieldName = "LoanInterest";
				break;
			case 14:
				strFieldName = "FreeFlag";
				break;
			case 15:
				strFieldName = "AutoPayFlag";
				break;
			case 16:
				strFieldName = "AutoPayInterest";
				break;
			case 17:
				strFieldName = "DeathSolicitude";
				break;
			case 18:
				strFieldName = "InOutFlag";
				break;
			case 19:
				strFieldName = "InOutAmnt";
				break;
			case 20:
				strFieldName = "ContPlanCode";
				break;
			case 21:
				strFieldName = "SubMangeCom";
				break;
			case 22:
				strFieldName = "CenterManageCom";
				break;
			case 23:
				strFieldName = "BranchManageCom";
				break;
			case 24:
				strFieldName = "PolYearCount";
				break;
			case 25:
				strFieldName = "PolMonthCount";
				break;
			case 26:
				strFieldName = "PolDayCount";
				break;
			case 27:
				strFieldName = "CValiDate";
				break;
			case 28:
				strFieldName = "SignDate";
				break;
			case 29:
				strFieldName = "PayToDate";
				break;
			case 30:
				strFieldName = "PayIntv";
				break;
			case 31:
				strFieldName = "PayYears";
				break;
			case 32:
				strFieldName = "PayCount";
				break;
			case 33:
				strFieldName = "IfThisTimePay";
				break;
			case 34:
				strFieldName = "Prem";
				break;
			case 35:
				strFieldName = "Mult";
				break;
			case 36:
				strFieldName = "CvalidAmnt";
				break;
			case 37:
				strFieldName = "Amnt";
				break;
			case 38:
				strFieldName = "TotalBonus";
				break;
			case 39:
				strFieldName = "HealthPrem";
				break;
			case 40:
				strFieldName = "OccPrem";
				break;
			case 41:
				strFieldName = "RatedPrem";
				break;
			case 42:
				strFieldName = "PolStrDate";
				break;
			case 43:
				strFieldName = "PolEndDate";
				break;
			case 44:
				strFieldName = "PoLYears";
				break;
			case 45:
				strFieldName = "GetStartAge";
				break;
			case 46:
				strFieldName = "GetIntv";
				break;
			case 47:
				strFieldName = "GetCriterion";
				break;
			case 48:
				strFieldName = "GetTimes";
				break;
			case 49:
				strFieldName = "GetYearsNum";
				break;
			case 50:
				strFieldName = "FirstName";
				break;
			case 51:
				strFieldName = "FirstAge";
				break;
			case 52:
				strFieldName = "FirstBirthday";
				break;
			case 53:
				strFieldName = "FirstSex";
				break;
			case 54:
				strFieldName = "FirstIdNo";
				break;
			case 55:
				strFieldName = "FirstOccupationType";
				break;
			case 56:
				strFieldName = "FirstInsuredNo";
				break;
			case 57:
				strFieldName = "SecondName";
				break;
			case 58:
				strFieldName = "SecondAge";
				break;
			case 59:
				strFieldName = "SecondBirthday";
				break;
			case 60:
				strFieldName = "SecondSex";
				break;
			case 61:
				strFieldName = "SecondIdNo";
				break;
			case 62:
				strFieldName = "SecondInsuredNo";
				break;
			case 63:
				strFieldName = "SecondOccupationType";
				break;
			case 64:
				strFieldName = "CashValue";
				break;
			case 65:
				strFieldName = "CustomerSerNum";
				break;
			case 66:
				strFieldName = "ErrorFlag";
				break;
			case 67:
				strFieldName = "PolNo";
				break;
			case 68:
				strFieldName = "MainPolNo";
				break;
			case 69:
				strFieldName = "ManageCom";
				break;
			case 70:
				strFieldName = "PayEndYearFlag";
				break;
			case 71:
				strFieldName = "InsuYear";
				break;
			case 72:
				strFieldName = "InsuYearFlag";
				break;
			case 73:
				strFieldName = "GetStartDate";
				break;
			case 74:
				strFieldName = "GetYear";
				break;
			case 75:
				strFieldName = "GetYearFlag";
				break;
			case 76:
				strFieldName = "SequenceNo";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeopleNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ForeFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoanFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoanInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeathSolicitude") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InOutFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InOutAmnt") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubMangeCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CenterManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolYearCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolMonthCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolDayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IfThisTimePay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CvalidAmnt") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TotalBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HealthPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OccPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RatedPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolStrDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PolEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PoLYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetStartAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetCriterion") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetYearsNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FirstName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FirstBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstIdNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstOccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecondName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecondAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SecondBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SecondSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecondIdNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecondInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SecondOccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CashValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CustomerSerNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ErrorFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_INT;
				break;
			case 31:
				nFieldType = Schema.TYPE_INT;
				break;
			case 32:
				nFieldType = Schema.TYPE_INT;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_INT;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 44:
				nFieldType = Schema.TYPE_INT;
				break;
			case 45:
				nFieldType = Schema.TYPE_INT;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_INT;
				break;
			case 49:
				nFieldType = Schema.TYPE_INT;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_INT;
				break;
			case 52:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_INT;
				break;
			case 59:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 65:
				nFieldType = Schema.TYPE_INT;
				break;
			case 66:
				nFieldType = Schema.TYPE_INT;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_INT;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 74:
				nFieldType = Schema.TYPE_INT;
				break;
			case 75:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
