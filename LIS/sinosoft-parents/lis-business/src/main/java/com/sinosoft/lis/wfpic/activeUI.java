package com.sinosoft.lis.wfpic;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class activeUI implements BusinessService
{
private static Logger logger = Logger.getLogger(activeUI.class);

    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    
	public CErrors getErrors() {
		 
		return mErrors;
	}

	public VData getResult() {
	 
		return mResult;
	}

	public boolean submitData(VData vData, String Operater)
	{
		TransferData tTransferData=(TransferData)vData.getObjectByObjectName("TransferData",0);
	    active tactive=new active();
	    String Action=(String)tTransferData.getValueByName("Action");
	    String Para=(String)tTransferData.getValueByName("Para");
	    String ActionId=(String)tTransferData.getValueByName("ActionId");
	    tactive.setAction(Action);
	    tactive.setActionId(ActionId);
	    tactive.setPara(Para);
	    if(!tactive.doAction())
	    {
            CError tError = new CError();
            tError.moduleName = "activeUI";
            tError.functionName = "submitData";
            tError.errorMessage = "执行失败！";
            this.mErrors.addOneError(tError);
            return false;	    	
	    }
 
	    mResult.add(tactive.getResult());
		return true;
	}

}
