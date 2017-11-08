package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.PEdorAppManuUWDelMissionBL;
import com.sinosoft.lis.claim.LLUWDelMissionBL;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterEndService;

public class LLSecondManuUWFinishAfterEndService implements AfterEndService {
private static Logger logger = Logger.getLogger(LLSecondManuUWFinishAfterEndService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public LLSecondManuUWFinishAfterEndService() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("======LLSecondManuUWFinishAfterEndService======");
		LLUWDelMissionBL tLLUWDelMissionBL = new LLUWDelMissionBL();
		//工作流升级这里不需要手动处理工作流，所以注掉
//		if (!tLLUWDelMissionBL.submitData(cInputData, "")) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tLLUWDelMissionBL.mErrors);
//			return false;
//		}
//
//		mResult = tLLUWDelMissionBL.getResult();

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
