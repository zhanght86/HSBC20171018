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
import com.sinosoft.lis.db.LBDutyDB;

/*
 * <p>ClassName: LBDutySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LBDutySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBDutySchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** 合同号码 */
	private String ContNo;
	/** 份数 */
	private double Mult;
	/** 标准保费 */
	private double StandPrem;
	/** 实际保费 */
	private double Prem;
	/** 累计保费 */
	private double SumPrem;
	/** 基本保额 */
	private double Amnt;
	/** 风险保额 */
	private double RiskAmnt;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费年期 */
	private int PayYears;
	/** 保险年期 */
	private int Years;
	/** 浮动费率 */
	private double FloatRate;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 首期交费月数 */
	private int FirstMonth;
	/** 交至日期 */
	private Date PaytoDate;
	/** 终交日期 */
	private Date PayEndDate;
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
	/** 保险责任终止日期 */
	private Date EndDate;
	/** 意外责任终止日期 */
	private Date AcciEndDate;
	/** 免交标志 */
	private String FreeFlag;
	/** 免交比率 */
	private double FreeRate;
	/** 免交起期 */
	private Date FreeStartDate;
	/** 免交止期 */
	private Date FreeEndDate;
	/** 起领日期 */
	private Date GetStartDate;
	/** 起领日期计算类型 */
	private String GetStartType;
	/** 生存金领取方式 */
	private String LiveGetMode;
	/** 身故金领取方式 */
	private String DeadGetMode;
	/** 红利金领取方式 */
	private String BonusGetMode;
	/** 社保标记 */
	private String SSFlag;
	/** 封顶线 */
	private double PeakLine;
	/** 起付限 */
	private double GetLimit;
	/** 赔付比例 */
	private double GetRate;
	/** 保费计算规则 */
	private String CalRule;
	/** 保费算保额标志 */
	private String PremToAmnt;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 险种责任生效日期 */
	private Date CValiDate;
	/** 领取间隔 */
	private int GetIntv;
	/** 归属规则 */
	private String AscriptionRuleCode;
	/** 缴费规则 */
	private String PayRuleCode;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 56;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBDutySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EdorNo";
		pk[1] = "PolNo";
		pk[2] = "DutyCode";

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
		LBDutySchema cloned = (LBDutySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
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

	public double getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
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

	/**
	* 调整每个责任的保费
	*/
	public double getFloatRate()
	{
		return FloatRate;
	}
	public void setFloatRate(double aFloatRate)
	{
		FloatRate = aFloatRate;
	}
	public void setFloatRate(String aFloatRate)
	{
		if (aFloatRate != null && !aFloatRate.equals(""))
		{
			Double tDouble = new Double(aFloatRate);
			double d = tDouble.doubleValue();
			FloatRate = d;
		}
	}

	/**
	* 取保费中的最小首期交费日期
	*/
	public String getFirstPayDate()
	{
		if( FirstPayDate != null )
			return fDate.getString(FirstPayDate);
		else
			return null;
	}
	public void setFirstPayDate(Date aFirstPayDate)
	{
		FirstPayDate = aFirstPayDate;
	}
	public void setFirstPayDate(String aFirstPayDate)
	{
		if (aFirstPayDate != null && !aFirstPayDate.equals("") )
		{
			FirstPayDate = fDate.getDate( aFirstPayDate );
		}
		else
			FirstPayDate = null;
	}

	/**
	* 表示首期一次性交费的期数。月数还是期数？
	*/
	public int getFirstMonth()
	{
		return FirstMonth;
	}
	public void setFirstMonth(int aFirstMonth)
	{
		FirstMonth = aFirstMonth;
	}
	public void setFirstMonth(String aFirstMonth)
	{
		if (aFirstMonth != null && !aFirstMonth.equals(""))
		{
			Integer tInteger = new Integer(aFirstMonth);
			int i = tInteger.intValue();
			FirstMonth = i;
		}
	}

	/**
	* 取该责任下保费项中的最大交至日期
	*/
	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	/**
	* 取该责任下保费项中的最大终交日期
	*/
	public String getPayEndDate()
	{
		if( PayEndDate != null )
			return fDate.getString(PayEndDate);
		else
			return null;
	}
	public void setPayEndDate(Date aPayEndDate)
	{
		PayEndDate = aPayEndDate;
	}
	public void setPayEndDate(String aPayEndDate)
	{
		if (aPayEndDate != null && !aPayEndDate.equals("") )
		{
			PayEndDate = fDate.getDate( aPayEndDate );
		}
		else
			PayEndDate = null;
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
	* A－年龄，M－月，D－日，Y－年
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
	* 意外责任的作用？？
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

	/**
	* 1---表示免交。<p>
	* 0---不免交.
	*/
	public String getFreeFlag()
	{
		return FreeFlag;
	}
	public void setFreeFlag(String aFreeFlag)
	{
		FreeFlag = aFreeFlag;
	}
	/**
	* 0 －－ 表示全免<p>
	* 1 －－ 表示不免交。
	*/
	public double getFreeRate()
	{
		return FreeRate;
	}
	public void setFreeRate(double aFreeRate)
	{
		FreeRate = aFreeRate;
	}
	public void setFreeRate(String aFreeRate)
	{
		if (aFreeRate != null && !aFreeRate.equals(""))
		{
			Double tDouble = new Double(aFreeRate);
			double d = tDouble.doubleValue();
			FreeRate = d;
		}
	}

	public String getFreeStartDate()
	{
		if( FreeStartDate != null )
			return fDate.getString(FreeStartDate);
		else
			return null;
	}
	public void setFreeStartDate(Date aFreeStartDate)
	{
		FreeStartDate = aFreeStartDate;
	}
	public void setFreeStartDate(String aFreeStartDate)
	{
		if (aFreeStartDate != null && !aFreeStartDate.equals("") )
		{
			FreeStartDate = fDate.getDate( aFreeStartDate );
		}
		else
			FreeStartDate = null;
	}

	public String getFreeEndDate()
	{
		if( FreeEndDate != null )
			return fDate.getString(FreeEndDate);
		else
			return null;
	}
	public void setFreeEndDate(Date aFreeEndDate)
	{
		FreeEndDate = aFreeEndDate;
	}
	public void setFreeEndDate(String aFreeEndDate)
	{
		if (aFreeEndDate != null && !aFreeEndDate.equals("") )
		{
			FreeEndDate = fDate.getDate( aFreeEndDate );
		}
		else
			FreeEndDate = null;
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
	/**
	* 1--领取现金<p>
	* 2--抵缴保费<p>
	* 3--购买缴清增额保险<p>
	* 4--累积生息<p>
	* 9--其他
	*/
	public String getLiveGetMode()
	{
		return LiveGetMode;
	}
	public void setLiveGetMode(String aLiveGetMode)
	{
		LiveGetMode = aLiveGetMode;
	}
	/**
	* 1--领取现金<p>
	* 2--抵缴保费<p>
	* 3--购买缴清增额保险<p>
	* 4--累积生息<p>
	* 9--其他
	*/
	public String getDeadGetMode()
	{
		return DeadGetMode;
	}
	public void setDeadGetMode(String aDeadGetMode)
	{
		DeadGetMode = aDeadGetMode;
	}
	/**
	* 1--累积生息<p>
	* 2--领取现金<p>
	* 3--抵缴保费<p>
	* 4--其他<p>
	* 5--增额交清
	*/
	public String getBonusGetMode()
	{
		return BonusGetMode;
	}
	public void setBonusGetMode(String aBonusGetMode)
	{
		BonusGetMode = aBonusGetMode;
	}
	/**
	* 0-非社保<p>
	* 1-参加社保
	*/
	public String getSSFlag()
	{
		return SSFlag;
	}
	public void setSSFlag(String aSSFlag)
	{
		SSFlag = aSSFlag;
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

	/**
	* 0-表定费率；1-统一费率；2-表定费率折扣；3-约定费率
	*/
	public String getCalRule()
	{
		return CalRule;
	}
	public void setCalRule(String aCalRule)
	{
		CalRule = aCalRule;
	}
	public String getPremToAmnt()
	{
		return PremToAmnt;
	}
	public void setPremToAmnt(String aPremToAmnt)
	{
		PremToAmnt = aPremToAmnt;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* 譬如 对于险种编码：<p>
	* 311603-存放的是同心卡的开卡日期<p>
	* 211801-团商险存放(在职/退休)作为计算要素传到计算类
	*/
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* 银代险的内部优惠标志：（该字段对所有银代险有效）<p>
	* 1 默认 其他<p>
	*/
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据
	*/
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
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

	public String getAscriptionRuleCode()
	{
		return AscriptionRuleCode;
	}
	public void setAscriptionRuleCode(String aAscriptionRuleCode)
	{
		AscriptionRuleCode = aAscriptionRuleCode;
	}
	public String getPayRuleCode()
	{
		return PayRuleCode;
	}
	public void setPayRuleCode(String aPayRuleCode)
	{
		PayRuleCode = aPayRuleCode;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}

	/**
	* 使用另外一个 LBDutySchema 对象给 Schema 赋值
	* @param: aLBDutySchema LBDutySchema
	**/
	public void setSchema(LBDutySchema aLBDutySchema)
	{
		this.EdorNo = aLBDutySchema.getEdorNo();
		this.PolNo = aLBDutySchema.getPolNo();
		this.DutyCode = aLBDutySchema.getDutyCode();
		this.ContNo = aLBDutySchema.getContNo();
		this.Mult = aLBDutySchema.getMult();
		this.StandPrem = aLBDutySchema.getStandPrem();
		this.Prem = aLBDutySchema.getPrem();
		this.SumPrem = aLBDutySchema.getSumPrem();
		this.Amnt = aLBDutySchema.getAmnt();
		this.RiskAmnt = aLBDutySchema.getRiskAmnt();
		this.PayIntv = aLBDutySchema.getPayIntv();
		this.PayYears = aLBDutySchema.getPayYears();
		this.Years = aLBDutySchema.getYears();
		this.FloatRate = aLBDutySchema.getFloatRate();
		this.FirstPayDate = fDate.getDate( aLBDutySchema.getFirstPayDate());
		this.FirstMonth = aLBDutySchema.getFirstMonth();
		this.PaytoDate = fDate.getDate( aLBDutySchema.getPaytoDate());
		this.PayEndDate = fDate.getDate( aLBDutySchema.getPayEndDate());
		this.PayEndYearFlag = aLBDutySchema.getPayEndYearFlag();
		this.PayEndYear = aLBDutySchema.getPayEndYear();
		this.GetYearFlag = aLBDutySchema.getGetYearFlag();
		this.GetYear = aLBDutySchema.getGetYear();
		this.InsuYearFlag = aLBDutySchema.getInsuYearFlag();
		this.InsuYear = aLBDutySchema.getInsuYear();
		this.AcciYearFlag = aLBDutySchema.getAcciYearFlag();
		this.AcciYear = aLBDutySchema.getAcciYear();
		this.EndDate = fDate.getDate( aLBDutySchema.getEndDate());
		this.AcciEndDate = fDate.getDate( aLBDutySchema.getAcciEndDate());
		this.FreeFlag = aLBDutySchema.getFreeFlag();
		this.FreeRate = aLBDutySchema.getFreeRate();
		this.FreeStartDate = fDate.getDate( aLBDutySchema.getFreeStartDate());
		this.FreeEndDate = fDate.getDate( aLBDutySchema.getFreeEndDate());
		this.GetStartDate = fDate.getDate( aLBDutySchema.getGetStartDate());
		this.GetStartType = aLBDutySchema.getGetStartType();
		this.LiveGetMode = aLBDutySchema.getLiveGetMode();
		this.DeadGetMode = aLBDutySchema.getDeadGetMode();
		this.BonusGetMode = aLBDutySchema.getBonusGetMode();
		this.SSFlag = aLBDutySchema.getSSFlag();
		this.PeakLine = aLBDutySchema.getPeakLine();
		this.GetLimit = aLBDutySchema.getGetLimit();
		this.GetRate = aLBDutySchema.getGetRate();
		this.CalRule = aLBDutySchema.getCalRule();
		this.PremToAmnt = aLBDutySchema.getPremToAmnt();
		this.StandbyFlag1 = aLBDutySchema.getStandbyFlag1();
		this.StandbyFlag2 = aLBDutySchema.getStandbyFlag2();
		this.StandbyFlag3 = aLBDutySchema.getStandbyFlag3();
		this.Operator = aLBDutySchema.getOperator();
		this.MakeDate = fDate.getDate( aLBDutySchema.getMakeDate());
		this.MakeTime = aLBDutySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBDutySchema.getModifyDate());
		this.ModifyTime = aLBDutySchema.getModifyTime();
		this.CValiDate = fDate.getDate( aLBDutySchema.getCValiDate());
		this.GetIntv = aLBDutySchema.getGetIntv();
		this.AscriptionRuleCode = aLBDutySchema.getAscriptionRuleCode();
		this.PayRuleCode = aLBDutySchema.getPayRuleCode();
		this.Currency = aLBDutySchema.getCurrency();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.Mult = rs.getDouble("Mult");
			this.StandPrem = rs.getDouble("StandPrem");
			this.Prem = rs.getDouble("Prem");
			this.SumPrem = rs.getDouble("SumPrem");
			this.Amnt = rs.getDouble("Amnt");
			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.PayIntv = rs.getInt("PayIntv");
			this.PayYears = rs.getInt("PayYears");
			this.Years = rs.getInt("Years");
			this.FloatRate = rs.getDouble("FloatRate");
			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.FirstMonth = rs.getInt("FirstMonth");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.PayEndDate = rs.getDate("PayEndDate");
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
			this.EndDate = rs.getDate("EndDate");
			this.AcciEndDate = rs.getDate("AcciEndDate");
			if( rs.getString("FreeFlag") == null )
				this.FreeFlag = null;
			else
				this.FreeFlag = rs.getString("FreeFlag").trim();

			this.FreeRate = rs.getDouble("FreeRate");
			this.FreeStartDate = rs.getDate("FreeStartDate");
			this.FreeEndDate = rs.getDate("FreeEndDate");
			this.GetStartDate = rs.getDate("GetStartDate");
			if( rs.getString("GetStartType") == null )
				this.GetStartType = null;
			else
				this.GetStartType = rs.getString("GetStartType").trim();

			if( rs.getString("LiveGetMode") == null )
				this.LiveGetMode = null;
			else
				this.LiveGetMode = rs.getString("LiveGetMode").trim();

			if( rs.getString("DeadGetMode") == null )
				this.DeadGetMode = null;
			else
				this.DeadGetMode = rs.getString("DeadGetMode").trim();

			if( rs.getString("BonusGetMode") == null )
				this.BonusGetMode = null;
			else
				this.BonusGetMode = rs.getString("BonusGetMode").trim();

			if( rs.getString("SSFlag") == null )
				this.SSFlag = null;
			else
				this.SSFlag = rs.getString("SSFlag").trim();

			this.PeakLine = rs.getDouble("PeakLine");
			this.GetLimit = rs.getDouble("GetLimit");
			this.GetRate = rs.getDouble("GetRate");
			if( rs.getString("CalRule") == null )
				this.CalRule = null;
			else
				this.CalRule = rs.getString("CalRule").trim();

			if( rs.getString("PremToAmnt") == null )
				this.PremToAmnt = null;
			else
				this.PremToAmnt = rs.getString("PremToAmnt").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.GetIntv = rs.getInt("GetIntv");
			if( rs.getString("AscriptionRuleCode") == null )
				this.AscriptionRuleCode = null;
			else
				this.AscriptionRuleCode = rs.getString("AscriptionRuleCode").trim();

			if( rs.getString("PayRuleCode") == null )
				this.PayRuleCode = null;
			else
				this.PayRuleCode = rs.getString("PayRuleCode").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBDuty表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBDutySchema getSchema()
	{
		LBDutySchema aLBDutySchema = new LBDutySchema();
		aLBDutySchema.setSchema(this);
		return aLBDutySchema;
	}

	public LBDutyDB getDB()
	{
		LBDutyDB aDBOper = new LBDutyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBDuty描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FloatRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FirstMonth));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcciYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AcciYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AcciEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FreeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FreeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FreeStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FreeEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LiveGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeadGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SSFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PeakLine));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalRule)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremToAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBDuty>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			FloatRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			FirstMonth= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			GetYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			GetYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			AcciYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AcciYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			AcciEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			FreeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			FreeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			FreeStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			FreeEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			GetStartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			LiveGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			DeadGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			BonusGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			PeakLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			CalRule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			PremToAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).intValue();
			AscriptionRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			PayRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutySchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
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
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FloatRate));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equalsIgnoreCase("FirstMonth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstMonth));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("AcciEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
		}
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreeFlag));
		}
		if (FCode.equalsIgnoreCase("FreeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FreeRate));
		}
		if (FCode.equalsIgnoreCase("FreeStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFreeStartDate()));
		}
		if (FCode.equalsIgnoreCase("FreeEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFreeEndDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartType));
		}
		if (FCode.equalsIgnoreCase("LiveGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LiveGetMode));
		}
		if (FCode.equalsIgnoreCase("DeadGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeadGetMode));
		}
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGetMode));
		}
		if (FCode.equalsIgnoreCase("SSFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SSFlag));
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeakLine));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("CalRule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalRule));
		}
		if (FCode.equalsIgnoreCase("PremToAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremToAmnt));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionRuleCode));
		}
		if (FCode.equalsIgnoreCase("PayRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayRuleCode));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = String.valueOf(Mult);
				break;
			case 5:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 6:
				strFieldValue = String.valueOf(Prem);
				break;
			case 7:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 8:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 9:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 10:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 11:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 12:
				strFieldValue = String.valueOf(Years);
				break;
			case 13:
				strFieldValue = String.valueOf(FloatRate);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 15:
				strFieldValue = String.valueOf(FirstMonth);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 19:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GetYearFlag);
				break;
			case 21:
				strFieldValue = String.valueOf(GetYear);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AcciYearFlag);
				break;
			case 25:
				strFieldValue = String.valueOf(AcciYear);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAcciEndDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(FreeFlag);
				break;
			case 29:
				strFieldValue = String.valueOf(FreeRate);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFreeStartDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFreeEndDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(GetStartType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(LiveGetMode);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(DeadGetMode);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(BonusGetMode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SSFlag);
				break;
			case 38:
				strFieldValue = String.valueOf(PeakLine);
				break;
			case 39:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 40:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(CalRule);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(PremToAmnt);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 52:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(AscriptionRuleCode);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(PayRuleCode);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(Currency);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
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
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
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
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
			}
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FloatRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
		}
		if (FCode.equalsIgnoreCase("FirstMonth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FirstMonth = i;
			}
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayEndDate = fDate.getDate( FValue );
			}
			else
				PayEndDate = null;
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		if (FCode.equalsIgnoreCase("FreeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FreeFlag = FValue.trim();
			}
			else
				FreeFlag = null;
		}
		if (FCode.equalsIgnoreCase("FreeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FreeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FreeStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FreeStartDate = fDate.getDate( FValue );
			}
			else
				FreeStartDate = null;
		}
		if (FCode.equalsIgnoreCase("FreeEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FreeEndDate = fDate.getDate( FValue );
			}
			else
				FreeEndDate = null;
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
		if (FCode.equalsIgnoreCase("LiveGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LiveGetMode = FValue.trim();
			}
			else
				LiveGetMode = null;
		}
		if (FCode.equalsIgnoreCase("DeadGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeadGetMode = FValue.trim();
			}
			else
				DeadGetMode = null;
		}
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusGetMode = FValue.trim();
			}
			else
				BonusGetMode = null;
		}
		if (FCode.equalsIgnoreCase("SSFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SSFlag = FValue.trim();
			}
			else
				SSFlag = null;
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
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalRule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalRule = FValue.trim();
			}
			else
				CalRule = null;
		}
		if (FCode.equalsIgnoreCase("PremToAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremToAmnt = FValue.trim();
			}
			else
				PremToAmnt = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptionRuleCode = FValue.trim();
			}
			else
				AscriptionRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("PayRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayRuleCode = FValue.trim();
			}
			else
				PayRuleCode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBDutySchema other = (LBDutySchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& ContNo.equals(other.getContNo())
			&& Mult == other.getMult()
			&& StandPrem == other.getStandPrem()
			&& Prem == other.getPrem()
			&& SumPrem == other.getSumPrem()
			&& Amnt == other.getAmnt()
			&& RiskAmnt == other.getRiskAmnt()
			&& PayIntv == other.getPayIntv()
			&& PayYears == other.getPayYears()
			&& Years == other.getYears()
			&& FloatRate == other.getFloatRate()
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& FirstMonth == other.getFirstMonth()
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& GetYearFlag.equals(other.getGetYearFlag())
			&& GetYear == other.getGetYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& AcciYearFlag.equals(other.getAcciYearFlag())
			&& AcciYear == other.getAcciYear()
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(AcciEndDate).equals(other.getAcciEndDate())
			&& FreeFlag.equals(other.getFreeFlag())
			&& FreeRate == other.getFreeRate()
			&& fDate.getString(FreeStartDate).equals(other.getFreeStartDate())
			&& fDate.getString(FreeEndDate).equals(other.getFreeEndDate())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& GetStartType.equals(other.getGetStartType())
			&& LiveGetMode.equals(other.getLiveGetMode())
			&& DeadGetMode.equals(other.getDeadGetMode())
			&& BonusGetMode.equals(other.getBonusGetMode())
			&& SSFlag.equals(other.getSSFlag())
			&& PeakLine == other.getPeakLine()
			&& GetLimit == other.getGetLimit()
			&& GetRate == other.getGetRate()
			&& CalRule.equals(other.getCalRule())
			&& PremToAmnt.equals(other.getPremToAmnt())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& GetIntv == other.getGetIntv()
			&& AscriptionRuleCode.equals(other.getAscriptionRuleCode())
			&& PayRuleCode.equals(other.getPayRuleCode())
			&& Currency.equals(other.getCurrency());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("Mult") ) {
			return 4;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 5;
		}
		if( strFieldName.equals("Prem") ) {
			return 6;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 7;
		}
		if( strFieldName.equals("Amnt") ) {
			return 8;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 9;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 10;
		}
		if( strFieldName.equals("PayYears") ) {
			return 11;
		}
		if( strFieldName.equals("Years") ) {
			return 12;
		}
		if( strFieldName.equals("FloatRate") ) {
			return 13;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 14;
		}
		if( strFieldName.equals("FirstMonth") ) {
			return 15;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 16;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 17;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 18;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 19;
		}
		if( strFieldName.equals("GetYearFlag") ) {
			return 20;
		}
		if( strFieldName.equals("GetYear") ) {
			return 21;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 22;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 23;
		}
		if( strFieldName.equals("AcciYearFlag") ) {
			return 24;
		}
		if( strFieldName.equals("AcciYear") ) {
			return 25;
		}
		if( strFieldName.equals("EndDate") ) {
			return 26;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return 27;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return 28;
		}
		if( strFieldName.equals("FreeRate") ) {
			return 29;
		}
		if( strFieldName.equals("FreeStartDate") ) {
			return 30;
		}
		if( strFieldName.equals("FreeEndDate") ) {
			return 31;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 32;
		}
		if( strFieldName.equals("GetStartType") ) {
			return 33;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return 34;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return 35;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return 36;
		}
		if( strFieldName.equals("SSFlag") ) {
			return 37;
		}
		if( strFieldName.equals("PeakLine") ) {
			return 38;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 39;
		}
		if( strFieldName.equals("GetRate") ) {
			return 40;
		}
		if( strFieldName.equals("CalRule") ) {
			return 41;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return 42;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 43;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 44;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 45;
		}
		if( strFieldName.equals("Operator") ) {
			return 46;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 47;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 48;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 50;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 51;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 52;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return 53;
		}
		if( strFieldName.equals("PayRuleCode") ) {
			return 54;
		}
		if( strFieldName.equals("Currency") ) {
			return 55;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "Mult";
				break;
			case 5:
				strFieldName = "StandPrem";
				break;
			case 6:
				strFieldName = "Prem";
				break;
			case 7:
				strFieldName = "SumPrem";
				break;
			case 8:
				strFieldName = "Amnt";
				break;
			case 9:
				strFieldName = "RiskAmnt";
				break;
			case 10:
				strFieldName = "PayIntv";
				break;
			case 11:
				strFieldName = "PayYears";
				break;
			case 12:
				strFieldName = "Years";
				break;
			case 13:
				strFieldName = "FloatRate";
				break;
			case 14:
				strFieldName = "FirstPayDate";
				break;
			case 15:
				strFieldName = "FirstMonth";
				break;
			case 16:
				strFieldName = "PaytoDate";
				break;
			case 17:
				strFieldName = "PayEndDate";
				break;
			case 18:
				strFieldName = "PayEndYearFlag";
				break;
			case 19:
				strFieldName = "PayEndYear";
				break;
			case 20:
				strFieldName = "GetYearFlag";
				break;
			case 21:
				strFieldName = "GetYear";
				break;
			case 22:
				strFieldName = "InsuYearFlag";
				break;
			case 23:
				strFieldName = "InsuYear";
				break;
			case 24:
				strFieldName = "AcciYearFlag";
				break;
			case 25:
				strFieldName = "AcciYear";
				break;
			case 26:
				strFieldName = "EndDate";
				break;
			case 27:
				strFieldName = "AcciEndDate";
				break;
			case 28:
				strFieldName = "FreeFlag";
				break;
			case 29:
				strFieldName = "FreeRate";
				break;
			case 30:
				strFieldName = "FreeStartDate";
				break;
			case 31:
				strFieldName = "FreeEndDate";
				break;
			case 32:
				strFieldName = "GetStartDate";
				break;
			case 33:
				strFieldName = "GetStartType";
				break;
			case 34:
				strFieldName = "LiveGetMode";
				break;
			case 35:
				strFieldName = "DeadGetMode";
				break;
			case 36:
				strFieldName = "BonusGetMode";
				break;
			case 37:
				strFieldName = "SSFlag";
				break;
			case 38:
				strFieldName = "PeakLine";
				break;
			case 39:
				strFieldName = "GetLimit";
				break;
			case 40:
				strFieldName = "GetRate";
				break;
			case 41:
				strFieldName = "CalRule";
				break;
			case 42:
				strFieldName = "PremToAmnt";
				break;
			case 43:
				strFieldName = "StandbyFlag1";
				break;
			case 44:
				strFieldName = "StandbyFlag2";
				break;
			case 45:
				strFieldName = "StandbyFlag3";
				break;
			case 46:
				strFieldName = "Operator";
				break;
			case 47:
				strFieldName = "MakeDate";
				break;
			case 48:
				strFieldName = "MakeTime";
				break;
			case 49:
				strFieldName = "ModifyDate";
				break;
			case 50:
				strFieldName = "ModifyTime";
				break;
			case 51:
				strFieldName = "CValiDate";
				break;
			case 52:
				strFieldName = "GetIntv";
				break;
			case 53:
				strFieldName = "AscriptionRuleCode";
				break;
			case 54:
				strFieldName = "PayRuleCode";
				break;
			case 55:
				strFieldName = "Currency";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FloatRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstMonth") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AcciEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FreeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FreeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FreeStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FreeEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LiveGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeadGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SSFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeakLine") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalRule") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremToAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 52:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
