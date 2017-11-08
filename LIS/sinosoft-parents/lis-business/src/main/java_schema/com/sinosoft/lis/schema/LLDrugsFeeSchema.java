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
import com.sinosoft.lis.db.LLDrugsFeeDB;

/*
 * <p>ClassName: LLDrugsFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LLDrugsFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLDrugsFeeSchema.class);
	// @Field
	/** 药品流水号 */
	private String DrugsSerialNo;
	/** 地区编码 */
	private String AreaCode;
	/** 药品名称 */
	private String DrugsName;
	/** 商品名称 */
	private String CommodityName;
	/** 医保类型 */
	private String HealthCareType;
	/** 剂型 */
	private String DosageForms;
	/** 规格 */
	private String Specifications;
	/** 自付比例 */
	private String SelfRate;
	/** 价格 */
	private double Price;
	/** 限制内容 */
	private String LimitInfo;
	/** 更新日期 */
	private Date UpDataDate;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLDrugsFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "DrugsSerialNo";

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
		LLDrugsFeeSchema cloned = (LLDrugsFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDrugsSerialNo()
	{
		return DrugsSerialNo;
	}
	public void setDrugsSerialNo(String aDrugsSerialNo)
	{
		if(aDrugsSerialNo!=null && aDrugsSerialNo.length()>12)
			throw new IllegalArgumentException("药品流水号DrugsSerialNo值"+aDrugsSerialNo+"的长度"+aDrugsSerialNo.length()+"大于最大值12");
		DrugsSerialNo = aDrugsSerialNo;
	}
	public String getAreaCode()
	{
		return AreaCode;
	}
	public void setAreaCode(String aAreaCode)
	{
		if(aAreaCode!=null && aAreaCode.length()>12)
			throw new IllegalArgumentException("地区编码AreaCode值"+aAreaCode+"的长度"+aAreaCode.length()+"大于最大值12");
		AreaCode = aAreaCode;
	}
	public String getDrugsName()
	{
		return DrugsName;
	}
	public void setDrugsName(String aDrugsName)
	{
		if(aDrugsName!=null && aDrugsName.length()>100)
			throw new IllegalArgumentException("药品名称DrugsName值"+aDrugsName+"的长度"+aDrugsName.length()+"大于最大值100");
		DrugsName = aDrugsName;
	}
	public String getCommodityName()
	{
		return CommodityName;
	}
	public void setCommodityName(String aCommodityName)
	{
		if(aCommodityName!=null && aCommodityName.length()>100)
			throw new IllegalArgumentException("商品名称CommodityName值"+aCommodityName+"的长度"+aCommodityName.length()+"大于最大值100");
		CommodityName = aCommodityName;
	}
	public String getHealthCareType()
	{
		return HealthCareType;
	}
	public void setHealthCareType(String aHealthCareType)
	{
		if(aHealthCareType!=null && aHealthCareType.length()>12)
			throw new IllegalArgumentException("医保类型HealthCareType值"+aHealthCareType+"的长度"+aHealthCareType.length()+"大于最大值12");
		HealthCareType = aHealthCareType;
	}
	public String getDosageForms()
	{
		return DosageForms;
	}
	public void setDosageForms(String aDosageForms)
	{
		if(aDosageForms!=null && aDosageForms.length()>12)
			throw new IllegalArgumentException("剂型DosageForms值"+aDosageForms+"的长度"+aDosageForms.length()+"大于最大值12");
		DosageForms = aDosageForms;
	}
	public String getSpecifications()
	{
		return Specifications;
	}
	public void setSpecifications(String aSpecifications)
	{
		if(aSpecifications!=null && aSpecifications.length()>12)
			throw new IllegalArgumentException("规格Specifications值"+aSpecifications+"的长度"+aSpecifications.length()+"大于最大值12");
		Specifications = aSpecifications;
	}
	public String getSelfRate()
	{
		return SelfRate;
	}
	public void setSelfRate(String aSelfRate)
	{
		if(aSelfRate!=null && aSelfRate.length()>12)
			throw new IllegalArgumentException("自付比例SelfRate值"+aSelfRate+"的长度"+aSelfRate.length()+"大于最大值12");
		SelfRate = aSelfRate;
	}
	public double getPrice()
	{
		return Price;
	}
	public void setPrice(double aPrice)
	{
		Price = aPrice;
	}
	public void setPrice(String aPrice)
	{
		if (aPrice != null && !aPrice.equals(""))
		{
			Double tDouble = new Double(aPrice);
			double d = tDouble.doubleValue();
			Price = d;
		}
	}

	public String getLimitInfo()
	{
		return LimitInfo;
	}
	public void setLimitInfo(String aLimitInfo)
	{
		if(aLimitInfo!=null && aLimitInfo.length()>100)
			throw new IllegalArgumentException("限制内容LimitInfo值"+aLimitInfo+"的长度"+aLimitInfo.length()+"大于最大值100");
		LimitInfo = aLimitInfo;
	}
	public String getUpDataDate()
	{
		if( UpDataDate != null )
			return fDate.getString(UpDataDate);
		else
			return null;
	}
	public void setUpDataDate(Date aUpDataDate)
	{
		UpDataDate = aUpDataDate;
	}
	public void setUpDataDate(String aUpDataDate)
	{
		if (aUpDataDate != null && !aUpDataDate.equals("") )
		{
			UpDataDate = fDate.getDate( aUpDataDate );
		}
		else
			UpDataDate = null;
	}

	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LLDrugsFeeSchema 对象给 Schema 赋值
	* @param: aLLDrugsFeeSchema LLDrugsFeeSchema
	**/
	public void setSchema(LLDrugsFeeSchema aLLDrugsFeeSchema)
	{
		this.DrugsSerialNo = aLLDrugsFeeSchema.getDrugsSerialNo();
		this.AreaCode = aLLDrugsFeeSchema.getAreaCode();
		this.DrugsName = aLLDrugsFeeSchema.getDrugsName();
		this.CommodityName = aLLDrugsFeeSchema.getCommodityName();
		this.HealthCareType = aLLDrugsFeeSchema.getHealthCareType();
		this.DosageForms = aLLDrugsFeeSchema.getDosageForms();
		this.Specifications = aLLDrugsFeeSchema.getSpecifications();
		this.SelfRate = aLLDrugsFeeSchema.getSelfRate();
		this.Price = aLLDrugsFeeSchema.getPrice();
		this.LimitInfo = aLLDrugsFeeSchema.getLimitInfo();
		this.UpDataDate = fDate.getDate( aLLDrugsFeeSchema.getUpDataDate());
		this.ManageCom = aLLDrugsFeeSchema.getManageCom();
		this.ComCode = aLLDrugsFeeSchema.getComCode();
		this.MakeOperator = aLLDrugsFeeSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLDrugsFeeSchema.getMakeDate());
		this.MakeTime = aLLDrugsFeeSchema.getMakeTime();
		this.ModifyOperator = aLLDrugsFeeSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLDrugsFeeSchema.getModifyDate());
		this.ModifyTime = aLLDrugsFeeSchema.getModifyTime();
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
			if( rs.getString("DrugsSerialNo") == null )
				this.DrugsSerialNo = null;
			else
				this.DrugsSerialNo = rs.getString("DrugsSerialNo").trim();

			if( rs.getString("AreaCode") == null )
				this.AreaCode = null;
			else
				this.AreaCode = rs.getString("AreaCode").trim();

			if( rs.getString("DrugsName") == null )
				this.DrugsName = null;
			else
				this.DrugsName = rs.getString("DrugsName").trim();

			if( rs.getString("CommodityName") == null )
				this.CommodityName = null;
			else
				this.CommodityName = rs.getString("CommodityName").trim();

			if( rs.getString("HealthCareType") == null )
				this.HealthCareType = null;
			else
				this.HealthCareType = rs.getString("HealthCareType").trim();

			if( rs.getString("DosageForms") == null )
				this.DosageForms = null;
			else
				this.DosageForms = rs.getString("DosageForms").trim();

			if( rs.getString("Specifications") == null )
				this.Specifications = null;
			else
				this.Specifications = rs.getString("Specifications").trim();

			if( rs.getString("SelfRate") == null )
				this.SelfRate = null;
			else
				this.SelfRate = rs.getString("SelfRate").trim();

			this.Price = rs.getDouble("Price");
			if( rs.getString("LimitInfo") == null )
				this.LimitInfo = null;
			else
				this.LimitInfo = rs.getString("LimitInfo").trim();

			this.UpDataDate = rs.getDate("UpDataDate");
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLDrugsFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLDrugsFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLDrugsFeeSchema getSchema()
	{
		LLDrugsFeeSchema aLLDrugsFeeSchema = new LLDrugsFeeSchema();
		aLLDrugsFeeSchema.setSchema(this);
		return aLLDrugsFeeSchema;
	}

	public LLDrugsFeeDB getDB()
	{
		LLDrugsFeeDB aDBOper = new LLDrugsFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLDrugsFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DrugsSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrugsName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CommodityName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthCareType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DosageForms)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Specifications)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SelfRate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Price));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LimitInfo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UpDataDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLDrugsFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DrugsSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AreaCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DrugsName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CommodityName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			HealthCareType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DosageForms = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Specifications = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SelfRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Price = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			LimitInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UpDataDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLDrugsFeeSchema";
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
		if (FCode.equalsIgnoreCase("DrugsSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrugsSerialNo));
		}
		if (FCode.equalsIgnoreCase("AreaCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaCode));
		}
		if (FCode.equalsIgnoreCase("DrugsName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrugsName));
		}
		if (FCode.equalsIgnoreCase("CommodityName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommodityName));
		}
		if (FCode.equalsIgnoreCase("HealthCareType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthCareType));
		}
		if (FCode.equalsIgnoreCase("DosageForms"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DosageForms));
		}
		if (FCode.equalsIgnoreCase("Specifications"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Specifications));
		}
		if (FCode.equalsIgnoreCase("SelfRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SelfRate));
		}
		if (FCode.equalsIgnoreCase("Price"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Price));
		}
		if (FCode.equalsIgnoreCase("LimitInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LimitInfo));
		}
		if (FCode.equalsIgnoreCase("UpDataDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUpDataDate()));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(DrugsSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AreaCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DrugsName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CommodityName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(HealthCareType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DosageForms);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Specifications);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SelfRate);
				break;
			case 8:
				strFieldValue = String.valueOf(Price);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(LimitInfo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUpDataDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("DrugsSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrugsSerialNo = FValue.trim();
			}
			else
				DrugsSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("AreaCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaCode = FValue.trim();
			}
			else
				AreaCode = null;
		}
		if (FCode.equalsIgnoreCase("DrugsName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrugsName = FValue.trim();
			}
			else
				DrugsName = null;
		}
		if (FCode.equalsIgnoreCase("CommodityName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommodityName = FValue.trim();
			}
			else
				CommodityName = null;
		}
		if (FCode.equalsIgnoreCase("HealthCareType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthCareType = FValue.trim();
			}
			else
				HealthCareType = null;
		}
		if (FCode.equalsIgnoreCase("DosageForms"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DosageForms = FValue.trim();
			}
			else
				DosageForms = null;
		}
		if (FCode.equalsIgnoreCase("Specifications"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Specifications = FValue.trim();
			}
			else
				Specifications = null;
		}
		if (FCode.equalsIgnoreCase("SelfRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SelfRate = FValue.trim();
			}
			else
				SelfRate = null;
		}
		if (FCode.equalsIgnoreCase("Price"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Price = d;
			}
		}
		if (FCode.equalsIgnoreCase("LimitInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LimitInfo = FValue.trim();
			}
			else
				LimitInfo = null;
		}
		if (FCode.equalsIgnoreCase("UpDataDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UpDataDate = fDate.getDate( FValue );
			}
			else
				UpDataDate = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLDrugsFeeSchema other = (LLDrugsFeeSchema)otherObject;
		return
			DrugsSerialNo.equals(other.getDrugsSerialNo())
			&& AreaCode.equals(other.getAreaCode())
			&& DrugsName.equals(other.getDrugsName())
			&& CommodityName.equals(other.getCommodityName())
			&& HealthCareType.equals(other.getHealthCareType())
			&& DosageForms.equals(other.getDosageForms())
			&& Specifications.equals(other.getSpecifications())
			&& SelfRate.equals(other.getSelfRate())
			&& Price == other.getPrice()
			&& LimitInfo.equals(other.getLimitInfo())
			&& fDate.getString(UpDataDate).equals(other.getUpDataDate())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("DrugsSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("AreaCode") ) {
			return 1;
		}
		if( strFieldName.equals("DrugsName") ) {
			return 2;
		}
		if( strFieldName.equals("CommodityName") ) {
			return 3;
		}
		if( strFieldName.equals("HealthCareType") ) {
			return 4;
		}
		if( strFieldName.equals("DosageForms") ) {
			return 5;
		}
		if( strFieldName.equals("Specifications") ) {
			return 6;
		}
		if( strFieldName.equals("SelfRate") ) {
			return 7;
		}
		if( strFieldName.equals("Price") ) {
			return 8;
		}
		if( strFieldName.equals("LimitInfo") ) {
			return 9;
		}
		if( strFieldName.equals("UpDataDate") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("ComCode") ) {
			return 12;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "DrugsSerialNo";
				break;
			case 1:
				strFieldName = "AreaCode";
				break;
			case 2:
				strFieldName = "DrugsName";
				break;
			case 3:
				strFieldName = "CommodityName";
				break;
			case 4:
				strFieldName = "HealthCareType";
				break;
			case 5:
				strFieldName = "DosageForms";
				break;
			case 6:
				strFieldName = "Specifications";
				break;
			case 7:
				strFieldName = "SelfRate";
				break;
			case 8:
				strFieldName = "Price";
				break;
			case 9:
				strFieldName = "LimitInfo";
				break;
			case 10:
				strFieldName = "UpDataDate";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "ComCode";
				break;
			case 13:
				strFieldName = "MakeOperator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyOperator";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("DrugsSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrugsName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommodityName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthCareType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DosageForms") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Specifications") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SelfRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Price") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LimitInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpDataDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
