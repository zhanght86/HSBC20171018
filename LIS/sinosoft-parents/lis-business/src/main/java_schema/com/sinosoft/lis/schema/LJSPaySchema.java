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
import com.sinosoft.lis.db.LJSPayDB;

/*
 * <p>ClassName: LJSPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJSPaySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJSPaySchema.class);
	// @Field
	/** 通知书号码 */
	private String GetNoticeNo;
	/** 其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 总应收金额 */
	private double SumDuePayMoney;
	/** 交费日期 */
	private Date PayDate;
	/** 银行在途标志 */
	private String BankOnTheWayFlag;
	/** 银行转帐成功标记 */
	private String BankSuccFlag;
	/** 送银行次数 */
	private int SendBankCount;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
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
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 险种编码 */
	private String RiskCode;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 帐户名 */
	private String AccName;
	/** 最早收费日期 */
	private Date StartPayDate;
	/** 续保收费标记 */
	private String PayTypeFlag;
	/** 续期特殊标志 */
	private String PayForm;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 币别 */
	private String Currency;
	/** 团体保单号 */
	private String GrpContNo;
	/** 第几次交费 */
	private int PayCount;
	/** 总实交金额 */
	private double SumActuPayMoney;
	/** 交费模式 */
	private String PayMode;
	/** 交费对象 */
	private String PayObj;
	/** 交费间隔 */
	private int PayIntv;
	/** 到帐日期 */
	private Date EnterAccDate;
	/** 确认日期 */
	private Date ConfDate;
	/** 共保公司标记 */
	private String GuaranteeFlag;
	/** 共保公司编码 */
	private String Insurancecom;
	/** 保费匹配流水号 */
	private String MatchSerialNo;
	/** 状态 */
	private String State;
	/** 定期结算标志 */
	private String BalanceOnTime;
	/** 定期结算汇总号码 */
	private String BalanceAllNo;
	/** 定期结算关联号码 */
	private String BalanceRelaNo;
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

	public static final int FIELDNUM = 53;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJSPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GetNoticeNo";
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
		LJSPaySchema cloned = (LJSPaySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 如果没有通知书通知客户交费，则<p>
	* 该字段就是交费收据号。
	*/
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
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
	* 6  ---新契约个人             (投保单印刷号)<p>
	* 4  ---新契约团体             (投保单印刷号)<p>
	* 7  ---新契约银代             (投保单印刷号)<p>
	* 2  ---续期交费（渠道—个人） (保单合同号  )<p>
	* 3  ---续期交费（渠道—银代） (保单合同号  )<p>
	* 10 ---保全交费               (保全受理号  )<p>
	* 01 ---预收续期保费团体       (            )<p>
	* 02 ---预收续期保费个人       (            )<p>
	* 03 ---预收续期保费银代       (            )<p>
	* 9  ---理赔收费               (理赔号      )<p>
	* 5  ---客户预存               (客户号      )
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public double getSumDuePayMoney()
	{
		return SumDuePayMoney;
	}
	public void setSumDuePayMoney(double aSumDuePayMoney)
	{
		SumDuePayMoney = aSumDuePayMoney;
	}
	public void setSumDuePayMoney(String aSumDuePayMoney)
	{
		if (aSumDuePayMoney != null && !aSumDuePayMoney.equals(""))
		{
			Double tDouble = new Double(aSumDuePayMoney);
			double d = tDouble.doubleValue();
			SumDuePayMoney = d;
		}
	}

	/**
	* 在续期交费中，该日期表示最晚的交费日期，当超过该日期时，保单将失效。
	*/
	public String getPayDate()
	{
		if( PayDate != null )
			return fDate.getString(PayDate);
		else
			return null;
	}
	public void setPayDate(Date aPayDate)
	{
		PayDate = aPayDate;
	}
	public void setPayDate(String aPayDate)
	{
		if (aPayDate != null && !aPayDate.equals("") )
		{
			PayDate = fDate.getDate( aPayDate );
		}
		else
			PayDate = null;
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
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
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
	* 该字段纪录最早可以收费的日期。<p>
	* 主要是针对催收的情况，会提前产生催收数据，但是催收产生后并不一定能送银行。<p>
	* 还需要有一段时间将交费通知书送到客户手中，然后客户将钱存到银行。
	*/
	public String getStartPayDate()
	{
		if( StartPayDate != null )
			return fDate.getString(StartPayDate);
		else
			return null;
	}
	public void setStartPayDate(Date aStartPayDate)
	{
		StartPayDate = aStartPayDate;
	}
	public void setStartPayDate(String aStartPayDate)
	{
		if (aStartPayDate != null && !aStartPayDate.equals("") )
		{
			StartPayDate = fDate.getDate( aStartPayDate );
		}
		else
			StartPayDate = null;
	}

	/**
	* 判断是否是续保类型<p>
	* <p>
	* 1 -续保首期收费<p>
	* <p>
	* 其它则不是
	*/
	public String getPayTypeFlag()
	{
		return PayTypeFlag;
	}
	public void setPayTypeFlag(String aPayTypeFlag)
	{
		PayTypeFlag = aPayTypeFlag;
	}
	/**
	* 1—需要当天报盘的特殊续期保单
	*/
	public String getPayForm()
	{
		return PayForm;
	}
	public void setPayForm(String aPayForm)
	{
		PayForm = aPayForm;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
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
	public int getPayCount()
	{
		return PayCount;
	}
	public void setPayCount(int aPayCount)
	{
		PayCount = aPayCount;
	}
	public void setPayCount(String aPayCount)
	{
		if (aPayCount != null && !aPayCount.equals(""))
		{
			Integer tInteger = new Integer(aPayCount);
			int i = tInteger.intValue();
			PayCount = i;
		}
	}

	public double getSumActuPayMoney()
	{
		return SumActuPayMoney;
	}
	public void setSumActuPayMoney(double aSumActuPayMoney)
	{
		SumActuPayMoney = aSumActuPayMoney;
	}
	public void setSumActuPayMoney(String aSumActuPayMoney)
	{
		if (aSumActuPayMoney != null && !aSumActuPayMoney.equals(""))
		{
			Double tDouble = new Double(aSumActuPayMoney);
			double d = tDouble.doubleValue();
			SumActuPayMoney = d;
		}
	}

	/**
	* 00-匹配关联，01-代扣
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
	* 00-对公，01-对私
	*/
	public String getPayObj()
	{
		return PayObj;
	}
	public void setPayObj(String aPayObj)
	{
		PayObj = aPayObj;
	}
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
	public String getMatchSerialNo()
	{
		return MatchSerialNo;
	}
	public void setMatchSerialNo(String aMatchSerialNo)
	{
		MatchSerialNo = aMatchSerialNo;
	}
	/**
	* 0-未关联(未发盘)，1-未确认(发盘在途)，2-已确认(代收成功)，3-代收失败，4-禁用，5-禁用，6-禁用，7-修改银行信息，8-坏账申请，9-禁用
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	/**
	* 0-即时结算，1-定期结算
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
	* 使用另外一个 LJSPaySchema 对象给 Schema 赋值
	* @param: aLJSPaySchema LJSPaySchema
	**/
	public void setSchema(LJSPaySchema aLJSPaySchema)
	{
		this.GetNoticeNo = aLJSPaySchema.getGetNoticeNo();
		this.OtherNo = aLJSPaySchema.getOtherNo();
		this.OtherNoType = aLJSPaySchema.getOtherNoType();
		this.AppntNo = aLJSPaySchema.getAppntNo();
		this.SumDuePayMoney = aLJSPaySchema.getSumDuePayMoney();
		this.PayDate = fDate.getDate( aLJSPaySchema.getPayDate());
		this.BankOnTheWayFlag = aLJSPaySchema.getBankOnTheWayFlag();
		this.BankSuccFlag = aLJSPaySchema.getBankSuccFlag();
		this.SendBankCount = aLJSPaySchema.getSendBankCount();
		this.ApproveCode = aLJSPaySchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLJSPaySchema.getApproveDate());
		this.SerialNo = aLJSPaySchema.getSerialNo();
		this.Operator = aLJSPaySchema.getOperator();
		this.MakeDate = fDate.getDate( aLJSPaySchema.getMakeDate());
		this.MakeTime = aLJSPaySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJSPaySchema.getModifyDate());
		this.ModifyTime = aLJSPaySchema.getModifyTime();
		this.ManageCom = aLJSPaySchema.getManageCom();
		this.AgentCom = aLJSPaySchema.getAgentCom();
		this.AgentType = aLJSPaySchema.getAgentType();
		this.BankCode = aLJSPaySchema.getBankCode();
		this.BankAccNo = aLJSPaySchema.getBankAccNo();
		this.RiskCode = aLJSPaySchema.getRiskCode();
		this.AgentCode = aLJSPaySchema.getAgentCode();
		this.AgentGroup = aLJSPaySchema.getAgentGroup();
		this.AccName = aLJSPaySchema.getAccName();
		this.StartPayDate = fDate.getDate( aLJSPaySchema.getStartPayDate());
		this.PayTypeFlag = aLJSPaySchema.getPayTypeFlag();
		this.PayForm = aLJSPaySchema.getPayForm();
		this.IDType = aLJSPaySchema.getIDType();
		this.IDNo = aLJSPaySchema.getIDNo();
		this.Currency = aLJSPaySchema.getCurrency();
		this.GrpContNo = aLJSPaySchema.getGrpContNo();
		this.PayCount = aLJSPaySchema.getPayCount();
		this.SumActuPayMoney = aLJSPaySchema.getSumActuPayMoney();
		this.PayMode = aLJSPaySchema.getPayMode();
		this.PayObj = aLJSPaySchema.getPayObj();
		this.PayIntv = aLJSPaySchema.getPayIntv();
		this.EnterAccDate = fDate.getDate( aLJSPaySchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLJSPaySchema.getConfDate());
		this.GuaranteeFlag = aLJSPaySchema.getGuaranteeFlag();
		this.Insurancecom = aLJSPaySchema.getInsurancecom();
		this.MatchSerialNo = aLJSPaySchema.getMatchSerialNo();
		this.State = aLJSPaySchema.getState();
		this.BalanceOnTime = aLJSPaySchema.getBalanceOnTime();
		this.BalanceAllNo = aLJSPaySchema.getBalanceAllNo();
		this.BalanceRelaNo = aLJSPaySchema.getBalanceRelaNo();
		this.SendRelaNo = aLJSPaySchema.getSendRelaNo();
		this.ComCode = aLJSPaySchema.getComCode();
		this.ModifyOperator = aLJSPaySchema.getModifyOperator();
		this.NetAmount = aLJSPaySchema.getNetAmount();
		this.TaxAmount = aLJSPaySchema.getTaxAmount();
		this.Tax = aLJSPaySchema.getTax();
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
			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			this.SumDuePayMoney = rs.getDouble("SumDuePayMoney");
			this.PayDate = rs.getDate("PayDate");
			if( rs.getString("BankOnTheWayFlag") == null )
				this.BankOnTheWayFlag = null;
			else
				this.BankOnTheWayFlag = rs.getString("BankOnTheWayFlag").trim();

			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			this.SendBankCount = rs.getInt("SendBankCount");
			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
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

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

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

			this.StartPayDate = rs.getDate("StartPayDate");
			if( rs.getString("PayTypeFlag") == null )
				this.PayTypeFlag = null;
			else
				this.PayTypeFlag = rs.getString("PayTypeFlag").trim();

			if( rs.getString("PayForm") == null )
				this.PayForm = null;
			else
				this.PayForm = rs.getString("PayForm").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			this.PayCount = rs.getInt("PayCount");
			this.SumActuPayMoney = rs.getDouble("SumActuPayMoney");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("PayObj") == null )
				this.PayObj = null;
			else
				this.PayObj = rs.getString("PayObj").trim();

			this.PayIntv = rs.getInt("PayIntv");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("GuaranteeFlag") == null )
				this.GuaranteeFlag = null;
			else
				this.GuaranteeFlag = rs.getString("GuaranteeFlag").trim();

			if( rs.getString("Insurancecom") == null )
				this.Insurancecom = null;
			else
				this.Insurancecom = rs.getString("Insurancecom").trim();

			if( rs.getString("MatchSerialNo") == null )
				this.MatchSerialNo = null;
			else
				this.MatchSerialNo = rs.getString("MatchSerialNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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
			logger.debug("数据库中的LJSPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJSPaySchema getSchema()
	{
		LJSPaySchema aLJSPaySchema = new LJSPaySchema();
		aLJSPaySchema.setSchema(this);
		return aLJSPaySchema;
	}

	public LJSPayDB getDB()
	{
		LJSPayDB aDBOper = new LJSPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumDuePayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankOnTheWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SendBankCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayTypeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayForm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumActuPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GuaranteeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Insurancecom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceOnTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SumDuePayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			BankOnTheWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SendBankCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			StartPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			PayTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PayForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			SumActuPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			PayObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).intValue();
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			GuaranteeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Insurancecom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MatchSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			BalanceOnTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			BalanceAllNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			BalanceRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			SendRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPaySchema";
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
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("SumDuePayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumDuePayMoney));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
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
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
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
		if (FCode.equalsIgnoreCase("StartPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartPayDate()));
		}
		if (FCode.equalsIgnoreCase("PayTypeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayTypeFlag));
		}
		if (FCode.equalsIgnoreCase("PayForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayForm));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("SumActuPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumActuPayMoney));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("PayObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayObj));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("GuaranteeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GuaranteeFlag));
		}
		if (FCode.equalsIgnoreCase("Insurancecom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Insurancecom));
		}
		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchSerialNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 4:
				strFieldValue = String.valueOf(SumDuePayMoney);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BankOnTheWayFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(SendBankCount);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartPayDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(PayTypeFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PayForm);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 33:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 34:
				strFieldValue = String.valueOf(SumActuPayMoney);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(PayObj);
				break;
			case 37:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(GuaranteeFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Insurancecom);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(MatchSerialNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(BalanceOnTime);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllNo);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaNo);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(SendRelaNo);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 50:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 51:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 52:
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

		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("SumDuePayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumDuePayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayDate = fDate.getDate( FValue );
			}
			else
				PayDate = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("StartPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartPayDate = fDate.getDate( FValue );
			}
			else
				StartPayDate = null;
		}
		if (FCode.equalsIgnoreCase("PayTypeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayTypeFlag = FValue.trim();
			}
			else
				PayTypeFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayForm = FValue.trim();
			}
			else
				PayForm = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
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
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("SumActuPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumActuPayMoney = d;
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
		if (FCode.equalsIgnoreCase("PayObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayObj = FValue.trim();
			}
			else
				PayObj = null;
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
		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchSerialNo = FValue.trim();
			}
			else
				MatchSerialNo = null;
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
		LJSPaySchema other = (LJSPaySchema)otherObject;
		return
			GetNoticeNo.equals(other.getGetNoticeNo())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& AppntNo.equals(other.getAppntNo())
			&& SumDuePayMoney == other.getSumDuePayMoney()
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& BankOnTheWayFlag.equals(other.getBankOnTheWayFlag())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& SendBankCount == other.getSendBankCount()
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& SerialNo.equals(other.getSerialNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& RiskCode.equals(other.getRiskCode())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AccName.equals(other.getAccName())
			&& fDate.getString(StartPayDate).equals(other.getStartPayDate())
			&& PayTypeFlag.equals(other.getPayTypeFlag())
			&& PayForm.equals(other.getPayForm())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& Currency.equals(other.getCurrency())
			&& GrpContNo.equals(other.getGrpContNo())
			&& PayCount == other.getPayCount()
			&& SumActuPayMoney == other.getSumActuPayMoney()
			&& PayMode.equals(other.getPayMode())
			&& PayObj.equals(other.getPayObj())
			&& PayIntv == other.getPayIntv()
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& GuaranteeFlag.equals(other.getGuaranteeFlag())
			&& Insurancecom.equals(other.getInsurancecom())
			&& MatchSerialNo.equals(other.getMatchSerialNo())
			&& State.equals(other.getState())
			&& BalanceOnTime.equals(other.getBalanceOnTime())
			&& BalanceAllNo.equals(other.getBalanceAllNo())
			&& BalanceRelaNo.equals(other.getBalanceRelaNo())
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 2;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 3;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return 4;
		}
		if( strFieldName.equals("PayDate") ) {
			return 5;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return 6;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 7;
		}
		if( strFieldName.equals("SendBankCount") ) {
			return 8;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 9;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 10;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 11;
		}
		if( strFieldName.equals("Operator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 17;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 18;
		}
		if( strFieldName.equals("AgentType") ) {
			return 19;
		}
		if( strFieldName.equals("BankCode") ) {
			return 20;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 21;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 22;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 23;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 24;
		}
		if( strFieldName.equals("AccName") ) {
			return 25;
		}
		if( strFieldName.equals("StartPayDate") ) {
			return 26;
		}
		if( strFieldName.equals("PayTypeFlag") ) {
			return 27;
		}
		if( strFieldName.equals("PayForm") ) {
			return 28;
		}
		if( strFieldName.equals("IDType") ) {
			return 29;
		}
		if( strFieldName.equals("IDNo") ) {
			return 30;
		}
		if( strFieldName.equals("Currency") ) {
			return 31;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 32;
		}
		if( strFieldName.equals("PayCount") ) {
			return 33;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return 34;
		}
		if( strFieldName.equals("PayMode") ) {
			return 35;
		}
		if( strFieldName.equals("PayObj") ) {
			return 36;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 37;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 38;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 39;
		}
		if( strFieldName.equals("GuaranteeFlag") ) {
			return 40;
		}
		if( strFieldName.equals("Insurancecom") ) {
			return 41;
		}
		if( strFieldName.equals("MatchSerialNo") ) {
			return 42;
		}
		if( strFieldName.equals("State") ) {
			return 43;
		}
		if( strFieldName.equals("BalanceOnTime") ) {
			return 44;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return 45;
		}
		if( strFieldName.equals("BalanceRelaNo") ) {
			return 46;
		}
		if( strFieldName.equals("SendRelaNo") ) {
			return 47;
		}
		if( strFieldName.equals("ComCode") ) {
			return 48;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 49;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 50;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 51;
		}
		if( strFieldName.equals("Tax") ) {
			return 52;
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
				strFieldName = "GetNoticeNo";
				break;
			case 1:
				strFieldName = "OtherNo";
				break;
			case 2:
				strFieldName = "OtherNoType";
				break;
			case 3:
				strFieldName = "AppntNo";
				break;
			case 4:
				strFieldName = "SumDuePayMoney";
				break;
			case 5:
				strFieldName = "PayDate";
				break;
			case 6:
				strFieldName = "BankOnTheWayFlag";
				break;
			case 7:
				strFieldName = "BankSuccFlag";
				break;
			case 8:
				strFieldName = "SendBankCount";
				break;
			case 9:
				strFieldName = "ApproveCode";
				break;
			case 10:
				strFieldName = "ApproveDate";
				break;
			case 11:
				strFieldName = "SerialNo";
				break;
			case 12:
				strFieldName = "Operator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "ManageCom";
				break;
			case 18:
				strFieldName = "AgentCom";
				break;
			case 19:
				strFieldName = "AgentType";
				break;
			case 20:
				strFieldName = "BankCode";
				break;
			case 21:
				strFieldName = "BankAccNo";
				break;
			case 22:
				strFieldName = "RiskCode";
				break;
			case 23:
				strFieldName = "AgentCode";
				break;
			case 24:
				strFieldName = "AgentGroup";
				break;
			case 25:
				strFieldName = "AccName";
				break;
			case 26:
				strFieldName = "StartPayDate";
				break;
			case 27:
				strFieldName = "PayTypeFlag";
				break;
			case 28:
				strFieldName = "PayForm";
				break;
			case 29:
				strFieldName = "IDType";
				break;
			case 30:
				strFieldName = "IDNo";
				break;
			case 31:
				strFieldName = "Currency";
				break;
			case 32:
				strFieldName = "GrpContNo";
				break;
			case 33:
				strFieldName = "PayCount";
				break;
			case 34:
				strFieldName = "SumActuPayMoney";
				break;
			case 35:
				strFieldName = "PayMode";
				break;
			case 36:
				strFieldName = "PayObj";
				break;
			case 37:
				strFieldName = "PayIntv";
				break;
			case 38:
				strFieldName = "EnterAccDate";
				break;
			case 39:
				strFieldName = "ConfDate";
				break;
			case 40:
				strFieldName = "GuaranteeFlag";
				break;
			case 41:
				strFieldName = "Insurancecom";
				break;
			case 42:
				strFieldName = "MatchSerialNo";
				break;
			case 43:
				strFieldName = "State";
				break;
			case 44:
				strFieldName = "BalanceOnTime";
				break;
			case 45:
				strFieldName = "BalanceAllNo";
				break;
			case 46:
				strFieldName = "BalanceRelaNo";
				break;
			case 47:
				strFieldName = "SendRelaNo";
				break;
			case 48:
				strFieldName = "ComCode";
				break;
			case 49:
				strFieldName = "ModifyOperator";
				break;
			case 50:
				strFieldName = "NetAmount";
				break;
			case 51:
				strFieldName = "TaxAmount";
				break;
			case 52:
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
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
		if( strFieldName.equals("StartPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayTypeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayForm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GuaranteeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Insurancecom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MatchSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_INT;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
