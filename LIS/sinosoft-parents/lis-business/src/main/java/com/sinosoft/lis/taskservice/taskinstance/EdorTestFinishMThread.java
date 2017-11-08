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
  * 保全试算数据清理批处理及保全未使用无扫描申请数据清理---线程版
  *
**/
public class EdorTestFinishMThread extends TaskThread {
private static Logger logger = Logger.getLogger(EdorTestFinishMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private String mCurrentDate = PubFun.getCurrentDate();
    private int XCount=0;
	public EdorTestFinishMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--保全未使用无扫描申请数据清理批处理-保全试算数据清理批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	  	
//		日志监控，性能监控
		PubFun.logPerformance (tG,tG.LogID[1],"保全未使用无扫描申请数据清理批处理开始","1");
		ExeSQL tExeSQL = new ExeSQL();
		Vector mTaskWaitList = new Vector();
		
        EdorUnuseNoScanApply();                //保全未使用无扫描申请数据清理
//		日志监控，性能监控
        
    	PubFun.logPerformance (tG,tG.LogID[1],"保全试算数据清理批处理","2");
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='EdorTestFinishCount' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_Count);
		tResult = tExeSQL.getOneValue(sqlbv);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		logger.debug("保全试算数据清理批处理的最大的数tConutMax:" + tConutMax);
		try {
		 //查询出当前系统等待保全试算数据清理的纪录
        String QuerySQL = "select * from LWMission where ActivityID = '0000000098'";
    	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(QuerySQL);
        RSWrapper rsWrapper = new RSWrapper();
        LWMissionSet tLWMissionSet = new LWMissionSet();
        if (!rsWrapper.prepareData(tLWMissionSet, sqlbv1))
        {
            logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全试算数据清理批处理, 数据准备失败");
            return false;
        }
        do {
        rsWrapper.getData();
		
        VData tVData = new VData();
        for (int i = 1; i <= tLWMissionSet.size(); i++)
        {
        	    XCount++;
                //LWMissionSchema
                LWMissionSchema tLWMissionSchema = new LWMissionSchema();
                tLWMissionSchema = tLWMissionSet.get(i);
                //LPEdorAppSchema
                LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema.setEdorAcceptNo(tLWMissionSchema.getMissionProp1());
                //TransferData
                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("MissionID", tLWMissionSchema.getMissionID());
                tTransferData.setNameAndValue("MissionProp1", tLWMissionSchema.getMissionProp1());
                tTransferData.setNameAndValue("SubMissionID", tLWMissionSchema.getSubMissionID());
                tTransferData.setNameAndValue("DelReason", "");    //删除原因
                tTransferData.setNameAndValue("ReasonCode", "");   //原因编号
                tTransferData.setNameAndValue("ActivityID", tLWMissionSchema.getActivityID());
                //VData

               	Map map = new HashMap();
				map.put("LPEdorAppSchema", tLPEdorAppSchema);
				map.put("TransferData", tTransferData);
				map.put("GlobalInput", tG);
				
				tVData.add(map);
				// 准备传输数据 VData
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
        
		String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
				+ (String) this.mParameters.get("TaskCode") + ":"
				+ (String) this.mParameters.get("TaskPlanCode");
		// tongmeng 2009-05-21 add
		// 自适应线程数
		int tThreadCount = 5;
		tExeSQL = new ExeSQL();
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='EdorTestFinishThreadCount'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv2);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (tLWMissionSet.size()< tThreadCount) {
			tThreadCount = tLWMissionSet.size();
		}
    	PubFun.logTrack(tG,tG.LogID[1],"保全试算数据清理批处理启动");
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
	  	PubFun.logTrack(tG,tG.LogID[1],"保全自动核保数据清理批处理完毕");
		return true;
	}
	 /**
     * 保全未使用无扫描申请数据清理批处理
     */
    private void EdorUnuseNoScanApply()
    {
        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理启动");

        //if (!(mCurrentTime.compareTo("23:00:00") >= 0 && mCurrentTime.compareTo("23:59:59") <= 0))
        //{
        //    return;    //在晚上 11 点以后运行
        //}
        //if (!(mCurrentTime.compareTo("00:00:00") >= 0 && mCurrentTime.compareTo("05:59:59") <= 0))
        //{
        //    return;    //在凌晨 06 点以前运行
        //}

        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("WFOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 查询逾期终止天数失败! ");
            //日志监控,过程监控        
    	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理, 系统未设置逾期终止天数");
            return;
        }

        //选取需要清理的数据
        String DeleteSQL = new String("");
        DeleteSQL = "delete from LWMission "
                  +  "where ActivityID = '0000000002' "
                  +    "and MissionProp3 is null "
                  +    "and MakeDate <= (to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') - " + "?sTimeoutDays?" + ")";
        //logger.debug(DeleteSQL);
    	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(DeleteSQL);
		sqlbv3.put("mCurrentDate", mCurrentDate);
		sqlbv3.put("sTimeoutDays", sTimeoutDays);
        MMap tMap = new MMap();
        tMap.put(sqlbv3, "DELETE");
        VData tVData = new VData();
        tVData.add(tMap);
        tMap = null;

        //PubSubmit
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 删除数据失败！");
            return;
        }
        tPubSubmit = null;
        tVData = null;

        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理完毕");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理完毕");
    }  //function EdorUnuseNoScanApply end

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
		EdorTestFinishMThread tEdorTestFinishMThread = new EdorTestFinishMThread();
		tEdorTestFinishMThread.dealMain();
	}
}
