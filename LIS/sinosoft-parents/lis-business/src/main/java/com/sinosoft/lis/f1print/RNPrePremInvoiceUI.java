package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RNPrePremInvoiceUI {
private static Logger logger = Logger.getLogger(RNPrePremInvoiceUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	public RNPrePremInvoiceUI() {
	}

	public static void main(String[] args) {
		String mAppntName = "";
		String mPayDate = "";
		String mContNo = "";
		String mTempFeeNo = "";
		String mPayMoney = "";
		String mOperator = "";
		String mIDNo = "";
		String mPayer = "";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AppntName", "张彦伟");
		tTransferData.setNameAndValue("PayDate", "2003-10-21");
		tTransferData.setNameAndValue("ContNo", "HB010227011001107");
		tTransferData.setNameAndValue("TempFeeNo", "8632332005330003957");
		tTransferData.setNameAndValue("PayMoney", "1170");
		tTransferData.setNameAndValue("Operator", "02");
		tTransferData.setNameAndValue("IDNo", "132325197209072811");
		tTransferData.setNameAndValue("Payer", "张彦伟");
		logger.debug("mAppntName:" + mAppntName);
		logger.debug("mPayDate:" + mPayDate);
		logger.debug("mContNo:" + mContNo);
		logger.debug("mTempFeeNo:" + mTempFeeNo);
		logger.debug("mPayMoney:" + mPayMoney);
		logger.debug("mOperator:" + mOperator);
		logger.debug("mIDNo:" + mIDNo);
		logger.debug("mPayer:" + mPayer);
		// logger.debug("mManageComName:" + mManageComName);
		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();

		tVData.addElement(tTransferData);
		tVData.add(tG);
		RNPrePremInvoiceUI u = new RNPrePremInvoiceUI();
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

		RNPrePremInvoiceBL tRNPrePremInvoiceBL = new RNPrePremInvoiceBL();
		logger.debug("Start RNPrePremInvoiceUI Submit ...");

		if (!tRNPrePremInvoiceBL.submitData(cInputData, "PRINT")) {
			if (tRNPrePremInvoiceBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tRNPrePremInvoiceBL.mErrors);
				return false;
			} else {
				buildError("submitData", "RNPrePremInvoiceBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tRNPrePremInvoiceBL.getResult();
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

		cError.moduleName = "RNPrePremInvoiceUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
