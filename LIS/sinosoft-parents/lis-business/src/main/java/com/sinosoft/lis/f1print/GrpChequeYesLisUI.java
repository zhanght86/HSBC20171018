package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 殷晓屹
 * @version 1.0
 * @date 2006-04-16
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpChequeYesLisUI {
private static Logger logger = Logger.getLogger(GrpChequeYesLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strPayDate;
	private String strPrintDate;
	private String strAgentGroup;

	public GrpChequeYesLisUI() {
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

			GrpChequeYesLisBL tGrpChequeYesLisBL = new GrpChequeYesLisBL();
			logger.debug("Start GrpChequeYesLis UI Submit ...");
			if (!tGrpChequeYesLisBL.submitData(vData, cOperate)) {
				if (tGrpChequeYesLisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGrpChequeYesLisBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"GrpChequeYesLis发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGrpChequeYesLisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "GrpChequeYesLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strPayDate);
		vData.add(strAgentGroup);
		vData.add(strPrintDate);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strPayDate = (String) cInputData.get(1);
		strAgentGroup = (String) cInputData.get(2);
		strPrintDate = (String) cInputData.get(3);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpChequeYesLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
