package com.sinosoft.workflow.claim;

import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLSecondUWApplyBLF;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowService;

public class LLScondUWApplyService implements WorkFlowService {
	private static Logger logger = Logger
			.getLogger(LLScondUWApplyService.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();

	public LLScondUWApplyService() {
	}

	@Override
	public boolean submitData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "InputConfirmAfterInitService";
			tError.functionName = "submitData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 调用BLF 进行逻辑处理,此处有开发人员根据具体情况进行修改
		LLSecondUWApplyBLF tLLSecondUWApplyBLF = new LLSecondUWApplyBLF();
		if (tLLSecondUWApplyBLF
				.submitData(cInputData, cOperate)) {
			mResult = tLLSecondUWApplyBLF.getResult();
		} else {
			this.mErrors
					.copyAllErrors(tLLSecondUWApplyBLF.mErrors);
			return false;
		}
		return true;

	}

	@Override
	public VData getResult() {
		return mResult;
	}

	@Override
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	@Override
	public CErrors getErrors() {
		return mErrors;
	}

}
