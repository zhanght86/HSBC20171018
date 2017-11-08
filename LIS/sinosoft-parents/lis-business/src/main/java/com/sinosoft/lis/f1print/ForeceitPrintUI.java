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

public class ForeceitPrintUI {
private static Logger logger = Logger.getLogger(ForeceitPrintUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strFeeDate;
	private String strEndDate;
	private String tUWStatType;
	private String strSaleChnl;

	public ForeceitPrintUI() {
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

			ForeceitPrintBL tForeceitPrintBL = new ForeceitPrintBL();
			logger.debug("Start foreceitlist UI Submit ...");
			if (!tForeceitPrintBL.submitData(vData, cOperate)) {
				if (tForeceitPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tForeceitPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"ForeceitPrintBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tForeceitPrintBL.getResult();
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
		vData.addElement(strFeeDate);
		vData.addElement(strSaleChnl);
		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		// strAgentCode = (String)cInputData.get(1);
		strFeeDate = (String) cInputData.get(1);
		strSaleChnl = (String) cInputData.get(2);
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
		ForeceitPrintUI tForeceitPrintUI = new ForeceitPrintUI();
		VData tVData = new VData();
		tVData.addElement("86310005");
		tVData.addElement("2006-04-17");
		tVData.addElement("1");
		tForeceitPrintUI.submitData(tVData, "PRINT");

	}
}
