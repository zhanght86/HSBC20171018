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
import com.sinosoft.lis.db.LLClmLimitAmntTraceDB;

/*
 * <p>ClassName: LLClmLimitAmntTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2010-09-09
 */
public class LLClmLimitAmntTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClmLimitAmntTraceSchema.class);

	// @Field
	/** 序号 */
	private String SerNo;
	/** 控制器编号 */
	private String ClaimCtrlCode;
	/** 个单合同号 */
	private String ContNo;
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
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 保障计划 */
	private String ContPlanCode;
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
	/** 申请金额 */
	private double AppAmnt;
	/** 申请天数 */
	private int AppDayCount;
	/** 责任类型 */
	private String DutyType;
	/** 不理算原因 */
	private String NoCalReason;
	/** 理算日期 */
	private Date CalDate;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 81;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClmLimitAmntTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "SerNo";
		pk[1] = "ClaimCtrlCode";
		pk[2] = "ContNo";

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
                LLClmLimitAmntTraceSchema cloned = (LLClmLimitAmntTraceSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerNo()
	{
		return SerNo;
	}
	public void setSerNo(String aSerNo)
	{
		SerNo = aSerNo;
	}
	public String getClaimCtrlCode()
	{
		return ClaimCtrlCode;
	}
	public void setClaimCtrlCode(String aClaimCtrlCode)
	{
		ClaimCtrlCode = aClaimCtrlCode;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getPolSort()
	{
		return PolSort;
	}
	public void setPolSort(String aPolSort)
	{
		PolSort = aPolSort;
	}
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		PolType = aPolType;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
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
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		CaseRelaNo = aCaseRelaNo;
	}
	public String getDefoType()
	{
		return DefoType;
	}
	public void setDefoType(String aDefoType)
	{
		DefoType = aDefoType;
	}
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	public String getDeclineNo()
	{
		return DeclineNo;
	}
	public void setDeclineNo(String aDeclineNo)
	{
		DeclineNo = aDeclineNo;
	}
	public String getStatType()
	{
		return StatType;
	}
	public void setStatType(String aStatType)
	{
		StatType = aStatType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
		PolMngCom = aPolMngCom;
	}
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
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
		AdjReason = aAdjReason;
	}
	public String getAdjRemark()
	{
		return AdjRemark;
	}
	public void setAdjRemark(String aAdjRemark)
	{
		AdjRemark = aAdjRemark;
	}
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

	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		ApproveCode = aApproveCode;
	}
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

	public String getAgreeCode()
	{
		return AgreeCode;
	}
	public void setAgreeCode(String aAgreeCode)
	{
		AgreeCode = aAgreeCode;
	}
	public String getGiveType()
	{
		return GiveType;
	}
	public void setGiveType(String aGiveType)
	{
		GiveType = aGiveType;
	}
	public String getGiveTypeDesc()
	{
		return GiveTypeDesc;
	}
	public void setGiveTypeDesc(String aGiveTypeDesc)
	{
		GiveTypeDesc = aGiveTypeDesc;
	}
	public String getGiveReason()
	{
		return GiveReason;
	}
	public void setGiveReason(String aGiveReason)
	{
		GiveReason = aGiveReason;
	}
	public String getGiveReasonDesc()
	{
		return GiveReasonDesc;
	}
	public void setGiveReasonDesc(String aGiveReasonDesc)
	{
		GiveReasonDesc = aGiveReasonDesc;
	}
	public String getSpecialRemark()
	{
		return SpecialRemark;
	}
	public void setSpecialRemark(String aSpecialRemark)
	{
		SpecialRemark = aSpecialRemark;
	}
	public String getPrepayFlag()
	{
		return PrepayFlag;
	}
	public void setPrepayFlag(String aPrepayFlag)
	{
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
		MngCom = aMngCom;
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

	public String getCasePolType()
	{
		return CasePolType;
	}
	public void setCasePolType(String aCasePolType)
	{
		CasePolType = aCasePolType;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getAcctFlag()
	{
		return AcctFlag;
	}
	public void setAcctFlag(String aAcctFlag)
	{
		AcctFlag = aAcctFlag;
	}
	public String getPosFlag()
	{
		return PosFlag;
	}
	public void setPosFlag(String aPosFlag)
	{
		PosFlag = aPosFlag;
	}
	public String getPosEdorNo()
	{
		return PosEdorNo;
	}
	public void setPosEdorNo(String aPosEdorNo)
	{
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
		EffectOnMainRisk = aEffectOnMainRisk;
	}
	public String getRiskCalCode()
	{
		return RiskCalCode;
	}
	public void setRiskCalCode(String aRiskCalCode)
	{
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
		NBPolNo = aNBPolNo;
	}
	public String getPosEdorType()
	{
		return PosEdorType;
	}
	public void setPosEdorType(String aPosEdorType)
	{
		PosEdorType = aPosEdorType;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
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

	public double getAppAmnt()
	{
		return AppAmnt;
	}
	public void setAppAmnt(double aAppAmnt)
	{
		AppAmnt = aAppAmnt;
	}
	public void setAppAmnt(String aAppAmnt)
	{
		if (aAppAmnt != null && !aAppAmnt.equals(""))
		{
			Double tDouble = new Double(aAppAmnt);
			double d = tDouble.doubleValue();
			AppAmnt = d;
		}
	}

	public int getAppDayCount()
	{
		return AppDayCount;
	}
	public void setAppDayCount(int aAppDayCount)
	{
		AppDayCount = aAppDayCount;
	}
	public void setAppDayCount(String aAppDayCount)
	{
		if (aAppDayCount != null && !aAppDayCount.equals(""))
		{
			Integer tInteger = new Integer(aAppDayCount);
			int i = tInteger.intValue();
			AppDayCount = i;
		}
	}

	public String getDutyType()
	{
		return DutyType;
	}
	public void setDutyType(String aDutyType)
	{
		DutyType = aDutyType;
	}
	public String getNoCalReason()
	{
		return NoCalReason;
	}
	public void setNoCalReason(String aNoCalReason)
	{
		NoCalReason = aNoCalReason;
	}
	public String getCalDate()
	{
		if( CalDate != null )
			return fDate.getString(CalDate);
		else
			return null;
	}
	public void setCalDate(Date aCalDate)
	{
		CalDate = aCalDate;
	}
	public void setCalDate(String aCalDate)
	{
		if (aCalDate != null && !aCalDate.equals("") )
		{
			CalDate = fDate.getDate( aCalDate );
		}
		else
			CalDate = null;
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
	* 使用另外一个 LLClmLimitAmntTraceSchema 对象给 Schema 赋值
	* @param: aLLClmLimitAmntTraceSchema LLClmLimitAmntTraceSchema
	**/
	public void setSchema(LLClmLimitAmntTraceSchema aLLClmLimitAmntTraceSchema)
	{
		this.SerNo = aLLClmLimitAmntTraceSchema.getSerNo();
		this.ClaimCtrlCode = aLLClmLimitAmntTraceSchema.getClaimCtrlCode();
		this.ContNo = aLLClmLimitAmntTraceSchema.getContNo();
		this.ClmNo = aLLClmLimitAmntTraceSchema.getClmNo();
		this.CaseNo = aLLClmLimitAmntTraceSchema.getCaseNo();
		this.PolNo = aLLClmLimitAmntTraceSchema.getPolNo();
		this.PolSort = aLLClmLimitAmntTraceSchema.getPolSort();
		this.PolType = aLLClmLimitAmntTraceSchema.getPolType();
		this.DutyCode = aLLClmLimitAmntTraceSchema.getDutyCode();
		this.GetDutyKind = aLLClmLimitAmntTraceSchema.getGetDutyKind();
		this.GetDutyCode = aLLClmLimitAmntTraceSchema.getGetDutyCode();
		this.CaseRelaNo = aLLClmLimitAmntTraceSchema.getCaseRelaNo();
		this.DefoType = aLLClmLimitAmntTraceSchema.getDefoType();
		this.RgtNo = aLLClmLimitAmntTraceSchema.getRgtNo();
		this.DeclineNo = aLLClmLimitAmntTraceSchema.getDeclineNo();
		this.StatType = aLLClmLimitAmntTraceSchema.getStatType();
		this.GrpContNo = aLLClmLimitAmntTraceSchema.getGrpContNo();
		this.GrpPolNo = aLLClmLimitAmntTraceSchema.getGrpPolNo();
		this.KindCode = aLLClmLimitAmntTraceSchema.getKindCode();
		this.RiskCode = aLLClmLimitAmntTraceSchema.getRiskCode();
		this.RiskVer = aLLClmLimitAmntTraceSchema.getRiskVer();
		this.ContPlanCode = aLLClmLimitAmntTraceSchema.getContPlanCode();
		this.PolMngCom = aLLClmLimitAmntTraceSchema.getPolMngCom();
		this.SaleChnl = aLLClmLimitAmntTraceSchema.getSaleChnl();
		this.AgentCode = aLLClmLimitAmntTraceSchema.getAgentCode();
		this.AgentGroup = aLLClmLimitAmntTraceSchema.getAgentGroup();
		this.TabFeeMoney = aLLClmLimitAmntTraceSchema.getTabFeeMoney();
		this.ClaimMoney = aLLClmLimitAmntTraceSchema.getClaimMoney();
		this.DeclineAmnt = aLLClmLimitAmntTraceSchema.getDeclineAmnt();
		this.OverAmnt = aLLClmLimitAmntTraceSchema.getOverAmnt();
		this.StandPay = aLLClmLimitAmntTraceSchema.getStandPay();
		this.RealPay = aLLClmLimitAmntTraceSchema.getRealPay();
		this.AdjReason = aLLClmLimitAmntTraceSchema.getAdjReason();
		this.AdjRemark = aLLClmLimitAmntTraceSchema.getAdjRemark();
		this.PreGiveAmnt = aLLClmLimitAmntTraceSchema.getPreGiveAmnt();
		this.SelfGiveAmnt = aLLClmLimitAmntTraceSchema.getSelfGiveAmnt();
		this.RefuseAmnt = aLLClmLimitAmntTraceSchema.getRefuseAmnt();
		this.OtherAmnt = aLLClmLimitAmntTraceSchema.getOtherAmnt();
		this.OutDutyAmnt = aLLClmLimitAmntTraceSchema.getOutDutyAmnt();
		this.OutDutyRate = aLLClmLimitAmntTraceSchema.getOutDutyRate();
		this.ApproveAmnt = aLLClmLimitAmntTraceSchema.getApproveAmnt();
		this.ApproveCode = aLLClmLimitAmntTraceSchema.getApproveCode();
		this.AgreeAmnt = aLLClmLimitAmntTraceSchema.getAgreeAmnt();
		this.AgreeCode = aLLClmLimitAmntTraceSchema.getAgreeCode();
		this.GiveType = aLLClmLimitAmntTraceSchema.getGiveType();
		this.GiveTypeDesc = aLLClmLimitAmntTraceSchema.getGiveTypeDesc();
		this.GiveReason = aLLClmLimitAmntTraceSchema.getGiveReason();
		this.GiveReasonDesc = aLLClmLimitAmntTraceSchema.getGiveReasonDesc();
		this.SpecialRemark = aLLClmLimitAmntTraceSchema.getSpecialRemark();
		this.PrepayFlag = aLLClmLimitAmntTraceSchema.getPrepayFlag();
		this.PrepaySum = aLLClmLimitAmntTraceSchema.getPrepaySum();
		this.MngCom = aLLClmLimitAmntTraceSchema.getMngCom();
		this.Operator = aLLClmLimitAmntTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLClmLimitAmntTraceSchema.getMakeDate());
		this.MakeTime = aLLClmLimitAmntTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLClmLimitAmntTraceSchema.getModifyDate());
		this.ModifyTime = aLLClmLimitAmntTraceSchema.getModifyTime();
		this.Amnt = aLLClmLimitAmntTraceSchema.getAmnt();
		this.YearBonus = aLLClmLimitAmntTraceSchema.getYearBonus();
		this.EndBonus = aLLClmLimitAmntTraceSchema.getEndBonus();
		this.GracePeriod = aLLClmLimitAmntTraceSchema.getGracePeriod();
		this.CasePolType = aLLClmLimitAmntTraceSchema.getCasePolType();
		this.CustomerNo = aLLClmLimitAmntTraceSchema.getCustomerNo();
		this.AcctFlag = aLLClmLimitAmntTraceSchema.getAcctFlag();
		this.PosFlag = aLLClmLimitAmntTraceSchema.getPosFlag();
		this.PosEdorNo = aLLClmLimitAmntTraceSchema.getPosEdorNo();
		this.CValiDate = fDate.getDate( aLLClmLimitAmntTraceSchema.getCValiDate());
		this.EffectOnMainRisk = aLLClmLimitAmntTraceSchema.getEffectOnMainRisk();
		this.RiskCalCode = aLLClmLimitAmntTraceSchema.getRiskCalCode();
		this.AddAmnt = aLLClmLimitAmntTraceSchema.getAddAmnt();
		this.NBPolNo = aLLClmLimitAmntTraceSchema.getNBPolNo();
		this.PosEdorType = aLLClmLimitAmntTraceSchema.getPosEdorType();
		this.RiskType = aLLClmLimitAmntTraceSchema.getRiskType();
		this.PopedomPay = aLLClmLimitAmntTraceSchema.getPopedomPay();
		this.InsFeePay = aLLClmLimitAmntTraceSchema.getInsFeePay();
		this.AppAmnt = aLLClmLimitAmntTraceSchema.getAppAmnt();
		this.AppDayCount = aLLClmLimitAmntTraceSchema.getAppDayCount();
		this.DutyType = aLLClmLimitAmntTraceSchema.getDutyType();
		this.NoCalReason = aLLClmLimitAmntTraceSchema.getNoCalReason();
		this.CalDate = fDate.getDate( aLLClmLimitAmntTraceSchema.getCalDate());
		this.Currency = aLLClmLimitAmntTraceSchema.getCurrency();
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
			if( rs.getString("SerNo") == null )
				this.SerNo = null;
			else
				this.SerNo = rs.getString("SerNo").trim();

			if( rs.getString("ClaimCtrlCode") == null )
				this.ClaimCtrlCode = null;
			else
				this.ClaimCtrlCode = rs.getString("ClaimCtrlCode").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

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

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

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
			this.AppAmnt = rs.getDouble("AppAmnt");
			this.AppDayCount = rs.getInt("AppDayCount");
			if( rs.getString("DutyType") == null )
				this.DutyType = null;
			else
				this.DutyType = rs.getString("DutyType").trim();

			if( rs.getString("NoCalReason") == null )
				this.NoCalReason = null;
			else
				this.NoCalReason = rs.getString("NoCalReason").trim();

			this.CalDate = rs.getDate("CalDate");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClmLimitAmntTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmLimitAmntTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClmLimitAmntTraceSchema getSchema()
	{
		LLClmLimitAmntTraceSchema aLLClmLimitAmntTraceSchema = new LLClmLimitAmntTraceSchema();
		aLLClmLimitAmntTraceSchema.setSchema(this);
		return aLLClmLimitAmntTraceSchema;
	}

	public LLClmLimitAmntTraceDB getDB()
	{
		LLClmLimitAmntTraceDB aDBOper = new LLClmLimitAmntTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClmLimitAmntTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(SerNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ClaimCtrlCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
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
strReturn.append( ChgData.chgData(AppAmnt));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(AppDayCount));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DutyType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NoCalReason)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( CalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClmLimitAmntTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClaimCtrlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PolSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			DefoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DeclineNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StatType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			TabFeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			DeclineAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			OverAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			PreGiveAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			SelfGiveAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			RefuseAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			OtherAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			OutDutyRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			ApproveAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			AgreeAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			AgreeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			GiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			GiveTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			GiveReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			GiveReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			SpecialRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			PrepayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			PrepaySum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,58,SysConst.PACKAGESPILTER))).doubleValue();
			YearBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).doubleValue();
			EndBonus = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,60,SysConst.PACKAGESPILTER))).doubleValue();
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,61,SysConst.PACKAGESPILTER))).intValue();
			CasePolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			AcctFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			PosFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			PosEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67,SysConst.PACKAGESPILTER));
			EffectOnMainRisk = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69, SysConst.PACKAGESPILTER );
			AddAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).doubleValue();
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71, SysConst.PACKAGESPILTER );
			PosEdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			PopedomPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,74,SysConst.PACKAGESPILTER))).doubleValue();
			InsFeePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			AppAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).doubleValue();
			AppDayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).intValue();
			DutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 78, SysConst.PACKAGESPILTER );
			NoCalReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			CalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 80,SysConst.PACKAGESPILTER));
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 81, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmLimitAmntTraceSchema";
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
		if (FCode.equalsIgnoreCase("SerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerNo));
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlCode));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
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
		if (FCode.equalsIgnoreCase("AppAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppAmnt));
		}
		if (FCode.equalsIgnoreCase("AppDayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppDayCount));
		}
		if (FCode.equalsIgnoreCase("DutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyType));
		}
		if (FCode.equalsIgnoreCase("NoCalReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoCalReason));
		}
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
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
				strFieldValue = StrTool.GBKToUnicode(SerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PolSort);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(DefoType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DeclineNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StatType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 26:
				strFieldValue = String.valueOf(TabFeeMoney);
				break;
			case 27:
				strFieldValue = String.valueOf(ClaimMoney);
				break;
			case 28:
				strFieldValue = String.valueOf(DeclineAmnt);
				break;
			case 29:
				strFieldValue = String.valueOf(OverAmnt);
				break;
			case 30:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 31:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 34:
				strFieldValue = String.valueOf(PreGiveAmnt);
				break;
			case 35:
				strFieldValue = String.valueOf(SelfGiveAmnt);
				break;
			case 36:
				strFieldValue = String.valueOf(RefuseAmnt);
				break;
			case 37:
				strFieldValue = String.valueOf(OtherAmnt);
				break;
			case 38:
				strFieldValue = String.valueOf(OutDutyAmnt);
				break;
			case 39:
				strFieldValue = String.valueOf(OutDutyRate);
				break;
			case 40:
				strFieldValue = String.valueOf(ApproveAmnt);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 42:
				strFieldValue = String.valueOf(AgreeAmnt);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AgreeCode);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(GiveType);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(GiveTypeDesc);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(GiveReason);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(GiveReasonDesc);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(SpecialRemark);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(PrepayFlag);
				break;
			case 50:
				strFieldValue = String.valueOf(PrepaySum);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 57:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 58:
				strFieldValue = String.valueOf(YearBonus);
				break;
			case 59:
				strFieldValue = String.valueOf(EndBonus);
				break;
			case 60:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(CasePolType);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(AcctFlag);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(PosFlag);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(PosEdorNo);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(EffectOnMainRisk);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 69:
				strFieldValue = String.valueOf(AddAmnt);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(PosEdorType);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 73:
				strFieldValue = String.valueOf(PopedomPay);
				break;
			case 74:
				strFieldValue = String.valueOf(InsFeePay);
				break;
			case 75:
				strFieldValue = String.valueOf(AppAmnt);
				break;
			case 76:
				strFieldValue = String.valueOf(AppDayCount);
				break;
			case 77:
				strFieldValue = StrTool.GBKToUnicode(DutyType);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(NoCalReason);
				break;
			case 79:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
				break;
			case 80:
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

		if (FCode.equalsIgnoreCase("SerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerNo = FValue.trim();
			}
			else
				SerNo = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlCode = FValue.trim();
			}
			else
				ClaimCtrlCode = null;
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
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
		if (FCode.equalsIgnoreCase("AppAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AppAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("AppDayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AppDayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("DutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyType = FValue.trim();
			}
			else
				DutyType = null;
		}
		if (FCode.equalsIgnoreCase("NoCalReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoCalReason = FValue.trim();
			}
			else
				NoCalReason = null;
		}
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CalDate = fDate.getDate( FValue );
			}
			else
				CalDate = null;
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
		LLClmLimitAmntTraceSchema other = (LLClmLimitAmntTraceSchema)otherObject;
		return
			SerNo.equals(other.getSerNo())
			&& ClaimCtrlCode.equals(other.getClaimCtrlCode())
			&& ContNo.equals(other.getContNo())
			&& ClmNo.equals(other.getClmNo())
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
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& ContPlanCode.equals(other.getContPlanCode())
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
			&& AppAmnt == other.getAppAmnt()
			&& AppDayCount == other.getAppDayCount()
			&& DutyType.equals(other.getDutyType())
			&& NoCalReason.equals(other.getNoCalReason())
			&& fDate.getString(CalDate).equals(other.getCalDate())
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
		if( strFieldName.equals("SerNo") ) {
			return 0;
		}
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 3;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 4;
		}
		if( strFieldName.equals("PolNo") ) {
			return 5;
		}
		if( strFieldName.equals("PolSort") ) {
			return 6;
		}
		if( strFieldName.equals("PolType") ) {
			return 7;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 8;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 9;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 10;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 11;
		}
		if( strFieldName.equals("DefoType") ) {
			return 12;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 13;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return 14;
		}
		if( strFieldName.equals("StatType") ) {
			return 15;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 16;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 17;
		}
		if( strFieldName.equals("KindCode") ) {
			return 18;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 19;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 20;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 21;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 22;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 23;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 24;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 25;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return 26;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return 27;
		}
		if( strFieldName.equals("DeclineAmnt") ) {
			return 28;
		}
		if( strFieldName.equals("OverAmnt") ) {
			return 29;
		}
		if( strFieldName.equals("StandPay") ) {
			return 30;
		}
		if( strFieldName.equals("RealPay") ) {
			return 31;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 32;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 33;
		}
		if( strFieldName.equals("PreGiveAmnt") ) {
			return 34;
		}
		if( strFieldName.equals("SelfGiveAmnt") ) {
			return 35;
		}
		if( strFieldName.equals("RefuseAmnt") ) {
			return 36;
		}
		if( strFieldName.equals("OtherAmnt") ) {
			return 37;
		}
		if( strFieldName.equals("OutDutyAmnt") ) {
			return 38;
		}
		if( strFieldName.equals("OutDutyRate") ) {
			return 39;
		}
		if( strFieldName.equals("ApproveAmnt") ) {
			return 40;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 41;
		}
		if( strFieldName.equals("AgreeAmnt") ) {
			return 42;
		}
		if( strFieldName.equals("AgreeCode") ) {
			return 43;
		}
		if( strFieldName.equals("GiveType") ) {
			return 44;
		}
		if( strFieldName.equals("GiveTypeDesc") ) {
			return 45;
		}
		if( strFieldName.equals("GiveReason") ) {
			return 46;
		}
		if( strFieldName.equals("GiveReasonDesc") ) {
			return 47;
		}
		if( strFieldName.equals("SpecialRemark") ) {
			return 48;
		}
		if( strFieldName.equals("PrepayFlag") ) {
			return 49;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return 50;
		}
		if( strFieldName.equals("MngCom") ) {
			return 51;
		}
		if( strFieldName.equals("Operator") ) {
			return 52;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 53;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 54;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 55;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 56;
		}
		if( strFieldName.equals("Amnt") ) {
			return 57;
		}
		if( strFieldName.equals("YearBonus") ) {
			return 58;
		}
		if( strFieldName.equals("EndBonus") ) {
			return 59;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 60;
		}
		if( strFieldName.equals("CasePolType") ) {
			return 61;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 62;
		}
		if( strFieldName.equals("AcctFlag") ) {
			return 63;
		}
		if( strFieldName.equals("PosFlag") ) {
			return 64;
		}
		if( strFieldName.equals("PosEdorNo") ) {
			return 65;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 66;
		}
		if( strFieldName.equals("EffectOnMainRisk") ) {
			return 67;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 68;
		}
		if( strFieldName.equals("AddAmnt") ) {
			return 69;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 70;
		}
		if( strFieldName.equals("PosEdorType") ) {
			return 71;
		}
		if( strFieldName.equals("RiskType") ) {
			return 72;
		}
		if( strFieldName.equals("PopedomPay") ) {
			return 73;
		}
		if( strFieldName.equals("InsFeePay") ) {
			return 74;
		}
		if( strFieldName.equals("AppAmnt") ) {
			return 75;
		}
		if( strFieldName.equals("AppDayCount") ) {
			return 76;
		}
		if( strFieldName.equals("DutyType") ) {
			return 77;
		}
		if( strFieldName.equals("NoCalReason") ) {
			return 78;
		}
		if( strFieldName.equals("CalDate") ) {
			return 79;
		}
		if( strFieldName.equals("Currency") ) {
			return 80;
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
				strFieldName = "SerNo";
				break;
			case 1:
				strFieldName = "ClaimCtrlCode";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "ClmNo";
				break;
			case 4:
				strFieldName = "CaseNo";
				break;
			case 5:
				strFieldName = "PolNo";
				break;
			case 6:
				strFieldName = "PolSort";
				break;
			case 7:
				strFieldName = "PolType";
				break;
			case 8:
				strFieldName = "DutyCode";
				break;
			case 9:
				strFieldName = "GetDutyKind";
				break;
			case 10:
				strFieldName = "GetDutyCode";
				break;
			case 11:
				strFieldName = "CaseRelaNo";
				break;
			case 12:
				strFieldName = "DefoType";
				break;
			case 13:
				strFieldName = "RgtNo";
				break;
			case 14:
				strFieldName = "DeclineNo";
				break;
			case 15:
				strFieldName = "StatType";
				break;
			case 16:
				strFieldName = "GrpContNo";
				break;
			case 17:
				strFieldName = "GrpPolNo";
				break;
			case 18:
				strFieldName = "KindCode";
				break;
			case 19:
				strFieldName = "RiskCode";
				break;
			case 20:
				strFieldName = "RiskVer";
				break;
			case 21:
				strFieldName = "ContPlanCode";
				break;
			case 22:
				strFieldName = "PolMngCom";
				break;
			case 23:
				strFieldName = "SaleChnl";
				break;
			case 24:
				strFieldName = "AgentCode";
				break;
			case 25:
				strFieldName = "AgentGroup";
				break;
			case 26:
				strFieldName = "TabFeeMoney";
				break;
			case 27:
				strFieldName = "ClaimMoney";
				break;
			case 28:
				strFieldName = "DeclineAmnt";
				break;
			case 29:
				strFieldName = "OverAmnt";
				break;
			case 30:
				strFieldName = "StandPay";
				break;
			case 31:
				strFieldName = "RealPay";
				break;
			case 32:
				strFieldName = "AdjReason";
				break;
			case 33:
				strFieldName = "AdjRemark";
				break;
			case 34:
				strFieldName = "PreGiveAmnt";
				break;
			case 35:
				strFieldName = "SelfGiveAmnt";
				break;
			case 36:
				strFieldName = "RefuseAmnt";
				break;
			case 37:
				strFieldName = "OtherAmnt";
				break;
			case 38:
				strFieldName = "OutDutyAmnt";
				break;
			case 39:
				strFieldName = "OutDutyRate";
				break;
			case 40:
				strFieldName = "ApproveAmnt";
				break;
			case 41:
				strFieldName = "ApproveCode";
				break;
			case 42:
				strFieldName = "AgreeAmnt";
				break;
			case 43:
				strFieldName = "AgreeCode";
				break;
			case 44:
				strFieldName = "GiveType";
				break;
			case 45:
				strFieldName = "GiveTypeDesc";
				break;
			case 46:
				strFieldName = "GiveReason";
				break;
			case 47:
				strFieldName = "GiveReasonDesc";
				break;
			case 48:
				strFieldName = "SpecialRemark";
				break;
			case 49:
				strFieldName = "PrepayFlag";
				break;
			case 50:
				strFieldName = "PrepaySum";
				break;
			case 51:
				strFieldName = "MngCom";
				break;
			case 52:
				strFieldName = "Operator";
				break;
			case 53:
				strFieldName = "MakeDate";
				break;
			case 54:
				strFieldName = "MakeTime";
				break;
			case 55:
				strFieldName = "ModifyDate";
				break;
			case 56:
				strFieldName = "ModifyTime";
				break;
			case 57:
				strFieldName = "Amnt";
				break;
			case 58:
				strFieldName = "YearBonus";
				break;
			case 59:
				strFieldName = "EndBonus";
				break;
			case 60:
				strFieldName = "GracePeriod";
				break;
			case 61:
				strFieldName = "CasePolType";
				break;
			case 62:
				strFieldName = "CustomerNo";
				break;
			case 63:
				strFieldName = "AcctFlag";
				break;
			case 64:
				strFieldName = "PosFlag";
				break;
			case 65:
				strFieldName = "PosEdorNo";
				break;
			case 66:
				strFieldName = "CValiDate";
				break;
			case 67:
				strFieldName = "EffectOnMainRisk";
				break;
			case 68:
				strFieldName = "RiskCalCode";
				break;
			case 69:
				strFieldName = "AddAmnt";
				break;
			case 70:
				strFieldName = "NBPolNo";
				break;
			case 71:
				strFieldName = "PosEdorType";
				break;
			case 72:
				strFieldName = "RiskType";
				break;
			case 73:
				strFieldName = "PopedomPay";
				break;
			case 74:
				strFieldName = "InsFeePay";
				break;
			case 75:
				strFieldName = "AppAmnt";
				break;
			case 76:
				strFieldName = "AppDayCount";
				break;
			case 77:
				strFieldName = "DutyType";
				break;
			case 78:
				strFieldName = "NoCalReason";
				break;
			case 79:
				strFieldName = "CalDate";
				break;
			case 80:
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
		if( strFieldName.equals("SerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
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
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
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
		if( strFieldName.equals("AppAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppDayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoCalReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 56:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 57:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 58:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 59:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 60:
				nFieldType = Schema.TYPE_INT;
				break;
			case 61:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 69:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 70:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 75:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 76:
				nFieldType = Schema.TYPE_INT;
				break;
			case 77:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 80:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
