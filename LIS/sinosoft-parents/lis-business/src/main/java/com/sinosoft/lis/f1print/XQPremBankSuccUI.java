package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-04-04
 */

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class XQPremBankSuccUI
{
private static Logger logger = Logger.getLogger(XQPremBankSuccUI.class);

  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

  private String strStartDate="";                //开始日期
  private String strEndDate="";                  //结束日期
  private String strAgentState = "";             //业务员的状态(1为在职单，0为孤儿单)
  private String strPremType = "";               //首续期的标志
  private String strFlag = "";                   //S or F(S为银行代收，F为银行代付)
  private String strComCode = "";                //系统登陆的机构(查询银行日志表)
  private String strStation = "";                //界面上录入的管理机构
  private GlobalInput mG = new GlobalInput();
  public XQPremBankSuccUI()
  {
  }

  /**
   传输数据的公共方法
   */
  public boolean submitData(VData cInputData, String cOperate)
  {
    try
    {
      if( !cOperate.equals("PRINT") )
      {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }
      
      NewSuccXQPremBankBL tNewSuccXQPremBankBL = new NewSuccXQPremBankBL();
      logger.debug("Start XQPremBankSuccUI Submit ...");

      if( !tNewSuccXQPremBankBL.submitData(cInputData, cOperate) )
      {
        if( tNewSuccXQPremBankBL.mErrors.needDealError() )
        {
          mErrors.copyAllErrors(tNewSuccXQPremBankBL.mErrors);
          return false;
        }
        else
        {
          buildError("submitData", "FinChargeDayModeF1PBL发生错误，但是没有提供详细的出错信息");
          return false;
        }
      }
      else
      {
        mResult = tNewSuccXQPremBankBL.getResult();
        return true;
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      CError cError = new CError( );
      cError.moduleName = "PLPsqsUI";
      cError.functionName = "submit";
      cError.errorMessage = e.toString();
      mErrors.addOneError(cError);
      return false;
    }
  }
  public VData getResult()
  {
    return this.mResult;
  }

  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );
    cError.moduleName = "FinChargeDayModeF1PUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }
  public boolean getInputData(VData tInputData)
  {
    strStartDate = (String)tInputData.get(0);
    strEndDate = (String)tInputData.get(1);
    strAgentState = (String)tInputData.get(2);
    strPremType = (String)tInputData.get(3);//首期还是续期
    strFlag = (String)tInputData.get(4);//F or S
    strStation = (String)tInputData.get(5);//界面上录入的管理机构
    mG.setSchema((GlobalInput)tInputData.getObjectByObjectName("GlobalInput",0));
    strComCode=mG.ManageCom.trim();
    logger.debug("strComCode"+strComCode);
    return true;
  }

  private boolean prepareOutputData(VData vData)
  {
    vData.clear();
    vData.addElement(strStartDate);
    vData.addElement(strEndDate);
    vData.addElement(strAgentState);
    vData.addElement(strPremType);
    vData.addElement(strFlag);
    vData.addElement(strStation);
    vData.addElement(strComCode);

    return true;
  }


  public static void main(String[] args)
  {
    String strStartDate="2004-4-1";                //开始日期
    String strEndDate="2004-5-30";                  //结束日期
    String strAgentState = "1";             //业务员的状态(1为在职单，0为孤儿单)
    String strPremType = "X";               //首续期的标志
    String strFlag = "S";                   //S or F(S为银行代收，F为银行代付)
    String strStation = "8611";
    GlobalInput tG = new GlobalInput();
    tG.Operator="001";
    tG.ManageCom="86";

    VData tVData = new VData();
    tVData.addElement(strStartDate);
    tVData.addElement(strEndDate);
    tVData.addElement(strAgentState);
    tVData.addElement(strPremType);
    tVData.addElement(strFlag);
    tVData.addElement(strStation);
    tVData.addElement(tG);

    XQPremBankSuccUI tXQPremBankSuccUI = new XQPremBankSuccUI();
    if(tXQPremBankSuccUI.submitData(tVData,"PRINT"))
    {
      logger.debug("执行完毕");
    }
  }
}
