

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
import com.sinosoft.lis.db.PD_LMDutyPayDB;

/*
 * <p>ClassName: PD_LMDutyPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_3
 */
public class PD_LMDutyPaySchema implements Schema, Cloneable
{
	// @Field
	/** 缴费编码 */
	private String PayPlanCode;
	/** 责任代码 */
	private String DutyCode;
	/** 算法编码 */
	private String CalCode;
	/** 缴费名称 */
	private String PayPlanName;
	/** 缴费类型 */
	private String Type;
	/** 交费间隔 */
	private int PayIntv;
	/** 缴费终止期间单位 */
	private String PayEndYearFlag;
	/** 缴费终止期间 */
	private int PayEndYear;
	/** 缴费终止日期计算参照 */
	private String PayEndDateCalRef;
	/** 缴费终止日期计算方式 */
	private String PayEndDateCalMode;
	/** 默认值 */
	private double DefaultVal;
	/** 反算算法 */
	private String CnterCalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 保费分配比例 */
	private double Rate;
	/** 最低限额 */
	private double MinPay;
	/** 保证收益率 */
	private double AssuYield;
	/** 提取管理费比例 */
	private double FeeRate;
	/** 缴至日期计算方法 */
	private String PayToDateCalMode;
	/** 催缴标记 */
	private String UrgePayFlag;
	/** 部分缴费标记 */
	private String PayLackFlag;
	/** 挂帐标记 */
	private String PayOverFlag;
	/** 挂帐处理 */
	private String PayOverDeal;
	/** 免交标记 */
	private String AvoidPayFlag;
	/** 缴费宽限期 */
	private int GracePeriod;
	/** 公用标记 */
	private String PubFlag;
	/** 是否允许零值标记 */
	private String ZeroFlag;
	/** 是否和账户相关 */
	private String NeedAcc;
	/** 交费目的分类 */
	private String PayAimClass;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;
	/** 交费起始期间 */
	private int PayStartYear;
	/** 交费起始期间单位 */
	private String PayStartYearFlag;
	/** 交费起始日期计算参照 */
	private String PayStartDateCalRef;
	/** 交费起始日期计算方式 */
	private String PayStartDateCalMode;
	/** 投资分类 */
	private String InvestType;
	/** 建议书算法 */
	private String PCalCode;
	/** 保费重算标记 */
	private String RCalPremFlag;
	/** 保费重算时点算法 */
	private String RCalPremCode;

