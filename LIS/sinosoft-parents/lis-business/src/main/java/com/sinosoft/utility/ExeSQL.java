/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
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
import java.util.Vector;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;

/**
 * <p>
 * ClassName: ExeSQL
 * </p>
 * <p>
 * Description: DB层数据库操作类文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: LIS
 * @CreateDate：2002-07-11
 */
public class ExeSQL {
private static Logger logger = Logger.getLogger(ExeSQL.class);
	private Connection con;

	/**
	 * mflag = true: 传入Connection mflag = false: 不传入Connection
	 */
	private boolean mflag = false;

	private static FDate fDate = new FDate();
	public CErrors mErrors = new CErrors(); // 错误信息

	// @Constructor
	public ExeSQL(Connection tConnection) {
		con = tConnection;
		mflag = true;
	}

	public ExeSQL() {
	}

	/**
	 * 获取唯一的返回值
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	public String getOneValue(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String mValue = "";
		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{
			return "";
		}
		if(sql.matches("\\s*\\{\\s*[Cc][Aa][Ll][Ll]\\s+\\S+\\s*\\([\\s\\S]*\\)\\s*\\}\\s*")){
			return getOneValue(sql,null);
		}
		try {
			
			// add by yt，如果没有传入连接，则类创建
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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
			rs=null;
			pstmt.close();
			pstmt=null;
			// 如果连接是类创建的，则关闭连接
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			// @@错误处理
			logger.debug("### Error ExeSQL at OneValue: " + sql);
			CError.buildErr(this, e.toString(), mErrors);
			// 设置返回值
			mValue = "";
		}finally{
			try {
				if (rs != null) {
					rs.close();
					rs=null;
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
				//CError.buildErr(this, e.toString(), mErrors);
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt=null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
		}
		return StrTool.cTrim(mValue);
	}

	/**
	 * 获取SQL的查询结果记录数
	 * 
	 * @param sql
	 *            String
	 * @param pstmt
	 *            PreparedStatement
	 * @param rs
	 *            ResultSet
	 * @return int
	 */
	private int getResultCount(String sql, PreparedStatement pstmt, ResultSet rs) {
		int iCount = 0;
		// 此方法对不同数据库通用
		sql = "select count(1) from (" + sql + ") rsc";
		logger.debug("getResultCount : " + sql);

		try {
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			// rs.next();
			// 这样可以保证，没有查询到数据的时候，也返回正常
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
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			iCount = 0;
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			
		}
		return iCount;
	}

