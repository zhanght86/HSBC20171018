/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIAssociatedDirectItemDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIAssociatedDirectItemDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIAssociatedDirectItemDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIAssociatedDirectItemDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 专项表字段标识 */
	private String ColumnID;
	/** 上游数据来源表名 */
	private String SourceTableID;
	/** 上游数据来源字段 */
	private String SourceColumnID;
	/** 描述 */
	private String ReMark;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIAssociatedDirectItemDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "ColumnID";

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
                FIAssociatedDirectItemDefSchema cloned = (FIAssociatedDirectItemDefSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getVersionNo()
	{
		return VersionNo;
	}
	public void setVersionNo(String aVersionNo)
	{
		VersionNo = aVersionNo;
	}
	public String getColumnID()
	{
		return ColumnID;
	}
	public void setColumnID(String aColumnID)
	{
		ColumnID = aColumnID;
	}
	public String getSourceTableID()
	{
		return SourceTableID;
	}
	public void setSourceTableID(String aSourceTableID)
	{
		SourceTableID = aSourceTableID;
	}
	public String getSourceColumnID()
	{
		return SourceColumnID;
	}
	public void setSourceColumnID(String aSourceColumnID)
	{
		SourceColumnID = aSourceColumnID;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}

	/**
	* 使用另外一个 FIAssociatedDirectItemDefSchema 对象给 Schema 赋值
	* @param: aFIAssociatedDirectItemDefSchema FIAssociatedDirectItemDefSchema
	**/
	public void setSchema(FIAssociatedDirectItemDefSchema aFIAssociatedDirectItemDefSchema)
	{
		this.VersionNo = aFIAssociatedDirectItemDefSchema.getVersionNo();
		this.ColumnID = aFIAssociatedDirectItemDefSchema.getColumnID();
		this.SourceTableID = aFIAssociatedDirectItemDefSchema.getSourceTableID();
		this.SourceColumnID = aFIAssociatedDirectItemDefSchema.getSourceColumnID();
		this.ReMark = aFIAssociatedDirectItemDefSchema.getReMark();
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
			if( rs.getString("VersionNo") == null )
				this.VersionNo = null;
			else
				this.VersionNo = rs.getString("VersionNo").trim();

			if( rs.getString("ColumnID") == null )
				this.ColumnID = null;
			else
				this.ColumnID = rs.getString("ColumnID").trim();

			if( rs.getString("SourceTableID") == null )
				this.SourceTableID = null;
			else
				this.SourceTableID = rs.getString("SourceTableID").trim();

			if( rs.getString("SourceColumnID") == null )
				this.SourceColumnID = null;
			else
				this.SourceColumnID = rs.getString("SourceColumnID").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIAssociatedDirectItemDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedDirectItemDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIAssociatedDirectItemDefSchema getSchema()
	{
		FIAssociatedDirectItemDefSchema aFIAssociatedDirectItemDefSchema = new FIAssociatedDirectItemDefSchema();
		aFIAssociatedDirectItemDefSchema.setSchema(this);
		return aFIAssociatedDirectItemDefSchema;
	}

	public FIAssociatedDirectItemDefDB getDB()
	{
		FIAssociatedDirectItemDefDB aDBOper = new FIAssociatedDirectItemDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIAssociatedDirectItemDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ColumnID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SourceTableID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SourceColumnID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIAssociatedDirectItemDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ColumnID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SourceTableID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SourceColumnID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedDirectItemDefSchema";
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
		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VersionNo));
		}
		if (FCode.equalsIgnoreCase("ColumnID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColumnID));
		}
		if (FCode.equalsIgnoreCase("SourceTableID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceTableID));
		}
		if (FCode.equalsIgnoreCase("SourceColumnID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceColumnID));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
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
				strFieldValue = StrTool.GBKToUnicode(VersionNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ColumnID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SourceTableID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SourceColumnID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
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

		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VersionNo = FValue.trim();
			}
			else
				VersionNo = null;
		}
		if (FCode.equalsIgnoreCase("ColumnID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColumnID = FValue.trim();
			}
			else
				ColumnID = null;
		}
		if (FCode.equalsIgnoreCase("SourceTableID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceTableID = FValue.trim();
			}
			else
				SourceTableID = null;
		}
		if (FCode.equalsIgnoreCase("SourceColumnID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceColumnID = FValue.trim();
			}
			else
				SourceColumnID = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIAssociatedDirectItemDefSchema other = (FIAssociatedDirectItemDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& ColumnID.equals(other.getColumnID())
			&& SourceTableID.equals(other.getSourceTableID())
			&& SourceColumnID.equals(other.getSourceColumnID())
			&& ReMark.equals(other.getReMark());
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
		if( strFieldName.equals("VersionNo") ) {
			return 0;
		}
		if( strFieldName.equals("ColumnID") ) {
			return 1;
		}
		if( strFieldName.equals("SourceTableID") ) {
			return 2;
		}
		if( strFieldName.equals("SourceColumnID") ) {
			return 3;
		}
		if( strFieldName.equals("ReMark") ) {
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "ColumnID";
				break;
			case 2:
				strFieldName = "SourceTableID";
				break;
			case 3:
				strFieldName = "SourceColumnID";
				break;
			case 4:
				strFieldName = "ReMark";
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
		if( strFieldName.equals("VersionNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColumnID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourceTableID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourceColumnID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
