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
import com.sinosoft.lis.db.LDCurrencyDB;

/*
 * <p>ClassName: LDCurrencySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造基础表
 */
public class LDCurrencySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCurrencySchema.class);

	// @Field
	/** 币种代码 */
	private String CurrCode;
	/** 币种名称 */
	private String CurrName;
	/** 有效标志 */
	private String Validation;
	/** 代码别名 */
	private String CodeAlias;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCurrencySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CurrCode";

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
		LDCurrencySchema cloned = (LDCurrencySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCurrCode()
	{
		return CurrCode;
	}
	public void setCurrCode(String aCurrCode)
	{
		CurrCode = aCurrCode;
	}
	public String getCurrName()
	{
		return CurrName;
	}
	public void setCurrName(String aCurrName)
	{
		CurrName = aCurrName;
	}
	/**
	* 0——无效；<p>
	* 1——有效；
	*/
	public String getValidation()
	{
		return Validation;
	}
	public void setValidation(String aValidation)
	{
		Validation = aValidation;
	}
	public String getCodeAlias()
	{
		return CodeAlias;
	}
	public void setCodeAlias(String aCodeAlias)
	{
		CodeAlias = aCodeAlias;
	}

	/**
	* 使用另外一个 LDCurrencySchema 对象给 Schema 赋值
	* @param: aLDCurrencySchema LDCurrencySchema
	**/
	public void setSchema(LDCurrencySchema aLDCurrencySchema)
	{
		this.CurrCode = aLDCurrencySchema.getCurrCode();
		this.CurrName = aLDCurrencySchema.getCurrName();
		this.Validation = aLDCurrencySchema.getValidation();
		this.CodeAlias = aLDCurrencySchema.getCodeAlias();
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
			if( rs.getString("CurrCode") == null )
				this.CurrCode = null;
			else
				this.CurrCode = rs.getString("CurrCode").trim();

			if( rs.getString("CurrName") == null )
				this.CurrName = null;
			else
				this.CurrName = rs.getString("CurrName").trim();

			if( rs.getString("Validation") == null )
				this.Validation = null;
			else
				this.Validation = rs.getString("Validation").trim();

			if( rs.getString("CodeAlias") == null )
				this.CodeAlias = null;
			else
				this.CodeAlias = rs.getString("CodeAlias").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCurrency表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCurrencySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCurrencySchema getSchema()
	{
		LDCurrencySchema aLDCurrencySchema = new LDCurrencySchema();
		aLDCurrencySchema.setSchema(this);
		return aLDCurrencySchema;
	}

	public LDCurrencyDB getDB()
	{
		LDCurrencyDB aDBOper = new LDCurrencyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCurrency描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CurrCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Validation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeAlias));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCurrency>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CurrCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CurrName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Validation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CodeAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCurrencySchema";
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
		if (FCode.equalsIgnoreCase("CurrCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrCode));
		}
		if (FCode.equalsIgnoreCase("CurrName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrName));
		}
		if (FCode.equalsIgnoreCase("Validation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Validation));
		}
		if (FCode.equalsIgnoreCase("CodeAlias"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeAlias));
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
				strFieldValue = StrTool.GBKToUnicode(CurrCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CurrName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Validation);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CodeAlias);
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

		if (FCode.equalsIgnoreCase("CurrCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrCode = FValue.trim();
			}
			else
				CurrCode = null;
		}
		if (FCode.equalsIgnoreCase("CurrName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrName = FValue.trim();
			}
			else
				CurrName = null;
		}
		if (FCode.equalsIgnoreCase("Validation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Validation = FValue.trim();
			}
			else
				Validation = null;
		}
		if (FCode.equalsIgnoreCase("CodeAlias"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeAlias = FValue.trim();
			}
			else
				CodeAlias = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCurrencySchema other = (LDCurrencySchema)otherObject;
		return
			CurrCode.equals(other.getCurrCode())
			&& CurrName.equals(other.getCurrName())
			&& Validation.equals(other.getValidation())
			&& CodeAlias.equals(other.getCodeAlias());
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
		if( strFieldName.equals("CurrCode") ) {
			return 0;
		}
		if( strFieldName.equals("CurrName") ) {
			return 1;
		}
		if( strFieldName.equals("Validation") ) {
			return 2;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return 3;
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
				strFieldName = "CurrCode";
				break;
			case 1:
				strFieldName = "CurrName";
				break;
			case 2:
				strFieldName = "Validation";
				break;
			case 3:
				strFieldName = "CodeAlias";
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
		if( strFieldName.equals("CurrCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Validation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeAlias") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