	public static final int FIELDNUM = 47;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PayPlanCode";

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
		PD_LMDutyPaySchema cloned = (PD_LMDutyPaySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	/**
	* 1-3位：险种号码 4位：业务类型（该位不一定按照此规则来编写，现在赔付都是3） 1 承保 2 领取 3 赔付 4 现金价值 5 单证描述 5-6位 顺序号
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getPayPlanName()
	{
		return PayPlanName;
	}
	public void setPayPlanName(String aPayPlanName)
	{
		PayPlanName = aPayPlanName;
	}
	/**
	* 0 －－ 一个保费项描述。 1 －－ 健康加费 2 －－ 职业加费 以下为以前的描述，现在不再使用。 账户性质(101-交费/211-帐户交费型现金制;212-帐户交费型股份制;221-帐户普通型现金制; 222-帐户普通型股份制;301-附加费)(2001/10/10) ##[1]-1:交费;2:帐户;3:加费 ##[2]-0:无关;1:交费履历型;2:普通帐户型 ##[3]-0:无关;1:单位为金额;2:单位为股份 ##[4]-0:普通帐户；1－门诊帐户 ##[5]-0：普通帐户；1－分红帐户（分红帐户在分红时有2.5%的保障收益，因此在保全时先不计算出利息，而在结算时计算分红结果）
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	/**
	* -1 表示随意缴。 0 －－ 表示趸缴 1 －－ 月缴 3 －－ 季缴 6 －－ 半年缴 12 －－ 年缴
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
	* Y--年、M--月、D--日、A--年龄
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
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getPayEndDateCalRef()
	{
		return PayEndDateCalRef;
	}
	public void setPayEndDateCalRef(String aPayEndDateCalRef)
	{
		PayEndDateCalRef = aPayEndDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getPayEndDateCalMode()
	{
		return PayEndDateCalMode;
	}
	public void setPayEndDateCalMode(String aPayEndDateCalMode)
	{
		PayEndDateCalMode = aPayEndDateCalMode;
	}
	public double getDefaultVal()
	{
		return DefaultVal;
	}
	public void setDefaultVal(double aDefaultVal)
	{
		DefaultVal = aDefaultVal;
	}
	public void setDefaultVal(String aDefaultVal)
	{
		if (aDefaultVal != null && !aDefaultVal.equals(""))
		{
			Double tDouble = new Double(aDefaultVal);
			double d = tDouble.doubleValue();
			DefaultVal = d;
		}
	}

	/**
	* 保费算保费
	*/
	public String getCnterCalCode()
	{
		return CnterCalCode;
	}
	public void setCnterCalCode(String aCnterCalCode)
	{
		CnterCalCode = aCnterCalCode;
	}
	/**
	* 其他算保费
	*/
	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		OthCalCode = aOthCalCode;
	}
	public double getRate()
	{
		return Rate;
	}
	public void setRate(double aRate)
	{
		Rate = aRate;
	}
	public void setRate(String aRate)
	{
		if (aRate != null && !aRate.equals(""))
		{
			Double tDouble = new Double(aRate);
			double d = tDouble.doubleValue();
			Rate = d;
		}
	}

	public double getMinPay()
	{
		return MinPay;
	}
	public void setMinPay(double aMinPay)
	{
		MinPay = aMinPay;
	}
	public void setMinPay(String aMinPay)
	{
		if (aMinPay != null && !aMinPay.equals(""))
		{
			Double tDouble = new Double(aMinPay);
			double d = tDouble.doubleValue();
			MinPay = d;
		}
	}

