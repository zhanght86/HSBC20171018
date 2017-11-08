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
import com.sinosoft.lis.db.lwprioritycolorDB;

/*
 * <p>ClassName: lwprioritycolorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 优先级别颜色定义
 */
public class lwprioritycolorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(lwprioritycolorSchema.class);
	// @Field
	/** 颜色id */
	private String Colorid;
	/** 颜色名称 */
	private String Color;

	public static final int FIELDNUM = 2;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public lwprioritycolorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Colorid";

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
		lwprioritycolorSchema cloned = (lwprioritycolorSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getColorid()
	{
		return Colorid;
	}
	public void setColorid(String aColorid)
	{
		if(aColorid!=null && aColorid.length()>10)
			throw new IllegalArgumentException("颜色idColorid值"+aColorid+"的长度"+aColorid.length()+"大于最大值10");
		Colorid = aColorid;
	}
	public String getColor()
	{
		return Color;
	}
	public void setColor(String aColor)
	{
		if(aColor!=null && aColor.length()>50)
			throw new IllegalArgumentException("颜色名称Color值"+aColor+"的长度"+aColor.length()+"大于最大值50");
		Color = aColor;
	}

	/**
	* 使用另外一个 lwprioritycolorSchema 对象给 Schema 赋值
	* @param: alwprioritycolorSchema lwprioritycolorSchema
	**/
	public void setSchema(lwprioritycolorSchema alwprioritycolorSchema)
	{
		this.Colorid = alwprioritycolorSchema.getColorid();
		this.Color = alwprioritycolorSchema.getColor();
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
			if( rs.getString("Colorid") == null )
				this.Colorid = null;
			else
				this.Colorid = rs.getString("Colorid").trim();

			if( rs.getString("Color") == null )
				this.Color = null;
			else
				this.Color = rs.getString("Color").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的lwprioritycolor表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "lwprioritycolorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public lwprioritycolorSchema getSchema()
	{
		lwprioritycolorSchema alwprioritycolorSchema = new lwprioritycolorSchema();
		alwprioritycolorSchema.setSchema(this);
		return alwprioritycolorSchema;
	}

	public lwprioritycolorDB getDB()
	{
		lwprioritycolorDB aDBOper = new lwprioritycolorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#Prplwprioritycolor描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Colorid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Color));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#Prplwprioritycolor>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Colorid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Color = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "lwprioritycolorSchema";
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
		if (FCode.equalsIgnoreCase("Colorid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Colorid));
		}
		if (FCode.equalsIgnoreCase("Color"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Color));
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
				strFieldValue = StrTool.GBKToUnicode(Colorid);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Color);
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

		if (FCode.equalsIgnoreCase("Colorid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Colorid = FValue.trim();
			}
			else
				Colorid = null;
		}
		if (FCode.equalsIgnoreCase("Color"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Color = FValue.trim();
			}
			else
				Color = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		lwprioritycolorSchema other = (lwprioritycolorSchema)otherObject;
		return
			Colorid.equals(other.getColorid())
			&& Color.equals(other.getColor());
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
		if( strFieldName.equals("Colorid") ) {
			return 0;
		}
		if( strFieldName.equals("Color") ) {
			return 1;
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
				strFieldName = "Colorid";
				break;
			case 1:
				strFieldName = "Color";
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
		if( strFieldName.equals("Colorid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Color") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
