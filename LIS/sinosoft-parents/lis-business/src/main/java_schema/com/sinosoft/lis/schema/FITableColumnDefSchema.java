/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FITableColumnDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FITableColumnDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FITableColumnDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FITableColumnDefSchema.class);

	// @Field
	/** 信息表标识 */
	private String TableID;
	/** 字段内部标识 */
	private String ColumnID;
	/** 字段外部标识 */
	private String ColumnMark;
	/** 字段含义说明 */
	private String ColumnMean;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FITableColumnDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TableID";
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
                FITableColumnDefSchema cloned = (FITableColumnDefSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTableID()
	{
		return TableID;
	}
	public void setTableID(String aTableID)
	{
		TableID = aTableID;
	}
	public String getColumnID()
	{
		return ColumnID;
	}
	public void setColumnID(String aColumnID)
	{
		ColumnID = aColumnID;
	}
	public String getColumnMark()
	{
		return ColumnMark;
	}
	public void setColumnMark(String aColumnMark)
	{
		ColumnMark = aColumnMark;
	}
	public String getColumnMean()
	{
		return ColumnMean;
	}
	public void setColumnMean(String aColumnMean)
	{
		ColumnMean = aColumnMean;
	}

	/**
	* 使用另外一个 FITableColumnDefSchema 对象给 Schema 赋值
	* @param: aFITableColumnDefSchema FITableColumnDefSchema
	**/
	public void setSchema(FITableColumnDefSchema aFITableColumnDefSchema)
	{
		this.TableID = aFITableColumnDefSchema.getTableID();
		this.ColumnID = aFITableColumnDefSchema.getColumnID();
		this.ColumnMark = aFITableColumnDefSchema.getColumnMark();
		this.ColumnMean = aFITableColumnDefSchema.getColumnMean();
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
			if( rs.getString("TableID") == null )
				this.TableID = null;
			else
				this.TableID = rs.getString("TableID").trim();

			if( rs.getString("ColumnID") == null )
				this.ColumnID = null;
			else
				this.ColumnID = rs.getString("ColumnID").trim();

			if( rs.getString("ColumnMark") == null )
				this.ColumnMark = null;
			else
				this.ColumnMark = rs.getString("ColumnMark").trim();

			if( rs.getString("ColumnMean") == null )
				this.ColumnMean = null;
			else
				this.ColumnMean = rs.getString("ColumnMean").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FITableColumnDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FITableColumnDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FITableColumnDefSchema getSchema()
	{
		FITableColumnDefSchema aFITableColumnDefSchema = new FITableColumnDefSchema();
		aFITableColumnDefSchema.setSchema(this);
		return aFITableColumnDefSchema;
	}

	public FITableColumnDefDB getDB()
	{
		FITableColumnDefDB aDBOper = new FITableColumnDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFITableColumnDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(TableID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ColumnID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ColumnMark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ColumnMean));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFITableColumnDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TableID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ColumnID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ColumnMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ColumnMean = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FITableColumnDefSchema";
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
		if (FCode.equalsIgnoreCase("TableID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableID));
		}
		if (FCode.equalsIgnoreCase("ColumnID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColumnID));
		}
		if (FCode.equalsIgnoreCase("ColumnMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColumnMark));
		}
		if (FCode.equalsIgnoreCase("ColumnMean"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColumnMean));
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
				strFieldValue = StrTool.GBKToUnicode(TableID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ColumnID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ColumnMark);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ColumnMean);
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

		if (FCode.equalsIgnoreCase("TableID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableID = FValue.trim();
			}
			else
				TableID = null;
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
		if (FCode.equalsIgnoreCase("ColumnMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColumnMark = FValue.trim();
			}
			else
				ColumnMark = null;
		}
		if (FCode.equalsIgnoreCase("ColumnMean"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColumnMean = FValue.trim();
			}
			else
				ColumnMean = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FITableColumnDefSchema other = (FITableColumnDefSchema)otherObject;
		return
			TableID.equals(other.getTableID())
			&& ColumnID.equals(other.getColumnID())
			&& ColumnMark.equals(other.getColumnMark())
			&& ColumnMean.equals(other.getColumnMean());
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
		if( strFieldName.equals("TableID") ) {
			return 0;
		}
		if( strFieldName.equals("ColumnID") ) {
			return 1;
		}
		if( strFieldName.equals("ColumnMark") ) {
			return 2;
		}
		if( strFieldName.equals("ColumnMean") ) {
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
				strFieldName = "TableID";
				break;
			case 1:
				strFieldName = "ColumnID";
				break;
			case 2:
				strFieldName = "ColumnMark";
				break;
			case 3:
				strFieldName = "ColumnMean";
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
		if( strFieldName.equals("TableID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColumnID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColumnMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColumnMean") ) {
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
