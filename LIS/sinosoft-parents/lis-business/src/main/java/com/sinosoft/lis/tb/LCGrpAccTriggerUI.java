package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LCGrpAccTriggerUI {
private static Logger logger = Logger.getLogger(LCGrpAccTriggerUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public LCGrpAccTriggerUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("---------------- UI begin");
		LCGrpAccTriggerBL tLCGrpAccTriggerBL = new LCGrpAccTriggerBL();
		if (!tLCGrpAccTriggerBL.submitData(cInputData, cOperate)) {
			this.mErrors.copyAllErrors(tLCGrpAccTriggerBL.mErrors);
			CError error = new CError();
			error.moduleName = "LCGrpAccTriggerUI";
			error.functionName = "submitData";
			error.errorMessage = "提交给BL时出错";
			this.mErrors.addOneError(error);
			return false;
		}
		mResult = tLCGrpAccTriggerBL.getResult();
		return true;
	}

	/**
	 * 返回集
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}
}
