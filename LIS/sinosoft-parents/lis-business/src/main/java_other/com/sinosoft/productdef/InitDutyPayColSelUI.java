

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

public class InitDutyPayColSelUI implements BusinessService{

	private VData mResult = new VData();
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息
	/* 保存线程号和连接池名映射 */
	private Hashtable mRuleMap = new Hashtable();
	private SSRS mRuleSSRS = new SSRS();
	
	ArrayList mRiskParamsList = new ArrayList();
	
	private String mRiskCode = "";
	private JSONObject items; 
	public InitDutyPayColSelUI(){
		
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

		  if(cOperate.equals("Duty"))
		 {
			 String RiskCode = (String)cInputData.get(0);
			 
			// String tFuncString = tTest.initFuncScript();
			 
			 //获取责任列表
			 String tDutyScript = getDutySel(RiskCode);
			 JSONArray jsonArray = new JSONArray();   
			 Map<String,String> item = new HashMap<String,String>();
				
			 item.put("inputType","Duty");
			 item.put("detail",tDutyScript);
			 jsonArray.add(0,item);
			 //获取缴费列表
			 String tPayScript = getPaySel(RiskCode);
			 item = new HashMap<String,String>();
			 item.put("inputType","Pay");
			 item.put("detail",tPayScript);
			 jsonArray.add(1,item);
//			 
//			 item = new HashMap<String,String>();
//			 item.put("inputType","Pay");
//			 item.put("detail",tPayString);
//			 jsonArray.add(2,item);
			 
			 this.mResult.add(jsonArray);
		 }
		  else  if(cOperate.equals("InitDuty"))
		 {
			 String RiskCode = (String)cInputData.get(0);
				 
				// String tFuncString = tTest.initFuncScript();
				 
				 //获取责任列表
				 String tInitScript = "";
				 
				 String tDutySQL = "select colcode,colname,colproperties from pdt_riskshowcol where showtype='Duty' "
					             + " and templateid in (select templateid from pdt_risktypetemplate where risktype='"+RiskCode+"') "
					             + " order by colorder ";
				 ExeSQL tExeSQL = new ExeSQL();
				 SSRS tSSRS = new SSRS();
				 tSSRS = tExeSQL.execSQL(tDutySQL);
				 
				 
				 tInitScript = prepareDutyGrid(tSSRS);
				 JSONArray jsonArray = new JSONArray();   
				 Map<String,String> item = new HashMap<String,String>();
					
				 item.put("inputType","InitDuty");
				 item.put("detail",tInitScript);
				 jsonArray.add(0,item);
				 
				 this.mResult.add(jsonArray);
			 }
		  else  if(cOperate.equals("InitPay"))
			 {
				 String RiskCode = (String)cInputData.get(0);
					 
					// String tFuncString = tTest.initFuncScript();
					 
					 //获取责任列表
					 String tInitScript = "";
					 
					 String tDutySQL = "select colcode,colname,colproperties from pdt_riskshowcol where showtype='Pay' "
						             + " and templateid in (select templateid from pdt_risktypetemplate where risktype='"+RiskCode+"') "
						             + " order by colorder ";
					 ExeSQL tExeSQL = new ExeSQL();
					 SSRS tSSRS = new SSRS();
					 tSSRS = tExeSQL.execSQL(tDutySQL);
					 
					 
					 tInitScript = preparePayGrid(tSSRS);
					 JSONArray jsonArray = new JSONArray();   
					 Map<String,String> item = new HashMap<String,String>();
						
					 item.put("inputType","InitPay");
					 item.put("detail",tInitScript);
					 jsonArray.add(0,item);
					 
					 this.mResult.add(jsonArray);
				 }
		
		 
		 
		 return true;
	}

	private String prepareDutyGrid(SSRS tSSRS) {
		String tInitScript;
		tInitScript = "<table id='dutyGrid' style='height:auto'> "
			         + " <thead>"
					 + " <tr> "
		             + " <th field='idx' width='50px'>序号</th> ";
		 for(int i=1;i<=tSSRS.getMaxRow();i++)
		 {
			 String tColCode = tSSRS.GetText(i, 1);
			 String tColName = tSSRS.GetText(i, 2);
			 String tColProperties = tSSRS.GetText(i, 3);
			 
			// String tTemp = "<th field='"+tColCode+"' width='"+tColProperties+"'>"+tColName+"</th> ";
			 String tTemp = "<th field='"+tColCode+"' width='"+tColProperties+"'>"+tColName+"</th> ";
			 if(!tColProperties.equals("0px"))
			 {
				 tInitScript = tInitScript + tTemp;
			 }
		 }
		 tInitScript = tInitScript + "</tr>"
		             + "</thead>"
		             + "</table>";
		return tInitScript;
	}
	
