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
import com.sinosoft.lis.db.LJAGetDB;

/*
 * <p>ClassName: LJAGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务付费
 */
public class LJAGetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJAGetSchema.class);
	// @Field
	/** 实付号码 */
	private String ActuGetNo;
	/** 其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 交费方式 */
	private String PayMode;
	/** 银行在途标志 */
	private String BankOnTheWayFlag;
	/** 银行转帐成功标记 */
	private String BankSuccFlag;
	/** 送银行次数 */
	private int SendBankCount;
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
	/** 帐户名 */
	private String AccName;
	/** 最早付费日期 */
	private Date StartGetDate;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 总给付金额 */
	private double SumGetMoney;
	/** 销售渠道 */
	private String SaleChnl;
	/** 应付日期 */
	private Date ShouldDate;
	/** 财务到帐日期 */
	private Date EnterAccDate;
	/** 财务确认日期 */
	private Date ConfDate;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 给付通知书号码 */
	private String GetNoticeNo;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 领取人 */
	private String Drawer;
	/** 领取人身份证号 */
	private String DrawerID;
	/** 流水号 */
	private String SerialNo;
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
	/** 实际领取人 */
	private String ActualDrawer;
	/** 实际领取人身份证号 */
	private String ActualDrawerID;
	/** 服务机构 */
	private String PolicyCom;
	/** 业务状态 */
	private String OperState;
	/** Billpayreason */
	private String BILLPAYREASON;
	/** 被保险人客户号 */
	private String InsuredNo;
	/** 证件类型 */
	private String DrawerType;
	/** 币别 */
	private String Currency;
	/** 团体保单号 */
	private String GrpContNo;
	/** 付费对象 */
	private String GetObj;
	/** 付费模式 */
	private String GetMode;
	/** 共保公司标记 */
	private String GuaranteeFlag;
	/** 共保公司编码 */
	private String Insurancecom;
	/** 状态 */
	private String State;
	/** 审核操作人 */
	private String ConfirmOperator;
	/** 审核日期 */
	private Date ConfirmDate;
	/** 审核时间 */
	private String ConfirmTime;
	/** 审核结论 */
	private String ConfirmConclusion;
	/** 审核结论描述 */
	private String ConfirmDesc;
	/** 定期结算标志 */
	private String BalanceOnTime;
	/** 定期结算汇总号码 */
	private String BalanceAllNo;
	/** 定期结算关联号码 */
	private String BalanceRelaNo;
	/** 大额关联号 */
	private String BigRelaNo;
	/** 付款银行编码 */
	private String OutBankCode;
	/** 付款银行账号 */
	private String OutBankAccNo;
	/** 发盘关联号 */
	private String SendRelaNo;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 净费用 */
	private double NetAmount;
	/** 税额 */
	private double TaxAmount;
	/** 税率 */
	private double Tax;

	public static final int FIELDNUM = 64;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJAGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ActuGetNo";
		pk[1] = "Currency";

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
		LJAGetSchema cloned = (LJAGetSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getActuGetNo()
	{
		return ActuGetNo;
	}
	public void setActuGetNo(String aActuGetNo)
	{
		ActuGetNo = aActuGetNo;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 1---客户号<p>
	* 2---生存领取对应的合同号<p>
	* 4---暂收退费的印刷号<p>
	* 5---理赔对应的赔案号<p>
	* 6---溢交退费对应的合同号<p>
	* 7---红利给付对应的合同号<p>
	* 9---续期回退对应的合同号<p>
	* 10--保全对应的保全受理号
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	/**
	* 1---现金<p>
	* 2---现金支票<p>
	* 3---支票<p>
	* 4---银行转帐<p>
	* 5---内部转帐<p>
	* 6---银行托收<p>
	* 7---业务员信用卡<p>
	* A---邮保通<p>
	* B---银保通
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
	* 表示该给付是否正在送银行的途中<p>
	* 对应原来的send_bank<p>
	* 0 ---表示不在送银行途中，对应原来的N<p>
	* 1 ---表示在送银行途中，对应原来的Y
	*/
	public String getBankOnTheWayFlag()
	{
		return BankOnTheWayFlag;
	}
	public void setBankOnTheWayFlag(String aBankOnTheWayFlag)
	{
		BankOnTheWayFlag = aBankOnTheWayFlag;
	}
	/**
	* 如果银行代付成功，则该标志为"1"<p>
	* 0 ---银行代付没有成功，默认值，对应旧系统的N<p>
	* 1 ---银行代付成功，对应旧系统的Y
	*/
	public String getBankSuccFlag()
	{
		return BankSuccFlag;
	}
	public void setBankSuccFlag(String aBankSuccFlag)
	{
		BankSuccFlag = aBankSuccFlag;
	}
	/**
	* 每送一次银行，该字段值加1
	*/
	public int getSendBankCount()
	{
		return SendBankCount;
	}
	public void setSendBankCount(int aSendBankCount)
	{
		SendBankCount = aSendBankCount;
	}
	public void setSendBankCount(String aSendBankCount)
	{
		if (aSendBankCount != null && !aSendBankCount.equals(""))
		{
			Integer tInteger = new Integer(aSendBankCount);
			int i = tInteger.intValue();
			SendBankCount = i;
		}
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
	/**
	* 对不同的代理机构号进行内部的分类。
	*/
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
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
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	/**
	* 该字段纪录最早可以付费的日期。<p>
	* 主要是针对催付的情况，会提前产生催付数据，但是催付产生后并不一定能送银行。
	*/
	public String getStartGetDate()
	{
		if( StartGetDate != null )
			return fDate.getString(StartGetDate);
		else
			return null;
	}
	public void setStartGetDate(Date aStartGetDate)
	{
		StartGetDate = aStartGetDate;
	}
	public void setStartGetDate(String aStartGetDate)
	{
		if (aStartGetDate != null && !aStartGetDate.equals("") )
		{
			StartGetDate = fDate.getDate( aStartGetDate );
		}
		else
			StartGetDate = null;
	}

	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public double getSumGetMoney()
	{
		return SumGetMoney;
	}
	public void setSumGetMoney(double aSumGetMoney)
	{
		SumGetMoney = aSumGetMoney;
	}
	public void setSumGetMoney(String aSumGetMoney)
	{
		if (aSumGetMoney != null && !aSumGetMoney.equals(""))
		{
			Double tDouble = new Double(aSumGetMoney);
			double d = tDouble.doubleValue();
			SumGetMoney = d;
		}
	}

	/**
	* 1-个人,2-团体,3-银代
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getShouldDate()
	{
		if( ShouldDate != null )
			return fDate.getString(ShouldDate);
		else
			return null;
	}
	public void setShouldDate(Date aShouldDate)
	{
		ShouldDate = aShouldDate;
	}
	public void setShouldDate(String aShouldDate)
	{
		if (aShouldDate != null && !aShouldDate.equals("") )
		{
			ShouldDate = fDate.getDate( aShouldDate );
		}
		else
			ShouldDate = null;
	}

	public String getEnterAccDate()
	{
		if( EnterAccDate != null )
			return fDate.getString(EnterAccDate);
		else
			return null;
	}
	public void setEnterAccDate(Date aEnterAccDate)
	{
		EnterAccDate = aEnterAccDate;
	}
	public void setEnterAccDate(String aEnterAccDate)
	{
		if (aEnterAccDate != null && !aEnterAccDate.equals("") )
		{
			EnterAccDate = fDate.getDate( aEnterAccDate );
		}
		else
			EnterAccDate = null;
	}

	public String getConfDate()
	{
		if( ConfDate != null )
			return fDate.getString(ConfDate);
		else
			return null;
	}
	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}
	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
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

	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
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
	public String getDrawer()
	{
		return Drawer;
	}
	public void setDrawer(String aDrawer)
	{
		Drawer = aDrawer;
	}
	public String getDrawerID()
	{
		return DrawerID;
	}
	public void setDrawerID(String aDrawerID)
	{
		DrawerID = aDrawerID;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
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
	public String getActualDrawer()
	{
		return ActualDrawer;
	}
	public void setActualDrawer(String aActualDrawer)
	{
		ActualDrawer = aActualDrawer;
	}
	public String getActualDrawerID()
	{
		return ActualDrawerID;
	}
	public void setActualDrawerID(String aActualDrawerID)
	{
		ActualDrawerID = aActualDrawerID;
	}
	public String getPolicyCom()
	{
		return PolicyCom;
	}
	public void setPolicyCom(String aPolicyCom)
	{
		PolicyCom = aPolicyCom;
	}
	/**
	* 标识业务上不能用的更新、删除的备份数据 operstate='1'，正常数据就是0，或为空
	*/
	public String getOperState()
	{
		return OperState;
	}
	public void setOperState(String aOperState)
	{
		OperState = aOperState;
	}
	public String getBILLPAYREASON()
	{
		return BILLPAYREASON;
	}
	public void setBILLPAYREASON(String aBILLPAYREASON)
	{
		BILLPAYREASON = aBILLPAYREASON;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
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
	public String getDrawerType()
	{
		return DrawerType;
	}
	public void setDrawerType(String aDrawerType)
	{
		DrawerType = aDrawerType;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	/**
	* 00-对公，01-对私
	*/
	public String getGetObj()
	{
		return GetObj;
	}
	public void setGetObj(String aGetObj)
	{
		GetObj = aGetObj;
	}
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		GetMode = aGetMode;
	}
	/**
	* 光大主：收（ljspay：00，空；ljsget：10，从公司）；付（ljsget：00，空；ljspay：10，从公司）；<p>
	* 光大从：收（ljspay：11，主公司）；付（ljsget：11，主公司）
	*/
	public String getGuaranteeFlag()
	{
		return GuaranteeFlag;
	}
	public void setGuaranteeFlag(String aGuaranteeFlag)
	{
		GuaranteeFlag = aGuaranteeFlag;
	}
	public String getInsurancecom()
	{
		return Insurancecom;
	}
	public void setInsurancecom(String aInsurancecom)
	{
		Insurancecom = aInsurancecom;
	}
	/**
	* 0-未发盘，1-发盘在途，2-已支付成功，3-支付失败，4-大额待审批，5-大额审批在途，6-大额审批失败，7-修改银行信息，8-转营业外申请，9-营业外申请通过
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getConfirmOperator()
	{
		return ConfirmOperator;
	}
	public void setConfirmOperator(String aConfirmOperator)
	{
		ConfirmOperator = aConfirmOperator;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	public String getConfirmTime()
	{
		return ConfirmTime;
	}
	public void setConfirmTime(String aConfirmTime)
	{
		ConfirmTime = aConfirmTime;
	}
	public String getConfirmConclusion()
	{
		return ConfirmConclusion;
	}
	public void setConfirmConclusion(String aConfirmConclusion)
	{
		ConfirmConclusion = aConfirmConclusion;
	}
	public String getConfirmDesc()
	{
		return ConfirmDesc;
	}
	public void setConfirmDesc(String aConfirmDesc)
	{
		ConfirmDesc = aConfirmDesc;
	}
	/**
	* 0-即时结算，1-定期结算，2-定期结算直接付费，3-非定期结算退回水溢交，4-定期结算退回溢交
	*/
	public String getBalanceOnTime()
	{
		return BalanceOnTime;
	}
	public void setBalanceOnTime(String aBalanceOnTime)
	{
		BalanceOnTime = aBalanceOnTime;
	}
	public String getBalanceAllNo()
	{
		return BalanceAllNo;
	}
	public void setBalanceAllNo(String aBalanceAllNo)
	{
		BalanceAllNo = aBalanceAllNo;
	}
	/**
	* 定期结算业务关联时标记
	*/
	public String getBalanceRelaNo()
	{
		return BalanceRelaNo;
	}
	public void setBalanceRelaNo(String aBalanceRelaNo)
	{
		BalanceRelaNo = aBalanceRelaNo;
	}
	public String getBigRelaNo()
	{
		return BigRelaNo;
	}
	public void setBigRelaNo(String aBigRelaNo)
	{
		BigRelaNo = aBigRelaNo;
	}
	public String getOutBankCode()
	{
		return OutBankCode;
	}
	public void setOutBankCode(String aOutBankCode)
	{
		OutBankCode = aOutBankCode;
	}
	public String getOutBankAccNo()
	{
		return OutBankAccNo;
	}
	public void setOutBankAccNo(String aOutBankAccNo)
	{
		OutBankAccNo = aOutBankAccNo;
	}
	public String getSendRelaNo()
	{
		return SendRelaNo;
	}
	public void setSendRelaNo(String aSendRelaNo)
	{
		SendRelaNo = aSendRelaNo;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		ModifyOperator = aModifyOperator;
	}
	public double getNetAmount()
	{
		return NetAmount;
	}
	public void setNetAmount(double aNetAmount)
	{
		NetAmount = aNetAmount;
	}
	public void setNetAmount(String aNetAmount)
	{
		if (aNetAmount != null && !aNetAmount.equals(""))
		{
			Double tDouble = new Double(aNetAmount);
			double d = tDouble.doubleValue();
			NetAmount = d;
		}
	}

	public double getTaxAmount()
	{
		return TaxAmount;
	}
	public void setTaxAmount(double aTaxAmount)
	{
		TaxAmount = aTaxAmount;
	}
	public void setTaxAmount(String aTaxAmount)
	{
		if (aTaxAmount != null && !aTaxAmount.equals(""))
		{
			Double tDouble = new Double(aTaxAmount);
			double d = tDouble.doubleValue();
			TaxAmount = d;
		}
	}

	public double getTax()
	{
		return Tax;
	}
	public void setTax(double aTax)
	{
		Tax = aTax;
	}
	public void setTax(String aTax)
	{
		if (aTax != null && !aTax.equals(""))
		{
			Double tDouble = new Double(aTax);
			double d = tDouble.doubleValue();
			Tax = d;
		}
	}


	/**
	* 使用另外一个 LJAGetSchema 对象给 Schema 赋值
	* @param: aLJAGetSchema LJAGetSchema
	**/
	public void setSchema(LJAGetSchema aLJAGetSchema)
	{
		this.ActuGetNo = aLJAGetSchema.getActuGetNo();
		this.OtherNo = aLJAGetSchema.getOtherNo();
		this.OtherNoType = aLJAGetSchema.getOtherNoType();
		this.PayMode = aLJAGetSchema.getPayMode();
		this.BankOnTheWayFlag = aLJAGetSchema.getBankOnTheWayFlag();
		this.BankSuccFlag = aLJAGetSchema.getBankSuccFlag();
		this.SendBankCount = aLJAGetSchema.getSendBankCount();
		this.ManageCom = aLJAGetSchema.getManageCom();
		this.AgentCom = aLJAGetSchema.getAgentCom();
		this.AgentType = aLJAGetSchema.getAgentType();
		this.AgentCode = aLJAGetSchema.getAgentCode();
		this.AgentGroup = aLJAGetSchema.getAgentGroup();
		this.AccName = aLJAGetSchema.getAccName();
		this.StartGetDate = fDate.getDate( aLJAGetSchema.getStartGetDate());
		this.AppntNo = aLJAGetSchema.getAppntNo();
		this.SumGetMoney = aLJAGetSchema.getSumGetMoney();
		this.SaleChnl = aLJAGetSchema.getSaleChnl();
		this.ShouldDate = fDate.getDate( aLJAGetSchema.getShouldDate());
		this.EnterAccDate = fDate.getDate( aLJAGetSchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLJAGetSchema.getConfDate());
		this.ApproveCode = aLJAGetSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLJAGetSchema.getApproveDate());
		this.GetNoticeNo = aLJAGetSchema.getGetNoticeNo();
		this.BankCode = aLJAGetSchema.getBankCode();
		this.BankAccNo = aLJAGetSchema.getBankAccNo();
		this.Drawer = aLJAGetSchema.getDrawer();
		this.DrawerID = aLJAGetSchema.getDrawerID();
		this.SerialNo = aLJAGetSchema.getSerialNo();
		this.Operator = aLJAGetSchema.getOperator();
		this.MakeDate = fDate.getDate( aLJAGetSchema.getMakeDate());
		this.MakeTime = aLJAGetSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJAGetSchema.getModifyDate());
		this.ModifyTime = aLJAGetSchema.getModifyTime();
		this.ActualDrawer = aLJAGetSchema.getActualDrawer();
		this.ActualDrawerID = aLJAGetSchema.getActualDrawerID();
		this.PolicyCom = aLJAGetSchema.getPolicyCom();
		this.OperState = aLJAGetSchema.getOperState();
		this.BILLPAYREASON = aLJAGetSchema.getBILLPAYREASON();
		this.InsuredNo = aLJAGetSchema.getInsuredNo();
		this.DrawerType = aLJAGetSchema.getDrawerType();
		this.Currency = aLJAGetSchema.getCurrency();
		this.GrpContNo = aLJAGetSchema.getGrpContNo();
		this.GetObj = aLJAGetSchema.getGetObj();
		this.GetMode = aLJAGetSchema.getGetMode();
		this.GuaranteeFlag = aLJAGetSchema.getGuaranteeFlag();
		this.Insurancecom = aLJAGetSchema.getInsurancecom();
		this.State = aLJAGetSchema.getState();
		this.ConfirmOperator = aLJAGetSchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJAGetSchema.getConfirmDate());
		this.ConfirmTime = aLJAGetSchema.getConfirmTime();
		this.ConfirmConclusion = aLJAGetSchema.getConfirmConclusion();
		this.ConfirmDesc = aLJAGetSchema.getConfirmDesc();
		this.BalanceOnTime = aLJAGetSchema.getBalanceOnTime();
		this.BalanceAllNo = aLJAGetSchema.getBalanceAllNo();
		this.BalanceRelaNo = aLJAGetSchema.getBalanceRelaNo();
		this.BigRelaNo = aLJAGetSchema.getBigRelaNo();
		this.OutBankCode = aLJAGetSchema.getOutBankCode();
		this.OutBankAccNo = aLJAGetSchema.getOutBankAccNo();
		this.SendRelaNo = aLJAGetSchema.getSendRelaNo();
		this.ComCode = aLJAGetSchema.getComCode();
		this.ModifyOperator = aLJAGetSchema.getModifyOperator();
		this.NetAmount = aLJAGetSchema.getNetAmount();
		this.TaxAmount = aLJAGetSchema.getTaxAmount();
		this.Tax = aLJAGetSchema.getTax();
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
			if( rs.getString("ActuGetNo") == null )
				this.ActuGetNo = null;
			else
				this.ActuGetNo = rs.getString("ActuGetNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("BankOnTheWayFlag") == null )
				this.BankOnTheWayFlag = null;
			else
				this.BankOnTheWayFlag = rs.getString("BankOnTheWayFlag").trim();

			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			this.SendBankCount = rs.getInt("SendBankCount");
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

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.StartGetDate = rs.getDate("StartGetDate");
			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			this.SumGetMoney = rs.getDouble("SumGetMoney");
			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			this.ShouldDate = rs.getDate("ShouldDate");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("Drawer") == null )
				this.Drawer = null;
			else
				this.Drawer = rs.getString("Drawer").trim();

			if( rs.getString("DrawerID") == null )
				this.DrawerID = null;
			else
				this.DrawerID = rs.getString("DrawerID").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

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

			if( rs.getString("ActualDrawer") == null )
				this.ActualDrawer = null;
			else
				this.ActualDrawer = rs.getString("ActualDrawer").trim();

			if( rs.getString("ActualDrawerID") == null )
				this.ActualDrawerID = null;
			else
				this.ActualDrawerID = rs.getString("ActualDrawerID").trim();

			if( rs.getString("PolicyCom") == null )
				this.PolicyCom = null;
			else
				this.PolicyCom = rs.getString("PolicyCom").trim();

			if( rs.getString("OperState") == null )
				this.OperState = null;
			else
				this.OperState = rs.getString("OperState").trim();

			if( rs.getString("BILLPAYREASON") == null )
				this.BILLPAYREASON = null;
			else
				this.BILLPAYREASON = rs.getString("BILLPAYREASON").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("DrawerType") == null )
				this.DrawerType = null;
			else
				this.DrawerType = rs.getString("DrawerType").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GetObj") == null )
				this.GetObj = null;
			else
				this.GetObj = rs.getString("GetObj").trim();

			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			if( rs.getString("GuaranteeFlag") == null )
				this.GuaranteeFlag = null;
			else
				this.GuaranteeFlag = rs.getString("GuaranteeFlag").trim();

			if( rs.getString("Insurancecom") == null )
				this.Insurancecom = null;
			else
				this.Insurancecom = rs.getString("Insurancecom").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ConfirmOperator") == null )
				this.ConfirmOperator = null;
			else
				this.ConfirmOperator = rs.getString("ConfirmOperator").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("ConfirmTime") == null )
				this.ConfirmTime = null;
			else
				this.ConfirmTime = rs.getString("ConfirmTime").trim();

			if( rs.getString("ConfirmConclusion") == null )
				this.ConfirmConclusion = null;
			else
				this.ConfirmConclusion = rs.getString("ConfirmConclusion").trim();

			if( rs.getString("ConfirmDesc") == null )
				this.ConfirmDesc = null;
			else
				this.ConfirmDesc = rs.getString("ConfirmDesc").trim();

			if( rs.getString("BalanceOnTime") == null )
				this.BalanceOnTime = null;
			else
				this.BalanceOnTime = rs.getString("BalanceOnTime").trim();

			if( rs.getString("BalanceAllNo") == null )
				this.BalanceAllNo = null;
			else
				this.BalanceAllNo = rs.getString("BalanceAllNo").trim();

			if( rs.getString("BalanceRelaNo") == null )
				this.BalanceRelaNo = null;
			else
				this.BalanceRelaNo = rs.getString("BalanceRelaNo").trim();

			if( rs.getString("BigRelaNo") == null )
				this.BigRelaNo = null;
			else
				this.BigRelaNo = rs.getString("BigRelaNo").trim();

			if( rs.getString("OutBankCode") == null )
				this.OutBankCode = null;
			else
				this.OutBankCode = rs.getString("OutBankCode").trim();

			if( rs.getString("OutBankAccNo") == null )
				this.OutBankAccNo = null;
			else
				this.OutBankAccNo = rs.getString("OutBankAccNo").trim();

			if( rs.getString("SendRelaNo") == null )
				this.SendRelaNo = null;
			else
				this.SendRelaNo = rs.getString("SendRelaNo").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.NetAmount = rs.getDouble("NetAmount");
			this.TaxAmount = rs.getDouble("TaxAmount");
			this.Tax = rs.getDouble("Tax");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJAGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJAGetSchema getSchema()
	{
		LJAGetSchema aLJAGetSchema = new LJAGetSchema();
		aLJAGetSchema.setSchema(this);
		return aLJAGetSchema;
	}

	public LJAGetDB getDB()
	{
		LJAGetDB aDBOper = new LJAGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJAGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ActuGetNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankOnTheWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SendBankCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ShouldDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Drawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActualDrawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActualDrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BILLPAYREASON)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrawerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GuaranteeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Insurancecom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceOnTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BigRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJAGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ActuGetNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BankOnTheWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SendBankCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StartGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			SumGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ShouldDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Drawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			DrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ActualDrawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ActualDrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			PolicyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			OperState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			BILLPAYREASON = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			DrawerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			GetObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			GuaranteeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			Insurancecom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49,SysConst.PACKAGESPILTER));
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			BalanceOnTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			BalanceAllNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			BalanceRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			BigRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			OutBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			OutBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			SendRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,64,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAGetSchema";
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
		if (FCode.equalsIgnoreCase("ActuGetNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActuGetNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("BankOnTheWayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankOnTheWayFlag));
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccFlag));
		}
		if (FCode.equalsIgnoreCase("SendBankCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendBankCount));
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
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("StartGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartGetDate()));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("SumGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumGetMoney));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Drawer));
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrawerID));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
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
		if (FCode.equalsIgnoreCase("ActualDrawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualDrawer));
		}
		if (FCode.equalsIgnoreCase("ActualDrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualDrawerID));
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCom));
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperState));
		}
		if (FCode.equalsIgnoreCase("BILLPAYREASON"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BILLPAYREASON));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("DrawerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrawerType));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GetObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetObj));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("GuaranteeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GuaranteeFlag));
		}
		if (FCode.equalsIgnoreCase("Insurancecom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Insurancecom));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmOperator));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmConclusion));
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmDesc));
		}
		if (FCode.equalsIgnoreCase("BalanceOnTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceOnTime));
		}
		if (FCode.equalsIgnoreCase("BalanceAllNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllNo));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaNo));
		}
		if (FCode.equalsIgnoreCase("BigRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BigRelaNo));
		}
		if (FCode.equalsIgnoreCase("OutBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutBankCode));
		}
		if (FCode.equalsIgnoreCase("OutBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutBankAccNo));
		}
		if (FCode.equalsIgnoreCase("SendRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendRelaNo));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("NetAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NetAmount));
		}
		if (FCode.equalsIgnoreCase("TaxAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaxAmount));
		}
		if (FCode.equalsIgnoreCase("Tax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Tax));
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
				strFieldValue = StrTool.GBKToUnicode(ActuGetNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BankOnTheWayFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 6:
				strFieldValue = String.valueOf(SendBankCount);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartGetDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 15:
				strFieldValue = String.valueOf(SumGetMoney);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Drawer);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(DrawerID);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ActualDrawer);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ActualDrawerID);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(PolicyCom);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(OperState);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(BILLPAYREASON);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(DrawerType);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(GetObj);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(GuaranteeFlag);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(Insurancecom);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(BalanceOnTime);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllNo);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaNo);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(BigRelaNo);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(OutBankCode);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(OutBankAccNo);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(SendRelaNo);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 61:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 62:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 63:
				strFieldValue = String.valueOf(Tax);
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

		if (FCode.equalsIgnoreCase("ActuGetNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActuGetNo = FValue.trim();
			}
			else
				ActuGetNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
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
		if (FCode.equalsIgnoreCase("BankOnTheWayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankOnTheWayFlag = FValue.trim();
			}
			else
				BankOnTheWayFlag = null;
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankSuccFlag = FValue.trim();
			}
			else
				BankSuccFlag = null;
		}
		if (FCode.equalsIgnoreCase("SendBankCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SendBankCount = i;
			}
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
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("StartGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartGetDate = fDate.getDate( FValue );
			}
			else
				StartGetDate = null;
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
		if (FCode.equalsIgnoreCase("SumGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumGetMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ShouldDate = fDate.getDate( FValue );
			}
			else
				ShouldDate = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnterAccDate = fDate.getDate( FValue );
			}
			else
				EnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
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
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
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
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Drawer = FValue.trim();
			}
			else
				Drawer = null;
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrawerID = FValue.trim();
			}
			else
				DrawerID = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("ActualDrawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActualDrawer = FValue.trim();
			}
			else
				ActualDrawer = null;
		}
		if (FCode.equalsIgnoreCase("ActualDrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActualDrawerID = FValue.trim();
			}
			else
				ActualDrawerID = null;
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyCom = FValue.trim();
			}
			else
				PolicyCom = null;
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperState = FValue.trim();
			}
			else
				OperState = null;
		}
		if (FCode.equalsIgnoreCase("BILLPAYREASON"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BILLPAYREASON = FValue.trim();
			}
			else
				BILLPAYREASON = null;
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
		if (FCode.equalsIgnoreCase("DrawerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrawerType = FValue.trim();
			}
			else
				DrawerType = null;
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GetObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetObj = FValue.trim();
			}
			else
				GetObj = null;
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetMode = FValue.trim();
			}
			else
				GetMode = null;
		}
		if (FCode.equalsIgnoreCase("GuaranteeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GuaranteeFlag = FValue.trim();
			}
			else
				GuaranteeFlag = null;
		}
		if (FCode.equalsIgnoreCase("Insurancecom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Insurancecom = FValue.trim();
			}
			else
				Insurancecom = null;
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
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmOperator = FValue.trim();
			}
			else
				ConfirmOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmTime = FValue.trim();
			}
			else
				ConfirmTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmConclusion = FValue.trim();
			}
			else
				ConfirmConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmDesc = FValue.trim();
			}
			else
				ConfirmDesc = null;
		}
		if (FCode.equalsIgnoreCase("BalanceOnTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceOnTime = FValue.trim();
			}
			else
				BalanceOnTime = null;
		}
		if (FCode.equalsIgnoreCase("BalanceAllNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAllNo = FValue.trim();
			}
			else
				BalanceAllNo = null;
		}
		if (FCode.equalsIgnoreCase("BalanceRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaNo = FValue.trim();
			}
			else
				BalanceRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("BigRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BigRelaNo = FValue.trim();
			}
			else
				BigRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("OutBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutBankCode = FValue.trim();
			}
			else
				OutBankCode = null;
		}
		if (FCode.equalsIgnoreCase("OutBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutBankAccNo = FValue.trim();
			}
			else
				OutBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("SendRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendRelaNo = FValue.trim();
			}
			else
				SendRelaNo = null;
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
		if (FCode.equalsIgnoreCase("NetAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				NetAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("TaxAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TaxAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("Tax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Tax = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJAGetSchema other = (LJAGetSchema)otherObject;
		return
			ActuGetNo.equals(other.getActuGetNo())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& PayMode.equals(other.getPayMode())
			&& BankOnTheWayFlag.equals(other.getBankOnTheWayFlag())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& SendBankCount == other.getSendBankCount()
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AccName.equals(other.getAccName())
			&& fDate.getString(StartGetDate).equals(other.getStartGetDate())
			&& AppntNo.equals(other.getAppntNo())
			&& SumGetMoney == other.getSumGetMoney()
			&& SaleChnl.equals(other.getSaleChnl())
			&& fDate.getString(ShouldDate).equals(other.getShouldDate())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& Drawer.equals(other.getDrawer())
			&& DrawerID.equals(other.getDrawerID())
			&& SerialNo.equals(other.getSerialNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ActualDrawer.equals(other.getActualDrawer())
			&& ActualDrawerID.equals(other.getActualDrawerID())
			&& PolicyCom.equals(other.getPolicyCom())
			&& OperState.equals(other.getOperState())
			&& BILLPAYREASON.equals(other.getBILLPAYREASON())
			&& InsuredNo.equals(other.getInsuredNo())
			&& DrawerType.equals(other.getDrawerType())
			&& Currency.equals(other.getCurrency())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GetObj.equals(other.getGetObj())
			&& GetMode.equals(other.getGetMode())
			&& GuaranteeFlag.equals(other.getGuaranteeFlag())
			&& Insurancecom.equals(other.getInsurancecom())
			&& State.equals(other.getState())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmDesc.equals(other.getConfirmDesc())
			&& BalanceOnTime.equals(other.getBalanceOnTime())
			&& BalanceAllNo.equals(other.getBalanceAllNo())
			&& BalanceRelaNo.equals(other.getBalanceRelaNo())
			&& BigRelaNo.equals(other.getBigRelaNo())
			&& OutBankCode.equals(other.getOutBankCode())
			&& OutBankAccNo.equals(other.getOutBankAccNo())
			&& SendRelaNo.equals(other.getSendRelaNo())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& NetAmount == other.getNetAmount()
			&& TaxAmount == other.getTaxAmount()
			&& Tax == other.getTax();
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
		if( strFieldName.equals("ActuGetNo") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 2;
		}
		if( strFieldName.equals("PayMode") ) {
			return 3;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return 4;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 5;
		}
		if( strFieldName.equals("SendBankCount") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 8;
		}
		if( strFieldName.equals("AgentType") ) {
			return 9;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 10;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 11;
		}
		if( strFieldName.equals("AccName") ) {
			return 12;
		}
		if( strFieldName.equals("StartGetDate") ) {
			return 13;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 14;
		}
		if( strFieldName.equals("SumGetMoney") ) {
			return 15;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 16;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return 17;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 18;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 19;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 20;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 21;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 22;
		}
		if( strFieldName.equals("BankCode") ) {
			return 23;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 24;
		}
		if( strFieldName.equals("Drawer") ) {
			return 25;
		}
		if( strFieldName.equals("DrawerID") ) {
			return 26;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 27;
		}
		if( strFieldName.equals("Operator") ) {
			return 28;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 29;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 32;
		}
		if( strFieldName.equals("ActualDrawer") ) {
			return 33;
		}
		if( strFieldName.equals("ActualDrawerID") ) {
			return 34;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return 35;
		}
		if( strFieldName.equals("OperState") ) {
			return 36;
		}
		if( strFieldName.equals("BILLPAYREASON") ) {
			return 37;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 38;
		}
		if( strFieldName.equals("DrawerType") ) {
			return 39;
		}
		if( strFieldName.equals("Currency") ) {
			return 40;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 41;
		}
		if( strFieldName.equals("GetObj") ) {
			return 42;
		}
		if( strFieldName.equals("GetMode") ) {
			return 43;
		}
		if( strFieldName.equals("GuaranteeFlag") ) {
			return 44;
		}
		if( strFieldName.equals("Insurancecom") ) {
			return 45;
		}
		if( strFieldName.equals("State") ) {
			return 46;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 47;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 48;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 49;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 50;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 51;
		}
		if( strFieldName.equals("BalanceOnTime") ) {
			return 52;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return 53;
		}
		if( strFieldName.equals("BalanceRelaNo") ) {
			return 54;
		}
		if( strFieldName.equals("BigRelaNo") ) {
			return 55;
		}
		if( strFieldName.equals("OutBankCode") ) {
			return 56;
		}
		if( strFieldName.equals("OutBankAccNo") ) {
			return 57;
		}
		if( strFieldName.equals("SendRelaNo") ) {
			return 58;
		}
		if( strFieldName.equals("ComCode") ) {
			return 59;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 60;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 61;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 62;
		}
		if( strFieldName.equals("Tax") ) {
			return 63;
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
				strFieldName = "ActuGetNo";
				break;
			case 1:
				strFieldName = "OtherNo";
				break;
			case 2:
				strFieldName = "OtherNoType";
				break;
			case 3:
				strFieldName = "PayMode";
				break;
			case 4:
				strFieldName = "BankOnTheWayFlag";
				break;
			case 5:
				strFieldName = "BankSuccFlag";
				break;
			case 6:
				strFieldName = "SendBankCount";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "AgentCom";
				break;
			case 9:
				strFieldName = "AgentType";
				break;
			case 10:
				strFieldName = "AgentCode";
				break;
			case 11:
				strFieldName = "AgentGroup";
				break;
			case 12:
				strFieldName = "AccName";
				break;
			case 13:
				strFieldName = "StartGetDate";
				break;
			case 14:
				strFieldName = "AppntNo";
				break;
			case 15:
				strFieldName = "SumGetMoney";
				break;
			case 16:
				strFieldName = "SaleChnl";
				break;
			case 17:
				strFieldName = "ShouldDate";
				break;
			case 18:
				strFieldName = "EnterAccDate";
				break;
			case 19:
				strFieldName = "ConfDate";
				break;
			case 20:
				strFieldName = "ApproveCode";
				break;
			case 21:
				strFieldName = "ApproveDate";
				break;
			case 22:
				strFieldName = "GetNoticeNo";
				break;
			case 23:
				strFieldName = "BankCode";
				break;
			case 24:
				strFieldName = "BankAccNo";
				break;
			case 25:
				strFieldName = "Drawer";
				break;
			case 26:
				strFieldName = "DrawerID";
				break;
			case 27:
				strFieldName = "SerialNo";
				break;
			case 28:
				strFieldName = "Operator";
				break;
			case 29:
				strFieldName = "MakeDate";
				break;
			case 30:
				strFieldName = "MakeTime";
				break;
			case 31:
				strFieldName = "ModifyDate";
				break;
			case 32:
				strFieldName = "ModifyTime";
				break;
			case 33:
				strFieldName = "ActualDrawer";
				break;
			case 34:
				strFieldName = "ActualDrawerID";
				break;
			case 35:
				strFieldName = "PolicyCom";
				break;
			case 36:
				strFieldName = "OperState";
				break;
			case 37:
				strFieldName = "BILLPAYREASON";
				break;
			case 38:
				strFieldName = "InsuredNo";
				break;
			case 39:
				strFieldName = "DrawerType";
				break;
			case 40:
				strFieldName = "Currency";
				break;
			case 41:
				strFieldName = "GrpContNo";
				break;
			case 42:
				strFieldName = "GetObj";
				break;
			case 43:
				strFieldName = "GetMode";
				break;
			case 44:
				strFieldName = "GuaranteeFlag";
				break;
			case 45:
				strFieldName = "Insurancecom";
				break;
			case 46:
				strFieldName = "State";
				break;
			case 47:
				strFieldName = "ConfirmOperator";
				break;
			case 48:
				strFieldName = "ConfirmDate";
				break;
			case 49:
				strFieldName = "ConfirmTime";
				break;
			case 50:
				strFieldName = "ConfirmConclusion";
				break;
			case 51:
				strFieldName = "ConfirmDesc";
				break;
			case 52:
				strFieldName = "BalanceOnTime";
				break;
			case 53:
				strFieldName = "BalanceAllNo";
				break;
			case 54:
				strFieldName = "BalanceRelaNo";
				break;
			case 55:
				strFieldName = "BigRelaNo";
				break;
			case 56:
				strFieldName = "OutBankCode";
				break;
			case 57:
				strFieldName = "OutBankAccNo";
				break;
			case 58:
				strFieldName = "SendRelaNo";
				break;
			case 59:
				strFieldName = "ComCode";
				break;
			case 60:
				strFieldName = "ModifyOperator";
				break;
			case 61:
				strFieldName = "NetAmount";
				break;
			case 62:
				strFieldName = "TaxAmount";
				break;
			case 63:
				strFieldName = "Tax";
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
		if( strFieldName.equals("ActuGetNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendBankCount") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Drawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrawerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
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
		if( strFieldName.equals("ActualDrawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActualDrawerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BILLPAYREASON") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrawerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GuaranteeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Insurancecom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceOnTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BigRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NetAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Tax") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 62:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 63:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
