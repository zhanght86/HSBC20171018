/*
 * @(#)PEdorAppUWConfirmAfterInitService.java	2005-06-03
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.PEdorAppUWManuApplyBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-人工核保申请服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class PEdorAppManuUWAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(PEdorAppManuUWAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public PEdorAppManuUWAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======PEdorAppManuUWAfterInitService======");
		PEdorAppUWManuApplyBL tPEdorAppUWManuApplyBL = new PEdorAppUWManuApplyBL();

		if (!tPEdorAppUWManuApplyBL.submitData(cInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAppUWManuApplyBL.mErrors);
			return false;
		}

		mResult = tPEdorAppUWManuApplyBL.getResult();

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
