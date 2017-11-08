/*
 * @(#)PEdorNoscanAppAfterInitService.java	2005-04-28
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.PEdorAppUpdMIsssionBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.BeforeEndService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-无扫描申请服务类
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
public class PEdorAppBeforeEndService implements BeforeEndService {
private static Logger logger = Logger.getLogger(PEdorAppBeforeEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public PEdorAppBeforeEndService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		PEdorAppUpdMIsssionBL tPEdorAppUpdMIsssionBL = new PEdorAppUpdMIsssionBL();

		if (!tPEdorAppUpdMIsssionBL.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAppUpdMIsssionBL.mErrors);
			return false;
		}

		mResult = tPEdorAppUpdMIsssionBL.getResult();
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
