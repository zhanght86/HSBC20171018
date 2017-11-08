/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LCUWTraceSchema;
import com.sinosoft.lis.vschema.LCUWTraceSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCUWTraceDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCUWTraceDB extends LCUWTraceSchema
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
	public LCUWTraceDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCUWTrace" );
		mflag = true;
	}

	public LCUWTraceDB()
	{
		con = null;
		db = new DBOper( "LCUWTrace" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCUWTraceSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCUWTraceSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCUWTrace WHERE  ContNo = ? AND InsuredNo = ? AND RiskCode = ? AND RuleLevel = ? AND UWNo = ? AND RuleCode = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getInsuredNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getRuleLevel() == null || this.getRuleLevel().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRuleLevel());
			}
			pstmt.setInt(5, this.getUWNo());
			if(this.getRuleCode() == null || this.getRuleCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRuleCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCUWTrace");
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
		SQLString sqlObj = new SQLString("LCUWTrace");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCUWTrace SET  GrpContNo = ? , ContNo = ? , InsuredNo = ? , RiskCode = ? , RuleLevel = ? , UWNo = ? , RuleCode = ? , PostponeDay = ? , PostponeDate = ? , AutoUWConclu = ? , AutoUWDate = ? , UWError = ? , UWConclu = ? , UWIdea = ? , UWDate = ? , UpReport = ? , UpReportContent = ? , HealthFlag = ? , QuesFlag = ? , SpecFlag = ? , AddPremFlag = ? , AddPremReason = ? , ReportFlag = ? , PrintFlag = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  ContNo = ? AND InsuredNo = ? AND RiskCode = ? AND RuleLevel = ? AND UWNo = ? AND RuleCode = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getInsuredNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRiskCode());
			}
			if(this.getRuleLevel() == null || this.getRuleLevel().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRuleLevel());
			}
			pstmt.setInt(6, this.getUWNo());
			if(this.getRuleCode() == null || this.getRuleCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRuleCode());
			}
			if(this.getPostponeDay() == null || this.getPostponeDay().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPostponeDay());
			}
			if(this.getPostponeDate() == null || this.getPostponeDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getPostponeDate()));
			}
			if(this.getAutoUWConclu() == null || this.getAutoUWConclu().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAutoUWConclu());
			}
			if(this.getAutoUWDate() == null || this.getAutoUWDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getAutoUWDate()));
			}
			if(this.getUWError() == null || this.getUWError().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getUWError());
			}
			if(this.getUWConclu() == null || this.getUWConclu().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getUWConclu());
			}
			if(this.getUWIdea() == null || this.getUWIdea().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getUWIdea());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getUWDate()));
			}
			if(this.getUpReport() == null || this.getUpReport().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getUpReport());
			}
			if(this.getUpReportContent() == null || this.getUpReportContent().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getUpReportContent());
			}
			if(this.getHealthFlag() == null || this.getHealthFlag().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getHealthFlag());
			}
			if(this.getQuesFlag() == null || this.getQuesFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getQuesFlag());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSpecFlag());
			}
			if(this.getAddPremFlag() == null || this.getAddPremFlag().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAddPremFlag());
			}
			if(this.getAddPremReason() == null || this.getAddPremReason().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAddPremReason());
			}
			if(this.getReportFlag() == null || this.getReportFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getReportFlag());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPrintFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getModifyTime());
			}
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getInsuredNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRiskCode());
			}
			if(this.getRuleLevel() == null || this.getRuleLevel().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getRuleLevel());
			}
			pstmt.setInt(37, this.getUWNo());
			if(this.getRuleCode() == null || this.getRuleCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getRuleCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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
		SQLString sqlObj = new SQLString("LCUWTrace");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCUWTrace VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getInsuredNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRiskCode());
			}
			if(this.getRuleLevel() == null || this.getRuleLevel().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRuleLevel());
			}
			pstmt.setInt(6, this.getUWNo());
			if(this.getRuleCode() == null || this.getRuleCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRuleCode());
			}
			if(this.getPostponeDay() == null || this.getPostponeDay().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPostponeDay());
			}
			if(this.getPostponeDate() == null || this.getPostponeDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getPostponeDate()));
			}
			if(this.getAutoUWConclu() == null || this.getAutoUWConclu().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAutoUWConclu());
			}
			if(this.getAutoUWDate() == null || this.getAutoUWDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getAutoUWDate()));
			}
			if(this.getUWError() == null || this.getUWError().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getUWError());
			}
			if(this.getUWConclu() == null || this.getUWConclu().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getUWConclu());
			}
			if(this.getUWIdea() == null || this.getUWIdea().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getUWIdea());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getUWDate()));
			}
			if(this.getUpReport() == null || this.getUpReport().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getUpReport());
			}
			if(this.getUpReportContent() == null || this.getUpReportContent().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getUpReportContent());
			}
			if(this.getHealthFlag() == null || this.getHealthFlag().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getHealthFlag());
			}
			if(this.getQuesFlag() == null || this.getQuesFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getQuesFlag());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSpecFlag());
			}
			if(this.getAddPremFlag() == null || this.getAddPremFlag().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAddPremFlag());
			}
			if(this.getAddPremReason() == null || this.getAddPremReason().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAddPremReason());
			}
			if(this.getReportFlag() == null || this.getReportFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getReportFlag());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPrintFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCUWTrace WHERE  ContNo = ? AND InsuredNo = ? AND RiskCode = ? AND RuleLevel = ? AND UWNo = ? AND RuleCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getInsuredNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getRuleLevel() == null || this.getRuleLevel().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRuleLevel());
			}
			pstmt.setInt(5, this.getUWNo());
			if(this.getRuleCode() == null || this.getRuleCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRuleCode());
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
					tError.moduleName = "LCUWTraceDB";
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
			tError.moduleName = "LCUWTraceDB";
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

	public LCUWTraceSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCUWTraceSet aLCUWTraceSet = new LCUWTraceSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCUWTrace");
			LCUWTraceSchema aSchema = this.getSchema();
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
				LCUWTraceSchema s1 = new LCUWTraceSchema();
				s1.setSchema(rs,i);
				aLCUWTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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

		return aLCUWTraceSet;
	}

	public LCUWTraceSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCUWTraceSet aLCUWTraceSet = new LCUWTraceSet();

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
				LCUWTraceSchema s1 = new LCUWTraceSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCUWTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCUWTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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

		return aLCUWTraceSet;
	}

	public LCUWTraceSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCUWTraceSet aLCUWTraceSet = new LCUWTraceSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCUWTrace");
			LCUWTraceSchema aSchema = this.getSchema();
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

				LCUWTraceSchema s1 = new LCUWTraceSchema();
				s1.setSchema(rs,i);
				aLCUWTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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

		return aLCUWTraceSet;
	}

	public LCUWTraceSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCUWTraceSet aLCUWTraceSet = new LCUWTraceSet();

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

				LCUWTraceSchema s1 = new LCUWTraceSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCUWTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCUWTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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

		return aLCUWTraceSet;
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
			SQLString sqlObj = new SQLString("LCUWTrace");
			LCUWTraceSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCUWTrace " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCUWTraceDB";
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
			tError.moduleName = "LCUWTraceDB";
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
        tError.moduleName = "LCUWTraceDB";
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
        tError.moduleName = "LCUWTraceDB";
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
        tError.moduleName = "LCUWTraceDB";
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
        tError.moduleName = "LCUWTraceDB";
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
 * @return LCUWTraceSet
 */
