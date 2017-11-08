package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CErrors;

public class TestThreadTask extends TaskThread  {
private static Logger logger = Logger.getLogger(TestThreadTask.class);

	public CErrors mErrors = new CErrors();

	  public TestThreadTask() {
	  }

	  public boolean dealMain() {
		   int i=0;
		   logger.debug("i:"+i++);
		  while(i>=0)
		  {
			 // logger.debug("getStopFlag:"+getStopFlag());
			  //while(!this.)
			  {
			  logger.debug("i:"+i++);
			  try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  }
		  }
	  
	    return true;
	  }
	 


	  public static void main(String[] args) {
		  TestThreadTask tAgentMTraceTask= new TestThreadTask();
		  tAgentMTraceTask.dealMain();
	  }
}

