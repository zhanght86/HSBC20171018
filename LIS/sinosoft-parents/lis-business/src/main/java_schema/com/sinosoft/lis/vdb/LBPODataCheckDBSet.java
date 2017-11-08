/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPODataCheckSchema;
import com.sinosoft.lis.vschema.LBPODataCheckSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPODataCheckDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPODataCheckDBSet extends LBPODataCheckSet
{
private static Logger logger = Logger.getLogger(LBPODataCheckDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBPODataCheckDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPODataCheck");
		mflag = true;
	}

	public LBPODataCheckDBSet()
	{
		db = new DBOper( "LBPODataCheck" );
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
			tError.moduleName = "LBPODataCheckDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPODataCheck WHERE  BussNoType = ? AND CheckOrder = ? AND CheckCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, StrTool.space1(this.get(i).getBussNoType(), 2));
			}
			if(this.get(i).getCheckOrder() == null || this.get(i).getCheckOrder().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space1(this.get(i).getCheckOrder(), 10));
			}
			if(this.get(i).getCheckCode() == null || this.get(i).getCheckCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCheckCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPODataCheck");
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
			tError.moduleName = "LBPODataCheckDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPODataCheck SET  BussNoType = ? , CheckOrder = ? , CheckCode = ? , CalSql = ? , Noti = ? WHERE  BussNoType = ? AND CheckOrder = ? AND CheckCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussNoType());
			}
			if(this.get(i).getCheckOrder() == null || this.get(i).getCheckOrder().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCheckOrder());
			}
			if(this.get(i).getCheckCode() == null || this.get(i).getCheckCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCheckCode());
			}
			if(this.get(i).getCalSql() == null || this.get(i).getCalSql().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalSql());
			}
			if(this.get(i).getNoti() == null || this.get(i).getNoti().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getNoti());
			}
			// set where condition
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, StrTool.space1(this.get(i).getBussNoType(), 2));
			}
			if(this.get(i).getCheckOrder() == null || this.get(i).getCheckOrder().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, StrTool.space1(this.get(i).getCheckOrder(), 10));
			}
			if(this.get(i).getCheckCode() == null || this.get(i).getCheckCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCheckCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPODataCheck");
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
			tError.moduleName = "LBPODataCheckDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPODataCheck(BussNoType ,CheckOrder ,CheckCode ,CalSql ,Noti) VALUES( ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussNoType() == null || this.get(i).getBussNoType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussNoType());
			}
			if(this.get(i).getCheckOrder() == null || this.get(i).getCheckOrder().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCheckOrder());
			}
			if(this.get(i).getCheckCode() == null || this.get(i).getCheckCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCheckCode());
			}
			if(this.get(i).getCalSql() == null || this.get(i).getCalSql().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalSql());
			}
			if(this.get(i).getNoti() == null || this.get(i).getNoti().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getNoti());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPODataCheck");
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
			tError.moduleName = "LBPODataCheckDBSet";
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