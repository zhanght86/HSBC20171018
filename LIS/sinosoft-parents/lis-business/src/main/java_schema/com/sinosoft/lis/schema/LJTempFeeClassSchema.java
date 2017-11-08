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
import com.sinosoft.lis.db.LJTempFeeClassDB;

/*
 * <p>ClassName: LJTempFeeClassSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJTempFeeClassSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJTempFeeClassSchema.class);
	// @Field
	/** 暂交费收据号码 */
	private String TempFeeNo;
	/** 交费方式 */
	private String PayMode;
	/** 票据号 */
	private String ChequeNo;
	/** 交费金额 */
	private double PayMoney;
	/** 投保人名称 */
	private String AppntName;
	/** 交费日期 */
	private Date PayDate;
	/** 确认日期 */
	private Date ConfDate;
	/** 复核日期 */
	private Date ApproveDate;
	/** 到帐日期 */
	private Date EnterAccDate;
	/** 是否核销标志 */
	private String ConfFlag;
	/** 流水号 */
	private String SerialNo;
	/** 交费机构 */
	private String ManageCom;
	/** 管理机构 */
	private String PolicyCom;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 财务确认操作日期 */
	private Date ConfMakeDate;
	/** 财务确认操作时间 */
	private String ConfMakeTime;
	/** 支票日期 */
	private Date ChequeDate;
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
	/** 收费银行编码 */
	private String InBankCode;
	/** 收费银行帐号 */
	private String InBankAccNo;
	/** 收费银行帐户名 */
	private String InAccName;
	/** 保单所属机构 */
	private String ContCom;
	/** 对应其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 收据类型 */
	private String TempFeeNoType;
	/** 业务状态 */
	private String OperState;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 银行帐号录入值 */
	private String OriBankAccNo;
	/** 币别 */
	private String Currency;
	/** 客户开户行 */
	private String CustBankCode;
	/** 客户开户行省 */
	private String CustBankProvince;
	/** 客户开户行市 */
	private String CustBankCity;
	/** 客户开户行明细 */
	private String CustBankDetail;
	/** 投保单位编码 */
	private String AppntNo;
	/** 客户经理编码 */
	private String AgentCode;
	/** 客户经理姓名 */
	private String AgentName;
	/** 付款人名称 */
	private String DraweeName;
	/** 是否已打印 */
	private String PrintState;
	/** 文件号码 */
	private String FileNo;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 48;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJTempFeeClassSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TempFeeNo";
		pk[1] = "PayMode";
		pk[2] = "ChequeNo";
		pk[3] = "Currency";

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
		LJTempFeeClassSchema cloned = (LJTempFeeClassSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
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
	* 交费方式为"内部转帐"时，该字段存为实付号<p>
	* <p>
	* 如果为现金缴费等无票据号的交费方式<p>
	* 则存全零
	*/
	public String getChequeNo()
	{
		return ChequeNo;
	}
	public void setChequeNo(String aChequeNo)
	{
		ChequeNo = aChequeNo;
	}
	public double getPayMoney()
	{
		return PayMoney;
	}
	public void setPayMoney(double aPayMoney)
	{
		PayMoney = aPayMoney;
	}
	public void setPayMoney(String aPayMoney)
	{
		if (aPayMoney != null && !aPayMoney.equals(""))
		{
			Double tDouble = new Double(aPayMoney);
			double d = tDouble.doubleValue();
			PayMoney = d;
		}
	}

	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
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

	/**
	* 0 ---没有财务核销<p>
	* 1 ---已经财务核销
	*/
	public String getConfFlag()
	{
		return ConfFlag;
	}
	public void setConfFlag(String aConfFlag)
	{
		ConfFlag = aConfFlag;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getPolicyCom()
	{
		return PolicyCom;
	}
	public void setPolicyCom(String aPolicyCom)
	{
		PolicyCom = aPolicyCom;
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
	* 表示给付后，财务的实际确认日期。<p>
	* 财务做财务给付确认操作的日期。
	*/
	public String getConfMakeDate()
	{
		if( ConfMakeDate != null )
			return fDate.getString(ConfMakeDate);
		else
			return null;
	}
	public void setConfMakeDate(Date aConfMakeDate)
	{
		ConfMakeDate = aConfMakeDate;
	}
	public void setConfMakeDate(String aConfMakeDate)
	{
		if (aConfMakeDate != null && !aConfMakeDate.equals("") )
		{
			ConfMakeDate = fDate.getDate( aConfMakeDate );
		}
		else
			ConfMakeDate = null;
	}

	public String getConfMakeTime()
	{
		return ConfMakeTime;
	}
	public void setConfMakeTime(String aConfMakeTime)
	{
		ConfMakeTime = aConfMakeTime;
	}
	public String getChequeDate()
	{
		if( ChequeDate != null )
			return fDate.getString(ChequeDate);
		else
			return null;
	}
	public void setChequeDate(Date aChequeDate)
	{
		ChequeDate = aChequeDate;
	}
	public void setChequeDate(String aChequeDate)
	{
		if (aChequeDate != null && !aChequeDate.equals("") )
		{
			ChequeDate = fDate.getDate( aChequeDate );
		}
		else
			ChequeDate = null;
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
	public String getInBankCode()
	{
		return InBankCode;
	}
	public void setInBankCode(String aInBankCode)
	{
		InBankCode = aInBankCode;
	}
	public String getInBankAccNo()
	{
		return InBankAccNo;
	}
	public void setInBankAccNo(String aInBankAccNo)
	{
		InBankAccNo = aInBankAccNo;
	}
	public String getInAccName()
	{
		return InAccName;
	}
	public void setInAccName(String aInAccName)
	{
		InAccName = aInAccName;
	}
	public String getContCom()
	{
		return ContCom;
	}
	public void setContCom(String aContCom)
	{
		ContCom = aContCom;
	}
	/**
	* 存放实收号码
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 对于交费类型为首期交费tempfeetype=1<p>
	* <p>
	* 0---首期个单交费核销后对应个单保单号<p>
	* 1---首期集体交费核销后对应集体保单号<p>
	* 2---首期合同交费核销后对应合同号<p>
	* 3---保全交费对应保全的批单号<p>
	* 4---首期个单、团单交费对应的印刷号码。<p>
	* 6---首期银行险时候转账，复核通过核销产生的对应银行险投保单号。<p>
	* 7---首期团单合同交费核销后对应团单合同号<p>
	* <p>
	* <p>
	* 另外，对于新单还没有暂缴费关联前，其他号码字段为空。所以对于暂交费退费需要根据该表中的投保人，名称来退。<p>
	* <p>
	* 对于从银行代收的数据(2003-9-8 yt add)：<p>
	* 首期直接交费 TempFeeType=1 OtherNoType=0<p>
	* 首期事后交费 TempFeeType=1 OtherNoType=0<p>
	* <p>
	* 正常续期催收 TempFeeType=2 OtherNoType=0<p>
	* <p>
	* 对于交费类型为续期交费tempfeetype=2<p>
	* 0---续期交费对应个单保单号<p>
	* 1---续期交费对应集体保单号<p>
	* 2---续期交费对应合同号
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	public String getTempFeeNoType()
	{
		return TempFeeNoType;
	}
	public void setTempFeeNoType(String aTempFeeNoType)
	{
		TempFeeNoType = aTempFeeNoType;
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
	public String getOriBankAccNo()
	{
		return OriBankAccNo;
	}
	public void setOriBankAccNo(String aOriBankAccNo)
	{
		OriBankAccNo = aOriBankAccNo;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getCustBankCode()
	{
		return CustBankCode;
	}
	public void setCustBankCode(String aCustBankCode)
	{
		CustBankCode = aCustBankCode;
	}
	public String getCustBankProvince()
	{
		return CustBankProvince;
	}
	public void setCustBankProvince(String aCustBankProvince)
	{
		CustBankProvince = aCustBankProvince;
	}
	public String getCustBankCity()
	{
		return CustBankCity;
	}
	public void setCustBankCity(String aCustBankCity)
	{
		CustBankCity = aCustBankCity;
	}
	public String getCustBankDetail()
	{
		return CustBankDetail;
	}
	public void setCustBankDetail(String aCustBankDetail)
	{
		CustBankDetail = aCustBankDetail;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentName()
	{
		return AgentName;
	}
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	public String getDraweeName()
	{
		return DraweeName;
	}
	public void setDraweeName(String aDraweeName)
	{
		DraweeName = aDraweeName;
	}
	public String getPrintState()
	{
		return PrintState;
	}
	public void setPrintState(String aPrintState)
	{
		PrintState = aPrintState;
	}
	public String getFileNo()
	{
		return FileNo;
	}
	public void setFileNo(String aFileNo)
	{
		FileNo = aFileNo;
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

	/**
	* 使用另外一个 LJTempFeeClassSchema 对象给 Schema 赋值
	* @param: aLJTempFeeClassSchema LJTempFeeClassSchema
	**/
	public void setSchema(LJTempFeeClassSchema aLJTempFeeClassSchema)
	{
		this.TempFeeNo = aLJTempFeeClassSchema.getTempFeeNo();
		this.PayMode = aLJTempFeeClassSchema.getPayMode();
		this.ChequeNo = aLJTempFeeClassSchema.getChequeNo();
		this.PayMoney = aLJTempFeeClassSchema.getPayMoney();
		this.AppntName = aLJTempFeeClassSchema.getAppntName();
		this.PayDate = fDate.getDate( aLJTempFeeClassSchema.getPayDate());
		this.ConfDate = fDate.getDate( aLJTempFeeClassSchema.getConfDate());
		this.ApproveDate = fDate.getDate( aLJTempFeeClassSchema.getApproveDate());
		this.EnterAccDate = fDate.getDate( aLJTempFeeClassSchema.getEnterAccDate());
		this.ConfFlag = aLJTempFeeClassSchema.getConfFlag();
		this.SerialNo = aLJTempFeeClassSchema.getSerialNo();
		this.ManageCom = aLJTempFeeClassSchema.getManageCom();
		this.PolicyCom = aLJTempFeeClassSchema.getPolicyCom();
		this.BankCode = aLJTempFeeClassSchema.getBankCode();
		this.BankAccNo = aLJTempFeeClassSchema.getBankAccNo();
		this.AccName = aLJTempFeeClassSchema.getAccName();
		this.ConfMakeDate = fDate.getDate( aLJTempFeeClassSchema.getConfMakeDate());
		this.ConfMakeTime = aLJTempFeeClassSchema.getConfMakeTime();
		this.ChequeDate = fDate.getDate( aLJTempFeeClassSchema.getChequeDate());
		this.Operator = aLJTempFeeClassSchema.getOperator();
		this.MakeDate = fDate.getDate( aLJTempFeeClassSchema.getMakeDate());
		this.MakeTime = aLJTempFeeClassSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLJTempFeeClassSchema.getModifyDate());
		this.ModifyTime = aLJTempFeeClassSchema.getModifyTime();
		this.InBankCode = aLJTempFeeClassSchema.getInBankCode();
		this.InBankAccNo = aLJTempFeeClassSchema.getInBankAccNo();
		this.InAccName = aLJTempFeeClassSchema.getInAccName();
		this.ContCom = aLJTempFeeClassSchema.getContCom();
		this.OtherNo = aLJTempFeeClassSchema.getOtherNo();
		this.OtherNoType = aLJTempFeeClassSchema.getOtherNoType();
		this.TempFeeNoType = aLJTempFeeClassSchema.getTempFeeNoType();
		this.OperState = aLJTempFeeClassSchema.getOperState();
		this.IDType = aLJTempFeeClassSchema.getIDType();
		this.IDNo = aLJTempFeeClassSchema.getIDNo();
		this.OriBankAccNo = aLJTempFeeClassSchema.getOriBankAccNo();
		this.Currency = aLJTempFeeClassSchema.getCurrency();
		this.CustBankCode = aLJTempFeeClassSchema.getCustBankCode();
		this.CustBankProvince = aLJTempFeeClassSchema.getCustBankProvince();
		this.CustBankCity = aLJTempFeeClassSchema.getCustBankCity();
		this.CustBankDetail = aLJTempFeeClassSchema.getCustBankDetail();
		this.AppntNo = aLJTempFeeClassSchema.getAppntNo();
		this.AgentCode = aLJTempFeeClassSchema.getAgentCode();
		this.AgentName = aLJTempFeeClassSchema.getAgentName();
		this.DraweeName = aLJTempFeeClassSchema.getDraweeName();
		this.PrintState = aLJTempFeeClassSchema.getPrintState();
		this.FileNo = aLJTempFeeClassSchema.getFileNo();
		this.ComCode = aLJTempFeeClassSchema.getComCode();
		this.ModifyOperator = aLJTempFeeClassSchema.getModifyOperator();
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
			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("ChequeNo") == null )
				this.ChequeNo = null;
			else
				this.ChequeNo = rs.getString("ChequeNo").trim();

			this.PayMoney = rs.getDouble("PayMoney");
			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.PayDate = rs.getDate("PayDate");
			this.ConfDate = rs.getDate("ConfDate");
			this.ApproveDate = rs.getDate("ApproveDate");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			if( rs.getString("ConfFlag") == null )
				this.ConfFlag = null;
			else
				this.ConfFlag = rs.getString("ConfFlag").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("PolicyCom") == null )
				this.PolicyCom = null;
			else
				this.PolicyCom = rs.getString("PolicyCom").trim();

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

			this.ConfMakeDate = rs.getDate("ConfMakeDate");
			if( rs.getString("ConfMakeTime") == null )
				this.ConfMakeTime = null;
			else
				this.ConfMakeTime = rs.getString("ConfMakeTime").trim();

			this.ChequeDate = rs.getDate("ChequeDate");
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

			if( rs.getString("InBankCode") == null )
				this.InBankCode = null;
			else
				this.InBankCode = rs.getString("InBankCode").trim();

			if( rs.getString("InBankAccNo") == null )
				this.InBankAccNo = null;
			else
				this.InBankAccNo = rs.getString("InBankAccNo").trim();

			if( rs.getString("InAccName") == null )
				this.InAccName = null;
			else
				this.InAccName = rs.getString("InAccName").trim();

			if( rs.getString("ContCom") == null )
				this.ContCom = null;
			else
				this.ContCom = rs.getString("ContCom").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("TempFeeNoType") == null )
				this.TempFeeNoType = null;
			else
				this.TempFeeNoType = rs.getString("TempFeeNoType").trim();

			if( rs.getString("OperState") == null )
				this.OperState = null;
			else
				this.OperState = rs.getString("OperState").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("OriBankAccNo") == null )
				this.OriBankAccNo = null;
			else
				this.OriBankAccNo = rs.getString("OriBankAccNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("CustBankCode") == null )
				this.CustBankCode = null;
			else
				this.CustBankCode = rs.getString("CustBankCode").trim();

			if( rs.getString("CustBankProvince") == null )
				this.CustBankProvince = null;
			else
				this.CustBankProvince = rs.getString("CustBankProvince").trim();

			if( rs.getString("CustBankCity") == null )
				this.CustBankCity = null;
			else
				this.CustBankCity = rs.getString("CustBankCity").trim();

			if( rs.getString("CustBankDetail") == null )
				this.CustBankDetail = null;
			else
				this.CustBankDetail = rs.getString("CustBankDetail").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("DraweeName") == null )
				this.DraweeName = null;
			else
				this.DraweeName = rs.getString("DraweeName").trim();

			if( rs.getString("PrintState") == null )
				this.PrintState = null;
			else
				this.PrintState = rs.getString("PrintState").trim();

			if( rs.getString("FileNo") == null )
				this.FileNo = null;
			else
				this.FileNo = rs.getString("FileNo").trim();

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
			logger.debug("数据库中的LJTempFeeClass表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeClassSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJTempFeeClassSchema getSchema()
	{
		LJTempFeeClassSchema aLJTempFeeClassSchema = new LJTempFeeClassSchema();
		aLJTempFeeClassSchema.setSchema(this);
		return aLJTempFeeClassSchema;
	}

	public LJTempFeeClassDB getDB()
	{
		LJTempFeeClassDB aDBOper = new LJTempFeeClassDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJTempFeeClass描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChequeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ChequeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DraweeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJTempFeeClass>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ChequeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ConfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PolicyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ConfMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ConfMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ChequeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			InBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			InBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			InAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ContCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			TempFeeNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			OperState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			OriBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			CustBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			CustBankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			CustBankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			CustBankDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			DraweeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			PrintState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			FileNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeClassSchema";
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
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("ChequeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChequeNo));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfFlag));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCom));
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
		if (FCode.equalsIgnoreCase("ConfMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ConfMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfMakeTime));
		}
		if (FCode.equalsIgnoreCase("ChequeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getChequeDate()));
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
		if (FCode.equalsIgnoreCase("InBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InBankCode));
		}
		if (FCode.equalsIgnoreCase("InBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InBankAccNo));
		}
		if (FCode.equalsIgnoreCase("InAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAccName));
		}
		if (FCode.equalsIgnoreCase("ContCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContCom));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("TempFeeNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNoType));
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperState));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("OriBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriBankAccNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCode));
		}
		if (FCode.equalsIgnoreCase("CustBankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankProvince));
		}
		if (FCode.equalsIgnoreCase("CustBankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCity));
		}
		if (FCode.equalsIgnoreCase("CustBankDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankDetail));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equalsIgnoreCase("DraweeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DraweeName));
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintState));
		}
		if (FCode.equalsIgnoreCase("FileNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileNo));
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
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ChequeNo);
				break;
			case 3:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ConfFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PolicyCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ConfMakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getChequeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(InBankCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(InBankAccNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(InAccName);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ContCom);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNoType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(OperState);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(OriBankAccNo);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(CustBankCode);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(CustBankProvince);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(CustBankCity);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(CustBankDetail);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(DraweeName);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(PrintState);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(FileNo);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 47:
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

		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
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
		if (FCode.equalsIgnoreCase("ChequeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChequeNo = FValue.trim();
			}
			else
				ChequeNo = null;
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PayMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayDate = fDate.getDate( FValue );
			}
			else
				PayDate = null;
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
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
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
		if (FCode.equalsIgnoreCase("ConfFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfFlag = FValue.trim();
			}
			else
				ConfFlag = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("ConfMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfMakeDate = fDate.getDate( FValue );
			}
			else
				ConfMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfMakeTime = FValue.trim();
			}
			else
				ConfMakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ChequeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ChequeDate = fDate.getDate( FValue );
			}
			else
				ChequeDate = null;
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
		if (FCode.equalsIgnoreCase("InBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InBankCode = FValue.trim();
			}
			else
				InBankCode = null;
		}
		if (FCode.equalsIgnoreCase("InBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InBankAccNo = FValue.trim();
			}
			else
				InBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("InAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InAccName = FValue.trim();
			}
			else
				InAccName = null;
		}
		if (FCode.equalsIgnoreCase("ContCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContCom = FValue.trim();
			}
			else
				ContCom = null;
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
		if (FCode.equalsIgnoreCase("TempFeeNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNoType = FValue.trim();
			}
			else
				TempFeeNoType = null;
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
		if (FCode.equalsIgnoreCase("OriBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriBankAccNo = FValue.trim();
			}
			else
				OriBankAccNo = null;
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
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCode = FValue.trim();
			}
			else
				CustBankCode = null;
		}
		if (FCode.equalsIgnoreCase("CustBankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankProvince = FValue.trim();
			}
			else
				CustBankProvince = null;
		}
		if (FCode.equalsIgnoreCase("CustBankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCity = FValue.trim();
			}
			else
				CustBankCity = null;
		}
		if (FCode.equalsIgnoreCase("CustBankDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankDetail = FValue.trim();
			}
			else
				CustBankDetail = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
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
		if (FCode.equalsIgnoreCase("DraweeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DraweeName = FValue.trim();
			}
			else
				DraweeName = null;
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintState = FValue.trim();
			}
			else
				PrintState = null;
		}
		if (FCode.equalsIgnoreCase("FileNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileNo = FValue.trim();
			}
			else
				FileNo = null;
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
		LJTempFeeClassSchema other = (LJTempFeeClassSchema)otherObject;
		return
			TempFeeNo.equals(other.getTempFeeNo())
			&& PayMode.equals(other.getPayMode())
			&& ChequeNo.equals(other.getChequeNo())
			&& PayMoney == other.getPayMoney()
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& ConfFlag.equals(other.getConfFlag())
			&& SerialNo.equals(other.getSerialNo())
			&& ManageCom.equals(other.getManageCom())
			&& PolicyCom.equals(other.getPolicyCom())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& fDate.getString(ConfMakeDate).equals(other.getConfMakeDate())
			&& ConfMakeTime.equals(other.getConfMakeTime())
			&& fDate.getString(ChequeDate).equals(other.getChequeDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& InBankCode.equals(other.getInBankCode())
			&& InBankAccNo.equals(other.getInBankAccNo())
			&& InAccName.equals(other.getInAccName())
			&& ContCom.equals(other.getContCom())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& TempFeeNoType.equals(other.getTempFeeNoType())
			&& OperState.equals(other.getOperState())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& OriBankAccNo.equals(other.getOriBankAccNo())
			&& Currency.equals(other.getCurrency())
			&& CustBankCode.equals(other.getCustBankCode())
			&& CustBankProvince.equals(other.getCustBankProvince())
			&& CustBankCity.equals(other.getCustBankCity())
			&& CustBankDetail.equals(other.getCustBankDetail())
			&& AppntNo.equals(other.getAppntNo())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentName.equals(other.getAgentName())
			&& DraweeName.equals(other.getDraweeName())
			&& PrintState.equals(other.getPrintState())
			&& FileNo.equals(other.getFileNo())
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
		if( strFieldName.equals("TempFeeNo") ) {
			return 0;
		}
		if( strFieldName.equals("PayMode") ) {
			return 1;
		}
		if( strFieldName.equals("ChequeNo") ) {
			return 2;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 3;
		}
		if( strFieldName.equals("AppntName") ) {
			return 4;
		}
		if( strFieldName.equals("PayDate") ) {
			return 5;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 6;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 7;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 8;
		}
		if( strFieldName.equals("ConfFlag") ) {
			return 9;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return 12;
		}
		if( strFieldName.equals("BankCode") ) {
			return 13;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 14;
		}
		if( strFieldName.equals("AccName") ) {
			return 15;
		}
		if( strFieldName.equals("ConfMakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("ConfMakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ChequeDate") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("InBankCode") ) {
			return 24;
		}
		if( strFieldName.equals("InBankAccNo") ) {
			return 25;
		}
		if( strFieldName.equals("InAccName") ) {
			return 26;
		}
		if( strFieldName.equals("ContCom") ) {
			return 27;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 28;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 29;
		}
		if( strFieldName.equals("TempFeeNoType") ) {
			return 30;
		}
		if( strFieldName.equals("OperState") ) {
			return 31;
		}
		if( strFieldName.equals("IDType") ) {
			return 32;
		}
		if( strFieldName.equals("IDNo") ) {
			return 33;
		}
		if( strFieldName.equals("OriBankAccNo") ) {
			return 34;
		}
		if( strFieldName.equals("Currency") ) {
			return 35;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return 36;
		}
		if( strFieldName.equals("CustBankProvince") ) {
			return 37;
		}
		if( strFieldName.equals("CustBankCity") ) {
			return 38;
		}
		if( strFieldName.equals("CustBankDetail") ) {
			return 39;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 40;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 41;
		}
		if( strFieldName.equals("AgentName") ) {
			return 42;
		}
		if( strFieldName.equals("DraweeName") ) {
			return 43;
		}
		if( strFieldName.equals("PrintState") ) {
			return 44;
		}
		if( strFieldName.equals("FileNo") ) {
			return 45;
		}
		if( strFieldName.equals("ComCode") ) {
			return 46;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 47;
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
				strFieldName = "TempFeeNo";
				break;
			case 1:
				strFieldName = "PayMode";
				break;
			case 2:
				strFieldName = "ChequeNo";
				break;
			case 3:
				strFieldName = "PayMoney";
				break;
			case 4:
				strFieldName = "AppntName";
				break;
			case 5:
				strFieldName = "PayDate";
				break;
			case 6:
				strFieldName = "ConfDate";
				break;
			case 7:
				strFieldName = "ApproveDate";
				break;
			case 8:
				strFieldName = "EnterAccDate";
				break;
			case 9:
				strFieldName = "ConfFlag";
				break;
			case 10:
				strFieldName = "SerialNo";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "PolicyCom";
				break;
			case 13:
				strFieldName = "BankCode";
				break;
			case 14:
				strFieldName = "BankAccNo";
				break;
			case 15:
				strFieldName = "AccName";
				break;
			case 16:
				strFieldName = "ConfMakeDate";
				break;
			case 17:
				strFieldName = "ConfMakeTime";
				break;
			case 18:
				strFieldName = "ChequeDate";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "InBankCode";
				break;
			case 25:
				strFieldName = "InBankAccNo";
				break;
			case 26:
				strFieldName = "InAccName";
				break;
			case 27:
				strFieldName = "ContCom";
				break;
			case 28:
				strFieldName = "OtherNo";
				break;
			case 29:
				strFieldName = "OtherNoType";
				break;
			case 30:
				strFieldName = "TempFeeNoType";
				break;
			case 31:
				strFieldName = "OperState";
				break;
			case 32:
				strFieldName = "IDType";
				break;
			case 33:
				strFieldName = "IDNo";
				break;
			case 34:
				strFieldName = "OriBankAccNo";
				break;
			case 35:
				strFieldName = "Currency";
				break;
			case 36:
				strFieldName = "CustBankCode";
				break;
			case 37:
				strFieldName = "CustBankProvince";
				break;
			case 38:
				strFieldName = "CustBankCity";
				break;
			case 39:
				strFieldName = "CustBankDetail";
				break;
			case 40:
				strFieldName = "AppntNo";
				break;
			case 41:
				strFieldName = "AgentCode";
				break;
			case 42:
				strFieldName = "AgentName";
				break;
			case 43:
				strFieldName = "DraweeName";
				break;
			case 44:
				strFieldName = "PrintState";
				break;
			case 45:
				strFieldName = "FileNo";
				break;
			case 46:
				strFieldName = "ComCode";
				break;
			case 47:
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
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChequeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCom") ) {
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
		if( strFieldName.equals("ConfMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfMakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChequeDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("InBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DraweeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileNo") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
