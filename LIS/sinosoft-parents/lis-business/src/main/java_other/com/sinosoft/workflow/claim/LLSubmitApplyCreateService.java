package com.sinosoft.workflow.claim;

import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLSubmitApplyBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowBL;
import com.sinosoft.workflowengine.WorkFlowService;

public class LLSubmitApplyCreateService implements WorkFlowService {
	private static Logger logger = Logger.getLogger(LLSubmitApplyCreateService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前台提交数据的容器 */
	private VData mResult = new VData();
	private VData tResult ;
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mG = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	public LLSubmitApplyCreateService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean submitData(VData cInputData, String cOperate) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mG = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		String DispType = (String)mTransferData.getValueByName("DispType");
		if(DispType == null || DispType.equals("")){
			LLSubmitApplyBL tLLSubmitApplyBL = new LLSubmitApplyBL();
			if (tLLSubmitApplyBL.submitData(cInputData, "INSERT")) {
						 
				mResult = tLLSubmitApplyBL.getResult();
				}else{
					this.mErrors.copyAllErrors(tLLSubmitApplyBL.mErrors);
					return false;
				}	
		}
		// 调用业务逻辑处理类，返回处理完数据
		
		return true;
	}

	@Override
	public VData getResult() {
		
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
