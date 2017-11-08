package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.sinosoft.lis.schema.FICustomerAccSchema;
import com.sinosoft.lis.vschema.FICustomerAccSet;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: FICustomerAccDB
 * </p>
 * <p>
 * Description: 客户账户总表（FICustomerAcc）的DB文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccDB extends FICustomerAccSchema
{
private static Logger logger = Logger.getLogger(FICustomerAccDB.class);

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
	public FICustomerAccDB(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con, "FICustomerAcc");
		mflag = true;
	}

	public FICustomerAccDB()
	{
		con = null;
		db = new DBOper("FICustomerAcc");
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		FICustomerAccSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
			return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		FICustomerAccSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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
			pstmt = con.prepareStatement("DELETE FROM FICustomerAcc WHERE  InsuAccNo = ?");
			if (this.getInsuAccNo() == null || this.getInsuAccNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getInsuAccNo());
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
			tError.moduleName = "FICustomerAccDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			// only for debug purpose
			SQLString sqlObj = new SQLString("FICustomerAcc");
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
			pstmt = con.prepareStatement("UPDATE FICustomerAcc SET  InsuAccNo = ? , Currency = ? , CustomerNo = ? , CustomerType = ? , AccType = ? , Summoney = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  InsuAccNo = ?");
			if (this.getInsuAccNo() == null || this.getInsuAccNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getInsuAccNo());
			}
			if (this.getCurrency() == null || this.getCurrency().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getCurrency());
			}
			if (this.getCustomerNo() == null || this.getCustomerNo().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getCustomerNo());
			}
			if (this.getCustomerType() == null || this.getCustomerType().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getCustomerType());
			}
			if (this.getAccType() == null || this.getAccType().equals("null"))
			{
				pstmt.setNull(5, 12);
			}
			else
			{
				pstmt.setString(5, this.getAccType());
			}
			pstmt.setDouble(6, this.getSummoney());
			if (this.getState() == null || this.getState().equals("null"))
			{
				pstmt.setNull(7, 12);
			}
			else
			{
				pstmt.setString(7, this.getState());
			}
			if (this.getOperator() == null || this.getOperator().equals("null"))
			{
				pstmt.setNull(8, 12);
			}
			else
			{
				pstmt.setString(8, this.getOperator());
			}
			if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
			{
				pstmt.setNull(9, 91);
			}
			else
			{
				pstmt.setDate(9, Date.valueOf(this.getMakeDate()));
			}
			if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
			{
				pstmt.setNull(10, 12);
			}
			else
			{
				pstmt.setString(10, this.getMakeTime());
			}
			if (this.getModifyDate() == null || this.getModifyDate().equals("null"))
			{
				pstmt.setNull(11, 91);
			}
			else
			{
				pstmt.setDate(11, Date.valueOf(this.getModifyDate()));
			}
			if (this.getModifyTime() == null || this.getModifyTime().equals("null"))
			{
				pstmt.setNull(12, 12);
			}
			else
			{
				pstmt.setString(12, this.getModifyTime());
			}
			// set where condition
			if (this.getInsuAccNo() == null || this.getInsuAccNo().equals("null"))
			{
				pstmt.setNull(13, 12);
			}
			else
			{
				pstmt.setString(13, this.getInsuAccNo());
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
			tError.moduleName = "FICustomerAccDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			// only for debug purpose
			SQLString sqlObj = new SQLString("FICustomerAcc");
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
			pstmt = con.prepareStatement("INSERT INTO FICustomerAcc VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if (this.getInsuAccNo() == null || this.getInsuAccNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getInsuAccNo());
			}
			if (this.getCurrency() == null || this.getCurrency().equals("null"))
			{
				pstmt.setNull(2, 12);
			}
			else
			{
				pstmt.setString(2, this.getCurrency());
			}
			if (this.getCustomerNo() == null || this.getCustomerNo().equals("null"))
			{
				pstmt.setNull(3, 12);
			}
			else
			{
				pstmt.setString(3, this.getCustomerNo());
			}
			if (this.getCustomerType() == null || this.getCustomerType().equals("null"))
			{
				pstmt.setNull(4, 12);
			}
			else
			{
				pstmt.setString(4, this.getCustomerType());
			}
			if (this.getAccType() == null || this.getAccType().equals("null"))
			{
				pstmt.setNull(5, 12);
			}
			else
			{
				pstmt.setString(5, this.getAccType());
			}
			pstmt.setDouble(6, this.getSummoney());
			if (this.getState() == null || this.getState().equals("null"))
			{
				pstmt.setNull(7, 12);
			}
			else
			{
				pstmt.setString(7, this.getState());
			}
			if (this.getOperator() == null || this.getOperator().equals("null"))
			{
				pstmt.setNull(8, 12);
			}
			else
			{
				pstmt.setString(8, this.getOperator());
			}
			if (this.getMakeDate() == null || this.getMakeDate().equals("null"))
			{
				pstmt.setNull(9, 91);
			}
			else
			{
				pstmt.setDate(9, Date.valueOf(this.getMakeDate()));
			}
			if (this.getMakeTime() == null || this.getMakeTime().equals("null"))
			{
				pstmt.setNull(10, 12);
			}
			else
			{
				pstmt.setString(10, this.getMakeTime());
			}
			if (this.getModifyDate() == null || this.getModifyDate().equals("null"))
			{
				pstmt.setNull(11, 91);
			}
			else
			{
				pstmt.setDate(11, Date.valueOf(this.getModifyDate()));
			}
			if (this.getModifyTime() == null || this.getModifyTime().equals("null"))
			{
				pstmt.setNull(12, 12);
			}
			else
			{
				pstmt.setString(12, this.getModifyTime());
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
			tError.moduleName = "FICustomerAccDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			// only for debug purpose
			SQLString sqlObj = new SQLString("FICustomerAcc");
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
			pstmt = con.prepareStatement("SELECT * FROM FICustomerAcc WHERE  InsuAccNo = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (this.getInsuAccNo() == null || this.getInsuAccNo().equals("null"))
			{
				pstmt.setNull(1, 12);
			}
			else
			{
				pstmt.setString(1, this.getInsuAccNo());
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
					tError.moduleName = "FICustomerAccDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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

	public FICustomerAccSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		FICustomerAccSet aFICustomerAccSet = new FICustomerAccSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("FICustomerAcc");
			FICustomerAccSchema aSchema = this.getSchema();
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
				FICustomerAccSchema s1 = new FICustomerAccSchema();
				s1.setSchema(rs,i);
				aFICustomerAccSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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

		return aFICustomerAccSet;

	}

	public FICustomerAccSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		FICustomerAccSet aFICustomerAccSet = new FICustomerAccSet();

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
				FICustomerAccSchema s1 = new FICustomerAccSchema();
				if (!s1.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FICustomerAccDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aFICustomerAccSet.add(s1);
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
			tError.moduleName = "FICustomerAccDB";
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

		return aFICustomerAccSet;
	}

	public FICustomerAccSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		FICustomerAccSet aFICustomerAccSet = new FICustomerAccSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("FICustomerAcc");
			FICustomerAccSchema aSchema = this.getSchema();
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

				FICustomerAccSchema s1 = new FICustomerAccSchema();
				s1.setSchema(rs,i);
				aFICustomerAccSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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

		return aFICustomerAccSet;

	}

	public FICustomerAccSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		FICustomerAccSet aFICustomerAccSet = new FICustomerAccSet();

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

				FICustomerAccSchema s1 = new FICustomerAccSchema();
				if (!s1.setSchema(rs, i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FICustomerAccDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aFICustomerAccSet.add(s1);
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
			tError.moduleName = "FICustomerAccDB";
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

		return aFICustomerAccSet;
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
			SQLString sqlObj = new SQLString("FICustomerAcc");
			FICustomerAccSchema aSchema = this.getSchema();
			sqlObj.setSQL(2, aSchema);
			String sql = "update FICustomerAcc " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if (operCount == 0)
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
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
	 * @return FICustomerAccSet
	 */
	public FICustomerAccSet getData()
	{
		int tCount = 0;
		FICustomerAccSet tFICustomerAccSet = new FICustomerAccSet();
		FICustomerAccSchema tFICustomerAccSchema = null;
		if (null == mResultSet)
		{
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
			tError.functionName = "getData";
			tError.errorMessage = "数据集为空，请先准备数据集！";
			this.mErrors.addOneError(tError);
			return null;
		}
		try
		{
			tCount = 1;
			tFICustomerAccSchema = new FICustomerAccSchema();
			tFICustomerAccSchema.setSchema(mResultSet, 1);
			tFICustomerAccSet.add(tFICustomerAccSchema);
			// 注意mResultSet.next()的作用
			while (tCount++ < SysConst.FETCHCOUNT)
			{
				if (mResultSet.next())
				{
					tFICustomerAccSchema = new FICustomerAccSchema();
					tFICustomerAccSchema.setSchema(mResultSet, 1);
					tFICustomerAccSet.add(tFICustomerAccSchema);
				}
			}
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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
		return tFICustomerAccSet;
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
				tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
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
				tError.moduleName = "FICustomerAccDB";
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
			tError.moduleName = "FICustomerAccDB";
			tError.functionName = "closeData";
			tError.errorMessage = ex3.toString();
			this.mErrors.addOneError(tError);
			flag = false;
		}
		return flag;
	}

	public FICustomerAccSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FICustomerAccSet aFICustomerAccSet = new FICustomerAccSet();

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
				FICustomerAccSchema s1 = new FICustomerAccSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FICustomerAccDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFICustomerAccSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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

		return aFICustomerAccSet;
	}

	public FICustomerAccSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FICustomerAccSet aFICustomerAccSet = new FICustomerAccSet();

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

				FICustomerAccSchema s1 = new FICustomerAccSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FICustomerAccDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFICustomerAccSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccDB";
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

		return aFICustomerAccSet; 
	}

}
