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
import com.sinosoft.lis.db.LMFactorConfigDB;

/*
 * <p>ClassName: LMFactorConfigSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMFactorConfigSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMFactorConfigSchema.class);
	// @Field
	/** 要素编码 */
	private String FactorCode;
	/** 要素名称 */
	private String FactorName;
	/** 字段编码 */
	private String FieldCode;
	/** 字段类型 */
	private String FieldType;
	/** 值类型 */
	private String ValueType;
	/** 值长度 */
	private String ValueLength;
	/** 值范围 */
	private String ValueScope;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMFactorConfigSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "FactorCode";

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
		LMFactorConfigSchema cloned = (LMFactorConfigSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFactorCode()
	{
		return FactorCode;
	}
	public void setFactorCode(String aFactorCode)
	{
		if(aFactorCode!=null && aFactorCode.length()>50)
			throw new IllegalArgumentException("要素编码FactorCode值"+aFactorCode+"的长度"+aFactorCode.length()+"大于最大值50");
		FactorCode = aFactorCode;
	}
	/**
	* 页面展示名称
	*/
	public String getFactorName()
	{
		return FactorName;
	}
	public void setFactorName(String aFactorName)
	{
		if(aFactorName!=null && aFactorName.length()>200)
			throw new IllegalArgumentException("要素名称FactorName值"+aFactorName+"的长度"+aFactorName.length()+"大于最大值200");
		FactorName = aFactorName;
	}
	public String getFieldCode()
	{
		return FieldCode;
	}
	public void setFieldCode(String aFieldCode)
	{
		if(aFieldCode!=null && aFieldCode.length()>50)
			throw new IllegalArgumentException("字段编码FieldCode值"+aFieldCode+"的长度"+aFieldCode.length()+"大于最大值50");
		FieldCode = aFieldCode;
	}
	/**
	* 0-录入框，1-下拉框，2-日期框
	*/
	public String getFieldType()
	{
		return FieldType;
	}
	public void setFieldType(String aFieldType)
	{
		if(aFieldType!=null && aFieldType.length()>2)
			throw new IllegalArgumentException("字段类型FieldType值"+aFieldType+"的长度"+aFieldType.length()+"大于最大值2");
		FieldType = aFieldType;
	}
	/**
	* 如果为常规类型(如日期)，需大写(DATE)；如果为ldcode中对应类型，请对应ldcode字段。<p>
	* 整数：INT<p>
	* 小数：NUM<p>
	* 日期：DATE<p>
	* 字符：VARCHAR
	*/
	public String getValueType()
	{
		return ValueType;
	}
	public void setValueType(String aValueType)
	{
		if(aValueType!=null && aValueType.length()>20)
			throw new IllegalArgumentException("值类型ValueType值"+aValueType+"的长度"+aValueType.length()+"大于最大值20");
		ValueType = aValueType;
	}
	/**
	* 配合字段类型【0-录入框】使用。如果值类型【NUM】，需用逗号隔开如：16,2
	*/
	public String getValueLength()
	{
		return ValueLength;
	}
	public void setValueLength(String aValueLength)
	{
		if(aValueLength!=null && aValueLength.length()>4)
			throw new IllegalArgumentException("值长度ValueLength值"+aValueLength+"的长度"+aValueLength.length()+"大于最大值4");
		ValueLength = aValueLength;
	}
	/**
	* 配合字段类型【0-录入框】使用，如果值类型【NUM】、【INT】可用。例如：(0,1]
	*/
	public String getValueScope()
	{
		return ValueScope;
	}
	public void setValueScope(String aValueScope)
	{
		if(aValueScope!=null && aValueScope.length()>20)
			throw new IllegalArgumentException("值范围ValueScope值"+aValueScope+"的长度"+aValueScope.length()+"大于最大值20");
		ValueScope = aValueScope;
	}

	/**
	* 使用另外一个 LMFactorConfigSchema 对象给 Schema 赋值
	* @param: aLMFactorConfigSchema LMFactorConfigSchema
	**/
	public void setSchema(LMFactorConfigSchema aLMFactorConfigSchema)
	{
		this.FactorCode = aLMFactorConfigSchema.getFactorCode();
		this.FactorName = aLMFactorConfigSchema.getFactorName();
		this.FieldCode = aLMFactorConfigSchema.getFieldCode();
		this.FieldType = aLMFactorConfigSchema.getFieldType();
		this.ValueType = aLMFactorConfigSchema.getValueType();
		this.ValueLength = aLMFactorConfigSchema.getValueLength();
		this.ValueScope = aLMFactorConfigSchema.getValueScope();
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
			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("FieldCode") == null )
				this.FieldCode = null;
			else
				this.FieldCode = rs.getString("FieldCode").trim();

			if( rs.getString("FieldType") == null )
				this.FieldType = null;
			else
				this.FieldType = rs.getString("FieldType").trim();

			if( rs.getString("ValueType") == null )
				this.ValueType = null;
			else
				this.ValueType = rs.getString("ValueType").trim();

			if( rs.getString("ValueLength") == null )
				this.ValueLength = null;
			else
				this.ValueLength = rs.getString("ValueLength").trim();

			if( rs.getString("ValueScope") == null )
				this.ValueScope = null;
			else
				this.ValueScope = rs.getString("ValueScope").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMFactorConfig表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactorConfigSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMFactorConfigSchema getSchema()
	{
		LMFactorConfigSchema aLMFactorConfigSchema = new LMFactorConfigSchema();
		aLMFactorConfigSchema.setSchema(this);
		return aLMFactorConfigSchema;
	}

	public LMFactorConfigDB getDB()
	{
		LMFactorConfigDB aDBOper = new LMFactorConfigDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFactorConfig描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FactorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValueType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValueLength)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValueScope));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMFactorConfig>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FieldCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FieldType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ValueType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ValueLength = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ValueScope = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMFactorConfigSchema";
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
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equalsIgnoreCase("FieldCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldCode));
		}
		if (FCode.equalsIgnoreCase("FieldType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldType));
		}
		if (FCode.equalsIgnoreCase("ValueType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValueType));
		}
		if (FCode.equalsIgnoreCase("ValueLength"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValueLength));
		}
		if (FCode.equalsIgnoreCase("ValueScope"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValueScope));
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
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FieldCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FieldType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ValueType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ValueLength);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ValueScope);
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

		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorCode = FValue.trim();
			}
			else
				FactorCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorName = FValue.trim();
			}
			else
				FactorName = null;
		}
		if (FCode.equalsIgnoreCase("FieldCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldCode = FValue.trim();
			}
			else
				FieldCode = null;
		}
		if (FCode.equalsIgnoreCase("FieldType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldType = FValue.trim();
			}
			else
				FieldType = null;
		}
		if (FCode.equalsIgnoreCase("ValueType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValueType = FValue.trim();
			}
			else
				ValueType = null;
		}
		if (FCode.equalsIgnoreCase("ValueLength"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValueLength = FValue.trim();
			}
			else
				ValueLength = null;
		}
		if (FCode.equalsIgnoreCase("ValueScope"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValueScope = FValue.trim();
			}
			else
				ValueScope = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMFactorConfigSchema other = (LMFactorConfigSchema)otherObject;
		return
			FactorCode.equals(other.getFactorCode())
			&& FactorName.equals(other.getFactorName())
			&& FieldCode.equals(other.getFieldCode())
			&& FieldType.equals(other.getFieldType())
			&& ValueType.equals(other.getValueType())
			&& ValueLength.equals(other.getValueLength())
			&& ValueScope.equals(other.getValueScope());
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
		if( strFieldName.equals("FactorCode") ) {
			return 0;
		}
		if( strFieldName.equals("FactorName") ) {
			return 1;
		}
		if( strFieldName.equals("FieldCode") ) {
			return 2;
		}
		if( strFieldName.equals("FieldType") ) {
			return 3;
		}
		if( strFieldName.equals("ValueType") ) {
			return 4;
		}
		if( strFieldName.equals("ValueLength") ) {
			return 5;
		}
		if( strFieldName.equals("ValueScope") ) {
			return 6;
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
				strFieldName = "FactorCode";
				break;
			case 1:
				strFieldName = "FactorName";
				break;
			case 2:
				strFieldName = "FieldCode";
				break;
			case 3:
				strFieldName = "FieldType";
				break;
			case 4:
				strFieldName = "ValueType";
				break;
			case 5:
				strFieldName = "ValueLength";
				break;
			case 6:
				strFieldName = "ValueScope";
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
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValueType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValueLength") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValueScope") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
