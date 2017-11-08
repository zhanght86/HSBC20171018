package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 李焕
 * @version 1.0
 * @date 2005-08-10
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ReceiptListUI {
private static Logger logger = Logger.getLogger(ReceiptListUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData mInputData = new VData();
	private String mOperate;

	public ReceiptListUI() {
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
			ReceiptListBL tReceiptListBL = new ReceiptListBL();
			logger.debug("Start ReceiptListUI Submit ...");
			if (!tReceiptListBL.submitData(cInputData, cOperate)) {
				if (tReceiptListBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tReceiptListBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"FinChargeDayModeF1PBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tReceiptListBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "ReceiptListUI";
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

		cError.moduleName = "SecPayNocticeUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		ReceiptListUI u = new ReceiptListUI();
		VData vt = new VData();
		vt.addElement("2301190000000778");
		u.submitData(vt, "PRINT");

	}
}
