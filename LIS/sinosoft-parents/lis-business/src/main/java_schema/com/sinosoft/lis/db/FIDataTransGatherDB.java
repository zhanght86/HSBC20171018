/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.FIDataTransGatherSchema;
import com.sinosoft.lis.vschema.FIDataTransGatherSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataTransGatherDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2009-01-09
 */
public class FIDataTransGatherDB extends FIDataTransGatherSchema
{
private static Logger logger = Logger.getLogger(FIDataTransGatherDB.class);

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
	public FIDataTransGatherDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "FIDataTransGather" );
		mflag = true;
	}

	public FIDataTransGatherDB()
	{
		con = null;
		db = new DBOper( "FIDataTransGather" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		FIDataTransGatherSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		FIDataTransGatherSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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
			pstmt = con.prepareStatement("DELETE FROM FIDataTransGather WHERE  FSerialNo = ?");
			if(this.getFSerialNo() == null || this.getFSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFSerialNo().trim());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransGather");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();
//真实的执行语句
logger.debug(String.valueOf("True Error Sql is : DELETE FROM FIDataTransGather WHERE FSerialNo='"+this.getFSerialNo() + "'").replaceAll("'null'", "null"));

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


		try
		{
			pstmt = con.prepareStatement("UPDATE FIDataTransGather SET  FSerialNo = ? , BatchNo = ? , CertificateID = ? , AccountCode = ? , FinItemType = ? , SumMoney = ? , AccountDate = ? , SaleChnl = ? , ManageCom = ? , ExecuteCom = ? , RiskCode = ? , CostCenter = ? , NotesNo = ? , CustomerID = ? , Budget = ? , CashFlow = ? , Currency = ? , ReMark = ? , StandByString1 = ? , StandByString2 = ? , StandByString3 = ? , StandByString4 = ? , StandByString5 = ? , StandByString6 = ? , StandByString7 = ? , StandByString8 = ? , StandByString9 = ? , StandByString10 = ? , StandByString11 = ? , StandByString12 = ? , StandByString13 = ? , StandByString14 = ? , StandByString15 = ? , StandByNum1 = ? , StandByNum2 = ? , StandByDate1 = ? , StandByDate2 = ? , ReturnFlag = ? , VoucherNo = ? , YearMonth = ? , VerifyFlag = ? , ErrorInfo = ? , ReturnDate = ? , ReturnTime = ? , DealOperator = ? WHERE  FSerialNo = ?");
			if(this.getFSerialNo() == null || this.getFSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFSerialNo().trim());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo().trim());
			}
			if(this.getCertificateID() == null || this.getCertificateID().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCertificateID().trim());
			}
			if(this.getAccountCode() == null || this.getAccountCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAccountCode().trim());
			}
			if(this.getFinItemType() == null || this.getFinItemType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFinItemType().trim());
			}
			pstmt.setDouble(6, this.getSumMoney());
			if(this.getAccountDate() == null || this.getAccountDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getAccountDate()));
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getSaleChnl().trim());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getManageCom().trim());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getExecuteCom().trim());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRiskCode().trim());
			}
			if(this.getCostCenter() == null || this.getCostCenter().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getCostCenter().trim());
			}
			if(this.getNotesNo() == null || this.getNotesNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getNotesNo().trim());
			}
			if(this.getCustomerID() == null || this.getCustomerID().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCustomerID().trim());
			}
			if(this.getBudget() == null || this.getBudget().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getBudget().trim());
			}
			if(this.getCashFlow() == null || this.getCashFlow().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getCashFlow().trim());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getCurrency().trim());
			}
			if(this.getReMark() == null || this.getReMark().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getReMark().trim());
			}
			if(this.getStandByString1() == null || this.getStandByString1().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStandByString1().trim());
			}
			if(this.getStandByString2() == null || this.getStandByString2().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandByString2().trim());
			}
			if(this.getStandByString3() == null || this.getStandByString3().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandByString3().trim());
			}
			if(this.getStandByString4() == null || this.getStandByString4().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getStandByString4().trim());
			}
			if(this.getStandByString5() == null || this.getStandByString5().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getStandByString5().trim());
			}
			if(this.getStandByString6() == null || this.getStandByString6().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getStandByString6().trim());
			}
			if(this.getStandByString7() == null || this.getStandByString7().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStandByString7().trim());
			}
			if(this.getStandByString8() == null || this.getStandByString8().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStandByString8().trim());
			}
			if(this.getStandByString9() == null || this.getStandByString9().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStandByString9().trim());
			}
			if(this.getStandByString10() == null || this.getStandByString10().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStandByString10().trim());
			}
			if(this.getStandByString11() == null || this.getStandByString11().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getStandByString11().trim());
			}
			if(this.getStandByString12() == null || this.getStandByString12().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getStandByString12().trim());
			}
			if(this.getStandByString13() == null || this.getStandByString13().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getStandByString13().trim());
			}
			if(this.getStandByString14() == null || this.getStandByString14().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getStandByString14().trim());
			}
			if(this.getStandByString15() == null || this.getStandByString15().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getStandByString15().trim());
			}
			pstmt.setDouble(34, this.getStandByNum1());
			pstmt.setDouble(35, this.getStandByNum2());
			if(this.getStandByDate1() == null || this.getStandByDate1().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getStandByDate1()));
			}
			if(this.getStandByDate2() == null || this.getStandByDate2().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getStandByDate2()));
			}
			if(this.getReturnFlag() == null || this.getReturnFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getReturnFlag().trim());
			}
			if(this.getVoucherNo() == null || this.getVoucherNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getVoucherNo().trim());
			}
			if(this.getYearMonth() == null || this.getYearMonth().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getYearMonth().trim());
			}
			if(this.getVerifyFlag() == null || this.getVerifyFlag().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getVerifyFlag().trim());
			}
			if(this.getErrorInfo() == null || this.getErrorInfo().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getErrorInfo().trim());
			}
			if(this.getReturnDate() == null || this.getReturnDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getReturnDate()));
			}
			if(this.getReturnTime() == null || this.getReturnTime().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getReturnTime().trim());
			}
			if(this.getDealOperator() == null || this.getDealOperator().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDealOperator().trim());
			}
			// set where condition
			if(this.getFSerialNo() == null || this.getFSerialNo().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getFSerialNo().trim());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransGather");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();
