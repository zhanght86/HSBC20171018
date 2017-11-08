package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class ContGrpInsuredExcelUI {
private static Logger logger = Logger.getLogger(ContGrpInsuredExcelUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
   public CErrors mErrors=new CErrors();
   private VData mResult = new VData();
   //业务处理相关变量
   /** 全局数据 */
   private GlobalInput mGlobalInput = new GlobalInput() ;
   private String StartDay="";
   private String EndDay="";
   private String CalManageCom="";
   private String Grppolno="";
   private String ManageCom="";
   private String InsuredNo="";
   private String Name="";
   private String IDNo="";
   private String ContPlanCode="";

   //含M00 1表示是 0表示否
  private String tIsHaveM00 = "";

  public ContGrpInsuredExcelUI() {
  }
  /**
传输数据的公共方法
*/
public boolean submitData(VData cInputData, String cOperate) {


  ContGrpInsuredExcelBL tContGrpInsuredExcelBL= new ContGrpInsuredExcelBL();
  logger.debug("Start ContGrpInsuredExcelUI Submit ...");

  if( !tContGrpInsuredExcelBL.submitData(cInputData, cOperate) ) {
    if( tContGrpInsuredExcelBL.mErrors.needDealError() ) {
      mErrors.copyAllErrors(tContGrpInsuredExcelBL.mErrors);
      return false;
    } else {
      buildError("submitData", "LAAgentMakePolBL发生错误，但是没有提供详细的出错信息");
      return false;
    }
  } else {
    mResult = tContGrpInsuredExcelBL.getResult();
    return true;
  }
}

/**
 * 准备往后层输出所需要的数据
 * 输出：如果准备数据时发生错误则返回false,否则返回true
 */
private boolean prepareOutputData(VData vData) {
  try {
    vData.clear();
    vData.addElement(Grppolno);
    vData.addElement(ManageCom);
    vData.addElement(InsuredNo);
    vData.addElement(Name);
    vData.addElement(IDNo);
    vData.addElement(ContPlanCode);
    vData.addElement(mGlobalInput);
  } catch (Exception ex) {
    ex.printStackTrace();
    buildError("prepareOutputData", "发生异常");
    return false;
  }
  return true;
}

/**
 * 根据前面的输入数据，进行UI逻辑处理
 * 如果在处理过程中出错，则返回false,否则返回true
 */
private boolean dealData() {
  return true;
}

/**
 * 从输入数据中得到所有对象
 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
 */
private boolean getInputData(VData cInputData) {
  //全局变量
	Grppolno=(String)cInputData.get(0);
	ManageCom=(String)cInputData.get(1);
	InsuredNo=(String)cInputData.get(2);
	Name=(String)cInputData.get(3);
	IDNo=(String)cInputData.get(4);
	ContPlanCode=(String)cInputData.get(5);
  //StartDay=(String)cInputData.get(0);
  //EndDay=(String)cInputData.get(1);
  //CalManageCom=(String)cInputData.get(2);
 // tIsHaveM00=(String)cInputData.get(3);
  //logger.debug("UI是否含M00["+tIsHaveM00+"]");
  mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;
  return true;
}

public VData getResult() {
  return this.mResult;
}

private void buildError(String szFunc, String szErrMsg) {
  CError cError = new CError();

  cError.moduleName = "LAAgentMakePolBL";
  cError.functionName = szFunc;
  cError.errorMessage = szErrMsg;
  this.mErrors.addOneError(cError);
}

public CErrors getErrors() {
  return mErrors;
  }
  public static void main(String[] args) {
	  ContGrpInsuredExcelUI tContGrpInsuredExcelUI = new ContGrpInsuredExcelUI();
  }
}
