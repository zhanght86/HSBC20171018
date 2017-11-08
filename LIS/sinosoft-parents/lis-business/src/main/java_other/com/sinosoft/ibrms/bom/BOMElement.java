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
 * <p>ClassName: BOMElement </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2010-12-15
 */

public class BOMElement extends AbstractBOM
{
private static Logger logger = Logger.getLogger(BOMElement.class);


	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 保费 */
	private Double Prem;

	/** 客户号 */
	private Double InsuredNo;

	/** 被保人年龄 */
	private Double AppAge;

	/** 被保人姓名 */
	private Double InsuredName;

	/** 被保人性别 */
	private Double Sex;

	/** 投保份数 */
	private Double Mult;

	/** 投保单号码 */
	private Double PolNo;

	/** 主险号码 */
	private Double MainPolNo;

	/** 险种编码 */
	private Double RiskCode;

	/** 生效日期 */
	private Double CValiDate;

	/** 保额 */
	private Double Amnt;



	// @Constructor
	public BOMElement()
	{  }

	public void setPrem( Double Prem )
	{
	  this.Prem = Prem;
 	}

	public Double getPrem()
	{
	  return Prem;
	}

	public void setInsuredNo( Double InsuredNo )
	{
	  this.InsuredNo = InsuredNo;
 	}

	public Double getInsuredNo()
	{
	  return InsuredNo;
	}

	public void setAppAge( Double AppAge )
	{
	  this.AppAge = AppAge;
 	}

	public Double getAppAge()
	{
	  return AppAge;
	}

	public void setInsuredName( Double InsuredName )
	{
	  this.InsuredName = InsuredName;
 	}

	public Double getInsuredName()
	{
	  return InsuredName;
	}

	public void setSex( Double Sex )
	{
	  this.Sex = Sex;
 	}

	public Double getSex()
	{
	  return Sex;
	}

	public void setMult( Double Mult )
	{
	  this.Mult = Mult;
 	}

	public Double getMult()
	{
	  return Mult;
	}

	public void setPolNo( Double PolNo )
	{
	  this.PolNo = PolNo;
 	}

	public Double getPolNo()
	{
	  return PolNo;
	}

	public void setMainPolNo( Double MainPolNo )
	{
	  this.MainPolNo = MainPolNo;
 	}

	public Double getMainPolNo()
	{
	  return MainPolNo;
	}

	public void setRiskCode( Double RiskCode )
	{
	  this.RiskCode = RiskCode;
 	}

	public Double getRiskCode()
	{
	  return RiskCode;
	}

	public void setCValiDate( Double CValiDate )
	{
	  this.CValiDate = CValiDate;
 	}

	public Double getCValiDate()
	{
	  return CValiDate;
	}

	public void setAmnt( Double Amnt )
	{
	  this.Amnt = Amnt;
 	}

	public Double getAmnt()
	{
	  return Amnt;
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

	if("Prem".equals(FCode))
		{
			setPrem(Double.valueOf(FValue));
		}

	if("InsuredNo".equals(FCode))
		{
			setInsuredNo(Double.valueOf(FValue));
		}

	if("AppAge".equals(FCode))
		{
			setAppAge(Double.valueOf(FValue));
		}

	if("InsuredName".equals(FCode))
		{
			setInsuredName(Double.valueOf(FValue));
		}

	if("Sex".equals(FCode))
		{
			setSex(Double.valueOf(FValue));
		}

	if("Mult".equals(FCode))
		{
			setMult(Double.valueOf(FValue));
		}

	if("PolNo".equals(FCode))
		{
			setPolNo(Double.valueOf(FValue));
		}

	if("MainPolNo".equals(FCode))
		{
			setMainPolNo(Double.valueOf(FValue));
		}

	if("RiskCode".equals(FCode))
		{
			setRiskCode(Double.valueOf(FValue));
		}

	if("CValiDate".equals(FCode))
		{
			setCValiDate(Double.valueOf(FValue));
		}

	if("Amnt".equals(FCode))
		{
			setAmnt(Double.valueOf(FValue));
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
	  if (FCode.equalsIgnoreCase("Prem"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPrem()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("AppAge"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAppAge()));
	  }
	  if (FCode.equalsIgnoreCase("InsuredName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredName()));
	  }
	  if (FCode.equalsIgnoreCase("Sex"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSex()));
	  }
	  if (FCode.equalsIgnoreCase("Mult"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getMult()));
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
	  if (FCode.equalsIgnoreCase("CValiDate"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCValiDate()));
	  }
	  if (FCode.equalsIgnoreCase("Amnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAmnt()));
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
