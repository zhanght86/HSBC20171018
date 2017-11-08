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
 * <p>ClassName: BOMInsured </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-03-23
 */

public class BOMInsured extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 既往理赔核保结论 */
	private String OClaimUWFlag;

	
	private String InsuredISOperator;

	/** 既往有逾期未回复调取医疗资料记录 */
	private String WithDHispital;

	/** 既往续期核保结论 */
	private String ORNewUWFlag;

	/** 既往职业类别 */
	private String OOccupationType;

	/** 既往退保次数 */
	private Double OSurrenderTimes;

	/** 既往复效次数 */
	private Double OReTimes;

	/** 与投保人关系 */
	private String RelationToAppnt;

	/** 参加工作时间 */
	private Date StartWorkDate;

	/** 投保年龄 */
	private Double InsuredAppAge;

	/** 既往保单的核保结论是否为延期 */
	private String LateOUWFlag;

	/** 客户号码 */
	private String InsuredNo;

	/** 性别 */
	private String Sex;

	/** 国籍 */
	private String NativePlace;

	/** 民族 */
	private String Nationality;

	/** 既往理赔记录标记 */
	private String OClaimFlag;

	/** 户口所在地 */
	private String RgtAddress;

	/** 婚姻状况 */
	private String Marriage;

	/** 结婚日期 */
	private Date MarriageDate;

	/** 体重 */
	private Double Avoirdupois;

	/** 出生时体重 */
	private Double BirthAvoirdupois;

	/** 出生时身高 */
	private Double BirthStature;

	/** 学历 */
	private String Degree;

	/** 信用等级 */
	private String CreditGrade;

	/** 入司日期 */
	private Date JoinCompanyDate;

	/** 职位 */
	private String Position;

	/** 职业类别 */
	private String OccupationType;

	/** 状态 */
	private String InsuredStat;

	/** 驾照 */
	private String License;

	/** 驾照类型 */
	private String LicenseType;

	/** 社保登记号 */
	private String SocialInsuNo;

	/** 每天吸烟量 */
	private Double SmokeAmount;

	/** 吸烟标记 */
	private String SmokeFlag;

	/** 饮酒标记 */
	private String DrinkFlag;

	/** 待复核投保单数 */
	private Double ApprovePolCount;

	/** 待核保投保单数 */
	private Double UWPolCount;

	/** 累计寿险风险保额 */
	private Double LifeSumAmnt;

	/** 累计重疾风险保额 */
	private Double DiseaseSumAmnt;

	/** 累计意外伤害风险保额 */
	private Double AccidentSumAmnt;

	/** 累计意外医疗风险保额 */
	private Double MedicalSumAmnt;

	/** 累计投保份数 */
	private String SumMult;

	/** 既往投保加费次数 */
	private Double OAddFeeTimes;

	/** 危险运动爱好 */
	private String DanSportInter;

	/** 交通事故告知 */
	private String TrafAccImpart;

	/** 出国意向告知 */
	private String AbroadImpart;

	/** 交通事故记录 */
	private String TrafficAccident;

	/** 康顺老年意外合同份数 */
	private Double SumKSCont;

	/** 附加每日住院给付保险合同份数 */
	private Double SumMRZYCont;

	/** 既往告知事项不全为否 */
	private String OImpart;

	/** 怀孕周数 */
	private Double PregnancyWeeks;

	/** 吸烟年数 */
	private Double SmokeYears;

	/** 饮酒年数 */
	private Double DrinkYears;

	/** 饮酒类型 */
	private String DrinkType;

	/** 孕妇告知 */
	private String ISPregnancy;

	/** 年收入 */
	private Double YearIncome;

	/** 残疾事项告知 */
	private String DisabilityImpart;

	/** 既往保单失效次数 */
	private Double OInvaliTimes;

	/** 职业代码 */
	private String OccupationCode;

	/** 一年内职业代码是否一致 */
	private String SameOccuCode;

	/** 金玉满堂系列产品份数 */
	private Double SumJYMTCount;

	/** 富贵盈门系列产品保费 */
	private Double SumFGYMPrem;

	/** 既往保单体检标记 */
	private String OHealthFlag;

	/** 既往拒保次数 */
	private Double ORejeceTimes;

	/** 既往延期承保次数 */
	private Double ODeferTimes;

	/** 既往撤件次数 */
	private Double OWithdrawTimes;

	/** 既往承保计划变更次数 */
	private Double OChangeFlag;

	/** 既往保单合同解除次数 */
	private Double OReleveTimes;

	/** 工资 */
	private Double Salary;

	/** 职业代码医疗险费率比例 */
	private Double OMRate;

	/** 妇科告知不全为否 */
	private String FemaleConTent;

	/** 婴儿告知不为否 */
	private String BabyConTent;

	/** 家族史不全为否 */
	private String FamilyConTent;

	/** 既往异常投保/理赔史 */
	private String OuncommonConTent;

	/** 既往职业类别高于本次 */
	private String OOMaxType;

	/** 体重是否符合标准范围 */
	private String AvoirdupoisFlag;

	/** 身高是否符合标准范围 */
	private String StatureFlag;

	/** 身故受益人是否录入 */
	private String DieBnfFlag;

	/** 是否达到再保呈报标准 */
	private String ZBFlag;

	/** 需要体检 */
	private String NeedPEN;

	/** 告知编码 */
	private String ImpartCode;

	/** 既往投保的职业医疗险费率 */
	private Double OMedRate;

	/** 生调标记 */
	private String ContenteFlag;

	/** 第一顺序生存受益人受益比例之和 */
	private Double FirstSurviveBnfSum;

	/** 体检标记 */
	private String HealthFlag;

	/** 第二顺序生存受益人受益比例之和 */
	private Double SecondSurviveBnfSum;

	/** 第三顺序生存受益人受益比例之和 */
	private Double ThirdSurviveBnfSum;

	/** 第四顺序生存受益人受益比例之和 */
	private Double FourthSurviveBnfSum;

	/** 第一顺序身故受益人受益比例之和 */
	private Double FirstDieBnfSum;

	/** 第二顺序身故受益人受益比例之和 */
	private Double SecondDieBnfSum;

	/** 第三顺序身故受益人受益比例之和 */
	private Double ThirdDieBnfSum;

	/** 第四顺序身故受益人受益比例之和 */
	private Double FourthDieBnfSum;

	/** 既往投保记录是否为拒保类型职业 */
	private String NOOCPType;

	/** 既往投保记录是否为-1级 */
	private String NegativeOCPType;

	/** 既往投保记录是否为六级 */
	private String SixthOCPType;

	/** 既往投保记录是否为五级 */
	private String FifthOCPType;

	/** 既往投保记录是否为四级 */
	private String FourthOCPType;

	/** 既往投保记录是否为三级 */
	private String ThirdOCPType;

	/** 既往投保记录是否为二级 */
	private String SecondOCPType;

	/** 累计风险保额 */
	private Double AddRiskPrem;

	/** 既往投保记录是否为一级 */
	private String FirstOCPType;

	/** 既往保单核保结论是否为撤保 */
	private String BackOUWFlag;

	/** 既往保单的核保结论是否为标准承保 */
	private String StandarOUWFlag;

	/** 既往保单核保结论是否为拒保 */
	private String RejectOUWFlag;

	/** 既往两年内发生报案但未立案标记 */
	private String tORPTnoRGT;

	/** 体检医院有黑名单标记 */
	private String InsHosBlack;

	/** 体检医院名称 */
	private String InsHopName;

	/** 是否有商业因素标准体承保记录 */
	private String ComrAppFlag;

	/** 身高体重指数是否符合标准范围 */
	private String BMIFlag;

	/** 每天饮酒量 */
	private Double Drink;

	/** 身故受益人是被保人本人 */
	private String DieBnfSelf;

	/** 健康告知不全为否 */
	private String HealthTellConTent;

	/** 既往保单核保结论是否为加费承保 */
	private String AddOUWFlag;

	/** 既往保单核保结论是否为特约承保 */
	private String SpecialOUWFlag;

	/** 累计年金险保额 */
	private Double SumAnnAmnt;

	/** 累计医疗险保额 */
	private Double SumMedAmnt;

	/** 累计身故风险保额 */
	private Double SumDieAmnt;

	/** 住院费用补偿险保额 */
	private Double SumZYAmnt;

	/** 康顺意外伤害保险保额 */
	private Double SumKSAmnt;

	/** 康顺老年意外份数 */
	private Double SumKSMult;

	/** 既往承保记录有次标准体 */
	private String OUWFlag4;

	/** 立案记录 */
	private String ORegister;

	/** 二年内既往报案记录 */
	private String OReportFlag;

	/** 既往未同意体检记录 */
	private String OPENotice;

	/** 既往未同意生调记录 */
	private String OMeetYN;

	/** 既往未同意加费记录 */
	private String OAddFeeYN;

	/** 既往未同意特约记录 */
	private String OSpecYN;

	/** 既往未同意保险计划变更记录 */
	private String OChangePlanYN;

	/** 既往逾期未体检记录 */
	private String OLatePENoticeYN;

	/** 既往逾期未生调 */
	private String OLateMeetYN;

	/** 既往逾期未加费记录 */
	private String OLateAddFeeYN;

	/** 既往逾期未回复特约记录 */
	private String OLateSpecYN;

	/** 逾期未回复保险计划变更记录 */
	private String OLateChangePlanYN;

	/** 既往有其他撤单记录 */
	private String OOtherOver;

	/** 本次或既往体检结果异常记录 */
	private String PENoticeError;

	/** 既往主险退保次数 */
	private Double OMainPol;

	/** 正在申请保全核保且尚未有结论 */
	private String EdorUWFlag;

	/** 额外死亡率 */
	private Double EM;

	/** 正在申请理赔核保且尚未有结论 */
	private String ClaimUWFlag;

	/** 正在申请续期核保且尚未有结论 */
	private String ReNewUWFlag;

	/** 既往保全核保结论 */
	private String OEdorUWFlag;

	/** 生存金领取开始年龄 */
	private Double GetStartAge;

	/** 生存金领取时年龄 */
	private Double GetAge;

	/** 有体检结果异常 */
	private String HealthPro;

	/** 黑名单标记 */
	private String BlackListFlag;

	/** 既往投保体检次数 */
	private Double OHealthTimes;

	/** 出生日期 */
	private Date Birthday;

	/** 身高 */
	private Double Stature;

	/** BMI值 */
	private Double BMI;

	/** 生存受益人为本人姓名不一致标记 */
	private String LiveBnfSelf;

	/** 愈期未交费撤单记录并有次标准记录 */
	private String HaveNoFeeWithD;

	/** 第二被保人加费评点 */
	private Double SecInsuAddPoint;

	/** 救援服务保险金 */
	private Double SaveAmnt;

	/** 保险计划编码 */
	private String ContPlanCode;



	// @Constructor
	public BOMInsured()
	{  }

	public void setOClaimUWFlag(String  OClaimUWFlag )
	{
	  this.OClaimUWFlag = OClaimUWFlag;
 	}

	public String getOClaimUWFlag()
	{
	  return OClaimUWFlag;
	}

	public void setInsuredISOperator(String  InsuredISOperator )
	{
	  this.InsuredISOperator = InsuredISOperator;
 	}

	public String getInsuredISOperator()
	{
	  return InsuredISOperator;
	}

	public void setWithDHispital(String  WithDHispital )
	{
	  this.WithDHispital = WithDHispital;
 	}

	public String getWithDHispital()
	{
	  return WithDHispital;
	}

	public void setORNewUWFlag(String  ORNewUWFlag )
	{
	  this.ORNewUWFlag = ORNewUWFlag;
 	}

	public String getORNewUWFlag()
	{
	  return ORNewUWFlag;
	}

	public void setOOccupationType(String  OOccupationType )
	{
	  this.OOccupationType = OOccupationType;
 	}

	public String getOOccupationType()
	{
	  return OOccupationType;
	}

	public void setOSurrenderTimes( Double OSurrenderTimes )
	{
	   if(!(new BOMPreCheck().CheckPI(OSurrenderTimes))){
	  	  return;
	    }else{
	    	this.OSurrenderTimes = OSurrenderTimes;
	   }
 	}

	public Double getOSurrenderTimes()
	{
	  return OSurrenderTimes;
	}

	public void setOReTimes( Double OReTimes )
	{
	   if(!(new BOMPreCheck().CheckPI(OReTimes))){
	  	  return;
	    }else{
	    	this.OReTimes = OReTimes;
	   }
 	}

	public Double getOReTimes()
	{
	  return OReTimes;
	}

	public void setRelationToAppnt(String  RelationToAppnt )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RelationToAppnt)))){
	    	return;
  	   }else{
	    	this.RelationToAppnt = RelationToAppnt;
	  }
 	}

	public String getRelationToAppnt()
	{
	  return RelationToAppnt;
	}

	public void setStartWorkDate(Date  StartWorkDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(StartWorkDate)))){
	    	return;
  	   }else{
	    	this.StartWorkDate = StartWorkDate;
	  }
 	}

	public Date getStartWorkDate()
	{
	  return StartWorkDate;
	}

	public void setInsuredAppAge( Double InsuredAppAge )
	{
	   if(!(new BOMPreCheck().CheckPI(InsuredAppAge))){
	  	  return;
	    }else{
	    	this.InsuredAppAge = InsuredAppAge;
	   }
 	}

	public Double getInsuredAppAge()
	{
	  return InsuredAppAge;
	}

	public void setLateOUWFlag(String  LateOUWFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(LateOUWFlag)))){
	    	return;
  	   }else{
	    	this.LateOUWFlag = LateOUWFlag;
	  }
 	}

	public String getLateOUWFlag()
	{
	  return LateOUWFlag;
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

	public void setSex(String  Sex )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(Sex)))){
	    	return;
  	   }else{
	    	this.Sex = Sex;
	  }
 	}

	public String getSex()
	{
	  return Sex;
	}

	public void setNativePlace(String  NativePlace )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(NativePlace)))){
	    	return;
  	   }else{
	    	this.NativePlace = NativePlace;
	  }
 	}

	public String getNativePlace()
	{
	  return NativePlace;
	}

	public void setNationality(String  Nationality )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(Nationality)))){
	    	return;
  	   }else{
	    	this.Nationality = Nationality;
	  }
 	}

	public String getNationality()
	{
	  return Nationality;
	}

	public void setOClaimFlag(String  OClaimFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OClaimFlag)))){
	    	return;
  	   }else{
	    	this.OClaimFlag = OClaimFlag;
	  }
 	}

	public String getOClaimFlag()
	{
	  return OClaimFlag;
	}

	public void setRgtAddress(String  RgtAddress )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RgtAddress)))){
	    	return;
  	   }else{
	    	this.RgtAddress = RgtAddress;
	  }
 	}

	public String getRgtAddress()
	{
	  return RgtAddress;
	}

	public void setMarriage(String  Marriage )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(Marriage)))){
	    	return;
  	   }else{
	    	this.Marriage = Marriage;
	  }
 	}

	public String getMarriage()
	{
	  return Marriage;
	}

	public void setMarriageDate(Date  MarriageDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(MarriageDate)))){
	    	return;
  	   }else{
	    	this.MarriageDate = MarriageDate;
	  }
 	}

	public Date getMarriageDate()
	{
	  return MarriageDate;
	}

	public void setAvoirdupois( Double Avoirdupois )
	{
	   if(!(new BOMPreCheck().CheckPlus(Avoirdupois))){
	  	  return;
	    }else{
	    	this.Avoirdupois = Avoirdupois;
	   }
 	}

	public Double getAvoirdupois()
	{
	  return Avoirdupois;
	}

	public void setBirthAvoirdupois( Double BirthAvoirdupois )
	{
	   if(!(new BOMPreCheck().CheckPlus(BirthAvoirdupois))){
	  	  return;
	    }else{
	    	this.BirthAvoirdupois = BirthAvoirdupois;
	   }
 	}

	public Double getBirthAvoirdupois()
	{
	  return BirthAvoirdupois;
	}

	public void setBirthStature( Double BirthStature )
	{
	   if(!(new BOMPreCheck().CheckPlus(BirthStature))){
	  	  return;
	    }else{
	    	this.BirthStature = BirthStature;
	   }
 	}

	public Double getBirthStature()
	{
	  return BirthStature;
	}

	public void setDegree(String  Degree )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(Degree)))){
	    	return;
  	   }else{
	    	this.Degree = Degree;
	  }
 	}

	public String getDegree()
	{
	  return Degree;
	}

	public void setCreditGrade(String  CreditGrade )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(CreditGrade)))){
	    	return;
  	   }else{
	    	this.CreditGrade = CreditGrade;
	  }
 	}

	public String getCreditGrade()
	{
	  return CreditGrade;
	}

	public void setJoinCompanyDate(Date  JoinCompanyDate )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(JoinCompanyDate)))){
	    	return;
  	   }else{
	    	this.JoinCompanyDate = JoinCompanyDate;
	  }
 	}

	public Date getJoinCompanyDate()
	{
	  return JoinCompanyDate;
	}

	public void setPosition(String  Position )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(Position)))){
	    	return;
  	   }else{
	    	this.Position = Position;
	  }
 	}

	public String getPosition()
	{
	  return Position;
	}

	public void setOccupationType(String  OccupationType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OccupationType)))){
	    	return;
  	   }else{
	    	this.OccupationType = OccupationType;
	  }
 	}

	public String getOccupationType()
	{
	  return OccupationType;
	}

	public void setInsuredStat(String  InsuredStat )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(InsuredStat)))){
	    	return;
  	   }else{
	    	this.InsuredStat = InsuredStat;
	  }
 	}

	public String getInsuredStat()
	{
	  return InsuredStat;
	}

	public void setLicense(String  License )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(License)))){
	    	return;
  	   }else{
	    	this.License = License;
	  }
 	}

	public String getLicense()
	{
	  return License;
	}

	public void setLicenseType(String  LicenseType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(LicenseType)))){
	    	return;
  	   }else{
	    	this.LicenseType = LicenseType;
	  }
 	}

	public String getLicenseType()
	{
	  return LicenseType;
	}

	public void setSocialInsuNo(String  SocialInsuNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(SocialInsuNo)))){
	    	return;
  	   }else{
	    	this.SocialInsuNo = SocialInsuNo;
	  }
 	}

	public String getSocialInsuNo()
	{
	  return SocialInsuNo;
	}

	public void setSmokeAmount( Double SmokeAmount )
	{
	   if(!(new BOMPreCheck().CheckIngeter(SmokeAmount))){
	  	  return;
	    }else{
	    	this.SmokeAmount = SmokeAmount;
	   }
 	}

	public Double getSmokeAmount()
	{
	  return SmokeAmount;
	}

	public void setSmokeFlag(String  SmokeFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(SmokeFlag)))){
	    	return;
  	   }else{
	    	this.SmokeFlag = SmokeFlag;
	  }
 	}

	public String getSmokeFlag()
	{
	  return SmokeFlag;
	}

	public void setDrinkFlag(String  DrinkFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(DrinkFlag)))){
	    	return;
  	   }else{
	    	this.DrinkFlag = DrinkFlag;
	  }
 	}

	public String getDrinkFlag()
	{
	  return DrinkFlag;
	}

	public void setApprovePolCount( Double ApprovePolCount )
	{
	   if(!(new BOMPreCheck().CheckIngeter(ApprovePolCount))){
	  	  return;
	    }else{
	    	this.ApprovePolCount = ApprovePolCount;
	   }
 	}

	public Double getApprovePolCount()
	{
	  return ApprovePolCount;
	}

	public void setUWPolCount( Double UWPolCount )
	{
	   if(!(new BOMPreCheck().CheckIngeter(UWPolCount))){
	  	  return;
	    }else{
	    	this.UWPolCount = UWPolCount;
	   }
 	}

	public Double getUWPolCount()
	{
	  return UWPolCount;
	}

	public void setLifeSumAmnt( Double LifeSumAmnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(LifeSumAmnt))){
	  	  return;
	    }else{
	    	this.LifeSumAmnt = LifeSumAmnt;
	   }
 	}

	public Double getLifeSumAmnt()
	{
	  return LifeSumAmnt;
	}

	public void setDiseaseSumAmnt( Double DiseaseSumAmnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(DiseaseSumAmnt))){
	  	  return;
	    }else{
	    	this.DiseaseSumAmnt = DiseaseSumAmnt;
	   }
 	}

	public Double getDiseaseSumAmnt()
	{
	  return DiseaseSumAmnt;
	}

	public void setAccidentSumAmnt( Double AccidentSumAmnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(AccidentSumAmnt))){
	  	  return;
	    }else{
	    	this.AccidentSumAmnt = AccidentSumAmnt;
	   }
 	}

	public Double getAccidentSumAmnt()
	{
	  return AccidentSumAmnt;
	}

	public void setMedicalSumAmnt( Double MedicalSumAmnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(MedicalSumAmnt))){
	  	  return;
	    }else{
	    	this.MedicalSumAmnt = MedicalSumAmnt;
	   }
 	}

	public Double getMedicalSumAmnt()
	{
	  return MedicalSumAmnt;
	}

	public void setSumMult(String  SumMult )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(SumMult)))){
	    	return;
  	   }else{
	    	this.SumMult = SumMult;
	  }
 	}

	public String getSumMult()
	{
	  return SumMult;
	}

	public void setOAddFeeTimes( Double OAddFeeTimes )
	{
	   if(!(new BOMPreCheck().CheckIngeter(OAddFeeTimes))){
	  	  return;
	    }else{
	    	this.OAddFeeTimes = OAddFeeTimes;
	   }
 	}

	public Double getOAddFeeTimes()
	{
	  return OAddFeeTimes;
	}

	public void setDanSportInter(String  DanSportInter )
	{
	  this.DanSportInter = DanSportInter;
 	}

	public String getDanSportInter()
	{
	  return DanSportInter;
	}

	public void setTrafAccImpart(String  TrafAccImpart )
	{
	  this.TrafAccImpart = TrafAccImpart;
 	}

	public String getTrafAccImpart()
	{
	  return TrafAccImpart;
	}

	public void setAbroadImpart(String  AbroadImpart )
	{
	  this.AbroadImpart = AbroadImpart;
 	}

	public String getAbroadImpart()
	{
	  return AbroadImpart;
	}

	public void setTrafficAccident(String  TrafficAccident )
	{
	  this.TrafficAccident = TrafficAccident;
 	}

	public String getTrafficAccident()
	{
	  return TrafficAccident;
	}

	public void setSumKSCont( Double SumKSCont )
	{
	  this.SumKSCont = SumKSCont;
 	}

	public Double getSumKSCont()
	{
	  return SumKSCont;
	}

	public void setSumMRZYCont( Double SumMRZYCont )
	{
	  this.SumMRZYCont = SumMRZYCont;
 	}

	public Double getSumMRZYCont()
	{
	  return SumMRZYCont;
	}

	public void setOImpart(String  OImpart )
	{
	  this.OImpart = OImpart;
 	}

	public String getOImpart()
	{
	  return OImpart;
	}

	public void setPregnancyWeeks( Double PregnancyWeeks )
	{
	  this.PregnancyWeeks = PregnancyWeeks;
 	}

	public Double getPregnancyWeeks()
	{
	  return PregnancyWeeks;
	}

	public void setSmokeYears( Double SmokeYears )
	{
	  this.SmokeYears = SmokeYears;
 	}

	public Double getSmokeYears()
	{
	  return SmokeYears;
	}

	public void setDrinkYears( Double DrinkYears )
	{
	  this.DrinkYears = DrinkYears;
 	}

	public Double getDrinkYears()
	{
	  return DrinkYears;
	}

	public void setDrinkType(String  DrinkType )
	{
	  this.DrinkType = DrinkType;
 	}

	public String getDrinkType()
	{
	  return DrinkType;
	}

	public void setISPregnancy(String  ISPregnancy )
	{
	  this.ISPregnancy = ISPregnancy;
 	}

	public String getISPregnancy()
	{
	  return ISPregnancy;
	}

	public void setYearIncome( Double YearIncome )
	{
	  this.YearIncome = YearIncome;
 	}

	public Double getYearIncome()
	{
	  return YearIncome;
	}

	public void setDisabilityImpart(String  DisabilityImpart )
	{
	  this.DisabilityImpart = DisabilityImpart;
 	}

	public String getDisabilityImpart()
	{
	  return DisabilityImpart;
	}

	public void setOInvaliTimes( Double OInvaliTimes )
	{
	   if(!(new BOMPreCheck().CheckIngeter(OInvaliTimes))){
	  	  return;
	    }else{
	    	this.OInvaliTimes = OInvaliTimes;
	   }
 	}

	public Double getOInvaliTimes()
	{
	  return OInvaliTimes;
	}

	public void setOccupationCode(String  OccupationCode )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OccupationCode)))){
	    	return;
  	   }else{
	    	this.OccupationCode = OccupationCode;
	  }
 	}

	public String getOccupationCode()
	{
	  return OccupationCode;
	}

	public void setSameOccuCode(String  SameOccuCode )
	{
	  this.SameOccuCode = SameOccuCode;
 	}

	public String getSameOccuCode()
	{
	  return SameOccuCode;
	}

	public void setSumJYMTCount( Double SumJYMTCount )
	{
	  this.SumJYMTCount = SumJYMTCount;
 	}

	public Double getSumJYMTCount()
	{
	  return SumJYMTCount;
	}

	public void setSumFGYMPrem( Double SumFGYMPrem )
	{
	  this.SumFGYMPrem = SumFGYMPrem;
 	}

	public Double getSumFGYMPrem()
	{
	  return SumFGYMPrem;
	}

	public void setOHealthFlag(String  OHealthFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(OHealthFlag)))){
	    	return;
  	   }else{
	    	this.OHealthFlag = OHealthFlag;
	  }
 	}

	public String getOHealthFlag()
	{
	  return OHealthFlag;
	}

	public void setORejeceTimes( Double ORejeceTimes )
	{
	   if(!(new BOMPreCheck().CheckIngeter(ORejeceTimes))){
	  	  return;
	    }else{
	    	this.ORejeceTimes = ORejeceTimes;
	   }
 	}

	public Double getORejeceTimes()
	{
	  return ORejeceTimes;
	}

	public void setODeferTimes( Double ODeferTimes )
	{
	   if(!(new BOMPreCheck().CheckIngeter(ODeferTimes))){
	  	  return;
	    }else{
	    	this.ODeferTimes = ODeferTimes;
	   }
 	}

	public Double getODeferTimes()
	{
	  return ODeferTimes;
	}

	public void setOWithdrawTimes( Double OWithdrawTimes )
	{
	   if(!(new BOMPreCheck().CheckIngeter(OWithdrawTimes))){
	  	  return;
	    }else{
	    	this.OWithdrawTimes = OWithdrawTimes;
	   }
 	}

	public Double getOWithdrawTimes()
	{
	  return OWithdrawTimes;
	}

	public void setOChangeFlag( Double OChangeFlag )
	{
	   if(!(new BOMPreCheck().CheckIngeter(OChangeFlag))){
	  	  return;
	    }else{
	    	this.OChangeFlag = OChangeFlag;
	   }
 	}

	public Double getOChangeFlag()
	{
	  return OChangeFlag;
	}

	public void setOReleveTimes( Double OReleveTimes )
	{
	   if(!(new BOMPreCheck().CheckIngeter(OReleveTimes))){
	  	  return;
	    }else{
	    	this.OReleveTimes = OReleveTimes;
	   }
 	}

	public Double getOReleveTimes()
	{
	  return OReleveTimes;
	}

	public void setSalary( Double Salary )
	{
	   if(!(new BOMPreCheck().CheckPlus(Salary))){
	  	  return;
	    }else{
	    	this.Salary = Salary;
	   }
 	}

	public Double getSalary()
	{
	  return Salary;
	}

	public void setOMRate( Double OMRate )
	{
	  this.OMRate = OMRate;
 	}

	public Double getOMRate()
	{
	  return OMRate;
	}

	public void setFemaleConTent(String  FemaleConTent )
	{
	  this.FemaleConTent = FemaleConTent;
 	}

	public String getFemaleConTent()
	{
	  return FemaleConTent;
	}

	public void setBabyConTent(String  BabyConTent )
	{
	  this.BabyConTent = BabyConTent;
 	}

	public String getBabyConTent()
	{
	  return BabyConTent;
	}

	public void setFamilyConTent(String  FamilyConTent )
	{
	  this.FamilyConTent = FamilyConTent;
 	}

	public String getFamilyConTent()
	{
	  return FamilyConTent;
	}

	public void setOuncommonConTent(String  OuncommonConTent )
	{
	  this.OuncommonConTent = OuncommonConTent;
 	}

	public String getOuncommonConTent()
	{
	  return OuncommonConTent;
	}

	public void setOOMaxType(String  OOMaxType )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(OOMaxType)))){
	    	return;
  	   }else{
	    	this.OOMaxType = OOMaxType;
	  }
 	}

	public String getOOMaxType()
	{
	  return OOMaxType;
	}

	public void setAvoirdupoisFlag(String  AvoirdupoisFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AvoirdupoisFlag)))){
	    	return;
  	   }else{
	    	this.AvoirdupoisFlag = AvoirdupoisFlag;
	  }
 	}

	public String getAvoirdupoisFlag()
	{
	  return AvoirdupoisFlag;
	}

	public void setStatureFlag(String  StatureFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(StatureFlag)))){
	    	return;
  	   }else{
	    	this.StatureFlag = StatureFlag;
	  }
 	}

	public String getStatureFlag()
	{
	  return StatureFlag;
	}

	public void setDieBnfFlag(String  DieBnfFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(DieBnfFlag)))){
	    	return;
  	   }else{
	    	this.DieBnfFlag = DieBnfFlag;
	  }
 	}

	public String getDieBnfFlag()
	{
	  return DieBnfFlag;
	}

	public void setZBFlag(String  ZBFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ZBFlag)))){
	    	return;
  	   }else{
	    	this.ZBFlag = ZBFlag;
	  }
 	}

	public String getZBFlag()
	{
	  return ZBFlag;
	}

	public void setNeedPEN(String  NeedPEN )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(NeedPEN)))){
	    	return;
  	   }else{
	    	this.NeedPEN = NeedPEN;
	  }
 	}

	public String getNeedPEN()
	{
	  return NeedPEN;
	}

	public void setImpartCode(String  ImpartCode )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ImpartCode)))){
	    	return;
  	   }else{
	    	this.ImpartCode = ImpartCode;
	  }
 	}

	public String getImpartCode()
	{
	  return ImpartCode;
	}

	public void setOMedRate( Double OMedRate )
	{
	  this.OMedRate = OMedRate;
 	}

	public Double getOMedRate()
	{
	  return OMedRate;
	}

	public void setContenteFlag(String  ContenteFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(ContenteFlag)))){
	    	return;
  	   }else{
	    	this.ContenteFlag = ContenteFlag;
	  }
 	}

	public String getContenteFlag()
	{
	  return ContenteFlag;
	}

	public void setFirstSurviveBnfSum( Double FirstSurviveBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(FirstSurviveBnfSum))){
	  	  return;
	    }else{
	    	this.FirstSurviveBnfSum = FirstSurviveBnfSum;
	   }
 	}

	public Double getFirstSurviveBnfSum()
	{
	  return FirstSurviveBnfSum;
	}

	public void setHealthFlag(String  HealthFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(HealthFlag)))){
	    	return;
  	   }else{
	    	this.HealthFlag = HealthFlag;
	  }
 	}

	public String getHealthFlag()
	{
	  return HealthFlag;
	}

	public void setSecondSurviveBnfSum( Double SecondSurviveBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(SecondSurviveBnfSum))){
	  	  return;
	    }else{
	    	this.SecondSurviveBnfSum = SecondSurviveBnfSum;
	   }
 	}

	public Double getSecondSurviveBnfSum()
	{
	  return SecondSurviveBnfSum;
	}

	public void setThirdSurviveBnfSum( Double ThirdSurviveBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(ThirdSurviveBnfSum))){
	  	  return;
	    }else{
	    	this.ThirdSurviveBnfSum = ThirdSurviveBnfSum;
	   }
 	}

	public Double getThirdSurviveBnfSum()
	{
	  return ThirdSurviveBnfSum;
	}

	public void setFourthSurviveBnfSum( Double FourthSurviveBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(FourthSurviveBnfSum))){
	  	  return;
	    }else{
	    	this.FourthSurviveBnfSum = FourthSurviveBnfSum;
	   }
 	}

	public Double getFourthSurviveBnfSum()
	{
	  return FourthSurviveBnfSum;
	}

	public void setFirstDieBnfSum( Double FirstDieBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(FirstDieBnfSum))){
	  	  return;
	    }else{
	    	this.FirstDieBnfSum = FirstDieBnfSum;
	   }
 	}

	public Double getFirstDieBnfSum()
	{
	  return FirstDieBnfSum;
	}

	public void setSecondDieBnfSum( Double SecondDieBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(SecondDieBnfSum))){
	  	  return;
	    }else{
	    	this.SecondDieBnfSum = SecondDieBnfSum;
	   }
 	}

	public Double getSecondDieBnfSum()
	{
	  return SecondDieBnfSum;
	}

	public void setThirdDieBnfSum( Double ThirdDieBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(ThirdDieBnfSum))){
	  	  return;
	    }else{
	    	this.ThirdDieBnfSum = ThirdDieBnfSum;
	   }
 	}

	public Double getThirdDieBnfSum()
	{
	  return ThirdDieBnfSum;
	}

	public void setFourthDieBnfSum( Double FourthDieBnfSum )
	{
	   if(!(new BOMPreCheck().CheckPlus(FourthDieBnfSum))){
	  	  return;
	    }else{
	    	this.FourthDieBnfSum = FourthDieBnfSum;
	   }
 	}

	public Double getFourthDieBnfSum()
	{
	  return FourthDieBnfSum;
	}

	public void setNOOCPType(String  NOOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(NOOCPType)))){
	    	return;
  	   }else{
	    	this.NOOCPType = NOOCPType;
	  }
 	}

	public String getNOOCPType()
	{
	  return NOOCPType;
	}

	public void setNegativeOCPType(String  NegativeOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(NegativeOCPType)))){
	    	return;
  	   }else{
	    	this.NegativeOCPType = NegativeOCPType;
	  }
 	}

	public String getNegativeOCPType()
	{
	  return NegativeOCPType;
	}

	public void setSixthOCPType(String  SixthOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(SixthOCPType)))){
	    	return;
  	   }else{
	    	this.SixthOCPType = SixthOCPType;
	  }
 	}

	public String getSixthOCPType()
	{
	  return SixthOCPType;
	}

	public void setFifthOCPType(String  FifthOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(FifthOCPType)))){
	    	return;
  	   }else{
	    	this.FifthOCPType = FifthOCPType;
	  }
 	}

	public String getFifthOCPType()
	{
	  return FifthOCPType;
	}

	public void setFourthOCPType(String  FourthOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(FourthOCPType)))){
	    	return;
  	   }else{
	    	this.FourthOCPType = FourthOCPType;
	  }
 	}

	public String getFourthOCPType()
	{
	  return FourthOCPType;
	}

	public void setThirdOCPType(String  ThirdOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(ThirdOCPType)))){
	    	return;
  	   }else{
	    	this.ThirdOCPType = ThirdOCPType;
	  }
 	}

	public String getThirdOCPType()
	{
	  return ThirdOCPType;
	}

	public void setSecondOCPType(String  SecondOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(SecondOCPType)))){
	    	return;
  	   }else{
	    	this.SecondOCPType = SecondOCPType;
	  }
 	}

	public String getSecondOCPType()
	{
	  return SecondOCPType;
	}

	public void setAddRiskPrem( Double AddRiskPrem )
	{
	  this.AddRiskPrem = AddRiskPrem;
 	}

	public Double getAddRiskPrem()
	{
	  return AddRiskPrem;
	}

	public void setFirstOCPType(String  FirstOCPType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(FirstOCPType)))){
	    	return;
  	   }else{
	    	this.FirstOCPType = FirstOCPType;
	  }
 	}

	public String getFirstOCPType()
	{
	  return FirstOCPType;
	}

	public void setBackOUWFlag(String  BackOUWFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(BackOUWFlag)))){
	    	return;
  	   }else{
	    	this.BackOUWFlag = BackOUWFlag;
	  }
 	}

	public String getBackOUWFlag()
	{
	  return BackOUWFlag;
	}

	public void setStandarOUWFlag(String  StandarOUWFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(StandarOUWFlag)))){
	    	return;
  	   }else{
	    	this.StandarOUWFlag = StandarOUWFlag;
	  }
 	}

	public String getStandarOUWFlag()
	{
	  return StandarOUWFlag;
	}

	public void setRejectOUWFlag(String  RejectOUWFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(RejectOUWFlag)))){
	    	return;
  	   }else{
	    	this.RejectOUWFlag = RejectOUWFlag;
	  }
 	}

	public String getRejectOUWFlag()
	{
	  return RejectOUWFlag;
	}

	public void settORPTnoRGT(String  tORPTnoRGT )
	{
	  this.tORPTnoRGT = tORPTnoRGT;
 	}

	public String gettORPTnoRGT()
	{
	  return tORPTnoRGT;
	}

	public void setInsHosBlack(String  InsHosBlack )
	{
	  this.InsHosBlack = InsHosBlack;
 	}

	public String getInsHosBlack()
	{
	  return InsHosBlack;
	}

	public void setInsHopName(String  InsHopName )
	{
	  this.InsHopName = InsHopName;
 	}

	public String getInsHopName()
	{
	  return InsHopName;
	}

	public void setComrAppFlag(String  ComrAppFlag )
	{
	  this.ComrAppFlag = ComrAppFlag;
 	}

	public String getComrAppFlag()
	{
	  return ComrAppFlag;
	}

	public void setBMIFlag(String  BMIFlag )
	{
	  this.BMIFlag = BMIFlag;
 	}

	public String getBMIFlag()
	{
	  return BMIFlag;
	}

	public void setDrink( Double Drink )
	{
	   if(!(new BOMPreCheck().CheckIngeter(Drink))){
	  	  return;
	    }else{
	    	this.Drink = Drink;
	   }
 	}

	public Double getDrink()
	{
	  return Drink;
	}

	public void setDieBnfSelf(String  DieBnfSelf )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(DieBnfSelf)))){
	    	return;
  	   }else{
	    	this.DieBnfSelf = DieBnfSelf;
	  }
 	}

	public String getDieBnfSelf()
	{
	  return DieBnfSelf;
	}

	public void setHealthTellConTent(String  HealthTellConTent )
	{
	  this.HealthTellConTent = HealthTellConTent;
 	}

	public String getHealthTellConTent()
	{
	  return HealthTellConTent;
	}

	public void setAddOUWFlag(String  AddOUWFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AddOUWFlag)))){
	    	return;
  	   }else{
	    	this.AddOUWFlag = AddOUWFlag;
	  }
 	}

	public String getAddOUWFlag()
	{
	  return AddOUWFlag;
	}

	public void setSpecialOUWFlag(String  SpecialOUWFlag )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(SpecialOUWFlag)))){
	    	return;
  	   }else{
	    	this.SpecialOUWFlag = SpecialOUWFlag;
	  }
 	}

	public String getSpecialOUWFlag()
	{
	  return SpecialOUWFlag;
	}

	public void setSumAnnAmnt( Double SumAnnAmnt )
	{
	  this.SumAnnAmnt = SumAnnAmnt;
 	}

	public Double getSumAnnAmnt()
	{
	  return SumAnnAmnt;
	}

	public void setSumMedAmnt( Double SumMedAmnt )
	{
	  this.SumMedAmnt = SumMedAmnt;
 	}

	public Double getSumMedAmnt()
	{
	  return SumMedAmnt;
	}

	public void setSumDieAmnt( Double SumDieAmnt )
	{
	  this.SumDieAmnt = SumDieAmnt;
 	}

	public Double getSumDieAmnt()
	{
	  return SumDieAmnt;
	}

	public void setSumZYAmnt( Double SumZYAmnt )
	{
	  this.SumZYAmnt = SumZYAmnt;
 	}

	public Double getSumZYAmnt()
	{
	  return SumZYAmnt;
	}

	public void setSumKSAmnt( Double SumKSAmnt )
	{
	  this.SumKSAmnt = SumKSAmnt;
 	}

	public Double getSumKSAmnt()
	{
	  return SumKSAmnt;
	}

	public void setSumKSMult( Double SumKSMult )
	{
	   if(!(new BOMPreCheck().CheckPI(SumKSMult))){
	  	  return;
	    }else{
	    	this.SumKSMult = SumKSMult;
	   }
 	}

	public Double getSumKSMult()
	{
	  return SumKSMult;
	}

	public void setOUWFlag4(String  OUWFlag4 )
	{
	  this.OUWFlag4 = OUWFlag4;
 	}

	public String getOUWFlag4()
	{
	  return OUWFlag4;
	}

	public void setORegister(String  ORegister )
	{
	  this.ORegister = ORegister;
 	}

	public String getORegister()
	{
	  return ORegister;
	}

	public void setOReportFlag(String  OReportFlag )
	{
	  this.OReportFlag = OReportFlag;
 	}

	public String getOReportFlag()
	{
	  return OReportFlag;
	}

	public void setOPENotice(String  OPENotice )
	{
	  this.OPENotice = OPENotice;
 	}

	public String getOPENotice()
	{
	  return OPENotice;
	}

	public void setOMeetYN(String  OMeetYN )
	{
	  this.OMeetYN = OMeetYN;
 	}

	public String getOMeetYN()
	{
	  return OMeetYN;
	}

	public void setOAddFeeYN(String  OAddFeeYN )
	{
	  this.OAddFeeYN = OAddFeeYN;
 	}

	public String getOAddFeeYN()
	{
	  return OAddFeeYN;
	}

	public void setOSpecYN(String  OSpecYN )
	{
	  this.OSpecYN = OSpecYN;
 	}

	public String getOSpecYN()
	{
	  return OSpecYN;
	}

	public void setOChangePlanYN(String  OChangePlanYN )
	{
	  this.OChangePlanYN = OChangePlanYN;
 	}

	public String getOChangePlanYN()
	{
	  return OChangePlanYN;
	}

	public void setOLatePENoticeYN(String  OLatePENoticeYN )
	{
	  this.OLatePENoticeYN = OLatePENoticeYN;
 	}

	public String getOLatePENoticeYN()
	{
	  return OLatePENoticeYN;
	}

	public void setOLateMeetYN(String  OLateMeetYN )
	{
	  this.OLateMeetYN = OLateMeetYN;
 	}

	public String getOLateMeetYN()
	{
	  return OLateMeetYN;
	}

	public void setOLateAddFeeYN(String  OLateAddFeeYN )
	{
	  this.OLateAddFeeYN = OLateAddFeeYN;
 	}

	public String getOLateAddFeeYN()
	{
	  return OLateAddFeeYN;
	}

	public void setOLateSpecYN(String  OLateSpecYN )
	{
	  this.OLateSpecYN = OLateSpecYN;
 	}

	public String getOLateSpecYN()
	{
	  return OLateSpecYN;
	}

	public void setOLateChangePlanYN(String  OLateChangePlanYN )
	{
	  this.OLateChangePlanYN = OLateChangePlanYN;
 	}

	public String getOLateChangePlanYN()
	{
	  return OLateChangePlanYN;
	}

	public void setOOtherOver(String  OOtherOver )
	{
	  this.OOtherOver = OOtherOver;
 	}

	public String getOOtherOver()
	{
	  return OOtherOver;
	}

	public void setPENoticeError(String  PENoticeError )
	{
	  this.PENoticeError = PENoticeError;
 	}

	public String getPENoticeError()
	{
	  return PENoticeError;
	}

	public void setOMainPol( Double OMainPol )
	{
	  this.OMainPol = OMainPol;
 	}

	public Double getOMainPol()
	{
	  return OMainPol;
	}

	public void setEdorUWFlag(String  EdorUWFlag )
	{
	  this.EdorUWFlag = EdorUWFlag;
 	}

	public String getEdorUWFlag()
	{
	  return EdorUWFlag;
	}

	public void setEM( Double EM )
	{
	   if(!(new BOMPreCheck().CheckPlus(EM))){
	  	  return;
	    }else{
	    	this.EM = EM;
	   }
 	}

	public Double getEM()
	{
	  return EM;
	}

	public void setClaimUWFlag(String  ClaimUWFlag )
	{
	  this.ClaimUWFlag = ClaimUWFlag;
 	}

	public String getClaimUWFlag()
	{
	  return ClaimUWFlag;
	}

	public void setReNewUWFlag(String  ReNewUWFlag )
	{
	  this.ReNewUWFlag = ReNewUWFlag;
 	}

	public String getReNewUWFlag()
	{
	  return ReNewUWFlag;
	}

	public void setOEdorUWFlag(String  OEdorUWFlag )
	{
	  this.OEdorUWFlag = OEdorUWFlag;
 	}

	public String getOEdorUWFlag()
	{
	  return OEdorUWFlag;
	}

	public void setGetStartAge( Double GetStartAge )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(GetStartAge)))){
	  	  return;
	    }else{
	    	this.GetStartAge = GetStartAge;
	   }
 	}

	public Double getGetStartAge()
	{
	  return GetStartAge;
	}

	public void setGetAge( Double GetAge )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(GetAge)))){
	  	  return;
	    }else{
	    	this.GetAge = GetAge;
	   }
 	}

	public Double getGetAge()
	{
	  return GetAge;
	}

	public void setHealthPro(String  HealthPro )
	{
	  this.HealthPro = HealthPro;
 	}

	public String getHealthPro()
	{
	  return HealthPro;
	}

	public void setBlackListFlag(String  BlackListFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BlackListFlag)))){
	    	return;
  	   }else{
	    	this.BlackListFlag = BlackListFlag;
	  }
 	}

	public String getBlackListFlag()
	{
	  return BlackListFlag;
	}

	public void setOHealthTimes( Double OHealthTimes )
	{
	   if(!(new BOMPreCheck().CheckPI(OHealthTimes))){
	  	  return;
	    }else{
	    	this.OHealthTimes = OHealthTimes;
	   }
 	}

	public Double getOHealthTimes()
	{
	  return OHealthTimes;
	}

	public void setBirthday(Date  Birthday )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(Birthday)))){
	    	return;
  	   }else{
	    	this.Birthday = Birthday;
	  }
 	}

	public Date getBirthday()
	{
	  return Birthday;
	}

	public void setStature( Double Stature )
	{
	   if(!(new BOMPreCheck().CheckPlus(Stature))){
	  	  return;
	    }else{
	    	this.Stature = Stature;
	   }
 	}

	public Double getStature()
	{
	  return Stature;
	}

	public void setBMI( Double BMI )
	{
	   if(!(new BOMPreCheck().CheckPlus(BMI))){
	  	  return;
	    }else{
	    	this.BMI = BMI;
	   }
 	}

	public Double getBMI()
	{
	  return BMI;
	}

	public void setLiveBnfSelf(String  LiveBnfSelf )
	{
	  this.LiveBnfSelf = LiveBnfSelf;
 	}

	public String getLiveBnfSelf()
	{
	  return LiveBnfSelf;
	}

	public void setHaveNoFeeWithD(String  HaveNoFeeWithD )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(HaveNoFeeWithD)))){
	    	return;
  	   }else{
	    	this.HaveNoFeeWithD = HaveNoFeeWithD;
	  }
 	}

	public String getHaveNoFeeWithD()
	{
	  return HaveNoFeeWithD;
	}

	public void setSecInsuAddPoint( Double SecInsuAddPoint )
	{
	   if(!(new BOMPreCheck().CheckPlus(SecInsuAddPoint))){
	  	  return;
	    }else{
	    	this.SecInsuAddPoint = SecInsuAddPoint;
	   }
 	}

	public Double getSecInsuAddPoint()
	{
	  return SecInsuAddPoint;
	}

	public void setSaveAmnt( Double SaveAmnt )
	{
	   if(!(new BOMPreCheck().CheckPlus(SaveAmnt))){
	  	  return;
	    }else{
	    	this.SaveAmnt = SaveAmnt;
	   }
 	}

	public Double getSaveAmnt()
	{
	  return SaveAmnt;
	}

	public void setContPlanCode(String  ContPlanCode )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ContPlanCode)))){
	    	return;
  	   }else{
	    	this.ContPlanCode = ContPlanCode;
	  }
 	}

	public String getContPlanCode()
	{
	  return ContPlanCode;
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

	if("OClaimUWFlag".equals(FCode))
		{
		    setOClaimUWFlag(FValue);
		}

	if("InsuredISOperator".equals(FCode))
		{
		    setInsuredISOperator(FValue);
		}

	if("WithDHispital".equals(FCode))
		{
		    setWithDHispital(FValue);
		}

	if("ORNewUWFlag".equals(FCode))
		{
		    setORNewUWFlag(FValue);
		}

	if("OOccupationType".equals(FCode))
		{
		    setOOccupationType(FValue);
		}

	if("OSurrenderTimes".equals(FCode))
		{
			setOSurrenderTimes(Double.valueOf(FValue));
		}

	if("OReTimes".equals(FCode))
		{
			setOReTimes(Double.valueOf(FValue));
		}

	if("RelationToAppnt".equals(FCode))
		{
		    setRelationToAppnt(FValue);
		}

	if("StartWorkDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStartWorkDate(d);
		}

	if("InsuredAppAge".equals(FCode))
		{
			setInsuredAppAge(Double.valueOf(FValue));
		}

	if("LateOUWFlag".equals(FCode))
		{
		    setLateOUWFlag(FValue);
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("Sex".equals(FCode))
		{
		    setSex(FValue);
		}

	if("NativePlace".equals(FCode))
		{
		    setNativePlace(FValue);
		}

	if("Nationality".equals(FCode))
		{
		    setNationality(FValue);
		}

	if("OClaimFlag".equals(FCode))
		{
		    setOClaimFlag(FValue);
		}

	if("RgtAddress".equals(FCode))
		{
		    setRgtAddress(FValue);
		}

	if("Marriage".equals(FCode))
		{
		    setMarriage(FValue);
		}

	if("MarriageDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setMarriageDate(d);
		}

	if("Avoirdupois".equals(FCode))
		{
			setAvoirdupois(Double.valueOf(FValue));
		}

	if("BirthAvoirdupois".equals(FCode))
		{
			setBirthAvoirdupois(Double.valueOf(FValue));
		}

	if("BirthStature".equals(FCode))
		{
			setBirthStature(Double.valueOf(FValue));
		}

	if("Degree".equals(FCode))
		{
		    setDegree(FValue);
		}

	if("CreditGrade".equals(FCode))
		{
		    setCreditGrade(FValue);
		}

	if("JoinCompanyDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setJoinCompanyDate(d);
		}

	if("Position".equals(FCode))
		{
		    setPosition(FValue);
		}

	if("OccupationType".equals(FCode))
		{
		    setOccupationType(FValue);
		}

	if("InsuredStat".equals(FCode))
		{
		    setInsuredStat(FValue);
		}

	if("License".equals(FCode))
		{
		    setLicense(FValue);
		}

	if("LicenseType".equals(FCode))
		{
		    setLicenseType(FValue);
		}

	if("SocialInsuNo".equals(FCode))
		{
		    setSocialInsuNo(FValue);
		}

	if("SmokeAmount".equals(FCode))
		{
			setSmokeAmount(Double.valueOf(FValue));
		}

	if("SmokeFlag".equals(FCode))
		{
		    setSmokeFlag(FValue);
		}

	if("DrinkFlag".equals(FCode))
		{
		    setDrinkFlag(FValue);
		}

	if("ApprovePolCount".equals(FCode))
		{
			setApprovePolCount(Double.valueOf(FValue));
		}

	if("UWPolCount".equals(FCode))
		{
			setUWPolCount(Double.valueOf(FValue));
		}

	if("LifeSumAmnt".equals(FCode))
		{
			setLifeSumAmnt(Double.valueOf(FValue));
		}

	if("DiseaseSumAmnt".equals(FCode))
		{
			setDiseaseSumAmnt(Double.valueOf(FValue));
		}

	if("AccidentSumAmnt".equals(FCode))
		{
			setAccidentSumAmnt(Double.valueOf(FValue));
		}

	if("MedicalSumAmnt".equals(FCode))
		{
			setMedicalSumAmnt(Double.valueOf(FValue));
		}

	if("SumMult".equals(FCode))
		{
		    setSumMult(FValue);
		}

	if("OAddFeeTimes".equals(FCode))
		{
			setOAddFeeTimes(Double.valueOf(FValue));
		}

	if("DanSportInter".equals(FCode))
		{
		    setDanSportInter(FValue);
		}

	if("TrafAccImpart".equals(FCode))
		{
		    setTrafAccImpart(FValue);
		}

	if("AbroadImpart".equals(FCode))
		{
		    setAbroadImpart(FValue);
		}

	if("TrafficAccident".equals(FCode))
		{
		    setTrafficAccident(FValue);
		}

	if("SumKSCont".equals(FCode))
		{
			setSumKSCont(Double.valueOf(FValue));
		}

	if("SumMRZYCont".equals(FCode))
		{
			setSumMRZYCont(Double.valueOf(FValue));
		}

	if("OImpart".equals(FCode))
		{
		    setOImpart(FValue);
		}

	if("PregnancyWeeks".equals(FCode))
		{
			setPregnancyWeeks(Double.valueOf(FValue));
		}

	if("SmokeYears".equals(FCode))
		{
			setSmokeYears(Double.valueOf(FValue));
		}

	if("DrinkYears".equals(FCode))
		{
			setDrinkYears(Double.valueOf(FValue));
		}

	if("DrinkType".equals(FCode))
		{
		    setDrinkType(FValue);
		}

	if("ISPregnancy".equals(FCode))
		{
		    setISPregnancy(FValue);
		}

	if("YearIncome".equals(FCode))
		{
			setYearIncome(Double.valueOf(FValue));
		}

	if("DisabilityImpart".equals(FCode))
		{
		    setDisabilityImpart(FValue);
		}

	if("OInvaliTimes".equals(FCode))
		{
			setOInvaliTimes(Double.valueOf(FValue));
		}

	if("OccupationCode".equals(FCode))
		{
		    setOccupationCode(FValue);
		}

	if("SameOccuCode".equals(FCode))
		{
		    setSameOccuCode(FValue);
		}

	if("SumJYMTCount".equals(FCode))
		{
			setSumJYMTCount(Double.valueOf(FValue));
		}

	if("SumFGYMPrem".equals(FCode))
		{
			setSumFGYMPrem(Double.valueOf(FValue));
		}

	if("OHealthFlag".equals(FCode))
		{
		    setOHealthFlag(FValue);
		}

	if("ORejeceTimes".equals(FCode))
		{
			setORejeceTimes(Double.valueOf(FValue));
		}

	if("ODeferTimes".equals(FCode))
		{
			setODeferTimes(Double.valueOf(FValue));
		}

	if("OWithdrawTimes".equals(FCode))
		{
			setOWithdrawTimes(Double.valueOf(FValue));
		}

	if("OChangeFlag".equals(FCode))
		{
			setOChangeFlag(Double.valueOf(FValue));
		}

	if("OReleveTimes".equals(FCode))
		{
			setOReleveTimes(Double.valueOf(FValue));
		}

	if("Salary".equals(FCode))
		{
			setSalary(Double.valueOf(FValue));
		}

	if("OMRate".equals(FCode))
		{
			setOMRate(Double.valueOf(FValue));
		}

	if("FemaleConTent".equals(FCode))
		{
		    setFemaleConTent(FValue);
		}

	if("BabyConTent".equals(FCode))
		{
		    setBabyConTent(FValue);
		}

	if("FamilyConTent".equals(FCode))
		{
		    setFamilyConTent(FValue);
		}

	if("OuncommonConTent".equals(FCode))
		{
		    setOuncommonConTent(FValue);
		}

	if("OOMaxType".equals(FCode))
		{
		    setOOMaxType(FValue);
		}

	if("AvoirdupoisFlag".equals(FCode))
		{
		    setAvoirdupoisFlag(FValue);
		}

	if("StatureFlag".equals(FCode))
		{
		    setStatureFlag(FValue);
		}

	if("DieBnfFlag".equals(FCode))
		{
		    setDieBnfFlag(FValue);
		}

	if("ZBFlag".equals(FCode))
		{
		    setZBFlag(FValue);
		}

	if("NeedPEN".equals(FCode))
		{
		    setNeedPEN(FValue);
		}

	if("ImpartCode".equals(FCode))
		{
		    setImpartCode(FValue);
		}

	if("OMedRate".equals(FCode))
		{
			setOMedRate(Double.valueOf(FValue));
		}

	if("ContenteFlag".equals(FCode))
		{
		    setContenteFlag(FValue);
		}

	if("FirstSurviveBnfSum".equals(FCode))
		{
			setFirstSurviveBnfSum(Double.valueOf(FValue));
		}

	if("HealthFlag".equals(FCode))
		{
		    setHealthFlag(FValue);
		}

	if("SecondSurviveBnfSum".equals(FCode))
		{
			setSecondSurviveBnfSum(Double.valueOf(FValue));
		}

	if("ThirdSurviveBnfSum".equals(FCode))
		{
			setThirdSurviveBnfSum(Double.valueOf(FValue));
		}

	if("FourthSurviveBnfSum".equals(FCode))
		{
			setFourthSurviveBnfSum(Double.valueOf(FValue));
		}

	if("FirstDieBnfSum".equals(FCode))
		{
			setFirstDieBnfSum(Double.valueOf(FValue));
		}

	if("SecondDieBnfSum".equals(FCode))
		{
			setSecondDieBnfSum(Double.valueOf(FValue));
		}

	if("ThirdDieBnfSum".equals(FCode))
		{
			setThirdDieBnfSum(Double.valueOf(FValue));
		}

	if("FourthDieBnfSum".equals(FCode))
		{
			setFourthDieBnfSum(Double.valueOf(FValue));
		}

	if("NOOCPType".equals(FCode))
		{
		    setNOOCPType(FValue);
		}

	if("NegativeOCPType".equals(FCode))
		{
		    setNegativeOCPType(FValue);
		}

	if("SixthOCPType".equals(FCode))
		{
		    setSixthOCPType(FValue);
		}

	if("FifthOCPType".equals(FCode))
		{
		    setFifthOCPType(FValue);
		}

	if("FourthOCPType".equals(FCode))
		{
		    setFourthOCPType(FValue);
		}

	if("ThirdOCPType".equals(FCode))
		{
		    setThirdOCPType(FValue);
		}

	if("SecondOCPType".equals(FCode))
		{
		    setSecondOCPType(FValue);
		}

	if("AddRiskPrem".equals(FCode))
		{
			setAddRiskPrem(Double.valueOf(FValue));
		}

	if("FirstOCPType".equals(FCode))
		{
		    setFirstOCPType(FValue);
		}

	if("BackOUWFlag".equals(FCode))
		{
		    setBackOUWFlag(FValue);
		}

	if("StandarOUWFlag".equals(FCode))
		{
		    setStandarOUWFlag(FValue);
		}

	if("RejectOUWFlag".equals(FCode))
		{
		    setRejectOUWFlag(FValue);
		}

	if("tORPTnoRGT".equals(FCode))
		{
		    settORPTnoRGT(FValue);
		}

	if("InsHosBlack".equals(FCode))
		{
		    setInsHosBlack(FValue);
		}

	if("InsHopName".equals(FCode))
		{
		    setInsHopName(FValue);
		}

	if("ComrAppFlag".equals(FCode))
		{
		    setComrAppFlag(FValue);
		}

	if("BMIFlag".equals(FCode))
		{
		    setBMIFlag(FValue);
		}

	if("Drink".equals(FCode))
		{
			setDrink(Double.valueOf(FValue));
		}

	if("DieBnfSelf".equals(FCode))
		{
		    setDieBnfSelf(FValue);
		}

	if("HealthTellConTent".equals(FCode))
		{
		    setHealthTellConTent(FValue);
		}

	if("AddOUWFlag".equals(FCode))
		{
		    setAddOUWFlag(FValue);
		}

	if("SpecialOUWFlag".equals(FCode))
		{
		    setSpecialOUWFlag(FValue);
		}

	if("SumAnnAmnt".equals(FCode))
		{
			setSumAnnAmnt(Double.valueOf(FValue));
		}

	if("SumMedAmnt".equals(FCode))
		{
			setSumMedAmnt(Double.valueOf(FValue));
		}

	if("SumDieAmnt".equals(FCode))
		{
			setSumDieAmnt(Double.valueOf(FValue));
		}

	if("SumZYAmnt".equals(FCode))
		{
			setSumZYAmnt(Double.valueOf(FValue));
		}

	if("SumKSAmnt".equals(FCode))
		{
			setSumKSAmnt(Double.valueOf(FValue));
		}

	if("SumKSMult".equals(FCode))
		{
			setSumKSMult(Double.valueOf(FValue));
		}

	if("OUWFlag4".equals(FCode))
		{
		    setOUWFlag4(FValue);
		}

	if("ORegister".equals(FCode))
		{
		    setORegister(FValue);
		}

	if("OReportFlag".equals(FCode))
		{
		    setOReportFlag(FValue);
		}

	if("OPENotice".equals(FCode))
		{
		    setOPENotice(FValue);
		}

	if("OMeetYN".equals(FCode))
		{
		    setOMeetYN(FValue);
		}

	if("OAddFeeYN".equals(FCode))
		{
		    setOAddFeeYN(FValue);
		}

	if("OSpecYN".equals(FCode))
		{
		    setOSpecYN(FValue);
		}

	if("OChangePlanYN".equals(FCode))
		{
		    setOChangePlanYN(FValue);
		}

	if("OLatePENoticeYN".equals(FCode))
		{
		    setOLatePENoticeYN(FValue);
		}

	if("OLateMeetYN".equals(FCode))
		{
		    setOLateMeetYN(FValue);
		}

	if("OLateAddFeeYN".equals(FCode))
		{
		    setOLateAddFeeYN(FValue);
		}

	if("OLateSpecYN".equals(FCode))
		{
		    setOLateSpecYN(FValue);
		}

	if("OLateChangePlanYN".equals(FCode))
		{
		    setOLateChangePlanYN(FValue);
		}

	if("OOtherOver".equals(FCode))
		{
		    setOOtherOver(FValue);
		}

	if("PENoticeError".equals(FCode))
		{
		    setPENoticeError(FValue);
		}

	if("OMainPol".equals(FCode))
		{
			setOMainPol(Double.valueOf(FValue));
		}

	if("EdorUWFlag".equals(FCode))
		{
		    setEdorUWFlag(FValue);
		}

	if("EM".equals(FCode))
		{
			setEM(Double.valueOf(FValue));
		}

	if("ClaimUWFlag".equals(FCode))
		{
		    setClaimUWFlag(FValue);
		}

	if("ReNewUWFlag".equals(FCode))
		{
		    setReNewUWFlag(FValue);
		}

	if("OEdorUWFlag".equals(FCode))
		{
		    setOEdorUWFlag(FValue);
		}

	if("GetStartAge".equals(FCode))
		{
			setGetStartAge(Double.valueOf(FValue));
		}

	if("GetAge".equals(FCode))
		{
			setGetAge(Double.valueOf(FValue));
		}

	if("HealthPro".equals(FCode))
		{
		    setHealthPro(FValue);
		}

	if("BlackListFlag".equals(FCode))
		{
		    setBlackListFlag(FValue);
		}

	if("OHealthTimes".equals(FCode))
		{
			setOHealthTimes(Double.valueOf(FValue));
		}

	if("Birthday".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setBirthday(d);
		}

	if("Stature".equals(FCode))
		{
			setStature(Double.valueOf(FValue));
		}

	if("BMI".equals(FCode))
		{
			setBMI(Double.valueOf(FValue));
		}

	if("LiveBnfSelf".equals(FCode))
		{
		    setLiveBnfSelf(FValue);
		}

	if("HaveNoFeeWithD".equals(FCode))
		{
		    setHaveNoFeeWithD(FValue);
		}

	if("SecInsuAddPoint".equals(FCode))
		{
			setSecInsuAddPoint(Double.valueOf(FValue));
		}

	if("SaveAmnt".equals(FCode))
		{
			setSaveAmnt(Double.valueOf(FValue));
		}

	if("ContPlanCode".equals(FCode))
		{
		    setContPlanCode(FValue);
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
	  if (FCode.equalsIgnoreCase("OClaimUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOClaimUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredISOperator"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredISOperator()));
	  }
	  if (FCode.equalsIgnoreCase("WithDHispital"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getWithDHispital()));
	  }
	  if (FCode.equalsIgnoreCase("ORNewUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getORNewUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OOccupationType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOOccupationType()));
	  }
	  if (FCode.equalsIgnoreCase("OSurrenderTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOSurrenderTimes()));
	  }
	  if (FCode.equalsIgnoreCase("OReTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOReTimes()));
	  }
	  if (FCode.equalsIgnoreCase("RelationToAppnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelationToAppnt()));
	  }
	  if (FCode.equalsIgnoreCase("StartWorkDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStartWorkDate())));
	  }
	  if (FCode.equalsIgnoreCase("InsuredAppAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredAppAge()));
	  }
	  if (FCode.equalsIgnoreCase("LateOUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLateOUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("Sex"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSex()));
	  }
	  if (FCode.equalsIgnoreCase("NativePlace"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNativePlace()));
	  }
	  if (FCode.equalsIgnoreCase("Nationality"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNationality()));
	  }
	  if (FCode.equalsIgnoreCase("OClaimFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOClaimFlag()));
	  }
	  if (FCode.equalsIgnoreCase("RgtAddress"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtAddress()));
	  }
	  if (FCode.equalsIgnoreCase("Marriage"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMarriage()));
	  }
	  if (FCode.equalsIgnoreCase("MarriageDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getMarriageDate())));
	  }
	  if (FCode.equalsIgnoreCase("Avoirdupois"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAvoirdupois()));
	  }
	  if (FCode.equalsIgnoreCase("BirthAvoirdupois"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBirthAvoirdupois()));
	  }
	  if (FCode.equalsIgnoreCase("BirthStature"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBirthStature()));
	  }
	  if (FCode.equalsIgnoreCase("Degree"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDegree()));
	  }
	  if (FCode.equalsIgnoreCase("CreditGrade"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCreditGrade()));
	  }
	  if (FCode.equalsIgnoreCase("JoinCompanyDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getJoinCompanyDate())));
	  }
	  if (FCode.equalsIgnoreCase("Position"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPosition()));
	  }
	  if (FCode.equalsIgnoreCase("OccupationType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationType()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredStat"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredStat()));
	  }
	  if (FCode.equalsIgnoreCase("License"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLicense()));
	  }
	  if (FCode.equalsIgnoreCase("LicenseType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLicenseType()));
	  }
	  if (FCode.equalsIgnoreCase("SocialInsuNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSocialInsuNo()));
	  }
	  if (FCode.equalsIgnoreCase("SmokeAmount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSmokeAmount()));
	  }
	  if (FCode.equalsIgnoreCase("SmokeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSmokeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("DrinkFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDrinkFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ApprovePolCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getApprovePolCount()));
	  }
	  if (FCode.equalsIgnoreCase("UWPolCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWPolCount()));
	  }
	  if (FCode.equalsIgnoreCase("LifeSumAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLifeSumAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("DiseaseSumAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDiseaseSumAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("AccidentSumAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAccidentSumAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("MedicalSumAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMedicalSumAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumMult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumMult()));
	  }
	  if (FCode.equalsIgnoreCase("OAddFeeTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOAddFeeTimes()));
	  }
	  if (FCode.equalsIgnoreCase("DanSportInter"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDanSportInter()));
	  }
	  if (FCode.equalsIgnoreCase("TrafAccImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTrafAccImpart()));
	  }
	  if (FCode.equalsIgnoreCase("AbroadImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAbroadImpart()));
	  }
	  if (FCode.equalsIgnoreCase("TrafficAccident"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTrafficAccident()));
	  }
	  if (FCode.equalsIgnoreCase("SumKSCont"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumKSCont()));
	  }
	  if (FCode.equalsIgnoreCase("SumMRZYCont"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumMRZYCont()));
	  }
	  if (FCode.equalsIgnoreCase("OImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOImpart()));
	  }
	  if (FCode.equalsIgnoreCase("PregnancyWeeks"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPregnancyWeeks()));
	  }
	  if (FCode.equalsIgnoreCase("SmokeYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSmokeYears()));
	  }
	  if (FCode.equalsIgnoreCase("DrinkYears"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDrinkYears()));
	  }
	  if (FCode.equalsIgnoreCase("DrinkType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDrinkType()));
	  }
	  if (FCode.equalsIgnoreCase("ISPregnancy"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getISPregnancy()));
	  }
	  if (FCode.equalsIgnoreCase("YearIncome"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getYearIncome()));
	  }
	  if (FCode.equalsIgnoreCase("DisabilityImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDisabilityImpart()));
	  }
	  if (FCode.equalsIgnoreCase("OInvaliTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOInvaliTimes()));
	  }
	  if (FCode.equalsIgnoreCase("OccupationCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationCode()));
	  }
	  if (FCode.equalsIgnoreCase("SameOccuCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSameOccuCode()));
	  }
	  if (FCode.equalsIgnoreCase("SumJYMTCount"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumJYMTCount()));
	  }
	  if (FCode.equalsIgnoreCase("SumFGYMPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumFGYMPrem()));
	  }
	  if (FCode.equalsIgnoreCase("OHealthFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOHealthFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ORejeceTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getORejeceTimes()));
	  }
	  if (FCode.equalsIgnoreCase("ODeferTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getODeferTimes()));
	  }
	  if (FCode.equalsIgnoreCase("OWithdrawTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOWithdrawTimes()));
	  }
	  if (FCode.equalsIgnoreCase("OChangeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOChangeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OReleveTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOReleveTimes()));
	  }
	  if (FCode.equalsIgnoreCase("Salary"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSalary()));
	  }
	  if (FCode.equalsIgnoreCase("OMRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOMRate()));
	  }
	  if (FCode.equalsIgnoreCase("FemaleConTent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFemaleConTent()));
	  }
	  if (FCode.equalsIgnoreCase("BabyConTent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBabyConTent()));
	  }
	  if (FCode.equalsIgnoreCase("FamilyConTent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFamilyConTent()));
	  }
	  if (FCode.equalsIgnoreCase("OuncommonConTent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOuncommonConTent()));
	  }
	  if (FCode.equalsIgnoreCase("OOMaxType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOOMaxType()));
	  }
	  if (FCode.equalsIgnoreCase("AvoirdupoisFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAvoirdupoisFlag()));
	  }
	  if (FCode.equalsIgnoreCase("StatureFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStatureFlag()));
	  }
	  if (FCode.equalsIgnoreCase("DieBnfFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDieBnfFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ZBFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getZBFlag()));
	  }
	  if (FCode.equalsIgnoreCase("NeedPEN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNeedPEN()));
	  }
	  if (FCode.equalsIgnoreCase("ImpartCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getImpartCode()));
	  }
	  if (FCode.equalsIgnoreCase("OMedRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOMedRate()));
	  }
	  if (FCode.equalsIgnoreCase("ContenteFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getContenteFlag()));
	  }
	  if (FCode.equalsIgnoreCase("FirstSurviveBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFirstSurviveBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("HealthFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHealthFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SecondSurviveBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSecondSurviveBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("ThirdSurviveBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThirdSurviveBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("FourthSurviveBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFourthSurviveBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("FirstDieBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFirstDieBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("SecondDieBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSecondDieBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("ThirdDieBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThirdDieBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("FourthDieBnfSum"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFourthDieBnfSum()));
	  }
	  if (FCode.equalsIgnoreCase("NOOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNOOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("NegativeOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNegativeOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("SixthOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSixthOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("FifthOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFifthOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("FourthOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFourthOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("ThirdOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getThirdOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("SecondOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSecondOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("AddRiskPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddRiskPrem()));
	  }
	  if (FCode.equalsIgnoreCase("FirstOCPType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFirstOCPType()));
	  }
	  if (FCode.equalsIgnoreCase("BackOUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBackOUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("StandarOUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStandarOUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("RejectOUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRejectOUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("tORPTnoRGT"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(gettORPTnoRGT()));
	  }
	  if (FCode.equalsIgnoreCase("InsHosBlack"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsHosBlack()));
	  }
	  if (FCode.equalsIgnoreCase("InsHopName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsHopName()));
	  }
	  if (FCode.equalsIgnoreCase("ComrAppFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getComrAppFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BMIFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBMIFlag()));
	  }
	  if (FCode.equalsIgnoreCase("Drink"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDrink()));
	  }
	  if (FCode.equalsIgnoreCase("DieBnfSelf"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDieBnfSelf()));
	  }
	  if (FCode.equalsIgnoreCase("HealthTellConTent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHealthTellConTent()));
	  }
	  if (FCode.equalsIgnoreCase("AddOUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddOUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SpecialOUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSpecialOUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SumAnnAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumAnnAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumMedAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumMedAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumDieAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumDieAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumZYAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumZYAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumKSAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumKSAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("SumKSMult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumKSMult()));
	  }
	  if (FCode.equalsIgnoreCase("OUWFlag4"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOUWFlag4()));
	  }
	  if (FCode.equalsIgnoreCase("ORegister"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getORegister()));
	  }
	  if (FCode.equalsIgnoreCase("OReportFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOReportFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OPENotice"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOPENotice()));
	  }
	  if (FCode.equalsIgnoreCase("OMeetYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOMeetYN()));
	  }
	  if (FCode.equalsIgnoreCase("OAddFeeYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOAddFeeYN()));
	  }
	  if (FCode.equalsIgnoreCase("OSpecYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOSpecYN()));
	  }
	  if (FCode.equalsIgnoreCase("OChangePlanYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOChangePlanYN()));
	  }
	  if (FCode.equalsIgnoreCase("OLatePENoticeYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOLatePENoticeYN()));
	  }
	  if (FCode.equalsIgnoreCase("OLateMeetYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOLateMeetYN()));
	  }
	  if (FCode.equalsIgnoreCase("OLateAddFeeYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOLateAddFeeYN()));
	  }
	  if (FCode.equalsIgnoreCase("OLateSpecYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOLateSpecYN()));
	  }
	  if (FCode.equalsIgnoreCase("OLateChangePlanYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOLateChangePlanYN()));
	  }
	  if (FCode.equalsIgnoreCase("OOtherOver"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOOtherOver()));
	  }
	  if (FCode.equalsIgnoreCase("PENoticeError"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPENoticeError()));
	  }
	  if (FCode.equalsIgnoreCase("OMainPol"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOMainPol()));
	  }
	  if (FCode.equalsIgnoreCase("EdorUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEdorUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("EM"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEM()));
	  }
	  if (FCode.equalsIgnoreCase("ClaimUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getClaimUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ReNewUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReNewUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OEdorUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOEdorUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("GetStartAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetStartAge()));
	  }
	  if (FCode.equalsIgnoreCase("GetAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getGetAge()));
	  }
	  if (FCode.equalsIgnoreCase("HealthPro"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHealthPro()));
	  }
	  if (FCode.equalsIgnoreCase("BlackListFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBlackListFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OHealthTimes"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOHealthTimes()));
	  }
	  if (FCode.equalsIgnoreCase("Birthday"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getBirthday())));
	  }
	  if (FCode.equalsIgnoreCase("Stature"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStature()));
	  }
	  if (FCode.equalsIgnoreCase("BMI"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBMI()));
	  }
	  if (FCode.equalsIgnoreCase("LiveBnfSelf"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLiveBnfSelf()));
	  }
	  if (FCode.equalsIgnoreCase("HaveNoFeeWithD"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHaveNoFeeWithD()));
	  }
	  if (FCode.equalsIgnoreCase("SecInsuAddPoint"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSecInsuAddPoint()));
	  }
	  if (FCode.equalsIgnoreCase("SaveAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSaveAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("ContPlanCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getContPlanCode()));
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
