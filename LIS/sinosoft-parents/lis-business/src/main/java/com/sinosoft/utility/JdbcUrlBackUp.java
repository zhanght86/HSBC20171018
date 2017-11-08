/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

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
 * 
 * @author: HST
 * @version: 1.0
 * @date: 2002-05-31
 */
public class JdbcUrlBackUp {
private static Logger logger = Logger.getLogger(JdbcUrlBackUp.class);
	// @Field
	private String DBType;
	private String IP;
	private String Port;
	private String DBName;
	private String ServerName;
	private String UserName;
	private String PassWord;

	// @Constructor
	public JdbcUrlBackUp() {
		// WebLogic连接池，其中MyPool为连接池的名称

		// DBType = "WEBLOGICPOOL";
		// DBName = "MyPool";

		// DBType = "ORACLE";
		// IP = "10.0.22.10";
		// Port = "1521";
		// DBName = "zkrtest";
		// UserName = "suggest";
		// PassWord = "suggest";

		// DBType = "ORACLE";
		// IP = "10.0.22.9";
		// Port = "1529";
		// DBName = "test";
		// UserName = "lis6test";
		// PassWord = "lis6test";

		DBType = "ORACLE";
		IP = "10.0.22.166";
		Port = "1521";
		DBName = "lis6";
		UserName = "lis6update";
		PassWord = "lis6update";
	}

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

	/**
	 * 获取连接句柄
	 * 
	 * @return String
	 */
	public String getJdbcUrl() {
		// String sUrl = "";
		StringBuffer sUrl = new StringBuffer(256);
		// Oracle连接句柄
		if (DBType.trim().toUpperCase().equals("ORACLE")) {
			// sUrl = "jdbc:oracle:thin:@" + IP + ":"
			// + Port + ":"
			// + DBName;
			sUrl.append("jdbc:oracle:thin:@");
			sUrl.append(IP);
			sUrl.append(":");
			sUrl.append(Port);
			sUrl.append(":");
			sUrl.append(DBName);
		}
		// InforMix连接句柄
		if (DBType.trim().toUpperCase().equals("INFORMIX")) {
			// sUrl = "jdbc:informix-sqli://" + IP + ":"
			// + Port + "/"
			// + DBName + ":"
			// + "informixserver=" + ServerName + ";"
			// + "user=" + UserName + ";"
			// + "password=" + PassWord + ";";
			sUrl.append("jdbc:informix-sqli://");
			sUrl.append(IP);
			sUrl.append(":");
			sUrl.append(Port);
			sUrl.append(DBName);
			sUrl.append(":");
			sUrl.append("informixserver=");
			sUrl.append(ServerName);
			sUrl.append(";");
			sUrl.append("user=");
			sUrl.append(UserName);
			sUrl.append(";");
			sUrl.append("password=");
			sUrl.append(PassWord);
			sUrl.append(";");
		}
		// SqlServer连接句柄
		if (DBType.trim().toUpperCase().equals("SQLSERVER")) {
			// sUrl = "jdbc:inetdae:" + IP + ":"
			// + Port + "?sql7=true&database=" + DBName + "&charset=gbk";
			sUrl.append("jdbc:inetdae:");
			sUrl.append(IP);
			sUrl.append(":");
			sUrl.append(Port);
			sUrl.append("?sql7=true&database=");
			sUrl.append(DBName);
			sUrl.append("&charset=gbk");
		}
		// WebLogicPool连接句柄
		if (DBType.trim().toUpperCase().equals("WEBLOGICPOOL")) {
			// sUrl = "jdbc:weblogic:pool:" + DBName;
			sUrl.append("jdbc:weblogic:pool:");
			sUrl.append(DBName);
		}
		// DB2连接句柄
		if (DBType.trim().toUpperCase().equals("DB2")) {
			// sUrl = "jdbc:db2://" + IP + ":"
			// + Port + "/"
			// + DBName;
			sUrl.append("jdbc:db2://");
			sUrl.append(IP);
			sUrl.append(":");
			sUrl.append(Port);
			sUrl.append("/");
			sUrl.append(DBName);
		}
		return sUrl.toString();
	}
}
