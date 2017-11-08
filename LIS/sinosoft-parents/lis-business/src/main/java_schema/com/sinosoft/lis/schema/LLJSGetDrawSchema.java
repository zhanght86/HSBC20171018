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
import com.sinosoft.lis.db.LLJSGetDrawDB;

/*
 * <p>ClassName: LLJSGetDrawSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LLJSGetDrawSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLJSGetDrawSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 给付通知书号码 */
	private String GetNoticeNo;
	/** 保单号码 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 给付日期 */
	private Date GetDate;
	/** 给付金额 */
	private double GetMoney;
	/** 补/退费到帐日期 */
	private Date EnterAccDate;
	/** 补/退费财务确认日期 */
	private Date ConfDate;
	/** 补/退费业务类型 */
	private String FeeOperationType;
	/** 补/退费财务类型 */
	private String FeeFinaType;
	/** 险类编码 */
	private String KindCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
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
	/** 单位名称 */
	private String GrpName;
	/** 经办人 */
	private String Handler;
	/** 上次领至日期 */
	private Date LastGettoDate;
	/** 本次领至日期 */
	private Date CurGetToDate;
	/** 给付首期标志 */
	private String GetFirstFlag;
	/** 给付渠道标志 */
	private String GetChannelFlag;
	/** 消户标记 */
	private String DestrayFlag;
	/** 保单类型 */
	private String PolType;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
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
	/** 是否生调(上柜) */
	private String RReportFlag;
	/** 是否上柜 */
	private String ComeFlag;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLJSGetDrawSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "ClmNo";
		pk[1] = "GetNoticeNo";
		pk[2] = "PolNo";
		pk[3] = "DutyCode";
		pk[4] = "GetDutyKind";
		pk[5] = "GetDutyCode";

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
		LLJSGetDrawSchema cloned = (LLJSGetDrawSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	/**
	* 也就是给付通知书号
	*/
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	/**
	* 对应kind_pay_li 中一种领取方式如有十年固定年金，无十年固定年金，总共领5次或领十次等
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
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
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
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
	* 对应以前的tf_type
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
	* 对应以前的tf_je_type
	*/
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		FeeFinaType = aFeeFinaType;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
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
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		GrpName = aGrpName;
	}
	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
	public String getLastGettoDate()
	{
		if( LastGettoDate != null )
			return fDate.getString(LastGettoDate);
		else
			return null;
	}
	public void setLastGettoDate(Date aLastGettoDate)
	{
		LastGettoDate = aLastGettoDate;
	}
	public void setLastGettoDate(String aLastGettoDate)
	{
		if (aLastGettoDate != null && !aLastGettoDate.equals("") )
		{
			LastGettoDate = fDate.getDate( aLastGettoDate );
		}
		else
			LastGettoDate = null;
	}

	public String getCurGetToDate()
	{
		if( CurGetToDate != null )
			return fDate.getString(CurGetToDate);
		else
			return null;
	}
	public void setCurGetToDate(Date aCurGetToDate)
	{
		CurGetToDate = aCurGetToDate;
	}
	public void setCurGetToDate(String aCurGetToDate)
	{
		if (aCurGetToDate != null && !aCurGetToDate.equals("") )
		{
			CurGetToDate = fDate.getDate( aCurGetToDate );
		}
		else
			CurGetToDate = null;
	}

	/**
	* 判断该给付是否是首次给付<p>
	* 0 ---不是首次<p>
	* 1 ---是首次给付
	*/
	public String getGetFirstFlag()
	{
		return GetFirstFlag;
	}
	public void setGetFirstFlag(String aGetFirstFlag)
	{
		GetFirstFlag = aGetFirstFlag;
	}
	/**
	* 对应旧系统的是否上柜(上柜:1;非上柜:0)<p>
	* 0 ---通过银行给付<p>
	* 1 ---通过保险公司柜台领取
	*/
	public String getGetChannelFlag()
	{
		return GetChannelFlag;
	}
	public void setGetChannelFlag(String aGetChannelFlag)
	{
		GetChannelFlag = aGetChannelFlag;
	}
	/**
	* 通过该判断是否需要销户<p>
	* 默认为0<p>
	* 0 ---表示不需要销户,旧系统中为N<p>
	* 1 ---表示需要销户，旧系统中为Y
	*/
	public String getDestrayFlag()
	{
		return DestrayFlag;
	}
	public void setDestrayFlag(String aDestrayFlag)
	{
		DestrayFlag = aDestrayFlag;
	}
	/**
	* 1 个单；2分单；3总单
	*/
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		PolType = aPolType;
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
	/**
	* 0:无需生调（上柜）<p>
	* 1:需生调
	*/
	public String getRReportFlag()
	{
		return RReportFlag;
	}
	public void setRReportFlag(String aRReportFlag)
	{
		RReportFlag = aRReportFlag;
	}
	/**
	* 0:无需生调（上柜）<p>
	* 1:需生调
	*/
	public String getComeFlag()
	{
		return ComeFlag;
	}
	public void setComeFlag(String aComeFlag)
	{
		ComeFlag = aComeFlag;
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
	* 使用另外一个 LLJSGetDrawSchema 对象给 Schema 赋值
	* @param: aLLJSGetDrawSchema LLJSGetDrawSchema
	**/
	public void setSchema(LLJSGetDrawSchema aLLJSGetDrawSchema)
	{
		this.ClmNo = aLLJSGetDrawSchema.getClmNo();
		this.GetNoticeNo = aLLJSGetDrawSchema.getGetNoticeNo();
		this.PolNo = aLLJSGetDrawSchema.getPolNo();
		this.DutyCode = aLLJSGetDrawSchema.getDutyCode();
		this.GetDutyKind = aLLJSGetDrawSchema.getGetDutyKind();
		this.GetDutyCode = aLLJSGetDrawSchema.getGetDutyCode();
		this.GrpPolNo = aLLJSGetDrawSchema.getGrpPolNo();
		this.GrpContNo = aLLJSGetDrawSchema.getGrpContNo();
		this.ContNo = aLLJSGetDrawSchema.getContNo();
		this.AppntNo = aLLJSGetDrawSchema.getAppntNo();
		this.InsuredNo = aLLJSGetDrawSchema.getInsuredNo();
		this.GetDate = fDate.getDate( aLLJSGetDrawSchema.getGetDate());
		this.GetMoney = aLLJSGetDrawSchema.getGetMoney();
		this.EnterAccDate = fDate.getDate( aLLJSGetDrawSchema.getEnterAccDate());
		this.ConfDate = fDate.getDate( aLLJSGetDrawSchema.getConfDate());
		this.FeeOperationType = aLLJSGetDrawSchema.getFeeOperationType();
		this.FeeFinaType = aLLJSGetDrawSchema.getFeeFinaType();
		this.KindCode = aLLJSGetDrawSchema.getKindCode();
		this.RiskCode = aLLJSGetDrawSchema.getRiskCode();
		this.RiskVersion = aLLJSGetDrawSchema.getRiskVersion();
		this.ManageCom = aLLJSGetDrawSchema.getManageCom();
		this.AgentCom = aLLJSGetDrawSchema.getAgentCom();
		this.AgentType = aLLJSGetDrawSchema.getAgentType();
		this.AgentCode = aLLJSGetDrawSchema.getAgentCode();
		this.AgentGroup = aLLJSGetDrawSchema.getAgentGroup();
		this.GrpName = aLLJSGetDrawSchema.getGrpName();
		this.Handler = aLLJSGetDrawSchema.getHandler();
		this.LastGettoDate = fDate.getDate( aLLJSGetDrawSchema.getLastGettoDate());
		this.CurGetToDate = fDate.getDate( aLLJSGetDrawSchema.getCurGetToDate());
		this.GetFirstFlag = aLLJSGetDrawSchema.getGetFirstFlag();
		this.GetChannelFlag = aLLJSGetDrawSchema.getGetChannelFlag();
		this.DestrayFlag = aLLJSGetDrawSchema.getDestrayFlag();
		this.PolType = aLLJSGetDrawSchema.getPolType();
		this.ApproveCode = aLLJSGetDrawSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLLJSGetDrawSchema.getApproveDate());
		this.ApproveTime = aLLJSGetDrawSchema.getApproveTime();
		this.SerialNo = aLLJSGetDrawSchema.getSerialNo();
		this.Operator = aLLJSGetDrawSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLJSGetDrawSchema.getMakeDate());
		this.MakeTime = aLLJSGetDrawSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLJSGetDrawSchema.getModifyDate());
		this.ModifyTime = aLLJSGetDrawSchema.getModifyTime();
		this.RReportFlag = aLLJSGetDrawSchema.getRReportFlag();
		this.ComeFlag = aLLJSGetDrawSchema.getComeFlag();
		this.Currency = aLLJSGetDrawSchema.getCurrency();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			this.GetDate = rs.getDate("GetDate");
			this.GetMoney = rs.getDouble("GetMoney");
			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("FeeOperationType") == null )
				this.FeeOperationType = null;
			else
				this.FeeOperationType = rs.getString("FeeOperationType").trim();

			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

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

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			this.LastGettoDate = rs.getDate("LastGettoDate");
			this.CurGetToDate = rs.getDate("CurGetToDate");
			if( rs.getString("GetFirstFlag") == null )
				this.GetFirstFlag = null;
			else
				this.GetFirstFlag = rs.getString("GetFirstFlag").trim();

			if( rs.getString("GetChannelFlag") == null )
				this.GetChannelFlag = null;
			else
				this.GetChannelFlag = rs.getString("GetChannelFlag").trim();

			if( rs.getString("DestrayFlag") == null )
				this.DestrayFlag = null;
			else
				this.DestrayFlag = rs.getString("DestrayFlag").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

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

			if( rs.getString("RReportFlag") == null )
				this.RReportFlag = null;
			else
				this.RReportFlag = rs.getString("RReportFlag").trim();

			if( rs.getString("ComeFlag") == null )
				this.ComeFlag = null;
			else
				this.ComeFlag = rs.getString("ComeFlag").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLJSGetDraw表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLJSGetDrawSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLJSGetDrawSchema getSchema()
	{
		LLJSGetDrawSchema aLLJSGetDrawSchema = new LLJSGetDrawSchema();
		aLLJSGetDrawSchema.setSchema(this);
		return aLLJSGetDrawSchema;
	}

	public LLJSGetDrawDB getDB()
	{
		LLJSGetDrawDB aDBOper = new LLJSGetDrawDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLJSGetDraw描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastGettoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurGetToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFirstFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetChannelFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestrayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RReportFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLJSGetDraw>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			FeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			LastGettoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			CurGetToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			GetFirstFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			GetChannelFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			DestrayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			RReportFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ComeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLJSGetDrawSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeOperationType));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
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
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("LastGettoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastGettoDate()));
		}
		if (FCode.equalsIgnoreCase("CurGetToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurGetToDate()));
		}
		if (FCode.equalsIgnoreCase("GetFirstFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFirstFlag));
		}
		if (FCode.equalsIgnoreCase("GetChannelFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetChannelFlag));
		}
		if (FCode.equalsIgnoreCase("DestrayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestrayFlag));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
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
		if (FCode.equalsIgnoreCase("RReportFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RReportFlag));
		}
		if (FCode.equalsIgnoreCase("ComeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComeFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetDate()));
				break;
			case 12:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(FeeOperationType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
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
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastGettoDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurGetToDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(GetFirstFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(GetChannelFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(DestrayFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(RReportFlag);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ComeFlag);
				break;
			case 44:
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equalsIgnoreCase("GetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetDate = fDate.getDate( FValue );
			}
			else
				GetDate = null;
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
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
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
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
		if (FCode.equalsIgnoreCase("LastGettoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastGettoDate = fDate.getDate( FValue );
			}
			else
				LastGettoDate = null;
		}
		if (FCode.equalsIgnoreCase("CurGetToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurGetToDate = fDate.getDate( FValue );
			}
			else
				CurGetToDate = null;
		}
		if (FCode.equalsIgnoreCase("GetFirstFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFirstFlag = FValue.trim();
			}
			else
				GetFirstFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetChannelFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetChannelFlag = FValue.trim();
			}
			else
				GetChannelFlag = null;
		}
		if (FCode.equalsIgnoreCase("DestrayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestrayFlag = FValue.trim();
			}
			else
				DestrayFlag = null;
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
		if (FCode.equalsIgnoreCase("RReportFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RReportFlag = FValue.trim();
			}
			else
				RReportFlag = null;
		}
		if (FCode.equalsIgnoreCase("ComeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComeFlag = FValue.trim();
			}
			else
				ComeFlag = null;
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
		LLJSGetDrawSchema other = (LLJSGetDrawSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& AppntNo.equals(other.getAppntNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& fDate.getString(GetDate).equals(other.getGetDate())
			&& GetMoney == other.getGetMoney()
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& FeeOperationType.equals(other.getFeeOperationType())
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& GrpName.equals(other.getGrpName())
			&& Handler.equals(other.getHandler())
			&& fDate.getString(LastGettoDate).equals(other.getLastGettoDate())
			&& fDate.getString(CurGetToDate).equals(other.getCurGetToDate())
			&& GetFirstFlag.equals(other.getGetFirstFlag())
			&& GetChannelFlag.equals(other.getGetChannelFlag())
			&& DestrayFlag.equals(other.getDestrayFlag())
			&& PolType.equals(other.getPolType())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& SerialNo.equals(other.getSerialNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& RReportFlag.equals(other.getRReportFlag())
			&& ComeFlag.equals(other.getComeFlag())
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 6;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 7;
		}
		if( strFieldName.equals("ContNo") ) {
			return 8;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 9;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 10;
		}
		if( strFieldName.equals("GetDate") ) {
			return 11;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 12;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 13;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 14;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return 15;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 16;
		}
		if( strFieldName.equals("KindCode") ) {
			return 17;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 18;
		}
		if( strFieldName.equals("RiskVersion") ) {
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
		if( strFieldName.equals("AgentCode") ) {
			return 23;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 24;
		}
		if( strFieldName.equals("GrpName") ) {
			return 25;
		}
		if( strFieldName.equals("Handler") ) {
			return 26;
		}
		if( strFieldName.equals("LastGettoDate") ) {
			return 27;
		}
		if( strFieldName.equals("CurGetToDate") ) {
			return 28;
		}
		if( strFieldName.equals("GetFirstFlag") ) {
			return 29;
		}
		if( strFieldName.equals("GetChannelFlag") ) {
			return 30;
		}
		if( strFieldName.equals("DestrayFlag") ) {
			return 31;
		}
		if( strFieldName.equals("PolType") ) {
			return 32;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 33;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 34;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 35;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 36;
		}
		if( strFieldName.equals("Operator") ) {
			return 37;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 38;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 39;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 40;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 41;
		}
		if( strFieldName.equals("RReportFlag") ) {
			return 42;
		}
		if( strFieldName.equals("ComeFlag") ) {
			return 43;
		}
		if( strFieldName.equals("Currency") ) {
			return 44;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "GetNoticeNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "DutyCode";
				break;
			case 4:
				strFieldName = "GetDutyKind";
				break;
			case 5:
				strFieldName = "GetDutyCode";
				break;
			case 6:
				strFieldName = "GrpPolNo";
				break;
			case 7:
				strFieldName = "GrpContNo";
				break;
			case 8:
				strFieldName = "ContNo";
				break;
			case 9:
				strFieldName = "AppntNo";
				break;
			case 10:
				strFieldName = "InsuredNo";
				break;
			case 11:
				strFieldName = "GetDate";
				break;
			case 12:
				strFieldName = "GetMoney";
				break;
			case 13:
				strFieldName = "EnterAccDate";
				break;
			case 14:
				strFieldName = "ConfDate";
				break;
			case 15:
				strFieldName = "FeeOperationType";
				break;
			case 16:
				strFieldName = "FeeFinaType";
				break;
			case 17:
				strFieldName = "KindCode";
				break;
			case 18:
				strFieldName = "RiskCode";
				break;
			case 19:
				strFieldName = "RiskVersion";
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
				strFieldName = "AgentCode";
				break;
			case 24:
				strFieldName = "AgentGroup";
				break;
			case 25:
				strFieldName = "GrpName";
				break;
			case 26:
				strFieldName = "Handler";
				break;
			case 27:
				strFieldName = "LastGettoDate";
				break;
			case 28:
				strFieldName = "CurGetToDate";
				break;
			case 29:
				strFieldName = "GetFirstFlag";
				break;
			case 30:
				strFieldName = "GetChannelFlag";
				break;
			case 31:
				strFieldName = "DestrayFlag";
				break;
			case 32:
				strFieldName = "PolType";
				break;
			case 33:
				strFieldName = "ApproveCode";
				break;
			case 34:
				strFieldName = "ApproveDate";
				break;
			case 35:
				strFieldName = "ApproveTime";
				break;
			case 36:
				strFieldName = "SerialNo";
				break;
			case 37:
				strFieldName = "Operator";
				break;
			case 38:
				strFieldName = "MakeDate";
				break;
			case 39:
				strFieldName = "MakeTime";
				break;
			case 40:
				strFieldName = "ModifyDate";
				break;
			case 41:
				strFieldName = "ModifyTime";
				break;
			case 42:
				strFieldName = "RReportFlag";
				break;
			case 43:
				strFieldName = "ComeFlag";
				break;
			case 44:
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
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
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastGettoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurGetToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetFirstFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetChannelFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestrayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
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
		if( strFieldName.equals("RReportFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComeFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
