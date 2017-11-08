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

public class GrpAdvListUI {
private static Logger logger = Logger.getLogger(GrpAdvListUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strStartDate;
	private String strEndDate;
	private String strPayMode;

	// private String tUWStatType ;

	public GrpAdvListUI() {
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

			GrpAdvListBL tGrpAdvListBL = new GrpAdvListBL();
			logger.debug("Start GrpAdvListUI Submit ...");
			if (!tGrpAdvListBL.submitData(vData, cOperate)) {
				if (tGrpAdvListBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGrpAdvListBL.mErrors);
					return false;
				} else {
					buildError("submitData", "tGrpAdvListBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGrpAdvListBL.getResult();
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
		vData.addElement(strPayMode);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		// strAgentCode = (String)cInputData.get(1);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strPayMode = (String) cInputData.get(3);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpAdvListUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GrpAdvListUI tGrpAdvListUI = new GrpAdvListUI();

		VData tVData = new VData();
		tVData.addElement("86320000");
		tVData.addElement("2002-02-13");
		tVData.addElement("2005-04-14");
		tVData.addElement("1");

		tGrpAdvListUI.submitData(tVData, "PRINT");

	}
}
