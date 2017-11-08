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
import com.sinosoft.lis.db.LBInsuredDB;

/*
 * <p>ClassName: LBInsuredSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LBInsuredSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBInsuredSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
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
	/** 工作证号 */
	private String WorkNo;
	/** 卡单编码 */
	private String CertifyCode;
	/** 卡单起号 */
	private String StartCode;
	/** 卡单止号 */
	private String EndCode;
	/** 是否有社保标志 */
	private String SocialInsuFlag;
	/** 组织机构代码 */
	private String OrganComCode;

	public static final int FIELDNUM = 66;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBInsuredSchema()
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
		LBInsuredSchema cloned = (LBInsuredSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
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
		if(aFamilyID!=null && aFamilyID.length()>10)
			throw new IllegalArgumentException("家庭保障号FamilyID值"+aFamilyID+"的长度"+aFamilyID.length()+"大于最大值10");
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
	* 0  --  身份证<p>
	* 1  --  护照<p>
	* 2  --  军官证<p>
	* 3  --  驾照<p>
	* 4  --  户口本<p>
	* 5  --  学生证<p>
	* 6  --  工作证<p>
	* 9  --  无证件
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
		if(aInsuredStat!=null && aInsuredStat.length()>1)
			throw new IllegalArgumentException("被保人状态InsuredStat值"+aInsuredStat+"的长度"+aInsuredStat.length()+"大于最大值1");
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
			throw new IllegalArgumentException("工作证号WorkNo值"+aWorkNo+"的长度"+aWorkNo.length()+"大于最大值10");
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

	/**
	* 使用另外一个 LBInsuredSchema 对象给 Schema 赋值
	* @param: aLBInsuredSchema LBInsuredSchema
	**/
	public void setSchema(LBInsuredSchema aLBInsuredSchema)
	{
		this.EdorNo = aLBInsuredSchema.getEdorNo();
		this.GrpContNo = aLBInsuredSchema.getGrpContNo();
		this.ContNo = aLBInsuredSchema.getContNo();
		this.InsuredNo = aLBInsuredSchema.getInsuredNo();
		this.PrtNo = aLBInsuredSchema.getPrtNo();
		this.AppntNo = aLBInsuredSchema.getAppntNo();
		this.ManageCom = aLBInsuredSchema.getManageCom();
		this.ExecuteCom = aLBInsuredSchema.getExecuteCom();
		this.FamilyID = aLBInsuredSchema.getFamilyID();
		this.RelationToMainInsured = aLBInsuredSchema.getRelationToMainInsured();
		this.RelationToAppnt = aLBInsuredSchema.getRelationToAppnt();
		this.AddressNo = aLBInsuredSchema.getAddressNo();
		this.SequenceNo = aLBInsuredSchema.getSequenceNo();
		this.Name = aLBInsuredSchema.getName();
		this.Sex = aLBInsuredSchema.getSex();
		this.Birthday = fDate.getDate( aLBInsuredSchema.getBirthday());
		this.IDType = aLBInsuredSchema.getIDType();
		this.IDNo = aLBInsuredSchema.getIDNo();
		this.NativePlace = aLBInsuredSchema.getNativePlace();
		this.Nationality = aLBInsuredSchema.getNationality();
		this.RgtAddress = aLBInsuredSchema.getRgtAddress();
		this.Marriage = aLBInsuredSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLBInsuredSchema.getMarriageDate());
		this.Health = aLBInsuredSchema.getHealth();
		this.Stature = aLBInsuredSchema.getStature();
		this.Avoirdupois = aLBInsuredSchema.getAvoirdupois();
		this.Degree = aLBInsuredSchema.getDegree();
		this.CreditGrade = aLBInsuredSchema.getCreditGrade();
		this.BankCode = aLBInsuredSchema.getBankCode();
		this.BankAccNo = aLBInsuredSchema.getBankAccNo();
		this.AccName = aLBInsuredSchema.getAccName();
		this.JoinCompanyDate = fDate.getDate( aLBInsuredSchema.getJoinCompanyDate());
		this.StartWorkDate = fDate.getDate( aLBInsuredSchema.getStartWorkDate());
		this.Position = aLBInsuredSchema.getPosition();
		this.Salary = aLBInsuredSchema.getSalary();
		this.OccupationType = aLBInsuredSchema.getOccupationType();
		this.OccupationCode = aLBInsuredSchema.getOccupationCode();
		this.WorkType = aLBInsuredSchema.getWorkType();
		this.PluralityType = aLBInsuredSchema.getPluralityType();
		this.SmokeFlag = aLBInsuredSchema.getSmokeFlag();
		this.ContPlanCode = aLBInsuredSchema.getContPlanCode();
		this.Operator = aLBInsuredSchema.getOperator();
		this.InsuredStat = aLBInsuredSchema.getInsuredStat();
		this.MakeDate = fDate.getDate( aLBInsuredSchema.getMakeDate());
		this.MakeTime = aLBInsuredSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBInsuredSchema.getModifyDate());
		this.ModifyTime = aLBInsuredSchema.getModifyTime();
		this.UWFlag = aLBInsuredSchema.getUWFlag();
		this.UWCode = aLBInsuredSchema.getUWCode();
		this.UWDate = fDate.getDate( aLBInsuredSchema.getUWDate());
		this.UWTime = aLBInsuredSchema.getUWTime();
		this.BMI = aLBInsuredSchema.getBMI();
		this.InsuredPeoples = aLBInsuredSchema.getInsuredPeoples();
		this.License = aLBInsuredSchema.getLicense();
		this.LicenseType = aLBInsuredSchema.getLicenseType();
		this.CustomerSeqNo = aLBInsuredSchema.getCustomerSeqNo();
		this.SocialInsuNo = aLBInsuredSchema.getSocialInsuNo();
		this.SendMsgFlag = aLBInsuredSchema.getSendMsgFlag();
		this.SendMailFlag = aLBInsuredSchema.getSendMailFlag();
		this.JoinContDate = fDate.getDate( aLBInsuredSchema.getJoinContDate());
		this.WorkNo = aLBInsuredSchema.getWorkNo();
		this.CertifyCode = aLBInsuredSchema.getCertifyCode();
		this.StartCode = aLBInsuredSchema.getStartCode();
		this.EndCode = aLBInsuredSchema.getEndCode();
		this.SocialInsuFlag = aLBInsuredSchema.getSocialInsuFlag();
		this.OrganComCode = aLBInsuredSchema.getOrganComCode();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

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

			if( rs.getString("OrganComCode") == null )
				this.OrganComCode = null;
			else
				this.OrganComCode = rs.getString("OrganComCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBInsured表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBInsuredSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBInsuredSchema getSchema()
	{
		LBInsuredSchema aLBInsuredSchema = new LBInsuredSchema();
		aLBInsuredSchema.setSchema(this);
		return aLBInsuredSchema;
	}

	public LBInsuredDB getDB()
	{
		LBInsuredDB aDBOper = new LBInsuredDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBInsured描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(OrganComCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBInsured>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RelationToMainInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RelationToAppnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SequenceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			InsuredStat = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			BMI = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).intValue();
			License = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			LicenseType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			CustomerSeqNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).intValue();
			SocialInsuNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			SendMsgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			SendMailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			JoinContDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60,SysConst.PACKAGESPILTER));
			WorkNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			StartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			EndCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			SocialInsuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			OrganComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBInsuredSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
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
		if (FCode.equalsIgnoreCase("OrganComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganComCode));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RelationToMainInsured);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RelationToAppnt);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SequenceNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 24:
				strFieldValue = String.valueOf(Stature);
				break;
			case 25:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 34:
				strFieldValue = String.valueOf(Salary);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(InsuredStat);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 51:
				strFieldValue = String.valueOf(BMI);
				break;
			case 52:
				strFieldValue = String.valueOf(InsuredPeoples);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(License);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(LicenseType);
				break;
			case 55:
				strFieldValue = String.valueOf(CustomerSeqNo);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuNo);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(SendMsgFlag);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(SendMailFlag);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinContDate()));
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(WorkNo);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(StartCode);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(EndCode);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlag);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(OrganComCode);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("OrganComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrganComCode = FValue.trim();
			}
			else
				OrganComCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBInsuredSchema other = (LBInsuredSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& GrpContNo.equals(other.getGrpContNo())
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
			&& OrganComCode.equals(other.getOrganComCode());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 3;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 4;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 7;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 8;
		}
		if( strFieldName.equals("RelationToMainInsured") ) {
			return 9;
		}
		if( strFieldName.equals("RelationToAppnt") ) {
			return 10;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 11;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return 12;
		}
		if( strFieldName.equals("Name") ) {
			return 13;
		}
		if( strFieldName.equals("Sex") ) {
			return 14;
		}
		if( strFieldName.equals("Birthday") ) {
			return 15;
		}
		if( strFieldName.equals("IDType") ) {
			return 16;
		}
		if( strFieldName.equals("IDNo") ) {
			return 17;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 18;
		}
		if( strFieldName.equals("Nationality") ) {
			return 19;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 20;
		}
		if( strFieldName.equals("Marriage") ) {
			return 21;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 22;
		}
		if( strFieldName.equals("Health") ) {
			return 23;
		}
		if( strFieldName.equals("Stature") ) {
			return 24;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 25;
		}
		if( strFieldName.equals("Degree") ) {
			return 26;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 27;
		}
		if( strFieldName.equals("BankCode") ) {
			return 28;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 29;
		}
		if( strFieldName.equals("AccName") ) {
			return 30;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 31;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 32;
		}
		if( strFieldName.equals("Position") ) {
			return 33;
		}
		if( strFieldName.equals("Salary") ) {
			return 34;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 35;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 36;
		}
		if( strFieldName.equals("WorkType") ) {
			return 37;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 38;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 39;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 40;
		}
		if( strFieldName.equals("Operator") ) {
			return 41;
		}
		if( strFieldName.equals("InsuredStat") ) {
			return 42;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 43;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 45;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 46;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 47;
		}
		if( strFieldName.equals("UWCode") ) {
			return 48;
		}
		if( strFieldName.equals("UWDate") ) {
			return 49;
		}
		if( strFieldName.equals("UWTime") ) {
			return 50;
		}
		if( strFieldName.equals("BMI") ) {
			return 51;
		}
		if( strFieldName.equals("InsuredPeoples") ) {
			return 52;
		}
		if( strFieldName.equals("License") ) {
			return 53;
		}
		if( strFieldName.equals("LicenseType") ) {
			return 54;
		}
		if( strFieldName.equals("CustomerSeqNo") ) {
			return 55;
		}
		if( strFieldName.equals("SocialInsuNo") ) {
			return 56;
		}
		if( strFieldName.equals("SendMsgFlag") ) {
			return 57;
		}
		if( strFieldName.equals("SendMailFlag") ) {
			return 58;
		}
		if( strFieldName.equals("JoinContDate") ) {
			return 59;
		}
		if( strFieldName.equals("WorkNo") ) {
			return 60;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 61;
		}
		if( strFieldName.equals("StartCode") ) {
			return 62;
		}
		if( strFieldName.equals("EndCode") ) {
			return 63;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return 64;
		}
		if( strFieldName.equals("OrganComCode") ) {
			return 65;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "InsuredNo";
				break;
			case 4:
				strFieldName = "PrtNo";
				break;
			case 5:
				strFieldName = "AppntNo";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "ExecuteCom";
				break;
			case 8:
				strFieldName = "FamilyID";
				break;
			case 9:
				strFieldName = "RelationToMainInsured";
				break;
			case 10:
				strFieldName = "RelationToAppnt";
				break;
			case 11:
				strFieldName = "AddressNo";
				break;
			case 12:
				strFieldName = "SequenceNo";
				break;
			case 13:
				strFieldName = "Name";
				break;
			case 14:
				strFieldName = "Sex";
				break;
			case 15:
				strFieldName = "Birthday";
				break;
			case 16:
				strFieldName = "IDType";
				break;
			case 17:
				strFieldName = "IDNo";
				break;
			case 18:
				strFieldName = "NativePlace";
				break;
			case 19:
				strFieldName = "Nationality";
				break;
			case 20:
				strFieldName = "RgtAddress";
				break;
			case 21:
				strFieldName = "Marriage";
				break;
			case 22:
				strFieldName = "MarriageDate";
				break;
			case 23:
				strFieldName = "Health";
				break;
			case 24:
				strFieldName = "Stature";
				break;
			case 25:
				strFieldName = "Avoirdupois";
				break;
			case 26:
				strFieldName = "Degree";
				break;
			case 27:
				strFieldName = "CreditGrade";
				break;
			case 28:
				strFieldName = "BankCode";
				break;
			case 29:
				strFieldName = "BankAccNo";
				break;
			case 30:
				strFieldName = "AccName";
				break;
			case 31:
				strFieldName = "JoinCompanyDate";
				break;
			case 32:
				strFieldName = "StartWorkDate";
				break;
			case 33:
				strFieldName = "Position";
				break;
			case 34:
				strFieldName = "Salary";
				break;
			case 35:
				strFieldName = "OccupationType";
				break;
			case 36:
				strFieldName = "OccupationCode";
				break;
			case 37:
				strFieldName = "WorkType";
				break;
			case 38:
				strFieldName = "PluralityType";
				break;
			case 39:
				strFieldName = "SmokeFlag";
				break;
			case 40:
				strFieldName = "ContPlanCode";
				break;
			case 41:
				strFieldName = "Operator";
				break;
			case 42:
				strFieldName = "InsuredStat";
				break;
			case 43:
				strFieldName = "MakeDate";
				break;
			case 44:
				strFieldName = "MakeTime";
				break;
			case 45:
				strFieldName = "ModifyDate";
				break;
			case 46:
				strFieldName = "ModifyTime";
				break;
			case 47:
				strFieldName = "UWFlag";
				break;
			case 48:
				strFieldName = "UWCode";
				break;
			case 49:
				strFieldName = "UWDate";
				break;
			case 50:
				strFieldName = "UWTime";
				break;
			case 51:
				strFieldName = "BMI";
				break;
			case 52:
				strFieldName = "InsuredPeoples";
				break;
			case 53:
				strFieldName = "License";
				break;
			case 54:
				strFieldName = "LicenseType";
				break;
			case 55:
				strFieldName = "CustomerSeqNo";
				break;
			case 56:
				strFieldName = "SocialInsuNo";
				break;
			case 57:
				strFieldName = "SendMsgFlag";
				break;
			case 58:
				strFieldName = "SendMailFlag";
				break;
			case 59:
				strFieldName = "JoinContDate";
				break;
			case 60:
				strFieldName = "WorkNo";
				break;
			case 61:
				strFieldName = "CertifyCode";
				break;
			case 62:
				strFieldName = "StartCode";
				break;
			case 63:
				strFieldName = "EndCode";
				break;
			case 64:
				strFieldName = "SocialInsuFlag";
				break;
			case 65:
				strFieldName = "OrganComCode";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
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
		if( strFieldName.equals("OrganComCode") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_INT;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
