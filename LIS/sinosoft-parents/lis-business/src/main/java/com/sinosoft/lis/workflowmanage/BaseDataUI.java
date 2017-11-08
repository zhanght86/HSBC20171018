/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BaseDataUI implements BusinessService
{
private static Logger logger = Logger.getLogger(BaseDataUI.class);

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public BaseDataUI()
	{
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		BaseDataBL tBaseDataBL = new BaseDataBL();
		tBaseDataBL.submitData(cInputData, cOperate);
		// 如果有需要处理的错误，则返回
		if (tBaseDataBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tBaseDataBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ComUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args)
	{
	}

	public VData getResult()
	{
		return this.mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}		
}
