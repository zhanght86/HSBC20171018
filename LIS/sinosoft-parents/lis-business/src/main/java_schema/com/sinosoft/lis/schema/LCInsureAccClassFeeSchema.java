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
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;

/*
 * <p>ClassName: LCInsureAccClassFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCInsureAccClassFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCInsureAccClassFeeSchema.class);
	// @Field
	/** 保单险种号码 */
	private String PolNo;
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
	/** 合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 管理机构 */
	private String ManageCom;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 账户类型 */
	private String AccType;
	/** 账户结算方式 */
	private String AccComputeFlag;
	/** 账户成立日期 */
	private Date AccFoundDate;
	/** 账户成立时间 */
	private String AccFoundTime;
	/** 结算日期 */
	private Date BalaDate;
	/** 结算时间 */
	private String BalaTime;
	/** 管理费比例 */
	private double FeeRate;
	/** 本次管理费金额 */
	private double Fee;
	/** 本次管理费单位数 */
	private double FeeUnit;
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
	/** 管理费编码 */
	private String FeeCode;
	/** 是否初始费用 */
	private String IsInitFee;
	/** 币别 */
	private String Currency;
	/** 投资类型 */
	private String InvestType;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 33;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCInsureAccClassFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "PolNo";
		pk[1] = "InsuAccNo";
		pk[2] = "PayPlanCode";
		pk[3] = "OtherNo";
		pk[4] = "AccAscription";
		pk[5] = "FeeCode";

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
		LCInsureAccClassFeeSchema cloned = (LCInsureAccClassFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
		if(aPayPlanCode!=null && aPayPlanCode.length()>10)
			throw new IllegalArgumentException("交费计划编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值10");
		PayPlanCode = aPayPlanCode;
	}
	/**
	* 存放交费号
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
	* 1 －－ 个人保单号<p>
	* 2 －－ 缴费收据号<p>
	* 3 －－ 集体保单号(对于集体公共账户对应的集体保单号码)
	*/
	public String getOtherType()
	{
		return OtherType;
	}
	public void setOtherType(String aOtherType)
	{
		if(aOtherType!=null && aOtherType.length()>1)
			throw new IllegalArgumentException("对应其它号码类型OtherType值"+aOtherType+"的长度"+aOtherType.length()+"大于最大值1");
		OtherType = aOtherType;
	}
	/**
	* 0-未归属<p>
	* 1-已归属
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
	* 001 -- 集体公共账户<p>
	* 002 -- 个人缴费账户<p>
	* 003 -- 个人累积生息账户<p>
	* 004 -- 个人红利账户
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
	* 0 －－ 不计利息<p>
	* 1 －－ 按固定利率生息（单利）<p>
	* 2 －－ 按固定利率生息（复利）<p>
	* 3 －－ 按利率表生息（单利）<p>
	* 4 －－ 按利率表生息（复利）
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
	public String getAccFoundDate()
	{
		if( AccFoundDate != null )
			return fDate.getString(AccFoundDate);
		else
			return null;
	}
	public void setAccFoundDate(Date aAccFoundDate)
	{
		AccFoundDate = aAccFoundDate;
	}
	public void setAccFoundDate(String aAccFoundDate)
	{
		if (aAccFoundDate != null && !aAccFoundDate.equals("") )
		{
			AccFoundDate = fDate.getDate( aAccFoundDate );
		}
		else
			AccFoundDate = null;
	}

	public String getAccFoundTime()
	{
		return AccFoundTime;
	}
	public void setAccFoundTime(String aAccFoundTime)
	{
		if(aAccFoundTime!=null && aAccFoundTime.length()>8)
			throw new IllegalArgumentException("账户成立时间AccFoundTime值"+aAccFoundTime+"的长度"+aAccFoundTime.length()+"大于最大值8");
		AccFoundTime = aAccFoundTime;
	}
	public String getBalaDate()
	{
		if( BalaDate != null )
			return fDate.getString(BalaDate);
		else
			return null;
	}
	public void setBalaDate(Date aBalaDate)
	{
		BalaDate = aBalaDate;
	}
	public void setBalaDate(String aBalaDate)
	{
		if (aBalaDate != null && !aBalaDate.equals("") )
		{
			BalaDate = fDate.getDate( aBalaDate );
		}
		else
			BalaDate = null;
	}

	public String getBalaTime()
	{
		return BalaTime;
	}
	public void setBalaTime(String aBalaTime)
	{
		if(aBalaTime!=null && aBalaTime.length()>8)
			throw new IllegalArgumentException("结算时间BalaTime值"+aBalaTime+"的长度"+aBalaTime.length()+"大于最大值8");
		BalaTime = aBalaTime;
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

	public double getFee()
	{
		return Fee;
	}
	public void setFee(double aFee)
	{
		Fee = aFee;
	}
	public void setFee(String aFee)
	{
		if (aFee != null && !aFee.equals(""))
		{
			Double tDouble = new Double(aFee);
			double d = tDouble.doubleValue();
			Fee = d;
		}
	}

	public double getFeeUnit()
	{
		return FeeUnit;
	}
	public void setFeeUnit(double aFeeUnit)
	{
		FeeUnit = aFeeUnit;
	}
	public void setFeeUnit(String aFeeUnit)
	{
		if (aFeeUnit != null && !aFeeUnit.equals(""))
		{
			Double tDouble = new Double(aFeeUnit);
			double d = tDouble.doubleValue();
			FeeUnit = d;
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
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		if(aFeeCode!=null && aFeeCode.length()>6)
			throw new IllegalArgumentException("管理费编码FeeCode值"+aFeeCode+"的长度"+aFeeCode.length()+"大于最大值6");
		FeeCode = aFeeCode;
	}
	public String getIsInitFee()
	{
		return IsInitFee;
	}
	public void setIsInitFee(String aIsInitFee)
	{
		if(aIsInitFee!=null && aIsInitFee.length()>10)
			throw new IllegalArgumentException("是否初始费用IsInitFee值"+aIsInitFee+"的长度"+aIsInitFee.length()+"大于最大值10");
		IsInitFee = aIsInitFee;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	public String getInvestType()
	{
		return InvestType;
	}
	public void setInvestType(String aInvestType)
	{
		if(aInvestType!=null && aInvestType.length()>3)
			throw new IllegalArgumentException("投资类型InvestType值"+aInvestType+"的长度"+aInvestType.length()+"大于最大值3");
		InvestType = aInvestType;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
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

	/**
	* 使用另外一个 LCInsureAccClassFeeSchema 对象给 Schema 赋值
	* @param: aLCInsureAccClassFeeSchema LCInsureAccClassFeeSchema
	**/
	public void setSchema(LCInsureAccClassFeeSchema aLCInsureAccClassFeeSchema)
	{
		this.PolNo = aLCInsureAccClassFeeSchema.getPolNo();
		this.InsuAccNo = aLCInsureAccClassFeeSchema.getInsuAccNo();
		this.PayPlanCode = aLCInsureAccClassFeeSchema.getPayPlanCode();
		this.OtherNo = aLCInsureAccClassFeeSchema.getOtherNo();
		this.OtherType = aLCInsureAccClassFeeSchema.getOtherType();
		this.AccAscription = aLCInsureAccClassFeeSchema.getAccAscription();
		this.RiskCode = aLCInsureAccClassFeeSchema.getRiskCode();
		this.ContNo = aLCInsureAccClassFeeSchema.getContNo();
		this.GrpPolNo = aLCInsureAccClassFeeSchema.getGrpPolNo();
		this.GrpContNo = aLCInsureAccClassFeeSchema.getGrpContNo();
		this.ManageCom = aLCInsureAccClassFeeSchema.getManageCom();
		this.InsuredNo = aLCInsureAccClassFeeSchema.getInsuredNo();
		this.AppntNo = aLCInsureAccClassFeeSchema.getAppntNo();
		this.AccType = aLCInsureAccClassFeeSchema.getAccType();
		this.AccComputeFlag = aLCInsureAccClassFeeSchema.getAccComputeFlag();
		this.AccFoundDate = fDate.getDate( aLCInsureAccClassFeeSchema.getAccFoundDate());
		this.AccFoundTime = aLCInsureAccClassFeeSchema.getAccFoundTime();
		this.BalaDate = fDate.getDate( aLCInsureAccClassFeeSchema.getBalaDate());
		this.BalaTime = aLCInsureAccClassFeeSchema.getBalaTime();
		this.FeeRate = aLCInsureAccClassFeeSchema.getFeeRate();
		this.Fee = aLCInsureAccClassFeeSchema.getFee();
		this.FeeUnit = aLCInsureAccClassFeeSchema.getFeeUnit();
		this.Operator = aLCInsureAccClassFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCInsureAccClassFeeSchema.getMakeDate());
		this.MakeTime = aLCInsureAccClassFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCInsureAccClassFeeSchema.getModifyDate());
		this.ModifyTime = aLCInsureAccClassFeeSchema.getModifyTime();
		this.FeeCode = aLCInsureAccClassFeeSchema.getFeeCode();
		this.IsInitFee = aLCInsureAccClassFeeSchema.getIsInitFee();
		this.Currency = aLCInsureAccClassFeeSchema.getCurrency();
		this.InvestType = aLCInsureAccClassFeeSchema.getInvestType();
		this.ComCode = aLCInsureAccClassFeeSchema.getComCode();
		this.ModifyOperator = aLCInsureAccClassFeeSchema.getModifyOperator();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			this.AccFoundDate = rs.getDate("AccFoundDate");
			if( rs.getString("AccFoundTime") == null )
				this.AccFoundTime = null;
			else
				this.AccFoundTime = rs.getString("AccFoundTime").trim();

			this.BalaDate = rs.getDate("BalaDate");
			if( rs.getString("BalaTime") == null )
				this.BalaTime = null;
			else
				this.BalaTime = rs.getString("BalaTime").trim();

			this.FeeRate = rs.getDouble("FeeRate");
			this.Fee = rs.getDouble("Fee");
			this.FeeUnit = rs.getDouble("FeeUnit");
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

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("IsInitFee") == null )
				this.IsInitFee = null;
			else
				this.IsInitFee = rs.getString("IsInitFee").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCInsureAccClassFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsureAccClassFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCInsureAccClassFeeSchema getSchema()
	{
		LCInsureAccClassFeeSchema aLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
		aLCInsureAccClassFeeSchema.setSchema(this);
		return aLCInsureAccClassFeeSchema;
	}

	public LCInsureAccClassFeeDB getDB()
	{
		LCInsureAccClassFeeDB aDBOper = new LCInsureAccClassFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAccClassFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccAscription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccComputeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccFoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccFoundTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalaDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalaTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsInitFee)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAccClassFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccAscription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AccComputeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AccFoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			AccFoundTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BalaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			BalaTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			FeeUnit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			IsInitFee = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsureAccClassFeeSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("AccFoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccFoundDate()));
		}
		if (FCode.equalsIgnoreCase("AccFoundTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccFoundTime));
		}
		if (FCode.equalsIgnoreCase("BalaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
		}
		if (FCode.equalsIgnoreCase("BalaTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalaTime));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fee));
		}
		if (FCode.equalsIgnoreCase("FeeUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeUnit));
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("IsInitFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsInitFee));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccAscription);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AccComputeFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccFoundDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccFoundTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BalaTime);
				break;
			case 19:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 20:
				strFieldValue = String.valueOf(Fee);
				break;
			case 21:
				strFieldValue = String.valueOf(FeeUnit);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(IsInitFee);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("AccFoundDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccFoundDate = fDate.getDate( FValue );
			}
			else
				AccFoundDate = null;
		}
		if (FCode.equalsIgnoreCase("AccFoundTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccFoundTime = FValue.trim();
			}
			else
				AccFoundTime = null;
		}
		if (FCode.equalsIgnoreCase("BalaDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalaDate = fDate.getDate( FValue );
			}
			else
				BalaDate = null;
		}
		if (FCode.equalsIgnoreCase("BalaTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalaTime = FValue.trim();
			}
			else
				BalaTime = null;
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
		if (FCode.equalsIgnoreCase("Fee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Fee = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeUnit = d;
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
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("IsInitFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsInitFee = FValue.trim();
			}
			else
				IsInitFee = null;
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
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestType = FValue.trim();
			}
			else
				InvestType = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCInsureAccClassFeeSchema other = (LCInsureAccClassFeeSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherType.equals(other.getOtherType())
			&& AccAscription.equals(other.getAccAscription())
			&& RiskCode.equals(other.getRiskCode())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ManageCom.equals(other.getManageCom())
			&& InsuredNo.equals(other.getInsuredNo())
			&& AppntNo.equals(other.getAppntNo())
			&& AccType.equals(other.getAccType())
			&& AccComputeFlag.equals(other.getAccComputeFlag())
			&& fDate.getString(AccFoundDate).equals(other.getAccFoundDate())
			&& AccFoundTime.equals(other.getAccFoundTime())
			&& fDate.getString(BalaDate).equals(other.getBalaDate())
			&& BalaTime.equals(other.getBalaTime())
			&& FeeRate == other.getFeeRate()
			&& Fee == other.getFee()
			&& FeeUnit == other.getFeeUnit()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& FeeCode.equals(other.getFeeCode())
			&& IsInitFee.equals(other.getIsInitFee())
			&& Currency.equals(other.getCurrency())
			&& InvestType.equals(other.getInvestType())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 1;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("OtherType") ) {
			return 4;
		}
		if( strFieldName.equals("AccAscription") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("ContNo") ) {
			return 7;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 11;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 12;
		}
		if( strFieldName.equals("AccType") ) {
			return 13;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return 14;
		}
		if( strFieldName.equals("AccFoundDate") ) {
			return 15;
		}
		if( strFieldName.equals("AccFoundTime") ) {
			return 16;
		}
		if( strFieldName.equals("BalaDate") ) {
			return 17;
		}
		if( strFieldName.equals("BalaTime") ) {
			return 18;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 19;
		}
		if( strFieldName.equals("Fee") ) {
			return 20;
		}
		if( strFieldName.equals("FeeUnit") ) {
			return 21;
		}
		if( strFieldName.equals("Operator") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 27;
		}
		if( strFieldName.equals("IsInitFee") ) {
			return 28;
		}
		if( strFieldName.equals("Currency") ) {
			return 29;
		}
		if( strFieldName.equals("InvestType") ) {
			return 30;
		}
		if( strFieldName.equals("ComCode") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 32;
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "PayPlanCode";
				break;
			case 3:
				strFieldName = "OtherNo";
				break;
			case 4:
				strFieldName = "OtherType";
				break;
			case 5:
				strFieldName = "AccAscription";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "ContNo";
				break;
			case 8:
				strFieldName = "GrpPolNo";
				break;
			case 9:
				strFieldName = "GrpContNo";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "InsuredNo";
				break;
			case 12:
				strFieldName = "AppntNo";
				break;
			case 13:
				strFieldName = "AccType";
				break;
			case 14:
				strFieldName = "AccComputeFlag";
				break;
			case 15:
				strFieldName = "AccFoundDate";
				break;
			case 16:
				strFieldName = "AccFoundTime";
				break;
			case 17:
				strFieldName = "BalaDate";
				break;
			case 18:
				strFieldName = "BalaTime";
				break;
			case 19:
				strFieldName = "FeeRate";
				break;
			case 20:
				strFieldName = "Fee";
				break;
			case 21:
				strFieldName = "FeeUnit";
				break;
			case 22:
				strFieldName = "Operator";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
				strFieldName = "ModifyTime";
				break;
			case 27:
				strFieldName = "FeeCode";
				break;
			case 28:
				strFieldName = "IsInitFee";
				break;
			case 29:
				strFieldName = "Currency";
				break;
			case 30:
				strFieldName = "InvestType";
				break;
			case 31:
				strFieldName = "ComCode";
				break;
			case 32:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("PolNo") ) {
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("AccFoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccFoundTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalaDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalaTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Fee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeUnit") ) {
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
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsInitFee") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
