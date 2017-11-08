package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 银行代打
 * </p>
 * <p>
 * Description: 银行代打生成数据库数据模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author guanwei
 * @version 1.0
 */

public class RNBankProxyPrtInvoiceUI {
private static Logger logger = Logger.getLogger(RNBankProxyPrtInvoiceUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INVOICE"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		try {
			logger.debug("---RNBankProxyPrtInvoice BLF BEGIN---");
			RNBankProxyPrtInvoiceBLF tRNBankProxyPrtInvoiceBLF = new RNBankProxyPrtInvoiceBLF();
			if (tRNBankProxyPrtInvoiceBLF.submitData(cInputData, cOperate) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tRNBankProxyPrtInvoiceBLF.mErrors);
				mResult.clear();
				mResult.add(mErrors.getFirstError());
				return false;
			} else {
				mResult = tRNBankProxyPrtInvoiceBLF.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("---RNBankProxyPrtInvoice BLF END---");
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		RNBankProxyPrtInvoiceUI RNBankProxyPrtInvoiceUI = new RNBankProxyPrtInvoiceUI();

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2006-04-01");
		tTransferData.setNameAndValue("EndDate", "2006-04-30");
		tTransferData.setNameAndValue("BankCode", "2100010");
		tTransferData.setNameAndValue("ManageCom", "86");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tGlobalInput);

		if (!RNBankProxyPrtInvoiceUI.submitData(tVData, "INVOICE")) {
			VData rVData = RNBankProxyPrtInvoiceUI.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}
	}
}
