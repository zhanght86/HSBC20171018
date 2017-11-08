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
 * <p>ClassName: BOMBqPol </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-03-26
 */

public class BOMBqPol extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";

	private BOMInsured tBOMInsured;

	/** 扣除年末生存领取 */
	private Double CVt1GetDraw;

	/** 份数 */
	private Double Mult;

	/** 备用字段1 */
	private String StandByFlag1;

	/** 交费终止日期 */
	private Date SignDate;

	/** 给付间隔 */
	private Double GetIntv;

	/** 起付线 */
	private Double GetLimit;

	/** 赔付比例 */
	private Double GetRate;

	/** 单位保额 */
	private Double VPU;

	/** 红利金领取方式 */
	private String BonusGetMode;

	/** 红利领取标记 */
	private String BonusGetModeFlag;

	/** 借款金额 */
	private Double LoanMoney;

	/** 递增率 */
	private Double AddRate;

	/** RPUL投资帐户部分领取的手续费 */
	private Double RpulARFee;

	/** RPUL投资计划变更和帐户转换的手续费 */
	private Double RpulPATIFee;

	/** 终了红利率 */
	private Double TBRate;

	/** 第t个保单年末单位红利保额的现价 */
	private Double GVt;

	/** 第t+1个保单年末单位红利保额的现价 */
	private Double GVt1;

	/** 累计保费 */
	private Double SumPrem;

	/** 当期保费(不含加费) */
	private Double NextPrem;

	/** 缴费次数 */
	private Double PayTimes;

	/** 基本保额对应生存领取金额 */
	private Double AliveGet;

	/** 险种630对应生存领取金 */
	private Double liveGet;

	/** 累计红利保险金额 */
	private Double SumAmntBonus;

	/** k+1年度退前累计领金占有效保额比例 */
	private Double SumYearGetRate;

	/** k+1年度退前累计基保额对应生存金 */
	private Double SumYearGet;

	/** 生存领取金占有效保额的比例 */
	private Double AliveGetRate;

	/** 退保原因 */
	private String EdorReasonCode;

	/** 扣除手续费之前的帐户退保金 */
	private Double ZTMoneyByAcc;

	/** 该险种只能附加于1年期以上主险以下 */
	private String MainSubLRiskFlag;

	/** 批改算法 */
	private String EdorTypeCal;

	/** 险种未过月数 */
	private String AAYears;

	/** 交费终止日期 */
	private Date PayEndDate;

	/** 领取年龄年期 */
	private Double GetYear;

	/** 币种 */
	private String Currency;

	/** 主附险标记 */
	private String SubRiskFlag;

	/** 交费期间单位 */
	private String PayYearFlag;

	/** 交费期间 */
	private Double PayYears;

	/** 还款金额 */
	private Double TrayMoney;

	/** 计息开始时间 */
	private Date StartDate;

	/** 核保状态 */
	private String UWFlag;

	/** 责任终止日期 */
	private Date EndDate;

	/** 分红率 */
	private String BonusRate;

	/** 保险期间单位 */
	private String InsuYearFlag;

	/** 保险年龄年期 */
	private Double InsuYear;

	/** 险种生效日期 */
	private Date CValiDate;

	/** 变更后保费 */
	private Double Prem;

	/** 保额 */
	private Double Amnt;

	/** 主险保单号码 */
	private String MainPolNo;

	/** 保单失效标志 */
	private String PolValiFlag;

	/** 险种保单号码 */
	private String PolNo;

	/** 被保人号码 */
	private String InsuredNo;

	/** 领取年龄年期标志 */
	private String GetYearFlag;

	/** 险种编码 */
	private String RiskCode;

	/** 保费交至日 */
	private Date PayToDate;

	/** 险种分类 */
	private String RiskSort;

	/** 缴费终止年期或年龄标记 */
	private String PayEndYearFlag;

	/** 缴费终止年期或年龄 */
	private Double PayEndYear;

	/** 结算日期 */
	private Date BalaDate;

	/** 上次结算日期 */
	private Date LastBalaDate;

	/** 冲减保额 */
	private Double ReduceAmnt;

	/** t+1个保单年度末现金价值 */
	private Double CVt1;

	/** t个保单年度末现金价值 */
	private Double CVt;

	/** 年度末现价扣除上年末生存领取 */
	private Double CVtGetDraw;

	/** 险种已过月数 */
	private Double IntervalM;

	/** 浮动费率 */
	private Double FloatRate;

	/** 交费终止日期 */
	private Date LoanDate;

	/** 退保新旧版本时间阈 */
	private Date CTVersionDate;

	/** 责任编码 */
	private String DutyCode;

	/** 发生理赔标记 */
	private String PolClaimFlag;

	/** 险种短期费率 */
	private Double ShortRate;

	/** 保额 */
	private Double Get;



	// @Constructor
	public BOMBqPol()
	{  }

	public void setFatherBOM(BOMInsured mBOMInsured)
	{
		this.tBOMInsured = mBOMInsured;
	}
 
	public AbstractBOM getFatherBOM()
	{
		return tBOMInsured;
	}

	public void setCVt1GetDraw( Double CVt1GetDraw )
	{
	  this.CVt1GetDraw = CVt1GetDraw;
 	}

	public Double getCVt1GetDraw()
	{
	  return CVt1GetDraw;
	}

	public void setMult( Double Mult )
	{
	  this.Mult = Mult;
 	}

	public Double getMult()
	{
	  return Mult;
	}

	public void setStandByFlag1(String  StandByFlag1 )
	{
	  this.StandByFlag1 = StandByFlag1;
 	}

	public String getStandByFlag1()
	{
	  return StandByFlag1;
	}

	public void setSignDate(Date  SignDate )
	{
	  this.SignDate = SignDate;
 	}

	public Date getSignDate()
	{
	  return SignDate;
	}

	public void setGetIntv( Double GetIntv )
	{
	  this.GetIntv = GetIntv;
 	}

	public Double getGetIntv()
	{
	  return GetIntv;
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

	public void setGetRate( Double GetRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(GetRate))){
	  	  return;
	    }else{
	    	this.GetRate = GetRate;
	   }
 	}

	public Double getGetRate()
	{
	  return GetRate;
	}

	public void setVPU( Double VPU )
	{
	  this.VPU = VPU;
 	}

	public Double getVPU()
	{
	  return VPU;
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

	public void setBonusGetModeFlag(String  BonusGetModeFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BonusGetModeFlag)))){
	    	return;
  	   }else{
	    	this.BonusGetModeFlag = BonusGetModeFlag;
	  }
 	}

	public String getBonusGetModeFlag()
	{
	  return BonusGetModeFlag;
	}

	public void setLoanMoney( Double LoanMoney )
	{
	  this.LoanMoney = LoanMoney;
 	}

	public Double getLoanMoney()
	{
	  return LoanMoney;
	}

	public void setAddRate( Double AddRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(AddRate))){
	  	  return;
	    }else{
	    	this.AddRate = AddRate;
	   }
 	}

	public Double getAddRate()
	{
	  return AddRate;
	}

	public void setRpulARFee( Double RpulARFee )
	{
	   if(!(new BOMPreCheck().CheckPlus(RpulARFee))){
	  	  return;
	    }else{
	    	this.RpulARFee = RpulARFee;
	   }
 	}

	public Double getRpulARFee()
	{
	  return RpulARFee;
	}

	public void setRpulPATIFee( Double RpulPATIFee )
	{
	   if(!(new BOMPreCheck().CheckPlus(RpulPATIFee))){
	  	  return;
	    }else{
	    	this.RpulPATIFee = RpulPATIFee;
	   }
 	}

	public Double getRpulPATIFee()
	{
	  return RpulPATIFee;
	}

	public void setTBRate( Double TBRate )
	{
	  this.TBRate = TBRate;
 	}

	public Double getTBRate()
	{
	  return TBRate;
	}

	public void setGVt( Double GVt )
	{
	  this.GVt = GVt;
 	}

	public Double getGVt()
	{
	  return GVt;
	}

	public void setGVt1( Double GVt1 )
	{
	  this.GVt1 = GVt1;
 	}

	public Double getGVt1()
	{
	  return GVt1;
	}

	public void setSumPrem( Double SumPrem )
	{
	  this.SumPrem = SumPrem;
 	}

	public Double getSumPrem()
	{
	  return SumPrem;
	}

	public void setNextPrem( Double NextPrem )
	{
	  this.NextPrem = NextPrem;
 	}

	public Double getNextPrem()
	{
	  return NextPrem;
	}

	public void setPayTimes( Double PayTimes )
	{
	  this.PayTimes = PayTimes;
 	}

	public Double getPayTimes()
	{
	  return PayTimes;
	}

	public void setAliveGet( Double AliveGet )
	{
	  this.AliveGet = AliveGet;
 	}

	public Double getAliveGet()
	{
	  return AliveGet;
	}

	public void setliveGet( Double liveGet )
	{
	  this.liveGet = liveGet;
 	}

	public Double getliveGet()
	{
	  return liveGet;
	}

	public void setSumAmntBonus( Double SumAmntBonus )
	{
	  this.SumAmntBonus = SumAmntBonus;
 	}

	public Double getSumAmntBonus()
	{
	  return SumAmntBonus;
	}

	public void setSumYearGetRate( Double SumYearGetRate )
	{
	  this.SumYearGetRate = SumYearGetRate;
 	}

	public Double getSumYearGetRate()
	{
	  return SumYearGetRate;
	}

	public void setSumYearGet( Double SumYearGet )
	{
	  this.SumYearGet = SumYearGet;
 	}

	public Double getSumYearGet()
	{
	  return SumYearGet;
	}

	public void setAliveGetRate( Double AliveGetRate )
	{
	  this.AliveGetRate = AliveGetRate;
 	}

	public Double getAliveGetRate()
	{
	  return AliveGetRate;
	}

	public void setEdorReasonCode(String  EdorReasonCode )
	{
	  this.EdorReasonCode = EdorReasonCode;
 	}

	public String getEdorReasonCode()
	{
	  return EdorReasonCode;
	}

	public void setZTMoneyByAcc( Double ZTMoneyByAcc )
	{
	  this.ZTMoneyByAcc = ZTMoneyByAcc;
 	}

	public Double getZTMoneyByAcc()
	{
	  return ZTMoneyByAcc;
	}

	public void setMainSubLRiskFlag(String  MainSubLRiskFlag )
	{
	  this.MainSubLRiskFlag = MainSubLRiskFlag;
 	}

	public String getMainSubLRiskFlag()
	{
	  return MainSubLRiskFlag;
	}

	public void setEdorTypeCal(String  EdorTypeCal )
	{
	  this.EdorTypeCal = EdorTypeCal;
 	}

	public String getEdorTypeCal()
	{
	  return EdorTypeCal;
	}

	public void setAAYears(String  AAYears )
	{
	  this.AAYears = AAYears;
 	}

	public String getAAYears()
	{
	  return AAYears;
	}

	public void setPayEndDate(Date  PayEndDate )
	{
	  this.PayEndDate = PayEndDate;
 	}

	public Date getPayEndDate()
	{
	  return PayEndDate;
	}

	public void setGetYear( Double GetYear )
	{
	  this.GetYear = GetYear;
 	}

	public Double getGetYear()
	{
	  return GetYear;
	}

	public void setCurrency(String  Currency )
	{
	  this.Currency = Currency;
 	}

	public String getCurrency()
	{
	  return Currency;
	}

	public void setSubRiskFlag(String  SubRiskFlag )
	{
	  this.SubRiskFlag = SubRiskFlag;
 	}

	public String getSubRiskFlag()
	{
	  return SubRiskFlag;
	}

	public void setPayYearFlag(String  PayYearFlag )
	{
	  this.PayYearFlag = PayYearFlag;
 	}

	public String getPayYearFlag()
	{
	  return PayYearFlag;
	}

	public void setPayYears( Double PayYears )
	{
	  this.PayYears = PayYears;
 	}

	public Double getPayYears()
	{
	  return PayYears;
	}

	public void setTrayMoney( Double TrayMoney )
	{
	  this.TrayMoney = TrayMoney;
 	}

	public Double getTrayMoney()
	{
	  return TrayMoney;
	}

	public void setStartDate(Date  StartDate )
	{
	  this.StartDate = StartDate;
 	}

	public Date getStartDate()
	{
	  return StartDate;
	}

	public void setUWFlag(String  UWFlag )
	{
	  this.UWFlag = UWFlag;
 	}

	public String getUWFlag()
	{
	  return UWFlag;
	}

	public void setEndDate(Date  EndDate )
	{
	  this.EndDate = EndDate;
 	}

	public Date getEndDate()
	{
	  return EndDate;
	}

	public void setBonusRate(String  BonusRate )
	{
	  this.BonusRate = BonusRate;
 	}

	public String getBonusRate()
	{
	  return BonusRate;
	}

	public void setInsuYearFlag(String  InsuYearFlag )
	{
	  this.InsuYearFlag = InsuYearFlag;
 	}

	public String getInsuYearFlag()
	{
	  return InsuYearFlag;
	}

	public void setInsuYear( Double InsuYear )
	{
	  this.InsuYear = InsuYear;
 	}

	public Double getInsuYear()
	{
	  return InsuYear;
	}

	public void setCValiDate(Date  CValiDate )
	{
	  this.CValiDate = CValiDate;
 	}

	public Date getCValiDate()
	{
	  return CValiDate;
	}

	public void setPrem( Double Prem )
	{
	  this.Prem = Prem;
 	}

	public Double getPrem()
	{
	  return Prem;
	}

	public void setAmnt( Double Amnt )
	{
	  this.Amnt = Amnt;
 	}

	public Double getAmnt()
	{
	  return Amnt;
	}

	public void setMainPolNo(String  MainPolNo )
	{
	  this.MainPolNo = MainPolNo;
 	}

	public String getMainPolNo()
	{
	  return MainPolNo;
	}

	public void setPolValiFlag(String  PolValiFlag )
	{
	  this.PolValiFlag = PolValiFlag;
 	}

	public String getPolValiFlag()
	{
	  return PolValiFlag;
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

	public void setGetYearFlag(String  GetYearFlag )
	{
	  this.GetYearFlag = GetYearFlag;
 	}

	public String getGetYearFlag()
	{
	  return GetYearFlag;
	}

	public void setRiskCode(String  RiskCode )
	{
	  this.RiskCode = RiskCode;
 	}

	public String getRiskCode()
	{
	  return RiskCode;
	}

	public void setPayToDate(Date  PayToDate )
	{
	  this.PayToDate = PayToDate;
 	}

	public Date getPayToDate()
	{
	  return PayToDate;
	}

	public void setRiskSort(String  RiskSort )
	{
	  this.RiskSort = RiskSort;
 	}

	public String getRiskSort()
	{
	  return RiskSort;
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

	public void setBalaDate(Date  BalaDate )
	{
	  this.BalaDate = BalaDate;
 	}

	public Date getBalaDate()
	{
	  return BalaDate;
	}

	public void setLastBalaDate(Date  LastBalaDate )
	{
	  this.LastBalaDate = LastBalaDate;
 	}

	public Date getLastBalaDate()
	{
	  return LastBalaDate;
	}

	public void setReduceAmnt( Double ReduceAmnt )
	{
	  this.ReduceAmnt = ReduceAmnt;
 	}

	public Double getReduceAmnt()
	{
	  return ReduceAmnt;
	}

	public void setCVt1( Double CVt1 )
	{
	  this.CVt1 = CVt1;
 	}

	public Double getCVt1()
	{
	  return CVt1;
	}

	public void setCVt( Double CVt )
	{
	  this.CVt = CVt;
 	}

	public Double getCVt()
	{
	  return CVt;
	}

	public void setCVtGetDraw( Double CVtGetDraw )
	{
	  this.CVtGetDraw = CVtGetDraw;
 	}

	public Double getCVtGetDraw()
	{
	  return CVtGetDraw;
	}

	public void setIntervalM( Double IntervalM )
	{
	  this.IntervalM = IntervalM;
 	}

	public Double getIntervalM()
	{
	  return IntervalM;
	}

	public void setFloatRate( Double FloatRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(FloatRate))){
	  	  return;
	    }else{
	    	this.FloatRate = FloatRate;
	   }
 	}

	public Double getFloatRate()
	{
	  return FloatRate;
	}

	public void setLoanDate(Date  LoanDate )
	{
	  this.LoanDate = LoanDate;
 	}

	public Date getLoanDate()
	{
	  return LoanDate;
	}

	public void setCTVersionDate(Date  CTVersionDate )
	{
	  this.CTVersionDate = CTVersionDate;
 	}

	public Date getCTVersionDate()
	{
	  return CTVersionDate;
	}

	public void setDutyCode(String  DutyCode )
	{
	  this.DutyCode = DutyCode;
 	}

	public String getDutyCode()
	{
	  return DutyCode;
	}

	public void setPolClaimFlag(String  PolClaimFlag )
	{
	  this.PolClaimFlag = PolClaimFlag;
 	}

	public String getPolClaimFlag()
	{
	  return PolClaimFlag;
	}

	public void setShortRate( Double ShortRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(ShortRate))){
	  	  return;
	    }else{
	    	this.ShortRate = ShortRate;
	   }
 	}

	public Double getShortRate()
	{
	  return ShortRate;
	}

	public void setGet( Double Get )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(Get)))){
	  	  return;
	    }else{
	    	this.Get = Get;
	   }
 	}

	public Double getGet()
	{
	  return Get;
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

	if("CVt1GetDraw".equals(FCode))
		{
			setCVt1GetDraw(Double.valueOf(FValue));
		}

	if("Mult".equals(FCode))
		{
			setMult(Double.valueOf(FValue));
		}

	if("StandByFlag1".equals(FCode))
		{
		    setStandByFlag1(FValue);
		}

	if("SignDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setSignDate(d);
		}

	if("GetIntv".equals(FCode))
		{
			setGetIntv(Double.valueOf(FValue));
		}

	if("GetLimit".equals(FCode))
		{
			setGetLimit(Double.valueOf(FValue));
		}

	if("GetRate".equals(FCode))
		{
			setGetRate(Double.valueOf(FValue));
		}

	if("VPU".equals(FCode))
		{
			setVPU(Double.valueOf(FValue));
		}

	if("BonusGetMode".equals(FCode))
		{
		    setBonusGetMode(FValue);
		}

	if("BonusGetModeFlag".equals(FCode))
		{
		    setBonusGetModeFlag(FValue);
		}

	if("LoanMoney".equals(FCode))
		{
			setLoanMoney(Double.valueOf(FValue));
		}

	if("AddRate".equals(FCode))
		{
			setAddRate(Double.valueOf(FValue));
		}

	if("RpulARFee".equals(FCode))
		{
			setRpulARFee(Double.valueOf(FValue));
		}

	if("RpulPATIFee".equals(FCode))
		{
			setRpulPATIFee(Double.valueOf(FValue));
		}

	if("TBRate".equals(FCode))
		{
			setTBRate(Double.valueOf(FValue));
		}

	if("GVt".equals(FCode))
		{
			setGVt(Double.valueOf(FValue));
		}

	if("GVt1".equals(FCode))
		{
			setGVt1(Double.valueOf(FValue));
		}

	if("SumPrem".equals(FCode))
		{
			setSumPrem(Double.valueOf(FValue));
		}

	if("NextPrem".equals(FCode))
		{
			setNextPrem(Double.valueOf(FValue));
		}

	if("PayTimes".equals(FCode))
		{
			setPayTimes(Double.valueOf(FValue));
		}

	if("AliveGet".equals(FCode))
		{
			setAliveGet(Double.valueOf(FValue));
		}

	if("liveGet".equals(FCode))
		{
			setliveGet(Double.valueOf(FValue));
		}

	if("SumAmntBonus".equals(FCode))
		{
			setSumAmntBonus(Double.valueOf(FValue));
		}

	if("SumYearGetRate".equals(FCode))
		{
			setSumYearGetRate(Double.valueOf(FValue));
		}

	if("SumYearGet".equals(FCode))
		{
			setSumYearGet(Double.valueOf(FValue));
		}

	if("AliveGetRate".equals(FCode))
		{
			setAliveGetRate(Double.valueOf(FValue));
		}

	if("EdorReasonCode".equals(FCode))
		{
		    setEdorReasonCode(FValue);
		}

	if("ZTMoneyByAcc".equals(FCode))
		{
			setZTMoneyByAcc(Double.valueOf(FValue));
		}

	if("MainSubLRiskFlag".equals(FCode))
		{
		    setMainSubLRiskFlag(FValue);
		}

	if("EdorTypeCal".equals(FCode))
		{
		    setEdorTypeCal(FValue);
		}

	if("AAYears".equals(FCode))
		{
		    setAAYears(FValue);
		}

	if("PayEndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setPayEndDate(d);
		}

	if("GetYear".equals(FCode))
		{
			setGetYear(Double.valueOf(FValue));
		}

	if("Currency".equals(FCode))
		{
		    setCurrency(FValue);
		}

	if("SubRiskFlag".equals(FCode))
		{
		    setSubRiskFlag(FValue);
		}

	if("PayYearFlag".equals(FCode))
		{
		    setPayYearFlag(FValue);
		}

	if("PayYears".equals(FCode))
		{
			setPayYears(Double.valueOf(FValue));
		}

	if("TrayMoney".equals(FCode))
		{
			setTrayMoney(Double.valueOf(FValue));
		}

	if("StartDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStartDate(d);
		}

	if("UWFlag".equals(FCode))
		{
		    setUWFlag(FValue);
		}

	if("EndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEndDate(d);
		}

	if("BonusRate".equals(FCode))
		{
		    setBonusRate(FValue);
		}

	if("InsuYearFlag".equals(FCode))
		{
		    setInsuYearFlag(FValue);
		}

	if("InsuYear".equals(FCode))
		{
			setInsuYear(Double.valueOf(FValue));
		}

	if("CValiDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setCValiDate(d);
		}

	if("Prem".equals(FCode))
		{
			setPrem(Double.valueOf(FValue));
		}

	if("Amnt".equals(FCode))
		{
			setAmnt(Double.valueOf(FValue));
		}

	if("MainPolNo".equals(FCode))
		{
		    setMainPolNo(FValue);
		}

	if("PolValiFlag".equals(FCode))
		{
		    setPolValiFlag(FValue);
		}

	if("PolNo".equals(FCode))
		{
		    setPolNo(FValue);
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("GetYearFlag".equals(FCode))
		{
		    setGetYearFlag(FValue);
		}

	if("RiskCode".equals(FCode))
		{
		    setRiskCode(FValue);
		}

	if("PayToDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setPayToDate(d);
		}

	if("RiskSort".equals(FCode))
		{
		    setRiskSort(FValue);
		}

	if("PayEndYearFlag".equals(FCode))
		{
		    setPayEndYearFlag(FValue);
		}

	if("PayEndYear".equals(FCode))
		{
			setPayEndYear(Double.valueOf(FValue));
		}

	if("BalaDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setBalaDate(d);
		}

	if("LastBalaDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setLastBalaDate(d);
		}

	if("ReduceAmnt".equals(FCode))
		{
			setReduceAmnt(Double.valueOf(FValue));
		}

	if("CVt1".equals(FCode))
		{
			setCVt1(Double.valueOf(FValue));
		}

	if("CVt".equals(FCode))
		{
			setCVt(Double.valueOf(FValue));
		}

	if("CVtGetDraw".equals(FCode))
		{
			setCVtGetDraw(Double.valueOf(FValue));
		}

	if("IntervalM".equals(FCode))
		{
			setIntervalM(Double.valueOf(FValue));
		}

	if("FloatRate".equals(FCode))
		{
			setFloatRate(Double.valueOf(FValue));
		}

	if("LoanDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setLoanDate(d);
		}

	if("CTVersionDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setCTVersionDate(d);
		}

	if("DutyCode".equals(FCode))
		{
		    setDutyCode(FValue);
		}

	if("PolClaimFlag".equals(FCode))
		{
		    setPolClaimFlag(FValue);
		}

	if("ShortRate".equals(FCode))
		{
			setShortRate(Double.valueOf(FValue));
		}

	if("Get".equals(FCode))
		{
			setGet(Double.valueOf(FValue));
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
	  if (FCode.equalsIgnoreCase("CVt1GetDraw"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCVt1GetDraw()));
	  }
	  if (FCode.equalsIgnoreCase("Mult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMult()));
	  }
	  if (FCode.equalsIgnoreCase("StandByFlag1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStandByFlag1()));
	  }
	  if (FCode.equalsIgnoreCase("SignDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getSignDate())));
	  }
	  if (FCode.equalsIgnoreCase("GetIntv"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetIntv()));
	  }
	  if (FCode.equalsIgnoreCase("GetLimit"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetLimit()));
	  }
	  if (FCode.equalsIgnoreCase("GetRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetRate()));
	  }
	  if (FCode.equalsIgnoreCase("VPU"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getVPU()));
	  }
	  if (FCode.equalsIgnoreCase("BonusGetMode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusGetMode()));
	  }
	  if (FCode.equalsIgnoreCase("BonusGetModeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusGetModeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("LoanMoney"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLoanMoney()));
	  }
	  if (FCode.equalsIgnoreCase("AddRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddRate()));
	  }
	  if (FCode.equalsIgnoreCase("RpulARFee"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRpulARFee()));
	  }
	  if (FCode.equalsIgnoreCase("RpulPATIFee"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRpulPATIFee()));
	  }
	  if (FCode.equalsIgnoreCase("TBRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTBRate()));
	  }
	  if (FCode.equalsIgnoreCase("GVt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGVt()));
	  }
	  if (FCode.equalsIgnoreCase("GVt1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGVt1()));
	  }
	  if (FCode.equalsIgnoreCase("SumPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumPrem()));
	  }
	  if (FCode.equalsIgnoreCase("NextPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNextPrem()));
	  }
	  if (FCode.equalsIgnoreCase("PayTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayTimes()));
	  }
	  if (FCode.equalsIgnoreCase("AliveGet"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAliveGet()));
	  }
	  if (FCode.equalsIgnoreCase("liveGet"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getliveGet()));
	  }
	  if (FCode.equalsIgnoreCase("SumAmntBonus"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumAmntBonus()));
	  }
	  if (FCode.equalsIgnoreCase("SumYearGetRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumYearGetRate()));
	  }
	  if (FCode.equalsIgnoreCase("SumYearGet"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumYearGet()));
	  }
	  if (FCode.equalsIgnoreCase("AliveGetRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAliveGetRate()));
	  }
	  if (FCode.equalsIgnoreCase("EdorReasonCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorReasonCode()));
	  }
	  if (FCode.equalsIgnoreCase("ZTMoneyByAcc"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getZTMoneyByAcc()));
	  }
	  if (FCode.equalsIgnoreCase("MainSubLRiskFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMainSubLRiskFlag()));
	  }
	  if (FCode.equalsIgnoreCase("EdorTypeCal"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorTypeCal()));
	  }
	  if (FCode.equalsIgnoreCase("AAYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAAYears()));
	  }
	  if (FCode.equalsIgnoreCase("PayEndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getPayEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("GetYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetYear()));
	  }
	  if (FCode.equalsIgnoreCase("Currency"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrency()));
	  }
	  if (FCode.equalsIgnoreCase("SubRiskFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSubRiskFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PayYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PayYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayYears()));
	  }
	  if (FCode.equalsIgnoreCase("TrayMoney"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTrayMoney()));
	  }
	  if (FCode.equalsIgnoreCase("StartDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStartDate())));
	  }
	  if (FCode.equalsIgnoreCase("UWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("EndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("BonusRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusRate()));
	  }
	  if (FCode.equalsIgnoreCase("InsuYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InsuYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuYear()));
	  }
	  if (FCode.equalsIgnoreCase("CValiDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getCValiDate())));
	  }
	  if (FCode.equalsIgnoreCase("Prem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPrem()));
	  }
	  if (FCode.equalsIgnoreCase("Amnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("MainPolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMainPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("PolValiFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolValiFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("GetYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("RiskCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRiskCode()));
	  }
	  if (FCode.equalsIgnoreCase("PayToDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getPayToDate())));
	  }
	  if (FCode.equalsIgnoreCase("RiskSort"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRiskSort()));
	  }
	  if (FCode.equalsIgnoreCase("PayEndYearFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayEndYearFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PayEndYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayEndYear()));
	  }
	  if (FCode.equalsIgnoreCase("BalaDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getBalaDate())));
	  }
	  if (FCode.equalsIgnoreCase("LastBalaDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getLastBalaDate())));
	  }
	  if (FCode.equalsIgnoreCase("ReduceAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReduceAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("CVt1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCVt1()));
	  }
	  if (FCode.equalsIgnoreCase("CVt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCVt()));
	  }
	  if (FCode.equalsIgnoreCase("CVtGetDraw"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCVtGetDraw()));
	  }
	  if (FCode.equalsIgnoreCase("IntervalM"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getIntervalM()));
	  }
	  if (FCode.equalsIgnoreCase("FloatRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFloatRate()));
	  }
	  if (FCode.equalsIgnoreCase("LoanDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getLoanDate())));
	  }
	  if (FCode.equalsIgnoreCase("CTVersionDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getCTVersionDate())));
	  }
	  if (FCode.equalsIgnoreCase("DutyCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDutyCode()));
	  }
	  if (FCode.equalsIgnoreCase("PolClaimFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolClaimFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ShortRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getShortRate()));
	  }
	  if (FCode.equalsIgnoreCase("Get"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGet()));
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
