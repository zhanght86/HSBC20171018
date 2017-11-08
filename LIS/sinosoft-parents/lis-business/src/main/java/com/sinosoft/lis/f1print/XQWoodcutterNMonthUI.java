package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class XQWoodcutterNMonthUI implements PrintService {
private static Logger logger = Logger.getLogger(XQWoodcutterNMonthUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();

	public XQWoodcutterNMonthUI() {
	}

	public static void main(String[] args) {
		LOPRTManagerSchema tmLOPRTManagerSchema = new LOPRTManagerSchema();
		tmLOPRTManagerSchema.setPrtSeq("810320000090373");
		tmLOPRTManagerSchema.setStandbyFlag4("3103200026512");
		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		// LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";

		tVData.addElement(tmLOPRTManagerSchema);
		tVData.add(tG);
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

		XQWoodcutterNMonthBL XQWoodcutterNMonthBL = new XQWoodcutterNMonthBL();
		logger.debug("Start ConFeeF1P UI Submit ...");

		if (!XQWoodcutterNMonthBL.submitData(cInputData, "PRINT")) {

			if (XQWoodcutterNMonthBL.mErrors.needDealError()) {

				mErrors.copyAllErrors(XQWoodcutterNMonthBL.mErrors);
				return false;
			} else {

				buildError("submitData", "XQWoodcutterNMonthBL，但是没有提供详细的出错信息");
				return false;
			}

		} else {
			mResult = XQWoodcutterNMonthBL.getResult();
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
