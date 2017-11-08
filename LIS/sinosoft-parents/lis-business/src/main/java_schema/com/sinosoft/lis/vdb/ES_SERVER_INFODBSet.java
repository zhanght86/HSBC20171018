/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: ES_SERVER_INFODBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_SERVER_INFODBSet extends ES_SERVER_INFOSet
{
private static Logger logger = Logger.getLogger(ES_SERVER_INFODBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public ES_SERVER_INFODBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"ES_SERVER_INFO");
		mflag = true;
	}

	public ES_SERVER_INFODBSet()
	{
		db = new DBOper( "ES_SERVER_INFO" );
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
			tError.moduleName = "ES_SERVER_INFODBSet";
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
			pstmt = con.prepareStatement("DELETE FROM ES_SERVER_INFO WHERE  HostName = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getHostName() == null || this.get(i).getHostName().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getHostName());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_SERVER_INFO");
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
			tError.moduleName = "ES_SERVER_INFODBSet";
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
			pstmt = con.prepareStatement("UPDATE ES_SERVER_INFO SET  HostName = ? , ServerIP = ? , LoginNameFTP = ? , LoginPwdFTP = ? , PicPathFTP = ? , ServerFlag = ? , ServerPort = ? , PicPath = ? , ServerBasePath = ? WHERE  HostName = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getHostName() == null || this.get(i).getHostName().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getHostName());
			}
			if(this.get(i).getServerIP() == null || this.get(i).getServerIP().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getServerIP());
			}
			if(this.get(i).getLoginNameFTP() == null || this.get(i).getLoginNameFTP().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getLoginNameFTP());
			}
			if(this.get(i).getLoginPwdFTP() == null || this.get(i).getLoginPwdFTP().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getLoginPwdFTP());
			}
			if(this.get(i).getPicPathFTP() == null || this.get(i).getPicPathFTP().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPicPathFTP());
			}
			if(this.get(i).getServerFlag() == null || this.get(i).getServerFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getServerFlag());
			}
			if(this.get(i).getServerPort() == null || this.get(i).getServerPort().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getServerPort());
			}
			if(this.get(i).getPicPath() == null || this.get(i).getPicPath().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getPicPath());
			}
			if(this.get(i).getServerBasePath() == null || this.get(i).getServerBasePath().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getServerBasePath());
			}
			// set where condition
			if(this.get(i).getHostName() == null || this.get(i).getHostName().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getHostName());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_SERVER_INFO");
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
			tError.moduleName = "ES_SERVER_INFODBSet";
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
			pstmt = con.prepareStatement("INSERT INTO ES_SERVER_INFO(HostName ,ServerIP ,LoginNameFTP ,LoginPwdFTP ,PicPathFTP ,ServerFlag ,ServerPort ,PicPath ,ServerBasePath) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getHostName() == null || this.get(i).getHostName().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getHostName());
			}
			if(this.get(i).getServerIP() == null || this.get(i).getServerIP().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getServerIP());
			}
			if(this.get(i).getLoginNameFTP() == null || this.get(i).getLoginNameFTP().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getLoginNameFTP());
			}
			if(this.get(i).getLoginPwdFTP() == null || this.get(i).getLoginPwdFTP().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getLoginPwdFTP());
			}
			if(this.get(i).getPicPathFTP() == null || this.get(i).getPicPathFTP().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPicPathFTP());
			}
			if(this.get(i).getServerFlag() == null || this.get(i).getServerFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getServerFlag());
			}
			if(this.get(i).getServerPort() == null || this.get(i).getServerPort().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getServerPort());
			}
			if(this.get(i).getPicPath() == null || this.get(i).getPicPath().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getPicPath());
			}
			if(this.get(i).getServerBasePath() == null || this.get(i).getServerBasePath().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getServerBasePath());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_SERVER_INFO");
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
			tError.moduleName = "ES_SERVER_INFODBSet";
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
