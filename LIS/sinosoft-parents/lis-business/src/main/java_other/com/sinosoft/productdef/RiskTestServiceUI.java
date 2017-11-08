

package com.sinosoft.productdef;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RiskTestServiceUI implements BusinessService {
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();

	public RiskTestServiceUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		/*
		  String tLanguage = "en";
	IbrmsTestService testService = new IbrmsTestService();
	testService.setLanguage(tLanguage);
	testService.getRuleInfo(request);
		 */
		
		
		HttpServletRequest request = (HttpServletRequest)cInputData.get(0);
//		String tLanguage = (String)cInputData.get(1);
		
		RiskTestService testService = new RiskTestService("");
		//testService.setLanguage(tLanguage);
		
		
		
		if(!cOperate.equals("test"))
		{
		
		
		TransferData resTransferData = new TransferData();
//		resTransferData.setNameAndValue("writeTestScript", twriteTestScript);
//		resTransferData.setNameAndValue("writeWinInfo", twriteWinInfo);
//		resTransferData.setNameAndValue("writeStep", twriteStep);
//		resTransferData.setNameAndValue("writeRuleInfo", twriteRuleInfo);
//		resTransferData.setNameAndValue("writeFormStepInfo", twriteFormStepInfo);
		
		mInputData.add(resTransferData);
	
		}
		else
		{
			
			//String json = testService.test(request);
			//mInputData.add(json);
		}
		return true;
	}

	public static void main(String[] args) {
		//
		RiskTestService testService = new RiskTestService("PPUL");
		System.out.println(testService.initAppntScript());
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}
}
