package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BqFileNameUI implements BusinessService {
private static Logger logger = Logger.getLogger(BqFileNameUI.class);

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
		BqFileNameBL tBqFileNameBL = new BqFileNameBL();
		
		tBqFileNameBL.submitData(mInputData, cOperate);
		if (tBqFileNameBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tBqFileNameBL.mErrors);
			return false;
		}
		mResult = tBqFileNameBL.getResult();
		return true;
	}

}
