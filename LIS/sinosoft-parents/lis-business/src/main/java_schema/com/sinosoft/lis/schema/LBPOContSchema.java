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
import com.sinosoft.lis.db.LBPOContDB;

/*
 * <p>ClassName: LBPOContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPOContSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOContSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 录入次数 */
	private int InputNo;
	/** 添空号 */
	private String FillNo;
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
	/** 交费间隔 */
	private String PayIntv;
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
	private String SignDate;
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
	private String PrintCount;
	/** 遗失补发次数 */
	private String LostTimes;
	/** 语种标记 */
	private String Lang;
	/** 币别 */
	private String Currency;
	/** 备注 */
	private String Remark;
	/** 人数 */
	private String Peoples;
	/** 份数 */
	private String Mult;
	/** 保费 */
	private String Prem;
	/** 保额 */
	private String Amnt;
	/** 累计保费 */
	private String SumPrem;
	/** 余额 */
	private String Dif;
	/** 交至日期 */
	private String PaytoDate;
	/** 首期交费日期 */
	private String FirstPayDate;
	/** 保单生效日期 */
	private String CValiDate;
	/** 录单人 */
	private String InputOperator;
	/** 录单完成日期 */
	private String InputDate;
	/** 录单完成时间 */
	private String InputTime;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private String ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 核保状态 */
	private String UWFlag;
	/** 核保人 */
	private String UWOperator;
	/** 核保完成日期 */
	private String UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 投保单/保单标志 */
	private String AppFlag;
	/** 投保单申请日期 */
	private String PolApplyDate;
	/** 保单送达日期 */
	private String GetPolDate;
	/** 保单送达时间 */
	private String GetPolTime;
	/** 保单回执客户签收日期 */
	private String CustomGetPolDate;
	/** 状态 */
	private String State;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private String MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private String ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 初审人 */
	private String FirstTrialOperator;
	/** 初审日期 */
	private String FirstTrialDate;
	/** 初审时间 */
	private String FirstTrialTime;
	/** 收单人 */
	private String ReceiveOperator;
	/** 收单日期 */
	private String ReceiveDate;
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
	private String RnewFlag;
	/** 代理人姓名 */
	private String AgentName;
	/** 中介机构名称 */
	private String AgentComName;
	/** 代理人签名 */
	private String SignAgentName;
	/** 代理人签名日期 */
	private String AgentSignDate;
	/** 附加豁免险标记 */
	private String ReleaseFlag;
	/** 投保人签名 */
	private String AppSignName;
	/** 第二被保人签名 */
	private String InsSignName2;
	/** 告知备注 */
	private String ImpartRemark;

	public static final int FIELDNUM = 96;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ContNo";
		pk[1] = "InputNo";
		pk[2] = "FillNo";

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
		LBPOContSchema cloned = (LBPOContSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public int getInputNo()
	{
		return InputNo;
	}
	public void setInputNo(int aInputNo)
	{
		InputNo = aInputNo;
	}
	public void setInputNo(String aInputNo)
	{
		if (aInputNo != null && !aInputNo.equals(""))
		{
			Integer tInteger = new Integer(aInputNo);
			int i = tInteger.intValue();
			InputNo = i;
		}
	}

	public String getFillNo()
	{
		return FillNo;
	}
	public void setFillNo(String aFillNo)
	{
		FillNo = aFillNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
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
		FamilyType = aFamilyType;
	}
	public String getFamilyID()
	{
		return FamilyID;
	}
	public void setFamilyID(String aFamilyID)
	{
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
		CardFlag = aCardFlag;
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
	* 关联统括保单处理
	*/
	public String getExecuteCom()
	{
		return ExecuteCom;
	}
	public void setExecuteCom(String aExecuteCom)
	{
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
		AgentCom = aAgentCom;
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
	/**
	* 代理机构计算佣金的方式
	*/
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
	}
	/**
	* 1-个人营销,2-团险直销,3-银行代理
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	/**
	* -1 -- 不定期交,<p>
	* 0  -- 趸交,<p>
	* 1  -- 月交<p>
	* 3  -- 季交<p>
	* 6  -- 半年交<p>
	* 12 -- 年交
	*/
	public String getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	/**
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
		PayLocation = aPayLocation;
	}
	/**
	* 备用
	*/
	public String getDisputedFlag()
	{
		return DisputedFlag;
	}
	public void setDisputedFlag(String aDisputedFlag)
	{
		DisputedFlag = aDisputedFlag;
	}
	/**
	* 备用
	*/
	public String getOutPayFlag()
	{
		return OutPayFlag;
	}
	public void setOutPayFlag(String aOutPayFlag)
	{
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
		GetPolMode = aGetPolMode;
	}
	public String getSignCom()
	{
		return SignCom;
	}
	public void setSignCom(String aSignCom)
	{
		SignCom = aSignCom;
	}
	public String getSignDate()
	{
		return SignDate;
	}
	public void setSignDate(String aSignDate)
	{
		SignDate = aSignDate;
	}
	public String getSignTime()
	{
		return SignTime;
	}
	public void setSignTime(String aSignTime)
	{
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
		ConsignNo = aConsignNo;
	}
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
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public String getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	public String getLostTimes()
	{
		return LostTimes;
	}
	public void setLostTimes(String aLostTimes)
	{
		LostTimes = aLostTimes;
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
	* 数据转化需求将此字段由char(60)扩为varchar2(1600)
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getPeoples()
	{
		return Peoples;
	}
	public void setPeoples(String aPeoples)
	{
		Peoples = aPeoples;
	}
	/**
	* 目前没有实际意义
	*/
	public String getMult()
	{
		return Mult;
	}
	public void setMult(String aMult)
	{
		Mult = aMult;
	}
	/**
	* 目前没有实际意义
	*/
	public String getPrem()
	{
		return Prem;
	}
	public void setPrem(String aPrem)
	{
		Prem = aPrem;
	}
	/**
	* 目前没有实际意义
	*/
	public String getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(String aAmnt)
	{
		Amnt = aAmnt;
	}
	public String getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	/**
	* 保单账户余额，用于交纳续期保费
	*/
	public String getDif()
	{
		return Dif;
	}
	public void setDif(String aDif)
	{
		Dif = aDif;
	}
	/**
	* 备用
	*/
	public String getPaytoDate()
	{
		return PaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public String getFirstPayDate()
	{
		return FirstPayDate;
	}
	public void setFirstPayDate(String aFirstPayDate)
	{
		FirstPayDate = aFirstPayDate;
	}
	public String getCValiDate()
	{
		return CValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public String getInputOperator()
	{
		return InputOperator;
	}
	public void setInputOperator(String aInputOperator)
	{
		InputOperator = aInputOperator;
	}
	public String getInputDate()
	{
		return InputDate;
	}
	public void setInputDate(String aInputDate)
	{
		InputDate = aInputDate;
	}
	public String getInputTime()
	{
		return InputTime;
	}
	public void setInputTime(String aInputTime)
	{
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
		ApproveFlag = aApproveFlag;
	}
	/**
	* 记录多主险的追加保险费（人民币大写）
	*/
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
		return ApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	/**
	* 记录多主险的追加保费
	*/
	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
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
		UWFlag = aUWFlag;
	}
	/**
	* 记录多主险的扫描员姓名
	*/
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
	}
	/**
	* 记录多主险的扫描日期
	*/
	public String getUWDate()
	{
		return UWDate;
	}
	public void setUWDate(String aUWDate)
	{
		UWDate = aUWDate;
	}
	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
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
		AppFlag = aAppFlag;
	}
	public String getPolApplyDate()
	{
		return PolApplyDate;
	}
	public void setPolApplyDate(String aPolApplyDate)
	{
		PolApplyDate = aPolApplyDate;
	}
	public String getGetPolDate()
	{
		return GetPolDate;
	}
	public void setGetPolDate(String aGetPolDate)
	{
		GetPolDate = aGetPolDate;
	}
	public String getGetPolTime()
	{
		return GetPolTime;
	}
	public void setGetPolTime(String aGetPolTime)
	{
		GetPolTime = aGetPolTime;
	}
	public String getCustomGetPolDate()
	{
		return CustomGetPolDate;
	}
	public void setCustomGetPolDate(String aCustomGetPolDate)
	{
		CustomGetPolDate = aCustomGetPolDate;
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
		State = aState;
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
		return MakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		MakeDate = aMakeDate;
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
		return ModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	public String getFirstTrialOperator()
	{
		return FirstTrialOperator;
	}
	public void setFirstTrialOperator(String aFirstTrialOperator)
	{
		FirstTrialOperator = aFirstTrialOperator;
	}
	public String getFirstTrialDate()
	{
		return FirstTrialDate;
	}
	public void setFirstTrialDate(String aFirstTrialDate)
	{
		FirstTrialDate = aFirstTrialDate;
	}
	public String getFirstTrialTime()
	{
		return FirstTrialTime;
	}
	public void setFirstTrialTime(String aFirstTrialTime)
	{
		FirstTrialTime = aFirstTrialTime;
	}
	public String getReceiveOperator()
	{
		return ReceiveOperator;
	}
	public void setReceiveOperator(String aReceiveOperator)
	{
		ReceiveOperator = aReceiveOperator;
	}
	public String getReceiveDate()
	{
		return ReceiveDate;
	}
	public void setReceiveDate(String aReceiveDate)
	{
		ReceiveDate = aReceiveDate;
	}
	public String getReceiveTime()
	{
		return ReceiveTime;
	}
	public void setReceiveTime(String aReceiveTime)
	{
		ReceiveTime = aReceiveTime;
	}
	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
	}
	public String getSellType()
	{
		return SellType;
	}
	public void setSellType(String aSellType)
	{
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
		ForceUWFlag = aForceUWFlag;
	}
	public String getForceUWReason()
	{
		return ForceUWReason;
	}
	public void setForceUWReason(String aForceUWReason)
	{
		ForceUWReason = aForceUWReason;
	}
	public String getNewBankCode()
	{
		return NewBankCode;
	}
	public void setNewBankCode(String aNewBankCode)
	{
		NewBankCode = aNewBankCode;
	}
	public String getNewBankAccNo()
	{
		return NewBankAccNo;
	}
	public void setNewBankAccNo(String aNewBankAccNo)
	{
		NewBankAccNo = aNewBankAccNo;
	}
	public String getNewAccName()
	{
		return NewAccName;
	}
	public void setNewAccName(String aNewAccName)
	{
		NewAccName = aNewAccName;
	}
	public String getNewPayMode()
	{
		return NewPayMode;
	}
	public void setNewPayMode(String aNewPayMode)
	{
		NewPayMode = aNewPayMode;
	}
	public String getAgentBankCode()
	{
		return AgentBankCode;
	}
	public void setAgentBankCode(String aAgentBankCode)
	{
		AgentBankCode = aAgentBankCode;
	}
	public String getBankAgent()
	{
		return BankAgent;
	}
	public void setBankAgent(String aBankAgent)
	{
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
		AutoPayFlag = aAutoPayFlag;
	}
	/**
	* -2 -- 非续保 <p>
	* -1 -- 自动续保<p>
	* 0 -- 人工续保
	*/
	public String getRnewFlag()
	{
		return RnewFlag;
	}
	public void setRnewFlag(String aRnewFlag)
	{
		RnewFlag = aRnewFlag;
	}
	public String getAgentName()
	{
		return AgentName;
	}
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	public String getAgentComName()
	{
		return AgentComName;
	}
	public void setAgentComName(String aAgentComName)
	{
		AgentComName = aAgentComName;
	}
	public String getSignAgentName()
	{
		return SignAgentName;
	}
	public void setSignAgentName(String aSignAgentName)
	{
		SignAgentName = aSignAgentName;
	}
	public String getAgentSignDate()
	{
		return AgentSignDate;
	}
	public void setAgentSignDate(String aAgentSignDate)
	{
		AgentSignDate = aAgentSignDate;
	}
	public String getReleaseFlag()
	{
		return ReleaseFlag;
	}
	public void setReleaseFlag(String aReleaseFlag)
	{
		ReleaseFlag = aReleaseFlag;
	}
	public String getAppSignName()
	{
		return AppSignName;
	}
	public void setAppSignName(String aAppSignName)
	{
		AppSignName = aAppSignName;
	}
	/**
	* 多主险时用来存放“被保险人/法定监护人签名”
	*/
	public String getInsSignName2()
	{
		return InsSignName2;
	}
	public void setInsSignName2(String aInsSignName2)
	{
		InsSignName2 = aInsSignName2;
	}
	public String getImpartRemark()
	{
		return ImpartRemark;
	}
	public void setImpartRemark(String aImpartRemark)
	{
		ImpartRemark = aImpartRemark;
	}

	/**
	* 使用另外一个 LBPOContSchema 对象给 Schema 赋值
	* @param: aLBPOContSchema LBPOContSchema
	**/
	public void setSchema(LBPOContSchema aLBPOContSchema)
	{
		this.GrpContNo = aLBPOContSchema.getGrpContNo();
		this.ContNo = aLBPOContSchema.getContNo();
		this.InputNo = aLBPOContSchema.getInputNo();
		this.FillNo = aLBPOContSchema.getFillNo();
		this.ProposalContNo = aLBPOContSchema.getProposalContNo();
		this.PrtNo = aLBPOContSchema.getPrtNo();
		this.ContType = aLBPOContSchema.getContType();
		this.FamilyType = aLBPOContSchema.getFamilyType();
		this.FamilyID = aLBPOContSchema.getFamilyID();
		this.PolType = aLBPOContSchema.getPolType();
		this.CardFlag = aLBPOContSchema.getCardFlag();
		this.ManageCom = aLBPOContSchema.getManageCom();
		this.ExecuteCom = aLBPOContSchema.getExecuteCom();
		this.AgentCom = aLBPOContSchema.getAgentCom();
		this.AgentCode = aLBPOContSchema.getAgentCode();
		this.AgentGroup = aLBPOContSchema.getAgentGroup();
		this.AgentCode1 = aLBPOContSchema.getAgentCode1();
		this.AgentType = aLBPOContSchema.getAgentType();
		this.SaleChnl = aLBPOContSchema.getSaleChnl();
		this.Handler = aLBPOContSchema.getHandler();
		this.Password = aLBPOContSchema.getPassword();
		this.PayIntv = aLBPOContSchema.getPayIntv();
		this.PayMode = aLBPOContSchema.getPayMode();
		this.PayLocation = aLBPOContSchema.getPayLocation();
		this.DisputedFlag = aLBPOContSchema.getDisputedFlag();
		this.OutPayFlag = aLBPOContSchema.getOutPayFlag();
		this.GetPolMode = aLBPOContSchema.getGetPolMode();
		this.SignCom = aLBPOContSchema.getSignCom();
		this.SignDate = aLBPOContSchema.getSignDate();
		this.SignTime = aLBPOContSchema.getSignTime();
		this.ConsignNo = aLBPOContSchema.getConsignNo();
		this.BankCode = aLBPOContSchema.getBankCode();
		this.BankAccNo = aLBPOContSchema.getBankAccNo();
		this.AccName = aLBPOContSchema.getAccName();
		this.PrintCount = aLBPOContSchema.getPrintCount();
		this.LostTimes = aLBPOContSchema.getLostTimes();
		this.Lang = aLBPOContSchema.getLang();
		this.Currency = aLBPOContSchema.getCurrency();
		this.Remark = aLBPOContSchema.getRemark();
		this.Peoples = aLBPOContSchema.getPeoples();
		this.Mult = aLBPOContSchema.getMult();
		this.Prem = aLBPOContSchema.getPrem();
		this.Amnt = aLBPOContSchema.getAmnt();
		this.SumPrem = aLBPOContSchema.getSumPrem();
		this.Dif = aLBPOContSchema.getDif();
		this.PaytoDate = aLBPOContSchema.getPaytoDate();
		this.FirstPayDate = aLBPOContSchema.getFirstPayDate();
		this.CValiDate = aLBPOContSchema.getCValiDate();
		this.InputOperator = aLBPOContSchema.getInputOperator();
		this.InputDate = aLBPOContSchema.getInputDate();
		this.InputTime = aLBPOContSchema.getInputTime();
		this.ApproveFlag = aLBPOContSchema.getApproveFlag();
		this.ApproveCode = aLBPOContSchema.getApproveCode();
		this.ApproveDate = aLBPOContSchema.getApproveDate();
		this.ApproveTime = aLBPOContSchema.getApproveTime();
		this.UWFlag = aLBPOContSchema.getUWFlag();
		this.UWOperator = aLBPOContSchema.getUWOperator();
		this.UWDate = aLBPOContSchema.getUWDate();
		this.UWTime = aLBPOContSchema.getUWTime();
		this.AppFlag = aLBPOContSchema.getAppFlag();
		this.PolApplyDate = aLBPOContSchema.getPolApplyDate();
		this.GetPolDate = aLBPOContSchema.getGetPolDate();
		this.GetPolTime = aLBPOContSchema.getGetPolTime();
		this.CustomGetPolDate = aLBPOContSchema.getCustomGetPolDate();
		this.State = aLBPOContSchema.getState();
		this.Operator = aLBPOContSchema.getOperator();
		this.MakeDate = aLBPOContSchema.getMakeDate();
		this.MakeTime = aLBPOContSchema.getMakeTime();
		this.ModifyDate = aLBPOContSchema.getModifyDate();
		this.ModifyTime = aLBPOContSchema.getModifyTime();
		this.FirstTrialOperator = aLBPOContSchema.getFirstTrialOperator();
		this.FirstTrialDate = aLBPOContSchema.getFirstTrialDate();
		this.FirstTrialTime = aLBPOContSchema.getFirstTrialTime();
		this.ReceiveOperator = aLBPOContSchema.getReceiveOperator();
		this.ReceiveDate = aLBPOContSchema.getReceiveDate();
		this.ReceiveTime = aLBPOContSchema.getReceiveTime();
		this.TempFeeNo = aLBPOContSchema.getTempFeeNo();
		this.SellType = aLBPOContSchema.getSellType();
		this.ForceUWFlag = aLBPOContSchema.getForceUWFlag();
		this.ForceUWReason = aLBPOContSchema.getForceUWReason();
		this.NewBankCode = aLBPOContSchema.getNewBankCode();
		this.NewBankAccNo = aLBPOContSchema.getNewBankAccNo();
		this.NewAccName = aLBPOContSchema.getNewAccName();
		this.NewPayMode = aLBPOContSchema.getNewPayMode();
		this.AgentBankCode = aLBPOContSchema.getAgentBankCode();
		this.BankAgent = aLBPOContSchema.getBankAgent();
		this.AutoPayFlag = aLBPOContSchema.getAutoPayFlag();
		this.RnewFlag = aLBPOContSchema.getRnewFlag();
		this.AgentName = aLBPOContSchema.getAgentName();
		this.AgentComName = aLBPOContSchema.getAgentComName();
		this.SignAgentName = aLBPOContSchema.getSignAgentName();
		this.AgentSignDate = aLBPOContSchema.getAgentSignDate();
		this.ReleaseFlag = aLBPOContSchema.getReleaseFlag();
		this.AppSignName = aLBPOContSchema.getAppSignName();
		this.InsSignName2 = aLBPOContSchema.getInsSignName2();
		this.ImpartRemark = aLBPOContSchema.getImpartRemark();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.InputNo = rs.getInt("InputNo");
			if( rs.getString("FillNo") == null )
				this.FillNo = null;
			else
				this.FillNo = rs.getString("FillNo").trim();

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

			if( rs.getString("PayIntv") == null )
				this.PayIntv = null;
			else
				this.PayIntv = rs.getString("PayIntv").trim();

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

			if( rs.getString("SignDate") == null )
				this.SignDate = null;
			else
				this.SignDate = rs.getString("SignDate").trim();

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

			if( rs.getString("PrintCount") == null )
				this.PrintCount = null;
			else
				this.PrintCount = rs.getString("PrintCount").trim();

			if( rs.getString("LostTimes") == null )
				this.LostTimes = null;
			else
				this.LostTimes = rs.getString("LostTimes").trim();

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

			if( rs.getString("Peoples") == null )
				this.Peoples = null;
			else
				this.Peoples = rs.getString("Peoples").trim();

			if( rs.getString("Mult") == null )
				this.Mult = null;
			else
				this.Mult = rs.getString("Mult").trim();

			if( rs.getString("Prem") == null )
				this.Prem = null;
			else
				this.Prem = rs.getString("Prem").trim();

			if( rs.getString("Amnt") == null )
				this.Amnt = null;
			else
				this.Amnt = rs.getString("Amnt").trim();

			if( rs.getString("SumPrem") == null )
				this.SumPrem = null;
			else
				this.SumPrem = rs.getString("SumPrem").trim();

			if( rs.getString("Dif") == null )
				this.Dif = null;
			else
				this.Dif = rs.getString("Dif").trim();

			if( rs.getString("PaytoDate") == null )
				this.PaytoDate = null;
			else
				this.PaytoDate = rs.getString("PaytoDate").trim();

			if( rs.getString("FirstPayDate") == null )
				this.FirstPayDate = null;
			else
				this.FirstPayDate = rs.getString("FirstPayDate").trim();

			if( rs.getString("CValiDate") == null )
				this.CValiDate = null;
			else
				this.CValiDate = rs.getString("CValiDate").trim();

			if( rs.getString("InputOperator") == null )
				this.InputOperator = null;
			else
				this.InputOperator = rs.getString("InputOperator").trim();

			if( rs.getString("InputDate") == null )
				this.InputDate = null;
			else
				this.InputDate = rs.getString("InputDate").trim();

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

			if( rs.getString("ApproveDate") == null )
				this.ApproveDate = null;
			else
				this.ApproveDate = rs.getString("ApproveDate").trim();

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

			if( rs.getString("UWDate") == null )
				this.UWDate = null;
			else
				this.UWDate = rs.getString("UWDate").trim();

			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("PolApplyDate") == null )
				this.PolApplyDate = null;
			else
				this.PolApplyDate = rs.getString("PolApplyDate").trim();

			if( rs.getString("GetPolDate") == null )
				this.GetPolDate = null;
			else
				this.GetPolDate = rs.getString("GetPolDate").trim();

			if( rs.getString("GetPolTime") == null )
				this.GetPolTime = null;
			else
				this.GetPolTime = rs.getString("GetPolTime").trim();

			if( rs.getString("CustomGetPolDate") == null )
				this.CustomGetPolDate = null;
			else
				this.CustomGetPolDate = rs.getString("CustomGetPolDate").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeDate") == null )
				this.MakeDate = null;
			else
				this.MakeDate = rs.getString("MakeDate").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyDate") == null )
				this.ModifyDate = null;
			else
				this.ModifyDate = rs.getString("ModifyDate").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("FirstTrialOperator") == null )
				this.FirstTrialOperator = null;
			else
				this.FirstTrialOperator = rs.getString("FirstTrialOperator").trim();

			if( rs.getString("FirstTrialDate") == null )
				this.FirstTrialDate = null;
			else
				this.FirstTrialDate = rs.getString("FirstTrialDate").trim();

			if( rs.getString("FirstTrialTime") == null )
				this.FirstTrialTime = null;
			else
				this.FirstTrialTime = rs.getString("FirstTrialTime").trim();

			if( rs.getString("ReceiveOperator") == null )
				this.ReceiveOperator = null;
			else
				this.ReceiveOperator = rs.getString("ReceiveOperator").trim();

			if( rs.getString("ReceiveDate") == null )
				this.ReceiveDate = null;
			else
				this.ReceiveDate = rs.getString("ReceiveDate").trim();

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

			if( rs.getString("RnewFlag") == null )
				this.RnewFlag = null;
			else
				this.RnewFlag = rs.getString("RnewFlag").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("AgentComName") == null )
				this.AgentComName = null;
			else
				this.AgentComName = rs.getString("AgentComName").trim();

			if( rs.getString("SignAgentName") == null )
				this.SignAgentName = null;
			else
				this.SignAgentName = rs.getString("SignAgentName").trim();

			if( rs.getString("AgentSignDate") == null )
				this.AgentSignDate = null;
			else
				this.AgentSignDate = rs.getString("AgentSignDate").trim();

			if( rs.getString("ReleaseFlag") == null )
				this.ReleaseFlag = null;
			else
				this.ReleaseFlag = rs.getString("ReleaseFlag").trim();

			if( rs.getString("AppSignName") == null )
				this.AppSignName = null;
			else
				this.AppSignName = rs.getString("AppSignName").trim();

			if( rs.getString("InsSignName2") == null )
				this.InsSignName2 = null;
			else
				this.InsSignName2 = rs.getString("InsSignName2").trim();

			if( rs.getString("ImpartRemark") == null )
				this.ImpartRemark = null;
			else
				this.ImpartRemark = rs.getString("ImpartRemark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPOCont表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOContSchema getSchema()
	{
		LBPOContSchema aLBPOContSchema = new LBPOContSchema();
		aLBPOContSchema.setSchema(this);
		return aLBPOContSchema;
	}

	public LBPOContDB getDB()
	{
		LBPOContDB aDBOper = new LBPOContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOCont描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InputNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FillNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(PayIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayLocation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisputedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConsignNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintCount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LostTimes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Lang)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Peoples)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Amnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SumPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Dif)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PaytoDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstPayDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CValiDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolApplyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomGetPolDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstTrialTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveDate)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(RnewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentComName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignAgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentSignDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReleaseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppSignName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsSignName2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartRemark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InputNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			FillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
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
			PayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PayLocation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			DisputedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			OutPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			GetPolMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			SignCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			SignDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ConsignNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			PrintCount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			LostTimes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Lang = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Peoples = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Mult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Prem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			Amnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			SumPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			Dif = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			PaytoDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			FirstPayDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			CValiDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			InputOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			InputDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			ApproveDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			UWDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			PolApplyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			GetPolDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			CustomGetPolDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			MakeDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			ModifyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			FirstTrialOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			FirstTrialDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			FirstTrialTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			ReceiveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			ReceiveDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			ReceiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			SellType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			ForceUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			ForceUWReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80, SysConst.PACKAGESPILTER );
			NewBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
			NewBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			NewAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			NewPayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			AgentBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			BankAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			RnewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			SignAgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			AgentSignDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			ReleaseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			AppSignName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			InsSignName2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95, SysConst.PACKAGESPILTER );
			ImpartRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputNo));
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FillNo));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(PaytoDate));
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstPayDate));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CValiDate));
		}
		if (FCode.equalsIgnoreCase("InputOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOperator));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolApplyDate));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolDate));
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolTime));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomGetPolDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstTrialDate));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveDate));
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
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equalsIgnoreCase("AgentComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComName));
		}
		if (FCode.equalsIgnoreCase("SignAgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignAgentName));
		}
		if (FCode.equalsIgnoreCase("AgentSignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentSignDate));
		}
		if (FCode.equalsIgnoreCase("ReleaseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReleaseFlag));
		}
		if (FCode.equalsIgnoreCase("AppSignName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppSignName));
		}
		if (FCode.equalsIgnoreCase("InsSignName2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsSignName2));
		}
		if (FCode.equalsIgnoreCase("ImpartRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartRemark));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = String.valueOf(InputNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FillNo);
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
				strFieldValue = StrTool.GBKToUnicode(PayIntv);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(PayLocation);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(DisputedFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(OutPayFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(GetPolMode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(SignCom);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(SignDate);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ConsignNo);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(PrintCount);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(LostTimes);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Lang);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Peoples);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Mult);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Prem);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(Amnt);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(SumPrem);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(Dif);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(PaytoDate);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(FirstPayDate);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(CValiDate);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(InputOperator);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(InputDate);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(ApproveDate);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(UWDate);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(PolApplyDate);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(GetPolDate);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(CustomGetPolDate);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(MakeDate);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(ModifyDate);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialOperator);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialDate);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(FirstTrialTime);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(ReceiveOperator);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(ReceiveDate);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(ReceiveTime);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(SellType);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(ForceUWFlag);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(ForceUWReason);
				break;
			case 80:
				strFieldValue = StrTool.GBKToUnicode(NewBankCode);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(NewBankAccNo);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(NewAccName);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(NewPayMode);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(AgentBankCode);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(BankAgent);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(RnewFlag);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(SignAgentName);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(AgentSignDate);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(ReleaseFlag);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(AppSignName);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(InsSignName2);
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(ImpartRemark);
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
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InputNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FillNo = FValue.trim();
			}
			else
				FillNo = null;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayIntv = FValue.trim();
			}
			else
				PayIntv = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				SignDate = FValue.trim();
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
				PrintCount = FValue.trim();
			}
			else
				PrintCount = null;
		}
		if (FCode.equalsIgnoreCase("LostTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LostTimes = FValue.trim();
			}
			else
				LostTimes = null;
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
				Peoples = FValue.trim();
			}
			else
				Peoples = null;
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mult = FValue.trim();
			}
			else
				Mult = null;
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prem = FValue.trim();
			}
			else
				Prem = null;
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Amnt = FValue.trim();
			}
			else
				Amnt = null;
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SumPrem = FValue.trim();
			}
			else
				SumPrem = null;
		}
		if (FCode.equalsIgnoreCase("Dif"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Dif = FValue.trim();
			}
			else
				Dif = null;
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PaytoDate = FValue.trim();
			}
			else
				PaytoDate = null;
		}
		if (FCode.equalsIgnoreCase("FirstPayDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstPayDate = FValue.trim();
			}
			else
				FirstPayDate = null;
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CValiDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				InputDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ApproveDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				UWDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				PolApplyDate = FValue.trim();
			}
			else
				PolApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				CustomGetPolDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				MakeDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ModifyDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				FirstTrialDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveDate = FValue.trim();
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
				RnewFlag = FValue.trim();
			}
			else
				RnewFlag = null;
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
		if (FCode.equalsIgnoreCase("AgentComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComName = FValue.trim();
			}
			else
				AgentComName = null;
		}
		if (FCode.equalsIgnoreCase("SignAgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignAgentName = FValue.trim();
			}
			else
				SignAgentName = null;
		}
		if (FCode.equalsIgnoreCase("AgentSignDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentSignDate = FValue.trim();
			}
			else
				AgentSignDate = null;
		}
		if (FCode.equalsIgnoreCase("ReleaseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReleaseFlag = FValue.trim();
			}
			else
				ReleaseFlag = null;
		}
		if (FCode.equalsIgnoreCase("AppSignName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppSignName = FValue.trim();
			}
			else
				AppSignName = null;
		}
		if (FCode.equalsIgnoreCase("InsSignName2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsSignName2 = FValue.trim();
			}
			else
				InsSignName2 = null;
		}
		if (FCode.equalsIgnoreCase("ImpartRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartRemark = FValue.trim();
			}
			else
				ImpartRemark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPOContSchema other = (LBPOContSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& InputNo == other.getInputNo()
			&& FillNo.equals(other.getFillNo())
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
			&& PayIntv.equals(other.getPayIntv())
			&& PayMode.equals(other.getPayMode())
			&& PayLocation.equals(other.getPayLocation())
			&& DisputedFlag.equals(other.getDisputedFlag())
			&& OutPayFlag.equals(other.getOutPayFlag())
			&& GetPolMode.equals(other.getGetPolMode())
			&& SignCom.equals(other.getSignCom())
			&& SignDate.equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& ConsignNo.equals(other.getConsignNo())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& PrintCount.equals(other.getPrintCount())
			&& LostTimes.equals(other.getLostTimes())
			&& Lang.equals(other.getLang())
			&& Currency.equals(other.getCurrency())
			&& Remark.equals(other.getRemark())
			&& Peoples.equals(other.getPeoples())
			&& Mult.equals(other.getMult())
			&& Prem.equals(other.getPrem())
			&& Amnt.equals(other.getAmnt())
			&& SumPrem.equals(other.getSumPrem())
			&& Dif.equals(other.getDif())
			&& PaytoDate.equals(other.getPaytoDate())
			&& FirstPayDate.equals(other.getFirstPayDate())
			&& CValiDate.equals(other.getCValiDate())
			&& InputOperator.equals(other.getInputOperator())
			&& InputDate.equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& ApproveDate.equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWFlag.equals(other.getUWFlag())
			&& UWOperator.equals(other.getUWOperator())
			&& UWDate.equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& AppFlag.equals(other.getAppFlag())
			&& PolApplyDate.equals(other.getPolApplyDate())
			&& GetPolDate.equals(other.getGetPolDate())
			&& GetPolTime.equals(other.getGetPolTime())
			&& CustomGetPolDate.equals(other.getCustomGetPolDate())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& MakeDate.equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate.equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& FirstTrialOperator.equals(other.getFirstTrialOperator())
			&& FirstTrialDate.equals(other.getFirstTrialDate())
			&& FirstTrialTime.equals(other.getFirstTrialTime())
			&& ReceiveOperator.equals(other.getReceiveOperator())
			&& ReceiveDate.equals(other.getReceiveDate())
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
			&& RnewFlag.equals(other.getRnewFlag())
			&& AgentName.equals(other.getAgentName())
			&& AgentComName.equals(other.getAgentComName())
			&& SignAgentName.equals(other.getSignAgentName())
			&& AgentSignDate.equals(other.getAgentSignDate())
			&& ReleaseFlag.equals(other.getReleaseFlag())
			&& AppSignName.equals(other.getAppSignName())
			&& InsSignName2.equals(other.getInsSignName2())
			&& ImpartRemark.equals(other.getImpartRemark());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("InputNo") ) {
			return 2;
		}
		if( strFieldName.equals("FillNo") ) {
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
		if( strFieldName.equals("PayIntv") ) {
			return 21;
		}
		if( strFieldName.equals("PayMode") ) {
			return 22;
		}
		if( strFieldName.equals("PayLocation") ) {
			return 23;
		}
		if( strFieldName.equals("DisputedFlag") ) {
			return 24;
		}
		if( strFieldName.equals("OutPayFlag") ) {
			return 25;
		}
		if( strFieldName.equals("GetPolMode") ) {
			return 26;
		}
		if( strFieldName.equals("SignCom") ) {
			return 27;
		}
		if( strFieldName.equals("SignDate") ) {
			return 28;
		}
		if( strFieldName.equals("SignTime") ) {
			return 29;
		}
		if( strFieldName.equals("ConsignNo") ) {
			return 30;
		}
		if( strFieldName.equals("BankCode") ) {
			return 31;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 32;
		}
		if( strFieldName.equals("AccName") ) {
			return 33;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 34;
		}
		if( strFieldName.equals("LostTimes") ) {
			return 35;
		}
		if( strFieldName.equals("Lang") ) {
			return 36;
		}
		if( strFieldName.equals("Currency") ) {
			return 37;
		}
		if( strFieldName.equals("Remark") ) {
			return 38;
		}
		if( strFieldName.equals("Peoples") ) {
			return 39;
		}
		if( strFieldName.equals("Mult") ) {
			return 40;
		}
		if( strFieldName.equals("Prem") ) {
			return 41;
		}
		if( strFieldName.equals("Amnt") ) {
			return 42;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 43;
		}
		if( strFieldName.equals("Dif") ) {
			return 44;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 45;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return 46;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 47;
		}
		if( strFieldName.equals("InputOperator") ) {
			return 48;
		}
		if( strFieldName.equals("InputDate") ) {
			return 49;
		}
		if( strFieldName.equals("InputTime") ) {
			return 50;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 51;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 52;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 53;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 54;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 55;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 56;
		}
		if( strFieldName.equals("UWDate") ) {
			return 57;
		}
		if( strFieldName.equals("UWTime") ) {
			return 58;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 59;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return 60;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 61;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 62;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 63;
		}
		if( strFieldName.equals("State") ) {
			return 64;
		}
		if( strFieldName.equals("Operator") ) {
			return 65;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 66;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 67;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 68;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 69;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return 70;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return 71;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return 72;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return 73;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 74;
		}
		if( strFieldName.equals("ReceiveTime") ) {
			return 75;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 76;
		}
		if( strFieldName.equals("SellType") ) {
			return 77;
		}
		if( strFieldName.equals("ForceUWFlag") ) {
			return 78;
		}
		if( strFieldName.equals("ForceUWReason") ) {
			return 79;
		}
		if( strFieldName.equals("NewBankCode") ) {
			return 80;
		}
		if( strFieldName.equals("NewBankAccNo") ) {
			return 81;
		}
		if( strFieldName.equals("NewAccName") ) {
			return 82;
		}
		if( strFieldName.equals("NewPayMode") ) {
			return 83;
		}
		if( strFieldName.equals("AgentBankCode") ) {
			return 84;
		}
		if( strFieldName.equals("BankAgent") ) {
			return 85;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 86;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 87;
		}
		if( strFieldName.equals("AgentName") ) {
			return 88;
		}
		if( strFieldName.equals("AgentComName") ) {
			return 89;
		}
		if( strFieldName.equals("SignAgentName") ) {
			return 90;
		}
		if( strFieldName.equals("AgentSignDate") ) {
			return 91;
		}
		if( strFieldName.equals("ReleaseFlag") ) {
			return 92;
		}
		if( strFieldName.equals("AppSignName") ) {
			return 93;
		}
		if( strFieldName.equals("InsSignName2") ) {
			return 94;
		}
		if( strFieldName.equals("ImpartRemark") ) {
			return 95;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "InputNo";
				break;
			case 3:
				strFieldName = "FillNo";
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
				strFieldName = "PayIntv";
				break;
			case 22:
				strFieldName = "PayMode";
				break;
			case 23:
				strFieldName = "PayLocation";
				break;
			case 24:
				strFieldName = "DisputedFlag";
				break;
			case 25:
				strFieldName = "OutPayFlag";
				break;
			case 26:
				strFieldName = "GetPolMode";
				break;
			case 27:
				strFieldName = "SignCom";
				break;
			case 28:
				strFieldName = "SignDate";
				break;
			case 29:
				strFieldName = "SignTime";
				break;
			case 30:
				strFieldName = "ConsignNo";
				break;
			case 31:
				strFieldName = "BankCode";
				break;
			case 32:
				strFieldName = "BankAccNo";
				break;
			case 33:
				strFieldName = "AccName";
				break;
			case 34:
				strFieldName = "PrintCount";
				break;
			case 35:
				strFieldName = "LostTimes";
				break;
			case 36:
				strFieldName = "Lang";
				break;
			case 37:
				strFieldName = "Currency";
				break;
			case 38:
				strFieldName = "Remark";
				break;
			case 39:
				strFieldName = "Peoples";
				break;
			case 40:
				strFieldName = "Mult";
				break;
			case 41:
				strFieldName = "Prem";
				break;
			case 42:
				strFieldName = "Amnt";
				break;
			case 43:
				strFieldName = "SumPrem";
				break;
			case 44:
				strFieldName = "Dif";
				break;
			case 45:
				strFieldName = "PaytoDate";
				break;
			case 46:
				strFieldName = "FirstPayDate";
				break;
			case 47:
				strFieldName = "CValiDate";
				break;
			case 48:
				strFieldName = "InputOperator";
				break;
			case 49:
				strFieldName = "InputDate";
				break;
			case 50:
				strFieldName = "InputTime";
				break;
			case 51:
				strFieldName = "ApproveFlag";
				break;
			case 52:
				strFieldName = "ApproveCode";
				break;
			case 53:
				strFieldName = "ApproveDate";
				break;
			case 54:
				strFieldName = "ApproveTime";
				break;
			case 55:
				strFieldName = "UWFlag";
				break;
			case 56:
				strFieldName = "UWOperator";
				break;
			case 57:
				strFieldName = "UWDate";
				break;
			case 58:
				strFieldName = "UWTime";
				break;
			case 59:
				strFieldName = "AppFlag";
				break;
			case 60:
				strFieldName = "PolApplyDate";
				break;
			case 61:
				strFieldName = "GetPolDate";
				break;
			case 62:
				strFieldName = "GetPolTime";
				break;
			case 63:
				strFieldName = "CustomGetPolDate";
				break;
			case 64:
				strFieldName = "State";
				break;
			case 65:
				strFieldName = "Operator";
				break;
			case 66:
				strFieldName = "MakeDate";
				break;
			case 67:
				strFieldName = "MakeTime";
				break;
			case 68:
				strFieldName = "ModifyDate";
				break;
			case 69:
				strFieldName = "ModifyTime";
				break;
			case 70:
				strFieldName = "FirstTrialOperator";
				break;
			case 71:
				strFieldName = "FirstTrialDate";
				break;
			case 72:
				strFieldName = "FirstTrialTime";
				break;
			case 73:
				strFieldName = "ReceiveOperator";
				break;
			case 74:
				strFieldName = "ReceiveDate";
				break;
			case 75:
				strFieldName = "ReceiveTime";
				break;
			case 76:
				strFieldName = "TempFeeNo";
				break;
			case 77:
				strFieldName = "SellType";
				break;
			case 78:
				strFieldName = "ForceUWFlag";
				break;
			case 79:
				strFieldName = "ForceUWReason";
				break;
			case 80:
				strFieldName = "NewBankCode";
				break;
			case 81:
				strFieldName = "NewBankAccNo";
				break;
			case 82:
				strFieldName = "NewAccName";
				break;
			case 83:
				strFieldName = "NewPayMode";
				break;
			case 84:
				strFieldName = "AgentBankCode";
				break;
			case 85:
				strFieldName = "BankAgent";
				break;
			case 86:
				strFieldName = "AutoPayFlag";
				break;
			case 87:
				strFieldName = "RnewFlag";
				break;
			case 88:
				strFieldName = "AgentName";
				break;
			case 89:
				strFieldName = "AgentComName";
				break;
			case 90:
				strFieldName = "SignAgentName";
				break;
			case 91:
				strFieldName = "AgentSignDate";
				break;
			case 92:
				strFieldName = "ReleaseFlag";
				break;
			case 93:
				strFieldName = "AppSignName";
				break;
			case 94:
				strFieldName = "InsSignName2";
				break;
			case 95:
				strFieldName = "ImpartRemark";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FillNo") ) {
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
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LostTimes") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Dif") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstPayDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolApplyDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstTrialTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return Schema.TYPE_STRING;
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignAgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentSignDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReleaseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppSignName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsSignName2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartRemark") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
