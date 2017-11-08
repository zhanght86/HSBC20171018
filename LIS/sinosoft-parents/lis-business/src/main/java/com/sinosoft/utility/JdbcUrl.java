package com.sinosoft.utility;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;
import oracle.jdbc.driver.OracleDriver;

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
public class JdbcUrl {
	private static Logger logger = Logger.getLogger(JdbcUrl.class);
	// @Constructor
	protected boolean jndi = false;
	
	private static final boolean Debug = false;

	private static Hashtable mPoolHashtable = new Hashtable();

	private static boolean fileConfigFlag = false;
	static {
		try {
			initMultPool();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化多连接池的信息
	 */
	private static void initMultPool() {

		if (!fileConfigFlag) {
			Hashtable tDBHashtable = null;

			// 默认连接
			tDBHashtable = new Hashtable();
			

			//云oracle
//			SysConst.DBTYPE = SysConst.DBTYPE_ORACLE;
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "123.206.214.39");
//			tDBHashtable.put("Port", "1521");
//			tDBHashtable.put("DBName", "lis");
//			tDBHashtable.put("UserName", "lis6");
//			tDBHashtable.put("PassWord", "lis20160517");
//			tDBHashtable.put("DefaultConn", "1");
//			System.out.println("-------------------云oracle");

			//local 
			SysConst.DBTYPE = SysConst.DBTYPE_ORACLE;
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "127.0.0.1");
//			tDBHashtable.put("Port", "1521");
//			tDBHashtable.put("DBName", "orcl");
//			tDBHashtable.put("UserName", "lis7");
//			tDBHashtable.put("PassWord", "lis7");
			
			//hsbw
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "59.108.60.203");
//			tDBHashtable.put("Port", "40674");
//			tDBHashtable.put("DBName", "orcl");
//			tDBHashtable.put("UserName", "LIS");
//			tDBHashtable.put("PassWord", "LIS");
////			
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "192.168.30.162");
//			tDBHashtable.put("Port", "1521");
//			tDBHashtable.put("DBName", "orcl");
//			tDBHashtable.put("UserName", "LIS");
//			tDBHashtable.put("PassWord", "LIS");
			
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "192.168.30.162");
//			tDBHashtable.put("Port", "1521");
//			tDBHashtable.put("DBName", "orcl");
//			tDBHashtable.put("UserName", "LIS");
//			tDBHashtable.put("PassWord", "LIS");
			//31.7 
//			SysConst.DBTYPE = SysConst.DBTYPE_ORACLE;
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "192.168.31.7");
//			tDBHashtable.put("Port", "1521");
//			tDBHashtable.put("DBName", "orcl");
//			tDBHashtable.put("UserName", "lis7");
//			tDBHashtable.put("PassWord", "lis7");
			
			//环境外网
//			tDBHashtable.put("DBType", "ORACLE");
//			tDBHashtable.put("IP", "59.108.99.163");
//			tDBHashtable.put("Port", "4023");
//			tDBHashtable.put("DBName", "pdborcl.localdomain");
//			tDBHashtable.put("UserName", "lis");
//			tDBHashtable.put("PassWord", "lis");
			
			tDBHashtable.put("DBType", "ORACLE");
			tDBHashtable.put("IP", "localhost");
			tDBHashtable.put("Port", "1521");
			tDBHashtable.put("DBName", "orcl");
			tDBHashtable.put("UserName", "LIS");
			tDBHashtable.put("PassWord", "sino");
			
			
			tDBHashtable.put("DefaultConn", "1");
			
//			云MySQL
//			 SysConst.DBTYPE= SysConst.DBTYPE_MYSQL;
//			 tDBHashtable.put("DBType", "MYSQL");
//			 tDBHashtable.put("IP", "5680f0940bec6.sh.cdb.myqcloud.com");
//			 tDBHashtable.put("Port", "7770");
//			 tDBHashtable.put("DBName", "lis");
//			 tDBHashtable.put("UserName", "lis");
//			 tDBHashtable.put("PassWord", "lis");
//			 tDBHashtable.put("DefaultConn", "1");
//			 System.out.println("-------------------云mysql");
			 //Default为默认连接名,不要修改
			 
