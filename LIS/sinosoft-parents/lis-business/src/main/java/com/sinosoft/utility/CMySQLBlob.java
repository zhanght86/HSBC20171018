package com.sinosoft.utility;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleResultSet;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CMySQLBlob {
	private static Logger logger = Logger.getLogger(CMySQLBlob.class);

	// 业务处理相关变量
	public CMySQLBlob() {
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
	 * 插入某个表的Blob字段
	 * 
	 * @param pSQL
	 *            String
	 * @param pTabName
	 *            String
	 * @param pUpdateField
	 *            String
	 * @param pWhereSQL
	 *            String
	 * @param pInStream
	 *            InputStream
	 * @param pConn
	 *            Connection
	 * @return boolean
	 */
	public boolean InsertBlobRecord(String pSQL, String pTabName,
			String pUpdateField, String pWhereSQL, InputStream pInStream,
			Connection pConn) {
		Statement stmt = null;
		PreparedStatement pstmt = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("CMySqlBlob没有传入连接！");
			return false;
		}
		if (pInStream == null) {
			String t = "";
			ByteArrayInputStream isinput = new ByteArrayInputStream(
					t.getBytes());
			pInStream = isinput;
		}
		try {
			// 得到数据输出对象
			logger.debug("###########: " + pSQL);
			stmt = pConn.createStatement();
			stmt.executeUpdate(pSQL);
			stmt.close();
			pstmt = pConn.prepareStatement("update " + pTabName + " set "
					+ pUpdateField + " =" + "?" + " WHERE 1=1  " + pWhereSQL);
			pstmt.setBinaryStream(1, pInStream);// 想数据库里插入，就一行，但这种方法只有mysql可以用
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex1) {
			}
			return false;
		}
		return true;
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
		PreparedStatement stmt1 = null;
		ResultSet rs = null;
		if (pConn == null) // 如果连接，则返回false
		{
			logger.debug("pConn没有传入连接！");
			return false;
		}
		try {
			String szSQL = "update " + pTabName + " set "
					+ pUpdateField + " =" + "?" + " WHERE 1=1  " + pWhereSQL;
			stmt1 = pConn.prepareStatement(szSQL);
			stmt1.setObject(1, pInXmlDoc);// 向数据库里插入，就一行，但这种方法只有mysql可以用
			stmt1.executeUpdate();
			stmt1.close();
			stmt1 = null;
			logger.debug("UpdateBlob :" + szSQL);
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (stmt1 != null) {
					stmt1.close();
					stmt1 = null;
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
	 * @return boolean
	 */
	public boolean UpdateBlob(InputStream pInStream, String pTabName,
			String pUpdateField, String pWhereSQL, Connection pConn) {
		Statement stmt = null;//
		PreparedStatement stmt1 = null;
		ResultSet rs = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("CMySqlBlob没有传入连接！");
			return false;
		}
		try {
			// 得到数据输出对象
			stmt = pConn.createStatement();
			String szSQL = "SELECT " + pUpdateField + " FROM " + pTabName
					+ " WHERE 1=1  " + pWhereSQL;

			logger.debug("szSQL=" + szSQL);
			rs = stmt.executeQuery(szSQL);
			if (!rs.next()) {
				logger.debug("CMySqlBlob该查询条件没有查询到记录！SQL为：" + szSQL);
				rs.close();
				stmt.close();
				return false;
			}
			java.sql.Blob blob = rs.getBlob(1);
			stmt1 = pConn.prepareStatement("update " + pTabName + " set "
					+ pUpdateField + " =" + "?" + " WHERE 1=1  " + pWhereSQL);
			stmt1.setBinaryStream(1, pInStream);// 想数据库里插入，就一行，但这种方法只有mysql可以用
			stmt1.executeUpdate();
			stmt1.close();
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
			return false;
		}
		return true;
	}

	private OutputStream getOutputStream(Blob blob) throws Exception {
		try {
			OutputStream outStr = blob.setBinaryStream(1);
			return outStr;

		} catch (SQLException e) {
		}

		return blob.setBinaryStream(1);
	}

	/**
	 * 删除某个表的Blob字段
	 * 
	 * @param pDeleteSQL
	 * @param pConn
	 * @return boolean
	 */
	public boolean DeleteBlobRecord(String pDeleteSQL, Connection pConn) {
		Statement stmt = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("CMySqlBlob没有传入连接！");
			return false;
		}
		try {
			// 得到数据输出对象
			stmt = pConn.createStatement();
			stmt.executeUpdate(pDeleteSQL);
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
	 * 插入某个表的Blob字段
	 * 
	 * @param pSQL
	 *            String
	 * @param pTabName
	 *            String
	 * @param pUpdateField
	 *            String
	 * @param pWhereSQL
	 *            String
	 * @param pInStream
	 *            InputStream
	 * @param pConn
	 *            Connection
	 * @return boolean
	 */
	public boolean InsertBlankBlobRecord(String pInsertSQL, Connection pConn) {
		
		PreparedStatement pstmt = null;
		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("CMySqlBlob没有传入连接！");
			return false;
		}
		try {
			// 得到数据输出对象
			logger.debug("###########: " + pInsertSQL);
			pstmt = pConn.prepareStatement(pInsertSQL);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (pstmt != null)
					pstmt.close();
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
	 * @param pTabName
	 * @param pSelectField
	 * @param pWhereSQL
	 * @param pConn
	 * 
	 */
	public Blob SelectBlob(String pTabName, String pSelectField,
			String pWhereSQL, Connection pConn) {
		Statement stmt = null;
		ResultSet rs = null;
		java.sql.Blob tOutData = null;

		if (pConn == null) // 如果连接没有寸入，则返回false
		{
			logger.debug("CMySqlBlob没有传入连接！");
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
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
					stmt = null;
				} catch (SQLException e) {
				}
			}

			return null;
		}
		return tOutData;
	}

	public static void main(String[] args) throws SQLException {
		String urlstr = "jdbc:mysql://localhost:3306/mysql";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("classnotfoundexception :");
			System.err.print(e.getMessage());
		}
		try {
			con = DriverManager.getConnection(urlstr, "root", "110226wjwj");
			con.setAutoCommit(false);
		} catch (SQLException ex) {
			System.err.println("sqlexception :" + ex.getMessage());
		}
		CMySQLBlob cb = new CMySQLBlob();
//		File f = new File("C:\\Users\\wangjingwei_PC\\Desktop\\whatsnew.xml");
//		DOMBuilder myBuilder = new DOMBuilder();
//		Document myDocument = null;
//		try {
////			myDocument = myBuilder.build(f);
//		} catch (JDOMException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
			if (!cb.InsertBlankBlobRecord("INSERT INTO test(id,name,testBlob) VALUES (3,'wjw1','sdfasdfadsfadsfdasfadsf')", con)) {
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			
			
		}
			try {
				con.commit();
				
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		con.close();
	}

}
