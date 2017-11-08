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

public class WorkMissionPrintUI {
private static Logger logger = Logger.getLogger(WorkMissionPrintUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	String strMngCom;
	String strStartDate;
	String strEndDate;
	String strSaleChnl;
	String strOperation;

	public WorkMissionPrintUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
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

			WorkMissionPrintBL tWorkMissionPrintBL = new WorkMissionPrintBL();
			logger.debug("Start PNoticeBill UI Submit ...");
			if (!tWorkMissionPrintBL.submitData(vData, cOperate)) {
				if (tWorkMissionPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tWorkMissionPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData", "PNoticeBillBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tWorkMissionPrintBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "PEfficiencyUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.addElement(strMngCom);
		vData.addElement(strStartDate);
		vData.addElement(strEndDate);
		vData.addElement(strSaleChnl);
		vData.addElement(strOperation);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		// strAgentCode = (String)cInputData.get(1);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);
		// strOperation = (String)cInputData.get(4);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FinChargeDayModeF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		WorkMissionPrintUI tWorkMissionPrintUI = new WorkMissionPrintUI();

		VData tVData = new VData();
		tVData.addElement("8632");
		tVData.addElement("2005-09-06");
		tVData.addElement("2005-09-10");
		tVData.addElement("1");

		tWorkMissionPrintUI.submitData(tVData, "PRINT");

	}
}
