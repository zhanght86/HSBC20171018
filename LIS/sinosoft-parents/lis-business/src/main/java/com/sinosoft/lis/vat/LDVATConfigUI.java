package com.sinosoft.lis.vat;

import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDVATConfigUI implements BusinessService{
	private static Logger logger = Logger.getLogger(LDVATConfigUI.class);

    private static Logger log = Logger.getLogger("com.sinosoft.lis.vat");
    public LDVATConfigUI()
    {
    }

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();



    public boolean submitData(VData cInputData, String cOperate)
    {
    	LDVATConfigBL tLDVATConfigBL = new LDVATConfigBL();

        //如果有需要处理的错误，则返回
        if (!tLDVATConfigBL.submitData(cInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLDVATConfigBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDVATConfigUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        log.debug("End PWProcess UI Submit...");
        this.mResult.clear();
        this.mResult = tLDVATConfigBL.getResult();

        return true;
    }

 
    public VData getResult()
    {
        return this.mResult;
    }
	public CErrors getErrors() {
		return mErrors;
	}

}
