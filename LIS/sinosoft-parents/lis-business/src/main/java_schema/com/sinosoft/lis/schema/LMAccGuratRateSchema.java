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
import com.sinosoft.lis.db.LMAccGuratRateDB;

/*
 * <p>ClassName: LMAccGuratRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMAccGuratRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMAccGuratRateSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 起始日期 */
	private Date RateStartDate;
	/** 结束日期 */
	private Date RateEndDate;
	/** 利率类型 */
	private String RateIntv;
	/** 保证利率 */
	private double Rate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 利率状态 */
	private String RateState;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMAccGuratRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "InsuAccNo";
		pk[2] = "RateStartDate";
		pk[3] = "RateIntv";

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
		LMAccGuratRateSchema cloned = (LMAccGuratRateSchema)super.clone();
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
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
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
	* Y －－ 年利率<p>
	* M －－ 月利率<p>
	* D －－ 日利率
	*/
	public String getRateIntv()
	{
		return RateIntv;
	}
	public void setRateIntv(String aRateIntv)
	{
		RateIntv = aRateIntv;
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
	* 由于输入的利率要进行高级别人的复核<p>
	* 1--------已复核<p>
	* 0--------复核
	*/
	public String getRateState()
	{
		return RateState;
	}
	public void setRateState(String aRateState)
	{
		RateState = aRateState;
	}

	/**
	* 使用另外一个 LMAccGuratRateSchema 对象给 Schema 赋值
	* @param: aLMAccGuratRateSchema LMAccGuratRateSchema
	**/
	public void setSchema(LMAccGuratRateSchema aLMAccGuratRateSchema)
	{
		this.RiskCode = aLMAccGuratRateSchema.getRiskCode();
		this.InsuAccNo = aLMAccGuratRateSchema.getInsuAccNo();
		this.RateStartDate = fDate.getDate( aLMAccGuratRateSchema.getRateStartDate());
		this.RateEndDate = fDate.getDate( aLMAccGuratRateSchema.getRateEndDate());
		this.RateIntv = aLMAccGuratRateSchema.getRateIntv();
		this.Rate = aLMAccGuratRateSchema.getRate();
		this.Operator = aLMAccGuratRateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLMAccGuratRateSchema.getMakeDate());
		this.MakeTime = aLMAccGuratRateSchema.getMakeTime();
		this.RateState = aLMAccGuratRateSchema.getRateState();
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

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			this.RateStartDate = rs.getDate("RateStartDate");
			this.RateEndDate = rs.getDate("RateEndDate");
			if( rs.getString("RateIntv") == null )
				this.RateIntv = null;
			else
				this.RateIntv = rs.getString("RateIntv").trim();

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

			if( rs.getString("RateState") == null )
				this.RateState = null;
			else
				this.RateState = rs.getString("RateState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMAccGuratRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMAccGuratRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMAccGuratRateSchema getSchema()
	{
		LMAccGuratRateSchema aLMAccGuratRateSchema = new LMAccGuratRateSchema();
		aLMAccGuratRateSchema.setSchema(this);
		return aLMAccGuratRateSchema;
	}

	public LMAccGuratRateDB getDB()
	{
		LMAccGuratRateDB aDBOper = new LMAccGuratRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMAccGuratRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RateStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RateEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMAccGuratRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RateStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			RateEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			RateIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RateState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMAccGuratRateSchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("RateStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRateStartDate()));
		}
		if (FCode.equalsIgnoreCase("RateEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRateEndDate()));
		}
		if (FCode.equalsIgnoreCase("RateIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateIntv));
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
		if (FCode.equalsIgnoreCase("RateState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateState));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRateStartDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRateEndDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RateIntv);
				break;
			case 5:
				strFieldValue = String.valueOf(Rate);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RateState);
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equalsIgnoreCase("RateIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateIntv = FValue.trim();
			}
			else
				RateIntv = null;
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
		if (FCode.equalsIgnoreCase("RateState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateState = FValue.trim();
			}
			else
				RateState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMAccGuratRateSchema other = (LMAccGuratRateSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& fDate.getString(RateStartDate).equals(other.getRateStartDate())
			&& fDate.getString(RateEndDate).equals(other.getRateEndDate())
			&& RateIntv.equals(other.getRateIntv())
			&& Rate == other.getRate()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& RateState.equals(other.getRateState());
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 1;
		}
		if( strFieldName.equals("RateStartDate") ) {
			return 2;
		}
		if( strFieldName.equals("RateEndDate") ) {
			return 3;
		}
		if( strFieldName.equals("RateIntv") ) {
			return 4;
		}
		if( strFieldName.equals("Rate") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("RateState") ) {
			return 9;
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
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "RateStartDate";
				break;
			case 3:
				strFieldName = "RateEndDate";
				break;
			case 4:
				strFieldName = "RateIntv";
				break;
			case 5:
				strFieldName = "Rate";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "RateState";
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RateEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RateIntv") ) {
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
		if( strFieldName.equals("RateState") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
