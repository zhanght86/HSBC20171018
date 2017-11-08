package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.LOAccUnitPriceSet;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CErrors;

public class LOAccUnitPriceBL {
private static Logger logger = Logger.getLogger(LOAccUnitPriceBL.class);
   // private  VData mInputData=new VData();
    private  MMap  map = new MMap();
    public LOAccUnitPriceBL() {
    }
    public CErrors mErrors=new CErrors();

    public boolean submitData(VData tInputData,String operate)
   {

       PubSubmit tPubSubmit = new PubSubmit();
       if (!tPubSubmit.submitData(tInputData, operate)) {
           this.mErrors.copyAllErrors(tPubSubmit.mErrors);
           CError tError = new CError();
           tError.moduleName = "LCInsureAccClassForCompareBL";
           tError.functionName = "submitData";
           tError.errorMessage = "数据提交失败!";
           this.mErrors.addOneError(tError);
           return false;
       }

       return true;
   }

}
