/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sinosoft.lis.schema.LPrtRelatedSchema;
import com.sinosoft.lis.vschema.LPrtRelatedSet;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LPrtRelatedDB
 * </p>
 * <p>
 * Description: DB层数据库操作类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-01-24
 */
public class LPrtRelatedDB extends LPrtRelatedSchema
{
private static Logger logger = Logger.getLogger(LPrtRelatedDB.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 **/
	private boolean mflag = false;

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;

	private Statement mStatement = null;

	// @Constructor
	public LPrtRelatedDB(Connection cConnection)
	{
		con = cConnection;
		db = new DBOper(con, "LPrtRelated");
		mflag = true;
	}

	public LPrtRelatedDB()
	{
		con = null;
		db = new DBOper("LPrtRelated");
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPrtRelatedSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
			return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPrtRelatedSchema tSchema = this.getSchema();
		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			return -1;
		}
		return tCount;
	}

	/**
	 * 删除操作 删除条件：主键
	 * @return boolean
	 **/
	public boolean delete()
	{
		PreparedStatement pstmt = null;
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			pstmt = con.prepareStatement("DELETE FROM LPrtRelated WHERE  PrintID = ? AND TempleteID = ? AND Language = ? AND OutputType = ?");
			if (this.getPrintID() == null || this.getPrintID().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getPrintID());
			}
			if (this.getTempleteID() == null || this.getTempleteID().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getTempleteID());
			}
			if (this.getLanguage() == null || this.getLanguage().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getLanguage());
			}
			if (this.getOutputType() == null || this.getOutputType().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getOutputType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			// 输出出错Sql语句
			SQLString sqlObj = new SQLString("LPrtRelated");
			sqlObj.setSQL(4, this.getSchema());
			logger.debug("出错Sql为：" + sqlObj.getSQL());
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return false;
		}
		finally
		{
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 更新操作 更新条件：主键
	 * @return boolean
	 */
	public boolean update()
	{
		PreparedStatement pstmt = null;
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			pstmt = con.prepareStatement("UPDATE LPrtRelated SET  PrintID = ? , TempleteID = ? , Language = ? , OutputType = ? WHERE  PrintID = ? AND TempleteID = ? AND Language = ? AND OutputType = ?");
			if (this.getPrintID() == null || this.getPrintID().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getPrintID());
			}
			if (this.getTempleteID() == null || this.getTempleteID().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getTempleteID());
			}
			if (this.getLanguage() == null || this.getLanguage().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getLanguage());
			}
			if (this.getOutputType() == null || this.getOutputType().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getOutputType());
			}
			if (this.getPrintID() == null || this.getPrintID().equals("null"))
			{
				pstmt.setNull(5, 12);
			}
			else
			{
				pstmt.setString(5, this.getPrintID());
			}
			if (this.getTempleteID() == null || this.getTempleteID().equals("null"))
			{
				pstmt.setNull(6, 12);
			}
			else
			{
				pstmt.setString(6, this.getTempleteID());
			}
			if (this.getLanguage() == null || this.getLanguage().equals("null"))
			{
				pstmt.setNull(7, 12);
			}
			else
			{
				pstmt.setString(7, this.getLanguage());
			}
			if (this.getOutputType() == null || this.getOutputType().equals("null"))
			{
				pstmt.setNull(8, 12);
			}
			else
			{
				pstmt.setString(8, this.getOutputType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			// 输出出错Sql语句
			SQLString sqlObj = new SQLString("LPrtRelated");
			sqlObj.setSQL(2, this.getSchema());
			logger.debug("出错Sql为：" + sqlObj.getSQL());
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return false;
		}
		finally
		{
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 新增操作
	 * @return boolean
	 */
	public boolean insert()
	{
		PreparedStatement pstmt = null;
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPrtRelated VALUES( ? , ? , ? , ?)");
			if (this.getPrintID() == null || this.getPrintID().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getPrintID());
			}
			if (this.getTempleteID() == null || this.getTempleteID().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getTempleteID());
			}
			if (this.getLanguage() == null || this.getLanguage().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getLanguage());
			}
			if (this.getOutputType() == null || this.getOutputType().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getOutputType());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			// 输出出错Sql语句
			SQLString sqlObj = new SQLString("LPrtRelated");
			sqlObj.setSQL(1, this.getSchema());
			logger.debug("出错Sql为：" + sqlObj.getSQL());
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return false;
		}
		finally
		{
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 查询操作 查询条件：主键
	 * @return boolean
	 */
	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			pstmt = con.prepareStatement("SELECT * FROM LPrtRelated WHERE  PrintID = ? AND TempleteID = ? AND Language = ? AND OutputType = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (this.getPrintID() == null || this.getPrintID().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getPrintID());
			}
			if (this.getTempleteID() == null || this.getTempleteID().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getTempleteID());
			}
			if (this.getLanguage() == null || this.getLanguage().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getLanguage());
			}
			if (this.getOutputType() == null || this.getOutputType().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getOutputType());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtRelatedDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors.addOneError(tError);
					try
					{
						rs.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					try
					{
						pstmt.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					return false;
				}
				break;
			}
			try
			{
				rs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (i == 0)
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			// 输出出错Sql语句
			SQLString sqlObj = new SQLString("LPrtRelated");
			sqlObj.setSQL(6, this.getSchema());
			logger.debug("出错Sql为：" + sqlObj.getSQL());
			try
			{
				rs.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				pstmt.close();
			}
			catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
			return false;
		}
		finally
		{
			// 断开数据库连接
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 查询操作 查询条件：传入的schema中有值的字段
	 * @return boolean
	 */
	public LPrtRelatedSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPrtRelatedSet aLPrtRelatedSet = new LPrtRelatedSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPrtRelated");
			LPrtRelatedSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LPrtRelatedSchema s1 = new LPrtRelatedSchema();
				s1.setSchema(rs,i);
				aLPrtRelatedSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLPrtRelatedSet;

	}

	public LPrtRelatedSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPrtRelatedSet aLPrtRelatedSet = new LPrtRelatedSet();
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LPrtRelatedSchema s1 = new LPrtRelatedSchema();
				if (!s1.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtRelatedDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLPrtRelatedSet.add(s1);
			}
			try
			{
				rs.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				stmt.close();
			}
			catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		catch (Exception e)
		{
			logger.debug("##### Error Sql in LPrtRelatedDB at query(): " + sql);
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try
			{
				rs.close();
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
			try
			{
				stmt.close();
			}
			catch (Exception ex3)
			{
				ex3.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
		}
		finally
		{
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return aLPrtRelatedSet;
	}

	public LPrtRelatedSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPrtRelatedSet aLPrtRelatedSet = new LPrtRelatedSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPrtRelated");
			LPrtRelatedSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LPrtRelatedSchema s1 = new LPrtRelatedSchema();
				s1.setSchema(rs,i);
				aLPrtRelatedSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLPrtRelatedSet;

	}

	public LPrtRelatedSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPrtRelatedSet aLPrtRelatedSet = new LPrtRelatedSet();
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				if (i < nStart)
				{
					continue;
				}
				if (i >= nStart + nCount)
				{
					break;
				}
				LPrtRelatedSchema s1 = new LPrtRelatedSchema();
				if (!s1.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtRelatedDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLPrtRelatedSet.add(s1);
			}
			try
			{
				rs.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			try
			{
				stmt.close();
			}
			catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		catch (Exception e)
		{
			logger.debug("##### Error Sql in LPrtRelatedDB at query(): " + sql);
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try
			{
				rs.close();
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
			try
			{
				stmt.close();
			}
			catch (Exception ex3)
			{
				ex3.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
		}
		finally
		{
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return aLPrtRelatedSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		SQLString sqlObj = new SQLString("LPrtRelated");
		sqlObj.setSQL(2, this.getSchema());
		String sql = "update LPrtRelated " + sqlObj.getUpdPart() + " where " + strWherePart;
		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			int operCount = stmt.executeUpdate(sql);
			if (operCount == 0)
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPrtRelatedDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors.addOneError(tError);
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch (Exception et)
					{
						et.printStackTrace();
					}
				}
				return false;
			}
		}
		catch (Exception e)
		{
			logger.debug("##### Error Sql in LPrtRelatedDB at update(String strWherePart): " + sql);
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try
			{
				stmt.close();
			}
			catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
			return false;
		}
		finally
		{
			// 断开数据库连接
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 准备数据查询条件
	 * @param strSQL String
	 * @return boolean
	 */
	public boolean prepareData(String strSQL)
	{
		if (mResultSet != null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "prepareData";
			tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "prepareData";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try
			{
				mResultSet.close();
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
			try
			{
				mStatement.close();
			}
			catch (Exception ex3)
			{
				ex3.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
			return false;
		}
		finally
		{
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 获取数据集
	 * @return boolean
	 */
	public boolean hasMoreData()
	{
		boolean flag = true;
		if (null == mResultSet)
		{
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "hasMoreData";
			tError.errorMessage = "数据集为空，请先准备数据集！";
			this.mErrors.addOneError(tError);
			return false;
		}
		try
		{
			flag = mResultSet.next();
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "hasMoreData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try
			{
				mResultSet.close();
				mResultSet = null;
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
			try
			{
				mStatement.close();
				mStatement = null;
			}
			catch (Exception ex3)
			{
				ex3.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
			return false;
		}
		return flag;
	}

	/**
	 * 获取定量数据
	 * @return LPrtRelatedSet
	 */
	public LPrtRelatedSet getData()
	{
		int tCount = 0;
		LPrtRelatedSet tLPrtRelatedSet = new LPrtRelatedSet();
		LPrtRelatedSchema tLPrtRelatedSchema = null;
		if (null == mResultSet)
		{
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "getData";
			tError.errorMessage = "数据集为空，请先准备数据集！";
			this.mErrors.addOneError(tError);
			return null;
		}
		try
		{
			tCount = 1;
			tLPrtRelatedSchema = new LPrtRelatedSchema();
			tLPrtRelatedSchema.setSchema(mResultSet, 1);
			tLPrtRelatedSet.add(tLPrtRelatedSchema);
			// 注意mResultSet.next()的作用
			while (tCount++ < SysConst.FETCHCOUNT)
			{
				if (mResultSet.next())
				{
					tLPrtRelatedSchema = new LPrtRelatedSchema();
					tLPrtRelatedSchema.setSchema(mResultSet, 1);
					tLPrtRelatedSet.add(tLPrtRelatedSchema);
				}
			}
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "getData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try
			{
				mResultSet.close();
				mResultSet = null;
			}
			catch (Exception ex2)
			{
				ex2.printStackTrace();
			}
			try
			{
				mStatement.close();
				mStatement = null;
			}
			catch (Exception ex3)
			{
				ex3.printStackTrace();
			}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{
					et.printStackTrace();
				}
			}
			return null;
		}
		return tLPrtRelatedSet;
	}

	/**
	 * 关闭数据集
	 * @return boolean
	 */
	public boolean closeData()
	{
		boolean flag = true;
		try
		{
			if (null == mResultSet)
			{
				CError tError = new CError();
				tError.moduleName = "LPrtRelatedDB";
				tError.functionName = "closeData";
				tError.errorMessage = "数据集已经关闭了！";
				this.mErrors.addOneError(tError);
				flag = false;
			}
			else
			{
				mResultSet.close();
				mResultSet = null;
			}
		}
		catch (Exception ex2)
		{
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "closeData";
			tError.errorMessage = ex2.toString();
			this.mErrors.addOneError(tError);
			flag = false;
		}
		try
		{
			if (null == mStatement)
			{
				CError tError = new CError();
				tError.moduleName = "LPrtRelatedDB";
				tError.functionName = "closeData";
				tError.errorMessage = "语句已经关闭了！";
				this.mErrors.addOneError(tError);
				flag = false;
			}
			else
			{
				mStatement.close();
				mStatement = null;
			}
		}
		catch (Exception ex3)
		{
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "closeData";
			tError.errorMessage = ex3.toString();
			this.mErrors.addOneError(tError);
			flag = false;
		}
		return flag;
	}

	public LPrtRelatedSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPrtRelatedSet aLPrtRelatedSet = new LPrtRelatedSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LPrtRelatedSchema s1 = new LPrtRelatedSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtRelatedDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPrtRelatedSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLPrtRelatedSet;
	}

	public LPrtRelatedSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPrtRelatedSet aLPrtRelatedSet = new LPrtRelatedSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LPrtRelatedSchema s1 = new LPrtRelatedSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtRelatedDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPrtRelatedSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtRelatedDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLPrtRelatedSet; 
	}

}
