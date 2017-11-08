

package com.sinosoft.lis.reinsure.test;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PrintUI {
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public boolean submitData(VData cInputData, String cOperater) {
		PrintBL tPrintBL = new PrintBL();
		if (!tPrintBL.submitData(cInputData, "")) {
			if (tPrintBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tPrintBL.mErrors);
			} else {
				buildError("submitData", "PrintBL发生错误，但是没有提供详细信息！");
			}
			return false;
		}
		mResult = tPrintBL.getResult();
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
