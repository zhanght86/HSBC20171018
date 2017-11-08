package com.sinosoft.lis.config;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.lis.db.LMCalFactorDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.lis.vschema.LMCalFactorSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.utility.CacheUtil;
import com.sinosoft.utility.SQLwithBindVariables;

public class CalCodeCacheService extends CacheService {

	final static Log log = LogFactory.getLog(CalCodeCacheService.class);
	public final static String DEFAULT_CACHENAME="calCodeCache";
	
	
	public CalCodeCacheService() {
		setCacheName(DEFAULT_CACHENAME);
	}


	@Override
	public void initCache() {
		String sql = "select * from lmcalmode a where not exists (select 1 from lmcalfactor b where b.calcode = a.calcode)";
		HashMap<String, LMCalModeSchema> calCodeMap = new HashMap<String, LMCalModeSchema>();
		HashMap<String, LMCalFactorSet> calFactorMap = new HashMap<String, LMCalFactorSet>();
		
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		LMCalModeDB tLMCalModeDB = new LMCalModeDB();
		LMCalModeSet tLMCalModeSet = tLMCalModeDB.executeQuery(sqlbv1);
		for(int i=1;i<=tLMCalModeSet.size();i++){
			LMCalModeSchema tLMCalModeSchema = tLMCalModeSet.get(i);
			calCodeMap.put(tLMCalModeSchema.getCalCode(), tLMCalModeSchema);
		}
		
		sql = "select * from lmcalmode a where  exists (select 1 from lmcalfactor b where b.calcode = a.calcode)";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		tLMCalModeDB = new LMCalModeDB();
		tLMCalModeSet = tLMCalModeDB.executeQuery(sqlbv2);
		for(int i=1;i<=tLMCalModeSet.size();i++){
			LMCalModeSchema tLMCalModeSchema = tLMCalModeSet.get(i);
			calCodeMap.put(tLMCalModeSchema.getCalCode(), tLMCalModeSchema);
			
			LMCalFactorDB tLMCalFactorDB = new LMCalFactorDB();
			tLMCalFactorDB.setCalCode(tLMCalModeSchema.getCalCode());
			LMCalFactorSet tLMCalFactorSet = tLMCalFactorDB.query();
			calFactorMap.put(tLMCalModeSchema.getCalCode(), tLMCalFactorSet);
		}
		
		CacheUtil.putObjectCached(getCacheName(), "LMCalMode", calCodeMap);
		CacheUtil.putObjectCached(getCacheName(), "LMCalFactor", calFactorMap);
	}
	
	
	@SuppressWarnings("unchecked")
	public LMCalModeSchema getLMCalModeShema(String calCode){
		LMCalModeSchema tLMCalModeSchema = null;
		Serializable obj = getCacheObjectByKey("LMCalMode");
		if(obj!=null && obj instanceof HashMap<?, ?>){
			tLMCalModeSchema = (LMCalModeSchema)((HashMap<String, LMCalModeSchema>)obj).get(calCode);
		}
		return tLMCalModeSchema;
	}
	
	@SuppressWarnings("unchecked")
	public LMCalFactorSet getLMCalFactorSet(String calCode){
		LMCalFactorSet tLMCalFactorSet = null;
		Serializable obj = getCacheObjectByKey("LMCalFactor");
		if(obj!=null && obj instanceof HashMap<?, ?>){
			tLMCalFactorSet = (LMCalFactorSet)((HashMap<String, LMCalFactorSet>)obj).get(calCode);
			if(tLMCalFactorSet == null){
				tLMCalFactorSet = new LMCalFactorSet();
			}
		}
		return tLMCalFactorSet;
	}

}
