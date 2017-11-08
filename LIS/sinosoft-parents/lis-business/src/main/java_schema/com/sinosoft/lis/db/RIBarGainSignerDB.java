

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.RIBarGainSignerSchema;
import com.sinosoft.lis.vschema.RIBarGainSignerSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIBarGainSignerDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBarGainSignerDB extends RIBarGainSignerSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public RIBarGainSignerDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "RIBarGainSigner" );
		mflag = true;
	}

	public RIBarGainSignerDB()
	{
		con = null;
		db = new DBOper( "RIBarGainSigner" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		RIBarGainSignerSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		RIBarGainSignerSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM RIBarGainSigner WHERE  SerialNo = ?");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBarGainSigner");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBarGainSigner");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE RIBarGainSigner SET  SerialNo = ? , RIContNo = ? , ReComCode = ? , RelaCode = ? , RelaName = ? , RelaTel = ? , Email = ? , FaxNo = ? , Department = ? , MobileTel = ? , Duty = ? , MakeDate = ? , MakeTime = ? , Operator = ? WHERE  SerialNo = ?");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getRIContNo() == null || this.getRIContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRIContNo());
			}
			if(this.getReComCode() == null || this.getReComCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getReComCode());
			}
			if(this.getRelaCode() == null || this.getRelaCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRelaCode());
			}
			if(this.getRelaName() == null || this.getRelaName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRelaName());
			}
			if(this.getRelaTel() == null || this.getRelaTel().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRelaTel());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getEmail());
			}
			if(this.getFaxNo() == null || this.getFaxNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFaxNo());
			}
			if(this.getDepartment() == null || this.getDepartment().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getDepartment());
			}
			if(this.getMobileTel() == null || this.getMobileTel().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getMobileTel());
			}
			if(this.getDuty() == null || this.getDuty().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getDuty());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getMakeTime()));
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getOperator());
			}
			// set where condition
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBarGainSigner");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO RIBarGainSigner(SerialNo ,RIContNo ,ReComCode ,RelaCode ,RelaName ,RelaTel ,Email ,FaxNo ,Department ,MobileTel ,Duty ,MakeDate ,MakeTime ,Operator) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getRIContNo() == null || this.getRIContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRIContNo());
			}
			if(this.getReComCode() == null || this.getReComCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getReComCode());
			}
			if(this.getRelaCode() == null || this.getRelaCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRelaCode());
			}
			if(this.getRelaName() == null || this.getRelaName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRelaName());
			}
			if(this.getRelaTel() == null || this.getRelaTel().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRelaTel());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getEmail());
			}
			if(this.getFaxNo() == null || this.getFaxNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFaxNo());
			}
			if(this.getDepartment() == null || this.getDepartment().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getDepartment());
			}
			if(this.getMobileTel() == null || this.getMobileTel().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getMobileTel());
			}
			if(this.getDuty() == null || this.getDuty().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getDuty());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getMakeTime()));
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM RIBarGainSigner WHERE  SerialNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIBarGainSignerDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
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
			catch(Exception e){}
		}

		return true;
	}

	public RIBarGainSignerSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBarGainSignerSet aRIBarGainSignerSet = new RIBarGainSignerSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIBarGainSigner");
			RIBarGainSignerSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				RIBarGainSignerSchema s1 = new RIBarGainSignerSchema();
				s1.setSchema(rs,i);
				aRIBarGainSignerSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

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

		return aRIBarGainSignerSet;
	}

	public RIBarGainSignerSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBarGainSignerSet aRIBarGainSignerSet = new RIBarGainSignerSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				RIBarGainSignerSchema s1 = new RIBarGainSignerSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIBarGainSignerDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIBarGainSignerSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

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

		return aRIBarGainSignerSet;
	}

	public RIBarGainSignerSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBarGainSignerSet aRIBarGainSignerSet = new RIBarGainSignerSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIBarGainSigner");
			RIBarGainSignerSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
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

				RIBarGainSignerSchema s1 = new RIBarGainSignerSchema();
				s1.setSchema(rs,i);
				aRIBarGainSignerSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

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

		return aRIBarGainSignerSet;
	}

	public RIBarGainSignerSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBarGainSignerSet aRIBarGainSignerSet = new RIBarGainSignerSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
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

				RIBarGainSignerSchema s1 = new RIBarGainSignerSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIBarGainSignerDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIBarGainSignerSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

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

		return aRIBarGainSignerSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIBarGainSigner");
			RIBarGainSignerSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update RIBarGainSigner " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "RIBarGainSignerDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBarGainSignerDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
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
			catch(Exception e){}
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
        tError.moduleName = "RIBarGainSignerDB";
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
        tError.moduleName = "RIBarGainSignerDB";
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
        tError.moduleName = "RIBarGainSignerDB";
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
        tError.moduleName = "RIBarGainSignerDB";
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
 * @return RIBarGainSignerSet
 */
public RIBarGainSignerSet getData()
{
    int tCount = 0;
    RIBarGainSignerSet tRIBarGainSignerSet = new RIBarGainSignerSet();
    RIBarGainSignerSchema tRIBarGainSignerSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "RIBarGainSignerDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tRIBarGainSignerSchema = new RIBarGainSignerSchema();
        tRIBarGainSignerSchema.setSchema(mResultSet, 1);
        tRIBarGainSignerSet.add(tRIBarGainSignerSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tRIBarGainSignerSchema = new RIBarGainSignerSchema();
                tRIBarGainSignerSchema.setSchema(mResultSet, 1);
                tRIBarGainSignerSet.add(tRIBarGainSignerSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "RIBarGainSignerDB";
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
    return tRIBarGainSignerSet;
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
            tError.moduleName = "RIBarGainSignerDB";
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
        tError.moduleName = "RIBarGainSignerDB";
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
            tError.moduleName = "RIBarGainSignerDB";
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
        tError.moduleName = "RIBarGainSignerDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
