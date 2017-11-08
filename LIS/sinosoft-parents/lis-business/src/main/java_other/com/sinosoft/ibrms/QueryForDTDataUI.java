package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class QueryForDTDataUI implements BusinessService {
private static Logger logger = Logger.getLogger(QueryForDTDataUI.class);
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();

	public QueryForDTDataUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		/*
		  String DTTableName=request.getParameter("DTTableName");
	 String LRTemplateID=request.getParameter("LRTemplateID");

	 String json= queryClass.queryDTData(DTTableName,LRTemplateID);

		 */
		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		ExeSQL tExeSQL=new ExeSQL();
		String DTTableName = (String)mTransferData.getValueByName("DTTableName");
		String LRTemplateID = (String)mTransferData.getValueByName("LRTemplateID");
		
		
		QueryForDTData tQueryForDTData=new QueryForDTData();

		 String json= tQueryForDTData.queryDTData(DTTableName,LRTemplateID);
		 mInputData.add(json);
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
