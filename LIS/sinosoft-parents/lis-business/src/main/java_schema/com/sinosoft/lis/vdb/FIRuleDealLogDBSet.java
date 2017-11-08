/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FIRuleDealLogSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIRuleDealLogDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIRuleDealLogDBSet extends FIRuleDealLogSet
{
private static Logger logger = Logger.getLogger(FIRuleDealLogDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FIRuleDealLogDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIRuleDealLog");
		mflag = true;
	}

	public FIRuleDealLogDBSet()
	{
		db = new DBOper( "FIRuleDealLog" );
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
			tError.moduleName = "FIRuleDealLogDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIRuleDealLog WHERE  CheckSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCheckSerialNo() == null || this.get(i).getCheckSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCheckSerialNo().trim());
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
			tError.moduleName = "FIRuleDealLogDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIRuleDealLog");
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
			pstmt = con.prepareStatement("UPDATE FIRuleDealLog SET  CheckSerialNo = ? , VersionNo = ? , RuleDealBatchNo = ? , DataSourceBatchNo = ? , CallPointID = ? , RuleDealResult = ? , DealOperator = ? , RulePlanID = ? , RuleDealDate = ? , RuleDealTime = ? , LogFilePath = ? , LogFileName = ? WHERE  CheckSerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCheckSerialNo() == null || this.get(i).getCheckSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCheckSerialNo().trim());
			}
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getVersionNo().trim());
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
			if(this.get(i).getCallPointID() == null || this.get(i).getCallPointID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCallPointID().trim());
			}
			if(this.get(i).getRuleDealResult() == null || this.get(i).getRuleDealResult().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRuleDealResult().trim());
			}
			if(this.get(i).getDealOperator() == null || this.get(i).getDealOperator().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDealOperator().trim());
			}
			if(this.get(i).getRulePlanID() == null || this.get(i).getRulePlanID().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRulePlanID().trim());
			}
			if(this.get(i).getRuleDealDate() == null || this.get(i).getRuleDealDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getRuleDealDate()));
			}
			if(this.get(i).getRuleDealTime() == null || this.get(i).getRuleDealTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRuleDealTime().trim());
			}
			if(this.get(i).getLogFilePath() == null || this.get(i).getLogFilePath().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getLogFilePath().trim());
			}
			if(this.get(i).getLogFileName() == null || this.get(i).getLogFileName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getLogFileName().trim());
			}
			// set where condition
			if(this.get(i).getCheckSerialNo() == null || this.get(i).getCheckSerialNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCheckSerialNo().trim());
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
			tError.moduleName = "FIRuleDealLogDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIRuleDealLog");
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
			pstmt = con.prepareStatement("INSERT INTO FIRuleDealLog VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCheckSerialNo() == null || this.get(i).getCheckSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCheckSerialNo().trim());
			}
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getVersionNo().trim());
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
			if(this.get(i).getCallPointID() == null || this.get(i).getCallPointID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCallPointID().trim());
			}
			if(this.get(i).getRuleDealResult() == null || this.get(i).getRuleDealResult().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRuleDealResult().trim());
			}
			if(this.get(i).getDealOperator() == null || this.get(i).getDealOperator().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDealOperator().trim());
			}
			if(this.get(i).getRulePlanID() == null || this.get(i).getRulePlanID().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRulePlanID().trim());
			}
			if(this.get(i).getRuleDealDate() == null || this.get(i).getRuleDealDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getRuleDealDate()));
			}
			if(this.get(i).getRuleDealTime() == null || this.get(i).getRuleDealTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRuleDealTime().trim());
			}
			if(this.get(i).getLogFilePath() == null || this.get(i).getLogFilePath().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getLogFilePath().trim());
			}
			if(this.get(i).getLogFileName() == null || this.get(i).getLogFileName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getLogFileName().trim());
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
			tError.moduleName = "FIRuleDealLogDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIRuleDealLog");
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
