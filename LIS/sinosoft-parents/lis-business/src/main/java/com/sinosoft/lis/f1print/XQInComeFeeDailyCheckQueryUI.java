package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class XQInComeFeeDailyCheckQueryUI implements PrintService {
private static Logger logger = Logger.getLogger(XQInComeFeeDailyCheckQueryUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	public XQInComeFeeDailyCheckQueryUI() {
	}

	public static void main(String[] args) {

	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		XQInComeFeeDailyCheckQueryBL tXQInComeFeeDailyCheckBL = new XQInComeFeeDailyCheckQueryBL();

		if (!tXQInComeFeeDailyCheckBL.submitData(cInputData, "")) {
			mErrors.copyAllErrors(tXQInComeFeeDailyCheckBL.mErrors);
			return false;
		} else {
			mResult = tXQInComeFeeDailyCheckBL.getResult();
			return true;
		}

	}

	/*
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ConFeeF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
