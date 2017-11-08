package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class XQChargeRollBackPrintUI implements PrintService {
private static Logger logger = Logger.getLogger(XQChargeRollBackPrintUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	// private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// private LOPRTManagerSchema mLOPRTManagerSchema = new
	// LOPRTManagerSchema();

	public XQChargeRollBackPrintUI() {
	}

	/**
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("cOperate:" + cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("BATCHCONFIRM")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		XQChargeRollBackPrintBL tXQChargeRollBackPrintBL = new XQChargeRollBackPrintBL();
		logger.debug("Start ConFeeF1P UI Submit ...");

		if (!tXQChargeRollBackPrintBL.submitData(cInputData, cOperate)) {
			if (tXQChargeRollBackPrintBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tXQChargeRollBackPrintBL.mErrors);
				return false;
			} else {
				buildError("submitData", "ConFeeF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tXQChargeRollBackPrintBL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mTransferData);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
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

		cError.moduleName = "ConFeeF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String arg[])

	{
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", "NJ010227011002120");
		GlobalInput tGI = new GlobalInput();
		tVData.addElement(tGI);
		tVData.addElement(tTransferData);

		XQChargeRollBackPrintUI tPubUI = new XQChargeRollBackPrintUI();

		if (!tPubUI.submitData(tVData, "PRINT")) {
			logger.debug("shibai");
		} else {
			logger.debug("cehng");
			tPubUI.getResult();
		}

	}

}