	/**
	 * 从指定位置查询全部数据
	 * 
	 * @param sql
	 *            String
	 * @param start
	 *            int
	 * @return String
	 */
	public String getEncodedResult(String sql, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang
		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{	
			return "";
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			// 查询字段的个数
			int n = rsmd.getColumnCount();
			// 查询记录的数量
			int m = start + (SysConst.MAXSCREENLINES * SysConst.MAXMEMORYPAGES);

			// 取得总记录数
			int k = 0;

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				dt.increase();

				k++;
				if ((k >= start) && (k < m)) {
					// only get record we needed
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER
									+ getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}

			if (k >= start) {
				if (k > 10000) {
					logger.debug("建议采用大批量数据查询模式！");
				}
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(k)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs=null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}
		return mResult.toString();
	}

	/**
	 * 从指定位置查询全部数据 数据大于200条但小于1000条
	 * 
	 * @param sql
	 *            String
	 * @param start
	 *            int
	 * @return String XinYQ added on 2006-09-30
	 */
	public String getEncodedResultEx(String sql, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang
		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{	
			return "";
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		try {
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			// 查询字段的个数
			int n = rsmd.getColumnCount();
			// 查询记录的数量
			// int m = start + (SysConst.MAXSCREENLINES *
			// SysConst.MAXMEMORYPAGES);
			// 突破默认200条数据限制,但不能过分放开,经和张荣讨论,暂定最多为1000条
			int m = start + 1000;

			// 取得总记录数
			int k = 0;

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				if(!dt.increase())
				{
					return "";
				}

				k++;
				if ((k >= start) && (k < m)) {
					// only get record we needed
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER
									+ getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}

			if (k >= start) {
				if (k > 10000) {
					logger.debug("建议采用大批量数据查询模式！");
				}
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(k)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
			
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
		}
		return mResult.toString();
	}

	/**
	 * 从指定位置查询全部数据，此方法为大数据量查询
	 * 
	 * @param sql
	 *            String
	 * @param start
	 *            int
	 * @return String
	 */
	public String getEncodedResultLarge(String sql, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{	
			return "";
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}

			// 取得总记录数 add by liuqiang
			int iCount = getResultCount(sql, pstmt, rs);
			// 如果记录数为0，表示没有查询的数据，这个时候，需要关闭连接
			if (iCount <= 0) {
				try {
					if (!mflag) {
						con.close();
					}
				} catch (SQLException ex) {
					// 可能连接会没有关闭
				}
				// 直接返回，查询结果为空
				return "100|未查询到相关数据!";
			}
			
			// 查询记录的数量
			int m = start + (SysConst.MAXSCREENLINES * SysConst.MAXMEMORYPAGES);

			// 根据数据库，查询指定范围数据集，采用此方法可以大幅度提高前台的分页查询效率
			StringBuffer tSBql = new StringBuffer();
			if (SysConst.DBTYPE.equals("ORACLE")) {
				tSBql.append("select * from (select rownum rnm,rs.* from (");
				tSBql.append(sql);
				tSBql.append(") rs where rownum < ");
				tSBql.append(m);
				tSBql.append(") rss where rnm >= ");
				tSBql.append(start);
			}else if (SysConst.DBTYPE.equals("MYSQL")) {
				tSBql.append("select '1',rs.* from (");
				tSBql.append(sql);
				tSBql.append(") rs limit ");
				tSBql.append(start-1);
				tSBql.append(",");
				tSBql.append(m-start);
			}else {
				tSBql
						.append("select * from (select rownumber() OVER () rnm ,rs.* from (");
				tSBql.append(sql);
				tSBql.append(") rs) rss WHERE rnm BETWEEN ");
				tSBql.append(start);
				tSBql.append(" and ");
				tSBql.append(m - 1);
			}

			logger.debug("ExecSQL : " + tSBql.toString());

			pstmt = con.prepareStatement(
					StrTool.GBKToUnicode(tSBql.toString()),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			// 查询字段的个数
			int n = rsmd.getColumnCount();

			int k = 0; // 用来判定是否有数据
			while (rs.next()) {
				k++;
				// 直接从位置2开始就ok了，呵呵，怎么没想到呢！！！
				for (int j = 2; j <= n; j++) {
					if (j == 2) {
						mResult.append(getDataValue(rsmd, rs, j));
					} else {
						mResult.append(SysConst.PACKAGESPILTER
								+ getDataValue(rsmd, rs, j));
					}
				}
				mResult.append(SysConst.RECORDSPLITER);
			}

			if (k > 0) {
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(iCount)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResultLarge(String sql, int start): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
			
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}
		return mResult.toString();
	}
	
	//***********************************************************************************//
	//绑定变量执行
	public SSRS execSQL(VData tVData) {
		PreparedStatement pstmt = null;
		String sql = (String)tVData.get(0);
		TransferData tParam = new TransferData();
		tParam = tVData.get(1)==null?(new TransferData()):(TransferData)tVData.get(1);
		
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		SSRS tSSRS = null;

		logger.debug("ExecSQL BV: " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{
			return new SSRS();
		}
		

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			//赋值
			setBindValues(pstmt, tParam);
			
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();

			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			// 取得总记录数
			while (rs.next()) {
				if(!dt.increase())
				{
					return new SSRS();
				}

				for (int j = 1; j <= n; j++) {
					tSSRS.SetText(getDataValue(rsmd, rs, j));
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
			logger.debug("### Error ExeSQL at execSQL(String sql): "
					+ sql);
			e.printStackTrace();

			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);

			tSSRS = null;
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
		}

		if (tSSRS != null && tSSRS.getMaxRow() > 2000) {
			logger.debug("01 01 01 SSRS行数为:" + tSSRS.getMaxRow()
					+ ";SQL为:" + sql);
		}

		return tSSRS;
	}
	
	public boolean execUpdateSQL(VData tVData) {
		PreparedStatement pstmt = null;
		String sql = (String)tVData.get(0);
		TransferData tParam = new TransferData();
		tParam = tVData.get(1)==null?(new TransferData()):(TransferData)tVData.get(1);
		logger.debug("execUpdateSQL BV : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL()||!sqlLog.checkSQLCondition())
		{
			return false;
		}
		

		try {
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			// 这里是否可以修改，还需要测试一下
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			//赋值
			setBindValues(pstmt, tParam);
			
			pstmt.executeUpdate();
			// int operCount = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			// @@错误处理
			logger.debug("### Error ExeSQL at execUpdateSQL: " + sql);
			CError.buildErr(this, e.toString(), mErrors);

			if (pstmt != null) {
					// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
					try {
						pstmt.close();
						pstmt = null;
					} catch (SQLException ex) {
						ex.printStackTrace();
						CError.buildErr(this, ex.toString(), mErrors);
					}
				}
				if (!mflag) {
					
					try {
						con.rollback();
						con.close();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
						CError.buildErr(this, e1.toString(), mErrors);
					}
				}
			

			return false;
		}

		return true;
	}
	
	private String getOneValue(String sql, TransferData tParam) {
		CallableStatement cstmt = null;
		String mValue = "";
		try {
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			cstmt = con.prepareCall(StrTool.GBKToUnicode(sql));
			if(tParam != null)
				setBindValues(cstmt, tParam);
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
			logger.debug("### Error ExeSQL at OneValue: " + sql);
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
	
	public String getOneValue(VData tVData ) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String mValue = "";
		String sql = (String)tVData.get(0);
		TransferData tParam = new TransferData();
		tParam = tVData.get(1)==null?(new TransferData()):(TransferData)tVData.get(1);
		
		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{
			return "";
		}
		if(sql.matches("\\s*\\{\\s*[Cc][Aa][Ll][Ll]\\s+\\S+\\s*\\([\\s\\S]*\\)\\s*\\}\\s*")){
			return getOneValue(sql,tParam);
		}
		try {
			
			// add by yt，如果没有传入连接，则类创建
			if (!mflag) {
				con = DBConnPool.getConnection();
			}

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			//赋值
			setBindValues(pstmt, tParam);
			
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
			// 如果连接是类创建的，则关闭连接
			if (!mflag) {
				con.close();
			}
		} catch (SQLException e) {
			// @@错误处理
			logger.debug("### Error ExeSQL at OneValue: " + sql);
			CError.buildErr(this, e.toString(), mErrors);
			// 设置返回值
			mValue = "";
		} finally {

				if (rs != null) {
					try {
						rs.close();
						rs = null;
					} catch (Exception e) {
						
						e.printStackTrace();
						//CError.buildErr(this, e.toString(), mErrors);
					}
				}
				if (pstmt != null) {
					// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
					try {
						pstmt.close();
						pstmt = null;
					} catch (SQLException ex) {
						ex.printStackTrace();
						//CError.buildErr(this, ex.toString(), mErrors);
					}
				}
				if (!mflag) {
					try {
						con.close();
					} catch (SQLException e) {
						
						//e.printStackTrace();
						//CError.buildErr(this, e.toString(), mErrors);
					}
				}
			
		}
		return StrTool.cTrim(mValue);
	}

	private void setBindValues(PreparedStatement pstmt, TransferData tParam)
			throws SQLException {
		Vector tNames = tParam.getValueNames();
		for(int i=0;i<tNames.size();i++)
		{
			String tTypeAndValue = (String)tParam.getValueByName((String)tNames.get(i));
			//String[] tParams = tTypeAndValue.split(":");
			String tType = tTypeAndValue.substring(0,tTypeAndValue.indexOf(":"));
			String tValue = tTypeAndValue.substring(tTypeAndValue.indexOf(":")+1);;
//			if(tParams.length==2)
//			{
//				tValue= tParams[1];
//			}
			logger.debug("tType:"+tType+":tValue:"+tValue);
			if(tType.toLowerCase().equals("string"))
			{
				if(tValue==null||tValue.equals("")||tValue.equals("null"))
				{
					pstmt.setNull(i+1, 12);
				}
				else
				{
					pstmt.setString(i+1, tValue);
				}
			}
			else if(tType.toLowerCase().equals("double"))
			{
				pstmt.setDouble(i+1, Double.parseDouble(tValue));
			}
			else if(tType.toLowerCase().equals("int"))
			{
				pstmt.setInt(i+1, Integer.parseInt(tValue));
			}
		}
	}
	
	public String getEncodedResult(VData tVData, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang
		String sql = (String)tVData.get(0);
		TransferData tParam = new TransferData();
		tParam = tVData.get(1)==null?(new TransferData()):(TransferData)tVData.get(1);
		logger.debug("ExecSQLBV : " + sql);
		logger.debug(PubFun.buildLog("ExecSQLBV : " + sql));
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{	
			return "";
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			setBindValues(pstmt, tParam);
				
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			// 查询字段的个数
			int n = rsmd.getColumnCount();
			// 查询记录的数量
			int m = start + (SysConst.MAXSCREENLINES * SysConst.MAXMEMORYPAGES);

			// 取得总记录数
			int k = 0;

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				if(!dt.increase())
				{
					return "";
				}

				k++;
				if ((k >= start) && (k < m)) {
					// only get record we needed
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER
									+ getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}

			if (k >= start) {
				if (k > 10000) {
					logger.debug("建议采用大批量数据查询模式！");
				}
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(k)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
		}
		return mResult.toString();
	}
	
	public String getEncodedResultEx(VData tVData, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang
		String sql = (String)tVData.get(0);
		TransferData tParam = new TransferData();
		tParam = tVData.get(1)==null?(new TransferData()):(TransferData)tVData.get(1);
		logger.debug("ExecSQLBV : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{	
			return "";
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			setBindValues(pstmt, tParam);
			
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			// 查询字段的个数
			int n = rsmd.getColumnCount();
			// 查询记录的数量
			// int m = start + (SysConst.MAXSCREENLINES *
			// SysConst.MAXMEMORYPAGES);
			// 突破默认200条数据限制,但不能过分放开,经和张荣讨论,暂定最多为1000条
			int m = start + 1000;

			// 取得总记录数
			int k = 0;

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				if(!dt.increase())
				{
					return "";
				}

				k++;
				if ((k >= start) && (k < m)) {
					// only get record we needed
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER
									+ getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}

			if (k >= start) {
				if (k > 10000) {
					logger.debug("建议采用大批量数据查询模式！");
				}
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(k)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
			
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}
		return mResult.toString();
	}
	public String getEncodedResultLarge(VData tVData, int start) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang
		String sql = (String)tVData.get(0);
		TransferData tParam = new TransferData();
		tParam = tVData.get(1)==null?(new TransferData()):(TransferData)tVData.get(1);
		logger.debug("ExecSQLBV : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{	
			return "";
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		// add by yt
		if (!mflag) {
			con = DBConnPool.getConnection();
		}

		// 取得总记录数 add by liuqiang
		int iCount = getResultCount(sql, pstmt, rs,tParam);
		// 如果记录数为0，表示没有查询的数据，这个时候，需要关闭连接
		if (iCount <= 0) {
			try {
				if (!mflag) {
					con.close();
				}
			} catch (SQLException ex) {
				// 可能连接会没有关闭
			}
			// 直接返回，查询结果为空
			return "100|未查询到相关数据!";
		}

		try {
			// 查询记录的数量
			int m = start + (SysConst.MAXSCREENLINES * SysConst.MAXMEMORYPAGES);

			// 根据数据库，查询指定范围数据集，采用此方法可以大幅度提高前台的分页查询效率
			StringBuffer tSBql = new StringBuffer();
			if (SysConst.DBTYPE.equals("ORACLE")) {
				tSBql.append("select * from (select rownum rnm,rs.* from (");
				tSBql.append(sql);
				tSBql.append(") rs where rownum < ");
				tSBql.append(m);
				tSBql.append(") rss where rnm >= ");
				tSBql.append(start);
			}else if (SysConst.DBTYPE.equals("MYSQL")) {
				tSBql.append("select '1',rs.* from (");
				tSBql.append(sql);
				tSBql.append(") rs limit ");
				tSBql.append(start-1);
				tSBql.append(",");
				tSBql.append(m-start);
			}else {
				tSBql
						.append("select * from (select rownumber() OVER () rnm ,rs.* from (");
				tSBql.append(sql);
				tSBql.append(") rs) rss WHERE rnm BETWEEN ");
				tSBql.append(start);
				tSBql.append(" and ");
				tSBql.append(m - 1);
			}

			logger.debug("ExecSQL : " + tSBql.toString());

			pstmt = con.prepareStatement(
					StrTool.GBKToUnicode(tSBql.toString()),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			setBindValues(pstmt, tParam);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			// 查询字段的个数
			int n = rsmd.getColumnCount();

			int k = 0; // 用来判定是否有数据
			while (rs.next()) {
				k++;
				// 直接从位置2开始就ok了，呵呵，怎么没想到呢！！！
				for (int j = 2; j <= n; j++) {
					if (j == 2) {
						mResult.append(getDataValue(rsmd, rs, j));
					} else {
						mResult.append(SysConst.PACKAGESPILTER
								+ getDataValue(rsmd, rs, j));
					}
				}
				mResult.append(SysConst.RECORDSPLITER);
			}

			if (k > 0) {
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(iCount)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResultLarge(String sql, int start): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
			
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}
		return mResult.toString();
	}
	
	private int getResultCount(String sql, PreparedStatement pstmt, ResultSet rs,TransferData tParam) {
		int iCount = 0;
		// 此方法对不同数据库通用
		sql = "select count(1) from (" + sql + ") rsc";
		logger.debug("getResultCount : " + sql);

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			setBindValues(pstmt, tParam);
			
			rs = pstmt.executeQuery();
			// rs.next();
			// 这样可以保证，没有查询到数据的时候，也返回正常
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
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			iCount = 0;
			
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e1) {
					
					e1.printStackTrace();
					//CError.buildErr(this, e1.toString(), mErrors);
				}
			}
			

				if (pstmt != null) {
					// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
					try {
						pstmt.close();
					} catch (SQLException ex) {
						ex.printStackTrace();
						//CError.buildErr(this, ex.toString(), mErrors);
					}
				}
				if (!mflag) {
					try {
						con.close();
					} catch (SQLException e1) {
						
						//e1.printStackTrace();
						//CError.buildErr(this, e1.toString(), mErrors);
					}
				}
			
		}
		return iCount;
	}
	//***********************************************************************************//

	/**
	 * 查询数据
	 * 
	 * @param sql
	 *            String
	 * @return String
	 */
	public String getEncodedResult(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang

		logger.debug("ExecSQL : " + sql);
		
		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			// 重新设置缓冲区，主要采用此中方式的查询数据量在几千左右
			pstmt.setFetchSize(500);
			rs = pstmt.executeQuery();
			rs.setFetchSize(500);
			rsmd = rs.getMetaData();

			int n = rsmd.getColumnCount();

			// 取得总记录数
			int k = 0;

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				if(!dt.increase())
				{
					return "";
				}

				k++;
				for (int j = 1; j <= n; j++) {
					if (j == 1) {
						mResult.append(getDataValue(rsmd, rs, j));
					} else {
						mResult.append(SysConst.PACKAGESPILTER);
						mResult.append(getDataValue(rsmd, rs, j));
					}
				}
				mResult.append(SysConst.RECORDSPLITER);
			}
			if (k > 0) {
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(k)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResult(String sql): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
			
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}
		return mResult.toString();
	}

	/**
	 * 从指定位置查询定量数据
	 * 
	 * @param sql
	 *            String
	 * @param start
	 *            int
	 * @param nCount
	 *            int
	 * @return String
	 */
	public String getEncodedResult(String sql, int start, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		StringBuffer mResult = new StringBuffer(256); // modified by liuqiang

		logger.debug("ExecSQL : " + sql);
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}
		if (nCount <= 0) {
			nCount = 1;
		}
		// add by yt
		
		// 取得总记录数 add by liuqiang
		// int iCount = getResultCount(sql,pstmt,rs);
		// if (iCount <= 0) return "";
		try {
			
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int n = rsmd.getColumnCount();
			int m = start + nCount;
			// 取得总记录数
			int k = 0;

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				if(!dt.increase())
				{
					return "";
				}

				k++;
				// 如果超过要取的记录数，直接退出
				if (k >= m) {
					break;
				}
				if ((k >= start) && (k < m)) {
					// only get record we needed
					for (int j = 1; j <= n; j++) {
						if (j == 1) {
							mResult.append(getDataValue(rsmd, rs, j));
						} else {
							mResult.append(SysConst.PACKAGESPILTER).append(
									getDataValue(rsmd, rs, j));
						}
					}
					mResult.append(SysConst.RECORDSPLITER);
				}
			}

			if (k >= start) {
				// "0|"为查询成功标记，与CODEQUERY统一，MINIM修改
				mResult.insert(0, "0|" + String.valueOf(k)
						+ SysConst.RECORDSPLITER);
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
			logger.debug("### Error ExeSQL at getEncodedResult(String sql, int start, int nCount): "
							+ sql);
			e.printStackTrace();
			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);
			mResult.setLength(0);
		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}
		return mResult.toString();
	}

	/**
	 * 把ResultSet中取出的数据转换为相应的数据值字符串
	 * 输出：如果成功执行，返回True，否则返回False，并且在Error中设置错误的详细信息
	 * 
	 * @param rsmd
	 *            ResultSetMetaData
	 * @param rs
	 *            ResultSet
	 * @param i
	 *            int
	 * @return String
	 */
	public static String getDataValue(ResultSetMetaData rsmd, ResultSet rs, int i) {
		String strValue = "";

		try {
			int dataType = rsmd.getColumnType(i);
			int dataScale = rsmd.getScale(i);
			int dataPrecision = rsmd.getPrecision(i);
			// 数据类型为字符
			if ((dataType == Types.CHAR) || (dataType == Types.VARCHAR) || (dataType == Types.LONGVARCHAR)) {
				// 由于存入数据库的数据是GBK模式，因此没有必要做一次unicodeToGBK
				// strValue = StrTool.unicodeToGBK(rs.getString(i));
				strValue = rs.getString(i);
			}
			// 数据类型为日期、时间
			else if ((dataType == Types.TIMESTAMP) || (dataType == Types.DATE)) {
				strValue = fDate.getString(rs.getDate(i));
			}
			// 数据类型为浮点
			else if ((dataType == Types.DECIMAL) || (dataType == Types.FLOAT) ||(dataType == Types.REAL)|| (dataType == Types.DOUBLE)) {
				strValue = String.valueOf(rs.getBigDecimal(i));
				if (strValue.indexOf(".") > 0) {
					strValue = strValue.replaceAll("0+?$", "");// 去掉后面无用的零
					strValue = strValue.replaceAll("\\.$", "");// 如果以小数点结尾去掉小数点
				}
			}
			// 数据类型为整型
			else if ((dataType == Types.INTEGER)
					|| (dataType == Types.SMALLINT)|| (dataType == Types.BIGINT)) {
				strValue = String.valueOf(rs.getInt(i));
				strValue = PubFun.getInt(strValue);
			}
			// 数据类型为浮点
			else if (dataType == Types.NUMERIC) {
				if (dataScale == 0) {
					if (dataPrecision == 0) {
						// strValue = String.valueOf(rs.getDouble(i));
						// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
						strValue = String.valueOf(rs.getBigDecimal(i));
					} else {
						strValue = String.valueOf(rs.getLong(i));
					}
				} else {
					// strValue = String.valueOf(rs.getDouble(i));
					// 采用下面的方法使得数据输出的时候不会产生科学计数法样式
					strValue = String.valueOf(rs.getBigDecimal(i));
				}
				strValue = PubFun.getInt(strValue);
				// update by YaoYi in 2011-9-16 for display.
				if (null == strValue || "null".equals(strValue)) {
					strValue="";
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return StrTool.cTrim(strValue);
	}

	/**
	 * 输入：cSQL，在ExeSQL类初始化的时候建立连接。 输出：如果成功执行，返回True，否则返回False，并且在Error中设置错误的详细信息
	 * 
	 * @param sql
	 *            String
	 * @return boolean
	 */
	public boolean execUpdateSQL(String sql) {
		PreparedStatement pstmt = null;
		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL()||!sqlLog.checkSQLCondition())
		{
			return false;
		}
		

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
				con.setAutoCommit(false);
			}
			// 这里是否可以修改，还需要测试一下
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

			pstmt.executeUpdate();
			// int operCount = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			if (!mflag) {
				con.commit();
				con.close();
			}
		} catch (SQLException e) {
			// @@错误处理
			logger.debug("### Error ExeSQL at execUpdateSQL: " + sql);
			CError.buildErr(this, e.toString(), mErrors);

			
				if (pstmt != null) {
					// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
					try {
						pstmt.close();
						pstmt = null;
					} catch (SQLException ex) {
						ex.printStackTrace();
						//CError.buildErr(this, ex.toString(), mErrors);
					}
				}
				if (!mflag) {
					try {
						con.rollback();
						con.close();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
						//CError.buildErr(this, e1.toString(), mErrors);
					}
				}
			

			return false;
		}

		return true;
	}

	/**
	 * 功能：可以执行输入的任意查询SQL语句。 输入：任意一个查询语句的字符串csql 返回：一个SSRS类的实例，内为查询结果
	 * 
	 * @param sql
	 *            String
	 * @return SSRS
	 */
	public SSRS execSQL(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		SSRS tSSRS = null;

		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{
			return new SSRS();
		}
		

		try {
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();

			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			// 取得总记录数
			while (rs.next()) {
				if(!dt.increase())
				{
					return new SSRS();
				}

				for (int j = 1; j <= n; j++) {
					tSSRS.SetText(getDataValue(rsmd, rs, j));
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
			logger.debug("### Error ExeSQL at execSQL(String sql): "
					+ sql);
			e.printStackTrace();

			// @@错误处理
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
					
					//e.printStackTrace();
				}
			}
		}

		if (tSSRS != null && tSSRS.getMaxRow() > 2000) {
			logger.debug("01 01 01 SSRS行数为:" + tSSRS.getMaxRow()
					+ ";SQL为:" + sql);
		}

		return tSSRS;
	}

	public SSRS execSQL(String sql, int start, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		SSRS tSSRS = null;

		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{
			return new SSRS();
		}
		// add by Fanym
		if (start <= 0) {
			start = 1;
		}

		if (nCount <= 0) {
			nCount = 1;
		}

		

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();

			int n = rsmd.getColumnCount();
			tSSRS = new SSRS(n);

			int m = start + nCount;
			int k = 0;

			// 取得总记录数

			// Kevin 2006-08-15
			DBThreshold dt = new DBThreshold();
			dt.setSQL(sql);

			while (rs.next()) {
				if(!dt.increase())
				{
					return new SSRS();
				}

				k++;

				// 只取特定范围内的记录行
				if ((k >= start) && (k < m)) {
					for (int j = 1; j <= n; j++) {
						tSSRS.SetText(getDataValue(rsmd, rs, j));
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
			logger.debug("### Error ExeSQL at execSQL(String sql, int start, int nCount): "
							+ sql);
			e.printStackTrace();

			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);

			tSSRS = null;

		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
				try {
					pstmt.close();
					pstmt = null;
				} catch (SQLException ex) {
					ex.printStackTrace();
					//CError.buildErr(this, ex.toString(), mErrors);
				}
			}
			if (!mflag) {
				try {
					con.close();
				} catch (SQLException e) {
					
					//e.printStackTrace();
					//CError.buildErr(this, e.toString(), mErrors);
				}
			}
		}

		return tSSRS;
	}
	
	public boolean execProcedure(String cDblinkName) {
        CallableStatement callStmt = null;
        // prepare the CALL statement for OUT_PARAM
        String procName = "create_dblink_synonym";
        String sql = "CALL " + procName + "(?)";

        logger.debug("ExecProcedure: " + sql);

        //add by yt
       
        try {
        	
        	 if (!mflag) {
                 con = DBConnPool.getConnection();
             }

        	 
            callStmt = con.prepareCall(sql);
            callStmt.setString(1, cDblinkName);
            callStmt.execute();
            //int operCount = pstmt.executeUpdate();
            callStmt.close();

            if (!mflag) {
                con.commit();
                con.close();
            }
        } catch (SQLException e) {
            // @@错误处理
            logger.debug("### Error ExeSQL at execUpdateSQL: " + sql);
            CError.buildErr(this, e.toString(), mErrors);
                if (callStmt != null) {
                    //由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
                    try {
                        callStmt.close();
                        callStmt = null;
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
	 * 功能：可以执行输入的任意查询SQL语句。 输入：任意一个查询语句的字符串csql 返回：一个List类的实例，内为查询结果
	 * 
	 * @param sql
	 *            String
	 * @return List
	 */
	public Map getOneRowData(String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RowSetDynaClass rsdc = null;
		Map tResultMap = new LinkedHashMap();
		logger.debug("ExecSQL : " + sql);
		SQLLog sqlLog = new SQLLog(sql);
		if(!sqlLog.checkSQL())
		{
			return new LinkedHashMap();
		}
	

		try {
			
			// add by yt
			if (!mflag) {
				con = DBConnPool.getConnection();
			}
			
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rs = pstmt.executeQuery();
			rsdc = new RowSetDynaClass(rs);
			DynaProperty[] tProperties = rsdc.getDynaProperties();
			
			List resultList = rsdc.getRows();
			Iterator itr = resultList.iterator();
			while (itr.hasNext()) {
				DynaBean dBean = (DynaBean) itr.next();
				for(int i=0;i<tProperties.length;i++)
				{
					try {
						String tProperty = tProperties[i].getName();
						Object objectValue = PropertyUtils.getProperty(dBean, tProperty);
						String tValue = "";
						if(objectValue==null){
							 tValue = "";
						}else{
							if(objectValue instanceof BigDecimal){
								tValue = String.valueOf(objectValue);
								if (tValue.indexOf(".") > 0) {
									tValue = tValue.replaceAll("0+?$", "");// 去掉后面无用的零
									tValue = tValue.replaceAll("\\.$", "");// 如果以小数点结尾去掉小数点
								}
							}else{
								tValue = (String)objectValue;
							}
						}
					//	String tValue = PropertyUtils.getProperty(dBean, tProperty)==null?"":(String)PropertyUtils.getProperty(dBean, tProperty);
						tResultMap.put(tProperty, tValue);
						//System.out.println(tProperty+":"+tValue);
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
			logger.debug("### Error ExeSQL at execSQL(String sql): "
					+ sql);
			e.printStackTrace();

			// @@错误处理
			CError.buildErr(this, e.toString(), mErrors);

		}finally{
			if (rs != null) {
				try {
					rs.close();
					rs = null;
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				// 由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
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
					
					//e.printStackTrace();
				}
			}
		}

		return tResultMap;
	}
	
	public static void main(String[] args) {
	}
	
	ExeSQLwithBindVariables exesqlbv = null;

	private ExeSQLwithBindVariables getBvInstance() {
		if (exesqlbv == null)
			if (mflag == true)
				exesqlbv = new ExeSQLwithBindVariables(con);
			else
				exesqlbv = new ExeSQLwithBindVariables();
		return exesqlbv;
	}

	private void dealBvErrors() {
		if (exesqlbv.mErrors.needDealError()) {
			mErrors.copyAllErrors(getBvInstance().mErrors);
			getBvInstance().mErrors = new CErrors();
		}
	}

	public SSRS execSQL(SQLwithBindVariables sqlbv) {
		SSRS ssrs = getBvInstance().execSQL(sqlbv);
		dealBvErrors();
		return ssrs;
	}

	public SSRS execSQL(SQLwithBindVariables sqlbv, int start, int nCount) {
		SSRS ssrs = getBvInstance().execSQL(sqlbv, start, nCount);
		dealBvErrors();
		return ssrs;
	}

	public boolean execUpdateSQL(SQLwithBindVariables sqlbv) {
		boolean result = getBvInstance().execUpdateSQL(sqlbv);
		dealBvErrors();
		return result;
	}

	public String getEncodedResult(SQLwithBindVariables sqlbv) {
		String result = getBvInstance().getEncodedResult(sqlbv);
		dealBvErrors();
		return result;
	}

	public String getEncodedResult(SQLwithBindVariables sqlbv, int start) {
		String result = getBvInstance().getEncodedResult(sqlbv, start);
		dealBvErrors();
		return result;
	}

	public String getEncodedResult(SQLwithBindVariables sqlbv, int start, int nCount) {
		String result = getBvInstance().getEncodedResult(sqlbv, start, nCount);
		dealBvErrors();
		return result;
	}

	public String getEncodedResultEx(SQLwithBindVariables sqlbv, int start) {
		String result = getBvInstance().getEncodedResultEx(sqlbv, start);
		dealBvErrors();
		return result;
	}

	public String getEncodedResultLarge(SQLwithBindVariables sqlbv, int start) {
		String result = getBvInstance().getEncodedResultLarge(sqlbv, start);
		dealBvErrors();
		return result;
	}

	public Map<String, String> getOneRowData(SQLwithBindVariables sqlbv) {
		Map<String, String> result = getBvInstance().getOneRowData(sqlbv);
		dealBvErrors();
		return result;
	}

	public String getOneValue(SQLwithBindVariables sqlbv) {
		String result = getBvInstance().getOneValue(sqlbv);
		dealBvErrors();
		return result;
	}
}
