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
import com.sinosoft.lis.db.LPInsureAccFeeDB;

/*
 * <p>ClassName: LPInsureAccFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPInsureAccFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPInsureAccFeeSchema.class);
	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 合同号码 */
	private String ContNo;
	/** 管理机构 */
	private String ManageCom;
	/** 印刷号码 */
	private String PrtNo;
	/** 险种编码 */
	private String RiskCode;
	/** 账户类型 */
	private String AccType;
	/** 投资类型 */
	private String InvestType;
	/** 基金公司代码 */
	private String FundCompanyCode;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 账户所有者 */
	private String Owner;
	/** 账户结算方式 */
	private String AccComputeFlag;
	/** 账户成立日期 */
	private Date AccFoundDate;
	/** 账户成立时间 */
	private String AccFoundTime;
	/** 管理费比例 */
	private double FeeRate;
	/** 本次管理费金额 */
	private double Fee;
	/** 本次管理费单位数 */
	private double FeeUnit;
	/** 结算日期 */
	private Date BalaDate;
	/** 结算时间 */
	private String BalaTime;
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
	/** 币别 */
	private String Currency;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPInsureAccFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "PolNo";
		pk[3] = "InsuAccNo";

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
		LPInsureAccFeeSchema cloned = (LPInsureAccFeeSchema)super.clone();
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
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>2)
			throw new IllegalArgumentException("批改类型EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值2");
		EdorType = aEdorType;
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
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
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
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		if(aPrtNo!=null && aPrtNo.length()>20)
			throw new IllegalArgumentException("印刷号码PrtNo值"+aPrtNo+"的长度"+aPrtNo.length()+"大于最大值20");
		PrtNo = aPrtNo;
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
	* 001 -- 货币基金<p>
	* 002 -- 计息账户<p>
	* 003 -- 基金股票
	*/
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
	public String getFundCompanyCode()
	{
		return FundCompanyCode;
	}
	public void setFundCompanyCode(String aFundCompanyCode)
	{
		if(aFundCompanyCode!=null && aFundCompanyCode.length()>20)
			throw new IllegalArgumentException("基金公司代码FundCompanyCode值"+aFundCompanyCode+"的长度"+aFundCompanyCode.length()+"大于最大值20");
		FundCompanyCode = aFundCompanyCode;
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
	* 0-雇主<p>
	* 1-雇员
	*/
	public String getOwner()
	{
		return Owner;
	}
	public void setOwner(String aOwner)
	{
		if(aOwner!=null && aOwner.length()>1)
			throw new IllegalArgumentException("账户所有者Owner值"+aOwner+"的长度"+aOwner.length()+"大于最大值1");
		Owner = aOwner;
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
	* 使用另外一个 LPInsureAccFeeSchema 对象给 Schema 赋值
	* @param: aLPInsureAccFeeSchema LPInsureAccFeeSchema
	**/
	public void setSchema(LPInsureAccFeeSchema aLPInsureAccFeeSchema)
	{
		this.EdorNo = aLPInsureAccFeeSchema.getEdorNo();
		this.EdorType = aLPInsureAccFeeSchema.getEdorType();
		this.GrpContNo = aLPInsureAccFeeSchema.getGrpContNo();
		this.GrpPolNo = aLPInsureAccFeeSchema.getGrpPolNo();
		this.PolNo = aLPInsureAccFeeSchema.getPolNo();
		this.InsuAccNo = aLPInsureAccFeeSchema.getInsuAccNo();
		this.ContNo = aLPInsureAccFeeSchema.getContNo();
		this.ManageCom = aLPInsureAccFeeSchema.getManageCom();
		this.PrtNo = aLPInsureAccFeeSchema.getPrtNo();
		this.RiskCode = aLPInsureAccFeeSchema.getRiskCode();
		this.AccType = aLPInsureAccFeeSchema.getAccType();
		this.InvestType = aLPInsureAccFeeSchema.getInvestType();
		this.FundCompanyCode = aLPInsureAccFeeSchema.getFundCompanyCode();
		this.InsuredNo = aLPInsureAccFeeSchema.getInsuredNo();
		this.AppntNo = aLPInsureAccFeeSchema.getAppntNo();
		this.Owner = aLPInsureAccFeeSchema.getOwner();
		this.AccComputeFlag = aLPInsureAccFeeSchema.getAccComputeFlag();
		this.AccFoundDate = fDate.getDate( aLPInsureAccFeeSchema.getAccFoundDate());
		this.AccFoundTime = aLPInsureAccFeeSchema.getAccFoundTime();
		this.FeeRate = aLPInsureAccFeeSchema.getFeeRate();
		this.Fee = aLPInsureAccFeeSchema.getFee();
		this.FeeUnit = aLPInsureAccFeeSchema.getFeeUnit();
		this.BalaDate = fDate.getDate( aLPInsureAccFeeSchema.getBalaDate());
		this.BalaTime = aLPInsureAccFeeSchema.getBalaTime();
		this.Operator = aLPInsureAccFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPInsureAccFeeSchema.getMakeDate());
		this.MakeTime = aLPInsureAccFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPInsureAccFeeSchema.getModifyDate());
		this.ModifyTime = aLPInsureAccFeeSchema.getModifyTime();
		this.Currency = aLPInsureAccFeeSchema.getCurrency();
		this.ComCode = aLPInsureAccFeeSchema.getComCode();
		this.ModifyOperator = aLPInsureAccFeeSchema.getModifyOperator();
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

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("FundCompanyCode") == null )
				this.FundCompanyCode = null;
			else
				this.FundCompanyCode = rs.getString("FundCompanyCode").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("Owner") == null )
				this.Owner = null;
			else
				this.Owner = rs.getString("Owner").trim();

			if( rs.getString("AccComputeFlag") == null )
				this.AccComputeFlag = null;
			else
				this.AccComputeFlag = rs.getString("AccComputeFlag").trim();

			this.AccFoundDate = rs.getDate("AccFoundDate");
			if( rs.getString("AccFoundTime") == null )
				this.AccFoundTime = null;
			else
				this.AccFoundTime = rs.getString("AccFoundTime").trim();

			this.FeeRate = rs.getDouble("FeeRate");
			this.Fee = rs.getDouble("Fee");
			this.FeeUnit = rs.getDouble("FeeUnit");
			this.BalaDate = rs.getDate("BalaDate");
			if( rs.getString("BalaTime") == null )
				this.BalaTime = null;
			else
				this.BalaTime = rs.getString("BalaTime").trim();

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

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

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
			logger.debug("数据库中的LPInsureAccFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsureAccFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPInsureAccFeeSchema getSchema()
	{
		LPInsureAccFeeSchema aLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
		aLPInsureAccFeeSchema.setSchema(this);
		return aLPInsureAccFeeSchema;
	}

	public LPInsureAccFeeDB getDB()
	{
		LPInsureAccFeeDB aDBOper = new LPInsureAccFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsureAccFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundCompanyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Owner)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccComputeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccFoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccFoundTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalaDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalaTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsureAccFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			FundCompanyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Owner = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AccComputeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AccFoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			AccFoundTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			FeeUnit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			BalaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			BalaTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsureAccFeeSchema";
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("FundCompanyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCompanyCode));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("Owner"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Owner));
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
		if (FCode.equalsIgnoreCase("BalaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
		}
		if (FCode.equalsIgnoreCase("BalaTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalaTime));
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(FundCompanyCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Owner);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccComputeFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccFoundDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AccFoundTime);
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalaDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BalaTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 31:
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
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
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
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
		if (FCode.equalsIgnoreCase("FundCompanyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundCompanyCode = FValue.trim();
			}
			else
				FundCompanyCode = null;
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
		if (FCode.equalsIgnoreCase("Owner"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Owner = FValue.trim();
			}
			else
				Owner = null;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
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
		LPInsureAccFeeSchema other = (LPInsureAccFeeSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& PolNo.equals(other.getPolNo())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& ContNo.equals(other.getContNo())
			&& ManageCom.equals(other.getManageCom())
			&& PrtNo.equals(other.getPrtNo())
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& InvestType.equals(other.getInvestType())
			&& FundCompanyCode.equals(other.getFundCompanyCode())
			&& InsuredNo.equals(other.getInsuredNo())
			&& AppntNo.equals(other.getAppntNo())
			&& Owner.equals(other.getOwner())
			&& AccComputeFlag.equals(other.getAccComputeFlag())
			&& fDate.getString(AccFoundDate).equals(other.getAccFoundDate())
			&& AccFoundTime.equals(other.getAccFoundTime())
			&& FeeRate == other.getFeeRate()
			&& Fee == other.getFee()
			&& FeeUnit == other.getFeeUnit()
			&& fDate.getString(BalaDate).equals(other.getBalaDate())
			&& BalaTime.equals(other.getBalaTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Currency.equals(other.getCurrency())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 3;
		}
		if( strFieldName.equals("PolNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 5;
		}
		if( strFieldName.equals("ContNo") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 8;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 9;
		}
		if( strFieldName.equals("AccType") ) {
			return 10;
		}
		if( strFieldName.equals("InvestType") ) {
			return 11;
		}
		if( strFieldName.equals("FundCompanyCode") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 13;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 14;
		}
		if( strFieldName.equals("Owner") ) {
			return 15;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return 16;
		}
		if( strFieldName.equals("AccFoundDate") ) {
			return 17;
		}
		if( strFieldName.equals("AccFoundTime") ) {
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
		if( strFieldName.equals("BalaDate") ) {
			return 22;
		}
		if( strFieldName.equals("BalaTime") ) {
			return 23;
		}
		if( strFieldName.equals("Operator") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 28;
		}
		if( strFieldName.equals("Currency") ) {
			return 29;
		}
		if( strFieldName.equals("ComCode") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 31;
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
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "GrpPolNo";
				break;
			case 4:
				strFieldName = "PolNo";
				break;
			case 5:
				strFieldName = "InsuAccNo";
				break;
			case 6:
				strFieldName = "ContNo";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "PrtNo";
				break;
			case 9:
				strFieldName = "RiskCode";
				break;
			case 10:
				strFieldName = "AccType";
				break;
			case 11:
				strFieldName = "InvestType";
				break;
			case 12:
				strFieldName = "FundCompanyCode";
				break;
			case 13:
				strFieldName = "InsuredNo";
				break;
			case 14:
				strFieldName = "AppntNo";
				break;
			case 15:
				strFieldName = "Owner";
				break;
			case 16:
				strFieldName = "AccComputeFlag";
				break;
			case 17:
				strFieldName = "AccFoundDate";
				break;
			case 18:
				strFieldName = "AccFoundTime";
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
				strFieldName = "BalaDate";
				break;
			case 23:
				strFieldName = "BalaTime";
				break;
			case 24:
				strFieldName = "Operator";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
				strFieldName = "MakeTime";
				break;
			case 27:
				strFieldName = "ModifyDate";
				break;
			case 28:
				strFieldName = "ModifyTime";
				break;
			case 29:
				strFieldName = "Currency";
				break;
			case 30:
				strFieldName = "ComCode";
				break;
			case 31:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundCompanyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Owner") ) {
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
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Fee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeUnit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalaDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalaTime") ) {
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
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
