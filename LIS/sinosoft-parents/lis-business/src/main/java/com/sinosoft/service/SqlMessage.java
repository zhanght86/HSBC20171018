package com.sinosoft.service;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;
import java.util.Hashtable;
import java.util.Map;
import java.text.MessageFormat;

/**
 * <p>Title: Sql语句的查找,及合并.用于EasyQueryUI前置使用</p>
 *
 * <p>Description: ejb转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 */
public class SqlMessage {
private static Logger logger = Logger.getLogger(SqlMessage.class);
    private Map nameMap = new Hashtable();
    private String dir = "SqlInfo";
    private static SqlMessage sqlMessage = new SqlMessage();
    private SqlMessage() {
    }
    /**
     * 单态模式
     * */
    public static SqlMessage getSqlMessage() {
        return sqlMessage;
    }
    
    /**
     * 解析Sql信息,返回合成sql语句
     * */
    public String getSql(String name, String seqno, String[] para) {
        ResourceBundle tResourceBundle = getResourceBundle(name);
		String tSql = null;
		if (tResourceBundle.containsKey(seqno + "_" + com.sinosoft.utility.SysConst.DBTYPE))
			tSql = tResourceBundle.getString(seqno + "_" + com.sinosoft.utility.SysConst.DBTYPE);
		else
			tSql = tResourceBundle.getString(seqno);
        //logger.debug("tSql before :"+tSql);
        //logger.debug("tSql after :"+MessageFormat.format(tSql, para));
        return MessageFormat.format(tSql, para);
    }
    
    public String getSourceSql(String name, String seqno) {
        ResourceBundle tResourceBundle = getResourceBundle(name);
		String tSql = null;
		if (tResourceBundle.containsKey(seqno + "_" + com.sinosoft.utility.SysConst.DBTYPE))
			tSql = tResourceBundle.getString(seqno + "_" + com.sinosoft.utility.SysConst.DBTYPE);
		else
			tSql = tResourceBundle.getString(seqno);
        return tSql;
    }

    /**获取SQl对应资源*/
    private ResourceBundle getResourceBundle(String name) {
        ResourceBundle tResourceBundle = (ResourceBundle) nameMap.get(name);
        if (tResourceBundle == null) {
            tResourceBundle = ResourceBundle.getBundle(dir +
                    "." + name);
            nameMap.put(name, tResourceBundle);
        }
        return tResourceBundle;
    }
}
