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
import com.sinosoft.lis.db.LDSysVarDB;

/*
 * <p>ClassName: LDSysVarSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDSysVarSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDSysVarSchema.class);

	// @Field
	/** 系统变量名 */
	private String SysVar;
	/** 系统变量类型 */
	private String SysVarType;
	/** 系统变量值 */
	private String SysVarValue;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDSysVarSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SysVar";

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
		LDSysVarSchema cloned = (LDSysVarSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSysVar()
	{
		return SysVar;
	}
	public void setSysVar(String aSysVar)
	{
		SysVar = aSysVar;
	}
	public String getSysVarType()
	{
		return SysVarType;
	}
	public void setSysVarType(String aSysVarType)
	{
		SysVarType = aSysVarType;
	}
	public String getSysVarValue()
	{
		return SysVarValue;
	}
	public void setSysVarValue(String aSysVarValue)
	{
		SysVarValue = aSysVarValue;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LDSysVarSchema 对象给 Schema 赋值
	* @param: aLDSysVarSchema LDSysVarSchema
	**/
	public void setSchema(LDSysVarSchema aLDSysVarSchema)
	{
		this.SysVar = aLDSysVarSchema.getSysVar();
		this.SysVarType = aLDSysVarSchema.getSysVarType();
		this.SysVarValue = aLDSysVarSchema.getSysVarValue();
		this.Remark = aLDSysVarSchema.getRemark();
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
			if( rs.getString("SysVar") == null )
				this.SysVar = null;
			else
				this.SysVar = rs.getString("SysVar").trim();

			if( rs.getString("SysVarType") == null )
				this.SysVarType = null;
			else
				this.SysVarType = rs.getString("SysVarType").trim();

			if( rs.getString("SysVarValue") == null )
				this.SysVarValue = null;
			else
				this.SysVarValue = rs.getString("SysVarValue").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDSysVar表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDSysVarSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDSysVarSchema getSchema()
	{
		LDSysVarSchema aLDSysVarSchema = new LDSysVarSchema();
		aLDSysVarSchema.setSchema(this);
		return aLDSysVarSchema;
	}

	public LDSysVarDB getDB()
	{
		LDSysVarDB aDBOper = new LDSysVarDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDSysVar描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SysVar)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysVarType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysVarValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDSysVar>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SysVar = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SysVarType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SysVarValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDSysVarSchema";
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
		if (FCode.equalsIgnoreCase("SysVar"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysVar));
		}
		if (FCode.equalsIgnoreCase("SysVarType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysVarType));
		}
		if (FCode.equalsIgnoreCase("SysVarValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysVarValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(SysVar);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SysVarType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SysVarValue);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("SysVar"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysVar = FValue.trim();
			}
			else
				SysVar = null;
		}
		if (FCode.equalsIgnoreCase("SysVarType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysVarType = FValue.trim();
			}
			else
				SysVarType = null;
		}
		if (FCode.equalsIgnoreCase("SysVarValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysVarValue = FValue.trim();
			}
			else
				SysVarValue = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDSysVarSchema other = (LDSysVarSchema)otherObject;
		return
			SysVar.equals(other.getSysVar())
			&& SysVarType.equals(other.getSysVarType())
			&& SysVarValue.equals(other.getSysVarValue())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("SysVar") ) {
			return 0;
		}
		if( strFieldName.equals("SysVarType") ) {
			return 1;
		}
		if( strFieldName.equals("SysVarValue") ) {
			return 2;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "SysVar";
				break;
			case 1:
				strFieldName = "SysVarType";
				break;
			case 2:
				strFieldName = "SysVarValue";
				break;
			case 3:
				strFieldName = "Remark";
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
		if( strFieldName.equals("SysVar") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysVarType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysVarValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
