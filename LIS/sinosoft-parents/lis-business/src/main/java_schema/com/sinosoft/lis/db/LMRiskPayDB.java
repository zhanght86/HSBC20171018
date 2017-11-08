/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMRiskPaySchema;
import com.sinosoft.lis.vschema.LMRiskPaySet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMRiskPayDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskPayDB extends LMRiskPaySchema
{
private static Logger logger = Logger.getLogger(LMRiskPayDB.class);

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
	public LMRiskPayDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LMRiskPay" );
		mflag = true;
	}

	public LMRiskPayDB()
	{
		con = null;
		db = new DBOper( "LMRiskPay" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LMRiskPaySchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LMRiskPaySchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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
			pstmt = con.prepareStatement("DELETE FROM LMRiskPay WHERE  RiskCode = ? AND RiskVer = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskVer());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskPay");
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
		SQLString sqlObj = new SQLString("LMRiskPay");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LMRiskPay SET  RiskCode = ? , RiskVer = ? , RiskName = ? , UrgePayFlag = ? , ChargeType = ? , CutPayIntv = ? , PayAvoidType = ? , ChargeAndPrem = ? , BalaDateType = ? , PayAvoidFlag = ? , PayAndRevEffe = ? , GracePeriod = ? , GracePeriodUnit = ? , GraceDateCalMode = ? , GraceCalCode = ? , OverdueDeal = ? WHERE  RiskCode = ? AND RiskVer = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskVer());
			}
			if(this.getRiskName() == null || this.getRiskName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskName());
			}
			if(this.getUrgePayFlag() == null || this.getUrgePayFlag().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getUrgePayFlag());
			}
			if(this.getChargeType() == null || this.getChargeType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getChargeType());
			}
			if(this.getCutPayIntv() == null || this.getCutPayIntv().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getCutPayIntv());
			}
			if(this.getPayAvoidType() == null || this.getPayAvoidType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getPayAvoidType());
			}
			if(this.getChargeAndPrem() == null || this.getChargeAndPrem().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getChargeAndPrem());
			}
			if(this.getBalaDateType() == null || this.getBalaDateType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getBalaDateType());
			}
			if(this.getPayAvoidFlag() == null || this.getPayAvoidFlag().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getPayAvoidFlag());
			}
			if(this.getPayAndRevEffe() == null || this.getPayAndRevEffe().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getPayAndRevEffe());
			}
			pstmt.setInt(12, this.getGracePeriod());
			if(this.getGracePeriodUnit() == null || this.getGracePeriodUnit().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getGracePeriodUnit());
			}
			if(this.getGraceDateCalMode() == null || this.getGraceDateCalMode().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getGraceDateCalMode());
			}
			if(this.getGraceCalCode() == null || this.getGraceCalCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGraceCalCode());
			}
			if(this.getOverdueDeal() == null || this.getOverdueDeal().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getOverdueDeal());
			}
			// set where condition
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskVer());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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
		SQLString sqlObj = new SQLString("LMRiskPay");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LMRiskPay(RiskCode ,RiskVer ,RiskName ,UrgePayFlag ,ChargeType ,CutPayIntv ,PayAvoidType ,ChargeAndPrem ,BalaDateType ,PayAvoidFlag ,PayAndRevEffe ,GracePeriod ,GracePeriodUnit ,GraceDateCalMode ,GraceCalCode ,OverdueDeal) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskVer());
			}
			if(this.getRiskName() == null || this.getRiskName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskName());
			}
			if(this.getUrgePayFlag() == null || this.getUrgePayFlag().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getUrgePayFlag());
			}
			if(this.getChargeType() == null || this.getChargeType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getChargeType());
			}
			if(this.getCutPayIntv() == null || this.getCutPayIntv().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getCutPayIntv());
			}
			if(this.getPayAvoidType() == null || this.getPayAvoidType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getPayAvoidType());
			}
			if(this.getChargeAndPrem() == null || this.getChargeAndPrem().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getChargeAndPrem());
			}
			if(this.getBalaDateType() == null || this.getBalaDateType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getBalaDateType());
			}
			if(this.getPayAvoidFlag() == null || this.getPayAvoidFlag().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getPayAvoidFlag());
			}
			if(this.getPayAndRevEffe() == null || this.getPayAndRevEffe().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getPayAndRevEffe());
			}
			pstmt.setInt(12, this.getGracePeriod());
			if(this.getGracePeriodUnit() == null || this.getGracePeriodUnit().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getGracePeriodUnit());
			}
			if(this.getGraceDateCalMode() == null || this.getGraceDateCalMode().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getGraceDateCalMode());
			}
			if(this.getGraceCalCode() == null || this.getGraceCalCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGraceCalCode());
			}
			if(this.getOverdueDeal() == null || this.getOverdueDeal().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getOverdueDeal());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LMRiskPay WHERE  RiskCode = ? AND RiskVer = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskVer());
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
					tError.moduleName = "LMRiskPayDB";
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
			tError.moduleName = "LMRiskPayDB";
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

	public LMRiskPaySet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LMRiskPaySet aLMRiskPaySet = new LMRiskPaySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LMRiskPay");
			LMRiskPaySchema aSchema = this.getSchema();
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
				LMRiskPaySchema s1 = new LMRiskPaySchema();
				s1.setSchema(rs,i);
				aLMRiskPaySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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

		return aLMRiskPaySet;

	}

	public LMRiskPaySet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMRiskPaySet aLMRiskPaySet = new LMRiskPaySet();

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
				LMRiskPaySchema s1 = new LMRiskPaySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskPayDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskPaySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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

		return aLMRiskPaySet;
	}

	public LMRiskPaySet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LMRiskPaySet aLMRiskPaySet = new LMRiskPaySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LMRiskPay");
			LMRiskPaySchema aSchema = this.getSchema();
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

				LMRiskPaySchema s1 = new LMRiskPaySchema();
				s1.setSchema(rs,i);
				aLMRiskPaySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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

		return aLMRiskPaySet;

	}

	public LMRiskPaySet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMRiskPaySet aLMRiskPaySet = new LMRiskPaySet();

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

				LMRiskPaySchema s1 = new LMRiskPaySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskPayDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskPaySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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

		return aLMRiskPaySet;
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
			SQLString sqlObj = new SQLString("LMRiskPay");
			LMRiskPaySchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LMRiskPay " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LMRiskPayDB";
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
			tError.moduleName = "LMRiskPayDB";
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
        tError.moduleName = "LMRiskPayDB";
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
        tError.moduleName = "LMRiskPayDB";
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
        tError.moduleName = "LMRiskPayDB";
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
        tError.moduleName = "LMRiskPayDB";
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
 * @return LMRiskPaySet
 */
