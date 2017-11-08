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
import com.sinosoft.lis.db.LLClaimUWMainDB;

/*
 * <p>ClassName: LLClaimUWMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUWMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUWMainSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
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
	/** 申请审核人员 */
	private String AppClmUWer;
	/** 申请动作 */
	private String AppActionType;
	/** 审批意见1 */
	private String Remark1;
	/** 审定意见1 */
	private String Remark2;
	/** 审批结论1 */
	private String checkDecision1;
	/** 审定结论1 */
	private String checkDecision2;
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
	/** 审核类型 */
	private String CheckType;
	/** 申请阶段 */
	private String AppPhase;
	/** 审核结论 */
	private String AuditConclusion;
	/** 审核不通过原因 */
	private String AuditNoPassReason;
	/** 审核不通过依据 */
	private String AuditNoPassDesc;
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
	/** 审批不通过原因 */
	private String ExamNoPassReason;
	/** 审批不通过依据 */
	private String ExamNoPassDesc;
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
	/** 审批机构 */
	private String ExamCom;
	/** 双审标志 */
	private String DSClaimFlag;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUWMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClmNo";

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
		LLClaimUWMainSchema cloned = (LLClaimUWMainSchema)super.clone();
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
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	/**
	* 【不用】
	*/
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	/**
	* 【不用】
	*/
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	/**
	* 立案时使用，记录立案人员
	*/
	public String getClmUWer()
	{
		return ClmUWer;
	}
	public void setClmUWer(String aClmUWer)
	{
		if(aClmUWer!=null && aClmUWer.length()>10)
			throw new IllegalArgumentException("核赔员ClmUWer值"+aClmUWer+"的长度"+aClmUWer.length()+"大于最大值10");
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
		if(aClmUWGrade!=null && aClmUWGrade.length()>1)
			throw new IllegalArgumentException("核赔级别ClmUWGrade值"+aClmUWGrade+"的长度"+aClmUWGrade.length()+"大于最大值1");
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
		if(aClmDecision!=null && aClmDecision.length()>1)
			throw new IllegalArgumentException("核赔结论ClmDecision值"+aClmDecision+"的长度"+aClmDecision.length()+"大于最大值1");
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
		if(aAppGrade!=null && aAppGrade.length()>6)
			throw new IllegalArgumentException("申请级别AppGrade值"+aAppGrade+"的长度"+aAppGrade.length()+"大于最大值6");
		AppGrade = aAppGrade;
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
		if(aAppClmUWer!=null && aAppClmUWer.length()>10)
			throw new IllegalArgumentException("申请审核人员AppClmUWer值"+aAppClmUWer+"的长度"+aAppClmUWer.length()+"大于最大值10");
		AppClmUWer = aAppClmUWer;
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
		if(aAppActionType!=null && aAppActionType.length()>1)
			throw new IllegalArgumentException("申请动作AppActionType值"+aAppActionType+"的长度"+aAppActionType.length()+"大于最大值1");
		AppActionType = aAppActionType;
	}
	/**
	* 【不用】
	*/
	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		if(aRemark1!=null && aRemark1.length()>1000)
			throw new IllegalArgumentException("审批意见1Remark1值"+aRemark1+"的长度"+aRemark1.length()+"大于最大值1000");
		Remark1 = aRemark1;
	}
	/**
	* 【不用】
	*/
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		if(aRemark2!=null && aRemark2.length()>1000)
			throw new IllegalArgumentException("审定意见1Remark2值"+aRemark2+"的长度"+aRemark2.length()+"大于最大值1000");
		Remark2 = aRemark2;
	}
	/**
	* 【不用】
	*/
	public String getcheckDecision1()
	{
		return checkDecision1;
	}
	public void setcheckDecision1(String acheckDecision1)
	{
		if(acheckDecision1!=null && acheckDecision1.length()>1)
			throw new IllegalArgumentException("审批结论1checkDecision1值"+acheckDecision1+"的长度"+acheckDecision1.length()+"大于最大值1");
		checkDecision1 = acheckDecision1;
	}
	/**
	* 【不用】
	*/
	public String getcheckDecision2()
	{
		return checkDecision2;
	}
	public void setcheckDecision2(String acheckDecision2)
	{
		if(acheckDecision2!=null && acheckDecision2.length()>1)
			throw new IllegalArgumentException("审定结论1checkDecision2值"+acheckDecision2+"的长度"+acheckDecision2.length()+"大于最大值1");
		checkDecision2 = acheckDecision2;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>2000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值2000");
		Remark = aRemark;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
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
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
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
		if(aCheckType!=null && aCheckType.length()>1)
			throw new IllegalArgumentException("审核类型CheckType值"+aCheckType+"的长度"+aCheckType.length()+"大于最大值1");
		CheckType = aCheckType;
	}
	/**
	* 【不用】<p>
	* 0-审核<p>
	* 1-审批
	*/
	public String getAppPhase()
	{
		return AppPhase;
	}
	public void setAppPhase(String aAppPhase)
	{
		if(aAppPhase!=null && aAppPhase.length()>1)
			throw new IllegalArgumentException("申请阶段AppPhase值"+aAppPhase+"的长度"+aAppPhase.length()+"大于最大值1");
		AppPhase = aAppPhase;
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
		if(aAuditConclusion!=null && aAuditConclusion.length()>6)
			throw new IllegalArgumentException("审核结论AuditConclusion值"+aAuditConclusion+"的长度"+aAuditConclusion.length()+"大于最大值6");
		AuditConclusion = aAuditConclusion;
	}
	public String getAuditNoPassReason()
	{
		return AuditNoPassReason;
	}
	public void setAuditNoPassReason(String aAuditNoPassReason)
	{
		if(aAuditNoPassReason!=null && aAuditNoPassReason.length()>6)
			throw new IllegalArgumentException("审核不通过原因AuditNoPassReason值"+aAuditNoPassReason+"的长度"+aAuditNoPassReason.length()+"大于最大值6");
		AuditNoPassReason = aAuditNoPassReason;
	}
	public String getAuditNoPassDesc()
	{
		return AuditNoPassDesc;
	}
	public void setAuditNoPassDesc(String aAuditNoPassDesc)
	{
		if(aAuditNoPassDesc!=null && aAuditNoPassDesc.length()>1000)
			throw new IllegalArgumentException("审核不通过依据AuditNoPassDesc值"+aAuditNoPassDesc+"的长度"+aAuditNoPassDesc.length()+"大于最大值1000");
		AuditNoPassDesc = aAuditNoPassDesc;
	}
	public String getAuditIdea()
	{
		return AuditIdea;
	}
	public void setAuditIdea(String aAuditIdea)
	{
		if(aAuditIdea!=null && aAuditIdea.length()>4000)
			throw new IllegalArgumentException("审核意见AuditIdea值"+aAuditIdea+"的长度"+aAuditIdea.length()+"大于最大值4000");
		AuditIdea = aAuditIdea;
	}
	public String getAuditLevel()
	{
		return AuditLevel;
	}
	public void setAuditLevel(String aAuditLevel)
	{
		if(aAuditLevel!=null && aAuditLevel.length()>10)
			throw new IllegalArgumentException("审核级别AuditLevel值"+aAuditLevel+"的长度"+aAuditLevel.length()+"大于最大值10");
		AuditLevel = aAuditLevel;
	}
	public String getAuditPer()
	{
		return AuditPer;
	}
	public void setAuditPer(String aAuditPer)
	{
		if(aAuditPer!=null && aAuditPer.length()>20)
			throw new IllegalArgumentException("审核人AuditPer值"+aAuditPer+"的长度"+aAuditPer.length()+"大于最大值20");
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
		if(aExamConclusion!=null && aExamConclusion.length()>6)
			throw new IllegalArgumentException("审批结论ExamConclusion值"+aExamConclusion+"的长度"+aExamConclusion.length()+"大于最大值6");
		ExamConclusion = aExamConclusion;
	}
	public String getExamNoPassReason()
	{
		return ExamNoPassReason;
	}
	public void setExamNoPassReason(String aExamNoPassReason)
	{
		if(aExamNoPassReason!=null && aExamNoPassReason.length()>6)
			throw new IllegalArgumentException("审批不通过原因ExamNoPassReason值"+aExamNoPassReason+"的长度"+aExamNoPassReason.length()+"大于最大值6");
		ExamNoPassReason = aExamNoPassReason;
	}
	public String getExamNoPassDesc()
	{
		return ExamNoPassDesc;
	}
	public void setExamNoPassDesc(String aExamNoPassDesc)
	{
		if(aExamNoPassDesc!=null && aExamNoPassDesc.length()>1000)
			throw new IllegalArgumentException("审批不通过依据ExamNoPassDesc值"+aExamNoPassDesc+"的长度"+aExamNoPassDesc.length()+"大于最大值1000");
		ExamNoPassDesc = aExamNoPassDesc;
	}
	public String getExamIdea()
	{
		return ExamIdea;
	}
	public void setExamIdea(String aExamIdea)
	{
		if(aExamIdea!=null && aExamIdea.length()>4000)
			throw new IllegalArgumentException("审批意见ExamIdea值"+aExamIdea+"的长度"+aExamIdea.length()+"大于最大值4000");
		ExamIdea = aExamIdea;
	}
	public String getExamLevel()
	{
		return ExamLevel;
	}
	public void setExamLevel(String aExamLevel)
	{
		if(aExamLevel!=null && aExamLevel.length()>10)
			throw new IllegalArgumentException("审批级别ExamLevel值"+aExamLevel+"的长度"+aExamLevel.length()+"大于最大值10");
		ExamLevel = aExamLevel;
	}
	public String getExamPer()
	{
		return ExamPer;
	}
	public void setExamPer(String aExamPer)
	{
		if(aExamPer!=null && aExamPer.length()>30)
			throw new IllegalArgumentException("审批人ExamPer值"+aExamPer+"的长度"+aExamPer.length()+"大于最大值30");
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
		if(aSpecialRemark!=null && aSpecialRemark.length()>1000)
			throw new IllegalArgumentException("特殊备注SpecialRemark值"+aSpecialRemark+"的长度"+aSpecialRemark.length()+"大于最大值1000");
		SpecialRemark = aSpecialRemark;
	}
	public String getExamCom()
	{
		return ExamCom;
	}
	public void setExamCom(String aExamCom)
	{
		if(aExamCom!=null && aExamCom.length()>20)
			throw new IllegalArgumentException("审批机构ExamCom值"+aExamCom+"的长度"+aExamCom.length()+"大于最大值20");
		ExamCom = aExamCom;
	}
	public String getDSClaimFlag()
	{
		return DSClaimFlag;
	}
	public void setDSClaimFlag(String aDSClaimFlag)
	{
		if(aDSClaimFlag!=null && aDSClaimFlag.length()>2)
			throw new IllegalArgumentException("双审标志DSClaimFlag值"+aDSClaimFlag+"的长度"+aDSClaimFlag.length()+"大于最大值2");
		DSClaimFlag = aDSClaimFlag;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 LLClaimUWMainSchema 对象给 Schema 赋值
	* @param: aLLClaimUWMainSchema LLClaimUWMainSchema
	**/
	public void setSchema(LLClaimUWMainSchema aLLClaimUWMainSchema)
	{
		this.ClmNo = aLLClaimUWMainSchema.getClmNo();
		this.RgtNo = aLLClaimUWMainSchema.getRgtNo();
		this.CaseNo = aLLClaimUWMainSchema.getCaseNo();
		this.ClmUWer = aLLClaimUWMainSchema.getClmUWer();
		this.ClmUWGrade = aLLClaimUWMainSchema.getClmUWGrade();
		this.ClmDecision = aLLClaimUWMainSchema.getClmDecision();
		this.AppGrade = aLLClaimUWMainSchema.getAppGrade();
		this.AppClmUWer = aLLClaimUWMainSchema.getAppClmUWer();
		this.AppActionType = aLLClaimUWMainSchema.getAppActionType();
		this.Remark1 = aLLClaimUWMainSchema.getRemark1();
		this.Remark2 = aLLClaimUWMainSchema.getRemark2();
		this.checkDecision1 = aLLClaimUWMainSchema.getcheckDecision1();
		this.checkDecision2 = aLLClaimUWMainSchema.getcheckDecision2();
		this.Remark = aLLClaimUWMainSchema.getRemark();
		this.Operator = aLLClaimUWMainSchema.getOperator();
		this.MngCom = aLLClaimUWMainSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLClaimUWMainSchema.getMakeDate());
		this.MakeTime = aLLClaimUWMainSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimUWMainSchema.getModifyDate());
		this.ModifyTime = aLLClaimUWMainSchema.getModifyTime();
		this.CheckType = aLLClaimUWMainSchema.getCheckType();
		this.AppPhase = aLLClaimUWMainSchema.getAppPhase();
		this.AuditConclusion = aLLClaimUWMainSchema.getAuditConclusion();
		this.AuditNoPassReason = aLLClaimUWMainSchema.getAuditNoPassReason();
		this.AuditNoPassDesc = aLLClaimUWMainSchema.getAuditNoPassDesc();
		this.AuditIdea = aLLClaimUWMainSchema.getAuditIdea();
		this.AuditLevel = aLLClaimUWMainSchema.getAuditLevel();
		this.AuditPer = aLLClaimUWMainSchema.getAuditPer();
		this.AuditDate = fDate.getDate( aLLClaimUWMainSchema.getAuditDate());
		this.ExamConclusion = aLLClaimUWMainSchema.getExamConclusion();
		this.ExamNoPassReason = aLLClaimUWMainSchema.getExamNoPassReason();
		this.ExamNoPassDesc = aLLClaimUWMainSchema.getExamNoPassDesc();
		this.ExamIdea = aLLClaimUWMainSchema.getExamIdea();
		this.ExamLevel = aLLClaimUWMainSchema.getExamLevel();
		this.ExamPer = aLLClaimUWMainSchema.getExamPer();
		this.ExamDate = fDate.getDate( aLLClaimUWMainSchema.getExamDate());
		this.SpecialRemark = aLLClaimUWMainSchema.getSpecialRemark();
		this.ExamCom = aLLClaimUWMainSchema.getExamCom();
		this.DSClaimFlag = aLLClaimUWMainSchema.getDSClaimFlag();
		this.ComCode = aLLClaimUWMainSchema.getComCode();
		this.ModifyOperator = aLLClaimUWMainSchema.getModifyOperator();
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

			if( rs.getString("AppClmUWer") == null )
				this.AppClmUWer = null;
			else
				this.AppClmUWer = rs.getString("AppClmUWer").trim();

			if( rs.getString("AppActionType") == null )
				this.AppActionType = null;
			else
				this.AppActionType = rs.getString("AppActionType").trim();

			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

			if( rs.getString("checkDecision1") == null )
				this.checkDecision1 = null;
			else
				this.checkDecision1 = rs.getString("checkDecision1").trim();

			if( rs.getString("checkDecision2") == null )
				this.checkDecision2 = null;
			else
				this.checkDecision2 = rs.getString("checkDecision2").trim();

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

			if( rs.getString("CheckType") == null )
				this.CheckType = null;
			else
				this.CheckType = rs.getString("CheckType").trim();

			if( rs.getString("AppPhase") == null )
				this.AppPhase = null;
			else
				this.AppPhase = rs.getString("AppPhase").trim();

			if( rs.getString("AuditConclusion") == null )
				this.AuditConclusion = null;
			else
				this.AuditConclusion = rs.getString("AuditConclusion").trim();

			if( rs.getString("AuditNoPassReason") == null )
				this.AuditNoPassReason = null;
			else
				this.AuditNoPassReason = rs.getString("AuditNoPassReason").trim();

			if( rs.getString("AuditNoPassDesc") == null )
				this.AuditNoPassDesc = null;
			else
				this.AuditNoPassDesc = rs.getString("AuditNoPassDesc").trim();

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

			if( rs.getString("ExamNoPassReason") == null )
				this.ExamNoPassReason = null;
			else
				this.ExamNoPassReason = rs.getString("ExamNoPassReason").trim();

			if( rs.getString("ExamNoPassDesc") == null )
				this.ExamNoPassDesc = null;
			else
				this.ExamNoPassDesc = rs.getString("ExamNoPassDesc").trim();

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

			if( rs.getString("ExamCom") == null )
				this.ExamCom = null;
			else
				this.ExamCom = rs.getString("ExamCom").trim();

			if( rs.getString("DSClaimFlag") == null )
				this.DSClaimFlag = null;
			else
				this.DSClaimFlag = rs.getString("DSClaimFlag").trim();

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
			logger.debug("数据库中的LLClaimUWMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUWMainSchema getSchema()
	{
		LLClaimUWMainSchema aLLClaimUWMainSchema = new LLClaimUWMainSchema();
		aLLClaimUWMainSchema.setSchema(this);
		return aLLClaimUWMainSchema;
	}

	public LLClaimUWMainDB getDB()
	{
		LLClaimUWMainDB aDBOper = new LLClaimUWMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmDecision)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(checkDecision1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(checkDecision2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditNoPassReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditNoPassDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AuditDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamNoPassReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamNoPassDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExamDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecialRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExamCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DSClaimFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ClmUWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ClmDecision = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AppGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AppActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			checkDecision1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			checkDecision2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			CheckType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AppPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AuditConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AuditNoPassReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AuditNoPassDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AuditIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AuditLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			AuditPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AuditDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ExamConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ExamNoPassReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ExamNoPassDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ExamIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ExamLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ExamPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ExamDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			SpecialRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ExamCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			DSClaimFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWMainSchema";
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
		if (FCode.equalsIgnoreCase("AppClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppClmUWer));
		}
		if (FCode.equalsIgnoreCase("AppActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppActionType));
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
		}
		if (FCode.equalsIgnoreCase("checkDecision1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(checkDecision1));
		}
		if (FCode.equalsIgnoreCase("checkDecision2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(checkDecision2));
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
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckType));
		}
		if (FCode.equalsIgnoreCase("AppPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppPhase));
		}
		if (FCode.equalsIgnoreCase("AuditConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditConclusion));
		}
		if (FCode.equalsIgnoreCase("AuditNoPassReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditNoPassReason));
		}
		if (FCode.equalsIgnoreCase("AuditNoPassDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditNoPassDesc));
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
		if (FCode.equalsIgnoreCase("ExamNoPassReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamNoPassReason));
		}
		if (FCode.equalsIgnoreCase("ExamNoPassDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamNoPassDesc));
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
		if (FCode.equalsIgnoreCase("ExamCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExamCom));
		}
		if (FCode.equalsIgnoreCase("DSClaimFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DSClaimFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClmUWer);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ClmUWGrade);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ClmDecision);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AppGrade);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppClmUWer);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AppActionType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(checkDecision1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(checkDecision2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
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
				strFieldValue = StrTool.GBKToUnicode(CheckType);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AppPhase);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AuditConclusion);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AuditNoPassReason);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AuditNoPassDesc);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AuditIdea);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AuditLevel);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(AuditPer);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAuditDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ExamConclusion);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ExamNoPassReason);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ExamNoPassDesc);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ExamIdea);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ExamLevel);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(ExamPer);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExamDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(SpecialRemark);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(ExamCom);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(DSClaimFlag);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 40:
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
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
		if (FCode.equalsIgnoreCase("AppClmUWer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppClmUWer = FValue.trim();
			}
			else
				AppClmUWer = null;
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
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark1 = FValue.trim();
			}
			else
				Remark1 = null;
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
		}
		if (FCode.equalsIgnoreCase("checkDecision1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				checkDecision1 = FValue.trim();
			}
			else
				checkDecision1 = null;
		}
		if (FCode.equalsIgnoreCase("checkDecision2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				checkDecision2 = FValue.trim();
			}
			else
				checkDecision2 = null;
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
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckType = FValue.trim();
			}
			else
				CheckType = null;
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
		if (FCode.equalsIgnoreCase("AuditConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditConclusion = FValue.trim();
			}
			else
				AuditConclusion = null;
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
		if (FCode.equalsIgnoreCase("ExamCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExamCom = FValue.trim();
			}
			else
				ExamCom = null;
		}
		if (FCode.equalsIgnoreCase("DSClaimFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DSClaimFlag = FValue.trim();
			}
			else
				DSClaimFlag = null;
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
		LLClaimUWMainSchema other = (LLClaimUWMainSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& RgtNo.equals(other.getRgtNo())
			&& CaseNo.equals(other.getCaseNo())
			&& ClmUWer.equals(other.getClmUWer())
			&& ClmUWGrade.equals(other.getClmUWGrade())
			&& ClmDecision.equals(other.getClmDecision())
			&& AppGrade.equals(other.getAppGrade())
			&& AppClmUWer.equals(other.getAppClmUWer())
			&& AppActionType.equals(other.getAppActionType())
			&& Remark1.equals(other.getRemark1())
			&& Remark2.equals(other.getRemark2())
			&& checkDecision1.equals(other.getcheckDecision1())
			&& checkDecision2.equals(other.getcheckDecision2())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& CheckType.equals(other.getCheckType())
			&& AppPhase.equals(other.getAppPhase())
			&& AuditConclusion.equals(other.getAuditConclusion())
			&& AuditNoPassReason.equals(other.getAuditNoPassReason())
			&& AuditNoPassDesc.equals(other.getAuditNoPassDesc())
			&& AuditIdea.equals(other.getAuditIdea())
			&& AuditLevel.equals(other.getAuditLevel())
			&& AuditPer.equals(other.getAuditPer())
			&& fDate.getString(AuditDate).equals(other.getAuditDate())
			&& ExamConclusion.equals(other.getExamConclusion())
			&& ExamNoPassReason.equals(other.getExamNoPassReason())
			&& ExamNoPassDesc.equals(other.getExamNoPassDesc())
			&& ExamIdea.equals(other.getExamIdea())
			&& ExamLevel.equals(other.getExamLevel())
			&& ExamPer.equals(other.getExamPer())
			&& fDate.getString(ExamDate).equals(other.getExamDate())
			&& SpecialRemark.equals(other.getSpecialRemark())
			&& ExamCom.equals(other.getExamCom())
			&& DSClaimFlag.equals(other.getDSClaimFlag())
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 1;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 2;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return 3;
		}
		if( strFieldName.equals("ClmUWGrade") ) {
			return 4;
		}
		if( strFieldName.equals("ClmDecision") ) {
			return 5;
		}
		if( strFieldName.equals("AppGrade") ) {
			return 6;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return 7;
		}
		if( strFieldName.equals("AppActionType") ) {
			return 8;
		}
		if( strFieldName.equals("Remark1") ) {
			return 9;
		}
		if( strFieldName.equals("Remark2") ) {
			return 10;
		}
		if( strFieldName.equals("checkDecision1") ) {
			return 11;
		}
		if( strFieldName.equals("checkDecision2") ) {
			return 12;
		}
		if( strFieldName.equals("Remark") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("CheckType") ) {
			return 20;
		}
		if( strFieldName.equals("AppPhase") ) {
			return 21;
		}
		if( strFieldName.equals("AuditConclusion") ) {
			return 22;
		}
		if( strFieldName.equals("AuditNoPassReason") ) {
			return 23;
		}
		if( strFieldName.equals("AuditNoPassDesc") ) {
			return 24;
		}
		if( strFieldName.equals("AuditIdea") ) {
			return 25;
		}
		if( strFieldName.equals("AuditLevel") ) {
			return 26;
		}
		if( strFieldName.equals("AuditPer") ) {
			return 27;
		}
		if( strFieldName.equals("AuditDate") ) {
			return 28;
		}
		if( strFieldName.equals("ExamConclusion") ) {
			return 29;
		}
		if( strFieldName.equals("ExamNoPassReason") ) {
			return 30;
		}
		if( strFieldName.equals("ExamNoPassDesc") ) {
			return 31;
		}
		if( strFieldName.equals("ExamIdea") ) {
			return 32;
		}
		if( strFieldName.equals("ExamLevel") ) {
			return 33;
		}
		if( strFieldName.equals("ExamPer") ) {
			return 34;
		}
		if( strFieldName.equals("ExamDate") ) {
			return 35;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return 36;
		}
		if( strFieldName.equals("ExamCom") ) {
			return 37;
		}
		if( strFieldName.equals("DSClaimFlag") ) {
			return 38;
		}
		if( strFieldName.equals("ComCode") ) {
			return 39;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 40;
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
				strFieldName = "RgtNo";
				break;
			case 2:
				strFieldName = "CaseNo";
				break;
			case 3:
				strFieldName = "ClmUWer";
				break;
			case 4:
				strFieldName = "ClmUWGrade";
				break;
			case 5:
				strFieldName = "ClmDecision";
				break;
			case 6:
				strFieldName = "AppGrade";
				break;
			case 7:
				strFieldName = "AppClmUWer";
				break;
			case 8:
				strFieldName = "AppActionType";
				break;
			case 9:
				strFieldName = "Remark1";
				break;
			case 10:
				strFieldName = "Remark2";
				break;
			case 11:
				strFieldName = "checkDecision1";
				break;
			case 12:
				strFieldName = "checkDecision2";
				break;
			case 13:
				strFieldName = "Remark";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MngCom";
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
				strFieldName = "CheckType";
				break;
			case 21:
				strFieldName = "AppPhase";
				break;
			case 22:
				strFieldName = "AuditConclusion";
				break;
			case 23:
				strFieldName = "AuditNoPassReason";
				break;
			case 24:
				strFieldName = "AuditNoPassDesc";
				break;
			case 25:
				strFieldName = "AuditIdea";
				break;
			case 26:
				strFieldName = "AuditLevel";
				break;
			case 27:
				strFieldName = "AuditPer";
				break;
			case 28:
				strFieldName = "AuditDate";
				break;
			case 29:
				strFieldName = "ExamConclusion";
				break;
			case 30:
				strFieldName = "ExamNoPassReason";
				break;
			case 31:
				strFieldName = "ExamNoPassDesc";
				break;
			case 32:
				strFieldName = "ExamIdea";
				break;
			case 33:
				strFieldName = "ExamLevel";
				break;
			case 34:
				strFieldName = "ExamPer";
				break;
			case 35:
				strFieldName = "ExamDate";
				break;
			case 36:
				strFieldName = "SpecialRemark";
				break;
			case 37:
				strFieldName = "ExamCom";
				break;
			case 38:
				strFieldName = "DSClaimFlag";
				break;
			case 39:
				strFieldName = "ComCode";
				break;
			case 40:
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
		if( strFieldName.equals("ClmNo") ) {
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
		if( strFieldName.equals("AppClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("checkDecision1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("checkDecision2") ) {
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
		if( strFieldName.equals("CheckType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditNoPassReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditNoPassDesc") ) {
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
		if( strFieldName.equals("ExamNoPassReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExamNoPassDesc") ) {
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
		if( strFieldName.equals("ExamCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DSClaimFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
