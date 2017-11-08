/*
 * @(#)GEdorApproveAfterInitService.java	2005-11-14
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.GEdorApproveBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全复核服务类
 */
public class GEdorApproveAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GEdorApproveAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public GEdorApproveAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======GEdorApproveAfterInitService======");
		GEdorApproveBL tGEdorApproveBL = new GEdorApproveBL();

		if (!tGEdorApproveBL.submitData(cInputData, "EDOR||APPROVE")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGEdorApproveBL.mErrors);
			return false;
		}

		mResult = tGEdorApproveBL.getResult();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
