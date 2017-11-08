

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LACommissionRateDB;

/*
 * <p>ClassName: LACommissionRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data _1
 */
public class LACommissionRateSchema implements Schema, Cloneable
{
	// @Field
	/** 流水號 */
	private String IDNo;
	/** 代理機構 */
	private String AgentCom;
	/** 管理機構 */
	private String ManageCom;
	/** 險種代碼 */
	private String RiskCode;
	/** 交費年度 */
	private int PayYear;
	/** 幣種 */
	private String Currency;
	/** 投資分類 */
	private String InvestType;
	/** 繳費方式 */
	private String PayType;
	/** 保障計畫 */
	private String ProtectionPlan;
	/** 交费年期 */
	private String PayYears;
	/** 傭金比例 */
	private double CommRate;
	/** 有效起期 */
	private Date StartDate;
	/** 有效止期 */
	private Date EndDate;
	/** 有效標誌 */
	private String ValidFlag;
	/** 審核狀態 */
	private String AppState;
	/** 審核日期 */
	private Date AppDate;
	/** 審核時間 */
	private String AppTime;
	/** 展業類型 */
	private String Branchtype;
	/** 操作類型 */
	private String ActionType;
	/** 原流水號 */
	private String OrigIDNo;
	/** 操作員 */
	private String Operator;
	/** 入機日期 */
	private Date MakeDate;
	/** 入機時間 */
	private String MakeTime;
	/** 最後修改日期 */
	private Date ModifyDate;
	/** 最後修改時間 */
	private String ModifyTime;
	/** 員工率 */
	private double StaffRate;
	/** 被保人年齡(由) */
	private String InsureAgeStart;
	/** 被保人年齡(至) */
	private String InsureAgeEnd;
	/** 繳至歲數 */
	private String PayToAge;
	/** 默認標誌 */
	private String DefaultFlag;
	/** Srflag */
	private String SRFlag;
	/** Plancode */
	private String PlanCode;
	/** 保障年期 */
	private String Insuyear;
	/** 保障年期标志 */
	private String InsuyearFlag;
	/** 特殊逻辑标志 */
	private String SectionFlag;
	/** Fpd標記 */
	private String FPDFlag;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LACommissionRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "IDNo";

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
		LACommissionRateSchema cloned = (LACommissionRateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	* 1-首年度<p>
	* 2-第二年<p>
	* ...
	*/
	public int getPayYear()
	{
		return PayYear;
	}
	public void setPayYear(int aPayYear)
	{
		PayYear = aPayYear;
	}
	public void setPayYear(String aPayYear)
	{
		if (aPayYear != null && !aPayYear.equals(""))
		{
			Integer tInteger = new Integer(aPayYear);
			int i = tInteger.intValue();
			PayYear = i;
		}
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
	* 1--定期供款<p>
	* 2--定期追加<p>
	* 3--不定期追加
	*/
	public String getInvestType()
	{
		return InvestType;
	}
	public void setInvestType(String aInvestType)
	{
		InvestType = aInvestType;
	}
	/**
	* 定期和非定期
	*/
	public String getPayType()
	{
		return PayType;
	}
	public void setPayType(String aPayType)
	{
		PayType = aPayType;
	}
	/**
	* 01-增長人壽保障<p>
	* 02-均衡人壽保障
	*/
	public String getProtectionPlan()
	{
		return ProtectionPlan;
	}
	public void setProtectionPlan(String aProtectionPlan)
	{
		ProtectionPlan = aProtectionPlan;
	}
	public String getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(String aPayYears)
	{
		PayYears = aPayYears;
	}
	public double getCommRate()
	{
		return CommRate;
	}
	public void setCommRate(double aCommRate)
	{
		CommRate = aCommRate;
	}
	public void setCommRate(String aCommRate)
	{
		if (aCommRate != null && !aCommRate.equals(""))
		{
			Double tDouble = new Double(aCommRate);
			double d = tDouble.doubleValue();
			CommRate = d;
		}
	}

	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
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

	/**
	* 01-有效<p>
	* 02-無效
	*/
	public String getValidFlag()
	{
		return ValidFlag;
	}
	public void setValidFlag(String aValidFlag)
	{
		ValidFlag = aValidFlag;
	}
	/**
	* 01待審核<p>
	* 02-審核通過<p>
	* 03-審核不通過
	*/
	public String getAppState()
	{
		return AppState;
	}
	public void setAppState(String aAppState)
	{
		AppState = aAppState;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		AppTime = aAppTime;
	}
	/**
	* 03、銀行代理<p>
	* 04、TM<p>
	* 08.、經紀代理<p>
	* 11、Other
	*/
	public String getBranchtype()
	{
		return Branchtype;
	}
	public void setBranchtype(String aBranchtype)
	{
		Branchtype = aBranchtype;
	}
	/**
	* 01-新增<p>
	* 02-修改<p>
	* 03-刪除
	*/
	public String getActionType()
	{
		return ActionType;
	}
	public void setActionType(String aActionType)
	{
		ActionType = aActionType;
	}
	public String getOrigIDNo()
	{
		return OrigIDNo;
	}
	public void setOrigIDNo(String aOrigIDNo)
	{
		OrigIDNo = aOrigIDNo;
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
	public double getStaffRate()
	{
		return StaffRate;
	}
	public void setStaffRate(double aStaffRate)
	{
		StaffRate = aStaffRate;
	}
	public void setStaffRate(String aStaffRate)
	{
		if (aStaffRate != null && !aStaffRate.equals(""))
		{
			Double tDouble = new Double(aStaffRate);
			double d = tDouble.doubleValue();
			StaffRate = d;
		}
	}

	public String getInsureAgeStart()
	{
		return InsureAgeStart;
	}
	public void setInsureAgeStart(String aInsureAgeStart)
	{
		InsureAgeStart = aInsureAgeStart;
	}
	public String getInsureAgeEnd()
	{
		return InsureAgeEnd;
	}
	public void setInsureAgeEnd(String aInsureAgeEnd)
	{
		InsureAgeEnd = aInsureAgeEnd;
	}
	public String getPayToAge()
	{
		return PayToAge;
	}
	public void setPayToAge(String aPayToAge)
	{
		PayToAge = aPayToAge;
	}
	/**
	* Y-是<p>
	* N-否
	*/
	public String getDefaultFlag()
	{
		return DefaultFlag;
	}
	public void setDefaultFlag(String aDefaultFlag)
	{
		DefaultFlag = aDefaultFlag;
	}
	public String getSRFlag()
	{
		return SRFlag;
	}
	public void setSRFlag(String aSRFlag)
	{
		SRFlag = aSRFlag;
	}
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		PlanCode = aPlanCode;
	}
	public String getInsuyear()
	{
		return Insuyear;
	}
	public void setInsuyear(String aInsuyear)
	{
		Insuyear = aInsuyear;
	}
	public String getInsuyearFlag()
	{
		return InsuyearFlag;
	}
	public void setInsuyearFlag(String aInsuyearFlag)
	{
		InsuyearFlag = aInsuyearFlag;
	}
	public String getSectionFlag()
	{
		return SectionFlag;
	}
	public void setSectionFlag(String aSectionFlag)
	{
		SectionFlag = aSectionFlag;
	}
	public String getFPDFlag()
	{
		return FPDFlag;
	}
	public void setFPDFlag(String aFPDFlag)
	{
		FPDFlag = aFPDFlag;
	}

	/**
	* 使用另外一个 LACommissionRateSchema 对象给 Schema 赋值
	* @param: aLACommissionRateSchema LACommissionRateSchema
	**/
	public void setSchema(LACommissionRateSchema aLACommissionRateSchema)
	{
		this.IDNo = aLACommissionRateSchema.getIDNo();
		this.AgentCom = aLACommissionRateSchema.getAgentCom();
		this.ManageCom = aLACommissionRateSchema.getManageCom();
		this.RiskCode = aLACommissionRateSchema.getRiskCode();
		this.PayYear = aLACommissionRateSchema.getPayYear();
		this.Currency = aLACommissionRateSchema.getCurrency();
		this.InvestType = aLACommissionRateSchema.getInvestType();
		this.PayType = aLACommissionRateSchema.getPayType();
		this.ProtectionPlan = aLACommissionRateSchema.getProtectionPlan();
		this.PayYears = aLACommissionRateSchema.getPayYears();
		this.CommRate = aLACommissionRateSchema.getCommRate();
		this.StartDate = fDate.getDate( aLACommissionRateSchema.getStartDate());
		this.EndDate = fDate.getDate( aLACommissionRateSchema.getEndDate());
		this.ValidFlag = aLACommissionRateSchema.getValidFlag();
		this.AppState = aLACommissionRateSchema.getAppState();
		this.AppDate = fDate.getDate( aLACommissionRateSchema.getAppDate());
		this.AppTime = aLACommissionRateSchema.getAppTime();
		this.Branchtype = aLACommissionRateSchema.getBranchtype();
		this.ActionType = aLACommissionRateSchema.getActionType();
		this.OrigIDNo = aLACommissionRateSchema.getOrigIDNo();
		this.Operator = aLACommissionRateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLACommissionRateSchema.getMakeDate());
		this.MakeTime = aLACommissionRateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLACommissionRateSchema.getModifyDate());
		this.ModifyTime = aLACommissionRateSchema.getModifyTime();
		this.StaffRate = aLACommissionRateSchema.getStaffRate();
		this.InsureAgeStart = aLACommissionRateSchema.getInsureAgeStart();
		this.InsureAgeEnd = aLACommissionRateSchema.getInsureAgeEnd();
		this.PayToAge = aLACommissionRateSchema.getPayToAge();
		this.DefaultFlag = aLACommissionRateSchema.getDefaultFlag();
		this.SRFlag = aLACommissionRateSchema.getSRFlag();
		this.PlanCode = aLACommissionRateSchema.getPlanCode();
		this.Insuyear = aLACommissionRateSchema.getInsuyear();
		this.InsuyearFlag = aLACommissionRateSchema.getInsuyearFlag();
		this.SectionFlag = aLACommissionRateSchema.getSectionFlag();
		this.FPDFlag = aLACommissionRateSchema.getFPDFlag();
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
			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.PayYear = rs.getInt("PayYear");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("InvestType") == null )
				this.InvestType = null;
			else
				this.InvestType = rs.getString("InvestType").trim();

