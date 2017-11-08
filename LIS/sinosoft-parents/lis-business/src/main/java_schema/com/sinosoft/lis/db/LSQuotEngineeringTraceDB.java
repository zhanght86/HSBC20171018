/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LSQuotEngineeringTraceSchema;
import com.sinosoft.lis.vschema.LSQuotEngineeringTraceSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LSQuotEngineeringTraceDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotEngineeringTraceDB extends LSQuotEngineeringTraceSchema
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
	public LSQuotEngineeringTraceDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LSQuotEngineeringTrace" );
		mflag = true;
	}

	public LSQuotEngineeringTraceDB()
	{
		con = null;
		db = new DBOper( "LSQuotEngineeringTrace" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LSQuotEngineeringTraceSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LSQuotEngineeringTraceSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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
			pstmt = con.prepareStatement("DELETE FROM LSQuotEngineeringTrace WHERE  SerialNo = ? AND QuotNo = ? AND QuotBatNo = ? AND SysPlanCode = ? AND PlanCode = ?");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getQuotNo());
			}
			pstmt.setInt(3, this.getQuotBatNo());
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPlanCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotEngineeringTrace");
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
		SQLString sqlObj = new SQLString("LSQuotEngineeringTrace");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LSQuotEngineeringTrace SET  SerialNo = ? , QuotNo = ? , QuotBatNo = ? , SysPlanCode = ? , PlanCode = ? , EnginName = ? , EnginType = ? , EnginArea = ? , EnginCost = ? , EnginPlace = ? , EnginStartDate = ? , EnginEndDate = ? , InsurerName = ? , InsurerType = ? , ContractorName = ? , ContractorType = ? , EnginDesc = ? , EnginCondition = ? , Remark = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? , EnginDays = ? , MinEnginArea = ? , MinEnginCost = ? WHERE  SerialNo = ? AND QuotNo = ? AND QuotBatNo = ? AND SysPlanCode = ? AND PlanCode = ?");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getQuotNo());
			}
			pstmt.setInt(3, this.getQuotBatNo());
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPlanCode());
			}
			if(this.getEnginName() == null || this.getEnginName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getEnginName());
			}
			if(this.getEnginType() == null || this.getEnginType().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getEnginType());
			}
			pstmt.setDouble(8, this.getEnginArea());
			pstmt.setDouble(9, this.getEnginCost());
			if(this.getEnginPlace() == null || this.getEnginPlace().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getEnginPlace());
			}
			if(this.getEnginStartDate() == null || this.getEnginStartDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getEnginStartDate()));
			}
			if(this.getEnginEndDate() == null || this.getEnginEndDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getEnginEndDate()));
			}
			if(this.getInsurerName() == null || this.getInsurerName().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getInsurerName());
			}
			if(this.getInsurerType() == null || this.getInsurerType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getInsurerType());
			}
			if(this.getContractorName() == null || this.getContractorName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getContractorName());
			}
			if(this.getContractorType() == null || this.getContractorType().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContractorType());
			}
			if(this.getEnginDesc() == null || this.getEnginDesc().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEnginDesc());
			}
			if(this.getEnginCondition() == null || this.getEnginCondition().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getEnginCondition());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getRemark());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getModifyTime());
			}
			if(this.getEnginDays() == null || this.getEnginDays().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getEnginDays());
			}
			pstmt.setDouble(27, this.getMinEnginArea());
			pstmt.setDouble(28, this.getMinEnginCost());
			// set where condition
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getSerialNo());
			}
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getQuotNo());
			}
			pstmt.setInt(31, this.getQuotBatNo());
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getPlanCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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
		SQLString sqlObj = new SQLString("LSQuotEngineeringTrace");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LSQuotEngineeringTrace VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getQuotNo());
			}
			pstmt.setInt(3, this.getQuotBatNo());
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPlanCode());
			}
			if(this.getEnginName() == null || this.getEnginName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getEnginName());
			}
			if(this.getEnginType() == null || this.getEnginType().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getEnginType());
			}
			pstmt.setDouble(8, this.getEnginArea());
			pstmt.setDouble(9, this.getEnginCost());
			if(this.getEnginPlace() == null || this.getEnginPlace().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getEnginPlace());
			}
			if(this.getEnginStartDate() == null || this.getEnginStartDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getEnginStartDate()));
			}
			if(this.getEnginEndDate() == null || this.getEnginEndDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getEnginEndDate()));
			}
			if(this.getInsurerName() == null || this.getInsurerName().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getInsurerName());
			}
			if(this.getInsurerType() == null || this.getInsurerType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getInsurerType());
			}
			if(this.getContractorName() == null || this.getContractorName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getContractorName());
			}
			if(this.getContractorType() == null || this.getContractorType().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContractorType());
			}
			if(this.getEnginDesc() == null || this.getEnginDesc().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEnginDesc());
			}
			if(this.getEnginCondition() == null || this.getEnginCondition().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getEnginCondition());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getRemark());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getModifyTime());
			}
			if(this.getEnginDays() == null || this.getEnginDays().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getEnginDays());
			}
			pstmt.setDouble(27, this.getMinEnginArea());
			pstmt.setDouble(28, this.getMinEnginCost());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LSQuotEngineeringTrace WHERE  SerialNo = ? AND QuotNo = ? AND QuotBatNo = ? AND SysPlanCode = ? AND PlanCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getQuotNo() == null || this.getQuotNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getQuotNo());
			}
			pstmt.setInt(3, this.getQuotBatNo());
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPlanCode());
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
					tError.moduleName = "LSQuotEngineeringTraceDB";
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
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

	public LSQuotEngineeringTraceSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotEngineeringTraceSet aLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LSQuotEngineeringTrace");
			LSQuotEngineeringTraceSchema aSchema = this.getSchema();
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
				LSQuotEngineeringTraceSchema s1 = new LSQuotEngineeringTraceSchema();
				s1.setSchema(rs,i);
				aLSQuotEngineeringTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

		return aLSQuotEngineeringTraceSet;
	}

	public LSQuotEngineeringTraceSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LSQuotEngineeringTraceSet aLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();

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
				LSQuotEngineeringTraceSchema s1 = new LSQuotEngineeringTraceSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LSQuotEngineeringTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotEngineeringTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

		return aLSQuotEngineeringTraceSet;
	}

	public LSQuotEngineeringTraceSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotEngineeringTraceSet aLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LSQuotEngineeringTrace");
			LSQuotEngineeringTraceSchema aSchema = this.getSchema();
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

				LSQuotEngineeringTraceSchema s1 = new LSQuotEngineeringTraceSchema();
				s1.setSchema(rs,i);
				aLSQuotEngineeringTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

		return aLSQuotEngineeringTraceSet;
	}

	public LSQuotEngineeringTraceSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LSQuotEngineeringTraceSet aLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();

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

				LSQuotEngineeringTraceSchema s1 = new LSQuotEngineeringTraceSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LSQuotEngineeringTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotEngineeringTraceSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

		return aLSQuotEngineeringTraceSet;
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
			SQLString sqlObj = new SQLString("LSQuotEngineeringTrace");
			LSQuotEngineeringTraceSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LSQuotEngineeringTrace " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LSQuotEngineeringTraceDB";
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
			tError.moduleName = "LSQuotEngineeringTraceDB";
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
        tError.moduleName = "LSQuotEngineeringTraceDB";
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
        tError.moduleName = "LSQuotEngineeringTraceDB";
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
        tError.moduleName = "LSQuotEngineeringTraceDB";
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
        tError.moduleName = "LSQuotEngineeringTraceDB";
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
 * @return LSQuotEngineeringTraceSet
 */
