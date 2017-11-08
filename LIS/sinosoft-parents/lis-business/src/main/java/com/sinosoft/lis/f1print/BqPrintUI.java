package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BqPrintUI implements BusinessService {
private static Logger logger = Logger.getLogger(BqPrintUI.class);

	private VData mInputData = new VData();

	public CErrors mErrors = new CErrors();

	private VData mResult;

	


	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		BqPrintBL tBqPrintBL = new BqPrintBL();
		
		tBqPrintBL.submitData(mInputData, cOperate);
		if (tBqPrintBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tBqPrintBL.mErrors);
			return false;
		}
		mResult = tBqPrintBL.getResult();
		return true;
	}

}
