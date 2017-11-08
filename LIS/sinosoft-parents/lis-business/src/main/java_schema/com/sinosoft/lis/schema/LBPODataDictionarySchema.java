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
import com.sinosoft.lis.db.LBPODataDictionaryDB;

/*
 * <p>ClassName: LBPODataDictionarySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPODataDictionarySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPODataDictionarySchema.class);

	// @Field
	/** 需校验表名 */
	private String TableName;
	/** 需校验字段 */
	private String ContrasCol;
	/** 错误提示 */
	private String MSG;
	/** 错误编码 */
	private String ErrorCode;
	/** 校验类型 */
	private String CheckType;
	/** 校验规则 */
	private String CheckRule;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPODataDictionarySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TableName";
		pk[1] = "ContrasCol";
		pk[2] = "ErrorCode";
		pk[3] = "CheckType";

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
		LBPODataDictionarySchema cloned = (LBPODataDictionarySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 需要被校验的表的名字
	*/
	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String aTableName)
	{
		TableName = aTableName;
	}
	public String getContrasCol()
	{
		return ContrasCol;
	}
	public void setContrasCol(String aContrasCol)
	{
		ContrasCol = aContrasCol;
	}
	public String getMSG()
	{
		return MSG;
	}
	public void setMSG(String aMSG)
	{
		MSG = aMSG;
	}
	public String getErrorCode()
	{
		return ErrorCode;
	}
	public void setErrorCode(String aErrorCode)
	{
		ErrorCode = aErrorCode;
	}
	public String getCheckType()
	{
		return CheckType;
	}
	public void setCheckType(String aCheckType)
	{
		CheckType = aCheckType;
	}
	public String getCheckRule()
	{
		return CheckRule;
	}
	public void setCheckRule(String aCheckRule)
	{
		CheckRule = aCheckRule;
	}

	/**
	* 使用另外一个 LBPODataDictionarySchema 对象给 Schema 赋值
	* @param: aLBPODataDictionarySchema LBPODataDictionarySchema
	**/
	public void setSchema(LBPODataDictionarySchema aLBPODataDictionarySchema)
	{
		this.TableName = aLBPODataDictionarySchema.getTableName();
		this.ContrasCol = aLBPODataDictionarySchema.getContrasCol();
		this.MSG = aLBPODataDictionarySchema.getMSG();
		this.ErrorCode = aLBPODataDictionarySchema.getErrorCode();
		this.CheckType = aLBPODataDictionarySchema.getCheckType();
		this.CheckRule = aLBPODataDictionarySchema.getCheckRule();
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
			if( rs.getString("TableName") == null )
				this.TableName = null;
			else
				this.TableName = rs.getString("TableName").trim();

			if( rs.getString("ContrasCol") == null )
				this.ContrasCol = null;
			else
				this.ContrasCol = rs.getString("ContrasCol").trim();

			if( rs.getString("MSG") == null )
				this.MSG = null;
			else
				this.MSG = rs.getString("MSG").trim();

			if( rs.getString("ErrorCode") == null )
				this.ErrorCode = null;
			else
				this.ErrorCode = rs.getString("ErrorCode").trim();

			if( rs.getString("CheckType") == null )
				this.CheckType = null;
			else
				this.CheckType = rs.getString("CheckType").trim();

			if( rs.getString("CheckRule") == null )
				this.CheckRule = null;
			else
				this.CheckRule = rs.getString("CheckRule").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPODataDictionary表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPODataDictionarySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPODataDictionarySchema getSchema()
	{
		LBPODataDictionarySchema aLBPODataDictionarySchema = new LBPODataDictionarySchema();
		aLBPODataDictionarySchema.setSchema(this);
		return aLBPODataDictionarySchema;
	}

	public LBPODataDictionaryDB getDB()
	{
		LBPODataDictionaryDB aDBOper = new LBPODataDictionaryDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPODataDictionary描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContrasCol)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MSG)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckRule));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPODataDictionary>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContrasCol = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MSG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ErrorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CheckType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CheckRule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPODataDictionarySchema";
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
		if (FCode.equalsIgnoreCase("TableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableName));
		}
		if (FCode.equalsIgnoreCase("ContrasCol"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContrasCol));
		}
		if (FCode.equalsIgnoreCase("MSG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MSG));
		}
		if (FCode.equalsIgnoreCase("ErrorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorCode));
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckType));
		}
		if (FCode.equalsIgnoreCase("CheckRule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckRule));
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
				strFieldValue = StrTool.GBKToUnicode(TableName);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContrasCol);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MSG);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ErrorCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CheckType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CheckRule);
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

		if (FCode.equalsIgnoreCase("TableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableName = FValue.trim();
			}
			else
				TableName = null;
		}
		if (FCode.equalsIgnoreCase("ContrasCol"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContrasCol = FValue.trim();
			}
			else
				ContrasCol = null;
		}
		if (FCode.equalsIgnoreCase("MSG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MSG = FValue.trim();
			}
			else
				MSG = null;
		}
		if (FCode.equalsIgnoreCase("ErrorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorCode = FValue.trim();
			}
			else
				ErrorCode = null;
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckType = FValue.trim();
			}
			else
				CheckType = null;
		}
		if (FCode.equalsIgnoreCase("CheckRule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckRule = FValue.trim();
			}
			else
				CheckRule = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPODataDictionarySchema other = (LBPODataDictionarySchema)otherObject;
		return
			TableName.equals(other.getTableName())
			&& ContrasCol.equals(other.getContrasCol())
			&& MSG.equals(other.getMSG())
			&& ErrorCode.equals(other.getErrorCode())
			&& CheckType.equals(other.getCheckType())
			&& CheckRule.equals(other.getCheckRule());
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
		if( strFieldName.equals("TableName") ) {
			return 0;
		}
		if( strFieldName.equals("ContrasCol") ) {
			return 1;
		}
		if( strFieldName.equals("MSG") ) {
			return 2;
		}
		if( strFieldName.equals("ErrorCode") ) {
			return 3;
		}
		if( strFieldName.equals("CheckType") ) {
			return 4;
		}
		if( strFieldName.equals("CheckRule") ) {
			return 5;
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
				strFieldName = "TableName";
				break;
			case 1:
				strFieldName = "ContrasCol";
				break;
			case 2:
				strFieldName = "MSG";
				break;
			case 3:
				strFieldName = "ErrorCode";
				break;
			case 4:
				strFieldName = "CheckType";
				break;
			case 5:
				strFieldName = "CheckRule";
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
		if( strFieldName.equals("TableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContrasCol") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MSG") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckRule") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
