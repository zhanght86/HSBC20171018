package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.util.LinkedList;
/**
 * 专门用来写规则引擎执行结果的类，只有一个线程来进行执行
 * 如果有任务来了，就执行，如果没有的话，就挂起直到有新的任务到来
 * 
 * @author Administrator
 *
 */
public class UpdateRuleResultThread extends Thread {
private static Logger logger = Logger.getLogger(UpdateRuleResultThread.class);
	
	private UpdateRuleResultThread(){
		this.start();
	}
	
	public static UpdateRuleResultThread getInstance(){
		return thread;
	}
	
	private LinkedList tasks = new LinkedList();
	
	public static UpdateRuleResultThread thread= new UpdateRuleResultThread();

	protected void addTask(Task task){
		synchronized(tasks)
		{
		   this.tasks.add(task);
		   this.tasks.notify();
		}
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			 Task task = null;
			 synchronized(this.tasks){
				 while(tasks.isEmpty()){
					 try {
						this.tasks.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				 }
				 task = (Task) tasks.removeFirst();
			 }
			 
			 try{
			     task.execute();
			 }catch (RuntimeException e) {
					// You might want to log something here
				    e.printStackTrace();
					logger.debug("runtime");
					thread= new UpdateRuleResultThread();
					//releaseThreadPool();
				}
		}
			 
                
          
          
	}

	
}
