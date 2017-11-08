/**
*
*   失效终止批处理
*
*/
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.f1print.PrintManagerBL;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class callPrevaliBLTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(callPrevaliBLTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mManageCom = "";
	private int XCount=0;
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	public callPrevaliBLTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  callPrevaliBLTMThread Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		//日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
//		日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"失效终止批处理开始","11");	
		
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='callPrevaliBLCount' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("失效终止个单取的最大的数tConutMax:" + tConutMax);
		try {
			logger.debug("callPrevaliBL 失效终止批处理运行开始。。。。");
			String subSql = "";
			if (mManageCom != null && !mManageCom.trim().equals("")) {
				subSql = " and exists(select /*+index(lcpol,PK_LCPOL)*/'X' from lcpol where appflag = '1' and ManageCom like concat('"+"?mManageCom?"+"','%') and polno = lc.polno) ";
			} 
			else {
				subSql = " and exists(select /*+index(lcpol,PK_LCPOL)*/'X' from lcpol where appflag = '1' and polno = lc.polno) ";
			}
			
			//Available lccontstate.polno=lcpol.polno
			String strSql = "select /*+index(lc,LCCONTSTATE_INDEX_3)*/* from lccontstate lc where 1=1 "
					+ subSql
					//+ " and not exists (select 'X' from loprtmanager where othernotype = '00' and code = 'BQ29' and otherno = polno)"
					+ " and startdate <= add_months('"+"?mCurrentDate?"+"',-24)"
					+ " and enddate is null and state = '1' and statetype = 'Available' "
					+ " and not exists(select 'X' from lcconthangupstate where posflag = '1' and contno = lc.contno) "
					//+ " and rownum <= 100"
					;
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(strSql);
			sqlbv2.put("mManageCom", mManageCom);
			sqlbv2.put("mCurrentDate", mCurrentDate);
			logger.debug("strSql"+strSql);
			LCContStateSet tLCContStateSet = new LCContStateSet();
			RSWrapper rsWrapper = new RSWrapper();
		// 传入结果集合,SQL
		if (!rsWrapper.prepareData(tLCContStateSet, sqlbv2)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
        do {
		rsWrapper.getData();
//		if (tLCContStateSet == null || tLCContStateSet.size() <= 0) {
//			return false;
//		}
		VData tVData = new VData();
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			for (int i = 1; i <= tLCContStateSet.size(); i++) 
			{
				// 逐条调用保单失效处理
				XCount++;
				Map map = new HashMap();
				map.put("LCContStateSchema", tLCContStateSet.get(i));
				map.put("state", "02");
				map.put("GlobalInput", tG);
				tVData.add(map);
				
			    if(i%tConutMax==0)
				{
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
				}
					
				if(i%tConutMax!=0&&i==tLCContStateSet.size())
				{
						mTaskWaitList.add(tVData);
						tVData = new VData();
				} 
			}
		}
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		countJS=tLCContStateSet.size();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='ContInvaliStatehreadCount'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (countJS < tThreadCount) {
			tThreadCount = countJS;
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.bq.ContInvaliBLMultTMThread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();
        } while (tLCContStateSet != null && tLCContStateSet.size() > 0);
		rsWrapper.close();
     }catch (Exception ex) {
		ex.printStackTrace();
	} 
		logger.debug("结束业务逻辑处理!--失效终止批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "失效终止批处理完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "失效终止批处理"+XCount+"笔,共花费"+tt+"秒");

		return true;
	}

	public static void main(String[] args) {
		callPrevaliBLTMThread tcallPrevaliBLTMThread = new callPrevaliBLTMThread();
		tcallPrevaliBLTMThread.dealMain();
	}
}
