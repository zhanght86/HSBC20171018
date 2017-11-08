package com.sinosoft.lis.config;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.utility.CacheUtil;

public abstract class CacheService {
	
	final static Log log = LogFactory.getLog(CodeCacheService.class);
	private String cacheName;
	
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public abstract void initCache();
	public void resetCache(){
		try{
			CacheUtil.getCache(getCacheName()).removeAll();
			initCache();
		}catch(Exception ex){
			ex.printStackTrace();
			log.info(ex);
		}
	}
	
	public Serializable getCacheObjectByKey(String key) {
		Serializable obj = null;
		try{
			obj = CacheUtil.getObjectCached(getCacheName(), key);
		}catch(Exception ex){
			log.info(ex);
		}
		return obj;
	}
}
