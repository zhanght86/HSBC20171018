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

public class GrpNFeeUI {
private static Logger logger = Logger.getLogger(GrpNFeeUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	String strMngCom;
	String strFeeDate;
	String strEndDate;
	String tUWStatType;

	public GrpNFeeUI() {
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

			GrpNFeeBL tGrpNFeeBL = new GrpNFeeBL();
			logger.debug("Start foreceitlist UI Submit ...");
			if (!tGrpNFeeBL.submitData(vData, cOperate)) {
				if (tGrpNFeeBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGrpNFeeBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"ForeceitPrintBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGrpNFeeBL.getResult();
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

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		// strAgentCode = (String)cInputData.get(1);
		strFeeDate = (String) cInputData.get(1);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "tGrpNFeeUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GrpNFeeUI tGrpNFeeUI = new GrpNFeeUI();

		VData tVData = new VData();
		tVData.addElement("86110000");

		tVData.addElement("2005-08-15");

		tGrpNFeeUI.submitData(tVData, "PRINT");

	}
}
