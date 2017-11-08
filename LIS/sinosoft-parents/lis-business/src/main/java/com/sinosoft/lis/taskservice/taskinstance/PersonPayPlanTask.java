package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.get.AliveAccAssignBL;
import com.sinosoft.lis.get.PersonPayPlanUI;
import com.sinosoft.lis.get.PersonPaytoAccBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LDComSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PersonPayPlanTask extends TaskThread {
private static Logger logger = Logger.getLogger(PersonPayPlanTask.class);
	public CErrors mErrors = new CErrors();
	private String tCalDate = PubFun.getCurrentDate();
	private String tCurrentdate = PubFun.getCurrentDate();
	private LDComSet tLDComSet = new LDComSet();
	
	public PersonPayPlanTask() {
	}

	public boolean dealMain() {
		
		
		FDate tFDate = new FDate();
		
		String tParamManageCom = (String) mParameters.get("ManageCom");
		logger.debug("The manageCom is :" + tParamManageCom);
		queryManageCom(tParamManageCom);
		String tParamCalDate = (String) mParameters.get("CalDate");
		
		if (tParamCalDate == null || tParamCalDate.equals("")) {
			//add by jiaqiangli 2009-03-26 提前45天催付
			tParamCalDate = PubFun.calDate(tCalDate, 45, "D", null);
		}
		
		GlobalInput tGI = new GlobalInput();
		VData tVData = new VData();
		tGI.Operator = "001";
		 //日志监控,初始化           	
		tGI.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
		tGI.LogID[1]=(String)mParameters.get("SerialNo"); 
		
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("timeEnd", tParamCalDate);
		
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCGetSchema tLCGetSchema = new LCGetSchema();
		
		int tComSize = this.tLDComSet.size();
		
		TransferData tOtherTransferData = new TransferData();
		VData tOtherVData = new VData();
		
		//for (int i = 1; i <= tComSize; i++) {
			
			//logger.debug("PersonPayPlanTask to deal" + tLDComSet.get(i).getComCode());
			
			//tGI.ManageCom = tLDComSet.get(i).getComCode();
			//机构循环时执行多次影响效率
		    tGI.ManageCom = "86";
			tGI.ComCode = tGI.ManageCom;
			tVData.clear();
			tVData.addElement(tGI);
			tVData.addElement(tTransferData);
			tVData.addElement(tLCPolSchema);
			tVData.addElement(tLCGetSchema);
//			日志监控，性能监控
			PubFun.logPerformance (tGI,tGI.LogID[1],"生存金催付批处理开始","1");
			PersonPayPlanUI tPersonPayPlanUI = new PersonPayPlanUI();
			try {
				if (tPersonPayPlanUI.submitData(tVData, "INSERT||PERSON") == false) {
					logger.debug("PersonPayPlanTask deal error" + tGI.ManageCom);
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				logger.debug("PersonPayPlanTask deal error" + tGI.ManageCom);
			}

			//add by jiaqiangli 2009-03-24 进行生存金入账户批处理
			logger.debug("come to PersonPaytoAccBL");
			String ljsget_sql = "select * from ljsget a where managecom like concat('"
					+ "?managecom?"
					//add by jiaqiangli 2009-03-31 排除团险
					+ "','%') and exists(select getnoticeno from ljsgetdraw b where b.grpcontno='00000000000000000000' and a.getnoticeno=b.getnoticeno) order by getdate ";
			  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		 		sqlbv1.sql(ljsget_sql);
		 		sqlbv1.put("managecom", tGI.ManageCom);
			LJSGetSet temp_LJSGetSet = new LJSGetSet();
			LJSGetDB tLJSGetDB = new LJSGetDB();

			temp_LJSGetSet = tLJSGetDB.executeQuery(sqlbv1);
//			日志监控，性能监控
			PubFun.logPerformance (tGI,tGI.LogID[1],"生存金入账户批处理开始","2");
	    	int tSuc = 0, tFail = 0;	
			for (int j = 1; j <= temp_LJSGetSet.size(); j++) {
				LJSGetSchema tLJSGetSchema = temp_LJSGetSet.get(j);
				if (tFDate.getDate(tLJSGetSchema.getGetDate()).compareTo(tFDate.getDate(tCurrentdate)) <= 0) {
					logger.debug("tLJSGetSchema.getGetNoticeNo()"+tLJSGetSchema.getGetNoticeNo()+"into lcinsureacc");
					tOtherTransferData.removeByName("GetNoticeNo");
					tOtherTransferData.setNameAndValue("GetNoticeNo", tLJSGetSchema.getGetNoticeNo());
					tOtherVData.clear();
					tOtherVData.add(tOtherTransferData);
					tOtherVData.add(tGI);
					PersonPaytoAccBL tPersonPaytoAccBL = new PersonPaytoAccBL();
					if (!tPersonPaytoAccBL.submitData(tOtherVData, "")) {
						CError.buildErr(this, "生存金入帐户失败,原因是:" + tPersonPaytoAccBL.mErrors.getFirstError());
						logger.debug("生存金入帐户失败,原因是:" + tPersonPaytoAccBL.mErrors.getFirstError());
						continue;
					}
					tSuc++;
//					 日志监控,状态监控                 		
         			PubFun.logState(tGI,tLJSGetSchema.getGetNoticeNo(), "生存金(通知书号：" +tLJSGetSchema.getGetNoticeNo()+ ")转入领取帐户中","1");  
					//add by jiaqiang 2009-03-30
//					//add by jiaqiang 2009-03-30 进行银行转帐授权的自动转账处理
//					LCContDB tLCContDB = new LCContDB();
//					tLCContDB.setContNo(tLJSGetSchema.getOtherNo());//保单号
//					tLCContDB.getInfo();
//					tOtherTransferData.removeByName("BalaDate");
//					tOtherTransferData.setNameAndValue("BalaDate", tCurrentdate);
//					tOtherVData.clear();
//					tOtherVData.add(tGI);
//					tOtherVData.add(tLCContDB.getSchema());
//					tOtherVData.add(tOtherTransferData);
//					AliveAccAssignBL tAliveAccAssignBL = new AliveAccAssignBL();
//					//add by jiaqiangli 非银行转帐的continue
//					tAliveAccAssignBL.submitData(tOtherVData, "");
				}
				else {
					logger.debug(tLJSGetSchema.getGetNoticeNo() + "尚未到应领日期，不入帐户！");
//					 日志监控,状态监控                 		
         			PubFun.logState(tGI,tLJSGetSchema.getGetNoticeNo(), "生存金(通知书号：" +tLJSGetSchema.getGetNoticeNo()+ ")尚未到应领日期，不入帐户","0");  
					tFail++;
					continue;
				}
			}
			logger.debug("PersonPayPlanTask end deal" + tGI.ManageCom);
//			日志监控,结果监控
			PubFun.logResult (tGI,tGI.LogID[1],"共有"+tSuc+"笔生存金转入领取帐户中");
			PubFun.logResult (tGI,tGI.LogID[1],"共有"+tFail+"笔生存金尚未到应领日期，不入帐户");	
		//}
		return true;
	}

	private void queryManageCom(String tManagecom) {
		String tSQL = "select * from ldcom where length(comcode) = 8 order by comcode ";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	 		sqlbv2.sql(tSQL);
		if (tManagecom != null && !tManagecom.equals(""))
			tSQL = "select * from ldcom where length(comcode) = 8 and comcode like concat('" + "?tManagecom?" + "','%') order by comcode ";
		sqlbv2.sql(tSQL);
		sqlbv2.put("tManagecom", tManagecom);
		LDComDB tLDComDB = new LDComDB();
		this.tLDComSet = tLDComDB.executeQuery(sqlbv2);
	}
	
	public static void main(String[] args) {
		PersonPayPlanTask tPersonPayPlanTask = new PersonPayPlanTask();
		tPersonPayPlanTask.dealMain();
	}
}
