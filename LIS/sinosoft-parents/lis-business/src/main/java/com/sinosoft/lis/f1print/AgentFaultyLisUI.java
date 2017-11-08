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

public class AgentFaultyLisUI {
private static Logger logger = Logger.getLogger(AgentFaultyLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	// private String strAgentCode;
	private String strIssueDate;

	// private String strFlag;
	// private String strStateFlag;
	private String strAgentGroup;

	public AgentFaultyLisUI() {
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

			AgentFaultyLisBL tAgentFaultyLisBL = new AgentFaultyLisBL();
			logger.debug("Start AgentFaultyLis UI Submit ...");
			if (!tAgentFaultyLisBL.submitData(cInputData, cOperate)) {
				if (tAgentFaultyLisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tAgentFaultyLisBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"AgentFaultyLisBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tAgentFaultyLisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "AgentFaultyLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		// vData.clear();
		// vData.add(strMngCom);
		// vData.add(strIssueDate);
		// vData.add(strAgentGroup);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// strIssueDate = (String) cInputData.get(1);
		// strAgentGroup= (String) cInputData.get(2);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "AgentFaultyLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		VData mResult = new VData();
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ManageCom", "8632");
		tTransferData.setNameAndValue("StartIssueDate", "2005-12-06");
		tTransferData.setNameAndValue("EndIssueDate", "2005-12-06");
		tTransferData.setNameAndValue("SaleChnl", "1");
		tVData.addElement(tTransferData);
		AgentFaultyLisUI tAgentFaultyLisUI = new AgentFaultyLisUI();
		tAgentFaultyLisUI.submitData(tVData, "PRINT");

	}
}
