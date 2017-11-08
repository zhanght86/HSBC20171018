package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class UWApplyUI implements BusinessService{
private static Logger logger = Logger.getLogger(UWApplyUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public UWApplyUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;

		UWApplyBL tUWApplyBL = new UWApplyBL();

		logger.debug("--------UWApplyUI Start!---------");
		if (tUWApplyBL.submitData(cInputData, cOperate)) {

		}
		logger.debug("--------UWApplyUI End!---------");

		// 如果有需要处理的错误，则返回
		if (tUWApplyBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tUWApplyBL.mErrors);
			return false;
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;

		return true;
	}

	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "002";
		tG.ManageCom = "86";
		tG.ComCode = "01";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ApplyNo", "5");
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(tTransferData);

		UWApplyUI ui = new UWApplyUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
