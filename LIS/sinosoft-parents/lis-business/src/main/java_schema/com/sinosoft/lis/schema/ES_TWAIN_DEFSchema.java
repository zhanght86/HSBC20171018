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
import com.sinosoft.lis.db.ES_TWAIN_DEFDB;

/*
 * <p>ClassName: ES_TWAIN_DEFSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug1
 */
public class ES_TWAIN_DEFSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_TWAIN_DEFSchema.class);
	// @Field
	/** Defsettingname */
	private String DefSettingName;
	/** Dpi */
	private int Dpi;
	/** Bitdepth */
	private String BitDepth;
	/** Pagemode */
	private String PageMode;
	/** Duplex */
	private String Duplex;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_TWAIN_DEFSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "DefSettingName";

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
		ES_TWAIN_DEFSchema cloned = (ES_TWAIN_DEFSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDefSettingName()
	{
		return DefSettingName;
	}
	public void setDefSettingName(String aDefSettingName)
	{
		DefSettingName = aDefSettingName;
	}
	public int getDpi()
	{
		return Dpi;
	}
	public void setDpi(int aDpi)
	{
		Dpi = aDpi;
	}
	public void setDpi(String aDpi)
	{
		if (aDpi != null && !aDpi.equals(""))
		{
			Integer tInteger = new Integer(aDpi);
			int i = tInteger.intValue();
			Dpi = i;
		}
	}

	/**
	* 1:黑色 8:灰度 24:彩色
	*/
	public String getBitDepth()
	{
		return BitDepth;
	}
	public void setBitDepth(String aBitDepth)
	{
		BitDepth = aBitDepth;
	}
	/**
	* 0:A4 1:A5
	*/
	public String getPageMode()
	{
		return PageMode;
	}
	public void setPageMode(String aPageMode)
	{
		PageMode = aPageMode;
	}
	/**
	* 0:单面 1:双面
	*/
	public String getDuplex()
	{
		return Duplex;
	}
	public void setDuplex(String aDuplex)
	{
		Duplex = aDuplex;
	}

	/**
	* 使用另外一个 ES_TWAIN_DEFSchema 对象给 Schema 赋值
	* @param: aES_TWAIN_DEFSchema ES_TWAIN_DEFSchema
	**/
	public void setSchema(ES_TWAIN_DEFSchema aES_TWAIN_DEFSchema)
	{
		this.DefSettingName = aES_TWAIN_DEFSchema.getDefSettingName();
		this.Dpi = aES_TWAIN_DEFSchema.getDpi();
		this.BitDepth = aES_TWAIN_DEFSchema.getBitDepth();
		this.PageMode = aES_TWAIN_DEFSchema.getPageMode();
		this.Duplex = aES_TWAIN_DEFSchema.getDuplex();
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
			if( rs.getString("DefSettingName") == null )
				this.DefSettingName = null;
			else
				this.DefSettingName = rs.getString("DefSettingName").trim();

			this.Dpi = rs.getInt("Dpi");
			if( rs.getString("BitDepth") == null )
				this.BitDepth = null;
			else
				this.BitDepth = rs.getString("BitDepth").trim();

			if( rs.getString("PageMode") == null )
				this.PageMode = null;
			else
				this.PageMode = rs.getString("PageMode").trim();

			if( rs.getString("Duplex") == null )
				this.Duplex = null;
			else
				this.Duplex = rs.getString("Duplex").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_TWAIN_DEF表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_TWAIN_DEFSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_TWAIN_DEFSchema getSchema()
	{
		ES_TWAIN_DEFSchema aES_TWAIN_DEFSchema = new ES_TWAIN_DEFSchema();
		aES_TWAIN_DEFSchema.setSchema(this);
		return aES_TWAIN_DEFSchema;
	}

	public ES_TWAIN_DEFDB getDB()
	{
		ES_TWAIN_DEFDB aDBOper = new ES_TWAIN_DEFDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_TWAIN_DEF描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DefSettingName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Dpi));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BitDepth)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Duplex));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_TWAIN_DEF>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DefSettingName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Dpi= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			BitDepth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PageMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Duplex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_TWAIN_DEFSchema";
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
		if (FCode.equalsIgnoreCase("DefSettingName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefSettingName));
		}
		if (FCode.equalsIgnoreCase("Dpi"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dpi));
		}
		if (FCode.equalsIgnoreCase("BitDepth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BitDepth));
		}
		if (FCode.equalsIgnoreCase("PageMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageMode));
		}
		if (FCode.equalsIgnoreCase("Duplex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Duplex));
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
				strFieldValue = StrTool.GBKToUnicode(DefSettingName);
				break;
			case 1:
				strFieldValue = String.valueOf(Dpi);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BitDepth);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PageMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Duplex);
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

		if (FCode.equalsIgnoreCase("DefSettingName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefSettingName = FValue.trim();
			}
			else
				DefSettingName = null;
		}
		if (FCode.equalsIgnoreCase("Dpi"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Dpi = i;
			}
		}
		if (FCode.equalsIgnoreCase("BitDepth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BitDepth = FValue.trim();
			}
			else
				BitDepth = null;
		}
		if (FCode.equalsIgnoreCase("PageMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageMode = FValue.trim();
			}
			else
				PageMode = null;
		}
		if (FCode.equalsIgnoreCase("Duplex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Duplex = FValue.trim();
			}
			else
				Duplex = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_TWAIN_DEFSchema other = (ES_TWAIN_DEFSchema)otherObject;
		return
			DefSettingName.equals(other.getDefSettingName())
			&& Dpi == other.getDpi()
			&& BitDepth.equals(other.getBitDepth())
			&& PageMode.equals(other.getPageMode())
			&& Duplex.equals(other.getDuplex());
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
		if( strFieldName.equals("DefSettingName") ) {
			return 0;
		}
		if( strFieldName.equals("Dpi") ) {
			return 1;
		}
		if( strFieldName.equals("BitDepth") ) {
			return 2;
		}
		if( strFieldName.equals("PageMode") ) {
			return 3;
		}
		if( strFieldName.equals("Duplex") ) {
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
				strFieldName = "DefSettingName";
				break;
			case 1:
				strFieldName = "Dpi";
				break;
			case 2:
				strFieldName = "BitDepth";
				break;
			case 3:
				strFieldName = "PageMode";
				break;
			case 4:
				strFieldName = "Duplex";
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
		if( strFieldName.equals("DefSettingName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Dpi") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BitDepth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Duplex") ) {
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
