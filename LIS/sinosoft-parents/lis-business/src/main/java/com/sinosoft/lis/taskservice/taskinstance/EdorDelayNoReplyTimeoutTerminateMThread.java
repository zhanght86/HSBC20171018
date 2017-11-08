package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;
/**
  *
  * 保全试算数据清理批处理及保全未使用无扫描申请数据清理 --线程版
  *
**/
public class EdorDelayNoReplyTimeoutTerminateMThread extends TaskThread {
private static Logger logger = Logger.getLogger(EdorDelayNoReplyTimeoutTerminateMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private String mCurrentDate = PubFun.getCurrentDate();
    int tConutMax = 100;
    int XCount = 100;
	public EdorDelayNoReplyTimeoutTerminateMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--保全客户延期未响应逾期终止批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	  	PubFun.logPerformance (tG,tG.LogID[1],"保全客户延期未响应逾期终止批处理开始","1");
//		日志监控，性能监控
    	PubFun.logTrack(tG,tG.LogID[1],"保全客户延期未响应逾期终止批处理启动");
    	logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理启动");
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("CUOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理, 查询逾期终止天数失败! ");
            //日志监控,过程监控        
    	  	PubFun.logTrack(tG,tG.LogID[1],"保全客户延期未响应逾期终止批处理, 系统未设置逾期终止天数");
            return false;
        }

		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='EdorDelayNoReplyTimeoutTerminateCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		logger.debug("保全客户延期未响应逾期终止批处理的最大的数tConutMax:" + tConutMax);
		try {
		 //查询出当前系统等待保全试算数据清理的纪录
		  String QuerySQL = new String("");
	        QuerySQL = "select * "
	                 +   "from LPEdorApp a "
	                
	                 +  "where OtherNoType = '3' "
	                 +    "and EdorState = '3' "
	                 //+    "and EdorAppDate <= (to_date('" + mCurrentDate + "','yyyy-mm-dd') - " + sTimeoutDays + ")"
	                 // modify by jiaqiangli 2009-05-13 EdorAppDate日期是可以往前指的
	                 +    "and MakeDate <= subdate(to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') , " + "?sTimeoutDays?" + ")"
	                 +    "and not exists (select 1 from loprtmanager where otherno=a.otherno and code='BQ37' and standbyflag1=a.edoracceptno) "
	                 + "union select * "
	                 +   "from LPEdorApp a "
	                
	                 +  "where OtherNoType = '3' "
	                 +    "and EdorState = '3' "
	                 //+    "and EdorAppDate <= (to_date('" + mCurrentDate + "','yyyy-mm-dd') - " + sTimeoutDays + ")"
	                 +    "and MakeDate <= subdate(to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') , " + "?sTimeoutDays?" + ")"
	                 +    "and exists (select 1 from loprtmanager where stateflag='2' and otherno=a.otherno and code='BQ37' and standbyflag1=a.edoracceptno) ";
	        //add by jiaqiangli 2009-04-09 未下发函件逾期或已下发函件且已回销逾期 stateflag = '2'
	        //logger.debug(QuerySQL);
	        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(QuerySQL);
			sqlbv2.put("sTimeoutDays", sTimeoutDays);
			sqlbv2.put("mCurrentDate", mCurrentDate);
	        RSWrapper rsWrapper = new RSWrapper();
	        LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
	        if (!rsWrapper.prepareData(tLPEdorAppSet, sqlbv2))
	        {
	            logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理, 数据准备失败! ");
	            return false;
	        }
	    do {
        rsWrapper.getData();
//        if (tLPEdorAppSet == null || tLPEdorAppSet.size() <= 0)
//        {
//        	return false;
//        }
		Vector mTaskWaitList = new Vector();
        VData tVData = new VData();
        int xLength=tLPEdorAppSet.size();
        for (int i = 1; i <= tLPEdorAppSet.size(); i++)
        {
                //LPEdorAppSchema
        	XCount++;
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema.setSchema(tLPEdorAppSet.get(i));
                //TransferData
                TransferData tTransferData = new TransferData();
                //VData
				// 准备传输数据 VData
            	Map map = new HashMap();
				map.put("LPEdorAppSchema", tLPEdorAppSchema);
				map.put("TransferData", tTransferData);
				map.put("GlobalInput", tG);
				tVData.add(map);
		     	if(i%tConutMax==0)
				{
            		mTaskWaitList.add(tVData);
            		tVData = new VData();
				}
				
				if(i%tConutMax!=0&&i==tLPEdorAppSet.size())
				{
					mTaskWaitList.add(tVData);
					tVData = new VData();
				} 
        }
        rsWrapper.close();
        rsWrapper = null;
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='EdorDelayNoReplyTimeoutTerminateThread'";
		  SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (xLength < tThreadCount) {
			tThreadCount = xLength;
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.bq.PEdorOverDueBLTMThread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();
        } while (tLPEdorAppSet != null && tLPEdorAppSet.size() > 0);
		rsWrapper.close();
     }catch (Exception ex) {
		ex.printStackTrace();
	} 

        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "保全试算数据清理批处理"+XCount+"笔,共花费"+tt+"秒");
	  	PubFun.logTrack(tG,tG.LogID[1],"保全客户延期未响应逾期终止批处理完毕");
		return true;
	}
	

    /**
     * 获取各种保全逾期终止批处理的逾期天数
     */
    private String getEdorTypeTimeoutDays(String sEdorType)
    {
        String sResultTimeoutDays = new String("");

        //查询逾期天数计算代码
        String QuerySQL = "select * from LMEdorCal where CalType = '" + "?sEdorType?" + "'";
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(QuerySQL);
		sqlbv4.put("sEdorType", sEdorType);
        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
        try
        {
            tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv4);
        }
        catch (Exception ex)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 执行 SQL 查询出错");
            logger.debug("\t   错误原因 : " + tLMEdorCalDB.mErrors.getFirstError());
            return null;
        }
        if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 未知的保全类型！ 查询逾期天数失败！");
            return null;
        }
        else
        {
            String sTempCalCode = tLMEdorCalSet.get(1).getCalCode();
            if (sTempCalCode == null || sTempCalCode.trim().equals(""))
            {
                logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 查询逾期天数计算代码失败！");
                return null;
            }
            else
            {
                //根据计算代码计算逾期天数
                Calculator tCalculator = new Calculator();
                tCalculator.setCalCode(sTempCalCode);
                sResultTimeoutDays = tCalculator.calculate();
                if (sResultTimeoutDays == null || sResultTimeoutDays.trim().equals(""))
                {
                    logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 根据计算代码计算逾期天数失败！");
                    return null;
                }
                tCalculator = null;
            }
        }
        tLMEdorCalDB = null;
        tLMEdorCalSet = null;

        return sResultTimeoutDays;
    } //function getEdorTypeTimeoutDays end

    //==========================================================================

	public static void main(String[] args) {
		EdorDelayNoReplyTimeoutTerminateMThread tEdorDelayNoReplyTimeoutTerminateMThread = new EdorDelayNoReplyTimeoutTerminateMThread();
		tEdorDelayNoReplyTimeoutTerminateMThread.dealMain();
	}
}