//真实的执行语句
logger.debug(String.valueOf("True Error Sql is : UPDATE FIDataTransGather SET FSerialNo='"+this.getFSerialNo() + "'" + " , BatchNo='"+this.getBatchNo() + "'" + " , CertificateID='"+this.getCertificateID() + "'" + " , AccountCode='"+this.getAccountCode() + "'" + " , FinItemType='"+this.getFinItemType() + "'" + " , SumMoney="+this.getSumMoney() + " , AccountDate='"+this.getAccountDate() + "'" + " , SaleChnl='"+this.getSaleChnl() + "'" + " , ManageCom='"+this.getManageCom() + "'" + " , ExecuteCom='"+this.getExecuteCom() + "'" + " , RiskCode='"+this.getRiskCode() + "'" + " , CostCenter='"+this.getCostCenter() + "'" + " , NotesNo='"+this.getNotesNo() + "'" + " , CustomerID='"+this.getCustomerID() + "'" + " , Budget='"+this.getBudget() + "'" + " , CashFlow='"+this.getCashFlow() + "'" + " , Currency='"+this.getCurrency() + "'" + " , ReMark='"+this.getReMark() + "'" + " , StandByString1='"+this.getStandByString1() + "'" + " , StandByString2='"+this.getStandByString2() + "'" + " , StandByString3='"+this.getStandByString3() + "'" + " , StandByString4='"+this.getStandByString4() + "'" + " , StandByString5='"+this.getStandByString5() + "'" + " , StandByString6='"+this.getStandByString6() + "'" + " , StandByString7='"+this.getStandByString7() + "'" + " , StandByString8='"+this.getStandByString8() + "'" + " , StandByString9='"+this.getStandByString9() + "'" + " , StandByString10='"+this.getStandByString10() + "'" + " , StandByString11='"+this.getStandByString11() + "'" + " , StandByString12='"+this.getStandByString12() + "'" + " , StandByString13='"+this.getStandByString13() + "'" + " , StandByString14='"+this.getStandByString14() + "'" + " , StandByString15='"+this.getStandByString15() + "'" + " , StandByNum1="+this.getStandByNum1() + " , StandByNum2="+this.getStandByNum2() + " , StandByDate1='"+this.getStandByDate1() + "'" + " , StandByDate2='"+this.getStandByDate2() + "'" + " , ReturnFlag='"+this.getReturnFlag() + "'" + " , VoucherNo='"+this.getVoucherNo() + "'" + " , YearMonth='"+this.getYearMonth() + "'" + " , VerifyFlag='"+this.getVerifyFlag() + "'" + " , ErrorInfo='"+this.getErrorInfo() + "'" + " , ReturnDate='"+this.getReturnDate() + "'" + " , ReturnTime='"+this.getReturnTime() + "'" + " , DealOperator='"+this.getDealOperator() + "'"+" WHERE FSerialNo='"+this.getFSerialNo() + "'").replaceAll("'null'", "null"));

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


		try
		{
			pstmt = con.prepareStatement("INSERT INTO FIDataTransGather VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getFSerialNo() == null || this.getFSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFSerialNo().trim());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo().trim());
			}
			if(this.getCertificateID() == null || this.getCertificateID().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCertificateID().trim());
			}
			if(this.getAccountCode() == null || this.getAccountCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAccountCode().trim());
			}
			if(this.getFinItemType() == null || this.getFinItemType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getFinItemType().trim());
			}
			pstmt.setDouble(6, this.getSumMoney());
			if(this.getAccountDate() == null || this.getAccountDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getAccountDate()));
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getSaleChnl().trim());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getManageCom().trim());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getExecuteCom().trim());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRiskCode().trim());
			}
			if(this.getCostCenter() == null || this.getCostCenter().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getCostCenter().trim());
			}
			if(this.getNotesNo() == null || this.getNotesNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getNotesNo().trim());
			}
			if(this.getCustomerID() == null || this.getCustomerID().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCustomerID().trim());
			}
			if(this.getBudget() == null || this.getBudget().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getBudget().trim());
			}
			if(this.getCashFlow() == null || this.getCashFlow().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getCashFlow().trim());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getCurrency().trim());
			}
			if(this.getReMark() == null || this.getReMark().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getReMark().trim());
			}
			if(this.getStandByString1() == null || this.getStandByString1().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStandByString1().trim());
			}
			if(this.getStandByString2() == null || this.getStandByString2().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandByString2().trim());
			}
			if(this.getStandByString3() == null || this.getStandByString3().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandByString3().trim());
			}
			if(this.getStandByString4() == null || this.getStandByString4().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getStandByString4().trim());
			}
			if(this.getStandByString5() == null || this.getStandByString5().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getStandByString5().trim());
			}
			if(this.getStandByString6() == null || this.getStandByString6().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getStandByString6().trim());
			}
			if(this.getStandByString7() == null || this.getStandByString7().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStandByString7().trim());
			}
			if(this.getStandByString8() == null || this.getStandByString8().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStandByString8().trim());
			}
			if(this.getStandByString9() == null || this.getStandByString9().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStandByString9().trim());
			}
			if(this.getStandByString10() == null || this.getStandByString10().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStandByString10().trim());
			}
			if(this.getStandByString11() == null || this.getStandByString11().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getStandByString11().trim());
			}
			if(this.getStandByString12() == null || this.getStandByString12().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getStandByString12().trim());
			}
			if(this.getStandByString13() == null || this.getStandByString13().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getStandByString13().trim());
			}
			if(this.getStandByString14() == null || this.getStandByString14().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getStandByString14().trim());
			}
			if(this.getStandByString15() == null || this.getStandByString15().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getStandByString15().trim());
			}
			pstmt.setDouble(34, this.getStandByNum1());
			pstmt.setDouble(35, this.getStandByNum2());
			if(this.getStandByDate1() == null || this.getStandByDate1().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getStandByDate1()));
			}
			if(this.getStandByDate2() == null || this.getStandByDate2().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getStandByDate2()));
			}
			if(this.getReturnFlag() == null || this.getReturnFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getReturnFlag().trim());
			}
			if(this.getVoucherNo() == null || this.getVoucherNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getVoucherNo().trim());
			}
			if(this.getYearMonth() == null || this.getYearMonth().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getYearMonth().trim());
			}
			if(this.getVerifyFlag() == null || this.getVerifyFlag().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getVerifyFlag().trim());
			}
			if(this.getErrorInfo() == null || this.getErrorInfo().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getErrorInfo().trim());
			}
			if(this.getReturnDate() == null || this.getReturnDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getReturnDate()));
			}
			if(this.getReturnTime() == null || this.getReturnTime().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getReturnTime().trim());
			}
			if(this.getDealOperator() == null || this.getDealOperator().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDealOperator().trim());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransGather");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();
