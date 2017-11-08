package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: lis</p>
 * <p>Description: 个单分红计算 - 分红险种数据准备UI </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class ComGroupConfigUI {
private static Logger logger = Logger.getLogger(ComGroupConfigUI.class);
  public CErrors mErrors = new CErrors();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();
  private GlobalInput mGlobalInput = new GlobalInput();

  public ComGroupConfigUI() {
  }

  public boolean submitData(VData sInputData,String tOperator)
  {
    try {
    	ComGroupConfigBL tComGroupConfigBL = new ComGroupConfigBL();
      if(!tComGroupConfigBL.submitData(sInputData,tOperator))
      {
        this.mErrors.copyAllErrors(tComGroupConfigBL.mErrors);
        return false;
      }
    }
    catch (Exception ex) {
      CError.buildErr(this,ex.toString());
      return false;
    }
     return true;
  }

  public static void main(String[] args) {
   
  }
}
