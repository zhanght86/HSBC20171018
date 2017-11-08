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
 * <p>ClassName: BOMBqInsured </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-03-26
 */

public class BOMBqInsured extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 投保年龄 */
	private Double InsuredAppAge;

	/** 职业审核标记 */
	private String OccupaToExamineFlag;

	/** 第二客户号码 */
	private String SecondInsuredNo;

	/** 吸烟状况 */
	private String SmokeFlag;

	/** 职业编码 */
	private String OccupationCode;

	/** 职业类别 */
	private String OccupationType;

	/** 参加工作日期 */
	private Date StartWorkDate;

	/** 入司日期 */
	private Date JoinCompanyDate;

	/** 户口所在地 */
	private String RgtAddress;

	/** 风险保额 */
	private Double SumDangerAmnt;

	/** 国籍 */
	private String NativePlace;

	/** 出生日期 */
	private Date Birthday;

	/** 被保人姓名 */
	private Double InsuredName;

	/** 性别 */
	private String Sex;

	/** 与投保人关系 */
	private String RelationToAppnt;

	/** 与主被保人关系 */
	private String RelationToMInsured;

	/** 客户号码 */
	private String InsuredNo;

	/** 驾照 */
	private String License;

	/** 驾照类型 */
	private String LicenseType;

	/** 婚姻状况 */
	private String Marriage;

	/** 结婚日期 */
	private Date MarriageDate;

	/** 信用等级 */
	private String CreditGrade;

	/** 学历 */
	private String Degree;

	/** 民族 */
	private String Nationality;

	/** 工资 */
	private Double Salary;

	/** 职位 */
	private String Position;



	// @Constructor
	public BOMBqInsured()
	{  }

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

	public void setOccupaToExamineFlag(String  OccupaToExamineFlag )
	{
	  this.OccupaToExamineFlag = OccupaToExamineFlag;
 	}

	public String getOccupaToExamineFlag()
	{
	  return OccupaToExamineFlag;
	}

	public void setSecondInsuredNo(String  SecondInsuredNo )
	{
	  this.SecondInsuredNo = SecondInsuredNo;
 	}

	public String getSecondInsuredNo()
	{
	  return SecondInsuredNo;
	}

	public void setSmokeFlag(String  SmokeFlag )
	{
	  this.SmokeFlag = SmokeFlag;
 	}

	public String getSmokeFlag()
	{
	  return SmokeFlag;
	}

	public void setOccupationCode(String  OccupationCode )
	{
	  this.OccupationCode = OccupationCode;
 	}

	public String getOccupationCode()
	{
	  return OccupationCode;
	}

	public void setOccupationType(String  OccupationType )
	{
	  this.OccupationType = OccupationType;
 	}

	public String getOccupationType()
	{
	  return OccupationType;
	}

	public void setStartWorkDate(Date  StartWorkDate )
	{
	  this.StartWorkDate = StartWorkDate;
 	}

	public Date getStartWorkDate()
	{
	  return StartWorkDate;
	}

	public void setJoinCompanyDate(Date  JoinCompanyDate )
	{
	  this.JoinCompanyDate = JoinCompanyDate;
 	}

	public Date getJoinCompanyDate()
	{
	  return JoinCompanyDate;
	}

	public void setRgtAddress(String  RgtAddress )
	{
	  this.RgtAddress = RgtAddress;
 	}

	public String getRgtAddress()
	{
	  return RgtAddress;
	}

	public void setSumDangerAmnt( Double SumDangerAmnt )
	{
	  this.SumDangerAmnt = SumDangerAmnt;
 	}

	public Double getSumDangerAmnt()
	{
	  return SumDangerAmnt;
	}

	public void setNativePlace(String  NativePlace )
	{
	  this.NativePlace = NativePlace;
 	}

	public String getNativePlace()
	{
	  return NativePlace;
	}

	public void setBirthday(Date  Birthday )
	{
	  this.Birthday = Birthday;
 	}

	public Date getBirthday()
	{
	  return Birthday;
	}

	public void setInsuredName( Double InsuredName )
	{
	  this.InsuredName = InsuredName;
 	}

	public Double getInsuredName()
	{
	  return InsuredName;
	}

	public void setSex(String  Sex )
	{
	  this.Sex = Sex;
 	}

	public String getSex()
	{
	  return Sex;
	}

	public void setRelationToAppnt(String  RelationToAppnt )
	{
	  this.RelationToAppnt = RelationToAppnt;
 	}

	public String getRelationToAppnt()
	{
	  return RelationToAppnt;
	}

	public void setRelationToMInsured(String  RelationToMInsured )
	{
	  this.RelationToMInsured = RelationToMInsured;
 	}

	public String getRelationToMInsured()
	{
	  return RelationToMInsured;
	}

	public void setInsuredNo(String  InsuredNo )
	{
	  this.InsuredNo = InsuredNo;
 	}

	public String getInsuredNo()
	{
	  return InsuredNo;
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

	if("InsuredAppAge".equals(FCode))
		{
			setInsuredAppAge(Double.valueOf(FValue));
		}

	if("OccupaToExamineFlag".equals(FCode))
		{
		    setOccupaToExamineFlag(FValue);
		}

	if("SecondInsuredNo".equals(FCode))
		{
		    setSecondInsuredNo(FValue);
		}

	if("SmokeFlag".equals(FCode))
		{
		    setSmokeFlag(FValue);
		}

	if("OccupationCode".equals(FCode))
		{
		    setOccupationCode(FValue);
		}

	if("OccupationType".equals(FCode))
		{
		    setOccupationType(FValue);
		}

	if("StartWorkDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setStartWorkDate(d);
		}

	if("JoinCompanyDate".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setJoinCompanyDate(d);
		}

	if("RgtAddress".equals(FCode))
		{
		    setRgtAddress(FValue);
		}

	if("SumDangerAmnt".equals(FCode))
		{
			setSumDangerAmnt(Double.valueOf(FValue));
		}

	if("NativePlace".equals(FCode))
		{
		    setNativePlace(FValue);
		}

	if("Birthday".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setBirthday(d);
		}

	if("InsuredName".equals(FCode))
		{
			setInsuredName(Double.valueOf(FValue));
		}

	if("Sex".equals(FCode))
		{
		    setSex(FValue);
		}

	if("RelationToAppnt".equals(FCode))
		{
		    setRelationToAppnt(FValue);
		}

	if("RelationToMInsured".equals(FCode))
		{
		    setRelationToMInsured(FValue);
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("License".equals(FCode))
		{
		    setLicense(FValue);
		}

	if("LicenseType".equals(FCode))
		{
		    setLicenseType(FValue);
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

	if("CreditGrade".equals(FCode))
		{
		    setCreditGrade(FValue);
		}

	if("Degree".equals(FCode))
		{
		    setDegree(FValue);
		}

	if("Nationality".equals(FCode))
		{
		    setNationality(FValue);
		}

	if("Salary".equals(FCode))
		{
			setSalary(Double.valueOf(FValue));
		}

	if("Position".equals(FCode))
		{
		    setPosition(FValue);
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
	  if (FCode.equalsIgnoreCase("InsuredAppAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredAppAge()));
	  }
	  if (FCode.equalsIgnoreCase("OccupaToExamineFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupaToExamineFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SecondInsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSecondInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("SmokeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSmokeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OccupationCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationCode()));
	  }
	  if (FCode.equalsIgnoreCase("OccupationType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationType()));
	  }
	  if (FCode.equalsIgnoreCase("StartWorkDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getStartWorkDate())));
	  }
	  if (FCode.equalsIgnoreCase("JoinCompanyDate"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getJoinCompanyDate())));
	  }
	  if (FCode.equalsIgnoreCase("RgtAddress"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRgtAddress()));
	  }
	  if (FCode.equalsIgnoreCase("SumDangerAmnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSumDangerAmnt()));
	  }
	  if (FCode.equalsIgnoreCase("NativePlace"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNativePlace()));
	  }
	  if (FCode.equalsIgnoreCase("Birthday"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getBirthday())));
	  }
	  if (FCode.equalsIgnoreCase("InsuredName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredName()));
	  }
	  if (FCode.equalsIgnoreCase("Sex"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSex()));
	  }
	  if (FCode.equalsIgnoreCase("RelationToAppnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelationToAppnt()));
	  }
	  if (FCode.equalsIgnoreCase("RelationToMInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelationToMInsured()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("License"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLicense()));
	  }
	  if (FCode.equalsIgnoreCase("LicenseType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLicenseType()));
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
	  if (FCode.equalsIgnoreCase("CreditGrade"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCreditGrade()));
	  }
	  if (FCode.equalsIgnoreCase("Degree"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDegree()));
	  }
	  if (FCode.equalsIgnoreCase("Nationality"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNationality()));
	  }
	  if (FCode.equalsIgnoreCase("Salary"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSalary()));
	  }
	  if (FCode.equalsIgnoreCase("Position"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPosition()));
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
