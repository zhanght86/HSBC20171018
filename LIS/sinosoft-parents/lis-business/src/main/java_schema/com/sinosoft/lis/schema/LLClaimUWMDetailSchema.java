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
import com.sinosoft.lis.db.LLClaimUWMDetailDB;

/*
 * <p>ClassName: LLClaimUWMDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUWMDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUWMDetailSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 核赔次数 */
	private String ClmUWNo;
	/** 立案号 */
	private String RgtNo;
	/** 分案号 */
	private String CaseNo;
	/** 核赔员 */
	private String ClmUWer;
	/** 核赔级别 */
	private String ClmUWGrade;
	/** 核赔结论 */
	private String ClmDecision;
	/** 申请级别 */
	private String AppGrade;
	/** 审核类型 */
	private String CheckType;
	/** 申请审核人员 */
	private String AppClmUWer;
	/** 申请阶段 */
	private String AppPhase;
	/** 申请动作 */
	private String AppActionType;
	/** 备注 */
	private String Remark;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String MngCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 审核结论 */
	private String AuditConclusion;
	/** 审核意见 */
	private String AuditIdea;
	/** 审核级别 */
	private String AuditLevel;
	/** 审核人 */
	private String AuditPer;
	/** 审核时间 */
	private Date AuditDate;
	/** 审批结论 */
	private String ExamConclusion;
	/** 审批意见 */
	private String ExamIdea;
	/** 审批级别 */
	private String ExamLevel;
	/** 审批人 */
	private String ExamPer;
	/** 审批时间 */
	private Date ExamDate;
	/** 特殊备注 */
	private String SpecialRemark;
	/** 审批不通过原因 */
	private String ExamNoPassReason;
	/** 审批不通过依据 */
	private String ExamNoPassDesc;
	/** 审核不通过原因 */
	private String AuditNoPassReason;
	/** 审核不通过依据 */
	private String AuditNoPassDesc;
	/** 回退号 */
	private String BackNo;
	/** 审批机构 */
	private String ExamCom;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUWMDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ClmNo";
		pk[1] = "ClmUWNo";

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
		LLClaimUWMDetailSchema cloned = (LLClaimUWMDetailSchema)super.clone();
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
	public String getClmUWNo()
	{
		return ClmUWNo;
	}
	public void setClmUWNo(String aClmUWNo)
	{
		ClmUWNo = aClmUWNo;
	}
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	public String getClmUWer()
	{
		return ClmUWer;
	}
	public void setClmUWer(String aClmUWer)
	{
		ClmUWer = aClmUWer;
	}
	/**
	* 【不用】
	*/
	public String getClmUWGrade()
	{
		return ClmUWGrade;
	}
	public void setClmUWGrade(String aClmUWGrade)
	{
		ClmUWGrade = aClmUWGrade;
	}
	/**
	* 【不用】<p>
	* 包括：上报、正常赔付、通融赔付、拒赔等
	*/
	public String getClmDecision()
	{
		return ClmDecision;
	}
	public void setClmDecision(String aClmDecision)
	{
		ClmDecision = aClmDecision;
	}
	/**
	* 【不用】
	*/
	public String getAppGrade()
	{
		return AppGrade;
	}
	public void setAppGrade(String aAppGrade)
	{
		AppGrade = aAppGrade;
	}
	/**
	* 0--审核结论<p>
	* 1--预付审核结论
	*/
	public String getCheckType()
	{
		return CheckType;
	}
	public void setCheckType(String aCheckType)
	{
		CheckType = aCheckType;
	}
	/**
	* 【不用】
	*/
	public String getAppClmUWer()
	{
		return AppClmUWer;
	}
	public void setAppClmUWer(String aAppClmUWer)
	{
		AppClmUWer = aAppClmUWer;
	}
	/**
	* 0-审核<p>
	* 1-复核
	*/
	public String getAppPhase()
	{
		return AppPhase;
	}
	public void setAppPhase(String aAppPhase)
	{
		AppPhase = aAppPhase;
	}
	/**
	* 【不用】<p>
	* 1 －－ 确认<p>
	* 2 －－ 退回<p>
	* 3 －－ 上报
	*/
	public String getAppActionType()
	{
		return AppActionType;
	}
	public void setAppActionType(String aAppActionType)
	{
		AppActionType = aAppActionType;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	* [当作审核机构使用]
	*/
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
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
	* 0给付<p>
	* 1拒付<p>
	* 2客户撤案<p>
	* 3公司撤案
	*/
	public String getAuditConclusion()
	{
		return AuditConclusion;
	}
	public void setAuditConclusion(String aAuditConclusion)
	{
		AuditConclusion = aAuditConclusion;
	}
	public String getAuditIdea()
	{
		return AuditIdea;
	}
	public void setAuditIdea(String aAuditIdea)
	{
		AuditIdea = aAuditIdea;
	}
	public String getAuditLevel()
	{
		return AuditLevel;
	}
	public void setAuditLevel(String aAuditLevel)
	{
		AuditLevel = aAuditLevel;
	}
	public String getAuditPer()
	{
		return AuditPer;
	}
	public void setAuditPer(String aAuditPer)
	{
		AuditPer = aAuditPer;
	}
	public String getAuditDate()
	{
		if( AuditDate != null )
			return fDate.getString(AuditDate);
		else
			return null;
	}
	public void setAuditDate(Date aAuditDate)
	{
		AuditDate = aAuditDate;
	}
	public void setAuditDate(String aAuditDate)
	{
		if (aAuditDate != null && !aAuditDate.equals("") )
		{
			AuditDate = fDate.getDate( aAuditDate );
		}
		else
			AuditDate = null;
	}

	/**
	* 0通过<p>
	* 1不通过
	*/
	public String getExamConclusion()
	{
		return ExamConclusion;
	}
	public void setExamConclusion(String aExamConclusion)
	{
		ExamConclusion = aExamConclusion;
	}
	public String getExamIdea()
	{
		return ExamIdea;
	}
	public void setExamIdea(String aExamIdea)
	{
		ExamIdea = aExamIdea;
	}
	public String getExamLevel()
	{
		return ExamLevel;
	}
	public void setExamLevel(String aExamLevel)
	{
		ExamLevel = aExamLevel;
	}
	public String getExamPer()
	{
		return ExamPer;
	}
	public void setExamPer(String aExamPer)
	{
		ExamPer = aExamPer;
	}
	public String getExamDate()
	{
		if( ExamDate != null )
			return fDate.getString(ExamDate);
		else
			return null;
	}
	public void setExamDate(Date aExamDate)
	{
		ExamDate = aExamDate;
	}
	public void setExamDate(String aExamDate)
	{
		if (aExamDate != null && !aExamDate.equals("") )
		{
			ExamDate = fDate.getDate( aExamDate );
		}
		else
			ExamDate = null;
	}

	public String getSpecialRemark()
	{
		return SpecialRemark;
	}
	public void setSpecialRemark(String aSpecialRemark)
	{
		SpecialRemark = aSpecialRemark;
	}
	public String getExamNoPassReason()
	{
		return ExamNoPassReason;
	}
	public void setExamNoPassReason(String aExamNoPassReason)
	{
		ExamNoPassReason = aExamNoPassReason;
	}
	public String getExamNoPassDesc()
	{
		return ExamNoPassDesc;
	}
	public void setExamNoPassDesc(String aExamNoPassDesc)
	{
		ExamNoPassDesc = aExamNoPassDesc;
	}
	public String getAuditNoPassReason()
	{
		return AuditNoPassReason;
	}
	public void setAuditNoPassReason(String aAuditNoPassReason)
	{
		AuditNoPassReason = aAuditNoPassReason;
	}
	public String getAuditNoPassDesc()
	{
		return AuditNoPassDesc;
	}
	public void setAuditNoPassDesc(String aAuditNoPassDesc)
	{
		AuditNoPassDesc = aAuditNoPassDesc;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		BackNo = aBackNo;
	}
	public String getExamCom()
	{
		return ExamCom;
	}
	public void setExamCom(String aExamCom)
	{
		ExamCom = aExamCom;
	}

	/**
	* 使用另外一个 LLClaimUWMDetailSchema 对象给 Schema 赋值
	* @param: aLLClaimUWMDetailSchema LLClaimUWMDetailSchema
	**/
	public void setSchema(LLClaimUWMDetailSchema aLLClaimUWMDetailSchema)
	{
		this.ClmNo = aLLClaimUWMDetailSchema.getClmNo();
		this.ClmUWNo = aLLClaimUWMDetailSchema.getClmUWNo();
		this.RgtNo = aLLClaimUWMDetailSchema.getRgtNo();
		this.CaseNo = aLLClaimUWMDetailSchema.getCaseNo();
		this.ClmUWer = aLLClaimUWMDetailSchema.getClmUWer();
		this.ClmUWGrade = aLLClaimUWMDetailSchema.getClmUWGrade();
		this.ClmDecision = aLLClaimUWMDetailSchema.getClmDecision();
		this.AppGrade = aLLClaimUWMDetailSchema.getAppGrade();
		this.CheckType = aLLClaimUWMDetailSchema.getCheckType();
		this.AppClmUWer = aLLClaimUWMDetailSchema.getAppClmUWer();
		this.AppPhase = aLLClaimUWMDetailSchema.getAppPhase();
		this.AppActionType = aLLClaimUWMDetailSchema.getAppActionType();
		this.Remark = aLLClaimUWMDetailSchema.getRemark();
		this.Operator = aLLClaimUWMDetailSchema.getOperator();
		this.MngCom = aLLClaimUWMDetailSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLClaimUWMDetailSchema.getMakeDate());
		this.MakeTime = aLLClaimUWMDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimUWMDetailSchema.getModifyDate());
		this.ModifyTime = aLLClaimUWMDetailSchema.getModifyTime();
		this.AuditConclusion = aLLClaimUWMDetailSchema.getAuditConclusion();
		this.AuditIdea = aLLClaimUWMDetailSchema.getAuditIdea();
		this.AuditLevel = aLLClaimUWMDetailSchema.getAuditLevel();
		this.AuditPer = aLLClaimUWMDetailSchema.getAuditPer();
		this.AuditDate = fDate.getDate( aLLClaimUWMDetailSchema.getAuditDate());
		this.ExamConclusion = aLLClaimUWMDetailSchema.getExamConclusion();
		this.ExamIdea = aLLClaimUWMDetailSchema.getExamIdea();
		this.ExamLevel = aLLClaimUWMDetailSchema.getExamLevel();
		this.ExamPer = aLLClaimUWMDetailSchema.getExamPer();
		this.ExamDate = fDate.getDate( aLLClaimUWMDetailSchema.getExamDate());
		this.SpecialRemark = aLLClaimUWMDetailSchema.getSpecialRemark();
		this.ExamNoPassReason = aLLClaimUWMDetailSchema.getExamNoPassReason();
		this.ExamNoPassDesc = aLLClaimUWMDetailSchema.getExamNoPassDesc();
		this.AuditNoPassReason = aLLClaimUWMDetailSchema.getAuditNoPassReason();
		this.AuditNoPassDesc = aLLClaimUWMDetailSchema.getAuditNoPassDesc();
		this.BackNo = aLLClaimUWMDetailSchema.getBackNo();
		this.ExamCom = aLLClaimUWMDetailSchema.getExamCom();
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

			if( rs.getString("ClmUWNo") == null )
				this.ClmUWNo = null;
			else
				this.ClmUWNo = rs.getString("ClmUWNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("ClmUWer") == null )
				this.ClmUWer = null;
			else
				this.ClmUWer = rs.getString("ClmUWer").trim();

			if( rs.getString("ClmUWGrade") == null )
				this.ClmUWGrade = null;
			else
				this.ClmUWGrade = rs.getString("ClmUWGrade").trim();

			if( rs.getString("ClmDecision") == null )
				this.ClmDecision = null;
			else
				this.ClmDecision = rs.getString("ClmDecision").trim();

			if( rs.getString("AppGrade") == null )
				this.AppGrade = null;
			else
				this.AppGrade = rs.getString("AppGrade").trim();

			if( rs.getString("CheckType") == null )
				this.CheckType = null;
			else
				this.CheckType = rs.getString("CheckType").trim();

			if( rs.getString("AppClmUWer") == null )
				this.AppClmUWer = null;
			else
				this.AppClmUWer = rs.getString("AppClmUWer").trim();

			if( rs.getString("AppPhase") == null )
				this.AppPhase = null;
			else
				this.AppPhase = rs.getString("AppPhase").trim();

			if( rs.getString("AppActionType") == null )
				this.AppActionType = null;
			else
				this.AppActionType = rs.getString("AppActionType").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("AuditConclusion") == null )
				this.AuditConclusion = null;
			else
				this.AuditConclusion = rs.getString("AuditConclusion").trim();

			if( rs.getString("AuditIdea") == null )
				this.AuditIdea = null;
			else
				this.AuditIdea = rs.getString("AuditIdea").trim();

			if( rs.getString("AuditLevel") == null )
				this.AuditLevel = null;
			else
				this.AuditLevel = rs.getString("AuditLevel").trim();

			if( rs.getString("AuditPer") == null )
				this.AuditPer = null;
			else
				this.AuditPer = rs.getString("AuditPer").trim();

			this.AuditDate = rs.getDate("AuditDate");
			if( rs.getString("ExamConclusion") == null )
				this.ExamConclusion = null;
			else
				this.ExamConclusion = rs.getString("ExamConclusion").trim();

			if( rs.getString("ExamIdea") == null )
				this.ExamIdea = null;
			else
				this.ExamIdea = rs.getString("ExamIdea").trim();

			if( rs.getString("ExamLevel") == null )
				this.ExamLevel = null;
			else
				this.ExamLevel = rs.getString("ExamLevel").trim();

			if( rs.getString("ExamPer") == null )
				this.ExamPer = null;
			else
				this.ExamPer = rs.getString("ExamPer").trim();

			this.ExamDate = rs.getDate("ExamDate");
			if( rs.getString("SpecialRemark") == null )
				this.SpecialRemark = null;
			else
				this.SpecialRemark = rs.getString("SpecialRemark").trim();

			if( rs.getString("ExamNoPassReason") == null )
				this.ExamNoPassReason = null;
			else
				this.ExamNoPassReason = rs.getString("ExamNoPassReason").trim();

			if( rs.getString("ExamNoPassDesc") == null )
				this.ExamNoPassDesc = null;
			else
				this.ExamNoPassDesc = rs.getString("ExamNoPassDesc").trim();

			if( rs.getString("AuditNoPassReason") == null )
				this.AuditNoPassReason = null;
			else
				this.AuditNoPassReason = rs.getString("AuditNoPassReason").trim();

			if( rs.getString("AuditNoPassDesc") == null )
				this.AuditNoPassDesc = null;
			else
				this.AuditNoPassDesc = rs.getString("AuditNoPassDesc").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			if( rs.getString("ExamCom") == null )
				this.ExamCom = null;
			else
				this.ExamCom = rs.getString("ExamCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimUWMDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWMDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUWMDetailSchema getSchema()
	{
		LLClaimUWMDetailSchema aLLClaimUWMDetailSchema = new LLClaimUWMDetailSchema();
		aLLClaimUWMDetailSchema.setSchema(this);
		return aLLClaimUWMDetailSchema;
	}

	public LLClaimUWMDetailDB getDB()
	{
		LLClaimUWMDetailDB aDBOper = new LLClaimUWMDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWMDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmDecision)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AuditDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExamDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecialRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamNoPassReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamNoPassDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditNoPassReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditNoPassDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWMDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClmUWNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ClmUWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClmDecision = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CheckType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AppClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AppPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AppActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AuditConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AuditIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AuditLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AuditPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AuditDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ExamConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ExamIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ExamLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ExamPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ExamDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			SpecialRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ExamNoPassReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ExamNoPassDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			AuditNoPassReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AuditNoPassDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ExamCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWMDetailSchema";
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
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWer));
		}
		if (FCode.equalsIgnoreCase("ClmUWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWGrade));
		}
		if (FCode.equalsIgnoreCase("ClmDecision"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmDecision));
		}
		if (FCode.equalsIgnoreCase("AppGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppGrade));
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckType));
		}
		if (FCode.equalsIgnoreCase("AppClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppClmUWer));
		}
		if (FCode.equalsIgnoreCase("AppPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppPhase));
		}
		if (FCode.equalsIgnoreCase("AppActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppActionType));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("AuditConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditConclusion));
		}
		if (FCode.equalsIgnoreCase("AuditIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditIdea));
		}
		if (FCode.equalsIgnoreCase("AuditLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditLevel));
		}
		if (FCode.equalsIgnoreCase("AuditPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditPer));
		}
		if (FCode.equalsIgnoreCase("AuditDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
		}
		if (FCode.equalsIgnoreCase("ExamConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamConclusion));
		}
		if (FCode.equalsIgnoreCase("ExamIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamIdea));
		}
		if (FCode.equalsIgnoreCase("ExamLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamLevel));
		}
		if (FCode.equalsIgnoreCase("ExamPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamPer));
		}
		if (FCode.equalsIgnoreCase("ExamDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getExamDate()));
		}
		if (FCode.equalsIgnoreCase("SpecialRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecialRemark));
		}
		if (FCode.equalsIgnoreCase("ExamNoPassReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamNoPassReason));
		}
		if (FCode.equalsIgnoreCase("ExamNoPassDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamNoPassDesc));
		}
		if (FCode.equalsIgnoreCase("AuditNoPassReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditNoPassReason));
		}
		if (FCode.equalsIgnoreCase("AuditNoPassDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditNoPassDesc));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("ExamCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamCom));
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
				strFieldValue = StrTool.GBKToUnicode(ClmUWNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmUWer);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ClmUWGrade);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClmDecision);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppGrade);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CheckType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppClmUWer);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AppPhase);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AppActionType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AuditConclusion);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AuditIdea);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AuditLevel);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AuditPer);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ExamConclusion);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ExamIdea);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ExamLevel);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ExamPer);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExamDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SpecialRemark);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ExamNoPassReason);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ExamNoPassDesc);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AuditNoPassReason);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AuditNoPassDesc);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ExamCom);
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
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWNo = FValue.trim();
			}
			else
				ClmUWNo = null;
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWer = FValue.trim();
			}
			else
				ClmUWer = null;
		}
		if (FCode.equalsIgnoreCase("ClmUWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWGrade = FValue.trim();
			}
			else
				ClmUWGrade = null;
		}
		if (FCode.equalsIgnoreCase("ClmDecision"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmDecision = FValue.trim();
			}
			else
				ClmDecision = null;
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
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckType = FValue.trim();
			}
			else
				CheckType = null;
		}
		if (FCode.equalsIgnoreCase("AppClmUWer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppClmUWer = FValue.trim();
			}
			else
				AppClmUWer = null;
		}
		if (FCode.equalsIgnoreCase("AppPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppPhase = FValue.trim();
			}
			else
				AppPhase = null;
		}
		if (FCode.equalsIgnoreCase("AppActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppActionType = FValue.trim();
			}
			else
				AppActionType = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("AuditConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditConclusion = FValue.trim();
			}
			else
				AuditConclusion = null;
		}
		if (FCode.equalsIgnoreCase("AuditIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditIdea = FValue.trim();
			}
			else
				AuditIdea = null;
		}
		if (FCode.equalsIgnoreCase("AuditLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditLevel = FValue.trim();
			}
			else
				AuditLevel = null;
		}
		if (FCode.equalsIgnoreCase("AuditPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditPer = FValue.trim();
			}
			else
				AuditPer = null;
		}
		if (FCode.equalsIgnoreCase("AuditDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AuditDate = fDate.getDate( FValue );
			}
			else
				AuditDate = null;
		}
		if (FCode.equalsIgnoreCase("ExamConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamConclusion = FValue.trim();
			}
			else
				ExamConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ExamIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamIdea = FValue.trim();
			}
			else
				ExamIdea = null;
		}
		if (FCode.equalsIgnoreCase("ExamLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamLevel = FValue.trim();
			}
			else
				ExamLevel = null;
		}
		if (FCode.equalsIgnoreCase("ExamPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamPer = FValue.trim();
			}
			else
				ExamPer = null;
		}
		if (FCode.equalsIgnoreCase("ExamDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ExamDate = fDate.getDate( FValue );
			}
			else
				ExamDate = null;
		}
		if (FCode.equalsIgnoreCase("SpecialRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecialRemark = FValue.trim();
			}
			else
				SpecialRemark = null;
		}
		if (FCode.equalsIgnoreCase("ExamNoPassReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamNoPassReason = FValue.trim();
			}
			else
				ExamNoPassReason = null;
		}
		if (FCode.equalsIgnoreCase("ExamNoPassDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamNoPassDesc = FValue.trim();
			}
			else
				ExamNoPassDesc = null;
		}
		if (FCode.equalsIgnoreCase("AuditNoPassReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditNoPassReason = FValue.trim();
			}
			else
				AuditNoPassReason = null;
		}
		if (FCode.equalsIgnoreCase("AuditNoPassDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditNoPassDesc = FValue.trim();
			}
			else
				AuditNoPassDesc = null;
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackNo = FValue.trim();
			}
			else
				BackNo = null;
		}
		if (FCode.equalsIgnoreCase("ExamCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamCom = FValue.trim();
			}
			else
				ExamCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLClaimUWMDetailSchema other = (LLClaimUWMDetailSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& ClmUWNo.equals(other.getClmUWNo())
			&& RgtNo.equals(other.getRgtNo())
			&& CaseNo.equals(other.getCaseNo())
			&& ClmUWer.equals(other.getClmUWer())
			&& ClmUWGrade.equals(other.getClmUWGrade())
			&& ClmDecision.equals(other.getClmDecision())
			&& AppGrade.equals(other.getAppGrade())
			&& CheckType.equals(other.getCheckType())
			&& AppClmUWer.equals(other.getAppClmUWer())
			&& AppPhase.equals(other.getAppPhase())
			&& AppActionType.equals(other.getAppActionType())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AuditConclusion.equals(other.getAuditConclusion())
			&& AuditIdea.equals(other.getAuditIdea())
			&& AuditLevel.equals(other.getAuditLevel())
			&& AuditPer.equals(other.getAuditPer())
			&& fDate.getString(AuditDate).equals(other.getAuditDate())
			&& ExamConclusion.equals(other.getExamConclusion())
			&& ExamIdea.equals(other.getExamIdea())
			&& ExamLevel.equals(other.getExamLevel())
			&& ExamPer.equals(other.getExamPer())
			&& fDate.getString(ExamDate).equals(other.getExamDate())
			&& SpecialRemark.equals(other.getSpecialRemark())
			&& ExamNoPassReason.equals(other.getExamNoPassReason())
			&& ExamNoPassDesc.equals(other.getExamNoPassDesc())
			&& AuditNoPassReason.equals(other.getAuditNoPassReason())
			&& AuditNoPassDesc.equals(other.getAuditNoPassDesc())
			&& BackNo.equals(other.getBackNo())
			&& ExamCom.equals(other.getExamCom());
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
		if( strFieldName.equals("ClmUWNo") ) {
			return 1;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 2;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 3;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return 4;
		}
		if( strFieldName.equals("ClmUWGrade") ) {
			return 5;
		}
		if( strFieldName.equals("ClmDecision") ) {
			return 6;
		}
		if( strFieldName.equals("AppGrade") ) {
			return 7;
		}
		if( strFieldName.equals("CheckType") ) {
			return 8;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return 9;
		}
		if( strFieldName.equals("AppPhase") ) {
			return 10;
		}
		if( strFieldName.equals("AppActionType") ) {
			return 11;
		}
		if( strFieldName.equals("Remark") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MngCom") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("AuditConclusion") ) {
			return 19;
		}
		if( strFieldName.equals("AuditIdea") ) {
			return 20;
		}
		if( strFieldName.equals("AuditLevel") ) {
			return 21;
		}
		if( strFieldName.equals("AuditPer") ) {
			return 22;
		}
		if( strFieldName.equals("AuditDate") ) {
			return 23;
		}
		if( strFieldName.equals("ExamConclusion") ) {
			return 24;
		}
		if( strFieldName.equals("ExamIdea") ) {
			return 25;
		}
		if( strFieldName.equals("ExamLevel") ) {
			return 26;
		}
		if( strFieldName.equals("ExamPer") ) {
			return 27;
		}
		if( strFieldName.equals("ExamDate") ) {
			return 28;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return 29;
		}
		if( strFieldName.equals("ExamNoPassReason") ) {
			return 30;
		}
		if( strFieldName.equals("ExamNoPassDesc") ) {
			return 31;
		}
		if( strFieldName.equals("AuditNoPassReason") ) {
			return 32;
		}
		if( strFieldName.equals("AuditNoPassDesc") ) {
			return 33;
		}
		if( strFieldName.equals("BackNo") ) {
			return 34;
		}
		if( strFieldName.equals("ExamCom") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "ClmUWNo";
				break;
			case 2:
				strFieldName = "RgtNo";
				break;
			case 3:
				strFieldName = "CaseNo";
				break;
			case 4:
				strFieldName = "ClmUWer";
				break;
			case 5:
				strFieldName = "ClmUWGrade";
				break;
			case 6:
				strFieldName = "ClmDecision";
				break;
			case 7:
				strFieldName = "AppGrade";
				break;
			case 8:
				strFieldName = "CheckType";
				break;
			case 9:
				strFieldName = "AppClmUWer";
				break;
			case 10:
				strFieldName = "AppPhase";
				break;
			case 11:
				strFieldName = "AppActionType";
				break;
			case 12:
				strFieldName = "Remark";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MngCom";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "AuditConclusion";
				break;
			case 20:
				strFieldName = "AuditIdea";
				break;
			case 21:
				strFieldName = "AuditLevel";
				break;
			case 22:
				strFieldName = "AuditPer";
				break;
			case 23:
				strFieldName = "AuditDate";
				break;
			case 24:
				strFieldName = "ExamConclusion";
				break;
			case 25:
				strFieldName = "ExamIdea";
				break;
			case 26:
				strFieldName = "ExamLevel";
				break;
			case 27:
				strFieldName = "ExamPer";
				break;
			case 28:
				strFieldName = "ExamDate";
				break;
			case 29:
				strFieldName = "SpecialRemark";
				break;
			case 30:
				strFieldName = "ExamNoPassReason";
				break;
			case 31:
				strFieldName = "ExamNoPassDesc";
				break;
			case 32:
				strFieldName = "AuditNoPassReason";
				break;
			case 33:
				strFieldName = "AuditNoPassDesc";
				break;
			case 34:
				strFieldName = "BackNo";
				break;
			case 35:
				strFieldName = "ExamCom";
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
		if( strFieldName.equals("ClmUWNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmDecision") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("AuditConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ExamConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamNoPassReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamNoPassDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditNoPassReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditNoPassDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
