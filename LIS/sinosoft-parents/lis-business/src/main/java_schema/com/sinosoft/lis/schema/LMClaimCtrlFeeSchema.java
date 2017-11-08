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
import com.sinosoft.lis.db.LMClaimCtrlFeeDB;

/*
 * <p>ClassName: LMClaimCtrlFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMClaimCtrlFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMClaimCtrlFeeSchema.class);

	// @Field
	/** 理赔控制编号 */
	private String ClaimCtrlCode;
	/** 起始费用 */
	private double ClmFeeMIN;
	/** 起始费用单位 */
	private String ClmFeeMINFlag;
	/** 费用期间间隔 */
	private double ClmFeeInterval;
	/** 费用期间间隔单位 */
	private String ClmFeeFlag;
	/** 赔付金额计算sql */
	private String CalCode2;
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
	public LMClaimCtrlFeeSchema()
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
		LMClaimCtrlFeeSchema cloned = (LMClaimCtrlFeeSchema)super.clone();
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
	public double getClmFeeMIN()
	{
		return ClmFeeMIN;
	}
	public void setClmFeeMIN(double aClmFeeMIN)
	{
		ClmFeeMIN = aClmFeeMIN;
	}
	public void setClmFeeMIN(String aClmFeeMIN)
	{
		if (aClmFeeMIN != null && !aClmFeeMIN.equals(""))
		{
			Double tDouble = new Double(aClmFeeMIN);
			double d = tDouble.doubleValue();
			ClmFeeMIN = d;
		}
	}

	public String getClmFeeMINFlag()
	{
		return ClmFeeMINFlag;
	}
	public void setClmFeeMINFlag(String aClmFeeMINFlag)
	{
		ClmFeeMINFlag = aClmFeeMINFlag;
	}
	public double getClmFeeInterval()
	{
		return ClmFeeInterval;
	}
	public void setClmFeeInterval(double aClmFeeInterval)
	{
		ClmFeeInterval = aClmFeeInterval;
	}
	public void setClmFeeInterval(String aClmFeeInterval)
	{
		if (aClmFeeInterval != null && !aClmFeeInterval.equals(""))
		{
			Double tDouble = new Double(aClmFeeInterval);
			double d = tDouble.doubleValue();
			ClmFeeInterval = d;
		}
	}

	public String getClmFeeFlag()
	{
		return ClmFeeFlag;
	}
	public void setClmFeeFlag(String aClmFeeFlag)
	{
		ClmFeeFlag = aClmFeeFlag;
	}
	public String getCalCode2()
	{
		return CalCode2;
	}
	public void setCalCode2(String aCalCode2)
	{
		CalCode2 = aCalCode2;
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
	* 使用另外一个 LMClaimCtrlFeeSchema 对象给 Schema 赋值
	* @param: aLMClaimCtrlFeeSchema LMClaimCtrlFeeSchema
	**/
	public void setSchema(LMClaimCtrlFeeSchema aLMClaimCtrlFeeSchema)
	{
		this.ClaimCtrlCode = aLMClaimCtrlFeeSchema.getClaimCtrlCode();
		this.ClmFeeMIN = aLMClaimCtrlFeeSchema.getClmFeeMIN();
		this.ClmFeeMINFlag = aLMClaimCtrlFeeSchema.getClmFeeMINFlag();
		this.ClmFeeInterval = aLMClaimCtrlFeeSchema.getClmFeeInterval();
		this.ClmFeeFlag = aLMClaimCtrlFeeSchema.getClmFeeFlag();
		this.CalCode2 = aLMClaimCtrlFeeSchema.getCalCode2();
		this.CalResultType = aLMClaimCtrlFeeSchema.getCalResultType();
		this.DefaultValue = aLMClaimCtrlFeeSchema.getDefaultValue();
		this.CalCtrlFlag = aLMClaimCtrlFeeSchema.getCalCtrlFlag();
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

			this.ClmFeeMIN = rs.getDouble("ClmFeeMIN");
			if( rs.getString("ClmFeeMINFlag") == null )
				this.ClmFeeMINFlag = null;
			else
				this.ClmFeeMINFlag = rs.getString("ClmFeeMINFlag").trim();

			this.ClmFeeInterval = rs.getDouble("ClmFeeInterval");
			if( rs.getString("ClmFeeFlag") == null )
				this.ClmFeeFlag = null;
			else
				this.ClmFeeFlag = rs.getString("ClmFeeFlag").trim();

			if( rs.getString("CalCode2") == null )
				this.CalCode2 = null;
			else
				this.CalCode2 = rs.getString("CalCode2").trim();

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
			logger.debug("数据库中的LMClaimCtrlFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimCtrlFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMClaimCtrlFeeSchema getSchema()
	{
		LMClaimCtrlFeeSchema aLMClaimCtrlFeeSchema = new LMClaimCtrlFeeSchema();
		aLMClaimCtrlFeeSchema.setSchema(this);
		return aLMClaimCtrlFeeSchema;
	}

	public LMClaimCtrlFeeDB getDB()
	{
		LMClaimCtrlFeeDB aDBOper = new LMClaimCtrlFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimCtrlFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClaimCtrlCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmFeeMIN));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeMINFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmFeeInterval));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFeeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalResultType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCtrlFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimCtrlFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClaimCtrlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClmFeeMIN = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).doubleValue();
			ClmFeeMINFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmFeeInterval = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ClmFeeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalResultType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DefaultValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CalCtrlFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimCtrlFeeSchema";
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
		if (FCode.equalsIgnoreCase("ClmFeeMIN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeMIN));
		}
		if (FCode.equalsIgnoreCase("ClmFeeMINFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeMINFlag));
		}
		if (FCode.equalsIgnoreCase("ClmFeeInterval"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeInterval));
		}
		if (FCode.equalsIgnoreCase("ClmFeeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFeeFlag));
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode2));
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
				strFieldValue = String.valueOf(ClmFeeMIN);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeMINFlag);
				break;
			case 3:
				strFieldValue = String.valueOf(ClmFeeInterval);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmFeeFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCode2);
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
		if (FCode.equalsIgnoreCase("ClmFeeMIN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClmFeeMIN = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmFeeMINFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeMINFlag = FValue.trim();
			}
			else
				ClmFeeMINFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmFeeInterval"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClmFeeInterval = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClmFeeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFeeFlag = FValue.trim();
			}
			else
				ClmFeeFlag = null;
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode2 = FValue.trim();
			}
			else
				CalCode2 = null;
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
		LMClaimCtrlFeeSchema other = (LMClaimCtrlFeeSchema)otherObject;
		return
			ClaimCtrlCode.equals(other.getClaimCtrlCode())
			&& ClmFeeMIN == other.getClmFeeMIN()
			&& ClmFeeMINFlag.equals(other.getClmFeeMINFlag())
			&& ClmFeeInterval == other.getClmFeeInterval()
			&& ClmFeeFlag.equals(other.getClmFeeFlag())
			&& CalCode2.equals(other.getCalCode2())
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
		if( strFieldName.equals("ClmFeeMIN") ) {
			return 1;
		}
		if( strFieldName.equals("ClmFeeMINFlag") ) {
			return 2;
		}
		if( strFieldName.equals("ClmFeeInterval") ) {
			return 3;
		}
		if( strFieldName.equals("ClmFeeFlag") ) {
			return 4;
		}
		if( strFieldName.equals("CalCode2") ) {
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
				strFieldName = "ClmFeeMIN";
				break;
			case 2:
				strFieldName = "ClmFeeMINFlag";
				break;
			case 3:
				strFieldName = "ClmFeeInterval";
				break;
			case 4:
				strFieldName = "ClmFeeFlag";
				break;
			case 5:
				strFieldName = "CalCode2";
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
		if( strFieldName.equals("ClmFeeMIN") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmFeeMINFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFeeInterval") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmFeeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode2") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
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