//真实的执行语句
logger.debug(String.valueOf("True Error Sql is : INSERT INTO FIDataTransGather VALUES('"+this.getFSerialNo() + "'"+",'"+this.getBatchNo() + "'"+",'"+this.getCertificateID() + "'"+",'"+this.getAccountCode() + "'"+",'"+this.getFinItemType() + "'"+","+this.getSumMoney()+",'"+this.getAccountDate() + "'"+",'"+this.getSaleChnl() + "'"+",'"+this.getManageCom() + "'"+",'"+this.getExecuteCom() + "'"+",'"+this.getRiskCode() + "'"+",'"+this.getCostCenter() + "'"+",'"+this.getNotesNo() + "'"+",'"+this.getCustomerID() + "'"+",'"+this.getBudget() + "'"+",'"+this.getCashFlow() + "'"+",'"+this.getCurrency() + "'"+",'"+this.getReMark() + "'"+",'"+this.getStandByString1() + "'"+",'"+this.getStandByString2() + "'"+",'"+this.getStandByString3() + "'"+",'"+this.getStandByString4() + "'"+",'"+this.getStandByString5() + "'"+",'"+this.getStandByString6() + "'"+",'"+this.getStandByString7() + "'"+",'"+this.getStandByString8() + "'"+",'"+this.getStandByString9() + "'"+",'"+this.getStandByString10() + "'"+",'"+this.getStandByString11() + "'"+",'"+this.getStandByString12() + "'"+",'"+this.getStandByString13() + "'"+",'"+this.getStandByString14() + "'"+",'"+this.getStandByString15() + "'"+","+this.getStandByNum1()+","+this.getStandByNum2()+",'"+this.getStandByDate1() + "'"+",'"+this.getStandByDate2() + "'"+",'"+this.getReturnFlag() + "'"+",'"+this.getVoucherNo() + "'"+",'"+this.getYearMonth() + "'"+",'"+this.getVerifyFlag() + "'"+",'"+this.getErrorInfo() + "'"+",'"+this.getReturnDate() + "'"+",'"+this.getReturnTime() + "'"+",'"+this.getDealOperator() + "'"+")").replaceAll("'null'", "null"));

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
			pstmt = con.prepareStatement("SELECT * FROM FIDataTransGather WHERE  FSerialNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getFSerialNo() == null || this.getFSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getFSerialNo().trim());
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
					tError.moduleName = "FIDataTransGatherDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

	public FIDataTransGatherSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		FIDataTransGatherSet aFIDataTransGatherSet = new FIDataTransGatherSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("FIDataTransGather");
			FIDataTransGatherSchema aSchema = this.getSchema();
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
				FIDataTransGatherSchema s1 = new FIDataTransGatherSchema();
				s1.setSchema(rs,i);
				aFIDataTransGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

		return aFIDataTransGatherSet;

	}

	public FIDataTransGatherSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		FIDataTransGatherSet aFIDataTransGatherSet = new FIDataTransGatherSet();

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
				FIDataTransGatherSchema s1 = new FIDataTransGatherSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIDataTransGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIDataTransGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

		return aFIDataTransGatherSet;
	}

	public FIDataTransGatherSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		FIDataTransGatherSet aFIDataTransGatherSet = new FIDataTransGatherSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("FIDataTransGather");
			FIDataTransGatherSchema aSchema = this.getSchema();
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

				FIDataTransGatherSchema s1 = new FIDataTransGatherSchema();
				s1.setSchema(rs,i);
				aFIDataTransGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

		return aFIDataTransGatherSet;

	}

	public FIDataTransGatherSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		FIDataTransGatherSet aFIDataTransGatherSet = new FIDataTransGatherSet();

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

				FIDataTransGatherSchema s1 = new FIDataTransGatherSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIDataTransGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIDataTransGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

		return aFIDataTransGatherSet;
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
			SQLString sqlObj = new SQLString("FIDataTransGather");
			FIDataTransGatherSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update FIDataTransGather " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "FIDataTransGatherDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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
        tError.moduleName = "FIDataTransGatherDB";
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
        tError.moduleName = "FIDataTransGatherDB";
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
        tError.moduleName = "FIDataTransGatherDB";
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
        tError.moduleName = "FIDataTransGatherDB";
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
 * @return FIDataTransGatherSet
 */
