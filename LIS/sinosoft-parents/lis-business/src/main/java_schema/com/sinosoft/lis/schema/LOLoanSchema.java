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
import com.sinosoft.lis.db.LOLoanDB;

/*
 * <p>ClassName: LOLoanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保
 */
public class LOLoanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOLoanSchema.class);

	// @Field
	/** 保单号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 批单号 */
	private String EdorNo;
	/** 流水号码 */
	private String SerialNo;
	/** 实付号码 */
	private String ActuGetNo;
	/** 业务类型 */
	private String LoanType;
	/** 顺序号码 */
	private String OrderNo;
	/** 借款日期 */
	private Date LoanDate;
	/** 还清日期 */
	private Date PayOffDate;
	/** 总金额 */
	private double SumMoney;
	/** 按录入还是描述计算 */
	private String InputFlag;
	/** 借款利息方式 */
	private String InterestType;
	/** 默认借款利率 */
	private double InterestRate;
	/** 利率类型 */
	private String InterestMode;
	/** 计算利率类型 */
	private String RateCalType;
	/** 计算利率编码 */
	private String RateCalCode;
	/** 是否按固定利率计算 */
	private String SpecifyRate;
	/** 余额 */
	private double LeaveMoney;
	/** 还清标志 */
	private String PayOffFlag;
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
	/** 预计利息 */
	private double PreInterest;
	/** 本次借款金额 */
	private double CurLoanMoney;
	/** 上次累计借款金额 */
	private double LastSumLoanMoney;
	/** 垫至日期 */
	private Date LoanToDate;
	/** 新计息日期 */
	private Date NewLoanDate;
	/** 保单现价是否能垫整期标记 */
	private String ZqFlag;
	/** 立案号 */
	private String ClmNo;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOLoanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ContNo";
		pk[1] = "PolNo";
		pk[2] = "EdorNo";
		pk[3] = "Currency";

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
		LOLoanSchema cloned = (LOLoanSchema)super.clone();
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
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("保单号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
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
	/**
	* 自动垫缴中，借款业务表中的该字段<p>
	* 和流水号码相同。
	*/
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
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>10)
			throw new IllegalArgumentException("流水号码SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值10");
		SerialNo = aSerialNo;
	}
	public String getActuGetNo()
	{
		return ActuGetNo;
	}
	public void setActuGetNo(String aActuGetNo)
	{
		if(aActuGetNo!=null && aActuGetNo.length()>20)
			throw new IllegalArgumentException("实付号码ActuGetNo值"+aActuGetNo+"的长度"+aActuGetNo.length()+"大于最大值20");
		ActuGetNo = aActuGetNo;
	}
	/**
	* 0 －－ 借款<p>
	* 1 －－ 垫交
	*/
	public String getLoanType()
	{
		return LoanType;
	}
	public void setLoanType(String aLoanType)
	{
		if(aLoanType!=null && aLoanType.length()>1)
			throw new IllegalArgumentException("业务类型LoanType值"+aLoanType+"的长度"+aLoanType.length()+"大于最大值1");
		LoanType = aLoanType;
	}
	/**
	* 自动垫缴程序中不填写。
	*/
	public String getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		if(aOrderNo!=null && aOrderNo.length()>10)
			throw new IllegalArgumentException("顺序号码OrderNo值"+aOrderNo+"的长度"+aOrderNo.length()+"大于最大值10");
		OrderNo = aOrderNo;
	}
	public String getLoanDate()
	{
		if( LoanDate != null )
			return fDate.getString(LoanDate);
		else
			return null;
	}
	public void setLoanDate(Date aLoanDate)
	{
		LoanDate = aLoanDate;
	}
	public void setLoanDate(String aLoanDate)
	{
		if (aLoanDate != null && !aLoanDate.equals("") )
		{
			LoanDate = fDate.getDate( aLoanDate );
		}
		else
			LoanDate = null;
	}

	public String getPayOffDate()
	{
		if( PayOffDate != null )
			return fDate.getString(PayOffDate);
		else
			return null;
	}
	public void setPayOffDate(Date aPayOffDate)
	{
		PayOffDate = aPayOffDate;
	}
	public void setPayOffDate(String aPayOffDate)
	{
		if (aPayOffDate != null && !aPayOffDate.equals("") )
		{
			PayOffDate = fDate.getDate( aPayOffDate );
		}
		else
			PayOffDate = null;
	}

	public double getSumMoney()
	{
		return SumMoney;
	}
	public void setSumMoney(double aSumMoney)
	{
		SumMoney = aSumMoney;
	}
	public void setSumMoney(String aSumMoney)
	{
		if (aSumMoney != null && !aSumMoney.equals(""))
		{
			Double tDouble = new Double(aSumMoney);
			double d = tDouble.doubleValue();
			SumMoney = d;
		}
	}

	/**
	* 1 －－ 表示按照录入进行利息计算<p>
	* 2 －－ 表示按照描述进行利息计算
	*/
	public String getInputFlag()
	{
		return InputFlag;
	}
	public void setInputFlag(String aInputFlag)
	{
		if(aInputFlag!=null && aInputFlag.length()>1)
			throw new IllegalArgumentException("按录入还是描述计算InputFlag值"+aInputFlag+"的长度"+aInputFlag.length()+"大于最大值1");
		InputFlag = aInputFlag;
	}
	/**
	* 1 －－ 按单利计算<p>
	* 2 －－ 按复利计算
	*/
	public String getInterestType()
	{
		return InterestType;
	}
	public void setInterestType(String aInterestType)
	{
		if(aInterestType!=null && aInterestType.length()>2)
			throw new IllegalArgumentException("借款利息方式InterestType值"+aInterestType+"的长度"+aInterestType.length()+"大于最大值2");
		InterestType = aInterestType;
	}
	/**
	* 指默认固定利率，针对固定利率
	*/
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

	/**
	* 针对固定利率<p>
	* 该字段对于浮动利率没有意义，在浮动利率描述表中已经能够体现，该字段只是对固定利率有意义。<p>
	* 1 －－年利率<p>
	* 2 －－月利率<p>
	* 3 －－日利率
	*/
	public String getInterestMode()
	{
		return InterestMode;
	}
	public void setInterestMode(String aInterestMode)
	{
		if(aInterestMode!=null && aInterestMode.length()>1)
			throw new IllegalArgumentException("利率类型InterestMode值"+aInterestMode+"的长度"+aInterestMode.length()+"大于最大值1");
		InterestMode = aInterestMode;
	}
	/**
	* 针对浮动利率<p>
	* 1 －－ 表示按照浮动利率表进行计算（在计算利率方法字段中描述的是浮动利率表名）<p>
	* 2 －－ 表示按照一个计算编码计算。
	*/
	public String getRateCalType()
	{
		return RateCalType;
	}
	public void setRateCalType(String aRateCalType)
	{
		if(aRateCalType!=null && aRateCalType.length()>1)
			throw new IllegalArgumentException("计算利率类型RateCalType值"+aRateCalType+"的长度"+aRateCalType.length()+"大于最大值1");
		RateCalType = aRateCalType;
	}
	/**
	* 针对浮动利率
	*/
	public String getRateCalCode()
	{
		return RateCalCode;
	}
	public void setRateCalCode(String aRateCalCode)
	{
		if(aRateCalCode!=null && aRateCalCode.length()>20)
			throw new IllegalArgumentException("计算利率编码RateCalCode值"+aRateCalCode+"的长度"+aRateCalCode.length()+"大于最大值20");
		RateCalCode = aRateCalCode;
	}
	/**
	* 1 －－ 按固定利率计算<p>
	* 2 －－ 按浮动利率计算
	*/
	public String getSpecifyRate()
	{
		return SpecifyRate;
	}
	public void setSpecifyRate(String aSpecifyRate)
	{
		if(aSpecifyRate!=null && aSpecifyRate.length()>1)
			throw new IllegalArgumentException("是否按固定利率计算SpecifyRate值"+aSpecifyRate+"的长度"+aSpecifyRate.length()+"大于最大值1");
		SpecifyRate = aSpecifyRate;
	}
	public double getLeaveMoney()
	{
		return LeaveMoney;
	}
	public void setLeaveMoney(double aLeaveMoney)
	{
		LeaveMoney = aLeaveMoney;
	}
	public void setLeaveMoney(String aLeaveMoney)
	{
		if (aLeaveMoney != null && !aLeaveMoney.equals(""))
		{
			Double tDouble = new Double(aLeaveMoney);
			double d = tDouble.doubleValue();
			LeaveMoney = d;
		}
	}

	/**
	* 0 －－ 未还清<p>
	* 1 －－ 还清
	*/
	public String getPayOffFlag()
	{
		return PayOffFlag;
	}
	public void setPayOffFlag(String aPayOffFlag)
	{
		if(aPayOffFlag!=null && aPayOffFlag.length()>1)
			throw new IllegalArgumentException("还清标志PayOffFlag值"+aPayOffFlag+"的长度"+aPayOffFlag.length()+"大于最大值1");
		PayOffFlag = aPayOffFlag;
	}
	/**
	* 对于自动垫缴的处理，该字段填写 000
	*/
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
	public double getPreInterest()
	{
		return PreInterest;
	}
	public void setPreInterest(double aPreInterest)
	{
		PreInterest = aPreInterest;
	}
	public void setPreInterest(String aPreInterest)
	{
		if (aPreInterest != null && !aPreInterest.equals(""))
		{
			Double tDouble = new Double(aPreInterest);
			double d = tDouble.doubleValue();
			PreInterest = d;
		}
	}

	public double getCurLoanMoney()
	{
		return CurLoanMoney;
	}
	public void setCurLoanMoney(double aCurLoanMoney)
	{
		CurLoanMoney = aCurLoanMoney;
	}
	public void setCurLoanMoney(String aCurLoanMoney)
	{
		if (aCurLoanMoney != null && !aCurLoanMoney.equals(""))
		{
			Double tDouble = new Double(aCurLoanMoney);
			double d = tDouble.doubleValue();
			CurLoanMoney = d;
		}
	}

	public double getLastSumLoanMoney()
	{
		return LastSumLoanMoney;
	}
	public void setLastSumLoanMoney(double aLastSumLoanMoney)
	{
		LastSumLoanMoney = aLastSumLoanMoney;
	}
	public void setLastSumLoanMoney(String aLastSumLoanMoney)
	{
		if (aLastSumLoanMoney != null && !aLastSumLoanMoney.equals(""))
		{
			Double tDouble = new Double(aLastSumLoanMoney);
			double d = tDouble.doubleValue();
			LastSumLoanMoney = d;
		}
	}

	public String getLoanToDate()
	{
		if( LoanToDate != null )
			return fDate.getString(LoanToDate);
		else
			return null;
	}
	public void setLoanToDate(Date aLoanToDate)
	{
		LoanToDate = aLoanToDate;
	}
	public void setLoanToDate(String aLoanToDate)
	{
		if (aLoanToDate != null && !aLoanToDate.equals("") )
		{
			LoanToDate = fDate.getDate( aLoanToDate );
		}
		else
			LoanToDate = null;
	}

	/**
	* 用于记录每次部分还款时，还清利息后和部分本金后的借款日期
	*/
	public String getNewLoanDate()
	{
		if( NewLoanDate != null )
			return fDate.getString(NewLoanDate);
		else
			return null;
	}
	public void setNewLoanDate(Date aNewLoanDate)
	{
		NewLoanDate = aNewLoanDate;
	}
	public void setNewLoanDate(String aNewLoanDate)
	{
		if (aNewLoanDate != null && !aNewLoanDate.equals("") )
		{
			NewLoanDate = fDate.getDate( aNewLoanDate );
		}
		else
			NewLoanDate = null;
	}

	/**
	* 0，不能垫整期;<p>
	* 1，能垫整期;
	*/
	public String getZqFlag()
	{
		return ZqFlag;
	}
	public void setZqFlag(String aZqFlag)
	{
		if(aZqFlag!=null && aZqFlag.length()>1)
			throw new IllegalArgumentException("保单现价是否能垫整期标记ZqFlag值"+aZqFlag+"的长度"+aZqFlag.length()+"大于最大值1");
		ZqFlag = aZqFlag;
	}
	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("立案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
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

	/**
	* 使用另外一个 LOLoanSchema 对象给 Schema 赋值
	* @param: aLOLoanSchema LOLoanSchema
	**/
	public void setSchema(LOLoanSchema aLOLoanSchema)
	{
		this.ContNo = aLOLoanSchema.getContNo();
		this.PolNo = aLOLoanSchema.getPolNo();
		this.EdorNo = aLOLoanSchema.getEdorNo();
		this.SerialNo = aLOLoanSchema.getSerialNo();
		this.ActuGetNo = aLOLoanSchema.getActuGetNo();
		this.LoanType = aLOLoanSchema.getLoanType();
		this.OrderNo = aLOLoanSchema.getOrderNo();
		this.LoanDate = fDate.getDate( aLOLoanSchema.getLoanDate());
		this.PayOffDate = fDate.getDate( aLOLoanSchema.getPayOffDate());
		this.SumMoney = aLOLoanSchema.getSumMoney();
		this.InputFlag = aLOLoanSchema.getInputFlag();
		this.InterestType = aLOLoanSchema.getInterestType();
		this.InterestRate = aLOLoanSchema.getInterestRate();
		this.InterestMode = aLOLoanSchema.getInterestMode();
		this.RateCalType = aLOLoanSchema.getRateCalType();
		this.RateCalCode = aLOLoanSchema.getRateCalCode();
		this.SpecifyRate = aLOLoanSchema.getSpecifyRate();
		this.LeaveMoney = aLOLoanSchema.getLeaveMoney();
		this.PayOffFlag = aLOLoanSchema.getPayOffFlag();
		this.Operator = aLOLoanSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOLoanSchema.getMakeDate());
		this.MakeTime = aLOLoanSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOLoanSchema.getModifyDate());
		this.ModifyTime = aLOLoanSchema.getModifyTime();
		this.PreInterest = aLOLoanSchema.getPreInterest();
		this.CurLoanMoney = aLOLoanSchema.getCurLoanMoney();
		this.LastSumLoanMoney = aLOLoanSchema.getLastSumLoanMoney();
		this.LoanToDate = fDate.getDate( aLOLoanSchema.getLoanToDate());
		this.NewLoanDate = fDate.getDate( aLOLoanSchema.getNewLoanDate());
		this.ZqFlag = aLOLoanSchema.getZqFlag();
		this.ClmNo = aLOLoanSchema.getClmNo();
		this.Currency = aLOLoanSchema.getCurrency();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("ActuGetNo") == null )
				this.ActuGetNo = null;
			else
				this.ActuGetNo = rs.getString("ActuGetNo").trim();

			if( rs.getString("LoanType") == null )
				this.LoanType = null;
			else
				this.LoanType = rs.getString("LoanType").trim();

			if( rs.getString("OrderNo") == null )
				this.OrderNo = null;
			else
				this.OrderNo = rs.getString("OrderNo").trim();

			this.LoanDate = rs.getDate("LoanDate");
			this.PayOffDate = rs.getDate("PayOffDate");
			this.SumMoney = rs.getDouble("SumMoney");
			if( rs.getString("InputFlag") == null )
				this.InputFlag = null;
			else
				this.InputFlag = rs.getString("InputFlag").trim();

			if( rs.getString("InterestType") == null )
				this.InterestType = null;
			else
				this.InterestType = rs.getString("InterestType").trim();

			this.InterestRate = rs.getDouble("InterestRate");
			if( rs.getString("InterestMode") == null )
				this.InterestMode = null;
			else
				this.InterestMode = rs.getString("InterestMode").trim();

			if( rs.getString("RateCalType") == null )
				this.RateCalType = null;
			else
				this.RateCalType = rs.getString("RateCalType").trim();

			if( rs.getString("RateCalCode") == null )
				this.RateCalCode = null;
			else
				this.RateCalCode = rs.getString("RateCalCode").trim();

			if( rs.getString("SpecifyRate") == null )
				this.SpecifyRate = null;
			else
				this.SpecifyRate = rs.getString("SpecifyRate").trim();

			this.LeaveMoney = rs.getDouble("LeaveMoney");
			if( rs.getString("PayOffFlag") == null )
				this.PayOffFlag = null;
			else
				this.PayOffFlag = rs.getString("PayOffFlag").trim();

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

			this.PreInterest = rs.getDouble("PreInterest");
			this.CurLoanMoney = rs.getDouble("CurLoanMoney");
			this.LastSumLoanMoney = rs.getDouble("LastSumLoanMoney");
			this.LoanToDate = rs.getDate("LoanToDate");
			this.NewLoanDate = rs.getDate("NewLoanDate");
			if( rs.getString("ZqFlag") == null )
				this.ZqFlag = null;
			else
				this.ZqFlag = rs.getString("ZqFlag").trim();

			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOLoan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOLoanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOLoanSchema getSchema()
	{
		LOLoanSchema aLOLoanSchema = new LOLoanSchema();
		aLOLoanSchema.setSchema(this);
		return aLOLoanSchema;
	}

	public LOLoanDB getDB()
	{
		LOLoanDB aDBOper = new LOLoanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOLoan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActuGetNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrderNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LoanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayOffDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InterestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecifyRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LeaveMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayOffFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurLoanMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LastSumLoanMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LoanToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( NewLoanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZqFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOLoan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ActuGetNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			LoanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OrderNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			LoanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			PayOffDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			InputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InterestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InterestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			InterestMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RateCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RateCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SpecifyRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			LeaveMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			PayOffFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PreInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			CurLoanMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			LastSumLoanMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			LoanToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			NewLoanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ZqFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOLoanSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ActuGetNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActuGetNo));
		}
		if (FCode.equalsIgnoreCase("LoanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoanType));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equalsIgnoreCase("LoanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLoanDate()));
		}
		if (FCode.equalsIgnoreCase("PayOffDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayOffDate()));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("InputFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputFlag));
		}
		if (FCode.equalsIgnoreCase("InterestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestType));
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestRate));
		}
		if (FCode.equalsIgnoreCase("InterestMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestMode));
		}
		if (FCode.equalsIgnoreCase("RateCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateCalType));
		}
		if (FCode.equalsIgnoreCase("RateCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateCalCode));
		}
		if (FCode.equalsIgnoreCase("SpecifyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecifyRate));
		}
		if (FCode.equalsIgnoreCase("LeaveMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LeaveMoney));
		}
		if (FCode.equalsIgnoreCase("PayOffFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayOffFlag));
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
		if (FCode.equalsIgnoreCase("PreInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreInterest));
		}
		if (FCode.equalsIgnoreCase("CurLoanMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurLoanMoney));
		}
		if (FCode.equalsIgnoreCase("LastSumLoanMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastSumLoanMoney));
		}
		if (FCode.equalsIgnoreCase("LoanToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLoanToDate()));
		}
		if (FCode.equalsIgnoreCase("NewLoanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getNewLoanDate()));
		}
		if (FCode.equalsIgnoreCase("ZqFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZqFlag));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ActuGetNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(LoanType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OrderNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLoanDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayOffDate()));
				break;
			case 9:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InputFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InterestType);
				break;
			case 12:
				strFieldValue = String.valueOf(InterestRate);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InterestMode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RateCalType);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RateCalCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SpecifyRate);
				break;
			case 17:
				strFieldValue = String.valueOf(LeaveMoney);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PayOffFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = String.valueOf(PreInterest);
				break;
			case 25:
				strFieldValue = String.valueOf(CurLoanMoney);
				break;
			case 26:
				strFieldValue = String.valueOf(LastSumLoanMoney);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLoanToDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getNewLoanDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ZqFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 31:
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("ActuGetNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActuGetNo = FValue.trim();
			}
			else
				ActuGetNo = null;
		}
		if (FCode.equalsIgnoreCase("LoanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoanType = FValue.trim();
			}
			else
				LoanType = null;
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrderNo = FValue.trim();
			}
			else
				OrderNo = null;
		}
		if (FCode.equalsIgnoreCase("LoanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LoanDate = fDate.getDate( FValue );
			}
			else
				LoanDate = null;
		}
		if (FCode.equalsIgnoreCase("PayOffDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayOffDate = fDate.getDate( FValue );
			}
			else
				PayOffDate = null;
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("InputFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputFlag = FValue.trim();
			}
			else
				InputFlag = null;
		}
		if (FCode.equalsIgnoreCase("InterestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestType = FValue.trim();
			}
			else
				InterestType = null;
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
		if (FCode.equalsIgnoreCase("InterestMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestMode = FValue.trim();
			}
			else
				InterestMode = null;
		}
		if (FCode.equalsIgnoreCase("RateCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateCalType = FValue.trim();
			}
			else
				RateCalType = null;
		}
		if (FCode.equalsIgnoreCase("RateCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateCalCode = FValue.trim();
			}
			else
				RateCalCode = null;
		}
		if (FCode.equalsIgnoreCase("SpecifyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecifyRate = FValue.trim();
			}
			else
				SpecifyRate = null;
		}
		if (FCode.equalsIgnoreCase("LeaveMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LeaveMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayOffFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayOffFlag = FValue.trim();
			}
			else
				PayOffFlag = null;
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
		if (FCode.equalsIgnoreCase("PreInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurLoanMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurLoanMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastSumLoanMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LastSumLoanMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LoanToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LoanToDate = fDate.getDate( FValue );
			}
			else
				LoanToDate = null;
		}
		if (FCode.equalsIgnoreCase("NewLoanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				NewLoanDate = fDate.getDate( FValue );
			}
			else
				NewLoanDate = null;
		}
		if (FCode.equalsIgnoreCase("ZqFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZqFlag = FValue.trim();
			}
			else
				ZqFlag = null;
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
		LOLoanSchema other = (LOLoanSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& EdorNo.equals(other.getEdorNo())
			&& SerialNo.equals(other.getSerialNo())
			&& ActuGetNo.equals(other.getActuGetNo())
			&& LoanType.equals(other.getLoanType())
			&& OrderNo.equals(other.getOrderNo())
			&& fDate.getString(LoanDate).equals(other.getLoanDate())
			&& fDate.getString(PayOffDate).equals(other.getPayOffDate())
			&& SumMoney == other.getSumMoney()
			&& InputFlag.equals(other.getInputFlag())
			&& InterestType.equals(other.getInterestType())
			&& InterestRate == other.getInterestRate()
			&& InterestMode.equals(other.getInterestMode())
			&& RateCalType.equals(other.getRateCalType())
			&& RateCalCode.equals(other.getRateCalCode())
			&& SpecifyRate.equals(other.getSpecifyRate())
			&& LeaveMoney == other.getLeaveMoney()
			&& PayOffFlag.equals(other.getPayOffFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PreInterest == other.getPreInterest()
			&& CurLoanMoney == other.getCurLoanMoney()
			&& LastSumLoanMoney == other.getLastSumLoanMoney()
			&& fDate.getString(LoanToDate).equals(other.getLoanToDate())
			&& fDate.getString(NewLoanDate).equals(other.getNewLoanDate())
			&& ZqFlag.equals(other.getZqFlag())
			&& ClmNo.equals(other.getClmNo())
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("ActuGetNo") ) {
			return 4;
		}
		if( strFieldName.equals("LoanType") ) {
			return 5;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 6;
		}
		if( strFieldName.equals("LoanDate") ) {
			return 7;
		}
		if( strFieldName.equals("PayOffDate") ) {
			return 8;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 9;
		}
		if( strFieldName.equals("InputFlag") ) {
			return 10;
		}
		if( strFieldName.equals("InterestType") ) {
			return 11;
		}
		if( strFieldName.equals("InterestRate") ) {
			return 12;
		}
		if( strFieldName.equals("InterestMode") ) {
			return 13;
		}
		if( strFieldName.equals("RateCalType") ) {
			return 14;
		}
		if( strFieldName.equals("RateCalCode") ) {
			return 15;
		}
		if( strFieldName.equals("SpecifyRate") ) {
			return 16;
		}
		if( strFieldName.equals("LeaveMoney") ) {
			return 17;
		}
		if( strFieldName.equals("PayOffFlag") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("PreInterest") ) {
			return 24;
		}
		if( strFieldName.equals("CurLoanMoney") ) {
			return 25;
		}
		if( strFieldName.equals("LastSumLoanMoney") ) {
			return 26;
		}
		if( strFieldName.equals("LoanToDate") ) {
			return 27;
		}
		if( strFieldName.equals("NewLoanDate") ) {
			return 28;
		}
		if( strFieldName.equals("ZqFlag") ) {
			return 29;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 30;
		}
		if( strFieldName.equals("Currency") ) {
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "EdorNo";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "ActuGetNo";
				break;
			case 5:
				strFieldName = "LoanType";
				break;
			case 6:
				strFieldName = "OrderNo";
				break;
			case 7:
				strFieldName = "LoanDate";
				break;
			case 8:
				strFieldName = "PayOffDate";
				break;
			case 9:
				strFieldName = "SumMoney";
				break;
			case 10:
				strFieldName = "InputFlag";
				break;
			case 11:
				strFieldName = "InterestType";
				break;
			case 12:
				strFieldName = "InterestRate";
				break;
			case 13:
				strFieldName = "InterestMode";
				break;
			case 14:
				strFieldName = "RateCalType";
				break;
			case 15:
				strFieldName = "RateCalCode";
				break;
			case 16:
				strFieldName = "SpecifyRate";
				break;
			case 17:
				strFieldName = "LeaveMoney";
				break;
			case 18:
				strFieldName = "PayOffFlag";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "PreInterest";
				break;
			case 25:
				strFieldName = "CurLoanMoney";
				break;
			case 26:
				strFieldName = "LastSumLoanMoney";
				break;
			case 27:
				strFieldName = "LoanToDate";
				break;
			case 28:
				strFieldName = "NewLoanDate";
				break;
			case 29:
				strFieldName = "ZqFlag";
				break;
			case 30:
				strFieldName = "ClmNo";
				break;
			case 31:
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActuGetNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayOffDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InputFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InterestMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecifyRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LeaveMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayOffFlag") ) {
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
		if( strFieldName.equals("PreInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurLoanMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastSumLoanMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LoanToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("NewLoanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ZqFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmNo") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
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
