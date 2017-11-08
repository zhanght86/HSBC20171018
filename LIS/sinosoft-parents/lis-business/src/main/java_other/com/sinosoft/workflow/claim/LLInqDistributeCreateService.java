package com.sinosoft.workflow.claim;

import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLInqApplyBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowService;

public class LLInqDistributeCreateService implements WorkFlowService {
	private static Logger logger = Logger.getLogger(LLInqDistributeCreateService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前台提交数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mG = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	
	public LLInqDistributeCreateService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean submitData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mG = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		String CreateOrExecuteFlag = (String)mTransferData.getValueByName("CreateOrExecuteFlag");
		if(CreateOrExecuteFlag==null || CreateOrExecuteFlag.equals("")){
			LLInqApplyBL tLLInqApplyBL = new LLInqApplyBL();
			if (tLLInqApplyBL.submitData(cInputData, "INSERT")) {
				mResult = tLLInqApplyBL.getResult();
			}else{
				this.mErrors.copyAllErrors(tLLInqApplyBL.mErrors);
				return false;
			}
		}
		
		return true;
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

	@Override
	public TransferData getReturnTransferData() {
		// TODO Auto-generated method stub
		return mTransferData;
	}

	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
