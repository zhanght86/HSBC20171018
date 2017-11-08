/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.ibrms.bom;

import java.util.*;
import java.text.SimpleDateFormat;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.ibrms.BOMPreCheck;
import org.apache.log4j.Logger;

/**
 * <p>ClassName: BOMCalClaim </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2016-11-28
 */

public class BOMCalClaim extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 出险原因 */
	private Double Oamnt;

	/** 保单号 */
	private String ContNo;

	/** 险种号码 */
	private String PolNo;

	/** 被保人号码 */
	private String InsuredNo;

	/** 事件号 */
	private String AccNo;

	/** 立案号 */
	private String RgtNo;

	/** 事故日期 */
	private Date AccDate;

	/** 申请类型 */
	private String RgtClass;

	/** 立案状态 */
	private String clmState;

	/** 赔案号 */
	private String ClmNo;

	/** 分案号(赔案号) */
	private String CaseNo;

	/** 回退号 */
	private String BackNo;

	/** 保单类型 */
	private String ConType;

	/** 责任编码 */
	private String DutyCode;

	/** 给付责任类型 */
	private String GetDutyKind;

	/** 给付类型 */
	private String CasePolType;

	/** 扫描单证号 */
	private String DocCode;

	/** 结算后总金额 */
	private Double InsureAccBalance;

	/** 总保费 */
	private Double SumPrem;

	/** 有效保额 */
	private Double Jegf;

	/** 原保额 */
	private Double Amnt;

	/** 出险时已保年期 */
	private Double RgtYears;

	/** 累计支付 */
	private Double PolPay;

	/** 赔付金额 */
	private Double Pay;

	/** 业务类型 */
	private String FeeOperationType;

	/** 实际住院天数 */
	private Double DaysInHos;

	/** 出险时点保单年度 */
	private Double AccYear;

	/** 豁免期数 */
	private Double ExemptPeriod;

	/** 剩余有效保额 */
	private Double LeaveAmnt;

	/** 疾病给付比例 */
	private Double DiseaseRate;

	/** 意外细节 */
	private String AccidentDetail;

	/** 基本保额 */
	private Double MaxAmnt;

	/** 给付责任的赔付次数 */
	private Double ClaimCount;

	/** 起领日期 */
	private Date GetStartDate;

	/** 医疗费用编码 */
	private String DutyFeeCode;

	/** 医疗费用序号 */
	private String DutyFeeStaNo;

	/** 被保人0岁保单生效对应日 */
	private Date InsuredvalidBirth;

	/** 出险时已保天数 */
	private Double RgtDays;

	/** 已付代码 */
	private String GetDutyCode;

	/** 出险时已保整月数 */
	private Double Rgtmonths;

	/** 累计红利保险金额 */
	private Double SumAmntBonus;

	/** 事故号 */
	private String CaseRelaNo;

	/** 保单号 */
	private String AccidentReason;

	/** 复效到出险时已保年期 */
	private Double MLRYears;

	/** 复效到出险时已保天数 */
	private Double MLRDays;

	/** 附险险的有效保额 */
	private Double TRMAmnt;

	/** 附险险的理赔给付金 */
	private Double RMAmnt;

	/** 账单结束日期 */
	private Date FeereceiEndDate;

	/** 共享保额给付责任算出理赔金 */
	private Double CurrentDutyPay;

	/** 共享不同保额给付责任算出理赔金 */
	private Double CurrentClDutyPay;

	/** 每个给付责任累计赔付金额 费用补偿 */
	private Double CompensateDutySumPay;

	/** 每个给付责任累计赔付额(费用型外) */
	private Double GetDutySumPay;

	/** 未成年人限额 */
	private Double PupilAmnt;

	/** 已部分领取的账户价值 */
	private Double SumAccGet;

	/** 利差返还后增加的保额 */
	private Double RateAmnt;

	/** 止领日期 */
	private Date GetEndDate;

	/** 已交费年期 */
	private Double PaytoDatYears;

	/** 保费-健康职业加费出险点保全补退费 */
	private Double TotalPrem;

	/** 治疗类型 */
	private String CureType;

	/** 每天床位费 */
	private Double InHospdayFee;

	/** 伤残比例 */
	private Double DefoRate;

	/** 费用结束日期 */
	private Date EndFeeDate;

	/** 费用开始日期 */
	private Date StartFeeDate;

	/** 保险结束日期 */
	private Date EndPolDate;

	/** 终了红利 */
	private Double FinalBonus;

	/** 出险时年龄 */
	private Double GetAge;

	/** 死亡日期 */
	private Date DeathDate;

	/** 续保次数 */
	private Double RenewCount;

	/** 保单是否复效的标记 */
	private String MLRFlag;

	/** 已领金额 */
	private Double SumMoney;

	/** 自付金额后实际参加理算的住院金额 */
	private Double RealAdjSumB;

	/** 自付金额后实际参加理算的门诊金额 */
	private Double RealAdjSumA;

	/** 储蓄保险计划身故保险金 */
	private Double RiskGLSYDeathBenefit;

	/** (连带被保人)共享保额给付算理赔金 */
	private Double CurrentRelateDutyPay;

	/** (连带)给付责任累计赔付额(费用型外) */
	private Double GetDutyRelateSumPay;

	/** 112208意外身故保险金 */
	private Double AcciDeathInsura;

	/** 112208疾病身故保险金 */
	private Double DiseaDeathInsura;

	/** 社保比例 */
	private Double SocialInsuRate;

	/** 有效保额(对于账户型的就是账户价值) */
	private Double ValidAmnt;

	/** 赔付限额 */
	private Double TotalLimit;

	/** 观察期 */
	private Double ObserveDate;

	/** 免赔额 */
	private Double ExemptMoney;

	/** 身故和全殊帐户保险金 */
	private Double DeadDisabAccIns;

	/** 年满60周岁后的首个保单周年日24时之前 */
	private String Be60YearsOfInsured;

	/** 年满60周岁后的首个保单周年日24时之后 */
	private String Af60YearsOfInsured;

	/** 首个生存保险金给付日 当日24时之前 */
	private String BeFirstSurvivalBenefit;

	/** 首个生存保险金给付日 当日24时之后 */
	private String AfFirstSurvivalBenefit;

	/** 已交保费减去累计已给付的基本生存保险金 */
	private Double PremiumedMinusAnnuityed;

	/** 累计增额红利 */
	private Double Additionalbonus;

	/** 被保险人身故之日起至满期日未给付的生存保险金期数 */
	private Double NotPayOfAnnuity;

	/** 身故前12个月内提取的款项 */
	private Double BD12FThePayment;

	/** 身故到满期经过的保单周年日的个数 */
	private Double DieAndPolicyAnnDays;

	/** 身故时上一保单周年日领取的养老年金金额 */
	private Double AnnAmntBeforeDeath;

	/** 已给付养老年金的保单年度个数 */
	private Double YearsOfAnnitied;



	// @Constructor
	public BOMCalClaim()
	{  }

	public void setOamnt( Double Oamnt )
	{
	  this.Oamnt = Oamnt;
 	}

	public Double getOamnt()
	{
	  return Oamnt;
	}

	public void setContNo(String  ContNo )
	{
	  this.ContNo = ContNo;
 	}

	public String getContNo()
	{
	  return ContNo;
	}

	public void setPolNo(String  PolNo )
	{
	  this.PolNo = PolNo;
 	}

	public String getPolNo()
	{
	  return PolNo;
	}

	public void setInsuredNo(String  InsuredNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(InsuredNo)))){
	    	return;
  	   }else{
	    	this.InsuredNo = InsuredNo;
	  }
 	}

	public String getInsuredNo()
	{
	  return InsuredNo;
	}

	public void setAccNo(String  AccNo )
	{
	  this.AccNo = AccNo;
 	}

	public String getAccNo()
	{
	  return AccNo;
	}

	public void setRgtNo(String  RgtNo )
	{
	  this.RgtNo = RgtNo;
 	}

	public String getRgtNo()
	{
	  return RgtNo;
	}

	public void setAccDate(Date  AccDate )
	{
	  this.AccDate = AccDate;
 	}

	public Date getAccDate()
	{
	  return AccDate;
	}

	public void setRgtClass(String  RgtClass )
	{
	  this.RgtClass = RgtClass;
 	}

	public String getRgtClass()
	{
	  return RgtClass;
	}

	public void setclmState(String  clmState )
	{
	  this.clmState = clmState;
 	}

	public String getclmState()
	{
	  return clmState;
	}

	public void setClmNo(String  ClmNo )
	{
	  this.ClmNo = ClmNo;
 	}

	public String getClmNo()
	{
	  return ClmNo;
	}

	public void setCaseNo(String  CaseNo )
	{
	  this.CaseNo = CaseNo;
 	}

	public String getCaseNo()
	{
	  return CaseNo;
	}

	public void setBackNo(String  BackNo )
	{
	  this.BackNo = BackNo;
 	}

	public String getBackNo()
	{
	  return BackNo;
	}

	public void setConType(String  ConType )
	{
	  this.ConType = ConType;
 	}

	public String getConType()
	{
	  return ConType;
	}

	public void setDutyCode(String  DutyCode )
	{
	  this.DutyCode = DutyCode;
 	}

	public String getDutyCode()
	{
	  return DutyCode;
	}

	public void setGetDutyKind(String  GetDutyKind )
	{
	  this.GetDutyKind = GetDutyKind;
 	}

	public String getGetDutyKind()
	{
	  return GetDutyKind;
	}

	public void setCasePolType(String  CasePolType )
	{
	  this.CasePolType = CasePolType;
 	}

	public String getCasePolType()
	{
	  return CasePolType;
	}

	public void setDocCode(String  DocCode )
	{
	  this.DocCode = DocCode;
 	}

	public String getDocCode()
	{
	  return DocCode;
	}

	public void setInsureAccBalance( Double InsureAccBalance )
	{
	  this.InsureAccBalance = InsureAccBalance;
 	}

	public Double getInsureAccBalance()
	{
	  return InsureAccBalance;
	}

	public void setSumPrem( Double SumPrem )
	{
	  this.SumPrem = SumPrem;
 	}

	public Double getSumPrem()
	{
	  return SumPrem;
	}

	public void setJegf( Double Jegf )
	{
	  this.Jegf = Jegf;
 	}

	public Double getJegf()
	{
	  return Jegf;
	}

	public void setAmnt( Double Amnt )
	{
	  this.Amnt = Amnt;
 	}

	public Double getAmnt()
	{
	  return Amnt;
	}

	public void setRgtYears( Double RgtYears )
	{
	  this.RgtYears = RgtYears;
 	}

	public Double getRgtYears()
	{
	  return RgtYears;
	}

	public void setPolPay( Double PolPay )
	{
	  this.PolPay = PolPay;
 	}

	public Double getPolPay()
	{
	  return PolPay;
	}

	public void setPay( Double Pay )
	{
	  this.Pay = Pay;
 	}

	public Double getPay()
	{
	  return Pay;
	}

	public void setFeeOperationType(String  FeeOperationType )
	{
	  this.FeeOperationType = FeeOperationType;
 	}

	public String getFeeOperationType()
	{
	  return FeeOperationType;
	}

	public void setDaysInHos( Double DaysInHos )
	{
	  this.DaysInHos = DaysInHos;
 	}

	public Double getDaysInHos()
	{
	  return DaysInHos;
	}

	public void setAccYear( Double AccYear )
	{
	  this.AccYear = AccYear;
 	}

	public Double getAccYear()
	{
	  return AccYear;
	}

	public void setExemptPeriod( Double ExemptPeriod )
	{
	  this.ExemptPeriod = ExemptPeriod;
 	}

	public Double getExemptPeriod()
	{
	  return ExemptPeriod;
	}

	public void setLeaveAmnt( Double LeaveAmnt )
	{
	  this.LeaveAmnt = LeaveAmnt;
 	}

	public Double getLeaveAmnt()
	{
	  return LeaveAmnt;
	}

	public void setDiseaseRate( Double DiseaseRate )
	{
	  this.DiseaseRate = DiseaseRate;
 	}

	public Double getDiseaseRate()
	{
	  return DiseaseRate;
	}

	public void setAccidentDetail(String  AccidentDetail )
	{
	  this.AccidentDetail = AccidentDetail;
 	}

	public String getAccidentDetail()
	{
	  return AccidentDetail;
	}

	public void setMaxAmnt( Double MaxAmnt )
	{
	  this.MaxAmnt = MaxAmnt;
 	}

	public Double getMaxAmnt()
	{
	  return MaxAmnt;
	}

	public void setClaimCount( Double ClaimCount )
	{
	  this.ClaimCount = ClaimCount;
 	}

	public Double getClaimCount()
	{
	  return ClaimCount;
	}

	public void setGetStartDate(Date  GetStartDate )
	{
	  this.GetStartDate = GetStartDate;
 	}

	public Date getGetStartDate()
	{
	  return GetStartDate;
	}

	public void setDutyFeeCode(String  DutyFeeCode )
	{
	  this.DutyFeeCode = DutyFeeCode;
 	}

	public String getDutyFeeCode()
	{
	  return DutyFeeCode;
	}

	public void setDutyFeeStaNo(String  DutyFeeStaNo )
	{
	  this.DutyFeeStaNo = DutyFeeStaNo;
 	}

	public String getDutyFeeStaNo()
	{
	  return DutyFeeStaNo;
	}

	public void setInsuredvalidBirth(Date  InsuredvalidBirth )
	{
	  this.InsuredvalidBirth = InsuredvalidBirth;
 	}

	public Date getInsuredvalidBirth()
	{
	  return InsuredvalidBirth;
	}

	public void setRgtDays( Double RgtDays )
	{
	  this.RgtDays = RgtDays;
 	}

	public Double getRgtDays()
	{
	  return RgtDays;
	}

	public void setGetDutyCode(String  GetDutyCode )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(GetDutyCode)))){
	    	return;
  	   }else{
	    	this.GetDutyCode = GetDutyCode;
	  }
 	}

	public String getGetDutyCode()
	{
	  return GetDutyCode;
	}

	public void setRgtmonths( Double Rgtmonths )
	{
	  this.Rgtmonths = Rgtmonths;
 	}

	public Double getRgtmonths()
	{
	  return Rgtmonths;
	}

	public void setSumAmntBonus( Double SumAmntBonus )
	{
	  this.SumAmntBonus = SumAmntBonus;
 	}

	public Double getSumAmntBonus()
	{
	  return SumAmntBonus;
	}

	public void setCaseRelaNo(String  CaseRelaNo )
	{
	  this.CaseRelaNo = CaseRelaNo;
 	}

	public String getCaseRelaNo()
	{
	  return CaseRelaNo;
	}

	public void setAccidentReason(String  AccidentReason )
	{
	  this.AccidentReason = AccidentReason;
 	}

	public String getAccidentReason()
	{
	  return AccidentReason;
	}

	public void setMLRYears( Double MLRYears )
	{
	  this.MLRYears = MLRYears;
 	}

	public Double getMLRYears()
	{
	  return MLRYears;
	}

	public void setMLRDays( Double MLRDays )
	{
	  this.MLRDays = MLRDays;
 	}

	public Double getMLRDays()
	{
	  return MLRDays;
	}

	public void setTRMAmnt( Double TRMAmnt )
	{
	  this.TRMAmnt = TRMAmnt;
 	}

	public Double getTRMAmnt()
	{
	  return TRMAmnt;
	}

	public void setRMAmnt( Double RMAmnt )
	{
	  this.RMAmnt = RMAmnt;
 	}

	public Double getRMAmnt()
	{
	  return RMAmnt;
	}

	public void setFeereceiEndDate(Date  FeereceiEndDate )
	{
	  this.FeereceiEndDate = FeereceiEndDate;
 	}

	public Date getFeereceiEndDate()
	{
	  return FeereceiEndDate;
	}

	public void setCurrentDutyPay( Double CurrentDutyPay )
	{
	  this.CurrentDutyPay = CurrentDutyPay;
 	}

	public Double getCurrentDutyPay()
	{
	  return CurrentDutyPay;
	}

	public void setCurrentClDutyPay( Double CurrentClDutyPay )
	{
	  this.CurrentClDutyPay = CurrentClDutyPay;
 	}

	public Double getCurrentClDutyPay()
	{
	  return CurrentClDutyPay;
	}

	public void setCompensateDutySumPay( Double CompensateDutySumPay )
	{
	  this.CompensateDutySumPay = CompensateDutySumPay;
 	}

	public Double getCompensateDutySumPay()
	{
	  return CompensateDutySumPay;
	}

	public void setGetDutySumPay( Double GetDutySumPay )
	{
	  this.GetDutySumPay = GetDutySumPay;
 	}

	public Double getGetDutySumPay()
	{
	  return GetDutySumPay;
	}

	public void setPupilAmnt( Double PupilAmnt )
	{
	  this.PupilAmnt = PupilAmnt;
 	}

	public Double getPupilAmnt()
	{
	  return PupilAmnt;
	}

	public void setSumAccGet( Double SumAccGet )
	{
	  this.SumAccGet = SumAccGet;
 	}

	public Double getSumAccGet()
	{
	  return SumAccGet;
	}

	public void setRateAmnt( Double RateAmnt )
	{
	  this.RateAmnt = RateAmnt;
 	}

	public Double getRateAmnt()
	{
	  return RateAmnt;
	}

	public void setGetEndDate(Date  GetEndDate )
	{
	  this.GetEndDate = GetEndDate;
 	}

	public Date getGetEndDate()
	{
	  return GetEndDate;
	}

	public void setPaytoDatYears( Double PaytoDatYears )
	{
	  this.PaytoDatYears = PaytoDatYears;
 	}

	public Double getPaytoDatYears()
	{
	  return PaytoDatYears;
	}

	public void setTotalPrem( Double TotalPrem )
	{
	  this.TotalPrem = TotalPrem;
 	}

	public Double getTotalPrem()
	{
	  return TotalPrem;
	}

	public void setCureType(String  CureType )
	{
	  this.CureType = CureType;
 	}

	public String getCureType()
	{
	  return CureType;
	}

	public void setInHospdayFee( Double InHospdayFee )
	{
	  this.InHospdayFee = InHospdayFee;
 	}

	public Double getInHospdayFee()
	{
	  return InHospdayFee;
	}

	public void setDefoRate( Double DefoRate )
	{
	  this.DefoRate = DefoRate;
 	}

	public Double getDefoRate()
	{
	  return DefoRate;
	}

	public void setEndFeeDate(Date  EndFeeDate )
	{
	  this.EndFeeDate = EndFeeDate;
 	}

	public Date getEndFeeDate()
	{
	  return EndFeeDate;
	}

	public void setStartFeeDate(Date  StartFeeDate )
	{
	  this.StartFeeDate = StartFeeDate;
 	}

	public Date getStartFeeDate()
	{
	  return StartFeeDate;
	}

	public void setEndPolDate(Date  EndPolDate )
	{
	  this.EndPolDate = EndPolDate;
 	}

	public Date getEndPolDate()
	{
	  return EndPolDate;
	}

	public void setFinalBonus( Double FinalBonus )
	{
	  this.FinalBonus = FinalBonus;
 	}

	public Double getFinalBonus()
	{
	  return FinalBonus;
	}

	public void setGetAge( Double GetAge )
	{
	  this.GetAge = GetAge;
 	}

	public Double getGetAge()
	{
	  return GetAge;
	}

	public void setDeathDate(Date  DeathDate )
	{
	  this.DeathDate = DeathDate;
 	}

	public Date getDeathDate()
	{
	  return DeathDate;
	}

	public void setRenewCount( Double RenewCount )
	{
	  this.RenewCount = RenewCount;
 	}

	public Double getRenewCount()
	{
	  return RenewCount;
	}

	public void setMLRFlag(String  MLRFlag )
	{
	  this.MLRFlag = MLRFlag;
 	}

	public String getMLRFlag()
	{
	  return MLRFlag;
	}

	public void setSumMoney( Double SumMoney )
	{
	  this.SumMoney = SumMoney;
 	}

	public Double getSumMoney()
	{
	  return SumMoney;
	}

	public void setRealAdjSumB( Double RealAdjSumB )
	{
	  this.RealAdjSumB = RealAdjSumB;
 	}

	public Double getRealAdjSumB()
	{
	  return RealAdjSumB;
	}

	public void setRealAdjSumA( Double RealAdjSumA )
	{
	  this.RealAdjSumA = RealAdjSumA;
 	}

	public Double getRealAdjSumA()
	{
	  return RealAdjSumA;
	}

	public void setRiskGLSYDeathBenefit( Double RiskGLSYDeathBenefit )
	{
	  this.RiskGLSYDeathBenefit = RiskGLSYDeathBenefit;
 	}

	public Double getRiskGLSYDeathBenefit()
	{
	  return RiskGLSYDeathBenefit;
	}

	public void setCurrentRelateDutyPay( Double CurrentRelateDutyPay )
	{
	  this.CurrentRelateDutyPay = CurrentRelateDutyPay;
 	}

	public Double getCurrentRelateDutyPay()
	{
	  return CurrentRelateDutyPay;
	}

	public void setGetDutyRelateSumPay( Double GetDutyRelateSumPay )
	{
	  this.GetDutyRelateSumPay = GetDutyRelateSumPay;
 	}

	public Double getGetDutyRelateSumPay()
	{
	  return GetDutyRelateSumPay;
	}

	public void setAcciDeathInsura( Double AcciDeathInsura )
	{
	  this.AcciDeathInsura = AcciDeathInsura;
 	}

	public Double getAcciDeathInsura()
	{
	  return AcciDeathInsura;
	}

	public void setDiseaDeathInsura( Double DiseaDeathInsura )
	{
	  this.DiseaDeathInsura = DiseaDeathInsura;
 	}

	public Double getDiseaDeathInsura()
	{
	  return DiseaDeathInsura;
	}

	public void setSocialInsuRate( Double SocialInsuRate )
	{
	  this.SocialInsuRate = SocialInsuRate;
 	}

	public Double getSocialInsuRate()
	{
	  return SocialInsuRate;
	}

	public void setValidAmnt( Double ValidAmnt )
	{
	  this.ValidAmnt = ValidAmnt;
 	}

	public Double getValidAmnt()
	{
	  return ValidAmnt;
	}

	public void setTotalLimit( Double TotalLimit )
	{
	  this.TotalLimit = TotalLimit;
 	}

	public Double getTotalLimit()
	{
	  return TotalLimit;
	}

	public void setObserveDate( Double ObserveDate )
	{
	  this.ObserveDate = ObserveDate;
 	}

	public Double getObserveDate()
	{
	  return ObserveDate;
	}

	public void setExemptMoney( Double ExemptMoney )
	{
	  this.ExemptMoney = ExemptMoney;
 	}

	public Double getExemptMoney()
	{
	  return ExemptMoney;
	}

	public void setDeadDisabAccIns( Double DeadDisabAccIns )
	{
	  this.DeadDisabAccIns = DeadDisabAccIns;
 	}

	public Double getDeadDisabAccIns()
	{
	  return DeadDisabAccIns;
	}

	public void setBe60YearsOfInsured(String  Be60YearsOfInsured )
	{
	  this.Be60YearsOfInsured = Be60YearsOfInsured;
 	}

	public String getBe60YearsOfInsured()
	{
	  return Be60YearsOfInsured;
	}

	public void setAf60YearsOfInsured(String  Af60YearsOfInsured )
	{
	  this.Af60YearsOfInsured = Af60YearsOfInsured;
 	}

	public String getAf60YearsOfInsured()
	{
	  return Af60YearsOfInsured;
	}

	public void setBeFirstSurvivalBenefit(String  BeFirstSurvivalBenefit )
	{
	  this.BeFirstSurvivalBenefit = BeFirstSurvivalBenefit;
 	}

	public String getBeFirstSurvivalBenefit()
	{
	  return BeFirstSurvivalBenefit;
	}

	public void setAfFirstSurvivalBenefit(String  AfFirstSurvivalBenefit )
	{
	  this.AfFirstSurvivalBenefit = AfFirstSurvivalBenefit;
 	}

	public String getAfFirstSurvivalBenefit()
	{
	  return AfFirstSurvivalBenefit;
	}

	public void setPremiumedMinusAnnuityed( Double PremiumedMinusAnnuityed )
	{
	  this.PremiumedMinusAnnuityed = PremiumedMinusAnnuityed;
 	}

	public Double getPremiumedMinusAnnuityed()
	{
	  return PremiumedMinusAnnuityed;
	}

	public void setAdditionalbonus( Double Additionalbonus )
	{
	  this.Additionalbonus = Additionalbonus;
 	}

	public Double getAdditionalbonus()
	{
	  return Additionalbonus;
	}

	public void setNotPayOfAnnuity( Double NotPayOfAnnuity )
	{
	  this.NotPayOfAnnuity = NotPayOfAnnuity;
 	}

	public Double getNotPayOfAnnuity()
	{
	  return NotPayOfAnnuity;
	}

	public void setBD12FThePayment( Double BD12FThePayment )
	{
	  this.BD12FThePayment = BD12FThePayment;
 	}

	public Double getBD12FThePayment()
	{
	  return BD12FThePayment;
	}

	public void setDieAndPolicyAnnDays( Double DieAndPolicyAnnDays )
	{
	  this.DieAndPolicyAnnDays = DieAndPolicyAnnDays;
 	}

	public Double getDieAndPolicyAnnDays()
	{
	  return DieAndPolicyAnnDays;
	}

	public void setAnnAmntBeforeDeath( Double AnnAmntBeforeDeath )
	{
	  this.AnnAmntBeforeDeath = AnnAmntBeforeDeath;
 	}

	public Double getAnnAmntBeforeDeath()
	{
	  return AnnAmntBeforeDeath;
	}

	public void setYearsOfAnnitied( Double YearsOfAnnitied )
	{
	  this.YearsOfAnnitied = YearsOfAnnitied;
 	}

	public Double getYearsOfAnnitied()
	{
	  return YearsOfAnnitied;
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

	if("Oamnt".equals(FCode))
		{
			setOamnt(Double.valueOf(FValue));
		}

	if("ContNo".equals(FCode))
		{
		    setContNo(FValue);
		}

	if("PolNo".equals(FCode))
		{
		    setPolNo(FValue);
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("AccNo".equals(FCode))
		{
		    setAccNo(FValue);
		}

	if("RgtNo".equals(FCode))
		{
		    setRgtNo(FValue);
		}

	if("AccDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setAccDate(d);
		}

	if("RgtClass".equals(FCode))
		{
		    setRgtClass(FValue);
		}

	if("clmState".equals(FCode))
		{
		    setclmState(FValue);
		}

	if("ClmNo".equals(FCode))
		{
		    setClmNo(FValue);
		}

	if("CaseNo".equals(FCode))
		{
		    setCaseNo(FValue);
		}

	if("BackNo".equals(FCode))
		{
		    setBackNo(FValue);
		}

	if("ConType".equals(FCode))
		{
		    setConType(FValue);
		}

	if("DutyCode".equals(FCode))
		{
		    setDutyCode(FValue);
		}

	if("GetDutyKind".equals(FCode))
		{
		    setGetDutyKind(FValue);
		}

	if("CasePolType".equals(FCode))
		{
		    setCasePolType(FValue);
		}

	if("DocCode".equals(FCode))
		{
		    setDocCode(FValue);
		}

	if("InsureAccBalance".equals(FCode))
		{
			setInsureAccBalance(Double.valueOf(FValue));
		}

	if("SumPrem".equals(FCode))
		{
			setSumPrem(Double.valueOf(FValue));
		}

	if("Jegf".equals(FCode))
		{
			setJegf(Double.valueOf(FValue));
		}

	if("Amnt".equals(FCode))
		{
			setAmnt(Double.valueOf(FValue));
		}

	if("RgtYears".equals(FCode))
		{
			setRgtYears(Double.valueOf(FValue));
		}

	if("PolPay".equals(FCode))
		{
			setPolPay(Double.valueOf(FValue));
		}

	if("Pay".equals(FCode))
		{
			setPay(Double.valueOf(FValue));
		}

	if("FeeOperationType".equals(FCode))
		{
		    setFeeOperationType(FValue);
		}

	if("DaysInHos".equals(FCode))
		{
			setDaysInHos(Double.valueOf(FValue));
		}

	if("AccYear".equals(FCode))
		{
			setAccYear(Double.valueOf(FValue));
		}

	if("ExemptPeriod".equals(FCode))
		{
			setExemptPeriod(Double.valueOf(FValue));
		}

	if("LeaveAmnt".equals(FCode))
		{
			setLeaveAmnt(Double.valueOf(FValue));
		}

	if("DiseaseRate".equals(FCode))
		{
			setDiseaseRate(Double.valueOf(FValue));
		}

	if("AccidentDetail".equals(FCode))
		{
		    setAccidentDetail(FValue);
		}

	if("MaxAmnt".equals(FCode))
		{
			setMaxAmnt(Double.valueOf(FValue));
		}

	if("ClaimCount".equals(FCode))
		{
			setClaimCount(Double.valueOf(FValue));
		}

	if("GetStartDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setGetStartDate(d);
		}

	if("DutyFeeCode".equals(FCode))
		{
		    setDutyFeeCode(FValue);
		}

	if("DutyFeeStaNo".equals(FCode))
		{
		    setDutyFeeStaNo(FValue);
		}

	if("InsuredvalidBirth".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setInsuredvalidBirth(d);
		}

	if("RgtDays".equals(FCode))
		{
			setRgtDays(Double.valueOf(FValue));
		}

	if("GetDutyCode".equals(FCode))
		{
		    setGetDutyCode(FValue);
		}

	if("Rgtmonths".equals(FCode))
		{
			setRgtmonths(Double.valueOf(FValue));
		}

	if("SumAmntBonus".equals(FCode))
		{
			setSumAmntBonus(Double.valueOf(FValue));
		}

	if("CaseRelaNo".equals(FCode))
		{
		    setCaseRelaNo(FValue);
		}

	if("AccidentReason".equals(FCode))
		{
		    setAccidentReason(FValue);
		}

	if("MLRYears".equals(FCode))
		{
			setMLRYears(Double.valueOf(FValue));
		}

	if("MLRDays".equals(FCode))
		{
			setMLRDays(Double.valueOf(FValue));
		}

	if("TRMAmnt".equals(FCode))
		{
			setTRMAmnt(Double.valueOf(FValue));
		}

	if("RMAmnt".equals(FCode))
		{
			setRMAmnt(Double.valueOf(FValue));
		}

	if("FeereceiEndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setFeereceiEndDate(d);
		}

	if("CurrentDutyPay".equals(FCode))
		{
			setCurrentDutyPay(Double.valueOf(FValue));
		}

	if("CurrentClDutyPay".equals(FCode))
		{
			setCurrentClDutyPay(Double.valueOf(FValue));
		}

	if("CompensateDutySumPay".equals(FCode))
		{
			setCompensateDutySumPay(Double.valueOf(FValue));
		}

	if("GetDutySumPay".equals(FCode))
		{
			setGetDutySumPay(Double.valueOf(FValue));
		}

	if("PupilAmnt".equals(FCode))
		{
			setPupilAmnt(Double.valueOf(FValue));
		}

	if("SumAccGet".equals(FCode))
		{
			setSumAccGet(Double.valueOf(FValue));
		}

	if("RateAmnt".equals(FCode))
		{
			setRateAmnt(Double.valueOf(FValue));
		}

	if("GetEndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setGetEndDate(d);
		}

	if("PaytoDatYears".equals(FCode))
		{
			setPaytoDatYears(Double.valueOf(FValue));
		}

	if("TotalPrem".equals(FCode))
		{
			setTotalPrem(Double.valueOf(FValue));
		}

	if("CureType".equals(FCode))
		{
		    setCureType(FValue);
		}

	if("InHospdayFee".equals(FCode))
		{
			setInHospdayFee(Double.valueOf(FValue));
		}

	if("DefoRate".equals(FCode))
		{
			setDefoRate(Double.valueOf(FValue));
		}

	if("EndFeeDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEndFeeDate(d);
		}

	if("StartFeeDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStartFeeDate(d);
		}

	if("EndPolDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEndPolDate(d);
		}

	if("FinalBonus".equals(FCode))
		{
			setFinalBonus(Double.valueOf(FValue));
		}

	if("GetAge".equals(FCode))
		{
			setGetAge(Double.valueOf(FValue));
		}

	if("DeathDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setDeathDate(d);
		}

	if("RenewCount".equals(FCode))
		{
			setRenewCount(Double.valueOf(FValue));
		}

	if("MLRFlag".equals(FCode))
		{
		    setMLRFlag(FValue);
		}

	if("SumMoney".equals(FCode))
		{
			setSumMoney(Double.valueOf(FValue));
		}

	if("RealAdjSumB".equals(FCode))
		{
			setRealAdjSumB(Double.valueOf(FValue));
		}

	if("RealAdjSumA".equals(FCode))
		{
			setRealAdjSumA(Double.valueOf(FValue));
		}

	if("RiskGLSYDeathBenefit".equals(FCode))
		{
			setRiskGLSYDeathBenefit(Double.valueOf(FValue));
		}

	if("CurrentRelateDutyPay".equals(FCode))
		{
			setCurrentRelateDutyPay(Double.valueOf(FValue));
		}

	if("GetDutyRelateSumPay".equals(FCode))
		{
			setGetDutyRelateSumPay(Double.valueOf(FValue));
		}

	if("AcciDeathInsura".equals(FCode))
		{
			setAcciDeathInsura(Double.valueOf(FValue));
		}

	if("DiseaDeathInsura".equals(FCode))
		{
			setDiseaDeathInsura(Double.valueOf(FValue));
		}

	if("SocialInsuRate".equals(FCode))
		{
			setSocialInsuRate(Double.valueOf(FValue));
		}

	if("ValidAmnt".equals(FCode))
		{
			setValidAmnt(Double.valueOf(FValue));
		}

	if("TotalLimit".equals(FCode))
		{
			setTotalLimit(Double.valueOf(FValue));
		}

	if("ObserveDate".equals(FCode))
		{
			setObserveDate(Double.valueOf(FValue));
		}

	if("ExemptMoney".equals(FCode))
		{
			setExemptMoney(Double.valueOf(FValue));
		}

	if("DeadDisabAccIns".equals(FCode))
		{
			setDeadDisabAccIns(Double.valueOf(FValue));
		}

	if("Be60YearsOfInsured".equals(FCode))
		{
		    setBe60YearsOfInsured(FValue);
		}

	if("Af60YearsOfInsured".equals(FCode))
		{
		    setAf60YearsOfInsured(FValue);
		}

	if("BeFirstSurvivalBenefit".equals(FCode))
		{
		    setBeFirstSurvivalBenefit(FValue);
		}

	if("AfFirstSurvivalBenefit".equals(FCode))
		{
		    setAfFirstSurvivalBenefit(FValue);
		}

	if("PremiumedMinusAnnuityed".equals(FCode))
		{
			setPremiumedMinusAnnuityed(Double.valueOf(FValue));
		}

	if("Additionalbonus".equals(FCode))
		{
			setAdditionalbonus(Double.valueOf(FValue));
		}

	if("NotPayOfAnnuity".equals(FCode))
		{
			setNotPayOfAnnuity(Double.valueOf(FValue));
		}

	if("BD12FThePayment".equals(FCode))
		{
			setBD12FThePayment(Double.valueOf(FValue));
		}

	if("DieAndPolicyAnnDays".equals(FCode))
		{
			setDieAndPolicyAnnDays(Double.valueOf(FValue));
		}

	if("AnnAmntBeforeDeath".equals(FCode))
		{
			setAnnAmntBeforeDeath(Double.valueOf(FValue));
		}

	if("YearsOfAnnitied".equals(FCode))
		{
			setYearsOfAnnitied(Double.valueOf(FValue));
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
	  if (FCode.equalsIgnoreCase("Oamnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOamnt()));
	  }
	  if (FCode.equalsIgnoreCase("ContNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getContNo()));
	  }
	  if (FCode.equalsIgnoreCase("PolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("AccNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccNo()));
	  }
	  if (FCode.equalsIgnoreCase("RgtNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtNo()));
	  }
	  if (FCode.equalsIgnoreCase("AccDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getAccDate())));
	  }
	  if (FCode.equalsIgnoreCase("RgtClass"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtClass()));
	  }
	  if (FCode.equalsIgnoreCase("clmState"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getclmState()));
	  }
	  if (FCode.equalsIgnoreCase("ClmNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getClmNo()));
	  }
	  if (FCode.equalsIgnoreCase("CaseNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCaseNo()));
	  }
	  if (FCode.equalsIgnoreCase("BackNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBackNo()));
	  }
	  if (FCode.equalsIgnoreCase("ConType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getConType()));
	  }
	  if (FCode.equalsIgnoreCase("DutyCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDutyCode()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutyKind"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutyKind()));
	  }
	  if (FCode.equalsIgnoreCase("CasePolType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCasePolType()));
	  }
	  if (FCode.equalsIgnoreCase("DocCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDocCode()));
	  }
	  if (FCode.equalsIgnoreCase("InsureAccBalance"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsureAccBalance()));
	  }
	  if (FCode.equalsIgnoreCase("SumPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumPrem()));
	  }
	  if (FCode.equalsIgnoreCase("Jegf"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getJegf()));
	  }
	  if (FCode.equalsIgnoreCase("Amnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("RgtYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtYears()));
	  }
	  if (FCode.equalsIgnoreCase("PolPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolPay()));
	  }
	  if (FCode.equalsIgnoreCase("Pay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPay()));
	  }
	  if (FCode.equalsIgnoreCase("FeeOperationType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFeeOperationType()));
	  }
	  if (FCode.equalsIgnoreCase("DaysInHos"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDaysInHos()));
	  }
	  if (FCode.equalsIgnoreCase("AccYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccYear()));
	  }
	  if (FCode.equalsIgnoreCase("ExemptPeriod"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getExemptPeriod()));
	  }
	  if (FCode.equalsIgnoreCase("LeaveAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLeaveAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("DiseaseRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDiseaseRate()));
	  }
	  if (FCode.equalsIgnoreCase("AccidentDetail"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccidentDetail()));
	  }
	  if (FCode.equalsIgnoreCase("MaxAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMaxAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("ClaimCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getClaimCount()));
	  }
	  if (FCode.equalsIgnoreCase("GetStartDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getGetStartDate())));
	  }
	  if (FCode.equalsIgnoreCase("DutyFeeCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDutyFeeCode()));
	  }
	  if (FCode.equalsIgnoreCase("DutyFeeStaNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDutyFeeStaNo()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredvalidBirth"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getInsuredvalidBirth())));
	  }
	  if (FCode.equalsIgnoreCase("RgtDays"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtDays()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutyCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutyCode()));
	  }
	  if (FCode.equalsIgnoreCase("Rgtmonths"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtmonths()));
	  }
	  if (FCode.equalsIgnoreCase("SumAmntBonus"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumAmntBonus()));
	  }
	  if (FCode.equalsIgnoreCase("CaseRelaNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCaseRelaNo()));
	  }
	  if (FCode.equalsIgnoreCase("AccidentReason"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccidentReason()));
	  }
	  if (FCode.equalsIgnoreCase("MLRYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMLRYears()));
	  }
	  if (FCode.equalsIgnoreCase("MLRDays"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMLRDays()));
	  }
	  if (FCode.equalsIgnoreCase("TRMAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTRMAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("RMAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRMAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("FeereceiEndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getFeereceiEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("CurrentDutyPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrentDutyPay()));
	  }
	  if (FCode.equalsIgnoreCase("CurrentClDutyPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrentClDutyPay()));
	  }
	  if (FCode.equalsIgnoreCase("CompensateDutySumPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCompensateDutySumPay()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutySumPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutySumPay()));
	  }
	  if (FCode.equalsIgnoreCase("PupilAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPupilAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumAccGet"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumAccGet()));
	  }
	  if (FCode.equalsIgnoreCase("RateAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRateAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("GetEndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getGetEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("PaytoDatYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPaytoDatYears()));
	  }
	  if (FCode.equalsIgnoreCase("TotalPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTotalPrem()));
	  }
	  if (FCode.equalsIgnoreCase("CureType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCureType()));
	  }
	  if (FCode.equalsIgnoreCase("InHospdayFee"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInHospdayFee()));
	  }
	  if (FCode.equalsIgnoreCase("DefoRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDefoRate()));
	  }
	  if (FCode.equalsIgnoreCase("EndFeeDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEndFeeDate())));
	  }
	  if (FCode.equalsIgnoreCase("StartFeeDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStartFeeDate())));
	  }
	  if (FCode.equalsIgnoreCase("EndPolDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEndPolDate())));
	  }
	  if (FCode.equalsIgnoreCase("FinalBonus"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFinalBonus()));
	  }
	  if (FCode.equalsIgnoreCase("GetAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetAge()));
	  }
	  if (FCode.equalsIgnoreCase("DeathDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getDeathDate())));
	  }
	  if (FCode.equalsIgnoreCase("RenewCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRenewCount()));
	  }
	  if (FCode.equalsIgnoreCase("MLRFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMLRFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SumMoney"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumMoney()));
	  }
	  if (FCode.equalsIgnoreCase("RealAdjSumB"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRealAdjSumB()));
	  }
	  if (FCode.equalsIgnoreCase("RealAdjSumA"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRealAdjSumA()));
	  }
	  if (FCode.equalsIgnoreCase("RiskGLSYDeathBenefit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRiskGLSYDeathBenefit()));
	  }
	  if (FCode.equalsIgnoreCase("CurrentRelateDutyPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrentRelateDutyPay()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutyRelateSumPay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutyRelateSumPay()));
	  }
	  if (FCode.equalsIgnoreCase("AcciDeathInsura"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAcciDeathInsura()));
	  }
	  if (FCode.equalsIgnoreCase("DiseaDeathInsura"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDiseaDeathInsura()));
	  }
	  if (FCode.equalsIgnoreCase("SocialInsuRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSocialInsuRate()));
	  }
	  if (FCode.equalsIgnoreCase("ValidAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getValidAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("TotalLimit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTotalLimit()));
	  }
	  if (FCode.equalsIgnoreCase("ObserveDate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getObserveDate()));
	  }
	  if (FCode.equalsIgnoreCase("ExemptMoney"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getExemptMoney()));
	  }
	  if (FCode.equalsIgnoreCase("DeadDisabAccIns"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDeadDisabAccIns()));
	  }
	  if (FCode.equalsIgnoreCase("Be60YearsOfInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBe60YearsOfInsured()));
	  }
	  if (FCode.equalsIgnoreCase("Af60YearsOfInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAf60YearsOfInsured()));
	  }
	  if (FCode.equalsIgnoreCase("BeFirstSurvivalBenefit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBeFirstSurvivalBenefit()));
	  }
	  if (FCode.equalsIgnoreCase("AfFirstSurvivalBenefit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAfFirstSurvivalBenefit()));
	  }
	  if (FCode.equalsIgnoreCase("PremiumedMinusAnnuityed"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPremiumedMinusAnnuityed()));
	  }
	  if (FCode.equalsIgnoreCase("Additionalbonus"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAdditionalbonus()));
	  }
	  if (FCode.equalsIgnoreCase("NotPayOfAnnuity"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNotPayOfAnnuity()));
	  }
	  if (FCode.equalsIgnoreCase("BD12FThePayment"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBD12FThePayment()));
	  }
	  if (FCode.equalsIgnoreCase("DieAndPolicyAnnDays"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDieAndPolicyAnnDays()));
	  }
	  if (FCode.equalsIgnoreCase("AnnAmntBeforeDeath"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAnnAmntBeforeDeath()));
	  }
	  if (FCode.equalsIgnoreCase("YearsOfAnnitied"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getYearsOfAnnitied()));
	  }
	  if (strReturn.equals(""))
	  {
	     strReturn = "null";
	  }
	  return strReturn;
	}

	// @CErrors
	public CErrors getErrors(){
		return new BOMPreCheck().mErrors;
	}
}
