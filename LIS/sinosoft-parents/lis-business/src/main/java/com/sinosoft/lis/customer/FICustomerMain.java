package com.sinosoft.lis.customer;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class FICustomerMain  implements BusinessService
{
private static Logger logger = Logger.getLogger(FICustomerMain.class);

	public CErrors mErrors = new CErrors();

	private MMap mMap = new MMap();
	
	private VData mResult = new VData();

	/**
	 * 新契约子类（FICustomerNB）、续期子类（FICustomerRN）、保全子类（FICustomerBQ）、理赔子类（FICustomerLP）、
	 * 业务应付（FICustomerYF）、余额领取（FICustomerLQ）、财务（FICustomerFI）、调用类（FICustomerMain）
	 */
	public boolean submitData(VData cInputData, String cFlagValue)
	{
		String classNameStr = "com.sinosoft.lis.customer.FICustomer" + cFlagValue;
		try
		{
			FICustomer tFICustomer = (FICustomer) Class.forName(classNameStr).newInstance();
			if (!tFICustomer.submitData(cInputData, cFlagValue))
			{
				if (tFICustomer.mErrors.needDealError())
				{
					this.mErrors.copyAllErrors(tFICustomer.mErrors);
				}
				else
				{
					buildError("FICustomerMain", "dealAccount", "客户账户处理失败！");
				}
				return false;
			}
			else
			{
				mMap.add(tFICustomer.getMMap());
				mResult.add(mMap);
			}
		}
		catch (ClassNotFoundException e)
		{
			logger.debug("找不到类" + classNameStr);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public void buildError(String moduleName, String szFunc, String szErrMsg)
	{
		CError cError = new CError();

		cError.moduleName = moduleName;
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public MMap getMMap()
	{
		return mMap;
	}
	
	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String arg[])
	{

	}

}
