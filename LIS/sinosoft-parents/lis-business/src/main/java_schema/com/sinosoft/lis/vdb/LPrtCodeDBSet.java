/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.LPrtCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: LPrtCodeDBSet
 * </p>
 * <p>
 * Description: DB层多记录数据库操作类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-03-01
 */
public class LPrtCodeDBSet extends LPrtCodeSet
{
private static Logger logger = Logger.getLogger(LPrtCodeDBSet.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 */
	private boolean mflag = false;

	// @Constructor
	public LPrtCodeDBSet(Connection cConnection)
	{
		con = cConnection;
		db = new DBOper(con, "LPrtCode");
		mflag = true;
	}

	public LPrtCodeDBSet()
	{
		db = new DBOper("LPrtCode");
	}

	// @Method
	public boolean deleteSQL()
	{
		if (db.deleteSQL(this))
		{
			return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtCodeDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 删除操作 删除条件：主键
	 * @return boolean
	 */
	public boolean delete()
	{
		PreparedStatement pstmt = null;
		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}
		try
		{
			if (!mflag)
			{
				// 如果是内部创建的连接，需要设置Commit模式
				con.setAutoCommit(false);
			}
			int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM LPrtCode WHERE  CodeType = ? AND TempleteID = ? AND Code = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getCodeType());
				}
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getTempleteID());
				}
				if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getCode());
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			if (!mflag)
			{
				// 如果是内部创建的连接，执行成功后需要执行Commit
				con.commit();
				con.close();
			}
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtCodeDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtCode");
			for (int i = 1; i <= tCount; i++)
			{
				// 输出出错Sql语句
				sqlObj.setSQL(4, this.get(i));
				logger.debug("出错Sql为：" + sqlObj.getSQL());
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
				// 如果是内部创建的连接，出错后需要执行RollBack
				try
				{
					con.rollback();
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
			if (!mflag)
			{
				// 如果是内部创建的连接，需要设置Commit模式
				con.setAutoCommit(false);
			}
			int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE LPrtCode SET  CodeType = ? , TempleteID = ? , Code = ? , Content1 = ? , Content2 = ? WHERE  CodeType = ? AND TempleteID = ? AND Code = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getCodeType());
				}
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getTempleteID());
				}
				if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getCode());
				}
				if (this.get(i).getContent1() == null || this.get(i).getContent1().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getContent1());
				}
				if (this.get(i).getContent2() == null || this.get(i).getContent2().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getContent2());
				}
				if (this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getCodeType());
				}
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getTempleteID());
				}
				if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getCode());
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			if (!mflag)
			{
				// 如果是内部创建的连接，执行成功后需要执行Commit
				con.commit();
				con.close();
			}
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtCodeDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtCode");
			for (int i = 1; i <= tCount; i++)
			{
				// 输出出错Sql语句
				sqlObj.setSQL(2, this.get(i));
				logger.debug("出错Sql为：" + sqlObj.getSQL());
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
				// 如果是内部创建的连接，出错后需要执行RollBack
				try
				{
					con.rollback();
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
			if (!mflag)
			{
				// 如果是内部创建的连接，需要设置Commit模式
				con.setAutoCommit(false);
			}
			int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO LPrtCode VALUES( ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getCodeType());
				}
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getTempleteID());
				}
				if (this.get(i).getCode() == null || this.get(i).getCode().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getCode());
				}
				if (this.get(i).getContent1() == null || this.get(i).getContent1().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getContent1());
				}
				if (this.get(i).getContent2() == null || this.get(i).getContent2().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getContent2());
				}
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			if (!mflag)
			{
				// 如果是内部创建的连接，执行成功后需要执行Commit
				con.commit();
				con.close();
			}
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPrtCodeDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtCode");
			for (int i = 1; i <= tCount; i++)
			{
				// 输出出错Sql语句
				sqlObj.setSQL(1, this.get(i));
				logger.debug("出错Sql为：" + sqlObj.getSQL());
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
				// 如果是内部创建的连接，出错后需要执行RollBack
				try
				{
					con.rollback();
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
}
