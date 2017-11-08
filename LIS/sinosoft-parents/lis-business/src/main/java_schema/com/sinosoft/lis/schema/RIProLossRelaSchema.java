

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIProLossRelaDB;

/*
 * <p>ClassName: RIProLossRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIProLossRelaSchema implements Schema, Cloneable
{
	// @Field
	/** 纯益手续费编码 */
	private String RIProfitNo;
	/** 币别 */
	private String Currency;
	/** 计算要素 */
	private String FactorCode;
	/** 要素名称 */
	private String FactorName;
	/** 再保公司 */
	private String ReComCode;
	/** 再保合同 */
	private String RIContNo;
	/** 录入类型 */
	private String InputType;
	/** 收入支出类型 */
	private String InOutType;
	/** 计算类型 */
	private String ItemCalType;
	/** 固定数字值 */
	private double DoubleValue;
	/** 计算处理类 */
	private String CalClass;
	/** 计算sql算法 */
	private String CalSQL;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIProLossRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RIProfitNo";
		pk[1] = "Currency";
		pk[2] = "FactorCode";

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
		RIProLossRelaSchema cloned = (RIProLossRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIProfitNo()
	{
		return RIProfitNo;
	}
	public void setRIProfitNo(String aRIProfitNo)
	{
		RIProfitNo = aRIProfitNo;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
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
	public String getReComCode()
	{
		return ReComCode;
	}
	public void setReComCode(String aReComCode)
	{
		ReComCode = aReComCode;
	}
	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	/**
	* 01：系统计算<p>
	* <p>
	* 02：手工录入
	*/
	public String getInputType()
	{
		return InputType;
	}
	public void setInputType(String aInputType)
	{
		InputType = aInputType;
	}
	public String getInOutType()
	{
		return InOutType;
	}
	public void setInOutType(String aInOutType)
	{
		InOutType = aInOutType;
	}
	/**
	* 01-固定数值,02-Sql计算,03-类计算
	*/
	public String getItemCalType()
	{
		return ItemCalType;
	}
	public void setItemCalType(String aItemCalType)
	{
		ItemCalType = aItemCalType;
	}
	public double getDoubleValue()
	{
		return DoubleValue;
	}
	public void setDoubleValue(double aDoubleValue)
	{
		DoubleValue = aDoubleValue;
	}
	public void setDoubleValue(String aDoubleValue)
	{
		if (aDoubleValue != null && !aDoubleValue.equals(""))
		{
			Double tDouble = new Double(aDoubleValue);
			double d = tDouble.doubleValue();
			DoubleValue = d;
		}
	}

	public String getCalClass()
	{
		return CalClass;
	}
	public void setCalClass(String aCalClass)
	{
		CalClass = aCalClass;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}

	/**
	* 使用另外一个 RIProLossRelaSchema 对象给 Schema 赋值
	* @param: aRIProLossRelaSchema RIProLossRelaSchema
	**/
	public void setSchema(RIProLossRelaSchema aRIProLossRelaSchema)
	{
		this.RIProfitNo = aRIProLossRelaSchema.getRIProfitNo();
		this.Currency = aRIProLossRelaSchema.getCurrency();
		this.FactorCode = aRIProLossRelaSchema.getFactorCode();
		this.FactorName = aRIProLossRelaSchema.getFactorName();
		this.ReComCode = aRIProLossRelaSchema.getReComCode();
		this.RIContNo = aRIProLossRelaSchema.getRIContNo();
		this.InputType = aRIProLossRelaSchema.getInputType();
		this.InOutType = aRIProLossRelaSchema.getInOutType();
		this.ItemCalType = aRIProLossRelaSchema.getItemCalType();
		this.DoubleValue = aRIProLossRelaSchema.getDoubleValue();
		this.CalClass = aRIProLossRelaSchema.getCalClass();
		this.CalSQL = aRIProLossRelaSchema.getCalSQL();
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
			if( rs.getString("RIProfitNo") == null )
				this.RIProfitNo = null;
			else
				this.RIProfitNo = rs.getString("RIProfitNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("FactorName") == null )
				this.FactorName = null;
			else
				this.FactorName = rs.getString("FactorName").trim();

			if( rs.getString("ReComCode") == null )
				this.ReComCode = null;
			else
				this.ReComCode = rs.getString("ReComCode").trim();

			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("InputType") == null )
				this.InputType = null;
			else
				this.InputType = rs.getString("InputType").trim();

			if( rs.getString("InOutType") == null )
				this.InOutType = null;
			else
				this.InOutType = rs.getString("InOutType").trim();

			if( rs.getString("ItemCalType") == null )
				this.ItemCalType = null;
			else
				this.ItemCalType = rs.getString("ItemCalType").trim();

			this.DoubleValue = rs.getDouble("DoubleValue");
			if( rs.getString("CalClass") == null )
				this.CalClass = null;
			else
				this.CalClass = rs.getString("CalClass").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIProLossRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProLossRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIProLossRelaSchema getSchema()
	{
		RIProLossRelaSchema aRIProLossRelaSchema = new RIProLossRelaSchema();
		aRIProLossRelaSchema.setSchema(this);
		return aRIProLossRelaSchema;
	}

	public RIProLossRelaDB getDB()
	{
		RIProLossRelaDB aDBOper = new RIProLossRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProLossRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIProfitNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InOutType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DoubleValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProLossRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIProfitNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InputType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InOutType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ItemCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DoubleValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			CalClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProLossRelaSchema";
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
		if (FCode.equalsIgnoreCase("RIProfitNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equalsIgnoreCase("FactorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorName));
		}
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReComCode));
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("InputType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputType));
		}
		if (FCode.equalsIgnoreCase("InOutType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InOutType));
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCalType));
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoubleValue));
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalClass));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
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
				strFieldValue = StrTool.GBKToUnicode(RIProfitNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactorName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReComCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InputType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InOutType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ItemCalType);
				break;
			case 9:
				strFieldValue = String.valueOf(DoubleValue);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CalClass);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
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

		if (FCode.equalsIgnoreCase("RIProfitNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitNo = FValue.trim();
			}
			else
				RIProfitNo = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
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
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReComCode = FValue.trim();
			}
			else
				ReComCode = null;
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("InputType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputType = FValue.trim();
			}
			else
				InputType = null;
		}
		if (FCode.equalsIgnoreCase("InOutType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InOutType = FValue.trim();
			}
			else
				InOutType = null;
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCalType = FValue.trim();
			}
			else
				ItemCalType = null;
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DoubleValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalClass = FValue.trim();
			}
			else
				CalClass = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIProLossRelaSchema other = (RIProLossRelaSchema)otherObject;
		return
			RIProfitNo.equals(other.getRIProfitNo())
			&& Currency.equals(other.getCurrency())
			&& FactorCode.equals(other.getFactorCode())
			&& FactorName.equals(other.getFactorName())
			&& ReComCode.equals(other.getReComCode())
			&& RIContNo.equals(other.getRIContNo())
			&& InputType.equals(other.getInputType())
			&& InOutType.equals(other.getInOutType())
			&& ItemCalType.equals(other.getItemCalType())
			&& DoubleValue == other.getDoubleValue()
			&& CalClass.equals(other.getCalClass())
			&& CalSQL.equals(other.getCalSQL());
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
		if( strFieldName.equals("RIProfitNo") ) {
			return 0;
		}
		if( strFieldName.equals("Currency") ) {
			return 1;
		}
		if( strFieldName.equals("FactorCode") ) {
			return 2;
		}
		if( strFieldName.equals("FactorName") ) {
			return 3;
		}
		if( strFieldName.equals("ReComCode") ) {
			return 4;
		}
		if( strFieldName.equals("RIContNo") ) {
			return 5;
		}
		if( strFieldName.equals("InputType") ) {
			return 6;
		}
		if( strFieldName.equals("InOutType") ) {
			return 7;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return 8;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return 9;
		}
		if( strFieldName.equals("CalClass") ) {
			return 10;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 11;
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
				strFieldName = "RIProfitNo";
				break;
			case 1:
				strFieldName = "Currency";
				break;
			case 2:
				strFieldName = "FactorCode";
				break;
			case 3:
				strFieldName = "FactorName";
				break;
			case 4:
				strFieldName = "ReComCode";
				break;
			case 5:
				strFieldName = "RIContNo";
				break;
			case 6:
				strFieldName = "InputType";
				break;
			case 7:
				strFieldName = "InOutType";
				break;
			case 8:
				strFieldName = "ItemCalType";
				break;
			case 9:
				strFieldName = "DoubleValue";
				break;
			case 10:
				strFieldName = "CalClass";
				break;
			case 11:
				strFieldName = "CalSQL";
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
		if( strFieldName.equals("RIProfitNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InOutType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
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
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
