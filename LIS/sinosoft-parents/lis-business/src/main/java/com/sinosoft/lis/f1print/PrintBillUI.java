package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-04-04
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PrintBillUI {
private static Logger logger = Logger.getLogger(PrintBillUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData mInputData = new VData();
	private String mOperate;

	public PrintBillUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		try {
			if (!cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
			NewPrintBillRightBL tNewPrintBillRightBL = new NewPrintBillRightBL();
			logger.debug("Start PrintBillUI Submit ...");
			if (!tNewPrintBillRightBL.submitData(cInputData, cOperate)) {
				if (tNewPrintBillRightBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tNewPrintBillRightBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"FinChargeDayModeF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tNewPrintBillRightBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "PrintBillUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "PrintBillUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String billno = "00000000000000000068";
		String bankcode = "0701";
		String comcode = "86";
		VData tVData = new VData();
		PrintBillUI tPrintBillUI = new PrintBillUI();

		tVData.addElement(billno);
		tVData.addElement(bankcode);
		tVData.addElement("YS");
		tVData.addElement(comcode);
		tVData.addElement("F");
		tVData.addElement("S");

		if (!tPrintBillUI.submitData(tVData, "PRINT")) {
			logger.debug("Fail : " + tPrintBillUI.mErrors.getFirstError());
		} else {
			logger.debug("Success ! ");
		}
	}
}
