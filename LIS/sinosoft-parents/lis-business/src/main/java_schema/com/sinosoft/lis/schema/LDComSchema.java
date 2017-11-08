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
import com.sinosoft.lis.db.LDComDB;

/*
 * <p>ClassName: LDComSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDComSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDComSchema.class);
	// @Field
	/** 机构编码 */
	private String ComCode;
	/** 对外公布的机构代码 */
	private String OutComCode;
	/** 机构名称 */
	private String Name;
	/** 短名称 */
	private String ShortName;
	/** 机构地址 */
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
	/** 主管人姓名 */
	private String SatrapName;
	/** 对应保监办代码 */
	private String InsuMonitorCode;
	/** 保险公司id */
	private String InsureID;
	/** 标识码 */
	private String SignID;
	/** 行政区划代码 */
	private String RegionalismCode;
	/** 公司性质 */
	private String ComNature;
	/** 校验码 */
	private String ValidCode;
	/** 标志 */
	private String Sign;
	/** 收费员渠道机构地区类型 */
	private String ComCitySize;
	/** 客户服务机构名称 */
	private String ServiceName;
	/** 客户服务机构编码 */
	private String ServiceNo;
	/** 客户服务电话 */
	private String ServicePhone;
	/** 客户服务投递地址 */
	private String ServicePostAddress;
	/** 机构级别 */
	private String ComGrade;
	/** 机构地区类型 */
	private String ComAreaType;
	/** 上级管理机构 */
	private String UpComCode;
	/** 直属属性 */
	private String IsDirUnder;
	/** 直销地区类别 */
	private String ComAreaType1;
	/** 省 */
	private String Province;
	/** 市 */
	private String City;
	/** 区/县 */
	private String County;
	/** 财务db */
	private String FinDB;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDComSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ComCode";

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
		LDComSchema cloned = (LDComSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>10)
			throw new IllegalArgumentException("机构编码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值10");
		ComCode = aComCode;
	}
	public String getOutComCode()
	{
		return OutComCode;
	}
	public void setOutComCode(String aOutComCode)
	{
		if(aOutComCode!=null && aOutComCode.length()>10)
			throw new IllegalArgumentException("对外公布的机构代码OutComCode值"+aOutComCode+"的长度"+aOutComCode.length()+"大于最大值10");
		OutComCode = aOutComCode;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>60)
			throw new IllegalArgumentException("机构名称Name值"+aName+"的长度"+aName.length()+"大于最大值60");
		Name = aName;
	}
	public String getShortName()
	{
		return ShortName;
	}
	public void setShortName(String aShortName)
	{
		if(aShortName!=null && aShortName.length()>60)
			throw new IllegalArgumentException("短名称ShortName值"+aShortName+"的长度"+aShortName.length()+"大于最大值60");
		ShortName = aShortName;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		if(aAddress!=null && aAddress.length()>80)
			throw new IllegalArgumentException("机构地址Address值"+aAddress+"的长度"+aAddress.length()+"大于最大值80");
		Address = aAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		if(aZipCode!=null && aZipCode.length()>6)
			throw new IllegalArgumentException("机构邮编ZipCode值"+aZipCode+"的长度"+aZipCode.length()+"大于最大值6");
		ZipCode = aZipCode;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>18)
			throw new IllegalArgumentException("机构电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值18");
		Phone = aPhone;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>18)
			throw new IllegalArgumentException("机构传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值18");
		Fax = aFax;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>60)
			throw new IllegalArgumentException("EmailEMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值60");
		EMail = aEMail;
	}
	public String getWebAddress()
	{
		return WebAddress;
	}
	public void setWebAddress(String aWebAddress)
	{
		if(aWebAddress!=null && aWebAddress.length()>100)
			throw new IllegalArgumentException("网址WebAddress值"+aWebAddress+"的长度"+aWebAddress.length()+"大于最大值100");
		WebAddress = aWebAddress;
	}
	public String getSatrapName()
	{
		return SatrapName;
	}
	public void setSatrapName(String aSatrapName)
	{
		if(aSatrapName!=null && aSatrapName.length()>20)
			throw new IllegalArgumentException("主管人姓名SatrapName值"+aSatrapName+"的长度"+aSatrapName.length()+"大于最大值20");
		SatrapName = aSatrapName;
	}
	public String getInsuMonitorCode()
	{
		return InsuMonitorCode;
	}
	public void setInsuMonitorCode(String aInsuMonitorCode)
	{
		if(aInsuMonitorCode!=null && aInsuMonitorCode.length()>10)
			throw new IllegalArgumentException("对应保监办代码InsuMonitorCode值"+aInsuMonitorCode+"的长度"+aInsuMonitorCode.length()+"大于最大值10");
		InsuMonitorCode = aInsuMonitorCode;
	}
	public String getInsureID()
	{
		return InsureID;
	}
	public void setInsureID(String aInsureID)
	{
		if(aInsureID!=null && aInsureID.length()>12)
			throw new IllegalArgumentException("保险公司idInsureID值"+aInsureID+"的长度"+aInsureID.length()+"大于最大值12");
		InsureID = aInsureID;
	}
	public String getSignID()
	{
		return SignID;
	}
	public void setSignID(String aSignID)
	{
		if(aSignID!=null && aSignID.length()>6)
			throw new IllegalArgumentException("标识码SignID值"+aSignID+"的长度"+aSignID.length()+"大于最大值6");
		SignID = aSignID;
	}
	public String getRegionalismCode()
	{
		return RegionalismCode;
	}
	public void setRegionalismCode(String aRegionalismCode)
	{
		if(aRegionalismCode!=null && aRegionalismCode.length()>6)
			throw new IllegalArgumentException("行政区划代码RegionalismCode值"+aRegionalismCode+"的长度"+aRegionalismCode.length()+"大于最大值6");
		RegionalismCode = aRegionalismCode;
	}
	public String getComNature()
	{
		return ComNature;
	}
	public void setComNature(String aComNature)
	{
		if(aComNature!=null && aComNature.length()>1)
			throw new IllegalArgumentException("公司性质ComNature值"+aComNature+"的长度"+aComNature.length()+"大于最大值1");
		ComNature = aComNature;
	}
	public String getValidCode()
	{
		return ValidCode;
	}
	public void setValidCode(String aValidCode)
	{
		if(aValidCode!=null && aValidCode.length()>2)
			throw new IllegalArgumentException("校验码ValidCode值"+aValidCode+"的长度"+aValidCode.length()+"大于最大值2");
		ValidCode = aValidCode;
	}
	public String getSign()
	{
		return Sign;
	}
	public void setSign(String aSign)
	{
		if(aSign!=null && aSign.length()>10)
			throw new IllegalArgumentException("标志Sign值"+aSign+"的长度"+aSign.length()+"大于最大值10");
		Sign = aSign;
	}
	/**
	* 该字段暂时用于存储收费员渠道的机构地区类型<p>
	* 1-一类地区<p>
	* 2-二类地区<p>
	* 3-三类地区<p>
	* 4-四类地区
	*/
	public String getComCitySize()
	{
		return ComCitySize;
	}
	public void setComCitySize(String aComCitySize)
	{
		if(aComCitySize!=null && aComCitySize.length()>1)
			throw new IllegalArgumentException("收费员渠道机构地区类型ComCitySize值"+aComCitySize+"的长度"+aComCitySize.length()+"大于最大值1");
		ComCitySize = aComCitySize;
	}
	/**
	* 01-支公司(营销服务部)<p>
	* 02-中心支公司<p>
	* 03-分公司<p>
	* 04-总公司
	*/
	public String getServiceName()
	{
		return ServiceName;
	}
	public void setServiceName(String aServiceName)
	{
		if(aServiceName!=null && aServiceName.length()>120)
			throw new IllegalArgumentException("客户服务机构名称ServiceName值"+aServiceName+"的长度"+aServiceName.length()+"大于最大值120");
		ServiceName = aServiceName;
	}
	/**
	* 01-支公司(营销服务部)<p>
	* 02-中心支公司<p>
	* 03-分公司<p>
	* 04-总公司
	*/
	public String getServiceNo()
	{
		return ServiceNo;
	}
	public void setServiceNo(String aServiceNo)
	{
		if(aServiceNo!=null && aServiceNo.length()>60)
			throw new IllegalArgumentException("客户服务机构编码ServiceNo值"+aServiceNo+"的长度"+aServiceNo.length()+"大于最大值60");
		ServiceNo = aServiceNo;
	}
	/**
	* 01-支公司(营销服务部)<p>
	* 02-中心支公司<p>
	* 03-分公司<p>
	* 04-总公司
	*/
	public String getServicePhone()
	{
		return ServicePhone;
	}
	public void setServicePhone(String aServicePhone)
	{
		if(aServicePhone!=null && aServicePhone.length()>18)
			throw new IllegalArgumentException("客户服务电话ServicePhone值"+aServicePhone+"的长度"+aServicePhone.length()+"大于最大值18");
		ServicePhone = aServicePhone;
	}
	/**
	* 01-支公司(营销服务部)<p>
	* 02-中心支公司<p>
	* 03-分公司<p>
	* 04-总公司
	*/
	public String getServicePostAddress()
	{
		return ServicePostAddress;
	}
	public void setServicePostAddress(String aServicePostAddress)
	{
		if(aServicePostAddress!=null && aServicePostAddress.length()>120)
			throw new IllegalArgumentException("客户服务投递地址ServicePostAddress值"+aServicePostAddress+"的长度"+aServicePostAddress.length()+"大于最大值120");
		ServicePostAddress = aServicePostAddress;
	}
	/**
	* 01-总公司<p>
	* 02-分公司<p>
	* 03-中心支公司<p>
	* 04-支公司(营销服务部)
	*/
	public String getComGrade()
	{
		return ComGrade;
	}
	public void setComGrade(String aComGrade)
	{
		if(aComGrade!=null && aComGrade.length()>2)
			throw new IllegalArgumentException("机构级别ComGrade值"+aComGrade+"的长度"+aComGrade.length()+"大于最大值2");
		ComGrade = aComGrade;
	}
	/**
	* 1:一类地区<p>
	* 2：二类地区
	*/
	public String getComAreaType()
	{
		return ComAreaType;
	}
	public void setComAreaType(String aComAreaType)
	{
		if(aComAreaType!=null && aComAreaType.length()>2)
			throw new IllegalArgumentException("机构地区类型ComAreaType值"+aComAreaType+"的长度"+aComAreaType.length()+"大于最大值2");
		ComAreaType = aComAreaType;
	}
	public String getUpComCode()
	{
		return UpComCode;
	}
	public void setUpComCode(String aUpComCode)
	{
		if(aUpComCode!=null && aUpComCode.length()>10)
			throw new IllegalArgumentException("上级管理机构UpComCode值"+aUpComCode+"的长度"+aUpComCode.length()+"大于最大值10");
		UpComCode = aUpComCode;
	}
	/**
	* 1:直属中支<p>
	* 2：非直属中支（如：营销服务部）
	*/
	public String getIsDirUnder()
	{
		return IsDirUnder;
	}
	public void setIsDirUnder(String aIsDirUnder)
	{
		if(aIsDirUnder!=null && aIsDirUnder.length()>1)
			throw new IllegalArgumentException("直属属性IsDirUnder值"+aIsDirUnder+"的长度"+aIsDirUnder.length()+"大于最大值1");
		IsDirUnder = aIsDirUnder;
	}
	/**
	* 保存直销渠道对应的地区类别
	*/
	public String getComAreaType1()
	{
		return ComAreaType1;
	}
	public void setComAreaType1(String aComAreaType1)
	{
		if(aComAreaType1!=null && aComAreaType1.length()>10)
			throw new IllegalArgumentException("直销地区类别ComAreaType1值"+aComAreaType1+"的长度"+aComAreaType1.length()+"大于最大值10");
		ComAreaType1 = aComAreaType1;
	}
	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String aProvince)
	{
		if(aProvince!=null && aProvince.length()>30)
			throw new IllegalArgumentException("省Province值"+aProvince+"的长度"+aProvince.length()+"大于最大值30");
		Province = aProvince;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String aCity)
	{
		if(aCity!=null && aCity.length()>30)
			throw new IllegalArgumentException("市City值"+aCity+"的长度"+aCity.length()+"大于最大值30");
		City = aCity;
	}
	public String getCounty()
	{
		return County;
	}
	public void setCounty(String aCounty)
	{
		if(aCounty!=null && aCounty.length()>30)
			throw new IllegalArgumentException("区/县County值"+aCounty+"的长度"+aCounty.length()+"大于最大值30");
		County = aCounty;
	}
	/**
	* 主要用于与Sun财务系统对接使用
	*/
	public String getFinDB()
	{
		return FinDB;
	}
	public void setFinDB(String aFinDB)
	{
		if(aFinDB!=null && aFinDB.length()>30)
			throw new IllegalArgumentException("财务dbFinDB值"+aFinDB+"的长度"+aFinDB.length()+"大于最大值30");
		FinDB = aFinDB;
	}

	/**
	* 使用另外一个 LDComSchema 对象给 Schema 赋值
	* @param: aLDComSchema LDComSchema
	**/
	public void setSchema(LDComSchema aLDComSchema)
	{
		this.ComCode = aLDComSchema.getComCode();
		this.OutComCode = aLDComSchema.getOutComCode();
		this.Name = aLDComSchema.getName();
		this.ShortName = aLDComSchema.getShortName();
		this.Address = aLDComSchema.getAddress();
		this.ZipCode = aLDComSchema.getZipCode();
		this.Phone = aLDComSchema.getPhone();
		this.Fax = aLDComSchema.getFax();
		this.EMail = aLDComSchema.getEMail();
		this.WebAddress = aLDComSchema.getWebAddress();
		this.SatrapName = aLDComSchema.getSatrapName();
		this.InsuMonitorCode = aLDComSchema.getInsuMonitorCode();
		this.InsureID = aLDComSchema.getInsureID();
		this.SignID = aLDComSchema.getSignID();
		this.RegionalismCode = aLDComSchema.getRegionalismCode();
		this.ComNature = aLDComSchema.getComNature();
		this.ValidCode = aLDComSchema.getValidCode();
		this.Sign = aLDComSchema.getSign();
		this.ComCitySize = aLDComSchema.getComCitySize();
		this.ServiceName = aLDComSchema.getServiceName();
		this.ServiceNo = aLDComSchema.getServiceNo();
		this.ServicePhone = aLDComSchema.getServicePhone();
		this.ServicePostAddress = aLDComSchema.getServicePostAddress();
		this.ComGrade = aLDComSchema.getComGrade();
		this.ComAreaType = aLDComSchema.getComAreaType();
		this.UpComCode = aLDComSchema.getUpComCode();
		this.IsDirUnder = aLDComSchema.getIsDirUnder();
		this.ComAreaType1 = aLDComSchema.getComAreaType1();
		this.Province = aLDComSchema.getProvince();
		this.City = aLDComSchema.getCity();
		this.County = aLDComSchema.getCounty();
		this.FinDB = aLDComSchema.getFinDB();
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
			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("OutComCode") == null )
				this.OutComCode = null;
			else
				this.OutComCode = rs.getString("OutComCode").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("ShortName") == null )
				this.ShortName = null;
			else
				this.ShortName = rs.getString("ShortName").trim();

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

			if( rs.getString("SatrapName") == null )
				this.SatrapName = null;
			else
				this.SatrapName = rs.getString("SatrapName").trim();

			if( rs.getString("InsuMonitorCode") == null )
				this.InsuMonitorCode = null;
			else
				this.InsuMonitorCode = rs.getString("InsuMonitorCode").trim();

			if( rs.getString("InsureID") == null )
				this.InsureID = null;
			else
				this.InsureID = rs.getString("InsureID").trim();

			if( rs.getString("SignID") == null )
				this.SignID = null;
			else
				this.SignID = rs.getString("SignID").trim();

			if( rs.getString("RegionalismCode") == null )
				this.RegionalismCode = null;
			else
				this.RegionalismCode = rs.getString("RegionalismCode").trim();

			if( rs.getString("ComNature") == null )
				this.ComNature = null;
			else
				this.ComNature = rs.getString("ComNature").trim();

			if( rs.getString("ValidCode") == null )
				this.ValidCode = null;
			else
				this.ValidCode = rs.getString("ValidCode").trim();

			if( rs.getString("Sign") == null )
				this.Sign = null;
			else
				this.Sign = rs.getString("Sign").trim();

			if( rs.getString("ComCitySize") == null )
				this.ComCitySize = null;
			else
				this.ComCitySize = rs.getString("ComCitySize").trim();

			if( rs.getString("ServiceName") == null )
				this.ServiceName = null;
			else
				this.ServiceName = rs.getString("ServiceName").trim();

			if( rs.getString("ServiceNo") == null )
				this.ServiceNo = null;
			else
				this.ServiceNo = rs.getString("ServiceNo").trim();

			if( rs.getString("ServicePhone") == null )
				this.ServicePhone = null;
			else
				this.ServicePhone = rs.getString("ServicePhone").trim();

			if( rs.getString("ServicePostAddress") == null )
				this.ServicePostAddress = null;
			else
				this.ServicePostAddress = rs.getString("ServicePostAddress").trim();

			if( rs.getString("ComGrade") == null )
				this.ComGrade = null;
			else
				this.ComGrade = rs.getString("ComGrade").trim();

			if( rs.getString("ComAreaType") == null )
				this.ComAreaType = null;
			else
				this.ComAreaType = rs.getString("ComAreaType").trim();

			if( rs.getString("UpComCode") == null )
				this.UpComCode = null;
			else
				this.UpComCode = rs.getString("UpComCode").trim();

			if( rs.getString("IsDirUnder") == null )
				this.IsDirUnder = null;
			else
				this.IsDirUnder = rs.getString("IsDirUnder").trim();

			if( rs.getString("ComAreaType1") == null )
				this.ComAreaType1 = null;
			else
				this.ComAreaType1 = rs.getString("ComAreaType1").trim();

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

			if( rs.getString("FinDB") == null )
				this.FinDB = null;
			else
				this.FinDB = rs.getString("FinDB").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCom表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDComSchema getSchema()
	{
		LDComSchema aLDComSchema = new LDComSchema();
		aLDComSchema.setSchema(this);
		return aLDComSchema;
	}

	public LDComDB getDB()
	{
		LDComDB aDBOper = new LDComDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCom描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShortName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WebAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SatrapName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuMonitorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsureID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RegionalismCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sign)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCitySize)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServiceName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServiceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServicePhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServicePostAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComAreaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsDirUnder)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComAreaType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Province)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(City)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(County)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinDB));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCom>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OutComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			WebAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SatrapName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuMonitorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InsureID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			SignID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RegionalismCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ComNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ValidCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Sign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ComCitySize = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ServiceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ServiceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ServicePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ServicePostAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ComGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ComAreaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			UpComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			IsDirUnder = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ComAreaType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			FinDB = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComSchema";
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("OutComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutComCode));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("ShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShortName));
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
		if (FCode.equalsIgnoreCase("SatrapName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SatrapName));
		}
		if (FCode.equalsIgnoreCase("InsuMonitorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuMonitorCode));
		}
		if (FCode.equalsIgnoreCase("InsureID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsureID));
		}
		if (FCode.equalsIgnoreCase("SignID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignID));
		}
		if (FCode.equalsIgnoreCase("RegionalismCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegionalismCode));
		}
		if (FCode.equalsIgnoreCase("ComNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComNature));
		}
		if (FCode.equalsIgnoreCase("ValidCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidCode));
		}
		if (FCode.equalsIgnoreCase("Sign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sign));
		}
		if (FCode.equalsIgnoreCase("ComCitySize"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCitySize));
		}
		if (FCode.equalsIgnoreCase("ServiceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServiceName));
		}
		if (FCode.equalsIgnoreCase("ServiceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServiceNo));
		}
		if (FCode.equalsIgnoreCase("ServicePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServicePhone));
		}
		if (FCode.equalsIgnoreCase("ServicePostAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServicePostAddress));
		}
		if (FCode.equalsIgnoreCase("ComGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComGrade));
		}
		if (FCode.equalsIgnoreCase("ComAreaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComAreaType));
		}
		if (FCode.equalsIgnoreCase("UpComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpComCode));
		}
		if (FCode.equalsIgnoreCase("IsDirUnder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsDirUnder));
		}
		if (FCode.equalsIgnoreCase("ComAreaType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComAreaType1));
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
		if (FCode.equalsIgnoreCase("FinDB"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinDB));
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
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OutComCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ShortName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Address);
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
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(WebAddress);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SatrapName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InsuMonitorCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InsureID);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(SignID);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RegionalismCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ComNature);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ValidCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Sign);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ComCitySize);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ServiceName);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ServiceNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ServicePhone);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ServicePostAddress);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ComGrade);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ComAreaType);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(UpComCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(IsDirUnder);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ComAreaType1);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(County);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(FinDB);
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

		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("OutComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutComCode = FValue.trim();
			}
			else
				OutComCode = null;
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
		if (FCode.equalsIgnoreCase("ShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShortName = FValue.trim();
			}
			else
				ShortName = null;
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
		if (FCode.equalsIgnoreCase("SatrapName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SatrapName = FValue.trim();
			}
			else
				SatrapName = null;
		}
		if (FCode.equalsIgnoreCase("InsuMonitorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuMonitorCode = FValue.trim();
			}
			else
				InsuMonitorCode = null;
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
		if (FCode.equalsIgnoreCase("SignID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignID = FValue.trim();
			}
			else
				SignID = null;
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
		if (FCode.equalsIgnoreCase("ComNature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComNature = FValue.trim();
			}
			else
				ComNature = null;
		}
		if (FCode.equalsIgnoreCase("ValidCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidCode = FValue.trim();
			}
			else
				ValidCode = null;
		}
		if (FCode.equalsIgnoreCase("Sign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sign = FValue.trim();
			}
			else
				Sign = null;
		}
		if (FCode.equalsIgnoreCase("ComCitySize"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCitySize = FValue.trim();
			}
			else
				ComCitySize = null;
		}
		if (FCode.equalsIgnoreCase("ServiceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServiceName = FValue.trim();
			}
			else
				ServiceName = null;
		}
		if (FCode.equalsIgnoreCase("ServiceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServiceNo = FValue.trim();
			}
			else
				ServiceNo = null;
		}
		if (FCode.equalsIgnoreCase("ServicePhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServicePhone = FValue.trim();
			}
			else
				ServicePhone = null;
		}
		if (FCode.equalsIgnoreCase("ServicePostAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServicePostAddress = FValue.trim();
			}
			else
				ServicePostAddress = null;
		}
		if (FCode.equalsIgnoreCase("ComGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComGrade = FValue.trim();
			}
			else
				ComGrade = null;
		}
		if (FCode.equalsIgnoreCase("ComAreaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComAreaType = FValue.trim();
			}
			else
				ComAreaType = null;
		}
		if (FCode.equalsIgnoreCase("UpComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpComCode = FValue.trim();
			}
			else
				UpComCode = null;
		}
		if (FCode.equalsIgnoreCase("IsDirUnder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsDirUnder = FValue.trim();
			}
			else
				IsDirUnder = null;
		}
		if (FCode.equalsIgnoreCase("ComAreaType1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComAreaType1 = FValue.trim();
			}
			else
				ComAreaType1 = null;
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
		if (FCode.equalsIgnoreCase("FinDB"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinDB = FValue.trim();
			}
			else
				FinDB = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDComSchema other = (LDComSchema)otherObject;
		return
			ComCode.equals(other.getComCode())
			&& OutComCode.equals(other.getOutComCode())
			&& Name.equals(other.getName())
			&& ShortName.equals(other.getShortName())
			&& Address.equals(other.getAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& EMail.equals(other.getEMail())
			&& WebAddress.equals(other.getWebAddress())
			&& SatrapName.equals(other.getSatrapName())
			&& InsuMonitorCode.equals(other.getInsuMonitorCode())
			&& InsureID.equals(other.getInsureID())
			&& SignID.equals(other.getSignID())
			&& RegionalismCode.equals(other.getRegionalismCode())
			&& ComNature.equals(other.getComNature())
			&& ValidCode.equals(other.getValidCode())
			&& Sign.equals(other.getSign())
			&& ComCitySize.equals(other.getComCitySize())
			&& ServiceName.equals(other.getServiceName())
			&& ServiceNo.equals(other.getServiceNo())
			&& ServicePhone.equals(other.getServicePhone())
			&& ServicePostAddress.equals(other.getServicePostAddress())
			&& ComGrade.equals(other.getComGrade())
			&& ComAreaType.equals(other.getComAreaType())
			&& UpComCode.equals(other.getUpComCode())
			&& IsDirUnder.equals(other.getIsDirUnder())
			&& ComAreaType1.equals(other.getComAreaType1())
			&& Province.equals(other.getProvince())
			&& City.equals(other.getCity())
			&& County.equals(other.getCounty())
			&& FinDB.equals(other.getFinDB());
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
		if( strFieldName.equals("ComCode") ) {
			return 0;
		}
		if( strFieldName.equals("OutComCode") ) {
			return 1;
		}
		if( strFieldName.equals("Name") ) {
			return 2;
		}
		if( strFieldName.equals("ShortName") ) {
			return 3;
		}
		if( strFieldName.equals("Address") ) {
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
		if( strFieldName.equals("EMail") ) {
			return 8;
		}
		if( strFieldName.equals("WebAddress") ) {
			return 9;
		}
		if( strFieldName.equals("SatrapName") ) {
			return 10;
		}
		if( strFieldName.equals("InsuMonitorCode") ) {
			return 11;
		}
		if( strFieldName.equals("InsureID") ) {
			return 12;
		}
		if( strFieldName.equals("SignID") ) {
			return 13;
		}
		if( strFieldName.equals("RegionalismCode") ) {
			return 14;
		}
		if( strFieldName.equals("ComNature") ) {
			return 15;
		}
		if( strFieldName.equals("ValidCode") ) {
			return 16;
		}
		if( strFieldName.equals("Sign") ) {
			return 17;
		}
		if( strFieldName.equals("ComCitySize") ) {
			return 18;
		}
		if( strFieldName.equals("ServiceName") ) {
			return 19;
		}
		if( strFieldName.equals("ServiceNo") ) {
			return 20;
		}
		if( strFieldName.equals("ServicePhone") ) {
			return 21;
		}
		if( strFieldName.equals("ServicePostAddress") ) {
			return 22;
		}
		if( strFieldName.equals("ComGrade") ) {
			return 23;
		}
		if( strFieldName.equals("ComAreaType") ) {
			return 24;
		}
		if( strFieldName.equals("UpComCode") ) {
			return 25;
		}
		if( strFieldName.equals("IsDirUnder") ) {
			return 26;
		}
		if( strFieldName.equals("ComAreaType1") ) {
			return 27;
		}
		if( strFieldName.equals("Province") ) {
			return 28;
		}
		if( strFieldName.equals("City") ) {
			return 29;
		}
		if( strFieldName.equals("County") ) {
			return 30;
		}
		if( strFieldName.equals("FinDB") ) {
			return 31;
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
				strFieldName = "ComCode";
				break;
			case 1:
				strFieldName = "OutComCode";
				break;
			case 2:
				strFieldName = "Name";
				break;
			case 3:
				strFieldName = "ShortName";
				break;
			case 4:
				strFieldName = "Address";
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
				strFieldName = "EMail";
				break;
			case 9:
				strFieldName = "WebAddress";
				break;
			case 10:
				strFieldName = "SatrapName";
				break;
			case 11:
				strFieldName = "InsuMonitorCode";
				break;
			case 12:
				strFieldName = "InsureID";
				break;
			case 13:
				strFieldName = "SignID";
				break;
			case 14:
				strFieldName = "RegionalismCode";
				break;
			case 15:
				strFieldName = "ComNature";
				break;
			case 16:
				strFieldName = "ValidCode";
				break;
			case 17:
				strFieldName = "Sign";
				break;
			case 18:
				strFieldName = "ComCitySize";
				break;
			case 19:
				strFieldName = "ServiceName";
				break;
			case 20:
				strFieldName = "ServiceNo";
				break;
			case 21:
				strFieldName = "ServicePhone";
				break;
			case 22:
				strFieldName = "ServicePostAddress";
				break;
			case 23:
				strFieldName = "ComGrade";
				break;
			case 24:
				strFieldName = "ComAreaType";
				break;
			case 25:
				strFieldName = "UpComCode";
				break;
			case 26:
				strFieldName = "IsDirUnder";
				break;
			case 27:
				strFieldName = "ComAreaType1";
				break;
			case 28:
				strFieldName = "Province";
				break;
			case 29:
				strFieldName = "City";
				break;
			case 30:
				strFieldName = "County";
				break;
			case 31:
				strFieldName = "FinDB";
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
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShortName") ) {
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
		if( strFieldName.equals("SatrapName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuMonitorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsureID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RegionalismCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sign") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCitySize") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServiceName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServiceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServicePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServicePostAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComAreaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsDirUnder") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComAreaType1") ) {
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
		if( strFieldName.equals("FinDB") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
