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
import com.sinosoft.lis.db.LBGrpContDB;

/*
 * <p>ClassName: LBGrpContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_13
 */
public class LBGrpContSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBGrpContSchema.class);
	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体投保单号码 */
	private String ProposalGrpContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 销售渠道 */
	private String SaleChnl;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 保单口令 */
	private String Password;
	/** 密码 */
	private String Password2;
	/** 客户号码 */
	private String AppntNo;
	/** 地址号码 */
	private String AddressNo;
	/** 投保总人数 */
	private int Peoples2;
	/** 单位名称 */
	private String GrpName;
	/** 行业分类 */
	private String BusinessType;
	/** 单位性质 */
	private String GrpNature;
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
	/** 客户组号码 */
	private String GrpGroupNo;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 合同争议处理方式 */
	private String DisputedFlag;
	/** 溢交处理方式 */
	private String OutPayFlag;
	/** 保单送达方式 */
	private String GetPolMode;
	/** 语种标记 */
	private String Lang;
	/** 币别 */
	private String Currency;
	/** 遗失补发次数 */
	private int LostTimes;
	/** 保单打印次数 */
	private int PrintCount;
	/** 最后一次催收日期 */
	private Date RegetDate;
	/** 最后一次保全日期 */
	private Date LastEdorDate;
	/** 最后一次给付日期 */
	private Date LastGetDate;
	/** 最后一次借款日期 */
	private Date LastLoanDate;
	/** 团体特殊业务标志 */
	private String SpecFlag;
	/** 集体特约 */
	private String GrpSpec;
	/** 交费方式 */
	private String PayMode;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private Date SignDate;
	/** 签单时间 */
	private String SignTime;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 交费间隔 */
	private int PayIntv;
	/** 管理费比例 */
	private double ManageFeeRate;
	/** 预计人数 */
	private int ExpPeoples;
	/** 预计保费 */
	private double ExpPremium;
	/** 预计保额 */
	private double ExpAmnt;
	/** 总人数 */
	private int Peoples;
	/** 总份数 */
	private double Mult;
	/** 总保费 */
	private double Prem;
	/** 总保额 */
	private double Amnt;
	/** 总累计保费 */
	private double SumPrem;
	/** 总累计交费 */
	private double SumPay;
	/** 差额 */
	private double Dif;
	/** 备注 */
	private String Remark;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 录单人 */
	private String InputOperator;
	/** 录单完成日期 */
	private Date InputDate;
	/** 录单完成时间 */
	private String InputTime;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 核保人 */
	private String UWOperator;
	/** 核保状态 */
	private String UWFlag;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
	/** 保单送达日期 */
	private Date GetPolDate;
	/** 保单送达时间 */
	private String GetPolTime;
	/** 状态 */
	private String State;
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
	/** 参保形式 */
	private String EnterKind;
	/** 保额等级 */
	private String AmntGrade;
	/** 单位可投保人数 */
	private int Peoples3;
	/** 在职投保人数 */
	private int OnWorkPeoples;
	/** 退休投保人数 */
	private int OffWorkPeoples;
	/** 其它投保人数 */
	private int OtherPeoples;
	/** 连带投保人数 */
	private int RelaPeoples;
	/** 连带配偶投保人数 */
	private int RelaMatePeoples;
	/** 连带子女投保人数 */
	private int RelaYoungPeoples;
	/** 连带其它投保人数 */
	private int RelaOtherPeoples;
	/** 初审人 */
	private String FirstTrialOperator;
	/** 初审日期 */
	private Date FirstTrialDate;
	/** 初审时间 */
	private String FirstTrialTime;
	/** 收单人 */
	private String ReceiveOperator;
	/** 收单日期 */
	private Date ReceiveDate;
	/** 收单时间 */
	private String ReceiveTime;
	/** 暂收据号 */
	private String TempFeeNo;
	/** 投保经办人 */
	private String HandlerName;
	/** 投保单填写日期 */
	private Date HandlerDate;
	/** 投保单位章 */
	private String HandlerPrint;
	/** 业务员填写日期 */
	private Date AgentDate;
	/** 行业大类 */
	private String BusinessBigType;
	/** 市场类型 */
	private String MarketType;
	/** 呈报号 */
	private String ReportNo;
	/** 协议书号 */
	private String ConferNo;
	/** 签报件号 */
	private String SignReportNo;
	/** 代理协议书号 */
	private String AgentConferNo;
	/** 总单类型 */
	private String ContType;
	/** 是否使用保全特殊算法 */
	private String EdorCalType;
	/** 客户关注 */
	private String ClientCare;
	/** 资金说明 */
	private String FundReason;
	/** 追溯期理赔特约 */
	private String BackDateRemark;
	/** 客户需求判断 */
	private String ClientNeedJudge;
	/** 是否赠送团单 */
	private String DonateContflag;
	/** 资金判断 */
	private String FundJudge;
	/** 审批号码 */
	private String ExamAndAppNo;
	/** 综拓专员编码 */
	private String AgentCodeOper;
	/** 综拓助理编码 */
	private String AgentCodeAssi;
	/** 延迟送达原因代码 */
	private String DelayReasonCode;
	/** 延迟送达原因 */
	private String DelayReasonDesc;

	public static final int FIELDNUM = 131;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBGrpContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GrpContNo";

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
		LBGrpContSchema cloned = (LBGrpContSchema)super.clone();
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
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		if(aProposalGrpContNo!=null && aProposalGrpContNo.length()>20)
			throw new IllegalArgumentException("集体投保单号码ProposalGrpContNo值"+aProposalGrpContNo+"的长度"+aProposalGrpContNo.length()+"大于最大值20");
		ProposalGrpContNo = aProposalGrpContNo;
	}
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
		if(aSaleChnl!=null && aSaleChnl.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值2");
		SaleChnl = aSaleChnl;
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
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		if(aAgentCom!=null && aAgentCom.length()>20)
			throw new IllegalArgumentException("代理机构AgentCom值"+aAgentCom+"的长度"+aAgentCom.length()+"大于最大值20");
		AgentCom = aAgentCom;
	}
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		if(aAgentType!=null && aAgentType.length()>20)
			throw new IllegalArgumentException("代理机构内部分类AgentType值"+aAgentType+"的长度"+aAgentType.length()+"大于最大值20");
		AgentType = aAgentType;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		if(aAgentCode!=null && aAgentCode.length()>10)
			throw new IllegalArgumentException("代理人编码AgentCode值"+aAgentCode+"的长度"+aAgentCode.length()+"大于最大值10");
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		if(aAgentGroup!=null && aAgentGroup.length()>12)
			throw new IllegalArgumentException("代理人组别AgentGroup值"+aAgentGroup+"的长度"+aAgentGroup.length()+"大于最大值12");
		AgentGroup = aAgentGroup;
	}
	public String getAgentCode1()
	{
		return AgentCode1;
	}
	public void setAgentCode1(String aAgentCode1)
	{
		if(aAgentCode1!=null && aAgentCode1.length()>10)
			throw new IllegalArgumentException("联合代理人代码AgentCode1值"+aAgentCode1+"的长度"+aAgentCode1.length()+"大于最大值10");
		AgentCode1 = aAgentCode1;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		if(aPassword!=null && aPassword.length()>16)
			throw new IllegalArgumentException("保单口令Password值"+aPassword+"的长度"+aPassword.length()+"大于最大值16");
		Password = aPassword;
	}
	public String getPassword2()
	{
		return Password2;
	}
	public void setPassword2(String aPassword2)
	{
		if(aPassword2!=null && aPassword2.length()>16)
			throw new IllegalArgumentException("密码Password2值"+aPassword2+"的长度"+aPassword2.length()+"大于最大值16");
		Password2 = aPassword2;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("客户号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public String getAddressNo()
	{
		return AddressNo;
	}
	public void setAddressNo(String aAddressNo)
	{
		if(aAddressNo!=null && aAddressNo.length()>20)
			throw new IllegalArgumentException("地址号码AddressNo值"+aAddressNo+"的长度"+aAddressNo.length()+"大于最大值20");
		AddressNo = aAddressNo;
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

	/**
	* 冗余，标准在团体客户表
	*/
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>60)
			throw new IllegalArgumentException("单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值60");
		GrpName = aGrpName;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getBusinessType()
	{
		return BusinessType;
	}
	public void setBusinessType(String aBusinessType)
	{
		if(aBusinessType!=null && aBusinessType.length()>20)
			throw new IllegalArgumentException("行业分类BusinessType值"+aBusinessType+"的长度"+aBusinessType.length()+"大于最大值20");
		BusinessType = aBusinessType;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getGrpNature()
	{
		return GrpNature;
	}
	public void setGrpNature(String aGrpNature)
	{
		if(aGrpNature!=null && aGrpNature.length()>10)
			throw new IllegalArgumentException("单位性质GrpNature值"+aGrpNature+"的长度"+aGrpNature.length()+"大于最大值10");
		GrpNature = aGrpNature;
	}
	/**
	* 冗余，标准在团体客户表
	*/
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

	/**
	* 冗余，标准在团体客户表
	*/
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

	/**
	* 冗余，标准在团体客户表
	*/
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

	/**
	* 冗余，标准在团体客户表
	*/
	public String getMainBussiness()
	{
		return MainBussiness;
	}
	public void setMainBussiness(String aMainBussiness)
	{
		if(aMainBussiness!=null && aMainBussiness.length()>60)
			throw new IllegalArgumentException("主营业务MainBussiness值"+aMainBussiness+"的长度"+aMainBussiness.length()+"大于最大值60");
		MainBussiness = aMainBussiness;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getCorporation()
	{
		return Corporation;
	}
	public void setCorporation(String aCorporation)
	{
		if(aCorporation!=null && aCorporation.length()>20)
			throw new IllegalArgumentException("法人Corporation值"+aCorporation+"的长度"+aCorporation.length()+"大于最大值20");
		Corporation = aCorporation;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getComAera()
	{
		return ComAera;
	}
	public void setComAera(String aComAera)
	{
		if(aComAera!=null && aComAera.length()>30)
			throw new IllegalArgumentException("机构分布区域ComAera值"+aComAera+"的长度"+aComAera.length()+"大于最大值30");
		ComAera = aComAera;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getFax()
	{
		return Fax;
	}
	public void setFax(String aFax)
	{
		if(aFax!=null && aFax.length()>18)
			throw new IllegalArgumentException("单位传真Fax值"+aFax+"的长度"+aFax.length()+"大于最大值18");
		Fax = aFax;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		if(aPhone!=null && aPhone.length()>18)
			throw new IllegalArgumentException("单位电话Phone值"+aPhone+"的长度"+aPhone.length()+"大于最大值18");
		Phone = aPhone;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		if(aGetFlag!=null && aGetFlag.length()>1)
			throw new IllegalArgumentException("付款方式GetFlag值"+aGetFlag+"的长度"+aGetFlag.length()+"大于最大值1");
		GetFlag = aGetFlag;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getSatrap()
	{
		return Satrap;
	}
	public void setSatrap(String aSatrap)
	{
		if(aSatrap!=null && aSatrap.length()>10)
			throw new IllegalArgumentException("负责人Satrap值"+aSatrap+"的长度"+aSatrap.length()+"大于最大值10");
		Satrap = aSatrap;
	}
	/**
	* 冗余，标准在团体客户表
	*/
	public String getEMail()
	{
		return EMail;
	}
	public void setEMail(String aEMail)
	{
		if(aEMail!=null && aEMail.length()>20)
			throw new IllegalArgumentException("公司e_mailEMail值"+aEMail+"的长度"+aEMail.length()+"大于最大值20");
		EMail = aEMail;
	}
	/**
	* 冗余，标准在团体客户表
	*/
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

	/**
	* 冗余，标准在团体客户表<p>
	* 将团体的客户编组
	*/
	public String getGrpGroupNo()
	{
		return GrpGroupNo;
	}
	public void setGrpGroupNo(String aGrpGroupNo)
	{
		if(aGrpGroupNo!=null && aGrpGroupNo.length()>10)
			throw new IllegalArgumentException("客户组号码GrpGroupNo值"+aGrpGroupNo+"的长度"+aGrpGroupNo.length()+"大于最大值10");
		GrpGroupNo = aGrpGroupNo;
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
	public String getDisputedFlag()
	{
		return DisputedFlag;
	}
	public void setDisputedFlag(String aDisputedFlag)
	{
		if(aDisputedFlag!=null && aDisputedFlag.length()>1)
			throw new IllegalArgumentException("合同争议处理方式DisputedFlag值"+aDisputedFlag+"的长度"+aDisputedFlag.length()+"大于最大值1");
		DisputedFlag = aDisputedFlag;
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
		if(aOutPayFlag!=null && aOutPayFlag.length()>1)
			throw new IllegalArgumentException("溢交处理方式OutPayFlag值"+aOutPayFlag+"的长度"+aOutPayFlag.length()+"大于最大值1");
		OutPayFlag = aOutPayFlag;
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
		if(aGetPolMode!=null && aGetPolMode.length()>1)
			throw new IllegalArgumentException("保单送达方式GetPolMode值"+aGetPolMode+"的长度"+aGetPolMode.length()+"大于最大值1");
		GetPolMode = aGetPolMode;
	}
	public String getLang()
	{
		return Lang;
	}
	public void setLang(String aLang)
	{
		if(aLang!=null && aLang.length()>1)
			throw new IllegalArgumentException("语种标记Lang值"+aLang+"的长度"+aLang.length()+"大于最大值1");
		Lang = aLang;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>2)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值2");
		Currency = aCurrency;
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

	public String getLastGetDate()
	{
		if( LastGetDate != null )
			return fDate.getString(LastGetDate);
		else
			return null;
	}
	public void setLastGetDate(Date aLastGetDate)
	{
		LastGetDate = aLastGetDate;
	}
	public void setLastGetDate(String aLastGetDate)
	{
		if (aLastGetDate != null && !aLastGetDate.equals("") )
		{
			LastGetDate = fDate.getDate( aLastGetDate );
		}
		else
			LastGetDate = null;
	}

	public String getLastLoanDate()
	{
		if( LastLoanDate != null )
			return fDate.getString(LastLoanDate);
		else
			return null;
	}
	public void setLastLoanDate(Date aLastLoanDate)
	{
		LastLoanDate = aLastLoanDate;
	}
	public void setLastLoanDate(String aLastLoanDate)
	{
		if (aLastLoanDate != null && !aLastLoanDate.equals("") )
		{
			LastLoanDate = fDate.getDate( aLastLoanDate );
		}
		else
			LastLoanDate = null;
	}

	/**
	* 根据团体内容的保密要求设定的权限字段
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		if(aSpecFlag!=null && aSpecFlag.length()>1)
			throw new IllegalArgumentException("团体特殊业务标志SpecFlag值"+aSpecFlag+"的长度"+aSpecFlag.length()+"大于最大值1");
		SpecFlag = aSpecFlag;
	}
	public String getGrpSpec()
	{
		return GrpSpec;
	}
	public void setGrpSpec(String aGrpSpec)
	{
		if(aGrpSpec!=null && aGrpSpec.length()>255)
			throw new IllegalArgumentException("集体特约GrpSpec值"+aGrpSpec+"的长度"+aGrpSpec.length()+"大于最大值255");
		GrpSpec = aGrpSpec;
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
		if(aPayMode!=null && aPayMode.length()>1)
			throw new IllegalArgumentException("交费方式PayMode值"+aPayMode+"的长度"+aPayMode.length()+"大于最大值1");
		PayMode = aPayMode;
	}
	public String getSignCom()
	{
		return SignCom;
	}
	public void setSignCom(String aSignCom)
	{
		if(aSignCom!=null && aSignCom.length()>10)
			throw new IllegalArgumentException("签单机构SignCom值"+aSignCom+"的长度"+aSignCom.length()+"大于最大值10");
		SignCom = aSignCom;
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

	public String getSignTime()
	{
		return SignTime;
	}
	public void setSignTime(String aSignTime)
	{
		if(aSignTime!=null && aSignTime.length()>8)
			throw new IllegalArgumentException("签单时间SignTime值"+aSignTime+"的长度"+aSignTime.length()+"大于最大值8");
		SignTime = aSignTime;
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

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>255)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值255");
		Remark = aRemark;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>10)
			throw new IllegalArgumentException("备用属性字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值10");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>10)
			throw new IllegalArgumentException("备用属性字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值10");
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		if(aStandbyFlag3!=null && aStandbyFlag3.length()>10)
			throw new IllegalArgumentException("备用属性字段3StandbyFlag3值"+aStandbyFlag3+"的长度"+aStandbyFlag3.length()+"大于最大值10");
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		if(aInputOperator!=null && aInputOperator.length()>10)
			throw new IllegalArgumentException("录单人InputOperator值"+aInputOperator+"的长度"+aInputOperator.length()+"大于最大值10");
		InputOperator = aInputOperator;
	}
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	public String getInputTime()
	{
		return InputTime;
	}
	public void setInputTime(String aInputTime)
	{
		if(aInputTime!=null && aInputTime.length()>8)
			throw new IllegalArgumentException("录单完成时间InputTime值"+aInputTime+"的长度"+aInputTime.length()+"大于最大值8");
		InputTime = aInputTime;
	}
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		if(aApproveFlag!=null && aApproveFlag.length()>1)
			throw new IllegalArgumentException("复核状态ApproveFlag值"+aApproveFlag+"的长度"+aApproveFlag.length()+"大于最大值1");
		ApproveFlag = aApproveFlag;
	}
	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		if(aApproveCode!=null && aApproveCode.length()>10)
			throw new IllegalArgumentException("复核人编码ApproveCode值"+aApproveCode+"的长度"+aApproveCode.length()+"大于最大值10");
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

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		if(aApproveTime!=null && aApproveTime.length()>8)
			throw new IllegalArgumentException("复核时间ApproveTime值"+aApproveTime+"的长度"+aApproveTime.length()+"大于最大值8");
		ApproveTime = aApproveTime;
	}
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		if(aUWOperator!=null && aUWOperator.length()>10)
			throw new IllegalArgumentException("核保人UWOperator值"+aUWOperator+"的长度"+aUWOperator.length()+"大于最大值10");
		UWOperator = aUWOperator;
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
	/**
	* 0－投保,1－承保,2-续保期间
	*/
	public String getAppFlag()
	{
		return AppFlag;
	}
	public void setAppFlag(String aAppFlag)
	{
		if(aAppFlag!=null && aAppFlag.length()>1)
			throw new IllegalArgumentException("投保单/保单标志AppFlag值"+aAppFlag+"的长度"+aAppFlag.length()+"大于最大值1");
		AppFlag = aAppFlag;
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

	public String getGetPolTime()
	{
		return GetPolTime;
	}
	public void setGetPolTime(String aGetPolTime)
	{
		if(aGetPolTime!=null && aGetPolTime.length()>8)
			throw new IllegalArgumentException("保单送达时间GetPolTime值"+aGetPolTime+"的长度"+aGetPolTime.length()+"大于最大值8");
		GetPolTime = aGetPolTime;
	}
	/**
	* [1-保障计划标记]<p>
	* [2]-计算方向<p>
	* 1-保费算保额<p>
	* 3-保额算保费)<p>
	* [3]-交费标记<p>
	* M－月，D－日，Y－年
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>10)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值10");
		State = aState;
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
	* 1、团体统一投保<p>
	* 2、成员自愿投保
	*/
	public String getEnterKind()
	{
		return EnterKind;
	}
	public void setEnterKind(String aEnterKind)
	{
		if(aEnterKind!=null && aEnterKind.length()>1)
			throw new IllegalArgumentException("参保形式EnterKind值"+aEnterKind+"的长度"+aEnterKind.length()+"大于最大值1");
		EnterKind = aEnterKind;
	}
	/**
	* 1、统一无区分<p>
	* 2、按职务级别<p>
	* 3、按工种类别<p>
	* 4、按服务年限<p>
	* 5、其它
	*/
	public String getAmntGrade()
	{
		return AmntGrade;
	}
	public void setAmntGrade(String aAmntGrade)
	{
		if(aAmntGrade!=null && aAmntGrade.length()>1)
			throw new IllegalArgumentException("保额等级AmntGrade值"+aAmntGrade+"的长度"+aAmntGrade.length()+"大于最大值1");
		AmntGrade = aAmntGrade;
	}
	public int getPeoples3()
	{
		return Peoples3;
	}
	public void setPeoples3(int aPeoples3)
	{
		Peoples3 = aPeoples3;
	}
	public void setPeoples3(String aPeoples3)
	{
		if (aPeoples3 != null && !aPeoples3.equals(""))
		{
			Integer tInteger = new Integer(aPeoples3);
			int i = tInteger.intValue();
			Peoples3 = i;
		}
	}

	public int getOnWorkPeoples()
	{
		return OnWorkPeoples;
	}
	public void setOnWorkPeoples(int aOnWorkPeoples)
	{
		OnWorkPeoples = aOnWorkPeoples;
	}
	public void setOnWorkPeoples(String aOnWorkPeoples)
	{
		if (aOnWorkPeoples != null && !aOnWorkPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOnWorkPeoples);
			int i = tInteger.intValue();
			OnWorkPeoples = i;
		}
	}

	public int getOffWorkPeoples()
	{
		return OffWorkPeoples;
	}
	public void setOffWorkPeoples(int aOffWorkPeoples)
	{
		OffWorkPeoples = aOffWorkPeoples;
	}
	public void setOffWorkPeoples(String aOffWorkPeoples)
	{
		if (aOffWorkPeoples != null && !aOffWorkPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOffWorkPeoples);
			int i = tInteger.intValue();
			OffWorkPeoples = i;
		}
	}

	public int getOtherPeoples()
	{
		return OtherPeoples;
	}
	public void setOtherPeoples(int aOtherPeoples)
	{
		OtherPeoples = aOtherPeoples;
	}
	public void setOtherPeoples(String aOtherPeoples)
	{
		if (aOtherPeoples != null && !aOtherPeoples.equals(""))
		{
			Integer tInteger = new Integer(aOtherPeoples);
			int i = tInteger.intValue();
			OtherPeoples = i;
		}
	}

	public int getRelaPeoples()
	{
		return RelaPeoples;
	}
	public void setRelaPeoples(int aRelaPeoples)
	{
		RelaPeoples = aRelaPeoples;
	}
	public void setRelaPeoples(String aRelaPeoples)
	{
		if (aRelaPeoples != null && !aRelaPeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaPeoples);
			int i = tInteger.intValue();
			RelaPeoples = i;
		}
	}

	public int getRelaMatePeoples()
	{
		return RelaMatePeoples;
	}
	public void setRelaMatePeoples(int aRelaMatePeoples)
	{
		RelaMatePeoples = aRelaMatePeoples;
	}
	public void setRelaMatePeoples(String aRelaMatePeoples)
	{
		if (aRelaMatePeoples != null && !aRelaMatePeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaMatePeoples);
			int i = tInteger.intValue();
			RelaMatePeoples = i;
		}
	}

	public int getRelaYoungPeoples()
	{
		return RelaYoungPeoples;
	}
	public void setRelaYoungPeoples(int aRelaYoungPeoples)
	{
		RelaYoungPeoples = aRelaYoungPeoples;
	}
	public void setRelaYoungPeoples(String aRelaYoungPeoples)
	{
		if (aRelaYoungPeoples != null && !aRelaYoungPeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaYoungPeoples);
			int i = tInteger.intValue();
			RelaYoungPeoples = i;
		}
	}

	public int getRelaOtherPeoples()
	{
		return RelaOtherPeoples;
	}
	public void setRelaOtherPeoples(int aRelaOtherPeoples)
	{
		RelaOtherPeoples = aRelaOtherPeoples;
	}
	public void setRelaOtherPeoples(String aRelaOtherPeoples)
	{
		if (aRelaOtherPeoples != null && !aRelaOtherPeoples.equals(""))
		{
			Integer tInteger = new Integer(aRelaOtherPeoples);
			int i = tInteger.intValue();
			RelaOtherPeoples = i;
		}
	}

	public String getFirstTrialOperator()
	{
		return FirstTrialOperator;
	}
	public void setFirstTrialOperator(String aFirstTrialOperator)
	{
		if(aFirstTrialOperator!=null && aFirstTrialOperator.length()>10)
			throw new IllegalArgumentException("初审人FirstTrialOperator值"+aFirstTrialOperator+"的长度"+aFirstTrialOperator.length()+"大于最大值10");
		FirstTrialOperator = aFirstTrialOperator;
	}
	public String getFirstTrialDate()
	{
		if( FirstTrialDate != null )
			return fDate.getString(FirstTrialDate);
		else
			return null;
	}
	public void setFirstTrialDate(Date aFirstTrialDate)
	{
		FirstTrialDate = aFirstTrialDate;
	}
	public void setFirstTrialDate(String aFirstTrialDate)
	{
		if (aFirstTrialDate != null && !aFirstTrialDate.equals("") )
		{
			FirstTrialDate = fDate.getDate( aFirstTrialDate );
		}
		else
			FirstTrialDate = null;
	}

	public String getFirstTrialTime()
	{
		return FirstTrialTime;
	}
	public void setFirstTrialTime(String aFirstTrialTime)
	{
		if(aFirstTrialTime!=null && aFirstTrialTime.length()>8)
			throw new IllegalArgumentException("初审时间FirstTrialTime值"+aFirstTrialTime+"的长度"+aFirstTrialTime.length()+"大于最大值8");
		FirstTrialTime = aFirstTrialTime;
	}
	public String getReceiveOperator()
	{
		return ReceiveOperator;
	}
	public void setReceiveOperator(String aReceiveOperator)
	{
		if(aReceiveOperator!=null && aReceiveOperator.length()>10)
			throw new IllegalArgumentException("收单人ReceiveOperator值"+aReceiveOperator+"的长度"+aReceiveOperator.length()+"大于最大值10");
		ReceiveOperator = aReceiveOperator;
	}
	public String getReceiveDate()
	{
		if( ReceiveDate != null )
			return fDate.getString(ReceiveDate);
		else
			return null;
	}
	public void setReceiveDate(Date aReceiveDate)
	{
		ReceiveDate = aReceiveDate;
	}
	public void setReceiveDate(String aReceiveDate)
	{
		if (aReceiveDate != null && !aReceiveDate.equals("") )
		{
			ReceiveDate = fDate.getDate( aReceiveDate );
		}
		else
			ReceiveDate = null;
	}

	public String getReceiveTime()
	{
		return ReceiveTime;
	}
	public void setReceiveTime(String aReceiveTime)
	{
		if(aReceiveTime!=null && aReceiveTime.length()>8)
			throw new IllegalArgumentException("收单时间ReceiveTime值"+aReceiveTime+"的长度"+aReceiveTime.length()+"大于最大值8");
		ReceiveTime = aReceiveTime;
	}
	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		if(aTempFeeNo!=null && aTempFeeNo.length()>20)
			throw new IllegalArgumentException("暂收据号TempFeeNo值"+aTempFeeNo+"的长度"+aTempFeeNo.length()+"大于最大值20");
		TempFeeNo = aTempFeeNo;
	}
	public String getHandlerName()
	{
		return HandlerName;
	}
	public void setHandlerName(String aHandlerName)
	{
		if(aHandlerName!=null && aHandlerName.length()>60)
			throw new IllegalArgumentException("投保经办人HandlerName值"+aHandlerName+"的长度"+aHandlerName.length()+"大于最大值60");
		HandlerName = aHandlerName;
	}
	public String getHandlerDate()
	{
		if( HandlerDate != null )
			return fDate.getString(HandlerDate);
		else
			return null;
	}
	public void setHandlerDate(Date aHandlerDate)
	{
		HandlerDate = aHandlerDate;
	}
	public void setHandlerDate(String aHandlerDate)
	{
		if (aHandlerDate != null && !aHandlerDate.equals("") )
		{
			HandlerDate = fDate.getDate( aHandlerDate );
		}
		else
			HandlerDate = null;
	}

	public String getHandlerPrint()
	{
		return HandlerPrint;
	}
	public void setHandlerPrint(String aHandlerPrint)
	{
		if(aHandlerPrint!=null && aHandlerPrint.length()>120)
			throw new IllegalArgumentException("投保单位章HandlerPrint值"+aHandlerPrint+"的长度"+aHandlerPrint.length()+"大于最大值120");
		HandlerPrint = aHandlerPrint;
	}
	public String getAgentDate()
	{
		if( AgentDate != null )
			return fDate.getString(AgentDate);
		else
			return null;
	}
	public void setAgentDate(Date aAgentDate)
	{
		AgentDate = aAgentDate;
	}
	public void setAgentDate(String aAgentDate)
	{
		if (aAgentDate != null && !aAgentDate.equals("") )
		{
			AgentDate = fDate.getDate( aAgentDate );
		}
		else
			AgentDate = null;
	}

	public String getBusinessBigType()
	{
		return BusinessBigType;
	}
	public void setBusinessBigType(String aBusinessBigType)
	{
		if(aBusinessBigType!=null && aBusinessBigType.length()>2)
			throw new IllegalArgumentException("行业大类BusinessBigType值"+aBusinessBigType+"的长度"+aBusinessBigType.length()+"大于最大值2");
		BusinessBigType = aBusinessBigType;
	}
	public String getMarketType()
	{
		return MarketType;
	}
	public void setMarketType(String aMarketType)
	{
		if(aMarketType!=null && aMarketType.length()>2)
			throw new IllegalArgumentException("市场类型MarketType值"+aMarketType+"的长度"+aMarketType.length()+"大于最大值2");
		MarketType = aMarketType;
	}
	public String getReportNo()
	{
		return ReportNo;
	}
	public void setReportNo(String aReportNo)
	{
		if(aReportNo!=null && aReportNo.length()>20)
			throw new IllegalArgumentException("呈报号ReportNo值"+aReportNo+"的长度"+aReportNo.length()+"大于最大值20");
		ReportNo = aReportNo;
	}
	public String getConferNo()
	{
		return ConferNo;
	}
	public void setConferNo(String aConferNo)
	{
		if(aConferNo!=null && aConferNo.length()>20)
			throw new IllegalArgumentException("协议书号ConferNo值"+aConferNo+"的长度"+aConferNo.length()+"大于最大值20");
		ConferNo = aConferNo;
	}
	public String getSignReportNo()
	{
		return SignReportNo;
	}
	public void setSignReportNo(String aSignReportNo)
	{
		if(aSignReportNo!=null && aSignReportNo.length()>20)
			throw new IllegalArgumentException("签报件号SignReportNo值"+aSignReportNo+"的长度"+aSignReportNo.length()+"大于最大值20");
		SignReportNo = aSignReportNo;
	}
	public String getAgentConferNo()
	{
		return AgentConferNo;
	}
	public void setAgentConferNo(String aAgentConferNo)
	{
		if(aAgentConferNo!=null && aAgentConferNo.length()>20)
			throw new IllegalArgumentException("代理协议书号AgentConferNo值"+aAgentConferNo+"的长度"+aAgentConferNo.length()+"大于最大值20");
		AgentConferNo = aAgentConferNo;
	}
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		if(aContType!=null && aContType.length()>1)
			throw new IllegalArgumentException("总单类型ContType值"+aContType+"的长度"+aContType.length()+"大于最大值1");
		ContType = aContType;
	}
	/**
	* Y-是<p>
	* N-否
	*/
	public String getEdorCalType()
	{
		return EdorCalType;
	}
	public void setEdorCalType(String aEdorCalType)
	{
		if(aEdorCalType!=null && aEdorCalType.length()>2)
			throw new IllegalArgumentException("是否使用保全特殊算法EdorCalType值"+aEdorCalType+"的长度"+aEdorCalType.length()+"大于最大值2");
		EdorCalType = aEdorCalType;
	}
	/**
	* 客户关注内容：保障功能 投资收益（选项）；<p>
	* E(ensure)-保障功能<p>
	* I(invest)-投资收益
	*/
	public String getClientCare()
	{
		return ClientCare;
	}
	public void setClientCare(String aClientCare)
	{
		if(aClientCare!=null && aClientCare.length()>20)
			throw new IllegalArgumentException("客户关注ClientCare值"+aClientCare+"的长度"+aClientCare.length()+"大于最大值20");
		ClientCare = aClientCare;
	}
	public String getFundReason()
	{
		return FundReason;
	}
	public void setFundReason(String aFundReason)
	{
		if(aFundReason!=null && aFundReason.length()>1000)
			throw new IllegalArgumentException("资金说明FundReason值"+aFundReason+"的长度"+aFundReason.length()+"大于最大值1000");
		FundReason = aFundReason;
	}
	/**
	* 生效日期早于签单日期的说明。<p>
	* <p>
	* 系统保单录入界面-投保单位资料增加和修改的时候，判定填写的生效日期是否早于系统日期，如果早于系统日期，则提示“保单的生效日期存在追溯情况，请在保单的备注处注明追溯期间的理赔特别约定”，要求在备注里面BackDateRemark录入对应的约定信息，保单打印的时候显示。
	*/
	public String getBackDateRemark()
	{
		return BackDateRemark;
	}
	public void setBackDateRemark(String aBackDateRemark)
	{
		if(aBackDateRemark!=null && aBackDateRemark.length()>1000)
			throw new IllegalArgumentException("追溯期理赔特约BackDateRemark值"+aBackDateRemark+"的长度"+aBackDateRemark.length()+"大于最大值1000");
		BackDateRemark = aBackDateRemark;
	}
	/**
	* 投保单购买的产品与客户表述需求是否一致，Y：一致，N：不一致
	*/
	public String getClientNeedJudge()
	{
		return ClientNeedJudge;
	}
	public void setClientNeedJudge(String aClientNeedJudge)
	{
		if(aClientNeedJudge!=null && aClientNeedJudge.length()>2)
			throw new IllegalArgumentException("客户需求判断ClientNeedJudge值"+aClientNeedJudge+"的长度"+aClientNeedJudge.length()+"大于最大值2");
		ClientNeedJudge = aClientNeedJudge;
	}
	/**
	* 0 - 不是赠送团单<p>
	* 1 - 是赠送团单
	*/
	public String getDonateContflag()
	{
		return DonateContflag;
	}
	public void setDonateContflag(String aDonateContflag)
	{
		if(aDonateContflag!=null && aDonateContflag.length()>4)
			throw new IllegalArgumentException("是否赠送团单DonateContflag值"+aDonateContflag+"的长度"+aDonateContflag.length()+"大于最大值4");
		DonateContflag = aDonateContflag;
	}
	/**
	* 资金是否由投保人提供（判断）否说明原因<p>
	* Y-是，N-不是
	*/
	public String getFundJudge()
	{
		return FundJudge;
	}
	public void setFundJudge(String aFundJudge)
	{
		if(aFundJudge!=null && aFundJudge.length()>2)
			throw new IllegalArgumentException("资金判断FundJudge值"+aFundJudge+"的长度"+aFundJudge.length()+"大于最大值2");
		FundJudge = aFundJudge;
	}
	public String getExamAndAppNo()
	{
		return ExamAndAppNo;
	}
	public void setExamAndAppNo(String aExamAndAppNo)
	{
		if(aExamAndAppNo!=null && aExamAndAppNo.length()>20)
			throw new IllegalArgumentException("审批号码ExamAndAppNo值"+aExamAndAppNo+"的长度"+aExamAndAppNo.length()+"大于最大值20");
		ExamAndAppNo = aExamAndAppNo;
	}
	public String getAgentCodeOper()
	{
		return AgentCodeOper;
	}
	public void setAgentCodeOper(String aAgentCodeOper)
	{
		if(aAgentCodeOper!=null && aAgentCodeOper.length()>10)
			throw new IllegalArgumentException("综拓专员编码AgentCodeOper值"+aAgentCodeOper+"的长度"+aAgentCodeOper.length()+"大于最大值10");
		AgentCodeOper = aAgentCodeOper;
	}
	public String getAgentCodeAssi()
	{
		return AgentCodeAssi;
	}
	public void setAgentCodeAssi(String aAgentCodeAssi)
	{
		if(aAgentCodeAssi!=null && aAgentCodeAssi.length()>10)
			throw new IllegalArgumentException("综拓助理编码AgentCodeAssi值"+aAgentCodeAssi+"的长度"+aAgentCodeAssi.length()+"大于最大值10");
		AgentCodeAssi = aAgentCodeAssi;
	}
	public String getDelayReasonCode()
	{
		return DelayReasonCode;
	}
	public void setDelayReasonCode(String aDelayReasonCode)
	{
		if(aDelayReasonCode!=null && aDelayReasonCode.length()>1)
			throw new IllegalArgumentException("延迟送达原因代码DelayReasonCode值"+aDelayReasonCode+"的长度"+aDelayReasonCode.length()+"大于最大值1");
		DelayReasonCode = aDelayReasonCode;
	}
	public String getDelayReasonDesc()
	{
		return DelayReasonDesc;
	}
	public void setDelayReasonDesc(String aDelayReasonDesc)
	{
		if(aDelayReasonDesc!=null && aDelayReasonDesc.length()>500)
			throw new IllegalArgumentException("延迟送达原因DelayReasonDesc值"+aDelayReasonDesc+"的长度"+aDelayReasonDesc.length()+"大于最大值500");
		DelayReasonDesc = aDelayReasonDesc;
	}

	/**
	* 使用另外一个 LBGrpContSchema 对象给 Schema 赋值
	* @param: aLBGrpContSchema LBGrpContSchema
	**/
	public void setSchema(LBGrpContSchema aLBGrpContSchema)
	{
		this.EdorNo = aLBGrpContSchema.getEdorNo();
		this.GrpContNo = aLBGrpContSchema.getGrpContNo();
		this.ProposalGrpContNo = aLBGrpContSchema.getProposalGrpContNo();
		this.PrtNo = aLBGrpContSchema.getPrtNo();
		this.SaleChnl = aLBGrpContSchema.getSaleChnl();
		this.ManageCom = aLBGrpContSchema.getManageCom();
		this.AgentCom = aLBGrpContSchema.getAgentCom();
		this.AgentType = aLBGrpContSchema.getAgentType();
		this.AgentCode = aLBGrpContSchema.getAgentCode();
		this.AgentGroup = aLBGrpContSchema.getAgentGroup();
		this.AgentCode1 = aLBGrpContSchema.getAgentCode1();
		this.Password = aLBGrpContSchema.getPassword();
		this.Password2 = aLBGrpContSchema.getPassword2();
		this.AppntNo = aLBGrpContSchema.getAppntNo();
		this.AddressNo = aLBGrpContSchema.getAddressNo();
		this.Peoples2 = aLBGrpContSchema.getPeoples2();
		this.GrpName = aLBGrpContSchema.getGrpName();
		this.BusinessType = aLBGrpContSchema.getBusinessType();
		this.GrpNature = aLBGrpContSchema.getGrpNature();
		this.RgtMoney = aLBGrpContSchema.getRgtMoney();
		this.Asset = aLBGrpContSchema.getAsset();
		this.NetProfitRate = aLBGrpContSchema.getNetProfitRate();
		this.MainBussiness = aLBGrpContSchema.getMainBussiness();
		this.Corporation = aLBGrpContSchema.getCorporation();
		this.ComAera = aLBGrpContSchema.getComAera();
		this.Fax = aLBGrpContSchema.getFax();
		this.Phone = aLBGrpContSchema.getPhone();
		this.GetFlag = aLBGrpContSchema.getGetFlag();
		this.Satrap = aLBGrpContSchema.getSatrap();
		this.EMail = aLBGrpContSchema.getEMail();
		this.FoundDate = fDate.getDate( aLBGrpContSchema.getFoundDate());
		this.GrpGroupNo = aLBGrpContSchema.getGrpGroupNo();
		this.BankCode = aLBGrpContSchema.getBankCode();
		this.BankAccNo = aLBGrpContSchema.getBankAccNo();
		this.AccName = aLBGrpContSchema.getAccName();
		this.DisputedFlag = aLBGrpContSchema.getDisputedFlag();
		this.OutPayFlag = aLBGrpContSchema.getOutPayFlag();
		this.GetPolMode = aLBGrpContSchema.getGetPolMode();
		this.Lang = aLBGrpContSchema.getLang();
		this.Currency = aLBGrpContSchema.getCurrency();
		this.LostTimes = aLBGrpContSchema.getLostTimes();
		this.PrintCount = aLBGrpContSchema.getPrintCount();
		this.RegetDate = fDate.getDate( aLBGrpContSchema.getRegetDate());
		this.LastEdorDate = fDate.getDate( aLBGrpContSchema.getLastEdorDate());
		this.LastGetDate = fDate.getDate( aLBGrpContSchema.getLastGetDate());
		this.LastLoanDate = fDate.getDate( aLBGrpContSchema.getLastLoanDate());
		this.SpecFlag = aLBGrpContSchema.getSpecFlag();
		this.GrpSpec = aLBGrpContSchema.getGrpSpec();
		this.PayMode = aLBGrpContSchema.getPayMode();
		this.SignCom = aLBGrpContSchema.getSignCom();
		this.SignDate = fDate.getDate( aLBGrpContSchema.getSignDate());
		this.SignTime = aLBGrpContSchema.getSignTime();
		this.CValiDate = fDate.getDate( aLBGrpContSchema.getCValiDate());
		this.PayIntv = aLBGrpContSchema.getPayIntv();
		this.ManageFeeRate = aLBGrpContSchema.getManageFeeRate();
		this.ExpPeoples = aLBGrpContSchema.getExpPeoples();
		this.ExpPremium = aLBGrpContSchema.getExpPremium();
		this.ExpAmnt = aLBGrpContSchema.getExpAmnt();
		this.Peoples = aLBGrpContSchema.getPeoples();
		this.Mult = aLBGrpContSchema.getMult();
		this.Prem = aLBGrpContSchema.getPrem();
		this.Amnt = aLBGrpContSchema.getAmnt();
		this.SumPrem = aLBGrpContSchema.getSumPrem();
		this.SumPay = aLBGrpContSchema.getSumPay();
		this.Dif = aLBGrpContSchema.getDif();
		this.Remark = aLBGrpContSchema.getRemark();
		this.StandbyFlag1 = aLBGrpContSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLBGrpContSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLBGrpContSchema.getStandbyFlag3();
		this.InputOperator = aLBGrpContSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLBGrpContSchema.getInputDate());
		this.InputTime = aLBGrpContSchema.getInputTime();
		this.ApproveFlag = aLBGrpContSchema.getApproveFlag();
		this.ApproveCode = aLBGrpContSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLBGrpContSchema.getApproveDate());
		this.ApproveTime = aLBGrpContSchema.getApproveTime();
		this.UWOperator = aLBGrpContSchema.getUWOperator();
		this.UWFlag = aLBGrpContSchema.getUWFlag();
		this.UWDate = fDate.getDate( aLBGrpContSchema.getUWDate());
		this.UWTime = aLBGrpContSchema.getUWTime();
		this.AppFlag = aLBGrpContSchema.getAppFlag();
		this.PolApplyDate = fDate.getDate( aLBGrpContSchema.getPolApplyDate());
		this.CustomGetPolDate = fDate.getDate( aLBGrpContSchema.getCustomGetPolDate());
		this.GetPolDate = fDate.getDate( aLBGrpContSchema.getGetPolDate());
		this.GetPolTime = aLBGrpContSchema.getGetPolTime();
		this.State = aLBGrpContSchema.getState();
		this.Operator = aLBGrpContSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBGrpContSchema.getMakeDate());
		this.MakeTime = aLBGrpContSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBGrpContSchema.getModifyDate());
		this.ModifyTime = aLBGrpContSchema.getModifyTime();
		this.EnterKind = aLBGrpContSchema.getEnterKind();
		this.AmntGrade = aLBGrpContSchema.getAmntGrade();
		this.Peoples3 = aLBGrpContSchema.getPeoples3();
		this.OnWorkPeoples = aLBGrpContSchema.getOnWorkPeoples();
		this.OffWorkPeoples = aLBGrpContSchema.getOffWorkPeoples();
		this.OtherPeoples = aLBGrpContSchema.getOtherPeoples();
		this.RelaPeoples = aLBGrpContSchema.getRelaPeoples();
		this.RelaMatePeoples = aLBGrpContSchema.getRelaMatePeoples();
		this.RelaYoungPeoples = aLBGrpContSchema.getRelaYoungPeoples();
		this.RelaOtherPeoples = aLBGrpContSchema.getRelaOtherPeoples();
		this.FirstTrialOperator = aLBGrpContSchema.getFirstTrialOperator();
		this.FirstTrialDate = fDate.getDate( aLBGrpContSchema.getFirstTrialDate());
		this.FirstTrialTime = aLBGrpContSchema.getFirstTrialTime();
		this.ReceiveOperator = aLBGrpContSchema.getReceiveOperator();
		this.ReceiveDate = fDate.getDate( aLBGrpContSchema.getReceiveDate());
		this.ReceiveTime = aLBGrpContSchema.getReceiveTime();
		this.TempFeeNo = aLBGrpContSchema.getTempFeeNo();
		this.HandlerName = aLBGrpContSchema.getHandlerName();
		this.HandlerDate = fDate.getDate( aLBGrpContSchema.getHandlerDate());
		this.HandlerPrint = aLBGrpContSchema.getHandlerPrint();
		this.AgentDate = fDate.getDate( aLBGrpContSchema.getAgentDate());
		this.BusinessBigType = aLBGrpContSchema.getBusinessBigType();
		this.MarketType = aLBGrpContSchema.getMarketType();
		this.ReportNo = aLBGrpContSchema.getReportNo();
		this.ConferNo = aLBGrpContSchema.getConferNo();
		this.SignReportNo = aLBGrpContSchema.getSignReportNo();
		this.AgentConferNo = aLBGrpContSchema.getAgentConferNo();
		this.ContType = aLBGrpContSchema.getContType();
		this.EdorCalType = aLBGrpContSchema.getEdorCalType();
		this.ClientCare = aLBGrpContSchema.getClientCare();
		this.FundReason = aLBGrpContSchema.getFundReason();
		this.BackDateRemark = aLBGrpContSchema.getBackDateRemark();
		this.ClientNeedJudge = aLBGrpContSchema.getClientNeedJudge();
		this.DonateContflag = aLBGrpContSchema.getDonateContflag();
		this.FundJudge = aLBGrpContSchema.getFundJudge();
		this.ExamAndAppNo = aLBGrpContSchema.getExamAndAppNo();
		this.AgentCodeOper = aLBGrpContSchema.getAgentCodeOper();
		this.AgentCodeAssi = aLBGrpContSchema.getAgentCodeAssi();
		this.DelayReasonCode = aLBGrpContSchema.getDelayReasonCode();
		this.DelayReasonDesc = aLBGrpContSchema.getDelayReasonDesc();
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

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

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

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("Password2") == null )
				this.Password2 = null;
			else
				this.Password2 = rs.getString("Password2").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AddressNo") == null )
				this.AddressNo = null;
			else
				this.AddressNo = rs.getString("AddressNo").trim();

			this.Peoples2 = rs.getInt("Peoples2");
			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("BusinessType") == null )
				this.BusinessType = null;
			else
				this.BusinessType = rs.getString("BusinessType").trim();

			if( rs.getString("GrpNature") == null )
				this.GrpNature = null;
			else
				this.GrpNature = rs.getString("GrpNature").trim();

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
			if( rs.getString("GrpGroupNo") == null )
				this.GrpGroupNo = null;
			else
				this.GrpGroupNo = rs.getString("GrpGroupNo").trim();

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

			if( rs.getString("DisputedFlag") == null )
				this.DisputedFlag = null;
			else
				this.DisputedFlag = rs.getString("DisputedFlag").trim();

			if( rs.getString("OutPayFlag") == null )
				this.OutPayFlag = null;
			else
				this.OutPayFlag = rs.getString("OutPayFlag").trim();

			if( rs.getString("GetPolMode") == null )
				this.GetPolMode = null;
			else
				this.GetPolMode = rs.getString("GetPolMode").trim();

			if( rs.getString("Lang") == null )
				this.Lang = null;
			else
				this.Lang = rs.getString("Lang").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.LostTimes = rs.getInt("LostTimes");
			this.PrintCount = rs.getInt("PrintCount");
			this.RegetDate = rs.getDate("RegetDate");
			this.LastEdorDate = rs.getDate("LastEdorDate");
			this.LastGetDate = rs.getDate("LastGetDate");
			this.LastLoanDate = rs.getDate("LastLoanDate");
			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("GrpSpec") == null )
				this.GrpSpec = null;
			else
				this.GrpSpec = rs.getString("GrpSpec").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.PayIntv = rs.getInt("PayIntv");
			this.ManageFeeRate = rs.getDouble("ManageFeeRate");
			this.ExpPeoples = rs.getInt("ExpPeoples");
			this.ExpPremium = rs.getDouble("ExpPremium");
			this.ExpAmnt = rs.getDouble("ExpAmnt");
			this.Peoples = rs.getInt("Peoples");
			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.SumPrem = rs.getDouble("SumPrem");
			this.SumPay = rs.getDouble("SumPay");
			this.Dif = rs.getDouble("Dif");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			this.PolApplyDate = rs.getDate("PolApplyDate");
			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			this.GetPolDate = rs.getDate("GetPolDate");
			if( rs.getString("GetPolTime") == null )
				this.GetPolTime = null;
			else
				this.GetPolTime = rs.getString("GetPolTime").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("EnterKind") == null )
				this.EnterKind = null;
			else
				this.EnterKind = rs.getString("EnterKind").trim();

			if( rs.getString("AmntGrade") == null )
				this.AmntGrade = null;
			else
				this.AmntGrade = rs.getString("AmntGrade").trim();

			this.Peoples3 = rs.getInt("Peoples3");
			this.OnWorkPeoples = rs.getInt("OnWorkPeoples");
			this.OffWorkPeoples = rs.getInt("OffWorkPeoples");
			this.OtherPeoples = rs.getInt("OtherPeoples");
			this.RelaPeoples = rs.getInt("RelaPeoples");
			this.RelaMatePeoples = rs.getInt("RelaMatePeoples");
			this.RelaYoungPeoples = rs.getInt("RelaYoungPeoples");
			this.RelaOtherPeoples = rs.getInt("RelaOtherPeoples");
			if( rs.getString("FirstTrialOperator") == null )
				this.FirstTrialOperator = null;
			else
				this.FirstTrialOperator = rs.getString("FirstTrialOperator").trim();

			this.FirstTrialDate = rs.getDate("FirstTrialDate");
			if( rs.getString("FirstTrialTime") == null )
				this.FirstTrialTime = null;
			else
				this.FirstTrialTime = rs.getString("FirstTrialTime").trim();

			if( rs.getString("ReceiveOperator") == null )
				this.ReceiveOperator = null;
			else
				this.ReceiveOperator = rs.getString("ReceiveOperator").trim();

			this.ReceiveDate = rs.getDate("ReceiveDate");
			if( rs.getString("ReceiveTime") == null )
				this.ReceiveTime = null;
			else
				this.ReceiveTime = rs.getString("ReceiveTime").trim();

			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			if( rs.getString("HandlerName") == null )
				this.HandlerName = null;
			else
				this.HandlerName = rs.getString("HandlerName").trim();

			this.HandlerDate = rs.getDate("HandlerDate");
			if( rs.getString("HandlerPrint") == null )
				this.HandlerPrint = null;
			else
				this.HandlerPrint = rs.getString("HandlerPrint").trim();

			this.AgentDate = rs.getDate("AgentDate");
			if( rs.getString("BusinessBigType") == null )
				this.BusinessBigType = null;
			else
				this.BusinessBigType = rs.getString("BusinessBigType").trim();

			if( rs.getString("MarketType") == null )
				this.MarketType = null;
			else
				this.MarketType = rs.getString("MarketType").trim();

			if( rs.getString("ReportNo") == null )
				this.ReportNo = null;
			else
				this.ReportNo = rs.getString("ReportNo").trim();

			if( rs.getString("ConferNo") == null )
				this.ConferNo = null;
			else
				this.ConferNo = rs.getString("ConferNo").trim();

			if( rs.getString("SignReportNo") == null )
				this.SignReportNo = null;
			else
				this.SignReportNo = rs.getString("SignReportNo").trim();

			if( rs.getString("AgentConferNo") == null )
				this.AgentConferNo = null;
			else
				this.AgentConferNo = rs.getString("AgentConferNo").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("EdorCalType") == null )
				this.EdorCalType = null;
			else
				this.EdorCalType = rs.getString("EdorCalType").trim();

			if( rs.getString("ClientCare") == null )
				this.ClientCare = null;
			else
				this.ClientCare = rs.getString("ClientCare").trim();

			if( rs.getString("FundReason") == null )
				this.FundReason = null;
			else
				this.FundReason = rs.getString("FundReason").trim();

			if( rs.getString("BackDateRemark") == null )
				this.BackDateRemark = null;
			else
				this.BackDateRemark = rs.getString("BackDateRemark").trim();

			if( rs.getString("ClientNeedJudge") == null )
				this.ClientNeedJudge = null;
			else
				this.ClientNeedJudge = rs.getString("ClientNeedJudge").trim();

			if( rs.getString("DonateContflag") == null )
				this.DonateContflag = null;
			else
				this.DonateContflag = rs.getString("DonateContflag").trim();

			if( rs.getString("FundJudge") == null )
				this.FundJudge = null;
			else
				this.FundJudge = rs.getString("FundJudge").trim();

			if( rs.getString("ExamAndAppNo") == null )
				this.ExamAndAppNo = null;
			else
				this.ExamAndAppNo = rs.getString("ExamAndAppNo").trim();

			if( rs.getString("AgentCodeOper") == null )
				this.AgentCodeOper = null;
			else
				this.AgentCodeOper = rs.getString("AgentCodeOper").trim();

			if( rs.getString("AgentCodeAssi") == null )
				this.AgentCodeAssi = null;
			else
				this.AgentCodeAssi = rs.getString("AgentCodeAssi").trim();

			if( rs.getString("DelayReasonCode") == null )
				this.DelayReasonCode = null;
			else
				this.DelayReasonCode = rs.getString("DelayReasonCode").trim();

			if( rs.getString("DelayReasonDesc") == null )
				this.DelayReasonDesc = null;
			else
				this.DelayReasonDesc = rs.getString("DelayReasonDesc").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBGrpCont表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBGrpContSchema getSchema()
	{
		LBGrpContSchema aLBGrpContSchema = new LBGrpContSchema();
		aLBGrpContSchema.setSchema(this);
		return aLBGrpContSchema;
	}

	public LBGrpContDB getDB()
	{
		LBGrpContDB aDBOper = new LBGrpContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBGrpCont描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNature)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RgtMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Asset));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetProfitRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBussiness)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Corporation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComAera)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Fax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Satrap)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EMail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpGroupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisputedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Lang)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LostTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RegetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastEdorDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastLoanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpSpec)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ManageFeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPremium));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Dif));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnterKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AmntGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnWorkPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OffWorkPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaMatePeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaYoungPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaOtherPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstTrialDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReceiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HandlerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HandlerDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HandlerPrint)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AgentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessBigType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MarketType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConferNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignReportNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentConferNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClientCare)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackDateRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClientNeedJudge)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DonateContflag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundJudge)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamAndAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeAssi)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBGrpCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Password2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Peoples2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BusinessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RgtMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Asset = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			MainBussiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ComAera = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			GrpGroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			LostTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).intValue();
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).intValue();
			RegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			LastGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			LastLoanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			GrpSpec = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53,SysConst.PACKAGESPILTER));
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).intValue();
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			ExpPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).intValue();
			ExpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).doubleValue();
			ExpAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).doubleValue();
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,64,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82,SysConst.PACKAGESPILTER));
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84,SysConst.PACKAGESPILTER));
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			EnterKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			AmntGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			Peoples3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,94,SysConst.PACKAGESPILTER))).intValue();
			OnWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,95,SysConst.PACKAGESPILTER))).intValue();
			OffWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,96,SysConst.PACKAGESPILTER))).intValue();
			OtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,97,SysConst.PACKAGESPILTER))).intValue();
			RelaPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,98,SysConst.PACKAGESPILTER))).intValue();
			RelaMatePeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,99,SysConst.PACKAGESPILTER))).intValue();
			RelaYoungPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,100,SysConst.PACKAGESPILTER))).intValue();
			RelaOtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,101,SysConst.PACKAGESPILTER))).intValue();
			FirstTrialOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			FirstTrialDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103,SysConst.PACKAGESPILTER));
			FirstTrialTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			ReceiveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			ReceiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106,SysConst.PACKAGESPILTER));
			ReceiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			HandlerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
			HandlerDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110,SysConst.PACKAGESPILTER));
			HandlerPrint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111, SysConst.PACKAGESPILTER );
			AgentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112,SysConst.PACKAGESPILTER));
			BusinessBigType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 113, SysConst.PACKAGESPILTER );
			MarketType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114, SysConst.PACKAGESPILTER );
			ReportNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			ConferNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 116, SysConst.PACKAGESPILTER );
			SignReportNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 117, SysConst.PACKAGESPILTER );
			AgentConferNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 118, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 119, SysConst.PACKAGESPILTER );
			EdorCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 120, SysConst.PACKAGESPILTER );
			ClientCare = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 121, SysConst.PACKAGESPILTER );
			FundReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 122, SysConst.PACKAGESPILTER );
			BackDateRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 123, SysConst.PACKAGESPILTER );
			ClientNeedJudge = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 124, SysConst.PACKAGESPILTER );
			DonateContflag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 125, SysConst.PACKAGESPILTER );
			FundJudge = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 126, SysConst.PACKAGESPILTER );
			ExamAndAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 127, SysConst.PACKAGESPILTER );
			AgentCodeOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 128, SysConst.PACKAGESPILTER );
			AgentCodeAssi = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 129, SysConst.PACKAGESPILTER );
			DelayReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 130, SysConst.PACKAGESPILTER );
			DelayReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 131, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpContSchema";
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
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
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("Password2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password2));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples2));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("BusinessType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessType));
		}
		if (FCode.equalsIgnoreCase("GrpNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNature));
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
		if (FCode.equalsIgnoreCase("GrpGroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpGroupNo));
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
		if (FCode.equalsIgnoreCase("DisputedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisputedFlag));
		}
		if (FCode.equalsIgnoreCase("OutPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayFlag));
		}
		if (FCode.equalsIgnoreCase("GetPolMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolMode));
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Lang));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostTimes));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("RegetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
		}
		if (FCode.equalsIgnoreCase("LastEdorDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
		}
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
		}
		if (FCode.equalsIgnoreCase("LastLoanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("GrpSpec"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpSpec));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignCom));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignTime));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("ManageFeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageFeeRate));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputTime));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equalsIgnoreCase("PolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolTime));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("EnterKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnterKind));
		}
		if (FCode.equalsIgnoreCase("AmntGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntGrade));
		}
		if (FCode.equalsIgnoreCase("Peoples3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples3));
		}
		if (FCode.equalsIgnoreCase("OnWorkPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnWorkPeoples));
		}
		if (FCode.equalsIgnoreCase("OffWorkPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OffWorkPeoples));
		}
		if (FCode.equalsIgnoreCase("OtherPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherPeoples));
		}
		if (FCode.equalsIgnoreCase("RelaPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaPeoples));
		}
		if (FCode.equalsIgnoreCase("RelaMatePeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaMatePeoples));
		}
		if (FCode.equalsIgnoreCase("RelaYoungPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaYoungPeoples));
		}
		if (FCode.equalsIgnoreCase("RelaOtherPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaOtherPeoples));
		}
		if (FCode.equalsIgnoreCase("FirstTrialOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialOperator));
		}
		if (FCode.equalsIgnoreCase("FirstTrialDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
		}
		if (FCode.equalsIgnoreCase("FirstTrialTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialTime));
		}
		if (FCode.equalsIgnoreCase("ReceiveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveOperator));
		}
		if (FCode.equalsIgnoreCase("ReceiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
		}
		if (FCode.equalsIgnoreCase("ReceiveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveTime));
		}
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equalsIgnoreCase("HandlerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HandlerName));
		}
		if (FCode.equalsIgnoreCase("HandlerDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHandlerDate()));
		}
		if (FCode.equalsIgnoreCase("HandlerPrint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HandlerPrint));
		}
		if (FCode.equalsIgnoreCase("AgentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAgentDate()));
		}
		if (FCode.equalsIgnoreCase("BusinessBigType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessBigType));
		}
		if (FCode.equalsIgnoreCase("MarketType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MarketType));
		}
		if (FCode.equalsIgnoreCase("ReportNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportNo));
		}
		if (FCode.equalsIgnoreCase("ConferNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConferNo));
		}
		if (FCode.equalsIgnoreCase("SignReportNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignReportNo));
		}
		if (FCode.equalsIgnoreCase("AgentConferNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentConferNo));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("EdorCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorCalType));
		}
		if (FCode.equalsIgnoreCase("ClientCare"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClientCare));
		}
		if (FCode.equalsIgnoreCase("FundReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundReason));
		}
		if (FCode.equalsIgnoreCase("BackDateRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackDateRemark));
		}
		if (FCode.equalsIgnoreCase("ClientNeedJudge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClientNeedJudge));
		}
		if (FCode.equalsIgnoreCase("DonateContflag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DonateContflag));
		}
		if (FCode.equalsIgnoreCase("FundJudge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundJudge));
		}
		if (FCode.equalsIgnoreCase("ExamAndAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamAndAppNo));
		}
		if (FCode.equalsIgnoreCase("AgentCodeOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCodeOper));
		}
		if (FCode.equalsIgnoreCase("AgentCodeAssi"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCodeAssi));
		}
		if (FCode.equalsIgnoreCase("DelayReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DelayReasonCode));
		}
		if (FCode.equalsIgnoreCase("DelayReasonDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DelayReasonDesc));
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
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Password2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 15:
				strFieldValue = String.valueOf(Peoples2);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 19:
				strFieldValue = String.valueOf(RgtMoney);
				break;
			case 20:
				strFieldValue = String.valueOf(Asset);
				break;
			case 21:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MainBussiness);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ComAera);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(GrpGroupNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(DisputedFlag);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(GetPolMode);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 40:
				strFieldValue = String.valueOf(LostTimes);
				break;
			case 41:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(GrpSpec);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 53:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 54:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 55:
				strFieldValue = String.valueOf(ExpPeoples);
				break;
			case 56:
				strFieldValue = String.valueOf(ExpPremium);
				break;
			case 57:
				strFieldValue = String.valueOf(ExpAmnt);
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
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(EnterKind);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(AmntGrade);
				break;
			case 93:
				strFieldValue = String.valueOf(Peoples3);
				break;
			case 94:
				strFieldValue = String.valueOf(OnWorkPeoples);
				break;
			case 95:
				strFieldValue = String.valueOf(OffWorkPeoples);
				break;
			case 96:
				strFieldValue = String.valueOf(OtherPeoples);
				break;
			case 97:
				strFieldValue = String.valueOf(RelaPeoples);
				break;
			case 98:
				strFieldValue = String.valueOf(RelaMatePeoples);
				break;
			case 99:
				strFieldValue = String.valueOf(RelaYoungPeoples);
				break;
			case 100:
				strFieldValue = String.valueOf(RelaOtherPeoples);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialOperator);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialTime);
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(ReceiveOperator);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(ReceiveTime);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(HandlerName);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandlerDate()));
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(HandlerPrint);
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAgentDate()));
				break;
			case 112:
				strFieldValue = StrTool.GBKToUnicode(BusinessBigType);
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(MarketType);
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(ReportNo);
				break;
			case 115:
				strFieldValue = StrTool.GBKToUnicode(ConferNo);
				break;
			case 116:
				strFieldValue = StrTool.GBKToUnicode(SignReportNo);
				break;
			case 117:
				strFieldValue = StrTool.GBKToUnicode(AgentConferNo);
				break;
			case 118:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 119:
				strFieldValue = StrTool.GBKToUnicode(EdorCalType);
				break;
			case 120:
				strFieldValue = StrTool.GBKToUnicode(ClientCare);
				break;
			case 121:
				strFieldValue = StrTool.GBKToUnicode(FundReason);
				break;
			case 122:
				strFieldValue = StrTool.GBKToUnicode(BackDateRemark);
				break;
			case 123:
				strFieldValue = StrTool.GBKToUnicode(ClientNeedJudge);
				break;
			case 124:
				strFieldValue = StrTool.GBKToUnicode(DonateContflag);
				break;
			case 125:
				strFieldValue = StrTool.GBKToUnicode(FundJudge);
				break;
			case 126:
				strFieldValue = StrTool.GBKToUnicode(ExamAndAppNo);
				break;
			case 127:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeOper);
				break;
			case 128:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeAssi);
				break;
			case 129:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonCode);
				break;
			case 130:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonDesc);
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
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
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equalsIgnoreCase("Peoples2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples2 = i;
			}
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
		if (FCode.equalsIgnoreCase("GrpGroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpGroupNo = FValue.trim();
			}
			else
				GrpGroupNo = null;
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
		if (FCode.equalsIgnoreCase("DisputedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisputedFlag = FValue.trim();
			}
			else
				DisputedFlag = null;
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
		if (FCode.equalsIgnoreCase("GetPolMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolMode = FValue.trim();
			}
			else
				GetPolMode = null;
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
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LostTimes = i;
			}
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
		if (FCode.equalsIgnoreCase("RegetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RegetDate = fDate.getDate( FValue );
			}
			else
				RegetDate = null;
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
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastGetDate = fDate.getDate( FValue );
			}
			else
				LastGetDate = null;
		}
		if (FCode.equalsIgnoreCase("LastLoanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastLoanDate = fDate.getDate( FValue );
			}
			else
				LastLoanDate = null;
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
		if (FCode.equalsIgnoreCase("GrpSpec"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpSpec = FValue.trim();
			}
			else
				GrpSpec = null;
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
		if (FCode.equalsIgnoreCase("SignCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignCom = FValue.trim();
			}
			else
				SignCom = null;
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
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignTime = FValue.trim();
			}
			else
				SignTime = null;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOperator = FValue.trim();
			}
			else
				InputOperator = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputTime = FValue.trim();
			}
			else
				InputTime = null;
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
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
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
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
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
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
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
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetPolDate = fDate.getDate( FValue );
			}
			else
				GetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolTime = FValue.trim();
			}
			else
				GetPolTime = null;
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
		if (FCode.equalsIgnoreCase("EnterKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnterKind = FValue.trim();
			}
			else
				EnterKind = null;
		}
		if (FCode.equalsIgnoreCase("AmntGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmntGrade = FValue.trim();
			}
			else
				AmntGrade = null;
		}
		if (FCode.equalsIgnoreCase("Peoples3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnWorkPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnWorkPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OffWorkPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OffWorkPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OtherPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaMatePeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaMatePeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaYoungPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaYoungPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaOtherPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaOtherPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("FirstTrialOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialOperator = FValue.trim();
			}
			else
				FirstTrialOperator = null;
		}
		if (FCode.equalsIgnoreCase("FirstTrialDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstTrialDate = fDate.getDate( FValue );
			}
			else
				FirstTrialDate = null;
		}
		if (FCode.equalsIgnoreCase("FirstTrialTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialTime = FValue.trim();
			}
			else
				FirstTrialTime = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveOperator = FValue.trim();
			}
			else
				ReceiveOperator = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReceiveDate = fDate.getDate( FValue );
			}
			else
				ReceiveDate = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveTime = FValue.trim();
			}
			else
				ReceiveTime = null;
		}
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("HandlerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HandlerName = FValue.trim();
			}
			else
				HandlerName = null;
		}
		if (FCode.equalsIgnoreCase("HandlerDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				HandlerDate = fDate.getDate( FValue );
			}
			else
				HandlerDate = null;
		}
		if (FCode.equalsIgnoreCase("HandlerPrint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HandlerPrint = FValue.trim();
			}
			else
				HandlerPrint = null;
		}
		if (FCode.equalsIgnoreCase("AgentDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AgentDate = fDate.getDate( FValue );
			}
			else
				AgentDate = null;
		}
		if (FCode.equalsIgnoreCase("BusinessBigType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessBigType = FValue.trim();
			}
			else
				BusinessBigType = null;
		}
		if (FCode.equalsIgnoreCase("MarketType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MarketType = FValue.trim();
			}
			else
				MarketType = null;
		}
		if (FCode.equalsIgnoreCase("ReportNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportNo = FValue.trim();
			}
			else
				ReportNo = null;
		}
		if (FCode.equalsIgnoreCase("ConferNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConferNo = FValue.trim();
			}
			else
				ConferNo = null;
		}
		if (FCode.equalsIgnoreCase("SignReportNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignReportNo = FValue.trim();
			}
			else
				SignReportNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentConferNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentConferNo = FValue.trim();
			}
			else
				AgentConferNo = null;
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("EdorCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorCalType = FValue.trim();
			}
			else
				EdorCalType = null;
		}
		if (FCode.equalsIgnoreCase("ClientCare"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClientCare = FValue.trim();
			}
			else
				ClientCare = null;
		}
		if (FCode.equalsIgnoreCase("FundReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundReason = FValue.trim();
			}
			else
				FundReason = null;
		}
		if (FCode.equalsIgnoreCase("BackDateRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackDateRemark = FValue.trim();
			}
			else
				BackDateRemark = null;
		}
		if (FCode.equalsIgnoreCase("ClientNeedJudge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClientNeedJudge = FValue.trim();
			}
			else
				ClientNeedJudge = null;
		}
		if (FCode.equalsIgnoreCase("DonateContflag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DonateContflag = FValue.trim();
			}
			else
				DonateContflag = null;
		}
		if (FCode.equalsIgnoreCase("FundJudge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundJudge = FValue.trim();
			}
			else
				FundJudge = null;
		}
		if (FCode.equalsIgnoreCase("ExamAndAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamAndAppNo = FValue.trim();
			}
			else
				ExamAndAppNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentCodeOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCodeOper = FValue.trim();
			}
			else
				AgentCodeOper = null;
		}
		if (FCode.equalsIgnoreCase("AgentCodeAssi"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCodeAssi = FValue.trim();
			}
			else
				AgentCodeAssi = null;
		}
		if (FCode.equalsIgnoreCase("DelayReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DelayReasonCode = FValue.trim();
			}
			else
				DelayReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("DelayReasonDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DelayReasonDesc = FValue.trim();
			}
			else
				DelayReasonDesc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBGrpContSchema other = (LBGrpContSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& Password.equals(other.getPassword())
			&& Password2.equals(other.getPassword2())
			&& AppntNo.equals(other.getAppntNo())
			&& AddressNo.equals(other.getAddressNo())
			&& Peoples2 == other.getPeoples2()
			&& GrpName.equals(other.getGrpName())
			&& BusinessType.equals(other.getBusinessType())
			&& GrpNature.equals(other.getGrpNature())
			&& RgtMoney == other.getRgtMoney()
			&& Asset == other.getAsset()
			&& NetProfitRate == other.getNetProfitRate()
			&& MainBussiness.equals(other.getMainBussiness())
			&& Corporation.equals(other.getCorporation())
			&& ComAera.equals(other.getComAera())
			&& Fax.equals(other.getFax())
			&& Phone.equals(other.getPhone())
			&& GetFlag.equals(other.getGetFlag())
			&& Satrap.equals(other.getSatrap())
			&& EMail.equals(other.getEMail())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& GrpGroupNo.equals(other.getGrpGroupNo())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& DisputedFlag.equals(other.getDisputedFlag())
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& GetPolMode.equals(other.getGetPolMode())
			&& Lang.equals(other.getLang())
			&& Currency.equals(other.getCurrency())
			&& LostTimes == other.getLostTimes()
			&& PrintCount == other.getPrintCount()
			&& fDate.getString(RegetDate).equals(other.getRegetDate())
			&& fDate.getString(LastEdorDate).equals(other.getLastEdorDate())
			&& fDate.getString(LastGetDate).equals(other.getLastGetDate())
			&& fDate.getString(LastLoanDate).equals(other.getLastLoanDate())
			&& SpecFlag.equals(other.getSpecFlag())
			&& GrpSpec.equals(other.getGrpSpec())
			&& PayMode.equals(other.getPayMode())
			&& SignCom.equals(other.getSignCom())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& PayIntv == other.getPayIntv()
			&& ManageFeeRate == other.getManageFeeRate()
			&& ExpPeoples == other.getExpPeoples()
			&& ExpPremium == other.getExpPremium()
			&& ExpAmnt == other.getExpAmnt()
			&& Peoples == other.getPeoples()
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& SumPrem == other.getSumPrem()
			&& SumPay == other.getSumPay()
			&& Dif == other.getDif()
			&& Remark.equals(other.getRemark())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWOperator.equals(other.getUWOperator())
			&& UWFlag.equals(other.getUWFlag())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& AppFlag.equals(other.getAppFlag())
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& GetPolTime.equals(other.getGetPolTime())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& EnterKind.equals(other.getEnterKind())
			&& AmntGrade.equals(other.getAmntGrade())
			&& Peoples3 == other.getPeoples3()
			&& OnWorkPeoples == other.getOnWorkPeoples()
			&& OffWorkPeoples == other.getOffWorkPeoples()
			&& OtherPeoples == other.getOtherPeoples()
			&& RelaPeoples == other.getRelaPeoples()
			&& RelaMatePeoples == other.getRelaMatePeoples()
			&& RelaYoungPeoples == other.getRelaYoungPeoples()
			&& RelaOtherPeoples == other.getRelaOtherPeoples()
			&& FirstTrialOperator.equals(other.getFirstTrialOperator())
			&& fDate.getString(FirstTrialDate).equals(other.getFirstTrialDate())
			&& FirstTrialTime.equals(other.getFirstTrialTime())
			&& ReceiveOperator.equals(other.getReceiveOperator())
			&& fDate.getString(ReceiveDate).equals(other.getReceiveDate())
			&& ReceiveTime.equals(other.getReceiveTime())
			&& TempFeeNo.equals(other.getTempFeeNo())
			&& HandlerName.equals(other.getHandlerName())
			&& fDate.getString(HandlerDate).equals(other.getHandlerDate())
			&& HandlerPrint.equals(other.getHandlerPrint())
			&& fDate.getString(AgentDate).equals(other.getAgentDate())
			&& BusinessBigType.equals(other.getBusinessBigType())
			&& MarketType.equals(other.getMarketType())
			&& ReportNo.equals(other.getReportNo())
			&& ConferNo.equals(other.getConferNo())
			&& SignReportNo.equals(other.getSignReportNo())
			&& AgentConferNo.equals(other.getAgentConferNo())
			&& ContType.equals(other.getContType())
			&& EdorCalType.equals(other.getEdorCalType())
			&& ClientCare.equals(other.getClientCare())
			&& FundReason.equals(other.getFundReason())
			&& BackDateRemark.equals(other.getBackDateRemark())
			&& ClientNeedJudge.equals(other.getClientNeedJudge())
			&& DonateContflag.equals(other.getDonateContflag())
			&& FundJudge.equals(other.getFundJudge())
			&& ExamAndAppNo.equals(other.getExamAndAppNo())
			&& AgentCodeOper.equals(other.getAgentCodeOper())
			&& AgentCodeAssi.equals(other.getAgentCodeAssi())
			&& DelayReasonCode.equals(other.getDelayReasonCode())
			&& DelayReasonDesc.equals(other.getDelayReasonDesc());
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
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 3;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 6;
		}
		if( strFieldName.equals("AgentType") ) {
			return 7;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 8;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 9;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 10;
		}
		if( strFieldName.equals("Password") ) {
			return 11;
		}
		if( strFieldName.equals("Password2") ) {
			return 12;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 13;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 14;
		}
		if( strFieldName.equals("Peoples2") ) {
			return 15;
		}
		if( strFieldName.equals("GrpName") ) {
			return 16;
		}
		if( strFieldName.equals("BusinessType") ) {
			return 17;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 18;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return 19;
		}
		if( strFieldName.equals("Asset") ) {
			return 20;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 21;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return 22;
		}
		if( strFieldName.equals("Corporation") ) {
			return 23;
		}
		if( strFieldName.equals("ComAera") ) {
			return 24;
		}
		if( strFieldName.equals("Fax") ) {
			return 25;
		}
		if( strFieldName.equals("Phone") ) {
			return 26;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 27;
		}
		if( strFieldName.equals("Satrap") ) {
			return 28;
		}
		if( strFieldName.equals("EMail") ) {
			return 29;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 30;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return 31;
		}
		if( strFieldName.equals("BankCode") ) {
			return 32;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 33;
		}
		if( strFieldName.equals("AccName") ) {
			return 34;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return 35;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 36;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return 37;
		}
		if( strFieldName.equals("Lang") ) {
			return 38;
		}
		if( strFieldName.equals("Currency") ) {
			return 39;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 40;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 41;
		}
		if( strFieldName.equals("RegetDate") ) {
			return 42;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 43;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return 44;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return 45;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 46;
		}
		if( strFieldName.equals("GrpSpec") ) {
			return 47;
		}
		if( strFieldName.equals("PayMode") ) {
			return 48;
		}
		if( strFieldName.equals("SignCom") ) {
			return 49;
		}
		if( strFieldName.equals("SignDate") ) {
			return 50;
		}
		if( strFieldName.equals("SignTime") ) {
			return 51;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 52;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 53;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 54;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return 55;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return 56;
		}
		if( strFieldName.equals("ExpAmnt") ) {
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
		if( strFieldName.equals("Remark") ) {
			return 65;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 66;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 67;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 68;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 69;
		}
		if( strFieldName.equals("InputDate") ) {
			return 70;
		}
		if( strFieldName.equals("InputTime") ) {
			return 71;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 72;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 73;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 74;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 75;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 76;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 77;
		}
		if( strFieldName.equals("UWDate") ) {
			return 78;
		}
		if( strFieldName.equals("UWTime") ) {
			return 79;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 80;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 81;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 82;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 83;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 84;
		}
		if( strFieldName.equals("State") ) {
			return 85;
		}
		if( strFieldName.equals("Operator") ) {
			return 86;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 87;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 88;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 89;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 90;
		}
		if( strFieldName.equals("EnterKind") ) {
			return 91;
		}
		if( strFieldName.equals("AmntGrade") ) {
			return 92;
		}
		if( strFieldName.equals("Peoples3") ) {
			return 93;
		}
		if( strFieldName.equals("OnWorkPeoples") ) {
			return 94;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return 95;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return 96;
		}
		if( strFieldName.equals("RelaPeoples") ) {
			return 97;
		}
		if( strFieldName.equals("RelaMatePeoples") ) {
			return 98;
		}
		if( strFieldName.equals("RelaYoungPeoples") ) {
			return 99;
		}
		if( strFieldName.equals("RelaOtherPeoples") ) {
			return 100;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return 101;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return 102;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return 103;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return 104;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 105;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return 106;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 107;
		}
		if( strFieldName.equals("HandlerName") ) {
			return 108;
		}
		if( strFieldName.equals("HandlerDate") ) {
			return 109;
		}
		if( strFieldName.equals("HandlerPrint") ) {
			return 110;
		}
		if( strFieldName.equals("AgentDate") ) {
			return 111;
		}
		if( strFieldName.equals("BusinessBigType") ) {
			return 112;
		}
		if( strFieldName.equals("MarketType") ) {
			return 113;
		}
		if( strFieldName.equals("ReportNo") ) {
			return 114;
		}
		if( strFieldName.equals("ConferNo") ) {
			return 115;
		}
		if( strFieldName.equals("SignReportNo") ) {
			return 116;
		}
		if( strFieldName.equals("AgentConferNo") ) {
			return 117;
		}
		if( strFieldName.equals("ContType") ) {
			return 118;
		}
		if( strFieldName.equals("EdorCalType") ) {
			return 119;
		}
		if( strFieldName.equals("ClientCare") ) {
			return 120;
		}
		if( strFieldName.equals("FundReason") ) {
			return 121;
		}
		if( strFieldName.equals("BackDateRemark") ) {
			return 122;
		}
		if( strFieldName.equals("ClientNeedJudge") ) {
			return 123;
		}
		if( strFieldName.equals("DonateContflag") ) {
			return 124;
		}
		if( strFieldName.equals("FundJudge") ) {
			return 125;
		}
		if( strFieldName.equals("ExamAndAppNo") ) {
			return 126;
		}
		if( strFieldName.equals("AgentCodeOper") ) {
			return 127;
		}
		if( strFieldName.equals("AgentCodeAssi") ) {
			return 128;
		}
		if( strFieldName.equals("DelayReasonCode") ) {
			return 129;
		}
		if( strFieldName.equals("DelayReasonDesc") ) {
			return 130;
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
				strFieldName = "ProposalGrpContNo";
				break;
			case 3:
				strFieldName = "PrtNo";
				break;
			case 4:
				strFieldName = "SaleChnl";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "AgentCom";
				break;
			case 7:
				strFieldName = "AgentType";
				break;
			case 8:
				strFieldName = "AgentCode";
				break;
			case 9:
				strFieldName = "AgentGroup";
				break;
			case 10:
				strFieldName = "AgentCode1";
				break;
			case 11:
				strFieldName = "Password";
				break;
			case 12:
				strFieldName = "Password2";
				break;
			case 13:
				strFieldName = "AppntNo";
				break;
			case 14:
				strFieldName = "AddressNo";
				break;
			case 15:
				strFieldName = "Peoples2";
				break;
			case 16:
				strFieldName = "GrpName";
				break;
			case 17:
				strFieldName = "BusinessType";
				break;
			case 18:
				strFieldName = "GrpNature";
				break;
			case 19:
				strFieldName = "RgtMoney";
				break;
			case 20:
				strFieldName = "Asset";
				break;
			case 21:
				strFieldName = "NetProfitRate";
				break;
			case 22:
				strFieldName = "MainBussiness";
				break;
			case 23:
				strFieldName = "Corporation";
				break;
			case 24:
				strFieldName = "ComAera";
				break;
			case 25:
				strFieldName = "Fax";
				break;
			case 26:
				strFieldName = "Phone";
				break;
			case 27:
				strFieldName = "GetFlag";
				break;
			case 28:
				strFieldName = "Satrap";
				break;
			case 29:
				strFieldName = "EMail";
				break;
			case 30:
				strFieldName = "FoundDate";
				break;
			case 31:
				strFieldName = "GrpGroupNo";
				break;
			case 32:
				strFieldName = "BankCode";
				break;
			case 33:
				strFieldName = "BankAccNo";
				break;
			case 34:
				strFieldName = "AccName";
				break;
			case 35:
				strFieldName = "DisputedFlag";
				break;
			case 36:
				strFieldName = "OutPayFlag";
				break;
			case 37:
				strFieldName = "GetPolMode";
				break;
			case 38:
				strFieldName = "Lang";
				break;
			case 39:
				strFieldName = "Currency";
				break;
			case 40:
				strFieldName = "LostTimes";
				break;
			case 41:
				strFieldName = "PrintCount";
				break;
			case 42:
				strFieldName = "RegetDate";
				break;
			case 43:
				strFieldName = "LastEdorDate";
				break;
			case 44:
				strFieldName = "LastGetDate";
				break;
			case 45:
				strFieldName = "LastLoanDate";
				break;
			case 46:
				strFieldName = "SpecFlag";
				break;
			case 47:
				strFieldName = "GrpSpec";
				break;
			case 48:
				strFieldName = "PayMode";
				break;
			case 49:
				strFieldName = "SignCom";
				break;
			case 50:
				strFieldName = "SignDate";
				break;
			case 51:
				strFieldName = "SignTime";
				break;
			case 52:
				strFieldName = "CValiDate";
				break;
			case 53:
				strFieldName = "PayIntv";
				break;
			case 54:
				strFieldName = "ManageFeeRate";
				break;
			case 55:
				strFieldName = "ExpPeoples";
				break;
			case 56:
				strFieldName = "ExpPremium";
				break;
			case 57:
				strFieldName = "ExpAmnt";
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
				strFieldName = "Remark";
				break;
			case 66:
				strFieldName = "StandbyFlag1";
				break;
			case 67:
				strFieldName = "StandbyFlag2";
				break;
			case 68:
				strFieldName = "StandbyFlag3";
				break;
			case 69:
				strFieldName = "InputOperator";
				break;
			case 70:
				strFieldName = "InputDate";
				break;
			case 71:
				strFieldName = "InputTime";
				break;
			case 72:
				strFieldName = "ApproveFlag";
				break;
			case 73:
				strFieldName = "ApproveCode";
				break;
			case 74:
				strFieldName = "ApproveDate";
				break;
			case 75:
				strFieldName = "ApproveTime";
				break;
			case 76:
				strFieldName = "UWOperator";
				break;
			case 77:
				strFieldName = "UWFlag";
				break;
			case 78:
				strFieldName = "UWDate";
				break;
			case 79:
				strFieldName = "UWTime";
				break;
			case 80:
				strFieldName = "AppFlag";
				break;
			case 81:
				strFieldName = "PolApplyDate";
				break;
			case 82:
				strFieldName = "CustomGetPolDate";
				break;
			case 83:
				strFieldName = "GetPolDate";
				break;
			case 84:
				strFieldName = "GetPolTime";
				break;
			case 85:
				strFieldName = "State";
				break;
			case 86:
				strFieldName = "Operator";
				break;
			case 87:
				strFieldName = "MakeDate";
				break;
			case 88:
				strFieldName = "MakeTime";
				break;
			case 89:
				strFieldName = "ModifyDate";
				break;
			case 90:
				strFieldName = "ModifyTime";
				break;
			case 91:
				strFieldName = "EnterKind";
				break;
			case 92:
				strFieldName = "AmntGrade";
				break;
			case 93:
				strFieldName = "Peoples3";
				break;
			case 94:
				strFieldName = "OnWorkPeoples";
				break;
			case 95:
				strFieldName = "OffWorkPeoples";
				break;
			case 96:
				strFieldName = "OtherPeoples";
				break;
			case 97:
				strFieldName = "RelaPeoples";
				break;
			case 98:
				strFieldName = "RelaMatePeoples";
				break;
			case 99:
				strFieldName = "RelaYoungPeoples";
				break;
			case 100:
				strFieldName = "RelaOtherPeoples";
				break;
			case 101:
				strFieldName = "FirstTrialOperator";
				break;
			case 102:
				strFieldName = "FirstTrialDate";
				break;
			case 103:
				strFieldName = "FirstTrialTime";
				break;
			case 104:
				strFieldName = "ReceiveOperator";
				break;
			case 105:
				strFieldName = "ReceiveDate";
				break;
			case 106:
				strFieldName = "ReceiveTime";
				break;
			case 107:
				strFieldName = "TempFeeNo";
				break;
			case 108:
				strFieldName = "HandlerName";
				break;
			case 109:
				strFieldName = "HandlerDate";
				break;
			case 110:
				strFieldName = "HandlerPrint";
				break;
			case 111:
				strFieldName = "AgentDate";
				break;
			case 112:
				strFieldName = "BusinessBigType";
				break;
			case 113:
				strFieldName = "MarketType";
				break;
			case 114:
				strFieldName = "ReportNo";
				break;
			case 115:
				strFieldName = "ConferNo";
				break;
			case 116:
				strFieldName = "SignReportNo";
				break;
			case 117:
				strFieldName = "AgentConferNo";
				break;
			case 118:
				strFieldName = "ContType";
				break;
			case 119:
				strFieldName = "EdorCalType";
				break;
			case 120:
				strFieldName = "ClientCare";
				break;
			case 121:
				strFieldName = "FundReason";
				break;
			case 122:
				strFieldName = "BackDateRemark";
				break;
			case 123:
				strFieldName = "ClientNeedJudge";
				break;
			case 124:
				strFieldName = "DonateContflag";
				break;
			case 125:
				strFieldName = "FundJudge";
				break;
			case 126:
				strFieldName = "ExamAndAppNo";
				break;
			case 127:
				strFieldName = "AgentCodeOper";
				break;
			case 128:
				strFieldName = "AgentCodeAssi";
				break;
			case 129:
				strFieldName = "DelayReasonCode";
				break;
			case 130:
				strFieldName = "DelayReasonDesc";
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
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
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
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples2") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpNature") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("GrpGroupNo") ) {
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
		if( strFieldName.equals("DisputedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Lang") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LostTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RegetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpSpec") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("EnterKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmntGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Peoples3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnWorkPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaMatePeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaYoungPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaOtherPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandlerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandlerDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("HandlerPrint") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BusinessBigType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MarketType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConferNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignReportNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentConferNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClientCare") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackDateRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClientNeedJudge") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DonateContflag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundJudge") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamAndAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCodeOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCodeAssi") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DelayReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DelayReasonDesc") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 41:
				nFieldType = Schema.TYPE_INT;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 53:
				nFieldType = Schema.TYPE_INT;
				break;
			case 54:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_INT;
				break;
			case 56:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 57:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 83:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 89:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 94:
				nFieldType = Schema.TYPE_INT;
				break;
			case 95:
				nFieldType = Schema.TYPE_INT;
				break;
			case 96:
				nFieldType = Schema.TYPE_INT;
				break;
			case 97:
				nFieldType = Schema.TYPE_INT;
				break;
			case 98:
				nFieldType = Schema.TYPE_INT;
				break;
			case 99:
				nFieldType = Schema.TYPE_INT;
				break;
			case 100:
				nFieldType = Schema.TYPE_INT;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 103:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_DATE;
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
			case 109:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 110:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 111:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 112:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 113:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 114:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 115:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 116:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 117:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 118:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 119:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 120:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 121:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 122:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 123:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 124:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 125:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 126:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 127:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 128:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 129:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 130:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
