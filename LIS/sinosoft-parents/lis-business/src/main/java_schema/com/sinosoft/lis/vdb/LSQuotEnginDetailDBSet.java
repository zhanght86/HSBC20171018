/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LSQuotEnginDetailSchema;
import com.sinosoft.lis.vschema.LSQuotEnginDetailSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LSQuotEnginDetailDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotEnginDetailDBSet extends LSQuotEnginDetailSet
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
	public LSQuotEnginDetailDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LSQuotEnginDetail");
		mflag = true;
	}

	public LSQuotEnginDetailDBSet()
	{
		db = new DBOper( "LSQuotEnginDetail" );
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
			tError.moduleName = "LSQuotEnginDetailDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LSQuotEnginDetail WHERE  QuotNo = ? AND QuotBatNo = ? AND SysPlanCode = ? AND PlanCode = ? AND EnginCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPlanCode());
			}
			if(this.get(i).getEnginCode() == null || this.get(i).getEnginCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getEnginCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotEnginDetail");
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
			tError.moduleName = "LSQuotEnginDetailDBSet";
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
			pstmt = con.prepareStatement("UPDATE LSQuotEnginDetail SET  QuotNo = ? , QuotBatNo = ? , SysPlanCode = ? , PlanCode = ? , EnginCode = ? , OtherDesc = ? WHERE  QuotNo = ? AND QuotBatNo = ? AND SysPlanCode = ? AND PlanCode = ? AND EnginCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPlanCode());
			}
			if(this.get(i).getEnginCode() == null || this.get(i).getEnginCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getEnginCode());
			}
			if(this.get(i).getOtherDesc() == null || this.get(i).getOtherDesc().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOtherDesc());
			}
			// set where condition
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getQuotNo());
			}
			pstmt.setInt(8, this.get(i).getQuotBatNo());
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPlanCode());
			}
			if(this.get(i).getEnginCode() == null || this.get(i).getEnginCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getEnginCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotEnginDetail");
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
			tError.moduleName = "LSQuotEnginDetailDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LSQuotEnginDetail VALUES( ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPlanCode());
			}
			if(this.get(i).getEnginCode() == null || this.get(i).getEnginCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getEnginCode());
			}
			if(this.get(i).getOtherDesc() == null || this.get(i).getOtherDesc().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOtherDesc());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotEnginDetail");
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
			tError.moduleName = "LSQuotEnginDetailDBSet";
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
