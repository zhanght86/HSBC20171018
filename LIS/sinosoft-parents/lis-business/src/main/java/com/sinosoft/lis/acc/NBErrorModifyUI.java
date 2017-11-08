package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class NBErrorModifyUI implements BusinessService
{
private static Logger logger = Logger.getLogger(NBErrorModifyUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public NBErrorModifyUI()
	{}

	public boolean submitData(VData cInputData, String operate)
	{
		NBErrorModifyBL tNBErrorModifyBL = new NBErrorModifyBL();
		tNBErrorModifyBL.submitData(cInputData, operate);
		if (tNBErrorModifyBL.mErrors.needDealError())
		{
			this.mErrors.copyAllErrors(tNBErrorModifyBL.mErrors);
			return false;
		}

		return true;
	}
	
	public CErrors getCErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		logger.debug("//////////////"+mErrors.getFirstError());
		return mErrors;
	}
}
