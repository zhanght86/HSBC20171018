package com.sinosoft.utility;


import javax.servlet.http.HttpServlet;


import com.sinosoft.lis.config.CacheService;

import org.apache.log4j.Logger;

public class LisCacheServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(LisCacheServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1046415832852981977L;
	
	
	public void init(){
		logger.debug("start init LisCache");
		try{
			String sql = "select cacheName, cacheService from ldcacheservice where state = '1'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			ExeSQL exeSql = new ExeSQL();
			SSRS cSSRS = exeSql.execSQL(sqlbv);
			for(int i=1;i<=cSSRS.getMaxRow();i++){
				String cacheName = cSSRS.GetText(i, 1);
				String cacheService = cSSRS.GetText(i, 2);
				Class tClass = Class.forName(cacheService);
				CacheService cs = (CacheService)tClass.newInstance();
				cs.setCacheName(cacheName);
				cs.initCache();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
