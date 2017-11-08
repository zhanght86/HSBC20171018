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
import com.sinosoft.lis.db.LPParseGuideFieldMapDB;

/*
 * <p>ClassName: LPParseGuideFieldMapSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPParseGuideFieldMapSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPParseGuideFieldMapSchema.class);

	// @Field
	/** 批改类型 */
	private String EdorType;
	/** 页签 */
	private int SheetNo;
	/** 列号 */
	private int ColNo;
	/** 映射表名 */
	private String TableName;
	/** 映射字段 */
	private String FieldName;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPParseGuideFieldMapSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EdorType";
		pk[1] = "SheetNo";
		pk[2] = "ColNo";

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
		LPParseGuideFieldMapSchema cloned = (LPParseGuideFieldMapSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public int getSheetNo()
	{
		return SheetNo;
	}
	public void setSheetNo(int aSheetNo)
	{
		SheetNo = aSheetNo;
	}
	public void setSheetNo(String aSheetNo)
	{
		if (aSheetNo != null && !aSheetNo.equals(""))
		{
			Integer tInteger = new Integer(aSheetNo);
			int i = tInteger.intValue();
			SheetNo = i;
		}
	}

	public int getColNo()
	{
		return ColNo;
	}
	public void setColNo(int aColNo)
	{
		ColNo = aColNo;
	}
	public void setColNo(String aColNo)
	{
		if (aColNo != null && !aColNo.equals(""))
		{
			Integer tInteger = new Integer(aColNo);
			int i = tInteger.intValue();
			ColNo = i;
		}
	}

	public String getTableName()
	{
		return TableName;
	}
	public void setTableName(String aTableName)
	{
		TableName = aTableName;
	}
	public String getFieldName()
	{
		return FieldName;
	}
	public void setFieldName(String aFieldName)
	{
		FieldName = aFieldName;
	}

	/**
	* 使用另外一个 LPParseGuideFieldMapSchema 对象给 Schema 赋值
	* @param: aLPParseGuideFieldMapSchema LPParseGuideFieldMapSchema
	**/
	public void setSchema(LPParseGuideFieldMapSchema aLPParseGuideFieldMapSchema)
	{
		this.EdorType = aLPParseGuideFieldMapSchema.getEdorType();
		this.SheetNo = aLPParseGuideFieldMapSchema.getSheetNo();
		this.ColNo = aLPParseGuideFieldMapSchema.getColNo();
		this.TableName = aLPParseGuideFieldMapSchema.getTableName();
		this.FieldName = aLPParseGuideFieldMapSchema.getFieldName();
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
			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			this.SheetNo = rs.getInt("SheetNo");
			this.ColNo = rs.getInt("ColNo");
			if( rs.getString("TableName") == null )
				this.TableName = null;
			else
				this.TableName = rs.getString("TableName").trim();

			if( rs.getString("FieldName") == null )
				this.FieldName = null;
			else
				this.FieldName = rs.getString("FieldName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPParseGuideFieldMap表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPParseGuideFieldMapSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPParseGuideFieldMapSchema getSchema()
	{
		LPParseGuideFieldMapSchema aLPParseGuideFieldMapSchema = new LPParseGuideFieldMapSchema();
		aLPParseGuideFieldMapSchema.setSchema(this);
		return aLPParseGuideFieldMapSchema;
	}

	public LPParseGuideFieldMapDB getDB()
	{
		LPParseGuideFieldMapDB aDBOper = new LPParseGuideFieldMapDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPParseGuideFieldMap描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SheetNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ColNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPParseGuideFieldMap>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SheetNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ColNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			TableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPParseGuideFieldMapSchema";
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("SheetNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SheetNo));
		}
		if (FCode.equalsIgnoreCase("ColNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColNo));
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableName));
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
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
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 1:
				strFieldValue = String.valueOf(SheetNo);
				break;
			case 2:
				strFieldValue = String.valueOf(ColNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TableName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FieldName);
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

		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("SheetNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SheetNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("ColNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ColNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("TableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableName = FValue.trim();
			}
			else
				TableName = null;
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldName = FValue.trim();
			}
			else
				FieldName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPParseGuideFieldMapSchema other = (LPParseGuideFieldMapSchema)otherObject;
		return
			EdorType.equals(other.getEdorType())
			&& SheetNo == other.getSheetNo()
			&& ColNo == other.getColNo()
			&& TableName.equals(other.getTableName())
			&& FieldName.equals(other.getFieldName());
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
		if( strFieldName.equals("EdorType") ) {
			return 0;
		}
		if( strFieldName.equals("SheetNo") ) {
			return 1;
		}
		if( strFieldName.equals("ColNo") ) {
			return 2;
		}
		if( strFieldName.equals("TableName") ) {
			return 3;
		}
		if( strFieldName.equals("FieldName") ) {
			return 4;
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
				strFieldName = "EdorType";
				break;
			case 1:
				strFieldName = "SheetNo";
				break;
			case 2:
				strFieldName = "ColNo";
				break;
			case 3:
				strFieldName = "TableName";
				break;
			case 4:
				strFieldName = "FieldName";
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
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SheetNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ColNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldName") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
