package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReAdvancePremQueryUI {
private static Logger logger = Logger.getLogger(ReAdvancePremQueryUI.class);
  public CErrors mErrors=new CErrors();
 private VData mResult = new VData();
//业务处理相关变量
/** 全局数据 */
 private GlobalInput mGlobalInput = new GlobalInput() ;
 private String tManageCom="";
 private String tComLevel="";
 private String tStartDate="";
 private String tEndDate="";
 private String tYearMonth="";
 private String tBranchType="";

  public ReAdvancePremQueryUI()
  {
  }

  /**
 传输数据的公共方法
 */
 public boolean submitData(VData cInputData, String cOperate)
 {
     // 准备传往后台的数据
      VData vData = new VData();
      vData = (VData) cInputData.clone();
      ReAdvancePremQueryBL tReAdvancePremQueryBL= new ReAdvancePremQueryBL();
      logger.debug("Start ReAdvancePremQueryBL Submit ...");

      if( !tReAdvancePremQueryBL.submitData(vData, cOperate) )
       {
         if( tReAdvancePremQueryBL.mErrors.needDealError() )
          {
              mErrors.copyAllErrors(tReAdvancePremQueryBL.mErrors);
              return false;
          }
         else
          {
              buildError("submitData", "ReAdvancePremQueryBL发生错误，但是没有提供详细的出错信息");
              return false;
          }
       }
      else
       {
          mResult = tReAdvancePremQueryBL.getResult();
          return true;
       }
  }

  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData(VData vData)
  {
     try
      {
         vData.clear();
         vData.addElement(tManageCom);
         vData.addElement(tComLevel);
         vData.addElement(tStartDate);
         vData.addElement(tEndDate);
         vData.addElement(tYearMonth);
         vData.addElement(tBranchType);
         vData.add(mGlobalInput);
      }
     catch (Exception ex)
      {
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
     tManageCom=(String)cInputData.get(0);
     tComLevel=(String)cInputData.get(1);
     tStartDate=(String)cInputData.get(2);
     tEndDate=(String)cInputData.get(3);
     tYearMonth=(String)cInputData.get(4);
     tBranchType=(String)cInputData.get(5);

     mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;
     return true;
  }


  public VData getResult()
   {
     return this.mResult;
   }

   private void buildError(String szFunc, String szErrMsg)
   {
     CError cError = new CError( );

     cError.moduleName = "ReAgentXQMoneyRateBL";
     cError.functionName = szFunc;
     cError.errorMessage = szErrMsg;
     this.mErrors.addOneError(cError);
   }

   public CErrors getErrors()
   {
     return mErrors;
   }

}
