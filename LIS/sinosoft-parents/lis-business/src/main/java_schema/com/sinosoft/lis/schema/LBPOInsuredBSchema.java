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
import com.sinosoft.lis.db.LBPOInsuredBDB;

/*
 * <p>ClassName: LBPOInsuredBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_16
 */
public class LBPOInsuredBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOInsuredBSchema.class);
	// @Field
	/** 转储号 */
	private String IDX;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 录入次数 */
	private int InputNo;
	/** 添空号 */
	private String FillNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 管理机构 */
	private String ManageCom;
	/** 处理机构 */
	private String ExecuteCom;
	/** 家庭保障号 */
	private String FamilyID;
	/** 与主被保人关系 */
	private String RelationToMainInsured;
	/** 与投保人关系 */
	private String RelationToAppnt;
	/** 客户地址号码 */
	private String AddressNo;
	/** 客户内部号码 */
	private String SequenceNo;
	/** 被保人名称 */
	private String Name;
	/** 被保人性别 */
	private String Sex;
	/** 被保人出生日期 */
	private String Birthday;
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
	private String MarriageDate;
	/** 健康状况 */
	private String Health;
	/** 身高 */
	private String Stature;
	/** 体重 */
	private String Avoirdupois;
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
	private String JoinCompanyDate;
	/** 职位 */
	private String Position;
	/** 工资 */
	private String Salary;
	/** 职业类别 */
	private String OccupationType;
	/** 参加工作日期 */
	private String StartWorkDate;
	/** 职业代码 */
	private String OccupationCode;
	/** 职业（工种） */
	private String WorkType;
	/** 兼职（工种） */
	private String PluralityType;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 操作员 */
	private String Operator;
	/** 被保人状态 */
	private String InsuredStat;
	/** 入机日期 */
	private String MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private String ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 核保状态 */
	private String UWFlag;
	/** 最终核保人编码 */
	private String UWCode;
	/** 核保完成日期 */
	private String UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 身体指标 */
	private String BMI;
	/** 被保人数目 */
	private String InsuredPeoples;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;
	/** 团体客户序号 */
	private String CustomerSeqNo;
	/** 社保登记号 */
	private String SocialInsuNo;
	/** 被保人签名 */
	private String SignName;
	/** E_mail */
	private String EMail;
	/** 首选回访电话 */
	private String Mobile;
	/** 单位传真 */
	private String CompanyFax;
	/** 其他联系电话 */
	private String CompanyPhone;
	/** 单位邮编 */
	private String CompanyZipCode;
	/** 单位地址 */
	private String CompanyAddress;
	/** 家庭传真 */
	private String HomeFax;
	/** 家庭电话 */
	private String HomePhone;
	/** 住址邮编 */
	private String HomeZipCode;
	/** 住址 */
	private String HomeAddress;
	/** 通讯传真 */
	private String Fax;
	/** 通讯电话 */
	private String Phone;
	/** 通讯邮编 */
	private String ZipCode;
	/** 通讯地址 */
	private String PostalAddress;
	/** 转储日期 */
	private Date TransDate;
	/** 转储时间 */
	private String TransTime;
	/** 转储操作员 */
	private String TransOperator;
	/** 是否有社保标志 */
	private String SocialInsuFlag;
	/** 证件有效期 */
	private String IDExpDate;

	public static final int FIELDNUM = 79;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOInsuredBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "IDX";
		pk[1] = "ContNo";
		pk[2] = "InputNo";
		pk[3] = "FillNo";
		pk[4] = "InsuredNo";

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
		LBPOInsuredBSchema cloned = (LBPOInsuredBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getIDX()
	{
		return IDX;
	}
	public void setIDX(String aIDX)
	{
		if(aIDX!=null && aIDX.length()>20)
			throw new IllegalArgumentException("转储号IDX值"+aIDX+"的长度"+aIDX.length()+"大于最大值20");
		IDX = aIDX;
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
	public int getInputNo()
	{
		return InputNo;
	}
	public void setInputNo(int aInputNo)
	{
		InputNo = aInputNo;
	}
	public void setInputNo(String aInputNo)
	{
		if (aInputNo != null && !aInputNo.equals(""))
		{
			Integer tInteger = new Integer(aInputNo);
			int i = tInteger.intValue();
			InputNo = i;
		}
	}

	public String getFillNo()
	{
		return FillNo;
	}
	public void setFillNo(String aFillNo)
	{
		if(aFillNo!=null && aFillNo.length()>10)
			throw new IllegalArgumentException("添空号FillNo值"+aFillNo+"的长度"+aFillNo.length()+"大于最大值10");
		FillNo = aFillNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	/**
	* 同投保单号
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>50)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值50");
		ManageCom = aManageCom;
	}
	/**
	* 关联统括保单处理
	*/
	public String getExecuteCom()
	{
		return ExecuteCom;
	}
	public void setExecuteCom(String aExecuteCom)
	{
		if(aExecuteCom!=null && aExecuteCom.length()>50)
			throw new IllegalArgumentException("处理机构ExecuteCom值"+aExecuteCom+"的长度"+aExecuteCom.length()+"大于最大值50");
		ExecuteCom = aExecuteCom;
	}
	public String getFamilyID()
	{
		return FamilyID;
	}
	public void setFamilyID(String aFamilyID)
	{
		if(aFamilyID!=null && aFamilyID.length()>50)
			throw new IllegalArgumentException("家庭保障号FamilyID值"+aFamilyID+"的长度"+aFamilyID.length()+"大于最大值50");
		FamilyID = aFamilyID;
	}
	public String getRelationToMainInsured()
	{
		return RelationToMainInsured;
	}
	public void setRelationToMainInsured(String aRelationToMainInsured)
	{
		if(aRelationToMainInsured!=null && aRelationToMainInsured.length()>50)
			throw new IllegalArgumentException("与主被保人关系RelationToMainInsured值"+aRelationToMainInsured+"的长度"+aRelationToMainInsured.length()+"大于最大值50");
		RelationToMainInsured = aRelationToMainInsured;
	}
	public String getRelationToAppnt()
	{
		return RelationToAppnt;
	}
	public void setRelationToAppnt(String aRelationToAppnt)
	{
		if(aRelationToAppnt!=null && aRelationToAppnt.length()>50)
			throw new IllegalArgumentException("与投保人关系RelationToAppnt值"+aRelationToAppnt+"的长度"+aRelationToAppnt.length()+"大于最大值50");
		RelationToAppnt = aRelationToAppnt;
	}
	public String getAddressNo()
	{
		return AddressNo;
	}
	public void setAddressNo(String aAddressNo)
	{
		if(aAddressNo!=null && aAddressNo.length()>50)
			throw new IllegalArgumentException("客户地址号码AddressNo值"+aAddressNo+"的长度"+aAddressNo.length()+"大于最大值50");
		AddressNo = aAddressNo;
	}
	/**
	* 客户分类的号码
	*/
	public String getSequenceNo()
	{
		return SequenceNo;
	}
	public void setSequenceNo(String aSequenceNo)
	{
		if(aSequenceNo!=null && aSequenceNo.length()>50)
			throw new IllegalArgumentException("客户内部号码SequenceNo值"+aSequenceNo+"的长度"+aSequenceNo.length()+"大于最大值50");
		SequenceNo = aSequenceNo;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>120)
			throw new IllegalArgumentException("被保人名称Name值"+aName+"的长度"+aName.length()+"大于最大值120");
		Name = aName;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		if(aSex!=null && aSex.length()>50)
			throw new IllegalArgumentException("被保人性别Sex值"+aSex+"的长度"+aSex.length()+"大于最大值50");
		Sex = aSex;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getBirthday()
	{
		return Birthday;
	}
	public void setBirthday(String aBirthday)
	{
		if(aBirthday!=null && aBirthday.length()>50)
			throw new IllegalArgumentException("被保人出生日期Birthday值"+aBirthday+"的长度"+aBirthday.length()+"大于最大值50");
		Birthday = aBirthday;
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
		if(aIDType!=null && aIDType.length()>50)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值50");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>50)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值50");
		IDNo = aIDNo;
	}
	public String getNativePlace()
	{
		return NativePlace;
	}
	public void setNativePlace(String aNativePlace)
	{
		if(aNativePlace!=null && aNativePlace.length()>50)
			throw new IllegalArgumentException("国籍NativePlace值"+aNativePlace+"的长度"+aNativePlace.length()+"大于最大值50");
		NativePlace = aNativePlace;
	}
	public String getNationality()
	{
		return Nationality;
	}
	public void setNationality(String aNationality)
	{
		if(aNationality!=null && aNationality.length()>50)
			throw new IllegalArgumentException("民族Nationality值"+aNationality+"的长度"+aNationality.length()+"大于最大值50");
		Nationality = aNationality;
	}
	public String getRgtAddress()
	{
		return RgtAddress;
	}
	public void setRgtAddress(String aRgtAddress)
	{
		if(aRgtAddress!=null && aRgtAddress.length()>100)
			throw new IllegalArgumentException("户口所在地RgtAddress值"+aRgtAddress+"的长度"+aRgtAddress.length()+"大于最大值100");
		RgtAddress = aRgtAddress;
	}
	public String getMarriage()
	{
		return Marriage;
	}
	public void setMarriage(String aMarriage)
	{
		if(aMarriage!=null && aMarriage.length()>50)
			throw new IllegalArgumentException("婚姻状况Marriage值"+aMarriage+"的长度"+aMarriage.length()+"大于最大值50");
		Marriage = aMarriage;
	}
	public String getMarriageDate()
	{
		return MarriageDate;
	}
	public void setMarriageDate(String aMarriageDate)
	{
		if(aMarriageDate!=null && aMarriageDate.length()>50)
			throw new IllegalArgumentException("结婚日期MarriageDate值"+aMarriageDate+"的长度"+aMarriageDate.length()+"大于最大值50");
		MarriageDate = aMarriageDate;
	}
	public String getHealth()
	{
		return Health;
	}
	public void setHealth(String aHealth)
	{
		if(aHealth!=null && aHealth.length()>50)
			throw new IllegalArgumentException("健康状况Health值"+aHealth+"的长度"+aHealth.length()+"大于最大值50");
		Health = aHealth;
	}
	public String getStature()
	{
		return Stature;
	}
	public void setStature(String aStature)
	{
		if(aStature!=null && aStature.length()>50)
			throw new IllegalArgumentException("身高Stature值"+aStature+"的长度"+aStature.length()+"大于最大值50");
		Stature = aStature;
	}
	public String getAvoirdupois()
	{
		return Avoirdupois;
	}
	public void setAvoirdupois(String aAvoirdupois)
	{
		if(aAvoirdupois!=null && aAvoirdupois.length()>50)
			throw new IllegalArgumentException("体重Avoirdupois值"+aAvoirdupois+"的长度"+aAvoirdupois.length()+"大于最大值50");
		Avoirdupois = aAvoirdupois;
	}
	public String getDegree()
	{
		return Degree;
	}
	public void setDegree(String aDegree)
	{
		if(aDegree!=null && aDegree.length()>50)
			throw new IllegalArgumentException("学历Degree值"+aDegree+"的长度"+aDegree.length()+"大于最大值50");
		Degree = aDegree;
	}
	public String getCreditGrade()
	{
		return CreditGrade;
	}
	public void setCreditGrade(String aCreditGrade)
	{
		if(aCreditGrade!=null && aCreditGrade.length()>50)
			throw new IllegalArgumentException("信用等级CreditGrade值"+aCreditGrade+"的长度"+aCreditGrade.length()+"大于最大值50");
		CreditGrade = aCreditGrade;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>50)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值50");
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		if(aBankAccNo!=null && aBankAccNo.length()>50)
			throw new IllegalArgumentException("银行帐号BankAccNo值"+aBankAccNo+"的长度"+aBankAccNo.length()+"大于最大值50");
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>100)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值100");
		AccName = aAccName;
	}
	public String getJoinCompanyDate()
	{
		return JoinCompanyDate;
	}
	public void setJoinCompanyDate(String aJoinCompanyDate)
	{
		if(aJoinCompanyDate!=null && aJoinCompanyDate.length()>50)
			throw new IllegalArgumentException("入司日期JoinCompanyDate值"+aJoinCompanyDate+"的长度"+aJoinCompanyDate.length()+"大于最大值50");
		JoinCompanyDate = aJoinCompanyDate;
	}
	public String getPosition()
	{
		return Position;
	}
	public void setPosition(String aPosition)
	{
		if(aPosition!=null && aPosition.length()>50)
			throw new IllegalArgumentException("职位Position值"+aPosition+"的长度"+aPosition.length()+"大于最大值50");
		Position = aPosition;
	}
	public String getSalary()
	{
		return Salary;
	}
	public void setSalary(String aSalary)
	{
		if(aSalary!=null && aSalary.length()>50)
			throw new IllegalArgumentException("工资Salary值"+aSalary+"的长度"+aSalary.length()+"大于最大值50");
		Salary = aSalary;
	}
	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		if(aOccupationType!=null && aOccupationType.length()>50)
			throw new IllegalArgumentException("职业类别OccupationType值"+aOccupationType+"的长度"+aOccupationType.length()+"大于最大值50");
		OccupationType = aOccupationType;
	}
	public String getStartWorkDate()
	{
		return StartWorkDate;
	}
	public void setStartWorkDate(String aStartWorkDate)
	{
		if(aStartWorkDate!=null && aStartWorkDate.length()>50)
			throw new IllegalArgumentException("参加工作日期StartWorkDate值"+aStartWorkDate+"的长度"+aStartWorkDate.length()+"大于最大值50");
		StartWorkDate = aStartWorkDate;
	}
	public String getOccupationCode()
	{
		return OccupationCode;
	}
	public void setOccupationCode(String aOccupationCode)
	{
		if(aOccupationCode!=null && aOccupationCode.length()>50)
			throw new IllegalArgumentException("职业代码OccupationCode值"+aOccupationCode+"的长度"+aOccupationCode.length()+"大于最大值50");
		OccupationCode = aOccupationCode;
	}
	public String getWorkType()
	{
		return WorkType;
	}
	public void setWorkType(String aWorkType)
	{
		if(aWorkType!=null && aWorkType.length()>50)
			throw new IllegalArgumentException("职业（工种）WorkType值"+aWorkType+"的长度"+aWorkType.length()+"大于最大值50");
		WorkType = aWorkType;
	}
	public String getPluralityType()
	{
		return PluralityType;
	}
	public void setPluralityType(String aPluralityType)
	{
		if(aPluralityType!=null && aPluralityType.length()>50)
			throw new IllegalArgumentException("兼职（工种）PluralityType值"+aPluralityType+"的长度"+aPluralityType.length()+"大于最大值50");
		PluralityType = aPluralityType;
	}
	public String getSmokeFlag()
	{
		return SmokeFlag;
	}
	public void setSmokeFlag(String aSmokeFlag)
	{
		if(aSmokeFlag!=null && aSmokeFlag.length()>50)
			throw new IllegalArgumentException("是否吸烟标志SmokeFlag值"+aSmokeFlag+"的长度"+aSmokeFlag.length()+"大于最大值50");
		SmokeFlag = aSmokeFlag;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		if(aContPlanCode!=null && aContPlanCode.length()>50)
			throw new IllegalArgumentException("保险计划编码ContPlanCode值"+aContPlanCode+"的长度"+aContPlanCode.length()+"大于最大值50");
		ContPlanCode = aContPlanCode;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>50)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值50");
		Operator = aOperator;
	}
	/**
	* 1-暂停状态<p>
	* 0-未暂停状态
	*/
	public String getInsuredStat()
	{
		return InsuredStat;
	}
	public void setInsuredStat(String aInsuredStat)
	{
		if(aInsuredStat!=null && aInsuredStat.length()>50)
			throw new IllegalArgumentException("被保人状态InsuredStat值"+aInsuredStat+"的长度"+aInsuredStat.length()+"大于最大值50");
		InsuredStat = aInsuredStat;
	}
	public String getMakeDate()
	{
		return MakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if(aMakeDate!=null && aMakeDate.length()>50)
			throw new IllegalArgumentException("入机日期MakeDate值"+aMakeDate+"的长度"+aMakeDate.length()+"大于最大值50");
		MakeDate = aMakeDate;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		if(aMakeTime!=null && aMakeTime.length()>50)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值50");
		MakeTime = aMakeTime;
	}
	public String getModifyDate()
	{
		return ModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if(aModifyDate!=null && aModifyDate.length()>50)
			throw new IllegalArgumentException("最后一次修改日期ModifyDate值"+aModifyDate+"的长度"+aModifyDate.length()+"大于最大值50");
		ModifyDate = aModifyDate;
	}
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		if(aModifyTime!=null && aModifyTime.length()>50)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值50");
		ModifyTime = aModifyTime;
	}
	/**
	* 1 拒保<p>
	* 2 延期<p>
	* 3 条件承保<p>
	* 4 通融<p>
	* 5 自动<p>
	* 6 待上级<p>
	* 7 问题件<p>
	* 8 核保通知书<p>
	* 9 正常<p>
	* a 撤单<p>
	* b 保险计划变更<p>
	* z 核保订正
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>50)
			throw new IllegalArgumentException("核保状态UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值50");
		UWFlag = aUWFlag;
	}
	public String getUWCode()
	{
		return UWCode;
	}
	public void setUWCode(String aUWCode)
	{
		if(aUWCode!=null && aUWCode.length()>50)
			throw new IllegalArgumentException("最终核保人编码UWCode值"+aUWCode+"的长度"+aUWCode.length()+"大于最大值50");
		UWCode = aUWCode;
	}
	public String getUWDate()
	{
		return UWDate;
	}
	public void setUWDate(String aUWDate)
	{
		if(aUWDate!=null && aUWDate.length()>50)
			throw new IllegalArgumentException("核保完成日期UWDate值"+aUWDate+"的长度"+aUWDate.length()+"大于最大值50");
		UWDate = aUWDate;
	}
	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
		if(aUWTime!=null && aUWTime.length()>50)
			throw new IllegalArgumentException("核保完成时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值50");
		UWTime = aUWTime;
	}
	public String getBMI()
	{
		return BMI;
	}
	public void setBMI(String aBMI)
	{
		if(aBMI!=null && aBMI.length()>50)
			throw new IllegalArgumentException("身体指标BMI值"+aBMI+"的长度"+aBMI.length()+"大于最大值50");
		BMI = aBMI;
	}
	public String getInsuredPeoples()
	{
		return InsuredPeoples;
	}
	public void setInsuredPeoples(String aInsuredPeoples)
	{
		if(aInsuredPeoples!=null && aInsuredPeoples.length()>50)
			throw new IllegalArgumentException("被保人数目InsuredPeoples值"+aInsuredPeoples+"的长度"+aInsuredPeoples.length()+"大于最大值50");
		InsuredPeoples = aInsuredPeoples;
	}
	public String getLicense()
	{
		return License;
	}
	public void setLicense(String aLicense)
	{
		if(aLicense!=null && aLicense.length()>50)
			throw new IllegalArgumentException("驾照License值"+aLicense+"的长度"+aLicense.length()+"大于最大值50");
		License = aLicense;
	}
	public String getLicenseType()
	{
		return LicenseType;
	}
	public void setLicenseType(String aLicenseType)
	{
		if(aLicenseType!=null && aLicenseType.length()>50)
			throw new IllegalArgumentException("驾照类型LicenseType值"+aLicenseType+"的长度"+aLicenseType.length()+"大于最大值50");
		LicenseType = aLicenseType;
	}
	public String getCustomerSeqNo()
	{
		return CustomerSeqNo;
	}
	public void setCustomerSeqNo(String aCustomerSeqNo)
	{
		if(aCustomerSeqNo!=null && aCustomerSeqNo.length()>50)
			throw new IllegalArgumentException("团体客户序号CustomerSeqNo值"+aCustomerSeqNo+"的长度"+aCustomerSeqNo.length()+"大于最大值50");
		CustomerSeqNo = aCustomerSeqNo;
	}
	public String getSocialInsuNo()
	{
		return SocialInsuNo;
	}
	public void setSocialInsuNo(String aSocialInsuNo)
	{
		if(aSocialInsuNo!=null && aSocialInsuNo.length()>50)
			throw new IllegalArgumentException("社保登记号SocialInsuNo值"+aSocialInsuNo+"的长度"+aSocialInsuNo.length()+"大于最大值50");
		SocialInsuNo = aSocialInsuNo;
	}
	public String getSignName()
	{
		return SignName;
	}
	public void setSignName(String aSignName)
	{
		if(aSignName!=null && aSignName.length()>120)
			throw new IllegalArgumentException("被保人签名SignName值"+aSignName+"的长度"+aSignName.length()+"大于最大值120");
		SignName = aSignName;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>100)
			throw new IllegalArgumentException("E_mailEMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值100");
		EMail = aEMail;
	}
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		if(aMobile!=null && aMobile.length()>50)
			throw new IllegalArgumentException("首选回访电话Mobile值"+aMobile+"的长度"+aMobile.length()+"大于最大值50");
		Mobile = aMobile;
	}
	public String getCompanyFax()
	{
		return CompanyFax;
	}
	public void setCompanyFax(String aCompanyFax)
	{
		if(aCompanyFax!=null && aCompanyFax.length()>50)
			throw new IllegalArgumentException("单位传真CompanyFax值"+aCompanyFax+"的长度"+aCompanyFax.length()+"大于最大值50");
		CompanyFax = aCompanyFax;
	}
	public String getCompanyPhone()
	{
		return CompanyPhone;
	}
	public void setCompanyPhone(String aCompanyPhone)
	{
		if(aCompanyPhone!=null && aCompanyPhone.length()>50)
			throw new IllegalArgumentException("其他联系电话CompanyPhone值"+aCompanyPhone+"的长度"+aCompanyPhone.length()+"大于最大值50");
		CompanyPhone = aCompanyPhone;
	}
	public String getCompanyZipCode()
	{
		return CompanyZipCode;
	}
	public void setCompanyZipCode(String aCompanyZipCode)
	{
		if(aCompanyZipCode!=null && aCompanyZipCode.length()>50)
			throw new IllegalArgumentException("单位邮编CompanyZipCode值"+aCompanyZipCode+"的长度"+aCompanyZipCode.length()+"大于最大值50");
		CompanyZipCode = aCompanyZipCode;
	}
	public String getCompanyAddress()
	{
		return CompanyAddress;
	}
	public void setCompanyAddress(String aCompanyAddress)
	{
		if(aCompanyAddress!=null && aCompanyAddress.length()>200)
			throw new IllegalArgumentException("单位地址CompanyAddress值"+aCompanyAddress+"的长度"+aCompanyAddress.length()+"大于最大值200");
		CompanyAddress = aCompanyAddress;
	}
	public String getHomeFax()
	{
		return HomeFax;
	}
	public void setHomeFax(String aHomeFax)
	{
		if(aHomeFax!=null && aHomeFax.length()>50)
			throw new IllegalArgumentException("家庭传真HomeFax值"+aHomeFax+"的长度"+aHomeFax.length()+"大于最大值50");
		HomeFax = aHomeFax;
	}
	public String getHomePhone()
	{
		return HomePhone;
	}
	public void setHomePhone(String aHomePhone)
	{
		if(aHomePhone!=null && aHomePhone.length()>50)
			throw new IllegalArgumentException("家庭电话HomePhone值"+aHomePhone+"的长度"+aHomePhone.length()+"大于最大值50");
		HomePhone = aHomePhone;
	}
	public String getHomeZipCode()
	{
		return HomeZipCode;
	}
	public void setHomeZipCode(String aHomeZipCode)
	{
		if(aHomeZipCode!=null && aHomeZipCode.length()>50)
			throw new IllegalArgumentException("住址邮编HomeZipCode值"+aHomeZipCode+"的长度"+aHomeZipCode.length()+"大于最大值50");
		HomeZipCode = aHomeZipCode;
	}
	public String getHomeAddress()
	{
		return HomeAddress;
	}
	public void setHomeAddress(String aHomeAddress)
	{
		if(aHomeAddress!=null && aHomeAddress.length()>200)
			throw new IllegalArgumentException("住址HomeAddress值"+aHomeAddress+"的长度"+aHomeAddress.length()+"大于最大值200");
		HomeAddress = aHomeAddress;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>50)
			throw new IllegalArgumentException("通讯传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值50");
		Fax = aFax;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>50)
			throw new IllegalArgumentException("通讯电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值50");
		Phone = aPhone;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		if(aZipCode!=null && aZipCode.length()>50)
			throw new IllegalArgumentException("通讯邮编ZipCode值"+aZipCode+"的长度"+aZipCode.length()+"大于最大值50");
		ZipCode = aZipCode;
	}
	public String getPostalAddress()
	{
		return PostalAddress;
	}
	public void setPostalAddress(String aPostalAddress)
	{
		if(aPostalAddress!=null && aPostalAddress.length()>200)
			throw new IllegalArgumentException("通讯地址PostalAddress值"+aPostalAddress+"的长度"+aPostalAddress.length()+"大于最大值200");
		PostalAddress = aPostalAddress;
	}
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	public String getTransTime()
	{
		return TransTime;
	}
	public void setTransTime(String aTransTime)
	{
		if(aTransTime!=null && aTransTime.length()>8)
			throw new IllegalArgumentException("转储时间TransTime值"+aTransTime+"的长度"+aTransTime.length()+"大于最大值8");
		TransTime = aTransTime;
	}
	public String getTransOperator()
	{
		return TransOperator;
	}
	public void setTransOperator(String aTransOperator)
	{
		if(aTransOperator!=null && aTransOperator.length()>10)
			throw new IllegalArgumentException("转储操作员TransOperator值"+aTransOperator+"的长度"+aTransOperator.length()+"大于最大值10");
		TransOperator = aTransOperator;
	}
	public String getSocialInsuFlag()
	{
		return SocialInsuFlag;
	}
	public void setSocialInsuFlag(String aSocialInsuFlag)
	{
		if(aSocialInsuFlag!=null && aSocialInsuFlag.length()>50)
			throw new IllegalArgumentException("是否有社保标志SocialInsuFlag值"+aSocialInsuFlag+"的长度"+aSocialInsuFlag.length()+"大于最大值50");
		SocialInsuFlag = aSocialInsuFlag;
	}
	public String getIDExpDate()
	{
		return IDExpDate;
	}
	public void setIDExpDate(String aIDExpDate)
	{
		if(aIDExpDate!=null && aIDExpDate.length()>50)
			throw new IllegalArgumentException("证件有效期IDExpDate值"+aIDExpDate+"的长度"+aIDExpDate.length()+"大于最大值50");
		IDExpDate = aIDExpDate;
	}

	/**
	* 使用另外一个 LBPOInsuredBSchema 对象给 Schema 赋值
	* @param: aLBPOInsuredBSchema LBPOInsuredBSchema
	**/
	public void setSchema(LBPOInsuredBSchema aLBPOInsuredBSchema)
	{
		this.IDX = aLBPOInsuredBSchema.getIDX();
		this.GrpContNo = aLBPOInsuredBSchema.getGrpContNo();
		this.ContNo = aLBPOInsuredBSchema.getContNo();
		this.InputNo = aLBPOInsuredBSchema.getInputNo();
		this.FillNo = aLBPOInsuredBSchema.getFillNo();
		this.InsuredNo = aLBPOInsuredBSchema.getInsuredNo();
		this.PrtNo = aLBPOInsuredBSchema.getPrtNo();
		this.AppntNo = aLBPOInsuredBSchema.getAppntNo();
		this.ManageCom = aLBPOInsuredBSchema.getManageCom();
		this.ExecuteCom = aLBPOInsuredBSchema.getExecuteCom();
		this.FamilyID = aLBPOInsuredBSchema.getFamilyID();
		this.RelationToMainInsured = aLBPOInsuredBSchema.getRelationToMainInsured();
		this.RelationToAppnt = aLBPOInsuredBSchema.getRelationToAppnt();
		this.AddressNo = aLBPOInsuredBSchema.getAddressNo();
		this.SequenceNo = aLBPOInsuredBSchema.getSequenceNo();
		this.Name = aLBPOInsuredBSchema.getName();
		this.Sex = aLBPOInsuredBSchema.getSex();
		this.Birthday = aLBPOInsuredBSchema.getBirthday();
		this.IDType = aLBPOInsuredBSchema.getIDType();
		this.IDNo = aLBPOInsuredBSchema.getIDNo();
		this.NativePlace = aLBPOInsuredBSchema.getNativePlace();
		this.Nationality = aLBPOInsuredBSchema.getNationality();
		this.RgtAddress = aLBPOInsuredBSchema.getRgtAddress();
		this.Marriage = aLBPOInsuredBSchema.getMarriage();
		this.MarriageDate = aLBPOInsuredBSchema.getMarriageDate();
		this.Health = aLBPOInsuredBSchema.getHealth();
		this.Stature = aLBPOInsuredBSchema.getStature();
		this.Avoirdupois = aLBPOInsuredBSchema.getAvoirdupois();
		this.Degree = aLBPOInsuredBSchema.getDegree();
		this.CreditGrade = aLBPOInsuredBSchema.getCreditGrade();
		this.BankCode = aLBPOInsuredBSchema.getBankCode();
		this.BankAccNo = aLBPOInsuredBSchema.getBankAccNo();
		this.AccName = aLBPOInsuredBSchema.getAccName();
		this.JoinCompanyDate = aLBPOInsuredBSchema.getJoinCompanyDate();
		this.Position = aLBPOInsuredBSchema.getPosition();
		this.Salary = aLBPOInsuredBSchema.getSalary();
		this.OccupationType = aLBPOInsuredBSchema.getOccupationType();
		this.StartWorkDate = aLBPOInsuredBSchema.getStartWorkDate();
		this.OccupationCode = aLBPOInsuredBSchema.getOccupationCode();
		this.WorkType = aLBPOInsuredBSchema.getWorkType();
		this.PluralityType = aLBPOInsuredBSchema.getPluralityType();
		this.SmokeFlag = aLBPOInsuredBSchema.getSmokeFlag();
		this.ContPlanCode = aLBPOInsuredBSchema.getContPlanCode();
		this.Operator = aLBPOInsuredBSchema.getOperator();
		this.InsuredStat = aLBPOInsuredBSchema.getInsuredStat();
		this.MakeDate = aLBPOInsuredBSchema.getMakeDate();
		this.MakeTime = aLBPOInsuredBSchema.getMakeTime();
		this.ModifyDate = aLBPOInsuredBSchema.getModifyDate();
		this.ModifyTime = aLBPOInsuredBSchema.getModifyTime();
		this.UWFlag = aLBPOInsuredBSchema.getUWFlag();
		this.UWCode = aLBPOInsuredBSchema.getUWCode();
		this.UWDate = aLBPOInsuredBSchema.getUWDate();
		this.UWTime = aLBPOInsuredBSchema.getUWTime();
		this.BMI = aLBPOInsuredBSchema.getBMI();
		this.InsuredPeoples = aLBPOInsuredBSchema.getInsuredPeoples();
		this.License = aLBPOInsuredBSchema.getLicense();
		this.LicenseType = aLBPOInsuredBSchema.getLicenseType();
		this.CustomerSeqNo = aLBPOInsuredBSchema.getCustomerSeqNo();
		this.SocialInsuNo = aLBPOInsuredBSchema.getSocialInsuNo();
		this.SignName = aLBPOInsuredBSchema.getSignName();
		this.EMail = aLBPOInsuredBSchema.getEMail();
		this.Mobile = aLBPOInsuredBSchema.getMobile();
		this.CompanyFax = aLBPOInsuredBSchema.getCompanyFax();
		this.CompanyPhone = aLBPOInsuredBSchema.getCompanyPhone();
		this.CompanyZipCode = aLBPOInsuredBSchema.getCompanyZipCode();
		this.CompanyAddress = aLBPOInsuredBSchema.getCompanyAddress();
		this.HomeFax = aLBPOInsuredBSchema.getHomeFax();
		this.HomePhone = aLBPOInsuredBSchema.getHomePhone();
		this.HomeZipCode = aLBPOInsuredBSchema.getHomeZipCode();
		this.HomeAddress = aLBPOInsuredBSchema.getHomeAddress();
		this.Fax = aLBPOInsuredBSchema.getFax();
		this.Phone = aLBPOInsuredBSchema.getPhone();
		this.ZipCode = aLBPOInsuredBSchema.getZipCode();
		this.PostalAddress = aLBPOInsuredBSchema.getPostalAddress();
		this.TransDate = fDate.getDate( aLBPOInsuredBSchema.getTransDate());
		this.TransTime = aLBPOInsuredBSchema.getTransTime();
		this.TransOperator = aLBPOInsuredBSchema.getTransOperator();
		this.SocialInsuFlag = aLBPOInsuredBSchema.getSocialInsuFlag();
		this.IDExpDate = aLBPOInsuredBSchema.getIDExpDate();
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
			if( rs.getString("IDX") == null )
				this.IDX = null;
			else
				this.IDX = rs.getString("IDX").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.InputNo = rs.getInt("InputNo");
			if( rs.getString("FillNo") == null )
				this.FillNo = null;
			else
				this.FillNo = rs.getString("FillNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("FamilyID") == null )
				this.FamilyID = null;
			else
				this.FamilyID = rs.getString("FamilyID").trim();

			if( rs.getString("RelationToMainInsured") == null )
				this.RelationToMainInsured = null;
			else
				this.RelationToMainInsured = rs.getString("RelationToMainInsured").trim();

			if( rs.getString("RelationToAppnt") == null )
				this.RelationToAppnt = null;
			else
				this.RelationToAppnt = rs.getString("RelationToAppnt").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			if( rs.getString("SequenceNo") == null )
				this.SequenceNo = null;
			else
				this.SequenceNo = rs.getString("SequenceNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			if( rs.getString("Birthday") == null )
				this.Birthday = null;
			else
				this.Birthday = rs.getString("Birthday").trim();

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

			if( rs.getString("MarriageDate") == null )
				this.MarriageDate = null;
			else
				this.MarriageDate = rs.getString("MarriageDate").trim();

			if( rs.getString("Health") == null )
				this.Health = null;
			else
				this.Health = rs.getString("Health").trim();

			if( rs.getString("Stature") == null )
				this.Stature = null;
			else
				this.Stature = rs.getString("Stature").trim();

			if( rs.getString("Avoirdupois") == null )
				this.Avoirdupois = null;
			else
				this.Avoirdupois = rs.getString("Avoirdupois").trim();

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

			if( rs.getString("JoinCompanyDate") == null )
				this.JoinCompanyDate = null;
			else
				this.JoinCompanyDate = rs.getString("JoinCompanyDate").trim();

			if( rs.getString("Position") == null )
				this.Position = null;
			else
				this.Position = rs.getString("Position").trim();

			if( rs.getString("Salary") == null )
				this.Salary = null;
			else
				this.Salary = rs.getString("Salary").trim();

			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("StartWorkDate") == null )
				this.StartWorkDate = null;
			else
				this.StartWorkDate = rs.getString("StartWorkDate").trim();

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

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("InsuredStat") == null )
				this.InsuredStat = null;
			else
				this.InsuredStat = rs.getString("InsuredStat").trim();

			if( rs.getString("MakeDate") == null )
				this.MakeDate = null;
			else
				this.MakeDate = rs.getString("MakeDate").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyDate") == null )
				this.ModifyDate = null;
			else
				this.ModifyDate = rs.getString("ModifyDate").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			if( rs.getString("UWDate") == null )
				this.UWDate = null;
			else
				this.UWDate = rs.getString("UWDate").trim();

			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("BMI") == null )
				this.BMI = null;
			else
				this.BMI = rs.getString("BMI").trim();

			if( rs.getString("InsuredPeoples") == null )
				this.InsuredPeoples = null;
			else
				this.InsuredPeoples = rs.getString("InsuredPeoples").trim();

			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("LicenseType") == null )
				this.LicenseType = null;
			else
				this.LicenseType = rs.getString("LicenseType").trim();

			if( rs.getString("CustomerSeqNo") == null )
				this.CustomerSeqNo = null;
			else
				this.CustomerSeqNo = rs.getString("CustomerSeqNo").trim();

			if( rs.getString("SocialInsuNo") == null )
				this.SocialInsuNo = null;
			else
				this.SocialInsuNo = rs.getString("SocialInsuNo").trim();

			if( rs.getString("SignName") == null )
				this.SignName = null;
			else
				this.SignName = rs.getString("SignName").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("CompanyFax") == null )
				this.CompanyFax = null;
			else
				this.CompanyFax = rs.getString("CompanyFax").trim();

			if( rs.getString("CompanyPhone") == null )
				this.CompanyPhone = null;
			else
				this.CompanyPhone = rs.getString("CompanyPhone").trim();

			if( rs.getString("CompanyZipCode") == null )
				this.CompanyZipCode = null;
			else
				this.CompanyZipCode = rs.getString("CompanyZipCode").trim();

			if( rs.getString("CompanyAddress") == null )
				this.CompanyAddress = null;
			else
				this.CompanyAddress = rs.getString("CompanyAddress").trim();

			if( rs.getString("HomeFax") == null )
				this.HomeFax = null;
			else
				this.HomeFax = rs.getString("HomeFax").trim();

			if( rs.getString("HomePhone") == null )
				this.HomePhone = null;
			else
				this.HomePhone = rs.getString("HomePhone").trim();

			if( rs.getString("HomeZipCode") == null )
				this.HomeZipCode = null;
			else
				this.HomeZipCode = rs.getString("HomeZipCode").trim();

			if( rs.getString("HomeAddress") == null )
				this.HomeAddress = null;
			else
				this.HomeAddress = rs.getString("HomeAddress").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("PostalAddress") == null )
				this.PostalAddress = null;
			else
				this.PostalAddress = rs.getString("PostalAddress").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("TransTime") == null )
				this.TransTime = null;
			else
				this.TransTime = rs.getString("TransTime").trim();

			if( rs.getString("TransOperator") == null )
				this.TransOperator = null;
			else
				this.TransOperator = rs.getString("TransOperator").trim();

			if( rs.getString("SocialInsuFlag") == null )
				this.SocialInsuFlag = null;
			else
				this.SocialInsuFlag = rs.getString("SocialInsuFlag").trim();

			if( rs.getString("IDExpDate") == null )
				this.IDExpDate = null;
			else
				this.IDExpDate = rs.getString("IDExpDate").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPOInsuredB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOInsuredBSchema getSchema()
	{
		LBPOInsuredBSchema aLBPOInsuredBSchema = new LBPOInsuredBSchema();
		aLBPOInsuredBSchema.setSchema(this);
		return aLBPOInsuredBSchema;
	}

	public LBPOInsuredBDB getDB()
	{
		LBPOInsuredBDB aDBOper = new LBPOInsuredBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOInsuredB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(IDX)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InputNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExecuteCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToMainInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToAppnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SequenceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Birthday)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NativePlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Nationality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Marriage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MarriageDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Health)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Stature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Avoirdupois)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Degree)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreditGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JoinCompanyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Position)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Salary)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartWorkDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PluralityType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredStat)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BMI)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredPeoples)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LicenseType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerSeqNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyFax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeFax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomePhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostalAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDExpDate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOInsuredB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			IDX = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InputNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			FillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RelationToMainInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RelationToAppnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SequenceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Birthday = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MarriageDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Stature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Avoirdupois = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			JoinCompanyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Salary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			StartWorkDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			InsuredStat = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			MakeDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			ModifyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			UWDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			BMI = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			InsuredPeoples = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			CustomerSeqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			SocialInsuNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			SignName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			CompanyFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			CompanyPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			CompanyZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			CompanyAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			HomeFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			HomePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			HomeZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75,SysConst.PACKAGESPILTER));
			TransTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			TransOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			SocialInsuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			IDExpDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredBSchema";
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
		if (FCode.equalsIgnoreCase("IDX"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDX));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputNo));
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FillNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equalsIgnoreCase("FamilyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyID));
		}
		if (FCode.equalsIgnoreCase("RelationToMainInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToMainInsured));
		}
		if (FCode.equalsIgnoreCase("RelationToAppnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToAppnt));
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equalsIgnoreCase("SequenceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SequenceNo));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(Birthday));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(MarriageDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(JoinCompanyDate));
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
		if (FCode.equalsIgnoreCase("StartWorkDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartWorkDate));
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("InsuredStat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredStat));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWDate));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("BMI"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BMI));
		}
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredPeoples));
		}
		if (FCode.equalsIgnoreCase("License"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(License));
		}
		if (FCode.equalsIgnoreCase("LicenseType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LicenseType));
		}
		if (FCode.equalsIgnoreCase("CustomerSeqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerSeqNo));
		}
		if (FCode.equalsIgnoreCase("SocialInsuNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuNo));
		}
		if (FCode.equalsIgnoreCase("SignName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignName));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equalsIgnoreCase("CompanyFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyFax));
		}
		if (FCode.equalsIgnoreCase("CompanyPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyPhone));
		}
		if (FCode.equalsIgnoreCase("CompanyZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyZipCode));
		}
		if (FCode.equalsIgnoreCase("CompanyAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyAddress));
		}
		if (FCode.equalsIgnoreCase("HomeFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeFax));
		}
		if (FCode.equalsIgnoreCase("HomePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomePhone));
		}
		if (FCode.equalsIgnoreCase("HomeZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeZipCode));
		}
		if (FCode.equalsIgnoreCase("HomeAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddress));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostalAddress));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransTime));
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransOperator));
		}
		if (FCode.equalsIgnoreCase("SocialInsuFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuFlag));
		}
		if (FCode.equalsIgnoreCase("IDExpDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDExpDate));
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
				strFieldValue = StrTool.GBKToUnicode(IDX);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = String.valueOf(InputNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FillNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RelationToMainInsured);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RelationToAppnt);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(SequenceNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Birthday);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MarriageDate);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Stature);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Avoirdupois);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(JoinCompanyDate);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Salary);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(StartWorkDate);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(InsuredStat);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(MakeDate);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(ModifyDate);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(UWDate);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(BMI);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(InsuredPeoples);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(CustomerSeqNo);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuNo);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(SignName);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(CompanyFax);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(CompanyPhone);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(CompanyZipCode);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(CompanyAddress);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(HomeFax);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(HomePhone);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(HomeZipCode);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(TransTime);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(TransOperator);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlag);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(IDExpDate);
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

		if (FCode.equalsIgnoreCase("IDX"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDX = FValue.trim();
			}
			else
				IDX = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InputNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FillNo = FValue.trim();
			}
			else
				FillNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExecuteCom = FValue.trim();
			}
			else
				ExecuteCom = null;
		}
		if (FCode.equalsIgnoreCase("FamilyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyID = FValue.trim();
			}
			else
				FamilyID = null;
		}
		if (FCode.equalsIgnoreCase("RelationToMainInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToMainInsured = FValue.trim();
			}
			else
				RelationToMainInsured = null;
		}
		if (FCode.equalsIgnoreCase("RelationToAppnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToAppnt = FValue.trim();
			}
			else
				RelationToAppnt = null;
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
		if (FCode.equalsIgnoreCase("SequenceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SequenceNo = FValue.trim();
			}
			else
				SequenceNo = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				Birthday = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				MarriageDate = FValue.trim();
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
				Stature = FValue.trim();
			}
			else
				Stature = null;
		}
		if (FCode.equalsIgnoreCase("Avoirdupois"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Avoirdupois = FValue.trim();
			}
			else
				Avoirdupois = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				JoinCompanyDate = FValue.trim();
			}
			else
				JoinCompanyDate = null;
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
				Salary = FValue.trim();
			}
			else
				Salary = null;
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
		if (FCode.equalsIgnoreCase("StartWorkDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartWorkDate = FValue.trim();
			}
			else
				StartWorkDate = null;
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
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
		if (FCode.equalsIgnoreCase("InsuredStat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredStat = FValue.trim();
			}
			else
				InsuredStat = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ModifyDate = FValue.trim();
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
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWDate = FValue.trim();
			}
			else
				UWDate = null;
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWTime = FValue.trim();
			}
			else
				UWTime = null;
		}
		if (FCode.equalsIgnoreCase("BMI"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BMI = FValue.trim();
			}
			else
				BMI = null;
		}
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredPeoples = FValue.trim();
			}
			else
				InsuredPeoples = null;
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
		if (FCode.equalsIgnoreCase("CustomerSeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerSeqNo = FValue.trim();
			}
			else
				CustomerSeqNo = null;
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
		if (FCode.equalsIgnoreCase("SignName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignName = FValue.trim();
			}
			else
				SignName = null;
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
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
		}
		if (FCode.equalsIgnoreCase("CompanyFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyFax = FValue.trim();
			}
			else
				CompanyFax = null;
		}
		if (FCode.equalsIgnoreCase("CompanyPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyPhone = FValue.trim();
			}
			else
				CompanyPhone = null;
		}
		if (FCode.equalsIgnoreCase("CompanyZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyZipCode = FValue.trim();
			}
			else
				CompanyZipCode = null;
		}
		if (FCode.equalsIgnoreCase("CompanyAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyAddress = FValue.trim();
			}
			else
				CompanyAddress = null;
		}
		if (FCode.equalsIgnoreCase("HomeFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeFax = FValue.trim();
			}
			else
				HomeFax = null;
		}
		if (FCode.equalsIgnoreCase("HomePhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomePhone = FValue.trim();
			}
			else
				HomePhone = null;
		}
		if (FCode.equalsIgnoreCase("HomeZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeZipCode = FValue.trim();
			}
			else
				HomeZipCode = null;
		}
		if (FCode.equalsIgnoreCase("HomeAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeAddress = FValue.trim();
			}
			else
				HomeAddress = null;
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
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
		}
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostalAddress = FValue.trim();
			}
			else
				PostalAddress = null;
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransTime = FValue.trim();
			}
			else
				TransTime = null;
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransOperator = FValue.trim();
			}
			else
				TransOperator = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				IDExpDate = FValue.trim();
			}
			else
				IDExpDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPOInsuredBSchema other = (LBPOInsuredBSchema)otherObject;
		return
			IDX.equals(other.getIDX())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& InputNo == other.getInputNo()
			&& FillNo.equals(other.getFillNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& PrtNo.equals(other.getPrtNo())
			&& AppntNo.equals(other.getAppntNo())
			&& ManageCom.equals(other.getManageCom())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& FamilyID.equals(other.getFamilyID())
			&& RelationToMainInsured.equals(other.getRelationToMainInsured())
			&& RelationToAppnt.equals(other.getRelationToAppnt())
			&& AddressNo.equals(other.getAddressNo())
			&& SequenceNo.equals(other.getSequenceNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& Birthday.equals(other.getBirthday())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& NativePlace.equals(other.getNativePlace())
			&& Nationality.equals(other.getNationality())
			&& RgtAddress.equals(other.getRgtAddress())
			&& Marriage.equals(other.getMarriage())
			&& MarriageDate.equals(other.getMarriageDate())
			&& Health.equals(other.getHealth())
			&& Stature.equals(other.getStature())
			&& Avoirdupois.equals(other.getAvoirdupois())
			&& Degree.equals(other.getDegree())
			&& CreditGrade.equals(other.getCreditGrade())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& JoinCompanyDate.equals(other.getJoinCompanyDate())
			&& Position.equals(other.getPosition())
			&& Salary.equals(other.getSalary())
			&& OccupationType.equals(other.getOccupationType())
			&& StartWorkDate.equals(other.getStartWorkDate())
			&& OccupationCode.equals(other.getOccupationCode())
			&& WorkType.equals(other.getWorkType())
			&& PluralityType.equals(other.getPluralityType())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& Operator.equals(other.getOperator())
			&& InsuredStat.equals(other.getInsuredStat())
			&& MakeDate.equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate.equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode())
			&& UWDate.equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& BMI.equals(other.getBMI())
			&& InsuredPeoples.equals(other.getInsuredPeoples())
			&& License.equals(other.getLicense())
			&& LicenseType.equals(other.getLicenseType())
			&& CustomerSeqNo.equals(other.getCustomerSeqNo())
			&& SocialInsuNo.equals(other.getSocialInsuNo())
			&& SignName.equals(other.getSignName())
			&& EMail.equals(other.getEMail())
			&& Mobile.equals(other.getMobile())
			&& CompanyFax.equals(other.getCompanyFax())
			&& CompanyPhone.equals(other.getCompanyPhone())
			&& CompanyZipCode.equals(other.getCompanyZipCode())
			&& CompanyAddress.equals(other.getCompanyAddress())
			&& HomeFax.equals(other.getHomeFax())
			&& HomePhone.equals(other.getHomePhone())
			&& HomeZipCode.equals(other.getHomeZipCode())
			&& HomeAddress.equals(other.getHomeAddress())
			&& Fax.equals(other.getFax())
			&& Phone.equals(other.getPhone())
			&& ZipCode.equals(other.getZipCode())
			&& PostalAddress.equals(other.getPostalAddress())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& TransTime.equals(other.getTransTime())
			&& TransOperator.equals(other.getTransOperator())
			&& SocialInsuFlag.equals(other.getSocialInsuFlag())
			&& IDExpDate.equals(other.getIDExpDate());
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
		if( strFieldName.equals("IDX") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("InputNo") ) {
			return 3;
		}
		if( strFieldName.equals("FillNo") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 5;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 6;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 9;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 10;
		}
		if( strFieldName.equals("RelationToMainInsured") ) {
			return 11;
		}
		if( strFieldName.equals("RelationToAppnt") ) {
			return 12;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 13;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return 14;
		}
		if( strFieldName.equals("Name") ) {
			return 15;
		}
		if( strFieldName.equals("Sex") ) {
			return 16;
		}
		if( strFieldName.equals("Birthday") ) {
			return 17;
		}
		if( strFieldName.equals("IDType") ) {
			return 18;
		}
		if( strFieldName.equals("IDNo") ) {
			return 19;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 20;
		}
		if( strFieldName.equals("Nationality") ) {
			return 21;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 22;
		}
		if( strFieldName.equals("Marriage") ) {
			return 23;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 24;
		}
		if( strFieldName.equals("Health") ) {
			return 25;
		}
		if( strFieldName.equals("Stature") ) {
			return 26;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 27;
		}
		if( strFieldName.equals("Degree") ) {
			return 28;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 29;
		}
		if( strFieldName.equals("BankCode") ) {
			return 30;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 31;
		}
		if( strFieldName.equals("AccName") ) {
			return 32;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 33;
		}
		if( strFieldName.equals("Position") ) {
			return 34;
		}
		if( strFieldName.equals("Salary") ) {
			return 35;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 36;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 37;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 38;
		}
		if( strFieldName.equals("WorkType") ) {
			return 39;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 40;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 41;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 42;
		}
		if( strFieldName.equals("Operator") ) {
			return 43;
		}
		if( strFieldName.equals("InsuredStat") ) {
			return 44;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 45;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 46;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 47;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 48;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 49;
		}
		if( strFieldName.equals("UWCode") ) {
			return 50;
		}
		if( strFieldName.equals("UWDate") ) {
			return 51;
		}
		if( strFieldName.equals("UWTime") ) {
			return 52;
		}
		if( strFieldName.equals("BMI") ) {
			return 53;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 54;
		}
		if( strFieldName.equals("License") ) {
			return 55;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 56;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
			return 57;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return 58;
		}
		if( strFieldName.equals("SignName") ) {
			return 59;
		}
		if( strFieldName.equals("EMail") ) {
			return 60;
		}
		if( strFieldName.equals("Mobile") ) {
			return 61;
		}
		if( strFieldName.equals("CompanyFax") ) {
			return 62;
		}
		if( strFieldName.equals("CompanyPhone") ) {
			return 63;
		}
		if( strFieldName.equals("CompanyZipCode") ) {
			return 64;
		}
		if( strFieldName.equals("CompanyAddress") ) {
			return 65;
		}
		if( strFieldName.equals("HomeFax") ) {
			return 66;
		}
		if( strFieldName.equals("HomePhone") ) {
			return 67;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return 68;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 69;
		}
		if( strFieldName.equals("Fax") ) {
			return 70;
		}
		if( strFieldName.equals("Phone") ) {
			return 71;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 72;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 73;
		}
		if( strFieldName.equals("TransDate") ) {
			return 74;
		}
		if( strFieldName.equals("TransTime") ) {
			return 75;
		}
		if( strFieldName.equals("TransOperator") ) {
			return 76;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return 77;
		}
		if( strFieldName.equals("IDExpDate") ) {
			return 78;
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
				strFieldName = "IDX";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "InputNo";
				break;
			case 4:
				strFieldName = "FillNo";
				break;
			case 5:
				strFieldName = "InsuredNo";
				break;
			case 6:
				strFieldName = "PrtNo";
				break;
			case 7:
				strFieldName = "AppntNo";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "ExecuteCom";
				break;
			case 10:
				strFieldName = "FamilyID";
				break;
			case 11:
				strFieldName = "RelationToMainInsured";
				break;
			case 12:
				strFieldName = "RelationToAppnt";
				break;
			case 13:
				strFieldName = "AddressNo";
				break;
			case 14:
				strFieldName = "SequenceNo";
				break;
			case 15:
				strFieldName = "Name";
				break;
			case 16:
				strFieldName = "Sex";
				break;
			case 17:
				strFieldName = "Birthday";
				break;
			case 18:
				strFieldName = "IDType";
				break;
			case 19:
				strFieldName = "IDNo";
				break;
			case 20:
				strFieldName = "NativePlace";
				break;
			case 21:
				strFieldName = "Nationality";
				break;
			case 22:
				strFieldName = "RgtAddress";
				break;
			case 23:
				strFieldName = "Marriage";
				break;
			case 24:
				strFieldName = "MarriageDate";
				break;
			case 25:
				strFieldName = "Health";
				break;
			case 26:
				strFieldName = "Stature";
				break;
			case 27:
				strFieldName = "Avoirdupois";
				break;
			case 28:
				strFieldName = "Degree";
				break;
			case 29:
				strFieldName = "CreditGrade";
				break;
			case 30:
				strFieldName = "BankCode";
				break;
			case 31:
				strFieldName = "BankAccNo";
				break;
			case 32:
				strFieldName = "AccName";
				break;
			case 33:
				strFieldName = "JoinCompanyDate";
				break;
			case 34:
				strFieldName = "Position";
				break;
			case 35:
				strFieldName = "Salary";
				break;
			case 36:
				strFieldName = "OccupationType";
				break;
			case 37:
				strFieldName = "StartWorkDate";
				break;
			case 38:
				strFieldName = "OccupationCode";
				break;
			case 39:
				strFieldName = "WorkType";
				break;
			case 40:
				strFieldName = "PluralityType";
				break;
			case 41:
				strFieldName = "SmokeFlag";
				break;
			case 42:
				strFieldName = "ContPlanCode";
				break;
			case 43:
				strFieldName = "Operator";
				break;
			case 44:
				strFieldName = "InsuredStat";
				break;
			case 45:
				strFieldName = "MakeDate";
				break;
			case 46:
				strFieldName = "MakeTime";
				break;
			case 47:
				strFieldName = "ModifyDate";
				break;
			case 48:
				strFieldName = "ModifyTime";
				break;
			case 49:
				strFieldName = "UWFlag";
				break;
			case 50:
				strFieldName = "UWCode";
				break;
			case 51:
				strFieldName = "UWDate";
				break;
			case 52:
				strFieldName = "UWTime";
				break;
			case 53:
				strFieldName = "BMI";
				break;
			case 54:
				strFieldName = "InsuredPeoples";
				break;
			case 55:
				strFieldName = "License";
				break;
			case 56:
				strFieldName = "LicenseType";
				break;
			case 57:
				strFieldName = "CustomerSeqNo";
				break;
			case 58:
				strFieldName = "SocialInsuNo";
				break;
			case 59:
				strFieldName = "SignName";
				break;
			case 60:
				strFieldName = "EMail";
				break;
			case 61:
				strFieldName = "Mobile";
				break;
			case 62:
				strFieldName = "CompanyFax";
				break;
			case 63:
				strFieldName = "CompanyPhone";
				break;
			case 64:
				strFieldName = "CompanyZipCode";
				break;
			case 65:
				strFieldName = "CompanyAddress";
				break;
			case 66:
				strFieldName = "HomeFax";
				break;
			case 67:
				strFieldName = "HomePhone";
				break;
			case 68:
				strFieldName = "HomeZipCode";
				break;
			case 69:
				strFieldName = "HomeAddress";
				break;
			case 70:
				strFieldName = "Fax";
				break;
			case 71:
				strFieldName = "Phone";
				break;
			case 72:
				strFieldName = "ZipCode";
				break;
			case 73:
				strFieldName = "PostalAddress";
				break;
			case 74:
				strFieldName = "TransDate";
				break;
			case 75:
				strFieldName = "TransTime";
				break;
			case 76:
				strFieldName = "TransOperator";
				break;
			case 77:
				strFieldName = "SocialInsuFlag";
				break;
			case 78:
				strFieldName = "IDExpDate";
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
		if( strFieldName.equals("IDX") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToMainInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToAppnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Health") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Stature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Position") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Salary") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartWorkDate") ) {
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
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredStat") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BMI") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDExpDate") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 75:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
