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
import com.sinosoft.lis.db.LAComDB;

/*
 * <p>ClassName: LAComSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAComSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAComSchema.class);

	// @Field
	/** 代理机构 */
	private String AgentCom;
	/** 管理机构 */
	private String ManageCom;
	/** 地区类型 */
	private String AreaType;
	/** 渠道类型 */
	private String ChannelType;
	/** 上级代理机构 */
	private String UpAgentCom;
	/** 机构名称 */
	private String Name;
	/** 机构注册地址 */
	private String Address;
	/** 机构邮编 */
	private String ZipCode;
	/** 机构电话 */
	private String Phone;
	/** 机构传真 */
	private String Fax;
	/** Email */
	private String EMail;
	/** 网址 */
	private String WebAddress;
	/** 负责人 */
	private String LinkMan;
	/** 密码 */
	private String Password;
	/** 法人 */
	private String Corporation;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 行业分类 */
	private String BusinessType;
	/** 单位性质 */
	private String GrpNature;
	/** 中介机构类别 */
	private String ACType;
	/** 销售资格 */
	private String SellFlag;
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
	/** 银行级别 */
	private String BankType;
	/** 是否统计网点合格率 */
	private String CalFlag;
	/** 工商执照编码 */
	private String BusiLicenseCode;
	/** 保险公司id */
	private String InsureID;
	/** 保险公司负责人 */
	private String InsurePrincipal;
	/** 主营业务 */
	private String ChiefBusiness;
	/** 营业地址 */
	private String BusiAddress;
	/** 签署人 */
	private String SubscribeMan;
	/** 签署人职务 */
	private String SubscribeManDuty;
	/** 许可证号码 */
	private String LicenseNo;
	/** 行政区划代码 */
	private String RegionalismCode;
	/** 上报代码 */
	private String AppAgentCom;
	/** 机构状态 */
	private String State;
	/** 相关说明 */
	private String Noti;
	/** 行业代码 */
	private String BusinessCode;
	/** 许可证登记日期 */
	private Date LicenseStartDate;
	/** 许可证截至日期 */
	private Date LicenseEndDate;
	/** 展业类型 */
	private String BranchType;
	/** 机构编码 */
	private String AgentComCode;
	/** 代理人编码 */
	private String AgentCode;
	/** 机构人员关系有效标识 */
	private String ComToAgentFlag;
	/** 成立日期 */
	private Date FoundDate;
	/** 停业日期 */
	private Date EndDate;

	public static final int FIELDNUM = 49;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAComSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AgentCom";

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
		LAComSchema cloned = (LAComSchema)super.clone();
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
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 01-A类地区<p>
	* <p>
	* 02-B类地区
	*/
	public String getAreaType()
	{
		return AreaType;
	}
	public void setAreaType(String aAreaType)
	{
		AreaType = aAreaType;
	}
	/**
	* A-中国银行,中国工商银行,中国建设银行<p>
	* ,B-中国农业银行<p>
	* ,C,<p>
	* D(其他)
	*/
	public String getChannelType()
	{
		return ChannelType;
	}
	public void setChannelType(String aChannelType)
	{
		ChannelType = aChannelType;
	}
	public String getUpAgentCom()
	{
		return UpAgentCom;
	}
	public void setUpAgentCom(String aUpAgentCom)
	{
		UpAgentCom = aUpAgentCom;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		Address = aAddress;
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
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		Fax = aFax;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		EMail = aEMail;
	}
	public String getWebAddress()
	{
		return WebAddress;
	}
	public void setWebAddress(String aWebAddress)
	{
		WebAddress = aWebAddress;
	}
	public String getLinkMan()
	{
		return LinkMan;
	}
	public void setLinkMan(String aLinkMan)
	{
		LinkMan = aLinkMan;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	public String getCorporation()
	{
		return Corporation;
	}
	public void setCorporation(String aCorporation)
	{
		Corporation = aCorporation;
	}
	/**
	* 保险公司帐号
	*/
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
	public String getBusinessType()
	{
		return BusinessType;
	}
	public void setBusinessType(String aBusinessType)
	{
		BusinessType = aBusinessType;
	}
	public String getGrpNature()
	{
		return GrpNature;
	}
	public void setGrpNature(String aGrpNature)
	{
		GrpNature = aGrpNature;
	}
	/**
	* 01-银行代理<p>
	* 02-兼业代理<p>
	* 03-专业代理<p>
	* 04-经济公司<p>
	* 05-其它中介业务)
	*/
	public String getACType()
	{
		return ACType;
	}
	public void setACType(String aACType)
	{
		ACType = aACType;
	}
	/**
	* Y 有<p>
	* <p>
	* N 无
	*/
	public String getSellFlag()
	{
		return SellFlag;
	}
	public void setSellFlag(String aSellFlag)
	{
		SellFlag = aSellFlag;
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
	* 00-总行<p>
	* 01-分行<p>
	* 02-支行<p>
	* 03-分理处<p>
	* <p>
	* 04-银行网点
	*/
	public String getBankType()
	{
		return BankType;
	}
	public void setBankType(String aBankType)
	{
		BankType = aBankType;
	}
	/**
	* N-(统计网点合格率时不包含)<p>
	* Y-统计网点合格率
	*/
	public String getCalFlag()
	{
		return CalFlag;
	}
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	public String getBusiLicenseCode()
	{
		return BusiLicenseCode;
	}
	public void setBusiLicenseCode(String aBusiLicenseCode)
	{
		BusiLicenseCode = aBusiLicenseCode;
	}
	public String getInsureID()
	{
		return InsureID;
	}
	public void setInsureID(String aInsureID)
	{
		InsureID = aInsureID;
	}
	public String getInsurePrincipal()
	{
		return InsurePrincipal;
	}
	public void setInsurePrincipal(String aInsurePrincipal)
	{
		InsurePrincipal = aInsurePrincipal;
	}
	public String getChiefBusiness()
	{
		return ChiefBusiness;
	}
	public void setChiefBusiness(String aChiefBusiness)
	{
		ChiefBusiness = aChiefBusiness;
	}
	public String getBusiAddress()
	{
		return BusiAddress;
	}
	public void setBusiAddress(String aBusiAddress)
	{
		BusiAddress = aBusiAddress;
	}
	public String getSubscribeMan()
	{
		return SubscribeMan;
	}
	public void setSubscribeMan(String aSubscribeMan)
	{
		SubscribeMan = aSubscribeMan;
	}
	public String getSubscribeManDuty()
	{
		return SubscribeManDuty;
	}
	public void setSubscribeManDuty(String aSubscribeManDuty)
	{
		SubscribeManDuty = aSubscribeManDuty;
	}
	public String getLicenseNo()
	{
		return LicenseNo;
	}
	public void setLicenseNo(String aLicenseNo)
	{
		LicenseNo = aLicenseNo;
	}
	public String getRegionalismCode()
	{
		return RegionalismCode;
	}
	public void setRegionalismCode(String aRegionalismCode)
	{
		RegionalismCode = aRegionalismCode;
	}
	public String getAppAgentCom()
	{
		return AppAgentCom;
	}
	public void setAppAgentCom(String aAppAgentCom)
	{
		AppAgentCom = aAppAgentCom;
	}
	/**
	* 1-有效 　未停业<p>
	* <p>
	* 0-无效 　停业　
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
	}
	public String getBusinessCode()
	{
		return BusinessCode;
	}
	public void setBusinessCode(String aBusinessCode)
	{
		BusinessCode = aBusinessCode;
	}
	public String getLicenseStartDate()
	{
		if( LicenseStartDate != null )
			return fDate.getString(LicenseStartDate);
		else
			return null;
	}
	public void setLicenseStartDate(Date aLicenseStartDate)
	{
		LicenseStartDate = aLicenseStartDate;
	}
	public void setLicenseStartDate(String aLicenseStartDate)
	{
		if (aLicenseStartDate != null && !aLicenseStartDate.equals("") )
		{
			LicenseStartDate = fDate.getDate( aLicenseStartDate );
		}
		else
			LicenseStartDate = null;
	}

	public String getLicenseEndDate()
	{
		if( LicenseEndDate != null )
			return fDate.getString(LicenseEndDate);
		else
			return null;
	}
	public void setLicenseEndDate(Date aLicenseEndDate)
	{
		LicenseEndDate = aLicenseEndDate;
	}
	public void setLicenseEndDate(String aLicenseEndDate)
	{
		if (aLicenseEndDate != null && !aLicenseEndDate.equals("") )
		{
			LicenseEndDate = fDate.getDate( aLicenseEndDate );
		}
		else
			LicenseEndDate = null;
	}

	/**
	* 展业类型(1-个人营销，2-团险，<p>
	* <p>
	* 3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	/**
	* 法人许可证代理机构编码
	*/
	public String getAgentComCode()
	{
		return AgentComCode;
	}
	public void setAgentComCode(String aAgentComCode)
	{
		AgentComCode = aAgentComCode;
	}
	/**
	* 用于记录代理
	*/
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/**
	* 用于记录该机构和客户经理的对应关系是否有效<p>
	* <p>
	* 0 －无效<p>
	* <p>
	* 1 －有效
	*/
	public String getComToAgentFlag()
	{
		return ComToAgentFlag;
	}
	public void setComToAgentFlag(String aComToAgentFlag)
	{
		ComToAgentFlag = aComToAgentFlag;
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

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}


	/**
	* 使用另外一个 LAComSchema 对象给 Schema 赋值
	* @param: aLAComSchema LAComSchema
	**/
	public void setSchema(LAComSchema aLAComSchema)
	{
		this.AgentCom = aLAComSchema.getAgentCom();
		this.ManageCom = aLAComSchema.getManageCom();
		this.AreaType = aLAComSchema.getAreaType();
		this.ChannelType = aLAComSchema.getChannelType();
		this.UpAgentCom = aLAComSchema.getUpAgentCom();
		this.Name = aLAComSchema.getName();
		this.Address = aLAComSchema.getAddress();
		this.ZipCode = aLAComSchema.getZipCode();
		this.Phone = aLAComSchema.getPhone();
		this.Fax = aLAComSchema.getFax();
		this.EMail = aLAComSchema.getEMail();
		this.WebAddress = aLAComSchema.getWebAddress();
		this.LinkMan = aLAComSchema.getLinkMan();
		this.Password = aLAComSchema.getPassword();
		this.Corporation = aLAComSchema.getCorporation();
		this.BankCode = aLAComSchema.getBankCode();
		this.BankAccNo = aLAComSchema.getBankAccNo();
		this.BusinessType = aLAComSchema.getBusinessType();
		this.GrpNature = aLAComSchema.getGrpNature();
		this.ACType = aLAComSchema.getACType();
		this.SellFlag = aLAComSchema.getSellFlag();
		this.Operator = aLAComSchema.getOperator();
		this.MakeDate = fDate.getDate( aLAComSchema.getMakeDate());
		this.MakeTime = aLAComSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAComSchema.getModifyDate());
		this.ModifyTime = aLAComSchema.getModifyTime();
		this.BankType = aLAComSchema.getBankType();
		this.CalFlag = aLAComSchema.getCalFlag();
		this.BusiLicenseCode = aLAComSchema.getBusiLicenseCode();
		this.InsureID = aLAComSchema.getInsureID();
		this.InsurePrincipal = aLAComSchema.getInsurePrincipal();
		this.ChiefBusiness = aLAComSchema.getChiefBusiness();
		this.BusiAddress = aLAComSchema.getBusiAddress();
		this.SubscribeMan = aLAComSchema.getSubscribeMan();
		this.SubscribeManDuty = aLAComSchema.getSubscribeManDuty();
		this.LicenseNo = aLAComSchema.getLicenseNo();
		this.RegionalismCode = aLAComSchema.getRegionalismCode();
		this.AppAgentCom = aLAComSchema.getAppAgentCom();
		this.State = aLAComSchema.getState();
		this.Noti = aLAComSchema.getNoti();
		this.BusinessCode = aLAComSchema.getBusinessCode();
		this.LicenseStartDate = fDate.getDate( aLAComSchema.getLicenseStartDate());
		this.LicenseEndDate = fDate.getDate( aLAComSchema.getLicenseEndDate());
		this.BranchType = aLAComSchema.getBranchType();
		this.AgentComCode = aLAComSchema.getAgentComCode();
		this.AgentCode = aLAComSchema.getAgentCode();
		this.ComToAgentFlag = aLAComSchema.getComToAgentFlag();
		this.FoundDate = fDate.getDate( aLAComSchema.getFoundDate());
		this.EndDate = fDate.getDate( aLAComSchema.getEndDate());
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
			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AreaType") == null )
				this.AreaType = null;
			else
				this.AreaType = rs.getString("AreaType").trim();

			if( rs.getString("ChannelType") == null )
				this.ChannelType = null;
			else
				this.ChannelType = rs.getString("ChannelType").trim();

			if( rs.getString("UpAgentCom") == null )
				this.UpAgentCom = null;
			else
				this.UpAgentCom = rs.getString("UpAgentCom").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

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

			if( rs.getString("WebAddress") == null )
				this.WebAddress = null;
			else
				this.WebAddress = rs.getString("WebAddress").trim();

			if( rs.getString("LinkMan") == null )
				this.LinkMan = null;
			else
				this.LinkMan = rs.getString("LinkMan").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("Corporation") == null )
				this.Corporation = null;
			else
				this.Corporation = rs.getString("Corporation").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("BusinessType") == null )
				this.BusinessType = null;
			else
				this.BusinessType = rs.getString("BusinessType").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			if( rs.getString("ACType") == null )
				this.ACType = null;
			else
				this.ACType = rs.getString("ACType").trim();

			if( rs.getString("SellFlag") == null )
				this.SellFlag = null;
			else
				this.SellFlag = rs.getString("SellFlag").trim();

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

			if( rs.getString("BankType") == null )
				this.BankType = null;
			else
				this.BankType = rs.getString("BankType").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			if( rs.getString("BusiLicenseCode") == null )
				this.BusiLicenseCode = null;
			else
				this.BusiLicenseCode = rs.getString("BusiLicenseCode").trim();

			if( rs.getString("InsureID") == null )
				this.InsureID = null;
			else
				this.InsureID = rs.getString("InsureID").trim();

			if( rs.getString("InsurePrincipal") == null )
				this.InsurePrincipal = null;
			else
				this.InsurePrincipal = rs.getString("InsurePrincipal").trim();

			if( rs.getString("ChiefBusiness") == null )
				this.ChiefBusiness = null;
			else
				this.ChiefBusiness = rs.getString("ChiefBusiness").trim();

			if( rs.getString("BusiAddress") == null )
				this.BusiAddress = null;
			else
				this.BusiAddress = rs.getString("BusiAddress").trim();

			if( rs.getString("SubscribeMan") == null )
				this.SubscribeMan = null;
			else
				this.SubscribeMan = rs.getString("SubscribeMan").trim();

			if( rs.getString("SubscribeManDuty") == null )
				this.SubscribeManDuty = null;
			else
				this.SubscribeManDuty = rs.getString("SubscribeManDuty").trim();

			if( rs.getString("LicenseNo") == null )
				this.LicenseNo = null;
			else
				this.LicenseNo = rs.getString("LicenseNo").trim();

			if( rs.getString("RegionalismCode") == null )
				this.RegionalismCode = null;
			else
				this.RegionalismCode = rs.getString("RegionalismCode").trim();

			if( rs.getString("AppAgentCom") == null )
				this.AppAgentCom = null;
			else
				this.AppAgentCom = rs.getString("AppAgentCom").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

			if( rs.getString("BusinessCode") == null )
				this.BusinessCode = null;
			else
				this.BusinessCode = rs.getString("BusinessCode").trim();

			this.LicenseStartDate = rs.getDate("LicenseStartDate");
			this.LicenseEndDate = rs.getDate("LicenseEndDate");
			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("AgentComCode") == null )
				this.AgentComCode = null;
			else
				this.AgentComCode = rs.getString("AgentComCode").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ComToAgentFlag") == null )
				this.ComToAgentFlag = null;
			else
				this.ComToAgentFlag = rs.getString("ComToAgentFlag").trim();

			this.FoundDate = rs.getDate("FoundDate");
			this.EndDate = rs.getDate("EndDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LACom表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAComSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAComSchema getSchema()
	{
		LAComSchema aLAComSchema = new LAComSchema();
		aLAComSchema.setSchema(this);
		return aLAComSchema;
	}

	public LAComDB getDB()
	{
		LAComDB aDBOper = new LAComDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLACom描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChannelType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpAgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WebAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Corporation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ACType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SellFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiLicenseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsureID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsurePrincipal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChiefBusiness)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubscribeMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubscribeManDuty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LicenseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RegionalismCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppAgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LicenseStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LicenseEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComToAgentFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLACom>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AreaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ChannelType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			UpAgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			WebAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			LinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BusinessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ACType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			SellFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			BankType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BusiLicenseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			InsureID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			InsurePrincipal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ChiefBusiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BusiAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			SubscribeMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			SubscribeManDuty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			LicenseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			RegionalismCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			AppAgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			BusinessCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			LicenseStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			LicenseEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			AgentComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			ComToAgentFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAComSchema";
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
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AreaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaType));
		}
		if (FCode.equalsIgnoreCase("ChannelType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChannelType));
		}
		if (FCode.equalsIgnoreCase("UpAgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpAgentCom));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
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
		if (FCode.equalsIgnoreCase("WebAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAddress));
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("Corporation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Corporation));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("BusinessType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessType));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("ACType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ACType));
		}
		if (FCode.equalsIgnoreCase("SellFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SellFlag));
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
		if (FCode.equalsIgnoreCase("BankType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankType));
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equalsIgnoreCase("BusiLicenseCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiLicenseCode));
		}
		if (FCode.equalsIgnoreCase("InsureID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsureID));
		}
		if (FCode.equalsIgnoreCase("InsurePrincipal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsurePrincipal));
		}
		if (FCode.equalsIgnoreCase("ChiefBusiness"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChiefBusiness));
		}
		if (FCode.equalsIgnoreCase("BusiAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiAddress));
		}
		if (FCode.equalsIgnoreCase("SubscribeMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubscribeMan));
		}
		if (FCode.equalsIgnoreCase("SubscribeManDuty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubscribeManDuty));
		}
		if (FCode.equalsIgnoreCase("LicenseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LicenseNo));
		}
		if (FCode.equalsIgnoreCase("RegionalismCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegionalismCode));
		}
		if (FCode.equalsIgnoreCase("AppAgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppAgentCom));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
		}
		if (FCode.equalsIgnoreCase("BusinessCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessCode));
		}
		if (FCode.equalsIgnoreCase("LicenseStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLicenseStartDate()));
		}
		if (FCode.equalsIgnoreCase("LicenseEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLicenseEndDate()));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("AgentComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComCode));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("ComToAgentFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComToAgentFlag));
		}
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AreaType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ChannelType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(UpAgentCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(WebAddress);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(LinkMan);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ACType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(SellFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(BankType);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BusiLicenseCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InsureID);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(InsurePrincipal);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ChiefBusiness);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BusiAddress);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(SubscribeMan);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(SubscribeManDuty);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(LicenseNo);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(RegionalismCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(AppAgentCom);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Noti);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(BusinessCode);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLicenseStartDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLicenseEndDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(AgentComCode);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(ComToAgentFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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

		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
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
		if (FCode.equalsIgnoreCase("AreaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaType = FValue.trim();
			}
			else
				AreaType = null;
		}
		if (FCode.equalsIgnoreCase("ChannelType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChannelType = FValue.trim();
			}
			else
				ChannelType = null;
		}
		if (FCode.equalsIgnoreCase("UpAgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpAgentCom = FValue.trim();
			}
			else
				UpAgentCom = null;
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
		if (FCode.equalsIgnoreCase("Address"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Address = FValue.trim();
			}
			else
				Address = null;
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
		if (FCode.equalsIgnoreCase("WebAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAddress = FValue.trim();
			}
			else
				WebAddress = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan = FValue.trim();
			}
			else
				LinkMan = null;
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
		if (FCode.equalsIgnoreCase("Corporation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Corporation = FValue.trim();
			}
			else
				Corporation = null;
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
		if (FCode.equalsIgnoreCase("ACType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ACType = FValue.trim();
			}
			else
				ACType = null;
		}
		if (FCode.equalsIgnoreCase("SellFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SellFlag = FValue.trim();
			}
			else
				SellFlag = null;
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
		if (FCode.equalsIgnoreCase("BankType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankType = FValue.trim();
			}
			else
				BankType = null;
		}
		if (FCode.equalsIgnoreCase("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equalsIgnoreCase("BusiLicenseCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiLicenseCode = FValue.trim();
			}
			else
				BusiLicenseCode = null;
		}
		if (FCode.equalsIgnoreCase("InsureID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsureID = FValue.trim();
			}
			else
				InsureID = null;
		}
		if (FCode.equalsIgnoreCase("InsurePrincipal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsurePrincipal = FValue.trim();
			}
			else
				InsurePrincipal = null;
		}
		if (FCode.equalsIgnoreCase("ChiefBusiness"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChiefBusiness = FValue.trim();
			}
			else
				ChiefBusiness = null;
		}
		if (FCode.equalsIgnoreCase("BusiAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiAddress = FValue.trim();
			}
			else
				BusiAddress = null;
		}
		if (FCode.equalsIgnoreCase("SubscribeMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubscribeMan = FValue.trim();
			}
			else
				SubscribeMan = null;
		}
		if (FCode.equalsIgnoreCase("SubscribeManDuty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubscribeManDuty = FValue.trim();
			}
			else
				SubscribeManDuty = null;
		}
		if (FCode.equalsIgnoreCase("LicenseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LicenseNo = FValue.trim();
			}
			else
				LicenseNo = null;
		}
		if (FCode.equalsIgnoreCase("RegionalismCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RegionalismCode = FValue.trim();
			}
			else
				RegionalismCode = null;
		}
		if (FCode.equalsIgnoreCase("AppAgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppAgentCom = FValue.trim();
			}
			else
				AppAgentCom = null;
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
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
		}
		if (FCode.equalsIgnoreCase("BusinessCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessCode = FValue.trim();
			}
			else
				BusinessCode = null;
		}
		if (FCode.equalsIgnoreCase("LicenseStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LicenseStartDate = fDate.getDate( FValue );
			}
			else
				LicenseStartDate = null;
		}
		if (FCode.equalsIgnoreCase("LicenseEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LicenseEndDate = fDate.getDate( FValue );
			}
			else
				LicenseEndDate = null;
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
		if (FCode.equalsIgnoreCase("AgentComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComCode = FValue.trim();
			}
			else
				AgentComCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("ComToAgentFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComToAgentFlag = FValue.trim();
			}
			else
				ComToAgentFlag = null;
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAComSchema other = (LAComSchema)otherObject;
		return
			AgentCom.equals(other.getAgentCom())
			&& ManageCom.equals(other.getManageCom())
			&& AreaType.equals(other.getAreaType())
			&& ChannelType.equals(other.getChannelType())
			&& UpAgentCom.equals(other.getUpAgentCom())
			&& Name.equals(other.getName())
			&& Address.equals(other.getAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& EMail.equals(other.getEMail())
			&& WebAddress.equals(other.getWebAddress())
			&& LinkMan.equals(other.getLinkMan())
			&& Password.equals(other.getPassword())
			&& Corporation.equals(other.getCorporation())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& BusinessType.equals(other.getBusinessType())
			&& GrpNature.equals(other.getGrpNature())
			&& ACType.equals(other.getACType())
			&& SellFlag.equals(other.getSellFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BankType.equals(other.getBankType())
			&& CalFlag.equals(other.getCalFlag())
			&& BusiLicenseCode.equals(other.getBusiLicenseCode())
			&& InsureID.equals(other.getInsureID())
			&& InsurePrincipal.equals(other.getInsurePrincipal())
			&& ChiefBusiness.equals(other.getChiefBusiness())
			&& BusiAddress.equals(other.getBusiAddress())
			&& SubscribeMan.equals(other.getSubscribeMan())
			&& SubscribeManDuty.equals(other.getSubscribeManDuty())
			&& LicenseNo.equals(other.getLicenseNo())
			&& RegionalismCode.equals(other.getRegionalismCode())
			&& AppAgentCom.equals(other.getAppAgentCom())
			&& State.equals(other.getState())
			&& Noti.equals(other.getNoti())
			&& BusinessCode.equals(other.getBusinessCode())
			&& fDate.getString(LicenseStartDate).equals(other.getLicenseStartDate())
			&& fDate.getString(LicenseEndDate).equals(other.getLicenseEndDate())
			&& BranchType.equals(other.getBranchType())
			&& AgentComCode.equals(other.getAgentComCode())
			&& AgentCode.equals(other.getAgentCode())
			&& ComToAgentFlag.equals(other.getComToAgentFlag())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& fDate.getString(EndDate).equals(other.getEndDate());
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
		if( strFieldName.equals("AgentCom") ) {
			return 0;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 1;
		}
		if( strFieldName.equals("AreaType") ) {
			return 2;
		}
		if( strFieldName.equals("ChannelType") ) {
			return 3;
		}
		if( strFieldName.equals("UpAgentCom") ) {
			return 4;
		}
		if( strFieldName.equals("Name") ) {
			return 5;
		}
		if( strFieldName.equals("Address") ) {
			return 6;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 7;
		}
		if( strFieldName.equals("Phone") ) {
			return 8;
		}
		if( strFieldName.equals("Fax") ) {
			return 9;
		}
		if( strFieldName.equals("EMail") ) {
			return 10;
		}
		if( strFieldName.equals("WebAddress") ) {
			return 11;
		}
		if( strFieldName.equals("LinkMan") ) {
			return 12;
		}
		if( strFieldName.equals("Password") ) {
			return 13;
		}
		if( strFieldName.equals("Corporation") ) {
			return 14;
		}
		if( strFieldName.equals("BankCode") ) {
			return 15;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 16;
		}
		if( strFieldName.equals("BusinessType") ) {
			return 17;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 18;
		}
		if( strFieldName.equals("ACType") ) {
			return 19;
		}
		if( strFieldName.equals("SellFlag") ) {
			return 20;
		}
		if( strFieldName.equals("Operator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 25;
		}
		if( strFieldName.equals("BankType") ) {
			return 26;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 27;
		}
		if( strFieldName.equals("BusiLicenseCode") ) {
			return 28;
		}
		if( strFieldName.equals("InsureID") ) {
			return 29;
		}
		if( strFieldName.equals("InsurePrincipal") ) {
			return 30;
		}
		if( strFieldName.equals("ChiefBusiness") ) {
			return 31;
		}
		if( strFieldName.equals("BusiAddress") ) {
			return 32;
		}
		if( strFieldName.equals("SubscribeMan") ) {
			return 33;
		}
		if( strFieldName.equals("SubscribeManDuty") ) {
			return 34;
		}
		if( strFieldName.equals("LicenseNo") ) {
			return 35;
		}
		if( strFieldName.equals("RegionalismCode") ) {
			return 36;
		}
		if( strFieldName.equals("AppAgentCom") ) {
			return 37;
		}
		if( strFieldName.equals("State") ) {
			return 38;
		}
		if( strFieldName.equals("Noti") ) {
			return 39;
		}
		if( strFieldName.equals("BusinessCode") ) {
			return 40;
		}
		if( strFieldName.equals("LicenseStartDate") ) {
			return 41;
		}
		if( strFieldName.equals("LicenseEndDate") ) {
			return 42;
		}
		if( strFieldName.equals("BranchType") ) {
			return 43;
		}
		if( strFieldName.equals("AgentComCode") ) {
			return 44;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 45;
		}
		if( strFieldName.equals("ComToAgentFlag") ) {
			return 46;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 47;
		}
		if( strFieldName.equals("EndDate") ) {
			return 48;
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
				strFieldName = "AgentCom";
				break;
			case 1:
				strFieldName = "ManageCom";
				break;
			case 2:
				strFieldName = "AreaType";
				break;
			case 3:
				strFieldName = "ChannelType";
				break;
			case 4:
				strFieldName = "UpAgentCom";
				break;
			case 5:
				strFieldName = "Name";
				break;
			case 6:
				strFieldName = "Address";
				break;
			case 7:
				strFieldName = "ZipCode";
				break;
			case 8:
				strFieldName = "Phone";
				break;
			case 9:
				strFieldName = "Fax";
				break;
			case 10:
				strFieldName = "EMail";
				break;
			case 11:
				strFieldName = "WebAddress";
				break;
			case 12:
				strFieldName = "LinkMan";
				break;
			case 13:
				strFieldName = "Password";
				break;
			case 14:
				strFieldName = "Corporation";
				break;
			case 15:
				strFieldName = "BankCode";
				break;
			case 16:
				strFieldName = "BankAccNo";
				break;
			case 17:
				strFieldName = "BusinessType";
				break;
			case 18:
				strFieldName = "GrpNature";
				break;
			case 19:
				strFieldName = "ACType";
				break;
			case 20:
				strFieldName = "SellFlag";
				break;
			case 21:
				strFieldName = "Operator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyDate";
				break;
			case 25:
				strFieldName = "ModifyTime";
				break;
			case 26:
				strFieldName = "BankType";
				break;
			case 27:
				strFieldName = "CalFlag";
				break;
			case 28:
				strFieldName = "BusiLicenseCode";
				break;
			case 29:
				strFieldName = "InsureID";
				break;
			case 30:
				strFieldName = "InsurePrincipal";
				break;
			case 31:
				strFieldName = "ChiefBusiness";
				break;
			case 32:
				strFieldName = "BusiAddress";
				break;
			case 33:
				strFieldName = "SubscribeMan";
				break;
			case 34:
				strFieldName = "SubscribeManDuty";
				break;
			case 35:
				strFieldName = "LicenseNo";
				break;
			case 36:
				strFieldName = "RegionalismCode";
				break;
			case 37:
				strFieldName = "AppAgentCom";
				break;
			case 38:
				strFieldName = "State";
				break;
			case 39:
				strFieldName = "Noti";
				break;
			case 40:
				strFieldName = "BusinessCode";
				break;
			case 41:
				strFieldName = "LicenseStartDate";
				break;
			case 42:
				strFieldName = "LicenseEndDate";
				break;
			case 43:
				strFieldName = "BranchType";
				break;
			case 44:
				strFieldName = "AgentComCode";
				break;
			case 45:
				strFieldName = "AgentCode";
				break;
			case 46:
				strFieldName = "ComToAgentFlag";
				break;
			case 47:
				strFieldName = "FoundDate";
				break;
			case 48:
				strFieldName = "EndDate";
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
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChannelType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpAgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
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
		if( strFieldName.equals("WebAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Corporation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ACType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SellFlag") ) {
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
		if( strFieldName.equals("BankType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiLicenseCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsureID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsurePrincipal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChiefBusiness") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubscribeMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubscribeManDuty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RegionalismCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppAgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LicenseStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LicenseEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComToAgentFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
