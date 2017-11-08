package com.sinosoft.utility;
import org.apache.log4j.Logger;

/*
 * <p>ClassName:  COracleBlob</p>
 * <p>Description: Oracle数据库Blob字段操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: YT
 * @CreateDate：2003-12-25
 */
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleResultSet;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

public class COracleBlob {
private static Logger logger = Logger.getLogger(COracleBlob.class);
	// 业务处理相关变量
	public COracleBlob() {
	}

	/**
	 * String -->InputStream
	 * 
	 * @param String
	 */
	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	/**
	 * InputStream -->String
	 * 
	 * @param String
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		String urlstr = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("classnotfoundexception :");
			System.err.print(e.getMessage());
		}
		try {
			con = DriverManager.getConnection(urlstr, "wjw", "wjw110226");
			con.setAutoCommit(false);
		} catch (SQLException ex) {
			System.err.println("sqlexception :" + ex.getMessage());
		}
		CMySQLBlob cb = new CMySQLBlob();
		File f = new File("C:\\Users\\wangjingwei_PC\\Desktop\\whatsnew.xml");
		DOMBuilder myBuilder = new DOMBuilder();
		Document myDocument = null;
		try {
			myDocument = myBuilder.build(f);
		} catch (JDOMException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (!cb.UpdateBlob(myDocument, "test", "testBiob", " and id = 1", con)) {
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		try {
			con.commit();
			con.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	/**
	 * 修改某个表的Blob字段
	 * 
	 * @param pInXmlDoc
	 * @param pTabName
	 * @param pUpdateField
	 * @param pWhereSQL
	 * @param pConn
	 */
	public boolean UpdateBlob(Document pInXmlDoc, String pTabName,
			String pUpdateField, String pWhereSQL, Connection pConn) {
		// TODO: implement
		Statement stmt = null;
		ResultSet rs = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("COracleBlob没有传入连接！");
			return false;
		}
		try {
			stmt = pConn.createStatement(OracleResultSet.TYPE_FORWARD_ONLY,
					OracleResultSet.CONCUR_UPDATABLE);
			String szSQL = "SELECT " + pUpdateField + " FROM " + pTabName
					+ " WHERE 1=1  " + pWhereSQL + " FOR UPDATE";
			logger.debug("UpdateBlob :" + szSQL);
			rs = stmt.executeQuery(szSQL);
			if (!rs.next()) {
				logger.debug("COracleBlob该查询条件没有查询到记录！SQL为：" + szSQL);
				rs.close();
				stmt.close();
				return false;
			}

			java.sql.Blob blob = rs.getBlob(1);
			OutputStream os=getOutputStream(blob);
			// 这里必须用weblogic的OracleThinBlob类得到os，不能用Oracle的BLOB类和java的Blob类!!-Fanym
			XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
			XMLOutputter outputter = new XMLOutputter("", false, "UTF-8");
			outputter.output(pInXmlDoc, os);
			os.flush();
			os.close();
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (rs != null){
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
			try {
			
				if (stmt != null){
					stmt.close();
					stmt = null;
				}
			} catch (Exception ex1) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 修改某个表的Blob字段
	 * 
	 * @param pInStream
	 * @param pTabName
	 * @param pUpdateField
	 * @param pWhereSQL
	 * @param pConn
	 */
	public boolean UpdateBlob(InputStream pInStream, String pTabName,
			String pUpdateField, String pWhereSQL, Connection pConn) {
		Statement stmt = null;
		ResultSet rs = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("COracleBlob没有传入连接！");
			return false;
		}
		try {
			// 得到数据输出对象
			stmt = pConn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			String szSQL = "SELECT " + pUpdateField + " FROM " + pTabName
					+ " WHERE 1=1  " + pWhereSQL + " FOR UPDATE";

			logger.debug("szSQL=" + szSQL);
			rs = stmt.executeQuery(szSQL);
			if (!rs.next()) {
				logger.debug("COracleBlob该查询条件没有查询到记录！SQL为：" + szSQL);
				rs.close();
				stmt.close();
				return false;
			}

			java.sql.Blob blob = rs.getBlob(1);
			OutputStream os=getOutputStream(blob);
			
			// 这里必须用weblogic的OracleThinBlob类得到os，不能用Oracle的BLOB类和java的Blob类!!-Fanym
			InputStream ins = pInStream;
			int inData = 0;
			while ((inData = ins.read()) != -1) {
				os.write(inData);
			}
			os.flush();
			os.close();
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (rs != null){
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
			if (stmt != null){
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			return false;
		}

		return true;
	}

	private OutputStream getOutputStream(Blob blob) throws Exception{
		try {
			Class otbclazz=Class.forName("weblogic.jdbc.vendor.oracle.OracleThinBlob");
			if(otbclazz.isInstance(blob)){
				Method m=otbclazz.getMethod("getBinaryOutputStream", null);
				OutputStream os=(OutputStream)m.invoke(blob, null);
				return os;
			}
		} catch (ClassNotFoundException e) {
		}
		oracle.sql.BLOB bb=(oracle.sql.BLOB)blob;
		return bb.getBinaryOutputStream();
	}

	/**
	 * @param pInsertSQL
	 * @param pConn
	 */
	public boolean DeleteBlobRecord(String pDeleteSQL, Connection pConn) {
		Statement stmt = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("COracleBlob没有传入连接！");
			return false;
		}
		try {
			// 得到数据输出对象
			stmt = pConn.createStatement(
					OracleResultSet.TYPE_FORWARD_ONLY,
					OracleResultSet.CONCUR_UPDATABLE);
			String szSQL = pDeleteSQL;
			stmt.executeUpdate(szSQL);
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex1) {
			}
			return false;
		}
		return true;
	}

	/**
	 * @param pInsertSQL
	 * @param pConn
	 */
	public boolean InsertBlankBlobRecord(String pInsertSQL, Connection pConn) {
		Statement stmt = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("COracleBlob没有传入连接！");
			return false;
		}
		try {
			// 得到数据输出对象
			logger.debug("###########: " + pInsertSQL);
			stmt = pConn.createStatement(
					OracleResultSet.TYPE_FORWARD_ONLY,
					OracleResultSet.CONCUR_UPDATABLE);
			stmt.executeUpdate(pInsertSQL);
			stmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex1) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 读取指定SQL语句中的某个Blob字段到一个OutStream中。
	 * 
	 * @param pOutStream
	 * @param pLocationSQL
	 * @param pSelectField
	 * @param pConn
	 *            如果传入的pConn为null，则在函数内部创建连接。
	 */
	public boolean SelectBlob(OutputStream pOutStream, String pTabName,
			String pSelectField, String pWhereSQL, Connection pConn) {
		// TODO: implement
		return false;
	}

	/**
	 * 读取指定SQL语句中的某个Blob字段到一个OutStream中。
	 * 
	 * @param pOutStream
	 * @param pLocationSQL
	 * @param pSelectField
	 * @param pConn
	 *            如果传入的pConn为null，则在函数内部创建连接。
	 */
	public Blob SelectBlob(String pTabName, String pSelectField,
			String pWhereSQL, Connection pConn) {
		Statement stmt = null;
		ResultSet rs = null;
		java.sql.Blob tOutData = null;

		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("COracleBlob没有传入连接！");
			return null;
		}
		try {
			stmt = pConn.createStatement();
			String szSQL;
			szSQL = "SELECT " + pSelectField + " FROM " + pTabName
					+ " WHERE 1=1 " + pWhereSQL;
			rs = stmt.executeQuery(szSQL);
			if (!rs.next()) {
				logger.debug("找不到打印数据,SQL为：" + szSQL);
				rs.close();
				stmt.close();
				return null;
			}
			tOutData = rs.getBlob(1);
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (rs != null){
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
			if (stmt != null){
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
			return null;
		}
		return tOutData;
	}

}
