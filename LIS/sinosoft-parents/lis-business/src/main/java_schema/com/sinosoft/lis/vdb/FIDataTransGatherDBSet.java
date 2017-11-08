/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FIDataTransGatherSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataTransGatherDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2009-01-09
 */
public class FIDataTransGatherDBSet extends FIDataTransGatherSet
{
private static Logger logger = Logger.getLogger(FIDataTransGatherDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FIDataTransGatherDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIDataTransGather");
		mflag = true;
	}

	public FIDataTransGatherDBSet()
	{
		db = new DBOper( "FIDataTransGather" );
		con = null;
		mflag = false;
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
			tError.moduleName = "FIDataTransGatherDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIDataTransGather WHERE  FSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFSerialNo().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransGather");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();
            }

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
			pstmt = con.prepareStatement("UPDATE FIDataTransGather SET  FSerialNo = ? , BatchNo = ? , CertificateID = ? , AccountCode = ? , FinItemType = ? , SumMoney = ? , AccountDate = ? , SaleChnl = ? , ManageCom = ? , ExecuteCom = ? , RiskCode = ? , CostCenter = ? , NotesNo = ? , CustomerID = ? , Budget = ? , CashFlow = ? , Currency = ? , ReMark = ? , StandByString1 = ? , StandByString2 = ? , StandByString3 = ? , StandByString4 = ? , StandByString5 = ? , StandByString6 = ? , StandByString7 = ? , StandByString8 = ? , StandByString9 = ? , StandByString10 = ? , StandByString11 = ? , StandByString12 = ? , StandByString13 = ? , StandByString14 = ? , StandByString15 = ? , StandByNum1 = ? , StandByNum2 = ? , StandByDate1 = ? , StandByDate2 = ? , ReturnFlag = ? , VoucherNo = ? , YearMonth = ? , VerifyFlag = ? , ErrorInfo = ? , ReturnDate = ? , ReturnTime = ? , DealOperator = ? WHERE  FSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFSerialNo().trim());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getAccountCode() == null || this.get(i).getAccountCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccountCode().trim());
			}
			if(this.get(i).getFinItemType() == null || this.get(i).getFinItemType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFinItemType().trim());
			}
			pstmt.setDouble(6, this.get(i).getSumMoney());
			if(this.get(i).getAccountDate() == null || this.get(i).getAccountDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getAccountDate()));
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSaleChnl().trim());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom().trim());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExecuteCom().trim());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRiskCode().trim());
			}
			if(this.get(i).getCostCenter() == null || this.get(i).getCostCenter().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCostCenter().trim());
			}
			if(this.get(i).getNotesNo() == null || this.get(i).getNotesNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getNotesNo().trim());
			}
			if(this.get(i).getCustomerID() == null || this.get(i).getCustomerID().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCustomerID().trim());
			}
			if(this.get(i).getBudget() == null || this.get(i).getBudget().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBudget().trim());
			}
			if(this.get(i).getCashFlow() == null || this.get(i).getCashFlow().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCashFlow().trim());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCurrency().trim());
			}
			if(this.get(i).getReMark() == null || this.get(i).getReMark().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getReMark().trim());
			}
			if(this.get(i).getStandByString1() == null || this.get(i).getStandByString1().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getStandByString1().trim());
			}
			if(this.get(i).getStandByString2() == null || this.get(i).getStandByString2().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStandByString2().trim());
			}
			if(this.get(i).getStandByString3() == null || this.get(i).getStandByString3().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandByString3().trim());
			}
			if(this.get(i).getStandByString4() == null || this.get(i).getStandByString4().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandByString4().trim());
			}
			if(this.get(i).getStandByString5() == null || this.get(i).getStandByString5().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStandByString5().trim());
			}
			if(this.get(i).getStandByString6() == null || this.get(i).getStandByString6().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandByString6().trim());
			}
			if(this.get(i).getStandByString7() == null || this.get(i).getStandByString7().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandByString7().trim());
			}
			if(this.get(i).getStandByString8() == null || this.get(i).getStandByString8().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandByString8().trim());
			}
			if(this.get(i).getStandByString9() == null || this.get(i).getStandByString9().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStandByString9().trim());
			}
			if(this.get(i).getStandByString10() == null || this.get(i).getStandByString10().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStandByString10().trim());
			}
			if(this.get(i).getStandByString11() == null || this.get(i).getStandByString11().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getStandByString11().trim());
			}
			if(this.get(i).getStandByString12() == null || this.get(i).getStandByString12().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getStandByString12().trim());
			}
			if(this.get(i).getStandByString13() == null || this.get(i).getStandByString13().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getStandByString13().trim());
			}
			if(this.get(i).getStandByString14() == null || this.get(i).getStandByString14().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getStandByString14().trim());
			}
			if(this.get(i).getStandByString15() == null || this.get(i).getStandByString15().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getStandByString15().trim());
			}
			pstmt.setDouble(34, this.get(i).getStandByNum1());
			pstmt.setDouble(35, this.get(i).getStandByNum2());
			if(this.get(i).getStandByDate1() == null || this.get(i).getStandByDate1().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getStandByDate1()));
			}
			if(this.get(i).getStandByDate2() == null || this.get(i).getStandByDate2().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getStandByDate2()));
			}
			if(this.get(i).getReturnFlag() == null || this.get(i).getReturnFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getReturnFlag().trim());
			}
			if(this.get(i).getVoucherNo() == null || this.get(i).getVoucherNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getVoucherNo().trim());
			}
			if(this.get(i).getYearMonth() == null || this.get(i).getYearMonth().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getYearMonth().trim());
			}
			if(this.get(i).getVerifyFlag() == null || this.get(i).getVerifyFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getVerifyFlag().trim());
			}
			if(this.get(i).getErrorInfo() == null || this.get(i).getErrorInfo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getErrorInfo().trim());
			}
			if(this.get(i).getReturnDate() == null || this.get(i).getReturnDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getReturnDate()));
			}
			if(this.get(i).getReturnTime() == null || this.get(i).getReturnTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getReturnTime().trim());
			}
			if(this.get(i).getDealOperator() == null || this.get(i).getDealOperator().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getDealOperator().trim());
			}
			// set where condition
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getFSerialNo().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransGather");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();
            }

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
			pstmt = con.prepareStatement("INSERT INTO FIDataTransGather VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFSerialNo().trim());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getAccountCode() == null || this.get(i).getAccountCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccountCode().trim());
			}
			if(this.get(i).getFinItemType() == null || this.get(i).getFinItemType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFinItemType().trim());
			}
			pstmt.setDouble(6, this.get(i).getSumMoney());
			if(this.get(i).getAccountDate() == null || this.get(i).getAccountDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getAccountDate()));
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSaleChnl().trim());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom().trim());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExecuteCom().trim());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRiskCode().trim());
			}
			if(this.get(i).getCostCenter() == null || this.get(i).getCostCenter().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCostCenter().trim());
			}
			if(this.get(i).getNotesNo() == null || this.get(i).getNotesNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getNotesNo().trim());
			}
			if(this.get(i).getCustomerID() == null || this.get(i).getCustomerID().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCustomerID().trim());
			}
			if(this.get(i).getBudget() == null || this.get(i).getBudget().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBudget().trim());
			}
			if(this.get(i).getCashFlow() == null || this.get(i).getCashFlow().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCashFlow().trim());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCurrency().trim());
			}
			if(this.get(i).getReMark() == null || this.get(i).getReMark().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getReMark().trim());
			}
			if(this.get(i).getStandByString1() == null || this.get(i).getStandByString1().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getStandByString1().trim());
			}
			if(this.get(i).getStandByString2() == null || this.get(i).getStandByString2().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStandByString2().trim());
			}
			if(this.get(i).getStandByString3() == null || this.get(i).getStandByString3().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandByString3().trim());
			}
			if(this.get(i).getStandByString4() == null || this.get(i).getStandByString4().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandByString4().trim());
			}
			if(this.get(i).getStandByString5() == null || this.get(i).getStandByString5().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStandByString5().trim());
			}
			if(this.get(i).getStandByString6() == null || this.get(i).getStandByString6().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandByString6().trim());
			}
			if(this.get(i).getStandByString7() == null || this.get(i).getStandByString7().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandByString7().trim());
			}
			if(this.get(i).getStandByString8() == null || this.get(i).getStandByString8().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandByString8().trim());
			}
			if(this.get(i).getStandByString9() == null || this.get(i).getStandByString9().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStandByString9().trim());
			}
			if(this.get(i).getStandByString10() == null || this.get(i).getStandByString10().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStandByString10().trim());
			}
			if(this.get(i).getStandByString11() == null || this.get(i).getStandByString11().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getStandByString11().trim());
			}
			if(this.get(i).getStandByString12() == null || this.get(i).getStandByString12().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getStandByString12().trim());
			}
			if(this.get(i).getStandByString13() == null || this.get(i).getStandByString13().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getStandByString13().trim());
			}
			if(this.get(i).getStandByString14() == null || this.get(i).getStandByString14().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getStandByString14().trim());
			}
			if(this.get(i).getStandByString15() == null || this.get(i).getStandByString15().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getStandByString15().trim());
			}
			pstmt.setDouble(34, this.get(i).getStandByNum1());
			pstmt.setDouble(35, this.get(i).getStandByNum2());
			if(this.get(i).getStandByDate1() == null || this.get(i).getStandByDate1().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getStandByDate1()));
			}
			if(this.get(i).getStandByDate2() == null || this.get(i).getStandByDate2().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getStandByDate2()));
			}
			if(this.get(i).getReturnFlag() == null || this.get(i).getReturnFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getReturnFlag().trim());
			}
			if(this.get(i).getVoucherNo() == null || this.get(i).getVoucherNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getVoucherNo().trim());
			}
			if(this.get(i).getYearMonth() == null || this.get(i).getYearMonth().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getYearMonth().trim());
			}
			if(this.get(i).getVerifyFlag() == null || this.get(i).getVerifyFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getVerifyFlag().trim());
			}
			if(this.get(i).getErrorInfo() == null || this.get(i).getErrorInfo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getErrorInfo().trim());
			}
			if(this.get(i).getReturnDate() == null || this.get(i).getReturnDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getReturnDate()));
			}
			if(this.get(i).getReturnTime() == null || this.get(i).getReturnTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getReturnTime().trim());
			}
			if(this.get(i).getDealOperator() == null || this.get(i).getDealOperator().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getDealOperator().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransGather");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();
            }

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
