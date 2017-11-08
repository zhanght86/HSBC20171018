/**
*
*   满期终止批处理
*    author zhangfh
*/  
package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.BqNameFun;
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

public class callTerminateTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(callTerminateTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mManageCom = "";
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private int XCount;
	public callTerminateTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  callTerminateTMThread Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		//日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
    	//日志监控,过程监控
//		日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"满期终止批处理开始","21");	
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='callTerminateCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("满期终止个单取的最大的数tConutMax:" + tConutMax);
		try {
			logger.debug("current thread"+this+"hashcode");
			logger.debug("start to mManageCom"+mManageCom+"-"+PubFun.getCurrentDate()+":"+PubFun.getCurrentTime());
			logger.debug("callTerminate 满期终止批处理运行开始。。。。");
			String subSql = (mManageCom != null && !mManageCom.trim().equals("")) ? (" and ManageCom like concat('"+"?mManageCom?"+"','%') ") : " ";
//			String strSql = "select * from LCPol a where  1=1 and contno = '86410320080210003899'"		
			String strSql = "select * from LCPol a where  1=1 "
					+ " and not exists(select 'X' from lccontstate where  enddate is null and state = '1' and  statetype = 'Available' and polno = a.polno) "// 失效状态下不参与满期终止
					+ " and not exists(select 'X' from lcconthangupstate where posflag = '1' and contno = a.contno) "// 保全挂起不满期终止
					//comment by jiaqiangli 已经在lcpol.appflag中排除掉
					//+ " and not exists(select 'X' from lccontstate where  enddate is null and state = '1' and  statetype = 'Terminate' and polno = a.polno) "//排除已经终止的保单 comment by jiaqiangli 2008-10-28
					+ " and a.AppFlag = '1' and a.EndDate <= '" + "?mCurrentDate?"
					+ "' " + subSql
					//+ "and rownum <= 100 order by contno,polno,mainpolno";
					+ "order by contno,polno,mainpolno";
			logger.debug("callTerminate strSql"+strSql);
			LCPolSet tLCPolSet = new LCPolSet();
			RSWrapper rsWrapper = new RSWrapper();
		// 传入结果集合,SQL
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(strSql);
			sqlbv2.put("mManageCom", mManageCom);
			sqlbv2.put("mCurrentDate", mCurrentDate);
		if (!rsWrapper.prepareData(tLCPolSet, sqlbv2)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
		rsWrapper.getData();
		VData tVData = new VData();
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			return false;
		}
		countJS=tLCPolSet.size();
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) 
			{
				XCount++;
				Map map = new HashMap();
				map.put("LCPolSchema", tLCPolSet.get(i));
				map.put("state", "03");
				map.put("GlobalInput", tG);
				tVData.add(map);
				
			    if(i%tConutMax==0)
				{
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
				}
					
				if(i%tConutMax!=0&&i==tLCPolSet.size())
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
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='callTerminatethreadCount'";
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
		 } while (tLCPolSet != null && tLCPolSet.size() > 0);
		rsWrapper.close();
     }catch (Exception ex) {
		ex.printStackTrace();
	} 
		logger.debug("结束业务逻辑处理!--满期终止批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "满期终止批处理完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "满期终止批处理"+String.valueOf(XCount)+"笔,共花费"+tt+"秒");
	
		return true;
	}

	public static void main(String[] args) {
		callTerminateTMThread tcallTerminateTMThread = new callTerminateTMThread();
		tcallTerminateTMThread.dealMain();
	}
}
