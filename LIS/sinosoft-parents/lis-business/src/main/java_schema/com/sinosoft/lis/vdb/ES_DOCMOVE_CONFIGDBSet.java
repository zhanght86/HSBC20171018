/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.ES_DOCMOVE_CONFIGSchema;
import com.sinosoft.lis.vschema.ES_DOCMOVE_CONFIGSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: ES_DOCMOVE_CONFIGDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOCMOVE_CONFIGDBSet extends ES_DOCMOVE_CONFIGSet
{
private static Logger logger = Logger.getLogger(ES_DOCMOVE_CONFIGDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public ES_DOCMOVE_CONFIGDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"ES_DOCMOVE_CONFIG");
		mflag = true;
	}

	public ES_DOCMOVE_CONFIGDBSet()
	{
		db = new DBOper( "ES_DOCMOVE_CONFIG" );
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
			tError.moduleName = "ES_DOCMOVE_CONFIGDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM ES_DOCMOVE_CONFIG WHERE  ToManageCom = ? AND ManageCom = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getToManageCom() == null || this.get(i).getToManageCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getToManageCom());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getManageCom());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_DOCMOVE_CONFIG");
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
			tError.moduleName = "ES_DOCMOVE_CONFIGDBSet";
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
			pstmt = con.prepareStatement("UPDATE ES_DOCMOVE_CONFIG SET  ToManageCom = ? , ANumber = ? , ACricle = ? , ABeginTime = ? , AEndTime = ? , ManageCom = ? , DBeginTime = ? , DEndTime = ? , ScanNo = ? , SerialNo = ? , TFailNo = ? WHERE  ToManageCom = ? AND ManageCom = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getToManageCom() == null || this.get(i).getToManageCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getToManageCom());
			}
			pstmt.setInt(2, this.get(i).getANumber());
			pstmt.setInt(3, this.get(i).getACricle());
			if(this.get(i).getABeginTime() == null || this.get(i).getABeginTime().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getABeginTime());
			}
			if(this.get(i).getAEndTime() == null || this.get(i).getAEndTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAEndTime());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			if(this.get(i).getDBeginTime() == null || this.get(i).getDBeginTime().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getDBeginTime()));
			}
			if(this.get(i).getDEndTime() == null || this.get(i).getDEndTime().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getDEndTime()));
			}
			if(this.get(i).getScanNo() == null || this.get(i).getScanNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getScanNo());
			}
			pstmt.setInt(10, this.get(i).getSerialNo());
			pstmt.setInt(11, this.get(i).getTFailNo());
			// set where condition
			if(this.get(i).getToManageCom() == null || this.get(i).getToManageCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getToManageCom());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getManageCom());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_DOCMOVE_CONFIG");
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
			tError.moduleName = "ES_DOCMOVE_CONFIGDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO ES_DOCMOVE_CONFIG(ToManageCom ,ANumber ,ACricle ,ABeginTime ,AEndTime ,ManageCom ,DBeginTime ,DEndTime ,ScanNo ,SerialNo ,TFailNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getToManageCom() == null || this.get(i).getToManageCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getToManageCom());
			}
			pstmt.setInt(2, this.get(i).getANumber());
			pstmt.setInt(3, this.get(i).getACricle());
			if(this.get(i).getABeginTime() == null || this.get(i).getABeginTime().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getABeginTime());
			}
			if(this.get(i).getAEndTime() == null || this.get(i).getAEndTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAEndTime());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			if(this.get(i).getDBeginTime() == null || this.get(i).getDBeginTime().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getDBeginTime()));
			}
			if(this.get(i).getDEndTime() == null || this.get(i).getDEndTime().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getDEndTime()));
			}
			if(this.get(i).getScanNo() == null || this.get(i).getScanNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getScanNo());
			}
			pstmt.setInt(10, this.get(i).getSerialNo());
			pstmt.setInt(11, this.get(i).getTFailNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("ES_DOCMOVE_CONFIG");
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
			tError.moduleName = "ES_DOCMOVE_CONFIGDBSet";
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
