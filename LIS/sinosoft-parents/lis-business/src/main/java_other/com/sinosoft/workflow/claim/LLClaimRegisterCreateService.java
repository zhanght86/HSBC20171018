package com.sinosoft.workflow.claim;

import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimRegisterNewBL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowService;

public class LLClaimRegisterCreateService implements WorkFlowService {
	private static Logger logger = Logger.getLogger(LLClaimRegisterCreateService.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private TransferData mTransferData = new TransferData();
	public LLClaimRegisterCreateService() {
	
	}

	@Override
	public boolean submitData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		String ScanFlag = (String)mTransferData.getValueByName("ScanFlag");
		if (mTransferData == null) {
			CError tError = new CError();
			tError.moduleName = "LLClaimRegisterCreateService";
			tError.functionName = "submitData";
			tError.errorMessage = "前台传输业务数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if(ScanFlag=="0" || "0".equals(ScanFlag)){
			LLClaimRegisterNewBL tLLClaimRegisterNewBL = new LLClaimRegisterNewBL();
			if(tLLClaimRegisterNewBL.submitData(cInputData, cOperate)){
				mResult = tLLClaimRegisterNewBL.getResult();
			}else{
				this.mErrors.copyAllErrors(tLLClaimRegisterNewBL.mErrors);
				
			}
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
