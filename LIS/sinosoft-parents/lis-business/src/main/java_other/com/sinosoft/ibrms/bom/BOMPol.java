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
 * <p>ClassName: BOMPol </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2016-11-15
 */

public class BOMPol extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";

	private BOMInsured tBOMInsured;

	/** 自动垫交标记 */
	private String AutoPayFlag;

	/** 未扣管理费帐户金额 */
	private Double AccValue;

	/** 标准保费 */
	private Double StandPrem;

	/** 险种终止日期 */
	private Date PayEndDate;

	/** 累计保费 */
	private Double SumPrem;

	/** 保险年期 */
	private Double Years;

	/** 个人累积生息帐户余额 */
	private Double GetBalance;

	/** 被保人号码 */
	private String InsuredNo;

	/** 红利领取人类型 */
	private String BonusManType;

	/** 停售标记 */
	private String StopFlag;

	/** 职业加费标记 */
	private String OAddFeeFlag;

	/** 生存金领取方式 */
	private String LiveGetMode;

	/** 健康加费标记 */
	private String HAddFeeFlag;

	/** 单位保额 */
	private Double VPU;

	/** 交费间隔 */
	private Double GetYear;

	/** 给付间隔 */
	private Double GetIntv;

	/** 责任终止日期 */
	private Date EndDate;

	/** 领取年龄年期标志 */
	private String GetYearFlag;

	/** 赔付比例 */
	private Double GetRate;

	/** 递增率 */
	private Double AddRate;

	/** 已付代码 */
	private String GetDutyCode;

	/** 保费减费/免费标志 */
	private String DerateOrFreeFlag;

	/** 累计该险种保额 */
	private Double TotalAmnt;

	/** 累计该险种份数 */
	private Double TotalMult;

	/** 责任编码 */
	private String DutyCode;

	/** 给付责任类型 */
	private String GetDutyKind;

	/** 起付线 */
	private Double GetLimit;

	/** 封顶线 */
	private Double PeakLine;

	/** 保单类型标记 */
	private String PolTypeFlag;

	/** 备用字段1 */
	private String StandbyFlag1;

	/** 备用字段1 */
	private String StandbyFlag2;

	/** 备用字段1 */
	private String StandbyFlag3;

	/** 被保人数目 */
	private Double InsuredPeoples;

	/** 续保原险种编码 */
	private String OldPolNo;

	/** 续保上一年度生效日期 */
	private Date StartDate;

	/** 保单状态 */
	private String PolState;

	/** 理赔是否提起续保标记 */
	private String ClaimToXBFlag;

	/** 红利金额 */
	private Double BonusMoney;

	/** 起领日期 */
	private Date GetStartDate;

	/** 生存领取开始年期 */
	private Double GetStartYear;

	/** 险种号码 */
	private String PolNo;

	/** 主险号码 */
	private String MainPolNo;

	/** 险种编码 */
	private String RiskCode;

	/** 总基本保额 */
	private Double Amnt;

	/** 总保费 */
	private Double Prem;

	/** 总份数 */
	private Double Mult;

	/** 浮动费率 */
	private Double FloatRate;

	/** 险种生效日期 */
	private Date CValiDate;

	/** 保险年龄年期 */
	private Double InsuYear;

	/** 保险期间单位 */
	private String InsuYearFlag;

	/** 险种类别 */
	private String KindCode;

	/** 核保状态 */
	private String UWFlag;

	/** 红利金领取方式 */
	private String BonusGetMode;

	/** 红利快计年度 */
	private Double BonusYear;

	/** 缴费终止年期或年龄标记 */
	private String PayEndYearFlag;

	/** 缴费终止年期或年龄 */
	private Double PayEndYear;

	/** 保单标记状态 */
	private String AppFlag;

	/** 险种帐户投资比例 */
	private Double InvestRate;

	/** 风险保额 */
	private Double RiskAmnt;

	/** 累计该险种保费 */
	private Double SumThisPrem;

	/** 交费期间 */
	private Double PayYears;

	/** 币种 */
	private String Currency;

	/** 折算系数 */
	private Double ModFactor;

	/** 本次生存金是第几次给付 */
	private Double GetTimes;

	/** 该险种理赔次数 */
	private Double PolClaimCount;

	/** 到期收益 */
	private Double MaturityBenefit;

	/** 红利保单年度 */
	private Double PolYear;

	/** 现金价值 */
	private Double CashValue;

	/** 增额红利的利率 */
	private Double RBRate;

	/** 终了红利的利率 */
	private Double TBRate;

	/** 已交保险费总额扣除累计已给付的年金 */
	private Double PremiumDeductedAnnuity;

	/** 账户价值 */
	private Double AccountValue;

	/** 未领取的红利利息 */
	private Double UnPaidBonousRate;

	/** t-1个保单年度末储蓄金(annuity or coupon) */
	private Double EVt1S;

	/** 退保时的交至日期与申请日期之间的天数 */
	private Double ThroughDay2;

	/** 较早(PTD与现价结算时间)和t保单年度之间的天数 */
	private Double ThroughDay1;

	/** t-1个保单年度的累计增额红利 */
	private Double RBt1;

	/** 追加保费 */
	private Double AddPrem;

	/** 推出日期 */
	private Date LaunchDate;

	/** 目标保费 */
	private Double TargetPrem;

	/** 前60个月的平均账户价值 */
	private Double AverageAccountValue60Mon;

	/** 交费频率 */
	private String Payfrequency;

	/** 主险是投连万能险 */
	private String MRiskisUniversalOrLink;

	/** 交费期间单位 */
	private String CoveragefeeduringtheUnit;

	/** 生存保险金的累计周期 */
	private String AccumulationPeriod;

	/** 首年度保险费的20% */
	private Double First20PremiumOfAnn;

	/** 已经生效或正在申请的身故保障金金额 */
	private Double ForOrProposalDiedMomey;

	/** 未领取的红利 */
	private Double UnPaidBonous;

	/** 缴至日期 */
	private Date PayToDate;

	/** t个保单年度末现金价值 */
	private Double CVt;

	/** t-1个保单年度末现金价值 */
	private Double CVt1S;

	/** 退保点所在保单年度的经过天数 */
	private Double ThroughDay;



	// @Constructor
	public BOMPol()
	{  }

	public void setFatherBOM(BOMInsured mBOMInsured)
	{
		this.tBOMInsured = mBOMInsured;
	}
 
	public AbstractBOM getFatherBOM()
	{
		return tBOMInsured;
	}

	public void setAutoPayFlag(String  AutoPayFlag )
	{
	  this.AutoPayFlag = AutoPayFlag;
 	}

	public String getAutoPayFlag()
	{
	  return AutoPayFlag;
	}

	public void setAccValue( Double AccValue )
	{
	  this.AccValue = AccValue;
 	}

	public Double getAccValue()
	{
	  return AccValue;
	}

	public void setStandPrem( Double StandPrem )
	{
	  this.StandPrem = StandPrem;
 	}

	public Double getStandPrem()
	{
	  return StandPrem;
	}

	public void setPayEndDate(Date  PayEndDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(PayEndDate)))){
	    	return;
  	   }else{
	    	this.PayEndDate = PayEndDate;
	  }
 	}

	public Date getPayEndDate()
	{
	  return PayEndDate;
	}

	public void setSumPrem( Double SumPrem )
	{
	  this.SumPrem = SumPrem;
 	}

	public Double getSumPrem()
	{
	  return SumPrem;
	}

	public void setYears( Double Years )
	{
	  this.Years = Years;
 	}

	public Double getYears()
	{
	  return Years;
	}

	public void setGetBalance( Double GetBalance )
	{
	  this.GetBalance = GetBalance;
 	}

	public Double getGetBalance()
	{
	  return GetBalance;
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

	public void setBonusManType(String  BonusManType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BonusManType)))){
	    	return;
  	   }else{
	    	this.BonusManType = BonusManType;
	  }
 	}

	public String getBonusManType()
	{
	  return BonusManType;
	}

	public void setStopFlag(String  StopFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(StopFlag)))){
	    	return;
  	   }else{
	    	this.StopFlag = StopFlag;
	  }
 	}

	public String getStopFlag()
	{
	  return StopFlag;
	}

	public void setOAddFeeFlag(String  OAddFeeFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OAddFeeFlag)))){
	    	return;
  	   }else{
	    	this.OAddFeeFlag = OAddFeeFlag;
	  }
 	}

	public String getOAddFeeFlag()
	{
	  return OAddFeeFlag;
	}

	public void setLiveGetMode(String  LiveGetMode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(LiveGetMode)))){
	    	return;
  	   }else{
	    	this.LiveGetMode = LiveGetMode;
	  }
 	}

	public String getLiveGetMode()
	{
	  return LiveGetMode;
	}

	public void setHAddFeeFlag(String  HAddFeeFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(HAddFeeFlag)))){
	    	return;
  	   }else{
	    	this.HAddFeeFlag = HAddFeeFlag;
	  }
 	}

	public String getHAddFeeFlag()
	{
	  return HAddFeeFlag;
	}

	public void setVPU( Double VPU )
	{
	  this.VPU = VPU;
 	}

	public Double getVPU()
	{
	  return VPU;
	}

	public void setGetYear( Double GetYear )
	{
	  this.GetYear = GetYear;
 	}

	public Double getGetYear()
	{
	  return GetYear;
	}

	public void setGetIntv( Double GetIntv )
	{
	  this.GetIntv = GetIntv;
 	}

	public Double getGetIntv()
	{
	  return GetIntv;
	}

	public void setEndDate(Date  EndDate )
	{
	  this.EndDate = EndDate;
 	}

	public Date getEndDate()
	{
	  return EndDate;
	}

	public void setGetYearFlag(String  GetYearFlag )
	{
	  this.GetYearFlag = GetYearFlag;
 	}

	public String getGetYearFlag()
	{
	  return GetYearFlag;
	}

	public void setGetRate( Double GetRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(GetRate)))){
	  	  return;
	    }else{
	    	this.GetRate = GetRate;
	   }
 	}

	public Double getGetRate()
	{
	  return GetRate;
	}

	public void setAddRate( Double AddRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(AddRate)))){
	  	  return;
	    }else{
	    	this.AddRate = AddRate;
	   }
 	}

	public Double getAddRate()
	{
	  return AddRate;
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

	public void setDerateOrFreeFlag(String  DerateOrFreeFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(DerateOrFreeFlag)))){
	    	return;
  	   }else{
	    	this.DerateOrFreeFlag = DerateOrFreeFlag;
	  }
 	}

	public String getDerateOrFreeFlag()
	{
	  return DerateOrFreeFlag;
	}

	public void setTotalAmnt( Double TotalAmnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(TotalAmnt)))){
	  	  return;
	    }else{
	    	this.TotalAmnt = TotalAmnt;
	   }
 	}

	public Double getTotalAmnt()
	{
	  return TotalAmnt;
	}

	public void setTotalMult( Double TotalMult )
	{
	  this.TotalMult = TotalMult;
 	}

	public Double getTotalMult()
	{
	  return TotalMult;
	}

	public void setDutyCode(String  DutyCode )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(DutyCode)))){
	    	return;
  	   }else{
	    	this.DutyCode = DutyCode;
	  }
 	}

	public String getDutyCode()
	{
	  return DutyCode;
	}

	public void setGetDutyKind(String  GetDutyKind )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(GetDutyKind)))){
	    	return;
  	   }else{
	    	this.GetDutyKind = GetDutyKind;
	  }
 	}

	public String getGetDutyKind()
	{
	  return GetDutyKind;
	}

	public void setGetLimit( Double GetLimit )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(GetLimit)))){
	  	  return;
	    }else{
	    	this.GetLimit = GetLimit;
	   }
 	}

	public Double getGetLimit()
	{
	  return GetLimit;
	}

	public void setPeakLine( Double PeakLine )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(PeakLine)))){
	  	  return;
	    }else{
	    	this.PeakLine = PeakLine;
	   }
 	}

	public Double getPeakLine()
	{
	  return PeakLine;
	}

	public void setPolTypeFlag(String  PolTypeFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(PolTypeFlag)))){
	    	return;
  	   }else{
	    	this.PolTypeFlag = PolTypeFlag;
	  }
 	}

	public String getPolTypeFlag()
	{
	  return PolTypeFlag;
	}

	public void setStandbyFlag1(String  StandbyFlag1 )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(StandbyFlag1)))){
	    	return;
  	   }else{
	    	this.StandbyFlag1 = StandbyFlag1;
	  }
 	}

	public String getStandbyFlag1()
	{
	  return StandbyFlag1;
	}

	public void setStandbyFlag2(String  StandbyFlag2 )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(StandbyFlag2)))){
	    	return;
  	   }else{
	    	this.StandbyFlag2 = StandbyFlag2;
	  }
 	}

	public String getStandbyFlag2()
	{
	  return StandbyFlag2;
	}

	public void setStandbyFlag3(String  StandbyFlag3 )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(StandbyFlag3)))){
	    	return;
  	   }else{
	    	this.StandbyFlag3 = StandbyFlag3;
	  }
 	}

	public String getStandbyFlag3()
	{
	  return StandbyFlag3;
	}

	public void setInsuredPeoples( Double InsuredPeoples )
	{
	   if(!(new BOMPreCheck().CheckIngeter(String.valueOf(InsuredPeoples)))){
	  	  return;
	    }else{
	    	this.InsuredPeoples = InsuredPeoples;
	   }
 	}

	public Double getInsuredPeoples()
	{
	  return InsuredPeoples;
	}

	public void setOldPolNo(String  OldPolNo )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OldPolNo)))){
	    	return;
  	   }else{
	    	this.OldPolNo = OldPolNo;
	  }
 	}

	public String getOldPolNo()
	{
	  return OldPolNo;
	}

	public void setStartDate(Date  StartDate )
	{
	  this.StartDate = StartDate;
 	}

	public Date getStartDate()
	{
	  return StartDate;
	}

	public void setPolState(String  PolState )
	{
	  this.PolState = PolState;
 	}

	public String getPolState()
	{
	  return PolState;
	}

	public void setClaimToXBFlag(String  ClaimToXBFlag )
	{
	  this.ClaimToXBFlag = ClaimToXBFlag;
 	}

	public String getClaimToXBFlag()
	{
	  return ClaimToXBFlag;
	}

	public void setBonusMoney( Double BonusMoney )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(BonusMoney)))){
	  	  return;
	    }else{
	    	this.BonusMoney = BonusMoney;
	   }
 	}

	public Double getBonusMoney()
	{
	  return BonusMoney;
	}

	public void setGetStartDate(Date  GetStartDate )
	{
	  this.GetStartDate = GetStartDate;
 	}

	public Date getGetStartDate()
	{
	  return GetStartDate;
	}

	public void setGetStartYear( Double GetStartYear )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(GetStartYear)))){
	  	  return;
	    }else{
	    	this.GetStartYear = GetStartYear;
	   }
 	}

	public Double getGetStartYear()
	{
	  return GetStartYear;
	}

	public void setPolNo(String  PolNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(PolNo)))){
	    	return;
  	   }else{
	    	this.PolNo = PolNo;
	  }
 	}

	public String getPolNo()
	{
	  return PolNo;
	}

	public void setMainPolNo(String  MainPolNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(MainPolNo)))){
	    	return;
  	   }else{
	    	this.MainPolNo = MainPolNo;
	  }
 	}

	public String getMainPolNo()
	{
	  return MainPolNo;
	}

	public void setRiskCode(String  RiskCode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RiskCode)))){
	    	return;
  	   }else{
	    	this.RiskCode = RiskCode;
	  }
 	}

	public String getRiskCode()
	{
	  return RiskCode;
	}

	public void setAmnt( Double Amnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(Amnt)))){
	  	  return;
	    }else{
	    	this.Amnt = Amnt;
	   }
 	}

	public Double getAmnt()
	{
	  return Amnt;
	}

	public void setPrem( Double Prem )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(Prem)))){
	  	  return;
	    }else{
	    	this.Prem = Prem;
	   }
 	}

	public Double getPrem()
	{
	  return Prem;
	}

	public void setMult( Double Mult )
	{
	   if(!(new BOMPreCheck().CheckIngeter(String.valueOf(Mult)))){
	  	  return;
	    }else{
	    	this.Mult = Mult;
	   }
 	}

	public Double getMult()
	{
	  return Mult;
	}

	public void setFloatRate( Double FloatRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(FloatRate)))){
	  	  return;
	    }else{
	    	this.FloatRate = FloatRate;
	   }
 	}

	public Double getFloatRate()
	{
	  return FloatRate;
	}

	public void setCValiDate(Date  CValiDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(CValiDate)))){
	    	return;
  	   }else{
	    	this.CValiDate = CValiDate;
	  }
 	}

	public Date getCValiDate()
	{
	  return CValiDate;
	}

	public void setInsuYear( Double InsuYear )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(InsuYear)))){
	  	  return;
	    }else{
	    	this.InsuYear = InsuYear;
	   }
 	}

	public Double getInsuYear()
	{
	  return InsuYear;
	}

	public void setInsuYearFlag(String  InsuYearFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(InsuYearFlag)))){
	    	return;
  	   }else{
	    	this.InsuYearFlag = InsuYearFlag;
	  }
 	}

	public String getInsuYearFlag()
	{
	  return InsuYearFlag;
	}

	public void setKindCode(String  KindCode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(KindCode)))){
	    	return;
  	   }else{
	    	this.KindCode = KindCode;
	  }
 	}

	public String getKindCode()
	{
	  return KindCode;
	}

	public void setUWFlag(String  UWFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(UWFlag)))){
	    	return;
  	   }else{
	    	this.UWFlag = UWFlag;
	  }
 	}

	public String getUWFlag()
	{
	  return UWFlag;
	}

	public void setBonusGetMode(String  BonusGetMode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BonusGetMode)))){
	    	return;
  	   }else{
	    	this.BonusGetMode = BonusGetMode;
	  }
 	}

	public String getBonusGetMode()
	{
	  return BonusGetMode;
	}

	public void setBonusYear( Double BonusYear )
	{
	  this.BonusYear = BonusYear;
 	}

	public Double getBonusYear()
	{
	  return BonusYear;
	}

	public void setPayEndYearFlag(String  PayEndYearFlag )
	{
	  this.PayEndYearFlag = PayEndYearFlag;
 	}

	public String getPayEndYearFlag()
	{
	  return PayEndYearFlag;
	}

	public void setPayEndYear( Double PayEndYear )
	{
	  this.PayEndYear = PayEndYear;
 	}

	public Double getPayEndYear()
	{
	  return PayEndYear;
	}

	public void setAppFlag(String  AppFlag )
	{
	  this.AppFlag = AppFlag;
 	}

	public String getAppFlag()
	{
	  return AppFlag;
	}

	public void setInvestRate( Double InvestRate )
	{
	  this.InvestRate = InvestRate;
 	}

	public Double getInvestRate()
	{
	  return InvestRate;
	}

	public void setRiskAmnt( Double RiskAmnt )
	{
	  this.RiskAmnt = RiskAmnt;
 	}

	public Double getRiskAmnt()
	{
	  return RiskAmnt;
	}

	public void setSumThisPrem( Double SumThisPrem )
	{
	  this.SumThisPrem = SumThisPrem;
 	}

	public Double getSumThisPrem()
	{
	  return SumThisPrem;
	}

	public void setPayYears( Double PayYears )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(PayYears)))){
	  	  return;
	    }else{
	    	this.PayYears = PayYears;
	   }
 	}

	public Double getPayYears()
	{
	  return PayYears;
	}

	public void setCurrency(String  Currency )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(Currency)))){
	    	return;
  	   }else{
	    	this.Currency = Currency;
	  }
 	}

	public String getCurrency()
	{
	  return Currency;
	}

	public void setModFactor( Double ModFactor )
	{
	  this.ModFactor = ModFactor;
 	}

	public Double getModFactor()
	{
	  return ModFactor;
	}

	public void setGetTimes( Double GetTimes )
	{
	  this.GetTimes = GetTimes;
 	}

	public Double getGetTimes()
	{
	  return GetTimes;
	}

	public void setPolClaimCount( Double PolClaimCount )
	{
	  this.PolClaimCount = PolClaimCount;
 	}

	public Double getPolClaimCount()
	{
	  return PolClaimCount;
	}

	public void setMaturityBenefit( Double MaturityBenefit )
	{
	  this.MaturityBenefit = MaturityBenefit;
 	}

	public Double getMaturityBenefit()
	{
	  return MaturityBenefit;
	}

	public void setPolYear( Double PolYear )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(PolYear)))){
	  	  return;
	    }else{
	    	this.PolYear = PolYear;
	   }
 	}

	public Double getPolYear()
	{
	  return PolYear;
	}

	public void setCashValue( Double CashValue )
	{
	  this.CashValue = CashValue;
 	}

	public Double getCashValue()
	{
	  return CashValue;
	}

	public void setRBRate( Double RBRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(RBRate)))){
	  	  return;
	    }else{
	    	this.RBRate = RBRate;
	   }
 	}

	public Double getRBRate()
	{
	  return RBRate;
	}

	public void setTBRate( Double TBRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(TBRate)))){
	  	  return;
	    }else{
	    	this.TBRate = TBRate;
	   }
 	}

	public Double getTBRate()
	{
	  return TBRate;
	}

	public void setPremiumDeductedAnnuity( Double PremiumDeductedAnnuity )
	{
	  this.PremiumDeductedAnnuity = PremiumDeductedAnnuity;
 	}

	public Double getPremiumDeductedAnnuity()
	{
	  return PremiumDeductedAnnuity;
	}

	public void setAccountValue( Double AccountValue )
	{
	  this.AccountValue = AccountValue;
 	}

	public Double getAccountValue()
	{
	  return AccountValue;
	}

	public void setUnPaidBonousRate( Double UnPaidBonousRate )
	{
	  this.UnPaidBonousRate = UnPaidBonousRate;
 	}

	public Double getUnPaidBonousRate()
	{
	  return UnPaidBonousRate;
	}

	public void setEVt1S( Double EVt1S )
	{
	  this.EVt1S = EVt1S;
 	}

	public Double getEVt1S()
	{
	  return EVt1S;
	}

	public void setThroughDay2( Double ThroughDay2 )
	{
	  this.ThroughDay2 = ThroughDay2;
 	}

	public Double getThroughDay2()
	{
	  return ThroughDay2;
	}

	public void setThroughDay1( Double ThroughDay1 )
	{
	  this.ThroughDay1 = ThroughDay1;
 	}

	public Double getThroughDay1()
	{
	  return ThroughDay1;
	}

	public void setRBt1( Double RBt1 )
	{
	  this.RBt1 = RBt1;
 	}

	public Double getRBt1()
	{
	  return RBt1;
	}

	public void setAddPrem( Double AddPrem )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(AddPrem)))){
	  	  return;
	    }else{
	    	this.AddPrem = AddPrem;
	   }
 	}

	public Double getAddPrem()
	{
	  return AddPrem;
	}

	public void setLaunchDate(Date  LaunchDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(LaunchDate)))){
	    	return;
  	   }else{
	    	this.LaunchDate = LaunchDate;
	  }
 	}

	public Date getLaunchDate()
	{
	  return LaunchDate;
	}

	public void setTargetPrem( Double TargetPrem )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(TargetPrem)))){
	  	  return;
	    }else{
	    	this.TargetPrem = TargetPrem;
	   }
 	}

	public Double getTargetPrem()
	{
	  return TargetPrem;
	}

	public void setAverageAccountValue60Mon( Double AverageAccountValue60Mon )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AverageAccountValue60Mon)))){
	  	  return;
	    }else{
	    	this.AverageAccountValue60Mon = AverageAccountValue60Mon;
	   }
 	}

	public Double getAverageAccountValue60Mon()
	{
	  return AverageAccountValue60Mon;
	}

	public void setPayfrequency(String  Payfrequency )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(Payfrequency)))){
	    	return;
  	   }else{
	    	this.Payfrequency = Payfrequency;
	  }
 	}

	public String getPayfrequency()
	{
	  return Payfrequency;
	}

	public void setMRiskisUniversalOrLink(String  MRiskisUniversalOrLink )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(MRiskisUniversalOrLink)))){
	    	return;
  	   }else{
	    	this.MRiskisUniversalOrLink = MRiskisUniversalOrLink;
	  }
 	}

	public String getMRiskisUniversalOrLink()
	{
	  return MRiskisUniversalOrLink;
	}

	public void setCoveragefeeduringtheUnit(String  CoveragefeeduringtheUnit )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(CoveragefeeduringtheUnit)))){
	    	return;
  	   }else{
	    	this.CoveragefeeduringtheUnit = CoveragefeeduringtheUnit;
	  }
 	}

	public String getCoveragefeeduringtheUnit()
	{
	  return CoveragefeeduringtheUnit;
	}

	public void setAccumulationPeriod(String  AccumulationPeriod )
	{
	  this.AccumulationPeriod = AccumulationPeriod;
 	}

	public String getAccumulationPeriod()
	{
	  return AccumulationPeriod;
	}

	public void setFirst20PremiumOfAnn( Double First20PremiumOfAnn )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(First20PremiumOfAnn)))){
	  	  return;
	    }else{
	    	this.First20PremiumOfAnn = First20PremiumOfAnn;
	   }
 	}

	public Double getFirst20PremiumOfAnn()
	{
	  return First20PremiumOfAnn;
	}

	public void setForOrProposalDiedMomey( Double ForOrProposalDiedMomey )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ForOrProposalDiedMomey)))){
	  	  return;
	    }else{
	    	this.ForOrProposalDiedMomey = ForOrProposalDiedMomey;
	   }
 	}

	public Double getForOrProposalDiedMomey()
	{
	  return ForOrProposalDiedMomey;
	}

	public void setUnPaidBonous( Double UnPaidBonous )
	{
	  this.UnPaidBonous = UnPaidBonous;
 	}

	public Double getUnPaidBonous()
	{
	  return UnPaidBonous;
	}

	public void setPayToDate(Date  PayToDate )
	{
	  this.PayToDate = PayToDate;
 	}

	public Date getPayToDate()
	{
	  return PayToDate;
	}

	public void setCVt( Double CVt )
	{
	  this.CVt = CVt;
 	}

	public Double getCVt()
	{
	  return CVt;
	}

	public void setCVt1S( Double CVt1S )
	{
	  this.CVt1S = CVt1S;
 	}

	public Double getCVt1S()
	{
	  return CVt1S;
	}

	public void setThroughDay( Double ThroughDay )
	{
	  this.ThroughDay = ThroughDay;
 	}

	public Double getThroughDay()
	{
	  return ThroughDay;
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

	if("AutoPayFlag".equals(FCode))
		{
		    setAutoPayFlag(FValue);
		}

	if("AccValue".equals(FCode))
		{
			setAccValue(Double.valueOf(FValue));
		}

	if("StandPrem".equals(FCode))
		{
			setStandPrem(Double.valueOf(FValue));
		}

	if("PayEndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setPayEndDate(d);
		}

	if("SumPrem".equals(FCode))
		{
			setSumPrem(Double.valueOf(FValue));
		}

	if("Years".equals(FCode))
		{
			setYears(Double.valueOf(FValue));
		}

	if("GetBalance".equals(FCode))
		{
			setGetBalance(Double.valueOf(FValue));
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("BonusManType".equals(FCode))
		{
		    setBonusManType(FValue);
		}

	if("StopFlag".equals(FCode))
		{
		    setStopFlag(FValue);
		}

	if("OAddFeeFlag".equals(FCode))
		{
		    setOAddFeeFlag(FValue);
		}

	if("LiveGetMode".equals(FCode))
		{
		    setLiveGetMode(FValue);
		}

	if("HAddFeeFlag".equals(FCode))
		{
		    setHAddFeeFlag(FValue);
		}

	if("VPU".equals(FCode))
		{
			setVPU(Double.valueOf(FValue));
		}

	if("GetYear".equals(FCode))
		{
			setGetYear(Double.valueOf(FValue));
		}

	if("GetIntv".equals(FCode))
		{
			setGetIntv(Double.valueOf(FValue));
		}

	if("EndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEndDate(d);
		}

	if("GetYearFlag".equals(FCode))
		{
		    setGetYearFlag(FValue);
		}

	if("GetRate".equals(FCode))
		{
			setGetRate(Double.valueOf(FValue));
		}

	if("AddRate".equals(FCode))
		{
			setAddRate(Double.valueOf(FValue));
		}

	if("GetDutyCode".equals(FCode))
		{
		    setGetDutyCode(FValue);
		}

	if("DerateOrFreeFlag".equals(FCode))
		{
		    setDerateOrFreeFlag(FValue);
		}

	if("TotalAmnt".equals(FCode))
		{
			setTotalAmnt(Double.valueOf(FValue));
		}

	if("TotalMult".equals(FCode))
		{
			setTotalMult(Double.valueOf(FValue));
		}

	if("DutyCode".equals(FCode))
		{
		    setDutyCode(FValue);
		}

	if("GetDutyKind".equals(FCode))
		{
		    setGetDutyKind(FValue);
		}

	if("GetLimit".equals(FCode))
		{
			setGetLimit(Double.valueOf(FValue));
		}

	if("PeakLine".equals(FCode))
		{
			setPeakLine(Double.valueOf(FValue));
		}

	if("PolTypeFlag".equals(FCode))
		{
		    setPolTypeFlag(FValue);
		}

	if("StandbyFlag1".equals(FCode))
		{
		    setStandbyFlag1(FValue);
		}

	if("StandbyFlag2".equals(FCode))
		{
		    setStandbyFlag2(FValue);
		}

	if("StandbyFlag3".equals(FCode))
		{
		    setStandbyFlag3(FValue);
		}

	if("InsuredPeoples".equals(FCode))
		{
			setInsuredPeoples(Double.valueOf(FValue));
		}

	if("OldPolNo".equals(FCode))
		{
		    setOldPolNo(FValue);
		}

	if("StartDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStartDate(d);
		}

	if("PolState".equals(FCode))
		{
		    setPolState(FValue);
		}

	if("ClaimToXBFlag".equals(FCode))
		{
		    setClaimToXBFlag(FValue);
		}

	if("BonusMoney".equals(FCode))
		{
			setBonusMoney(Double.valueOf(FValue));
		}

	if("GetStartDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setGetStartDate(d);
		}

	if("GetStartYear".equals(FCode))
		{
			setGetStartYear(Double.valueOf(FValue));
		}

	if("PolNo".equals(FCode))
		{
		    setPolNo(FValue);
		}

	if("MainPolNo".equals(FCode))
		{
		    setMainPolNo(FValue);
		}

	if("RiskCode".equals(FCode))
		{
		    setRiskCode(FValue);
		}

	if("Amnt".equals(FCode))
		{
			setAmnt(Double.valueOf(FValue));
		}

	if("Prem".equals(FCode))
		{
			setPrem(Double.valueOf(FValue));
		}

	if("Mult".equals(FCode))
		{
			setMult(Double.valueOf(FValue));
		}

	if("FloatRate".equals(FCode))
		{
			setFloatRate(Double.valueOf(FValue));
		}

	if("CValiDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setCValiDate(d);
		}

	if("InsuYear".equals(FCode))
		{
			setInsuYear(Double.valueOf(FValue));
		}

	if("InsuYearFlag".equals(FCode))
		{
		    setInsuYearFlag(FValue);
		}

	if("KindCode".equals(FCode))
		{
		    setKindCode(FValue);
		}

	if("UWFlag".equals(FCode))
		{
		    setUWFlag(FValue);
		}

	if("BonusGetMode".equals(FCode))
		{
		    setBonusGetMode(FValue);
		}

	if("BonusYear".equals(FCode))
		{
			setBonusYear(Double.valueOf(FValue));
		}

	if("PayEndYearFlag".equals(FCode))
		{
		    setPayEndYearFlag(FValue);
		}

	if("PayEndYear".equals(FCode))
		{
			setPayEndYear(Double.valueOf(FValue));
		}

	if("AppFlag".equals(FCode))
		{
		    setAppFlag(FValue);
		}

	if("InvestRate".equals(FCode))
		{
			setInvestRate(Double.valueOf(FValue));
		}

	if("RiskAmnt".equals(FCode))
		{
			setRiskAmnt(Double.valueOf(FValue));
		}

	if("SumThisPrem".equals(FCode))
		{
			setSumThisPrem(Double.valueOf(FValue));
		}

	if("PayYears".equals(FCode))
		{
			setPayYears(Double.valueOf(FValue));
		}

	if("Currency".equals(FCode))
		{
		    setCurrency(FValue);
		}

	if("ModFactor".equals(FCode))
		{
			setModFactor(Double.valueOf(FValue));
		}

	if("GetTimes".equals(FCode))
		{
			setGetTimes(Double.valueOf(FValue));
		}

	if("PolClaimCount".equals(FCode))
		{
			setPolClaimCount(Double.valueOf(FValue));
		}

	if("MaturityBenefit".equals(FCode))
		{
			setMaturityBenefit(Double.valueOf(FValue));
		}

	if("PolYear".equals(FCode))
		{
			setPolYear(Double.valueOf(FValue));
		}

	if("CashValue".equals(FCode))
		{
			setCashValue(Double.valueOf(FValue));
		}

	if("RBRate".equals(FCode))
		{
			setRBRate(Double.valueOf(FValue));
		}

	if("TBRate".equals(FCode))
		{
			setTBRate(Double.valueOf(FValue));
		}

	if("PremiumDeductedAnnuity".equals(FCode))
		{
			setPremiumDeductedAnnuity(Double.valueOf(FValue));
		}

	if("AccountValue".equals(FCode))
		{
			setAccountValue(Double.valueOf(FValue));
		}

	if("UnPaidBonousRate".equals(FCode))
		{
			setUnPaidBonousRate(Double.valueOf(FValue));
		}

	if("EVt1S".equals(FCode))
		{
			setEVt1S(Double.valueOf(FValue));
		}

	if("ThroughDay2".equals(FCode))
		{
			setThroughDay2(Double.valueOf(FValue));
		}

	if("ThroughDay1".equals(FCode))
		{
			setThroughDay1(Double.valueOf(FValue));
		}

	if("RBt1".equals(FCode))
		{
			setRBt1(Double.valueOf(FValue));
		}

	if("AddPrem".equals(FCode))
		{
			setAddPrem(Double.valueOf(FValue));
		}

	if("LaunchDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setLaunchDate(d);
		}

	if("TargetPrem".equals(FCode))
		{
			setTargetPrem(Double.valueOf(FValue));
		}

	if("AverageAccountValue60Mon".equals(FCode))
		{
			setAverageAccountValue60Mon(Double.valueOf(FValue));
		}

	if("Payfrequency".equals(FCode))
		{
		    setPayfrequency(FValue);
		}

	if("MRiskisUniversalOrLink".equals(FCode))
		{
		    setMRiskisUniversalOrLink(FValue);
		}

	if("CoveragefeeduringtheUnit".equals(FCode))
		{
		    setCoveragefeeduringtheUnit(FValue);
		}

	if("AccumulationPeriod".equals(FCode))
		{
		    setAccumulationPeriod(FValue);
		}

	if("First20PremiumOfAnn".equals(FCode))
		{
			setFirst20PremiumOfAnn(Double.valueOf(FValue));
		}

	if("ForOrProposalDiedMomey".equals(FCode))
		{
			setForOrProposalDiedMomey(Double.valueOf(FValue));
		}

	if("UnPaidBonous".equals(FCode))
		{
			setUnPaidBonous(Double.valueOf(FValue));
		}

	if("PayToDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setPayToDate(d);
		}

	if("CVt".equals(FCode))
		{
			setCVt(Double.valueOf(FValue));
		}

	if("CVt1S".equals(FCode))
		{
			setCVt1S(Double.valueOf(FValue));
		}

	if("ThroughDay".equals(FCode))
		{
			setThroughDay(Double.valueOf(FValue));
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
	  if (FCode.equalsIgnoreCase("AutoPayFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAutoPayFlag()));
	  }
	  if (FCode.equalsIgnoreCase("AccValue"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccValue()));
	  }
	  if (FCode.equalsIgnoreCase("StandPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStandPrem()));
	  }
	  if (FCode.equalsIgnoreCase("PayEndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getPayEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("SumPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumPrem()));
	  }
	  if (FCode.equalsIgnoreCase("Years"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getYears()));
	  }
	  if (FCode.equalsIgnoreCase("GetBalance"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetBalance()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("BonusManType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusManType()));
	  }
	  if (FCode.equalsIgnoreCase("StopFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStopFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OAddFeeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOAddFeeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("LiveGetMode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLiveGetMode()));
	  }
	  if (FCode.equalsIgnoreCase("HAddFeeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHAddFeeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("VPU"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getVPU()));
	  }
	  if (FCode.equalsIgnoreCase("GetYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetYear()));
	  }
	  if (FCode.equalsIgnoreCase("GetIntv"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetIntv()));
	  }
	  if (FCode.equalsIgnoreCase("EndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("GetYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("GetRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetRate()));
	  }
	  if (FCode.equalsIgnoreCase("AddRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddRate()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutyCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutyCode()));
	  }
	  if (FCode.equalsIgnoreCase("DerateOrFreeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDerateOrFreeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("TotalAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTotalAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("TotalMult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTotalMult()));
	  }
	  if (FCode.equalsIgnoreCase("DutyCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDutyCode()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutyKind"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutyKind()));
	  }
	  if (FCode.equalsIgnoreCase("GetLimit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetLimit()));
	  }
	  if (FCode.equalsIgnoreCase("PeakLine"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPeakLine()));
	  }
	  if (FCode.equalsIgnoreCase("PolTypeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolTypeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("StandbyFlag1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStandbyFlag1()));
	  }
	  if (FCode.equalsIgnoreCase("StandbyFlag2"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStandbyFlag2()));
	  }
	  if (FCode.equalsIgnoreCase("StandbyFlag3"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStandbyFlag3()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredPeoples"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredPeoples()));
	  }
	  if (FCode.equalsIgnoreCase("OldPolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOldPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("StartDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStartDate())));
	  }
	  if (FCode.equalsIgnoreCase("PolState"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolState()));
	  }
	  if (FCode.equalsIgnoreCase("ClaimToXBFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getClaimToXBFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BonusMoney"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusMoney()));
	  }
	  if (FCode.equalsIgnoreCase("GetStartDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getGetStartDate())));
	  }
	  if (FCode.equalsIgnoreCase("GetStartYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetStartYear()));
	  }
	  if (FCode.equalsIgnoreCase("PolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("MainPolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMainPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("RiskCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRiskCode()));
	  }
	  if (FCode.equalsIgnoreCase("Amnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("Prem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPrem()));
	  }
	  if (FCode.equalsIgnoreCase("Mult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMult()));
	  }
	  if (FCode.equalsIgnoreCase("FloatRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFloatRate()));
	  }
	  if (FCode.equalsIgnoreCase("CValiDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getCValiDate())));
	  }
	  if (FCode.equalsIgnoreCase("InsuYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuYear()));
	  }
	  if (FCode.equalsIgnoreCase("InsuYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("KindCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getKindCode()));
	  }
	  if (FCode.equalsIgnoreCase("UWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BonusGetMode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusGetMode()));
	  }
	  if (FCode.equalsIgnoreCase("BonusYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusYear()));
	  }
	  if (FCode.equalsIgnoreCase("PayEndYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayEndYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PayEndYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayEndYear()));
	  }
	  if (FCode.equalsIgnoreCase("AppFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InvestRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInvestRate()));
	  }
	  if (FCode.equalsIgnoreCase("RiskAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRiskAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumThisPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumThisPrem()));
	  }
	  if (FCode.equalsIgnoreCase("PayYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayYears()));
	  }
	  if (FCode.equalsIgnoreCase("Currency"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrency()));
	  }
	  if (FCode.equalsIgnoreCase("ModFactor"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getModFactor()));
	  }
	  if (FCode.equalsIgnoreCase("GetTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetTimes()));
	  }
	  if (FCode.equalsIgnoreCase("PolClaimCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolClaimCount()));
	  }
	  if (FCode.equalsIgnoreCase("MaturityBenefit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMaturityBenefit()));
	  }
	  if (FCode.equalsIgnoreCase("PolYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolYear()));
	  }
	  if (FCode.equalsIgnoreCase("CashValue"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCashValue()));
	  }
	  if (FCode.equalsIgnoreCase("RBRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRBRate()));
	  }
	  if (FCode.equalsIgnoreCase("TBRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTBRate()));
	  }
	  if (FCode.equalsIgnoreCase("PremiumDeductedAnnuity"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPremiumDeductedAnnuity()));
	  }
	  if (FCode.equalsIgnoreCase("AccountValue"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccountValue()));
	  }
	  if (FCode.equalsIgnoreCase("UnPaidBonousRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUnPaidBonousRate()));
	  }
	  if (FCode.equalsIgnoreCase("EVt1S"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEVt1S()));
	  }
	  if (FCode.equalsIgnoreCase("ThroughDay2"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThroughDay2()));
	  }
	  if (FCode.equalsIgnoreCase("ThroughDay1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThroughDay1()));
	  }
	  if (FCode.equalsIgnoreCase("RBt1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRBt1()));
	  }
	  if (FCode.equalsIgnoreCase("AddPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddPrem()));
	  }
	  if (FCode.equalsIgnoreCase("LaunchDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getLaunchDate())));
	  }
	  if (FCode.equalsIgnoreCase("TargetPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTargetPrem()));
	  }
	  if (FCode.equalsIgnoreCase("AverageAccountValue60Mon"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAverageAccountValue60Mon()));
	  }
	  if (FCode.equalsIgnoreCase("Payfrequency"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayfrequency()));
	  }
	  if (FCode.equalsIgnoreCase("MRiskisUniversalOrLink"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMRiskisUniversalOrLink()));
	  }
	  if (FCode.equalsIgnoreCase("CoveragefeeduringtheUnit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCoveragefeeduringtheUnit()));
	  }
	  if (FCode.equalsIgnoreCase("AccumulationPeriod"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccumulationPeriod()));
	  }
	  if (FCode.equalsIgnoreCase("First20PremiumOfAnn"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFirst20PremiumOfAnn()));
	  }
	  if (FCode.equalsIgnoreCase("ForOrProposalDiedMomey"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getForOrProposalDiedMomey()));
	  }
	  if (FCode.equalsIgnoreCase("UnPaidBonous"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUnPaidBonous()));
	  }
	  if (FCode.equalsIgnoreCase("PayToDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getPayToDate())));
	  }
	  if (FCode.equalsIgnoreCase("CVt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCVt()));
	  }
	  if (FCode.equalsIgnoreCase("CVt1S"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCVt1S()));
	  }
	  if (FCode.equalsIgnoreCase("ThroughDay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThroughDay()));
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
