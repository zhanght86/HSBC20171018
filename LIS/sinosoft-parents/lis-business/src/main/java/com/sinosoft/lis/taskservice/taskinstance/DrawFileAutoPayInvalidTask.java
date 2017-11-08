package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 抽档自垫失效批处理(顺序执行)
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009-05-08
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author lijiaqiang
 * @version 1.0
 */

public class DrawFileAutoPayInvalidTask extends TaskThread {
private static Logger logger = Logger.getLogger(DrawFileAutoPayInvalidTask.class);
	public CErrors mErrors = new CErrors();

	public DrawFileAutoPayInvalidTask() {
	}

	public boolean dealMain() {
		logger.debug("start to DrawFileAutoPayInvalidTask" + PubFun.getCurrentDate() + "-"
				+ PubFun.getCurrentTime());
	     
        GlobalInput tGI=new GlobalInput();
        tGI.ComCode="86";
        tGI.Operator="001";
        tGI.ManageCom="86";        
		 //日志监控,初始化           	
        tGI.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
        tGI.LogID[1]=(String)mParameters.get("SerialNo"); 
	    //日志监控,过程监控        
	  	PubFun.logTrack(tGI,tGI.LogID[1],"保全综合批处理开始");
		VData tVData = new VData();    
    	tVData.add(tGI);
		
		//add by jiaqiangli 2009-05-23 预期终止批处里中的过期催收应该先运行
//		日志监控，性能监控
		PubFun.logPerformance (tGI,tGI.LogID[1],"保全过期数据清理批处理","1");
		PEdorValidTask tPEdorValidTask = new PEdorValidTask();
		tPEdorValidTask.dealMain(tVData);
		logger.debug("end to PEdorValid" + PubFun.getCurrentDate() + "-" + PubFun.getCurrentTime());
		//add by jiaqiangli 2009-05-23 预期终止批处里中的过期催收应该先运行

		//modify by jiaqiangli 2009-05-14 调用main函数不安全
		// DrawFile
//		日志监控，性能监控
		PubFun.logPerformance (tGI,tGI.LogID[1],"续期续保自动催收批处理","2");
		RnewPolAuto tRnewPolAuto = new RnewPolAuto();
		tRnewPolAuto.dealMain(tVData);
		logger.debug("end to DrawFile" + PubFun.getCurrentDate() + "-" + PubFun.getCurrentTime());

		// AutoPay
//		日志监控，性能监控
		PubFun.logPerformance (tGI,tGI.LogID[1],"自动垫交批处理","3");
		AutoPayTask tAutoPayTask = new AutoPayTask();
		tAutoPayTask.dealMain(tVData);
		logger.debug("end to AutoPay" + PubFun.getCurrentDate() + "-" + PubFun.getCurrentTime());

		// Invalid
//		日志监控，性能监控
		PubFun.logPerformance (tGI,tGI.LogID[1],"失效批处理","4");
		ContInvaliState tContInvaliState = new ContInvaliState();
		tContInvaliState.dealMain(tVData);

		logger.debug("end to DrawFileAutoPayInvalidTask" + PubFun.getCurrentDate() + "-"
				+ PubFun.getCurrentTime());
		
//		日志监控，性能监控
		PubFun.logPerformance (tGI,tGI.LogID[1],"终止批处理","5");
		ContEPTerminateMThread tContEPTerminateMThread = new ContEPTerminateMThread();
		tContEPTerminateMThread.dealMain(tVData);

		logger.debug("end to ContEPTerminateMThread" + PubFun.getCurrentDate() + "-"
				+ PubFun.getCurrentTime());
		
		//增加续期催收自动撤销批处理（撤销掉失效和终止的单子）
//		日志监控，性能监控
		PubFun.logPerformance (tGI,tGI.LogID[1],"续期催收自动撤销批处理（已经催收，但是保单状态变成失效或者终止）","6");
		RnewDueFeeCancle tRnewDueFeeCancle = new RnewDueFeeCancle();
		tRnewDueFeeCancle.dealMain(tVData);
	    //日志监控,过程监控        
	  	PubFun.logTrack(tGI,tGI.LogID[1],"保全综合批处理结束");
		
		return true;
	}

	public static void main(String[] args) {
		DrawFileAutoPayInvalidTask tDrawFileAutoPayInvalidTask = new DrawFileAutoPayInvalidTask();
		tDrawFileAutoPayInvalidTask.dealMain();
	}
}
