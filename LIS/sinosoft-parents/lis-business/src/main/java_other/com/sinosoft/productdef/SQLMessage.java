

package com.sinosoft.productdef;

import java.util.ResourceBundle;
import java.util.Hashtable;
import java.util.Map;
import java.text.MessageFormat;

/**
 * <p>Title: SQL语句的查找,及合并。</p>
 *
 * <p>Description: ejb转发设计</p>
 *
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 */
public class SQLMessage {
    private Map nameMap = new Hashtable();
    private String dir = "SqlInfo";
    private static SQLMessage sqlMessage = new SQLMessage();
    private SQLMessage() {
    }
    /**
     * 单态模式
     * */
    public static SQLMessage getSqlMessage() {
        return sqlMessage;
    }
    
    /**
     * 解析Sql信息,返回合成sql语句
     * */
    public String getSql(String name, String seqno, String[] para) {
        ResourceBundle tResourceBundle = getResourceBundle(name);
        String tSql = tResourceBundle.getString(seqno);
        return MessageFormat.format(tSql, para);
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
