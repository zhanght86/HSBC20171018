package com.sinosoft.lis.pubfun;
import java.util.Hashtable;

import org.apache.log4j.Logger;
/*
 * <p>ClassName: OptimisticLockCache </p>
 */

public final class OptimisticLockCache {
@SuppressWarnings("unused")
private static Logger logger = Logger.getLogger(OptimisticLockCache.class);

	private static OptimisticLockCache mOptimisticLockCache = null;
	
	/* 保存需要校验的类 */
	private Hashtable<String,String> mClassCache = new Hashtable<String,String>();


	private OptimisticLockCache() {
	}

	public static OptimisticLockCache getInstance() {
		if (mOptimisticLockCache == null) {
			mOptimisticLockCache = new OptimisticLockCache();
		}
		return mOptimisticLockCache;
	}
	
	public void setCheckClass(String className){
		mClassCache.put(className, className);
	}
	
	public boolean isCheckClass(String className){
		if(mClassCache.contains(className)){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
