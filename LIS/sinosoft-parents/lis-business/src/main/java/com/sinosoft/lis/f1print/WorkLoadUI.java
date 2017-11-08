package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class WorkLoadUI {
	private static Logger logger = Logger.getLogger(UWCheckUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// private VData mInputData = new VData();
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mRiskCode = "";
	private String mRiskFlag = "";
	private String mDay[] = null;
	private String mManageCom = "";
	private String mDefineCode = "";

	public WorkLoadUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
			WorkLoadBL tWorkLoadBL = new WorkLoadBL();
			logger.debug("Start UWCheckBL Submit ...");

			if (!tWorkLoadBL.submitData(cInputData, cOperate)) {
				if (tWorkLoadBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tWorkLoadBL.mErrors);
					return false;
				} else {
					buildError("submitData", "tUWCheckBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tWorkLoadBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "tRiskClaimCheckUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) { // 全局变量
		mDay = (String[]) cInputData.get(1);
		mManageCom = (String) cInputData.get(2);
		mDefineCode = (String) cInputData.get(3);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mDefineCode == "") {
			buildError("mDefineCode", "没有得到足够的信息！");
			return false;
		}
		if (mManageCom == "") {
			buildError("ManageCom", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RiskClaimCheckUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
