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
import com.sinosoft.lis.db.LMCalFactorDB;

/*
 * <p>ClassName: LMCalFactorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMCalFactorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMCalFactorSchema.class);

	// @Field
	/** 算法编码 */
	private String CalCode;
	/** 要素编码 */
	private String FactorCode;
	/** 要素名称 */
	private String FactorName;
	/** 要素类型 */
	private String FactorType;
	/** 要素优先级 */
	private String FactorGrade;
	/** 要素算法编码 */
	private String FactorCalCode;
	/** 默认值 */
	private String FactorDefault;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCalFactorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CalCode";
		pk[1] = "FactorCode";

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
		LMCalFactorSchema cloned = (LMCalFactorSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getFactorCode()
	{
		return FactorCode;
	}
	public void setFactorCode(String aFactorCode)
	{
		FactorCode = aFactorCode;
	}
	public String getFactorName()
	{
		return FactorName;
	}
	public void setFactorName(String aFactorName)
	{
		FactorName = aFactorName;
	}
	/**
	* 1--基本要素、2--扩展要素，或别的分类。
	*/
	public String getFactorType()
	{
		return FactorType;
	}
	public void setFactorType(String aFactorType)
	{
		FactorType = aFactorType;
	}
	/**
	* 1--最高级 2--高级 ......类推
	*/
	public String getFactorGrade()
	{
		return FactorGrade;
	}
	public void setFactorGrade(String aFactorGrade)
	{
		FactorGrade = aFactorGrade;
	}
	public String getFactorCalCode()
	{
		return FactorCalCode;
	}
	public void setFactorCalCode(String aFactorCalCode)
	{
		FactorCalCode = aFactorCalCode;
	}
	public String getFactorDefault()
	{
		return FactorDefault;
	}
	public void setFactorDefault(String aFactorDefault)
	{
		FactorDefault = aFactorDefault;
	}

	/**
	* 使用另外一个 LMCalFactorSchema 对象给 Schema 赋值
	* @param: aLMCalFactorSchema LMCalFactorSchema
	**/
	public void setSchema(LMCalFactorSchema aLMCalFactorSchema)
	{
		this.CalCode = aLMCalFactorSchema.getCalCode();
		this.FactorCode = aLMCalFactorSchema.getFactorCode();
		this.FactorName = aLMCalFactorSchema.getFactorName();
		this.FactorType = aLMCalFactorSchema.getFactorType();
		this.FactorGrade = aLMCalFactorSchema.getFactorGrade();
		this.FactorCalCode = aLMCalFactorSchema.getFactorCalCode();
		this.FactorDefault = aLMCalFactorSchema.getFactorDefault();
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
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("FactorType") == null )
				this.FactorType = null;
			else
				this.FactorType = rs.getString("FactorType").trim();

			if( rs.getString("FactorGrade") == null )
				this.FactorGrade = null;
			else
				this.FactorGrade = rs.getString("FactorGrade").trim();

			if( rs.getString("FactorCalCode") == null )
				this.FactorCalCode = null;
			else
				this.FactorCalCode = rs.getString("FactorCalCode").trim();

			if( rs.getString("FactorDefault") == null )
				this.FactorDefault = null;
			else
				this.FactorDefault = rs.getString("FactorDefault").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMCalFactor表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalFactorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMCalFactorSchema getSchema()
	{
		LMCalFactorSchema aLMCalFactorSchema = new LMCalFactorSchema();
		aLMCalFactorSchema.setSchema(this);
		return aLMCalFactorSchema;
	}

	public LMCalFactorDB getDB()
	{
		LMCalFactorDB aDBOper = new LMCalFactorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalFactor描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorDefault));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalFactor>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FactorGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FactorCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FactorDefault = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCalFactorSchema";
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
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equalsIgnoreCase("FactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorType));
		}
		if (FCode.equalsIgnoreCase("FactorGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorGrade));
		}
		if (FCode.equalsIgnoreCase("FactorCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCalCode));
		}
		if (FCode.equalsIgnoreCase("FactorDefault"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorDefault));
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
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactorType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FactorGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactorCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FactorDefault);
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

		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorCode = FValue.trim();
			}
			else
				FactorCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorName = FValue.trim();
			}
			else
				FactorName = null;
		}
		if (FCode.equalsIgnoreCase("FactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorType = FValue.trim();
			}
			else
				FactorType = null;
		}
		if (FCode.equalsIgnoreCase("FactorGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorGrade = FValue.trim();
			}
			else
				FactorGrade = null;
		}
		if (FCode.equalsIgnoreCase("FactorCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorCalCode = FValue.trim();
			}
			else
				FactorCalCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorDefault"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorDefault = FValue.trim();
			}
			else
				FactorDefault = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCalFactorSchema other = (LMCalFactorSchema)otherObject;
		return
			CalCode.equals(other.getCalCode())
			&& FactorCode.equals(other.getFactorCode())
			&& FactorName.equals(other.getFactorName())
			&& FactorType.equals(other.getFactorType())
			&& FactorGrade.equals(other.getFactorGrade())
			&& FactorCalCode.equals(other.getFactorCalCode())
			&& FactorDefault.equals(other.getFactorDefault());
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
		if( strFieldName.equals("CalCode") ) {
			return 0;
		}
		if( strFieldName.equals("FactorCode") ) {
			return 1;
		}
		if( strFieldName.equals("FactorName") ) {
			return 2;
		}
		if( strFieldName.equals("FactorType") ) {
			return 3;
		}
		if( strFieldName.equals("FactorGrade") ) {
			return 4;
		}
		if( strFieldName.equals("FactorCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("FactorDefault") ) {
			return 6;
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
				strFieldName = "CalCode";
				break;
			case 1:
				strFieldName = "FactorCode";
				break;
			case 2:
				strFieldName = "FactorName";
				break;
			case 3:
				strFieldName = "FactorType";
				break;
			case 4:
				strFieldName = "FactorGrade";
				break;
			case 5:
				strFieldName = "FactorCalCode";
				break;
			case 6:
				strFieldName = "FactorDefault";
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
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorDefault") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
