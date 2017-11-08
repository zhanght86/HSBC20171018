

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_LMRiskAccFundDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_LMRiskAccFundSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PD_LMRiskAccFundBL {
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
	 private PD_LMRiskAccFundSchema mPD_LMRiskAccFundSchema=null;
	 public PD_LMRiskAccFundBL() {
	 }

	/**
	* 传输数据的公共方法
	 * @param: cInputData 输入的数据
	   *         cOperate 数据操作
	* @return:
	*/
	 public boolean submitData(VData cInputData,String cOperate)
	 {
	  mOperate=cOperate;
	 if(!getInputData(cInputData)){
		 return false;
	 }	    //进行业务处理
	 if(!check()){
		 return false;
	 }
	    if(!deal()){
	    	mErrors.addOneError("客户与基金关联业务处理出错!");
	    	return false;
	    }	
        mResult.add(map);
        PubSubmit tSubmit = new PubSubmit();
        if(!tSubmit.submitData(mResult)){
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "PD_LMRiskAccFundBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            System.out.println(mErrors.getContent());
            return false;
        }
	    
	    return true;
	}
	 private boolean check() {
		if(null==mOperate||"".equals(mOperate))return false;
		return true;
	}

private boolean deal() {
	
	PD_LMRiskAccFundSchema tPD_LMRiskAccFundSchema=new PD_LMRiskAccFundSchema();
	PD_LMRiskAccFundDB tPD_LMRiskAccFundDB=new PD_LMRiskAccFundDB();
	tPD_LMRiskAccFundDB.setPayPlanCode(mPD_LMRiskAccFundSchema.getPayPlanCode());
	tPD_LMRiskAccFundDB.setRiskCode(mPD_LMRiskAccFundSchema.getRiskCode());
	tPD_LMRiskAccFundDB.setAccountCode(mPD_LMRiskAccFundSchema.getAccountCode());

if("SAVE".equals(mOperate.toUpperCase())){	//保存
	if(tPD_LMRiskAccFundDB.getInfo()){
		this.mErrors.addOneError("已经存在该数据，不能做保存操作！请做修改或者删除操作！");
		return false;
	}
	tPD_LMRiskAccFundSchema.setAccountCode(mPD_LMRiskAccFundSchema.getAccountCode());
	tPD_LMRiskAccFundSchema.setAccountName(mPD_LMRiskAccFundSchema.getAccountName());
	tPD_LMRiskAccFundSchema.setPayPlanCode(mPD_LMRiskAccFundSchema.getPayPlanCode());
	tPD_LMRiskAccFundSchema.setAccountType(mPD_LMRiskAccFundSchema.getAccountType());
	tPD_LMRiskAccFundSchema.setRiskCode(mPD_LMRiskAccFundSchema.getRiskCode());
	tPD_LMRiskAccFundSchema.setOperator(this.mGlobalInput.Operator);
	tPD_LMRiskAccFundSchema.setMakeDate(PubFun.getCurrentDate());
	tPD_LMRiskAccFundSchema.setMakeTime(PubFun.getCurrentTime());
	tPD_LMRiskAccFundSchema.setModifyDate(PubFun.getCurrentDate());
	tPD_LMRiskAccFundSchema.setModifyTime(PubFun.getCurrentTime());	
	map.put(tPD_LMRiskAccFundSchema, "INSERT");
}else if("UPDATE".equals(mOperate.toUpperCase())){//更新
	if(!tPD_LMRiskAccFundDB.getInfo()){
		this.mErrors.addOneError("没有查询到相关数据！");
		return false;
	}
	tPD_LMRiskAccFundSchema=tPD_LMRiskAccFundDB.getSchema();
	tPD_LMRiskAccFundSchema.setAccountCode(mPD_LMRiskAccFundSchema.getAccountCode());
	tPD_LMRiskAccFundSchema.setAccountName(mPD_LMRiskAccFundSchema.getAccountName());
	tPD_LMRiskAccFundSchema.setPayPlanCode(mPD_LMRiskAccFundSchema.getPayPlanCode());
	tPD_LMRiskAccFundSchema.setAccountType(mPD_LMRiskAccFundSchema.getAccountType());
	tPD_LMRiskAccFundSchema.setRiskCode(mPD_LMRiskAccFundSchema.getRiskCode());
	tPD_LMRiskAccFundSchema.setOperator(this.mGlobalInput.Operator);
	tPD_LMRiskAccFundSchema.setModifyDate(PubFun.getCurrentDate());
	tPD_LMRiskAccFundSchema.setModifyTime(PubFun.getCurrentTime());	
	map.put(tPD_LMRiskAccFundSchema, "UPDATE");
}else if("DEL".equals(mOperate.toUpperCase())||"DELETE".equals(mOperate.toUpperCase())){//删除
	if(!tPD_LMRiskAccFundDB.getInfo()){
		this.mErrors.addOneError("没有查询到相关数据！");
		return false;
	}

	map.put(tPD_LMRiskAccFundDB.getSchema(), "DELETE");
}else {
	this.mErrors.addOneError("传入操作符错误！");
	return false;
} 
return true;
	}
private boolean getInputData(VData cInputData){
		 this.mPD_LMRiskAccFundSchema=(PD_LMRiskAccFundSchema)cInputData.getObjectByObjectName("PD_LMRiskAccFundSchema", 0);
		 mGlobalInput=(GlobalInput)cInputData.get(0);
		 return true;
	 }
public String getErrorMessage(){
	return this.mErrors.getFirstError();
}
}
