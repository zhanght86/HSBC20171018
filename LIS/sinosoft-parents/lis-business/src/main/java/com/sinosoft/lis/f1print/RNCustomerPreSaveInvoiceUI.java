package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RNCustomerPreSaveInvoiceUI {
private static Logger logger = Logger.getLogger(RNCustomerPreSaveInvoiceUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	public RNCustomerPreSaveInvoiceUI() {
	}

	public static void main(String[] args) {
		String mCustomerNo = "";
		String mCustomerName = "";
		String mPayMoney = "";
		String mPayMode = "";
		String mTempFeeNo = "";
		String mActuGetNo = "";
		String mPayDate = "";
		String mCollector = "";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CustomerNo", "0000109410");
		tTransferData.setNameAndValue("CustomerName", "北京丽达电器维修中心");
		tTransferData.setNameAndValue("PayMoney", "2945");
		tTransferData.setNameAndValue("PayMode", "支票");
		tTransferData.setNameAndValue("TempFeeNo", "370210000008601");
		tTransferData.setNameAndValue("ActuGetNo", "3602100015096");
		tTransferData.setNameAndValue("PayDate", "0000109410");
		tTransferData.setNameAndValue("Collector", "");
		logger.debug("mCustomerNo:" + mCustomerNo);
		logger.debug("mCustomerName:" + mCustomerName);
		logger.debug("mPayMoney:" + mPayMoney);
		logger.debug("mPayMode:" + mPayMode);
		logger.debug("mTempFeeNo:" + mTempFeeNo);
		logger.debug("mActuGetNo" + mActuGetNo);
		logger.debug("mPayDate:" + mPayDate);
		logger.debug("mCollector:" + mCollector);
		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();

		tVData.addElement(tTransferData);
		tVData.add(tG);
		RNCustomerPreSaveInvoiceUI u = new RNCustomerPreSaveInvoiceUI();
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

		RNCustomerPreSaveInvoiceBL tRNCustomerPreSaveInvoiceBL = new RNCustomerPreSaveInvoiceBL();
		logger.debug("Start RNCustomerPreSaveInvoiceBL Submit ...");

		if (!tRNCustomerPreSaveInvoiceBL.submitData(cInputData, "PRINT")) {
			if (tRNCustomerPreSaveInvoiceBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tRNCustomerPreSaveInvoiceBL.mErrors);
				return false;
			} else {
				buildError("submitData",
						"RNCustomerPreSaveInvoiceBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tRNCustomerPreSaveInvoiceBL.getResult();
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

		cError.moduleName = "RNCustomerPreSaveInvoiceUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
