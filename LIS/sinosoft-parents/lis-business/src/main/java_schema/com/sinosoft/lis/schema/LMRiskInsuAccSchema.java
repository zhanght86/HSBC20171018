

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
import com.sinosoft.lis.db.LMRiskInsuAccDB;

/*
 * <p>ClassName: LMRiskInsuAccSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LMRiskInsuAccSchema implements Schema, Cloneable
{
	// @Field
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 账号类型 */
	private String AccType;
	/** 账户分类 */
	private String AccKind;
	/** 保险帐户名称 */
	private String InsuAccName;
	/** 账户产生位置 */
	private String AccCreatePos;
	/** 账号产生规则 */
	private String AccCreateType;
	/** 账户固定利率 */
	private double AccRate;
	/** 账户对应利率表 */
	private String AccRateTable;
	/** 账户结清计算公式 */
	private String AccCancelCode;
	/** 账户结算方式 */
	private String AccComputeFlag;
	/** 投资类型 */
	private String InvestType;
	/** 基金公司代码 */
	private String FundCompanyCode;
	/** 账户所有者 */
	private String Owner;
	/** 是否参与分红 */
	private String BonusFlag;
	/** 分红记入账户的方式 */
	private String AccBonusMode;
	/** 分红记入账户代码 */
	private String BonusToInsuAccNo;
	/** 分红时是否进行账户结算 */
	private String InsuAccCalBalaFlag;
	/** 分红方式 */
	private String BonusMode;
	/** 投资收益类型 */
	private String InvestFlag;
	/** 计价周期 */
	private String CalValueFreq;
	/** 计价价格获取规则 */
	private String CalValueRule;
	/** 小数位数 */
	private String UnitDecimal;
	/** 四舍五入标记 */
	private String RoundMethod;
	/** 是否参与分红2 */
	private String AccBonusFlag;
	/** 币别 */
	private String Currency;
	/** T加n计价 */
	private int TNFlag;
	/** 基金风险 */
	private String FundRiskLevel;
	/** 基金公司名称 */
	private String FundCompanyName;
	/** 基金英文名称 */
	private String FundEnglishName;
	/** 基金转换类型 */
	private String SwitchType;
	/** 基金状态 */
	private String FundState;
	/** 基金开通日期 */
	private Date FundOpenDate;
	/** 基金开始暂停日期 */
	private Date FundSuspendDate;
	/** 基金关闭日期 */
	private Date FundCloseDate;
	/** 基金满期日期 */
	private Date FundMaturityDate;
	/** T加n发盘 */
	private int TNFlag1;
	/** 基金起始发盘金额 */
	private double FundMoney;
	/** 基金类型 */
	private String FundClass;
	/** 目标币种 */
	private String DueCurreny;

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskInsuAccSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "InsuAccNo";

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
		LMRiskInsuAccSchema cloned = (LMRiskInsuAccSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 账户号码的定义规则：<p>
	* 6位长度，其他不限制。<p>
	* 从000001开始往后排。
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
	* 001-缴费账户<p>
	* 002－红利账户
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	/**
	* 1 －－ 现金账户<p>
	* 2 －－ 股份账户
	*/
	public String getAccKind()
	{
		return AccKind;
	}
	public void setAccKind(String aAccKind)
	{
		AccKind = aAccKind;
	}
	public String getInsuAccName()
	{
		return InsuAccName;
	}
	public void setInsuAccName(String aInsuAccName)
	{
		InsuAccName = aInsuAccName;
	}
	/**
	* 1 －－投保单录入时产生<p>
	* 2 －－缴费时产生<p>
	* 3 －－领取时产生<p>
	* 4 －－第一次账户缴费时产生
	*/
	public String getAccCreatePos()
	{
		return AccCreatePos;
	}
	public void setAccCreatePos(String aAccCreatePos)
	{
		AccCreatePos = aAccCreatePos;
	}
	/**
	* 1 －－ 同个人保单号<p>
	* 2 －－ 同缴费收据号<p>
	* 3 －－ 自动产生（调用CreateMax产生）<p>
	* 4 －－ 固定账号<p>
	* 5 －－ 同集体保险账户
	*/
	public String getAccCreateType()
	{
		return AccCreateType;
	}
	public void setAccCreateType(String aAccCreateType)
	{
		AccCreateType = aAccCreateType;
	}
	public double getAccRate()
	{
		return AccRate;
	}
	public void setAccRate(double aAccRate)
	{
		AccRate = aAccRate;
	}
	public void setAccRate(String aAccRate)
	{
		if (aAccRate != null && !aAccRate.equals(""))
		{
			Double tDouble = new Double(aAccRate);
			double d = tDouble.doubleValue();
			AccRate = d;
		}
	}

	/**
	* 账户利率表：Interest＋账户号码
	*/
	public String getAccRateTable()
	{
		return AccRateTable;
	}
	public void setAccRateTable(String aAccRateTable)
	{
		AccRateTable = aAccRateTable;
	}
	public String getAccCancelCode()
	{
		return AccCancelCode;
	}
	public void setAccCancelCode(String aAccCancelCode)
	{
		AccCancelCode = aAccCancelCode;
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
		AccComputeFlag = aAccComputeFlag;
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
		InvestType = aInvestType;
	}
	public String getFundCompanyCode()
	{
		return FundCompanyCode;
	}
	public void setFundCompanyCode(String aFundCompanyCode)
	{
		FundCompanyCode = aFundCompanyCode;
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
		Owner = aOwner;
	}
	public String getBonusFlag()
	{
		return BonusFlag;
	}
	public void setBonusFlag(String aBonusFlag)
	{
		BonusFlag = aBonusFlag;
	}
	public String getAccBonusMode()
	{
		return AccBonusMode;
	}
	public void setAccBonusMode(String aAccBonusMode)
	{
		AccBonusMode = aAccBonusMode;
	}
	public String getBonusToInsuAccNo()
	{
		return BonusToInsuAccNo;
	}
	public void setBonusToInsuAccNo(String aBonusToInsuAccNo)
	{
		BonusToInsuAccNo = aBonusToInsuAccNo;
	}
	public String getInsuAccCalBalaFlag()
	{
		return InsuAccCalBalaFlag;
	}
	public void setInsuAccCalBalaFlag(String aInsuAccCalBalaFlag)
	{
		InsuAccCalBalaFlag = aInsuAccCalBalaFlag;
	}
	public String getBonusMode()
	{
		return BonusMode;
	}
	public void setBonusMode(String aBonusMode)
	{
		BonusMode = aBonusMode;
	}
	public String getInvestFlag()
	{
		return InvestFlag;
	}
	public void setInvestFlag(String aInvestFlag)
	{
		InvestFlag = aInvestFlag;
	}
	public String getCalValueFreq()
	{
		return CalValueFreq;
	}
	public void setCalValueFreq(String aCalValueFreq)
	{
		CalValueFreq = aCalValueFreq;
	}
	public String getCalValueRule()
	{
		return CalValueRule;
	}
	public void setCalValueRule(String aCalValueRule)
	{
		CalValueRule = aCalValueRule;
	}
	public String getUnitDecimal()
	{
		return UnitDecimal;
	}
	public void setUnitDecimal(String aUnitDecimal)
	{
		UnitDecimal = aUnitDecimal;
	}
	public String getRoundMethod()
	{
		return RoundMethod;
	}
	public void setRoundMethod(String aRoundMethod)
	{
		RoundMethod = aRoundMethod;
	}
	/**
	* 0－不参与分红<p>
	* 1－参与分红
	*/
	public String getAccBonusFlag()
	{
		return AccBonusFlag;
	}
	public void setAccBonusFlag(String aAccBonusFlag)
	{
		AccBonusFlag = aAccBonusFlag;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public int getTNFlag()
	{
		return TNFlag;
	}
	public void setTNFlag(int aTNFlag)
	{
		TNFlag = aTNFlag;
	}
	public void setTNFlag(String aTNFlag)
	{
		if (aTNFlag != null && !aTNFlag.equals(""))
		{
			Integer tInteger = new Integer(aTNFlag);
			int i = tInteger.intValue();
			TNFlag = i;
		}
	}

	/**
	* 该基金的风险级别
	*/
	public String getFundRiskLevel()
	{
		return FundRiskLevel;
	}
	public void setFundRiskLevel(String aFundRiskLevel)
	{
		FundRiskLevel = aFundRiskLevel;
	}
	public String getFundCompanyName()
	{
		return FundCompanyName;
	}
	public void setFundCompanyName(String aFundCompanyName)
	{
		FundCompanyName = aFundCompanyName;
	}
	public String getFundEnglishName()
	{
		return FundEnglishName;
	}
	public void setFundEnglishName(String aFundEnglishName)
	{
		FundEnglishName = aFundEnglishName;
	}
	/**
	* 某些基金公司基金转换在同一天进行处理；另外一些基金公司则是先转出再转入
	*/
	public String getSwitchType()
	{
		return SwitchType;
	}
	public void setSwitchType(String aSwitchType)
	{
		SwitchType = aSwitchType;
	}
	/**
	* 表示基金状态：<p>
	* 0-开通<p>
	* 1-暂停<p>
	* 2-关闭<p>
	* 3-满期
	*/
	public String getFundState()
	{
		return FundState;
	}
	public void setFundState(String aFundState)
	{
		FundState = aFundState;
	}
	public String getFundOpenDate()
	{
		if( FundOpenDate != null )
			return fDate.getString(FundOpenDate);
		else
			return null;
	}
	public void setFundOpenDate(Date aFundOpenDate)
	{
		FundOpenDate = aFundOpenDate;
	}
	public void setFundOpenDate(String aFundOpenDate)
	{
		if (aFundOpenDate != null && !aFundOpenDate.equals("") )
		{
			FundOpenDate = fDate.getDate( aFundOpenDate );
		}
		else
			FundOpenDate = null;
	}

	public String getFundSuspendDate()
	{
		if( FundSuspendDate != null )
			return fDate.getString(FundSuspendDate);
		else
			return null;
	}
	public void setFundSuspendDate(Date aFundSuspendDate)
	{
		FundSuspendDate = aFundSuspendDate;
	}
	public void setFundSuspendDate(String aFundSuspendDate)
	{
		if (aFundSuspendDate != null && !aFundSuspendDate.equals("") )
		{
			FundSuspendDate = fDate.getDate( aFundSuspendDate );
		}
		else
			FundSuspendDate = null;
	}

	public String getFundCloseDate()
	{
		if( FundCloseDate != null )
			return fDate.getString(FundCloseDate);
		else
			return null;
	}
	public void setFundCloseDate(Date aFundCloseDate)
	{
		FundCloseDate = aFundCloseDate;
	}
	public void setFundCloseDate(String aFundCloseDate)
	{
		if (aFundCloseDate != null && !aFundCloseDate.equals("") )
		{
			FundCloseDate = fDate.getDate( aFundCloseDate );
		}
		else
			FundCloseDate = null;
	}

	public String getFundMaturityDate()
	{
		if( FundMaturityDate != null )
			return fDate.getString(FundMaturityDate);
		else
			return null;
	}
	public void setFundMaturityDate(Date aFundMaturityDate)
	{
		FundMaturityDate = aFundMaturityDate;
	}
	public void setFundMaturityDate(String aFundMaturityDate)
	{
		if (aFundMaturityDate != null && !aFundMaturityDate.equals("") )
		{
			FundMaturityDate = fDate.getDate( aFundMaturityDate );
		}
		else
			FundMaturityDate = null;
	}

	public int getTNFlag1()
	{
		return TNFlag1;
	}
	public void setTNFlag1(int aTNFlag1)
	{
		TNFlag1 = aTNFlag1;
	}
	public void setTNFlag1(String aTNFlag1)
	{
		if (aTNFlag1 != null && !aTNFlag1.equals(""))
		{
			Integer tInteger = new Integer(aTNFlag1);
			int i = tInteger.intValue();
			TNFlag1 = i;
		}
	}

	public double getFundMoney()
	{
		return FundMoney;
	}
	public void setFundMoney(double aFundMoney)
	{
		FundMoney = aFundMoney;
	}
	public void setFundMoney(String aFundMoney)
	{
		if (aFundMoney != null && !aFundMoney.equals(""))
		{
			Double tDouble = new Double(aFundMoney);
			double d = tDouble.doubleValue();
			FundMoney = d;
		}
	}

	public String getFundClass()
	{
		return FundClass;
	}
	public void setFundClass(String aFundClass)
	{
		FundClass = aFundClass;
	}
	public String getDueCurreny()
	{
		return DueCurreny;
	}
	public void setDueCurreny(String aDueCurreny)
	{
		DueCurreny = aDueCurreny;
	}

	/**
	* 使用另外一个 LMRiskInsuAccSchema 对象给 Schema 赋值
	* @param: aLMRiskInsuAccSchema LMRiskInsuAccSchema
	**/
	public void setSchema(LMRiskInsuAccSchema aLMRiskInsuAccSchema)
	{
		this.InsuAccNo = aLMRiskInsuAccSchema.getInsuAccNo();
		this.AccType = aLMRiskInsuAccSchema.getAccType();
		this.AccKind = aLMRiskInsuAccSchema.getAccKind();
		this.InsuAccName = aLMRiskInsuAccSchema.getInsuAccName();
		this.AccCreatePos = aLMRiskInsuAccSchema.getAccCreatePos();
		this.AccCreateType = aLMRiskInsuAccSchema.getAccCreateType();
		this.AccRate = aLMRiskInsuAccSchema.getAccRate();
		this.AccRateTable = aLMRiskInsuAccSchema.getAccRateTable();
		this.AccCancelCode = aLMRiskInsuAccSchema.getAccCancelCode();
		this.AccComputeFlag = aLMRiskInsuAccSchema.getAccComputeFlag();
		this.InvestType = aLMRiskInsuAccSchema.getInvestType();
		this.FundCompanyCode = aLMRiskInsuAccSchema.getFundCompanyCode();
		this.Owner = aLMRiskInsuAccSchema.getOwner();
		this.BonusFlag = aLMRiskInsuAccSchema.getBonusFlag();
		this.AccBonusMode = aLMRiskInsuAccSchema.getAccBonusMode();
		this.BonusToInsuAccNo = aLMRiskInsuAccSchema.getBonusToInsuAccNo();
		this.InsuAccCalBalaFlag = aLMRiskInsuAccSchema.getInsuAccCalBalaFlag();
		this.BonusMode = aLMRiskInsuAccSchema.getBonusMode();
		this.InvestFlag = aLMRiskInsuAccSchema.getInvestFlag();
		this.CalValueFreq = aLMRiskInsuAccSchema.getCalValueFreq();
		this.CalValueRule = aLMRiskInsuAccSchema.getCalValueRule();
		this.UnitDecimal = aLMRiskInsuAccSchema.getUnitDecimal();
		this.RoundMethod = aLMRiskInsuAccSchema.getRoundMethod();
		this.AccBonusFlag = aLMRiskInsuAccSchema.getAccBonusFlag();
		this.Currency = aLMRiskInsuAccSchema.getCurrency();
		this.TNFlag = aLMRiskInsuAccSchema.getTNFlag();
		this.FundRiskLevel = aLMRiskInsuAccSchema.getFundRiskLevel();
		this.FundCompanyName = aLMRiskInsuAccSchema.getFundCompanyName();
		this.FundEnglishName = aLMRiskInsuAccSchema.getFundEnglishName();
		this.SwitchType = aLMRiskInsuAccSchema.getSwitchType();
		this.FundState = aLMRiskInsuAccSchema.getFundState();
		this.FundOpenDate = fDate.getDate( aLMRiskInsuAccSchema.getFundOpenDate());
		this.FundSuspendDate = fDate.getDate( aLMRiskInsuAccSchema.getFundSuspendDate());
		this.FundCloseDate = fDate.getDate( aLMRiskInsuAccSchema.getFundCloseDate());
		this.FundMaturityDate = fDate.getDate( aLMRiskInsuAccSchema.getFundMaturityDate());
		this.TNFlag1 = aLMRiskInsuAccSchema.getTNFlag1();
		this.FundMoney = aLMRiskInsuAccSchema.getFundMoney();
		this.FundClass = aLMRiskInsuAccSchema.getFundClass();
		this.DueCurreny = aLMRiskInsuAccSchema.getDueCurreny();
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
			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccKind") == null )
				this.AccKind = null;
			else
				this.AccKind = rs.getString("AccKind").trim();

			if( rs.getString("InsuAccName") == null )
				this.InsuAccName = null;
			else
				this.InsuAccName = rs.getString("InsuAccName").trim();

			if( rs.getString("AccCreatePos") == null )
				this.AccCreatePos = null;
			else
				this.AccCreatePos = rs.getString("AccCreatePos").trim();

			if( rs.getString("AccCreateType") == null )
				this.AccCreateType = null;
			else
				this.AccCreateType = rs.getString("AccCreateType").trim();

			this.AccRate = rs.getDouble("AccRate");
			if( rs.getString("AccRateTable") == null )
				this.AccRateTable = null;
			else
				this.AccRateTable = rs.getString("AccRateTable").trim();

			if( rs.getString("AccCancelCode") == null )
				this.AccCancelCode = null;
			else
				this.AccCancelCode = rs.getString("AccCancelCode").trim();

			if( rs.getString("AccComputeFlag") == null )
				this.AccComputeFlag = null;
			else
				this.AccComputeFlag = rs.getString("AccComputeFlag").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("FundCompanyCode") == null )
				this.FundCompanyCode = null;
			else
				this.FundCompanyCode = rs.getString("FundCompanyCode").trim();

			if( rs.getString("Owner") == null )
				this.Owner = null;
			else
				this.Owner = rs.getString("Owner").trim();

			if( rs.getString("BonusFlag") == null )
				this.BonusFlag = null;
			else
				this.BonusFlag = rs.getString("BonusFlag").trim();

			if( rs.getString("AccBonusMode") == null )
				this.AccBonusMode = null;
			else
				this.AccBonusMode = rs.getString("AccBonusMode").trim();

			if( rs.getString("BonusToInsuAccNo") == null )
				this.BonusToInsuAccNo = null;
			else
				this.BonusToInsuAccNo = rs.getString("BonusToInsuAccNo").trim();

			if( rs.getString("InsuAccCalBalaFlag") == null )
				this.InsuAccCalBalaFlag = null;
			else
				this.InsuAccCalBalaFlag = rs.getString("InsuAccCalBalaFlag").trim();

			if( rs.getString("BonusMode") == null )
				this.BonusMode = null;
			else
				this.BonusMode = rs.getString("BonusMode").trim();

			if( rs.getString("InvestFlag") == null )
				this.InvestFlag = null;
			else
				this.InvestFlag = rs.getString("InvestFlag").trim();

			if( rs.getString("CalValueFreq") == null )
				this.CalValueFreq = null;
			else
				this.CalValueFreq = rs.getString("CalValueFreq").trim();

			if( rs.getString("CalValueRule") == null )
				this.CalValueRule = null;
			else
				this.CalValueRule = rs.getString("CalValueRule").trim();

			if( rs.getString("UnitDecimal") == null )
				this.UnitDecimal = null;
			else
				this.UnitDecimal = rs.getString("UnitDecimal").trim();

			if( rs.getString("RoundMethod") == null )
				this.RoundMethod = null;
			else
				this.RoundMethod = rs.getString("RoundMethod").trim();

			if( rs.getString("AccBonusFlag") == null )
				this.AccBonusFlag = null;
			else
				this.AccBonusFlag = rs.getString("AccBonusFlag").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.TNFlag = rs.getInt("TNFlag");
			if( rs.getString("FundRiskLevel") == null )
				this.FundRiskLevel = null;
			else
				this.FundRiskLevel = rs.getString("FundRiskLevel").trim();

			if( rs.getString("FundCompanyName") == null )
				this.FundCompanyName = null;
			else
				this.FundCompanyName = rs.getString("FundCompanyName").trim();

			if( rs.getString("FundEnglishName") == null )
				this.FundEnglishName = null;
			else
				this.FundEnglishName = rs.getString("FundEnglishName").trim();

			if( rs.getString("SwitchType") == null )
				this.SwitchType = null;
			else
				this.SwitchType = rs.getString("SwitchType").trim();

			if( rs.getString("FundState") == null )
				this.FundState = null;
			else
				this.FundState = rs.getString("FundState").trim();

			this.FundOpenDate = rs.getDate("FundOpenDate");
			this.FundSuspendDate = rs.getDate("FundSuspendDate");
			this.FundCloseDate = rs.getDate("FundCloseDate");
			this.FundMaturityDate = rs.getDate("FundMaturityDate");
			this.TNFlag1 = rs.getInt("TNFlag1");
			this.FundMoney = rs.getDouble("FundMoney");
			if( rs.getString("FundClass") == null )
				this.FundClass = null;
			else
				this.FundClass = rs.getString("FundClass").trim();

			if( rs.getString("DueCurreny") == null )
				this.DueCurreny = null;
			else
				this.DueCurreny = rs.getString("DueCurreny").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRiskInsuAcc表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskInsuAccSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskInsuAccSchema getSchema()
	{
		LMRiskInsuAccSchema aLMRiskInsuAccSchema = new LMRiskInsuAccSchema();
		aLMRiskInsuAccSchema.setSchema(this);
		return aLMRiskInsuAccSchema;
	}

	public LMRiskInsuAccDB getDB()
	{
		LMRiskInsuAccDB aDBOper = new LMRiskInsuAccDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskInsuAcc描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCreatePos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCreateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccRateTable)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCancelCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccComputeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundCompanyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Owner)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccBonusMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusToInsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccCalBalaFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalValueFreq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalValueRule)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnitDecimal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RoundMethod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccBonusFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TNFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundRiskLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundCompanyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundEnglishName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SwitchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FundOpenDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FundSuspendDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FundCloseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FundMaturityDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TNFlag1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FundMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DueCurreny));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskInsuAcc>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccCreatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccCreateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			AccRateTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccCancelCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccComputeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			FundCompanyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Owner = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BonusFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AccBonusMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BonusToInsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InsuAccCalBalaFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BonusMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InvestFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			CalValueFreq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			CalValueRule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			UnitDecimal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			RoundMethod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AccBonusFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			TNFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			FundRiskLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			FundCompanyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			FundEnglishName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			SwitchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			FundState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			FundOpenDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			FundSuspendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			FundCloseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34,SysConst.PACKAGESPILTER));
			FundMaturityDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			TNFlag1= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).intValue();
			FundMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			FundClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			DueCurreny = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskInsuAccSchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccKind));
		}
		if (FCode.equalsIgnoreCase("InsuAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccName));
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCreatePos));
		}
		if (FCode.equalsIgnoreCase("AccCreateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCreateType));
		}
		if (FCode.equalsIgnoreCase("AccRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccRate));
		}
		if (FCode.equalsIgnoreCase("AccRateTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccRateTable));
		}
		if (FCode.equalsIgnoreCase("AccCancelCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCancelCode));
		}
		if (FCode.equalsIgnoreCase("AccComputeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccComputeFlag));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("FundCompanyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCompanyCode));
		}
		if (FCode.equalsIgnoreCase("Owner"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Owner));
		}
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusFlag));
		}
		if (FCode.equalsIgnoreCase("AccBonusMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccBonusMode));
		}
		if (FCode.equalsIgnoreCase("BonusToInsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusToInsuAccNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccCalBalaFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccCalBalaFlag));
		}
		if (FCode.equalsIgnoreCase("BonusMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusMode));
		}
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestFlag));
		}
		if (FCode.equalsIgnoreCase("CalValueFreq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalValueFreq));
		}
		if (FCode.equalsIgnoreCase("CalValueRule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalValueRule));
		}
		if (FCode.equalsIgnoreCase("UnitDecimal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitDecimal));
		}
		if (FCode.equalsIgnoreCase("RoundMethod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RoundMethod));
		}
		if (FCode.equalsIgnoreCase("AccBonusFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccBonusFlag));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("TNFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TNFlag));
		}
		if (FCode.equalsIgnoreCase("FundRiskLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundRiskLevel));
		}
		if (FCode.equalsIgnoreCase("FundCompanyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundCompanyName));
		}
		if (FCode.equalsIgnoreCase("FundEnglishName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundEnglishName));
		}
		if (FCode.equalsIgnoreCase("SwitchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SwitchType));
		}
		if (FCode.equalsIgnoreCase("FundState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundState));
		}
		if (FCode.equalsIgnoreCase("FundOpenDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFundOpenDate()));
		}
		if (FCode.equalsIgnoreCase("FundSuspendDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFundSuspendDate()));
		}
		if (FCode.equalsIgnoreCase("FundCloseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFundCloseDate()));
		}
		if (FCode.equalsIgnoreCase("FundMaturityDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFundMaturityDate()));
		}
		if (FCode.equalsIgnoreCase("TNFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TNFlag1));
		}
		if (FCode.equalsIgnoreCase("FundMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundMoney));
		}
		if (FCode.equalsIgnoreCase("FundClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundClass));
		}
		if (FCode.equalsIgnoreCase("DueCurreny"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DueCurreny));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccKind);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuAccName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccCreatePos);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccCreateType);
				break;
			case 6:
				strFieldValue = String.valueOf(AccRate);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccRateTable);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccCancelCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccComputeFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(FundCompanyCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Owner);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BonusFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AccBonusMode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BonusToInsuAccNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(InsuAccCalBalaFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BonusMode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(InvestFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(CalValueFreq);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(CalValueRule);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(UnitDecimal);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(RoundMethod);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AccBonusFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 25:
				strFieldValue = String.valueOf(TNFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(FundRiskLevel);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(FundCompanyName);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(FundEnglishName);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SwitchType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(FundState);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFundOpenDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFundSuspendDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFundCloseDate()));
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFundMaturityDate()));
				break;
			case 35:
				strFieldValue = String.valueOf(TNFlag1);
				break;
			case 36:
				strFieldValue = String.valueOf(FundMoney);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(FundClass);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(DueCurreny);
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

		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equalsIgnoreCase("AccKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccKind = FValue.trim();
			}
			else
				AccKind = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccName = FValue.trim();
			}
			else
				InsuAccName = null;
		}
		if (FCode.equalsIgnoreCase("AccCreatePos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCreatePos = FValue.trim();
			}
			else
				AccCreatePos = null;
		}
		if (FCode.equalsIgnoreCase("AccCreateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCreateType = FValue.trim();
			}
			else
				AccCreateType = null;
		}
		if (FCode.equalsIgnoreCase("AccRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccRateTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccRateTable = FValue.trim();
			}
			else
				AccRateTable = null;
		}
		if (FCode.equalsIgnoreCase("AccCancelCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCancelCode = FValue.trim();
			}
			else
				AccCancelCode = null;
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
		if (FCode.equalsIgnoreCase("Owner"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Owner = FValue.trim();
			}
			else
				Owner = null;
		}
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusFlag = FValue.trim();
			}
			else
				BonusFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccBonusMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccBonusMode = FValue.trim();
			}
			else
				AccBonusMode = null;
		}
		if (FCode.equalsIgnoreCase("BonusToInsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusToInsuAccNo = FValue.trim();
			}
			else
				BonusToInsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccCalBalaFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccCalBalaFlag = FValue.trim();
			}
			else
				InsuAccCalBalaFlag = null;
		}
		if (FCode.equalsIgnoreCase("BonusMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusMode = FValue.trim();
			}
			else
				BonusMode = null;
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
		if (FCode.equalsIgnoreCase("CalValueFreq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalValueFreq = FValue.trim();
			}
			else
				CalValueFreq = null;
		}
		if (FCode.equalsIgnoreCase("CalValueRule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalValueRule = FValue.trim();
			}
			else
				CalValueRule = null;
		}
		if (FCode.equalsIgnoreCase("UnitDecimal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitDecimal = FValue.trim();
			}
			else
				UnitDecimal = null;
		}
		if (FCode.equalsIgnoreCase("RoundMethod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RoundMethod = FValue.trim();
			}
			else
				RoundMethod = null;
		}
		if (FCode.equalsIgnoreCase("AccBonusFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccBonusFlag = FValue.trim();
			}
			else
				AccBonusFlag = null;
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
		if (FCode.equalsIgnoreCase("TNFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TNFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("FundRiskLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundRiskLevel = FValue.trim();
			}
			else
				FundRiskLevel = null;
		}
		if (FCode.equalsIgnoreCase("FundCompanyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundCompanyName = FValue.trim();
			}
			else
				FundCompanyName = null;
		}
		if (FCode.equalsIgnoreCase("FundEnglishName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundEnglishName = FValue.trim();
			}
			else
				FundEnglishName = null;
		}
		if (FCode.equalsIgnoreCase("SwitchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SwitchType = FValue.trim();
			}
			else
				SwitchType = null;
		}
		if (FCode.equalsIgnoreCase("FundState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundState = FValue.trim();
			}
			else
				FundState = null;
		}
		if (FCode.equalsIgnoreCase("FundOpenDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FundOpenDate = fDate.getDate( FValue );
			}
			else
				FundOpenDate = null;
		}
		if (FCode.equalsIgnoreCase("FundSuspendDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FundSuspendDate = fDate.getDate( FValue );
			}
			else
				FundSuspendDate = null;
		}
		if (FCode.equalsIgnoreCase("FundCloseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FundCloseDate = fDate.getDate( FValue );
			}
			else
				FundCloseDate = null;
		}
		if (FCode.equalsIgnoreCase("FundMaturityDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FundMaturityDate = fDate.getDate( FValue );
			}
			else
				FundMaturityDate = null;
		}
		if (FCode.equalsIgnoreCase("TNFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TNFlag1 = i;
			}
		}
		if (FCode.equalsIgnoreCase("FundMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FundMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("FundClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundClass = FValue.trim();
			}
			else
				FundClass = null;
		}
		if (FCode.equalsIgnoreCase("DueCurreny"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DueCurreny = FValue.trim();
			}
			else
				DueCurreny = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskInsuAccSchema other = (LMRiskInsuAccSchema)otherObject;
		return
			InsuAccNo.equals(other.getInsuAccNo())
			&& AccType.equals(other.getAccType())
			&& AccKind.equals(other.getAccKind())
			&& InsuAccName.equals(other.getInsuAccName())
			&& AccCreatePos.equals(other.getAccCreatePos())
			&& AccCreateType.equals(other.getAccCreateType())
			&& AccRate == other.getAccRate()
			&& AccRateTable.equals(other.getAccRateTable())
			&& AccCancelCode.equals(other.getAccCancelCode())
			&& AccComputeFlag.equals(other.getAccComputeFlag())
			&& InvestType.equals(other.getInvestType())
			&& FundCompanyCode.equals(other.getFundCompanyCode())
			&& Owner.equals(other.getOwner())
			&& BonusFlag.equals(other.getBonusFlag())
			&& AccBonusMode.equals(other.getAccBonusMode())
			&& BonusToInsuAccNo.equals(other.getBonusToInsuAccNo())
			&& InsuAccCalBalaFlag.equals(other.getInsuAccCalBalaFlag())
			&& BonusMode.equals(other.getBonusMode())
			&& InvestFlag.equals(other.getInvestFlag())
			&& CalValueFreq.equals(other.getCalValueFreq())
			&& CalValueRule.equals(other.getCalValueRule())
			&& UnitDecimal.equals(other.getUnitDecimal())
			&& RoundMethod.equals(other.getRoundMethod())
			&& AccBonusFlag.equals(other.getAccBonusFlag())
			&& Currency.equals(other.getCurrency())
			&& TNFlag == other.getTNFlag()
			&& FundRiskLevel.equals(other.getFundRiskLevel())
			&& FundCompanyName.equals(other.getFundCompanyName())
			&& FundEnglishName.equals(other.getFundEnglishName())
			&& SwitchType.equals(other.getSwitchType())
			&& FundState.equals(other.getFundState())
			&& fDate.getString(FundOpenDate).equals(other.getFundOpenDate())
			&& fDate.getString(FundSuspendDate).equals(other.getFundSuspendDate())
			&& fDate.getString(FundCloseDate).equals(other.getFundCloseDate())
			&& fDate.getString(FundMaturityDate).equals(other.getFundMaturityDate())
			&& TNFlag1 == other.getTNFlag1()
			&& FundMoney == other.getFundMoney()
			&& FundClass.equals(other.getFundClass())
			&& DueCurreny.equals(other.getDueCurreny());
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 0;
		}
		if( strFieldName.equals("AccType") ) {
			return 1;
		}
		if( strFieldName.equals("AccKind") ) {
			return 2;
		}
		if( strFieldName.equals("InsuAccName") ) {
			return 3;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return 4;
		}
		if( strFieldName.equals("AccCreateType") ) {
			return 5;
		}
		if( strFieldName.equals("AccRate") ) {
			return 6;
		}
		if( strFieldName.equals("AccRateTable") ) {
			return 7;
		}
		if( strFieldName.equals("AccCancelCode") ) {
			return 8;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return 9;
		}
		if( strFieldName.equals("InvestType") ) {
			return 10;
		}
		if( strFieldName.equals("FundCompanyCode") ) {
			return 11;
		}
		if( strFieldName.equals("Owner") ) {
			return 12;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return 13;
		}
		if( strFieldName.equals("AccBonusMode") ) {
			return 14;
		}
		if( strFieldName.equals("BonusToInsuAccNo") ) {
			return 15;
		}
		if( strFieldName.equals("InsuAccCalBalaFlag") ) {
			return 16;
		}
		if( strFieldName.equals("BonusMode") ) {
			return 17;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return 18;
		}
		if( strFieldName.equals("CalValueFreq") ) {
			return 19;
		}
		if( strFieldName.equals("CalValueRule") ) {
			return 20;
		}
		if( strFieldName.equals("UnitDecimal") ) {
			return 21;
		}
		if( strFieldName.equals("RoundMethod") ) {
			return 22;
		}
		if( strFieldName.equals("AccBonusFlag") ) {
			return 23;
		}
		if( strFieldName.equals("Currency") ) {
			return 24;
		}
		if( strFieldName.equals("TNFlag") ) {
			return 25;
		}
		if( strFieldName.equals("FundRiskLevel") ) {
			return 26;
		}
		if( strFieldName.equals("FundCompanyName") ) {
			return 27;
		}
		if( strFieldName.equals("FundEnglishName") ) {
			return 28;
		}
		if( strFieldName.equals("SwitchType") ) {
			return 29;
		}
		if( strFieldName.equals("FundState") ) {
			return 30;
		}
		if( strFieldName.equals("FundOpenDate") ) {
			return 31;
		}
		if( strFieldName.equals("FundSuspendDate") ) {
			return 32;
		}
		if( strFieldName.equals("FundCloseDate") ) {
			return 33;
		}
		if( strFieldName.equals("FundMaturityDate") ) {
			return 34;
		}
		if( strFieldName.equals("TNFlag1") ) {
			return 35;
		}
		if( strFieldName.equals("FundMoney") ) {
			return 36;
		}
		if( strFieldName.equals("FundClass") ) {
			return 37;
		}
		if( strFieldName.equals("DueCurreny") ) {
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
				strFieldName = "InsuAccNo";
				break;
			case 1:
				strFieldName = "AccType";
				break;
			case 2:
				strFieldName = "AccKind";
				break;
			case 3:
				strFieldName = "InsuAccName";
				break;
			case 4:
				strFieldName = "AccCreatePos";
				break;
			case 5:
				strFieldName = "AccCreateType";
				break;
			case 6:
				strFieldName = "AccRate";
				break;
			case 7:
				strFieldName = "AccRateTable";
				break;
			case 8:
				strFieldName = "AccCancelCode";
				break;
			case 9:
				strFieldName = "AccComputeFlag";
				break;
			case 10:
				strFieldName = "InvestType";
				break;
			case 11:
				strFieldName = "FundCompanyCode";
				break;
			case 12:
				strFieldName = "Owner";
				break;
			case 13:
				strFieldName = "BonusFlag";
				break;
			case 14:
				strFieldName = "AccBonusMode";
				break;
			case 15:
				strFieldName = "BonusToInsuAccNo";
				break;
			case 16:
				strFieldName = "InsuAccCalBalaFlag";
				break;
			case 17:
				strFieldName = "BonusMode";
				break;
			case 18:
				strFieldName = "InvestFlag";
				break;
			case 19:
				strFieldName = "CalValueFreq";
				break;
			case 20:
				strFieldName = "CalValueRule";
				break;
			case 21:
				strFieldName = "UnitDecimal";
				break;
			case 22:
				strFieldName = "RoundMethod";
				break;
			case 23:
				strFieldName = "AccBonusFlag";
				break;
			case 24:
				strFieldName = "Currency";
				break;
			case 25:
				strFieldName = "TNFlag";
				break;
			case 26:
				strFieldName = "FundRiskLevel";
				break;
			case 27:
				strFieldName = "FundCompanyName";
				break;
			case 28:
				strFieldName = "FundEnglishName";
				break;
			case 29:
				strFieldName = "SwitchType";
				break;
			case 30:
				strFieldName = "FundState";
				break;
			case 31:
				strFieldName = "FundOpenDate";
				break;
			case 32:
				strFieldName = "FundSuspendDate";
				break;
			case 33:
				strFieldName = "FundCloseDate";
				break;
			case 34:
				strFieldName = "FundMaturityDate";
				break;
			case 35:
				strFieldName = "TNFlag1";
				break;
			case 36:
				strFieldName = "FundMoney";
				break;
			case 37:
				strFieldName = "FundClass";
				break;
			case 38:
				strFieldName = "DueCurreny";
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCreatePos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCreateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccRateTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCancelCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccComputeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundCompanyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Owner") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccBonusMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusToInsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccCalBalaFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalValueFreq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalValueRule") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitDecimal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RoundMethod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccBonusFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TNFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FundRiskLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundCompanyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundEnglishName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SwitchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundOpenDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FundSuspendDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FundCloseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FundMaturityDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TNFlag1") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FundMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FundClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DueCurreny") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 35:
				nFieldType = Schema.TYPE_INT;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
