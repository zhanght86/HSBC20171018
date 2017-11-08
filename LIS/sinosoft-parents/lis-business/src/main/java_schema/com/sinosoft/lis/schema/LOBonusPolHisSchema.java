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
import com.sinosoft.lis.db.LOBonusPolHisDB;

/*
 * <p>ClassName: LOBonusPolHisSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LOBonusPolHisSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBonusPolHisSchema.class);
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 管理机构 */
	private String ManageCom;
	/** 保单险种号码 */
	private String PolNo;
	/** 会计年度 */
	private int FiscalYear;
	/** 序列号 */
	private String SerialNo;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 对应其它号码 */
	private String OtherNo;
	/** 对应其它号码类型 */
	private String OtherType;
	/** 账户归属属性 */
	private String AccAscription;
	/** 险种编码 */
	private String RiskCode;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 账户类型 */
	private String AccType;
	/** 账户结算方式 */
	private String AccComputeFlag;
	/** 账户分红日期 */
	private Date BonusDate;
	/** 账户分红次数 */
	private int BonusInt;
	/** 红利金领取方式 */
	private String BonusGetMode;
	/** 上期余额 */
	private double LastBala;
	/** 上期单位数 */
	private double LastStock;
	/** 本期分红利率 */
	private double BonusRate;
	/** 本期利率 */
	private double InterestRate;
	/** 本期红利 */
	private double SumBonus;
	/** 本期利息 */
	private double SumInterest;
	/** 当前余额 */
	private double InsuAccBala;
	/** 当前单位数 */
	private double InsuAccStock;
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
	/** 保全受理号 */
	private String EdorNo;
	/** 备用1 */
	private String StandbyFlag1;
	/** 备用2 */
	private String StandbyFlag2;
	/** 备用3 */
	private String StandbyFlag3;

	public static final int FIELDNUM = 37;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBonusPolHisSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LOBonusPolHisSchema cloned = (LOBonusPolHisSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单险种号码PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public int getFiscalYear()
	{
		return FiscalYear;
	}
	public void setFiscalYear(int aFiscalYear)
	{
		FiscalYear = aFiscalYear;
	}
	public void setFiscalYear(String aFiscalYear)
	{
		if (aFiscalYear != null && !aFiscalYear.equals(""))
		{
			Integer tInteger = new Integer(aFiscalYear);
			int i = tInteger.intValue();
			FiscalYear = i;
		}
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("序列号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		if(aInsuAccNo!=null && aInsuAccNo.length()>20)
			throw new IllegalArgumentException("保险帐户号码InsuAccNo值"+aInsuAccNo+"的长度"+aInsuAccNo.length()+"大于最大值20");
		InsuAccNo = aInsuAccNo;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		if(aPayPlanCode!=null && aPayPlanCode.length()>8)
			throw new IllegalArgumentException("交费计划编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值8");
		PayPlanCode = aPayPlanCode;
	}
	/**
	* 对于每次交费都产生新账号的情况，该字段存放交费号。
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("对应其它号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	/**
	* 1 －－ 个人保单号 2 －－ 缴费收据号 3 －－ 集体保单号(对于集体公共账户对应的集体保单号码)
	*/
	public String getOtherType()
	{
		return OtherType;
	}
	public void setOtherType(String aOtherType)
	{
		if(aOtherType!=null && aOtherType.length()>2)
			throw new IllegalArgumentException("对应其它号码类型OtherType值"+aOtherType+"的长度"+aOtherType.length()+"大于最大值2");
		OtherType = aOtherType;
	}
	/**
	* 0-未归属 1-已归属
	*/
	public String getAccAscription()
	{
		return AccAscription;
	}
	public void setAccAscription(String aAccAscription)
	{
		if(aAccAscription!=null && aAccAscription.length()>1)
			throw new IllegalArgumentException("账户归属属性AccAscription值"+aAccAscription+"的长度"+aAccAscription.length()+"大于最大值1");
		AccAscription = aAccAscription;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号码InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>24)
			throw new IllegalArgumentException("投保人客户号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值24");
		AppntNo = aAppntNo;
	}
	/**
	* 001 -- 集体公共账户 002 -- 个人缴费账户 003 -- 个人累积生息账户 004 -- 个人红利账户
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		if(aAccType!=null && aAccType.length()>3)
			throw new IllegalArgumentException("账户类型AccType值"+aAccType+"的长度"+aAccType.length()+"大于最大值3");
		AccType = aAccType;
	}
	/**
	* 0 －－ 不计利息 1 －－ 按固定利率生息（单利） 2 －－ 按固定利率生息（复利） 3 －－ 按利率表生息（单利） 4 －－ 按利率表生息（复利）
	*/
	public String getAccComputeFlag()
	{
		return AccComputeFlag;
	}
	public void setAccComputeFlag(String aAccComputeFlag)
	{
		if(aAccComputeFlag!=null && aAccComputeFlag.length()>3)
			throw new IllegalArgumentException("账户结算方式AccComputeFlag值"+aAccComputeFlag+"的长度"+aAccComputeFlag.length()+"大于最大值3");
		AccComputeFlag = aAccComputeFlag;
	}
	public String getBonusDate()
	{
		if( BonusDate != null )
			return fDate.getString(BonusDate);
		else
			return null;
	}
	public void setBonusDate(Date aBonusDate)
	{
		BonusDate = aBonusDate;
	}
	public void setBonusDate(String aBonusDate)
	{
		if (aBonusDate != null && !aBonusDate.equals("") )
		{
			BonusDate = fDate.getDate( aBonusDate );
		}
		else
			BonusDate = null;
	}

	public int getBonusInt()
	{
		return BonusInt;
	}
	public void setBonusInt(int aBonusInt)
	{
		BonusInt = aBonusInt;
	}
	public void setBonusInt(String aBonusInt)
	{
		if (aBonusInt != null && !aBonusInt.equals(""))
		{
			Integer tInteger = new Integer(aBonusInt);
			int i = tInteger.intValue();
			BonusInt = i;
		}
	}

	/**
	* 1--累积生息 2--领取现金 3--抵缴保费 4--其他 5--增额交清
	*/
	public String getBonusGetMode()
	{
		return BonusGetMode;
	}
	public void setBonusGetMode(String aBonusGetMode)
	{
		if(aBonusGetMode!=null && aBonusGetMode.length()>1)
			throw new IllegalArgumentException("红利金领取方式BonusGetMode值"+aBonusGetMode+"的长度"+aBonusGetMode.length()+"大于最大值1");
		BonusGetMode = aBonusGetMode;
	}
	public double getLastBala()
	{
		return LastBala;
	}
	public void setLastBala(double aLastBala)
	{
		LastBala = aLastBala;
	}
	public void setLastBala(String aLastBala)
	{
		if (aLastBala != null && !aLastBala.equals(""))
		{
			Double tDouble = new Double(aLastBala);
			double d = tDouble.doubleValue();
			LastBala = d;
		}
	}

	public double getLastStock()
	{
		return LastStock;
	}
	public void setLastStock(double aLastStock)
	{
		LastStock = aLastStock;
	}
	public void setLastStock(String aLastStock)
	{
		if (aLastStock != null && !aLastStock.equals(""))
		{
			Double tDouble = new Double(aLastStock);
			double d = tDouble.doubleValue();
			LastStock = d;
		}
	}

	public double getBonusRate()
	{
		return BonusRate;
	}
	public void setBonusRate(double aBonusRate)
	{
		BonusRate = aBonusRate;
	}
	public void setBonusRate(String aBonusRate)
	{
		if (aBonusRate != null && !aBonusRate.equals(""))
		{
			Double tDouble = new Double(aBonusRate);
			double d = tDouble.doubleValue();
			BonusRate = d;
		}
	}

	public double getInterestRate()
	{
		return InterestRate;
	}
	public void setInterestRate(double aInterestRate)
	{
		InterestRate = aInterestRate;
	}
	public void setInterestRate(String aInterestRate)
	{
		if (aInterestRate != null && !aInterestRate.equals(""))
		{
			Double tDouble = new Double(aInterestRate);
			double d = tDouble.doubleValue();
			InterestRate = d;
		}
	}

	public double getSumBonus()
	{
		return SumBonus;
	}
	public void setSumBonus(double aSumBonus)
	{
		SumBonus = aSumBonus;
	}
	public void setSumBonus(String aSumBonus)
	{
		if (aSumBonus != null && !aSumBonus.equals(""))
		{
			Double tDouble = new Double(aSumBonus);
			double d = tDouble.doubleValue();
			SumBonus = d;
		}
	}

	public double getSumInterest()
	{
		return SumInterest;
	}
	public void setSumInterest(double aSumInterest)
	{
		SumInterest = aSumInterest;
	}
	public void setSumInterest(String aSumInterest)
	{
		if (aSumInterest != null && !aSumInterest.equals(""))
		{
			Double tDouble = new Double(aSumInterest);
			double d = tDouble.doubleValue();
			SumInterest = d;
		}
	}

	public double getInsuAccBala()
	{
		return InsuAccBala;
	}
	public void setInsuAccBala(double aInsuAccBala)
	{
		InsuAccBala = aInsuAccBala;
	}
	public void setInsuAccBala(String aInsuAccBala)
	{
		if (aInsuAccBala != null && !aInsuAccBala.equals(""))
		{
			Double tDouble = new Double(aInsuAccBala);
			double d = tDouble.doubleValue();
			InsuAccBala = d;
		}
	}

	public double getInsuAccStock()
	{
		return InsuAccStock;
	}
	public void setInsuAccStock(double aInsuAccStock)
	{
		InsuAccStock = aInsuAccStock;
	}
	public void setInsuAccStock(String aInsuAccStock)
	{
		if (aInsuAccStock != null && !aInsuAccStock.equals(""))
		{
			Double tDouble = new Double(aInsuAccStock);
			double d = tDouble.doubleValue();
			InsuAccStock = d;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("保全受理号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>20)
			throw new IllegalArgumentException("备用1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值20");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>1)
			throw new IllegalArgumentException("备用2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值1");
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		if(aStandbyFlag3!=null && aStandbyFlag3.length()>1)
			throw new IllegalArgumentException("备用3StandbyFlag3值"+aStandbyFlag3+"的长度"+aStandbyFlag3.length()+"大于最大值1");
		StandbyFlag3 = aStandbyFlag3;
	}

	/**
	* 使用另外一个 LOBonusPolHisSchema 对象给 Schema 赋值
	* @param: aLOBonusPolHisSchema LOBonusPolHisSchema
	**/
	public void setSchema(LOBonusPolHisSchema aLOBonusPolHisSchema)
	{
		this.GrpContNo = aLOBonusPolHisSchema.getGrpContNo();
		this.GrpPolNo = aLOBonusPolHisSchema.getGrpPolNo();
		this.ContNo = aLOBonusPolHisSchema.getContNo();
		this.ManageCom = aLOBonusPolHisSchema.getManageCom();
		this.PolNo = aLOBonusPolHisSchema.getPolNo();
		this.FiscalYear = aLOBonusPolHisSchema.getFiscalYear();
		this.SerialNo = aLOBonusPolHisSchema.getSerialNo();
		this.InsuAccNo = aLOBonusPolHisSchema.getInsuAccNo();
		this.PayPlanCode = aLOBonusPolHisSchema.getPayPlanCode();
		this.OtherNo = aLOBonusPolHisSchema.getOtherNo();
		this.OtherType = aLOBonusPolHisSchema.getOtherType();
		this.AccAscription = aLOBonusPolHisSchema.getAccAscription();
		this.RiskCode = aLOBonusPolHisSchema.getRiskCode();
		this.InsuredNo = aLOBonusPolHisSchema.getInsuredNo();
		this.AppntNo = aLOBonusPolHisSchema.getAppntNo();
		this.AccType = aLOBonusPolHisSchema.getAccType();
		this.AccComputeFlag = aLOBonusPolHisSchema.getAccComputeFlag();
		this.BonusDate = fDate.getDate( aLOBonusPolHisSchema.getBonusDate());
		this.BonusInt = aLOBonusPolHisSchema.getBonusInt();
		this.BonusGetMode = aLOBonusPolHisSchema.getBonusGetMode();
		this.LastBala = aLOBonusPolHisSchema.getLastBala();
		this.LastStock = aLOBonusPolHisSchema.getLastStock();
		this.BonusRate = aLOBonusPolHisSchema.getBonusRate();
		this.InterestRate = aLOBonusPolHisSchema.getInterestRate();
		this.SumBonus = aLOBonusPolHisSchema.getSumBonus();
		this.SumInterest = aLOBonusPolHisSchema.getSumInterest();
		this.InsuAccBala = aLOBonusPolHisSchema.getInsuAccBala();
		this.InsuAccStock = aLOBonusPolHisSchema.getInsuAccStock();
		this.Operator = aLOBonusPolHisSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOBonusPolHisSchema.getMakeDate());
		this.MakeTime = aLOBonusPolHisSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOBonusPolHisSchema.getModifyDate());
		this.ModifyTime = aLOBonusPolHisSchema.getModifyTime();
		this.EdorNo = aLOBonusPolHisSchema.getEdorNo();
		this.StandbyFlag1 = aLOBonusPolHisSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLOBonusPolHisSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLOBonusPolHisSchema.getStandbyFlag3();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			this.FiscalYear = rs.getInt("FiscalYear");
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherType") == null )
				this.OtherType = null;
			else
				this.OtherType = rs.getString("OtherType").trim();

			if( rs.getString("AccAscription") == null )
				this.AccAscription = null;
			else
				this.AccAscription = rs.getString("AccAscription").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccComputeFlag") == null )
				this.AccComputeFlag = null;
			else
				this.AccComputeFlag = rs.getString("AccComputeFlag").trim();

			this.BonusDate = rs.getDate("BonusDate");
			this.BonusInt = rs.getInt("BonusInt");
			if( rs.getString("BonusGetMode") == null )
				this.BonusGetMode = null;
			else
				this.BonusGetMode = rs.getString("BonusGetMode").trim();

			this.LastBala = rs.getDouble("LastBala");
			this.LastStock = rs.getDouble("LastStock");
			this.BonusRate = rs.getDouble("BonusRate");
			this.InterestRate = rs.getDouble("InterestRate");
			this.SumBonus = rs.getDouble("SumBonus");
			this.SumInterest = rs.getDouble("SumInterest");
			this.InsuAccBala = rs.getDouble("InsuAccBala");
			this.InsuAccStock = rs.getDouble("InsuAccStock");
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

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBonusPolHis表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusPolHisSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBonusPolHisSchema getSchema()
	{
		LOBonusPolHisSchema aLOBonusPolHisSchema = new LOBonusPolHisSchema();
		aLOBonusPolHisSchema.setSchema(this);
		return aLOBonusPolHisSchema;
	}

	public LOBonusPolHisDB getDB()
	{
		LOBonusPolHisDB aDBOper = new LOBonusPolHisDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusPolHis描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccAscription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccComputeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusInt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LastBala));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LastStock));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InterestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuAccBala));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuAccStock));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusPolHis>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OtherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AccAscription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AccComputeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BonusDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			BonusInt= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			BonusGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			LastBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			LastStock = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			BonusRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			InterestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			SumBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			SumInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			InsuAccBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			InsuAccStock = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusPolHisSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherType));
		}
		if (FCode.equalsIgnoreCase("AccAscription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccAscription));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccComputeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccComputeFlag));
		}
		if (FCode.equalsIgnoreCase("BonusDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusDate()));
		}
		if (FCode.equalsIgnoreCase("BonusInt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusInt));
		}
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGetMode));
		}
		if (FCode.equalsIgnoreCase("LastBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastBala));
		}
		if (FCode.equalsIgnoreCase("LastStock"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastStock));
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusRate));
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestRate));
		}
		if (FCode.equalsIgnoreCase("SumBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumBonus));
		}
		if (FCode.equalsIgnoreCase("SumInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumInterest));
		}
		if (FCode.equalsIgnoreCase("InsuAccBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccBala));
		}
		if (FCode.equalsIgnoreCase("InsuAccStock"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccStock));
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 5:
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(OtherType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AccAscription);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccComputeFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusDate()));
				break;
			case 18:
				strFieldValue = String.valueOf(BonusInt);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BonusGetMode);
				break;
			case 20:
				strFieldValue = String.valueOf(LastBala);
				break;
			case 21:
				strFieldValue = String.valueOf(LastStock);
				break;
			case 22:
				strFieldValue = String.valueOf(BonusRate);
				break;
			case 23:
				strFieldValue = String.valueOf(InterestRate);
				break;
			case 24:
				strFieldValue = String.valueOf(SumBonus);
				break;
			case 25:
				strFieldValue = String.valueOf(SumInterest);
				break;
			case 26:
				strFieldValue = String.valueOf(InsuAccBala);
				break;
			case 27:
				strFieldValue = String.valueOf(InsuAccStock);
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherType = FValue.trim();
			}
			else
				OtherType = null;
		}
		if (FCode.equalsIgnoreCase("AccAscription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccAscription = FValue.trim();
			}
			else
				AccAscription = null;
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
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("AccComputeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccComputeFlag = FValue.trim();
			}
			else
				AccComputeFlag = null;
		}
		if (FCode.equalsIgnoreCase("BonusDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusDate = fDate.getDate( FValue );
			}
			else
				BonusDate = null;
		}
		if (FCode.equalsIgnoreCase("BonusInt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BonusInt = i;
			}
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
		if (FCode.equalsIgnoreCase("LastBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastBala = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastStock"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastStock = d;
			}
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InterestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumBonus = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuAccBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuAccBala = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuAccStock"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuAccStock = d;
			}
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBonusPolHisSchema other = (LOBonusPolHisSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& ManageCom.equals(other.getManageCom())
			&& PolNo.equals(other.getPolNo())
			&& FiscalYear == other.getFiscalYear()
			&& SerialNo.equals(other.getSerialNo())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherType.equals(other.getOtherType())
			&& AccAscription.equals(other.getAccAscription())
			&& RiskCode.equals(other.getRiskCode())
			&& InsuredNo.equals(other.getInsuredNo())
			&& AppntNo.equals(other.getAppntNo())
			&& AccType.equals(other.getAccType())
			&& AccComputeFlag.equals(other.getAccComputeFlag())
			&& fDate.getString(BonusDate).equals(other.getBonusDate())
			&& BonusInt == other.getBonusInt()
			&& BonusGetMode.equals(other.getBonusGetMode())
			&& LastBala == other.getLastBala()
			&& LastStock == other.getLastStock()
			&& BonusRate == other.getBonusRate()
			&& InterestRate == other.getInterestRate()
			&& SumBonus == other.getSumBonus()
			&& SumInterest == other.getSumInterest()
			&& InsuAccBala == other.getInsuAccBala()
			&& InsuAccStock == other.getInsuAccStock()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& EdorNo.equals(other.getEdorNo())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("PolNo") ) {
			return 4;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return 5;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 6;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 7;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 8;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 9;
		}
		if( strFieldName.equals("OtherType") ) {
			return 10;
		}
		if( strFieldName.equals("AccAscription") ) {
			return 11;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 13;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 14;
		}
		if( strFieldName.equals("AccType") ) {
			return 15;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return 16;
		}
		if( strFieldName.equals("BonusDate") ) {
			return 17;
		}
		if( strFieldName.equals("BonusInt") ) {
			return 18;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return 19;
		}
		if( strFieldName.equals("LastBala") ) {
			return 20;
		}
		if( strFieldName.equals("LastStock") ) {
			return 21;
		}
		if( strFieldName.equals("BonusRate") ) {
			return 22;
		}
		if( strFieldName.equals("InterestRate") ) {
			return 23;
		}
		if( strFieldName.equals("SumBonus") ) {
			return 24;
		}
		if( strFieldName.equals("SumInterest") ) {
			return 25;
		}
		if( strFieldName.equals("InsuAccBala") ) {
			return 26;
		}
		if( strFieldName.equals("InsuAccStock") ) {
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
		if( strFieldName.equals("EdorNo") ) {
			return 33;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 34;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 35;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 36;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "PolNo";
				break;
			case 5:
				strFieldName = "FiscalYear";
				break;
			case 6:
				strFieldName = "SerialNo";
				break;
			case 7:
				strFieldName = "InsuAccNo";
				break;
			case 8:
				strFieldName = "PayPlanCode";
				break;
			case 9:
				strFieldName = "OtherNo";
				break;
			case 10:
				strFieldName = "OtherType";
				break;
			case 11:
				strFieldName = "AccAscription";
				break;
			case 12:
				strFieldName = "RiskCode";
				break;
			case 13:
				strFieldName = "InsuredNo";
				break;
			case 14:
				strFieldName = "AppntNo";
				break;
			case 15:
				strFieldName = "AccType";
				break;
			case 16:
				strFieldName = "AccComputeFlag";
				break;
			case 17:
				strFieldName = "BonusDate";
				break;
			case 18:
				strFieldName = "BonusInt";
				break;
			case 19:
				strFieldName = "BonusGetMode";
				break;
			case 20:
				strFieldName = "LastBala";
				break;
			case 21:
				strFieldName = "LastStock";
				break;
			case 22:
				strFieldName = "BonusRate";
				break;
			case 23:
				strFieldName = "InterestRate";
				break;
			case 24:
				strFieldName = "SumBonus";
				break;
			case 25:
				strFieldName = "SumInterest";
				break;
			case 26:
				strFieldName = "InsuAccBala";
				break;
			case 27:
				strFieldName = "InsuAccStock";
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
				strFieldName = "EdorNo";
				break;
			case 34:
				strFieldName = "StandbyFlag1";
				break;
			case 35:
				strFieldName = "StandbyFlag2";
				break;
			case 36:
				strFieldName = "StandbyFlag3";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccAscription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BonusInt") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BonusGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastStock") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InterestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuAccBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuAccStock") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("EdorNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
