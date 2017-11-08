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
import com.sinosoft.lis.db.LJSPayPersonDB;

/*
 * <p>ClassName: LJSPayPersonSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJSPayPersonSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJSPayPersonSchema.class);
	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 第几次交费 */
	private int PayCount;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
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
	/** 投保人客户号码 */
	private String AppntNo;
	/** 通知书号码 */
	private String GetNoticeNo;
	/** 交费目的分类 */
	private String PayAimClass;
	/** 责任编码 */
	private String DutyCode;
	/** 交费计划编码 */
	private String PayPlanCode;
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
	/** 转入保险帐户状态 */
	private String InInsuAccState;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行在途标志 */
	private String BankOnTheWayFlag;
	/** 银行转帐成功标记 */
	private String BankSuccFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
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
	/** 主险保单年度 */
	private int MainPolYear;
	/** 保全批单号 */
	private String EdorNo;
	/** 币别 */
	private String Currency;
	/** 批单号码 */
	private String EndorsementNo;
	/** 补退费标志 */
	private String GetFlag;
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

	public static final int FIELDNUM = 49;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJSPayPersonSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "PolNo";
		pk[1] = "PayCount";
		pk[2] = "GetNoticeNo";
		pk[3] = "PayAimClass";
		pk[4] = "DutyCode";
		pk[5] = "PayPlanCode";
		pk[6] = "PayType";

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
		LJSPayPersonSchema cloned = (LJSPayPersonSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
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

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
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
	/**
	* 1 --个人交费<p>
	* 2 --集体交费
	*/
	public String getPayAimClass()
	{
		return PayAimClass;
	}
	public void setPayAimClass(String aPayAimClass)
	{
		PayAimClass = aPayAimClass;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
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

	/**
	* AA ---  加保<p>
	* HM ---  豁免<p>
	* NS ---  新增附加险<p>
	* RB ---  保全回退<p>
	* RE ---  复效<p>
	* ZC ---  正常
	*/
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

	/**
	* 0--非账户交费<p>
	* 1－－账户交费，还没有转入账户<p>
	* 2－－账户交费，已经转入账户
	*/
	public String getInInsuAccState()
	{
		return InInsuAccState;
	}
	public void setInInsuAccState(String aInInsuAccState)
	{
		InInsuAccState = aInInsuAccState;
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

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		ApproveTime = aApproveTime;
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
	public int getMainPolYear()
	{
		return MainPolYear;
	}
	public void setMainPolYear(int aMainPolYear)
	{
		MainPolYear = aMainPolYear;
	}
	public void setMainPolYear(String aMainPolYear)
	{
		if (aMainPolYear != null && !aMainPolYear.equals(""))
		{
			Integer tInteger = new Integer(aMainPolYear);
			int i = tInteger.intValue();
			MainPolYear = i;
		}
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getEndorsementNo()
	{
		return EndorsementNo;
	}
	public void setEndorsementNo(String aEndorsementNo)
	{
		EndorsementNo = aEndorsementNo;
	}
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		GetFlag = aGetFlag;
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
	* 使用另外一个 LJSPayPersonSchema 对象给 Schema 赋值
	* @param: aLJSPayPersonSchema LJSPayPersonSchema
	**/
	public void setSchema(LJSPayPersonSchema aLJSPayPersonSchema)
	{
		this.PolNo = aLJSPayPersonSchema.getPolNo();
		this.PayCount = aLJSPayPersonSchema.getPayCount();
		this.GrpContNo = aLJSPayPersonSchema.getGrpContNo();
		this.GrpPolNo = aLJSPayPersonSchema.getGrpPolNo();
		this.ContNo = aLJSPayPersonSchema.getContNo();
		this.ManageCom = aLJSPayPersonSchema.getManageCom();
		this.AgentCom = aLJSPayPersonSchema.getAgentCom();
		this.AgentType = aLJSPayPersonSchema.getAgentType();
		this.RiskCode = aLJSPayPersonSchema.getRiskCode();
		this.AgentCode = aLJSPayPersonSchema.getAgentCode();
		this.AgentGroup = aLJSPayPersonSchema.getAgentGroup();
		this.PayTypeFlag = aLJSPayPersonSchema.getPayTypeFlag();
		this.AppntNo = aLJSPayPersonSchema.getAppntNo();
		this.GetNoticeNo = aLJSPayPersonSchema.getGetNoticeNo();
		this.PayAimClass = aLJSPayPersonSchema.getPayAimClass();
		this.DutyCode = aLJSPayPersonSchema.getDutyCode();
		this.PayPlanCode = aLJSPayPersonSchema.getPayPlanCode();
		this.SumDuePayMoney = aLJSPayPersonSchema.getSumDuePayMoney();
		this.SumActuPayMoney = aLJSPayPersonSchema.getSumActuPayMoney();
		this.PayIntv = aLJSPayPersonSchema.getPayIntv();
		this.PayDate = fDate.getDate( aLJSPayPersonSchema.getPayDate());
		this.PayType = aLJSPayPersonSchema.getPayType();
		this.LastPayToDate = fDate.getDate( aLJSPayPersonSchema.getLastPayToDate());
		this.CurPayToDate = fDate.getDate( aLJSPayPersonSchema.getCurPayToDate());
		this.InInsuAccState = aLJSPayPersonSchema.getInInsuAccState();
		this.BankCode = aLJSPayPersonSchema.getBankCode();
		this.BankAccNo = aLJSPayPersonSchema.getBankAccNo();
		this.BankOnTheWayFlag = aLJSPayPersonSchema.getBankOnTheWayFlag();
		this.BankSuccFlag = aLJSPayPersonSchema.getBankSuccFlag();
		this.ApproveCode = aLJSPayPersonSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLJSPayPersonSchema.getApproveDate());
		this.ApproveTime = aLJSPayPersonSchema.getApproveTime();
		this.SerialNo = aLJSPayPersonSchema.getSerialNo();
		this.InputFlag = aLJSPayPersonSchema.getInputFlag();
		this.Operator = aLJSPayPersonSchema.getOperator();
		this.MakeDate = fDate.getDate( aLJSPayPersonSchema.getMakeDate());
		this.MakeTime = aLJSPayPersonSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJSPayPersonSchema.getModifyDate());
		this.ModifyTime = aLJSPayPersonSchema.getModifyTime();
		this.MainPolYear = aLJSPayPersonSchema.getMainPolYear();
		this.EdorNo = aLJSPayPersonSchema.getEdorNo();
		this.Currency = aLJSPayPersonSchema.getCurrency();
		this.EndorsementNo = aLJSPayPersonSchema.getEndorsementNo();
		this.GetFlag = aLJSPayPersonSchema.getGetFlag();
		this.ComCode = aLJSPayPersonSchema.getComCode();
		this.ModifyOperator = aLJSPayPersonSchema.getModifyOperator();
		this.NetAmount = aLJSPayPersonSchema.getNetAmount();
		this.TaxAmount = aLJSPayPersonSchema.getTaxAmount();
		this.Tax = aLJSPayPersonSchema.getTax();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			this.PayCount = rs.getInt("PayCount");
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

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

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("PayAimClass") == null )
				this.PayAimClass = null;
			else
				this.PayAimClass = rs.getString("PayAimClass").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

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
			if( rs.getString("InInsuAccState") == null )
				this.InInsuAccState = null;
			else
				this.InInsuAccState = rs.getString("InInsuAccState").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("BankOnTheWayFlag") == null )
				this.BankOnTheWayFlag = null;
			else
				this.BankOnTheWayFlag = rs.getString("BankOnTheWayFlag").trim();

			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

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

			this.MainPolYear = rs.getInt("MainPolYear");
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("EndorsementNo") == null )
				this.EndorsementNo = null;
			else
				this.EndorsementNo = rs.getString("EndorsementNo").trim();

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

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
			logger.debug("数据库中的LJSPayPerson表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayPersonSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJSPayPersonSchema getSchema()
	{
		LJSPayPersonSchema aLJSPayPersonSchema = new LJSPayPersonSchema();
		aLJSPayPersonSchema.setSchema(this);
		return aLJSPayPersonSchema;
	}

	public LJSPayPersonDB getDB()
	{
		LJSPayPersonDB aDBOper = new LJSPayPersonDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPayPerson描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayTypeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAimClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumDuePayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumActuPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InInsuAccState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankOnTheWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MainPolYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndorsementNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJSPayPerson>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PayTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PayAimClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			SumDuePayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			SumActuPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			PayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			LastPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			CurPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			InInsuAccState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BankOnTheWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			InputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			MainPolYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			EndorsementNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayPersonSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("PayAimClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAimClass));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
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
		if (FCode.equalsIgnoreCase("InInsuAccState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InInsuAccState));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("BankOnTheWayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankOnTheWayFlag));
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccFlag));
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
		if (FCode.equalsIgnoreCase("MainPolYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolYear));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("EndorsementNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndorsementNo));
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PayTypeFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PayAimClass);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 17:
				strFieldValue = String.valueOf(SumDuePayMoney);
				break;
			case 18:
				strFieldValue = String.valueOf(SumActuPayMoney);
				break;
			case 19:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PayType);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(InInsuAccState);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BankOnTheWayFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(InputFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 39:
				strFieldValue = String.valueOf(MainPolYear);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(EndorsementNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 46:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 47:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 48:
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
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
		if (FCode.equalsIgnoreCase("PayAimClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAimClass = FValue.trim();
			}
			else
				PayAimClass = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("InInsuAccState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InInsuAccState = FValue.trim();
			}
			else
				InInsuAccState = null;
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
		if (FCode.equalsIgnoreCase("MainPolYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MainPolYear = i;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("EndorsementNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndorsementNo = FValue.trim();
			}
			else
				EndorsementNo = null;
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
		LJSPayPersonSchema other = (LJSPayPersonSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& PayCount == other.getPayCount()
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& RiskCode.equals(other.getRiskCode())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& PayTypeFlag.equals(other.getPayTypeFlag())
			&& AppntNo.equals(other.getAppntNo())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& PayAimClass.equals(other.getPayAimClass())
			&& DutyCode.equals(other.getDutyCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& SumDuePayMoney == other.getSumDuePayMoney()
			&& SumActuPayMoney == other.getSumActuPayMoney()
			&& PayIntv == other.getPayIntv()
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& PayType.equals(other.getPayType())
			&& fDate.getString(LastPayToDate).equals(other.getLastPayToDate())
			&& fDate.getString(CurPayToDate).equals(other.getCurPayToDate())
			&& InInsuAccState.equals(other.getInInsuAccState())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& BankOnTheWayFlag.equals(other.getBankOnTheWayFlag())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& SerialNo.equals(other.getSerialNo())
			&& InputFlag.equals(other.getInputFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& MainPolYear == other.getMainPolYear()
			&& EdorNo.equals(other.getEdorNo())
			&& Currency.equals(other.getCurrency())
			&& EndorsementNo.equals(other.getEndorsementNo())
			&& GetFlag.equals(other.getGetFlag())
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("PayCount") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
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
		if( strFieldName.equals("RiskCode") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 9;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 10;
		}
		if( strFieldName.equals("PayTypeFlag") ) {
			return 11;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 12;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 13;
		}
		if( strFieldName.equals("PayAimClass") ) {
			return 14;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 15;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 16;
		}
		if( strFieldName.equals("SumDuePayMoney") ) {
			return 17;
		}
		if( strFieldName.equals("SumActuPayMoney") ) {
			return 18;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 19;
		}
		if( strFieldName.equals("PayDate") ) {
			return 20;
		}
		if( strFieldName.equals("PayType") ) {
			return 21;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return 22;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return 23;
		}
		if( strFieldName.equals("InInsuAccState") ) {
			return 24;
		}
		if( strFieldName.equals("BankCode") ) {
			return 25;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 26;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return 27;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 28;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 29;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 30;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 31;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 32;
		}
		if( strFieldName.equals("InputFlag") ) {
			return 33;
		}
		if( strFieldName.equals("Operator") ) {
			return 34;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 35;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 36;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 38;
		}
		if( strFieldName.equals("MainPolYear") ) {
			return 39;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 40;
		}
		if( strFieldName.equals("Currency") ) {
			return 41;
		}
		if( strFieldName.equals("EndorsementNo") ) {
			return 42;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 43;
		}
		if( strFieldName.equals("ComCode") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 45;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 46;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 47;
		}
		if( strFieldName.equals("Tax") ) {
			return 48;
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "PayCount";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "GrpPolNo";
				break;
			case 4:
				strFieldName = "ContNo";
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
				strFieldName = "RiskCode";
				break;
			case 9:
				strFieldName = "AgentCode";
				break;
			case 10:
				strFieldName = "AgentGroup";
				break;
			case 11:
				strFieldName = "PayTypeFlag";
				break;
			case 12:
				strFieldName = "AppntNo";
				break;
			case 13:
				strFieldName = "GetNoticeNo";
				break;
			case 14:
				strFieldName = "PayAimClass";
				break;
			case 15:
				strFieldName = "DutyCode";
				break;
			case 16:
				strFieldName = "PayPlanCode";
				break;
			case 17:
				strFieldName = "SumDuePayMoney";
				break;
			case 18:
				strFieldName = "SumActuPayMoney";
				break;
			case 19:
				strFieldName = "PayIntv";
				break;
			case 20:
				strFieldName = "PayDate";
				break;
			case 21:
				strFieldName = "PayType";
				break;
			case 22:
				strFieldName = "LastPayToDate";
				break;
			case 23:
				strFieldName = "CurPayToDate";
				break;
			case 24:
				strFieldName = "InInsuAccState";
				break;
			case 25:
				strFieldName = "BankCode";
				break;
			case 26:
				strFieldName = "BankAccNo";
				break;
			case 27:
				strFieldName = "BankOnTheWayFlag";
				break;
			case 28:
				strFieldName = "BankSuccFlag";
				break;
			case 29:
				strFieldName = "ApproveCode";
				break;
			case 30:
				strFieldName = "ApproveDate";
				break;
			case 31:
				strFieldName = "ApproveTime";
				break;
			case 32:
				strFieldName = "SerialNo";
				break;
			case 33:
				strFieldName = "InputFlag";
				break;
			case 34:
				strFieldName = "Operator";
				break;
			case 35:
				strFieldName = "MakeDate";
				break;
			case 36:
				strFieldName = "MakeTime";
				break;
			case 37:
				strFieldName = "ModifyDate";
				break;
			case 38:
				strFieldName = "ModifyTime";
				break;
			case 39:
				strFieldName = "MainPolYear";
				break;
			case 40:
				strFieldName = "EdorNo";
				break;
			case 41:
				strFieldName = "Currency";
				break;
			case 42:
				strFieldName = "EndorsementNo";
				break;
			case 43:
				strFieldName = "GetFlag";
				break;
			case 44:
				strFieldName = "ComCode";
				break;
			case 45:
				strFieldName = "ModifyOperator";
				break;
			case 46:
				strFieldName = "NetAmount";
				break;
			case 47:
				strFieldName = "TaxAmount";
				break;
			case 48:
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
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
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAimClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
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
		if( strFieldName.equals("InInsuAccState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
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
		if( strFieldName.equals("MainPolYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndorsementNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
