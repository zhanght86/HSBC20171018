/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMPolDutyEdorCalSchema;
import com.sinosoft.lis.vschema.PD_LMPolDutyEdorCalSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMPolDutyEdorCalDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMPolDutyEdorCalDB extends PD_LMPolDutyEdorCalSchema
{
private static Logger logger = Logger.getLogger(PD_LMPolDutyEdorCalDB.class);

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
	public PD_LMPolDutyEdorCalDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMPolDutyEdorCal" );
		mflag = true;
	}

	public PD_LMPolDutyEdorCalDB()
	{
		con = null;
		db = new DBOper( "PD_LMPolDutyEdorCal" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMPolDutyEdorCalSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMPolDutyEdorCalSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMPolDutyEdorCal WHERE  RiskCode = ? AND DutyCode = ? AND EdorType = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getDutyCode());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMPolDutyEdorCal");
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
		SQLString sqlObj = new SQLString("PD_LMPolDutyEdorCal");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMPolDutyEdorCal SET  RiskCode = ? , DutyCode = ? , EdorType = ? , CalMode = ? , ChgPremCalCode = ? , InterestCalCode = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  RiskCode = ? AND DutyCode = ? AND EdorType = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getDutyCode());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorType());
			}
			if(this.getCalMode() == null || this.getCalMode().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getCalMode());
			}
			if(this.getChgPremCalCode() == null || this.getChgPremCalCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getChgPremCalCode());
			}
			if(this.getInterestCalCode() == null || this.getInterestCalCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInterestCalCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStandbyflag2());
			}
			pstmt.setInt(14, this.getStandbyflag3());
			pstmt.setInt(15, this.getStandbyflag4());
			pstmt.setDouble(16, this.getStandbyflag5());
			pstmt.setDouble(17, this.getStandbyflag6());
			// set where condition
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getDutyCode());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getEdorType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
		SQLString sqlObj = new SQLString("PD_LMPolDutyEdorCal");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMPolDutyEdorCal(RiskCode ,DutyCode ,EdorType ,CalMode ,ChgPremCalCode ,InterestCalCode ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getDutyCode());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorType());
			}
			if(this.getCalMode() == null || this.getCalMode().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getCalMode());
			}
			if(this.getChgPremCalCode() == null || this.getChgPremCalCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getChgPremCalCode());
			}
			if(this.getInterestCalCode() == null || this.getInterestCalCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInterestCalCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStandbyflag2());
			}
			pstmt.setInt(14, this.getStandbyflag3());
			pstmt.setInt(15, this.getStandbyflag4());
			pstmt.setDouble(16, this.getStandbyflag5());
			pstmt.setDouble(17, this.getStandbyflag6());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMPolDutyEdorCal WHERE  RiskCode = ? AND DutyCode = ? AND EdorType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getDutyCode());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEdorType());
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
					tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

	public PD_LMPolDutyEdorCalSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LMPolDutyEdorCalSet aPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LMPolDutyEdorCal");
			PD_LMPolDutyEdorCalSchema aSchema = this.getSchema();
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
				PD_LMPolDutyEdorCalSchema s1 = new PD_LMPolDutyEdorCalSchema();
				s1.setSchema(rs,i);
				aPD_LMPolDutyEdorCalSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

		return aPD_LMPolDutyEdorCalSet;

	}

	public PD_LMPolDutyEdorCalSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMPolDutyEdorCalSet aPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();

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
				PD_LMPolDutyEdorCalSchema s1 = new PD_LMPolDutyEdorCalSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMPolDutyEdorCalDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMPolDutyEdorCalSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

		return aPD_LMPolDutyEdorCalSet;
	}

	public PD_LMPolDutyEdorCalSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LMPolDutyEdorCalSet aPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LMPolDutyEdorCal");
			PD_LMPolDutyEdorCalSchema aSchema = this.getSchema();
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

				PD_LMPolDutyEdorCalSchema s1 = new PD_LMPolDutyEdorCalSchema();
				s1.setSchema(rs,i);
				aPD_LMPolDutyEdorCalSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

		return aPD_LMPolDutyEdorCalSet;

	}

	public PD_LMPolDutyEdorCalSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMPolDutyEdorCalSet aPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();

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

				PD_LMPolDutyEdorCalSchema s1 = new PD_LMPolDutyEdorCalSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMPolDutyEdorCalDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMPolDutyEdorCalSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

		return aPD_LMPolDutyEdorCalSet;
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
			SQLString sqlObj = new SQLString("PD_LMPolDutyEdorCal");
			PD_LMPolDutyEdorCalSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMPolDutyEdorCal " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
 * @return PD_LMPolDutyEdorCalSet
 */
public PD_LMPolDutyEdorCalSet getData()
{
    int tCount = 0;
    PD_LMPolDutyEdorCalSet tPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();
    PD_LMPolDutyEdorCalSchema tPD_LMPolDutyEdorCalSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMPolDutyEdorCalSchema = new PD_LMPolDutyEdorCalSchema();
        tPD_LMPolDutyEdorCalSchema.setSchema(mResultSet, 1);
        tPD_LMPolDutyEdorCalSet.add(tPD_LMPolDutyEdorCalSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMPolDutyEdorCalSchema = new PD_LMPolDutyEdorCalSchema();
                tPD_LMPolDutyEdorCalSchema.setSchema(mResultSet, 1);
                tPD_LMPolDutyEdorCalSet.add(tPD_LMPolDutyEdorCalSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
    return tPD_LMPolDutyEdorCalSet;
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
            tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
            tError.moduleName = "PD_LMPolDutyEdorCalDB";
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
        tError.moduleName = "PD_LMPolDutyEdorCalDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public PD_LMPolDutyEdorCalSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LMPolDutyEdorCalSet aPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();

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
				PD_LMPolDutyEdorCalSchema s1 = new PD_LMPolDutyEdorCalSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMPolDutyEdorCalDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMPolDutyEdorCalSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

		return aPD_LMPolDutyEdorCalSet;
	}

	public PD_LMPolDutyEdorCalSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LMPolDutyEdorCalSet aPD_LMPolDutyEdorCalSet = new PD_LMPolDutyEdorCalSet();

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

				PD_LMPolDutyEdorCalSchema s1 = new PD_LMPolDutyEdorCalSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMPolDutyEdorCalDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMPolDutyEdorCalSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMPolDutyEdorCalDB";
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

		return aPD_LMPolDutyEdorCalSet; 
	}

}
