package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Blob;
import java.sql.Connection;
import java.util.List;

import org.jdom.Element;
import org.jdom.input.DOMBuilder;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.BPOMissionDetailErrorSchema;
import com.sinosoft.lis.schema.BPOMissionStateSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.BPOMissionDetailErrorSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: TBXMLTransfer</p>
 * <p>Description: 将XML转换为保单表对应的VData相关数据，支持多主险，多被保人</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author ln
 * @version 1.0
 */

public class TBXMLTransfer
{
private static Logger logger = Logger.getLogger(TBXMLTransfer.class);

  public CErrors mErrors = new CErrors();
  /**错误信息数据项*/
  private static final String XML_BASEINFO = "BaseInfo";
  private static final String XML_SUCCFLAG = "SuccFlag";
  private static final String XML_ERRORS = "Errors";
  private static final String XML_ERROR = "Error";
  private static final String XML_ERRTYPE = "ErrType"; //NEW
  private static final String XML_ERRTAG = "ErrTag";
  private static final String XML_ERRCODE = "ErrCode";
  private static final String XML_ERRMESSAGE = "ErrMessage";

  /**保单信息数据项*/
  private static final String XML_LCPOL = "LCPol";
  private static final String XML_PRTNO = "PrtNo";
  private static final String XML_MANAGECOM = "ManageCom";
  private static final String XML_SALECHNL = "SaleChnl";
  private static final String XML_AGENTCODE = "AgentCode";
  private static final String XML_AGENTNAME = "AgentName";
  private static final String XML_AGENTGROUP = "AgentGroup";
  private static final String XML_AGENTCOM = "AgentCom";
  private static final String XML_REMARK = "Remark";
  private static final String XML_POLAPPLYDATE = "PolApplyDate";
  private static final String XML_PAYMODE = "PayMode";
  private static final String XML_PAYLOCATION = "PayLocation";
  private static final String XML_BANKCODE = "BankCode";
  private static final String XML_CHIEFBANKCODE = "ChiefBankCode";//???????????????????
  private static final String XML_ACCNAME = "AccName";
  private static final String XML_BANKACCNO = "BankAccNo";
  private static final String XML_RENBANKCODE = "ReNBankCode";//new
  private static final String XML_RENACCNAME = "ReNAccName";//new
  private static final String XML_RENBANKACCNO = "ReNBankAccNo";//new
  private static final String XML_OUTPAYFLAG = "OutPayFlag";
  private static final String XML_GETPOLMODE = "GetPolMode";
  private static final String XML_FLOATRATE = "FloatRate";
  private static final String XML_PAYINTV = "PayIntv";
  private static final String XML_AUTOPAYFLAG = "AutoPayFlag";
  private static final String XML_RNEWFLAG = "RnewFlag";
  private static final String XML_SPEC = "Spec";//new 特约信息
  private static final String XML_SIGNNAME = "SignName";//new 初审员签名 2009-2-9 ADD
  private static final String XML_FIRSTTRIALDATE = "FirstTrialDate";//new 初审日期 2010-03-18 ADD
  private static final String XML_XQREMINDFLAG = "XQremindflag";//new 初审日期 2010-04-14 ADD
  
  /**投保人信息*/
  private static final String XML_LCAPPNT = "LCAppnt";
  private static final String XML_APPNTNAME = "AppntName";
  private static final String XML_APPNTSEX = "AppntSex";
  private static final String XML_APPNTBIRTHDAY = "AppntBirthday";
  private static final String XML_APPNTRELATIONINSURED = "AppntRelationToInsured";
  private static final String XML_APPNTIDTYPE = "AppntIDType";
  private static final String XML_APPNTIDNO = "AppntIDNo";
  private static final String XML_APPNTNATIVEPLACE = "AppntNativePlace";
  private static final String XML_APPNTRGTADDRESS = "AppntRgtAddress";
  private static final String XML_APPNTMARRIAGE = "AppntMarriage";
  private static final String XML_APPNTPOSTALADDRESS = "AppntPostalAddress";
  private static final String XML_APPNTZIPCODE = "AppntZipCode";
  private static final String XML_APPNTHOMEADDRESS = "AppntHomeAddress";
  private static final String XML_APPNTHOMEZIPCODE = "AppntHomeZipCode";
  private static final String XML_APPNTPHONE = "AppntPhone";
  private static final String XML_APPNTPHONE2 = "AppntPhone2";
  private static final String XML_APPNTEMAIL = "AppntEMail";
  private static final String XML_APPNTGRPNAME = "AppntGrpName";
  private static final String XML_APPNTSOCIALINSUFLAG = "AppntSocialInsuFlag";
  private static final String XML_APPNTIDENDDATE = "AppntIDEndDate";
  private static final String XML_APPNTWORKTYPE = "AppntWorkType";   //职业类别
  private static final String XML_APPNTPLURALITYTYPE = "AppntPluralityType";   //兼职
  private static final String XML_APPNTOCCUPATIONCODE = "AppntOccupationCode";

  /**告知信息列表*/
  private static final String XML_LCCUSTOMERIMPARTSET = "LCCustomerImpartSet";
  private static final String XML_LCCUSTOMERIMPART =  "LCCustomerImpart";
  private static final String XML_IMPARTFLAG = "ImpartFlag";
  private static final String XML_IMPARTVER =  "ImpartVer";
  private static final String XML_IMPARTCODE = "ImpartCode";
  private static final String XML_IMPARTCONTENT =  "ImpartContent";
  private static final String XML_CUSTOMERNOTYPE =  "CustomerNoType";
  private static final String XML_CUSTOMERTOINSURED =  "CustomerToInsured";

  /**被保险人信息列表*/
  private static final String XML_LCINSUREDS = "LCInsureds";
  private static final String XML_LCINSURED = "LCInsured";
  private static final String XML_INSUREDNO = "InsuredNo";//new
  private static final String XML_LCINSUREDDES = "LCInsuredDes";//new
  private static final String XML_RELATIONTOMAININSURED = "RelationToMainInsured";//new
  private static final String XML_NAME = "Name";
  private static final String XML_SEX = "Sex";
  private static final String XML_BIRTHDAY = "Birthday";
  private static final String XML_RELATIONTOAPPNT = "RelationToAppnt";//new
  private static final String XML_IDTYPE = "IDType";
  private static final String XML_IDNO = "IDNo";
  private static final String XML_IDENDDATE = "IDEndDate";
  private static final String XML_NATIVEPLACE = "NativePlace";
  private static final String XML_RGTADDRESS = "RgtAddress";
  private static final String XML_MARRIAGE = "Marriage";
  private static final String XML_HOMEADDRESS = "HomeAddress";
  private static final String XML_HOMEZIPCODE = "HomeZipCode";
  private static final String XML_PHONE = "Phone";
  private static final String XML_PHONE2 = "Phone2";
  private static final String XML_EMAIL = "EMail";
  private static final String XML_GRPNAME = "GrpName";
  private static final String XML_SOCIALINSUFLAG = "SocialInsuFlag";
  private static final String XML_WORKTYPE = "WorkType";   //职业类别
  private static final String XML_PLURALITYTYPE = "PluralityType";   //兼职
  private static final String XML_OCCUPATIONCODE = "OccupationCode";

