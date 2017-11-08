/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LJRefundConfirmSubSchema;
import com.sinosoft.lis.vschema.LJRefundConfirmSubSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJRefundConfirmSubDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJRefundConfirmSubDBSet extends LJRefundConfirmSubSet
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
	public LJRefundConfirmSubDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LJRefundConfirmSub");
		mflag = true;
	}

	public LJRefundConfirmSubDBSet()
	{
		db = new DBOper( "LJRefundConfirmSub" );
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
			tError.moduleName = "LJRefundConfirmSubDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LJRefundConfirmSub WHERE  ApplyBatNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getApplyBatNo() == null || this.get(i).getApplyBatNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getApplyBatNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJRefundConfirmSub");
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
			tError.moduleName = "LJRefundConfirmSubDBSet";
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
			pstmt = con.prepareStatement("UPDATE LJRefundConfirmSub SET  ApplyBatNo = ? , RefundMoney = ? , CustBankCode = ? , BankCode = ? , CustBankProvince = ? , CustBankCity = ? , CustBankDetail = ? , CustBankAccNo = ? , CustAccName = ? WHERE  ApplyBatNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getApplyBatNo() == null || this.get(i).getApplyBatNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getApplyBatNo());
			}
			pstmt.setDouble(2, this.get(i).getRefundMoney());
			if(this.get(i).getCustBankCode() == null || this.get(i).getCustBankCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCustBankCode());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBankCode());
			}
			if(this.get(i).getCustBankProvince() == null || this.get(i).getCustBankProvince().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCustBankProvince());
			}
			if(this.get(i).getCustBankCity() == null || this.get(i).getCustBankCity().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustBankCity());
			}
			if(this.get(i).getCustBankDetail() == null || this.get(i).getCustBankDetail().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCustBankDetail());
			}
			if(this.get(i).getCustBankAccNo() == null || this.get(i).getCustBankAccNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCustBankAccNo());
			}
			if(this.get(i).getCustAccName() == null || this.get(i).getCustAccName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCustAccName());
			}
			// set where condition
			if(this.get(i).getApplyBatNo() == null || this.get(i).getApplyBatNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getApplyBatNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJRefundConfirmSub");
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
			tError.moduleName = "LJRefundConfirmSubDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LJRefundConfirmSub VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getApplyBatNo() == null || this.get(i).getApplyBatNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getApplyBatNo());
			}
			pstmt.setDouble(2, this.get(i).getRefundMoney());
			if(this.get(i).getCustBankCode() == null || this.get(i).getCustBankCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCustBankCode());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBankCode());
			}
			if(this.get(i).getCustBankProvince() == null || this.get(i).getCustBankProvince().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCustBankProvince());
			}
			if(this.get(i).getCustBankCity() == null || this.get(i).getCustBankCity().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCustBankCity());
			}
			if(this.get(i).getCustBankDetail() == null || this.get(i).getCustBankDetail().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCustBankDetail());
			}
			if(this.get(i).getCustBankAccNo() == null || this.get(i).getCustBankAccNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCustBankAccNo());
			}
			if(this.get(i).getCustAccName() == null || this.get(i).getCustAccName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCustAccName());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJRefundConfirmSub");
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
			tError.moduleName = "LJRefundConfirmSubDBSet";
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
