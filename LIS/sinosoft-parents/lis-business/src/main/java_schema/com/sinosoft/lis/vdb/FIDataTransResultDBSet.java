/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FIDataTransResultSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataTransResultDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataTransResultDBSet extends FIDataTransResultSet
{
private static Logger logger = Logger.getLogger(FIDataTransResultDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FIDataTransResultDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIDataTransResult");
		mflag = true;
	}

	public FIDataTransResultDBSet()
	{
		db = new DBOper( "FIDataTransResult" );
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
			tError.moduleName = "FIDataTransResultDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIDataTransResult WHERE  FSerialNo = ?");
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
			tError.moduleName = "FIDataTransResultDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransResult");
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
			pstmt = con.prepareStatement("UPDATE FIDataTransResult SET  FSerialNo = ? , ASerialNo = ? , BatchNo = ? , CertificateID = ? , CostID = ? , AccountCode = ? , FinItemType = ? , SumMoney = ? , AccountDate = ? , SaleChnl = ? , ManageCom = ? , ExecuteCom = ? , RiskCode = ? , CostCenter = ? , NotesNo = ? , CustomerID = ? , Budget = ? , CashFlow = ? , Currency = ? , UpCertificate = ? , StandByString1 = ? , StandByString2 = ? , StandByString3 = ? , StandByString4 = ? , StandByString5 = ? , StandByNum1 = ? , StandByNum2 = ? , StandByDate1 = ? , StandByDate2 = ? WHERE  FSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFSerialNo().trim());
			}
			if(this.get(i).getASerialNo() == null || this.get(i).getASerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getASerialNo().trim());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getCostID() == null || this.get(i).getCostID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCostID().trim());
			}
			if(this.get(i).getAccountCode() == null || this.get(i).getAccountCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAccountCode().trim());
			}
			if(this.get(i).getFinItemType() == null || this.get(i).getFinItemType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFinItemType().trim());
			}
			pstmt.setDouble(8, this.get(i).getSumMoney());
			if(this.get(i).getAccountDate() == null || this.get(i).getAccountDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getAccountDate()));
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSaleChnl().trim());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getManageCom().trim());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getExecuteCom().trim());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRiskCode().trim());
			}
			if(this.get(i).getCostCenter() == null || this.get(i).getCostCenter().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCostCenter().trim());
			}
			if(this.get(i).getNotesNo() == null || this.get(i).getNotesNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getNotesNo().trim());
			}
			if(this.get(i).getCustomerID() == null || this.get(i).getCustomerID().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCustomerID().trim());
			}
			if(this.get(i).getBudget() == null || this.get(i).getBudget().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getBudget().trim());
			}
			if(this.get(i).getCashFlow() == null || this.get(i).getCashFlow().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCashFlow().trim());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCurrency().trim());
			}
			if(this.get(i).getUpCertificate() == null || this.get(i).getUpCertificate().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getUpCertificate().trim());
			}
			if(this.get(i).getStandByString1() == null || this.get(i).getStandByString1().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandByString1().trim());
			}
			if(this.get(i).getStandByString2() == null || this.get(i).getStandByString2().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandByString2().trim());
			}
			if(this.get(i).getStandByString3() == null || this.get(i).getStandByString3().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStandByString3().trim());
			}
			if(this.get(i).getStandByString4() == null || this.get(i).getStandByString4().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandByString4().trim());
			}
			if(this.get(i).getStandByString5() == null || this.get(i).getStandByString5().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandByString5().trim());
			}
			pstmt.setDouble(26, this.get(i).getStandByNum1());
			pstmt.setDouble(27, this.get(i).getStandByNum2());
			if(this.get(i).getStandByDate1() == null || this.get(i).getStandByDate1().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getStandByDate1()));
			}
			if(this.get(i).getStandByDate2() == null || this.get(i).getStandByDate2().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getStandByDate2()));
			}
			// set where condition
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getFSerialNo().trim());
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
			tError.moduleName = "FIDataTransResultDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransResult");
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
			pstmt = con.prepareStatement("INSERT INTO FIDataTransResult VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFSerialNo() == null || this.get(i).getFSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFSerialNo().trim());
			}
			if(this.get(i).getASerialNo() == null || this.get(i).getASerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getASerialNo().trim());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getCostID() == null || this.get(i).getCostID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCostID().trim());
			}
			if(this.get(i).getAccountCode() == null || this.get(i).getAccountCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAccountCode().trim());
			}
			if(this.get(i).getFinItemType() == null || this.get(i).getFinItemType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFinItemType().trim());
			}
			pstmt.setDouble(8, this.get(i).getSumMoney());
			if(this.get(i).getAccountDate() == null || this.get(i).getAccountDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getAccountDate()));
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSaleChnl().trim());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getManageCom().trim());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getExecuteCom().trim());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRiskCode().trim());
			}
			if(this.get(i).getCostCenter() == null || this.get(i).getCostCenter().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCostCenter().trim());
			}
			if(this.get(i).getNotesNo() == null || this.get(i).getNotesNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getNotesNo().trim());
			}
			if(this.get(i).getCustomerID() == null || this.get(i).getCustomerID().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCustomerID().trim());
			}
			if(this.get(i).getBudget() == null || this.get(i).getBudget().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getBudget().trim());
			}
			if(this.get(i).getCashFlow() == null || this.get(i).getCashFlow().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCashFlow().trim());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCurrency().trim());
			}
			if(this.get(i).getUpCertificate() == null || this.get(i).getUpCertificate().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getUpCertificate().trim());
			}
			if(this.get(i).getStandByString1() == null || this.get(i).getStandByString1().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandByString1().trim());
			}
			if(this.get(i).getStandByString2() == null || this.get(i).getStandByString2().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandByString2().trim());
			}
			if(this.get(i).getStandByString3() == null || this.get(i).getStandByString3().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStandByString3().trim());
			}
			if(this.get(i).getStandByString4() == null || this.get(i).getStandByString4().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandByString4().trim());
			}
			if(this.get(i).getStandByString5() == null || this.get(i).getStandByString5().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandByString5().trim());
			}
			pstmt.setDouble(26, this.get(i).getStandByNum1());
			pstmt.setDouble(27, this.get(i).getStandByNum2());
			if(this.get(i).getStandByDate1() == null || this.get(i).getStandByDate1().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getStandByDate1()));
			}
			if(this.get(i).getStandByDate2() == null || this.get(i).getStandByDate2().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getStandByDate2()));
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
			tError.moduleName = "FIDataTransResultDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataTransResult");
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
