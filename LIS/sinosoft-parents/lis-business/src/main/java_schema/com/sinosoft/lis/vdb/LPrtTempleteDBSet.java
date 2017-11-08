/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.LPrtTempleteSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: LPrtTempleteDBSet
 * </p>
 * <p>
 * Description: DB层多记录数据库操作类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-01-24
 */
public class LPrtTempleteDBSet extends LPrtTempleteSet
{
private static Logger logger = Logger.getLogger(LPrtTempleteDBSet.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 */
	private boolean mflag = false;

	// @Constructor
	public LPrtTempleteDBSet(Connection cConnection)
	{
		con = cConnection;
		db = new DBOper(con, "LPrtTemplete");
		mflag = true;
	}

	public LPrtTempleteDBSet()
	{
		db = new DBOper("LPrtTemplete");
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
			tError.moduleName = "LPrtTempleteDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LPrtTemplete WHERE  TempleteID = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getTempleteID());
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
			tError.moduleName = "LPrtTempleteDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtTemplete");
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
			pstmt = con.prepareStatement("UPDATE LPrtTemplete SET  TempleteID = ? , TempleteName = ? , Language = ? , TempleteType = ? , OutputType = ? , Output = ? , FilePath = ? , DefaultType = ? , State = ? , Remark = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  TempleteID = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getTempleteID());
				}
				if (this.get(i).getTempleteName() == null || this.get(i).getTempleteName().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getTempleteName());
				}
				if (this.get(i).getLanguage() == null || this.get(i).getLanguage().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getLanguage());
				}
				if (this.get(i).getTempleteType() == null || this.get(i).getTempleteType().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getTempleteType());
				}
				if (this.get(i).getOutputType() == null || this.get(i).getOutputType().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getOutputType());
				}
				if (this.get(i).getOutput() == null || this.get(i).getOutput().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getOutput());
				}
				if (this.get(i).getFilePath() == null || this.get(i).getFilePath().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getFilePath());
				}
				if (this.get(i).getDefaultType() == null || this.get(i).getDefaultType().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getDefaultType());
				}
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(9, null);
				}
				else
				{
					pstmt.setString(9, this.get(i).getState());
				}
				if (this.get(i).getRemark() == null || this.get(i).getRemark().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getRemark());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(12, null);
				}
				else
				{
					pstmt.setDate(12, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(13, null);
				}
				else
				{
					pstmt.setString(13, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(14, null);
				}
				else
				{
					pstmt.setDate(14, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(15, null);
				}
				else
				{
					pstmt.setString(15, this.get(i).getModifyTime());
				}
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(16, null);
				}
				else
				{
					pstmt.setString(16, this.get(i).getTempleteID());
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
			tError.moduleName = "LPrtTempleteDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtTemplete");
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
			pstmt = con.prepareStatement("INSERT INTO LPrtTemplete VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getTempleteID() == null || this.get(i).getTempleteID().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getTempleteID());
				}
				if (this.get(i).getTempleteName() == null || this.get(i).getTempleteName().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getTempleteName());
				}
				if (this.get(i).getLanguage() == null || this.get(i).getLanguage().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getLanguage());
				}
				if (this.get(i).getTempleteType() == null || this.get(i).getTempleteType().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getTempleteType());
				}
				if (this.get(i).getOutputType() == null || this.get(i).getOutputType().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getOutputType());
				}
				if (this.get(i).getOutput() == null || this.get(i).getOutput().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getOutput());
				}
				if (this.get(i).getFilePath() == null || this.get(i).getFilePath().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getFilePath());
				}
				if (this.get(i).getDefaultType() == null || this.get(i).getDefaultType().equals("null"))
				{
					pstmt.setString(8, null);
				}
				else
				{
					pstmt.setString(8, this.get(i).getDefaultType());
				}
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(9, null);
				}
				else
				{
					pstmt.setString(9, this.get(i).getState());
				}
				if (this.get(i).getRemark() == null || this.get(i).getRemark().equals("null"))
				{
					pstmt.setString(10, null);
				}
				else
				{
					pstmt.setString(10, this.get(i).getRemark());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(12, null);
				}
				else
				{
					pstmt.setDate(12, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(13, null);
				}
				else
				{
					pstmt.setString(13, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(14, null);
				}
				else
				{
					pstmt.setDate(14, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(15, null);
				}
				else
				{
					pstmt.setString(15, this.get(i).getModifyTime());
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
			tError.moduleName = "LPrtTempleteDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtTemplete");
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
