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
import com.sinosoft.lis.db.LBAppntGrpDB;

/*
 * <p>ClassName: LBAppntGrpSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LBAppntGrpSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBAppntGrpSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 保单号码 */
	private String PolNo;
	/** 单位编码 */
	private String GrpNo;
	/** 被保人与投保人关系 */
	private String RelationToInsured;
	/** 投保人级别 */
	private String AppntGrade;
	/** 密码 */
	private String Password;
	/** 单位名称 */
	private String GrpName;
	/** 单位地址编码 */
	private String GrpAddressCode;
	/** 单位地址 */
	private String GrpAddress;
	/** 单位邮编 */
	private String GrpZipCode;
	/** 行业分类 */
	private String BusinessType;
	/** 单位性质 */
	private String GrpNature;
	/** 总人数 */
	private int Peoples;
	/** 注册资本 */
	private double RgtMoney;
	/** 资产总额 */
	private double Asset;
	/** 净资产收益率 */
	private double NetProfitRate;
	/** 主营业务 */
	private String MainBussiness;
	/** 法人 */
	private String Corporation;
	/** 机构分布区域 */
	private String ComAera;
	/** 联系人1 */
	private String LinkMan1;
	/** 部门1 */
	private String Department1;
	/** 职务1 */
	private String HeadShip1;
	/** 联系电话1 */
	private String Phone1;
	/** E_mail1 */
	private String E_Mail1;
	/** 传真1 */
	private String Fax1;
	/** 联系人2 */
	private String LinkMan2;
	/** 部门2 */
	private String Department2;
	/** 职务2 */
	private String HeadShip2;
	/** 联系电话2 */
	private String Phone2;
	/** E_mail2 */
	private String E_Mail2;
	/** 传真2 */
	private String Fax2;
	/** 单位传真 */
	private String Fax;
	/** 单位电话 */
	private String Phone;
	/** 付款方式 */
	private String GetFlag;
	/** 负责人 */
	private String Satrap;
	/** 公司e_mail */
	private String EMail;
	/** 成立日期 */
	private Date FoundDate;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行编码 */
	private String BankCode;
	/** 客户组号码 */
	private String GrpGroupNo;
	/** 状态 */
	private String State;
	/** 备注 */
	private String Remark;
	/** 黑名单标记 */
	private String BlacklistFlag;
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

	public static final int FIELDNUM = 48;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBAppntGrpSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PolNo";

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
		LBAppntGrpSchema cloned = (LBAppntGrpSchema)super.clone();
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
		EdorNo = aEdorNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getGrpNo()
	{
		return GrpNo;
	}
	public void setGrpNo(String aGrpNo)
	{
		GrpNo = aGrpNo;
	}
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		RelationToInsured = aRelationToInsured;
	}
	/**
	* 1 ---主投保人<p>
	* 2 ---从头保人
	*/
	public String getAppntGrade()
	{
		return AppntGrade;
	}
	public void setAppntGrade(String aAppntGrade)
	{
		AppntGrade = aAppntGrade;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		GrpName = aGrpName;
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
	public String getGrpZipCode()
	{
		return GrpZipCode;
	}
	public void setGrpZipCode(String aGrpZipCode)
	{
		GrpZipCode = aGrpZipCode;
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
	public int getPeoples()
	{
		return Peoples;
	}
	public void setPeoples(int aPeoples)
	{
		Peoples = aPeoples;
	}
	public void setPeoples(String aPeoples)
	{
		if (aPeoples != null && !aPeoples.equals(""))
		{
			Integer tInteger = new Integer(aPeoples);
			int i = tInteger.intValue();
			Peoples = i;
		}
	}

	public double getRgtMoney()
	{
		return RgtMoney;
	}
	public void setRgtMoney(double aRgtMoney)
	{
		RgtMoney = aRgtMoney;
	}
	public void setRgtMoney(String aRgtMoney)
	{
		if (aRgtMoney != null && !aRgtMoney.equals(""))
		{
			Double tDouble = new Double(aRgtMoney);
			double d = tDouble.doubleValue();
			RgtMoney = d;
		}
	}

	public double getAsset()
	{
		return Asset;
	}
	public void setAsset(double aAsset)
	{
		Asset = aAsset;
	}
	public void setAsset(String aAsset)
	{
		if (aAsset != null && !aAsset.equals(""))
		{
			Double tDouble = new Double(aAsset);
			double d = tDouble.doubleValue();
			Asset = d;
		}
	}

	public double getNetProfitRate()
	{
		return NetProfitRate;
	}
	public void setNetProfitRate(double aNetProfitRate)
	{
		NetProfitRate = aNetProfitRate;
	}
	public void setNetProfitRate(String aNetProfitRate)
	{
		if (aNetProfitRate != null && !aNetProfitRate.equals(""))
		{
			Double tDouble = new Double(aNetProfitRate);
			double d = tDouble.doubleValue();
			NetProfitRate = d;
		}
	}

	public String getMainBussiness()
	{
		return MainBussiness;
	}
	public void setMainBussiness(String aMainBussiness)
	{
		MainBussiness = aMainBussiness;
	}
	public String getCorporation()
	{
		return Corporation;
	}
	public void setCorporation(String aCorporation)
	{
		Corporation = aCorporation;
	}
	public String getComAera()
	{
		return ComAera;
	}
	public void setComAera(String aComAera)
	{
		ComAera = aComAera;
	}
	public String getLinkMan1()
	{
		return LinkMan1;
	}
	public void setLinkMan1(String aLinkMan1)
	{
		LinkMan1 = aLinkMan1;
	}
	public String getDepartment1()
	{
		return Department1;
	}
	public void setDepartment1(String aDepartment1)
	{
		Department1 = aDepartment1;
	}
	public String getHeadShip1()
	{
		return HeadShip1;
	}
	public void setHeadShip1(String aHeadShip1)
	{
		HeadShip1 = aHeadShip1;
	}
	public String getPhone1()
	{
		return Phone1;
	}
	public void setPhone1(String aPhone1)
	{
		Phone1 = aPhone1;
	}
	public String getE_Mail1()
	{
		return E_Mail1;
	}
	public void setE_Mail1(String aE_Mail1)
	{
		E_Mail1 = aE_Mail1;
	}
	public String getFax1()
	{
		return Fax1;
	}
	public void setFax1(String aFax1)
	{
		Fax1 = aFax1;
	}
	public String getLinkMan2()
	{
		return LinkMan2;
	}
	public void setLinkMan2(String aLinkMan2)
	{
		LinkMan2 = aLinkMan2;
	}
	public String getDepartment2()
	{
		return Department2;
	}
	public void setDepartment2(String aDepartment2)
	{
		Department2 = aDepartment2;
	}
	public String getHeadShip2()
	{
		return HeadShip2;
	}
	public void setHeadShip2(String aHeadShip2)
	{
		HeadShip2 = aHeadShip2;
	}
	public String getPhone2()
	{
		return Phone2;
	}
	public void setPhone2(String aPhone2)
	{
		Phone2 = aPhone2;
	}
	public String getE_Mail2()
	{
		return E_Mail2;
	}
	public void setE_Mail2(String aE_Mail2)
	{
		E_Mail2 = aE_Mail2;
	}
	public String getFax2()
	{
		return Fax2;
	}
	public void setFax2(String aFax2)
	{
		Fax2 = aFax2;
	}
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		Fax = aFax;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		GetFlag = aGetFlag;
	}
	public String getSatrap()
	{
		return Satrap;
	}
	public void setSatrap(String aSatrap)
	{
		Satrap = aSatrap;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		EMail = aEMail;
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

	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/**
	* 将团体的客户编组
	*/
	public String getGrpGroupNo()
	{
		return GrpGroupNo;
	}
	public void setGrpGroupNo(String aGrpGroupNo)
	{
		GrpGroupNo = aGrpGroupNo;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	* 0-正常,1-黑名单
	*/
	public String getBlacklistFlag()
	{
		return BlacklistFlag;
	}
	public void setBlacklistFlag(String aBlacklistFlag)
	{
		BlacklistFlag = aBlacklistFlag;
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
	* 使用另外一个 LBAppntGrpSchema 对象给 Schema 赋值
	* @param: aLBAppntGrpSchema LBAppntGrpSchema
	**/
	public void setSchema(LBAppntGrpSchema aLBAppntGrpSchema)
	{
		this.EdorNo = aLBAppntGrpSchema.getEdorNo();
		this.PolNo = aLBAppntGrpSchema.getPolNo();
		this.GrpNo = aLBAppntGrpSchema.getGrpNo();
		this.RelationToInsured = aLBAppntGrpSchema.getRelationToInsured();
		this.AppntGrade = aLBAppntGrpSchema.getAppntGrade();
		this.Password = aLBAppntGrpSchema.getPassword();
		this.GrpName = aLBAppntGrpSchema.getGrpName();
		this.GrpAddressCode = aLBAppntGrpSchema.getGrpAddressCode();
		this.GrpAddress = aLBAppntGrpSchema.getGrpAddress();
		this.GrpZipCode = aLBAppntGrpSchema.getGrpZipCode();
		this.BusinessType = aLBAppntGrpSchema.getBusinessType();
		this.GrpNature = aLBAppntGrpSchema.getGrpNature();
		this.Peoples = aLBAppntGrpSchema.getPeoples();
		this.RgtMoney = aLBAppntGrpSchema.getRgtMoney();
		this.Asset = aLBAppntGrpSchema.getAsset();
		this.NetProfitRate = aLBAppntGrpSchema.getNetProfitRate();
		this.MainBussiness = aLBAppntGrpSchema.getMainBussiness();
		this.Corporation = aLBAppntGrpSchema.getCorporation();
		this.ComAera = aLBAppntGrpSchema.getComAera();
		this.LinkMan1 = aLBAppntGrpSchema.getLinkMan1();
		this.Department1 = aLBAppntGrpSchema.getDepartment1();
		this.HeadShip1 = aLBAppntGrpSchema.getHeadShip1();
		this.Phone1 = aLBAppntGrpSchema.getPhone1();
		this.E_Mail1 = aLBAppntGrpSchema.getE_Mail1();
		this.Fax1 = aLBAppntGrpSchema.getFax1();
		this.LinkMan2 = aLBAppntGrpSchema.getLinkMan2();
		this.Department2 = aLBAppntGrpSchema.getDepartment2();
		this.HeadShip2 = aLBAppntGrpSchema.getHeadShip2();
		this.Phone2 = aLBAppntGrpSchema.getPhone2();
		this.E_Mail2 = aLBAppntGrpSchema.getE_Mail2();
		this.Fax2 = aLBAppntGrpSchema.getFax2();
		this.Fax = aLBAppntGrpSchema.getFax();
		this.Phone = aLBAppntGrpSchema.getPhone();
		this.GetFlag = aLBAppntGrpSchema.getGetFlag();
		this.Satrap = aLBAppntGrpSchema.getSatrap();
		this.EMail = aLBAppntGrpSchema.getEMail();
		this.FoundDate = fDate.getDate( aLBAppntGrpSchema.getFoundDate());
		this.BankAccNo = aLBAppntGrpSchema.getBankAccNo();
		this.BankCode = aLBAppntGrpSchema.getBankCode();
		this.GrpGroupNo = aLBAppntGrpSchema.getGrpGroupNo();
		this.State = aLBAppntGrpSchema.getState();
		this.Remark = aLBAppntGrpSchema.getRemark();
		this.BlacklistFlag = aLBAppntGrpSchema.getBlacklistFlag();
		this.Operator = aLBAppntGrpSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBAppntGrpSchema.getMakeDate());
		this.MakeTime = aLBAppntGrpSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBAppntGrpSchema.getModifyDate());
		this.ModifyTime = aLBAppntGrpSchema.getModifyTime();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("AppntGrade") == null )
				this.AppntGrade = null;
			else
				this.AppntGrade = rs.getString("AppntGrade").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("GrpAddressCode") == null )
				this.GrpAddressCode = null;
			else
				this.GrpAddressCode = rs.getString("GrpAddressCode").trim();

			if( rs.getString("GrpAddress") == null )
				this.GrpAddress = null;
			else
				this.GrpAddress = rs.getString("GrpAddress").trim();

			if( rs.getString("GrpZipCode") == null )
				this.GrpZipCode = null;
			else
				this.GrpZipCode = rs.getString("GrpZipCode").trim();

			if( rs.getString("BusinessType") == null )
				this.BusinessType = null;
			else
				this.BusinessType = rs.getString("BusinessType").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

			this.Peoples = rs.getInt("Peoples");
			this.RgtMoney = rs.getDouble("RgtMoney");
			this.Asset = rs.getDouble("Asset");
			this.NetProfitRate = rs.getDouble("NetProfitRate");
			if( rs.getString("MainBussiness") == null )
				this.MainBussiness = null;
			else
				this.MainBussiness = rs.getString("MainBussiness").trim();

			if( rs.getString("Corporation") == null )
				this.Corporation = null;
			else
				this.Corporation = rs.getString("Corporation").trim();

			if( rs.getString("ComAera") == null )
				this.ComAera = null;
			else
				this.ComAera = rs.getString("ComAera").trim();

			if( rs.getString("LinkMan1") == null )
				this.LinkMan1 = null;
			else
				this.LinkMan1 = rs.getString("LinkMan1").trim();

			if( rs.getString("Department1") == null )
				this.Department1 = null;
			else
				this.Department1 = rs.getString("Department1").trim();

			if( rs.getString("HeadShip1") == null )
				this.HeadShip1 = null;
			else
				this.HeadShip1 = rs.getString("HeadShip1").trim();

			if( rs.getString("Phone1") == null )
				this.Phone1 = null;
			else
				this.Phone1 = rs.getString("Phone1").trim();

			if( rs.getString("E_Mail1") == null )
				this.E_Mail1 = null;
			else
				this.E_Mail1 = rs.getString("E_Mail1").trim();

			if( rs.getString("Fax1") == null )
				this.Fax1 = null;
			else
				this.Fax1 = rs.getString("Fax1").trim();

			if( rs.getString("LinkMan2") == null )
				this.LinkMan2 = null;
			else
				this.LinkMan2 = rs.getString("LinkMan2").trim();

			if( rs.getString("Department2") == null )
				this.Department2 = null;
			else
				this.Department2 = rs.getString("Department2").trim();

			if( rs.getString("HeadShip2") == null )
				this.HeadShip2 = null;
			else
				this.HeadShip2 = rs.getString("HeadShip2").trim();

			if( rs.getString("Phone2") == null )
				this.Phone2 = null;
			else
				this.Phone2 = rs.getString("Phone2").trim();

			if( rs.getString("E_Mail2") == null )
				this.E_Mail2 = null;
			else
				this.E_Mail2 = rs.getString("E_Mail2").trim();

			if( rs.getString("Fax2") == null )
				this.Fax2 = null;
			else
				this.Fax2 = rs.getString("Fax2").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

			if( rs.getString("Satrap") == null )
				this.Satrap = null;
			else
				this.Satrap = rs.getString("Satrap").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			this.FoundDate = rs.getDate("FoundDate");
			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("GrpGroupNo") == null )
				this.GrpGroupNo = null;
			else
				this.GrpGroupNo = rs.getString("GrpGroupNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("BlacklistFlag") == null )
				this.BlacklistFlag = null;
			else
				this.BlacklistFlag = rs.getString("BlacklistFlag").trim();

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
			logger.debug("数据库中的LBAppntGrp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBAppntGrpSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBAppntGrpSchema getSchema()
	{
		LBAppntGrpSchema aLBAppntGrpSchema = new LBAppntGrpSchema();
		aLBAppntGrpSchema.setSchema(this);
		return aLBAppntGrpSchema;
	}

	public LBAppntGrpDB getDB()
	{
		LBAppntGrpDB aDBOper = new LBAppntGrpDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBAppntGrp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RgtMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Asset));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetProfitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBussiness)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Corporation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComAera)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(E_Mail1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkMan2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Department2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadShip2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(E_Mail2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Satrap)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpGroupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlacklistFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBAppntGrp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BusinessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			RgtMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Asset = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			MainBussiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ComAera = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			LinkMan1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Department1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			HeadShip1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Phone1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			E_Mail1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Fax1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			LinkMan2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Department2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			HeadShip2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Phone2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			E_Mail2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Fax2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			GrpGroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			BlacklistFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBAppntGrpSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("AppntGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntGrade));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("GrpAddressCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddressCode));
		}
		if (FCode.equalsIgnoreCase("GrpAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAddress));
		}
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpZipCode));
		}
		if (FCode.equalsIgnoreCase("BusinessType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessType));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
		}
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples));
		}
		if (FCode.equalsIgnoreCase("RgtMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtMoney));
		}
		if (FCode.equalsIgnoreCase("Asset"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Asset));
		}
		if (FCode.equalsIgnoreCase("NetProfitRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetProfitRate));
		}
		if (FCode.equalsIgnoreCase("MainBussiness"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainBussiness));
		}
		if (FCode.equalsIgnoreCase("Corporation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Corporation));
		}
		if (FCode.equalsIgnoreCase("ComAera"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComAera));
		}
		if (FCode.equalsIgnoreCase("LinkMan1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan1));
		}
		if (FCode.equalsIgnoreCase("Department1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department1));
		}
		if (FCode.equalsIgnoreCase("HeadShip1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadShip1));
		}
		if (FCode.equalsIgnoreCase("Phone1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone1));
		}
		if (FCode.equalsIgnoreCase("E_Mail1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(E_Mail1));
		}
		if (FCode.equalsIgnoreCase("Fax1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax1));
		}
		if (FCode.equalsIgnoreCase("LinkMan2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkMan2));
		}
		if (FCode.equalsIgnoreCase("Department2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Department2));
		}
		if (FCode.equalsIgnoreCase("HeadShip2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadShip2));
		}
		if (FCode.equalsIgnoreCase("Phone2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone2));
		}
		if (FCode.equalsIgnoreCase("E_Mail2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(E_Mail2));
		}
		if (FCode.equalsIgnoreCase("Fax2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax2));
		}
		if (FCode.equalsIgnoreCase("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
		}
		if (FCode.equalsIgnoreCase("Satrap"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Satrap));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("GrpGroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpGroupNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("BlacklistFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlacklistFlag));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpAddressCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpAddress);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpZipCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 12:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 13:
				strFieldValue = String.valueOf(RgtMoney);
				break;
			case 14:
				strFieldValue = String.valueOf(Asset);
				break;
			case 15:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MainBussiness);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ComAera);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(LinkMan1);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Department1);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(HeadShip1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Phone1);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(E_Mail1);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Fax1);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(LinkMan2);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Department2);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(HeadShip2);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Phone2);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(E_Mail2);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Fax2);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(GrpGroupNo);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(BlacklistFlag);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 47:
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
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
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equalsIgnoreCase("GrpZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpZipCode = FValue.trim();
			}
			else
				GrpZipCode = null;
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
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RgtMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RgtMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("Asset"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Asset = d;
			}
		}
		if (FCode.equalsIgnoreCase("NetProfitRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NetProfitRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MainBussiness"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainBussiness = FValue.trim();
			}
			else
				MainBussiness = null;
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
		if (FCode.equalsIgnoreCase("ComAera"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComAera = FValue.trim();
			}
			else
				ComAera = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan1 = FValue.trim();
			}
			else
				LinkMan1 = null;
		}
		if (FCode.equalsIgnoreCase("Department1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Department1 = FValue.trim();
			}
			else
				Department1 = null;
		}
		if (FCode.equalsIgnoreCase("HeadShip1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadShip1 = FValue.trim();
			}
			else
				HeadShip1 = null;
		}
		if (FCode.equalsIgnoreCase("Phone1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone1 = FValue.trim();
			}
			else
				Phone1 = null;
		}
		if (FCode.equalsIgnoreCase("E_Mail1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				E_Mail1 = FValue.trim();
			}
			else
				E_Mail1 = null;
		}
		if (FCode.equalsIgnoreCase("Fax1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax1 = FValue.trim();
			}
			else
				Fax1 = null;
		}
		if (FCode.equalsIgnoreCase("LinkMan2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkMan2 = FValue.trim();
			}
			else
				LinkMan2 = null;
		}
		if (FCode.equalsIgnoreCase("Department2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Department2 = FValue.trim();
			}
			else
				Department2 = null;
		}
		if (FCode.equalsIgnoreCase("HeadShip2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadShip2 = FValue.trim();
			}
			else
				HeadShip2 = null;
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
		if (FCode.equalsIgnoreCase("E_Mail2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				E_Mail2 = FValue.trim();
			}
			else
				E_Mail2 = null;
		}
		if (FCode.equalsIgnoreCase("Fax2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax2 = FValue.trim();
			}
			else
				Fax2 = null;
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
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFlag = FValue.trim();
			}
			else
				GetFlag = null;
		}
		if (FCode.equalsIgnoreCase("Satrap"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Satrap = FValue.trim();
			}
			else
				Satrap = null;
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
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FoundDate = fDate.getDate( FValue );
			}
			else
				FoundDate = null;
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("GrpGroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpGroupNo = FValue.trim();
			}
			else
				GrpGroupNo = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		LBAppntGrpSchema other = (LBAppntGrpSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& PolNo.equals(other.getPolNo())
			&& GrpNo.equals(other.getGrpNo())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& AppntGrade.equals(other.getAppntGrade())
			&& Password.equals(other.getPassword())
			&& GrpName.equals(other.getGrpName())
			&& GrpAddressCode.equals(other.getGrpAddressCode())
			&& GrpAddress.equals(other.getGrpAddress())
			&& GrpZipCode.equals(other.getGrpZipCode())
			&& BusinessType.equals(other.getBusinessType())
			&& GrpNature.equals(other.getGrpNature())
			&& Peoples == other.getPeoples()
			&& RgtMoney == other.getRgtMoney()
			&& Asset == other.getAsset()
			&& NetProfitRate == other.getNetProfitRate()
			&& MainBussiness.equals(other.getMainBussiness())
			&& Corporation.equals(other.getCorporation())
			&& ComAera.equals(other.getComAera())
			&& LinkMan1.equals(other.getLinkMan1())
			&& Department1.equals(other.getDepartment1())
			&& HeadShip1.equals(other.getHeadShip1())
			&& Phone1.equals(other.getPhone1())
			&& E_Mail1.equals(other.getE_Mail1())
			&& Fax1.equals(other.getFax1())
			&& LinkMan2.equals(other.getLinkMan2())
			&& Department2.equals(other.getDepartment2())
			&& HeadShip2.equals(other.getHeadShip2())
			&& Phone2.equals(other.getPhone2())
			&& E_Mail2.equals(other.getE_Mail2())
			&& Fax2.equals(other.getFax2())
			&& Fax.equals(other.getFax())
			&& Phone.equals(other.getPhone())
			&& GetFlag.equals(other.getGetFlag())
			&& Satrap.equals(other.getSatrap())
			&& EMail.equals(other.getEMail())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& BankAccNo.equals(other.getBankAccNo())
			&& BankCode.equals(other.getBankCode())
			&& GrpGroupNo.equals(other.getGrpGroupNo())
			&& State.equals(other.getState())
			&& Remark.equals(other.getRemark())
			&& BlacklistFlag.equals(other.getBlacklistFlag())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 2;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 3;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return 4;
		}
		if( strFieldName.equals("Password") ) {
			return 5;
		}
		if( strFieldName.equals("GrpName") ) {
			return 6;
		}
		if( strFieldName.equals("GrpAddressCode") ) {
			return 7;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return 8;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return 9;
		}
		if( strFieldName.equals("BusinessType") ) {
			return 10;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 11;
		}
		if( strFieldName.equals("Peoples") ) {
			return 12;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return 13;
		}
		if( strFieldName.equals("Asset") ) {
			return 14;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 15;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return 16;
		}
		if( strFieldName.equals("Corporation") ) {
			return 17;
		}
		if( strFieldName.equals("ComAera") ) {
			return 18;
		}
		if( strFieldName.equals("LinkMan1") ) {
			return 19;
		}
		if( strFieldName.equals("Department1") ) {
			return 20;
		}
		if( strFieldName.equals("HeadShip1") ) {
			return 21;
		}
		if( strFieldName.equals("Phone1") ) {
			return 22;
		}
		if( strFieldName.equals("E_Mail1") ) {
			return 23;
		}
		if( strFieldName.equals("Fax1") ) {
			return 24;
		}
		if( strFieldName.equals("LinkMan2") ) {
			return 25;
		}
		if( strFieldName.equals("Department2") ) {
			return 26;
		}
		if( strFieldName.equals("HeadShip2") ) {
			return 27;
		}
		if( strFieldName.equals("Phone2") ) {
			return 28;
		}
		if( strFieldName.equals("E_Mail2") ) {
			return 29;
		}
		if( strFieldName.equals("Fax2") ) {
			return 30;
		}
		if( strFieldName.equals("Fax") ) {
			return 31;
		}
		if( strFieldName.equals("Phone") ) {
			return 32;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 33;
		}
		if( strFieldName.equals("Satrap") ) {
			return 34;
		}
		if( strFieldName.equals("EMail") ) {
			return 35;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 36;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 37;
		}
		if( strFieldName.equals("BankCode") ) {
			return 38;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return 39;
		}
		if( strFieldName.equals("State") ) {
			return 40;
		}
		if( strFieldName.equals("Remark") ) {
			return 41;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
			return 42;
		}
		if( strFieldName.equals("Operator") ) {
			return 43;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 44;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 45;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 46;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 47;
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
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "GrpNo";
				break;
			case 3:
				strFieldName = "RelationToInsured";
				break;
			case 4:
				strFieldName = "AppntGrade";
				break;
			case 5:
				strFieldName = "Password";
				break;
			case 6:
				strFieldName = "GrpName";
				break;
			case 7:
				strFieldName = "GrpAddressCode";
				break;
			case 8:
				strFieldName = "GrpAddress";
				break;
			case 9:
				strFieldName = "GrpZipCode";
				break;
			case 10:
				strFieldName = "BusinessType";
				break;
			case 11:
				strFieldName = "GrpNature";
				break;
			case 12:
				strFieldName = "Peoples";
				break;
			case 13:
				strFieldName = "RgtMoney";
				break;
			case 14:
				strFieldName = "Asset";
				break;
			case 15:
				strFieldName = "NetProfitRate";
				break;
			case 16:
				strFieldName = "MainBussiness";
				break;
			case 17:
				strFieldName = "Corporation";
				break;
			case 18:
				strFieldName = "ComAera";
				break;
			case 19:
				strFieldName = "LinkMan1";
				break;
			case 20:
				strFieldName = "Department1";
				break;
			case 21:
				strFieldName = "HeadShip1";
				break;
			case 22:
				strFieldName = "Phone1";
				break;
			case 23:
				strFieldName = "E_Mail1";
				break;
			case 24:
				strFieldName = "Fax1";
				break;
			case 25:
				strFieldName = "LinkMan2";
				break;
			case 26:
				strFieldName = "Department2";
				break;
			case 27:
				strFieldName = "HeadShip2";
				break;
			case 28:
				strFieldName = "Phone2";
				break;
			case 29:
				strFieldName = "E_Mail2";
				break;
			case 30:
				strFieldName = "Fax2";
				break;
			case 31:
				strFieldName = "Fax";
				break;
			case 32:
				strFieldName = "Phone";
				break;
			case 33:
				strFieldName = "GetFlag";
				break;
			case 34:
				strFieldName = "Satrap";
				break;
			case 35:
				strFieldName = "EMail";
				break;
			case 36:
				strFieldName = "FoundDate";
				break;
			case 37:
				strFieldName = "BankAccNo";
				break;
			case 38:
				strFieldName = "BankCode";
				break;
			case 39:
				strFieldName = "GrpGroupNo";
				break;
			case 40:
				strFieldName = "State";
				break;
			case 41:
				strFieldName = "Remark";
				break;
			case 42:
				strFieldName = "BlacklistFlag";
				break;
			case 43:
				strFieldName = "Operator";
				break;
			case 44:
				strFieldName = "MakeDate";
				break;
			case 45:
				strFieldName = "MakeTime";
				break;
			case 46:
				strFieldName = "ModifyDate";
				break;
			case 47:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddressCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Asset") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Corporation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComAera") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadShip1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("E_Mail1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkMan2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Department2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadShip2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("E_Mail2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Satrap") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlacklistFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
