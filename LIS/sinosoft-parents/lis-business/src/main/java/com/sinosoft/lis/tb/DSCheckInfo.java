package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import java.util.*;

/**
 * <p>Title: lis</p>
 * <p>Description:双岗校验JAVA类 </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: sinosoft</p>
 * @author tongmeng
 * @version 6.0
 */

public class DSCheckInfo extends BasicCheckInfo {
private static Logger logger = Logger.getLogger(DSCheckInfo.class);
  
  private DSPubCheckCol mDSPubCheckCol = new DSPubCheckCol();
  
  public DSCheckInfo() {
  }
  
 // private TransferData mTransferData = new TransferData();

  public DSCheckInfo(VData cInputData)
  {
    super(cInputData);
  }
 
  /**
   * 校验代理人填写日期
   * @return
   */
 public String checkAgentSignDate()
 {
	 String tResult = "";
	 String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  String tPolType = tContNo.substring(0,4);
	  String tPolApplyDate = (String)this.mTransferData.getValueByName("LBPOCont.PolApplyDate");
	 String tAgentSignDate = (String)this.mTransferData.getValueByName("LBPOCont.AgentSignDate");

	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621")||tPolType.equals("8616"))
	  {
		  String tRule = "|notnull&&WITHOUT:?^？&&DATE"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentSignDate,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentSignDate,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  if(tAgentSignDate==null||tAgentSignDate.equals("")||tAgentSignDate.equals("null"))
	  {
		  return "";
	  }
	  String tSQL="";
	  if(tPolApplyDate!=null&&!"null".equals(tPolApplyDate)&&!"".equals(tPolApplyDate)){
		  tSQL = "select (case count(1) when 0 then 0 else 1 end) from dual where to_date('?polappdate?','yyyy-mm-dd')-to_date('agentsigndate','yyyy-mm-dd')>30 ";
	  }
	  SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
	  sqlbv36.sql(tSQL);
	  sqlbv36.put("polappdate", tPolApplyDate);
	  sqlbv36.put("agentsigndate", tAgentSignDate);
	 ExeSQL tExeSQL = new ExeSQL();
	 String tValue = tExeSQL.getOneValue(sqlbv36);
	 if(!tValue.equals("")&&Integer.parseInt(tValue)==1)
	 {
		 tResult = "业务员签名日期距投保申请日期超过30天";
	 }
	 return tResult;
 }
 
 /**
  * 校验业务员签名
  * @return
  */
 public String checkAgentSignName()
 {
	 String tResult = "";
	 String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  String tPolType = tContNo.substring(0,4);
	 String tAgentSignName = (String)this.mTransferData.getValueByName("LBPOCont.SignAgentName");

	// String tErrorInfo = ""; 
	 DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621")||tPolType.equals("8616"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentSignName,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  if(tAgentSignName!=null&&!"null".equals(tAgentSignName)&&!"".equals(tAgentSignName)){
			  if(tAgentSignName.equals("否")){
				  tResult=tResult+"业务员签名录入为‘否’";
			  }
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentSignName,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
 }
 
 /**
  * 校验业务员签名
  * @return
  */
 public String checkPayIntv()
 {//notnull&WITHOUT:?^？^1^-1^3^6&code:PayIntv
	 String tResult = "";
	 String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  String tPolType = tContNo.substring(0,4);
	 String tPayIntv = (String)this.mTransferData.getValueByName("LBPOCont.PayIntv");
	 //简易没有交费间隔
	 if(!tPolType.equals("8616"))
	  {
		 // String tErrorInfo = ""; 
		 DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
		 String tRule = "|notnull&WITHOUT:?^？&code:PayIntv";
		 String tErrorInfo = tDSPubCheckCol.checkValueByRule(tPayIntv,tRule,null,"0");
		 if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		 {
			 if(tErrorInfo.indexOf("(")!=-1)
			 {
				 tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			 }
			 return tErrorInfo;
		 }
		 if(tPayIntv!=null&&(tPayIntv.equals("-1")||tPayIntv.equals("1")||tPayIntv.equals("3")||tPayIntv.equals("6")))
		 {
			 tResult = "交费方式不为年交或趸交";
		 }
		 
	  }
	  return tResult;
 }
 
 
  /**
   * 校验受益人性别和证件号
   * @return
   */
  public String checkBnfSexAndIdNo()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOBnf.Sex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOBnf.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOBnf.ContNo");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOBnf.InputNo");
	  String tName = (String)this.mTransferData.getValueByName("LBPOBnf.Name");
	  String tFillNo = (String)this.mTransferData.getValueByName("LBPOBnf.FillNo");
	  String tPolNo = (String)this.mTransferData.getValueByName("LBPOBnf.PolNo");
	  String tInsuredNo = (String)this.mTransferData.getValueByName("LBPOBnf.InsuredNo");
	  //tongmeng 2009-02-11 
	  //注释受益人和性别的校验
	  //tResult = this.checkIDNoAndSex(tSex, tIDType, tIDNo);
	  //tongmeng 2009-02-12 add
	  //增加证件类型和证件号码的校验
	  //校验受益人和投保人关系
	  LBPOInsuredDB tLBPOInsuredDB=new LBPOInsuredDB();
	  LBPOInsuredSet tLBPOInsuredSet=new LBPOInsuredSet();
	  LBPOAppntDB tLBPOAppntDB = new LBPOAppntDB();
	  LBPOAppntSet tLBPOAppntSet = new LBPOAppntSet();
	  //查询被保人
	  tLBPOInsuredDB.setPrtNo(tContNo);
	  tLBPOInsuredDB.setInputNo(tInputNo);
	  tLBPOInsuredSet = tLBPOInsuredDB.query();
	  //查询投保人
	  tLBPOAppntDB.setPrtNo(tContNo);
	  tLBPOAppntDB.setInputNo(tInputNo);
	  tLBPOAppntSet = tLBPOAppntDB.query();
	  if(tName==null) 
	  {
		  tName = "";
	  }
	  if(tIDNo==null)
	  {
		  tIDNo = "";
	  }
	  if(tIDType==null)
	  {
		  tIDType = "";
	  }
	  for(int i=1;i<=tLBPOInsuredSet.size();i++){
		  String tInsuredName= tLBPOInsuredSet.get(i).getName();
		  String tInsuredIDNo = tLBPOInsuredSet.get(i).getIDNo();
		  if(tName.equals(tInsuredName)&&
		     !tIDNo.replaceAll("[^0-9]", "").equals(tInsuredIDNo.replaceAll("[^0-9]", ""))){
			  //受益人同投保人姓名相同，但是证件号码不同
			  tResult =tResult+"受益人同被保人姓名相同为："+tName+",但是证件号码不同;";
		  }
	  }
	  for(int j=1;j<=tLBPOAppntSet.size();j++){
		  String tAppntName= tLBPOAppntSet.get(j).getAppntName();
		  String tAppntIDNo = tLBPOAppntSet.get(j).getIDNo();
		  if(tAppntIDNo==null)
		  {
			  tAppntIDNo = "";
		  }
		  if(tAppntName==null)
		  {
			  tAppntName = "";
		  }
		  if(tName.equals(tAppntName)&&
				  !tIDNo.replaceAll("[^0-9]", "").equals(tAppntIDNo.replaceAll("[^0-9]", ""))){
			  //受益人同投保人姓名相同，但是证件号码不同
			  tResult =tResult+"受益人同投保人姓名相同为："+tName+",但是证件号码不同;";
		  }
	  }
	  if(tIDType!=null&&tIDType.equals("0"))
	  {
		  if(tIDNo.length()!=18&&tIDNo.length()!=15)
		  {
			  tResult = "证件类型为身份证,证件号码长度错误!";
		  }
	  }
	  //校验证件号码为空时，其他信息是否为空 如果非空则给出问题件
	  LBPOBnfSchema tLBPOBnfSchema=new LBPOBnfSchema();
	  LBPOBnfDB tLBPOBnfDB=new LBPOBnfDB();
	  tLBPOBnfDB.setInputNo(tInputNo);
	  tLBPOBnfDB.setFillNo(tFillNo);
	  tLBPOBnfDB.setPolNo(tPolNo);
	  tLBPOBnfDB.setInsuredNo(tInsuredNo);
	  if(tLBPOBnfDB.getInfo()){
		  tLBPOBnfSchema = tLBPOBnfDB.getSchema();
		  if(tIDNo==null||"".equals(tIDNo)||"null".equals(tIDNo)){
			  if(mDSPubCheckCol.ifRemove(tLBPOBnfSchema, "03")){
				  //此时不用给出问题件 （IDNo为空并且判断出信息为空）
			  }else{
				  tResult=tResult+"受益人信息不为空,而受益人的证件号码为空;";
			  }
		  }
	  }
  
	  return tResult; 
  }
//tongmeng 2009-03-13 add
  //校验被保人职业编码
  public String checkInsuredOccupationCode()
  {
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOInsured.ContNo");
	  String tOccupationCode = (String)this.mTransferData.getValueByName("LBPOInsured.OccupationCode");

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621")||tPolType.equals("8616"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:occupationcode"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tOccupationCode,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:occupationcode#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tOccupationCode,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
  }
  //校验被保人职业
  public String checkInsuredWorkType()
  {
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOInsured.ContNo");
	  String tWorkType = (String)this.mTransferData.getValueByName("LBPOInsured.WorkType");

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621")||tPolType.equals("8616"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tWorkType,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tWorkType,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
  }
//校验被保人婚姻状况
  public String checkInsuredMarriage()
  {
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOInsured.ContNo");
	  String tMarriage = (String)this.mTransferData.getValueByName("LBPOInsured.Marriage");

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:Marriage"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tMarriage,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:Marriage#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tMarriage,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
  }
  
  //tongmeng 2009-03-13 add
  //校验投保人职业编码
  public String checkAppntOccupationCode()
  {
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOAppnt.ContNo");
	  String tOccupationCode = (String)this.mTransferData.getValueByName("LBPOAppnt.OccupationCode");

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:occupationcode"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tOccupationCode,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:occupationcode#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tOccupationCode,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
  }
  //校验投保人职业
  public String checkAppntWorkType()
  {
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOAppnt.ContNo");
	  String tWorkType = (String)this.mTransferData.getValueByName("LBPOAppnt.WorkType");

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tWorkType,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tWorkType,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
  }
  //校验投保人婚姻状况
  public String checkAppntMarriage()
  {
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOAppnt.ContNo");
	  String tMarriage = (String)this.mTransferData.getValueByName("LBPOAppnt.Marriage");

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:Marriage"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tMarriage,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:Marriage#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tMarriage,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  return tResult;
  }
  /**
   * 校验受益人受益顺序和受益比例
   * @return
   */
  public String checkBnfGradeAndLot()
  {
	  //所有收益比例相加应该为1 ;受益顺序不为1时 收益比例不能为100% 
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOBnf.Sex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOBnf.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");
	  //tongmeng 2009-02-11 
	  //注释受益人和性别的校验
	  //tResult = this.checkIDNoAndSex(tSex, tIDType, tIDNo);
	  //tongmeng 2009-02-12 add
	  //增加证件类型和证件号码的校验
	  if(tIDNo==null)
	  {
		  tIDNo = "";
	  }
	  if(tIDType!=null&&tIDType.equals("0"))
	  {
		  if(tIDNo.length()!=18&&tIDNo.length()!=15)
		  {
			  tResult = "证件类型为身份证,证件号码长度错误!";
		  }
	  }
	  
	  return tResult; 
  }
  /**
   * 校验受益人受地址编号和地址内容
   * @return
   */
  public String checkBnfAddressNo()
  {
	  //受益人地址编码为3 时 需要录入详细地址 
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOBnf.Sex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOBnf.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");
	  //tongmeng 2009-02-11 
	  //注释受益人和性别的校验
	  //tResult = this.checkIDNoAndSex(tSex, tIDType, tIDNo);
	  //tongmeng 2009-02-12 add
	  //增加证件类型和证件号码的校验
	  if(tIDNo==null)
	  {
		  tIDNo = "";
	  }
	  if(tIDType!=null&&tIDType.equals("0"))
	  {
		  if(tIDNo.length()!=18&&tIDNo.length()!=15)
		  {
			  tResult = "证件类型为身份证,证件号码长度错误!";
		  }
	  }
	  
	  return tResult; 
  }
  
  /**
   * 校验受益人姓名
   * @return
   */
  public String checkBnfName()
  {
	  //受益人地址编码为3 时 需要录入详细地址 
	  String tResult = "";
	  String tName = (String)this.mTransferData.getValueByName("LBPOBnf.Name");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOBnf.ContNo");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");
	  String tBnfType = (String)this.mTransferData.getValueByName("LBPOBnf.BnfType");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOBnf.InputNo");
	  if(tIDNo==null||tIDNo.equals("null"))
	  {
		  tIDNo = "";
	  }
	  
	  if(tBnfType==null||tBnfType.equals("null"))
	  {
		  tBnfType = "";
	  }

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&LEN<=10&WITHOUT:?^？"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tName,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&LEN<=10&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tName,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  //查处受益人信息集合
	  LBPOBnfDB tLBPOBnfDB =new LBPOBnfDB();
	  LBPOBnfSet tLBPOBnfSet =new LBPOBnfSet();
	  SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
	  sqlbv37.sql("select * from lbpobnf where inputno ='"+"?inputno?"+"' and contno ='"+"?contno?"+"'");
	  sqlbv37.put("inputno", tInputNo);
	  sqlbv37.put("contno", tContNo);
	  tLBPOBnfSet =tLBPOBnfDB.executeQuery(sqlbv37);
