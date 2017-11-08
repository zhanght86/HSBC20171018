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
import com.sinosoft.lis.db.LDWFMulLineDB;

/*
 * <p>ClassName: LDWFMulLineSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LDWFMulLineSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDWFMulLineSchema.class);
	// @Field
	/** 功能id */
	private String FunctionID;
	/** 原表名 */
	private String BusinessTable;
	/** 字段 */
	private String FieldCode;
	/** 列宽 */
	private String ColWidth;
	/** 列序号 */
	private String ColSerialno;
	/** 是否显示 */
	private String IsShow;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDWFMulLineSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "FunctionID";
		pk[1] = "BusinessTable";
		pk[2] = "FieldCode";

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
		LDWFMulLineSchema cloned = (LDWFMulLineSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFunctionID()
	{
		return FunctionID;
	}
	public void setFunctionID(String aFunctionID)
	{
		if(aFunctionID!=null && aFunctionID.length()>10)
			throw new IllegalArgumentException("功能idFunctionID值"+aFunctionID+"的长度"+aFunctionID.length()+"大于最大值10");
		FunctionID = aFunctionID;
	}
	public String getBusinessTable()
	{
		return BusinessTable;
	}
	public void setBusinessTable(String aBusinessTable)
	{
		if(aBusinessTable!=null && aBusinessTable.length()>40)
			throw new IllegalArgumentException("原表名BusinessTable值"+aBusinessTable+"的长度"+aBusinessTable.length()+"大于最大值40");
		BusinessTable = aBusinessTable;
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
	public String getColWidth()
	{
		return ColWidth;
	}
	public void setColWidth(String aColWidth)
	{
		if(aColWidth!=null && aColWidth.length()>10)
			throw new IllegalArgumentException("列宽ColWidth值"+aColWidth+"的长度"+aColWidth.length()+"大于最大值10");
		ColWidth = aColWidth;
	}
	public String getColSerialno()
	{
		return ColSerialno;
	}
	public void setColSerialno(String aColSerialno)
	{
		if(aColSerialno!=null && aColSerialno.length()>10)
			throw new IllegalArgumentException("列序号ColSerialno值"+aColSerialno+"的长度"+aColSerialno.length()+"大于最大值10");
		ColSerialno = aColSerialno;
	}
	public String getIsShow()
	{
		return IsShow;
	}
	public void setIsShow(String aIsShow)
	{
		if(aIsShow!=null && aIsShow.length()>1)
			throw new IllegalArgumentException("是否显示IsShow值"+aIsShow+"的长度"+aIsShow.length()+"大于最大值1");
		IsShow = aIsShow;
	}

	/**
	* 使用另外一个 LDWFMulLineSchema 对象给 Schema 赋值
	* @param: aLDWFMulLineSchema LDWFMulLineSchema
	**/
	public void setSchema(LDWFMulLineSchema aLDWFMulLineSchema)
	{
		this.FunctionID = aLDWFMulLineSchema.getFunctionID();
		this.BusinessTable = aLDWFMulLineSchema.getBusinessTable();
		this.FieldCode = aLDWFMulLineSchema.getFieldCode();
		this.ColWidth = aLDWFMulLineSchema.getColWidth();
		this.ColSerialno = aLDWFMulLineSchema.getColSerialno();
		this.IsShow = aLDWFMulLineSchema.getIsShow();
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
			if( rs.getString("FunctionID") == null )
				this.FunctionID = null;
			else
				this.FunctionID = rs.getString("FunctionID").trim();

			if( rs.getString("BusinessTable") == null )
				this.BusinessTable = null;
			else
				this.BusinessTable = rs.getString("BusinessTable").trim();

			if( rs.getString("FieldCode") == null )
				this.FieldCode = null;
			else
				this.FieldCode = rs.getString("FieldCode").trim();

			if( rs.getString("ColWidth") == null )
				this.ColWidth = null;
			else
				this.ColWidth = rs.getString("ColWidth").trim();

			if( rs.getString("ColSerialno") == null )
				this.ColSerialno = null;
			else
				this.ColSerialno = rs.getString("ColSerialno").trim();

			if( rs.getString("IsShow") == null )
				this.IsShow = null;
			else
				this.IsShow = rs.getString("IsShow").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDWFMulLine表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWFMulLineSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDWFMulLineSchema getSchema()
	{
		LDWFMulLineSchema aLDWFMulLineSchema = new LDWFMulLineSchema();
		aLDWFMulLineSchema.setSchema(this);
		return aLDWFMulLineSchema;
	}

	public LDWFMulLineDB getDB()
	{
		LDWFMulLineDB aDBOper = new LDWFMulLineDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWFMulLine描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FunctionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessTable)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColWidth)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColSerialno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsShow));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWFMulLine>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FunctionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BusinessTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FieldCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ColWidth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ColSerialno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IsShow = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWFMulLineSchema";
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
		if (FCode.equalsIgnoreCase("FunctionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FunctionID));
		}
		if (FCode.equalsIgnoreCase("BusinessTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessTable));
		}
		if (FCode.equalsIgnoreCase("FieldCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldCode));
		}
		if (FCode.equalsIgnoreCase("ColWidth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColWidth));
		}
		if (FCode.equalsIgnoreCase("ColSerialno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColSerialno));
		}
		if (FCode.equalsIgnoreCase("IsShow"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsShow));
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
				strFieldValue = StrTool.GBKToUnicode(FunctionID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BusinessTable);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FieldCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ColWidth);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ColSerialno);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IsShow);
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

		if (FCode.equalsIgnoreCase("FunctionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FunctionID = FValue.trim();
			}
			else
				FunctionID = null;
		}
		if (FCode.equalsIgnoreCase("BusinessTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessTable = FValue.trim();
			}
			else
				BusinessTable = null;
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
		if (FCode.equalsIgnoreCase("ColWidth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColWidth = FValue.trim();
			}
			else
				ColWidth = null;
		}
		if (FCode.equalsIgnoreCase("ColSerialno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColSerialno = FValue.trim();
			}
			else
				ColSerialno = null;
		}
		if (FCode.equalsIgnoreCase("IsShow"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsShow = FValue.trim();
			}
			else
				IsShow = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDWFMulLineSchema other = (LDWFMulLineSchema)otherObject;
		return
			FunctionID.equals(other.getFunctionID())
			&& BusinessTable.equals(other.getBusinessTable())
			&& FieldCode.equals(other.getFieldCode())
			&& ColWidth.equals(other.getColWidth())
			&& ColSerialno.equals(other.getColSerialno())
			&& IsShow.equals(other.getIsShow());
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
		if( strFieldName.equals("FunctionID") ) {
			return 0;
		}
		if( strFieldName.equals("BusinessTable") ) {
			return 1;
		}
		if( strFieldName.equals("FieldCode") ) {
			return 2;
		}
		if( strFieldName.equals("ColWidth") ) {
			return 3;
		}
		if( strFieldName.equals("ColSerialno") ) {
			return 4;
		}
		if( strFieldName.equals("IsShow") ) {
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
				strFieldName = "FunctionID";
				break;
			case 1:
				strFieldName = "BusinessTable";
				break;
			case 2:
				strFieldName = "FieldCode";
				break;
			case 3:
				strFieldName = "ColWidth";
				break;
			case 4:
				strFieldName = "ColSerialno";
				break;
			case 5:
				strFieldName = "IsShow";
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
		if( strFieldName.equals("FunctionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColWidth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColSerialno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsShow") ) {
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
