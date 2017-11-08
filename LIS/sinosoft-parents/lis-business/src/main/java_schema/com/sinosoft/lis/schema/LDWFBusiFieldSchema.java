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
import com.sinosoft.lis.db.LDWFBusiFieldDB;

/*
 * <p>ClassName: LDWFBusiFieldSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LDWFBusiFieldSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDWFBusiFieldSchema.class);
	// @Field
	/** 业务表名 */
	private String BusiNessTable;
	/** 字段 */
	private String FieldCode;
	/** 字段名 */
	private String FieldCodeName;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDWFBusiFieldSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "BusiNessTable";
		pk[1] = "FieldCode";

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
		LDWFBusiFieldSchema cloned = (LDWFBusiFieldSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBusiNessTable()
	{
		return BusiNessTable;
	}
	public void setBusiNessTable(String aBusiNessTable)
	{
		if(aBusiNessTable!=null && aBusiNessTable.length()>40)
			throw new IllegalArgumentException("业务表名BusiNessTable值"+aBusiNessTable+"的长度"+aBusiNessTable.length()+"大于最大值40");
		BusiNessTable = aBusiNessTable;
	}
	public String getFieldCode()
	{
		return FieldCode;
	}
	public void setFieldCode(String aFieldCode)
	{
		if(aFieldCode!=null && aFieldCode.length()>40)
			throw new IllegalArgumentException("字段FieldCode值"+aFieldCode+"的长度"+aFieldCode.length()+"大于最大值40");
		FieldCode = aFieldCode;
	}
	public String getFieldCodeName()
	{
		return FieldCodeName;
	}
	public void setFieldCodeName(String aFieldCodeName)
	{
		if(aFieldCodeName!=null && aFieldCodeName.length()>40)
			throw new IllegalArgumentException("字段名FieldCodeName值"+aFieldCodeName+"的长度"+aFieldCodeName.length()+"大于最大值40");
		FieldCodeName = aFieldCodeName;
	}

	/**
	* 使用另外一个 LDWFBusiFieldSchema 对象给 Schema 赋值
	* @param: aLDWFBusiFieldSchema LDWFBusiFieldSchema
	**/
	public void setSchema(LDWFBusiFieldSchema aLDWFBusiFieldSchema)
	{
		this.BusiNessTable = aLDWFBusiFieldSchema.getBusiNessTable();
		this.FieldCode = aLDWFBusiFieldSchema.getFieldCode();
		this.FieldCodeName = aLDWFBusiFieldSchema.getFieldCodeName();
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
			if( rs.getString("BusiNessTable") == null )
				this.BusiNessTable = null;
			else
				this.BusiNessTable = rs.getString("BusiNessTable").trim();

			if( rs.getString("FieldCode") == null )
				this.FieldCode = null;
			else
				this.FieldCode = rs.getString("FieldCode").trim();

			if( rs.getString("FieldCodeName") == null )
				this.FieldCodeName = null;
			else
				this.FieldCodeName = rs.getString("FieldCodeName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDWFBusiField表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWFBusiFieldSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDWFBusiFieldSchema getSchema()
	{
		LDWFBusiFieldSchema aLDWFBusiFieldSchema = new LDWFBusiFieldSchema();
		aLDWFBusiFieldSchema.setSchema(this);
		return aLDWFBusiFieldSchema;
	}

	public LDWFBusiFieldDB getDB()
	{
		LDWFBusiFieldDB aDBOper = new LDWFBusiFieldDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWFBusiField描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BusiNessTable)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldCodeName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWFBusiField>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BusiNessTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FieldCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FieldCodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWFBusiFieldSchema";
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
		if (FCode.equalsIgnoreCase("BusiNessTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiNessTable));
		}
		if (FCode.equalsIgnoreCase("FieldCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldCode));
		}
		if (FCode.equalsIgnoreCase("FieldCodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldCodeName));
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
				strFieldValue = StrTool.GBKToUnicode(BusiNessTable);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FieldCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FieldCodeName);
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

		if (FCode.equalsIgnoreCase("BusiNessTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiNessTable = FValue.trim();
			}
			else
				BusiNessTable = null;
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
		if (FCode.equalsIgnoreCase("FieldCodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldCodeName = FValue.trim();
			}
			else
				FieldCodeName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDWFBusiFieldSchema other = (LDWFBusiFieldSchema)otherObject;
		return
			BusiNessTable.equals(other.getBusiNessTable())
			&& FieldCode.equals(other.getFieldCode())
			&& FieldCodeName.equals(other.getFieldCodeName());
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
		if( strFieldName.equals("BusiNessTable") ) {
			return 0;
		}
		if( strFieldName.equals("FieldCode") ) {
			return 1;
		}
		if( strFieldName.equals("FieldCodeName") ) {
			return 2;
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
				strFieldName = "BusiNessTable";
				break;
			case 1:
				strFieldName = "FieldCode";
				break;
			case 2:
				strFieldName = "FieldCodeName";
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
		if( strFieldName.equals("BusiNessTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldCodeName") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
