

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
import com.sinosoft.lis.db.RIDivisionLineDefDB;

/*
 * <p>ClassName: RIDivisionLineDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIDivisionLineDefSchema implements Schema, Cloneable
{
	// @Field
	/** 再保合同号码 */
	private String RIContNo;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 溢额线类型 */
	private String DivisionLineType;
	/** 溢额线顺序编号 */
	private int DivisionLineOrder;
	/** 溢额线数值 */
	private double DivisionLineValue;
	/** 动态限额编码 */
	private String FactorCode;
	/** 动态限额系数 */
	private String DivisionFactor;
	/** 状态 */
	private String State;
	/** 币别 */
	private String Currency;
	/** 备用字段1 */
	private String StandByOne;
	/** 备用字段2 */
	private String StandByTwo;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIDivisionLineDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RIPreceptNo";
		pk[1] = "DivisionLineOrder";

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
		RIDivisionLineDefSchema cloned = (RIDivisionLineDefSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	/**
	* 01-自留额 <p>
	* 02-层次线 <p>
	* 03-临分限额 <p>
	* 04-最低分出额
	*/
	public String getDivisionLineType()
	{
		return DivisionLineType;
	}
	public void setDivisionLineType(String aDivisionLineType)
	{
		DivisionLineType = aDivisionLineType;
	}
	public int getDivisionLineOrder()
	{
		return DivisionLineOrder;
	}
	public void setDivisionLineOrder(int aDivisionLineOrder)
	{
		DivisionLineOrder = aDivisionLineOrder;
	}
	public void setDivisionLineOrder(String aDivisionLineOrder)
	{
		if (aDivisionLineOrder != null && !aDivisionLineOrder.equals(""))
		{
			Integer tInteger = new Integer(aDivisionLineOrder);
			int i = tInteger.intValue();
			DivisionLineOrder = i;
		}
	}

	public double getDivisionLineValue()
	{
		return DivisionLineValue;
	}
	public void setDivisionLineValue(double aDivisionLineValue)
	{
		DivisionLineValue = aDivisionLineValue;
	}
	public void setDivisionLineValue(String aDivisionLineValue)
	{
		if (aDivisionLineValue != null && !aDivisionLineValue.equals(""))
		{
			Double tDouble = new Double(aDivisionLineValue);
			double d = tDouble.doubleValue();
			DivisionLineValue = d;
		}
	}

	public String getFactorCode()
	{
		return FactorCode;
	}
	public void setFactorCode(String aFactorCode)
	{
		FactorCode = aFactorCode;
	}
	/**
	* 动态限额的系数
	*/
	public String getDivisionFactor()
	{
		return DivisionFactor;
	}
	public void setDivisionFactor(String aDivisionFactor)
	{
		DivisionFactor = aDivisionFactor;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getStandByOne()
	{
		return StandByOne;
	}
	public void setStandByOne(String aStandByOne)
	{
		StandByOne = aStandByOne;
	}
	public String getStandByTwo()
	{
		return StandByTwo;
	}
	public void setStandByTwo(String aStandByTwo)
	{
		StandByTwo = aStandByTwo;
	}

	/**
	* 使用另外一个 RIDivisionLineDefSchema 对象给 Schema 赋值
	* @param: aRIDivisionLineDefSchema RIDivisionLineDefSchema
	**/
	public void setSchema(RIDivisionLineDefSchema aRIDivisionLineDefSchema)
	{
		this.RIContNo = aRIDivisionLineDefSchema.getRIContNo();
		this.RIPreceptNo = aRIDivisionLineDefSchema.getRIPreceptNo();
		this.DivisionLineType = aRIDivisionLineDefSchema.getDivisionLineType();
		this.DivisionLineOrder = aRIDivisionLineDefSchema.getDivisionLineOrder();
		this.DivisionLineValue = aRIDivisionLineDefSchema.getDivisionLineValue();
		this.FactorCode = aRIDivisionLineDefSchema.getFactorCode();
		this.DivisionFactor = aRIDivisionLineDefSchema.getDivisionFactor();
		this.State = aRIDivisionLineDefSchema.getState();
		this.Currency = aRIDivisionLineDefSchema.getCurrency();
		this.StandByOne = aRIDivisionLineDefSchema.getStandByOne();
		this.StandByTwo = aRIDivisionLineDefSchema.getStandByTwo();
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
			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("DivisionLineType") == null )
				this.DivisionLineType = null;
			else
				this.DivisionLineType = rs.getString("DivisionLineType").trim();

			this.DivisionLineOrder = rs.getInt("DivisionLineOrder");
			this.DivisionLineValue = rs.getDouble("DivisionLineValue");
			if( rs.getString("FactorCode") == null )
				this.FactorCode = null;
			else
				this.FactorCode = rs.getString("FactorCode").trim();

			if( rs.getString("DivisionFactor") == null )
				this.DivisionFactor = null;
			else
				this.DivisionFactor = rs.getString("DivisionFactor").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("StandByOne") == null )
				this.StandByOne = null;
			else
				this.StandByOne = rs.getString("StandByOne").trim();

			if( rs.getString("StandByTwo") == null )
				this.StandByTwo = null;
			else
				this.StandByTwo = rs.getString("StandByTwo").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIDivisionLineDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIDivisionLineDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIDivisionLineDefSchema getSchema()
	{
		RIDivisionLineDefSchema aRIDivisionLineDefSchema = new RIDivisionLineDefSchema();
		aRIDivisionLineDefSchema.setSchema(this);
		return aRIDivisionLineDefSchema;
	}

	public RIDivisionLineDefDB getDB()
	{
		RIDivisionLineDefDB aDBOper = new RIDivisionLineDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIDivisionLineDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DivisionLineType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DivisionLineOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DivisionLineValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DivisionFactor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByOne)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByTwo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIDivisionLineDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DivisionLineType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DivisionLineOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			DivisionLineValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			FactorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DivisionFactor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StandByOne = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandByTwo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIDivisionLineDefSchema";
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
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("DivisionLineType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DivisionLineType));
		}
		if (FCode.equalsIgnoreCase("DivisionLineOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DivisionLineOrder));
		}
		if (FCode.equalsIgnoreCase("DivisionLineValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DivisionLineValue));
		}
		if (FCode.equalsIgnoreCase("FactorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorCode));
		}
		if (FCode.equalsIgnoreCase("DivisionFactor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DivisionFactor));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("StandByOne"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByOne));
		}
		if (FCode.equalsIgnoreCase("StandByTwo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByTwo));
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
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DivisionLineType);
				break;
			case 3:
				strFieldValue = String.valueOf(DivisionLineOrder);
				break;
			case 4:
				strFieldValue = String.valueOf(DivisionLineValue);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactorCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DivisionFactor);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandByOne);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandByTwo);
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

		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("DivisionLineType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DivisionLineType = FValue.trim();
			}
			else
				DivisionLineType = null;
		}
		if (FCode.equalsIgnoreCase("DivisionLineOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DivisionLineOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("DivisionLineValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DivisionLineValue = d;
			}
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
		if (FCode.equalsIgnoreCase("DivisionFactor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DivisionFactor = FValue.trim();
			}
			else
				DivisionFactor = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("StandByOne"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByOne = FValue.trim();
			}
			else
				StandByOne = null;
		}
		if (FCode.equalsIgnoreCase("StandByTwo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByTwo = FValue.trim();
			}
			else
				StandByTwo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIDivisionLineDefSchema other = (RIDivisionLineDefSchema)otherObject;
		return
			RIContNo.equals(other.getRIContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& DivisionLineType.equals(other.getDivisionLineType())
			&& DivisionLineOrder == other.getDivisionLineOrder()
			&& DivisionLineValue == other.getDivisionLineValue()
			&& FactorCode.equals(other.getFactorCode())
			&& DivisionFactor.equals(other.getDivisionFactor())
			&& State.equals(other.getState())
			&& Currency.equals(other.getCurrency())
			&& StandByOne.equals(other.getStandByOne())
			&& StandByTwo.equals(other.getStandByTwo());
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
		if( strFieldName.equals("RIContNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 1;
		}
		if( strFieldName.equals("DivisionLineType") ) {
			return 2;
		}
		if( strFieldName.equals("DivisionLineOrder") ) {
			return 3;
		}
		if( strFieldName.equals("DivisionLineValue") ) {
			return 4;
		}
		if( strFieldName.equals("FactorCode") ) {
			return 5;
		}
		if( strFieldName.equals("DivisionFactor") ) {
			return 6;
		}
		if( strFieldName.equals("State") ) {
			return 7;
		}
		if( strFieldName.equals("Currency") ) {
			return 8;
		}
		if( strFieldName.equals("StandByOne") ) {
			return 9;
		}
		if( strFieldName.equals("StandByTwo") ) {
			return 10;
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
				strFieldName = "RIContNo";
				break;
			case 1:
				strFieldName = "RIPreceptNo";
				break;
			case 2:
				strFieldName = "DivisionLineType";
				break;
			case 3:
				strFieldName = "DivisionLineOrder";
				break;
			case 4:
				strFieldName = "DivisionLineValue";
				break;
			case 5:
				strFieldName = "FactorCode";
				break;
			case 6:
				strFieldName = "DivisionFactor";
				break;
			case 7:
				strFieldName = "State";
				break;
			case 8:
				strFieldName = "Currency";
				break;
			case 9:
				strFieldName = "StandByOne";
				break;
			case 10:
				strFieldName = "StandByTwo";
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
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DivisionLineType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DivisionLineOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DivisionLineValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FactorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DivisionFactor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByOne") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByTwo") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
