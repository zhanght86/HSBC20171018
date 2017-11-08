package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LCPerInvestPlanUI
{
private static Logger logger = Logger.getLogger(LCPerInvestPlanUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public LCPerInvestPlanUI()
	{}

	public boolean submitData(VData cInputData, String operate)
	{
		LCPerInvestPlanBL tLCPerInvestPlanBL = new LCPerInvestPlanBL();
		tLCPerInvestPlanBL.submitData(cInputData, operate);
		if (tLCPerInvestPlanBL.mErrors.needDealError())
		{
			this.mErrors.copyAllErrors(tLCPerInvestPlanBL.mErrors);
			return false;
		}

		return true;
	}

}
