

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.RIRecordTraceBSchema;
import com.sinosoft.lis.vschema.RIRecordTraceBSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIRecordTraceBDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIRecordTraceBDB extends RIRecordTraceBSchema
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
	public RIRecordTraceBDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "RIRecordTraceB" );
		mflag = true;
	}

	public RIRecordTraceBDB()
	{
		con = null;
		db = new DBOper( "RIRecordTraceB" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		RIRecordTraceBSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		RIRecordTraceBSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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
			pstmt = con.prepareStatement("DELETE FROM RIRecordTraceB WHERE  BakeSerialNo = ? AND SerialNo = ?");
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIRecordTraceB");
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
		SQLString sqlObj = new SQLString("RIRecordTraceB");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE RIRecordTraceB SET  BakeSerialNo = ? , SerialNo = ? , BatchNo = ? , AccumulateDefNO = ? , RIPreceptNo = ? , OtherNo = ? , ContNo = ? , AreaID = ? , EventNo = ? , EventType = ? , RiskCode = ? , DutyCode = ? , IncomeCompanyNo = ? , FeeType = ? , FeeMoney = ? , CurentAmnt = ? , CessionAmount = ? , CessionRate = ? , AmountChang = ? , PremChang = ? , CommChang = ? , ReturnPay = ? , ReturnFee = ? , AddSubFlag = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? , StandbyString4 = ? , StandbyDouble1 = ? , StandbyDouble2 = ? , StandbyDouble3 = ? , StandbyDouble4 = ? , StandbyDouble5 = ? , StandbyDouble6 = ? , StandbyDouble7 = ? , StandbyDouble8 = ? , StandbyDouble9 = ? , StandbyDate1 = ? , StandbyDate2 = ? , ReinsureType = ? , RIDate = ? , FIDate = ? , Currency = ? , BillNo = ? , BillBatchNo = ? , SettleFlag = ? , PrePremChang = ? WHERE  BakeSerialNo = ? AND SerialNo = ?");
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getSerialNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBatchNo());
			}
			if(this.getAccumulateDefNO() == null || this.getAccumulateDefNO().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAccumulateDefNO());
			}
			if(this.getRIPreceptNo() == null || this.getRIPreceptNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRIPreceptNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getOtherNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getContNo());
			}
			if(this.getAreaID() == null || this.getAreaID().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAreaID());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getEventNo());
			}
			if(this.getEventType() == null || this.getEventType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getEventType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getDutyCode());
			}
			if(this.getIncomeCompanyNo() == null || this.getIncomeCompanyNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getIncomeCompanyNo());
			}
			if(this.getFeeType() == null || this.getFeeType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getFeeType());
			}
			pstmt.setDouble(15, this.getFeeMoney());
			pstmt.setDouble(16, this.getCurentAmnt());
			pstmt.setDouble(17, this.getCessionAmount());
			pstmt.setDouble(18, this.getCessionRate());
			pstmt.setDouble(19, this.getAmountChang());
			pstmt.setDouble(20, this.getPremChang());
			pstmt.setDouble(21, this.getCommChang());
			pstmt.setDouble(22, this.getReturnPay());
			pstmt.setDouble(23, this.getReturnFee());
			if(this.getAddSubFlag() == null || this.getAddSubFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAddSubFlag());
			}
			if(this.getStandbyString1() == null || this.getStandbyString1().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStandbyString1());
			}
			if(this.getStandbyString2() == null || this.getStandbyString2().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStandbyString2());
			}
			if(this.getStandbyString3() == null || this.getStandbyString3().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStandbyString3());
			}
			if(this.getStandbyString4() == null || this.getStandbyString4().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStandbyString4());
			}
			pstmt.setDouble(29, this.getStandbyDouble1());
			pstmt.setDouble(30, this.getStandbyDouble2());
			pstmt.setDouble(31, this.getStandbyDouble3());
			pstmt.setDouble(32, this.getStandbyDouble4());
			pstmt.setDouble(33, this.getStandbyDouble5());
			pstmt.setDouble(34, this.getStandbyDouble6());
			pstmt.setDouble(35, this.getStandbyDouble7());
			pstmt.setDouble(36, this.getStandbyDouble8());
			pstmt.setDouble(37, this.getStandbyDouble9());
			pstmt.setDouble(38, this.getStandbyDate1());
			pstmt.setDouble(39, this.getStandbyDate2());
			if(this.getReinsureType() == null || this.getReinsureType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getReinsureType());
			}
			if(this.getRIDate() == null || this.getRIDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getRIDate()));
			}
			if(this.getFIDate() == null || this.getFIDate().equals("null")) {
				pstmt.setNull(42, 91);
			} else {
				pstmt.setDate(42, Date.valueOf(this.getFIDate()));
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getCurrency());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getBillNo());
			}
			if(this.getBillBatchNo() == null || this.getBillBatchNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getBillBatchNo());
			}
			if(this.getSettleFlag() == null || this.getSettleFlag().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getSettleFlag());
			}
			pstmt.setDouble(47, this.getPrePremChang());
			// set where condition
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getBakeSerialNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getSerialNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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
		SQLString sqlObj = new SQLString("RIRecordTraceB");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO RIRecordTraceB(BakeSerialNo ,SerialNo ,BatchNo ,AccumulateDefNO ,RIPreceptNo ,OtherNo ,ContNo ,AreaID ,EventNo ,EventType ,RiskCode ,DutyCode ,IncomeCompanyNo ,FeeType ,FeeMoney ,CurentAmnt ,CessionAmount ,CessionRate ,AmountChang ,PremChang ,CommChang ,ReturnPay ,ReturnFee ,AddSubFlag ,StandbyString1 ,StandbyString2 ,StandbyString3 ,StandbyString4 ,StandbyDouble1 ,StandbyDouble2 ,StandbyDouble3 ,StandbyDouble4 ,StandbyDouble5 ,StandbyDouble6 ,StandbyDouble7 ,StandbyDouble8 ,StandbyDouble9 ,StandbyDate1 ,StandbyDate2 ,ReinsureType ,RIDate ,FIDate ,Currency ,BillNo ,BillBatchNo ,SettleFlag ,PrePremChang) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getSerialNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBatchNo());
			}
			if(this.getAccumulateDefNO() == null || this.getAccumulateDefNO().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAccumulateDefNO());
			}
			if(this.getRIPreceptNo() == null || this.getRIPreceptNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRIPreceptNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getOtherNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getContNo());
			}
			if(this.getAreaID() == null || this.getAreaID().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAreaID());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getEventNo());
			}
			if(this.getEventType() == null || this.getEventType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getEventType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getDutyCode());
			}
			if(this.getIncomeCompanyNo() == null || this.getIncomeCompanyNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getIncomeCompanyNo());
			}
			if(this.getFeeType() == null || this.getFeeType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getFeeType());
			}
			pstmt.setDouble(15, this.getFeeMoney());
			pstmt.setDouble(16, this.getCurentAmnt());
			pstmt.setDouble(17, this.getCessionAmount());
			pstmt.setDouble(18, this.getCessionRate());
			pstmt.setDouble(19, this.getAmountChang());
			pstmt.setDouble(20, this.getPremChang());
			pstmt.setDouble(21, this.getCommChang());
			pstmt.setDouble(22, this.getReturnPay());
			pstmt.setDouble(23, this.getReturnFee());
			if(this.getAddSubFlag() == null || this.getAddSubFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAddSubFlag());
			}
			if(this.getStandbyString1() == null || this.getStandbyString1().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStandbyString1());
			}
			if(this.getStandbyString2() == null || this.getStandbyString2().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStandbyString2());
			}
			if(this.getStandbyString3() == null || this.getStandbyString3().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStandbyString3());
			}
			if(this.getStandbyString4() == null || this.getStandbyString4().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStandbyString4());
			}
			pstmt.setDouble(29, this.getStandbyDouble1());
			pstmt.setDouble(30, this.getStandbyDouble2());
			pstmt.setDouble(31, this.getStandbyDouble3());
			pstmt.setDouble(32, this.getStandbyDouble4());
			pstmt.setDouble(33, this.getStandbyDouble5());
			pstmt.setDouble(34, this.getStandbyDouble6());
			pstmt.setDouble(35, this.getStandbyDouble7());
			pstmt.setDouble(36, this.getStandbyDouble8());
			pstmt.setDouble(37, this.getStandbyDouble9());
			pstmt.setDouble(38, this.getStandbyDate1());
			pstmt.setDouble(39, this.getStandbyDate2());
			if(this.getReinsureType() == null || this.getReinsureType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getReinsureType());
			}
			if(this.getRIDate() == null || this.getRIDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getRIDate()));
			}
			if(this.getFIDate() == null || this.getFIDate().equals("null")) {
				pstmt.setNull(42, 91);
			} else {
				pstmt.setDate(42, Date.valueOf(this.getFIDate()));
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getCurrency());
			}
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getBillNo());
			}
			if(this.getBillBatchNo() == null || this.getBillBatchNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getBillBatchNo());
			}
			if(this.getSettleFlag() == null || this.getSettleFlag().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getSettleFlag());
			}
			pstmt.setDouble(47, this.getPrePremChang());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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
			pstmt = con.prepareStatement("SELECT * FROM RIRecordTraceB WHERE  BakeSerialNo = ? AND SerialNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getSerialNo());
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
					tError.moduleName = "RIRecordTraceBDB";
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
			tError.moduleName = "RIRecordTraceBDB";
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

	public RIRecordTraceBSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIRecordTraceBSet aRIRecordTraceBSet = new RIRecordTraceBSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIRecordTraceB");
			RIRecordTraceBSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				RIRecordTraceBSchema s1 = new RIRecordTraceBSchema();
				s1.setSchema(rs,i);
				aRIRecordTraceBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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

		return aRIRecordTraceBSet;
	}

	public RIRecordTraceBSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIRecordTraceBSet aRIRecordTraceBSet = new RIRecordTraceBSet();

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
				RIRecordTraceBSchema s1 = new RIRecordTraceBSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIRecordTraceBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIRecordTraceBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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

		return aRIRecordTraceBSet;
	}

	public RIRecordTraceBSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIRecordTraceBSet aRIRecordTraceBSet = new RIRecordTraceBSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIRecordTraceB");
			RIRecordTraceBSchema aSchema = this.getSchema();
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

				RIRecordTraceBSchema s1 = new RIRecordTraceBSchema();
				s1.setSchema(rs,i);
				aRIRecordTraceBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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

		return aRIRecordTraceBSet;
	}

	public RIRecordTraceBSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIRecordTraceBSet aRIRecordTraceBSet = new RIRecordTraceBSet();

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

				RIRecordTraceBSchema s1 = new RIRecordTraceBSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIRecordTraceBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIRecordTraceBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceBDB";
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

		return aRIRecordTraceBSet;
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
			SQLString sqlObj = new SQLString("RIRecordTraceB");
			RIRecordTraceBSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update RIRecordTraceB " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "RIRecordTraceBDB";
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
			tError.moduleName = "RIRecordTraceBDB";
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
        tError.moduleName = "RIRecordTraceBDB";
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
        tError.moduleName = "RIRecordTraceBDB";
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
        tError.moduleName = "RIRecordTraceBDB";
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
        tError.moduleName = "RIRecordTraceBDB";
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
 * @return RIRecordTraceBSet
 */
public RIRecordTraceBSet getData()
{
    int tCount = 0;
    RIRecordTraceBSet tRIRecordTraceBSet = new RIRecordTraceBSet();
    RIRecordTraceBSchema tRIRecordTraceBSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "RIRecordTraceBDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tRIRecordTraceBSchema = new RIRecordTraceBSchema();
        tRIRecordTraceBSchema.setSchema(mResultSet, 1);
        tRIRecordTraceBSet.add(tRIRecordTraceBSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tRIRecordTraceBSchema = new RIRecordTraceBSchema();
                tRIRecordTraceBSchema.setSchema(mResultSet, 1);
                tRIRecordTraceBSet.add(tRIRecordTraceBSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "RIRecordTraceBDB";
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
    return tRIRecordTraceBSet;
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
            tError.moduleName = "RIRecordTraceBDB";
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
        tError.moduleName = "RIRecordTraceBDB";
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
            tError.moduleName = "RIRecordTraceBDB";
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
        tError.moduleName = "RIRecordTraceBDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
