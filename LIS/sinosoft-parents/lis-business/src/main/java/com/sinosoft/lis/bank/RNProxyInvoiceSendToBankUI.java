package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.sql.SQLException;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 银行代打发票
 * </p>
 * 
 * <p>
 * Description: 银行代打发票数据库生成文件模块
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author : guanwei
 * 
 * @version 1.0
 */

public class RNProxyInvoiceSendToBankUI {
private static Logger logger = Logger.getLogger(RNProxyInvoiceSendToBankUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData vData = new VData();
	private TransferData mTransferData = new TransferData();

	public RNProxyInvoiceSendToBankUI() {
	}

	public static void main(String[] args) throws SQLException {
		String mStartDate = "";
		String mEndDate = "";
		String mBankCode = "";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2006-01-01");
		tTransferData.setNameAndValue("EndDate", "2006-01-31");
		tTransferData.setNameAndValue("BankCode", "2100010");
		logger.debug("mStartDate:" + mStartDate);
		logger.debug("mEndDate:" + mEndDate);
		logger.debug("mBankCode:" + mBankCode);
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();

		tVData.addElement(tTransferData);
		tVData.add(tG);
		RNProxyInvoiceSendToBankUI tRNProxyInvoiceSendToBankUI = new RNProxyInvoiceSendToBankUI();
		tRNProxyInvoiceSendToBankUI.submitData(tVData, "INVOICE");
		VData result = new VData();
		result = tRNProxyInvoiceSendToBankUI.getResult();
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate)
			throws SQLException {
		if (!cOperate.equals("INVOICE")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		RNProxyInvoiceSendToBankBL tRNProxyInvoiceSendToBankBL = new RNProxyInvoiceSendToBankBL();
		logger.debug("Start RNProxyInvoiceSendToBankUI Submit ...");

		if (!tRNProxyInvoiceSendToBankBL.submitData(cInputData, "INVOICE")) {
			logger.debug("Start RNProxyInvoiceSendToBankUI Submit 1 ...");
			if (tRNProxyInvoiceSendToBankBL.mErrors.needDealError()) {
				logger.debug("Start RNProxyInvoiceSendToBankUI Submit 2...");
				mErrors.copyAllErrors(tRNProxyInvoiceSendToBankBL.mErrors);
				return false;
			} else {
				buildError("submitData",
						"RNProxyInvoiceSendToBankBL发生错误，submitData failed");
				return false;
			}
		} else {
			mResult = tRNProxyInvoiceSendToBankBL.getResult();
			return true;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RNProxyInvoiceSendToBankUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
