package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.operfee.IndiFinUrgeVerifyUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.workflow.bq.*;
import com.sinosoft.lis.bq.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;   

import com.sinosoft.lis.reagent.*;

import java.sql.Connection;

/**
 * <p>Title: </p>
 * <p>Description: 保全确认自动运行程序</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author xiongzh 2010-4-26
 * @version 1.0
 */

public class BQAutoConfirm extends TaskThread 
{
private static Logger logger = Logger.getLogger(BQAutoConfirm.class);



        public BQAutoConfirm() {
        }

        public CErrors mErrors = new CErrors();
        private String mDate=null;
        private ExeSQL tExeSQL = new ExeSQL();
        private String tContNo ="";


        public boolean dealMain()
        {
            /*业务处理逻辑*/
           logger.debug("进入业务逻辑处理,开始保全确认自动运行。!");
            logger.debug("Start BQAutoConfirm~~~"); 
            
            GlobalInput tGI=new GlobalInput();
            //由于涉及到后续保全确认批单号生成，操作机构取受理号前6位
            //tGI.ComCode="86";
            tGI.Operator="AUTO";
            //tGI.ManageCom="86";
            
            //日志监控,初始化           	
        	tGI.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
        	tGI.LogID[1]=(String)mParameters.get("SerialNo"); 
        	
          
            //日志监控,过程监控        
        	PubFun.logTrack(tGI,tGI.LogID[1],"保全确认批处理业务处理开始");

        	//启动时间        	
        	Date  dNow =  new Date();         	
        	long initTime =dNow.getTime();         	

            //实现查询出需要进行保全自动确认的任务号集合
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
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(Data_sql);
            SSRS data_ssrs = new SSRS();
            data_ssrs = tExeSQL.execSQL(sqlbv1);
            for(int i=1;i<=data_ssrs.MaxRow;i++)
            {
            	//此字段待核实
            	String sTemplatePath = "xerox/printdata";//application.getRealPath("xerox/printdata") + "/";
            	String tEdorAcceptNo=data_ssrs.GetText(i, 1);
            	String tMissionID = data_ssrs.GetText(i, 2);
            	String tSubMissionID = data_ssrs.GetText(i, 3);
            	//经过确认，操作机构取受理号前6位
            	tGI.ComCode = data_ssrs.GetText(i, 1).substring(0, 6);
            	tGI.ManageCom = data_ssrs.GetText(i, 1).substring(0, 6);
            	
                // 日志监控,过程监控
        		PubFun.logTrack (tGI,tEdorAcceptNo,"开始确认受理号为"+tEdorAcceptNo+"的保全件");
        		
            	//由于在自动运行的同时，界面也可能有并发的保全操作，因此必须对队列中的任务做一个确认前的校验
            	//1，该受理号下的确认节点还存在，若没有，说明被手工确认或者撤销掉了，那么此处跳过，不予处理
            	//2，该确认节点的defaultoperator必须是空，否则说明是等待期间内该受理号被手工申请到个人工作池，此种也不予处理
            	String check_sql="  select 1 from lwmission a where a.missionprop1='"+"?tEdorAcceptNo?"+"' "
            	+" and activityid = '0000000009' and processid = '0000000000' and a.defaultoperator is null ";
            	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    			sqlbv2.sql(check_sql);
    			sqlbv2.put("tEdorAcceptNo", tEdorAcceptNo);
            	String check = tExeSQL.getOneValue(sqlbv2);
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
                VData tVData = new VData();        
                EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
                //add by jiaqiangli 2009-06-10 为防止并发
                PubConcurrencyLock mPubLock = new PubConcurrencyLock();
                  
              	try
              	{
              		//LF0001 保全确认与暂收退费的组间并发控制
              		if (mPubLock.lock(tEdorAcceptNo, "LF0001", tGI.Operator) == true) 
              		{
                         logger.debug("保全确认与暂收退费的组间并发控制处理成功");           
                 		//BQCF01 保全确认并发控制组
                 		if (mPubLock.lock(tEdorAcceptNo, "BQCF01", tGI.Operator) == true) 
                 		{
                 			tVData.add(tGI);		
                 			tVData.add(tTransferData);   
                 			
                 			if(tEdorWorkFlowUI.submitData(tVData, "0000000009")== true){                 			
                             // 日志监控,状态监控                 		
                 			PubFun.logState(tGI, tEdorAcceptNo, "保全受理号为"+tEdorAcceptNo+"的保全件确认成功","1");                 			             			
                 			}
                 			else 
                     		{
                 				// 日志监控,状态监控                 		
                     			PubFun.logState(tGI, tEdorAcceptNo, "保全受理号为"+tEdorAcceptNo+"的保全件确认失败","0");     		   
                     		}
                 		}
                 		else 
                 		{
                 			logger.debug("系统忙，受理号"+tEdorAcceptNo+"并发加锁失败！");      		   
                 		}
              		}
              		else 
              		{      			
              			logger.debug("受理号"+tEdorAcceptNo+"下保单正在进行暂收退费,此次不予处理");      	
              		}

              	}
              	catch(Exception ex)
              	{
              	      logger.debug("确认失败，原因是:" + ex.toString());        	      
              	}
              	finally {
              		//注意解锁
              		mPubLock.unLock();
              	}
            }

                   	
          
            //完成时间
        	Date  dend = new Date();         	
        	long  endTime = dend.getTime(); 
        	long tt=(endTime-initTime)/1000; 
        	 //日志监控,过程监控
    		PubFun.logTrack(tGI,tGI.LogID[1],"保全确认批处理业务处理完毕");
        	//日志监控,处理结果
    		PubFun.logResult(tGI, tGI.LogID[1], "总共处理保全确认"+data_ssrs.MaxRow+"笔,共花费"+tt+"秒");    	
            logger.debug("end deal BQAutoConfirm!! ");                       
            return true;
        }
            
            
      
        
        public static void main(String[] args)
        {
//          CalCommisionTask tCalCommisionTask = new CalCommisionTask();
//          tCalCommisionTask.run();
        	BQAutoConfirm tBQAutoConfirm = new BQAutoConfirm();
        	tBQAutoConfirm.dealMain();
        }
        

}
