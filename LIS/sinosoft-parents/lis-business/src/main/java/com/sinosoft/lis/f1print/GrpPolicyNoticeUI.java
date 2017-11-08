package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpPolicyNoticeUI implements PrintService {
private static Logger logger = Logger.getLogger(GrpPolicyNoticeUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpPolicyNoticeUI() {
	}

	public static void main(String[] args) {

		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		// LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "862100";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", "");
		tTransferData.setNameAndValue("ContType", "");
		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		GrpPolicyNoticeUI tGrpPolicyNoticeUI = new GrpPolicyNoticeUI();

		XQWoodcutterNoticeUI u = new XQWoodcutterNoticeUI();
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
		logger.debug("cOperate:" + cOperate);
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")
				&& !cOperate.equals("BATCHCONFIRM")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		GrpPolicyNoticeBL tGrpPolicyNoticeBL = new GrpPolicyNoticeBL();
		logger.debug("Start ConFeeF1P UI Submit ...");

		if (!tGrpPolicyNoticeBL.submitData(cInputData, "PRINT")) {

			if (tGrpPolicyNoticeBL.mErrors.needDealError()) {

				mErrors.copyAllErrors(tGrpPolicyNoticeBL.mErrors);
				return false;
			} else {

				buildError("submitData", "XQWoodcutterNMonthBL，但是没有提供详细的出错信息");
				return false;
			}

		} else {
			mResult = tGrpPolicyNoticeBL.getResult();
			return true;
		}

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

}
