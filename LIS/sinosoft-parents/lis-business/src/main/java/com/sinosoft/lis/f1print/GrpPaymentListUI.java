//GrpPaymentListUI.java

package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpPaymentListUI {
private static Logger logger = Logger.getLogger(GrpPaymentListUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RNContAccGetPrintUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串 sf dfe");
			return false;
		}
		GrpPaymentListBL tGrpPaymentListBL = new GrpPaymentListBL();
		logger.debug("Start GrpPaymentListBL Submit ...");

		if (!tGrpPaymentListBL.submitData(cInputData, "PRINT")) {

			if (tGrpPaymentListBL.mErrors.needDealError()) {

				mErrors.copyAllErrors(tGrpPaymentListBL.mErrors);
				return false;
			} else {

				buildError("submitData", "tGrpPaymentListBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tGrpPaymentListBL.getResult();
			return true;
		}
	}

}
