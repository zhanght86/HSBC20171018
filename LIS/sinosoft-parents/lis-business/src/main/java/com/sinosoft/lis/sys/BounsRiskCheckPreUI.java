package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
/**
 * <p>Title: lis</p>
 * <p>Description: 个单分红计算 - 分红险种数据准备UI </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class BounsRiskCheckPreUI implements BusinessService{
private static Logger logger = Logger.getLogger(BounsRiskCheckPreUI.class);
  public CErrors mErrors = new CErrors();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();
  private GlobalInput mGlobalInput = new GlobalInput();

  public BounsRiskCheckPreUI() {
  }

  public boolean submitData(VData sInputData,String tOperator)
  {
    try {
      BonusRiskCheckPreBL tBonusRiskCheckPreBL = new BonusRiskCheckPreBL();
      if(!tBonusRiskCheckPreBL.submitData(sInputData,tOperator))
      {
        this.mErrors.copyAllErrors(tBonusRiskCheckPreBL.mErrors);
        return false;
      }
    }
    catch (Exception ex) {
      this.dealError("",ex.toString());
      return false;
    }
     return true;
  }
  
  /**
   * 错误容器
   * @param FuncName String
   * @param ErrMsg String
   */
  private void dealError(String FuncName, String ErrMsg) {
    CError tError = new CError();
    tError.moduleName = "NAAgentAccreditAutoRun";
    tError.functionName = FuncName;
    tError.errorMessage = ErrMsg;
    this.mErrors.addOneError(tError);
  }

  public static void main(String[] args) {
    BounsRiskCheckPreUI BounsRiskCheckPreUI1 = new BounsRiskCheckPreUI();
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}

public VData getResult() {
	// TODO Auto-generated method stub
	return null;
}

}
