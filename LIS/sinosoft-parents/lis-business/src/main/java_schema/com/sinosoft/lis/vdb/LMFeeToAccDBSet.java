/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMFeeToAccSchema;
import com.sinosoft.lis.vschema.LMFeeToAccSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMFeeToAccDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LMFeeToAccDBSet extends LMFeeToAccSet
{
private static Logger logger = Logger.getLogger(LMFeeToAccDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMFeeToAccDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMFeeToAcc");
		mflag = true;
	}

	public LMFeeToAccDBSet()
	{
		db = new DBOper( "LMFeeToAcc" );
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
			tError.moduleName = "LMFeeToAccDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMFeeToAcc WHERE  FeeCode = ? AND PayPlanCode = ? AND InsuAccNo = ? AND PayPlanCode1 = ? AND InsuAccNo1 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, StrTool.space1(this.get(i).getFeeCode(), 6));
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space1(this.get(i).getPayPlanCode(), 8));
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getInsuAccNo(), 20));
			}
			if(this.get(i).getPayPlanCode1() == null || this.get(i).getPayPlanCode1().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, StrTool.space1(this.get(i).getPayPlanCode1(), 8));
			}
			if(this.get(i).getInsuAccNo1() == null || this.get(i).getInsuAccNo1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, StrTool.space1(this.get(i).getInsuAccNo1(), 20));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMFeeToAcc");
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
			tError.moduleName = "LMFeeToAccDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMFeeToAcc SET  FeeCode = ? , PayPlanCode = ? , InsuAccNo = ? , PayPlanCode1 = ? , InsuAccNo1 = ? , FeeNum = ? WHERE  FeeCode = ? AND PayPlanCode = ? AND InsuAccNo = ? AND PayPlanCode1 = ? AND InsuAccNo1 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getPayPlanCode1() == null || this.get(i).getPayPlanCode1().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayPlanCode1());
			}
			if(this.get(i).getInsuAccNo1() == null || this.get(i).getInsuAccNo1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getInsuAccNo1());
			}
			pstmt.setInt(6, this.get(i).getFeeNum());
			// set where condition
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, StrTool.space1(this.get(i).getFeeCode(), 6));
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, StrTool.space1(this.get(i).getPayPlanCode(), 8));
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, StrTool.space1(this.get(i).getInsuAccNo(), 20));
			}
			if(this.get(i).getPayPlanCode1() == null || this.get(i).getPayPlanCode1().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, StrTool.space1(this.get(i).getPayPlanCode1(), 8));
			}
			if(this.get(i).getInsuAccNo1() == null || this.get(i).getInsuAccNo1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, StrTool.space1(this.get(i).getInsuAccNo1(), 20));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMFeeToAcc");
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
			tError.moduleName = "LMFeeToAccDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMFeeToAcc VALUES( ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getPayPlanCode1() == null || this.get(i).getPayPlanCode1().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayPlanCode1());
			}
			if(this.get(i).getInsuAccNo1() == null || this.get(i).getInsuAccNo1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getInsuAccNo1());
			}
			pstmt.setInt(6, this.get(i).getFeeNum());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMFeeToAcc");
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
			tError.moduleName = "LMFeeToAccDBSet";
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
