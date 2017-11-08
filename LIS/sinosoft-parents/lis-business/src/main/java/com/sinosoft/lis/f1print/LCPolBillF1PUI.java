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

public class LCPolBillF1PUI {
private static Logger logger = Logger.getLogger(LCPolBillF1PUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private LCPolSchema mLCPolSchema = new LCPolSchema();
  private VData mResult = new VData();
  //private VData mInputData = new VData();
  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  public LCPolBillF1PUI() {
  }

  /**
传输数据的公共方法
*/
  public boolean submitData(VData cInputData, String cOperate)
  {
    try{
      if( !cOperate.equals("PRINT") && !cOperate.equals("PRINT||RE")
    		  && !cOperate.equals("PRINT||VIP") && !cOperate.equals("PRINT||LR")) {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }

      LCPolBillF1PBL tLCPolBillF1PBL = new LCPolBillF1PBL();
      logger.debug("Start LCPolBillF1P UI Submit ...");

      if( !tLCPolBillF1PBL.submitData(cInputData, cOperate) ) {
        if( tLCPolBillF1PBL.mErrors.needDealError() ) {
          mErrors.copyAllErrors(tLCPolBillF1PBL.mErrors);
          return false;
        } else {
          buildError("submitData", "LCPolBillF1PBL发生错误，但是没有提供详细的出错信息");
          return false;
        }
      } else {
        mResult = tLCPolBillF1PBL.getResult();
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

    cError.moduleName = "LCPolBillF1PUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  public static void main(String[] args) {
	  {
		GlobalInput tG = new GlobalInput();
		
	  TransferData tTransferData = new TransferData();
	  tTransferData.setNameAndValue("StartDay", "");
	  tTransferData.setNameAndValue("EndDay", "");
	  tTransferData.setNameAndValue("StartTime","");
	  tTransferData.setNameAndValue("EndTime", "");
	  tTransferData.setNameAndValue("ManageCom", "");
	  tTransferData.setNameAndValue("SaleChnl", "");
	  tTransferData.setNameAndValue("StartPolNo", "");
	  tTransferData.setNameAndValue("EndPolNo", "");

		VData tVData = new VData();
		VData mResult = new VData();
		CErrors mErrors = new CErrors();
			tVData.add(tTransferData);
	    	tVData.add(tG);
	    	LCPolBillF1PUI tLCPolBillF1PUI = new LCPolBillF1PUI();
	    	if(!tLCPolBillF1PUI.submitData(tVData,"PRINT||VIP"))
	    	{
	    		
	        }
		mResult = tLCPolBillF1PUI.getResult();
		XmlExport txmlExport = new XmlExport();
	  }
  }
}
