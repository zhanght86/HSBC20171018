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
import com.sinosoft.lis.db.LMRevenueRateDB;

/*
 * <p>ClassName: LMRevenueRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRevenueRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRevenueRateSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 起始日期 */
	private Date RateStartDate;
	/** 结束日期 */
	private Date RateEndDate;
	/** 税率类型 */
	private String RateType;
	/** 税率 */
	private double Rate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRevenueRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "RateStartDate";
		pk[2] = "RateType";

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
		LMRevenueRateSchema cloned = (LMRevenueRateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRateStartDate()
	{
		if( RateStartDate != null )
			return fDate.getString(RateStartDate);
		else
			return null;
	}
	public void setRateStartDate(Date aRateStartDate)
	{
		RateStartDate = aRateStartDate;
	}
	public void setRateStartDate(String aRateStartDate)
	{
		if (aRateStartDate != null && !aRateStartDate.equals("") )
		{
			RateStartDate = fDate.getDate( aRateStartDate );
		}
		else
			RateStartDate = null;
	}

	public String getRateEndDate()
	{
		if( RateEndDate != null )
			return fDate.getString(RateEndDate);
		else
			return null;
	}
	public void setRateEndDate(Date aRateEndDate)
	{
		RateEndDate = aRateEndDate;
	}
	public void setRateEndDate(String aRateEndDate)
	{
		if (aRateEndDate != null && !aRateEndDate.equals("") )
		{
			RateEndDate = fDate.getDate( aRateEndDate );
		}
		else
			RateEndDate = null;
	}

	/**
	* YH - 印花税
	*/
	public String getRateType()
	{
		return RateType;
	}
	public void setRateType(String aRateType)
	{
		RateType = aRateType;
	}
	public double getRate()
	{
		return Rate;
	}
	public void setRate(double aRate)
	{
		Rate = aRate;
	}
	public void setRate(String aRate)
	{
		if (aRate != null && !aRate.equals(""))
		{
			Double tDouble = new Double(aRate);
			double d = tDouble.doubleValue();
			Rate = d;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 LMRevenueRateSchema 对象给 Schema 赋值
	* @param: aLMRevenueRateSchema LMRevenueRateSchema
	**/
	public void setSchema(LMRevenueRateSchema aLMRevenueRateSchema)
	{
		this.RiskCode = aLMRevenueRateSchema.getRiskCode();
		this.RateStartDate = fDate.getDate( aLMRevenueRateSchema.getRateStartDate());
		this.RateEndDate = fDate.getDate( aLMRevenueRateSchema.getRateEndDate());
		this.RateType = aLMRevenueRateSchema.getRateType();
		this.Rate = aLMRevenueRateSchema.getRate();
		this.Operator = aLMRevenueRateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLMRevenueRateSchema.getMakeDate());
		this.MakeTime = aLMRevenueRateSchema.getMakeTime();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.RateStartDate = rs.getDate("RateStartDate");
			this.RateEndDate = rs.getDate("RateEndDate");
			if( rs.getString("RateType") == null )
				this.RateType = null;
			else
				this.RateType = rs.getString("RateType").trim();

			this.Rate = rs.getDouble("Rate");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRevenueRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRevenueRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRevenueRateSchema getSchema()
	{
		LMRevenueRateSchema aLMRevenueRateSchema = new LMRevenueRateSchema();
		aLMRevenueRateSchema.setSchema(this);
		return aLMRevenueRateSchema;
	}

	public LMRevenueRateDB getDB()
	{
		LMRevenueRateDB aDBOper = new LMRevenueRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRevenueRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RateStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RateEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRevenueRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RateStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2,SysConst.PACKAGESPILTER));
			RateEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			RateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRevenueRateSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RateStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRateStartDate()));
		}
		if (FCode.equalsIgnoreCase("RateEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRateEndDate()));
		}
		if (FCode.equalsIgnoreCase("RateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateType));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRateStartDate()));
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRateEndDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RateType);
				break;
			case 4:
				strFieldValue = String.valueOf(Rate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RateStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RateStartDate = fDate.getDate( FValue );
			}
			else
				RateStartDate = null;
		}
		if (FCode.equalsIgnoreCase("RateEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RateEndDate = fDate.getDate( FValue );
			}
			else
				RateEndDate = null;
		}
		if (FCode.equalsIgnoreCase("RateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateType = FValue.trim();
			}
			else
				RateType = null;
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
			}
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRevenueRateSchema other = (LMRevenueRateSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& fDate.getString(RateStartDate).equals(other.getRateStartDate())
			&& fDate.getString(RateEndDate).equals(other.getRateEndDate())
			&& RateType.equals(other.getRateType())
			&& Rate == other.getRate()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("RateStartDate") ) {
			return 1;
		}
		if( strFieldName.equals("RateEndDate") ) {
			return 2;
		}
		if( strFieldName.equals("RateType") ) {
			return 3;
		}
		if( strFieldName.equals("Rate") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RateStartDate";
				break;
			case 2:
				strFieldName = "RateEndDate";
				break;
			case 3:
				strFieldName = "RateType";
				break;
			case 4:
				strFieldName = "Rate";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RateEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 2:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
