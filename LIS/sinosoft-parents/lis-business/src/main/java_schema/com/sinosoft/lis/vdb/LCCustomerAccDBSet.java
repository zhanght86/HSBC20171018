/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LCCustomerAccSchema;
import com.sinosoft.lis.vschema.LCCustomerAccSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCCustomerAccDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LCCustomerAccDBSet extends LCCustomerAccSet
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
	public LCCustomerAccDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCCustomerAcc");
		mflag = true;
	}

	public LCCustomerAccDBSet()
	{
		db = new DBOper( "LCCustomerAcc" );
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
			tError.moduleName = "LCCustomerAccDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCCustomerAcc WHERE  CustomerNo = ? AND CustomerType = ? AND InsuAccNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerType() == null || this.get(i).getCustomerType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, StrTool.space1(this.get(i).getCustomerType(), 1));
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuAccNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCustomerAcc");
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
			tError.moduleName = "LCCustomerAccDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCCustomerAcc SET  CustomerNo = ? , CustomerType = ? , InsuAccNo = ? , AccType = ? , AccComputeFlag = ? , BalaDate = ? , BalaTime = ? , SumPay = ? , SumPaym = ? , InsuAccBala = ? , InsuAccGetMoney = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  CustomerNo = ? AND CustomerType = ? AND InsuAccNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerType() == null || this.get(i).getCustomerType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerType());
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getAccType() == null || this.get(i).getAccType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccType());
			}
			if(this.get(i).getAccComputeFlag() == null || this.get(i).getAccComputeFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccComputeFlag());
			}
			if(this.get(i).getBalaDate() == null || this.get(i).getBalaDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getBalaDate()));
			}
			if(this.get(i).getBalaTime() == null || this.get(i).getBalaTime().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBalaTime());
			}
			pstmt.setDouble(8, this.get(i).getSumPay());
			pstmt.setDouble(9, this.get(i).getSumPaym());
			pstmt.setDouble(10, this.get(i).getInsuAccBala());
			pstmt.setDouble(11, this.get(i).getInsuAccGetMoney());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerType() == null || this.get(i).getCustomerType().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, StrTool.space1(this.get(i).getCustomerType(), 1));
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getInsuAccNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCustomerAcc");
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
			tError.moduleName = "LCCustomerAccDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCCustomerAcc VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerType() == null || this.get(i).getCustomerType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerType());
			}
			if(this.get(i).getInsuAccNo() == null || this.get(i).getInsuAccNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuAccNo());
			}
			if(this.get(i).getAccType() == null || this.get(i).getAccType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccType());
			}
			if(this.get(i).getAccComputeFlag() == null || this.get(i).getAccComputeFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccComputeFlag());
			}
			if(this.get(i).getBalaDate() == null || this.get(i).getBalaDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getBalaDate()));
			}
			if(this.get(i).getBalaTime() == null || this.get(i).getBalaTime().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBalaTime());
			}
			pstmt.setDouble(8, this.get(i).getSumPay());
			pstmt.setDouble(9, this.get(i).getSumPaym());
			pstmt.setDouble(10, this.get(i).getInsuAccBala());
			pstmt.setDouble(11, this.get(i).getInsuAccGetMoney());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCustomerAcc");
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
			tError.moduleName = "LCCustomerAccDBSet";
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