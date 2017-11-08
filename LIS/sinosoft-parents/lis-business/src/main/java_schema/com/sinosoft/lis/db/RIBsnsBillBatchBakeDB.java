

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.RIBsnsBillBatchBakeSchema;
import com.sinosoft.lis.vschema.RIBsnsBillBatchBakeSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIBsnsBillBatchBakeDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBsnsBillBatchBakeDB extends RIBsnsBillBatchBakeSchema
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
	public RIBsnsBillBatchBakeDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "RIBsnsBillBatchBake" );
		mflag = true;
	}

	public RIBsnsBillBatchBakeDB()
	{
		con = null;
		db = new DBOper( "RIBsnsBillBatchBake" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		RIBsnsBillBatchBakeSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		RIBsnsBillBatchBakeSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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
			pstmt = con.prepareStatement("DELETE FROM RIBsnsBillBatchBake WHERE  BackupNo = ? AND BatchNo = ? AND BillNo = ? AND Currency = ?");
			if(this.getBackupNo() == null || this.getBackupNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackupNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBillNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCurrency());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBsnsBillBatchBake");
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
		SQLString sqlObj = new SQLString("RIBsnsBillBatchBake");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE RIBsnsBillBatchBake SET  BackupNo = ? , BatchNo = ? , BillNo = ? , Currency = ? , IncomeCompanyNo = ? , State = ? , RIContNo = ? , BillTimes = ? , StartDate = ? , EndDate = ? , UWOperator = ? , Idea = ? , Operator = ? , ManageCom = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? WHERE  BackupNo = ? AND BatchNo = ? AND BillNo = ? AND Currency = ?");
			if(this.getBackupNo() == null || this.getBackupNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackupNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBillNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCurrency());
			}
			if(this.getIncomeCompanyNo() == null || this.getIncomeCompanyNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIncomeCompanyNo());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getState());
			}
			if(this.getRIContNo() == null || this.getRIContNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRIContNo());
			}
			if(this.getBillTimes() == null || this.getBillTimes().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getBillTimes());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getEndDate()));
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getUWOperator());
			}
			if(this.getIdea() == null || this.getIdea().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIdea());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getOperator());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getManageCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getModifyTime());
			}
			if(this.getStandbyString1() == null || this.getStandbyString1().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStandbyString1());
			}
			if(this.getStandbyString2() == null || this.getStandbyString2().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandbyString2());
			}
			if(this.getStandbyString3() == null || this.getStandbyString3().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandbyString3());
			}
			// set where condition
			if(this.getBackupNo() == null || this.getBackupNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getBackupNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBatchNo());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBillNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getCurrency());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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
		SQLString sqlObj = new SQLString("RIBsnsBillBatchBake");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO RIBsnsBillBatchBake(BackupNo ,BatchNo ,BillNo ,Currency ,IncomeCompanyNo ,State ,RIContNo ,BillTimes ,StartDate ,EndDate ,UWOperator ,Idea ,Operator ,ManageCom ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,StandbyString1 ,StandbyString2 ,StandbyString3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBackupNo() == null || this.getBackupNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackupNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBillNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCurrency());
			}
			if(this.getIncomeCompanyNo() == null || this.getIncomeCompanyNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getIncomeCompanyNo());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getState());
			}
			if(this.getRIContNo() == null || this.getRIContNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRIContNo());
			}
			if(this.getBillTimes() == null || this.getBillTimes().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getBillTimes());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getEndDate()));
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getUWOperator());
			}
			if(this.getIdea() == null || this.getIdea().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIdea());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getOperator());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getManageCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getModifyTime());
			}
			if(this.getStandbyString1() == null || this.getStandbyString1().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStandbyString1());
			}
			if(this.getStandbyString2() == null || this.getStandbyString2().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandbyString2());
			}
			if(this.getStandbyString3() == null || this.getStandbyString3().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandbyString3());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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
			pstmt = con.prepareStatement("SELECT * FROM RIBsnsBillBatchBake WHERE  BackupNo = ? AND BatchNo = ? AND BillNo = ? AND Currency = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBackupNo() == null || this.getBackupNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackupNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBillNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCurrency());
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
					tError.moduleName = "RIBsnsBillBatchBakeDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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

	public RIBsnsBillBatchBakeSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBsnsBillBatchBakeSet aRIBsnsBillBatchBakeSet = new RIBsnsBillBatchBakeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIBsnsBillBatchBake");
			RIBsnsBillBatchBakeSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				RIBsnsBillBatchBakeSchema s1 = new RIBsnsBillBatchBakeSchema();
				s1.setSchema(rs,i);
				aRIBsnsBillBatchBakeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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

		return aRIBsnsBillBatchBakeSet;
	}

	public RIBsnsBillBatchBakeSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBsnsBillBatchBakeSet aRIBsnsBillBatchBakeSet = new RIBsnsBillBatchBakeSet();

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
				RIBsnsBillBatchBakeSchema s1 = new RIBsnsBillBatchBakeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIBsnsBillBatchBakeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIBsnsBillBatchBakeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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

		return aRIBsnsBillBatchBakeSet;
	}

	public RIBsnsBillBatchBakeSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBsnsBillBatchBakeSet aRIBsnsBillBatchBakeSet = new RIBsnsBillBatchBakeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIBsnsBillBatchBake");
			RIBsnsBillBatchBakeSchema aSchema = this.getSchema();
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

				RIBsnsBillBatchBakeSchema s1 = new RIBsnsBillBatchBakeSchema();
				s1.setSchema(rs,i);
				aRIBsnsBillBatchBakeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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

		return aRIBsnsBillBatchBakeSet;
	}

	public RIBsnsBillBatchBakeSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIBsnsBillBatchBakeSet aRIBsnsBillBatchBakeSet = new RIBsnsBillBatchBakeSet();

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

				RIBsnsBillBatchBakeSchema s1 = new RIBsnsBillBatchBakeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIBsnsBillBatchBakeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIBsnsBillBatchBakeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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

		return aRIBsnsBillBatchBakeSet;
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
			SQLString sqlObj = new SQLString("RIBsnsBillBatchBake");
			RIBsnsBillBatchBakeSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update RIBsnsBillBatchBake " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "RIBsnsBillBatchBakeDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchBakeDB";
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
        tError.moduleName = "RIBsnsBillBatchBakeDB";
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
        e.printStackTrace();
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "RIBsnsBillBatchBakeDB";
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
        tError.moduleName = "RIBsnsBillBatchBakeDB";
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
        tError.moduleName = "RIBsnsBillBatchBakeDB";
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
 * @return RIBsnsBillBatchBakeSet
 */
