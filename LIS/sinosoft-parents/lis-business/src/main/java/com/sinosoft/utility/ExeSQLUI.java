package com.sinosoft.utility;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;

public class ExeSQLUI implements BusinessService {
private static Logger logger = Logger.getLogger(ExeSQLUI.class);
	private VData mInputData=new VData();
	public CErrors mErrors = new CErrors();

	public ExeSQLUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份

		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		ExeSQL tExeSQL=new ExeSQL();
		if(cOperate.equals("getOneValue"))
		{
			Object sql= mTransferData.getValueByName("SQL");
			if(sql instanceof String){
				String value=tExeSQL.getOneValue((String)sql);
				mInputData.add(value);
			}
			else if(sql instanceof SQLwithBindVariables){
				String value=tExeSQL.getOneValue((SQLwithBindVariables)sql);
				mInputData.add(value);
			}
		}
		else if(cOperate.equals("execSQL"))
		{
			Object sql= mTransferData.getValueByName("SQL");
			if(sql instanceof String){
				SSRS tSSRS=tExeSQL.execSQL((String)sql);
				mInputData.add(tSSRS);
			}
			else if(sql instanceof SQLwithBindVariables){
				SSRS tSSRS=tExeSQL.execSQL((SQLwithBindVariables)sql);
				mInputData.add(tSSRS);
			}
		}
		else if(cOperate.equals("execUpdateSQL"))
		{
			boolean bool = false;

			Object sql= mTransferData.getValueByName("SQL");
			if(sql instanceof String){
				bool=tExeSQL.execUpdateSQL((String)sql);
			}
			else if(sql instanceof SQLwithBindVariables){
				bool=tExeSQL.execUpdateSQL((SQLwithBindVariables)sql);
			}
			if(bool)
			{
				mInputData.add("true");
			}
			else
			{
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				mInputData.add("false");
			}
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
