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
import com.sinosoft.utility.XmlExport;

public class UWStaticPrintSEUI {
private static Logger logger = Logger.getLogger(UWStaticPrintSEUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	String strMngCom;
	String strStartDate;
	String strEndDate;
	String tUWStatType;
	String tOperator;

	public UWStaticPrintSEUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT") && !cOperate.equals("SAVE")) {
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

			UWStaticPrintSEBL tUWStaticPrintSEBL = new UWStaticPrintSEBL();
			if (!tUWStaticPrintSEBL.submitData(vData, cOperate)) {
				if (tUWStaticPrintSEBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tUWStaticPrintSEBL.mErrors);
					return false;
				} else {
					buildError("submitData", "UWQualityBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tUWStaticPrintSEBL.getResult();
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
		td.setNameAndValue("Operator", tOperator);
		vData.add(td);
		// vData.addElement(strMngCom);
		// vData.addElement(strStartDate);
		// vData.addElement(strEndDate);
		// vData.addElement(tUWStatType);
		// vData.addElement(tOperator);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// strStartDate = (String) cInputData.get(1);
		// strEndDate = (String) cInputData.get(2);
		// tUWStatType = (String) cInputData.get(3);
		// tOperator = (String )cInputData.get(4);

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
		tOperator = (String) tTD.getValueByName("Operator");

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
		UWStaticPrintSEUI tUWStaticPrintSEUI = new UWStaticPrintSEUI();

		VData tResult = new VData();
		XmlExport txmlExport = new XmlExport();
		VData tVData = new VData();
		tVData.addElement("8632");
		tVData.addElement("2005-10-19");
		tVData.addElement("2005-10-20");
		tVData.addElement("1");
		tVData.addElement("001");

		tUWStaticPrintSEUI.submitData(tVData, "SAVE");
		tResult = tUWStaticPrintSEUI.getResult();
		txmlExport = (XmlExport) tResult.getObjectByObjectName("XmlExport", 0);
		if (txmlExport != null) {
			logger.debug("_--txmlExport--" + txmlExport.toString());
		} else
			logger.debug("－－－什么都都没有－－－");
	}
}
