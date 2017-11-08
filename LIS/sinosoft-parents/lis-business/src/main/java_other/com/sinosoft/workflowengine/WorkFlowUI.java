package com.sinosoft.workflowengine;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class WorkFlowUI implements BusinessService
{
private static Logger logger = Logger.getLogger(WorkFlowUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult ;
 

	public WorkFlowUI() 
	{
	}
	public boolean submitData(VData cInputData, String cOperate) 
	{
		WorkFlowBL tWorkFlowBL=new WorkFlowBL();
		if (!tWorkFlowBL.submitData(cInputData, cOperate)) 
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tWorkFlowBL.mErrors);
//			CError tError = new CError();
//			tError.moduleName = "WorkFlowUI";
//			tError.functionName = "submitData";
//			tError.errorMessage = "工作流处理失败!";
//			this.mErrors.addOneError(tError);
			return false;
		}
		mResult = tWorkFlowBL.getResult();
		return true;
	}
	public VData getResult()
	{
		return mResult;
	}
	public CErrors getErrors() 
	{
		return mErrors;
	}
}
