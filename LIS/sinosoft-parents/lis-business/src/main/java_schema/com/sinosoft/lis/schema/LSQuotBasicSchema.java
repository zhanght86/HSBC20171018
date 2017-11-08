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
import com.sinosoft.lis.db.LSQuotBasicDB;

/*
 * <p>ClassName: LSQuotBasicSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotBasicSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotBasicSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 准客户号码 */
	private String PreCustomerNo;
	/** 准客户名称 */
	private String PreCustomerName;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 单位性质 */
	private String GrpNature;
	/** 行业分类 */
	private String BusiCategory;
	/** 地址 */
	private String Address;
	/** 公司简介 */
	private String CustomerIntro;
	/** 产品类型 */
	private String ProdType;
	/** 渠道类型 */
	private String SaleChannel;
	/** 保费分摊方式 */
	private String PremMode;
	/** 预计保费规模 */
	private double PrePrem;
	/** 是否为续保业务 */
	private String RenewFlag;
	/** 是否为统括单 */
	private String BlanketFlag;
	/** 是否为弹性询价 */
	private String ElasticFlag;
	/** 缴费方式 */
	private String PayIntv;
	/** 保险期间 */
	private int InsuPeriod;
	/** 保险期间单位 */
	private String InsuPeriodFlag;
	/** 契约生效日类型 */
	private String ValDateType;
	/** 约定生效日期 */
	private Date AppointValDate;
	/** 是否共保 */
	private String Coinsurance;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
	/** 批次状态 */
	private String QuotState;
	/** 归档原因 */
	private String FileReason;
	/** 归档描述 */
	private String FileDesc;
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

	public static final int FIELDNUM = 37;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotBasicSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";

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
		LSQuotBasicSchema cloned = (LSQuotBasicSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuotNo()
	{
		return QuotNo;
	}
	public void setQuotNo(String aQuotNo)
	{
		if(aQuotNo!=null && aQuotNo.length()>20)
			logger.debug("询价号QuotNo值"+aQuotNo+"的长度"+aQuotNo.length()+"大于最大值20");
		QuotNo = aQuotNo;
	}
	public int getQuotBatNo()
	{
		return QuotBatNo;
	}
	public void setQuotBatNo(int aQuotBatNo)
	{
		QuotBatNo = aQuotBatNo;
	}
	public void setQuotBatNo(String aQuotBatNo)
	{
		if (aQuotBatNo != null && !aQuotBatNo.equals(""))
		{
			Integer tInteger = new Integer(aQuotBatNo);
			int i = tInteger.intValue();
			QuotBatNo = i;
		}
	}

	public String getPreCustomerNo()
	{
		return PreCustomerNo;
	}
	public void setPreCustomerNo(String aPreCustomerNo)
	{
		if(aPreCustomerNo!=null && aPreCustomerNo.length()>10)
			logger.debug("准客户号码PreCustomerNo值"+aPreCustomerNo+"的长度"+aPreCustomerNo.length()+"大于最大值10");
		PreCustomerNo = aPreCustomerNo;
	}
	public String getPreCustomerName()
	{
		return PreCustomerName;
	}
	public void setPreCustomerName(String aPreCustomerName)
	{
		if(aPreCustomerName!=null && aPreCustomerName.length()>120)
			logger.debug("准客户名称PreCustomerName值"+aPreCustomerName+"的长度"+aPreCustomerName.length()+"大于最大值120");
		PreCustomerName = aPreCustomerName;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>2)
			logger.debug("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值2");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>30)
			logger.debug("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值30");
		IDNo = aIDNo;
	}
	public String getGrpNature()
	{
		return GrpNature;
	}
	public void setGrpNature(String aGrpNature)
	{
		if(aGrpNature!=null && aGrpNature.length()>3)
			logger.debug("单位性质GrpNature值"+aGrpNature+"的长度"+aGrpNature.length()+"大于最大值3");
		GrpNature = aGrpNature;
	}
	public String getBusiCategory()
	{
		return BusiCategory;
	}
	public void setBusiCategory(String aBusiCategory)
	{
		if(aBusiCategory!=null && aBusiCategory.length()>3)
			logger.debug("行业分类BusiCategory值"+aBusiCategory+"的长度"+aBusiCategory.length()+"大于最大值2");
		BusiCategory = aBusiCategory;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		if(aAddress!=null && aAddress.length()>500)
			logger.debug("地址Address值"+aAddress+"的长度"+aAddress.length()+"大于最大值500");
		Address = aAddress;
	}
	public String getCustomerIntro()
	{
		return CustomerIntro;
	}
	public void setCustomerIntro(String aCustomerIntro)
	{
		if(aCustomerIntro!=null && aCustomerIntro.length()>1000)
			logger.debug("公司简介CustomerIntro值"+aCustomerIntro+"的长度"+aCustomerIntro.length()+"大于最大值1000");
		CustomerIntro = aCustomerIntro;
	}
	public String getProdType()
	{
		return ProdType;
	}
	public void setProdType(String aProdType)
	{
		if(aProdType!=null && aProdType.length()>2)
			logger.debug("产品类型ProdType值"+aProdType+"的长度"+aProdType.length()+"大于最大值2");
		ProdType = aProdType;
	}
	public String getSaleChannel()
	{
		return SaleChannel;
	}
	public void setSaleChannel(String aSaleChannel)
	{
		if(aSaleChannel!=null && aSaleChannel.length()>2)
			logger.debug("渠道类型SaleChannel值"+aSaleChannel+"的长度"+aSaleChannel.length()+"大于最大值2");
		SaleChannel = aSaleChannel;
	}
	public String getPremMode()
	{
		return PremMode;
	}
	public void setPremMode(String aPremMode)
	{
		if(aPremMode!=null && aPremMode.length()>2)
			logger.debug("保费分摊方式PremMode值"+aPremMode+"的长度"+aPremMode.length()+"大于最大值2");
		PremMode = aPremMode;
	}
	public double getPrePrem()
	{
		return PrePrem;
	}
	public void setPrePrem(double aPrePrem)
	{
		PrePrem = aPrePrem;
	}
	public void setPrePrem(String aPrePrem)
	{
		if (aPrePrem != null && !aPrePrem.equals(""))
		{
			Double tDouble = new Double(aPrePrem);
			double d = tDouble.doubleValue();
			PrePrem = d;
		}
	}

	public String getRenewFlag()
	{
		return RenewFlag;
	}
	public void setRenewFlag(String aRenewFlag)
	{
		if(aRenewFlag!=null && aRenewFlag.length()>1)
			logger.debug("是否为续保业务RenewFlag值"+aRenewFlag+"的长度"+aRenewFlag.length()+"大于最大值1");
		RenewFlag = aRenewFlag;
	}
	public String getBlanketFlag()
	{
		return BlanketFlag;
	}
	public void setBlanketFlag(String aBlanketFlag)
	{
		if(aBlanketFlag!=null && aBlanketFlag.length()>1)
			logger.debug("是否为统括单BlanketFlag值"+aBlanketFlag+"的长度"+aBlanketFlag.length()+"大于最大值1");
		BlanketFlag = aBlanketFlag;
	}
	public String getElasticFlag()
	{
		return ElasticFlag;
	}
	public void setElasticFlag(String aElasticFlag)
	{
		if(aElasticFlag!=null && aElasticFlag.length()>2)
			logger.debug("是否为弹性询价ElasticFlag值"+aElasticFlag+"的长度"+aElasticFlag.length()+"大于最大值2");
		ElasticFlag = aElasticFlag;
	}
	public String getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if(aPayIntv!=null && aPayIntv.length()>2)
			logger.debug("缴费方式PayIntv值"+aPayIntv+"的长度"+aPayIntv.length()+"大于最大值2");
		PayIntv = aPayIntv;
	}
	public int getInsuPeriod()
	{
		return InsuPeriod;
	}
	public void setInsuPeriod(int aInsuPeriod)
	{
		InsuPeriod = aInsuPeriod;
	}
	public void setInsuPeriod(String aInsuPeriod)
	{
		if (aInsuPeriod != null && !aInsuPeriod.equals(""))
		{
			Integer tInteger = new Integer(aInsuPeriod);
			int i = tInteger.intValue();
			InsuPeriod = i;
		}
	}

	public String getInsuPeriodFlag()
	{
		return InsuPeriodFlag;
	}
	public void setInsuPeriodFlag(String aInsuPeriodFlag)
	{
		if(aInsuPeriodFlag!=null && aInsuPeriodFlag.length()>1)
			logger.debug("保险期间单位InsuPeriodFlag值"+aInsuPeriodFlag+"的长度"+aInsuPeriodFlag.length()+"大于最大值1");
		InsuPeriodFlag = aInsuPeriodFlag;
	}
	public String getValDateType()
	{
		return ValDateType;
	}
	public void setValDateType(String aValDateType)
	{
		if(aValDateType!=null && aValDateType.length()>1)
			logger.debug("契约生效日类型ValDateType值"+aValDateType+"的长度"+aValDateType.length()+"大于最大值1");
		ValDateType = aValDateType;
	}
	public String getAppointValDate()
	{
		if( AppointValDate != null )
			return fDate.getString(AppointValDate);
		else
			return null;
	}
	public void setAppointValDate(Date aAppointValDate)
	{
		AppointValDate = aAppointValDate;
	}
	public void setAppointValDate(String aAppointValDate)
	{
		if (aAppointValDate != null && !aAppointValDate.equals("") )
		{
			AppointValDate = fDate.getDate( aAppointValDate );
		}
		else
			AppointValDate = null;
	}

	/**
	* 0-否；1-是
	*/
	public String getCoinsurance()
	{
		return Coinsurance;
	}
	public void setCoinsurance(String aCoinsurance)
	{
		if(aCoinsurance!=null && aCoinsurance.length()>2)
			logger.debug("是否共保Coinsurance值"+aCoinsurance+"的长度"+aCoinsurance.length()+"大于最大值2");
		Coinsurance = aCoinsurance;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			logger.debug("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>20)
			logger.debug("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值20");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>200)
			logger.debug("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值200");
		Segment3 = aSegment3;
	}
	/**
	* 00-询价录入，01-询价在途，02-审批通过，03-审批未通过，04-自动归档，05-手动归档，06-询价撤销
	*/
	public String getQuotState()
	{
		return QuotState;
	}
	public void setQuotState(String aQuotState)
	{
		if(aQuotState!=null && aQuotState.length()>2)
			logger.debug("批次状态QuotState值"+aQuotState+"的长度"+aQuotState.length()+"大于最大值2");
		QuotState = aQuotState;
	}
	public String getFileReason()
	{
		return FileReason;
	}
	public void setFileReason(String aFileReason)
	{
		if(aFileReason!=null && aFileReason.length()>2)
			logger.debug("归档原因FileReason值"+aFileReason+"的长度"+aFileReason.length()+"大于最大值2");
		FileReason = aFileReason;
	}
	public String getFileDesc()
	{
		return FileDesc;
	}
	public void setFileDesc(String aFileDesc)
	{
		if(aFileDesc!=null && aFileDesc.length()>3000)
			logger.debug("归档描述FileDesc值"+aFileDesc+"的长度"+aFileDesc.length()+"大于最大值3000");
		FileDesc = aFileDesc;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			logger.debug("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			logger.debug("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			logger.debug("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
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
			logger.debug("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			logger.debug("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
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
			logger.debug("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LSQuotBasicSchema 对象给 Schema 赋值
	* @param: aLSQuotBasicSchema LSQuotBasicSchema
	**/
	public void setSchema(LSQuotBasicSchema aLSQuotBasicSchema)
	{	
		this.QuotNo = aLSQuotBasicSchema.getQuotNo();
		this.QuotBatNo = aLSQuotBasicSchema.getQuotBatNo();
		this.PreCustomerNo = aLSQuotBasicSchema.getPreCustomerNo();
		this.PreCustomerName = aLSQuotBasicSchema.getPreCustomerName();
		this.IDType = aLSQuotBasicSchema.getIDType();
		this.IDNo = aLSQuotBasicSchema.getIDNo();
		this.GrpNature = aLSQuotBasicSchema.getGrpNature();
		this.BusiCategory = aLSQuotBasicSchema.getBusiCategory();
		this.Address = aLSQuotBasicSchema.getAddress();
		this.CustomerIntro = aLSQuotBasicSchema.getCustomerIntro();
		this.ProdType = aLSQuotBasicSchema.getProdType();
		this.SaleChannel = aLSQuotBasicSchema.getSaleChannel();
		this.PremMode = aLSQuotBasicSchema.getPremMode();
		this.PrePrem = aLSQuotBasicSchema.getPrePrem();
		this.RenewFlag = aLSQuotBasicSchema.getRenewFlag();
		this.BlanketFlag = aLSQuotBasicSchema.getBlanketFlag();
		this.ElasticFlag = aLSQuotBasicSchema.getElasticFlag();
		this.PayIntv = aLSQuotBasicSchema.getPayIntv();
		this.InsuPeriod = aLSQuotBasicSchema.getInsuPeriod();
		this.InsuPeriodFlag = aLSQuotBasicSchema.getInsuPeriodFlag();
		this.ValDateType = aLSQuotBasicSchema.getValDateType();
		this.AppointValDate = fDate.getDate( aLSQuotBasicSchema.getAppointValDate());
		this.Coinsurance = aLSQuotBasicSchema.getCoinsurance();
		this.Segment1 = aLSQuotBasicSchema.getSegment1();
		this.Segment2 = aLSQuotBasicSchema.getSegment2();
		this.Segment3 = aLSQuotBasicSchema.getSegment3();
		this.QuotState = aLSQuotBasicSchema.getQuotState();
		this.FileReason = aLSQuotBasicSchema.getFileReason();
		this.FileDesc = aLSQuotBasicSchema.getFileDesc();
		this.ManageCom = aLSQuotBasicSchema.getManageCom();
		this.ComCode = aLSQuotBasicSchema.getComCode();
		this.MakeOperator = aLSQuotBasicSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotBasicSchema.getMakeDate());
		this.MakeTime = aLSQuotBasicSchema.getMakeTime();
		this.ModifyOperator = aLSQuotBasicSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotBasicSchema.getModifyDate());
		this.ModifyTime = aLSQuotBasicSchema.getModifyTime();
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
			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("PreCustomerNo") == null )
				this.PreCustomerNo = null;
			else
				this.PreCustomerNo = rs.getString("PreCustomerNo").trim();

			if( rs.getString("PreCustomerName") == null )
				this.PreCustomerName = null;
			else
				this.PreCustomerName = rs.getString("PreCustomerName").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			if( rs.getString("BusiCategory") == null )
				this.BusiCategory = null;
			else
				this.BusiCategory = rs.getString("BusiCategory").trim();

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("CustomerIntro") == null )
				this.CustomerIntro = null;
			else
				this.CustomerIntro = rs.getString("CustomerIntro").trim();

			if( rs.getString("ProdType") == null )
				this.ProdType = null;
			else
				this.ProdType = rs.getString("ProdType").trim();

			if( rs.getString("SaleChannel") == null )
				this.SaleChannel = null;
			else
				this.SaleChannel = rs.getString("SaleChannel").trim();

			if( rs.getString("PremMode") == null )
				this.PremMode = null;
			else
				this.PremMode = rs.getString("PremMode").trim();

			this.PrePrem = rs.getDouble("PrePrem");
			if( rs.getString("RenewFlag") == null )
				this.RenewFlag = null;
			else
				this.RenewFlag = rs.getString("RenewFlag").trim();

			if( rs.getString("BlanketFlag") == null )
				this.BlanketFlag = null;
			else
				this.BlanketFlag = rs.getString("BlanketFlag").trim();

			if( rs.getString("ElasticFlag") == null )
				this.ElasticFlag = null;
			else
				this.ElasticFlag = rs.getString("ElasticFlag").trim();

			if( rs.getString("PayIntv") == null )
				this.PayIntv = null;
			else
				this.PayIntv = rs.getString("PayIntv").trim();

			this.InsuPeriod = rs.getInt("InsuPeriod");
			if( rs.getString("InsuPeriodFlag") == null )
				this.InsuPeriodFlag = null;
			else
				this.InsuPeriodFlag = rs.getString("InsuPeriodFlag").trim();

			if( rs.getString("ValDateType") == null )
				this.ValDateType = null;
			else
				this.ValDateType = rs.getString("ValDateType").trim();

			this.AppointValDate = rs.getDate("AppointValDate");
			if( rs.getString("Coinsurance") == null )
				this.Coinsurance = null;
			else
				this.Coinsurance = rs.getString("Coinsurance").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("QuotState") == null )
				this.QuotState = null;
			else
				this.QuotState = rs.getString("QuotState").trim();

			if( rs.getString("FileReason") == null )
				this.FileReason = null;
			else
				this.FileReason = rs.getString("FileReason").trim();

			if( rs.getString("FileDesc") == null )
				this.FileDesc = null;
			else
				this.FileDesc = rs.getString("FileDesc").trim();

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
			logger.debug("数据库中的LSQuotBasic表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotBasicSchema getSchema()
	{
		LSQuotBasicSchema aLSQuotBasicSchema = new LSQuotBasicSchema();
		aLSQuotBasicSchema.setSchema(this);
		return aLSQuotBasicSchema;
	}

	public LSQuotBasicDB getDB()
	{
		LSQuotBasicDB aDBOper = new LSQuotBasicDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotBasic描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerIntro)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProdType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChannel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrePrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlanketFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ElasticFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValDateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppointValDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Coinsurance)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuotState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileDesc)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotBasic>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PreCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PreCustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BusiCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CustomerIntro = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ProdType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SaleChannel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PremMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PrePrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			RenewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BlanketFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ElasticFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			InsuPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			InsuPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ValDateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AppointValDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			Coinsurance = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			QuotState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			FileReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			FileDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotBasicSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerNo));
		}
		if (FCode.equalsIgnoreCase("PreCustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCustomerName));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("BusiCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiCategory));
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equalsIgnoreCase("CustomerIntro"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerIntro));
		}
		if (FCode.equalsIgnoreCase("ProdType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProdType));
		}
		if (FCode.equalsIgnoreCase("SaleChannel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChannel));
		}
		if (FCode.equalsIgnoreCase("PremMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremMode));
		}
		if (FCode.equalsIgnoreCase("PrePrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrePrem));
		}
		if (FCode.equalsIgnoreCase("RenewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewFlag));
		}
		if (FCode.equalsIgnoreCase("BlanketFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlanketFlag));
		}
		if (FCode.equalsIgnoreCase("ElasticFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ElasticFlag));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("InsuPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriod));
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValDateType));
		}
		if (FCode.equalsIgnoreCase("AppointValDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppointValDate()));
		}
		if (FCode.equalsIgnoreCase("Coinsurance"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Coinsurance));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("QuotState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotState));
		}
		if (FCode.equalsIgnoreCase("FileReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileReason));
		}
		if (FCode.equalsIgnoreCase("FileDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileDesc));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 1:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PreCustomerName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BusiCategory);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CustomerIntro);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ProdType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SaleChannel);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PremMode);
				break;
			case 13:
				strFieldValue = String.valueOf(PrePrem);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RenewFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BlanketFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ElasticFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(PayIntv);
				break;
			case 18:
				strFieldValue = String.valueOf(InsuPeriod);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(InsuPeriodFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ValDateType);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppointValDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Coinsurance);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(QuotState);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(FileReason);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(FileDesc);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 36:
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

		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotNo = FValue.trim();
			}
			else
				QuotNo = null;
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				QuotBatNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("PreCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerNo = FValue.trim();
			}
			else
				PreCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("PreCustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCustomerName = FValue.trim();
			}
			else
				PreCustomerName = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNature = FValue.trim();
			}
			else
				GrpNature = null;
		}
		if (FCode.equalsIgnoreCase("BusiCategory"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiCategory = FValue.trim();
			}
			else
				BusiCategory = null;
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Address = FValue.trim();
			}
			else
				Address = null;
		}
		if (FCode.equalsIgnoreCase("CustomerIntro"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerIntro = FValue.trim();
			}
			else
				CustomerIntro = null;
		}
		if (FCode.equalsIgnoreCase("ProdType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProdType = FValue.trim();
			}
			else
				ProdType = null;
		}
		if (FCode.equalsIgnoreCase("SaleChannel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChannel = FValue.trim();
			}
			else
				SaleChannel = null;
		}
		if (FCode.equalsIgnoreCase("PremMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremMode = FValue.trim();
			}
			else
				PremMode = null;
		}
		if (FCode.equalsIgnoreCase("PrePrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrePrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("RenewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewFlag = FValue.trim();
			}
			else
				RenewFlag = null;
		}
		if (FCode.equalsIgnoreCase("BlanketFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlanketFlag = FValue.trim();
			}
			else
				BlanketFlag = null;
		}
		if (FCode.equalsIgnoreCase("ElasticFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ElasticFlag = FValue.trim();
			}
			else
				ElasticFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayIntv = FValue.trim();
			}
			else
				PayIntv = null;
		}
		if (FCode.equalsIgnoreCase("InsuPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuPeriodFlag = FValue.trim();
			}
			else
				InsuPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValDateType = FValue.trim();
			}
			else
				ValDateType = null;
		}
		if (FCode.equalsIgnoreCase("AppointValDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppointValDate = fDate.getDate( FValue );
			}
			else
				AppointValDate = null;
		}
		if (FCode.equalsIgnoreCase("Coinsurance"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Coinsurance = FValue.trim();
			}
			else
				Coinsurance = null;
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("QuotState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotState = FValue.trim();
			}
			else
				QuotState = null;
		}
		if (FCode.equalsIgnoreCase("FileReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileReason = FValue.trim();
			}
			else
				FileReason = null;
		}
		if (FCode.equalsIgnoreCase("FileDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileDesc = FValue.trim();
			}
			else
				FileDesc = null;
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
		LSQuotBasicSchema other = (LSQuotBasicSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& PreCustomerNo.equals(other.getPreCustomerNo())
			&& PreCustomerName.equals(other.getPreCustomerName())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& GrpNature.equals(other.getGrpNature())
			&& BusiCategory.equals(other.getBusiCategory())
			&& Address.equals(other.getAddress())
			&& CustomerIntro.equals(other.getCustomerIntro())
			&& ProdType.equals(other.getProdType())
			&& SaleChannel.equals(other.getSaleChannel())
			&& PremMode.equals(other.getPremMode())
			&& PrePrem == other.getPrePrem()
			&& RenewFlag.equals(other.getRenewFlag())
			&& BlanketFlag.equals(other.getBlanketFlag())
			&& ElasticFlag.equals(other.getElasticFlag())
			&& PayIntv.equals(other.getPayIntv())
			&& InsuPeriod == other.getInsuPeriod()
			&& InsuPeriodFlag.equals(other.getInsuPeriodFlag())
			&& ValDateType.equals(other.getValDateType())
			&& fDate.getString(AppointValDate).equals(other.getAppointValDate())
			&& Coinsurance.equals(other.getCoinsurance())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& QuotState.equals(other.getQuotState())
			&& FileReason.equals(other.getFileReason())
			&& FileDesc.equals(other.getFileDesc())
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
		if( strFieldName.equals("QuotNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("PreCustomerName") ) {
			return 3;
		}
		if( strFieldName.equals("IDType") ) {
			return 4;
		}
		if( strFieldName.equals("IDNo") ) {
			return 5;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 6;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return 7;
		}
		if( strFieldName.equals("Address") ) {
			return 8;
		}
		if( strFieldName.equals("CustomerIntro") ) {
			return 9;
		}
		if( strFieldName.equals("ProdType") ) {
			return 10;
		}
		if( strFieldName.equals("SaleChannel") ) {
			return 11;
		}
		if( strFieldName.equals("PremMode") ) {
			return 12;
		}
		if( strFieldName.equals("PrePrem") ) {
			return 13;
		}
		if( strFieldName.equals("RenewFlag") ) {
			return 14;
		}
		if( strFieldName.equals("BlanketFlag") ) {
			return 15;
		}
		if( strFieldName.equals("ElasticFlag") ) {
			return 16;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 17;
		}
		if( strFieldName.equals("InsuPeriod") ) {
			return 18;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return 19;
		}
		if( strFieldName.equals("ValDateType") ) {
			return 20;
		}
		if( strFieldName.equals("AppointValDate") ) {
			return 21;
		}
		if( strFieldName.equals("Coinsurance") ) {
			return 22;
		}
		if( strFieldName.equals("Segment1") ) {
			return 23;
		}
		if( strFieldName.equals("Segment2") ) {
			return 24;
		}
		if( strFieldName.equals("Segment3") ) {
			return 25;
		}
		if( strFieldName.equals("QuotState") ) {
			return 26;
		}
		if( strFieldName.equals("FileReason") ) {
			return 27;
		}
		if( strFieldName.equals("FileDesc") ) {
			return 28;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 29;
		}
		if( strFieldName.equals("ComCode") ) {
			return 30;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 31;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 32;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 34;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 35;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 36;
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
				strFieldName = "QuotNo";
				break;
			case 1:
				strFieldName = "QuotBatNo";
				break;
			case 2:
				strFieldName = "PreCustomerNo";
				break;
			case 3:
				strFieldName = "PreCustomerName";
				break;
			case 4:
				strFieldName = "IDType";
				break;
			case 5:
				strFieldName = "IDNo";
				break;
			case 6:
				strFieldName = "GrpNature";
				break;
			case 7:
				strFieldName = "BusiCategory";
				break;
			case 8:
				strFieldName = "Address";
				break;
			case 9:
				strFieldName = "CustomerIntro";
				break;
			case 10:
				strFieldName = "ProdType";
				break;
			case 11:
				strFieldName = "SaleChannel";
				break;
			case 12:
				strFieldName = "PremMode";
				break;
			case 13:
				strFieldName = "PrePrem";
				break;
			case 14:
				strFieldName = "RenewFlag";
				break;
			case 15:
				strFieldName = "BlanketFlag";
				break;
			case 16:
				strFieldName = "ElasticFlag";
				break;
			case 17:
				strFieldName = "PayIntv";
				break;
			case 18:
				strFieldName = "InsuPeriod";
				break;
			case 19:
				strFieldName = "InsuPeriodFlag";
				break;
			case 20:
				strFieldName = "ValDateType";
				break;
			case 21:
				strFieldName = "AppointValDate";
				break;
			case 22:
				strFieldName = "Coinsurance";
				break;
			case 23:
				strFieldName = "Segment1";
				break;
			case 24:
				strFieldName = "Segment2";
				break;
			case 25:
				strFieldName = "Segment3";
				break;
			case 26:
				strFieldName = "QuotState";
				break;
			case 27:
				strFieldName = "FileReason";
				break;
			case 28:
				strFieldName = "FileDesc";
				break;
			case 29:
				strFieldName = "ManageCom";
				break;
			case 30:
				strFieldName = "ComCode";
				break;
			case 31:
				strFieldName = "MakeOperator";
				break;
			case 32:
				strFieldName = "MakeDate";
				break;
			case 33:
				strFieldName = "MakeTime";
				break;
			case 34:
				strFieldName = "ModifyOperator";
				break;
			case 35:
				strFieldName = "ModifyDate";
				break;
			case 36:
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PreCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreCustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerIntro") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProdType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChannel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrePrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RenewFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlanketFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ElasticFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValDateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppointValDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Coinsurance") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileDesc") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
