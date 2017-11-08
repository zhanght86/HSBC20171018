package com.sinosoft.utility;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

public class ExeSQLwithBindVariables {

	private static Logger logger = Logger.getLogger(ExeSQLwithBindVariables.class);
	private Connection con;

	private boolean mflag = false;

	public CErrors mErrors = new CErrors(); // 错误信息

	protected ExeSQLwithBindVariables(Connection tConnection) {
		con = tConnection;
		mflag = true;
	}

	protected ExeSQLwithBindVariables() {
	}
	
	private String getOneValueProcedure(SQLwithBindVariables sqlbv) {
		CallableStatement cstmt = null;
		String mValue = "";
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			cstmt = con.prepareCall(StrTool.GBKToUnicode(sqlbv.sql()));
			sqlbv.setParameters(cstmt);
			//强制要求第一个参数为输出参数
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.executeUpdate();
			mValue = cstmt.getString(1);
			cstmt.close();
			cstmt = null;
			// 如果连接是类创建的，则关闭连接
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at OneValue: " + sqlbv.originalSql());
			CError.buildErr(this, e.toString(), mErrors);
			mValue = "";
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
					cstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}

		}
		return StrTool.cTrim(mValue);
	}

	/**
	 * 获取唯一的返回值
	 */
	protected String getOneValue(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String mValue = "";
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return "";
		}
		if(sqlbv.sql().matches("\\s*\\{\\s*[Cc][Aa][Ll][Ll]\\s+\\S+\\s*\\([\\s\\S]*\\)\\s*\\}\\s*")){
			return getOneValueProcedure(sqlbv);
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				// 其实并不是很合适，主要是因为有可能取得对象的数据类型有误
				int dataType = rsmd.getColumnType(1);
				if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT) ||(dataType == Types.REAL) || (dataType == Types.DOUBLE)) {
					BigDecimal tBigDecimal = rs.getBigDecimal(1);
					if (tBigDecimal == null) {
						mValue = null;
					} else {
						mValue = String.valueOf(tBigDecimal);
						if (mValue.indexOf(".") > 0) {
							mValue = mValue.replaceAll("0+?$", "");// 去掉后面无用的零
							mValue = mValue.replaceAll("\\.$", "");// 如果以小数点结尾去掉小数点
						}
					}
				} else {
					mValue = rs.getString(1);
				}
				break;
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at OneValue: " + sqlbv.originalSql());
			CError.buildErr(this, e.toString(), mErrors);
			mValue = "";
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return StrTool.cTrim(mValue);
	}

	/**
	 * 获取SQL的查询结果记录数
	 */
	private int getResultCount(SQLwithBindVariables sqlbv, PreparedStatement pstmt, ResultSet rs) {
		int iCount = 0;
		String sql = "select count(1) from (" + sqlbv.sql() + ") rsc";
		logger.debug("getResultCount : " + sql);
		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				iCount = rs.getInt(1);
				break;
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
		} catch (SQLException e) {
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			iCount = 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return iCount;
	}

	/**
	 * 从指定位置查询全部数据
	 * 
	 */
	protected String getEncodedResult(SQLwithBindVariables sqlbv, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		StringBuffer mResult = new StringBuffer(256);
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return "";
		}
		if (start <= 0) {
			start = 1;
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			int m = start + (SysConst.MAXSCREENLINES * SysConst.MAXMEMORYPAGES);
			int k = 0;
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sqlbv.originalSql());

			while (rs.next()) {
				dt.increase();
				k++;
				if ((k >= start) && (k < m)) {
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(ExeSQL.getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER + ExeSQL.getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}
			if (k >= start) {
				if (k > 10000) {
					logger.debug("建议采用大批量数据查询模式！");
				}
				mResult.insert(0, "0|" + String.valueOf(k) + SysConst.RECORDSPLITER);
				mResult.delete(mResult.length() - 1, mResult.length());
			} else {
				mResult.append("100|未查询到相关数据!");
			}
			rs.close();
			pstmt.close();
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return mResult.toString();
	}

	/**
	 * 从指定位置查询全部数据 数据大于200条但小于1000条
	 */
	protected String getEncodedResultEx(SQLwithBindVariables sqlbv, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		StringBuffer mResult = new StringBuffer(256);
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return "";
		}
		if (start <= 0) {
			start = 1;
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			int m = start + 1000;
			int k = 0;
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sqlbv.originalSql());
			while (rs.next()) {
				if (!dt.increase()) {
					return "";
				}
				k++;
				if ((k >= start) && (k < m)) {
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(ExeSQL.getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER + ExeSQL.getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}
			if (k >= start) {
				if (k > 10000) {
					logger.debug("建议采用大批量数据查询模式！");
				}
				mResult.insert(0, "0|" + String.valueOf(k) + SysConst.RECORDSPLITER);
				mResult.delete(mResult.length() - 1, mResult.length());
			} else {
				mResult.append("100|未查询到相关数据!");
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);

		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}

		}
		return mResult.toString();
	}

	/**
	 * 从指定位置查询全部数据，此方法为大数据量查询
	 */
	protected String getEncodedResultLarge(SQLwithBindVariables sqlbv, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		StringBuffer mResult = new StringBuffer(256);
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return "";
		}
		if (start <= 0) {
			start = 1;
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			int iCount = getResultCount(sqlbv, pstmt, rs);
			if (iCount <= 0) {
				try {
					if (!mflag) {
						con.close();
					}
				} catch (SQLException ex) {
				}
				return "100|未查询到相关数据!";
			}
			int m = start + (SysConst.MAXSCREENLINES * SysConst.MAXMEMORYPAGES);
			StringBuffer tSBql = new StringBuffer();
			if (SysConst.DBTYPE.equals("ORACLE")) {
				tSBql.append("select * from (select rownum rnm,rs.* from (");
				tSBql.append(sqlbv.sql());
				tSBql.append(") rs where rownum < ");
				tSBql.append(m);
				tSBql.append(") rss where rnm >= ");
				tSBql.append(start);
			}else if (SysConst.DBTYPE.equals("MYSQL")) {
				tSBql.append("select '1',rs.* from (");
				tSBql.append(sqlbv.sql());
				tSBql.append(") rs limit ");
				tSBql.append(start-1);
				tSBql.append(",");
				tSBql.append(m-start);
			}else {
				tSBql.append("select * from (select rownumber() OVER () rnm ,rs.* from (");
				tSBql.append(sqlbv.sql());
				tSBql.append(") rs) rss WHERE rnm BETWEEN ");
				tSBql.append(start);
				tSBql.append(" and ");
				tSBql.append(m - 1);
			}
			logger.debug("ExecSQL : " + tSBql.toString());
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(tSBql.toString()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			int k = 0;
			while (rs.next()) {
				k++;
				for (int j = 2; j <= n; j++) {
					if (j == 2) {
						mResult.append(ExeSQL.getDataValue(rsmd, rs, j));
					} else {
						mResult.append(SysConst.PACKAGESPILTER + ExeSQL.getDataValue(rsmd, rs, j));
					}
				}
				mResult.append(SysConst.RECORDSPLITER);
			}
			if (k > 0) {
				mResult.insert(0, "0|" + String.valueOf(iCount) + SysConst.RECORDSPLITER);
				mResult.delete(mResult.length() - 1, mResult.length());
			} else {
				mResult.append("100|未查询到相关数据!");
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at getEncodedResultLarge(String sql, int start): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);

		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return mResult.toString();
	}

	/**
	 * 查询数据
	 */
	protected String getEncodedResult(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		StringBuffer mResult = new StringBuffer(256);
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			pstmt.setFetchSize(500);
			rs = pstmt.executeQuery();
			rs.setFetchSize(500);
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			int k = 0;
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sqlbv.originalSql());

			while (rs.next()) {
				if (!dt.increase()) {
					return "";
				}
				k++;
				for (int j = 1; j <= n; j++) {
					if (j == 1) {
						mResult.append(ExeSQL.getDataValue(rsmd, rs, j));
					} else {
						mResult.append(SysConst.PACKAGESPILTER);
						mResult.append(ExeSQL.getDataValue(rsmd, rs, j));
					}
				}
				mResult.append(SysConst.RECORDSPLITER);
			}
			if (k > 0) {
				mResult.insert(0, "0|" + String.valueOf(k) + SysConst.RECORDSPLITER);
				mResult.delete(mResult.length() - 1, mResult.length());
			} else {
				mResult.append("100|未查询到相关数据！");
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at getEncodedResult(String sql): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);

		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return mResult.toString();
	}

	/**
	 * 从指定位置查询定量数据
	 */
	protected String getEncodedResult(SQLwithBindVariables sqlbv, int start, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		StringBuffer mResult = new StringBuffer(256);
		if (start <= 0) {
			start = 1;
		}
		if (nCount <= 0) {
			nCount = 1;
		}
		try {

			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			int m = start + nCount;
			int k = 0;
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sqlbv.originalSql());
			while (rs.next()) {
				if (!dt.increase()) {
					return "";
				}
				k++;
				if (k >= m) {
					break;
				}
				if ((k >= start) && (k < m)) {
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(ExeSQL.getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER).append(ExeSQL.getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}
			if (k >= start) {
				mResult.insert(0, "0|" + String.valueOf(k) + SysConst.RECORDSPLITER);
				mResult.delete(mResult.length() - 1, mResult.length());
			} else {
				mResult.append("100|未查询到相关数据！");
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start, int nCount): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return mResult.toString();
	}



	/**
	 * 输入：cSQL，在ExeSQL类初始化的时候建立连接。 输出：如果成功执行，返回True，否则返回False，并且在Error中设置错误的详细信息
	 */
	protected boolean execUpdateSQL(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL() || !sqlLog.checkSQLCondition()) {
			return false;
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
				con.setAutoCommit(false);
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			sqlbv.setParameters(pstmt);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at execUpdateSQL: " + sqlbv.originalSql());
			CError.buildErr(this, e.toString(), mErrors);
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.rollback();
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * 功能：可以执行输入的任意查询SQL语句。 输入：任意一个查询语句的字符串csql 返回：一个SSRS类的实例，内为查询结果
	 */
	protected SSRS execSQL(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		SSRS tSSRS = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return new SSRS();
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sqlbv.originalSql());
			while (rs.next()) {
				if (!dt.increase()) {
					return new SSRS();
				}
				for (int j = 1; j <= n; j++) {
					tSSRS.SetText(ExeSQL.getDataValue(rsmd, rs, j));
				}
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at execSQL(String sql): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			tSSRS = null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		if (tSSRS != null && tSSRS.getMaxRow() > 2000) {
			logger.debug("01 01 01 SSRS行数为:" + tSSRS.getMaxRow() + ";SQL为:" + sqlbv.originalSql());
		}
		return tSSRS;
	}

	protected SSRS execSQL(SQLwithBindVariables sqlbv, int start, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		SSRS tSSRS = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return new SSRS();
		}
		if (start <= 0) {
			start = 1;
		}
		if (nCount <= 0) {
			nCount = 1;
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);
			int m = start + nCount;
			int k = 0;
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sqlbv.originalSql());
			while (rs.next()) {
				if (!dt.increase()) {
					return new SSRS();
				}
				k++;
				if ((k >= start) && (k < m)) {
					for (int j = 1; j <= n; j++) {
						tSSRS.SetText(ExeSQL.getDataValue(rsmd, rs, j));
					}
				}
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at execSQL(String sql, int start, int nCount): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
			tSSRS = null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}
		return tSSRS;
	}

	
	/**
	 * 功能：可以执行输入的任意查询SQL语句。 输入：任意一个查询语句的字符串csql 返回：一个List类的实例，内为查询结果
	 */
	protected Map<String,String> getOneRowData(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RowSetDynaClass rsdc = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		Map<String,String> tResultMap = new LinkedHashMap<String,String>();
		SQLLog sqlLog = new SQLLog(sqlbv.originalSql());
		if (!sqlLog.checkSQL()) {
			return new LinkedHashMap<String,String>();
		}
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			rsdc = new RowSetDynaClass(rs);
			DynaProperty[] tProperties = rsdc.getDynaProperties();

			List resultList = rsdc.getRows();
			Iterator itr = resultList.iterator();
			while (itr.hasNext()) {
				DynaBean dBean = (DynaBean) itr.next();
				for (int i = 0; i < tProperties.length; i++) {
					try {
						String tProperty = tProperties[i].getName();
						Object objectValue = PropertyUtils.getProperty(dBean, tProperty);
						String tValue = "";
						if (objectValue == null) {
							tValue = "";
						} else {
							if (objectValue instanceof BigDecimal) {
								tValue = String.valueOf(objectValue);
								if (tValue.indexOf(".") > 0) {
									tValue = tValue.replaceAll("0+?$", "");// 去掉后面无用的零
									tValue = tValue.replaceAll("\\.$", "");// 如果以小数点结尾去掉小数点
								}
							} else {
								tValue = (String) objectValue;
							}
						}
						tResultMap.put(tProperty, tValue);
					} catch (IllegalAccessException e) {

						e.printStackTrace();
					} catch (InvocationTargetException e) {

						e.printStackTrace();
					} catch (NoSuchMethodException e) {

						e.printStackTrace();
					}
				}
				break;
			}
			rs.close();
			rs = null;
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			logger.debug("### Error ExeSQL at execSQL(String sql): " + sqlbv.originalSql());
			e.printStackTrace();
			CError.buildErr(this, e.toString(), mErrors);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {

				}
			}
		}
		return tResultMap;
	}
}
