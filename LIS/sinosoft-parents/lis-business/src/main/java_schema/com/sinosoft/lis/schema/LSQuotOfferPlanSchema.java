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
import com.sinosoft.lis.db.LSQuotOfferPlanDB;

/*
 * <p>ClassName: LSQuotOfferPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotOfferPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotOfferPlanSchema.class);
	// @Field
	/** 报价单号 */
	private String OfferListNo;
	/** 系统方案编码 */
	private String SysPlanCode;
	/** 方案编码 */
	private String PlanCode;
	/** 方案描述 */
	private String PlanDesc;
	/** 方案类型 */
	private String PlanType;
	/** 方案标识 */
	private String PlanFlag;
	/** 保费计算方式 */
	private String PremCalType;
	/** 保险期间 */
	private int InsuPeriod;
	/** 保险期间单位 */
	private String InsuPeriodFlag;
	/** 职业类别标记 */
	private String OccupTypeFlag;
	/** 最低职业类别 */
	private String MinOccupType;
	/** 最高职业类别 */
	private String MaxOccupType;
	/** 职业类别 */
	private String OccupType;
	/** 职业中分类 */
	private String OccupMidType;
	/** 工种 */
	private String OccupCode;
	/** 最低年龄 */
	private int MinAge;
	/** 最高年龄 */
	private int MaxAge;
	/** 平均年龄 */
	private int AvgAge;
	/** 人数 */
	private int NumPeople;
	/** 参加社保占比 */
	private double SocialInsuRate;
	/** 男性比例 */
	private double MaleRate;
	/** 女性比例 */
	private double FemaleRate;
	/** 退休占比 */
	private double RetireRate;
	/** 保费分摊方式 */
	private String PremMode;
	/** 企业负担占比 */
	private double EnterpriseRate;
	/** 最低月薪 */
	private double MinSalary;
	/** 最高月薪 */
	private double MaxSalary;
	/** 平均月薪 */
	private double AvgSalary;
	/** 其他说明 */
	private String OtherDesc;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 职业比例 */
	private String OccupRate;
	/** 套餐编码 */
	private String CombiCode;
	/** 套餐费率 */
	private String CombiRate;
	/** 套餐份数 */
	private int CombiMult;

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotOfferPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "OfferListNo";
		pk[1] = "SysPlanCode";
		pk[2] = "PlanCode";

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
		LSQuotOfferPlanSchema cloned = (LSQuotOfferPlanSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getOfferListNo()
	{
		return OfferListNo;
	}
	public void setOfferListNo(String aOfferListNo)
	{
		if(aOfferListNo!=null && aOfferListNo.length()>20)
			throw new IllegalArgumentException("报价单号OfferListNo值"+aOfferListNo+"的长度"+aOfferListNo.length()+"大于最大值20");
		OfferListNo = aOfferListNo;
	}
	public String getSysPlanCode()
	{
		return SysPlanCode;
	}
	public void setSysPlanCode(String aSysPlanCode)
	{
		if(aSysPlanCode!=null && aSysPlanCode.length()>10)
			throw new IllegalArgumentException("系统方案编码SysPlanCode值"+aSysPlanCode+"的长度"+aSysPlanCode.length()+"大于最大值10");
		SysPlanCode = aSysPlanCode;
	}
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>10)
			throw new IllegalArgumentException("方案编码PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值10");
		PlanCode = aPlanCode;
	}
	public String getPlanDesc()
	{
		return PlanDesc;
	}
	public void setPlanDesc(String aPlanDesc)
	{
		if(aPlanDesc!=null && aPlanDesc.length()>30)
			throw new IllegalArgumentException("方案描述PlanDesc值"+aPlanDesc+"的长度"+aPlanDesc.length()+"大于最大值30");
		PlanDesc = aPlanDesc;
	}
	/**
	* 00-普通方案，01-公共保额，02-个人账户，03-团体账户
	*/
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		if(aPlanType!=null && aPlanType.length()>2)
			throw new IllegalArgumentException("方案类型PlanType值"+aPlanType+"的长度"+aPlanType.length()+"大于最大值2");
		PlanType = aPlanType;
	}
	/**
	* 00-核心方案，01-自选方案
	*/
	public String getPlanFlag()
	{
		return PlanFlag;
	}
	public void setPlanFlag(String aPlanFlag)
	{
		if(aPlanFlag!=null && aPlanFlag.length()>2)
			throw new IllegalArgumentException("方案标识PlanFlag值"+aPlanFlag+"的长度"+aPlanFlag.length()+"大于最大值2");
		PlanFlag = aPlanFlag;
	}
	/**
	* 1-按人数，2-按工程总造价，3-按建筑总面积
	*/
	public String getPremCalType()
	{
		return PremCalType;
	}
	public void setPremCalType(String aPremCalType)
	{
		if(aPremCalType!=null && aPremCalType.length()>2)
			throw new IllegalArgumentException("保费计算方式PremCalType值"+aPremCalType+"的长度"+aPremCalType.length()+"大于最大值2");
		PremCalType = aPremCalType;
	}
	public int getInsuPeriod()
	{
		return InsuPeriod;
	}
	public void setInsuPeriod(int aInsuPeriod)
	{
		InsuPeriod = aInsuPeriod;
	}
	public void setInsuPeriod(String aInsuPeriod)
	{
		if (aInsuPeriod != null && !aInsuPeriod.equals(""))
		{
			Integer tInteger = new Integer(aInsuPeriod);
			int i = tInteger.intValue();
			InsuPeriod = i;
		}
	}

	public String getInsuPeriodFlag()
	{
		return InsuPeriodFlag;
	}
	public void setInsuPeriodFlag(String aInsuPeriodFlag)
	{
		if(aInsuPeriodFlag!=null && aInsuPeriodFlag.length()>1)
			throw new IllegalArgumentException("保险期间单位InsuPeriodFlag值"+aInsuPeriodFlag+"的长度"+aInsuPeriodFlag.length()+"大于最大值1");
		InsuPeriodFlag = aInsuPeriodFlag;
	}
	public String getOccupTypeFlag()
	{
		return OccupTypeFlag;
	}
	public void setOccupTypeFlag(String aOccupTypeFlag)
	{
		if(aOccupTypeFlag!=null && aOccupTypeFlag.length()>2)
			throw new IllegalArgumentException("职业类别标记OccupTypeFlag值"+aOccupTypeFlag+"的长度"+aOccupTypeFlag.length()+"大于最大值2");
		OccupTypeFlag = aOccupTypeFlag;
	}
	public String getMinOccupType()
	{
		return MinOccupType;
	}
	public void setMinOccupType(String aMinOccupType)
	{
		if(aMinOccupType!=null && aMinOccupType.length()>10)
			throw new IllegalArgumentException("最低职业类别MinOccupType值"+aMinOccupType+"的长度"+aMinOccupType.length()+"大于最大值10");
		MinOccupType = aMinOccupType;
	}
	public String getMaxOccupType()
	{
		return MaxOccupType;
	}
	public void setMaxOccupType(String aMaxOccupType)
	{
		if(aMaxOccupType!=null && aMaxOccupType.length()>10)
			throw new IllegalArgumentException("最高职业类别MaxOccupType值"+aMaxOccupType+"的长度"+aMaxOccupType.length()+"大于最大值10");
		MaxOccupType = aMaxOccupType;
	}
	public String getOccupType()
	{
		return OccupType;
	}
	public void setOccupType(String aOccupType)
	{
		if(aOccupType!=null && aOccupType.length()>10)
			throw new IllegalArgumentException("职业类别OccupType值"+aOccupType+"的长度"+aOccupType.length()+"大于最大值10");
		OccupType = aOccupType;
	}
	public String getOccupMidType()
	{
		return OccupMidType;
	}
	public void setOccupMidType(String aOccupMidType)
	{
		if(aOccupMidType!=null && aOccupMidType.length()>10)
			throw new IllegalArgumentException("职业中分类OccupMidType值"+aOccupMidType+"的长度"+aOccupMidType.length()+"大于最大值10");
		OccupMidType = aOccupMidType;
	}
	public String getOccupCode()
	{
		return OccupCode;
	}
	public void setOccupCode(String aOccupCode)
	{
		if(aOccupCode!=null && aOccupCode.length()>10)
			throw new IllegalArgumentException("工种OccupCode值"+aOccupCode+"的长度"+aOccupCode.length()+"大于最大值10");
		OccupCode = aOccupCode;
	}
	public int getMinAge()
	{
		return MinAge;
	}
	public void setMinAge(int aMinAge)
	{
		MinAge = aMinAge;
	}
	public void setMinAge(String aMinAge)
	{
		if (aMinAge != null && !aMinAge.equals(""))
		{
			Integer tInteger = new Integer(aMinAge);
			int i = tInteger.intValue();
			MinAge = i;
		}
	}

	public int getMaxAge()
	{
		return MaxAge;
	}
	public void setMaxAge(int aMaxAge)
	{
		MaxAge = aMaxAge;
	}
	public void setMaxAge(String aMaxAge)
	{
		if (aMaxAge != null && !aMaxAge.equals(""))
		{
			Integer tInteger = new Integer(aMaxAge);
			int i = tInteger.intValue();
			MaxAge = i;
		}
	}

	public int getAvgAge()
	{
		return AvgAge;
	}
	public void setAvgAge(int aAvgAge)
	{
		AvgAge = aAvgAge;
	}
	public void setAvgAge(String aAvgAge)
	{
		if (aAvgAge != null && !aAvgAge.equals(""))
		{
			Integer tInteger = new Integer(aAvgAge);
			int i = tInteger.intValue();
			AvgAge = i;
		}
	}

	public int getNumPeople()
	{
		return NumPeople;
	}
	public void setNumPeople(int aNumPeople)
	{
		NumPeople = aNumPeople;
	}
	public void setNumPeople(String aNumPeople)
	{
		if (aNumPeople != null && !aNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aNumPeople);
			int i = tInteger.intValue();
			NumPeople = i;
		}
	}

	public double getSocialInsuRate()
	{
		return SocialInsuRate;
	}
	public void setSocialInsuRate(double aSocialInsuRate)
	{
		SocialInsuRate = aSocialInsuRate;
	}
	public void setSocialInsuRate(String aSocialInsuRate)
	{
		if (aSocialInsuRate != null && !aSocialInsuRate.equals(""))
		{
			Double tDouble = new Double(aSocialInsuRate);
			double d = tDouble.doubleValue();
			SocialInsuRate = d;
		}
	}

	public double getMaleRate()
	{
		return MaleRate;
	}
	public void setMaleRate(double aMaleRate)
	{
		MaleRate = aMaleRate;
	}
	public void setMaleRate(String aMaleRate)
	{
		if (aMaleRate != null && !aMaleRate.equals(""))
		{
			Double tDouble = new Double(aMaleRate);
			double d = tDouble.doubleValue();
			MaleRate = d;
		}
	}

	public double getFemaleRate()
	{
		return FemaleRate;
	}
	public void setFemaleRate(double aFemaleRate)
	{
		FemaleRate = aFemaleRate;
	}
	public void setFemaleRate(String aFemaleRate)
	{
		if (aFemaleRate != null && !aFemaleRate.equals(""))
		{
			Double tDouble = new Double(aFemaleRate);
			double d = tDouble.doubleValue();
			FemaleRate = d;
		}
	}

	public double getRetireRate()
	{
		return RetireRate;
	}
	public void setRetireRate(double aRetireRate)
	{
		RetireRate = aRetireRate;
	}
	public void setRetireRate(String aRetireRate)
	{
		if (aRetireRate != null && !aRetireRate.equals(""))
		{
			Double tDouble = new Double(aRetireRate);
			double d = tDouble.doubleValue();
			RetireRate = d;
		}
	}

	public String getPremMode()
	{
		return PremMode;
	}
	public void setPremMode(String aPremMode)
	{
		if(aPremMode!=null && aPremMode.length()>2)
			throw new IllegalArgumentException("保费分摊方式PremMode值"+aPremMode+"的长度"+aPremMode.length()+"大于最大值2");
		PremMode = aPremMode;
	}
	public double getEnterpriseRate()
	{
		return EnterpriseRate;
	}
	public void setEnterpriseRate(double aEnterpriseRate)
	{
		EnterpriseRate = aEnterpriseRate;
	}
	public void setEnterpriseRate(String aEnterpriseRate)
	{
		if (aEnterpriseRate != null && !aEnterpriseRate.equals(""))
		{
			Double tDouble = new Double(aEnterpriseRate);
			double d = tDouble.doubleValue();
			EnterpriseRate = d;
		}
	}

	public double getMinSalary()
	{
		return MinSalary;
	}
	public void setMinSalary(double aMinSalary)
	{
		MinSalary = aMinSalary;
	}
	public void setMinSalary(String aMinSalary)
	{
		if (aMinSalary != null && !aMinSalary.equals(""))
		{
			Double tDouble = new Double(aMinSalary);
			double d = tDouble.doubleValue();
			MinSalary = d;
		}
	}

	public double getMaxSalary()
	{
		return MaxSalary;
	}
	public void setMaxSalary(double aMaxSalary)
	{
		MaxSalary = aMaxSalary;
	}
	public void setMaxSalary(String aMaxSalary)
	{
		if (aMaxSalary != null && !aMaxSalary.equals(""))
		{
			Double tDouble = new Double(aMaxSalary);
			double d = tDouble.doubleValue();
			MaxSalary = d;
		}
	}

	public double getAvgSalary()
	{
		return AvgSalary;
	}
	public void setAvgSalary(double aAvgSalary)
	{
		AvgSalary = aAvgSalary;
	}
	public void setAvgSalary(String aAvgSalary)
	{
		if (aAvgSalary != null && !aAvgSalary.equals(""))
		{
			Double tDouble = new Double(aAvgSalary);
			double d = tDouble.doubleValue();
			AvgSalary = d;
		}
	}

	public String getOtherDesc()
	{
		return OtherDesc;
	}
	public void setOtherDesc(String aOtherDesc)
	{
		if(aOtherDesc!=null && aOtherDesc.length()>3000)
			throw new IllegalArgumentException("其他说明OtherDesc值"+aOtherDesc+"的长度"+aOtherDesc.length()+"大于最大值3000");
		OtherDesc = aOtherDesc;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getOccupRate()
	{
		return OccupRate;
	}
	public void setOccupRate(String aOccupRate)
	{
		if(aOccupRate!=null && aOccupRate.length()>40)
			throw new IllegalArgumentException("职业比例OccupRate值"+aOccupRate+"的长度"+aOccupRate.length()+"大于最大值40");
		OccupRate = aOccupRate;
	}
	public String getCombiCode()
	{
		return CombiCode;
	}
	public void setCombiCode(String aCombiCode)
	{
		if(aCombiCode!=null && aCombiCode.length()>10)
			throw new IllegalArgumentException("套餐编码CombiCode值"+aCombiCode+"的长度"+aCombiCode.length()+"大于最大值10");
		CombiCode = aCombiCode;
	}
	public String getCombiRate()
	{
		return CombiRate;
	}
	public void setCombiRate(String aCombiRate)
	{
		if(aCombiRate!=null && aCombiRate.length()>20)
			throw new IllegalArgumentException("套餐费率CombiRate值"+aCombiRate+"的长度"+aCombiRate.length()+"大于最大值20");
		CombiRate = aCombiRate;
	}
	public int getCombiMult()
	{
		return CombiMult;
	}
	public void setCombiMult(int aCombiMult)
	{
		CombiMult = aCombiMult;
	}
	public void setCombiMult(String aCombiMult)
	{
		if (aCombiMult != null && !aCombiMult.equals(""))
		{
			Integer tInteger = new Integer(aCombiMult);
			int i = tInteger.intValue();
			CombiMult = i;
		}
	}


	/**
	* 使用另外一个 LSQuotOfferPlanSchema 对象给 Schema 赋值
	* @param: aLSQuotOfferPlanSchema LSQuotOfferPlanSchema
	**/
	public void setSchema(LSQuotOfferPlanSchema aLSQuotOfferPlanSchema)
	{
		this.OfferListNo = aLSQuotOfferPlanSchema.getOfferListNo();
		this.SysPlanCode = aLSQuotOfferPlanSchema.getSysPlanCode();
		this.PlanCode = aLSQuotOfferPlanSchema.getPlanCode();
		this.PlanDesc = aLSQuotOfferPlanSchema.getPlanDesc();
		this.PlanType = aLSQuotOfferPlanSchema.getPlanType();
		this.PlanFlag = aLSQuotOfferPlanSchema.getPlanFlag();
		this.PremCalType = aLSQuotOfferPlanSchema.getPremCalType();
		this.InsuPeriod = aLSQuotOfferPlanSchema.getInsuPeriod();
		this.InsuPeriodFlag = aLSQuotOfferPlanSchema.getInsuPeriodFlag();
		this.OccupTypeFlag = aLSQuotOfferPlanSchema.getOccupTypeFlag();
		this.MinOccupType = aLSQuotOfferPlanSchema.getMinOccupType();
		this.MaxOccupType = aLSQuotOfferPlanSchema.getMaxOccupType();
		this.OccupType = aLSQuotOfferPlanSchema.getOccupType();
		this.OccupMidType = aLSQuotOfferPlanSchema.getOccupMidType();
		this.OccupCode = aLSQuotOfferPlanSchema.getOccupCode();
		this.MinAge = aLSQuotOfferPlanSchema.getMinAge();
		this.MaxAge = aLSQuotOfferPlanSchema.getMaxAge();
		this.AvgAge = aLSQuotOfferPlanSchema.getAvgAge();
		this.NumPeople = aLSQuotOfferPlanSchema.getNumPeople();
		this.SocialInsuRate = aLSQuotOfferPlanSchema.getSocialInsuRate();
		this.MaleRate = aLSQuotOfferPlanSchema.getMaleRate();
		this.FemaleRate = aLSQuotOfferPlanSchema.getFemaleRate();
		this.RetireRate = aLSQuotOfferPlanSchema.getRetireRate();
		this.PremMode = aLSQuotOfferPlanSchema.getPremMode();
		this.EnterpriseRate = aLSQuotOfferPlanSchema.getEnterpriseRate();
		this.MinSalary = aLSQuotOfferPlanSchema.getMinSalary();
		this.MaxSalary = aLSQuotOfferPlanSchema.getMaxSalary();
		this.AvgSalary = aLSQuotOfferPlanSchema.getAvgSalary();
		this.OtherDesc = aLSQuotOfferPlanSchema.getOtherDesc();
		this.MakeOperator = aLSQuotOfferPlanSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotOfferPlanSchema.getMakeDate());
		this.MakeTime = aLSQuotOfferPlanSchema.getMakeTime();
		this.ModifyOperator = aLSQuotOfferPlanSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotOfferPlanSchema.getModifyDate());
		this.ModifyTime = aLSQuotOfferPlanSchema.getModifyTime();
		this.OccupRate = aLSQuotOfferPlanSchema.getOccupRate();
		this.CombiCode = aLSQuotOfferPlanSchema.getCombiCode();
		this.CombiRate = aLSQuotOfferPlanSchema.getCombiRate();
		this.CombiMult = aLSQuotOfferPlanSchema.getCombiMult();
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
			if( rs.getString("OfferListNo") == null )
				this.OfferListNo = null;
			else
				this.OfferListNo = rs.getString("OfferListNo").trim();

			if( rs.getString("SysPlanCode") == null )
				this.SysPlanCode = null;
			else
				this.SysPlanCode = rs.getString("SysPlanCode").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("PlanDesc") == null )
				this.PlanDesc = null;
			else
				this.PlanDesc = rs.getString("PlanDesc").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			if( rs.getString("PlanFlag") == null )
				this.PlanFlag = null;
			else
				this.PlanFlag = rs.getString("PlanFlag").trim();

			if( rs.getString("PremCalType") == null )
				this.PremCalType = null;
			else
				this.PremCalType = rs.getString("PremCalType").trim();

			this.InsuPeriod = rs.getInt("InsuPeriod");
			if( rs.getString("InsuPeriodFlag") == null )
				this.InsuPeriodFlag = null;
			else
				this.InsuPeriodFlag = rs.getString("InsuPeriodFlag").trim();

			if( rs.getString("OccupTypeFlag") == null )
				this.OccupTypeFlag = null;
			else
				this.OccupTypeFlag = rs.getString("OccupTypeFlag").trim();

			if( rs.getString("MinOccupType") == null )
				this.MinOccupType = null;
			else
				this.MinOccupType = rs.getString("MinOccupType").trim();

			if( rs.getString("MaxOccupType") == null )
				this.MaxOccupType = null;
			else
				this.MaxOccupType = rs.getString("MaxOccupType").trim();

			if( rs.getString("OccupType") == null )
				this.OccupType = null;
			else
				this.OccupType = rs.getString("OccupType").trim();

			if( rs.getString("OccupMidType") == null )
				this.OccupMidType = null;
			else
				this.OccupMidType = rs.getString("OccupMidType").trim();

			if( rs.getString("OccupCode") == null )
				this.OccupCode = null;
			else
				this.OccupCode = rs.getString("OccupCode").trim();

			this.MinAge = rs.getInt("MinAge");
			this.MaxAge = rs.getInt("MaxAge");
			this.AvgAge = rs.getInt("AvgAge");
			this.NumPeople = rs.getInt("NumPeople");
			this.SocialInsuRate = rs.getDouble("SocialInsuRate");
			this.MaleRate = rs.getDouble("MaleRate");
			this.FemaleRate = rs.getDouble("FemaleRate");
			this.RetireRate = rs.getDouble("RetireRate");
			if( rs.getString("PremMode") == null )
				this.PremMode = null;
			else
				this.PremMode = rs.getString("PremMode").trim();

			this.EnterpriseRate = rs.getDouble("EnterpriseRate");
			this.MinSalary = rs.getDouble("MinSalary");
			this.MaxSalary = rs.getDouble("MaxSalary");
			this.AvgSalary = rs.getDouble("AvgSalary");
			if( rs.getString("OtherDesc") == null )
				this.OtherDesc = null;
			else
				this.OtherDesc = rs.getString("OtherDesc").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("OccupRate") == null )
				this.OccupRate = null;
			else
				this.OccupRate = rs.getString("OccupRate").trim();

			if( rs.getString("CombiCode") == null )
				this.CombiCode = null;
			else
				this.CombiCode = rs.getString("CombiCode").trim();

			if( rs.getString("CombiRate") == null )
				this.CombiRate = null;
			else
				this.CombiRate = rs.getString("CombiRate").trim();

			this.CombiMult = rs.getInt("CombiMult");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LSQuotOfferPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotOfferPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotOfferPlanSchema getSchema()
	{
		LSQuotOfferPlanSchema aLSQuotOfferPlanSchema = new LSQuotOfferPlanSchema();
		aLSQuotOfferPlanSchema.setSchema(this);
		return aLSQuotOfferPlanSchema;
	}

	public LSQuotOfferPlanDB getDB()
	{
		LSQuotOfferPlanDB aDBOper = new LSQuotOfferPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotOfferPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OfferListNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupTypeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MinOccupType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MaxOccupType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupMidType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AvgAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SocialInsuRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaleRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FemaleRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RetireRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnterpriseRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinSalary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxSalary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AvgSalary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CombiCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CombiRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CombiMult));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotOfferPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OfferListNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PlanDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PlanFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PremCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsuPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			InsuPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OccupTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MinOccupType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MaxOccupType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OccupType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			OccupMidType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			OccupCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MinAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			MaxAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			AvgAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			NumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			SocialInsuRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			MaleRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			FemaleRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			RetireRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			PremMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			EnterpriseRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			MinSalary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			MaxSalary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			AvgSalary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			OtherDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			OccupRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			CombiCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			CombiRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			CombiMult= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotOfferPlanSchema";
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
		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferListNo));
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanDesc));
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equalsIgnoreCase("PlanFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanFlag));
		}
		if (FCode.equalsIgnoreCase("PremCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremCalType));
		}
		if (FCode.equalsIgnoreCase("InsuPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriod));
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("OccupTypeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupTypeFlag));
		}
		if (FCode.equalsIgnoreCase("MinOccupType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinOccupType));
		}
		if (FCode.equalsIgnoreCase("MaxOccupType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxOccupType));
		}
		if (FCode.equalsIgnoreCase("OccupType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupType));
		}
		if (FCode.equalsIgnoreCase("OccupMidType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupMidType));
		}
		if (FCode.equalsIgnoreCase("OccupCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupCode));
		}
		if (FCode.equalsIgnoreCase("MinAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinAge));
		}
		if (FCode.equalsIgnoreCase("MaxAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAge));
		}
		if (FCode.equalsIgnoreCase("AvgAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvgAge));
		}
		if (FCode.equalsIgnoreCase("NumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumPeople));
		}
		if (FCode.equalsIgnoreCase("SocialInsuRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuRate));
		}
		if (FCode.equalsIgnoreCase("MaleRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaleRate));
		}
		if (FCode.equalsIgnoreCase("FemaleRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FemaleRate));
		}
		if (FCode.equalsIgnoreCase("RetireRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetireRate));
		}
		if (FCode.equalsIgnoreCase("PremMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremMode));
		}
		if (FCode.equalsIgnoreCase("EnterpriseRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnterpriseRate));
		}
		if (FCode.equalsIgnoreCase("MinSalary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinSalary));
		}
		if (FCode.equalsIgnoreCase("MaxSalary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxSalary));
		}
		if (FCode.equalsIgnoreCase("AvgSalary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvgSalary));
		}
		if (FCode.equalsIgnoreCase("OtherDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherDesc));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("OccupRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupRate));
		}
		if (FCode.equalsIgnoreCase("CombiCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CombiCode));
		}
		if (FCode.equalsIgnoreCase("CombiRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CombiRate));
		}
		if (FCode.equalsIgnoreCase("CombiMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CombiMult));
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
				strFieldValue = StrTool.GBKToUnicode(OfferListNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PlanDesc);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PlanFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PremCalType);
				break;
			case 7:
				strFieldValue = String.valueOf(InsuPeriod);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InsuPeriodFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OccupTypeFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MinOccupType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MaxOccupType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OccupType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(OccupMidType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(OccupCode);
				break;
			case 15:
				strFieldValue = String.valueOf(MinAge);
				break;
			case 16:
				strFieldValue = String.valueOf(MaxAge);
				break;
			case 17:
				strFieldValue = String.valueOf(AvgAge);
				break;
			case 18:
				strFieldValue = String.valueOf(NumPeople);
				break;
			case 19:
				strFieldValue = String.valueOf(SocialInsuRate);
				break;
			case 20:
				strFieldValue = String.valueOf(MaleRate);
				break;
			case 21:
				strFieldValue = String.valueOf(FemaleRate);
				break;
			case 22:
				strFieldValue = String.valueOf(RetireRate);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(PremMode);
				break;
			case 24:
				strFieldValue = String.valueOf(EnterpriseRate);
				break;
			case 25:
				strFieldValue = String.valueOf(MinSalary);
				break;
			case 26:
				strFieldValue = String.valueOf(MaxSalary);
				break;
			case 27:
				strFieldValue = String.valueOf(AvgSalary);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(OtherDesc);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OccupRate);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(CombiCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(CombiRate);
				break;
			case 38:
				strFieldValue = String.valueOf(CombiMult);
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

		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OfferListNo = FValue.trim();
			}
			else
				OfferListNo = null;
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysPlanCode = FValue.trim();
			}
			else
				SysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanDesc = FValue.trim();
			}
			else
				PlanDesc = null;
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
		if (FCode.equalsIgnoreCase("PlanFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanFlag = FValue.trim();
			}
			else
				PlanFlag = null;
		}
		if (FCode.equalsIgnoreCase("PremCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremCalType = FValue.trim();
			}
			else
				PremCalType = null;
		}
		if (FCode.equalsIgnoreCase("InsuPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuPeriodFlag = FValue.trim();
			}
			else
				InsuPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("OccupTypeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupTypeFlag = FValue.trim();
			}
			else
				OccupTypeFlag = null;
		}
		if (FCode.equalsIgnoreCase("MinOccupType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MinOccupType = FValue.trim();
			}
			else
				MinOccupType = null;
		}
		if (FCode.equalsIgnoreCase("MaxOccupType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MaxOccupType = FValue.trim();
			}
			else
				MaxOccupType = null;
		}
		if (FCode.equalsIgnoreCase("OccupType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupType = FValue.trim();
			}
			else
				OccupType = null;
		}
		if (FCode.equalsIgnoreCase("OccupMidType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupMidType = FValue.trim();
			}
			else
				OccupMidType = null;
		}
		if (FCode.equalsIgnoreCase("OccupCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupCode = FValue.trim();
			}
			else
				OccupCode = null;
		}
		if (FCode.equalsIgnoreCase("MinAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("AvgAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AvgAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("NumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("SocialInsuRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SocialInsuRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaleRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaleRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FemaleRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FemaleRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RetireRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RetireRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremMode = FValue.trim();
			}
			else
				PremMode = null;
		}
		if (FCode.equalsIgnoreCase("EnterpriseRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnterpriseRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MinSalary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MinSalary = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxSalary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxSalary = d;
			}
		}
		if (FCode.equalsIgnoreCase("AvgSalary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AvgSalary = d;
			}
		}
		if (FCode.equalsIgnoreCase("OtherDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherDesc = FValue.trim();
			}
			else
				OtherDesc = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		if (FCode.equalsIgnoreCase("OccupRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupRate = FValue.trim();
			}
			else
				OccupRate = null;
		}
		if (FCode.equalsIgnoreCase("CombiCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CombiCode = FValue.trim();
			}
			else
				CombiCode = null;
		}
		if (FCode.equalsIgnoreCase("CombiRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CombiRate = FValue.trim();
			}
			else
				CombiRate = null;
		}
		if (FCode.equalsIgnoreCase("CombiMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CombiMult = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LSQuotOfferPlanSchema other = (LSQuotOfferPlanSchema)otherObject;
		return
			OfferListNo.equals(other.getOfferListNo())
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& PlanDesc.equals(other.getPlanDesc())
			&& PlanType.equals(other.getPlanType())
			&& PlanFlag.equals(other.getPlanFlag())
			&& PremCalType.equals(other.getPremCalType())
			&& InsuPeriod == other.getInsuPeriod()
			&& InsuPeriodFlag.equals(other.getInsuPeriodFlag())
			&& OccupTypeFlag.equals(other.getOccupTypeFlag())
			&& MinOccupType.equals(other.getMinOccupType())
			&& MaxOccupType.equals(other.getMaxOccupType())
			&& OccupType.equals(other.getOccupType())
			&& OccupMidType.equals(other.getOccupMidType())
			&& OccupCode.equals(other.getOccupCode())
			&& MinAge == other.getMinAge()
			&& MaxAge == other.getMaxAge()
			&& AvgAge == other.getAvgAge()
			&& NumPeople == other.getNumPeople()
			&& SocialInsuRate == other.getSocialInsuRate()
			&& MaleRate == other.getMaleRate()
			&& FemaleRate == other.getFemaleRate()
			&& RetireRate == other.getRetireRate()
			&& PremMode.equals(other.getPremMode())
			&& EnterpriseRate == other.getEnterpriseRate()
			&& MinSalary == other.getMinSalary()
			&& MaxSalary == other.getMaxSalary()
			&& AvgSalary == other.getAvgSalary()
			&& OtherDesc.equals(other.getOtherDesc())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& OccupRate.equals(other.getOccupRate())
			&& CombiCode.equals(other.getCombiCode())
			&& CombiRate.equals(other.getCombiRate())
			&& CombiMult == other.getCombiMult();
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
		if( strFieldName.equals("OfferListNo") ) {
			return 0;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("PlanDesc") ) {
			return 3;
		}
		if( strFieldName.equals("PlanType") ) {
			return 4;
		}
		if( strFieldName.equals("PlanFlag") ) {
			return 5;
		}
		if( strFieldName.equals("PremCalType") ) {
			return 6;
		}
		if( strFieldName.equals("InsuPeriod") ) {
			return 7;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return 8;
		}
		if( strFieldName.equals("OccupTypeFlag") ) {
			return 9;
		}
		if( strFieldName.equals("MinOccupType") ) {
			return 10;
		}
		if( strFieldName.equals("MaxOccupType") ) {
			return 11;
		}
		if( strFieldName.equals("OccupType") ) {
			return 12;
		}
		if( strFieldName.equals("OccupMidType") ) {
			return 13;
		}
		if( strFieldName.equals("OccupCode") ) {
			return 14;
		}
		if( strFieldName.equals("MinAge") ) {
			return 15;
		}
		if( strFieldName.equals("MaxAge") ) {
			return 16;
		}
		if( strFieldName.equals("AvgAge") ) {
			return 17;
		}
		if( strFieldName.equals("NumPeople") ) {
			return 18;
		}
		if( strFieldName.equals("SocialInsuRate") ) {
			return 19;
		}
		if( strFieldName.equals("MaleRate") ) {
			return 20;
		}
		if( strFieldName.equals("FemaleRate") ) {
			return 21;
		}
		if( strFieldName.equals("RetireRate") ) {
			return 22;
		}
		if( strFieldName.equals("PremMode") ) {
			return 23;
		}
		if( strFieldName.equals("EnterpriseRate") ) {
			return 24;
		}
		if( strFieldName.equals("MinSalary") ) {
			return 25;
		}
		if( strFieldName.equals("MaxSalary") ) {
			return 26;
		}
		if( strFieldName.equals("AvgSalary") ) {
			return 27;
		}
		if( strFieldName.equals("OtherDesc") ) {
			return 28;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 29;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 30;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 34;
		}
		if( strFieldName.equals("OccupRate") ) {
			return 35;
		}
		if( strFieldName.equals("CombiCode") ) {
			return 36;
		}
		if( strFieldName.equals("CombiRate") ) {
			return 37;
		}
		if( strFieldName.equals("CombiMult") ) {
			return 38;
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
				strFieldName = "OfferListNo";
				break;
			case 1:
				strFieldName = "SysPlanCode";
				break;
			case 2:
				strFieldName = "PlanCode";
				break;
			case 3:
				strFieldName = "PlanDesc";
				break;
			case 4:
				strFieldName = "PlanType";
				break;
			case 5:
				strFieldName = "PlanFlag";
				break;
			case 6:
				strFieldName = "PremCalType";
				break;
			case 7:
				strFieldName = "InsuPeriod";
				break;
			case 8:
				strFieldName = "InsuPeriodFlag";
				break;
			case 9:
				strFieldName = "OccupTypeFlag";
				break;
			case 10:
				strFieldName = "MinOccupType";
				break;
			case 11:
				strFieldName = "MaxOccupType";
				break;
			case 12:
				strFieldName = "OccupType";
				break;
			case 13:
				strFieldName = "OccupMidType";
				break;
			case 14:
				strFieldName = "OccupCode";
				break;
			case 15:
				strFieldName = "MinAge";
				break;
			case 16:
				strFieldName = "MaxAge";
				break;
			case 17:
				strFieldName = "AvgAge";
				break;
			case 18:
				strFieldName = "NumPeople";
				break;
			case 19:
				strFieldName = "SocialInsuRate";
				break;
			case 20:
				strFieldName = "MaleRate";
				break;
			case 21:
				strFieldName = "FemaleRate";
				break;
			case 22:
				strFieldName = "RetireRate";
				break;
			case 23:
				strFieldName = "PremMode";
				break;
			case 24:
				strFieldName = "EnterpriseRate";
				break;
			case 25:
				strFieldName = "MinSalary";
				break;
			case 26:
				strFieldName = "MaxSalary";
				break;
			case 27:
				strFieldName = "AvgSalary";
				break;
			case 28:
				strFieldName = "OtherDesc";
				break;
			case 29:
				strFieldName = "MakeOperator";
				break;
			case 30:
				strFieldName = "MakeDate";
				break;
			case 31:
				strFieldName = "MakeTime";
				break;
			case 32:
				strFieldName = "ModifyOperator";
				break;
			case 33:
				strFieldName = "ModifyDate";
				break;
			case 34:
				strFieldName = "ModifyTime";
				break;
			case 35:
				strFieldName = "OccupRate";
				break;
			case 36:
				strFieldName = "CombiCode";
				break;
			case 37:
				strFieldName = "CombiRate";
				break;
			case 38:
				strFieldName = "CombiMult";
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
		if( strFieldName.equals("OfferListNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupTypeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinOccupType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxOccupType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupMidType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AvgAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SocialInsuRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaleRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FemaleRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RetireRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnterpriseRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MinSalary") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxSalary") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AvgSalary") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OtherDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CombiCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CombiRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CombiMult") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
