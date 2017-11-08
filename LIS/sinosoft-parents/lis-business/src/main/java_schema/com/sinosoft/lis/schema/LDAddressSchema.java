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
import com.sinosoft.lis.db.LDAddressDB;

/*
 * <p>ClassName: LDAddressSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDAddressSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDAddressSchema.class);

	// @Field
	/** 地域类型 */
	private String PlaceType;
	/** 地域代码 */
	private String PlaceCode;
	/** 地域名称 */
	private String PlaceName;
	/** 上级地域代码 */
	private String UpPlaceName;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDAddressSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "PlaceType";
		pk[1] = "PlaceCode";

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
		LDAddressSchema cloned = (LDAddressSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 01-省<p>
	* 02-市<p>
	* 03-区/县
	*/
	public String getPlaceType()
	{
		return PlaceType;
	}
	public void setPlaceType(String aPlaceType)
	{
		PlaceType = aPlaceType;
	}
	public String getPlaceCode()
	{
		return PlaceCode;
	}
	public void setPlaceCode(String aPlaceCode)
	{
		PlaceCode = aPlaceCode;
	}
	public String getPlaceName()
	{
		return PlaceName;
	}
	public void setPlaceName(String aPlaceName)
	{
		PlaceName = aPlaceName;
	}
	/**
	* 省级无上级地域代码<p>
	* 市的上级代码为所在省代码<p>
	* 县/区的上级代码为所在市代码
	*/
	public String getUpPlaceName()
	{
		return UpPlaceName;
	}
	public void setUpPlaceName(String aUpPlaceName)
	{
		UpPlaceName = aUpPlaceName;
	}

	/**
	* 使用另外一个 LDAddressSchema 对象给 Schema 赋值
	* @param: aLDAddressSchema LDAddressSchema
	**/
	public void setSchema(LDAddressSchema aLDAddressSchema)
	{
		this.PlaceType = aLDAddressSchema.getPlaceType();
		this.PlaceCode = aLDAddressSchema.getPlaceCode();
		this.PlaceName = aLDAddressSchema.getPlaceName();
		this.UpPlaceName = aLDAddressSchema.getUpPlaceName();
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
			if( rs.getString("PlaceType") == null )
				this.PlaceType = null;
			else
				this.PlaceType = rs.getString("PlaceType").trim();

			if( rs.getString("PlaceCode") == null )
				this.PlaceCode = null;
			else
				this.PlaceCode = rs.getString("PlaceCode").trim();

			if( rs.getString("PlaceName") == null )
				this.PlaceName = null;
			else
				this.PlaceName = rs.getString("PlaceName").trim();

			if( rs.getString("UpPlaceName") == null )
				this.UpPlaceName = null;
			else
				this.UpPlaceName = rs.getString("UpPlaceName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDAddress表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAddressSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDAddressSchema getSchema()
	{
		LDAddressSchema aLDAddressSchema = new LDAddressSchema();
		aLDAddressSchema.setSchema(this);
		return aLDAddressSchema;
	}

	public LDAddressDB getDB()
	{
		LDAddressDB aDBOper = new LDAddressDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAddress描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PlaceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlaceCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlaceName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpPlaceName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDAddress>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PlaceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PlaceCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlaceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UpPlaceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDAddressSchema";
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
		if (FCode.equalsIgnoreCase("PlaceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlaceType));
		}
		if (FCode.equalsIgnoreCase("PlaceCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlaceCode));
		}
		if (FCode.equalsIgnoreCase("PlaceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlaceName));
		}
		if (FCode.equalsIgnoreCase("UpPlaceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpPlaceName));
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
				strFieldValue = StrTool.GBKToUnicode(PlaceType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PlaceCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlaceName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UpPlaceName);
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

		if (FCode.equalsIgnoreCase("PlaceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlaceType = FValue.trim();
			}
			else
				PlaceType = null;
		}
		if (FCode.equalsIgnoreCase("PlaceCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlaceCode = FValue.trim();
			}
			else
				PlaceCode = null;
		}
		if (FCode.equalsIgnoreCase("PlaceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlaceName = FValue.trim();
			}
			else
				PlaceName = null;
		}
		if (FCode.equalsIgnoreCase("UpPlaceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpPlaceName = FValue.trim();
			}
			else
				UpPlaceName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDAddressSchema other = (LDAddressSchema)otherObject;
		return
			PlaceType.equals(other.getPlaceType())
			&& PlaceCode.equals(other.getPlaceCode())
			&& PlaceName.equals(other.getPlaceName())
			&& UpPlaceName.equals(other.getUpPlaceName());
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
		if( strFieldName.equals("PlaceType") ) {
			return 0;
		}
		if( strFieldName.equals("PlaceCode") ) {
			return 1;
		}
		if( strFieldName.equals("PlaceName") ) {
			return 2;
		}
		if( strFieldName.equals("UpPlaceName") ) {
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
				strFieldName = "PlaceType";
				break;
			case 1:
				strFieldName = "PlaceCode";
				break;
			case 2:
				strFieldName = "PlaceName";
				break;
			case 3:
				strFieldName = "UpPlaceName";
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
		if( strFieldName.equals("PlaceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlaceCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlaceName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpPlaceName") ) {
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
