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
import com.sinosoft.lis.db.LCGrpIvstPlanDB;

/*
 * <p>ClassName: LCGrpIvstPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LCGrpIvstPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpIvstPlanSchema.class);

	// @Field
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 险种编码 */
	private String RiskCode;
	/** 投资规则编码 */
	private String InvestRuleCode;
	/** 投资规则名称 */
	private String InvestRuleName;
	/** 缴费项编码 */
	private String PayPlanCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 投资录入方式 */
	private String InputMode;
	/** 投资比例下限 */
	private double InvestMinRate;
	/** 投资比例上限 */
	private double InvestMaxRate;
	/** 投资比例 */
	private double InvestRate;
	/** 投资金额 */
	private double InvestMoney;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpIvstPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "GrpPolNo";
		pk[1] = "InvestRuleCode";
		pk[2] = "PayPlanCode";
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
		LCGrpIvstPlanSchema cloned = (LCGrpIvstPlanSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	/**
	* 记录员工分类信息
	*/
	public String getInvestRuleCode()
	{
		return InvestRuleCode;
	}
	public void setInvestRuleCode(String aInvestRuleCode)
	{
		if(aInvestRuleCode!=null && aInvestRuleCode.length()>10)
			throw new IllegalArgumentException("投资规则编码InvestRuleCode值"+aInvestRuleCode+"的长度"+aInvestRuleCode.length()+"大于最大值10");
		InvestRuleCode = aInvestRuleCode;
	}
	/**
	* 记录员工分类信息
	*/
	public String getInvestRuleName()
	{
		return InvestRuleName;
	}
	public void setInvestRuleName(String aInvestRuleName)
	{
		if(aInvestRuleName!=null && aInvestRuleName.length()>20)
			throw new IllegalArgumentException("投资规则名称InvestRuleName值"+aInvestRuleName+"的长度"+aInvestRuleName.length()+"大于最大值20");
		InvestRuleName = aInvestRuleName;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		if(aPayPlanCode!=null && aPayPlanCode.length()>8)
			throw new IllegalArgumentException("缴费项编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值8");
		PayPlanCode = aPayPlanCode;
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
	/**
	* 1－－按比例<p>
	* 2－－按金额
	*/
	public String getInputMode()
	{
		return InputMode;
	}
	public void setInputMode(String aInputMode)
	{
		if(aInputMode!=null && aInputMode.length()>1)
			throw new IllegalArgumentException("投资录入方式InputMode值"+aInputMode+"的长度"+aInputMode.length()+"大于最大值1");
		InputMode = aInputMode;
	}
	public double getInvestMinRate()
	{
		return InvestMinRate;
	}
	public void setInvestMinRate(double aInvestMinRate)
	{
		InvestMinRate = aInvestMinRate;
	}
	public void setInvestMinRate(String aInvestMinRate)
	{
		if (aInvestMinRate != null && !aInvestMinRate.equals(""))
		{
			Double tDouble = new Double(aInvestMinRate);
			double d = tDouble.doubleValue();
			InvestMinRate = d;
		}
	}

	public double getInvestMaxRate()
	{
		return InvestMaxRate;
	}
	public void setInvestMaxRate(double aInvestMaxRate)
	{
		InvestMaxRate = aInvestMaxRate;
	}
	public void setInvestMaxRate(String aInvestMaxRate)
	{
		if (aInvestMaxRate != null && !aInvestMaxRate.equals(""))
		{
			Double tDouble = new Double(aInvestMaxRate);
			double d = tDouble.doubleValue();
			InvestMaxRate = d;
		}
	}

	public double getInvestRate()
	{
		return InvestRate;
	}
	public void setInvestRate(double aInvestRate)
	{
		InvestRate = aInvestRate;
	}
	public void setInvestRate(String aInvestRate)
	{
		if (aInvestRate != null && !aInvestRate.equals(""))
		{
			Double tDouble = new Double(aInvestRate);
			double d = tDouble.doubleValue();
			InvestRate = d;
		}
	}

	public double getInvestMoney()
	{
		return InvestMoney;
	}
	public void setInvestMoney(double aInvestMoney)
	{
		InvestMoney = aInvestMoney;
	}
	public void setInvestMoney(String aInvestMoney)
	{
		if (aInvestMoney != null && !aInvestMoney.equals(""))
		{
			Double tDouble = new Double(aInvestMoney);
			double d = tDouble.doubleValue();
			InvestMoney = d;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>10)
			throw new IllegalArgumentException("最后一次操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值10");
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

	/**
	* 使用另外一个 LCGrpIvstPlanSchema 对象给 Schema 赋值
	* @param: aLCGrpIvstPlanSchema LCGrpIvstPlanSchema
	**/
	public void setSchema(LCGrpIvstPlanSchema aLCGrpIvstPlanSchema)
	{
		this.GrpPolNo = aLCGrpIvstPlanSchema.getGrpPolNo();
		this.GrpContNo = aLCGrpIvstPlanSchema.getGrpContNo();
		this.RiskCode = aLCGrpIvstPlanSchema.getRiskCode();
		this.InvestRuleCode = aLCGrpIvstPlanSchema.getInvestRuleCode();
		this.InvestRuleName = aLCGrpIvstPlanSchema.getInvestRuleName();
		this.PayPlanCode = aLCGrpIvstPlanSchema.getPayPlanCode();
		this.InsuAccNo = aLCGrpIvstPlanSchema.getInsuAccNo();
		this.InputMode = aLCGrpIvstPlanSchema.getInputMode();
		this.InvestMinRate = aLCGrpIvstPlanSchema.getInvestMinRate();
		this.InvestMaxRate = aLCGrpIvstPlanSchema.getInvestMaxRate();
		this.InvestRate = aLCGrpIvstPlanSchema.getInvestRate();
		this.InvestMoney = aLCGrpIvstPlanSchema.getInvestMoney();
		this.Operator = aLCGrpIvstPlanSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpIvstPlanSchema.getMakeDate());
		this.MakeTime = aLCGrpIvstPlanSchema.getMakeTime();
		this.ModifyOperator = aLCGrpIvstPlanSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLCGrpIvstPlanSchema.getModifyDate());
		this.ModifyTime = aLCGrpIvstPlanSchema.getModifyTime();
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
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("InvestRuleCode") == null )
				this.InvestRuleCode = null;
			else
				this.InvestRuleCode = rs.getString("InvestRuleCode").trim();

			if( rs.getString("InvestRuleName") == null )
				this.InvestRuleName = null;
			else
				this.InvestRuleName = rs.getString("InvestRuleName").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("InputMode") == null )
				this.InputMode = null;
			else
				this.InputMode = rs.getString("InputMode").trim();

			this.InvestMinRate = rs.getDouble("InvestMinRate");
			this.InvestMaxRate = rs.getDouble("InvestMaxRate");
			this.InvestRate = rs.getDouble("InvestRate");
			this.InvestMoney = rs.getDouble("InvestMoney");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpIvstPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpIvstPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpIvstPlanSchema getSchema()
	{
		LCGrpIvstPlanSchema aLCGrpIvstPlanSchema = new LCGrpIvstPlanSchema();
		aLCGrpIvstPlanSchema.setSchema(this);
		return aLCGrpIvstPlanSchema;
	}

	public LCGrpIvstPlanDB getDB()
	{
		LCGrpIvstPlanDB aDBOper = new LCGrpIvstPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpIvstPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestRuleName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMinRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMaxRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InvestMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpIvstPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InvestRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InvestRuleName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InputMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InvestMinRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMaxRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			InvestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			InvestMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpIvstPlanSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("InvestRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRuleCode));
		}
		if (FCode.equalsIgnoreCase("InvestRuleName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRuleName));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("InputMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputMode));
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMinRate));
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMaxRate));
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestRate));
		}
		if (FCode.equalsIgnoreCase("InvestMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestMoney));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InvestRuleCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(InvestRuleName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InputMode);
				break;
			case 8:
				strFieldValue = String.valueOf(InvestMinRate);
				break;
			case 9:
				strFieldValue = String.valueOf(InvestMaxRate);
				break;
			case 10:
				strFieldValue = String.valueOf(InvestRate);
				break;
			case 11:
				strFieldValue = String.valueOf(InvestMoney);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("InvestRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestRuleCode = FValue.trim();
			}
			else
				InvestRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("InvestRuleName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestRuleName = FValue.trim();
			}
			else
				InvestRuleName = null;
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("InputMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputMode = FValue.trim();
			}
			else
				InputMode = null;
		}
		if (FCode.equalsIgnoreCase("InvestMinRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMinRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestMaxRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMaxRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InvestMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InvestMoney = d;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpIvstPlanSchema other = (LCGrpIvstPlanSchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& InvestRuleCode.equals(other.getInvestRuleCode())
			&& InvestRuleName.equals(other.getInvestRuleName())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& InputMode.equals(other.getInputMode())
			&& InvestMinRate == other.getInvestMinRate()
			&& InvestMaxRate == other.getInvestMaxRate()
			&& InvestRate == other.getInvestRate()
			&& InvestMoney == other.getInvestMoney()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("InvestRuleCode") ) {
			return 3;
		}
		if( strFieldName.equals("InvestRuleName") ) {
			return 4;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 5;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 6;
		}
		if( strFieldName.equals("InputMode") ) {
			return 7;
		}
		if( strFieldName.equals("InvestMinRate") ) {
			return 8;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return 9;
		}
		if( strFieldName.equals("InvestRate") ) {
			return 10;
		}
		if( strFieldName.equals("InvestMoney") ) {
			return 11;
		}
		if( strFieldName.equals("Operator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "InvestRuleCode";
				break;
			case 4:
				strFieldName = "InvestRuleName";
				break;
			case 5:
				strFieldName = "PayPlanCode";
				break;
			case 6:
				strFieldName = "InsuAccNo";
				break;
			case 7:
				strFieldName = "InputMode";
				break;
			case 8:
				strFieldName = "InvestMinRate";
				break;
			case 9:
				strFieldName = "InvestMaxRate";
				break;
			case 10:
				strFieldName = "InvestRate";
				break;
			case 11:
				strFieldName = "InvestMoney";
				break;
			case 12:
				strFieldName = "Operator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyOperator";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestRuleName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestMinRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMaxRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InvestMoney") ) {
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
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
