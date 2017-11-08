package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.io.*;

public class LCGrpPolBillF1PUI {
private static Logger logger = Logger.getLogger(LCGrpPolBillF1PUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
        public CErrors mErrors=new CErrors();
        private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
        private VData mResult = new VData();
        //private VData mInputData = new VData();
        //业务处理相关变量
         /** 全局数据 */
        private GlobalInput mGlobalInput =new GlobalInput() ;
        private String mDay[]=null;
        private String mTime[] = null;
        private String AgentGroup="";

  public LCGrpPolBillF1PUI() {
	  
	  
  }

  /**
传输数据的公共方法
*/
  public boolean submitData(VData cInputData, String cOperate)
  {
	 
	  
	    try{
	        if( !cOperate.equals("PRINT") && !cOperate.equals("PRINT||LR")) {
	          buildError("submitData", "不支持的操作字符串");
	          return false;
	        }

	        LCGrpPolBillF1PBL tLCGrpPolBillF1PBL = new LCGrpPolBillF1PBL();
	        logger.debug("Start LCPolBillF1P UI Submit ...");

	        if( !tLCGrpPolBillF1PBL.submitData(cInputData, cOperate) ) {
	          if( tLCGrpPolBillF1PBL.mErrors.needDealError() ) {
	            mErrors.copyAllErrors(tLCGrpPolBillF1PBL.mErrors);
	            return false;
	          } else {
	            buildError("submitData", "LCGrpPolBillF1PBL发生错误，但是没有提供详细的出错信息");
	            return false;
	          }
	        } else {
	          mResult = tLCGrpPolBillF1PBL.getResult();
	          return true;
	        }

	      }catch(Exception e){
	        e.printStackTrace();
	        CError cError = new CError( );
	        cError.moduleName = "LCPolBillF1PUI";
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

    cError.moduleName = "LCGrpPolBillF1PUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  public static void main(String[] args) {
    String mDay[]=new String[2];
    String mTime[] = new String[2];
    mDay[0] = "2002-1-1";
    mDay[1] = "2004-1-1";
    mTime[0] = "00:00:00";
    mTime[1] = "23:59:59";
    LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
    tLCGrpPolSchema.setManageCom("86");
    GlobalInput tG = new GlobalInput();
    tG.Operator="lis";
    VData tVData = new VData();
    tVData.addElement(mDay);
    tVData.addElement(tLCGrpPolSchema);
    tVData.addElement(tG);
    LCGrpPolBillF1PUI tLCGrpPolBillF1PUI = new LCGrpPolBillF1PUI();
    tLCGrpPolBillF1PUI.submitData(tVData,"PRINT");
  }
}
