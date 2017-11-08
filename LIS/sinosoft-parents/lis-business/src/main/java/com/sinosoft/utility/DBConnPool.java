/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/*
 * <p>ClassName: dbConnsPool </p> <p>Description: 数据库连接池 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-10-04
 */

public class DBConnPool {
private static Logger logger = Logger.getLogger(DBConnPool.class);
	// 虽然没有调用该对象，但是没有的话，无法连接到数据库，采用一种很古怪的方式来实现类的加载
	// private static DBConnPool dbconnpool = new DBConnPool();

	// 连接池对象
	private static DBConn dbConns[];
	// 设置应用程序的最大连接数，可相对扩充，但是会常驻内存，占用空间，因此大小需要适度
	private static final int nConnCount = 63;

	// @Constructor
	static {
		dbConns = new DBConn[nConnCount];
		for (int nIndex = 0; nIndex < nConnCount; nIndex++) {
			dbConns[nIndex] = new DBConn();
		}
	}

	// 构建函数
	private DBConnPool() {
	}

	/**
	 * 获取连接
	 * 
	 * @return DBConn
	 */
	static public DBConn getConnection() {
		JdbcUrl JUrl = new JdbcUrl();
		// update by wangzw,为了支持WebLogic、apache、WebSphere的连接池，在得到连接的最前面判断
		if (JUrl.jndi || JUrl.getDBType().toUpperCase().equals("WEBLOGICPOOL")
				|| JUrl.getDBType().toUpperCase().equals("COMMONSDBCP")
				|| JUrl.getDBType().toUpperCase().equals("WEBSPHERE")) {
			DBConn tDBConn = new DBConn();
			if (tDBConn.createConnection()) {
				return tDBConn;
			} else {
				return null;
			}
		}

		try {
			DBSemaphore.Lock();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		// 如果没有返回，则表示使用的是自己的连接池
		for (int nIndex = 0; nIndex < nConnCount; nIndex++) {
			DBConn dbConn = dbConns[nIndex];
			// 判定连接是否被使用
			if (dbConn.isInUse()) {
				continue;
			}
			if (!dbConn.createConnection()) {
				// 如果创建连接失败
				DBSemaphore.UnLock();
				return null;
			}
			// 如果连接数超过1的话，才输出，否则输出太多，很烦人
			if (nIndex >= 1) {
				logger.debug("DBConnPool : get connection, index is "
						+ String.valueOf(nIndex));
			}
			try {
				// 特殊处理连接的AutoCommit是否已经被设置
				dbConn.setAutoCommit(true);
				dbConn.setInUse();
				DBSemaphore.UnLock();
				return dbConn;
			} catch (Exception ex) {
				ex.printStackTrace();

				DBSemaphore.UnLock();

				return null;
			}
		}
		// 如果全部的连接数都被占用的话，应用程序出错
		logger.debug("DBConnPool : All connections are in use");
		// 如果为了应用正常流转，可以在这里强制释放掉全部的应用连接数
		DBSemaphore.UnLock();
		return null;
	}

	/**
	 * 获取连接
	 * tongmeng 2011-06-22 add
	 * 如果传连接的话,不能使用dbConns的对象 
	 * @return DBConn
	 */
	static public DBConn getConnection(String tResourceName) {
		try
		{
			DBConn tDBConn = new DBConn(tResourceName);
			if (tDBConn.createConnection()) {
				return tDBConn;
			} else {
				return null;
			}
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * 连接查看
	 * 
	 * @param os
	 *            OutputStream
	 */
	public static void dumpConnInfo(OutputStream os) {
		try {
			if (dbConns == null) {
				os.write("all connections are free".getBytes());
				return;
			}

			for (int nIndex = 0; nIndex < nConnCount; nIndex++) {
				DBConn dbConn = dbConns[nIndex];

				os
						.write((String.valueOf(nIndex) + "------------------------------------\r\n\r")
								.getBytes());
				if (dbConn != null && dbConn.isInUse()) {
					dbConn.dumpConnInfo(os);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * close connection
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			conn = null;
			e.printStackTrace();
		}
	}

	protected static DBConn[] getDBConns() {
		return dbConns;
	}
}
