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
import com.sinosoft.lis.db.LJAGetTempFeeDB;

/*
 * <p>ClassName: LJAGetTempFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务付费
 */
public class LJAGetTempFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJAGetTempFeeSchema.class);
	// @Field
	/** 实付号码 */
	private String ActuGetNo;
	/** 暂交费收据号码 */
	private String TempFeeNo;
	/** 险种编码 */
	private String RiskCode;
	/** 暂交费收据号类型 */
	private String TempFeeType;
	/** 补/退费业务类型 */
	private String FeeOperationType;
	/** 补/退费财务类型 */
	private String FeeFinaType;
	/** 交费方式 */
	private String PayMode;
	/** 退费金额 */
	private double GetMoney;
	/** 退费日期 */
	private Date GetDate;
	/** 到帐日期 */
	private Date EnterAccDate;
	/** 确认日期 */
	private Date ConfDate;
	/** 管理机构 */
	private String ManageCom;
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
	/** 操作员 */
	private String Operator;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 退费原因编码 */
	private String GetReasonCode;
	/** 状态 */
	private String State;
	/** 给付通知书号码 */
	private String GetNoticeNo;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 业务状态 */
	private String OperState;
	/** 币别 */
	private String Currency;
	/** 净费用 */
	private double NetAmount;
	/** 税额 */
	private double TaxAmount;
	/** 税率 */
	private double Tax;

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJAGetTempFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ActuGetNo";
		pk[1] = "TempFeeNo";
		pk[2] = "RiskCode";
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
		LJAGetTempFeeSchema cloned = (LJAGetTempFeeSchema)super.clone();
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
	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 0 ---直接交费,新生成<p>
	* 1 ---新单交费，录入暂交费收据号<p>
	* 2 ---续期催收交费，录入催收号<p>
	* 3 ---续期非催收交费，新生成<p>
	* 4 ---保全交费，新生成<p>
	* 5 ---客户预存交费退费
	*/
	public String getTempFeeType()
	{
		return TempFeeType;
	}
	public void setTempFeeType(String aTempFeeType)
	{
		TempFeeType = aTempFeeType;
	}
	/**
	* 对应以前的tf_type,正常退费为空<p>
	* YC -- 对应预存保费退费
	*/
	public String getFeeOperationType()
	{
		return FeeOperationType;
	}
	public void setFeeOperationType(String aFeeOperationType)
	{
		FeeOperationType = aFeeOperationType;
	}
	/**
	* 对应以前的tf_je_type,正常退费为空<p>
	* YC -- 对应预存保费退费
	*/
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		FeeFinaType = aFeeFinaType;
	}
	/**
	* 1 现金<p>
	* 2 现金支票<p>
	* 3 转账支票<p>
	* 4 银行转账<p>
	* 5 内部转帐<p>
	* 6 POS付款<p>
	* 7 网上银行
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
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

	public String getGetDate()
	{
		if( GetDate != null )
			return fDate.getString(GetDate);
		else
			return null;
	}
	public void setGetDate(Date aGetDate)
	{
		GetDate = aGetDate;
	}
	public void setGetDate(String aGetDate)
	{
		if (aGetDate != null && !aGetDate.equals("") )
		{
			GetDate = fDate.getDate( aGetDate );
		}
		else
			GetDate = null;
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

	/**
	* 保险计划变更时，如果对于附加险的中止处理，则退费原因编码,先默认为0
	*/
	public String getGetReasonCode()
	{
		return GetReasonCode;
	}
	public void setGetReasonCode(String aGetReasonCode)
	{
		GetReasonCode = aGetReasonCode;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
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
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
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
	* 使用另外一个 LJAGetTempFeeSchema 对象给 Schema 赋值
	* @param: aLJAGetTempFeeSchema LJAGetTempFeeSchema
	**/
	public void setSchema(LJAGetTempFeeSchema aLJAGetTempFeeSchema)
	{
		this.ActuGetNo = aLJAGetTempFeeSchema.getActuGetNo();
		this.TempFeeNo = aLJAGetTempFeeSchema.getTempFeeNo();
		this.RiskCode = aLJAGetTempFeeSchema.getRiskCode();
		this.TempFeeType = aLJAGetTempFeeSchema.getTempFeeType();
		this.FeeOperationType = aLJAGetTempFeeSchema.getFeeOperationType();
		this.FeeFinaType = aLJAGetTempFeeSchema.getFeeFinaType();
		this.PayMode = aLJAGetTempFeeSchema.getPayMode();
		this.GetMoney = aLJAGetTempFeeSchema.getGetMoney();
		this.GetDate = fDate.getDate( aLJAGetTempFeeSchema.getGetDate());
		this.EnterAccDate = fDate.getDate( aLJAGetTempFeeSchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLJAGetTempFeeSchema.getConfDate());
		this.ManageCom = aLJAGetTempFeeSchema.getManageCom();
		this.AgentCom = aLJAGetTempFeeSchema.getAgentCom();
		this.AgentType = aLJAGetTempFeeSchema.getAgentType();
		this.APPntName = aLJAGetTempFeeSchema.getAPPntName();
		this.AgentGroup = aLJAGetTempFeeSchema.getAgentGroup();
		this.AgentCode = aLJAGetTempFeeSchema.getAgentCode();
		this.SerialNo = aLJAGetTempFeeSchema.getSerialNo();
		this.Operator = aLJAGetTempFeeSchema.getOperator();
		this.MakeTime = aLJAGetTempFeeSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLJAGetTempFeeSchema.getMakeDate());
		this.GetReasonCode = aLJAGetTempFeeSchema.getGetReasonCode();
		this.State = aLJAGetTempFeeSchema.getState();
		this.GetNoticeNo = aLJAGetTempFeeSchema.getGetNoticeNo();
		this.ModifyDate = fDate.getDate( aLJAGetTempFeeSchema.getModifyDate());
		this.ModifyTime = aLJAGetTempFeeSchema.getModifyTime();
		this.OperState = aLJAGetTempFeeSchema.getOperState();
		this.Currency = aLJAGetTempFeeSchema.getCurrency();
		this.NetAmount = aLJAGetTempFeeSchema.getNetAmount();
		this.TaxAmount = aLJAGetTempFeeSchema.getTaxAmount();
		this.Tax = aLJAGetTempFeeSchema.getTax();
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

			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("TempFeeType") == null )
				this.TempFeeType = null;
			else
				this.TempFeeType = rs.getString("TempFeeType").trim();

			if( rs.getString("FeeOperationType") == null )
				this.FeeOperationType = null;
			else
				this.FeeOperationType = rs.getString("FeeOperationType").trim();

			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			this.GetMoney = rs.getDouble("GetMoney");
			this.GetDate = rs.getDate("GetDate");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("GetReasonCode") == null )
				this.GetReasonCode = null;
			else
				this.GetReasonCode = rs.getString("GetReasonCode").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("OperState") == null )
				this.OperState = null;
			else
				this.OperState = rs.getString("OperState").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			this.NetAmount = rs.getDouble("NetAmount");
			this.TaxAmount = rs.getDouble("TaxAmount");
			this.Tax = rs.getDouble("Tax");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJAGetTempFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAGetTempFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJAGetTempFeeSchema getSchema()
	{
		LJAGetTempFeeSchema aLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		aLJAGetTempFeeSchema.setSchema(this);
		return aLJAGetTempFeeSchema;
	}

	public LJAGetTempFeeDB getDB()
	{
		LJAGetTempFeeDB aDBOper = new LJAGetTempFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJAGetTempFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ActuGetNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(APPntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NetAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TaxAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Tax));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJAGetTempFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ActuGetNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TempFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			GetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			APPntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			GetReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			OperState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			NetAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			TaxAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			Tax = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAGetTempFeeSchema";
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
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("TempFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeType));
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeOperationType));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
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
		if (FCode.equalsIgnoreCase("GetReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetReasonCode));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperState));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TempFeeType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FeeOperationType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 7:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(APPntName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(GetReasonCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(OperState);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 28:
				strFieldValue = String.valueOf(NetAmount);
				break;
			case 29:
				strFieldValue = String.valueOf(TaxAmount);
				break;
			case 30:
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
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
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
		if (FCode.equalsIgnoreCase("TempFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeType = FValue.trim();
			}
			else
				TempFeeType = null;
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeOperationType = FValue.trim();
			}
			else
				FeeOperationType = null;
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeFinaType = FValue.trim();
			}
			else
				FeeFinaType = null;
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
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetDate = fDate.getDate( FValue );
			}
			else
				GetDate = null;
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
		if (FCode.equalsIgnoreCase("GetReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetReasonCode = FValue.trim();
			}
			else
				GetReasonCode = null;
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
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
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
		if (FCode.equalsIgnoreCase("OperState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperState = FValue.trim();
			}
			else
				OperState = null;
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
		LJAGetTempFeeSchema other = (LJAGetTempFeeSchema)otherObject;
		return
			ActuGetNo.equals(other.getActuGetNo())
			&& TempFeeNo.equals(other.getTempFeeNo())
			&& RiskCode.equals(other.getRiskCode())
			&& TempFeeType.equals(other.getTempFeeType())
			&& FeeOperationType.equals(other.getFeeOperationType())
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& PayMode.equals(other.getPayMode())
			&& GetMoney == other.getGetMoney()
			&& fDate.getString(GetDate).equals(other.getGetDate())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& APPntName.equals(other.getAPPntName())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode.equals(other.getAgentCode())
			&& SerialNo.equals(other.getSerialNo())
			&& Operator.equals(other.getOperator())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& GetReasonCode.equals(other.getGetReasonCode())
			&& State.equals(other.getState())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& OperState.equals(other.getOperState())
			&& Currency.equals(other.getCurrency())
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
		if( strFieldName.equals("TempFeeNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("TempFeeType") ) {
			return 3;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return 4;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 5;
		}
		if( strFieldName.equals("PayMode") ) {
			return 6;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 7;
		}
		if( strFieldName.equals("GetDate") ) {
			return 8;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 9;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 10;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 12;
		}
		if( strFieldName.equals("AgentType") ) {
			return 13;
		}
		if( strFieldName.equals("APPntName") ) {
			return 14;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 15;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 16;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("GetReasonCode") ) {
			return 21;
		}
		if( strFieldName.equals("State") ) {
			return 22;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 25;
		}
		if( strFieldName.equals("OperState") ) {
			return 26;
		}
		if( strFieldName.equals("Currency") ) {
			return 27;
		}
		if( strFieldName.equals("NetAmount") ) {
			return 28;
		}
		if( strFieldName.equals("TaxAmount") ) {
			return 29;
		}
		if( strFieldName.equals("Tax") ) {
			return 30;
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
				strFieldName = "TempFeeNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "TempFeeType";
				break;
			case 4:
				strFieldName = "FeeOperationType";
				break;
			case 5:
				strFieldName = "FeeFinaType";
				break;
			case 6:
				strFieldName = "PayMode";
				break;
			case 7:
				strFieldName = "GetMoney";
				break;
			case 8:
				strFieldName = "GetDate";
				break;
			case 9:
				strFieldName = "EnterAccDate";
				break;
			case 10:
				strFieldName = "ConfDate";
				break;
			case 11:
				strFieldName = "ManageCom";
				break;
			case 12:
				strFieldName = "AgentCom";
				break;
			case 13:
				strFieldName = "AgentType";
				break;
			case 14:
				strFieldName = "APPntName";
				break;
			case 15:
				strFieldName = "AgentGroup";
				break;
			case 16:
				strFieldName = "AgentCode";
				break;
			case 17:
				strFieldName = "SerialNo";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "GetReasonCode";
				break;
			case 22:
				strFieldName = "State";
				break;
			case 23:
				strFieldName = "GetNoticeNo";
				break;
			case 24:
				strFieldName = "ModifyDate";
				break;
			case 25:
				strFieldName = "ModifyTime";
				break;
			case 26:
				strFieldName = "OperState";
				break;
			case 27:
				strFieldName = "Currency";
				break;
			case 28:
				strFieldName = "NetAmount";
				break;
			case 29:
				strFieldName = "TaxAmount";
				break;
			case 30:
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
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
