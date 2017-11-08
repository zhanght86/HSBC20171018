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

public class ProductSaleConfigUI {
private static Logger logger = Logger.getLogger(ProductSaleConfigUI.class);
  public CErrors mErrors = new CErrors();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();
  private GlobalInput mGlobalInput = new GlobalInput();

  public ProductSaleConfigUI() {
  }

  public boolean submitData(VData sInputData,String tOperator)
  {
    try {
    	ProductSaleConfigBL tProductSaleConfigBL = new ProductSaleConfigBL();
      if(!tProductSaleConfigBL.submitData(sInputData,tOperator))
      {
        this.mErrors.copyAllErrors(tProductSaleConfigBL.mErrors);
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
