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
import com.sinosoft.lis.db.LDCalendarDB;

/*
 * <p>ClassName: LDCalendarSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDCalendarSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCalendarSchema.class);

	// @Field
	/** 日期 */
	private Date CommonDate;
	/** 工作日标志 */
	private String WorkDateFlag;
	/** 星期 */
	private String WeekDay;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCalendarSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CommonDate";

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
		LDCalendarSchema cloned = (LDCalendarSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCommonDate()
	{
		if( CommonDate != null )
			return fDate.getString(CommonDate);
		else
			return null;
	}
	public void setCommonDate(Date aCommonDate)
	{
		CommonDate = aCommonDate;
	}
	public void setCommonDate(String aCommonDate)
	{
		if (aCommonDate != null && !aCommonDate.equals("") )
		{
			CommonDate = fDate.getDate( aCommonDate );
		}
		else
			CommonDate = null;
	}

	/**
	* Y为工作日,N为非工作日
	*/
	public String getWorkDateFlag()
	{
		return WorkDateFlag;
	}
	public void setWorkDateFlag(String aWorkDateFlag)
	{
		WorkDateFlag = aWorkDateFlag;
	}
	public String getWeekDay()
	{
		return WeekDay;
	}
	public void setWeekDay(String aWeekDay)
	{
		WeekDay = aWeekDay;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LDCalendarSchema 对象给 Schema 赋值
	* @param: aLDCalendarSchema LDCalendarSchema
	**/
	public void setSchema(LDCalendarSchema aLDCalendarSchema)
	{
		this.CommonDate = fDate.getDate( aLDCalendarSchema.getCommonDate());
		this.WorkDateFlag = aLDCalendarSchema.getWorkDateFlag();
		this.WeekDay = aLDCalendarSchema.getWeekDay();
		this.Remark = aLDCalendarSchema.getRemark();
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
			this.CommonDate = rs.getDate("CommonDate");
			if( rs.getString("WorkDateFlag") == null )
				this.WorkDateFlag = null;
			else
				this.WorkDateFlag = rs.getString("WorkDateFlag").trim();

			if( rs.getString("WeekDay") == null )
				this.WeekDay = null;
			else
				this.WeekDay = rs.getString("WeekDay").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCalendar表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCalendarSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCalendarSchema getSchema()
	{
		LDCalendarSchema aLDCalendarSchema = new LDCalendarSchema();
		aLDCalendarSchema.setSchema(this);
		return aLDCalendarSchema;
	}

	public LDCalendarDB getDB()
	{
		LDCalendarDB aDBOper = new LDCalendarDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCalendar描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(fDate.getString( CommonDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkDateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WeekDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCalendar>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CommonDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1,SysConst.PACKAGESPILTER));
			WorkDateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			WeekDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCalendarSchema";
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
		if (FCode.equalsIgnoreCase("CommonDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCommonDate()));
		}
		if (FCode.equalsIgnoreCase("WorkDateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkDateFlag));
		}
		if (FCode.equalsIgnoreCase("WeekDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WeekDay));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCommonDate()));
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(WorkDateFlag);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(WeekDay);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("CommonDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CommonDate = fDate.getDate( FValue );
			}
			else
				CommonDate = null;
		}
		if (FCode.equalsIgnoreCase("WorkDateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkDateFlag = FValue.trim();
			}
			else
				WorkDateFlag = null;
		}
		if (FCode.equalsIgnoreCase("WeekDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WeekDay = FValue.trim();
			}
			else
				WeekDay = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCalendarSchema other = (LDCalendarSchema)otherObject;
		return
			fDate.getString(CommonDate).equals(other.getCommonDate())
			&& WorkDateFlag.equals(other.getWorkDateFlag())
			&& WeekDay.equals(other.getWeekDay())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("CommonDate") ) {
			return 0;
		}
		if( strFieldName.equals("WorkDateFlag") ) {
			return 1;
		}
		if( strFieldName.equals("WeekDay") ) {
			return 2;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "CommonDate";
				break;
			case 1:
				strFieldName = "WorkDateFlag";
				break;
			case 2:
				strFieldName = "WeekDay";
				break;
			case 3:
				strFieldName = "Remark";
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
		if( strFieldName.equals("CommonDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("WorkDateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WeekDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
