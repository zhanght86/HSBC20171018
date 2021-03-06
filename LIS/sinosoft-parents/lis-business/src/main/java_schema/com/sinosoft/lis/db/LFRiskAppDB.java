/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LFRiskAppSchema;
import com.sinosoft.lis.vschema.LFRiskAppSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LFRiskAppDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LFRiskAppDB extends LFRiskAppSchema
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
	public LFRiskAppDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LFRiskApp" );
		mflag = true;
	}

	public LFRiskAppDB()
	{
		con = null;
		db = new DBOper( "LFRiskApp" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LFRiskAppSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LFRiskAppSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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
			pstmt = con.prepareStatement("DELETE FROM LFRiskApp WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getManageCom());
			}
			pstmt.setInt(3, this.getPayIntv());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSaleChnl());
			}
			if(this.getFirstPayFlag() == null || this.getFirstPayFlag().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFirstPayFlag());
			}
			if(this.getPersonPolFlag() == null || this.getPersonPolFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPersonPolFlag());
			}
			if(this.getReportDate() == null || this.getReportDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getReportDate()));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskApp");
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
		SQLString sqlObj = new SQLString("LFRiskApp");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LFRiskApp SET  RiskCode = ? , ManageCom = ? , PayIntv = ? , SaleChnl = ? , FirstPayFlag = ? , PersonPolFlag = ? , ReportDate = ? , Amnt = ? , AmntSum = ? , Prem = ? , PremSum = ? , InsuredCount = ? , InsuredCountSum = ? , PolCount = ? , PolCountSum = ? , CurYearAmnt = ? , CurYearAmntSum = ? , AllYearAmnt = ? , AllYearAmntSum = ? , CurYearPrem = ? , CurYearPremSum = ? , AllYearPrem = ? , AllYearPremSum = ? , CurYearInsured = ? , CurYearInsuredSum = ? , AllYearInsured = ? , AllYearInsuredSum = ? , CurYearPol = ? , CurYearPolSum = ? , AllYearPol = ? , AllYearPolSum = ? , MakeDate = ? , MakeTime = ? WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getManageCom());
			}
			pstmt.setInt(3, this.getPayIntv());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSaleChnl());
			}
			if(this.getFirstPayFlag() == null || this.getFirstPayFlag().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFirstPayFlag());
			}
			if(this.getPersonPolFlag() == null || this.getPersonPolFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPersonPolFlag());
			}
			if(this.getReportDate() == null || this.getReportDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getReportDate()));
			}
			pstmt.setDouble(8, this.getAmnt());
			pstmt.setDouble(9, this.getAmntSum());
			pstmt.setDouble(10, this.getPrem());
			pstmt.setDouble(11, this.getPremSum());
			pstmt.setInt(12, this.getInsuredCount());
			pstmt.setInt(13, this.getInsuredCountSum());
			pstmt.setInt(14, this.getPolCount());
			pstmt.setInt(15, this.getPolCountSum());
			pstmt.setDouble(16, this.getCurYearAmnt());
			pstmt.setDouble(17, this.getCurYearAmntSum());
			pstmt.setDouble(18, this.getAllYearAmnt());
			pstmt.setDouble(19, this.getAllYearAmntSum());
			pstmt.setDouble(20, this.getCurYearPrem());
			pstmt.setDouble(21, this.getCurYearPremSum());
			pstmt.setDouble(22, this.getAllYearPrem());
			pstmt.setDouble(23, this.getAllYearPremSum());
			pstmt.setInt(24, this.getCurYearInsured());
			pstmt.setInt(25, this.getCurYearInsuredSum());
			pstmt.setInt(26, this.getAllYearInsured());
			pstmt.setInt(27, this.getAllYearInsuredSum());
			pstmt.setInt(28, this.getCurYearPol());
			pstmt.setInt(29, this.getCurYearPolSum());
			pstmt.setInt(30, this.getAllYearPol());
			pstmt.setInt(31, this.getAllYearPolSum());
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getMakeTime());
			}
			// set where condition
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRiskCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getManageCom());
			}
			pstmt.setInt(36, this.getPayIntv());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getSaleChnl());
			}
			if(this.getFirstPayFlag() == null || this.getFirstPayFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getFirstPayFlag());
			}
			if(this.getPersonPolFlag() == null || this.getPersonPolFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getPersonPolFlag());
			}
			if(this.getReportDate() == null || this.getReportDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getReportDate()));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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
		SQLString sqlObj = new SQLString("LFRiskApp");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LFRiskApp VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getManageCom());
			}
			pstmt.setInt(3, this.getPayIntv());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSaleChnl());
			}
			if(this.getFirstPayFlag() == null || this.getFirstPayFlag().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFirstPayFlag());
			}
			if(this.getPersonPolFlag() == null || this.getPersonPolFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPersonPolFlag());
			}
			if(this.getReportDate() == null || this.getReportDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getReportDate()));
			}
			pstmt.setDouble(8, this.getAmnt());
			pstmt.setDouble(9, this.getAmntSum());
			pstmt.setDouble(10, this.getPrem());
			pstmt.setDouble(11, this.getPremSum());
			pstmt.setInt(12, this.getInsuredCount());
			pstmt.setInt(13, this.getInsuredCountSum());
			pstmt.setInt(14, this.getPolCount());
			pstmt.setInt(15, this.getPolCountSum());
			pstmt.setDouble(16, this.getCurYearAmnt());
			pstmt.setDouble(17, this.getCurYearAmntSum());
			pstmt.setDouble(18, this.getAllYearAmnt());
			pstmt.setDouble(19, this.getAllYearAmntSum());
			pstmt.setDouble(20, this.getCurYearPrem());
			pstmt.setDouble(21, this.getCurYearPremSum());
			pstmt.setDouble(22, this.getAllYearPrem());
			pstmt.setDouble(23, this.getAllYearPremSum());
			pstmt.setInt(24, this.getCurYearInsured());
			pstmt.setInt(25, this.getCurYearInsuredSum());
			pstmt.setInt(26, this.getAllYearInsured());
			pstmt.setInt(27, this.getAllYearInsuredSum());
			pstmt.setInt(28, this.getCurYearPol());
			pstmt.setInt(29, this.getCurYearPolSum());
			pstmt.setInt(30, this.getAllYearPol());
			pstmt.setInt(31, this.getAllYearPolSum());
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getMakeTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LFRiskApp WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getManageCom());
			}
			pstmt.setInt(3, this.getPayIntv());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSaleChnl());
			}
			if(this.getFirstPayFlag() == null || this.getFirstPayFlag().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFirstPayFlag());
			}
			if(this.getPersonPolFlag() == null || this.getPersonPolFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPersonPolFlag());
			}
			if(this.getReportDate() == null || this.getReportDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getReportDate()));
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
					tError.moduleName = "LFRiskAppDB";
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
			tError.moduleName = "LFRiskAppDB";
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

	public LFRiskAppSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LFRiskAppSet aLFRiskAppSet = new LFRiskAppSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LFRiskApp");
			LFRiskAppSchema aSchema = this.getSchema();
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
				LFRiskAppSchema s1 = new LFRiskAppSchema();
				s1.setSchema(rs,i);
				aLFRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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

		return aLFRiskAppSet;
	}

	public LFRiskAppSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LFRiskAppSet aLFRiskAppSet = new LFRiskAppSet();

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
				LFRiskAppSchema s1 = new LFRiskAppSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLFRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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

		return aLFRiskAppSet;
	}

	public LFRiskAppSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LFRiskAppSet aLFRiskAppSet = new LFRiskAppSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LFRiskApp");
			LFRiskAppSchema aSchema = this.getSchema();
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

				LFRiskAppSchema s1 = new LFRiskAppSchema();
				s1.setSchema(rs,i);
				aLFRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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

		return aLFRiskAppSet;
	}

	public LFRiskAppSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LFRiskAppSet aLFRiskAppSet = new LFRiskAppSet();

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

				LFRiskAppSchema s1 = new LFRiskAppSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLFRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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

		return aLFRiskAppSet;
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
			SQLString sqlObj = new SQLString("LFRiskApp");
			LFRiskAppSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LFRiskApp " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LFRiskAppDB";
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
			tError.moduleName = "LFRiskAppDB";
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
        tError.moduleName = "LFRiskAppDB";
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
        tError.moduleName = "LFRiskAppDB";
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
        tError.moduleName = "LFRiskAppDB";
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
        tError.moduleName = "LFRiskAppDB";
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
 * @return LFRiskAppSet
 */
