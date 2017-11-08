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

public class RwcstxPrintUI {
private static Logger logger = Logger.getLogger(RwcstxPrintUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	String strMngCom;
	String strStaticType;
	String strOverTime;
	String strSaleChnl;

	public RwcstxPrintUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
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

			RwcstxPrintBL tRwcstxPrintBL = new RwcstxPrintBL();
			logger.debug("Start PNoticeBill UI Submit ...");
			if (!tRwcstxPrintBL.submitData(vData, cOperate)) {
				if (tRwcstxPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tRwcstxPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData", "RwcstxPrintBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tRwcstxPrintBL.getResult();
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
		vData.addElement(strSaleChnl);
		vData.addElement(strStaticType);
		vData.addElement(strOverTime);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		// strAgentCode = (String)cInputData.get(1);
		strSaleChnl = (String) cInputData.get(1);
		strStaticType = (String) cInputData.get(2);
		strOverTime = (String) cInputData.get(3);
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
		RwcstxPrintUI tRwcstxPrintUI = new RwcstxPrintUI();

		VData tVData = new VData();
		tVData.addElement("86110000");
		tVData.addElement("1");
		tVData.addElement("1");
		tVData.addElement("3");

		tRwcstxPrintUI.submitData(tVData, "PRINT");

	}
}
