

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMDutyPay_LibSchema;
import com.sinosoft.lis.vschema.PD_LMDutyPay_LibSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyPay_LibDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyPay_LibDB extends PD_LMDutyPay_LibSchema
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
	public PD_LMDutyPay_LibDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMDutyPay_Lib" );
		mflag = true;
	}

	public PD_LMDutyPay_LibDB()
	{
		con = null;
		db = new DBOper( "PD_LMDutyPay_Lib" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMDutyPay_LibSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMDutyPay_LibSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMDutyPay_Lib WHERE  PayPlanCode2 = ?");
			if(this.getPayPlanCode2() == null || this.getPayPlanCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPayPlanCode2());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyPay_Lib");
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
		SQLString sqlObj = new SQLString("PD_LMDutyPay_Lib");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMDutyPay_Lib SET  PayPlanCode2 = ? , CalCode = ? , PayPlanName = ? , Type = ? , PayIntv = ? , PayEndYearFlag = ? , PayEndYear = ? , PayEndDateCalRef = ? , PayEndDateCalMode = ? , DefaultVal = ? , CnterCalCode = ? , OthCalCode = ? , Rate = ? , MinPay = ? , AssuYield = ? , FeeRate = ? , PayToDateCalMode = ? , UrgePayFlag = ? , PayLackFlag = ? , PayOverFlag = ? , PayOverDeal = ? , AvoidPayFlag = ? , GracePeriod = ? , PubFlag = ? , ZeroFlag = ? , NeedAcc = ? , PayAimClass = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  PayPlanCode2 = ?");
			if(this.getPayPlanCode2() == null || this.getPayPlanCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPayPlanCode2());
			}
			if(this.getCalCode() == null || this.getCalCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCalCode());
			}
			if(this.getPayPlanName() == null || this.getPayPlanName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanName());
			}
			if(this.getType() == null || this.getType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getType());
			}
			pstmt.setInt(5, this.getPayIntv());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayEndYearFlag());
			}
			pstmt.setInt(7, this.getPayEndYear());
			if(this.getPayEndDateCalRef() == null || this.getPayEndDateCalRef().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPayEndDateCalRef());
			}
			if(this.getPayEndDateCalMode() == null || this.getPayEndDateCalMode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getPayEndDateCalMode());
			}
			pstmt.setDouble(10, this.getDefaultVal());
			if(this.getCnterCalCode() == null || this.getCnterCalCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCnterCalCode());
			}
			if(this.getOthCalCode() == null || this.getOthCalCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getOthCalCode());
			}
			pstmt.setDouble(13, this.getRate());
			pstmt.setDouble(14, this.getMinPay());
			pstmt.setDouble(15, this.getAssuYield());
			pstmt.setDouble(16, this.getFeeRate());
			if(this.getPayToDateCalMode() == null || this.getPayToDateCalMode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPayToDateCalMode());
			}
			if(this.getUrgePayFlag() == null || this.getUrgePayFlag().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getUrgePayFlag());
			}
			if(this.getPayLackFlag() == null || this.getPayLackFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPayLackFlag());
			}
			if(this.getPayOverFlag() == null || this.getPayOverFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPayOverFlag());
			}
			if(this.getPayOverDeal() == null || this.getPayOverDeal().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPayOverDeal());
			}
			if(this.getAvoidPayFlag() == null || this.getAvoidPayFlag().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAvoidPayFlag());
			}
			pstmt.setInt(23, this.getGracePeriod());
			if(this.getPubFlag() == null || this.getPubFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPubFlag());
			}
			if(this.getZeroFlag() == null || this.getZeroFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getZeroFlag());
			}
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getNeedAcc());
			}
			if(this.getPayAimClass() == null || this.getPayAimClass().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getPayAimClass());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getMakeTime());
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
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getStandbyflag2());
			}
			pstmt.setInt(35, this.getStandbyflag3());
			pstmt.setInt(36, this.getStandbyflag4());
			pstmt.setDouble(37, this.getStandbyflag5());
			pstmt.setDouble(38, this.getStandbyflag6());
			// set where condition
			if(this.getPayPlanCode2() == null || this.getPayPlanCode2().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getPayPlanCode2());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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
		SQLString sqlObj = new SQLString("PD_LMDutyPay_Lib");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMDutyPay_Lib(PayPlanCode2 ,CalCode ,PayPlanName ,Type ,PayIntv ,PayEndYearFlag ,PayEndYear ,PayEndDateCalRef ,PayEndDateCalMode ,DefaultVal ,CnterCalCode ,OthCalCode ,Rate ,MinPay ,AssuYield ,FeeRate ,PayToDateCalMode ,UrgePayFlag ,PayLackFlag ,PayOverFlag ,PayOverDeal ,AvoidPayFlag ,GracePeriod ,PubFlag ,ZeroFlag ,NeedAcc ,PayAimClass ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getPayPlanCode2() == null || this.getPayPlanCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPayPlanCode2());
			}
			if(this.getCalCode() == null || this.getCalCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCalCode());
			}
			if(this.getPayPlanName() == null || this.getPayPlanName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayPlanName());
			}
			if(this.getType() == null || this.getType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getType());
			}
			pstmt.setInt(5, this.getPayIntv());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayEndYearFlag());
			}
			pstmt.setInt(7, this.getPayEndYear());
			if(this.getPayEndDateCalRef() == null || this.getPayEndDateCalRef().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPayEndDateCalRef());
			}
			if(this.getPayEndDateCalMode() == null || this.getPayEndDateCalMode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getPayEndDateCalMode());
			}
			pstmt.setDouble(10, this.getDefaultVal());
			if(this.getCnterCalCode() == null || this.getCnterCalCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCnterCalCode());
			}
			if(this.getOthCalCode() == null || this.getOthCalCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getOthCalCode());
			}
			pstmt.setDouble(13, this.getRate());
			pstmt.setDouble(14, this.getMinPay());
			pstmt.setDouble(15, this.getAssuYield());
			pstmt.setDouble(16, this.getFeeRate());
			if(this.getPayToDateCalMode() == null || this.getPayToDateCalMode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPayToDateCalMode());
			}
			if(this.getUrgePayFlag() == null || this.getUrgePayFlag().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getUrgePayFlag());
			}
			if(this.getPayLackFlag() == null || this.getPayLackFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPayLackFlag());
			}
			if(this.getPayOverFlag() == null || this.getPayOverFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPayOverFlag());
			}
			if(this.getPayOverDeal() == null || this.getPayOverDeal().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPayOverDeal());
			}
			if(this.getAvoidPayFlag() == null || this.getAvoidPayFlag().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAvoidPayFlag());
			}
			pstmt.setInt(23, this.getGracePeriod());
			if(this.getPubFlag() == null || this.getPubFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPubFlag());
			}
			if(this.getZeroFlag() == null || this.getZeroFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getZeroFlag());
			}
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getNeedAcc());
			}
			if(this.getPayAimClass() == null || this.getPayAimClass().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getPayAimClass());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getMakeTime());
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
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getStandbyflag2());
			}
			pstmt.setInt(35, this.getStandbyflag3());
			pstmt.setInt(36, this.getStandbyflag4());
			pstmt.setDouble(37, this.getStandbyflag5());
			pstmt.setDouble(38, this.getStandbyflag6());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMDutyPay_Lib WHERE  PayPlanCode2 = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getPayPlanCode2() == null || this.getPayPlanCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPayPlanCode2());
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
					tError.moduleName = "PD_LMDutyPay_LibDB";
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
			tError.moduleName = "PD_LMDutyPay_LibDB";
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

	public PD_LMDutyPay_LibSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyPay_LibSet aPD_LMDutyPay_LibSet = new PD_LMDutyPay_LibSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMDutyPay_Lib");
			PD_LMDutyPay_LibSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				PD_LMDutyPay_LibSchema s1 = new PD_LMDutyPay_LibSchema();
				s1.setSchema(rs,i);
				aPD_LMDutyPay_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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

		return aPD_LMDutyPay_LibSet;
	}

	public PD_LMDutyPay_LibSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyPay_LibSet aPD_LMDutyPay_LibSet = new PD_LMDutyPay_LibSet();

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
				PD_LMDutyPay_LibSchema s1 = new PD_LMDutyPay_LibSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyPay_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyPay_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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

		return aPD_LMDutyPay_LibSet;
	}

	public PD_LMDutyPay_LibSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyPay_LibSet aPD_LMDutyPay_LibSet = new PD_LMDutyPay_LibSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMDutyPay_Lib");
			PD_LMDutyPay_LibSchema aSchema = this.getSchema();
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

				PD_LMDutyPay_LibSchema s1 = new PD_LMDutyPay_LibSchema();
				s1.setSchema(rs,i);
				aPD_LMDutyPay_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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

		return aPD_LMDutyPay_LibSet;
	}

	public PD_LMDutyPay_LibSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyPay_LibSet aPD_LMDutyPay_LibSet = new PD_LMDutyPay_LibSet();

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

				PD_LMDutyPay_LibSchema s1 = new PD_LMDutyPay_LibSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyPay_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyPay_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPay_LibDB";
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

		return aPD_LMDutyPay_LibSet;
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
			SQLString sqlObj = new SQLString("PD_LMDutyPay_Lib");
			PD_LMDutyPay_LibSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMDutyPay_Lib " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMDutyPay_LibDB";
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
			tError.moduleName = "PD_LMDutyPay_LibDB";
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
        tError.moduleName = "PD_LMDutyPay_LibDB";
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
        tError.moduleName = "PD_LMDutyPay_LibDB";
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
        tError.moduleName = "PD_LMDutyPay_LibDB";
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
        tError.moduleName = "PD_LMDutyPay_LibDB";
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
 * @return PD_LMDutyPay_LibSet
 */
public PD_LMDutyPay_LibSet getData()
{
    int tCount = 0;
    PD_LMDutyPay_LibSet tPD_LMDutyPay_LibSet = new PD_LMDutyPay_LibSet();
    PD_LMDutyPay_LibSchema tPD_LMDutyPay_LibSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMDutyPay_LibDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMDutyPay_LibSchema = new PD_LMDutyPay_LibSchema();
        tPD_LMDutyPay_LibSchema.setSchema(mResultSet, 1);
        tPD_LMDutyPay_LibSet.add(tPD_LMDutyPay_LibSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMDutyPay_LibSchema = new PD_LMDutyPay_LibSchema();
                tPD_LMDutyPay_LibSchema.setSchema(mResultSet, 1);
                tPD_LMDutyPay_LibSet.add(tPD_LMDutyPay_LibSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMDutyPay_LibDB";
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
    return tPD_LMDutyPay_LibSet;
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
            tError.moduleName = "PD_LMDutyPay_LibDB";
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
        tError.moduleName = "PD_LMDutyPay_LibDB";
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
            tError.moduleName = "PD_LMDutyPay_LibDB";
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
        tError.moduleName = "PD_LMDutyPay_LibDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
