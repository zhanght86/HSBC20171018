

package com.sinosoft.productdef;

import com.sinosoft.lis.pubfun.CodeQueryBL;
import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.utility.StringManager;

/**
 * <p>Title: SQL解析</p>
 *
 * <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author ranjun
 * @version 1.0
 */

public class SQLParseUtil {
	/**SQLINFO分割符*/
	public static final String SQLINFO = ";";
	/**SQL PARA分割符*/
	public static final String SQLPAEA = ":";
	/**子SQL 标志符*/
	public static final String SQLSUBPAEA = "*";
	
	public static final String SQLSHARP = "#";
	 
	
	/**
	 * 解析SQL
	 * */
	public String parseSQL(String mSQL)
	{
		if ((mSQL == null) || mSQL.trim().equals("")) {
			throw new ServiceException("解析SQL信息出错");
		}
		String[] tSql = mSQL.split(SQLINFO);
		if (tSql.length < 2) {
			throw new ServiceException("解析SQL信息出错");
		}
		String[] tPara = tSql[2].split(SQLPAEA);
		for (int i = 0; i < tPara.length; i++) {
			tPara[i] = dealSubSql(tPara[i], tSql[0], tSql[1] + "_" + i);
		}
		mSQL = SQLMessage.getSqlMessage().getSql(tSql[0], tSql[1], tPara);
		mSQL = getTranslatedSQL(mSQL); 
		return mSQL;
	}

	/**
	 * notes added by ranjun@sinosoft.com.cn 20111112
	 * 翻译SQL
	 * @param mSQL
	 * @return
	 */
	private String getTranslatedSQL(String mSQL){
		String tSQL ="";
		int sharpindex=mSQL.indexOf(SQLSHARP);
		while(sharpindex!=-1){
			tSQL=tSQL+mSQL.substring(0,sharpindex);
			mSQL=mSQL.substring(sharpindex+1,mSQL.length());
			int tempindex=mSQL.indexOf(SQLSHARP);
			if(tempindex!=-1){
				CodeQueryBL codequerybl = new CodeQueryBL();
				//获取SQL中的资源文件id，并根据codequerybl进行翻译处理 
				String tResourceId =codequerybl.transI18NMenu(mSQL.substring(0,tempindex)); 
				tSQL = tSQL+tResourceId;
				mSQL=mSQL.substring(tempindex+1,mSQL.length());
			}
			sharpindex=mSQL.indexOf(SQLSHARP);			 
		}
		return tSQL+mSQL;
	}
	/**获取参数信息,处理子SQL参数*/
	private String dealSubSql(String tStr,String resource,String sqlID)
	{
		if(tStr==null||tStr.equals(""))
		{
			return "";
		}
		if(tStr.startsWith(SQLSUBPAEA))
		{
			String ts = tStr.substring(1);
			if(ts==null||ts.equals(""))
			{
				return "";
			}
			else
			{
				 return SQLMessage.getSqlMessage().getSql(resource,sqlID,new String[] {ts});
			}
		}
		else
		{
			return tStr;
		}
	}
}
