

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
import com.sinosoft.lis.db.InterfacePToSInfoDB;

/*
 * <p>ClassName: InterfacePToSInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class InterfacePToSInfoSchema implements Schema, Cloneable
{
	// @Field
	/** 产品编码 */
	private String ProductCode;
	/** 产品中文名 */
	private String ProductCHName;
	/** 产品英文名 */
	private String ProductENName;
	/** Plancode */
	private String IProductCode;
	/** Plan中文名 */
	private String IProductCHName;
	/** Plan英文名 */
	private String IProductENName;
	/** 是否和保单生效日期相关 */
	private String IsEffectiveDate;
	/** 开售日期 */
	private Date EffectiveDate;
	/** 停售日期 */
	private Date EffectiveEndDate;
	/** 是否与配偶共购相关 */
	private String IsSpouseCode;
	/** 是否配偶共购 */
	private String SpouseCode;
	/** 是否与员工单相关 */
	private String IsStaffCode;
	/** 是否员工单 */
	private String StaffCode;
	/** 是否与联保相关 */
	private String IsJoinCode;
	/** 是否联保 */
	private String JoinCode;
	/** 是否与缴费期间相关 */
	private String IsPremPayPeriod;
	/** 缴费期间单位 */
	private String PremPayPeriodType;
	/** 缴费期间 */
	private int PremPayPeriod;
	/** 是否与保障计划相关 */
	private String IsBenefitOption;
	/** 保障计划类型 */
	private String BenefitOptionType;
	/** 保障计划相关值 */
	private String BenefitOption;
	/** 是否与保险期间相关 */
	private String IsBenefitPeriod;
	/** 保险期间单位 */
	private String BenefitPeriodType;
	/** 保险期间 */
	private int BenefitPeriod;
	/** 是否与销售机构相关 */
	private String IsChannel;
	/** 销售机构 */
	private String Channel;
	/** 是否与币种相关 */
	private String IsCurrency;
	/** 币种 */
	private String Currency;
	/** 是否与分红类型相关 */
	private String IsPar;
	/** 分红标记 */
	private String Par;
	/** 是否与核保类型相关 */
	private String IsUreUWType;
	/** 核保类型 */
	private String UreUWType;
	/** Batchno */
	private int BatchNo;
	/** 操作员 */
	private String OPERATOR;
	/** 生成日期 */
	private Date MAKEDATE;
	/** 生成时间 */
	private String MAKETIME;
	/** 修改日期 */
	private Date MODIFYDATE;
	/** 修改时间 */
	private String MODIFYTIME;
	/** Standbyflag1 */
	private String StandByFlag1;
	/** Standbyflag2 */
	private String StandByFlag2;
	/** Standbyflag3 */
	private String StandByFlag3;

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public InterfacePToSInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[13];
		pk[0] = "ProductCode";
		pk[1] = "IProductCode";
		pk[2] = "IsEffectiveDate";
		pk[3] = "IsSpouseCode";
		pk[4] = "IsStaffCode";
		pk[5] = "IsJoinCode";
		pk[6] = "IsPremPayPeriod";
		pk[7] = "IsBenefitOption";
		pk[8] = "IsBenefitPeriod";
		pk[9] = "IsChannel";
		pk[10] = "IsCurrency";
		pk[11] = "IsPar";
		pk[12] = "IsUreUWType";

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
		InterfacePToSInfoSchema cloned = (InterfacePToSInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getProductCode()
	{
		return ProductCode;
	}
	public void setProductCode(String aProductCode)
	{
		ProductCode = aProductCode;
	}
	public String getProductCHName()
	{
		return ProductCHName;
	}
	public void setProductCHName(String aProductCHName)
	{
		ProductCHName = aProductCHName;
	}
	public String getProductENName()
	{
		return ProductENName;
	}
	public void setProductENName(String aProductENName)
	{
		ProductENName = aProductENName;
	}
	public String getIProductCode()
	{
		return IProductCode;
	}
	public void setIProductCode(String aIProductCode)
	{
		IProductCode = aIProductCode;
	}
	public String getIProductCHName()
	{
		return IProductCHName;
	}
	public void setIProductCHName(String aIProductCHName)
	{
		IProductCHName = aIProductCHName;
	}
	public String getIProductENName()
	{
		return IProductENName;
	}
	public void setIProductENName(String aIProductENName)
	{
		IProductENName = aIProductENName;
	}
	public String getIsEffectiveDate()
	{
		return IsEffectiveDate;
	}
	public void setIsEffectiveDate(String aIsEffectiveDate)
	{
		IsEffectiveDate = aIsEffectiveDate;
	}
	public String getEffectiveDate()
	{
		if( EffectiveDate != null )
			return fDate.getString(EffectiveDate);
		else
			return null;
	}
	public void setEffectiveDate(Date aEffectiveDate)
	{
		EffectiveDate = aEffectiveDate;
	}
	public void setEffectiveDate(String aEffectiveDate)
	{
		if (aEffectiveDate != null && !aEffectiveDate.equals("") )
		{
			EffectiveDate = fDate.getDate( aEffectiveDate );
		}
		else
			EffectiveDate = null;
	}

	public String getEffectiveEndDate()
	{
		if( EffectiveEndDate != null )
			return fDate.getString(EffectiveEndDate);
		else
			return null;
	}
	public void setEffectiveEndDate(Date aEffectiveEndDate)
	{
		EffectiveEndDate = aEffectiveEndDate;
	}
	public void setEffectiveEndDate(String aEffectiveEndDate)
	{
		if (aEffectiveEndDate != null && !aEffectiveEndDate.equals("") )
		{
			EffectiveEndDate = fDate.getDate( aEffectiveEndDate );
		}
		else
			EffectiveEndDate = null;
	}

	public String getIsSpouseCode()
	{
		return IsSpouseCode;
	}
	public void setIsSpouseCode(String aIsSpouseCode)
	{
		IsSpouseCode = aIsSpouseCode;
	}
	public String getSpouseCode()
	{
		return SpouseCode;
	}
	public void setSpouseCode(String aSpouseCode)
	{
		SpouseCode = aSpouseCode;
	}
	public String getIsStaffCode()
	{
		return IsStaffCode;
	}
	public void setIsStaffCode(String aIsStaffCode)
	{
		IsStaffCode = aIsStaffCode;
	}
	public String getStaffCode()
	{
		return StaffCode;
	}
	public void setStaffCode(String aStaffCode)
	{
		StaffCode = aStaffCode;
	}
	public String getIsJoinCode()
	{
		return IsJoinCode;
	}
	public void setIsJoinCode(String aIsJoinCode)
	{
		IsJoinCode = aIsJoinCode;
	}
	public String getJoinCode()
	{
		return JoinCode;
	}
	public void setJoinCode(String aJoinCode)
	{
		JoinCode = aJoinCode;
	}
	public String getIsPremPayPeriod()
	{
		return IsPremPayPeriod;
	}
	public void setIsPremPayPeriod(String aIsPremPayPeriod)
	{
		IsPremPayPeriod = aIsPremPayPeriod;
	}
	public String getPremPayPeriodType()
	{
		return PremPayPeriodType;
	}
	public void setPremPayPeriodType(String aPremPayPeriodType)
	{
		PremPayPeriodType = aPremPayPeriodType;
	}
	public int getPremPayPeriod()
	{
		return PremPayPeriod;
	}
	public void setPremPayPeriod(int aPremPayPeriod)
	{
		PremPayPeriod = aPremPayPeriod;
	}
	public void setPremPayPeriod(String aPremPayPeriod)
	{
		if (aPremPayPeriod != null && !aPremPayPeriod.equals(""))
		{
			Integer tInteger = new Integer(aPremPayPeriod);
			int i = tInteger.intValue();
			PremPayPeriod = i;
		}
	}

	public String getIsBenefitOption()
	{
		return IsBenefitOption;
	}
	public void setIsBenefitOption(String aIsBenefitOption)
	{
		IsBenefitOption = aIsBenefitOption;
	}
	public String getBenefitOptionType()
	{
		return BenefitOptionType;
	}
	public void setBenefitOptionType(String aBenefitOptionType)
	{
		BenefitOptionType = aBenefitOptionType;
	}
	public String getBenefitOption()
	{
		return BenefitOption;
	}
	public void setBenefitOption(String aBenefitOption)
	{
		BenefitOption = aBenefitOption;
	}
	public String getIsBenefitPeriod()
	{
		return IsBenefitPeriod;
	}
	public void setIsBenefitPeriod(String aIsBenefitPeriod)
	{
		IsBenefitPeriod = aIsBenefitPeriod;
	}
	public String getBenefitPeriodType()
	{
		return BenefitPeriodType;
	}
	public void setBenefitPeriodType(String aBenefitPeriodType)
	{
		BenefitPeriodType = aBenefitPeriodType;
	}
	public int getBenefitPeriod()
	{
		return BenefitPeriod;
	}
	public void setBenefitPeriod(int aBenefitPeriod)
	{
		BenefitPeriod = aBenefitPeriod;
	}
	public void setBenefitPeriod(String aBenefitPeriod)
	{
		if (aBenefitPeriod != null && !aBenefitPeriod.equals(""))
		{
			Integer tInteger = new Integer(aBenefitPeriod);
			int i = tInteger.intValue();
			BenefitPeriod = i;
		}
	}

	public String getIsChannel()
	{
		return IsChannel;
	}
	public void setIsChannel(String aIsChannel)
	{
		IsChannel = aIsChannel;
	}
	public String getChannel()
	{
		return Channel;
	}
	public void setChannel(String aChannel)
	{
		Channel = aChannel;
	}
	public String getIsCurrency()
	{
		return IsCurrency;
	}
	public void setIsCurrency(String aIsCurrency)
	{
		IsCurrency = aIsCurrency;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getIsPar()
	{
		return IsPar;
	}
	public void setIsPar(String aIsPar)
	{
		IsPar = aIsPar;
	}
	public String getPar()
	{
		return Par;
	}
	public void setPar(String aPar)
	{
		Par = aPar;
	}
	public String getIsUreUWType()
	{
		return IsUreUWType;
	}
	public void setIsUreUWType(String aIsUreUWType)
	{
		IsUreUWType = aIsUreUWType;
	}
	public String getUreUWType()
	{
		return UreUWType;
	}
	public void setUreUWType(String aUreUWType)
	{
		UreUWType = aUreUWType;
	}
	public int getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(int aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		if (aBatchNo != null && !aBatchNo.equals(""))
		{
			Integer tInteger = new Integer(aBatchNo);
			int i = tInteger.intValue();
			BatchNo = i;
		}
	}

	public String getOPERATOR()
	{
		return OPERATOR;
	}
	public void setOPERATOR(String aOPERATOR)
	{
		OPERATOR = aOPERATOR;
	}
	public String getMAKEDATE()
	{
		if( MAKEDATE != null )
			return fDate.getString(MAKEDATE);
		else
			return null;
	}
	public void setMAKEDATE(Date aMAKEDATE)
	{
		MAKEDATE = aMAKEDATE;
	}
	public void setMAKEDATE(String aMAKEDATE)
	{
		if (aMAKEDATE != null && !aMAKEDATE.equals("") )
		{
			MAKEDATE = fDate.getDate( aMAKEDATE );
		}
		else
			MAKEDATE = null;
	}

	public String getMAKETIME()
	{
		return MAKETIME;
	}
	public void setMAKETIME(String aMAKETIME)
	{
		MAKETIME = aMAKETIME;
	}
	public String getMODIFYDATE()
	{
		if( MODIFYDATE != null )
			return fDate.getString(MODIFYDATE);
		else
			return null;
	}
	public void setMODIFYDATE(Date aMODIFYDATE)
	{
		MODIFYDATE = aMODIFYDATE;
	}
	public void setMODIFYDATE(String aMODIFYDATE)
	{
		if (aMODIFYDATE != null && !aMODIFYDATE.equals("") )
		{
			MODIFYDATE = fDate.getDate( aMODIFYDATE );
		}
		else
			MODIFYDATE = null;
	}

	public String getMODIFYTIME()
	{
		return MODIFYTIME;
	}
	public void setMODIFYTIME(String aMODIFYTIME)
	{
		MODIFYTIME = aMODIFYTIME;
	}
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		StandByFlag2 = aStandByFlag2;
	}
	public String getStandByFlag3()
	{
		return StandByFlag3;
	}
	public void setStandByFlag3(String aStandByFlag3)
	{
		StandByFlag3 = aStandByFlag3;
	}

	/**
	* 使用另外一个 InterfacePToSInfoSchema 对象给 Schema 赋值
	* @param: aInterfacePToSInfoSchema InterfacePToSInfoSchema
	**/
	public void setSchema(InterfacePToSInfoSchema aInterfacePToSInfoSchema)
	{
		this.ProductCode = aInterfacePToSInfoSchema.getProductCode();
		this.ProductCHName = aInterfacePToSInfoSchema.getProductCHName();
		this.ProductENName = aInterfacePToSInfoSchema.getProductENName();
		this.IProductCode = aInterfacePToSInfoSchema.getIProductCode();
		this.IProductCHName = aInterfacePToSInfoSchema.getIProductCHName();
		this.IProductENName = aInterfacePToSInfoSchema.getIProductENName();
		this.IsEffectiveDate = aInterfacePToSInfoSchema.getIsEffectiveDate();
		this.EffectiveDate = fDate.getDate( aInterfacePToSInfoSchema.getEffectiveDate());
		this.EffectiveEndDate = fDate.getDate( aInterfacePToSInfoSchema.getEffectiveEndDate());
		this.IsSpouseCode = aInterfacePToSInfoSchema.getIsSpouseCode();
		this.SpouseCode = aInterfacePToSInfoSchema.getSpouseCode();
		this.IsStaffCode = aInterfacePToSInfoSchema.getIsStaffCode();
		this.StaffCode = aInterfacePToSInfoSchema.getStaffCode();
		this.IsJoinCode = aInterfacePToSInfoSchema.getIsJoinCode();
		this.JoinCode = aInterfacePToSInfoSchema.getJoinCode();
		this.IsPremPayPeriod = aInterfacePToSInfoSchema.getIsPremPayPeriod();
		this.PremPayPeriodType = aInterfacePToSInfoSchema.getPremPayPeriodType();
		this.PremPayPeriod = aInterfacePToSInfoSchema.getPremPayPeriod();
		this.IsBenefitOption = aInterfacePToSInfoSchema.getIsBenefitOption();
		this.BenefitOptionType = aInterfacePToSInfoSchema.getBenefitOptionType();
		this.BenefitOption = aInterfacePToSInfoSchema.getBenefitOption();
		this.IsBenefitPeriod = aInterfacePToSInfoSchema.getIsBenefitPeriod();
		this.BenefitPeriodType = aInterfacePToSInfoSchema.getBenefitPeriodType();
		this.BenefitPeriod = aInterfacePToSInfoSchema.getBenefitPeriod();
		this.IsChannel = aInterfacePToSInfoSchema.getIsChannel();
		this.Channel = aInterfacePToSInfoSchema.getChannel();
		this.IsCurrency = aInterfacePToSInfoSchema.getIsCurrency();
		this.Currency = aInterfacePToSInfoSchema.getCurrency();
		this.IsPar = aInterfacePToSInfoSchema.getIsPar();
		this.Par = aInterfacePToSInfoSchema.getPar();
		this.IsUreUWType = aInterfacePToSInfoSchema.getIsUreUWType();
		this.UreUWType = aInterfacePToSInfoSchema.getUreUWType();
		this.BatchNo = aInterfacePToSInfoSchema.getBatchNo();
		this.OPERATOR = aInterfacePToSInfoSchema.getOPERATOR();
		this.MAKEDATE = fDate.getDate( aInterfacePToSInfoSchema.getMAKEDATE());
		this.MAKETIME = aInterfacePToSInfoSchema.getMAKETIME();
		this.MODIFYDATE = fDate.getDate( aInterfacePToSInfoSchema.getMODIFYDATE());
		this.MODIFYTIME = aInterfacePToSInfoSchema.getMODIFYTIME();
		this.StandByFlag1 = aInterfacePToSInfoSchema.getStandByFlag1();
		this.StandByFlag2 = aInterfacePToSInfoSchema.getStandByFlag2();
		this.StandByFlag3 = aInterfacePToSInfoSchema.getStandByFlag3();
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
			if( rs.getString("ProductCode") == null )
				this.ProductCode = null;
			else
				this.ProductCode = rs.getString("ProductCode").trim();

			if( rs.getString("ProductCHName") == null )
				this.ProductCHName = null;
			else
				this.ProductCHName = rs.getString("ProductCHName").trim();

			if( rs.getString("ProductENName") == null )
				this.ProductENName = null;
			else
				this.ProductENName = rs.getString("ProductENName").trim();

			if( rs.getString("IProductCode") == null )
				this.IProductCode = null;
			else
				this.IProductCode = rs.getString("IProductCode").trim();

			if( rs.getString("IProductCHName") == null )
				this.IProductCHName = null;
			else
				this.IProductCHName = rs.getString("IProductCHName").trim();

			if( rs.getString("IProductENName") == null )
				this.IProductENName = null;
			else
				this.IProductENName = rs.getString("IProductENName").trim();

			if( rs.getString("IsEffectiveDate") == null )
				this.IsEffectiveDate = null;
			else
				this.IsEffectiveDate = rs.getString("IsEffectiveDate").trim();

			this.EffectiveDate = rs.getDate("EffectiveDate");
			this.EffectiveEndDate = rs.getDate("EffectiveEndDate");
			if( rs.getString("IsSpouseCode") == null )
				this.IsSpouseCode = null;
			else
				this.IsSpouseCode = rs.getString("IsSpouseCode").trim();

			if( rs.getString("SpouseCode") == null )
				this.SpouseCode = null;
			else
				this.SpouseCode = rs.getString("SpouseCode").trim();

			if( rs.getString("IsStaffCode") == null )
				this.IsStaffCode = null;
			else
				this.IsStaffCode = rs.getString("IsStaffCode").trim();

			if( rs.getString("StaffCode") == null )
				this.StaffCode = null;
			else
				this.StaffCode = rs.getString("StaffCode").trim();

			if( rs.getString("IsJoinCode") == null )
				this.IsJoinCode = null;
			else
				this.IsJoinCode = rs.getString("IsJoinCode").trim();

			if( rs.getString("JoinCode") == null )
				this.JoinCode = null;
			else
				this.JoinCode = rs.getString("JoinCode").trim();

			if( rs.getString("IsPremPayPeriod") == null )
				this.IsPremPayPeriod = null;
			else
				this.IsPremPayPeriod = rs.getString("IsPremPayPeriod").trim();

			if( rs.getString("PremPayPeriodType") == null )
				this.PremPayPeriodType = null;
			else
				this.PremPayPeriodType = rs.getString("PremPayPeriodType").trim();

			this.PremPayPeriod = rs.getInt("PremPayPeriod");
			if( rs.getString("IsBenefitOption") == null )
				this.IsBenefitOption = null;
			else
				this.IsBenefitOption = rs.getString("IsBenefitOption").trim();

			if( rs.getString("BenefitOptionType") == null )
				this.BenefitOptionType = null;
			else
				this.BenefitOptionType = rs.getString("BenefitOptionType").trim();

			if( rs.getString("BenefitOption") == null )
				this.BenefitOption = null;
			else
				this.BenefitOption = rs.getString("BenefitOption").trim();

			if( rs.getString("IsBenefitPeriod") == null )
				this.IsBenefitPeriod = null;
			else
				this.IsBenefitPeriod = rs.getString("IsBenefitPeriod").trim();

			if( rs.getString("BenefitPeriodType") == null )
				this.BenefitPeriodType = null;
			else
				this.BenefitPeriodType = rs.getString("BenefitPeriodType").trim();

			this.BenefitPeriod = rs.getInt("BenefitPeriod");
			if( rs.getString("IsChannel") == null )
				this.IsChannel = null;
			else
				this.IsChannel = rs.getString("IsChannel").trim();

			if( rs.getString("Channel") == null )
				this.Channel = null;
			else
				this.Channel = rs.getString("Channel").trim();

			if( rs.getString("IsCurrency") == null )
				this.IsCurrency = null;
			else
				this.IsCurrency = rs.getString("IsCurrency").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("IsPar") == null )
				this.IsPar = null;
			else
				this.IsPar = rs.getString("IsPar").trim();

			if( rs.getString("Par") == null )
				this.Par = null;
			else
				this.Par = rs.getString("Par").trim();

			if( rs.getString("IsUreUWType") == null )
				this.IsUreUWType = null;
			else
				this.IsUreUWType = rs.getString("IsUreUWType").trim();

			if( rs.getString("UreUWType") == null )
				this.UreUWType = null;
			else
				this.UreUWType = rs.getString("UreUWType").trim();

			this.BatchNo = rs.getInt("BatchNo");
			if( rs.getString("OPERATOR") == null )
				this.OPERATOR = null;
			else
				this.OPERATOR = rs.getString("OPERATOR").trim();

			this.MAKEDATE = rs.getDate("MAKEDATE");
			if( rs.getString("MAKETIME") == null )
				this.MAKETIME = null;
			else
				this.MAKETIME = rs.getString("MAKETIME").trim();

			this.MODIFYDATE = rs.getDate("MODIFYDATE");
			if( rs.getString("MODIFYTIME") == null )
				this.MODIFYTIME = null;
			else
				this.MODIFYTIME = rs.getString("MODIFYTIME").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("StandByFlag3") == null )
				this.StandByFlag3 = null;
			else
				this.StandByFlag3 = rs.getString("StandByFlag3").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的InterfacePToSInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public InterfacePToSInfoSchema getSchema()
	{
		InterfacePToSInfoSchema aInterfacePToSInfoSchema = new InterfacePToSInfoSchema();
		aInterfacePToSInfoSchema.setSchema(this);
		return aInterfacePToSInfoSchema;
	}

	public InterfacePToSInfoDB getDB()
	{
		InterfacePToSInfoDB aDBOper = new InterfacePToSInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpInterfacePToSInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ProductCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProductCHName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProductENName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IProductCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IProductCHName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IProductENName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsEffectiveDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EffectiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EffectiveEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsSpouseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpouseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsStaffCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StaffCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsJoinCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JoinCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsPremPayPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremPayPeriodType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremPayPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsBenefitOption)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BenefitOptionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BenefitOption)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsBenefitPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BenefitPeriodType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BenefitPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsChannel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Channel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsCurrency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsPar)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Par)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsUreUWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UreUWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BatchNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OPERATOR)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MAKEDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MAKETIME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MODIFYDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MODIFYTIME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpInterfacePToSInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ProductCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProductCHName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProductENName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IProductCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IProductCHName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IProductENName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			IsEffectiveDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EffectiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EffectiveEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			IsSpouseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SpouseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IsStaffCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StaffCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			IsJoinCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			JoinCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			IsPremPayPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PremPayPeriodType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PremPayPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			IsBenefitOption = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BenefitOptionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			BenefitOption = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			IsBenefitPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			BenefitPeriodType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BenefitPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			IsChannel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Channel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			IsCurrency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			IsPar = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Par = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			IsUreUWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			UreUWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BatchNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).intValue();
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			StandByFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InterfacePToSInfoSchema";
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
		if (FCode.equalsIgnoreCase("ProductCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProductCode));
		}
		if (FCode.equalsIgnoreCase("ProductCHName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProductCHName));
		}
		if (FCode.equalsIgnoreCase("ProductENName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProductENName));
		}
		if (FCode.equalsIgnoreCase("IProductCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IProductCode));
		}
		if (FCode.equalsIgnoreCase("IProductCHName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IProductCHName));
		}
		if (FCode.equalsIgnoreCase("IProductENName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IProductENName));
		}
		if (FCode.equalsIgnoreCase("IsEffectiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsEffectiveDate));
		}
		if (FCode.equalsIgnoreCase("EffectiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEffectiveDate()));
		}
		if (FCode.equalsIgnoreCase("EffectiveEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEffectiveEndDate()));
		}
		if (FCode.equalsIgnoreCase("IsSpouseCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsSpouseCode));
		}
		if (FCode.equalsIgnoreCase("SpouseCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpouseCode));
		}
		if (FCode.equalsIgnoreCase("IsStaffCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsStaffCode));
		}
		if (FCode.equalsIgnoreCase("StaffCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StaffCode));
		}
		if (FCode.equalsIgnoreCase("IsJoinCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsJoinCode));
		}
		if (FCode.equalsIgnoreCase("JoinCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JoinCode));
		}
		if (FCode.equalsIgnoreCase("IsPremPayPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsPremPayPeriod));
		}
		if (FCode.equalsIgnoreCase("PremPayPeriodType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremPayPeriodType));
		}
		if (FCode.equalsIgnoreCase("PremPayPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremPayPeriod));
		}
		if (FCode.equalsIgnoreCase("IsBenefitOption"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsBenefitOption));
		}
		if (FCode.equalsIgnoreCase("BenefitOptionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BenefitOptionType));
		}
		if (FCode.equalsIgnoreCase("BenefitOption"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BenefitOption));
		}
		if (FCode.equalsIgnoreCase("IsBenefitPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsBenefitPeriod));
		}
		if (FCode.equalsIgnoreCase("BenefitPeriodType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BenefitPeriodType));
		}
		if (FCode.equalsIgnoreCase("BenefitPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BenefitPeriod));
		}
		if (FCode.equalsIgnoreCase("IsChannel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsChannel));
		}
		if (FCode.equalsIgnoreCase("Channel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Channel));
		}
		if (FCode.equalsIgnoreCase("IsCurrency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsCurrency));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("IsPar"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsPar));
		}
		if (FCode.equalsIgnoreCase("Par"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Par));
		}
		if (FCode.equalsIgnoreCase("IsUreUWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsUreUWType));
		}
		if (FCode.equalsIgnoreCase("UreUWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UreUWType));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("OPERATOR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPERATOR));
		}
		if (FCode.equalsIgnoreCase("MAKEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
		}
		if (FCode.equalsIgnoreCase("MAKETIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
		}
		if (FCode.equalsIgnoreCase("MODIFYDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
		}
		if (FCode.equalsIgnoreCase("MODIFYTIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MODIFYTIME));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag3));
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
				strFieldValue = StrTool.GBKToUnicode(ProductCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProductCHName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProductENName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IProductCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IProductCHName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IProductENName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(IsEffectiveDate);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEffectiveDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEffectiveEndDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(IsSpouseCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SpouseCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IsStaffCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StaffCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(IsJoinCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(JoinCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(IsPremPayPeriod);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PremPayPeriodType);
				break;
			case 17:
				strFieldValue = String.valueOf(PremPayPeriod);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(IsBenefitOption);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BenefitOptionType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(BenefitOption);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(IsBenefitPeriod);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(BenefitPeriodType);
				break;
			case 23:
				strFieldValue = String.valueOf(BenefitPeriod);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(IsChannel);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Channel);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(IsCurrency);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(IsPar);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Par);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(IsUreUWType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(UreUWType);
				break;
			case 32:
				strFieldValue = String.valueOf(BatchNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(OPERATOR);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag3);
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

		if (FCode.equalsIgnoreCase("ProductCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProductCode = FValue.trim();
			}
			else
				ProductCode = null;
		}
		if (FCode.equalsIgnoreCase("ProductCHName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProductCHName = FValue.trim();
			}
			else
				ProductCHName = null;
		}
		if (FCode.equalsIgnoreCase("ProductENName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProductENName = FValue.trim();
			}
			else
				ProductENName = null;
		}
		if (FCode.equalsIgnoreCase("IProductCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IProductCode = FValue.trim();
			}
			else
				IProductCode = null;
		}
		if (FCode.equalsIgnoreCase("IProductCHName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IProductCHName = FValue.trim();
			}
			else
				IProductCHName = null;
		}
		if (FCode.equalsIgnoreCase("IProductENName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IProductENName = FValue.trim();
			}
			else
				IProductENName = null;
		}
		if (FCode.equalsIgnoreCase("IsEffectiveDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsEffectiveDate = FValue.trim();
			}
			else
				IsEffectiveDate = null;
		}
		if (FCode.equalsIgnoreCase("EffectiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EffectiveDate = fDate.getDate( FValue );
			}
			else
				EffectiveDate = null;
		}
		if (FCode.equalsIgnoreCase("EffectiveEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EffectiveEndDate = fDate.getDate( FValue );
			}
			else
				EffectiveEndDate = null;
		}
		if (FCode.equalsIgnoreCase("IsSpouseCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsSpouseCode = FValue.trim();
			}
			else
				IsSpouseCode = null;
		}
		if (FCode.equalsIgnoreCase("SpouseCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpouseCode = FValue.trim();
			}
			else
				SpouseCode = null;
		}
		if (FCode.equalsIgnoreCase("IsStaffCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsStaffCode = FValue.trim();
			}
			else
				IsStaffCode = null;
		}
		if (FCode.equalsIgnoreCase("StaffCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StaffCode = FValue.trim();
			}
			else
				StaffCode = null;
		}
		if (FCode.equalsIgnoreCase("IsJoinCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsJoinCode = FValue.trim();
			}
			else
				IsJoinCode = null;
		}
		if (FCode.equalsIgnoreCase("JoinCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JoinCode = FValue.trim();
			}
			else
				JoinCode = null;
		}
		if (FCode.equalsIgnoreCase("IsPremPayPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsPremPayPeriod = FValue.trim();
			}
			else
				IsPremPayPeriod = null;
		}
		if (FCode.equalsIgnoreCase("PremPayPeriodType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremPayPeriodType = FValue.trim();
			}
			else
				PremPayPeriodType = null;
		}
		if (FCode.equalsIgnoreCase("PremPayPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PremPayPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("IsBenefitOption"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsBenefitOption = FValue.trim();
			}
			else
				IsBenefitOption = null;
		}
		if (FCode.equalsIgnoreCase("BenefitOptionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BenefitOptionType = FValue.trim();
			}
			else
				BenefitOptionType = null;
		}
		if (FCode.equalsIgnoreCase("BenefitOption"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BenefitOption = FValue.trim();
			}
			else
				BenefitOption = null;
		}
		if (FCode.equalsIgnoreCase("IsBenefitPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsBenefitPeriod = FValue.trim();
			}
			else
				IsBenefitPeriod = null;
		}
		if (FCode.equalsIgnoreCase("BenefitPeriodType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BenefitPeriodType = FValue.trim();
			}
			else
				BenefitPeriodType = null;
		}
		if (FCode.equalsIgnoreCase("BenefitPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BenefitPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("IsChannel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsChannel = FValue.trim();
			}
			else
				IsChannel = null;
		}
		if (FCode.equalsIgnoreCase("Channel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Channel = FValue.trim();
			}
			else
				Channel = null;
		}
		if (FCode.equalsIgnoreCase("IsCurrency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsCurrency = FValue.trim();
			}
			else
				IsCurrency = null;
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
		if (FCode.equalsIgnoreCase("IsPar"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsPar = FValue.trim();
			}
			else
				IsPar = null;
		}
		if (FCode.equalsIgnoreCase("Par"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Par = FValue.trim();
			}
			else
				Par = null;
		}
		if (FCode.equalsIgnoreCase("IsUreUWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsUreUWType = FValue.trim();
			}
			else
				IsUreUWType = null;
		}
		if (FCode.equalsIgnoreCase("UreUWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UreUWType = FValue.trim();
			}
			else
				UreUWType = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BatchNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("OPERATOR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPERATOR = FValue.trim();
			}
			else
				OPERATOR = null;
		}
		if (FCode.equalsIgnoreCase("MAKEDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MAKEDATE = fDate.getDate( FValue );
			}
			else
				MAKEDATE = null;
		}
		if (FCode.equalsIgnoreCase("MAKETIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKETIME = FValue.trim();
			}
			else
				MAKETIME = null;
		}
		if (FCode.equalsIgnoreCase("MODIFYDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MODIFYDATE = fDate.getDate( FValue );
			}
			else
				MODIFYDATE = null;
		}
		if (FCode.equalsIgnoreCase("MODIFYTIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MODIFYTIME = FValue.trim();
			}
			else
				MODIFYTIME = null;
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
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag3 = FValue.trim();
			}
			else
				StandByFlag3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		InterfacePToSInfoSchema other = (InterfacePToSInfoSchema)otherObject;
		return
			ProductCode.equals(other.getProductCode())
			&& ProductCHName.equals(other.getProductCHName())
			&& ProductENName.equals(other.getProductENName())
			&& IProductCode.equals(other.getIProductCode())
			&& IProductCHName.equals(other.getIProductCHName())
			&& IProductENName.equals(other.getIProductENName())
			&& IsEffectiveDate.equals(other.getIsEffectiveDate())
			&& fDate.getString(EffectiveDate).equals(other.getEffectiveDate())
			&& fDate.getString(EffectiveEndDate).equals(other.getEffectiveEndDate())
			&& IsSpouseCode.equals(other.getIsSpouseCode())
			&& SpouseCode.equals(other.getSpouseCode())
			&& IsStaffCode.equals(other.getIsStaffCode())
			&& StaffCode.equals(other.getStaffCode())
			&& IsJoinCode.equals(other.getIsJoinCode())
			&& JoinCode.equals(other.getJoinCode())
			&& IsPremPayPeriod.equals(other.getIsPremPayPeriod())
			&& PremPayPeriodType.equals(other.getPremPayPeriodType())
			&& PremPayPeriod == other.getPremPayPeriod()
			&& IsBenefitOption.equals(other.getIsBenefitOption())
			&& BenefitOptionType.equals(other.getBenefitOptionType())
			&& BenefitOption.equals(other.getBenefitOption())
			&& IsBenefitPeriod.equals(other.getIsBenefitPeriod())
			&& BenefitPeriodType.equals(other.getBenefitPeriodType())
			&& BenefitPeriod == other.getBenefitPeriod()
			&& IsChannel.equals(other.getIsChannel())
			&& Channel.equals(other.getChannel())
			&& IsCurrency.equals(other.getIsCurrency())
			&& Currency.equals(other.getCurrency())
			&& IsPar.equals(other.getIsPar())
			&& Par.equals(other.getPar())
			&& IsUreUWType.equals(other.getIsUreUWType())
			&& UreUWType.equals(other.getUreUWType())
			&& BatchNo == other.getBatchNo()
			&& OPERATOR.equals(other.getOPERATOR())
			&& fDate.getString(MAKEDATE).equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
			&& fDate.getString(MODIFYDATE).equals(other.getMODIFYDATE())
			&& MODIFYTIME.equals(other.getMODIFYTIME())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& StandByFlag3.equals(other.getStandByFlag3());
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
		if( strFieldName.equals("ProductCode") ) {
			return 0;
		}
		if( strFieldName.equals("ProductCHName") ) {
			return 1;
		}
		if( strFieldName.equals("ProductENName") ) {
			return 2;
		}
		if( strFieldName.equals("IProductCode") ) {
			return 3;
		}
		if( strFieldName.equals("IProductCHName") ) {
			return 4;
		}
		if( strFieldName.equals("IProductENName") ) {
			return 5;
		}
		if( strFieldName.equals("IsEffectiveDate") ) {
			return 6;
		}
		if( strFieldName.equals("EffectiveDate") ) {
			return 7;
		}
		if( strFieldName.equals("EffectiveEndDate") ) {
			return 8;
		}
		if( strFieldName.equals("IsSpouseCode") ) {
			return 9;
		}
		if( strFieldName.equals("SpouseCode") ) {
			return 10;
		}
		if( strFieldName.equals("IsStaffCode") ) {
			return 11;
		}
		if( strFieldName.equals("StaffCode") ) {
			return 12;
		}
		if( strFieldName.equals("IsJoinCode") ) {
			return 13;
		}
		if( strFieldName.equals("JoinCode") ) {
			return 14;
		}
		if( strFieldName.equals("IsPremPayPeriod") ) {
			return 15;
		}
		if( strFieldName.equals("PremPayPeriodType") ) {
			return 16;
		}
		if( strFieldName.equals("PremPayPeriod") ) {
			return 17;
		}
		if( strFieldName.equals("IsBenefitOption") ) {
			return 18;
		}
		if( strFieldName.equals("BenefitOptionType") ) {
			return 19;
		}
		if( strFieldName.equals("BenefitOption") ) {
			return 20;
		}
		if( strFieldName.equals("IsBenefitPeriod") ) {
			return 21;
		}
		if( strFieldName.equals("BenefitPeriodType") ) {
			return 22;
		}
		if( strFieldName.equals("BenefitPeriod") ) {
			return 23;
		}
		if( strFieldName.equals("IsChannel") ) {
			return 24;
		}
		if( strFieldName.equals("Channel") ) {
			return 25;
		}
		if( strFieldName.equals("IsCurrency") ) {
			return 26;
		}
		if( strFieldName.equals("Currency") ) {
			return 27;
		}
		if( strFieldName.equals("IsPar") ) {
			return 28;
		}
		if( strFieldName.equals("Par") ) {
			return 29;
		}
		if( strFieldName.equals("IsUreUWType") ) {
			return 30;
		}
		if( strFieldName.equals("UreUWType") ) {
			return 31;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 32;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return 33;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 34;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 35;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return 36;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return 37;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 38;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 39;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return 40;
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
				strFieldName = "ProductCode";
				break;
			case 1:
				strFieldName = "ProductCHName";
				break;
			case 2:
				strFieldName = "ProductENName";
				break;
			case 3:
				strFieldName = "IProductCode";
				break;
			case 4:
				strFieldName = "IProductCHName";
				break;
			case 5:
				strFieldName = "IProductENName";
				break;
			case 6:
				strFieldName = "IsEffectiveDate";
				break;
			case 7:
				strFieldName = "EffectiveDate";
				break;
			case 8:
				strFieldName = "EffectiveEndDate";
				break;
			case 9:
				strFieldName = "IsSpouseCode";
				break;
			case 10:
				strFieldName = "SpouseCode";
				break;
			case 11:
				strFieldName = "IsStaffCode";
				break;
			case 12:
				strFieldName = "StaffCode";
				break;
			case 13:
				strFieldName = "IsJoinCode";
				break;
			case 14:
				strFieldName = "JoinCode";
				break;
			case 15:
				strFieldName = "IsPremPayPeriod";
				break;
			case 16:
				strFieldName = "PremPayPeriodType";
				break;
			case 17:
				strFieldName = "PremPayPeriod";
				break;
			case 18:
				strFieldName = "IsBenefitOption";
				break;
			case 19:
				strFieldName = "BenefitOptionType";
				break;
			case 20:
				strFieldName = "BenefitOption";
				break;
			case 21:
				strFieldName = "IsBenefitPeriod";
				break;
			case 22:
				strFieldName = "BenefitPeriodType";
				break;
			case 23:
				strFieldName = "BenefitPeriod";
				break;
			case 24:
				strFieldName = "IsChannel";
				break;
			case 25:
				strFieldName = "Channel";
				break;
			case 26:
				strFieldName = "IsCurrency";
				break;
			case 27:
				strFieldName = "Currency";
				break;
			case 28:
				strFieldName = "IsPar";
				break;
			case 29:
				strFieldName = "Par";
				break;
			case 30:
				strFieldName = "IsUreUWType";
				break;
			case 31:
				strFieldName = "UreUWType";
				break;
			case 32:
				strFieldName = "BatchNo";
				break;
			case 33:
				strFieldName = "OPERATOR";
				break;
			case 34:
				strFieldName = "MAKEDATE";
				break;
			case 35:
				strFieldName = "MAKETIME";
				break;
			case 36:
				strFieldName = "MODIFYDATE";
				break;
			case 37:
				strFieldName = "MODIFYTIME";
				break;
			case 38:
				strFieldName = "StandByFlag1";
				break;
			case 39:
				strFieldName = "StandByFlag2";
				break;
			case 40:
				strFieldName = "StandByFlag3";
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
		if( strFieldName.equals("ProductCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProductCHName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProductENName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IProductCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IProductCHName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IProductENName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsEffectiveDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EffectiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EffectiveEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IsSpouseCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpouseCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsStaffCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StaffCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsJoinCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JoinCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsPremPayPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremPayPeriodType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremPayPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IsBenefitOption") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BenefitOptionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BenefitOption") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsBenefitPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BenefitPeriodType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BenefitPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IsChannel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Channel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsCurrency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsPar") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Par") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsUreUWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UreUWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag3") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_INT;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}

