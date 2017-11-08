package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PubFun1UI implements BusinessService {
private static Logger logger = Logger.getLogger(PubFun1UI.class);
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();

	public PubFun1UI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		//tring tableName, String id,String tLan
		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		
		if(cOperate.equals("CreateRuleCalCode"))
		{
			String tModulName= (String)mTransferData.getValueByName("ModulName");
			
			
			String tNo = PubFun1.CreateRuleCalCode(tModulName);
			mInputData.add(tNo);
		}
		else
		{
			String tRuleName= (String)mTransferData.getValueByName("RuleName");
		
		
			String RuleId = PubFun1.CreateMaxNo("ibrms" + tRuleName, 4);
			mInputData.add(RuleId);
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
