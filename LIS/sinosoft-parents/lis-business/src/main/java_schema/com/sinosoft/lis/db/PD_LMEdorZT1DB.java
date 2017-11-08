/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMEdorZT1Schema;
import com.sinosoft.lis.vschema.PD_LMEdorZT1Set;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMEdorZT1DB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMEdorZT1DB extends PD_LMEdorZT1Schema
{
private static Logger logger = Logger.getLogger(PD_LMEdorZT1DB.class);

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
	public PD_LMEdorZT1DB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMEdorZT1" );
		mflag = true;
	}

	public PD_LMEdorZT1DB()
	{
		con = null;
		db = new DBOper( "PD_LMEdorZT1" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMEdorZT1Schema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMEdorZT1Schema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMEdorZT1 WHERE  RiskCode = ? AND DutyCode = ? AND PayPlanCode = ? AND SurrCalType = ? AND CycPayIntvType = ?");
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
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanCode());
			}
			if(this.getSurrCalType() == null || this.getSurrCalType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSurrCalType());
			}
			if(this.getCycPayIntvType() == null || this.getCycPayIntvType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCycPayIntvType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMEdorZT1");
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
		SQLString sqlObj = new SQLString("PD_LMEdorZT1");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMEdorZT1 SET  RiskCode = ? , DutyCode = ? , PayPlanCode = ? , SurrCalType = ? , CycPayIntvType = ? , CycPayCalCode = ? , LiveGetType = ? , DeadGetType = ? , CalCodeType = ? , ZTYearType = ? , OnePayCalCode = ? , OnePayIntvType = ? , OutGetType = ? , CashValueCode = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  RiskCode = ? AND DutyCode = ? AND PayPlanCode = ? AND SurrCalType = ? AND CycPayIntvType = ?");
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
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanCode());
			}
			if(this.getSurrCalType() == null || this.getSurrCalType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSurrCalType());
			}
			if(this.getCycPayIntvType() == null || this.getCycPayIntvType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCycPayIntvType());
			}
			if(this.getCycPayCalCode() == null || this.getCycPayCalCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCycPayCalCode());
			}
			if(this.getLiveGetType() == null || this.getLiveGetType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getLiveGetType());
			}
			if(this.getDeadGetType() == null || this.getDeadGetType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getDeadGetType());
			}
			if(this.getCalCodeType() == null || this.getCalCodeType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getCalCodeType());
			}
			if(this.getZTYearType() == null || this.getZTYearType().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getZTYearType());
			}
			if(this.getOnePayCalCode() == null || this.getOnePayCalCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getOnePayCalCode());
			}
			if(this.getOnePayIntvType() == null || this.getOnePayIntvType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getOnePayIntvType());
			}
			if(this.getOutGetType() == null || this.getOutGetType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getOutGetType());
			}
			if(this.getCashValueCode() == null || this.getCashValueCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCashValueCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandbyflag2());
			}
			pstmt.setInt(22, this.getStandbyflag3());
			pstmt.setInt(23, this.getStandbyflag4());
			pstmt.setDouble(24, this.getStandbyflag5());
			pstmt.setDouble(25, this.getStandbyflag6());
			// set where condition
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPayPlanCode());
			}
			if(this.getSurrCalType() == null || this.getSurrCalType().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getSurrCalType());
			}
			if(this.getCycPayIntvType() == null || this.getCycPayIntvType().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getCycPayIntvType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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
		SQLString sqlObj = new SQLString("PD_LMEdorZT1");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMEdorZT1(RiskCode ,DutyCode ,PayPlanCode ,SurrCalType ,CycPayIntvType ,CycPayCalCode ,LiveGetType ,DeadGetType ,CalCodeType ,ZTYearType ,OnePayCalCode ,OnePayIntvType ,OutGetType ,CashValueCode ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanCode());
			}
			if(this.getSurrCalType() == null || this.getSurrCalType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSurrCalType());
			}
			if(this.getCycPayIntvType() == null || this.getCycPayIntvType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCycPayIntvType());
			}
			if(this.getCycPayCalCode() == null || this.getCycPayCalCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCycPayCalCode());
			}
			if(this.getLiveGetType() == null || this.getLiveGetType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getLiveGetType());
			}
			if(this.getDeadGetType() == null || this.getDeadGetType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getDeadGetType());
			}
			if(this.getCalCodeType() == null || this.getCalCodeType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getCalCodeType());
			}
			if(this.getZTYearType() == null || this.getZTYearType().equals("null")) {
				pstmt.setNull(10, 1);
			} else {
				pstmt.setString(10, this.getZTYearType());
			}
			if(this.getOnePayCalCode() == null || this.getOnePayCalCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getOnePayCalCode());
			}
			if(this.getOnePayIntvType() == null || this.getOnePayIntvType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getOnePayIntvType());
			}
			if(this.getOutGetType() == null || this.getOutGetType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getOutGetType());
			}
			if(this.getCashValueCode() == null || this.getCashValueCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCashValueCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandbyflag2());
			}
			pstmt.setInt(22, this.getStandbyflag3());
			pstmt.setInt(23, this.getStandbyflag4());
			pstmt.setDouble(24, this.getStandbyflag5());
			pstmt.setDouble(25, this.getStandbyflag6());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMEdorZT1 WHERE  RiskCode = ? AND DutyCode = ? AND PayPlanCode = ? AND SurrCalType = ? AND CycPayIntvType = ?", 
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
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanCode());
			}
			if(this.getSurrCalType() == null || this.getSurrCalType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSurrCalType());
			}
			if(this.getCycPayIntvType() == null || this.getCycPayIntvType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getCycPayIntvType());
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
					tError.moduleName = "PD_LMEdorZT1DB";
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
			tError.moduleName = "PD_LMEdorZT1DB";
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

	public PD_LMEdorZT1Set query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LMEdorZT1Set aPD_LMEdorZT1Set = new PD_LMEdorZT1Set();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LMEdorZT1");
			PD_LMEdorZT1Schema aSchema = this.getSchema();
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
				PD_LMEdorZT1Schema s1 = new PD_LMEdorZT1Schema();
				s1.setSchema(rs,i);
				aPD_LMEdorZT1Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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

		return aPD_LMEdorZT1Set;

	}

	public PD_LMEdorZT1Set executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMEdorZT1Set aPD_LMEdorZT1Set = new PD_LMEdorZT1Set();

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
				PD_LMEdorZT1Schema s1 = new PD_LMEdorZT1Schema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMEdorZT1DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMEdorZT1Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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

		return aPD_LMEdorZT1Set;
	}

	public PD_LMEdorZT1Set query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LMEdorZT1Set aPD_LMEdorZT1Set = new PD_LMEdorZT1Set();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LMEdorZT1");
			PD_LMEdorZT1Schema aSchema = this.getSchema();
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

				PD_LMEdorZT1Schema s1 = new PD_LMEdorZT1Schema();
				s1.setSchema(rs,i);
				aPD_LMEdorZT1Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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

		return aPD_LMEdorZT1Set;

	}

	public PD_LMEdorZT1Set executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMEdorZT1Set aPD_LMEdorZT1Set = new PD_LMEdorZT1Set();

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

				PD_LMEdorZT1Schema s1 = new PD_LMEdorZT1Schema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMEdorZT1DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMEdorZT1Set.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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

		return aPD_LMEdorZT1Set;
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
			SQLString sqlObj = new SQLString("PD_LMEdorZT1");
			PD_LMEdorZT1Schema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMEdorZT1 " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMEdorZT1DB";
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
			tError.moduleName = "PD_LMEdorZT1DB";
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
        tError.moduleName = "PD_LMEdorZT1DB";
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
        tError.moduleName = "PD_LMEdorZT1DB";
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
        tError.moduleName = "PD_LMEdorZT1DB";
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
        tError.moduleName = "PD_LMEdorZT1DB";
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
 * @return PD_LMEdorZT1Set
 */
