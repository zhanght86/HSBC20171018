

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;

import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.vschema.LMDutySet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMDutyDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LMDutyDB extends LMDutySchema
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
	public LMDutyDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LMDuty" );
		mflag = true;
	}

	public LMDutyDB()
	{
		con = null;
		db = new DBOper( "LMDuty" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LMDutySchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LMDutySchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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
			pstmt = con.prepareStatement("DELETE FROM LMDuty WHERE  DutyCode = ?");
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDuty");
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
		SQLString sqlObj = new SQLString("LMDuty");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LMDuty SET  DutyCode = ? , DutyName = ? , PayEndYear = ? , PayEndYearFlag = ? , PayEndDateCalRef = ? , PayEndDateCalMode = ? , GetYear = ? , GetYearFlag = ? , InsuYear = ? , InsuYearFlag = ? , AcciYear = ? , AcciYearFlag = ? , CalMode = ? , PayEndYearRela = ? , GetYearRela = ? , InsuYearRela = ? , BasicCalCode = ? , RiskCalCode = ? , AmntFlag = ? , VPU = ? , AddFeeFlag = ? , EndDateCalMode = ? , AddAmntFlag = ? , PCalMode = ? WHERE  DutyCode = ?");
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getDutyCode());
			}
			if(this.getDutyName() == null || this.getDutyName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getDutyName());
			}
			pstmt.setInt(3, this.getPayEndYear());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getPayEndYearFlag());
			}
			if(this.getPayEndDateCalRef() == null || this.getPayEndDateCalRef().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getPayEndDateCalRef());
			}
			if(this.getPayEndDateCalMode() == null || this.getPayEndDateCalMode().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getPayEndDateCalMode());
			}
			pstmt.setInt(7, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getGetYearFlag());
			}
			pstmt.setInt(9, this.getInsuYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getInsuYearFlag());
			}
			pstmt.setInt(11, this.getAcciYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getAcciYearFlag());
			}
			if(this.getCalMode() == null || this.getCalMode().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getCalMode());
			}
			if(this.getPayEndYearRela() == null || this.getPayEndYearRela().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getPayEndYearRela());
			}
			if(this.getGetYearRela() == null || this.getGetYearRela().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getGetYearRela());
			}
			if(this.getInsuYearRela() == null || this.getInsuYearRela().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getInsuYearRela());
			}
			if(this.getBasicCalCode() == null || this.getBasicCalCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getBasicCalCode());
			}
			if(this.getRiskCalCode() == null || this.getRiskCalCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskCalCode());
			}
			if(this.getAmntFlag() == null || this.getAmntFlag().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getAmntFlag());
			}
			pstmt.setDouble(20, this.getVPU());
			if(this.getAddFeeFlag() == null || this.getAddFeeFlag().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAddFeeFlag());
			}
			if(this.getEndDateCalMode() == null || this.getEndDateCalMode().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getEndDateCalMode());
			}
			if(this.getAddAmntFlag() == null || this.getAddAmntFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAddAmntFlag());
			}
			if(this.getPCalMode() == null || this.getPCalMode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPCalMode());
			}
			// set where condition
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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
		SQLString sqlObj = new SQLString("LMDuty");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LMDuty(DutyCode ,DutyName ,PayEndYear ,PayEndYearFlag ,PayEndDateCalRef ,PayEndDateCalMode ,GetYear ,GetYearFlag ,InsuYear ,InsuYearFlag ,AcciYear ,AcciYearFlag ,CalMode ,PayEndYearRela ,GetYearRela ,InsuYearRela ,BasicCalCode ,RiskCalCode ,AmntFlag ,VPU ,AddFeeFlag ,EndDateCalMode ,AddAmntFlag ,PCalMode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getDutyCode());
			}
			if(this.getDutyName() == null || this.getDutyName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getDutyName());
			}
			pstmt.setInt(3, this.getPayEndYear());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getPayEndYearFlag());
			}
			if(this.getPayEndDateCalRef() == null || this.getPayEndDateCalRef().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getPayEndDateCalRef());
			}
			if(this.getPayEndDateCalMode() == null || this.getPayEndDateCalMode().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getPayEndDateCalMode());
			}
			pstmt.setInt(7, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getGetYearFlag());
			}
			pstmt.setInt(9, this.getInsuYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getInsuYearFlag());
			}
			pstmt.setInt(11, this.getAcciYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getAcciYearFlag());
			}
			if(this.getCalMode() == null || this.getCalMode().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getCalMode());
			}
			if(this.getPayEndYearRela() == null || this.getPayEndYearRela().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getPayEndYearRela());
			}
			if(this.getGetYearRela() == null || this.getGetYearRela().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getGetYearRela());
			}
			if(this.getInsuYearRela() == null || this.getInsuYearRela().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getInsuYearRela());
			}
			if(this.getBasicCalCode() == null || this.getBasicCalCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getBasicCalCode());
			}
			if(this.getRiskCalCode() == null || this.getRiskCalCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskCalCode());
			}
			if(this.getAmntFlag() == null || this.getAmntFlag().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getAmntFlag());
			}
			pstmt.setDouble(20, this.getVPU());
			if(this.getAddFeeFlag() == null || this.getAddFeeFlag().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAddFeeFlag());
			}
			if(this.getEndDateCalMode() == null || this.getEndDateCalMode().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getEndDateCalMode());
			}
			if(this.getAddAmntFlag() == null || this.getAddAmntFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAddAmntFlag());
			}
			if(this.getPCalMode() == null || this.getPCalMode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPCalMode());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LMDuty WHERE  DutyCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getDutyCode());
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
					tError.moduleName = "LMDutyDB";
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
			tError.moduleName = "LMDutyDB";
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

	public LMDutySet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMDutySet aLMDutySet = new LMDutySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LMDuty");
			LMDutySchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				LMDutySchema s1 = new LMDutySchema();
				s1.setSchema(rs,i);
				aLMDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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

		return aLMDutySet;
	}

	public LMDutySet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LMDutySet aLMDutySet = new LMDutySet();

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
				LMDutySchema s1 = new LMDutySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMDutySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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

		return aLMDutySet;
	}
	
	public LMDutySet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMDutySet aLMDutySet = new LMDutySet();

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
				LMDutySchema s1 = new LMDutySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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

		return aLMDutySet;
	}

	public LMDutySet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMDutySet aLMDutySet = new LMDutySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LMDuty");
			LMDutySchema aSchema = this.getSchema();
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

				LMDutySchema s1 = new LMDutySchema();
				s1.setSchema(rs,i);
				aLMDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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

		return aLMDutySet;
	}

	public LMDutySet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMDutySet aLMDutySet = new LMDutySet();

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

				LMDutySchema s1 = new LMDutySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyDB";
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

		return aLMDutySet;
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
			SQLString sqlObj = new SQLString("LMDuty");
			LMDutySchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LMDuty " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LMDutyDB";
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
			tError.moduleName = "LMDutyDB";
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
        tError.moduleName = "LMDutyDB";
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
        tError.moduleName = "LMDutyDB";
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
        tError.moduleName = "LMDutyDB";
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
        tError.moduleName = "LMDutyDB";
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
 * @return LMDutySet
 */
public LMDutySet getData()
{
    int tCount = 0;
    LMDutySet tLMDutySet = new LMDutySet();
    LMDutySchema tLMDutySchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LMDutyDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLMDutySchema = new LMDutySchema();
        tLMDutySchema.setSchema(mResultSet, 1);
        tLMDutySet.add(tLMDutySchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLMDutySchema = new LMDutySchema();
                tLMDutySchema.setSchema(mResultSet, 1);
                tLMDutySet.add(tLMDutySchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LMDutyDB";
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
    return tLMDutySet;
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
            tError.moduleName = "LMDutyDB";
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
        tError.moduleName = "LMDutyDB";
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
            tError.moduleName = "LMDutyDB";
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
        tError.moduleName = "LMDutyDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
