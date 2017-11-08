

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

/**
 * <p>ClassName: BOMCalInsured </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-04-06
 */

public class BOMCalInsured extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 年龄 */
	private Double InsuredAge;

	/** 加费比例 */
	private Double AddFeeRate;

	/** EM值 */
	private Double EM;

	/** 加费倍数 */
	private Double Multiple;

	/** 客户号码 */
	private String InsuredNo;

	/** 与主被保人关系 */
	private String RelationToMainInsured;

	/** 性别 */
	private String Sex;

	/** 出生日期 */
	private Date Birthday;

	/** 职业类别 */
	private String OccupationType;

	/** 投保年龄 */
	private Double AppAge;

	/** 吸烟状况 */
	private String SmokeFlag;

	/** 出险时点年龄 */
	private Double InsureAccAge;
	
	
	/** Residence */
	private String Residence;

	/** UnderwritingClass */
	private String UnderwritingClass;


	// @Constructor
	public BOMCalInsured()
	{  }

	public void setInsuredAge( Double InsuredAge )
	{
	   if(!(new BOMPreCheck().CheckIngeter(String.valueOf(InsuredAge)))){
	  	  return;
	    }else{
	    	this.InsuredAge = InsuredAge;
	   }
 	}

	public Double getInsuredAge()
	{
	  return InsuredAge;
	}

	public void setAddFeeRate( Double AddFeeRate )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(AddFeeRate)))){
	  	  return;
	    }else{
	    	this.AddFeeRate = AddFeeRate;
	   }
 	}

	public Double getAddFeeRate()
	{
	  return AddFeeRate;
	}

	public void setEM( Double EM )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(EM)))){
	  	  return;
	    }else{
	    	this.EM = EM;
	   }
 	}

	public Double getEM()
	{
	  return EM;
	}

	public void setMultiple( Double Multiple )
	{
	   if(!(new BOMPreCheck().CheckPlus(String.valueOf(Multiple)))){
	  	  return;
	    }else{
	    	this.Multiple = Multiple;
	   }
 	}

	public Double getMultiple()
	{
	  return Multiple;
	}

	public void setInsuredNo(String  InsuredNo )
	{
	  this.InsuredNo = InsuredNo;
 	}

	public String getInsuredNo()
	{
	  return InsuredNo;
	}

	public void setRelationToMainInsured(String  RelationToMainInsured )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(RelationToMainInsured)))){
	    	return;
  	   }else{
	    	this.RelationToMainInsured = RelationToMainInsured;
	  }
 	}

	public String getRelationToMainInsured()
	{
	  return RelationToMainInsured;
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

	public void setAppAge( Double AppAge )
	{
	   if(!(new BOMPreCheck().CheckIngeter(String.valueOf(AppAge)))){
	  	  return;
	    }else{
	    	this.AppAge = AppAge;
	   }
 	}

	public Double getAppAge()
	{
	  return AppAge;
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

	public void setInsureAccAge( Double InsureAccAge )
	{
	   if(!(new BOMPreCheck().CheckIngeter(String.valueOf(InsureAccAge)))){
	  	  return;
	    }else{
	    	this.InsureAccAge = InsureAccAge;
	   }
 	}

	public Double getInsureAccAge()
	{
	  return InsureAccAge;
	}
	
	public void setResidence(String  Residence )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(Residence)))){
	    	return;
  	   }else{
	    	this.Residence = Residence;
	  }
 	}

	public String getResidence()
	{
	  return Residence;
	}

	public void setUnderwritingClass(String  UnderwritingClass )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(UnderwritingClass)))){
	    	return;
  	   }else{
	    	this.UnderwritingClass = UnderwritingClass;
	  }
 	}

	public String getUnderwritingClass()
	{
	  return UnderwritingClass;
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

	if("InsuredAge".equals(FCode))
		{
			setInsuredAge(Double.valueOf(FValue));
		}

	if("AddFeeRate".equals(FCode))
		{
			setAddFeeRate(Double.valueOf(FValue));
		}

	if("EM".equals(FCode))
		{
			setEM(Double.valueOf(FValue));
		}

	if("Multiple".equals(FCode))
		{
			setMultiple(Double.valueOf(FValue));
		}

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("RelationToMainInsured".equals(FCode))
		{
		    setRelationToMainInsured(FValue);
		}

	if("Sex".equals(FCode))
		{
		    setSex(FValue);
		}

	if("Birthday".equals(FCode))
		{
		Date d = fDate.getDate(FValue);
			setBirthday(d);
		}

	if("OccupationType".equals(FCode))
		{
		    setOccupationType(FValue);
		}

	if("AppAge".equals(FCode))
		{
			setAppAge(Double.valueOf(FValue));
		}

	if("SmokeFlag".equals(FCode))
		{
		    setSmokeFlag(FValue);
		}

	if("InsureAccAge".equals(FCode))
		{
			setInsureAccAge(Double.valueOf(FValue));
		}
	if("Residence".equals(FCode))
	{
	    setResidence(FValue);
	}

	if("UnderwritingClass".equals(FCode))
	{
	    setUnderwritingClass(FValue);
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
	  if (FCode.equalsIgnoreCase("InsuredAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredAge()));
	  }
	  if (FCode.equalsIgnoreCase("AddFeeRate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAddFeeRate()));
	  }
	  if (FCode.equalsIgnoreCase("EM"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getEM()));
	  }
	  if (FCode.equalsIgnoreCase("Multiple"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMultiple()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("RelationToMainInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelationToMainInsured()));
	  }
	  if (FCode.equalsIgnoreCase("Sex"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSex()));
	  }
	  if (FCode.equalsIgnoreCase("Birthday"))
	  {
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     strReturn = StrTool.GBKToUnicode(String.valueOf(sdf.format(getBirthday())));
	  }
	  if (FCode.equalsIgnoreCase("OccupationType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOccupationType()));
	  }
	  if (FCode.equalsIgnoreCase("AppAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppAge()));
	  }
	  if (FCode.equalsIgnoreCase("SmokeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSmokeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("InsureAccAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsureAccAge()));
	  }
	  if (FCode.equalsIgnoreCase("Residence"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getResidence()));
	  }
	  if (FCode.equalsIgnoreCase("UnderwritingClass"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUnderwritingClass()));
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