public LMRiskPaySet getData()
{
    int tCount = 0;
    LMRiskPaySet tLMRiskPaySet = new LMRiskPaySet();
    LMRiskPaySchema tLMRiskPaySchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LMRiskPayDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLMRiskPaySchema = new LMRiskPaySchema();
        tLMRiskPaySchema.setSchema(mResultSet, 1);
        tLMRiskPaySet.add(tLMRiskPaySchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLMRiskPaySchema = new LMRiskPaySchema();
                tLMRiskPaySchema.setSchema(mResultSet, 1);
                tLMRiskPaySet.add(tLMRiskPaySchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LMRiskPayDB";
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
    return tLMRiskPaySet;
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
            tError.moduleName = "LMRiskPayDB";
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
        tError.moduleName = "LMRiskPayDB";
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
            tError.moduleName = "LMRiskPayDB";
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
        tError.moduleName = "LMRiskPayDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LMRiskPaySet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LMRiskPaySet aLMRiskPaySet = new LMRiskPaySet();

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
				LMRiskPaySchema s1 = new LMRiskPaySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskPayDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskPaySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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

		return aLMRiskPaySet;
	}

	public LMRiskPaySet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LMRiskPaySet aLMRiskPaySet = new LMRiskPaySet();

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

				LMRiskPaySchema s1 = new LMRiskPaySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskPayDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskPaySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPayDB";
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

		return aLMRiskPaySet; 
	}

}
