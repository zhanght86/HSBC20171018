package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class IndiDueFeeCancelUI implements BusinessService {
private static Logger logger = Logger.getLogger(IndiDueFeeCancelUI.class);

	// 业务处理相关变量
	private VData mInputData;

	public CErrors mErrors = new CErrors();

	public IndiDueFeeCancelUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
		tIndiDueFeeCancelBL.submitData(mInputData, cOperate);

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tIndiDueFeeCancelBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tIndiDueFeeCancelBL.mErrors);
			return false;
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		return true;
	}
	
	public VData getResult() {
		return mInputData;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
