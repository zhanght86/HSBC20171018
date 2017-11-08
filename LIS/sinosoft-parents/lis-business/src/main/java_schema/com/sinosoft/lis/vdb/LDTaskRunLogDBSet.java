/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDTaskRunLogSchema;
import com.sinosoft.lis.vschema.LDTaskRunLogSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDTaskRunLogDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: LDTaskRunLog
 */
public class LDTaskRunLogDBSet extends LDTaskRunLogSet
{
private static Logger logger = Logger.getLogger(LDTaskRunLogDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDTaskRunLogDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDTaskRunLog");
		mflag = true;
	}

	public LDTaskRunLogDBSet()
	{
		db = new DBOper( "LDTaskRunLog" );
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
			tError.moduleName = "LDTaskRunLogDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDTaskRunLog WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDTaskRunLog");
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
			tError.moduleName = "LDTaskRunLogDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDTaskRunLog SET  SerialNo = ? , TaskCode = ? , TaskPlanCode = ? , ExecuteDate = ? , ExecuteTime = ? , FinishDate = ? , FinishTime = ? , ExecuteFrequence = ? , ExecuteState = ? , ExecuteResult = ? , ServerInfo = ? , TaskGroupCode = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());
			if(this.get(i).getTaskCode() == null || this.get(i).getTaskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTaskCode());
			}
			if(this.get(i).getTaskPlanCode() == null || this.get(i).getTaskPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTaskPlanCode());
			}
			if(this.get(i).getExecuteDate() == null || this.get(i).getExecuteDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getExecuteDate()));
			}
			if(this.get(i).getExecuteTime() == null || this.get(i).getExecuteTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getExecuteTime());
			}
			if(this.get(i).getFinishDate() == null || this.get(i).getFinishDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getFinishDate()));
			}
			if(this.get(i).getFinishTime() == null || this.get(i).getFinishTime().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFinishTime());
			}
			pstmt.setInt(8, this.get(i).getExecuteFrequence());
			if(this.get(i).getExecuteState() == null || this.get(i).getExecuteState().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getExecuteState());
			}
			if(this.get(i).getExecuteResult() == null || this.get(i).getExecuteResult().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExecuteResult());
			}
			if(this.get(i).getServerInfo() == null || this.get(i).getServerInfo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getServerInfo());
			}
			if(this.get(i).getTaskGroupCode() == null || this.get(i).getTaskGroupCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getTaskGroupCode());
			}
			// set where condition
			pstmt.setInt(13, this.get(i).getSerialNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDTaskRunLog");
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
			tError.moduleName = "LDTaskRunLogDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDTaskRunLog VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());
			if(this.get(i).getTaskCode() == null || this.get(i).getTaskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTaskCode());
			}
			if(this.get(i).getTaskPlanCode() == null || this.get(i).getTaskPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getTaskPlanCode());
			}
			if(this.get(i).getExecuteDate() == null || this.get(i).getExecuteDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getExecuteDate()));
			}
			if(this.get(i).getExecuteTime() == null || this.get(i).getExecuteTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getExecuteTime());
			}
			if(this.get(i).getFinishDate() == null || this.get(i).getFinishDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getFinishDate()));
			}
			if(this.get(i).getFinishTime() == null || this.get(i).getFinishTime().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFinishTime());
			}
			pstmt.setInt(8, this.get(i).getExecuteFrequence());
			if(this.get(i).getExecuteState() == null || this.get(i).getExecuteState().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getExecuteState());
			}
			if(this.get(i).getExecuteResult() == null || this.get(i).getExecuteResult().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExecuteResult());
			}
			if(this.get(i).getServerInfo() == null || this.get(i).getServerInfo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getServerInfo());
			}
			if(this.get(i).getTaskGroupCode() == null || this.get(i).getTaskGroupCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getTaskGroupCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDTaskRunLog");
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
			tError.moduleName = "LDTaskRunLogDBSet";
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
