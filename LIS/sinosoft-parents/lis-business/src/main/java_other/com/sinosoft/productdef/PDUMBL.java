



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
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.RiskState;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CommonBase;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


public class PDUMBL    implements BusinessService{
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
 private String mCalCode = "";
 private String mCalCodeType = "";
 
 private TransferData mTransferData = new TransferData();
 
 public PDUMBL() {
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
 
 if(!result)
 {
  this.mErrors.addOneError("PDUMBL.submitData处理失败，" + commonBase.mErrors.getFirstError());
  this.mResult.add(0,commonBase.mErrors.getFirstError());
  return false;
 }
 new RiskState().setState(this.mRiskCode, "契约业务控制->险种核保规则", "1");
    return true;
}
 

 private boolean getInputData(VData cInputData) {
 	try {
 		mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
 		this.mTableName = (String)mTransferData.getValueByName("tableName");
 		this.mCalCodeType = (String)mTransferData.getValueByName("CalCodeType");
 		this.mRiskCode  = (String)mTransferData.getValueByName("RiskCode");
 		Hashtable tHashtable = new Hashtable();
 		tHashtable.put("RiskCode", mRiskCode);
 		
 		String tSQL_RiskName = "select  case count(1) when 0 then '000000' else max(riskname) end  from pd_lmrisk where riskcode='"+mRiskCode+"'";
 		
 		tHashtable.put("RiskName", (new ExeSQL()).getOneValue(tSQL_RiskName));
 		
 		mTransferData.setNameAndValue("Hashtable", tHashtable);
 		//tongmeng 2011-07-13 modify
 		String tCalCode = mTransferData.getValueByName("CALCODE")==null?"":(String)mTransferData.getValueByName("CALCODE");
 		if(tCalCode.equals(""))
 		{
 			tCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
 			mTransferData.removeByName("CALCODE");
 			mTransferData.setNameAndValue("CALCODE", tCalCode);
 			this.mResult.add(0,tCalCode);
 		}
 		
 		else
 		{
 			//校验算法类型和算法编码的关系
 			if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
 			||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
 			{
 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
 				return false;
 			}
 			this.mResult.add(0,tCalCode);
 		}
 		/*

 		 */
 		//为了复用CommonBase,在这里还是先准备个List
 		ArrayList<String> tList = new ArrayList<String>();
 		
 		String relationtype=(String)mTransferData.getValueByName("RELAPOLTYPE");
 		if(!("IF".equals(relationtype)||"SM".equals(relationtype))){
 	 		tList.add((String)mTransferData.getValueByName("UWCODE"));
 	 		tList.add((String)mTransferData.getValueByName("CALCODE"));
 	 		tList.add((String)mTransferData.getValueByName("RELAPOLTYPE"));
 	 		tList.add((String)mTransferData.getValueByName("UWTYPE"));
 	 		tList.add((String)mTransferData.getValueByName("UWORDER"));
 	 		tList.add((String)mTransferData.getValueByName("REMARK"));
 	 		//tList.add((String)mTransferData.getValueByName("RISKCODE"));
 	 		tList.add((String)mTransferData.getValueByName("STANDBYFLAG1"));
 	 		tList.add((String)mTransferData.getValueByName("STANDBYFLAG2"));
 		}else{
 		tList.add(mRiskCode);
 		if("SM".equals(relationtype)){
 	 		tList.add("BQAlert");
 		}else{
 	 		tList.add((String)mTransferData.getValueByName("UWTYPE")+"Insert");
 		}
 		tList.add((String)mTransferData.getValueByName("UWORDER"));
	 	tList.add((String)mTransferData.getValueByName("UWCODE"));
 		tList.add((String)mTransferData.getValueByName("RELAPOLTYPE"));
 		tList.add((String)mTransferData.getValueByName("REMARK"));
 		tList.add((String)mTransferData.getValueByName("RISKCODE"));
 		tList.add((String)mTransferData.getValueByName("STANDBYFLAG1"));
 		tList.add((String)mTransferData.getValueByName("STANDBYFLAG2"));
 		}
 		
 		mTransferData.setNameAndValue("list", tList);
 		
 		
 		
 	} catch (Exception e) {
 		// TODO Auto-generated catch block
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