//身故受益人校验
	  if(tBnfType.equals("1"))
	  {
	  /*
	  （2）当受益人与投或被保人证件号码一致时（只对号码中的数字进行判断，不包括其中的符号。
	  如投保人证件号码处填写为“1981-01-01”,受益人证件号码处为“1981.01.01”，
	  则只对数字“19810101”进行判断），华道后台对姓名进行检验，如不相符给出问题件。
	  */

	  if(tPolType.equals("8611")||tPolType.equals("8621")
			  ||tPolType.equals("8625")
			  ||tPolType.equals("8635")
			  ||tPolType.equals("8616"))
	  {
		 //单一投保人和被保人.
		 String tAppntIdNo = "";
		 String tAppntName = "";
		 String tInsuredIdNo = "";
		 String tInsuredName = "";
		 ExeSQL tExeSQL = new ExeSQL();
		 SSRS tSSRS = new SSRS();
		 //获得投保人证件号
		 String tSQL_Appnt = "select (case when idno is null then '' else idno end),(case when appntname is null then '' else appntname end) from lbpoappnt "
			               + " where contno='"+"?contno?"+"' order by inputno desc ";
		 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		 sqlbv.sql(tSQL_Appnt);
		 sqlbv.put("contno", tContNo);
		 tSSRS = tExeSQL.execSQL(sqlbv);
		 if(tSSRS.getMaxRow()>0)
		 {
			 tAppntIdNo = tSSRS.GetText(1,1);
			 tAppntName = tSSRS.GetText(1,2);
		 }
		 if(tAppntName==null)
		 {
			 tAppntName = "";
		 }
		 if(tAppntIdNo==null)
		 {
			 tAppntIdNo = "";
		 }
		 else
		 {
			 tAppntIdNo = tAppntIdNo.replaceAll("[^0-9]", "");
		 }
		 String tSQL_Insured = "select (case when idno is null then '' else idno end),(case when name is null then '' else name end) from lbpoinsured "
			 				 + " where contno='"+"?contno?"+"' order by inputno desc ";
		 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		 sqlbv1.sql(tSQL_Insured);
		 sqlbv1.put("contno", tContNo);
		 tSSRS = tExeSQL.execSQL(sqlbv1);
		 if(tSSRS.getMaxRow()>0)
		 {
			 tInsuredIdNo = tSSRS.GetText(1,1);
			 tInsuredName = tSSRS.GetText(1,2);
		 }
		 if(tInsuredName==null)
		 {
			 tInsuredName = "";
		 }
		 if(tInsuredIdNo==null)
		 {
			 tInsuredIdNo = "";
		 }
		 else
		 {
			 tInsuredIdNo = tInsuredIdNo.replaceAll("[^0-9]", "");
		 }
		 //boolean ifTrue = false;
		 //不为空才做校验
		 if(!tIDNo.equals(""))
		 {
			 if (!tAppntIdNo.equals("")&&tIDNo.equals(tAppntIdNo)
					 &&!tName.equals(tAppntName)) 
			 {
				 //ifTrue = true;
				 tResult = tResult + "身故受益人与投保人证件号相同,但姓名不一致;";
			 }
			 if (!tInsuredIdNo.equals("")&&tIDNo.equals(tInsuredIdNo)
					 &&!tName.equals(tInsuredName)) 
			 {
				 //ifTrue = true;
				 tResult = tResult + "身故受益人与被保人证件号相同,但姓名不一致;";
			 }
		 }
		 return tResult;
			
	  }
	  }
	  else  if(tBnfType.equals("0"))
	  {
		  //生存受益人校验
		  //录入为“空”且受益人资料任意一项有内容，按“空”导入，需给出问题件
//		  int tBnfSize =tLBPOBnfSet.size();
//		  for(int i=1;i<=tBnfSize;i++){
//				LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
//				tLBPOBnfSchema = tLBPOBnfSet.get(i);
//				if(mDSPubCheckCol.ifRemove(tLBPOBnfSchema, "03")){
////					tLBPOBnfSet.remove(tLBPOBnfSchema);
////					tBnfSize--;
////					i--;
//				}else{
//					if(tName==null||"".equals(tName)||"null".equals(tName)){
//						tResult =tResult+"受益人信息不为空，但是姓名为空";
//					}
//				}
//			}
		  
	  }
	  
	  return tResult; 
  }
  
  /**
   * 校验受益人证件类型
   */
  public String checkIDType()
  {
	  //受益人地址编码为3 时 需要录入详细地址 
	 
	  String tResult = "";
	  String tName = (String)this.mTransferData.getValueByName("LBPOBnf.Name");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOBnf.ContNo");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOBnf.IDType");
	  String tBnfType = (String)this.mTransferData.getValueByName("LBPOBnf.BnfType");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOBnf.InputNo");
	  String tBirthday = (String)this.mTransferData.getValueByName("LBPOBnf.Birthday");
	  String tRelationToInsured = (String)this.mTransferData.getValueByName("LBPOBnf.RelationToInsured");
	  String tPolApplyDate="";
	  if(tIDNo==null||tIDNo.equals("null"))
	  {
		  tIDNo = "";
	  }
	  
	  if(tIDType==null||tIDType.equals("null"))
	  {
		  tIDType = "";
	  }

	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(!"8616".equals(tPolType)){
		  if(tPolType.equals("8611")||tPolType.equals("8621"))
		  {
			  String tRule = "|notnull&code:IDType&WITHOUT:5^6^8^?^？"; 
			  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tIDType,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
		  }
		  else
		  {
			  String tRule = "|notnull&code:IDType&WITHOUT:5^6^8^?^？#null"; 
			  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tIDType,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
		  }
		  //（5）当受益人与投、被保人证件号码一致时，
		  //系统对证件名称进行检验，如不相符给出问题件。
		  LBPOBnfDB tLBPOBnfDB =new LBPOBnfDB();
		  LBPOBnfSet tLBPOBnfSet =new LBPOBnfSet();
		  SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		  sqlbv2.sql("select * from lbpobnf where contno='"+"?contno?"+"' and inputno ='"+"?inputno?"+"'");
		  sqlbv2.put("contno", tContNo);
		  sqlbv2.put("inputno", tInputNo);
		  tLBPOBnfSet= tLBPOBnfDB.executeQuery(sqlbv2);
		  //获取投保日期
		  ExeSQL tExeSQL =new ExeSQL();
		  String tPolApplyDateSQL = "select polapplydate from lbpocont where contno='"+"?contno?"+"' and inputno ='"+"?inputno?"+"' "
		  + " order by inputno desc ";
		  SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		  sqlbv3.sql(tPolApplyDateSQL);
		  sqlbv3.put("contno", tContNo);
		  sqlbv3.put("inputno", tInputNo);
		  tPolApplyDate = tExeSQL.getOneValue(sqlbv3);
		  if(tPolApplyDate==null||tPolApplyDate.equals("")){
			  return tResult;
		  }
		  //单一投保人和被保人.
		  String tAppntIdNo = "";
		  String tAppntName = "";
		  String tAppntIDType = "";
		  String tInsuredIdNo = "";
		  String tInsuredName = "";
		  String tInsuredTDType = "";
		  SSRS tSSRS = new SSRS();
		  //获得投保人证件号
		  String tSQL_Appnt = "select (case when idno is null then '' else idno end),(case when appntname is null then '' else appntname end),(case when idtype is null then '' else idtype end) from lbpoappnt "
			  + " where contno='"+"?contno?"+"' order by inputno desc ";
		  SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		  sqlbv4.sql(tSQL_Appnt);
		  sqlbv4.put("contno", tContNo);
		  tSSRS = tExeSQL.execSQL(sqlbv4);
		  if(tSSRS.getMaxRow()>0)
		  {
			  tAppntIdNo = tSSRS.GetText(1,1);
			  tAppntName = tSSRS.GetText(1,2);
			  tAppntIDType = tSSRS.GetText(1,3);
		  }
		  if(tAppntName==null)
		  {
			  tAppntName = "";
		  }
		  if(tAppntIDType==null)
		  {
			  tAppntIDType = "";
		  }
		  
		  if(tAppntIdNo==null)
		  {
			  tAppntIdNo = "";
		  }
//		 else
//		 {
//			 tAppntIdNo = tAppntIdNo.replaceAll("[^0-9]", "");
//		 }
		  String tSQL_Insured = "select (case when idno is null then '' else idno end),(case when name is null then '' else name end),(case when idtype is null then '' else idtype end) from lbpoinsured "
			  + " where contno='"+"?contno?"+"' order by inputno desc ";
		  SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		  sqlbv5.sql(tSQL_Insured);
		  sqlbv5.put("contno", tContNo);
		  tSSRS = tExeSQL.execSQL(sqlbv5);
		  if(tSSRS.getMaxRow()>0)
		  {
			  tInsuredIdNo = tSSRS.GetText(1,1);
			  tInsuredName = tSSRS.GetText(1,2);
			  tInsuredTDType = tSSRS.GetText(1,3);
		  }
		  if(tInsuredName==null)
		  {
			  tInsuredName = "";
		  }
		  if(tInsuredTDType==null)
		  {
			  tInsuredTDType = "";
		  }
		  if(tInsuredIdNo==null)
		  {
			  tInsuredIdNo = "";
		  }
		  //对不同受益人有不同校验
		  if(tBnfType.equals("1"))
		  {
			  
			  if(tPolType.equals("8611")||tPolType.equals("8621")
					  ||tPolType.equals("8625")
					  ||tPolType.equals("8635")
					  ||tPolType.equals("8616"))
			  {
//		 else
//		 {
//			 tInsuredIdNo = tInsuredIdNo.replaceAll("[^0-9]", "");
//		 }
				  //boolean ifTrue = false;
				  //不为空才做校验
				  if(!tIDNo.equals(""))
				  {
					  if (!tAppntIdNo.equals("")&&tIDNo.equals(tAppntIdNo)
							  &&!tIDType.equals(tAppntIDType)) 
					  {
						  //ifTrue = true;
						  tResult = tResult + "受益人与投保人证件号相同,但证件类型不一致;";
					  }
					  if (!tInsuredIdNo.equals("")&&tIDNo.equals(tInsuredIdNo)
							  &&!tIDType.equals(tAppntIDType)) 
					  {
						  //ifTrue = true;
						  tResult = tResult + "受益人与被保人证件号相同,但证件类型不一致;";
					  }
				  }
				  return tResult;
				  
			  }
		  }else if(tBnfType.equals("0"))
		  {
			  //生存受益人
			  /*如录入为“空”，且受益人资料其它项有内容时，对证件号进行判断。如果是身份证号，
			   * 证件类型导入“0-身份证”，无需给出问题件；后台无法判断时，转换“8-其它”。并给出问题件；
			   * 如录入为“空”，且受益人资料其它项均为“空”，按“空”导入，无需给出问题件
			   * */
			  int tBnfSize =tLBPOBnfSet.size();
			  for(int i=1;i<=tBnfSize;i++){
				  LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
				  tLBPOBnfSchema = tLBPOBnfSet.get(i);
				  if(mDSPubCheckCol.ifRemove(tLBPOBnfSchema, "03")){
//					tLBPOBnfSet.remove(tLBPOBnfSchema);
//					tBnfSize--;
//					i--;
				  }else{
					  if(tIDType==null||"".equals(tIDType)||"null".equals(tIDType)){
						  if(tIDNo!=null&&(tIDType.length()==15||tIDNo.length()==18)){
							  //判断是身份证 无须问题件
						  }else{
							  //不是身份证 或为空
							  tResult =tResult+"受益人证件类型为空，并且证件号码不是身份证";
						  }
					  }
				  }
			  }
			  
			  if(!tIDNo.equals(""))
			  {
				  if (!tAppntIdNo.equals("")&&tIDNo.equals(tAppntIdNo)
						  &&!tIDType.equals(tAppntIDType)) 
				  {
					  //ifTrue = true;
					  tResult = tResult + "受益人与投保人证件号相同,但证件类型不一致;";
				  }
				  if (!tInsuredIdNo.equals("")&&tIDNo.equals(tInsuredIdNo)
						  &&!tIDType.equals(tAppntIDType)) 
				  {
					  //ifTrue = true;
					  tResult = tResult + "受益人与被保人证件号相同,但证件类型不一致;";
				  }
			  }
			  //华道根据受益人“证件号码”上出生日期进行判断，如年龄满18周岁，证件类型为“9-无证件”，给出问题件
			  int tAge = PubFun.calInterval(tBirthday,tPolApplyDate, "Y");
			  if(tAge>18&&tIDType!=null&&!"9".equals(tIDType))
			  {
				  tResult =tResult+ "受益人已满18岁，但是证件类型为9-无证件";
			  }
			  
		  }
	  }
	  return tResult; 
  }
  
  /**
   * 校验被保人性别和身份证号合法性
   * @return
   */
  public String checkInsuredSexAndIdNo()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOInsured.Sex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOInsured.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOInsured.IDNo");
	  
	  tResult = this.checkIDNoAndSex(tSex, tIDType, tIDNo);
  
	  return tResult; 
  }
  /**
   * 校验投保人性别和证件号
   * @return
   */
  public String checkAppntSexAndIdNo()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntSex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOAppnt.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOAppnt.IDNo");
	  String tBirthday = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntBirthday");
	  
	  tResult = this.checkIDNoAndSex(tSex, tIDType, tIDNo);
  
	  return tResult; 
  }
  
  /**
   * 校验投保人生日和证件号
   * @return
   */
  public String checkAppntBirthAndIdNo()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntSex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOAppnt.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOAppnt.IDNo");
	  String tBirthday = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntBirthday");
	  
	  tResult = this.checkIDNoAndBirth(tSex, tIDType, tIDNo, tBirthday);
  
	  return tResult; 
  }
  /**
   * 校验被保人生日和证件号
   * @return
   */
  public String checkInsuredBirthAndIdNo()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOInsured.Sex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOInsured.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOInsured.IDNo");
	  String tBirthday = (String)this.mTransferData.getValueByName("LBPOInsured.Birthday");
	  
	  tResult = this.checkIDNoAndBirth(tSex, tIDType, tIDNo, tBirthday);
  
	  return tResult; 
  }
  
  /**
   * 校验投保人年龄
   * @return
   */
  public String checkAppntAge()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntSex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOAppnt.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOAppnt.IDNo");
	  String tBirthday = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntBirthday");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOAppnt.ContNo");
	  String tPolApplyDate = "";
	  ExeSQL tExeSQL = new ExeSQL();
	  if(tBirthday==null||tBirthday.equals(""))
	  {
		  return tResult;
	  }
	  //判断录入生日是否大于录入日期（是否是未来时间）
	  String tNowDate = PubFun.getCurrentDate();
	  int tInterval =PubFun.calInterval(tBirthday, tNowDate, "D");
	  if(tInterval<0){
		  tResult ="投保人出生日期大于当前日期,请确认";
	  }
	  String tPolApplyDateSQL = "select polapplydate from lbpocont where contno='"+"?contno?"+"' "
	                          + " order by inputno desc ";
	  SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
	  sqlbv6.sql(tPolApplyDateSQL);
	  sqlbv6.put("contno", tContNo);
	  tPolApplyDate = tExeSQL.getOneValue(sqlbv6);
	  if(tPolApplyDate==null||tPolApplyDate.equals("")){
		  return tResult;
	  }
	  int tAge = PubFun.calInterval(tBirthday,tPolApplyDate, "Y");
	  if(tAge<18)
	  {
		  tResult = "投保人年龄未满18岁";
	  }
	  else 
	  {
		  if(tIDType!=null&&tIDType.equals("9"))
		  {
			  tResult = "投保人年龄满18岁但证件类型为无证件";
		  }
	  }
  
	  return tResult; 
  }
  /**
   * 校验被保人年龄
   * @return
   */
  public String checkInsuredAge()
  {
	  String tResult = "";
	  String tSex = (String)this.mTransferData.getValueByName("LBPOInsured.Sex");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOInsured.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOInsured.IDNo");
	  String tBirthday = (String)this.mTransferData.getValueByName("LBPOInsured.Birthday");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOInsured.ContNo");
	  String tPolApplyDate = "";
	  ExeSQL tExeSQL = new ExeSQL();
	  if(tBirthday==null||tBirthday.equals(""))
	  {
		  return "";
	  }
	  //判断录入生日是否大于录入日期（是否是未来时间）
	  String tNowDate = PubFun.getCurrentDate();
	  int tInterval =PubFun.calInterval(tBirthday, tNowDate, "D");
	  if(tInterval<0){
		  tResult ="被保人出生日期大于当前日期,请确认";
	  }
	  String tPolApplyDateSQL = "select polapplydate from lbpocont where contno='"+"?contno?"+"' "
	                          + " order by inputno desc ";
	  SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
	  sqlbv7.sql(tPolApplyDateSQL);
	  sqlbv7.put("contno", tContNo);
	  tPolApplyDate = tExeSQL.getOneValue(sqlbv7);
	  if(tPolApplyDate==null||tPolApplyDate.equals("")){
		  return tResult;
	  }
	  int tAge = PubFun.calInterval(tBirthday,tPolApplyDate, "Y");
	  if(tAge<18)
	  {
		  //tResult = "被保人年龄未满18岁";
	  }
	  else 
	  {
		  if(tIDType!=null&&tIDType.equals("9"))
		  {
			  tResult = "被保人年龄满18岁但证件类型为无证件";
		  }
	  }
  
	  return tResult; 
  }
  /**
   * 校验投保人关系和投保人性别
   * @return
   */
  public String checkAppntRelationAndSex()
  {
	  boolean tSame=false;
	  boolean tInsuredNull=false;
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOAppnt.ContNo");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOAppnt.InputNo");
	  String tFillNo = (String)this.mTransferData.getValueByName("LBPOAppnt.FillNo");
	  String tPolType = tContNo.substring(0,4);
	  //获得投保人Schema
	  LBPOAppntDB tLBPOAppntDB =new LBPOAppntDB();
      tLBPOAppntDB.setContNo(tContNo);
      tLBPOAppntDB.setInputNo(tInputNo);
      tLBPOAppntDB.setFillNo(tFillNo);
      if(!tLBPOAppntDB.getInfo()){
    	  CError.buildErr(this, "查询投保人表错误！");
      }
      //
	  String tAppntBirthday = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntBirthday");
	  String tRelationToInsured = (String)this.mTransferData.getValueByName("LBPOAppnt.RelationToInsured");
	  String tSex = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntSex");
	  String tAppntName = tLBPOAppntDB.getAppntName();
	  String tAppntIDNo = tLBPOAppntDB.getIDNo();
	  String tAppntBirthDay = tLBPOAppntDB.getAppntBirthday();
	  
	  if(tAppntBirthDay==null)
	  {
		  tAppntBirthDay = "";
	  }
	  SSRS tSSRS=new SSRS();
	  ExeSQL tExeSQL = new ExeSQL();
	  //获得被保人Schema
	  LBPOInsuredDB tLBPOInsuredDB=new LBPOInsuredDB();
	  LBPOInsuredSet tLBPOInsuredSet=new LBPOInsuredSet();
	  LBPOInsuredSchema tLBPOInsuredSchema=new LBPOInsuredSchema();
	  String tLBPOInsuredSQL ="select * from lbpoinsured where contno = '"+"?contno?"+"' and sequenceno='1' and inputno='"+"?inputno?"+"'";
	  SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
	  sqlbv8.sql(tLBPOInsuredSQL);
	  sqlbv8.put("contno", tContNo);
	  sqlbv8.put("inputno", tInputNo);
	  tLBPOInsuredSet=tLBPOInsuredDB.executeQuery(sqlbv8);
	  if(tLBPOInsuredSet.size()>0)
	     tLBPOInsuredSchema = tLBPOInsuredSet.get(1);
	  //
      String tInsuredBirthday = tLBPOInsuredSchema.getBirthday();
      if(tInsuredBirthday==null)
      {
    	  tInsuredBirthday = "";
      }
      String tInsuredSex = tLBPOInsuredSchema.getSex();
      String tInsuredName = tLBPOInsuredSchema.getName();
      String tInsuredIDNo = tLBPOInsuredSchema.getIDNo();
      if(!"00".equals(tRelationToInsured)){
    	  //投保人与被保人关系不是本人 且被保人资料为空 给出异常件
    	  if(mDSPubCheckCol.ifRemove(tLBPOInsuredSchema, "03")){
    		  //华拓先判断被保人资料填写是否为空缺，如被保人资料处录入全部为“空”，被保人资料按“空”导入，并给出问题件
    		  if(tPolType.equals("8616"))
    		  {
    			  tResult = tResult +"被保人信息为空;";
    		  }else{
    			  tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，不是本人，而被保人信息为空;";
    			  tInsuredNull=true;
    		  }
			}else{
				tInsuredNull=false;
				//校验 投被保人的姓名、身份证号是否一致 如果全都一致 给出异常件
				if(tAppntName==null) tAppntName="";
				if(tAppntIDNo==null) tAppntIDNo="";
				if(tAppntName.equals(tInsuredName)&&tAppntIDNo.equals(tInsuredIDNo)){
					tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，不是本人，而投、被保人的基本信息一致;";
					tSame = true;
				}else{
					tSame = false;
				}
			}
    	  //如果基本信息一致 或是 被保人信息为空 则为关系错误 将不会校验 生日关系
    	  if("03".equals(tRelationToInsured)
    			  ||"07".equals(tRelationToInsured)
    			  ||"11".equals(tRelationToInsured)
    			  ||"15".equals(tRelationToInsured)
    			  ||"19".equals(tRelationToInsured)
    			  ||"21".equals(tRelationToInsured)
    	  ){
    		  //以上关系为 投保人的年龄要大于被保险人 并且性别理论上应该是'男'
    		  if(!tSame&&!tInsuredNull){
    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
    					  &&tAppntBirthday!=null&&!"null".equals(tAppntBirthday)&&!"".equals(tAppntBirthday)){
    				  if(PubFun.checkDate(tInsuredBirthday,tAppntBirthday)==true){
    					  //被保险人生日大于投保人生日
    					  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而被保人生日大于投保人生日;";
    				  }
    			  }
    		  }
    		  if(!"0".equals(tSex)){
    			  tResult = tResult+"投保人性别同他(她)和被保人关系推出的性别不一致";
    		  }
    		  
    	  }
    	  if("04".equals(tRelationToInsured)
    			  ||"08".equals(tRelationToInsured)
    			  ||"12".equals(tRelationToInsured)
    			  ||"16".equals(tRelationToInsured)
    			  ||"20".equals(tRelationToInsured)
    			  ||"22".equals(tRelationToInsured)
    	  ){
    		  //以上关系为 投保人的年龄要大于被保险人
//    	以上关系为 投保人的年龄要大于被保险人 并且性别理论上应该是'女'
    		  if(!tSame&&!tInsuredNull){
    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
    					  &&tAppntBirthday!=null&&!"null".equals(tAppntBirthday)&&!"".equals(tAppntBirthday)){
    				  if(PubFun.checkDate(tInsuredBirthday,tAppntBirthday)==true){
    					  //被保险人生日大于投保人生日
    					  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而被保人生日大于投保人生日;";
    				  }
    			  }
    		  }
    		  if(!"1".equals(tSex)){
    			  tResult = tResult+"投保人性别同他(她)和被保人关系推出的性别不一致";
    		  }
    	  }
    	  if("05".equals(tRelationToInsured)
    			  ||"09".equals(tRelationToInsured)
    			  ||"13".equals(tRelationToInsured)
    			  ||"17".equals(tRelationToInsured)
    			  ||"24".equals(tRelationToInsured)
    	  ){
    		  //以上关系为 投保人的年龄要大于被保险人
//    	以上关系为 投保人的年龄要肖被保险人 并且性别理论上应该是'男'
    		  if(!tSame&&!tInsuredNull){
    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
    					  &&tAppntBirthday!=null&&!"null".equals(tAppntBirthday)&&!"".equals(tAppntBirthday)){
    				  if(PubFun.checkDate(tInsuredBirthday,tAppntBirthday)==false){
    					  //被保险人生日大于投保人生日
    					  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投保人生日大于被保人生日;";
    				  }
    			  }
    		  }
    		  if(!"0".equals(tSex)){
    			  tResult = tResult+"投保人性别同他(她)和被保人关系推出的性别不一致";
    		  }
    	  }
    	  if("06".equals(tRelationToInsured)
    			  ||"10".equals(tRelationToInsured)
    			  ||"14".equals(tRelationToInsured)
    			  ||"18".equals(tRelationToInsured)
    			  ||"23".equals(tRelationToInsured)
    	  ){
    		  //以上关系为 投保人的年龄要大于被保险人
//    	以上关系为 投保人的年龄要肖被保险人 并且性别理论上应该是'女'
    		  if(!tSame&&!tInsuredNull){
    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
    					  &&tAppntBirthday!=null&&!"null".equals(tAppntBirthday)&&!"".equals(tAppntBirthday)){
    				  if(PubFun.checkDate(tInsuredBirthday,tAppntBirthday)==false){
    					  //被保险人生日大于投保人生日
    					  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投保人生日大于被保人生日;";
    				  }
    			  }
    		  }
    		  if(!"1".equals(tSex)){
    			  tResult = tResult+"投保人性别同他(她)和被保人关系推出的性别不一致";
    		  }
    	  }
    	  if("01".equals(tRelationToInsured)
    			  ||"02".equals(tRelationToInsured)
    			  ||"33".equals(tRelationToInsured)
    	  ){
    		  //01 投保人是被保人的丈夫 投保人性别为男 被保人为女 02 投保人是被保人的的妻子 
    		  //投保人性别为女 被保人性别为男 33配偶 性别不能相同(如果婚姻法允许同性恋结婚再做改动)
    		  if("01".equals(tRelationToInsured)){
    			  if(!"0".equals(tSex)||!"1".equals(tInsuredSex)){
    				  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而双方中至少一方的性别有误;";
    			  }
    		  }
    		  if("02".equals(tRelationToInsured)){
    			  if(!"1".equals(tSex)||!"0".equals(tInsuredSex)){
    				  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而双方中至少一方的性别有误;";
    			  }
    		  }
    		  if("33".equals(tRelationToInsured)){
    			  if(tSex==null) tSex="";
    			  if(!tSex.equals(tInsuredSex)){
    				  tResult = tResult+"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而双方中至少一方的性别有误;";
    			  }
    		  }
    		  
    	  }
      }else{
    	  //如果是本人 则需要校验基本信息是否相同 姓名和IDNO
    	  if(mDSPubCheckCol.ifRemove(tLBPOInsuredSchema, "03")){
    		  //被保人没有信息
    		  if(tPolType.equals("8616"))
    		  {
    			  tResult = tResult +"被保人信息为空;";
    		  }
//				tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而被保人信息为空;";
			}else{
				//校验 投被保人的姓名、身份证号是否一致 如果全都一致 给出异常件
				if(tAppntName==null) tAppntName="";
				if(tAppntIDNo==null) tAppntIDNo="";
				if(!tAppntName.equals(tInsuredName)&&!tAppntIDNo.equals(tInsuredIDNo)){
					tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投、被保人的基本信息均不一致;";
				}else{
					if(!tAppntName.equals(tInsuredName)){
						tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投、被保人的姓名不一致;";
					}
                    if(!tAppntIDNo.equals(tInsuredIDNo)){
						tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投、被保人的证件号不一致;";
						
					}
				}
				if(!tInsuredBirthday.equals(tAppntBirthDay))
				{
					tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投、被保人的出生日期不一致;";

				}
				if(!tInsuredSex.equals(tSex))
				{
					tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投、被保人的性别不一致;";
					
				}
			}
      }
	  return tResult;
  }
  /**
   * 校验受益人关系
   * @return
   */
  public String checkBnfRelationAndSex()
  {
	  boolean tSame=false;
	  boolean tInsuredNull=false;
	  String tResult = "";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOBnf.ContNo");
	  String tBnfType = (String)this.mTransferData.getValueByName("LBPOBnf.BnfType");
	  String tName = (String)this.mTransferData.getValueByName("LBPOBnf.Name");
	  String tIDType = (String)this.mTransferData.getValueByName("LBPOBnf.IDType");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");
	  String tRelationToInsured = (String)this.mTransferData.getValueByName("LBPOBnf.RelationToInsured");
	  String tBnfBirthday = (String)this.mTransferData.getValueByName("LBPOBnf.Birthday");
	  String tBnfLot = (String)this.mTransferData.getValueByName("LBPOBnf.BnfLot");
	  String tBnfGrade = (String)this.mTransferData.getValueByName("LBPOBnf.BnfGrade");
	  String tSex = (String)this.mTransferData.getValueByName("LBPOBnf.Sex");
	//ln 2010-04-08 注释掉该方法中所有受益人性别的校验
	  if(tName==null)
	  {
		  tName = "";
	  }
	  if(tIDNo==null)
	  {
		  tIDNo = "";
	  }
	  if(tIDType==null)
	  {
		  tIDType = "";
	  }
	  
	  //校验受益人关系
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？^30&code:relation"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tRelationToInsured,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？^30&code:relation"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tRelationToInsured,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }  
		  String tInputNo = (String)this.mTransferData.getValueByName("LBPOAppnt.InputNo");
		  String tFillNo = (String)this.mTransferData.getValueByName("LBPOAppnt.FillNo");
		  //获得投保人Schema
		  LBPOAppntDB tLBPOAppntDB =new LBPOAppntDB();
	      tLBPOAppntDB.setContNo(tContNo);
	      tLBPOAppntDB.setInputNo(tInputNo);
	      tLBPOAppntDB.setFillNo(tFillNo);
	      if(!tLBPOAppntDB.getInfo()){
	    	  CError.buildErr(this, "查询投保人表错误！");
	      }
	      //
		  String tAppntBirthday = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntBirthday");
		  String tAppntRelationToInsured = (String)this.mTransferData.getValueByName("LBPOAppnt.RelationToInsured");
		  String tAppntSex = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntSex");
		  String tAppntName = tLBPOAppntDB.getAppntName();
		  String tAppntIDNo = tLBPOAppntDB.getIDNo();
		  String tAppntBirthDay = tLBPOAppntDB.getAppntBirthday();
		  
		  if(tAppntBirthDay==null)
		  {
			  tAppntBirthDay = "";
		  }
		  
		  if(tAppntRelationToInsured==null)
		  {
			  tAppntRelationToInsured = "";
		  }
		  SSRS tSSRS=new SSRS();
		  ExeSQL tExeSQL = new ExeSQL();
		  //获得被保人Schema
		  LBPOInsuredDB tLBPOInsuredDB=new LBPOInsuredDB();
		  LBPOInsuredSet tLBPOInsuredSet=new LBPOInsuredSet();
		  LBPOInsuredSchema tLBPOInsuredSchema=new LBPOInsuredSchema();
		  String tLBPOInsuredSQL ="select * from lbpoinsured where contno = '"+"?contno?"+"' and sequenceno='1' and inputno='"+"?inputno?"+"'";
		  SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		  sqlbv9.sql(tLBPOInsuredSQL);
		  sqlbv9.put("contno", tContNo);
		  sqlbv9.put("inputno", tInputNo);
		  tLBPOInsuredSet=tLBPOInsuredDB.executeQuery(sqlbv9);
		  if(tLBPOInsuredSet.size()>0)
		     tLBPOInsuredSchema = tLBPOInsuredSet.get(1);
		  //
	      String tInsuredBirthday = tLBPOInsuredSchema.getBirthday();
	      if(tInsuredBirthday==null)
	      {
	    	  tInsuredBirthday = "";
	      }
	      String tInsuredSex = tLBPOInsuredSchema.getSex();
	      String tInsuredName = tLBPOInsuredSchema.getName();
	      String tInsuredIDNo = tLBPOInsuredSchema.getIDNo();
	      String tInsuredIdType = tLBPOInsuredSchema.getIDType();
	      if(tInsuredSex==null)
	      {
	    	  tInsuredSex = "";
	      }
	      if(tInsuredName==null)
	      {
	    	  tInsuredName = "";
	      }
	      if(tInsuredIDNo==null)
	      {
	    	  tInsuredIDNo = "";
	      }
	      if(tInsuredIdType==null)
	      {
	    	  tInsuredIdType = "";
	      }

	      //tongmeng 2009-03-30 add
	      //校验受益人与投保人关系
	      /*
	       （3）当受益人与投保人姓名、证件号码一致时，
	       后台对受益人“系被保人的”关系同投保人“系被保人的”关系进行检验，如不相符，则给出问题件。
	       */
	  	if(tAppntName==null) tAppntName="";
		if(tAppntIDNo==null) tAppntIDNo="";
		if(tName.equals(tAppntName)&&tIDNo.equals(tAppntIDNo)){
			if(!tAppntRelationToInsured.equals(tRelationToInsured))
			{
				tResult = tResult +"与投保人姓名、证件号码一致,但受益人与被保人的关系和投保人与被保人关系不一致";

			}
		}
		if(tName.equals(tAppntName)&&!tIDNo.equals(tAppntIDNo)){
			tResult = tResult +"受益人和投保人的关系是"+tRelationToInsured+"，而受益人、投保人的姓名一致，证件号码不一致;";
		}
		if(tIDNo.equals(tAppntIDNo)&&!tName.equals(tAppntName)){
			tResult = tResult +"受益人和投保人的关系是"+tRelationToInsured+"，而受益人、投保人的证件号码一致，姓名不一致;";
		}
	      //
	      
	      
	      if(!"00".equals(tRelationToInsured)){
					tInsuredNull=false;
					//校验 投被保人的姓名、身份证号是否一致 如果全都一致 给出异常件
				
					if(tName.equals(tInsuredName)&&tIDNo.equals(tInsuredIDNo)){
						tResult = tResult +"受益人和被保人的关系是"+tRelationToInsured+"，而受益人、被保人的基本信息一致;";
						tSame = true;
					}else{
						tSame = false;
					}
					if(tName.equals(tInsuredName)&&!tIDNo.equals(tInsuredIDNo)){
						tResult = tResult +"受益人和被保人的关系是"+tRelationToInsured+"，而受益人、被保人的姓名一致，证件号码不一致;";
					}
					if(tIDNo.equals(tInsuredIDNo)&&!tName.equals(tInsuredName)){
						tResult = tResult +"受益人和被保人的关系是"+tRelationToInsured+"，而受益人、被保人的证件号码一致，姓名不一致;";
					}
					//对受益人性别校验
					if(!"00".equals(tRelationToInsured)&&!tPolType.equals("8615")&&!tPolType.equals("8625")&&!tPolType.equals("8635")){
				    	  //投保人与被保人关系不是本人 且被保人资料为空 给出异常件
//				    	  if(mDSPubCheckCol.ifRemove(tLBPOInsuredSchema, "03")){
//								tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而被保人信息为空;";
//								tInsuredNull=true;
//							}else{
//								tInsuredNull=false;
//								//校验 投被保人的姓名、身份证号是否一致 如果全都一致 给出异常件
//								if(tAppntName==null) tAppntName="";
//								if(tAppntIDNo==null) tAppntIDNo="";
//								if(tAppntName.equals(tInsuredName)&&tAppntIDNo.equals(tInsuredIDNo)){
//									tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而投、被保人的基本信息一致;";
//									tSame = true;
//								}else{
//									tSame = false;
//								}
//							}
				    	  //如果基本信息一致 或是 被保人信息为空 则为关系错误 将不会校验 生日关系
				    	  if("03".equals(tRelationToInsured)
				    			  ||"07".equals(tRelationToInsured)
				    			  ||"11".equals(tRelationToInsured)
				    			  ||"15".equals(tRelationToInsured)
				    			  ||"19".equals(tRelationToInsured)
				    			  ||"21".equals(tRelationToInsured)
				    	  ){
				    		  //以上关系为 投保人的年龄要大于被保险人 并且性别理论上应该是'男'
				    		  if(!tSame&&!tInsuredNull){
				    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
				    					  &&tBnfBirthday!=null&&!"null".equals(tBnfBirthday)&&!"".equals(tBnfBirthday)){
				    				  if(PubFun.checkDate(tInsuredBirthday,tBnfBirthday)==true){
				    					  //被保险人生日大于投保人生日
				    					  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而被保人生日大于受益人生日;";
				    				  }
				    			  }
				    		  }
//				    		  if(!"0".equals(tSex)){
//				    			  tResult = tResult+"受益人性别同他(她)和被保人关系推出的性别不一致";
//				    		  }
				    		  
				    	  }
				    	  if("04".equals(tRelationToInsured)
				    			  ||"08".equals(tRelationToInsured)
				    			  ||"12".equals(tRelationToInsured)
				    			  ||"16".equals(tRelationToInsured)
				    			  ||"20".equals(tRelationToInsured)
				    			  ||"22".equals(tRelationToInsured)
				    	  ){
				    		  //以上关系为 投保人的年龄要大于被保险人
//				    	以上关系为 投保人的年龄要大于被保险人 并且性别理论上应该是'女'
				    		  if(!tSame&&!tInsuredNull){
				    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
				    					  &&tBnfBirthday!=null&&!"null".equals(tBnfBirthday)&&!"".equals(tBnfBirthday)){
				    				  if(PubFun.checkDate(tInsuredBirthday,tBnfBirthday)==true){
				    					  //被保险人生日大于投保人生日
				    					  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而被保人生日大于受益人生日;";
				    				  }
				    			  }
				    		  }
//				    		  if(!"1".equals(tSex)){
//				    			  tResult = tResult+"受益人性别同他(她)和被保人关系推出的性别不一致";
//				    		  }
				    	  }
				    	  if("05".equals(tRelationToInsured)
				    			  ||"09".equals(tRelationToInsured)
				    			  ||"13".equals(tRelationToInsured)
				    			  ||"17".equals(tRelationToInsured)
				    			  ||"24".equals(tRelationToInsured)
				    	  ){
				    		  //以上关系为 投保人的年龄要大于被保险人
//				    	以上关系为 投保人的年龄要肖被保险人 并且性别理论上应该是'男'
				    		  if(!tSame&&!tInsuredNull){
				    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
				    					  &&tBnfBirthday!=null&&!"null".equals(tBnfBirthday)&&!"".equals(tBnfBirthday)){
				    				  if(PubFun.checkDate(tInsuredBirthday,tAppntBirthday)==false){
				    					  //被保险人生日大于投保人生日
				    					  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而被保人生日大于受益人生日;";
				    				  }
				    			  }
				    		  }
