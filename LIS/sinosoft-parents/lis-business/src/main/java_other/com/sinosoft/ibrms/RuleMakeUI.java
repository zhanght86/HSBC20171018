package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class RuleMakeUI implements BusinessService{
private static Logger logger = Logger.getLogger(RuleMakeUI.class);

	public CErrors mErrors = new CErrors();
	private VData mResult=new VData();

	public RuleMakeUI() {

	}

	public boolean submitData(VData tVData,String Operater) {

		if (tVData == null) {
			logger.debug("\t@> RuleMakeUI.submitData() :传入的数据为空！");
			return false;
		} else {
			VData TVData = new VData();
			TVData = (VData) tVData.clone();

			RuleMakeBL tRuleMakeBL = new RuleMakeBL();
			tRuleMakeBL.submitData(TVData);
			mResult = tRuleMakeBL.getResult();
		}
		return false;
	}
	
	public VData getResult() {
		
		return this.mResult;
	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		logger.debug("//////////////"+mErrors.getFirstError());
		return mErrors;
	}

}
