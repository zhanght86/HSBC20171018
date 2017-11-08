/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LWProcessTransSchema;
import com.sinosoft.lis.vschema.LWProcessTransSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LWProcessTransDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWProcessTransDBSet extends LWProcessTransSet
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
	public LWProcessTransDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LWProcessTrans");
		mflag = true;
	}

	public LWProcessTransDBSet()
	{
		db = new DBOper( "LWProcessTrans" );
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
			tError.moduleName = "LWProcessTransDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LWProcessTrans WHERE  TransitionID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTransitionID() == null || this.get(i).getTransitionID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTransitionID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWProcessTrans");
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
			tError.moduleName = "LWProcessTransDBSet";
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
			pstmt = con.prepareStatement("UPDATE LWProcessTrans SET  TransitionID = ? , ProcessID = ? , TransitionStart = ? , TransitionEnd = ? , TransitionCond = ? , TransitionCondT = ? , TransitionModel = ? , StartType = ? , Version = ? WHERE  TransitionID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTransitionID() == null || this.get(i).getTransitionID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTransitionID());
			}
			if(this.get(i).getProcessID() == null || this.get(i).getProcessID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getProcessID());
			}
			if(this.get(i).getTransitionStart() == null || this.get(i).getTransitionStart().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTransitionStart());
			}
			if(this.get(i).getTransitionEnd() == null || this.get(i).getTransitionEnd().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getTransitionEnd());
			}
			if(this.get(i).getTransitionCond() == null || this.get(i).getTransitionCond().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getTransitionCond());
			}
			if(this.get(i).getTransitionCondT() == null || this.get(i).getTransitionCondT().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getTransitionCondT());
			}
			if(this.get(i).getTransitionModel() == null || this.get(i).getTransitionModel().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getTransitionModel());
			}
			if(this.get(i).getStartType() == null || this.get(i).getStartType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getStartType());
			}
			if(this.get(i).getVersion() == null || this.get(i).getVersion().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getVersion());
			}
			// set where condition
			if(this.get(i).getTransitionID() == null || this.get(i).getTransitionID().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getTransitionID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWProcessTrans");
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
			tError.moduleName = "LWProcessTransDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LWProcessTrans VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTransitionID() == null || this.get(i).getTransitionID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTransitionID());
			}
			if(this.get(i).getProcessID() == null || this.get(i).getProcessID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getProcessID());
			}
			if(this.get(i).getTransitionStart() == null || this.get(i).getTransitionStart().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTransitionStart());
			}
			if(this.get(i).getTransitionEnd() == null || this.get(i).getTransitionEnd().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getTransitionEnd());
			}
			if(this.get(i).getTransitionCond() == null || this.get(i).getTransitionCond().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getTransitionCond());
			}
			if(this.get(i).getTransitionCondT() == null || this.get(i).getTransitionCondT().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getTransitionCondT());
			}
			if(this.get(i).getTransitionModel() == null || this.get(i).getTransitionModel().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getTransitionModel());
			}
			if(this.get(i).getStartType() == null || this.get(i).getStartType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getStartType());
			}
			if(this.get(i).getVersion() == null || this.get(i).getVersion().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getVersion());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LWProcessTrans");
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
			tError.moduleName = "LWProcessTransDBSet";
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
