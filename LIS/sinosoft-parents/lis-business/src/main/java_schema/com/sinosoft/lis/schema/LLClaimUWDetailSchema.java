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
import com.sinosoft.lis.db.LLClaimUWDetailDB;

/*
 * <p>ClassName: LLClaimUWDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUWDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimUWDetailSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 核赔次数 */
	private String ClmUWNo;
	/** 保单号 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 受理事故号 */
	private String CaseRelaNo;
	/** 伤残类型 */
	private String DefoType;
	/** 集体合同号 */
	private String GrpContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 个单合同号 */
	private String ContNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 险种代码 */
	private String RiskCode;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 销售渠道 */
	private String SaleChnl;
	/** 代理人代码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 账单金额 */
	private double TabFeeMoney;
	/** 理算金额 */
	private double ClaimMoney;
	/** 核算赔付金额 */
	private double StandPay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 调整原因 */
	private String AdjReason;
	/** 调??备注 */
	private String AdjRemark;
	/** 赔付结论 */
	private String GiveType;
	/** 赔付结论描述 */
	private String GiveTypeDesc;
	/** 赔付结论依据 */
	private String GiveReason;
	/** 赔付结论依据描述 */
	private String GiveReasonDesc;
	/** 特殊备注 */
	private String SpecialRemark;
	/** 审核类型 */
	private String CheckType;
	/** 收据号 */
	private String ReceiptNo;
	/** 备注 */
	private String Remark;
	/** 申请审核人员 */
	private String AppClmUWer;
	/** 申请动作 */
	private String AppActionType;
	/** 核赔员 */
	private String ClmUWer;
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
	/** 承保时保单号 */
	private String NBPolNo;
	/** 预付标志 */
	private String PrepayFlag;
	/** 预付金额 */
	private double PrepaySum;
	/** 有效保额 */
	private double Amnt;
	/** 年度红利 */
	private double YearBonus;
	/** 终了红利 */
	private double EndBonus;
	/** 缴费宽限期 */
	private int GracePeriod;
	/** 给付类型 */
	private String CasePolType;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 帐户标志 */
	private String AcctFlag;
	/** 保全标志 */
	private String PosFlag;
	/** 保全批单号[出险之后] */
	private String PosEdorNo;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 附险影响主险标志 */
	private String EffectOnMainRisk;
	/** 附险影响主险的公式 */
	private String RiskCalCode;
	/** 加保保额 */
	private double AddAmnt;
	/** 保全批改类型 */
	private String PosEdorType;
	/** 险种类型 */
	private String RiskType;
	/** 回退号 */
	private String BackNo;
	/** 权限理赔额 */
	private double PopedomPay;
	/** 保费理赔额 */
	private double InsFeePay;

	public static final int FIELDNUM = 63;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimUWDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[8];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "ClmUWNo";
		pk[3] = "PolNo";
		pk[4] = "DutyCode";
		pk[5] = "GetDutyCode";
		pk[6] = "GetDutyKind";
		pk[7] = "CaseRelaNo";

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
		LLClaimUWDetailSchema cloned = (LLClaimUWDetailSchema)super.clone();
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
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>10)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值10");
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>6)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值6");
		GetDutyCode = aGetDutyCode;
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
		if(aGetDutyKind!=null && aGetDutyKind.length()>6)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值6");
		GetDutyKind = aGetDutyKind;
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
	/**
	* 1--普通伤残(使用7级34项残疾给付表)<p>
	* 2--普通伤残(使用老的残疾给付表)
	*/
	public String getDefoType()
	{
		return DefoType;
	}
	public void setDefoType(String aDefoType)
	{
		if(aDefoType!=null && aDefoType.length()>10)
			throw new IllegalArgumentException("伤残类型DefoType值"+aDefoType+"的长度"+aDefoType.length()+"大于最大值10");
		DefoType = aDefoType;
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个单合同号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
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
	/**
	* 录入原始金额
	*/
	public double getTabFeeMoney()
	{
		return TabFeeMoney;
	}
	public void setTabFeeMoney(double aTabFeeMoney)
	{
		TabFeeMoney = aTabFeeMoney;
	}
	public void setTabFeeMoney(String aTabFeeMoney)
	{
		if (aTabFeeMoney != null && !aTabFeeMoney.equals(""))
		{
			Double tDouble = new Double(aTabFeeMoney);
			double d = tDouble.doubleValue();
			TabFeeMoney = d;
		}
	}

	/**
	* 参与计算金额
	*/
	public double getClaimMoney()
	{
		return ClaimMoney;
	}
	public void setClaimMoney(double aClaimMoney)
	{
		ClaimMoney = aClaimMoney;
	}
	public void setClaimMoney(String aClaimMoney)
	{
		if (aClaimMoney != null && !aClaimMoney.equals(""))
		{
			Double tDouble = new Double(aClaimMoney);
			double d = tDouble.doubleValue();
			ClaimMoney = d;
		}
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

	public String getAdjReason()
	{
		return AdjReason;
	}
	public void setAdjReason(String aAdjReason)
	{
		if(aAdjReason!=null && aAdjReason.length()>6)
			throw new IllegalArgumentException("调整原因AdjReason值"+aAdjReason+"的长度"+aAdjReason.length()+"大于最大值6");
		AdjReason = aAdjReason;
	}
	public String getAdjRemark()
	{
		return AdjRemark;
	}
	public void setAdjRemark(String aAdjRemark)
	{
		if(aAdjRemark!=null && aAdjRemark.length()>1000)
			throw new IllegalArgumentException("调??备注AdjRemark值"+aAdjRemark+"的长度"+aAdjRemark.length()+"大于最大值1000");
		AdjRemark = aAdjRemark;
	}
	/**
	* 01给付<p>
	* 02拒付
	*/
	public String getGiveType()
	{
		return GiveType;
	}
	public void setGiveType(String aGiveType)
	{
		if(aGiveType!=null && aGiveType.length()>10)
			throw new IllegalArgumentException("赔付结论GiveType值"+aGiveType+"的长度"+aGiveType.length()+"大于最大值10");
		GiveType = aGiveType;
	}
	public String getGiveTypeDesc()
	{
		return GiveTypeDesc;
	}
	public void setGiveTypeDesc(String aGiveTypeDesc)
	{
		if(aGiveTypeDesc!=null && aGiveTypeDesc.length()>1000)
			throw new IllegalArgumentException("赔付结论描述GiveTypeDesc值"+aGiveTypeDesc+"的长度"+aGiveTypeDesc.length()+"大于最大值1000");
		GiveTypeDesc = aGiveTypeDesc;
	}
	public String getGiveReason()
	{
		return GiveReason;
	}
	public void setGiveReason(String aGiveReason)
	{
		if(aGiveReason!=null && aGiveReason.length()>10)
			throw new IllegalArgumentException("赔付结论依据GiveReason值"+aGiveReason+"的长度"+aGiveReason.length()+"大于最大值10");
		GiveReason = aGiveReason;
	}
	public String getGiveReasonDesc()
	{
		return GiveReasonDesc;
	}
	public void setGiveReasonDesc(String aGiveReasonDesc)
	{
		if(aGiveReasonDesc!=null && aGiveReasonDesc.length()>1000)
			throw new IllegalArgumentException("赔付结论依据描述GiveReasonDesc值"+aGiveReasonDesc+"的长度"+aGiveReasonDesc.length()+"大于最大值1000");
		GiveReasonDesc = aGiveReasonDesc;
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
	/**
	* 对应收据的号码，不包括诊疗的收据
	*/
	public String getReceiptNo()
	{
		return ReceiptNo;
	}
	public void setReceiptNo(String aReceiptNo)
	{
		if(aReceiptNo!=null && aReceiptNo.length()>20)
			throw new IllegalArgumentException("收据号ReceiptNo值"+aReceiptNo+"的长度"+aReceiptNo.length()+"大于最大值20");
		ReceiptNo = aReceiptNo;
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
	public String getNBPolNo()
	{
		return NBPolNo;
	}
	public void setNBPolNo(String aNBPolNo)
	{
		if(aNBPolNo!=null && aNBPolNo.length()>20)
			throw new IllegalArgumentException("承保时保单号NBPolNo值"+aNBPolNo+"的长度"+aNBPolNo.length()+"大于最大值20");
		NBPolNo = aNBPolNo;
	}
	/**
	* 0没有预付<p>
	* 1已经预付
	*/
	public String getPrepayFlag()
	{
		return PrepayFlag;
	}
	public void setPrepayFlag(String aPrepayFlag)
	{
		if(aPrepayFlag!=null && aPrepayFlag.length()>6)
			throw new IllegalArgumentException("预付标志PrepayFlag值"+aPrepayFlag+"的长度"+aPrepayFlag.length()+"大于最大值6");
		PrepayFlag = aPrepayFlag;
	}
	public double getPrepaySum()
	{
		return PrepaySum;
	}
	public void setPrepaySum(double aPrepaySum)
	{
		PrepaySum = aPrepaySum;
	}
	public void setPrepaySum(String aPrepaySum)
	{
		if (aPrepaySum != null && !aPrepaySum.equals(""))
		{
			Double tDouble = new Double(aPrepaySum);
			double d = tDouble.doubleValue();
			PrepaySum = d;
		}
	}

	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	public double getYearBonus()
	{
		return YearBonus;
	}
	public void setYearBonus(double aYearBonus)
	{
		YearBonus = aYearBonus;
	}
	public void setYearBonus(String aYearBonus)
	{
		if (aYearBonus != null && !aYearBonus.equals(""))
		{
			Double tDouble = new Double(aYearBonus);
			double d = tDouble.doubleValue();
			YearBonus = d;
		}
	}

	public double getEndBonus()
	{
		return EndBonus;
	}
	public void setEndBonus(double aEndBonus)
	{
		EndBonus = aEndBonus;
	}
	public void setEndBonus(String aEndBonus)
	{
		if (aEndBonus != null && !aEndBonus.equals(""))
		{
			Double tDouble = new Double(aEndBonus);
			double d = tDouble.doubleValue();
			EndBonus = d;
		}
	}

	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* 0 －－ 被保人<p>
	* 1 －－ 投保人<p>
	* 2 －－ 连带被保人
	*/
	public String getCasePolType()
	{
		return CasePolType;
	}
	public void setCasePolType(String aCasePolType)
	{
		if(aCasePolType!=null && aCasePolType.length()>6)
			throw new IllegalArgumentException("给付类型CasePolType值"+aCasePolType+"的长度"+aCasePolType.length()+"大于最大值6");
		CasePolType = aCasePolType;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getAcctFlag()
	{
		return AcctFlag;
	}
	public void setAcctFlag(String aAcctFlag)
	{
		if(aAcctFlag!=null && aAcctFlag.length()>10)
			throw new IllegalArgumentException("帐户标志AcctFlag值"+aAcctFlag+"的长度"+aAcctFlag.length()+"大于最大值10");
		AcctFlag = aAcctFlag;
	}
	/**
	* 0未做保全<p>
	* 1???做保全LPGet不发生变化,<p>
	* 2已做保??LPGet发生变化
	*/
	public String getPosFlag()
	{
		return PosFlag;
	}
	public void setPosFlag(String aPosFlag)
	{
		if(aPosFlag!=null && aPosFlag.length()>6)
			throw new IllegalArgumentException("保全标志PosFlag值"+aPosFlag+"的长度"+aPosFlag.length()+"大于最大值6");
		PosFlag = aPosFlag;
	}
	public String getPosEdorNo()
	{
		return PosEdorNo;
	}
	public void setPosEdorNo(String aPosEdorNo)
	{
		if(aPosEdorNo!=null && aPosEdorNo.length()>20)
			throw new IllegalArgumentException("保全批单号[出险之后]PosEdorNo值"+aPosEdorNo+"的长度"+aPosEdorNo.length()+"大于最大值20");
		PosEdorNo = aPosEdorNo;
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

	public String getEffectOnMainRisk()
	{
		return EffectOnMainRisk;
	}
	public void setEffectOnMainRisk(String aEffectOnMainRisk)
	{
		if(aEffectOnMainRisk!=null && aEffectOnMainRisk.length()>6)
			throw new IllegalArgumentException("附险影响主险标志EffectOnMainRisk值"+aEffectOnMainRisk+"的长度"+aEffectOnMainRisk.length()+"大于最大值6");
		EffectOnMainRisk = aEffectOnMainRisk;
	}
	public String getRiskCalCode()
	{
		return RiskCalCode;
	}
	public void setRiskCalCode(String aRiskCalCode)
	{
		if(aRiskCalCode!=null && aRiskCalCode.length()>400)
			throw new IllegalArgumentException("附险影响主险的公式RiskCalCode值"+aRiskCalCode+"的长度"+aRiskCalCode.length()+"大于最大值400");
		RiskCalCode = aRiskCalCode;
	}
	public double getAddAmnt()
	{
		return AddAmnt;
	}
	public void setAddAmnt(double aAddAmnt)
	{
		AddAmnt = aAddAmnt;
	}
	public void setAddAmnt(String aAddAmnt)
	{
		if (aAddAmnt != null && !aAddAmnt.equals(""))
		{
			Double tDouble = new Double(aAddAmnt);
			double d = tDouble.doubleValue();
			AddAmnt = d;
		}
	}

	public String getPosEdorType()
	{
		return PosEdorType;
	}
	public void setPosEdorType(String aPosEdorType)
	{
		if(aPosEdorType!=null && aPosEdorType.length()>6)
			throw new IllegalArgumentException("保全批改类型PosEdorType值"+aPosEdorType+"的长度"+aPosEdorType.length()+"大于最大值6");
		PosEdorType = aPosEdorType;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		if(aRiskType!=null && aRiskType.length()>6)
			throw new IllegalArgumentException("险种类型RiskType值"+aRiskType+"的长度"+aRiskType.length()+"大于最大值6");
		RiskType = aRiskType;
	}
	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		if(aBackNo!=null && aBackNo.length()>20)
			throw new IllegalArgumentException("回退号BackNo值"+aBackNo+"的长度"+aBackNo.length()+"大于最大值20");
		BackNo = aBackNo;
	}
	/**
	* [用于权限分配时的扣除额]
	*/
	public double getPopedomPay()
	{
		return PopedomPay;
	}
	public void setPopedomPay(double aPopedomPay)
	{
		PopedomPay = aPopedomPay;
	}
	public void setPopedomPay(String aPopedomPay)
	{
		if (aPopedomPay != null && !aPopedomPay.equals(""))
		{
			Double tDouble = new Double(aPopedomPay);
			double d = tDouble.doubleValue();
			PopedomPay = d;
		}
	}

	/**
	* [暂不用]
	*/
	public double getInsFeePay()
	{
		return InsFeePay;
	}
	public void setInsFeePay(double aInsFeePay)
	{
		InsFeePay = aInsFeePay;
	}
	public void setInsFeePay(String aInsFeePay)
	{
		if (aInsFeePay != null && !aInsFeePay.equals(""))
		{
			Double tDouble = new Double(aInsFeePay);
			double d = tDouble.doubleValue();
			InsFeePay = d;
		}
	}


	/**
	* 使用另外一个 LLClaimUWDetailSchema 对象给 Schema 赋值
	* @param: aLLClaimUWDetailSchema LLClaimUWDetailSchema
	**/
	public void setSchema(LLClaimUWDetailSchema aLLClaimUWDetailSchema)
	{
		this.ClmNo = aLLClaimUWDetailSchema.getClmNo();
		this.CaseNo = aLLClaimUWDetailSchema.getCaseNo();
		this.ClmUWNo = aLLClaimUWDetailSchema.getClmUWNo();
		this.PolNo = aLLClaimUWDetailSchema.getPolNo();
		this.DutyCode = aLLClaimUWDetailSchema.getDutyCode();
		this.GetDutyCode = aLLClaimUWDetailSchema.getGetDutyCode();
		this.GetDutyKind = aLLClaimUWDetailSchema.getGetDutyKind();
		this.CaseRelaNo = aLLClaimUWDetailSchema.getCaseRelaNo();
		this.DefoType = aLLClaimUWDetailSchema.getDefoType();
		this.GrpContNo = aLLClaimUWDetailSchema.getGrpContNo();
		this.GrpPolNo = aLLClaimUWDetailSchema.getGrpPolNo();
		this.ContNo = aLLClaimUWDetailSchema.getContNo();
		this.KindCode = aLLClaimUWDetailSchema.getKindCode();
		this.RiskVer = aLLClaimUWDetailSchema.getRiskVer();
		this.RiskCode = aLLClaimUWDetailSchema.getRiskCode();
		this.PolMngCom = aLLClaimUWDetailSchema.getPolMngCom();
		this.SaleChnl = aLLClaimUWDetailSchema.getSaleChnl();
		this.AgentCode = aLLClaimUWDetailSchema.getAgentCode();
		this.AgentGroup = aLLClaimUWDetailSchema.getAgentGroup();
		this.TabFeeMoney = aLLClaimUWDetailSchema.getTabFeeMoney();
		this.ClaimMoney = aLLClaimUWDetailSchema.getClaimMoney();
		this.StandPay = aLLClaimUWDetailSchema.getStandPay();
		this.RealPay = aLLClaimUWDetailSchema.getRealPay();
		this.AdjReason = aLLClaimUWDetailSchema.getAdjReason();
		this.AdjRemark = aLLClaimUWDetailSchema.getAdjRemark();
		this.GiveType = aLLClaimUWDetailSchema.getGiveType();
		this.GiveTypeDesc = aLLClaimUWDetailSchema.getGiveTypeDesc();
		this.GiveReason = aLLClaimUWDetailSchema.getGiveReason();
		this.GiveReasonDesc = aLLClaimUWDetailSchema.getGiveReasonDesc();
		this.SpecialRemark = aLLClaimUWDetailSchema.getSpecialRemark();
		this.CheckType = aLLClaimUWDetailSchema.getCheckType();
		this.ReceiptNo = aLLClaimUWDetailSchema.getReceiptNo();
		this.Remark = aLLClaimUWDetailSchema.getRemark();
		this.AppClmUWer = aLLClaimUWDetailSchema.getAppClmUWer();
		this.AppActionType = aLLClaimUWDetailSchema.getAppActionType();
		this.ClmUWer = aLLClaimUWDetailSchema.getClmUWer();
		this.Operator = aLLClaimUWDetailSchema.getOperator();
		this.MngCom = aLLClaimUWDetailSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLClaimUWDetailSchema.getMakeDate());
		this.MakeTime = aLLClaimUWDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimUWDetailSchema.getModifyDate());
		this.ModifyTime = aLLClaimUWDetailSchema.getModifyTime();
		this.NBPolNo = aLLClaimUWDetailSchema.getNBPolNo();
		this.PrepayFlag = aLLClaimUWDetailSchema.getPrepayFlag();
		this.PrepaySum = aLLClaimUWDetailSchema.getPrepaySum();
		this.Amnt = aLLClaimUWDetailSchema.getAmnt();
		this.YearBonus = aLLClaimUWDetailSchema.getYearBonus();
		this.EndBonus = aLLClaimUWDetailSchema.getEndBonus();
		this.GracePeriod = aLLClaimUWDetailSchema.getGracePeriod();
		this.CasePolType = aLLClaimUWDetailSchema.getCasePolType();
		this.CustomerNo = aLLClaimUWDetailSchema.getCustomerNo();
		this.AcctFlag = aLLClaimUWDetailSchema.getAcctFlag();
		this.PosFlag = aLLClaimUWDetailSchema.getPosFlag();
		this.PosEdorNo = aLLClaimUWDetailSchema.getPosEdorNo();
		this.CValiDate = fDate.getDate( aLLClaimUWDetailSchema.getCValiDate());
		this.EffectOnMainRisk = aLLClaimUWDetailSchema.getEffectOnMainRisk();
		this.RiskCalCode = aLLClaimUWDetailSchema.getRiskCalCode();
		this.AddAmnt = aLLClaimUWDetailSchema.getAddAmnt();
		this.PosEdorType = aLLClaimUWDetailSchema.getPosEdorType();
		this.RiskType = aLLClaimUWDetailSchema.getRiskType();
		this.BackNo = aLLClaimUWDetailSchema.getBackNo();
		this.PopedomPay = aLLClaimUWDetailSchema.getPopedomPay();
		this.InsFeePay = aLLClaimUWDetailSchema.getInsFeePay();
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

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("ClmUWNo") == null )
				this.ClmUWNo = null;
			else
				this.ClmUWNo = rs.getString("ClmUWNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

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

			this.TabFeeMoney = rs.getDouble("TabFeeMoney");
			this.ClaimMoney = rs.getDouble("ClaimMoney");
			this.StandPay = rs.getDouble("StandPay");
			this.RealPay = rs.getDouble("RealPay");
			if( rs.getString("AdjReason") == null )
				this.AdjReason = null;
			else
				this.AdjReason = rs.getString("AdjReason").trim();

			if( rs.getString("AdjRemark") == null )
				this.AdjRemark = null;
			else
				this.AdjRemark = rs.getString("AdjRemark").trim();

			if( rs.getString("GiveType") == null )
				this.GiveType = null;
			else
				this.GiveType = rs.getString("GiveType").trim();

			if( rs.getString("GiveTypeDesc") == null )
				this.GiveTypeDesc = null;
			else
				this.GiveTypeDesc = rs.getString("GiveTypeDesc").trim();

			if( rs.getString("GiveReason") == null )
				this.GiveReason = null;
			else
				this.GiveReason = rs.getString("GiveReason").trim();

			if( rs.getString("GiveReasonDesc") == null )
				this.GiveReasonDesc = null;
			else
				this.GiveReasonDesc = rs.getString("GiveReasonDesc").trim();

			if( rs.getString("SpecialRemark") == null )
				this.SpecialRemark = null;
			else
				this.SpecialRemark = rs.getString("SpecialRemark").trim();

			if( rs.getString("CheckType") == null )
				this.CheckType = null;
			else
				this.CheckType = rs.getString("CheckType").trim();

			if( rs.getString("ReceiptNo") == null )
				this.ReceiptNo = null;
			else
				this.ReceiptNo = rs.getString("ReceiptNo").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("AppClmUWer") == null )
				this.AppClmUWer = null;
			else
				this.AppClmUWer = rs.getString("AppClmUWer").trim();

			if( rs.getString("AppActionType") == null )
				this.AppActionType = null;
			else
				this.AppActionType = rs.getString("AppActionType").trim();

			if( rs.getString("ClmUWer") == null )
				this.ClmUWer = null;
			else
				this.ClmUWer = rs.getString("ClmUWer").trim();

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

			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("PrepayFlag") == null )
				this.PrepayFlag = null;
			else
				this.PrepayFlag = rs.getString("PrepayFlag").trim();

			this.PrepaySum = rs.getDouble("PrepaySum");
			this.Amnt = rs.getDouble("Amnt");
			this.YearBonus = rs.getDouble("YearBonus");
			this.EndBonus = rs.getDouble("EndBonus");
			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("CasePolType") == null )
				this.CasePolType = null;
			else
				this.CasePolType = rs.getString("CasePolType").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AcctFlag") == null )
				this.AcctFlag = null;
			else
				this.AcctFlag = rs.getString("AcctFlag").trim();

			if( rs.getString("PosFlag") == null )
				this.PosFlag = null;
			else
				this.PosFlag = rs.getString("PosFlag").trim();

			if( rs.getString("PosEdorNo") == null )
				this.PosEdorNo = null;
			else
				this.PosEdorNo = rs.getString("PosEdorNo").trim();

			this.CValiDate = rs.getDate("CValiDate");
			if( rs.getString("EffectOnMainRisk") == null )
				this.EffectOnMainRisk = null;
			else
				this.EffectOnMainRisk = rs.getString("EffectOnMainRisk").trim();

			if( rs.getString("RiskCalCode") == null )
				this.RiskCalCode = null;
			else
				this.RiskCalCode = rs.getString("RiskCalCode").trim();

			this.AddAmnt = rs.getDouble("AddAmnt");
			if( rs.getString("PosEdorType") == null )
				this.PosEdorType = null;
			else
				this.PosEdorType = rs.getString("PosEdorType").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			this.PopedomPay = rs.getDouble("PopedomPay");
			this.InsFeePay = rs.getDouble("InsFeePay");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimUWDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimUWDetailSchema getSchema()
	{
		LLClaimUWDetailSchema aLLClaimUWDetailSchema = new LLClaimUWDetailSchema();
		aLLClaimUWDetailSchema.setSchema(this);
		return aLLClaimUWDetailSchema;
	}

	public LLClaimUWDetailDB getDB()
	{
		LLClaimUWDetailDB aDBOper = new LLClaimUWDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TabFeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecialRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmUWer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrepayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrepaySum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(YearBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndBonus));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AcctFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosEdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EffectOnMainRisk)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosEdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PopedomPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsFeePay));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimUWDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClmUWNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			TabFeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			GiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			GiveTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			GiveReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			GiveReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			SpecialRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CheckType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ReceiptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AppClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			AppActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ClmUWer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			PrepayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			PrepaySum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,46,SysConst.PACKAGESPILTER))).doubleValue();
			YearBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).doubleValue();
			EndBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).intValue();
			CasePolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			AcctFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			PosFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			PosEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55,SysConst.PACKAGESPILTER));
			EffectOnMainRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			AddAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).doubleValue();
			PosEdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			PopedomPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,62,SysConst.PACKAGESPILTER))).doubleValue();
			InsFeePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimUWDetailSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("ClmUWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
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
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TabFeeMoney));
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimMoney));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjReason));
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjRemark));
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveType));
		}
		if (FCode.equalsIgnoreCase("GiveTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveTypeDesc));
		}
		if (FCode.equalsIgnoreCase("GiveReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveReason));
		}
		if (FCode.equalsIgnoreCase("GiveReasonDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveReasonDesc));
		}
		if (FCode.equalsIgnoreCase("SpecialRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecialRemark));
		}
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckType));
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptNo));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("AppClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppClmUWer));
		}
		if (FCode.equalsIgnoreCase("AppActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppActionType));
		}
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmUWer));
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("PrepayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepayFlag));
		}
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepaySum));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("YearBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YearBonus));
		}
		if (FCode.equalsIgnoreCase("EndBonus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndBonus));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePolType));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AcctFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcctFlag));
		}
		if (FCode.equalsIgnoreCase("PosFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosFlag));
		}
		if (FCode.equalsIgnoreCase("PosEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosEdorNo));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("EffectOnMainRisk"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EffectOnMainRisk));
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalCode));
		}
		if (FCode.equalsIgnoreCase("AddAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddAmnt));
		}
		if (FCode.equalsIgnoreCase("PosEdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosEdorType));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("PopedomPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopedomPay));
		}
		if (FCode.equalsIgnoreCase("InsFeePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsFeePay));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClmUWNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 19:
				strFieldValue = String.valueOf(TabFeeMoney);
				break;
			case 20:
				strFieldValue = String.valueOf(ClaimMoney);
				break;
			case 21:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 22:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(GiveType);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(GiveTypeDesc);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(GiveReason);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(GiveReasonDesc);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SpecialRemark);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(CheckType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ReceiptNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AppClmUWer);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(AppActionType);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ClmUWer);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
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
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(PrepayFlag);
				break;
			case 44:
				strFieldValue = String.valueOf(PrepaySum);
				break;
			case 45:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 46:
				strFieldValue = String.valueOf(YearBonus);
				break;
			case 47:
				strFieldValue = String.valueOf(EndBonus);
				break;
			case 48:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(CasePolType);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(AcctFlag);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(PosFlag);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(PosEdorNo);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(EffectOnMainRisk);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 57:
				strFieldValue = String.valueOf(AddAmnt);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(PosEdorType);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 61:
				strFieldValue = String.valueOf(PopedomPay);
				break;
			case 62:
				strFieldValue = String.valueOf(InsFeePay);
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefoType = FValue.trim();
			}
			else
				DefoType = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
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
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TabFeeMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjReason = FValue.trim();
			}
			else
				AdjReason = null;
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjRemark = FValue.trim();
			}
			else
				AdjRemark = null;
		}
		if (FCode.equalsIgnoreCase("GiveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveType = FValue.trim();
			}
			else
				GiveType = null;
		}
		if (FCode.equalsIgnoreCase("GiveTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveTypeDesc = FValue.trim();
			}
			else
				GiveTypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("GiveReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveReason = FValue.trim();
			}
			else
				GiveReason = null;
		}
		if (FCode.equalsIgnoreCase("GiveReasonDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveReasonDesc = FValue.trim();
			}
			else
				GiveReasonDesc = null;
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
		if (FCode.equalsIgnoreCase("CheckType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckType = FValue.trim();
			}
			else
				CheckType = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiptNo = FValue.trim();
			}
			else
				ReceiptNo = null;
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
		if (FCode.equalsIgnoreCase("ClmUWer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmUWer = FValue.trim();
			}
			else
				ClmUWer = null;
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
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
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrepaySum = d;
			}
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("YearBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				YearBonus = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndBonus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EndBonus = d;
			}
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("CasePolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePolType = FValue.trim();
			}
			else
				CasePolType = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("AcctFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcctFlag = FValue.trim();
			}
			else
				AcctFlag = null;
		}
		if (FCode.equalsIgnoreCase("PosFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosFlag = FValue.trim();
			}
			else
				PosFlag = null;
		}
		if (FCode.equalsIgnoreCase("PosEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosEdorNo = FValue.trim();
			}
			else
				PosEdorNo = null;
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
		if (FCode.equalsIgnoreCase("EffectOnMainRisk"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EffectOnMainRisk = FValue.trim();
			}
			else
				EffectOnMainRisk = null;
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalCode = FValue.trim();
			}
			else
				RiskCalCode = null;
		}
		if (FCode.equalsIgnoreCase("AddAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PosEdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PosEdorType = FValue.trim();
			}
			else
				PosEdorType = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
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
		if (FCode.equalsIgnoreCase("PopedomPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PopedomPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsFeePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsFeePay = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLClaimUWDetailSchema other = (LLClaimUWDetailSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& ClmUWNo.equals(other.getClmUWNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& DefoType.equals(other.getDefoType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskCode.equals(other.getRiskCode())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& TabFeeMoney == other.getTabFeeMoney()
			&& ClaimMoney == other.getClaimMoney()
			&& StandPay == other.getStandPay()
			&& RealPay == other.getRealPay()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& GiveType.equals(other.getGiveType())
			&& GiveTypeDesc.equals(other.getGiveTypeDesc())
			&& GiveReason.equals(other.getGiveReason())
			&& GiveReasonDesc.equals(other.getGiveReasonDesc())
			&& SpecialRemark.equals(other.getSpecialRemark())
			&& CheckType.equals(other.getCheckType())
			&& ReceiptNo.equals(other.getReceiptNo())
			&& Remark.equals(other.getRemark())
			&& AppClmUWer.equals(other.getAppClmUWer())
			&& AppActionType.equals(other.getAppActionType())
			&& ClmUWer.equals(other.getClmUWer())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& NBPolNo.equals(other.getNBPolNo())
			&& PrepayFlag.equals(other.getPrepayFlag())
			&& PrepaySum == other.getPrepaySum()
			&& Amnt == other.getAmnt()
			&& YearBonus == other.getYearBonus()
			&& EndBonus == other.getEndBonus()
			&& GracePeriod == other.getGracePeriod()
			&& CasePolType.equals(other.getCasePolType())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AcctFlag.equals(other.getAcctFlag())
			&& PosFlag.equals(other.getPosFlag())
			&& PosEdorNo.equals(other.getPosEdorNo())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& EffectOnMainRisk.equals(other.getEffectOnMainRisk())
			&& RiskCalCode.equals(other.getRiskCalCode())
			&& AddAmnt == other.getAddAmnt()
			&& PosEdorType.equals(other.getPosEdorType())
			&& RiskType.equals(other.getRiskType())
			&& BackNo.equals(other.getBackNo())
			&& PopedomPay == other.getPopedomPay()
			&& InsFeePay == other.getInsFeePay();
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
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolNo") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 6;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 7;
		}
		if( strFieldName.equals("DefoType") ) {
			return 8;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 10;
		}
		if( strFieldName.equals("ContNo") ) {
			return 11;
		}
		if( strFieldName.equals("KindCode") ) {
			return 12;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 13;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 14;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 15;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 17;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 18;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return 19;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return 20;
		}
		if( strFieldName.equals("StandPay") ) {
			return 21;
		}
		if( strFieldName.equals("RealPay") ) {
			return 22;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 23;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 24;
		}
		if( strFieldName.equals("GiveType") ) {
			return 25;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return 26;
		}
		if( strFieldName.equals("GiveReason") ) {
			return 27;
		}
		if( strFieldName.equals("GiveReasonDesc") ) {
			return 28;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return 29;
		}
		if( strFieldName.equals("CheckType") ) {
			return 30;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return 31;
		}
		if( strFieldName.equals("Remark") ) {
			return 32;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return 33;
		}
		if( strFieldName.equals("AppActionType") ) {
			return 34;
		}
		if( strFieldName.equals("ClmUWer") ) {
			return 35;
		}
		if( strFieldName.equals("Operator") ) {
			return 36;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("NBPolNo") ) {
			return 42;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return 43;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return 44;
		}
		if( strFieldName.equals("Amnt") ) {
			return 45;
		}
		if( strFieldName.equals("YearBonus") ) {
			return 46;
		}
		if( strFieldName.equals("EndBonus") ) {
			return 47;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 48;
		}
		if( strFieldName.equals("CasePolType") ) {
			return 49;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 50;
		}
		if( strFieldName.equals("AcctFlag") ) {
			return 51;
		}
		if( strFieldName.equals("PosFlag") ) {
			return 52;
		}
		if( strFieldName.equals("PosEdorNo") ) {
			return 53;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 54;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return 55;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 56;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return 57;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return 58;
		}
		if( strFieldName.equals("RiskType") ) {
			return 59;
		}
		if( strFieldName.equals("BackNo") ) {
			return 60;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return 61;
		}
		if( strFieldName.equals("InsFeePay") ) {
			return 62;
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
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "ClmUWNo";
				break;
			case 3:
				strFieldName = "PolNo";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "GetDutyCode";
				break;
			case 6:
				strFieldName = "GetDutyKind";
				break;
			case 7:
				strFieldName = "CaseRelaNo";
				break;
			case 8:
				strFieldName = "DefoType";
				break;
			case 9:
				strFieldName = "GrpContNo";
				break;
			case 10:
				strFieldName = "GrpPolNo";
				break;
			case 11:
				strFieldName = "ContNo";
				break;
			case 12:
				strFieldName = "KindCode";
				break;
			case 13:
				strFieldName = "RiskVer";
				break;
			case 14:
				strFieldName = "RiskCode";
				break;
			case 15:
				strFieldName = "PolMngCom";
				break;
			case 16:
				strFieldName = "SaleChnl";
				break;
			case 17:
				strFieldName = "AgentCode";
				break;
			case 18:
				strFieldName = "AgentGroup";
				break;
			case 19:
				strFieldName = "TabFeeMoney";
				break;
			case 20:
				strFieldName = "ClaimMoney";
				break;
			case 21:
				strFieldName = "StandPay";
				break;
			case 22:
				strFieldName = "RealPay";
				break;
			case 23:
				strFieldName = "AdjReason";
				break;
			case 24:
				strFieldName = "AdjRemark";
				break;
			case 25:
				strFieldName = "GiveType";
				break;
			case 26:
				strFieldName = "GiveTypeDesc";
				break;
			case 27:
				strFieldName = "GiveReason";
				break;
			case 28:
				strFieldName = "GiveReasonDesc";
				break;
			case 29:
				strFieldName = "SpecialRemark";
				break;
			case 30:
				strFieldName = "CheckType";
				break;
			case 31:
				strFieldName = "ReceiptNo";
				break;
			case 32:
				strFieldName = "Remark";
				break;
			case 33:
				strFieldName = "AppClmUWer";
				break;
			case 34:
				strFieldName = "AppActionType";
				break;
			case 35:
				strFieldName = "ClmUWer";
				break;
			case 36:
				strFieldName = "Operator";
				break;
			case 37:
				strFieldName = "MngCom";
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
				strFieldName = "NBPolNo";
				break;
			case 43:
				strFieldName = "PrepayFlag";
				break;
			case 44:
				strFieldName = "PrepaySum";
				break;
			case 45:
				strFieldName = "Amnt";
				break;
			case 46:
				strFieldName = "YearBonus";
				break;
			case 47:
				strFieldName = "EndBonus";
				break;
			case 48:
				strFieldName = "GracePeriod";
				break;
			case 49:
				strFieldName = "CasePolType";
				break;
			case 50:
				strFieldName = "CustomerNo";
				break;
			case 51:
				strFieldName = "AcctFlag";
				break;
			case 52:
				strFieldName = "PosFlag";
				break;
			case 53:
				strFieldName = "PosEdorNo";
				break;
			case 54:
				strFieldName = "CValiDate";
				break;
			case 55:
				strFieldName = "EffectOnMainRisk";
				break;
			case 56:
				strFieldName = "RiskCalCode";
				break;
			case 57:
				strFieldName = "AddAmnt";
				break;
			case 58:
				strFieldName = "PosEdorType";
				break;
			case 59:
				strFieldName = "RiskType";
				break;
			case 60:
				strFieldName = "BackNo";
				break;
			case 61:
				strFieldName = "PopedomPay";
				break;
			case 62:
				strFieldName = "InsFeePay";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
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
		if( strFieldName.equals("TabFeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GiveReasonDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppClmUWer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmUWer") ) {
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
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("YearBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndBonus") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CasePolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcctFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PosFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PosEdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsFeePay") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 45:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 46:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_INT;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 62:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
