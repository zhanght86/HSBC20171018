package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

public class PoolThread extends Thread {
private static Logger logger = Logger.getLogger(PoolThread.class);
	
	private boolean runningFlag; 
	
	private Task    task;

	private ThreadPoolManager poolManager;
	
	public PoolThread(ThreadPoolManager poolManager){
		this.poolManager = poolManager;
	}
    
	public boolean isRunning(){
		return runningFlag;
	}
	
	public synchronized void   setRunning(){
		this.runningFlag = true;
		logger.debug("thread notify");
		this.notify();
		
	}
	
	
	public  void releaseThreadPool(){
		this.runningFlag = false;
		this.poolManager.releaseThreadPool(this);
		
	}
	
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	//@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
		while (true) {
			synchronized (this) {
				while (!this.runningFlag) {
					try {
						this.wait();
					} catch (InterruptedException ignored) {
					}
				}

			}
			//		 If we don't catch RuntimeException, 
			// the pool could leak threads
			try {
				task.execute();
				releaseThreadPool();
			} catch (RuntimeException e) {
				// You might want to log something here
				logger.debug("runtime error "+e.getMessage());
				
				releaseThreadPool();
			//	this.poolManager.createPoolThread();
			}
		}

	}

}
