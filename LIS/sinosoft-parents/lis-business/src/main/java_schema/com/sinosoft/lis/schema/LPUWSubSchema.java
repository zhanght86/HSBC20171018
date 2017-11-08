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
import com.sinosoft.lis.db.LPUWSubDB;

/*
 * <p>ClassName: LPUWSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全流程
 */
public class LPUWSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPUWSubSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 保单号码 */
	private String PolNo;
	/** 投保单号码 */
	private String ProposalNo;
	/** 核保顺序号 */
	private int UWNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 核保通过标志 */
	private String PassFlag;
	/** 核保级别 */
	private String UWGrade;
	/** 申请级别 */
	private String AppGrade;
	/** 延至日 */
	private String PostponeDay;
	/** 延至日期 */
	private Date PostponeDate;
	/** 是否自动核保 */
	private String AutoUWFlag;
	/** 状态 */
	private String State;
	/** 管理机构 */
	private String ManageCom;
	/** 核保意见 */
	private String UWIdea;
	/** 上报内容 */
	private String UpReportContent;
	/** 操作员 */
	private String Operator;
	/** 是否体检件 */
	private String HealthFlag;
	/** 是否问题件 */
	private String QuesFlag;
	/** 特约标志 */
	private String SpecFlag;
	/** 加费标志 */
	private String AddPremFlag;
	/** 加费原因 */
	private String AddPremReason;
	/** 生调标志 */
	private String ReportFlag;
	/** 核保通知书打印标记 */
	private String PrintFlag;
	/** 业务员通知书打印标记 */
	private String PrintFlag2;
	/** 保险计划变更标记 */
	private String ChangePolFlag;
	/** 保险计划变更原因 */
	private String ChangePolReason;
	/** 特约原因 */
	private String SpecReason;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 建议核保结论 */
	private String SugPassFlag;
	/** 建议核保意见 */
	private String SugUWIdea;

	public static final int FIELDNUM = 42;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPUWSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "PolNo";
		pk[3] = "UWNo";

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
		LPUWSubSchema cloned = (LPUWSubSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
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
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	public int getUWNo()
	{
		return UWNo;
	}
	public void setUWNo(int aUWNo)
	{
		UWNo = aUWNo;
	}
	public void setUWNo(String aUWNo)
	{
		if (aUWNo != null && !aUWNo.equals(""))
		{
			Integer tInteger = new Integer(aUWNo);
			int i = tInteger.intValue();
			UWNo = i;
		}
	}

	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
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
	* 0 未核保<p>
	* 1 拒保<p>
	* 2 延期<p>
	* 3 加费承保<p>
	* 4 特约承保<p>
	* 5 自核未通过<p>
	* 9 标准承保<p>
	* a 撤保<p>
	* <p>
	* 对应LDCode.CodeType = 'edoruwstate'
	*/
	public String getPassFlag()
	{
		return PassFlag;
	}
	public void setPassFlag(String aPassFlag)
	{
		PassFlag = aPassFlag;
	}
	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		UWGrade = aUWGrade;
	}
	public String getAppGrade()
	{
		return AppGrade;
	}
	public void setAppGrade(String aAppGrade)
	{
		AppGrade = aAppGrade;
	}
	public String getPostponeDay()
	{
		return PostponeDay;
	}
	public void setPostponeDay(String aPostponeDay)
	{
		PostponeDay = aPostponeDay;
	}
	public String getPostponeDate()
	{
		if( PostponeDate != null )
			return fDate.getString(PostponeDate);
		else
			return null;
	}
	public void setPostponeDate(Date aPostponeDate)
	{
		PostponeDate = aPostponeDate;
	}
	public void setPostponeDate(String aPostponeDate)
	{
		if (aPostponeDate != null && !aPostponeDate.equals("") )
		{
			PostponeDate = fDate.getDate( aPostponeDate );
		}
		else
			PostponeDate = null;
	}

	/**
	* 1 ---自动核保<p>
	* 2 ---人工核保
	*/
	public String getAutoUWFlag()
	{
		return AutoUWFlag;
	}
	public void setAutoUWFlag(String aAutoUWFlag)
	{
		AutoUWFlag = aAutoUWFlag;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getUWIdea()
	{
		return UWIdea;
	}
	public void setUWIdea(String aUWIdea)
	{
		UWIdea = aUWIdea;
	}
	public String getUpReportContent()
	{
		return UpReportContent;
	}
	public void setUpReportContent(String aUpReportContent)
	{
		UpReportContent = aUpReportContent;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，但还没有打印，没有回复<p>
	* 2 ---是，而且已经打印没有回复<p>
	* 3 ---是，而且已经打印已经回复
	*/
	public String getHealthFlag()
	{
		return HealthFlag;
	}
	public void setHealthFlag(String aHealthFlag)
	{
		HealthFlag = aHealthFlag;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，但是没有回复<p>
	* 2 ---是，而且已经回复
	*/
	public String getQuesFlag()
	{
		return QuesFlag;
	}
	public void setQuesFlag(String aQuesFlag)
	{
		QuesFlag = aQuesFlag;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，未发核保通知书<p>
	* 2 ---是，已发核保通知书<p>
	* 3 ---是，已打印核保通知书
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		SpecFlag = aSpecFlag;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，未发核保通知书<p>
	* 2 ---是，已发核保通知书<p>
	* 3 ---是，已打印核保通知书
	*/
	public String getAddPremFlag()
	{
		return AddPremFlag;
	}
	public void setAddPremFlag(String aAddPremFlag)
	{
		AddPremFlag = aAddPremFlag;
	}
	public String getAddPremReason()
	{
		return AddPremReason;
	}
	public void setAddPremReason(String aAddPremReason)
	{
		AddPremReason = aAddPremReason;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，但是没有回复<p>
	* 2 ---是，而且已经回复
	*/
	public String getReportFlag()
	{
		return ReportFlag;
	}
	public void setReportFlag(String aReportFlag)
	{
		ReportFlag = aReportFlag;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，发核保通知书未打印<p>
	* 2 ---是，而且已经打印没有回复<p>
	* 3 ---是，而且已经打印已经回复
	*/
	public String getPrintFlag()
	{
		return PrintFlag;
	}
	public void setPrintFlag(String aPrintFlag)
	{
		PrintFlag = aPrintFlag;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，发核保通知书未打印<p>
	* 2 ---是，而且已经打印没有回复<p>
	* 3 ---是，而且已经打印已经回复
	*/
	public String getPrintFlag2()
	{
		return PrintFlag2;
	}
	public void setPrintFlag2(String aPrintFlag2)
	{
		PrintFlag2 = aPrintFlag2;
	}
	/**
	* 0 ---非<p>
	* 1 ---是，但是没有回复<p>
	* 2 ---是，而且已经回复
	*/
	public String getChangePolFlag()
	{
		return ChangePolFlag;
	}
	public void setChangePolFlag(String aChangePolFlag)
	{
		ChangePolFlag = aChangePolFlag;
	}
	public String getChangePolReason()
	{
		return ChangePolReason;
	}
	public void setChangePolReason(String aChangePolReason)
	{
		ChangePolReason = aChangePolReason;
	}
	public String getSpecReason()
	{
		return SpecReason;
	}
	public void setSpecReason(String aSpecReason)
	{
		SpecReason = aSpecReason;
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
	public String getSugPassFlag()
	{
		return SugPassFlag;
	}
	public void setSugPassFlag(String aSugPassFlag)
	{
		SugPassFlag = aSugPassFlag;
	}
	public String getSugUWIdea()
	{
		return SugUWIdea;
	}
	public void setSugUWIdea(String aSugUWIdea)
	{
		SugUWIdea = aSugUWIdea;
	}

	/**
	* 使用另外一个 LPUWSubSchema 对象给 Schema 赋值
	* @param: aLPUWSubSchema LPUWSubSchema
	**/
	public void setSchema(LPUWSubSchema aLPUWSubSchema)
	{
		this.EdorNo = aLPUWSubSchema.getEdorNo();
		this.EdorType = aLPUWSubSchema.getEdorType();
		this.GrpContNo = aLPUWSubSchema.getGrpContNo();
		this.ContNo = aLPUWSubSchema.getContNo();
		this.ProposalContNo = aLPUWSubSchema.getProposalContNo();
		this.PolNo = aLPUWSubSchema.getPolNo();
		this.ProposalNo = aLPUWSubSchema.getProposalNo();
		this.UWNo = aLPUWSubSchema.getUWNo();
		this.InsuredNo = aLPUWSubSchema.getInsuredNo();
		this.InsuredName = aLPUWSubSchema.getInsuredName();
		this.AppntNo = aLPUWSubSchema.getAppntNo();
		this.AppntName = aLPUWSubSchema.getAppntName();
		this.AgentCode = aLPUWSubSchema.getAgentCode();
		this.AgentGroup = aLPUWSubSchema.getAgentGroup();
		this.PassFlag = aLPUWSubSchema.getPassFlag();
		this.UWGrade = aLPUWSubSchema.getUWGrade();
		this.AppGrade = aLPUWSubSchema.getAppGrade();
		this.PostponeDay = aLPUWSubSchema.getPostponeDay();
		this.PostponeDate = fDate.getDate( aLPUWSubSchema.getPostponeDate());
		this.AutoUWFlag = aLPUWSubSchema.getAutoUWFlag();
		this.State = aLPUWSubSchema.getState();
		this.ManageCom = aLPUWSubSchema.getManageCom();
		this.UWIdea = aLPUWSubSchema.getUWIdea();
		this.UpReportContent = aLPUWSubSchema.getUpReportContent();
		this.Operator = aLPUWSubSchema.getOperator();
		this.HealthFlag = aLPUWSubSchema.getHealthFlag();
		this.QuesFlag = aLPUWSubSchema.getQuesFlag();
		this.SpecFlag = aLPUWSubSchema.getSpecFlag();
		this.AddPremFlag = aLPUWSubSchema.getAddPremFlag();
		this.AddPremReason = aLPUWSubSchema.getAddPremReason();
		this.ReportFlag = aLPUWSubSchema.getReportFlag();
		this.PrintFlag = aLPUWSubSchema.getPrintFlag();
		this.PrintFlag2 = aLPUWSubSchema.getPrintFlag2();
		this.ChangePolFlag = aLPUWSubSchema.getChangePolFlag();
		this.ChangePolReason = aLPUWSubSchema.getChangePolReason();
		this.SpecReason = aLPUWSubSchema.getSpecReason();
		this.MakeDate = fDate.getDate( aLPUWSubSchema.getMakeDate());
		this.MakeTime = aLPUWSubSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPUWSubSchema.getModifyDate());
		this.ModifyTime = aLPUWSubSchema.getModifyTime();
		this.SugPassFlag = aLPUWSubSchema.getSugPassFlag();
		this.SugUWIdea = aLPUWSubSchema.getSugUWIdea();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			this.UWNo = rs.getInt("UWNo");
			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("PassFlag") == null )
				this.PassFlag = null;
			else
				this.PassFlag = rs.getString("PassFlag").trim();

			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

			if( rs.getString("AppGrade") == null )
				this.AppGrade = null;
			else
				this.AppGrade = rs.getString("AppGrade").trim();

			if( rs.getString("PostponeDay") == null )
				this.PostponeDay = null;
			else
				this.PostponeDay = rs.getString("PostponeDay").trim();

			this.PostponeDate = rs.getDate("PostponeDate");
			if( rs.getString("AutoUWFlag") == null )
				this.AutoUWFlag = null;
			else
				this.AutoUWFlag = rs.getString("AutoUWFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("UWIdea") == null )
				this.UWIdea = null;
			else
				this.UWIdea = rs.getString("UWIdea").trim();

			if( rs.getString("UpReportContent") == null )
				this.UpReportContent = null;
			else
				this.UpReportContent = rs.getString("UpReportContent").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("HealthFlag") == null )
				this.HealthFlag = null;
			else
				this.HealthFlag = rs.getString("HealthFlag").trim();

			if( rs.getString("QuesFlag") == null )
				this.QuesFlag = null;
			else
				this.QuesFlag = rs.getString("QuesFlag").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("AddPremFlag") == null )
				this.AddPremFlag = null;
			else
				this.AddPremFlag = rs.getString("AddPremFlag").trim();

			if( rs.getString("AddPremReason") == null )
				this.AddPremReason = null;
			else
				this.AddPremReason = rs.getString("AddPremReason").trim();

			if( rs.getString("ReportFlag") == null )
				this.ReportFlag = null;
			else
				this.ReportFlag = rs.getString("ReportFlag").trim();

			if( rs.getString("PrintFlag") == null )
				this.PrintFlag = null;
			else
				this.PrintFlag = rs.getString("PrintFlag").trim();

			if( rs.getString("PrintFlag2") == null )
				this.PrintFlag2 = null;
			else
				this.PrintFlag2 = rs.getString("PrintFlag2").trim();

			if( rs.getString("ChangePolFlag") == null )
				this.ChangePolFlag = null;
			else
				this.ChangePolFlag = rs.getString("ChangePolFlag").trim();

			if( rs.getString("ChangePolReason") == null )
				this.ChangePolReason = null;
			else
				this.ChangePolReason = rs.getString("ChangePolReason").trim();

			if( rs.getString("SpecReason") == null )
				this.SpecReason = null;
			else
				this.SpecReason = rs.getString("SpecReason").trim();

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

			if( rs.getString("SugPassFlag") == null )
				this.SugPassFlag = null;
			else
				this.SugPassFlag = rs.getString("SugPassFlag").trim();

			if( rs.getString("SugUWIdea") == null )
				this.SugUWIdea = null;
			else
				this.SugUWIdea = rs.getString("SugUWIdea").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPUWSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPUWSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPUWSubSchema getSchema()
	{
		LPUWSubSchema aLPUWSubSchema = new LPUWSubSchema();
		aLPUWSubSchema.setSchema(this);
		return aLPUWSubSchema;
	}

	public LPUWSubDB getDB()
	{
		LPUWSubDB aDBOper = new LPUWSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPUWSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PostponeDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PostponeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoUWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpReportContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuesFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddPremFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddPremReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChangePolFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChangePolReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SugPassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SugUWIdea));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPUWSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UWNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AppGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PostponeDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PostponeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			AutoUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			UWIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			UpReportContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			HealthFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			QuesFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AddPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			AddPremReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ReportFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PrintFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			PrintFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ChangePolFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ChangePolReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			SpecReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			SugPassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			SugUWIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPUWSubSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PassFlag));
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
		}
		if (FCode.equalsIgnoreCase("AppGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppGrade));
		}
		if (FCode.equalsIgnoreCase("PostponeDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostponeDay));
		}
		if (FCode.equalsIgnoreCase("PostponeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPostponeDate()));
		}
		if (FCode.equalsIgnoreCase("AutoUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoUWFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWIdea));
		}
		if (FCode.equalsIgnoreCase("UpReportContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpReportContent));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("HealthFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthFlag));
		}
		if (FCode.equalsIgnoreCase("QuesFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuesFlag));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("AddPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPremFlag));
		}
		if (FCode.equalsIgnoreCase("AddPremReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPremReason));
		}
		if (FCode.equalsIgnoreCase("ReportFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportFlag));
		}
		if (FCode.equalsIgnoreCase("PrintFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintFlag));
		}
		if (FCode.equalsIgnoreCase("PrintFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintFlag2));
		}
		if (FCode.equalsIgnoreCase("ChangePolFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangePolFlag));
		}
		if (FCode.equalsIgnoreCase("ChangePolReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangePolReason));
		}
		if (FCode.equalsIgnoreCase("SpecReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecReason));
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
		if (FCode.equalsIgnoreCase("SugPassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SugPassFlag));
		}
		if (FCode.equalsIgnoreCase("SugUWIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SugUWIdea));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 7:
				strFieldValue = String.valueOf(UWNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PassFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AppGrade);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(PostponeDay);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPostponeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AutoUWFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(UWIdea);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(UpReportContent);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(HealthFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(QuesFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AddPremFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AddPremReason);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ReportFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(PrintFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(PrintFlag2);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ChangePolFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ChangePolReason);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(SpecReason);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(SugPassFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(SugUWIdea);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
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
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWNo = i;
			}
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
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
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
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
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
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PassFlag = FValue.trim();
			}
			else
				PassFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
		}
		if (FCode.equalsIgnoreCase("AppGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppGrade = FValue.trim();
			}
			else
				AppGrade = null;
		}
		if (FCode.equalsIgnoreCase("PostponeDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostponeDay = FValue.trim();
			}
			else
				PostponeDay = null;
		}
		if (FCode.equalsIgnoreCase("PostponeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PostponeDate = fDate.getDate( FValue );
			}
			else
				PostponeDate = null;
		}
		if (FCode.equalsIgnoreCase("AutoUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoUWFlag = FValue.trim();
			}
			else
				AutoUWFlag = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWIdea = FValue.trim();
			}
			else
				UWIdea = null;
		}
		if (FCode.equalsIgnoreCase("UpReportContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpReportContent = FValue.trim();
			}
			else
				UpReportContent = null;
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
		if (FCode.equalsIgnoreCase("HealthFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthFlag = FValue.trim();
			}
			else
				HealthFlag = null;
		}
		if (FCode.equalsIgnoreCase("QuesFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuesFlag = FValue.trim();
			}
			else
				QuesFlag = null;
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecFlag = FValue.trim();
			}
			else
				SpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("AddPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddPremFlag = FValue.trim();
			}
			else
				AddPremFlag = null;
		}
		if (FCode.equalsIgnoreCase("AddPremReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddPremReason = FValue.trim();
			}
			else
				AddPremReason = null;
		}
		if (FCode.equalsIgnoreCase("ReportFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportFlag = FValue.trim();
			}
			else
				ReportFlag = null;
		}
		if (FCode.equalsIgnoreCase("PrintFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintFlag = FValue.trim();
			}
			else
				PrintFlag = null;
		}
		if (FCode.equalsIgnoreCase("PrintFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintFlag2 = FValue.trim();
			}
			else
				PrintFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("ChangePolFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChangePolFlag = FValue.trim();
			}
			else
				ChangePolFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChangePolReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChangePolReason = FValue.trim();
			}
			else
				ChangePolReason = null;
		}
		if (FCode.equalsIgnoreCase("SpecReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecReason = FValue.trim();
			}
			else
				SpecReason = null;
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
		if (FCode.equalsIgnoreCase("SugPassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SugPassFlag = FValue.trim();
			}
			else
				SugPassFlag = null;
		}
		if (FCode.equalsIgnoreCase("SugUWIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SugUWIdea = FValue.trim();
			}
			else
				SugUWIdea = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPUWSubSchema other = (LPUWSubSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PolNo.equals(other.getPolNo())
			&& ProposalNo.equals(other.getProposalNo())
			&& UWNo == other.getUWNo()
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& PassFlag.equals(other.getPassFlag())
			&& UWGrade.equals(other.getUWGrade())
			&& AppGrade.equals(other.getAppGrade())
			&& PostponeDay.equals(other.getPostponeDay())
			&& fDate.getString(PostponeDate).equals(other.getPostponeDate())
			&& AutoUWFlag.equals(other.getAutoUWFlag())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& UWIdea.equals(other.getUWIdea())
			&& UpReportContent.equals(other.getUpReportContent())
			&& Operator.equals(other.getOperator())
			&& HealthFlag.equals(other.getHealthFlag())
			&& QuesFlag.equals(other.getQuesFlag())
			&& SpecFlag.equals(other.getSpecFlag())
			&& AddPremFlag.equals(other.getAddPremFlag())
			&& AddPremReason.equals(other.getAddPremReason())
			&& ReportFlag.equals(other.getReportFlag())
			&& PrintFlag.equals(other.getPrintFlag())
			&& PrintFlag2.equals(other.getPrintFlag2())
			&& ChangePolFlag.equals(other.getChangePolFlag())
			&& ChangePolReason.equals(other.getChangePolReason())
			&& SpecReason.equals(other.getSpecReason())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& SugPassFlag.equals(other.getSugPassFlag())
			&& SugUWIdea.equals(other.getSugUWIdea());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("PolNo") ) {
			return 5;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 6;
		}
		if( strFieldName.equals("UWNo") ) {
			return 7;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 8;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 9;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 10;
		}
		if( strFieldName.equals("AppntName") ) {
			return 11;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 12;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 13;
		}
		if( strFieldName.equals("PassFlag") ) {
			return 14;
		}
		if( strFieldName.equals("UWGrade") ) {
			return 15;
		}
		if( strFieldName.equals("AppGrade") ) {
			return 16;
		}
		if( strFieldName.equals("PostponeDay") ) {
			return 17;
		}
		if( strFieldName.equals("PostponeDate") ) {
			return 18;
		}
		if( strFieldName.equals("AutoUWFlag") ) {
			return 19;
		}
		if( strFieldName.equals("State") ) {
			return 20;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 21;
		}
		if( strFieldName.equals("UWIdea") ) {
			return 22;
		}
		if( strFieldName.equals("UpReportContent") ) {
			return 23;
		}
		if( strFieldName.equals("Operator") ) {
			return 24;
		}
		if( strFieldName.equals("HealthFlag") ) {
			return 25;
		}
		if( strFieldName.equals("QuesFlag") ) {
			return 26;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 27;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return 28;
		}
		if( strFieldName.equals("AddPremReason") ) {
			return 29;
		}
		if( strFieldName.equals("ReportFlag") ) {
			return 30;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return 31;
		}
		if( strFieldName.equals("PrintFlag2") ) {
			return 32;
		}
		if( strFieldName.equals("ChangePolFlag") ) {
			return 33;
		}
		if( strFieldName.equals("ChangePolReason") ) {
			return 34;
		}
		if( strFieldName.equals("SpecReason") ) {
			return 35;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 36;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 38;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 39;
		}
		if( strFieldName.equals("SugPassFlag") ) {
			return 40;
		}
		if( strFieldName.equals("SugUWIdea") ) {
			return 41;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "PolNo";
				break;
			case 6:
				strFieldName = "ProposalNo";
				break;
			case 7:
				strFieldName = "UWNo";
				break;
			case 8:
				strFieldName = "InsuredNo";
				break;
			case 9:
				strFieldName = "InsuredName";
				break;
			case 10:
				strFieldName = "AppntNo";
				break;
			case 11:
				strFieldName = "AppntName";
				break;
			case 12:
				strFieldName = "AgentCode";
				break;
			case 13:
				strFieldName = "AgentGroup";
				break;
			case 14:
				strFieldName = "PassFlag";
				break;
			case 15:
				strFieldName = "UWGrade";
				break;
			case 16:
				strFieldName = "AppGrade";
				break;
			case 17:
				strFieldName = "PostponeDay";
				break;
			case 18:
				strFieldName = "PostponeDate";
				break;
			case 19:
				strFieldName = "AutoUWFlag";
				break;
			case 20:
				strFieldName = "State";
				break;
			case 21:
				strFieldName = "ManageCom";
				break;
			case 22:
				strFieldName = "UWIdea";
				break;
			case 23:
				strFieldName = "UpReportContent";
				break;
			case 24:
				strFieldName = "Operator";
				break;
			case 25:
				strFieldName = "HealthFlag";
				break;
			case 26:
				strFieldName = "QuesFlag";
				break;
			case 27:
				strFieldName = "SpecFlag";
				break;
			case 28:
				strFieldName = "AddPremFlag";
				break;
			case 29:
				strFieldName = "AddPremReason";
				break;
			case 30:
				strFieldName = "ReportFlag";
				break;
			case 31:
				strFieldName = "PrintFlag";
				break;
			case 32:
				strFieldName = "PrintFlag2";
				break;
			case 33:
				strFieldName = "ChangePolFlag";
				break;
			case 34:
				strFieldName = "ChangePolReason";
				break;
			case 35:
				strFieldName = "SpecReason";
				break;
			case 36:
				strFieldName = "MakeDate";
				break;
			case 37:
				strFieldName = "MakeTime";
				break;
			case 38:
				strFieldName = "ModifyDate";
				break;
			case 39:
				strFieldName = "ModifyTime";
				break;
			case 40:
				strFieldName = "SugPassFlag";
				break;
			case 41:
				strFieldName = "SugUWIdea";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostponeDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PostponeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AutoUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpReportContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuesFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPremReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChangePolFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChangePolReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecReason") ) {
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
		if( strFieldName.equals("SugPassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SugUWIdea") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
