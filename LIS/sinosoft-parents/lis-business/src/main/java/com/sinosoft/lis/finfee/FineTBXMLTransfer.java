package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import org.jdom.*;
import org.jdom.input.*;

import java.util.*;
import java.sql.*;

/**
 * <p>Title: FineTBXMLTransfer</p>
 * <p>Description: 将XML转换为保单表对应的VData相关数据</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author ln
 * @version 1.0
 */

public class FineTBXMLTransfer
{
private static Logger logger = Logger.getLogger(FineTBXMLTransfer.class);

  public CErrors mErrors = new CErrors();
  /**错误信息数据项*/
  private static final String XML_BASEINFO = "BaseInfo";
  private static final String XML_SUCCFLAG = "SuccFlag";
  private static final String XML_ERRORS = "Errors";
  private static final String XML_ERROR = "Error";
  private static final String XML_ERRTAG = "ErrTag";
  private static final String XML_ERRCODE = "ErrCode";
  private static final String XML_ERRMESSAGE = "ErrMessage";

  /**财务信息数据项*/
  private static final String XML_TEMPFEE = "TempFee"; 
  private static final String XML_TEMPFEENO = "TempFeeNo"; //单证印刷号
  private static final String XML_TEMPFEETYPE = "TempFeeType";  //暂收据类型
  private static final String XML_MANAGECOM = "ManageCom";  //管理机构代码
  private static final String XML_SALECHNL = "SaleChnl";  //销售渠道
  private static final String XML_AGENTCODE = "AgentCode";  //代理人编码
  private static final String XML_AGENTNAME = "AgentName";  //代理人姓名
  private static final String XML_AGENTGROUP = "AgentGroup";  //代理人组别
  private static final String XML_AGENTCOM = "AgentCom";  //代理人机构
  private static final String XML_PAYDATE = "PayDate";  //交费日期
  private static final String XML_PAYMODE = "PayMode";  //交费方式
  private static final String XML_PAYMONEY = "PayMoney";  //交费总金额
  private static final String XML_BANKCODE = "BankCode";  //开户银行
  private static final String XML_ACCNAME = "AccName"; //户名
  private static final String XML_BANKACCNO = "BankAccNo";  //银行账号
  private static final String XML_OTHERNO = "OtherNo";  //投保单印刷号
  private static final String XML_BANKNAME = "BankName";  //银行名称
  private static final String XML_ENTERACCDATE = "EnterAccDate";  //到账日期
  private static final String XML_CHEQUENO = "ChequeNo";  //票据号码
  private static final String XML_APPNTNAME = "AppntName";  //投保人姓名
  
  //系统6.0预留
  private static final String XML_REMARK = "Remark"; // 备注 6.0
  private static final String XML_IDNO = "IDNo"; //证件号码
  private static final String XML_IDTYPE = "IDType"; //证件类型
  private static final String XML_INACCNAME = "InAccName";  //收费银行帐户名
  private static final String XML_INBANKACCNO = "InBankAccNo";  //收费银行帐号
  private static final String XML_INBANKCODE = "InBankCode";  //收费银行编码
  private static final String XML_CHEQUEDATE = "ChequeDate";  //支票日期
  private static final String XML_BRANCHCODE = "BranchCode";  //代理人所在组
  private static final String XML_DEPARTMENT = "Department";  //代理人所在部
  private static final String XML_DISTICT = "Distict";  //代理人所在区
  private static final String XML_STANDPREM = "StandPrem";   //预收标保
  private static final String XML_CONTCOM = "ContCom";   //保单所属机构
  private static final String XML_POLICYCOM = "PolicyCom";  //交费机构

  /**险种信息*/
  private static final String XML_RISKS = "Risks";
  private static final String XML_RISK = "Risk";
  private static final String XML_RISKCODE = "RiskCode";  //险种代码
  private static final String XML_RISKNAME = "RiskName";  //险种名称
  private static final String XML_MONEY= "PayMoney";  //交费金额
  private static final String XML_PAYYEARS = "PayYears";  //缴费年期
  private static final String XML_PAYINTV = "PayIntv";  //缴费间隔