//云	ORACLE	 
//			 tDBHashtable.put("DBType", "ORACLE");
//			 tDBHashtable.put("IP", " 123.206.214.39");
//			 tDBHashtable.put("Port", "15210");
//			 tDBHashtable.put("DBName", "lis");
//			 tDBHashtable.put("UserName", "lis6");
//			 tDBHashtable.put("PassWord", "lis20160517");
//			 tDBHashtable.put("DefaultConn", "1");
//			 mPoolHashtable.put("Default", tDBHashtable);


			mPoolHashtable.put("Local", tDBHashtable);
		} else {
			GetConfigFromFile();
		}

	}

	private void getOnePoolData(String tResourceName) {
		if (mPoolHashtable.containsKey(tResourceName)) {
			Hashtable tDBHashtable = new Hashtable();
			tDBHashtable = (Hashtable) mPoolHashtable.get(tResourceName);
			DBType = tDBHashtable.get("DBType") == null ? ""
					: (String) tDBHashtable.get("DBType");
			IP = tDBHashtable.get("IP") == null ? "" : (String) tDBHashtable
					.get("IP");
			Port = tDBHashtable.get("Port") == null ? ""
					: (String) tDBHashtable.get("Port");
			DBName = tDBHashtable.get("DBName") == null ? ""
					: (String) tDBHashtable.get("DBName");
			UserName = tDBHashtable.get("UserName") == null ? ""
					: (String) tDBHashtable.get("UserName");
			PassWord = tDBHashtable.get("PassWord") == null ? ""
					: (String) tDBHashtable.get("PassWord");
		}
	}

	public JdbcUrl() {
		// WebLogic连接池，其中MyPool为连接池的名称

		// DBType = "WEBLOGICPOOL";
		// DBName = "MyPool";

		// DBType = "ORACLE";
		// IP = "192.168.71.246";
		// Port = "1521";
		// DBName = "lisnational";
		// UserName = "lisopr";
		// PassWord = "lisopr";

		this.getOnePoolData("Local");

		// DBType = "FileConfig";

		// if (DBType.trim().toUpperCase().equals("FILECONFIG"))
		// {
		// GetConfigFromFile("");
		// }
	}

	// tongmeng 2011-06-21 add
	// 增加多连接池
	public JdbcUrl(String tResourceName) {
		// WebLogic连接池，其中MyPool为连接池的名称

		this.getOnePoolData(tResourceName);

		// if (DBType.trim().toUpperCase().equals("FILECONFIG"))
		// {
		// GetConfigFromFile(tResourceName);
		// }
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
			sUrl = "jdbc:oracle:thin:" + UserName + "/" + PassWord + "@" + IP
					+ ":" + Port + "/" + DBName;
		} 
		else if (DBType.trim().toUpperCase().equals("INFORMIX")) {
			sUrl = "jdbc:informix-sqli://" + IP + ":" + Port + "/" + DBName
					+ ":" + "informixserver=" + ServerName + ";" + "user="
					+ UserName + ";" + "password=" + PassWord + ";";
		} else if (DBType.trim().toUpperCase().equals("SQLSERVER")) {
			sUrl = "jdbc:microsoft:sqlserver://" + IP + ":" + Port
					+ ";databaseName=" + DBName + ";selectMethod=cursor";
		} else if (DBType.trim().toUpperCase().equals("WEBLOGICPOOL")) {
			sUrl = "jdbc:weblogic:pool:" + DBName;
		} else if (DBType.trim().toUpperCase().equals("MYSQL")) {
			sUrl = "jdbc:mysql://" + IP + ":" + Port + '/' + DBName + "?useUnicode=true&amp;characterEncoding=UTF-8";
		}
		return sUrl;
	}

	static void GetConfigFromFile() {
		// tDBType = "";
		String tPath = getPath();
		ArrayList DBNameList = null;
		try {
			DBNameList = FileName(tPath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (DBNameList == null || DBNameList.size() <= 0)
			;
		for (int i = 0; i <= DBNameList.size() - 1; i++) {
			try {
				String[] tFileName = (String[]) DBNameList.get(i);
				InputStreamReader strInStream = new InputStreamReader(
						getDBConfig(tPath, tFileName), "UTF-8");
				// DOMBuilder domBuilder = new DOMBuilder();
				SAXBuilder builder = new SAXBuilder();
				Document D = builder.build(strInStream);
				Element root = D.getRootElement();
				List tList = root.getChildren("DB");
				if (tList == null || tList.size() <= 0)
					continue;
				for (int j = 0; j < tList.size(); j++) {
					try {
						Element Info = (Element) tList.get(j);

						// String tD
						String tDefaultConn = Info
								.getChildTextTrim("DefaultConn");
						String tConnName = Info.getChildTextTrim("ConnName");

						String tDBType = Info.getChildTextTrim("DBType");
						String tIP = Info.getChildTextTrim("IP");
						String tPort = Info.getChildTextTrim("Port");
						String tDBName = Info.getChildTextTrim("DBName");
						String tUserName = Info.getChildTextTrim("UserName");
						String tPassWord = Info.getChildTextTrim("PassWord");
						// String tServerName =
						// Info.getChildTextTrim("ServerName");

						Hashtable tDBHashtable = new Hashtable();
						tDBHashtable.put("DBType", tDBType);
						tDBHashtable.put("IP", tIP);
						tDBHashtable.put("Port", tPort);
						tDBHashtable.put("DBName", tDBName);
						tDBHashtable.put("UserName", tUserName);
						tDBHashtable.put("PassWord", tPassWord);
						tDBHashtable.put("DefaultConn", tDefaultConn);
						if (tDefaultConn.equals("1")) {
							mPoolHashtable.put("Default", tDBHashtable);
						} else {
							mPoolHashtable.put(tConnName, tDBHashtable);
						}

						if (Debug) {
							logger.debug("数据库连接   " + "DBType=" + tDBType
									+ ";IP=" + tIP + ";Port=" + tPort
									+ ";DBName=" + tDBName + ";User="
									+ tUserName + ";PassWord=" + tPassWord
									// +";ServerName="+tServerName
									+ "");
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}
				// if(tDBType == null || tDBType.equals("")) continue;

			} catch (Exception ex) {
				ex.printStackTrace();
				continue;
			}
		}

	}

	private static InputStream getDBConfig(String tPath, String tFileName[]) {
		InputStream tDBConfigIns = null;
		InputStream tKeyIns = null;
		ByteArrayOutputStream tDBConfigbaos = null ;
		ByteArrayOutputStream tKeybaos = null;
		String keyName = null;
		
		try {
			
			
			
			try {
				tDBConfigIns = new FileInputStream(tPath + tFileName[0]
						+ "." + tFileName[1]);
			
				tDBConfigbaos = new ByteArrayOutputStream();
				tKeybaos = new ByteArrayOutputStream();
				
			/** 解密 **/
			
			File directory = new File(tPath);
			File[] files = directory.listFiles();
			for (int i = 0; i <= files.length - 1; i++) {
				String fileName = files[i].getName();
				int m = fileName.indexOf(".");
				if (m <= 0)
					continue;
				if (tFileName[0].equalsIgnoreCase(fileName.substring(0, m))
						&& "dat".equalsIgnoreCase((fileName.substring(m + 1)))) {
					keyName = fileName;
					break;
				}
			}
			if (keyName == null) {
				logger.debug("未找到密钥文件");
				return tDBConfigIns;
			}
			
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				logger.debug("未找到密钥文件");
				return null;
			}
			
			int i, j;
			try {
				
				tKeyIns = new FileInputStream(tPath + keyName);
				while ((j = tKeyIns.read()) != -1) {
					tKeybaos.write(j);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			while ((i = tDBConfigIns.read()) != -1) {
				tDBConfigbaos.write(i);
			}
			
			byte[] key = hex2byte(new String(tKeybaos.toByteArray()));
			byte[] b = decode(tDBConfigbaos.toByteArray(), key);
			if (Debug) {
				logger.debug(new String(tKeybaos.toByteArray()));
			}
//			tDBConfigIns.close();
//			tKeyIns.close();
//			tDBConfigbaos.close();
//			tKeybaos.close();
			return (new ByteArrayInputStream(b));
		} catch (Exception ex) {
			
			return null;
		}finally{
			if(tDBConfigIns!=null){
				try {
					tDBConfigIns.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(tKeyIns!=null){
				try {
					tKeyIns.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(tDBConfigbaos!=null){
				try {
					tDBConfigbaos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(tKeybaos!=null){
				try {
					tKeybaos.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// 将16进制字符串转换成字节码
	public static byte[] hex2byte(String strHex) {
		byte[] bHex = new byte[8];
		if (strHex == null || strHex.length() != 16) {
			return null;
		}
		for (int nIndex = 0; nIndex < 8; nIndex++) {
			int n = Integer.parseInt(
					strHex.substring(nIndex * 2, nIndex * 2 + 2), 16);
			bHex[nIndex] = (byte) n;
		}
		return bHex;
	}

	// 解密
	public static byte[] decode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, "DES");
		Cipher c1 = Cipher.getInstance("DES");
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

	private static String getPath() {
		String tPath = JdbcUrl.class.getResource("").getPath();
		int n = tPath.indexOf("business.jar");
		if (n != -1) {
			tPath = tPath.substring(0, n) + "APP-INF/classes/dbconfig/";
		} else {
			n = tPath.indexOf("WEB-INF");
			tPath = tPath.substring(0, n) + "dbconfig/";
		}

		if (Debug) {
			logger.debug("配置文件所在目录[" + tPath + "]");
		}
		return tPath;
	}

	private static ArrayList FileName(String tPath) throws Exception {
		try {
			ArrayList tArrayList = new ArrayList();
			File directory = new File(tPath);
			File[] files = directory.listFiles();
			for (int i = 0; i <= files.length - 1; i++) {
				String fileName = files[i].getName();
				int m = fileName.indexOf(".");
				if (m <= 0)
					continue;
				if (!"xml".equalsIgnoreCase((fileName.substring(m + 1)))) {
					continue;
				}
				String[] tName = { fileName.substring(0, m),
						fileName.substring(m + 1) };
				tArrayList.add(tName);
			}
			return tArrayList;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public static void main(String args[]) {
		JdbcUrl J = new JdbcUrl();
		logger.debug(J.getJdbcUrl());
	}

}
