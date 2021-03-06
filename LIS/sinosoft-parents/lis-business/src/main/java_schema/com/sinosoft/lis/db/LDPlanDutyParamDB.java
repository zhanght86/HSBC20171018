/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDPlanDutyParamSchema;
import com.sinosoft.lis.vschema.LDPlanDutyParamSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDPlanDutyParamDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDPlanDutyParamDB extends LDPlanDutyParamSchema
{
private static Logger logger = Logger.getLogger(LDPlanDutyParamDB.class);

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
	public LDPlanDutyParamDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LDPlanDutyParam" );
		mflag = true;
	}

	public LDPlanDutyParamDB()
	{
		con = null;
		db = new DBOper( "LDPlanDutyParam" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LDPlanDutyParamSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LDPlanDutyParamSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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
			pstmt = con.prepareStatement("DELETE FROM LDPlanDutyParam WHERE  MainRiskCode = ? AND RiskCode = ? AND ContPlanCode = ? AND DutyCode = ? AND CalFactor = ? AND PlanType = ?");
			if(this.getMainRiskCode() == null || this.getMainRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getMainRiskCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskCode());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContPlanCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getDutyCode());
			}
			if(this.getCalFactor() == null || this.getCalFactor().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCalFactor());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, StrTool.space1(this.getPlanType(), 1));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDPlanDutyParam");
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
		SQLString sqlObj = new SQLString("LDPlanDutyParam");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LDPlanDutyParam SET  MainRiskCode = ? , MainRiskVersion = ? , RiskCode = ? , RiskVersion = ? , ContPlanCode = ? , ContPlanName = ? , DutyCode = ? , CalFactor = ? , CalFactorType = ? , CalFactorValue = ? , Remark = ? , PlanType = ? , PortfolioFlag = ? WHERE  MainRiskCode = ? AND RiskCode = ? AND ContPlanCode = ? AND DutyCode = ? AND CalFactor = ? AND PlanType = ?");
			if(this.getMainRiskCode() == null || this.getMainRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getMainRiskCode());
			}
			if(this.getMainRiskVersion() == null || this.getMainRiskVersion().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getMainRiskVersion());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRiskVersion());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContPlanCode());
			}
			if(this.getContPlanName() == null || this.getContPlanName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getContPlanName());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDutyCode());
			}
			if(this.getCalFactor() == null || this.getCalFactor().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCalFactor());
			}
			if(this.getCalFactorType() == null || this.getCalFactorType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCalFactorType());
			}
			if(this.getCalFactorValue() == null || this.getCalFactorValue().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCalFactorValue());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRemark());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPlanType());
			}
			if(this.getPortfolioFlag() == null || this.getPortfolioFlag().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getPortfolioFlag());
			}
			// set where condition
			if(this.getMainRiskCode() == null || this.getMainRiskCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getMainRiskCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRiskCode());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContPlanCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getDutyCode());
			}
			if(this.getCalFactor() == null || this.getCalFactor().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCalFactor());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, StrTool.space1(this.getPlanType(), 1));
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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
		SQLString sqlObj = new SQLString("LDPlanDutyParam");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LDPlanDutyParam(MainRiskCode ,MainRiskVersion ,RiskCode ,RiskVersion ,ContPlanCode ,ContPlanName ,DutyCode ,CalFactor ,CalFactorType ,CalFactorValue ,Remark ,PlanType ,PortfolioFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getMainRiskCode() == null || this.getMainRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getMainRiskCode());
			}
			if(this.getMainRiskVersion() == null || this.getMainRiskVersion().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getMainRiskVersion());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRiskVersion());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContPlanCode());
			}
			if(this.getContPlanName() == null || this.getContPlanName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getContPlanName());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDutyCode());
			}
			if(this.getCalFactor() == null || this.getCalFactor().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCalFactor());
			}
			if(this.getCalFactorType() == null || this.getCalFactorType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCalFactorType());
			}
			if(this.getCalFactorValue() == null || this.getCalFactorValue().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCalFactorValue());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRemark());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPlanType());
			}
			if(this.getPortfolioFlag() == null || this.getPortfolioFlag().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getPortfolioFlag());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LDPlanDutyParam WHERE  MainRiskCode = ? AND RiskCode = ? AND ContPlanCode = ? AND DutyCode = ? AND CalFactor = ? AND PlanType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getMainRiskCode() == null || this.getMainRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getMainRiskCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskCode());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContPlanCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getDutyCode());
			}
			if(this.getCalFactor() == null || this.getCalFactor().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCalFactor());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, StrTool.space1(this.getPlanType(), 1));
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
					tError.moduleName = "LDPlanDutyParamDB";
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
			tError.moduleName = "LDPlanDutyParamDB";
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

	public LDPlanDutyParamSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LDPlanDutyParamSet aLDPlanDutyParamSet = new LDPlanDutyParamSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDPlanDutyParam");
			LDPlanDutyParamSchema aSchema = this.getSchema();
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
				LDPlanDutyParamSchema s1 = new LDPlanDutyParamSchema();
				s1.setSchema(rs,i);
				aLDPlanDutyParamSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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

		return aLDPlanDutyParamSet;

	}

	public LDPlanDutyParamSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDPlanDutyParamSet aLDPlanDutyParamSet = new LDPlanDutyParamSet();

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
				LDPlanDutyParamSchema s1 = new LDPlanDutyParamSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPlanDutyParamDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPlanDutyParamSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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

		return aLDPlanDutyParamSet;
	}

	public LDPlanDutyParamSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LDPlanDutyParamSet aLDPlanDutyParamSet = new LDPlanDutyParamSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDPlanDutyParam");
			LDPlanDutyParamSchema aSchema = this.getSchema();
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

				LDPlanDutyParamSchema s1 = new LDPlanDutyParamSchema();
				s1.setSchema(rs,i);
				aLDPlanDutyParamSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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

		return aLDPlanDutyParamSet;

	}

	public LDPlanDutyParamSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDPlanDutyParamSet aLDPlanDutyParamSet = new LDPlanDutyParamSet();

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

				LDPlanDutyParamSchema s1 = new LDPlanDutyParamSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPlanDutyParamDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPlanDutyParamSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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

		return aLDPlanDutyParamSet;
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
			SQLString sqlObj = new SQLString("LDPlanDutyParam");
			LDPlanDutyParamSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LDPlanDutyParam " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDPlanDutyParamDB";
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
			tError.moduleName = "LDPlanDutyParamDB";
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
        tError.moduleName = "LDPlanDutyParamDB";
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
        tError.moduleName = "LDPlanDutyParamDB";
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
        tError.moduleName = "LDPlanDutyParamDB";
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
        tError.moduleName = "LDPlanDutyParamDB";
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
 * @return LDPlanDutyParamSet
 */
