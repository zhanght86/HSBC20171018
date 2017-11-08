/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FIRuleDealErrLogSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIRuleDealErrLogDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIRuleDealErrLogDBSet extends FIRuleDealErrLogSet
{
private static Logger logger = Logger.getLogger(FIRuleDealErrLogDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FIRuleDealErrLogDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIRuleDealErrLog");
		mflag = true;
	}

	public FIRuleDealErrLogDBSet()
	{
		db = new DBOper( "FIRuleDealErrLog" );
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
			tError.moduleName = "FIRuleDealErrLogDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIRuleDealErrLog WHERE  ErrSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getErrSerialNo() == null || this.get(i).getErrSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getErrSerialNo().trim());
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
			tError.moduleName = "FIRuleDealErrLogDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIRuleDealErrLog");
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
			pstmt = con.prepareStatement("UPDATE FIRuleDealErrLog SET  ErrSerialNo = ? , Aserialno = ? , RuleDealBatchNo = ? , DataSourceBatchNo = ? , RuleID = ? , ErrInfo = ? , RulePlanID = ? , DealState = ? , CertificateID = ? , businessno = ? WHERE  ErrSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getErrSerialNo() == null || this.get(i).getErrSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getErrSerialNo().trim());
			}
			if(this.get(i).getAserialno() == null || this.get(i).getAserialno().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAserialno().trim());
			}
			if(this.get(i).getRuleDealBatchNo() == null || this.get(i).getRuleDealBatchNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRuleDealBatchNo().trim());
			}
			if(this.get(i).getDataSourceBatchNo() == null || this.get(i).getDataSourceBatchNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDataSourceBatchNo().trim());
			}
			if(this.get(i).getRuleID() == null || this.get(i).getRuleID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRuleID().trim());
			}
			if(this.get(i).getErrInfo() == null || this.get(i).getErrInfo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getErrInfo().trim());
			}
			if(this.get(i).getRulePlanID() == null || this.get(i).getRulePlanID().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRulePlanID().trim());
			}
			if(this.get(i).getDealState() == null || this.get(i).getDealState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDealState().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getbusinessno() == null || this.get(i).getbusinessno().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getbusinessno().trim());
			}
			// set where condition
			if(this.get(i).getErrSerialNo() == null || this.get(i).getErrSerialNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getErrSerialNo().trim());
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
			tError.moduleName = "FIRuleDealErrLogDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIRuleDealErrLog");
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
			pstmt = con.prepareStatement("INSERT INTO FIRuleDealErrLog VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getErrSerialNo() == null || this.get(i).getErrSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getErrSerialNo().trim());
			}
			if(this.get(i).getAserialno() == null || this.get(i).getAserialno().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAserialno().trim());
			}
			if(this.get(i).getRuleDealBatchNo() == null || this.get(i).getRuleDealBatchNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRuleDealBatchNo().trim());
			}
			if(this.get(i).getDataSourceBatchNo() == null || this.get(i).getDataSourceBatchNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDataSourceBatchNo().trim());
			}
			if(this.get(i).getRuleID() == null || this.get(i).getRuleID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRuleID().trim());
			}
			if(this.get(i).getErrInfo() == null || this.get(i).getErrInfo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getErrInfo().trim());
			}
			if(this.get(i).getRulePlanID() == null || this.get(i).getRulePlanID().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRulePlanID().trim());
			}
			if(this.get(i).getDealState() == null || this.get(i).getDealState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDealState().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getbusinessno() == null || this.get(i).getbusinessno().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getbusinessno().trim());
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
			tError.moduleName = "FIRuleDealErrLogDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIRuleDealErrLog");
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
