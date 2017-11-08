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
import com.sinosoft.lis.db.LCPrintAppntDB;

/*
 * <p>ClassName: LCPrintAppntSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCPrintAppntSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPrintAppntSchema.class);
	// @Field
	/** 报价单号 */
	private String OfferListNo;
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 投保人编码 */
	private String CustomerNo;
	/** 地址码 */
	private String AddressNo;
	/** 投保人名称 */
	private String GrpName;
	/** 汉语拼音名称 */
	private String GrpNamePY;
	/** 检索关键字 */
	private String SearchKeyWord;
	/** 经营范围 */
	private String MainBusiness;
	/** 法人 */
	private String Corporation;
	/** 法人证件类型 */
	private String CorIDType;
	/** 法人证件号码 */
	private String CorID;
	/** 法人证件有效止期 */
	private Date CorIDExpiryDate;
	/** 行业大类 */
	private String BusinessCategory;
	/** 行业分类 */
	private String BusiCategory;
	/** 成立日期 */
	private Date FoundDate;
	/** 社保保险登记证号 */
	private String SocialInsuCode;
	/** 单位性质 */
	private String GrpNature;
	/** 单位证件类型 */
	private String GrpIDType;
	/** 单位证件号码 */
	private String GrpID;
	/** 单位证件有效止期 */
	private Date GrpIDExpiryDate;
	/** 单位电话 */
	private String Phone;
	/** 单位传真 */
	private String Fax;
	/** 单位emaill */
	private String EMail;
	/** 员工总人数 */
	private int SumNumPeople;
	/** 主被保险人数 */
	private int MainContNumber;
	/** 附属被保险人数 */
	private int RelatedContNumber;
	/** 在职人数 */
	private int OnJobNumber;
	/** 退休人数 */
	private int RetireNumber;
	/** 其它人数 */
	private int OtherNumber;
	/** 注册资本 */
	private double RgtCapital;
	/** 资产总额 */
	private double TotalAssets;
	/** 净资产收益率 */
	private double NetProfitRate;
	/** 负责人 */
	private String Satrap;
	/** 实际控制人 */
	private String ActuCtrl;
	/** 营业执照号 */
	private String License;
	/** 组织机构代码 */
	private String OrganizationCode;
	/** 税务登记证 */
	private String TaxCode;
	/** 单位联系人 */
	private String LinkMan;
	/** 联系人证件类型 */
	private String LinkIDType;
	/** 联系人证件号码 */
	private String LinkID;
	/** 联系人证件有效止期 */
	private Date LinkIDExpiryDate;
	/** 联系人电话 */
	private String LinkPhone;
	/** 状态 */
	private String State;
	/** 生效日期类型 */
	private String ValDateType;
	/** 生效日期 */
	private Date ValDate;
	/** 保险期间 */
	private int InsuPeriod;
	/** 保险期间单位 */
	private String InsuPeriodFlag;
	/** 保险年期 */
	private int InsuYears;
	/** 付款方式 */
	private String PayMode;
	/** 保费分摊方式 */
	private String PremMode;
	/** 缴费费方式 */
	private int PayIntv;
	/** 渠道类型 */
	private String ChnlType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 备注 */
	private String Remark;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
	/** 承保机构 */
	private String AppManageCom;
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

	public static final int FIELDNUM = 67;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPrintAppntSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpPropNo";

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
		LCPrintAppntSchema cloned = (LCPrintAppntSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getOfferListNo()
	{
		return OfferListNo;
	}
	public void setOfferListNo(String aOfferListNo)
	{
		if(aOfferListNo!=null && aOfferListNo.length()>20)
			throw new IllegalArgumentException("报价单号OfferListNo值"+aOfferListNo+"的长度"+aOfferListNo.length()+"大于最大值20");
		OfferListNo = aOfferListNo;
	}
	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("团体投保单号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
	}
	/**
	* SGP+两位年份+4位省市行政代码(保监）+9位流水
	*/
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("投保人编码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getAddressNo()
	{
		return AddressNo;
	}
	public void setAddressNo(String aAddressNo)
	{
		if(aAddressNo!=null && aAddressNo.length()>20)
			throw new IllegalArgumentException("地址码AddressNo值"+aAddressNo+"的长度"+aAddressNo.length()+"大于最大值20");
		AddressNo = aAddressNo;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>200)
			throw new IllegalArgumentException("投保人名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值200");
		GrpName = aGrpName;
	}
	public String getGrpNamePY()
	{
		return GrpNamePY;
	}
	public void setGrpNamePY(String aGrpNamePY)
	{
		if(aGrpNamePY!=null && aGrpNamePY.length()>200)
			throw new IllegalArgumentException("汉语拼音名称GrpNamePY值"+aGrpNamePY+"的长度"+aGrpNamePY.length()+"大于最大值200");
		GrpNamePY = aGrpNamePY;
	}
	public String getSearchKeyWord()
	{
		return SearchKeyWord;
	}
	public void setSearchKeyWord(String aSearchKeyWord)
	{
		if(aSearchKeyWord!=null && aSearchKeyWord.length()>200)
			throw new IllegalArgumentException("检索关键字SearchKeyWord值"+aSearchKeyWord+"的长度"+aSearchKeyWord.length()+"大于最大值200");
		SearchKeyWord = aSearchKeyWord;
	}
	public String getMainBusiness()
	{
		return MainBusiness;
	}
	public void setMainBusiness(String aMainBusiness)
	{
		if(aMainBusiness!=null && aMainBusiness.length()>1000)
			throw new IllegalArgumentException("经营范围MainBusiness值"+aMainBusiness+"的长度"+aMainBusiness.length()+"大于最大值1000");
		MainBusiness = aMainBusiness;
	}
	public String getCorporation()
	{
		return Corporation;
	}
	public void setCorporation(String aCorporation)
	{
		if(aCorporation!=null && aCorporation.length()>200)
			throw new IllegalArgumentException("法人Corporation值"+aCorporation+"的长度"+aCorporation.length()+"大于最大值200");
		Corporation = aCorporation;
	}
	public String getCorIDType()
	{
		return CorIDType;
	}
	public void setCorIDType(String aCorIDType)
	{
		if(aCorIDType!=null && aCorIDType.length()>2)
			throw new IllegalArgumentException("法人证件类型CorIDType值"+aCorIDType+"的长度"+aCorIDType.length()+"大于最大值2");
		CorIDType = aCorIDType;
	}
	public String getCorID()
	{
		return CorID;
	}
	public void setCorID(String aCorID)
	{
		if(aCorID!=null && aCorID.length()>30)
			throw new IllegalArgumentException("法人证件号码CorID值"+aCorID+"的长度"+aCorID.length()+"大于最大值30");
		CorID = aCorID;
	}
	public String getCorIDExpiryDate()
	{
		if( CorIDExpiryDate != null )
			return fDate.getString(CorIDExpiryDate);
		else
			return null;
	}
	public void setCorIDExpiryDate(Date aCorIDExpiryDate)
	{
		CorIDExpiryDate = aCorIDExpiryDate;
	}
	public void setCorIDExpiryDate(String aCorIDExpiryDate)
	{
		if (aCorIDExpiryDate != null && !aCorIDExpiryDate.equals("") )
		{
			CorIDExpiryDate = fDate.getDate( aCorIDExpiryDate );
		}
		else
			CorIDExpiryDate = null;
	}

	public String getBusinessCategory()
	{
		return BusinessCategory;
	}
	public void setBusinessCategory(String aBusinessCategory)
	{
		if(aBusinessCategory!=null && aBusinessCategory.length()>10)
			throw new IllegalArgumentException("行业大类BusinessCategory值"+aBusinessCategory+"的长度"+aBusinessCategory.length()+"大于最大值10");
		BusinessCategory = aBusinessCategory;
	}
	public String getBusiCategory()
	{
		return BusiCategory;
	}
	public void setBusiCategory(String aBusiCategory)
	{
		if(aBusiCategory!=null && aBusiCategory.length()>10)
			throw new IllegalArgumentException("行业分类BusiCategory值"+aBusiCategory+"的长度"+aBusiCategory.length()+"大于最大值10");
		BusiCategory = aBusiCategory;
	}
	public String getFoundDate()
	{
		if( FoundDate != null )
			return fDate.getString(FoundDate);
		else
			return null;
	}
	public void setFoundDate(Date aFoundDate)
	{
		FoundDate = aFoundDate;
	}
	public void setFoundDate(String aFoundDate)
	{
		if (aFoundDate != null && !aFoundDate.equals("") )
		{
			FoundDate = fDate.getDate( aFoundDate );
		}
		else
			FoundDate = null;
	}

	public String getSocialInsuCode()
	{
		return SocialInsuCode;
	}
	public void setSocialInsuCode(String aSocialInsuCode)
	{
		if(aSocialInsuCode!=null && aSocialInsuCode.length()>50)
			throw new IllegalArgumentException("社保保险登记证号SocialInsuCode值"+aSocialInsuCode+"的长度"+aSocialInsuCode.length()+"大于最大值50");
		SocialInsuCode = aSocialInsuCode;
	}
	public String getGrpNature()
	{
		return GrpNature;
	}
	public void setGrpNature(String aGrpNature)
	{
		if(aGrpNature!=null && aGrpNature.length()>10)
			throw new IllegalArgumentException("单位性质GrpNature值"+aGrpNature+"的长度"+aGrpNature.length()+"大于最大值10");
		GrpNature = aGrpNature;
	}
	public String getGrpIDType()
	{
		return GrpIDType;
	}
	public void setGrpIDType(String aGrpIDType)
	{
		if(aGrpIDType!=null && aGrpIDType.length()>2)
			throw new IllegalArgumentException("单位证件类型GrpIDType值"+aGrpIDType+"的长度"+aGrpIDType.length()+"大于最大值2");
		GrpIDType = aGrpIDType;
	}
	public String getGrpID()
	{
		return GrpID;
	}
	public void setGrpID(String aGrpID)
	{
		if(aGrpID!=null && aGrpID.length()>30)
			throw new IllegalArgumentException("单位证件号码GrpID值"+aGrpID+"的长度"+aGrpID.length()+"大于最大值30");
		GrpID = aGrpID;
	}
	public String getGrpIDExpiryDate()
	{
		if( GrpIDExpiryDate != null )
			return fDate.getString(GrpIDExpiryDate);
		else
			return null;
	}
	public void setGrpIDExpiryDate(Date aGrpIDExpiryDate)
	{
		GrpIDExpiryDate = aGrpIDExpiryDate;
	}
	public void setGrpIDExpiryDate(String aGrpIDExpiryDate)
	{
		if (aGrpIDExpiryDate != null && !aGrpIDExpiryDate.equals("") )
		{
			GrpIDExpiryDate = fDate.getDate( aGrpIDExpiryDate );
		}
		else
			GrpIDExpiryDate = null;
	}

	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>20)
			throw new IllegalArgumentException("单位电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值20");
		Phone = aPhone;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>20)
			throw new IllegalArgumentException("单位传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值20");
		Fax = aFax;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>60)
			throw new IllegalArgumentException("单位emaillEMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值60");
		EMail = aEMail;
	}
	public int getSumNumPeople()
	{
		return SumNumPeople;
	}
	public void setSumNumPeople(int aSumNumPeople)
	{
		SumNumPeople = aSumNumPeople;
	}
	public void setSumNumPeople(String aSumNumPeople)
	{
		if (aSumNumPeople != null && !aSumNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aSumNumPeople);
			int i = tInteger.intValue();
			SumNumPeople = i;
		}
	}

	public int getMainContNumber()
	{
		return MainContNumber;
	}
	public void setMainContNumber(int aMainContNumber)
	{
		MainContNumber = aMainContNumber;
	}
	public void setMainContNumber(String aMainContNumber)
	{
		if (aMainContNumber != null && !aMainContNumber.equals(""))
		{
			Integer tInteger = new Integer(aMainContNumber);
			int i = tInteger.intValue();
			MainContNumber = i;
		}
	}

	public int getRelatedContNumber()
	{
		return RelatedContNumber;
	}
	public void setRelatedContNumber(int aRelatedContNumber)
	{
		RelatedContNumber = aRelatedContNumber;
	}
	public void setRelatedContNumber(String aRelatedContNumber)
	{
		if (aRelatedContNumber != null && !aRelatedContNumber.equals(""))
		{
			Integer tInteger = new Integer(aRelatedContNumber);
			int i = tInteger.intValue();
			RelatedContNumber = i;
		}
	}

	public int getOnJobNumber()
	{
		return OnJobNumber;
	}
	public void setOnJobNumber(int aOnJobNumber)
	{
		OnJobNumber = aOnJobNumber;
	}
	public void setOnJobNumber(String aOnJobNumber)
	{
		if (aOnJobNumber != null && !aOnJobNumber.equals(""))
		{
			Integer tInteger = new Integer(aOnJobNumber);
			int i = tInteger.intValue();
			OnJobNumber = i;
		}
	}

	public int getRetireNumber()
	{
		return RetireNumber;
	}
	public void setRetireNumber(int aRetireNumber)
	{
		RetireNumber = aRetireNumber;
	}
	public void setRetireNumber(String aRetireNumber)
	{
		if (aRetireNumber != null && !aRetireNumber.equals(""))
		{
			Integer tInteger = new Integer(aRetireNumber);
			int i = tInteger.intValue();
			RetireNumber = i;
		}
	}

	public int getOtherNumber()
	{
		return OtherNumber;
	}
	public void setOtherNumber(int aOtherNumber)
	{
		OtherNumber = aOtherNumber;
	}
	public void setOtherNumber(String aOtherNumber)
	{
		if (aOtherNumber != null && !aOtherNumber.equals(""))
		{
			Integer tInteger = new Integer(aOtherNumber);
			int i = tInteger.intValue();
			OtherNumber = i;
		}
	}

	public double getRgtCapital()
	{
		return RgtCapital;
	}
	public void setRgtCapital(double aRgtCapital)
	{
		RgtCapital = aRgtCapital;
	}
	public void setRgtCapital(String aRgtCapital)
	{
		if (aRgtCapital != null && !aRgtCapital.equals(""))
		{
			Double tDouble = new Double(aRgtCapital);
			double d = tDouble.doubleValue();
			RgtCapital = d;
		}
	}

	public double getTotalAssets()
	{
		return TotalAssets;
	}
	public void setTotalAssets(double aTotalAssets)
	{
		TotalAssets = aTotalAssets;
	}
	public void setTotalAssets(String aTotalAssets)
	{
		if (aTotalAssets != null && !aTotalAssets.equals(""))
		{
			Double tDouble = new Double(aTotalAssets);
			double d = tDouble.doubleValue();
			TotalAssets = d;
		}
	}

	public double getNetProfitRate()
	{
		return NetProfitRate;
	}
	public void setNetProfitRate(double aNetProfitRate)
	{
		NetProfitRate = aNetProfitRate;
	}
	public void setNetProfitRate(String aNetProfitRate)
	{
		if (aNetProfitRate != null && !aNetProfitRate.equals(""))
		{
			Double tDouble = new Double(aNetProfitRate);
			double d = tDouble.doubleValue();
			NetProfitRate = d;
		}
	}

	public String getSatrap()
	{
		return Satrap;
	}
	public void setSatrap(String aSatrap)
	{
		if(aSatrap!=null && aSatrap.length()>200)
			throw new IllegalArgumentException("负责人Satrap值"+aSatrap+"的长度"+aSatrap.length()+"大于最大值200");
		Satrap = aSatrap;
	}
	public String getActuCtrl()
	{
		return ActuCtrl;
	}
	public void setActuCtrl(String aActuCtrl)
	{
		if(aActuCtrl!=null && aActuCtrl.length()>200)
			throw new IllegalArgumentException("实际控制人ActuCtrl值"+aActuCtrl+"的长度"+aActuCtrl.length()+"大于最大值200");
		ActuCtrl = aActuCtrl;
	}
	public String getLicense()
	{
		return License;
	}
	public void setLicense(String aLicense)
	{
		if(aLicense!=null && aLicense.length()>20)
			throw new IllegalArgumentException("营业执照号License值"+aLicense+"的长度"+aLicense.length()+"大于最大值20");
		License = aLicense;
	}
	public String getOrganizationCode()
	{
		return OrganizationCode;
	}
	public void setOrganizationCode(String aOrganizationCode)
	{
		if(aOrganizationCode!=null && aOrganizationCode.length()>20)
			throw new IllegalArgumentException("组织机构代码OrganizationCode值"+aOrganizationCode+"的长度"+aOrganizationCode.length()+"大于最大值20");
		OrganizationCode = aOrganizationCode;
	}
	public String getTaxCode()
	{
		return TaxCode;
	}
	public void setTaxCode(String aTaxCode)
	{
		if(aTaxCode!=null && aTaxCode.length()>20)
			throw new IllegalArgumentException("税务登记证TaxCode值"+aTaxCode+"的长度"+aTaxCode.length()+"大于最大值20");
		TaxCode = aTaxCode;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		if(aLinkMan!=null && aLinkMan.length()>200)
			throw new IllegalArgumentException("单位联系人LinkMan值"+aLinkMan+"的长度"+aLinkMan.length()+"大于最大值200");
		LinkMan = aLinkMan;
	}
	public String getLinkIDType()
	{
		return LinkIDType;
	}
	public void setLinkIDType(String aLinkIDType)
	{
		if(aLinkIDType!=null && aLinkIDType.length()>2)
			throw new IllegalArgumentException("联系人证件类型LinkIDType值"+aLinkIDType+"的长度"+aLinkIDType.length()+"大于最大值2");
		LinkIDType = aLinkIDType;
	}
	public String getLinkID()
	{
		return LinkID;
	}
	public void setLinkID(String aLinkID)
	{
		if(aLinkID!=null && aLinkID.length()>30)
			throw new IllegalArgumentException("联系人证件号码LinkID值"+aLinkID+"的长度"+aLinkID.length()+"大于最大值30");
		LinkID = aLinkID;
	}
	public String getLinkIDExpiryDate()
	{
		if( LinkIDExpiryDate != null )
			return fDate.getString(LinkIDExpiryDate);
		else
			return null;
	}
	public void setLinkIDExpiryDate(Date aLinkIDExpiryDate)
	{
		LinkIDExpiryDate = aLinkIDExpiryDate;
	}
	public void setLinkIDExpiryDate(String aLinkIDExpiryDate)
	{
		if (aLinkIDExpiryDate != null && !aLinkIDExpiryDate.equals("") )
		{
			LinkIDExpiryDate = fDate.getDate( aLinkIDExpiryDate );
		}
		else
			LinkIDExpiryDate = null;
	}

	public String getLinkPhone()
	{
		return LinkPhone;
	}
	public void setLinkPhone(String aLinkPhone)
	{
		if(aLinkPhone!=null && aLinkPhone.length()>20)
			throw new IllegalArgumentException("联系人电话LinkPhone值"+aLinkPhone+"的长度"+aLinkPhone.length()+"大于最大值20");
		LinkPhone = aLinkPhone;
	}
	/**
	* 00-未打印<p>
	* 01-已打印
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getValDateType()
	{
		return ValDateType;
	}
	public void setValDateType(String aValDateType)
	{
		if(aValDateType!=null && aValDateType.length()>2)
			throw new IllegalArgumentException("生效日期类型ValDateType值"+aValDateType+"的长度"+aValDateType.length()+"大于最大值2");
		ValDateType = aValDateType;
	}
	public String getValDate()
	{
		if( ValDate != null )
			return fDate.getString(ValDate);
		else
			return null;
	}
	public void setValDate(Date aValDate)
	{
		ValDate = aValDate;
	}
	public void setValDate(String aValDate)
	{
		if (aValDate != null && !aValDate.equals("") )
		{
			ValDate = fDate.getDate( aValDate );
		}
		else
			ValDate = null;
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

	/**
	* A-岁，D-天，M-月，Y-年
	*/
	public String getInsuPeriodFlag()
	{
		return InsuPeriodFlag;
	}
	public void setInsuPeriodFlag(String aInsuPeriodFlag)
	{
		if(aInsuPeriodFlag!=null && aInsuPeriodFlag.length()>1)
			throw new IllegalArgumentException("保险期间单位InsuPeriodFlag值"+aInsuPeriodFlag+"的长度"+aInsuPeriodFlag.length()+"大于最大值1");
		InsuPeriodFlag = aInsuPeriodFlag;
	}
	public int getInsuYears()
	{
		return InsuYears;
	}
	public void setInsuYears(int aInsuYears)
	{
		InsuYears = aInsuYears;
	}
	public void setInsuYears(String aInsuYears)
	{
		if (aInsuYears != null && !aInsuYears.equals(""))
		{
			Integer tInteger = new Integer(aInsuYears);
			int i = tInteger.intValue();
			InsuYears = i;
		}
	}

	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		if(aPayMode!=null && aPayMode.length()>2)
			throw new IllegalArgumentException("付款方式PayMode值"+aPayMode+"的长度"+aPayMode.length()+"大于最大值2");
		PayMode = aPayMode;
	}
	public String getPremMode()
	{
		return PremMode;
	}
	public void setPremMode(String aPremMode)
	{
		if(aPremMode!=null && aPremMode.length()>2)
			throw new IllegalArgumentException("保费分摊方式PremMode值"+aPremMode+"的长度"+aPremMode.length()+"大于最大值2");
		PremMode = aPremMode;
	}
	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* 1-个人，2-团体，3-银保
	*/
	public String getChnlType()
	{
		return ChnlType;
	}
	public void setChnlType(String aChnlType)
	{
		if(aChnlType!=null && aChnlType.length()>10)
			throw new IllegalArgumentException("渠道类型ChnlType值"+aChnlType+"的长度"+aChnlType.length()+"大于最大值10");
		ChnlType = aChnlType;
	}
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		if(aSaleChnl!=null && aSaleChnl.length()>10)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值10");
		SaleChnl = aSaleChnl;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>30)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值30");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>30)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值30");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>30)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值30");
		Segment3 = aSegment3;
	}
	public String getAppManageCom()
	{
		return AppManageCom;
	}
	public void setAppManageCom(String aAppManageCom)
	{
		if(aAppManageCom!=null && aAppManageCom.length()>20)
			throw new IllegalArgumentException("承保机构AppManageCom值"+aAppManageCom+"的长度"+aAppManageCom.length()+"大于最大值20");
		AppManageCom = aAppManageCom;
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
	* 使用另外一个 LCPrintAppntSchema 对象给 Schema 赋值
	* @param: aLCPrintAppntSchema LCPrintAppntSchema
	**/
	public void setSchema(LCPrintAppntSchema aLCPrintAppntSchema)
	{
		this.OfferListNo = aLCPrintAppntSchema.getOfferListNo();
		this.GrpPropNo = aLCPrintAppntSchema.getGrpPropNo();
		this.GrpContNo = aLCPrintAppntSchema.getGrpContNo();
		this.CustomerNo = aLCPrintAppntSchema.getCustomerNo();
		this.AddressNo = aLCPrintAppntSchema.getAddressNo();
		this.GrpName = aLCPrintAppntSchema.getGrpName();
		this.GrpNamePY = aLCPrintAppntSchema.getGrpNamePY();
		this.SearchKeyWord = aLCPrintAppntSchema.getSearchKeyWord();
		this.MainBusiness = aLCPrintAppntSchema.getMainBusiness();
		this.Corporation = aLCPrintAppntSchema.getCorporation();
		this.CorIDType = aLCPrintAppntSchema.getCorIDType();
		this.CorID = aLCPrintAppntSchema.getCorID();
		this.CorIDExpiryDate = fDate.getDate( aLCPrintAppntSchema.getCorIDExpiryDate());
		this.BusinessCategory = aLCPrintAppntSchema.getBusinessCategory();
		this.BusiCategory = aLCPrintAppntSchema.getBusiCategory();
		this.FoundDate = fDate.getDate( aLCPrintAppntSchema.getFoundDate());
		this.SocialInsuCode = aLCPrintAppntSchema.getSocialInsuCode();
		this.GrpNature = aLCPrintAppntSchema.getGrpNature();
		this.GrpIDType = aLCPrintAppntSchema.getGrpIDType();
		this.GrpID = aLCPrintAppntSchema.getGrpID();
		this.GrpIDExpiryDate = fDate.getDate( aLCPrintAppntSchema.getGrpIDExpiryDate());
		this.Phone = aLCPrintAppntSchema.getPhone();
		this.Fax = aLCPrintAppntSchema.getFax();
		this.EMail = aLCPrintAppntSchema.getEMail();
		this.SumNumPeople = aLCPrintAppntSchema.getSumNumPeople();
		this.MainContNumber = aLCPrintAppntSchema.getMainContNumber();
		this.RelatedContNumber = aLCPrintAppntSchema.getRelatedContNumber();
		this.OnJobNumber = aLCPrintAppntSchema.getOnJobNumber();
		this.RetireNumber = aLCPrintAppntSchema.getRetireNumber();
		this.OtherNumber = aLCPrintAppntSchema.getOtherNumber();
		this.RgtCapital = aLCPrintAppntSchema.getRgtCapital();
		this.TotalAssets = aLCPrintAppntSchema.getTotalAssets();
		this.NetProfitRate = aLCPrintAppntSchema.getNetProfitRate();
		this.Satrap = aLCPrintAppntSchema.getSatrap();
		this.ActuCtrl = aLCPrintAppntSchema.getActuCtrl();
		this.License = aLCPrintAppntSchema.getLicense();
		this.OrganizationCode = aLCPrintAppntSchema.getOrganizationCode();
		this.TaxCode = aLCPrintAppntSchema.getTaxCode();
		this.LinkMan = aLCPrintAppntSchema.getLinkMan();
		this.LinkIDType = aLCPrintAppntSchema.getLinkIDType();
		this.LinkID = aLCPrintAppntSchema.getLinkID();
		this.LinkIDExpiryDate = fDate.getDate( aLCPrintAppntSchema.getLinkIDExpiryDate());
		this.LinkPhone = aLCPrintAppntSchema.getLinkPhone();
		this.State = aLCPrintAppntSchema.getState();
		this.ValDateType = aLCPrintAppntSchema.getValDateType();
		this.ValDate = fDate.getDate( aLCPrintAppntSchema.getValDate());
		this.InsuPeriod = aLCPrintAppntSchema.getInsuPeriod();
		this.InsuPeriodFlag = aLCPrintAppntSchema.getInsuPeriodFlag();
		this.InsuYears = aLCPrintAppntSchema.getInsuYears();
		this.PayMode = aLCPrintAppntSchema.getPayMode();
		this.PremMode = aLCPrintAppntSchema.getPremMode();
		this.PayIntv = aLCPrintAppntSchema.getPayIntv();
		this.ChnlType = aLCPrintAppntSchema.getChnlType();
		this.SaleChnl = aLCPrintAppntSchema.getSaleChnl();
		this.Remark = aLCPrintAppntSchema.getRemark();
		this.Segment1 = aLCPrintAppntSchema.getSegment1();
		this.Segment2 = aLCPrintAppntSchema.getSegment2();
		this.Segment3 = aLCPrintAppntSchema.getSegment3();
		this.AppManageCom = aLCPrintAppntSchema.getAppManageCom();
		this.ManageCom = aLCPrintAppntSchema.getManageCom();
		this.ComCode = aLCPrintAppntSchema.getComCode();
		this.MakeOperator = aLCPrintAppntSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLCPrintAppntSchema.getMakeDate());
		this.MakeTime = aLCPrintAppntSchema.getMakeTime();
		this.ModifyOperator = aLCPrintAppntSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLCPrintAppntSchema.getModifyDate());
		this.ModifyTime = aLCPrintAppntSchema.getModifyTime();
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
			if( rs.getString("OfferListNo") == null )
				this.OfferListNo = null;
			else
				this.OfferListNo = rs.getString("OfferListNo").trim();

			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("GrpNamePY") == null )
				this.GrpNamePY = null;
			else
				this.GrpNamePY = rs.getString("GrpNamePY").trim();

			if( rs.getString("SearchKeyWord") == null )
				this.SearchKeyWord = null;
			else
				this.SearchKeyWord = rs.getString("SearchKeyWord").trim();

			if( rs.getString("MainBusiness") == null )
				this.MainBusiness = null;
			else
				this.MainBusiness = rs.getString("MainBusiness").trim();

			if( rs.getString("Corporation") == null )
				this.Corporation = null;
			else
				this.Corporation = rs.getString("Corporation").trim();

			if( rs.getString("CorIDType") == null )
				this.CorIDType = null;
			else
				this.CorIDType = rs.getString("CorIDType").trim();

			if( rs.getString("CorID") == null )
				this.CorID = null;
			else
				this.CorID = rs.getString("CorID").trim();

			this.CorIDExpiryDate = rs.getDate("CorIDExpiryDate");
			if( rs.getString("BusinessCategory") == null )
				this.BusinessCategory = null;
			else
				this.BusinessCategory = rs.getString("BusinessCategory").trim();

			if( rs.getString("BusiCategory") == null )
				this.BusiCategory = null;
			else
				this.BusiCategory = rs.getString("BusiCategory").trim();

			this.FoundDate = rs.getDate("FoundDate");
			if( rs.getString("SocialInsuCode") == null )
				this.SocialInsuCode = null;
			else
				this.SocialInsuCode = rs.getString("SocialInsuCode").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			if( rs.getString("GrpIDType") == null )
				this.GrpIDType = null;
			else
				this.GrpIDType = rs.getString("GrpIDType").trim();

			if( rs.getString("GrpID") == null )
				this.GrpID = null;
			else
				this.GrpID = rs.getString("GrpID").trim();

			this.GrpIDExpiryDate = rs.getDate("GrpIDExpiryDate");
			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			this.SumNumPeople = rs.getInt("SumNumPeople");
			this.MainContNumber = rs.getInt("MainContNumber");
			this.RelatedContNumber = rs.getInt("RelatedContNumber");
			this.OnJobNumber = rs.getInt("OnJobNumber");
			this.RetireNumber = rs.getInt("RetireNumber");
			this.OtherNumber = rs.getInt("OtherNumber");
			this.RgtCapital = rs.getDouble("RgtCapital");
			this.TotalAssets = rs.getDouble("TotalAssets");
			this.NetProfitRate = rs.getDouble("NetProfitRate");
			if( rs.getString("Satrap") == null )
				this.Satrap = null;
			else
				this.Satrap = rs.getString("Satrap").trim();

			if( rs.getString("ActuCtrl") == null )
				this.ActuCtrl = null;
			else
				this.ActuCtrl = rs.getString("ActuCtrl").trim();

			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("OrganizationCode") == null )
				this.OrganizationCode = null;
			else
				this.OrganizationCode = rs.getString("OrganizationCode").trim();

			if( rs.getString("TaxCode") == null )
				this.TaxCode = null;
			else
				this.TaxCode = rs.getString("TaxCode").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			if( rs.getString("LinkIDType") == null )
				this.LinkIDType = null;
			else
				this.LinkIDType = rs.getString("LinkIDType").trim();

			if( rs.getString("LinkID") == null )
				this.LinkID = null;
			else
				this.LinkID = rs.getString("LinkID").trim();

			this.LinkIDExpiryDate = rs.getDate("LinkIDExpiryDate");
			if( rs.getString("LinkPhone") == null )
				this.LinkPhone = null;
			else
				this.LinkPhone = rs.getString("LinkPhone").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ValDateType") == null )
				this.ValDateType = null;
			else
				this.ValDateType = rs.getString("ValDateType").trim();

			this.ValDate = rs.getDate("ValDate");
			this.InsuPeriod = rs.getInt("InsuPeriod");
			if( rs.getString("InsuPeriodFlag") == null )
				this.InsuPeriodFlag = null;
			else
				this.InsuPeriodFlag = rs.getString("InsuPeriodFlag").trim();

			this.InsuYears = rs.getInt("InsuYears");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PremMode") == null )
				this.PremMode = null;
			else
				this.PremMode = rs.getString("PremMode").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("ChnlType") == null )
				this.ChnlType = null;
			else
				this.ChnlType = rs.getString("ChnlType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("AppManageCom") == null )
				this.AppManageCom = null;
			else
				this.AppManageCom = rs.getString("AppManageCom").trim();

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
			logger.debug("数据库中的LCPrintAppnt表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPrintAppntSchema getSchema()
	{
		LCPrintAppntSchema aLCPrintAppntSchema = new LCPrintAppntSchema();
		aLCPrintAppntSchema.setSchema(this);
		return aLCPrintAppntSchema;
	}

	public LCPrintAppntDB getDB()
	{
		LCPrintAppntDB aDBOper = new LCPrintAppntDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPrintAppnt描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(OfferListNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SearchKeyWord)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBusiness)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Corporation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CorIDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GrpIDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MainContNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelatedContNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnJobNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RetireNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RgtCapital));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalAssets));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetProfitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Satrap)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActuCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganizationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaxCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LinkIDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValDateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChnlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPrintAppnt>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			OfferListNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SearchKeyWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MainBusiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CorIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CorID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CorIDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			BusinessCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BusiCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			SocialInsuCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			GrpIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			GrpID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GrpIDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			SumNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			MainContNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			RelatedContNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			OnJobNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			RetireNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).intValue();
			OtherNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).intValue();
			RgtCapital = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			TotalAssets = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ActuCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			OrganizationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			TaxCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			LinkIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			LinkID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			LinkIDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			LinkPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ValDateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			ValDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			InsuPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).intValue();
			InsuPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			InsuYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			PremMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			ChnlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			AppManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntSchema";
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
		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OfferListNo));
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("GrpNamePY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNamePY));
		}
		if (FCode.equalsIgnoreCase("SearchKeyWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SearchKeyWord));
		}
		if (FCode.equalsIgnoreCase("MainBusiness"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainBusiness));
		}
		if (FCode.equalsIgnoreCase("Corporation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Corporation));
		}
		if (FCode.equalsIgnoreCase("CorIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CorIDType));
		}
		if (FCode.equalsIgnoreCase("CorID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CorID));
		}
		if (FCode.equalsIgnoreCase("CorIDExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCorIDExpiryDate()));
		}
		if (FCode.equalsIgnoreCase("BusinessCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessCategory));
		}
		if (FCode.equalsIgnoreCase("BusiCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiCategory));
		}
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
		}
		if (FCode.equalsIgnoreCase("SocialInsuCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuCode));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("GrpIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpIDType));
		}
		if (FCode.equalsIgnoreCase("GrpID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpID));
		}
		if (FCode.equalsIgnoreCase("GrpIDExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGrpIDExpiryDate()));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumNumPeople));
		}
		if (FCode.equalsIgnoreCase("MainContNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainContNumber));
		}
		if (FCode.equalsIgnoreCase("RelatedContNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelatedContNumber));
		}
		if (FCode.equalsIgnoreCase("OnJobNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnJobNumber));
		}
		if (FCode.equalsIgnoreCase("RetireNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetireNumber));
		}
		if (FCode.equalsIgnoreCase("OtherNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNumber));
		}
		if (FCode.equalsIgnoreCase("RgtCapital"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtCapital));
		}
		if (FCode.equalsIgnoreCase("TotalAssets"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalAssets));
		}
		if (FCode.equalsIgnoreCase("NetProfitRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetProfitRate));
		}
		if (FCode.equalsIgnoreCase("Satrap"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Satrap));
		}
		if (FCode.equalsIgnoreCase("ActuCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActuCtrl));
		}
		if (FCode.equalsIgnoreCase("License"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(License));
		}
		if (FCode.equalsIgnoreCase("OrganizationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganizationCode));
		}
		if (FCode.equalsIgnoreCase("TaxCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaxCode));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("LinkIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkIDType));
		}
		if (FCode.equalsIgnoreCase("LinkID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkID));
		}
		if (FCode.equalsIgnoreCase("LinkIDExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLinkIDExpiryDate()));
		}
		if (FCode.equalsIgnoreCase("LinkPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkPhone));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValDateType));
		}
		if (FCode.equalsIgnoreCase("ValDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValDate()));
		}
		if (FCode.equalsIgnoreCase("InsuPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriod));
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYears));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PremMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremMode));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("ChnlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChnlType));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("AppManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(OfferListNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpNamePY);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SearchKeyWord);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MainBusiness);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CorIDType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CorID);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCorIDExpiryDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BusinessCategory);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BusiCategory);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(GrpIDType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(GrpID);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGrpIDExpiryDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 24:
				strFieldValue = String.valueOf(SumNumPeople);
				break;
			case 25:
				strFieldValue = String.valueOf(MainContNumber);
				break;
			case 26:
				strFieldValue = String.valueOf(RelatedContNumber);
				break;
			case 27:
				strFieldValue = String.valueOf(OnJobNumber);
				break;
			case 28:
				strFieldValue = String.valueOf(RetireNumber);
				break;
			case 29:
				strFieldValue = String.valueOf(OtherNumber);
				break;
			case 30:
				strFieldValue = String.valueOf(RgtCapital);
				break;
			case 31:
				strFieldValue = String.valueOf(TotalAssets);
				break;
			case 32:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ActuCtrl);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(OrganizationCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(TaxCode);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(LinkIDType);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(LinkID);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLinkIDExpiryDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(LinkPhone);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ValDateType);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValDate()));
				break;
			case 46:
				strFieldValue = String.valueOf(InsuPeriod);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(InsuPeriodFlag);
				break;
			case 48:
				strFieldValue = String.valueOf(InsuYears);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(PremMode);
				break;
			case 51:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(ChnlType);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(AppManageCom);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 66:
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

		if (FCode.equalsIgnoreCase("OfferListNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OfferListNo = FValue.trim();
			}
			else
				OfferListNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddressNo = FValue.trim();
			}
			else
				AddressNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
		}
		if (FCode.equalsIgnoreCase("GrpNamePY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNamePY = FValue.trim();
			}
			else
				GrpNamePY = null;
		}
		if (FCode.equalsIgnoreCase("SearchKeyWord"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SearchKeyWord = FValue.trim();
			}
			else
				SearchKeyWord = null;
		}
		if (FCode.equalsIgnoreCase("MainBusiness"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainBusiness = FValue.trim();
			}
			else
				MainBusiness = null;
		}
		if (FCode.equalsIgnoreCase("Corporation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Corporation = FValue.trim();
			}
			else
				Corporation = null;
		}
		if (FCode.equalsIgnoreCase("CorIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CorIDType = FValue.trim();
			}
			else
				CorIDType = null;
		}
		if (FCode.equalsIgnoreCase("CorID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CorID = FValue.trim();
			}
			else
				CorID = null;
		}
		if (FCode.equalsIgnoreCase("CorIDExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CorIDExpiryDate = fDate.getDate( FValue );
			}
			else
				CorIDExpiryDate = null;
		}
		if (FCode.equalsIgnoreCase("BusinessCategory"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessCategory = FValue.trim();
			}
			else
				BusinessCategory = null;
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
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FoundDate = fDate.getDate( FValue );
			}
			else
				FoundDate = null;
		}
		if (FCode.equalsIgnoreCase("SocialInsuCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuCode = FValue.trim();
			}
			else
				SocialInsuCode = null;
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
		if (FCode.equalsIgnoreCase("GrpIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpIDType = FValue.trim();
			}
			else
				GrpIDType = null;
		}
		if (FCode.equalsIgnoreCase("GrpID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpID = FValue.trim();
			}
			else
				GrpID = null;
		}
		if (FCode.equalsIgnoreCase("GrpIDExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GrpIDExpiryDate = fDate.getDate( FValue );
			}
			else
				GrpIDExpiryDate = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax = FValue.trim();
			}
			else
				Fax = null;
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail = FValue.trim();
			}
			else
				EMail = null;
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumNumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("MainContNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MainContNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelatedContNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelatedContNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnJobNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnJobNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("RetireNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RetireNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OtherNumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("RgtCapital"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RgtCapital = d;
			}
		}
		if (FCode.equalsIgnoreCase("TotalAssets"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalAssets = d;
			}
		}
		if (FCode.equalsIgnoreCase("NetProfitRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NetProfitRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("Satrap"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Satrap = FValue.trim();
			}
			else
				Satrap = null;
		}
		if (FCode.equalsIgnoreCase("ActuCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActuCtrl = FValue.trim();
			}
			else
				ActuCtrl = null;
		}
		if (FCode.equalsIgnoreCase("License"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				License = FValue.trim();
			}
			else
				License = null;
		}
		if (FCode.equalsIgnoreCase("OrganizationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrganizationCode = FValue.trim();
			}
			else
				OrganizationCode = null;
		}
		if (FCode.equalsIgnoreCase("TaxCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaxCode = FValue.trim();
			}
			else
				TaxCode = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan = FValue.trim();
			}
			else
				LinkMan = null;
		}
		if (FCode.equalsIgnoreCase("LinkIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkIDType = FValue.trim();
			}
			else
				LinkIDType = null;
		}
		if (FCode.equalsIgnoreCase("LinkID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkID = FValue.trim();
			}
			else
				LinkID = null;
		}
		if (FCode.equalsIgnoreCase("LinkIDExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LinkIDExpiryDate = fDate.getDate( FValue );
			}
			else
				LinkIDExpiryDate = null;
		}
		if (FCode.equalsIgnoreCase("LinkPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkPhone = FValue.trim();
			}
			else
				LinkPhone = null;
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
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValDateType = FValue.trim();
			}
			else
				ValDateType = null;
		}
		if (FCode.equalsIgnoreCase("ValDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValDate = fDate.getDate( FValue );
			}
			else
				ValDate = null;
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
		if (FCode.equalsIgnoreCase("InsuYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYears = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("ChnlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChnlType = FValue.trim();
			}
			else
				ChnlType = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("AppManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppManageCom = FValue.trim();
			}
			else
				AppManageCom = null;
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
		LCPrintAppntSchema other = (LCPrintAppntSchema)otherObject;
		return
			OfferListNo.equals(other.getOfferListNo())
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AddressNo.equals(other.getAddressNo())
			&& GrpName.equals(other.getGrpName())
			&& GrpNamePY.equals(other.getGrpNamePY())
			&& SearchKeyWord.equals(other.getSearchKeyWord())
			&& MainBusiness.equals(other.getMainBusiness())
			&& Corporation.equals(other.getCorporation())
			&& CorIDType.equals(other.getCorIDType())
			&& CorID.equals(other.getCorID())
			&& fDate.getString(CorIDExpiryDate).equals(other.getCorIDExpiryDate())
			&& BusinessCategory.equals(other.getBusinessCategory())
			&& BusiCategory.equals(other.getBusiCategory())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& SocialInsuCode.equals(other.getSocialInsuCode())
			&& GrpNature.equals(other.getGrpNature())
			&& GrpIDType.equals(other.getGrpIDType())
			&& GrpID.equals(other.getGrpID())
			&& fDate.getString(GrpIDExpiryDate).equals(other.getGrpIDExpiryDate())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& EMail.equals(other.getEMail())
			&& SumNumPeople == other.getSumNumPeople()
			&& MainContNumber == other.getMainContNumber()
			&& RelatedContNumber == other.getRelatedContNumber()
			&& OnJobNumber == other.getOnJobNumber()
			&& RetireNumber == other.getRetireNumber()
			&& OtherNumber == other.getOtherNumber()
			&& RgtCapital == other.getRgtCapital()
			&& TotalAssets == other.getTotalAssets()
			&& NetProfitRate == other.getNetProfitRate()
			&& Satrap.equals(other.getSatrap())
			&& ActuCtrl.equals(other.getActuCtrl())
			&& License.equals(other.getLicense())
			&& OrganizationCode.equals(other.getOrganizationCode())
			&& TaxCode.equals(other.getTaxCode())
			&& LinkMan.equals(other.getLinkMan())
			&& LinkIDType.equals(other.getLinkIDType())
			&& LinkID.equals(other.getLinkID())
			&& fDate.getString(LinkIDExpiryDate).equals(other.getLinkIDExpiryDate())
			&& LinkPhone.equals(other.getLinkPhone())
			&& State.equals(other.getState())
			&& ValDateType.equals(other.getValDateType())
			&& fDate.getString(ValDate).equals(other.getValDate())
			&& InsuPeriod == other.getInsuPeriod()
			&& InsuPeriodFlag.equals(other.getInsuPeriodFlag())
			&& InsuYears == other.getInsuYears()
			&& PayMode.equals(other.getPayMode())
			&& PremMode.equals(other.getPremMode())
			&& PayIntv == other.getPayIntv()
			&& ChnlType.equals(other.getChnlType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Remark.equals(other.getRemark())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& AppManageCom.equals(other.getAppManageCom())
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
		if( strFieldName.equals("OfferListNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 3;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 4;
		}
		if( strFieldName.equals("GrpName") ) {
			return 5;
		}
		if( strFieldName.equals("GrpNamePY") ) {
			return 6;
		}
		if( strFieldName.equals("SearchKeyWord") ) {
			return 7;
		}
		if( strFieldName.equals("MainBusiness") ) {
			return 8;
		}
		if( strFieldName.equals("Corporation") ) {
			return 9;
		}
		if( strFieldName.equals("CorIDType") ) {
			return 10;
		}
		if( strFieldName.equals("CorID") ) {
			return 11;
		}
		if( strFieldName.equals("CorIDExpiryDate") ) {
			return 12;
		}
		if( strFieldName.equals("BusinessCategory") ) {
			return 13;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return 14;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 15;
		}
		if( strFieldName.equals("SocialInsuCode") ) {
			return 16;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 17;
		}
		if( strFieldName.equals("GrpIDType") ) {
			return 18;
		}
		if( strFieldName.equals("GrpID") ) {
			return 19;
		}
		if( strFieldName.equals("GrpIDExpiryDate") ) {
			return 20;
		}
		if( strFieldName.equals("Phone") ) {
			return 21;
		}
		if( strFieldName.equals("Fax") ) {
			return 22;
		}
		if( strFieldName.equals("EMail") ) {
			return 23;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return 24;
		}
		if( strFieldName.equals("MainContNumber") ) {
			return 25;
		}
		if( strFieldName.equals("RelatedContNumber") ) {
			return 26;
		}
		if( strFieldName.equals("OnJobNumber") ) {
			return 27;
		}
		if( strFieldName.equals("RetireNumber") ) {
			return 28;
		}
		if( strFieldName.equals("OtherNumber") ) {
			return 29;
		}
		if( strFieldName.equals("RgtCapital") ) {
			return 30;
		}
		if( strFieldName.equals("TotalAssets") ) {
			return 31;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 32;
		}
		if( strFieldName.equals("Satrap") ) {
			return 33;
		}
		if( strFieldName.equals("ActuCtrl") ) {
			return 34;
		}
		if( strFieldName.equals("License") ) {
			return 35;
		}
		if( strFieldName.equals("OrganizationCode") ) {
			return 36;
		}
		if( strFieldName.equals("TaxCode") ) {
			return 37;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 38;
		}
		if( strFieldName.equals("LinkIDType") ) {
			return 39;
		}
		if( strFieldName.equals("LinkID") ) {
			return 40;
		}
		if( strFieldName.equals("LinkIDExpiryDate") ) {
			return 41;
		}
		if( strFieldName.equals("LinkPhone") ) {
			return 42;
		}
		if( strFieldName.equals("State") ) {
			return 43;
		}
		if( strFieldName.equals("ValDateType") ) {
			return 44;
		}
		if( strFieldName.equals("ValDate") ) {
			return 45;
		}
		if( strFieldName.equals("InsuPeriod") ) {
			return 46;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return 47;
		}
		if( strFieldName.equals("InsuYears") ) {
			return 48;
		}
		if( strFieldName.equals("PayMode") ) {
			return 49;
		}
		if( strFieldName.equals("PremMode") ) {
			return 50;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 51;
		}
		if( strFieldName.equals("ChnlType") ) {
			return 52;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 53;
		}
		if( strFieldName.equals("Remark") ) {
			return 54;
		}
		if( strFieldName.equals("Segment1") ) {
			return 55;
		}
		if( strFieldName.equals("Segment2") ) {
			return 56;
		}
		if( strFieldName.equals("Segment3") ) {
			return 57;
		}
		if( strFieldName.equals("AppManageCom") ) {
			return 58;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 59;
		}
		if( strFieldName.equals("ComCode") ) {
			return 60;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 61;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 62;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 63;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 64;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 65;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 66;
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
				strFieldName = "OfferListNo";
				break;
			case 1:
				strFieldName = "GrpPropNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "CustomerNo";
				break;
			case 4:
				strFieldName = "AddressNo";
				break;
			case 5:
				strFieldName = "GrpName";
				break;
			case 6:
				strFieldName = "GrpNamePY";
				break;
			case 7:
				strFieldName = "SearchKeyWord";
				break;
			case 8:
				strFieldName = "MainBusiness";
				break;
			case 9:
				strFieldName = "Corporation";
				break;
			case 10:
				strFieldName = "CorIDType";
				break;
			case 11:
				strFieldName = "CorID";
				break;
			case 12:
				strFieldName = "CorIDExpiryDate";
				break;
			case 13:
				strFieldName = "BusinessCategory";
				break;
			case 14:
				strFieldName = "BusiCategory";
				break;
			case 15:
				strFieldName = "FoundDate";
				break;
			case 16:
				strFieldName = "SocialInsuCode";
				break;
			case 17:
				strFieldName = "GrpNature";
				break;
			case 18:
				strFieldName = "GrpIDType";
				break;
			case 19:
				strFieldName = "GrpID";
				break;
			case 20:
				strFieldName = "GrpIDExpiryDate";
				break;
			case 21:
				strFieldName = "Phone";
				break;
			case 22:
				strFieldName = "Fax";
				break;
			case 23:
				strFieldName = "EMail";
				break;
			case 24:
				strFieldName = "SumNumPeople";
				break;
			case 25:
				strFieldName = "MainContNumber";
				break;
			case 26:
				strFieldName = "RelatedContNumber";
				break;
			case 27:
				strFieldName = "OnJobNumber";
				break;
			case 28:
				strFieldName = "RetireNumber";
				break;
			case 29:
				strFieldName = "OtherNumber";
				break;
			case 30:
				strFieldName = "RgtCapital";
				break;
			case 31:
				strFieldName = "TotalAssets";
				break;
			case 32:
				strFieldName = "NetProfitRate";
				break;
			case 33:
				strFieldName = "Satrap";
				break;
			case 34:
				strFieldName = "ActuCtrl";
				break;
			case 35:
				strFieldName = "License";
				break;
			case 36:
				strFieldName = "OrganizationCode";
				break;
			case 37:
				strFieldName = "TaxCode";
				break;
			case 38:
				strFieldName = "LinkMan";
				break;
			case 39:
				strFieldName = "LinkIDType";
				break;
			case 40:
				strFieldName = "LinkID";
				break;
			case 41:
				strFieldName = "LinkIDExpiryDate";
				break;
			case 42:
				strFieldName = "LinkPhone";
				break;
			case 43:
				strFieldName = "State";
				break;
			case 44:
				strFieldName = "ValDateType";
				break;
			case 45:
				strFieldName = "ValDate";
				break;
			case 46:
				strFieldName = "InsuPeriod";
				break;
			case 47:
				strFieldName = "InsuPeriodFlag";
				break;
			case 48:
				strFieldName = "InsuYears";
				break;
			case 49:
				strFieldName = "PayMode";
				break;
			case 50:
				strFieldName = "PremMode";
				break;
			case 51:
				strFieldName = "PayIntv";
				break;
			case 52:
				strFieldName = "ChnlType";
				break;
			case 53:
				strFieldName = "SaleChnl";
				break;
			case 54:
				strFieldName = "Remark";
				break;
			case 55:
				strFieldName = "Segment1";
				break;
			case 56:
				strFieldName = "Segment2";
				break;
			case 57:
				strFieldName = "Segment3";
				break;
			case 58:
				strFieldName = "AppManageCom";
				break;
			case 59:
				strFieldName = "ManageCom";
				break;
			case 60:
				strFieldName = "ComCode";
				break;
			case 61:
				strFieldName = "MakeOperator";
				break;
			case 62:
				strFieldName = "MakeDate";
				break;
			case 63:
				strFieldName = "MakeTime";
				break;
			case 64:
				strFieldName = "ModifyOperator";
				break;
			case 65:
				strFieldName = "ModifyDate";
				break;
			case 66:
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
		if( strFieldName.equals("OfferListNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNamePY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SearchKeyWord") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainBusiness") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Corporation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CorIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CorID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CorIDExpiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BusinessCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SocialInsuCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpIDExpiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MainContNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelatedContNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnJobNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RetireNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherNumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RgtCapital") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TotalAssets") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Satrap") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActuCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrganizationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaxCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkIDExpiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LinkPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValDateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ChnlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("AppManageCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_INT;
				break;
			case 29:
				nFieldType = Schema.TYPE_INT;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
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
			case 41:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 46:
				nFieldType = Schema.TYPE_INT;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_INT;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_INT;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
