/*
 * @(#)PEdorAppUWConfirmAfterInitService.java	2005-06-02
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.GEdorAccepManuUWBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团险保全-人工核保确认服务类
 * </p>
 */
public class GEdorAppUWConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(GEdorAppUWConfirmAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public GEdorAppUWConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		
		GEdorAccepManuUWBL tPEdorAccepManuUWBL = new GEdorAccepManuUWBL();

		if (!tPEdorAccepManuUWBL.submitData(cInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAccepManuUWBL.mErrors);
			return false;
		}

		mResult = tPEdorAccepManuUWBL.getResult();

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
