/**
 * Copyright (c) 2006 Sinosoft Co.,LTD.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import com.sinosoft.lis.vschema.LPrtDefinitionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.SQLString;

/**
 * <p>
 * ClassName: LPrtDefinitionDBSet
 * </p>
 * <p>
 * Description: DB层多记录数据库操作类文件
 * </p>
 * <p>
 * Company: Sinosoft Co.,LTD
 * </p>
 * @Database: Physical Data _1
 * @author：Makerx
 * @CreateDate：2011-02-21
 */
public class LPrtDefinitionDBSet extends LPrtDefinitionSet
{
private static Logger logger = Logger.getLogger(LPrtDefinitionDBSet.class);

	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 */
	private boolean mflag = false;

	// @Constructor
	public LPrtDefinitionDBSet(Connection cConnection)
	{
		con = cConnection;
		db = new DBOper(con, "LPrtDefinition");
		mflag = true;
	}

	public LPrtDefinitionDBSet()
	{
		db = new DBOper("LPrtDefinition");
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
			tError.moduleName = "LPrtDefinitionDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LPrtDefinition WHERE  PrintID = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getPrintID() == null || this.get(i).getPrintID().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getPrintID());
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
			tError.moduleName = "LPrtDefinitionDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtDefinition");
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
			pstmt = con.prepareStatement("UPDATE LPrtDefinition SET  PrintID = ? , PrintName = ? , PrintObject = ? , PrintType = ? , State = ? , LanguageType = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  PrintID = ?");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getPrintID() == null || this.get(i).getPrintID().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getPrintID());
				}
				if (this.get(i).getPrintName() == null || this.get(i).getPrintName().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getPrintName());
				}
				if (this.get(i).getPrintObject() == null || this.get(i).getPrintObject().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getPrintObject());
				}
				if (this.get(i).getPrintType() == null || this.get(i).getPrintType().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getPrintType());
				}
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getState());
				}
				if (this.get(i).getLanguageType() == null || this.get(i).getLanguageType().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getLanguageType());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(8, null);
				}
				else
				{
					pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(9, null);
				}
				else
				{
					pstmt.setString(9, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(10, null);
				}
				else
				{
					pstmt.setDate(10, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getModifyTime());
				}
				if (this.get(i).getPrintID() == null || this.get(i).getPrintID().equals("null"))
				{
					pstmt.setString(12, null);
				}
				else
				{
					pstmt.setString(12, this.get(i).getPrintID());
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
			tError.moduleName = "LPrtDefinitionDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtDefinition");
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
			pstmt = con.prepareStatement("INSERT INTO LPrtDefinition VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			for (int i = 1; i <= tCount; i++)
			{
				if (this.get(i).getPrintID() == null || this.get(i).getPrintID().equals("null"))
				{
					pstmt.setString(1, null);
				}
				else
				{
					pstmt.setString(1, this.get(i).getPrintID());
				}
				if (this.get(i).getPrintName() == null || this.get(i).getPrintName().equals("null"))
				{
					pstmt.setString(2, null);
				}
				else
				{
					pstmt.setString(2, this.get(i).getPrintName());
				}
				if (this.get(i).getPrintObject() == null || this.get(i).getPrintObject().equals("null"))
				{
					pstmt.setString(3, null);
				}
				else
				{
					pstmt.setString(3, this.get(i).getPrintObject());
				}
				if (this.get(i).getPrintType() == null || this.get(i).getPrintType().equals("null"))
				{
					pstmt.setString(4, null);
				}
				else
				{
					pstmt.setString(4, this.get(i).getPrintType());
				}
				if (this.get(i).getState() == null || this.get(i).getState().equals("null"))
				{
					pstmt.setString(5, null);
				}
				else
				{
					pstmt.setString(5, this.get(i).getState());
				}
				if (this.get(i).getLanguageType() == null || this.get(i).getLanguageType().equals("null"))
				{
					pstmt.setString(6, null);
				}
				else
				{
					pstmt.setString(6, this.get(i).getLanguageType());
				}
				if (this.get(i).getOperator() == null || this.get(i).getOperator().equals("null"))
				{
					pstmt.setString(7, null);
				}
				else
				{
					pstmt.setString(7, this.get(i).getOperator());
				}
				if (this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null"))
				{
					pstmt.setDate(8, null);
				}
				else
				{
					pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
				}
				if (this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null"))
				{
					pstmt.setString(9, null);
				}
				else
				{
					pstmt.setString(9, this.get(i).getMakeTime());
				}
				if (this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null"))
				{
					pstmt.setDate(10, null);
				}
				else
				{
					pstmt.setDate(10, Date.valueOf(this.get(i).getModifyDate()));
				}
				if (this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null"))
				{
					pstmt.setString(11, null);
				}
				else
				{
					pstmt.setString(11, this.get(i).getModifyTime());
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
			tError.moduleName = "LPrtDefinitionDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			int tCount = this.size();
			SQLString sqlObj = new SQLString("LPrtDefinition");
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
