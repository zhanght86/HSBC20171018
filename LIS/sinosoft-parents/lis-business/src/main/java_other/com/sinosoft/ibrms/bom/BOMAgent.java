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
 * <p>ClassName: BOMAgent </p>
 * <p>Description: BOM 类文件 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2010-03-17
 */

public class BOMAgent extends AbstractBOM
{
private static Logger logger = Logger.getLogger(BOMAgent.class);


	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";


	/** 等级是否为差 */
	private String LevelYN;

	/** 号码 */
	private String AgentCode;

	/** 销售资格标记 */
	private String SaleQuaf;

	/** 类别 */
	private String AgentKind;

	/** 状态 */
	private String AgentState;

	/** 管理机构 */
	private String ManageCom;

	/** 内勤标记 */
	private String InsideFlag;

	/** 展业类型 */
	private String BranchType;

	/** 黑名单标记 */
	private String AgentBlankFlag;

	/** 资格证号 */
	private String QuafNo;

	/** 业务员告知是否异常 */
	private String SpecAImpart;

	/** 业务员告知投保人投保经过异常 */
	private String ProAImpart;

	/** 与被保人关系 */
	private String RelToAppnt;

	/** 差异化核保等级 */
	private String UWLevel;



	// @Constructor
	public BOMAgent()
	{  }

	public void setLevelYN(String  LevelYN )
	{
	  this.LevelYN = LevelYN;
 	}

	public String getLevelYN()
	{
	  return LevelYN;
	}

	public void setAgentCode(String  AgentCode )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(AgentCode)))){
	    	return;
  	   }else{
	    	this.AgentCode = AgentCode;
	  }
 	}

	public String getAgentCode()
	{
	  return AgentCode;
	}

	public void setSaleQuaf(String  SaleQuaf )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(SaleQuaf)))){
	    	return;
  	   }else{
	    	this.SaleQuaf = SaleQuaf;
	  }
 	}

	public String getSaleQuaf()
	{
	  return SaleQuaf;
	}

	public void setAgentKind(String  AgentKind )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AgentKind)))){
	    	return;
  	   }else{
	    	this.AgentKind = AgentKind;
	  }
 	}

	public String getAgentKind()
	{
	  return AgentKind;
	}

	public void setAgentState(String  AgentState )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AgentState)))){
	    	return;
  	   }else{
	    	this.AgentState = AgentState;
	  }
 	}

	public String getAgentState()
	{
	  return AgentState;
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

	public void setInsideFlag(String  InsideFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(InsideFlag)))){
	    	return;
  	   }else{
	    	this.InsideFlag = InsideFlag;
	  }
 	}

	public String getInsideFlag()
	{
	  return InsideFlag;
	}

	public void setBranchType(String  BranchType )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(BranchType)))){
	    	return;
  	   }else{
	    	this.BranchType = BranchType;
	  }
 	}

	public String getBranchType()
	{
	  return BranchType;
	}

	public void setAgentBlankFlag(String  AgentBlankFlag )
	{
	  if(!(new BOMPreCheck().CheckSequence(String.valueOf(AgentBlankFlag)))){
	    	return;
  	   }else{
	    	this.AgentBlankFlag = AgentBlankFlag;
	  }
 	}

	public String getAgentBlankFlag()
	{
	  return AgentBlankFlag;
	}

	public void setQuafNo(String  QuafNo )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(QuafNo)))){
	    	return;
  	   }else{
	    	this.QuafNo = QuafNo;
	  }
 	}

	public String getQuafNo()
	{
	  return QuafNo;
	}

	public void setSpecAImpart(String  SpecAImpart )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(SpecAImpart)))){
	    	return;
  	   }else{
	    	this.SpecAImpart = SpecAImpart;
	  }
 	}

	public String getSpecAImpart()
	{
	  return SpecAImpart;
	}

	public void setProAImpart(String  ProAImpart )
	{
	  if(!(new BOMPreCheck().CheckNotNull(String.valueOf(ProAImpart)))){
	    	return;
  	   }else{
	    	this.ProAImpart = ProAImpart;
	  }
 	}

	public String getProAImpart()
	{
	  return ProAImpart;
	}

	public void setRelToAppnt(String  RelToAppnt )
	{
	  this.RelToAppnt = RelToAppnt;
 	}

	public String getRelToAppnt()
	{
	  return RelToAppnt;
	}

	public void setUWLevel(String  UWLevel )
	{
	  this.UWLevel = UWLevel;
 	}

	public String getUWLevel()
	{
	  return UWLevel;
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

	if("LevelYN".equals(FCode))
		{
		    setLevelYN(FValue);
		}

	if("AgentCode".equals(FCode))
		{
		    setAgentCode(FValue);
		}

	if("SaleQuaf".equals(FCode))
		{
		    setSaleQuaf(FValue);
		}

	if("AgentKind".equals(FCode))
		{
		    setAgentKind(FValue);
		}

	if("AgentState".equals(FCode))
		{
		    setAgentState(FValue);
		}

	if("ManageCom".equals(FCode))
		{
		    setManageCom(FValue);
		}

	if("InsideFlag".equals(FCode))
		{
		    setInsideFlag(FValue);
		}

	if("BranchType".equals(FCode))
		{
		    setBranchType(FValue);
		}

	if("AgentBlankFlag".equals(FCode))
		{
		    setAgentBlankFlag(FValue);
		}

	if("QuafNo".equals(FCode))
		{
		    setQuafNo(FValue);
		}

	if("SpecAImpart".equals(FCode))
		{
		    setSpecAImpart(FValue);
		}

	if("ProAImpart".equals(FCode))
		{
		    setProAImpart(FValue);
		}

	if("RelToAppnt".equals(FCode))
		{
		    setRelToAppnt(FValue);
		}

	if("UWLevel".equals(FCode))
		{
		    setUWLevel(FValue);
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
	  if (FCode.equalsIgnoreCase("LevelYN"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getLevelYN()));
	  }
	  if (FCode.equalsIgnoreCase("AgentCode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAgentCode()));
	  }
	  if (FCode.equalsIgnoreCase("SaleQuaf"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSaleQuaf()));
	  }
	  if (FCode.equalsIgnoreCase("AgentKind"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAgentKind()));
	  }
	  if (FCode.equalsIgnoreCase("AgentState"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAgentState()));
	  }
	  if (FCode.equalsIgnoreCase("ManageCom"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getManageCom()));
	  }
	  if (FCode.equalsIgnoreCase("InsideFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInsideFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BranchType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBranchType()));
	  }
	  if (FCode.equalsIgnoreCase("AgentBlankFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAgentBlankFlag()));
	  }
	  if (FCode.equalsIgnoreCase("QuafNo"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQuafNo()));
	  }
	  if (FCode.equalsIgnoreCase("SpecAImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSpecAImpart()));
	  }
	  if (FCode.equalsIgnoreCase("ProAImpart"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getProAImpart()));
	  }
	  if (FCode.equalsIgnoreCase("RelToAppnt"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRelToAppnt()));
	  }
	  if (FCode.equalsIgnoreCase("UWLevel"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWLevel()));
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