public RIBsnsBillBatchBakeSet getData()
{
    int tCount = 0;
    RIBsnsBillBatchBakeSet tRIBsnsBillBatchBakeSet = new RIBsnsBillBatchBakeSet();
    RIBsnsBillBatchBakeSchema tRIBsnsBillBatchBakeSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "RIBsnsBillBatchBakeDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tRIBsnsBillBatchBakeSchema = new RIBsnsBillBatchBakeSchema();
        tRIBsnsBillBatchBakeSchema.setSchema(mResultSet, 1);
        tRIBsnsBillBatchBakeSet.add(tRIBsnsBillBatchBakeSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tRIBsnsBillBatchBakeSchema = new RIBsnsBillBatchBakeSchema();
                tRIBsnsBillBatchBakeSchema.setSchema(mResultSet, 1);
                tRIBsnsBillBatchBakeSet.add(tRIBsnsBillBatchBakeSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "RIBsnsBillBatchBakeDB";
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
    return tRIBsnsBillBatchBakeSet;
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
            tError.moduleName = "RIBsnsBillBatchBakeDB";
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
        tError.moduleName = "RIBsnsBillBatchBakeDB";
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
            tError.moduleName = "RIBsnsBillBatchBakeDB";
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
        tError.moduleName = "RIBsnsBillBatchBakeDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}