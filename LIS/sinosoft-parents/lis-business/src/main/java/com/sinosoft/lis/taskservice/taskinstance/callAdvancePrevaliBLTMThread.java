/**
*
*   失效预终止批处理
*   author zhangfh
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

public class callAdvancePrevaliBLTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(callAdvancePrevaliBLTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mManageCom = "";
	private int XCount=0;
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	public callAdvancePrevaliBLTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  callAdvancePrevaliBLTMThread Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		//日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
    	//日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"失效预终止批处理开始","01");	
		
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='callAdvancePrevaliBLTMCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("失效预终止个单取的最大的数tConutMax:" + tConutMax);
		try {
			logger.debug("callPrevaliBL 失效预终止批处理运行开始。。。。");
			String subSql = "";
			if (mManageCom != null && !mManageCom.trim().equals("")) {
				subSql = " and exists(select /*+index(lcpol,PK_LCPOL)*/'X' from lcpol where appflag = '1' and ManageCom like concat('"+"?mManageCom?"+"','%') and contno=lc.contno and polno = lc.polno and polno=mainpolno) ";
				
			} 
			else {
				subSql = " and exists(select /*+index(lcpol,PK_LCPOL)*/'X' from lcpol where appflag = '1' and contno=lc.contno and polno = lc.polno and polno=mainpolno) ";
			}
		
			//Available lccontstate.polno=lcpol.polno
			String strSql = "select /*+index(lc,LCCONTSTATE_INDEX_3)*/* from lccontstate lc where 1=1 "
					+ subSql
					//排除已经发过通知书的
					+ " and not exists (select 'X' from loprtmanager where othernotype = '"+"?othernotype?"+"' and code = '"+"?code?"+"' and otherno = lc.contno)"
					// 提前一个月发通知书
					+ " and lc.startdate <= add_months('"+"?mCurrentDate?"+"',-23)"
					+ " and lc.enddate is null and lc.state = '1' and lc.statetype = 'Available' "
					+ " and not exists(select 'X' from lcconthangupstate where posflag = '1' and contno = lc.contno) "
					//loprtmanager.otherno = lcpol.contno 按contno生成loprtmanager
					//循环主险
					//+ " and exists (select 1 from lcpol lp where lc.contno=lp.contno and lc.polno=lp.polno and lp.polno=lp.mainpolno) "
					//+ " and rownum <= 100"
					;
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(strSql);
			sqlbv2.put("mManageCom", mManageCom);
			sqlbv2.put("othernotype", PrintManagerBL.ONT_CONT);
			sqlbv2.put("code", PrintManagerBL.CODE_PEdorPreInvali);
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
//        if (tLCContStateSet == null || tLCContStateSet.size() <= 0)
//        {
//        	return false;
//        }
		VData tVData = new VData();
		countJS=tLCContStateSet.size();
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			for (int i = 1; i <= tLCContStateSet.size(); i++) 
			{
				// 逐条调用保单失效处理
				XCount++;
				Map map = new HashMap();
				map.put("LCContStateSchema", tLCContStateSet.get(i));
				map.put("state", "01");
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
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='callAdvancePrevaliBLTMThread'";
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
		logger.debug("结束业务逻辑处理!--失效预终止批处理" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "失效预终止批处理完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	PubFun.logResult(tG,tG.LogID[1], "失效预终止批处理"+String.valueOf(XCount)+"笔,共花费"+tt+"秒");

		return true;
	}

	public static void main(String[] args) {
		callAdvancePrevaliBLTMThread tcallAdvancePrevaliBLTMThread = new callAdvancePrevaliBLTMThread();
		tcallAdvancePrevaliBLTMThread.dealMain();
	}
}
