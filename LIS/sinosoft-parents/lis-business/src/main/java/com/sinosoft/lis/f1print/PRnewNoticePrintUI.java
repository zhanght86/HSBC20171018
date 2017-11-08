package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;

public class PRnewNoticePrintUI implements BusinessService {
private static Logger logger = Logger.getLogger(PRnewNoticePrintUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
          public CErrors mErrors=new CErrors();

          private VData mResult = new VData();
          //private VData mInputData = new VData();
          //业务处理相关变量
           /** 全局数据 */
          private GlobalInput mGlobalInput =new GlobalInput() ;
          private LOPRTManagerSchema mLOPRTManagerSchema=new LOPRTManagerSchema();


  public PRnewNoticePrintUI() {
  }
  /**
       传输数据的公共方法
       */
  public boolean submitData(VData cInputData, String cOperate)
  {
    if( !cOperate.equals("PRINT")&& !cOperate.equals("PRINTBATCH")  ) {
      buildError("submitData", "不支持的操作字符串");
      return false;
    }

    // 得到外部传入的数据，将数据备份到本类中
//    if( !getInputData(cInputData) ) {
//      return false;
//    }

    // 进行业务处理
//    if( !dealData() ) {
//      return false;
//    }

    // 准备传往后台的数据
//    VData vData = new VData();

//    if( !prepareOutputData(vData) ) {
//      return false;
//    }

    PRnewNoticePrintBL tPRnewNoticePrintBL = new PRnewNoticePrintBL();
    logger.debug("Start tPRnewNoticePrint UI Submit ...");

    if( !tPRnewNoticePrintBL.submitData(cInputData, cOperate) ) {
      if( tPRnewNoticePrintBL.mErrors.needDealError() ) {
        mErrors.copyAllErrors(tPRnewNoticePrintBL.mErrors);
        return false;
      } else {
        buildError("submitData", "BodyCheckPrintBL发生错误，但是没有提供详细的出错信息");
        return false;
      }
    } else {
      mResult = tPRnewNoticePrintBL.getResult();
      return true;
    }
  }



  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData(VData vData)
  {
    try {
      vData.clear();
      vData.add(mGlobalInput);
      vData.add(mLOPRTManagerSchema);
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
  private boolean dealData()
  {
    return true;
  }

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    //全局变量
    mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    mLOPRTManagerSchema.setSchema((LOPRTManagerSchema)cInputData.getObjectByObjectName("LOPRTManagerSchema",0));

    if( mLOPRTManagerSchema==null ) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }

    return true;
  }

  public VData getResult()
  {
    return this.mResult;
  }
  
  public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "DerferAppF1PUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }


  public static void main(String[] args) {
    BodyCheckPrintUI tBodyCheckPrintUI = new BodyCheckPrintUI();
    LCPolSchema tLCPolSchema = new LCPolSchema();
    //tLCPolSchema.setPolNo("00000120020110000021");
    tLCPolSchema.setPolNo("86110020020110000275");
    VData tVData = new VData();
    tVData.addElement(tLCPolSchema);
    GlobalInput tG = new GlobalInput();
    tG.ManageCom="86";
    tG.Operator="001";
    tVData.addElement(tG);
    tBodyCheckPrintUI.submitData(tVData,"PRINT");
  }
}
