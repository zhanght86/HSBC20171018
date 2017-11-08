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
import com.sinosoft.lis.db.LSQuotUWRuleDB;

/*
 * <p>ClassName: LSQuotUWRuleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotUWRuleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotUWRuleSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 险种编码 */
	private String RiskCode;
	/** 规则编码 */
	private String RuleCode;
	/** 规则类型 */
	private String RuleType;
	/** 规则分类 */
	private String RuleClass;
	/** 规则层级 */
	private String RuleLevel;
	/** 规则描述 */
	private String RuleDescription;
	/** 计算sql */
	private String CalSQL;
	/** 参数个数 */
	private int ParamsNum;
	/** 参数 */
	private String Params;
	/** 默认值 */
	private String DefaultValues;
	/** 参数值 */
	private String InputValues;
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

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotUWRuleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";
		pk[2] = "RiskCode";
		pk[3] = "RuleCode";

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
		LSQuotUWRuleSchema cloned = (LSQuotUWRuleSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuotNo()
	{
		return QuotNo;
	}
	public void setQuotNo(String aQuotNo)
	{
		if(aQuotNo!=null && aQuotNo.length()>20)
			throw new IllegalArgumentException("询价号QuotNo值"+aQuotNo+"的长度"+aQuotNo.length()+"大于最大值20");
		QuotNo = aQuotNo;
	}
	public int getQuotBatNo()
	{
		return QuotBatNo;
	}
	public void setQuotBatNo(int aQuotBatNo)
	{
		QuotBatNo = aQuotBatNo;
	}
	public void setQuotBatNo(String aQuotBatNo)
	{
		if (aQuotBatNo != null && !aQuotBatNo.equals(""))
		{
			Integer tInteger = new Integer(aQuotBatNo);
			int i = tInteger.intValue();
			QuotBatNo = i;
		}
	}

	/**
	* 规则层级为00-团体保单、10-个人保单，险种默认为000000
	*/
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
	public String getRuleCode()
	{
		return RuleCode;
	}
	public void setRuleCode(String aRuleCode)
	{
		if(aRuleCode!=null && aRuleCode.length()>10)
			throw new IllegalArgumentException("规则编码RuleCode值"+aRuleCode+"的长度"+aRuleCode.length()+"大于最大值10");
		RuleCode = aRuleCode;
	}
	/**
	* 01-核保规则，02-核保要点
	*/
	public String getRuleType()
	{
		return RuleType;
	}
	public void setRuleType(String aRuleType)
	{
		if(aRuleType!=null && aRuleType.length()>2)
			throw new IllegalArgumentException("规则类型RuleType值"+aRuleType+"的长度"+aRuleType.length()+"大于最大值2");
		RuleType = aRuleType;
	}
	public String getRuleClass()
	{
		return RuleClass;
	}
	public void setRuleClass(String aRuleClass)
	{
		if(aRuleClass!=null && aRuleClass.length()>2)
			throw new IllegalArgumentException("规则分类RuleClass值"+aRuleClass+"的长度"+aRuleClass.length()+"大于最大值2");
		RuleClass = aRuleClass;
	}
	/**
	* 00-团体保单，01-团体险种，10-个人保单，11-个人险种
	*/
	public String getRuleLevel()
	{
		return RuleLevel;
	}
	public void setRuleLevel(String aRuleLevel)
	{
		if(aRuleLevel!=null && aRuleLevel.length()>2)
			throw new IllegalArgumentException("规则层级RuleLevel值"+aRuleLevel+"的长度"+aRuleLevel.length()+"大于最大值2");
		RuleLevel = aRuleLevel;
	}
	public String getRuleDescription()
	{
		return RuleDescription;
	}
	public void setRuleDescription(String aRuleDescription)
	{
		if(aRuleDescription!=null && aRuleDescription.length()>4000)
			throw new IllegalArgumentException("规则描述RuleDescription值"+aRuleDescription+"的长度"+aRuleDescription.length()+"大于最大值4000");
		RuleDescription = aRuleDescription;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		if(aCalSQL!=null && aCalSQL.length()>4000)
			throw new IllegalArgumentException("计算sqlCalSQL值"+aCalSQL+"的长度"+aCalSQL.length()+"大于最大值4000");
		CalSQL = aCalSQL;
	}
	public int getParamsNum()
	{
		return ParamsNum;
	}
	public void setParamsNum(int aParamsNum)
	{
		ParamsNum = aParamsNum;
	}
	public void setParamsNum(String aParamsNum)
	{
		if (aParamsNum != null && !aParamsNum.equals(""))
		{
			Integer tInteger = new Integer(aParamsNum);
			int i = tInteger.intValue();
			ParamsNum = i;
		}
	}

	public String getParams()
	{
		return Params;
	}
	public void setParams(String aParams)
	{
		if(aParams!=null && aParams.length()>200)
			throw new IllegalArgumentException("参数Params值"+aParams+"的长度"+aParams.length()+"大于最大值200");
		Params = aParams;
	}
	public String getDefaultValues()
	{
		return DefaultValues;
	}
	public void setDefaultValues(String aDefaultValues)
	{
		if(aDefaultValues!=null && aDefaultValues.length()>200)
			throw new IllegalArgumentException("默认值DefaultValues值"+aDefaultValues+"的长度"+aDefaultValues.length()+"大于最大值200");
		DefaultValues = aDefaultValues;
	}
	public String getInputValues()
	{
		return InputValues;
	}
	public void setInputValues(String aInputValues)
	{
		if(aInputValues!=null && aInputValues.length()>200)
			throw new IllegalArgumentException("参数值InputValues值"+aInputValues+"的长度"+aInputValues.length()+"大于最大值200");
		InputValues = aInputValues;
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

	/**
	* 使用另外一个 LSQuotUWRuleSchema 对象给 Schema 赋值
	* @param: aLSQuotUWRuleSchema LSQuotUWRuleSchema
	**/
	public void setSchema(LSQuotUWRuleSchema aLSQuotUWRuleSchema)
	{
		this.QuotNo = aLSQuotUWRuleSchema.getQuotNo();
		this.QuotBatNo = aLSQuotUWRuleSchema.getQuotBatNo();
		this.RiskCode = aLSQuotUWRuleSchema.getRiskCode();
		this.RuleCode = aLSQuotUWRuleSchema.getRuleCode();
		this.RuleType = aLSQuotUWRuleSchema.getRuleType();
		this.RuleClass = aLSQuotUWRuleSchema.getRuleClass();
		this.RuleLevel = aLSQuotUWRuleSchema.getRuleLevel();
		this.RuleDescription = aLSQuotUWRuleSchema.getRuleDescription();
		this.CalSQL = aLSQuotUWRuleSchema.getCalSQL();
		this.ParamsNum = aLSQuotUWRuleSchema.getParamsNum();
		this.Params = aLSQuotUWRuleSchema.getParams();
		this.DefaultValues = aLSQuotUWRuleSchema.getDefaultValues();
		this.InputValues = aLSQuotUWRuleSchema.getInputValues();
		this.MakeOperator = aLSQuotUWRuleSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotUWRuleSchema.getMakeDate());
		this.MakeTime = aLSQuotUWRuleSchema.getMakeTime();
		this.ModifyOperator = aLSQuotUWRuleSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotUWRuleSchema.getModifyDate());
		this.ModifyTime = aLSQuotUWRuleSchema.getModifyTime();
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
			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RuleCode") == null )
				this.RuleCode = null;
			else
				this.RuleCode = rs.getString("RuleCode").trim();

			if( rs.getString("RuleType") == null )
				this.RuleType = null;
			else
				this.RuleType = rs.getString("RuleType").trim();

			if( rs.getString("RuleClass") == null )
				this.RuleClass = null;
			else
				this.RuleClass = rs.getString("RuleClass").trim();

			if( rs.getString("RuleLevel") == null )
				this.RuleLevel = null;
			else
				this.RuleLevel = rs.getString("RuleLevel").trim();

			if( rs.getString("RuleDescription") == null )
				this.RuleDescription = null;
			else
				this.RuleDescription = rs.getString("RuleDescription").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

			this.ParamsNum = rs.getInt("ParamsNum");
			if( rs.getString("Params") == null )
				this.Params = null;
			else
				this.Params = rs.getString("Params").trim();

			if( rs.getString("DefaultValues") == null )
				this.DefaultValues = null;
			else
				this.DefaultValues = rs.getString("DefaultValues").trim();

			if( rs.getString("InputValues") == null )
				this.InputValues = null;
			else
				this.InputValues = rs.getString("InputValues").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LSQuotUWRule表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotUWRuleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotUWRuleSchema getSchema()
	{
		LSQuotUWRuleSchema aLSQuotUWRuleSchema = new LSQuotUWRuleSchema();
		aLSQuotUWRuleSchema.setSchema(this);
		return aLSQuotUWRuleSchema;
	}

	public LSQuotUWRuleDB getDB()
	{
		LSQuotUWRuleDB aDBOper = new LSQuotUWRuleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotUWRule描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamsNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Params)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultValues)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputValues)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotUWRule>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RuleClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RuleLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RuleDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ParamsNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			Params = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DefaultValues = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InputValues = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotUWRuleSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCode));
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleType));
		}
		if (FCode.equalsIgnoreCase("RuleClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleClass));
		}
		if (FCode.equalsIgnoreCase("RuleLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleLevel));
		}
		if (FCode.equalsIgnoreCase("RuleDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDescription));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
		}
		if (FCode.equalsIgnoreCase("ParamsNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamsNum));
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Params));
		}
		if (FCode.equalsIgnoreCase("DefaultValues"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValues));
		}
		if (FCode.equalsIgnoreCase("InputValues"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputValues));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 1:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RuleClass);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RuleLevel);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RuleDescription);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
				break;
			case 9:
				strFieldValue = String.valueOf(ParamsNum);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Params);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DefaultValues);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InputValues);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
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

		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotNo = FValue.trim();
			}
			else
				QuotNo = null;
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				QuotBatNo = i;
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
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCode = FValue.trim();
			}
			else
				RuleCode = null;
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleType = FValue.trim();
			}
			else
				RuleType = null;
		}
		if (FCode.equalsIgnoreCase("RuleClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleClass = FValue.trim();
			}
			else
				RuleClass = null;
		}
		if (FCode.equalsIgnoreCase("RuleLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleLevel = FValue.trim();
			}
			else
				RuleLevel = null;
		}
		if (FCode.equalsIgnoreCase("RuleDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDescription = FValue.trim();
			}
			else
				RuleDescription = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		if (FCode.equalsIgnoreCase("ParamsNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ParamsNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Params = FValue.trim();
			}
			else
				Params = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValues"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultValues = FValue.trim();
			}
			else
				DefaultValues = null;
		}
		if (FCode.equalsIgnoreCase("InputValues"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputValues = FValue.trim();
			}
			else
				InputValues = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LSQuotUWRuleSchema other = (LSQuotUWRuleSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& RiskCode.equals(other.getRiskCode())
			&& RuleCode.equals(other.getRuleCode())
			&& RuleType.equals(other.getRuleType())
			&& RuleClass.equals(other.getRuleClass())
			&& RuleLevel.equals(other.getRuleLevel())
			&& RuleDescription.equals(other.getRuleDescription())
			&& CalSQL.equals(other.getCalSQL())
			&& ParamsNum == other.getParamsNum()
			&& Params.equals(other.getParams())
			&& DefaultValues.equals(other.getDefaultValues())
			&& InputValues.equals(other.getInputValues())
			&& MakeOperator.equals(other.getMakeOperator())
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
		if( strFieldName.equals("QuotNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("RuleCode") ) {
			return 3;
		}
		if( strFieldName.equals("RuleType") ) {
			return 4;
		}
		if( strFieldName.equals("RuleClass") ) {
			return 5;
		}
		if( strFieldName.equals("RuleLevel") ) {
			return 6;
		}
		if( strFieldName.equals("RuleDescription") ) {
			return 7;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 8;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return 9;
		}
		if( strFieldName.equals("Params") ) {
			return 10;
		}
		if( strFieldName.equals("DefaultValues") ) {
			return 11;
		}
		if( strFieldName.equals("InputValues") ) {
			return 12;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
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
				strFieldName = "QuotNo";
				break;
			case 1:
				strFieldName = "QuotBatNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "RuleCode";
				break;
			case 4:
				strFieldName = "RuleType";
				break;
			case 5:
				strFieldName = "RuleClass";
				break;
			case 6:
				strFieldName = "RuleLevel";
				break;
			case 7:
				strFieldName = "RuleDescription";
				break;
			case 8:
				strFieldName = "CalSQL";
				break;
			case 9:
				strFieldName = "ParamsNum";
				break;
			case 10:
				strFieldName = "Params";
				break;
			case 11:
				strFieldName = "DefaultValues";
				break;
			case 12:
				strFieldName = "InputValues";
				break;
			case 13:
				strFieldName = "MakeOperator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyOperator";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Params") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValues") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputValues") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
