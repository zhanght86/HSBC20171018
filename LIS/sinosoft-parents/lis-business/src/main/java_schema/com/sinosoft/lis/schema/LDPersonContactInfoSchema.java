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
import com.sinosoft.lis.db.LDPersonContactInfoDB;

/*
 * <p>ClassName: LDPersonContactInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 客户管理
 */
public class LDPersonContactInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPersonContactInfoSchema.class);
	// @Field
	/** 客户号码 */
	private String CustomerNo;
	/** 省 */
	private String Province;
	/** 市 */
	private String City;
	/** 区/县 */
	private String County;
	/** 通讯地址 */
	private String PostalAddress;
	/** 通讯邮编 */
	private String ZipCode;
	/** 通讯电话 */
	private String Phone;
	/** 通讯传真 */
	private String Fax;
	/** 家庭地址 */
	private String HomeAddress;
	/** 家庭邮编 */
	private String HomeZipCode;
	/** 家庭电话 */
	private String HomePhone;
	/** 家庭传真 */
	private String HomeFax;
	/** 单位名称 */
	private String CompanyName;
	/** 单位地址 */
	private String CompanyAddress;
	/** 单位邮编 */
	private String CompanyZipCode;
	/** 单位电话 */
	private String CompanyPhone;
	/** 单位传真 */
	private String CompanyFax;
	/** 紧急联系人 */
	private String UrgentLinkMan;
	/** 与紧急联系人关系 */
	private String RelationToUrgent;
	/** 紧急联系人手机 */
	private String UrgentMobile;
	/** 手机1 */
	private String Mobile1;
	/** Email1 */
	private String EMail1;
	/** Qq1 */
	private String QQ1;
	/** Msn1 */
	private String MSN1;
	/** 微信号1 */
	private String Wechat1;
	/** 手机2 */
	private String Mobile2;
	/** Email2 */
	private String EMail2;
	/** Qq2 */
	private String QQ2;
	/** Msn2 */
	private String MSN2;
	/** 微信号2 */
	private String Wechat2;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPersonContactInfoSchema()
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
		LDPersonContactInfoSchema cloned = (LDPersonContactInfoSchema)super.clone();
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
	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String aProvince)
	{
		if(aProvince!=null && aProvince.length()>10)
			throw new IllegalArgumentException("省Province值"+aProvince+"的长度"+aProvince.length()+"大于最大值10");
		Province = aProvince;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String aCity)
	{
		if(aCity!=null && aCity.length()>10)
			throw new IllegalArgumentException("市City值"+aCity+"的长度"+aCity.length()+"大于最大值10");
		City = aCity;
	}
	public String getCounty()
	{
		return County;
	}
	public void setCounty(String aCounty)
	{
		if(aCounty!=null && aCounty.length()>10)
			throw new IllegalArgumentException("区/县County值"+aCounty+"的长度"+aCounty.length()+"大于最大值10");
		County = aCounty;
	}
	public String getPostalAddress()
	{
		return PostalAddress;
	}
	public void setPostalAddress(String aPostalAddress)
	{
		if(aPostalAddress!=null && aPostalAddress.length()>300)
			throw new IllegalArgumentException("通讯地址PostalAddress值"+aPostalAddress+"的长度"+aPostalAddress.length()+"大于最大值300");
		PostalAddress = aPostalAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		if(aZipCode!=null && aZipCode.length()>10)
			throw new IllegalArgumentException("通讯邮编ZipCode值"+aZipCode+"的长度"+aZipCode.length()+"大于最大值10");
		ZipCode = aZipCode;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>20)
			throw new IllegalArgumentException("通讯电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值20");
		Phone = aPhone;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>20)
			throw new IllegalArgumentException("通讯传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值20");
		Fax = aFax;
	}
	public String getHomeAddress()
	{
		return HomeAddress;
	}
	public void setHomeAddress(String aHomeAddress)
	{
		if(aHomeAddress!=null && aHomeAddress.length()>300)
			throw new IllegalArgumentException("家庭地址HomeAddress值"+aHomeAddress+"的长度"+aHomeAddress.length()+"大于最大值300");
		HomeAddress = aHomeAddress;
	}
	public String getHomeZipCode()
	{
		return HomeZipCode;
	}
	public void setHomeZipCode(String aHomeZipCode)
	{
		if(aHomeZipCode!=null && aHomeZipCode.length()>10)
			throw new IllegalArgumentException("家庭邮编HomeZipCode值"+aHomeZipCode+"的长度"+aHomeZipCode.length()+"大于最大值10");
		HomeZipCode = aHomeZipCode;
	}
	public String getHomePhone()
	{
		return HomePhone;
	}
	public void setHomePhone(String aHomePhone)
	{
		if(aHomePhone!=null && aHomePhone.length()>20)
			throw new IllegalArgumentException("家庭电话HomePhone值"+aHomePhone+"的长度"+aHomePhone.length()+"大于最大值20");
		HomePhone = aHomePhone;
	}
	public String getHomeFax()
	{
		return HomeFax;
	}
	public void setHomeFax(String aHomeFax)
	{
		if(aHomeFax!=null && aHomeFax.length()>20)
			throw new IllegalArgumentException("家庭传真HomeFax值"+aHomeFax+"的长度"+aHomeFax.length()+"大于最大值20");
		HomeFax = aHomeFax;
	}
	public String getCompanyName()
	{
		return CompanyName;
	}
	public void setCompanyName(String aCompanyName)
	{
		if(aCompanyName!=null && aCompanyName.length()>200)
			throw new IllegalArgumentException("单位名称CompanyName值"+aCompanyName+"的长度"+aCompanyName.length()+"大于最大值200");
		CompanyName = aCompanyName;
	}
	public String getCompanyAddress()
	{
		return CompanyAddress;
	}
	public void setCompanyAddress(String aCompanyAddress)
	{
		if(aCompanyAddress!=null && aCompanyAddress.length()>300)
			throw new IllegalArgumentException("单位地址CompanyAddress值"+aCompanyAddress+"的长度"+aCompanyAddress.length()+"大于最大值300");
		CompanyAddress = aCompanyAddress;
	}
	public String getCompanyZipCode()
	{
		return CompanyZipCode;
	}
	public void setCompanyZipCode(String aCompanyZipCode)
	{
		if(aCompanyZipCode!=null && aCompanyZipCode.length()>10)
			throw new IllegalArgumentException("单位邮编CompanyZipCode值"+aCompanyZipCode+"的长度"+aCompanyZipCode.length()+"大于最大值10");
		CompanyZipCode = aCompanyZipCode;
	}
	public String getCompanyPhone()
	{
		return CompanyPhone;
	}
	public void setCompanyPhone(String aCompanyPhone)
	{
		if(aCompanyPhone!=null && aCompanyPhone.length()>20)
			throw new IllegalArgumentException("单位电话CompanyPhone值"+aCompanyPhone+"的长度"+aCompanyPhone.length()+"大于最大值20");
		CompanyPhone = aCompanyPhone;
	}
	public String getCompanyFax()
	{
		return CompanyFax;
	}
	public void setCompanyFax(String aCompanyFax)
	{
		if(aCompanyFax!=null && aCompanyFax.length()>20)
			throw new IllegalArgumentException("单位传真CompanyFax值"+aCompanyFax+"的长度"+aCompanyFax.length()+"大于最大值20");
		CompanyFax = aCompanyFax;
	}
	public String getUrgentLinkMan()
	{
		return UrgentLinkMan;
	}
	public void setUrgentLinkMan(String aUrgentLinkMan)
	{
		if(aUrgentLinkMan!=null && aUrgentLinkMan.length()>300)
			throw new IllegalArgumentException("紧急联系人UrgentLinkMan值"+aUrgentLinkMan+"的长度"+aUrgentLinkMan.length()+"大于最大值300");
		UrgentLinkMan = aUrgentLinkMan;
	}
	public String getRelationToUrgent()
	{
		return RelationToUrgent;
	}
	public void setRelationToUrgent(String aRelationToUrgent)
	{
		if(aRelationToUrgent!=null && aRelationToUrgent.length()>2)
			throw new IllegalArgumentException("与紧急联系人关系RelationToUrgent值"+aRelationToUrgent+"的长度"+aRelationToUrgent.length()+"大于最大值2");
		RelationToUrgent = aRelationToUrgent;
	}
	public String getUrgentMobile()
	{
		return UrgentMobile;
	}
	public void setUrgentMobile(String aUrgentMobile)
	{
		if(aUrgentMobile!=null && aUrgentMobile.length()>20)
			throw new IllegalArgumentException("紧急联系人手机UrgentMobile值"+aUrgentMobile+"的长度"+aUrgentMobile.length()+"大于最大值20");
		UrgentMobile = aUrgentMobile;
	}
	public String getMobile1()
	{
		return Mobile1;
	}
	public void setMobile1(String aMobile1)
	{
		if(aMobile1!=null && aMobile1.length()>20)
			throw new IllegalArgumentException("手机1Mobile1值"+aMobile1+"的长度"+aMobile1.length()+"大于最大值20");
		Mobile1 = aMobile1;
	}
	public String getEMail1()
	{
		return EMail1;
	}
	public void setEMail1(String aEMail1)
	{
		if(aEMail1!=null && aEMail1.length()>60)
			throw new IllegalArgumentException("Email1EMail1值"+aEMail1+"的长度"+aEMail1.length()+"大于最大值60");
		EMail1 = aEMail1;
	}
	public String getQQ1()
	{
		return QQ1;
	}
	public void setQQ1(String aQQ1)
	{
		if(aQQ1!=null && aQQ1.length()>20)
			throw new IllegalArgumentException("Qq1QQ1值"+aQQ1+"的长度"+aQQ1.length()+"大于最大值20");
		QQ1 = aQQ1;
	}
	public String getMSN1()
	{
		return MSN1;
	}
	public void setMSN1(String aMSN1)
	{
		if(aMSN1!=null && aMSN1.length()>60)
			throw new IllegalArgumentException("Msn1MSN1值"+aMSN1+"的长度"+aMSN1.length()+"大于最大值60");
		MSN1 = aMSN1;
	}
	public String getWechat1()
	{
		return Wechat1;
	}
	public void setWechat1(String aWechat1)
	{
		if(aWechat1!=null && aWechat1.length()>60)
			throw new IllegalArgumentException("微信号1Wechat1值"+aWechat1+"的长度"+aWechat1.length()+"大于最大值60");
		Wechat1 = aWechat1;
	}
	public String getMobile2()
	{
		return Mobile2;
	}
	public void setMobile2(String aMobile2)
	{
		if(aMobile2!=null && aMobile2.length()>20)
			throw new IllegalArgumentException("手机2Mobile2值"+aMobile2+"的长度"+aMobile2.length()+"大于最大值20");
		Mobile2 = aMobile2;
	}
	public String getEMail2()
	{
		return EMail2;
	}
	public void setEMail2(String aEMail2)
	{
		if(aEMail2!=null && aEMail2.length()>60)
			throw new IllegalArgumentException("Email2EMail2值"+aEMail2+"的长度"+aEMail2.length()+"大于最大值60");
		EMail2 = aEMail2;
	}
	public String getQQ2()
	{
		return QQ2;
	}
	public void setQQ2(String aQQ2)
	{
		if(aQQ2!=null && aQQ2.length()>20)
			throw new IllegalArgumentException("Qq2QQ2值"+aQQ2+"的长度"+aQQ2.length()+"大于最大值20");
		QQ2 = aQQ2;
	}
	public String getMSN2()
	{
		return MSN2;
	}
	public void setMSN2(String aMSN2)
	{
		if(aMSN2!=null && aMSN2.length()>60)
			throw new IllegalArgumentException("Msn2MSN2值"+aMSN2+"的长度"+aMSN2.length()+"大于最大值60");
		MSN2 = aMSN2;
	}
	public String getWechat2()
	{
		return Wechat2;
	}
	public void setWechat2(String aWechat2)
	{
		if(aWechat2!=null && aWechat2.length()>60)
			throw new IllegalArgumentException("微信号2Wechat2值"+aWechat2+"的长度"+aWechat2.length()+"大于最大值60");
		Wechat2 = aWechat2;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	* 使用另外一个 LDPersonContactInfoSchema 对象给 Schema 赋值
	* @param: aLDPersonContactInfoSchema LDPersonContactInfoSchema
	**/
	public void setSchema(LDPersonContactInfoSchema aLDPersonContactInfoSchema)
	{
		this.CustomerNo = aLDPersonContactInfoSchema.getCustomerNo();
		this.Province = aLDPersonContactInfoSchema.getProvince();
		this.City = aLDPersonContactInfoSchema.getCity();
		this.County = aLDPersonContactInfoSchema.getCounty();
		this.PostalAddress = aLDPersonContactInfoSchema.getPostalAddress();
		this.ZipCode = aLDPersonContactInfoSchema.getZipCode();
		this.Phone = aLDPersonContactInfoSchema.getPhone();
		this.Fax = aLDPersonContactInfoSchema.getFax();
		this.HomeAddress = aLDPersonContactInfoSchema.getHomeAddress();
		this.HomeZipCode = aLDPersonContactInfoSchema.getHomeZipCode();
		this.HomePhone = aLDPersonContactInfoSchema.getHomePhone();
		this.HomeFax = aLDPersonContactInfoSchema.getHomeFax();
		this.CompanyName = aLDPersonContactInfoSchema.getCompanyName();
		this.CompanyAddress = aLDPersonContactInfoSchema.getCompanyAddress();
		this.CompanyZipCode = aLDPersonContactInfoSchema.getCompanyZipCode();
		this.CompanyPhone = aLDPersonContactInfoSchema.getCompanyPhone();
		this.CompanyFax = aLDPersonContactInfoSchema.getCompanyFax();
		this.UrgentLinkMan = aLDPersonContactInfoSchema.getUrgentLinkMan();
		this.RelationToUrgent = aLDPersonContactInfoSchema.getRelationToUrgent();
		this.UrgentMobile = aLDPersonContactInfoSchema.getUrgentMobile();
		this.Mobile1 = aLDPersonContactInfoSchema.getMobile1();
		this.EMail1 = aLDPersonContactInfoSchema.getEMail1();
		this.QQ1 = aLDPersonContactInfoSchema.getQQ1();
		this.MSN1 = aLDPersonContactInfoSchema.getMSN1();
		this.Wechat1 = aLDPersonContactInfoSchema.getWechat1();
		this.Mobile2 = aLDPersonContactInfoSchema.getMobile2();
		this.EMail2 = aLDPersonContactInfoSchema.getEMail2();
		this.QQ2 = aLDPersonContactInfoSchema.getQQ2();
		this.MSN2 = aLDPersonContactInfoSchema.getMSN2();
		this.Wechat2 = aLDPersonContactInfoSchema.getWechat2();
		this.MakeOperator = aLDPersonContactInfoSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDPersonContactInfoSchema.getMakeDate());
		this.MakeTime = aLDPersonContactInfoSchema.getMakeTime();
		this.ModifyOperator = aLDPersonContactInfoSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDPersonContactInfoSchema.getModifyDate());
		this.ModifyTime = aLDPersonContactInfoSchema.getModifyTime();
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

			if( rs.getString("Province") == null )
				this.Province = null;
			else
				this.Province = rs.getString("Province").trim();

			if( rs.getString("City") == null )
				this.City = null;
			else
				this.City = rs.getString("City").trim();

			if( rs.getString("County") == null )
				this.County = null;
			else
				this.County = rs.getString("County").trim();

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

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("HomeAddress") == null )
				this.HomeAddress = null;
			else
				this.HomeAddress = rs.getString("HomeAddress").trim();

			if( rs.getString("HomeZipCode") == null )
				this.HomeZipCode = null;
			else
				this.HomeZipCode = rs.getString("HomeZipCode").trim();

			if( rs.getString("HomePhone") == null )
				this.HomePhone = null;
			else
				this.HomePhone = rs.getString("HomePhone").trim();

			if( rs.getString("HomeFax") == null )
				this.HomeFax = null;
			else
				this.HomeFax = rs.getString("HomeFax").trim();

			if( rs.getString("CompanyName") == null )
				this.CompanyName = null;
			else
				this.CompanyName = rs.getString("CompanyName").trim();

			if( rs.getString("CompanyAddress") == null )
				this.CompanyAddress = null;
			else
				this.CompanyAddress = rs.getString("CompanyAddress").trim();

			if( rs.getString("CompanyZipCode") == null )
				this.CompanyZipCode = null;
			else
				this.CompanyZipCode = rs.getString("CompanyZipCode").trim();

			if( rs.getString("CompanyPhone") == null )
				this.CompanyPhone = null;
			else
				this.CompanyPhone = rs.getString("CompanyPhone").trim();

			if( rs.getString("CompanyFax") == null )
				this.CompanyFax = null;
			else
				this.CompanyFax = rs.getString("CompanyFax").trim();

			if( rs.getString("UrgentLinkMan") == null )
				this.UrgentLinkMan = null;
			else
				this.UrgentLinkMan = rs.getString("UrgentLinkMan").trim();

			if( rs.getString("RelationToUrgent") == null )
				this.RelationToUrgent = null;
			else
				this.RelationToUrgent = rs.getString("RelationToUrgent").trim();

			if( rs.getString("UrgentMobile") == null )
				this.UrgentMobile = null;
			else
				this.UrgentMobile = rs.getString("UrgentMobile").trim();

			if( rs.getString("Mobile1") == null )
				this.Mobile1 = null;
			else
				this.Mobile1 = rs.getString("Mobile1").trim();

			if( rs.getString("EMail1") == null )
				this.EMail1 = null;
			else
				this.EMail1 = rs.getString("EMail1").trim();

			if( rs.getString("QQ1") == null )
				this.QQ1 = null;
			else
				this.QQ1 = rs.getString("QQ1").trim();

			if( rs.getString("MSN1") == null )
				this.MSN1 = null;
			else
				this.MSN1 = rs.getString("MSN1").trim();

			if( rs.getString("Wechat1") == null )
				this.Wechat1 = null;
			else
				this.Wechat1 = rs.getString("Wechat1").trim();

			if( rs.getString("Mobile2") == null )
				this.Mobile2 = null;
			else
				this.Mobile2 = rs.getString("Mobile2").trim();

			if( rs.getString("EMail2") == null )
				this.EMail2 = null;
			else
				this.EMail2 = rs.getString("EMail2").trim();

			if( rs.getString("QQ2") == null )
				this.QQ2 = null;
			else
				this.QQ2 = rs.getString("QQ2").trim();

			if( rs.getString("MSN2") == null )
				this.MSN2 = null;
			else
				this.MSN2 = rs.getString("MSN2").trim();

			if( rs.getString("Wechat2") == null )
				this.Wechat2 = null;
			else
				this.Wechat2 = rs.getString("Wechat2").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDPersonContactInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonContactInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPersonContactInfoSchema getSchema()
	{
		LDPersonContactInfoSchema aLDPersonContactInfoSchema = new LDPersonContactInfoSchema();
		aLDPersonContactInfoSchema.setSchema(this);
		return aLDPersonContactInfoSchema;
	}

	public LDPersonContactInfoDB getDB()
	{
		LDPersonContactInfoDB aDBOper = new LDPersonContactInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPersonContactInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Province)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(City)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(County)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostalAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomePhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeFax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CompanyFax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgentLinkMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToUrgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgentMobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QQ1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MSN1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Wechat1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QQ2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MSN2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Wechat2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPersonContactInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HomeZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			HomePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			HomeFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CompanyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CompanyAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CompanyZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CompanyPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CompanyFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			UrgentLinkMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			RelationToUrgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			UrgentMobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Mobile1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			EMail1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			QQ1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MSN1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Wechat1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Mobile2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			EMail2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			QQ2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MSN2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Wechat2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonContactInfoSchema";
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
		if (FCode.equalsIgnoreCase("Province"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Province));
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(City));
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(County));
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
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("HomeAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddress));
		}
		if (FCode.equalsIgnoreCase("HomeZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeZipCode));
		}
		if (FCode.equalsIgnoreCase("HomePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomePhone));
		}
		if (FCode.equalsIgnoreCase("HomeFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeFax));
		}
		if (FCode.equalsIgnoreCase("CompanyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyName));
		}
		if (FCode.equalsIgnoreCase("CompanyAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyAddress));
		}
		if (FCode.equalsIgnoreCase("CompanyZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyZipCode));
		}
		if (FCode.equalsIgnoreCase("CompanyPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyPhone));
		}
		if (FCode.equalsIgnoreCase("CompanyFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyFax));
		}
		if (FCode.equalsIgnoreCase("UrgentLinkMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgentLinkMan));
		}
		if (FCode.equalsIgnoreCase("RelationToUrgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToUrgent));
		}
		if (FCode.equalsIgnoreCase("UrgentMobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgentMobile));
		}
		if (FCode.equalsIgnoreCase("Mobile1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile1));
		}
		if (FCode.equalsIgnoreCase("EMail1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail1));
		}
		if (FCode.equalsIgnoreCase("QQ1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QQ1));
		}
		if (FCode.equalsIgnoreCase("MSN1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MSN1));
		}
		if (FCode.equalsIgnoreCase("Wechat1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Wechat1));
		}
		if (FCode.equalsIgnoreCase("Mobile2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile2));
		}
		if (FCode.equalsIgnoreCase("EMail2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail2));
		}
		if (FCode.equalsIgnoreCase("QQ2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QQ2));
		}
		if (FCode.equalsIgnoreCase("MSN2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MSN2));
		}
		if (FCode.equalsIgnoreCase("Wechat2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Wechat2));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(County);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HomeZipCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(HomePhone);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(HomeFax);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CompanyName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CompanyAddress);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CompanyZipCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CompanyPhone);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CompanyFax);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(UrgentLinkMan);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(RelationToUrgent);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(UrgentMobile);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Mobile1);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(EMail1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(QQ1);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MSN1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Wechat1);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Mobile2);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(EMail2);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(QQ2);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(MSN2);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Wechat2);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 35:
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

		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("Province"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Province = FValue.trim();
			}
			else
				Province = null;
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				City = FValue.trim();
			}
			else
				City = null;
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				County = FValue.trim();
			}
			else
				County = null;
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
		if (FCode.equalsIgnoreCase("Fax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax = FValue.trim();
			}
			else
				Fax = null;
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
		if (FCode.equalsIgnoreCase("HomeZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeZipCode = FValue.trim();
			}
			else
				HomeZipCode = null;
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
		if (FCode.equalsIgnoreCase("HomeFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeFax = FValue.trim();
			}
			else
				HomeFax = null;
		}
		if (FCode.equalsIgnoreCase("CompanyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyName = FValue.trim();
			}
			else
				CompanyName = null;
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
		if (FCode.equalsIgnoreCase("CompanyZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyZipCode = FValue.trim();
			}
			else
				CompanyZipCode = null;
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
		if (FCode.equalsIgnoreCase("CompanyFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyFax = FValue.trim();
			}
			else
				CompanyFax = null;
		}
		if (FCode.equalsIgnoreCase("UrgentLinkMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgentLinkMan = FValue.trim();
			}
			else
				UrgentLinkMan = null;
		}
		if (FCode.equalsIgnoreCase("RelationToUrgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToUrgent = FValue.trim();
			}
			else
				RelationToUrgent = null;
		}
		if (FCode.equalsIgnoreCase("UrgentMobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgentMobile = FValue.trim();
			}
			else
				UrgentMobile = null;
		}
		if (FCode.equalsIgnoreCase("Mobile1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile1 = FValue.trim();
			}
			else
				Mobile1 = null;
		}
		if (FCode.equalsIgnoreCase("EMail1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail1 = FValue.trim();
			}
			else
				EMail1 = null;
		}
		if (FCode.equalsIgnoreCase("QQ1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QQ1 = FValue.trim();
			}
			else
				QQ1 = null;
		}
		if (FCode.equalsIgnoreCase("MSN1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MSN1 = FValue.trim();
			}
			else
				MSN1 = null;
		}
		if (FCode.equalsIgnoreCase("Wechat1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Wechat1 = FValue.trim();
			}
			else
				Wechat1 = null;
		}
		if (FCode.equalsIgnoreCase("Mobile2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile2 = FValue.trim();
			}
			else
				Mobile2 = null;
		}
		if (FCode.equalsIgnoreCase("EMail2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail2 = FValue.trim();
			}
			else
				EMail2 = null;
		}
		if (FCode.equalsIgnoreCase("QQ2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QQ2 = FValue.trim();
			}
			else
				QQ2 = null;
		}
		if (FCode.equalsIgnoreCase("MSN2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MSN2 = FValue.trim();
			}
			else
				MSN2 = null;
		}
		if (FCode.equalsIgnoreCase("Wechat2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Wechat2 = FValue.trim();
			}
			else
				Wechat2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LDPersonContactInfoSchema other = (LDPersonContactInfoSchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& Province.equals(other.getProvince())
			&& City.equals(other.getCity())
			&& County.equals(other.getCounty())
			&& PostalAddress.equals(other.getPostalAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& HomeAddress.equals(other.getHomeAddress())
			&& HomeZipCode.equals(other.getHomeZipCode())
			&& HomePhone.equals(other.getHomePhone())
			&& HomeFax.equals(other.getHomeFax())
			&& CompanyName.equals(other.getCompanyName())
			&& CompanyAddress.equals(other.getCompanyAddress())
			&& CompanyZipCode.equals(other.getCompanyZipCode())
			&& CompanyPhone.equals(other.getCompanyPhone())
			&& CompanyFax.equals(other.getCompanyFax())
			&& UrgentLinkMan.equals(other.getUrgentLinkMan())
			&& RelationToUrgent.equals(other.getRelationToUrgent())
			&& UrgentMobile.equals(other.getUrgentMobile())
			&& Mobile1.equals(other.getMobile1())
			&& EMail1.equals(other.getEMail1())
			&& QQ1.equals(other.getQQ1())
			&& MSN1.equals(other.getMSN1())
			&& Wechat1.equals(other.getWechat1())
			&& Mobile2.equals(other.getMobile2())
			&& EMail2.equals(other.getEMail2())
			&& QQ2.equals(other.getQQ2())
			&& MSN2.equals(other.getMSN2())
			&& Wechat2.equals(other.getWechat2())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("CustomerNo") ) {
			return 0;
		}
		if( strFieldName.equals("Province") ) {
			return 1;
		}
		if( strFieldName.equals("City") ) {
			return 2;
		}
		if( strFieldName.equals("County") ) {
			return 3;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 4;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 5;
		}
		if( strFieldName.equals("Phone") ) {
			return 6;
		}
		if( strFieldName.equals("Fax") ) {
			return 7;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 8;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return 9;
		}
		if( strFieldName.equals("HomePhone") ) {
			return 10;
		}
		if( strFieldName.equals("HomeFax") ) {
			return 11;
		}
		if( strFieldName.equals("CompanyName") ) {
			return 12;
		}
		if( strFieldName.equals("CompanyAddress") ) {
			return 13;
		}
		if( strFieldName.equals("CompanyZipCode") ) {
			return 14;
		}
		if( strFieldName.equals("CompanyPhone") ) {
			return 15;
		}
		if( strFieldName.equals("CompanyFax") ) {
			return 16;
		}
		if( strFieldName.equals("UrgentLinkMan") ) {
			return 17;
		}
		if( strFieldName.equals("RelationToUrgent") ) {
			return 18;
		}
		if( strFieldName.equals("UrgentMobile") ) {
			return 19;
		}
		if( strFieldName.equals("Mobile1") ) {
			return 20;
		}
		if( strFieldName.equals("EMail1") ) {
			return 21;
		}
		if( strFieldName.equals("QQ1") ) {
			return 22;
		}
		if( strFieldName.equals("MSN1") ) {
			return 23;
		}
		if( strFieldName.equals("Wechat1") ) {
			return 24;
		}
		if( strFieldName.equals("Mobile2") ) {
			return 25;
		}
		if( strFieldName.equals("EMail2") ) {
			return 26;
		}
		if( strFieldName.equals("QQ2") ) {
			return 27;
		}
		if( strFieldName.equals("MSN2") ) {
			return 28;
		}
		if( strFieldName.equals("Wechat2") ) {
			return 29;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 30;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 31;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 34;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 35;
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
				strFieldName = "Province";
				break;
			case 2:
				strFieldName = "City";
				break;
			case 3:
				strFieldName = "County";
				break;
			case 4:
				strFieldName = "PostalAddress";
				break;
			case 5:
				strFieldName = "ZipCode";
				break;
			case 6:
				strFieldName = "Phone";
				break;
			case 7:
				strFieldName = "Fax";
				break;
			case 8:
				strFieldName = "HomeAddress";
				break;
			case 9:
				strFieldName = "HomeZipCode";
				break;
			case 10:
				strFieldName = "HomePhone";
				break;
			case 11:
				strFieldName = "HomeFax";
				break;
			case 12:
				strFieldName = "CompanyName";
				break;
			case 13:
				strFieldName = "CompanyAddress";
				break;
			case 14:
				strFieldName = "CompanyZipCode";
				break;
			case 15:
				strFieldName = "CompanyPhone";
				break;
			case 16:
				strFieldName = "CompanyFax";
				break;
			case 17:
				strFieldName = "UrgentLinkMan";
				break;
			case 18:
				strFieldName = "RelationToUrgent";
				break;
			case 19:
				strFieldName = "UrgentMobile";
				break;
			case 20:
				strFieldName = "Mobile1";
				break;
			case 21:
				strFieldName = "EMail1";
				break;
			case 22:
				strFieldName = "QQ1";
				break;
			case 23:
				strFieldName = "MSN1";
				break;
			case 24:
				strFieldName = "Wechat1";
				break;
			case 25:
				strFieldName = "Mobile2";
				break;
			case 26:
				strFieldName = "EMail2";
				break;
			case 27:
				strFieldName = "QQ2";
				break;
			case 28:
				strFieldName = "MSN2";
				break;
			case 29:
				strFieldName = "Wechat2";
				break;
			case 30:
				strFieldName = "MakeOperator";
				break;
			case 31:
				strFieldName = "MakeDate";
				break;
			case 32:
				strFieldName = "MakeTime";
				break;
			case 33:
				strFieldName = "ModifyOperator";
				break;
			case 34:
				strFieldName = "ModifyDate";
				break;
			case 35:
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Province") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("City") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("County") ) {
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
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgentLinkMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToUrgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgentMobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QQ1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MSN1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Wechat1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QQ2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MSN2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Wechat2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
