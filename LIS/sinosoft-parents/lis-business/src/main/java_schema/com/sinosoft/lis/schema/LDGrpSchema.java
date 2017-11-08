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
import com.sinosoft.lis.db.LDGrpDB;

/*
 * <p>ClassName: LDGrpSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LDGrpSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDGrpSchema.class);
	// @Field
	/** 客户号码 */
	private String CustomerNo;
	/** 密码 */
	private String Password;
	/** 单位名称 */
	private String GrpName;
	/** 行业分类 */
	private String BusinessType;
	/** 单位性质 */
	private String GrpNature;
	/** 总人数 */
	private int Peoples;
	/** 注册资本 */
	private double RgtMoney;
	/** 资产总额 */
	private double Asset;
	/** 净资产收益率 */
	private double NetProfitRate;
	/** 主营业务 */
	private String MainBussiness;
	/** 法人 */
	private String Corporation;
	/** 机构分布区域 */
	private String ComAera;
	/** 单位传真 */
	private String Fax;
	/** 单位电话 */
	private String Phone;
	/** 付款方式 */
	private String GetFlag;
	/** 负责人 */
	private String Satrap;
	/** 公司e_mail */
	private String EMail;
	/** 成立日期 */
	private Date FoundDate;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 客户组号码 */
	private String GrpGroupNo;
	/** 黑名单标记 */
	private String BlacklistFlag;
	/** 状态 */
	private String State;
	/** 备注 */
	private String Remark;
	/** Vip值 */
	private String VIPValue;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 子公司标志 */
	private String SubCompanyFlag;
	/** 上级客户号码 */
	private String SupCustoemrNo;
	/** 级别代码 */
	private String LevelCode;
	/** 在职人数 */
	private int OnWorkPeoples;
	/** 退休人数 */
	private int OffWorkPeoples;
	/** 其它人员人数 */
	private int OtherPeoples;
	/** 行业大类 */
	private String BusinessBigType;
	/** 单位社保登记号 */
	private String SocialInsuNo;
	/** 黑名单原因 */
	private String BlackListReason;
	/** 单位拼音名称 */
	private String GrpNamePY;
	/** 检索关键字 */
	private String SearchKeyWord;
	/** 法人证件类型 */
	private String CorIDType;
	/** 法人证件号码 */
	private String CorID;
	/** 联系人证件有效止期 */
	private Date CorIDExpiryDate;
	/** 实际控制人 */
	private String ActuCtrl;
	/** 营业执照号 */
	private String License;
	/** 组织机构代码 */
	private String OrganizationCode;
	/** 税务登记证 */
	private String TaxCode;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 51;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDGrpSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CustomerNo";

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
		LDGrpSchema cloned = (LDGrpSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		if(aPassword!=null && aPassword.length()>16)
			throw new IllegalArgumentException("密码Password值"+aPassword+"的长度"+aPassword.length()+"大于最大值16");
		Password = aPassword;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>60)
			throw new IllegalArgumentException("单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值60");
		GrpName = aGrpName;
	}
	public String getBusinessType()
	{
		return BusinessType;
	}
	public void setBusinessType(String aBusinessType)
	{
		if(aBusinessType!=null && aBusinessType.length()>20)
			throw new IllegalArgumentException("行业分类BusinessType值"+aBusinessType+"的长度"+aBusinessType.length()+"大于最大值20");
		BusinessType = aBusinessType;
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
	public int getPeoples()
	{
		return Peoples;
	}
	public void setPeoples(int aPeoples)
	{
		Peoples = aPeoples;
	}
	public void setPeoples(String aPeoples)
	{
		if (aPeoples != null && !aPeoples.equals(""))
		{
			Integer tInteger = new Integer(aPeoples);
			int i = tInteger.intValue();
			Peoples = i;
		}
	}

	public double getRgtMoney()
	{
		return RgtMoney;
	}
	public void setRgtMoney(double aRgtMoney)
	{
		RgtMoney = aRgtMoney;
	}
	public void setRgtMoney(String aRgtMoney)
	{
		if (aRgtMoney != null && !aRgtMoney.equals(""))
		{
			Double tDouble = new Double(aRgtMoney);
			double d = tDouble.doubleValue();
			RgtMoney = d;
		}
	}

	public double getAsset()
	{
		return Asset;
	}
	public void setAsset(double aAsset)
	{
		Asset = aAsset;
	}
	public void setAsset(String aAsset)
	{
		if (aAsset != null && !aAsset.equals(""))
		{
			Double tDouble = new Double(aAsset);
			double d = tDouble.doubleValue();
			Asset = d;
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

	public String getMainBussiness()
	{
		return MainBussiness;
	}
	public void setMainBussiness(String aMainBussiness)
	{
		if(aMainBussiness!=null && aMainBussiness.length()>60)
			throw new IllegalArgumentException("主营业务MainBussiness值"+aMainBussiness+"的长度"+aMainBussiness.length()+"大于最大值60");
		MainBussiness = aMainBussiness;
	}
	public String getCorporation()
	{
		return Corporation;
	}
	public void setCorporation(String aCorporation)
	{
		if(aCorporation!=null && aCorporation.length()>20)
			throw new IllegalArgumentException("法人Corporation值"+aCorporation+"的长度"+aCorporation.length()+"大于最大值20");
		Corporation = aCorporation;
	}
	public String getComAera()
	{
		return ComAera;
	}
	public void setComAera(String aComAera)
	{
		if(aComAera!=null && aComAera.length()>30)
			throw new IllegalArgumentException("机构分布区域ComAera值"+aComAera+"的长度"+aComAera.length()+"大于最大值30");
		ComAera = aComAera;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>18)
			throw new IllegalArgumentException("单位传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值18");
		Fax = aFax;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>18)
			throw new IllegalArgumentException("单位电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值18");
		Phone = aPhone;
	}
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		if(aGetFlag!=null && aGetFlag.length()>1)
			throw new IllegalArgumentException("付款方式GetFlag值"+aGetFlag+"的长度"+aGetFlag.length()+"大于最大值1");
		GetFlag = aGetFlag;
	}
	public String getSatrap()
	{
		return Satrap;
	}
	public void setSatrap(String aSatrap)
	{
		if(aSatrap!=null && aSatrap.length()>120)
			throw new IllegalArgumentException("负责人Satrap值"+aSatrap+"的长度"+aSatrap.length()+"大于最大值120");
		Satrap = aSatrap;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>20)
			throw new IllegalArgumentException("公司e_mailEMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值20");
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

	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>10)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值10");
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		if(aBankAccNo!=null && aBankAccNo.length()>40)
			throw new IllegalArgumentException("银行帐号BankAccNo值"+aBankAccNo+"的长度"+aBankAccNo.length()+"大于最大值40");
		BankAccNo = aBankAccNo;
	}
	/**
	* 将团体的客户编组
	*/
	public String getGrpGroupNo()
	{
		return GrpGroupNo;
	}
	public void setGrpGroupNo(String aGrpGroupNo)
	{
		if(aGrpGroupNo!=null && aGrpGroupNo.length()>10)
			throw new IllegalArgumentException("客户组号码GrpGroupNo值"+aGrpGroupNo+"的长度"+aGrpGroupNo.length()+"大于最大值10");
		GrpGroupNo = aGrpGroupNo;
	}
	/**
	* 0-正常,1-黑名单
	*/
	public String getBlacklistFlag()
	{
		return BlacklistFlag;
	}
	public void setBlacklistFlag(String aBlacklistFlag)
	{
		if(aBlacklistFlag!=null && aBlacklistFlag.length()>1)
			throw new IllegalArgumentException("黑名单标记BlacklistFlag值"+aBlacklistFlag+"的长度"+aBlacklistFlag.length()+"大于最大值1");
		BlacklistFlag = aBlacklistFlag;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>10)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值10");
		State = aState;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>255)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值255");
		Remark = aRemark;
	}
	/**
	* 目前只通过<p>
	* <p>
	* 0标识非VIP客户<p>
	* 1标识VIP客户<p>
	* <p>
	* 预设计支持VIP值区别VIP等级
	*/
	public String getVIPValue()
	{
		return VIPValue;
	}
	public void setVIPValue(String aVIPValue)
	{
		if(aVIPValue!=null && aVIPValue.length()>1)
			throw new IllegalArgumentException("Vip值VIPValue值"+aVIPValue+"的长度"+aVIPValue.length()+"大于最大值1");
		VIPValue = aVIPValue;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员代码Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
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
	public String getSubCompanyFlag()
	{
		return SubCompanyFlag;
	}
	public void setSubCompanyFlag(String aSubCompanyFlag)
	{
		if(aSubCompanyFlag!=null && aSubCompanyFlag.length()>1)
			throw new IllegalArgumentException("子公司标志SubCompanyFlag值"+aSubCompanyFlag+"的长度"+aSubCompanyFlag.length()+"大于最大值1");
		SubCompanyFlag = aSubCompanyFlag;
	}
	public String getSupCustoemrNo()
	{
		return SupCustoemrNo;
	}
	public void setSupCustoemrNo(String aSupCustoemrNo)
	{
		if(aSupCustoemrNo!=null && aSupCustoemrNo.length()>20)
			throw new IllegalArgumentException("上级客户号码SupCustoemrNo值"+aSupCustoemrNo+"的长度"+aSupCustoemrNo.length()+"大于最大值20");
		SupCustoemrNo = aSupCustoemrNo;
	}
	/**
	* 1.0	**集团(本部)<p>
	* 1.1.0	A子公司(本部)<p>
	* 1.1.1	A子公司北京分公司<p>
	* 1.1.2	A子公司上海分公司<p>
	* ……	……<p>
	* 1.2.0	B子公司健康险(本部)<p>
	* 1.2.1	B子公司北京分公司
	*/
	public String getLevelCode()
	{
		return LevelCode;
	}
	public void setLevelCode(String aLevelCode)
	{
		if(aLevelCode!=null && aLevelCode.length()>20)
			throw new IllegalArgumentException("级别代码LevelCode值"+aLevelCode+"的长度"+aLevelCode.length()+"大于最大值20");
		LevelCode = aLevelCode;
	}
	public int getOnWorkPeoples()
	{
		return OnWorkPeoples;
	}
	public void setOnWorkPeoples(int aOnWorkPeoples)
	{
		OnWorkPeoples = aOnWorkPeoples;
	}
	public void setOnWorkPeoples(String aOnWorkPeoples)
	{
		if (aOnWorkPeoples != null && !aOnWorkPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOnWorkPeoples);
			int i = tInteger.intValue();
			OnWorkPeoples = i;
		}
	}

	public int getOffWorkPeoples()
	{
		return OffWorkPeoples;
	}
	public void setOffWorkPeoples(int aOffWorkPeoples)
	{
		OffWorkPeoples = aOffWorkPeoples;
	}
	public void setOffWorkPeoples(String aOffWorkPeoples)
	{
		if (aOffWorkPeoples != null && !aOffWorkPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOffWorkPeoples);
			int i = tInteger.intValue();
			OffWorkPeoples = i;
		}
	}

	public int getOtherPeoples()
	{
		return OtherPeoples;
	}
	public void setOtherPeoples(int aOtherPeoples)
	{
		OtherPeoples = aOtherPeoples;
	}
	public void setOtherPeoples(String aOtherPeoples)
	{
		if (aOtherPeoples != null && !aOtherPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOtherPeoples);
			int i = tInteger.intValue();
			OtherPeoples = i;
		}
	}

	public String getBusinessBigType()
	{
		return BusinessBigType;
	}
	public void setBusinessBigType(String aBusinessBigType)
	{
		if(aBusinessBigType!=null && aBusinessBigType.length()>2)
			throw new IllegalArgumentException("行业大类BusinessBigType值"+aBusinessBigType+"的长度"+aBusinessBigType.length()+"大于最大值2");
		BusinessBigType = aBusinessBigType;
	}
	public String getSocialInsuNo()
	{
		return SocialInsuNo;
	}
	public void setSocialInsuNo(String aSocialInsuNo)
	{
		if(aSocialInsuNo!=null && aSocialInsuNo.length()>20)
			throw new IllegalArgumentException("单位社保登记号SocialInsuNo值"+aSocialInsuNo+"的长度"+aSocialInsuNo.length()+"大于最大值20");
		SocialInsuNo = aSocialInsuNo;
	}
	public String getBlackListReason()
	{
		return BlackListReason;
	}
	public void setBlackListReason(String aBlackListReason)
	{
		if(aBlackListReason!=null && aBlackListReason.length()>3)
			throw new IllegalArgumentException("黑名单原因BlackListReason值"+aBlackListReason+"的长度"+aBlackListReason.length()+"大于最大值3");
		BlackListReason = aBlackListReason;
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
		if(aLicense!=null && aLicense.length()>50)
			throw new IllegalArgumentException("营业执照号License值"+aLicense+"的长度"+aLicense.length()+"大于最大值50");
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

	/**
	* 使用另外一个 LDGrpSchema 对象给 Schema 赋值
	* @param: aLDGrpSchema LDGrpSchema
	**/
	public void setSchema(LDGrpSchema aLDGrpSchema)
	{
		this.CustomerNo = aLDGrpSchema.getCustomerNo();
		this.Password = aLDGrpSchema.getPassword();
		this.GrpName = aLDGrpSchema.getGrpName();
		this.BusinessType = aLDGrpSchema.getBusinessType();
		this.GrpNature = aLDGrpSchema.getGrpNature();
		this.Peoples = aLDGrpSchema.getPeoples();
		this.RgtMoney = aLDGrpSchema.getRgtMoney();
		this.Asset = aLDGrpSchema.getAsset();
		this.NetProfitRate = aLDGrpSchema.getNetProfitRate();
		this.MainBussiness = aLDGrpSchema.getMainBussiness();
		this.Corporation = aLDGrpSchema.getCorporation();
		this.ComAera = aLDGrpSchema.getComAera();
		this.Fax = aLDGrpSchema.getFax();
		this.Phone = aLDGrpSchema.getPhone();
		this.GetFlag = aLDGrpSchema.getGetFlag();
		this.Satrap = aLDGrpSchema.getSatrap();
		this.EMail = aLDGrpSchema.getEMail();
		this.FoundDate = fDate.getDate( aLDGrpSchema.getFoundDate());
		this.BankCode = aLDGrpSchema.getBankCode();
		this.BankAccNo = aLDGrpSchema.getBankAccNo();
		this.GrpGroupNo = aLDGrpSchema.getGrpGroupNo();
		this.BlacklistFlag = aLDGrpSchema.getBlacklistFlag();
		this.State = aLDGrpSchema.getState();
		this.Remark = aLDGrpSchema.getRemark();
		this.VIPValue = aLDGrpSchema.getVIPValue();
		this.Operator = aLDGrpSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDGrpSchema.getMakeDate());
		this.MakeTime = aLDGrpSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDGrpSchema.getModifyDate());
		this.ModifyTime = aLDGrpSchema.getModifyTime();
		this.SubCompanyFlag = aLDGrpSchema.getSubCompanyFlag();
		this.SupCustoemrNo = aLDGrpSchema.getSupCustoemrNo();
		this.LevelCode = aLDGrpSchema.getLevelCode();
		this.OnWorkPeoples = aLDGrpSchema.getOnWorkPeoples();
		this.OffWorkPeoples = aLDGrpSchema.getOffWorkPeoples();
		this.OtherPeoples = aLDGrpSchema.getOtherPeoples();
		this.BusinessBigType = aLDGrpSchema.getBusinessBigType();
		this.SocialInsuNo = aLDGrpSchema.getSocialInsuNo();
		this.BlackListReason = aLDGrpSchema.getBlackListReason();
		this.GrpNamePY = aLDGrpSchema.getGrpNamePY();
		this.SearchKeyWord = aLDGrpSchema.getSearchKeyWord();
		this.CorIDType = aLDGrpSchema.getCorIDType();
		this.CorID = aLDGrpSchema.getCorID();
		this.CorIDExpiryDate = fDate.getDate( aLDGrpSchema.getCorIDExpiryDate());
		this.ActuCtrl = aLDGrpSchema.getActuCtrl();
		this.License = aLDGrpSchema.getLicense();
		this.OrganizationCode = aLDGrpSchema.getOrganizationCode();
		this.TaxCode = aLDGrpSchema.getTaxCode();
		this.ManageCom = aLDGrpSchema.getManageCom();
		this.ComCode = aLDGrpSchema.getComCode();
		this.ModifyOperator = aLDGrpSchema.getModifyOperator();
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
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("BusinessType") == null )
				this.BusinessType = null;
			else
				this.BusinessType = rs.getString("BusinessType").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			this.Peoples = rs.getInt("Peoples");
			this.RgtMoney = rs.getDouble("RgtMoney");
			this.Asset = rs.getDouble("Asset");
			this.NetProfitRate = rs.getDouble("NetProfitRate");
			if( rs.getString("MainBussiness") == null )
				this.MainBussiness = null;
			else
				this.MainBussiness = rs.getString("MainBussiness").trim();

			if( rs.getString("Corporation") == null )
				this.Corporation = null;
			else
				this.Corporation = rs.getString("Corporation").trim();

			if( rs.getString("ComAera") == null )
				this.ComAera = null;
			else
				this.ComAera = rs.getString("ComAera").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

			if( rs.getString("Satrap") == null )
				this.Satrap = null;
			else
				this.Satrap = rs.getString("Satrap").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			this.FoundDate = rs.getDate("FoundDate");
			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("GrpGroupNo") == null )
				this.GrpGroupNo = null;
			else
				this.GrpGroupNo = rs.getString("GrpGroupNo").trim();

			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("VIPValue") == null )
				this.VIPValue = null;
			else
				this.VIPValue = rs.getString("VIPValue").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("SubCompanyFlag") == null )
				this.SubCompanyFlag = null;
			else
				this.SubCompanyFlag = rs.getString("SubCompanyFlag").trim();

			if( rs.getString("SupCustoemrNo") == null )
				this.SupCustoemrNo = null;
			else
				this.SupCustoemrNo = rs.getString("SupCustoemrNo").trim();

			if( rs.getString("LevelCode") == null )
				this.LevelCode = null;
			else
				this.LevelCode = rs.getString("LevelCode").trim();

			this.OnWorkPeoples = rs.getInt("OnWorkPeoples");
			this.OffWorkPeoples = rs.getInt("OffWorkPeoples");
			this.OtherPeoples = rs.getInt("OtherPeoples");
			if( rs.getString("BusinessBigType") == null )
				this.BusinessBigType = null;
			else
				this.BusinessBigType = rs.getString("BusinessBigType").trim();

			if( rs.getString("SocialInsuNo") == null )
				this.SocialInsuNo = null;
			else
				this.SocialInsuNo = rs.getString("SocialInsuNo").trim();

			if( rs.getString("BlackListReason") == null )
				this.BlackListReason = null;
			else
				this.BlackListReason = rs.getString("BlackListReason").trim();

			if( rs.getString("GrpNamePY") == null )
				this.GrpNamePY = null;
			else
				this.GrpNamePY = rs.getString("GrpNamePY").trim();

			if( rs.getString("SearchKeyWord") == null )
				this.SearchKeyWord = null;
			else
				this.SearchKeyWord = rs.getString("SearchKeyWord").trim();

			if( rs.getString("CorIDType") == null )
				this.CorIDType = null;
			else
				this.CorIDType = rs.getString("CorIDType").trim();

			if( rs.getString("CorID") == null )
				this.CorID = null;
			else
				this.CorID = rs.getString("CorID").trim();

			this.CorIDExpiryDate = rs.getDate("CorIDExpiryDate");
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

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDGrp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDGrpSchema getSchema()
	{
		LDGrpSchema aLDGrpSchema = new LDGrpSchema();
		aLDGrpSchema.setSchema(this);
		return aLDGrpSchema;
	}

	public LDGrpDB getDB()
	{
		LDGrpDB aDBOper = new LDGrpDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDGrp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RgtMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Asset));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetProfitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBussiness)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Corporation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComAera)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Satrap)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpGroupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCompanyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SupCustoemrNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LevelCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnWorkPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OffWorkPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessBigType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlackListReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SearchKeyWord)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CorID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CorIDExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActuCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganizationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaxCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDGrp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BusinessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			RgtMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Asset = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			MainBussiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ComAera = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GrpGroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			VIPValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			SubCompanyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			SupCustoemrNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			LevelCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			OnWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			OffWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).intValue();
			OtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).intValue();
			BusinessBigType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SocialInsuNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			BlackListReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			GrpNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			SearchKeyWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			CorIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			CorID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			CorIDExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			ActuCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			OrganizationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			TaxCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpSchema";
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("BusinessType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessType));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples));
		}
		if (FCode.equalsIgnoreCase("RgtMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtMoney));
		}
		if (FCode.equalsIgnoreCase("Asset"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Asset));
		}
		if (FCode.equalsIgnoreCase("NetProfitRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetProfitRate));
		}
		if (FCode.equalsIgnoreCase("MainBussiness"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainBussiness));
		}
		if (FCode.equalsIgnoreCase("Corporation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Corporation));
		}
		if (FCode.equalsIgnoreCase("ComAera"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComAera));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
		}
		if (FCode.equalsIgnoreCase("Satrap"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Satrap));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("GrpGroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpGroupNo));
		}
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("VIPValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPValue));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("SubCompanyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCompanyFlag));
		}
		if (FCode.equalsIgnoreCase("SupCustoemrNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SupCustoemrNo));
		}
		if (FCode.equalsIgnoreCase("LevelCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LevelCode));
		}
		if (FCode.equalsIgnoreCase("OnWorkPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnWorkPeoples));
		}
		if (FCode.equalsIgnoreCase("OffWorkPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OffWorkPeoples));
		}
		if (FCode.equalsIgnoreCase("OtherPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherPeoples));
		}
		if (FCode.equalsIgnoreCase("BusinessBigType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessBigType));
		}
		if (FCode.equalsIgnoreCase("SocialInsuNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuNo));
		}
		if (FCode.equalsIgnoreCase("BlackListReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlackListReason));
		}
		if (FCode.equalsIgnoreCase("GrpNamePY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNamePY));
		}
		if (FCode.equalsIgnoreCase("SearchKeyWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SearchKeyWord));
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 5:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 6:
				strFieldValue = String.valueOf(RgtMoney);
				break;
			case 7:
				strFieldValue = String.valueOf(Asset);
				break;
			case 8:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MainBussiness);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ComAera);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GrpGroupNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(VIPValue);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(SubCompanyFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(SupCustoemrNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(LevelCode);
				break;
			case 33:
				strFieldValue = String.valueOf(OnWorkPeoples);
				break;
			case 34:
				strFieldValue = String.valueOf(OffWorkPeoples);
				break;
			case 35:
				strFieldValue = String.valueOf(OtherPeoples);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(BusinessBigType);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuNo);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(BlackListReason);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(GrpNamePY);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(SearchKeyWord);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(CorIDType);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(CorID);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCorIDExpiryDate()));
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ActuCtrl);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(OrganizationCode);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(TaxCode);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equalsIgnoreCase("BusinessType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessType = FValue.trim();
			}
			else
				BusinessType = null;
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
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RgtMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RgtMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("Asset"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Asset = d;
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
		if (FCode.equalsIgnoreCase("MainBussiness"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainBussiness = FValue.trim();
			}
			else
				MainBussiness = null;
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
		if (FCode.equalsIgnoreCase("ComAera"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComAera = FValue.trim();
			}
			else
				ComAera = null;
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
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFlag = FValue.trim();
			}
			else
				GetFlag = null;
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpGroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpGroupNo = FValue.trim();
			}
			else
				GrpGroupNo = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("VIPValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VIPValue = FValue.trim();
			}
			else
				VIPValue = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("SubCompanyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCompanyFlag = FValue.trim();
			}
			else
				SubCompanyFlag = null;
		}
		if (FCode.equalsIgnoreCase("SupCustoemrNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SupCustoemrNo = FValue.trim();
			}
			else
				SupCustoemrNo = null;
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
		if (FCode.equalsIgnoreCase("OnWorkPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnWorkPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OffWorkPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OffWorkPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OtherPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("BusinessBigType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessBigType = FValue.trim();
			}
			else
				BusinessBigType = null;
		}
		if (FCode.equalsIgnoreCase("SocialInsuNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuNo = FValue.trim();
			}
			else
				SocialInsuNo = null;
		}
		if (FCode.equalsIgnoreCase("BlackListReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlackListReason = FValue.trim();
			}
			else
				BlackListReason = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDGrpSchema other = (LDGrpSchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& Password.equals(other.getPassword())
			&& GrpName.equals(other.getGrpName())
			&& BusinessType.equals(other.getBusinessType())
			&& GrpNature.equals(other.getGrpNature())
			&& Peoples == other.getPeoples()
			&& RgtMoney == other.getRgtMoney()
			&& Asset == other.getAsset()
			&& NetProfitRate == other.getNetProfitRate()
			&& MainBussiness.equals(other.getMainBussiness())
			&& Corporation.equals(other.getCorporation())
			&& ComAera.equals(other.getComAera())
			&& Fax.equals(other.getFax())
			&& Phone.equals(other.getPhone())
			&& GetFlag.equals(other.getGetFlag())
			&& Satrap.equals(other.getSatrap())
			&& EMail.equals(other.getEMail())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& GrpGroupNo.equals(other.getGrpGroupNo())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
			&& State.equals(other.getState())
			&& Remark.equals(other.getRemark())
			&& VIPValue.equals(other.getVIPValue())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& SubCompanyFlag.equals(other.getSubCompanyFlag())
			&& SupCustoemrNo.equals(other.getSupCustoemrNo())
			&& LevelCode.equals(other.getLevelCode())
			&& OnWorkPeoples == other.getOnWorkPeoples()
			&& OffWorkPeoples == other.getOffWorkPeoples()
			&& OtherPeoples == other.getOtherPeoples()
			&& BusinessBigType.equals(other.getBusinessBigType())
			&& SocialInsuNo.equals(other.getSocialInsuNo())
			&& BlackListReason.equals(other.getBlackListReason())
			&& GrpNamePY.equals(other.getGrpNamePY())
			&& SearchKeyWord.equals(other.getSearchKeyWord())
			&& CorIDType.equals(other.getCorIDType())
			&& CorID.equals(other.getCorID())
			&& fDate.getString(CorIDExpiryDate).equals(other.getCorIDExpiryDate())
			&& ActuCtrl.equals(other.getActuCtrl())
			&& License.equals(other.getLicense())
			&& OrganizationCode.equals(other.getOrganizationCode())
			&& TaxCode.equals(other.getTaxCode())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("CustomerNo") ) {
			return 0;
		}
		if( strFieldName.equals("Password") ) {
			return 1;
		}
		if( strFieldName.equals("GrpName") ) {
			return 2;
		}
		if( strFieldName.equals("BusinessType") ) {
			return 3;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 4;
		}
		if( strFieldName.equals("Peoples") ) {
			return 5;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return 6;
		}
		if( strFieldName.equals("Asset") ) {
			return 7;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 8;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return 9;
		}
		if( strFieldName.equals("Corporation") ) {
			return 10;
		}
		if( strFieldName.equals("ComAera") ) {
			return 11;
		}
		if( strFieldName.equals("Fax") ) {
			return 12;
		}
		if( strFieldName.equals("Phone") ) {
			return 13;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 14;
		}
		if( strFieldName.equals("Satrap") ) {
			return 15;
		}
		if( strFieldName.equals("EMail") ) {
			return 16;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 17;
		}
		if( strFieldName.equals("BankCode") ) {
			return 18;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 19;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return 20;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 21;
		}
		if( strFieldName.equals("State") ) {
			return 22;
		}
		if( strFieldName.equals("Remark") ) {
			return 23;
		}
		if( strFieldName.equals("VIPValue") ) {
			return 24;
		}
		if( strFieldName.equals("Operator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 29;
		}
		if( strFieldName.equals("SubCompanyFlag") ) {
			return 30;
		}
		if( strFieldName.equals("SupCustoemrNo") ) {
			return 31;
		}
		if( strFieldName.equals("LevelCode") ) {
			return 32;
		}
		if( strFieldName.equals("OnWorkPeoples") ) {
			return 33;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return 34;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return 35;
		}
		if( strFieldName.equals("BusinessBigType") ) {
			return 36;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return 37;
		}
		if( strFieldName.equals("BlackListReason") ) {
			return 38;
		}
		if( strFieldName.equals("GrpNamePY") ) {
			return 39;
		}
		if( strFieldName.equals("SearchKeyWord") ) {
			return 40;
		}
		if( strFieldName.equals("CorIDType") ) {
			return 41;
		}
		if( strFieldName.equals("CorID") ) {
			return 42;
		}
		if( strFieldName.equals("CorIDExpiryDate") ) {
			return 43;
		}
		if( strFieldName.equals("ActuCtrl") ) {
			return 44;
		}
		if( strFieldName.equals("License") ) {
			return 45;
		}
		if( strFieldName.equals("OrganizationCode") ) {
			return 46;
		}
		if( strFieldName.equals("TaxCode") ) {
			return 47;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 48;
		}
		if( strFieldName.equals("ComCode") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 50;
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
				strFieldName = "CustomerNo";
				break;
			case 1:
				strFieldName = "Password";
				break;
			case 2:
				strFieldName = "GrpName";
				break;
			case 3:
				strFieldName = "BusinessType";
				break;
			case 4:
				strFieldName = "GrpNature";
				break;
			case 5:
				strFieldName = "Peoples";
				break;
			case 6:
				strFieldName = "RgtMoney";
				break;
			case 7:
				strFieldName = "Asset";
				break;
			case 8:
				strFieldName = "NetProfitRate";
				break;
			case 9:
				strFieldName = "MainBussiness";
				break;
			case 10:
				strFieldName = "Corporation";
				break;
			case 11:
				strFieldName = "ComAera";
				break;
			case 12:
				strFieldName = "Fax";
				break;
			case 13:
				strFieldName = "Phone";
				break;
			case 14:
				strFieldName = "GetFlag";
				break;
			case 15:
				strFieldName = "Satrap";
				break;
			case 16:
				strFieldName = "EMail";
				break;
			case 17:
				strFieldName = "FoundDate";
				break;
			case 18:
				strFieldName = "BankCode";
				break;
			case 19:
				strFieldName = "BankAccNo";
				break;
			case 20:
				strFieldName = "GrpGroupNo";
				break;
			case 21:
				strFieldName = "BlacklistFlag";
				break;
			case 22:
				strFieldName = "State";
				break;
			case 23:
				strFieldName = "Remark";
				break;
			case 24:
				strFieldName = "VIPValue";
				break;
			case 25:
				strFieldName = "Operator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "ModifyDate";
				break;
			case 29:
				strFieldName = "ModifyTime";
				break;
			case 30:
				strFieldName = "SubCompanyFlag";
				break;
			case 31:
				strFieldName = "SupCustoemrNo";
				break;
			case 32:
				strFieldName = "LevelCode";
				break;
			case 33:
				strFieldName = "OnWorkPeoples";
				break;
			case 34:
				strFieldName = "OffWorkPeoples";
				break;
			case 35:
				strFieldName = "OtherPeoples";
				break;
			case 36:
				strFieldName = "BusinessBigType";
				break;
			case 37:
				strFieldName = "SocialInsuNo";
				break;
			case 38:
				strFieldName = "BlackListReason";
				break;
			case 39:
				strFieldName = "GrpNamePY";
				break;
			case 40:
				strFieldName = "SearchKeyWord";
				break;
			case 41:
				strFieldName = "CorIDType";
				break;
			case 42:
				strFieldName = "CorID";
				break;
			case 43:
				strFieldName = "CorIDExpiryDate";
				break;
			case 44:
				strFieldName = "ActuCtrl";
				break;
			case 45:
				strFieldName = "License";
				break;
			case 46:
				strFieldName = "OrganizationCode";
				break;
			case 47:
				strFieldName = "TaxCode";
				break;
			case 48:
				strFieldName = "ManageCom";
				break;
			case 49:
				strFieldName = "ComCode";
				break;
			case 50:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Asset") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Corporation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComAera") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Satrap") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VIPValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCompanyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SupCustoemrNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LevelCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnWorkPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BusinessBigType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlackListReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNamePY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SearchKeyWord") ) {
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_INT;
				break;
			case 34:
				nFieldType = Schema.TYPE_INT;
				break;
			case 35:
				nFieldType = Schema.TYPE_INT;
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
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
