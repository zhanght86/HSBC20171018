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
  * 续期催收自动撤销批处理
  *
**/
public class RnewDueFeeCancleTMThread extends TaskThread {
private static Logger logger = Logger.getLogger(RnewDueFeeCancleTMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private String mCurrentDate = PubFun.getCurrentDate();
	public RnewDueFeeCancleTMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--续期催收自动撤销批处理开始" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
		
//		日志监控，性能监控
//		日志监控,过程监控
		PubFun.logPerformance (tG,tG.LogID[1],"续期催收自动撤销批处理开始","11");	
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='RnewDueFeeCancleTMCount' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
 		sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		Vector mTaskWaitList=new Vector();
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		logger.debug("保全自动核保数据清理批处理的最大的数tConutMax:" + tConutMax);

//		需要撤销的单子：已经催收，但是保单状态变成失效或者终止  -- 终止：52464   --失效： 44584
		String sql =" select otherno from ljspay where othernotype = '2'"
                   +" and exists (select 1 from lccont where contno = ljspay.otherno and conttype='1' and appflag = '4')"
                   +" union"
                   +" select otherno from ljspay where othernotype = '2'"
                   +" and exists (select 1 from lccont where contno = ljspay.otherno and conttype='1' and appflag = '1')"
                   +" and exists (select 1 from lccontstate where contno = ljspay.otherno"
				   +" and statetype = 'Available' and state = '1' and enddate is null)";	
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
 		sqlbv2.sql(sql);
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv2);
//		if (tSSRS == null || tSSRS.MaxRow <= 0) {
//			return false;
//		}
		logger.debug("tSSRS.getMaxRow()="+tSSRS.getMaxRow());
		VData tVData = new VData();
		for(int i=1;i<=tSSRS.getMaxRow();i++)			
		{
			LCContSchema tLCContSchema = new LCContSchema(); // 个人保单表
			tLCContSchema.setContNo(tSSRS.GetText(i, 1));				
			Map map = new HashMap();
			map.put("LCContSchema", tLCContSchema);
			map.put("GlobalInput", tG);
			tVData.add(map);
            if(i%tConutMax==0)
			{
            	mTaskWaitList.add(tVData);
            	tVData = new VData();
			}
				
			if(i%tConutMax!=0&&i==tSSRS.getMaxRow())
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
		String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='RnewDueFeeCancleTMThread'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
 		sqlbv3.sql(tSQL_ThreadCount);
		String tSignMThreadCount = tExeSQL.getOneValue(sqlbv3);
		if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
				&& Integer.parseInt(tSignMThreadCount) > 0) {
			tThreadCount = Integer.parseInt(tSignMThreadCount);
		}
		if (tSSRS.getMaxRow() < tThreadCount) {
			tThreadCount = tSSRS.getMaxRow();
		}
		
		this.mServiceA = new ServiceA(
				"com.sinosoft.lis.operfee.IndiDueFeeCancelUIMthread", mTaskWaitList,
				tThreadCount, 10, TaskCode);
		this.mServiceA.start();


        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "续期催收自动撤销批处理开始"+tSSRS.getMaxRow()+"笔,共花费"+tt+"秒");
	  	
		return true;
	}
	

    //==========================================================================

	public static void main(String[] args) {
		RnewDueFeeCancleTMThread tRnewDueFeeCancleTMThread = new RnewDueFeeCancleTMThread();
		tRnewDueFeeCancleTMThread.dealMain();
	}
}