  /**生存保险金、年金、红利处理方式列表*/
  private static final String XML_LCDEALTYPESET = "LCDealTypeSet";//new
  private static final String XML_LCDEALTYPE = "LCDealType";//new
  private static final String XML_TYPETORISK = "TypeToRisk";//new
  private static final String XML_GETYEARFLAG = "GetYearFlag";    //领取(起领)年龄年期标志 1字节  Y-年,A-岁
  private static final String XML_GETYEAR = "GetYear";            //领取(起领)年龄
  private static final String XML_GETYEARS = "GetYears";          //领取期间
  private static final String XML_GETDUTYKIND = "GetDutyKind";
  private static final String XML_LIVEGETMODE = "LiveGetMode";
  private static final String XML_BONUSGETMODE = "BonusGetMode";
  
  /**投资账户信息列表*/
  private static final String XML_LCINVEST = "LCInvest";//
  private static final String XML_INVESTFLAG = "InvestFlag";//
  private static final String XML_INVESTSET = "InvestSet";//
  private static final String XML_EFFTIME = "EffTime";    //
  private static final String XML_INVEST = "Invest";            //
  private static final String XML_ACCTORISK = "AccToRisk";          //
  private static final String XML_ACCNO = "AccNo";
  private static final String XML_ACCLOT = "AccLot";
  private static final String XML_ACCMONEY = "AccMoney";
  
  /**万能险/投连险追加费信息列表*/
  private static final String XML_LCADDIT = "LCAddit";//new
  private static final String XML_ADDITFLAG = "AdditFlag";//
  private static final String XML_ADDITSET = "AdditSet";//
  private static final String XML_ADDIT = "Addit";    //
  private static final String XML_ADDITTORISK = "AdditToRisk";            
  private static final String XML_ADDITPREM = "AdditPrem";         
  private static final String XML_ADDITAMNT = "AdditAmnt";
  private static final String XML_ADDAMNT = "AddAmnt";
  
  /**受益人信息列表*/
  private static final String XML_LCBNFS = "LCBnfs";
  private static final String XML_LCBNF = "LCBnf";
  private static final String XML_BNFTORISK = "BnfToRisk"; //NEW
  private static final String XML_BNFTYPE = "BnfType";
  private static final String XML_RELATIONTOINSURED = "RelationToInsured";
  private static final String XML_BNFLOT = "BnfLot";   //受益比例
  private static final String XML_BNFGRADE = "BnfGrade";
  private static final String XML_ADDRESS = "Address";
  private static final String XML_IDEXPDATE = "BnfIDEndDate";

  /**险种信息列表*/
  private static final String XML_LCRISKS = "LCRisks";//new
  private static final String XML_RISKS = "Risks";
  private static final String XML_RISKNO = "RiskNo";//new
  private static final String XML_RISK = "Risk";
  private static final String XML_RISKCODE = "RiskCode";
  private static final String XML_RISKNAME = "RiskName";
  private static final String XML_PREM = "Prem";
  private static final String XML_ADDPREM = "AddPrem";//职业加费
  private static final String XML_MULT = "Mult";
  private static final String XML_AMNT = "Amnt";
  private static final String XML_LEVEL = "Level";
  private static final String XML_PAYENDYEAR = "PayEndYear";
  private static final String XML_INSUYEAR = "InsuYear";

  public TBXMLTransfer()
  {
  }

