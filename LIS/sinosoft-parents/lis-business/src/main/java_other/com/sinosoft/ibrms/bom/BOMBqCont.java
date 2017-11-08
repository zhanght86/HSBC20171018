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
 * <p>ClassName: BOMBqCont </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-06-25
 */

public class BOMBqCont extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 保全申请机构 */
	private String ManageCom;

	/** 用户授权标记 */
	private String UserPopedomFlag;

	/** 资料备齐日小于等于上计价日标记 */
	private String ReadyAllInforFlag;

	/** 犹豫期内不允许进行该保全操作标记 */
	private String CustomgetPolDateFlag;

	/** 保全用户授权标记 */
	private String EdorPopedomFlag;

	/** 客户层保全处理客户号码 */
	private String CustomerNo;

	/** 保全周年日 */
	private Date StrSDate;

	/** 保全手续费标记(AR,TI,PA) */
	private String QHFlag;

	/** 批改算法 */
	private Date EdorTypeCal;

	/** 退保点所在保单年度的经过天数 */
	private Double ThroughDay;

	/** 保全项目 */
	private String EdorType;

	/** 保全申请日期 */
	private Date EdorAppDate;

	/** 保全生效日期 */
	private Date EdorValiDate;

	/** 退保点 */
	private Date ZTPoint;

	/** 保险年龄年期 */
	private Double InsuYear;

	/** 保额 */
	private Double Amnt;

	/** 保费 */
	private Double Prem;

	/** 投保日期 */
	private Date PolApplyDate;

	/** 销售渠道 */
	private String SaleChnl;

	/** 保全支付方式 */
	private String PayForm;

	/** 退保控制范围 */
	private Double LimitDay;

	/** 当前日期 */
	private Date CURValidate;

	/** 操作员 */
	private String Operator;

	/** 保单状态 */
	private String PolState;

	/** 自动转账进行中 */
	private String AutopayFlag;

	/** 当前日期 */
	private Date CurrentDate;

	/** 变动的保费 */
	private Double ChgPrem;

	/** 受理号 */
	private String EdorAcceptNo;

	/** 批改状态 */
	private String EdorState;

	/** 保全核保状态 */
	private String UWFlag;

	/** 补/退费金额 */
	private Double GetMoney;

	/** 补/退费利息 */
	private Double GetInterest;

	/** 原始利率类型 */
	private String RateType;

	/** 存借类型 */
	private String RLType;

	/** 单利/复利 */
	private String SCType;

	/** 年利/月利/日利 */
	private String YMDinterest;

	/** 是否强制分红标志 */
	private String ForceDVFlag;

	/** 币种 */
	private String Currency;

	/** 保单号码 */
	private String ContNo;

	/** 保全受理号 */
	private String EdorNo;

	/** 缴费费率 */
	private Double MonsRate;

	/** 期初时刻保单经过天数 */
	private String ThroughDays;

	/** 期末时刻保单经过天数 */
	private Double ThroughDays1;

	/** 保单年度 */
	private Double Interval;

	/** 当期保费是否已交标记 */
	private String PayNextFlag;

	/** 年金领取方式 */
	private String GetDutyKind;

	/** 交费间隔 */
	private String PayIntv;

	/** 责任终止日期 */
	private Date EndDate;

	/** 申请日对应生效日不是同天并30日内 */
	private String ValidateFlag;

	/** 保单期未年度 */
	private Double Duration;



	// @Constructor
	public BOMBqCont()
	{  }

	public void setManageCom(String  ManageCom )
	{
	  this.ManageCom = ManageCom;
 	}

	public String getManageCom()
	{
	  return ManageCom;
	}

	public void setUserPopedomFlag(String  UserPopedomFlag )
	{
	  this.UserPopedomFlag = UserPopedomFlag;
 	}

	public String getUserPopedomFlag()
	{
	  return UserPopedomFlag;
	}

	public void setReadyAllInforFlag(String  ReadyAllInforFlag )
	{
	  this.ReadyAllInforFlag = ReadyAllInforFlag;
 	}

	public String getReadyAllInforFlag()
	{
	  return ReadyAllInforFlag;
	}

	public void setCustomgetPolDateFlag(String  CustomgetPolDateFlag )
	{
	  this.CustomgetPolDateFlag = CustomgetPolDateFlag;
 	}

	public String getCustomgetPolDateFlag()
	{
	  return CustomgetPolDateFlag;
	}

	public void setEdorPopedomFlag(String  EdorPopedomFlag )
	{
	  this.EdorPopedomFlag = EdorPopedomFlag;
 	}

	public String getEdorPopedomFlag()
	{
	  return EdorPopedomFlag;
	}

	public void setCustomerNo(String  CustomerNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(CustomerNo)))){
	    	return;
  	   }else{
	    	this.CustomerNo = CustomerNo;
	  }
 	}

	public String getCustomerNo()
	{
	  return CustomerNo;
	}

	public void setStrSDate(Date  StrSDate )
	{
	  this.StrSDate = StrSDate;
 	}

	public Date getStrSDate()
	{
	  return StrSDate;
	}

	public void setQHFlag(String  QHFlag )
	{
	  this.QHFlag = QHFlag;
 	}

	public String getQHFlag()
	{
	  return QHFlag;
	}

	public void setEdorTypeCal(Date  EdorTypeCal )
	{
	  this.EdorTypeCal = EdorTypeCal;
 	}

	public Date getEdorTypeCal()
	{
	  return EdorTypeCal;
	}

	public void setThroughDay( Double ThroughDay )
	{
	  this.ThroughDay = ThroughDay;
 	}

	public Double getThroughDay()
	{
	  return ThroughDay;
	}

	public void setEdorType(String  EdorType )
	{
	  this.EdorType = EdorType;
 	}

	public String getEdorType()
	{
	  return EdorType;
	}

	public void setEdorAppDate(Date  EdorAppDate )
	{
	  this.EdorAppDate = EdorAppDate;
 	}

	public Date getEdorAppDate()
	{
	  return EdorAppDate;
	}

	public void setEdorValiDate(Date  EdorValiDate )
	{
	  this.EdorValiDate = EdorValiDate;
 	}

	public Date getEdorValiDate()
	{
	  return EdorValiDate;
	}

	public void setZTPoint(Date  ZTPoint )
	{
	  this.ZTPoint = ZTPoint;
 	}

	public Date getZTPoint()
	{
	  return ZTPoint;
	}

	public void setInsuYear( Double InsuYear )
	{
	  this.InsuYear = InsuYear;
 	}

	public Double getInsuYear()
	{
	  return InsuYear;
	}

	public void setAmnt( Double Amnt )
	{
	  this.Amnt = Amnt;
 	}

	public Double getAmnt()
	{
	  return Amnt;
	}

	public void setPrem( Double Prem )
	{
	  this.Prem = Prem;
 	}

	public Double getPrem()
	{
	  return Prem;
	}

	public void setPolApplyDate(Date  PolApplyDate )
	{
	  this.PolApplyDate = PolApplyDate;
 	}

	public Date getPolApplyDate()
	{
	  return PolApplyDate;
	}

	public void setSaleChnl(String  SaleChnl )
	{
	  this.SaleChnl = SaleChnl;
 	}

	public String getSaleChnl()
	{
	  return SaleChnl;
	}

	public void setPayForm(String  PayForm )
	{
	  this.PayForm = PayForm;
 	}

	public String getPayForm()
	{
	  return PayForm;
	}

	public void setLimitDay( Double LimitDay )
	{
	  this.LimitDay = LimitDay;
 	}

	public Double getLimitDay()
	{
	  return LimitDay;
	}

	public void setCURValidate(Date  CURValidate )
	{
	  this.CURValidate = CURValidate;
 	}

	public Date getCURValidate()
	{
	  return CURValidate;
	}

	public void setOperator(String  Operator )
	{
	  this.Operator = Operator;
 	}

	public String getOperator()
	{
	  return Operator;
	}

	public void setPolState(String  PolState )
	{
	  this.PolState = PolState;
 	}

	public String getPolState()
	{
	  return PolState;
	}

	public void setAutopayFlag(String  AutopayFlag )
	{
	  this.AutopayFlag = AutopayFlag;
 	}

	public String getAutopayFlag()
	{
	  return AutopayFlag;
	}

	public void setCurrentDate(Date  CurrentDate )
	{
	  this.CurrentDate = CurrentDate;
 	}

	public Date getCurrentDate()
	{
	  return CurrentDate;
	}

	public void setChgPrem( Double ChgPrem )
	{
	  this.ChgPrem = ChgPrem;
 	}

	public Double getChgPrem()
	{
	  return ChgPrem;
	}

	public void setEdorAcceptNo(String  EdorAcceptNo )
	{
	  this.EdorAcceptNo = EdorAcceptNo;
 	}

	public String getEdorAcceptNo()
	{
	  return EdorAcceptNo;
	}

	public void setEdorState(String  EdorState )
	{
	  this.EdorState = EdorState;
 	}

	public String getEdorState()
	{
	  return EdorState;
	}

	public void setUWFlag(String  UWFlag )
	{
	  this.UWFlag = UWFlag;
 	}

	public String getUWFlag()
	{
	  return UWFlag;
	}

	public void setGetMoney( Double GetMoney )
	{
	  this.GetMoney = GetMoney;
 	}

	public Double getGetMoney()
	{
	  return GetMoney;
	}

	public void setGetInterest( Double GetInterest )
	{
	  this.GetInterest = GetInterest;
 	}

	public Double getGetInterest()
	{
	  return GetInterest;
	}

	public void setRateType(String  RateType )
	{
	  this.RateType = RateType;
 	}

	public String getRateType()
	{
	  return RateType;
	}

	public void setRLType(String  RLType )
	{
	  this.RLType = RLType;
 	}

	public String getRLType()
	{
	  return RLType;
	}

	public void setSCType(String  SCType )
	{
	  this.SCType = SCType;
 	}

	public String getSCType()
	{
	  return SCType;
	}

	public void setYMDinterest(String  YMDinterest )
	{
	  this.YMDinterest = YMDinterest;
 	}

	public String getYMDinterest()
	{
	  return YMDinterest;
	}

	public void setForceDVFlag(String  ForceDVFlag )
	{
	  this.ForceDVFlag = ForceDVFlag;
 	}

	public String getForceDVFlag()
	{
	  return ForceDVFlag;
	}

	public void setCurrency(String  Currency )
	{
	  this.Currency = Currency;
 	}

	public String getCurrency()
	{
	  return Currency;
	}

	public void setContNo(String  ContNo )
	{
	  this.ContNo = ContNo;
 	}

	public String getContNo()
	{
	  return ContNo;
	}

	public void setEdorNo(String  EdorNo )
	{
	  this.EdorNo = EdorNo;
 	}

	public String getEdorNo()
	{
	  return EdorNo;
	}

	public void setMonsRate( Double MonsRate )
	{
	  this.MonsRate = MonsRate;
 	}

	public Double getMonsRate()
	{
	  return MonsRate;
	}

	public void setThroughDays(String  ThroughDays )
	{
	  this.ThroughDays = ThroughDays;
 	}

	public String getThroughDays()
	{
	  return ThroughDays;
	}

	public void setThroughDays1( Double ThroughDays1 )
	{
	  this.ThroughDays1 = ThroughDays1;
 	}

	public Double getThroughDays1()
	{
	  return ThroughDays1;
	}

	public void setInterval( Double Interval )
	{
	  this.Interval = Interval;
 	}

	public Double getInterval()
	{
	  return Interval;
	}

	public void setPayNextFlag(String  PayNextFlag )
	{
	  this.PayNextFlag = PayNextFlag;
 	}

	public String getPayNextFlag()
	{
	  return PayNextFlag;
	}

	public void setGetDutyKind(String  GetDutyKind )
	{
	  this.GetDutyKind = GetDutyKind;
 	}

	public String getGetDutyKind()
	{
	  return GetDutyKind;
	}

	public void setPayIntv(String  PayIntv )
	{
	  this.PayIntv = PayIntv;
 	}

	public String getPayIntv()
	{
	  return PayIntv;
	}

	public void setEndDate(Date  EndDate )
	{
	  this.EndDate = EndDate;
 	}

	public Date getEndDate()
	{
	  return EndDate;
	}

	public void setValidateFlag(String  ValidateFlag )
	{
	  this.ValidateFlag = ValidateFlag;
 	}

	public String getValidateFlag()
	{
	  return ValidateFlag;
	}

	public void setDuration( Double Duration )
	{
	  this.Duration = Duration;
 	}

	public Double getDuration()
	{
	  return Duration;
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

	if("ManageCom".equals(FCode))
		{
		    setManageCom(FValue);
		}

	if("UserPopedomFlag".equals(FCode))
		{
		    setUserPopedomFlag(FValue);
		}

	if("ReadyAllInforFlag".equals(FCode))
		{
		    setReadyAllInforFlag(FValue);
		}

	if("CustomgetPolDateFlag".equals(FCode))
		{
		    setCustomgetPolDateFlag(FValue);
		}

	if("EdorPopedomFlag".equals(FCode))
		{
		    setEdorPopedomFlag(FValue);
		}

	if("CustomerNo".equals(FCode))
		{
		    setCustomerNo(FValue);
		}

	if("StrSDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStrSDate(d);
		}

	if("QHFlag".equals(FCode))
		{
		    setQHFlag(FValue);
		}

	if("EdorTypeCal".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEdorTypeCal(d);
		}

	if("ThroughDay".equals(FCode))
		{
			setThroughDay(Double.valueOf(FValue));
		}

	if("EdorType".equals(FCode))
		{
		    setEdorType(FValue);
		}

	if("EdorAppDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEdorAppDate(d);
		}

	if("EdorValiDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEdorValiDate(d);
		}

	if("ZTPoint".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setZTPoint(d);
		}

	if("InsuYear".equals(FCode))
		{
			setInsuYear(Double.valueOf(FValue));
		}

	if("Amnt".equals(FCode))
		{
			setAmnt(Double.valueOf(FValue));
		}

	if("Prem".equals(FCode))
		{
			setPrem(Double.valueOf(FValue));
		}

	if("PolApplyDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setPolApplyDate(d);
		}

	if("SaleChnl".equals(FCode))
		{
		    setSaleChnl(FValue);
		}

	if("PayForm".equals(FCode))
		{
		    setPayForm(FValue);
		}

	if("LimitDay".equals(FCode))
		{
			setLimitDay(Double.valueOf(FValue));
		}

	if("CURValidate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setCURValidate(d);
		}

	if("Operator".equals(FCode))
		{
		    setOperator(FValue);
		}

	if("PolState".equals(FCode))
		{
		    setPolState(FValue);
		}

	if("AutopayFlag".equals(FCode))
		{
		    setAutopayFlag(FValue);
		}

	if("CurrentDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setCurrentDate(d);
		}

	if("ChgPrem".equals(FCode))
		{
			setChgPrem(Double.valueOf(FValue));
		}

	if("EdorAcceptNo".equals(FCode))
		{
		    setEdorAcceptNo(FValue);
		}

	if("EdorState".equals(FCode))
		{
		    setEdorState(FValue);
		}

	if("UWFlag".equals(FCode))
		{
		    setUWFlag(FValue);
		}

	if("GetMoney".equals(FCode))
		{
			setGetMoney(Double.valueOf(FValue));
		}

	if("GetInterest".equals(FCode))
		{
			setGetInterest(Double.valueOf(FValue));
		}

	if("RateType".equals(FCode))
		{
		    setRateType(FValue);
		}

	if("RLType".equals(FCode))
		{
		    setRLType(FValue);
		}

	if("SCType".equals(FCode))
		{
		    setSCType(FValue);
		}

	if("YMDinterest".equals(FCode))
		{
		    setYMDinterest(FValue);
		}

	if("ForceDVFlag".equals(FCode))
		{
		    setForceDVFlag(FValue);
		}

	if("Currency".equals(FCode))
		{
		    setCurrency(FValue);
		}

	if("ContNo".equals(FCode))
		{
		    setContNo(FValue);
		}

	if("EdorNo".equals(FCode))
		{
		    setEdorNo(FValue);
		}

	if("MonsRate".equals(FCode))
		{
			setMonsRate(Double.valueOf(FValue));
		}

	if("ThroughDays".equals(FCode))
		{
		    setThroughDays(FValue);
		}

	if("ThroughDays1".equals(FCode))
		{
			setThroughDays1(Double.valueOf(FValue));
		}

	if("Interval".equals(FCode))
		{
			setInterval(Double.valueOf(FValue));
		}

	if("PayNextFlag".equals(FCode))
		{
		    setPayNextFlag(FValue);
		}

	if("GetDutyKind".equals(FCode))
		{
		    setGetDutyKind(FValue);
		}

	if("PayIntv".equals(FCode))
		{
		    setPayIntv(FValue);
		}

	if("EndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEndDate(d);
		}

	if("ValidateFlag".equals(FCode))
		{
		    setValidateFlag(FValue);
		}

	if("Duration".equals(FCode))
		{
			setDuration(Double.valueOf(FValue));
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
	  if (FCode.equalsIgnoreCase("ManageCom"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getManageCom()));
	  }
	  if (FCode.equalsIgnoreCase("UserPopedomFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUserPopedomFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ReadyAllInforFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReadyAllInforFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CustomgetPolDateFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCustomgetPolDateFlag()));
	  }
	  if (FCode.equalsIgnoreCase("EdorPopedomFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorPopedomFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CustomerNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCustomerNo()));
	  }
	  if (FCode.equalsIgnoreCase("StrSDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStrSDate())));
	  }
	  if (FCode.equalsIgnoreCase("QHFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQHFlag()));
	  }
	  if (FCode.equalsIgnoreCase("EdorTypeCal"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEdorTypeCal())));
	  }
	  if (FCode.equalsIgnoreCase("ThroughDay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThroughDay()));
	  }
	  if (FCode.equalsIgnoreCase("EdorType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorType()));
	  }
	  if (FCode.equalsIgnoreCase("EdorAppDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEdorAppDate())));
	  }
	  if (FCode.equalsIgnoreCase("EdorValiDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEdorValiDate())));
	  }
	  if (FCode.equalsIgnoreCase("ZTPoint"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getZTPoint())));
	  }
	  if (FCode.equalsIgnoreCase("InsuYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuYear()));
	  }
	  if (FCode.equalsIgnoreCase("Amnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("Prem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPrem()));
	  }
	  if (FCode.equalsIgnoreCase("PolApplyDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getPolApplyDate())));
	  }
	  if (FCode.equalsIgnoreCase("SaleChnl"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSaleChnl()));
	  }
	  if (FCode.equalsIgnoreCase("PayForm"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayForm()));
	  }
	  if (FCode.equalsIgnoreCase("LimitDay"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLimitDay()));
	  }
	  if (FCode.equalsIgnoreCase("CURValidate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getCURValidate())));
	  }
	  if (FCode.equalsIgnoreCase("Operator"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOperator()));
	  }
	  if (FCode.equalsIgnoreCase("PolState"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolState()));
	  }
	  if (FCode.equalsIgnoreCase("AutopayFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAutopayFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CurrentDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getCurrentDate())));
	  }
	  if (FCode.equalsIgnoreCase("ChgPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getChgPrem()));
	  }
	  if (FCode.equalsIgnoreCase("EdorAcceptNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorAcceptNo()));
	  }
	  if (FCode.equalsIgnoreCase("EdorState"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorState()));
	  }
	  if (FCode.equalsIgnoreCase("UWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("GetMoney"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetMoney()));
	  }
	  if (FCode.equalsIgnoreCase("GetInterest"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetInterest()));
	  }
	  if (FCode.equalsIgnoreCase("RateType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRateType()));
	  }
	  if (FCode.equalsIgnoreCase("RLType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRLType()));
	  }
	  if (FCode.equalsIgnoreCase("SCType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSCType()));
	  }
	  if (FCode.equalsIgnoreCase("YMDinterest"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getYMDinterest()));
	  }
	  if (FCode.equalsIgnoreCase("ForceDVFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getForceDVFlag()));
	  }
	  if (FCode.equalsIgnoreCase("Currency"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCurrency()));
	  }
	  if (FCode.equalsIgnoreCase("ContNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getContNo()));
	  }
	  if (FCode.equalsIgnoreCase("EdorNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorNo()));
	  }
	  if (FCode.equalsIgnoreCase("MonsRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMonsRate()));
	  }
	  if (FCode.equalsIgnoreCase("ThroughDays"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThroughDays()));
	  }
	  if (FCode.equalsIgnoreCase("ThroughDays1"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThroughDays1()));
	  }
	  if (FCode.equalsIgnoreCase("Interval"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInterval()));
	  }
	  if (FCode.equalsIgnoreCase("PayNextFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayNextFlag()));
	  }
	  if (FCode.equalsIgnoreCase("GetDutyKind"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetDutyKind()));
	  }
	  if (FCode.equalsIgnoreCase("PayIntv"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayIntv()));
	  }
	  if (FCode.equalsIgnoreCase("EndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("ValidateFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getValidateFlag()));
	  }
	  if (FCode.equalsIgnoreCase("Duration"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDuration()));
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
