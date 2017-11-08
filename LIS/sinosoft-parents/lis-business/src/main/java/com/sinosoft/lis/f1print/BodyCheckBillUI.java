package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 裴真
 * @version 1.0
 * @date 2006-04-06
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BodyCheckBillUI {
private static Logger logger = Logger.getLogger(BodyCheckBillUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom, strQGrpProposalNo, strAgentCode, twebpath;

	public BodyCheckBillUI() {
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

			BodyCheckBillBL tBodyCheckBillBL = new BodyCheckBillBL();
			logger.debug("Start BodyCheckBillUI Submit ...");
			if (!tBodyCheckBillBL.submitData(vData, cOperate)) {
				if (tBodyCheckBillBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tBodyCheckBillBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"BodyCheckBillBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tBodyCheckBillBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "NBPreNoLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strQGrpProposalNo);
		vData.add(strAgentCode);
		vData.add(twebpath);
		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strQGrpProposalNo = (String) cInputData.get(1);
		strAgentCode = (String) cInputData.get(2);
		twebpath = (String) cInputData.get(3);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "BodyCheckBillUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		BodyCheckBillUI tBodyCheckBillUI = new BodyCheckBillUI();
		VData tVData = new VData();
		tVData.addElement("86210000");
		tVData.addElement("20060406100001");
		tVData.addElement("");
		String tPath = "G:\\NewSys\\ui\\f1print\\NCLtemplate//";
		tVData.addElement(tPath);
		tBodyCheckBillUI.submitData(tVData, "PRINT");

	}
}
