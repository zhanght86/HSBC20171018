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
import com.sinosoft.lis.db.LMClaimCtrlPeriodDB;

/*
 * <p>ClassName: LMClaimCtrlPeriodSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMClaimCtrlPeriodSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMClaimCtrlPeriodSchema.class);

	// @Field
	/** 理赔控制编号 */
	private String ClaimCtrlCode;
	/** 起始期间 */
	private int ClmPeriodStart;
	/** 起始期间单位 */
	private String ClmPeriodStartFlag;
	/** 期间间隔 */
	private int ClmPeriodInterval;
	/** 期间间隔单位 */
	private String ClmPeriodFlag;
	/** 赔付金额计算sql */
	private String CalCode;
	/** 赔付金额类型 */
	private String CalResultType;
	/** 赔付金额默认值 */
	private double DefaultValue;
	/** 赔付金额计算方式 */
	private String CalCtrlFlag;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMClaimCtrlPeriodSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClaimCtrlCode";

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
		LMClaimCtrlPeriodSchema cloned = (LMClaimCtrlPeriodSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClaimCtrlCode()
	{
		return ClaimCtrlCode;
	}
	public void setClaimCtrlCode(String aClaimCtrlCode)
	{
		ClaimCtrlCode = aClaimCtrlCode;
	}
	public int getClmPeriodStart()
	{
		return ClmPeriodStart;
	}
	public void setClmPeriodStart(int aClmPeriodStart)
	{
		ClmPeriodStart = aClmPeriodStart;
	}
	public void setClmPeriodStart(String aClmPeriodStart)
	{
		if (aClmPeriodStart != null && !aClmPeriodStart.equals(""))
		{
			Integer tInteger = new Integer(aClmPeriodStart);
			int i = tInteger.intValue();
			ClmPeriodStart = i;
		}
	}

	public String getClmPeriodStartFlag()
	{
		return ClmPeriodStartFlag;
	}
	public void setClmPeriodStartFlag(String aClmPeriodStartFlag)
	{
		ClmPeriodStartFlag = aClmPeriodStartFlag;
	}
	public int getClmPeriodInterval()
	{
		return ClmPeriodInterval;
	}
	public void setClmPeriodInterval(int aClmPeriodInterval)
	{
		ClmPeriodInterval = aClmPeriodInterval;
	}
	public void setClmPeriodInterval(String aClmPeriodInterval)
	{
		if (aClmPeriodInterval != null && !aClmPeriodInterval.equals(""))
		{
			Integer tInteger = new Integer(aClmPeriodInterval);
			int i = tInteger.intValue();
			ClmPeriodInterval = i;
		}
	}

	public String getClmPeriodFlag()
	{
		return ClmPeriodFlag;
	}
	public void setClmPeriodFlag(String aClmPeriodFlag)
	{
		ClmPeriodFlag = aClmPeriodFlag;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 1.金额<p>
	* 2.比例
	*/
	public String getCalResultType()
	{
		return CalResultType;
	}
	public void setCalResultType(String aCalResultType)
	{
		CalResultType = aCalResultType;
	}
	public double getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(double aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		if (aDefaultValue != null && !aDefaultValue.equals(""))
		{
			Double tDouble = new Double(aDefaultValue);
			double d = tDouble.doubleValue();
			DefaultValue = d;
		}
	}

	public String getCalCtrlFlag()
	{
		return CalCtrlFlag;
	}
	public void setCalCtrlFlag(String aCalCtrlFlag)
	{
		CalCtrlFlag = aCalCtrlFlag;
	}

	/**
	* 使用另外一个 LMClaimCtrlPeriodSchema 对象给 Schema 赋值
	* @param: aLMClaimCtrlPeriodSchema LMClaimCtrlPeriodSchema
	**/
	public void setSchema(LMClaimCtrlPeriodSchema aLMClaimCtrlPeriodSchema)
	{
		this.ClaimCtrlCode = aLMClaimCtrlPeriodSchema.getClaimCtrlCode();
		this.ClmPeriodStart = aLMClaimCtrlPeriodSchema.getClmPeriodStart();
		this.ClmPeriodStartFlag = aLMClaimCtrlPeriodSchema.getClmPeriodStartFlag();
		this.ClmPeriodInterval = aLMClaimCtrlPeriodSchema.getClmPeriodInterval();
		this.ClmPeriodFlag = aLMClaimCtrlPeriodSchema.getClmPeriodFlag();
		this.CalCode = aLMClaimCtrlPeriodSchema.getCalCode();
		this.CalResultType = aLMClaimCtrlPeriodSchema.getCalResultType();
		this.DefaultValue = aLMClaimCtrlPeriodSchema.getDefaultValue();
		this.CalCtrlFlag = aLMClaimCtrlPeriodSchema.getCalCtrlFlag();
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
			if( rs.getString("ClaimCtrlCode") == null )
				this.ClaimCtrlCode = null;
			else
				this.ClaimCtrlCode = rs.getString("ClaimCtrlCode").trim();

			this.ClmPeriodStart = rs.getInt("ClmPeriodStart");
			if( rs.getString("ClmPeriodStartFlag") == null )
				this.ClmPeriodStartFlag = null;
			else
				this.ClmPeriodStartFlag = rs.getString("ClmPeriodStartFlag").trim();

			this.ClmPeriodInterval = rs.getInt("ClmPeriodInterval");
			if( rs.getString("ClmPeriodFlag") == null )
				this.ClmPeriodFlag = null;
			else
				this.ClmPeriodFlag = rs.getString("ClmPeriodFlag").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalResultType") == null )
				this.CalResultType = null;
			else
				this.CalResultType = rs.getString("CalResultType").trim();

			this.DefaultValue = rs.getDouble("DefaultValue");
			if( rs.getString("CalCtrlFlag") == null )
				this.CalCtrlFlag = null;
			else
				this.CalCtrlFlag = rs.getString("CalCtrlFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMClaimCtrlPeriod表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimCtrlPeriodSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMClaimCtrlPeriodSchema getSchema()
	{
		LMClaimCtrlPeriodSchema aLMClaimCtrlPeriodSchema = new LMClaimCtrlPeriodSchema();
		aLMClaimCtrlPeriodSchema.setSchema(this);
		return aLMClaimCtrlPeriodSchema;
	}

	public LMClaimCtrlPeriodDB getDB()
	{
		LMClaimCtrlPeriodDB aDBOper = new LMClaimCtrlPeriodDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimCtrlPeriod描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClaimCtrlCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmPeriodStart));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodStartFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmPeriodInterval));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalResultType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCtrlFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimCtrlPeriod>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClaimCtrlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClmPeriodStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ClmPeriodStartFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmPeriodInterval= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ClmPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalResultType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DefaultValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CalCtrlFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimCtrlPeriodSchema";
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
		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlCode));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodStart));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodStartFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodStartFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodInterval"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodInterval));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalResultType));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCtrlFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlCode);
				break;
			case 1:
				strFieldValue = String.valueOf(ClmPeriodStart);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodStartFlag);
				break;
			case 3:
				strFieldValue = String.valueOf(ClmPeriodInterval);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalResultType);
				break;
			case 7:
				strFieldValue = String.valueOf(DefaultValue);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCtrlFlag);
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

		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlCode = FValue.trim();
			}
			else
				ClaimCtrlCode = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmPeriodStart = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmPeriodStartFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodStartFlag = FValue.trim();
			}
			else
				ClmPeriodStartFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodInterval"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmPeriodInterval = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodFlag = FValue.trim();
			}
			else
				ClmPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalResultType = FValue.trim();
			}
			else
				CalResultType = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCtrlFlag = FValue.trim();
			}
			else
				CalCtrlFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMClaimCtrlPeriodSchema other = (LMClaimCtrlPeriodSchema)otherObject;
		return
			ClaimCtrlCode.equals(other.getClaimCtrlCode())
			&& ClmPeriodStart == other.getClmPeriodStart()
			&& ClmPeriodStartFlag.equals(other.getClmPeriodStartFlag())
			&& ClmPeriodInterval == other.getClmPeriodInterval()
			&& ClmPeriodFlag.equals(other.getClmPeriodFlag())
			&& CalCode.equals(other.getCalCode())
			&& CalResultType.equals(other.getCalResultType())
			&& DefaultValue == other.getDefaultValue()
			&& CalCtrlFlag.equals(other.getCalCtrlFlag());
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return 0;
		}
		if( strFieldName.equals("ClmPeriodStart") ) {
			return 1;
		}
		if( strFieldName.equals("ClmPeriodStartFlag") ) {
			return 2;
		}
		if( strFieldName.equals("ClmPeriodInterval") ) {
			return 3;
		}
		if( strFieldName.equals("ClmPeriodFlag") ) {
			return 4;
		}
		if( strFieldName.equals("CalCode") ) {
			return 5;
		}
		if( strFieldName.equals("CalResultType") ) {
			return 6;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 7;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
			return 8;
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
				strFieldName = "ClaimCtrlCode";
				break;
			case 1:
				strFieldName = "ClmPeriodStart";
				break;
			case 2:
				strFieldName = "ClmPeriodStartFlag";
				break;
			case 3:
				strFieldName = "ClmPeriodInterval";
				break;
			case 4:
				strFieldName = "ClmPeriodFlag";
				break;
			case 5:
				strFieldName = "CalCode";
				break;
			case 6:
				strFieldName = "CalResultType";
				break;
			case 7:
				strFieldName = "DefaultValue";
				break;
			case 8:
				strFieldName = "CalCtrlFlag";
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmPeriodStartFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodInterval") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalResultType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
