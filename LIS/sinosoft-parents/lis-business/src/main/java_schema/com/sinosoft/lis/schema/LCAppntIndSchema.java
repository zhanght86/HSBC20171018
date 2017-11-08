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
import com.sinosoft.lis.db.LCAppntIndDB;

/*
 * <p>ClassName: LCAppntIndSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LCAppntIndSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCAppntIndSchema.class);

	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 客户号码 */
	private String CustomerNo;
	/** 客户内部号码 */
	private String SequenceNo;
	/** 投保人级别 */
	private String AppntGrade;
	/** 被保人与投保人关系 */
	private String RelationToInsured;
	/** 密码 */
	private String Password;
	/** 客户姓名 */
	private String Name;
	/** 客户性别 */
	private String Sex;
	/** 客户出生日期 */
	private Date Birthday;
	/** 籍贯 */
	private String NativePlace;
	/** 民族 */
	private String Nationality;
	/** 婚姻状况 */
	private String Marriage;
	/** 结婚日期 */
	private Date MarriageDate;
	/** 职业类别 */
	private String OccupationType;
	/** 开始工作日期 */
	private Date StartWorkDate;
	/** 工资 */
	private double Salary;
	/** 健康状况 */
	private String Health;
	/** 身高 */
	private double Stature;
	/** 体重 */
	private double Avoirdupois;
	/** 信用等级 */
	private String CreditGrade;
	/** 证件类型 */
	private String IDType;
	/** 属性 */
	private String Proterty;
	/** 证件号码 */
	private String IDNo;
	/** 其它证件类型 */
	private String OthIDType;
	/** 其它证件号码 */
	private String OthIDNo;
	/** Ic卡号 */
	private String ICNo;
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
	/** 入司日期 */
	private Date JoinCompanyDate;
	/** 职位 */
	private String Position;
	/** 单位编码 */
	private String GrpNo;
	/** 单位名称 */
	private String GrpName;
	/** 单位电话 */
	private String GrpPhone;
	/** 单位地址编码 */
	private String GrpAddressCode;
	/** 单位地址 */
	private String GrpAddress;
	/** 死亡日期 */
	private Date DeathDate;
	/** 备注 */
	private String Remark;
	/** 状态 */
	private String State;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 职业（工种） */
	private String WorkType;
	/** 兼职（工种） */
	private String PluralityType;
	/** 学历 */
	private String Degree;
	/** 职业代码 */
	private String OccupationCode;
	/** 单位邮编 */
	private String GrpZipCode;
	/** 是否吸烟标志 */
	private String SmokeFlag;
	/** 户口所在地 */
	private String RgtAddress;
	/** 家庭地址邮政编码 */
	private String HomeZipCode;
	/** 联系电话2 */
	private String Phone2;

	public static final int FIELDNUM = 55;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCAppntIndSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "PolNo";
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
		LCAppntIndSchema cloned = (LCAppntIndSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/**
	* 对于团体投保，需要用该字段纪录当前保单对应在<p>
	* 集体内的编号。
	*/
	public String getSequenceNo()
	{
		return SequenceNo;
	}
	public void setSequenceNo(String aSequenceNo)
	{
		SequenceNo = aSequenceNo;
	}
	/**
	* M ---主投保人<p>
	* S ---从头保人
	*/
	public String getAppntGrade()
	{
		return AppntGrade;
	}
	public void setAppntGrade(String aAppntGrade)
	{
		AppntGrade = aAppntGrade;
	}
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		RelationToInsured = aRelationToInsured;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
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

	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
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

	public String getHealth()
	{
		return Health;
	}
	public void setHealth(String aHealth)
	{
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

	public String getCreditGrade()
	{
		return CreditGrade;
	}
	public void setCreditGrade(String aCreditGrade)
	{
		CreditGrade = aCreditGrade;
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
		IDType = aIDType;
	}
	public String getProterty()
	{
		return Proterty;
	}
	public void setProterty(String aProterty)
	{
		Proterty = aProterty;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getOthIDType()
	{
		return OthIDType;
	}
	public void setOthIDType(String aOthIDType)
	{
		OthIDType = aOthIDType;
	}
	public String getOthIDNo()
	{
		return OthIDNo;
	}
	public void setOthIDNo(String aOthIDNo)
	{
		OthIDNo = aOthIDNo;
	}
	public String getICNo()
	{
		return ICNo;
	}
	public void setICNo(String aICNo)
	{
		ICNo = aICNo;
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

	public String getPosition()
	{
		return Position;
	}
	public void setPosition(String aPosition)
	{
		Position = aPosition;
	}
	public String getGrpNo()
	{
		return GrpNo;
	}
	public void setGrpNo(String aGrpNo)
	{
		GrpNo = aGrpNo;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		GrpName = aGrpName;
	}
	public String getGrpPhone()
	{
		return GrpPhone;
	}
	public void setGrpPhone(String aGrpPhone)
	{
		GrpPhone = aGrpPhone;
	}
	public String getGrpAddressCode()
	{
		return GrpAddressCode;
	}
	public void setGrpAddressCode(String aGrpAddressCode)
	{
		GrpAddressCode = aGrpAddressCode;
	}
	public String getGrpAddress()
	{
		return GrpAddress;
	}
	public void setGrpAddress(String aGrpAddress)
	{
		GrpAddress = aGrpAddress;
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

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	public String getWorkType()
	{
		return WorkType;
	}
	public void setWorkType(String aWorkType)
	{
		WorkType = aWorkType;
	}
	public String getPluralityType()
	{
		return PluralityType;
	}
	public void setPluralityType(String aPluralityType)
	{
		PluralityType = aPluralityType;
	}
	public String getDegree()
	{
		return Degree;
	}
	public void setDegree(String aDegree)
	{
		Degree = aDegree;
	}
	public String getOccupationCode()
	{
		return OccupationCode;
	}
	public void setOccupationCode(String aOccupationCode)
	{
		OccupationCode = aOccupationCode;
	}
	public String getGrpZipCode()
	{
		return GrpZipCode;
	}
	public void setGrpZipCode(String aGrpZipCode)
	{
		GrpZipCode = aGrpZipCode;
	}
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
	public String getHomeZipCode()
	{
		return HomeZipCode;
	}
	public void setHomeZipCode(String aHomeZipCode)
	{
		HomeZipCode = aHomeZipCode;
	}
	public String getPhone2()
	{
		return Phone2;
	}
	public void setPhone2(String aPhone2)
	{
		Phone2 = aPhone2;
	}

	/**
	* 使用另外一个 LCAppntIndSchema 对象给 Schema 赋值
	* @param: aLCAppntIndSchema LCAppntIndSchema
	**/
	public void setSchema(LCAppntIndSchema aLCAppntIndSchema)
	{
		this.PolNo = aLCAppntIndSchema.getPolNo();
		this.CustomerNo = aLCAppntIndSchema.getCustomerNo();
		this.SequenceNo = aLCAppntIndSchema.getSequenceNo();
		this.AppntGrade = aLCAppntIndSchema.getAppntGrade();
		this.RelationToInsured = aLCAppntIndSchema.getRelationToInsured();
		this.Password = aLCAppntIndSchema.getPassword();
		this.Name = aLCAppntIndSchema.getName();
		this.Sex = aLCAppntIndSchema.getSex();
		this.Birthday = fDate.getDate( aLCAppntIndSchema.getBirthday());
		this.NativePlace = aLCAppntIndSchema.getNativePlace();
		this.Nationality = aLCAppntIndSchema.getNationality();
		this.Marriage = aLCAppntIndSchema.getMarriage();
		this.MarriageDate = fDate.getDate( aLCAppntIndSchema.getMarriageDate());
		this.OccupationType = aLCAppntIndSchema.getOccupationType();
		this.StartWorkDate = fDate.getDate( aLCAppntIndSchema.getStartWorkDate());
		this.Salary = aLCAppntIndSchema.getSalary();
		this.Health = aLCAppntIndSchema.getHealth();
		this.Stature = aLCAppntIndSchema.getStature();
		this.Avoirdupois = aLCAppntIndSchema.getAvoirdupois();
		this.CreditGrade = aLCAppntIndSchema.getCreditGrade();
		this.IDType = aLCAppntIndSchema.getIDType();
		this.Proterty = aLCAppntIndSchema.getProterty();
		this.IDNo = aLCAppntIndSchema.getIDNo();
		this.OthIDType = aLCAppntIndSchema.getOthIDType();
		this.OthIDNo = aLCAppntIndSchema.getOthIDNo();
		this.ICNo = aLCAppntIndSchema.getICNo();
		this.HomeAddressCode = aLCAppntIndSchema.getHomeAddressCode();
		this.HomeAddress = aLCAppntIndSchema.getHomeAddress();
		this.PostalAddress = aLCAppntIndSchema.getPostalAddress();
		this.ZipCode = aLCAppntIndSchema.getZipCode();
		this.Phone = aLCAppntIndSchema.getPhone();
		this.BP = aLCAppntIndSchema.getBP();
		this.Mobile = aLCAppntIndSchema.getMobile();
		this.EMail = aLCAppntIndSchema.getEMail();
		this.JoinCompanyDate = fDate.getDate( aLCAppntIndSchema.getJoinCompanyDate());
		this.Position = aLCAppntIndSchema.getPosition();
		this.GrpNo = aLCAppntIndSchema.getGrpNo();
		this.GrpName = aLCAppntIndSchema.getGrpName();
		this.GrpPhone = aLCAppntIndSchema.getGrpPhone();
		this.GrpAddressCode = aLCAppntIndSchema.getGrpAddressCode();
		this.GrpAddress = aLCAppntIndSchema.getGrpAddress();
		this.DeathDate = fDate.getDate( aLCAppntIndSchema.getDeathDate());
		this.Remark = aLCAppntIndSchema.getRemark();
		this.State = aLCAppntIndSchema.getState();
		this.ModifyDate = fDate.getDate( aLCAppntIndSchema.getModifyDate());
		this.ModifyTime = aLCAppntIndSchema.getModifyTime();
		this.WorkType = aLCAppntIndSchema.getWorkType();
		this.PluralityType = aLCAppntIndSchema.getPluralityType();
		this.Degree = aLCAppntIndSchema.getDegree();
		this.OccupationCode = aLCAppntIndSchema.getOccupationCode();
		this.GrpZipCode = aLCAppntIndSchema.getGrpZipCode();
		this.SmokeFlag = aLCAppntIndSchema.getSmokeFlag();
		this.RgtAddress = aLCAppntIndSchema.getRgtAddress();
		this.HomeZipCode = aLCAppntIndSchema.getHomeZipCode();
		this.Phone2 = aLCAppntIndSchema.getPhone2();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("SequenceNo") == null )
				this.SequenceNo = null;
			else
				this.SequenceNo = rs.getString("SequenceNo").trim();

			if( rs.getString("AppntGrade") == null )
				this.AppntGrade = null;
			else
				this.AppntGrade = rs.getString("AppntGrade").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

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

			this.MarriageDate = rs.getDate("MarriageDate");
			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			this.StartWorkDate = rs.getDate("StartWorkDate");
			this.Salary = rs.getDouble("Salary");
			if( rs.getString("Health") == null )
				this.Health = null;
			else
				this.Health = rs.getString("Health").trim();

			this.Stature = rs.getDouble("Stature");
			this.Avoirdupois = rs.getDouble("Avoirdupois");
			if( rs.getString("CreditGrade") == null )
				this.CreditGrade = null;
			else
				this.CreditGrade = rs.getString("CreditGrade").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("Proterty") == null )
				this.Proterty = null;
			else
				this.Proterty = rs.getString("Proterty").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

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

			this.JoinCompanyDate = rs.getDate("JoinCompanyDate");
			if( rs.getString("Position") == null )
				this.Position = null;
			else
				this.Position = rs.getString("Position").trim();

			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("GrpPhone") == null )
				this.GrpPhone = null;
			else
				this.GrpPhone = rs.getString("GrpPhone").trim();

			if( rs.getString("GrpAddressCode") == null )
				this.GrpAddressCode = null;
			else
				this.GrpAddressCode = rs.getString("GrpAddressCode").trim();

			if( rs.getString("GrpAddress") == null )
				this.GrpAddress = null;
			else
				this.GrpAddress = rs.getString("GrpAddress").trim();

			this.DeathDate = rs.getDate("DeathDate");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("WorkType") == null )
				this.WorkType = null;
			else
				this.WorkType = rs.getString("WorkType").trim();

			if( rs.getString("PluralityType") == null )
				this.PluralityType = null;
			else
				this.PluralityType = rs.getString("PluralityType").trim();

			if( rs.getString("Degree") == null )
				this.Degree = null;
			else
				this.Degree = rs.getString("Degree").trim();

			if( rs.getString("OccupationCode") == null )
				this.OccupationCode = null;
			else
				this.OccupationCode = rs.getString("OccupationCode").trim();

			if( rs.getString("GrpZipCode") == null )
				this.GrpZipCode = null;
			else
				this.GrpZipCode = rs.getString("GrpZipCode").trim();

			if( rs.getString("SmokeFlag") == null )
				this.SmokeFlag = null;
			else
				this.SmokeFlag = rs.getString("SmokeFlag").trim();

			if( rs.getString("RgtAddress") == null )
				this.RgtAddress = null;
			else
				this.RgtAddress = rs.getString("RgtAddress").trim();

			if( rs.getString("HomeZipCode") == null )
				this.HomeZipCode = null;
			else
				this.HomeZipCode = rs.getString("HomeZipCode").trim();

			if( rs.getString("Phone2") == null )
				this.Phone2 = null;
			else
				this.Phone2 = rs.getString("Phone2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCAppntInd表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntIndSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCAppntIndSchema getSchema()
	{
		LCAppntIndSchema aLCAppntIndSchema = new LCAppntIndSchema();
		aLCAppntIndSchema.setSchema(this);
		return aLCAppntIndSchema;
	}

	public LCAppntIndDB getDB()
	{
		LCAppntIndDB aDBOper = new LCAppntIndDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAppntInd描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SequenceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NativePlace)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Nationality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Marriage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MarriageDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartWorkDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Salary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Health)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Stature));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Avoirdupois));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CreditGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Proterty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostalAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BP)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( JoinCompanyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Position)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DeathDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PluralityType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Degree)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SmokeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAppntInd>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SequenceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppntGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StartWorkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Stature = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			Avoirdupois = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Proterty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			OthIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			OthIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ICNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			HomeAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			JoinCompanyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GrpPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			GrpAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			GrpAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			DeathDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			WorkType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			PluralityType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			OccupationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			GrpZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			SmokeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			RgtAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			HomeZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			Phone2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntIndSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("SequenceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SequenceNo));
		}
		if (FCode.equalsIgnoreCase("AppntGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntGrade));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
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
		if (FCode.equalsIgnoreCase("MarriageDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("StartWorkDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
		}
		if (FCode.equalsIgnoreCase("Salary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salary));
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
		if (FCode.equalsIgnoreCase("CreditGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CreditGrade));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("Proterty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Proterty));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
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
		if (FCode.equalsIgnoreCase("JoinCompanyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
		}
		if (FCode.equalsIgnoreCase("Position"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Position));
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("GrpPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPhone));
		}
		if (FCode.equalsIgnoreCase("GrpAddressCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddressCode));
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddress));
		}
		if (FCode.equalsIgnoreCase("DeathDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("WorkType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkType));
		}
		if (FCode.equalsIgnoreCase("PluralityType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PluralityType));
		}
		if (FCode.equalsIgnoreCase("Degree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Degree));
		}
		if (FCode.equalsIgnoreCase("OccupationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationCode));
		}
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpZipCode));
		}
		if (FCode.equalsIgnoreCase("SmokeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SmokeFlag));
		}
		if (FCode.equalsIgnoreCase("RgtAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtAddress));
		}
		if (FCode.equalsIgnoreCase("HomeZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeZipCode));
		}
		if (FCode.equalsIgnoreCase("Phone2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone2));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SequenceNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppntGrade);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartWorkDate()));
				break;
			case 15:
				strFieldValue = String.valueOf(Salary);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 17:
				strFieldValue = String.valueOf(Stature);
				break;
			case 18:
				strFieldValue = String.valueOf(Avoirdupois);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Proterty);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(OthIDType);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(OthIDNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ICNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(HomeAddressCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BP);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getJoinCompanyDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(GrpPhone);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(GrpAddressCode);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(GrpAddress);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDeathDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(WorkType);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(PluralityType);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(OccupationCode);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(GrpZipCode);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(SmokeFlag);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(RgtAddress);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(HomeZipCode);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(Phone2);
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("SequenceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SequenceNo = FValue.trim();
			}
			else
				SequenceNo = null;
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
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
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
		if (FCode.equalsIgnoreCase("MarriageDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MarriageDate = fDate.getDate( FValue );
			}
			else
				MarriageDate = null;
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
			if( FValue != null && !FValue.equals("") )
			{
				StartWorkDate = fDate.getDate( FValue );
			}
			else
				StartWorkDate = null;
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
		if (FCode.equalsIgnoreCase("CreditGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CreditGrade = FValue.trim();
			}
			else
				CreditGrade = null;
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
		if (FCode.equalsIgnoreCase("Proterty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Proterty = FValue.trim();
			}
			else
				Proterty = null;
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
		if (FCode.equalsIgnoreCase("JoinCompanyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				JoinCompanyDate = fDate.getDate( FValue );
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
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNo = FValue.trim();
			}
			else
				GrpNo = null;
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
		if (FCode.equalsIgnoreCase("GrpPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPhone = FValue.trim();
			}
			else
				GrpPhone = null;
		}
		if (FCode.equalsIgnoreCase("GrpAddressCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpAddressCode = FValue.trim();
			}
			else
				GrpAddressCode = null;
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpAddress = FValue.trim();
			}
			else
				GrpAddress = null;
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
		if (FCode.equalsIgnoreCase("Degree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Degree = FValue.trim();
			}
			else
				Degree = null;
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
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpZipCode = FValue.trim();
			}
			else
				GrpZipCode = null;
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
		if (FCode.equalsIgnoreCase("HomeZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeZipCode = FValue.trim();
			}
			else
				HomeZipCode = null;
		}
		if (FCode.equalsIgnoreCase("Phone2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone2 = FValue.trim();
			}
			else
				Phone2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCAppntIndSchema other = (LCAppntIndSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& SequenceNo.equals(other.getSequenceNo())
			&& AppntGrade.equals(other.getAppntGrade())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& Password.equals(other.getPassword())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& NativePlace.equals(other.getNativePlace())
			&& Nationality.equals(other.getNationality())
			&& Marriage.equals(other.getMarriage())
			&& fDate.getString(MarriageDate).equals(other.getMarriageDate())
			&& OccupationType.equals(other.getOccupationType())
			&& fDate.getString(StartWorkDate).equals(other.getStartWorkDate())
			&& Salary == other.getSalary()
			&& Health.equals(other.getHealth())
			&& Stature == other.getStature()
			&& Avoirdupois == other.getAvoirdupois()
			&& CreditGrade.equals(other.getCreditGrade())
			&& IDType.equals(other.getIDType())
			&& Proterty.equals(other.getProterty())
			&& IDNo.equals(other.getIDNo())
			&& OthIDType.equals(other.getOthIDType())
			&& OthIDNo.equals(other.getOthIDNo())
			&& ICNo.equals(other.getICNo())
			&& HomeAddressCode.equals(other.getHomeAddressCode())
			&& HomeAddress.equals(other.getHomeAddress())
			&& PostalAddress.equals(other.getPostalAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& BP.equals(other.getBP())
			&& Mobile.equals(other.getMobile())
			&& EMail.equals(other.getEMail())
			&& fDate.getString(JoinCompanyDate).equals(other.getJoinCompanyDate())
			&& Position.equals(other.getPosition())
			&& GrpNo.equals(other.getGrpNo())
			&& GrpName.equals(other.getGrpName())
			&& GrpPhone.equals(other.getGrpPhone())
			&& GrpAddressCode.equals(other.getGrpAddressCode())
			&& GrpAddress.equals(other.getGrpAddress())
			&& fDate.getString(DeathDate).equals(other.getDeathDate())
			&& Remark.equals(other.getRemark())
			&& State.equals(other.getState())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& WorkType.equals(other.getWorkType())
			&& PluralityType.equals(other.getPluralityType())
			&& Degree.equals(other.getDegree())
			&& OccupationCode.equals(other.getOccupationCode())
			&& GrpZipCode.equals(other.getGrpZipCode())
			&& SmokeFlag.equals(other.getSmokeFlag())
			&& RgtAddress.equals(other.getRgtAddress())
			&& HomeZipCode.equals(other.getHomeZipCode())
			&& Phone2.equals(other.getPhone2());
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return 2;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return 3;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 4;
		}
		if( strFieldName.equals("Password") ) {
			return 5;
		}
		if( strFieldName.equals("Name") ) {
			return 6;
		}
		if( strFieldName.equals("Sex") ) {
			return 7;
		}
		if( strFieldName.equals("Birthday") ) {
			return 8;
		}
		if( strFieldName.equals("NativePlace") ) {
			return 9;
		}
		if( strFieldName.equals("Nationality") ) {
			return 10;
		}
		if( strFieldName.equals("Marriage") ) {
			return 11;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 12;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 13;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return 14;
		}
		if( strFieldName.equals("Salary") ) {
			return 15;
		}
		if( strFieldName.equals("Health") ) {
			return 16;
		}
		if( strFieldName.equals("Stature") ) {
			return 17;
		}
		if( strFieldName.equals("Avoirdupois") ) {
			return 18;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 19;
		}
		if( strFieldName.equals("IDType") ) {
			return 20;
		}
		if( strFieldName.equals("Proterty") ) {
			return 21;
		}
		if( strFieldName.equals("IDNo") ) {
			return 22;
		}
		if( strFieldName.equals("OthIDType") ) {
			return 23;
		}
		if( strFieldName.equals("OthIDNo") ) {
			return 24;
		}
		if( strFieldName.equals("ICNo") ) {
			return 25;
		}
		if( strFieldName.equals("HomeAddressCode") ) {
			return 26;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 27;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 28;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 29;
		}
		if( strFieldName.equals("Phone") ) {
			return 30;
		}
		if( strFieldName.equals("BP") ) {
			return 31;
		}
		if( strFieldName.equals("Mobile") ) {
			return 32;
		}
		if( strFieldName.equals("EMail") ) {
			return 33;
		}
		if( strFieldName.equals("JoinCompanyDate") ) {
			return 34;
		}
		if( strFieldName.equals("Position") ) {
			return 35;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 36;
		}
		if( strFieldName.equals("GrpName") ) {
			return 37;
		}
		if( strFieldName.equals("GrpPhone") ) {
			return 38;
		}
		if( strFieldName.equals("GrpAddressCode") ) {
			return 39;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return 40;
		}
		if( strFieldName.equals("DeathDate") ) {
			return 41;
		}
		if( strFieldName.equals("Remark") ) {
			return 42;
		}
		if( strFieldName.equals("State") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 45;
		}
		if( strFieldName.equals("WorkType") ) {
			return 46;
		}
		if( strFieldName.equals("PluralityType") ) {
			return 47;
		}
		if( strFieldName.equals("Degree") ) {
			return 48;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return 49;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return 50;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return 51;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return 52;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return 53;
		}
		if( strFieldName.equals("Phone2") ) {
			return 54;
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "SequenceNo";
				break;
			case 3:
				strFieldName = "AppntGrade";
				break;
			case 4:
				strFieldName = "RelationToInsured";
				break;
			case 5:
				strFieldName = "Password";
				break;
			case 6:
				strFieldName = "Name";
				break;
			case 7:
				strFieldName = "Sex";
				break;
			case 8:
				strFieldName = "Birthday";
				break;
			case 9:
				strFieldName = "NativePlace";
				break;
			case 10:
				strFieldName = "Nationality";
				break;
			case 11:
				strFieldName = "Marriage";
				break;
			case 12:
				strFieldName = "MarriageDate";
				break;
			case 13:
				strFieldName = "OccupationType";
				break;
			case 14:
				strFieldName = "StartWorkDate";
				break;
			case 15:
				strFieldName = "Salary";
				break;
			case 16:
				strFieldName = "Health";
				break;
			case 17:
				strFieldName = "Stature";
				break;
			case 18:
				strFieldName = "Avoirdupois";
				break;
			case 19:
				strFieldName = "CreditGrade";
				break;
			case 20:
				strFieldName = "IDType";
				break;
			case 21:
				strFieldName = "Proterty";
				break;
			case 22:
				strFieldName = "IDNo";
				break;
			case 23:
				strFieldName = "OthIDType";
				break;
			case 24:
				strFieldName = "OthIDNo";
				break;
			case 25:
				strFieldName = "ICNo";
				break;
			case 26:
				strFieldName = "HomeAddressCode";
				break;
			case 27:
				strFieldName = "HomeAddress";
				break;
			case 28:
				strFieldName = "PostalAddress";
				break;
			case 29:
				strFieldName = "ZipCode";
				break;
			case 30:
				strFieldName = "Phone";
				break;
			case 31:
				strFieldName = "BP";
				break;
			case 32:
				strFieldName = "Mobile";
				break;
			case 33:
				strFieldName = "EMail";
				break;
			case 34:
				strFieldName = "JoinCompanyDate";
				break;
			case 35:
				strFieldName = "Position";
				break;
			case 36:
				strFieldName = "GrpNo";
				break;
			case 37:
				strFieldName = "GrpName";
				break;
			case 38:
				strFieldName = "GrpPhone";
				break;
			case 39:
				strFieldName = "GrpAddressCode";
				break;
			case 40:
				strFieldName = "GrpAddress";
				break;
			case 41:
				strFieldName = "DeathDate";
				break;
			case 42:
				strFieldName = "Remark";
				break;
			case 43:
				strFieldName = "State";
				break;
			case 44:
				strFieldName = "ModifyDate";
				break;
			case 45:
				strFieldName = "ModifyTime";
				break;
			case 46:
				strFieldName = "WorkType";
				break;
			case 47:
				strFieldName = "PluralityType";
				break;
			case 48:
				strFieldName = "Degree";
				break;
			case 49:
				strFieldName = "OccupationCode";
				break;
			case 50:
				strFieldName = "GrpZipCode";
				break;
			case 51:
				strFieldName = "SmokeFlag";
				break;
			case 52:
				strFieldName = "RgtAddress";
				break;
			case 53:
				strFieldName = "HomeZipCode";
				break;
			case 54:
				strFieldName = "Phone2";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SequenceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
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
		if( strFieldName.equals("MarriageDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartWorkDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Salary") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("CreditGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Proterty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
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
		if( strFieldName.equals("JoinCompanyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Position") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddressCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeathDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PluralityType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Degree") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SmokeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone2") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
