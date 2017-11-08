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

public class ChnlListPrintUI {
private static Logger logger = Logger.getLogger(ChnlListPrintUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	String strMngCom;
	String strStartDate;
	String strEndDate;
	String tUWStatType;
	String tChnlType;

	public ChnlListPrintUI() {
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

			ChnlListPrintBL tChnlListPrintBL = new ChnlListPrintBL();
			logger.debug("Start CBZKStatic UI Submit ...");
			if (!tChnlListPrintBL.submitData(vData, cOperate)) {
				if (tChnlListPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tChnlListPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData", "UWQualityBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tChnlListPrintBL.getResult();
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
		vData.addElement(tUWStatType);
		vData.addElement(tChnlType);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		// strAgentCode = (String)cInputData.get(1);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		tUWStatType = (String) cInputData.get(3);
		tChnlType = (String) cInputData.get(4);

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
		ChnlListPrintUI tChnlListPrintUI = new ChnlListPrintUI();

		VData tVData = new VData();
		tVData.addElement("86110000");
		tVData.addElement("2005-08-27");
		tVData.addElement("2005-08-28");
		tVData.addElement("2");
		tVData.addElement("1");

		tChnlListPrintUI.submitData(tVData, "PRINT");

	}
}
