

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMRiskFeeSchema;
import com.sinosoft.lis.vschema.PD_LMRiskFeeSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMRiskFeeDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class PD_LMRiskFeeDB extends PD_LMRiskFeeSchema
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
	public PD_LMRiskFeeDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMRiskFee" );
		mflag = true;
	}

	public PD_LMRiskFeeDB()
	{
		con = null;
		db = new DBOper( "PD_LMRiskFee" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMRiskFeeSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMRiskFeeSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMRiskFee WHERE  FeeCode = ? AND InsuAccNo = ? AND PayPlanCode = ? AND FeeCalMode = ?");
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFeeCode());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getInsuAccNo());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanCode());
			}
			if(this.getFeeCalMode() == null || this.getFeeCalMode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFeeCalMode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMRiskFee");
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
		SQLString sqlObj = new SQLString("PD_LMRiskFee");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMRiskFee SET  FeeCode = ? , FeeName = ? , FeeNoti = ? , InsuAccNo = ? , PayPlanCode = ? , PayInsuAccName = ? , FeeKind = ? , FeeItemType = ? , FeeTakePlace = ? , FeeCalMode = ? , FeeCalModeType = ? , FeeCalCode = ? , FeeValue = ? , CompareValue = ? , FeePeriod = ? , MaxTime = ? , DefaultFlag = ? , FeeStartDate = ? , FeeNum = ? , FeeBaseType = ? , InterfaceClassName = ? , FeeType = ? , PeriodType = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , RiskCode = ? WHERE  FeeCode = ? AND InsuAccNo = ? AND PayPlanCode = ? AND FeeCalMode = ?");
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFeeCode());
			}
			if(this.getFeeName() == null || this.getFeeName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getFeeName());
			}
			if(this.getFeeNoti() == null || this.getFeeNoti().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFeeNoti());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuAccNo());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPayPlanCode());
			}
			if(this.getPayInsuAccName() == null || this.getPayInsuAccName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayInsuAccName());
			}
			if(this.getFeeKind() == null || this.getFeeKind().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getFeeKind());
			}
			if(this.getFeeItemType() == null || this.getFeeItemType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFeeItemType());
			}
			if(this.getFeeTakePlace() == null || this.getFeeTakePlace().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getFeeTakePlace());
			}
			if(this.getFeeCalMode() == null || this.getFeeCalMode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getFeeCalMode());
			}
			if(this.getFeeCalModeType() == null || this.getFeeCalModeType().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getFeeCalModeType());
			}
			if(this.getFeeCalCode() == null || this.getFeeCalCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getFeeCalCode());
			}
			pstmt.setDouble(13, this.getFeeValue());
			pstmt.setDouble(14, this.getCompareValue());
			if(this.getFeePeriod() == null || this.getFeePeriod().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getFeePeriod());
			}
			pstmt.setInt(16, this.getMaxTime());
			if(this.getDefaultFlag() == null || this.getDefaultFlag().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getDefaultFlag());
			}
			if(this.getFeeStartDate() == null || this.getFeeStartDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getFeeStartDate()));
			}
			pstmt.setInt(19, this.getFeeNum());
			if(this.getFeeBaseType() == null || this.getFeeBaseType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getFeeBaseType());
			}
			if(this.getInterfaceClassName() == null || this.getInterfaceClassName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getInterfaceClassName());
			}
			if(this.getFeeType() == null || this.getFeeType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getFeeType());
			}
			if(this.getPeriodType() == null || this.getPeriodType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getPeriodType());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getStandbyflag2());
			}
			pstmt.setInt(31, this.getStandbyflag3());
			pstmt.setInt(32, this.getStandbyflag4());
			pstmt.setDouble(33, this.getStandbyflag5());
			pstmt.setDouble(34, this.getStandbyflag6());
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRiskCode());
			}
			// set where condition
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getFeeCode());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getInsuAccNo());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getPayPlanCode());
			}
			if(this.getFeeCalMode() == null || this.getFeeCalMode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getFeeCalMode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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
		SQLString sqlObj = new SQLString("PD_LMRiskFee");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMRiskFee(FeeCode ,FeeName ,FeeNoti ,InsuAccNo ,PayPlanCode ,PayInsuAccName ,FeeKind ,FeeItemType ,FeeTakePlace ,FeeCalMode ,FeeCalModeType ,FeeCalCode ,FeeValue ,CompareValue ,FeePeriod ,MaxTime ,DefaultFlag ,FeeStartDate ,FeeNum ,FeeBaseType ,InterfaceClassName ,FeeType ,PeriodType ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6 ,RiskCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFeeCode());
			}
			if(this.getFeeName() == null || this.getFeeName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getFeeName());
			}
			if(this.getFeeNoti() == null || this.getFeeNoti().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFeeNoti());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuAccNo());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPayPlanCode());
			}
			if(this.getPayInsuAccName() == null || this.getPayInsuAccName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayInsuAccName());
			}
			if(this.getFeeKind() == null || this.getFeeKind().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getFeeKind());
			}
			if(this.getFeeItemType() == null || this.getFeeItemType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFeeItemType());
			}
			if(this.getFeeTakePlace() == null || this.getFeeTakePlace().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getFeeTakePlace());
			}
			if(this.getFeeCalMode() == null || this.getFeeCalMode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getFeeCalMode());
			}
			if(this.getFeeCalModeType() == null || this.getFeeCalModeType().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getFeeCalModeType());
			}
			if(this.getFeeCalCode() == null || this.getFeeCalCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getFeeCalCode());
			}
			pstmt.setDouble(13, this.getFeeValue());
			pstmt.setDouble(14, this.getCompareValue());
			if(this.getFeePeriod() == null || this.getFeePeriod().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getFeePeriod());
			}
			pstmt.setInt(16, this.getMaxTime());
			if(this.getDefaultFlag() == null || this.getDefaultFlag().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getDefaultFlag());
			}
			if(this.getFeeStartDate() == null || this.getFeeStartDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getFeeStartDate()));
			}
			pstmt.setInt(19, this.getFeeNum());
			if(this.getFeeBaseType() == null || this.getFeeBaseType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getFeeBaseType());
			}
			if(this.getInterfaceClassName() == null || this.getInterfaceClassName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getInterfaceClassName());
			}
			if(this.getFeeType() == null || this.getFeeType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getFeeType());
			}
			if(this.getPeriodType() == null || this.getPeriodType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getPeriodType());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getStandbyflag2());
			}
			pstmt.setInt(31, this.getStandbyflag3());
			pstmt.setInt(32, this.getStandbyflag4());
			pstmt.setDouble(33, this.getStandbyflag5());
			pstmt.setDouble(34, this.getStandbyflag6());
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRiskCode());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMRiskFee WHERE  FeeCode = ? AND InsuAccNo = ? AND PayPlanCode = ? AND FeeCalMode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getFeeCode() == null || this.getFeeCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFeeCode());
			}
			if(this.getInsuAccNo() == null || this.getInsuAccNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getInsuAccNo());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanCode());
			}
			if(this.getFeeCalMode() == null || this.getFeeCalMode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFeeCalMode());
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
					tError.moduleName = "PD_LMRiskFeeDB";
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
			tError.moduleName = "PD_LMRiskFeeDB";
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

	public PD_LMRiskFeeSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskFeeSet aPD_LMRiskFeeSet = new PD_LMRiskFeeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMRiskFee");
			PD_LMRiskFeeSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				PD_LMRiskFeeSchema s1 = new PD_LMRiskFeeSchema();
				s1.setSchema(rs,i);
				aPD_LMRiskFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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

		return aPD_LMRiskFeeSet;
	}

	public PD_LMRiskFeeSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskFeeSet aPD_LMRiskFeeSet = new PD_LMRiskFeeSet();

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
				PD_LMRiskFeeSchema s1 = new PD_LMRiskFeeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMRiskFeeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMRiskFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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

		return aPD_LMRiskFeeSet;
	}

	public PD_LMRiskFeeSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskFeeSet aPD_LMRiskFeeSet = new PD_LMRiskFeeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMRiskFee");
			PD_LMRiskFeeSchema aSchema = this.getSchema();
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

				PD_LMRiskFeeSchema s1 = new PD_LMRiskFeeSchema();
				s1.setSchema(rs,i);
				aPD_LMRiskFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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

		return aPD_LMRiskFeeSet;
	}

	public PD_LMRiskFeeSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMRiskFeeSet aPD_LMRiskFeeSet = new PD_LMRiskFeeSet();

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

				PD_LMRiskFeeSchema s1 = new PD_LMRiskFeeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMRiskFeeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMRiskFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFeeDB";
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

		return aPD_LMRiskFeeSet;
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
			SQLString sqlObj = new SQLString("PD_LMRiskFee");
			PD_LMRiskFeeSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMRiskFee " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMRiskFeeDB";
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
			tError.moduleName = "PD_LMRiskFeeDB";
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
        tError.moduleName = "PD_LMRiskFeeDB";
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
        tError.moduleName = "PD_LMRiskFeeDB";
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
        tError.moduleName = "PD_LMRiskFeeDB";
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
        tError.moduleName = "PD_LMRiskFeeDB";
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
 * @return PD_LMRiskFeeSet
 */
public PD_LMRiskFeeSet getData()
{
    int tCount = 0;
    PD_LMRiskFeeSet tPD_LMRiskFeeSet = new PD_LMRiskFeeSet();
    PD_LMRiskFeeSchema tPD_LMRiskFeeSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMRiskFeeDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMRiskFeeSchema = new PD_LMRiskFeeSchema();
        tPD_LMRiskFeeSchema.setSchema(mResultSet, 1);
        tPD_LMRiskFeeSet.add(tPD_LMRiskFeeSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMRiskFeeSchema = new PD_LMRiskFeeSchema();
                tPD_LMRiskFeeSchema.setSchema(mResultSet, 1);
                tPD_LMRiskFeeSet.add(tPD_LMRiskFeeSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMRiskFeeDB";
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
    return tPD_LMRiskFeeSet;
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
            tError.moduleName = "PD_LMRiskFeeDB";
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
        tError.moduleName = "PD_LMRiskFeeDB";
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
            tError.moduleName = "PD_LMRiskFeeDB";
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
        tError.moduleName = "PD_LMRiskFeeDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
