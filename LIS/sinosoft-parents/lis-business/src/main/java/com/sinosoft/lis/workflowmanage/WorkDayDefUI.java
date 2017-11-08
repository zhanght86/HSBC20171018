package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

import org.apache.log4j.*;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;

/**
 * <p>Title: </p>
 * <p/>
 * <p>Description: </p>
 * <p/>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p/>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WorkDayDefUI implements BusinessService
{
private static Logger logger = Logger.getLogger(WorkDayDefUI.class);

	private static Logger log = Logger.getLogger(WorkDayDefUI.class);

	public WorkDayDefUI()
	{
	}

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();


	public boolean submitData(VData cInputData, String cOperate)
	{
		WorkDayDefBL tWorkDayDefBL = new WorkDayDefBL();
		log.debug("Start PWProcess UI Submit...");
		//如果有需要处理的错误，则返回
		if (!tWorkDayDefBL.submitData(cInputData, cOperate))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tWorkDayDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PWProcessUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
//        mAccountManagerCode=tPWProcessBL.getAccountManagerCode();
		log.debug("End PWProcess UI Submit...");
		this.mResult.clear();
		this.mResult = tWorkDayDefBL.getResult();
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}	
}
