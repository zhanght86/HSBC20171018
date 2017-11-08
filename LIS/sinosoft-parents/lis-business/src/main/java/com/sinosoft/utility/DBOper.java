/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

// import com.sinosoft.lis.schema.LAComSchema;
/*
 * <p>ClassName: DBOper </p> <p>Description: 数据库操作类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: HST
 * @CreateDate：2002-06-27
 */
public class DBOper {
private static Logger logger = Logger.getLogger(DBOper.class);
	// @Field
	private Connection con;
	private String tableName;
	private boolean cflag = false;
	private Logger m_log = null; // 增加错误信息输入记录
	public CErrors mErrors = new CErrors(); // 错误信息

	// @Constructor
	public DBOper(Connection tConnection, String t) {
		m_log = Logger.getLogger(DBOper.class);
		con = tConnection;
		tableName = t;

	}

	public DBOper(String t) {
		m_log = Logger.getLogger(DBOper.class);
		tableName = t;
		con = null;
		cflag = true;
	}

	// @Method
	public Connection getConnection() {
		// return con;
		return DBConnPool.getConnection();
	}

	/**
	 * db的插入操作
	 * 
	 * @param s
	 *            Schema
	 * @return boolean
	 */
	public boolean insert(Schema s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;

		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "insert";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			stmt = con.createStatement();
			SQLString sqlObj = new SQLString(tableName);
			// 设置插入属性
			sqlObj.setSQL(1, s);
			sql = sqlObj.getSQL();

			int operCount = stmt.executeUpdate(sql);
			if (operCount > 0) {
				flag = true;
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DBOper";
				tError.functionName = "insert";
				tError.errorMessage = "实际操作的数据记录数为０条数据";
				this.mErrors.addOneError(tError);

				flag = false;
			}
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理类

			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "insert";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

		return flag;
	}

	/**
	 * db的批量插入操作
	 * 
	 * @param s
	 *            SchemaSet
	 * @return boolean
	 */
	public boolean insert(SchemaSet s) {
		Statement stmt = null;
		String sql = null;
		boolean flag;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}
		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "insert";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			int n = s.size();
			Schema aSchema = null;
			sql = null;
			SQLString sqlObj = null;
			stmt = con.createStatement();
			for (int i = 1; i <= n; i++) {
				aSchema = (Schema) s.getObj(i);
				sqlObj = new SQLString(tableName);
				// 设置插入属性
				sqlObj.setSQL(1, aSchema);
				sql = sqlObj.getSQL();
				// logger.debug(sql);
				stmt.addBatch(sql);
			}
			// int operCount = stmt.executeUpdate(sql);
			try {
				// 可以返回全部的数组信息，但是是否需要拿这个数组的信息做判定，还需要观察
				int operCount[] = stmt.executeBatch();
				// 是否需要做如下处理？
				// for (int i = 0; i < operCount.length; i++)
				// {
				// if (operCount[i] <= 0)
				// {
				// flag = false;
				// break;
				// }
				// }
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			stmt.close();
		} catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "insert";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

