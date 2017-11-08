package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

public class Semaphore {
private static Logger logger = Logger.getLogger(Semaphore.class);
     private long permits;

	public Semaphore(long permits) {
		super();
		this.permits = permits;
	}
	
	public void  acquire() throws InterruptedException {
		if(Thread.interrupted()) throw new InterruptedException();
		synchronized (this) {
			try{
			    while(this.permits<=0) wait();
			    -- this.permits;
			}catch(InterruptedException ex){
				notify();
				throw ex;
			}
			
		}
	}
	
	
	public synchronized void release() {
		++this.permits;
		notify();
	}
	
	public boolean attempt(long msecs) throws InterruptedException {
		if(Thread.interrupted())  throw new InterruptedException();
		
		synchronized (this) {
			if(this.permits>0){
				--this.permits;
				return true;
			}
			
			if(msecs<=0)
				return false;
			try{
			long waitTime = msecs;
			long startTime = System.currentTimeMillis();
			
			for(;;){
			     wait(waitTime);
			     if(permits>0){
			    	 --permits;
			    	 return true;
			     }
			     waitTime = msecs-(System.currentTimeMillis()-startTime);
			     if(waitTime<=0)
			    	 return false;
			     
			}
			}catch(InterruptedException ex){
				notify();
				throw ex;
			}
			
		}
		
		
	}
	
	 public synchronized long permits() {
		    return permits;
    }

     
     
	
}