public LFRiskAppSet getData()
{
    int tCount = 0;
    LFRiskAppSet tLFRiskAppSet = new LFRiskAppSet();
    LFRiskAppSchema tLFRiskAppSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LFRiskAppDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLFRiskAppSchema = new LFRiskAppSchema();
        tLFRiskAppSchema.setSchema(mResultSet, 1);
        tLFRiskAppSet.add(tLFRiskAppSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLFRiskAppSchema = new LFRiskAppSchema();
                tLFRiskAppSchema.setSchema(mResultSet, 1);
                tLFRiskAppSet.add(tLFRiskAppSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LFRiskAppDB";
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
    return tLFRiskAppSet;
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
            tError.moduleName = "LFRiskAppDB";
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
        tError.moduleName = "LFRiskAppDB";
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
            tError.moduleName = "LFRiskAppDB";
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
        tError.moduleName = "LFRiskAppDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LFRiskAppSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LFRiskAppSet aLFRiskAppSet = new LFRiskAppSet();

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
				LFRiskAppSchema s1 = new LFRiskAppSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLFRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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

		return aLFRiskAppSet;
	}

	public LFRiskAppSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LFRiskAppSet aLFRiskAppSet = new LFRiskAppSet();

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

				LFRiskAppSchema s1 = new LFRiskAppSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LFRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLFRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppDB";
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

		return aLFRiskAppSet; 
	}

}
