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
import com.sinosoft.lis.db.LMUWRuleDB;

/*
 * <p>ClassName: LMUWRuleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMUWRuleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMUWRuleSchema.class);
	// @Field
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

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMUWRuleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RuleCode";

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
		LMUWRuleSchema cloned = (LMUWRuleSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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

	/**
	* 使用另外一个 LMUWRuleSchema 对象给 Schema 赋值
	* @param: aLMUWRuleSchema LMUWRuleSchema
	**/
	public void setSchema(LMUWRuleSchema aLMUWRuleSchema)
	{
		this.RuleCode = aLMUWRuleSchema.getRuleCode();
		this.RuleType = aLMUWRuleSchema.getRuleType();
		this.RuleClass = aLMUWRuleSchema.getRuleClass();
		this.RuleLevel = aLMUWRuleSchema.getRuleLevel();
		this.RuleDescription = aLMUWRuleSchema.getRuleDescription();
		this.CalSQL = aLMUWRuleSchema.getCalSQL();
		this.ParamsNum = aLMUWRuleSchema.getParamsNum();
		this.Params = aLMUWRuleSchema.getParams();
		this.DefaultValues = aLMUWRuleSchema.getDefaultValues();
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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMUWRule表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMUWRuleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMUWRuleSchema getSchema()
	{
		LMUWRuleSchema aLMUWRuleSchema = new LMUWRuleSchema();
		aLMUWRuleSchema.setSchema(this);
		return aLMUWRuleSchema;
	}

	public LMUWRuleDB getDB()
	{
		LMUWRuleDB aDBOper = new LMUWRuleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMUWRule描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamsNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Params)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultValues));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMUWRule>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RuleType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ParamsNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			Params = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DefaultValues = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMUWRuleSchema";
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
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RuleType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleClass);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RuleLevel);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleDescription);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
				break;
			case 6:
				strFieldValue = String.valueOf(ParamsNum);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Params);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DefaultValues);
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMUWRuleSchema other = (LMUWRuleSchema)otherObject;
		return
			RuleCode.equals(other.getRuleCode())
			&& RuleType.equals(other.getRuleType())
			&& RuleClass.equals(other.getRuleClass())
			&& RuleLevel.equals(other.getRuleLevel())
			&& RuleDescription.equals(other.getRuleDescription())
			&& CalSQL.equals(other.getCalSQL())
			&& ParamsNum == other.getParamsNum()
			&& Params.equals(other.getParams())
			&& DefaultValues.equals(other.getDefaultValues());
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
		if( strFieldName.equals("RuleCode") ) {
			return 0;
		}
		if( strFieldName.equals("RuleType") ) {
			return 1;
		}
		if( strFieldName.equals("RuleClass") ) {
			return 2;
		}
		if( strFieldName.equals("RuleLevel") ) {
			return 3;
		}
		if( strFieldName.equals("RuleDescription") ) {
			return 4;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 5;
		}
		if( strFieldName.equals("ParamsNum") ) {
			return 6;
		}
		if( strFieldName.equals("Params") ) {
			return 7;
		}
		if( strFieldName.equals("DefaultValues") ) {
			return 8;
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
				strFieldName = "RuleCode";
				break;
			case 1:
				strFieldName = "RuleType";
				break;
			case 2:
				strFieldName = "RuleClass";
				break;
			case 3:
				strFieldName = "RuleLevel";
				break;
			case 4:
				strFieldName = "RuleDescription";
				break;
			case 5:
				strFieldName = "CalSQL";
				break;
			case 6:
				strFieldName = "ParamsNum";
				break;
			case 7:
				strFieldName = "Params";
				break;
			case 8:
				strFieldName = "DefaultValues";
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
