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
import com.sinosoft.lis.db.LJFIGetDB;

/*
 * <p>ClassName: LJFIGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务付费
 */
public class LJFIGetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJFIGetSchema.class);
	// @Field
	/** 实付号码 */
	private String ActuGetNo;
	/** 交费方式 */
	private String PayMode;
	/** 其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 给付金额 */
	private double GetMoney;
	/** 应付日期 */
	private Date ShouldDate;
	/** 财务到帐日期 */
	private Date EnterAccDate;
	/** 财务确认日期 */
	private Date ConfDate;
	/** 银行编码 */
	private String BankCode;
	/** 票据号 */
	private String ChequeNo;
	/** 银行帐号 */
	private String BankAccNo;
	/** 帐户名称 */
	private String AccName;
	/** 财务确认操作日期 */
	private Date ConfMakeDate;
	/** 财务确认操作时间 */
	private String ConfMakeTime;
	/** 销售渠道 */
	private String SaleChnl;
	/** 付费机构 */
	private String ManageCom;
	/** 管理机构 */
	private String PolicyCom;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 投保人名称 */
	private String APPntName;
	/** 代理人组别 */
	private String AgentGroup;
	/** 代理人编码 */
	private String AgentCode;
	/** 流水号 */
	private String SerialNo;
	/** 领取人 */
	private String Drawer;
	/** 领取人身份证号 */
	private String DrawerID;
	/** 操作员 */
	private String Operator;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 状态 */
	private String State;
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
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 35;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJFIGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ActuGetNo";
		pk[1] = "PayMode";
		pk[2] = "Currency";

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
		LJFIGetSchema cloned = (LJFIGetSchema)super.clone();
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
	/**
	* 1	现金<p>
	* 2	现金支票<p>
	* 3	转账支票<p>
	* 4	银行转账<p>
	* 5	内部转帐<p>
	* 6	POS付款<p>
	* 7	网上银行<p>
	* T     转养老金
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
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
	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	/**
	* 表示应该给付的日期。<p>
	* 对于应于生成实付的日期（LJAGet.MakeDate）
	*/
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

	/**
	* 表示实际给到客户帐户中的日期。<p>
	* 柜台领取：为操作日期当天<p>
	* 银行代付：为银行扣款日期
	*/
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
	* 付费成功日期
	*/
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

	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getChequeNo()
	{
		return ChequeNo;
	}
	public void setChequeNo(String aChequeNo)
	{
		ChequeNo = aChequeNo;
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
	public String getAPPntName()
	{
		return APPntName;
	}
	public void setAPPntName(String aAPPntName)
	{
		APPntName = aAPPntName;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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

	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}

	/**
	* 使用另外一个 LJFIGetSchema 对象给 Schema 赋值
	* @param: aLJFIGetSchema LJFIGetSchema
	**/
	public void setSchema(LJFIGetSchema aLJFIGetSchema)
	{
		this.ActuGetNo = aLJFIGetSchema.getActuGetNo();
		this.PayMode = aLJFIGetSchema.getPayMode();
		this.OtherNo = aLJFIGetSchema.getOtherNo();
		this.OtherNoType = aLJFIGetSchema.getOtherNoType();
		this.GetMoney = aLJFIGetSchema.getGetMoney();
		this.ShouldDate = fDate.getDate( aLJFIGetSchema.getShouldDate());
		this.EnterAccDate = fDate.getDate( aLJFIGetSchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLJFIGetSchema.getConfDate());
		this.BankCode = aLJFIGetSchema.getBankCode();
		this.ChequeNo = aLJFIGetSchema.getChequeNo();
		this.BankAccNo = aLJFIGetSchema.getBankAccNo();
		this.AccName = aLJFIGetSchema.getAccName();
		this.ConfMakeDate = fDate.getDate( aLJFIGetSchema.getConfMakeDate());
		this.ConfMakeTime = aLJFIGetSchema.getConfMakeTime();
		this.SaleChnl = aLJFIGetSchema.getSaleChnl();
		this.ManageCom = aLJFIGetSchema.getManageCom();
		this.PolicyCom = aLJFIGetSchema.getPolicyCom();
		this.AgentCom = aLJFIGetSchema.getAgentCom();
		this.AgentType = aLJFIGetSchema.getAgentType();
		this.APPntName = aLJFIGetSchema.getAPPntName();
		this.AgentGroup = aLJFIGetSchema.getAgentGroup();
		this.AgentCode = aLJFIGetSchema.getAgentCode();
		this.SerialNo = aLJFIGetSchema.getSerialNo();
		this.Drawer = aLJFIGetSchema.getDrawer();
		this.DrawerID = aLJFIGetSchema.getDrawerID();
		this.Operator = aLJFIGetSchema.getOperator();
		this.MakeTime = aLJFIGetSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLJFIGetSchema.getMakeDate());
		this.State = aLJFIGetSchema.getState();
		this.ModifyDate = fDate.getDate( aLJFIGetSchema.getModifyDate());
		this.ModifyTime = aLJFIGetSchema.getModifyTime();
		this.InBankCode = aLJFIGetSchema.getInBankCode();
		this.InBankAccNo = aLJFIGetSchema.getInBankAccNo();
		this.InAccName = aLJFIGetSchema.getInAccName();
		this.Currency = aLJFIGetSchema.getCurrency();
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

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			this.GetMoney = rs.getDouble("GetMoney");
			this.ShouldDate = rs.getDate("ShouldDate");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("ChequeNo") == null )
				this.ChequeNo = null;
			else
				this.ChequeNo = rs.getString("ChequeNo").trim();

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

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("PolicyCom") == null )
				this.PolicyCom = null;
			else
				this.PolicyCom = rs.getString("PolicyCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("APPntName") == null )
				this.APPntName = null;
			else
				this.APPntName = rs.getString("APPntName").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("Drawer") == null )
				this.Drawer = null;
			else
				this.Drawer = rs.getString("Drawer").trim();

			if( rs.getString("DrawerID") == null )
				this.DrawerID = null;
			else
				this.DrawerID = rs.getString("DrawerID").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJFIGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFIGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJFIGetSchema getSchema()
	{
		LJFIGetSchema aLJFIGetSchema = new LJFIGetSchema();
		aLJFIGetSchema.setSchema(this);
		return aLJFIGetSchema;
	}

	public LJFIGetDB getDB()
	{
		LJFIGetDB aDBOper = new LJFIGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJFIGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ActuGetNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ShouldDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChequeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(APPntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Drawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJFIGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ActuGetNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			ShouldDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ChequeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ConfMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ConfMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PolicyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			APPntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Drawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			DrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			InBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			InAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFIGetSchema";
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
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("ChequeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChequeNo));
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("APPntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPntName));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Drawer));
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrawerID));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 4:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ChequeNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ConfMakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PolicyCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(APPntName);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Drawer);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(DrawerID);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(InBankCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(InBankAccNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(InAccName);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Currency);
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
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
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
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
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
		if (FCode.equalsIgnoreCase("PolicyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyCom = FValue.trim();
			}
			else
				PolicyCom = null;
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
		if (FCode.equalsIgnoreCase("APPntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPntName = FValue.trim();
			}
			else
				APPntName = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJFIGetSchema other = (LJFIGetSchema)otherObject;
		return
			ActuGetNo.equals(other.getActuGetNo())
			&& PayMode.equals(other.getPayMode())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& GetMoney == other.getGetMoney()
			&& fDate.getString(ShouldDate).equals(other.getShouldDate())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& BankCode.equals(other.getBankCode())
			&& ChequeNo.equals(other.getChequeNo())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& fDate.getString(ConfMakeDate).equals(other.getConfMakeDate())
			&& ConfMakeTime.equals(other.getConfMakeTime())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ManageCom.equals(other.getManageCom())
			&& PolicyCom.equals(other.getPolicyCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& APPntName.equals(other.getAPPntName())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode.equals(other.getAgentCode())
			&& SerialNo.equals(other.getSerialNo())
			&& Drawer.equals(other.getDrawer())
			&& DrawerID.equals(other.getDrawerID())
			&& Operator.equals(other.getOperator())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& State.equals(other.getState())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& InBankCode.equals(other.getInBankCode())
			&& InBankAccNo.equals(other.getInBankAccNo())
			&& InAccName.equals(other.getInAccName())
			&& Currency.equals(other.getCurrency());
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
		if( strFieldName.equals("PayMode") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 3;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 4;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return 5;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 6;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 7;
		}
		if( strFieldName.equals("BankCode") ) {
			return 8;
		}
		if( strFieldName.equals("ChequeNo") ) {
			return 9;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 10;
		}
		if( strFieldName.equals("AccName") ) {
			return 11;
		}
		if( strFieldName.equals("ConfMakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("ConfMakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 14;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 15;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 17;
		}
		if( strFieldName.equals("AgentType") ) {
			return 18;
		}
		if( strFieldName.equals("APPntName") ) {
			return 19;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 20;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 21;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 22;
		}
		if( strFieldName.equals("Drawer") ) {
			return 23;
		}
		if( strFieldName.equals("DrawerID") ) {
			return 24;
		}
		if( strFieldName.equals("Operator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 27;
		}
		if( strFieldName.equals("State") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 30;
		}
		if( strFieldName.equals("InBankCode") ) {
			return 31;
		}
		if( strFieldName.equals("InBankAccNo") ) {
			return 32;
		}
		if( strFieldName.equals("InAccName") ) {
			return 33;
		}
		if( strFieldName.equals("Currency") ) {
			return 34;
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
				strFieldName = "PayMode";
				break;
			case 2:
				strFieldName = "OtherNo";
				break;
			case 3:
				strFieldName = "OtherNoType";
				break;
			case 4:
				strFieldName = "GetMoney";
				break;
			case 5:
				strFieldName = "ShouldDate";
				break;
			case 6:
				strFieldName = "EnterAccDate";
				break;
			case 7:
				strFieldName = "ConfDate";
				break;
			case 8:
				strFieldName = "BankCode";
				break;
			case 9:
				strFieldName = "ChequeNo";
				break;
			case 10:
				strFieldName = "BankAccNo";
				break;
			case 11:
				strFieldName = "AccName";
				break;
			case 12:
				strFieldName = "ConfMakeDate";
				break;
			case 13:
				strFieldName = "ConfMakeTime";
				break;
			case 14:
				strFieldName = "SaleChnl";
				break;
			case 15:
				strFieldName = "ManageCom";
				break;
			case 16:
				strFieldName = "PolicyCom";
				break;
			case 17:
				strFieldName = "AgentCom";
				break;
			case 18:
				strFieldName = "AgentType";
				break;
			case 19:
				strFieldName = "APPntName";
				break;
			case 20:
				strFieldName = "AgentGroup";
				break;
			case 21:
				strFieldName = "AgentCode";
				break;
			case 22:
				strFieldName = "SerialNo";
				break;
			case 23:
				strFieldName = "Drawer";
				break;
			case 24:
				strFieldName = "DrawerID";
				break;
			case 25:
				strFieldName = "Operator";
				break;
			case 26:
				strFieldName = "MakeTime";
				break;
			case 27:
				strFieldName = "MakeDate";
				break;
			case 28:
				strFieldName = "State";
				break;
			case 29:
				strFieldName = "ModifyDate";
				break;
			case 30:
				strFieldName = "ModifyTime";
				break;
			case 31:
				strFieldName = "InBankCode";
				break;
			case 32:
				strFieldName = "InBankAccNo";
				break;
			case 33:
				strFieldName = "InAccName";
				break;
			case 34:
				strFieldName = "Currency";
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
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChequeNo") ) {
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
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Drawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrawerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
