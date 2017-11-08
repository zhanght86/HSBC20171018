package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class IbrmsTestServiceUI implements BusinessService {
private static Logger logger = Logger.getLogger(IbrmsTestServiceUI.class);
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();

	public IbrmsTestServiceUI() {
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
		String tLanguage = (String)cInputData.get(1);
		
		IbrmsTestService testService = new IbrmsTestService();
		testService.setLanguage(tLanguage);
		
		if(!cOperate.equals("test"))
		{
		testService.getRuleInfo(request);
		
		String twriteTestScript = testService.writeTestScript(request);
		String twriteWinInfo = testService.writeWinInfo(request);
		String twriteStep = testService.writeStep(request);
		String twriteRuleInfo = testService.writeRuleInfo(request);
		String twriteFormStepInfo = testService.writeFormStepInfo(request);
		
		TransferData resTransferData = new TransferData();
		resTransferData.setNameAndValue("writeTestScript", twriteTestScript);
		resTransferData.setNameAndValue("writeWinInfo", twriteWinInfo);
		resTransferData.setNameAndValue("writeStep", twriteStep);
		resTransferData.setNameAndValue("writeRuleInfo", twriteRuleInfo);
		resTransferData.setNameAndValue("writeFormStepInfo", twriteFormStepInfo);
		
		mInputData.add(resTransferData);
	
		}
		else
		{
			
			String json = testService.test(request);
			mInputData.add(json);
		}
		return true;
	}

	public static void main(String[] args) {
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
