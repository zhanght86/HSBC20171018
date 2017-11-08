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
import com.sinosoft.lis.db.LDPersonDB;

/*
 * <p>ClassName: LDPersonSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LDPersonSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPersonSchema.class);
	// @Field
	/** 客户号码 */
	private String CustomerNo;
	/** 客户姓名 */
	private String Name;
	/** 客户性别 */
	private String Sex;
	/** 客户出生日期 */
	private Date Birthday;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 密码 */
	private String Password;
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
	/** 其它证件类型 */
	private String OthIDType;
	/** 其它证件号码 */
	private String OthIDNo;
	/** Ic卡号 */
	private String ICNo;
	/** 单位编码 */
	private String GrpNo;
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
	/** 死亡日期 */
	private Date DeathDate;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 黑名单标记 */
	private String BlacklistFlag;
	/** 属性 */
	private String Proterty;
	/** 备注 */
	private String Remark;
	/** 状态 */
	private String State;
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
	/** 单位名称 */
	private String GrpName;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;
	/** 社保登记号 */
	private String SocialInsuNo;
	/** 原因类别 */
	private String ReasonType;
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
	/** 证件是否长期 */
	private String IDValFlag;
	/** 证件有效起期 */
	private Date IDInitiateDate;
	/** 家族病史 */
	private String FamilyDisease;
	/** 身体指标 */
	private double BMI;
	/** 工龄 */
	private int Seniority;
	/** 工作单位 */
	private String WorkCompName;
	/** 工作地 */
	private String WorkAddress;
	/** 社保地 */
	private String SocialInsuAddress;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 67;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPersonSchema()
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
		LDPersonSchema cloned = (LDPersonSchema)super.clone();
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
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>120)
			throw new IllegalArgumentException("客户姓名Name值"+aName+"的长度"+aName.length()+"大于最大值120");
		Name = aName;
	}
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		if(aSex!=null && aSex.length()>1)
			throw new IllegalArgumentException("客户性别Sex值"+aSex+"的长度"+aSex.length()+"大于最大值1");
		Sex = aSex;
	}
	public String getBirthday()
	{
		if( Birthday != null )
			return fDate.getString(Birthday);
		else
			return null;
	}
	public void setBirthday(Date aBirthday)
	{
		Birthday = aBirthday;
	}
	public void setBirthday(String aBirthday)
	{
		if (aBirthday != null && !aBirthday.equals("") )
		{
			Birthday = fDate.getDate( aBirthday );
		}
		else
			Birthday = null;
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
	public String getOthIDType()
	{
		return OthIDType;
	}
	public void setOthIDType(String aOthIDType)
	{
		if(aOthIDType!=null && aOthIDType.length()>20)
			throw new IllegalArgumentException("其它证件类型OthIDType值"+aOthIDType+"的长度"+aOthIDType.length()+"大于最大值20");
		OthIDType = aOthIDType;
	}
	public String getOthIDNo()
	{
		return OthIDNo;
	}
	public void setOthIDNo(String aOthIDNo)
	{
		if(aOthIDNo!=null && aOthIDNo.length()>20)
			throw new IllegalArgumentException("其它证件号码OthIDNo值"+aOthIDNo+"的长度"+aOthIDNo.length()+"大于最大值20");
		OthIDNo = aOthIDNo;
	}
	public String getICNo()
	{
		return ICNo;
	}
	public void setICNo(String aICNo)
	{
		if(aICNo!=null && aICNo.length()>20)
			throw new IllegalArgumentException("Ic卡号ICNo值"+aICNo+"的长度"+aICNo.length()+"大于最大值20");
		ICNo = aICNo;
	}
	public String getGrpNo()
	{
		return GrpNo;
	}
	public void setGrpNo(String aGrpNo)
	{
		if(aGrpNo!=null && aGrpNo.length()>20)
			throw new IllegalArgumentException("单位编码GrpNo值"+aGrpNo+"的长度"+aGrpNo.length()+"大于最大值20");
		GrpNo = aGrpNo;
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
		if(aWorkType!=null && aWorkType.length()>40)
			throw new IllegalArgumentException("职业（工种）WorkType值"+aWorkType+"的长度"+aWorkType.length()+"大于最大值40");
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
	public String getProterty()
	{
		return Proterty;
	}
	public void setProterty(String aProterty)
	{
		if(aProterty!=null && aProterty.length()>2)
			throw new IllegalArgumentException("属性Proterty值"+aProterty+"的长度"+aProterty.length()+"大于最大值2");
		Proterty = aProterty;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>80)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值80");
		Remark = aRemark;
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
	public String getSocialInsuNo()
	{
		return SocialInsuNo;
	}
	public void setSocialInsuNo(String aSocialInsuNo)
	{
		if(aSocialInsuNo!=null && aSocialInsuNo.length()>20)
			throw new IllegalArgumentException("社保登记号SocialInsuNo值"+aSocialInsuNo+"的长度"+aSocialInsuNo.length()+"大于最大值20");
		SocialInsuNo = aSocialInsuNo;
	}
	public String getReasonType()
	{
		return ReasonType;
	}
	public void setReasonType(String aReasonType)
	{
		if(aReasonType!=null && aReasonType.length()>2)
			throw new IllegalArgumentException("原因类别ReasonType值"+aReasonType+"的长度"+aReasonType.length()+"大于最大值2");
		ReasonType = aReasonType;
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
	public String getIDValFlag()
	{
		return IDValFlag;
	}
	public void setIDValFlag(String aIDValFlag)
	{
		if(aIDValFlag!=null && aIDValFlag.length()>2)
			throw new IllegalArgumentException("证件是否长期IDValFlag值"+aIDValFlag+"的长度"+aIDValFlag.length()+"大于最大值2");
		IDValFlag = aIDValFlag;
	}
	public String getIDInitiateDate()
	{
		if( IDInitiateDate != null )
			return fDate.getString(IDInitiateDate);
		else
			return null;
	}
	public void setIDInitiateDate(Date aIDInitiateDate)
	{
		IDInitiateDate = aIDInitiateDate;
	}
	public void setIDInitiateDate(String aIDInitiateDate)
	{
		if (aIDInitiateDate != null && !aIDInitiateDate.equals("") )
		{
			IDInitiateDate = fDate.getDate( aIDInitiateDate );
		}
		else
			IDInitiateDate = null;
	}

	public String getFamilyDisease()
	{
		return FamilyDisease;
	}
	public void setFamilyDisease(String aFamilyDisease)
	{
		if(aFamilyDisease!=null && aFamilyDisease.length()>200)
			throw new IllegalArgumentException("家族病史FamilyDisease值"+aFamilyDisease+"的长度"+aFamilyDisease.length()+"大于最大值200");
		FamilyDisease = aFamilyDisease;
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

	public int getSeniority()
	{
		return Seniority;
	}
	public void setSeniority(int aSeniority)
	{
		Seniority = aSeniority;
	}
	public void setSeniority(String aSeniority)
	{
		if (aSeniority != null && !aSeniority.equals(""))
		{
			Integer tInteger = new Integer(aSeniority);
			int i = tInteger.intValue();
			Seniority = i;
		}
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
	public String getWorkAddress()
	{
		return WorkAddress;
	}
	public void setWorkAddress(String aWorkAddress)
	{
		if(aWorkAddress!=null && aWorkAddress.length()>200)
			throw new IllegalArgumentException("工作地WorkAddress值"+aWorkAddress+"的长度"+aWorkAddress.length()+"大于最大值200");
		WorkAddress = aWorkAddress;
	}
	public String getSocialInsuAddress()
	{
		return SocialInsuAddress;
	}
	public void setSocialInsuAddress(String aSocialInsuAddress)
	{
		if(aSocialInsuAddress!=null && aSocialInsuAddress.length()>200)
			throw new IllegalArgumentException("社保地SocialInsuAddress值"+aSocialInsuAddress+"的长度"+aSocialInsuAddress.length()+"大于最大值200");
		SocialInsuAddress = aSocialInsuAddress;
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
	* 使用另外一个 LDPersonSchema 对象给 Schema 赋值
	* @param: aLDPersonSchema LDPersonSchema
	**/
	public void setSchema(LDPersonSchema aLDPersonSchema)
	{
		this.CustomerNo = aLDPersonSchema.getCustomerNo();
		this.Name = aLDPersonSchema.getName();
		this.Sex = aLDPersonSchema.getSex();
		this.Birthday = fDate.getDate( aLDPersonSchema.getBirthday());
		this.IDType = aLDPersonSchema.getIDType();
		this.IDNo = aLDPersonSchema.getIDNo();
		this.Password = aLDPersonSchema.getPassword();
		this.NativePlace = aLDPersonSchema.getNativePlace();
		this.Nationality = aLDPersonSchema.getNationality();
		this.RgtAddress = aLDPersonSchema.getRgtAddress();
		this.Marriage = aLDPersonSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLDPersonSchema.getMarriageDate());
		this.Health = aLDPersonSchema.getHealth();
		this.Stature = aLDPersonSchema.getStature();
		this.Avoirdupois = aLDPersonSchema.getAvoirdupois();
		this.Degree = aLDPersonSchema.getDegree();
		this.CreditGrade = aLDPersonSchema.getCreditGrade();
		this.OthIDType = aLDPersonSchema.getOthIDType();
		this.OthIDNo = aLDPersonSchema.getOthIDNo();
		this.ICNo = aLDPersonSchema.getICNo();
		this.GrpNo = aLDPersonSchema.getGrpNo();
		this.JoinCompanyDate = fDate.getDate( aLDPersonSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLDPersonSchema.getStartWorkDate());
		this.Position = aLDPersonSchema.getPosition();
		this.Salary = aLDPersonSchema.getSalary();
		this.OccupationType = aLDPersonSchema.getOccupationType();
		this.OccupationCode = aLDPersonSchema.getOccupationCode();
		this.WorkType = aLDPersonSchema.getWorkType();
		this.PluralityType = aLDPersonSchema.getPluralityType();
		this.DeathDate = fDate.getDate( aLDPersonSchema.getDeathDate());
		this.SmokeFlag = aLDPersonSchema.getSmokeFlag();
		this.BlacklistFlag = aLDPersonSchema.getBlacklistFlag();
		this.Proterty = aLDPersonSchema.getProterty();
		this.Remark = aLDPersonSchema.getRemark();
		this.State = aLDPersonSchema.getState();
		this.VIPValue = aLDPersonSchema.getVIPValue();
		this.Operator = aLDPersonSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDPersonSchema.getMakeDate());
		this.MakeTime = aLDPersonSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDPersonSchema.getModifyDate());
		this.ModifyTime = aLDPersonSchema.getModifyTime();
		this.GrpName = aLDPersonSchema.getGrpName();
		this.License = aLDPersonSchema.getLicense();
		this.LicenseType = aLDPersonSchema.getLicenseType();
		this.SocialInsuNo = aLDPersonSchema.getSocialInsuNo();
		this.ReasonType = aLDPersonSchema.getReasonType();
		this.SocialInsuFlag = aLDPersonSchema.getSocialInsuFlag();
		this.IDExpDate = fDate.getDate( aLDPersonSchema.getIDExpDate());
		this.LastName = aLDPersonSchema.getLastName();
		this.FirstName = aLDPersonSchema.getFirstName();
		this.LastNameEn = aLDPersonSchema.getLastNameEn();
		this.FirstNameEn = aLDPersonSchema.getFirstNameEn();
		this.NameEn = aLDPersonSchema.getNameEn();
		this.LastNamePY = aLDPersonSchema.getLastNamePY();
		this.FirstNamePY = aLDPersonSchema.getFirstNamePY();
		this.Language = aLDPersonSchema.getLanguage();
		this.IDValFlag = aLDPersonSchema.getIDValFlag();
		this.IDInitiateDate = fDate.getDate( aLDPersonSchema.getIDInitiateDate());
		this.FamilyDisease = aLDPersonSchema.getFamilyDisease();
		this.BMI = aLDPersonSchema.getBMI();
		this.Seniority = aLDPersonSchema.getSeniority();
		this.WorkCompName = aLDPersonSchema.getWorkCompName();
		this.WorkAddress = aLDPersonSchema.getWorkAddress();
		this.SocialInsuAddress = aLDPersonSchema.getSocialInsuAddress();
		this.ManageCom = aLDPersonSchema.getManageCom();
		this.ComCode = aLDPersonSchema.getComCode();
		this.ModifyOperator = aLDPersonSchema.getModifyOperator();
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

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			this.Birthday = rs.getDate("Birthday");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

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

			if( rs.getString("OthIDType") == null )
				this.OthIDType = null;
			else
				this.OthIDType = rs.getString("OthIDType").trim();

			if( rs.getString("OthIDNo") == null )
				this.OthIDNo = null;
			else
				this.OthIDNo = rs.getString("OthIDNo").trim();

			if( rs.getString("ICNo") == null )
				this.ICNo = null;
			else
				this.ICNo = rs.getString("ICNo").trim();

			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

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

			this.DeathDate = rs.getDate("DeathDate");
			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

			if( rs.getString("Proterty") == null )
				this.Proterty = null;
			else
				this.Proterty = rs.getString("Proterty").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("LicenseType") == null )
				this.LicenseType = null;
			else
				this.LicenseType = rs.getString("LicenseType").trim();

			if( rs.getString("SocialInsuNo") == null )
				this.SocialInsuNo = null;
			else
				this.SocialInsuNo = rs.getString("SocialInsuNo").trim();

			if( rs.getString("ReasonType") == null )
				this.ReasonType = null;
			else
				this.ReasonType = rs.getString("ReasonType").trim();

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

			if( rs.getString("IDValFlag") == null )
				this.IDValFlag = null;
			else
				this.IDValFlag = rs.getString("IDValFlag").trim();

			this.IDInitiateDate = rs.getDate("IDInitiateDate");
			if( rs.getString("FamilyDisease") == null )
				this.FamilyDisease = null;
			else
				this.FamilyDisease = rs.getString("FamilyDisease").trim();

			this.BMI = rs.getDouble("BMI");
			this.Seniority = rs.getInt("Seniority");
			if( rs.getString("WorkCompName") == null )
				this.WorkCompName = null;
			else
				this.WorkCompName = rs.getString("WorkCompName").trim();

			if( rs.getString("WorkAddress") == null )
				this.WorkAddress = null;
			else
				this.WorkAddress = rs.getString("WorkAddress").trim();

			if( rs.getString("SocialInsuAddress") == null )
				this.SocialInsuAddress = null;
			else
				this.SocialInsuAddress = rs.getString("SocialInsuAddress").trim();

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
			logger.debug("数据库中的LDPerson表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPersonSchema getSchema()
	{
		LDPersonSchema aLDPersonSchema = new LDPersonSchema();
		aLDPersonSchema.setSchema(this);
		return aLDPersonSchema;
	}

	public LDPersonDB getDB()
	{
		LDPersonDB aDBOper = new LDPersonDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPerson描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(OthIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( JoinCompanyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartWorkDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Position)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Salary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PluralityType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeathDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Proterty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LicenseType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonType)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(IDValFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDInitiateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyDisease)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BMI));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Seniority));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkCompName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPerson>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			OthIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			OthIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ICNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Proterty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			VIPValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			SocialInsuNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			ReasonType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			SocialInsuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			IDExpDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			LastName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			FirstName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			LastNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			FirstNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			NameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			LastNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			FirstNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			Language = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			IDValFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			IDInitiateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			FamilyDisease = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			BMI = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			Seniority= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).intValue();
			WorkCompName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			WorkAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			SocialInsuAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonSchema";
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
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
		if (FCode.equalsIgnoreCase("OthIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthIDType));
		}
		if (FCode.equalsIgnoreCase("OthIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthIDNo));
		}
		if (FCode.equalsIgnoreCase("ICNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICNo));
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
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
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistFlag));
		}
		if (FCode.equalsIgnoreCase("Proterty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Proterty));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("License"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(License));
		}
		if (FCode.equalsIgnoreCase("LicenseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LicenseType));
		}
		if (FCode.equalsIgnoreCase("SocialInsuNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuNo));
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonType));
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
		if (FCode.equalsIgnoreCase("IDValFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDValFlag));
		}
		if (FCode.equalsIgnoreCase("IDInitiateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDInitiateDate()));
		}
		if (FCode.equalsIgnoreCase("FamilyDisease"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyDisease));
		}
		if (FCode.equalsIgnoreCase("BMI"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BMI));
		}
		if (FCode.equalsIgnoreCase("Seniority"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Seniority));
		}
		if (FCode.equalsIgnoreCase("WorkCompName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkCompName));
		}
		if (FCode.equalsIgnoreCase("WorkAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkAddress));
		}
		if (FCode.equalsIgnoreCase("SocialInsuAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuAddress));
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
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 13:
				strFieldValue = String.valueOf(Stature);
				break;
			case 14:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(OthIDType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(OthIDNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ICNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 24:
				strFieldValue = String.valueOf(Salary);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Proterty);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(VIPValue);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuNo);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ReasonType);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDExpDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(LastName);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(FirstName);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(LastNameEn);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(FirstNameEn);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(NameEn);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(LastNamePY);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(FirstNamePY);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(IDValFlag);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDInitiateDate()));
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(FamilyDisease);
				break;
			case 59:
				strFieldValue = String.valueOf(BMI);
				break;
			case 60:
				strFieldValue = String.valueOf(Seniority);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(WorkCompName);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(WorkAddress);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuAddress);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 66:
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Birthday = fDate.getDate( FValue );
			}
			else
				Birthday = null;
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
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equalsIgnoreCase("OthIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthIDType = FValue.trim();
			}
			else
				OthIDType = null;
		}
		if (FCode.equalsIgnoreCase("OthIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthIDNo = FValue.trim();
			}
			else
				OthIDNo = null;
		}
		if (FCode.equalsIgnoreCase("ICNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICNo = FValue.trim();
			}
			else
				ICNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNo = FValue.trim();
			}
			else
				GrpNo = null;
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
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DeathDate = fDate.getDate( FValue );
			}
			else
				DeathDate = null;
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
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlacklistFlag = FValue.trim();
			}
			else
				BlacklistFlag = null;
		}
		if (FCode.equalsIgnoreCase("Proterty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Proterty = FValue.trim();
			}
			else
				Proterty = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
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
		if (FCode.equalsIgnoreCase("SocialInsuNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuNo = FValue.trim();
			}
			else
				SocialInsuNo = null;
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonType = FValue.trim();
			}
			else
				ReasonType = null;
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
		if (FCode.equalsIgnoreCase("IDValFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDValFlag = FValue.trim();
			}
			else
				IDValFlag = null;
		}
		if (FCode.equalsIgnoreCase("IDInitiateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				IDInitiateDate = fDate.getDate( FValue );
			}
			else
				IDInitiateDate = null;
		}
		if (FCode.equalsIgnoreCase("FamilyDisease"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyDisease = FValue.trim();
			}
			else
				FamilyDisease = null;
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
		if (FCode.equalsIgnoreCase("Seniority"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Seniority = i;
			}
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
		if (FCode.equalsIgnoreCase("WorkAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkAddress = FValue.trim();
			}
			else
				WorkAddress = null;
		}
		if (FCode.equalsIgnoreCase("SocialInsuAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuAddress = FValue.trim();
			}
			else
				SocialInsuAddress = null;
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
		LDPersonSchema other = (LDPersonSchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& Password.equals(other.getPassword())
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
			&& OthIDType.equals(other.getOthIDType())
			&& OthIDNo.equals(other.getOthIDNo())
			&& ICNo.equals(other.getICNo())
			&& GrpNo.equals(other.getGrpNo())
			&& fDate.getString(JoinCompanyDate).equals(other.getJoinCompanyDate())
			&& fDate.getString(StartWorkDate).equals(other.getStartWorkDate())
			&& Position.equals(other.getPosition())
			&& Salary == other.getSalary()
			&& OccupationType.equals(other.getOccupationType())
			&& OccupationCode.equals(other.getOccupationCode())
			&& WorkType.equals(other.getWorkType())
			&& PluralityType.equals(other.getPluralityType())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
			&& Proterty.equals(other.getProterty())
			&& Remark.equals(other.getRemark())
			&& State.equals(other.getState())
			&& VIPValue.equals(other.getVIPValue())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& GrpName.equals(other.getGrpName())
			&& License.equals(other.getLicense())
			&& LicenseType.equals(other.getLicenseType())
			&& SocialInsuNo.equals(other.getSocialInsuNo())
			&& ReasonType.equals(other.getReasonType())
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
			&& IDValFlag.equals(other.getIDValFlag())
			&& fDate.getString(IDInitiateDate).equals(other.getIDInitiateDate())
			&& FamilyDisease.equals(other.getFamilyDisease())
			&& BMI == other.getBMI()
			&& Seniority == other.getSeniority()
			&& WorkCompName.equals(other.getWorkCompName())
			&& WorkAddress.equals(other.getWorkAddress())
			&& SocialInsuAddress.equals(other.getSocialInsuAddress())
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
		if( strFieldName.equals("Name") ) {
			return 1;
		}
		if( strFieldName.equals("Sex") ) {
			return 2;
		}
		if( strFieldName.equals("Birthday") ) {
			return 3;
		}
		if( strFieldName.equals("IDType") ) {
			return 4;
		}
		if( strFieldName.equals("IDNo") ) {
			return 5;
		}
		if( strFieldName.equals("Password") ) {
			return 6;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 7;
		}
		if( strFieldName.equals("Nationality") ) {
			return 8;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 9;
		}
		if( strFieldName.equals("Marriage") ) {
			return 10;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 11;
		}
		if( strFieldName.equals("Health") ) {
			return 12;
		}
		if( strFieldName.equals("Stature") ) {
			return 13;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 14;
		}
		if( strFieldName.equals("Degree") ) {
			return 15;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 16;
		}
		if( strFieldName.equals("OthIDType") ) {
			return 17;
		}
		if( strFieldName.equals("OthIDNo") ) {
			return 18;
		}
		if( strFieldName.equals("ICNo") ) {
			return 19;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 20;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 21;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 22;
		}
		if( strFieldName.equals("Position") ) {
			return 23;
		}
		if( strFieldName.equals("Salary") ) {
			return 24;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 25;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 26;
		}
		if( strFieldName.equals("WorkType") ) {
			return 27;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 28;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 29;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 30;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 31;
		}
		if( strFieldName.equals("Proterty") ) {
			return 32;
		}
		if( strFieldName.equals("Remark") ) {
			return 33;
		}
		if( strFieldName.equals("State") ) {
			return 34;
		}
		if( strFieldName.equals("VIPValue") ) {
			return 35;
		}
		if( strFieldName.equals("Operator") ) {
			return 36;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 37;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 39;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 40;
		}
		if( strFieldName.equals("GrpName") ) {
			return 41;
		}
		if( strFieldName.equals("License") ) {
			return 42;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 43;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return 44;
		}
		if( strFieldName.equals("ReasonType") ) {
			return 45;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return 46;
		}
		if( strFieldName.equals("IDExpDate") ) {
			return 47;
		}
		if( strFieldName.equals("LastName") ) {
			return 48;
		}
		if( strFieldName.equals("FirstName") ) {
			return 49;
		}
		if( strFieldName.equals("LastNameEn") ) {
			return 50;
		}
		if( strFieldName.equals("FirstNameEn") ) {
			return 51;
		}
		if( strFieldName.equals("NameEn") ) {
			return 52;
		}
		if( strFieldName.equals("LastNamePY") ) {
			return 53;
		}
		if( strFieldName.equals("FirstNamePY") ) {
			return 54;
		}
		if( strFieldName.equals("Language") ) {
			return 55;
		}
		if( strFieldName.equals("IDValFlag") ) {
			return 56;
		}
		if( strFieldName.equals("IDInitiateDate") ) {
			return 57;
		}
		if( strFieldName.equals("FamilyDisease") ) {
			return 58;
		}
		if( strFieldName.equals("BMI") ) {
			return 59;
		}
		if( strFieldName.equals("Seniority") ) {
			return 60;
		}
		if( strFieldName.equals("WorkCompName") ) {
			return 61;
		}
		if( strFieldName.equals("WorkAddress") ) {
			return 62;
		}
		if( strFieldName.equals("SocialInsuAddress") ) {
			return 63;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 64;
		}
		if( strFieldName.equals("ComCode") ) {
			return 65;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "CustomerNo";
				break;
			case 1:
				strFieldName = "Name";
				break;
			case 2:
				strFieldName = "Sex";
				break;
			case 3:
				strFieldName = "Birthday";
				break;
			case 4:
				strFieldName = "IDType";
				break;
			case 5:
				strFieldName = "IDNo";
				break;
			case 6:
				strFieldName = "Password";
				break;
			case 7:
				strFieldName = "NativePlace";
				break;
			case 8:
				strFieldName = "Nationality";
				break;
			case 9:
				strFieldName = "RgtAddress";
				break;
			case 10:
				strFieldName = "Marriage";
				break;
			case 11:
				strFieldName = "MarriageDate";
				break;
			case 12:
				strFieldName = "Health";
				break;
			case 13:
				strFieldName = "Stature";
				break;
			case 14:
				strFieldName = "Avoirdupois";
				break;
			case 15:
				strFieldName = "Degree";
				break;
			case 16:
				strFieldName = "CreditGrade";
				break;
			case 17:
				strFieldName = "OthIDType";
				break;
			case 18:
				strFieldName = "OthIDNo";
				break;
			case 19:
				strFieldName = "ICNo";
				break;
			case 20:
				strFieldName = "GrpNo";
				break;
			case 21:
				strFieldName = "JoinCompanyDate";
				break;
			case 22:
				strFieldName = "StartWorkDate";
				break;
			case 23:
				strFieldName = "Position";
				break;
			case 24:
				strFieldName = "Salary";
				break;
			case 25:
				strFieldName = "OccupationType";
				break;
			case 26:
				strFieldName = "OccupationCode";
				break;
			case 27:
				strFieldName = "WorkType";
				break;
			case 28:
				strFieldName = "PluralityType";
				break;
			case 29:
				strFieldName = "DeathDate";
				break;
			case 30:
				strFieldName = "SmokeFlag";
				break;
			case 31:
				strFieldName = "BlacklistFlag";
				break;
			case 32:
				strFieldName = "Proterty";
				break;
			case 33:
				strFieldName = "Remark";
				break;
			case 34:
				strFieldName = "State";
				break;
			case 35:
				strFieldName = "VIPValue";
				break;
			case 36:
				strFieldName = "Operator";
				break;
			case 37:
				strFieldName = "MakeDate";
				break;
			case 38:
				strFieldName = "MakeTime";
				break;
			case 39:
				strFieldName = "ModifyDate";
				break;
			case 40:
				strFieldName = "ModifyTime";
				break;
			case 41:
				strFieldName = "GrpName";
				break;
			case 42:
				strFieldName = "License";
				break;
			case 43:
				strFieldName = "LicenseType";
				break;
			case 44:
				strFieldName = "SocialInsuNo";
				break;
			case 45:
				strFieldName = "ReasonType";
				break;
			case 46:
				strFieldName = "SocialInsuFlag";
				break;
			case 47:
				strFieldName = "IDExpDate";
				break;
			case 48:
				strFieldName = "LastName";
				break;
			case 49:
				strFieldName = "FirstName";
				break;
			case 50:
				strFieldName = "LastNameEn";
				break;
			case 51:
				strFieldName = "FirstNameEn";
				break;
			case 52:
				strFieldName = "NameEn";
				break;
			case 53:
				strFieldName = "LastNamePY";
				break;
			case 54:
				strFieldName = "FirstNamePY";
				break;
			case 55:
				strFieldName = "Language";
				break;
			case 56:
				strFieldName = "IDValFlag";
				break;
			case 57:
				strFieldName = "IDInitiateDate";
				break;
			case 58:
				strFieldName = "FamilyDisease";
				break;
			case 59:
				strFieldName = "BMI";
				break;
			case 60:
				strFieldName = "Seniority";
				break;
			case 61:
				strFieldName = "WorkCompName";
				break;
			case 62:
				strFieldName = "WorkAddress";
				break;
			case 63:
				strFieldName = "SocialInsuAddress";
				break;
			case 64:
				strFieldName = "ManageCom";
				break;
			case 65:
				strFieldName = "ComCode";
				break;
			case 66:
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
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
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
		if( strFieldName.equals("OthIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNo") ) {
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
		if( strFieldName.equals("DeathDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Proterty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonType") ) {
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
		if( strFieldName.equals("IDValFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDInitiateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FamilyDisease") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BMI") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Seniority") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WorkCompName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuAddress") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 60:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