  /**
   * 根据印刷号返回一个保单的数据集合
   * @param tBussNo
   * @return
   */
  public VData getOnePolData(String tBussNo)
  {
    VData tVData = new VData();
    Connection conn = DBConnPool.getConnection();
    COracleBlob blob = new COracleBlob();
    CMySQLBlob tCMySQLBlob = new CMySQLBlob();
    DOMBuilder domBuilder = new DOMBuilder();
    Element tOnePolData ;
    String tSQL = " and BussNo='"+tBussNo+"' and BussNoType='TB'";
    Blob tBlob = null;
    if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	tBlob = blob.SelectBlob("BPOPolData", "PolData", tSQL, conn);
    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    	tBlob = tCMySQLBlob.SelectBlob("BPOPolData", "PolData", tSQL, conn);	
    }
    try {
      tOnePolData = domBuilder.build(tBlob.getBinaryStream()).getRootElement();
      tVData = XMLToVData(tOnePolData);
      conn.close();
    }
    catch (Exception ex) {
    	ex.printStackTrace();
      try
         {
           conn.close();
        } catch(Exception ex1){}
    }
    return tVData;
  }
  /**
   *
   * @param tOnePolData
   * @return
   */
  public VData XMLToVData(Element tOnePolData)
  {
    String tSuccFlag = ""; //0-正常件, 1-外包方可处理的异常件（如扫描件上有一两处无法识别），2-外包方无法录入的异常件（如整个扫描件无法识别）
    int tRiskSCount = 0;  //多主险种数量
    int tRiskCount = 0;  //险种数量
    String tRiskIndex = "";
    VData tResult = new VData();
    try
    {
      LCPolSchema tLCPolSchema = new LCPolSchema();
      LCPolSchema tLCPolSchema1 = new LCPolSchema();
      LCPolSet tLCPolSet1 = new LCPolSet();
      LCAppntIndSchema tLCAppntIndSchema = new LCAppntIndSchema();
      LCInsuredSet tLCInsuredSet = new LCInsuredSet();
      LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
      LCBnfSet tLCBnfSet = new LCBnfSet();
      LCBnfSchema tLCBnfSchema = new LCBnfSchema();
      LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
      LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
      TransferData tTransferData = new TransferData();
      TransferData tTransferData1 = new TransferData(); //被保人下校验信息
      Reflections tReflections = new Reflections();
      //任务处理状态主表和任务处理错误信息表
      BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
      BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = new BPOMissionDetailErrorSet();
      BPOMissionDetailErrorSchema tBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();

      RiskBasicInfo tRiskBasicInfo = new RiskBasicInfo();//单个险种信息
      VData tRiskBasicInfoSet = new VData();   //被保人下险种信息集合  
      VData tRiskBasicInfoMainSet = new VData();   //被保人下主险信息集合
      VData tTransferDataSet = new VData();   //被保人下校验信息集合1
      VData tTransferDataSet1 = new VData();   //被保人下校验信息集合2
      VData tInsuredRelaSet = new VData();   //被保人下所有信息集合
	  VData InsuredResults = new VData(); //所有被保人信息集合

      Element BaseInfo = tOnePolData.getChild(XML_BASEINFO);
      Element LCPol = tOnePolData.getChild(XML_LCPOL);
      Element LCAppnt = tOnePolData.getChild(XML_LCAPPNT);
      Element LCCustomerImpartSet = tOnePolData.getChild(XML_LCCUSTOMERIMPARTSET);
      Element LCInsureds = tOnePolData.getChild(XML_LCINSUREDS);

      String tsamePersonFlag = "";//投保人与被保人是否同一人
      tSuccFlag = StrTool.cTrim(BaseInfo.getChildText(XML_SUCCFLAG));
      //如果外包方返回的数据错误则返回为空
      if(tSuccFlag ==null || "".equals(tSuccFlag)||(!"0".equals(tSuccFlag) && !"1".equals(tSuccFlag) &&!"2".equals(tSuccFlag)))
      {
        logger.debug("外包方返回的异常件标志SuccFlag不正确："+tSuccFlag);
        CError.buildErr(this, "外包方返回的异常件标志SuccFlag不正确："+tSuccFlag);

        return null;
      }

      //处理保单表
      tLCPolSchema.setPrtNo(StrTool.cTrim(LCPol.getChildText(XML_PRTNO)));
      if("".equals(StrTool.cTrim(LCPol.getChildText(XML_MANAGECOM))))
      {
        ExeSQL tExeSQL = new ExeSQL();
        String tSQL = "";
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        tSQL = "select Managecom from ES_Doc_Main where DocCode='"+"?DocCode?"+"' and subtype='UA001' and rownum=1";
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
        	tSQL = "select Managecom from ES_Doc_Main where DocCode='"+"?DocCode?"+"' and subtype='UA001' limit 0,1";	
        }
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    	sqlbv2.sql(tSQL);
    	sqlbv2.put("DocCode",tLCPolSchema.getPrtNo());
        tLCPolSchema.setManageCom(StrTool.cTrim(tExeSQL.getOneValue(sqlbv2)));
      }
      else
      {
        tLCPolSchema.setManageCom(StrTool.cTrim(LCPol.getChildText(XML_MANAGECOM)));
      }
      logger.debug("*** Salechnl : "+StrTool.cTrim(LCPol.getChildText(XML_SALECHNL)));
      if(StrTool.cTrim(LCPol.getChildText(XML_SALECHNL)).length()<=1)
      {
        tLCPolSchema.setSaleChnl("0"+StrTool.cTrim(LCPol.getChildText(XML_SALECHNL)));
      }
      else
      {
        tLCPolSchema.setSaleChnl(StrTool.cTrim(LCPol.getChildText(XML_SALECHNL)));
      }
      tLCPolSchema.setAgentCode(StrTool.cTrim(LCPol.getChildText(XML_AGENTCODE)));
      tLCPolSchema.setAgentGroup(StrTool.cTrim(LCPol.getChildText(XML_AGENTGROUP)));
      tLCPolSchema.setAgentCom(StrTool.cTrim(LCPol.getChildText(XML_AGENTCOM)));
      tLCPolSchema.setRemark(StrTool.cTrim(LCPol.getChildText(XML_REMARK)));
      tLCPolSchema.setPolApplyDate(StrTool.cTrim(LCPol.getChildText(XML_POLAPPLYDATE)));
      tLCPolSchema.setCValiDate(StrTool.cTrim(LCPol.getChildText(XML_POLAPPLYDATE)));

      tTransferData.setNameAndValue("NewPayMode", StrTool.cTrim(LCPol.getChildText(XML_PAYMODE)));
      String tNewBankCode = StrTool.cTrim(LCPol.getChildText(XML_BANKCODE));
      String tNewBankAccNo = StrTool.cTrim(LCPol.getChildText(XML_BANKACCNO));
      String ttNewBankAccNo = StrTool.cTrim(LCPol.getChildText(XML_BANKACCNO));
      if(!tNewBankCode.equals("")&&!tNewBankAccNo.equals(""))
    	  ttNewBankAccNo = PubFun.getBankAccNo(tNewBankCode , tNewBankAccNo);
    //首期账号      
      if(!StrTool.cTrim(LCPol.getChildText(XML_PAYMODE)).equals("") && (StrTool.cTrim(LCPol.getChildText(XML_PAYMODE)).equals("0") 
    		  				||StrTool.cTrim(LCPol.getChildText(XML_PAYMODE)).equals("8")))//如果为银行转账
      {   	  
          tTransferData.setNameAndValue("NewBankCode", StrTool.cTrim(LCPol.getChildText(XML_BANKCODE))); //
          tTransferData.setNameAndValue("NewAccName", StrTool.cTrim(LCPol.getChildText(XML_ACCNAME))); //
          tTransferData.setNameAndValue("NewBankAccNo", ttNewBankAccNo); //
      }
      else if(!StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)).equals("") && (StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)).equals("0")
    		  				||StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)).equals("8")))
      {
    	  tTransferData.setNameAndValue("NewBankCode", ""); //
	      tTransferData.setNameAndValue("NewAccName", ""); //
	      tTransferData.setNameAndValue("NewBankAccNo", ""); //
      }
      else
      {    	  
          tTransferData.setNameAndValue("NewBankCode", StrTool.cTrim(LCPol.getChildText(XML_BANKCODE))); //
          tTransferData.setNameAndValue("NewAccName", StrTool.cTrim(LCPol.getChildText(XML_ACCNAME))); //
          tTransferData.setNameAndValue("NewBankAccNo", ttNewBankAccNo); //
      }
      
      //加入续期账号
      tTransferData.setNameAndValue("PayLocation", StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)));
      if(!StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)).equals("") && (StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)).equals("0")
    		  				||StrTool.cTrim(LCPol.getChildText(XML_PAYLOCATION)).equals("8")))//如果为银行转账
      {
    	  tTransferData.setNameAndValue("BankCode", StrTool.cTrim(LCPol.getChildText(XML_BANKCODE))); //
	      tTransferData.setNameAndValue("AccName", StrTool.cTrim(LCPol.getChildText(XML_ACCNAME))); //
	      tTransferData.setNameAndValue("BankAccNo", ttNewBankAccNo); //
      }
      else
      {
    	  tTransferData.setNameAndValue("BankCode", ""); //
	      tTransferData.setNameAndValue("AccName", ""); //
	      tTransferData.setNameAndValue("BankAccNo", ""); //
      }
      
      tTransferData.setNameAndValue("SignName", StrTool.cTrim(LCPol.getChildText(XML_SIGNNAME))); //
      tTransferData.setNameAndValue("FirstTrialDate", StrTool.cTrim(LCPol.getChildText(XML_FIRSTTRIALDATE))); //初审日期 2010-03-18 ADD
      tTransferData.setNameAndValue("XQremindflag", StrTool.cTrim(LCPol.getChildText(XML_XQREMINDFLAG))); //续期交费提示 2010-04-13 ADD hanbin
      if( !StrTool.cTrim(LCPol.getChildText(XML_RENBANKCODE)).equals("")
    		  && !StrTool.cTrim(LCPol.getChildText(XML_RENACCNAME)).equals("")
    		  && !StrTool.cTrim(LCPol.getChildText(XML_RENBANKACCNO)).equals("")
    		  && !StrTool.cTrim(LCPol.getChildText(XML_BANKCODE)).equals("")
    		  && !StrTool.cTrim(LCPol.getChildText(XML_ACCNAME)).equals("")
    		  && !StrTool.cTrim(LCPol.getChildText(XML_BANKACCNO)).equals("")
    		  && StrTool.cTrim(LCPol.getChildText(XML_RENBANKCODE)).equals(StrTool.cTrim(LCPol.getChildText(XML_BANKCODE)))
    		  && StrTool.cTrim(LCPol.getChildText(XML_RENACCNAME)).equals(StrTool.cTrim(LCPol.getChildText(XML_ACCNAME)))
    		  && StrTool.cTrim(LCPol.getChildText(XML_RENBANKACCNO)).equals(StrTool.cTrim(LCPol.getChildText(XML_BANKACCNO))))
      {
    	  tTransferData.setNameAndValue("sameAccontFlag", "1");
      }
      else
    	  tTransferData.setNameAndValue("sameAccontFlag", "0");
      
      tTransferData.setNameAndValue("OutPayFlag", StrTool.cTrim(LCPol.getChildText(XML_OUTPAYFLAG))); //
      tTransferData.setNameAndValue("GetPolMode", StrTool.cTrim(LCPol.getChildText(XML_GETPOLMODE))); //

      tLCPolSchema.setFloatRate(StrTool.cTrim(LCPol.getChildText(XML_FLOATRATE)));
      try{tLCPolSchema.setPayIntv(StrTool.cTrim(LCPol.getChildText(XML_PAYINTV)));}catch(Exception ex){} //防止格式错误
      //2009-1-21 ln add --自动垫交标志为空则默认为不垫交
      if(StrTool.cTrim(LCPol.getChildText(XML_AUTOPAYFLAG)).equals(""))
    	  tLCPolSchema.setAutoPayFlag("0");
      else
    	  tLCPolSchema.setAutoPayFlag(StrTool.cTrim(LCPol.getChildText(XML_AUTOPAYFLAG)));
      tLCPolSchema.setRnewFlag(StrTool.cTrim(LCPol.getChildText(XML_RNEWFLAG)));

      tTransferData.setNameAndValue(XML_CHIEFBANKCODE,StrTool.cTrim(LCPol.getChildText(XML_CHIEFBANKCODE)));
      tTransferData.setNameAndValue(XML_AGENTNAME,StrTool.cTrim(LCPol.getChildText(XML_AGENTNAME)));      

      //任务处理状态主表
      tBPOMissionStateSchema.setBussNo(tLCPolSchema.getPrtNo());
      tBPOMissionStateSchema.setBussNoType("TB");
      tBPOMissionStateSchema.setState("0");
      //清洁件
      if("0".equals(tSuccFlag))
      {
        tBPOMissionStateSchema.setDealType("00");
      }
      //外包方返回可处理异常件
      if("1".equals(tSuccFlag))
      {
        tBPOMissionStateSchema.setDealType("02");
      }
      //外包方返回不可处理的异常件
      if("2".equals(tSuccFlag))
      {
        tBPOMissionStateSchema.setDealType("03");
      }

      //1-处理异常件的错误信息
      if("1".equals(tSuccFlag) || "2".equals(tSuccFlag))
      {
        Element Errors = BaseInfo.getChild(XML_ERRORS);
        List ErrorList = Errors.getChildren(XML_ERROR);
        String tErrMessage = "";
        String tErrType = "";
        if(ErrorList != null && ErrorList.size()>0)
        {
          for(int i = 0; i<ErrorList.size(); i++)
          {
            Element Error = (Element)ErrorList.get(i);
            tBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();
            tBPOMissionDetailErrorSchema.setBussNo(tLCPolSchema.getPrtNo());
            tBPOMissionDetailErrorSchema.setBussNoType("TB");
            tBPOMissionDetailErrorSchema.setErrorCount(i+1);
            tBPOMissionDetailErrorSchema.setErrorTag(StrTool.cTrim(Error.getChildTextTrim(XML_ERRTAG)));
            tBPOMissionDetailErrorSchema.setErrorCode(StrTool.cTrim(Error.getChildTextTrim(XML_ERRCODE)));
            
            tErrMessage = StrTool.cTrim(Error.getChildTextTrim(XML_ERRMESSAGE));
            tErrType = StrTool.cTrim(Error.getChildTextTrim(XML_ERRTYPE));
            
            if(!"".equals(tErrMessage))
               if("1".equals(tErrType))
            	   tErrMessage = "普通错误："+tErrMessage;
               else if("2".equals(tErrType))
            	   tErrMessage = "重大错误："+tErrMessage;            	   
            tBPOMissionDetailErrorSchema.setErrorContent(tErrMessage);
            
            tBPOMissionDetailErrorSet.add(tBPOMissionDetailErrorSchema);
          }
        }
      }
      
      tResult.add(tBPOMissionStateSchema);
      if(tBPOMissionDetailErrorSet != null && tBPOMissionDetailErrorSet.size()>0)
      {
        tResult.add(tBPOMissionDetailErrorSet);
      }
      
    //----------------print---------------------------------------------------//         
      logger.debug("***BPOMissionStateSchema: "+tBPOMissionStateSchema.encode());
      
      if(tBPOMissionDetailErrorSet != null && tBPOMissionDetailErrorSet.size()>0)
      {         
        logger.debug("***BPOMissionDetailErrorSet: "+tBPOMissionDetailErrorSet.encode());
      }        
      
      //准备清洁件和外包方可处理的异常件的保单数据
      if(!"2".equals(tSuccFlag))
      {
        //投保人信息
        if( LCAppnt != null )
        {
//          logger.debug("***XML_APPNTNAME  :"+StrTool.GBKToUnicode(LCAppnt.getChildText(XML_APPNTNAME)));

          try
          {
            String tAppntName=new String(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTNAME)));
            logger.debug("AppntName: "+tAppntName+"###");
            if (tAppntName.indexOf("?")!=-1){
              logger.debug("AppntName包含乱码");
            }
            tAppntName = TBPubFun.changForBR(tAppntName);
            logger.debug("AppntName: "+tAppntName+"###11");
            tLCAppntIndSchema.setName(tAppntName.trim());
          }catch(Exception ex){}
          tLCAppntIndSchema.setSex(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTSEX)));
          tLCAppntIndSchema.setBirthday(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTBIRTHDAY)));
          tLCAppntIndSchema.setRelationToInsured(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTRELATIONINSURED)));
          
          if(!StrTool.cTrim(LCAppnt.getChildText(XML_APPNTRELATIONINSURED)).equals("")
        		  &&StrTool.cTrim(LCAppnt.getChildText(XML_APPNTRELATIONINSURED)).equals("00"))
          {
        	  tsamePersonFlag = "1"; //投保人同被保人标志
          }  
          
          tLCAppntIndSchema.setIDType(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTIDTYPE)));
          tLCAppntIndSchema.setIDNo(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTIDNO)));
          
          tLCAppntIndSchema.setNativePlace(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTNATIVEPLACE)));
          tLCAppntIndSchema.setRgtAddress(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTRGTADDRESS)));
          tLCAppntIndSchema.setMarriage(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTMARRIAGE)));
          try
          {
            String tPostalAddress=new String(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTPOSTALADDRESS)));
            logger.debug("PostalAddress: "+tPostalAddress);
            if (tPostalAddress.indexOf("?")!=-1){
              logger.debug("PostalAddress包含乱码");
            }
            tLCAppntIndSchema.setPostalAddress(tPostalAddress);
            }catch(Exception ex){}
          tLCAppntIndSchema.setZipCode(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTZIPCODE)));
          tLCAppntIndSchema.setHomeAddress(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTHOMEADDRESS)));
          tLCAppntIndSchema.setHomeZipCode(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTHOMEZIPCODE)));
          tLCAppntIndSchema.setPhone(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTPHONE)));
          tLCAppntIndSchema.setPhone2(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTPHONE2)));
          tLCAppntIndSchema.setEMail(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTEMAIL)));
          tLCAppntIndSchema.setGrpName(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTGRPNAME)));
          tLCAppntIndSchema.setWorkType(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTWORKTYPE)));
          tLCAppntIndSchema.setPluralityType(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTPLURALITYTYPE)));
          tLCAppntIndSchema.setOccupationCode(StrTool.cTrim(LCAppnt.getChildText(XML_APPNTOCCUPATIONCODE)));
        //2009-9-7 ln add --新增两个字段
          tTransferData.setNameAndValue("AppntSocialInsuFlag", StrTool.cTrim(LCAppnt.getChildText(XML_APPNTSOCIALINSUFLAG))); //是否有公费医疗、社会医疗保险
          tTransferData.setNameAndValue("AppntIDEndDate", StrTool.cTrim(LCAppnt.getChildText(XML_APPNTIDENDDATE)));//证件号码有效期
        }
       
        //告知信息
        if( LCCustomerImpartSet != null )
        {
          List LCCustomerImpartList = LCCustomerImpartSet.getChildren(XML_LCCUSTOMERIMPART);
          if( LCCustomerImpartList != null && LCCustomerImpartList.size()>0 )
          {
        	Element LCCustomerImpart;
            for(int i=0 ; i<LCCustomerImpartList.size();i++)
            {
              LCCustomerImpart = (Element) LCCustomerImpartList.get(i);
              tLCCustomerImpartSchema = new LCCustomerImpartSchema();
              logger.debug("***LCCustomerImpart->IMPARTFLAG "+StrTool.cTrim(LCCustomerImpart.getChildText(XML_IMPARTFLAG)));
              if(!"1".equals(StrTool.cTrim(LCCustomerImpart.getChildText(XML_IMPARTFLAG))))
              {
                logger.debug("***无需导入。。。。");
                continue;
              }
              tLCCustomerImpartSchema.setImpartVer(StrTool.cTrim(LCCustomerImpart.getChildText(XML_IMPARTVER)));
              tLCCustomerImpartSchema.setImpartCode(StrTool.cTrim(LCCustomerImpart.getChildText(XML_IMPARTCODE)));
              tLCCustomerImpartSchema.setImpartContent("");
              tLCCustomerImpartSchema.setImpartParamModle(StrTool.cTrim(LCCustomerImpart.getChildText(XML_IMPARTCONTENT)));
              tLCCustomerImpartSchema.setCustomerNoType(StrTool.cTrim(LCCustomerImpart.getChildText(XML_CUSTOMERNOTYPE)));
              tLCCustomerImpartSchema.setCustomerNo(StrTool.cTrim(LCCustomerImpart.getChildText(XML_CUSTOMERTOINSURED)));
              tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
            }
          }
        }           
        
        tResult.add(tTransferData);
        tResult.add(tLCPolSchema);
        tResult.add(tLCAppntIndSchema);
        tResult.add(tLCCustomerImpartSet);
        //----------------print---------------------------------------------------//   
        logger.debug("***LCPolSchema: "+tLCPolSchema.encode());
        logger.debug("***LCAppntIndSchema: "+tLCAppntIndSchema.encode());        
        
        if(tLCCustomerImpartSet !=null )
        {          
          logger.debug("***LCCustomerImpartSet: "+tLCCustomerImpartSet.encode());
        }
      //------------------------------------------------------------------------------//      

        //被保险人相关信息
        if (LCInsureds != null)
        {
            List LCInsuredList = LCInsureds.getChildren(XML_LCINSURED);
            if (LCInsuredList != null && LCInsuredList.size()>0 )
            {
            	Element LCInsured;
        		Element LCInsuredDes ;
        		Element LCDealTypeSet ;
        		Element LCInvest ;
        		Element LCAddit ;
        		Element LCBnfs ;
        		Element LCRisks ;
        		int j = 0;
        		int k = 0;
        		boolean insuFlag = false;
        		boolean insuFlag1 = false;//多被保人标志
        		
            	for (int i = 0; i < LCInsuredList.size(); i++)
                {               		
            		tInsuredRelaSet = new VData();
            		tLCInsuredSet = new LCInsuredSet();
            		tLCPolSet1 = new LCPolSet();
                    tLCBnfSet = new LCBnfSet(); 
                    tRiskBasicInfoMainSet = new VData();
            		tTransferDataSet = new VData();
            		tTransferDataSet1 = new VData();
            		tLCPolSet1 = new LCPolSet();
                    
            		LCInsured = (Element) LCInsuredList.get(i);
            		if(StrTool.cTrim(LCInsured.getChildText(XML_INSUREDNO)).equals(""))
            	    {
            			CError.buildErr(this, "被保人序号为空！");
            			return null;
            	    }
            		else if(StrTool.cTrim(LCInsured.getChildText(XML_INSUREDNO)).equals("-1"))
            	    {
            			insuFlag = true;
            	    }
            		else
            		{
            			insuFlag1 = true;
            		}
            		
            		/*if((insuFlag&&(LCInsuredList.size()>1))
            				||(insuFlag1&&(LCInsuredList.size()<=1)))
            		{
            			CError.buildErr(this, "被保人序号有误！");
            			return null;
            	    }*/                    	
            		
            		List LCInsuredDesList = LCInsured.getChildren(XML_LCINSUREDDES);
            		LCDealTypeSet = (Element) LCInsured.getChild(XML_LCDEALTYPESET);
            		LCInvest = (Element) LCInsured.getChild(XML_LCINVEST);
            		LCAddit = (Element) LCInsured.getChild(XML_LCADDIT);
            		LCBnfs = (Element) LCInsured.getChild(XML_LCBNFS);
            		LCRisks = (Element) LCInsured.getChild(XML_LCRISKS);         		
            		
            		if (LCInsuredDesList != null && LCInsuredDesList.size()>0 )
                    {              			
                    	for (j = 0; j < LCInsuredDesList.size(); j++)
                        {  
                    		tLCInsuredSchema = new LCInsuredSchema();                    		
                    		//tLCInsuredSchema.setInsuredNo(StrTool.cTrim(LCInsured.getChildText(XML_INSUREDNO))); //是否多被保人标志(被保人序号)
                    		tTransferData1 = new TransferData();
                    		LCInsuredDes = (Element) LCInsuredDesList.get(j);
                    		if (LCInsuredDes == null )
                            {
                                //tReflections.transFields(tLCInsuredSchema,tLCAppntIndSchema);
                                //tTransferData1.setNameAndValue("samePersonFlag", "1"); //投保人同被保人标志
                                
                                CError.buildErr(this, "被保人信息为空！");
                     		    return null;
                            }
                    		else
                    		{   
                    			if (!tsamePersonFlag.equals("")
                    					&& "1".equals(tsamePersonFlag) 
                    					&& insuFlag)
	                             {
	                             	 //如果投保人和被保人的关系为本人，则表明投保人和被保人为同一个人--对于普通投保单适用
	                                         tTransferData1.setNameAndValue("samePersonFlag", "1"); //被保人同投保人标志
	                                         tReflections.transFields(tLCInsuredSchema,tLCAppntIndSchema);
	                                         tLCInsuredSchema.setRelationToMainInsured(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOMAININSURED)));
	                                         tLCInsuredSchema.setRelationToAppnt(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOAPPNT)));
	                             }                    			
                    			 if (!"".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOAPPNT))) 
                    					 && "00".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOAPPNT)))
                                         && insuFlag1)
	                             {
	                             	 //如果被保人和投保人的关系为本人，则表明投保人和被保人为同一个人--对于多被保人适用
	                                         tTransferData1.setNameAndValue("samePersonFlag", "1"); //被保人同投保人标志
	                                         tReflections.transFields(tLCInsuredSchema,tLCAppntIndSchema);
	                                         tLCInsuredSchema.setRelationToMainInsured(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOMAININSURED)));
	                                         tLCInsuredSchema.setRelationToAppnt(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOAPPNT)));
	                             }
                    			//如果被保人姓名，性别，出生日期，证件类型和证件号码都为空，则表明投保人和被保人为同一个人
                    			 else if ("".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_NAME))) &&
                                    "".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_SEX))) &&
                                    "".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_BIRTHDAY))) &&
                                    "".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_IDTYPE))) &&
                                    "".equals(StrTool.cTrim(LCInsuredDes.getChildText(XML_IDNO))))
                                {
                                    tTransferData1.setNameAndValue("samePersonFlag", "1"); //被保人同投保人标志
                                    tReflections.transFields(tLCInsuredSchema,tLCAppntIndSchema);
                                    tLCInsuredSchema.setRelationToMainInsured(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOMAININSURED)));
                                    tLCInsuredSchema.setRelationToAppnt(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOAPPNT)));
                                }                               
                                else
                                {
                                    tTransferData1.setNameAndValue("samePersonFlag", "0"); //被保人不同投保人标志
                                    //tLCInsuredSchema.setSequenceNo(String.valueOf(j + 1));

                                    //即使被保险信息为空，也要初始化一个LCInsuredSchema,供后边使用
                                    try
                                    {
                                        String tInsuredName = new String(StrTool.cTrim(LCInsuredDes.getChildText(XML_NAME)));
                                        logger.debug("tInsuredName: " + tInsuredName);
                                        tInsuredName = TBPubFun.changForBR(tInsuredName);
                                        logger.debug("tInsuredName: "+tInsuredName+"###11");
                                        tLCInsuredSchema.setName(tInsuredName.trim());
                                    }
                                    catch (Exception ex)
                                    {}
                                    tLCInsuredSchema.setSex(StrTool.cTrim(LCInsuredDes.getChildText(XML_SEX)));
                                    tLCInsuredSchema.setBirthday(StrTool.cTrim(LCInsuredDes.getChildText(XML_BIRTHDAY)));
                                    tLCInsuredSchema.setRelationToMainInsured(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOMAININSURED)));
                                    tLCInsuredSchema.setRelationToAppnt(StrTool.cTrim(LCInsuredDes.getChildText(XML_RELATIONTOAPPNT)));
                                    tLCInsuredSchema.setIDType(StrTool.cTrim(LCInsuredDes.getChildText(XML_IDTYPE)));
                                    tLCInsuredSchema.setIDNo(StrTool.cTrim(LCInsuredDes.getChildText(XML_IDNO)));
                                    tLCInsuredSchema.setIDExpDate(StrTool.cTrim(LCInsuredDes.getChildText(XML_IDENDDATE)));//证件号码有效期
                                    tLCInsuredSchema.setNativePlace(StrTool.cTrim(LCInsuredDes.getChildText(XML_NATIVEPLACE)));
                                    tLCInsuredSchema.setRgtAddress(StrTool.cTrim(LCInsuredDes.getChildText(XML_NATIVEPLACE)));
                                    tLCInsuredSchema.setNativePlace(StrTool.cTrim(LCInsuredDes.getChildText(XML_NATIVEPLACE)));
                                    tLCInsuredSchema.setRgtAddress(StrTool.cTrim(LCInsuredDes.getChildText(XML_RGTADDRESS)));
                                    tLCInsuredSchema.setMarriage(StrTool.cTrim(LCInsuredDes.getChildText(XML_MARRIAGE)));
                                    tLCInsuredSchema.setSocialInsuFlag(StrTool.cTrim(LCInsuredDes.getChildText(XML_SOCIALINSUFLAG))); //是否有公费医疗、社会医疗保险
                                    
                                    tTransferData1.setNameAndValue("HomeAddress", StrTool.cTrim(LCInsuredDes.getChildText(XML_HOMEADDRESS))); //
                                    tTransferData1.setNameAndValue("HomeZipCode", StrTool.cTrim(LCInsuredDes.getChildText(XML_HOMEZIPCODE))); //
                                    tTransferData1.setNameAndValue("Phone", StrTool.cTrim(LCInsuredDes.getChildText(XML_PHONE))); //
                                    tTransferData1.setNameAndValue("Phone2", StrTool.cTrim(LCInsuredDes.getChildText(XML_PHONE2))); //
                                    tTransferData1.setNameAndValue("EMail", StrTool.cTrim(LCInsuredDes.getChildText(XML_EMAIL)));//被保人电子邮箱
                                    tTransferData1.setNameAndValue("GrpName", StrTool.cTrim(LCInsuredDes.getChildText(XML_GRPNAME))); //                                   

                                    tLCInsuredSchema.setWorkType(StrTool.cTrim(LCInsuredDes.getChildText(XML_WORKTYPE)));
                                    tLCInsuredSchema.setPluralityType(StrTool.cTrim(LCInsuredDes.getChildText(XML_PLURALITYTYPE)));
                                    tLCInsuredSchema.setOccupationCode(StrTool.cTrim(LCInsuredDes.getChildText(XML_OCCUPATIONCODE)));
                                }                 		
                    		} 
                    		tTransferDataSet.add(tTransferData1);
                    		tLCInsuredSchema.setSequenceNo(StrTool.cTrim(LCInsured.getChildText(XML_INSUREDNO)));
                            tLCInsuredSet.add(tLCInsuredSchema);  
                        }
                    }
            		else
            		{
            			/**
            			tLCInsuredSchema = new LCInsuredSchema();
                		tLCInsuredSchema.setInsuredNo(StrTool.cTrim(LCInsured.getChildText(XML_INSUREDNO))); //是否多被保人标志(被保人序号)
                		tTransferData1 = new TransferData();
                        tReflections.transFields(tLCInsuredSchema,tLCAppntIndSchema);
                        tTransferData1.setNameAndValue("samePersonFlag", "1"); //投保人同被保人标志
                        tTransferDataSet.add(tTransferData1);
                        tLCInsuredSchema.setSequenceNo(StrTool.cTrim(LCInsured.getChildText(XML_INSUREDNO)));
                        tLCInsuredSet.add(tLCInsuredSchema);
                        **/
                        CError.buildErr(this, "被保人信息为空！");
             		    return null;
            		}
   		
            		//生存保险金、年金、红利处理方式信息
            		if (LCDealTypeSet != null )
            		{
            			List LCDealTypeList = LCDealTypeSet.getChildren(XML_LCDEALTYPE);
            			Element LCDealType;
                		tLCPolSet1 = new LCPolSet();
                        if (LCDealTypeList != null && LCDealTypeList.size()>0 )
                        {                        	
                        	for (j = 0; j < LCDealTypeList.size(); j++)
                            { 
                        		LCDealType = (Element) LCDealTypeList.get(j);
                        		if(StrTool.cTrim(LCDealType.getChildText(XML_TYPETORISK)).equals(""))
	  	                  	    {
	  	                  			CError.buildErr(this, "生存保险金、年金、红利处理方式关联的主险序号为空！");
	  	                  			return null;
	  	                  	    }
                        		
                        		tTransferData1 = new TransferData();
                        		tLCPolSchema1 = new LCPolSchema();
//                        		tLCPolSchema1.setSchema(tLCPolSchema);//加入单个生存保险金、年金、红利处理方式信息
                        		tLCPolSchema1.setInsuredNo(StrTool.cTrim(LCDealType.getChildText(XML_TYPETORISK))); //多主险序号                       		
                        		String tGetYear = StrTool.cTrim(LCDealType.getChildText(XML_GETYEAR));
                        	    logger.debug("****tGetYear :  "+tGetYear);
                        	    try
                        	    {
                        	    	tLCPolSchema1.setGetYear(tGetYear);
                        	    }
                        	    catch(Exception ex)
                        	    {
                        	        logger.debug("****tLCPolSchema1.setGetYear("+tGetYear+") 发生异常");
                        	    }

                        	    if(!(StrTool.cTrim(LCDealType.getChildText(XML_GETYEARFLAG))).equals(""))
                        	    {
                        	    	tLCPolSchema1.setGetYearFlag(StrTool.cTrim(LCDealType.getChildText(XML_GETYEARFLAG)));
                        	    }	
                        	    else if( tLCPolSchema1.getGetYear()>0) //开领年龄不为空时，领取年龄标志默认为“岁（A）”
                        	    {
                        	    	tLCPolSchema1.setGetYearFlag("A");
                        	    }
                        	    
                        	    //需要校验的信息
                        	    String tGetYears = "";
                        	    if(StrTool.cTrim(LCDealType.getChildText(XML_GETYEARS)).indexOf("年")!=-1) //如果包含单位，则截取
                        	    {
                        	        tGetYears = LCDealType.getChildText(XML_GETYEARS).substring(0,LCDealType.getChildText(XML_GETYEARS).indexOf("年"));
                        	    }
                        	    else
                        	    {
                        	        tGetYears = StrTool.cTrim(LCDealType.getChildText(XML_GETYEARS));
                        	    }
                        	    logger.debug("***tGetYears: "+tGetYears);

                        	    tTransferData1.setNameAndValue(XML_GETYEARS,tGetYears);
                        	    tTransferData1.setNameAndValue(XML_GETDUTYKIND,StrTool.cTrim(LCDealType.getChildText(XML_GETDUTYKIND)));
                        	    
                        	    tLCPolSchema1.setLiveGetMode(StrTool.cTrim(LCDealType.getChildText(XML_LIVEGETMODE)));
                        	    tLCPolSchema1.setBonusGetMode(StrTool.cTrim(LCDealType.getChildText(XML_BONUSGETMODE)));                       	    
                        	    tTransferDataSet1.add(tTransferData1);
                        	    tLCPolSet1.add(tLCPolSchema1);
                            }
            	        }
                       
            		}
            		
            		//险种信息
            		if(LCRisks !=null)
                    {
                      List RiskSList = LCRisks.getChildren(XML_RISKS);
                      if(RiskSList != null &&  RiskSList.size() > 0 )
                      {
                        tRiskSCount = RiskSList.size();
                        Element RiskS = null;
                        List RiskList = null;
                        boolean riskFlag = false;  
                        boolean riskFlag1 = false; 
                        for(j=0 ;j<tRiskSCount ; j++)
                        {                           
                          tRiskBasicInfoSet = new VData();
                          RiskS = (Element)RiskSList.get(j);
                          tRiskIndex = StrTool.cTrim(RiskS.getChildText(XML_RISKNO)); //多主险序号
                          if(tRiskIndex.equals(""))
                  	      {
                  			 CError.buildErr(this, "主险序号为空！");
                  			 return null;
                  	      }
                          else if(tRiskIndex.equals("-1"))
                  	      {
                  			 riskFlag = true;
                  	      }
                          else
                  		  {
                        	 riskFlag1 = true;
                  		  }
                  		
                  		  /*if((riskFlag&&(tRiskSCount>1))
                  				||(riskFlag1&&(tRiskSCount<=1)))
                  		  {
                  			 CError.buildErr(this, "主险序号有误！");
                  			 return null;
                  	      }*/
                          
                          RiskList = RiskS.getChildren(XML_RISK);
                          
                          if(RiskList != null &&  RiskList.size() > 0 )
                          {                            
                            tRiskCount = RiskList.size();
                            Element Risk = null;
                            for(k=0 ;k<tRiskCount ; k++)
                            {
                            	Risk = (Element)RiskList.get(k);
                            	tRiskBasicInfo = new RiskBasicInfo();//单个险种信息
                                tRiskBasicInfo.setRiskCode(StrTool.cTrim(Risk.getChildText(XML_RISKCODE)));
                                tRiskBasicInfo.setRiskName(StrTool.cTrim(Risk.getChildText(XML_RISKNAME)));
                                tRiskBasicInfo.setPrem(("".equals(StrTool.cTrim(Risk.getChildText(XML_PREM))))?"0.00":StrTool.cTrim(Risk.getChildText(XML_PREM)));
                                //tongmeng 
                                tRiskBasicInfo.setInputPrem(("".equals(StrTool.cTrim(Risk.getChildText(XML_PREM))))?"0.00":StrTool.cTrim(Risk.getChildText(XML_PREM)));
                                tRiskBasicInfo.setAddPrem(("".equals(StrTool.cTrim(Risk.getChildText(XML_ADDPREM))))?"0.00":StrTool.cTrim(Risk.getChildText(XML_ADDPREM)));
                                tRiskBasicInfo.setStrMult(StrTool.cTrim(Risk.getChildText(XML_MULT)));
                                tRiskBasicInfo.setAmnt(("".equals(StrTool.cTrim(Risk.getChildText(XML_AMNT))))?"0.00":StrTool.cTrim(Risk.getChildText(XML_AMNT)));

                                //银代保单特殊处理
                                if(!"".equals(tLCPolSchema.getPrtNo()) && tLCPolSchema.getPrtNo().length()> 4
                                   && ("15".equals(tLCPolSchema.getPrtNo().substring(2,4)) || "25".equals(tLCPolSchema.getPrtNo().substring(2,4)))
                                   && !"".equals(StrTool.cTrim(Risk.getChildText(XML_INSUYEAR)))
                                   )
                                {
                                  tRiskBasicInfo.setStrInsuYear(StrTool.cTrim(Risk.getChildText(XML_INSUYEAR))+"年");
                                }
                                else
                                {
                                  tRiskBasicInfo.setStrInsuYear(StrTool.cTrim(Risk.getChildText(XML_INSUYEAR)));
                                }

                                //银代保单特殊处理
                                if(!"".equals(tLCPolSchema.getPrtNo()) && tLCPolSchema.getPrtNo().length()> 4
                                   && ("15".equals(tLCPolSchema.getPrtNo().substring(2,4)) || "25".equals(tLCPolSchema.getPrtNo().substring(2,4)))
                                   && !"".equals(StrTool.cTrim(Risk.getChildText(XML_PAYENDYEAR)))
                                   )
                                {
                                  tRiskBasicInfo.setStrPayEndYear(StrTool.cTrim(Risk.getChildText(XML_PAYENDYEAR))+"年");
                                }
                                else
                                {
                                  tRiskBasicInfo.setStrPayEndYear(StrTool.cTrim(Risk.getChildText(XML_PAYENDYEAR)));
                                }

                                tRiskBasicInfo.setGetYear(tLCPolSchema.getGetYear());
                                tRiskBasicInfo.setLevel(StrTool.cTrim(Risk.getChildText(XML_LEVEL)));
                                tRiskBasicInfo.setRemark(StrTool.cTrim(Risk.getChildText(XML_REMARK)));
                                tRiskBasicInfo.setRiskNo(tRiskIndex);  //多主险序号
                                tRiskBasicInfo.convertRiskinfo();   //转换险种信息
                                tRiskBasicInfoSet.add(tRiskBasicInfo);
                            }                         
                          }
                          tRiskBasicInfoMainSet.add(tRiskBasicInfoSet);
                        }
                      }
                    }
            		
            		//受益人信息
                    if(LCBnfs != null)
                    {
                      List LCBnfList = LCBnfs.getChildren(XML_LCBNF);
                      if(LCBnfList != null &&  LCBnfList.size() > 0 )
                      {
                    	Element LCBnf = null;
                        for(j=0;j<LCBnfList.size();j++)
                        {
                          LCBnf = (Element)LCBnfList.get(j); 
                          if(StrTool.cTrim(LCBnf.getChildText(XML_BNFTORISK)).equals(""))
                  	      {
                  			 CError.buildErr(this, "受益人关联的主险序号为空！");
                  			 return null;
                  	      }
                          
                          if(!"".equals(StrTool.cTrim(LCBnf.getChildText(XML_BNFGRADE))) &&
                             !"".equals(StrTool.cTrim(LCBnf.getChildText(XML_BNFTYPE))))
                          {
                            tLCBnfSchema = new LCBnfSchema();

                            try
                            {
                              String tBnfName=new String(StrTool.cTrim(LCBnf.getChildText(XML_NAME)));
                              logger.debug("tBnfName: "+tBnfName);
                              tLCBnfSchema.setName(StrTool.cTrim(tBnfName));
                            }catch(Exception ex){}
                            tLCBnfSchema.setBnfType(StrTool.cTrim(LCBnf.getChildText(XML_BNFTYPE)));
                            tLCBnfSchema.setIDType(StrTool.cTrim(LCBnf.getChildText(XML_IDTYPE)));
                            tLCBnfSchema.setIDNo(StrTool.cTrim(LCBnf.getChildText(XML_IDNO)));
                            tLCBnfSchema.setRelationToInsured(StrTool.cTrim(LCBnf.getChildText(XML_RELATIONTOINSURED)));
                            tLCBnfSchema.setPostalAddress(StrTool.cTrim(LCBnf.getChildText(XML_ADDRESS)));
                            tLCBnfSchema.setIDExpDate(StrTool.cTrim(LCBnf.getChildText(XML_IDEXPDATE)));
                            //转换受益比例，如果包含百分号，转换为小数
                            logger.debug("第"+i+"个受益比例(转换前): "+StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT)));
                            if(!"".equals(StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT))))
                            {
                              int index = StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT)).indexOf("%");
                              int index1 = StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT)).indexOf("％");
                              if(index != -1)
                              {
                                try
                                {
                                  double tBnfLot = Double.parseDouble( StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT)).substring(0,index))/100;
                                  tLCBnfSchema.setBnfLot(tBnfLot);
                                }
                                catch(Exception ex){} //防止格式错误
                              }
                              else if(index1 != -1)
                              {
                                try
                                {
                                  double tBnfLot = Double.parseDouble( StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT)).substring(0,index1))/100;
                                  tLCBnfSchema.setBnfLot(tBnfLot);
                                }
                                catch(Exception ex){} //防止格式错误
                              }
                              else
                              {
                                try
                                {
                                  tLCBnfSchema.setBnfLot(StrTool.cTrim(LCBnf.getChildText(XML_BNFLOT)));
                                }catch(Exception ex){} //防止格式错误
                              }
                            }

                            logger.debug("第"+i+"个受益比例(转换后): "+tLCBnfSchema.getBnfLot());
                            tLCBnfSchema.setBnfGrade(StrTool.cTrim(LCBnf.getChildText(XML_BNFGRADE)));
                            tLCBnfSchema.setBnfNo(StrTool.cTrim(LCBnf.getChildText(XML_BNFTORISK)));//多主险序号
                            tLCBnfSet.add(tLCBnfSchema);
                          }
                        }
                      }
                    }
                    
                    tInsuredRelaSet.add(tTransferDataSet);
                    tInsuredRelaSet.add(tTransferDataSet1);
                    tInsuredRelaSet.add(tLCInsuredSet);
                    tInsuredRelaSet.add(tLCBnfSet);
                    tInsuredRelaSet.add(tLCPolSet1);                    
                    tInsuredRelaSet.add(tRiskBasicInfoMainSet);                                          
                    
                    //---------------------print------------------------------------------//
                    if(tLCInsuredSet !=null)
                    {
                      logger.debug("***LCInsuredSet: "+tLCInsuredSet.encode());
                    }

                    if(tLCBnfSet !=null)
                    {
                      logger.debug("***LCBnfSet: "+tLCBnfSet.encode());
                    }
                    
                    if(tLCPolSet1 !=null)
                    {
                      logger.debug("***LCPolSet1（生存等处理方式信息）: "+tLCPolSet1.encode());
                    }

                    if(tRiskBasicInfoSet !=null)
                    {
                      for(k = 0 ; k<tRiskBasicInfoSet.size();k++)
                      {
                        logger.debug("***RiskBasicInfoSet"+(k+1)+" : "+((RiskBasicInfo)tRiskBasicInfoSet.get(k)).encode());
                      }
                    }
                    //-------------------------------------------------------------------//
                    
                    InsuredResults.add(tInsuredRelaSet);
                
                }
            	tResult.add(InsuredResults);
             }
          }       
     
        }

      } catch(Exception ex)
      {
        logger.debug("数据转换时发生异常："+ ex.toString());
        CError.buildErr(this, "数据转换时发生异常："+ ex.toString());
        return null;
      }
      return tResult;
  }

  public static void main(String[] args)
  {
    TBXMLTransfer TBXMLTransfer1 = new TBXMLTransfer();
    VData t=TBXMLTransfer1.getOnePolData("86110100005051");
    logger.debug("t.size: "+t.size());
//    try
//    {
//
//      java.io.FileInputStream t = new java.io.FileInputStream("C:/Documents and Settings/Administrator/桌面/录单外包/MS外包录入/返回给外包方的相关资料/外包方承保数据返回格式.xml");
//      org.jdom.Document tDocument = TBPubFun.produceXmlDoc(t);
//      org.jdom.Element tElement = tDocument.getRootElement();
//      org.jdom.Element tOnePolData = tElement.getChild("OnePolData");
//      VData tVData = TBXMLTransfer1.XMLToVData(tOnePolData);
//      VData tRiskBasicInfoSet;
//      RiskBasicInfo tRiskBasicInfo = new RiskBasicInfo();
//      tRiskBasicInfoSet = (VData)tVData.getObjectByObjectName("VData",0);
//      if(tRiskBasicInfoSet!=null)
//      {
//        for(int i=0;i<tRiskBasicInfoSet.size();i++)
//        {
//          tRiskBasicInfo = (RiskBasicInfo)tRiskBasicInfoSet.get(i);
//          logger.debug("Riskcode: "+tRiskBasicInfo.getRiskCode());
//        }
//      }
//      }catch(Exception ex)
//      {
//
//      }
  }
}