	private String preparePayGrid(SSRS tSSRS) {
		String tInitScript;
		tInitScript = "<table id='payGrid' style='height:auto'> "
			         + " <thead>"
					 + " <tr> "
		             + " <th field='idx' width='50px'>序号</th> ";
		 for(int i=1;i<=tSSRS.getMaxRow();i++)
		 {
			 String tColCode = tSSRS.GetText(i, 1);
			 String tColName = tSSRS.GetText(i, 2);
			 String tColProperties = tSSRS.GetText(i, 3);
			 
			// String tTemp = "<th field='"+tColCode+"' width='"+tColProperties+"'>"+tColName+"</th> ";
			 String tTemp = "<th field='"+tColCode+"' width='"+tColProperties+"'>"+tColName+"</th> ";
			 if(!tColProperties.equals("0px"))
			 {
				 tInitScript = tInitScript + tTemp;
			 }
		 }
		 tInitScript = tInitScript + "</tr>"
		             + "</thead>"
		             + "</table>";
		return tInitScript;
	}
	

	private String getDutySel(String RiskCode) {
		String tSQL = " select code,codename,codealias, "
			         + " nvl((select 1 from pdt_riskshowcol where showtype='Duty' and colcode=a.code and colproperties!='0px' and templateid in "
			         + " (select templateid from pdt_risktypetemplate where risktype='"+RiskCode+"')),0)"
			         + " from ldcode a where codetype='riskparamscol'  "
			         + " order by othersign/1";
		 SSRS tDutySSRS = new SSRS();
		 ExeSQL tExeSQL = new ExeSQL();
		 tDutySSRS = tExeSQL.execSQL(tSQL);
		 String tDutyScript = "";
		 tDutyScript = "<table class= common align=center>";
			 
		 for(int i=1;i<=tDutySSRS.getMaxRow();i++)
		 {
			 String tColCode = tDutySSRS.GetText(i, 1);
			 String tColName = tDutySSRS.GetText(i, 2);
			 String tColAlias = tDutySSRS.GetText(i, 3);
			 String tCheckFlag = tDutySSRS.GetText(i, 4);
			 if(tCheckFlag.equals("0"))
			 {
				 tCheckFlag = "";
			 }
			 else
			 {
				 tCheckFlag = "checked";
			 }
			 if(i==1)
			 {
				 tDutyScript = tDutyScript + " <tr class=common> ";
			 }
			 
			 tDutyScript = tDutyScript + "<td class= common width=25%> <input type=checkbox name='checkBoxDuty' "
			                           //+ " id='"+tColCode+"' checked='"+tCheckFlag+"' >"+tColName
			 						   + " id='Duty_"+tColCode+"'  "+tCheckFlag+"  >"+tColName
			 		                   + " </td> ";
			 
			 if(i%4==0)
		     {	
				tDutyScript = tDutyScript  + " </tr> "
		    		        + " <tr class=common> ";
		    		
		     }
			 if(i==tDutySSRS.getMaxRow())
		 	 {	
				tDutyScript = tDutyScript  + " </tr> ";
		 	 }
		 }
		 
		 tDutyScript = tDutyScript + "</table>";
		 System.out.println("tDutyScript:"+tDutyScript);
		return tDutyScript;
	}
			
	private String getPaySel(String RiskCode) {
		String tSQL = " select code,codename,codealias, "
			         + " nvl((select 1 from pdt_riskshowcol where showtype='Pay' and colcode=a.code and colproperties!='0px' and templateid in "
			         + " (select templateid from pdt_risktypetemplate where risktype='"+RiskCode+"')),0)"
			         + " from ldcode a where codetype='riskparamscol1' "
			         + " order by othersign/1";
		 SSRS tPaySSRS = new SSRS();
		 ExeSQL tExeSQL = new ExeSQL();
		 tPaySSRS = tExeSQL.execSQL(tSQL);
		 String tPayScript = "";
		 tPayScript = "<table class= common align=center>";
			 
		 for(int i=1;i<=tPaySSRS.getMaxRow();i++)
		 {
			 String tColCode = tPaySSRS.GetText(i, 1);
			 String tColName = tPaySSRS.GetText(i, 2);
			 String tColAlias = tPaySSRS.GetText(i, 3);
			 String tCheckFlag = tPaySSRS.GetText(i, 4);
			 if(tCheckFlag.equals("0"))
			 {
				 tCheckFlag = "";
			 }
			 else
			 {
				 tCheckFlag = "checked";
			 }
			 if(i==1)
			 {
				 tPayScript = tPayScript + " <tr class=common> ";
			 }
			 
			 tPayScript = tPayScript + "<td class= common width=25%> <input type=checkbox name='checkBoxPay' "
			                           //+ " id='"+tColCode+"' checked='"+tCheckFlag+"' >"+tColName
			 						   + " id='Pay_"+tColCode+"'  "+tCheckFlag+" >"+tColName
			 		                   + " </td> ";
			 
			 if(i%4==0)
		     {	
				 tPayScript = tPayScript  + " </tr> "
		    		        + " <tr class=common> ";
		    		
		     }
			 if(i==tPaySSRS.getMaxRow())
		 	 {	
				 tPayScript = tPayScript  + " </tr> ";
		 	 }
		 }
		 
		 tPayScript = tPayScript + "</table>";
		 System.out.println("tDutyScript:"+tPayScript);
		return tPayScript;
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
