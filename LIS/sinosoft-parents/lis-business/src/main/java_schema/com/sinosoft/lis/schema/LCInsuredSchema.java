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
import com.sinosoft.lis.db.LCInsuredDB;

/*
 * <p>ClassName: LCInsuredSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCInsuredSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCInsuredSchema.class);
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
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
	private Date Birthday;
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
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 操作员 */
	private String Operator;
	/** 被保人状态 */
	private String InsuredStat;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 核保状态 */
	private String UWFlag;
	/** 最终核保人编码 */
	private String UWCode;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 身体指标 */
	private double BMI;
	/** 被保人数目 */
	private int InsuredPeoples;
	/** 驾照 */
	private String License;
	/** 驾照类型 */
	private String LicenseType;
	/** 团体客户序号 */
	private int CustomerSeqNo;
	/** 社保登记号 */
	private String SocialInsuNo;
	/** 短消息标志 */
	private String SendMsgFlag;
	/** 邮件标志 */
	private String SendMailFlag;
	/** 加入保单日期 */
	private Date JoinContDate;
	/** 工作证号码 */
	private String WorkNo;
	/** 卡单编码 */
	private String CertifyCode;
	/** 卡单起号 */
	private String StartCode;
	/** 卡单止号 */
	private String EndCode;
	/** 是否有社保标志 */
	private String SocialInsuFlag;
	/** 证件有效期 */
	private Date IDExpDate;
	/** 子公司号码 */
	private String GrpNo;
	/** 组织机构代码 */
	private String OrganComCode;
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
	/** 主被保人客户号 */
	private String MainCustomerNo;
	/** 被保险人类型 */
	private String InsuredType;
	/** 证件是否长期 */
	private String IsLongValid;
	/** 证件开始日期 */
	private Date IDStartDate;
	/** 被保人投保年龄 */
	private int InsuredAppAge;
	/** 工龄 */
	private double Seniority;
	/** 工作单位 */
	private String WorkCompName;
	/** 保障等级方案编码 */
	private String PlanCode;
	/** 死亡日期 */
	private Date DeathDate;
	/** 黑名单标识 */
	private String BlacklistFlag;
	/** Vip值 */
	private String VIPValue;
	/** Qq信息标识 */
	private String SendQQFlag;
	/** Msn信息标识 */
	private String SendMSNFlag;
	/** 服务区域 */
	private String ServerArea;
	/** 客户群编码 */
	private String SubCustomerNo;
	/** 是否次标准体 */
	private String Substandard;
	/** 备注 */
	private String Remark;
	/** 套餐代码 */
	private String PakageCode;
	/** 前往国家/地区 */
	private String Destination;
	/** 出行目的 */
	private String Purpose;
	/** 紧急联系人 */
	private String EmergencyContact;
	/** 紧急联系人电话 */
	private String EmerContPhone;
	/** 承保天数 */
	private String ApplytoDay;
	/** 有无生育保险 */
	private String MaternityFlag;
	/** 急难援助卡 */
	private String RescueType;
	/** 所在部门 */
	private String DeptCode;
	/** 所在分公司 */
	private String SubCompanyCode;
	/** 被保险人编码 */
	private String InsureCode;
	/** 工作地 */
	private String WorkAddress;
	/** 社保地 */
	private String SocialInsuAddress;
	/** 来源保单号 */
	private String OriginContNo;
	/** 来源客户号 */
	private String OriginInsuredNo;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 109;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCInsuredSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "InsuredNo";

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
		LCInsuredSchema cloned = (LCInsuredSchema)super.clone();
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
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
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
		if(aExecuteCom!=null && aExecuteCom.length()>10)
			throw new IllegalArgumentException("处理机构ExecuteCom值"+aExecuteCom+"的长度"+aExecuteCom.length()+"大于最大值10");
		ExecuteCom = aExecuteCom;
	}
	public String getFamilyID()
	{
		return FamilyID;
	}
	public void setFamilyID(String aFamilyID)
	{
		if(aFamilyID!=null && aFamilyID.length()>20)
			throw new IllegalArgumentException("家庭保障号FamilyID值"+aFamilyID+"的长度"+aFamilyID.length()+"大于最大值20");
		FamilyID = aFamilyID;
	}
	public String getRelationToMainInsured()
	{
		return RelationToMainInsured;
	}
	public void setRelationToMainInsured(String aRelationToMainInsured)
	{
		if(aRelationToMainInsured!=null && aRelationToMainInsured.length()>2)
			throw new IllegalArgumentException("与主被保人关系RelationToMainInsured值"+aRelationToMainInsured+"的长度"+aRelationToMainInsured.length()+"大于最大值2");
		RelationToMainInsured = aRelationToMainInsured;
	}
	public String getRelationToAppnt()
	{
		return RelationToAppnt;
	}
	public void setRelationToAppnt(String aRelationToAppnt)
	{
		if(aRelationToAppnt!=null && aRelationToAppnt.length()>2)
			throw new IllegalArgumentException("与投保人关系RelationToAppnt值"+aRelationToAppnt+"的长度"+aRelationToAppnt.length()+"大于最大值2");
		RelationToAppnt = aRelationToAppnt;
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
	* 客户分类的号码
	*/
	public String getSequenceNo()
	{
		return SequenceNo;
	}
	public void setSequenceNo(String aSequenceNo)
	{
		if(aSequenceNo!=null && aSequenceNo.length()>20)
			throw new IllegalArgumentException("客户内部号码SequenceNo值"+aSequenceNo+"的长度"+aSequenceNo.length()+"大于最大值20");
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
		if(aSex!=null && aSex.length()>1)
			throw new IllegalArgumentException("被保人性别Sex值"+aSex+"的长度"+aSex.length()+"大于最大值1");
		Sex = aSex;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
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
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		if(aContPlanCode!=null && aContPlanCode.length()>20)
			throw new IllegalArgumentException("保险计划编码ContPlanCode值"+aContPlanCode+"的长度"+aContPlanCode.length()+"大于最大值20");
		ContPlanCode = aContPlanCode;
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
		if(aInsuredStat!=null && aInsuredStat.length()>2)
			throw new IllegalArgumentException("被保人状态InsuredStat值"+aInsuredStat+"的长度"+aInsuredStat.length()+"大于最大值2");
		InsuredStat = aInsuredStat;
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
	/**
	* 1 拒保<p>
	* 2 延期<p>
	* 3 条件承保<p>
	* 4 通融<p>
	* 5 自动<p>
	* 6 待上级<p>
	* 7 问题件<p>
	* 8 核保通??书<p>
	* 9 正常<p>
	* a 撤单<p>
	* b ??险计划变更<p>
	* z 核保订正
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>1)
			throw new IllegalArgumentException("核保状态UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值1");
		UWFlag = aUWFlag;
	}
	public String getUWCode()
	{
		return UWCode;
	}
	public void setUWCode(String aUWCode)
	{
		if(aUWCode!=null && aUWCode.length()>10)
			throw new IllegalArgumentException("最终核保人编码UWCode值"+aUWCode+"的长度"+aUWCode.length()+"大于最大值10");
		UWCode = aUWCode;
	}
	public String getUWDate()
	{
		if( UWDate != null )
			return fDate.getString(UWDate);
		else
			return null;
	}
	public void setUWDate(Date aUWDate)
	{
		UWDate = aUWDate;
	}
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
		if(aUWTime!=null && aUWTime.length()>8)
			throw new IllegalArgumentException("核保完成时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值8");
		UWTime = aUWTime;
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

	public int getInsuredPeoples()
	{
		return InsuredPeoples;
	}
	public void setInsuredPeoples(int aInsuredPeoples)
	{
		InsuredPeoples = aInsuredPeoples;
	}
	public void setInsuredPeoples(String aInsuredPeoples)
	{
		if (aInsuredPeoples != null && !aInsuredPeoples.equals(""))
		{
			Integer tInteger = new Integer(aInsuredPeoples);
			int i = tInteger.intValue();
			InsuredPeoples = i;
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
	public int getCustomerSeqNo()
	{
		return CustomerSeqNo;
	}
	public void setCustomerSeqNo(int aCustomerSeqNo)
	{
		CustomerSeqNo = aCustomerSeqNo;
	}
	public void setCustomerSeqNo(String aCustomerSeqNo)
	{
		if (aCustomerSeqNo != null && !aCustomerSeqNo.equals(""))
		{
			Integer tInteger = new Integer(aCustomerSeqNo);
			int i = tInteger.intValue();
			CustomerSeqNo = i;
		}
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
	public String getSendMsgFlag()
	{
		return SendMsgFlag;
	}
	public void setSendMsgFlag(String aSendMsgFlag)
	{
		if(aSendMsgFlag!=null && aSendMsgFlag.length()>10)
			throw new IllegalArgumentException("短消息标志SendMsgFlag值"+aSendMsgFlag+"的长度"+aSendMsgFlag.length()+"大于最大值10");
		SendMsgFlag = aSendMsgFlag;
	}
	public String getSendMailFlag()
	{
		return SendMailFlag;
	}
	public void setSendMailFlag(String aSendMailFlag)
	{
		if(aSendMailFlag!=null && aSendMailFlag.length()>10)
			throw new IllegalArgumentException("邮件标志SendMailFlag值"+aSendMailFlag+"的长度"+aSendMailFlag.length()+"大于最大值10");
		SendMailFlag = aSendMailFlag;
	}
	public String getJoinContDate()
	{
		if( JoinContDate != null )
			return fDate.getString(JoinContDate);
		else
			return null;
	}
	public void setJoinContDate(Date aJoinContDate)
	{
		JoinContDate = aJoinContDate;
	}
	public void setJoinContDate(String aJoinContDate)
	{
		if (aJoinContDate != null && !aJoinContDate.equals("") )
		{
			JoinContDate = fDate.getDate( aJoinContDate );
		}
		else
			JoinContDate = null;
	}

	public String getWorkNo()
	{
		return WorkNo;
	}
	public void setWorkNo(String aWorkNo)
	{
		if(aWorkNo!=null && aWorkNo.length()>10)
			throw new IllegalArgumentException("工作证号码WorkNo值"+aWorkNo+"的长度"+aWorkNo.length()+"大于最大值10");
		WorkNo = aWorkNo;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		if(aCertifyCode!=null && aCertifyCode.length()>10)
			throw new IllegalArgumentException("卡单编码CertifyCode值"+aCertifyCode+"的长度"+aCertifyCode.length()+"大于最大值10");
		CertifyCode = aCertifyCode;
	}
	public String getStartCode()
	{
		return StartCode;
	}
	public void setStartCode(String aStartCode)
	{
		if(aStartCode!=null && aStartCode.length()>20)
			throw new IllegalArgumentException("卡单起号StartCode值"+aStartCode+"的长度"+aStartCode.length()+"大于最大值20");
		StartCode = aStartCode;
	}
	public String getEndCode()
	{
		return EndCode;
	}
	public void setEndCode(String aEndCode)
	{
		if(aEndCode!=null && aEndCode.length()>20)
			throw new IllegalArgumentException("卡单止号EndCode值"+aEndCode+"的长度"+aEndCode.length()+"大于最大值20");
		EndCode = aEndCode;
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

	public String getGrpNo()
	{
		return GrpNo;
	}
	public void setGrpNo(String aGrpNo)
	{
		if(aGrpNo!=null && aGrpNo.length()>20)
			throw new IllegalArgumentException("子公司号码GrpNo值"+aGrpNo+"的长度"+aGrpNo.length()+"大于最大值20");
		GrpNo = aGrpNo;
	}
	public String getOrganComCode()
	{
		return OrganComCode;
	}
	public void setOrganComCode(String aOrganComCode)
	{
		if(aOrganComCode!=null && aOrganComCode.length()>30)
			throw new IllegalArgumentException("组织机构代码OrganComCode值"+aOrganComCode+"的长度"+aOrganComCode.length()+"大于最大值30");
		OrganComCode = aOrganComCode;
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
	public String getMainCustomerNo()
	{
		return MainCustomerNo;
	}
	public void setMainCustomerNo(String aMainCustomerNo)
	{
		if(aMainCustomerNo!=null && aMainCustomerNo.length()>20)
			throw new IllegalArgumentException("主被保人客户号MainCustomerNo值"+aMainCustomerNo+"的长度"+aMainCustomerNo.length()+"大于最大值20");
		MainCustomerNo = aMainCustomerNo;
	}
	public String getInsuredType()
	{
		return InsuredType;
	}
	public void setInsuredType(String aInsuredType)
	{
		if(aInsuredType!=null && aInsuredType.length()>2)
			throw new IllegalArgumentException("被保险人类型InsuredType值"+aInsuredType+"的长度"+aInsuredType.length()+"大于最大值2");
		InsuredType = aInsuredType;
	}
	public String getIsLongValid()
	{
		return IsLongValid;
	}
	public void setIsLongValid(String aIsLongValid)
	{
		if(aIsLongValid!=null && aIsLongValid.length()>2)
			throw new IllegalArgumentException("证件是否长期IsLongValid值"+aIsLongValid+"的长度"+aIsLongValid.length()+"大于最大值2");
		IsLongValid = aIsLongValid;
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

	public int getInsuredAppAge()
	{
		return InsuredAppAge;
	}
	public void setInsuredAppAge(int aInsuredAppAge)
	{
		InsuredAppAge = aInsuredAppAge;
	}
	public void setInsuredAppAge(String aInsuredAppAge)
	{
		if (aInsuredAppAge != null && !aInsuredAppAge.equals(""))
		{
			Integer tInteger = new Integer(aInsuredAppAge);
			int i = tInteger.intValue();
			InsuredAppAge = i;
		}
	}

	public double getSeniority()
	{
		return Seniority;
	}
	public void setSeniority(double aSeniority)
	{
		Seniority = aSeniority;
	}
	public void setSeniority(String aSeniority)
	{
		if (aSeniority != null && !aSeniority.equals(""))
		{
			Double tDouble = new Double(aSeniority);
			double d = tDouble.doubleValue();
			Seniority = d;
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
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>20)
			throw new IllegalArgumentException("保障等级方案编码PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值20");
		PlanCode = aPlanCode;
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
	public String getServerArea()
	{
		return ServerArea;
	}
	public void setServerArea(String aServerArea)
	{
		if(aServerArea!=null && aServerArea.length()>10)
			throw new IllegalArgumentException("服务区域ServerArea值"+aServerArea+"的长度"+aServerArea.length()+"大于最大值10");
		ServerArea = aServerArea;
	}
	public String getSubCustomerNo()
	{
		return SubCustomerNo;
	}
	public void setSubCustomerNo(String aSubCustomerNo)
	{
		if(aSubCustomerNo!=null && aSubCustomerNo.length()>20)
			throw new IllegalArgumentException("客户群编码SubCustomerNo值"+aSubCustomerNo+"的长度"+aSubCustomerNo.length()+"大于最大值20");
		SubCustomerNo = aSubCustomerNo;
	}
	public String getSubstandard()
	{
		return Substandard;
	}
	public void setSubstandard(String aSubstandard)
	{
		if(aSubstandard!=null && aSubstandard.length()>10)
			throw new IllegalArgumentException("是否次标准体Substandard值"+aSubstandard+"的长度"+aSubstandard.length()+"大于最大值10");
		Substandard = aSubstandard;
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
	public String getPakageCode()
	{
		return PakageCode;
	}
	public void setPakageCode(String aPakageCode)
	{
		if(aPakageCode!=null && aPakageCode.length()>20)
			throw new IllegalArgumentException("套餐代码PakageCode值"+aPakageCode+"的长度"+aPakageCode.length()+"大于最大值20");
		PakageCode = aPakageCode;
	}
	public String getDestination()
	{
		return Destination;
	}
	public void setDestination(String aDestination)
	{
		if(aDestination!=null && aDestination.length()>600)
			throw new IllegalArgumentException("前往国家/地区Destination值"+aDestination+"的长度"+aDestination.length()+"大于最大值600");
		Destination = aDestination;
	}
	public String getPurpose()
	{
		return Purpose;
	}
	public void setPurpose(String aPurpose)
	{
		if(aPurpose!=null && aPurpose.length()>10)
			throw new IllegalArgumentException("出行目的Purpose值"+aPurpose+"的长度"+aPurpose.length()+"大于最大值10");
		Purpose = aPurpose;
	}
	public String getEmergencyContact()
	{
		return EmergencyContact;
	}
	public void setEmergencyContact(String aEmergencyContact)
	{
		if(aEmergencyContact!=null && aEmergencyContact.length()>200)
			throw new IllegalArgumentException("紧急联系人EmergencyContact值"+aEmergencyContact+"的长度"+aEmergencyContact.length()+"大于最大值200");
		EmergencyContact = aEmergencyContact;
	}
	public String getEmerContPhone()
	{
		return EmerContPhone;
	}
	public void setEmerContPhone(String aEmerContPhone)
	{
		if(aEmerContPhone!=null && aEmerContPhone.length()>100)
			throw new IllegalArgumentException("紧急联系人电话EmerContPhone值"+aEmerContPhone+"的长度"+aEmerContPhone.length()+"大于最大值100");
		EmerContPhone = aEmerContPhone;
	}
	public String getApplytoDay()
	{
		return ApplytoDay;
	}
	public void setApplytoDay(String aApplytoDay)
	{
		if(aApplytoDay!=null && aApplytoDay.length()>10)
			throw new IllegalArgumentException("承保天数ApplytoDay值"+aApplytoDay+"的长度"+aApplytoDay.length()+"大于最大值10");
		ApplytoDay = aApplytoDay;
	}
	public String getMaternityFlag()
	{
		return MaternityFlag;
	}
	public void setMaternityFlag(String aMaternityFlag)
	{
		if(aMaternityFlag!=null && aMaternityFlag.length()>1)
			throw new IllegalArgumentException("有无生育保险MaternityFlag值"+aMaternityFlag+"的长度"+aMaternityFlag.length()+"大于最大值1");
		MaternityFlag = aMaternityFlag;
	}
	public String getRescueType()
	{
		return RescueType;
	}
	public void setRescueType(String aRescueType)
	{
		if(aRescueType!=null && aRescueType.length()>1)
			throw new IllegalArgumentException("急难援助卡RescueType值"+aRescueType+"的长度"+aRescueType.length()+"大于最大值1");
		RescueType = aRescueType;
	}
	public String getDeptCode()
	{
		return DeptCode;
	}
	public void setDeptCode(String aDeptCode)
	{
		if(aDeptCode!=null && aDeptCode.length()>300)
			throw new IllegalArgumentException("所在部门DeptCode值"+aDeptCode+"的长度"+aDeptCode.length()+"大于最大值300");
		DeptCode = aDeptCode;
	}
	public String getSubCompanyCode()
	{
		return SubCompanyCode;
	}
	public void setSubCompanyCode(String aSubCompanyCode)
	{
		if(aSubCompanyCode!=null && aSubCompanyCode.length()>200)
			throw new IllegalArgumentException("所在分公司SubCompanyCode值"+aSubCompanyCode+"的长度"+aSubCompanyCode.length()+"大于最大值200");
		SubCompanyCode = aSubCompanyCode;
	}
	public String getInsureCode()
	{
		return InsureCode;
	}
	public void setInsureCode(String aInsureCode)
	{
		if(aInsureCode!=null && aInsureCode.length()>100)
			throw new IllegalArgumentException("被保险人编码InsureCode值"+aInsureCode+"的长度"+aInsureCode.length()+"大于最大值100");
		InsureCode = aInsureCode;
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
	public String getOriginContNo()
	{
		return OriginContNo;
	}
	public void setOriginContNo(String aOriginContNo)
	{
		if(aOriginContNo!=null && aOriginContNo.length()>20)
			throw new IllegalArgumentException("来源保单号OriginContNo值"+aOriginContNo+"的长度"+aOriginContNo.length()+"大于最大值20");
		OriginContNo = aOriginContNo;
	}
	public String getOriginInsuredNo()
	{
		return OriginInsuredNo;
	}
	public void setOriginInsuredNo(String aOriginInsuredNo)
	{
		if(aOriginInsuredNo!=null && aOriginInsuredNo.length()>20)
			throw new IllegalArgumentException("来源客户号OriginInsuredNo值"+aOriginInsuredNo+"的长度"+aOriginInsuredNo.length()+"大于最大值20");
		OriginInsuredNo = aOriginInsuredNo;
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
	* 使用另外一个 LCInsuredSchema 对象给 Schema 赋值
	* @param: aLCInsuredSchema LCInsuredSchema
	**/
	public void setSchema(LCInsuredSchema aLCInsuredSchema)
	{
		this.GrpContNo = aLCInsuredSchema.getGrpContNo();
		this.ContNo = aLCInsuredSchema.getContNo();
		this.InsuredNo = aLCInsuredSchema.getInsuredNo();
		this.PrtNo = aLCInsuredSchema.getPrtNo();
		this.AppntNo = aLCInsuredSchema.getAppntNo();
		this.ManageCom = aLCInsuredSchema.getManageCom();
		this.ExecuteCom = aLCInsuredSchema.getExecuteCom();
		this.FamilyID = aLCInsuredSchema.getFamilyID();
		this.RelationToMainInsured = aLCInsuredSchema.getRelationToMainInsured();
		this.RelationToAppnt = aLCInsuredSchema.getRelationToAppnt();
		this.AddressNo = aLCInsuredSchema.getAddressNo();
		this.SequenceNo = aLCInsuredSchema.getSequenceNo();
		this.Name = aLCInsuredSchema.getName();
		this.Sex = aLCInsuredSchema.getSex();
		this.Birthday = fDate.getDate( aLCInsuredSchema.getBirthday());
		this.IDType = aLCInsuredSchema.getIDType();
		this.IDNo = aLCInsuredSchema.getIDNo();
		this.NativePlace = aLCInsuredSchema.getNativePlace();
		this.Nationality = aLCInsuredSchema.getNationality();
		this.RgtAddress = aLCInsuredSchema.getRgtAddress();
		this.Marriage = aLCInsuredSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLCInsuredSchema.getMarriageDate());
		this.Health = aLCInsuredSchema.getHealth();
		this.Stature = aLCInsuredSchema.getStature();
		this.Avoirdupois = aLCInsuredSchema.getAvoirdupois();
		this.Degree = aLCInsuredSchema.getDegree();
		this.CreditGrade = aLCInsuredSchema.getCreditGrade();
		this.BankCode = aLCInsuredSchema.getBankCode();
		this.BankAccNo = aLCInsuredSchema.getBankAccNo();
		this.AccName = aLCInsuredSchema.getAccName();
		this.JoinCompanyDate = fDate.getDate( aLCInsuredSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLCInsuredSchema.getStartWorkDate());
		this.Position = aLCInsuredSchema.getPosition();
		this.Salary = aLCInsuredSchema.getSalary();
		this.OccupationType = aLCInsuredSchema.getOccupationType();
		this.OccupationCode = aLCInsuredSchema.getOccupationCode();
		this.WorkType = aLCInsuredSchema.getWorkType();
		this.PluralityType = aLCInsuredSchema.getPluralityType();
		this.SmokeFlag = aLCInsuredSchema.getSmokeFlag();
		this.ContPlanCode = aLCInsuredSchema.getContPlanCode();
		this.Operator = aLCInsuredSchema.getOperator();
		this.InsuredStat = aLCInsuredSchema.getInsuredStat();
		this.MakeDate = fDate.getDate( aLCInsuredSchema.getMakeDate());
		this.MakeTime = aLCInsuredSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCInsuredSchema.getModifyDate());
		this.ModifyTime = aLCInsuredSchema.getModifyTime();
		this.UWFlag = aLCInsuredSchema.getUWFlag();
		this.UWCode = aLCInsuredSchema.getUWCode();
		this.UWDate = fDate.getDate( aLCInsuredSchema.getUWDate());
		this.UWTime = aLCInsuredSchema.getUWTime();
		this.BMI = aLCInsuredSchema.getBMI();
		this.InsuredPeoples = aLCInsuredSchema.getInsuredPeoples();
		this.License = aLCInsuredSchema.getLicense();
		this.LicenseType = aLCInsuredSchema.getLicenseType();
		this.CustomerSeqNo = aLCInsuredSchema.getCustomerSeqNo();
		this.SocialInsuNo = aLCInsuredSchema.getSocialInsuNo();
		this.SendMsgFlag = aLCInsuredSchema.getSendMsgFlag();
		this.SendMailFlag = aLCInsuredSchema.getSendMailFlag();
		this.JoinContDate = fDate.getDate( aLCInsuredSchema.getJoinContDate());
		this.WorkNo = aLCInsuredSchema.getWorkNo();
		this.CertifyCode = aLCInsuredSchema.getCertifyCode();
		this.StartCode = aLCInsuredSchema.getStartCode();
		this.EndCode = aLCInsuredSchema.getEndCode();
		this.SocialInsuFlag = aLCInsuredSchema.getSocialInsuFlag();
		this.IDExpDate = fDate.getDate( aLCInsuredSchema.getIDExpDate());
		this.GrpNo = aLCInsuredSchema.getGrpNo();
		this.OrganComCode = aLCInsuredSchema.getOrganComCode();
		this.LastName = aLCInsuredSchema.getLastName();
		this.FirstName = aLCInsuredSchema.getFirstName();
		this.LastNameEn = aLCInsuredSchema.getLastNameEn();
		this.FirstNameEn = aLCInsuredSchema.getFirstNameEn();
		this.NameEn = aLCInsuredSchema.getNameEn();
		this.LastNamePY = aLCInsuredSchema.getLastNamePY();
		this.FirstNamePY = aLCInsuredSchema.getFirstNamePY();
		this.Language = aLCInsuredSchema.getLanguage();
		this.MainCustomerNo = aLCInsuredSchema.getMainCustomerNo();
		this.InsuredType = aLCInsuredSchema.getInsuredType();
		this.IsLongValid = aLCInsuredSchema.getIsLongValid();
		this.IDStartDate = fDate.getDate( aLCInsuredSchema.getIDStartDate());
		this.InsuredAppAge = aLCInsuredSchema.getInsuredAppAge();
		this.Seniority = aLCInsuredSchema.getSeniority();
		this.WorkCompName = aLCInsuredSchema.getWorkCompName();
		this.PlanCode = aLCInsuredSchema.getPlanCode();
		this.DeathDate = fDate.getDate( aLCInsuredSchema.getDeathDate());
		this.BlacklistFlag = aLCInsuredSchema.getBlacklistFlag();
		this.VIPValue = aLCInsuredSchema.getVIPValue();
		this.SendQQFlag = aLCInsuredSchema.getSendQQFlag();
		this.SendMSNFlag = aLCInsuredSchema.getSendMSNFlag();
		this.ServerArea = aLCInsuredSchema.getServerArea();
		this.SubCustomerNo = aLCInsuredSchema.getSubCustomerNo();
		this.Substandard = aLCInsuredSchema.getSubstandard();
		this.Remark = aLCInsuredSchema.getRemark();
		this.PakageCode = aLCInsuredSchema.getPakageCode();
		this.Destination = aLCInsuredSchema.getDestination();
		this.Purpose = aLCInsuredSchema.getPurpose();
		this.EmergencyContact = aLCInsuredSchema.getEmergencyContact();
		this.EmerContPhone = aLCInsuredSchema.getEmerContPhone();
		this.ApplytoDay = aLCInsuredSchema.getApplytoDay();
		this.MaternityFlag = aLCInsuredSchema.getMaternityFlag();
		this.RescueType = aLCInsuredSchema.getRescueType();
		this.DeptCode = aLCInsuredSchema.getDeptCode();
		this.SubCompanyCode = aLCInsuredSchema.getSubCompanyCode();
		this.InsureCode = aLCInsuredSchema.getInsureCode();
		this.WorkAddress = aLCInsuredSchema.getWorkAddress();
		this.SocialInsuAddress = aLCInsuredSchema.getSocialInsuAddress();
		this.OriginContNo = aLCInsuredSchema.getOriginContNo();
		this.OriginInsuredNo = aLCInsuredSchema.getOriginInsuredNo();
		this.ComCode = aLCInsuredSchema.getComCode();
		this.ModifyOperator = aLCInsuredSchema.getModifyOperator();
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

			this.Birthday = rs.getDate("Birthday");
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

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			this.BMI = rs.getDouble("BMI");
			this.InsuredPeoples = rs.getInt("InsuredPeoples");
			if( rs.getString("License") == null )
				this.License = null;
			else
				this.License = rs.getString("License").trim();

			if( rs.getString("LicenseType") == null )
				this.LicenseType = null;
			else
				this.LicenseType = rs.getString("LicenseType").trim();

			this.CustomerSeqNo = rs.getInt("CustomerSeqNo");
			if( rs.getString("SocialInsuNo") == null )
				this.SocialInsuNo = null;
			else
				this.SocialInsuNo = rs.getString("SocialInsuNo").trim();

			if( rs.getString("SendMsgFlag") == null )
				this.SendMsgFlag = null;
			else
				this.SendMsgFlag = rs.getString("SendMsgFlag").trim();

			if( rs.getString("SendMailFlag") == null )
				this.SendMailFlag = null;
			else
				this.SendMailFlag = rs.getString("SendMailFlag").trim();

			this.JoinContDate = rs.getDate("JoinContDate");
			if( rs.getString("WorkNo") == null )
				this.WorkNo = null;
			else
				this.WorkNo = rs.getString("WorkNo").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("StartCode") == null )
				this.StartCode = null;
			else
				this.StartCode = rs.getString("StartCode").trim();

			if( rs.getString("EndCode") == null )
				this.EndCode = null;
			else
				this.EndCode = rs.getString("EndCode").trim();

			if( rs.getString("SocialInsuFlag") == null )
				this.SocialInsuFlag = null;
			else
				this.SocialInsuFlag = rs.getString("SocialInsuFlag").trim();

			this.IDExpDate = rs.getDate("IDExpDate");
			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

			if( rs.getString("OrganComCode") == null )
				this.OrganComCode = null;
			else
				this.OrganComCode = rs.getString("OrganComCode").trim();

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

			if( rs.getString("MainCustomerNo") == null )
				this.MainCustomerNo = null;
			else
				this.MainCustomerNo = rs.getString("MainCustomerNo").trim();

			if( rs.getString("InsuredType") == null )
				this.InsuredType = null;
			else
				this.InsuredType = rs.getString("InsuredType").trim();

			if( rs.getString("IsLongValid") == null )
				this.IsLongValid = null;
			else
				this.IsLongValid = rs.getString("IsLongValid").trim();

			this.IDStartDate = rs.getDate("IDStartDate");
			this.InsuredAppAge = rs.getInt("InsuredAppAge");
			this.Seniority = rs.getDouble("Seniority");
			if( rs.getString("WorkCompName") == null )
				this.WorkCompName = null;
			else
				this.WorkCompName = rs.getString("WorkCompName").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			this.DeathDate = rs.getDate("DeathDate");
			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

			if( rs.getString("VIPValue") == null )
				this.VIPValue = null;
			else
				this.VIPValue = rs.getString("VIPValue").trim();

			if( rs.getString("SendQQFlag") == null )
				this.SendQQFlag = null;
			else
				this.SendQQFlag = rs.getString("SendQQFlag").trim();

			if( rs.getString("SendMSNFlag") == null )
				this.SendMSNFlag = null;
			else
				this.SendMSNFlag = rs.getString("SendMSNFlag").trim();

			if( rs.getString("ServerArea") == null )
				this.ServerArea = null;
			else
				this.ServerArea = rs.getString("ServerArea").trim();

			if( rs.getString("SubCustomerNo") == null )
				this.SubCustomerNo = null;
			else
				this.SubCustomerNo = rs.getString("SubCustomerNo").trim();

			if( rs.getString("Substandard") == null )
				this.Substandard = null;
			else
				this.Substandard = rs.getString("Substandard").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("PakageCode") == null )
				this.PakageCode = null;
			else
				this.PakageCode = rs.getString("PakageCode").trim();

			if( rs.getString("Destination") == null )
				this.Destination = null;
			else
				this.Destination = rs.getString("Destination").trim();

			if( rs.getString("Purpose") == null )
				this.Purpose = null;
			else
				this.Purpose = rs.getString("Purpose").trim();

			if( rs.getString("EmergencyContact") == null )
				this.EmergencyContact = null;
			else
				this.EmergencyContact = rs.getString("EmergencyContact").trim();

			if( rs.getString("EmerContPhone") == null )
				this.EmerContPhone = null;
			else
				this.EmerContPhone = rs.getString("EmerContPhone").trim();

			if( rs.getString("ApplytoDay") == null )
				this.ApplytoDay = null;
			else
				this.ApplytoDay = rs.getString("ApplytoDay").trim();

			if( rs.getString("MaternityFlag") == null )
				this.MaternityFlag = null;
			else
				this.MaternityFlag = rs.getString("MaternityFlag").trim();

			if( rs.getString("RescueType") == null )
				this.RescueType = null;
			else
				this.RescueType = rs.getString("RescueType").trim();

			if( rs.getString("DeptCode") == null )
				this.DeptCode = null;
			else
				this.DeptCode = rs.getString("DeptCode").trim();

			if( rs.getString("SubCompanyCode") == null )
				this.SubCompanyCode = null;
			else
				this.SubCompanyCode = rs.getString("SubCompanyCode").trim();

			if( rs.getString("InsureCode") == null )
				this.InsureCode = null;
			else
				this.InsureCode = rs.getString("InsureCode").trim();

			if( rs.getString("WorkAddress") == null )
				this.WorkAddress = null;
			else
				this.WorkAddress = rs.getString("WorkAddress").trim();

			if( rs.getString("SocialInsuAddress") == null )
				this.SocialInsuAddress = null;
			else
				this.SocialInsuAddress = rs.getString("SocialInsuAddress").trim();

			if( rs.getString("OriginContNo") == null )
				this.OriginContNo = null;
			else
				this.OriginContNo = rs.getString("OriginContNo").trim();

			if( rs.getString("OriginInsuredNo") == null )
				this.OriginInsuredNo = null;
			else
				this.OriginInsuredNo = rs.getString("OriginInsuredNo").trim();

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
			logger.debug("数据库中的LCInsured表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCInsuredSchema getSchema()
	{
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		aLCInsuredSchema.setSchema(this);
		return aLCInsuredSchema;
	}

	public LCInsuredDB getDB()
	{
		LCInsuredDB aDBOper = new LCInsuredDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsured描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredStat)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BMI));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(License)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LicenseType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CustomerSeqNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendMsgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendMailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( JoinContDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDExpDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastNameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstNameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstNamePY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Language)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsLongValid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( IDStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredAppAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Seniority));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkCompName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeathDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendQQFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendMSNFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerArea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Substandard)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PakageCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Destination)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Purpose)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EmergencyContact)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EmerContPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplytoDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MaternityFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RescueType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeptCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCompanyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsureCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriginContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriginInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsured>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RelationToMainInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RelationToAppnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SequenceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			InsuredStat = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			BMI = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			CustomerSeqNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).intValue();
			SocialInsuNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			SendMsgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			SendMailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			JoinContDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59,SysConst.PACKAGESPILTER));
			WorkNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			StartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			EndCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			SocialInsuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			IDExpDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65,SysConst.PACKAGESPILTER));
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			OrganComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			LastName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			FirstName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			LastNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			FirstNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			NameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			LastNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			FirstNamePY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			Language = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			MainCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			InsuredType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			IsLongValid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			IDStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79,SysConst.PACKAGESPILTER));
			InsuredAppAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).intValue();
			Seniority = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).doubleValue();
			WorkCompName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84,SysConst.PACKAGESPILTER));
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			VIPValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			SendQQFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			SendMSNFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			ServerArea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			SubCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			Substandard = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			PakageCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			Destination = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			Purpose = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			EmergencyContact = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			EmerContPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			ApplytoDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			MaternityFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			RescueType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			DeptCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			SubCompanyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			InsureCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			WorkAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			SocialInsuAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			OriginContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			OriginInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCInsuredSchema";
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
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
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
		if (FCode.equalsIgnoreCase("SendMsgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendMsgFlag));
		}
		if (FCode.equalsIgnoreCase("SendMailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendMailFlag));
		}
		if (FCode.equalsIgnoreCase("JoinContDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getJoinContDate()));
		}
		if (FCode.equalsIgnoreCase("WorkNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkNo));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("StartCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartCode));
		}
		if (FCode.equalsIgnoreCase("EndCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndCode));
		}
		if (FCode.equalsIgnoreCase("SocialInsuFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuFlag));
		}
		if (FCode.equalsIgnoreCase("IDExpDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDExpDate()));
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
		}
		if (FCode.equalsIgnoreCase("OrganComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganComCode));
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
		if (FCode.equalsIgnoreCase("MainCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainCustomerNo));
		}
		if (FCode.equalsIgnoreCase("InsuredType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredType));
		}
		if (FCode.equalsIgnoreCase("IsLongValid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsLongValid));
		}
		if (FCode.equalsIgnoreCase("IDStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getIDStartDate()));
		}
		if (FCode.equalsIgnoreCase("InsuredAppAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredAppAge));
		}
		if (FCode.equalsIgnoreCase("Seniority"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Seniority));
		}
		if (FCode.equalsIgnoreCase("WorkCompName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkCompName));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
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
		if (FCode.equalsIgnoreCase("SendQQFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendQQFlag));
		}
		if (FCode.equalsIgnoreCase("SendMSNFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendMSNFlag));
		}
		if (FCode.equalsIgnoreCase("ServerArea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerArea));
		}
		if (FCode.equalsIgnoreCase("SubCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCustomerNo));
		}
		if (FCode.equalsIgnoreCase("Substandard"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Substandard));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("PakageCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PakageCode));
		}
		if (FCode.equalsIgnoreCase("Destination"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Destination));
		}
		if (FCode.equalsIgnoreCase("Purpose"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Purpose));
		}
		if (FCode.equalsIgnoreCase("EmergencyContact"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmergencyContact));
		}
		if (FCode.equalsIgnoreCase("EmerContPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmerContPhone));
		}
		if (FCode.equalsIgnoreCase("ApplytoDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplytoDay));
		}
		if (FCode.equalsIgnoreCase("MaternityFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaternityFlag));
		}
		if (FCode.equalsIgnoreCase("RescueType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RescueType));
		}
		if (FCode.equalsIgnoreCase("DeptCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeptCode));
		}
		if (FCode.equalsIgnoreCase("SubCompanyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCompanyCode));
		}
		if (FCode.equalsIgnoreCase("InsureCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsureCode));
		}
		if (FCode.equalsIgnoreCase("WorkAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkAddress));
		}
		if (FCode.equalsIgnoreCase("SocialInsuAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuAddress));
		}
		if (FCode.equalsIgnoreCase("OriginContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriginContNo));
		}
		if (FCode.equalsIgnoreCase("OriginInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriginInsuredNo));
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
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RelationToMainInsured);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RelationToAppnt);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SequenceNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 23:
				strFieldValue = String.valueOf(Stature);
				break;
			case 24:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 33:
				strFieldValue = String.valueOf(Salary);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(InsuredStat);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 50:
				strFieldValue = String.valueOf(BMI);
				break;
			case 51:
				strFieldValue = String.valueOf(InsuredPeoples);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
				break;
			case 54:
				strFieldValue = String.valueOf(CustomerSeqNo);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuNo);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(SendMsgFlag);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(SendMailFlag);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinContDate()));
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(WorkNo);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(StartCode);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(EndCode);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDExpDate()));
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(OrganComCode);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(LastName);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(FirstName);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(LastNameEn);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(FirstNameEn);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(NameEn);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(LastNamePY);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(FirstNamePY);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(MainCustomerNo);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(InsuredType);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(IsLongValid);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getIDStartDate()));
				break;
			case 79:
				strFieldValue = String.valueOf(InsuredAppAge);
				break;
			case 80:
				strFieldValue = String.valueOf(Seniority);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(WorkCompName);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(VIPValue);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(SendQQFlag);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(SendMSNFlag);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(ServerArea);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(SubCustomerNo);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(Substandard);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(PakageCode);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(Destination);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(Purpose);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(EmergencyContact);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(EmerContPhone);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(ApplytoDay);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(MaternityFlag);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(RescueType);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(DeptCode);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(SubCompanyCode);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(InsureCode);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(WorkAddress);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuAddress);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(OriginContNo);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(OriginInsuredNo);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 108:
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
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
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
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BMI = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredPeoples = i;
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
		if (FCode.equalsIgnoreCase("CustomerSeqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CustomerSeqNo = i;
			}
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
		if (FCode.equalsIgnoreCase("JoinContDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				JoinContDate = fDate.getDate( FValue );
			}
			else
				JoinContDate = null;
		}
		if (FCode.equalsIgnoreCase("WorkNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkNo = FValue.trim();
			}
			else
				WorkNo = null;
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("StartCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartCode = FValue.trim();
			}
			else
				StartCode = null;
		}
		if (FCode.equalsIgnoreCase("EndCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndCode = FValue.trim();
			}
			else
				EndCode = null;
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
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNo = FValue.trim();
			}
			else
				GrpNo = null;
		}
		if (FCode.equalsIgnoreCase("OrganComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrganComCode = FValue.trim();
			}
			else
				OrganComCode = null;
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
		if (FCode.equalsIgnoreCase("MainCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainCustomerNo = FValue.trim();
			}
			else
				MainCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredType = FValue.trim();
			}
			else
				InsuredType = null;
		}
		if (FCode.equalsIgnoreCase("IsLongValid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsLongValid = FValue.trim();
			}
			else
				IsLongValid = null;
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
		if (FCode.equalsIgnoreCase("InsuredAppAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredAppAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("Seniority"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Seniority = d;
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
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
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
		if (FCode.equalsIgnoreCase("ServerArea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerArea = FValue.trim();
			}
			else
				ServerArea = null;
		}
		if (FCode.equalsIgnoreCase("SubCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCustomerNo = FValue.trim();
			}
			else
				SubCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("Substandard"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Substandard = FValue.trim();
			}
			else
				Substandard = null;
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
		if (FCode.equalsIgnoreCase("PakageCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PakageCode = FValue.trim();
			}
			else
				PakageCode = null;
		}
		if (FCode.equalsIgnoreCase("Destination"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Destination = FValue.trim();
			}
			else
				Destination = null;
		}
		if (FCode.equalsIgnoreCase("Purpose"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Purpose = FValue.trim();
			}
			else
				Purpose = null;
		}
		if (FCode.equalsIgnoreCase("EmergencyContact"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EmergencyContact = FValue.trim();
			}
			else
				EmergencyContact = null;
		}
		if (FCode.equalsIgnoreCase("EmerContPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EmerContPhone = FValue.trim();
			}
			else
				EmerContPhone = null;
		}
		if (FCode.equalsIgnoreCase("ApplytoDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplytoDay = FValue.trim();
			}
			else
				ApplytoDay = null;
		}
		if (FCode.equalsIgnoreCase("MaternityFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MaternityFlag = FValue.trim();
			}
			else
				MaternityFlag = null;
		}
		if (FCode.equalsIgnoreCase("RescueType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RescueType = FValue.trim();
			}
			else
				RescueType = null;
		}
		if (FCode.equalsIgnoreCase("DeptCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeptCode = FValue.trim();
			}
			else
				DeptCode = null;
		}
		if (FCode.equalsIgnoreCase("SubCompanyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCompanyCode = FValue.trim();
			}
			else
				SubCompanyCode = null;
		}
		if (FCode.equalsIgnoreCase("InsureCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsureCode = FValue.trim();
			}
			else
				InsureCode = null;
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
		if (FCode.equalsIgnoreCase("OriginContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriginContNo = FValue.trim();
			}
			else
				OriginContNo = null;
		}
		if (FCode.equalsIgnoreCase("OriginInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriginInsuredNo = FValue.trim();
			}
			else
				OriginInsuredNo = null;
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
		LCInsuredSchema other = (LCInsuredSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
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
			&& fDate.getString(Birthday).equals(other.getBirthday())
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
			&& ContPlanCode.equals(other.getContPlanCode())
			&& Operator.equals(other.getOperator())
			&& InsuredStat.equals(other.getInsuredStat())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& BMI == other.getBMI()
			&& InsuredPeoples == other.getInsuredPeoples()
			&& License.equals(other.getLicense())
			&& LicenseType.equals(other.getLicenseType())
			&& CustomerSeqNo == other.getCustomerSeqNo()
			&& SocialInsuNo.equals(other.getSocialInsuNo())
			&& SendMsgFlag.equals(other.getSendMsgFlag())
			&& SendMailFlag.equals(other.getSendMailFlag())
			&& fDate.getString(JoinContDate).equals(other.getJoinContDate())
			&& WorkNo.equals(other.getWorkNo())
			&& CertifyCode.equals(other.getCertifyCode())
			&& StartCode.equals(other.getStartCode())
			&& EndCode.equals(other.getEndCode())
			&& SocialInsuFlag.equals(other.getSocialInsuFlag())
			&& fDate.getString(IDExpDate).equals(other.getIDExpDate())
			&& GrpNo.equals(other.getGrpNo())
			&& OrganComCode.equals(other.getOrganComCode())
			&& LastName.equals(other.getLastName())
			&& FirstName.equals(other.getFirstName())
			&& LastNameEn.equals(other.getLastNameEn())
			&& FirstNameEn.equals(other.getFirstNameEn())
			&& NameEn.equals(other.getNameEn())
			&& LastNamePY.equals(other.getLastNamePY())
			&& FirstNamePY.equals(other.getFirstNamePY())
			&& Language.equals(other.getLanguage())
			&& MainCustomerNo.equals(other.getMainCustomerNo())
			&& InsuredType.equals(other.getInsuredType())
			&& IsLongValid.equals(other.getIsLongValid())
			&& fDate.getString(IDStartDate).equals(other.getIDStartDate())
			&& InsuredAppAge == other.getInsuredAppAge()
			&& Seniority == other.getSeniority()
			&& WorkCompName.equals(other.getWorkCompName())
			&& PlanCode.equals(other.getPlanCode())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
			&& VIPValue.equals(other.getVIPValue())
			&& SendQQFlag.equals(other.getSendQQFlag())
			&& SendMSNFlag.equals(other.getSendMSNFlag())
			&& ServerArea.equals(other.getServerArea())
			&& SubCustomerNo.equals(other.getSubCustomerNo())
			&& Substandard.equals(other.getSubstandard())
			&& Remark.equals(other.getRemark())
			&& PakageCode.equals(other.getPakageCode())
			&& Destination.equals(other.getDestination())
			&& Purpose.equals(other.getPurpose())
			&& EmergencyContact.equals(other.getEmergencyContact())
			&& EmerContPhone.equals(other.getEmerContPhone())
			&& ApplytoDay.equals(other.getApplytoDay())
			&& MaternityFlag.equals(other.getMaternityFlag())
			&& RescueType.equals(other.getRescueType())
			&& DeptCode.equals(other.getDeptCode())
			&& SubCompanyCode.equals(other.getSubCompanyCode())
			&& InsureCode.equals(other.getInsureCode())
			&& WorkAddress.equals(other.getWorkAddress())
			&& SocialInsuAddress.equals(other.getSocialInsuAddress())
			&& OriginContNo.equals(other.getOriginContNo())
			&& OriginInsuredNo.equals(other.getOriginInsuredNo())
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
		if( strFieldName.equals("InsuredNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 6;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 7;
		}
		if( strFieldName.equals("RelationToMainInsured") ) {
			return 8;
		}
		if( strFieldName.equals("RelationToAppnt") ) {
			return 9;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 10;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return 11;
		}
		if( strFieldName.equals("Name") ) {
			return 12;
		}
		if( strFieldName.equals("Sex") ) {
			return 13;
		}
		if( strFieldName.equals("Birthday") ) {
			return 14;
		}
		if( strFieldName.equals("IDType") ) {
			return 15;
		}
		if( strFieldName.equals("IDNo") ) {
			return 16;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 17;
		}
		if( strFieldName.equals("Nationality") ) {
			return 18;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 19;
		}
		if( strFieldName.equals("Marriage") ) {
			return 20;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 21;
		}
		if( strFieldName.equals("Health") ) {
			return 22;
		}
		if( strFieldName.equals("Stature") ) {
			return 23;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 24;
		}
		if( strFieldName.equals("Degree") ) {
			return 25;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 26;
		}
		if( strFieldName.equals("BankCode") ) {
			return 27;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 28;
		}
		if( strFieldName.equals("AccName") ) {
			return 29;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 30;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 31;
		}
		if( strFieldName.equals("Position") ) {
			return 32;
		}
		if( strFieldName.equals("Salary") ) {
			return 33;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 34;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 35;
		}
		if( strFieldName.equals("WorkType") ) {
			return 36;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 37;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 38;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 39;
		}
		if( strFieldName.equals("Operator") ) {
			return 40;
		}
		if( strFieldName.equals("InsuredStat") ) {
			return 41;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 42;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 45;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 46;
		}
		if( strFieldName.equals("UWCode") ) {
			return 47;
		}
		if( strFieldName.equals("UWDate") ) {
			return 48;
		}
		if( strFieldName.equals("UWTime") ) {
			return 49;
		}
		if( strFieldName.equals("BMI") ) {
			return 50;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 51;
		}
		if( strFieldName.equals("License") ) {
			return 52;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 53;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
			return 54;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return 55;
		}
		if( strFieldName.equals("SendMsgFlag") ) {
			return 56;
		}
		if( strFieldName.equals("SendMailFlag") ) {
			return 57;
		}
		if( strFieldName.equals("JoinContDate") ) {
			return 58;
		}
		if( strFieldName.equals("WorkNo") ) {
			return 59;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 60;
		}
		if( strFieldName.equals("StartCode") ) {
			return 61;
		}
		if( strFieldName.equals("EndCode") ) {
			return 62;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return 63;
		}
		if( strFieldName.equals("IDExpDate") ) {
			return 64;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 65;
		}
		if( strFieldName.equals("OrganComCode") ) {
			return 66;
		}
		if( strFieldName.equals("LastName") ) {
			return 67;
		}
		if( strFieldName.equals("FirstName") ) {
			return 68;
		}
		if( strFieldName.equals("LastNameEn") ) {
			return 69;
		}
		if( strFieldName.equals("FirstNameEn") ) {
			return 70;
		}
		if( strFieldName.equals("NameEn") ) {
			return 71;
		}
		if( strFieldName.equals("LastNamePY") ) {
			return 72;
		}
		if( strFieldName.equals("FirstNamePY") ) {
			return 73;
		}
		if( strFieldName.equals("Language") ) {
			return 74;
		}
		if( strFieldName.equals("MainCustomerNo") ) {
			return 75;
		}
		if( strFieldName.equals("InsuredType") ) {
			return 76;
		}
		if( strFieldName.equals("IsLongValid") ) {
			return 77;
		}
		if( strFieldName.equals("IDStartDate") ) {
			return 78;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return 79;
		}
		if( strFieldName.equals("Seniority") ) {
			return 80;
		}
		if( strFieldName.equals("WorkCompName") ) {
			return 81;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 82;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 83;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 84;
		}
		if( strFieldName.equals("VIPValue") ) {
			return 85;
		}
		if( strFieldName.equals("SendQQFlag") ) {
			return 86;
		}
		if( strFieldName.equals("SendMSNFlag") ) {
			return 87;
		}
		if( strFieldName.equals("ServerArea") ) {
			return 88;
		}
		if( strFieldName.equals("SubCustomerNo") ) {
			return 89;
		}
		if( strFieldName.equals("Substandard") ) {
			return 90;
		}
		if( strFieldName.equals("Remark") ) {
			return 91;
		}
		if( strFieldName.equals("PakageCode") ) {
			return 92;
		}
		if( strFieldName.equals("Destination") ) {
			return 93;
		}
		if( strFieldName.equals("Purpose") ) {
			return 94;
		}
		if( strFieldName.equals("EmergencyContact") ) {
			return 95;
		}
		if( strFieldName.equals("EmerContPhone") ) {
			return 96;
		}
		if( strFieldName.equals("ApplytoDay") ) {
			return 97;
		}
		if( strFieldName.equals("MaternityFlag") ) {
			return 98;
		}
		if( strFieldName.equals("RescueType") ) {
			return 99;
		}
		if( strFieldName.equals("DeptCode") ) {
			return 100;
		}
		if( strFieldName.equals("SubCompanyCode") ) {
			return 101;
		}
		if( strFieldName.equals("InsureCode") ) {
			return 102;
		}
		if( strFieldName.equals("WorkAddress") ) {
			return 103;
		}
		if( strFieldName.equals("SocialInsuAddress") ) {
			return 104;
		}
		if( strFieldName.equals("OriginContNo") ) {
			return 105;
		}
		if( strFieldName.equals("OriginInsuredNo") ) {
			return 106;
		}
		if( strFieldName.equals("ComCode") ) {
			return 107;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 108;
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
				strFieldName = "InsuredNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "AppntNo";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "ExecuteCom";
				break;
			case 7:
				strFieldName = "FamilyID";
				break;
			case 8:
				strFieldName = "RelationToMainInsured";
				break;
			case 9:
				strFieldName = "RelationToAppnt";
				break;
			case 10:
				strFieldName = "AddressNo";
				break;
			case 11:
				strFieldName = "SequenceNo";
				break;
			case 12:
				strFieldName = "Name";
				break;
			case 13:
				strFieldName = "Sex";
				break;
			case 14:
				strFieldName = "Birthday";
				break;
			case 15:
				strFieldName = "IDType";
				break;
			case 16:
				strFieldName = "IDNo";
				break;
			case 17:
				strFieldName = "NativePlace";
				break;
			case 18:
				strFieldName = "Nationality";
				break;
			case 19:
				strFieldName = "RgtAddress";
				break;
			case 20:
				strFieldName = "Marriage";
				break;
			case 21:
				strFieldName = "MarriageDate";
				break;
			case 22:
				strFieldName = "Health";
				break;
			case 23:
				strFieldName = "Stature";
				break;
			case 24:
				strFieldName = "Avoirdupois";
				break;
			case 25:
				strFieldName = "Degree";
				break;
			case 26:
				strFieldName = "CreditGrade";
				break;
			case 27:
				strFieldName = "BankCode";
				break;
			case 28:
				strFieldName = "BankAccNo";
				break;
			case 29:
				strFieldName = "AccName";
				break;
			case 30:
				strFieldName = "JoinCompanyDate";
				break;
			case 31:
				strFieldName = "StartWorkDate";
				break;
			case 32:
				strFieldName = "Position";
				break;
			case 33:
				strFieldName = "Salary";
				break;
			case 34:
				strFieldName = "OccupationType";
				break;
			case 35:
				strFieldName = "OccupationCode";
				break;
			case 36:
				strFieldName = "WorkType";
				break;
			case 37:
				strFieldName = "PluralityType";
				break;
			case 38:
				strFieldName = "SmokeFlag";
				break;
			case 39:
				strFieldName = "ContPlanCode";
				break;
			case 40:
				strFieldName = "Operator";
				break;
			case 41:
				strFieldName = "InsuredStat";
				break;
			case 42:
				strFieldName = "MakeDate";
				break;
			case 43:
				strFieldName = "MakeTime";
				break;
			case 44:
				strFieldName = "ModifyDate";
				break;
			case 45:
				strFieldName = "ModifyTime";
				break;
			case 46:
				strFieldName = "UWFlag";
				break;
			case 47:
				strFieldName = "UWCode";
				break;
			case 48:
				strFieldName = "UWDate";
				break;
			case 49:
				strFieldName = "UWTime";
				break;
			case 50:
				strFieldName = "BMI";
				break;
			case 51:
				strFieldName = "InsuredPeoples";
				break;
			case 52:
				strFieldName = "License";
				break;
			case 53:
				strFieldName = "LicenseType";
				break;
			case 54:
				strFieldName = "CustomerSeqNo";
				break;
			case 55:
				strFieldName = "SocialInsuNo";
				break;
			case 56:
				strFieldName = "SendMsgFlag";
				break;
			case 57:
				strFieldName = "SendMailFlag";
				break;
			case 58:
				strFieldName = "JoinContDate";
				break;
			case 59:
				strFieldName = "WorkNo";
				break;
			case 60:
				strFieldName = "CertifyCode";
				break;
			case 61:
				strFieldName = "StartCode";
				break;
			case 62:
				strFieldName = "EndCode";
				break;
			case 63:
				strFieldName = "SocialInsuFlag";
				break;
			case 64:
				strFieldName = "IDExpDate";
				break;
			case 65:
				strFieldName = "GrpNo";
				break;
			case 66:
				strFieldName = "OrganComCode";
				break;
			case 67:
				strFieldName = "LastName";
				break;
			case 68:
				strFieldName = "FirstName";
				break;
			case 69:
				strFieldName = "LastNameEn";
				break;
			case 70:
				strFieldName = "FirstNameEn";
				break;
			case 71:
				strFieldName = "NameEn";
				break;
			case 72:
				strFieldName = "LastNamePY";
				break;
			case 73:
				strFieldName = "FirstNamePY";
				break;
			case 74:
				strFieldName = "Language";
				break;
			case 75:
				strFieldName = "MainCustomerNo";
				break;
			case 76:
				strFieldName = "InsuredType";
				break;
			case 77:
				strFieldName = "IsLongValid";
				break;
			case 78:
				strFieldName = "IDStartDate";
				break;
			case 79:
				strFieldName = "InsuredAppAge";
				break;
			case 80:
				strFieldName = "Seniority";
				break;
			case 81:
				strFieldName = "WorkCompName";
				break;
			case 82:
				strFieldName = "PlanCode";
				break;
			case 83:
				strFieldName = "DeathDate";
				break;
			case 84:
				strFieldName = "BlacklistFlag";
				break;
			case 85:
				strFieldName = "VIPValue";
				break;
			case 86:
				strFieldName = "SendQQFlag";
				break;
			case 87:
				strFieldName = "SendMSNFlag";
				break;
			case 88:
				strFieldName = "ServerArea";
				break;
			case 89:
				strFieldName = "SubCustomerNo";
				break;
			case 90:
				strFieldName = "Substandard";
				break;
			case 91:
				strFieldName = "Remark";
				break;
			case 92:
				strFieldName = "PakageCode";
				break;
			case 93:
				strFieldName = "Destination";
				break;
			case 94:
				strFieldName = "Purpose";
				break;
			case 95:
				strFieldName = "EmergencyContact";
				break;
			case 96:
				strFieldName = "EmerContPhone";
				break;
			case 97:
				strFieldName = "ApplytoDay";
				break;
			case 98:
				strFieldName = "MaternityFlag";
				break;
			case 99:
				strFieldName = "RescueType";
				break;
			case 100:
				strFieldName = "DeptCode";
				break;
			case 101:
				strFieldName = "SubCompanyCode";
				break;
			case 102:
				strFieldName = "InsureCode";
				break;
			case 103:
				strFieldName = "WorkAddress";
				break;
			case 104:
				strFieldName = "SocialInsuAddress";
				break;
			case 105:
				strFieldName = "OriginContNo";
				break;
			case 106:
				strFieldName = "OriginInsuredNo";
				break;
			case 107:
				strFieldName = "ComCode";
				break;
			case 108:
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
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BMI") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("License") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendMsgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendMailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JoinContDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("WorkNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDExpDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GrpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrganComCode") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("MainCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsLongValid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuredAppAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Seniority") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WorkCompName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
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
		if( strFieldName.equals("SendQQFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendMSNFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerArea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Substandard") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PakageCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Destination") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Purpose") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EmergencyContact") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EmerContPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplytoDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaternityFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RescueType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeptCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCompanyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsureCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriginContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriginInsuredNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 79:
				nFieldType = Schema.TYPE_INT;
				break;
			case 80:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 89:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 95:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 96:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 97:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 98:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 99:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 106:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 107:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 108:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
