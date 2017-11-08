

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIRecordTraceSchema;
import com.sinosoft.lis.vschema.RIRecordTraceSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIRecordTraceDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIRecordTraceDBSet extends RIRecordTraceSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public RIRecordTraceDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIRecordTrace");
		mflag = true;
	}

	public RIRecordTraceDBSet()
	{
		db = new DBOper( "RIRecordTrace" );
	}
	// @Method
	public boolean deleteSQL()
	{
		if (db.deleteSQL(this))
		{
		        return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

    /**
     * 删除操作
     * 删除条件：主键
     * @return boolean
     */
	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM RIRecordTrace WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIRecordTrace");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 更新操作
     * 更新条件：主键
     * @return boolean
     */
	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE RIRecordTrace SET  SerialNo = ? , BatchNo = ? , AccumulateDefNO = ? , RIPreceptNo = ? , OtherNo = ? , ContNo = ? , AreaID = ? , EventNo = ? , EventType = ? , RiskCode = ? , DutyCode = ? , IncomeCompanyNo = ? , FeeType = ? , FeeMoney = ? , CurentAmnt = ? , CessionAmount = ? , CessionRate = ? , AmountChang = ? , PremChang = ? , CommChang = ? , ReturnPay = ? , ReturnFee = ? , AddSubFlag = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? , StandbyString4 = ? , StandbyDouble1 = ? , StandbyDouble2 = ? , StandbyDouble3 = ? , StandbyDouble4 = ? , StandbyDouble5 = ? , StandbyDouble6 = ? , StandbyDouble7 = ? , StandbyDouble8 = ? , StandbyDouble9 = ? , StandbyDate1 = ? , StandbyDate2 = ? , ReinsureType = ? , RIDate = ? , FIDate = ? , Currency = ? , BillNo = ? , BillBatchNo = ? , SettleFlag = ? , PrePremChang = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBatchNo());
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRIPreceptNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOtherNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getContNo());
			}
			if(this.get(i).getAreaID() == null || this.get(i).getAreaID().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAreaID());
			}
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getEventNo());
			}
			if(this.get(i).getEventType() == null || this.get(i).getEventType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEventType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDutyCode());
			}
			if(this.get(i).getIncomeCompanyNo() == null || this.get(i).getIncomeCompanyNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIncomeCompanyNo());
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getFeeType());
			}
			pstmt.setDouble(14, this.get(i).getFeeMoney());
			pstmt.setDouble(15, this.get(i).getCurentAmnt());
			pstmt.setDouble(16, this.get(i).getCessionAmount());
			pstmt.setDouble(17, this.get(i).getCessionRate());
			pstmt.setDouble(18, this.get(i).getAmountChang());
			pstmt.setDouble(19, this.get(i).getPremChang());
			pstmt.setDouble(20, this.get(i).getCommChang());
			pstmt.setDouble(21, this.get(i).getReturnPay());
			pstmt.setDouble(22, this.get(i).getReturnFee());
			if(this.get(i).getAddSubFlag() == null || this.get(i).getAddSubFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAddSubFlag());
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandbyString3());
			}
			if(this.get(i).getStandbyString4() == null || this.get(i).getStandbyString4().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStandbyString4());
			}
			pstmt.setDouble(28, this.get(i).getStandbyDouble1());
			pstmt.setDouble(29, this.get(i).getStandbyDouble2());
			pstmt.setDouble(30, this.get(i).getStandbyDouble3());
			pstmt.setDouble(31, this.get(i).getStandbyDouble4());
			pstmt.setDouble(32, this.get(i).getStandbyDouble5());
			pstmt.setDouble(33, this.get(i).getStandbyDouble6());
			pstmt.setDouble(34, this.get(i).getStandbyDouble7());
			pstmt.setDouble(35, this.get(i).getStandbyDouble8());
			pstmt.setDouble(36, this.get(i).getStandbyDouble9());
			pstmt.setDouble(37, this.get(i).getStandbyDate1());
			pstmt.setDouble(38, this.get(i).getStandbyDate2());
			if(this.get(i).getReinsureType() == null || this.get(i).getReinsureType().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getReinsureType());
			}
			if(this.get(i).getRIDate() == null || this.get(i).getRIDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getRIDate()));
			}
			if(this.get(i).getFIDate() == null || this.get(i).getFIDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getFIDate()));
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCurrency());
			}
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getBillNo());
			}
			if(this.get(i).getBillBatchNo() == null || this.get(i).getBillBatchNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBillBatchNo());
			}
			if(this.get(i).getSettleFlag() == null || this.get(i).getSettleFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getSettleFlag());
			}
			pstmt.setDouble(46, this.get(i).getPrePremChang());
			// set where condition
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIRecordTrace");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 新增操作
     * @return boolean
     */
	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO RIRecordTrace(SerialNo ,BatchNo ,AccumulateDefNO ,RIPreceptNo ,OtherNo ,ContNo ,AreaID ,EventNo ,EventType ,RiskCode ,DutyCode ,IncomeCompanyNo ,FeeType ,FeeMoney ,CurentAmnt ,CessionAmount ,CessionRate ,AmountChang ,PremChang ,CommChang ,ReturnPay ,ReturnFee ,AddSubFlag ,StandbyString1 ,StandbyString2 ,StandbyString3 ,StandbyString4 ,StandbyDouble1 ,StandbyDouble2 ,StandbyDouble3 ,StandbyDouble4 ,StandbyDouble5 ,StandbyDouble6 ,StandbyDouble7 ,StandbyDouble8 ,StandbyDouble9 ,StandbyDate1 ,StandbyDate2 ,ReinsureType ,RIDate ,FIDate ,Currency ,BillNo ,BillBatchNo ,SettleFlag ,PrePremChang) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBatchNo());
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRIPreceptNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOtherNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getContNo());
			}
			if(this.get(i).getAreaID() == null || this.get(i).getAreaID().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAreaID());
			}
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getEventNo());
			}
			if(this.get(i).getEventType() == null || this.get(i).getEventType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEventType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDutyCode());
			}
			if(this.get(i).getIncomeCompanyNo() == null || this.get(i).getIncomeCompanyNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getIncomeCompanyNo());
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getFeeType());
			}
			pstmt.setDouble(14, this.get(i).getFeeMoney());
			pstmt.setDouble(15, this.get(i).getCurentAmnt());
			pstmt.setDouble(16, this.get(i).getCessionAmount());
			pstmt.setDouble(17, this.get(i).getCessionRate());
			pstmt.setDouble(18, this.get(i).getAmountChang());
			pstmt.setDouble(19, this.get(i).getPremChang());
			pstmt.setDouble(20, this.get(i).getCommChang());
			pstmt.setDouble(21, this.get(i).getReturnPay());
			pstmt.setDouble(22, this.get(i).getReturnFee());
			if(this.get(i).getAddSubFlag() == null || this.get(i).getAddSubFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAddSubFlag());
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandbyString3());
			}
			if(this.get(i).getStandbyString4() == null || this.get(i).getStandbyString4().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStandbyString4());
			}
			pstmt.setDouble(28, this.get(i).getStandbyDouble1());
			pstmt.setDouble(29, this.get(i).getStandbyDouble2());
			pstmt.setDouble(30, this.get(i).getStandbyDouble3());
			pstmt.setDouble(31, this.get(i).getStandbyDouble4());
			pstmt.setDouble(32, this.get(i).getStandbyDouble5());
			pstmt.setDouble(33, this.get(i).getStandbyDouble6());
			pstmt.setDouble(34, this.get(i).getStandbyDouble7());
			pstmt.setDouble(35, this.get(i).getStandbyDouble8());
			pstmt.setDouble(36, this.get(i).getStandbyDouble9());
			pstmt.setDouble(37, this.get(i).getStandbyDate1());
			pstmt.setDouble(38, this.get(i).getStandbyDate2());
			if(this.get(i).getReinsureType() == null || this.get(i).getReinsureType().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getReinsureType());
			}
			if(this.get(i).getRIDate() == null || this.get(i).getRIDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getRIDate()));
			}
			if(this.get(i).getFIDate() == null || this.get(i).getFIDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getFIDate()));
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCurrency());
			}
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getBillNo());
			}
			if(this.get(i).getBillBatchNo() == null || this.get(i).getBillBatchNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBillBatchNo());
			}
			if(this.get(i).getSettleFlag() == null || this.get(i).getSettleFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getSettleFlag());
			}
			pstmt.setDouble(46, this.get(i).getPrePremChang());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIRecordTrace");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIRecordTraceDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

}
