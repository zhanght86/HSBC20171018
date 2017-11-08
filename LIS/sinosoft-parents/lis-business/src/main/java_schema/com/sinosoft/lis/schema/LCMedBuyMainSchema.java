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
import com.sinosoft.lis.db.LCMedBuyMainDB;

/*
 * <p>ClassName: LCMedBuyMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCMedBuyMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCMedBuyMainSchema.class);
	// @Field
	/** 购药批次号 */
	private String BatchNo;
	/** 医疗卡号 */
	private String MedCardNo;
	/** 药品分类1 */
	private String MedType1;
	/** 药品消费总金额 */
	private double SumPayPrice;
	/** 药品报销总金额 */
	private double SumClmPrice;
	/** 生成日期 */
	private Date MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 备注 */
	private String Memo;
	/** 备用字段1 */
	private String StandByFlag1;
	/** 备用字段2 */
	private String StandByFlag2;
	/** 赔案生成标志 */
	private String ClmFlag;
	/** 赔案号 */
	private String ClmNo;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCMedBuyMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BatchNo";
		pk[1] = "MedCardNo";
		pk[2] = "MedType1";

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
		LCMedBuyMainSchema cloned = (LCMedBuyMainSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		if(aBatchNo!=null && aBatchNo.length()>40)
			throw new IllegalArgumentException("购药批次号BatchNo值"+aBatchNo+"的长度"+aBatchNo.length()+"大于最大值40");
		BatchNo = aBatchNo;
	}
	public String getMedCardNo()
	{
		return MedCardNo;
	}
	public void setMedCardNo(String aMedCardNo)
	{
		if(aMedCardNo!=null && aMedCardNo.length()>40)
			throw new IllegalArgumentException("医疗卡号MedCardNo值"+aMedCardNo+"的长度"+aMedCardNo.length()+"大于最大值40");
		MedCardNo = aMedCardNo;
	}
	public String getMedType1()
	{
		return MedType1;
	}
	public void setMedType1(String aMedType1)
	{
		if(aMedType1!=null && aMedType1.length()>8)
			throw new IllegalArgumentException("药品分类1MedType1值"+aMedType1+"的长度"+aMedType1.length()+"大于最大值8");
		MedType1 = aMedType1;
	}
	public double getSumPayPrice()
	{
		return SumPayPrice;
	}
	public void setSumPayPrice(double aSumPayPrice)
	{
		SumPayPrice = aSumPayPrice;
	}
	public void setSumPayPrice(String aSumPayPrice)
	{
		if (aSumPayPrice != null && !aSumPayPrice.equals(""))
		{
			Double tDouble = new Double(aSumPayPrice);
			double d = tDouble.doubleValue();
			SumPayPrice = d;
		}
	}

	public double getSumClmPrice()
	{
		return SumClmPrice;
	}
	public void setSumClmPrice(double aSumClmPrice)
	{
		SumClmPrice = aSumClmPrice;
	}
	public void setSumClmPrice(String aSumClmPrice)
	{
		if (aSumClmPrice != null && !aSumClmPrice.equals(""))
		{
			Double tDouble = new Double(aSumClmPrice);
			double d = tDouble.doubleValue();
			SumClmPrice = d;
		}
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
		if(aMakeTime!=null && aMakeTime.length()>20)
			throw new IllegalArgumentException("生成时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值20");
		MakeTime = aMakeTime;
	}
	public String getMemo()
	{
		return Memo;
	}
	public void setMemo(String aMemo)
	{
		if(aMemo!=null && aMemo.length()>100)
			throw new IllegalArgumentException("备注Memo值"+aMemo+"的长度"+aMemo.length()+"大于最大值100");
		Memo = aMemo;
	}
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		if(aStandByFlag1!=null && aStandByFlag1.length()>1)
			throw new IllegalArgumentException("备用字段1StandByFlag1值"+aStandByFlag1+"的长度"+aStandByFlag1.length()+"大于最大值1");
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		if(aStandByFlag2!=null && aStandByFlag2.length()>1)
			throw new IllegalArgumentException("备用字段2StandByFlag2值"+aStandByFlag2+"的长度"+aStandByFlag2.length()+"大于最大值1");
		StandByFlag2 = aStandByFlag2;
	}
	/**
	* 0--未生成 1--已生成
	*/
	public String getClmFlag()
	{
		return ClmFlag;
	}
	public void setClmFlag(String aClmFlag)
	{
		if(aClmFlag!=null && aClmFlag.length()>1)
			throw new IllegalArgumentException("赔案生成标志ClmFlag值"+aClmFlag+"的长度"+aClmFlag.length()+"大于最大值1");
		ClmFlag = aClmFlag;
	}
	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}

	/**
	* 使用另外一个 LCMedBuyMainSchema 对象给 Schema 赋值
	* @param: aLCMedBuyMainSchema LCMedBuyMainSchema
	**/
	public void setSchema(LCMedBuyMainSchema aLCMedBuyMainSchema)
	{
		this.BatchNo = aLCMedBuyMainSchema.getBatchNo();
		this.MedCardNo = aLCMedBuyMainSchema.getMedCardNo();
		this.MedType1 = aLCMedBuyMainSchema.getMedType1();
		this.SumPayPrice = aLCMedBuyMainSchema.getSumPayPrice();
		this.SumClmPrice = aLCMedBuyMainSchema.getSumClmPrice();
		this.MakeDate = fDate.getDate( aLCMedBuyMainSchema.getMakeDate());
		this.MakeTime = aLCMedBuyMainSchema.getMakeTime();
		this.Memo = aLCMedBuyMainSchema.getMemo();
		this.StandByFlag1 = aLCMedBuyMainSchema.getStandByFlag1();
		this.StandByFlag2 = aLCMedBuyMainSchema.getStandByFlag2();
		this.ClmFlag = aLCMedBuyMainSchema.getClmFlag();
		this.ClmNo = aLCMedBuyMainSchema.getClmNo();
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
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("MedCardNo") == null )
				this.MedCardNo = null;
			else
				this.MedCardNo = rs.getString("MedCardNo").trim();

			if( rs.getString("MedType1") == null )
				this.MedType1 = null;
			else
				this.MedType1 = rs.getString("MedType1").trim();

			this.SumPayPrice = rs.getDouble("SumPayPrice");
			this.SumClmPrice = rs.getDouble("SumClmPrice");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Memo") == null )
				this.Memo = null;
			else
				this.Memo = rs.getString("Memo").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("ClmFlag") == null )
				this.ClmFlag = null;
			else
				this.ClmFlag = rs.getString("ClmFlag").trim();

			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCMedBuyMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCMedBuyMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCMedBuyMainSchema getSchema()
	{
		LCMedBuyMainSchema aLCMedBuyMainSchema = new LCMedBuyMainSchema();
		aLCMedBuyMainSchema.setSchema(this);
		return aLCMedBuyMainSchema;
	}

	public LCMedBuyMainDB getDB()
	{
		LCMedBuyMainDB aDBOper = new LCMedBuyMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCMedBuyMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedCardNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPayPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumClmPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Memo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCMedBuyMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MedCardNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MedType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SumPayPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			SumClmPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Memo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ClmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCMedBuyMainSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("MedCardNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedCardNo));
		}
		if (FCode.equalsIgnoreCase("MedType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedType1));
		}
		if (FCode.equalsIgnoreCase("SumPayPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPayPrice));
		}
		if (FCode.equalsIgnoreCase("SumClmPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumClmPrice));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Memo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Memo));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmFlag));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MedCardNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MedType1);
				break;
			case 3:
				strFieldValue = String.valueOf(SumPayPrice);
				break;
			case 4:
				strFieldValue = String.valueOf(SumClmPrice);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Memo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ClmFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
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

		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("MedCardNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedCardNo = FValue.trim();
			}
			else
				MedCardNo = null;
		}
		if (FCode.equalsIgnoreCase("MedType1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedType1 = FValue.trim();
			}
			else
				MedType1 = null;
		}
		if (FCode.equalsIgnoreCase("SumPayPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPayPrice = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumClmPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumClmPrice = d;
			}
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
		if (FCode.equalsIgnoreCase("Memo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Memo = FValue.trim();
			}
			else
				Memo = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag1 = FValue.trim();
			}
			else
				StandByFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag2 = FValue.trim();
			}
			else
				StandByFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("ClmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmFlag = FValue.trim();
			}
			else
				ClmFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCMedBuyMainSchema other = (LCMedBuyMainSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& MedCardNo.equals(other.getMedCardNo())
			&& MedType1.equals(other.getMedType1())
			&& SumPayPrice == other.getSumPayPrice()
			&& SumClmPrice == other.getSumClmPrice()
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Memo.equals(other.getMemo())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& ClmFlag.equals(other.getClmFlag())
			&& ClmNo.equals(other.getClmNo());
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
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("MedCardNo") ) {
			return 1;
		}
		if( strFieldName.equals("MedType1") ) {
			return 2;
		}
		if( strFieldName.equals("SumPayPrice") ) {
			return 3;
		}
		if( strFieldName.equals("SumClmPrice") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("Memo") ) {
			return 7;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 8;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 9;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ClmNo") ) {
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
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "MedCardNo";
				break;
			case 2:
				strFieldName = "MedType1";
				break;
			case 3:
				strFieldName = "SumPayPrice";
				break;
			case 4:
				strFieldName = "SumClmPrice";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "Memo";
				break;
			case 8:
				strFieldName = "StandByFlag1";
				break;
			case 9:
				strFieldName = "StandByFlag2";
				break;
			case 10:
				strFieldName = "ClmFlag";
				break;
			case 11:
				strFieldName = "ClmNo";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedCardNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedType1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumPayPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumClmPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Memo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmNo") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
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
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
