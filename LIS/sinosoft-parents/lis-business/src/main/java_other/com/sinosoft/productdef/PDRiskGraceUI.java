

package com.sinosoft.productdef;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PDRiskGraceUI implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData data, String Operater) {
		
		PDRiskGraceBL tPDRiskGraceBL = new PDRiskGraceBL();
		tPDRiskGraceBL.submitData(data, Operater);
		if(tPDRiskGraceBL.getErrors().needDealError()){
			this.mErrors.copyAllErrors(tPDRiskGraceBL.getErrors());
			return false;
		}
		this.mResult.clear();
		this.mResult = tPDRiskGraceBL.getResult();
		
		return true;
	}

}
