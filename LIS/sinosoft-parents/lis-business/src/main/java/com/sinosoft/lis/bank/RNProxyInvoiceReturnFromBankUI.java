package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 银行发票代打业务业务系统
 * </p>
 * <p>
 * Description: 银行返回文件转换到数据模块
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

public class RNProxyInvoiceReturnFromBankUI {
private static Logger logger = Logger.getLogger(RNProxyInvoiceReturnFromBankUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public RNProxyInvoiceReturnFromBankUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"READ"
	 * @return 布尔值（true--提交成功, false--提交失败
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---RNProxyInvoiceReturnFromBankBL BEGIN---");
		RNProxyInvoiceReturnFromBankBL tRNProxyInvoiceReturnFromBankBL = new RNProxyInvoiceReturnFromBankBL();

		if (tRNProxyInvoiceReturnFromBankBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tRNProxyInvoiceReturnFromBankBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tRNProxyInvoiceReturnFromBankBL.getResult();
		}
		logger.debug("---RNProxyInvoiceReturnFromBankBL END---");
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
		RNProxyInvoiceReturnFromBankUI tRNProxyInvoiceReturnFromBankUI = new RNProxyInvoiceReturnFromBankUI();

		TransferData transferData1 = new TransferData();
		transferData1
				.setNameAndValue("fileName",
						"C:\\Documents and Settings\\Administrator\\桌面\\xh_inv_ret_2100010_200601.z");
		transferData1.setNameAndValue("bankCode", "2100010");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";

		VData inVData = new VData();
		inVData.add(transferData1);
		inVData.add(tGlobalInput);

		if (!tRNProxyInvoiceReturnFromBankUI.submitData(inVData, "READ")) {
			VData rVData = tRNProxyInvoiceReturnFromBankUI.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}
	}
}
