package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCApplyRecallPolSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class ApplyRecallPolUI {
private static Logger logger = Logger.getLogger(ApplyRecallPolUI.class);

	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public ApplyRecallPolUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		ApplyRecallPolBL tApplyRecallPolBL = new ApplyRecallPolBL();
		logger.debug("Start tApplyRecallPolBL Submit...");
		tApplyRecallPolBL.submitData(mInputData, cOperate);

		logger.debug("End tApplyRecallPolBL Submit...");

		// 如果有需要处理的错误，则返回
		if (tApplyRecallPolBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tApplyRecallPolBL.mErrors);
			return false;
		}
		mResult.clear();
		mResult = tApplyRecallPolBL.getResult();
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";

		LCApplyRecallPolSchema tLCApplyRecallPolSchema = new LCApplyRecallPolSchema();

		tLCApplyRecallPolSchema.setPrtNo("60933299999999");
		tLCApplyRecallPolSchema.setApplyType("0");
		tLCApplyRecallPolSchema.setRemark("test by zhangtao at 2005-04-13");
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(tLCApplyRecallPolSchema);
		tVData.add(tG);

		// 数据传输
		ApplyRecallPolUI tApplyRecallPolUI = new ApplyRecallPolUI();
		if (!tApplyRecallPolUI.submitData(tVData, "")) {
			logger.debug(tApplyRecallPolUI.mErrors.getError(0).errorMessage);
		}

	}

}
