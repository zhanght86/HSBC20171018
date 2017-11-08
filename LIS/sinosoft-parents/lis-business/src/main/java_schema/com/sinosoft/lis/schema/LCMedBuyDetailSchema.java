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
import com.sinosoft.lis.db.LCMedBuyDetailDB;

/*
 * <p>ClassName: LCMedBuyDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCMedBuyDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCMedBuyDetailSchema.class);
	// @Field
	/** 购药批次号 */
	private String BatchNo;
	/** 药品流水号 */
	private String SeqNo;
	/** 医疗卡号 */
	private String MedCardNo;
	/** 药品名称 */
	private String MedName;
	/** 药品分类1 */
	private String MedType1;
	/** 药品分类2 */
	private String MedType2;
	/** 药品分类3 */
	private String MedType3;
	/** 药品数量 */
	private double Quanity;
	/** 药品单价 */
	private double UnitPrice;
	/** 药品总金额 */
	private double SumPrice;
	/** 可报销标示 */
	private String BXFlag;
	/** 生成日期 */
	private Date MakeDate;
	/** 生成时间 */
	private String MakeTime;
	/** 药店代码 */
	private String MedShopCode;
	/** 交易柜台号 */
	private String ConterNo;
	/** 备注 */
	private String Memo;
	/** 备用字段1 */
	private String StandByFlag1;
	/** 备用字段2 */
	private String StandByFlag2;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCMedBuyDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BatchNo";
		pk[1] = "SeqNo";
		pk[2] = "MedCardNo";

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
		LCMedBuyDetailSchema cloned = (LCMedBuyDetailSchema)super.clone();
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
	public String getSeqNo()
	{
		return SeqNo;
	}
	public void setSeqNo(String aSeqNo)
	{
		if(aSeqNo!=null && aSeqNo.length()>40)
			throw new IllegalArgumentException("药品流水号SeqNo值"+aSeqNo+"的长度"+aSeqNo.length()+"大于最大值40");
		SeqNo = aSeqNo;
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
	public String getMedName()
	{
		return MedName;
	}
	public void setMedName(String aMedName)
	{
		if(aMedName!=null && aMedName.length()>100)
			throw new IllegalArgumentException("药品名称MedName值"+aMedName+"的长度"+aMedName.length()+"大于最大值100");
		MedName = aMedName;
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
	public String getMedType2()
	{
		return MedType2;
	}
	public void setMedType2(String aMedType2)
	{
		if(aMedType2!=null && aMedType2.length()>30)
			throw new IllegalArgumentException("药品分类2MedType2值"+aMedType2+"的长度"+aMedType2.length()+"大于最大值30");
		MedType2 = aMedType2;
	}
	public String getMedType3()
	{
		return MedType3;
	}
	public void setMedType3(String aMedType3)
	{
		if(aMedType3!=null && aMedType3.length()>4)
			throw new IllegalArgumentException("药品分类3MedType3值"+aMedType3+"的长度"+aMedType3.length()+"大于最大值4");
		MedType3 = aMedType3;
	}
	public double getQuanity()
	{
		return Quanity;
	}
	public void setQuanity(double aQuanity)
	{
		Quanity = aQuanity;
	}
	public void setQuanity(String aQuanity)
	{
		if (aQuanity != null && !aQuanity.equals(""))
		{
			Double tDouble = new Double(aQuanity);
			double d = tDouble.doubleValue();
			Quanity = d;
		}
	}

	public double getUnitPrice()
	{
		return UnitPrice;
	}
	public void setUnitPrice(double aUnitPrice)
	{
		UnitPrice = aUnitPrice;
	}
	public void setUnitPrice(String aUnitPrice)
	{
		if (aUnitPrice != null && !aUnitPrice.equals(""))
		{
			Double tDouble = new Double(aUnitPrice);
			double d = tDouble.doubleValue();
			UnitPrice = d;
		}
	}

	public double getSumPrice()
	{
		return SumPrice;
	}
	public void setSumPrice(double aSumPrice)
	{
		SumPrice = aSumPrice;
	}
	public void setSumPrice(String aSumPrice)
	{
		if (aSumPrice != null && !aSumPrice.equals(""))
		{
			Double tDouble = new Double(aSumPrice);
			double d = tDouble.doubleValue();
			SumPrice = d;
		}
	}

	public String getBXFlag()
	{
		return BXFlag;
	}
	public void setBXFlag(String aBXFlag)
	{
		if(aBXFlag!=null && aBXFlag.length()>1)
			throw new IllegalArgumentException("可报销标示BXFlag值"+aBXFlag+"的长度"+aBXFlag.length()+"大于最大值1");
		BXFlag = aBXFlag;
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
	public String getMedShopCode()
	{
		return MedShopCode;
	}
	public void setMedShopCode(String aMedShopCode)
	{
		if(aMedShopCode!=null && aMedShopCode.length()>20)
			throw new IllegalArgumentException("药店代码MedShopCode值"+aMedShopCode+"的长度"+aMedShopCode.length()+"大于最大值20");
		MedShopCode = aMedShopCode;
	}
	public String getConterNo()
	{
		return ConterNo;
	}
	public void setConterNo(String aConterNo)
	{
		if(aConterNo!=null && aConterNo.length()>20)
			throw new IllegalArgumentException("交易柜台号ConterNo值"+aConterNo+"的长度"+aConterNo.length()+"大于最大值20");
		ConterNo = aConterNo;
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
	* 使用另外一个 LCMedBuyDetailSchema 对象给 Schema 赋值
	* @param: aLCMedBuyDetailSchema LCMedBuyDetailSchema
	**/
	public void setSchema(LCMedBuyDetailSchema aLCMedBuyDetailSchema)
	{
		this.BatchNo = aLCMedBuyDetailSchema.getBatchNo();
		this.SeqNo = aLCMedBuyDetailSchema.getSeqNo();
		this.MedCardNo = aLCMedBuyDetailSchema.getMedCardNo();
		this.MedName = aLCMedBuyDetailSchema.getMedName();
		this.MedType1 = aLCMedBuyDetailSchema.getMedType1();
		this.MedType2 = aLCMedBuyDetailSchema.getMedType2();
		this.MedType3 = aLCMedBuyDetailSchema.getMedType3();
		this.Quanity = aLCMedBuyDetailSchema.getQuanity();
		this.UnitPrice = aLCMedBuyDetailSchema.getUnitPrice();
		this.SumPrice = aLCMedBuyDetailSchema.getSumPrice();
		this.BXFlag = aLCMedBuyDetailSchema.getBXFlag();
		this.MakeDate = fDate.getDate( aLCMedBuyDetailSchema.getMakeDate());
		this.MakeTime = aLCMedBuyDetailSchema.getMakeTime();
		this.MedShopCode = aLCMedBuyDetailSchema.getMedShopCode();
		this.ConterNo = aLCMedBuyDetailSchema.getConterNo();
		this.Memo = aLCMedBuyDetailSchema.getMemo();
		this.StandByFlag1 = aLCMedBuyDetailSchema.getStandByFlag1();
		this.StandByFlag2 = aLCMedBuyDetailSchema.getStandByFlag2();
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

			if( rs.getString("SeqNo") == null )
				this.SeqNo = null;
			else
				this.SeqNo = rs.getString("SeqNo").trim();

			if( rs.getString("MedCardNo") == null )
				this.MedCardNo = null;
			else
				this.MedCardNo = rs.getString("MedCardNo").trim();

			if( rs.getString("MedName") == null )
				this.MedName = null;
			else
				this.MedName = rs.getString("MedName").trim();

			if( rs.getString("MedType1") == null )
				this.MedType1 = null;
			else
				this.MedType1 = rs.getString("MedType1").trim();

			if( rs.getString("MedType2") == null )
				this.MedType2 = null;
			else
				this.MedType2 = rs.getString("MedType2").trim();

			if( rs.getString("MedType3") == null )
				this.MedType3 = null;
			else
				this.MedType3 = rs.getString("MedType3").trim();

			this.Quanity = rs.getDouble("Quanity");
			this.UnitPrice = rs.getDouble("UnitPrice");
			this.SumPrice = rs.getDouble("SumPrice");
			if( rs.getString("BXFlag") == null )
				this.BXFlag = null;
			else
				this.BXFlag = rs.getString("BXFlag").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("MedShopCode") == null )
				this.MedShopCode = null;
			else
				this.MedShopCode = rs.getString("MedShopCode").trim();

			if( rs.getString("ConterNo") == null )
				this.ConterNo = null;
			else
				this.ConterNo = rs.getString("ConterNo").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCMedBuyDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCMedBuyDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCMedBuyDetailSchema getSchema()
	{
		LCMedBuyDetailSchema aLCMedBuyDetailSchema = new LCMedBuyDetailSchema();
		aLCMedBuyDetailSchema.setSchema(this);
		return aLCMedBuyDetailSchema;
	}

	public LCMedBuyDetailDB getDB()
	{
		LCMedBuyDetailDB aDBOper = new LCMedBuyDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCMedBuyDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SeqNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedCardNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedType2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedType3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Quanity));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UnitPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BXFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MedShopCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConterNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Memo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCMedBuyDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SeqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MedCardNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MedName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MedType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MedType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MedType3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Quanity = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			UnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			BXFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MedShopCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ConterNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Memo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCMedBuyDetailSchema";
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
		if (FCode.equalsIgnoreCase("SeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeqNo));
		}
		if (FCode.equalsIgnoreCase("MedCardNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedCardNo));
		}
		if (FCode.equalsIgnoreCase("MedName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedName));
		}
		if (FCode.equalsIgnoreCase("MedType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedType1));
		}
		if (FCode.equalsIgnoreCase("MedType2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedType2));
		}
		if (FCode.equalsIgnoreCase("MedType3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedType3));
		}
		if (FCode.equalsIgnoreCase("Quanity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Quanity));
		}
		if (FCode.equalsIgnoreCase("UnitPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitPrice));
		}
		if (FCode.equalsIgnoreCase("SumPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrice));
		}
		if (FCode.equalsIgnoreCase("BXFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BXFlag));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MedShopCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedShopCode));
		}
		if (FCode.equalsIgnoreCase("ConterNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConterNo));
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
				strFieldValue = StrTool.GBKToUnicode(SeqNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MedCardNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MedName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MedType1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MedType2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MedType3);
				break;
			case 7:
				strFieldValue = String.valueOf(Quanity);
				break;
			case 8:
				strFieldValue = String.valueOf(UnitPrice);
				break;
			case 9:
				strFieldValue = String.valueOf(SumPrice);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BXFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MedShopCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ConterNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Memo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
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
		if (FCode.equalsIgnoreCase("SeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SeqNo = FValue.trim();
			}
			else
				SeqNo = null;
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
		if (FCode.equalsIgnoreCase("MedName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedName = FValue.trim();
			}
			else
				MedName = null;
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
		if (FCode.equalsIgnoreCase("MedType2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedType2 = FValue.trim();
			}
			else
				MedType2 = null;
		}
		if (FCode.equalsIgnoreCase("MedType3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedType3 = FValue.trim();
			}
			else
				MedType3 = null;
		}
		if (FCode.equalsIgnoreCase("Quanity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Quanity = d;
			}
		}
		if (FCode.equalsIgnoreCase("UnitPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnitPrice = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrice = d;
			}
		}
		if (FCode.equalsIgnoreCase("BXFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BXFlag = FValue.trim();
			}
			else
				BXFlag = null;
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
		if (FCode.equalsIgnoreCase("MedShopCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MedShopCode = FValue.trim();
			}
			else
				MedShopCode = null;
		}
		if (FCode.equalsIgnoreCase("ConterNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConterNo = FValue.trim();
			}
			else
				ConterNo = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCMedBuyDetailSchema other = (LCMedBuyDetailSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& SeqNo.equals(other.getSeqNo())
			&& MedCardNo.equals(other.getMedCardNo())
			&& MedName.equals(other.getMedName())
			&& MedType1.equals(other.getMedType1())
			&& MedType2.equals(other.getMedType2())
			&& MedType3.equals(other.getMedType3())
			&& Quanity == other.getQuanity()
			&& UnitPrice == other.getUnitPrice()
			&& SumPrice == other.getSumPrice()
			&& BXFlag.equals(other.getBXFlag())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& MedShopCode.equals(other.getMedShopCode())
			&& ConterNo.equals(other.getConterNo())
			&& Memo.equals(other.getMemo())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2());
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
		if( strFieldName.equals("SeqNo") ) {
			return 1;
		}
		if( strFieldName.equals("MedCardNo") ) {
			return 2;
		}
		if( strFieldName.equals("MedName") ) {
			return 3;
		}
		if( strFieldName.equals("MedType1") ) {
			return 4;
		}
		if( strFieldName.equals("MedType2") ) {
			return 5;
		}
		if( strFieldName.equals("MedType3") ) {
			return 6;
		}
		if( strFieldName.equals("Quanity") ) {
			return 7;
		}
		if( strFieldName.equals("UnitPrice") ) {
			return 8;
		}
		if( strFieldName.equals("SumPrice") ) {
			return 9;
		}
		if( strFieldName.equals("BXFlag") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("MedShopCode") ) {
			return 13;
		}
		if( strFieldName.equals("ConterNo") ) {
			return 14;
		}
		if( strFieldName.equals("Memo") ) {
			return 15;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 16;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 17;
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
				strFieldName = "SeqNo";
				break;
			case 2:
				strFieldName = "MedCardNo";
				break;
			case 3:
				strFieldName = "MedName";
				break;
			case 4:
				strFieldName = "MedType1";
				break;
			case 5:
				strFieldName = "MedType2";
				break;
			case 6:
				strFieldName = "MedType3";
				break;
			case 7:
				strFieldName = "Quanity";
				break;
			case 8:
				strFieldName = "UnitPrice";
				break;
			case 9:
				strFieldName = "SumPrice";
				break;
			case 10:
				strFieldName = "BXFlag";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "MedShopCode";
				break;
			case 14:
				strFieldName = "ConterNo";
				break;
			case 15:
				strFieldName = "Memo";
				break;
			case 16:
				strFieldName = "StandByFlag1";
				break;
			case 17:
				strFieldName = "StandByFlag2";
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
		if( strFieldName.equals("SeqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedCardNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedType1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedType2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedType3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Quanity") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UnitPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BXFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MedShopCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConterNo") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
