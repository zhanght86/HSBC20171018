package com.sinosoft.lis.taskservice.taskinstance;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class BQAutoConfirmTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(BQAutoConfirmTMThread.class);
	public CErrors mErrors = new CErrors();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mManageCom = "";
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	public BQAutoConfirmTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  BQAutoConfirmTMThread Start!!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		//日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	
    	//日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"保全确认自动运行程序业务处理开始","1");
		
		//启动时间        	
		Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
    	int countJS=0;
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='BQAutoConfirmTMCount' ";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		 sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("保全确认自动运行程序个单取的最大的数tConutMax:" + tConutMax);
		try {
			logger.debug("current thread"+this+"hashcode");
			logger.debug("start to mManageCom"+mManageCom+"-"+PubFun.getCurrentDate()+":"+PubFun.getCurrentTime());
			logger.debug("callTerminate 保全确认自动运行程序运行开始。。。。");
            String Data_sql="";
            Data_sql = " select b.edoracceptno, a.missionid, a.submissionid  from lwmission a, lpedorapp b, lpedormain c "
            +" where a.missionprop1 = b.edoracceptno and b.edoracceptno = c.edoracceptno "
            +" and activityid = '0000000009' and processid = '0000000000'  "
            +" and defaultoperator is null "
            +" and (c.uwstate in ('9','2') or  c.uwstate='4' and exists(select 1 from lpcuwmaster d4 "
            +"         where d4.edoracceptno = b.edoracceptno and customeridea = '1')) "
            +" and exists(select 1 from LPAppUWMasterMain where EdorAcceptNo = b.edoracceptno and LPAppUWMasterMain.State='9') "
            +" order by a.MakeDate, a.MakeTime" 
	          ;
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
   		    sqlbv2.sql(Data_sql);
            SSRS data_ssrs = new SSRS();
            data_ssrs = tExeSQL.execSQL(sqlbv2);
            logger.debug("ssss");
//            if (data_ssrs == null ||data_ssrs.MaxRow <= 0) {
//				return false;
//			}
            VData tVData = new VData();
            countJS=data_ssrs.MaxRow;
            for(int i=1;i<=data_ssrs.MaxRow;i++)
            {
            	String sTemplatePath = "xerox/printdata";//application.getRealPath("xerox/printdata") + "/";
            	String tEdorAcceptNo=data_ssrs.GetText(i, 1);
            	String tMissionID = data_ssrs.GetText(i, 2);
            	String tSubMissionID = data_ssrs.GetText(i, 3);
            	//经过确认，操作机构取受理号前6位
            	tG.ComCode = data_ssrs.GetText(i, 1).substring(0, 6);
            	tG.ManageCom = data_ssrs.GetText(i, 1).substring(0, 6);
            	
                // 日志监控,过程监控
        		PubFun.logTrack (tG,tEdorAcceptNo,"开始确认受理号为"+tEdorAcceptNo+"的保全件");
        		
            	//由于在自动运行的同时，界面也可能有并发的保全操作，因此必须对队列中的任务做一个确认前的校验
            	//1，该受理号下的确认节点还存在，若没有，说明被手工确认或者撤销掉了，那么此处跳过，不予处理
            	//2，该确认节点的defaultoperator必须是空，否则说明是等待期间内该受理号被手工申请到个人工作池，此种也不予处理
            	String check_sql="  select 1 from lwmission a where a.missionprop1='"+"?tEdorAcceptNo?"+"' "
            	+" and activityid = '0000000009' and processid = '0000000000' and a.defaultoperator is null ";
            	 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        		    sqlbv3.sql(check_sql);
        		    sqlbv3.put("tEdorAcceptNo", tEdorAcceptNo);
            	String check = tExeSQL.getOneValue(sqlbv3);
            	if(!"1".equals(check))
            	{
            		logger.debug("受理号为"+tEdorAcceptNo+"的确认节点不符合校验条件，跳过");
            		continue;
            	}
               	TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
                tTransferData.setNameAndValue("MissionID", tMissionID);
                tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
                tTransferData.setNameAndValue("TemplatePath", sTemplatePath);
                tTransferData.setNameAndValue("ActivityID", "0000000009");
                
                String fmtransact = "INSERT||EDORCONFIRM";
     
                //add by jiaqiangli 2009-06-10 为防止并发
                PubConcurrencyLock mPubLock = new PubConcurrencyLock();

               	Map map = new HashMap();
				map.put("TransferData", tTransferData);
				map.put("GlobalInput", tG);
				tVData.add(map);
                
			    if(i%tConutMax==0)
				{
	            		mTaskWaitList.add(tVData);
	            		tVData = new VData();
				}
					
				if(i%tConutMax!=0&&i==data_ssrs.MaxRow)
				{
						mTaskWaitList.add(tVData);
						tVData = new VData();
				} 
			
		}
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='BQAutoConfirmTMCount'";
		 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		    sqlbv4.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv4);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (countJS < tThreadCount) {
			tThreadCount = countJS;
		}
		this.mServiceA = new ServiceA(
				"com.sinosoft.workflow.bq.EdorWorkFlowUIMthread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();
		mPubLock.unLock();				
		logger.debug("结束业务逻辑处理!--保全确认自动运行程序" + PubFun.getCurrentDate() + "**"
				+ PubFun.getCurrentTime());
		 //日志监控,过程监控	
		PubFun.logTrack(tG, tG.LogID[1], "保全确认自动运行批处理完毕");
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	logger.debug("ss");
    	PubFun.logResult(tG,tG.LogID[1], "保全确认自动运行处理"+String.valueOf(countJS)+"笔,共花费"+tt+"秒");
	}catch (Exception ex) {
			ex.printStackTrace();
	} 
		return true;
	}

	public static void main(String[] args) {
		BQAutoConfirmTMThread tBQAutoConfirmTMThread = new BQAutoConfirmTMThread();
		tBQAutoConfirmTMThread.dealMain();
	}
}
