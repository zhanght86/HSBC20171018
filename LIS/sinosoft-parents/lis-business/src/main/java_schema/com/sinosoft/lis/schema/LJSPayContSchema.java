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
import com.sinosoft.lis.db.LJSPayContDB;

/*
 * <p>ClassName: LJSPayContSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJSPayContSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJSPayContSchema.class);
	// @Field
	/** 总单/合同号码 */
	private String ContNo;
	/** 第几次交费 */
	private int PayCount;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 通知书号码 */
	private String GetNoticeNo;
	/** 总应交金额 */
	private double SumDuePayMoney;
	/** 总实交金额 */
	private double SumActuPayMoney;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费日期 */
	private Date PayDate;
	/** 交费类型 */
	private String PayType;
	/** 原交至日期 */
	private Date LastPayToDate;
	/** 现交至日期 */
	private Date CurPayToDate;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 流水号 */
	private String SerialNo;
	/** 录入标志 */
	private String InputFlag;
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
	/** 险种编码 */
	private String RiskCode;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 续保收费标记 */
	private String PayTypeFlag;
	/** 团体保单号 */
	private String GrpContNo;
	/** 到帐日期 */
	private Date EnterAccDate;
	/** 确认日期 */
	private Date ConfDate;
	/** 客户姓名 */
	private String CustomerName;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 银行在途标志 */
	private String BankOnTheWayFlag;
	/** 银行转帐成功标记 */
	private String BankSuccFlag;
	/** 送银行次数 */
	private int SendBankCount;
	/** 状态 */
	private String State;
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

	public static final int FIELDNUM = 46;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJSPayContSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "GetNoticeNo";

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
		LJSPayContSchema cloned = (LJSPayContSchema)super.clone();
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
	* 合同号分为：<p>
	* 个单合同号<p>
	* 集体合同号
	*/
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
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

	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
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

	public String getPayType()
	{
		return PayType;
	}
	public void setPayType(String aPayType)
	{
		PayType = aPayType;
	}
	public String getLastPayToDate()
	{
		if( LastPayToDate != null )
			return fDate.getString(LastPayToDate);
		else
			return null;
	}
	public void setLastPayToDate(Date aLastPayToDate)
	{
		LastPayToDate = aLastPayToDate;
	}
	public void setLastPayToDate(String aLastPayToDate)
	{
		if (aLastPayToDate != null && !aLastPayToDate.equals("") )
		{
			LastPayToDate = fDate.getDate( aLastPayToDate );
		}
		else
			LastPayToDate = null;
	}

	public String getCurPayToDate()
	{
		if( CurPayToDate != null )
			return fDate.getString(CurPayToDate);
		else
			return null;
	}
	public void setCurPayToDate(Date aCurPayToDate)
	{
		CurPayToDate = aCurPayToDate;
	}
	public void setCurPayToDate(String aCurPayToDate)
	{
		if (aCurPayToDate != null && !aCurPayToDate.equals("") )
		{
			CurPayToDate = fDate.getDate( aCurPayToDate );
		}
		else
			CurPayToDate = null;
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
	/**
	* 0---表示还没有录入交费信息<p>
	* 1---表示已经录入交费信息。<p>
	* 这个标志一般只有在录入集体交费信息的时候才有用。
	*/
	public String getInputFlag()
	{
		return InputFlag;
	}
	public void setInputFlag(String aInputFlag)
	{
		InputFlag = aInputFlag;
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
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
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

	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		CustomerName = aCustomerName;
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
	/**
	* 0-否，1-是
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
	* 0-未成功，1-成功
	*/
	public String getBankSuccFlag()
	{
		return BankSuccFlag;
	}
	public void setBankSuccFlag(String aBankSuccFlag)
	{
		BankSuccFlag = aBankSuccFlag;
	}
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

	/**
	* 0-未关联(未发盘)，1-未确认(发盘在途)，2-已确认(代收成功)
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	* 使用另外一个 LJSPayContSchema 对象给 Schema 赋值
	* @param: aLJSPayContSchema LJSPayContSchema
	**/
	public void setSchema(LJSPayContSchema aLJSPayContSchema)
	{
		this.ContNo = aLJSPayContSchema.getContNo();
		this.PayCount = aLJSPayContSchema.getPayCount();
		this.AppntNo = aLJSPayContSchema.getAppntNo();
		this.GetNoticeNo = aLJSPayContSchema.getGetNoticeNo();
		this.SumDuePayMoney = aLJSPayContSchema.getSumDuePayMoney();
		this.SumActuPayMoney = aLJSPayContSchema.getSumActuPayMoney();
		this.PayIntv = aLJSPayContSchema.getPayIntv();
		this.PayDate = fDate.getDate( aLJSPayContSchema.getPayDate());
		this.PayType = aLJSPayContSchema.getPayType();
		this.LastPayToDate = fDate.getDate( aLJSPayContSchema.getLastPayToDate());
		this.CurPayToDate = fDate.getDate( aLJSPayContSchema.getCurPayToDate());
		this.ApproveCode = aLJSPayContSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLJSPayContSchema.getApproveDate());
		this.SerialNo = aLJSPayContSchema.getSerialNo();
		this.InputFlag = aLJSPayContSchema.getInputFlag();
		this.Operator = aLJSPayContSchema.getOperator();
		this.MakeDate = fDate.getDate( aLJSPayContSchema.getMakeDate());
		this.MakeTime = aLJSPayContSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJSPayContSchema.getModifyDate());
		this.ModifyTime = aLJSPayContSchema.getModifyTime();
		this.ManageCom = aLJSPayContSchema.getManageCom();
		this.AgentCom = aLJSPayContSchema.getAgentCom();
		this.AgentType = aLJSPayContSchema.getAgentType();
		this.RiskCode = aLJSPayContSchema.getRiskCode();
		this.AgentCode = aLJSPayContSchema.getAgentCode();
		this.AgentGroup = aLJSPayContSchema.getAgentGroup();
		this.PayTypeFlag = aLJSPayContSchema.getPayTypeFlag();
		this.GrpContNo = aLJSPayContSchema.getGrpContNo();
		this.EnterAccDate = fDate.getDate( aLJSPayContSchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLJSPayContSchema.getConfDate());
		this.CustomerName = aLJSPayContSchema.getCustomerName();
		this.IDType = aLJSPayContSchema.getIDType();
		this.IDNo = aLJSPayContSchema.getIDNo();
		this.BankCode = aLJSPayContSchema.getBankCode();
		this.BankAccNo = aLJSPayContSchema.getBankAccNo();
		this.AccName = aLJSPayContSchema.getAccName();
		this.BankOnTheWayFlag = aLJSPayContSchema.getBankOnTheWayFlag();
		this.BankSuccFlag = aLJSPayContSchema.getBankSuccFlag();
		this.SendBankCount = aLJSPayContSchema.getSendBankCount();
		this.State = aLJSPayContSchema.getState();
		this.SendRelaNo = aLJSPayContSchema.getSendRelaNo();
		this.ComCode = aLJSPayContSchema.getComCode();
		this.ModifyOperator = aLJSPayContSchema.getModifyOperator();
		this.NetAmount = aLJSPayContSchema.getNetAmount();
		this.TaxAmount = aLJSPayContSchema.getTaxAmount();
		this.Tax = aLJSPayContSchema.getTax();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.PayCount = rs.getInt("PayCount");
			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			this.SumDuePayMoney = rs.getDouble("SumDuePayMoney");
			this.SumActuPayMoney = rs.getDouble("SumActuPayMoney");
			this.PayIntv = rs.getInt("PayIntv");
			this.PayDate = rs.getDate("PayDate");
			if( rs.getString("PayType") == null )
				this.PayType = null;
			else
				this.PayType = rs.getString("PayType").trim();

			this.LastPayToDate = rs.getDate("LastPayToDate");
			this.CurPayToDate = rs.getDate("CurPayToDate");
			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("InputFlag") == null )
				this.InputFlag = null;
			else
				this.InputFlag = rs.getString("InputFlag").trim();

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

			if( rs.getString("PayTypeFlag") == null )
				this.PayTypeFlag = null;
			else
				this.PayTypeFlag = rs.getString("PayTypeFlag").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

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

			if( rs.getString("BankOnTheWayFlag") == null )
				this.BankOnTheWayFlag = null;
			else
				this.BankOnTheWayFlag = rs.getString("BankOnTheWayFlag").trim();

			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			this.SendBankCount = rs.getInt("SendBankCount");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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
			logger.debug("数据库中的LJSPayCont表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJSPayContSchema getSchema()
	{
		LJSPayContSchema aLJSPayContSchema = new LJSPayContSchema();
		aLJSPayContSchema.setSchema(this);
		return aLJSPayContSchema;
	}

	public LJSPayContDB getDB()
	{
		LJSPayContDB aDBOper = new LJSPayContDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPayCont描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumDuePayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumActuPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayTypeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankOnTheWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SendBankCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPayCont>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SumDuePayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			SumActuPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			PayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			LastPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			CurPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			PayTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			BankOnTheWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SendBankCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).intValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			SendRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("SumDuePayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumDuePayMoney));
		}
		if (FCode.equalsIgnoreCase("SumActuPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumActuPayMoney));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("PayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayType));
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
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
		if (FCode.equalsIgnoreCase("InputFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputFlag));
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
		if (FCode.equalsIgnoreCase("PayTypeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayTypeFlag));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
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
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 4:
				strFieldValue = String.valueOf(SumDuePayMoney);
				break;
			case 5:
				strFieldValue = String.valueOf(SumActuPayMoney);
				break;
			case 6:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PayType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InputFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(PayTypeFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
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
				strFieldValue = StrTool.GBKToUnicode(BankOnTheWayFlag);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 38:
				strFieldValue = String.valueOf(SendBankCount);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(SendRelaNo);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 43:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 44:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 45:
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

		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equalsIgnoreCase("SumDuePayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumDuePayMoney = d;
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
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
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
		if (FCode.equalsIgnoreCase("PayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayType = FValue.trim();
			}
			else
				PayType = null;
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastPayToDate = fDate.getDate( FValue );
			}
			else
				LastPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurPayToDate = fDate.getDate( FValue );
			}
			else
				CurPayToDate = null;
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
		if (FCode.equalsIgnoreCase("InputFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputFlag = FValue.trim();
			}
			else
				InputFlag = null;
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
		if (FCode.equalsIgnoreCase("PayTypeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayTypeFlag = FValue.trim();
			}
			else
				PayTypeFlag = null;
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
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		LJSPayContSchema other = (LJSPayContSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& PayCount == other.getPayCount()
			&& AppntNo.equals(other.getAppntNo())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& SumDuePayMoney == other.getSumDuePayMoney()
			&& SumActuPayMoney == other.getSumActuPayMoney()
			&& PayIntv == other.getPayIntv()
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& PayType.equals(other.getPayType())
			&& fDate.getString(LastPayToDate).equals(other.getLastPayToDate())
			&& fDate.getString(CurPayToDate).equals(other.getCurPayToDate())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& SerialNo.equals(other.getSerialNo())
			&& InputFlag.equals(other.getInputFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& RiskCode.equals(other.getRiskCode())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& PayTypeFlag.equals(other.getPayTypeFlag())
			&& GrpContNo.equals(other.getGrpContNo())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& CustomerName.equals(other.getCustomerName())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& BankOnTheWayFlag.equals(other.getBankOnTheWayFlag())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& SendBankCount == other.getSendBankCount()
			&& State.equals(other.getState())
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("PayCount") ) {
			return 1;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 3;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return 4;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return 5;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 6;
		}
		if( strFieldName.equals("PayDate") ) {
			return 7;
		}
		if( strFieldName.equals("PayType") ) {
			return 8;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return 9;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return 10;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 11;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 12;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 13;
		}
		if( strFieldName.equals("InputFlag") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 20;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 21;
		}
		if( strFieldName.equals("AgentType") ) {
			return 22;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 23;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 24;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 25;
		}
		if( strFieldName.equals("PayTypeFlag") ) {
			return 26;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 27;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 28;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 29;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 30;
		}
		if( strFieldName.equals("IDType") ) {
			return 31;
		}
		if( strFieldName.equals("IDNo") ) {
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
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return 36;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 37;
		}
		if( strFieldName.equals("SendBankCount") ) {
			return 38;
		}
		if( strFieldName.equals("State") ) {
			return 39;
		}
		if( strFieldName.equals("SendRelaNo") ) {
			return 40;
		}
		if( strFieldName.equals("ComCode") ) {
			return 41;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 42;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 43;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 44;
		}
		if( strFieldName.equals("Tax") ) {
			return 45;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "PayCount";
				break;
			case 2:
				strFieldName = "AppntNo";
				break;
			case 3:
				strFieldName = "GetNoticeNo";
				break;
			case 4:
				strFieldName = "SumDuePayMoney";
				break;
			case 5:
				strFieldName = "SumActuPayMoney";
				break;
			case 6:
				strFieldName = "PayIntv";
				break;
			case 7:
				strFieldName = "PayDate";
				break;
			case 8:
				strFieldName = "PayType";
				break;
			case 9:
				strFieldName = "LastPayToDate";
				break;
			case 10:
				strFieldName = "CurPayToDate";
				break;
			case 11:
				strFieldName = "ApproveCode";
				break;
			case 12:
				strFieldName = "ApproveDate";
				break;
			case 13:
				strFieldName = "SerialNo";
				break;
			case 14:
				strFieldName = "InputFlag";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "ManageCom";
				break;
			case 21:
				strFieldName = "AgentCom";
				break;
			case 22:
				strFieldName = "AgentType";
				break;
			case 23:
				strFieldName = "RiskCode";
				break;
			case 24:
				strFieldName = "AgentCode";
				break;
			case 25:
				strFieldName = "AgentGroup";
				break;
			case 26:
				strFieldName = "PayTypeFlag";
				break;
			case 27:
				strFieldName = "GrpContNo";
				break;
			case 28:
				strFieldName = "EnterAccDate";
				break;
			case 29:
				strFieldName = "ConfDate";
				break;
			case 30:
				strFieldName = "CustomerName";
				break;
			case 31:
				strFieldName = "IDType";
				break;
			case 32:
				strFieldName = "IDNo";
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
				strFieldName = "BankOnTheWayFlag";
				break;
			case 37:
				strFieldName = "BankSuccFlag";
				break;
			case 38:
				strFieldName = "SendBankCount";
				break;
			case 39:
				strFieldName = "State";
				break;
			case 40:
				strFieldName = "SendRelaNo";
				break;
			case 41:
				strFieldName = "ComCode";
				break;
			case 42:
				strFieldName = "ModifyOperator";
				break;
			case 43:
				strFieldName = "NetAmount";
				break;
			case 44:
				strFieldName = "TaxAmount";
				break;
			case 45:
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("InputFlag") ) {
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayTypeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
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
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendBankCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 44:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 45:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
