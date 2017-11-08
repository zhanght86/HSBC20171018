/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.PD_FIELDMAP2Schema;
import com.sinosoft.lis.vschema.PD_FIELDMAP2Set;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_FIELDMAP2DB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台
 */
public class PD_FIELDMAP2DB extends PD_FIELDMAP2Schema
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
	public PD_FIELDMAP2DB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_FIELDMAP2" );
		mflag = true;
	}

	public PD_FIELDMAP2DB()
	{
		con = null;
		db = new DBOper( "PD_FIELDMAP2" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_FIELDMAP2Schema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_FIELDMAP2Schema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_FIELDMAP2 WHERE  TABLECODE1 = ? AND TABLECODE2 = ? AND FIELDCODE1 = ? AND FIELDCODE2 = ?");
			if(this.getTABLECODE1() == null || this.getTABLECODE1().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTABLECODE1());
			}
			if(this.getTABLECODE2() == null || this.getTABLECODE2().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTABLECODE2());
			}
			if(this.getFIELDCODE1() == null || this.getFIELDCODE1().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFIELDCODE1());
			}
			if(this.getFIELDCODE2() == null || this.getFIELDCODE2().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFIELDCODE2());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_FIELDMAP2");
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
		SQLString sqlObj = new SQLString("PD_FIELDMAP2");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_FIELDMAP2 SET  TABLECODE1 = ? , TABLECODE2 = ? , FIELDCODE1 = ? , FIELDCODE2 = ? , FIELDTYPE1 = ? , ISPDTABLE1PRIMARY = ? , FIELDTYPE2 = ? , ISTABLE2PRIMARY = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? , STANDBYFLAG1 = ? , STANDBYFLAG2 = ? , STANDBYFLAG3 = ? , STANDBYFLAG4 = ? , STANDBYFLAG5 = ? , STANDBYFLAG6 = ? WHERE  TABLECODE1 = ? AND TABLECODE2 = ? AND FIELDCODE1 = ? AND FIELDCODE2 = ?");
			if(this.getTABLECODE1() == null || this.getTABLECODE1().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTABLECODE1());
			}
			if(this.getTABLECODE2() == null || this.getTABLECODE2().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTABLECODE2());
			}
			if(this.getFIELDCODE1() == null || this.getFIELDCODE1().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFIELDCODE1());
			}
			if(this.getFIELDCODE2() == null || this.getFIELDCODE2().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFIELDCODE2());
			}
			if(this.getFIELDTYPE1() == null || this.getFIELDTYPE1().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFIELDTYPE1());
			}
			if(this.getISPDTABLE1PRIMARY() == null || this.getISPDTABLE1PRIMARY().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getISPDTABLE1PRIMARY());
			}
			if(this.getFIELDTYPE2() == null || this.getFIELDTYPE2().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getFIELDTYPE2());
			}
			if(this.getISTABLE2PRIMARY() == null || this.getISTABLE2PRIMARY().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getISTABLE2PRIMARY());
			}
			if(this.getOPERATOR() == null || this.getOPERATOR().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getOPERATOR());
			}
			if(this.getMAKEDATE() == null || this.getMAKEDATE().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getMAKEDATE()));
			}
			if(this.getMAKETIME() == null || this.getMAKETIME().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getMAKETIME());
			}
			if(this.getMODIFYDATE() == null || this.getMODIFYDATE().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getMODIFYDATE()));
			}
			if(this.getMODIFYTIME() == null || this.getMODIFYTIME().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getMODIFYTIME());
			}
			if(this.getSTANDBYFLAG1() == null || this.getSTANDBYFLAG1().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getSTANDBYFLAG1());
			}
			if(this.getSTANDBYFLAG2() == null || this.getSTANDBYFLAG2().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getSTANDBYFLAG2());
			}
			pstmt.setInt(16, this.getSTANDBYFLAG3());
			pstmt.setInt(17, this.getSTANDBYFLAG4());
			pstmt.setDouble(18, this.getSTANDBYFLAG5());
			pstmt.setDouble(19, this.getSTANDBYFLAG6());
			// set where condition
			if(this.getTABLECODE1() == null || this.getTABLECODE1().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getTABLECODE1());
			}
			if(this.getTABLECODE2() == null || this.getTABLECODE2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getTABLECODE2());
			}
			if(this.getFIELDCODE1() == null || this.getFIELDCODE1().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getFIELDCODE1());
			}
			if(this.getFIELDCODE2() == null || this.getFIELDCODE2().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getFIELDCODE2());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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
		SQLString sqlObj = new SQLString("PD_FIELDMAP2");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_FIELDMAP2 VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getTABLECODE1() == null || this.getTABLECODE1().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTABLECODE1());
			}
			if(this.getTABLECODE2() == null || this.getTABLECODE2().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTABLECODE2());
			}
			if(this.getFIELDCODE1() == null || this.getFIELDCODE1().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFIELDCODE1());
			}
			if(this.getFIELDCODE2() == null || this.getFIELDCODE2().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFIELDCODE2());
			}
			if(this.getFIELDTYPE1() == null || this.getFIELDTYPE1().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFIELDTYPE1());
			}
			if(this.getISPDTABLE1PRIMARY() == null || this.getISPDTABLE1PRIMARY().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getISPDTABLE1PRIMARY());
			}
			if(this.getFIELDTYPE2() == null || this.getFIELDTYPE2().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getFIELDTYPE2());
			}
			if(this.getISTABLE2PRIMARY() == null || this.getISTABLE2PRIMARY().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getISTABLE2PRIMARY());
			}
			if(this.getOPERATOR() == null || this.getOPERATOR().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getOPERATOR());
			}
			if(this.getMAKEDATE() == null || this.getMAKEDATE().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getMAKEDATE()));
			}
			if(this.getMAKETIME() == null || this.getMAKETIME().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getMAKETIME());
			}
			if(this.getMODIFYDATE() == null || this.getMODIFYDATE().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getMODIFYDATE()));
			}
			if(this.getMODIFYTIME() == null || this.getMODIFYTIME().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getMODIFYTIME());
			}
			if(this.getSTANDBYFLAG1() == null || this.getSTANDBYFLAG1().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getSTANDBYFLAG1());
			}
			if(this.getSTANDBYFLAG2() == null || this.getSTANDBYFLAG2().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getSTANDBYFLAG2());
			}
			pstmt.setInt(16, this.getSTANDBYFLAG3());
			pstmt.setInt(17, this.getSTANDBYFLAG4());
			pstmt.setDouble(18, this.getSTANDBYFLAG5());
			pstmt.setDouble(19, this.getSTANDBYFLAG6());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_FIELDMAP2 WHERE  TABLECODE1 = ? AND TABLECODE2 = ? AND FIELDCODE1 = ? AND FIELDCODE2 = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getTABLECODE1() == null || this.getTABLECODE1().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTABLECODE1());
			}
			if(this.getTABLECODE2() == null || this.getTABLECODE2().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getTABLECODE2());
			}
			if(this.getFIELDCODE1() == null || this.getFIELDCODE1().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFIELDCODE1());
			}
			if(this.getFIELDCODE2() == null || this.getFIELDCODE2().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFIELDCODE2());
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
					tError.moduleName = "PD_FIELDMAP2DB";
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
			tError.moduleName = "PD_FIELDMAP2DB";
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

	public PD_FIELDMAP2Set query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_FIELDMAP2Set aPD_FIELDMAP2Set = new PD_FIELDMAP2Set();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_FIELDMAP2");
			PD_FIELDMAP2Schema aSchema = this.getSchema();
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
				PD_FIELDMAP2Schema s1 = new PD_FIELDMAP2Schema();
				s1.setSchema(rs,i);
				aPD_FIELDMAP2Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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

		return aPD_FIELDMAP2Set;
	}

	public PD_FIELDMAP2Set executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_FIELDMAP2Set aPD_FIELDMAP2Set = new PD_FIELDMAP2Set();

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
				PD_FIELDMAP2Schema s1 = new PD_FIELDMAP2Schema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_FIELDMAP2DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_FIELDMAP2Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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

		return aPD_FIELDMAP2Set;
	}

	public PD_FIELDMAP2Set query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_FIELDMAP2Set aPD_FIELDMAP2Set = new PD_FIELDMAP2Set();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_FIELDMAP2");
			PD_FIELDMAP2Schema aSchema = this.getSchema();
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

				PD_FIELDMAP2Schema s1 = new PD_FIELDMAP2Schema();
				s1.setSchema(rs,i);
				aPD_FIELDMAP2Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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

		return aPD_FIELDMAP2Set;
	}

	public PD_FIELDMAP2Set executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_FIELDMAP2Set aPD_FIELDMAP2Set = new PD_FIELDMAP2Set();

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

				PD_FIELDMAP2Schema s1 = new PD_FIELDMAP2Schema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_FIELDMAP2DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_FIELDMAP2Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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

		return aPD_FIELDMAP2Set;
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
			SQLString sqlObj = new SQLString("PD_FIELDMAP2");
			PD_FIELDMAP2Schema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_FIELDMAP2 " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_FIELDMAP2DB";
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
			tError.moduleName = "PD_FIELDMAP2DB";
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
        tError.moduleName = "PD_FIELDMAP2DB";
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
        tError.moduleName = "PD_FIELDMAP2DB";
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
        tError.moduleName = "PD_FIELDMAP2DB";
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
        tError.moduleName = "PD_FIELDMAP2DB";
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
 * @return PD_FIELDMAP2Set
 */
