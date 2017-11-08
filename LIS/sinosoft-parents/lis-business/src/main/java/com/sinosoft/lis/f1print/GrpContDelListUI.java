package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpContDelListUI {
private static Logger logger = Logger.getLogger(GrpContDelListUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	public GrpContDelListUI() {
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

			GrpContDelListBL tGrpContDelListBL = new GrpContDelListBL();
			logger.debug("Start GrpContDelListUI Submit ...");
			if (!tGrpContDelListBL.submitData(vData, cOperate)) {
				if (tGrpContDelListBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGrpContDelListBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"GrpContDelListBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGrpContDelListBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "GrpContDelListUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(mGlobalInput);
		vData.add(mTransferData);
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpContDelListUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
