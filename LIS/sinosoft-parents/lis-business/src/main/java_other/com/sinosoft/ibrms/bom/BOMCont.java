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
 * <p>ClassName: BOMCont </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2016-11-15
 */

public class BOMCont extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 同业务员险种扫描日期份数 */
	private Double UnionContCount;

	/** 保单年度 */
	private Double Interval;

	/** 要约撤销标记 */
	private String OImportDisFlag;

	/** 要约更正标记（打印） */
	private String ImportUpdatePFlag;

	/** 生效日回溯标记 */
	private String PolAppDFlag;

	/** 有体检资料标记 */
	private String HPENotice;

	/** 保单号码 */
	private String ContNo;

	/** 卡单标记 */
	private String CardFlag;

	/** 申请日期 */
	private Date PolApplyDate;

	/** 销售渠道 */
	private String SaleChnl;

	/** 管理机构 */
	private String ManageCom;

	/** 交费方式 */
	private String PayMode;

	/** 保全项目 */
	private String EdorType;

	/** 保全申请日期 */
	private Date EdorAppDate;

	/** 保全 */
	private Date EdorValiDate;

	/** 保险年龄年期 */
	private Double InsuYear;

	/** 份数 */
	private Double Mult;

	/** 交费间隔 */
	private String PayIntv;

	/** 保费 */
	private Double Prem;

	/** 银行账户名 */
	private String AccName;

	/** 强制人工核保标记 */
	private String ForceUWFlag;

	/** 被保人数目 */
	private Double InsuredPeoples;

	/** 已暂收保费 */
	private Double Tempfee;

	/** 自动垫交标记 */
	private String AutoPayFlag;

	/** 自动续保标记 */
	private String RnewFlag;

	/** 入机日期 */
	private Date MakeDate;

	/** 扫描日期 */
	private Date ScanDate;

	/** 有特别约定标记 */
	private String HSpecFlag;

	/** 问卷标记 */
	private String HAskFlag;

	/** 身份证明标记 */
	private String StatusFlag;

	/** 病历资料标记 */
	private String IllFlag;

	/** 其他资料标记 */
	private String OtherDataFlag;

	/** 体检医院名称与系统定义不一致 */
	private String HospitalCodePro;

	/** 核保结论 */
	private String UWFlag;

	/** 保额 */
	private Double Amnt;

	/** 经过自核次数 */
	private Double AutoUWTimes;

	/** 溢交退费方式 */
	private String OutPayFlag;

	/** 开户行 */
	private String BankCode;

	/** 责任终止日期 */
	private Date EndDate;

	/** 保险红利年期 */
	private Double BonusYear;

	/** 投保人问题件标记 */
	private String AppntIssueFlag;

	/** 业务员问题件标记 */
	private String AgentIssueFlag;

	/** 机构问题件标记 */
	private String ComIssueFlag;

	/** 客户问题件标记 */
	private String CustomerIssueFlag;

	/** 出单方式 */
	private String SellType;

	/** 备注信息标记 */
	private String RemarkFlag;

	/** 保险计划变更标记 */
	private String ChangePolFlag;

	/** 保单生效日期 */
	private Date Cvalidate;

	/** 保单标记状态 */
	private String AppFlag;

	/** 补充告知问卷标记 */
	private String ReinImpart;

	/** 备注栏的字数 */
	private Double RemarkCount;

	/** 体检医院是否定点医院 */
	private String IsAppointHos;

	/** 陪检记录 */
	private String AccoBodyCheck;

	/** 系统抽检标记 */
	private String SpotCheckFlag;

	/** 生调回复人员是否与系统定义不一致 */
	private String MOpeIsNotDefined;

	/** 二级机构 */
	private String SecondaryManagecom;

	/** 三级机构 */
	private String ThirdStageManagecom;

	/** 抽检因子 */
	private Double SamplingFactor;

	/** 自核日期 */
	private Date UWDate;

	/** 四级机构 */
	private String FourStageManagecom;

	/** 当前已抽检数 */
	private Double TotalCount;

	/** 管理机构差异化核保等级 */
	private String UWLevel;

	/** 初审日期 */
	private Date FirstTrialDate;

	/** 管理机构百团标记 */
	private String ManaSpecFlag;

	/** 银行账号 */
	private String BankAccNo;

	/** 填单保费 */
	private Double ContPrem;

	/** 应交保费 */
	private Double RealPrem;

	/** 业务员是否亲眼见过被保险人 */
	private String hasSeenInsured;

	/** 要约更正标记（非打印） */
	private String ImportUpdateNFlag;

	/** 首期交费形式 */
	private String InitialPayment;

	/** 续期交费形式 */
	private String RenewalPayment;



	// @Constructor
	public BOMCont()
	{  }

	public void setUnionContCount( Double UnionContCount )
	{
	   if(!(new BOMPreCheck().CheckIngeter(String.valueOf(UnionContCount)))){
	  	  return;
	    }else{
	    	this.UnionContCount = UnionContCount;
	   }
 	}

	public Double getUnionContCount()
	{
	  return UnionContCount;
	}

	public void setInterval( Double Interval )
	{
	  this.Interval = Interval;
 	}

	public Double getInterval()
	{
	  return Interval;
	}

	public void setOImportDisFlag(String  OImportDisFlag )
	{
	  this.OImportDisFlag = OImportDisFlag;
 	}

	public String getOImportDisFlag()
	{
	  return OImportDisFlag;
	}

	public void setImportUpdatePFlag(String  ImportUpdatePFlag )
	{
	  this.ImportUpdatePFlag = ImportUpdatePFlag;
 	}

	public String getImportUpdatePFlag()
	{
	  return ImportUpdatePFlag;
	}

	public void setPolAppDFlag(String  PolAppDFlag )
	{
	  this.PolAppDFlag = PolAppDFlag;
 	}

	public String getPolAppDFlag()
	{
	  return PolAppDFlag;
	}

	public void setHPENotice(String  HPENotice )
	{
	  this.HPENotice = HPENotice;
 	}

	public String getHPENotice()
	{
	  return HPENotice;
	}

	public void setContNo(String  ContNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ContNo)))){
	    	return;
  	   }else{
	    	this.ContNo = ContNo;
	  }
 	}

	public String getContNo()
	{
	  return ContNo;
	}

	public void setCardFlag(String  CardFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(CardFlag)))){
	    	return;
  	   }else{
	    	this.CardFlag = CardFlag;
	  }
 	}

	public String getCardFlag()
	{
	  return CardFlag;
	}

	public void setPolApplyDate(Date  PolApplyDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(PolApplyDate)))){
	    	return;
  	   }else{
	    	this.PolApplyDate = PolApplyDate;
	  }
 	}

	public Date getPolApplyDate()
	{
	  return PolApplyDate;
	}

	public void setSaleChnl(String  SaleChnl )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(SaleChnl)))){
	    	return;
  	   }else{
	    	this.SaleChnl = SaleChnl;
	  }
 	}

	public String getSaleChnl()
	{
	  return SaleChnl;
	}

	public void setManageCom(String  ManageCom )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(ManageCom)))){
	    	return;
  	   }else{
	    	this.ManageCom = ManageCom;
	  }
 	}

	public String getManageCom()
	{
	  return ManageCom;
	}

	public void setPayMode(String  PayMode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(PayMode)))){
	    	return;
  	   }else{
	    	this.PayMode = PayMode;
	  }
 	}

	public String getPayMode()
	{
	  return PayMode;
	}

	public void setEdorType(String  EdorType )
	{
	  if(!(new BOMPreCheck().CheckEngLish(String.valueOf(EdorType)))){
	    	return;
  	   }else{
	    	this.EdorType = EdorType;
	  }
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

	public void setInsuYear( Double InsuYear )
	{
	   if(!(new BOMPreCheck().CheckPI(String.valueOf(InsuYear)))){
	  	  return;
	    }else{
	    	this.InsuYear = InsuYear;
	   }
 	}

	public Double getInsuYear()
	{
	  return InsuYear;
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

	public void setPayIntv(String  PayIntv )
	{
	  if(!(new BOMPreCheck().CheckIngeter(String.valueOf(PayIntv)))){
	    	return;
  	   }else{
	    	this.PayIntv = PayIntv;
	  }
 	}

	public String getPayIntv()
	{
	  return PayIntv;
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

	public void setAccName(String  AccName )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AccName)))){
	    	return;
  	   }else{
	    	this.AccName = AccName;
	  }
 	}

	public String getAccName()
	{
	  return AccName;
	}

	public void setForceUWFlag(String  ForceUWFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(ForceUWFlag)))){
	    	return;
  	   }else{
	    	this.ForceUWFlag = ForceUWFlag;
	  }
 	}

	public String getForceUWFlag()
	{
	  return ForceUWFlag;
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

	public void setTempfee( Double Tempfee )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(Tempfee)))){
	  	  return;
	    }else{
	    	this.Tempfee = Tempfee;
	   }
 	}

	public Double getTempfee()
	{
	  return Tempfee;
	}

	public void setAutoPayFlag(String  AutoPayFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AutoPayFlag)))){
	    	return;
  	   }else{
	    	this.AutoPayFlag = AutoPayFlag;
	  }
 	}

	public String getAutoPayFlag()
	{
	  return AutoPayFlag;
	}

	public void setRnewFlag(String  RnewFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RnewFlag)))){
	    	return;
  	   }else{
	    	this.RnewFlag = RnewFlag;
	  }
 	}

	public String getRnewFlag()
	{
	  return RnewFlag;
	}

	public void setMakeDate(Date  MakeDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(MakeDate)))){
	    	return;
  	   }else{
	    	this.MakeDate = MakeDate;
	  }
 	}

	public Date getMakeDate()
	{
	  return MakeDate;
	}

	public void setScanDate(Date  ScanDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ScanDate)))){
	    	return;
  	   }else{
	    	this.ScanDate = ScanDate;
	  }
 	}

	public Date getScanDate()
	{
	  return ScanDate;
	}

	public void setHSpecFlag(String  HSpecFlag )
	{
	  this.HSpecFlag = HSpecFlag;
 	}

	public String getHSpecFlag()
	{
	  return HSpecFlag;
	}

	public void setHAskFlag(String  HAskFlag )
	{
	  this.HAskFlag = HAskFlag;
 	}

	public String getHAskFlag()
	{
	  return HAskFlag;
	}

	public void setStatusFlag(String  StatusFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(StatusFlag)))){
	    	return;
  	   }else{
	    	this.StatusFlag = StatusFlag;
	  }
 	}

	public String getStatusFlag()
	{
	  return StatusFlag;
	}

	public void setIllFlag(String  IllFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(IllFlag)))){
	    	return;
  	   }else{
	    	this.IllFlag = IllFlag;
	  }
 	}

	public String getIllFlag()
	{
	  return IllFlag;
	}

	public void setOtherDataFlag(String  OtherDataFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OtherDataFlag)))){
	    	return;
  	   }else{
	    	this.OtherDataFlag = OtherDataFlag;
	  }
 	}

	public String getOtherDataFlag()
	{
	  return OtherDataFlag;
	}

	public void setHospitalCodePro(String  HospitalCodePro )
	{
	  this.HospitalCodePro = HospitalCodePro;
 	}

	public String getHospitalCodePro()
	{
	  return HospitalCodePro;
	}

	public void setUWFlag(String  UWFlag )
	{
	  this.UWFlag = UWFlag;
 	}

	public String getUWFlag()
	{
	  return UWFlag;
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

	public void setAutoUWTimes( Double AutoUWTimes )
	{
	   if(!(new BOMPreCheck().CheckPI(String.valueOf(AutoUWTimes)))){
	  	  return;
	    }else{
	    	this.AutoUWTimes = AutoUWTimes;
	   }
 	}

	public Double getAutoUWTimes()
	{
	  return AutoUWTimes;
	}

	public void setOutPayFlag(String  OutPayFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OutPayFlag)))){
	    	return;
  	   }else{
	    	this.OutPayFlag = OutPayFlag;
	  }
 	}

	public String getOutPayFlag()
	{
	  return OutPayFlag;
	}

	public void setBankCode(String  BankCode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BankCode)))){
	    	return;
  	   }else{
	    	this.BankCode = BankCode;
	  }
 	}

	public String getBankCode()
	{
	  return BankCode;
	}

	public void setEndDate(Date  EndDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(EndDate)))){
	    	return;
  	   }else{
	    	this.EndDate = EndDate;
	  }
 	}

	public Date getEndDate()
	{
	  return EndDate;
	}

	public void setBonusYear( Double BonusYear )
	{
	   if(!(new BOMPreCheck().CheckPI(String.valueOf(BonusYear)))){
	  	  return;
	    }else{
	    	this.BonusYear = BonusYear;
	   }
 	}

	public Double getBonusYear()
	{
	  return BonusYear;
	}

	public void setAppntIssueFlag(String  AppntIssueFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AppntIssueFlag)))){
	    	return;
  	   }else{
	    	this.AppntIssueFlag = AppntIssueFlag;
	  }
 	}

	public String getAppntIssueFlag()
	{
	  return AppntIssueFlag;
	}

	public void setAgentIssueFlag(String  AgentIssueFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AgentIssueFlag)))){
	    	return;
  	   }else{
	    	this.AgentIssueFlag = AgentIssueFlag;
	  }
 	}

	public String getAgentIssueFlag()
	{
	  return AgentIssueFlag;
	}

	public void setComIssueFlag(String  ComIssueFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(ComIssueFlag)))){
	    	return;
  	   }else{
	    	this.ComIssueFlag = ComIssueFlag;
	  }
 	}

	public String getComIssueFlag()
	{
	  return ComIssueFlag;
	}

	public void setCustomerIssueFlag(String  CustomerIssueFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(CustomerIssueFlag)))){
	    	return;
  	   }else{
	    	this.CustomerIssueFlag = CustomerIssueFlag;
	  }
 	}

	public String getCustomerIssueFlag()
	{
	  return CustomerIssueFlag;
	}

	public void setSellType(String  SellType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(SellType)))){
	    	return;
  	   }else{
	    	this.SellType = SellType;
	  }
 	}

	public String getSellType()
	{
	  return SellType;
	}

	public void setRemarkFlag(String  RemarkFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RemarkFlag)))){
	    	return;
  	   }else{
	    	this.RemarkFlag = RemarkFlag;
	  }
 	}

	public String getRemarkFlag()
	{
	  return RemarkFlag;
	}

	public void setChangePolFlag(String  ChangePolFlag )
	{
	  this.ChangePolFlag = ChangePolFlag;
 	}

	public String getChangePolFlag()
	{
	  return ChangePolFlag;
	}

	public void setCvalidate(Date  Cvalidate )
	{
	  this.Cvalidate = Cvalidate;
 	}

	public Date getCvalidate()
	{
	  return Cvalidate;
	}

	public void setAppFlag(String  AppFlag )
	{
	  this.AppFlag = AppFlag;
 	}

	public String getAppFlag()
	{
	  return AppFlag;
	}

	public void setReinImpart(String  ReinImpart )
	{
	  this.ReinImpart = ReinImpart;
 	}

	public String getReinImpart()
	{
	  return ReinImpart;
	}

	public void setRemarkCount( Double RemarkCount )
	{
	  this.RemarkCount = RemarkCount;
 	}

	public Double getRemarkCount()
	{
	  return RemarkCount;
	}

	public void setIsAppointHos(String  IsAppointHos )
	{
	  this.IsAppointHos = IsAppointHos;
 	}

	public String getIsAppointHos()
	{
	  return IsAppointHos;
	}

	public void setAccoBodyCheck(String  AccoBodyCheck )
	{
	  this.AccoBodyCheck = AccoBodyCheck;
 	}

	public String getAccoBodyCheck()
	{
	  return AccoBodyCheck;
	}

	public void setSpotCheckFlag(String  SpotCheckFlag )
	{
	  this.SpotCheckFlag = SpotCheckFlag;
 	}

	public String getSpotCheckFlag()
	{
	  return SpotCheckFlag;
	}

	public void setMOpeIsNotDefined(String  MOpeIsNotDefined )
	{
	  this.MOpeIsNotDefined = MOpeIsNotDefined;
 	}

	public String getMOpeIsNotDefined()
	{
	  return MOpeIsNotDefined;
	}

	public void setSecondaryManagecom(String  SecondaryManagecom )
	{
	  this.SecondaryManagecom = SecondaryManagecom;
 	}

	public String getSecondaryManagecom()
	{
	  return SecondaryManagecom;
	}

	public void setThirdStageManagecom(String  ThirdStageManagecom )
	{
	  this.ThirdStageManagecom = ThirdStageManagecom;
 	}

	public String getThirdStageManagecom()
	{
	  return ThirdStageManagecom;
	}

	public void setSamplingFactor( Double SamplingFactor )
	{
	  this.SamplingFactor = SamplingFactor;
 	}

	public Double getSamplingFactor()
	{
	  return SamplingFactor;
	}

	public void setUWDate(Date  UWDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(UWDate)))){
	    	return;
  	   }else{
	    	this.UWDate = UWDate;
	  }
 	}

	public Date getUWDate()
	{
	  return UWDate;
	}

	public void setFourStageManagecom(String  FourStageManagecom )
	{
	  this.FourStageManagecom = FourStageManagecom;
 	}

	public String getFourStageManagecom()
	{
	  return FourStageManagecom;
	}

	public void setTotalCount( Double TotalCount )
	{
	  this.TotalCount = TotalCount;
 	}

	public Double getTotalCount()
	{
	  return TotalCount;
	}

	public void setUWLevel(String  UWLevel )
	{
	  this.UWLevel = UWLevel;
 	}

	public String getUWLevel()
	{
	  return UWLevel;
	}

	public void setFirstTrialDate(Date  FirstTrialDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(FirstTrialDate)))){
	    	return;
  	   }else{
	    	this.FirstTrialDate = FirstTrialDate;
	  }
 	}

	public Date getFirstTrialDate()
	{
	  return FirstTrialDate;
	}

	public void setManaSpecFlag(String  ManaSpecFlag )
	{
	  this.ManaSpecFlag = ManaSpecFlag;
 	}

	public String getManaSpecFlag()
	{
	  return ManaSpecFlag;
	}

	public void setBankAccNo(String  BankAccNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(BankAccNo)))){
	    	return;
  	   }else{
	    	this.BankAccNo = BankAccNo;
	  }
 	}

	public String getBankAccNo()
	{
	  return BankAccNo;
	}

	public void setContPrem( Double ContPrem )
	{
	  this.ContPrem = ContPrem;
 	}

	public Double getContPrem()
	{
	  return ContPrem;
	}

	public void setRealPrem( Double RealPrem )
	{
	  this.RealPrem = RealPrem;
 	}

	public Double getRealPrem()
	{
	  return RealPrem;
	}

	public void sethasSeenInsured(String  hasSeenInsured )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(hasSeenInsured)))){
	    	return;
  	   }else{
	    	this.hasSeenInsured = hasSeenInsured;
	  }
 	}

	public String gethasSeenInsured()
	{
	  return hasSeenInsured;
	}

	public void setImportUpdateNFlag(String  ImportUpdateNFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(ImportUpdateNFlag)))){
	    	return;
  	   }else{
	    	this.ImportUpdateNFlag = ImportUpdateNFlag;
	  }
 	}

	public String getImportUpdateNFlag()
	{
	  return ImportUpdateNFlag;
	}

	public void setInitialPayment(String  InitialPayment )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(InitialPayment)))){
	    	return;
  	   }else{
	    	this.InitialPayment = InitialPayment;
	  }
 	}

	public String getInitialPayment()
	{
	  return InitialPayment;
	}

	public void setRenewalPayment(String  RenewalPayment )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RenewalPayment)))){
	    	return;
  	   }else{
	    	this.RenewalPayment = RenewalPayment;
	  }
 	}

	public String getRenewalPayment()
	{
	  return RenewalPayment;
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

	if("UnionContCount".equals(FCode))
		{
			setUnionContCount(Double.valueOf(FValue));
		}

	if("Interval".equals(FCode))
		{
			setInterval(Double.valueOf(FValue));
		}

	if("OImportDisFlag".equals(FCode))
		{
		    setOImportDisFlag(FValue);
		}

	if("ImportUpdatePFlag".equals(FCode))
		{
		    setImportUpdatePFlag(FValue);
		}

	if("PolAppDFlag".equals(FCode))
		{
		    setPolAppDFlag(FValue);
		}

	if("HPENotice".equals(FCode))
		{
		    setHPENotice(FValue);
		}

	if("ContNo".equals(FCode))
		{
		    setContNo(FValue);
		}

	if("CardFlag".equals(FCode))
		{
		    setCardFlag(FValue);
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

	if("ManageCom".equals(FCode))
		{
		    setManageCom(FValue);
		}

	if("PayMode".equals(FCode))
		{
		    setPayMode(FValue);
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

	if("InsuYear".equals(FCode))
		{
			setInsuYear(Double.valueOf(FValue));
		}

	if("Mult".equals(FCode))
		{
			setMult(Double.valueOf(FValue));
		}

	if("PayIntv".equals(FCode))
		{
		    setPayIntv(FValue);
		}

	if("Prem".equals(FCode))
		{
			setPrem(Double.valueOf(FValue));
		}

	if("AccName".equals(FCode))
		{
		    setAccName(FValue);
		}

	if("ForceUWFlag".equals(FCode))
		{
		    setForceUWFlag(FValue);
		}

	if("InsuredPeoples".equals(FCode))
		{
			setInsuredPeoples(Double.valueOf(FValue));
		}

	if("Tempfee".equals(FCode))
		{
			setTempfee(Double.valueOf(FValue));
		}

	if("AutoPayFlag".equals(FCode))
		{
		    setAutoPayFlag(FValue);
		}

	if("RnewFlag".equals(FCode))
		{
		    setRnewFlag(FValue);
		}

	if("MakeDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setMakeDate(d);
		}

	if("ScanDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setScanDate(d);
		}

	if("HSpecFlag".equals(FCode))
		{
		    setHSpecFlag(FValue);
		}

	if("HAskFlag".equals(FCode))
		{
		    setHAskFlag(FValue);
		}

	if("StatusFlag".equals(FCode))
		{
		    setStatusFlag(FValue);
		}

	if("IllFlag".equals(FCode))
		{
		    setIllFlag(FValue);
		}

	if("OtherDataFlag".equals(FCode))
		{
		    setOtherDataFlag(FValue);
		}

	if("HospitalCodePro".equals(FCode))
		{
		    setHospitalCodePro(FValue);
		}

	if("UWFlag".equals(FCode))
		{
		    setUWFlag(FValue);
		}

	if("Amnt".equals(FCode))
		{
			setAmnt(Double.valueOf(FValue));
		}

	if("AutoUWTimes".equals(FCode))
		{
			setAutoUWTimes(Double.valueOf(FValue));
		}

	if("OutPayFlag".equals(FCode))
		{
		    setOutPayFlag(FValue);
		}

	if("BankCode".equals(FCode))
		{
		    setBankCode(FValue);
		}

	if("EndDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setEndDate(d);
		}

	if("BonusYear".equals(FCode))
		{
			setBonusYear(Double.valueOf(FValue));
		}

	if("AppntIssueFlag".equals(FCode))
		{
		    setAppntIssueFlag(FValue);
		}

	if("AgentIssueFlag".equals(FCode))
		{
		    setAgentIssueFlag(FValue);
		}

	if("ComIssueFlag".equals(FCode))
		{
		    setComIssueFlag(FValue);
		}

	if("CustomerIssueFlag".equals(FCode))
		{
		    setCustomerIssueFlag(FValue);
		}

	if("SellType".equals(FCode))
		{
		    setSellType(FValue);
		}

	if("RemarkFlag".equals(FCode))
		{
		    setRemarkFlag(FValue);
		}

	if("ChangePolFlag".equals(FCode))
		{
		    setChangePolFlag(FValue);
		}

	if("Cvalidate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setCvalidate(d);
		}

	if("AppFlag".equals(FCode))
		{
		    setAppFlag(FValue);
		}

	if("ReinImpart".equals(FCode))
		{
		    setReinImpart(FValue);
		}

	if("RemarkCount".equals(FCode))
		{
			setRemarkCount(Double.valueOf(FValue));
		}

	if("IsAppointHos".equals(FCode))
		{
		    setIsAppointHos(FValue);
		}

	if("AccoBodyCheck".equals(FCode))
		{
		    setAccoBodyCheck(FValue);
		}

	if("SpotCheckFlag".equals(FCode))
		{
		    setSpotCheckFlag(FValue);
		}

	if("MOpeIsNotDefined".equals(FCode))
		{
		    setMOpeIsNotDefined(FValue);
		}

	if("SecondaryManagecom".equals(FCode))
		{
		    setSecondaryManagecom(FValue);
		}

	if("ThirdStageManagecom".equals(FCode))
		{
		    setThirdStageManagecom(FValue);
		}

	if("SamplingFactor".equals(FCode))
		{
			setSamplingFactor(Double.valueOf(FValue));
		}

	if("UWDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setUWDate(d);
		}

	if("FourStageManagecom".equals(FCode))
		{
		    setFourStageManagecom(FValue);
		}

	if("TotalCount".equals(FCode))
		{
			setTotalCount(Double.valueOf(FValue));
		}

	if("UWLevel".equals(FCode))
		{
		    setUWLevel(FValue);
		}

	if("FirstTrialDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setFirstTrialDate(d);
		}

	if("ManaSpecFlag".equals(FCode))
		{
		    setManaSpecFlag(FValue);
		}

	if("BankAccNo".equals(FCode))
		{
		    setBankAccNo(FValue);
		}

	if("ContPrem".equals(FCode))
		{
			setContPrem(Double.valueOf(FValue));
		}

	if("RealPrem".equals(FCode))
		{
			setRealPrem(Double.valueOf(FValue));
		}

	if("hasSeenInsured".equals(FCode))
		{
		    sethasSeenInsured(FValue);
		}

	if("ImportUpdateNFlag".equals(FCode))
		{
		    setImportUpdateNFlag(FValue);
		}

	if("InitialPayment".equals(FCode))
		{
		    setInitialPayment(FValue);
		}

	if("RenewalPayment".equals(FCode))
		{
		    setRenewalPayment(FValue);
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
	  if (FCode.equalsIgnoreCase("UnionContCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUnionContCount()));
	  }
	  if (FCode.equalsIgnoreCase("Interval"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInterval()));
	  }
	  if (FCode.equalsIgnoreCase("OImportDisFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOImportDisFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ImportUpdatePFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getImportUpdatePFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PolAppDFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPolAppDFlag()));
	  }
	  if (FCode.equalsIgnoreCase("HPENotice"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHPENotice()));
	  }
	  if (FCode.equalsIgnoreCase("ContNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getContNo()));
	  }
	  if (FCode.equalsIgnoreCase("CardFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCardFlag()));
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
	  if (FCode.equalsIgnoreCase("ManageCom"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getManageCom()));
	  }
	  if (FCode.equalsIgnoreCase("PayMode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayMode()));
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
	  if (FCode.equalsIgnoreCase("InsuYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuYear()));
	  }
	  if (FCode.equalsIgnoreCase("Mult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMult()));
	  }
	  if (FCode.equalsIgnoreCase("PayIntv"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPayIntv()));
	  }
	  if (FCode.equalsIgnoreCase("Prem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPrem()));
	  }
	  if (FCode.equalsIgnoreCase("AccName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccName()));
	  }
	  if (FCode.equalsIgnoreCase("ForceUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getForceUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredPeoples"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredPeoples()));
	  }
	  if (FCode.equalsIgnoreCase("Tempfee"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTempfee()));
	  }
	  if (FCode.equalsIgnoreCase("AutoPayFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAutoPayFlag()));
	  }
	  if (FCode.equalsIgnoreCase("RnewFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRnewFlag()));
	  }
	  if (FCode.equalsIgnoreCase("MakeDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getMakeDate())));
	  }
	  if (FCode.equalsIgnoreCase("ScanDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getScanDate())));
	  }
	  if (FCode.equalsIgnoreCase("HSpecFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHSpecFlag()));
	  }
	  if (FCode.equalsIgnoreCase("HAskFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHAskFlag()));
	  }
	  if (FCode.equalsIgnoreCase("StatusFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStatusFlag()));
	  }
	  if (FCode.equalsIgnoreCase("IllFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getIllFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OtherDataFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOtherDataFlag()));
	  }
	  if (FCode.equalsIgnoreCase("HospitalCodePro"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHospitalCodePro()));
	  }
	  if (FCode.equalsIgnoreCase("UWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("Amnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("AutoUWTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAutoUWTimes()));
	  }
	  if (FCode.equalsIgnoreCase("OutPayFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOutPayFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BankCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBankCode()));
	  }
	  if (FCode.equalsIgnoreCase("EndDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getEndDate())));
	  }
	  if (FCode.equalsIgnoreCase("BonusYear"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBonusYear()));
	  }
	  if (FCode.equalsIgnoreCase("AppntIssueFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppntIssueFlag()));
	  }
	  if (FCode.equalsIgnoreCase("AgentIssueFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAgentIssueFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ComIssueFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getComIssueFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CustomerIssueFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCustomerIssueFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SellType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSellType()));
	  }
	  if (FCode.equalsIgnoreCase("RemarkFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRemarkFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ChangePolFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getChangePolFlag()));
	  }
	  if (FCode.equalsIgnoreCase("Cvalidate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getCvalidate())));
	  }
	  if (FCode.equalsIgnoreCase("AppFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ReinImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReinImpart()));
	  }
	  if (FCode.equalsIgnoreCase("RemarkCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRemarkCount()));
	  }
	  if (FCode.equalsIgnoreCase("IsAppointHos"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getIsAppointHos()));
	  }
	  if (FCode.equalsIgnoreCase("AccoBodyCheck"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccoBodyCheck()));
	  }
	  if (FCode.equalsIgnoreCase("SpotCheckFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSpotCheckFlag()));
	  }
	  if (FCode.equalsIgnoreCase("MOpeIsNotDefined"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMOpeIsNotDefined()));
	  }
	  if (FCode.equalsIgnoreCase("SecondaryManagecom"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSecondaryManagecom()));
	  }
	  if (FCode.equalsIgnoreCase("ThirdStageManagecom"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThirdStageManagecom()));
	  }
	  if (FCode.equalsIgnoreCase("SamplingFactor"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSamplingFactor()));
	  }
	  if (FCode.equalsIgnoreCase("UWDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getUWDate())));
	  }
	  if (FCode.equalsIgnoreCase("FourStageManagecom"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFourStageManagecom()));
	  }
	  if (FCode.equalsIgnoreCase("TotalCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTotalCount()));
	  }
	  if (FCode.equalsIgnoreCase("UWLevel"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWLevel()));
	  }
	  if (FCode.equalsIgnoreCase("FirstTrialDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getFirstTrialDate())));
	  }
	  if (FCode.equalsIgnoreCase("ManaSpecFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getManaSpecFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BankAccNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBankAccNo()));
	  }
	  if (FCode.equalsIgnoreCase("ContPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getContPrem()));
	  }
	  if (FCode.equalsIgnoreCase("RealPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRealPrem()));
	  }
	  if (FCode.equalsIgnoreCase("hasSeenInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(gethasSeenInsured()));
	  }
	  if (FCode.equalsIgnoreCase("ImportUpdateNFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getImportUpdateNFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InitialPayment"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInitialPayment()));
	  }
	  if (FCode.equalsIgnoreCase("RenewalPayment"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRenewalPayment()));
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
