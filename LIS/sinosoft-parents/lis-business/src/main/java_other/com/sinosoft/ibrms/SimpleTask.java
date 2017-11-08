package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

public class SimpleTask implements Task {
private static Logger logger = Logger.getLogger(SimpleTask.class);
    int taskId;
	
	public SimpleTask(int taskId){
		this.taskId = taskId;
	}
	
	public Object execute() {
		//logger.debug(taskId+" execute current thread"+Thread.currentThread().getId());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.debug("error");
			e.printStackTrace();
		}
		return null;
		// TODO Auto-generated method stub

	}

}
