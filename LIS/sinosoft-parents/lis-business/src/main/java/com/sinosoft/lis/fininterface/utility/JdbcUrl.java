package com.sinosoft.lis.fininterface.utility;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FIDataBaseLinkSchema;

/**
 * <p>
 * ClassName: JdbcUrl
 * </p>
 * <p>
 * Description: 构建 Jdbc 的 url
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
public class JdbcUrl {
private static Logger logger = Logger.getLogger(JdbcUrl.class);


    public JdbcUrl(FIDataBaseLinkSchema tFIDataBaseLinkSchema)
    {

        DBType = tFIDataBaseLinkSchema.getDBType();
        IP = tFIDataBaseLinkSchema.getIP();
        Port = tFIDataBaseLinkSchema.getPort();
        DBName = tFIDataBaseLinkSchema.getDBName();
        UserName = tFIDataBaseLinkSchema.getUserName();
        PassWord = tFIDataBaseLinkSchema.getPassWord();

    }

    // @Field
    private String DBType;

    private String IP;

    private String Port;

    private String DBName;

    private String ServerName;

    private String UserName;

    private String PassWord;

    // @Method
    public String getDBType() {
        return DBType;
    }

    public String getIP() {
        return IP;
    }

    public String getPort() {
        return Port;
    }

    public String getDBName() {
        return DBName;
    }

    public String getServerName() {
        return ServerName;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setDBType(String aDBType) {
        DBType = aDBType;
    }

    public void setIP(String aIP) {
        IP = aIP;
    }

    public void setPort(String aPort) {
        Port = aPort;
    }

    public void setDBName(String aDBName) {
        DBName = aDBName;
    }

    public void setServerName(String aServerName) {
        ServerName = aServerName;
    }

    public void setUser(String aUserName) {
        UserName = aUserName;
    }

    public void setPassWord(String aPassWord) {
        PassWord = aPassWord;
    }

    public String getJdbcUrl() {
        String sUrl = "";

        if (DBType.trim().toUpperCase().equals("ORACLE")) {
            sUrl = "jdbc:oracle:thin:@" + IP + ":" + Port + ":" + DBName;
        }

        if (DBType.trim().toUpperCase().equals("INFORMIX")) {
            sUrl = "jdbc:informix-sqli://" + IP + ":" + Port + "/" + DBName +
                   ":" + "informixserver=" + ServerName
                   + ";" + "user=" + UserName + ";" + "password=" + PassWord +
                   ";";
        }

        if (DBType.trim().toUpperCase().equals("SQLSERVER")) {
            sUrl = "jdbc:microsoft:sqlserver://" + IP + ":" + Port +
                   ";databaseName=" + DBName + ";selectMethod=cursor";
        }
        if (DBType.trim().toUpperCase().equals("WEBLOGICPOOL")) {
            sUrl = "jdbc:weblogic:pool:" + DBName;
        }

        return sUrl;
    }
}
