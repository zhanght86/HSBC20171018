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
import com.sinosoft.lis.db.LDGrpTraceDB;

/*
 * <p>ClassName: LDGrpTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 客户管理
 */
public class LDGrpTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDGrpTraceSchema.class);
	// @Field
	/** 轨迹号 */
	private String TraceID;
	/** 客户号码 */
	private String CustomerNo;
	/** 服务密码 */
	private String ServicePwd;
	/** 客户名称 */
	private String CustomerName;
	/** 单位拼音名称 */
	private String GrpNamePY;
	/** 检索关键字 */
	private String SearchKeyWord;
	/** 行业大类 */
	private String BusinessCategory;
	/** 行业分类 */
	private String BusiCategory;
	/** 单位性质 */
	private String GrpNature;
	/** 员工总人数 */
	private int SumNumPeople;
	/** 经营范围 */
	private String MainBusiness;
	/** 法人 */
	private String Corporation;
	/** 法人证件类型 */
	private String CorIDType;
	/** 法人证件号码 */
	private String CorlID;
	/** 联系人证件有效止期 */
	private Date IDExpiryDate;
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
	/** 社保保险登记证号 */
	private String SocialInsuCode;
	/** 组织机构代码 */
	private String OrganizationCode;
	/** 税务登记证 */
	private String TaxCode;
	/** 单位电话 */
	private String Phone;
	/** 单位传真 */
	private String Fax;
	/** 单位emaill */
	private String EMail;
	/** 成立日期 */
	private Date FoundDate;
	/** 状态 */
	private String State;
	/** 黑名单标识 */
	private String BlacklistFlag;
	/** Vip值 */
	private String VIPValue;
	/** 级别代码 */
	private String LevelCode;
	/** 上级客户号码 */
	private String UpCustoemrNo;
	/** 备注 */
	private String Remark;
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

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDGrpTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TraceID";
		pk[1] = "CustomerNo";

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
		LDGrpTraceSchema cloned = (LDGrpTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTraceID()
	{
		return TraceID;
	}
	public void setTraceID(String aTraceID)
	{
		if(aTraceID!=null && aTraceID.length()>10)
			throw new IllegalArgumentException("轨迹号TraceID值"+aTraceID+"的长度"+aTraceID.length()+"大于最大值10");
		TraceID = aTraceID;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getServicePwd()
	{
		return ServicePwd;
	}
	public void setServicePwd(String aServicePwd)
	{
		if(aServicePwd!=null && aServicePwd.length()>30)
			throw new IllegalArgumentException("服务密码ServicePwd值"+aServicePwd+"的长度"+aServicePwd.length()+"大于最大值30");
		ServicePwd = aServicePwd;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>200)
			throw new IllegalArgumentException("客户名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值200");
		CustomerName = aCustomerName;
	}
	public String getGrpNamePY()
	{
		return GrpNamePY;
	}
	public void setGrpNamePY(String aGrpNamePY)
	{
		if(aGrpNamePY!=null && aGrpNamePY.length()>200)
			throw new IllegalArgumentException("单位拼音名称GrpNamePY值"+aGrpNamePY+"的长度"+aGrpNamePY.length()+"大于最大值200");
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
	public String getCorlID()
	{
		return CorlID;
	}
	public void setCorlID(String aCorlID)
	{
		if(aCorlID!=null && aCorlID.length()>30)
			throw new IllegalArgumentException("法人证件号码CorlID值"+aCorlID+"的长度"+aCorlID.length()+"大于最大值30");
		CorlID = aCorlID;
	}
	public String getIDExpiryDate()
	{
		if( IDExpiryDate != null )
			return fDate.getString(IDExpiryDate);
		else
			return null;
	}
	public void setIDExpiryDate(Date aIDExpiryDate)
	{
		IDExpiryDate = aIDExpiryDate;
	}
	public void setIDExpiryDate(String aIDExpiryDate)
	{
		if (aIDExpiryDate != null && !aIDExpiryDate.equals("") )
		{
			IDExpiryDate = fDate.getDate( aIDExpiryDate );
		}
		else
			IDExpiryDate = null;
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

	/**
	* 00-正常<p>
	* 90-被合并
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
	/**
	* 0-正常，1-黑名单
	*/
	public String getBlacklistFlag()
	{
		return BlacklistFlag;
	}
	public void setBlacklistFlag(String aBlacklistFlag)
	{
		if(aBlacklistFlag!=null && aBlacklistFlag.length()>1)
			throw new IllegalArgumentException("黑名单标识BlacklistFlag值"+aBlacklistFlag+"的长度"+aBlacklistFlag.length()+"大于最大值1");
		BlacklistFlag = aBlacklistFlag;
	}
	/**
	* 目前使用0-非VIP客户，1-VIP客户；预设计支持VIP值区别VIP等级
	*/
	public String getVIPValue()
	{
		return VIPValue;
	}
	public void setVIPValue(String aVIPValue)
	{
		if(aVIPValue!=null && aVIPValue.length()>10)
			throw new IllegalArgumentException("Vip值VIPValue值"+aVIPValue+"的长度"+aVIPValue.length()+"大于最大值10");
		VIPValue = aVIPValue;
	}
	/**
	* 0-普通，1-集团，2-子公司，3-分公司
	*/
	public String getLevelCode()
	{
		return LevelCode;
	}
	public void setLevelCode(String aLevelCode)
	{
		if(aLevelCode!=null && aLevelCode.length()>10)
			throw new IllegalArgumentException("级别代码LevelCode值"+aLevelCode+"的长度"+aLevelCode.length()+"大于最大值10");
		LevelCode = aLevelCode;
	}
	public String getUpCustoemrNo()
	{
		return UpCustoemrNo;
	}
	public void setUpCustoemrNo(String aUpCustoemrNo)
	{
		if(aUpCustoemrNo!=null && aUpCustoemrNo.length()>20)
			throw new IllegalArgumentException("上级客户号码UpCustoemrNo值"+aUpCustoemrNo+"的长度"+aUpCustoemrNo.length()+"大于最大值20");
		UpCustoemrNo = aUpCustoemrNo;
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
	* 使用另外一个 LDGrpTraceSchema 对象给 Schema 赋值
	* @param: aLDGrpTraceSchema LDGrpTraceSchema
	**/
	public void setSchema(LDGrpTraceSchema aLDGrpTraceSchema)
	{
		this.TraceID = aLDGrpTraceSchema.getTraceID();
		this.CustomerNo = aLDGrpTraceSchema.getCustomerNo();
		this.ServicePwd = aLDGrpTraceSchema.getServicePwd();
		this.CustomerName = aLDGrpTraceSchema.getCustomerName();
		this.GrpNamePY = aLDGrpTraceSchema.getGrpNamePY();
		this.SearchKeyWord = aLDGrpTraceSchema.getSearchKeyWord();
		this.BusinessCategory = aLDGrpTraceSchema.getBusinessCategory();
		this.BusiCategory = aLDGrpTraceSchema.getBusiCategory();
		this.GrpNature = aLDGrpTraceSchema.getGrpNature();
		this.SumNumPeople = aLDGrpTraceSchema.getSumNumPeople();
		this.MainBusiness = aLDGrpTraceSchema.getMainBusiness();
		this.Corporation = aLDGrpTraceSchema.getCorporation();
		this.CorIDType = aLDGrpTraceSchema.getCorIDType();
		this.CorlID = aLDGrpTraceSchema.getCorlID();
		this.IDExpiryDate = fDate.getDate( aLDGrpTraceSchema.getIDExpiryDate());
		this.OnJobNumber = aLDGrpTraceSchema.getOnJobNumber();
		this.RetireNumber = aLDGrpTraceSchema.getRetireNumber();
		this.OtherNumber = aLDGrpTraceSchema.getOtherNumber();
		this.RgtCapital = aLDGrpTraceSchema.getRgtCapital();
		this.TotalAssets = aLDGrpTraceSchema.getTotalAssets();
		this.NetProfitRate = aLDGrpTraceSchema.getNetProfitRate();
		this.Satrap = aLDGrpTraceSchema.getSatrap();
		this.ActuCtrl = aLDGrpTraceSchema.getActuCtrl();
		this.License = aLDGrpTraceSchema.getLicense();
		this.SocialInsuCode = aLDGrpTraceSchema.getSocialInsuCode();
		this.OrganizationCode = aLDGrpTraceSchema.getOrganizationCode();
		this.TaxCode = aLDGrpTraceSchema.getTaxCode();
		this.Phone = aLDGrpTraceSchema.getPhone();
		this.Fax = aLDGrpTraceSchema.getFax();
		this.EMail = aLDGrpTraceSchema.getEMail();
		this.FoundDate = fDate.getDate( aLDGrpTraceSchema.getFoundDate());
		this.State = aLDGrpTraceSchema.getState();
		this.BlacklistFlag = aLDGrpTraceSchema.getBlacklistFlag();
		this.VIPValue = aLDGrpTraceSchema.getVIPValue();
		this.LevelCode = aLDGrpTraceSchema.getLevelCode();
		this.UpCustoemrNo = aLDGrpTraceSchema.getUpCustoemrNo();
		this.Remark = aLDGrpTraceSchema.getRemark();
		this.ManageCom = aLDGrpTraceSchema.getManageCom();
		this.ComCode = aLDGrpTraceSchema.getComCode();
		this.MakeOperator = aLDGrpTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDGrpTraceSchema.getMakeDate());
		this.MakeTime = aLDGrpTraceSchema.getMakeTime();
		this.ModifyOperator = aLDGrpTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDGrpTraceSchema.getModifyDate());
		this.ModifyTime = aLDGrpTraceSchema.getModifyTime();
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
			if( rs.getString("TraceID") == null )
				this.TraceID = null;
			else
				this.TraceID = rs.getString("TraceID").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("ServicePwd") == null )
				this.ServicePwd = null;
			else
				this.ServicePwd = rs.getString("ServicePwd").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("GrpNamePY") == null )
				this.GrpNamePY = null;
			else
				this.GrpNamePY = rs.getString("GrpNamePY").trim();

			if( rs.getString("SearchKeyWord") == null )
				this.SearchKeyWord = null;
			else
				this.SearchKeyWord = rs.getString("SearchKeyWord").trim();

			if( rs.getString("BusinessCategory") == null )
				this.BusinessCategory = null;
			else
				this.BusinessCategory = rs.getString("BusinessCategory").trim();

			if( rs.getString("BusiCategory") == null )
				this.BusiCategory = null;
			else
				this.BusiCategory = rs.getString("BusiCategory").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			this.SumNumPeople = rs.getInt("SumNumPeople");
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

			if( rs.getString("CorlID") == null )
				this.CorlID = null;
			else
				this.CorlID = rs.getString("CorlID").trim();

			this.IDExpiryDate = rs.getDate("IDExpiryDate");
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

			if( rs.getString("SocialInsuCode") == null )
				this.SocialInsuCode = null;
			else
				this.SocialInsuCode = rs.getString("SocialInsuCode").trim();

			if( rs.getString("OrganizationCode") == null )
				this.OrganizationCode = null;
			else
				this.OrganizationCode = rs.getString("OrganizationCode").trim();

			if( rs.getString("TaxCode") == null )
				this.TaxCode = null;
			else
				this.TaxCode = rs.getString("TaxCode").trim();

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

			this.FoundDate = rs.getDate("FoundDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

			if( rs.getString("VIPValue") == null )
				this.VIPValue = null;
			else
				this.VIPValue = rs.getString("VIPValue").trim();

			if( rs.getString("LevelCode") == null )
				this.LevelCode = null;
			else
				this.LevelCode = rs.getString("LevelCode").trim();

			if( rs.getString("UpCustoemrNo") == null )
				this.UpCustoemrNo = null;
			else
				this.UpCustoemrNo = rs.getString("UpCustoemrNo").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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
			logger.debug("数据库中的LDGrpTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDGrpTraceSchema getSchema()
	{
		LDGrpTraceSchema aLDGrpTraceSchema = new LDGrpTraceSchema();
		aLDGrpTraceSchema.setSchema(this);
		return aLDGrpTraceSchema;
	}

	public LDGrpTraceDB getDB()
	{
		LDGrpTraceDB aDBOper = new LDGrpTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDGrpTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TraceID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServicePwd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SearchKeyWord)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBusiness)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Corporation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorlID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnJobNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RetireNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherNumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RgtCapital));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalAssets));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetProfitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Satrap)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActuCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganizationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaxCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LevelCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpCustoemrNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDGrpTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TraceID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ServicePwd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SearchKeyWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BusinessCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BusiCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SumNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			MainBusiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CorIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CorlID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			IDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			OnJobNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			RetireNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			OtherNumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			RgtCapital = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			TotalAssets = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ActuCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			SocialInsuCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			OrganizationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			TaxCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			VIPValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			LevelCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			UpCustoemrNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpTraceSchema";
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
		if (FCode.equalsIgnoreCase("TraceID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TraceID));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("ServicePwd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServicePwd));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("GrpNamePY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNamePY));
		}
		if (FCode.equalsIgnoreCase("SearchKeyWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SearchKeyWord));
		}
		if (FCode.equalsIgnoreCase("BusinessCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessCategory));
		}
		if (FCode.equalsIgnoreCase("BusiCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiCategory));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumNumPeople));
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
		if (FCode.equalsIgnoreCase("CorlID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CorlID));
		}
		if (FCode.equalsIgnoreCase("IDExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDExpiryDate()));
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
		if (FCode.equalsIgnoreCase("SocialInsuCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuCode));
		}
		if (FCode.equalsIgnoreCase("OrganizationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganizationCode));
		}
		if (FCode.equalsIgnoreCase("TaxCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaxCode));
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
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistFlag));
		}
		if (FCode.equalsIgnoreCase("VIPValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPValue));
		}
		if (FCode.equalsIgnoreCase("LevelCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LevelCode));
		}
		if (FCode.equalsIgnoreCase("UpCustoemrNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpCustoemrNo));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(TraceID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ServicePwd);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpNamePY);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SearchKeyWord);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BusinessCategory);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BusiCategory);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 9:
				strFieldValue = String.valueOf(SumNumPeople);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MainBusiness);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CorIDType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CorlID);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDExpiryDate()));
				break;
			case 15:
				strFieldValue = String.valueOf(OnJobNumber);
				break;
			case 16:
				strFieldValue = String.valueOf(RetireNumber);
				break;
			case 17:
				strFieldValue = String.valueOf(OtherNumber);
				break;
			case 18:
				strFieldValue = String.valueOf(RgtCapital);
				break;
			case 19:
				strFieldValue = String.valueOf(TotalAssets);
				break;
			case 20:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ActuCtrl);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(OrganizationCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(TaxCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(VIPValue);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(LevelCode);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(UpCustoemrNo);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 44:
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

		if (FCode.equalsIgnoreCase("TraceID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TraceID = FValue.trim();
			}
			else
				TraceID = null;
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
		if (FCode.equalsIgnoreCase("ServicePwd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServicePwd = FValue.trim();
			}
			else
				ServicePwd = null;
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
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
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNature = FValue.trim();
			}
			else
				GrpNature = null;
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
		if (FCode.equalsIgnoreCase("CorlID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CorlID = FValue.trim();
			}
			else
				CorlID = null;
		}
		if (FCode.equalsIgnoreCase("IDExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDExpiryDate = fDate.getDate( FValue );
			}
			else
				IDExpiryDate = null;
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
		if (FCode.equalsIgnoreCase("SocialInsuCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuCode = FValue.trim();
			}
			else
				SocialInsuCode = null;
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
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FoundDate = fDate.getDate( FValue );
			}
			else
				FoundDate = null;
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
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlacklistFlag = FValue.trim();
			}
			else
				BlacklistFlag = null;
		}
		if (FCode.equalsIgnoreCase("VIPValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VIPValue = FValue.trim();
			}
			else
				VIPValue = null;
		}
		if (FCode.equalsIgnoreCase("LevelCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LevelCode = FValue.trim();
			}
			else
				LevelCode = null;
		}
		if (FCode.equalsIgnoreCase("UpCustoemrNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpCustoemrNo = FValue.trim();
			}
			else
				UpCustoemrNo = null;
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
		LDGrpTraceSchema other = (LDGrpTraceSchema)otherObject;
		return
			TraceID.equals(other.getTraceID())
			&& CustomerNo.equals(other.getCustomerNo())
			&& ServicePwd.equals(other.getServicePwd())
			&& CustomerName.equals(other.getCustomerName())
			&& GrpNamePY.equals(other.getGrpNamePY())
			&& SearchKeyWord.equals(other.getSearchKeyWord())
			&& BusinessCategory.equals(other.getBusinessCategory())
			&& BusiCategory.equals(other.getBusiCategory())
			&& GrpNature.equals(other.getGrpNature())
			&& SumNumPeople == other.getSumNumPeople()
			&& MainBusiness.equals(other.getMainBusiness())
			&& Corporation.equals(other.getCorporation())
			&& CorIDType.equals(other.getCorIDType())
			&& CorlID.equals(other.getCorlID())
			&& fDate.getString(IDExpiryDate).equals(other.getIDExpiryDate())
			&& OnJobNumber == other.getOnJobNumber()
			&& RetireNumber == other.getRetireNumber()
			&& OtherNumber == other.getOtherNumber()
			&& RgtCapital == other.getRgtCapital()
			&& TotalAssets == other.getTotalAssets()
			&& NetProfitRate == other.getNetProfitRate()
			&& Satrap.equals(other.getSatrap())
			&& ActuCtrl.equals(other.getActuCtrl())
			&& License.equals(other.getLicense())
			&& SocialInsuCode.equals(other.getSocialInsuCode())
			&& OrganizationCode.equals(other.getOrganizationCode())
			&& TaxCode.equals(other.getTaxCode())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& EMail.equals(other.getEMail())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& State.equals(other.getState())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
			&& VIPValue.equals(other.getVIPValue())
			&& LevelCode.equals(other.getLevelCode())
			&& UpCustoemrNo.equals(other.getUpCustoemrNo())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("TraceID") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("ServicePwd") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 3;
		}
		if( strFieldName.equals("GrpNamePY") ) {
			return 4;
		}
		if( strFieldName.equals("SearchKeyWord") ) {
			return 5;
		}
		if( strFieldName.equals("BusinessCategory") ) {
			return 6;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return 7;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 8;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return 9;
		}
		if( strFieldName.equals("MainBusiness") ) {
			return 10;
		}
		if( strFieldName.equals("Corporation") ) {
			return 11;
		}
		if( strFieldName.equals("CorIDType") ) {
			return 12;
		}
		if( strFieldName.equals("CorlID") ) {
			return 13;
		}
		if( strFieldName.equals("IDExpiryDate") ) {
			return 14;
		}
		if( strFieldName.equals("OnJobNumber") ) {
			return 15;
		}
		if( strFieldName.equals("RetireNumber") ) {
			return 16;
		}
		if( strFieldName.equals("OtherNumber") ) {
			return 17;
		}
		if( strFieldName.equals("RgtCapital") ) {
			return 18;
		}
		if( strFieldName.equals("TotalAssets") ) {
			return 19;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 20;
		}
		if( strFieldName.equals("Satrap") ) {
			return 21;
		}
		if( strFieldName.equals("ActuCtrl") ) {
			return 22;
		}
		if( strFieldName.equals("License") ) {
			return 23;
		}
		if( strFieldName.equals("SocialInsuCode") ) {
			return 24;
		}
		if( strFieldName.equals("OrganizationCode") ) {
			return 25;
		}
		if( strFieldName.equals("TaxCode") ) {
			return 26;
		}
		if( strFieldName.equals("Phone") ) {
			return 27;
		}
		if( strFieldName.equals("Fax") ) {
			return 28;
		}
		if( strFieldName.equals("EMail") ) {
			return 29;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 30;
		}
		if( strFieldName.equals("State") ) {
			return 31;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 32;
		}
		if( strFieldName.equals("VIPValue") ) {
			return 33;
		}
		if( strFieldName.equals("LevelCode") ) {
			return 34;
		}
		if( strFieldName.equals("UpCustoemrNo") ) {
			return 35;
		}
		if( strFieldName.equals("Remark") ) {
			return 36;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 37;
		}
		if( strFieldName.equals("ComCode") ) {
			return 38;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 39;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 40;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 41;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 42;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 44;
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
				strFieldName = "TraceID";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "ServicePwd";
				break;
			case 3:
				strFieldName = "CustomerName";
				break;
			case 4:
				strFieldName = "GrpNamePY";
				break;
			case 5:
				strFieldName = "SearchKeyWord";
				break;
			case 6:
				strFieldName = "BusinessCategory";
				break;
			case 7:
				strFieldName = "BusiCategory";
				break;
			case 8:
				strFieldName = "GrpNature";
				break;
			case 9:
				strFieldName = "SumNumPeople";
				break;
			case 10:
				strFieldName = "MainBusiness";
				break;
			case 11:
				strFieldName = "Corporation";
				break;
			case 12:
				strFieldName = "CorIDType";
				break;
			case 13:
				strFieldName = "CorlID";
				break;
			case 14:
				strFieldName = "IDExpiryDate";
				break;
			case 15:
				strFieldName = "OnJobNumber";
				break;
			case 16:
				strFieldName = "RetireNumber";
				break;
			case 17:
				strFieldName = "OtherNumber";
				break;
			case 18:
				strFieldName = "RgtCapital";
				break;
			case 19:
				strFieldName = "TotalAssets";
				break;
			case 20:
				strFieldName = "NetProfitRate";
				break;
			case 21:
				strFieldName = "Satrap";
				break;
			case 22:
				strFieldName = "ActuCtrl";
				break;
			case 23:
				strFieldName = "License";
				break;
			case 24:
				strFieldName = "SocialInsuCode";
				break;
			case 25:
				strFieldName = "OrganizationCode";
				break;
			case 26:
				strFieldName = "TaxCode";
				break;
			case 27:
				strFieldName = "Phone";
				break;
			case 28:
				strFieldName = "Fax";
				break;
			case 29:
				strFieldName = "EMail";
				break;
			case 30:
				strFieldName = "FoundDate";
				break;
			case 31:
				strFieldName = "State";
				break;
			case 32:
				strFieldName = "BlacklistFlag";
				break;
			case 33:
				strFieldName = "VIPValue";
				break;
			case 34:
				strFieldName = "LevelCode";
				break;
			case 35:
				strFieldName = "UpCustoemrNo";
				break;
			case 36:
				strFieldName = "Remark";
				break;
			case 37:
				strFieldName = "ManageCom";
				break;
			case 38:
				strFieldName = "ComCode";
				break;
			case 39:
				strFieldName = "MakeOperator";
				break;
			case 40:
				strFieldName = "MakeDate";
				break;
			case 41:
				strFieldName = "MakeTime";
				break;
			case 42:
				strFieldName = "ModifyOperator";
				break;
			case 43:
				strFieldName = "ModifyDate";
				break;
			case 44:
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
		if( strFieldName.equals("TraceID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServicePwd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNamePY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SearchKeyWord") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("CorlID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDExpiryDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("SocialInsuCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrganizationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaxCode") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("FoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VIPValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LevelCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpCustoemrNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
