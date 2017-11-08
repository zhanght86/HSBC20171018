package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author lh
 * @version 1.0
 */

public class UrgeNoticeUI {
private static Logger logger = Logger.getLogger(UrgeNoticeUI.class);

	private VData mInputData;
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public UrgeNoticeUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		UrgeNoticeBL tUrgeNoticeBL = new UrgeNoticeBL();
		logger.debug("Start UrgeNotice UI Submit...");
		tUrgeNoticeBL.submitData(mInputData, cOperate);

		logger.debug("End UrgeNotice UI Submit...");

		// 如果有需要处理的错误，则返回
		if (tUrgeNoticeBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tUrgeNoticeBL.mErrors);
		}
		logger.debug(mErrors.getErrorCount());
		mInputData = null;
		this.mResult = tUrgeNoticeBL.getResult();
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		UrgeNoticeUI tUrgeNoticeUI = new UrgeNoticeUI();
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "001";
		tG.ManageCom = "86";
		tG.Operator = "001";
		VData tVData = new VData();
		tVData.addElement(tG);
		tUrgeNoticeUI.submitData(tVData, "UPDATE");
	}
}