public LDPlanDutyParamSet getData()
{
    int tCount = 0;
    LDPlanDutyParamSet tLDPlanDutyParamSet = new LDPlanDutyParamSet();
    LDPlanDutyParamSchema tLDPlanDutyParamSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LDPlanDutyParamDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
        tLDPlanDutyParamSchema.setSchema(mResultSet, 1);
        tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLDPlanDutyParamSchema = new LDPlanDutyParamSchema();
                tLDPlanDutyParamSchema.setSchema(mResultSet, 1);
                tLDPlanDutyParamSet.add(tLDPlanDutyParamSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LDPlanDutyParamDB";
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
    return tLDPlanDutyParamSet;
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
            tError.moduleName = "LDPlanDutyParamDB";
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
        tError.moduleName = "LDPlanDutyParamDB";
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
            tError.moduleName = "LDPlanDutyParamDB";
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
        tError.moduleName = "LDPlanDutyParamDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LDPlanDutyParamSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDPlanDutyParamSet aLDPlanDutyParamSet = new LDPlanDutyParamSet();

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
				LDPlanDutyParamSchema s1 = new LDPlanDutyParamSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPlanDutyParamDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPlanDutyParamSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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

		return aLDPlanDutyParamSet;
	}

	public LDPlanDutyParamSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDPlanDutyParamSet aLDPlanDutyParamSet = new LDPlanDutyParamSet();

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

				LDPlanDutyParamSchema s1 = new LDPlanDutyParamSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPlanDutyParamDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPlanDutyParamSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanDutyParamDB";
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

		return aLDPlanDutyParamSet; 
	}

}
