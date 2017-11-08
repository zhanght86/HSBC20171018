package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title:新契约团体承保月报</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @date 2006-07-14
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpAccInsuMonthListUI {
private static Logger logger = Logger.getLogger(GrpAccInsuMonthListUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public GrpAccInsuMonthListUI() {
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
			GrpAccInsuMonthListBL tGrpAccInsuMonthListBL = new GrpAccInsuMonthListBL();
			if (!tGrpAccInsuMonthListBL.submitData(cInputData, cOperate)) {
				if (tGrpAccInsuMonthListBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tGrpAccInsuMonthListBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"tGrpAccInsuMonthListBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tGrpAccInsuMonthListBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "GrpAccInsuMonthListUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		return true;
	}

	private boolean getInputData(VData cInputData) {
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpAccInsuMonthListUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		mErrors.addOneError(cError);
	}

	public static void main(String[] args) {

	}
}
