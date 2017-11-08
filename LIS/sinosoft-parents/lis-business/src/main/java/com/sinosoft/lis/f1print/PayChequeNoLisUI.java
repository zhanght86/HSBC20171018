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

public class PayChequeNoLisUI {
private static Logger logger = Logger.getLogger(PayChequeNoLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	// private String strAgentCode;
	private String strIssueDate;

	// private String strFlag;
	private String strPrintDate;
	private String strAgentGroup;

	public PayChequeNoLisUI() {
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

			PayChequeNoLisBL tPayChequeNoLisBL = new PayChequeNoLisBL();
			logger.debug("Start PayChequeNoLis UI Submit ...");
			if (!tPayChequeNoLisBL.submitData(vData, cOperate)) {
				if (tPayChequeNoLisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tPayChequeNoLisBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"PayChequeNoLisBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tPayChequeNoLisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "PayChequeNoLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strIssueDate);
		vData.add(strAgentGroup);
		vData.add(strPrintDate);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strIssueDate = (String) cInputData.get(1);
		strAgentGroup = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PayChequeNoLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
