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
  * 保全自动核保数据清理批处理 -线程版
  *
**/
public class EdorAutoUWFinishMThread extends TaskThread {
private static Logger logger = Logger.getLogger(EdorAutoUWFinishMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    int XCount=0;
    private String mCurrentDate = PubFun.getCurrentDate();
	public EdorAutoUWFinishMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--保全自动核保数据清理批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
		
//		日志监控，性能监控
		PubFun.logPerformance (tG,tG.LogID[1],"保全自动核保数据清理批处理","1");
        logger.debug("\t@> PEdorValidTask.EdorAutoUWFinish() : 保全自动核保数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全自动核保数据清理批处理启动");
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='EdorAutoUWFinishCount' ";
		 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		logger.debug("保全自动核保数据清理批处理的最大的数tConutMax:" + tConutMax);
		try {
		 //查询出当前系统等待保全试算数据清理的纪录
        String QuerySQL = "select * from LWMission where ActivityID = '0000000004'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(QuerySQL);
        RSWrapper rsWrapper = new RSWrapper();
        LWMissionSet tLWMissionSet = new LWMissionSet();
        if (!rsWrapper.prepareData(tLWMissionSet, sqlbv3))
        {
            logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全自动核保数据清理批处理, 数据准备失败");
            return false;
        }
        do {
        rsWrapper.getData();
//        if (tLWMissionSet == null || tLWMissionSet.size() <= 0)
//        {
//        	return false;
//        }
		Vector mTaskWaitList = new Vector();
        VData tVData = new VData();
        for (int i = 1; i <= tLWMissionSet.size(); i++)
        {
                //LWMissionSchema
        		XCount++;
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                tLWMissionSchema = tLWMissionSet.get(i);
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema.setEdorAcceptNo(tLWMissionSchema.getMissionProp1());
                //TransferData
                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("MissionID", tLWMissionSchema.getMissionID());
                tTransferData.setNameAndValue("SubMissionID", tLWMissionSchema.getSubMissionID());
                tTransferData.setNameAndValue("EdorAcceptNo", tLWMissionSchema.getMissionProp1());
                tTransferData.setNameAndValue("OtherNo", tLWMissionSchema.getMissionProp2());
                tTransferData.setNameAndValue("OtherNoType", tLWMissionSchema.getMissionProp3());
                tTransferData.setNameAndValue("EdorAppName", tLWMissionSchema.getMissionProp4());
                tTransferData.setNameAndValue("Apptype", tLWMissionSchema.getMissionProp5());
                tTransferData.setNameAndValue("ManageCom", tLWMissionSchema.getMissionProp7());
                tTransferData.setNameAndValue("AppntName", tLWMissionSchema.getMissionProp11());
                tTransferData.setNameAndValue("PaytoDate", tLWMissionSchema.getMissionProp12());
                tTransferData.setNameAndValue("ActivityID", tLWMissionSchema.getActivityID());
                //VData
				// 准备传输数据 VData
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
				
				if(i%tConutMax!=0&&i==tLWMissionSet.size())
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
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='EdorAutoUWFinishMThread'";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv2);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (tLWMissionSet.size() < tThreadCount) {
			tThreadCount = tLWMissionSet.size();
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.workflow.bq.EdorWorkFlowUIMthread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();

        } while (tLWMissionSet != null && tLWMissionSet.size() > 0);
		rsWrapper.close();
     }catch (Exception ex) {
		ex.printStackTrace();
	} 
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "保全自动核保数据清理批处理"+XCount+"笔,共花费"+tt+"秒");
	  	
		return true;
	}
	

    //==========================================================================

	public static void main(String[] args) {
		EdorAutoUWFinishMThread tEdorAutoUWFinishMThread = new EdorAutoUWFinishMThread();
		tEdorAutoUWFinishMThread.dealMain();
	}
}