		return flag;
	}

	/**
	 * db的update操作
	 * 
	 * @param s
	 *            Schema
	 * @return boolean
	 */
	public boolean update(Schema s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "update";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			stmt = con.createStatement();
			SQLString sqlObj = new SQLString(tableName);
			// 设置更新属性
			sqlObj.setSQL(2, s);
			sql = sqlObj.getSQL();

			int operCount = stmt.executeUpdate(sql);
			if (operCount > 0) {
				flag = true;
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DBOper";
				tError.functionName = "update";
				tError.errorMessage = "实际操作的数据记录数为０条数据";
				this.mErrors.addOneError(tError);

				flag = false;
			}
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "update";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * db的批量update操作
	 * 
	 * @param s
	 *            SchemaSet
	 * @return boolean
	 */
	public boolean update(SchemaSet s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "update";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			int n = s.size();
			Schema aSchema = null;
			sql = null;
			SQLString sqlObj = null;
			stmt = con.createStatement();
			for (int i = 1; i <= n; i++) {
				aSchema = (Schema) s.getObj(i);
				sqlObj = new SQLString(tableName);
				// 设置更新属性
				sqlObj.setSQL(2, aSchema);
				sql = sqlObj.getSQL();
				stmt.addBatch(sql);
			}

			try {
				int operCount[] = stmt.executeBatch();
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "update";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * db根据sql删除
	 * 
	 * @param s
	 *            Schema
	 * @return boolean
	 */
	public boolean deleteSQL(Schema s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			stmt = con.createStatement();
			SQLString sqlObj = new SQLString(tableName);
			// 设置删除属性
			sqlObj.setSQL(3, s);
			// 如果删除条件为空，则出错返回，以防止数据被异常删除
			if (sqlObj.getWherePart().compareTo("") == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "DBOper";
				tError.functionName = "delete";
				tError.errorMessage = "删除条件为空";
				this.mErrors.addOneError(tError);

				try {
					stmt.close();
				} catch (Exception ex) {
				}

				if (cflag) {
					try {
						con.close();
					} catch (Exception et) {
					}
				}
				return false;
			}
			sql = sqlObj.getSQL();

			int operCount = stmt.executeUpdate(sql);
			flag = true;
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "deleteSQL";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * db根据sql批量删除
	 * 
	 * @param s
	 *            Schema
	 * @return boolean
	 */
	public boolean deleteSQL(SchemaSet s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;

		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			int n = s.size();
			Schema aSchema = null;
			sql = null;
			SQLString sqlObj = null;
			stmt = con.createStatement();
			for (int i = 1; i <= n; i++) {
				aSchema = (Schema) s.getObj(i);
				sqlObj = new SQLString(tableName);
				// 设置删除属性
				sqlObj.setSQL(3, aSchema);
				// 如果删除条件为空，则出错返回，以防止数据被异常删除
				if (sqlObj.getWherePart().compareTo("") == 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "DBOper";
					tError.functionName = "delete";
					tError.errorMessage = "删除条件为空";
					this.mErrors.addOneError(tError);

					try {
						stmt.close();
					} catch (Exception ex) {
					}

					if (cflag) {
						try {
							con.close();
						} catch (Exception et) {
						}
					}
					return false;
				}
				sql = sqlObj.getSQL();
				stmt.addBatch(sql);
			}

			try {
				int operCount[] = stmt.executeBatch();
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "deleteSQL";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * db根据主键删除
	 * 
	 * @param s
	 *            Schema
	 * @return boolean
	 */
	public boolean delete(Schema s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "delete";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			stmt = con.createStatement();
			SQLString sqlObj = new SQLString(tableName);
			// 设置删除属性
			sqlObj.setSQL(4, s);
			sql = sqlObj.getSQL();

			int operCount = stmt.executeUpdate(sql);
			flag = true;
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "delete";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	/**
	 * db根据主键批量删除
	 * 
	 * @param s
	 *            Schema
	 * @return boolean
	 */
	public boolean delete(SchemaSet s) {
		Statement stmt = null;
		boolean flag;
		String sql = null;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "delete";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			int n = s.size();
			Schema aSchema = null;
			sql = null;
			SQLString sqlObj = null;
			stmt = con.createStatement();
			for (int i = 1; i <= n; i++) {
				aSchema = (Schema) s.getObj(i);
				sqlObj = new SQLString(tableName);
				// 设置删除属性
				sqlObj.setSQL(4, aSchema);
				sql = sqlObj.getSQL();
				stmt.addBatch(sql);
			}

			try {
				int operCount[] = stmt.executeBatch();
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
			stmt.close();
		} // end of try
		catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "delete";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			try {
				stmt.close();
			} catch (Exception ex) {
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return false;
		}
		// 断开数据库连接
		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return flag;
	}

	public int getCount(Schema s) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int RSCount;
		String sql = null;
		// 如果要使用内部的Connection对象
		if (cflag) {
			con = DBConnPool.getConnection();
		}

		if (null == con) {
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "getCount";
			tError.errorMessage = "Connection建立失败!";
			this.mErrors.addOneError(tError);
			return -1;
		}

		try {
			SQLString sqlObj = new SQLString(tableName);
			sqlObj.setSQLNew(7, s);
			sql = sqlObj.getSQL();
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			List tBV = sqlObj.getBV();
			setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			rs.next();
			RSCount = rs.getInt(1);
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
		} catch (Exception e) {
			// @@错误处理
			m_log.error(sql, e);
			CError tError = new CError();
			tError.moduleName = "DBOper";
			tError.functionName = "getCount";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			if(rs!=null){
				try {
					rs.close();
					rs = null;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
			
			if(pstmt!=null){
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}

			if (cflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
			return -1;
		}

		if (cflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}
		return RSCount;
	}
	
	public void setBV(PreparedStatement pstmt, List tBV) throws SQLException {
		for(int i=0;i<tBV.size();i++)
		{
			String[] tParams = (String[])tBV.get(i);
			String tType = tParams[0];
			String tValue = tParams[1];
			switch (Integer.parseInt(tType)) {
				case Schema.TYPE_STRING:
					pstmt.setString(i+1, tValue);
					logger.debug("bind variable "+ (i+1)+": type=STRING, value="+tValue);
					break;
				case Schema.TYPE_DATE:
					pstmt.setDate(i+1,  Date.valueOf(tValue));
					logger.debug("bind variable "+ (i+1)+": type=DATE, value="+tValue);
					break;
				case Schema.TYPE_DOUBLE:
					pstmt.setDouble(i+1, Double.parseDouble(tValue));
					logger.debug("bind variable "+ (i+1)+": type=DOUBLE, value="+tValue);
					break;
				case Schema.TYPE_FLOAT:
					pstmt.setFloat(i+1, Float.parseFloat(tValue));
					logger.debug("bind variable "+ (i+1)+": type=FLOAT, value="+tValue);
					break;
				case Schema.TYPE_INT:
					pstmt.setInt(i+1, Integer.parseInt(tValue));
					logger.debug("bind variable "+ (i+1)+": type=INT, value="+tValue);
					break;
				default:
					logger.debug("出现异常数据类型");
					break;
				}
					
		}
	}
	public static void main(String args[]) {
		// DBOper dbop = new DBOper("LACOM");
		// LJSPaySchema lacomschema = new LJSPaySchema();
		// dbop.insert(lacomschema);
	}
}
