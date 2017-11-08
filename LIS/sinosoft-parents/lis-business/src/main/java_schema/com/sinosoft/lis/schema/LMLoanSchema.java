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
import com.sinosoft.lis.db.LMLoanDB;

/*
 * <p>ClassName: LMLoanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LMLoanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMLoanSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 控制类别 */
	private String ControlType;
	/** 能借款比例 */
	private double CanRate;
	/** 借款利息方式 */
	private String InterestType;
	/** 默认借款利率 */
	private double InterestRate;
	/** 利率类型 */
	private String InterestMode;
	/** 计算利率类型 */
	private String RateCalType;
	/** 计算利率编码 */
	private String RateCalCode;
	/** 是否按固定利率计算 */
	private String SpecifyRate;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMLoanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		LMLoanSchema cloned = (LMLoanSchema)super.clone();
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
	/**
	* 0 -- 保费<p>
	* 1 -- 现金价值
	*/
	public String getControlType()
	{
		return ControlType;
	}
	public void setControlType(String aControlType)
	{
		ControlType = aControlType;
	}
	public double getCanRate()
	{
		return CanRate;
	}
	public void setCanRate(double aCanRate)
	{
		CanRate = aCanRate;
	}
	public void setCanRate(String aCanRate)
	{
		if (aCanRate != null && !aCanRate.equals(""))
		{
			Double tDouble = new Double(aCanRate);
			double d = tDouble.doubleValue();
			CanRate = d;
		}
	}

	/**
	* 1 －－ 按单利计算<p>
	* 2 －－ 按复利计算
	*/
	public String getInterestType()
	{
		return InterestType;
	}
	public void setInterestType(String aInterestType)
	{
		InterestType = aInterestType;
	}
	/**
	* 指默认固定利率，针对固定利率
	*/
	public double getInterestRate()
	{
		return InterestRate;
	}
	public void setInterestRate(double aInterestRate)
	{
		InterestRate = aInterestRate;
	}
	public void setInterestRate(String aInterestRate)
	{
		if (aInterestRate != null && !aInterestRate.equals(""))
		{
			Double tDouble = new Double(aInterestRate);
			double d = tDouble.doubleValue();
			InterestRate = d;
		}
	}

	/**
	* 针对固定利率<p>
	* 该字段对于浮动利率没有意义，在浮动利率描述表中已经能够体现，该字段只是对固定利率有意义。<p>
	* 1 －－年利率<p>
	* 2 －－月利率<p>
	* 3 －－日利率
	*/
	public String getInterestMode()
	{
		return InterestMode;
	}
	public void setInterestMode(String aInterestMode)
	{
		InterestMode = aInterestMode;
	}
	/**
	* 针对浮动利率<p>
	* 1 －－ 表示按照浮动利率表进行计算（在计算利率方法字段中描述的是浮动利率表名）<p>
	* 2 －－ 表示按照一个计算编码计算。
	*/
	public String getRateCalType()
	{
		return RateCalType;
	}
	public void setRateCalType(String aRateCalType)
	{
		RateCalType = aRateCalType;
	}
	/**
	* 针对浮动利率
	*/
	public String getRateCalCode()
	{
		return RateCalCode;
	}
	public void setRateCalCode(String aRateCalCode)
	{
		RateCalCode = aRateCalCode;
	}
	/**
	* 1 －－ 按固定利率计算<p>
	* 2 －－ 按浮动利率计算
	*/
	public String getSpecifyRate()
	{
		return SpecifyRate;
	}
	public void setSpecifyRate(String aSpecifyRate)
	{
		SpecifyRate = aSpecifyRate;
	}

	/**
	* 使用另外一个 LMLoanSchema 对象给 Schema 赋值
	* @param: aLMLoanSchema LMLoanSchema
	**/
	public void setSchema(LMLoanSchema aLMLoanSchema)
	{
		this.RiskCode = aLMLoanSchema.getRiskCode();
		this.ControlType = aLMLoanSchema.getControlType();
		this.CanRate = aLMLoanSchema.getCanRate();
		this.InterestType = aLMLoanSchema.getInterestType();
		this.InterestRate = aLMLoanSchema.getInterestRate();
		this.InterestMode = aLMLoanSchema.getInterestMode();
		this.RateCalType = aLMLoanSchema.getRateCalType();
		this.RateCalCode = aLMLoanSchema.getRateCalCode();
		this.SpecifyRate = aLMLoanSchema.getSpecifyRate();
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

			if( rs.getString("ControlType") == null )
				this.ControlType = null;
			else
				this.ControlType = rs.getString("ControlType").trim();

			this.CanRate = rs.getDouble("CanRate");
			if( rs.getString("InterestType") == null )
				this.InterestType = null;
			else
				this.InterestType = rs.getString("InterestType").trim();

			this.InterestRate = rs.getDouble("InterestRate");
			if( rs.getString("InterestMode") == null )
				this.InterestMode = null;
			else
				this.InterestMode = rs.getString("InterestMode").trim();

			if( rs.getString("RateCalType") == null )
				this.RateCalType = null;
			else
				this.RateCalType = rs.getString("RateCalType").trim();

			if( rs.getString("RateCalCode") == null )
				this.RateCalCode = null;
			else
				this.RateCalCode = rs.getString("RateCalCode").trim();

			if( rs.getString("SpecifyRate") == null )
				this.SpecifyRate = null;
			else
				this.SpecifyRate = rs.getString("SpecifyRate").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMLoan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMLoanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMLoanSchema getSchema()
	{
		LMLoanSchema aLMLoanSchema = new LMLoanSchema();
		aLMLoanSchema.setSchema(this);
		return aLMLoanSchema;
	}

	public LMLoanDB getDB()
	{
		LMLoanDB aDBOper = new LMLoanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMLoan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ControlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CanRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InterestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RateCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecifyRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMLoan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ControlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CanRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			InterestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InterestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			InterestMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RateCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RateCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SpecifyRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMLoanSchema";
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
		if (FCode.equalsIgnoreCase("ControlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ControlType));
		}
		if (FCode.equalsIgnoreCase("CanRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CanRate));
		}
		if (FCode.equalsIgnoreCase("InterestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestType));
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestRate));
		}
		if (FCode.equalsIgnoreCase("InterestMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestMode));
		}
		if (FCode.equalsIgnoreCase("RateCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateCalType));
		}
		if (FCode.equalsIgnoreCase("RateCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RateCalCode));
		}
		if (FCode.equalsIgnoreCase("SpecifyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecifyRate));
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
				strFieldValue = StrTool.GBKToUnicode(ControlType);
				break;
			case 2:
				strFieldValue = String.valueOf(CanRate);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InterestType);
				break;
			case 4:
				strFieldValue = String.valueOf(InterestRate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InterestMode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RateCalType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RateCalCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SpecifyRate);
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
		if (FCode.equalsIgnoreCase("ControlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ControlType = FValue.trim();
			}
			else
				ControlType = null;
		}
		if (FCode.equalsIgnoreCase("CanRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CanRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InterestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestType = FValue.trim();
			}
			else
				InterestType = null;
		}
		if (FCode.equalsIgnoreCase("InterestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InterestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InterestMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestMode = FValue.trim();
			}
			else
				InterestMode = null;
		}
		if (FCode.equalsIgnoreCase("RateCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateCalType = FValue.trim();
			}
			else
				RateCalType = null;
		}
		if (FCode.equalsIgnoreCase("RateCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RateCalCode = FValue.trim();
			}
			else
				RateCalCode = null;
		}
		if (FCode.equalsIgnoreCase("SpecifyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecifyRate = FValue.trim();
			}
			else
				SpecifyRate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMLoanSchema other = (LMLoanSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ControlType.equals(other.getControlType())
			&& CanRate == other.getCanRate()
			&& InterestType.equals(other.getInterestType())
			&& InterestRate == other.getInterestRate()
			&& InterestMode.equals(other.getInterestMode())
			&& RateCalType.equals(other.getRateCalType())
			&& RateCalCode.equals(other.getRateCalCode())
			&& SpecifyRate.equals(other.getSpecifyRate());
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
		if( strFieldName.equals("ControlType") ) {
			return 1;
		}
		if( strFieldName.equals("CanRate") ) {
			return 2;
		}
		if( strFieldName.equals("InterestType") ) {
			return 3;
		}
		if( strFieldName.equals("InterestRate") ) {
			return 4;
		}
		if( strFieldName.equals("InterestMode") ) {
			return 5;
		}
		if( strFieldName.equals("RateCalType") ) {
			return 6;
		}
		if( strFieldName.equals("RateCalCode") ) {
			return 7;
		}
		if( strFieldName.equals("SpecifyRate") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "ControlType";
				break;
			case 2:
				strFieldName = "CanRate";
				break;
			case 3:
				strFieldName = "InterestType";
				break;
			case 4:
				strFieldName = "InterestRate";
				break;
			case 5:
				strFieldName = "InterestMode";
				break;
			case 6:
				strFieldName = "RateCalType";
				break;
			case 7:
				strFieldName = "RateCalCode";
				break;
			case 8:
				strFieldName = "SpecifyRate";
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
		if( strFieldName.equals("ControlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CanRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InterestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InterestMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RateCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecifyRate") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
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
