package com.sinosoft.lis.config;

import java.io.Serializable;
import java.util.TreeMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.vschema.LDOccupationSet;
import com.sinosoft.utility.CacheUtil;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;

public class CodeCacheService extends CacheService {

	final static Log log = LogFactory.getLog(CodeCacheService.class);
	public final static String DEFAULT_CACHENAME="codeCache";
	
	public CodeCacheService() {
		setCacheName(DEFAULT_CACHENAME);
	}
	
	@Override
	public void initCache() {
		String sql = "select codetype,code,codename from ldcode order by codetype,code";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS codeSSRS = tExeSQL.execSQL(sqlbv1);
		String lastCodeType = "";
		TreeMap<String , String> codeMap = new TreeMap<String, String>();
		for(int i = 1; i<=codeSSRS.MaxRow;i++){
			String codeType = codeSSRS.GetText(i, 1);
			String code = codeSSRS.GetText(i, 2);
			String codeName = codeSSRS.GetText(i, 3);
			if(i>1 && !lastCodeType.equals(codeType)){
				CacheUtil.putObjectCached(getCacheName(), lastCodeType, codeMap);
				codeMap = new TreeMap<String, String>();
			}
			codeMap.put(code, codeName);
			lastCodeType = codeType;
			if(i==codeSSRS.MaxRow){
				CacheUtil.putObjectCached(getCacheName(), codeType, codeMap);
			}
		}
		
		sql = "select * from ldoccupation";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		LDOccupationSet tLDOccupationSet = new LDOccupationDB().executeQuery(sqlbv2);
		CacheUtil.putObjectCached(getCacheName(), "ldoccupation", tLDOccupationSet);

	}

	
	@SuppressWarnings("unchecked")
	public String getCodeName(String codeType,String code){
		String codeName = null;
		Serializable obj = getCacheObjectByKey(codeType);
		if(obj!=null && obj instanceof TreeMap<?, ?>){
			codeName = (String)((TreeMap<String, String>)obj).get(code);
		}
		return codeName;
	}
	
	@SuppressWarnings("unchecked")
	public String getCodeList(String codeType){
		String result= "";
		Serializable obj = getCacheObjectByKey(codeType);
		if(obj!=null){
			if(obj instanceof TreeMap<?, ?>){
				TreeMap<String, String> codeMap = (TreeMap<String, String>)obj;
				Iterator<String> it = codeMap.keySet().iterator();
				int size = codeMap.size();
				result="0|"+String.valueOf(codeMap.size())+ SysConst.RECORDSPLITER;
				int i=1;
				while(it.hasNext()){
					String code = it.next();
					String codeName = codeMap.get(code);
					result+=code+SysConst.PACKAGESPILTER+codeName;
					if(i<size){
						result+=SysConst.RECORDSPLITER;
					}
					i++;
				}
			}
		}
		return result;
	}

}
