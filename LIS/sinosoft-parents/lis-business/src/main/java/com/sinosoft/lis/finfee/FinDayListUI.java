package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Hezy Lys
 * @version 1.0
 * @date:2003-05-28
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FinDayListUI implements BusinessService{
private static Logger logger = Logger.getLogger(FinDayListUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public FinDayListUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			// 进行业务处理
			if (!dealData()) {
				return false;
			}

			FinDayListBL tFinDayListBL = new FinDayListBL();
			logger.debug("Start FinDayListBL Submit ...");
			if (!tFinDayListBL.submitData(cInputData, cOperate)) {
				if (tFinDayListBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tFinDayListBL.mErrors);
					return false;
				} else {
					buildError("submitData", "FinDayListBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "FinDayListUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		GlobalInput tGlobalInput = (GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0);
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		if (tGlobalInput == null || tTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "FinDayListUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}


	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
