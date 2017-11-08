package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-02-17
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PrintBillYFRightUI {
private static Logger logger = Logger.getLogger(PrintBillYFRightUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public PrintBillYFRightUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			PrintBillYFRightBL tPrintBillYFRightBL = new PrintBillYFRightBL();

			logger.debug("Start PrintBillYFRight UI Submit ...");

			if (!tPrintBillYFRightBL.submitData(cInputData, cOperate)) {
				if (tPrintBillYFRightBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tPrintBillYFRightBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"FinChargeDayModeF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tPrintBillYFRightBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "PrintBillYFRightUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintBillYFRightUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
