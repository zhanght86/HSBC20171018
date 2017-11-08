package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-04-16
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class CBydqfqdUI {
private static Logger logger = Logger.getLogger(CBydqfqdUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strManageCom;
	private String strPrtDate;

	public CBydqfqdUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 只对打印操作进行支持
			if (!cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			if (!getInputData(cInputData)) {
				return false;
			}
			VData vData = new VData();
			if (!prepareOutputData(vData)) {
				return false;
			}

			CBydqfqdBL tCBydqfqdBL = new CBydqfqdBL();
			logger.debug("Start tCBydqfqd UI Submit ...");
			if (!tCBydqfqdBL.submitData(vData, cOperate)) {
				if (tCBydqfqdBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCBydqfqdBL.mErrors);
					return false;
				} else {
					buildError("submitData", "tCBydqfqdBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tCBydqfqdBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "CBgrqfqdUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strManageCom);
		logger.debug("UI prepareOutputData管理机构：" + strManageCom);
		vData.add(strPrtDate);
		logger.debug("UI prepareOutputData打印日期：" + strPrtDate);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strManageCom = (String) cInputData.get(0);
		logger.debug("UI getInputData管理机构：" + strManageCom);
		strPrtDate = (String) cInputData.get(1);
		logger.debug("UI getInputData打印日期：" + strPrtDate);
		return true;

	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "tCBydqfqdUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String ManageCom = "";
		String PrtDate = "";
		VData tVData = new VData();
		GlobalInput tg = new GlobalInput();
		tg.ManageCom = "8651";
		tg.ComCode = "86510000";
		tg.Operator = "110001";
		tVData.addElement(ManageCom);
		tVData.addElement(PrtDate);
		tVData.addElement(tg);
		// tVData.addElement("8300090000000021");

		CBydqfqdUI tCBydqfqdUI = new CBydqfqdUI();
		tCBydqfqdUI.submitData(tVData, "PRINT");
	}
}
