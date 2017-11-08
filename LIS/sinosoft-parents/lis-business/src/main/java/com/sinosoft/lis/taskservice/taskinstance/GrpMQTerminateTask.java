package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.VData;

public class GrpMQTerminateTask extends TaskThread {
private static Logger logger = Logger.getLogger(GrpMQTerminateTask.class);
	
	public GrpMQTerminateTask() {
	}

	public boolean dealMain() {

		MMap tMMap = new MMap();
		
		//grppol 
		tMMap.put("update lcgrppol a set a.appflag='4' where a.appflag='1' and not exists(select 1 from lcpol b where b.grppolno=a.grppolno and b.appflag in ('0', '1', '2', '9'))", "UPDATE");
		
		//grpcont
		tMMap.put("update lcgrpcont a set a.appflag='4' where a.appflag='1' and not exists(select 1 from lcpol b where b.grpcontno=a.grpcontno and b.appflag in ('0', '1', '2', '9'))", "UPDATE");
		
		VData mResult = new VData();
		mResult.add(tMMap);
		
		PubSubmit tSubmit = new PubSubmit();
		
		logger.debug("start to GrpMQTerminateTask"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		if (!tSubmit.submitData(mResult, "")) {
			logger.debug("GrpMQTerminateTask failed");
			return false;
		}
		logger.debug("end to GrpMQTerminateTask"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());

		return true;
	}

	public static void main(String args[]) {
		GrpMQTerminateTask tGrpMQTerminateTask = new GrpMQTerminateTask();
		tGrpMQTerminateTask.dealMain();
	}
}
