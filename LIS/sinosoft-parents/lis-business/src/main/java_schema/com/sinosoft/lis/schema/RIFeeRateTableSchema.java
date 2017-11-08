

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
import com.sinosoft.lis.db.RIFeeRateTableDB;

/*
 * <p>ClassName: RIFeeRateTableSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIFeeRateTableSchema implements Schema, Cloneable
{
	// @Field
	/** 费率表编号 */
	private String FeeTableNo;
	/** 费率表名称 */
	private String FeeTableName;
	/** 费率表批次 */
	private String BatchNo;
	/** 数字字段一 */
	private double NumOne;
	/** 数字字段二 */
	private double NumTwo;
	/** 数字字段三 */
	private double NumThree;
	/** 数字字段四 */
	private double NumFour;
	/** 数字字段五 */
	private double NumFive;
	/** 数字字段六 */
	private double NumSix;
	/** 数字字段七 */
	private double NumSeven;
	/** 数字字段八 */
	private double NumEight;
	/** 数字字段九 */
	private double NumNine;
	/** 数字字段十 */
	private double NumTen;
	/** 字符字段一 */
	private String StrOne;
	/** 字符字段二 */
	private String StrTwo;
	/** 字符字段三 */
	private String StrThree;
	/** 字符字段四 */
	private String StrFour;
	/** 字符字段五 */
	private String StrFive;
	/** 费率数值 */
	private double FeeRate;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIFeeRateTableSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[0];

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
		RIFeeRateTableSchema cloned = (RIFeeRateTableSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFeeTableNo()
	{
		return FeeTableNo;
	}
	public void setFeeTableNo(String aFeeTableNo)
	{
		FeeTableNo = aFeeTableNo;
	}
	public String getFeeTableName()
	{
		return FeeTableName;
	}
	public void setFeeTableName(String aFeeTableName)
	{
		FeeTableName = aFeeTableName;
	}
	/**
	* G01-一般  S01-特殊01 S02-特殊02。。。。。。。
	*/
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public double getNumOne()
	{
		return NumOne;
	}
	public void setNumOne(double aNumOne)
	{
		NumOne = aNumOne;
	}
	public void setNumOne(String aNumOne)
	{
		if (aNumOne != null && !aNumOne.equals(""))
		{
			Double tDouble = new Double(aNumOne);
			double d = tDouble.doubleValue();
			NumOne = d;
		}
	}

	public double getNumTwo()
	{
		return NumTwo;
	}
	public void setNumTwo(double aNumTwo)
	{
		NumTwo = aNumTwo;
	}
	public void setNumTwo(String aNumTwo)
	{
		if (aNumTwo != null && !aNumTwo.equals(""))
		{
			Double tDouble = new Double(aNumTwo);
			double d = tDouble.doubleValue();
			NumTwo = d;
		}
	}

	public double getNumThree()
	{
		return NumThree;
	}
	public void setNumThree(double aNumThree)
	{
		NumThree = aNumThree;
	}
	public void setNumThree(String aNumThree)
	{
		if (aNumThree != null && !aNumThree.equals(""))
		{
			Double tDouble = new Double(aNumThree);
			double d = tDouble.doubleValue();
			NumThree = d;
		}
	}

	public double getNumFour()
	{
		return NumFour;
	}
	public void setNumFour(double aNumFour)
	{
		NumFour = aNumFour;
	}
	public void setNumFour(String aNumFour)
	{
		if (aNumFour != null && !aNumFour.equals(""))
		{
			Double tDouble = new Double(aNumFour);
			double d = tDouble.doubleValue();
			NumFour = d;
		}
	}

	public double getNumFive()
	{
		return NumFive;
	}
	public void setNumFive(double aNumFive)
	{
		NumFive = aNumFive;
	}
	public void setNumFive(String aNumFive)
	{
		if (aNumFive != null && !aNumFive.equals(""))
		{
			Double tDouble = new Double(aNumFive);
			double d = tDouble.doubleValue();
			NumFive = d;
		}
	}

	public double getNumSix()
	{
		return NumSix;
	}
	public void setNumSix(double aNumSix)
	{
		NumSix = aNumSix;
	}
	public void setNumSix(String aNumSix)
	{
		if (aNumSix != null && !aNumSix.equals(""))
		{
			Double tDouble = new Double(aNumSix);
			double d = tDouble.doubleValue();
			NumSix = d;
		}
	}

	public double getNumSeven()
	{
		return NumSeven;
	}
	public void setNumSeven(double aNumSeven)
	{
		NumSeven = aNumSeven;
	}
	public void setNumSeven(String aNumSeven)
	{
		if (aNumSeven != null && !aNumSeven.equals(""))
		{
			Double tDouble = new Double(aNumSeven);
			double d = tDouble.doubleValue();
			NumSeven = d;
		}
	}

	public double getNumEight()
	{
		return NumEight;
	}
	public void setNumEight(double aNumEight)
	{
		NumEight = aNumEight;
	}
	public void setNumEight(String aNumEight)
	{
		if (aNumEight != null && !aNumEight.equals(""))
		{
			Double tDouble = new Double(aNumEight);
			double d = tDouble.doubleValue();
			NumEight = d;
		}
	}

	public double getNumNine()
	{
		return NumNine;
	}
	public void setNumNine(double aNumNine)
	{
		NumNine = aNumNine;
	}
	public void setNumNine(String aNumNine)
	{
		if (aNumNine != null && !aNumNine.equals(""))
		{
			Double tDouble = new Double(aNumNine);
			double d = tDouble.doubleValue();
			NumNine = d;
		}
	}

	public double getNumTen()
	{
		return NumTen;
	}
	public void setNumTen(double aNumTen)
	{
		NumTen = aNumTen;
	}
	public void setNumTen(String aNumTen)
	{
		if (aNumTen != null && !aNumTen.equals(""))
		{
			Double tDouble = new Double(aNumTen);
			double d = tDouble.doubleValue();
			NumTen = d;
		}
	}

	public String getStrOne()
	{
		return StrOne;
	}
	public void setStrOne(String aStrOne)
	{
		StrOne = aStrOne;
	}
	public String getStrTwo()
	{
		return StrTwo;
	}
	public void setStrTwo(String aStrTwo)
	{
		StrTwo = aStrTwo;
	}
	public String getStrThree()
	{
		return StrThree;
	}
	public void setStrThree(String aStrThree)
	{
		StrThree = aStrThree;
	}
	public String getStrFour()
	{
		return StrFour;
	}
	public void setStrFour(String aStrFour)
	{
		StrFour = aStrFour;
	}
	public String getStrFive()
	{
		return StrFive;
	}
	public void setStrFive(String aStrFive)
	{
		StrFive = aStrFive;
	}
	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}


	/**
	* 使用另外一个 RIFeeRateTableSchema 对象给 Schema 赋值
	* @param: aRIFeeRateTableSchema RIFeeRateTableSchema
	**/
	public void setSchema(RIFeeRateTableSchema aRIFeeRateTableSchema)
	{
		this.FeeTableNo = aRIFeeRateTableSchema.getFeeTableNo();
		this.FeeTableName = aRIFeeRateTableSchema.getFeeTableName();
		this.BatchNo = aRIFeeRateTableSchema.getBatchNo();
		this.NumOne = aRIFeeRateTableSchema.getNumOne();
		this.NumTwo = aRIFeeRateTableSchema.getNumTwo();
		this.NumThree = aRIFeeRateTableSchema.getNumThree();
		this.NumFour = aRIFeeRateTableSchema.getNumFour();
		this.NumFive = aRIFeeRateTableSchema.getNumFive();
		this.NumSix = aRIFeeRateTableSchema.getNumSix();
		this.NumSeven = aRIFeeRateTableSchema.getNumSeven();
		this.NumEight = aRIFeeRateTableSchema.getNumEight();
		this.NumNine = aRIFeeRateTableSchema.getNumNine();
		this.NumTen = aRIFeeRateTableSchema.getNumTen();
		this.StrOne = aRIFeeRateTableSchema.getStrOne();
		this.StrTwo = aRIFeeRateTableSchema.getStrTwo();
		this.StrThree = aRIFeeRateTableSchema.getStrThree();
		this.StrFour = aRIFeeRateTableSchema.getStrFour();
		this.StrFive = aRIFeeRateTableSchema.getStrFive();
		this.FeeRate = aRIFeeRateTableSchema.getFeeRate();
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
			if( rs.getString("FeeTableNo") == null )
				this.FeeTableNo = null;
			else
				this.FeeTableNo = rs.getString("FeeTableNo").trim();

			if( rs.getString("FeeTableName") == null )
				this.FeeTableName = null;
			else
				this.FeeTableName = rs.getString("FeeTableName").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			this.NumOne = rs.getDouble("NumOne");
			this.NumTwo = rs.getDouble("NumTwo");
			this.NumThree = rs.getDouble("NumThree");
			this.NumFour = rs.getDouble("NumFour");
			this.NumFive = rs.getDouble("NumFive");
			this.NumSix = rs.getDouble("NumSix");
			this.NumSeven = rs.getDouble("NumSeven");
			this.NumEight = rs.getDouble("NumEight");
			this.NumNine = rs.getDouble("NumNine");
			this.NumTen = rs.getDouble("NumTen");
			if( rs.getString("StrOne") == null )
				this.StrOne = null;
			else
				this.StrOne = rs.getString("StrOne").trim();

			if( rs.getString("StrTwo") == null )
				this.StrTwo = null;
			else
				this.StrTwo = rs.getString("StrTwo").trim();

			if( rs.getString("StrThree") == null )
				this.StrThree = null;
			else
				this.StrThree = rs.getString("StrThree").trim();

			if( rs.getString("StrFour") == null )
				this.StrFour = null;
			else
				this.StrFour = rs.getString("StrFour").trim();

			if( rs.getString("StrFive") == null )
				this.StrFive = null;
			else
				this.StrFive = rs.getString("StrFive").trim();

			this.FeeRate = rs.getDouble("FeeRate");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIFeeRateTable表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIFeeRateTableSchema getSchema()
	{
		RIFeeRateTableSchema aRIFeeRateTableSchema = new RIFeeRateTableSchema();
		aRIFeeRateTableSchema.setSchema(this);
		return aRIFeeRateTableSchema;
	}

	public RIFeeRateTableDB getDB()
	{
		RIFeeRateTableDB aDBOper = new RIFeeRateTableDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTable描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumOne));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumTwo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumThree));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumFour));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumFive));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumSix));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumSeven));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumEight));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumNine));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumTen));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StrOne)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StrTwo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StrThree)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StrFour)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StrFive)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTable>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NumOne = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			NumTwo = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			NumThree = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			NumFour = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			NumFive = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			NumSix = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			NumSeven = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			NumEight = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			NumNine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			NumTen = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			StrOne = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StrTwo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StrThree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StrFour = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StrFive = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableSchema";
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
		if (FCode.equalsIgnoreCase("FeeTableNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableNo));
		}
		if (FCode.equalsIgnoreCase("FeeTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableName));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("NumOne"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumOne));
		}
		if (FCode.equalsIgnoreCase("NumTwo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumTwo));
		}
		if (FCode.equalsIgnoreCase("NumThree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumThree));
		}
		if (FCode.equalsIgnoreCase("NumFour"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumFour));
		}
		if (FCode.equalsIgnoreCase("NumFive"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumFive));
		}
		if (FCode.equalsIgnoreCase("NumSix"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumSix));
		}
		if (FCode.equalsIgnoreCase("NumSeven"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumSeven));
		}
		if (FCode.equalsIgnoreCase("NumEight"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumEight));
		}
		if (FCode.equalsIgnoreCase("NumNine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumNine));
		}
		if (FCode.equalsIgnoreCase("NumTen"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumTen));
		}
		if (FCode.equalsIgnoreCase("StrOne"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StrOne));
		}
		if (FCode.equalsIgnoreCase("StrTwo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StrTwo));
		}
		if (FCode.equalsIgnoreCase("StrThree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StrThree));
		}
		if (FCode.equalsIgnoreCase("StrFour"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StrFour));
		}
		if (FCode.equalsIgnoreCase("StrFive"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StrFive));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
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
				strFieldValue = StrTool.GBKToUnicode(FeeTableNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeTableName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 3:
				strFieldValue = String.valueOf(NumOne);
				break;
			case 4:
				strFieldValue = String.valueOf(NumTwo);
				break;
			case 5:
				strFieldValue = String.valueOf(NumThree);
				break;
			case 6:
				strFieldValue = String.valueOf(NumFour);
				break;
			case 7:
				strFieldValue = String.valueOf(NumFive);
				break;
			case 8:
				strFieldValue = String.valueOf(NumSix);
				break;
			case 9:
				strFieldValue = String.valueOf(NumSeven);
				break;
			case 10:
				strFieldValue = String.valueOf(NumEight);
				break;
			case 11:
				strFieldValue = String.valueOf(NumNine);
				break;
			case 12:
				strFieldValue = String.valueOf(NumTen);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StrOne);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StrTwo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StrThree);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StrFour);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StrFive);
				break;
			case 18:
				strFieldValue = String.valueOf(FeeRate);
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

		if (FCode.equalsIgnoreCase("FeeTableNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableNo = FValue.trim();
			}
			else
				FeeTableNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableName = FValue.trim();
			}
			else
				FeeTableName = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("NumOne"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumOne = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumTwo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumTwo = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumThree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumThree = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumFour"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumFour = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumFive"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumFive = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumSix"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumSix = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumSeven"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumSeven = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumEight"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumEight = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumNine"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumNine = d;
			}
		}
		if (FCode.equalsIgnoreCase("NumTen"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NumTen = d;
			}
		}
		if (FCode.equalsIgnoreCase("StrOne"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StrOne = FValue.trim();
			}
			else
				StrOne = null;
		}
		if (FCode.equalsIgnoreCase("StrTwo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StrTwo = FValue.trim();
			}
			else
				StrTwo = null;
		}
		if (FCode.equalsIgnoreCase("StrThree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StrThree = FValue.trim();
			}
			else
				StrThree = null;
		}
		if (FCode.equalsIgnoreCase("StrFour"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StrFour = FValue.trim();
			}
			else
				StrFour = null;
		}
		if (FCode.equalsIgnoreCase("StrFive"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StrFive = FValue.trim();
			}
			else
				StrFive = null;
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIFeeRateTableSchema other = (RIFeeRateTableSchema)otherObject;
		return
			FeeTableNo.equals(other.getFeeTableNo())
			&& FeeTableName.equals(other.getFeeTableName())
			&& BatchNo.equals(other.getBatchNo())
			&& NumOne == other.getNumOne()
			&& NumTwo == other.getNumTwo()
			&& NumThree == other.getNumThree()
			&& NumFour == other.getNumFour()
			&& NumFive == other.getNumFive()
			&& NumSix == other.getNumSix()
			&& NumSeven == other.getNumSeven()
			&& NumEight == other.getNumEight()
			&& NumNine == other.getNumNine()
			&& NumTen == other.getNumTen()
			&& StrOne.equals(other.getStrOne())
			&& StrTwo.equals(other.getStrTwo())
			&& StrThree.equals(other.getStrThree())
			&& StrFour.equals(other.getStrFour())
			&& StrFive.equals(other.getStrFive())
			&& FeeRate == other.getFeeRate();
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
		if( strFieldName.equals("FeeTableNo") ) {
			return 0;
		}
		if( strFieldName.equals("FeeTableName") ) {
			return 1;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 2;
		}
		if( strFieldName.equals("NumOne") ) {
			return 3;
		}
		if( strFieldName.equals("NumTwo") ) {
			return 4;
		}
		if( strFieldName.equals("NumThree") ) {
			return 5;
		}
		if( strFieldName.equals("NumFour") ) {
			return 6;
		}
		if( strFieldName.equals("NumFive") ) {
			return 7;
		}
		if( strFieldName.equals("NumSix") ) {
			return 8;
		}
		if( strFieldName.equals("NumSeven") ) {
			return 9;
		}
		if( strFieldName.equals("NumEight") ) {
			return 10;
		}
		if( strFieldName.equals("NumNine") ) {
			return 11;
		}
		if( strFieldName.equals("NumTen") ) {
			return 12;
		}
		if( strFieldName.equals("StrOne") ) {
			return 13;
		}
		if( strFieldName.equals("StrTwo") ) {
			return 14;
		}
		if( strFieldName.equals("StrThree") ) {
			return 15;
		}
		if( strFieldName.equals("StrFour") ) {
			return 16;
		}
		if( strFieldName.equals("StrFive") ) {
			return 17;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 18;
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
				strFieldName = "FeeTableNo";
				break;
			case 1:
				strFieldName = "FeeTableName";
				break;
			case 2:
				strFieldName = "BatchNo";
				break;
			case 3:
				strFieldName = "NumOne";
				break;
			case 4:
				strFieldName = "NumTwo";
				break;
			case 5:
				strFieldName = "NumThree";
				break;
			case 6:
				strFieldName = "NumFour";
				break;
			case 7:
				strFieldName = "NumFive";
				break;
			case 8:
				strFieldName = "NumSix";
				break;
			case 9:
				strFieldName = "NumSeven";
				break;
			case 10:
				strFieldName = "NumEight";
				break;
			case 11:
				strFieldName = "NumNine";
				break;
			case 12:
				strFieldName = "NumTen";
				break;
			case 13:
				strFieldName = "StrOne";
				break;
			case 14:
				strFieldName = "StrTwo";
				break;
			case 15:
				strFieldName = "StrThree";
				break;
			case 16:
				strFieldName = "StrFour";
				break;
			case 17:
				strFieldName = "StrFive";
				break;
			case 18:
				strFieldName = "FeeRate";
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
		if( strFieldName.equals("FeeTableNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NumOne") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumTwo") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumThree") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumFour") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumFive") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumSix") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumSeven") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumEight") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumNine") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NumTen") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StrOne") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StrTwo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StrThree") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StrFour") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StrFive") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeRate") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
