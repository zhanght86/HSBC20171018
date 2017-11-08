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
import com.sinosoft.lis.db.LQClaimExpenseDB;

/*
 * <p>ClassName: LQClaimExpenseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2010-09-09
 */
public class LQClaimExpenseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LQClaimExpenseSchema.class);

	// @Field
	/** 询价编码 */
	private String AskPriceNo;
	/** 报价版本号 */
	private String AskNo;
	/** Serialno */
	private String SerialNo;
	/** Year */
	private String Year;
	/** Expensetype */
	private String ExpenseType;
	/** Expensevalue */
	private double ExpenseValue;
	/** Attribute1 */
	private String Attribute1;
	/** Attribute2 */
	private String Attribute2;
	/** Attribute3 */
	private String Attribute3;
	/** Attribute4 */
	private String Attribute4;
	/** Attribute5 */
	private String Attribute5;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LQClaimExpenseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "AskPriceNo";
		pk[1] = "AskNo";
		pk[2] = "SerialNo";
		pk[3] = "ExpenseType";

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
                LQClaimExpenseSchema cloned = (LQClaimExpenseSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAskPriceNo()
	{
		return AskPriceNo;
	}
	public void setAskPriceNo(String aAskPriceNo)
	{
		AskPriceNo = aAskPriceNo;
	}
	public String getAskNo()
	{
		return AskNo;
	}
	public void setAskNo(String aAskNo)
	{
		AskNo = aAskNo;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getYear()
	{
		return Year;
	}
	public void setYear(String aYear)
	{
		Year = aYear;
	}
	public String getExpenseType()
	{
		return ExpenseType;
	}
	public void setExpenseType(String aExpenseType)
	{
		ExpenseType = aExpenseType;
	}
	public double getExpenseValue()
	{
		return ExpenseValue;
	}
	public void setExpenseValue(double aExpenseValue)
	{
		ExpenseValue = aExpenseValue;
	}
	public void setExpenseValue(String aExpenseValue)
	{
		if (aExpenseValue != null && !aExpenseValue.equals(""))
		{
			Double tDouble = new Double(aExpenseValue);
			double d = tDouble.doubleValue();
			ExpenseValue = d;
		}
	}

	public String getAttribute1()
	{
		return Attribute1;
	}
	public void setAttribute1(String aAttribute1)
	{
		Attribute1 = aAttribute1;
	}
	public String getAttribute2()
	{
		return Attribute2;
	}
	public void setAttribute2(String aAttribute2)
	{
		Attribute2 = aAttribute2;
	}
	public String getAttribute3()
	{
		return Attribute3;
	}
	public void setAttribute3(String aAttribute3)
	{
		Attribute3 = aAttribute3;
	}
	public String getAttribute4()
	{
		return Attribute4;
	}
	public void setAttribute4(String aAttribute4)
	{
		Attribute4 = aAttribute4;
	}
	public String getAttribute5()
	{
		return Attribute5;
	}
	public void setAttribute5(String aAttribute5)
	{
		Attribute5 = aAttribute5;
	}

	/**
	* 使用另外一个 LQClaimExpenseSchema 对象给 Schema 赋值
	* @param: aLQClaimExpenseSchema LQClaimExpenseSchema
	**/
	public void setSchema(LQClaimExpenseSchema aLQClaimExpenseSchema)
	{
		this.AskPriceNo = aLQClaimExpenseSchema.getAskPriceNo();
		this.AskNo = aLQClaimExpenseSchema.getAskNo();
		this.SerialNo = aLQClaimExpenseSchema.getSerialNo();
		this.Year = aLQClaimExpenseSchema.getYear();
		this.ExpenseType = aLQClaimExpenseSchema.getExpenseType();
		this.ExpenseValue = aLQClaimExpenseSchema.getExpenseValue();
		this.Attribute1 = aLQClaimExpenseSchema.getAttribute1();
		this.Attribute2 = aLQClaimExpenseSchema.getAttribute2();
		this.Attribute3 = aLQClaimExpenseSchema.getAttribute3();
		this.Attribute4 = aLQClaimExpenseSchema.getAttribute4();
		this.Attribute5 = aLQClaimExpenseSchema.getAttribute5();
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
			if( rs.getString("AskPriceNo") == null )
				this.AskPriceNo = null;
			else
				this.AskPriceNo = rs.getString("AskPriceNo").trim();

			if( rs.getString("AskNo") == null )
				this.AskNo = null;
			else
				this.AskNo = rs.getString("AskNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("Year") == null )
				this.Year = null;
			else
				this.Year = rs.getString("Year").trim();

			if( rs.getString("ExpenseType") == null )
				this.ExpenseType = null;
			else
				this.ExpenseType = rs.getString("ExpenseType").trim();

			this.ExpenseValue = rs.getDouble("ExpenseValue");
			if( rs.getString("Attribute1") == null )
				this.Attribute1 = null;
			else
				this.Attribute1 = rs.getString("Attribute1").trim();

			if( rs.getString("Attribute2") == null )
				this.Attribute2 = null;
			else
				this.Attribute2 = rs.getString("Attribute2").trim();

			if( rs.getString("Attribute3") == null )
				this.Attribute3 = null;
			else
				this.Attribute3 = rs.getString("Attribute3").trim();

			if( rs.getString("Attribute4") == null )
				this.Attribute4 = null;
			else
				this.Attribute4 = rs.getString("Attribute4").trim();

			if( rs.getString("Attribute5") == null )
				this.Attribute5 = null;
			else
				this.Attribute5 = rs.getString("Attribute5").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LQClaimExpense表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LQClaimExpenseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LQClaimExpenseSchema getSchema()
	{
		LQClaimExpenseSchema aLQClaimExpenseSchema = new LQClaimExpenseSchema();
		aLQClaimExpenseSchema.setSchema(this);
		return aLQClaimExpenseSchema;
	}

	public LQClaimExpenseDB getDB()
	{
		LQClaimExpenseDB aDBOper = new LQClaimExpenseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQClaimExpense描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(AskPriceNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AskNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Year)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ExpenseType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(ExpenseValue));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute4)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute5));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQClaimExpense>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AskPriceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Year = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ExpenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ExpenseValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			Attribute1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Attribute2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Attribute3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Attribute4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Attribute5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LQClaimExpenseSchema";
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
		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskPriceNo));
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Year));
		}
		if (FCode.equalsIgnoreCase("ExpenseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpenseType));
		}
		if (FCode.equalsIgnoreCase("ExpenseValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpenseValue));
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute1));
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute2));
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute3));
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute4));
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute5));
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
				strFieldValue = StrTool.GBKToUnicode(AskPriceNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AskNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Year);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ExpenseType);
				break;
			case 5:
				strFieldValue = String.valueOf(ExpenseValue);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Attribute1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Attribute2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Attribute3);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Attribute4);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Attribute5);
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

		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskPriceNo = FValue.trim();
			}
			else
				AskPriceNo = null;
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskNo = FValue.trim();
			}
			else
				AskNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Year = FValue.trim();
			}
			else
				Year = null;
		}
		if (FCode.equalsIgnoreCase("ExpenseType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpenseType = FValue.trim();
			}
			else
				ExpenseType = null;
		}
		if (FCode.equalsIgnoreCase("ExpenseValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpenseValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute1 = FValue.trim();
			}
			else
				Attribute1 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute2 = FValue.trim();
			}
			else
				Attribute2 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute3 = FValue.trim();
			}
			else
				Attribute3 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute4 = FValue.trim();
			}
			else
				Attribute4 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute5 = FValue.trim();
			}
			else
				Attribute5 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LQClaimExpenseSchema other = (LQClaimExpenseSchema)otherObject;
		return
			AskPriceNo.equals(other.getAskPriceNo())
			&& AskNo.equals(other.getAskNo())
			&& SerialNo.equals(other.getSerialNo())
			&& Year.equals(other.getYear())
			&& ExpenseType.equals(other.getExpenseType())
			&& ExpenseValue == other.getExpenseValue()
			&& Attribute1.equals(other.getAttribute1())
			&& Attribute2.equals(other.getAttribute2())
			&& Attribute3.equals(other.getAttribute3())
			&& Attribute4.equals(other.getAttribute4())
			&& Attribute5.equals(other.getAttribute5());
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
		if( strFieldName.equals("AskPriceNo") ) {
			return 0;
		}
		if( strFieldName.equals("AskNo") ) {
			return 1;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("Year") ) {
			return 3;
		}
		if( strFieldName.equals("ExpenseType") ) {
			return 4;
		}
		if( strFieldName.equals("ExpenseValue") ) {
			return 5;
		}
		if( strFieldName.equals("Attribute1") ) {
			return 6;
		}
		if( strFieldName.equals("Attribute2") ) {
			return 7;
		}
		if( strFieldName.equals("Attribute3") ) {
			return 8;
		}
		if( strFieldName.equals("Attribute4") ) {
			return 9;
		}
		if( strFieldName.equals("Attribute5") ) {
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
				strFieldName = "AskPriceNo";
				break;
			case 1:
				strFieldName = "AskNo";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "Year";
				break;
			case 4:
				strFieldName = "ExpenseType";
				break;
			case 5:
				strFieldName = "ExpenseValue";
				break;
			case 6:
				strFieldName = "Attribute1";
				break;
			case 7:
				strFieldName = "Attribute2";
				break;
			case 8:
				strFieldName = "Attribute3";
				break;
			case 9:
				strFieldName = "Attribute4";
				break;
			case 10:
				strFieldName = "Attribute5";
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
		if( strFieldName.equals("AskPriceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Year") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpenseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpenseValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Attribute1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute5") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
