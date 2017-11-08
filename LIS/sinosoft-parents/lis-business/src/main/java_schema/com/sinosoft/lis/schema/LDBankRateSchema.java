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
import com.sinosoft.lis.db.LDBankRateDB;

/*
 * <p>ClassName: LDBankRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDBankRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDBankRateSchema.class);

	// @Field
	/** 期间 */
	private int Period;
	/** 期间标记 */
	private String PeriodFlag;
	/** 利率类型 */
	private String Type;
	/** 存/贷 */
	private String Depst_Loan;
	/** 公布日期 */
	private Date DeclareDate;
	/** 止期 */
	private Date EndDate;
	/** 利率 */
	private double Rate;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDBankRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "Period";
		pk[1] = "PeriodFlag";
		pk[2] = "Type";
		pk[3] = "Depst_Loan";
		pk[4] = "DeclareDate";
		pk[5] = "EndDate";

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
		LDBankRateSchema cloned = (LDBankRateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getPeriod()
	{
		return Period;
	}
	public void setPeriod(int aPeriod)
	{
		Period = aPeriod;
	}
	public void setPeriod(String aPeriod)
	{
		if (aPeriod != null && !aPeriod.equals(""))
		{
			Integer tInteger = new Integer(aPeriod);
			int i = tInteger.intValue();
			Period = i;
		}
	}

	/**
	* Y-年<p>
	* M-月<p>
	* D-天
	*/
	public String getPeriodFlag()
	{
		return PeriodFlag;
	}
	public void setPeriodFlag(String aPeriodFlag)
	{
		PeriodFlag = aPeriodFlag;
	}
	/**
	* C-活期<p>
	* F-定期
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	/**
	* D-存款<p>
	* L-贷款
	*/
	public String getDepst_Loan()
	{
		return Depst_Loan;
	}
	public void setDepst_Loan(String aDepst_Loan)
	{
		Depst_Loan = aDepst_Loan;
	}
	public String getDeclareDate()
	{
		if( DeclareDate != null )
			return fDate.getString(DeclareDate);
		else
			return null;
	}
	public void setDeclareDate(Date aDeclareDate)
	{
		DeclareDate = aDeclareDate;
	}
	public void setDeclareDate(String aDeclareDate)
	{
		if (aDeclareDate != null && !aDeclareDate.equals("") )
		{
			DeclareDate = fDate.getDate( aDeclareDate );
		}
		else
			DeclareDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
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


	/**
	* 使用另外一个 LDBankRateSchema 对象给 Schema 赋值
	* @param: aLDBankRateSchema LDBankRateSchema
	**/
	public void setSchema(LDBankRateSchema aLDBankRateSchema)
	{
		this.Period = aLDBankRateSchema.getPeriod();
		this.PeriodFlag = aLDBankRateSchema.getPeriodFlag();
		this.Type = aLDBankRateSchema.getType();
		this.Depst_Loan = aLDBankRateSchema.getDepst_Loan();
		this.DeclareDate = fDate.getDate( aLDBankRateSchema.getDeclareDate());
		this.EndDate = fDate.getDate( aLDBankRateSchema.getEndDate());
		this.Rate = aLDBankRateSchema.getRate();
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
			this.Period = rs.getInt("Period");
			if( rs.getString("PeriodFlag") == null )
				this.PeriodFlag = null;
			else
				this.PeriodFlag = rs.getString("PeriodFlag").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("Depst_Loan") == null )
				this.Depst_Loan = null;
			else
				this.Depst_Loan = rs.getString("Depst_Loan").trim();

			this.DeclareDate = rs.getDate("DeclareDate");
			this.EndDate = rs.getDate("EndDate");
			this.Rate = rs.getDouble("Rate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDBankRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDBankRateSchema getSchema()
	{
		LDBankRateSchema aLDBankRateSchema = new LDBankRateSchema();
		aLDBankRateSchema.setSchema(this);
		return aLDBankRateSchema;
	}

	public LDBankRateDB getDB()
	{
		LDBankRateDB aDBOper = new LDBankRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDBankRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(Period));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Depst_Loan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeclareDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDBankRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Period= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			PeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Depst_Loan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DeclareDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankRateSchema";
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
		if (FCode.equalsIgnoreCase("Period"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Period));
		}
		if (FCode.equalsIgnoreCase("PeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeriodFlag));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("Depst_Loan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Depst_Loan));
		}
		if (FCode.equalsIgnoreCase("DeclareDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeclareDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
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
				strFieldValue = String.valueOf(Period);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PeriodFlag);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Depst_Loan);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeclareDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 6:
				strFieldValue = String.valueOf(Rate);
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

		if (FCode.equalsIgnoreCase("Period"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Period = i;
			}
		}
		if (FCode.equalsIgnoreCase("PeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PeriodFlag = FValue.trim();
			}
			else
				PeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("Depst_Loan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Depst_Loan = FValue.trim();
			}
			else
				Depst_Loan = null;
		}
		if (FCode.equalsIgnoreCase("DeclareDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeclareDate = fDate.getDate( FValue );
			}
			else
				DeclareDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDBankRateSchema other = (LDBankRateSchema)otherObject;
		return
			Period == other.getPeriod()
			&& PeriodFlag.equals(other.getPeriodFlag())
			&& Type.equals(other.getType())
			&& Depst_Loan.equals(other.getDepst_Loan())
			&& fDate.getString(DeclareDate).equals(other.getDeclareDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& Rate == other.getRate();
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
		if( strFieldName.equals("Period") ) {
			return 0;
		}
		if( strFieldName.equals("PeriodFlag") ) {
			return 1;
		}
		if( strFieldName.equals("Type") ) {
			return 2;
		}
		if( strFieldName.equals("Depst_Loan") ) {
			return 3;
		}
		if( strFieldName.equals("DeclareDate") ) {
			return 4;
		}
		if( strFieldName.equals("EndDate") ) {
			return 5;
		}
		if( strFieldName.equals("Rate") ) {
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
				strFieldName = "Period";
				break;
			case 1:
				strFieldName = "PeriodFlag";
				break;
			case 2:
				strFieldName = "Type";
				break;
			case 3:
				strFieldName = "Depst_Loan";
				break;
			case 4:
				strFieldName = "DeclareDate";
				break;
			case 5:
				strFieldName = "EndDate";
				break;
			case 6:
				strFieldName = "Rate";
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
		if( strFieldName.equals("Period") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Depst_Loan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclareDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