			if( rs.getString("PayType") == null )
				this.PayType = null;
			else
				this.PayType = rs.getString("PayType").trim();

			if( rs.getString("ProtectionPlan") == null )
				this.ProtectionPlan = null;
			else
				this.ProtectionPlan = rs.getString("ProtectionPlan").trim();

			if( rs.getString("PayYears") == null )
				this.PayYears = null;
			else
				this.PayYears = rs.getString("PayYears").trim();

			this.CommRate = rs.getDouble("CommRate");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("ValidFlag") == null )
				this.ValidFlag = null;
			else
				this.ValidFlag = rs.getString("ValidFlag").trim();

			if( rs.getString("AppState") == null )
				this.AppState = null;
			else
				this.AppState = rs.getString("AppState").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("Branchtype") == null )
				this.Branchtype = null;
			else
				this.Branchtype = rs.getString("Branchtype").trim();

			if( rs.getString("ActionType") == null )
				this.ActionType = null;
			else
				this.ActionType = rs.getString("ActionType").trim();

			if( rs.getString("OrigIDNo") == null )
				this.OrigIDNo = null;
			else
				this.OrigIDNo = rs.getString("OrigIDNo").trim();

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

			this.StaffRate = rs.getDouble("StaffRate");
			if( rs.getString("InsureAgeStart") == null )
				this.InsureAgeStart = null;
			else
				this.InsureAgeStart = rs.getString("InsureAgeStart").trim();

			if( rs.getString("InsureAgeEnd") == null )
				this.InsureAgeEnd = null;
			else
				this.InsureAgeEnd = rs.getString("InsureAgeEnd").trim();

			if( rs.getString("PayToAge") == null )
				this.PayToAge = null;
			else
				this.PayToAge = rs.getString("PayToAge").trim();

			if( rs.getString("DefaultFlag") == null )
				this.DefaultFlag = null;
			else
				this.DefaultFlag = rs.getString("DefaultFlag").trim();

			if( rs.getString("SRFlag") == null )
				this.SRFlag = null;
			else
				this.SRFlag = rs.getString("SRFlag").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("Insuyear") == null )
				this.Insuyear = null;
			else
				this.Insuyear = rs.getString("Insuyear").trim();

			if( rs.getString("InsuyearFlag") == null )
				this.InsuyearFlag = null;
			else
				this.InsuyearFlag = rs.getString("InsuyearFlag").trim();

			if( rs.getString("SectionFlag") == null )
				this.SectionFlag = null;
			else
				this.SectionFlag = rs.getString("SectionFlag").trim();

			if( rs.getString("FPDFlag") == null )
				this.FPDFlag = null;
			else
				this.FPDFlag = rs.getString("FPDFlag").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LACommissionRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommissionRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LACommissionRateSchema getSchema()
	{
		LACommissionRateSchema aLACommissionRateSchema = new LACommissionRateSchema();
		aLACommissionRateSchema.setSchema(this);
		return aLACommissionRateSchema;
	}

	public LACommissionRateDB getDB()
	{
		LACommissionRateDB aDBOper = new LACommissionRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLACommissionRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtectionPlan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayYears)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Branchtype)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrigIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StaffRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsureAgeStart)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsureAgeEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayToAge)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefaultFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SRFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Insuyear)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuyearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SectionFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FPDFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLACommissionRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InvestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ProtectionPlan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PayYears = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CommRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ValidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Branchtype = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			OrigIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StaffRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			InsureAgeStart = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			InsureAgeEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			PayToAge = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			DefaultFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			SRFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Insuyear = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			InsuyearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			SectionFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			FPDFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommissionRateSchema";
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
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYear));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestType));
		}
		if (FCode.equalsIgnoreCase("PayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayType));
		}
		if (FCode.equalsIgnoreCase("ProtectionPlan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtectionPlan));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("CommRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommRate));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidFlag));
		}
		if (FCode.equalsIgnoreCase("AppState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppState));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("Branchtype"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Branchtype));
		}
		if (FCode.equalsIgnoreCase("ActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionType));
		}
		if (FCode.equalsIgnoreCase("OrigIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrigIDNo));
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
		if (FCode.equalsIgnoreCase("StaffRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StaffRate));
		}
		if (FCode.equalsIgnoreCase("InsureAgeStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsureAgeStart));
		}
		if (FCode.equalsIgnoreCase("InsureAgeEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsureAgeEnd));
		}
		if (FCode.equalsIgnoreCase("PayToAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayToAge));
		}
		if (FCode.equalsIgnoreCase("DefaultFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultFlag));
		}
		if (FCode.equalsIgnoreCase("SRFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SRFlag));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("Insuyear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Insuyear));
		}
		if (FCode.equalsIgnoreCase("InsuyearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuyearFlag));
		}
		if (FCode.equalsIgnoreCase("SectionFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SectionFlag));
		}
		if (FCode.equalsIgnoreCase("FPDFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FPDFlag));
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
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = String.valueOf(PayYear);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InvestType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ProtectionPlan);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PayYears);
				break;
			case 10:
				strFieldValue = String.valueOf(CommRate);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ValidFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppState);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Branchtype);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ActionType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(OrigIDNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 25:
				strFieldValue = String.valueOf(StaffRate);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(InsureAgeStart);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(InsureAgeEnd);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(PayToAge);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(DefaultFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(SRFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Insuyear);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(InsuyearFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(SectionFlag);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(FPDFlag);
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

		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYear = i;
			}
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
		if (FCode.equalsIgnoreCase("InvestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestType = FValue.trim();
			}
			else
				InvestType = null;
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
		if (FCode.equalsIgnoreCase("ProtectionPlan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtectionPlan = FValue.trim();
			}
			else
				ProtectionPlan = null;
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayYears = FValue.trim();
			}
			else
				PayYears = null;
		}
		if (FCode.equalsIgnoreCase("CommRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
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
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidFlag = FValue.trim();
			}
			else
				ValidFlag = null;
		}
		if (FCode.equalsIgnoreCase("AppState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppState = FValue.trim();
			}
			else
				AppState = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("Branchtype"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Branchtype = FValue.trim();
			}
			else
				Branchtype = null;
		}
		if (FCode.equalsIgnoreCase("ActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionType = FValue.trim();
			}
			else
				ActionType = null;
		}
		if (FCode.equalsIgnoreCase("OrigIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrigIDNo = FValue.trim();
			}
			else
				OrigIDNo = null;
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
		if (FCode.equalsIgnoreCase("StaffRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StaffRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsureAgeStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsureAgeStart = FValue.trim();
			}
			else
				InsureAgeStart = null;
		}
		if (FCode.equalsIgnoreCase("InsureAgeEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsureAgeEnd = FValue.trim();
			}
			else
				InsureAgeEnd = null;
		}
		if (FCode.equalsIgnoreCase("PayToAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayToAge = FValue.trim();
			}
			else
				PayToAge = null;
		}
		if (FCode.equalsIgnoreCase("DefaultFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefaultFlag = FValue.trim();
			}
			else
				DefaultFlag = null;
		}
		if (FCode.equalsIgnoreCase("SRFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SRFlag = FValue.trim();
			}
			else
				SRFlag = null;
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
		}
		if (FCode.equalsIgnoreCase("Insuyear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Insuyear = FValue.trim();
			}
			else
				Insuyear = null;
		}
		if (FCode.equalsIgnoreCase("InsuyearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuyearFlag = FValue.trim();
			}
			else
				InsuyearFlag = null;
		}
		if (FCode.equalsIgnoreCase("SectionFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SectionFlag = FValue.trim();
			}
			else
				SectionFlag = null;
		}
		if (FCode.equalsIgnoreCase("FPDFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FPDFlag = FValue.trim();
			}
			else
				FPDFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LACommissionRateSchema other = (LACommissionRateSchema)otherObject;
		return
			IDNo.equals(other.getIDNo())
			&& AgentCom.equals(other.getAgentCom())
			&& ManageCom.equals(other.getManageCom())
			&& RiskCode.equals(other.getRiskCode())
			&& PayYear == other.getPayYear()
			&& Currency.equals(other.getCurrency())
			&& InvestType.equals(other.getInvestType())
			&& PayType.equals(other.getPayType())
			&& ProtectionPlan.equals(other.getProtectionPlan())
			&& PayYears.equals(other.getPayYears())
			&& CommRate == other.getCommRate()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& ValidFlag.equals(other.getValidFlag())
			&& AppState.equals(other.getAppState())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& Branchtype.equals(other.getBranchtype())
			&& ActionType.equals(other.getActionType())
			&& OrigIDNo.equals(other.getOrigIDNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StaffRate == other.getStaffRate()
			&& InsureAgeStart.equals(other.getInsureAgeStart())
			&& InsureAgeEnd.equals(other.getInsureAgeEnd())
			&& PayToAge.equals(other.getPayToAge())
			&& DefaultFlag.equals(other.getDefaultFlag())
			&& SRFlag.equals(other.getSRFlag())
			&& PlanCode.equals(other.getPlanCode())
			&& Insuyear.equals(other.getInsuyear())
			&& InsuyearFlag.equals(other.getInsuyearFlag())
			&& SectionFlag.equals(other.getSectionFlag())
			&& FPDFlag.equals(other.getFPDFlag());
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
		if( strFieldName.equals("IDNo") ) {
			return 0;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("PayYear") ) {
			return 4;
		}
		if( strFieldName.equals("Currency") ) {
			return 5;
		}
		if( strFieldName.equals("InvestType") ) {
			return 6;
		}
		if( strFieldName.equals("PayType") ) {
			return 7;
		}
		if( strFieldName.equals("ProtectionPlan") ) {
			return 8;
		}
		if( strFieldName.equals("PayYears") ) {
			return 9;
		}
		if( strFieldName.equals("CommRate") ) {
			return 10;
		}
		if( strFieldName.equals("StartDate") ) {
			return 11;
		}
		if( strFieldName.equals("EndDate") ) {
			return 12;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return 13;
		}
		if( strFieldName.equals("AppState") ) {
			return 14;
		}
		if( strFieldName.equals("AppDate") ) {
			return 15;
		}
		if( strFieldName.equals("AppTime") ) {
			return 16;
		}
		if( strFieldName.equals("Branchtype") ) {
			return 17;
		}
		if( strFieldName.equals("ActionType") ) {
			return 18;
		}
		if( strFieldName.equals("OrigIDNo") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
		}
		if( strFieldName.equals("StaffRate") ) {
			return 25;
		}
		if( strFieldName.equals("InsureAgeStart") ) {
			return 26;
		}
		if( strFieldName.equals("InsureAgeEnd") ) {
			return 27;
		}
		if( strFieldName.equals("PayToAge") ) {
			return 28;
		}
		if( strFieldName.equals("DefaultFlag") ) {
			return 29;
		}
		if( strFieldName.equals("SRFlag") ) {
			return 30;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 31;
		}
		if( strFieldName.equals("Insuyear") ) {
			return 32;
		}
		if( strFieldName.equals("InsuyearFlag") ) {
			return 33;
		}
		if( strFieldName.equals("SectionFlag") ) {
			return 34;
		}
		if( strFieldName.equals("FPDFlag") ) {
			return 35;
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
				strFieldName = "IDNo";
				break;
			case 1:
				strFieldName = "AgentCom";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "PayYear";
				break;
			case 5:
				strFieldName = "Currency";
				break;
			case 6:
				strFieldName = "InvestType";
				break;
			case 7:
				strFieldName = "PayType";
				break;
			case 8:
				strFieldName = "ProtectionPlan";
				break;
			case 9:
				strFieldName = "PayYears";
				break;
			case 10:
				strFieldName = "CommRate";
				break;
			case 11:
				strFieldName = "StartDate";
				break;
			case 12:
				strFieldName = "EndDate";
				break;
			case 13:
				strFieldName = "ValidFlag";
				break;
			case 14:
				strFieldName = "AppState";
				break;
			case 15:
				strFieldName = "AppDate";
				break;
			case 16:
				strFieldName = "AppTime";
				break;
			case 17:
				strFieldName = "Branchtype";
				break;
			case 18:
				strFieldName = "ActionType";
				break;
			case 19:
				strFieldName = "OrigIDNo";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
				break;
			case 25:
				strFieldName = "StaffRate";
				break;
			case 26:
				strFieldName = "InsureAgeStart";
				break;
			case 27:
				strFieldName = "InsureAgeEnd";
				break;
			case 28:
				strFieldName = "PayToAge";
				break;
			case 29:
				strFieldName = "DefaultFlag";
				break;
			case 30:
				strFieldName = "SRFlag";
				break;
			case 31:
				strFieldName = "PlanCode";
				break;
			case 32:
				strFieldName = "Insuyear";
				break;
			case 33:
				strFieldName = "InsuyearFlag";
				break;
			case 34:
				strFieldName = "SectionFlag";
				break;
			case 35:
				strFieldName = "FPDFlag";
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
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProtectionPlan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Branchtype") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrigIDNo") ) {
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
		if( strFieldName.equals("StaffRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsureAgeStart") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsureAgeEnd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayToAge") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SRFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Insuyear") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuyearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SectionFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FPDFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}

