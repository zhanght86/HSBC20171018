



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
import java.util.Hashtable;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CommonBase;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


public class PDSugIncomeFactorBL    implements BusinessService{
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
 
 private String mTableName = "";
 private String mRiskCode = "";
 
 private TransferData mTransferData = new TransferData();
 
 public PDSugIncomeFactorBL() {
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
    
    //进行业务处理
    CommonBase commonBase = new CommonBase();
    boolean result = commonBase.submitData(cInputData, cOperate);
	if(!result){
		this.mErrors.addOneError("PDSugIncomeFactorBL.submitData处理失败，" + commonBase.mErrors.getFirstError());
	    this.mResult.add(0,commonBase.mErrors.getFirstError());
	    return false;
	}
 	new RiskState().setState(this.mRiskCode, "契约业务控制->收益算法定义->收益因子定义->新增页面", "1");
    return true;
}
 

 private boolean getInputData(VData cInputData) {
 	try {
 		mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
 		this.mTableName = (String)mTransferData.getValueByName("tableName");
 		this.mRiskCode  = (String)mTransferData.getValueByName("RiskCode");
 		Hashtable tHashtable = new Hashtable();
 		tHashtable.put("RiskCode", mRiskCode);
 		String tSQL_RiskName = "select riskname from pd_lmrisk where riskcode='"+mRiskCode+"'";
 		tHashtable.put("RiskName", (new ExeSQL()).getOneValue(tSQL_RiskName));
 		mTransferData.setNameAndValue("Hashtable", tHashtable);
 		ArrayList tList = new ArrayList();
 		tList.add(mRiskCode);
 		tList.add((String)mTransferData.getValueByName("ITEMCODE"));
 		tList.add((String)mTransferData.getValueByName("CALORDERNO"));
		tList.add((String)mTransferData.getValueByName("CALELEMENT"));
		tList.add((String)mTransferData.getValueByName("ELEMENTPROPERTY"));
		tList.add((String)mTransferData.getValueByName("ADJUSTPOSITION"));
		tList.add((String)mTransferData.getValueByName("VARIABLECODE"));
		tList.add((String)mTransferData.getValueByName("CALSQL"));
		tList.add((String)mTransferData.getValueByName("SQLEXCUTETYPE"));
		tList.add((String)mTransferData.getValueByName("STARTPOINT"));
		tList.add((String)mTransferData.getValueByName("STARTPOINTFLAG"));
		tList.add((String)mTransferData.getValueByName("ENDPOINT"));
		tList.add((String)mTransferData.getValueByName("ENDPOINTFLAG"));
		tList.add((String)mTransferData.getValueByName("INITVALUE"));
		tList.add((String)mTransferData.getValueByName("STEPVALUE"));
		tList.add((String)mTransferData.getValueByName("RESULTPRECISION"));
		tList.add((String)mTransferData.getValueByName("REMARK"));
		mTransferData.setNameAndValue("list", tList);
 		
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


