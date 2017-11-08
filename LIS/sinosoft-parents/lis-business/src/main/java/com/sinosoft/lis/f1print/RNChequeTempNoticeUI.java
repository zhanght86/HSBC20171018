package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RNChequeTempNoticeUI {
private static Logger logger = Logger.getLogger(RNChequeTempNoticeUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	public RNChequeTempNoticeUI() {
	}

	public static void main(String[] args) {
		String mChequeNo = "";
		String mOtherNo = "";
		String mPayMoney = "";
		String mAccName = "";
		String mAgentName = "";
		String mAgentCode = "";
		String mMakeDate = "";
		String mManageComName = "";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ChequeNo", "9876543210987654");
		tTransferData.setNameAndValue("OtherNo", "56016000000000");
		tTransferData.setNameAndValue("PayMoney", "50000.00");
		tTransferData.setNameAndValue("AccName", "张三那厮");
		tTransferData.setNameAndValue("AgentName", "周云芳");
		tTransferData.setNameAndValue("AgentCode", "032A7300");
		tTransferData.setNameAndValue("MakeDate", "2005-12-22");
		tTransferData.setNameAndValue("ManageComName", "苏州本部");
		logger.debug("mChequeNo:" + mChequeNo);
		logger.debug("mOtherNo:" + mOtherNo);
		logger.debug("mPayMoney:" + mPayMoney);
		logger.debug("mAccName:" + mAccName);
		logger.debug("mAgentName:" + mAgentName);
		logger.debug("mAgentCode:" + mAgentCode);
		logger.debug("mMakeDate:" + mMakeDate);
		logger.debug("mManageComName:" + mManageComName);
		GlobalInput g = new GlobalInput();
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();

		tVData.addElement(tTransferData);
		tVData.add(tG);
		RNChequeTempNoticeUI u = new RNChequeTempNoticeUI();
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

		RNChequeTempNoticeBL tRNChequeTempNoticeBL = new RNChequeTempNoticeBL();
		logger.debug("Start RNChequeTempNoticeUI Submit ...");

		if (!tRNChequeTempNoticeBL.submitData(cInputData, "PRINT")) {
			if (tRNChequeTempNoticeBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tRNChequeTempNoticeBL.mErrors);
				return false;
			} else {
				buildError("submitData",
						"RNChequeTempNoticeBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tRNChequeTempNoticeBL.getResult();
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

		cError.moduleName = "RNChequeTempNoticeUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
