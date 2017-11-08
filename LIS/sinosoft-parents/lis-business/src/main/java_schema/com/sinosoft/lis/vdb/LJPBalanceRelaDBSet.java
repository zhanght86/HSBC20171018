/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LJPBalanceRelaSchema;
import com.sinosoft.lis.vschema.LJPBalanceRelaSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJPBalanceRelaDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPBalanceRelaDBSet extends LJPBalanceRelaSet
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
	public LJPBalanceRelaDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LJPBalanceRela");
		mflag = true;
	}

	public LJPBalanceRelaDBSet()
	{
		db = new DBOper( "LJPBalanceRela" );
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
			tError.moduleName = "LJPBalanceRelaDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LJPBalanceRela WHERE  BalanceRelaNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBalanceRelaNo() == null || this.get(i).getBalanceRelaNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBalanceRelaNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJPBalanceRela");
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
			tError.moduleName = "LJPBalanceRelaDBSet";
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
			pstmt = con.prepareStatement("UPDATE LJPBalanceRela SET  BalanceRelaNo = ? , BalanceAllNo = ? , GrpContNo = ? , BalanceType = ? , BalanceRelaSum = ? , BalanceRelaOperator = ? , BalanceRelaType = ? , BalanceRelaState = ? , BalanceRelaDate = ? , BalanceRelaTime = ? WHERE  BalanceRelaNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBalanceRelaNo() == null || this.get(i).getBalanceRelaNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBalanceRelaNo());
			}
			if(this.get(i).getBalanceAllNo() == null || this.get(i).getBalanceAllNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBalanceAllNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getBalanceType() == null || this.get(i).getBalanceType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBalanceType());
			}
			pstmt.setDouble(5, this.get(i).getBalanceRelaSum());
			if(this.get(i).getBalanceRelaOperator() == null || this.get(i).getBalanceRelaOperator().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBalanceRelaOperator());
			}
			if(this.get(i).getBalanceRelaType() == null || this.get(i).getBalanceRelaType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBalanceRelaType());
			}
			if(this.get(i).getBalanceRelaState() == null || this.get(i).getBalanceRelaState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBalanceRelaState());
			}
			if(this.get(i).getBalanceRelaDate() == null || this.get(i).getBalanceRelaDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getBalanceRelaDate()));
			}
			if(this.get(i).getBalanceRelaTime() == null || this.get(i).getBalanceRelaTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBalanceRelaTime());
			}
			// set where condition
			if(this.get(i).getBalanceRelaNo() == null || this.get(i).getBalanceRelaNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBalanceRelaNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJPBalanceRela");
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
			tError.moduleName = "LJPBalanceRelaDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LJPBalanceRela VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBalanceRelaNo() == null || this.get(i).getBalanceRelaNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBalanceRelaNo());
			}
			if(this.get(i).getBalanceAllNo() == null || this.get(i).getBalanceAllNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBalanceAllNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getBalanceType() == null || this.get(i).getBalanceType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBalanceType());
			}
			pstmt.setDouble(5, this.get(i).getBalanceRelaSum());
			if(this.get(i).getBalanceRelaOperator() == null || this.get(i).getBalanceRelaOperator().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBalanceRelaOperator());
			}
			if(this.get(i).getBalanceRelaType() == null || this.get(i).getBalanceRelaType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBalanceRelaType());
			}
			if(this.get(i).getBalanceRelaState() == null || this.get(i).getBalanceRelaState().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBalanceRelaState());
			}
			if(this.get(i).getBalanceRelaDate() == null || this.get(i).getBalanceRelaDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getBalanceRelaDate()));
			}
			if(this.get(i).getBalanceRelaTime() == null || this.get(i).getBalanceRelaTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBalanceRelaTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJPBalanceRela");
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
			tError.moduleName = "LJPBalanceRelaDBSet";
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
