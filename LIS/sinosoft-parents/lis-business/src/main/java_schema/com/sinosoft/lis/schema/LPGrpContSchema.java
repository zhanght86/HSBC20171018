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
import com.sinosoft.lis.db.LPGrpContDB;

/*
 * <p>ClassName: LPGrpContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPGrpContSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGrpContSchema.class);
	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
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
	/** 保全替换比例 */
	private double EdorTransPercent;
	/** 卡单标志 */
	private String CardFlag;
	/** 综拓专员编码 */
	private String AgentCodeOper;
	/** 综拓助理编码 */
	private String AgentCodeAssi;
	/** 延迟送达原因代码 */
	private String DelayReasonCode;
	/** 延迟送达原因 */
	private String DelayReasonDesc;
	/** 产品组合佣金比例标志 */
	private String ContPlanFlag;
	/** 佣金比例值 */
	private double FYCRATE;
	/** 保单标识 */
	private String ContFlag;
	/** 销售部门 */
	private String SaleDepart;
	/** 渠道类型 */
	private String ChnlType;
	/** 网点编码 */
	private String AgentBranchesCode;
	/** 项目类型 */
	private String ProjectType;
	/** 续保标识 */
	private String RenewFlag;
	/** 续保次数 */
	private int RenewCount;
	/** 续保保单号 */
	private String RenewContNo;
	/** 期初人数 */
	private int InitNumPeople;
	/** 期初份数 */
	private int InitMult;
	/** 期初保额 */
	private double InitAmnt;
	/** 期初风险保额 */
	private double InitRiskAmnt;
	/** 期初保费 */
	private double InitPrem;
	/** 期初标准保费 */
	private double InitStandPrem;
	/** 当前风险保额 */
	private double RiskAmnt;
	/** 当前标准保费 */
	private double StandPrem;
	/** 累计投保人数 */
	private int SumNumPeople;
	/** 保险期间 */
	private int InsuYear;
	/** 保险期间单位 */
	private String InsuYearFlag;
	/** 保险年期 */
	private int Years;
	/** 交费期间 */
	private int PayEndYear;
	/** 交费期间单位 */
	private String PayEndYearFlag;
	/** 交费年期 */
	private int PayYears;
	/** 生效日期类型 */
	private String ValDateType;
	/** 终止日期 */
	private Date EndDate;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 交至日期 */
	private Date PayToDate;
	/** 自动垫交标识 */
	private String AutoPayFlag;
	/** 签单人 */
	private String SignOperator;
	/** 是否共保 */
	private String Coinsurance;
	/** 保额分摊比例 */
	private double AmntShareRate;
	/** 保费分摊比例 */
	private double PremShareRate;
	/** 扫描机构 */
	private String ScanCom;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 172;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGrpContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "GrpContNo";

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
		LPGrpContSchema cloned = (LPGrpContSchema)super.clone();
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
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>2)
			throw new IllegalArgumentException("批改类型EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值2");
		EdorType = aEdorType;
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
		if(aGrpName!=null && aGrpName.length()>120)
			throw new IllegalArgumentException("单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值120");
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
	* 冗余，标准在团体客??表
	*/
	public String getSatrap()
	{
		return Satrap;
	}
	public void setSatrap(String aSatrap)
	{
		if(aSatrap!=null && aSatrap.length()>120)
			throw new IllegalArgumentException("负责人Satrap值"+aSatrap+"的长度"+aSatrap.length()+"大于最大值120");
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
		if(aGrpSpec!=null && aGrpSpec.length()>1000)
			throw new IllegalArgumentException("集体特约GrpSpec值"+aGrpSpec+"的长度"+aGrpSpec.length()+"大于最大值1000");
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
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
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
		if(aHandlerName!=null && aHandlerName.length()>120)
			throw new IllegalArgumentException("投保经办人HandlerName值"+aHandlerName+"的长度"+aHandlerName.length()+"大于最大值120");
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
		if(aContType!=null && aContType.length()>10)
			throw new IllegalArgumentException("总单类型ContType值"+aContType+"的长度"+aContType.length()+"大于最大值10");
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
		if(aEdorCalType!=null && aEdorCalType.length()>10)
			throw new IllegalArgumentException("是否使用保全特殊算法EdorCalType值"+aEdorCalType+"的长度"+aEdorCalType.length()+"大于最大值10");
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
		if(aClientNeedJudge!=null && aClientNeedJudge.length()>10)
			throw new IllegalArgumentException("客户需求判断ClientNeedJudge值"+aClientNeedJudge+"的长度"+aClientNeedJudge.length()+"大于最大值10");
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
		if(aDonateContflag!=null && aDonateContflag.length()>10)
			throw new IllegalArgumentException("是否赠送团单DonateContflag值"+aDonateContflag+"的长度"+aDonateContflag.length()+"大于最大值10");
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
		if(aFundJudge!=null && aFundJudge.length()>10)
			throw new IllegalArgumentException("资金判断FundJudge值"+aFundJudge+"的长度"+aFundJudge.length()+"大于最大值10");
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
	public double getEdorTransPercent()
	{
		return EdorTransPercent;
	}
	public void setEdorTransPercent(double aEdorTransPercent)
	{
		EdorTransPercent = aEdorTransPercent;
	}
	public void setEdorTransPercent(String aEdorTransPercent)
	{
		if (aEdorTransPercent != null && !aEdorTransPercent.equals(""))
		{
			Double tDouble = new Double(aEdorTransPercent);
			double d = tDouble.doubleValue();
			EdorTransPercent = d;
		}
	}

	public String getCardFlag()
	{
		return CardFlag;
	}
	public void setCardFlag(String aCardFlag)
	{
		if(aCardFlag!=null && aCardFlag.length()>1)
			throw new IllegalArgumentException("卡单标志CardFlag值"+aCardFlag+"的长度"+aCardFlag.length()+"大于最大值1");
		CardFlag = aCardFlag;
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
	public String getContPlanFlag()
	{
		return ContPlanFlag;
	}
	public void setContPlanFlag(String aContPlanFlag)
	{
		if(aContPlanFlag!=null && aContPlanFlag.length()>1)
			throw new IllegalArgumentException("产品组合佣金比例标志ContPlanFlag值"+aContPlanFlag+"的长度"+aContPlanFlag.length()+"大于最大值1");
		ContPlanFlag = aContPlanFlag;
	}
	public double getFYCRATE()
	{
		return FYCRATE;
	}
	public void setFYCRATE(double aFYCRATE)
	{
		FYCRATE = aFYCRATE;
	}
	public void setFYCRATE(String aFYCRATE)
	{
		if (aFYCRATE != null && !aFYCRATE.equals(""))
		{
			Double tDouble = new Double(aFYCRATE);
			double d = tDouble.doubleValue();
			FYCRATE = d;
		}
	}

	/**
	* 0-普通，1-统括
	*/
	public String getContFlag()
	{
		return ContFlag;
	}
	public void setContFlag(String aContFlag)
	{
		if(aContFlag!=null && aContFlag.length()>1)
			throw new IllegalArgumentException("保单标识ContFlag值"+aContFlag+"的长度"+aContFlag.length()+"大于最大值1");
		ContFlag = aContFlag;
	}
	public String getSaleDepart()
	{
		return SaleDepart;
	}
	public void setSaleDepart(String aSaleDepart)
	{
		if(aSaleDepart!=null && aSaleDepart.length()>2)
			throw new IllegalArgumentException("销售部门SaleDepart值"+aSaleDepart+"的长度"+aSaleDepart.length()+"大于最大值2");
		SaleDepart = aSaleDepart;
	}
	public String getChnlType()
	{
		return ChnlType;
	}
	public void setChnlType(String aChnlType)
	{
		if(aChnlType!=null && aChnlType.length()>10)
			throw new IllegalArgumentException("渠道类型ChnlType值"+aChnlType+"的长度"+aChnlType.length()+"大于最大值10");
		ChnlType = aChnlType;
	}
	/**
	* 小额信贷使用
	*/
	public String getAgentBranchesCode()
	{
		return AgentBranchesCode;
	}
	public void setAgentBranchesCode(String aAgentBranchesCode)
	{
		if(aAgentBranchesCode!=null && aAgentBranchesCode.length()>20)
			throw new IllegalArgumentException("网点编码AgentBranchesCode值"+aAgentBranchesCode+"的长度"+aAgentBranchesCode.length()+"大于最大值20");
		AgentBranchesCode = aAgentBranchesCode;
	}
	public String getProjectType()
	{
		return ProjectType;
	}
	public void setProjectType(String aProjectType)
	{
		if(aProjectType!=null && aProjectType.length()>30)
			throw new IllegalArgumentException("项目类型ProjectType值"+aProjectType+"的长度"+aProjectType.length()+"大于最大值30");
		ProjectType = aProjectType;
	}
	/**
	* 0-否，1-是
	*/
	public String getRenewFlag()
	{
		return RenewFlag;
	}
	public void setRenewFlag(String aRenewFlag)
	{
		if(aRenewFlag!=null && aRenewFlag.length()>10)
			throw new IllegalArgumentException("续保标识RenewFlag值"+aRenewFlag+"的长度"+aRenewFlag.length()+"大于最大值10");
		RenewFlag = aRenewFlag;
	}
	public int getRenewCount()
	{
		return RenewCount;
	}
	public void setRenewCount(int aRenewCount)
	{
		RenewCount = aRenewCount;
	}
	public void setRenewCount(String aRenewCount)
	{
		if (aRenewCount != null && !aRenewCount.equals(""))
		{
			Integer tInteger = new Integer(aRenewCount);
			int i = tInteger.intValue();
			RenewCount = i;
		}
	}

	/**
	* 0-否，1-是
	*/
	public String getRenewContNo()
	{
		return RenewContNo;
	}
	public void setRenewContNo(String aRenewContNo)
	{
		if(aRenewContNo!=null && aRenewContNo.length()>20)
			throw new IllegalArgumentException("续保保单号RenewContNo值"+aRenewContNo+"的长度"+aRenewContNo.length()+"大于最大值20");
		RenewContNo = aRenewContNo;
	}
	public int getInitNumPeople()
	{
		return InitNumPeople;
	}
	public void setInitNumPeople(int aInitNumPeople)
	{
		InitNumPeople = aInitNumPeople;
	}
	public void setInitNumPeople(String aInitNumPeople)
	{
		if (aInitNumPeople != null && !aInitNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aInitNumPeople);
			int i = tInteger.intValue();
			InitNumPeople = i;
		}
	}

	public int getInitMult()
	{
		return InitMult;
	}
	public void setInitMult(int aInitMult)
	{
		InitMult = aInitMult;
	}
	public void setInitMult(String aInitMult)
	{
		if (aInitMult != null && !aInitMult.equals(""))
		{
			Integer tInteger = new Integer(aInitMult);
			int i = tInteger.intValue();
			InitMult = i;
		}
	}

	/**
	* 期初基本保额
	*/
	public double getInitAmnt()
	{
		return InitAmnt;
	}
	public void setInitAmnt(double aInitAmnt)
	{
		InitAmnt = aInitAmnt;
	}
	public void setInitAmnt(String aInitAmnt)
	{
		if (aInitAmnt != null && !aInitAmnt.equals(""))
		{
			Double tDouble = new Double(aInitAmnt);
			double d = tDouble.doubleValue();
			InitAmnt = d;
		}
	}

	public double getInitRiskAmnt()
	{
		return InitRiskAmnt;
	}
	public void setInitRiskAmnt(double aInitRiskAmnt)
	{
		InitRiskAmnt = aInitRiskAmnt;
	}
	public void setInitRiskAmnt(String aInitRiskAmnt)
	{
		if (aInitRiskAmnt != null && !aInitRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aInitRiskAmnt);
			double d = tDouble.doubleValue();
			InitRiskAmnt = d;
		}
	}

	public double getInitPrem()
	{
		return InitPrem;
	}
	public void setInitPrem(double aInitPrem)
	{
		InitPrem = aInitPrem;
	}
	public void setInitPrem(String aInitPrem)
	{
		if (aInitPrem != null && !aInitPrem.equals(""))
		{
			Double tDouble = new Double(aInitPrem);
			double d = tDouble.doubleValue();
			InitPrem = d;
		}
	}

	public double getInitStandPrem()
	{
		return InitStandPrem;
	}
	public void setInitStandPrem(double aInitStandPrem)
	{
		InitStandPrem = aInitStandPrem;
	}
	public void setInitStandPrem(String aInitStandPrem)
	{
		if (aInitStandPrem != null && !aInitStandPrem.equals(""))
		{
			Double tDouble = new Double(aInitStandPrem);
			double d = tDouble.doubleValue();
			InitStandPrem = d;
		}
	}

	public double getRiskAmnt()
	{
		return RiskAmnt;
	}
	public void setRiskAmnt(double aRiskAmnt)
	{
		RiskAmnt = aRiskAmnt;
	}
	public void setRiskAmnt(String aRiskAmnt)
	{
		if (aRiskAmnt != null && !aRiskAmnt.equals(""))
		{
			Double tDouble = new Double(aRiskAmnt);
			double d = tDouble.doubleValue();
			RiskAmnt = d;
		}
	}

	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public int getSumNumPeople()
	{
		return SumNumPeople;
	}
	public void setSumNumPeople(int aSumNumPeople)
	{
		SumNumPeople = aSumNumPeople;
	}
	public void setSumNumPeople(String aSumNumPeople)
	{
		if (aSumNumPeople != null && !aSumNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aSumNumPeople);
			int i = tInteger.intValue();
			SumNumPeople = i;
		}
	}

	public int getInsuYear()
	{
		return InsuYear;
	}
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
	}

	/**
	* A-岁，D-天，M-月，Y-年
	*/
	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		if(aInsuYearFlag!=null && aInsuYearFlag.length()>1)
			throw new IllegalArgumentException("保险期间单位InsuYearFlag值"+aInsuYearFlag+"的长度"+aInsuYearFlag.length()+"大于最大值1");
		InsuYearFlag = aInsuYearFlag;
	}
	public int getYears()
	{
		return Years;
	}
	public void setYears(int aYears)
	{
		Years = aYears;
	}
	public void setYears(String aYears)
	{
		if (aYears != null && !aYears.equals(""))
		{
			Integer tInteger = new Integer(aYears);
			int i = tInteger.intValue();
			Years = i;
		}
	}

	public int getPayEndYear()
	{
		return PayEndYear;
	}
	public void setPayEndYear(int aPayEndYear)
	{
		PayEndYear = aPayEndYear;
	}
	public void setPayEndYear(String aPayEndYear)
	{
		if (aPayEndYear != null && !aPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aPayEndYear);
			int i = tInteger.intValue();
			PayEndYear = i;
		}
	}

	/**
	* A-岁，Y-年
	*/
	public String getPayEndYearFlag()
	{
		return PayEndYearFlag;
	}
	public void setPayEndYearFlag(String aPayEndYearFlag)
	{
		if(aPayEndYearFlag!=null && aPayEndYearFlag.length()>1)
			throw new IllegalArgumentException("交费期间单位PayEndYearFlag值"+aPayEndYearFlag+"的长度"+aPayEndYearFlag.length()+"大于最大值1");
		PayEndYearFlag = aPayEndYearFlag;
	}
	/**
	* 对于交费期间单位为“年”：表示需要交费的年数<p>
	* 对于交费期间单位为“月”：表示需要交费的月数<p>
	* 对于交费期间单位为“日”：表示需要交费的天数<p>
	* 对于交费期间单位为“岁”：该字段存放将根据年龄折算成的需要交费的年数
	*/
	public int getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(int aPayYears)
	{
		PayYears = aPayYears;
	}
	public void setPayYears(String aPayYears)
	{
		if (aPayYears != null && !aPayYears.equals(""))
		{
			Integer tInteger = new Integer(aPayYears);
			int i = tInteger.intValue();
			PayYears = i;
		}
	}

	/**
	* 0-保费到账次日<p>
	* 1-保险合同约定日期
	*/
	public String getValDateType()
	{
		return ValDateType;
	}
	public void setValDateType(String aValDateType)
	{
		if(aValDateType!=null && aValDateType.length()>2)
			throw new IllegalArgumentException("生效日期类型ValDateType值"+aValDateType+"的长度"+aValDateType.length()+"大于最大值2");
		ValDateType = aValDateType;
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

	public String getPayToDate()
	{
		if( PayToDate != null )
			return fDate.getString(PayToDate);
		else
			return null;
	}
	public void setPayToDate(Date aPayToDate)
	{
		PayToDate = aPayToDate;
	}
	public void setPayToDate(String aPayToDate)
	{
		if (aPayToDate != null && !aPayToDate.equals("") )
		{
			PayToDate = fDate.getDate( aPayToDate );
		}
		else
			PayToDate = null;
	}

	/**
	* 0-否，1-是
	*/
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		if(aAutoPayFlag!=null && aAutoPayFlag.length()>1)
			throw new IllegalArgumentException("自动垫交标识AutoPayFlag值"+aAutoPayFlag+"的长度"+aAutoPayFlag.length()+"大于最大值1");
		AutoPayFlag = aAutoPayFlag;
	}
	public String getSignOperator()
	{
		return SignOperator;
	}
	public void setSignOperator(String aSignOperator)
	{
		if(aSignOperator!=null && aSignOperator.length()>30)
			throw new IllegalArgumentException("签单人SignOperator值"+aSignOperator+"的长度"+aSignOperator.length()+"大于最大值30");
		SignOperator = aSignOperator;
	}
	/**
	* 0-否；1-是
	*/
	public String getCoinsurance()
	{
		return Coinsurance;
	}
	public void setCoinsurance(String aCoinsurance)
	{
		if(aCoinsurance!=null && aCoinsurance.length()>2)
			throw new IllegalArgumentException("是否共保Coinsurance值"+aCoinsurance+"的长度"+aCoinsurance.length()+"大于最大值2");
		Coinsurance = aCoinsurance;
	}
	public double getAmntShareRate()
	{
		return AmntShareRate;
	}
	public void setAmntShareRate(double aAmntShareRate)
	{
		AmntShareRate = aAmntShareRate;
	}
	public void setAmntShareRate(String aAmntShareRate)
	{
		if (aAmntShareRate != null && !aAmntShareRate.equals(""))
		{
			Double tDouble = new Double(aAmntShareRate);
			double d = tDouble.doubleValue();
			AmntShareRate = d;
		}
	}

	public double getPremShareRate()
	{
		return PremShareRate;
	}
	public void setPremShareRate(double aPremShareRate)
	{
		PremShareRate = aPremShareRate;
	}
	public void setPremShareRate(String aPremShareRate)
	{
		if (aPremShareRate != null && !aPremShareRate.equals(""))
		{
			Double tDouble = new Double(aPremShareRate);
			double d = tDouble.doubleValue();
			PremShareRate = d;
		}
	}

	public String getScanCom()
	{
		return ScanCom;
	}
	public void setScanCom(String aScanCom)
	{
		if(aScanCom!=null && aScanCom.length()>20)
			throw new IllegalArgumentException("扫描机构ScanCom值"+aScanCom+"的长度"+aScanCom.length()+"大于最大值20");
		ScanCom = aScanCom;
	}
	/**
	* 承保公司代码
	*/
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

	/**
	* 使用另外一个 LPGrpContSchema 对象给 Schema 赋值
	* @param: aLPGrpContSchema LPGrpContSchema
	**/
	public void setSchema(LPGrpContSchema aLPGrpContSchema)
	{
		this.EdorNo = aLPGrpContSchema.getEdorNo();
		this.EdorType = aLPGrpContSchema.getEdorType();
		this.GrpContNo = aLPGrpContSchema.getGrpContNo();
		this.ProposalGrpContNo = aLPGrpContSchema.getProposalGrpContNo();
		this.PrtNo = aLPGrpContSchema.getPrtNo();
		this.SaleChnl = aLPGrpContSchema.getSaleChnl();
		this.ManageCom = aLPGrpContSchema.getManageCom();
		this.AgentCom = aLPGrpContSchema.getAgentCom();
		this.AgentType = aLPGrpContSchema.getAgentType();
		this.AgentCode = aLPGrpContSchema.getAgentCode();
		this.AgentGroup = aLPGrpContSchema.getAgentGroup();
		this.AgentCode1 = aLPGrpContSchema.getAgentCode1();
		this.Password = aLPGrpContSchema.getPassword();
		this.Password2 = aLPGrpContSchema.getPassword2();
		this.AppntNo = aLPGrpContSchema.getAppntNo();
		this.AddressNo = aLPGrpContSchema.getAddressNo();
		this.Peoples2 = aLPGrpContSchema.getPeoples2();
		this.GrpName = aLPGrpContSchema.getGrpName();
		this.BusinessType = aLPGrpContSchema.getBusinessType();
		this.GrpNature = aLPGrpContSchema.getGrpNature();
		this.RgtMoney = aLPGrpContSchema.getRgtMoney();
		this.Asset = aLPGrpContSchema.getAsset();
		this.NetProfitRate = aLPGrpContSchema.getNetProfitRate();
		this.MainBussiness = aLPGrpContSchema.getMainBussiness();
		this.Corporation = aLPGrpContSchema.getCorporation();
		this.ComAera = aLPGrpContSchema.getComAera();
		this.Fax = aLPGrpContSchema.getFax();
		this.Phone = aLPGrpContSchema.getPhone();
		this.GetFlag = aLPGrpContSchema.getGetFlag();
		this.Satrap = aLPGrpContSchema.getSatrap();
		this.EMail = aLPGrpContSchema.getEMail();
		this.FoundDate = fDate.getDate( aLPGrpContSchema.getFoundDate());
		this.GrpGroupNo = aLPGrpContSchema.getGrpGroupNo();
		this.BankCode = aLPGrpContSchema.getBankCode();
		this.BankAccNo = aLPGrpContSchema.getBankAccNo();
		this.AccName = aLPGrpContSchema.getAccName();
		this.DisputedFlag = aLPGrpContSchema.getDisputedFlag();
		this.OutPayFlag = aLPGrpContSchema.getOutPayFlag();
		this.GetPolMode = aLPGrpContSchema.getGetPolMode();
		this.Lang = aLPGrpContSchema.getLang();
		this.Currency = aLPGrpContSchema.getCurrency();
		this.LostTimes = aLPGrpContSchema.getLostTimes();
		this.PrintCount = aLPGrpContSchema.getPrintCount();
		this.RegetDate = fDate.getDate( aLPGrpContSchema.getRegetDate());
		this.LastEdorDate = fDate.getDate( aLPGrpContSchema.getLastEdorDate());
		this.LastGetDate = fDate.getDate( aLPGrpContSchema.getLastGetDate());
		this.LastLoanDate = fDate.getDate( aLPGrpContSchema.getLastLoanDate());
		this.SpecFlag = aLPGrpContSchema.getSpecFlag();
		this.GrpSpec = aLPGrpContSchema.getGrpSpec();
		this.PayMode = aLPGrpContSchema.getPayMode();
		this.SignCom = aLPGrpContSchema.getSignCom();
		this.SignDate = fDate.getDate( aLPGrpContSchema.getSignDate());
		this.SignTime = aLPGrpContSchema.getSignTime();
		this.CValiDate = fDate.getDate( aLPGrpContSchema.getCValiDate());
		this.PayIntv = aLPGrpContSchema.getPayIntv();
		this.ManageFeeRate = aLPGrpContSchema.getManageFeeRate();
		this.ExpPeoples = aLPGrpContSchema.getExpPeoples();
		this.ExpPremium = aLPGrpContSchema.getExpPremium();
		this.ExpAmnt = aLPGrpContSchema.getExpAmnt();
		this.Peoples = aLPGrpContSchema.getPeoples();
		this.Mult = aLPGrpContSchema.getMult();
		this.Prem = aLPGrpContSchema.getPrem();
		this.Amnt = aLPGrpContSchema.getAmnt();
		this.SumPrem = aLPGrpContSchema.getSumPrem();
		this.SumPay = aLPGrpContSchema.getSumPay();
		this.Dif = aLPGrpContSchema.getDif();
		this.Remark = aLPGrpContSchema.getRemark();
		this.StandbyFlag1 = aLPGrpContSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLPGrpContSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLPGrpContSchema.getStandbyFlag3();
		this.InputOperator = aLPGrpContSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLPGrpContSchema.getInputDate());
		this.InputTime = aLPGrpContSchema.getInputTime();
		this.ApproveFlag = aLPGrpContSchema.getApproveFlag();
		this.ApproveCode = aLPGrpContSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLPGrpContSchema.getApproveDate());
		this.ApproveTime = aLPGrpContSchema.getApproveTime();
		this.UWOperator = aLPGrpContSchema.getUWOperator();
		this.UWFlag = aLPGrpContSchema.getUWFlag();
		this.UWDate = fDate.getDate( aLPGrpContSchema.getUWDate());
		this.UWTime = aLPGrpContSchema.getUWTime();
		this.AppFlag = aLPGrpContSchema.getAppFlag();
		this.PolApplyDate = fDate.getDate( aLPGrpContSchema.getPolApplyDate());
		this.CustomGetPolDate = fDate.getDate( aLPGrpContSchema.getCustomGetPolDate());
		this.GetPolDate = fDate.getDate( aLPGrpContSchema.getGetPolDate());
		this.GetPolTime = aLPGrpContSchema.getGetPolTime();
		this.State = aLPGrpContSchema.getState();
		this.Operator = aLPGrpContSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPGrpContSchema.getMakeDate());
		this.MakeTime = aLPGrpContSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPGrpContSchema.getModifyDate());
		this.ModifyTime = aLPGrpContSchema.getModifyTime();
		this.EnterKind = aLPGrpContSchema.getEnterKind();
		this.AmntGrade = aLPGrpContSchema.getAmntGrade();
		this.Peoples3 = aLPGrpContSchema.getPeoples3();
		this.OnWorkPeoples = aLPGrpContSchema.getOnWorkPeoples();
		this.OffWorkPeoples = aLPGrpContSchema.getOffWorkPeoples();
		this.OtherPeoples = aLPGrpContSchema.getOtherPeoples();
		this.RelaPeoples = aLPGrpContSchema.getRelaPeoples();
		this.RelaMatePeoples = aLPGrpContSchema.getRelaMatePeoples();
		this.RelaYoungPeoples = aLPGrpContSchema.getRelaYoungPeoples();
		this.RelaOtherPeoples = aLPGrpContSchema.getRelaOtherPeoples();
		this.FirstTrialOperator = aLPGrpContSchema.getFirstTrialOperator();
		this.FirstTrialDate = fDate.getDate( aLPGrpContSchema.getFirstTrialDate());
		this.FirstTrialTime = aLPGrpContSchema.getFirstTrialTime();
		this.ReceiveOperator = aLPGrpContSchema.getReceiveOperator();
		this.ReceiveDate = fDate.getDate( aLPGrpContSchema.getReceiveDate());
		this.ReceiveTime = aLPGrpContSchema.getReceiveTime();
		this.TempFeeNo = aLPGrpContSchema.getTempFeeNo();
		this.HandlerName = aLPGrpContSchema.getHandlerName();
		this.HandlerDate = fDate.getDate( aLPGrpContSchema.getHandlerDate());
		this.HandlerPrint = aLPGrpContSchema.getHandlerPrint();
		this.AgentDate = fDate.getDate( aLPGrpContSchema.getAgentDate());
		this.BusinessBigType = aLPGrpContSchema.getBusinessBigType();
		this.MarketType = aLPGrpContSchema.getMarketType();
		this.ReportNo = aLPGrpContSchema.getReportNo();
		this.ConferNo = aLPGrpContSchema.getConferNo();
		this.SignReportNo = aLPGrpContSchema.getSignReportNo();
		this.AgentConferNo = aLPGrpContSchema.getAgentConferNo();
		this.ContType = aLPGrpContSchema.getContType();
		this.EdorCalType = aLPGrpContSchema.getEdorCalType();
		this.ClientCare = aLPGrpContSchema.getClientCare();
		this.FundReason = aLPGrpContSchema.getFundReason();
		this.BackDateRemark = aLPGrpContSchema.getBackDateRemark();
		this.ClientNeedJudge = aLPGrpContSchema.getClientNeedJudge();
		this.DonateContflag = aLPGrpContSchema.getDonateContflag();
		this.FundJudge = aLPGrpContSchema.getFundJudge();
		this.ExamAndAppNo = aLPGrpContSchema.getExamAndAppNo();
		this.EdorTransPercent = aLPGrpContSchema.getEdorTransPercent();
		this.CardFlag = aLPGrpContSchema.getCardFlag();
		this.AgentCodeOper = aLPGrpContSchema.getAgentCodeOper();
		this.AgentCodeAssi = aLPGrpContSchema.getAgentCodeAssi();
		this.DelayReasonCode = aLPGrpContSchema.getDelayReasonCode();
		this.DelayReasonDesc = aLPGrpContSchema.getDelayReasonDesc();
		this.ContPlanFlag = aLPGrpContSchema.getContPlanFlag();
		this.FYCRATE = aLPGrpContSchema.getFYCRATE();
		this.ContFlag = aLPGrpContSchema.getContFlag();
		this.SaleDepart = aLPGrpContSchema.getSaleDepart();
		this.ChnlType = aLPGrpContSchema.getChnlType();
		this.AgentBranchesCode = aLPGrpContSchema.getAgentBranchesCode();
		this.ProjectType = aLPGrpContSchema.getProjectType();
		this.RenewFlag = aLPGrpContSchema.getRenewFlag();
		this.RenewCount = aLPGrpContSchema.getRenewCount();
		this.RenewContNo = aLPGrpContSchema.getRenewContNo();
		this.InitNumPeople = aLPGrpContSchema.getInitNumPeople();
		this.InitMult = aLPGrpContSchema.getInitMult();
		this.InitAmnt = aLPGrpContSchema.getInitAmnt();
		this.InitRiskAmnt = aLPGrpContSchema.getInitRiskAmnt();
		this.InitPrem = aLPGrpContSchema.getInitPrem();
		this.InitStandPrem = aLPGrpContSchema.getInitStandPrem();
		this.RiskAmnt = aLPGrpContSchema.getRiskAmnt();
		this.StandPrem = aLPGrpContSchema.getStandPrem();
		this.SumNumPeople = aLPGrpContSchema.getSumNumPeople();
		this.InsuYear = aLPGrpContSchema.getInsuYear();
		this.InsuYearFlag = aLPGrpContSchema.getInsuYearFlag();
		this.Years = aLPGrpContSchema.getYears();
		this.PayEndYear = aLPGrpContSchema.getPayEndYear();
		this.PayEndYearFlag = aLPGrpContSchema.getPayEndYearFlag();
		this.PayYears = aLPGrpContSchema.getPayYears();
		this.ValDateType = aLPGrpContSchema.getValDateType();
		this.EndDate = fDate.getDate( aLPGrpContSchema.getEndDate());
		this.FirstPayDate = fDate.getDate( aLPGrpContSchema.getFirstPayDate());
		this.PayEndDate = fDate.getDate( aLPGrpContSchema.getPayEndDate());
		this.PayToDate = fDate.getDate( aLPGrpContSchema.getPayToDate());
		this.AutoPayFlag = aLPGrpContSchema.getAutoPayFlag();
		this.SignOperator = aLPGrpContSchema.getSignOperator();
		this.Coinsurance = aLPGrpContSchema.getCoinsurance();
		this.AmntShareRate = aLPGrpContSchema.getAmntShareRate();
		this.PremShareRate = aLPGrpContSchema.getPremShareRate();
		this.ScanCom = aLPGrpContSchema.getScanCom();
		this.ComCode = aLPGrpContSchema.getComCode();
		this.ModifyOperator = aLPGrpContSchema.getModifyOperator();
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

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

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

			this.EdorTransPercent = rs.getDouble("EdorTransPercent");
			if( rs.getString("CardFlag") == null )
				this.CardFlag = null;
			else
				this.CardFlag = rs.getString("CardFlag").trim();

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

			if( rs.getString("ContPlanFlag") == null )
				this.ContPlanFlag = null;
			else
				this.ContPlanFlag = rs.getString("ContPlanFlag").trim();

			this.FYCRATE = rs.getDouble("FYCRATE");
			if( rs.getString("ContFlag") == null )
				this.ContFlag = null;
			else
				this.ContFlag = rs.getString("ContFlag").trim();

			if( rs.getString("SaleDepart") == null )
				this.SaleDepart = null;
			else
				this.SaleDepart = rs.getString("SaleDepart").trim();

			if( rs.getString("ChnlType") == null )
				this.ChnlType = null;
			else
				this.ChnlType = rs.getString("ChnlType").trim();

			if( rs.getString("AgentBranchesCode") == null )
				this.AgentBranchesCode = null;
			else
				this.AgentBranchesCode = rs.getString("AgentBranchesCode").trim();

			if( rs.getString("ProjectType") == null )
				this.ProjectType = null;
			else
				this.ProjectType = rs.getString("ProjectType").trim();

			if( rs.getString("RenewFlag") == null )
				this.RenewFlag = null;
			else
				this.RenewFlag = rs.getString("RenewFlag").trim();

			this.RenewCount = rs.getInt("RenewCount");
			if( rs.getString("RenewContNo") == null )
				this.RenewContNo = null;
			else
				this.RenewContNo = rs.getString("RenewContNo").trim();

			this.InitNumPeople = rs.getInt("InitNumPeople");
			this.InitMult = rs.getInt("InitMult");
			this.InitAmnt = rs.getDouble("InitAmnt");
			this.InitRiskAmnt = rs.getDouble("InitRiskAmnt");
			this.InitPrem = rs.getDouble("InitPrem");
			this.InitStandPrem = rs.getDouble("InitStandPrem");
			this.RiskAmnt = rs.getDouble("RiskAmnt");
			this.StandPrem = rs.getDouble("StandPrem");
			this.SumNumPeople = rs.getInt("SumNumPeople");
			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.Years = rs.getInt("Years");
			this.PayEndYear = rs.getInt("PayEndYear");
			if( rs.getString("PayEndYearFlag") == null )
				this.PayEndYearFlag = null;
			else
				this.PayEndYearFlag = rs.getString("PayEndYearFlag").trim();

			this.PayYears = rs.getInt("PayYears");
			if( rs.getString("ValDateType") == null )
				this.ValDateType = null;
			else
				this.ValDateType = rs.getString("ValDateType").trim();

			this.EndDate = rs.getDate("EndDate");
			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			this.PayToDate = rs.getDate("PayToDate");
			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			if( rs.getString("SignOperator") == null )
				this.SignOperator = null;
			else
				this.SignOperator = rs.getString("SignOperator").trim();

			if( rs.getString("Coinsurance") == null )
				this.Coinsurance = null;
			else
				this.Coinsurance = rs.getString("Coinsurance").trim();

			this.AmntShareRate = rs.getDouble("AmntShareRate");
			this.PremShareRate = rs.getDouble("PremShareRate");
			if( rs.getString("ScanCom") == null )
				this.ScanCom = null;
			else
				this.ScanCom = rs.getString("ScanCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPGrpCont表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGrpContSchema getSchema()
	{
		LPGrpContSchema aLPGrpContSchema = new LPGrpContSchema();
		aLPGrpContSchema.setSchema(this);
		return aLPGrpContSchema;
	}

	public LPGrpContDB getDB()
	{
		LPGrpContDB aDBOper = new LPGrpContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpCont描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append( ChgData.chgData(EdorTransPercent));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeAssi)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FYCRATE));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleDepart)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChnlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentBranchesCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProjectType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RenewCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RenewContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitMult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitRiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InitStandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumNumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayEndYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayEndYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValDateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Coinsurance)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AmntShareRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremShareRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGrpCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Password2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AddressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Peoples2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			BusinessType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			GrpNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			RgtMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			Asset = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			NetProfitRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			MainBussiness = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Corporation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ComAera = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Satrap = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			GrpGroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			LostTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).intValue();
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).intValue();
			RegetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			LastEdorDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			LastGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			LastLoanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47,SysConst.PACKAGESPILTER));
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			GrpSpec = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54,SysConst.PACKAGESPILTER));
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).intValue();
			ManageFeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).doubleValue();
			ExpPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).intValue();
			ExpPremium = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).doubleValue();
			ExpAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).doubleValue();
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,64,SysConst.PACKAGESPILTER))).doubleValue();
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,66,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83,SysConst.PACKAGESPILTER));
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85,SysConst.PACKAGESPILTER));
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			EnterKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			AmntGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			Peoples3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,95,SysConst.PACKAGESPILTER))).intValue();
			OnWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,96,SysConst.PACKAGESPILTER))).intValue();
			OffWorkPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,97,SysConst.PACKAGESPILTER))).intValue();
			OtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,98,SysConst.PACKAGESPILTER))).intValue();
			RelaPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,99,SysConst.PACKAGESPILTER))).intValue();
			RelaMatePeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,100,SysConst.PACKAGESPILTER))).intValue();
			RelaYoungPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,101,SysConst.PACKAGESPILTER))).intValue();
			RelaOtherPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,102,SysConst.PACKAGESPILTER))).intValue();
			FirstTrialOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			FirstTrialDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104,SysConst.PACKAGESPILTER));
			FirstTrialTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			ReceiveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			ReceiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107,SysConst.PACKAGESPILTER));
			ReceiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
			HandlerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
			HandlerDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111,SysConst.PACKAGESPILTER));
			HandlerPrint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112, SysConst.PACKAGESPILTER );
			AgentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 113,SysConst.PACKAGESPILTER));
			BusinessBigType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114, SysConst.PACKAGESPILTER );
			MarketType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			ReportNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 116, SysConst.PACKAGESPILTER );
			ConferNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 117, SysConst.PACKAGESPILTER );
			SignReportNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 118, SysConst.PACKAGESPILTER );
			AgentConferNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 119, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 120, SysConst.PACKAGESPILTER );
			EdorCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 121, SysConst.PACKAGESPILTER );
			ClientCare = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 122, SysConst.PACKAGESPILTER );
			FundReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 123, SysConst.PACKAGESPILTER );
			BackDateRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 124, SysConst.PACKAGESPILTER );
			ClientNeedJudge = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 125, SysConst.PACKAGESPILTER );
			DonateContflag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 126, SysConst.PACKAGESPILTER );
			FundJudge = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 127, SysConst.PACKAGESPILTER );
			ExamAndAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 128, SysConst.PACKAGESPILTER );
			EdorTransPercent = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,129,SysConst.PACKAGESPILTER))).doubleValue();
			CardFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 130, SysConst.PACKAGESPILTER );
			AgentCodeOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 131, SysConst.PACKAGESPILTER );
			AgentCodeAssi = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 132, SysConst.PACKAGESPILTER );
			DelayReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 133, SysConst.PACKAGESPILTER );
			DelayReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 134, SysConst.PACKAGESPILTER );
			ContPlanFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 135, SysConst.PACKAGESPILTER );
			FYCRATE = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,136,SysConst.PACKAGESPILTER))).doubleValue();
			ContFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 137, SysConst.PACKAGESPILTER );
			SaleDepart = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 138, SysConst.PACKAGESPILTER );
			ChnlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 139, SysConst.PACKAGESPILTER );
			AgentBranchesCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 140, SysConst.PACKAGESPILTER );
			ProjectType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 141, SysConst.PACKAGESPILTER );
			RenewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 142, SysConst.PACKAGESPILTER );
			RenewCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,143,SysConst.PACKAGESPILTER))).intValue();
			RenewContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 144, SysConst.PACKAGESPILTER );
			InitNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,145,SysConst.PACKAGESPILTER))).intValue();
			InitMult= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,146,SysConst.PACKAGESPILTER))).intValue();
			InitAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,147,SysConst.PACKAGESPILTER))).doubleValue();
			InitRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,148,SysConst.PACKAGESPILTER))).doubleValue();
			InitPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,149,SysConst.PACKAGESPILTER))).doubleValue();
			InitStandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,150,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,151,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,152,SysConst.PACKAGESPILTER))).doubleValue();
			SumNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,153,SysConst.PACKAGESPILTER))).intValue();
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,154,SysConst.PACKAGESPILTER))).intValue();
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 155, SysConst.PACKAGESPILTER );
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,156,SysConst.PACKAGESPILTER))).intValue();
			PayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,157,SysConst.PACKAGESPILTER))).intValue();
			PayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 158, SysConst.PACKAGESPILTER );
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,159,SysConst.PACKAGESPILTER))).intValue();
			ValDateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 160, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 161,SysConst.PACKAGESPILTER));
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 162,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 163,SysConst.PACKAGESPILTER));
			PayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 164,SysConst.PACKAGESPILTER));
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 165, SysConst.PACKAGESPILTER );
			SignOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 166, SysConst.PACKAGESPILTER );
			Coinsurance = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 167, SysConst.PACKAGESPILTER );
			AmntShareRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,168,SysConst.PACKAGESPILTER))).doubleValue();
			PremShareRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,169,SysConst.PACKAGESPILTER))).doubleValue();
			ScanCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 170, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 171, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 172, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContSchema";
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
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
		if (FCode.equalsIgnoreCase("EdorTransPercent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorTransPercent));
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardFlag));
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
		if (FCode.equalsIgnoreCase("ContPlanFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanFlag));
		}
		if (FCode.equalsIgnoreCase("FYCRATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FYCRATE));
		}
		if (FCode.equalsIgnoreCase("ContFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContFlag));
		}
		if (FCode.equalsIgnoreCase("SaleDepart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleDepart));
		}
		if (FCode.equalsIgnoreCase("ChnlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChnlType));
		}
		if (FCode.equalsIgnoreCase("AgentBranchesCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentBranchesCode));
		}
		if (FCode.equalsIgnoreCase("ProjectType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProjectType));
		}
		if (FCode.equalsIgnoreCase("RenewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewFlag));
		}
		if (FCode.equalsIgnoreCase("RenewCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewCount));
		}
		if (FCode.equalsIgnoreCase("RenewContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RenewContNo));
		}
		if (FCode.equalsIgnoreCase("InitNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitNumPeople));
		}
		if (FCode.equalsIgnoreCase("InitMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitMult));
		}
		if (FCode.equalsIgnoreCase("InitAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitAmnt));
		}
		if (FCode.equalsIgnoreCase("InitRiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitRiskAmnt));
		}
		if (FCode.equalsIgnoreCase("InitPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitPrem));
		}
		if (FCode.equalsIgnoreCase("InitStandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitStandPrem));
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmnt));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumNumPeople));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYear));
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayEndYearFlag));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValDateType));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("SignOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignOperator));
		}
		if (FCode.equalsIgnoreCase("Coinsurance"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Coinsurance));
		}
		if (FCode.equalsIgnoreCase("AmntShareRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntShareRate));
		}
		if (FCode.equalsIgnoreCase("PremShareRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremShareRate));
		}
		if (FCode.equalsIgnoreCase("ScanCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Password2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AddressNo);
				break;
			case 16:
				strFieldValue = String.valueOf(Peoples2);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BusinessType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(GrpNature);
				break;
			case 20:
				strFieldValue = String.valueOf(RgtMoney);
				break;
			case 21:
				strFieldValue = String.valueOf(Asset);
				break;
			case 22:
				strFieldValue = String.valueOf(NetProfitRate);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MainBussiness);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Corporation);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ComAera);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Satrap);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(GrpGroupNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(DisputedFlag);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(GetPolMode);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 41:
				strFieldValue = String.valueOf(LostTimes);
				break;
			case 42:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRegetDate()));
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastEdorDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastLoanDate()));
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(GrpSpec);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 54:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 55:
				strFieldValue = String.valueOf(ManageFeeRate);
				break;
			case 56:
				strFieldValue = String.valueOf(ExpPeoples);
				break;
			case 57:
				strFieldValue = String.valueOf(ExpPremium);
				break;
			case 58:
				strFieldValue = String.valueOf(ExpAmnt);
				break;
			case 59:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 60:
				strFieldValue = String.valueOf(Mult);
				break;
			case 61:
				strFieldValue = String.valueOf(Prem);
				break;
			case 62:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 63:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 64:
				strFieldValue = String.valueOf(SumPay);
				break;
			case 65:
				strFieldValue = String.valueOf(Dif);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(EnterKind);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(AmntGrade);
				break;
			case 94:
				strFieldValue = String.valueOf(Peoples3);
				break;
			case 95:
				strFieldValue = String.valueOf(OnWorkPeoples);
				break;
			case 96:
				strFieldValue = String.valueOf(OffWorkPeoples);
				break;
			case 97:
				strFieldValue = String.valueOf(OtherPeoples);
				break;
			case 98:
				strFieldValue = String.valueOf(RelaPeoples);
				break;
			case 99:
				strFieldValue = String.valueOf(RelaMatePeoples);
				break;
			case 100:
				strFieldValue = String.valueOf(RelaYoungPeoples);
				break;
			case 101:
				strFieldValue = String.valueOf(RelaOtherPeoples);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialOperator);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialTime);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(ReceiveOperator);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(ReceiveTime);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(HandlerName);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandlerDate()));
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(HandlerPrint);
				break;
			case 112:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAgentDate()));
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(BusinessBigType);
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(MarketType);
				break;
			case 115:
				strFieldValue = StrTool.GBKToUnicode(ReportNo);
				break;
			case 116:
				strFieldValue = StrTool.GBKToUnicode(ConferNo);
				break;
			case 117:
				strFieldValue = StrTool.GBKToUnicode(SignReportNo);
				break;
			case 118:
				strFieldValue = StrTool.GBKToUnicode(AgentConferNo);
				break;
			case 119:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 120:
				strFieldValue = StrTool.GBKToUnicode(EdorCalType);
				break;
			case 121:
				strFieldValue = StrTool.GBKToUnicode(ClientCare);
				break;
			case 122:
				strFieldValue = StrTool.GBKToUnicode(FundReason);
				break;
			case 123:
				strFieldValue = StrTool.GBKToUnicode(BackDateRemark);
				break;
			case 124:
				strFieldValue = StrTool.GBKToUnicode(ClientNeedJudge);
				break;
			case 125:
				strFieldValue = StrTool.GBKToUnicode(DonateContflag);
				break;
			case 126:
				strFieldValue = StrTool.GBKToUnicode(FundJudge);
				break;
			case 127:
				strFieldValue = StrTool.GBKToUnicode(ExamAndAppNo);
				break;
			case 128:
				strFieldValue = String.valueOf(EdorTransPercent);
				break;
			case 129:
				strFieldValue = StrTool.GBKToUnicode(CardFlag);
				break;
			case 130:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeOper);
				break;
			case 131:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeAssi);
				break;
			case 132:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonCode);
				break;
			case 133:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonDesc);
				break;
			case 134:
				strFieldValue = StrTool.GBKToUnicode(ContPlanFlag);
				break;
			case 135:
				strFieldValue = String.valueOf(FYCRATE);
				break;
			case 136:
				strFieldValue = StrTool.GBKToUnicode(ContFlag);
				break;
			case 137:
				strFieldValue = StrTool.GBKToUnicode(SaleDepart);
				break;
			case 138:
				strFieldValue = StrTool.GBKToUnicode(ChnlType);
				break;
			case 139:
				strFieldValue = StrTool.GBKToUnicode(AgentBranchesCode);
				break;
			case 140:
				strFieldValue = StrTool.GBKToUnicode(ProjectType);
				break;
			case 141:
				strFieldValue = StrTool.GBKToUnicode(RenewFlag);
				break;
			case 142:
				strFieldValue = String.valueOf(RenewCount);
				break;
			case 143:
				strFieldValue = StrTool.GBKToUnicode(RenewContNo);
				break;
			case 144:
				strFieldValue = String.valueOf(InitNumPeople);
				break;
			case 145:
				strFieldValue = String.valueOf(InitMult);
				break;
			case 146:
				strFieldValue = String.valueOf(InitAmnt);
				break;
			case 147:
				strFieldValue = String.valueOf(InitRiskAmnt);
				break;
			case 148:
				strFieldValue = String.valueOf(InitPrem);
				break;
			case 149:
				strFieldValue = String.valueOf(InitStandPrem);
				break;
			case 150:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 151:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 152:
				strFieldValue = String.valueOf(SumNumPeople);
				break;
			case 153:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 154:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 155:
				strFieldValue = String.valueOf(Years);
				break;
			case 156:
				strFieldValue = String.valueOf(PayEndYear);
				break;
			case 157:
				strFieldValue = StrTool.GBKToUnicode(PayEndYearFlag);
				break;
			case 158:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 159:
				strFieldValue = StrTool.GBKToUnicode(ValDateType);
				break;
			case 160:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 161:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 162:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 163:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
				break;
			case 164:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 165:
				strFieldValue = StrTool.GBKToUnicode(SignOperator);
				break;
			case 166:
				strFieldValue = StrTool.GBKToUnicode(Coinsurance);
				break;
			case 167:
				strFieldValue = String.valueOf(AmntShareRate);
				break;
			case 168:
				strFieldValue = String.valueOf(PremShareRate);
				break;
			case 169:
				strFieldValue = StrTool.GBKToUnicode(ScanCom);
				break;
			case 170:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 171:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
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
		if (FCode.equalsIgnoreCase("EdorTransPercent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EdorTransPercent = d;
			}
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardFlag = FValue.trim();
			}
			else
				CardFlag = null;
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
		if (FCode.equalsIgnoreCase("ContPlanFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanFlag = FValue.trim();
			}
			else
				ContPlanFlag = null;
		}
		if (FCode.equalsIgnoreCase("FYCRATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FYCRATE = d;
			}
		}
		if (FCode.equalsIgnoreCase("ContFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContFlag = FValue.trim();
			}
			else
				ContFlag = null;
		}
		if (FCode.equalsIgnoreCase("SaleDepart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleDepart = FValue.trim();
			}
			else
				SaleDepart = null;
		}
		if (FCode.equalsIgnoreCase("ChnlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChnlType = FValue.trim();
			}
			else
				ChnlType = null;
		}
		if (FCode.equalsIgnoreCase("AgentBranchesCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentBranchesCode = FValue.trim();
			}
			else
				AgentBranchesCode = null;
		}
		if (FCode.equalsIgnoreCase("ProjectType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProjectType = FValue.trim();
			}
			else
				ProjectType = null;
		}
		if (FCode.equalsIgnoreCase("RenewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewFlag = FValue.trim();
			}
			else
				RenewFlag = null;
		}
		if (FCode.equalsIgnoreCase("RenewCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RenewCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("RenewContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RenewContNo = FValue.trim();
			}
			else
				RenewContNo = null;
		}
		if (FCode.equalsIgnoreCase("InitNumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InitNumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("InitMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InitMult = i;
			}
		}
		if (FCode.equalsIgnoreCase("InitAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitRiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitRiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("InitStandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InitStandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("RiskAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiskAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumNumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumNumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayEndYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayEndYearFlag = FValue.trim();
			}
			else
				PayEndYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
			}
		}
		if (FCode.equalsIgnoreCase("ValDateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValDateType = FValue.trim();
			}
			else
				ValDateType = null;
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
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayToDate = fDate.getDate( FValue );
			}
			else
				PayToDate = null;
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayFlag = FValue.trim();
			}
			else
				AutoPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("SignOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignOperator = FValue.trim();
			}
			else
				SignOperator = null;
		}
		if (FCode.equalsIgnoreCase("Coinsurance"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Coinsurance = FValue.trim();
			}
			else
				Coinsurance = null;
		}
		if (FCode.equalsIgnoreCase("AmntShareRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AmntShareRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremShareRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremShareRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ScanCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanCom = FValue.trim();
			}
			else
				ScanCom = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPGrpContSchema other = (LPGrpContSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
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
			&& EdorTransPercent == other.getEdorTransPercent()
			&& CardFlag.equals(other.getCardFlag())
			&& AgentCodeOper.equals(other.getAgentCodeOper())
			&& AgentCodeAssi.equals(other.getAgentCodeAssi())
			&& DelayReasonCode.equals(other.getDelayReasonCode())
			&& DelayReasonDesc.equals(other.getDelayReasonDesc())
			&& ContPlanFlag.equals(other.getContPlanFlag())
			&& FYCRATE == other.getFYCRATE()
			&& ContFlag.equals(other.getContFlag())
			&& SaleDepart.equals(other.getSaleDepart())
			&& ChnlType.equals(other.getChnlType())
			&& AgentBranchesCode.equals(other.getAgentBranchesCode())
			&& ProjectType.equals(other.getProjectType())
			&& RenewFlag.equals(other.getRenewFlag())
			&& RenewCount == other.getRenewCount()
			&& RenewContNo.equals(other.getRenewContNo())
			&& InitNumPeople == other.getInitNumPeople()
			&& InitMult == other.getInitMult()
			&& InitAmnt == other.getInitAmnt()
			&& InitRiskAmnt == other.getInitRiskAmnt()
			&& InitPrem == other.getInitPrem()
			&& InitStandPrem == other.getInitStandPrem()
			&& RiskAmnt == other.getRiskAmnt()
			&& StandPrem == other.getStandPrem()
			&& SumNumPeople == other.getSumNumPeople()
			&& InsuYear == other.getInsuYear()
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& Years == other.getYears()
			&& PayEndYear == other.getPayEndYear()
			&& PayEndYearFlag.equals(other.getPayEndYearFlag())
			&& PayYears == other.getPayYears()
			&& ValDateType.equals(other.getValDateType())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& fDate.getString(PayToDate).equals(other.getPayToDate())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& SignOperator.equals(other.getSignOperator())
			&& Coinsurance.equals(other.getCoinsurance())
			&& AmntShareRate == other.getAmntShareRate()
			&& PremShareRate == other.getPremShareRate()
			&& ScanCom.equals(other.getScanCom())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 4;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 7;
		}
		if( strFieldName.equals("AgentType") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 9;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 10;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 11;
		}
		if( strFieldName.equals("Password") ) {
			return 12;
		}
		if( strFieldName.equals("Password2") ) {
			return 13;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 14;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 15;
		}
		if( strFieldName.equals("Peoples2") ) {
			return 16;
		}
		if( strFieldName.equals("GrpName") ) {
			return 17;
		}
		if( strFieldName.equals("BusinessType") ) {
			return 18;
		}
		if( strFieldName.equals("GrpNature") ) {
			return 19;
		}
		if( strFieldName.equals("RgtMoney") ) {
			return 20;
		}
		if( strFieldName.equals("Asset") ) {
			return 21;
		}
		if( strFieldName.equals("NetProfitRate") ) {
			return 22;
		}
		if( strFieldName.equals("MainBussiness") ) {
			return 23;
		}
		if( strFieldName.equals("Corporation") ) {
			return 24;
		}
		if( strFieldName.equals("ComAera") ) {
			return 25;
		}
		if( strFieldName.equals("Fax") ) {
			return 26;
		}
		if( strFieldName.equals("Phone") ) {
			return 27;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 28;
		}
		if( strFieldName.equals("Satrap") ) {
			return 29;
		}
		if( strFieldName.equals("EMail") ) {
			return 30;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 31;
		}
		if( strFieldName.equals("GrpGroupNo") ) {
			return 32;
		}
		if( strFieldName.equals("BankCode") ) {
			return 33;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 34;
		}
		if( strFieldName.equals("AccName") ) {
			return 35;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return 36;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 37;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return 38;
		}
		if( strFieldName.equals("Lang") ) {
			return 39;
		}
		if( strFieldName.equals("Currency") ) {
			return 40;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 41;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 42;
		}
		if( strFieldName.equals("RegetDate") ) {
			return 43;
		}
		if( strFieldName.equals("LastEdorDate") ) {
			return 44;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return 45;
		}
		if( strFieldName.equals("LastLoanDate") ) {
			return 46;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 47;
		}
		if( strFieldName.equals("GrpSpec") ) {
			return 48;
		}
		if( strFieldName.equals("PayMode") ) {
			return 49;
		}
		if( strFieldName.equals("SignCom") ) {
			return 50;
		}
		if( strFieldName.equals("SignDate") ) {
			return 51;
		}
		if( strFieldName.equals("SignTime") ) {
			return 52;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 53;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 54;
		}
		if( strFieldName.equals("ManageFeeRate") ) {
			return 55;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return 56;
		}
		if( strFieldName.equals("ExpPremium") ) {
			return 57;
		}
		if( strFieldName.equals("ExpAmnt") ) {
			return 58;
		}
		if( strFieldName.equals("Peoples") ) {
			return 59;
		}
		if( strFieldName.equals("Mult") ) {
			return 60;
		}
		if( strFieldName.equals("Prem") ) {
			return 61;
		}
		if( strFieldName.equals("Amnt") ) {
			return 62;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 63;
		}
		if( strFieldName.equals("SumPay") ) {
			return 64;
		}
		if( strFieldName.equals("Dif") ) {
			return 65;
		}
		if( strFieldName.equals("Remark") ) {
			return 66;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 67;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 68;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 69;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 70;
		}
		if( strFieldName.equals("InputDate") ) {
			return 71;
		}
		if( strFieldName.equals("InputTime") ) {
			return 72;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 73;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 74;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 75;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 76;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 77;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 78;
		}
		if( strFieldName.equals("UWDate") ) {
			return 79;
		}
		if( strFieldName.equals("UWTime") ) {
			return 80;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 81;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 82;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 83;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 84;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 85;
		}
		if( strFieldName.equals("State") ) {
			return 86;
		}
		if( strFieldName.equals("Operator") ) {
			return 87;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 88;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 89;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 90;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 91;
		}
		if( strFieldName.equals("EnterKind") ) {
			return 92;
		}
		if( strFieldName.equals("AmntGrade") ) {
			return 93;
		}
		if( strFieldName.equals("Peoples3") ) {
			return 94;
		}
		if( strFieldName.equals("OnWorkPeoples") ) {
			return 95;
		}
		if( strFieldName.equals("OffWorkPeoples") ) {
			return 96;
		}
		if( strFieldName.equals("OtherPeoples") ) {
			return 97;
		}
		if( strFieldName.equals("RelaPeoples") ) {
			return 98;
		}
		if( strFieldName.equals("RelaMatePeoples") ) {
			return 99;
		}
		if( strFieldName.equals("RelaYoungPeoples") ) {
			return 100;
		}
		if( strFieldName.equals("RelaOtherPeoples") ) {
			return 101;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return 102;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return 103;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return 104;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return 105;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 106;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return 107;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 108;
		}
		if( strFieldName.equals("HandlerName") ) {
			return 109;
		}
		if( strFieldName.equals("HandlerDate") ) {
			return 110;
		}
		if( strFieldName.equals("HandlerPrint") ) {
			return 111;
		}
		if( strFieldName.equals("AgentDate") ) {
			return 112;
		}
		if( strFieldName.equals("BusinessBigType") ) {
			return 113;
		}
		if( strFieldName.equals("MarketType") ) {
			return 114;
		}
		if( strFieldName.equals("ReportNo") ) {
			return 115;
		}
		if( strFieldName.equals("ConferNo") ) {
			return 116;
		}
		if( strFieldName.equals("SignReportNo") ) {
			return 117;
		}
		if( strFieldName.equals("AgentConferNo") ) {
			return 118;
		}
		if( strFieldName.equals("ContType") ) {
			return 119;
		}
		if( strFieldName.equals("EdorCalType") ) {
			return 120;
		}
		if( strFieldName.equals("ClientCare") ) {
			return 121;
		}
		if( strFieldName.equals("FundReason") ) {
			return 122;
		}
		if( strFieldName.equals("BackDateRemark") ) {
			return 123;
		}
		if( strFieldName.equals("ClientNeedJudge") ) {
			return 124;
		}
		if( strFieldName.equals("DonateContflag") ) {
			return 125;
		}
		if( strFieldName.equals("FundJudge") ) {
			return 126;
		}
		if( strFieldName.equals("ExamAndAppNo") ) {
			return 127;
		}
		if( strFieldName.equals("EdorTransPercent") ) {
			return 128;
		}
		if( strFieldName.equals("CardFlag") ) {
			return 129;
		}
		if( strFieldName.equals("AgentCodeOper") ) {
			return 130;
		}
		if( strFieldName.equals("AgentCodeAssi") ) {
			return 131;
		}
		if( strFieldName.equals("DelayReasonCode") ) {
			return 132;
		}
		if( strFieldName.equals("DelayReasonDesc") ) {
			return 133;
		}
		if( strFieldName.equals("ContPlanFlag") ) {
			return 134;
		}
		if( strFieldName.equals("FYCRATE") ) {
			return 135;
		}
		if( strFieldName.equals("ContFlag") ) {
			return 136;
		}
		if( strFieldName.equals("SaleDepart") ) {
			return 137;
		}
		if( strFieldName.equals("ChnlType") ) {
			return 138;
		}
		if( strFieldName.equals("AgentBranchesCode") ) {
			return 139;
		}
		if( strFieldName.equals("ProjectType") ) {
			return 140;
		}
		if( strFieldName.equals("RenewFlag") ) {
			return 141;
		}
		if( strFieldName.equals("RenewCount") ) {
			return 142;
		}
		if( strFieldName.equals("RenewContNo") ) {
			return 143;
		}
		if( strFieldName.equals("InitNumPeople") ) {
			return 144;
		}
		if( strFieldName.equals("InitMult") ) {
			return 145;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return 146;
		}
		if( strFieldName.equals("InitRiskAmnt") ) {
			return 147;
		}
		if( strFieldName.equals("InitPrem") ) {
			return 148;
		}
		if( strFieldName.equals("InitStandPrem") ) {
			return 149;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 150;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 151;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return 152;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 153;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 154;
		}
		if( strFieldName.equals("Years") ) {
			return 155;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return 156;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return 157;
		}
		if( strFieldName.equals("PayYears") ) {
			return 158;
		}
		if( strFieldName.equals("ValDateType") ) {
			return 159;
		}
		if( strFieldName.equals("EndDate") ) {
			return 160;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 161;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 162;
		}
		if( strFieldName.equals("PayToDate") ) {
			return 163;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 164;
		}
		if( strFieldName.equals("SignOperator") ) {
			return 165;
		}
		if( strFieldName.equals("Coinsurance") ) {
			return 166;
		}
		if( strFieldName.equals("AmntShareRate") ) {
			return 167;
		}
		if( strFieldName.equals("PremShareRate") ) {
			return 168;
		}
		if( strFieldName.equals("ScanCom") ) {
			return 169;
		}
		if( strFieldName.equals("ComCode") ) {
			return 170;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 171;
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
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ProposalGrpContNo";
				break;
			case 4:
				strFieldName = "PrtNo";
				break;
			case 5:
				strFieldName = "SaleChnl";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "AgentCom";
				break;
			case 8:
				strFieldName = "AgentType";
				break;
			case 9:
				strFieldName = "AgentCode";
				break;
			case 10:
				strFieldName = "AgentGroup";
				break;
			case 11:
				strFieldName = "AgentCode1";
				break;
			case 12:
				strFieldName = "Password";
				break;
			case 13:
				strFieldName = "Password2";
				break;
			case 14:
				strFieldName = "AppntNo";
				break;
			case 15:
				strFieldName = "AddressNo";
				break;
			case 16:
				strFieldName = "Peoples2";
				break;
			case 17:
				strFieldName = "GrpName";
				break;
			case 18:
				strFieldName = "BusinessType";
				break;
			case 19:
				strFieldName = "GrpNature";
				break;
			case 20:
				strFieldName = "RgtMoney";
				break;
			case 21:
				strFieldName = "Asset";
				break;
			case 22:
				strFieldName = "NetProfitRate";
				break;
			case 23:
				strFieldName = "MainBussiness";
				break;
			case 24:
				strFieldName = "Corporation";
				break;
			case 25:
				strFieldName = "ComAera";
				break;
			case 26:
				strFieldName = "Fax";
				break;
			case 27:
				strFieldName = "Phone";
				break;
			case 28:
				strFieldName = "GetFlag";
				break;
			case 29:
				strFieldName = "Satrap";
				break;
			case 30:
				strFieldName = "EMail";
				break;
			case 31:
				strFieldName = "FoundDate";
				break;
			case 32:
				strFieldName = "GrpGroupNo";
				break;
			case 33:
				strFieldName = "BankCode";
				break;
			case 34:
				strFieldName = "BankAccNo";
				break;
			case 35:
				strFieldName = "AccName";
				break;
			case 36:
				strFieldName = "DisputedFlag";
				break;
			case 37:
				strFieldName = "OutPayFlag";
				break;
			case 38:
				strFieldName = "GetPolMode";
				break;
			case 39:
				strFieldName = "Lang";
				break;
			case 40:
				strFieldName = "Currency";
				break;
			case 41:
				strFieldName = "LostTimes";
				break;
			case 42:
				strFieldName = "PrintCount";
				break;
			case 43:
				strFieldName = "RegetDate";
				break;
			case 44:
				strFieldName = "LastEdorDate";
				break;
			case 45:
				strFieldName = "LastGetDate";
				break;
			case 46:
				strFieldName = "LastLoanDate";
				break;
			case 47:
				strFieldName = "SpecFlag";
				break;
			case 48:
				strFieldName = "GrpSpec";
				break;
			case 49:
				strFieldName = "PayMode";
				break;
			case 50:
				strFieldName = "SignCom";
				break;
			case 51:
				strFieldName = "SignDate";
				break;
			case 52:
				strFieldName = "SignTime";
				break;
			case 53:
				strFieldName = "CValiDate";
				break;
			case 54:
				strFieldName = "PayIntv";
				break;
			case 55:
				strFieldName = "ManageFeeRate";
				break;
			case 56:
				strFieldName = "ExpPeoples";
				break;
			case 57:
				strFieldName = "ExpPremium";
				break;
			case 58:
				strFieldName = "ExpAmnt";
				break;
			case 59:
				strFieldName = "Peoples";
				break;
			case 60:
				strFieldName = "Mult";
				break;
			case 61:
				strFieldName = "Prem";
				break;
			case 62:
				strFieldName = "Amnt";
				break;
			case 63:
				strFieldName = "SumPrem";
				break;
			case 64:
				strFieldName = "SumPay";
				break;
			case 65:
				strFieldName = "Dif";
				break;
			case 66:
				strFieldName = "Remark";
				break;
			case 67:
				strFieldName = "StandbyFlag1";
				break;
			case 68:
				strFieldName = "StandbyFlag2";
				break;
			case 69:
				strFieldName = "StandbyFlag3";
				break;
			case 70:
				strFieldName = "InputOperator";
				break;
			case 71:
				strFieldName = "InputDate";
				break;
			case 72:
				strFieldName = "InputTime";
				break;
			case 73:
				strFieldName = "ApproveFlag";
				break;
			case 74:
				strFieldName = "ApproveCode";
				break;
			case 75:
				strFieldName = "ApproveDate";
				break;
			case 76:
				strFieldName = "ApproveTime";
				break;
			case 77:
				strFieldName = "UWOperator";
				break;
			case 78:
				strFieldName = "UWFlag";
				break;
			case 79:
				strFieldName = "UWDate";
				break;
			case 80:
				strFieldName = "UWTime";
				break;
			case 81:
				strFieldName = "AppFlag";
				break;
			case 82:
				strFieldName = "PolApplyDate";
				break;
			case 83:
				strFieldName = "CustomGetPolDate";
				break;
			case 84:
				strFieldName = "GetPolDate";
				break;
			case 85:
				strFieldName = "GetPolTime";
				break;
			case 86:
				strFieldName = "State";
				break;
			case 87:
				strFieldName = "Operator";
				break;
			case 88:
				strFieldName = "MakeDate";
				break;
			case 89:
				strFieldName = "MakeTime";
				break;
			case 90:
				strFieldName = "ModifyDate";
				break;
			case 91:
				strFieldName = "ModifyTime";
				break;
			case 92:
				strFieldName = "EnterKind";
				break;
			case 93:
				strFieldName = "AmntGrade";
				break;
			case 94:
				strFieldName = "Peoples3";
				break;
			case 95:
				strFieldName = "OnWorkPeoples";
				break;
			case 96:
				strFieldName = "OffWorkPeoples";
				break;
			case 97:
				strFieldName = "OtherPeoples";
				break;
			case 98:
				strFieldName = "RelaPeoples";
				break;
			case 99:
				strFieldName = "RelaMatePeoples";
				break;
			case 100:
				strFieldName = "RelaYoungPeoples";
				break;
			case 101:
				strFieldName = "RelaOtherPeoples";
				break;
			case 102:
				strFieldName = "FirstTrialOperator";
				break;
			case 103:
				strFieldName = "FirstTrialDate";
				break;
			case 104:
				strFieldName = "FirstTrialTime";
				break;
			case 105:
				strFieldName = "ReceiveOperator";
				break;
			case 106:
				strFieldName = "ReceiveDate";
				break;
			case 107:
				strFieldName = "ReceiveTime";
				break;
			case 108:
				strFieldName = "TempFeeNo";
				break;
			case 109:
				strFieldName = "HandlerName";
				break;
			case 110:
				strFieldName = "HandlerDate";
				break;
			case 111:
				strFieldName = "HandlerPrint";
				break;
			case 112:
				strFieldName = "AgentDate";
				break;
			case 113:
				strFieldName = "BusinessBigType";
				break;
			case 114:
				strFieldName = "MarketType";
				break;
			case 115:
				strFieldName = "ReportNo";
				break;
			case 116:
				strFieldName = "ConferNo";
				break;
			case 117:
				strFieldName = "SignReportNo";
				break;
			case 118:
				strFieldName = "AgentConferNo";
				break;
			case 119:
				strFieldName = "ContType";
				break;
			case 120:
				strFieldName = "EdorCalType";
				break;
			case 121:
				strFieldName = "ClientCare";
				break;
			case 122:
				strFieldName = "FundReason";
				break;
			case 123:
				strFieldName = "BackDateRemark";
				break;
			case 124:
				strFieldName = "ClientNeedJudge";
				break;
			case 125:
				strFieldName = "DonateContflag";
				break;
			case 126:
				strFieldName = "FundJudge";
				break;
			case 127:
				strFieldName = "ExamAndAppNo";
				break;
			case 128:
				strFieldName = "EdorTransPercent";
				break;
			case 129:
				strFieldName = "CardFlag";
				break;
			case 130:
				strFieldName = "AgentCodeOper";
				break;
			case 131:
				strFieldName = "AgentCodeAssi";
				break;
			case 132:
				strFieldName = "DelayReasonCode";
				break;
			case 133:
				strFieldName = "DelayReasonDesc";
				break;
			case 134:
				strFieldName = "ContPlanFlag";
				break;
			case 135:
				strFieldName = "FYCRATE";
				break;
			case 136:
				strFieldName = "ContFlag";
				break;
			case 137:
				strFieldName = "SaleDepart";
				break;
			case 138:
				strFieldName = "ChnlType";
				break;
			case 139:
				strFieldName = "AgentBranchesCode";
				break;
			case 140:
				strFieldName = "ProjectType";
				break;
			case 141:
				strFieldName = "RenewFlag";
				break;
			case 142:
				strFieldName = "RenewCount";
				break;
			case 143:
				strFieldName = "RenewContNo";
				break;
			case 144:
				strFieldName = "InitNumPeople";
				break;
			case 145:
				strFieldName = "InitMult";
				break;
			case 146:
				strFieldName = "InitAmnt";
				break;
			case 147:
				strFieldName = "InitRiskAmnt";
				break;
			case 148:
				strFieldName = "InitPrem";
				break;
			case 149:
				strFieldName = "InitStandPrem";
				break;
			case 150:
				strFieldName = "RiskAmnt";
				break;
			case 151:
				strFieldName = "StandPrem";
				break;
			case 152:
				strFieldName = "SumNumPeople";
				break;
			case 153:
				strFieldName = "InsuYear";
				break;
			case 154:
				strFieldName = "InsuYearFlag";
				break;
			case 155:
				strFieldName = "Years";
				break;
			case 156:
				strFieldName = "PayEndYear";
				break;
			case 157:
				strFieldName = "PayEndYearFlag";
				break;
			case 158:
				strFieldName = "PayYears";
				break;
			case 159:
				strFieldName = "ValDateType";
				break;
			case 160:
				strFieldName = "EndDate";
				break;
			case 161:
				strFieldName = "FirstPayDate";
				break;
			case 162:
				strFieldName = "PayEndDate";
				break;
			case 163:
				strFieldName = "PayToDate";
				break;
			case 164:
				strFieldName = "AutoPayFlag";
				break;
			case 165:
				strFieldName = "SignOperator";
				break;
			case 166:
				strFieldName = "Coinsurance";
				break;
			case 167:
				strFieldName = "AmntShareRate";
				break;
			case 168:
				strFieldName = "PremShareRate";
				break;
			case 169:
				strFieldName = "ScanCom";
				break;
			case 170:
				strFieldName = "ComCode";
				break;
			case 171:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("EdorType") ) {
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
		if( strFieldName.equals("EdorTransPercent") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CardFlag") ) {
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
		if( strFieldName.equals("ContPlanFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FYCRATE") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ContFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleDepart") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChnlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentBranchesCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProjectType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RenewFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RenewCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RenewContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitNumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InitMult") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitRiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InitStandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ValDateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Coinsurance") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmntShareRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremShareRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ScanCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 42:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 54:
				nFieldType = Schema.TYPE_INT;
				break;
			case 55:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 56:
				nFieldType = Schema.TYPE_INT;
				break;
			case 57:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 58:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 59:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 83:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 84:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 89:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 90:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 104:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 105:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 106:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 107:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 108:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 109:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 110:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 111:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 112:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 129:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 130:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 131:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 132:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 133:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 134:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 135:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 136:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 137:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 138:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 139:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 140:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 141:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 142:
				nFieldType = Schema.TYPE_INT;
				break;
			case 143:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 144:
				nFieldType = Schema.TYPE_INT;
				break;
			case 145:
				nFieldType = Schema.TYPE_INT;
				break;
			case 146:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 147:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 148:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 149:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 150:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 151:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 152:
				nFieldType = Schema.TYPE_INT;
				break;
			case 153:
				nFieldType = Schema.TYPE_INT;
				break;
			case 154:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 155:
				nFieldType = Schema.TYPE_INT;
				break;
			case 156:
				nFieldType = Schema.TYPE_INT;
				break;
			case 157:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 158:
				nFieldType = Schema.TYPE_INT;
				break;
			case 159:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 160:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 161:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 162:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 163:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 164:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 165:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 166:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 167:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 168:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 169:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 170:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 171:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
