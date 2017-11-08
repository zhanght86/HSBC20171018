package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpPayForNoticeUI implements PrintService {
private static Logger logger = Logger.getLogger(GrpPayForNoticeUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public GrpPayForNoticeUI() {
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

		GrpPayForNoticeBL tGrpPayForNoticeBL = new GrpPayForNoticeBL();
		logger.debug("Start ConFeeF1P UI Submit ...");

		if (!tGrpPayForNoticeBL.submitData(cInputData, cOperate)) {
			if (tGrpPayForNoticeBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tGrpPayForNoticeBL.mErrors);
				return false;
			} else {
				buildError("submitData", "ConFeeF1PBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tGrpPayForNoticeBL.getResult();
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

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();

		VData tVData = new VData();
		LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86110000";

		tTransferData.setNameAndValue("AppntNo", "0000002320");
		tTransferData.setNameAndValue("AppntName", "南天");

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		tLOPRTManagerSchema.setPrtSeq("8100000000216");
		mLOPRTManagerSet.add(tLOPRTManagerSchema);
		tVData.addElement(tTransferData);
		tVData.addElement(mLOPRTManagerSet);
		tVData.add(tG);
		GrpPayForNoticeUI tGrpPayForNoticeUI = new GrpPayForNoticeUI();
		tGrpPayForNoticeUI.submitData(tVData, "CONFIRM");

	}

}
