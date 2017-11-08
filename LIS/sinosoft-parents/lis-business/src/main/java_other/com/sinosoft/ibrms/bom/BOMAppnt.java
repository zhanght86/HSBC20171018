/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.ibrms.bom;
import org.apache.log4j.Logger;

import java.util.*;
import java.text.SimpleDateFormat;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.ibrms.BOMPreCheck;

/**
 * <p>ClassName: BOMAppnt </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2010-03-17
 */

public class BOMAppnt extends AbstractBOM
{
private static Logger logger = Logger.getLogger(BOMAppnt.class);


	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 投保人投保经过是否为业务员推销 */
	private String isSalerPromotion;

	/** 投保人投保经过是否为同事朋友推荐 */
	private String isFriendsRecommend;

	/** 投保人投保经过是否投保人自己提出 */
	private String isPresenter;

	/** 投保人投保目的为家庭经济保障 */
	private String isEnsureIntention;

	/** 投保人投保目的为贷款偿还 */
	private String isPayIntention;

	/** 投保人投保目的为贮蓄/投资 */
	private String isInvestItention;

	/** 危险运动爱好 */
	private String DanSportInter;

	/** 累计年交保费 */
	private Double AddYPrem;

	/** 年收入 */
	private Double YearGet;

	/** 投保人职业为MS公司业务员 */
	private String AppntISOperator;

	/** 交通事故告知 */
	private String TrafAccImpart;

	/** 出国意向告知 */
	private String AbroadImpart;

	/** 民族 */
	private String Nationality;

	/** 客户号码 */
	private String AppntNo;

	/** 姓名 */
	private String AppntName;

	/** 身高 */
	private Double Stature;

	/** 体重 */
	private Double Avoirdupois;

	/** 性别 */
	private String AppntSex;

	/** 出生日期 */
	private Date AppntBirthday;

	/** 投保年龄 */
	private Double AppntAge;

	/** 国籍 */
	private String NativePlace;

	/** 户口所在地 */
	private String RgtAddress;

	/** 婚姻状况 */
	private String Marriage;

	/** 结婚日期 */
	private Date MarriageDate;

	/** 学历 */
	private String Degree;

	/** 信用等级 */
	private String CreditGrade;

	/** 入司日期 */
	private Date JoinCompanyDate;

	/** 参加工作日期 */
	private Date StartWorkDate;

	/** 职位 */
	private String Position;

	/** 工资 */
	private Double Salary;

	/** 职业类别 */
	private String OccupationType;

	/** 职业代码 */
	private String OccupationCode;

	/** 吸烟标记 */
	private String SmokeFlag;

	/** 黑名单标记 */
	private String BlackListFlag;

	/** 与被保人关系 */
	private String RelationToInsured;

	/** 内部员工标记 */
	private String InnerFlag;

	/** 体检医院有黑名单记录 */
	private String AppHosBlack;

	/** 体检医院名称 */
	private String AppHosName;

	/** 健康告知不全为否 */
	private String HealthTellConTent;

	/** 妇科告知不全为否 */
	private String FemaleConTent;

	/** 婴儿告知不为否 */
	private String BabyConTent;

	/** 家族史 */
	private String FamilyConTent;

	/** 既往异常投保/理赔史 */
	private String OuncommonConTent;

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

	/** 联系电话与业务员的联系电话是否一致 */
	private String SamePhone;

	/** 残疾事项告知 */
	private String DisabilityImpart;



	// @Constructor
	public BOMAppnt()
	{  }

