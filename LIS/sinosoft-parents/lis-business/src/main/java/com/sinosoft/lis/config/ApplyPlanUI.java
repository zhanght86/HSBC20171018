package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class ApplyPlanUI {
private static Logger logger = Logger.getLogger(ApplyPlanUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public ApplyPlanUI() {
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
		ApplyPlanBL tApplyPlanBL = new ApplyPlanBL();

		logger.debug("--------UWApplyUI Start!---------");
		if (tApplyPlanBL.submitData(cInputData, cOperate)) {

		}
		logger.debug("--------UWApplyUI End!---------");

		// 如果有需要处理的错误，则返回
		if (tApplyPlanBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tApplyPlanBL.mErrors);
			return false;
		}
		logger.debug("error num=" + mErrors.getErrorCount());

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

	public static void main(String[] args) {
		ApplyPlanUI applyplanui = new ApplyPlanUI();
	}
}
