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
import com.sinosoft.lis.db.LPContDB;

/*
 * <p>ClassName: LPContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPContSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPContSchema.class);
	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 总单类型 */
	private String ContType;
	/** 家庭单类型 */
	private String FamilyType;
	/** 家庭保障号 */
	private String FamilyID;
	/** 保单类型标记 */
	private String PolType;
	/** 卡单标志 */
	private String CardFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 处理机构 */
	private String ExecuteCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 联合代理人代码 */
	private String AgentCode1;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 销售渠道 */
	private String SaleChnl;
	/** 经办人 */
	private String Handler;
	/** 保单口令 */
	private String Password;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 投保人性别 */
	private String AppntSex;
	/** 投保人出生日期 */
	private Date AppntBirthday;
	/** 投保人证件类型 */
	private String AppntIDType;
	/** 投保人证件号码 */
	private String AppntIDNo;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人出生日期 */
	private Date InsuredBirthday;
	/** 证件类型 */
	private String InsuredIDType;
	/** 证件号码 */
	private String InsuredIDNo;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费方式 */
	private String PayMode;
	/** 交费位置 */
	private String PayLocation;
	/** 合同争议处理方式 */
	private String DisputedFlag;
	/** 溢交处理方式 */
	private String OutPayFlag;
	/** 保单送达方式 */
	private String GetPolMode;
	/** 签单机构 */
	private String SignCom;
	/** 签单日期 */
	private Date SignDate;
	/** 签单时间 */
	private String SignTime;
	/** 银行委托书号码 */
	private String ConsignNo;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 保单打印次数 */
	private int PrintCount;
	/** 遗失补发次数 */
	private int LostTimes;
	/** 语种标记 */
	private String Lang;
	/** 币别 */
	private String Currency;
	/** 备注 */
	private String Remark;
	/** 人数 */
	private int Peoples;
	/** 份数 */
	private double Mult;
	/** 保费 */
	private double Prem;
	/** 保额 */
	private double Amnt;
	/** 累??保费 */
	private double SumPrem;
	/** 余额 */
	private double Dif;
	/** 交至日期 */
	private Date PaytoDate;
	/** 首期交费日期 */
	private Date FirstPayDate;
	/** 保单生效日期 */
	private Date CValiDate;
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
	/** 核保状态 */
	private String UWFlag;
	/** 核保人 */
	private String UWOperator;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 投保单申请日期 */
	private Date PolApplyDate;
	/** 保单送达日期 */
	private Date GetPolDate;
	/** 保单送达时间 */
	private String GetPolTime;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
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
	/** 销售方式 */
	private String SellType;
	/** 强制人工核保标志 */
	private String ForceUWFlag;
	/** 强制人工核保原因 */
	private String ForceUWReason;
	/** 首期银行编码 */
	private String NewBankCode;
	/** 首期银行帐号 */
	private String NewBankAccNo;
	/** 首期银行帐户名 */
	private String NewAccName;
	/** 首期交费方式 */
	private String NewPayMode;
	/** 银代银行代码 */
	private String AgentBankCode;
	/** 银代柜员 */
	private String BankAgent;
	/** 自动垫交标志 */
	private String AutoPayFlag;
	/** 续保标志 */
	private int RnewFlag;
	/** 家庭保单号 */
	private String FamilyContNo;
	/** 商业因素标准体承保标志 */
	private String BussFlag;
	/** 初审员签名 */
	private String SignName;
	/** 合同成立日期 */
	private Date OrganizeDate;
	/** 合同成立时间 */
	private String OrganizeTime;
	/** 首期自动发盘标志 */
	private String NewAutoSendBankFlag;
	/** 综拓专员编码 */
	private String AgentCodeOper;
	/** 综拓助理编码 */
	private String AgentCodeAssi;
	/** 延迟送达原因代码 */
	private String DelayReasonCode;
	/** 延迟送达原因 */
	private String DelayReasonDesc;
	/** 续期缴费提示 */
	private String XQremindflag;
	/** 组织机构代码 */
	private String OrganComCode;
	/** 保单标识 */
	private String ContFlag;
	/** 销售部门 */
	private String SaleDepart;
	/** 渠道类型 */
	private String ChnlType;
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
	/** 累计交费 */
	private double SumPay;
	/** 终止日期 */
	private Date EndDate;
	/** 终交日期 */
	private Date PayEndDate;
	/** 是否默认帐户 */
	private String AccKind;
	/** 签单人 */
	private String SignOperator;
	/** 借款开始日期 */
	private Date LoanStartDate;
	/** 借款截止日期 */
	private Date LoanEndDate;
	/** 网点编码 */
	private String AgentBranchesCode;
	/** 是否打印发票 */
	private String NeedBillFlag;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 137;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "ContNo";

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
		LPContSchema cloned = (LPContSchema)super.clone();
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		if(aProposalContNo!=null && aProposalContNo.length()>20)
			throw new IllegalArgumentException("总单投保单号码ProposalContNo值"+aProposalContNo+"的长度"+aProposalContNo.length()+"大于最大值20");
		ProposalContNo = aProposalContNo;
	}
	/**
	* 同投保单号
	*/
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
	* 2-集体总单,1-个人总投保单
	*/
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
	* 0-个人，1-家庭单
	*/
	public String getFamilyType()
	{
		return FamilyType;
	}
	public void setFamilyType(String aFamilyType)
	{
		if(aFamilyType!=null && aFamilyType.length()>1)
			throw new IllegalArgumentException("家庭单类型FamilyType值"+aFamilyType+"的长度"+aFamilyType.length()+"大于最大值1");
		FamilyType = aFamilyType;
	}
	public String getFamilyID()
	{
		return FamilyID;
	}
	public void setFamilyID(String aFamilyID)
	{
		if(aFamilyID!=null && aFamilyID.length()>20)
			throw new IllegalArgumentException("家庭保障号FamilyID值"+aFamilyID+"的长度"+aFamilyID.length()+"大于最大值20");
		FamilyID = aFamilyID;
	}
	/**
	* 0 --个人单：<p>
	* 1 --无名单；如果是个单表示生日单（数据转换）<p>
	* 2 --（团单）公共帐户
	*/
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		if(aPolType!=null && aPolType.length()>1)
			throw new IllegalArgumentException("保单类型标记PolType值"+aPolType+"的长度"+aPolType.length()+"大于最大值1");
		PolType = aPolType;
	}
	/**
	* 0 --正常<p>
	* 1 --定额单<p>
	* 3 --卡单
	*/
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
	* 关联统括保单处理
	*/
	public String getExecuteCom()
	{
		return ExecuteCom;
	}
	public void setExecuteCom(String aExecuteCom)
	{
		if(aExecuteCom!=null && aExecuteCom.length()>10)
			throw new IllegalArgumentException("处理机构ExecuteCom值"+aExecuteCom+"的长度"+aExecuteCom.length()+"大于最大值10");
		ExecuteCom = aExecuteCom;
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
	/**
	* 代理机构计算佣金的方式
	*/
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
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		if(aHandler!=null && aHandler.length()>10)
			throw new IllegalArgumentException("经办人Handler值"+aHandler+"的长度"+aHandler.length()+"大于最大值10");
		Handler = aHandler;
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
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>24)
			throw new IllegalArgumentException("投保人客户号码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值24");
		AppntNo = aAppntNo;
	}
	/**
	* 冗余，标准在个人客户表
	*/
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>120)
			throw new IllegalArgumentException("投保人名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值120");
		AppntName = aAppntName;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getAppntSex()
	{
		return AppntSex;
	}
	public void setAppntSex(String aAppntSex)
	{
		if(aAppntSex!=null && aAppntSex.length()>1)
			throw new IllegalArgumentException("投保人性别AppntSex值"+aAppntSex+"的长度"+aAppntSex.length()+"大于最大值1");
		AppntSex = aAppntSex;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getAppntBirthday()
	{
		if( AppntBirthday != null )
			return fDate.getString(AppntBirthday);
		else
			return null;
	}
	public void setAppntBirthday(Date aAppntBirthday)
	{
		AppntBirthday = aAppntBirthday;
	}
	public void setAppntBirthday(String aAppntBirthday)
	{
		if (aAppntBirthday != null && !aAppntBirthday.equals("") )
		{
			AppntBirthday = fDate.getDate( aAppntBirthday );
		}
		else
			AppntBirthday = null;
	}

	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getAppntIDType()
	{
		return AppntIDType;
	}
	public void setAppntIDType(String aAppntIDType)
	{
		if(aAppntIDType!=null && aAppntIDType.length()>1)
			throw new IllegalArgumentException("投保人证件类型AppntIDType值"+aAppntIDType+"的长度"+aAppntIDType.length()+"大于最大值1");
		AppntIDType = aAppntIDType;
	}
	public String getAppntIDNo()
	{
		return AppntIDNo;
	}
	public void setAppntIDNo(String aAppntIDNo)
	{
		if(aAppntIDNo!=null && aAppntIDNo.length()>20)
			throw new IllegalArgumentException("投保人证件号码AppntIDNo值"+aAppntIDNo+"的长度"+aAppntIDNo.length()+"大于最大值20");
		AppntIDNo = aAppntIDNo;
	}
	/**
	* 家庭单时需要反写
	*/
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		if(aInsuredName!=null && aInsuredName.length()>120)
			throw new IllegalArgumentException("被保人名称InsuredName值"+aInsuredName+"的长度"+aInsuredName.length()+"大于最大值120");
		InsuredName = aInsuredName;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getInsuredSex()
	{
		return InsuredSex;
	}
	public void setInsuredSex(String aInsuredSex)
	{
		if(aInsuredSex!=null && aInsuredSex.length()>1)
			throw new IllegalArgumentException("被保人性别InsuredSex值"+aInsuredSex+"的长度"+aInsuredSex.length()+"大于最大值1");
		InsuredSex = aInsuredSex;
	}
	/**
	* 冗余，作为保单层面的标准
	*/
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
	}

	/**
	* 0 -- 身份证<p>
	* 1 -- 护照<p>
	* 2 -- 军官证<p>
	* 3 -- 驾照<p>
	* 4 -- 出生证明<p>
	* 5 -- 户口簿<p>
	* 8 -- 其他<p>
	* 9 -- 数据转换证件
	*/
	public String getInsuredIDType()
	{
		return InsuredIDType;
	}
	public void setInsuredIDType(String aInsuredIDType)
	{
		if(aInsuredIDType!=null && aInsuredIDType.length()>1)
			throw new IllegalArgumentException("证件类型InsuredIDType值"+aInsuredIDType+"的长度"+aInsuredIDType.length()+"大于最大值1");
		InsuredIDType = aInsuredIDType;
	}
	public String getInsuredIDNo()
	{
		return InsuredIDNo;
	}
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		if(aInsuredIDNo!=null && aInsuredIDNo.length()>20)
			throw new IllegalArgumentException("证件号码InsuredIDNo值"+aInsuredIDNo+"的长度"+aInsuredIDNo.length()+"大于最大值20");
		InsuredIDNo = aInsuredIDNo;
	}
	/**
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
	* 7	其他<p>
	* 8 --银行转帐(银代险事后转账)<p>
	* 9 --现金缴纳(银代险)<p>
	* 如果该字段的值是0，则在进行溢交保费退费的时候自动通过银行进行代付。<p>
	* <p>
	* 0--老系统数据补录<p>
	* 1--现金<p>
	* 2--现金送款簿<p>
	* 3--支票<p>
	* 4--银行转帐（非制返盘）<p>
	* 5--内部转帐<p>
	* 6--POS收款<p>
	* 7--银行代扣（制返盘）<p>
	* 8--邮政业务<p>
	* 9--银行收款
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
	/**
	* 该字段表示续期交费形式<p>
	* 0 --银行转帐<p>
	* 1 --自行缴纳<p>
	* 2 --上门缴费
	*/
	public String getPayLocation()
	{
		return PayLocation;
	}
	public void setPayLocation(String aPayLocation)
	{
		if(aPayLocation!=null && aPayLocation.length()>1)
			throw new IllegalArgumentException("交费位置PayLocation值"+aPayLocation+"的长度"+aPayLocation.length()+"大于最大值1");
		PayLocation = aPayLocation;
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
	* 暂时备用，数据转换数据都为0，新系统数据为1<p>
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
	/**
	* 通过银行投保时客户需要填写的委托书
	*/
	public String getConsignNo()
	{
		return ConsignNo;
	}
	public void setConsignNo(String aConsignNo)
	{
		if(aConsignNo!=null && aConsignNo.length()>20)
			throw new IllegalArgumentException("银行委托书号码ConsignNo值"+aConsignNo+"的长度"+aConsignNo.length()+"大于最大值20");
		ConsignNo = aConsignNo;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1600)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1600");
		Remark = aRemark;
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

	/**
	* 保单账户余额，用于交纳续期保费
	*/
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

	/**
	* 备用
	*/
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
	/**
	* 0-未复核<p>
	* 1-复核不通过<p>
	* 9-复核通过
	*/
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
	* 0 - 投保<p>
	* 1 - 承保<p>
	* 2 - 团体保单增人后未生效状态<p>
	* 4 - 终止<p>
	* 9 - 附加险自动续保期间
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
	* 1001&&&& -- 通知书逾期<p>
	* 1002&&&& -- 通知书逾期作废<p>
	* 1003&&&& -- 签单逾期作废
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
	public String getSellType()
	{
		return SellType;
	}
	public void setSellType(String aSellType)
	{
		if(aSellType!=null && aSellType.length()>2)
			throw new IllegalArgumentException("销售方式SellType值"+aSellType+"的长度"+aSellType.length()+"大于最大值2");
		SellType = aSellType;
	}
	/**
	* 1-为强制
	*/
	public String getForceUWFlag()
	{
		return ForceUWFlag;
	}
	public void setForceUWFlag(String aForceUWFlag)
	{
		if(aForceUWFlag!=null && aForceUWFlag.length()>1)
			throw new IllegalArgumentException("强制人工核保标志ForceUWFlag值"+aForceUWFlag+"的长度"+aForceUWFlag.length()+"大于最大值1");
		ForceUWFlag = aForceUWFlag;
	}
	public String getForceUWReason()
	{
		return ForceUWReason;
	}
	public void setForceUWReason(String aForceUWReason)
	{
		if(aForceUWReason!=null && aForceUWReason.length()>500)
			throw new IllegalArgumentException("强制人工核保原因ForceUWReason值"+aForceUWReason+"的长度"+aForceUWReason.length()+"大于最大值500");
		ForceUWReason = aForceUWReason;
	}
	public String getNewBankCode()
	{
		return NewBankCode;
	}
	public void setNewBankCode(String aNewBankCode)
	{
		if(aNewBankCode!=null && aNewBankCode.length()>10)
			throw new IllegalArgumentException("首期银行编码NewBankCode值"+aNewBankCode+"的长度"+aNewBankCode.length()+"大于最大值10");
		NewBankCode = aNewBankCode;
	}
	public String getNewBankAccNo()
	{
		return NewBankAccNo;
	}
	public void setNewBankAccNo(String aNewBankAccNo)
	{
		if(aNewBankAccNo!=null && aNewBankAccNo.length()>40)
			throw new IllegalArgumentException("首期银行帐号NewBankAccNo值"+aNewBankAccNo+"的长度"+aNewBankAccNo.length()+"大于最大值40");
		NewBankAccNo = aNewBankAccNo;
	}
	public String getNewAccName()
	{
		return NewAccName;
	}
	public void setNewAccName(String aNewAccName)
	{
		if(aNewAccName!=null && aNewAccName.length()>60)
			throw new IllegalArgumentException("首期银行帐户名NewAccName值"+aNewAccName+"的长度"+aNewAccName.length()+"大于最大值60");
		NewAccName = aNewAccName;
	}
	public String getNewPayMode()
	{
		return NewPayMode;
	}
	public void setNewPayMode(String aNewPayMode)
	{
		if(aNewPayMode!=null && aNewPayMode.length()>1)
			throw new IllegalArgumentException("首期交费方式NewPayMode值"+aNewPayMode+"的长度"+aNewPayMode.length()+"大于最大值1");
		NewPayMode = aNewPayMode;
	}
	public String getAgentBankCode()
	{
		return AgentBankCode;
	}
	public void setAgentBankCode(String aAgentBankCode)
	{
		if(aAgentBankCode!=null && aAgentBankCode.length()>6)
			throw new IllegalArgumentException("银代银行代码AgentBankCode值"+aAgentBankCode+"的长度"+aAgentBankCode.length()+"大于最大值6");
		AgentBankCode = aAgentBankCode;
	}
	public String getBankAgent()
	{
		return BankAgent;
	}
	public void setBankAgent(String aBankAgent)
	{
		if(aBankAgent!=null && aBankAgent.length()>20)
			throw new IllegalArgumentException("银代柜员BankAgent值"+aBankAgent+"的长度"+aBankAgent.length()+"大于最大值20");
		BankAgent = aBankAgent;
	}
	/**
	* 0 --正常<p>
	* 1 --垫交
	*/
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		if(aAutoPayFlag!=null && aAutoPayFlag.length()>1)
			throw new IllegalArgumentException("自动垫交标志AutoPayFlag值"+aAutoPayFlag+"的长度"+aAutoPayFlag.length()+"大于最大值1");
		AutoPayFlag = aAutoPayFlag;
	}
	/**
	* -2 -- 非续保 <p>
	* -1 -- 自动续保<p>
	* 0 -- 人工续保
	*/
	public int getRnewFlag()
	{
		return RnewFlag;
	}
	public void setRnewFlag(int aRnewFlag)
	{
		RnewFlag = aRnewFlag;
	}
	public void setRnewFlag(String aRnewFlag)
	{
		if (aRnewFlag != null && !aRnewFlag.equals(""))
		{
			Integer tInteger = new Integer(aRnewFlag);
			int i = tInteger.intValue();
			RnewFlag = i;
		}
	}

	public String getFamilyContNo()
	{
		return FamilyContNo;
	}
	public void setFamilyContNo(String aFamilyContNo)
	{
		if(aFamilyContNo!=null && aFamilyContNo.length()>20)
			throw new IllegalArgumentException("家庭保单号FamilyContNo值"+aFamilyContNo+"的长度"+aFamilyContNo.length()+"大于最大值20");
		FamilyContNo = aFamilyContNo;
	}
	/**
	* Y--有
	*/
	public String getBussFlag()
	{
		return BussFlag;
	}
	public void setBussFlag(String aBussFlag)
	{
		if(aBussFlag!=null && aBussFlag.length()>2)
			throw new IllegalArgumentException("商业因素标准体承保标志BussFlag值"+aBussFlag+"的长度"+aBussFlag.length()+"大于最大值2");
		BussFlag = aBussFlag;
	}
	public String getSignName()
	{
		return SignName;
	}
	public void setSignName(String aSignName)
	{
		if(aSignName!=null && aSignName.length()>120)
			throw new IllegalArgumentException("初审员签名SignName值"+aSignName+"的长度"+aSignName.length()+"大于最大值120");
		SignName = aSignName;
	}
	public String getOrganizeDate()
	{
		if( OrganizeDate != null )
			return fDate.getString(OrganizeDate);
		else
			return null;
	}
	public void setOrganizeDate(Date aOrganizeDate)
	{
		OrganizeDate = aOrganizeDate;
	}
	public void setOrganizeDate(String aOrganizeDate)
	{
		if (aOrganizeDate != null && !aOrganizeDate.equals("") )
		{
			OrganizeDate = fDate.getDate( aOrganizeDate );
		}
		else
			OrganizeDate = null;
	}

	public String getOrganizeTime()
	{
		return OrganizeTime;
	}
	public void setOrganizeTime(String aOrganizeTime)
	{
		if(aOrganizeTime!=null && aOrganizeTime.length()>8)
			throw new IllegalArgumentException("合同成立时间OrganizeTime值"+aOrganizeTime+"的长度"+aOrganizeTime.length()+"大于最大值8");
		OrganizeTime = aOrganizeTime;
	}
	/**
	* 投保单默认为自动发盘，核保师在人工核保可指定保单的自动发盘<p>
	* 标志<p>
	* <p>
	* 0              表示不自动发盘<p>
	* 1或者空   表示自动发盘
	*/
	public String getNewAutoSendBankFlag()
	{
		return NewAutoSendBankFlag;
	}
	public void setNewAutoSendBankFlag(String aNewAutoSendBankFlag)
	{
		if(aNewAutoSendBankFlag!=null && aNewAutoSendBankFlag.length()>1)
			throw new IllegalArgumentException("首期自动发盘标志NewAutoSendBankFlag值"+aNewAutoSendBankFlag+"的长度"+aNewAutoSendBankFlag.length()+"大于最大值1");
		NewAutoSendBankFlag = aNewAutoSendBankFlag;
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
	* 续期交费是否需要提示：<p>
	* <p>
	* 0 ----  否<p>
	* 1 ----  是
	*/
	public String getXQremindflag()
	{
		return XQremindflag;
	}
	public void setXQremindflag(String aXQremindflag)
	{
		if(aXQremindflag!=null && aXQremindflag.length()>1)
			throw new IllegalArgumentException("续期缴费提示XQremindflag值"+aXQremindflag+"的长度"+aXQremindflag.length()+"大于最大值1");
		XQremindflag = aXQremindflag;
	}
	public String getOrganComCode()
	{
		return OrganComCode;
	}
	public void setOrganComCode(String aOrganComCode)
	{
		if(aOrganComCode!=null && aOrganComCode.length()>30)
			throw new IllegalArgumentException("组织机构代码OrganComCode值"+aOrganComCode+"的长度"+aOrganComCode.length()+"大于最大值30");
		OrganComCode = aOrganComCode;
	}
	/**
	* 0-个人单，1-无名单，2-公共账户，3-公共保额
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
	/**
	* 1-个人，2-团体，3-银保
	*/
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

	/**
	* 0-否，1-是
	*/
	public String getAccKind()
	{
		return AccKind;
	}
	public void setAccKind(String aAccKind)
	{
		if(aAccKind!=null && aAccKind.length()>1)
			throw new IllegalArgumentException("是否默认帐户AccKind值"+aAccKind+"的长度"+aAccKind.length()+"大于最大值1");
		AccKind = aAccKind;
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
	* 小额信贷使用
	*/
	public String getLoanStartDate()
	{
		if( LoanStartDate != null )
			return fDate.getString(LoanStartDate);
		else
			return null;
	}
	public void setLoanStartDate(Date aLoanStartDate)
	{
		LoanStartDate = aLoanStartDate;
	}
	public void setLoanStartDate(String aLoanStartDate)
	{
		if (aLoanStartDate != null && !aLoanStartDate.equals("") )
		{
			LoanStartDate = fDate.getDate( aLoanStartDate );
		}
		else
			LoanStartDate = null;
	}

	/**
	* 小额信贷使用
	*/
	public String getLoanEndDate()
	{
		if( LoanEndDate != null )
			return fDate.getString(LoanEndDate);
		else
			return null;
	}
	public void setLoanEndDate(Date aLoanEndDate)
	{
		LoanEndDate = aLoanEndDate;
	}
	public void setLoanEndDate(String aLoanEndDate)
	{
		if (aLoanEndDate != null && !aLoanEndDate.equals("") )
		{
			LoanEndDate = fDate.getDate( aLoanEndDate );
		}
		else
			LoanEndDate = null;
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
	/**
	* 小额信贷使用
	*/
	public String getNeedBillFlag()
	{
		return NeedBillFlag;
	}
	public void setNeedBillFlag(String aNeedBillFlag)
	{
		if(aNeedBillFlag!=null && aNeedBillFlag.length()>1)
			throw new IllegalArgumentException("是否打印发票NeedBillFlag值"+aNeedBillFlag+"的长度"+aNeedBillFlag.length()+"大于最大值1");
		NeedBillFlag = aNeedBillFlag;
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
	* 使用另外一个 LPContSchema 对象给 Schema 赋值
	* @param: aLPContSchema LPContSchema
	**/
	public void setSchema(LPContSchema aLPContSchema)
	{
		this.EdorNo = aLPContSchema.getEdorNo();
		this.EdorType = aLPContSchema.getEdorType();
		this.GrpContNo = aLPContSchema.getGrpContNo();
		this.ContNo = aLPContSchema.getContNo();
		this.ProposalContNo = aLPContSchema.getProposalContNo();
		this.PrtNo = aLPContSchema.getPrtNo();
		this.ContType = aLPContSchema.getContType();
		this.FamilyType = aLPContSchema.getFamilyType();
		this.FamilyID = aLPContSchema.getFamilyID();
		this.PolType = aLPContSchema.getPolType();
		this.CardFlag = aLPContSchema.getCardFlag();
		this.ManageCom = aLPContSchema.getManageCom();
		this.ExecuteCom = aLPContSchema.getExecuteCom();
		this.AgentCom = aLPContSchema.getAgentCom();
		this.AgentCode = aLPContSchema.getAgentCode();
		this.AgentGroup = aLPContSchema.getAgentGroup();
		this.AgentCode1 = aLPContSchema.getAgentCode1();
		this.AgentType = aLPContSchema.getAgentType();
		this.SaleChnl = aLPContSchema.getSaleChnl();
		this.Handler = aLPContSchema.getHandler();
		this.Password = aLPContSchema.getPassword();
		this.AppntNo = aLPContSchema.getAppntNo();
		this.AppntName = aLPContSchema.getAppntName();
		this.AppntSex = aLPContSchema.getAppntSex();
		this.AppntBirthday = fDate.getDate( aLPContSchema.getAppntBirthday());
		this.AppntIDType = aLPContSchema.getAppntIDType();
		this.AppntIDNo = aLPContSchema.getAppntIDNo();
		this.InsuredNo = aLPContSchema.getInsuredNo();
		this.InsuredName = aLPContSchema.getInsuredName();
		this.InsuredSex = aLPContSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLPContSchema.getInsuredBirthday());
		this.InsuredIDType = aLPContSchema.getInsuredIDType();
		this.InsuredIDNo = aLPContSchema.getInsuredIDNo();
		this.PayIntv = aLPContSchema.getPayIntv();
		this.PayMode = aLPContSchema.getPayMode();
		this.PayLocation = aLPContSchema.getPayLocation();
		this.DisputedFlag = aLPContSchema.getDisputedFlag();
		this.OutPayFlag = aLPContSchema.getOutPayFlag();
		this.GetPolMode = aLPContSchema.getGetPolMode();
		this.SignCom = aLPContSchema.getSignCom();
		this.SignDate = fDate.getDate( aLPContSchema.getSignDate());
		this.SignTime = aLPContSchema.getSignTime();
		this.ConsignNo = aLPContSchema.getConsignNo();
		this.BankCode = aLPContSchema.getBankCode();
		this.BankAccNo = aLPContSchema.getBankAccNo();
		this.AccName = aLPContSchema.getAccName();
		this.PrintCount = aLPContSchema.getPrintCount();
		this.LostTimes = aLPContSchema.getLostTimes();
		this.Lang = aLPContSchema.getLang();
		this.Currency = aLPContSchema.getCurrency();
		this.Remark = aLPContSchema.getRemark();
		this.Peoples = aLPContSchema.getPeoples();
		this.Mult = aLPContSchema.getMult();
		this.Prem = aLPContSchema.getPrem();
		this.Amnt = aLPContSchema.getAmnt();
		this.SumPrem = aLPContSchema.getSumPrem();
		this.Dif = aLPContSchema.getDif();
		this.PaytoDate = fDate.getDate( aLPContSchema.getPaytoDate());
		this.FirstPayDate = fDate.getDate( aLPContSchema.getFirstPayDate());
		this.CValiDate = fDate.getDate( aLPContSchema.getCValiDate());
		this.InputOperator = aLPContSchema.getInputOperator();
		this.InputDate = fDate.getDate( aLPContSchema.getInputDate());
		this.InputTime = aLPContSchema.getInputTime();
		this.ApproveFlag = aLPContSchema.getApproveFlag();
		this.ApproveCode = aLPContSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLPContSchema.getApproveDate());
		this.ApproveTime = aLPContSchema.getApproveTime();
		this.UWFlag = aLPContSchema.getUWFlag();
		this.UWOperator = aLPContSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLPContSchema.getUWDate());
		this.UWTime = aLPContSchema.getUWTime();
		this.AppFlag = aLPContSchema.getAppFlag();
		this.PolApplyDate = fDate.getDate( aLPContSchema.getPolApplyDate());
		this.GetPolDate = fDate.getDate( aLPContSchema.getGetPolDate());
		this.GetPolTime = aLPContSchema.getGetPolTime();
		this.CustomGetPolDate = fDate.getDate( aLPContSchema.getCustomGetPolDate());
		this.State = aLPContSchema.getState();
		this.Operator = aLPContSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPContSchema.getMakeDate());
		this.MakeTime = aLPContSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPContSchema.getModifyDate());
		this.ModifyTime = aLPContSchema.getModifyTime();
		this.FirstTrialOperator = aLPContSchema.getFirstTrialOperator();
		this.FirstTrialDate = fDate.getDate( aLPContSchema.getFirstTrialDate());
		this.FirstTrialTime = aLPContSchema.getFirstTrialTime();
		this.ReceiveOperator = aLPContSchema.getReceiveOperator();
		this.ReceiveDate = fDate.getDate( aLPContSchema.getReceiveDate());
		this.ReceiveTime = aLPContSchema.getReceiveTime();
		this.TempFeeNo = aLPContSchema.getTempFeeNo();
		this.SellType = aLPContSchema.getSellType();
		this.ForceUWFlag = aLPContSchema.getForceUWFlag();
		this.ForceUWReason = aLPContSchema.getForceUWReason();
		this.NewBankCode = aLPContSchema.getNewBankCode();
		this.NewBankAccNo = aLPContSchema.getNewBankAccNo();
		this.NewAccName = aLPContSchema.getNewAccName();
		this.NewPayMode = aLPContSchema.getNewPayMode();
		this.AgentBankCode = aLPContSchema.getAgentBankCode();
		this.BankAgent = aLPContSchema.getBankAgent();
		this.AutoPayFlag = aLPContSchema.getAutoPayFlag();
		this.RnewFlag = aLPContSchema.getRnewFlag();
		this.FamilyContNo = aLPContSchema.getFamilyContNo();
		this.BussFlag = aLPContSchema.getBussFlag();
		this.SignName = aLPContSchema.getSignName();
		this.OrganizeDate = fDate.getDate( aLPContSchema.getOrganizeDate());
		this.OrganizeTime = aLPContSchema.getOrganizeTime();
		this.NewAutoSendBankFlag = aLPContSchema.getNewAutoSendBankFlag();
		this.AgentCodeOper = aLPContSchema.getAgentCodeOper();
		this.AgentCodeAssi = aLPContSchema.getAgentCodeAssi();
		this.DelayReasonCode = aLPContSchema.getDelayReasonCode();
		this.DelayReasonDesc = aLPContSchema.getDelayReasonDesc();
		this.XQremindflag = aLPContSchema.getXQremindflag();
		this.OrganComCode = aLPContSchema.getOrganComCode();
		this.ContFlag = aLPContSchema.getContFlag();
		this.SaleDepart = aLPContSchema.getSaleDepart();
		this.ChnlType = aLPContSchema.getChnlType();
		this.RenewCount = aLPContSchema.getRenewCount();
		this.RenewContNo = aLPContSchema.getRenewContNo();
		this.InitNumPeople = aLPContSchema.getInitNumPeople();
		this.InitMult = aLPContSchema.getInitMult();
		this.InitAmnt = aLPContSchema.getInitAmnt();
		this.InitRiskAmnt = aLPContSchema.getInitRiskAmnt();
		this.InitPrem = aLPContSchema.getInitPrem();
		this.InitStandPrem = aLPContSchema.getInitStandPrem();
		this.RiskAmnt = aLPContSchema.getRiskAmnt();
		this.StandPrem = aLPContSchema.getStandPrem();
		this.SumNumPeople = aLPContSchema.getSumNumPeople();
		this.SumPay = aLPContSchema.getSumPay();
		this.EndDate = fDate.getDate( aLPContSchema.getEndDate());
		this.PayEndDate = fDate.getDate( aLPContSchema.getPayEndDate());
		this.AccKind = aLPContSchema.getAccKind();
		this.SignOperator = aLPContSchema.getSignOperator();
		this.LoanStartDate = fDate.getDate( aLPContSchema.getLoanStartDate());
		this.LoanEndDate = fDate.getDate( aLPContSchema.getLoanEndDate());
		this.AgentBranchesCode = aLPContSchema.getAgentBranchesCode();
		this.NeedBillFlag = aLPContSchema.getNeedBillFlag();
		this.ComCode = aLPContSchema.getComCode();
		this.ModifyOperator = aLPContSchema.getModifyOperator();
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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("FamilyType") == null )
				this.FamilyType = null;
			else
				this.FamilyType = rs.getString("FamilyType").trim();

			if( rs.getString("FamilyID") == null )
				this.FamilyID = null;
			else
				this.FamilyID = rs.getString("FamilyID").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			if( rs.getString("CardFlag") == null )
				this.CardFlag = null;
			else
				this.CardFlag = rs.getString("CardFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

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

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AppntSex") == null )
				this.AppntSex = null;
			else
				this.AppntSex = rs.getString("AppntSex").trim();

			this.AppntBirthday = rs.getDate("AppntBirthday");
			if( rs.getString("AppntIDType") == null )
				this.AppntIDType = null;
			else
				this.AppntIDType = rs.getString("AppntIDType").trim();

			if( rs.getString("AppntIDNo") == null )
				this.AppntIDNo = null;
			else
				this.AppntIDNo = rs.getString("AppntIDNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayLocation") == null )
				this.PayLocation = null;
			else
				this.PayLocation = rs.getString("PayLocation").trim();

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

			if( rs.getString("SignCom") == null )
				this.SignCom = null;
			else
				this.SignCom = rs.getString("SignCom").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			if( rs.getString("ConsignNo") == null )
				this.ConsignNo = null;
			else
				this.ConsignNo = rs.getString("ConsignNo").trim();

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

			this.PrintCount = rs.getInt("PrintCount");
			this.LostTimes = rs.getInt("LostTimes");
			if( rs.getString("Lang") == null )
				this.Lang = null;
			else
				this.Lang = rs.getString("Lang").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.Peoples = rs.getInt("Peoples");
			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			this.SumPrem = rs.getDouble("SumPrem");
			this.Dif = rs.getDouble("Dif");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.FirstPayDate = rs.getDate("FirstPayDate");
			this.CValiDate = rs.getDate("CValiDate");
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

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

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
			this.GetPolDate = rs.getDate("GetPolDate");
			if( rs.getString("GetPolTime") == null )
				this.GetPolTime = null;
			else
				this.GetPolTime = rs.getString("GetPolTime").trim();

			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
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

			if( rs.getString("SellType") == null )
				this.SellType = null;
			else
				this.SellType = rs.getString("SellType").trim();

			if( rs.getString("ForceUWFlag") == null )
				this.ForceUWFlag = null;
			else
				this.ForceUWFlag = rs.getString("ForceUWFlag").trim();

			if( rs.getString("ForceUWReason") == null )
				this.ForceUWReason = null;
			else
				this.ForceUWReason = rs.getString("ForceUWReason").trim();

			if( rs.getString("NewBankCode") == null )
				this.NewBankCode = null;
			else
				this.NewBankCode = rs.getString("NewBankCode").trim();

			if( rs.getString("NewBankAccNo") == null )
				this.NewBankAccNo = null;
			else
				this.NewBankAccNo = rs.getString("NewBankAccNo").trim();

			if( rs.getString("NewAccName") == null )
				this.NewAccName = null;
			else
				this.NewAccName = rs.getString("NewAccName").trim();

			if( rs.getString("NewPayMode") == null )
				this.NewPayMode = null;
			else
				this.NewPayMode = rs.getString("NewPayMode").trim();

			if( rs.getString("AgentBankCode") == null )
				this.AgentBankCode = null;
			else
				this.AgentBankCode = rs.getString("AgentBankCode").trim();

			if( rs.getString("BankAgent") == null )
				this.BankAgent = null;
			else
				this.BankAgent = rs.getString("BankAgent").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			this.RnewFlag = rs.getInt("RnewFlag");
			if( rs.getString("FamilyContNo") == null )
				this.FamilyContNo = null;
			else
				this.FamilyContNo = rs.getString("FamilyContNo").trim();

			if( rs.getString("BussFlag") == null )
				this.BussFlag = null;
			else
				this.BussFlag = rs.getString("BussFlag").trim();

			if( rs.getString("SignName") == null )
				this.SignName = null;
			else
				this.SignName = rs.getString("SignName").trim();

			this.OrganizeDate = rs.getDate("OrganizeDate");
			if( rs.getString("OrganizeTime") == null )
				this.OrganizeTime = null;
			else
				this.OrganizeTime = rs.getString("OrganizeTime").trim();

			if( rs.getString("NewAutoSendBankFlag") == null )
				this.NewAutoSendBankFlag = null;
			else
				this.NewAutoSendBankFlag = rs.getString("NewAutoSendBankFlag").trim();

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

			if( rs.getString("XQremindflag") == null )
				this.XQremindflag = null;
			else
				this.XQremindflag = rs.getString("XQremindflag").trim();

			if( rs.getString("OrganComCode") == null )
				this.OrganComCode = null;
			else
				this.OrganComCode = rs.getString("OrganComCode").trim();

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
			this.SumPay = rs.getDouble("SumPay");
			this.EndDate = rs.getDate("EndDate");
			this.PayEndDate = rs.getDate("PayEndDate");
			if( rs.getString("AccKind") == null )
				this.AccKind = null;
			else
				this.AccKind = rs.getString("AccKind").trim();

			if( rs.getString("SignOperator") == null )
				this.SignOperator = null;
			else
				this.SignOperator = rs.getString("SignOperator").trim();

			this.LoanStartDate = rs.getDate("LoanStartDate");
			this.LoanEndDate = rs.getDate("LoanEndDate");
			if( rs.getString("AgentBranchesCode") == null )
				this.AgentBranchesCode = null;
			else
				this.AgentBranchesCode = rs.getString("AgentBranchesCode").trim();

			if( rs.getString("NeedBillFlag") == null )
				this.NeedBillFlag = null;
			else
				this.NeedBillFlag = rs.getString("NeedBillFlag").trim();

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
			logger.debug("数据库中的LPCont表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPContSchema getSchema()
	{
		LPContSchema aLPContSchema = new LPContSchema();
		aLPContSchema.setSchema(this);
		return aLPContSchema;
	}

	public LPContDB getDB()
	{
		LPContDB aDBOper = new LPContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPCont描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExecuteCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppntBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisputedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConsignNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LostTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Lang)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Dif));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FirstTrialDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReceiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SellType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForceUWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForceUWReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewPayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RnewFlag));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OrganizeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganizeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewAutoSendBankFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCodeAssi)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(XQremindflag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrganComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleDepart)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChnlType)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append( ChgData.chgData(SumPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LoanStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LoanEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentBranchesCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedBillFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPCont>历史记账凭证主表信息</A>表字段
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
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FamilyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FamilyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CardFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AppntSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AppntBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			AppntIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AppntIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ConsignNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).intValue();
			LostTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).intValue();
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			Peoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).doubleValue();
			Dif = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).doubleValue();
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58,SysConst.PACKAGESPILTER));
			FirstPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59,SysConst.PACKAGESPILTER));
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60,SysConst.PACKAGESPILTER));
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			PolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74,SysConst.PACKAGESPILTER));
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			FirstTrialOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			FirstTrialDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84,SysConst.PACKAGESPILTER));
			FirstTrialTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			ReceiveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			ReceiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87,SysConst.PACKAGESPILTER));
			ReceiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			SellType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			ForceUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			ForceUWReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			NewBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			NewBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			NewAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			NewPayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
			AgentBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			BankAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			RnewFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,100,SysConst.PACKAGESPILTER))).intValue();
			FamilyContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			BussFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			SignName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			OrganizeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104,SysConst.PACKAGESPILTER));
			OrganizeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 105, SysConst.PACKAGESPILTER );
			NewAutoSendBankFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
			AgentCodeOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 107, SysConst.PACKAGESPILTER );
			AgentCodeAssi = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 108, SysConst.PACKAGESPILTER );
			DelayReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 109, SysConst.PACKAGESPILTER );
			DelayReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 110, SysConst.PACKAGESPILTER );
			XQremindflag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 111, SysConst.PACKAGESPILTER );
			OrganComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 112, SysConst.PACKAGESPILTER );
			ContFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 113, SysConst.PACKAGESPILTER );
			SaleDepart = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 114, SysConst.PACKAGESPILTER );
			ChnlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 115, SysConst.PACKAGESPILTER );
			RenewCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,116,SysConst.PACKAGESPILTER))).intValue();
			RenewContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 117, SysConst.PACKAGESPILTER );
			InitNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,118,SysConst.PACKAGESPILTER))).intValue();
			InitMult= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,119,SysConst.PACKAGESPILTER))).intValue();
			InitAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,120,SysConst.PACKAGESPILTER))).doubleValue();
			InitRiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,121,SysConst.PACKAGESPILTER))).doubleValue();
			InitPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,122,SysConst.PACKAGESPILTER))).doubleValue();
			InitStandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,123,SysConst.PACKAGESPILTER))).doubleValue();
			RiskAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,124,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,125,SysConst.PACKAGESPILTER))).doubleValue();
			SumNumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,126,SysConst.PACKAGESPILTER))).intValue();
			SumPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,127,SysConst.PACKAGESPILTER))).doubleValue();
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 128,SysConst.PACKAGESPILTER));
			PayEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 129,SysConst.PACKAGESPILTER));
			AccKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 130, SysConst.PACKAGESPILTER );
			SignOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 131, SysConst.PACKAGESPILTER );
			LoanStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 132,SysConst.PACKAGESPILTER));
			LoanEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 133,SysConst.PACKAGESPILTER));
			AgentBranchesCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 134, SysConst.PACKAGESPILTER );
			NeedBillFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 135, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 136, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 137, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("FamilyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyType));
		}
		if (FCode.equalsIgnoreCase("FamilyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyID));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equalsIgnoreCase("CardFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardFlag));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
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
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("AppntSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntSex));
		}
		if (FCode.equalsIgnoreCase("AppntBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
		}
		if (FCode.equalsIgnoreCase("AppntIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDType));
		}
		if (FCode.equalsIgnoreCase("AppntIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntIDNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
		}
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PayLocation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayLocation));
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
		if (FCode.equalsIgnoreCase("ConsignNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConsignNo));
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
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LostTimes));
		}
		if (FCode.equalsIgnoreCase("Lang"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Lang));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("Dif"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dif));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
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
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
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
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolTime));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
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
		if (FCode.equalsIgnoreCase("SellType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SellType));
		}
		if (FCode.equalsIgnoreCase("ForceUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForceUWFlag));
		}
		if (FCode.equalsIgnoreCase("ForceUWReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForceUWReason));
		}
		if (FCode.equalsIgnoreCase("NewBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankCode));
		}
		if (FCode.equalsIgnoreCase("NewBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewBankAccNo));
		}
		if (FCode.equalsIgnoreCase("NewAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewAccName));
		}
		if (FCode.equalsIgnoreCase("NewPayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewPayMode));
		}
		if (FCode.equalsIgnoreCase("AgentBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentBankCode));
		}
		if (FCode.equalsIgnoreCase("BankAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAgent));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewFlag));
		}
		if (FCode.equalsIgnoreCase("FamilyContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyContNo));
		}
		if (FCode.equalsIgnoreCase("BussFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussFlag));
		}
		if (FCode.equalsIgnoreCase("SignName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignName));
		}
		if (FCode.equalsIgnoreCase("OrganizeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOrganizeDate()));
		}
		if (FCode.equalsIgnoreCase("OrganizeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganizeTime));
		}
		if (FCode.equalsIgnoreCase("NewAutoSendBankFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewAutoSendBankFlag));
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
		if (FCode.equalsIgnoreCase("XQremindflag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(XQremindflag));
		}
		if (FCode.equalsIgnoreCase("OrganComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrganComCode));
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
		if (FCode.equalsIgnoreCase("SumPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPay));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
		}
		if (FCode.equalsIgnoreCase("AccKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccKind));
		}
		if (FCode.equalsIgnoreCase("SignOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignOperator));
		}
		if (FCode.equalsIgnoreCase("LoanStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLoanStartDate()));
		}
		if (FCode.equalsIgnoreCase("LoanEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLoanEndDate()));
		}
		if (FCode.equalsIgnoreCase("AgentBranchesCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentBranchesCode));
		}
		if (FCode.equalsIgnoreCase("NeedBillFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedBillFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FamilyType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FamilyID);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CardFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AppntSex);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppntBirthday()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AppntIDType);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AppntIDNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 33:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
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
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ConsignNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 46:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 47:
				strFieldValue = String.valueOf(LostTimes);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 51:
				strFieldValue = String.valueOf(Peoples);
				break;
			case 52:
				strFieldValue = String.valueOf(Mult);
				break;
			case 53:
				strFieldValue = String.valueOf(Prem);
				break;
			case 54:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 55:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 56:
				strFieldValue = String.valueOf(Dif);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstPayDate()));
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPolApplyDate()));
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialOperator);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFirstTrialDate()));
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialTime);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(ReceiveOperator);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(ReceiveTime);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(SellType);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(ForceUWFlag);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(ForceUWReason);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(NewBankCode);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(NewBankAccNo);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(NewAccName);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(NewPayMode);
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(AgentBankCode);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(BankAgent);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 99:
				strFieldValue = String.valueOf(RnewFlag);
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(FamilyContNo);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(BussFlag);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(SignName);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOrganizeDate()));
				break;
			case 104:
				strFieldValue = StrTool.GBKToUnicode(OrganizeTime);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(NewAutoSendBankFlag);
				break;
			case 106:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeOper);
				break;
			case 107:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeAssi);
				break;
			case 108:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonCode);
				break;
			case 109:
				strFieldValue = StrTool.GBKToUnicode(DelayReasonDesc);
				break;
			case 110:
				strFieldValue = StrTool.GBKToUnicode(XQremindflag);
				break;
			case 111:
				strFieldValue = StrTool.GBKToUnicode(OrganComCode);
				break;
			case 112:
				strFieldValue = StrTool.GBKToUnicode(ContFlag);
				break;
			case 113:
				strFieldValue = StrTool.GBKToUnicode(SaleDepart);
				break;
			case 114:
				strFieldValue = StrTool.GBKToUnicode(ChnlType);
				break;
			case 115:
				strFieldValue = String.valueOf(RenewCount);
				break;
			case 116:
				strFieldValue = StrTool.GBKToUnicode(RenewContNo);
				break;
			case 117:
				strFieldValue = String.valueOf(InitNumPeople);
				break;
			case 118:
				strFieldValue = String.valueOf(InitMult);
				break;
			case 119:
				strFieldValue = String.valueOf(InitAmnt);
				break;
			case 120:
				strFieldValue = String.valueOf(InitRiskAmnt);
				break;
			case 121:
				strFieldValue = String.valueOf(InitPrem);
				break;
			case 122:
				strFieldValue = String.valueOf(InitStandPrem);
				break;
			case 123:
				strFieldValue = String.valueOf(RiskAmnt);
				break;
			case 124:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 125:
				strFieldValue = String.valueOf(SumNumPeople);
				break;
			case 126:
				strFieldValue = String.valueOf(SumPay);
				break;
			case 127:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 128:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayEndDate()));
				break;
			case 129:
				strFieldValue = StrTool.GBKToUnicode(AccKind);
				break;
			case 130:
				strFieldValue = StrTool.GBKToUnicode(SignOperator);
				break;
			case 131:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLoanStartDate()));
				break;
			case 132:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLoanEndDate()));
				break;
			case 133:
				strFieldValue = StrTool.GBKToUnicode(AgentBranchesCode);
				break;
			case 134:
				strFieldValue = StrTool.GBKToUnicode(NeedBillFlag);
				break;
			case 135:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 136:
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("FamilyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyType = FValue.trim();
			}
			else
				FamilyType = null;
		}
		if (FCode.equalsIgnoreCase("FamilyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyID = FValue.trim();
			}
			else
				FamilyID = null;
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolType = FValue.trim();
			}
			else
				PolType = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExecuteCom = FValue.trim();
			}
			else
				ExecuteCom = null;
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
		if (FCode.equalsIgnoreCase("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
		}
		if (FCode.equalsIgnoreCase("AppntSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntSex = FValue.trim();
			}
			else
				AppntSex = null;
		}
		if (FCode.equalsIgnoreCase("AppntBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppntBirthday = fDate.getDate( FValue );
			}
			else
				AppntBirthday = null;
		}
		if (FCode.equalsIgnoreCase("AppntIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDType = FValue.trim();
			}
			else
				AppntIDType = null;
		}
		if (FCode.equalsIgnoreCase("AppntIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntIDNo = FValue.trim();
			}
			else
				AppntIDNo = null;
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
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredBirthday = fDate.getDate( FValue );
			}
			else
				InsuredBirthday = null;
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
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
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
		if (FCode.equalsIgnoreCase("PayLocation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayLocation = FValue.trim();
			}
			else
				PayLocation = null;
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
		if (FCode.equalsIgnoreCase("ConsignNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConsignNo = FValue.trim();
			}
			else
				ConsignNo = null;
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
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("Dif"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Dif = d;
			}
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
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FirstPayDate = fDate.getDate( FValue );
			}
			else
				FirstPayDate = null;
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
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
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
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomGetPolDate = null;
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
		if (FCode.equalsIgnoreCase("SellType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SellType = FValue.trim();
			}
			else
				SellType = null;
		}
		if (FCode.equalsIgnoreCase("ForceUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForceUWFlag = FValue.trim();
			}
			else
				ForceUWFlag = null;
		}
		if (FCode.equalsIgnoreCase("ForceUWReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForceUWReason = FValue.trim();
			}
			else
				ForceUWReason = null;
		}
		if (FCode.equalsIgnoreCase("NewBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankCode = FValue.trim();
			}
			else
				NewBankCode = null;
		}
		if (FCode.equalsIgnoreCase("NewBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewBankAccNo = FValue.trim();
			}
			else
				NewBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("NewAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewAccName = FValue.trim();
			}
			else
				NewAccName = null;
		}
		if (FCode.equalsIgnoreCase("NewPayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewPayMode = FValue.trim();
			}
			else
				NewPayMode = null;
		}
		if (FCode.equalsIgnoreCase("AgentBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentBankCode = FValue.trim();
			}
			else
				AgentBankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAgent = FValue.trim();
			}
			else
				BankAgent = null;
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
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RnewFlag = i;
			}
		}
		if (FCode.equalsIgnoreCase("FamilyContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyContNo = FValue.trim();
			}
			else
				FamilyContNo = null;
		}
		if (FCode.equalsIgnoreCase("BussFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussFlag = FValue.trim();
			}
			else
				BussFlag = null;
		}
		if (FCode.equalsIgnoreCase("SignName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignName = FValue.trim();
			}
			else
				SignName = null;
		}
		if (FCode.equalsIgnoreCase("OrganizeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OrganizeDate = fDate.getDate( FValue );
			}
			else
				OrganizeDate = null;
		}
		if (FCode.equalsIgnoreCase("OrganizeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrganizeTime = FValue.trim();
			}
			else
				OrganizeTime = null;
		}
		if (FCode.equalsIgnoreCase("NewAutoSendBankFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewAutoSendBankFlag = FValue.trim();
			}
			else
				NewAutoSendBankFlag = null;
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
		if (FCode.equalsIgnoreCase("XQremindflag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				XQremindflag = FValue.trim();
			}
			else
				XQremindflag = null;
		}
		if (FCode.equalsIgnoreCase("OrganComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrganComCode = FValue.trim();
			}
			else
				OrganComCode = null;
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
		if (FCode.equalsIgnoreCase("SumPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPay = d;
			}
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
		if (FCode.equalsIgnoreCase("PayEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayEndDate = fDate.getDate( FValue );
			}
			else
				PayEndDate = null;
		}
		if (FCode.equalsIgnoreCase("AccKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccKind = FValue.trim();
			}
			else
				AccKind = null;
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
		if (FCode.equalsIgnoreCase("LoanStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LoanStartDate = fDate.getDate( FValue );
			}
			else
				LoanStartDate = null;
		}
		if (FCode.equalsIgnoreCase("LoanEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LoanEndDate = fDate.getDate( FValue );
			}
			else
				LoanEndDate = null;
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
		if (FCode.equalsIgnoreCase("NeedBillFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedBillFlag = FValue.trim();
			}
			else
				NeedBillFlag = null;
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
		LPContSchema other = (LPContSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ContType.equals(other.getContType())
			&& FamilyType.equals(other.getFamilyType())
			&& FamilyID.equals(other.getFamilyID())
			&& PolType.equals(other.getPolType())
			&& CardFlag.equals(other.getCardFlag())
			&& ManageCom.equals(other.getManageCom())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& AgentType.equals(other.getAgentType())
			&& SaleChnl.equals(other.getSaleChnl())
			&& Handler.equals(other.getHandler())
			&& Password.equals(other.getPassword())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& AppntSex.equals(other.getAppntSex())
			&& fDate.getString(AppntBirthday).equals(other.getAppntBirthday())
			&& AppntIDType.equals(other.getAppntIDType())
			&& AppntIDNo.equals(other.getAppntIDNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& PayIntv == other.getPayIntv()
			&& PayMode.equals(other.getPayMode())
			&& PayLocation.equals(other.getPayLocation())
			&& DisputedFlag.equals(other.getDisputedFlag())
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& GetPolMode.equals(other.getGetPolMode())
			&& SignCom.equals(other.getSignCom())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& ConsignNo.equals(other.getConsignNo())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& PrintCount == other.getPrintCount()
			&& LostTimes == other.getLostTimes()
			&& Lang.equals(other.getLang())
			&& Currency.equals(other.getCurrency())
			&& Remark.equals(other.getRemark())
			&& Peoples == other.getPeoples()
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& SumPrem == other.getSumPrem()
			&& Dif == other.getDif()
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(FirstPayDate).equals(other.getFirstPayDate())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& InputOperator.equals(other.getInputOperator())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& AppFlag.equals(other.getAppFlag())
			&& fDate.getString(PolApplyDate).equals(other.getPolApplyDate())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& GetPolTime.equals(other.getGetPolTime())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& FirstTrialOperator.equals(other.getFirstTrialOperator())
			&& fDate.getString(FirstTrialDate).equals(other.getFirstTrialDate())
			&& FirstTrialTime.equals(other.getFirstTrialTime())
			&& ReceiveOperator.equals(other.getReceiveOperator())
			&& fDate.getString(ReceiveDate).equals(other.getReceiveDate())
			&& ReceiveTime.equals(other.getReceiveTime())
			&& TempFeeNo.equals(other.getTempFeeNo())
			&& SellType.equals(other.getSellType())
			&& ForceUWFlag.equals(other.getForceUWFlag())
			&& ForceUWReason.equals(other.getForceUWReason())
			&& NewBankCode.equals(other.getNewBankCode())
			&& NewBankAccNo.equals(other.getNewBankAccNo())
			&& NewAccName.equals(other.getNewAccName())
			&& NewPayMode.equals(other.getNewPayMode())
			&& AgentBankCode.equals(other.getAgentBankCode())
			&& BankAgent.equals(other.getBankAgent())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& RnewFlag == other.getRnewFlag()
			&& FamilyContNo.equals(other.getFamilyContNo())
			&& BussFlag.equals(other.getBussFlag())
			&& SignName.equals(other.getSignName())
			&& fDate.getString(OrganizeDate).equals(other.getOrganizeDate())
			&& OrganizeTime.equals(other.getOrganizeTime())
			&& NewAutoSendBankFlag.equals(other.getNewAutoSendBankFlag())
			&& AgentCodeOper.equals(other.getAgentCodeOper())
			&& AgentCodeAssi.equals(other.getAgentCodeAssi())
			&& DelayReasonCode.equals(other.getDelayReasonCode())
			&& DelayReasonDesc.equals(other.getDelayReasonDesc())
			&& XQremindflag.equals(other.getXQremindflag())
			&& OrganComCode.equals(other.getOrganComCode())
			&& ContFlag.equals(other.getContFlag())
			&& SaleDepart.equals(other.getSaleDepart())
			&& ChnlType.equals(other.getChnlType())
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
			&& SumPay == other.getSumPay()
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(PayEndDate).equals(other.getPayEndDate())
			&& AccKind.equals(other.getAccKind())
			&& SignOperator.equals(other.getSignOperator())
			&& fDate.getString(LoanStartDate).equals(other.getLoanStartDate())
			&& fDate.getString(LoanEndDate).equals(other.getLoanEndDate())
			&& AgentBranchesCode.equals(other.getAgentBranchesCode())
			&& NeedBillFlag.equals(other.getNeedBillFlag())
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
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 5;
		}
		if( strFieldName.equals("ContType") ) {
			return 6;
		}
		if( strFieldName.equals("FamilyType") ) {
			return 7;
		}
		if( strFieldName.equals("FamilyID") ) {
			return 8;
		}
		if( strFieldName.equals("PolType") ) {
			return 9;
		}
		if( strFieldName.equals("CardFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 12;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 13;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 14;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 15;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 16;
		}
		if( strFieldName.equals("AgentType") ) {
			return 17;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 18;
		}
		if( strFieldName.equals("Handler") ) {
			return 19;
		}
		if( strFieldName.equals("Password") ) {
			return 20;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 21;
		}
		if( strFieldName.equals("AppntName") ) {
			return 22;
		}
		if( strFieldName.equals("AppntSex") ) {
			return 23;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return 24;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return 25;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return 26;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 27;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 28;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 29;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 30;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 31;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 32;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 33;
		}
		if( strFieldName.equals("PayMode") ) {
			return 34;
		}
		if( strFieldName.equals("PayLocation") ) {
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
		if( strFieldName.equals("SignCom") ) {
			return 39;
		}
		if( strFieldName.equals("SignDate") ) {
			return 40;
		}
		if( strFieldName.equals("SignTime") ) {
			return 41;
		}
		if( strFieldName.equals("ConsignNo") ) {
			return 42;
		}
		if( strFieldName.equals("BankCode") ) {
			return 43;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 44;
		}
		if( strFieldName.equals("AccName") ) {
			return 45;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 46;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 47;
		}
		if( strFieldName.equals("Lang") ) {
			return 48;
		}
		if( strFieldName.equals("Currency") ) {
			return 49;
		}
		if( strFieldName.equals("Remark") ) {
			return 50;
		}
		if( strFieldName.equals("Peoples") ) {
			return 51;
		}
		if( strFieldName.equals("Mult") ) {
			return 52;
		}
		if( strFieldName.equals("Prem") ) {
			return 53;
		}
		if( strFieldName.equals("Amnt") ) {
			return 54;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 55;
		}
		if( strFieldName.equals("Dif") ) {
			return 56;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 57;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 58;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 59;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 60;
		}
		if( strFieldName.equals("InputDate") ) {
			return 61;
		}
		if( strFieldName.equals("InputTime") ) {
			return 62;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 63;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 64;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 65;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 66;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 67;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 68;
		}
		if( strFieldName.equals("UWDate") ) {
			return 69;
		}
		if( strFieldName.equals("UWTime") ) {
			return 70;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 71;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 72;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 73;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 74;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 75;
		}
		if( strFieldName.equals("State") ) {
			return 76;
		}
		if( strFieldName.equals("Operator") ) {
			return 77;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 78;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 79;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 80;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 81;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return 82;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return 83;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return 84;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return 85;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 86;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return 87;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 88;
		}
		if( strFieldName.equals("SellType") ) {
			return 89;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return 90;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return 91;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return 92;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return 93;
		}
		if( strFieldName.equals("NewAccName") ) {
			return 94;
		}
		if( strFieldName.equals("NewPayMode") ) {
			return 95;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return 96;
		}
		if( strFieldName.equals("BankAgent") ) {
			return 97;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 98;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 99;
		}
		if( strFieldName.equals("FamilyContNo") ) {
			return 100;
		}
		if( strFieldName.equals("BussFlag") ) {
			return 101;
		}
		if( strFieldName.equals("SignName") ) {
			return 102;
		}
		if( strFieldName.equals("OrganizeDate") ) {
			return 103;
		}
		if( strFieldName.equals("OrganizeTime") ) {
			return 104;
		}
		if( strFieldName.equals("NewAutoSendBankFlag") ) {
			return 105;
		}
		if( strFieldName.equals("AgentCodeOper") ) {
			return 106;
		}
		if( strFieldName.equals("AgentCodeAssi") ) {
			return 107;
		}
		if( strFieldName.equals("DelayReasonCode") ) {
			return 108;
		}
		if( strFieldName.equals("DelayReasonDesc") ) {
			return 109;
		}
		if( strFieldName.equals("XQremindflag") ) {
			return 110;
		}
		if( strFieldName.equals("OrganComCode") ) {
			return 111;
		}
		if( strFieldName.equals("ContFlag") ) {
			return 112;
		}
		if( strFieldName.equals("SaleDepart") ) {
			return 113;
		}
		if( strFieldName.equals("ChnlType") ) {
			return 114;
		}
		if( strFieldName.equals("RenewCount") ) {
			return 115;
		}
		if( strFieldName.equals("RenewContNo") ) {
			return 116;
		}
		if( strFieldName.equals("InitNumPeople") ) {
			return 117;
		}
		if( strFieldName.equals("InitMult") ) {
			return 118;
		}
		if( strFieldName.equals("InitAmnt") ) {
			return 119;
		}
		if( strFieldName.equals("InitRiskAmnt") ) {
			return 120;
		}
		if( strFieldName.equals("InitPrem") ) {
			return 121;
		}
		if( strFieldName.equals("InitStandPrem") ) {
			return 122;
		}
		if( strFieldName.equals("RiskAmnt") ) {
			return 123;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 124;
		}
		if( strFieldName.equals("SumNumPeople") ) {
			return 125;
		}
		if( strFieldName.equals("SumPay") ) {
			return 126;
		}
		if( strFieldName.equals("EndDate") ) {
			return 127;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return 128;
		}
		if( strFieldName.equals("AccKind") ) {
			return 129;
		}
		if( strFieldName.equals("SignOperator") ) {
			return 130;
		}
		if( strFieldName.equals("LoanStartDate") ) {
			return 131;
		}
		if( strFieldName.equals("LoanEndDate") ) {
			return 132;
		}
		if( strFieldName.equals("AgentBranchesCode") ) {
			return 133;
		}
		if( strFieldName.equals("NeedBillFlag") ) {
			return 134;
		}
		if( strFieldName.equals("ComCode") ) {
			return 135;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 136;
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
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "PrtNo";
				break;
			case 6:
				strFieldName = "ContType";
				break;
			case 7:
				strFieldName = "FamilyType";
				break;
			case 8:
				strFieldName = "FamilyID";
				break;
			case 9:
				strFieldName = "PolType";
				break;
			case 10:
				strFieldName = "CardFlag";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "ExecuteCom";
				break;
			case 13:
				strFieldName = "AgentCom";
				break;
			case 14:
				strFieldName = "AgentCode";
				break;
			case 15:
				strFieldName = "AgentGroup";
				break;
			case 16:
				strFieldName = "AgentCode1";
				break;
			case 17:
				strFieldName = "AgentType";
				break;
			case 18:
				strFieldName = "SaleChnl";
				break;
			case 19:
				strFieldName = "Handler";
				break;
			case 20:
				strFieldName = "Password";
				break;
			case 21:
				strFieldName = "AppntNo";
				break;
			case 22:
				strFieldName = "AppntName";
				break;
			case 23:
				strFieldName = "AppntSex";
				break;
			case 24:
				strFieldName = "AppntBirthday";
				break;
			case 25:
				strFieldName = "AppntIDType";
				break;
			case 26:
				strFieldName = "AppntIDNo";
				break;
			case 27:
				strFieldName = "InsuredNo";
				break;
			case 28:
				strFieldName = "InsuredName";
				break;
			case 29:
				strFieldName = "InsuredSex";
				break;
			case 30:
				strFieldName = "InsuredBirthday";
				break;
			case 31:
				strFieldName = "InsuredIDType";
				break;
			case 32:
				strFieldName = "InsuredIDNo";
				break;
			case 33:
				strFieldName = "PayIntv";
				break;
			case 34:
				strFieldName = "PayMode";
				break;
			case 35:
				strFieldName = "PayLocation";
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
				strFieldName = "SignCom";
				break;
			case 40:
				strFieldName = "SignDate";
				break;
			case 41:
				strFieldName = "SignTime";
				break;
			case 42:
				strFieldName = "ConsignNo";
				break;
			case 43:
				strFieldName = "BankCode";
				break;
			case 44:
				strFieldName = "BankAccNo";
				break;
			case 45:
				strFieldName = "AccName";
				break;
			case 46:
				strFieldName = "PrintCount";
				break;
			case 47:
				strFieldName = "LostTimes";
				break;
			case 48:
				strFieldName = "Lang";
				break;
			case 49:
				strFieldName = "Currency";
				break;
			case 50:
				strFieldName = "Remark";
				break;
			case 51:
				strFieldName = "Peoples";
				break;
			case 52:
				strFieldName = "Mult";
				break;
			case 53:
				strFieldName = "Prem";
				break;
			case 54:
				strFieldName = "Amnt";
				break;
			case 55:
				strFieldName = "SumPrem";
				break;
			case 56:
				strFieldName = "Dif";
				break;
			case 57:
				strFieldName = "PaytoDate";
				break;
			case 58:
				strFieldName = "FirstPayDate";
				break;
			case 59:
				strFieldName = "CValiDate";
				break;
			case 60:
				strFieldName = "InputOperator";
				break;
			case 61:
				strFieldName = "InputDate";
				break;
			case 62:
				strFieldName = "InputTime";
				break;
			case 63:
				strFieldName = "ApproveFlag";
				break;
			case 64:
				strFieldName = "ApproveCode";
				break;
			case 65:
				strFieldName = "ApproveDate";
				break;
			case 66:
				strFieldName = "ApproveTime";
				break;
			case 67:
				strFieldName = "UWFlag";
				break;
			case 68:
				strFieldName = "UWOperator";
				break;
			case 69:
				strFieldName = "UWDate";
				break;
			case 70:
				strFieldName = "UWTime";
				break;
			case 71:
				strFieldName = "AppFlag";
				break;
			case 72:
				strFieldName = "PolApplyDate";
				break;
			case 73:
				strFieldName = "GetPolDate";
				break;
			case 74:
				strFieldName = "GetPolTime";
				break;
			case 75:
				strFieldName = "CustomGetPolDate";
				break;
			case 76:
				strFieldName = "State";
				break;
			case 77:
				strFieldName = "Operator";
				break;
			case 78:
				strFieldName = "MakeDate";
				break;
			case 79:
				strFieldName = "MakeTime";
				break;
			case 80:
				strFieldName = "ModifyDate";
				break;
			case 81:
				strFieldName = "ModifyTime";
				break;
			case 82:
				strFieldName = "FirstTrialOperator";
				break;
			case 83:
				strFieldName = "FirstTrialDate";
				break;
			case 84:
				strFieldName = "FirstTrialTime";
				break;
			case 85:
				strFieldName = "ReceiveOperator";
				break;
			case 86:
				strFieldName = "ReceiveDate";
				break;
			case 87:
				strFieldName = "ReceiveTime";
				break;
			case 88:
				strFieldName = "TempFeeNo";
				break;
			case 89:
				strFieldName = "SellType";
				break;
			case 90:
				strFieldName = "ForceUWFlag";
				break;
			case 91:
				strFieldName = "ForceUWReason";
				break;
			case 92:
				strFieldName = "NewBankCode";
				break;
			case 93:
				strFieldName = "NewBankAccNo";
				break;
			case 94:
				strFieldName = "NewAccName";
				break;
			case 95:
				strFieldName = "NewPayMode";
				break;
			case 96:
				strFieldName = "AgentBankCode";
				break;
			case 97:
				strFieldName = "BankAgent";
				break;
			case 98:
				strFieldName = "AutoPayFlag";
				break;
			case 99:
				strFieldName = "RnewFlag";
				break;
			case 100:
				strFieldName = "FamilyContNo";
				break;
			case 101:
				strFieldName = "BussFlag";
				break;
			case 102:
				strFieldName = "SignName";
				break;
			case 103:
				strFieldName = "OrganizeDate";
				break;
			case 104:
				strFieldName = "OrganizeTime";
				break;
			case 105:
				strFieldName = "NewAutoSendBankFlag";
				break;
			case 106:
				strFieldName = "AgentCodeOper";
				break;
			case 107:
				strFieldName = "AgentCodeAssi";
				break;
			case 108:
				strFieldName = "DelayReasonCode";
				break;
			case 109:
				strFieldName = "DelayReasonDesc";
				break;
			case 110:
				strFieldName = "XQremindflag";
				break;
			case 111:
				strFieldName = "OrganComCode";
				break;
			case 112:
				strFieldName = "ContFlag";
				break;
			case 113:
				strFieldName = "SaleDepart";
				break;
			case 114:
				strFieldName = "ChnlType";
				break;
			case 115:
				strFieldName = "RenewCount";
				break;
			case 116:
				strFieldName = "RenewContNo";
				break;
			case 117:
				strFieldName = "InitNumPeople";
				break;
			case 118:
				strFieldName = "InitMult";
				break;
			case 119:
				strFieldName = "InitAmnt";
				break;
			case 120:
				strFieldName = "InitRiskAmnt";
				break;
			case 121:
				strFieldName = "InitPrem";
				break;
			case 122:
				strFieldName = "InitStandPrem";
				break;
			case 123:
				strFieldName = "RiskAmnt";
				break;
			case 124:
				strFieldName = "StandPrem";
				break;
			case 125:
				strFieldName = "SumNumPeople";
				break;
			case 126:
				strFieldName = "SumPay";
				break;
			case 127:
				strFieldName = "EndDate";
				break;
			case 128:
				strFieldName = "PayEndDate";
				break;
			case 129:
				strFieldName = "AccKind";
				break;
			case 130:
				strFieldName = "SignOperator";
				break;
			case 131:
				strFieldName = "LoanStartDate";
				break;
			case 132:
				strFieldName = "LoanEndDate";
				break;
			case 133:
				strFieldName = "AgentBranchesCode";
				break;
			case 134:
				strFieldName = "NeedBillFlag";
				break;
			case 135:
				strFieldName = "ComCode";
				break;
			case 136:
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
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
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayLocation") ) {
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
		if( strFieldName.equals("SignCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConsignNo") ) {
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
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LostTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Lang") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("Dif") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOperator") ) {
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
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("SellType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewPayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FamilyContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrganizeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OrganizeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewAutoSendBankFlag") ) {
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
		if( strFieldName.equals("XQremindflag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrganComCode") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("SumPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoanStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LoanEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AgentBranchesCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedBillFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 47:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 52:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 53:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 54:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 57:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 58:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 59:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 73:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 79:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 80:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 99:
				nFieldType = Schema.TYPE_INT;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
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
			case 109:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 110:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 111:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 116:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 117:
				nFieldType = Schema.TYPE_INT;
				break;
			case 118:
				nFieldType = Schema.TYPE_INT;
				break;
			case 119:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 120:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 121:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 122:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 123:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 124:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 125:
				nFieldType = Schema.TYPE_INT;
				break;
			case 126:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 127:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 128:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 129:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 130:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 131:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 132:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 133:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 134:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 135:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 136:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
