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
import com.sinosoft.lis.db.LAAgentBlacklistDB;

/*
 * <p>ClassName: LAAgentBlacklistSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAAgentBlacklistSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAAgentBlacklistSchema.class);

	// @Field
	/** 代理人黑名单编码 */
	private String BlackListCode;
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
	/** 工种/行业 */
	private String Business;
	/** 计入黑名单原因 */
	private String BlacklistReason;
	/** 原所属保险公司 */
	private String InsurerCompany;
	/** 原所属展业机构 */
	private String AgentName;
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

	public static final int FIELDNUM = 39;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAAgentBlacklistSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BlackListCode";

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
		LAAgentBlacklistSchema cloned = (LAAgentBlacklistSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBlackListCode()
	{
		return BlackListCode;
	}
	public void setBlackListCode(String aBlackListCode)
	{
		BlackListCode = aBlackListCode;
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
	public String getBusiness()
	{
		return Business;
	}
	public void setBusiness(String aBusiness)
	{
		Business = aBusiness;
	}
	public String getBlacklistReason()
	{
		return BlacklistReason;
	}
	public void setBlacklistReason(String aBlacklistReason)
	{
		BlacklistReason = aBlacklistReason;
	}
	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getInsurerCompany()
	{
		return InsurerCompany;
	}
	public void setInsurerCompany(String aInsurerCompany)
	{
		InsurerCompany = aInsurerCompany;
	}
	public String getAgentName()
	{
		return AgentName;
	}
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
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
	* 使用另外一个 LAAgentBlacklistSchema 对象给 Schema 赋值
	* @param: aLAAgentBlacklistSchema LAAgentBlacklistSchema
	**/
	public void setSchema(LAAgentBlacklistSchema aLAAgentBlacklistSchema)
	{
		this.BlackListCode = aLAAgentBlacklistSchema.getBlackListCode();
		this.Name = aLAAgentBlacklistSchema.getName();
		this.Sex = aLAAgentBlacklistSchema.getSex();
		this.Birthday = fDate.getDate( aLAAgentBlacklistSchema.getBirthday());
		this.NativePlace = aLAAgentBlacklistSchema.getNativePlace();
		this.Nationality = aLAAgentBlacklistSchema.getNationality();
		this.Marriage = aLAAgentBlacklistSchema.getMarriage();
		this.CreditGrade = aLAAgentBlacklistSchema.getCreditGrade();
		this.HomeAddressCode = aLAAgentBlacklistSchema.getHomeAddressCode();
		this.HomeAddress = aLAAgentBlacklistSchema.getHomeAddress();
		this.PostalAddress = aLAAgentBlacklistSchema.getPostalAddress();
		this.ZipCode = aLAAgentBlacklistSchema.getZipCode();
		this.Phone = aLAAgentBlacklistSchema.getPhone();
		this.BP = aLAAgentBlacklistSchema.getBP();
		this.Mobile = aLAAgentBlacklistSchema.getMobile();
		this.EMail = aLAAgentBlacklistSchema.getEMail();
		this.MarriageDate = fDate.getDate( aLAAgentBlacklistSchema.getMarriageDate());
		this.IDNo = aLAAgentBlacklistSchema.getIDNo();
		this.Source = aLAAgentBlacklistSchema.getSource();
		this.BloodType = aLAAgentBlacklistSchema.getBloodType();
		this.PolityVisage = aLAAgentBlacklistSchema.getPolityVisage();
		this.Degree = aLAAgentBlacklistSchema.getDegree();
		this.GraduateSchool = aLAAgentBlacklistSchema.getGraduateSchool();
		this.Speciality = aLAAgentBlacklistSchema.getSpeciality();
		this.PostTitle = aLAAgentBlacklistSchema.getPostTitle();
		this.ForeignLevel = aLAAgentBlacklistSchema.getForeignLevel();
		this.WorkAge = aLAAgentBlacklistSchema.getWorkAge();
		this.OldCom = aLAAgentBlacklistSchema.getOldCom();
		this.OldOccupation = aLAAgentBlacklistSchema.getOldOccupation();
		this.HeadShip = aLAAgentBlacklistSchema.getHeadShip();
		this.Business = aLAAgentBlacklistSchema.getBusiness();
		this.BlacklistReason = aLAAgentBlacklistSchema.getBlacklistReason();
		this.InsurerCompany = aLAAgentBlacklistSchema.getInsurerCompany();
		this.AgentName = aLAAgentBlacklistSchema.getAgentName();
		this.Operator = aLAAgentBlacklistSchema.getOperator();
		this.MakeDate = fDate.getDate( aLAAgentBlacklistSchema.getMakeDate());
		this.MakeTime = aLAAgentBlacklistSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAAgentBlacklistSchema.getModifyDate());
		this.ModifyTime = aLAAgentBlacklistSchema.getModifyTime();
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
			if( rs.getString("BlackListCode") == null )
				this.BlackListCode = null;
			else
				this.BlackListCode = rs.getString("BlackListCode").trim();

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

			if( rs.getString("Business") == null )
				this.Business = null;
			else
				this.Business = rs.getString("Business").trim();

			if( rs.getString("BlacklistReason") == null )
				this.BlacklistReason = null;
			else
				this.BlacklistReason = rs.getString("BlacklistReason").trim();

			if( rs.getString("InsurerCompany") == null )
				this.InsurerCompany = null;
			else
				this.InsurerCompany = rs.getString("InsurerCompany").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAAgentBlacklist表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAAgentBlacklistSchema getSchema()
	{
		LAAgentBlacklistSchema aLAAgentBlacklistSchema = new LAAgentBlacklistSchema();
		aLAAgentBlacklistSchema.setSchema(this);
		return aLAAgentBlacklistSchema;
	}

	public LAAgentBlacklistDB getDB()
	{
		LAAgentBlacklistDB aDBOper = new LAAgentBlacklistDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAgentBlacklist描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BlackListCode)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(Business)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsurerCompany)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAgentBlacklist>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BlackListCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			NativePlace = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Nationality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CreditGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HomeAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MarriageDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Source = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BloodType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PolityVisage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Degree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			GraduateSchool = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Speciality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PostTitle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ForeignLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			WorkAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			OldCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			OldOccupation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			HeadShip = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Business = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BlacklistReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InsurerCompany = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistSchema";
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
		if (FCode.equalsIgnoreCase("BlackListCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlackListCode));
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
		if (FCode.equalsIgnoreCase("Business"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Business));
		}
		if (FCode.equalsIgnoreCase("BlacklistReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistReason));
		}
		if (FCode.equalsIgnoreCase("InsurerCompany"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsurerCompany));
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
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
				strFieldValue = StrTool.GBKToUnicode(BlackListCode);
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
				strFieldValue = StrTool.GBKToUnicode(NativePlace);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Nationality);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CreditGrade);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HomeAddressCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BP);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMarriageDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Source);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BloodType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PolityVisage);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Degree);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(GraduateSchool);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Speciality);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(PostTitle);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ForeignLevel);
				break;
			case 26:
				strFieldValue = String.valueOf(WorkAge);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(OldCom);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(OldOccupation);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(HeadShip);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Business);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BlacklistReason);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(InsurerCompany);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 38:
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

		if (FCode.equalsIgnoreCase("BlackListCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlackListCode = FValue.trim();
			}
			else
				BlackListCode = null;
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
		if (FCode.equalsIgnoreCase("Business"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Business = FValue.trim();
			}
			else
				Business = null;
		}
		if (FCode.equalsIgnoreCase("BlacklistReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlacklistReason = FValue.trim();
			}
			else
				BlacklistReason = null;
		}
		if (FCode.equalsIgnoreCase("InsurerCompany"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsurerCompany = FValue.trim();
			}
			else
				InsurerCompany = null;
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAAgentBlacklistSchema other = (LAAgentBlacklistSchema)otherObject;
		return
			BlackListCode.equals(other.getBlackListCode())
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
			&& Business.equals(other.getBusiness())
			&& BlacklistReason.equals(other.getBlacklistReason())
			&& InsurerCompany.equals(other.getInsurerCompany())
			&& AgentName.equals(other.getAgentName())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
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
		if( strFieldName.equals("BlackListCode") ) {
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
		if( strFieldName.equals("NativePlace") ) {
			return 4;
		}
		if( strFieldName.equals("Nationality") ) {
			return 5;
		}
		if( strFieldName.equals("Marriage") ) {
			return 6;
		}
		if( strFieldName.equals("CreditGrade") ) {
			return 7;
		}
		if( strFieldName.equals("HomeAddressCode") ) {
			return 8;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 9;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 10;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 11;
		}
		if( strFieldName.equals("Phone") ) {
			return 12;
		}
		if( strFieldName.equals("BP") ) {
			return 13;
		}
		if( strFieldName.equals("Mobile") ) {
			return 14;
		}
		if( strFieldName.equals("EMail") ) {
			return 15;
		}
		if( strFieldName.equals("MarriageDate") ) {
			return 16;
		}
		if( strFieldName.equals("IDNo") ) {
			return 17;
		}
		if( strFieldName.equals("Source") ) {
			return 18;
		}
		if( strFieldName.equals("BloodType") ) {
			return 19;
		}
		if( strFieldName.equals("PolityVisage") ) {
			return 20;
		}
		if( strFieldName.equals("Degree") ) {
			return 21;
		}
		if( strFieldName.equals("GraduateSchool") ) {
			return 22;
		}
		if( strFieldName.equals("Speciality") ) {
			return 23;
		}
		if( strFieldName.equals("PostTitle") ) {
			return 24;
		}
		if( strFieldName.equals("ForeignLevel") ) {
			return 25;
		}
		if( strFieldName.equals("WorkAge") ) {
			return 26;
		}
		if( strFieldName.equals("OldCom") ) {
			return 27;
		}
		if( strFieldName.equals("OldOccupation") ) {
			return 28;
		}
		if( strFieldName.equals("HeadShip") ) {
			return 29;
		}
		if( strFieldName.equals("Business") ) {
			return 30;
		}
		if( strFieldName.equals("BlacklistReason") ) {
			return 31;
		}
		if( strFieldName.equals("InsurerCompany") ) {
			return 32;
		}
		if( strFieldName.equals("AgentName") ) {
			return 33;
		}
		if( strFieldName.equals("Operator") ) {
			return 34;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 35;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 36;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 38;
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
				strFieldName = "BlackListCode";
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
				strFieldName = "NativePlace";
				break;
			case 5:
				strFieldName = "Nationality";
				break;
			case 6:
				strFieldName = "Marriage";
				break;
			case 7:
				strFieldName = "CreditGrade";
				break;
			case 8:
				strFieldName = "HomeAddressCode";
				break;
			case 9:
				strFieldName = "HomeAddress";
				break;
			case 10:
				strFieldName = "PostalAddress";
				break;
			case 11:
				strFieldName = "ZipCode";
				break;
			case 12:
				strFieldName = "Phone";
				break;
			case 13:
				strFieldName = "BP";
				break;
			case 14:
				strFieldName = "Mobile";
				break;
			case 15:
				strFieldName = "EMail";
				break;
			case 16:
				strFieldName = "MarriageDate";
				break;
			case 17:
				strFieldName = "IDNo";
				break;
			case 18:
				strFieldName = "Source";
				break;
			case 19:
				strFieldName = "BloodType";
				break;
			case 20:
				strFieldName = "PolityVisage";
				break;
			case 21:
				strFieldName = "Degree";
				break;
			case 22:
				strFieldName = "GraduateSchool";
				break;
			case 23:
				strFieldName = "Speciality";
				break;
			case 24:
				strFieldName = "PostTitle";
				break;
			case 25:
				strFieldName = "ForeignLevel";
				break;
			case 26:
				strFieldName = "WorkAge";
				break;
			case 27:
				strFieldName = "OldCom";
				break;
			case 28:
				strFieldName = "OldOccupation";
				break;
			case 29:
				strFieldName = "HeadShip";
				break;
			case 30:
				strFieldName = "Business";
				break;
			case 31:
				strFieldName = "BlacklistReason";
				break;
			case 32:
				strFieldName = "InsurerCompany";
				break;
			case 33:
				strFieldName = "AgentName";
				break;
			case 34:
				strFieldName = "Operator";
				break;
			case 35:
				strFieldName = "MakeDate";
				break;
			case 36:
				strFieldName = "MakeTime";
				break;
			case 37:
				strFieldName = "ModifyDate";
				break;
			case 38:
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
		if( strFieldName.equals("BlackListCode") ) {
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
		if( strFieldName.equals("Business") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlacklistReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsurerCompany") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
