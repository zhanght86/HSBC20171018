package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.AutoCancelLostBL;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全－自动解除保单挂失状态</p>
 */

public class AutoCancelLostTask extends TaskThread {
private static Logger logger = Logger.getLogger(AutoCancelLostTask.class);
	public CErrors mErrors = new CErrors();

	public AutoCancelLostTask() {
	}

	public boolean dealMain() {
		
		GlobalInput tG = new GlobalInput();  		
		tG.Operator = "AUTO";
		tG.ComCode = "86";
		tG.ManageCom = "86";
		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 
	    //日志监控,过程监控        
    	PubFun.logTrack(tG,tG.LogID[1],"保全挂失解挂批处理开始");
		VData tVData = new VData();
		tVData.add( tG );
		AutoCancelLostBL tAutoCancelLostBL = new AutoCancelLostBL();
		if (!tAutoCancelLostBL.submitData(tVData)) {
			CError tError = new CError();
			tError.moduleName = "AutoCancelLostTask";
			tError.functionName = "run";
			tError.errorMessage = "调用错误AutoCancelLostBL";
			this.mErrors.addOneError(tError);

		}
		  //日志监控,过程监控        
    	PubFun.logTrack(tG,tG.LogID[1],"保全挂失解挂批处理结束");
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  AutoCancelLostTask Execute !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		return true;
	}

	public static void main(String[] args) {
		AutoCancelLostTask tAutoCancelLostTask = new AutoCancelLostTask();
		tAutoCancelLostTask.dealMain();
	}
}
