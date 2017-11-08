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
import com.sinosoft.lis.db.LAAgentDB;

/*
 * <p>ClassName: LAAgentSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAAgentSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAAgentSchema.class);

	// @Field
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人展业机构代码 */
	private String AgentGroup;
	/** 管理机构 */
	private String ManageCom;
	/** 密码 */
	private String Password;
	/** 推荐报名编号 */
	private String EntryNo;
	/** 姓名 */
	private String Name;
	/** 性别 */
	private String Sex;
	/** 出生日期 */
	private Date Birthday;
	/** 籍贯 */
	private String NativePlace;
	/** 民族 */
	private String Nationality;
	/** 婚姻状况 */
	private String Marriage;
	/** 信用等级 */
	private String CreditGrade;
	/** 家庭地址编码 */
	private String HomeAddressCode;
	/** 家庭地址 */
	private String HomeAddress;
	/** 通讯地址 */
	private String PostalAddress;
	/** 邮政编码 */
	private String ZipCode;
	/** 电话 */
	private String Phone;
	/** 传呼 */
	private String BP;
	/** 手机 */
	private String Mobile;
	/** E_mail */
	private String EMail;
	/** 结婚日期 */
	private Date MarriageDate;
	/** 身份证号码 */
	private String IDNo;
	/** 来源地 */
	private String Source;
	/** 血型 */
	private String BloodType;
	/** 政治面貌 */
	private String PolityVisage;
	/** 学历 */
	private String Degree;
	/** 毕业院校 */
	private String GraduateSchool;
	/** 专业 */
	private String Speciality;
	/** 职称 */
	private String PostTitle;
	/** 外语水平 */
	private String ForeignLevel;
	/** 从业年限 */
	private int WorkAge;
	/** 原工作单位 */
	private String OldCom;
	/** 原职业 */
	private String OldOccupation;
	/** 工作职务 */
	private String HeadShip;
	/** 推荐代理人 */
	private String RecommendAgent;
	/** 工种/行业 */
	private String Business;
	/** 销售资格 */
	private String SaleQuaf;
	/** 代理人资格证号码 */
	private String QuafNo;
	/** 证书开始日期 */
	private Date QuafStartDate;
	/** 证书结束日期 */
	private Date QuafEndDate;
	/** 展业证号码1 */
	private String DevNo1;
	/** 展业证号码2 */
	private String DevNo2;
	/** 聘用合同号码 */
	private String RetainContNo;
	/** 代理人类别 */
	private String AgentKind;
	/** 业务拓展级别 */
	private String DevGrade;
	/** 内勤标志 */
	private String InsideFlag;
	/** 是否专职标志 */
	private String FullTimeFlag;
	/** 是否有待业证标志 */
	private String NoWorkFlag;
	/** 档案调入日期 */
	private Date TrainDate;
	/** 录用日期 */
	private Date EmployDate;
	/** 转正日期 */
	private Date InDueFormDate;
	/** 离司日期 */
	private Date OutWorkDate;
	/** 推荐名编号2 */
	private String RecommendNo;
	/** 担保人名称 */
	private String CautionerName;
	/** 担保人性别 */
	private String CautionerSex;
	/** 担保人身份证 */
	private String CautionerID;
	/** 担保人出生日 */
	private Date CautionerBirthday;
	/** 复核员 */
	private String Approver;
	/** 复核日期 */
	private Date ApproveDate;
	/** 保证金 */
	private double AssuMoney;
	/** 备注 */
	private String Remark;
	/** 代理人状态 */
	private String AgentState;
	/** 档案标志位 */
	private String QualiPassFlag;
	/** 打折标志 */
	private String SmokeFlag;
	/** 户口所在地 */
	private String RgtAddress;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐户 */
	private String BankAccNo;
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
	/** 展业类型 */
	private String BranchType;
	/** 培训期数 */
	private String TrainPeriods;
	/** 代理人组别 */
	private String BranchCode;
	/** 代理人年龄 */
	private double Age;
	/** 所属渠道 */
	private String ChannelName;
	/** 证件类型 */
	private String IDType;

	public static final int FIELDNUM = 78;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAAgentSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AgentCode";

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
		LAAgentSchema cloned = (LAAgentSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	/**
	* --新版基本法没有用到
	*/
	public String getEntryNo()
	{
		return EntryNo;
	}
	public void setEntryNo(String aEntryNo)
	{
		EntryNo = aEntryNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
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

	public String getNativePlace()
	{
		return NativePlace;
	}
	public void setNativePlace(String aNativePlace)
	{
		NativePlace = aNativePlace;
	}
	public String getNationality()
	{
		return Nationality;
	}
	public void setNationality(String aNationality)
	{
		Nationality = aNationality;
	}
	public String getMarriage()
	{
		return Marriage;
	}
	public void setMarriage(String aMarriage)
	{
		Marriage = aMarriage;
	}
	public String getCreditGrade()
	{
		return CreditGrade;
	}
	public void setCreditGrade(String aCreditGrade)
	{
		CreditGrade = aCreditGrade;
	}
	public String getHomeAddressCode()
	{
		return HomeAddressCode;
	}
	public void setHomeAddressCode(String aHomeAddressCode)
	{
		HomeAddressCode = aHomeAddressCode;
	}
	public String getHomeAddress()
	{
		return HomeAddress;
	}
	public void setHomeAddress(String aHomeAddress)
	{
		HomeAddress = aHomeAddress;
	}
	public String getPostalAddress()
	{
		return PostalAddress;
	}
	public void setPostalAddress(String aPostalAddress)
	{
		PostalAddress = aPostalAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		ZipCode = aZipCode;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getBP()
	{
		return BP;
	}
	public void setBP(String aBP)
	{
		BP = aBP;
	}
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		Mobile = aMobile;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		EMail = aEMail;
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

	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getSource()
	{
		return Source;
	}
	public void setSource(String aSource)
	{
		Source = aSource;
	}
	/**
	* A/B/AB/O
	*/
	public String getBloodType()
	{
		return BloodType;
	}
	public void setBloodType(String aBloodType)
	{
		BloodType = aBloodType;
	}
	public String getPolityVisage()
	{
		return PolityVisage;
	}
	public void setPolityVisage(String aPolityVisage)
	{
		PolityVisage = aPolityVisage;
	}
	public String getDegree()
	{
		return Degree;
	}
	public void setDegree(String aDegree)
	{
		Degree = aDegree;
	}
	public String getGraduateSchool()
	{
		return GraduateSchool;
	}
	public void setGraduateSchool(String aGraduateSchool)
	{
		GraduateSchool = aGraduateSchool;
	}
	public String getSpeciality()
	{
		return Speciality;
	}
	public void setSpeciality(String aSpeciality)
	{
		Speciality = aSpeciality;
	}
	public String getPostTitle()
	{
		return PostTitle;
	}
	public void setPostTitle(String aPostTitle)
	{
		PostTitle = aPostTitle;
	}
	public String getForeignLevel()
	{
		return ForeignLevel;
	}
	public void setForeignLevel(String aForeignLevel)
	{
		ForeignLevel = aForeignLevel;
	}
	public int getWorkAge()
	{
		return WorkAge;
	}
	public void setWorkAge(int aWorkAge)
	{
		WorkAge = aWorkAge;
	}
	public void setWorkAge(String aWorkAge)
	{
		if (aWorkAge != null && !aWorkAge.equals(""))
		{
			Integer tInteger = new Integer(aWorkAge);
			int i = tInteger.intValue();
			WorkAge = i;
		}
	}

	public String getOldCom()
	{
		return OldCom;
	}
	public void setOldCom(String aOldCom)
	{
		OldCom = aOldCom;
	}
	public String getOldOccupation()
	{
		return OldOccupation;
	}
	public void setOldOccupation(String aOldOccupation)
	{
		OldOccupation = aOldOccupation;
	}
	public String getHeadShip()
	{
		return HeadShip;
	}
	public void setHeadShip(String aHeadShip)
	{
		HeadShip = aHeadShip;
	}
	public String getRecommendAgent()
	{
		return RecommendAgent;
	}
	public void setRecommendAgent(String aRecommendAgent)
	{
		RecommendAgent = aRecommendAgent;
	}
	public String getBusiness()
	{
		return Business;
	}
	public void setBusiness(String aBusiness)
	{
		Business = aBusiness;
	}
	/**
	* Y/N
	*/
	public String getSaleQuaf()
	{
		return SaleQuaf;
	}
	public void setSaleQuaf(String aSaleQuaf)
	{
		SaleQuaf = aSaleQuaf;
	}
	public String getQuafNo()
	{
		return QuafNo;
	}
	public void setQuafNo(String aQuafNo)
	{
		QuafNo = aQuafNo;
	}
	public String getQuafStartDate()
	{
		if( QuafStartDate != null )
			return fDate.getString(QuafStartDate);
		else
			return null;
	}
	public void setQuafStartDate(Date aQuafStartDate)
	{
		QuafStartDate = aQuafStartDate;
	}
	public void setQuafStartDate(String aQuafStartDate)
	{
		if (aQuafStartDate != null && !aQuafStartDate.equals("") )
		{
			QuafStartDate = fDate.getDate( aQuafStartDate );
		}
		else
			QuafStartDate = null;
	}

	public String getQuafEndDate()
	{
		if( QuafEndDate != null )
			return fDate.getString(QuafEndDate);
		else
			return null;
	}
	public void setQuafEndDate(Date aQuafEndDate)
	{
		QuafEndDate = aQuafEndDate;
	}
	public void setQuafEndDate(String aQuafEndDate)
	{
		if (aQuafEndDate != null && !aQuafEndDate.equals("") )
		{
			QuafEndDate = fDate.getDate( aQuafEndDate );
		}
		else
			QuafEndDate = null;
	}

	public String getDevNo1()
	{
		return DevNo1;
	}
	public void setDevNo1(String aDevNo1)
	{
		DevNo1 = aDevNo1;
	}
	public String getDevNo2()
	{
		return DevNo2;
	}
	public void setDevNo2(String aDevNo2)
	{
		DevNo2 = aDevNo2;
	}
	public String getRetainContNo()
	{
		return RetainContNo;
	}
	public void setRetainContNo(String aRetainContNo)
	{
		RetainContNo = aRetainContNo;
	}
	/**
	* 01-银代经理<p>
	* 02-银代协理<p>
	* 03-渠道经理<p>
	* 04-银代客户经理<p>
	* 05-培训岗<p>
	* <p>
	* 06-企划策划岗<p>
	* <p>
	* 07-销售支援岗<p>
	* 08-综合岗<p>
	* <p>
	* <p>
	* <p>
	* 10-普通代理人<p>
	* 11-组主管<p>
	* <p>
	* 12-部主管<p>
	* <p>
	* 13-督导长<p>
	* <p>
	* 14-区域督导长<p>
	* <p>
	* <p>
	* <p>
	* 20-法人客户经理<p>
	* 21-法人组经理<p>
	* <p>
	* 22-法人协理<p>
	* 23－法人部经理<p>
	* 24-培训岗<p>
	* <p>
	* 25-企划策划岗<p>
	* <p>
	* 26-销售支援岗<p>
	* 27-综合岗
	*/
	public String getAgentKind()
	{
		return AgentKind;
	}
	public void setAgentKind(String aAgentKind)
	{
		AgentKind = aAgentKind;
	}
	public String getDevGrade()
	{
		return DevGrade;
	}
	public void setDevGrade(String aDevGrade)
	{
		DevGrade = aDevGrade;
	}
	/**
	* 0-内勤<p>
	* 1-外勤
	*/
	public String getInsideFlag()
	{
		return InsideFlag;
	}
	public void setInsideFlag(String aInsideFlag)
	{
		InsideFlag = aInsideFlag;
	}
	/**
	* 0 --专职<p>
	* 1 --兼职
	*/
	public String getFullTimeFlag()
	{
		return FullTimeFlag;
	}
	public void setFullTimeFlag(String aFullTimeFlag)
	{
		FullTimeFlag = aFullTimeFlag;
	}
	/**
	* 0 --没有待业证<p>
	* <p>
	* 1 --有待业证
	*/
	public String getNoWorkFlag()
	{
		return NoWorkFlag;
	}
	public void setNoWorkFlag(String aNoWorkFlag)
	{
		NoWorkFlag = aNoWorkFlag;
	}
	public String getTrainDate()
	{
		if( TrainDate != null )
			return fDate.getString(TrainDate);
		else
			return null;
	}
	public void setTrainDate(Date aTrainDate)
	{
		TrainDate = aTrainDate;
	}
	public void setTrainDate(String aTrainDate)
	{
		if (aTrainDate != null && !aTrainDate.equals("") )
		{
			TrainDate = fDate.getDate( aTrainDate );
		}
		else
			TrainDate = null;
	}

	/**
	* --入司日期
	*/
	public String getEmployDate()
	{
		if( EmployDate != null )
			return fDate.getString(EmployDate);
		else
			return null;
	}
	public void setEmployDate(Date aEmployDate)
	{
		EmployDate = aEmployDate;
	}
	public void setEmployDate(String aEmployDate)
	{
		if (aEmployDate != null && !aEmployDate.equals("") )
		{
			EmployDate = fDate.getDate( aEmployDate );
		}
		else
			EmployDate = null;
	}

	/**
	* 不可修改，由行政信息提供<p>
	* A01->A02
	*/
	public String getInDueFormDate()
	{
		if( InDueFormDate != null )
			return fDate.getString(InDueFormDate);
		else
			return null;
	}
	public void setInDueFormDate(Date aInDueFormDate)
	{
		InDueFormDate = aInDueFormDate;
	}
	public void setInDueFormDate(String aInDueFormDate)
	{
		if (aInDueFormDate != null && !aInDueFormDate.equals("") )
		{
			InDueFormDate = fDate.getDate( aInDueFormDate );
		}
		else
			InDueFormDate = null;
	}

	/**
	* 不可修改，由行政信息提供<p>
	* 离职减员或考核清退置CurrentDate
	*/
	public String getOutWorkDate()
	{
		if( OutWorkDate != null )
			return fDate.getString(OutWorkDate);
		else
			return null;
	}
	public void setOutWorkDate(Date aOutWorkDate)
	{
		OutWorkDate = aOutWorkDate;
	}
	public void setOutWorkDate(String aOutWorkDate)
	{
		if (aOutWorkDate != null && !aOutWorkDate.equals("") )
		{
			OutWorkDate = fDate.getDate( aOutWorkDate );
		}
		else
			OutWorkDate = null;
	}

	public String getRecommendNo()
	{
		return RecommendNo;
	}
	public void setRecommendNo(String aRecommendNo)
	{
		RecommendNo = aRecommendNo;
	}
	public String getCautionerName()
	{
		return CautionerName;
	}
	public void setCautionerName(String aCautionerName)
	{
		CautionerName = aCautionerName;
	}
	public String getCautionerSex()
	{
		return CautionerSex;
	}
	public void setCautionerSex(String aCautionerSex)
	{
		CautionerSex = aCautionerSex;
	}
	public String getCautionerID()
	{
		return CautionerID;
	}
	public void setCautionerID(String aCautionerID)
	{
		CautionerID = aCautionerID;
	}
	public String getCautionerBirthday()
	{
		if( CautionerBirthday != null )
			return fDate.getString(CautionerBirthday);
		else
			return null;
	}
	public void setCautionerBirthday(Date aCautionerBirthday)
	{
		CautionerBirthday = aCautionerBirthday;
	}
	public void setCautionerBirthday(String aCautionerBirthday)
	{
		if (aCautionerBirthday != null && !aCautionerBirthday.equals("") )
		{
			CautionerBirthday = fDate.getDate( aCautionerBirthday );
		}
		else
			CautionerBirthday = null;
	}

	public String getApprover()
	{
		return Approver;
	}
	public void setApprover(String aApprover)
	{
		Approver = aApprover;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public double getAssuMoney()
	{
		return AssuMoney;
	}
	public void setAssuMoney(double aAssuMoney)
	{
		AssuMoney = aAssuMoney;
	}
	public void setAssuMoney(String aAssuMoney)
	{
		if (aAssuMoney != null && !aAssuMoney.equals(""))
		{
			Double tDouble = new Double(aAssuMoney);
			double d = tDouble.doubleValue();
			AssuMoney = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* 代理人状态(01－增员，02－二次增员，03－离职,04-二次离职,05-清退）
	*/
	public String getAgentState()
	{
		return AgentState;
	}
	public void setAgentState(String aAgentState)
	{
		AgentState = aAgentState;
	}
	/**
	* 0 or null-档案未调入<p>
	* <p>
	* 1-档案调入。
	*/
	public String getQualiPassFlag()
	{
		return QualiPassFlag;
	}
	public void setQualiPassFlag(String aQualiPassFlag)
	{
		QualiPassFlag = aQualiPassFlag;
	}
	/**
	* 0,null-打折A01<p>
	* 1-不打折
	*/
	public String getSmokeFlag()
	{
		return SmokeFlag;
	}
	public void setSmokeFlag(String aSmokeFlag)
	{
		SmokeFlag = aSmokeFlag;
	}
	public String getRgtAddress()
	{
		return RgtAddress;
	}
	public void setRgtAddress(String aRgtAddress)
	{
		RgtAddress = aRgtAddress;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
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
		ModifyTime = aModifyTime;
	}
	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，4-续收员.5-续收督导,6-联办代理,7-新中介,9-中介续收督导)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	public String getTrainPeriods()
	{
		return TrainPeriods;
	}
	public void setTrainPeriods(String aTrainPeriods)
	{
		TrainPeriods = aTrainPeriods;
	}
	/**
	* ---个险：直辖组编码labranchgroup.agentgroup
	*/
	public String getBranchCode()
	{
		return BranchCode;
	}
	public void setBranchCode(String aBranchCode)
	{
		BranchCode = aBranchCode;
	}
	public double getAge()
	{
		return Age;
	}
	public void setAge(double aAge)
	{
		Age = aAge;
	}
	public void setAge(String aAge)
	{
		if (aAge != null && !aAge.equals(""))
		{
			Double tDouble = new Double(aAge);
			double d = tDouble.doubleValue();
			Age = d;
		}
	}

	public String getChannelName()
	{
		return ChannelName;
	}
	public void setChannelName(String aChannelName)
	{
		ChannelName = aChannelName;
	}
	/**
	* I-身份证 默认及历史数据都置<p>
	* <p>
	* O-其他如军官证侨胞证<p>
	* <p>
	* <p>
	* add by jiaqiangli 增加字段
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}

	/**
	* 使用另外一个 LAAgentSchema 对象给 Schema 赋值
	* @param: aLAAgentSchema LAAgentSchema
	**/
	public void setSchema(LAAgentSchema aLAAgentSchema)
	{
		this.AgentCode = aLAAgentSchema.getAgentCode();
		this.AgentGroup = aLAAgentSchema.getAgentGroup();
		this.ManageCom = aLAAgentSchema.getManageCom();
		this.Password = aLAAgentSchema.getPassword();
		this.EntryNo = aLAAgentSchema.getEntryNo();
		this.Name = aLAAgentSchema.getName();
		this.Sex = aLAAgentSchema.getSex();
		this.Birthday = fDate.getDate( aLAAgentSchema.getBirthday());
		this.NativePlace = aLAAgentSchema.getNativePlace();
		this.Nationality = aLAAgentSchema.getNationality();
		this.Marriage = aLAAgentSchema.getMarriage();
		this.CreditGrade = aLAAgentSchema.getCreditGrade();
		this.HomeAddressCode = aLAAgentSchema.getHomeAddressCode();
		this.HomeAddress = aLAAgentSchema.getHomeAddress();
		this.PostalAddress = aLAAgentSchema.getPostalAddress();
		this.ZipCode = aLAAgentSchema.getZipCode();
		this.Phone = aLAAgentSchema.getPhone();
		this.BP = aLAAgentSchema.getBP();
		this.Mobile = aLAAgentSchema.getMobile();
		this.EMail = aLAAgentSchema.getEMail();
		this.MarriageDate = fDate.getDate( aLAAgentSchema.getMarriageDate());
		this.IDNo = aLAAgentSchema.getIDNo();
		this.Source = aLAAgentSchema.getSource();
		this.BloodType = aLAAgentSchema.getBloodType();
		this.PolityVisage = aLAAgentSchema.getPolityVisage();
		this.Degree = aLAAgentSchema.getDegree();
		this.GraduateSchool = aLAAgentSchema.getGraduateSchool();
		this.Speciality = aLAAgentSchema.getSpeciality();
		this.PostTitle = aLAAgentSchema.getPostTitle();
		this.ForeignLevel = aLAAgentSchema.getForeignLevel();
		this.WorkAge = aLAAgentSchema.getWorkAge();
		this.OldCom = aLAAgentSchema.getOldCom();
		this.OldOccupation = aLAAgentSchema.getOldOccupation();
		this.HeadShip = aLAAgentSchema.getHeadShip();
		this.RecommendAgent = aLAAgentSchema.getRecommendAgent();
		this.Business = aLAAgentSchema.getBusiness();
		this.SaleQuaf = aLAAgentSchema.getSaleQuaf();
		this.QuafNo = aLAAgentSchema.getQuafNo();
		this.QuafStartDate = fDate.getDate( aLAAgentSchema.getQuafStartDate());
		this.QuafEndDate = fDate.getDate( aLAAgentSchema.getQuafEndDate());
		this.DevNo1 = aLAAgentSchema.getDevNo1();
		this.DevNo2 = aLAAgentSchema.getDevNo2();
		this.RetainContNo = aLAAgentSchema.getRetainContNo();
		this.AgentKind = aLAAgentSchema.getAgentKind();
		this.DevGrade = aLAAgentSchema.getDevGrade();
		this.InsideFlag = aLAAgentSchema.getInsideFlag();
		this.FullTimeFlag = aLAAgentSchema.getFullTimeFlag();
		this.NoWorkFlag = aLAAgentSchema.getNoWorkFlag();
		this.TrainDate = fDate.getDate( aLAAgentSchema.getTrainDate());
		this.EmployDate = fDate.getDate( aLAAgentSchema.getEmployDate());
		this.InDueFormDate = fDate.getDate( aLAAgentSchema.getInDueFormDate());
		this.OutWorkDate = fDate.getDate( aLAAgentSchema.getOutWorkDate());
		this.RecommendNo = aLAAgentSchema.getRecommendNo();
		this.CautionerName = aLAAgentSchema.getCautionerName();
		this.CautionerSex = aLAAgentSchema.getCautionerSex();
		this.CautionerID = aLAAgentSchema.getCautionerID();
		this.CautionerBirthday = fDate.getDate( aLAAgentSchema.getCautionerBirthday());
		this.Approver = aLAAgentSchema.getApprover();
		this.ApproveDate = fDate.getDate( aLAAgentSchema.getApproveDate());
		this.AssuMoney = aLAAgentSchema.getAssuMoney();
		this.Remark = aLAAgentSchema.getRemark();
		this.AgentState = aLAAgentSchema.getAgentState();
		this.QualiPassFlag = aLAAgentSchema.getQualiPassFlag();
		this.SmokeFlag = aLAAgentSchema.getSmokeFlag();
		this.RgtAddress = aLAAgentSchema.getRgtAddress();
		this.BankCode = aLAAgentSchema.getBankCode();
		this.BankAccNo = aLAAgentSchema.getBankAccNo();
		this.Operator = aLAAgentSchema.getOperator();
		this.MakeDate = fDate.getDate( aLAAgentSchema.getMakeDate());
		this.MakeTime = aLAAgentSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAAgentSchema.getModifyDate());
		this.ModifyTime = aLAAgentSchema.getModifyTime();
		this.BranchType = aLAAgentSchema.getBranchType();
		this.TrainPeriods = aLAAgentSchema.getTrainPeriods();
		this.BranchCode = aLAAgentSchema.getBranchCode();
		this.Age = aLAAgentSchema.getAge();
		this.ChannelName = aLAAgentSchema.getChannelName();
		this.IDType = aLAAgentSchema.getIDType();
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
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("EntryNo") == null )
				this.EntryNo = null;
			else
				this.EntryNo = rs.getString("EntryNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			this.Birthday = rs.getDate("Birthday");
			if( rs.getString("NativePlace") == null )
				this.NativePlace = null;
			else
				this.NativePlace = rs.getString("NativePlace").trim();

			if( rs.getString("Nationality") == null )
				this.Nationality = null;
			else
				this.Nationality = rs.getString("Nationality").trim();

			if( rs.getString("Marriage") == null )
				this.Marriage = null;
			else
				this.Marriage = rs.getString("Marriage").trim();

			if( rs.getString("CreditGrade") == null )
				this.CreditGrade = null;
			else
				this.CreditGrade = rs.getString("CreditGrade").trim();

			if( rs.getString("HomeAddressCode") == null )
				this.HomeAddressCode = null;
			else
				this.HomeAddressCode = rs.getString("HomeAddressCode").trim();

			if( rs.getString("HomeAddress") == null )
				this.HomeAddress = null;
			else
				this.HomeAddress = rs.getString("HomeAddress").trim();

			if( rs.getString("PostalAddress") == null )
				this.PostalAddress = null;
			else
				this.PostalAddress = rs.getString("PostalAddress").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("BP") == null )
				this.BP = null;
			else
				this.BP = rs.getString("BP").trim();

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			this.MarriageDate = rs.getDate("MarriageDate");
			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Source") == null )
				this.Source = null;
			else
				this.Source = rs.getString("Source").trim();

			if( rs.getString("BloodType") == null )
				this.BloodType = null;
			else
				this.BloodType = rs.getString("BloodType").trim();

			if( rs.getString("PolityVisage") == null )
				this.PolityVisage = null;
			else
				this.PolityVisage = rs.getString("PolityVisage").trim();

			if( rs.getString("Degree") == null )
				this.Degree = null;
			else
				this.Degree = rs.getString("Degree").trim();

			if( rs.getString("GraduateSchool") == null )
				this.GraduateSchool = null;
			else
				this.GraduateSchool = rs.getString("GraduateSchool").trim();

			if( rs.getString("Speciality") == null )
				this.Speciality = null;
			else
				this.Speciality = rs.getString("Speciality").trim();

			if( rs.getString("PostTitle") == null )
				this.PostTitle = null;
			else
				this.PostTitle = rs.getString("PostTitle").trim();

			if( rs.getString("ForeignLevel") == null )
				this.ForeignLevel = null;
			else
				this.ForeignLevel = rs.getString("ForeignLevel").trim();

			this.WorkAge = rs.getInt("WorkAge");
			if( rs.getString("OldCom") == null )
				this.OldCom = null;
			else
				this.OldCom = rs.getString("OldCom").trim();

			if( rs.getString("OldOccupation") == null )
				this.OldOccupation = null;
			else
				this.OldOccupation = rs.getString("OldOccupation").trim();

			if( rs.getString("HeadShip") == null )
				this.HeadShip = null;
			else
				this.HeadShip = rs.getString("HeadShip").trim();

			if( rs.getString("RecommendAgent") == null )
				this.RecommendAgent = null;
			else
				this.RecommendAgent = rs.getString("RecommendAgent").trim();

			if( rs.getString("Business") == null )
				this.Business = null;
			else
				this.Business = rs.getString("Business").trim();

			if( rs.getString("SaleQuaf") == null )
				this.SaleQuaf = null;
			else
				this.SaleQuaf = rs.getString("SaleQuaf").trim();

			if( rs.getString("QuafNo") == null )
				this.QuafNo = null;
			else
				this.QuafNo = rs.getString("QuafNo").trim();

			this.QuafStartDate = rs.getDate("QuafStartDate");
			this.QuafEndDate = rs.getDate("QuafEndDate");
			if( rs.getString("DevNo1") == null )
				this.DevNo1 = null;
			else
				this.DevNo1 = rs.getString("DevNo1").trim();

			if( rs.getString("DevNo2") == null )
				this.DevNo2 = null;
			else
				this.DevNo2 = rs.getString("DevNo2").trim();

			if( rs.getString("RetainContNo") == null )
				this.RetainContNo = null;
			else
				this.RetainContNo = rs.getString("RetainContNo").trim();

			if( rs.getString("AgentKind") == null )
				this.AgentKind = null;
			else
				this.AgentKind = rs.getString("AgentKind").trim();

			if( rs.getString("DevGrade") == null )
				this.DevGrade = null;
			else
				this.DevGrade = rs.getString("DevGrade").trim();

			if( rs.getString("InsideFlag") == null )
				this.InsideFlag = null;
			else
				this.InsideFlag = rs.getString("InsideFlag").trim();

			if( rs.getString("FullTimeFlag") == null )
				this.FullTimeFlag = null;
			else
				this.FullTimeFlag = rs.getString("FullTimeFlag").trim();

			if( rs.getString("NoWorkFlag") == null )
				this.NoWorkFlag = null;
			else
				this.NoWorkFlag = rs.getString("NoWorkFlag").trim();

			this.TrainDate = rs.getDate("TrainDate");
			this.EmployDate = rs.getDate("EmployDate");
			this.InDueFormDate = rs.getDate("InDueFormDate");
			this.OutWorkDate = rs.getDate("OutWorkDate");
			if( rs.getString("RecommendNo") == null )
				this.RecommendNo = null;
			else
				this.RecommendNo = rs.getString("RecommendNo").trim();

			if( rs.getString("CautionerName") == null )
				this.CautionerName = null;
			else
				this.CautionerName = rs.getString("CautionerName").trim();

			if( rs.getString("CautionerSex") == null )
				this.CautionerSex = null;
			else
				this.CautionerSex = rs.getString("CautionerSex").trim();

			if( rs.getString("CautionerID") == null )
				this.CautionerID = null;
			else
				this.CautionerID = rs.getString("CautionerID").trim();

			this.CautionerBirthday = rs.getDate("CautionerBirthday");
			if( rs.getString("Approver") == null )
				this.Approver = null;
			else
				this.Approver = rs.getString("Approver").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			this.AssuMoney = rs.getDouble("AssuMoney");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("AgentState") == null )
				this.AgentState = null;
			else
				this.AgentState = rs.getString("AgentState").trim();

			if( rs.getString("QualiPassFlag") == null )
				this.QualiPassFlag = null;
			else
				this.QualiPassFlag = rs.getString("QualiPassFlag").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("RgtAddress") == null )
				this.RgtAddress = null;
			else
				this.RgtAddress = rs.getString("RgtAddress").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

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

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("TrainPeriods") == null )
				this.TrainPeriods = null;
			else
				this.TrainPeriods = rs.getString("TrainPeriods").trim();

			if( rs.getString("BranchCode") == null )
				this.BranchCode = null;
			else
				this.BranchCode = rs.getString("BranchCode").trim();

			this.Age = rs.getDouble("Age");
			if( rs.getString("ChannelName") == null )
				this.ChannelName = null;
			else
				this.ChannelName = rs.getString("ChannelName").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAAgent表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAAgentSchema getSchema()
	{
		LAAgentSchema aLAAgentSchema = new LAAgentSchema();
		aLAAgentSchema.setSchema(this);
		return aLAAgentSchema;
	}

	public LAAgentDB getDB()
	{
		LAAgentDB aDBOper = new LAAgentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAgent描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EntryNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NativePlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Nationality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Marriage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreditGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostalAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MarriageDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Source)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BloodType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolityVisage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Degree)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GraduateSchool)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Speciality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostTitle)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForeignLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(WorkAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldOccupation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RecommendAgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Business)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleQuaf)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuafNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( QuafStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( QuafEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DevNo1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DevNo2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RetainContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DevGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsideFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FullTimeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoWorkFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TrainDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EmployDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InDueFormDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutWorkDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RecommendNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CautionerBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Approver)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssuMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QualiPassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TrainPeriods)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Age));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChannelName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAgent>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EntryNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			HomeAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Source = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BloodType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PolityVisage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			GraduateSchool = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Speciality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PostTitle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ForeignLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			WorkAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).intValue();
			OldCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			OldOccupation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			HeadShip = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			RecommendAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Business = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			SaleQuaf = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			QuafNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			QuafStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			QuafEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			DevNo1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			DevNo2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			RetainContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			AgentKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			DevGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			InsideFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			FullTimeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			NoWorkFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			TrainDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			EmployDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			InDueFormDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			OutWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			RecommendNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			CautionerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			CautionerSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			CautionerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			CautionerBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57,SysConst.PACKAGESPILTER));
			Approver = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59,SysConst.PACKAGESPILTER));
			AssuMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			AgentState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			QualiPassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			TrainPeriods = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			BranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			Age = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).doubleValue();
			ChannelName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentSchema";
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("EntryNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EntryNo));
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
		if (FCode.equalsIgnoreCase("NativePlace"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NativePlace));
		}
		if (FCode.equalsIgnoreCase("Nationality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Nationality));
		}
		if (FCode.equalsIgnoreCase("Marriage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Marriage));
		}
		if (FCode.equalsIgnoreCase("CreditGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreditGrade));
		}
		if (FCode.equalsIgnoreCase("HomeAddressCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddressCode));
		}
		if (FCode.equalsIgnoreCase("HomeAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddress));
		}
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostalAddress));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("BP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BP));
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("MarriageDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("Source"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Source));
		}
		if (FCode.equalsIgnoreCase("BloodType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BloodType));
		}
		if (FCode.equalsIgnoreCase("PolityVisage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolityVisage));
		}
		if (FCode.equalsIgnoreCase("Degree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Degree));
		}
		if (FCode.equalsIgnoreCase("GraduateSchool"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GraduateSchool));
		}
		if (FCode.equalsIgnoreCase("Speciality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Speciality));
		}
		if (FCode.equalsIgnoreCase("PostTitle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostTitle));
		}
		if (FCode.equalsIgnoreCase("ForeignLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForeignLevel));
		}
		if (FCode.equalsIgnoreCase("WorkAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkAge));
		}
		if (FCode.equalsIgnoreCase("OldCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldCom));
		}
		if (FCode.equalsIgnoreCase("OldOccupation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldOccupation));
		}
		if (FCode.equalsIgnoreCase("HeadShip"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadShip));
		}
		if (FCode.equalsIgnoreCase("RecommendAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecommendAgent));
		}
		if (FCode.equalsIgnoreCase("Business"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Business));
		}
		if (FCode.equalsIgnoreCase("SaleQuaf"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleQuaf));
		}
		if (FCode.equalsIgnoreCase("QuafNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuafNo));
		}
		if (FCode.equalsIgnoreCase("QuafStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getQuafStartDate()));
		}
		if (FCode.equalsIgnoreCase("QuafEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getQuafEndDate()));
		}
		if (FCode.equalsIgnoreCase("DevNo1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DevNo1));
		}
		if (FCode.equalsIgnoreCase("DevNo2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DevNo2));
		}
		if (FCode.equalsIgnoreCase("RetainContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetainContNo));
		}
		if (FCode.equalsIgnoreCase("AgentKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentKind));
		}
		if (FCode.equalsIgnoreCase("DevGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DevGrade));
		}
		if (FCode.equalsIgnoreCase("InsideFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsideFlag));
		}
		if (FCode.equalsIgnoreCase("FullTimeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FullTimeFlag));
		}
		if (FCode.equalsIgnoreCase("NoWorkFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoWorkFlag));
		}
		if (FCode.equalsIgnoreCase("TrainDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTrainDate()));
		}
		if (FCode.equalsIgnoreCase("EmployDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEmployDate()));
		}
		if (FCode.equalsIgnoreCase("InDueFormDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInDueFormDate()));
		}
		if (FCode.equalsIgnoreCase("OutWorkDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutWorkDate()));
		}
		if (FCode.equalsIgnoreCase("RecommendNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecommendNo));
		}
		if (FCode.equalsIgnoreCase("CautionerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerName));
		}
		if (FCode.equalsIgnoreCase("CautionerSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerSex));
		}
		if (FCode.equalsIgnoreCase("CautionerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerID));
		}
		if (FCode.equalsIgnoreCase("CautionerBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCautionerBirthday()));
		}
		if (FCode.equalsIgnoreCase("Approver"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Approver));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("AssuMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuMoney));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("AgentState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentState));
		}
		if (FCode.equalsIgnoreCase("QualiPassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QualiPassFlag));
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equalsIgnoreCase("RgtAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtAddress));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
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
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("TrainPeriods"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TrainPeriods));
		}
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchCode));
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Age));
		}
		if (FCode.equalsIgnoreCase("ChannelName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChannelName));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
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
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EntryNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(HomeAddressCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BP);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Source);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BloodType);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(PolityVisage);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(GraduateSchool);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Speciality);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PostTitle);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ForeignLevel);
				break;
			case 30:
				strFieldValue = String.valueOf(WorkAge);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(OldCom);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(OldOccupation);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(HeadShip);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(RecommendAgent);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Business);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(SaleQuaf);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(QuafNo);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getQuafStartDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getQuafEndDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(DevNo1);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(DevNo2);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(RetainContNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AgentKind);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(DevGrade);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(InsideFlag);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(FullTimeFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(NoWorkFlag);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTrainDate()));
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEmployDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInDueFormDate()));
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutWorkDate()));
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(RecommendNo);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(CautionerName);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(CautionerSex);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(CautionerID);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCautionerBirthday()));
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(Approver);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 59:
				strFieldValue = String.valueOf(AssuMoney);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(AgentState);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(QualiPassFlag);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(TrainPeriods);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(BranchCode);
				break;
			case 75:
				strFieldValue = String.valueOf(Age);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(ChannelName);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(IDType);
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

		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
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
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
		}
		if (FCode.equalsIgnoreCase("EntryNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EntryNo = FValue.trim();
			}
			else
				EntryNo = null;
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
		if (FCode.equalsIgnoreCase("Marriage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Marriage = FValue.trim();
			}
			else
				Marriage = null;
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
		if (FCode.equalsIgnoreCase("HomeAddressCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeAddressCode = FValue.trim();
			}
			else
				HomeAddressCode = null;
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
		if (FCode.equalsIgnoreCase("PostalAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostalAddress = FValue.trim();
			}
			else
				PostalAddress = null;
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
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("BP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BP = FValue.trim();
			}
			else
				BP = null;
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
		if (FCode.equalsIgnoreCase("EMail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail = FValue.trim();
			}
			else
				EMail = null;
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
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("Source"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Source = FValue.trim();
			}
			else
				Source = null;
		}
		if (FCode.equalsIgnoreCase("BloodType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BloodType = FValue.trim();
			}
			else
				BloodType = null;
		}
		if (FCode.equalsIgnoreCase("PolityVisage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolityVisage = FValue.trim();
			}
			else
				PolityVisage = null;
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
		if (FCode.equalsIgnoreCase("GraduateSchool"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GraduateSchool = FValue.trim();
			}
			else
				GraduateSchool = null;
		}
		if (FCode.equalsIgnoreCase("Speciality"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Speciality = FValue.trim();
			}
			else
				Speciality = null;
		}
		if (FCode.equalsIgnoreCase("PostTitle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostTitle = FValue.trim();
			}
			else
				PostTitle = null;
		}
		if (FCode.equalsIgnoreCase("ForeignLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForeignLevel = FValue.trim();
			}
			else
				ForeignLevel = null;
		}
		if (FCode.equalsIgnoreCase("WorkAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WorkAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("OldCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldCom = FValue.trim();
			}
			else
				OldCom = null;
		}
		if (FCode.equalsIgnoreCase("OldOccupation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldOccupation = FValue.trim();
			}
			else
				OldOccupation = null;
		}
		if (FCode.equalsIgnoreCase("HeadShip"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadShip = FValue.trim();
			}
			else
				HeadShip = null;
		}
		if (FCode.equalsIgnoreCase("RecommendAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RecommendAgent = FValue.trim();
			}
			else
				RecommendAgent = null;
		}
		if (FCode.equalsIgnoreCase("Business"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Business = FValue.trim();
			}
			else
				Business = null;
		}
		if (FCode.equalsIgnoreCase("SaleQuaf"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleQuaf = FValue.trim();
			}
			else
				SaleQuaf = null;
		}
		if (FCode.equalsIgnoreCase("QuafNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuafNo = FValue.trim();
			}
			else
				QuafNo = null;
		}
		if (FCode.equalsIgnoreCase("QuafStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				QuafStartDate = fDate.getDate( FValue );
			}
			else
				QuafStartDate = null;
		}
		if (FCode.equalsIgnoreCase("QuafEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				QuafEndDate = fDate.getDate( FValue );
			}
			else
				QuafEndDate = null;
		}
		if (FCode.equalsIgnoreCase("DevNo1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DevNo1 = FValue.trim();
			}
			else
				DevNo1 = null;
		}
		if (FCode.equalsIgnoreCase("DevNo2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DevNo2 = FValue.trim();
			}
			else
				DevNo2 = null;
		}
		if (FCode.equalsIgnoreCase("RetainContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RetainContNo = FValue.trim();
			}
			else
				RetainContNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentKind = FValue.trim();
			}
			else
				AgentKind = null;
		}
		if (FCode.equalsIgnoreCase("DevGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DevGrade = FValue.trim();
			}
			else
				DevGrade = null;
		}
		if (FCode.equalsIgnoreCase("InsideFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsideFlag = FValue.trim();
			}
			else
				InsideFlag = null;
		}
		if (FCode.equalsIgnoreCase("FullTimeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FullTimeFlag = FValue.trim();
			}
			else
				FullTimeFlag = null;
		}
		if (FCode.equalsIgnoreCase("NoWorkFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoWorkFlag = FValue.trim();
			}
			else
				NoWorkFlag = null;
		}
		if (FCode.equalsIgnoreCase("TrainDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TrainDate = fDate.getDate( FValue );
			}
			else
				TrainDate = null;
		}
		if (FCode.equalsIgnoreCase("EmployDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EmployDate = fDate.getDate( FValue );
			}
			else
				EmployDate = null;
		}
		if (FCode.equalsIgnoreCase("InDueFormDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InDueFormDate = fDate.getDate( FValue );
			}
			else
				InDueFormDate = null;
		}
		if (FCode.equalsIgnoreCase("OutWorkDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutWorkDate = fDate.getDate( FValue );
			}
			else
				OutWorkDate = null;
		}
		if (FCode.equalsIgnoreCase("RecommendNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RecommendNo = FValue.trim();
			}
			else
				RecommendNo = null;
		}
		if (FCode.equalsIgnoreCase("CautionerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerName = FValue.trim();
			}
			else
				CautionerName = null;
		}
		if (FCode.equalsIgnoreCase("CautionerSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerSex = FValue.trim();
			}
			else
				CautionerSex = null;
		}
		if (FCode.equalsIgnoreCase("CautionerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerID = FValue.trim();
			}
			else
				CautionerID = null;
		}
		if (FCode.equalsIgnoreCase("CautionerBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CautionerBirthday = fDate.getDate( FValue );
			}
			else
				CautionerBirthday = null;
		}
		if (FCode.equalsIgnoreCase("Approver"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Approver = FValue.trim();
			}
			else
				Approver = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("AssuMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AssuMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("AgentState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentState = FValue.trim();
			}
			else
				AgentState = null;
		}
		if (FCode.equalsIgnoreCase("QualiPassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QualiPassFlag = FValue.trim();
			}
			else
				QualiPassFlag = null;
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
		if (FCode.equalsIgnoreCase("RgtAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtAddress = FValue.trim();
			}
			else
				RgtAddress = null;
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
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("TrainPeriods"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TrainPeriods = FValue.trim();
			}
			else
				TrainPeriods = null;
		}
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchCode = FValue.trim();
			}
			else
				BranchCode = null;
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Age = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChannelName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChannelName = FValue.trim();
			}
			else
				ChannelName = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAAgentSchema other = (LAAgentSchema)otherObject;
		return
			AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& ManageCom.equals(other.getManageCom())
			&& Password.equals(other.getPassword())
			&& EntryNo.equals(other.getEntryNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& NativePlace.equals(other.getNativePlace())
			&& Nationality.equals(other.getNationality())
			&& Marriage.equals(other.getMarriage())
			&& CreditGrade.equals(other.getCreditGrade())
			&& HomeAddressCode.equals(other.getHomeAddressCode())
			&& HomeAddress.equals(other.getHomeAddress())
			&& PostalAddress.equals(other.getPostalAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& BP.equals(other.getBP())
			&& Mobile.equals(other.getMobile())
			&& EMail.equals(other.getEMail())
			&& fDate.getString(MarriageDate).equals(other.getMarriageDate())
			&& IDNo.equals(other.getIDNo())
			&& Source.equals(other.getSource())
			&& BloodType.equals(other.getBloodType())
			&& PolityVisage.equals(other.getPolityVisage())
			&& Degree.equals(other.getDegree())
			&& GraduateSchool.equals(other.getGraduateSchool())
			&& Speciality.equals(other.getSpeciality())
			&& PostTitle.equals(other.getPostTitle())
			&& ForeignLevel.equals(other.getForeignLevel())
			&& WorkAge == other.getWorkAge()
			&& OldCom.equals(other.getOldCom())
			&& OldOccupation.equals(other.getOldOccupation())
			&& HeadShip.equals(other.getHeadShip())
			&& RecommendAgent.equals(other.getRecommendAgent())
			&& Business.equals(other.getBusiness())
			&& SaleQuaf.equals(other.getSaleQuaf())
			&& QuafNo.equals(other.getQuafNo())
			&& fDate.getString(QuafStartDate).equals(other.getQuafStartDate())
			&& fDate.getString(QuafEndDate).equals(other.getQuafEndDate())
			&& DevNo1.equals(other.getDevNo1())
			&& DevNo2.equals(other.getDevNo2())
			&& RetainContNo.equals(other.getRetainContNo())
			&& AgentKind.equals(other.getAgentKind())
			&& DevGrade.equals(other.getDevGrade())
			&& InsideFlag.equals(other.getInsideFlag())
			&& FullTimeFlag.equals(other.getFullTimeFlag())
			&& NoWorkFlag.equals(other.getNoWorkFlag())
			&& fDate.getString(TrainDate).equals(other.getTrainDate())
			&& fDate.getString(EmployDate).equals(other.getEmployDate())
			&& fDate.getString(InDueFormDate).equals(other.getInDueFormDate())
			&& fDate.getString(OutWorkDate).equals(other.getOutWorkDate())
			&& RecommendNo.equals(other.getRecommendNo())
			&& CautionerName.equals(other.getCautionerName())
			&& CautionerSex.equals(other.getCautionerSex())
			&& CautionerID.equals(other.getCautionerID())
			&& fDate.getString(CautionerBirthday).equals(other.getCautionerBirthday())
			&& Approver.equals(other.getApprover())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& AssuMoney == other.getAssuMoney()
			&& Remark.equals(other.getRemark())
			&& AgentState.equals(other.getAgentState())
			&& QualiPassFlag.equals(other.getQualiPassFlag())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& RgtAddress.equals(other.getRgtAddress())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchType.equals(other.getBranchType())
			&& TrainPeriods.equals(other.getTrainPeriods())
			&& BranchCode.equals(other.getBranchCode())
			&& Age == other.getAge()
			&& ChannelName.equals(other.getChannelName())
			&& IDType.equals(other.getIDType());
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
		if( strFieldName.equals("AgentCode") ) {
			return 0;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("Password") ) {
			return 3;
		}
		if( strFieldName.equals("EntryNo") ) {
			return 4;
		}
		if( strFieldName.equals("Name") ) {
			return 5;
		}
		if( strFieldName.equals("Sex") ) {
			return 6;
		}
		if( strFieldName.equals("Birthday") ) {
			return 7;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 8;
		}
		if( strFieldName.equals("Nationality") ) {
			return 9;
		}
		if( strFieldName.equals("Marriage") ) {
			return 10;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 11;
		}
		if( strFieldName.equals("HomeAddressCode") ) {
			return 12;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 13;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 14;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 15;
		}
		if( strFieldName.equals("Phone") ) {
			return 16;
		}
		if( strFieldName.equals("BP") ) {
			return 17;
		}
		if( strFieldName.equals("Mobile") ) {
			return 18;
		}
		if( strFieldName.equals("EMail") ) {
			return 19;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 20;
		}
		if( strFieldName.equals("IDNo") ) {
			return 21;
		}
		if( strFieldName.equals("Source") ) {
			return 22;
		}
		if( strFieldName.equals("BloodType") ) {
			return 23;
		}
		if( strFieldName.equals("PolityVisage") ) {
			return 24;
		}
		if( strFieldName.equals("Degree") ) {
			return 25;
		}
		if( strFieldName.equals("GraduateSchool") ) {
			return 26;
		}
		if( strFieldName.equals("Speciality") ) {
			return 27;
		}
		if( strFieldName.equals("PostTitle") ) {
			return 28;
		}
		if( strFieldName.equals("ForeignLevel") ) {
			return 29;
		}
		if( strFieldName.equals("WorkAge") ) {
			return 30;
		}
		if( strFieldName.equals("OldCom") ) {
			return 31;
		}
		if( strFieldName.equals("OldOccupation") ) {
			return 32;
		}
		if( strFieldName.equals("HeadShip") ) {
			return 33;
		}
		if( strFieldName.equals("RecommendAgent") ) {
			return 34;
		}
		if( strFieldName.equals("Business") ) {
			return 35;
		}
		if( strFieldName.equals("SaleQuaf") ) {
			return 36;
		}
		if( strFieldName.equals("QuafNo") ) {
			return 37;
		}
		if( strFieldName.equals("QuafStartDate") ) {
			return 38;
		}
		if( strFieldName.equals("QuafEndDate") ) {
			return 39;
		}
		if( strFieldName.equals("DevNo1") ) {
			return 40;
		}
		if( strFieldName.equals("DevNo2") ) {
			return 41;
		}
		if( strFieldName.equals("RetainContNo") ) {
			return 42;
		}
		if( strFieldName.equals("AgentKind") ) {
			return 43;
		}
		if( strFieldName.equals("DevGrade") ) {
			return 44;
		}
		if( strFieldName.equals("InsideFlag") ) {
			return 45;
		}
		if( strFieldName.equals("FullTimeFlag") ) {
			return 46;
		}
		if( strFieldName.equals("NoWorkFlag") ) {
			return 47;
		}
		if( strFieldName.equals("TrainDate") ) {
			return 48;
		}
		if( strFieldName.equals("EmployDate") ) {
			return 49;
		}
		if( strFieldName.equals("InDueFormDate") ) {
			return 50;
		}
		if( strFieldName.equals("OutWorkDate") ) {
			return 51;
		}
		if( strFieldName.equals("RecommendNo") ) {
			return 52;
		}
		if( strFieldName.equals("CautionerName") ) {
			return 53;
		}
		if( strFieldName.equals("CautionerSex") ) {
			return 54;
		}
		if( strFieldName.equals("CautionerID") ) {
			return 55;
		}
		if( strFieldName.equals("CautionerBirthday") ) {
			return 56;
		}
		if( strFieldName.equals("Approver") ) {
			return 57;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 58;
		}
		if( strFieldName.equals("AssuMoney") ) {
			return 59;
		}
		if( strFieldName.equals("Remark") ) {
			return 60;
		}
		if( strFieldName.equals("AgentState") ) {
			return 61;
		}
		if( strFieldName.equals("QualiPassFlag") ) {
			return 62;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 63;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 64;
		}
		if( strFieldName.equals("BankCode") ) {
			return 65;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 66;
		}
		if( strFieldName.equals("Operator") ) {
			return 67;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 68;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 69;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 70;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 71;
		}
		if( strFieldName.equals("BranchType") ) {
			return 72;
		}
		if( strFieldName.equals("TrainPeriods") ) {
			return 73;
		}
		if( strFieldName.equals("BranchCode") ) {
			return 74;
		}
		if( strFieldName.equals("Age") ) {
			return 75;
		}
		if( strFieldName.equals("ChannelName") ) {
			return 76;
		}
		if( strFieldName.equals("IDType") ) {
			return 77;
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
				strFieldName = "AgentCode";
				break;
			case 1:
				strFieldName = "AgentGroup";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "Password";
				break;
			case 4:
				strFieldName = "EntryNo";
				break;
			case 5:
				strFieldName = "Name";
				break;
			case 6:
				strFieldName = "Sex";
				break;
			case 7:
				strFieldName = "Birthday";
				break;
			case 8:
				strFieldName = "NativePlace";
				break;
			case 9:
				strFieldName = "Nationality";
				break;
			case 10:
				strFieldName = "Marriage";
				break;
			case 11:
				strFieldName = "CreditGrade";
				break;
			case 12:
				strFieldName = "HomeAddressCode";
				break;
			case 13:
				strFieldName = "HomeAddress";
				break;
			case 14:
				strFieldName = "PostalAddress";
				break;
			case 15:
				strFieldName = "ZipCode";
				break;
			case 16:
				strFieldName = "Phone";
				break;
			case 17:
				strFieldName = "BP";
				break;
			case 18:
				strFieldName = "Mobile";
				break;
			case 19:
				strFieldName = "EMail";
				break;
			case 20:
				strFieldName = "MarriageDate";
				break;
			case 21:
				strFieldName = "IDNo";
				break;
			case 22:
				strFieldName = "Source";
				break;
			case 23:
				strFieldName = "BloodType";
				break;
			case 24:
				strFieldName = "PolityVisage";
				break;
			case 25:
				strFieldName = "Degree";
				break;
			case 26:
				strFieldName = "GraduateSchool";
				break;
			case 27:
				strFieldName = "Speciality";
				break;
			case 28:
				strFieldName = "PostTitle";
				break;
			case 29:
				strFieldName = "ForeignLevel";
				break;
			case 30:
				strFieldName = "WorkAge";
				break;
			case 31:
				strFieldName = "OldCom";
				break;
			case 32:
				strFieldName = "OldOccupation";
				break;
			case 33:
				strFieldName = "HeadShip";
				break;
			case 34:
				strFieldName = "RecommendAgent";
				break;
			case 35:
				strFieldName = "Business";
				break;
			case 36:
				strFieldName = "SaleQuaf";
				break;
			case 37:
				strFieldName = "QuafNo";
				break;
			case 38:
				strFieldName = "QuafStartDate";
				break;
			case 39:
				strFieldName = "QuafEndDate";
				break;
			case 40:
				strFieldName = "DevNo1";
				break;
			case 41:
				strFieldName = "DevNo2";
				break;
			case 42:
				strFieldName = "RetainContNo";
				break;
			case 43:
				strFieldName = "AgentKind";
				break;
			case 44:
				strFieldName = "DevGrade";
				break;
			case 45:
				strFieldName = "InsideFlag";
				break;
			case 46:
				strFieldName = "FullTimeFlag";
				break;
			case 47:
				strFieldName = "NoWorkFlag";
				break;
			case 48:
				strFieldName = "TrainDate";
				break;
			case 49:
				strFieldName = "EmployDate";
				break;
			case 50:
				strFieldName = "InDueFormDate";
				break;
			case 51:
				strFieldName = "OutWorkDate";
				break;
			case 52:
				strFieldName = "RecommendNo";
				break;
			case 53:
				strFieldName = "CautionerName";
				break;
			case 54:
				strFieldName = "CautionerSex";
				break;
			case 55:
				strFieldName = "CautionerID";
				break;
			case 56:
				strFieldName = "CautionerBirthday";
				break;
			case 57:
				strFieldName = "Approver";
				break;
			case 58:
				strFieldName = "ApproveDate";
				break;
			case 59:
				strFieldName = "AssuMoney";
				break;
			case 60:
				strFieldName = "Remark";
				break;
			case 61:
				strFieldName = "AgentState";
				break;
			case 62:
				strFieldName = "QualiPassFlag";
				break;
			case 63:
				strFieldName = "SmokeFlag";
				break;
			case 64:
				strFieldName = "RgtAddress";
				break;
			case 65:
				strFieldName = "BankCode";
				break;
			case 66:
				strFieldName = "BankAccNo";
				break;
			case 67:
				strFieldName = "Operator";
				break;
			case 68:
				strFieldName = "MakeDate";
				break;
			case 69:
				strFieldName = "MakeTime";
				break;
			case 70:
				strFieldName = "ModifyDate";
				break;
			case 71:
				strFieldName = "ModifyTime";
				break;
			case 72:
				strFieldName = "BranchType";
				break;
			case 73:
				strFieldName = "TrainPeriods";
				break;
			case 74:
				strFieldName = "BranchCode";
				break;
			case 75:
				strFieldName = "Age";
				break;
			case 76:
				strFieldName = "ChannelName";
				break;
			case 77:
				strFieldName = "IDType";
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
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EntryNo") ) {
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
		if( strFieldName.equals("NativePlace") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Nationality") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Marriage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeAddressCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Source") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BloodType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolityVisage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Degree") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GraduateSchool") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Speciality") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostTitle") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForeignLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OldCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldOccupation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadShip") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RecommendAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Business") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleQuaf") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuafNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuafStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("QuafEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DevNo1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DevNo2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RetainContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DevGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsideFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FullTimeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoWorkFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrainDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EmployDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InDueFormDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutWorkDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RecommendNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Approver") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AssuMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QualiPassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
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
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrainPeriods") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Age") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChannelName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 49:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 50:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 59:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 76:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
