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
import com.sinosoft.lis.db.LBPOEdorInsuredListDB;

/*
 * <p>ClassName: LBPOEdorInsuredListSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LBPOEdorInsuredListSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOEdorInsuredListSchema.class);
	// @Field
	/** 流水号 */
	private int SerialNo;
	/** 保全受理号 */
	private String EdorAppNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 操作节点 */
	private String ActivityID;
	/** 批次号 */
	private String BatchNo;
	/** 顺序号 */
	private int OrderNo;
	/** 保全批单号 */
	private String EdorNo;
	/** 个人保单号 */
	private String ContNo;
	/** 被保险人客户号 */
	private String InsuredNo;
	/** 分组号 */
	private int GroupNo;
	/** 组内顺序号 */
	private int GroupOrderNo;
	/** 变更类型 */
	private String EdorType;
	/** 被保险人id */
	private String InsuredID;
	/** 被保险人类型 */
	private String InsuredType;
	/** 被保险人类型名称 */
	private String InsuredTypeName;
	/** 被保险人人数 */
	private String NumPeople;
	/** 与主被保险人关系 */
	private String RelationToMain;
	/** 与主被保险人关系名称 */
	private String RelationToMainName;
	/** 主被保险人id */
	private String MainInsuredID;
	/** 主被保险人姓名 */
	private String MainInsuredName;
	/** 主被保险人证件号码 */
	private String MainInsuredIDNo;
	/** 原被保险人姓名 */
	private String OldInsuredName;
	/** 原被保险人证件号码 */
	private String OldInsuredIDNo;
	/** 被保险人姓名 */
	private String InsuredName;
	/** 被保险人证件类型 */
	private String InsuredIDType;
	/** 被保险人证件类型名称 */
	private String InsuredIDTypeName;
	/** 被保险人证件号码 */
	private String InsuredIDNo;
	/** 被保险人性别 */
	private String InsuredGender;
	/** 被保险人性别名称 */
	private String InsuredGenderName;
	/** 被保险人出生日期 */
	private String InsuredBirthday;
	/** 生效日期 */
	private String EdorValDate;
	/** 保险方案 */
	private String PlanCode;
	/** 保险方案描述 */
	private String PlanDesc;
	/** 职业代码 */
	private String OccupCode;
	/** 职业名称 */
	private String OccupName;
	/** 开户银行 */
	private String HeadBankCode;
	/** 开户银行名称 */
	private String HeadBankName;
	/** 开户名 */
	private String AccName;
	/** 账号 */
	private String BankAccNo;
	/** 开户行所在省 */
	private String BankProvince;
	/** 开户行所在省名称 */
	private String BankProvinceName;
	/** 开户行所在市 */
	private String BankCity;
	/** 开户行所在市名称 */
	private String BankCityName;
	/** 服务区域 */
	private String ServerArea;
	/** 服务区域名称 */
	private String ServerAreaName;
	/** 是否次标准体 */
	private String Substandard;
	/** 是否次标准体名称 */
	private String SubstandardName;
	/** 社保标记 */
	private String SocialInsuFlag;
	/** 社保标记名称 */
	private String SocialInsuFlagName;
	/** 职级 */
	private String Position;
	/** 职级名称 */
	private String PositionName;
	/** 入司时间 */
	private String JoinCompDate;
	/** 工龄 */
	private String Seniority;
	/** 月薪 */
	private String Salary;
	/** 领取年龄 */
	private String GetYear;
	/** 起领日期计算类型 */
	private String GetStartType;
	/** 起领日期计算类型名称 */
	private String GetStartTypeName;
	/** 转入金额 */
	private String InAmount;
	/** 转出金额 */
	private String OutAmount;
	/** 满期领取方式 */
	private String ExpiryGetMode;
	/** 满期领取方式名称 */
	private String ExpiryGetModeName;
	/** 满期领取支付方式 */
	private String ExpiryPayMode;
	/** 满期领取支付方式名称 */
	private String ExpiryPayModeName;
	/** 年金转换类型 */
	private String TransMode;
	/** 年金转换类型名称 */
	private String TransModeName;
	/** 年金转换金额 */
	private String TransAmount;
	/** 年金领取方式 */
	private String AnnuityGetMode;
	/** 年金领取方式名称 */
	private String AnnuityGetModeName;
	/** 员工号 */
	private String WorkIDNo;
	/** 证件是否长期 */
	private String IsLongValid;
	/** 证件是否长期名称 */
	private String IsLongValidName;
	/** 证件有效期 */
	private String IDEndDate;
	/** 所在分公司 */
	private String SubCompanyCode;
	/** 所在部门 */
	private String DeptCode;
	/** 被保险人编码 */
	private String InsureCode;
	/** 所属客户群 */
	private String SubCustomerNo;
	/** 所属客户群名称 */
	private String SubCustomerName;
	/** 工作地 */
	private String WorkAddress;
	/** 社保地 */
	private String SocialInsuAddress;
	/** 邮政编码 */
	private String ZipCode;
	/** 电子邮箱 */
	private String EMail;
	/** 微信号 */
	private String Wechat;
	/** 联系电话 */
	private String Phone;
	/** 移动电话 */
	private String Mobile;
	/** 联系地址省 */
	private String Province;
	/** 联系地址省名称 */
	private String ProvinceName;
	/** 联系地址市 */
	private String City;
	/** 联系地址市名称 */
	private String CityName;
	/** 联系地址区/县 */
	private String County;
	/** 联系地址区/县名称 */
	private String CountyName;
	/** 详细地址 */
	private String Address;
	/** 来源保单号 */
	private String OriginContNo;
	/** 来源客户号 */
	private String OriginInsuredNo;
	/** 状态 */
	private String State;
	/** 原因 */
	private String Reason;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
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

	public static final int FIELDNUM = 103;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOEdorInsuredListSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LBPOEdorInsuredListSchema cloned = (LBPOEdorInsuredListSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	public String getEdorAppNo()
	{
		return EdorAppNo;
	}
	public void setEdorAppNo(String aEdorAppNo)
	{
		if(aEdorAppNo!=null && aEdorAppNo.length()>20)
			throw new IllegalArgumentException("保全受理号EdorAppNo值"+aEdorAppNo+"的长度"+aEdorAppNo.length()+"大于最大值20");
		EdorAppNo = aEdorAppNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("操作节点ActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		if(aBatchNo!=null && aBatchNo.length()>30)
			throw new IllegalArgumentException("批次号BatchNo值"+aBatchNo+"的长度"+aBatchNo.length()+"大于最大值30");
		BatchNo = aBatchNo;
	}
	public int getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(int aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		if (aOrderNo != null && !aOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aOrderNo);
			int i = tInteger.intValue();
			OrderNo = i;
		}
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("保全批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个人保单号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>20)
			throw new IllegalArgumentException("被保险人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值20");
		InsuredNo = aInsuredNo;
	}
	public int getGroupNo()
	{
		return GroupNo;
	}
	public void setGroupNo(int aGroupNo)
	{
		GroupNo = aGroupNo;
	}
	public void setGroupNo(String aGroupNo)
	{
		if (aGroupNo != null && !aGroupNo.equals(""))
		{
			Integer tInteger = new Integer(aGroupNo);
			int i = tInteger.intValue();
			GroupNo = i;
		}
	}

	public int getGroupOrderNo()
	{
		return GroupOrderNo;
	}
	public void setGroupOrderNo(int aGroupOrderNo)
	{
		GroupOrderNo = aGroupOrderNo;
	}
	public void setGroupOrderNo(String aGroupOrderNo)
	{
		if (aGroupOrderNo != null && !aGroupOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aGroupOrderNo);
			int i = tInteger.intValue();
			GroupOrderNo = i;
		}
	}

	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>50)
			throw new IllegalArgumentException("变更类型EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值50");
		EdorType = aEdorType;
	}
	public String getInsuredID()
	{
		return InsuredID;
	}
	public void setInsuredID(String aInsuredID)
	{
		if(aInsuredID!=null && aInsuredID.length()>10)
			throw new IllegalArgumentException("被保险人idInsuredID值"+aInsuredID+"的长度"+aInsuredID.length()+"大于最大值10");
		InsuredID = aInsuredID;
	}
	public String getInsuredType()
	{
		return InsuredType;
	}
	public void setInsuredType(String aInsuredType)
	{
		if(aInsuredType!=null && aInsuredType.length()>1)
			throw new IllegalArgumentException("被保险人类型InsuredType值"+aInsuredType+"的长度"+aInsuredType.length()+"大于最大值1");
		InsuredType = aInsuredType;
	}
	public String getInsuredTypeName()
	{
		return InsuredTypeName;
	}
	public void setInsuredTypeName(String aInsuredTypeName)
	{
		if(aInsuredTypeName!=null && aInsuredTypeName.length()>30)
			throw new IllegalArgumentException("被保险人类型名称InsuredTypeName值"+aInsuredTypeName+"的长度"+aInsuredTypeName.length()+"大于最大值30");
		InsuredTypeName = aInsuredTypeName;
	}
	public String getNumPeople()
	{
		return NumPeople;
	}
	public void setNumPeople(String aNumPeople)
	{
		if(aNumPeople!=null && aNumPeople.length()>10)
			throw new IllegalArgumentException("被保险人人数NumPeople值"+aNumPeople+"的长度"+aNumPeople.length()+"大于最大值10");
		NumPeople = aNumPeople;
	}
	public String getRelationToMain()
	{
		return RelationToMain;
	}
	public void setRelationToMain(String aRelationToMain)
	{
		if(aRelationToMain!=null && aRelationToMain.length()>2)
			throw new IllegalArgumentException("与主被保险人关系RelationToMain值"+aRelationToMain+"的长度"+aRelationToMain.length()+"大于最大值2");
		RelationToMain = aRelationToMain;
	}
	public String getRelationToMainName()
	{
		return RelationToMainName;
	}
	public void setRelationToMainName(String aRelationToMainName)
	{
		if(aRelationToMainName!=null && aRelationToMainName.length()>30)
			throw new IllegalArgumentException("与主被保险人关系名称RelationToMainName值"+aRelationToMainName+"的长度"+aRelationToMainName.length()+"大于最大值30");
		RelationToMainName = aRelationToMainName;
	}
	public String getMainInsuredID()
	{
		return MainInsuredID;
	}
	public void setMainInsuredID(String aMainInsuredID)
	{
		if(aMainInsuredID!=null && aMainInsuredID.length()>10)
			throw new IllegalArgumentException("主被保险人idMainInsuredID值"+aMainInsuredID+"的长度"+aMainInsuredID.length()+"大于最大值10");
		MainInsuredID = aMainInsuredID;
	}
	public String getMainInsuredName()
	{
		return MainInsuredName;
	}
	public void setMainInsuredName(String aMainInsuredName)
	{
		if(aMainInsuredName!=null && aMainInsuredName.length()>200)
			throw new IllegalArgumentException("主被保险人姓名MainInsuredName值"+aMainInsuredName+"的长度"+aMainInsuredName.length()+"大于最大值200");
		MainInsuredName = aMainInsuredName;
	}
	public String getMainInsuredIDNo()
	{
		return MainInsuredIDNo;
	}
	public void setMainInsuredIDNo(String aMainInsuredIDNo)
	{
		if(aMainInsuredIDNo!=null && aMainInsuredIDNo.length()>30)
			throw new IllegalArgumentException("主被保险人证件号码MainInsuredIDNo值"+aMainInsuredIDNo+"的长度"+aMainInsuredIDNo.length()+"大于最大值30");
		MainInsuredIDNo = aMainInsuredIDNo;
	}
	public String getOldInsuredName()
	{
		return OldInsuredName;
	}
	public void setOldInsuredName(String aOldInsuredName)
	{
		if(aOldInsuredName!=null && aOldInsuredName.length()>200)
			throw new IllegalArgumentException("原被保险人姓名OldInsuredName值"+aOldInsuredName+"的长度"+aOldInsuredName.length()+"大于最大值200");
		OldInsuredName = aOldInsuredName;
	}
	public String getOldInsuredIDNo()
	{
		return OldInsuredIDNo;
	}
	public void setOldInsuredIDNo(String aOldInsuredIDNo)
	{
		if(aOldInsuredIDNo!=null && aOldInsuredIDNo.length()>30)
			throw new IllegalArgumentException("原被保险人证件号码OldInsuredIDNo值"+aOldInsuredIDNo+"的长度"+aOldInsuredIDNo.length()+"大于最大值30");
		OldInsuredIDNo = aOldInsuredIDNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		if(aInsuredName!=null && aInsuredName.length()>300)
			throw new IllegalArgumentException("被保险人姓名InsuredName值"+aInsuredName+"的长度"+aInsuredName.length()+"大于最大值300");
		InsuredName = aInsuredName;
	}
	public String getInsuredIDType()
	{
		return InsuredIDType;
	}
	public void setInsuredIDType(String aInsuredIDType)
	{
		if(aInsuredIDType!=null && aInsuredIDType.length()>2)
			throw new IllegalArgumentException("被保险人证件类型InsuredIDType值"+aInsuredIDType+"的长度"+aInsuredIDType.length()+"大于最大值2");
		InsuredIDType = aInsuredIDType;
	}
	public String getInsuredIDTypeName()
	{
		return InsuredIDTypeName;
	}
	public void setInsuredIDTypeName(String aInsuredIDTypeName)
	{
		if(aInsuredIDTypeName!=null && aInsuredIDTypeName.length()>30)
			throw new IllegalArgumentException("被保险人证件类型名称InsuredIDTypeName值"+aInsuredIDTypeName+"的长度"+aInsuredIDTypeName.length()+"大于最大值30");
		InsuredIDTypeName = aInsuredIDTypeName;
	}
	public String getInsuredIDNo()
	{
		return InsuredIDNo;
	}
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		if(aInsuredIDNo!=null && aInsuredIDNo.length()>30)
			throw new IllegalArgumentException("被保险人证件号码InsuredIDNo值"+aInsuredIDNo+"的长度"+aInsuredIDNo.length()+"大于最大值30");
		InsuredIDNo = aInsuredIDNo;
	}
	public String getInsuredGender()
	{
		return InsuredGender;
	}
	public void setInsuredGender(String aInsuredGender)
	{
		if(aInsuredGender!=null && aInsuredGender.length()>1)
			throw new IllegalArgumentException("被保险人性别InsuredGender值"+aInsuredGender+"的长度"+aInsuredGender.length()+"大于最大值1");
		InsuredGender = aInsuredGender;
	}
	public String getInsuredGenderName()
	{
		return InsuredGenderName;
	}
	public void setInsuredGenderName(String aInsuredGenderName)
	{
		if(aInsuredGenderName!=null && aInsuredGenderName.length()>30)
			throw new IllegalArgumentException("被保险人性别名称InsuredGenderName值"+aInsuredGenderName+"的长度"+aInsuredGenderName.length()+"大于最大值30");
		InsuredGenderName = aInsuredGenderName;
	}
	public String getInsuredBirthday()
	{
		return InsuredBirthday;
	}
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if(aInsuredBirthday!=null && aInsuredBirthday.length()>30)
			throw new IllegalArgumentException("被保险人出生日期InsuredBirthday值"+aInsuredBirthday+"的长度"+aInsuredBirthday.length()+"大于最大值30");
		InsuredBirthday = aInsuredBirthday;
	}
	public String getEdorValDate()
	{
		return EdorValDate;
	}
	public void setEdorValDate(String aEdorValDate)
	{
		if(aEdorValDate!=null && aEdorValDate.length()>30)
			throw new IllegalArgumentException("生效日期EdorValDate值"+aEdorValDate+"的长度"+aEdorValDate.length()+"大于最大值30");
		EdorValDate = aEdorValDate;
	}
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>20)
			throw new IllegalArgumentException("保险方案PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值20");
		PlanCode = aPlanCode;
	}
	public String getPlanDesc()
	{
		return PlanDesc;
	}
	public void setPlanDesc(String aPlanDesc)
	{
		if(aPlanDesc!=null && aPlanDesc.length()>100)
			throw new IllegalArgumentException("保险方案描述PlanDesc值"+aPlanDesc+"的长度"+aPlanDesc.length()+"大于最大值100");
		PlanDesc = aPlanDesc;
	}
	public String getOccupCode()
	{
		return OccupCode;
	}
	public void setOccupCode(String aOccupCode)
	{
		if(aOccupCode!=null && aOccupCode.length()>10)
			throw new IllegalArgumentException("职业代码OccupCode值"+aOccupCode+"的长度"+aOccupCode.length()+"大于最大值10");
		OccupCode = aOccupCode;
	}
	public String getOccupName()
	{
		return OccupName;
	}
	public void setOccupName(String aOccupName)
	{
		if(aOccupName!=null && aOccupName.length()>100)
			throw new IllegalArgumentException("职业名称OccupName值"+aOccupName+"的长度"+aOccupName.length()+"大于最大值100");
		OccupName = aOccupName;
	}
	public String getHeadBankCode()
	{
		return HeadBankCode;
	}
	public void setHeadBankCode(String aHeadBankCode)
	{
		if(aHeadBankCode!=null && aHeadBankCode.length()>30)
			throw new IllegalArgumentException("开户银行HeadBankCode值"+aHeadBankCode+"的长度"+aHeadBankCode.length()+"大于最大值30");
		HeadBankCode = aHeadBankCode;
	}
	public String getHeadBankName()
	{
		return HeadBankName;
	}
	public void setHeadBankName(String aHeadBankName)
	{
		if(aHeadBankName!=null && aHeadBankName.length()>300)
			throw new IllegalArgumentException("开户银行名称HeadBankName值"+aHeadBankName+"的长度"+aHeadBankName.length()+"大于最大值300");
		HeadBankName = aHeadBankName;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>300)
			throw new IllegalArgumentException("开户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值300");
		AccName = aAccName;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		if(aBankAccNo!=null && aBankAccNo.length()>30)
			throw new IllegalArgumentException("账号BankAccNo值"+aBankAccNo+"的长度"+aBankAccNo.length()+"大于最大值30");
		BankAccNo = aBankAccNo;
	}
	public String getBankProvince()
	{
		return BankProvince;
	}
	public void setBankProvince(String aBankProvince)
	{
		if(aBankProvince!=null && aBankProvince.length()>6)
			throw new IllegalArgumentException("开户行所在省BankProvince值"+aBankProvince+"的长度"+aBankProvince.length()+"大于最大值6");
		BankProvince = aBankProvince;
	}
	public String getBankProvinceName()
	{
		return BankProvinceName;
	}
	public void setBankProvinceName(String aBankProvinceName)
	{
		if(aBankProvinceName!=null && aBankProvinceName.length()>30)
			throw new IllegalArgumentException("开户行所在省名称BankProvinceName值"+aBankProvinceName+"的长度"+aBankProvinceName.length()+"大于最大值30");
		BankProvinceName = aBankProvinceName;
	}
	public String getBankCity()
	{
		return BankCity;
	}
	public void setBankCity(String aBankCity)
	{
		if(aBankCity!=null && aBankCity.length()>6)
			throw new IllegalArgumentException("开户行所在市BankCity值"+aBankCity+"的长度"+aBankCity.length()+"大于最大值6");
		BankCity = aBankCity;
	}
	public String getBankCityName()
	{
		return BankCityName;
	}
	public void setBankCityName(String aBankCityName)
	{
		if(aBankCityName!=null && aBankCityName.length()>50)
			throw new IllegalArgumentException("开户行所在市名称BankCityName值"+aBankCityName+"的长度"+aBankCityName.length()+"大于最大值50");
		BankCityName = aBankCityName;
	}
	public String getServerArea()
	{
		return ServerArea;
	}
	public void setServerArea(String aServerArea)
	{
		if(aServerArea!=null && aServerArea.length()>2)
			throw new IllegalArgumentException("服务区域ServerArea值"+aServerArea+"的长度"+aServerArea.length()+"大于最大值2");
		ServerArea = aServerArea;
	}
	public String getServerAreaName()
	{
		return ServerAreaName;
	}
	public void setServerAreaName(String aServerAreaName)
	{
		if(aServerAreaName!=null && aServerAreaName.length()>50)
			throw new IllegalArgumentException("服务区域名称ServerAreaName值"+aServerAreaName+"的长度"+aServerAreaName.length()+"大于最大值50");
		ServerAreaName = aServerAreaName;
	}
	public String getSubstandard()
	{
		return Substandard;
	}
	public void setSubstandard(String aSubstandard)
	{
		if(aSubstandard!=null && aSubstandard.length()>2)
			throw new IllegalArgumentException("是否次标准体Substandard值"+aSubstandard+"的长度"+aSubstandard.length()+"大于最大值2");
		Substandard = aSubstandard;
	}
	public String getSubstandardName()
	{
		return SubstandardName;
	}
	public void setSubstandardName(String aSubstandardName)
	{
		if(aSubstandardName!=null && aSubstandardName.length()>20)
			throw new IllegalArgumentException("是否次标准体名称SubstandardName值"+aSubstandardName+"的长度"+aSubstandardName.length()+"大于最大值20");
		SubstandardName = aSubstandardName;
	}
	public String getSocialInsuFlag()
	{
		return SocialInsuFlag;
	}
	public void setSocialInsuFlag(String aSocialInsuFlag)
	{
		if(aSocialInsuFlag!=null && aSocialInsuFlag.length()>2)
			throw new IllegalArgumentException("社保标记SocialInsuFlag值"+aSocialInsuFlag+"的长度"+aSocialInsuFlag.length()+"大于最大值2");
		SocialInsuFlag = aSocialInsuFlag;
	}
	public String getSocialInsuFlagName()
	{
		return SocialInsuFlagName;
	}
	public void setSocialInsuFlagName(String aSocialInsuFlagName)
	{
		if(aSocialInsuFlagName!=null && aSocialInsuFlagName.length()>20)
			throw new IllegalArgumentException("社保标记名称SocialInsuFlagName值"+aSocialInsuFlagName+"的长度"+aSocialInsuFlagName.length()+"大于最大值20");
		SocialInsuFlagName = aSocialInsuFlagName;
	}
	public String getPosition()
	{
		return Position;
	}
	public void setPosition(String aPosition)
	{
		if(aPosition!=null && aPosition.length()>20)
			throw new IllegalArgumentException("职级Position值"+aPosition+"的长度"+aPosition.length()+"大于最大值20");
		Position = aPosition;
	}
	public String getPositionName()
	{
		return PositionName;
	}
	public void setPositionName(String aPositionName)
	{
		if(aPositionName!=null && aPositionName.length()>30)
			throw new IllegalArgumentException("职级名称PositionName值"+aPositionName+"的长度"+aPositionName.length()+"大于最大值30");
		PositionName = aPositionName;
	}
	public String getJoinCompDate()
	{
		return JoinCompDate;
	}
	public void setJoinCompDate(String aJoinCompDate)
	{
		if(aJoinCompDate!=null && aJoinCompDate.length()>30)
			throw new IllegalArgumentException("入司时间JoinCompDate值"+aJoinCompDate+"的长度"+aJoinCompDate.length()+"大于最大值30");
		JoinCompDate = aJoinCompDate;
	}
	public String getSeniority()
	{
		return Seniority;
	}
	public void setSeniority(String aSeniority)
	{
		if(aSeniority!=null && aSeniority.length()>10)
			throw new IllegalArgumentException("工龄Seniority值"+aSeniority+"的长度"+aSeniority.length()+"大于最大值10");
		Seniority = aSeniority;
	}
	public String getSalary()
	{
		return Salary;
	}
	public void setSalary(String aSalary)
	{
		if(aSalary!=null && aSalary.length()>10)
			throw new IllegalArgumentException("月薪Salary值"+aSalary+"的长度"+aSalary.length()+"大于最大值10");
		Salary = aSalary;
	}
	public String getGetYear()
	{
		return GetYear;
	}
	public void setGetYear(String aGetYear)
	{
		if(aGetYear!=null && aGetYear.length()>10)
			throw new IllegalArgumentException("领取年龄GetYear值"+aGetYear+"的长度"+aGetYear.length()+"大于最大值10");
		GetYear = aGetYear;
	}
	public String getGetStartType()
	{
		return GetStartType;
	}
	public void setGetStartType(String aGetStartType)
	{
		if(aGetStartType!=null && aGetStartType.length()>2)
			throw new IllegalArgumentException("起领日期计算类型GetStartType值"+aGetStartType+"的长度"+aGetStartType.length()+"大于最大值2");
		GetStartType = aGetStartType;
	}
	public String getGetStartTypeName()
	{
		return GetStartTypeName;
	}
	public void setGetStartTypeName(String aGetStartTypeName)
	{
		if(aGetStartTypeName!=null && aGetStartTypeName.length()>30)
			throw new IllegalArgumentException("起领日期计算类型名称GetStartTypeName值"+aGetStartTypeName+"的长度"+aGetStartTypeName.length()+"大于最大值30");
		GetStartTypeName = aGetStartTypeName;
	}
	public String getInAmount()
	{
		return InAmount;
	}
	public void setInAmount(String aInAmount)
	{
		if(aInAmount!=null && aInAmount.length()>20)
			throw new IllegalArgumentException("转入金额InAmount值"+aInAmount+"的长度"+aInAmount.length()+"大于最大值20");
		InAmount = aInAmount;
	}
	public String getOutAmount()
	{
		return OutAmount;
	}
	public void setOutAmount(String aOutAmount)
	{
		if(aOutAmount!=null && aOutAmount.length()>20)
			throw new IllegalArgumentException("转出金额OutAmount值"+aOutAmount+"的长度"+aOutAmount.length()+"大于最大值20");
		OutAmount = aOutAmount;
	}
	public String getExpiryGetMode()
	{
		return ExpiryGetMode;
	}
	public void setExpiryGetMode(String aExpiryGetMode)
	{
		if(aExpiryGetMode!=null && aExpiryGetMode.length()>2)
			throw new IllegalArgumentException("满期领取方式ExpiryGetMode值"+aExpiryGetMode+"的长度"+aExpiryGetMode.length()+"大于最大值2");
		ExpiryGetMode = aExpiryGetMode;
	}
	public String getExpiryGetModeName()
	{
		return ExpiryGetModeName;
	}
	public void setExpiryGetModeName(String aExpiryGetModeName)
	{
		if(aExpiryGetModeName!=null && aExpiryGetModeName.length()>30)
			throw new IllegalArgumentException("满期领取方式名称ExpiryGetModeName值"+aExpiryGetModeName+"的长度"+aExpiryGetModeName.length()+"大于最大值30");
		ExpiryGetModeName = aExpiryGetModeName;
	}
	public String getExpiryPayMode()
	{
		return ExpiryPayMode;
	}
	public void setExpiryPayMode(String aExpiryPayMode)
	{
		if(aExpiryPayMode!=null && aExpiryPayMode.length()>2)
			throw new IllegalArgumentException("满期领取支付方式ExpiryPayMode值"+aExpiryPayMode+"的长度"+aExpiryPayMode.length()+"大于最大值2");
		ExpiryPayMode = aExpiryPayMode;
	}
	public String getExpiryPayModeName()
	{
		return ExpiryPayModeName;
	}
	public void setExpiryPayModeName(String aExpiryPayModeName)
	{
		if(aExpiryPayModeName!=null && aExpiryPayModeName.length()>30)
			throw new IllegalArgumentException("满期领取支付方式名称ExpiryPayModeName值"+aExpiryPayModeName+"的长度"+aExpiryPayModeName.length()+"大于最大值30");
		ExpiryPayModeName = aExpiryPayModeName;
	}
	public String getTransMode()
	{
		return TransMode;
	}
	public void setTransMode(String aTransMode)
	{
		if(aTransMode!=null && aTransMode.length()>2)
			throw new IllegalArgumentException("年金转换类型TransMode值"+aTransMode+"的长度"+aTransMode.length()+"大于最大值2");
		TransMode = aTransMode;
	}
	public String getTransModeName()
	{
		return TransModeName;
	}
	public void setTransModeName(String aTransModeName)
	{
		if(aTransModeName!=null && aTransModeName.length()>30)
			throw new IllegalArgumentException("年金转换类型名称TransModeName值"+aTransModeName+"的长度"+aTransModeName.length()+"大于最大值30");
		TransModeName = aTransModeName;
	}
	public String getTransAmount()
	{
		return TransAmount;
	}
	public void setTransAmount(String aTransAmount)
	{
		if(aTransAmount!=null && aTransAmount.length()>20)
			throw new IllegalArgumentException("年金转换金额TransAmount值"+aTransAmount+"的长度"+aTransAmount.length()+"大于最大值20");
		TransAmount = aTransAmount;
	}
	public String getAnnuityGetMode()
	{
		return AnnuityGetMode;
	}
	public void setAnnuityGetMode(String aAnnuityGetMode)
	{
		if(aAnnuityGetMode!=null && aAnnuityGetMode.length()>10)
			throw new IllegalArgumentException("年金领取方式AnnuityGetMode值"+aAnnuityGetMode+"的长度"+aAnnuityGetMode.length()+"大于最大值10");
		AnnuityGetMode = aAnnuityGetMode;
	}
	public String getAnnuityGetModeName()
	{
		return AnnuityGetModeName;
	}
	public void setAnnuityGetModeName(String aAnnuityGetModeName)
	{
		if(aAnnuityGetModeName!=null && aAnnuityGetModeName.length()>30)
			throw new IllegalArgumentException("年金领取方式名称AnnuityGetModeName值"+aAnnuityGetModeName+"的长度"+aAnnuityGetModeName.length()+"大于最大值30");
		AnnuityGetModeName = aAnnuityGetModeName;
	}
	public String getWorkIDNo()
	{
		return WorkIDNo;
	}
	public void setWorkIDNo(String aWorkIDNo)
	{
		if(aWorkIDNo!=null && aWorkIDNo.length()>20)
			throw new IllegalArgumentException("员工号WorkIDNo值"+aWorkIDNo+"的长度"+aWorkIDNo.length()+"大于最大值20");
		WorkIDNo = aWorkIDNo;
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
	public String getIsLongValidName()
	{
		return IsLongValidName;
	}
	public void setIsLongValidName(String aIsLongValidName)
	{
		if(aIsLongValidName!=null && aIsLongValidName.length()>20)
			throw new IllegalArgumentException("证件是否长期名称IsLongValidName值"+aIsLongValidName+"的长度"+aIsLongValidName.length()+"大于最大值20");
		IsLongValidName = aIsLongValidName;
	}
	public String getIDEndDate()
	{
		return IDEndDate;
	}
	public void setIDEndDate(String aIDEndDate)
	{
		if(aIDEndDate!=null && aIDEndDate.length()>30)
			throw new IllegalArgumentException("证件有效期IDEndDate值"+aIDEndDate+"的长度"+aIDEndDate.length()+"大于最大值30");
		IDEndDate = aIDEndDate;
	}
	public String getSubCompanyCode()
	{
		return SubCompanyCode;
	}
	public void setSubCompanyCode(String aSubCompanyCode)
	{
		if(aSubCompanyCode!=null && aSubCompanyCode.length()>20)
			throw new IllegalArgumentException("所在分公司SubCompanyCode值"+aSubCompanyCode+"的长度"+aSubCompanyCode.length()+"大于最大值20");
		SubCompanyCode = aSubCompanyCode;
	}
	public String getDeptCode()
	{
		return DeptCode;
	}
	public void setDeptCode(String aDeptCode)
	{
		if(aDeptCode!=null && aDeptCode.length()>100)
			throw new IllegalArgumentException("所在部门DeptCode值"+aDeptCode+"的长度"+aDeptCode.length()+"大于最大值100");
		DeptCode = aDeptCode;
	}
	public String getInsureCode()
	{
		return InsureCode;
	}
	public void setInsureCode(String aInsureCode)
	{
		if(aInsureCode!=null && aInsureCode.length()>20)
			throw new IllegalArgumentException("被保险人编码InsureCode值"+aInsureCode+"的长度"+aInsureCode.length()+"大于最大值20");
		InsureCode = aInsureCode;
	}
	public String getSubCustomerNo()
	{
		return SubCustomerNo;
	}
	public void setSubCustomerNo(String aSubCustomerNo)
	{
		if(aSubCustomerNo!=null && aSubCustomerNo.length()>20)
			throw new IllegalArgumentException("所属客户群SubCustomerNo值"+aSubCustomerNo+"的长度"+aSubCustomerNo.length()+"大于最大值20");
		SubCustomerNo = aSubCustomerNo;
	}
	public String getSubCustomerName()
	{
		return SubCustomerName;
	}
	public void setSubCustomerName(String aSubCustomerName)
	{
		if(aSubCustomerName!=null && aSubCustomerName.length()>100)
			throw new IllegalArgumentException("所属客户群名称SubCustomerName值"+aSubCustomerName+"的长度"+aSubCustomerName.length()+"大于最大值100");
		SubCustomerName = aSubCustomerName;
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
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		if(aZipCode!=null && aZipCode.length()>10)
			throw new IllegalArgumentException("邮政编码ZipCode值"+aZipCode+"的长度"+aZipCode.length()+"大于最大值10");
		ZipCode = aZipCode;
	}
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>100)
			throw new IllegalArgumentException("电子邮箱EMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值100");
		EMail = aEMail;
	}
	public String getWechat()
	{
		return Wechat;
	}
	public void setWechat(String aWechat)
	{
		if(aWechat!=null && aWechat.length()>60)
			throw new IllegalArgumentException("微信号Wechat值"+aWechat+"的长度"+aWechat.length()+"大于最大值60");
		Wechat = aWechat;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>30)
			throw new IllegalArgumentException("联系电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值30");
		Phone = aPhone;
	}
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		if(aMobile!=null && aMobile.length()>20)
			throw new IllegalArgumentException("移动电话Mobile值"+aMobile+"的长度"+aMobile.length()+"大于最大值20");
		Mobile = aMobile;
	}
	public String getProvince()
	{
		return Province;
	}
	public void setProvince(String aProvince)
	{
		if(aProvince!=null && aProvince.length()>6)
			throw new IllegalArgumentException("联系地址省Province值"+aProvince+"的长度"+aProvince.length()+"大于最大值6");
		Province = aProvince;
	}
	public String getProvinceName()
	{
		return ProvinceName;
	}
	public void setProvinceName(String aProvinceName)
	{
		if(aProvinceName!=null && aProvinceName.length()>30)
			throw new IllegalArgumentException("联系地址省名称ProvinceName值"+aProvinceName+"的长度"+aProvinceName.length()+"大于最大值30");
		ProvinceName = aProvinceName;
	}
	public String getCity()
	{
		return City;
	}
	public void setCity(String aCity)
	{
		if(aCity!=null && aCity.length()>6)
			throw new IllegalArgumentException("联系地址市City值"+aCity+"的长度"+aCity.length()+"大于最大值6");
		City = aCity;
	}
	public String getCityName()
	{
		return CityName;
	}
	public void setCityName(String aCityName)
	{
		if(aCityName!=null && aCityName.length()>50)
			throw new IllegalArgumentException("联系地址市名称CityName值"+aCityName+"的长度"+aCityName.length()+"大于最大值50");
		CityName = aCityName;
	}
	public String getCounty()
	{
		return County;
	}
	public void setCounty(String aCounty)
	{
		if(aCounty!=null && aCounty.length()>6)
			throw new IllegalArgumentException("联系地址区/县County值"+aCounty+"的长度"+aCounty.length()+"大于最大值6");
		County = aCounty;
	}
	public String getCountyName()
	{
		return CountyName;
	}
	public void setCountyName(String aCountyName)
	{
		if(aCountyName!=null && aCountyName.length()>50)
			throw new IllegalArgumentException("联系地址区/县名称CountyName值"+aCountyName+"的长度"+aCountyName.length()+"大于最大值50");
		CountyName = aCountyName;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String aAddress)
	{
		if(aAddress!=null && aAddress.length()>300)
			throw new IllegalArgumentException("详细地址Address值"+aAddress+"的长度"+aAddress.length()+"大于最大值300");
		Address = aAddress;
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
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>3000)
			throw new IllegalArgumentException("原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值3000");
		Reason = aReason;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
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
	* 使用另外一个 LBPOEdorInsuredListSchema 对象给 Schema 赋值
	* @param: aLBPOEdorInsuredListSchema LBPOEdorInsuredListSchema
	**/
	public void setSchema(LBPOEdorInsuredListSchema aLBPOEdorInsuredListSchema)
	{
		this.SerialNo = aLBPOEdorInsuredListSchema.getSerialNo();
		this.EdorAppNo = aLBPOEdorInsuredListSchema.getEdorAppNo();
		this.GrpContNo = aLBPOEdorInsuredListSchema.getGrpContNo();
		this.ActivityID = aLBPOEdorInsuredListSchema.getActivityID();
		this.BatchNo = aLBPOEdorInsuredListSchema.getBatchNo();
		this.OrderNo = aLBPOEdorInsuredListSchema.getOrderNo();
		this.EdorNo = aLBPOEdorInsuredListSchema.getEdorNo();
		this.ContNo = aLBPOEdorInsuredListSchema.getContNo();
		this.InsuredNo = aLBPOEdorInsuredListSchema.getInsuredNo();
		this.GroupNo = aLBPOEdorInsuredListSchema.getGroupNo();
		this.GroupOrderNo = aLBPOEdorInsuredListSchema.getGroupOrderNo();
		this.EdorType = aLBPOEdorInsuredListSchema.getEdorType();
		this.InsuredID = aLBPOEdorInsuredListSchema.getInsuredID();
		this.InsuredType = aLBPOEdorInsuredListSchema.getInsuredType();
		this.InsuredTypeName = aLBPOEdorInsuredListSchema.getInsuredTypeName();
		this.NumPeople = aLBPOEdorInsuredListSchema.getNumPeople();
		this.RelationToMain = aLBPOEdorInsuredListSchema.getRelationToMain();
		this.RelationToMainName = aLBPOEdorInsuredListSchema.getRelationToMainName();
		this.MainInsuredID = aLBPOEdorInsuredListSchema.getMainInsuredID();
		this.MainInsuredName = aLBPOEdorInsuredListSchema.getMainInsuredName();
		this.MainInsuredIDNo = aLBPOEdorInsuredListSchema.getMainInsuredIDNo();
		this.OldInsuredName = aLBPOEdorInsuredListSchema.getOldInsuredName();
		this.OldInsuredIDNo = aLBPOEdorInsuredListSchema.getOldInsuredIDNo();
		this.InsuredName = aLBPOEdorInsuredListSchema.getInsuredName();
		this.InsuredIDType = aLBPOEdorInsuredListSchema.getInsuredIDType();
		this.InsuredIDTypeName = aLBPOEdorInsuredListSchema.getInsuredIDTypeName();
		this.InsuredIDNo = aLBPOEdorInsuredListSchema.getInsuredIDNo();
		this.InsuredGender = aLBPOEdorInsuredListSchema.getInsuredGender();
		this.InsuredGenderName = aLBPOEdorInsuredListSchema.getInsuredGenderName();
		this.InsuredBirthday = aLBPOEdorInsuredListSchema.getInsuredBirthday();
		this.EdorValDate = aLBPOEdorInsuredListSchema.getEdorValDate();
		this.PlanCode = aLBPOEdorInsuredListSchema.getPlanCode();
		this.PlanDesc = aLBPOEdorInsuredListSchema.getPlanDesc();
		this.OccupCode = aLBPOEdorInsuredListSchema.getOccupCode();
		this.OccupName = aLBPOEdorInsuredListSchema.getOccupName();
		this.HeadBankCode = aLBPOEdorInsuredListSchema.getHeadBankCode();
		this.HeadBankName = aLBPOEdorInsuredListSchema.getHeadBankName();
		this.AccName = aLBPOEdorInsuredListSchema.getAccName();
		this.BankAccNo = aLBPOEdorInsuredListSchema.getBankAccNo();
		this.BankProvince = aLBPOEdorInsuredListSchema.getBankProvince();
		this.BankProvinceName = aLBPOEdorInsuredListSchema.getBankProvinceName();
		this.BankCity = aLBPOEdorInsuredListSchema.getBankCity();
		this.BankCityName = aLBPOEdorInsuredListSchema.getBankCityName();
		this.ServerArea = aLBPOEdorInsuredListSchema.getServerArea();
		this.ServerAreaName = aLBPOEdorInsuredListSchema.getServerAreaName();
		this.Substandard = aLBPOEdorInsuredListSchema.getSubstandard();
		this.SubstandardName = aLBPOEdorInsuredListSchema.getSubstandardName();
		this.SocialInsuFlag = aLBPOEdorInsuredListSchema.getSocialInsuFlag();
		this.SocialInsuFlagName = aLBPOEdorInsuredListSchema.getSocialInsuFlagName();
		this.Position = aLBPOEdorInsuredListSchema.getPosition();
		this.PositionName = aLBPOEdorInsuredListSchema.getPositionName();
		this.JoinCompDate = aLBPOEdorInsuredListSchema.getJoinCompDate();
		this.Seniority = aLBPOEdorInsuredListSchema.getSeniority();
		this.Salary = aLBPOEdorInsuredListSchema.getSalary();
		this.GetYear = aLBPOEdorInsuredListSchema.getGetYear();
		this.GetStartType = aLBPOEdorInsuredListSchema.getGetStartType();
		this.GetStartTypeName = aLBPOEdorInsuredListSchema.getGetStartTypeName();
		this.InAmount = aLBPOEdorInsuredListSchema.getInAmount();
		this.OutAmount = aLBPOEdorInsuredListSchema.getOutAmount();
		this.ExpiryGetMode = aLBPOEdorInsuredListSchema.getExpiryGetMode();
		this.ExpiryGetModeName = aLBPOEdorInsuredListSchema.getExpiryGetModeName();
		this.ExpiryPayMode = aLBPOEdorInsuredListSchema.getExpiryPayMode();
		this.ExpiryPayModeName = aLBPOEdorInsuredListSchema.getExpiryPayModeName();
		this.TransMode = aLBPOEdorInsuredListSchema.getTransMode();
		this.TransModeName = aLBPOEdorInsuredListSchema.getTransModeName();
		this.TransAmount = aLBPOEdorInsuredListSchema.getTransAmount();
		this.AnnuityGetMode = aLBPOEdorInsuredListSchema.getAnnuityGetMode();
		this.AnnuityGetModeName = aLBPOEdorInsuredListSchema.getAnnuityGetModeName();
		this.WorkIDNo = aLBPOEdorInsuredListSchema.getWorkIDNo();
		this.IsLongValid = aLBPOEdorInsuredListSchema.getIsLongValid();
		this.IsLongValidName = aLBPOEdorInsuredListSchema.getIsLongValidName();
		this.IDEndDate = aLBPOEdorInsuredListSchema.getIDEndDate();
		this.SubCompanyCode = aLBPOEdorInsuredListSchema.getSubCompanyCode();
		this.DeptCode = aLBPOEdorInsuredListSchema.getDeptCode();
		this.InsureCode = aLBPOEdorInsuredListSchema.getInsureCode();
		this.SubCustomerNo = aLBPOEdorInsuredListSchema.getSubCustomerNo();
		this.SubCustomerName = aLBPOEdorInsuredListSchema.getSubCustomerName();
		this.WorkAddress = aLBPOEdorInsuredListSchema.getWorkAddress();
		this.SocialInsuAddress = aLBPOEdorInsuredListSchema.getSocialInsuAddress();
		this.ZipCode = aLBPOEdorInsuredListSchema.getZipCode();
		this.EMail = aLBPOEdorInsuredListSchema.getEMail();
		this.Wechat = aLBPOEdorInsuredListSchema.getWechat();
		this.Phone = aLBPOEdorInsuredListSchema.getPhone();
		this.Mobile = aLBPOEdorInsuredListSchema.getMobile();
		this.Province = aLBPOEdorInsuredListSchema.getProvince();
		this.ProvinceName = aLBPOEdorInsuredListSchema.getProvinceName();
		this.City = aLBPOEdorInsuredListSchema.getCity();
		this.CityName = aLBPOEdorInsuredListSchema.getCityName();
		this.County = aLBPOEdorInsuredListSchema.getCounty();
		this.CountyName = aLBPOEdorInsuredListSchema.getCountyName();
		this.Address = aLBPOEdorInsuredListSchema.getAddress();
		this.OriginContNo = aLBPOEdorInsuredListSchema.getOriginContNo();
		this.OriginInsuredNo = aLBPOEdorInsuredListSchema.getOriginInsuredNo();
		this.State = aLBPOEdorInsuredListSchema.getState();
		this.Reason = aLBPOEdorInsuredListSchema.getReason();
		this.ManageCom = aLBPOEdorInsuredListSchema.getManageCom();
		this.ComCode = aLBPOEdorInsuredListSchema.getComCode();
		this.MakeOperator = aLBPOEdorInsuredListSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLBPOEdorInsuredListSchema.getMakeDate());
		this.MakeTime = aLBPOEdorInsuredListSchema.getMakeTime();
		this.ModifyOperator = aLBPOEdorInsuredListSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLBPOEdorInsuredListSchema.getModifyDate());
		this.ModifyTime = aLBPOEdorInsuredListSchema.getModifyTime();
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
			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("EdorAppNo") == null )
				this.EdorAppNo = null;
			else
				this.EdorAppNo = rs.getString("EdorAppNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			this.OrderNo = rs.getInt("OrderNo");
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			this.GroupNo = rs.getInt("GroupNo");
			this.GroupOrderNo = rs.getInt("GroupOrderNo");
			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("InsuredID") == null )
				this.InsuredID = null;
			else
				this.InsuredID = rs.getString("InsuredID").trim();

			if( rs.getString("InsuredType") == null )
				this.InsuredType = null;
			else
				this.InsuredType = rs.getString("InsuredType").trim();

			if( rs.getString("InsuredTypeName") == null )
				this.InsuredTypeName = null;
			else
				this.InsuredTypeName = rs.getString("InsuredTypeName").trim();

			if( rs.getString("NumPeople") == null )
				this.NumPeople = null;
			else
				this.NumPeople = rs.getString("NumPeople").trim();

			if( rs.getString("RelationToMain") == null )
				this.RelationToMain = null;
			else
				this.RelationToMain = rs.getString("RelationToMain").trim();

			if( rs.getString("RelationToMainName") == null )
				this.RelationToMainName = null;
			else
				this.RelationToMainName = rs.getString("RelationToMainName").trim();

			if( rs.getString("MainInsuredID") == null )
				this.MainInsuredID = null;
			else
				this.MainInsuredID = rs.getString("MainInsuredID").trim();

			if( rs.getString("MainInsuredName") == null )
				this.MainInsuredName = null;
			else
				this.MainInsuredName = rs.getString("MainInsuredName").trim();

			if( rs.getString("MainInsuredIDNo") == null )
				this.MainInsuredIDNo = null;
			else
				this.MainInsuredIDNo = rs.getString("MainInsuredIDNo").trim();

			if( rs.getString("OldInsuredName") == null )
				this.OldInsuredName = null;
			else
				this.OldInsuredName = rs.getString("OldInsuredName").trim();

			if( rs.getString("OldInsuredIDNo") == null )
				this.OldInsuredIDNo = null;
			else
				this.OldInsuredIDNo = rs.getString("OldInsuredIDNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredIDTypeName") == null )
				this.InsuredIDTypeName = null;
			else
				this.InsuredIDTypeName = rs.getString("InsuredIDTypeName").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			if( rs.getString("InsuredGender") == null )
				this.InsuredGender = null;
			else
				this.InsuredGender = rs.getString("InsuredGender").trim();

			if( rs.getString("InsuredGenderName") == null )
				this.InsuredGenderName = null;
			else
				this.InsuredGenderName = rs.getString("InsuredGenderName").trim();

			if( rs.getString("InsuredBirthday") == null )
				this.InsuredBirthday = null;
			else
				this.InsuredBirthday = rs.getString("InsuredBirthday").trim();

			if( rs.getString("EdorValDate") == null )
				this.EdorValDate = null;
			else
				this.EdorValDate = rs.getString("EdorValDate").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("PlanDesc") == null )
				this.PlanDesc = null;
			else
				this.PlanDesc = rs.getString("PlanDesc").trim();

			if( rs.getString("OccupCode") == null )
				this.OccupCode = null;
			else
				this.OccupCode = rs.getString("OccupCode").trim();

			if( rs.getString("OccupName") == null )
				this.OccupName = null;
			else
				this.OccupName = rs.getString("OccupName").trim();

			if( rs.getString("HeadBankCode") == null )
				this.HeadBankCode = null;
			else
				this.HeadBankCode = rs.getString("HeadBankCode").trim();

			if( rs.getString("HeadBankName") == null )
				this.HeadBankName = null;
			else
				this.HeadBankName = rs.getString("HeadBankName").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("BankProvince") == null )
				this.BankProvince = null;
			else
				this.BankProvince = rs.getString("BankProvince").trim();

			if( rs.getString("BankProvinceName") == null )
				this.BankProvinceName = null;
			else
				this.BankProvinceName = rs.getString("BankProvinceName").trim();

			if( rs.getString("BankCity") == null )
				this.BankCity = null;
			else
				this.BankCity = rs.getString("BankCity").trim();

			if( rs.getString("BankCityName") == null )
				this.BankCityName = null;
			else
				this.BankCityName = rs.getString("BankCityName").trim();

			if( rs.getString("ServerArea") == null )
				this.ServerArea = null;
			else
				this.ServerArea = rs.getString("ServerArea").trim();

			if( rs.getString("ServerAreaName") == null )
				this.ServerAreaName = null;
			else
				this.ServerAreaName = rs.getString("ServerAreaName").trim();

			if( rs.getString("Substandard") == null )
				this.Substandard = null;
			else
				this.Substandard = rs.getString("Substandard").trim();

			if( rs.getString("SubstandardName") == null )
				this.SubstandardName = null;
			else
				this.SubstandardName = rs.getString("SubstandardName").trim();

			if( rs.getString("SocialInsuFlag") == null )
				this.SocialInsuFlag = null;
			else
				this.SocialInsuFlag = rs.getString("SocialInsuFlag").trim();

			if( rs.getString("SocialInsuFlagName") == null )
				this.SocialInsuFlagName = null;
			else
				this.SocialInsuFlagName = rs.getString("SocialInsuFlagName").trim();

			if( rs.getString("Position") == null )
				this.Position = null;
			else
				this.Position = rs.getString("Position").trim();

			if( rs.getString("PositionName") == null )
				this.PositionName = null;
			else
				this.PositionName = rs.getString("PositionName").trim();

			if( rs.getString("JoinCompDate") == null )
				this.JoinCompDate = null;
			else
				this.JoinCompDate = rs.getString("JoinCompDate").trim();

			if( rs.getString("Seniority") == null )
				this.Seniority = null;
			else
				this.Seniority = rs.getString("Seniority").trim();

			if( rs.getString("Salary") == null )
				this.Salary = null;
			else
				this.Salary = rs.getString("Salary").trim();

			if( rs.getString("GetYear") == null )
				this.GetYear = null;
			else
				this.GetYear = rs.getString("GetYear").trim();

			if( rs.getString("GetStartType") == null )
				this.GetStartType = null;
			else
				this.GetStartType = rs.getString("GetStartType").trim();

			if( rs.getString("GetStartTypeName") == null )
				this.GetStartTypeName = null;
			else
				this.GetStartTypeName = rs.getString("GetStartTypeName").trim();

			if( rs.getString("InAmount") == null )
				this.InAmount = null;
			else
				this.InAmount = rs.getString("InAmount").trim();

			if( rs.getString("OutAmount") == null )
				this.OutAmount = null;
			else
				this.OutAmount = rs.getString("OutAmount").trim();

			if( rs.getString("ExpiryGetMode") == null )
				this.ExpiryGetMode = null;
			else
				this.ExpiryGetMode = rs.getString("ExpiryGetMode").trim();

			if( rs.getString("ExpiryGetModeName") == null )
				this.ExpiryGetModeName = null;
			else
				this.ExpiryGetModeName = rs.getString("ExpiryGetModeName").trim();

			if( rs.getString("ExpiryPayMode") == null )
				this.ExpiryPayMode = null;
			else
				this.ExpiryPayMode = rs.getString("ExpiryPayMode").trim();

			if( rs.getString("ExpiryPayModeName") == null )
				this.ExpiryPayModeName = null;
			else
				this.ExpiryPayModeName = rs.getString("ExpiryPayModeName").trim();

			if( rs.getString("TransMode") == null )
				this.TransMode = null;
			else
				this.TransMode = rs.getString("TransMode").trim();

			if( rs.getString("TransModeName") == null )
				this.TransModeName = null;
			else
				this.TransModeName = rs.getString("TransModeName").trim();

			if( rs.getString("TransAmount") == null )
				this.TransAmount = null;
			else
				this.TransAmount = rs.getString("TransAmount").trim();

			if( rs.getString("AnnuityGetMode") == null )
				this.AnnuityGetMode = null;
			else
				this.AnnuityGetMode = rs.getString("AnnuityGetMode").trim();

			if( rs.getString("AnnuityGetModeName") == null )
				this.AnnuityGetModeName = null;
			else
				this.AnnuityGetModeName = rs.getString("AnnuityGetModeName").trim();

			if( rs.getString("WorkIDNo") == null )
				this.WorkIDNo = null;
			else
				this.WorkIDNo = rs.getString("WorkIDNo").trim();

			if( rs.getString("IsLongValid") == null )
				this.IsLongValid = null;
			else
				this.IsLongValid = rs.getString("IsLongValid").trim();

			if( rs.getString("IsLongValidName") == null )
				this.IsLongValidName = null;
			else
				this.IsLongValidName = rs.getString("IsLongValidName").trim();

			if( rs.getString("IDEndDate") == null )
				this.IDEndDate = null;
			else
				this.IDEndDate = rs.getString("IDEndDate").trim();

			if( rs.getString("SubCompanyCode") == null )
				this.SubCompanyCode = null;
			else
				this.SubCompanyCode = rs.getString("SubCompanyCode").trim();

			if( rs.getString("DeptCode") == null )
				this.DeptCode = null;
			else
				this.DeptCode = rs.getString("DeptCode").trim();

			if( rs.getString("InsureCode") == null )
				this.InsureCode = null;
			else
				this.InsureCode = rs.getString("InsureCode").trim();

			if( rs.getString("SubCustomerNo") == null )
				this.SubCustomerNo = null;
			else
				this.SubCustomerNo = rs.getString("SubCustomerNo").trim();

			if( rs.getString("SubCustomerName") == null )
				this.SubCustomerName = null;
			else
				this.SubCustomerName = rs.getString("SubCustomerName").trim();

			if( rs.getString("WorkAddress") == null )
				this.WorkAddress = null;
			else
				this.WorkAddress = rs.getString("WorkAddress").trim();

			if( rs.getString("SocialInsuAddress") == null )
				this.SocialInsuAddress = null;
			else
				this.SocialInsuAddress = rs.getString("SocialInsuAddress").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			if( rs.getString("Wechat") == null )
				this.Wechat = null;
			else
				this.Wechat = rs.getString("Wechat").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("Province") == null )
				this.Province = null;
			else
				this.Province = rs.getString("Province").trim();

			if( rs.getString("ProvinceName") == null )
				this.ProvinceName = null;
			else
				this.ProvinceName = rs.getString("ProvinceName").trim();

			if( rs.getString("City") == null )
				this.City = null;
			else
				this.City = rs.getString("City").trim();

			if( rs.getString("CityName") == null )
				this.CityName = null;
			else
				this.CityName = rs.getString("CityName").trim();

			if( rs.getString("County") == null )
				this.County = null;
			else
				this.County = rs.getString("County").trim();

			if( rs.getString("CountyName") == null )
				this.CountyName = null;
			else
				this.CountyName = rs.getString("CountyName").trim();

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("OriginContNo") == null )
				this.OriginContNo = null;
			else
				this.OriginContNo = rs.getString("OriginContNo").trim();

			if( rs.getString("OriginInsuredNo") == null )
				this.OriginInsuredNo = null;
			else
				this.OriginInsuredNo = rs.getString("OriginInsuredNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

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
			logger.debug("数据库中的LBPOEdorInsuredList表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOEdorInsuredListSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOEdorInsuredListSchema getSchema()
	{
		LBPOEdorInsuredListSchema aLBPOEdorInsuredListSchema = new LBPOEdorInsuredListSchema();
		aLBPOEdorInsuredListSchema.setSchema(this);
		return aLBPOEdorInsuredListSchema;
	}

	public LBPOEdorInsuredListDB getDB()
	{
		LBPOEdorInsuredListDB aDBOper = new LBPOEdorInsuredListDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOEdorInsuredList描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OrderNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GroupNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GroupOrderNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NumPeople)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToMain)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToMainName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainInsuredID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainInsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainInsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldInsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldInsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredGender)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredGenderName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredBirthday)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorValDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadBankName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankProvinceName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCityName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerArea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ServerAreaName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Substandard)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubstandardName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuFlagName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Position)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PositionName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JoinCompDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Seniority)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Salary)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetYear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAmount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutAmount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpiryGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpiryGetModeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpiryPayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpiryPayModeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransModeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransAmount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AnnuityGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AnnuityGetModeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsLongValid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsLongValidName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDEndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCompanyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeptCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsureCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SocialInsuAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Wechat)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Province)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProvinceName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(City)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CityName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(County)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CountyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Address)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriginContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriginInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOEdorInsuredList>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			EdorAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GroupNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			GroupOrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InsuredID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InsuredTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			NumPeople = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			RelationToMain = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RelationToMainName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MainInsuredID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MainInsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MainInsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			OldInsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			OldInsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InsuredIDTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InsuredGender = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			InsuredGenderName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			InsuredBirthday = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			EdorValDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PlanDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			OccupCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			OccupName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			HeadBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			HeadBankName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			BankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			BankProvinceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			BankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			BankCityName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ServerArea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ServerAreaName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			Substandard = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			SubstandardName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			SocialInsuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			SocialInsuFlagName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			Position = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			PositionName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			JoinCompDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			Seniority = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			Salary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			GetYear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			GetStartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			GetStartTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			InAmount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			OutAmount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			ExpiryGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			ExpiryGetModeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			ExpiryPayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			ExpiryPayModeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			TransMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			TransModeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			TransAmount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			AnnuityGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			AnnuityGetModeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			WorkIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			IsLongValid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			IsLongValidName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			IDEndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			SubCompanyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			DeptCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			InsureCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			SubCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			SubCustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			WorkAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			SocialInsuAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			Wechat = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			ProvinceName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			CityName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			CountyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			OriginContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			OriginInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOEdorInsuredListSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAppNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("GroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupNo));
		}
		if (FCode.equalsIgnoreCase("GroupOrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupOrderNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("InsuredID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredID));
		}
		if (FCode.equalsIgnoreCase("InsuredType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredType));
		}
		if (FCode.equalsIgnoreCase("InsuredTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredTypeName));
		}
		if (FCode.equalsIgnoreCase("NumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumPeople));
		}
		if (FCode.equalsIgnoreCase("RelationToMain"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToMain));
		}
		if (FCode.equalsIgnoreCase("RelationToMainName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToMainName));
		}
		if (FCode.equalsIgnoreCase("MainInsuredID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainInsuredID));
		}
		if (FCode.equalsIgnoreCase("MainInsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainInsuredName));
		}
		if (FCode.equalsIgnoreCase("MainInsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainInsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("OldInsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldInsuredName));
		}
		if (FCode.equalsIgnoreCase("OldInsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldInsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equalsIgnoreCase("InsuredIDTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDTypeName));
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("InsuredGender"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredGender));
		}
		if (FCode.equalsIgnoreCase("InsuredGenderName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredGenderName));
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredBirthday));
		}
		if (FCode.equalsIgnoreCase("EdorValDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorValDate));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanDesc));
		}
		if (FCode.equalsIgnoreCase("OccupCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupCode));
		}
		if (FCode.equalsIgnoreCase("OccupName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupName));
		}
		if (FCode.equalsIgnoreCase("HeadBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadBankCode));
		}
		if (FCode.equalsIgnoreCase("HeadBankName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadBankName));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("BankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankProvince));
		}
		if (FCode.equalsIgnoreCase("BankProvinceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankProvinceName));
		}
		if (FCode.equalsIgnoreCase("BankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCity));
		}
		if (FCode.equalsIgnoreCase("BankCityName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCityName));
		}
		if (FCode.equalsIgnoreCase("ServerArea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerArea));
		}
		if (FCode.equalsIgnoreCase("ServerAreaName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ServerAreaName));
		}
		if (FCode.equalsIgnoreCase("Substandard"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Substandard));
		}
		if (FCode.equalsIgnoreCase("SubstandardName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubstandardName));
		}
		if (FCode.equalsIgnoreCase("SocialInsuFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuFlag));
		}
		if (FCode.equalsIgnoreCase("SocialInsuFlagName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuFlagName));
		}
		if (FCode.equalsIgnoreCase("Position"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Position));
		}
		if (FCode.equalsIgnoreCase("PositionName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PositionName));
		}
		if (FCode.equalsIgnoreCase("JoinCompDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JoinCompDate));
		}
		if (FCode.equalsIgnoreCase("Seniority"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Seniority));
		}
		if (FCode.equalsIgnoreCase("Salary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salary));
		}
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetYear));
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartType));
		}
		if (FCode.equalsIgnoreCase("GetStartTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartTypeName));
		}
		if (FCode.equalsIgnoreCase("InAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAmount));
		}
		if (FCode.equalsIgnoreCase("OutAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutAmount));
		}
		if (FCode.equalsIgnoreCase("ExpiryGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpiryGetMode));
		}
		if (FCode.equalsIgnoreCase("ExpiryGetModeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpiryGetModeName));
		}
		if (FCode.equalsIgnoreCase("ExpiryPayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpiryPayMode));
		}
		if (FCode.equalsIgnoreCase("ExpiryPayModeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpiryPayModeName));
		}
		if (FCode.equalsIgnoreCase("TransMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransMode));
		}
		if (FCode.equalsIgnoreCase("TransModeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransModeName));
		}
		if (FCode.equalsIgnoreCase("TransAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransAmount));
		}
		if (FCode.equalsIgnoreCase("AnnuityGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AnnuityGetMode));
		}
		if (FCode.equalsIgnoreCase("AnnuityGetModeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AnnuityGetModeName));
		}
		if (FCode.equalsIgnoreCase("WorkIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkIDNo));
		}
		if (FCode.equalsIgnoreCase("IsLongValid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsLongValid));
		}
		if (FCode.equalsIgnoreCase("IsLongValidName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsLongValidName));
		}
		if (FCode.equalsIgnoreCase("IDEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDEndDate));
		}
		if (FCode.equalsIgnoreCase("SubCompanyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCompanyCode));
		}
		if (FCode.equalsIgnoreCase("DeptCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeptCode));
		}
		if (FCode.equalsIgnoreCase("InsureCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsureCode));
		}
		if (FCode.equalsIgnoreCase("SubCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCustomerNo));
		}
		if (FCode.equalsIgnoreCase("SubCustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCustomerName));
		}
		if (FCode.equalsIgnoreCase("WorkAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkAddress));
		}
		if (FCode.equalsIgnoreCase("SocialInsuAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocialInsuAddress));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equalsIgnoreCase("Wechat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Wechat));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equalsIgnoreCase("Province"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Province));
		}
		if (FCode.equalsIgnoreCase("ProvinceName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProvinceName));
		}
		if (FCode.equalsIgnoreCase("City"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(City));
		}
		if (FCode.equalsIgnoreCase("CityName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CityName));
		}
		if (FCode.equalsIgnoreCase("County"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(County));
		}
		if (FCode.equalsIgnoreCase("CountyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CountyName));
		}
		if (FCode.equalsIgnoreCase("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equalsIgnoreCase("OriginContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriginContNo));
		}
		if (FCode.equalsIgnoreCase("OriginInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriginInsuredNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
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
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorAppNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 5:
				strFieldValue = String.valueOf(OrderNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 9:
				strFieldValue = String.valueOf(GroupNo);
				break;
			case 10:
				strFieldValue = String.valueOf(GroupOrderNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InsuredID);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InsuredTypeName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(NumPeople);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(RelationToMain);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RelationToMainName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MainInsuredID);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MainInsuredName);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MainInsuredIDNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(OldInsuredName);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(OldInsuredIDNo);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDTypeName);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InsuredGender);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(InsuredGenderName);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InsuredBirthday);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(EdorValDate);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PlanDesc);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(OccupCode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(OccupName);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(HeadBankCode);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(HeadBankName);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(BankProvince);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(BankProvinceName);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(BankCity);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(BankCityName);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ServerArea);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ServerAreaName);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(Substandard);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(SubstandardName);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlag);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuFlagName);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(Position);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(PositionName);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(JoinCompDate);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(Seniority);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(Salary);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(GetYear);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(GetStartType);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(GetStartTypeName);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(InAmount);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(OutAmount);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(ExpiryGetMode);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(ExpiryGetModeName);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(ExpiryPayMode);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(ExpiryPayModeName);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(TransMode);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(TransModeName);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(TransAmount);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(AnnuityGetMode);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(AnnuityGetModeName);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(WorkIDNo);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(IsLongValid);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(IsLongValidName);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(IDEndDate);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(SubCompanyCode);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(DeptCode);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(InsureCode);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(SubCustomerNo);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(SubCustomerName);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(WorkAddress);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(SocialInsuAddress);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(Wechat);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(ProvinceName);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(CityName);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(County);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(CountyName);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(OriginContNo);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(OriginInsuredNo);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 102:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAppNo = FValue.trim();
			}
			else
				EdorAppNo = null;
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
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityID = FValue.trim();
			}
			else
				ActivityID = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("GroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GroupNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("GroupOrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GroupOrderNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("InsuredID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredID = FValue.trim();
			}
			else
				InsuredID = null;
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
		if (FCode.equalsIgnoreCase("InsuredTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredTypeName = FValue.trim();
			}
			else
				InsuredTypeName = null;
		}
		if (FCode.equalsIgnoreCase("NumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NumPeople = FValue.trim();
			}
			else
				NumPeople = null;
		}
		if (FCode.equalsIgnoreCase("RelationToMain"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToMain = FValue.trim();
			}
			else
				RelationToMain = null;
		}
		if (FCode.equalsIgnoreCase("RelationToMainName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToMainName = FValue.trim();
			}
			else
				RelationToMainName = null;
		}
		if (FCode.equalsIgnoreCase("MainInsuredID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainInsuredID = FValue.trim();
			}
			else
				MainInsuredID = null;
		}
		if (FCode.equalsIgnoreCase("MainInsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainInsuredName = FValue.trim();
			}
			else
				MainInsuredName = null;
		}
		if (FCode.equalsIgnoreCase("MainInsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainInsuredIDNo = FValue.trim();
			}
			else
				MainInsuredIDNo = null;
		}
		if (FCode.equalsIgnoreCase("OldInsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldInsuredName = FValue.trim();
			}
			else
				OldInsuredName = null;
		}
		if (FCode.equalsIgnoreCase("OldInsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldInsuredIDNo = FValue.trim();
			}
			else
				OldInsuredIDNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
		}
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDType = FValue.trim();
			}
			else
				InsuredIDType = null;
		}
		if (FCode.equalsIgnoreCase("InsuredIDTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDTypeName = FValue.trim();
			}
			else
				InsuredIDTypeName = null;
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredGender"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredGender = FValue.trim();
			}
			else
				InsuredGender = null;
		}
		if (FCode.equalsIgnoreCase("InsuredGenderName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredGenderName = FValue.trim();
			}
			else
				InsuredGenderName = null;
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredBirthday = FValue.trim();
			}
			else
				InsuredBirthday = null;
		}
		if (FCode.equalsIgnoreCase("EdorValDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorValDate = FValue.trim();
			}
			else
				EdorValDate = null;
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
		if (FCode.equalsIgnoreCase("PlanDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanDesc = FValue.trim();
			}
			else
				PlanDesc = null;
		}
		if (FCode.equalsIgnoreCase("OccupCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupCode = FValue.trim();
			}
			else
				OccupCode = null;
		}
		if (FCode.equalsIgnoreCase("OccupName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupName = FValue.trim();
			}
			else
				OccupName = null;
		}
		if (FCode.equalsIgnoreCase("HeadBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadBankCode = FValue.trim();
			}
			else
				HeadBankCode = null;
		}
		if (FCode.equalsIgnoreCase("HeadBankName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadBankName = FValue.trim();
			}
			else
				HeadBankName = null;
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
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("BankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankProvince = FValue.trim();
			}
			else
				BankProvince = null;
		}
		if (FCode.equalsIgnoreCase("BankProvinceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankProvinceName = FValue.trim();
			}
			else
				BankProvinceName = null;
		}
		if (FCode.equalsIgnoreCase("BankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCity = FValue.trim();
			}
			else
				BankCity = null;
		}
		if (FCode.equalsIgnoreCase("BankCityName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCityName = FValue.trim();
			}
			else
				BankCityName = null;
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
		if (FCode.equalsIgnoreCase("ServerAreaName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ServerAreaName = FValue.trim();
			}
			else
				ServerAreaName = null;
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
		if (FCode.equalsIgnoreCase("SubstandardName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubstandardName = FValue.trim();
			}
			else
				SubstandardName = null;
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
		if (FCode.equalsIgnoreCase("SocialInsuFlagName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SocialInsuFlagName = FValue.trim();
			}
			else
				SocialInsuFlagName = null;
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
		if (FCode.equalsIgnoreCase("PositionName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PositionName = FValue.trim();
			}
			else
				PositionName = null;
		}
		if (FCode.equalsIgnoreCase("JoinCompDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JoinCompDate = FValue.trim();
			}
			else
				JoinCompDate = null;
		}
		if (FCode.equalsIgnoreCase("Seniority"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Seniority = FValue.trim();
			}
			else
				Seniority = null;
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
		if (FCode.equalsIgnoreCase("GetYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetYear = FValue.trim();
			}
			else
				GetYear = null;
		}
		if (FCode.equalsIgnoreCase("GetStartType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartType = FValue.trim();
			}
			else
				GetStartType = null;
		}
		if (FCode.equalsIgnoreCase("GetStartTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartTypeName = FValue.trim();
			}
			else
				GetStartTypeName = null;
		}
		if (FCode.equalsIgnoreCase("InAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InAmount = FValue.trim();
			}
			else
				InAmount = null;
		}
		if (FCode.equalsIgnoreCase("OutAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutAmount = FValue.trim();
			}
			else
				OutAmount = null;
		}
		if (FCode.equalsIgnoreCase("ExpiryGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpiryGetMode = FValue.trim();
			}
			else
				ExpiryGetMode = null;
		}
		if (FCode.equalsIgnoreCase("ExpiryGetModeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpiryGetModeName = FValue.trim();
			}
			else
				ExpiryGetModeName = null;
		}
		if (FCode.equalsIgnoreCase("ExpiryPayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpiryPayMode = FValue.trim();
			}
			else
				ExpiryPayMode = null;
		}
		if (FCode.equalsIgnoreCase("ExpiryPayModeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpiryPayModeName = FValue.trim();
			}
			else
				ExpiryPayModeName = null;
		}
		if (FCode.equalsIgnoreCase("TransMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransMode = FValue.trim();
			}
			else
				TransMode = null;
		}
		if (FCode.equalsIgnoreCase("TransModeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransModeName = FValue.trim();
			}
			else
				TransModeName = null;
		}
		if (FCode.equalsIgnoreCase("TransAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransAmount = FValue.trim();
			}
			else
				TransAmount = null;
		}
		if (FCode.equalsIgnoreCase("AnnuityGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AnnuityGetMode = FValue.trim();
			}
			else
				AnnuityGetMode = null;
		}
		if (FCode.equalsIgnoreCase("AnnuityGetModeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AnnuityGetModeName = FValue.trim();
			}
			else
				AnnuityGetModeName = null;
		}
		if (FCode.equalsIgnoreCase("WorkIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkIDNo = FValue.trim();
			}
			else
				WorkIDNo = null;
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
		if (FCode.equalsIgnoreCase("IsLongValidName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsLongValidName = FValue.trim();
			}
			else
				IsLongValidName = null;
		}
		if (FCode.equalsIgnoreCase("IDEndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDEndDate = FValue.trim();
			}
			else
				IDEndDate = null;
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
		if (FCode.equalsIgnoreCase("DeptCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeptCode = FValue.trim();
			}
			else
				DeptCode = null;
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
		if (FCode.equalsIgnoreCase("SubCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCustomerNo = FValue.trim();
			}
			else
				SubCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("SubCustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCustomerName = FValue.trim();
			}
			else
				SubCustomerName = null;
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
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
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
		if (FCode.equalsIgnoreCase("Wechat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Wechat = FValue.trim();
			}
			else
				Wechat = null;
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
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
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
		if (FCode.equalsIgnoreCase("ProvinceName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProvinceName = FValue.trim();
			}
			else
				ProvinceName = null;
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
		if (FCode.equalsIgnoreCase("CityName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CityName = FValue.trim();
			}
			else
				CityName = null;
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
		if (FCode.equalsIgnoreCase("CountyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CountyName = FValue.trim();
			}
			else
				CountyName = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		LBPOEdorInsuredListSchema other = (LBPOEdorInsuredListSchema)otherObject;
		return
			SerialNo == other.getSerialNo()
			&& EdorAppNo.equals(other.getEdorAppNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ActivityID.equals(other.getActivityID())
			&& BatchNo.equals(other.getBatchNo())
			&& OrderNo == other.getOrderNo()
			&& EdorNo.equals(other.getEdorNo())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& GroupNo == other.getGroupNo()
			&& GroupOrderNo == other.getGroupOrderNo()
			&& EdorType.equals(other.getEdorType())
			&& InsuredID.equals(other.getInsuredID())
			&& InsuredType.equals(other.getInsuredType())
			&& InsuredTypeName.equals(other.getInsuredTypeName())
			&& NumPeople.equals(other.getNumPeople())
			&& RelationToMain.equals(other.getRelationToMain())
			&& RelationToMainName.equals(other.getRelationToMainName())
			&& MainInsuredID.equals(other.getMainInsuredID())
			&& MainInsuredName.equals(other.getMainInsuredName())
			&& MainInsuredIDNo.equals(other.getMainInsuredIDNo())
			&& OldInsuredName.equals(other.getOldInsuredName())
			&& OldInsuredIDNo.equals(other.getOldInsuredIDNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredIDTypeName.equals(other.getInsuredIDTypeName())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& InsuredGender.equals(other.getInsuredGender())
			&& InsuredGenderName.equals(other.getInsuredGenderName())
			&& InsuredBirthday.equals(other.getInsuredBirthday())
			&& EdorValDate.equals(other.getEdorValDate())
			&& PlanCode.equals(other.getPlanCode())
			&& PlanDesc.equals(other.getPlanDesc())
			&& OccupCode.equals(other.getOccupCode())
			&& OccupName.equals(other.getOccupName())
			&& HeadBankCode.equals(other.getHeadBankCode())
			&& HeadBankName.equals(other.getHeadBankName())
			&& AccName.equals(other.getAccName())
			&& BankAccNo.equals(other.getBankAccNo())
			&& BankProvince.equals(other.getBankProvince())
			&& BankProvinceName.equals(other.getBankProvinceName())
			&& BankCity.equals(other.getBankCity())
			&& BankCityName.equals(other.getBankCityName())
			&& ServerArea.equals(other.getServerArea())
			&& ServerAreaName.equals(other.getServerAreaName())
			&& Substandard.equals(other.getSubstandard())
			&& SubstandardName.equals(other.getSubstandardName())
			&& SocialInsuFlag.equals(other.getSocialInsuFlag())
			&& SocialInsuFlagName.equals(other.getSocialInsuFlagName())
			&& Position.equals(other.getPosition())
			&& PositionName.equals(other.getPositionName())
			&& JoinCompDate.equals(other.getJoinCompDate())
			&& Seniority.equals(other.getSeniority())
			&& Salary.equals(other.getSalary())
			&& GetYear.equals(other.getGetYear())
			&& GetStartType.equals(other.getGetStartType())
			&& GetStartTypeName.equals(other.getGetStartTypeName())
			&& InAmount.equals(other.getInAmount())
			&& OutAmount.equals(other.getOutAmount())
			&& ExpiryGetMode.equals(other.getExpiryGetMode())
			&& ExpiryGetModeName.equals(other.getExpiryGetModeName())
			&& ExpiryPayMode.equals(other.getExpiryPayMode())
			&& ExpiryPayModeName.equals(other.getExpiryPayModeName())
			&& TransMode.equals(other.getTransMode())
			&& TransModeName.equals(other.getTransModeName())
			&& TransAmount.equals(other.getTransAmount())
			&& AnnuityGetMode.equals(other.getAnnuityGetMode())
			&& AnnuityGetModeName.equals(other.getAnnuityGetModeName())
			&& WorkIDNo.equals(other.getWorkIDNo())
			&& IsLongValid.equals(other.getIsLongValid())
			&& IsLongValidName.equals(other.getIsLongValidName())
			&& IDEndDate.equals(other.getIDEndDate())
			&& SubCompanyCode.equals(other.getSubCompanyCode())
			&& DeptCode.equals(other.getDeptCode())
			&& InsureCode.equals(other.getInsureCode())
			&& SubCustomerNo.equals(other.getSubCustomerNo())
			&& SubCustomerName.equals(other.getSubCustomerName())
			&& WorkAddress.equals(other.getWorkAddress())
			&& SocialInsuAddress.equals(other.getSocialInsuAddress())
			&& ZipCode.equals(other.getZipCode())
			&& EMail.equals(other.getEMail())
			&& Wechat.equals(other.getWechat())
			&& Phone.equals(other.getPhone())
			&& Mobile.equals(other.getMobile())
			&& Province.equals(other.getProvince())
			&& ProvinceName.equals(other.getProvinceName())
			&& City.equals(other.getCity())
			&& CityName.equals(other.getCityName())
			&& County.equals(other.getCounty())
			&& CountyName.equals(other.getCountyName())
			&& Address.equals(other.getAddress())
			&& OriginContNo.equals(other.getOriginContNo())
			&& OriginInsuredNo.equals(other.getOriginInsuredNo())
			&& State.equals(other.getState())
			&& Reason.equals(other.getReason())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ActivityID") ) {
			return 3;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 4;
		}
		if( strFieldName.equals("OrderNo") ) {
			return 5;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 6;
		}
		if( strFieldName.equals("ContNo") ) {
			return 7;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 8;
		}
		if( strFieldName.equals("GroupNo") ) {
			return 9;
		}
		if( strFieldName.equals("GroupOrderNo") ) {
			return 10;
		}
		if( strFieldName.equals("EdorType") ) {
			return 11;
		}
		if( strFieldName.equals("InsuredID") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredType") ) {
			return 13;
		}
		if( strFieldName.equals("InsuredTypeName") ) {
			return 14;
		}
		if( strFieldName.equals("NumPeople") ) {
			return 15;
		}
		if( strFieldName.equals("RelationToMain") ) {
			return 16;
		}
		if( strFieldName.equals("RelationToMainName") ) {
			return 17;
		}
		if( strFieldName.equals("MainInsuredID") ) {
			return 18;
		}
		if( strFieldName.equals("MainInsuredName") ) {
			return 19;
		}
		if( strFieldName.equals("MainInsuredIDNo") ) {
			return 20;
		}
		if( strFieldName.equals("OldInsuredName") ) {
			return 21;
		}
		if( strFieldName.equals("OldInsuredIDNo") ) {
			return 22;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 23;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 24;
		}
		if( strFieldName.equals("InsuredIDTypeName") ) {
			return 25;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 26;
		}
		if( strFieldName.equals("InsuredGender") ) {
			return 27;
		}
		if( strFieldName.equals("InsuredGenderName") ) {
			return 28;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 29;
		}
		if( strFieldName.equals("EdorValDate") ) {
			return 30;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 31;
		}
		if( strFieldName.equals("PlanDesc") ) {
			return 32;
		}
		if( strFieldName.equals("OccupCode") ) {
			return 33;
		}
		if( strFieldName.equals("OccupName") ) {
			return 34;
		}
		if( strFieldName.equals("HeadBankCode") ) {
			return 35;
		}
		if( strFieldName.equals("HeadBankName") ) {
			return 36;
		}
		if( strFieldName.equals("AccName") ) {
			return 37;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 38;
		}
		if( strFieldName.equals("BankProvince") ) {
			return 39;
		}
		if( strFieldName.equals("BankProvinceName") ) {
			return 40;
		}
		if( strFieldName.equals("BankCity") ) {
			return 41;
		}
		if( strFieldName.equals("BankCityName") ) {
			return 42;
		}
		if( strFieldName.equals("ServerArea") ) {
			return 43;
		}
		if( strFieldName.equals("ServerAreaName") ) {
			return 44;
		}
		if( strFieldName.equals("Substandard") ) {
			return 45;
		}
		if( strFieldName.equals("SubstandardName") ) {
			return 46;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return 47;
		}
		if( strFieldName.equals("SocialInsuFlagName") ) {
			return 48;
		}
		if( strFieldName.equals("Position") ) {
			return 49;
		}
		if( strFieldName.equals("PositionName") ) {
			return 50;
		}
		if( strFieldName.equals("JoinCompDate") ) {
			return 51;
		}
		if( strFieldName.equals("Seniority") ) {
			return 52;
		}
		if( strFieldName.equals("Salary") ) {
			return 53;
		}
		if( strFieldName.equals("GetYear") ) {
			return 54;
		}
		if( strFieldName.equals("GetStartType") ) {
			return 55;
		}
		if( strFieldName.equals("GetStartTypeName") ) {
			return 56;
		}
		if( strFieldName.equals("InAmount") ) {
			return 57;
		}
		if( strFieldName.equals("OutAmount") ) {
			return 58;
		}
		if( strFieldName.equals("ExpiryGetMode") ) {
			return 59;
		}
		if( strFieldName.equals("ExpiryGetModeName") ) {
			return 60;
		}
		if( strFieldName.equals("ExpiryPayMode") ) {
			return 61;
		}
		if( strFieldName.equals("ExpiryPayModeName") ) {
			return 62;
		}
		if( strFieldName.equals("TransMode") ) {
			return 63;
		}
		if( strFieldName.equals("TransModeName") ) {
			return 64;
		}
		if( strFieldName.equals("TransAmount") ) {
			return 65;
		}
		if( strFieldName.equals("AnnuityGetMode") ) {
			return 66;
		}
		if( strFieldName.equals("AnnuityGetModeName") ) {
			return 67;
		}
		if( strFieldName.equals("WorkIDNo") ) {
			return 68;
		}
		if( strFieldName.equals("IsLongValid") ) {
			return 69;
		}
		if( strFieldName.equals("IsLongValidName") ) {
			return 70;
		}
		if( strFieldName.equals("IDEndDate") ) {
			return 71;
		}
		if( strFieldName.equals("SubCompanyCode") ) {
			return 72;
		}
		if( strFieldName.equals("DeptCode") ) {
			return 73;
		}
		if( strFieldName.equals("InsureCode") ) {
			return 74;
		}
		if( strFieldName.equals("SubCustomerNo") ) {
			return 75;
		}
		if( strFieldName.equals("SubCustomerName") ) {
			return 76;
		}
		if( strFieldName.equals("WorkAddress") ) {
			return 77;
		}
		if( strFieldName.equals("SocialInsuAddress") ) {
			return 78;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 79;
		}
		if( strFieldName.equals("EMail") ) {
			return 80;
		}
		if( strFieldName.equals("Wechat") ) {
			return 81;
		}
		if( strFieldName.equals("Phone") ) {
			return 82;
		}
		if( strFieldName.equals("Mobile") ) {
			return 83;
		}
		if( strFieldName.equals("Province") ) {
			return 84;
		}
		if( strFieldName.equals("ProvinceName") ) {
			return 85;
		}
		if( strFieldName.equals("City") ) {
			return 86;
		}
		if( strFieldName.equals("CityName") ) {
			return 87;
		}
		if( strFieldName.equals("County") ) {
			return 88;
		}
		if( strFieldName.equals("CountyName") ) {
			return 89;
		}
		if( strFieldName.equals("Address") ) {
			return 90;
		}
		if( strFieldName.equals("OriginContNo") ) {
			return 91;
		}
		if( strFieldName.equals("OriginInsuredNo") ) {
			return 92;
		}
		if( strFieldName.equals("State") ) {
			return 93;
		}
		if( strFieldName.equals("Reason") ) {
			return 94;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 95;
		}
		if( strFieldName.equals("ComCode") ) {
			return 96;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 97;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 98;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 99;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 100;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 101;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 102;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "EdorAppNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ActivityID";
				break;
			case 4:
				strFieldName = "BatchNo";
				break;
			case 5:
				strFieldName = "OrderNo";
				break;
			case 6:
				strFieldName = "EdorNo";
				break;
			case 7:
				strFieldName = "ContNo";
				break;
			case 8:
				strFieldName = "InsuredNo";
				break;
			case 9:
				strFieldName = "GroupNo";
				break;
			case 10:
				strFieldName = "GroupOrderNo";
				break;
			case 11:
				strFieldName = "EdorType";
				break;
			case 12:
				strFieldName = "InsuredID";
				break;
			case 13:
				strFieldName = "InsuredType";
				break;
			case 14:
				strFieldName = "InsuredTypeName";
				break;
			case 15:
				strFieldName = "NumPeople";
				break;
			case 16:
				strFieldName = "RelationToMain";
				break;
			case 17:
				strFieldName = "RelationToMainName";
				break;
			case 18:
				strFieldName = "MainInsuredID";
				break;
			case 19:
				strFieldName = "MainInsuredName";
				break;
			case 20:
				strFieldName = "MainInsuredIDNo";
				break;
			case 21:
				strFieldName = "OldInsuredName";
				break;
			case 22:
				strFieldName = "OldInsuredIDNo";
				break;
			case 23:
				strFieldName = "InsuredName";
				break;
			case 24:
				strFieldName = "InsuredIDType";
				break;
			case 25:
				strFieldName = "InsuredIDTypeName";
				break;
			case 26:
				strFieldName = "InsuredIDNo";
				break;
			case 27:
				strFieldName = "InsuredGender";
				break;
			case 28:
				strFieldName = "InsuredGenderName";
				break;
			case 29:
				strFieldName = "InsuredBirthday";
				break;
			case 30:
				strFieldName = "EdorValDate";
				break;
			case 31:
				strFieldName = "PlanCode";
				break;
			case 32:
				strFieldName = "PlanDesc";
				break;
			case 33:
				strFieldName = "OccupCode";
				break;
			case 34:
				strFieldName = "OccupName";
				break;
			case 35:
				strFieldName = "HeadBankCode";
				break;
			case 36:
				strFieldName = "HeadBankName";
				break;
			case 37:
				strFieldName = "AccName";
				break;
			case 38:
				strFieldName = "BankAccNo";
				break;
			case 39:
				strFieldName = "BankProvince";
				break;
			case 40:
				strFieldName = "BankProvinceName";
				break;
			case 41:
				strFieldName = "BankCity";
				break;
			case 42:
				strFieldName = "BankCityName";
				break;
			case 43:
				strFieldName = "ServerArea";
				break;
			case 44:
				strFieldName = "ServerAreaName";
				break;
			case 45:
				strFieldName = "Substandard";
				break;
			case 46:
				strFieldName = "SubstandardName";
				break;
			case 47:
				strFieldName = "SocialInsuFlag";
				break;
			case 48:
				strFieldName = "SocialInsuFlagName";
				break;
			case 49:
				strFieldName = "Position";
				break;
			case 50:
				strFieldName = "PositionName";
				break;
			case 51:
				strFieldName = "JoinCompDate";
				break;
			case 52:
				strFieldName = "Seniority";
				break;
			case 53:
				strFieldName = "Salary";
				break;
			case 54:
				strFieldName = "GetYear";
				break;
			case 55:
				strFieldName = "GetStartType";
				break;
			case 56:
				strFieldName = "GetStartTypeName";
				break;
			case 57:
				strFieldName = "InAmount";
				break;
			case 58:
				strFieldName = "OutAmount";
				break;
			case 59:
				strFieldName = "ExpiryGetMode";
				break;
			case 60:
				strFieldName = "ExpiryGetModeName";
				break;
			case 61:
				strFieldName = "ExpiryPayMode";
				break;
			case 62:
				strFieldName = "ExpiryPayModeName";
				break;
			case 63:
				strFieldName = "TransMode";
				break;
			case 64:
				strFieldName = "TransModeName";
				break;
			case 65:
				strFieldName = "TransAmount";
				break;
			case 66:
				strFieldName = "AnnuityGetMode";
				break;
			case 67:
				strFieldName = "AnnuityGetModeName";
				break;
			case 68:
				strFieldName = "WorkIDNo";
				break;
			case 69:
				strFieldName = "IsLongValid";
				break;
			case 70:
				strFieldName = "IsLongValidName";
				break;
			case 71:
				strFieldName = "IDEndDate";
				break;
			case 72:
				strFieldName = "SubCompanyCode";
				break;
			case 73:
				strFieldName = "DeptCode";
				break;
			case 74:
				strFieldName = "InsureCode";
				break;
			case 75:
				strFieldName = "SubCustomerNo";
				break;
			case 76:
				strFieldName = "SubCustomerName";
				break;
			case 77:
				strFieldName = "WorkAddress";
				break;
			case 78:
				strFieldName = "SocialInsuAddress";
				break;
			case 79:
				strFieldName = "ZipCode";
				break;
			case 80:
				strFieldName = "EMail";
				break;
			case 81:
				strFieldName = "Wechat";
				break;
			case 82:
				strFieldName = "Phone";
				break;
			case 83:
				strFieldName = "Mobile";
				break;
			case 84:
				strFieldName = "Province";
				break;
			case 85:
				strFieldName = "ProvinceName";
				break;
			case 86:
				strFieldName = "City";
				break;
			case 87:
				strFieldName = "CityName";
				break;
			case 88:
				strFieldName = "County";
				break;
			case 89:
				strFieldName = "CountyName";
				break;
			case 90:
				strFieldName = "Address";
				break;
			case 91:
				strFieldName = "OriginContNo";
				break;
			case 92:
				strFieldName = "OriginInsuredNo";
				break;
			case 93:
				strFieldName = "State";
				break;
			case 94:
				strFieldName = "Reason";
				break;
			case 95:
				strFieldName = "ManageCom";
				break;
			case 96:
				strFieldName = "ComCode";
				break;
			case 97:
				strFieldName = "MakeOperator";
				break;
			case 98:
				strFieldName = "MakeDate";
				break;
			case 99:
				strFieldName = "MakeTime";
				break;
			case 100:
				strFieldName = "ModifyOperator";
				break;
			case 101:
				strFieldName = "ModifyDate";
				break;
			case 102:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GroupNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GroupOrderNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NumPeople") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToMain") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToMainName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainInsuredID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainInsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainInsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldInsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldInsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredGender") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredGenderName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorValDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OccupName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HeadBankName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankProvinceName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCityName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerArea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ServerAreaName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Substandard") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubstandardName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuFlagName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Position") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PositionName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JoinCompDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Seniority") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Salary") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetYear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetStartType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetStartTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InAmount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutAmount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryGetModeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryPayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryPayModeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransModeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransAmount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AnnuityGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AnnuityGetModeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsLongValid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsLongValidName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDEndDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCompanyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeptCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsureCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SocialInsuAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Wechat") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Province") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProvinceName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("City") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CityName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("County") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CountyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriginContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriginInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 99:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
