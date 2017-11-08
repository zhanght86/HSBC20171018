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
import com.sinosoft.lis.db.LLClaimUserDB;

/*
 * <p>ClassName: LLClaimUserSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUserSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUserSchema.class);
	// @Field
	/** 用户编码 */
	private String UserCode;
	/** 用户姓名 */
	private String UserName;
	/** 机构编码 */
	private String ComCode;
	/** 权限级别 */
	private String JobCategory;
	/** 上级用户代码 */
	private String UpUserCode;
	/** 是否分公司审批人 */
	private String HandleFlag;
	/** 理赔处理权限 */
	private String ClaimDeal;
	/** 理赔查询权限 */
	private String ClaimQuery;
	/** 报案权限 */
	private String ReportFlag;
	/** 立案权限 */
	private String RegisterFlag;
	/** 理赔计算权限 */
	private String ClaimFlag;
	/** 核赔权限 */
	private String ClaimPopedom;
	/** 审核权限 */
	private String CheckFlag;
	/** 审批权限 */
	private String UWFlag;
	/** 呈报权限 */
	private String SubmitFlag;
	/** 调查权限 */
	private String SurveyFlag;
	/** 给付权限 */
	private String PayFlag;
	/** 审核级别 */
	private String CheckLevel;
	/** 审批级别 */
	private String UWLevel;
	/** 有效标识 */
	private String StateFlag;
	/** 入机时间 */
	private Date MakeDate;
	/** 入机日期 */
	private String MakeTime;
	/** 修改时间 */
	private Date ModifyDate;
	/** 修改日期 */
	private String ModiftTime;
	/** 操作人员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 原因 */
	private String Reason;
	/** 备注 */
	private String Remark;
	/** 在岗状态 */
	private String RgtFlag;
	/** 联系电话 */
	private String RelPhone;
	/** 所属科室 */
	private String RelDept;
	/** 预付权限 */
	private String PrepayFlag;
	/** 简易案件权限 */
	private String SimpleFlag;
	/** 豁免权限 */
	private String ExemptFlag;
	/** 单证扫描权限 */
	private String CertifyScan;
	/** 任务分配权限 */
	private String TaskAssign;
	/** Email */
	private String Email;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 38;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUserSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "UserCode";

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
		LLClaimUserSchema cloned = (LLClaimUserSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String aUserCode)
	{
		if(aUserCode!=null && aUserCode.length()>30)
			throw new IllegalArgumentException("用户编码UserCode值"+aUserCode+"的长度"+aUserCode.length()+"大于最大值30");
		UserCode = aUserCode;
	}
	public String getUserName()
	{
		return UserName;
	}
	public void setUserName(String aUserName)
	{
		if(aUserName!=null && aUserName.length()>20)
			throw new IllegalArgumentException("用户姓名UserName值"+aUserName+"的长度"+aUserName.length()+"大于最大值20");
		UserName = aUserName;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("机构编码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	/**
	* A：首席核赔师<p>
	* B：总公司区域签批人<p>
	* C：分公司签批人<p>
	* D：分公司审核人<p>
	* E：分公司辅助岗<p>
	* F：三级机构审核人<p>
	* G：三级机构辅助岗<p>
	* H：总公司理赔内勤<p>
	* I：分公司理赔内勤
	*/
	public String getJobCategory()
	{
		return JobCategory;
	}
	public void setJobCategory(String aJobCategory)
	{
		if(aJobCategory!=null && aJobCategory.length()>6)
			throw new IllegalArgumentException("权限级别JobCategory值"+aJobCategory+"的长度"+aJobCategory.length()+"大于最大值6");
		JobCategory = aJobCategory;
	}
	public String getUpUserCode()
	{
		return UpUserCode;
	}
	public void setUpUserCode(String aUpUserCode)
	{
		if(aUpUserCode!=null && aUpUserCode.length()>6)
			throw new IllegalArgumentException("上级用户代码UpUserCode值"+aUpUserCode+"的长度"+aUpUserCode.length()+"大于最大值6");
		UpUserCode = aUpUserCode;
	}
	public String getHandleFlag()
	{
		return HandleFlag;
	}
	public void setHandleFlag(String aHandleFlag)
	{
		if(aHandleFlag!=null && aHandleFlag.length()>6)
			throw new IllegalArgumentException("是否分公司审批人HandleFlag值"+aHandleFlag+"的长度"+aHandleFlag.length()+"大于最大值6");
		HandleFlag = aHandleFlag;
	}
	/**
	* 包含所有权限
	*/
	public String getClaimDeal()
	{
		return ClaimDeal;
	}
	public void setClaimDeal(String aClaimDeal)
	{
		if(aClaimDeal!=null && aClaimDeal.length()>6)
			throw new IllegalArgumentException("理赔处理权限ClaimDeal值"+aClaimDeal+"的长度"+aClaimDeal.length()+"大于最大值6");
		ClaimDeal = aClaimDeal;
	}
	public String getClaimQuery()
	{
		return ClaimQuery;
	}
	public void setClaimQuery(String aClaimQuery)
	{
		if(aClaimQuery!=null && aClaimQuery.length()>6)
			throw new IllegalArgumentException("理赔查询权限ClaimQuery值"+aClaimQuery+"的长度"+aClaimQuery.length()+"大于最大值6");
		ClaimQuery = aClaimQuery;
	}
	/**
	* 0-无报案权限<p>
	* 1-有报案权限
	*/
	public String getReportFlag()
	{
		return ReportFlag;
	}
	public void setReportFlag(String aReportFlag)
	{
		if(aReportFlag!=null && aReportFlag.length()>6)
			throw new IllegalArgumentException("报案权限ReportFlag值"+aReportFlag+"的长度"+aReportFlag.length()+"大于最大值6");
		ReportFlag = aReportFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getRegisterFlag()
	{
		return RegisterFlag;
	}
	public void setRegisterFlag(String aRegisterFlag)
	{
		if(aRegisterFlag!=null && aRegisterFlag.length()>6)
			throw new IllegalArgumentException("立案权限RegisterFlag值"+aRegisterFlag+"的长度"+aRegisterFlag.length()+"大于最大值6");
		RegisterFlag = aRegisterFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getClaimFlag()
	{
		return ClaimFlag;
	}
	public void setClaimFlag(String aClaimFlag)
	{
		if(aClaimFlag!=null && aClaimFlag.length()>6)
			throw new IllegalArgumentException("理赔计算权限ClaimFlag值"+aClaimFlag+"的长度"+aClaimFlag.length()+"大于最大值6");
		ClaimFlag = aClaimFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getClaimPopedom()
	{
		return ClaimPopedom;
	}
	public void setClaimPopedom(String aClaimPopedom)
	{
		if(aClaimPopedom!=null && aClaimPopedom.length()>6)
			throw new IllegalArgumentException("核赔权限ClaimPopedom值"+aClaimPopedom+"的长度"+aClaimPopedom.length()+"大于最大值6");
		ClaimPopedom = aClaimPopedom;
	}
	/**
	* 0-无审核权限<p>
	* 1-有审核权限
	*/
	public String getCheckFlag()
	{
		return CheckFlag;
	}
	public void setCheckFlag(String aCheckFlag)
	{
		if(aCheckFlag!=null && aCheckFlag.length()>6)
			throw new IllegalArgumentException("审核权限CheckFlag值"+aCheckFlag+"的长度"+aCheckFlag.length()+"大于最大值6");
		CheckFlag = aCheckFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>6)
			throw new IllegalArgumentException("审批权限UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值6");
		UWFlag = aUWFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getSubmitFlag()
	{
		return SubmitFlag;
	}
	public void setSubmitFlag(String aSubmitFlag)
	{
		if(aSubmitFlag!=null && aSubmitFlag.length()>6)
			throw new IllegalArgumentException("呈报权限SubmitFlag值"+aSubmitFlag+"的长度"+aSubmitFlag.length()+"大于最大值6");
		SubmitFlag = aSubmitFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getSurveyFlag()
	{
		return SurveyFlag;
	}
	public void setSurveyFlag(String aSurveyFlag)
	{
		if(aSurveyFlag!=null && aSurveyFlag.length()>6)
			throw new IllegalArgumentException("调查权限SurveyFlag值"+aSurveyFlag+"的长度"+aSurveyFlag.length()+"大于最大值6");
		SurveyFlag = aSurveyFlag;
	}
	public String getPayFlag()
	{
		return PayFlag;
	}
	public void setPayFlag(String aPayFlag)
	{
		if(aPayFlag!=null && aPayFlag.length()>6)
			throw new IllegalArgumentException("给付权限PayFlag值"+aPayFlag+"的长度"+aPayFlag.length()+"大于最大值6");
		PayFlag = aPayFlag;
	}
	public String getCheckLevel()
	{
		return CheckLevel;
	}
	public void setCheckLevel(String aCheckLevel)
	{
		if(aCheckLevel!=null && aCheckLevel.length()>6)
			throw new IllegalArgumentException("审核级别CheckLevel值"+aCheckLevel+"的长度"+aCheckLevel.length()+"大于最大值6");
		CheckLevel = aCheckLevel;
	}
	public String getUWLevel()
	{
		return UWLevel;
	}
	public void setUWLevel(String aUWLevel)
	{
		if(aUWLevel!=null && aUWLevel.length()>6)
			throw new IllegalArgumentException("审批级别UWLevel值"+aUWLevel+"的长度"+aUWLevel.length()+"大于最大值6");
		UWLevel = aUWLevel;
	}
	/**
	* 0-无效<p>
	* 1-有效
	*/
	public String getStateFlag()
	{
		return StateFlag;
	}
	public void setStateFlag(String aStateFlag)
	{
		if(aStateFlag!=null && aStateFlag.length()>6)
			throw new IllegalArgumentException("有效标识StateFlag值"+aStateFlag+"的长度"+aStateFlag.length()+"大于最大值6");
		StateFlag = aStateFlag;
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
			throw new IllegalArgumentException("入机日期MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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

	public String getModiftTime()
	{
		return ModiftTime;
	}
	public void setModiftTime(String aModiftTime)
	{
		if(aModiftTime!=null && aModiftTime.length()>8)
			throw new IllegalArgumentException("修改日期ModiftTime值"+aModiftTime+"的长度"+aModiftTime.length()+"大于最大值8");
		ModiftTime = aModiftTime;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作人员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	/**
	* 指无效时的原因。
	*/
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>100)
			throw new IllegalArgumentException("原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值100");
		Reason = aReason;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	/**
	* 0	在岗<p>
	* 1	离岗在司<p>
	* 2	离岗离司
	*/
	public String getRgtFlag()
	{
		return RgtFlag;
	}
	public void setRgtFlag(String aRgtFlag)
	{
		if(aRgtFlag!=null && aRgtFlag.length()>6)
			throw new IllegalArgumentException("在岗状态RgtFlag值"+aRgtFlag+"的长度"+aRgtFlag.length()+"大于最大值6");
		RgtFlag = aRgtFlag;
	}
	public String getRelPhone()
	{
		return RelPhone;
	}
	public void setRelPhone(String aRelPhone)
	{
		if(aRelPhone!=null && aRelPhone.length()>100)
			throw new IllegalArgumentException("联系电话RelPhone值"+aRelPhone+"的长度"+aRelPhone.length()+"大于最大值100");
		RelPhone = aRelPhone;
	}
	public String getRelDept()
	{
		return RelDept;
	}
	public void setRelDept(String aRelDept)
	{
		if(aRelDept!=null && aRelDept.length()>100)
			throw new IllegalArgumentException("所属科室RelDept值"+aRelDept+"的长度"+aRelDept.length()+"大于最大值100");
		RelDept = aRelDept;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getPrepayFlag()
	{
		return PrepayFlag;
	}
	public void setPrepayFlag(String aPrepayFlag)
	{
		if(aPrepayFlag!=null && aPrepayFlag.length()>6)
			throw new IllegalArgumentException("预付权限PrepayFlag值"+aPrepayFlag+"的长度"+aPrepayFlag.length()+"大于最大值6");
		PrepayFlag = aPrepayFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getSimpleFlag()
	{
		return SimpleFlag;
	}
	public void setSimpleFlag(String aSimpleFlag)
	{
		if(aSimpleFlag!=null && aSimpleFlag.length()>6)
			throw new IllegalArgumentException("简易案件权限SimpleFlag值"+aSimpleFlag+"的长度"+aSimpleFlag.length()+"大于最大值6");
		SimpleFlag = aSimpleFlag;
	}
	/**
	* 0无权限<p>
	* 1有权限
	*/
	public String getExemptFlag()
	{
		return ExemptFlag;
	}
	public void setExemptFlag(String aExemptFlag)
	{
		if(aExemptFlag!=null && aExemptFlag.length()>6)
			throw new IllegalArgumentException("豁免权限ExemptFlag值"+aExemptFlag+"的长度"+aExemptFlag.length()+"大于最大值6");
		ExemptFlag = aExemptFlag;
	}
	public String getCertifyScan()
	{
		return CertifyScan;
	}
	public void setCertifyScan(String aCertifyScan)
	{
		if(aCertifyScan!=null && aCertifyScan.length()>6)
			throw new IllegalArgumentException("单证扫描权限CertifyScan值"+aCertifyScan+"的长度"+aCertifyScan.length()+"大于最大值6");
		CertifyScan = aCertifyScan;
	}
	public String getTaskAssign()
	{
		return TaskAssign;
	}
	public void setTaskAssign(String aTaskAssign)
	{
		if(aTaskAssign!=null && aTaskAssign.length()>6)
			throw new IllegalArgumentException("任务分配权限TaskAssign值"+aTaskAssign+"的长度"+aTaskAssign.length()+"大于最大值6");
		TaskAssign = aTaskAssign;
	}
	public String getEmail()
	{
		return Email;
	}
	public void setEmail(String aEmail)
	{
		if(aEmail!=null && aEmail.length()>60)
			throw new IllegalArgumentException("EmailEmail值"+aEmail+"的长度"+aEmail.length()+"大于最大值60");
		Email = aEmail;
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
	* 使用另外一个 LLClaimUserSchema 对象给 Schema 赋值
	* @param: aLLClaimUserSchema LLClaimUserSchema
	**/
	public void setSchema(LLClaimUserSchema aLLClaimUserSchema)
	{
		this.UserCode = aLLClaimUserSchema.getUserCode();
		this.UserName = aLLClaimUserSchema.getUserName();
		this.ComCode = aLLClaimUserSchema.getComCode();
		this.JobCategory = aLLClaimUserSchema.getJobCategory();
		this.UpUserCode = aLLClaimUserSchema.getUpUserCode();
		this.HandleFlag = aLLClaimUserSchema.getHandleFlag();
		this.ClaimDeal = aLLClaimUserSchema.getClaimDeal();
		this.ClaimQuery = aLLClaimUserSchema.getClaimQuery();
		this.ReportFlag = aLLClaimUserSchema.getReportFlag();
		this.RegisterFlag = aLLClaimUserSchema.getRegisterFlag();
		this.ClaimFlag = aLLClaimUserSchema.getClaimFlag();
		this.ClaimPopedom = aLLClaimUserSchema.getClaimPopedom();
		this.CheckFlag = aLLClaimUserSchema.getCheckFlag();
		this.UWFlag = aLLClaimUserSchema.getUWFlag();
		this.SubmitFlag = aLLClaimUserSchema.getSubmitFlag();
		this.SurveyFlag = aLLClaimUserSchema.getSurveyFlag();
		this.PayFlag = aLLClaimUserSchema.getPayFlag();
		this.CheckLevel = aLLClaimUserSchema.getCheckLevel();
		this.UWLevel = aLLClaimUserSchema.getUWLevel();
		this.StateFlag = aLLClaimUserSchema.getStateFlag();
		this.MakeDate = fDate.getDate( aLLClaimUserSchema.getMakeDate());
		this.MakeTime = aLLClaimUserSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimUserSchema.getModifyDate());
		this.ModiftTime = aLLClaimUserSchema.getModiftTime();
		this.Operator = aLLClaimUserSchema.getOperator();
		this.ManageCom = aLLClaimUserSchema.getManageCom();
		this.Reason = aLLClaimUserSchema.getReason();
		this.Remark = aLLClaimUserSchema.getRemark();
		this.RgtFlag = aLLClaimUserSchema.getRgtFlag();
		this.RelPhone = aLLClaimUserSchema.getRelPhone();
		this.RelDept = aLLClaimUserSchema.getRelDept();
		this.PrepayFlag = aLLClaimUserSchema.getPrepayFlag();
		this.SimpleFlag = aLLClaimUserSchema.getSimpleFlag();
		this.ExemptFlag = aLLClaimUserSchema.getExemptFlag();
		this.CertifyScan = aLLClaimUserSchema.getCertifyScan();
		this.TaskAssign = aLLClaimUserSchema.getTaskAssign();
		this.Email = aLLClaimUserSchema.getEmail();
		this.ModifyOperator = aLLClaimUserSchema.getModifyOperator();
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
			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("UserName") == null )
				this.UserName = null;
			else
				this.UserName = rs.getString("UserName").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("JobCategory") == null )
				this.JobCategory = null;
			else
				this.JobCategory = rs.getString("JobCategory").trim();

			if( rs.getString("UpUserCode") == null )
				this.UpUserCode = null;
			else
				this.UpUserCode = rs.getString("UpUserCode").trim();

			if( rs.getString("HandleFlag") == null )
				this.HandleFlag = null;
			else
				this.HandleFlag = rs.getString("HandleFlag").trim();

			if( rs.getString("ClaimDeal") == null )
				this.ClaimDeal = null;
			else
				this.ClaimDeal = rs.getString("ClaimDeal").trim();

			if( rs.getString("ClaimQuery") == null )
				this.ClaimQuery = null;
			else
				this.ClaimQuery = rs.getString("ClaimQuery").trim();

			if( rs.getString("ReportFlag") == null )
				this.ReportFlag = null;
			else
				this.ReportFlag = rs.getString("ReportFlag").trim();

			if( rs.getString("RegisterFlag") == null )
				this.RegisterFlag = null;
			else
				this.RegisterFlag = rs.getString("RegisterFlag").trim();

			if( rs.getString("ClaimFlag") == null )
				this.ClaimFlag = null;
			else
				this.ClaimFlag = rs.getString("ClaimFlag").trim();

			if( rs.getString("ClaimPopedom") == null )
				this.ClaimPopedom = null;
			else
				this.ClaimPopedom = rs.getString("ClaimPopedom").trim();

			if( rs.getString("CheckFlag") == null )
				this.CheckFlag = null;
			else
				this.CheckFlag = rs.getString("CheckFlag").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("SubmitFlag") == null )
				this.SubmitFlag = null;
			else
				this.SubmitFlag = rs.getString("SubmitFlag").trim();

			if( rs.getString("SurveyFlag") == null )
				this.SurveyFlag = null;
			else
				this.SurveyFlag = rs.getString("SurveyFlag").trim();

			if( rs.getString("PayFlag") == null )
				this.PayFlag = null;
			else
				this.PayFlag = rs.getString("PayFlag").trim();

			if( rs.getString("CheckLevel") == null )
				this.CheckLevel = null;
			else
				this.CheckLevel = rs.getString("CheckLevel").trim();

			if( rs.getString("UWLevel") == null )
				this.UWLevel = null;
			else
				this.UWLevel = rs.getString("UWLevel").trim();

			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModiftTime") == null )
				this.ModiftTime = null;
			else
				this.ModiftTime = rs.getString("ModiftTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("RgtFlag") == null )
				this.RgtFlag = null;
			else
				this.RgtFlag = rs.getString("RgtFlag").trim();

			if( rs.getString("RelPhone") == null )
				this.RelPhone = null;
			else
				this.RelPhone = rs.getString("RelPhone").trim();

			if( rs.getString("RelDept") == null )
				this.RelDept = null;
			else
				this.RelDept = rs.getString("RelDept").trim();

			if( rs.getString("PrepayFlag") == null )
				this.PrepayFlag = null;
			else
				this.PrepayFlag = rs.getString("PrepayFlag").trim();

			if( rs.getString("SimpleFlag") == null )
				this.SimpleFlag = null;
			else
				this.SimpleFlag = rs.getString("SimpleFlag").trim();

			if( rs.getString("ExemptFlag") == null )
				this.ExemptFlag = null;
			else
				this.ExemptFlag = rs.getString("ExemptFlag").trim();

			if( rs.getString("CertifyScan") == null )
				this.CertifyScan = null;
			else
				this.CertifyScan = rs.getString("CertifyScan").trim();

			if( rs.getString("TaskAssign") == null )
				this.TaskAssign = null;
			else
				this.TaskAssign = rs.getString("TaskAssign").trim();

			if( rs.getString("Email") == null )
				this.Email = null;
			else
				this.Email = rs.getString("Email").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimUser表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUserSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUserSchema getSchema()
	{
		LLClaimUserSchema aLLClaimUserSchema = new LLClaimUserSchema();
		aLLClaimUserSchema.setSchema(this);
		return aLLClaimUserSchema;
	}

	public LLClaimUserDB getDB()
	{
		LLClaimUserDB aDBOper = new LLClaimUserDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUser描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UserName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JobCategory)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpUserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HandleFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimDeal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimQuery)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RegisterFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubmitFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModiftTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrepayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SimpleFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExemptFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyScan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TaskAssign)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Email)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUser>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UserName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			JobCategory = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			UpUserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			HandleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClaimDeal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ClaimQuery = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ReportFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RegisterFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ClaimFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ClaimPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SubmitFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			SurveyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CheckLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			UWLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModiftTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			RgtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			RelPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			RelDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PrepayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			SimpleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ExemptFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			CertifyScan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			TaskAssign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Email = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUserSchema";
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
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equalsIgnoreCase("UserName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserName));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("JobCategory"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JobCategory));
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpUserCode));
		}
		if (FCode.equalsIgnoreCase("HandleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HandleFlag));
		}
		if (FCode.equalsIgnoreCase("ClaimDeal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimDeal));
		}
		if (FCode.equalsIgnoreCase("ClaimQuery"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimQuery));
		}
		if (FCode.equalsIgnoreCase("ReportFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportFlag));
		}
		if (FCode.equalsIgnoreCase("RegisterFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegisterFlag));
		}
		if (FCode.equalsIgnoreCase("ClaimFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimFlag));
		}
		if (FCode.equalsIgnoreCase("ClaimPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimPopedom));
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckFlag));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("SubmitFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubmitFlag));
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyFlag));
		}
		if (FCode.equalsIgnoreCase("PayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayFlag));
		}
		if (FCode.equalsIgnoreCase("CheckLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckLevel));
		}
		if (FCode.equalsIgnoreCase("UWLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWLevel));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
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
		if (FCode.equalsIgnoreCase("ModiftTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModiftTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("RgtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtFlag));
		}
		if (FCode.equalsIgnoreCase("RelPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelPhone));
		}
		if (FCode.equalsIgnoreCase("RelDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelDept));
		}
		if (FCode.equalsIgnoreCase("PrepayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepayFlag));
		}
		if (FCode.equalsIgnoreCase("SimpleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SimpleFlag));
		}
		if (FCode.equalsIgnoreCase("ExemptFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExemptFlag));
		}
		if (FCode.equalsIgnoreCase("CertifyScan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyScan));
		}
		if (FCode.equalsIgnoreCase("TaskAssign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskAssign));
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Email));
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
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UserName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(JobCategory);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(UpUserCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(HandleFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClaimDeal);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClaimQuery);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ReportFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RegisterFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ClaimFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ClaimPopedom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CheckFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(SubmitFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(SurveyFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PayFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CheckLevel);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(UWLevel);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
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
				strFieldValue = StrTool.GBKToUnicode(ModiftTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(RgtFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(RelPhone);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(RelDept);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(PrepayFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(SimpleFlag);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ExemptFlag);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(CertifyScan);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(TaskAssign);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Email);
				break;
			case 37:
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

		if (FCode.equalsIgnoreCase("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
		}
		if (FCode.equalsIgnoreCase("UserName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserName = FValue.trim();
			}
			else
				UserName = null;
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
		if (FCode.equalsIgnoreCase("JobCategory"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JobCategory = FValue.trim();
			}
			else
				JobCategory = null;
		}
		if (FCode.equalsIgnoreCase("UpUserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpUserCode = FValue.trim();
			}
			else
				UpUserCode = null;
		}
		if (FCode.equalsIgnoreCase("HandleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HandleFlag = FValue.trim();
			}
			else
				HandleFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClaimDeal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimDeal = FValue.trim();
			}
			else
				ClaimDeal = null;
		}
		if (FCode.equalsIgnoreCase("ClaimQuery"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimQuery = FValue.trim();
			}
			else
				ClaimQuery = null;
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
		if (FCode.equalsIgnoreCase("RegisterFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RegisterFlag = FValue.trim();
			}
			else
				RegisterFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClaimFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimFlag = FValue.trim();
			}
			else
				ClaimFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClaimPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimPopedom = FValue.trim();
			}
			else
				ClaimPopedom = null;
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckFlag = FValue.trim();
			}
			else
				CheckFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("SubmitFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubmitFlag = FValue.trim();
			}
			else
				SubmitFlag = null;
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyFlag = FValue.trim();
			}
			else
				SurveyFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayFlag = FValue.trim();
			}
			else
				PayFlag = null;
		}
		if (FCode.equalsIgnoreCase("CheckLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckLevel = FValue.trim();
			}
			else
				CheckLevel = null;
		}
		if (FCode.equalsIgnoreCase("UWLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWLevel = FValue.trim();
			}
			else
				UWLevel = null;
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
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
		if (FCode.equalsIgnoreCase("ModiftTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModiftTime = FValue.trim();
			}
			else
				ModiftTime = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
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
		if (FCode.equalsIgnoreCase("RgtFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtFlag = FValue.trim();
			}
			else
				RgtFlag = null;
		}
		if (FCode.equalsIgnoreCase("RelPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelPhone = FValue.trim();
			}
			else
				RelPhone = null;
		}
		if (FCode.equalsIgnoreCase("RelDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelDept = FValue.trim();
			}
			else
				RelDept = null;
		}
		if (FCode.equalsIgnoreCase("PrepayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrepayFlag = FValue.trim();
			}
			else
				PrepayFlag = null;
		}
		if (FCode.equalsIgnoreCase("SimpleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SimpleFlag = FValue.trim();
			}
			else
				SimpleFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExemptFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExemptFlag = FValue.trim();
			}
			else
				ExemptFlag = null;
		}
		if (FCode.equalsIgnoreCase("CertifyScan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyScan = FValue.trim();
			}
			else
				CertifyScan = null;
		}
		if (FCode.equalsIgnoreCase("TaskAssign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskAssign = FValue.trim();
			}
			else
				TaskAssign = null;
		}
		if (FCode.equalsIgnoreCase("Email"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Email = FValue.trim();
			}
			else
				Email = null;
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
		LLClaimUserSchema other = (LLClaimUserSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& UserName.equals(other.getUserName())
			&& ComCode.equals(other.getComCode())
			&& JobCategory.equals(other.getJobCategory())
			&& UpUserCode.equals(other.getUpUserCode())
			&& HandleFlag.equals(other.getHandleFlag())
			&& ClaimDeal.equals(other.getClaimDeal())
			&& ClaimQuery.equals(other.getClaimQuery())
			&& ReportFlag.equals(other.getReportFlag())
			&& RegisterFlag.equals(other.getRegisterFlag())
			&& ClaimFlag.equals(other.getClaimFlag())
			&& ClaimPopedom.equals(other.getClaimPopedom())
			&& CheckFlag.equals(other.getCheckFlag())
			&& UWFlag.equals(other.getUWFlag())
			&& SubmitFlag.equals(other.getSubmitFlag())
			&& SurveyFlag.equals(other.getSurveyFlag())
			&& PayFlag.equals(other.getPayFlag())
			&& CheckLevel.equals(other.getCheckLevel())
			&& UWLevel.equals(other.getUWLevel())
			&& StateFlag.equals(other.getStateFlag())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModiftTime.equals(other.getModiftTime())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& Reason.equals(other.getReason())
			&& Remark.equals(other.getRemark())
			&& RgtFlag.equals(other.getRgtFlag())
			&& RelPhone.equals(other.getRelPhone())
			&& RelDept.equals(other.getRelDept())
			&& PrepayFlag.equals(other.getPrepayFlag())
			&& SimpleFlag.equals(other.getSimpleFlag())
			&& ExemptFlag.equals(other.getExemptFlag())
			&& CertifyScan.equals(other.getCertifyScan())
			&& TaskAssign.equals(other.getTaskAssign())
			&& Email.equals(other.getEmail())
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
		if( strFieldName.equals("UserCode") ) {
			return 0;
		}
		if( strFieldName.equals("UserName") ) {
			return 1;
		}
		if( strFieldName.equals("ComCode") ) {
			return 2;
		}
		if( strFieldName.equals("JobCategory") ) {
			return 3;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return 4;
		}
		if( strFieldName.equals("HandleFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ClaimDeal") ) {
			return 6;
		}
		if( strFieldName.equals("ClaimQuery") ) {
			return 7;
		}
		if( strFieldName.equals("ReportFlag") ) {
			return 8;
		}
		if( strFieldName.equals("RegisterFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ClaimFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ClaimPopedom") ) {
			return 11;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return 12;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 13;
		}
		if( strFieldName.equals("SubmitFlag") ) {
			return 14;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return 15;
		}
		if( strFieldName.equals("PayFlag") ) {
			return 16;
		}
		if( strFieldName.equals("CheckLevel") ) {
			return 17;
		}
		if( strFieldName.equals("UWLevel") ) {
			return 18;
		}
		if( strFieldName.equals("StateFlag") ) {
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
		if( strFieldName.equals("ModiftTime") ) {
			return 23;
		}
		if( strFieldName.equals("Operator") ) {
			return 24;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 25;
		}
		if( strFieldName.equals("Reason") ) {
			return 26;
		}
		if( strFieldName.equals("Remark") ) {
			return 27;
		}
		if( strFieldName.equals("RgtFlag") ) {
			return 28;
		}
		if( strFieldName.equals("RelPhone") ) {
			return 29;
		}
		if( strFieldName.equals("RelDept") ) {
			return 30;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return 31;
		}
		if( strFieldName.equals("SimpleFlag") ) {
			return 32;
		}
		if( strFieldName.equals("ExemptFlag") ) {
			return 33;
		}
		if( strFieldName.equals("CertifyScan") ) {
			return 34;
		}
		if( strFieldName.equals("TaskAssign") ) {
			return 35;
		}
		if( strFieldName.equals("Email") ) {
			return 36;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 37;
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
				strFieldName = "UserCode";
				break;
			case 1:
				strFieldName = "UserName";
				break;
			case 2:
				strFieldName = "ComCode";
				break;
			case 3:
				strFieldName = "JobCategory";
				break;
			case 4:
				strFieldName = "UpUserCode";
				break;
			case 5:
				strFieldName = "HandleFlag";
				break;
			case 6:
				strFieldName = "ClaimDeal";
				break;
			case 7:
				strFieldName = "ClaimQuery";
				break;
			case 8:
				strFieldName = "ReportFlag";
				break;
			case 9:
				strFieldName = "RegisterFlag";
				break;
			case 10:
				strFieldName = "ClaimFlag";
				break;
			case 11:
				strFieldName = "ClaimPopedom";
				break;
			case 12:
				strFieldName = "CheckFlag";
				break;
			case 13:
				strFieldName = "UWFlag";
				break;
			case 14:
				strFieldName = "SubmitFlag";
				break;
			case 15:
				strFieldName = "SurveyFlag";
				break;
			case 16:
				strFieldName = "PayFlag";
				break;
			case 17:
				strFieldName = "CheckLevel";
				break;
			case 18:
				strFieldName = "UWLevel";
				break;
			case 19:
				strFieldName = "StateFlag";
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
				strFieldName = "ModiftTime";
				break;
			case 24:
				strFieldName = "Operator";
				break;
			case 25:
				strFieldName = "ManageCom";
				break;
			case 26:
				strFieldName = "Reason";
				break;
			case 27:
				strFieldName = "Remark";
				break;
			case 28:
				strFieldName = "RgtFlag";
				break;
			case 29:
				strFieldName = "RelPhone";
				break;
			case 30:
				strFieldName = "RelDept";
				break;
			case 31:
				strFieldName = "PrepayFlag";
				break;
			case 32:
				strFieldName = "SimpleFlag";
				break;
			case 33:
				strFieldName = "ExemptFlag";
				break;
			case 34:
				strFieldName = "CertifyScan";
				break;
			case 35:
				strFieldName = "TaskAssign";
				break;
			case 36:
				strFieldName = "Email";
				break;
			case 37:
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
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JobCategory") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpUserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimDeal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimQuery") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RegisterFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubmitFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateFlag") ) {
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
		if( strFieldName.equals("ModiftTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SimpleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExemptFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyScan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TaskAssign") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Email") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
