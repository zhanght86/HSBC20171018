package com.sinosoft.ibrms;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.lis.config.CacheService;
import com.sinosoft.utility.CacheUtil;
import com.sinosoft.utility.SSRS;

public class RuleEngineCacheService extends CacheService {

	final static Log log = LogFactory.getLog(RuleEngineCacheService.class);
	public final static String DEFAULT_CACHENAME="ruleEngineCache";
	
	public RuleEngineCacheService() {
		setCacheName(DEFAULT_CACHENAME);
	}
	
	@Override
	public void initCache() {

	}

	public SSRS getResultBySQL(String sqlKey){
		SSRS ssrs = null;
		Serializable obj = getCacheObjectByKey(sqlKey);
		if(obj!=null&& obj instanceof SSRS){
			ssrs = (SSRS)obj;
		}
		return ssrs;
	}
	
	public void putSQLResult(String sqlKey,SSRS ssrs){
		CacheUtil.putObjectCached(getCacheName(), sqlKey, ssrs);
	}
}