	public void setisSalerPromotion(String  isSalerPromotion )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(isSalerPromotion)))){
	    	return;
  	   }else{
	    	this.isSalerPromotion = isSalerPromotion;
	  }
 	}

	public String getisSalerPromotion()
	{
	  return isSalerPromotion;
	}

	public void setisFriendsRecommend(String  isFriendsRecommend )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(isFriendsRecommend)))){
	    	return;
  	   }else{
	    	this.isFriendsRecommend = isFriendsRecommend;
	  }
 	}

	public String getisFriendsRecommend()
	{
	  return isFriendsRecommend;
	}

	public void setisPresenter(String  isPresenter )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(isPresenter)))){
	    	return;
  	   }else{
	    	this.isPresenter = isPresenter;
	  }
 	}

	public String getisPresenter()
	{
	  return isPresenter;
	}

	public void setisEnsureIntention(String  isEnsureIntention )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(isEnsureIntention)))){
	    	return;
  	   }else{
	    	this.isEnsureIntention = isEnsureIntention;
	  }
 	}

	public String getisEnsureIntention()
	{
	  return isEnsureIntention;
	}

	public void setisPayIntention(String  isPayIntention )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(isPayIntention)))){
	    	return;
  	   }else{
	    	this.isPayIntention = isPayIntention;
	  }
 	}

	public String getisPayIntention()
	{
	  return isPayIntention;
	}

	public void setisInvestItention(String  isInvestItention )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(isInvestItention)))){
	    	return;
  	   }else{
	    	this.isInvestItention = isInvestItention;
	  }
 	}

	public String getisInvestItention()
	{
	  return isInvestItention;
	}

	public void setDanSportInter(String  DanSportInter )
	{
	  this.DanSportInter = DanSportInter;
 	}

	public String getDanSportInter()
	{
	  return DanSportInter;
	}

	public void setAddYPrem( Double AddYPrem )
	{
	  this.AddYPrem = AddYPrem;
 	}

	public Double getAddYPrem()
	{
	  return AddYPrem;
	}

	public void setYearGet( Double YearGet )
	{
	  this.YearGet = YearGet;
 	}

	public Double getYearGet()
	{
	  return YearGet;
	}

	public void setAppntISOperator(String  AppntISOperator )
	{
	  this.AppntISOperator = AppntISOperator;
 	}

	public String getAppntISOperator()
	{
	  return AppntISOperator;
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

	public void setAppntNo(String  AppntNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AppntNo)))){
	    	return;
  	   }else{
	    	this.AppntNo = AppntNo;
	  }
 	}

	public String getAppntNo()
	{
	  return AppntNo;
	}

	public void setAppntName(String  AppntName )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AppntName)))){
	    	return;
  	   }else{
	    	this.AppntName = AppntName;
	  }
 	}

	public String getAppntName()
	{
	  return AppntName;
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

	public void setAppntSex(String  AppntSex )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AppntSex)))){
	    	return;
  	   }else{
	    	this.AppntSex = AppntSex;
	  }
 	}

	public String getAppntSex()
	{
	  return AppntSex;
	}

	public void setAppntBirthday(Date  AppntBirthday )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AppntBirthday)))){
	    	return;
  	   }else{
	    	this.AppntBirthday = AppntBirthday;
	  }
 	}

	public Date getAppntBirthday()
	{
	  return AppntBirthday;
	}

	public void setAppntAge( Double AppntAge )
	{
	   if(!(new BOMPreCheck().CheckPI(AppntAge))){
	  	  return;
	    }else{
	    	this.AppntAge = AppntAge;
	   }
 	}

	public Double getAppntAge()
	{
	  return AppntAge;
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
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(CreditGrade)))){
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

	public void setRelationToInsured(String  RelationToInsured )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RelationToInsured)))){
	    	return;
  	   }else{
	    	this.RelationToInsured = RelationToInsured;
	  }
 	}

	public String getRelationToInsured()
	{
	  return RelationToInsured;
	}

	public void setInnerFlag(String  InnerFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(InnerFlag)))){
	    	return;
  	   }else{
	    	this.InnerFlag = InnerFlag;
	  }
 	}

	public String getInnerFlag()
	{
	  return InnerFlag;
	}

	public void setAppHosBlack(String  AppHosBlack )
	{
	  this.AppHosBlack = AppHosBlack;
 	}

	public String getAppHosBlack()
	{
	  return AppHosBlack;
	}

	public void setAppHosName(String  AppHosName )
	{
	  this.AppHosName = AppHosName;
 	}

	public String getAppHosName()
	{
	  return AppHosName;
	}

	public void setHealthTellConTent(String  HealthTellConTent )
	{
	  this.HealthTellConTent = HealthTellConTent;
 	}

	public String getHealthTellConTent()
	{
	  return HealthTellConTent;
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

	public void setSamePhone(String  SamePhone )
	{
	  this.SamePhone = SamePhone;
 	}

	public String getSamePhone()
	{
	  return SamePhone;
	}

	public void setDisabilityImpart(String  DisabilityImpart )
	{
	  this.DisabilityImpart = DisabilityImpart;
 	}

	public String getDisabilityImpart()
	{
	  return DisabilityImpart;
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

	if("isSalerPromotion".equals(FCode))
		{
		    setisSalerPromotion(FValue);
		}

	if("isFriendsRecommend".equals(FCode))
		{
		    setisFriendsRecommend(FValue);
		}

	if("isPresenter".equals(FCode))
		{
		    setisPresenter(FValue);
		}

	if("isEnsureIntention".equals(FCode))
		{
		    setisEnsureIntention(FValue);
		}

	if("isPayIntention".equals(FCode))
		{
		    setisPayIntention(FValue);
		}

	if("isInvestItention".equals(FCode))
		{
		    setisInvestItention(FValue);
		}

	if("DanSportInter".equals(FCode))
		{
		    setDanSportInter(FValue);
		}

	if("AddYPrem".equals(FCode))
		{
			setAddYPrem(Double.valueOf(FValue));
		}

	if("YearGet".equals(FCode))
		{
			setYearGet(Double.valueOf(FValue));
		}

	if("AppntISOperator".equals(FCode))
		{
		    setAppntISOperator(FValue);
		}

	if("TrafAccImpart".equals(FCode))
		{
		    setTrafAccImpart(FValue);
		}

	if("AbroadImpart".equals(FCode))
		{
		    setAbroadImpart(FValue);
		}

	if("Nationality".equals(FCode))
		{
		    setNationality(FValue);
		}

	if("AppntNo".equals(FCode))
		{
		    setAppntNo(FValue);
		}

	if("AppntName".equals(FCode))
		{
		    setAppntName(FValue);
		}

	if("Stature".equals(FCode))
		{
			setStature(Double.valueOf(FValue));
		}

	if("Avoirdupois".equals(FCode))
		{
			setAvoirdupois(Double.valueOf(FValue));
		}

	if("AppntSex".equals(FCode))
		{
		    setAppntSex(FValue);
		}

	if("AppntBirthday".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setAppntBirthday(d);
		}

	if("AppntAge".equals(FCode))
		{
			setAppntAge(Double.valueOf(FValue));
		}

	if("NativePlace".equals(FCode))
		{
		    setNativePlace(FValue);
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

	if("StartWorkDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStartWorkDate(d);
		}

	if("Position".equals(FCode))
		{
		    setPosition(FValue);
		}

	if("Salary".equals(FCode))
		{
			setSalary(Double.valueOf(FValue));
		}

	if("OccupationType".equals(FCode))
		{
		    setOccupationType(FValue);
		}

	if("OccupationCode".equals(FCode))
		{
		    setOccupationCode(FValue);
		}

	if("SmokeFlag".equals(FCode))
		{
		    setSmokeFlag(FValue);
		}

	if("BlackListFlag".equals(FCode))
		{
		    setBlackListFlag(FValue);
		}

	if("RelationToInsured".equals(FCode))
		{
		    setRelationToInsured(FValue);
		}

	if("InnerFlag".equals(FCode))
		{
		    setInnerFlag(FValue);
		}

	if("AppHosBlack".equals(FCode))
		{
		    setAppHosBlack(FValue);
		}

	if("AppHosName".equals(FCode))
		{
		    setAppHosName(FValue);
		}

	if("HealthTellConTent".equals(FCode))
		{
		    setHealthTellConTent(FValue);
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

	if("SamePhone".equals(FCode))
		{
		    setSamePhone(FValue);
		}

	if("DisabilityImpart".equals(FCode))
		{
		    setDisabilityImpart(FValue);
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
	  if (FCode.equalsIgnoreCase("isSalerPromotion"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getisSalerPromotion()));
	  }
	  if (FCode.equalsIgnoreCase("isFriendsRecommend"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getisFriendsRecommend()));
	  }
	  if (FCode.equalsIgnoreCase("isPresenter"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getisPresenter()));
	  }
	  if (FCode.equalsIgnoreCase("isEnsureIntention"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getisEnsureIntention()));
	  }
	  if (FCode.equalsIgnoreCase("isPayIntention"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getisPayIntention()));
	  }
	  if (FCode.equalsIgnoreCase("isInvestItention"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getisInvestItention()));
	  }
	  if (FCode.equalsIgnoreCase("DanSportInter"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDanSportInter()));
	  }
	  if (FCode.equalsIgnoreCase("AddYPrem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddYPrem()));
	  }
	  if (FCode.equalsIgnoreCase("YearGet"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getYearGet()));
	  }
	  if (FCode.equalsIgnoreCase("AppntISOperator"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppntISOperator()));
	  }
	  if (FCode.equalsIgnoreCase("TrafAccImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getTrafAccImpart()));
	  }
	  if (FCode.equalsIgnoreCase("AbroadImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAbroadImpart()));
	  }
	  if (FCode.equalsIgnoreCase("Nationality"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNationality()));
	  }
	  if (FCode.equalsIgnoreCase("AppntNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppntNo()));
	  }
	  if (FCode.equalsIgnoreCase("AppntName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppntName()));
	  }
	  if (FCode.equalsIgnoreCase("Stature"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getStature()));
	  }
	  if (FCode.equalsIgnoreCase("Avoirdupois"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAvoirdupois()));
	  }
	  if (FCode.equalsIgnoreCase("AppntSex"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppntSex()));
	  }
	  if (FCode.equalsIgnoreCase("AppntBirthday"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getAppntBirthday())));
	  }
	  if (FCode.equalsIgnoreCase("AppntAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppntAge()));
	  }
	  if (FCode.equalsIgnoreCase("NativePlace"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNativePlace()));
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
	  if (FCode.equalsIgnoreCase("StartWorkDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStartWorkDate())));
	  }
	  if (FCode.equalsIgnoreCase("Position"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPosition()));
	  }
	  if (FCode.equalsIgnoreCase("Salary"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSalary()));
	  }
	  if (FCode.equalsIgnoreCase("OccupationType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationType()));
	  }
	  if (FCode.equalsIgnoreCase("OccupationCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationCode()));
	  }
	  if (FCode.equalsIgnoreCase("SmokeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSmokeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BlackListFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBlackListFlag()));
	  }
	  if (FCode.equalsIgnoreCase("RelationToInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelationToInsured()));
	  }
	  if (FCode.equalsIgnoreCase("InnerFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInnerFlag()));
	  }
	  if (FCode.equalsIgnoreCase("AppHosBlack"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppHosBlack()));
	  }
	  if (FCode.equalsIgnoreCase("AppHosName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppHosName()));
	  }
	  if (FCode.equalsIgnoreCase("HealthTellConTent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getHealthTellConTent()));
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
	  if (FCode.equalsIgnoreCase("SamePhone"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSamePhone()));
	  }
	  if (FCode.equalsIgnoreCase("DisabilityImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDisabilityImpart()));
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