public LCUWTraceSet getData()
{
    int tCount = 0;
    LCUWTraceSet tLCUWTraceSet = new LCUWTraceSet();
    LCUWTraceSchema tLCUWTraceSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCUWTraceDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCUWTraceSchema = new LCUWTraceSchema();
        tLCUWTraceSchema.setSchema(mResultSet, 1);
        tLCUWTraceSet.add(tLCUWTraceSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCUWTraceSchema = new LCUWTraceSchema();
                tLCUWTraceSchema.setSchema(mResultSet, 1);
                tLCUWTraceSet.add(tLCUWTraceSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCUWTraceDB";
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
    return tLCUWTraceSet;
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
            tError.moduleName = "LCUWTraceDB";
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
        tError.moduleName = "LCUWTraceDB";
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
            tError.moduleName = "LCUWTraceDB";
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
        tError.moduleName = "LCUWTraceDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCUWTraceSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCUWTraceSet aLCUWTraceSet = new LCUWTraceSet();

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
				LCUWTraceSchema s1 = new LCUWTraceSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LCUWTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLCUWTraceSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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

		return aLCUWTraceSet;
	}

	public LCUWTraceSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCUWTraceSet aLCUWTraceSet = new LCUWTraceSet();

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

				LCUWTraceSchema s1 = new LCUWTraceSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LCUWTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLCUWTraceSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LCUWTraceDB";
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

		return aLCUWTraceSet; 
	}

}
