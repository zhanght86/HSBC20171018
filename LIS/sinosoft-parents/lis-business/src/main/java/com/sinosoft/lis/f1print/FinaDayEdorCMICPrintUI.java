package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/*******************************************************************************
 * <p>
 * Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @todo : 保全-要件变更日结
 * @author : liuxiaosong
 * @version : 1.00
 * @date : 2006-11-28
 ******************************************************************************/
public class FinaDayEdorCMICPrintUI implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorCMICPrintUI.class);

	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();

	public FinaDayEdorCMICPrintUI() {
	}

	/**
	 * 高层调用本类入口
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		FinaDayEdorCMICPrintBL tFinaDayEdorCMICPrintBL = new FinaDayEdorCMICPrintBL();

		if (!tFinaDayEdorCMICPrintBL.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tFinaDayEdorCMICPrintBL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tFinaDayEdorCMICPrintBL.getResult();
		}
		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 错误汇总
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		FinaDayEdorCMICPrintUI tFinaDayEdorPUPrintUI = new FinaDayEdorCMICPrintUI();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "8621");
		tTransferData.setNameAndValue("StartDate", "2006-1-30");
		tTransferData.setNameAndValue("EndDate", "2007-11-30");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		if (!tFinaDayEdorPUPrintUI.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}

}
