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
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class UWStaticPrintUI {
private static Logger logger = Logger.getLogger(UWStaticPrintUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	String strMngCom;
	String strStartDate;
	String strEndDate;
	String tUWStatType;

	public UWStaticPrintUI() {
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

			UWStaticPrintBL tUWStaticPrintBL = new UWStaticPrintBL();
			logger.debug("Start CBZKStatic UI Submit ...");
			if (!tUWStaticPrintBL.submitData(vData, cOperate)) {
				if (tUWStaticPrintBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tUWStaticPrintBL.mErrors);
					return false;
				} else {
					buildError("submitData", "UWQualityBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tUWStaticPrintBL.getResult();
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
		TransferData td = new TransferData();
		td.setNameAndValue("ManageCom", strMngCom);
		td.setNameAndValue("StartDate", strStartDate);
		td.setNameAndValue("EndDate", strEndDate);
		td.setNameAndValue("UWStatType", tUWStatType);
		vData.add(td);
		// vData.addElement(strMngCom);
		// vData.addElement(strStartDate);
		// vData.addElement(strEndDate);
		// vData.addElement(tUWStatType);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// //strAgentCode = (String)cInputData.get(1);
		// strStartDate = (String) cInputData.get(1);
		// strEndDate = (String) cInputData.get(2);
		// tUWStatType = (String) cInputData.get(3);

		TransferData tTD = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (tTD == null) {
			buildError("getInputData", "传入数据不足！");
			return false;
		}
		strMngCom = (String) tTD.getValueByName("ManageCom");
		strStartDate = (String) tTD.getValueByName("StartDate");
		strEndDate = (String) tTD.getValueByName("EndDate");
		tUWStatType = (String) tTD.getValueByName("UWStatType");

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
		UWStaticPrintUI tUWStaticPrintUI = new UWStaticPrintUI();

		VData tVData = new VData();
		tVData.addElement("86");
		tVData.addElement("2005-08-01");
		tVData.addElement("2005-08-02");
		tVData.addElement("6");

		tUWStaticPrintUI.submitData(tVData, "PRINT");

	}
}