//				    		  if(!"0".equals(tSex)){
//				    			  tResult = tResult+"受益人性别同他(她)和被保人关系推出的性别不一致";
//				    		  }
				    	  }
				    	  if("06".equals(tRelationToInsured)
				    			  ||"10".equals(tRelationToInsured)
				    			  ||"14".equals(tRelationToInsured)
				    			  ||"18".equals(tRelationToInsured)
				    			  ||"23".equals(tRelationToInsured)
				    	  ){
				    		  //以上关系为 投保人的年龄要大于被保险人
//				    	以上关系为 投保人的年龄要肖被保险人 并且性别理论上应该是'女'
				    		  if(!tSame&&!tInsuredNull){
				    			  if(tInsuredBirthday!=null&&!"null".equals(tInsuredBirthday)&&!"".equals(tInsuredBirthday)
				    					  &&tBnfBirthday!=null&&!"null".equals(tBnfBirthday)&&!"".equals(tBnfBirthday)){
				    				  if(PubFun.checkDate(tInsuredBirthday,tAppntBirthday)==false){
				    					  //被保险人生日大于投保人生日
				    					  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而被保人生日大于受益人生日;";
				    				  }
				    			  }
				    		  }
//				    		  if(!"1".equals(tSex)){
//				    			  tResult = tResult+"受益人性别同他(她)和被保人关系推出的性别不一致";
//				    		  }
				    	  }
				    	  if("01".equals(tRelationToInsured)
				    			  ||"02".equals(tRelationToInsured)
				    			  ||"33".equals(tRelationToInsured)
				    	  ){
				    		  //01 投保人是被保人的丈夫 投保人性别为男 被保人为女 02 投保人是被保人的的妻子 
				    		  //投保人性别为女 被保人性别为男 33配偶 性别不能相同(如果婚姻法允许同性恋结婚再做改动)
//				    		  if("01".equals(tRelationToInsured)){
//				    			  if(!"0".equals(tSex)||!"1".equals(tInsuredSex)){
//				    				  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而双方中至少一方的性别有误;";
//				    			  }
//				    		  }
//				    		  if("02".equals(tRelationToInsured)){
//				    			  if(!"1".equals(tSex)||!"0".equals(tInsuredSex)){
//				    				  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而双方中至少一方的性别有误;";
//				    			  }
//				    		  }
//				    		  if("33".equals(tRelationToInsured)){
//				    			  if(tSex==null) tSex="";
//				    			  if(!tSex.equals(tInsuredSex)){
//				    				  tResult = tResult+"受益人和被保人的关系(受益人是被保人的)是"+tRelationToInsured+"，而双方中至少一方的性别有误;";
//				    			  }
//				    		  }
				    		  
				    	  }
				      }
	      }else{
	    	  //如果是本人 则需要校验基本信息是否相同 姓名和IDNO
	    	  if(mDSPubCheckCol.ifRemove(tLBPOInsuredSchema, "03")){
	    		  //被保人没有信息
//					tResult = tResult +"投保人和被保人的关系(投保人是被保人的)是"+tRelationToInsured+"，而被保人信息为空;";
				}else{
					//校验 投被保人的姓名、身份证号是否一致 如果全都一致 给出异常件
					if(!tName.equals(tInsuredName)){
							tResult = tResult +"受益人和被保人的关系是"+tRelationToInsured+"，而受益人、被保人的姓名不一致;";
						}
	                if(!tIDNo.equals(tInsuredIDNo)){
							tResult = tResult +"受益人和被保人的关系是"+tRelationToInsured+"，而受益人、被保人的证件号不一致;";
							
						}
	                if(!tIDType.equals(tInsuredIdType))
	                {
						tResult = tResult +"受益人和被保人的关系是"+tRelationToInsured+"，而受益人、被保人的证件类型不一致;";

	                }
	                	
				}
	  }
	  
	  
	  
      
	  return tResult;
  }
  
  
  /**
   * 校验自动垫交标志
   * @return
   */
  public String checkAutoPayFlag()
  {
	  String tResult = "";
	  String tAutoPayFlag = (String)this.mTransferData.getValueByName("LBPOCont.AutoPayFlag");
	  String tPayIntv = (String)this.mTransferData.getValueByName("LBPOCont.PayIntv");
	  
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");

	
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:autopayflag#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAutoPayFlag,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  
		  if(tAutoPayFlag==null||tAutoPayFlag.equals("")||(!tAutoPayFlag.equals("")&&tAutoPayFlag.equals("null"))){
				if(!"0".equals(tPayIntv)){
					tResult = "交费方式为非趸交并且自动垫交标志为空";
				}
			}
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？&code:autopayflag#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAutoPayFlag,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	 
	  return tResult;
  }
  /**
   * 校验保额类型
   * 按份数卖的 应为30份 非份数卖的为 30元
   * @return
   */
  public String checkAmntType()
  {
	  String tResult = "";
	  String tAutoPayFlag = (String)this.mTransferData.getValueByName("LBPOCont.AutoPayFlag");
	  String tPayIntv = (String)this.mTransferData.getValueByName("LBPOCont.PayIntv");
	  if(tAutoPayFlag==null||tAutoPayFlag.equals("")||(!tAutoPayFlag.equals("")&&tAutoPayFlag.equals("null"))){
		  if(!"0".equals(tPayIntv)){
			  tResult = "交费方式为非趸交并且自动垫交标志为空";
		  }
	  }
	  return tResult;
  }
  /**
   * 校验受益人地址
   * @return
   */
  public String checkBnfAddress()
  {
	  String tResult = "";
	  String tBnfAddress = (String)this.mTransferData.getValueByName("LBPOBnf.BnfAddress");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOBnf.ContNo");
	  String tBnfType = (String)this.mTransferData.getValueByName("LBPOBnf.BnfType");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOBnf.InputNo");
	  String tName = (String)this.mTransferData.getValueByName("LBPOBnf.Name");
	  String tIDNo = (String)this.mTransferData.getValueByName("LBPOBnf.IDNo");

	  //得到投、被保人姓名和证件号码
	  String tAppntName ="";
	  String tAppntIDNo ="";
	  String tInsuredName ="";
	  String tInsuredIDNo ="";
	  LBPOInsuredDB tLBPOInsuredDB =new LBPOInsuredDB();
	  LBPOInsuredSet tLBPOInsuredSet =new LBPOInsuredSet();
	  LBPOInsuredSchema tLBPOInsuredSchema =new LBPOInsuredSchema();
	  SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
	  sqlbv10.sql("select * from lbpoinsured where prtno ='"+"?prtno?"+"' and inputno ='"+"?inputno?"+"'");
	  sqlbv10.put("prtno", tContNo);
	  sqlbv10.put("inputno", tInputNo);
	  tLBPOInsuredSet=tLBPOInsuredDB.executeQuery(sqlbv10);
	  LBPOAppntDB tLBPOAppntDB =new LBPOAppntDB();
	  LBPOAppntSet tLBPOAppntSet =new LBPOAppntSet();
	  LBPOAppntSchema tLBPOAppntSchema =new LBPOAppntSchema();
	  SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
	  sqlbv11.sql("select * from lbpoappnt where prtno ='"+"?prtno?"+"' and inputno ='"+"?inputno?"+"'");
	  sqlbv11.put("prtno", tContNo);
	  sqlbv11.put("inputno", tInputNo);
	  tLBPOAppntSet=tLBPOAppntDB.executeQuery(sqlbv11);
	  if(tLBPOAppntSet.size()>0){
		  tLBPOAppntSchema =tLBPOAppntSet.get(1);
		  tAppntName =tLBPOAppntSchema.getAppntName();
		  tAppntIDNo =tLBPOAppntSchema.getIDNo();
	  }
		  
	  LBPOBnfDB tLBPOBnfDB =new LBPOBnfDB();
	  LBPOBnfSet tLBPOBnfSet =new LBPOBnfSet();
	  LBPOBnfSchema tLBPOBnfSchema =new LBPOBnfSchema();
	  SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
	  sqlbv12.sql("select * from lbpobnf where contno ='"+"?prtno?"+"' and inputno ='"+"?inputno?"+"'");
	  sqlbv12.put("prtno", tContNo);
	  sqlbv12.put("inputno", tInputNo);
	  tLBPOBnfSet =tLBPOBnfDB.executeQuery(sqlbv12);
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tBnfAddress,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  if(tBnfAddress!=null&&tBnfAddress.trim().length()>=1)
		  {
			  if(tBnfAddress.substring(0,1).equals("3")&&tBnfAddress.length()==1)
			  {
				  tResult = "受益人地址为3,没有写明具体地址";
			  }
		  }
		  /*
		   * 如录入为“空”，且受益人资料其它项有内容时，后台对生存受益人与投、被保险人（姓名、证件号码）进行校验。
		   * 如判断为投保人，则按序号“1”直接导入；如判断为被保人，则按序号“2”导入，无需给出问题件；
		   * 如判断为非投保人或非被保人时，按“空”导入，并给出问题件。如录入为“空”，且受益人资料其它项均为“空”，按“空”导入，
		   * 无需给出问题件
		   * */
		  boolean SameToAppnt =false;
		  boolean SameToInsured =false;
		  int tBnfSize = tLBPOBnfSet.size();
			for(int a=1;a<=tBnfSize;a++){
				tLBPOBnfSchema = tLBPOBnfSet.get(a);
				if(mDSPubCheckCol.ifRemove(tLBPOBnfSchema, "03")){
//					mLBPOPolSet.remove(rLBPOPolSchema);
//					mLBPOPolSetSize--;
//					a--;
				}else{
					if(tBnfAddress==null||"".equals(tBnfAddress)){
						//判断是否同投保人姓名证件相同
						if(tName!=null&&tName.equals(tAppntName)&&tIDNo!=null&&tIDNo.equals(tAppntIDNo)){
							SameToAppnt =true;
						}
						for(int i=1;i<=tLBPOInsuredSet.size();i++){
							tLBPOInsuredSchema =tLBPOInsuredSet.get(i);
							tInsuredName =tLBPOInsuredSchema.getName();
							tInsuredIDNo =tLBPOInsuredSchema.getIDNo();
							if(tName!=null&&tName.equals(tInsuredName)&&tIDNo!=null&&tIDNo.equals(tInsuredName)){
								SameToInsured =true;
							}
						}
						if(!SameToInsured&&!SameToAppnt){
							//既不是投保人、也不是被保人
							tResult =tResult+"受益人地址录入为空，且受益人姓名、证件号码与投、被保人均不一致";
						}
					}
				}
			}
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tBnfAddress,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  
	  return tResult;
  }
  /**
   * 校验续期缴费方式
   * @return
   */
  public String checkRePayMode()
  {
	  String tResult = "";
	  String tPayLocation = (String)this.mTransferData.getValueByName("LBPOCont.PayLocation");
	  String tPayMode = (String)this.mTransferData.getValueByName("LBPOCont.PayMode");
	  String tPayIntv = (String)this.mTransferData.getValueByName("LBPOCont.PayIntv");
	  String tBankCode = (String)this.mTransferData.getValueByName("LBPOCont.BankCode");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");

	  String tPolType = tContNo.substring(0,4);
	  String tCheckRes = "";
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  if("0".equals(tPayLocation)||"0".equals(tPayMode)){
			  //首续期至少有一个银行转账
			  if(tBankCode==null||"".equals(tBankCode)||"null".equals(tBankCode)){
				  //而银行代码为空 则给出异常件
				  tResult = "首、续期交费方式有银行转账,而银行代码为空";
			  }
		  }else{
			  //没有一个为银行转账
			  if(tBankCode!=null&&!"".equals(tBankCode)&&!"null".equals(tBankCode)){
				  tResult = "首、续期交费方式都不是银行转账,而银行代码不为空";
			  }
		  }
		  
		  if(!tResult.equals(""))
		  {
			  return tResult;
		  }
		  
			try {
				tCheckRes = tDSPubCheckCol.checkValueByRule(tPayLocation,
						"续期交费|notnull&WITHOUT:?^？&Code:paylocation", mTransferData, "1");
			} catch (Exception ex) {
				return tResult;
			}
			tResult = tResult + tCheckRes;
			
			/*录入为“空”时，与“交费方式”进行校验，如“交费方式”为“0-趸交”,转换为代码“2-人工收取”导入系统，
			 *无需给出问题件；如“交费方式”为非趸交，转换为代码“2-人工收取”导入系统，给出问题件*/
			if(tPayLocation==null||"".equals(tPayLocation)||"null".equals(tPayLocation)){
				if(!"0".equals(tPayIntv)){
					tResult=tResult+"续期交费方式为空，并且交费方式非趸交";
				}
			}
		} else {
			tCheckRes = tDSPubCheckCol.checkValueByRule(tPayLocation,
					"续期交费|notnull&WITHOUT:?^？&Code:paylocation#null", mTransferData, "1");
			tResult = tResult + tCheckRes;
		}
	  
	  if(tPayIntv!=null&&tPayIntv.equals("0"))
	  {
		  return tResult;
	  }
	 
		  
	  return tResult;
  }
  /**
   * 校验首期缴费方式
   * @return
   */
  public String checkRePayMode1()
  {
	  String tResult = "";
	  String tPayLocation = (String)this.mTransferData.getValueByName("LBPOCont.PayLocation");
	  String tPayMode = (String)this.mTransferData.getValueByName("LBPOCont.PayMode");
	  String tPayIntv = (String)this.mTransferData.getValueByName("LBPOCont.PayIntv");
	  String tBankCode = (String)this.mTransferData.getValueByName("LBPOCont.BankCode");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  
	  String tPolType = tContNo.substring(0,4);
	  String tCheckRes = "";
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  if("0".equals(tPayLocation)||"0".equals(tPayMode)){
			  //首续期至少有一个银行转账
			  if(tBankCode==null||"".equals(tBankCode)||"null".equals(tBankCode)){
				  //而银行代码为空 则给出异常件
				  tResult = "首、续期交费方式有银行转账,而银行代码为空";
			  }
		  }else{
			  //没有一个为银行转账
			  if(tBankCode!=null&&!"".equals(tBankCode)&&!"null".equals(tBankCode)){
				  tResult = "首、续期交费方式都不是银行转账,而银行代码不为空";
			  }
		  }
		  
		  if(!tResult.equals(""))
		  {
			  return tResult;
		  }
		  
		  try {
			  tCheckRes = tDSPubCheckCol.checkValueByRule(tPayMode,
					  "续期交费|notnull&WITHOUT:?^？&Code:paylocation", mTransferData, "1");
		  } catch (Exception ex) {
			  return tResult;
		  }
		  tResult = tResult + tCheckRes;
		  
		  /*录入为“空”时，与“交费方式”进行校验，如“交费方式”为“0-趸交”,转换为代码“2-人工收取”导入系统，
		   *无需给出问题件；如“交费方式”为非趸交，转换为代码“2-人工收取”导入系统，给出问题件*/
		  if(tPayLocation==null||"".equals(tPayLocation)||"null".equals(tPayLocation)){
			  if(!"0".equals(tPayIntv)){
				  tResult=tResult+"续期交费方式为空，并且交费方式非趸交";
			  }
		  }
	  } else {
		  tCheckRes = tDSPubCheckCol.checkValueByRule(tPayMode,
				  "续期交费|notnull&WITHOUT:?^？&Code:paylocation#null", mTransferData, "1");
		  tResult = tResult + tCheckRes;
	  }
	  
	  if(tPayIntv!=null&&tPayIntv.equals("0"))
	  {
		  return tResult;
	  }
	  
	  
	  return tResult;
  }
  //tongmeng 2009-02-09 add
  //按照华道的校验规则校验
  /**
   * 校验投保人签名是否和投保人姓名一致
   * @return
   */
  public String checkAppntSignName()
  {
	  String tResult = "";
	  //投保人签名
	  String tAppSignName = (String)this.mTransferData.getValueByName("LBPOCont.AppSignName");
	  String tInsSignName2 = (String)this.mTransferData.getValueByName("LBPOCont.InsSignName2");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOCont.InputNo");
	  String tAppntName = "";
	  ExeSQL tExeSQL = new ExeSQL();
	  //如果投被保人的关系为本人-00时，判断签名是否都与appntname不同，如果时则提示错误
	  String tSql =" select a.relationtoinsured from lbpoappnt a where a.contno='"+"?contno?"+"' and inputno='"+"?inputno?"+"'";
	  SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
	  sqlbv13.sql(tSql);
	  sqlbv13.put("contno", tContNo);
	  sqlbv13.put("inputno", tInputNo);
	  String tRelationToInsured = tExeSQL.getOneValue(sqlbv13);
	  if(tAppSignName.equals("手印"))
	  {
		return "";  
	  }
	  String tSQL = "select AppntName from lbpoappnt where contno='"+"?contno?"+"' "
      + " order by inputno desc ";
	  SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
	  sqlbv14.sql(tSQL);
	  sqlbv14.put("contno", tContNo);
	  tAppntName = tExeSQL.getOneValue(sqlbv14);
	  if(tAppntName==null)
	  {
		  tAppntName = "";
	  }
	  if(tAppSignName==null)
	  {
		  tAppSignName = "";
	  }
	  if(tInsSignName2==null)
	  {
		  tInsSignName2 = "";
	  }
	  if("00".equals(tRelationToInsured)){
		  //本人时如果投保人签名或被保人签名中的一个与投保人姓名相同 就为正确
		  if(!tAppSignName.equals(tAppntName)
				  //||!tInsSignName2.equals(tAppntName)
				  ){
			  tResult = "投被保人为本人,但是投保人签名与投保人姓名不一致";
		  }
	  }else{
		  if(!tAppSignName.equals(tAppntName))
		  {
			  tResult = "投保人姓名和投保人签名不一致";
		  }
	  }

	  return tResult;
  }

  /**
   * 校验被保人签名和被保人姓名是否一致
   * @return
   */
  /**
   * 校验被保人签名和被保人姓名是否一致
   * @return
   */
  public String checkInsuredSignName()
  {
	  String tResult = "";
	  String tInsSignName = (String)this.mTransferData.getValueByName("LBPOCont.InsSignName2");
	  String tAppSignName = (String)this.mTransferData.getValueByName("LBPOCont.AppSignName");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOCont.InputNo");
	  String tPolApplyDate = (String)this.mTransferData.getValueByName("LBPOCont.PolApplyDate");
	  String tBirthday = "";//(String)this.mTransferData.getValueByName("LBPOInsured.Birthday");	  //如果投保单申请时间为空,无法计算被保人年龄.不继续执行
	  if(tPolApplyDate==null||"".equals(tPolApplyDate)||"null".equals(tPolApplyDate)){
		  return "";
	  }
	  if(tInsSignName.equals("手印"))
	  {
		return "";  
	  }
	  
	  ExeSQL tExeSQL = new ExeSQL();
	  String tSQL = "select Birthday,name from LBPOInsured where contno='"+"?contno?"+"' "
      			  + " order by inputno desc ";
	  SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
	  sqlbv15.sql(tSQL);
	  sqlbv15.put("contno", tContNo);
	  SSRS tSSRS = new SSRS();
	  tSSRS = tExeSQL.execSQL(sqlbv15);
//	如果投被保人的关系为本人-00时，判断签名是否都与appntname不同，如果时则提示错误
	  String tSql =" select a.relationtoinsured from lbpoappnt a where a.contno='"+"?contno?"+"' and inputno='"+"?inputno?"+"'";
	  SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
	  sqlbv16.sql(tSql);
	  sqlbv16.put("contno", tContNo);
	  sqlbv16.put("inputno", tInputNo);
	  String tRelationToInsured = tExeSQL.getOneValue(sqlbv16);
	  if(tSSRS.getMaxRow()<=0)
	  {
		  return "";
	  }
	  
	  String tInsuredName = "";
	  tInsuredName = tSSRS.GetText(1,2);
	  tBirthday = tSSRS.GetText(1,1);
	  int tAge = PubFun.calInterval(tBirthday,tPolApplyDate, "Y");
	  if(tInsSignName==null)
	  {
		  tInsSignName = "";
	  }
	  if(tInsuredName == null)
	  {
		  tInsuredName = "";
	  }
	  if(tAppSignName == null)
	  {
		  tAppSignName = "";
	  }
	  
	  if(tAge>=18)
	  {
		  
		  if("00".equals(tRelationToInsured)){
//			本人时如果投保人签名或被保人签名中的一个与投保人姓名相同 就为正确
			  if(//!tAppSignName.equals(tInsuredName)||
					  !tInsSignName.equals(tInsuredName))
			  {
				  tResult = "投被保人为本人,但是被保人签名与被保人姓名不一致";
			  }
		  }else{
			  if("".equals(tInsSignName)){
				  tResult = "投被保人不是本人,但是被保人签名为空";
			  }else{
				  if(!tInsSignName.equals(tInsuredName))
				  {
					  tResult = "被保人签名与被保人姓名不符";
				  }
			  }
		  }
	  }
	  else
	  {
		  //未成年,不校验被保人签名与被保人姓名
		  tResult = "";
	  }
//	  if(tPolApplyDate==null||tPolApplyDate.equals(""))
//	  {
//		  return "";
//	  }
	  return tResult;
  }
  /**
   * 银行编码校验
   */
  public String checkBankCode(){
	  String tResult = "";
	  String tBankCode = this.mTransferData.getValueByName("LBPOCont.BankCode")==null?"":(String)this.mTransferData.getValueByName("LBPOCont.BankCode");
	  String tAccName = this.mTransferData.getValueByName("LBPOCont.AccName")==null?"":(String)this.mTransferData.getValueByName("LBPOCont.AccName");
	  String tBankAccNo = this.mTransferData.getValueByName("LBPOCont.BankAccNo")==null?"":(String)this.mTransferData.getValueByName("LBPOCont.BankAccNo");
	  int tCount = 0;
	  if(tBankCode.equals("null"))
	  {
		  tBankCode = "";
		  tCount = tCount +1;
	  }
	  if(tAccName.equals("null"))
	  {
		  tAccName = "";
		  tCount = tCount +1;
	  }
	  if(tBankAccNo.equals("null"))
	  {
		  tBankAccNo = "";
		  tCount = tCount +1;
	  }
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
		  String tPolType = tContNo.substring(0,4);
		  String tAppntName = "";
		  String tSQL_Appnt = "select (case when appntname is null then '' else appntname end) from lbpoappnt where contno='"+"?contno?"+"' order by inputno desc";
		  SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		  sqlbv17.sql(tSQL_Appnt);
		  sqlbv17.put("contno", tContNo);
		  ExeSQL tExeSQL=new ExeSQL();
		  tAppntName = tExeSQL.getOneValue(sqlbv17);
		  if(tAppntName==null)
		  {
			  tAppntName = "";
		  }
		  // String tErrorInfo = ""; 
		 DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
		  if(tPolType.equals("8611")||tPolType.equals("8621"))
		  {
			  if(tBankCode==null||tBankCode.equals("")||tBankCode.equals("null"))
			  {
				  return "";
			  }
			
			  String tBankSQL = "select count(1) from ldbank where bankcode ='"+"?bankcode?"+"'";
			  SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			  sqlbv18.sql(tBankSQL);
			  sqlbv18.put("bankcode", tBankCode);
			  String tExists = tExeSQL.getOneValue(sqlbv18);
			  if(Integer.parseInt(tExists)==0){
				  //说明没有查询到这个银行编码
				  tResult="银行编码错误";
			  }
		  }
		  else
		  {
			  String tRule = "|WITHOUT:?^？"; 
			  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tBankCode,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
			  
			  //
			  tErrorInfo = tDSPubCheckCol.checkValueByRule(tAccName,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
			  //
			  tErrorInfo = tDSPubCheckCol.checkValueByRule(tBankAccNo,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
			  if(tCount>0 && tCount!=3)
			  {
				  tResult = "开户户名、开户行、交费账号三项有任意一或两项为空";
				  return tResult;
			  }
			  if(tBankCode==null||tBankCode.equals("")||tBankCode.equals("null"))
			  {
				  return "";
			  }
			  //ExeSQL tExeSQL=new ExeSQL();
			  String tBankSQL = "select count(1) from ldbank where bankcode ='"+"?bankcode?"+"'";
			  SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			  sqlbv19.sql(tBankSQL);
			  sqlbv19.put("bankcode", tBankCode);
			  String tExists = tExeSQL.getOneValue(sqlbv19);
			  if(Integer.parseInt(tExists)==0){
				  //说明没有查询到这个银行编码
				  tResult="银行编码错误;";
			  }
			  
			  if(!tAccName.equals(tAppntName))
			  {
				  tResult=tResult + "户名与投保人姓名不符;";
			  }

		  }


	  return tResult;
  }
  
  /**
   * 校验投保单申请时间和录入时间的关系
   * @return
   */
  public String checkPolApplyDate()
  {
	  String tResult = "";
	  String tPolApplyDate = (String)this.mTransferData.getValueByName("LBPOCont.PolApplyDate");
	  String tMakeDate = (String)this.mTransferData.getValueByName("LBPOCont.MakeDate");
	  String tDayIntv = "";
	  String tSQL ="";
	  SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
	  if(tPolApplyDate!=null&&!"null".equals(tPolApplyDate)&&!"".equals(tPolApplyDate)){
		  tSQL = "select datediff(to_date('"+"?date1?"+"','yyyy-mm-dd'),to_date('"+"?date2?"+"','yyyy-mm-dd')) from dual ";
		  sqlbv20.put("date1", tMakeDate);
		  sqlbv20.put("date2", tPolApplyDate);
	  }
      else{
          return tResult;
      }
	  sqlbv20.sql(tSQL);
	  ExeSQL tExeSQL = new ExeSQL();
	  SSRS tSSRS=new SSRS();
	  tSSRS =tExeSQL.execSQL(sqlbv20);
	  if(tSSRS.getMaxRow()>0){
		  tDayIntv = tSSRS.GetText(1, 1);
		  if(tDayIntv==null||tDayIntv.equals(""))
		  {
			  return "";
		  }
		  else
		  {
			  int tIntv = 0;
			  tIntv = Integer.parseInt(tDayIntv);
			  if(tIntv>=30)
			  {
				  tResult = "录入日期在投保申请日期30天之后";
			  }
			  else if(tIntv<0)
			  {
				  tResult = "投保申请日期在录入日期之后";
			  }
		  }
	  }
	  return tResult;
  }
  /**
   * 校验业务员姓名和工号
   * @return
   */
  public String checkAgentCodeAndName()
  {
 	 String tResult = "";
 	 
 	 String tAgentCode = (String)this.mTransferData.getValueByName("LBPOCont.AgentCode");
 	 String tInputAgentName = (String)this.mTransferData.getValueByName("LBPOCont.Handler");
 	 String tAgentName = "";
 	 if(tAgentCode==null||tAgentCode.equals(""))
 	 {
 		 return "";
 	 }
 	 if(tInputAgentName==null)
 	 {
 		tInputAgentName = "";
 	 }
 	 String tSQL = "select name from laagent where agentcode='"+"?agentcode?"+"' ";
 	 SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
 	 sqlbv21.sql(tSQL);
 	 sqlbv21.put("agentcode", tAgentCode);
 	 ExeSQL tExeSQL = new ExeSQL();
 	 tAgentName = tExeSQL.getOneValue(sqlbv21);
 	 if(tAgentName==null)
 	 {
 		tAgentName = "";
 	 }
 	 if(!tInputAgentName.equals(tAgentName))
 	 {
 		tResult = "录入业务员姓名与业务员不符";
 	 }
 	 return tResult;
  }
  /**
   * 校验业务员工号和销售渠道
   * @return
   */
  public String checkAgentCodeAndSaleChnl()
  {
 	 String tResult = "";
 	 
 	 String tAgentCode = (String)this.mTransferData.getValueByName("LBPOCont.AgentCode");
 	 String tSaleChnl = (String)this.mTransferData.getValueByName("LBPOCont.SaleChnl");
 	 //String tAgentName = "";
 	 if(tSaleChnl==null)
 	 {
 		tSaleChnl = "";
 	 }
 	 if(tAgentCode==null||tAgentCode.equals(""))
 	 {
 		 return null;
 	 }
 	String tSplitCode = tAgentCode.substring(4);
 	if(tSplitCode.equals("999999"))
 	{
 		//
 		if(!tSaleChnl.equals("11"))
 		{
 			tResult = "内部员工,但保单销售渠道不是‘其他’";
 		}
 		else
 		{
 			return "";
 		}
 		
 	}
 	 
 	//校验代理人展业类型和保单销售渠道是否匹配
 	ExeSQL tExeSQL = new ExeSQL();
 	String tSQL = "select (case count(1) when 0 then 0 else 1 end) from ldcode1 where codetype='salechnlagentctrl' "
 		         + " and trim(code)='"+"?code?"+"' and trim(code1)=(select branchtype from laagent where agentcode='"+"?agentcode?"+"')";
 	SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
 	sqlbv22.sql(tSQL);
 	sqlbv22.put("code", tSaleChnl);
 	sqlbv22.put("agentcode", tAgentCode);
 	String tValue = "";
 	tValue = tExeSQL.getOneValue(sqlbv22);
 	if(tValue!=null&&Integer.parseInt(tValue)==0)
 	{
 		tResult = "业务员展业渠道和保单销售渠道不一致";
 	}
 	 return tResult;
  }
  /**
   * 校验中介投保单中介机构编码规则
   * @return
   */
  public String checkAgentCom()
  {
	  String tResult = "";
	  String tAgentCom = (String)this.mTransferData.getValueByName("LBPOCont.AgentCom");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ContNo");
	  String tAgentComName = (String)this.mTransferData.getValueByName("LBPOCont.AgentComName");
	  String tSaleChnl = (String)this.mTransferData.getValueByName("LBPOCont.SaleChnl");
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611"))
	  {
		  String tRule = "|NOTNULL&Code:AgentCom&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentCom,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else  if(tPolType.equals("8616"))
	  {
		  String tRule = "|NOTNULL&Code:AgentCom&WITHOUT:?^？#null"; 
//		  if(tSaleChnl!=null&&!tSaleChnl.equals("")&&!tSaleChnl.equals("02"))
//		  {
//			  tRule = "|NOTNULL&Code:AgentCom&WITHOUT:?^？"; 
//		  }
		  
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentCom,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else 
	  {
		  String tRule = "|NOTNULL&Code:AgentCom&WITHOUT:?^？#"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentCom,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  tRule = "|NOTNULL&WITHOUT:?^？#"; 
		  tErrorInfo = tDSPubCheckCol.checkValueByRule(tAgentComName,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  //校验代理机构和代理机构编码内容是否一致/
		  //tongmeng 20090327 modify
		  //不做校验了..
		  /*
		  String tSQL = " select decode(count(*),0,0,1) from lacom "
			          + " where agentcom='"+tAgentCom+"' and name='"+tAgentComName+"'";
		  ExeSQL tExeSQL = new ExeSQL();
		  String tValue = "";
		  tValue = tExeSQL.getOneValue(tSQL);
		  if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)<=0)
		  {
			  tErrorInfo = "代理机构编码和代理机构名称不一致";
			  return tErrorInfo;
		  }
		  */
	  }
	  return tResult;
  }
  
  /**
   * 校验投保人国籍
   * @return
   */
  public String checkAppntNativePlace()
  {
	  String tResult = "";
	  String tNativePlace = (String)this.mTransferData.getValueByName("LBPOAppnt.NativePlace");
	  String tAppntName = (String)this.mTransferData.getValueByName("LBPOAppnt.AppntName");
	  if(tAppntName==null||tAppntName.equals(""))
	  {
		  return "";
	  }
	  if(tNativePlace==null||tNativePlace.equals("")||tNativePlace.equals("null"))
	  {
		  //如果投保人国籍为空,校验投保人姓名是否包含英文
		  String tSQL = "select  translate('"+"?appntname?"+"', "
			          + " 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ',"
			          + " '****************************************************') "
                      + " from dual ";
		  SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		  sqlbv23.sql(tSQL);
		  sqlbv23.put("appntname", tAppntName);
		  ExeSQL tExeSQL = new ExeSQL();
		  String tValue = tExeSQL.getOneValue(sqlbv23);
		  if(tValue!=null&&tValue.indexOf("*")!=-1)
		  {
			  tResult = "投保人国籍为空,但投保人姓名包含英文";
		  }
		  
	  }
	  return tResult;
  }
  /**
   * 校验被保人国籍
   * @return
   */
  public String checkInsuredNativePlace()
  {
	  String tResult = "";
	  String tNativePlace = (String)this.mTransferData.getValueByName("LBPOInsured.NativePlace");
	  String tInsuredName = (String)this.mTransferData.getValueByName("LBPOInsured.Name");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOInsured.ContNo");
	  String tPolType = tContNo.substring(0,4);
	  if(tInsuredName==null||tInsuredName.equals(""))
	  {
		  return "";
	  }
	  //简易被保人没有国籍
	  if(!tPolType.equals("8616"))
	  {
		  if(tNativePlace==null||tNativePlace.equals("")||tNativePlace.equals("null"))
		  {
			  //如果投保人国籍为空,校验投保人姓名是否包含英文
			  String tSQL = "select  translate('"+"?insuredname?"+"', "
			  + " 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ',"
			  + " '****************************************************') "
			  + " from dual ";
			  SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			  sqlbv24.sql(tSQL);
			  sqlbv24.put("insuredname", tInsuredName);
			  ExeSQL tExeSQL = new ExeSQL();
			  String tValue = tExeSQL.getOneValue(sqlbv24);
			  if(tValue!=null&&tValue.indexOf("*")!=-1)
			  {
				  tResult = "被保人国籍为空,但被保人姓名包含英文";
			  }
			  
		  }
	  }
	  
	  return tResult;
  }
  /**
   * 校验保险期间
   * @return
   */
  public String checkInsuYearInfo(){
	  String tRes="";
	  String tInsuYear = this.mTransferData.getValueByName("LBPOPol.InsuYear")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.InsuYear");
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOPol.ContNo");
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  //简易没有保险期间
	  if(!tPolType.equals("8616"))
	  {
			  String tRule = "|notnull&&WITHOUT:?^？"; 
			  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tInsuYear,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
	  }
	  //校验是否包含特定字 并且格式是否合法
	  if(tInsuYear==null)
	  {
		  tInsuYear = "";
	  }
//	  if(tInsuYear!=null&&!"".equals(tInsuYear)){
		  if(tInsuYear.indexOf("年")!=-1
			||(tInsuYear.indexOf("保")!=-1&&tInsuYear.indexOf("年")!=-1)	  
		  ){
			  String tReplace1 = StrTool.replace(tInsuYear, "年", "");
			  String tReplace2 = StrTool.replace(tReplace1, "保", "");
			  if(!tReplace2.equals(tInsuYear.replaceAll("[^0-9]", ""))){
				  tRes="保险期间录入不是数字+年";
			  }
		  }else if((tInsuYear.indexOf("至")!=-1&&tInsuYear.indexOf("岁")!=-1)
			||(tInsuYear.indexOf("保至")!=-1&&tInsuYear.indexOf("周岁")!=-1)
			||(tInsuYear.indexOf("保至")!=-1&&tInsuYear.indexOf("岁")!=-1)
		  ){
			  String tReplace1 = StrTool.replace(tInsuYear, "至", "");
			  String tReplace2 = StrTool.replace(tReplace1, "岁", "");
			  String tReplace3 = StrTool.replace(tReplace2, "保", "");
			  String tReplace4 = StrTool.replace(tReplace3, "周", "");
			  if(!tReplace4.equals(tInsuYear.replaceAll("[^0-9]", ""))){
				  tRes="保险期间录入不是至+数字+岁";
			  }
		  }else {
			  if(!tInsuYear.equals("终身")&&!tInsuYear.equals("终生")){
				  tRes="保险期间无“年”、“至 岁”或不为“终身(终生)”";
			  }
		  }
//	  }
		 if(!tRes.equals(""))
		 {
			 return tRes;
		 }
	  //tongmeng 2009-02-23 add
	  //险种校验规则
   	 //tongmeng 2009-03-24 modify
	 //双岗不做险种规则校验
	 /*
	  if(tRiskCode==null)
	  {
		  tRiskCode = "";
	  }
	  if(tRiskCode.equals("112203")
			  ||tRiskCode.equals("112101")
			  ||tRiskCode.equals("112202")	
			  ||tRiskCode.equals("111504")	  
	  )	
	  {
		  if(!tInsuYear.equals("终身")&&!tInsuYear.equals("终生")){
			  tRes="保险期间不为终身(终生)";
		  }
	  }
	  else if(tRiskCode.equals("112201"))
	  {
		  if(!tInsuYear.equals("25年")||!tInsuYear.equals("二十五年"))
		  {
			  tRes="保险期间录入错误";
		  }
	  }
	  else if(tRiskCode.equals("111301"))
	  {
		  //（1）录入不为“至70周岁、保至70岁、至70岁、保至70周岁”、“至七十周岁、保至七十岁、至七十岁、保至七十周岁”时
		  
		  if(!tInsuYear.equals("至70周岁")
				  &&!tInsuYear.equals("保至70岁")
				   &&!tInsuYear.equals("至70岁")
				    &&!tInsuYear.equals("保至70周岁")
				     &&!tInsuYear.equals("至七十周岁")
				      &&!tInsuYear.equals("保至七十岁")
				       &&!tInsuYear.equals("至七十岁")
				       &&!tInsuYear.equals("保至七十周岁")
				       
				  )
		  {
			  tRes="保险期间录入错误";
		  }
	  }
	  else if(tRiskCode.equals("112401"))
	  {
		  //长瑞需要校验计划
		  String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
	      			  + " order by inputno desc ";
		  ExeSQL tExeSQL = new ExeSQL();
		  String tRemark = "";
		  tRemark = tExeSQL.getOneValue(tSQL);
		  if(tRemark==null)
		  {
			  tRemark = "";
		  }
		  if(tRemark.indexOf("A")!=-1)
		  {
			  if(tInsuYear.indexOf("88")==-1
					  ||tInsuYear.indexOf("八十八")==-1
					  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
		  else if(tRemark.indexOf("B")!=-1)
		  {
			  //70（七十）、75（七十五）、80（八十）、85（八十五）
			  if(tInsuYear.indexOf("70")==-1
					  ||tInsuYear.indexOf("七十")==-1
					  ||tInsuYear.indexOf("75")==-1
					  ||tInsuYear.indexOf("七十五")==-1
					  ||tInsuYear.indexOf("80")==-1
					  ||tInsuYear.indexOf("八十")==-1
					  ||tInsuYear.indexOf("85")==-1
					  ||tInsuYear.indexOf("八十五")==-1
					  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
	  }
	  else if(tRiskCode.equals("112206"))
	  {
		  //至25周岁、保至25岁、至25岁、保至25周岁”、“至二十五周岁、保至二十五岁、至二十五岁、保至二十五周岁
		  
		  if(!tInsuYear.equals("至25周岁")
				  &&!tInsuYear.equals("保至25岁")
				   &&!tInsuYear.equals("至25岁")
				    &&!tInsuYear.equals("保至25周岁")
				     &&!tInsuYear.equals("至二十五周岁")
				      &&!tInsuYear.equals("保至二十五岁")
				       &&!tInsuYear.equals("至二十五岁")
				       &&!tInsuYear.equals("保至二十五周岁")
				       
				  )
		  {
			  tRes="保险期间录入错误";
		  }
	  }
	  else if(tRiskCode.equals("112207"))
	  {
		  if(tInsuYear.indexOf("至")==-1&&tInsuYear.indexOf("岁")==-1)
		  {
			  tRes="保险期间录入错误";
		  }
		  String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
		  			  + " order by inputno desc ";
		  ExeSQL tExeSQL = new ExeSQL();
		  String tRemark = "";
		  tRemark = tExeSQL.getOneValue(tSQL);
		  if(tRemark==null)
		  {	
			  tRemark = "";
		  }
		  if(tRemark.indexOf("A")!=-1)
		  {
			  if(tInsuYear.indexOf("70")==-1
					  ||tInsuYear.indexOf("七十")==-1
		  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
		  else if(tRemark.indexOf("B")!=-1)
		  {
			  //
			  if(tInsuYear.indexOf("60")==-1
					  ||tInsuYear.indexOf("六十")==-1
					  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
	  }
	  else if(tRiskCode.equals("112208"))
	  {
		  if((tInsuYear.indexOf("至")==-1&&tInsuYear.indexOf("岁")==-1)
				  ||tInsuYear.indexOf("年")==-1
		  	)
		  {
			  tRes="保险期间录入错误";
		  }
		  //30-30年、40-40年、50-50年、88-至88周岁
		  if(!tInsuYear.equals("30")
				  &&!tInsuYear.equals("30年")
				   &&!tInsuYear.equals("40")
				    &&!tInsuYear.equals("40年")
				     &&!tInsuYear.equals("50")
				      &&!tInsuYear.equals("50年")
				       &&!tInsuYear.equals("88")
				       
				  )
		  {
			  tRes="保险期间录入错误";
		  }
		  
	  }
	  else if(tRiskCode.equals("112204"))
	  {
		  if(tInsuYear.indexOf("年")==-1)
		  {
			  tRes="保险期间录入错误";
		  }
		  String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
			  		  + " order by inputno desc ";
		  ExeSQL tExeSQL = new ExeSQL();
		  String tRemark = "";
		  tRemark = tExeSQL.getOneValue(tSQL);
		  if(tRemark==null)
		  {	
			  tRemark = "";
		  }
		  if(tRemark.indexOf("A")!=-1)
		  {
			  if(tInsuYear.indexOf("10")==-1&&tInsuYear.indexOf("十")==-1)
			  {
				  tRes="保险期间录入错误";
			  }
		  }
		  else if(tRemark.indexOf("B")!=-1)
		  {
			  if(tInsuYear.indexOf("20")==-1&&tInsuYear.indexOf("二十")==-1)
			  {
				  tRes="保险期间录入错误";
			  }
		  }
		  else if(tRemark.indexOf("C")!=-1)
		  {
			  if(tInsuYear.indexOf("30")==-1&&tInsuYear.indexOf("三十")==-1)
			  {
				  tRes="保险期间录入错误";
			  }
		  }
		  else if(tRemark.indexOf("D")!=-1)
		  {
			  if(tInsuYear.indexOf("40")==-1&&tInsuYear.indexOf("四十")==-1)
			  {
				  tRes="保险期间录入错误";
			  }
		  }
	  	}
	    //短险
	  	 else if(tRiskCode.equals("111601")
	  			 ||tRiskCode.equals("111602")
	  			 ||tRiskCode.equals("111802")
	  			 ||tRiskCode.equals("121801")
	  			 ||tRiskCode.equals("131602")
	  			 ||tRiskCode.equals("121705")
	  			 ||tRiskCode.equals("121704")
	  			 ||tRiskCode.equals("111801")
	  			 ||tRiskCode.equals("121601")
	  			 )
	  	 {
	  		 if(tInsuYear.indexOf("1年")==-1&&tInsuYear.indexOf("一年")==-1)
	  		 {
	  			 tRes="保险期间录入错误";
	  		 }
	  	 }
	  	 else if(tRiskCode.equals("122202"))
		  {
			  //至17周岁、保至17岁、至17岁、保至17周岁”、“至十七周岁、保至十七岁、至十七岁、保至十七周岁
			  
			  if(!tInsuYear.equals("至17周岁")
					  &&!tInsuYear.equals("保至17岁")
					   &&!tInsuYear.equals("至17岁")
					    &&!tInsuYear.equals("保至17周岁")
					     &&!tInsuYear.equals("至十七周岁")
					      &&!tInsuYear.equals("保至十七岁")
					       &&!tInsuYear.equals("至十七岁")
					       &&!tInsuYear.equals("保至十七周岁")
					       
					  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
	  	 else if(tRiskCode.equals("122201"))
		  {
			  //至14周岁、保至14岁、至14岁、保至14周岁”、“至十四周岁、保至十四岁、至十四岁、保至十四周岁
			  
			  if(!tInsuYear.equals("至14周岁")
					  &&!tInsuYear.equals("保至14岁")
					   &&!tInsuYear.equals("至14岁")
					    &&!tInsuYear.equals("保至14周岁")
					     &&!tInsuYear.equals("至十四周岁")
					      &&!tInsuYear.equals("保至十四岁")
					       &&!tInsuYear.equals("至十四岁")
					       &&!tInsuYear.equals("保至十四周岁")
					       
					  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
	  	 else if(tRiskCode.equals("312202")
	  			 ||tRiskCode.equals("312203")
	  			 ||tRiskCode.equals("312204")
	  			 )
		 {
			  //保险期间为“5、8、五、八”年，
			  
			  if(!tInsuYear.equals("5年")
					  &&!tInsuYear.equals("8年")
					   &&!tInsuYear.equals("五年")
					    &&!tInsuYear.equals("八年")
					  )
			  {
				  tRes="保险期间录入错误";
			  }
		  }
	  //tongmeng 2009-03-12 add
	  
	  	else if(tRiskCode.equals("112213"))
		  {
			  //长瑞需要校验计划
			  String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
		      			  + " order by inputno desc ";
			  ExeSQL tExeSQL = new ExeSQL();
			  String tRemark = "";
			  tRemark = tExeSQL.getOneValue(tSQL);
			  if(tRemark==null)
			  {
				  tRemark = "";
			  }
			  if(tRemark.indexOf("A")!=-1)
			  {
				  if(tInsuYear.indexOf("30年")==-1
						  ||tInsuYear.indexOf("30Y")==-1
						  ||tInsuYear.indexOf("40Y")==-1
						  ||tInsuYear.indexOf("40年")==-1
						  )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
			  else if(tRemark.indexOf("B")!=-1)
			  {
				  //不为“至60周岁”或“至88周岁”
				  if(tInsuYear.indexOf("60")==-1
						  ||tInsuYear.indexOf("六十")==-1
						  ||tInsuYear.indexOf("88")==-1
						  ||tInsuYear.indexOf("八十八")==-1
						  )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
		  }
	  //
		else if(tRiskCode.equals("112212"))
		  {
			
			 //保险期间如不为“至60周岁”和“至80周岁”时，给出问题件
			if(tInsuYear.indexOf("80")==-1&&tInsuYear.indexOf("八十")==-1
					&&tInsuYear.indexOf("60")==-1&&tInsuYear.indexOf("六十")==-1
				)
			{
				tRes="保险期间录入错误";
				return tRes;
			}*/
			  /*
			   计划A:校验保险期间为“至80周岁”时，被保人年龄如小于28天或大于59周岁，
			   则给出问题件。
			   计划B:校验保险期间为“至60周岁”时，被保人年龄如小于28天或大于50周岁，
			   则给出问题件。
			   */
		 /*
			String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
			  + " order by inputno desc ";
			ExeSQL tExeSQL = new ExeSQL();
			String tRemark = "";
			tRemark = tExeSQL.getOneValue(tSQL);
			  String tPolApplyDateSQL = "select polapplydate from lbpocont where contno='"+tContNo+"' "
              						  + " order by inputno desc ";
			  String tPolApplyDate = tExeSQL.getOneValue(tPolApplyDateSQL);
			  if(tPolApplyDate==null||tPolApplyDate.equals("")){
				  return "";
			  }
			  String tInsuredBirthDaySQL = " select birthday from lbpoinsured where contno='"+tContNo+"' "
			  						  + " order by inputno desc ";
			  String tBirthday = tExeSQL.getOneValue(tInsuredBirthDaySQL);
			  
			  int tAge = PubFun.calInterval(tBirthday,tPolApplyDate, "Y");
			  int tAge1 = PubFun.calInterval(tBirthday,tPolApplyDate, "D");

			  if(tInsuYear.indexOf("80")!=-1||tInsuYear.indexOf("八十")!=-1
					  )
			  {
				//A计划
				  if(tRemark!=null&&tRemark.indexOf("A")==-1)
				  {
					  tRes="保险期间与保险计划不符";
					  return tRes;
				  }
				  
				  if(tAge>59
				    ||(tAge==0&&tAge1<28)
				    )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
			  else if(tInsuYear.indexOf("60")!=-1||tInsuYear.indexOf("六十")!=-1
					  )
			  {
				//B计划
				  if(tRemark!=null&&tRemark.indexOf("B")==-1)
				  {
					  tRes="保险期间与保险计划不符";
					  return tRes;
				  }
				  if(tAge>50
				    ||(tAge==0&&tAge1<28)
				    )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
		  }
	  //
		else if(tRiskCode.equals("121305"))
		  {
		  */
			/*
			 1、保险期间如不为“20年”、“30年”、“40年”、“至60周岁”、“至80周岁”和“至88周岁”时，给出问题件。


			 */
			 //保险期间如不为“至60周岁”和“至80周岁”时，给出问题件
		/*	
		 if(tInsuYear.indexOf("20")==-1&&tInsuYear.indexOf("二十")==-1
					&&tInsuYear.indexOf("30")==-1&&tInsuYear.indexOf("三十")==-1
					&&tInsuYear.indexOf("40")==-1&&tInsuYear.indexOf("四十")==-1
					&&tInsuYear.indexOf("80")==-1&&tInsuYear.indexOf("八十")==-1
					&&tInsuYear.indexOf("88")==-1&&tInsuYear.indexOf("八十八")==-1

			)
			{
				tRes="保险期间录入错误";
				return tRes;
			}
			*/
			  /*
			   2、此项导入系统时，只取数字（转换为阿拉伯形式），不取单位。
A计划：保险期间为“20年”、“30年”和“40年”，如不为以上几种，给出问题件；
B计划：保险期间为“至60周岁”、“至80周岁和“至88周岁”，如不为以上几种，给出问题件。。
			   */
		/*	String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
			  + " order by inputno desc ";
			ExeSQL tExeSQL = new ExeSQL();
			String tRemark = "";
			tRemark = tExeSQL.getOneValue(tSQL);
			 if(tRemark.indexOf("A")!=-1)
			  {
				  if(tInsuYear.indexOf("20")==-1
						  ||tInsuYear.indexOf("二十")==-1
						  ||tInsuYear.indexOf("30")==-1
						  ||tInsuYear.indexOf("三十")==-1
						  ||tInsuYear.indexOf("40")==-1
						  ||tInsuYear.indexOf("四十")==-1
						  )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
			  else if(tRemark.indexOf("B")!=-1)
			  {
				  //不为“至60周岁”或“至88周岁”
				  if(tInsuYear.indexOf("60")==-1
						  ||tInsuYear.indexOf("六十")==-1
						  ||tInsuYear.indexOf("88")==-1
						  ||tInsuYear.indexOf("八十八")==-1
						  ||tInsuYear.indexOf("80")==-1
						  ||tInsuYear.indexOf("八十")==-1
						  )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
		  }
		else if(tRiskCode.equals("121505"))
		  {*/
			/*
			1、保险期间如不为“20年”、“30年”、“40年”、“至60周岁”、“至80周岁”和“至88周岁”时，给出问题件。
2、此项导入系统时，只取数字（转换为阿拉伯形式），不取单位。
A计划：保险期间为“20年”、“30年”和“40年”，如不为以上几种，给出问题件；
B计划：保险期间为“至60周岁”、“至80周岁和“至88周岁”，如不为以上几种，给出问题件。


			 */
			 //保险期间如不为“至60周岁”和“至80周岁”时，给出问题件
/*			if(tInsuYear.indexOf("20")==-1&&tInsuYear.indexOf("二十")==-1
					&&tInsuYear.indexOf("30")==-1&&tInsuYear.indexOf("三十")==-1
					&&tInsuYear.indexOf("40")==-1&&tInsuYear.indexOf("四十")==-1
					&&tInsuYear.indexOf("80")==-1&&tInsuYear.indexOf("八十")==-1
					&&tInsuYear.indexOf("88")==-1&&tInsuYear.indexOf("八十八")==-1

			)
			{
				tRes="保险期间录入错误";
				return tRes;
			}*/
			  /*
			   2、此项导入系统时，只取数字（转换为阿拉伯形式），不取单位。
A计划：保险期间为“20年”、“30年”和“40年”，如不为以上几种，给出问题件；
B计划：保险期间为“至60周岁”、“至80周岁和“至88周岁”，如不为以上几种，给出问题件。。
			   */
			/*String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
			  + " order by inputno desc ";
			ExeSQL tExeSQL = new ExeSQL();
			String tRemark = "";
			tRemark = tExeSQL.getOneValue(tSQL);
			 if(tRemark.indexOf("A")!=-1)
			  {
				  if(tInsuYear.indexOf("20")==-1
						  ||tInsuYear.indexOf("二十")==-1
						  ||tInsuYear.indexOf("30")==-1
						  ||tInsuYear.indexOf("三十")==-1
						  ||tInsuYear.indexOf("40")==-1
						  ||tInsuYear.indexOf("四十")==-1
						  )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
			  else if(tRemark.indexOf("B")!=-1)
			  {
				  //不为“至60周岁”或“至88周岁”
				  if(tInsuYear.indexOf("60")==-1
						  ||tInsuYear.indexOf("六十")==-1
						  ||tInsuYear.indexOf("88")==-1
						  ||tInsuYear.indexOf("八十八")==-1
						  ||tInsuYear.indexOf("80")==-1
						  ||tInsuYear.indexOf("八十")==-1
						  )
				  {
					  tRes="保险期间录入错误";
				  }
			  }
		  }*/
	  return tRes;
  }
  
  /**
   * 告知校验
   */
  public String checkImpart(){
	  String tResult="";
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.ContNo");
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.InputNo");
	  String tInsured1 = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.Insured1");
	  String tImpartParamModle = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.ImpartParamModle");
	  String tPrtFlag = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.PrtFlag");
	  String tImpartCode = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.ImpartCode");
	  String tImpartVer = (String)this.mTransferData.getValueByName("LBPOCustomerImpart.ImpartVer");
	  String tPolType = tContNo.substring(0,4);
	  //得到投被保人关系
	  SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
	  sqlbv25.sql("select relationtoinsured from lbpoappnt where contno ='"+"?contno?"+"' and inputno ='"+"?inputno?"+"'");
	  sqlbv25.put("contno", tContNo);
	  sqlbv25.put("inputno", tInputNo);
	  ExeSQL tExeSQL =new ExeSQL();
	  String tRelationToInsured =tExeSQL.getOneValue(sqlbv25);
	  if(tRelationToInsured==null){
		  tRelationToInsured="";
	  }
	  
	  if(tPolType.equals("8616"))
	  {
		  if(tInsured1==null||"".equals(tInsured1)||"null".equals(tInsured1)){
			  tResult=tResult+"告知为选择‘是或否’;";
		  }else{
			  if(!"0".equals(tInsured1)&&!"1".equals(tInsured1)){
				  tResult=tResult+"告知选择的不是‘是或否’;";
			  }
		  }
	  }
	  if(tPolType.equals("8611")){
//		  if("1".equals(tPrtFlag)&&"1".equals(tInsured1)){
//			  tImpartParamModle=StrTool.replace(tImpartParamModle, "/", "");
//			  if(tImpartParamModle!=null&&!"".equals(tImpartParamModle)){
//				  tResult=tResult+"投被保人告知都选择了否，但是告知内容非空;";
//			  }
//		  }
		  String tImpartInfo[]=tImpartParamModle.split("/");
		  //健康告知
		  if("A01".equals(tImpartVer)){
			  if("A0101".equals(tImpartCode)){
				  //被保险人/投保人身高及体重
				  if(tImpartParamModle!=null&&tImpartParamModle.indexOf("/")==-1){
					  tResult=tResult+"投、被保人身高、体重处内容没有‘/’;";
				  }
				  if(StrTool.replace(tImpartParamModle, "/", "").equals("")){
					  tResult =tResult+"投、被保人身高、体重处内容没有录入;";
				  }else{
					  if(tImpartInfo.length>2){
						  if(tImpartInfo[0]==null||"".equals(tImpartInfo[0])
								  ||tImpartInfo[1]==null||"".equals(tImpartInfo[1])){
							  tResult =tResult+"被保人没有填写身高体重,或是内容不完整;";
						  }
					  }else{
						  tResult =tResult+"被保人没有填写身高体重,或是内容不完整;";
					  }
				  }
			  }
			  if("A0102".equals(tImpartCode)){
				  //被保险人/投保人身高及体重
				  if(tImpartParamModle!=null&&tImpartParamModle.indexOf("/")==-1){
					  tResult=tResult+"投、被保人在有无吸烟习惯处的内容中没有‘/’;";
				  }
				  if(
						  //tPrtFlag==null||"".equals(tPrtFlag)||"null".equals(tPrtFlag)
						  tInsured1==null||"".equals(tInsured1)||"null".equals(tInsured1)){
					  tResult=tResult+"	被保人在有无吸烟习惯处没有选择‘是或否’;";
				  }else{
					  if(tImpartParamModle!=null){
						  if(tImpartInfo.length>2){
							  //说明有被保人信息
							  if(tInsured1.equals("0")
									  &&(tImpartInfo==null||"".equals(tImpartInfo[0]))
											  ||"".equals(tImpartInfo[1])){
								  tResult=tResult+"被保人在是否吸烟处选择了是，但是内容中没有填写,或是内容不完整;";
							  }
							  if(tInsured1.equals("1")
									  &&(tImpartInfo!=null||!"".equals(tImpartInfo[0]))
											  ||!"".equals(tImpartInfo[1])){
								  tResult=tResult+"被保人在是否吸烟处选择了否，但是有内容;";
							  }
						  }
					  }
					  if(!tInsured1.equals("1")&&!tInsured1.equals("0")){
						  tResult =tResult+"被保人在是否吸烟处的选择不为‘是或否’;";
					  }
				  }
				  
			  }
			  if("A0103".equals(tImpartCode)){
				  if(tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1)
				  ){
					  tResult=tResult+"被保人饮酒处没有选择是或否;";
				  }else{
					  if(!tInsured1.equals("1")&&!tInsured1.equals("0")){
						  tResult=tResult+"被保人饮酒处选择不明确，不为是或否;";
					  }
				  }
				  if((tInsured1!=null&&"null".equals(tInsured1)&&"".equals(tInsured1))
						  ||(tPrtFlag!=null&&"null".equals(tPrtFlag)&&"".equals(tPrtFlag))){
					  if(tImpartParamModle==null||"".equals(StrTool.replace(tImpartParamModle, "/", ""))){
						  tResult =tResult+"投、被保人有无饮酒习惯处选择了是或否，但是内容为空;";
					  }
				  }
			  }
			  if("A0104".equals(tImpartCode)){
				  if(tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1)
				  ){
					  tResult=tResult+"被保人有无驾驶执照处没有选择是或否;";
				  }else{
					  if(!tInsured1.equals("1")&&!tInsured1.equals("0")){
						  tResult=tResult+"被保人有无驾驶执照处选择不明确，不为是或否;";
					  }
				  }
				  if((tInsured1!=null&&"null".equals(tInsured1)&&"".equals(tInsured1))
						  ||(tPrtFlag!=null&&"null".equals(tPrtFlag)&&"".equals(tPrtFlag))){
					  if(tImpartParamModle==null||"".equals(StrTool.replace(tImpartParamModle, "/", ""))){
						  tResult =tResult+"投、被保人有无驾驶执照处选择了是或否，但是内容为空;";
					  }
				  }
			  }
			  if("A0105".equals(tImpartCode)){
				  if(tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1)
				  ){
					  tResult=tResult+"被保人有无参加飞行等危险运动处没有选择是或否;";
				  }else{
					  if(!tInsured1.equals("1")&&!tInsured1.equals("0")){
						  tResult=tResult+"被保人有无参加飞行等危险运动处选择不明确，不为是或否;";
					  }
				  }
				  if((tInsured1!=null&&"null".equals(tInsured1)&&"".equals(tInsured1))
						  ||(tPrtFlag!=null&&"null".equals(tPrtFlag)&&"".equals(tPrtFlag))){
					  if(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle)){
						  tResult =tResult+"投、被保人有无驾驶执照处选择了是或否，但是内容为空;";
					  }
				  }
			  }
			  if("A0106".equals(tImpartCode)||"A0107".equals(tImpartCode)||"A0108".equals(tImpartCode)
					  ||"A0109".equals(tImpartCode)||"A0110".equals(tImpartCode)||"A0111a".equals(tImpartCode)
					  ||"A0111b".equals(tImpartCode)){
				  if(tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1)
				  ){
					  tResult=tResult+"被保人在6-12项(编码："+tImpartCode+")处没有选择是或否;";
				  }else{
					  if(!tInsured1.equals("1")&&!tInsured1.equals("0")){
						  tResult=tResult+"被保人在6-12项(编码："+tImpartCode+")处选择不明确，不为是或否;";
					  }
				  }
			  }
			  //女性补充告知
			  if("A0113a".equals(tImpartCode)||"A0113b".equals(tImpartCode)){
				  if("A0113a".equals(tImpartCode)){
					  if(("0".equals(tInsured1)||"0".equals(tPrtFlag))&&(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle))){
						  tResult=tResult+"女性补充告知处投、被保人选择了是，但是内容为空;";
					  }
					  if("1".equals(tInsured1)&&"1".equals(tPrtFlag)&&tImpartParamModle!=null&&!"null".equals(tImpartParamModle)&&!"".equals(tImpartParamModle)){
						  tResult=tResult+"女性补充告知处投、被保人选择了否，但是内容非空;";
					  }
					  if("00".equals(tRelationToInsured)){
						  if((tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!"1".equals(tInsured1)&&!"0".equals(tInsured1))
								  ||(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!"1".equals(tPrtFlag)&&!"0".equals(tPrtFlag))){
							  tResult=tResult+"女性补充告知处投被保人为同一人，但是都录入错误;";
						  }
					  }
                      if(!"".equals(tRelationToInsured)&&!"00".equals(tRelationToInsured)){
						  if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&(!"1".equals(tInsured1)&&!"0".equals(tInsured1))){
							  tResult =tResult+"女性补充告知处被保人选择不明确，不为是或否;";
						  }
						  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&(!"1".equals(tPrtFlag)&&!"0".equals(tPrtFlag))){
							  tResult=tResult+"女性补充告知处投保人选择不明确，不为是或否;";
						  }
					  }
				  }
				  LBPOInsuredDB tLBPOInsuredDB =new LBPOInsuredDB();
				  LBPOInsuredSet tLBPOInsuredSet =new LBPOInsuredSet();
				  LBPOInsuredSchema tLBPOInsuredSchema =new LBPOInsuredSchema();
				  SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				  sqlbv27.sql("select * from lbpoinsured where contno ='"+"?contno?"+"' and inputno ='"+"?inputno?"+"' and sequenceno ='1'");
				  sqlbv27.put("contno", tContNo);
				  sqlbv27.put("inputno", tInputNo);
				  tLBPOInsuredSet=tLBPOInsuredDB.executeQuery(sqlbv27);
				  if(tLBPOInsuredSet.size()>0){
					  tLBPOInsuredSchema=tLBPOInsuredSet.get(1);
				  }
				  LBPOAppntDB tLBPOAppntDB =new LBPOAppntDB();
				  LBPOAppntSet tLBPOAppntSet =new LBPOAppntSet();
				  LBPOAppntSchema tLBPOAppntSchema =new LBPOAppntSchema();
				  SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
				  sqlbv26.sql("select * from lbpoappnt where contno ='"+"?contno?"+"' and inputno ='"+"?inputno?"+"'");
				  sqlbv26.put("contno", tContNo);
				  sqlbv26.put("inputno", tInputNo);
				  tLBPOAppntSet=tLBPOAppntDB.executeQuery(sqlbv26);
				  if(tLBPOAppntSet.size()>0){
					  tLBPOAppntSchema =tLBPOAppntSet.get(1);
				  }
				  String tPolApplyDate ="";
				  String tPolApplyDateSQL = "select polapplydate from lbpocont where contno='"+"?contno?"+"' and inputno ='"+"?inputno?"+"' "
				  + " order by inputno desc ";
				  SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				  sqlbv28.sql(tPolApplyDateSQL);
				  sqlbv28.put("contno", tContNo);
				  sqlbv28.put("inputno", tInputNo);
				  tPolApplyDate = tExeSQL.getOneValue(sqlbv28);
				  if(tPolApplyDate==null||tPolApplyDate.equals("")){
					  return tResult;
				  }
				  if(tInsured1==null){
					  int tAge = PubFun.calInterval(tLBPOInsuredSchema.getBirthday(),tPolApplyDate, "Y");
					  if(tAge>=14&&"1".equals(tLBPOInsuredSchema.getSex())){
						  tResult=tResult+"被保人为女性、且满14周岁，但是没有填写女性补充告知;";
					  }
					  
				  }
				  if(tPrtFlag==null){
					  int tAge = PubFun.calInterval(tLBPOAppntSchema.getAppntBirthday(),tPolApplyDate, "Y");
					  if(tAge>=14&&"1".equals(tLBPOInsuredSchema.getSex())){
						  tResult=tResult+"投保人为女性、且满14周岁，但是没有填写女性补充告知;";
					  }
					  
				  }
			  }
			  if("A0114a".equals(tImpartCode)||"A0114b".equals(tImpartCode)){
				  if("A0114a".equals(tImpartCode)){
					  if(tImpartParamModle!=null&&tImpartParamModle.indexOf("/")==-1){
						  tResult=tResult+"投、被保人在婴幼儿补充告知栏的内容中没有‘/’;";
					  }
				  }
				  if("A0114b".equals(tImpartCode)){
					  if("00".equals(tRelationToInsured)){
						  if((tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!"1".equals(tInsured1)&&!"0".equals(tInsured1))
								  ||(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!"1".equals(tPrtFlag)&&!"0".equals(tPrtFlag))){
							  tResult=tResult+"婴幼儿补充告知栏处投被保人为同一人，但是都录入错误;";
						  }
					  }
					  if(!"".equals(tRelationToInsured)&&!"00".equals(tRelationToInsured)){
						  if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!"1".equals(tInsured1)&&!"0".equals(tInsured1)){
							  tResult =tResult+"被保人在婴幼儿补充告知栏处选择不明确，不为是或否;";
						  }
						  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!"1".equals(tPrtFlag)&&!"0".equals(tPrtFlag)){
							  tResult =tResult+"投保人在婴幼儿补充告知栏处选择不明确，不为是或否;";
						  }
					  }
				  }
				  if(("0".equals(tPrtFlag)||"0".equals(tInsured1))&&"A0114a".equals(tImpartCode)){
					  if("".equals(StrTool.replace(tImpartParamModle, "/", ""))){
						  tResult=tResult+"投保人或是被保人在婴幼儿补充告知栏处选择了是，但内容没有录入;";
					  }
				  }
				  LBPOInsuredDB tLBPOInsuredDB =new LBPOInsuredDB();
				  LBPOInsuredSet tLBPOInsuredSet =new LBPOInsuredSet();
				  LBPOInsuredSchema tLBPOInsuredSchema =new LBPOInsuredSchema();
				  SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
				  sqlbv29.sql("select * from lbpoinsured where contno ='"+"?contno?"+"' and inputno ='"+"?inputno?"+"' and sequenceno ='1'");
				  sqlbv29.put("contno", tContNo);
				  sqlbv29.put("inputno", tInputNo);
				  tLBPOInsuredSet=tLBPOInsuredDB.executeQuery(sqlbv29);
				  if(tLBPOInsuredSet.size()>0){
					  tLBPOInsuredSchema=tLBPOInsuredSet.get(1);
				  }
				  String tPolApplyDate ="";
				  String tPolApplyDateSQL = "select polapplydate from lbpocont where contno='"+"?contno?"+"' and inputno ='"+"?inputno?"+"' "
				  + " order by inputno desc ";
				  SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
				  sqlbv30.sql(tPolApplyDateSQL);
				  sqlbv30.put("contno", tContNo);
				  sqlbv30.put("inputno", tInputNo);
				  tPolApplyDate = tExeSQL.getOneValue(sqlbv30);
				  if(tPolApplyDate==null||tPolApplyDate.equals("")){
					  return tResult;
				  }
				  int tAge = PubFun.calInterval(tLBPOInsuredSchema.getBirthday(),tPolApplyDate, "Y");
				  if((tInsured1==null||"".equals(tInsured1)||"null".equals(tInsured1))&&tAge<=2){
					  tResult =tResult+"被保人年龄没有超过2岁，但是没有选择婴幼儿补充告知;";
				  }
				  
			  }
		 if("A0115a".equals(tImpartCode)||"A0115b".equals(tImpartCode)){
			 if("00".equals(tRelationToInsured)){
				 if((tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1))
						 &&(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag))){
					 tResult=tResult+"15项告知(编码："+tImpartCode+")，投被保人为同一人，但是均没有录入是或否;";
				 }
				 if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!tInsured1.equals("1")&&!tInsured1.equals("0")
					&&tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
					 tResult =tResult+"15项告知(编码："+tImpartCode+")，投被保人为同一人，但是录入均不为是或否;";
				 }
			  }
			 if(!"".equals(tRelationToInsured)&&!"00".equals(tRelationToInsured)){
				 if(tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1)){
					 tResult =tResult+"被保人15项告知(编码："+tImpartCode+")没有选择是或否;";
				 }
				 if(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag)){
					 tResult =tResult+"投保人15项告知(编码："+tImpartCode+")没有选择是或否;";
				 }
				 if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!tInsured1.equals("1")&&!tInsured1.equals("0")){
					 tResult =tResult+"被保人15项告知(编码："+tImpartCode+")选择不明确，不为是或否;";
				 }
				 if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
					 tResult =tResult+"投保人15项告知(编码："+tImpartCode+")选择不明确，不为是或否;";
				 }
			 }
			  }
		  }
		  //保险、财务
		  if("A02".equals(tImpartVer)){
			  if("A0116".equals(tImpartCode)||"A0117".equals(tImpartCode)
					  ||"A0118".equals(tImpartCode)||"A0119".equals(tImpartCode)){
				  if("00".equals(tRelationToInsured)){
					  if((tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1))
							  &&(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag))){
						  tResult =tResult+"16-19项告知(编码："+tImpartCode+")，投被保人为同一人，但是均没有录入是或否;";
					  }
					  if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!tInsured1.equals("1")&&!tInsured1.equals("0")
								&&tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
								 tResult =tResult+"16-19项告知(编码："+tImpartCode+")，投被保人为同一人，但是录入均不为是或否;";
							 }
				  }
				  if(!"".equals(tRelationToInsured)&&!"00".equals(tRelationToInsured)){
					  if(tInsured1==null||"null".equals(tInsured1)||"".equals(tInsured1)){
						  tResult =tResult+"被保人16-19项告知(编码："+tImpartCode+")没有选择是或否;";
					  }
					  if(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag)){
						  tResult =tResult+"投保人16-19项告知(编码："+tImpartCode+")没有选择是或否;";
					  }
					  if(tInsured1!=null&&!"null".equals(tInsured1)&&!"".equals(tInsured1)&&!tInsured1.equals("1")&&!tInsured1.equals("0")){
						  tResult =tResult+"被保人16-19项(编码："+tImpartCode+")选择不明确，不为是或否;";
					  }
					  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
						  tResult =tResult+"投保人16-19项(编码："+tImpartCode+")选择不明确，不为是或否;";
					  }
				  }
			  }
			  if("A0120".equals(tImpartCode)){
				  LBPOAppntDB tLBPOAppntDB =new LBPOAppntDB();
				  LBPOAppntSet tLBPOAppntSet =new LBPOAppntSet();
				  LBPOAppntSchema tLBPOAppntSchema =new LBPOAppntSchema();
				  SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
				  sqlbv31.sql("select * from lbpoappnt where contno ='"+"?contno?"+"' and inputno ='"+"?inputno?"+"'");
				  sqlbv31.put("contno", tContNo);
				  sqlbv31.put("inputno", tInputNo);
				  tLBPOAppntSet=tLBPOAppntDB.executeQuery(sqlbv31);
				  if(tLBPOAppntSet.size()>0){
					  tLBPOAppntSchema =tLBPOAppntSet.get(1);
				  }
				  if("00".equals(tLBPOAppntSchema.getRelationToInsured())){
					  if(tImpartParamModle!=null&&StrTool.replace(tImpartParamModle, "/", "").equals("")){
						  tResult =tResult+"每年固定收入;投被保人关系为同一人，但是投、被保人均没有录入;";
					  }
				  }
				  if(tLBPOAppntSchema.getRelationToInsured()!=null&&!"00".equals(tLBPOAppntSchema.getRelationToInsured())){
					  if(tImpartInfo.length>2){
						  //被保人不管
//						  if("".equals(tImpartInfo[0])||"".equals(tImpartInfo[1])){
//							  tResult =tResult+"每年固定收入;投被保人关系不是同一人，但是被保人没有录入,或是录入不完整;";
//						  }
					  }
					  else{
						  tResult =tResult+"每年固定收入;投被保人关系不是同一人，但是投保人没有录入,或是录入不完整;";
					  }
					  if(tImpartInfo.length>3){
						  if("".equals(tImpartInfo[2])||"".equals(tImpartInfo[3])){
							  tResult =tResult+"每年固定收入;投被保人关系不是同一人，但是投保人均没有录入，或录入不完整;";
						  }
					  }
					  if(tImpartInfo.length==3){
						  tResult =tResult+"每年固定收入;投被保人关系不是同一人，但是投保人均没有录入，或录入不完整;";
					  }
				  }
				  String tAppntIncome[]=tImpartParamModle.split("/");
				  String tIncome="";
				  if(tAppntIncome.length>3){
					  tIncome =tAppntIncome[2]+tAppntIncome[3];
					  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
					  String tRule = "|WITHOUT:?^？"; 
					  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tIncome,tRule,null,"0");
					  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
					  {
						  if(tErrorInfo.indexOf("(")!=-1)
						  {
							  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
						  }
						  return tErrorInfo;
					  }
				  }
			  }
		  }
		  //业务员告知
		  if("A03".equals(tImpartVer)){
			  if("A0151".equals(tImpartCode)){
				  if(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag)){
					  tResult =tResult+"投保人在您是被保险人的亲属没有选择是或否;";
				  }
				  if("0".equals(tPrtFlag)&&(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle))){
					  tResult=tResult+"您是被保险人的亲属处投保人选择了是，但是内容为空;";
				  }
				  if("1".equals(tPrtFlag)&&tImpartParamModle!=null&&!"null".equals(tImpartParamModle)&&!"".equals(tImpartParamModle)){
					  tResult=tResult+"您是被保险人的亲属处投保人选择了否，但是内容非空;";
				  }
				  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
					  tResult =tResult+"投保人您是被保险人的亲属项选择不明确，不为是或否;";
				  }
			  }
			  if("A0152".equals(tImpartCode)){
				  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
					  tResult =tResult+"投保人您亲眼见过被保险人项选择不明确，不为是或否;";
				  }
				  if(tPrtFlag!=null&&"1".equals(tPrtFlag)){  
					  tResult=tResult+"投保人您亲眼见过被保险人项选择了否;";
				  }
				  
			  }
			  if("A0153".equals(tImpartCode)){
				  if(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag)){
					  tResult =tResult+"投保人在被保险人是否有身体缺陷没有选择是或否;";
				  }
				  if("0".equals(tPrtFlag)&&(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle))){
					  tResult=tResult+"被保险人是否有身体缺陷处投保人选择了是，但是内容为空;";
				  }
				  if("1".equals(tPrtFlag)&&tImpartParamModle!=null&&!"null".equals(tImpartParamModle)&&!"".equals(tImpartParamModle)){
					  tResult=tResult+"被保险人是否有身体缺陷处投保人选择了否，但是内容非空;";
				  }
				  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
					  tResult =tResult+"投保人被保险人是否有身体缺陷项选择不明确，不为是或否;";
				  }
			  }
			  if("A0154".equals(tImpartCode)){
				  if(tPrtFlag==null||"null".equals(tPrtFlag)||"".equals(tPrtFlag)){
					  tResult =tResult+"投保人在被保险人是否有危险运动爱好没有选择是或否;";
				  }
				  if("0".equals(tPrtFlag)&&(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle))){
					  tResult=tResult+"被保险人是否有危险运动爱好处投保人选择了是，但是内容为空;";
				  }
				  if("1".equals(tPrtFlag)&&tImpartParamModle!=null&&!"null".equals(tImpartParamModle)&&!"".equals(tImpartParamModle)){
					  tResult=tResult+"被保险人是否有危险运动爱好处投保人选择了否，但是内容非空;";
				  }
				  if(tPrtFlag!=null&&!"null".equals(tPrtFlag)&&!"".equals(tPrtFlag)&&!tPrtFlag.equals("1")&&!tPrtFlag.equals("0")){
					  tResult =tResult+"投保人被保险人是否有危险运动爱好项选择不明确，不为是或否;";
				  }
			  }
			  if("A0155".equals(tImpartCode)){
				  if(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle)){
					  tResult=tResult+"投保经过未录入内容;";
				  }
			  }
			  if("A0156".equals(tImpartCode)){
				  if(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle)){
					  tResult=tResult+"投保目的未录入内容;";
				  }
			  }
			  if("A0157".equals(tImpartCode)){
				  if(tImpartParamModle==null||"null".equals(tImpartParamModle)||"".equals(tImpartParamModle)){
					  tResult=tResult+"家庭总资产未录入内容;";
				  }
			  }
		  }
		  //业务员告知
	  }
	  return tResult;
  }
  /**
   * 校验合同备注信息
   * @return
   */
  public String checkLCContRemark()
  {
	 String tResult = "";
	 String tImpartRemark = (String)this.mTransferData.getValueByName("LBPOCont.ImpartRemark");
	 if(tImpartRemark!=null&&tImpartRemark.indexOf("受益人")!=-1)
	 {
		 tResult = "备注中包含‘受益人’信息";
	 }
	 return tResult;
  }

  /**
   * 校验交费期间
   * @return
   */
  public String checkPayYearsInfo(){
	  String tRes="";
	  String tPayEndYear = this.mTransferData.getValueByName("LBPOPol.PayEndYear")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.PayEndYear");
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  //String tPayIntv = this.mTransferData.getValueByName("LBPOPol.PayIntv")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.PayIntv");
	  String tInsuYear = this.mTransferData.getValueByName("LBPOPol.InsuYear")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.InsuYear");
	  String tPayYears = this.mTransferData.getValueByName("LBPOPol.PayYears")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.PayYears");
	  String tContNo = this.mTransferData.getValueByName("LBPOPol.ContNo")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.ContNo");
	 
	  String tPayIntv = "";
	  String tSQL = "select payintv from lbpocont where contno='"+"?contno?"+"' order by inputno desc";
	  SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
	  sqlbv32.sql(tSQL);
	  sqlbv32.put("contno", tContNo);
	  ExeSQL tExeSQL = new ExeSQL();
	  tPayIntv = tExeSQL.getOneValue(sqlbv32);
	  if(tPayIntv==null||tPayIntv.equals("null"))
	  {
		  tPayIntv = "";
	  }
	  //如果包含年 或至和岁 校验录入是否合法 即数字+年、至+数字+岁
	  if(!tPayEndYear.equals("")&&!tPayEndYear.equals("null"))
	  {
	  if(tPayEndYear.indexOf("年")!=-1||
			  (tPayEndYear.indexOf("交")!=-1&&tPayEndYear.indexOf("年")!=-1)
			  ||(tPayEndYear.indexOf("缴")!=-1&&tPayEndYear.indexOf("年")!=-1)
			  ){
			String tReplace1 = StrTool.replace(tPayEndYear, "年", "");
			String tReplace2 = StrTool.replace(tReplace1, "交", "");
			String tReplace3 = StrTool.replace(tReplace2, "缴", "");
			if(!tReplace3.equals(tPayEndYear.replaceAll("[^0-9]", ""))){
				tRes="交费期间录入不是数字+年";
		  }
	  }else if((tPayEndYear.indexOf("至")!=-1&&tPayEndYear.indexOf("岁")!=-1)
			  ||(tPayEndYear.indexOf("至")!=-1&&tPayEndYear.indexOf("周岁")!=-1)
			  ||(tPayEndYear.indexOf("交至")!=-1&&tPayEndYear.indexOf("周岁")!=-1)
			  ||(tPayEndYear.indexOf("缴至")!=-1&&tPayEndYear.indexOf("周岁")!=-1)
			  ||(tPayEndYear.indexOf("缴至")!=-1&&tPayEndYear.indexOf("岁")!=-1)
			  ){
			String tReplace1 = StrTool.replace(tPayEndYear, "至", "");
			String tReplace2 = StrTool.replace(tReplace1, "岁", "");
			String tReplace3 = StrTool.replace(tReplace2, "周", "");
			String tReplace4 = StrTool.replace(tReplace3, "缴", "");
			String tReplace5 = StrTool.replace(tReplace4, "交", "");
		if(!tReplace5.equals(tPayEndYear.replaceAll("[^0-9]", ""))){
				tRes="交费期间录入不是至+数字+岁";
		  }
      }
//	  else if(tPayEndYear.indexOf("趸")==-1||tPayEndYear.indexOf("一次")==-1){
//		tRes = "交费期间无“年”、“至 岁”、“趸”或“一次”";
//	}
	  }
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621")||tPolType.equals("8616"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tPayEndYear,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  if(tPayIntv.equals("0"))
		  {
			  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tPayEndYear,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
			  if(tPayEndYear!=null&&!"null".equals(tPayEndYear)&&!"".equals(tPayEndYear)&&tPayEndYear.indexOf("1")==-1&&tPayEndYear.indexOf("一")==-1
					  &&tPayEndYear.indexOf("趸")==-1)
			  {
				  return "交费间隔为趸交,交费期间录入错误";
			  }
		  }
		  else
		  {
			  tRule = "|notnull&WITHOUT:?^？"; 
			  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tPayEndYear,tRule,null,"0");
			  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
			  {
				  if(tErrorInfo.indexOf("(")!=-1)
				  {
					  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
				  }
				  return tErrorInfo;
			  }
			  if(tPayEndYear.indexOf("1")!=-1||tPayEndYear.indexOf("一")!=-1
					  ||tPayEndYear.indexOf("趸")!=-1)
			  {
				  return "交费间隔为期交,交费期间录入错误";
			  }
		  }
		 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tPayEndYear,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  //简易没有交费方式、交费年期、保险期间
	  /*
	  if(!tPolType.equals("8616")){
		  if(!tRes.equals(""))
		  {
			  return tRes;
		  }
		  
		  if(tRiskCode.equals("112203")
				  ||tRiskCode.equals("112101")
				  ||tRiskCode.equals("112201")
				  ||tRiskCode.equals("112207")
				  ||tRiskCode.equals("111504")
				  ||tRiskCode.equals("112208")
		  )
		  {
			  if(tPayYears.indexOf("年")==-1&&
					  tPayYears.indexOf("趸")==-1
					  &&tPayYears.indexOf("一次")==-1
			  )
			  {
				  tRes="交费期间不为:年,趸,一次";
			  }
		  }
		  else if(tRiskCode.equals("112202")
				  ||tRiskCode.equals("111301")
				  ||tRiskCode.equals("112206")
		  )
		  {
			  if(tPayYears.indexOf("年")!=-1
					  ||(tPayYears.indexOf("至")!=-1&&tPayYears.indexOf("岁")!=-1)
					  ||tPayYears.indexOf("趸")!=-1
					  ||tPayYears.indexOf("一次")!=-1
			  )
			  {
				  
			  }
			  else
			  {
				  tRes="交费期间不为:年,至 岁,趸,一次";
			  }
		  }
		  else if(tRiskCode.equals("112401")
		  )
		  {
			  if(tPayYears.indexOf("年")!=-1
					  ||(tPayYears.indexOf("至")!=-1&&tPayYears.indexOf("岁")!=-1))
			  {
				  tRes="交费期间不为:年,至 岁 ";
			  }
		  }
		  else if(tRiskCode.equals("112204")
		  )
		  {
			  if(!tPayIntv.equals("0"))
			  {
				  //如果不为趸交,需要判断缴费年期和保险期间是否一致
				  if(!tInsuYear.equals(tPayYears))
				  {
					  tRes="交费期间与保险期间不一致";
				  }
			  }
			  else
			  {
				  if(tPayYears.indexOf("年")==-1&&
						  tPayYears.indexOf("趸")==-1
						  &&tPayYears.indexOf("一次")==-1
				  )
				  {
					  tRes="交费期间不为:年,趸,一次";
				  }
			  }
		  }
		  //短险
		  else if(tRiskCode.equals("111601")
				  ||tRiskCode.equals("111602")  
				  ||tRiskCode.equals("111802")  
				  ||tRiskCode.equals("121801")  
				  ||tRiskCode.equals("131602")
				  ||tRiskCode.equals("121705")
				  ||tRiskCode.equals("121704")
				  ||tRiskCode.equals("111801")
				  ||tRiskCode.equals("121601")
		  )
		  {
			  if(tPayYears.indexOf("1年")==-1&&tPayYears.indexOf("一年")==-1)
			  {
				  tRes="交费期间录入错误";
			  }
		  }
		  else if(tRiskCode.equals("312202")
				  ||tRiskCode.equals("312203")
				  ||tRiskCode.equals("312204")
		  )
		  {
			  if(tPayYears.equals(""))
			  {
				  if(tPayIntv.equals("12"))
				  {
					  tRes="交费期间录入错误";
				  }
			  }
			  else
			  {
				  if(tPayIntv.equals("12"))
				  {
					  if(!tPayYears.equals(tInsuYear))
					  {
						  tRes="交费期间录入错误";
					  }
				  }
				  else if(tPayIntv.equals("0"))
				  {
					  String tTemptPayYears = tPayYears.replaceAll("[^0-9]","");
					  if(tTemptPayYears.equals(""))
					  {
						  tTemptPayYears = "-1";
					  }
					  String tTemptInsuYear = tInsuYear.replaceAll("[^0-9]","");
					  if(tTemptInsuYear.equals(""))
					  {
						  tTemptInsuYear = "-1";
					  }
					  
					  if(!tPayYears.equals(tInsuYear)
							  ||Integer.parseInt(tTemptInsuYear)==1
							  ||Integer.parseInt(tTemptPayYears)==1
					  )
					  {
						  tRes="交费期间录入错误";
					  }
				  }
			  }
		  }
		  //tongmeng 2009-03-12 add
		  //112213
		  else if(tRiskCode.equals("112213"))
		  {
			  //10Y、15Y、20Y、1000A(趸交)
			  if(!tPayYears.equals("10Y")
					  ||!tPayYears.equals("10年")
					  ||!tPayYears.equals("15Y")
					  ||!tPayYears.equals("15年")
					  ||!tPayYears.equals("20Y")
					  ||!tPayYears.equals("20年")
					  ||!tPayYears.equals("1000A")
			  )
			  {
				  tRes="交费期间录入错误";
			  }
		  }
		  else if(tRiskCode.equals("121305")
				  ||tRiskCode.equals("121505"))
		  {
			  //5Y、10Y、15Y、20Y、1000A(趸交)
			  if(!tPayYears.equals("10Y")
					  ||!tPayYears.equals("10年")
					  ||!tPayYears.equals("15Y")
					  ||!tPayYears.equals("15年")
					  ||!tPayYears.equals("20Y")
					  ||!tPayYears.equals("20年")
					  ||!tPayYears.equals("1000A")
					  ||!tPayYears.equals("5Y")
					  ||!tPayYears.equals("5年")
			  )
			  {
				  tRes="交费期间录入错误";
			  }
			  
		  }
	  }*/

	  return tRes;
  }
  /**
   * 校验受益人的受益级别和每个受益级别的受益比例和
   * 
   * */
  public String checkBnfGradeAndBnfLot(){
	  String tResult="";
	  //将所有受益人信息查询出来
	  String tInputNo = (String)this.mTransferData.getValueByName("LBPOBnf.InputNo");
	  String tInsuredNo = (String)this.mTransferData.getValueByName("LBPOBnf.InsuredNo");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOBnf.ContNo");
	  String tempBnfGrade = (String)this.mTransferData.getValueByName("LBPOBnf.BnfGrade");
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&WITHOUT:?^？"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tempBnfGrade,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  
	  }
	  else
	  {
		  String tRule = "|notnull&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tempBnfGrade,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  
	  
	  String tBnfGradeSQL="select * from LBPOBnf where contno='"+"?contno?"+"' and inputno='"+"?inputno?"+"' and insuredno='"+"?insuredno?"+"' order by bnfgrade ";
	  SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
	  sqlbv33.sql(tBnfGradeSQL);
	  sqlbv33.put("contno", tContNo);
	  sqlbv33.put("inputno", tInputNo);
	  sqlbv33.put("insuredno", tInsuredNo);
	  String tBnfLotSQL="select bnfgrade,bnflot from LBPOBnf where contno='"+"?contno?"+"' and inputno='"+"?inputno?"+"' and insuredno='"+"?insuredno?"+"' order by bnfgrade ";
	  SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
	  sqlbv34.sql(tBnfLotSQL);
	  sqlbv34.put("contno", tContNo);
	  sqlbv34.put("inputno", tInputNo);
	  sqlbv34.put("insuredno", tInsuredNo);
	  LBPOBnfDB tLBPOBnfDB=new LBPOBnfDB();
	  LBPOBnfSet tLBPOBnfSet=new LBPOBnfSet();
	  
	  LBPOBnfSchema tLBPOBnfSchema=new LBPOBnfSchema();
	  tLBPOBnfSet = tLBPOBnfDB.executeQuery(sqlbv33);
	  boolean tGrade;
	  SSRS tSSRS=new SSRS();
	  ExeSQL tExeSQL = new ExeSQL();
	  tSSRS = tExeSQL.execSQL(sqlbv34);
	  //移除为空的受益信息
	  int BnfSize = tLBPOBnfSet.size();
	  for(int n=1;n<=BnfSize;n++){
		  tLBPOBnfSchema =new LBPOBnfSchema();
		  tLBPOBnfSchema = tLBPOBnfSet.get(n);
		  if(mDSPubCheckCol.ifRemove(tLBPOBnfSchema, "03")){
			  tLBPOBnfSet.remove(tLBPOBnfSchema);
			  BnfSize--;
			  n--;
			}else{
				//如果有信息，看是否某些字段中为'法定' 如果是法定 则按空处理，移除
				for(int i=0;i<tLBPOBnfSchema.getFieldCount();i++){
//					logger.debug("字段名称："+tLBPOBnfSchema.getFieldName(i));
//					logger.debug("字段值："+tLBPOBnfSchema.getV(i));
					if("法定".equals(tLBPOBnfSchema.getV(i))){
						//如果某个字段为‘法定’视为空！
						tLBPOBnfSet.remove(tLBPOBnfSchema);
						BnfSize--;
						n--;
						break;
					}
				}
			}
	  }
	  String tBeforeBnfGrade="";//前一个受益人的收益顺序
	  for(int i=1;i<=tLBPOBnfSet.size();i++){
		  double cBnfLot=0;
		  double addbnf=0;
		  tLBPOBnfSchema =new LBPOBnfSchema();
		  tLBPOBnfSchema = tLBPOBnfSet.get(i);
//		  if(mDSPubCheckCol.ifRemove(tLBPOBnfSchema, "03")){
//			  //为空
//			  continue;
//			}else{
		  
				//非空
				String tBnfGrade = tLBPOBnfSchema.getBnfGrade();				
				if(tPolType.equals("8635"))
				{
					if(tBnfGrade==null)
					{
						tBnfGrade = "1";
					}
				}
				
				if(tBnfGrade==null) break;
				try{
					if(i==1)
					{
						if(i!=Integer.parseInt(tBnfGrade)){
							tResult="受益人受益顺序不连续;";
						}
					}						
					else
					{
						if((Integer.parseInt(tBnfGrade)==Integer.parseInt(tBeforeBnfGrade))
								||(Integer.parseInt(tBnfGrade)==Integer.parseInt(tBeforeBnfGrade)+1))
						{}
						else
							tResult="受益人受益顺序不连续;";
					}
							
				}catch(Exception ex){
					tResult="受益人受益顺序不合法";
					break;
				}
				tBeforeBnfGrade = tLBPOBnfSchema.getBnfGrade();
				
				int tCount = tSSRS.MaxRow;
				for(int j=1;j<=tCount;j++){
					String tNowBnfGrade =tSSRS.GetText(j, 1);
					if(tPolType.equals("8635"))
					{
						if(tNowBnfGrade==null||"null".equals(tNowBnfGrade)||"".equals(tNowBnfGrade)){
							tNowBnfGrade="1";
						}
						
					}
					if(tBnfGrade.equals(tNowBnfGrade)){
						String tBnfLot = tSSRS.GetText(j, 2);
//						if(tCount==1&&(tBnfLot==null||"".equals(tBnfLot))){
//							//如果受益人为一人，且收益比例为空 怎按‘1’ 导入 无须给出问题件
//							return tResult;
//						}
						try{
							if(tBnfLot.indexOf("%")!=-1){
								String rBnfLot = StrTool.replace(tBnfLot, "%", "");
								cBnfLot =Double.parseDouble(rBnfLot)/100;
							}else{
								cBnfLot =Double.parseDouble(tBnfLot);
							}
						}catch(Exception ex){
							tResult=tResult+"第"+tBnfGrade+"受益人的受益比例录入不合法;";
							break;
						}
						addbnf=addbnf+cBnfLot;
					}
				}
				if(addbnf!=1){
					tResult=tResult+"第"+tBnfGrade+"受益顺序的受益比例和不为‘1’;";
					break;
				}
//			}
	  }
	
	  return tResult;
  }
  
  /**
   * 校验险种录入规则
   * @return
   */
  public String checkPolRiskRule()
  {
	  String tResult = "";
	  String tRiskCode = (String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
//	  String tAmnt = (String)this.mTransferData.getValueByName("LBPOPol.Amnt");
//	  String tInsureYear = 
	  /*
	  if(tRiskCode.equals("112203"))
	  {
//		  if(tAmnt==null||tAmnt.indexOf("元")==-1)
//		  {
//			  tResult = "保额录入错误";
//		  }
		  
	  }*/

	  return tResult;
  }
  /**
   * 年金领取方式
   * @return
   */
  public String checkLiveGetMode()
  {
	  String tRes = "";
	  //目前投保单没有录生存金的地方并且年金的险种只有一个并且年金领取方式的校验已经放到险种规则里面
	  //在与客户确认后注释掉生存金/年金领取方式的校验
//	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":
//		  (String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
//	  String tLiveGetMode = this.mTransferData.getValueByName("LBPOPol.LiveGetMode")==null?"":
//		  (String)this.mTransferData.getValueByName("LBPOPol.LiveGetMode");
//	  String tContNo = (String)this.mTransferData.getValueByName("LBPOPol.ContNo");
//
//	  if(tLiveGetMode.equals(""))
//	  {
//		  return "";
//	  }
//
//	  
//	  String tPolType = tContNo.substring(0,4);
//	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
//	  if(tPolType.equals("8611")||tPolType.equals("8621"))
//	  {
//		  String tRule = "|notnull&code:livegetmode&WITHOUT:?^？^4^其他"; 
//		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tLiveGetMode,tRule,null,"0");
//		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
//		  {
//			  if(tErrorInfo.indexOf("(")!=-1)
//			  {
//				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
//			  }
//			  return tErrorInfo;
//		  }
//	  }
//	  else
//	  {
//		  String tRule = "|notnull&code:livegetmode&WITHOUT:?^？^4^其他#null"; 
//		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tLiveGetMode,tRule,null,"0");
//		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
//		  {
//			  if(tErrorInfo.indexOf("(")!=-1)
//			  {
//				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
//			  }
//			  return tErrorInfo;
//		  }
//	  }
	  
	  /*
	 
	  if(tRiskCode.equals("112203")
		||tRiskCode.equals("112202")
		||tRiskCode.equals("112201")
		||tRiskCode.equals("112206")
	  )
	  {
		  if(tLiveGetMode.equals("3"))
		  {
			  tRes = "生存保险金/年金领取方式 错误 ";
		  }
	  }
	  else if(tRiskCode.equals("112401")
			  ||tRiskCode.equals("112207")
			  )
	  {
		  if(!tLiveGetMode.equals("1"))
		  {
			  tRes = "生存保险金/年金领取方式 错误 ";
		  }
	  }
	  else if(tRiskCode.equals("112208"))
	  {
		  if(!tLiveGetMode.equals("2")&&!tLiveGetMode.equals(""))
		  {
			  tRes = "生存保险金/年金领取方式 错误 ";
		  }
	  }
	  else if(tRiskCode.equals("112212"))
	  {
		  if(tLiveGetMode.equals("4")
				  ||tLiveGetMode.indexOf(",")!=-1
				  ||tLiveGetMode.equals("3"))
		  {
			  tRes = "生存保险金/年金领取方式 错误 ";
		  }
	  }
	  else if(tRiskCode.equals("121305"))
	  {
		  tRes = "";
	  }*/
	  return tRes;
  }
  /**
   * 红利领取方式
   * @return
   */
  public String checkBonusGetMode()
  {
	  String tRes = "";
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":
		  (String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  String tBonusGetMode = this.mTransferData.getValueByName("LBPOPol.BonusGetMode")==null?"":
		  (String)this.mTransferData.getValueByName("LBPOPol.BonusGetMode");
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOPol.ContNo");


	  if(tBonusGetMode.equals(""))
	  {
		  return "";
	  }
	  
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&code:bonusgetmode&WITHOUT:?^？^4^其他"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tBonusGetMode,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&code:bonusgetmode&WITHOUT:?^？^4^其他#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tBonusGetMode,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  
	  
	  /*
	  if(tRiskCode.equals("112203")
		||tRiskCode.equals("112101")
		||tRiskCode.equals("112202")
		||tRiskCode.equals("112201")
		||tRiskCode.equals("112206")
		||tRiskCode.equals("112208")
	  )
	  {
		  if(!tBonusGetMode.equals("")
				  ||!tBonusGetMode.equals("1")
				  ||!tBonusGetMode.equals("2")
				  ||!tBonusGetMode.equals("3")
				  //||tBonusGetMode.equals("4")
				  )
		  {
			  tRes = "红利领取方式 错误 ";
		  }
	  }
	  else if(tRiskCode.equals("112401")
			  ||tRiskCode.equals("112204")
			   ||tRiskCode.equals("112213")
			   ||tRiskCode.equals("112212")
			  )
	  {
		  if(!tBonusGetMode.equals("5"))
		  {
			  tRes = "红利领取方式 错误 ";
		  }
	  }
	  else if(tRiskCode.equals("112207")
	  )
	  {
		  if(!tBonusGetMode.equals("1"))
		  {
			  tRes = "红利领取方式 错误 ";
		  }
	  }
	  */
	  return tRes;
  }
  
  /**
   * 校验险种保额
   * @return
   */
  public String checkPolAmnt()
  {
	  String tResult = "";
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  String tAmnt = this.mTransferData.getValueByName("LBPOPol.Amnt")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.Amnt");
	  String tContNo = this.mTransferData.getValueByName("LBPOPol.ContNo")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.ContNo");
	  
	  String tMult= this.mTransferData.getValueByName("LBPOPol.Mult")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.Mult");
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&Num&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAmnt,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "notnull&Num&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tAmnt,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  if(tMult==null||tMult.equals("")||tMult.equals("null"))
		  {
			 if(tAmnt==null||tAmnt.equals("")||tAmnt.equals("null"))
			 {
				 tErrorInfo = "份数与保额同时为空";
				 return tErrorInfo;
			 }
		  }
	  }
	  
	  //	  String tInsureYear = 
	  /*
	  if(tRiskCode.equals("112203")
			  ||tRiskCode.equals("112101")
			  ||tRiskCode.equals("112202")
			  ||tRiskCode.equals("112201")
			  ||tRiskCode.equals("111301")
			  ||tRiskCode.equals("112401")
			  ||tRiskCode.equals("112206")
			  ||tRiskCode.equals("112207")
			  ||tRiskCode.equals("111504")
			  ||tRiskCode.equals("112208")
			  ||tRiskCode.equals("112204")
			  //短险
			  ||tRiskCode.equals("111601")
			  ||tRiskCode.equals("121801")
			  ||tRiskCode.equals("131602")
	  )
	  {
		  if(tAmnt==null||tAmnt.indexOf("元")==-1)
		  {
			  tResult = "保额录入错误";
		  }  
	  }
	  else if(tRiskCode.equals("121704"))
	  {
		  if(tAmnt.indexOf("天")==-1&&tAmnt.indexOf("日")==-1)
		  {
			  tResult = "保额录入错误";
		  }  
	  }
	  else if(tRiskCode.equals("111602"))
	  {
		  String tSQL = "select remark from lbpocont where contno='"+tContNo+"' "
		  			  + " order by inputno desc ";
	  		ExeSQL tExeSQL = new ExeSQL();
	  		String tRemark = "";
	  		tRemark = tExeSQL.getOneValue(tSQL);
	  		if(tRemark==null)
	  		{	
	  			tRemark = "";
	  		}
	  		if(tRemark.indexOf("计划")==-1)
	  		{
	  			tResult = "计划录入错误";
	  		}
	  }
	  else if(tRiskCode.equals("111801"))
	  {
//		  if(tAmnt.indexOf("天")==-1&&tAmnt.indexOf("日")==-1)
//		  {
//			  tResult = "保额录入错误";
//		  }  
	  }*/
	  return tResult;
  }
  
  /**
   * 年金开始领取方式
   * @return
   */
  public String checkLiveGetYear()
  {
	  String tRes = "";
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  String tGetYear = this.mTransferData.getValueByName("LBPOPol.GetYear")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.GetYear");
	  if(tRiskCode.equals("112401"))
	  {
		  //50-50岁、55-55岁、60-60岁、65-65岁
		  if(tGetYear.indexOf("50")==-1
				  &&tGetYear.indexOf("50岁")==-1
				  &&tGetYear.indexOf("55")==-1
				  &&tGetYear.indexOf("55岁")==-1
				  &&tGetYear.indexOf("60")==-1
				  &&tGetYear.indexOf("60岁")==-1
				  &&tGetYear.indexOf("65")==-1
				  &&tGetYear.indexOf("65岁")==-1
				  )
		  {
			  tRes = "年金开始领取年龄错误";
		  }
	  }
	  return tRes;
  }
  /**
   * 校验年金领取期限
   * @return
   */
  public String checkGetLimit()
  {
	  String tRes = "";
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  String tGetLimit= this.mTransferData.getValueByName("LBPOPol.GetLimit")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.GetLimit");
	/*
	  if(tRiskCode.equals("112401"))
	  {
		  if(tGetLimit.indexOf("1")==-1&&tGetLimit.indexOf("2")==-1)
		  {
			  tRes = "年金领取方式错误";
		  }
	  }
	  */
	  return tRes;
  }
  
  public String checkPolMult()
  {
	  String tRes = "";
	  //tongmeng 2009-03-13 add
	  //按照投保单类型校验
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOPol.ContNo");
	  String tRiskCode = this.mTransferData.getValueByName("LBPOPol.RiskCode")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.RiskCode");
	  String tMult= this.mTransferData.getValueByName("LBPOPol.Mult")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.Mult");
	  String tAmnt = this.mTransferData.getValueByName("LBPOPol.Amnt")==null?"":(String)this.mTransferData.getValueByName("LBPOPol.Amnt");
	  String tPolType = tContNo.substring(0,4);
	  DSPubCheckCol tDSPubCheckCol = new DSPubCheckCol();
	  if(tPolType.equals("8611")||tPolType.equals("8621"))
	  {
		  String tRule = "|notnull&Num&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tMult,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
	  }
	  else
	  {
		  String tRule = "|notnull&Num&WITHOUT:?^？#null"; 
		  String tErrorInfo = tDSPubCheckCol.checkValueByRule(tMult,tRule,null,"0");
		  if(tErrorInfo!=null&&!tErrorInfo.equals(""))
		  {
			  if(tErrorInfo.indexOf("(")!=-1)
			  {
				  tErrorInfo = tErrorInfo.substring(tErrorInfo.indexOf("(")+1,tErrorInfo.lastIndexOf(")"));
			  }
			  return tErrorInfo;
		  }
		  if(tMult==null||tMult.equals("")||tMult.equals("null"))
		  {
			 if(tAmnt==null||tAmnt.equals("")||tAmnt.equals("null"))
			 {
				 tErrorInfo = "份数与保额同时为空";
				 return tErrorInfo;
			 }
		  }
	  }
	  /*
		  if(tRiskCode.equals("141802"))
		  {
			  //需要过滤录入的数据.
			  tMult = tMult.replaceAll("[^0-9]", "");
			  if(tMult.equals("")||Integer.parseInt(tMult)!=1)
			  {
				  tRes = "险种份数录入错误";
			  }
		  }
		  else if(tRiskCode.equals("141602")
				  ||tRiskCode.equals("141811")  
	  			)
		  {
			  tMult = tMult.replaceAll("[^0-9]", "");
		  	if(tMult.equals("")||Integer.parseInt(tMult)<1||Integer.parseInt(tMult)>5)
		  	{
		  		tRes = "险种份数录入错误";
		  	}
		  }
		  else if(tRiskCode.equals("141803"))
		  {
			  tMult = tMult.replaceAll("[^0-9]", "");
			  if(tMult.equals("")||Integer.parseInt(tMult)<1||Integer.parseInt(tMult)>4)
			  {
			  tRes = "险种份数录入错误";
			  }
		  }
		  else if(tRiskCode.equals("112213")
			||tRiskCode.equals("112212")  
	  			)
		  {
			  if(tMult!=null&&!tMult.equals("")&&!tMult.equals("null"))
			  {
				  tRes = "险种份数录入错误";
			  }

		  }
	  */
	  return tRes;
  }
  /*---------------------------------------------------------------------------*/
  /**
   * 参考算法
   * @return 返回的是错误信息
   */
  public String checkJavaTest()
  {
	  String tContNo = (String)this.mTransferData.getValueByName("LBPOCont.ManageCom");
	  
	  logger.debug("tContNo:"+tContNo);
	  return tContNo;
  }
  public static void main(String[] args) {
	  
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("LBPOInsured.Sex", "0");
	  tTransferData.setNameAndValue("LBPOInsured.IDType", "0");
	  tTransferData.setNameAndValue("LBPOInsured.IDNo", "230204820121113");
	  VData tVData = new VData();
	  tVData.add(tTransferData);
	  DSCheckInfo tDSCheckInfo = new DSCheckInfo(tVData);
	  tDSCheckInfo.checkInsuredSexAndIdNo();
  }
}
