package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

public class RulePool {
private static Logger logger = Logger.getLogger(RulePool.class);
	private RulePool(){}
	
	private int poolSize; //需要管理几个ThreadPoolManager;
	
	private   Semaphore available; 
	
	private   boolean[] used;
	
	private   ThreadPoolManager[] poolManagers;
	
	private boolean init = false;
	
	private static RulePool pool = new RulePool(); 
	public static RulePool getInstance() {
		return pool;
		
	}
	
	public synchronized void init(int poolSize,int minThreadPoolSize,int maxThreadPoolSize){
		if(!init){
		    init = true;
		    this.poolSize = poolSize;
		    available = new Semaphore(this.poolSize);
		    
		    poolManagers = new ThreadPoolManager[this.poolSize];
		    used = new boolean[this.poolSize];
		    
		    for(int i=0;i<this.poolSize;i++){
		    	used[i] = false;
		    	poolManagers[i] = new ThreadPoolManager(minThreadPoolSize,maxThreadPoolSize);
		    }
		    
		}
	}
	
	public ThreadPoolManager getThreadPool() throws InterruptedException { // no synch
		 available.acquire();
		 return getNextAvailableThreadPool();
	}
	
	protected synchronized ThreadPoolManager getNextAvailableThreadPool(){
		for(int i=0;i<poolManagers.length;i++){
			if(!used[i]){
				used[i] = true;
				return poolManagers[i];
			}
		}
		return null;
	}
	
	public void putThreadPool(ThreadPoolManager poolManager) {
		if(markAsUnused(poolManager))
			 available.release();
	}
	
	protected synchronized boolean markAsUnused(ThreadPoolManager item) { 
		for(int i=0;i<poolManagers.length;i++){
			if(poolManagers[i] == item && used[i]  ){
				used[i] = false;
				return true;
			}
		}
		return false;
	}

}
