/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LFRiskAppSchema;
import com.sinosoft.lis.vschema.LFRiskAppSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LFRiskAppDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LFRiskAppDBSet extends LFRiskAppSet
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
	public LFRiskAppDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LFRiskApp");
		mflag = true;
	}

	public LFRiskAppDBSet()
	{
		db = new DBOper( "LFRiskApp" );
		con = db.getConnection();
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
			tError.moduleName = "LFRiskAppDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LFRiskApp WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getManageCom());
			}
			pstmt.setInt(3, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSaleChnl());
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFirstPayFlag());
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPersonPolFlag());
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getReportDate()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskApp");
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
			tError.moduleName = "LFRiskAppDBSet";
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
			pstmt = con.prepareStatement("UPDATE LFRiskApp SET  RiskCode = ? , ManageCom = ? , PayIntv = ? , SaleChnl = ? , FirstPayFlag = ? , PersonPolFlag = ? , ReportDate = ? , Amnt = ? , AmntSum = ? , Prem = ? , PremSum = ? , InsuredCount = ? , InsuredCountSum = ? , PolCount = ? , PolCountSum = ? , CurYearAmnt = ? , CurYearAmntSum = ? , AllYearAmnt = ? , AllYearAmntSum = ? , CurYearPrem = ? , CurYearPremSum = ? , AllYearPrem = ? , AllYearPremSum = ? , CurYearInsured = ? , CurYearInsuredSum = ? , AllYearInsured = ? , AllYearInsuredSum = ? , CurYearPol = ? , CurYearPolSum = ? , AllYearPol = ? , AllYearPolSum = ? , MakeDate = ? , MakeTime = ? WHERE  RiskCode = ? AND ManageCom = ? AND PayIntv = ? AND SaleChnl = ? AND FirstPayFlag = ? AND PersonPolFlag = ? AND ReportDate = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getManageCom());
			}
			pstmt.setInt(3, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSaleChnl());
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFirstPayFlag());
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPersonPolFlag());
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getReportDate()));
			}
			pstmt.setDouble(8, this.get(i).getAmnt());
			pstmt.setDouble(9, this.get(i).getAmntSum());
			pstmt.setDouble(10, this.get(i).getPrem());
			pstmt.setDouble(11, this.get(i).getPremSum());
			pstmt.setInt(12, this.get(i).getInsuredCount());
			pstmt.setInt(13, this.get(i).getInsuredCountSum());
			pstmt.setInt(14, this.get(i).getPolCount());
			pstmt.setInt(15, this.get(i).getPolCountSum());
			pstmt.setDouble(16, this.get(i).getCurYearAmnt());
			pstmt.setDouble(17, this.get(i).getCurYearAmntSum());
			pstmt.setDouble(18, this.get(i).getAllYearAmnt());
			pstmt.setDouble(19, this.get(i).getAllYearAmntSum());
			pstmt.setDouble(20, this.get(i).getCurYearPrem());
			pstmt.setDouble(21, this.get(i).getCurYearPremSum());
			pstmt.setDouble(22, this.get(i).getAllYearPrem());
			pstmt.setDouble(23, this.get(i).getAllYearPremSum());
			pstmt.setInt(24, this.get(i).getCurYearInsured());
			pstmt.setInt(25, this.get(i).getCurYearInsuredSum());
			pstmt.setInt(26, this.get(i).getAllYearInsured());
			pstmt.setInt(27, this.get(i).getAllYearInsuredSum());
			pstmt.setInt(28, this.get(i).getCurYearPol());
			pstmt.setInt(29, this.get(i).getCurYearPolSum());
			pstmt.setInt(30, this.get(i).getAllYearPol());
			pstmt.setInt(31, this.get(i).getAllYearPolSum());
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getMakeTime());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getRiskCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getManageCom());
			}
			pstmt.setInt(36, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSaleChnl());
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getFirstPayFlag());
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getPersonPolFlag());
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getReportDate()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskApp");
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
			tError.moduleName = "LFRiskAppDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LFRiskApp VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getManageCom());
			}
			pstmt.setInt(3, this.get(i).getPayIntv());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSaleChnl());
			}
			if(this.get(i).getFirstPayFlag() == null || this.get(i).getFirstPayFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFirstPayFlag());
			}
			if(this.get(i).getPersonPolFlag() == null || this.get(i).getPersonPolFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPersonPolFlag());
			}
			if(this.get(i).getReportDate() == null || this.get(i).getReportDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getReportDate()));
			}
			pstmt.setDouble(8, this.get(i).getAmnt());
			pstmt.setDouble(9, this.get(i).getAmntSum());
			pstmt.setDouble(10, this.get(i).getPrem());
			pstmt.setDouble(11, this.get(i).getPremSum());
			pstmt.setInt(12, this.get(i).getInsuredCount());
			pstmt.setInt(13, this.get(i).getInsuredCountSum());
			pstmt.setInt(14, this.get(i).getPolCount());
			pstmt.setInt(15, this.get(i).getPolCountSum());
			pstmt.setDouble(16, this.get(i).getCurYearAmnt());
			pstmt.setDouble(17, this.get(i).getCurYearAmntSum());
			pstmt.setDouble(18, this.get(i).getAllYearAmnt());
			pstmt.setDouble(19, this.get(i).getAllYearAmntSum());
			pstmt.setDouble(20, this.get(i).getCurYearPrem());
			pstmt.setDouble(21, this.get(i).getCurYearPremSum());
			pstmt.setDouble(22, this.get(i).getAllYearPrem());
			pstmt.setDouble(23, this.get(i).getAllYearPremSum());
			pstmt.setInt(24, this.get(i).getCurYearInsured());
			pstmt.setInt(25, this.get(i).getCurYearInsuredSum());
			pstmt.setInt(26, this.get(i).getAllYearInsured());
			pstmt.setInt(27, this.get(i).getAllYearInsuredSum());
			pstmt.setInt(28, this.get(i).getCurYearPol());
			pstmt.setInt(29, this.get(i).getCurYearPolSum());
			pstmt.setInt(30, this.get(i).getAllYearPol());
			pstmt.setInt(31, this.get(i).getAllYearPolSum());
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getMakeTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LFRiskApp");
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
			tError.moduleName = "LFRiskAppDBSet";
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
