



package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class RiskFaceDefUI implements BusinessService{

	private VData mResult = new VData();
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息
	/* 保存线程号和连接池名映射 */
	private Hashtable mRuleMap = new Hashtable();
	private SSRS mRuleSSRS = new SSRS();
	
	ArrayList mRiskParamsList = new ArrayList();
	
	private String mRiskCode = "";
	private JSONObject items; 
	public RiskFaceDefUI(){
		
	}
	
	/**
	* 传输数据的公共方法
	 * @param: cInputData 输入的数据
	   *         cOperate 数据操作
	* @return:
	* 
	* cOperate : InitRiskParams  初始化险种参数
	* 
	*/
	 public boolean submitData(VData cInputData,String cOperate)
	 {
		 /*
		  * 初始化险种要素选择界面
		  */
		 if(cOperate.equals("InitRiskParams"))
		 {
			 String RiskCode = (String)cInputData.get(0);
				RiskParamsBL mRiskParamsBL = new RiskParamsBL(RiskCode);
				this.mResult.add(mRiskParamsBL.getItems());
		 }
		 else  if(cOperate.equals("InitRiskDutyParams"))
		 {
			 String RiskCode = (String)cInputData.get(0);
			 String StandbyFlag1 = (String)cInputData.get(1);
			 RiskParamsBL mRiskParamsBL = new RiskParamsBL(RiskCode,StandbyFlag1);
			 this.mResult.add(mRiskParamsBL.getDutyItems());
		 }
			 
		 /**
		  * 险种要素选择界面 "保存" 按钮
		  */
		 else if(cOperate.equals("SaveRiskParams"))
		 {
			 RiskParamsBL mRiskParamsBL = new RiskParamsBL();
			 mRiskParamsBL.submitData(cInputData,"");
		 }
		 /**
		  * 险种界面定义 点击 "保存" 按钮 
		  */
		 else if(cOperate.equals("SaveRiskFace"))
		 {
			 RiskParamsBL mRiskParamsBL = new RiskParamsBL();
			 mRiskParamsBL.submitData(cInputData,"");
		 }
		 
		 else  if(cOperate.equals("InitRiskInputParams"))
		 {
			 String RiskCode = (String)cInputData.get(0);
			 String StandbyFlag1 = (String)cInputData.get(1);
			 RiskFaceDefService tTest = new RiskFaceDefService(RiskCode,StandbyFlag1);
			 String tRiskString = tTest.initRiskScript();
			 String tDutyString = tTest.initDutyScript();
			 String tPayString = tTest.initPayScript();
			// String tFuncString = tTest.initFuncScript();
			 
			 JSONArray jsonArray = new JSONArray();   
			 Map<String,String> item = new HashMap<String,String>();
				
			 item.put("inputType","Risk");
			 item.put("detail",tRiskString);
			 jsonArray.add(0,item);
	
			 item = new HashMap<String,String>();
			 item.put("inputType","Duty");
			 item.put("detail",tDutyString);
			 jsonArray.add(1,item);
			 
			 item = new HashMap<String,String>();
			 item.put("inputType","Pay");
			 item.put("detail",tPayString);
			 jsonArray.add(2,item);
			 
			 this.mResult.add(jsonArray);
		 }
		 
		 else if(cOperate.equals("RiskFaceDefService"))
		 {
			 RiskFaceDefService mRiskFaceDefService = new RiskFaceDefService();
			 mRiskFaceDefService.savRiskOrder(cInputData);
		 }
		 
		 else if(cOperate.equals("InitPayGrid"))
		 {
			 String RiskCode = (String)cInputData.get(0);
			 String tSQL = "select a.dutycode, a.payplancode, a.payplanname, '' from pd_lmdutypayrela a "
				         + " where dutycode in (select dutycode from pd_lmriskduty where riskcode = '"+RiskCode+"' and SpecFlag = 'N')";
			 ExeSQL tExeSQL = new ExeSQL();
			 SSRS tSSRS = new SSRS();
			 tSSRS = tExeSQL.execSQL(tSQL);
			 
			 List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			 
			 for(int i=1;i<=tSSRS.getMaxRow();i++)
			 {
				 
				 Map<String,String> item = new HashMap<String,String>();
					String riskParamCode = tSSRS.GetText(i, 2);
					String riskParamName = tSSRS.GetText(i, 3);
					String showFlag = tSSRS.GetText(i, 4);
					
					//String riskParamsShow = this.buildShow(showFlag);
					
					item.put("idx",String.valueOf(i));
					item.put("DutyCode",tSSRS.GetText(i, 1));
					item.put("PayPlanCode",tSSRS.GetText(i, 2));
					item.put("PayPlanName",tSSRS.GetText(i, 3));
					item.put("Prem",tSSRS.GetText(i, 4));
					list.add(item);
				}	
					
					
				//}
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("total",list.size()); 
				jsonMap.put("rows",list);
				try {
					items = JSONObject.fromObject(jsonMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 this.mResult.add(items);
		 }
		 else if(cOperate.equals("InitDutyGrid"))
		 {
			 String RiskCode = (String)cInputData.get(0);
			 String tSQL = "select a.dutycode, a.dutyname, '' from pd_lmduty a "
				         + " where dutycode in (select dutycode from pd_lmriskduty where riskcode = '"+RiskCode+"' and SpecFlag = 'N')";
			 ExeSQL tExeSQL = new ExeSQL();
			 SSRS tSSRS = new SSRS();
			 tSSRS = tExeSQL.execSQL(tSQL);
			 
			 List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			 
			 for(int i=1;i<=tSSRS.getMaxRow();i++)
			 {
				 
				 Map<String,String> item = new HashMap<String,String>();
					
					item.put("idx",String.valueOf(i));
					item.put("DutyCode",tSSRS.GetText(i, 1));
					item.put("DutyName",tSSRS.GetText(i, 2));
					item.put("Prem",tSSRS.GetText(i, 3));
					list.add(item);
				}	
					
					
				//}
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("total",list.size()); 
				jsonMap.put("rows",list);
				try {
					items = JSONObject.fromObject(jsonMap);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 this.mResult.add(items);
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}

