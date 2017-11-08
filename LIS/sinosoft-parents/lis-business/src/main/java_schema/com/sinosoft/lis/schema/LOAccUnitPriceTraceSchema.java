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
import com.sinosoft.lis.db.LOAccUnitPriceTraceDB;

/*
 * <p>ClassName: LOAccUnitPriceTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LOAccUnitPriceTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOAccUnitPriceTraceSchema.class);

	// @Field
	/** 计价修改次数 */
	private int SerialNo;
	/** 险种编码 */
	private String RiskCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 价格起期 */
	private Date StartDate;
	/** 价格止期 */
	private Date EndDate;
	/** 投资收益类型 */
	private String InvestFlag;
	/** 价格应公布日期 */
	private Date SRateDate;
	/** 价格实际公布日期 */
	private Date ARateDate;
	/** 单位买入价格 */
	private double UnitPriceBuy;
	/** 单位卖出价格 */
	private double UnitPriceSell;
	/** 赎回比例 */
	private double RedeemRate;
	/** 赎回金额 */
	private double RedeemMoney;
	/** 修改类型 */
	private String Type;
	/** 修改原因 */
	private String Reason;
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
	/** 账户总资产 */
	private double InsuTotalMoney;
	/** 负债 */
	private double Liabilities;
	/** 未实现利得营业税 */
	private double OtherTax;
	/** 账户资产管理费 */
	private double AccasManageFee;
	/** 公司投资单位数 */
	private double CompanyUnitCount;
	/** 公司本次变动单位数 */
	private double ComChgUnitCount;
	/** 客户投资单位数 */
	private double CustomersUnitCount;
	/** 收缩扩张标志 */
	private String SKFlag;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOAccUnitPriceTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "SerialNo";
		pk[1] = "RiskCode";
		pk[2] = "InsuAccNo";
		pk[3] = "StartDate";

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
		LOAccUnitPriceTraceSchema cloned = (LOAccUnitPriceTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
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
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
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

	/**
	* 1--金额类型<p>
	* 2--份额类型
	*/
	public String getInvestFlag()
	{
		return InvestFlag;
	}
	public void setInvestFlag(String aInvestFlag)
	{
		if(aInvestFlag!=null && aInvestFlag.length()>1)
			throw new IllegalArgumentException("投资收益类型InvestFlag值"+aInvestFlag+"的长度"+aInvestFlag.length()+"大于最大值1");
		InvestFlag = aInvestFlag;
	}
	public String getSRateDate()
	{
		if( SRateDate != null )
			return fDate.getString(SRateDate);
		else
			return null;
	}
	public void setSRateDate(Date aSRateDate)
	{
		SRateDate = aSRateDate;
	}
	public void setSRateDate(String aSRateDate)
	{
		if (aSRateDate != null && !aSRateDate.equals("") )
		{
			SRateDate = fDate.getDate( aSRateDate );
		}
		else
			SRateDate = null;
	}

	public String getARateDate()
	{
		if( ARateDate != null )
			return fDate.getString(ARateDate);
		else
			return null;
	}
	public void setARateDate(Date aARateDate)
	{
		ARateDate = aARateDate;
	}
	public void setARateDate(String aARateDate)
	{
		if (aARateDate != null && !aARateDate.equals("") )
		{
			ARateDate = fDate.getDate( aARateDate );
		}
		else
			ARateDate = null;
	}

	public double getUnitPriceBuy()
	{
		return UnitPriceBuy;
	}
	public void setUnitPriceBuy(double aUnitPriceBuy)
	{
		UnitPriceBuy = aUnitPriceBuy;
	}
	public void setUnitPriceBuy(String aUnitPriceBuy)
	{
		if (aUnitPriceBuy != null && !aUnitPriceBuy.equals(""))
		{
			Double tDouble = new Double(aUnitPriceBuy);
			double d = tDouble.doubleValue();
			UnitPriceBuy = d;
		}
	}

	public double getUnitPriceSell()
	{
		return UnitPriceSell;
	}
	public void setUnitPriceSell(double aUnitPriceSell)
	{
		UnitPriceSell = aUnitPriceSell;
	}
	public void setUnitPriceSell(String aUnitPriceSell)
	{
		if (aUnitPriceSell != null && !aUnitPriceSell.equals(""))
		{
			Double tDouble = new Double(aUnitPriceSell);
			double d = tDouble.doubleValue();
			UnitPriceSell = d;
		}
	}

	public double getRedeemRate()
	{
		return RedeemRate;
	}
	public void setRedeemRate(double aRedeemRate)
	{
		RedeemRate = aRedeemRate;
	}
	public void setRedeemRate(String aRedeemRate)
	{
		if (aRedeemRate != null && !aRedeemRate.equals(""))
		{
			Double tDouble = new Double(aRedeemRate);
			double d = tDouble.doubleValue();
			RedeemRate = d;
		}
	}

	public double getRedeemMoney()
	{
		return RedeemMoney;
	}
	public void setRedeemMoney(double aRedeemMoney)
	{
		RedeemMoney = aRedeemMoney;
	}
	public void setRedeemMoney(String aRedeemMoney)
	{
		if (aRedeemMoney != null && !aRedeemMoney.equals(""))
		{
			Double tDouble = new Double(aRedeemMoney);
			double d = tDouble.doubleValue();
			RedeemMoney = d;
		}
	}

	/**
	* 修改原因分类<p>
	* 00-录入错误<p>
	* 01-复核不通过<p>
	* 02-
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		if(aType!=null && aType.length()>2)
			throw new IllegalArgumentException("修改类型Type值"+aType+"的长度"+aType.length()+"大于最大值2");
		Type = aType;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>500)
			throw new IllegalArgumentException("修改原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值500");
		Reason = aReason;
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
	public double getInsuTotalMoney()
	{
		return InsuTotalMoney;
	}
	public void setInsuTotalMoney(double aInsuTotalMoney)
	{
		InsuTotalMoney = aInsuTotalMoney;
	}
	public void setInsuTotalMoney(String aInsuTotalMoney)
	{
		if (aInsuTotalMoney != null && !aInsuTotalMoney.equals(""))
		{
			Double tDouble = new Double(aInsuTotalMoney);
			double d = tDouble.doubleValue();
			InsuTotalMoney = d;
		}
	}

	public double getLiabilities()
	{
		return Liabilities;
	}
	public void setLiabilities(double aLiabilities)
	{
		Liabilities = aLiabilities;
	}
	public void setLiabilities(String aLiabilities)
	{
		if (aLiabilities != null && !aLiabilities.equals(""))
		{
			Double tDouble = new Double(aLiabilities);
			double d = tDouble.doubleValue();
			Liabilities = d;
		}
	}

	public double getOtherTax()
	{
		return OtherTax;
	}
	public void setOtherTax(double aOtherTax)
	{
		OtherTax = aOtherTax;
	}
	public void setOtherTax(String aOtherTax)
	{
		if (aOtherTax != null && !aOtherTax.equals(""))
		{
			Double tDouble = new Double(aOtherTax);
			double d = tDouble.doubleValue();
			OtherTax = d;
		}
	}

	public double getAccasManageFee()
	{
		return AccasManageFee;
	}
	public void setAccasManageFee(double aAccasManageFee)
	{
		AccasManageFee = aAccasManageFee;
	}
	public void setAccasManageFee(String aAccasManageFee)
	{
		if (aAccasManageFee != null && !aAccasManageFee.equals(""))
		{
			Double tDouble = new Double(aAccasManageFee);
			double d = tDouble.doubleValue();
			AccasManageFee = d;
		}
	}

	public double getCompanyUnitCount()
	{
		return CompanyUnitCount;
	}
	public void setCompanyUnitCount(double aCompanyUnitCount)
	{
		CompanyUnitCount = aCompanyUnitCount;
	}
	public void setCompanyUnitCount(String aCompanyUnitCount)
	{
		if (aCompanyUnitCount != null && !aCompanyUnitCount.equals(""))
		{
			Double tDouble = new Double(aCompanyUnitCount);
			double d = tDouble.doubleValue();
			CompanyUnitCount = d;
		}
	}

	public double getComChgUnitCount()
	{
		return ComChgUnitCount;
	}
	public void setComChgUnitCount(double aComChgUnitCount)
	{
		ComChgUnitCount = aComChgUnitCount;
	}
	public void setComChgUnitCount(String aComChgUnitCount)
	{
		if (aComChgUnitCount != null && !aComChgUnitCount.equals(""))
		{
			Double tDouble = new Double(aComChgUnitCount);
			double d = tDouble.doubleValue();
			ComChgUnitCount = d;
		}
	}

	public double getCustomersUnitCount()
	{
		return CustomersUnitCount;
	}
	public void setCustomersUnitCount(double aCustomersUnitCount)
	{
		CustomersUnitCount = aCustomersUnitCount;
	}
	public void setCustomersUnitCount(String aCustomersUnitCount)
	{
		if (aCustomersUnitCount != null && !aCustomersUnitCount.equals(""))
		{
			Double tDouble = new Double(aCustomersUnitCount);
			double d = tDouble.doubleValue();
			CustomersUnitCount = d;
		}
	}

	/**
	* 1-收缩期<p>
	* 2-扩张期
	*/
	public String getSKFlag()
	{
		return SKFlag;
	}
	public void setSKFlag(String aSKFlag)
	{
		if(aSKFlag!=null && aSKFlag.length()>1)
			throw new IllegalArgumentException("收缩扩张标志SKFlag值"+aSKFlag+"的长度"+aSKFlag.length()+"大于最大值1");
		SKFlag = aSKFlag;
	}

	/**
	* 使用另外一个 LOAccUnitPriceTraceSchema 对象给 Schema 赋值
	* @param: aLOAccUnitPriceTraceSchema LOAccUnitPriceTraceSchema
	**/
	public void setSchema(LOAccUnitPriceTraceSchema aLOAccUnitPriceTraceSchema)
	{
		this.SerialNo = aLOAccUnitPriceTraceSchema.getSerialNo();
		this.RiskCode = aLOAccUnitPriceTraceSchema.getRiskCode();
		this.InsuAccNo = aLOAccUnitPriceTraceSchema.getInsuAccNo();
		this.StartDate = fDate.getDate( aLOAccUnitPriceTraceSchema.getStartDate());
		this.EndDate = fDate.getDate( aLOAccUnitPriceTraceSchema.getEndDate());
		this.InvestFlag = aLOAccUnitPriceTraceSchema.getInvestFlag();
		this.SRateDate = fDate.getDate( aLOAccUnitPriceTraceSchema.getSRateDate());
		this.ARateDate = fDate.getDate( aLOAccUnitPriceTraceSchema.getARateDate());
		this.UnitPriceBuy = aLOAccUnitPriceTraceSchema.getUnitPriceBuy();
		this.UnitPriceSell = aLOAccUnitPriceTraceSchema.getUnitPriceSell();
		this.RedeemRate = aLOAccUnitPriceTraceSchema.getRedeemRate();
		this.RedeemMoney = aLOAccUnitPriceTraceSchema.getRedeemMoney();
		this.Type = aLOAccUnitPriceTraceSchema.getType();
		this.Reason = aLOAccUnitPriceTraceSchema.getReason();
		this.Operator = aLOAccUnitPriceTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOAccUnitPriceTraceSchema.getMakeDate());
		this.MakeTime = aLOAccUnitPriceTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOAccUnitPriceTraceSchema.getModifyDate());
		this.ModifyTime = aLOAccUnitPriceTraceSchema.getModifyTime();
		this.InsuTotalMoney = aLOAccUnitPriceTraceSchema.getInsuTotalMoney();
		this.Liabilities = aLOAccUnitPriceTraceSchema.getLiabilities();
		this.OtherTax = aLOAccUnitPriceTraceSchema.getOtherTax();
		this.AccasManageFee = aLOAccUnitPriceTraceSchema.getAccasManageFee();
		this.CompanyUnitCount = aLOAccUnitPriceTraceSchema.getCompanyUnitCount();
		this.ComChgUnitCount = aLOAccUnitPriceTraceSchema.getComChgUnitCount();
		this.CustomersUnitCount = aLOAccUnitPriceTraceSchema.getCustomersUnitCount();
		this.SKFlag = aLOAccUnitPriceTraceSchema.getSKFlag();
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
			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("InvestFlag") == null )
				this.InvestFlag = null;
			else
				this.InvestFlag = rs.getString("InvestFlag").trim();

			this.SRateDate = rs.getDate("SRateDate");
			this.ARateDate = rs.getDate("ARateDate");
			this.UnitPriceBuy = rs.getDouble("UnitPriceBuy");
			this.UnitPriceSell = rs.getDouble("UnitPriceSell");
			this.RedeemRate = rs.getDouble("RedeemRate");
			this.RedeemMoney = rs.getDouble("RedeemMoney");
			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

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

			this.InsuTotalMoney = rs.getDouble("InsuTotalMoney");
			this.Liabilities = rs.getDouble("Liabilities");
			this.OtherTax = rs.getDouble("OtherTax");
			this.AccasManageFee = rs.getDouble("AccasManageFee");
			this.CompanyUnitCount = rs.getDouble("CompanyUnitCount");
			this.ComChgUnitCount = rs.getDouble("ComChgUnitCount");
			this.CustomersUnitCount = rs.getDouble("CustomersUnitCount");
			if( rs.getString("SKFlag") == null )
				this.SKFlag = null;
			else
				this.SKFlag = rs.getString("SKFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOAccUnitPriceTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOAccUnitPriceTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOAccUnitPriceTraceSchema getSchema()
	{
		LOAccUnitPriceTraceSchema aLOAccUnitPriceTraceSchema = new LOAccUnitPriceTraceSchema();
		aLOAccUnitPriceTraceSchema.setSchema(this);
		return aLOAccUnitPriceTraceSchema;
	}

	public LOAccUnitPriceTraceDB getDB()
	{
		LOAccUnitPriceTraceDB aDBOper = new LOAccUnitPriceTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOAccUnitPriceTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SRateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ARateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UnitPriceBuy));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UnitPriceSell));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RedeemRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RedeemMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuTotalMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Liabilities));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherTax));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccasManageFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CompanyUnitCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ComChgUnitCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CustomersUnitCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SKFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOAccUnitPriceTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			InvestFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SRateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ARateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			UnitPriceBuy = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			UnitPriceSell = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			RedeemRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			RedeemMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			InsuTotalMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Liabilities = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			OtherTax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			AccasManageFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			CompanyUnitCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			ComChgUnitCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			CustomersUnitCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			SKFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOAccUnitPriceTraceSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestFlag));
		}
		if (FCode.equalsIgnoreCase("SRateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSRateDate()));
		}
		if (FCode.equalsIgnoreCase("ARateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getARateDate()));
		}
		if (FCode.equalsIgnoreCase("UnitPriceBuy"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitPriceBuy));
		}
		if (FCode.equalsIgnoreCase("UnitPriceSell"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitPriceSell));
		}
		if (FCode.equalsIgnoreCase("RedeemRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RedeemRate));
		}
		if (FCode.equalsIgnoreCase("RedeemMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RedeemMoney));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
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
		if (FCode.equalsIgnoreCase("InsuTotalMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuTotalMoney));
		}
		if (FCode.equalsIgnoreCase("Liabilities"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Liabilities));
		}
		if (FCode.equalsIgnoreCase("OtherTax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherTax));
		}
		if (FCode.equalsIgnoreCase("AccasManageFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccasManageFee));
		}
		if (FCode.equalsIgnoreCase("CompanyUnitCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyUnitCount));
		}
		if (FCode.equalsIgnoreCase("ComChgUnitCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComChgUnitCount));
		}
		if (FCode.equalsIgnoreCase("CustomersUnitCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomersUnitCount));
		}
		if (FCode.equalsIgnoreCase("SKFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SKFlag));
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
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InvestFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSRateDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getARateDate()));
				break;
			case 8:
				strFieldValue = String.valueOf(UnitPriceBuy);
				break;
			case 9:
				strFieldValue = String.valueOf(UnitPriceSell);
				break;
			case 10:
				strFieldValue = String.valueOf(RedeemRate);
				break;
			case 11:
				strFieldValue = String.valueOf(RedeemMoney);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = String.valueOf(InsuTotalMoney);
				break;
			case 20:
				strFieldValue = String.valueOf(Liabilities);
				break;
			case 21:
				strFieldValue = String.valueOf(OtherTax);
				break;
			case 22:
				strFieldValue = String.valueOf(AccasManageFee);
				break;
			case 23:
				strFieldValue = String.valueOf(CompanyUnitCount);
				break;
			case 24:
				strFieldValue = String.valueOf(ComChgUnitCount);
				break;
			case 25:
				strFieldValue = String.valueOf(CustomersUnitCount);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(SKFlag);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
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
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestFlag = FValue.trim();
			}
			else
				InvestFlag = null;
		}
		if (FCode.equalsIgnoreCase("SRateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SRateDate = fDate.getDate( FValue );
			}
			else
				SRateDate = null;
		}
		if (FCode.equalsIgnoreCase("ARateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ARateDate = fDate.getDate( FValue );
			}
			else
				ARateDate = null;
		}
		if (FCode.equalsIgnoreCase("UnitPriceBuy"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnitPriceBuy = d;
			}
		}
		if (FCode.equalsIgnoreCase("UnitPriceSell"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnitPriceSell = d;
			}
		}
		if (FCode.equalsIgnoreCase("RedeemRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RedeemRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RedeemMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RedeemMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
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
		if (FCode.equalsIgnoreCase("InsuTotalMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuTotalMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("Liabilities"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Liabilities = d;
			}
		}
		if (FCode.equalsIgnoreCase("OtherTax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OtherTax = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccasManageFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccasManageFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("CompanyUnitCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CompanyUnitCount = d;
			}
		}
		if (FCode.equalsIgnoreCase("ComChgUnitCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ComChgUnitCount = d;
			}
		}
		if (FCode.equalsIgnoreCase("CustomersUnitCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CustomersUnitCount = d;
			}
		}
		if (FCode.equalsIgnoreCase("SKFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SKFlag = FValue.trim();
			}
			else
				SKFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOAccUnitPriceTraceSchema other = (LOAccUnitPriceTraceSchema)otherObject;
		return
			SerialNo == other.getSerialNo()
			&& RiskCode.equals(other.getRiskCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& InvestFlag.equals(other.getInvestFlag())
			&& fDate.getString(SRateDate).equals(other.getSRateDate())
			&& fDate.getString(ARateDate).equals(other.getARateDate())
			&& UnitPriceBuy == other.getUnitPriceBuy()
			&& UnitPriceSell == other.getUnitPriceSell()
			&& RedeemRate == other.getRedeemRate()
			&& RedeemMoney == other.getRedeemMoney()
			&& Type.equals(other.getType())
			&& Reason.equals(other.getReason())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& InsuTotalMoney == other.getInsuTotalMoney()
			&& Liabilities == other.getLiabilities()
			&& OtherTax == other.getOtherTax()
			&& AccasManageFee == other.getAccasManageFee()
			&& CompanyUnitCount == other.getCompanyUnitCount()
			&& ComChgUnitCount == other.getComChgUnitCount()
			&& CustomersUnitCount == other.getCustomersUnitCount()
			&& SKFlag.equals(other.getSKFlag());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 2;
		}
		if( strFieldName.equals("StartDate") ) {
			return 3;
		}
		if( strFieldName.equals("EndDate") ) {
			return 4;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return 5;
		}
		if( strFieldName.equals("SRateDate") ) {
			return 6;
		}
		if( strFieldName.equals("ARateDate") ) {
			return 7;
		}
		if( strFieldName.equals("UnitPriceBuy") ) {
			return 8;
		}
		if( strFieldName.equals("UnitPriceSell") ) {
			return 9;
		}
		if( strFieldName.equals("RedeemRate") ) {
			return 10;
		}
		if( strFieldName.equals("RedeemMoney") ) {
			return 11;
		}
		if( strFieldName.equals("Type") ) {
			return 12;
		}
		if( strFieldName.equals("Reason") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("InsuTotalMoney") ) {
			return 19;
		}
		if( strFieldName.equals("Liabilities") ) {
			return 20;
		}
		if( strFieldName.equals("OtherTax") ) {
			return 21;
		}
		if( strFieldName.equals("AccasManageFee") ) {
			return 22;
		}
		if( strFieldName.equals("CompanyUnitCount") ) {
			return 23;
		}
		if( strFieldName.equals("ComChgUnitCount") ) {
			return 24;
		}
		if( strFieldName.equals("CustomersUnitCount") ) {
			return 25;
		}
		if( strFieldName.equals("SKFlag") ) {
			return 26;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "InsuAccNo";
				break;
			case 3:
				strFieldName = "StartDate";
				break;
			case 4:
				strFieldName = "EndDate";
				break;
			case 5:
				strFieldName = "InvestFlag";
				break;
			case 6:
				strFieldName = "SRateDate";
				break;
			case 7:
				strFieldName = "ARateDate";
				break;
			case 8:
				strFieldName = "UnitPriceBuy";
				break;
			case 9:
				strFieldName = "UnitPriceSell";
				break;
			case 10:
				strFieldName = "RedeemRate";
				break;
			case 11:
				strFieldName = "RedeemMoney";
				break;
			case 12:
				strFieldName = "Type";
				break;
			case 13:
				strFieldName = "Reason";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "InsuTotalMoney";
				break;
			case 20:
				strFieldName = "Liabilities";
				break;
			case 21:
				strFieldName = "OtherTax";
				break;
			case 22:
				strFieldName = "AccasManageFee";
				break;
			case 23:
				strFieldName = "CompanyUnitCount";
				break;
			case 24:
				strFieldName = "ComChgUnitCount";
				break;
			case 25:
				strFieldName = "CustomersUnitCount";
				break;
			case 26:
				strFieldName = "SKFlag";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SRateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ARateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UnitPriceBuy") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UnitPriceSell") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RedeemRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RedeemMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
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
		if( strFieldName.equals("InsuTotalMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Liabilities") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OtherTax") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccasManageFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CompanyUnitCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ComChgUnitCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CustomersUnitCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SKFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