public FIDataTransGatherSet getData()
{
    int tCount = 0;
    FIDataTransGatherSet tFIDataTransGatherSet = new FIDataTransGatherSet();
    FIDataTransGatherSchema tFIDataTransGatherSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "FIDataTransGatherDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tFIDataTransGatherSchema = new FIDataTransGatherSchema();
        tFIDataTransGatherSchema.setSchema(mResultSet, 1);
        tFIDataTransGatherSet.add(tFIDataTransGatherSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tFIDataTransGatherSchema = new FIDataTransGatherSchema();
                tFIDataTransGatherSchema.setSchema(mResultSet, 1);
                tFIDataTransGatherSet.add(tFIDataTransGatherSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "FIDataTransGatherDB";
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
    return tFIDataTransGatherSet;
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
            tError.moduleName = "FIDataTransGatherDB";
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
        tError.moduleName = "FIDataTransGatherDB";
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
            tError.moduleName = "FIDataTransGatherDB";
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
        tError.moduleName = "FIDataTransGatherDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public FIDataTransGatherSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FIDataTransGatherSet aFIDataTransGatherSet = new FIDataTransGatherSet();

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
				FIDataTransGatherSchema s1 = new FIDataTransGatherSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIDataTransGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIDataTransGatherSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

		return aFIDataTransGatherSet;
	}

	public FIDataTransGatherSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FIDataTransGatherSet aFIDataTransGatherSet = new FIDataTransGatherSet();

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

				FIDataTransGatherSchema s1 = new FIDataTransGatherSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "FIDataTransGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aFIDataTransGatherSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherDB";
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

		return aFIDataTransGatherSet; 
	}

}
