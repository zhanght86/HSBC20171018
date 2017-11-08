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
import com.sinosoft.lis.db.LDNatiAreaDB;

/*
 * <p>ClassName: LDNatiAreaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LDNatiAreaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDNatiAreaSchema.class);
	// @Field
	/** 行政区域代码 */
	private String AreaCode;
	/** 行政区域名称 */
	private String AreaName;
	/** 所在市规模 */
	private String ComCitySize;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDNatiAreaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AreaCode";

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
		LDNatiAreaSchema cloned = (LDNatiAreaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 关联中华人民共和国行政区划代码
	*/
	public String getAreaCode()
	{
		return AreaCode;
	}
	public void setAreaCode(String aAreaCode)
	{
		if(aAreaCode!=null && aAreaCode.length()>6)
			throw new IllegalArgumentException("行政区域代码AreaCode值"+aAreaCode+"的长度"+aAreaCode.length()+"大于最大值6");
		AreaCode = aAreaCode;
	}
	public String getAreaName()
	{
		return AreaName;
	}
	public void setAreaName(String aAreaName)
	{
		if(aAreaName!=null && aAreaName.length()>100)
			throw new IllegalArgumentException("行政区域名称AreaName值"+aAreaName+"的长度"+aAreaName.length()+"大于最大值100");
		AreaName = aAreaName;
	}
	public String getComCitySize()
	{
		return ComCitySize;
	}
	public void setComCitySize(String aComCitySize)
	{
		if(aComCitySize!=null && aComCitySize.length()>1)
			throw new IllegalArgumentException("所在市规模ComCitySize值"+aComCitySize+"的长度"+aComCitySize.length()+"大于最大值1");
		ComCitySize = aComCitySize;
	}

	/**
	* 使用另外一个 LDNatiAreaSchema 对象给 Schema 赋值
	* @param: aLDNatiAreaSchema LDNatiAreaSchema
	**/
	public void setSchema(LDNatiAreaSchema aLDNatiAreaSchema)
	{
		this.AreaCode = aLDNatiAreaSchema.getAreaCode();
		this.AreaName = aLDNatiAreaSchema.getAreaName();
		this.ComCitySize = aLDNatiAreaSchema.getComCitySize();
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
			if( rs.getString("AreaCode") == null )
				this.AreaCode = null;
			else
				this.AreaCode = rs.getString("AreaCode").trim();

			if( rs.getString("AreaName") == null )
				this.AreaName = null;
			else
				this.AreaName = rs.getString("AreaName").trim();

			if( rs.getString("ComCitySize") == null )
				this.ComCitySize = null;
			else
				this.ComCitySize = rs.getString("ComCitySize").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDNatiArea表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDNatiAreaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDNatiAreaSchema getSchema()
	{
		LDNatiAreaSchema aLDNatiAreaSchema = new LDNatiAreaSchema();
		aLDNatiAreaSchema.setSchema(this);
		return aLDNatiAreaSchema;
	}

	public LDNatiAreaDB getDB()
	{
		LDNatiAreaDB aDBOper = new LDNatiAreaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDNatiArea描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AreaCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCitySize));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDNatiArea>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AreaCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AreaName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ComCitySize = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDNatiAreaSchema";
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
		if (FCode.equalsIgnoreCase("AreaCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaCode));
		}
		if (FCode.equalsIgnoreCase("AreaName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaName));
		}
		if (FCode.equalsIgnoreCase("ComCitySize"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCitySize));
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
				strFieldValue = StrTool.GBKToUnicode(AreaCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AreaName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ComCitySize);
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

		if (FCode.equalsIgnoreCase("AreaCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaCode = FValue.trim();
			}
			else
				AreaCode = null;
		}
		if (FCode.equalsIgnoreCase("AreaName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaName = FValue.trim();
			}
			else
				AreaName = null;
		}
		if (FCode.equalsIgnoreCase("ComCitySize"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCitySize = FValue.trim();
			}
			else
				ComCitySize = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDNatiAreaSchema other = (LDNatiAreaSchema)otherObject;
		return
			AreaCode.equals(other.getAreaCode())
			&& AreaName.equals(other.getAreaName())
			&& ComCitySize.equals(other.getComCitySize());
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
		if( strFieldName.equals("AreaCode") ) {
			return 0;
		}
		if( strFieldName.equals("AreaName") ) {
			return 1;
		}
		if( strFieldName.equals("ComCitySize") ) {
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
				strFieldName = "AreaCode";
				break;
			case 1:
				strFieldName = "AreaName";
				break;
			case 2:
				strFieldName = "ComCitySize";
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
		if( strFieldName.equals("AreaCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCitySize") ) {
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
