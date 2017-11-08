package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;


import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class AdvancePremQueryUI {
private static Logger logger = Logger.getLogger(AdvancePremQueryUI.class);

	 public CErrors mErrors=new CErrors();
	// private VData mResult = new VData();
	 private GlobalInput mGlobalInput = new GlobalInput() ;
	 private String tManageCom="";
	 private String tComLevel="";
	 private String tdate="";
	 private  String tBranchType="";
	 
	 public AdvancePremQueryUI()
	  {
	  }	 
	 public boolean submitData(VData cInputData, String cOperate)
	 {

	     // 准备传往后台的数据
	      VData vData = new VData();
	      vData = (VData) cInputData.clone();
          AdvancePremQueryBL tAdvancePremQueryBL= new AdvancePremQueryBL();
     
	      logger.debug("Start RevancePremQueryBL Submit ...");

	     if( !tAdvancePremQueryBL.submitData(vData, cOperate) )
	      {
	    	 //logger.debug("111111111");
	        if( tAdvancePremQueryBL.mErrors.needDealError() )
	         {
	             mErrors.copyAllErrors(tAdvancePremQueryBL.mErrors);
	             return false;
	         }
	        else
	         {
	              buildError("submitData", "ReAdvancePremQueryBL发生错误，但是没有提供详细的出错信息");
	             return false;
	         }
	      }
    // logger.debug("33333333333"); 
 return true;
}
	 private boolean prepareOutputData(VData vData)
	  {
	     try
	      {
	         vData.clear();
	         vData.addElement(tManageCom);
	         vData.addElement(tComLevel);
	         vData.addElement(tdate);
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
	 
	 private boolean dealData()
	 {
	   return true;
	  } 
	 private boolean getInputData(VData cInputData)
	 {
	     //全局变量
	     tManageCom=(String)cInputData.get(0);
	     tComLevel=(String)cInputData.get(1);
	     tdate=(String)cInputData.get(2);
	     tBranchType=(String)cInputData.get(3);
	     
	    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;
	     
	   // logger.debug(tManageCom);
	    //logger.debug(tComLevel);
	    //logger.debug( tdate);
	   //logger.debug(tBranchType);
	    
	    
	    
	    return true;
	  }
	 

	   private void buildError(String szFunc, String szErrMsg)
	   {
	     CError cError = new CError( );

	     cError.moduleName = "AgentXQMoneyRateBL";
	     cError.functionName = szFunc;
	     cError.errorMessage = szErrMsg;
	     this.mErrors.addOneError(cError);
	   }

	   public CErrors getErrors()
	   {
	     return mErrors;
	   }

}

