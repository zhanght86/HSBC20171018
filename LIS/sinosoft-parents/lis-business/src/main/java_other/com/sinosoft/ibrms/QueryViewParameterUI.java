package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class QueryViewParameterUI implements BusinessService {
private static Logger logger = Logger.getLogger(QueryViewParameterUI.class);
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();

	public QueryViewParameterUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		//tring tableName, String id,String tLan
		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		ExeSQL tExeSQL=new ExeSQL();
		String tableName = (String)mTransferData.getValueByName("tableName");
		String id = (String)mTransferData.getValueByName("id");
		String tLan = (String)mTransferData.getValueByName("tLan");
		
		QueryViewParameter queryViewParameter=new QueryViewParameter();

		String viewParameter=queryViewParameter.queryForViewParameter(tableName,id,tLan);
		mInputData.add(viewParameter);
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
