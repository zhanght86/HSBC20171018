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
import com.sinosoft.lis.db.LLClaimDetailDB;

/*
 * <p>ClassName: LLClaimDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_7
 */
public class LLClaimDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimDetailSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 保单号 */
	private String PolNo;
	/** 保单类型 */
	private String PolSort;
	/** 保单性质 */
	private String PolType;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 受理事故号 */
	private String CaseRelaNo;
	/** 伤残类型 */
	private String DefoType;
	/** 立案号 */
	private String RgtNo;
	/** 拒赔号 */
	private String DeclineNo;
	/** 统计类别 */
	private String StatType;
	/** 集体合同号 */
	private String GrpContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 个单合同号 */
	private String ContNo;
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
	/** 账单金额 */
	private double TabFeeMoney;
	/** 理算金额 */
	private double ClaimMoney;
	/** 拒付金额 */
	private double DeclineAmnt;
	/** 溢额 */
	private double OverAmnt;
	/** 核算赔付金额 */
	private double StandPay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 调整原因 */
	private String AdjReason;
	/** 调整备注 */
	private String AdjRemark;
	/** 先期给付金额 */
	private double PreGiveAmnt;
	/** 自费给付金额 */
	private double SelfGiveAmnt;
	/** 不合理费用金额 */
	private double RefuseAmnt;
	/** 其它已给付 */
	private double OtherAmnt;
	/** 免赔额 */
	private double OutDutyAmnt;
	/** 免赔比例 */
	private double OutDutyRate;
	/** 通融给付金额 */
	private double ApproveAmnt;
	/** 通融给付原因代码 */
	private String ApproveCode;
	/** 协议给付 */
	private double AgreeAmnt;
	/** 协议给付原因代码 */
	private String AgreeCode;
	/** 赔付结论 */
	private String GiveType;
	/** 赔付结论描述 */
	private String GiveTypeDesc;
	/** 赔付结论依据【用作拒付原因】 */
	private String GiveReason;
	/** 赔付结论依据描述【用作拒付依据】 */
	private String GiveReasonDesc;
	/** 特殊备注 */
	private String SpecialRemark;
	/** 预付标志 */
	private String PrepayFlag;
	/** 预付金额 */
	private double PrepaySum;
	/** 管理机构 */
	private String MngCom;
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
	/** 承保时保单号 */
	private String NBPolNo;
	/** 保全批改类型 */
	private String PosEdorType;
	/** 险种类型 */
	private String RiskType;
	/** 权限理赔额 */
	private double PopedomPay;
	/** 保费理赔额 */
	private double InsFeePay;
	/** 业务状态 */
	private String OperState;
	/** 币别 */
	private String Currency;
	/** 业务类型 */
	private String BussType;
	/** 业务号 */
	private String BussNo;
	/** 是否赔付公共保额 */
	private String IsPublicAmnt;
	/** 公共保额险种 */
	private String PublicRiskCode;
	/** 公共保额责任 */
	private String PublicDutyCode;
	/** 赔付公共保额 */
	private double PublicAmnt;
	/** 本金 */
	private double Salary;
	/** 给付类型2 */
	private String GiveClass;
	/** 自付二赔付金额 */
	private String IndividualPayBUsedLimit;
	/** 自费赔付金额 */
	private String SelfCalculateUsedLimint;
	/** 牙科赔付金额 */
	private String DentistryPay;
	/** 体检赔付金额 */
	private String PhysicalPay;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 88;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "PolNo";
		pk[3] = "GetDutyKind";
		pk[4] = "GetDutyCode";
		pk[5] = "CaseRelaNo";

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
		LLClaimDetailSchema cloned = (LLClaimDetailSchema)super.clone();
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
	/**
	* C -- C表保单<p>
	* B -- B表保单<p>
	* A -- 未承保保单
	*/
	public String getPolSort()
	{
		return PolSort;
	}
	public void setPolSort(String aPolSort)
	{
		if(aPolSort!=null && aPolSort.length()>6)
			throw new IllegalArgumentException("保单类型PolSort值"+aPolSort+"的长度"+aPolSort.length()+"大于最大值6");
		PolSort = aPolSort;
	}
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		if(aPolType!=null && aPolType.length()>6)
			throw new IllegalArgumentException("保单性质PolType值"+aPolType+"的长度"+aPolType.length()+"大于最大值6");
		PolType = aPolType;
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
	/**
	* 100  意外医疗<p>
	* 101  意外伤残<p>
	* 102  意外死亡<p>
	* 103  意外高残<p>
	* 104  意外大病<p>
	* 105  意外特种疾病<p>
	* 106  意外失业失能<p>
	* 109  意外豁免<p>
	* 200  疾病医疗<p>
	* 201  疾病伤残<p>
	* 202  疾病死亡<p>
	* 203  疾病高残<p>
	* 204  疾病大病<p>
	* 205  疾病特种疾病<p>
	* 206  疾病失业失能<p>
	* 209  疾病豁免
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
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>10)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值10");
		GetDutyCode = aGetDutyCode;
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
	* 【不用】
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
	public String getDeclineNo()
	{
		return DeclineNo;
	}
	public void setDeclineNo(String aDeclineNo)
	{
		if(aDeclineNo!=null && aDeclineNo.length()>20)
			throw new IllegalArgumentException("拒赔号DeclineNo值"+aDeclineNo+"的长度"+aDeclineNo.length()+"大于最大值20");
		DeclineNo = aDeclineNo;
	}
	public String getStatType()
	{
		return StatType;
	}
	public void setStatType(String aStatType)
	{
		if(aStatType!=null && aStatType.length()>2)
			throw new IllegalArgumentException("统计类别StatType值"+aStatType+"的长度"+aStatType.length()+"大于最大值2");
		StatType = aStatType;
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

	/**
	* 【不用】
	*/
	public double getDeclineAmnt()
	{
		return DeclineAmnt;
	}
	public void setDeclineAmnt(double aDeclineAmnt)
	{
		DeclineAmnt = aDeclineAmnt;
	}
	public void setDeclineAmnt(String aDeclineAmnt)
	{
		if (aDeclineAmnt != null && !aDeclineAmnt.equals(""))
		{
			Double tDouble = new Double(aDeclineAmnt);
			double d = tDouble.doubleValue();
			DeclineAmnt = d;
		}
	}

	/**
	* 【不用】
	*/
	public double getOverAmnt()
	{
		return OverAmnt;
	}
	public void setOverAmnt(double aOverAmnt)
	{
		OverAmnt = aOverAmnt;
	}
	public void setOverAmnt(String aOverAmnt)
	{
		if (aOverAmnt != null && !aOverAmnt.equals(""))
		{
			Double tDouble = new Double(aOverAmnt);
			double d = tDouble.doubleValue();
			OverAmnt = d;
		}
	}

	/**
	* 计算出来的金额
	*/
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

	/**
	* 最后赔付的金额
	*/
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

	/**
	* 01 	退还续期保费<p>
	* 02 	退还剩余保费<p>
	* 03 	退还现金价值<p>
	* 04 	累计年度红利<p>
	* 05 	终了红利<p>
	* 06 	附加险退保<p>
	* 07 	扣除自垫本金<p>
	* 08 	扣除自垫利息<p>
	* 09 	扣除保单质押贷款本金<p>
	* 10 	扣除保单质押贷款利息<p>
	* 11 	扣除理赔事故发生后超额给付年金<p>
	* 12 	延迟报案导致查勘费扣除<p>
	* 13 	其他
	*/
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
			throw new IllegalArgumentException("调整备注AdjRemark值"+aAdjRemark+"的长度"+aAdjRemark.length()+"大于最大值1000");
		AdjRemark = aAdjRemark;
	}
	/**
	* 第三方已经支付的金额,可以当作预付处理
	*/
	public double getPreGiveAmnt()
	{
		return PreGiveAmnt;
	}
	public void setPreGiveAmnt(double aPreGiveAmnt)
	{
		PreGiveAmnt = aPreGiveAmnt;
	}
	public void setPreGiveAmnt(String aPreGiveAmnt)
	{
		if (aPreGiveAmnt != null && !aPreGiveAmnt.equals(""))
		{
			Double tDouble = new Double(aPreGiveAmnt);
			double d = tDouble.doubleValue();
			PreGiveAmnt = d;
		}
	}

	/**
	* 【不用】
	*/
	public double getSelfGiveAmnt()
	{
		return SelfGiveAmnt;
	}
	public void setSelfGiveAmnt(double aSelfGiveAmnt)
	{
		SelfGiveAmnt = aSelfGiveAmnt;
	}
	public void setSelfGiveAmnt(String aSelfGiveAmnt)
	{
		if (aSelfGiveAmnt != null && !aSelfGiveAmnt.equals(""))
		{
			Double tDouble = new Double(aSelfGiveAmnt);
			double d = tDouble.doubleValue();
			SelfGiveAmnt = d;
		}
	}

	/**
	* 【不用】
	*/
	public double getRefuseAmnt()
	{
		return RefuseAmnt;
	}
	public void setRefuseAmnt(double aRefuseAmnt)
	{
		RefuseAmnt = aRefuseAmnt;
	}
	public void setRefuseAmnt(String aRefuseAmnt)
	{
		if (aRefuseAmnt != null && !aRefuseAmnt.equals(""))
		{
			Double tDouble = new Double(aRefuseAmnt);
			double d = tDouble.doubleValue();
			RefuseAmnt = d;
		}
	}

	/**
	* 【不用】
	*/
	public double getOtherAmnt()
	{
		return OtherAmnt;
	}
	public void setOtherAmnt(double aOtherAmnt)
	{
		OtherAmnt = aOtherAmnt;
	}
	public void setOtherAmnt(String aOtherAmnt)
	{
		if (aOtherAmnt != null && !aOtherAmnt.equals(""))
		{
			Double tDouble = new Double(aOtherAmnt);
			double d = tDouble.doubleValue();
			OtherAmnt = d;
		}
	}

	public double getOutDutyAmnt()
	{
		return OutDutyAmnt;
	}
	public void setOutDutyAmnt(double aOutDutyAmnt)
	{
		OutDutyAmnt = aOutDutyAmnt;
	}
	public void setOutDutyAmnt(String aOutDutyAmnt)
	{
		if (aOutDutyAmnt != null && !aOutDutyAmnt.equals(""))
		{
			Double tDouble = new Double(aOutDutyAmnt);
			double d = tDouble.doubleValue();
			OutDutyAmnt = d;
		}
	}

	public double getOutDutyRate()
	{
		return OutDutyRate;
	}
	public void setOutDutyRate(double aOutDutyRate)
	{
		OutDutyRate = aOutDutyRate;
	}
	public void setOutDutyRate(String aOutDutyRate)
	{
		if (aOutDutyRate != null && !aOutDutyRate.equals(""))
		{
			Double tDouble = new Double(aOutDutyRate);
			double d = tDouble.doubleValue();
			OutDutyRate = d;
		}
	}

	/**
	* 【不用】
	*/
	public double getApproveAmnt()
	{
		return ApproveAmnt;
	}
	public void setApproveAmnt(double aApproveAmnt)
	{
		ApproveAmnt = aApproveAmnt;
	}
	public void setApproveAmnt(String aApproveAmnt)
	{
		if (aApproveAmnt != null && !aApproveAmnt.equals(""))
		{
			Double tDouble = new Double(aApproveAmnt);
			double d = tDouble.doubleValue();
			ApproveAmnt = d;
		}
	}

	/**
	* 【不用】
	*/
	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		if(aApproveCode!=null && aApproveCode.length()>2)
			throw new IllegalArgumentException("通融给付原因代码ApproveCode值"+aApproveCode+"的长度"+aApproveCode.length()+"大于最大值2");
		ApproveCode = aApproveCode;
	}
	/**
	* 【不用】
	*/
	public double getAgreeAmnt()
	{
		return AgreeAmnt;
	}
	public void setAgreeAmnt(double aAgreeAmnt)
	{
		AgreeAmnt = aAgreeAmnt;
	}
	public void setAgreeAmnt(String aAgreeAmnt)
	{
		if (aAgreeAmnt != null && !aAgreeAmnt.equals(""))
		{
			Double tDouble = new Double(aAgreeAmnt);
			double d = tDouble.doubleValue();
			AgreeAmnt = d;
		}
	}

	/**
	* 【不用】
	*/
	public String getAgreeCode()
	{
		return AgreeCode;
	}
	public void setAgreeCode(String aAgreeCode)
	{
		if(aAgreeCode!=null && aAgreeCode.length()>2)
			throw new IllegalArgumentException("协议给付原因代码AgreeCode值"+aAgreeCode+"的长度"+aAgreeCode.length()+"大于最大值2");
		AgreeCode = aAgreeCode;
	}
	/**
	* 0给付<p>
	* 1拒付<p>
	* 2不给
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
			throw new IllegalArgumentException("赔付结论依据【用作拒付原因】GiveReason值"+aGiveReason+"的长度"+aGiveReason.length()+"大于最大值10");
		GiveReason = aGiveReason;
	}
	public String getGiveReasonDesc()
	{
		return GiveReasonDesc;
	}
	public void setGiveReasonDesc(String aGiveReasonDesc)
	{
		if(aGiveReasonDesc!=null && aGiveReasonDesc.length()>1000)
			throw new IllegalArgumentException("赔付结论依据描述【用作拒付依据】GiveReasonDesc值"+aGiveReasonDesc+"的长度"+aGiveReasonDesc.length()+"大于最大值1000");
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
	* 00 －－ 投保人<p>
	* 01 －－ 被保人
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
	/**
	* 0非帐户<p>
	* 1帐户
	*/
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
	* 1已做保全LPGet不发生变化,<p>
	* 2已做保全LPGet发生变化
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

	/**
	* 01影响主险<p>
	* 000000或Null不影响
	*/
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
	* 标识业务上不能用的更新、删除的备份数据 operstate='1'，正常数据就是0，或为空
	*/
	public String getOperState()
	{
		return OperState;
	}
	public void setOperState(String aOperState)
	{
		if(aOperState!=null && aOperState.length()>10)
			throw new IllegalArgumentException("业务状态OperState值"+aOperState+"的长度"+aOperState.length()+"大于最大值10");
		OperState = aOperState;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>10)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值10");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getIsPublicAmnt()
	{
		return IsPublicAmnt;
	}
	public void setIsPublicAmnt(String aIsPublicAmnt)
	{
		if(aIsPublicAmnt!=null && aIsPublicAmnt.length()>6)
			throw new IllegalArgumentException("是否赔付公共保额IsPublicAmnt值"+aIsPublicAmnt+"的长度"+aIsPublicAmnt.length()+"大于最大值6");
		IsPublicAmnt = aIsPublicAmnt;
	}
	public String getPublicRiskCode()
	{
		return PublicRiskCode;
	}
	public void setPublicRiskCode(String aPublicRiskCode)
	{
		if(aPublicRiskCode!=null && aPublicRiskCode.length()>10)
			throw new IllegalArgumentException("公共保额险种PublicRiskCode值"+aPublicRiskCode+"的长度"+aPublicRiskCode.length()+"大于最大值10");
		PublicRiskCode = aPublicRiskCode;
	}
	public String getPublicDutyCode()
	{
		return PublicDutyCode;
	}
	public void setPublicDutyCode(String aPublicDutyCode)
	{
		if(aPublicDutyCode!=null && aPublicDutyCode.length()>10)
			throw new IllegalArgumentException("公共保额责任PublicDutyCode值"+aPublicDutyCode+"的长度"+aPublicDutyCode.length()+"大于最大值10");
		PublicDutyCode = aPublicDutyCode;
	}
	public double getPublicAmnt()
	{
		return PublicAmnt;
	}
	public void setPublicAmnt(double aPublicAmnt)
	{
		PublicAmnt = aPublicAmnt;
	}
	public void setPublicAmnt(String aPublicAmnt)
	{
		if (aPublicAmnt != null && !aPublicAmnt.equals(""))
		{
			Double tDouble = new Double(aPublicAmnt);
			double d = tDouble.doubleValue();
			PublicAmnt = d;
		}
	}

	public double getSalary()
	{
		return Salary;
	}
	public void setSalary(double aSalary)
	{
		Salary = aSalary;
	}
	public void setSalary(String aSalary)
	{
		if (aSalary != null && !aSalary.equals(""))
		{
			Double tDouble = new Double(aSalary);
			double d = tDouble.doubleValue();
			Salary = d;
		}
	}

	/**
	* 0-赔款，1-保费
	*/
	public String getGiveClass()
	{
		return GiveClass;
	}
	public void setGiveClass(String aGiveClass)
	{
		if(aGiveClass!=null && aGiveClass.length()>6)
			throw new IllegalArgumentException("给付类型2GiveClass值"+aGiveClass+"的长度"+aGiveClass.length()+"大于最大值6");
		GiveClass = aGiveClass;
	}
	public String getIndividualPayBUsedLimit()
	{
		return IndividualPayBUsedLimit;
	}
	public void setIndividualPayBUsedLimit(String aIndividualPayBUsedLimit)
	{
		if(aIndividualPayBUsedLimit!=null && aIndividualPayBUsedLimit.length()>20)
			throw new IllegalArgumentException("自付二赔付金额IndividualPayBUsedLimit值"+aIndividualPayBUsedLimit+"的长度"+aIndividualPayBUsedLimit.length()+"大于最大值20");
		IndividualPayBUsedLimit = aIndividualPayBUsedLimit;
	}
	public String getSelfCalculateUsedLimint()
	{
		return SelfCalculateUsedLimint;
	}
	public void setSelfCalculateUsedLimint(String aSelfCalculateUsedLimint)
	{
		if(aSelfCalculateUsedLimint!=null && aSelfCalculateUsedLimint.length()>20)
			throw new IllegalArgumentException("自费赔付金额SelfCalculateUsedLimint值"+aSelfCalculateUsedLimint+"的长度"+aSelfCalculateUsedLimint.length()+"大于最大值20");
		SelfCalculateUsedLimint = aSelfCalculateUsedLimint;
	}
	public String getDentistryPay()
	{
		return DentistryPay;
	}
	public void setDentistryPay(String aDentistryPay)
	{
		if(aDentistryPay!=null && aDentistryPay.length()>20)
			throw new IllegalArgumentException("牙科赔付金额DentistryPay值"+aDentistryPay+"的长度"+aDentistryPay.length()+"大于最大值20");
		DentistryPay = aDentistryPay;
	}
	public String getPhysicalPay()
	{
		return PhysicalPay;
	}
	public void setPhysicalPay(String aPhysicalPay)
	{
		if(aPhysicalPay!=null && aPhysicalPay.length()>20)
			throw new IllegalArgumentException("体检赔付金额PhysicalPay值"+aPhysicalPay+"的长度"+aPhysicalPay.length()+"大于最大值20");
		PhysicalPay = aPhysicalPay;
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
	* 使用另外一个 LLClaimDetailSchema 对象给 Schema 赋值
	* @param: aLLClaimDetailSchema LLClaimDetailSchema
	**/
	public void setSchema(LLClaimDetailSchema aLLClaimDetailSchema)
	{
		this.ClmNo = aLLClaimDetailSchema.getClmNo();
		this.CaseNo = aLLClaimDetailSchema.getCaseNo();
		this.PolNo = aLLClaimDetailSchema.getPolNo();
		this.PolSort = aLLClaimDetailSchema.getPolSort();
		this.PolType = aLLClaimDetailSchema.getPolType();
		this.DutyCode = aLLClaimDetailSchema.getDutyCode();
		this.GetDutyKind = aLLClaimDetailSchema.getGetDutyKind();
		this.GetDutyCode = aLLClaimDetailSchema.getGetDutyCode();
		this.CaseRelaNo = aLLClaimDetailSchema.getCaseRelaNo();
		this.DefoType = aLLClaimDetailSchema.getDefoType();
		this.RgtNo = aLLClaimDetailSchema.getRgtNo();
		this.DeclineNo = aLLClaimDetailSchema.getDeclineNo();
		this.StatType = aLLClaimDetailSchema.getStatType();
		this.GrpContNo = aLLClaimDetailSchema.getGrpContNo();
		this.GrpPolNo = aLLClaimDetailSchema.getGrpPolNo();
		this.ContNo = aLLClaimDetailSchema.getContNo();
		this.KindCode = aLLClaimDetailSchema.getKindCode();
		this.RiskCode = aLLClaimDetailSchema.getRiskCode();
		this.RiskVer = aLLClaimDetailSchema.getRiskVer();
		this.PolMngCom = aLLClaimDetailSchema.getPolMngCom();
		this.SaleChnl = aLLClaimDetailSchema.getSaleChnl();
		this.AgentCode = aLLClaimDetailSchema.getAgentCode();
		this.AgentGroup = aLLClaimDetailSchema.getAgentGroup();
		this.TabFeeMoney = aLLClaimDetailSchema.getTabFeeMoney();
		this.ClaimMoney = aLLClaimDetailSchema.getClaimMoney();
		this.DeclineAmnt = aLLClaimDetailSchema.getDeclineAmnt();
		this.OverAmnt = aLLClaimDetailSchema.getOverAmnt();
		this.StandPay = aLLClaimDetailSchema.getStandPay();
		this.RealPay = aLLClaimDetailSchema.getRealPay();
		this.AdjReason = aLLClaimDetailSchema.getAdjReason();
		this.AdjRemark = aLLClaimDetailSchema.getAdjRemark();
		this.PreGiveAmnt = aLLClaimDetailSchema.getPreGiveAmnt();
		this.SelfGiveAmnt = aLLClaimDetailSchema.getSelfGiveAmnt();
		this.RefuseAmnt = aLLClaimDetailSchema.getRefuseAmnt();
		this.OtherAmnt = aLLClaimDetailSchema.getOtherAmnt();
		this.OutDutyAmnt = aLLClaimDetailSchema.getOutDutyAmnt();
		this.OutDutyRate = aLLClaimDetailSchema.getOutDutyRate();
		this.ApproveAmnt = aLLClaimDetailSchema.getApproveAmnt();
		this.ApproveCode = aLLClaimDetailSchema.getApproveCode();
		this.AgreeAmnt = aLLClaimDetailSchema.getAgreeAmnt();
		this.AgreeCode = aLLClaimDetailSchema.getAgreeCode();
		this.GiveType = aLLClaimDetailSchema.getGiveType();
		this.GiveTypeDesc = aLLClaimDetailSchema.getGiveTypeDesc();
		this.GiveReason = aLLClaimDetailSchema.getGiveReason();
		this.GiveReasonDesc = aLLClaimDetailSchema.getGiveReasonDesc();
		this.SpecialRemark = aLLClaimDetailSchema.getSpecialRemark();
		this.PrepayFlag = aLLClaimDetailSchema.getPrepayFlag();
		this.PrepaySum = aLLClaimDetailSchema.getPrepaySum();
		this.MngCom = aLLClaimDetailSchema.getMngCom();
		this.Operator = aLLClaimDetailSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLClaimDetailSchema.getMakeDate());
		this.MakeTime = aLLClaimDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClaimDetailSchema.getModifyDate());
		this.ModifyTime = aLLClaimDetailSchema.getModifyTime();
		this.Amnt = aLLClaimDetailSchema.getAmnt();
		this.YearBonus = aLLClaimDetailSchema.getYearBonus();
		this.EndBonus = aLLClaimDetailSchema.getEndBonus();
		this.GracePeriod = aLLClaimDetailSchema.getGracePeriod();
		this.CasePolType = aLLClaimDetailSchema.getCasePolType();
		this.CustomerNo = aLLClaimDetailSchema.getCustomerNo();
		this.AcctFlag = aLLClaimDetailSchema.getAcctFlag();
		this.PosFlag = aLLClaimDetailSchema.getPosFlag();
		this.PosEdorNo = aLLClaimDetailSchema.getPosEdorNo();
		this.CValiDate = fDate.getDate( aLLClaimDetailSchema.getCValiDate());
		this.EffectOnMainRisk = aLLClaimDetailSchema.getEffectOnMainRisk();
		this.RiskCalCode = aLLClaimDetailSchema.getRiskCalCode();
		this.AddAmnt = aLLClaimDetailSchema.getAddAmnt();
		this.NBPolNo = aLLClaimDetailSchema.getNBPolNo();
		this.PosEdorType = aLLClaimDetailSchema.getPosEdorType();
		this.RiskType = aLLClaimDetailSchema.getRiskType();
		this.PopedomPay = aLLClaimDetailSchema.getPopedomPay();
		this.InsFeePay = aLLClaimDetailSchema.getInsFeePay();
		this.OperState = aLLClaimDetailSchema.getOperState();
		this.Currency = aLLClaimDetailSchema.getCurrency();
		this.BussType = aLLClaimDetailSchema.getBussType();
		this.BussNo = aLLClaimDetailSchema.getBussNo();
		this.IsPublicAmnt = aLLClaimDetailSchema.getIsPublicAmnt();
		this.PublicRiskCode = aLLClaimDetailSchema.getPublicRiskCode();
		this.PublicDutyCode = aLLClaimDetailSchema.getPublicDutyCode();
		this.PublicAmnt = aLLClaimDetailSchema.getPublicAmnt();
		this.Salary = aLLClaimDetailSchema.getSalary();
		this.GiveClass = aLLClaimDetailSchema.getGiveClass();
		this.IndividualPayBUsedLimit = aLLClaimDetailSchema.getIndividualPayBUsedLimit();
		this.SelfCalculateUsedLimint = aLLClaimDetailSchema.getSelfCalculateUsedLimint();
		this.DentistryPay = aLLClaimDetailSchema.getDentistryPay();
		this.PhysicalPay = aLLClaimDetailSchema.getPhysicalPay();
		this.ComCode = aLLClaimDetailSchema.getComCode();
		this.ModifyOperator = aLLClaimDetailSchema.getModifyOperator();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("PolSort") == null )
				this.PolSort = null;
			else
				this.PolSort = rs.getString("PolSort").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

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

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("DefoType") == null )
				this.DefoType = null;
			else
				this.DefoType = rs.getString("DefoType").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("DeclineNo") == null )
				this.DeclineNo = null;
			else
				this.DeclineNo = rs.getString("DeclineNo").trim();

			if( rs.getString("StatType") == null )
				this.StatType = null;
			else
				this.StatType = rs.getString("StatType").trim();

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

			this.TabFeeMoney = rs.getDouble("TabFeeMoney");
			this.ClaimMoney = rs.getDouble("ClaimMoney");
			this.DeclineAmnt = rs.getDouble("DeclineAmnt");
			this.OverAmnt = rs.getDouble("OverAmnt");
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

			this.PreGiveAmnt = rs.getDouble("PreGiveAmnt");
			this.SelfGiveAmnt = rs.getDouble("SelfGiveAmnt");
			this.RefuseAmnt = rs.getDouble("RefuseAmnt");
			this.OtherAmnt = rs.getDouble("OtherAmnt");
			this.OutDutyAmnt = rs.getDouble("OutDutyAmnt");
			this.OutDutyRate = rs.getDouble("OutDutyRate");
			this.ApproveAmnt = rs.getDouble("ApproveAmnt");
			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.AgreeAmnt = rs.getDouble("AgreeAmnt");
			if( rs.getString("AgreeCode") == null )
				this.AgreeCode = null;
			else
				this.AgreeCode = rs.getString("AgreeCode").trim();

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

			if( rs.getString("PrepayFlag") == null )
				this.PrepayFlag = null;
			else
				this.PrepayFlag = rs.getString("PrepayFlag").trim();

			this.PrepaySum = rs.getDouble("PrepaySum");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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
			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("PosEdorType") == null )
				this.PosEdorType = null;
			else
				this.PosEdorType = rs.getString("PosEdorType").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			this.PopedomPay = rs.getDouble("PopedomPay");
			this.InsFeePay = rs.getDouble("InsFeePay");
			if( rs.getString("OperState") == null )
				this.OperState = null;
			else
				this.OperState = rs.getString("OperState").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("IsPublicAmnt") == null )
				this.IsPublicAmnt = null;
			else
				this.IsPublicAmnt = rs.getString("IsPublicAmnt").trim();

			if( rs.getString("PublicRiskCode") == null )
				this.PublicRiskCode = null;
			else
				this.PublicRiskCode = rs.getString("PublicRiskCode").trim();

			if( rs.getString("PublicDutyCode") == null )
				this.PublicDutyCode = null;
			else
				this.PublicDutyCode = rs.getString("PublicDutyCode").trim();

			this.PublicAmnt = rs.getDouble("PublicAmnt");
			this.Salary = rs.getDouble("Salary");
			if( rs.getString("GiveClass") == null )
				this.GiveClass = null;
			else
				this.GiveClass = rs.getString("GiveClass").trim();

			if( rs.getString("IndividualPayBUsedLimit") == null )
				this.IndividualPayBUsedLimit = null;
			else
				this.IndividualPayBUsedLimit = rs.getString("IndividualPayBUsedLimit").trim();

			if( rs.getString("SelfCalculateUsedLimint") == null )
				this.SelfCalculateUsedLimint = null;
			else
				this.SelfCalculateUsedLimint = rs.getString("SelfCalculateUsedLimint").trim();

			if( rs.getString("DentistryPay") == null )
				this.DentistryPay = null;
			else
				this.DentistryPay = rs.getString("DentistryPay").trim();

			if( rs.getString("PhysicalPay") == null )
				this.PhysicalPay = null;
			else
				this.PhysicalPay = rs.getString("PhysicalPay").trim();

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
			logger.debug("数据库中的LLClaimDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimDetailSchema getSchema()
	{
		LLClaimDetailSchema aLLClaimDetailSchema = new LLClaimDetailSchema();
		aLLClaimDetailSchema.setSchema(this);
		return aLLClaimDetailSchema;
	}

	public LLClaimDetailDB getDB()
	{
		LLClaimDetailDB aDBOper = new LLClaimDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StatType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TabFeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeclineAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OverAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreGiveAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SelfGiveAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RefuseAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutDutyAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutDutyRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ApproveAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AgreeAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgreeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecialRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrepayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrepaySum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PosEdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PopedomPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsFeePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsPublicAmnt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PublicRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PublicDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PublicAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Salary));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GiveClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndividualPayBUsedLimit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SelfCalculateUsedLimint)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DentistryPay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PhysicalPay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DeclineNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StatType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			TabFeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			DeclineAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			OverAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PreGiveAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			SelfGiveAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			RefuseAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			OtherAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			ApproveAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			AgreeAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			AgreeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			GiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			GiveTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			GiveReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			GiveReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			SpecialRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			PrepayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			PrepaySum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).doubleValue();
			YearBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,56,SysConst.PACKAGESPILTER))).doubleValue();
			EndBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,57,SysConst.PACKAGESPILTER))).doubleValue();
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).intValue();
			CasePolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			AcctFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			PosFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			PosEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64,SysConst.PACKAGESPILTER));
			EffectOnMainRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			AddAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).doubleValue();
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			PosEdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			PopedomPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).doubleValue();
			InsFeePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).doubleValue();
			OperState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 75, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 76, SysConst.PACKAGESPILTER );
			IsPublicAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 77, SysConst.PACKAGESPILTER );
			PublicRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			PublicDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			PublicAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).doubleValue();
			Salary = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).doubleValue();
			GiveClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			IndividualPayBUsedLimit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			SelfCalculateUsedLimint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			DentistryPay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			PhysicalPay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("PolSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolSort));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
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
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("DefoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefoType));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineNo));
		}
		if (FCode.equalsIgnoreCase("StatType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatType));
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
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TabFeeMoney));
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimMoney));
		}
		if (FCode.equalsIgnoreCase("DeclineAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineAmnt));
		}
		if (FCode.equalsIgnoreCase("OverAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OverAmnt));
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
		if (FCode.equalsIgnoreCase("PreGiveAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreGiveAmnt));
		}
		if (FCode.equalsIgnoreCase("SelfGiveAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SelfGiveAmnt));
		}
		if (FCode.equalsIgnoreCase("RefuseAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RefuseAmnt));
		}
		if (FCode.equalsIgnoreCase("OtherAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherAmnt));
		}
		if (FCode.equalsIgnoreCase("OutDutyAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyAmnt));
		}
		if (FCode.equalsIgnoreCase("OutDutyRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDutyRate));
		}
		if (FCode.equalsIgnoreCase("ApproveAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveAmnt));
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("AgreeAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgreeAmnt));
		}
		if (FCode.equalsIgnoreCase("AgreeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgreeCode));
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
		if (FCode.equalsIgnoreCase("PrepayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepayFlag));
		}
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepaySum));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("PosEdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PosEdorType));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("PopedomPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopedomPay));
		}
		if (FCode.equalsIgnoreCase("InsFeePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsFeePay));
		}
		if (FCode.equalsIgnoreCase("OperState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperState));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("IsPublicAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsPublicAmnt));
		}
		if (FCode.equalsIgnoreCase("PublicRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PublicRiskCode));
		}
		if (FCode.equalsIgnoreCase("PublicDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PublicDutyCode));
		}
		if (FCode.equalsIgnoreCase("PublicAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PublicAmnt));
		}
		if (FCode.equalsIgnoreCase("Salary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salary));
		}
		if (FCode.equalsIgnoreCase("GiveClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GiveClass));
		}
		if (FCode.equalsIgnoreCase("IndividualPayBUsedLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndividualPayBUsedLimit));
		}
		if (FCode.equalsIgnoreCase("SelfCalculateUsedLimint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SelfCalculateUsedLimint));
		}
		if (FCode.equalsIgnoreCase("DentistryPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DentistryPay));
		}
		if (FCode.equalsIgnoreCase("PhysicalPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PhysicalPay));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolSort);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DeclineNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StatType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 23:
				strFieldValue = String.valueOf(TabFeeMoney);
				break;
			case 24:
				strFieldValue = String.valueOf(ClaimMoney);
				break;
			case 25:
				strFieldValue = String.valueOf(DeclineAmnt);
				break;
			case 26:
				strFieldValue = String.valueOf(OverAmnt);
				break;
			case 27:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 28:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 31:
				strFieldValue = String.valueOf(PreGiveAmnt);
				break;
			case 32:
				strFieldValue = String.valueOf(SelfGiveAmnt);
				break;
			case 33:
				strFieldValue = String.valueOf(RefuseAmnt);
				break;
			case 34:
				strFieldValue = String.valueOf(OtherAmnt);
				break;
			case 35:
				strFieldValue = String.valueOf(OutDutyAmnt);
				break;
			case 36:
				strFieldValue = String.valueOf(OutDutyRate);
				break;
			case 37:
				strFieldValue = String.valueOf(ApproveAmnt);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 39:
				strFieldValue = String.valueOf(AgreeAmnt);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(AgreeCode);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(GiveType);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(GiveTypeDesc);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(GiveReason);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(GiveReasonDesc);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(SpecialRemark);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(PrepayFlag);
				break;
			case 47:
				strFieldValue = String.valueOf(PrepaySum);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 54:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 55:
				strFieldValue = String.valueOf(YearBonus);
				break;
			case 56:
				strFieldValue = String.valueOf(EndBonus);
				break;
			case 57:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(CasePolType);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(AcctFlag);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(PosFlag);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(PosEdorNo);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(EffectOnMainRisk);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 66:
				strFieldValue = String.valueOf(AddAmnt);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(PosEdorType);
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 70:
				strFieldValue = String.valueOf(PopedomPay);
				break;
			case 71:
				strFieldValue = String.valueOf(InsFeePay);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(OperState);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 74:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 75:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 76:
				strFieldValue = StrTool.GBKToUnicode(IsPublicAmnt);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(PublicRiskCode);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(PublicDutyCode);
				break;
			case 79:
				strFieldValue = String.valueOf(PublicAmnt);
				break;
			case 80:
				strFieldValue = String.valueOf(Salary);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(GiveClass);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(IndividualPayBUsedLimit);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(SelfCalculateUsedLimint);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(DentistryPay);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(PhysicalPay);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 87:
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("PolSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolSort = FValue.trim();
			}
			else
				PolSort = null;
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeclineNo = FValue.trim();
			}
			else
				DeclineNo = null;
		}
		if (FCode.equalsIgnoreCase("StatType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StatType = FValue.trim();
			}
			else
				StatType = null;
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
		if (FCode.equalsIgnoreCase("DeclineAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DeclineAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OverAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OverAmnt = d;
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
		if (FCode.equalsIgnoreCase("PreGiveAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreGiveAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("SelfGiveAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SelfGiveAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("RefuseAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RefuseAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OtherAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OtherAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutDutyAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutDutyAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutDutyRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutDutyRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ApproveAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ApproveAmnt = d;
			}
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
		if (FCode.equalsIgnoreCase("AgreeAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AgreeAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("AgreeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgreeCode = FValue.trim();
			}
			else
				AgreeCode = null;
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
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
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("IsPublicAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsPublicAmnt = FValue.trim();
			}
			else
				IsPublicAmnt = null;
		}
		if (FCode.equalsIgnoreCase("PublicRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PublicRiskCode = FValue.trim();
			}
			else
				PublicRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("PublicDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PublicDutyCode = FValue.trim();
			}
			else
				PublicDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("PublicAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PublicAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Salary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Salary = d;
			}
		}
		if (FCode.equalsIgnoreCase("GiveClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GiveClass = FValue.trim();
			}
			else
				GiveClass = null;
		}
		if (FCode.equalsIgnoreCase("IndividualPayBUsedLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndividualPayBUsedLimit = FValue.trim();
			}
			else
				IndividualPayBUsedLimit = null;
		}
		if (FCode.equalsIgnoreCase("SelfCalculateUsedLimint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SelfCalculateUsedLimint = FValue.trim();
			}
			else
				SelfCalculateUsedLimint = null;
		}
		if (FCode.equalsIgnoreCase("DentistryPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DentistryPay = FValue.trim();
			}
			else
				DentistryPay = null;
		}
		if (FCode.equalsIgnoreCase("PhysicalPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PhysicalPay = FValue.trim();
			}
			else
				PhysicalPay = null;
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
		LLClaimDetailSchema other = (LLClaimDetailSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& PolNo.equals(other.getPolNo())
			&& PolSort.equals(other.getPolSort())
			&& PolType.equals(other.getPolType())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& DefoType.equals(other.getDefoType())
			&& RgtNo.equals(other.getRgtNo())
			&& DeclineNo.equals(other.getDeclineNo())
			&& StatType.equals(other.getStatType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& TabFeeMoney == other.getTabFeeMoney()
			&& ClaimMoney == other.getClaimMoney()
			&& DeclineAmnt == other.getDeclineAmnt()
			&& OverAmnt == other.getOverAmnt()
			&& StandPay == other.getStandPay()
			&& RealPay == other.getRealPay()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& PreGiveAmnt == other.getPreGiveAmnt()
			&& SelfGiveAmnt == other.getSelfGiveAmnt()
			&& RefuseAmnt == other.getRefuseAmnt()
			&& OtherAmnt == other.getOtherAmnt()
			&& OutDutyAmnt == other.getOutDutyAmnt()
			&& OutDutyRate == other.getOutDutyRate()
			&& ApproveAmnt == other.getApproveAmnt()
			&& ApproveCode.equals(other.getApproveCode())
			&& AgreeAmnt == other.getAgreeAmnt()
			&& AgreeCode.equals(other.getAgreeCode())
			&& GiveType.equals(other.getGiveType())
			&& GiveTypeDesc.equals(other.getGiveTypeDesc())
			&& GiveReason.equals(other.getGiveReason())
			&& GiveReasonDesc.equals(other.getGiveReasonDesc())
			&& SpecialRemark.equals(other.getSpecialRemark())
			&& PrepayFlag.equals(other.getPrepayFlag())
			&& PrepaySum == other.getPrepaySum()
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
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
			&& NBPolNo.equals(other.getNBPolNo())
			&& PosEdorType.equals(other.getPosEdorType())
			&& RiskType.equals(other.getRiskType())
			&& PopedomPay == other.getPopedomPay()
			&& InsFeePay == other.getInsFeePay()
			&& OperState.equals(other.getOperState())
			&& Currency.equals(other.getCurrency())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& IsPublicAmnt.equals(other.getIsPublicAmnt())
			&& PublicRiskCode.equals(other.getPublicRiskCode())
			&& PublicDutyCode.equals(other.getPublicDutyCode())
			&& PublicAmnt == other.getPublicAmnt()
			&& Salary == other.getSalary()
			&& GiveClass.equals(other.getGiveClass())
			&& IndividualPayBUsedLimit.equals(other.getIndividualPayBUsedLimit())
			&& SelfCalculateUsedLimint.equals(other.getSelfCalculateUsedLimint())
			&& DentistryPay.equals(other.getDentistryPay())
			&& PhysicalPay.equals(other.getPhysicalPay())
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
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolSort") ) {
			return 3;
		}
		if( strFieldName.equals("PolType") ) {
			return 4;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 6;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 7;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 8;
		}
		if( strFieldName.equals("DefoType") ) {
			return 9;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 10;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return 11;
		}
		if( strFieldName.equals("StatType") ) {
			return 12;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 13;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 14;
		}
		if( strFieldName.equals("ContNo") ) {
			return 15;
		}
		if( strFieldName.equals("KindCode") ) {
			return 16;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 17;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 18;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 19;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 20;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 21;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 22;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return 23;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return 24;
		}
		if( strFieldName.equals("DeclineAmnt") ) {
			return 25;
		}
		if( strFieldName.equals("OverAmnt") ) {
			return 26;
		}
		if( strFieldName.equals("StandPay") ) {
			return 27;
		}
		if( strFieldName.equals("RealPay") ) {
			return 28;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 29;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 30;
		}
		if( strFieldName.equals("PreGiveAmnt") ) {
			return 31;
		}
		if( strFieldName.equals("SelfGiveAmnt") ) {
			return 32;
		}
		if( strFieldName.equals("RefuseAmnt") ) {
			return 33;
		}
		if( strFieldName.equals("OtherAmnt") ) {
			return 34;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return 35;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return 36;
		}
		if( strFieldName.equals("ApproveAmnt") ) {
			return 37;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 38;
		}
		if( strFieldName.equals("AgreeAmnt") ) {
			return 39;
		}
		if( strFieldName.equals("AgreeCode") ) {
			return 40;
		}
		if( strFieldName.equals("GiveType") ) {
			return 41;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return 42;
		}
		if( strFieldName.equals("GiveReason") ) {
			return 43;
		}
		if( strFieldName.equals("GiveReasonDesc") ) {
			return 44;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return 45;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return 46;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return 47;
		}
		if( strFieldName.equals("MngCom") ) {
			return 48;
		}
		if( strFieldName.equals("Operator") ) {
			return 49;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 50;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 51;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 52;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 53;
		}
		if( strFieldName.equals("Amnt") ) {
			return 54;
		}
		if( strFieldName.equals("YearBonus") ) {
			return 55;
		}
		if( strFieldName.equals("EndBonus") ) {
			return 56;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 57;
		}
		if( strFieldName.equals("CasePolType") ) {
			return 58;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 59;
		}
		if( strFieldName.equals("AcctFlag") ) {
			return 60;
		}
		if( strFieldName.equals("PosFlag") ) {
			return 61;
		}
		if( strFieldName.equals("PosEdorNo") ) {
			return 62;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 63;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return 64;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 65;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return 66;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 67;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return 68;
		}
		if( strFieldName.equals("RiskType") ) {
			return 69;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return 70;
		}
		if( strFieldName.equals("InsFeePay") ) {
			return 71;
		}
		if( strFieldName.equals("OperState") ) {
			return 72;
		}
		if( strFieldName.equals("Currency") ) {
			return 73;
		}
		if( strFieldName.equals("BussType") ) {
			return 74;
		}
		if( strFieldName.equals("BussNo") ) {
			return 75;
		}
		if( strFieldName.equals("IsPublicAmnt") ) {
			return 76;
		}
		if( strFieldName.equals("PublicRiskCode") ) {
			return 77;
		}
		if( strFieldName.equals("PublicDutyCode") ) {
			return 78;
		}
		if( strFieldName.equals("PublicAmnt") ) {
			return 79;
		}
		if( strFieldName.equals("Salary") ) {
			return 80;
		}
		if( strFieldName.equals("GiveClass") ) {
			return 81;
		}
		if( strFieldName.equals("IndividualPayBUsedLimit") ) {
			return 82;
		}
		if( strFieldName.equals("SelfCalculateUsedLimint") ) {
			return 83;
		}
		if( strFieldName.equals("DentistryPay") ) {
			return 84;
		}
		if( strFieldName.equals("PhysicalPay") ) {
			return 85;
		}
		if( strFieldName.equals("ComCode") ) {
			return 86;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 87;
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
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "PolSort";
				break;
			case 4:
				strFieldName = "PolType";
				break;
			case 5:
				strFieldName = "DutyCode";
				break;
			case 6:
				strFieldName = "GetDutyKind";
				break;
			case 7:
				strFieldName = "GetDutyCode";
				break;
			case 8:
				strFieldName = "CaseRelaNo";
				break;
			case 9:
				strFieldName = "DefoType";
				break;
			case 10:
				strFieldName = "RgtNo";
				break;
			case 11:
				strFieldName = "DeclineNo";
				break;
			case 12:
				strFieldName = "StatType";
				break;
			case 13:
				strFieldName = "GrpContNo";
				break;
			case 14:
				strFieldName = "GrpPolNo";
				break;
			case 15:
				strFieldName = "ContNo";
				break;
			case 16:
				strFieldName = "KindCode";
				break;
			case 17:
				strFieldName = "RiskCode";
				break;
			case 18:
				strFieldName = "RiskVer";
				break;
			case 19:
				strFieldName = "PolMngCom";
				break;
			case 20:
				strFieldName = "SaleChnl";
				break;
			case 21:
				strFieldName = "AgentCode";
				break;
			case 22:
				strFieldName = "AgentGroup";
				break;
			case 23:
				strFieldName = "TabFeeMoney";
				break;
			case 24:
				strFieldName = "ClaimMoney";
				break;
			case 25:
				strFieldName = "DeclineAmnt";
				break;
			case 26:
				strFieldName = "OverAmnt";
				break;
			case 27:
				strFieldName = "StandPay";
				break;
			case 28:
				strFieldName = "RealPay";
				break;
			case 29:
				strFieldName = "AdjReason";
				break;
			case 30:
				strFieldName = "AdjRemark";
				break;
			case 31:
				strFieldName = "PreGiveAmnt";
				break;
			case 32:
				strFieldName = "SelfGiveAmnt";
				break;
			case 33:
				strFieldName = "RefuseAmnt";
				break;
			case 34:
				strFieldName = "OtherAmnt";
				break;
			case 35:
				strFieldName = "OutDutyAmnt";
				break;
			case 36:
				strFieldName = "OutDutyRate";
				break;
			case 37:
				strFieldName = "ApproveAmnt";
				break;
			case 38:
				strFieldName = "ApproveCode";
				break;
			case 39:
				strFieldName = "AgreeAmnt";
				break;
			case 40:
				strFieldName = "AgreeCode";
				break;
			case 41:
				strFieldName = "GiveType";
				break;
			case 42:
				strFieldName = "GiveTypeDesc";
				break;
			case 43:
				strFieldName = "GiveReason";
				break;
			case 44:
				strFieldName = "GiveReasonDesc";
				break;
			case 45:
				strFieldName = "SpecialRemark";
				break;
			case 46:
				strFieldName = "PrepayFlag";
				break;
			case 47:
				strFieldName = "PrepaySum";
				break;
			case 48:
				strFieldName = "MngCom";
				break;
			case 49:
				strFieldName = "Operator";
				break;
			case 50:
				strFieldName = "MakeDate";
				break;
			case 51:
				strFieldName = "MakeTime";
				break;
			case 52:
				strFieldName = "ModifyDate";
				break;
			case 53:
				strFieldName = "ModifyTime";
				break;
			case 54:
				strFieldName = "Amnt";
				break;
			case 55:
				strFieldName = "YearBonus";
				break;
			case 56:
				strFieldName = "EndBonus";
				break;
			case 57:
				strFieldName = "GracePeriod";
				break;
			case 58:
				strFieldName = "CasePolType";
				break;
			case 59:
				strFieldName = "CustomerNo";
				break;
			case 60:
				strFieldName = "AcctFlag";
				break;
			case 61:
				strFieldName = "PosFlag";
				break;
			case 62:
				strFieldName = "PosEdorNo";
				break;
			case 63:
				strFieldName = "CValiDate";
				break;
			case 64:
				strFieldName = "EffectOnMainRisk";
				break;
			case 65:
				strFieldName = "RiskCalCode";
				break;
			case 66:
				strFieldName = "AddAmnt";
				break;
			case 67:
				strFieldName = "NBPolNo";
				break;
			case 68:
				strFieldName = "PosEdorType";
				break;
			case 69:
				strFieldName = "RiskType";
				break;
			case 70:
				strFieldName = "PopedomPay";
				break;
			case 71:
				strFieldName = "InsFeePay";
				break;
			case 72:
				strFieldName = "OperState";
				break;
			case 73:
				strFieldName = "Currency";
				break;
			case 74:
				strFieldName = "BussType";
				break;
			case 75:
				strFieldName = "BussNo";
				break;
			case 76:
				strFieldName = "IsPublicAmnt";
				break;
			case 77:
				strFieldName = "PublicRiskCode";
				break;
			case 78:
				strFieldName = "PublicDutyCode";
				break;
			case 79:
				strFieldName = "PublicAmnt";
				break;
			case 80:
				strFieldName = "Salary";
				break;
			case 81:
				strFieldName = "GiveClass";
				break;
			case 82:
				strFieldName = "IndividualPayBUsedLimit";
				break;
			case 83:
				strFieldName = "SelfCalculateUsedLimint";
				break;
			case 84:
				strFieldName = "DentistryPay";
				break;
			case 85:
				strFieldName = "PhysicalPay";
				break;
			case 86:
				strFieldName = "ComCode";
				break;
			case 87:
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
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
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StatType") ) {
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
		if( strFieldName.equals("TabFeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DeclineAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OverAmnt") ) {
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
		if( strFieldName.equals("PreGiveAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SelfGiveAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RefuseAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OtherAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ApproveAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgreeAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgreeCode") ) {
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
		if( strFieldName.equals("PrepayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsFeePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OperState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsPublicAmnt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PublicRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PublicDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PublicAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Salary") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GiveClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IndividualPayBUsedLimit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SelfCalculateUsedLimint") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DentistryPay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PhysicalPay") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
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
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 55:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 57:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 71:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 75:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 76:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 80:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
