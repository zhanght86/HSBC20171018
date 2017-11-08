/*
 * @(#)GrpEdorPTDetailBLF.java      May 22, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全处理－－减保操作（选人）
 * </p>
 * <p>
 * Description: 团体保全减保保全明细处理BLF
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: May 22, 2006
 * @version：1.0
 */
public class GrpEdorPTDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(GrpEdorPTDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if ("OnlyCheck".equals(mOperate)) {
			logger.debug("Just Check.....");
			return true;
		}

		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorPTDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		GrpEdorPTDetailBL tGrpEdorPTDetailBL = new GrpEdorPTDetailBL();
		if (!tGrpEdorPTDetailBL.submitData(mInputData, mOperate)) {
			mErrors.copyAllErrors(tGrpEdorPTDetailBL.mErrors);
			return false;
		}
		mResult = tGrpEdorPTDetailBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
