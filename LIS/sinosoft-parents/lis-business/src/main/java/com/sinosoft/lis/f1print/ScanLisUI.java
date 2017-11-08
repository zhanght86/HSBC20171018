package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-04-16
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ScanLisUI {
private static Logger logger = Logger.getLogger(ScanLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	// private String strMngCom;
	// private String strNoticeType;
	// private String strIssueDate;
	private TransferData mTransferData = new TransferData();
	private String mBusiType;

	// private String strFlag;
	// private String strStateFlag;
	// private String strAgentGroup;

	public ScanLisUI() {
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

			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			mBusiType = (String) mTransferData.getValueByName("BusiType");
			logger.debug("Start ScanLis UI Submit ...");
			if ("BQ".equals(mBusiType.toUpperCase())) {
				// 保全扫描清单
				EdorScanBillPrintBL tEdorScanBillPrintBL = new EdorScanBillPrintBL();
				if (!tEdorScanBillPrintBL.submitData(cInputData, cOperate)) {
					if (tEdorScanBillPrintBL.mErrors.needDealError()) {
						mErrors.copyAllErrors(tEdorScanBillPrintBL.mErrors);
						return false;
					} else {
						buildError("submitData",
								"EdorScanBillPrintBL发生错误，但是没有提供详细的出错信息");
						return false;
					}
				} else {
					mResult = tEdorScanBillPrintBL.getResult();
					return true;
				}
			} else {
				ScanLisBL tScanLisBL = new ScanLisBL();
				if (!tScanLisBL.submitData(cInputData, cOperate)) {
					if (tScanLisBL.mErrors.needDealError()) {
						mErrors.copyAllErrors(tScanLisBL.mErrors);
						return false;
					} else {
						buildError("submitData", "ScanLisBL发生错误，但是没有提供详细的出错信息");
						return false;
					}
				} else {
					mResult = tScanLisBL.getResult();
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "ScanLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		// vData.clear();
		// vData.add(strMngCom);
		// vData.add(strIssueDate);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		// strMngCom = (String) cInputData.get(0);
		// strIssueDate = (String) cInputData.get(1);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ScanLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		ScanLisUI tScanLisUI = new ScanLisUI();
		// tTransferData.setNameAndValue("ManageCom", "8651");
		// tTransferData.setNameAndValue("ScanStartDate", "2005-11-20");
		// tTransferData.setNameAndValue("ScanEndDate", "2005-11-20");
		// tTransferData.setNameAndValue("BusiType", "TB");
		// tTransferData.setNameAndValue("BusiPaperType", "01");
		tTransferData.setNameAndValue("ScanBatchNo", "00865100200512010001");
		tVData.addElement(tTransferData);
		tScanLisUI.submitData(tVData, "PRINT");

	}
}
