package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author not attributable
 * @version 6.0
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class WaitPrtListUI {
private static Logger logger = Logger.getLogger(WaitPrtListUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strStartIssueDate;
	private String strAgentGroup;
	private String strSaleChnl;
	private String strEndIssueDate;
	private String strPrtState;

	public WaitPrtListUI() {
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

			WaitPrtListBL tWaitPrtListBL = new WaitPrtListBL();
			logger.debug("Start WaitPaymentLis UI Submit ...");
			if (!tWaitPrtListBL.submitData(vData, cOperate)) {
				if (tWaitPrtListBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tWaitPrtListBL.mErrors);
					return false;
				} else {
					buildError("submitData", "WaitPrtListUI发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tWaitPrtListBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "WaitPaymentLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strStartIssueDate);
		vData.add(strAgentGroup);
		vData.add(strSaleChnl);
		vData.add(strEndIssueDate);
		vData.add(strPrtState);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartIssueDate = (String) cInputData.get(1);
		strAgentGroup = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);
		strEndIssueDate = (String) cInputData.get(4);
		strPrtState = (String) cInputData.get(5);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "WaitPaymentLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		WaitPrtListUI waitprtlistui = new WaitPrtListUI();

	}
}
