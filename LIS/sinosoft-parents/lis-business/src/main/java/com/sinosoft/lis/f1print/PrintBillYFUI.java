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

public class PrintBillYFUI {
private static Logger logger = Logger.getLogger(PrintBillYFUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strBillNo;
	private String strBankCode;
	private String strMngCom;

	public PrintBillYFUI() {
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

			PrintBillYFBL tPrintBillYFBL = new PrintBillYFBL();

			logger.debug("Start PLPsqs UI Submit ...");

			if (!tPrintBillYFBL.submitData(cInputData, cOperate)) {
				if (tPrintBillYFBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tPrintBillYFBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"FinChargeDayModeF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tPrintBillYFBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "PrintBillYFUI";
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

		cError.moduleName = "PrintBillYFUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
