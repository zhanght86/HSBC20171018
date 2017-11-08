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
import com.sinosoft.lis.db.LLClaimUnderwriteDB;

/*
 * <p>ClassName: LLClaimUnderwriteSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LLClaimUnderwriteSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUnderwriteSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 立案号 */
	private String RgtNo;
	/** 总单/合同号 */
	private String ContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 保单号 */
	private String PolNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 销售渠道 */
	private String SaleChnl;
	/** 代理人代码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 投保人客户号 */
	private String AppntNo;
	/** 投保人名称 */
	private String AppntName;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 保单状态 */
	private String PolState;
	/** 核算赔付金额 */
	private double StandPay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 核赔员 */
	private String ClmUWer;
	/** 核赔级别 */
	private String ClmUWGrade;
	/** 机器核赔结论 */
	private String AutoClmDecision;
	/** 核赔结论 */
	private String ClmDecision;
	/** 管理机构 */
	private String MngCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 是否已经自动核赔 */
	private String AutoClmFlag;
	/** 申请级别 */
	private String AppGrade;
	/** 备注 */
	private String Remark;
	/** 审核类型 */
	private String CheckType;
	/** 申请审核人员 */
	private String AppClmUWer;
	/** 申请动作 */
	private String AppActionType;
	/** 核赔次数 */
	private String ClmUWNo;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 集体合同号 */
	private String GrpContNo;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 受理事故号 */
	private String CaseRelaNo;
	/** 核赔依据 */
	private String ClmDepend;
	/** 分案号 */
	private String CaseNo;
	/** 操作员 */
	private String Operator;
	/** 给付责任类型 */
	private String GetDutyKind;

	public static final int FIELDNUM = 42;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUnderwriteSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ClmNo";
		pk[1] = "PolNo";

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
		LLClaimUnderwriteSchema cloned = (LLClaimUnderwriteSchema)super.clone();
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("总单/合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单号GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单号PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		if(aKindCode!=null && aKindCode.length()>3)
			throw new IllegalArgumentException("险类代码KindCode值"+aKindCode+"的长度"+aKindCode.length()+"大于最大值3");
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		if(aRiskVer!=null && aRiskVer.length()>8)
			throw new IllegalArgumentException("险种版本号RiskVer值"+aRiskVer+"的长度"+aRiskVer.length()+"大于最大值8");
		RiskVer = aRiskVer;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
		if(aPolMngCom!=null && aPolMngCom.length()>10)
			throw new IllegalArgumentException("保单管理机构PolMngCom值"+aPolMngCom+"的长度"+aPolMngCom.length()+"大于最大值10");
		PolMngCom = aPolMngCom;
	}
	/**
	* 01-团险直销,02-个人营销,03-银行代理<p>
	* 04-兼业代理,05-专业代理,06-经纪公司<p>
	* 07-不计业绩销售渠道,99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		if(aSaleChnl!=null && aSaleChnl.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值2");
		SaleChnl = aSaleChnl;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		if(aAgentCode!=null && aAgentCode.length()>10)
			throw new IllegalArgumentException("代理人代码AgentCode值"+aAgentCode+"的长度"+aAgentCode.length()+"大于最大值10");
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		if(aAgentGroup!=null && aAgentGroup.length()>12)
			throw new IllegalArgumentException("代理人组别AgentGroup值"+aAgentGroup+"的长度"+aAgentGroup.length()+"大于最大值12");
		AgentGroup = aAgentGroup;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		if(aInsuredName!=null && aInsuredName.length()>20)
			throw new IllegalArgumentException("被保人名称InsuredName值"+aInsuredName+"的长度"+aInsuredName.length()+"大于最大值20");
		InsuredName = aInsuredName;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>24)
			throw new IllegalArgumentException("投保人客户号AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值24");
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>60)
			throw new IllegalArgumentException("投保人名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值60");
		AppntName = aAppntName;
	}
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		if(aPolState!=null && aPolState.length()>10)
			throw new IllegalArgumentException("保单状态PolState值"+aPolState+"的长度"+aPolState.length()+"大于最大值10");
		PolState = aPolState;
	}
	public double getStandPay()
	{
		return StandPay;
	}
	public void setStandPay(double aStandPay)
	{
		StandPay = aStandPay;
	}
	public void setStandPay(String aStandPay)
	{
		if (aStandPay != null && !aStandPay.equals(""))
		{
			Double tDouble = new Double(aStandPay);
			double d = tDouble.doubleValue();
			StandPay = d;
		}
	}

	public double getRealPay()
	{
		return RealPay;
	}
	public void setRealPay(double aRealPay)
	{
		RealPay = aRealPay;
	}
	public void setRealPay(String aRealPay)
	{
		if (aRealPay != null && !aRealPay.equals(""))
		{
			Double tDouble = new Double(aRealPay);
			double d = tDouble.doubleValue();
			RealPay = d;
		}
	}

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
	* 包括：上报、正常赔付、通融赔付、拒赔等
	*/
	public String getAutoClmDecision()
	{
		return AutoClmDecision;
	}
	public void setAutoClmDecision(String aAutoClmDecision)
	{
		if(aAutoClmDecision!=null && aAutoClmDecision.length()>1)
			throw new IllegalArgumentException("机器核赔结论AutoClmDecision值"+aAutoClmDecision+"的长度"+aAutoClmDecision.length()+"大于最大值1");
		AutoClmDecision = aAutoClmDecision;
	}
	/**
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
	/**
	* 0 -- 没有核保<p>
	* 1 -- 已经自动核保
	*/
	public String getAutoClmFlag()
	{
		return AutoClmFlag;
	}
	public void setAutoClmFlag(String aAutoClmFlag)
	{
		if(aAutoClmFlag!=null && aAutoClmFlag.length()>1)
			throw new IllegalArgumentException("是否已经自动核赔AutoClmFlag值"+aAutoClmFlag+"的长度"+aAutoClmFlag.length()+"大于最大值1");
		AutoClmFlag = aAutoClmFlag;
	}
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
	* 0 －－ 初次审核<p>
	* 1 －－ 签批退回审核<p>
	* 2 －－ 申诉审核
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
	public String getClmUWNo()
	{
		return ClmUWNo;
	}
	public void setClmUWNo(String aClmUWNo)
	{
		if(aClmUWNo!=null && aClmUWNo.length()>20)
			throw new IllegalArgumentException("核赔次数ClmUWNo值"+aClmUWNo+"的长度"+aClmUWNo.length()+"大于最大值20");
		ClmUWNo = aClmUWNo;
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

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
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
	* 支持一次案件多次受理<p>
	* 受理事故号
	*/
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		if(aCaseRelaNo!=null && aCaseRelaNo.length()>20)
			throw new IllegalArgumentException("受理事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}
	public String getClmDepend()
	{
		return ClmDepend;
	}
	public void setClmDepend(String aClmDepend)
	{
		if(aClmDepend!=null && aClmDepend.length()>100)
			throw new IllegalArgumentException("核赔依据ClmDepend值"+aClmDepend+"的长度"+aClmDepend.length()+"大于最大值100");
		ClmDepend = aClmDepend;
	}
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
	}
	/**
	* #100  意外医疗 #101  意外伤残 #102  意外死 #103  意外医疗津贴 #104  意外高残 #200  疾病医疗 #201  疾病伤残 #202  疾病死亡 #203  疾病医疗津贴 #204  疾病高残
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>10)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值10");
		GetDutyKind = aGetDutyKind;
	}

	/**
	* 使用另外一个 LLClaimUnderwriteSchema 对象给 Schema 赋值
	* @param: aLLClaimUnderwriteSchema LLClaimUnderwriteSchema
	**/
	public void setSchema(LLClaimUnderwriteSchema aLLClaimUnderwriteSchema)
	{
		this.ClmNo = aLLClaimUnderwriteSchema.getClmNo();
		this.RgtNo = aLLClaimUnderwriteSchema.getRgtNo();
		this.ContNo = aLLClaimUnderwriteSchema.getContNo();
		this.GrpPolNo = aLLClaimUnderwriteSchema.getGrpPolNo();
		this.PolNo = aLLClaimUnderwriteSchema.getPolNo();
		this.KindCode = aLLClaimUnderwriteSchema.getKindCode();
		this.RiskCode = aLLClaimUnderwriteSchema.getRiskCode();
		this.RiskVer = aLLClaimUnderwriteSchema.getRiskVer();
		this.PolMngCom = aLLClaimUnderwriteSchema.getPolMngCom();
		this.SaleChnl = aLLClaimUnderwriteSchema.getSaleChnl();
		this.AgentCode = aLLClaimUnderwriteSchema.getAgentCode();
		this.AgentGroup = aLLClaimUnderwriteSchema.getAgentGroup();
		this.InsuredNo = aLLClaimUnderwriteSchema.getInsuredNo();
		this.InsuredName = aLLClaimUnderwriteSchema.getInsuredName();
		this.AppntNo = aLLClaimUnderwriteSchema.getAppntNo();
		this.AppntName = aLLClaimUnderwriteSchema.getAppntName();
		this.CValiDate = fDate.getDate( aLLClaimUnderwriteSchema.getCValiDate());
		this.PolState = aLLClaimUnderwriteSchema.getPolState();
		this.StandPay = aLLClaimUnderwriteSchema.getStandPay();
		this.RealPay = aLLClaimUnderwriteSchema.getRealPay();
		this.ClmUWer = aLLClaimUnderwriteSchema.getClmUWer();
		this.ClmUWGrade = aLLClaimUnderwriteSchema.getClmUWGrade();
		this.AutoClmDecision = aLLClaimUnderwriteSchema.getAutoClmDecision();
		this.ClmDecision = aLLClaimUnderwriteSchema.getClmDecision();
		this.MngCom = aLLClaimUnderwriteSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLClaimUnderwriteSchema.getMakeDate());
		this.MakeTime = aLLClaimUnderwriteSchema.getMakeTime();
		this.AutoClmFlag = aLLClaimUnderwriteSchema.getAutoClmFlag();
		this.AppGrade = aLLClaimUnderwriteSchema.getAppGrade();
		this.Remark = aLLClaimUnderwriteSchema.getRemark();
		this.CheckType = aLLClaimUnderwriteSchema.getCheckType();
		this.AppClmUWer = aLLClaimUnderwriteSchema.getAppClmUWer();
		this.AppActionType = aLLClaimUnderwriteSchema.getAppActionType();
		this.ClmUWNo = aLLClaimUnderwriteSchema.getClmUWNo();
		this.ModifyDate = fDate.getDate( aLLClaimUnderwriteSchema.getModifyDate());
		this.GrpContNo = aLLClaimUnderwriteSchema.getGrpContNo();
		this.ModifyTime = aLLClaimUnderwriteSchema.getModifyTime();
		this.CaseRelaNo = aLLClaimUnderwriteSchema.getCaseRelaNo();
		this.ClmDepend = aLLClaimUnderwriteSchema.getClmDepend();
		this.CaseNo = aLLClaimUnderwriteSchema.getCaseNo();
		this.Operator = aLLClaimUnderwriteSchema.getOperator();
		this.GetDutyKind = aLLClaimUnderwriteSchema.getGetDutyKind();
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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("PolMngCom") == null )
				this.PolMngCom = null;
			else
				this.PolMngCom = rs.getString("PolMngCom").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

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

			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			this.StandPay = rs.getDouble("StandPay");
			this.RealPay = rs.getDouble("RealPay");
			if( rs.getString("ClmUWer") == null )
				this.ClmUWer = null;
			else
				this.ClmUWer = rs.getString("ClmUWer").trim();

			if( rs.getString("ClmUWGrade") == null )
				this.ClmUWGrade = null;
			else
				this.ClmUWGrade = rs.getString("ClmUWGrade").trim();

			if( rs.getString("AutoClmDecision") == null )
				this.AutoClmDecision = null;
			else
				this.AutoClmDecision = rs.getString("AutoClmDecision").trim();

			if( rs.getString("ClmDecision") == null )
				this.ClmDecision = null;
			else
				this.ClmDecision = rs.getString("ClmDecision").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("AutoClmFlag") == null )
				this.AutoClmFlag = null;
			else
				this.AutoClmFlag = rs.getString("AutoClmFlag").trim();

			if( rs.getString("AppGrade") == null )
				this.AppGrade = null;
			else
				this.AppGrade = rs.getString("AppGrade").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("CheckType") == null )
				this.CheckType = null;
			else
				this.CheckType = rs.getString("CheckType").trim();

			if( rs.getString("AppClmUWer") == null )
				this.AppClmUWer = null;
			else
				this.AppClmUWer = rs.getString("AppClmUWer").trim();

			if( rs.getString("AppActionType") == null )
				this.AppActionType = null;
			else
				this.AppActionType = rs.getString("AppActionType").trim();

			if( rs.getString("ClmUWNo") == null )
				this.ClmUWNo = null;
			else
				this.ClmUWNo = rs.getString("ClmUWNo").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("ClmDepend") == null )
				this.ClmDepend = null;
			else
				this.ClmDepend = rs.getString("ClmDepend").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimUnderwrite表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUnderwriteSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUnderwriteSchema getSchema()
	{
		LLClaimUnderwriteSchema aLLClaimUnderwriteSchema = new LLClaimUnderwriteSchema();
		aLLClaimUnderwriteSchema.setSchema(this);
		return aLLClaimUnderwriteSchema;
	}

	public LLClaimUnderwriteDB getDB()
	{
		LLClaimUnderwriteDB aDBOper = new LLClaimUnderwriteDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUnderwrite描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoClmDecision)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmDecision)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoClmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmDepend)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUnderwrite>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			ClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ClmUWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AutoClmDecision = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ClmDecision = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			AutoClmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AppGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CheckType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			AppClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			AppActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			ClmUWNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ClmDepend = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUnderwriteSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMngCom));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWer));
		}
		if (FCode.equalsIgnoreCase("ClmUWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWGrade));
		}
		if (FCode.equalsIgnoreCase("AutoClmDecision"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoClmDecision));
		}
		if (FCode.equalsIgnoreCase("ClmDecision"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmDecision));
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
		if (FCode.equalsIgnoreCase("AutoClmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoClmFlag));
		}
		if (FCode.equalsIgnoreCase("AppGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppGrade));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckType));
		}
		if (FCode.equalsIgnoreCase("AppClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppClmUWer));
		}
		if (FCode.equalsIgnoreCase("AppActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppActionType));
		}
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWNo));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("ClmDepend"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmDepend));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 18:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 19:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ClmUWer);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ClmUWGrade);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AutoClmDecision);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ClmDecision);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(AutoClmFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AppGrade);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(CheckType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(AppClmUWer);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AppActionType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ClmUWNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ClmDepend);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolMngCom = FValue.trim();
			}
			else
				PolMngCom = null;
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealPay = d;
			}
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
		if (FCode.equalsIgnoreCase("AutoClmDecision"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoClmDecision = FValue.trim();
			}
			else
				AutoClmDecision = null;
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
		if (FCode.equalsIgnoreCase("AutoClmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoClmFlag = FValue.trim();
			}
			else
				AutoClmFlag = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("AppActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppActionType = FValue.trim();
			}
			else
				AppActionType = null;
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
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
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("ClmDepend"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmDepend = FValue.trim();
			}
			else
				ClmDepend = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLClaimUnderwriteSchema other = (LLClaimUnderwriteSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& RgtNo.equals(other.getRgtNo())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& PolNo.equals(other.getPolNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& PolState.equals(other.getPolState())
			&& StandPay == other.getStandPay()
			&& RealPay == other.getRealPay()
			&& ClmUWer.equals(other.getClmUWer())
			&& ClmUWGrade.equals(other.getClmUWGrade())
			&& AutoClmDecision.equals(other.getAutoClmDecision())
			&& ClmDecision.equals(other.getClmDecision())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& AutoClmFlag.equals(other.getAutoClmFlag())
			&& AppGrade.equals(other.getAppGrade())
			&& Remark.equals(other.getRemark())
			&& CheckType.equals(other.getCheckType())
			&& AppClmUWer.equals(other.getAppClmUWer())
			&& AppActionType.equals(other.getAppActionType())
			&& ClmUWNo.equals(other.getClmUWNo())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ModifyTime.equals(other.getModifyTime())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& ClmDepend.equals(other.getClmDepend())
			&& CaseNo.equals(other.getCaseNo())
			&& Operator.equals(other.getOperator())
			&& GetDutyKind.equals(other.getGetDutyKind());
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
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 3;
		}
		if( strFieldName.equals("PolNo") ) {
			return 4;
		}
		if( strFieldName.equals("KindCode") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 7;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 8;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 9;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 10;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 11;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 12;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 13;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 14;
		}
		if( strFieldName.equals("AppntName") ) {
			return 15;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 16;
		}
		if( strFieldName.equals("PolState") ) {
			return 17;
		}
		if( strFieldName.equals("StandPay") ) {
			return 18;
		}
		if( strFieldName.equals("RealPay") ) {
			return 19;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return 20;
		}
		if( strFieldName.equals("ClmUWGrade") ) {
			return 21;
		}
		if( strFieldName.equals("AutoClmDecision") ) {
			return 22;
		}
		if( strFieldName.equals("ClmDecision") ) {
			return 23;
		}
		if( strFieldName.equals("MngCom") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
		}
		if( strFieldName.equals("AutoClmFlag") ) {
			return 27;
		}
		if( strFieldName.equals("AppGrade") ) {
			return 28;
		}
		if( strFieldName.equals("Remark") ) {
			return 29;
		}
		if( strFieldName.equals("CheckType") ) {
			return 30;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return 31;
		}
		if( strFieldName.equals("AppActionType") ) {
			return 32;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return 33;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 34;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 35;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 36;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 37;
		}
		if( strFieldName.equals("ClmDepend") ) {
			return 38;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 39;
		}
		if( strFieldName.equals("Operator") ) {
			return 40;
		}
		if( strFieldName.equals("GetDutyKind") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "RgtNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "GrpPolNo";
				break;
			case 4:
				strFieldName = "PolNo";
				break;
			case 5:
				strFieldName = "KindCode";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "RiskVer";
				break;
			case 8:
				strFieldName = "PolMngCom";
				break;
			case 9:
				strFieldName = "SaleChnl";
				break;
			case 10:
				strFieldName = "AgentCode";
				break;
			case 11:
				strFieldName = "AgentGroup";
				break;
			case 12:
				strFieldName = "InsuredNo";
				break;
			case 13:
				strFieldName = "InsuredName";
				break;
			case 14:
				strFieldName = "AppntNo";
				break;
			case 15:
				strFieldName = "AppntName";
				break;
			case 16:
				strFieldName = "CValiDate";
				break;
			case 17:
				strFieldName = "PolState";
				break;
			case 18:
				strFieldName = "StandPay";
				break;
			case 19:
				strFieldName = "RealPay";
				break;
			case 20:
				strFieldName = "ClmUWer";
				break;
			case 21:
				strFieldName = "ClmUWGrade";
				break;
			case 22:
				strFieldName = "AutoClmDecision";
				break;
			case 23:
				strFieldName = "ClmDecision";
				break;
			case 24:
				strFieldName = "MngCom";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
				strFieldName = "MakeTime";
				break;
			case 27:
				strFieldName = "AutoClmFlag";
				break;
			case 28:
				strFieldName = "AppGrade";
				break;
			case 29:
				strFieldName = "Remark";
				break;
			case 30:
				strFieldName = "CheckType";
				break;
			case 31:
				strFieldName = "AppClmUWer";
				break;
			case 32:
				strFieldName = "AppActionType";
				break;
			case 33:
				strFieldName = "ClmUWNo";
				break;
			case 34:
				strFieldName = "ModifyDate";
				break;
			case 35:
				strFieldName = "GrpContNo";
				break;
			case 36:
				strFieldName = "ModifyTime";
				break;
			case 37:
				strFieldName = "CaseRelaNo";
				break;
			case 38:
				strFieldName = "ClmDepend";
				break;
			case 39:
				strFieldName = "CaseNo";
				break;
			case 40:
				strFieldName = "Operator";
				break;
			case 41:
				strFieldName = "GetDutyKind";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoClmDecision") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmDecision") ) {
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
		if( strFieldName.equals("AutoClmFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmDepend") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
