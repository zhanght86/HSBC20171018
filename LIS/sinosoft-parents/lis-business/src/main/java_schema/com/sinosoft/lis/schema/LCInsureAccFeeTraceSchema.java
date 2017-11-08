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
import com.sinosoft.lis.db.LCInsureAccFeeTraceDB;

/*
 * <p>ClassName: LCInsureAccFeeTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug
 */
public class LCInsureAccFeeTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCInsureAccFeeTraceSchema.class);
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 流水号 */
	private String SerialNo;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 险种编码 */
	private String RiskCode;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 对应其它号码 */
	private String OtherNo;
	/** 对应其它号码类型 */
	private String OtherType;
	/** 账户归属属性 */
	private String AccAscription;
	/** 金额类型 */
	private String MoneyType;
	/** 管理费比例 */
	private double FeeRate;
	/** 本次管理费金额 */
	private double Fee;
	/** 本次管理费单位数 */
	private double FeeUnit;
	/** 交费日期 */
	private Date PayDate;
	/** 状态 */
	private String State;
	/** 管理费编码 */
	private String FeeCode;
	/** 管理费的顺序号 */
	private int InerSerialNo;
	/** 管理机构 */
	private String ManageCom;
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
	/** 缴费编码 */
	private String PayNo;
	/** 币种 */
	private String Currency;
	/** 投资类型 */
	private String InvestType;
	/** 账户批单号码 */
	private String AccalterNo;
	/** 账户批单号码类型 */
	private String AccalterType;
	/** 业务生效日期 */
	private Date ConfDate;
	/** T日 */
	private Date AllocationDate;
	/** 计价日期 */
	private Date PricingDate;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 净费额 */
	private double NetAmount;
	/** 税费 */
	private double TaxAmount;
	/** 税率 */
	private double Tax;

	public static final int FIELDNUM = 38;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCInsureAccFeeTraceSchema()
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
		LCInsureAccFeeTraceSchema cloned = (LCInsureAccFeeTraceSchema)super.clone();
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
		GrpContNo = aGrpContNo;
	}
	/**
	* 集体保单险种号码
	*/
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	/**
	* 合同号码
	*/
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	/**
	* 保单险种号码
	*/
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	/**
	* 流水号
	*/
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 保险帐户号码
	*/
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	/**
	* 险种编码
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 交费计划编码
	*/
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	/**
	* 对应其它号码
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 对应其它号码类型
	*/
	public String getOtherType()
	{
		return OtherType;
	}
	public void setOtherType(String aOtherType)
	{
		OtherType = aOtherType;
	}
	/**
	* 账户归属属性
	*/
	public String getAccAscription()
	{
		return AccAscription;
	}
	public void setAccAscription(String aAccAscription)
	{
		AccAscription = aAccAscription;
	}
	/**
	* 金额类型
	*/
	public String getMoneyType()
	{
		return MoneyType;
	}
	public void setMoneyType(String aMoneyType)
	{
		MoneyType = aMoneyType;
	}
	/**
	* 管理费比例
	*/
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
	* 本次管理费金额
	*/
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

	/**
	* 本次管理费单位数
	*/
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

	/**
	* 交费日期
	*/
	public String getPayDate()
	{
		if( PayDate != null )
			return fDate.getString(PayDate);
		else
			return null;
	}
	public void setPayDate(Date aPayDate)
	{
		PayDate = aPayDate;
	}
	public void setPayDate(String aPayDate)
	{
		if (aPayDate != null && !aPayDate.equals("") )
		{
			PayDate = fDate.getDate( aPayDate );
		}
		else
			PayDate = null;
	}

	/**
	* 状态
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	/**
	* 管理费编码
	*/
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		FeeCode = aFeeCode;
	}
	/**
	* 管理费的顺序号
	*/
	public int getInerSerialNo()
	{
		return InerSerialNo;
	}
	public void setInerSerialNo(int aInerSerialNo)
	{
		InerSerialNo = aInerSerialNo;
	}
	public void setInerSerialNo(String aInerSerialNo)
	{
		if (aInerSerialNo != null && !aInerSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aInerSerialNo);
			int i = tInteger.intValue();
			InerSerialNo = i;
		}
	}

	/**
	* 管理机构
	*/
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 操作员
	*/
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 入机日期
	*/
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

	/**
	* 入机时间
	*/
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/**
	* 最后一次修改日期
	*/
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

	/**
	* 最后一次修改时间
	*/
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/**
	* 缴费编码
	*/
	public String getPayNo()
	{
		return PayNo;
	}
	public void setPayNo(String aPayNo)
	{
		PayNo = aPayNo;
	}
	/**
	* 币种
	*/
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/**
	* 投资类型
	*/
	public String getInvestType()
	{
		return InvestType;
	}
	public void setInvestType(String aInvestType)
	{
		InvestType = aInvestType;
	}
	/**
	* 账户批单号码
	*/
	public String getAccalterNo()
	{
		return AccalterNo;
	}
	public void setAccalterNo(String aAccalterNo)
	{
		AccalterNo = aAccalterNo;
	}
	/**
	* 账户批单号码类型
	*/
	public String getAccalterType()
	{
		return AccalterType;
	}
	public void setAccalterType(String aAccalterType)
	{
		AccalterType = aAccalterType;
	}
	/**
	* 业务生效日期
	*/
	public String getConfDate()
	{
		if( ConfDate != null )
			return fDate.getString(ConfDate);
		else
			return null;
	}
	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}
	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
	}

	/**
	* T日
	*/
	public String getAllocationDate()
	{
		if( AllocationDate != null )
			return fDate.getString(AllocationDate);
		else
			return null;
	}
	public void setAllocationDate(Date aAllocationDate)
	{
		AllocationDate = aAllocationDate;
	}
	public void setAllocationDate(String aAllocationDate)
	{
		if (aAllocationDate != null && !aAllocationDate.equals("") )
		{
			AllocationDate = fDate.getDate( aAllocationDate );
		}
		else
			AllocationDate = null;
	}

	/**
	* 计价日期
	*/
	public String getPricingDate()
	{
		if( PricingDate != null )
			return fDate.getString(PricingDate);
		else
			return null;
	}
	public void setPricingDate(Date aPricingDate)
	{
		PricingDate = aPricingDate;
	}
	public void setPricingDate(String aPricingDate)
	{
		if (aPricingDate != null && !aPricingDate.equals("") )
		{
			PricingDate = fDate.getDate( aPricingDate );
		}
		else
			PricingDate = null;
	}

	/**
	* 公司代码
	*/
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	/**
	* 最后一次修改操作员
	*/
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		ModifyOperator = aModifyOperator;
	}
	public double getNetAmount()
	{
		return NetAmount;
	}
	public void setNetAmount(double aNetAmount)
	{
		NetAmount = aNetAmount;
	}
	public void setNetAmount(String aNetAmount)
	{
		if (aNetAmount != null && !aNetAmount.equals(""))
		{
			Double tDouble = new Double(aNetAmount);
			double d = tDouble.doubleValue();
			NetAmount = d;
		}
	}

	public double getTaxAmount()
	{
		return TaxAmount;
	}
	public void setTaxAmount(double aTaxAmount)
	{
		TaxAmount = aTaxAmount;
	}
	public void setTaxAmount(String aTaxAmount)
	{
		if (aTaxAmount != null && !aTaxAmount.equals(""))
		{
			Double tDouble = new Double(aTaxAmount);
			double d = tDouble.doubleValue();
			TaxAmount = d;
		}
	}

	public double getTax()
	{
		return Tax;
	}
	public void setTax(double aTax)
	{
		Tax = aTax;
	}
	public void setTax(String aTax)
	{
		if (aTax != null && !aTax.equals(""))
		{
			Double tDouble = new Double(aTax);
			double d = tDouble.doubleValue();
			Tax = d;
		}
	}


	/**
	* 使用另外一个 LCInsureAccFeeTraceSchema 对象给 Schema 赋值
	* @param: aLCInsureAccFeeTraceSchema LCInsureAccFeeTraceSchema
	**/
	public void setSchema(LCInsureAccFeeTraceSchema aLCInsureAccFeeTraceSchema)
	{
		this.GrpContNo = aLCInsureAccFeeTraceSchema.getGrpContNo();
		this.GrpPolNo = aLCInsureAccFeeTraceSchema.getGrpPolNo();
		this.ContNo = aLCInsureAccFeeTraceSchema.getContNo();
		this.PolNo = aLCInsureAccFeeTraceSchema.getPolNo();
		this.SerialNo = aLCInsureAccFeeTraceSchema.getSerialNo();
		this.InsuAccNo = aLCInsureAccFeeTraceSchema.getInsuAccNo();
		this.RiskCode = aLCInsureAccFeeTraceSchema.getRiskCode();
		this.PayPlanCode = aLCInsureAccFeeTraceSchema.getPayPlanCode();
		this.OtherNo = aLCInsureAccFeeTraceSchema.getOtherNo();
		this.OtherType = aLCInsureAccFeeTraceSchema.getOtherType();
		this.AccAscription = aLCInsureAccFeeTraceSchema.getAccAscription();
		this.MoneyType = aLCInsureAccFeeTraceSchema.getMoneyType();
		this.FeeRate = aLCInsureAccFeeTraceSchema.getFeeRate();
		this.Fee = aLCInsureAccFeeTraceSchema.getFee();
		this.FeeUnit = aLCInsureAccFeeTraceSchema.getFeeUnit();
		this.PayDate = fDate.getDate( aLCInsureAccFeeTraceSchema.getPayDate());
		this.State = aLCInsureAccFeeTraceSchema.getState();
		this.FeeCode = aLCInsureAccFeeTraceSchema.getFeeCode();
		this.InerSerialNo = aLCInsureAccFeeTraceSchema.getInerSerialNo();
		this.ManageCom = aLCInsureAccFeeTraceSchema.getManageCom();
		this.Operator = aLCInsureAccFeeTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCInsureAccFeeTraceSchema.getMakeDate());
		this.MakeTime = aLCInsureAccFeeTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCInsureAccFeeTraceSchema.getModifyDate());
		this.ModifyTime = aLCInsureAccFeeTraceSchema.getModifyTime();
		this.PayNo = aLCInsureAccFeeTraceSchema.getPayNo();
		this.Currency = aLCInsureAccFeeTraceSchema.getCurrency();
		this.InvestType = aLCInsureAccFeeTraceSchema.getInvestType();
		this.AccalterNo = aLCInsureAccFeeTraceSchema.getAccalterNo();
		this.AccalterType = aLCInsureAccFeeTraceSchema.getAccalterType();
		this.ConfDate = fDate.getDate( aLCInsureAccFeeTraceSchema.getConfDate());
		this.AllocationDate = fDate.getDate( aLCInsureAccFeeTraceSchema.getAllocationDate());
		this.PricingDate = fDate.getDate( aLCInsureAccFeeTraceSchema.getPricingDate());
		this.ComCode = aLCInsureAccFeeTraceSchema.getComCode();
		this.ModifyOperator = aLCInsureAccFeeTraceSchema.getModifyOperator();
		this.NetAmount = aLCInsureAccFeeTraceSchema.getNetAmount();
		this.TaxAmount = aLCInsureAccFeeTraceSchema.getTaxAmount();
		this.Tax = aLCInsureAccFeeTraceSchema.getTax();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

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

			if( rs.getString("MoneyType") == null )
				this.MoneyType = null;
			else
				this.MoneyType = rs.getString("MoneyType").trim();

			this.FeeRate = rs.getDouble("FeeRate");
			this.Fee = rs.getDouble("Fee");
			this.FeeUnit = rs.getDouble("FeeUnit");
			this.PayDate = rs.getDate("PayDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			this.InerSerialNo = rs.getInt("InerSerialNo");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			if( rs.getString("PayNo") == null )
				this.PayNo = null;
			else
				this.PayNo = rs.getString("PayNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("AccalterNo") == null )
				this.AccalterNo = null;
			else
				this.AccalterNo = rs.getString("AccalterNo").trim();

			if( rs.getString("AccalterType") == null )
				this.AccalterType = null;
			else
				this.AccalterType = rs.getString("AccalterType").trim();

			this.ConfDate = rs.getDate("ConfDate");
			this.AllocationDate = rs.getDate("AllocationDate");
			this.PricingDate = rs.getDate("PricingDate");
			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.NetAmount = rs.getDouble("NetAmount");
			this.TaxAmount = rs.getDouble("TaxAmount");
			this.Tax = rs.getDouble("Tax");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCInsureAccFeeTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsureAccFeeTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCInsureAccFeeTraceSchema getSchema()
	{
		LCInsureAccFeeTraceSchema aLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
		aLCInsureAccFeeTraceSchema.setSchema(this);
		return aLCInsureAccFeeTraceSchema;
	}

	public LCInsureAccFeeTraceDB getDB()
	{
		LCInsureAccFeeTraceDB aDBOper = new LCInsureAccFeeTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAccFeeTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccAscription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoneyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InerSerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccalterNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccalterType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AllocationDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PricingDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAccFeeTrace>历史记账凭证主表信息</A>表字段
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
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OtherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccAscription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MoneyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			FeeUnit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InerSerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			PayNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AccalterNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AccalterType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			AllocationDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			PricingDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsureAccFeeTraceSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
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
		if (FCode.equalsIgnoreCase("MoneyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoneyType));
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
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("InerSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InerSerialNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("PayNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("AccalterNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccalterNo));
		}
		if (FCode.equalsIgnoreCase("AccalterType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccalterType));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("AllocationDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAllocationDate()));
		}
		if (FCode.equalsIgnoreCase("PricingDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPricingDate()));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("NetAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetAmount));
		}
		if (FCode.equalsIgnoreCase("TaxAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaxAmount));
		}
		if (FCode.equalsIgnoreCase("Tax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Tax));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OtherType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccAscription);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MoneyType);
				break;
			case 12:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 13:
				strFieldValue = String.valueOf(Fee);
				break;
			case 14:
				strFieldValue = String.valueOf(FeeUnit);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 18:
				strFieldValue = String.valueOf(InerSerialNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(PayNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AccalterNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AccalterType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAllocationDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPricingDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 35:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 36:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 37:
				strFieldValue = String.valueOf(Tax);
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("MoneyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoneyType = FValue.trim();
			}
			else
				MoneyType = null;
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
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayDate = fDate.getDate( FValue );
			}
			else
				PayDate = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("InerSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InerSerialNo = i;
			}
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
		if (FCode.equalsIgnoreCase("PayNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayNo = FValue.trim();
			}
			else
				PayNo = null;
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
		if (FCode.equalsIgnoreCase("AccalterNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccalterNo = FValue.trim();
			}
			else
				AccalterNo = null;
		}
		if (FCode.equalsIgnoreCase("AccalterType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccalterType = FValue.trim();
			}
			else
				AccalterType = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("AllocationDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AllocationDate = fDate.getDate( FValue );
			}
			else
				AllocationDate = null;
		}
		if (FCode.equalsIgnoreCase("PricingDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PricingDate = fDate.getDate( FValue );
			}
			else
				PricingDate = null;
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
		if (FCode.equalsIgnoreCase("NetAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NetAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("TaxAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TaxAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("Tax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Tax = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCInsureAccFeeTraceSchema other = (LCInsureAccFeeTraceSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& SerialNo.equals(other.getSerialNo())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& RiskCode.equals(other.getRiskCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherType.equals(other.getOtherType())
			&& AccAscription.equals(other.getAccAscription())
			&& MoneyType.equals(other.getMoneyType())
			&& FeeRate == other.getFeeRate()
			&& Fee == other.getFee()
			&& FeeUnit == other.getFeeUnit()
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& State.equals(other.getState())
			&& FeeCode.equals(other.getFeeCode())
			&& InerSerialNo == other.getInerSerialNo()
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PayNo.equals(other.getPayNo())
			&& Currency.equals(other.getCurrency())
			&& InvestType.equals(other.getInvestType())
			&& AccalterNo.equals(other.getAccalterNo())
			&& AccalterType.equals(other.getAccalterType())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& fDate.getString(AllocationDate).equals(other.getAllocationDate())
			&& fDate.getString(PricingDate).equals(other.getPricingDate())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& NetAmount == other.getNetAmount()
			&& TaxAmount == other.getTaxAmount()
			&& Tax == other.getTax();
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
		if( strFieldName.equals("PolNo") ) {
			return 3;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 7;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 8;
		}
		if( strFieldName.equals("OtherType") ) {
			return 9;
		}
		if( strFieldName.equals("AccAscription") ) {
			return 10;
		}
		if( strFieldName.equals("MoneyType") ) {
			return 11;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 12;
		}
		if( strFieldName.equals("Fee") ) {
			return 13;
		}
		if( strFieldName.equals("FeeUnit") ) {
			return 14;
		}
		if( strFieldName.equals("PayDate") ) {
			return 15;
		}
		if( strFieldName.equals("State") ) {
			return 16;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 17;
		}
		if( strFieldName.equals("InerSerialNo") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
		}
		if( strFieldName.equals("PayNo") ) {
			return 25;
		}
		if( strFieldName.equals("Currency") ) {
			return 26;
		}
		if( strFieldName.equals("InvestType") ) {
			return 27;
		}
		if( strFieldName.equals("AccalterNo") ) {
			return 28;
		}
		if( strFieldName.equals("AccalterType") ) {
			return 29;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 30;
		}
		if( strFieldName.equals("AllocationDate") ) {
			return 31;
		}
		if( strFieldName.equals("PricingDate") ) {
			return 32;
		}
		if( strFieldName.equals("ComCode") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 34;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 35;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 36;
		}
		if( strFieldName.equals("Tax") ) {
			return 37;
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
				strFieldName = "PolNo";
				break;
			case 4:
				strFieldName = "SerialNo";
				break;
			case 5:
				strFieldName = "InsuAccNo";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "PayPlanCode";
				break;
			case 8:
				strFieldName = "OtherNo";
				break;
			case 9:
				strFieldName = "OtherType";
				break;
			case 10:
				strFieldName = "AccAscription";
				break;
			case 11:
				strFieldName = "MoneyType";
				break;
			case 12:
				strFieldName = "FeeRate";
				break;
			case 13:
				strFieldName = "Fee";
				break;
			case 14:
				strFieldName = "FeeUnit";
				break;
			case 15:
				strFieldName = "PayDate";
				break;
			case 16:
				strFieldName = "State";
				break;
			case 17:
				strFieldName = "FeeCode";
				break;
			case 18:
				strFieldName = "InerSerialNo";
				break;
			case 19:
				strFieldName = "ManageCom";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
				break;
			case 25:
				strFieldName = "PayNo";
				break;
			case 26:
				strFieldName = "Currency";
				break;
			case 27:
				strFieldName = "InvestType";
				break;
			case 28:
				strFieldName = "AccalterNo";
				break;
			case 29:
				strFieldName = "AccalterType";
				break;
			case 30:
				strFieldName = "ConfDate";
				break;
			case 31:
				strFieldName = "AllocationDate";
				break;
			case 32:
				strFieldName = "PricingDate";
				break;
			case 33:
				strFieldName = "ComCode";
				break;
			case 34:
				strFieldName = "ModifyOperator";
				break;
			case 35:
				strFieldName = "NetAmount";
				break;
			case 36:
				strFieldName = "TaxAmount";
				break;
			case 37:
				strFieldName = "Tax";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
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
		if( strFieldName.equals("MoneyType") ) {
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
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InerSerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("PayNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccalterNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccalterType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AllocationDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PricingDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NetAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Tax") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