public PD_LMEdorZT1Set getData()
{
    int tCount = 0;
    PD_LMEdorZT1Set tPD_LMEdorZT1Set = new PD_LMEdorZT1Set();
    PD_LMEdorZT1Schema tPD_LMEdorZT1Schema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMEdorZT1DB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMEdorZT1Schema = new PD_LMEdorZT1Schema();
        tPD_LMEdorZT1Schema.setSchema(mResultSet, 1);
        tPD_LMEdorZT1Set.add(tPD_LMEdorZT1Schema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMEdorZT1Schema = new PD_LMEdorZT1Schema();
                tPD_LMEdorZT1Schema.setSchema(mResultSet, 1);
                tPD_LMEdorZT1Set.add(tPD_LMEdorZT1Schema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMEdorZT1DB";
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
    return tPD_LMEdorZT1Set;
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
            tError.moduleName = "PD_LMEdorZT1DB";
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
        tError.moduleName = "PD_LMEdorZT1DB";
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
            tError.moduleName = "PD_LMEdorZT1DB";
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
        tError.moduleName = "PD_LMEdorZT1DB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public PD_LMEdorZT1Set executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LMEdorZT1Set aPD_LMEdorZT1Set = new PD_LMEdorZT1Set();

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
				PD_LMEdorZT1Schema s1 = new PD_LMEdorZT1Schema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMEdorZT1DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMEdorZT1Set.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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

		return aPD_LMEdorZT1Set;
	}

	public PD_LMEdorZT1Set executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LMEdorZT1Set aPD_LMEdorZT1Set = new PD_LMEdorZT1Set();

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

				PD_LMEdorZT1Schema s1 = new PD_LMEdorZT1Schema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMEdorZT1DB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMEdorZT1Set.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZT1DB";
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

		return aPD_LMEdorZT1Set; 
	}

}
