package com.sinosoft.ibrms;
import org.apache.log4j.Logger;
// 线程池
import java.util.LinkedList;
import java.util.List;

public class ThreadPoolManager {
private static Logger logger = Logger.getLogger(ThreadPoolManager.class);
	private int minThreads; //最小线程数
	private int maxThreads; //最大线程数
	private int currentRunningThreads = 0; //当前正在运行的线程数
	private int currentThreads =0;  //当前已经创建的线程数
	private boolean processing = false;
	/**
	 * 
	 */
	private List pools = new LinkedList();
	
	private LinkedList tasks = new LinkedList();
	
	public ThreadPoolManager(int minThreads,int maxThreads){
		if(maxThreads<minThreads)
			 maxThreads = minThreads;
		this.minThreads = minThreads;
		this.maxThreads = maxThreads;
		for(int i=0;i<minThreads;i++){
			PoolThread poolThread = this.createPoolThread();
			pools.add(poolThread);
			currentThreads++;
			poolThread.start();
		}
	}
	
	protected PoolThread createPoolThread(){
		return new PoolThread(this);
	}
	

	public  void releaseThreadPool(PoolThread poolThread){
		synchronized(this.pools){
		   this.currentRunningThreads --;
		   logger.debug("notify");
		   this.pools.notify();
		}
	}
	
	protected void addTask(Task task){
		synchronized(tasks)
		{
		   this.tasks.add(task);
		//   if(this.processing)
		//      this.tasks.notify();
		}
	}
	
	public void process(){
		this.processing = true;
		while (true) {
			 Task task = null;
			 synchronized(this.tasks){
			/*	 while(tasks.isEmpty()){
					 try {
						this.tasks.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				 }*/
				 if(tasks.isEmpty())
					 return ;
				 task = (Task) tasks.removeFirst();
			 }
			 
                 
           
            synchronized(this.pools){
            	if(currentRunningThreads == currentThreads) //当前线程总数是否与当前运行线程总数相同
            	{
            		if(currentThreads<maxThreads) //还可以再创建线程执行
            		{
            			PoolThread poolThread = this.createPoolThread();
            			poolThread.start();
            			pools.add(poolThread);
            			currentThreads ++;
            			poolThreadRun(poolThread,task);
            			//poolThread.setTask(task);
            		}else{
            			while(currentRunningThreads == currentThreads){
            				 try
                             {
            					 pools.wait();
                             }
                             catch (InterruptedException ignored)
                             {
                             }
                             logger.debug("wait");
            			}
            			getPoolThreadToRun(task);
            		}
            			
            	}else{
                	getPoolThreadToRun(task);
                }
                
            		
            }
		}

		//return true;
	}
	
	protected void getPoolThreadToRun(Task task){
		logger.debug("get pool begin +"+ currentRunningThreads);
		synchronized(this.pools){
		  for(int i=0;i<pools.size();i++){
			PoolThread poolThread = (PoolThread)pools.get(i);
			if(!poolThread.isRunning()){
				poolThreadRun(poolThread,task);
				//logger.debug("get pool end +"+poolThread.getId());
				
				break;
			}
		 }
		}
	}
	
	protected void poolThreadRun(PoolThread poolThread,Task task){
		poolThread.setTask(task);
		this.currentRunningThreads++;
		poolThread.setRunning();
		
		
	}
	
	/**
	 * 停止线程池
	 *
	 */
	public void stop(){
		
	}
	
	public static void main(String[] args){
		ThreadPoolManager manager = new ThreadPoolManager(5,7);
		for(int i=0;i<90;i++){
			Task task = new SimpleTask(i);
		//	task.execute();
			manager.addTask(task);
		}
		
		manager.process();
	}
}
