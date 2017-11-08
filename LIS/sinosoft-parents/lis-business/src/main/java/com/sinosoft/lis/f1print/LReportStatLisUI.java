package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * modify by zhangxing
 * @version 1.0
 * @date 2003-04-16
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LReportStatLisUI {
private static Logger logger = Logger.getLogger(LReportStatLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;

	public LReportStatLisUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 只对打印操作进行支持
			logger.debug("opertate=" + cOperate);
			if (!cOperate.equals("PRINT") && !cOperate.equals("PRINT2")) {
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

			LReportStatLisBL tLReportStatLisBL = new LReportStatLisBL();
			logger.debug("Start LReportStatLis UI Submit ...");
			if (!tLReportStatLisBL.submitData(vData, cOperate)) {
				if (tLReportStatLisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tLReportStatLisBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"LReportStatLisBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tLReportStatLisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "LReportStatLisUI";
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

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LReportStatLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String tStrMngCom = "863200";
		String tIssueDate = "2005-09-07";
		String tPrintDate = "2005-09-11";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tPrintDate);

		LReportStatLisUI tLReportStatLisUI = new LReportStatLisUI();
		tLReportStatLisUI.submitData(tVData, "PRINT");

	}
}
