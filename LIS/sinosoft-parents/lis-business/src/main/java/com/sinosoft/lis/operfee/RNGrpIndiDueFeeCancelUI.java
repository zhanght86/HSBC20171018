package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单抽档撤销
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author guanwei
 * @version 1.0
 */

public class RNGrpIndiDueFeeCancelUI {
private static Logger logger = Logger.getLogger(RNGrpIndiDueFeeCancelUI.class);

	// 业务处理相关变量
	private VData mInputData;
	public CErrors mErrors = new CErrors();

	public RNGrpIndiDueFeeCancelUI() {
	}

	public static void main(String[] args) {
		RNGrpIndiDueFeeCancelUI tRNGrpIndiDueFeeCancelUI = new RNGrpIndiDueFeeCancelUI();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		RNGrpIndiDueFeeCancelBL tRNGrpIndiDueFeeCancelBL = new RNGrpIndiDueFeeCancelBL();
		tRNGrpIndiDueFeeCancelBL.submitData(mInputData, cOperate);

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tRNGrpIndiDueFeeCancelBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tRNGrpIndiDueFeeCancelBL.mErrors);
			return false;
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		return true;
	}

}
