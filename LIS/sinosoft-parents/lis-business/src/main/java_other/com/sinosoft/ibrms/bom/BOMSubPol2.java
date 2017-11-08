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
 * <p>ClassName: BOMSubPol2 </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-02-24
 */

public class BOMSubPol2 extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";

	private BOMMainPol tBOMMainPol;

	/** 交费期间 */
	private Double PayYears;

	/** 币种 */
	private String Currency;

	/** 险种号码 */
	private String PolNo;

	/** 被保人号码 */
	private String InsuredNo;

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

	/** 保险期间 */
	private Double InsuYear;

	/** 保险期间单位 */
	private String InsuYearFlag;

	/** 险种类别 */
	private String KindCode;

	/** 核保状态 */
	private String UWFlag;

	/** 生存金领取方式 */
	private String LiveGetMode;

	/** 红利金领取方式 */
	private String BonusGetMode;

	/** 红利领取人类型 */
	private String BonusManType;

	/** 停售标记 */
	private String StopFlag;

	/** 职业加费标记 */
	private String OAddFeeFlag;

	/** 健康加费标记 */
	private String HAddFeeFlag;

	/** 保费减费/免费标志 */
	private String DerateOrFreeFlag;

	/** 累计该险种保额 */
	private Double TotalAmnt;

	/** 被保人数目 */
	private Double InsuredPeoples;

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

	/** 单位保额 */
	private Double VPU;

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

	/** 自动垫交标记 */
	private String AutoPayFlag;



	// @Constructor
	public BOMSubPol2()
	{  }

	public void setFatherBOM(BOMMainPol mBOMMainPol)
	{
		this.tBOMMainPol = mBOMMainPol;
	}
 
	public AbstractBOM getFatherBOM()
	{
		return tBOMMainPol;
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
	   if(!(new BOMPreCheck().CheckPlus(Amnt))){
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
	   if(!(new BOMPreCheck().CheckPlus(Prem))){
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
	   if(!(new BOMPreCheck().CheckIngeter(Mult))){
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
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(InsuYearFlag)))){
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
	   if(!(new BOMPreCheck().CheckPlus(TotalAmnt))){
	  	  return;
	    }else{
	    	this.TotalAmnt = TotalAmnt;
	   }
 	}

	public Double getTotalAmnt()
	{
	  return TotalAmnt;
	}

	public void setInsuredPeoples( Double InsuredPeoples )
	{
	   if(!(new BOMPreCheck().CheckIngeter(InsuredPeoples))){
	  	  return;
	    }else{
	    	this.InsuredPeoples = InsuredPeoples;
	   }
 	}

	public Double getInsuredPeoples()
	{
	  return InsuredPeoples;
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

	public void setVPU( Double VPU )
	{
	  this.VPU = VPU;
 	}

	public Double getVPU()
	{
	  return VPU;
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

	public void setAutoPayFlag(String  AutoPayFlag )
	{
	  this.AutoPayFlag = AutoPayFlag;
 	}

	public String getAutoPayFlag()
	{
	  return AutoPayFlag;
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

	if("PayYears".equals(FCode))
		{
			setPayYears(Double.valueOf(FValue));
		}

	if("Currency".equals(FCode))
		{
		    setCurrency(FValue);
		}

	if("PolNo".equals(FCode))
		{
		    setPolNo(FValue);
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
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

	if("LiveGetMode".equals(FCode))
		{
		    setLiveGetMode(FValue);
		}

	if("BonusGetMode".equals(FCode))
		{
		    setBonusGetMode(FValue);
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

	if("HAddFeeFlag".equals(FCode))
		{
		    setHAddFeeFlag(FValue);
		}

	if("DerateOrFreeFlag".equals(FCode))
		{
		    setDerateOrFreeFlag(FValue);
		}

	if("TotalAmnt".equals(FCode))
		{
			setTotalAmnt(Double.valueOf(FValue));
		}

	if("InsuredPeoples".equals(FCode))
		{
			setInsuredPeoples(Double.valueOf(FValue));
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

	if("VPU".equals(FCode))
		{
			setVPU(Double.valueOf(FValue));
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

	if("AutoPayFlag".equals(FCode))
		{
		    setAutoPayFlag(FValue);
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
	  if (FCode.equalsIgnoreCase("PayYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayYears()));
	  }
	  if (FCode.equalsIgnoreCase("Currency"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrency()));
	  }
	  if (FCode.equalsIgnoreCase("PolNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolNo()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
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
	  if (FCode.equalsIgnoreCase("LiveGetMode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLiveGetMode()));
	  }
	  if (FCode.equalsIgnoreCase("BonusGetMode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusGetMode()));
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
	  if (FCode.equalsIgnoreCase("HAddFeeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHAddFeeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("DerateOrFreeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDerateOrFreeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("TotalAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTotalAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredPeoples"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredPeoples()));
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
	  if (FCode.equalsIgnoreCase("VPU"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getVPU()));
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
	  if (FCode.equalsIgnoreCase("AutoPayFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAutoPayFlag()));
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
