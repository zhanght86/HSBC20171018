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
import com.sinosoft.lis.db.LCGrpPolBakDB;

/*
 * <p>ClassName: LCGrpPolBakSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCGrpPolBakSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpPolBakSchema.class);

	// @Field
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 总单/合同号码 */
	private String ContNo;
	/** 集体投保单号码 */
	private String GrpProposalNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 险类编码 */
	private String KindCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 签单机构 */
	private String SignCom;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 保单口令 */
	private String Password;
	/** 单位编码 */
	private String GrpNo;
	/** 密码 */
	private String Password2;
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
	/** 投保总人数 */
	private int Peoples2;
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
	/** 交费间隔 */
	private int PayIntv;
	/** 交费方式 */
	private String PayMode;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 保单送达日期 */
	private Date GetPolDate;
	/** 签单日期 */
	private Date SignDate;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 交至日期 */
	private Date PaytoDate;
	/** 最后一次催收日期 */
	private Date RegetDate;
	/** 总人数 */
	private int Peoples;
	/** 份数 */
	private double Mult;
	/** 总保费 */
	private double Prem;
	/** 总保额 */
	private double Amnt;
	/** 累计保费 */
	private double SumPrem;
	/** 累计交费 */
	private double SumPay;
	/** 差额 */
	private double Dif;
	/** 社保标记 */
	private String SSFlag;
	/** 封顶线 */
	private double PeakLine;
	/** 起付限 */
	private double GetLimit;
	/** 赔付比例 */
	private double GetRate;
	/** 医疗费用限额 */
	private double MaxMedFee;
	/** 预计人数 */
	private int ExpPeoples;
	/** 预计保费 */
	private double ExpPremium;
	/** 预计保额 */
	private double ExpAmnt;
	/** 合同争议处理方式 */
	private String DisputedFlag;
	/** 分红比率 */
	private double BonusRate;
	/** 语种标记 */
	private String Lang;
	/** 币别 */
	private String Currency;
	/** 状态 */
	private String State;
	/** 遗失补发次数 */
	private int LostTimes;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 核保人 */
	private String UWOperator;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 备注 */
	private String Remark;
	/** 核保状态 */
	private String UWFlag;
	/** 溢交处理方式 */
	private String OutPayFlag;
	/** 复核状态 */
	private String ApproveFlag;
	/** 雇员自付比例 */
	private double EmployeeRate;
	/** 家属自付比例 */
	private double FamilyRate;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 银行帐户名 */
	private String AccName;
	/** 保单打印次数 */
	private int PrintCount;
	/** 最后一次保全日期 */
	private Date LastEdorDate;
	/** 管理费比例 */
	private double ManageFeeRate;
	/** 集体特约 */
	private String GrpSpec;
	/** 保单送达方式 */
	private String GetPolMode;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 团体特殊业务标志 */
	private String SpecFlag;

	public static final int FIELDNUM = 109;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpPolBakSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpPolNo";

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
		LCGrpPolBakSchema cloned = (LCGrpPolBakSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		GrpProposalNo = aGrpProposalNo;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	public String getSignCom()
	{
		return SignCom;
	}
	public void setSignCom(String aSignCom)
	{
		SignCom = aSignCom;
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
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
	}
	/**
	* 01-团险直销,02-个人营销,03-银行代理<p>
	* 04-兼业代理,05-专业代理,06-经纪公司<p>
	* 07-不计业绩销售渠道,99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	public String getGrpNo()
	{
		return GrpNo;
	}
	public void setGrpNo(String aGrpNo)
	{
		GrpNo = aGrpNo;
	}
	public String getPassword2()
	{
		return Password2;
	}
	public void setPassword2(String aPassword2)
	{
		Password2 = aPassword2;
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
	public int getPeoples2()
	{
		return Peoples2;
	}
	public void setPeoples2(int aPeoples2)
	{
		Peoples2 = aPeoples2;
	}
	public void setPeoples2(String aPeoples2)
	{
		if (aPeoples2 != null && !aPeoples2.equals(""))
		{
			Integer tInteger = new Integer(aPeoples2);
			int i = tInteger.intValue();
			Peoples2 = i;
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
	/**
	* 交费间隔<p>
	* -1 -- 不定期交,<p>
	* 0  -- 趸交,<p>
	* 1  -- 月交<p>
	* 3  -- 季交<p>
	* 6  -- 半年交<p>
	* 12 -- 年交
	*/
	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* 1	现金<p>
	* 2	现金支票<p>
	* 3	转账支票<p>
	* 4	银行转账<p>
	* 5	内部转帐<p>
	* 6	银行托收<p>
	* 7	其他
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	public String getGetPolDate()
	{
		if( GetPolDate != null )
			return fDate.getString(GetPolDate);
		else
			return null;
	}
	public void setGetPolDate(Date aGetPolDate)
	{
		GetPolDate = aGetPolDate;
	}
	public void setGetPolDate(String aGetPolDate)
	{
		if (aGetPolDate != null && !aGetPolDate.equals("") )
		{
			GetPolDate = fDate.getDate( aGetPolDate );
		}
		else
			GetPolDate = null;
	}

	public String getSignDate()
	{
		if( SignDate != null )
			return fDate.getString(SignDate);
		else
			return null;
	}
	public void setSignDate(Date aSignDate)
	{
		SignDate = aSignDate;
	}
	public void setSignDate(String aSignDate)
	{
		if (aSignDate != null && !aSignDate.equals("") )
		{
			SignDate = fDate.getDate( aSignDate );
		}
		else
			SignDate = null;
	}

	public String getFirstPayDate()
	{
		if( FirstPayDate != null )
			return fDate.getString(FirstPayDate);
		else
			return null;
	}
	public void setFirstPayDate(Date aFirstPayDate)
	{
		FirstPayDate = aFirstPayDate;
	}
	public void setFirstPayDate(String aFirstPayDate)
	{
		if (aFirstPayDate != null && !aFirstPayDate.equals("") )
		{
			FirstPayDate = fDate.getDate( aFirstPayDate );
		}
		else
			FirstPayDate = null;
	}

	public String getPayEndDate()
	{
		if( PayEndDate != null )
			return fDate.getString(PayEndDate);
		else
			return null;
	}
	public void setPayEndDate(Date aPayEndDate)
	{
		PayEndDate = aPayEndDate;
	}
	public void setPayEndDate(String aPayEndDate)
	{
		if (aPayEndDate != null && !aPayEndDate.equals("") )
		{
			PayEndDate = fDate.getDate( aPayEndDate );
		}
		else
			PayEndDate = null;
	}

	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	public String getRegetDate()
	{
		if( RegetDate != null )
			return fDate.getString(RegetDate);
		else
			return null;
	}
	public void setRegetDate(Date aRegetDate)
	{
		RegetDate = aRegetDate;
	}
	public void setRegetDate(String aRegetDate)
	{
		if (aRegetDate != null && !aRegetDate.equals("") )
		{
			RegetDate = fDate.getDate( aRegetDate );
		}
		else
			RegetDate = null;
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

	public double getMult()
	{
		return Mult;
	}
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}

	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	public double getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	public double getSumPay()
	{
		return SumPay;
	}
	public void setSumPay(double aSumPay)
	{
		SumPay = aSumPay;
	}
	public void setSumPay(String aSumPay)
	{
		if (aSumPay != null && !aSumPay.equals(""))
		{
			Double tDouble = new Double(aSumPay);
			double d = tDouble.doubleValue();
			SumPay = d;
		}
	}

	public double getDif()
	{
		return Dif;
	}
	public void setDif(double aDif)
	{
		Dif = aDif;
	}
	public void setDif(String aDif)
	{
		if (aDif != null && !aDif.equals(""))
		{
			Double tDouble = new Double(aDif);
			double d = tDouble.doubleValue();
			Dif = d;
		}
	}

	public String getSSFlag()
	{
		return SSFlag;
	}
	public void setSSFlag(String aSSFlag)
	{
		SSFlag = aSSFlag;
	}
	public double getPeakLine()
	{
		return PeakLine;
	}
	public void setPeakLine(double aPeakLine)
	{
		PeakLine = aPeakLine;
	}
	public void setPeakLine(String aPeakLine)
	{
		if (aPeakLine != null && !aPeakLine.equals(""))
		{
			Double tDouble = new Double(aPeakLine);
			double d = tDouble.doubleValue();
			PeakLine = d;
		}
	}

	public double getGetLimit()
	{
		return GetLimit;
	}
	public void setGetLimit(double aGetLimit)
	{
		GetLimit = aGetLimit;
	}
	public void setGetLimit(String aGetLimit)
	{
		if (aGetLimit != null && !aGetLimit.equals(""))
		{
			Double tDouble = new Double(aGetLimit);
			double d = tDouble.doubleValue();
			GetLimit = d;
		}
	}

	public double getGetRate()
	{
		return GetRate;
	}
	public void setGetRate(double aGetRate)
	{
		GetRate = aGetRate;
	}
	public void setGetRate(String aGetRate)
	{
		if (aGetRate != null && !aGetRate.equals(""))
		{
			Double tDouble = new Double(aGetRate);
			double d = tDouble.doubleValue();
			GetRate = d;
		}
	}

	public double getMaxMedFee()
	{
		return MaxMedFee;
	}
	public void setMaxMedFee(double aMaxMedFee)
	{
		MaxMedFee = aMaxMedFee;
	}
	public void setMaxMedFee(String aMaxMedFee)
	{
		if (aMaxMedFee != null && !aMaxMedFee.equals(""))
		{
			Double tDouble = new Double(aMaxMedFee);
			double d = tDouble.doubleValue();
			MaxMedFee = d;
		}
	}

	public int getExpPeoples()
	{
		return ExpPeoples;
	}
	public void setExpPeoples(int aExpPeoples)
	{
		ExpPeoples = aExpPeoples;
	}
	public void setExpPeoples(String aExpPeoples)
	{
		if (aExpPeoples != null && !aExpPeoples.equals(""))
		{
			Integer tInteger = new Integer(aExpPeoples);
			int i = tInteger.intValue();
			ExpPeoples = i;
		}
	}

	public double getExpPremium()
	{
		return ExpPremium;
	}
	public void setExpPremium(double aExpPremium)
	{
		ExpPremium = aExpPremium;
	}
	public void setExpPremium(String aExpPremium)
	{
		if (aExpPremium != null && !aExpPremium.equals(""))
		{
			Double tDouble = new Double(aExpPremium);
			double d = tDouble.doubleValue();
			ExpPremium = d;
		}
	}

	public double getExpAmnt()
	{
		return ExpAmnt;
	}
	public void setExpAmnt(double aExpAmnt)
	{
		ExpAmnt = aExpAmnt;
	}
	public void setExpAmnt(String aExpAmnt)
	{
		if (aExpAmnt != null && !aExpAmnt.equals(""))
		{
			Double tDouble = new Double(aExpAmnt);
			double d = tDouble.doubleValue();
			ExpAmnt = d;
		}
	}

	public String getDisputedFlag()
	{
		return DisputedFlag;
	}
	public void setDisputedFlag(String aDisputedFlag)
	{
		DisputedFlag = aDisputedFlag;
	}
	public double getBonusRate()
	{
		return BonusRate;
	}
	public void setBonusRate(double aBonusRate)
	{
		BonusRate = aBonusRate;
	}
	public void setBonusRate(String aBonusRate)
	{
		if (aBonusRate != null && !aBonusRate.equals(""))
		{
			Double tDouble = new Double(aBonusRate);
			double d = tDouble.doubleValue();
			BonusRate = d;
		}
	}

	public String getLang()
	{
		return Lang;
	}
	public void setLang(String aLang)
	{
		Lang = aLang;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	/**
	* [1-保障计划标记]<p>
	* [2]-计算方向<p>
	*     1-保费算保额<p>
	*     3-保额算保费)<p>
	* [3]-交费标记<p>
	*    M－月，D－日，Y－年
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public int getLostTimes()
	{
		return LostTimes;
	}
	public void setLostTimes(int aLostTimes)
	{
		LostTimes = aLostTimes;
	}
	public void setLostTimes(String aLostTimes)
	{
		if (aLostTimes != null && !aLostTimes.equals(""))
		{
			Integer tInteger = new Integer(aLostTimes);
			int i = tInteger.intValue();
			LostTimes = i;
		}
	}

	/**
	* 0－投保,1－承保,2-续保期间
	*/
	public String getAppFlag()
	{
		return AppFlag;
	}
	public void setAppFlag(String aAppFlag)
	{
		AppFlag = aAppFlag;
	}
	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		ApproveCode = aApproveCode;
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

	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
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
	public String getAgentCode1()
	{
		return AgentCode1;
	}
	public void setAgentCode1(String aAgentCode1)
	{
		AgentCode1 = aAgentCode1;
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
	* 1 拒保 <p>
	* 2 延期 <p>
	* 3 条件承保 <p>
	* 4 通融 <p>
	* 5 自动 <p>
	* 6 待上级 <p>
	* 7 问题件 <p>
	* 8 核保通知书 <p>
	* 9 正常 <p>
	* a 撤单 <p>
	* b 保险计划变更<p>
	* z 核保订正
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	/**
	* 1---退费<p>
	* 2---充当续期保费
	*/
	public String getOutPayFlag()
	{
		return OutPayFlag;
	}
	public void setOutPayFlag(String aOutPayFlag)
	{
		OutPayFlag = aOutPayFlag;
	}
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		ApproveFlag = aApproveFlag;
	}
	public double getEmployeeRate()
	{
		return EmployeeRate;
	}
	public void setEmployeeRate(double aEmployeeRate)
	{
		EmployeeRate = aEmployeeRate;
	}
	public void setEmployeeRate(String aEmployeeRate)
	{
		if (aEmployeeRate != null && !aEmployeeRate.equals(""))
		{
			Double tDouble = new Double(aEmployeeRate);
			double d = tDouble.doubleValue();
			EmployeeRate = d;
		}
	}

	public double getFamilyRate()
	{
		return FamilyRate;
	}
	public void setFamilyRate(double aFamilyRate)
	{
		FamilyRate = aFamilyRate;
	}
	public void setFamilyRate(String aFamilyRate)
	{
		if (aFamilyRate != null && !aFamilyRate.equals(""))
		{
			Double tDouble = new Double(aFamilyRate);
			double d = tDouble.doubleValue();
			FamilyRate = d;
		}
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
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public int getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(int aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		if (aPrintCount != null && !aPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aPrintCount);
			int i = tInteger.intValue();
			PrintCount = i;
		}
	}

	public String getLastEdorDate()
	{
		if( LastEdorDate != null )
			return fDate.getString(LastEdorDate);
		else
			return null;
	}
	public void setLastEdorDate(Date aLastEdorDate)
	{
		LastEdorDate = aLastEdorDate;
	}
	public void setLastEdorDate(String aLastEdorDate)
	{
		if (aLastEdorDate != null && !aLastEdorDate.equals("") )
		{
			LastEdorDate = fDate.getDate( aLastEdorDate );
		}
		else
			LastEdorDate = null;
	}

	public double getManageFeeRate()
	{
		return ManageFeeRate;
	}
	public void setManageFeeRate(double aManageFeeRate)
	{
		ManageFeeRate = aManageFeeRate;
	}
	public void setManageFeeRate(String aManageFeeRate)
	{
		if (aManageFeeRate != null && !aManageFeeRate.equals(""))
		{
			Double tDouble = new Double(aManageFeeRate);
			double d = tDouble.doubleValue();
			ManageFeeRate = d;
		}
	}

	public String getGrpSpec()
	{
		return GrpSpec;
	}
	public void setGrpSpec(String aGrpSpec)
	{
		GrpSpec = aGrpSpec;
	}
	/**
	* 0 -- 返回银行领取<p>
	* 1 -- 邮寄或专递
	*/
	public String getGetPolMode()
	{
		return GetPolMode;
	}
	public void setGetPolMode(String aGetPolMode)
	{
		GetPolMode = aGetPolMode;
	}
	public String getPolApplyDate()
	{
		if( PolApplyDate != null )
			return fDate.getString(PolApplyDate);
		else
			return null;
	}
	public void setPolApplyDate(Date aPolApplyDate)
	{
		PolApplyDate = aPolApplyDate;
	}
	public void setPolApplyDate(String aPolApplyDate)
	{
		if (aPolApplyDate != null && !aPolApplyDate.equals("") )
		{
			PolApplyDate = fDate.getDate( aPolApplyDate );
		}
		else
			PolApplyDate = null;
	}

	public String getCustomGetPolDate()
	{
		if( CustomGetPolDate != null )
			return fDate.getString(CustomGetPolDate);
		else
			return null;
	}
	public void setCustomGetPolDate(Date aCustomGetPolDate)
	{
		CustomGetPolDate = aCustomGetPolDate;
	}
	public void setCustomGetPolDate(String aCustomGetPolDate)
	{
		if (aCustomGetPolDate != null && !aCustomGetPolDate.equals("") )
		{
			CustomGetPolDate = fDate.getDate( aCustomGetPolDate );
		}
		else
			CustomGetPolDate = null;
	}

	/**
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* 对于险种编码：311603，存放的是同心卡的开卡日期
	*/
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据<p>
	* 银代险的内部优惠标志：（该字段对所有银代险有效）<p>
	* 1 默认 其他<p>
	*/
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	/**
	* 根据不同险种的特殊要求，存放不同的数据
	*/
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	/**
	* 1。其它（一般是合同分保）<p>
	* 2。临分<p>
	* 3。自留
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		SpecFlag = aSpecFlag;
	}

	/**
	* 使用另外一个 LCGrpPolBakSchema 对象给 Schema 赋值
	* @param: aLCGrpPolBakSchema LCGrpPolBakSchema
	**/
	public void setSchema(LCGrpPolBakSchema aLCGrpPolBakSchema)
	{
		this.GrpPolNo = aLCGrpPolBakSchema.getGrpPolNo();
		this.ContNo = aLCGrpPolBakSchema.getContNo();
		this.GrpProposalNo = aLCGrpPolBakSchema.getGrpProposalNo();
		this.PrtNo = aLCGrpPolBakSchema.getPrtNo();
		this.KindCode = aLCGrpPolBakSchema.getKindCode();
		this.RiskCode = aLCGrpPolBakSchema.getRiskCode();
		this.RiskVersion = aLCGrpPolBakSchema.getRiskVersion();
		this.SignCom = aLCGrpPolBakSchema.getSignCom();
		this.ManageCom = aLCGrpPolBakSchema.getManageCom();
		this.AgentCom = aLCGrpPolBakSchema.getAgentCom();
		this.AgentType = aLCGrpPolBakSchema.getAgentType();
		this.SaleChnl = aLCGrpPolBakSchema.getSaleChnl();
		this.Password = aLCGrpPolBakSchema.getPassword();
		this.GrpNo = aLCGrpPolBakSchema.getGrpNo();
		this.Password2 = aLCGrpPolBakSchema.getPassword2();
		this.GrpName = aLCGrpPolBakSchema.getGrpName();
		this.GrpAddressCode = aLCGrpPolBakSchema.getGrpAddressCode();
		this.GrpAddress = aLCGrpPolBakSchema.getGrpAddress();
		this.GrpZipCode = aLCGrpPolBakSchema.getGrpZipCode();
		this.BusinessType = aLCGrpPolBakSchema.getBusinessType();
		this.GrpNature = aLCGrpPolBakSchema.getGrpNature();
		this.Peoples2 = aLCGrpPolBakSchema.getPeoples2();
		this.RgtMoney = aLCGrpPolBakSchema.getRgtMoney();
		this.Asset = aLCGrpPolBakSchema.getAsset();
		this.NetProfitRate = aLCGrpPolBakSchema.getNetProfitRate();
		this.MainBussiness = aLCGrpPolBakSchema.getMainBussiness();
		this.Corporation = aLCGrpPolBakSchema.getCorporation();
		this.ComAera = aLCGrpPolBakSchema.getComAera();
		this.LinkMan1 = aLCGrpPolBakSchema.getLinkMan1();
		this.Department1 = aLCGrpPolBakSchema.getDepartment1();
		this.HeadShip1 = aLCGrpPolBakSchema.getHeadShip1();
		this.Phone1 = aLCGrpPolBakSchema.getPhone1();
		this.E_Mail1 = aLCGrpPolBakSchema.getE_Mail1();
		this.Fax1 = aLCGrpPolBakSchema.getFax1();
		this.LinkMan2 = aLCGrpPolBakSchema.getLinkMan2();
		this.Department2 = aLCGrpPolBakSchema.getDepartment2();
		this.HeadShip2 = aLCGrpPolBakSchema.getHeadShip2();
		this.Phone2 = aLCGrpPolBakSchema.getPhone2();
		this.E_Mail2 = aLCGrpPolBakSchema.getE_Mail2();
		this.Fax2 = aLCGrpPolBakSchema.getFax2();
		this.Fax = aLCGrpPolBakSchema.getFax();
		this.Phone = aLCGrpPolBakSchema.getPhone();
		this.GetFlag = aLCGrpPolBakSchema.getGetFlag();
		this.Satrap = aLCGrpPolBakSchema.getSatrap();
		this.EMail = aLCGrpPolBakSchema.getEMail();
		this.FoundDate = fDate.getDate( aLCGrpPolBakSchema.getFoundDate());
		this.BankAccNo = aLCGrpPolBakSchema.getBankAccNo();
		this.BankCode = aLCGrpPolBakSchema.getBankCode();
		this.GrpGroupNo = aLCGrpPolBakSchema.getGrpGroupNo();
		this.PayIntv = aLCGrpPolBakSchema.getPayIntv();
		this.PayMode = aLCGrpPolBakSchema.getPayMode();
		this.CValiDate = fDate.getDate( aLCGrpPolBakSchema.getCValiDate());
		this.GetPolDate = fDate.getDate( aLCGrpPolBakSchema.getGetPolDate());
		this.SignDate = fDate.getDate( aLCGrpPolBakSchema.getSignDate());
		this.FirstPayDate = fDate.getDate( aLCGrpPolBakSchema.getFirstPayDate());
		this.PayEndDate = fDate.getDate( aLCGrpPolBakSchema.getPayEndDate());
		this.PaytoDate = fDate.getDate( aLCGrpPolBakSchema.getPaytoDate());
		this.RegetDate = fDate.getDate( aLCGrpPolBakSchema.getRegetDate());
		this.Peoples = aLCGrpPolBakSchema.getPeoples();
		this.Mult = aLCGrpPolBakSchema.getMult();
		this.Prem = aLCGrpPolBakSchema.getPrem();
		this.Amnt = aLCGrpPolBakSchema.getAmnt();
		this.SumPrem = aLCGrpPolBakSchema.getSumPrem();
		this.SumPay = aLCGrpPolBakSchema.getSumPay();
		this.Dif = aLCGrpPolBakSchema.getDif();
		this.SSFlag = aLCGrpPolBakSchema.getSSFlag();
		this.PeakLine = aLCGrpPolBakSchema.getPeakLine();
		this.GetLimit = aLCGrpPolBakSchema.getGetLimit();
		this.GetRate = aLCGrpPolBakSchema.getGetRate();
		this.MaxMedFee = aLCGrpPolBakSchema.getMaxMedFee();
		this.ExpPeoples = aLCGrpPolBakSchema.getExpPeoples();
		this.ExpPremium = aLCGrpPolBakSchema.getExpPremium();
		this.ExpAmnt = aLCGrpPolBakSchema.getExpAmnt();
		this.DisputedFlag = aLCGrpPolBakSchema.getDisputedFlag();
		this.BonusRate = aLCGrpPolBakSchema.getBonusRate();
		this.Lang = aLCGrpPolBakSchema.getLang();
		this.Currency = aLCGrpPolBakSchema.getCurrency();
		this.State = aLCGrpPolBakSchema.getState();
		this.LostTimes = aLCGrpPolBakSchema.getLostTimes();
		this.AppFlag = aLCGrpPolBakSchema.getAppFlag();
		this.ApproveCode = aLCGrpPolBakSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLCGrpPolBakSchema.getApproveDate());
		this.UWOperator = aLCGrpPolBakSchema.getUWOperator();
		this.AgentCode = aLCGrpPolBakSchema.getAgentCode();
		this.AgentGroup = aLCGrpPolBakSchema.getAgentGroup();
		this.AgentCode1 = aLCGrpPolBakSchema.getAgentCode1();
		this.Remark = aLCGrpPolBakSchema.getRemark();
		this.UWFlag = aLCGrpPolBakSchema.getUWFlag();
		this.OutPayFlag = aLCGrpPolBakSchema.getOutPayFlag();
		this.ApproveFlag = aLCGrpPolBakSchema.getApproveFlag();
		this.EmployeeRate = aLCGrpPolBakSchema.getEmployeeRate();
		this.FamilyRate = aLCGrpPolBakSchema.getFamilyRate();
		this.Operator = aLCGrpPolBakSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpPolBakSchema.getMakeDate());
		this.MakeTime = aLCGrpPolBakSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpPolBakSchema.getModifyDate());
		this.ModifyTime = aLCGrpPolBakSchema.getModifyTime();
		this.AccName = aLCGrpPolBakSchema.getAccName();
		this.PrintCount = aLCGrpPolBakSchema.getPrintCount();
		this.LastEdorDate = fDate.getDate( aLCGrpPolBakSchema.getLastEdorDate());
		this.ManageFeeRate = aLCGrpPolBakSchema.getManageFeeRate();
		this.GrpSpec = aLCGrpPolBakSchema.getGrpSpec();
		this.GetPolMode = aLCGrpPolBakSchema.getGetPolMode();
		this.PolApplyDate = fDate.getDate( aLCGrpPolBakSchema.getPolApplyDate());
		this.CustomGetPolDate = fDate.getDate( aLCGrpPolBakSchema.getCustomGetPolDate());
		this.StandbyFlag1 = aLCGrpPolBakSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCGrpPolBakSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLCGrpPolBakSchema.getStandbyFlag3();
		this.SpecFlag = aLCGrpPolBakSchema.getSpecFlag();
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
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

			if( rs.getString("Password2") == null )
				this.Password2 = null;
			else
				this.Password2 = rs.getString("Password2").trim();

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

			this.Peoples2 = rs.getInt("Peoples2");
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

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.GetPolDate = rs.getDate("GetPolDate");
			this.SignDate = rs.getDate("SignDate");
			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.RegetDate = rs.getDate("RegetDate");
			this.Peoples = rs.getInt("Peoples");
			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.SumPrem = rs.getDouble("SumPrem");
			this.SumPay = rs.getDouble("SumPay");
			this.Dif = rs.getDouble("Dif");
			if( rs.getString("SSFlag") == null )
				this.SSFlag = null;
			else
				this.SSFlag = rs.getString("SSFlag").trim();

			this.PeakLine = rs.getDouble("PeakLine");
			this.GetLimit = rs.getDouble("GetLimit");
			this.GetRate = rs.getDouble("GetRate");
			this.MaxMedFee = rs.getDouble("MaxMedFee");
			this.ExpPeoples = rs.getInt("ExpPeoples");
			this.ExpPremium = rs.getDouble("ExpPremium");
			this.ExpAmnt = rs.getDouble("ExpAmnt");
			if( rs.getString("DisputedFlag") == null )
				this.DisputedFlag = null;
			else
				this.DisputedFlag = rs.getString("DisputedFlag").trim();

			this.BonusRate = rs.getDouble("BonusRate");
			if( rs.getString("Lang") == null )
				this.Lang = null;
			else
				this.Lang = rs.getString("Lang").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.LostTimes = rs.getInt("LostTimes");
			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode1") == null )
				this.AgentCode1 = null;
			else
				this.AgentCode1 = rs.getString("AgentCode1").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("OutPayFlag") == null )
				this.OutPayFlag = null;
			else
				this.OutPayFlag = rs.getString("OutPayFlag").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			this.EmployeeRate = rs.getDouble("EmployeeRate");
			this.FamilyRate = rs.getDouble("FamilyRate");
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

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.PrintCount = rs.getInt("PrintCount");
			this.LastEdorDate = rs.getDate("LastEdorDate");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			if( rs.getString("GrpSpec") == null )
				this.GrpSpec = null;
			else
				this.GrpSpec = rs.getString("GrpSpec").trim();

			if( rs.getString("GetPolMode") == null )
				this.GetPolMode = null;
			else
				this.GetPolMode = rs.getString("GetPolMode").trim();

			this.PolApplyDate = rs.getDate("PolApplyDate");
			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpPolBak表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpPolBakSchema getSchema()
	{
		LCGrpPolBakSchema aLCGrpPolBakSchema = new LCGrpPolBakSchema();
		aLCGrpPolBakSchema.setSchema(this);
		return aLCGrpPolBakSchema;
	}

	public LCGrpPolBakDB getDB()
	{
		LCGrpPolBakDB aDBOper = new LCGrpPolBakDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpPolBak描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples2));strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RegetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Dif));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SSFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PeakLine));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxMedFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPremium));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisputedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Lang)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LostTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EmployeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FamilyRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastEdorDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpSpec)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpPolBak>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Password2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			GrpAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			GrpAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			GrpZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			BusinessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Peoples2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			RgtMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			Asset = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			MainBussiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ComAera = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			LinkMan1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Department1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			HeadShip1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Phone1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			E_Mail1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Fax1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			LinkMan2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Department2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			HeadShip2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Phone2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			E_Mail2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Fax2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			GrpGroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,50,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53,SysConst.PACKAGESPILTER));
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54,SysConst.PACKAGESPILTER));
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56,SysConst.PACKAGESPILTER));
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57,SysConst.PACKAGESPILTER));
			RegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,64,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			SSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			PeakLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).doubleValue();
			GetLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,68,SysConst.PACKAGESPILTER))).doubleValue();
			GetRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).doubleValue();
			MaxMedFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).doubleValue();
			ExpPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).intValue();
			ExpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).doubleValue();
			ExpAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,73,SysConst.PACKAGESPILTER))).doubleValue();
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			BonusRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			LostTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,79,SysConst.PACKAGESPILTER))).intValue();
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82,SysConst.PACKAGESPILTER));
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			EmployeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,91,SysConst.PACKAGESPILTER))).doubleValue();
			FamilyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,92,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,99,SysConst.PACKAGESPILTER))).intValue();
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100,SysConst.PACKAGESPILTER));
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,101,SysConst.PACKAGESPILTER))).doubleValue();
			GrpSpec = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104,SysConst.PACKAGESPILTER));
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105,SysConst.PACKAGESPILTER));
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpPolBakSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignCom));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
		}
		if (FCode.equalsIgnoreCase("Password2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password2));
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
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples2));
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("RegetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
		}
		if (FCode.equalsIgnoreCase("Peoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("SumPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPay));
		}
		if (FCode.equalsIgnoreCase("Dif"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dif));
		}
		if (FCode.equalsIgnoreCase("SSFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SSFlag));
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeakLine));
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetLimit));
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetRate));
		}
		if (FCode.equalsIgnoreCase("MaxMedFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxMedFee));
		}
		if (FCode.equalsIgnoreCase("ExpPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpPeoples));
		}
		if (FCode.equalsIgnoreCase("ExpPremium"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpPremium));
		}
		if (FCode.equalsIgnoreCase("ExpAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpAmnt));
		}
		if (FCode.equalsIgnoreCase("DisputedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisputedFlag));
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusRate));
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Lang));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostTimes));
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode1));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("OutPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("EmployeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EmployeeRate));
		}
		if (FCode.equalsIgnoreCase("FamilyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyRate));
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
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
		}
		if (FCode.equalsIgnoreCase("GrpSpec"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpSpec));
		}
		if (FCode.equalsIgnoreCase("GetPolMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolMode));
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Password2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(GrpAddressCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(GrpAddress);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(GrpZipCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 21:
				strFieldValue = String.valueOf(Peoples2);
				break;
			case 22:
				strFieldValue = String.valueOf(RgtMoney);
				break;
			case 23:
				strFieldValue = String.valueOf(Asset);
				break;
			case 24:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MainBussiness);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ComAera);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(LinkMan1);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Department1);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(HeadShip1);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Phone1);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(E_Mail1);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Fax1);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(LinkMan2);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Department2);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(HeadShip2);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Phone2);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(E_Mail2);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Fax2);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(GrpGroupNo);
				break;
			case 49:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
				break;
			case 58:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 59:
				strFieldValue = String.valueOf(Mult);
				break;
			case 60:
				strFieldValue = String.valueOf(Prem);
				break;
			case 61:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 62:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 63:
				strFieldValue = String.valueOf(SumPay);
				break;
			case 64:
				strFieldValue = String.valueOf(Dif);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(SSFlag);
				break;
			case 66:
				strFieldValue = String.valueOf(PeakLine);
				break;
			case 67:
				strFieldValue = String.valueOf(GetLimit);
				break;
			case 68:
				strFieldValue = String.valueOf(GetRate);
				break;
			case 69:
				strFieldValue = String.valueOf(MaxMedFee);
				break;
			case 70:
				strFieldValue = String.valueOf(ExpPeoples);
				break;
			case 71:
				strFieldValue = String.valueOf(ExpPremium);
				break;
			case 72:
				strFieldValue = String.valueOf(ExpAmnt);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(DisputedFlag);
				break;
			case 74:
				strFieldValue = String.valueOf(BonusRate);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 78:
				strFieldValue = String.valueOf(LostTimes);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 90:
				strFieldValue = String.valueOf(EmployeeRate);
				break;
			case 91:
				strFieldValue = String.valueOf(FamilyRate);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 98:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 100:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(GrpSpec);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(GetPolMode);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
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

		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpProposalNo = FValue.trim();
			}
			else
				GrpProposalNo = null;
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignCom = FValue.trim();
			}
			else
				SignCom = null;
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
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
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
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNo = FValue.trim();
			}
			else
				GrpNo = null;
		}
		if (FCode.equalsIgnoreCase("Password2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password2 = FValue.trim();
			}
			else
				Password2 = null;
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
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples2 = i;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetPolDate = fDate.getDate( FValue );
			}
			else
				GetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayEndDate = fDate.getDate( FValue );
			}
			else
				PayEndDate = null;
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
		}
		if (FCode.equalsIgnoreCase("RegetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RegetDate = fDate.getDate( FValue );
			}
			else
				RegetDate = null;
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
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("Dif"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Dif = d;
			}
		}
		if (FCode.equalsIgnoreCase("SSFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SSFlag = FValue.trim();
			}
			else
				SSFlag = null;
		}
		if (FCode.equalsIgnoreCase("PeakLine"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PeakLine = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxMedFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxMedFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ExpPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("ExpPremium"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpPremium = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExpAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExpAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("DisputedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisputedFlag = FValue.trim();
			}
			else
				DisputedFlag = null;
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Lang = FValue.trim();
			}
			else
				Lang = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
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
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LostTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveCode = FValue.trim();
			}
			else
				ApproveCode = null;
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
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
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
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode1 = FValue.trim();
			}
			else
				AgentCode1 = null;
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
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("OutPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutPayFlag = FValue.trim();
			}
			else
				OutPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
		}
		if (FCode.equalsIgnoreCase("EmployeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EmployeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FamilyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FamilyRate = d;
			}
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
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastEdorDate = fDate.getDate( FValue );
			}
			else
				LastEdorDate = null;
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ManageFeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("GrpSpec"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpSpec = FValue.trim();
			}
			else
				GrpSpec = null;
		}
		if (FCode.equalsIgnoreCase("GetPolMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolMode = FValue.trim();
			}
			else
				GetPolMode = null;
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PolApplyDate = fDate.getDate( FValue );
			}
			else
				PolApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomGetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecFlag = FValue.trim();
			}
			else
				SpecFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpPolBakSchema other = (LCGrpPolBakSchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& PrtNo.equals(other.getPrtNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& SignCom.equals(other.getSignCom())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Password.equals(other.getPassword())
			&& GrpNo.equals(other.getGrpNo())
			&& Password2.equals(other.getPassword2())
			&& GrpName.equals(other.getGrpName())
			&& GrpAddressCode.equals(other.getGrpAddressCode())
			&& GrpAddress.equals(other.getGrpAddress())
			&& GrpZipCode.equals(other.getGrpZipCode())
			&& BusinessType.equals(other.getBusinessType())
			&& GrpNature.equals(other.getGrpNature())
			&& Peoples2 == other.getPeoples2()
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
			&& PayIntv == other.getPayIntv()
			&& PayMode.equals(other.getPayMode())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(RegetDate).equals(other.getRegetDate())
			&& Peoples == other.getPeoples()
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& SumPrem == other.getSumPrem()
			&& SumPay == other.getSumPay()
			&& Dif == other.getDif()
			&& SSFlag.equals(other.getSSFlag())
			&& PeakLine == other.getPeakLine()
			&& GetLimit == other.getGetLimit()
			&& GetRate == other.getGetRate()
			&& MaxMedFee == other.getMaxMedFee()
			&& ExpPeoples == other.getExpPeoples()
			&& ExpPremium == other.getExpPremium()
			&& ExpAmnt == other.getExpAmnt()
			&& DisputedFlag.equals(other.getDisputedFlag())
			&& BonusRate == other.getBonusRate()
			&& Lang.equals(other.getLang())
			&& Currency.equals(other.getCurrency())
			&& State.equals(other.getState())
			&& LostTimes == other.getLostTimes()
			&& AppFlag.equals(other.getAppFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& UWOperator.equals(other.getUWOperator())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& Remark.equals(other.getRemark())
			&& UWFlag.equals(other.getUWFlag())
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& EmployeeRate == other.getEmployeeRate()
			&& FamilyRate == other.getFamilyRate()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AccName.equals(other.getAccName())
			&& PrintCount == other.getPrintCount()
			&& fDate.getString(LastEdorDate).equals(other.getLastEdorDate())
			&& ManageFeeRate == other.getManageFeeRate()
			&& GrpSpec.equals(other.getGrpSpec())
			&& GetPolMode.equals(other.getGetPolMode())
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& SpecFlag.equals(other.getSpecFlag());
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("KindCode") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 6;
		}
		if( strFieldName.equals("SignCom") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 9;
		}
		if( strFieldName.equals("AgentType") ) {
			return 10;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 11;
		}
		if( strFieldName.equals("Password") ) {
			return 12;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 13;
		}
		if( strFieldName.equals("Password2") ) {
			return 14;
		}
		if( strFieldName.equals("GrpName") ) {
			return 15;
		}
		if( strFieldName.equals("GrpAddressCode") ) {
			return 16;
		}
		if( strFieldName.equals("GrpAddress") ) {
			return 17;
		}
		if( strFieldName.equals("GrpZipCode") ) {
			return 18;
		}
		if( strFieldName.equals("BusinessType") ) {
			return 19;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 20;
		}
		if( strFieldName.equals("Peoples2") ) {
			return 21;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return 22;
		}
		if( strFieldName.equals("Asset") ) {
			return 23;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 24;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return 25;
		}
		if( strFieldName.equals("Corporation") ) {
			return 26;
		}
		if( strFieldName.equals("ComAera") ) {
			return 27;
		}
		if( strFieldName.equals("LinkMan1") ) {
			return 28;
		}
		if( strFieldName.equals("Department1") ) {
			return 29;
		}
		if( strFieldName.equals("HeadShip1") ) {
			return 30;
		}
		if( strFieldName.equals("Phone1") ) {
			return 31;
		}
		if( strFieldName.equals("E_Mail1") ) {
			return 32;
		}
		if( strFieldName.equals("Fax1") ) {
			return 33;
		}
		if( strFieldName.equals("LinkMan2") ) {
			return 34;
		}
		if( strFieldName.equals("Department2") ) {
			return 35;
		}
		if( strFieldName.equals("HeadShip2") ) {
			return 36;
		}
		if( strFieldName.equals("Phone2") ) {
			return 37;
		}
		if( strFieldName.equals("E_Mail2") ) {
			return 38;
		}
		if( strFieldName.equals("Fax2") ) {
			return 39;
		}
		if( strFieldName.equals("Fax") ) {
			return 40;
		}
		if( strFieldName.equals("Phone") ) {
			return 41;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 42;
		}
		if( strFieldName.equals("Satrap") ) {
			return 43;
		}
		if( strFieldName.equals("EMail") ) {
			return 44;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 45;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 46;
		}
		if( strFieldName.equals("BankCode") ) {
			return 47;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return 48;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 49;
		}
		if( strFieldName.equals("PayMode") ) {
			return 50;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 51;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 52;
		}
		if( strFieldName.equals("SignDate") ) {
			return 53;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 54;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 55;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 56;
		}
		if( strFieldName.equals("RegetDate") ) {
			return 57;
		}
		if( strFieldName.equals("Peoples") ) {
			return 58;
		}
		if( strFieldName.equals("Mult") ) {
			return 59;
		}
		if( strFieldName.equals("Prem") ) {
			return 60;
		}
		if( strFieldName.equals("Amnt") ) {
			return 61;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 62;
		}
		if( strFieldName.equals("SumPay") ) {
			return 63;
		}
		if( strFieldName.equals("Dif") ) {
			return 64;
		}
		if( strFieldName.equals("SSFlag") ) {
			return 65;
		}
		if( strFieldName.equals("PeakLine") ) {
			return 66;
		}
		if( strFieldName.equals("GetLimit") ) {
			return 67;
		}
		if( strFieldName.equals("GetRate") ) {
			return 68;
		}
		if( strFieldName.equals("MaxMedFee") ) {
			return 69;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return 70;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return 71;
		}
		if( strFieldName.equals("ExpAmnt") ) {
			return 72;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return 73;
		}
		if( strFieldName.equals("BonusRate") ) {
			return 74;
		}
		if( strFieldName.equals("Lang") ) {
			return 75;
		}
		if( strFieldName.equals("Currency") ) {
			return 76;
		}
		if( strFieldName.equals("State") ) {
			return 77;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 78;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 79;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 80;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 81;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 82;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 83;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 84;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 85;
		}
		if( strFieldName.equals("Remark") ) {
			return 86;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 87;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 88;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 89;
		}
		if( strFieldName.equals("EmployeeRate") ) {
			return 90;
		}
		if( strFieldName.equals("FamilyRate") ) {
			return 91;
		}
		if( strFieldName.equals("Operator") ) {
			return 92;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 93;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 94;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 95;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 96;
		}
		if( strFieldName.equals("AccName") ) {
			return 97;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 98;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 99;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 100;
		}
		if( strFieldName.equals("GrpSpec") ) {
			return 101;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return 102;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 103;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 104;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 105;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 106;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 107;
		}
		if( strFieldName.equals("SpecFlag") ) {
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "GrpProposalNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "KindCode";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "RiskVersion";
				break;
			case 7:
				strFieldName = "SignCom";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "AgentCom";
				break;
			case 10:
				strFieldName = "AgentType";
				break;
			case 11:
				strFieldName = "SaleChnl";
				break;
			case 12:
				strFieldName = "Password";
				break;
			case 13:
				strFieldName = "GrpNo";
				break;
			case 14:
				strFieldName = "Password2";
				break;
			case 15:
				strFieldName = "GrpName";
				break;
			case 16:
				strFieldName = "GrpAddressCode";
				break;
			case 17:
				strFieldName = "GrpAddress";
				break;
			case 18:
				strFieldName = "GrpZipCode";
				break;
			case 19:
				strFieldName = "BusinessType";
				break;
			case 20:
				strFieldName = "GrpNature";
				break;
			case 21:
				strFieldName = "Peoples2";
				break;
			case 22:
				strFieldName = "RgtMoney";
				break;
			case 23:
				strFieldName = "Asset";
				break;
			case 24:
				strFieldName = "NetProfitRate";
				break;
			case 25:
				strFieldName = "MainBussiness";
				break;
			case 26:
				strFieldName = "Corporation";
				break;
			case 27:
				strFieldName = "ComAera";
				break;
			case 28:
				strFieldName = "LinkMan1";
				break;
			case 29:
				strFieldName = "Department1";
				break;
			case 30:
				strFieldName = "HeadShip1";
				break;
			case 31:
				strFieldName = "Phone1";
				break;
			case 32:
				strFieldName = "E_Mail1";
				break;
			case 33:
				strFieldName = "Fax1";
				break;
			case 34:
				strFieldName = "LinkMan2";
				break;
			case 35:
				strFieldName = "Department2";
				break;
			case 36:
				strFieldName = "HeadShip2";
				break;
			case 37:
				strFieldName = "Phone2";
				break;
			case 38:
				strFieldName = "E_Mail2";
				break;
			case 39:
				strFieldName = "Fax2";
				break;
			case 40:
				strFieldName = "Fax";
				break;
			case 41:
				strFieldName = "Phone";
				break;
			case 42:
				strFieldName = "GetFlag";
				break;
			case 43:
				strFieldName = "Satrap";
				break;
			case 44:
				strFieldName = "EMail";
				break;
			case 45:
				strFieldName = "FoundDate";
				break;
			case 46:
				strFieldName = "BankAccNo";
				break;
			case 47:
				strFieldName = "BankCode";
				break;
			case 48:
				strFieldName = "GrpGroupNo";
				break;
			case 49:
				strFieldName = "PayIntv";
				break;
			case 50:
				strFieldName = "PayMode";
				break;
			case 51:
				strFieldName = "CValiDate";
				break;
			case 52:
				strFieldName = "GetPolDate";
				break;
			case 53:
				strFieldName = "SignDate";
				break;
			case 54:
				strFieldName = "FirstPayDate";
				break;
			case 55:
				strFieldName = "PayEndDate";
				break;
			case 56:
				strFieldName = "PaytoDate";
				break;
			case 57:
				strFieldName = "RegetDate";
				break;
			case 58:
				strFieldName = "Peoples";
				break;
			case 59:
				strFieldName = "Mult";
				break;
			case 60:
				strFieldName = "Prem";
				break;
			case 61:
				strFieldName = "Amnt";
				break;
			case 62:
				strFieldName = "SumPrem";
				break;
			case 63:
				strFieldName = "SumPay";
				break;
			case 64:
				strFieldName = "Dif";
				break;
			case 65:
				strFieldName = "SSFlag";
				break;
			case 66:
				strFieldName = "PeakLine";
				break;
			case 67:
				strFieldName = "GetLimit";
				break;
			case 68:
				strFieldName = "GetRate";
				break;
			case 69:
				strFieldName = "MaxMedFee";
				break;
			case 70:
				strFieldName = "ExpPeoples";
				break;
			case 71:
				strFieldName = "ExpPremium";
				break;
			case 72:
				strFieldName = "ExpAmnt";
				break;
			case 73:
				strFieldName = "DisputedFlag";
				break;
			case 74:
				strFieldName = "BonusRate";
				break;
			case 75:
				strFieldName = "Lang";
				break;
			case 76:
				strFieldName = "Currency";
				break;
			case 77:
				strFieldName = "State";
				break;
			case 78:
				strFieldName = "LostTimes";
				break;
			case 79:
				strFieldName = "AppFlag";
				break;
			case 80:
				strFieldName = "ApproveCode";
				break;
			case 81:
				strFieldName = "ApproveDate";
				break;
			case 82:
				strFieldName = "UWOperator";
				break;
			case 83:
				strFieldName = "AgentCode";
				break;
			case 84:
				strFieldName = "AgentGroup";
				break;
			case 85:
				strFieldName = "AgentCode1";
				break;
			case 86:
				strFieldName = "Remark";
				break;
			case 87:
				strFieldName = "UWFlag";
				break;
			case 88:
				strFieldName = "OutPayFlag";
				break;
			case 89:
				strFieldName = "ApproveFlag";
				break;
			case 90:
				strFieldName = "EmployeeRate";
				break;
			case 91:
				strFieldName = "FamilyRate";
				break;
			case 92:
				strFieldName = "Operator";
				break;
			case 93:
				strFieldName = "MakeDate";
				break;
			case 94:
				strFieldName = "MakeTime";
				break;
			case 95:
				strFieldName = "ModifyDate";
				break;
			case 96:
				strFieldName = "ModifyTime";
				break;
			case 97:
				strFieldName = "AccName";
				break;
			case 98:
				strFieldName = "PrintCount";
				break;
			case 99:
				strFieldName = "LastEdorDate";
				break;
			case 100:
				strFieldName = "ManageFeeRate";
				break;
			case 101:
				strFieldName = "GrpSpec";
				break;
			case 102:
				strFieldName = "GetPolMode";
				break;
			case 103:
				strFieldName = "PolApplyDate";
				break;
			case 104:
				strFieldName = "CustomGetPolDate";
				break;
			case 105:
				strFieldName = "StandbyFlag1";
				break;
			case 106:
				strFieldName = "StandbyFlag2";
				break;
			case 107:
				strFieldName = "StandbyFlag3";
				break;
			case 108:
				strFieldName = "SpecFlag";
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password2") ) {
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
		if( strFieldName.equals("Peoples2") ) {
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
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RegetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Peoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Dif") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SSFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeakLine") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxMedFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExpAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Lang") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LostTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EmployeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FamilyRate") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GrpSpec") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 52:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 53:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 54:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 57:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 58:
				nFieldType = Schema.TYPE_INT;
				break;
			case 59:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 60:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 61:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 62:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 63:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 64:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 67:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 68:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 69:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 70:
				nFieldType = Schema.TYPE_INT;
				break;
			case 71:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 91:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 94:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 95:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 96:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 97:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 98:
				nFieldType = Schema.TYPE_INT;
				break;
			case 99:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 100:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 104:
				nFieldType = Schema.TYPE_DATE;
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
