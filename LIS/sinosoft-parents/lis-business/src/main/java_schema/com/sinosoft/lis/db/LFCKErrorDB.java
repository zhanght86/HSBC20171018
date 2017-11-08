package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;

import java.sql.*;

import com.sinosoft.lis.schema.LFCKErrorSchema;
import com.sinosoft.lis.vschema.LFCKErrorSet;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: LFCKErrorDB
 * </p>
 * <p>
 * Description: 保监会报表校验错误信息表（LFCKError）的DB文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-11-03
 */
public class LFCKErrorDB extends LFCKErrorSchema
{
	// @Field
	private Connection con;

	private DBOper db;

	/**
	 * flag = true: 传入Connection flag = false: 不传入Connection
	 **/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors(); // 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;

	private Statement mStatement = null;

	// @Constructor
	public LFCKErrorDB(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con, "LFCKError");
		mflag = true;
	}

	public LFCKErrorDB()
	{
		con = null;
		db = new DBOper("LFCKError");
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LFCKErrorSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
			return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LFCKErrorSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM LFCKError WHERE  SerialNo = ?");
			if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception ex)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			// only for debug purpose
			SQLString sqlObj = new SQLString("LFCKError");
			sqlObj.printTrueSQL(this, "delete");

			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{}
			}

			return false;
		}

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("UPDATE LFCKError SET  SerialNo = ? , ItemCode = ? , comcodeisc = ? , CKRuleCode = ? , CKError = ? , MakeDate = ? , MakeTime = ? WHERE  SerialNo = ?");
			if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getSerialNo());
			}
			if (this.getItemCode() == null || this.getItemCode().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getItemCode());
			}
			if (this.getcomcodeisc() == null || this.getcomcodeisc().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getcomcodeisc());
			}
			if (this.getCKRuleCode() == null || this.getCKRuleCode().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getCKRuleCode());
			}
			if (this.getCKError() == null || this.getCKError().equals("null"))
			{
				pstmt.setNull(5, 12);
			}
			else
			{
				pstmt.setString(5, this.getCKError());
			}
			if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
			{
				pstmt.setNull(6, 91);
			}
			else
			{
				pstmt.setDate(6, Date.valueOf(this.getMakeDate()));
			}
			if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
			{
				pstmt.setNull(7, 12);
			}
			else
			{
				pstmt.setString(7, this.getMakeTime());
			}
			// set where condition
			if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
			{
				pstmt.setNull(8, 12);
			}
			else
			{
				pstmt.setString(8, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception ex)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			// only for debug purpose
			SQLString sqlObj = new SQLString("LFCKError");
			sqlObj.printTrueSQL(this, "update");
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{}
			}

			return false;
		}

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LFCKError VALUES( ? , ? , ? , ? , ? , ? , ?)");
			if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getSerialNo());
			}
			if (this.getItemCode() == null || this.getItemCode().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getItemCode());
			}
			if (this.getcomcodeisc() == null || this.getcomcodeisc().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getcomcodeisc());
			}
			if (this.getCKRuleCode() == null || this.getCKRuleCode().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getCKRuleCode());
			}
			if (this.getCKError() == null || this.getCKError().equals("null"))
			{
				pstmt.setNull(5, 12);
			}
			else
			{
				pstmt.setString(5, this.getCKError());
			}
			if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
			{
				pstmt.setNull(6, 91);
			}
			else
			{
				pstmt.setDate(6, Date.valueOf(this.getMakeDate()));
			}
			if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
			{
				pstmt.setNull(7, 12);
			}
			else
			{
				pstmt.setString(7, this.getMakeTime());
			}
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (Exception ex)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			// only for debug purpose
			SQLString sqlObj = new SQLString("LFCKError");
			sqlObj.printTrueSQL(this, "insert");
			try
			{
				pstmt.close();
			}
			catch (Exception e)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception e)
				{}
			}

			return false;
		}

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}

		return true;
	}

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
			pstmt = con.prepareStatement("SELECT * FROM LFCKError WHERE  SerialNo = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (this.getSerialNo() == null || this.getSerialNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getSerialNo());
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
					tError.moduleName = "LFCKErrorDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors.addOneError(tError);

					try
					{
						rs.close();
					}
					catch (Exception ex)
					{}
					try
					{
						pstmt.close();
					}
					catch (Exception ex1)
					{}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch (Exception et)
						{}
					}
					return false;
				}
				break;
			}
			try
			{
				rs.close();
			}
			catch (Exception ex2)
			{}
			try
			{
				pstmt.close();
			}
			catch (Exception ex3)
			{}

			if (i == 0)
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch (Exception et)
					{}
				}
				return false;
			}
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try
			{
				rs.close();
			}
			catch (Exception ex)
			{}
			try
			{
				pstmt.close();
			}
			catch (Exception ex1)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
			return false;
		}
		// 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}

		return true;
	}

	public LFCKErrorSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LFCKErrorSet aLFCKErrorSet = new LFCKErrorSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LFCKError");
			LFCKErrorSchema aSchema = this.getSchema();
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
				LFCKErrorSchema s1 = new LFCKErrorSchema();
				s1.setSchema(rs,i);
				aLFCKErrorSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
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

		return aLFCKErrorSet;

	}

	public LFCKErrorSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LFCKErrorSet aLFCKErrorSet = new LFCKErrorSet();

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
				LFCKErrorSchema s1 = new LFCKErrorSchema();
				if (!s1.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFCKErrorDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLFCKErrorSet.add(s1);
			}
			try
			{
				rs.close();
			}
			catch (Exception ex)
			{}
			try
			{
				stmt.close();
			}
			catch (Exception ex1)
			{}
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try
			{
				rs.close();
			}
			catch (Exception ex2)
			{}
			try
			{
				stmt.close();
			}
			catch (Exception ex3)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
		}

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}

		return aLFCKErrorSet;
	}

	public LFCKErrorSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LFCKErrorSet aLFCKErrorSet = new LFCKErrorSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LFCKError");
			LFCKErrorSchema aSchema = this.getSchema();
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

				LFCKErrorSchema s1 = new LFCKErrorSchema();
				s1.setSchema(rs,i);
				aLFCKErrorSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
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

		return aLFCKErrorSet;

	}

	public LFCKErrorSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LFCKErrorSet aLFCKErrorSet = new LFCKErrorSet();

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

				if (i < nStart)
				{
					continue;
				}

				if (i >= nStart + nCount)
				{
					break;
				}

				LFCKErrorSchema s1 = new LFCKErrorSchema();
				if (!s1.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFCKErrorDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLFCKErrorSet.add(s1);
			}
			try
			{
				rs.close();
			}
			catch (Exception ex)
			{}
			try
			{
				stmt.close();
			}
			catch (Exception ex1)
			{}
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try
			{
				rs.close();
			}
			catch (Exception ex2)
			{}
			try
			{
				stmt.close();
			}
			catch (Exception ex3)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
		}

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
		}

		return aLFCKErrorSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

		if (!mflag)
		{
			con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LFCKError");
			LFCKErrorSchema aSchema = this.getSchema();
			sqlObj.setSQL(2, aSchema);
			String sql = "update LFCKError " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if (operCount == 0)
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LFCKErrorDB";
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
					{}
				}
				return false;
			}
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try
			{
				stmt.close();
			}
			catch (Exception ex1)
			{}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
			return false;
		}
		// 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
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
			tError.moduleName = "LFCKErrorDB";
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
			mStatement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
		}
		catch (Exception e)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "prepareData";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try
			{
				mResultSet.close();
			}
			catch (Exception ex2)
			{}
			try
			{
				mStatement.close();
			}
			catch (Exception ex3)
			{}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
			return false;
		}

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch (Exception e)
			{}
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
			tError.moduleName = "LFCKErrorDB";
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
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "hasMoreData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try
			{
				mResultSet.close();
				mResultSet = null;
			}
			catch (Exception ex2)
			{}
			try
			{
				mStatement.close();
				mStatement = null;
			}
			catch (Exception ex3)
			{}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
			return false;
		}
		return flag;
	}

	/**
	 * 获取定量数据
	 * @return LFCKErrorSet
	 */
	public LFCKErrorSet getData()
	{
		int tCount = 0;
		LFCKErrorSet tLFCKErrorSet = new LFCKErrorSet();
		LFCKErrorSchema tLFCKErrorSchema = null;
		if (null == mResultSet)
		{
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "getData";
			tError.errorMessage = "数据集为空，请先准备数据集！";
			this.mErrors.addOneError(tError);
			return null;
		}
		try
		{
			tCount = 1;
			tLFCKErrorSchema = new LFCKErrorSchema();
			tLFCKErrorSchema.setSchema(mResultSet, 1);
			tLFCKErrorSet.add(tLFCKErrorSchema);
			// 注意mResultSet.next()的作用
			while (tCount++ < SysConst.FETCHCOUNT)
			{
				if (mResultSet.next())
				{
					tLFCKErrorSchema = new LFCKErrorSchema();
					tLFCKErrorSchema.setSchema(mResultSet, 1);
					tLFCKErrorSet.add(tLFCKErrorSchema);
				}
			}
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "getData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try
			{
				mResultSet.close();
				mResultSet = null;
			}
			catch (Exception ex2)
			{}
			try
			{
				mStatement.close();
				mStatement = null;
			}
			catch (Exception ex3)
			{}
			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch (Exception et)
				{}
			}
			return null;
		}
		return tLFCKErrorSet;
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
				tError.moduleName = "LFCKErrorDB";
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
			tError.moduleName = "LFCKErrorDB";
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
				tError.moduleName = "LFCKErrorDB";
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
			tError.moduleName = "LFCKErrorDB";
			tError.functionName = "closeData";
			tError.errorMessage = ex3.toString();
			this.mErrors.addOneError(tError);
			flag = false;
		}
		return flag;
	}

	public LFCKErrorSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LFCKErrorSet aLFCKErrorSet = new LFCKErrorSet();

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
				LFCKErrorSchema s1 = new LFCKErrorSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFCKErrorDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLFCKErrorSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
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

		return aLFCKErrorSet;
	}

	public LFCKErrorSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LFCKErrorSet aLFCKErrorSet = new LFCKErrorSet();

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

				LFCKErrorSchema s1 = new LFCKErrorSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFCKErrorDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLFCKErrorSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFCKErrorDB";
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

		return aLFCKErrorSet; 
	}

}
