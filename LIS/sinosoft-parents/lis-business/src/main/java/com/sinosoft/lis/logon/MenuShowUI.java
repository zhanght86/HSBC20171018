package com.sinosoft.lis.logon;
import org.apache.log4j.Logger;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
public class MenuShowUI implements BusinessService {
private static Logger logger = Logger.getLogger(MenuShowUI.class);

		private VData mInputData=new VData();
		public CErrors mErrors = new CErrors();

		public MenuShowUI() {
		}

		// 传输数据的公共方法
		public boolean submitData(VData cInputData, String cOperate) 
		{
			// 首先将数据在本类中做一个备份
			TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
			MenuShow tMenuShow=new MenuShow();
			if(cOperate.equals("Favorite"))
			{
				String UserCode=(String)mTransferData.getValueByName("UserCode");
				String value=tMenuShow.getFavorite(UserCode);
				mInputData.add(value);
			}
			else if(cOperate.equals("SQL"))
			{
				String UserCode=(String)mTransferData.getValueByName("UserCode");
				TransferData tTransferData=tMenuShow.getSQL(UserCode);
				mInputData.add(tTransferData);
			}
			else if(cOperate.equals("LDMenu"))
			{
				
				SQLwithBindVariables sqlbva = new SQLwithBindVariables();
				if(mTransferData.getValueByName("SQL") instanceof SQLwithBindVariables){
					sqlbva = (SQLwithBindVariables)mTransferData.getValueByName("SQL");
				}else{
					String sql=(String)mTransferData.getValueByName("SQL");
					sqlbva.sql(sql);
				}
				String value=tMenuShow.getLDMenu(sqlbva);
				mInputData.add(value);
			}else if(cOperate.equals("FavoriteTop"))
			{
				String UserCode=(String)mTransferData.getValueByName("UserCode");
				String value=tMenuShow.getFavoriteTop(UserCode);
				mInputData.add(value);
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