	public double getAssuYield()
	{
		return AssuYield;
	}
	public void setAssuYield(double aAssuYield)
	{
		AssuYield = aAssuYield;
	}
	public void setAssuYield(String aAssuYield)
	{
		if (aAssuYield != null && !aAssuYield.equals(""))
		{
			Double tDouble = new Double(aAssuYield);
			double d = tDouble.doubleValue();
			AssuYield = d;
		}
	}

	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}

	/**
	* 0-正常算法、1-取当月一日
	*/
	public String getPayToDateCalMode()
	{
		return PayToDateCalMode;
	}
	public void setPayToDateCalMode(String aPayToDateCalMode)
	{
		PayToDateCalMode = aPayToDateCalMode;
	}
	/**
	* Y--催缴、N--不催缴。
	*/
	public String getUrgePayFlag()
	{
		return UrgePayFlag;
	}
	public void setUrgePayFlag(String aUrgePayFlag)
	{
		UrgePayFlag = aUrgePayFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getPayLackFlag()
	{
		return PayLackFlag;
	}
	public void setPayLackFlag(String aPayLackFlag)
	{
		PayLackFlag = aPayLackFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getPayOverFlag()
	{
		return PayOverFlag;
	}
	public void setPayOverFlag(String aPayOverFlag)
	{
		PayOverFlag = aPayOverFlag;
	}
	/**
	* 表明多缴部分的记帐方式
	*/
	public String getPayOverDeal()
	{
		return PayOverDeal;
	}
	public void setPayOverDeal(String aPayOverDeal)
	{
		PayOverDeal = aPayOverDeal;
	}
	/**
	* Y--是 N--否
	*/
	public String getAvoidPayFlag()
	{
		return AvoidPayFlag;
	}
	public void setAvoidPayFlag(String aAvoidPayFlag)
	{
		AvoidPayFlag = aAvoidPayFlag;
	}
	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* Y--公用帐户、N--普通帐户
	*/
	public String getPubFlag()
	{
		return PubFlag;
	}
	public void setPubFlag(String aPubFlag)
	{
		PubFlag = aPubFlag;
	}
	/**
	* Y--允许 N--不允许 not null
	*/
	public String getZeroFlag()
	{
		return ZeroFlag;
	}
	public void setZeroFlag(String aZeroFlag)
	{
		ZeroFlag = aZeroFlag;
	}
	/**
	* 0 －－ 账户无关 1 －－ 账户相关
	*/
	public String getNeedAcc()
	{
		return NeedAcc;
	}
	public void setNeedAcc(String aNeedAcc)
	{
		NeedAcc = aNeedAcc;
	}
	/**
	* 1 --个人交费 2 --集体交费
	*/
	public String getPayAimClass()
	{
		return PayAimClass;
	}
	public void setPayAimClass(String aPayAimClass)
	{
		PayAimClass = aPayAimClass;
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}

	public int getPayStartYear()
	{
		return PayStartYear;
	}
	public void setPayStartYear(int aPayStartYear)
	{
		PayStartYear = aPayStartYear;
	}
	public void setPayStartYear(String aPayStartYear)
	{
		if (aPayStartYear != null && !aPayStartYear.equals(""))
		{
			Integer tInteger = new Integer(aPayStartYear);
			int i = tInteger.intValue();
			PayStartYear = i;
		}
	}

	/**
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getPayStartYearFlag()
	{
		return PayStartYearFlag;
	}
	public void setPayStartYearFlag(String aPayStartYearFlag)
	{
		PayStartYearFlag = aPayStartYearFlag;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getPayStartDateCalRef()
	{
		return PayStartDateCalRef;
	}
	public void setPayStartDateCalRef(String aPayStartDateCalRef)
	{
		PayStartDateCalRef = aPayStartDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getPayStartDateCalMode()
	{
		return PayStartDateCalMode;
	}
	public void setPayStartDateCalMode(String aPayStartDateCalMode)
	{
		PayStartDateCalMode = aPayStartDateCalMode;
	}
	/**
	* 1--定期供款<p>
	* 2--定期追加<p>
	* 3--不定期追加
	*/
	public String getInvestType()
	{
		return InvestType;
	}
	public void setInvestType(String aInvestType)
	{
		InvestType = aInvestType;
	}
	public String getPCalCode()
	{
		return PCalCode;
	}
	public void setPCalCode(String aPCalCode)
	{
		PCalCode = aPCalCode;
	}
	/**
	* Y-需要；N-不需要
	*/
	public String getRCalPremFlag()
	{
		return RCalPremFlag;
	}
	public void setRCalPremFlag(String aRCalPremFlag)
	{
		RCalPremFlag = aRCalPremFlag;
	}
	public String getRCalPremCode()
	{
		return RCalPremCode;
	}
	public void setRCalPremCode(String aRCalPremCode)
	{
		RCalPremCode = aRCalPremCode;
	}

	/**
	* 使用另外一个 PD_LMDutyPaySchema 对象给 Schema 赋值
	* @param: aPD_LMDutyPaySchema PD_LMDutyPaySchema
	**/
	public void setSchema(PD_LMDutyPaySchema aPD_LMDutyPaySchema)
	{
		this.PayPlanCode = aPD_LMDutyPaySchema.getPayPlanCode();
		this.DutyCode = aPD_LMDutyPaySchema.getDutyCode();
		this.CalCode = aPD_LMDutyPaySchema.getCalCode();
		this.PayPlanName = aPD_LMDutyPaySchema.getPayPlanName();
		this.Type = aPD_LMDutyPaySchema.getType();
		this.PayIntv = aPD_LMDutyPaySchema.getPayIntv();
		this.PayEndYearFlag = aPD_LMDutyPaySchema.getPayEndYearFlag();
		this.PayEndYear = aPD_LMDutyPaySchema.getPayEndYear();
		this.PayEndDateCalRef = aPD_LMDutyPaySchema.getPayEndDateCalRef();
		this.PayEndDateCalMode = aPD_LMDutyPaySchema.getPayEndDateCalMode();
		this.DefaultVal = aPD_LMDutyPaySchema.getDefaultVal();
		this.CnterCalCode = aPD_LMDutyPaySchema.getCnterCalCode();
		this.OthCalCode = aPD_LMDutyPaySchema.getOthCalCode();
		this.Rate = aPD_LMDutyPaySchema.getRate();
		this.MinPay = aPD_LMDutyPaySchema.getMinPay();
		this.AssuYield = aPD_LMDutyPaySchema.getAssuYield();
		this.FeeRate = aPD_LMDutyPaySchema.getFeeRate();
		this.PayToDateCalMode = aPD_LMDutyPaySchema.getPayToDateCalMode();
		this.UrgePayFlag = aPD_LMDutyPaySchema.getUrgePayFlag();
		this.PayLackFlag = aPD_LMDutyPaySchema.getPayLackFlag();
		this.PayOverFlag = aPD_LMDutyPaySchema.getPayOverFlag();
		this.PayOverDeal = aPD_LMDutyPaySchema.getPayOverDeal();
		this.AvoidPayFlag = aPD_LMDutyPaySchema.getAvoidPayFlag();
		this.GracePeriod = aPD_LMDutyPaySchema.getGracePeriod();
		this.PubFlag = aPD_LMDutyPaySchema.getPubFlag();
		this.ZeroFlag = aPD_LMDutyPaySchema.getZeroFlag();
		this.NeedAcc = aPD_LMDutyPaySchema.getNeedAcc();
		this.PayAimClass = aPD_LMDutyPaySchema.getPayAimClass();
		this.Operator = aPD_LMDutyPaySchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyPaySchema.getMakeDate());
		this.MakeTime = aPD_LMDutyPaySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyPaySchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyPaySchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyPaySchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyPaySchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyPaySchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyPaySchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyPaySchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyPaySchema.getStandbyflag6();
		this.PayStartYear = aPD_LMDutyPaySchema.getPayStartYear();
		this.PayStartYearFlag = aPD_LMDutyPaySchema.getPayStartYearFlag();
		this.PayStartDateCalRef = aPD_LMDutyPaySchema.getPayStartDateCalRef();
		this.PayStartDateCalMode = aPD_LMDutyPaySchema.getPayStartDateCalMode();
		this.InvestType = aPD_LMDutyPaySchema.getInvestType();
		this.PCalCode = aPD_LMDutyPaySchema.getPCalCode();
		this.RCalPremFlag = aPD_LMDutyPaySchema.getRCalPremFlag();
		this.RCalPremCode = aPD_LMDutyPaySchema.getRCalPremCode();
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
			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("PayPlanName") == null )
				this.PayPlanName = null;
			else
				this.PayPlanName = rs.getString("PayPlanName").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("PayEndDateCalRef") == null )
				this.PayEndDateCalRef = null;
			else
				this.PayEndDateCalRef = rs.getString("PayEndDateCalRef").trim();

			if( rs.getString("PayEndDateCalMode") == null )
				this.PayEndDateCalMode = null;
			else
				this.PayEndDateCalMode = rs.getString("PayEndDateCalMode").trim();

			this.DefaultVal = rs.getDouble("DefaultVal");
			if( rs.getString("CnterCalCode") == null )
				this.CnterCalCode = null;
			else
				this.CnterCalCode = rs.getString("CnterCalCode").trim();

			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			this.Rate = rs.getDouble("Rate");
			this.MinPay = rs.getDouble("MinPay");
			this.AssuYield = rs.getDouble("AssuYield");
			this.FeeRate = rs.getDouble("FeeRate");
			if( rs.getString("PayToDateCalMode") == null )
				this.PayToDateCalMode = null;
			else
				this.PayToDateCalMode = rs.getString("PayToDateCalMode").trim();

			if( rs.getString("UrgePayFlag") == null )
				this.UrgePayFlag = null;
			else
				this.UrgePayFlag = rs.getString("UrgePayFlag").trim();

			if( rs.getString("PayLackFlag") == null )
				this.PayLackFlag = null;
			else
				this.PayLackFlag = rs.getString("PayLackFlag").trim();

			if( rs.getString("PayOverFlag") == null )
				this.PayOverFlag = null;
			else
				this.PayOverFlag = rs.getString("PayOverFlag").trim();

			if( rs.getString("PayOverDeal") == null )
				this.PayOverDeal = null;
			else
				this.PayOverDeal = rs.getString("PayOverDeal").trim();

			if( rs.getString("AvoidPayFlag") == null )
				this.AvoidPayFlag = null;
			else
				this.AvoidPayFlag = rs.getString("AvoidPayFlag").trim();

			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("PubFlag") == null )
				this.PubFlag = null;
			else
				this.PubFlag = rs.getString("PubFlag").trim();

			if( rs.getString("ZeroFlag") == null )
				this.ZeroFlag = null;
			else
				this.ZeroFlag = rs.getString("ZeroFlag").trim();

			if( rs.getString("NeedAcc") == null )
				this.NeedAcc = null;
			else
				this.NeedAcc = rs.getString("NeedAcc").trim();

			if( rs.getString("PayAimClass") == null )
				this.PayAimClass = null;
			else
				this.PayAimClass = rs.getString("PayAimClass").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
			this.PayStartYear = rs.getInt("PayStartYear");
			if( rs.getString("PayStartYearFlag") == null )
				this.PayStartYearFlag = null;
			else
				this.PayStartYearFlag = rs.getString("PayStartYearFlag").trim();

			if( rs.getString("PayStartDateCalRef") == null )
				this.PayStartDateCalRef = null;
			else
				this.PayStartDateCalRef = rs.getString("PayStartDateCalRef").trim();

			if( rs.getString("PayStartDateCalMode") == null )
				this.PayStartDateCalMode = null;
			else
				this.PayStartDateCalMode = rs.getString("PayStartDateCalMode").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("PCalCode") == null )
				this.PCalCode = null;
			else
				this.PCalCode = rs.getString("PCalCode").trim();

			if( rs.getString("RCalPremFlag") == null )
				this.RCalPremFlag = null;
			else
				this.RCalPremFlag = rs.getString("RCalPremFlag").trim();

			if( rs.getString("RCalPremCode") == null )
				this.RCalPremCode = null;
			else
				this.RCalPremCode = rs.getString("RCalPremCode").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMDutyPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyPaySchema getSchema()
	{
		PD_LMDutyPaySchema aPD_LMDutyPaySchema = new PD_LMDutyPaySchema();
		aPD_LMDutyPaySchema.setSchema(this);
		return aPD_LMDutyPaySchema;
	}

	public PD_LMDutyPayDB getDB()
	{
		PD_LMDutyPayDB aDBOper = new PD_LMDutyPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultVal));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CnterCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssuYield));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayToDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLackFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayOverFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayOverDeal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvoidPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZeroFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAimClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayStartYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayStartYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayStartDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayStartDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RCalPremFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RCalPremCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			PayEndDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PayEndDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DefaultVal = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			CnterCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			MinPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			AssuYield = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			PayToDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			UrgePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PayLackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PayOverFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			PayOverDeal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AvoidPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			PubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ZeroFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			NeedAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			PayAimClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			PayStartYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
			PayStartYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			PayStartDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			PayStartDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			PCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			RCalPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			RCalPremCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPaySchema";
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanName));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDateCalRef));
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultVal));
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CnterCalCode));
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthCalCode));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("MinPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinPay));
		}
		if (FCode.equalsIgnoreCase("AssuYield"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuYield));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
		}
		if (FCode.equalsIgnoreCase("PayToDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayToDateCalMode));
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgePayFlag));
		}
		if (FCode.equalsIgnoreCase("PayLackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLackFlag));
		}
		if (FCode.equalsIgnoreCase("PayOverFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayOverFlag));
		}
		if (FCode.equalsIgnoreCase("PayOverDeal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayOverDeal));
		}
		if (FCode.equalsIgnoreCase("AvoidPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvoidPayFlag));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("PubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PubFlag));
		}
		if (FCode.equalsIgnoreCase("ZeroFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZeroFlag));
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedAcc));
		}
		if (FCode.equalsIgnoreCase("PayAimClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAimClass));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
		}
		if (FCode.equalsIgnoreCase("PayStartYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartYear));
		}
		if (FCode.equalsIgnoreCase("PayStartYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartDateCalRef));
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayStartDateCalMode));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("PCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PCalCode));
		}
		if (FCode.equalsIgnoreCase("RCalPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCalPremFlag));
		}
		if (FCode.equalsIgnoreCase("RCalPremCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCalPremCode));
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
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayPlanName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 5:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PayEndDateCalRef);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PayEndDateCalMode);
				break;
			case 10:
				strFieldValue = String.valueOf(DefaultVal);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CnterCalCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 13:
				strFieldValue = String.valueOf(Rate);
				break;
			case 14:
				strFieldValue = String.valueOf(MinPay);
				break;
			case 15:
				strFieldValue = String.valueOf(AssuYield);
				break;
			case 16:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(PayToDateCalMode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(UrgePayFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PayLackFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PayOverFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PayOverDeal);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AvoidPayFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(PubFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ZeroFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(NeedAcc);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(PayAimClass);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 35:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 36:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 37:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 38:
				strFieldValue = String.valueOf(Standbyflag6);
				break;
			case 39:
				strFieldValue = String.valueOf(PayStartYear);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(PayStartYearFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(PayStartDateCalRef);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(PayStartDateCalMode);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(PCalCode);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(RCalPremFlag);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(RCalPremCode);
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

		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanName = FValue.trim();
			}
			else
				PayPlanName = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
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
		if (FCode.equalsIgnoreCase("PayEndDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDateCalRef = FValue.trim();
			}
			else
				PayEndDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndDateCalMode = FValue.trim();
			}
			else
				PayEndDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultVal = d;
			}
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CnterCalCode = FValue.trim();
			}
			else
				CnterCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthCalCode = FValue.trim();
			}
			else
				OthCalCode = null;
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MinPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MinPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("AssuYield"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AssuYield = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayToDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayToDateCalMode = FValue.trim();
			}
			else
				PayToDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgePayFlag = FValue.trim();
			}
			else
				UrgePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayLackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayLackFlag = FValue.trim();
			}
			else
				PayLackFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayOverFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayOverFlag = FValue.trim();
			}
			else
				PayOverFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayOverDeal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayOverDeal = FValue.trim();
			}
			else
				PayOverDeal = null;
		}
		if (FCode.equalsIgnoreCase("AvoidPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvoidPayFlag = FValue.trim();
			}
			else
				AvoidPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("PubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PubFlag = FValue.trim();
			}
			else
				PubFlag = null;
		}
		if (FCode.equalsIgnoreCase("ZeroFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZeroFlag = FValue.trim();
			}
			else
				ZeroFlag = null;
		}
		if (FCode.equalsIgnoreCase("NeedAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedAcc = FValue.trim();
			}
			else
				NeedAcc = null;
		}
		if (FCode.equalsIgnoreCase("PayAimClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAimClass = FValue.trim();
			}
			else
				PayAimClass = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayStartYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayStartYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayStartYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayStartYearFlag = FValue.trim();
			}
			else
				PayStartYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayStartDateCalRef = FValue.trim();
			}
			else
				PayStartDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("PayStartDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayStartDateCalMode = FValue.trim();
			}
			else
				PayStartDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestType = FValue.trim();
			}
			else
				InvestType = null;
		}
		if (FCode.equalsIgnoreCase("PCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PCalCode = FValue.trim();
			}
			else
				PCalCode = null;
		}
		if (FCode.equalsIgnoreCase("RCalPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCalPremFlag = FValue.trim();
			}
			else
				RCalPremFlag = null;
		}
		if (FCode.equalsIgnoreCase("RCalPremCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCalPremCode = FValue.trim();
			}
			else
				RCalPremCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMDutyPaySchema other = (PD_LMDutyPaySchema)otherObject;
		return
			PayPlanCode.equals(other.getPayPlanCode())
			&& DutyCode.equals(other.getDutyCode())
			&& CalCode.equals(other.getCalCode())
			&& PayPlanName.equals(other.getPayPlanName())
			&& Type.equals(other.getType())
			&& PayIntv == other.getPayIntv()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayEndYear == other.getPayEndYear()
			&& PayEndDateCalRef.equals(other.getPayEndDateCalRef())
			&& PayEndDateCalMode.equals(other.getPayEndDateCalMode())
			&& DefaultVal == other.getDefaultVal()
			&& CnterCalCode.equals(other.getCnterCalCode())
			&& OthCalCode.equals(other.getOthCalCode())
			&& Rate == other.getRate()
			&& MinPay == other.getMinPay()
			&& AssuYield == other.getAssuYield()
			&& FeeRate == other.getFeeRate()
			&& PayToDateCalMode.equals(other.getPayToDateCalMode())
			&& UrgePayFlag.equals(other.getUrgePayFlag())
			&& PayLackFlag.equals(other.getPayLackFlag())
			&& PayOverFlag.equals(other.getPayOverFlag())
			&& PayOverDeal.equals(other.getPayOverDeal())
			&& AvoidPayFlag.equals(other.getAvoidPayFlag())
			&& GracePeriod == other.getGracePeriod()
			&& PubFlag.equals(other.getPubFlag())
			&& ZeroFlag.equals(other.getZeroFlag())
			&& NeedAcc.equals(other.getNeedAcc())
			&& PayAimClass.equals(other.getPayAimClass())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6()
			&& PayStartYear == other.getPayStartYear()
			&& PayStartYearFlag.equals(other.getPayStartYearFlag())
			&& PayStartDateCalRef.equals(other.getPayStartDateCalRef())
			&& PayStartDateCalMode.equals(other.getPayStartDateCalMode())
			&& InvestType.equals(other.getInvestType())
			&& PCalCode.equals(other.getPCalCode())
			&& RCalPremFlag.equals(other.getRCalPremFlag())
			&& RCalPremCode.equals(other.getRCalPremCode());
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
		if( strFieldName.equals("PayPlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 1;
		}
		if( strFieldName.equals("CalCode") ) {
			return 2;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return 3;
		}
		if( strFieldName.equals("Type") ) {
			return 4;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 5;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 6;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 7;
		}
		if( strFieldName.equals("PayEndDateCalRef") ) {
			return 8;
		}
		if( strFieldName.equals("PayEndDateCalMode") ) {
			return 9;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return 10;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return 11;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 12;
		}
		if( strFieldName.equals("Rate") ) {
			return 13;
		}
		if( strFieldName.equals("MinPay") ) {
			return 14;
		}
		if( strFieldName.equals("AssuYield") ) {
			return 15;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 16;
		}
		if( strFieldName.equals("PayToDateCalMode") ) {
			return 17;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return 18;
		}
		if( strFieldName.equals("PayLackFlag") ) {
			return 19;
		}
		if( strFieldName.equals("PayOverFlag") ) {
			return 20;
		}
		if( strFieldName.equals("PayOverDeal") ) {
			return 21;
		}
		if( strFieldName.equals("AvoidPayFlag") ) {
			return 22;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 23;
		}
		if( strFieldName.equals("PubFlag") ) {
			return 24;
		}
		if( strFieldName.equals("ZeroFlag") ) {
			return 25;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return 26;
		}
		if( strFieldName.equals("PayAimClass") ) {
			return 27;
		}
		if( strFieldName.equals("Operator") ) {
			return 28;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 29;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 32;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 33;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 34;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 35;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 36;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 37;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 38;
		}
		if( strFieldName.equals("PayStartYear") ) {
			return 39;
		}
		if( strFieldName.equals("PayStartYearFlag") ) {
			return 40;
		}
		if( strFieldName.equals("PayStartDateCalRef") ) {
			return 41;
		}
		if( strFieldName.equals("PayStartDateCalMode") ) {
			return 42;
		}
		if( strFieldName.equals("InvestType") ) {
			return 43;
		}
		if( strFieldName.equals("PCalCode") ) {
			return 44;
		}
		if( strFieldName.equals("RCalPremFlag") ) {
			return 45;
		}
		if( strFieldName.equals("RCalPremCode") ) {
			return 46;
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
				strFieldName = "PayPlanCode";
				break;
			case 1:
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "CalCode";
				break;
			case 3:
				strFieldName = "PayPlanName";
				break;
			case 4:
				strFieldName = "Type";
				break;
			case 5:
				strFieldName = "PayIntv";
				break;
			case 6:
				strFieldName = "PayEndYearFlag";
				break;
			case 7:
				strFieldName = "PayEndYear";
				break;
			case 8:
				strFieldName = "PayEndDateCalRef";
				break;
			case 9:
				strFieldName = "PayEndDateCalMode";
				break;
			case 10:
				strFieldName = "DefaultVal";
				break;
			case 11:
				strFieldName = "CnterCalCode";
				break;
			case 12:
				strFieldName = "OthCalCode";
				break;
			case 13:
				strFieldName = "Rate";
				break;
			case 14:
				strFieldName = "MinPay";
				break;
			case 15:
				strFieldName = "AssuYield";
				break;
			case 16:
				strFieldName = "FeeRate";
				break;
			case 17:
				strFieldName = "PayToDateCalMode";
				break;
			case 18:
				strFieldName = "UrgePayFlag";
				break;
			case 19:
				strFieldName = "PayLackFlag";
				break;
			case 20:
				strFieldName = "PayOverFlag";
				break;
			case 21:
				strFieldName = "PayOverDeal";
				break;
			case 22:
				strFieldName = "AvoidPayFlag";
				break;
			case 23:
				strFieldName = "GracePeriod";
				break;
			case 24:
				strFieldName = "PubFlag";
				break;
			case 25:
				strFieldName = "ZeroFlag";
				break;
			case 26:
				strFieldName = "NeedAcc";
				break;
			case 27:
				strFieldName = "PayAimClass";
				break;
			case 28:
				strFieldName = "Operator";
				break;
			case 29:
				strFieldName = "MakeDate";
				break;
			case 30:
				strFieldName = "MakeTime";
				break;
			case 31:
				strFieldName = "ModifyDate";
				break;
			case 32:
				strFieldName = "ModifyTime";
				break;
			case 33:
				strFieldName = "Standbyflag1";
				break;
			case 34:
				strFieldName = "Standbyflag2";
				break;
			case 35:
				strFieldName = "Standbyflag3";
				break;
			case 36:
				strFieldName = "Standbyflag4";
				break;
			case 37:
				strFieldName = "Standbyflag5";
				break;
			case 38:
				strFieldName = "Standbyflag6";
				break;
			case 39:
				strFieldName = "PayStartYear";
				break;
			case 40:
				strFieldName = "PayStartYearFlag";
				break;
			case 41:
				strFieldName = "PayStartDateCalRef";
				break;
			case 42:
				strFieldName = "PayStartDateCalMode";
				break;
			case 43:
				strFieldName = "InvestType";
				break;
			case 44:
				strFieldName = "PCalCode";
				break;
			case 45:
				strFieldName = "RCalPremFlag";
				break;
			case 46:
				strFieldName = "RCalPremCode";
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
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayEndDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MinPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AssuYield") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayToDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLackFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayOverFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayOverDeal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvoidPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZeroFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAimClass") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayStartYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayStartYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayStartDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayStartDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCalPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCalPremCode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