  public FineTBXMLTransfer()
  {
  }

  /**
   * 根据印刷号返回一个收据的数据集合
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
    String tSQL = " and BussNo='"+tBussNo+"' and BussNoType='OF'";
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
    int tRiskCount = 0;  //险种数量
    VData tResult = new VData();
    try
    {
      LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
      LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();

      //任务处理状态主表和任务处理错误信息表
      BPOMissionStateSchema tBPOMissionStateSchema = new BPOMissionStateSchema();
      BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = new BPOMissionDetailErrorSet();
      BPOMissionDetailErrorSchema tBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();

      FineRiskBasicInfo tRiskBasicInfo = new FineRiskBasicInfo();//单个险种信息
      VData tRiskBasicInfoSet = new VData();   //险种集合      
      TransferData tTransferData = new TransferData();//待校验字段集合

      Element BaseInfo = tOnePolData.getChild(XML_BASEINFO);
      Element TempFee = tOnePolData.getChild(XML_TEMPFEE);  
      Element Risks = tOnePolData.getChild(XML_RISKS);

      tSuccFlag = StrTool.cTrim(BaseInfo.getChildText(XML_SUCCFLAG));
      //如果外包方返回的数据错误则返回为空
      if("".equals(tSuccFlag)||(!"0".equals(tSuccFlag) && !"1".equals(tSuccFlag) &&!"2".equals(tSuccFlag)))
      {
        logger.debug("外包方返回的异常件标志SuccFlag不正确："+tSuccFlag);
        CError.buildErr(this, "外包方返回的异常件标志SuccFlag不正确："+tSuccFlag) ;

        return null;
      }

      //处理暂交费表
      tLJTempFeeSchema.setTempFeeType("1");//新单交费
      tLJTempFeeSchema.setTempFeeNo(StrTool.cTrim(TempFee.getChildText(XML_TEMPFEENO)));
      if("".equals(StrTool.cTrim(TempFee.getChildText(XML_PAYMODE))))
      {
    	  tLJTempFeeSchema.setTempFeeNoType("");
      }
      else if("4".equals(StrTool.cTrim(TempFee.getChildText(XML_PAYMODE))))
      {
    	  tLJTempFeeSchema.setTempFeeNoType("2");
      }
      else
    	  tLJTempFeeSchema.setTempFeeNoType("1");      
      
      tLJTempFeeSchema.setOtherNoType("4");
      tLJTempFeeSchema.setOperState("0");
      
      tLJTempFeeSchema.setOtherNo(StrTool.cTrim(TempFee.getChildText(XML_OTHERNO)));
      tLJTempFeeSchema.setAgentCode(StrTool.cTrim(TempFee.getChildText(XML_AGENTCODE)));
      if("".equals(StrTool.cTrim(TempFee.getChildText(XML_MANAGECOM))))
      {
        ExeSQL tExeSQL = new ExeSQL();
        String tSQL ="";
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        tSQL = "select Managecom from ES_Doc_Main where DocCode='?DocCode?' and busstype='OF' and rownum=1";
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
        	tSQL = "select Managecom from ES_Doc_Main where DocCode='?DocCode?' and busstype='OF' limit 0,1";
        }
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSQL);
        sqlbv.put("DocCode", tLJTempFeeSchema.getTempFeeNo());
        tLJTempFeeSchema.setManageCom(StrTool.cTrim(tExeSQL.getOneValue(sqlbv)));
        tLJTempFeeClassSchema.setManageCom(StrTool.cTrim(tExeSQL.getOneValue(sqlbv)));
      }
      else
      {
    	  tLJTempFeeSchema.setManageCom(StrTool.cTrim(TempFee.getChildText(XML_MANAGECOM)));
    	  tLJTempFeeClassSchema.setManageCom(StrTool.cTrim(TempFee.getChildText(XML_MANAGECOM)));
      }

      //需要校验的信息
      String tPayMoney = "";
      tTransferData.setNameAndValue("PayMoney", "succ");
      if(StrTool.cTrim(TempFee.getChildText(XML_PAYMONEY)).indexOf("元")!=-1)//如果包含单位，报错
      {
    	  tPayMoney = TempFee.getChildText(XML_PAYMONEY).substring(0,TempFee.getChildText(XML_PAYMONEY).indexOf("元"));
      }
      else if(StrTool.cTrim(TempFee.getChildText(XML_PAYMONEY)).indexOf("万元")!=-1)//如果包含单位，报错
      {
    	  tPayMoney = TempFee.getChildText(XML_PAYMONEY).substring(0,TempFee.getChildText(XML_PAYMONEY).indexOf("万元"));
    	  tTransferData.setNameAndValue("PayMoney", "10000");
      }
      else if(StrTool.cTrim(TempFee.getChildText(XML_PAYMONEY)).indexOf("?")!=-1)//如果包含单位，报错
      {
    	  tTransferData.setNameAndValue("PayMoney", "error");
      }
      else
      {
    	  tPayMoney = StrTool.cTrim(TempFee.getChildText(XML_PAYMONEY));
      }
      logger.debug("***LJTempFeeClassSchema:PayMoney: "+tPayMoney);
      
      //处理暂交费分类表
      tLJTempFeeClassSchema.setTempFeeNo(StrTool.cTrim(TempFee.getChildText(XML_TEMPFEENO)));
      tLJTempFeeClassSchema.setPayMode(StrTool.cTrim(TempFee.getChildText(XML_PAYMODE)));
      tLJTempFeeClassSchema.setAccName(StrTool.cTrim(TempFee.getChildText(XML_ACCNAME)));
      tLJTempFeeClassSchema.setIDNo(StrTool.cTrim(TempFee.getChildText(XML_IDNO)));
      if(!StrTool.cTrim(TempFee.getChildText(XML_IDNO)).equals(""))
    		  tLJTempFeeClassSchema.setIDType("0");//身份证类型
      tLJTempFeeClassSchema.setChequeNo(StrTool.cTrim(TempFee.getChildText(XML_CHEQUENO)));
      tLJTempFeeClassSchema.setPayMoney(tPayMoney);
      tLJTempFeeClassSchema.setBankCode(StrTool.cTrim(TempFee.getChildText(XML_BANKCODE)));
      String tNewBankCode = StrTool.cTrim(TempFee.getChildText(XML_BANKCODE));
      String tNewBankAccNo = StrTool.cTrim(TempFee.getChildText(XML_BANKACCNO));
      String ttNewBankAccNo = StrTool.cTrim(TempFee.getChildText(XML_BANKACCNO));
      if(!tNewBankCode.equals("")&&!tNewBankAccNo.equals(""))
    	  ttNewBankAccNo = PubFun.getBankAccNo(tNewBankCode , tNewBankAccNo);
      tLJTempFeeClassSchema.setOriBankAccNo(tNewBankAccNo);//记录原始银行账号信息
      tLJTempFeeClassSchema.setBankAccNo(ttNewBankAccNo);//记录过滤后的银行账号信息
      tLJTempFeeClassSchema.setOperState("0");
      
      //任务处理状态主表
      tBPOMissionStateSchema.setBussNo(tLJTempFeeSchema.getTempFeeNo());
      tBPOMissionStateSchema.setBussNoType("OF");
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
        if(ErrorList != null && ErrorList.size()>0)
        {
          for(int i = 0; i<ErrorList.size(); i++)
          {
            Element Error = (Element)ErrorList.get(i);
            tBPOMissionDetailErrorSchema = new BPOMissionDetailErrorSchema();
            tBPOMissionDetailErrorSchema.setBussNo(tLJTempFeeSchema.getTempFeeNo());
            tBPOMissionDetailErrorSchema.setBussNoType("OF");
            tBPOMissionDetailErrorSchema.setErrorCount(i+1);
            tBPOMissionDetailErrorSchema.setErrorTag(StrTool.cTrim(Error.getChildTextTrim(XML_ERRTAG)));
            tBPOMissionDetailErrorSchema.setErrorCode(StrTool.cTrim(Error.getChildTextTrim(XML_ERRCODE)));
            tBPOMissionDetailErrorSchema.setErrorContent(StrTool.cTrim(Error.getChildTextTrim(XML_ERRMESSAGE)));
            tBPOMissionDetailErrorSet.add(tBPOMissionDetailErrorSchema);
          }
        }
      }
      //准备清洁件和外包方可处理的异常件的保单数据
      if(!"2".equals(tSuccFlag))
      {        
        if(Risks !=null)
        {
          List RiskList = Risks.getChildren(XML_RISK);
          if(RiskList != null &&  RiskList.size() > 0 )
          {
            tRiskCount = RiskList.size();
            for(int i=0 ;i<tRiskCount ; i++)
            {
              Element Risk = (Element)RiskList.get(i);
              tRiskBasicInfo = new FineRiskBasicInfo();//单个险种信息
              tRiskBasicInfo.setRiskCode(StrTool.cTrim(Risk.getChildText(XML_RISKCODE)));
              tRiskBasicInfo.setPayMoney(("".equals(StrTool.cTrim(Risk.getChildText(XML_MONEY))))?"0.00":StrTool.cTrim(Risk.getChildText(XML_PAYMONEY)));
              tRiskBasicInfo.setPayYears(StrTool.cTrim(Risk.getChildText(XML_PAYYEARS)));
              tRiskBasicInfo.setPayIntv(StrTool.cTrim(Risk.getChildText(XML_PAYINTV)));
              
              tRiskBasicInfo.convertRiskinfo();   //转换险种信息
              tRiskBasicInfoSet.add(tRiskBasicInfo); ;
            }
          }
        }
      }

      tResult.add(tLJTempFeeSchema);
      logger.debug("***LJTempFeeSchema: "+tLJTempFeeSchema.encode());
      tResult.add(tBPOMissionStateSchema);
      logger.debug("***BPOMissionStateSchema: "+tBPOMissionStateSchema.encode());
      if(tBPOMissionDetailErrorSet != null && tBPOMissionDetailErrorSet.size()>0)
      {
        tResult.add(tBPOMissionDetailErrorSet);
        logger.debug("***BPOMissionDetailErrorSet: "+tBPOMissionDetailErrorSet.encode());
      }
      tResult.add(tLJTempFeeClassSchema);
      logger.debug("***LJTempFeeClassSchema: "+tLJTempFeeClassSchema.encode());

      if(tRiskBasicInfoSet !=null)
      {
        tResult.add(tRiskBasicInfoSet);
        for(int i = 0 ; i<tRiskBasicInfoSet.size();i++)
        {
          logger.debug("***RiskBasicInfoSet"+(i+1)+" : "+((FineRiskBasicInfo)tRiskBasicInfoSet.get(i)).encode());
        }
      }
      
      tResult.add(tTransferData);

      } catch(Exception ex)
      {
        logger.debug("数据转换时发生异常："+ ex.toString());
        CError.buildErr(this, "数据转换时发生异常："+ ex.toString()) ;
        return null;
      }
      return tResult;
  }

  public static void main(String[] args)
  {
    FineTBXMLTransfer TBXMLTransfer1 = new FineTBXMLTransfer();
    VData t=TBXMLTransfer1.getOnePolData("86110100005051");
    logger.debug("t.size: "+t.size());
  }
}
