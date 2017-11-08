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
import com.sinosoft.lis.db.LCAppntDB;

/*
 * <p>ClassName: LCAppntSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCAppntSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCAppntSchema.class);
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人级别 */
	private String AppntGrade;
	/** 投保人名称 */
	private String AppntName;
	/** 投保人性别 */
	private String AppntSex;
	/** 投保人出生日期 */
	private Date AppntBirthday;
	/** 投保人类型 */
	private String AppntType;
	/** 客户地址号码 */
	private String AddressNo;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 国籍 */
	private String NativePlace;
	/** 民族 */
	private String Nationality;
	/** 户口所在地 */
	private String RgtAddress;
	/** 婚姻状况 */
	private String Marriage;
	/** 结婚日期 */
	private Date MarriageDate;
	/** 健康状况 */
	private String Health;
	/** 身高 */
	private double Stature;
	/** 体重 */
	private double Avoirdupois;
	/** 学历 */
	private String Degree;
	/** 信用等级 */
	private String CreditGrade;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 入司日期 */
	private Date JoinCompanyDate;
	/** 参加工作日期 */
	private Date StartWorkDate;
	/** 职位 */
	private String Position;
	/** 工资 */
	private double Salary;
	/** 职业类别 */
	private String OccupationType;
	/** 职业代码 */
	private String OccupationCode;
	/** 职业（工种） */
	private String WorkType;
	/** 兼职（工种） */
	private String PluralityType;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 身体指标 */
	private double BMI;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;
	/** 与被保人关系 */
	private String RelationToInsured;
	/** 是否有社保标志 */
	private String SocialInsuFlag;
	/** 证件有效期 */
	private Date IDExpDate;
	/** 姓 */
	private String LastName;
	/** 名 */
	private String FirstName;
	/** 英文姓 */
	private String LastNameEn;
	/** 英文名 */
	private String FirstNameEn;
	/** 英文姓名 */
	private String NameEn;
	/** 拼音姓 */
	private String LastNamePY;
	/** 拼音名 */
	private String FirstNamePY;
	/** 语言 */
	private String Language;
	/** 证件开始日期 */
	private Date IDStartDate;
	/** 证件结束日期 */
	private Date IDEndDate;
	/** 工作单位 */
	private String WorkCompName;
	/** 工作证号 */
	private String WorkIDNo;
	/** 状态 */
	private String State;
	/** 死亡日期 */
	private Date DeathDate;
	/** 黑名单标识 */
	private String BlacklistFlag;
	/** Vip值 */
	private String VIPValue;
	/** 短信标识 */
	private String SendMsgFlag;
	/** 邮件标识 */
	private String SendMailFlag;
	/** Qq信息标识 */
	private String SendQQFlag;
	/** Msn信息标识 */
	private String SendMSNFlag;
	/** 微信标示 */
	private String WechatFlag;
	/** 备注 */
	private String Remark;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 70;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCAppntSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ContNo";

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
		LCAppntSchema cloned = (LCAppntSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 冗余，标准在个人保单表，方便查询统计
	*/
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	/**
	* 冗余，标准在个人保单表
	*/
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		if(aPrtNo!=null && aPrtNo.length()>20)
			throw new IllegalArgumentException("印刷号码PrtNo值"+aPrtNo+"的长度"+aPrtNo.length()+"大于最大值20");
		PrtNo = aPrtNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人客户号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	/**
	* 备用
	*/
	public String getAppntGrade()
	{
		return AppntGrade;
	}
	public void setAppntGrade(String aAppntGrade)
	{
		if(aAppntGrade!=null && aAppntGrade.length()>1)
			throw new IllegalArgumentException("投保人级别AppntGrade值"+aAppntGrade+"的长度"+aAppntGrade.length()+"大于最大值1");
		AppntGrade = aAppntGrade;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>120)
			throw new IllegalArgumentException("投保人名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值120");
		AppntName = aAppntName;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getAppntSex()
	{
		return AppntSex;
	}
	public void setAppntSex(String aAppntSex)
	{
		if(aAppntSex!=null && aAppntSex.length()>1)
			throw new IllegalArgumentException("投保人性别AppntSex值"+aAppntSex+"的长度"+aAppntSex.length()+"大于最大值1");
		AppntSex = aAppntSex;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getAppntBirthday()
	{
		if( AppntBirthday != null )
			return fDate.getString(AppntBirthday);
		else
			return null;
	}
	public void setAppntBirthday(Date aAppntBirthday)
	{
		AppntBirthday = aAppntBirthday;
	}
	public void setAppntBirthday(String aAppntBirthday)
	{
		if (aAppntBirthday != null && !aAppntBirthday.equals("") )
		{
			AppntBirthday = fDate.getDate( aAppntBirthday );
		}
		else
			AppntBirthday = null;
	}

	/**
	* 备用
	*/
	public String getAppntType()
	{
		return AppntType;
	}
	public void setAppntType(String aAppntType)
	{
		if(aAppntType!=null && aAppntType.length()>1)
			throw new IllegalArgumentException("投保人类型AppntType值"+aAppntType+"的长度"+aAppntType.length()+"大于最大值1");
		AppntType = aAppntType;
	}
	public String getAddressNo()
	{
		return AddressNo;
	}
	public void setAddressNo(String aAddressNo)
	{
		if(aAddressNo!=null && aAddressNo.length()>20)
			throw new IllegalArgumentException("客户地址号码AddressNo值"+aAddressNo+"的长度"+aAddressNo.length()+"大于最大值20");
		AddressNo = aAddressNo;
	}
	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>1)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值1");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>20)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值20");
		IDNo = aIDNo;
	}
	public String getNativePlace()
	{
		return NativePlace;
	}
	public void setNativePlace(String aNativePlace)
	{
		if(aNativePlace!=null && aNativePlace.length()>3)
			throw new IllegalArgumentException("国籍NativePlace值"+aNativePlace+"的长度"+aNativePlace.length()+"大于最大值3");
		NativePlace = aNativePlace;
	}
	public String getNationality()
	{
		return Nationality;
	}
	public void setNationality(String aNationality)
	{
		if(aNationality!=null && aNationality.length()>3)
			throw new IllegalArgumentException("民族Nationality值"+aNationality+"的长度"+aNationality.length()+"大于最大值3");
		Nationality = aNationality;
	}
	public String getRgtAddress()
	{
		return RgtAddress;
	}
	public void setRgtAddress(String aRgtAddress)
	{
		if(aRgtAddress!=null && aRgtAddress.length()>80)
			throw new IllegalArgumentException("户口所在地RgtAddress值"+aRgtAddress+"的长度"+aRgtAddress.length()+"大于最大值80");
		RgtAddress = aRgtAddress;
	}
	public String getMarriage()
	{
		return Marriage;
	}
	public void setMarriage(String aMarriage)
	{
		if(aMarriage!=null && aMarriage.length()>1)
			throw new IllegalArgumentException("婚姻状况Marriage值"+aMarriage+"的长度"+aMarriage.length()+"大于最大值1");
		Marriage = aMarriage;
	}
	public String getMarriageDate()
	{
		if( MarriageDate != null )
			return fDate.getString(MarriageDate);
		else
			return null;
	}
	public void setMarriageDate(Date aMarriageDate)
	{
		MarriageDate = aMarriageDate;
	}
	public void setMarriageDate(String aMarriageDate)
	{
		if (aMarriageDate != null && !aMarriageDate.equals("") )
		{
			MarriageDate = fDate.getDate( aMarriageDate );
		}
		else
			MarriageDate = null;
	}

	public String getHealth()
	{
		return Health;
	}
	public void setHealth(String aHealth)
	{
		if(aHealth!=null && aHealth.length()>6)
			throw new IllegalArgumentException("健康状况Health值"+aHealth+"的长度"+aHealth.length()+"大于最大值6");
		Health = aHealth;
	}
	public double getStature()
	{
		return Stature;
	}
	public void setStature(double aStature)
	{
		Stature = aStature;
	}
	public void setStature(String aStature)
	{
		if (aStature != null && !aStature.equals(""))
		{
			Double tDouble = new Double(aStature);
			double d = tDouble.doubleValue();
			Stature = d;
		}
	}

	public double getAvoirdupois()
	{
		return Avoirdupois;
	}
	public void setAvoirdupois(double aAvoirdupois)
	{
		Avoirdupois = aAvoirdupois;
	}
	public void setAvoirdupois(String aAvoirdupois)
	{
		if (aAvoirdupois != null && !aAvoirdupois.equals(""))
		{
			Double tDouble = new Double(aAvoirdupois);
			double d = tDouble.doubleValue();
			Avoirdupois = d;
		}
	}

	public String getDegree()
	{
		return Degree;
	}
	public void setDegree(String aDegree)
	{
		if(aDegree!=null && aDegree.length()>6)
			throw new IllegalArgumentException("学历Degree值"+aDegree+"的长度"+aDegree.length()+"大于最大值6");
		Degree = aDegree;
	}
	public String getCreditGrade()
	{
		return CreditGrade;
	}
	public void setCreditGrade(String aCreditGrade)
	{
		if(aCreditGrade!=null && aCreditGrade.length()>1)
			throw new IllegalArgumentException("信用等级CreditGrade值"+aCreditGrade+"的长度"+aCreditGrade.length()+"大于最大值1");
		CreditGrade = aCreditGrade;
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
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>60)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值60");
		AccName = aAccName;
	}
	public String getJoinCompanyDate()
	{
		if( JoinCompanyDate != null )
			return fDate.getString(JoinCompanyDate);
		else
			return null;
	}
	public void setJoinCompanyDate(Date aJoinCompanyDate)
	{
		JoinCompanyDate = aJoinCompanyDate;
	}
	public void setJoinCompanyDate(String aJoinCompanyDate)
	{
		if (aJoinCompanyDate != null && !aJoinCompanyDate.equals("") )
		{
			JoinCompanyDate = fDate.getDate( aJoinCompanyDate );
		}
		else
			JoinCompanyDate = null;
	}

	public String getStartWorkDate()
	{
		if( StartWorkDate != null )
			return fDate.getString(StartWorkDate);
		else
			return null;
	}
	public void setStartWorkDate(Date aStartWorkDate)
	{
		StartWorkDate = aStartWorkDate;
	}
	public void setStartWorkDate(String aStartWorkDate)
	{
		if (aStartWorkDate != null && !aStartWorkDate.equals("") )
		{
			StartWorkDate = fDate.getDate( aStartWorkDate );
		}
		else
			StartWorkDate = null;
	}

	public String getPosition()
	{
		return Position;
	}
	public void setPosition(String aPosition)
	{
		if(aPosition!=null && aPosition.length()>6)
			throw new IllegalArgumentException("职位Position值"+aPosition+"的长度"+aPosition.length()+"大于最大值6");
		Position = aPosition;
	}
	public double getSalary()
	{
		return Salary;
	}
	public void setSalary(double aSalary)
	{
		Salary = aSalary;
	}
	public void setSalary(String aSalary)
	{
		if (aSalary != null && !aSalary.equals(""))
		{
			Double tDouble = new Double(aSalary);
			double d = tDouble.doubleValue();
			Salary = d;
		}
	}

	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		if(aOccupationType!=null && aOccupationType.length()>10)
			throw new IllegalArgumentException("职业类别OccupationType值"+aOccupationType+"的长度"+aOccupationType.length()+"大于最大值10");
		OccupationType = aOccupationType;
	}
	public String getOccupationCode()
	{
		return OccupationCode;
	}
	public void setOccupationCode(String aOccupationCode)
	{
		if(aOccupationCode!=null && aOccupationCode.length()>10)
			throw new IllegalArgumentException("职业代码OccupationCode值"+aOccupationCode+"的长度"+aOccupationCode.length()+"大于最大值10");
		OccupationCode = aOccupationCode;
	}
	public String getWorkType()
	{
		return WorkType;
	}
	public void setWorkType(String aWorkType)
	{
		if(aWorkType!=null && aWorkType.length()>300)
			throw new IllegalArgumentException("职业（工种）WorkType值"+aWorkType+"的长度"+aWorkType.length()+"大于最大值300");
		WorkType = aWorkType;
	}
	public String getPluralityType()
	{
		return PluralityType;
	}
	public void setPluralityType(String aPluralityType)
	{
		if(aPluralityType!=null && aPluralityType.length()>40)
			throw new IllegalArgumentException("兼职（工种）PluralityType值"+aPluralityType+"的长度"+aPluralityType.length()+"大于最大值40");
		PluralityType = aPluralityType;
	}
	public String getSmokeFlag()
	{
		return SmokeFlag;
	}
	public void setSmokeFlag(String aSmokeFlag)
	{
		if(aSmokeFlag!=null && aSmokeFlag.length()>1)
			throw new IllegalArgumentException("是否吸烟标志SmokeFlag值"+aSmokeFlag+"的长度"+aSmokeFlag.length()+"大于最大值1");
		SmokeFlag = aSmokeFlag;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
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
	public double getBMI()
	{
		return BMI;
	}
	public void setBMI(double aBMI)
	{
		BMI = aBMI;
	}
	public void setBMI(String aBMI)
	{
		if (aBMI != null && !aBMI.equals(""))
		{
			Double tDouble = new Double(aBMI);
			double d = tDouble.doubleValue();
			BMI = d;
		}
	}

	public String getLicense()
	{
		return License;
	}
	public void setLicense(String aLicense)
	{
		if(aLicense!=null && aLicense.length()>1)
			throw new IllegalArgumentException("驾照License值"+aLicense+"的长度"+aLicense.length()+"大于最大值1");
		License = aLicense;
	}
	public String getLicenseType()
	{
		return LicenseType;
	}
	public void setLicenseType(String aLicenseType)
	{
		if(aLicenseType!=null && aLicenseType.length()>4)
			throw new IllegalArgumentException("驾照类型LicenseType值"+aLicenseType+"的长度"+aLicenseType.length()+"大于最大值4");
		LicenseType = aLicenseType;
	}
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		if(aRelationToInsured!=null && aRelationToInsured.length()>2)
			throw new IllegalArgumentException("与被保人关系RelationToInsured值"+aRelationToInsured+"的长度"+aRelationToInsured.length()+"大于最大值2");
		RelationToInsured = aRelationToInsured;
	}
	public String getSocialInsuFlag()
	{
		return SocialInsuFlag;
	}
	public void setSocialInsuFlag(String aSocialInsuFlag)
	{
		if(aSocialInsuFlag!=null && aSocialInsuFlag.length()>1)
			throw new IllegalArgumentException("是否有社保标志SocialInsuFlag值"+aSocialInsuFlag+"的长度"+aSocialInsuFlag.length()+"大于最大值1");
		SocialInsuFlag = aSocialInsuFlag;
	}
	public String getIDExpDate()
	{
		if( IDExpDate != null )
			return fDate.getString(IDExpDate);
		else
			return null;
	}
	public void setIDExpDate(Date aIDExpDate)
	{
		IDExpDate = aIDExpDate;
	}
	public void setIDExpDate(String aIDExpDate)
	{
		if (aIDExpDate != null && !aIDExpDate.equals("") )
		{
			IDExpDate = fDate.getDate( aIDExpDate );
		}
		else
			IDExpDate = null;
	}

	public String getLastName()
	{
		return LastName;
	}
	public void setLastName(String aLastName)
	{
		if(aLastName!=null && aLastName.length()>80)
			throw new IllegalArgumentException("姓LastName值"+aLastName+"的长度"+aLastName.length()+"大于最大值80");
		LastName = aLastName;
	}
	public String getFirstName()
	{
		return FirstName;
	}
	public void setFirstName(String aFirstName)
	{
		if(aFirstName!=null && aFirstName.length()>80)
			throw new IllegalArgumentException("名FirstName值"+aFirstName+"的长度"+aFirstName.length()+"大于最大值80");
		FirstName = aFirstName;
	}
	public String getLastNameEn()
	{
		return LastNameEn;
	}
	public void setLastNameEn(String aLastNameEn)
	{
		if(aLastNameEn!=null && aLastNameEn.length()>80)
			throw new IllegalArgumentException("英文姓LastNameEn值"+aLastNameEn+"的长度"+aLastNameEn.length()+"大于最大值80");
		LastNameEn = aLastNameEn;
	}
	public String getFirstNameEn()
	{
		return FirstNameEn;
	}
	public void setFirstNameEn(String aFirstNameEn)
	{
		if(aFirstNameEn!=null && aFirstNameEn.length()>80)
			throw new IllegalArgumentException("英文名FirstNameEn值"+aFirstNameEn+"的长度"+aFirstNameEn.length()+"大于最大值80");
		FirstNameEn = aFirstNameEn;
	}
	public String getNameEn()
	{
		return NameEn;
	}
	public void setNameEn(String aNameEn)
	{
		if(aNameEn!=null && aNameEn.length()>120)
			throw new IllegalArgumentException("英文姓名NameEn值"+aNameEn+"的长度"+aNameEn.length()+"大于最大值120");
		NameEn = aNameEn;
	}
	public String getLastNamePY()
	{
		return LastNamePY;
	}
	public void setLastNamePY(String aLastNamePY)
	{
		if(aLastNamePY!=null && aLastNamePY.length()>80)
			throw new IllegalArgumentException("拼音姓LastNamePY值"+aLastNamePY+"的长度"+aLastNamePY.length()+"大于最大值80");
		LastNamePY = aLastNamePY;
	}
	public String getFirstNamePY()
	{
		return FirstNamePY;
	}
	public void setFirstNamePY(String aFirstNamePY)
	{
		if(aFirstNamePY!=null && aFirstNamePY.length()>80)
			throw new IllegalArgumentException("拼音名FirstNamePY值"+aFirstNamePY+"的长度"+aFirstNamePY.length()+"大于最大值80");
		FirstNamePY = aFirstNamePY;
	}
	public String getLanguage()
	{
		return Language;
	}
	public void setLanguage(String aLanguage)
	{
		if(aLanguage!=null && aLanguage.length()>10)
			throw new IllegalArgumentException("语言Language值"+aLanguage+"的长度"+aLanguage.length()+"大于最大值10");
		Language = aLanguage;
	}
	/**
	* 证件签发日期
	*/
	public String getIDStartDate()
	{
		if( IDStartDate != null )
			return fDate.getString(IDStartDate);
		else
			return null;
	}
	public void setIDStartDate(Date aIDStartDate)
	{
		IDStartDate = aIDStartDate;
	}
	public void setIDStartDate(String aIDStartDate)
	{
		if (aIDStartDate != null && !aIDStartDate.equals("") )
		{
			IDStartDate = fDate.getDate( aIDStartDate );
		}
		else
			IDStartDate = null;
	}

	public String getIDEndDate()
	{
		if( IDEndDate != null )
			return fDate.getString(IDEndDate);
		else
			return null;
	}
	public void setIDEndDate(Date aIDEndDate)
	{
		IDEndDate = aIDEndDate;
	}
	public void setIDEndDate(String aIDEndDate)
	{
		if (aIDEndDate != null && !aIDEndDate.equals("") )
		{
			IDEndDate = fDate.getDate( aIDEndDate );
		}
		else
			IDEndDate = null;
	}

	public String getWorkCompName()
	{
		return WorkCompName;
	}
	public void setWorkCompName(String aWorkCompName)
	{
		if(aWorkCompName!=null && aWorkCompName.length()>200)
			throw new IllegalArgumentException("工作单位WorkCompName值"+aWorkCompName+"的长度"+aWorkCompName.length()+"大于最大值200");
		WorkCompName = aWorkCompName;
	}
	public String getWorkIDNo()
	{
		return WorkIDNo;
	}
	public void setWorkIDNo(String aWorkIDNo)
	{
		if(aWorkIDNo!=null && aWorkIDNo.length()>20)
			throw new IllegalArgumentException("工作证号WorkIDNo值"+aWorkIDNo+"的长度"+aWorkIDNo.length()+"大于最大值20");
		WorkIDNo = aWorkIDNo;
	}
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
	public String getDeathDate()
	{
		if( DeathDate != null )
			return fDate.getString(DeathDate);
		else
			return null;
	}
	public void setDeathDate(Date aDeathDate)
	{
		DeathDate = aDeathDate;
	}
	public void setDeathDate(String aDeathDate)
	{
		if (aDeathDate != null && !aDeathDate.equals("") )
		{
			DeathDate = fDate.getDate( aDeathDate );
		}
		else
			DeathDate = null;
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
		if(aVIPValue!=null && aVIPValue.length()>10)
			throw new IllegalArgumentException("Vip值VIPValue值"+aVIPValue+"的长度"+aVIPValue.length()+"大于最大值10");
		VIPValue = aVIPValue;
	}
	/**
	* 0-不发送，1-发送
	*/
	public String getSendMsgFlag()
	{
		return SendMsgFlag;
	}
	public void setSendMsgFlag(String aSendMsgFlag)
	{
		if(aSendMsgFlag!=null && aSendMsgFlag.length()>1)
			throw new IllegalArgumentException("短信标识SendMsgFlag值"+aSendMsgFlag+"的长度"+aSendMsgFlag.length()+"大于最大值1");
		SendMsgFlag = aSendMsgFlag;
	}
	/**
	* 0-不发送，1-发送
	*/
	public String getSendMailFlag()
	{
		return SendMailFlag;
	}
	public void setSendMailFlag(String aSendMailFlag)
	{
		if(aSendMailFlag!=null && aSendMailFlag.length()>1)
			throw new IllegalArgumentException("邮件标识SendMailFlag值"+aSendMailFlag+"的长度"+aSendMailFlag.length()+"大于最大值1");
		SendMailFlag = aSendMailFlag;
	}
	/**
	* 0-不发送，1-发送
	*/
	public String getSendQQFlag()
	{
		return SendQQFlag;
	}
	public void setSendQQFlag(String aSendQQFlag)
	{
		if(aSendQQFlag!=null && aSendQQFlag.length()>1)
			throw new IllegalArgumentException("Qq信息标识SendQQFlag值"+aSendQQFlag+"的长度"+aSendQQFlag.length()+"大于最大值1");
		SendQQFlag = aSendQQFlag;
	}
	/**
	* 0-不发送，1-发送
	*/
	public String getSendMSNFlag()
	{
		return SendMSNFlag;
	}
	public void setSendMSNFlag(String aSendMSNFlag)
	{
		if(aSendMSNFlag!=null && aSendMSNFlag.length()>1)
			throw new IllegalArgumentException("Msn信息标识SendMSNFlag值"+aSendMSNFlag+"的长度"+aSendMSNFlag.length()+"大于最大值1");
		SendMSNFlag = aSendMSNFlag;
	}
	public String getWechatFlag()
	{
		return WechatFlag;
	}
	public void setWechatFlag(String aWechatFlag)
	{
		if(aWechatFlag!=null && aWechatFlag.length()>1)
			throw new IllegalArgumentException("微信标示WechatFlag值"+aWechatFlag+"的长度"+aWechatFlag.length()+"大于最大值1");
		WechatFlag = aWechatFlag;
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
	* 使用另外一个 LCAppntSchema 对象给 Schema 赋值
	* @param: aLCAppntSchema LCAppntSchema
	**/
	public void setSchema(LCAppntSchema aLCAppntSchema)
	{
		this.GrpContNo = aLCAppntSchema.getGrpContNo();
		this.ContNo = aLCAppntSchema.getContNo();
		this.PrtNo = aLCAppntSchema.getPrtNo();
		this.AppntNo = aLCAppntSchema.getAppntNo();
		this.AppntGrade = aLCAppntSchema.getAppntGrade();
		this.AppntName = aLCAppntSchema.getAppntName();
		this.AppntSex = aLCAppntSchema.getAppntSex();
		this.AppntBirthday = fDate.getDate( aLCAppntSchema.getAppntBirthday());
		this.AppntType = aLCAppntSchema.getAppntType();
		this.AddressNo = aLCAppntSchema.getAddressNo();
		this.IDType = aLCAppntSchema.getIDType();
		this.IDNo = aLCAppntSchema.getIDNo();
		this.NativePlace = aLCAppntSchema.getNativePlace();
		this.Nationality = aLCAppntSchema.getNationality();
		this.RgtAddress = aLCAppntSchema.getRgtAddress();
		this.Marriage = aLCAppntSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLCAppntSchema.getMarriageDate());
		this.Health = aLCAppntSchema.getHealth();
		this.Stature = aLCAppntSchema.getStature();
		this.Avoirdupois = aLCAppntSchema.getAvoirdupois();
		this.Degree = aLCAppntSchema.getDegree();
		this.CreditGrade = aLCAppntSchema.getCreditGrade();
		this.BankCode = aLCAppntSchema.getBankCode();
		this.BankAccNo = aLCAppntSchema.getBankAccNo();
		this.AccName = aLCAppntSchema.getAccName();
		this.JoinCompanyDate = fDate.getDate( aLCAppntSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLCAppntSchema.getStartWorkDate());
		this.Position = aLCAppntSchema.getPosition();
		this.Salary = aLCAppntSchema.getSalary();
		this.OccupationType = aLCAppntSchema.getOccupationType();
		this.OccupationCode = aLCAppntSchema.getOccupationCode();
		this.WorkType = aLCAppntSchema.getWorkType();
		this.PluralityType = aLCAppntSchema.getPluralityType();
		this.SmokeFlag = aLCAppntSchema.getSmokeFlag();
		this.Operator = aLCAppntSchema.getOperator();
		this.ManageCom = aLCAppntSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLCAppntSchema.getMakeDate());
		this.MakeTime = aLCAppntSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCAppntSchema.getModifyDate());
		this.ModifyTime = aLCAppntSchema.getModifyTime();
		this.BMI = aLCAppntSchema.getBMI();
		this.License = aLCAppntSchema.getLicense();
		this.LicenseType = aLCAppntSchema.getLicenseType();
		this.RelationToInsured = aLCAppntSchema.getRelationToInsured();
		this.SocialInsuFlag = aLCAppntSchema.getSocialInsuFlag();
		this.IDExpDate = fDate.getDate( aLCAppntSchema.getIDExpDate());
		this.LastName = aLCAppntSchema.getLastName();
		this.FirstName = aLCAppntSchema.getFirstName();
		this.LastNameEn = aLCAppntSchema.getLastNameEn();
		this.FirstNameEn = aLCAppntSchema.getFirstNameEn();
		this.NameEn = aLCAppntSchema.getNameEn();
		this.LastNamePY = aLCAppntSchema.getLastNamePY();
		this.FirstNamePY = aLCAppntSchema.getFirstNamePY();
		this.Language = aLCAppntSchema.getLanguage();
		this.IDStartDate = fDate.getDate( aLCAppntSchema.getIDStartDate());
		this.IDEndDate = fDate.getDate( aLCAppntSchema.getIDEndDate());
		this.WorkCompName = aLCAppntSchema.getWorkCompName();
		this.WorkIDNo = aLCAppntSchema.getWorkIDNo();
		this.State = aLCAppntSchema.getState();
		this.DeathDate = fDate.getDate( aLCAppntSchema.getDeathDate());
		this.BlacklistFlag = aLCAppntSchema.getBlacklistFlag();
		this.VIPValue = aLCAppntSchema.getVIPValue();
		this.SendMsgFlag = aLCAppntSchema.getSendMsgFlag();
		this.SendMailFlag = aLCAppntSchema.getSendMailFlag();
		this.SendQQFlag = aLCAppntSchema.getSendQQFlag();
		this.SendMSNFlag = aLCAppntSchema.getSendMSNFlag();
		this.WechatFlag = aLCAppntSchema.getWechatFlag();
		this.Remark = aLCAppntSchema.getRemark();
		this.ComCode = aLCAppntSchema.getComCode();
		this.ModifyOperator = aLCAppntSchema.getModifyOperator();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntGrade") == null )
				this.AppntGrade = null;
			else
				this.AppntGrade = rs.getString("AppntGrade").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntSex") == null )
				this.AppntSex = null;
			else
				this.AppntSex = rs.getString("AppntSex").trim();

			this.AppntBirthday = rs.getDate("AppntBirthday");
			if( rs.getString("AppntType") == null )
				this.AppntType = null;
			else
				this.AppntType = rs.getString("AppntType").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("NativePlace") == null )
				this.NativePlace = null;
			else
				this.NativePlace = rs.getString("NativePlace").trim();

			if( rs.getString("Nationality") == null )
				this.Nationality = null;
			else
				this.Nationality = rs.getString("Nationality").trim();

			if( rs.getString("RgtAddress") == null )
				this.RgtAddress = null;
			else
				this.RgtAddress = rs.getString("RgtAddress").trim();

			if( rs.getString("Marriage") == null )
				this.Marriage = null;
			else
				this.Marriage = rs.getString("Marriage").trim();

			this.MarriageDate = rs.getDate("MarriageDate");
			if( rs.getString("Health") == null )
				this.Health = null;
			else
				this.Health = rs.getString("Health").trim();

			this.Stature = rs.getDouble("Stature");
			this.Avoirdupois = rs.getDouble("Avoirdupois");
			if( rs.getString("Degree") == null )
				this.Degree = null;
			else
				this.Degree = rs.getString("Degree").trim();

			if( rs.getString("CreditGrade") == null )
				this.CreditGrade = null;
			else
				this.CreditGrade = rs.getString("CreditGrade").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.JoinCompanyDate = rs.getDate("JoinCompanyDate");
			this.StartWorkDate = rs.getDate("StartWorkDate");
			if( rs.getString("Position") == null )
				this.Position = null;
			else
				this.Position = rs.getString("Position").trim();

			this.Salary = rs.getDouble("Salary");
			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("OccupationCode") == null )
				this.OccupationCode = null;
			else
				this.OccupationCode = rs.getString("OccupationCode").trim();

			if( rs.getString("WorkType") == null )
				this.WorkType = null;
			else
				this.WorkType = rs.getString("WorkType").trim();

			if( rs.getString("PluralityType") == null )
				this.PluralityType = null;
			else
				this.PluralityType = rs.getString("PluralityType").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

			this.BMI = rs.getDouble("BMI");
			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("LicenseType") == null )
				this.LicenseType = null;
			else
				this.LicenseType = rs.getString("LicenseType").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("SocialInsuFlag") == null )
				this.SocialInsuFlag = null;
			else
				this.SocialInsuFlag = rs.getString("SocialInsuFlag").trim();

			this.IDExpDate = rs.getDate("IDExpDate");
			if( rs.getString("LastName") == null )
				this.LastName = null;
			else
				this.LastName = rs.getString("LastName").trim();

			if( rs.getString("FirstName") == null )
				this.FirstName = null;
			else
				this.FirstName = rs.getString("FirstName").trim();

			if( rs.getString("LastNameEn") == null )
				this.LastNameEn = null;
			else
				this.LastNameEn = rs.getString("LastNameEn").trim();

			if( rs.getString("FirstNameEn") == null )
				this.FirstNameEn = null;
			else
				this.FirstNameEn = rs.getString("FirstNameEn").trim();

			if( rs.getString("NameEn") == null )
				this.NameEn = null;
			else
				this.NameEn = rs.getString("NameEn").trim();

			if( rs.getString("LastNamePY") == null )
				this.LastNamePY = null;
			else
				this.LastNamePY = rs.getString("LastNamePY").trim();

			if( rs.getString("FirstNamePY") == null )
				this.FirstNamePY = null;
			else
				this.FirstNamePY = rs.getString("FirstNamePY").trim();

			if( rs.getString("Language") == null )
				this.Language = null;
			else
				this.Language = rs.getString("Language").trim();

			this.IDStartDate = rs.getDate("IDStartDate");
			this.IDEndDate = rs.getDate("IDEndDate");
			if( rs.getString("WorkCompName") == null )
				this.WorkCompName = null;
			else
				this.WorkCompName = rs.getString("WorkCompName").trim();

			if( rs.getString("WorkIDNo") == null )
				this.WorkIDNo = null;
			else
				this.WorkIDNo = rs.getString("WorkIDNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.DeathDate = rs.getDate("DeathDate");
			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

			if( rs.getString("VIPValue") == null )
				this.VIPValue = null;
			else
				this.VIPValue = rs.getString("VIPValue").trim();

			if( rs.getString("SendMsgFlag") == null )
				this.SendMsgFlag = null;
			else
				this.SendMsgFlag = rs.getString("SendMsgFlag").trim();

			if( rs.getString("SendMailFlag") == null )
				this.SendMailFlag = null;
			else
				this.SendMailFlag = rs.getString("SendMailFlag").trim();

			if( rs.getString("SendQQFlag") == null )
				this.SendQQFlag = null;
			else
				this.SendQQFlag = rs.getString("SendQQFlag").trim();

			if( rs.getString("SendMSNFlag") == null )
				this.SendMSNFlag = null;
			else
				this.SendMSNFlag = rs.getString("SendMSNFlag").trim();

			if( rs.getString("WechatFlag") == null )
				this.WechatFlag = null;
			else
				this.WechatFlag = rs.getString("WechatFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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
			logger.debug("数据库中的LCAppnt表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCAppntSchema getSchema()
	{
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		aLCAppntSchema.setSchema(this);
		return aLCAppntSchema;
	}

	public LCAppntDB getDB()
	{
		LCAppntDB aDBOper = new LCAppntDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAppnt描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppntBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NativePlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Nationality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Marriage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MarriageDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Health)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Stature));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Avoirdupois));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Degree)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreditGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( JoinCompanyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartWorkDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Position)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Salary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PluralityType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BMI));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LicenseType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDExpDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastNameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstNameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Language)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkCompName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeathDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendMsgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendMailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendQQFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendMSNFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WechatFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAppnt>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AppntSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppntBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			AppntType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			BMI = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			SocialInsuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			IDExpDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			LastName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			FirstName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			LastNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			FirstNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			NameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			LastNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			FirstNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			Language = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			IDStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55,SysConst.PACKAGESPILTER));
			IDEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56,SysConst.PACKAGESPILTER));
			WorkCompName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			WorkIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60,SysConst.PACKAGESPILTER));
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			VIPValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			SendMsgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			SendMailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			SendQQFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			SendMSNFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			WechatFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntGrade));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("AppntSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntSex));
		}
		if (FCode.equalsIgnoreCase("AppntBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
		}
		if (FCode.equalsIgnoreCase("AppntType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntType));
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("NativePlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NativePlace));
		}
		if (FCode.equalsIgnoreCase("Nationality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Nationality));
		}
		if (FCode.equalsIgnoreCase("RgtAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtAddress));
		}
		if (FCode.equalsIgnoreCase("Marriage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Marriage));
		}
		if (FCode.equalsIgnoreCase("MarriageDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
		}
		if (FCode.equalsIgnoreCase("Health"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Health));
		}
		if (FCode.equalsIgnoreCase("Stature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Stature));
		}
		if (FCode.equalsIgnoreCase("Avoirdupois"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Avoirdupois));
		}
		if (FCode.equalsIgnoreCase("Degree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Degree));
		}
		if (FCode.equalsIgnoreCase("CreditGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreditGrade));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("JoinCompanyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
		}
		if (FCode.equalsIgnoreCase("StartWorkDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
		}
		if (FCode.equalsIgnoreCase("Position"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Position));
		}
		if (FCode.equalsIgnoreCase("Salary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salary));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationCode));
		}
		if (FCode.equalsIgnoreCase("WorkType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkType));
		}
		if (FCode.equalsIgnoreCase("PluralityType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PluralityType));
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
		if (FCode.equalsIgnoreCase("BMI"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BMI));
		}
		if (FCode.equalsIgnoreCase("License"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(License));
		}
		if (FCode.equalsIgnoreCase("LicenseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LicenseType));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("SocialInsuFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuFlag));
		}
		if (FCode.equalsIgnoreCase("IDExpDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDExpDate()));
		}
		if (FCode.equalsIgnoreCase("LastName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastName));
		}
		if (FCode.equalsIgnoreCase("FirstName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstName));
		}
		if (FCode.equalsIgnoreCase("LastNameEn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastNameEn));
		}
		if (FCode.equalsIgnoreCase("FirstNameEn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstNameEn));
		}
		if (FCode.equalsIgnoreCase("NameEn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NameEn));
		}
		if (FCode.equalsIgnoreCase("LastNamePY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastNamePY));
		}
		if (FCode.equalsIgnoreCase("FirstNamePY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstNamePY));
		}
		if (FCode.equalsIgnoreCase("Language"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Language));
		}
		if (FCode.equalsIgnoreCase("IDStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDStartDate()));
		}
		if (FCode.equalsIgnoreCase("IDEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDEndDate()));
		}
		if (FCode.equalsIgnoreCase("WorkCompName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkCompName));
		}
		if (FCode.equalsIgnoreCase("WorkIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkIDNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
		}
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistFlag));
		}
		if (FCode.equalsIgnoreCase("VIPValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPValue));
		}
		if (FCode.equalsIgnoreCase("SendMsgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendMsgFlag));
		}
		if (FCode.equalsIgnoreCase("SendMailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendMailFlag));
		}
		if (FCode.equalsIgnoreCase("SendQQFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendQQFlag));
		}
		if (FCode.equalsIgnoreCase("SendMSNFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendMSNFlag));
		}
		if (FCode.equalsIgnoreCase("WechatFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WechatFlag));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AppntSex);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AppntType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 18:
				strFieldValue = String.valueOf(Stature);
				break;
			case 19:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 28:
				strFieldValue = String.valueOf(Salary);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 40:
				strFieldValue = String.valueOf(BMI);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlag);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDExpDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(LastName);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(FirstName);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(LastNameEn);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(FirstNameEn);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(NameEn);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(LastNamePY);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(FirstNamePY);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDStartDate()));
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDEndDate()));
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(WorkCompName);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(WorkIDNo);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(VIPValue);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(SendMsgFlag);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(SendMailFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(SendQQFlag);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(SendMSNFlag);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(WechatFlag);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 69:
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntGrade = FValue.trim();
			}
			else
				AppntGrade = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
		}
		if (FCode.equalsIgnoreCase("AppntSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntSex = FValue.trim();
			}
			else
				AppntSex = null;
		}
		if (FCode.equalsIgnoreCase("AppntBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppntBirthday = fDate.getDate( FValue );
			}
			else
				AppntBirthday = null;
		}
		if (FCode.equalsIgnoreCase("AppntType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntType = FValue.trim();
			}
			else
				AppntType = null;
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
		if (FCode.equalsIgnoreCase("NativePlace"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NativePlace = FValue.trim();
			}
			else
				NativePlace = null;
		}
		if (FCode.equalsIgnoreCase("Nationality"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Nationality = FValue.trim();
			}
			else
				Nationality = null;
		}
		if (FCode.equalsIgnoreCase("RgtAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtAddress = FValue.trim();
			}
			else
				RgtAddress = null;
		}
		if (FCode.equalsIgnoreCase("Marriage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Marriage = FValue.trim();
			}
			else
				Marriage = null;
		}
		if (FCode.equalsIgnoreCase("MarriageDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MarriageDate = fDate.getDate( FValue );
			}
			else
				MarriageDate = null;
		}
		if (FCode.equalsIgnoreCase("Health"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Health = FValue.trim();
			}
			else
				Health = null;
		}
		if (FCode.equalsIgnoreCase("Stature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Stature = d;
			}
		}
		if (FCode.equalsIgnoreCase("Avoirdupois"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Avoirdupois = d;
			}
		}
		if (FCode.equalsIgnoreCase("Degree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Degree = FValue.trim();
			}
			else
				Degree = null;
		}
		if (FCode.equalsIgnoreCase("CreditGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreditGrade = FValue.trim();
			}
			else
				CreditGrade = null;
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
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("JoinCompanyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				JoinCompanyDate = fDate.getDate( FValue );
			}
			else
				JoinCompanyDate = null;
		}
		if (FCode.equalsIgnoreCase("StartWorkDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartWorkDate = fDate.getDate( FValue );
			}
			else
				StartWorkDate = null;
		}
		if (FCode.equalsIgnoreCase("Position"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Position = FValue.trim();
			}
			else
				Position = null;
		}
		if (FCode.equalsIgnoreCase("Salary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Salary = d;
			}
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
		}
		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationCode = FValue.trim();
			}
			else
				OccupationCode = null;
		}
		if (FCode.equalsIgnoreCase("WorkType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkType = FValue.trim();
			}
			else
				WorkType = null;
		}
		if (FCode.equalsIgnoreCase("PluralityType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PluralityType = FValue.trim();
			}
			else
				PluralityType = null;
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SmokeFlag = FValue.trim();
			}
			else
				SmokeFlag = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("BMI"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BMI = d;
			}
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
		if (FCode.equalsIgnoreCase("LicenseType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LicenseType = FValue.trim();
			}
			else
				LicenseType = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("SocialInsuFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuFlag = FValue.trim();
			}
			else
				SocialInsuFlag = null;
		}
		if (FCode.equalsIgnoreCase("IDExpDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDExpDate = fDate.getDate( FValue );
			}
			else
				IDExpDate = null;
		}
		if (FCode.equalsIgnoreCase("LastName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastName = FValue.trim();
			}
			else
				LastName = null;
		}
		if (FCode.equalsIgnoreCase("FirstName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstName = FValue.trim();
			}
			else
				FirstName = null;
		}
		if (FCode.equalsIgnoreCase("LastNameEn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastNameEn = FValue.trim();
			}
			else
				LastNameEn = null;
		}
		if (FCode.equalsIgnoreCase("FirstNameEn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstNameEn = FValue.trim();
			}
			else
				FirstNameEn = null;
		}
		if (FCode.equalsIgnoreCase("NameEn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NameEn = FValue.trim();
			}
			else
				NameEn = null;
		}
		if (FCode.equalsIgnoreCase("LastNamePY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastNamePY = FValue.trim();
			}
			else
				LastNamePY = null;
		}
		if (FCode.equalsIgnoreCase("FirstNamePY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstNamePY = FValue.trim();
			}
			else
				FirstNamePY = null;
		}
		if (FCode.equalsIgnoreCase("Language"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Language = FValue.trim();
			}
			else
				Language = null;
		}
		if (FCode.equalsIgnoreCase("IDStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDStartDate = fDate.getDate( FValue );
			}
			else
				IDStartDate = null;
		}
		if (FCode.equalsIgnoreCase("IDEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDEndDate = fDate.getDate( FValue );
			}
			else
				IDEndDate = null;
		}
		if (FCode.equalsIgnoreCase("WorkCompName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkCompName = FValue.trim();
			}
			else
				WorkCompName = null;
		}
		if (FCode.equalsIgnoreCase("WorkIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkIDNo = FValue.trim();
			}
			else
				WorkIDNo = null;
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
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeathDate = fDate.getDate( FValue );
			}
			else
				DeathDate = null;
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
		if (FCode.equalsIgnoreCase("SendMsgFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendMsgFlag = FValue.trim();
			}
			else
				SendMsgFlag = null;
		}
		if (FCode.equalsIgnoreCase("SendMailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendMailFlag = FValue.trim();
			}
			else
				SendMailFlag = null;
		}
		if (FCode.equalsIgnoreCase("SendQQFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendQQFlag = FValue.trim();
			}
			else
				SendQQFlag = null;
		}
		if (FCode.equalsIgnoreCase("SendMSNFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendMSNFlag = FValue.trim();
			}
			else
				SendMSNFlag = null;
		}
		if (FCode.equalsIgnoreCase("WechatFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WechatFlag = FValue.trim();
			}
			else
				WechatFlag = null;
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
		LCAppntSchema other = (LCAppntSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntGrade.equals(other.getAppntGrade())
			&& AppntName.equals(other.getAppntName())
			&& AppntSex.equals(other.getAppntSex())
			&& fDate.getString(AppntBirthday).equals(other.getAppntBirthday())
			&& AppntType.equals(other.getAppntType())
			&& AddressNo.equals(other.getAddressNo())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& NativePlace.equals(other.getNativePlace())
			&& Nationality.equals(other.getNationality())
			&& RgtAddress.equals(other.getRgtAddress())
			&& Marriage.equals(other.getMarriage())
			&& fDate.getString(MarriageDate).equals(other.getMarriageDate())
			&& Health.equals(other.getHealth())
			&& Stature == other.getStature()
			&& Avoirdupois == other.getAvoirdupois()
			&& Degree.equals(other.getDegree())
			&& CreditGrade.equals(other.getCreditGrade())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& fDate.getString(JoinCompanyDate).equals(other.getJoinCompanyDate())
			&& fDate.getString(StartWorkDate).equals(other.getStartWorkDate())
			&& Position.equals(other.getPosition())
			&& Salary == other.getSalary()
			&& OccupationType.equals(other.getOccupationType())
			&& OccupationCode.equals(other.getOccupationCode())
			&& WorkType.equals(other.getWorkType())
			&& PluralityType.equals(other.getPluralityType())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BMI == other.getBMI()
			&& License.equals(other.getLicense())
			&& LicenseType.equals(other.getLicenseType())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& SocialInsuFlag.equals(other.getSocialInsuFlag())
			&& fDate.getString(IDExpDate).equals(other.getIDExpDate())
			&& LastName.equals(other.getLastName())
			&& FirstName.equals(other.getFirstName())
			&& LastNameEn.equals(other.getLastNameEn())
			&& FirstNameEn.equals(other.getFirstNameEn())
			&& NameEn.equals(other.getNameEn())
			&& LastNamePY.equals(other.getLastNamePY())
			&& FirstNamePY.equals(other.getFirstNamePY())
			&& Language.equals(other.getLanguage())
			&& fDate.getString(IDStartDate).equals(other.getIDStartDate())
			&& fDate.getString(IDEndDate).equals(other.getIDEndDate())
			&& WorkCompName.equals(other.getWorkCompName())
			&& WorkIDNo.equals(other.getWorkIDNo())
			&& State.equals(other.getState())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
			&& VIPValue.equals(other.getVIPValue())
			&& SendMsgFlag.equals(other.getSendMsgFlag())
			&& SendMailFlag.equals(other.getSendMailFlag())
			&& SendQQFlag.equals(other.getSendQQFlag())
			&& SendMSNFlag.equals(other.getSendMSNFlag())
			&& WechatFlag.equals(other.getWechatFlag())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 2;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 3;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return 4;
		}
		if( strFieldName.equals("AppntName") ) {
			return 5;
		}
		if( strFieldName.equals("AppntSex") ) {
			return 6;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return 7;
		}
		if( strFieldName.equals("AppntType") ) {
			return 8;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 9;
		}
		if( strFieldName.equals("IDType") ) {
			return 10;
		}
		if( strFieldName.equals("IDNo") ) {
			return 11;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 12;
		}
		if( strFieldName.equals("Nationality") ) {
			return 13;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 14;
		}
		if( strFieldName.equals("Marriage") ) {
			return 15;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 16;
		}
		if( strFieldName.equals("Health") ) {
			return 17;
		}
		if( strFieldName.equals("Stature") ) {
			return 18;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 19;
		}
		if( strFieldName.equals("Degree") ) {
			return 20;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 21;
		}
		if( strFieldName.equals("BankCode") ) {
			return 22;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 23;
		}
		if( strFieldName.equals("AccName") ) {
			return 24;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 25;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 26;
		}
		if( strFieldName.equals("Position") ) {
			return 27;
		}
		if( strFieldName.equals("Salary") ) {
			return 28;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 29;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 30;
		}
		if( strFieldName.equals("WorkType") ) {
			return 31;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 32;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 33;
		}
		if( strFieldName.equals("Operator") ) {
			return 34;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 39;
		}
		if( strFieldName.equals("BMI") ) {
			return 40;
		}
		if( strFieldName.equals("License") ) {
			return 41;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 42;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 43;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return 44;
		}
		if( strFieldName.equals("IDExpDate") ) {
			return 45;
		}
		if( strFieldName.equals("LastName") ) {
			return 46;
		}
		if( strFieldName.equals("FirstName") ) {
			return 47;
		}
		if( strFieldName.equals("LastNameEn") ) {
			return 48;
		}
		if( strFieldName.equals("FirstNameEn") ) {
			return 49;
		}
		if( strFieldName.equals("NameEn") ) {
			return 50;
		}
		if( strFieldName.equals("LastNamePY") ) {
			return 51;
		}
		if( strFieldName.equals("FirstNamePY") ) {
			return 52;
		}
		if( strFieldName.equals("Language") ) {
			return 53;
		}
		if( strFieldName.equals("IDStartDate") ) {
			return 54;
		}
		if( strFieldName.equals("IDEndDate") ) {
			return 55;
		}
		if( strFieldName.equals("WorkCompName") ) {
			return 56;
		}
		if( strFieldName.equals("WorkIDNo") ) {
			return 57;
		}
		if( strFieldName.equals("State") ) {
			return 58;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 59;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 60;
		}
		if( strFieldName.equals("VIPValue") ) {
			return 61;
		}
		if( strFieldName.equals("SendMsgFlag") ) {
			return 62;
		}
		if( strFieldName.equals("SendMailFlag") ) {
			return 63;
		}
		if( strFieldName.equals("SendQQFlag") ) {
			return 64;
		}
		if( strFieldName.equals("SendMSNFlag") ) {
			return 65;
		}
		if( strFieldName.equals("WechatFlag") ) {
			return 66;
		}
		if( strFieldName.equals("Remark") ) {
			return 67;
		}
		if( strFieldName.equals("ComCode") ) {
			return 68;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 69;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "PrtNo";
				break;
			case 3:
				strFieldName = "AppntNo";
				break;
			case 4:
				strFieldName = "AppntGrade";
				break;
			case 5:
				strFieldName = "AppntName";
				break;
			case 6:
				strFieldName = "AppntSex";
				break;
			case 7:
				strFieldName = "AppntBirthday";
				break;
			case 8:
				strFieldName = "AppntType";
				break;
			case 9:
				strFieldName = "AddressNo";
				break;
			case 10:
				strFieldName = "IDType";
				break;
			case 11:
				strFieldName = "IDNo";
				break;
			case 12:
				strFieldName = "NativePlace";
				break;
			case 13:
				strFieldName = "Nationality";
				break;
			case 14:
				strFieldName = "RgtAddress";
				break;
			case 15:
				strFieldName = "Marriage";
				break;
			case 16:
				strFieldName = "MarriageDate";
				break;
			case 17:
				strFieldName = "Health";
				break;
			case 18:
				strFieldName = "Stature";
				break;
			case 19:
				strFieldName = "Avoirdupois";
				break;
			case 20:
				strFieldName = "Degree";
				break;
			case 21:
				strFieldName = "CreditGrade";
				break;
			case 22:
				strFieldName = "BankCode";
				break;
			case 23:
				strFieldName = "BankAccNo";
				break;
			case 24:
				strFieldName = "AccName";
				break;
			case 25:
				strFieldName = "JoinCompanyDate";
				break;
			case 26:
				strFieldName = "StartWorkDate";
				break;
			case 27:
				strFieldName = "Position";
				break;
			case 28:
				strFieldName = "Salary";
				break;
			case 29:
				strFieldName = "OccupationType";
				break;
			case 30:
				strFieldName = "OccupationCode";
				break;
			case 31:
				strFieldName = "WorkType";
				break;
			case 32:
				strFieldName = "PluralityType";
				break;
			case 33:
				strFieldName = "SmokeFlag";
				break;
			case 34:
				strFieldName = "Operator";
				break;
			case 35:
				strFieldName = "ManageCom";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyDate";
				break;
			case 39:
				strFieldName = "ModifyTime";
				break;
			case 40:
				strFieldName = "BMI";
				break;
			case 41:
				strFieldName = "License";
				break;
			case 42:
				strFieldName = "LicenseType";
				break;
			case 43:
				strFieldName = "RelationToInsured";
				break;
			case 44:
				strFieldName = "SocialInsuFlag";
				break;
			case 45:
				strFieldName = "IDExpDate";
				break;
			case 46:
				strFieldName = "LastName";
				break;
			case 47:
				strFieldName = "FirstName";
				break;
			case 48:
				strFieldName = "LastNameEn";
				break;
			case 49:
				strFieldName = "FirstNameEn";
				break;
			case 50:
				strFieldName = "NameEn";
				break;
			case 51:
				strFieldName = "LastNamePY";
				break;
			case 52:
				strFieldName = "FirstNamePY";
				break;
			case 53:
				strFieldName = "Language";
				break;
			case 54:
				strFieldName = "IDStartDate";
				break;
			case 55:
				strFieldName = "IDEndDate";
				break;
			case 56:
				strFieldName = "WorkCompName";
				break;
			case 57:
				strFieldName = "WorkIDNo";
				break;
			case 58:
				strFieldName = "State";
				break;
			case 59:
				strFieldName = "DeathDate";
				break;
			case 60:
				strFieldName = "BlacklistFlag";
				break;
			case 61:
				strFieldName = "VIPValue";
				break;
			case 62:
				strFieldName = "SendMsgFlag";
				break;
			case 63:
				strFieldName = "SendMailFlag";
				break;
			case 64:
				strFieldName = "SendQQFlag";
				break;
			case 65:
				strFieldName = "SendMSNFlag";
				break;
			case 66:
				strFieldName = "WechatFlag";
				break;
			case 67:
				strFieldName = "Remark";
				break;
			case 68:
				strFieldName = "ComCode";
				break;
			case 69:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NativePlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Nationality") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Marriage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Health") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Stature") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Degree") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Position") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Salary") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PluralityType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("BMI") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDExpDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastNameEn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstNameEn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NameEn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastNamePY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstNamePY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Language") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("WorkCompName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeathDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VIPValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendMsgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendMailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendQQFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendMSNFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WechatFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
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
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
