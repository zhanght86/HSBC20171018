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

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ReportLisUI {
private static Logger logger = Logger.getLogger(ReportLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;
	private String strPrintDate;

	public ReportLisUI() {
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

			ReportLisBL tReportLisBL = new ReportLisBL();
			logger.debug("Start ReportLis UI Submit ...");
			if (!tReportLisBL.submitData(vData, cOperate)) {
				if (tReportLisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tReportLisBL.mErrors);
					return false;
				} else {
					buildError("submitData", "ReportLisBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tReportLisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "ReportLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strStartDate);
		vData.add(strEndDate);
		vData.add(strPrintDate);
		// vData.add(strAgentGroup);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
		// strAgentGroup= (String) cInputData.get(2);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ReportLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
