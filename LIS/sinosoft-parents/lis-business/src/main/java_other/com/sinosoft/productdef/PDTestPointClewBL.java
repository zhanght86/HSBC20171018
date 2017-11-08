

/**
 * <p>Title: PDTestPointClew</p>
 * <p>Description: 测试要点提示</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */
 
package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class PDTestPointClewBL  {
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
 
 public PDTestPointClewBL() {
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
	 PD_TestPointClew_LibSchema tPD_TestPointClew_LibSchema = (PD_TestPointClew_LibSchema)transferData.getValueByName("PD_TestPointClew_LibSchema");
	 
	 GlobalInput tG = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
	 
	 PD_TestPointClew_LibDB tPD_TestPointClew_LibDB = new PD_TestPointClew_LibDB();
	 tPD_TestPointClew_LibDB.setSchema(tPD_TestPointClew_LibSchema);
	 
	 tPD_TestPointClew_LibSchema.setModifyDate(PubFun.getCurrentDate());
	 tPD_TestPointClew_LibSchema.setModifyTime(PubFun.getCurrentTime());
	 
	 if(cOperate.equals("save"))
	 {
		 if(tPD_TestPointClew_LibDB.getInfo())
		 {
			 this.mErrors.addOneError("数据中已经存在相同记录，请不要重复插入");
			 return false;
		 }
		 
		 tPD_TestPointClew_LibSchema.setOperator(tG.Operator);
		 tPD_TestPointClew_LibSchema.setMakeDate(PubFun.getCurrentDate());
		 tPD_TestPointClew_LibSchema.setMakeTime(PubFun.getCurrentTime());
		 
		 String sql = "select max(Id) from Pd_Testpointclew_Lib where tablecode = '"
			 			+ tPD_TestPointClew_LibSchema.getTableCode() + "' and fieldcode = '"
			 			+ tPD_TestPointClew_LibSchema.getFieldCode() + "'";
		 
		 ExeSQL exec = new ExeSQL();
		 String result = exec.getOneValue(sql);
		 String newId = "1";
		 if(!result.equals(""))
		 {
			 newId = String.valueOf(Integer.parseInt(result) + 1);
		 }
		 
		 tPD_TestPointClew_LibSchema.setId(newId);
		 
		 tPD_TestPointClew_LibDB.setSchema(tPD_TestPointClew_LibSchema);
		 
		 if(!tPD_TestPointClew_LibDB.insert())
		 {
			 this.mErrors.addOneError(tPD_TestPointClew_LibDB.mErrors.getFirstError());
			 return false;
		 }
	 }
	 else if(cOperate.equals("update"))
	 {
		 if(!tPD_TestPointClew_LibDB.getInfo())
		 {
			 this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
			 return false;
		 }
		 
		 PD_TestPointClew_LibSchema oldPD_TestPointClew_LibSchema = tPD_TestPointClew_LibDB.getSchema();
		 
		 tPD_TestPointClew_LibSchema.setModifyDate(PubFun.getCurrentDate());
		 tPD_TestPointClew_LibSchema.setModifyTime(PubFun.getCurrentTime());
		 tPD_TestPointClew_LibSchema.setMakeDate(oldPD_TestPointClew_LibSchema.getMakeDate());
		 tPD_TestPointClew_LibSchema.setMakeTime(oldPD_TestPointClew_LibSchema.getMakeTime());
		 tPD_TestPointClew_LibSchema.setOperator(oldPD_TestPointClew_LibSchema.getOperator());
		 tPD_TestPointClew_LibSchema.setId(oldPD_TestPointClew_LibSchema.getId());
		 
		 tPD_TestPointClew_LibDB.setSchema(tPD_TestPointClew_LibSchema);
		 
		 if(!tPD_TestPointClew_LibDB.update())
		 {
			 this.mErrors.addOneError(tPD_TestPointClew_LibDB.mErrors.getFirstError());
			 return false;
		 }
	 }
	 else if(cOperate.equals("del"))
	 {
		 if(!tPD_TestPointClew_LibDB.getInfo())
		 {
			 this.mErrors.addOneError("数据中不存在该记录，无法删除");
			 return false;
		 }
		 
		 if(!tPD_TestPointClew_LibDB.delete())
		 {
			 this.mErrors.addOneError(tPD_TestPointClew_LibDB.mErrors.getFirstError());
			 return false;
		 }
	 }
	 
	 return true;
 }
 

 public static void main(String[] args) {
 }
}
