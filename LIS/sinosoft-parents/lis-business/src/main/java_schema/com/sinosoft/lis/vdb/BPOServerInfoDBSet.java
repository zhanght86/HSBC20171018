/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.BPOServerInfoSchema;
import com.sinosoft.lis.vschema.BPOServerInfoSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: BPOServerInfoDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 录入外包（lis6.0）
 */
public class BPOServerInfoDBSet extends BPOServerInfoSet
{
private static Logger logger = Logger.getLogger(BPOServerInfoDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public BPOServerInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"BPOServerInfo");
		mflag = true;
	}

	public BPOServerInfoDBSet()
	{
		db = new DBOper( "BPOServerInfo" );
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
			tError.moduleName = "BPOServerInfoDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM BPOServerInfo WHERE  BPOID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBPOID() == null || this.get(i).getBPOID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBPOID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("BPOServerInfo");
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
			tError.moduleName = "BPOServerInfoDBSet";
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
			pstmt = con.prepareStatement("UPDATE BPOServerInfo SET  BPOID = ? , BPOName = ? , InputState = ? , LisOperator = ? , ScanPicPath = ? , BackDataPath = ? , BackDataBackupPath = ? , CoInputStartDate = ? , CoInputStartTime = ? , CoInputEndDate = ? , CoInputEndTime = ? , Remark = ? , ServerIP = ? , ServerPort = ? , LogInUser = ? , LogInPwd = ? , Suffix = ? , BussNoType = ? WHERE  BPOID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBPOID() == null || this.get(i).getBPOID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBPOID());
			}
			if(this.get(i).getBPOName() == null || this.get(i).getBPOName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBPOName());
			}
			if(this.get(i).getInputState() == null || this.get(i).getInputState().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInputState());
			}
			if(this.get(i).getLisOperator() == null || this.get(i).getLisOperator().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getLisOperator());
			}
			if(this.get(i).getScanPicPath() == null || this.get(i).getScanPicPath().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getScanPicPath());
			}
			if(this.get(i).getBackDataPath() == null || this.get(i).getBackDataPath().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBackDataPath());
			}
			if(this.get(i).getBackDataBackupPath() == null || this.get(i).getBackDataBackupPath().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBackDataBackupPath());
			}
			if(this.get(i).getCoInputStartDate() == null || this.get(i).getCoInputStartDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getCoInputStartDate()));
			}
			if(this.get(i).getCoInputStartTime() == null || this.get(i).getCoInputStartTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCoInputStartTime());
			}
			if(this.get(i).getCoInputEndDate() == null || this.get(i).getCoInputEndDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getCoInputEndDate()));
			}
			if(this.get(i).getCoInputEndTime() == null || this.get(i).getCoInputEndTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCoInputEndTime());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRemark());
			}
			if(this.get(i).getServerIP() == null || this.get(i).getServerIP().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getServerIP());
			}
			if(this.get(i).getServerPort() == null || this.get(i).getServerPort().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getServerPort());
			}
			if(this.get(i).getLogInUser() == null || this.get(i).getLogInUser().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getLogInUser());
			}
			if(this.get(i).getLogInPwd() == null || this.get(i).getLogInPwd().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getLogInPwd());
			}
			if(this.get(i).getSuffix() == null || this.get(i).getSuffix().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSuffix());
			}
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBussNoType());
			}
			// set where condition
			if(this.get(i).getBPOID() == null || this.get(i).getBPOID().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBPOID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("BPOServerInfo");
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
			tError.moduleName = "BPOServerInfoDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO BPOServerInfo(BPOID ,BPOName ,InputState ,LisOperator ,ScanPicPath ,BackDataPath ,BackDataBackupPath ,CoInputStartDate ,CoInputStartTime ,CoInputEndDate ,CoInputEndTime ,Remark ,ServerIP ,ServerPort ,LogInUser ,LogInPwd ,Suffix ,BussNoType) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBPOID() == null || this.get(i).getBPOID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBPOID());
			}
			if(this.get(i).getBPOName() == null || this.get(i).getBPOName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBPOName());
			}
			if(this.get(i).getInputState() == null || this.get(i).getInputState().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInputState());
			}
			if(this.get(i).getLisOperator() == null || this.get(i).getLisOperator().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getLisOperator());
			}
			if(this.get(i).getScanPicPath() == null || this.get(i).getScanPicPath().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getScanPicPath());
			}
			if(this.get(i).getBackDataPath() == null || this.get(i).getBackDataPath().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBackDataPath());
			}
			if(this.get(i).getBackDataBackupPath() == null || this.get(i).getBackDataBackupPath().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBackDataBackupPath());
			}
			if(this.get(i).getCoInputStartDate() == null || this.get(i).getCoInputStartDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getCoInputStartDate()));
			}
			if(this.get(i).getCoInputStartTime() == null || this.get(i).getCoInputStartTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCoInputStartTime());
			}
			if(this.get(i).getCoInputEndDate() == null || this.get(i).getCoInputEndDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getCoInputEndDate()));
			}
			if(this.get(i).getCoInputEndTime() == null || this.get(i).getCoInputEndTime().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCoInputEndTime());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRemark());
			}
			if(this.get(i).getServerIP() == null || this.get(i).getServerIP().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getServerIP());
			}
			if(this.get(i).getServerPort() == null || this.get(i).getServerPort().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getServerPort());
			}
			if(this.get(i).getLogInUser() == null || this.get(i).getLogInUser().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getLogInUser());
			}
			if(this.get(i).getLogInPwd() == null || this.get(i).getLogInPwd().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getLogInPwd());
			}
			if(this.get(i).getSuffix() == null || this.get(i).getSuffix().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSuffix());
			}
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBussNoType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("BPOServerInfo");
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
			tError.moduleName = "BPOServerInfoDBSet";
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
