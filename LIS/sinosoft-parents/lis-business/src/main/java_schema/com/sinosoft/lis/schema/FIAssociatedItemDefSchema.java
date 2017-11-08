/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIAssociatedItemDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIAssociatedItemDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIAssociatedItemDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIAssociatedItemDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 专项编号 */
	private String AssociatedID;
	/** 专项名称 */
	private String AssociatedName;
	/** 专项表字段标识 */
	private String ColumnID;
	/** 上游数据来源表名 */
	private String SourceTableID;
	/** 上游数据来源字段 */
	private String SourceColumnID;
	/** 转换标志 */
	private String TransFlag;
	/** 转换sql */
	private String TransSQL;
	/** 转化类型处理类 */
	private String TransClass;
	/** 描述 */
	private String ReMark;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIAssociatedItemDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "AssociatedID";

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
                FIAssociatedItemDefSchema cloned = (FIAssociatedItemDefSchema)super.clone();
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
	public String getAssociatedID()
	{
		return AssociatedID;
	}
	public void setAssociatedID(String aAssociatedID)
	{
		AssociatedID = aAssociatedID;
	}
	public String getAssociatedName()
	{
		return AssociatedName;
	}
	public void setAssociatedName(String aAssociatedName)
	{
		AssociatedName = aAssociatedName;
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
	public String getTransFlag()
	{
		return TransFlag;
	}
	public void setTransFlag(String aTransFlag)
	{
		TransFlag = aTransFlag;
	}
	public String getTransSQL()
	{
		return TransSQL;
	}
	public void setTransSQL(String aTransSQL)
	{
		TransSQL = aTransSQL;
	}
	public String getTransClass()
	{
		return TransClass;
	}
	public void setTransClass(String aTransClass)
	{
		TransClass = aTransClass;
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
	* 使用另外一个 FIAssociatedItemDefSchema 对象给 Schema 赋值
	* @param: aFIAssociatedItemDefSchema FIAssociatedItemDefSchema
	**/
	public void setSchema(FIAssociatedItemDefSchema aFIAssociatedItemDefSchema)
	{
		this.VersionNo = aFIAssociatedItemDefSchema.getVersionNo();
		this.AssociatedID = aFIAssociatedItemDefSchema.getAssociatedID();
		this.AssociatedName = aFIAssociatedItemDefSchema.getAssociatedName();
		this.ColumnID = aFIAssociatedItemDefSchema.getColumnID();
		this.SourceTableID = aFIAssociatedItemDefSchema.getSourceTableID();
		this.SourceColumnID = aFIAssociatedItemDefSchema.getSourceColumnID();
		this.TransFlag = aFIAssociatedItemDefSchema.getTransFlag();
		this.TransSQL = aFIAssociatedItemDefSchema.getTransSQL();
		this.TransClass = aFIAssociatedItemDefSchema.getTransClass();
		this.ReMark = aFIAssociatedItemDefSchema.getReMark();
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

			if( rs.getString("AssociatedID") == null )
				this.AssociatedID = null;
			else
				this.AssociatedID = rs.getString("AssociatedID").trim();

			if( rs.getString("AssociatedName") == null )
				this.AssociatedName = null;
			else
				this.AssociatedName = rs.getString("AssociatedName").trim();

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

			if( rs.getString("TransFlag") == null )
				this.TransFlag = null;
			else
				this.TransFlag = rs.getString("TransFlag").trim();

			if( rs.getString("TransSQL") == null )
				this.TransSQL = null;
			else
				this.TransSQL = rs.getString("TransSQL").trim();

			if( rs.getString("TransClass") == null )
				this.TransClass = null;
			else
				this.TransClass = rs.getString("TransClass").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIAssociatedItemDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedItemDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIAssociatedItemDefSchema getSchema()
	{
		FIAssociatedItemDefSchema aFIAssociatedItemDefSchema = new FIAssociatedItemDefSchema();
		aFIAssociatedItemDefSchema.setSchema(this);
		return aFIAssociatedItemDefSchema;
	}

	public FIAssociatedItemDefDB getDB()
	{
		FIAssociatedItemDefDB aDBOper = new FIAssociatedItemDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIAssociatedItemDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AssociatedID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AssociatedName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ColumnID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SourceTableID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SourceColumnID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TransFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TransSQL)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TransClass)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIAssociatedItemDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AssociatedID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AssociatedName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ColumnID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SourceTableID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SourceColumnID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TransFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			TransSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TransClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedItemDefSchema";
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
		if (FCode.equalsIgnoreCase("AssociatedID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssociatedID));
		}
		if (FCode.equalsIgnoreCase("AssociatedName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssociatedName));
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
		if (FCode.equalsIgnoreCase("TransFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransFlag));
		}
		if (FCode.equalsIgnoreCase("TransSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSQL));
		}
		if (FCode.equalsIgnoreCase("TransClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransClass));
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
				strFieldValue = StrTool.GBKToUnicode(AssociatedID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AssociatedName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ColumnID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SourceTableID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SourceColumnID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TransFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(TransSQL);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TransClass);
				break;
			case 9:
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
		if (FCode.equalsIgnoreCase("AssociatedID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssociatedID = FValue.trim();
			}
			else
				AssociatedID = null;
		}
		if (FCode.equalsIgnoreCase("AssociatedName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssociatedName = FValue.trim();
			}
			else
				AssociatedName = null;
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
		if (FCode.equalsIgnoreCase("TransFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransFlag = FValue.trim();
			}
			else
				TransFlag = null;
		}
		if (FCode.equalsIgnoreCase("TransSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransSQL = FValue.trim();
			}
			else
				TransSQL = null;
		}
		if (FCode.equalsIgnoreCase("TransClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransClass = FValue.trim();
			}
			else
				TransClass = null;
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
		FIAssociatedItemDefSchema other = (FIAssociatedItemDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& AssociatedID.equals(other.getAssociatedID())
			&& AssociatedName.equals(other.getAssociatedName())
			&& ColumnID.equals(other.getColumnID())
			&& SourceTableID.equals(other.getSourceTableID())
			&& SourceColumnID.equals(other.getSourceColumnID())
			&& TransFlag.equals(other.getTransFlag())
			&& TransSQL.equals(other.getTransSQL())
			&& TransClass.equals(other.getTransClass())
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
		if( strFieldName.equals("AssociatedID") ) {
			return 1;
		}
		if( strFieldName.equals("AssociatedName") ) {
			return 2;
		}
		if( strFieldName.equals("ColumnID") ) {
			return 3;
		}
		if( strFieldName.equals("SourceTableID") ) {
			return 4;
		}
		if( strFieldName.equals("SourceColumnID") ) {
			return 5;
		}
		if( strFieldName.equals("TransFlag") ) {
			return 6;
		}
		if( strFieldName.equals("TransSQL") ) {
			return 7;
		}
		if( strFieldName.equals("TransClass") ) {
			return 8;
		}
		if( strFieldName.equals("ReMark") ) {
			return 9;
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
				strFieldName = "AssociatedID";
				break;
			case 2:
				strFieldName = "AssociatedName";
				break;
			case 3:
				strFieldName = "ColumnID";
				break;
			case 4:
				strFieldName = "SourceTableID";
				break;
			case 5:
				strFieldName = "SourceColumnID";
				break;
			case 6:
				strFieldName = "TransFlag";
				break;
			case 7:
				strFieldName = "TransSQL";
				break;
			case 8:
				strFieldName = "TransClass";
				break;
			case 9:
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
		if( strFieldName.equals("AssociatedID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssociatedName") ) {
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
		if( strFieldName.equals("TransFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransClass") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