public PD_FIELDMAP2Set getData()
{
    int tCount = 0;
    PD_FIELDMAP2Set tPD_FIELDMAP2Set = new PD_FIELDMAP2Set();
    PD_FIELDMAP2Schema tPD_FIELDMAP2Schema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_FIELDMAP2DB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_FIELDMAP2Schema = new PD_FIELDMAP2Schema();
        tPD_FIELDMAP2Schema.setSchema(mResultSet, 1);
        tPD_FIELDMAP2Set.add(tPD_FIELDMAP2Schema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_FIELDMAP2Schema = new PD_FIELDMAP2Schema();
                tPD_FIELDMAP2Schema.setSchema(mResultSet, 1);
                tPD_FIELDMAP2Set.add(tPD_FIELDMAP2Schema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_FIELDMAP2DB";
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
    return tPD_FIELDMAP2Set;
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
            tError.moduleName = "PD_FIELDMAP2DB";
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
        tError.moduleName = "PD_FIELDMAP2DB";
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
            tError.moduleName = "PD_FIELDMAP2DB";
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
        tError.moduleName = "PD_FIELDMAP2DB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public PD_FIELDMAP2Set executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_FIELDMAP2Set aPD_FIELDMAP2Set = new PD_FIELDMAP2Set();

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
				PD_FIELDMAP2Schema s1 = new PD_FIELDMAP2Schema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_FIELDMAP2DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_FIELDMAP2Set.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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

		return aPD_FIELDMAP2Set;
	}

	public PD_FIELDMAP2Set executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_FIELDMAP2Set aPD_FIELDMAP2Set = new PD_FIELDMAP2Set();

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

				PD_FIELDMAP2Schema s1 = new PD_FIELDMAP2Schema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_FIELDMAP2DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_FIELDMAP2Set.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2DB";
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

		return aPD_FIELDMAP2Set; 
	}

}
