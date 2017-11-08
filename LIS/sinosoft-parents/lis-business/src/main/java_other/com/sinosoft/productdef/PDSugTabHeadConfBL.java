



/**
 * <p>Title: PDUM</p>
 * <p>Description: 险种核保规则定义</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-14
 */
 
package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.db.PD_LMTabHeadRelaDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.lis.schema.PD_LMTabHeadRelaSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CommonBase;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.LDWorkCalendarSet;
import com.sinosoft.lis.vschema.PD_LMTabHeadRelaSet;


public class PDSugTabHeadConfBL    implements BusinessService{
 /** 错误处理类，每个需要错误处理的类中都放置该类 */
 public  CErrors mErrors=new CErrors();
 private VData mResult = new VData();
 /** 往后面传输数据的容器 */
 private VData mInputData= new VData();
 /** 全局数据 */
 private GlobalInput mGlobalInput =new GlobalInput() ;
 private MMap map=new MMap();
 
 private String mRiskCode = "";
 private PD_LMTabHeadRelaSet mPD_LMTabHeadRelaSet = new PD_LMTabHeadRelaSet();
 public PDSugTabHeadConfBL() {
 }

/**
* 传输数据的公共方法
 * @param: cInputData 输入的数据
   *         cOperate 数据操作
* @return:
*/
 public boolean submitData(VData cInputData,String cOperate)
 {
	 if (!getInputData(cInputData)) {
			this.mResult.add(0,this.mErrors.getFirstError());
			return false;
	 }

	if (!check()) {
		this.mResult.add(0,this.mErrors.getFirstError());
		return false;
	}
	
	if(!dealData())
	{
		this.mResult.add(0,this.mErrors.getFirstError());
		return false;
	}
	if(!prepareOutputData())
	{
		this.mResult.add(0,this.mErrors.getFirstError());
		return false;
	}
    
	PubSubmit tSubmit = new PubSubmit();
	if (!tSubmit.submitData(mInputData, ""))
	{ //数据提交
		// @@错误处理
		this.mErrors.copyAllErrors(tSubmit.mErrors);
		CError tError = new CError();
		tError.moduleName = "PDSugTabHeadConfBL";
		tError.functionName = "submitData";
		tError.errorMessage = "数据提交失败!";
		this.mErrors.addOneError(tError);
		return false;
	}
	return true;
}
 

 private boolean getInputData(VData cInputData) {
 	try {
 		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
 		mPD_LMTabHeadRelaSet = (PD_LMTabHeadRelaSet) cInputData.getObjectByObjectName("PD_LMTabHeadRelaSet", 0);

 		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDSugTabHeadConfBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
 		
 	} catch (Exception e) {
 		e.printStackTrace();
 		CError.buildErr(this, "获取数据出错!");
 		
 		return false;
 	}
 	
 	return true;
 }

 private boolean dealData() {
 	try {
 		//按照算法类型生成算法编码 
 		PD_LMTabHeadRelaSchema pD_LMTabHeadRelaSchema = new PD_LMTabHeadRelaSchema(); 
 		if(mPD_LMTabHeadRelaSet.size()>0){
 			pD_LMTabHeadRelaSchema = mPD_LMTabHeadRelaSet.get(1);
 			map.put("delete from PD_LMTabHeadRela where RiskCode='"+pD_LMTabHeadRelaSchema.getRiskCode()+"' and Type = '"+pD_LMTabHeadRelaSchema.getType()+"'","DELETE");
 		}
 		
 		map.put(mPD_LMTabHeadRelaSet, "INSERT");
 	} catch (Exception e) {
 		// TODO Auto-generated catch block
 		e.printStackTrace();
 		CError.buildErr(this, "获取数据出错!");
 		return false;
 	}
 	
 	return true;
 }
 
 private boolean check()
 {
  return true;
 }
 private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDSugTabHeadConfBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}


 public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
	
 public static void main(String[] args) {
 }
}


