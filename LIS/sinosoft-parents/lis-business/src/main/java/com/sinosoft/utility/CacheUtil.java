package com.sinosoft.utility;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存管理工具类
 * @author ssx
 */
public class CacheUtil {

	final static Log log = LogFactory.getLog(CacheUtil.class);

	private static CacheManager manager;
	
	static{
		try {
			manager = CacheManager.getInstance();
			if(manager==null)
				manager = CacheManager.create();
		} catch (CacheException e) {
			log.fatal("Initialize cache manager failed.", e);
		}
	}

	/**
	 * 从缓存中获取对象
	 * @param cache_name 缓存名称
	 * @param key 缓存对象的key
	 * @return
	 */
	public static Serializable getObjectCached(String cache_name, Serializable key){
		Cache cache = getCache(cache_name);
		if(cache!=null){
			try {
				Element elem = cache.get(key);
				if(elem!=null && !cache.isExpired(elem))
					return elem.getValue();
			} catch (Exception e) {
				log.error("Get cache("+cache_name+") of "+key+" failed.", e);
			}
		}
		return null;
	}
	
	public static CacheManager getCacheManager(){
		if(manager==null){
			manager = CacheManager.getInstance();
			if(manager==null)
				manager = CacheManager.create();
		}
		return manager;
	}

	/**
	 * 把对象放入缓存中
	 * @param cache_name 
	 * @param key
	 * @param value
	 */
	public synchronized static void putObjectCached(String cache_name, Serializable key, Serializable value){
		Cache cache = getCache(cache_name);
		if(cache!=null){
			try {
				cache.remove(key);
				Element elem = new Element(key, value);
				cache.put(elem);
			} catch (Exception e) {
				log.error("put cache("+cache_name+") of "+key+" failed.", e);
			}
		}
	}

	/**
	 * 获取指定名称的缓存
	 * @param arg0
	 * @return
	 * @throws IllegalStateException
	 */
	public static Cache getCache(String arg0) throws IllegalStateException {
		return manager.getCache(arg0);
	}

	/**
	 * 获取缓冲中的信息
	 * @param cache
	 * @param key
	 * @return
	 * @throws IllegalStateException
	 * @throws CacheException
	 */
	public static Element getElement(String cache, Serializable key) throws IllegalStateException, CacheException{
		Cache cCache = getCache(cache);
		return cCache.get(key);
	}

	/**
	 * 停止缓存管理器
	
	public static void shutdown(){
		if(manager!=null)
			manager.shutdown();
	}
	*/

}