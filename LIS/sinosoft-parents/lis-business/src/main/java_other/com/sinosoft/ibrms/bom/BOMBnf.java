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
 * <p>ClassName: BOMBnf </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-04-01
 */

public class BOMBnf extends AbstractBOM
{

	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";

	private BOMInsured tBOMInsured;

	/** 被保人号码 */
	private String InsuredNo;

	/** 客户号码 */
	private String CustomerNo;

	/** 与被保人关系 */
	private String RelationToInsured;

	/** 受益人姓名 */
	private String BnfName;

	/** 性别 */
	private String Sex;

	/** 出生日期 */
	private Date Birthday;

	/** 受益份额 */
	private Double BnfLot;

	/** 类别 */
	private String BnfType;

	/** 级别 */
	private String BnfGrade;



	// @Constructor
	public BOMBnf()
	{  }

	public void setFatherBOM(BOMInsured mBOMInsured)
	{
		this.tBOMInsured = mBOMInsured;
	}
 
	public AbstractBOM getFatherBOM()
	{
		return tBOMInsured;
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

	public void setBnfName(String  BnfName )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(BnfName)))){
	    	return;
  	   }else{
	    	this.BnfName = BnfName;
	  }
 	}

	public String getBnfName()
	{
	  return BnfName;
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

	public void setBnfLot( Double BnfLot )
	{
	   if(!(new BOMPreCheck().CheckNotNull(String.valueOf(BnfLot)))){
	  	  return;
	    }else{
	    	this.BnfLot = BnfLot;
	   }
 	}

	public Double getBnfLot()
	{
	  return BnfLot;
	}

	public void setBnfType(String  BnfType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BnfType)))){
	    	return;
  	   }else{
	    	this.BnfType = BnfType;
	  }
 	}

	public String getBnfType()
	{
	  return BnfType;
	}

	public void setBnfGrade(String  BnfGrade )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BnfGrade)))){
	    	return;
  	   }else{
	    	this.BnfGrade = BnfGrade;
	  }
 	}

	public String getBnfGrade()
	{
	  return BnfGrade;
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

	if("InsuredNo".equals(FCode))
		{
		    setInsuredNo(FValue);
		}

	if("CustomerNo".equals(FCode))
		{
		    setCustomerNo(FValue);
		}

	if("RelationToInsured".equals(FCode))
		{
		    setRelationToInsured(FValue);
		}

	if("BnfName".equals(FCode))
		{
		    setBnfName(FValue);
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

	if("BnfLot".equals(FCode))
		{
			setBnfLot(Double.valueOf(FValue));
		}

	if("BnfType".equals(FCode))
		{
		    setBnfType(FValue);
		}

	if("BnfGrade".equals(FCode))
		{
		    setBnfGrade(FValue);
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
	  if (FCode.equalsIgnoreCase("InsuredNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsuredNo()));
	  }
	  if (FCode.equalsIgnoreCase("CustomerNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCustomerNo()));
	  }
	  if (FCode.equalsIgnoreCase("RelationToInsured"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelationToInsured()));
	  }
	  if (FCode.equalsIgnoreCase("BnfName"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBnfName()));
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
	  if (FCode.equalsIgnoreCase("BnfLot"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBnfLot()));
	  }
	  if (FCode.equalsIgnoreCase("BnfType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBnfType()));
	  }
	  if (FCode.equalsIgnoreCase("BnfGrade"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBnfGrade()));
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
