<%@page import="com.sinosoft.lis.config.CacheService"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="net.sf.ehcache.Element"%>
<%@page import="com.sinosoft.utility.CacheUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.json.JSONArray"%>
<%
	String oper = request.getParameter("oper");
	if("1".equals(oper)){
		String[] cacheNames = CacheUtil.getCacheManager().getCacheNames();
		Map map = new HashMap();
		List rows = new ArrayList();
		for(int i =0;i<cacheNames.length;i++){
			Cache cache = CacheUtil.getCacheManager().getCache(cacheNames[i]);
			//cache.removeAll();
			Map row = new HashMap();
			String cacheName = cacheNames[i];
			String cacheSize = String.valueOf(PubFun.round(cache.calculateInMemorySize()/(double)(1024*1024),2));
			String size = String.valueOf(cache.getSize());
			String maxElements = String.valueOf(cache.getCacheConfiguration().getMaxElementsInMemory());
			row.put("cacheName", cacheName);
			row.put("cacheSize", cacheSize);
			row.put("size", size);
			row.put("maxElements", maxElements);
			rows.add(row);
		}
		
	
		map.put("total",String.valueOf(cacheNames.length));
		map.put("rows",rows);
		JSONObject json = JSONObject.fromObject(map);
		out.print(json);
		
	}else if ("2".equals(oper)){
		String msg = "succ";
		try{
			String cacheName = request.getParameter("cacheName");
			String sql = "select cacheService from ldcacheservice where cacheName = '"+cacheName+"'";
			ExeSQL exeSql = new ExeSQL();
			String cacheService = exeSql.getOneValue(sql);
			Class tClass = Class.forName(cacheService);
			CacheService cs = (CacheService)tClass.newInstance();
			cs.setCacheName(cacheName);
			cs.resetCache();
		} catch(Exception ex){
			ex.printStackTrace();
			msg= "fail";
		}
		Map map = new HashMap();
		map.put("msg", msg);
		JSONObject json = JSONObject.fromObject(map);
		out.print(json);
	}
%>
