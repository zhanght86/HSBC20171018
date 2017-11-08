

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_TestPlanClew_LibDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.PD_TestPlanClew_LibSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDTestPlanClewBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	 public  CErrors mErrors=new CErrors();
	 private VData mResult = new VData();
	 /** 往后面传输数据的容器 */
	 private VData mInputData= new VData();
	 /** 全局数据 */
	 private GlobalInput mGlobalInput =new GlobalInput() ;
	 /** 数据操作字符串 */
	 private String mOperate;
	 /** 业务处理相关变量 */
	 private MMap map=new MMap();
	 
	 public PDTestPlanClewBL() {
	 }

	/**
	* 传输数据的公共方法
	* @param: cInputData 输入的数据
	*         cOperate 数据操作
	* @return:
	*/
	 public boolean submitData(VData cInputData,String cOperate)
	 {
		try
		{
		    if(!check())
		    {
		     return false;
		    }
		    
		    //进行业务处理
		    if(!deal(cInputData,cOperate))
		    {
		   	 return false;
		    }
		}
		catch(Exception ex)
		{
			this.mErrors.addOneError(ex.getMessage());
			return false;
		}

		return true;
	}
	 
	 private boolean check()
	 {
	  return true;
	 }
	 
	 private boolean deal(VData cInputData, String cOperate)
	 {
		 TransferData transferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
		 PD_TestPlanClew_LibSchema tPD_TestPlanClew_LibSchema = (PD_TestPlanClew_LibSchema)transferData.getValueByName("PD_TestPlanClew_LibSchema");
		 
		 GlobalInput tG = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
		 
		 PD_TestPlanClew_LibDB tPD_TestPlanClew_LibDB = new PD_TestPlanClew_LibDB();
		 tPD_TestPlanClew_LibDB.setSchema(tPD_TestPlanClew_LibSchema);
		 tPD_TestPlanClew_LibSchema.setMODIFYDATE(PubFun.getCurrentDate());
		 tPD_TestPlanClew_LibSchema.setMODIFYTIME(PubFun.getCurrentTime());
		 	 
		 if(cOperate.equals("save"))
		 {
			 if(tPD_TestPlanClew_LibDB.getInfo())
			 {
				 this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
				 return false;
			 }
			 
			 tPD_TestPlanClew_LibSchema.setMAKEDATE(PubFun.getCurrentDate());
			 tPD_TestPlanClew_LibSchema.setMAKETIME(PubFun.getCurrentTime());
			 tPD_TestPlanClew_LibSchema.setOPERATOR(tG.Operator);;
			 
			 String sql = "select max(t.clewcontentcode) from pd_testplanclew_lib t ";
			 
			 ExeSQL exec = new ExeSQL();
			 String result = exec.getOneValue(sql);
			 String newId = "1";
			 if(!result.equals(""))
			 {
				 newId = String.valueOf(Integer.parseInt(result) + 1);
			 }
			 
			 tPD_TestPlanClew_LibSchema.setClewContentCode(newId);
			 
			 tPD_TestPlanClew_LibDB.setSchema(tPD_TestPlanClew_LibSchema);
			 
			 if(!tPD_TestPlanClew_LibDB.insert())
			 {
				 this.mErrors.addOneError(tPD_TestPlanClew_LibDB.mErrors.getFirstError());
				 return false;
			 }
		 }
		 else if(cOperate.equals("update"))
		 {
			 if(!tPD_TestPlanClew_LibDB.getInfo())
			 {
				 this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
				 return false;
			 }
			 
			 PD_TestPlanClew_LibSchema oldPD_TestPlanClew_LibSchema = tPD_TestPlanClew_LibDB.getSchema();
			 
			 tPD_TestPlanClew_LibSchema.setMODIFYDATE(PubFun.getCurrentDate());
			 tPD_TestPlanClew_LibSchema.setMODIFYTIME(PubFun.getCurrentTime());
			 tPD_TestPlanClew_LibSchema.setMAKEDATE(oldPD_TestPlanClew_LibSchema.getMAKEDATE());
			 tPD_TestPlanClew_LibSchema.setMAKETIME(oldPD_TestPlanClew_LibSchema.getMAKETIME());
			 tPD_TestPlanClew_LibSchema.setOPERATOR(oldPD_TestPlanClew_LibSchema.getOPERATOR());
			 tPD_TestPlanClew_LibSchema.setClewContentCode(oldPD_TestPlanClew_LibSchema.getClewContentCode());
			 
			 tPD_TestPlanClew_LibDB.setSchema(tPD_TestPlanClew_LibSchema);
			 
			 if(!tPD_TestPlanClew_LibDB.update())
			 {
				 this.mErrors.addOneError(tPD_TestPlanClew_LibDB.mErrors.getFirstError());
				 return false;
			 }
		 }
		 else if(cOperate.equals("del"))
		 {
			 if(!tPD_TestPlanClew_LibDB.getInfo())
			 {
				 this.mErrors.addOneError("数据中不存在该记录，无法删除");
				 return false;
			 }
			 
			 if(!tPD_TestPlanClew_LibDB.delete())
			 {
				 this.mErrors.addOneError(tPD_TestPlanClew_LibDB.mErrors.getFirstError());
				 return false;
			 }
		 }
		 
		 return true;
	 }
	 

}