public LSQuotEngineeringTraceSet getData()
{
    int tCount = 0;
    LSQuotEngineeringTraceSet tLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();
    LSQuotEngineeringTraceSchema tLSQuotEngineeringTraceSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LSQuotEngineeringTraceDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLSQuotEngineeringTraceSchema = new LSQuotEngineeringTraceSchema();
        tLSQuotEngineeringTraceSchema.setSchema(mResultSet, 1);
        tLSQuotEngineeringTraceSet.add(tLSQuotEngineeringTraceSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLSQuotEngineeringTraceSchema = new LSQuotEngineeringTraceSchema();
                tLSQuotEngineeringTraceSchema.setSchema(mResultSet, 1);
                tLSQuotEngineeringTraceSet.add(tLSQuotEngineeringTraceSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LSQuotEngineeringTraceDB";
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
    return tLSQuotEngineeringTraceSet;
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
            tError.moduleName = "LSQuotEngineeringTraceDB";
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
        tError.moduleName = "LSQuotEngineeringTraceDB";
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
            tError.moduleName = "LSQuotEngineeringTraceDB";
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
        tError.moduleName = "LSQuotEngineeringTraceDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LSQuotEngineeringTraceSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotEngineeringTraceSet aLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();

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
				LSQuotEngineeringTraceSchema s1 = new LSQuotEngineeringTraceSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LSQuotEngineeringTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotEngineeringTraceSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

		return aLSQuotEngineeringTraceSet;
	}

	public LSQuotEngineeringTraceSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LSQuotEngineeringTraceSet aLSQuotEngineeringTraceSet = new LSQuotEngineeringTraceSet();

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

				LSQuotEngineeringTraceSchema s1 = new LSQuotEngineeringTraceSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LSQuotEngineeringTraceDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLSQuotEngineeringTraceSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LSQuotEngineeringTraceDB";
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

		return aLSQuotEngineeringTraceSet; 
	}

}