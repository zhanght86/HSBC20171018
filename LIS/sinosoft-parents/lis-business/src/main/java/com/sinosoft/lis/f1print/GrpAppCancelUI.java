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

import java.util.Vector;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpAppCancelUI {
private static Logger logger = Logger.getLogger(GrpAppCancelUI.class);

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strIssueDate;
	private String strIssueEndDate;

	private String strPrintDate;
	// private String strAgentGroup;
	private String mListType;

	public GrpAppCancelUI() {
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

			GrpAppCancelBL tGrpAppCancelBL = new GrpAppCancelBL();
			logger.debug("Start GrpAppCancel UI Submit ...");
			if (!tGrpAppCancelBL.submitData(vData, cOperate)) {
				if (tGrpAppCancelBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGrpAppCancelBL.mErrors);
					return false;
				} else {
					buildError("submitData", "GrpAppCancelBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGrpAppCancelBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "GrpAppCancelUI";
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
		vData.add(strIssueEndDate);
		// vData.add(strAgentGroup);
		vData.add(strPrintDate);
		vData.add(mListType);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strIssueDate = (String) cInputData.get(1);
		strIssueEndDate = (String) cInputData.get(2);
		// strAgentGroup= (String) cInputData.get(3);
		strPrintDate = (String) cInputData.get(3);
		mListType = (String) cInputData.get(4);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpAppCancelUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private Vector getVsearch(Vector v) {
		Vector temp = new Vector();
		Vector temp1 = new Vector();
		Vector bin = new Vector();
		temp = v;
		int length = 0;
		length = temp.size();
		for (int i = 0; i < length; i++) {
			temp1 = (Vector) temp.get(i);
			if (temp1.size() == 1) {
				bin.add(temp1);
			} else {
				for (int k = 0; k < temp1.size(); k++) {
					if (temp1.get(k) != null) {
						bin.add(temp1.get(k));

					}
				}
			}

		}
		return bin;
	}

	public static void main(String[] args) {
	}

}
