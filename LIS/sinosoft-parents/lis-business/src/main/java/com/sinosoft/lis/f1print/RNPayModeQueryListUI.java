package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RNPayModeQueryListUI {
private static Logger logger = Logger.getLogger(RNPayModeQueryListUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	public RNPayModeQueryListUI() {
	}

	public static void main(String[] args) {
		String mStartDate = "";
		String mEndDate = "";
		String mManageCom = "";
		String mSaleChnl = "";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-12-01");
		tTransferData.setNameAndValue("EndDate", "2005-12-10");
		tTransferData.setNameAndValue("ManageCom", "865100");
		tTransferData.setNameAndValue("SaleChnl", "1");
		logger.debug("mStartDate:" + mStartDate);
		logger.debug("mEndDate:" + mEndDate);
		logger.debug("mManageCom:" + mManageCom);
		logger.debug("mSaleChnl:" + mSaleChnl);
		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();

		tVData.addElement(tTransferData);
		tVData.add(tG);
		RNPayModeQueryListUI u = new RNPayModeQueryListUI();
		u.submitData(tVData, "PRINT");
		VData result = new VData();
		result = u.getResult();
	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("BATCHCONFIRM")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		RNPayModeQueryListBL tRNPayModeQueryListBL = new RNPayModeQueryListBL();
		logger.debug("Start RNPayModeQueryListUI Submit ...");

		if (!tRNPayModeQueryListBL.submitData(cInputData, "PRINT")) {

			if (tRNPayModeQueryListBL.mErrors.needDealError()) {

				mErrors.copyAllErrors(tRNPayModeQueryListBL.mErrors);
				return false;
			} else {

				buildError("submitData",
						"RNPayModeQueryListBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tRNPayModeQueryListBL.getResult();
			return true;
		}
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

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RNPayModeQueryListUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

}
