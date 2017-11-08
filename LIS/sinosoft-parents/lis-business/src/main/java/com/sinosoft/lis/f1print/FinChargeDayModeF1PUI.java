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

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class FinChargeDayModeF1PUI implements BusinessService{
private static Logger logger = Logger.getLogger(FinChargeDayModeF1PUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
      public CErrors mErrors=new CErrors();

      private VData mResult = new VData();
      //private VData mInputData = new VData();
      //业务处理相关变量
       /** 全局数据 */
      private GlobalInput mGlobalInput =new GlobalInput() ;
      private String mDay[]=null;


  public FinChargeDayModeF1PUI() {
  }

  /**
传输数据的公共方法
*/
  public boolean submitData(VData cInputData, String cOperate)
  {
    try{
      if( !cOperate.equals("PRINT") ) {
        buildError("submitData", "不支持的操作字符串");
        return false;
      }

      // 得到外部传入的数据，将数据备份到本类中
      if( !getInputData(cInputData) ) {
        return false;
      }

      // 进行业务处理
      if( !dealData() ) {
        return false;
      }

      // 准备传往后台的数据
      VData vData = new VData();

      if( !prepareOutputData(vData) ) {
        return false;
      }

      FinChargeDayModeF1PBL tFinChargeDayModeF1PBL = new FinChargeDayModeF1PBL();
      logger.debug("Start FinChargeDayModeF1P UI Submit ...");

      if( !tFinChargeDayModeF1PBL.submitData(vData, cOperate) ) {
        if( tFinChargeDayModeF1PBL.mErrors.needDealError() ) {
          mErrors.copyAllErrors(tFinChargeDayModeF1PBL.mErrors);
          return false;
        } else {
          buildError("submitData", "FinChargeDayModeF1PBL发生错误，但是没有提供详细的出错信息");
          return false;
        }
      } else {
        mResult = tFinChargeDayModeF1PBL.getResult();
        return true;
      }
    }catch(Exception e){
      e.printStackTrace();
      CError cError = new CError( );
      cError.moduleName = "FinChargeDayModeF1PUI";
      cError.functionName = "submit";
      cError.errorMessage = e.toString();
      mErrors.addOneError(cError);
      return false;
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

      vData.add(mDay);
      vData.add(mGlobalInput);
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
    mDay=(String[])cInputData.get(0);
    //mDay=(String[])cInputData.getObjectByObjectName("String[]",0);
    mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));

    logger.debug("start");
    logger.debug("end");
    logger.debug(mGlobalInput.Operator);
    logger.debug(mGlobalInput.ManageCom);

    if( mGlobalInput==null ) {
      buildError("getInputData", "没有得到足够的信息！");
      return false;
    }

    return true;
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


  public static void main(String[] args) {
    FinChargeDayModeF1PUI finChargeDayModeF1PUI1 = new FinChargeDayModeF1PUI();
  }


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
