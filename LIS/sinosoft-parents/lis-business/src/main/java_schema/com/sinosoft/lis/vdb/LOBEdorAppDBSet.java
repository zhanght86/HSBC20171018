/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LOBEdorAppSchema;
import com.sinosoft.lis.vschema.LOBEdorAppSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOBEdorAppDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LOBEdorAppDBSet extends LOBEdorAppSet
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
	public LOBEdorAppDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LOBEdorApp");
		mflag = true;
	}

	public LOBEdorAppDBSet()
	{
		db = new DBOper( "LOBEdorApp" );
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
			tError.moduleName = "LOBEdorAppDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LOBEdorApp WHERE  EdorAcceptNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorAcceptNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBEdorApp");
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
			tError.moduleName = "LOBEdorAppDBSet";
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
			pstmt = con.prepareStatement("UPDATE LOBEdorApp SET  EdorAcceptNo = ? , OtherNo = ? , OtherNoType = ? , EdorAppName = ? , AppType = ? , ManageCom = ? , ChgPrem = ? , ChgAmnt = ? , ChgGetAmnt = ? , GetMoney = ? , GetInterest = ? , EdorAppDate = ? , EdorState = ? , BankCode = ? , BankAccNo = ? , AccName = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , PrintFlag = ? , AppGrade = ? , UWState = ? , UWGrade = ? , UWOperator = ? , UWDate = ? , UWTime = ? , ConfOperator = ? , ConfDate = ? , ConfTime = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Reason = ? , ReasonCode = ? , ApproveFlag = ? , ApproveOperator = ? , ApproveDate = ? , ApproveTime = ? , PayGetName = ? , PersonID = ? , PayForm = ? , GetForm = ? , ApproveGrade = ? , AppPreGrade = ? , Currency = ? WHERE  EdorAcceptNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}
			if(this.get(i).getEdorAppName() == null || this.get(i).getEdorAppName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEdorAppName());
			}
			if(this.get(i).getAppType() == null || this.get(i).getAppType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppType());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			pstmt.setDouble(7, this.get(i).getChgPrem());
			pstmt.setDouble(8, this.get(i).getChgAmnt());
			pstmt.setDouble(9, this.get(i).getChgGetAmnt());
			pstmt.setDouble(10, this.get(i).getGetMoney());
			pstmt.setDouble(11, this.get(i).getGetInterest());
			if(this.get(i).getEdorAppDate() == null || this.get(i).getEdorAppDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getEdorAppDate()));
			}
			if(this.get(i).getEdorState() == null || this.get(i).getEdorState().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getEdorState());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAccName());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPhone());
			}
			if(this.get(i).getPrintFlag() == null || this.get(i).getPrintFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPrintFlag());
			}
			if(this.get(i).getAppGrade() == null || this.get(i).getAppGrade().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAppGrade());
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUWState());
			}
			if(this.get(i).getUWGrade() == null || this.get(i).getUWGrade().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getUWGrade());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getUWTime());
			}
			if(this.get(i).getConfOperator() == null || this.get(i).getConfOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getConfOperator());
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getConfTime() == null || this.get(i).getConfTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getConfTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyTime());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getReason());
			}
			if(this.get(i).getReasonCode() == null || this.get(i).getReasonCode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getReasonCode());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveOperator() == null || this.get(i).getApproveOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getApproveOperator());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getApproveTime());
			}
			if(this.get(i).getPayGetName() == null || this.get(i).getPayGetName().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPayGetName());
			}
			if(this.get(i).getPersonID() == null || this.get(i).getPersonID().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPersonID());
			}
			if(this.get(i).getPayForm() == null || this.get(i).getPayForm().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPayForm());
			}
			if(this.get(i).getGetForm() == null || this.get(i).getGetForm().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getGetForm());
			}
			if(this.get(i).getApproveGrade() == null || this.get(i).getApproveGrade().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getApproveGrade());
			}
			if(this.get(i).getAppPreGrade() == null || this.get(i).getAppPreGrade().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getAppPreGrade());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getCurrency());
			}
			// set where condition
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getEdorAcceptNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBEdorApp");
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
			tError.moduleName = "LOBEdorAppDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LOBEdorApp VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}
			if(this.get(i).getEdorAppName() == null || this.get(i).getEdorAppName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEdorAppName());
			}
			if(this.get(i).getAppType() == null || this.get(i).getAppType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppType());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			pstmt.setDouble(7, this.get(i).getChgPrem());
			pstmt.setDouble(8, this.get(i).getChgAmnt());
			pstmt.setDouble(9, this.get(i).getChgGetAmnt());
			pstmt.setDouble(10, this.get(i).getGetMoney());
			pstmt.setDouble(11, this.get(i).getGetInterest());
			if(this.get(i).getEdorAppDate() == null || this.get(i).getEdorAppDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getEdorAppDate()));
			}
			if(this.get(i).getEdorState() == null || this.get(i).getEdorState().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getEdorState());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAccName());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPhone());
			}
			if(this.get(i).getPrintFlag() == null || this.get(i).getPrintFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPrintFlag());
			}
			if(this.get(i).getAppGrade() == null || this.get(i).getAppGrade().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAppGrade());
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUWState());
			}
			if(this.get(i).getUWGrade() == null || this.get(i).getUWGrade().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getUWGrade());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getUWTime());
			}
			if(this.get(i).getConfOperator() == null || this.get(i).getConfOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getConfOperator());
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getConfTime() == null || this.get(i).getConfTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getConfTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyTime());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getReason());
			}
			if(this.get(i).getReasonCode() == null || this.get(i).getReasonCode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getReasonCode());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveOperator() == null || this.get(i).getApproveOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getApproveOperator());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getApproveTime());
			}
			if(this.get(i).getPayGetName() == null || this.get(i).getPayGetName().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPayGetName());
			}
			if(this.get(i).getPersonID() == null || this.get(i).getPersonID().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPersonID());
			}
			if(this.get(i).getPayForm() == null || this.get(i).getPayForm().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPayForm());
			}
			if(this.get(i).getGetForm() == null || this.get(i).getGetForm().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getGetForm());
			}
			if(this.get(i).getApproveGrade() == null || this.get(i).getApproveGrade().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getApproveGrade());
			}
			if(this.get(i).getAppPreGrade() == null || this.get(i).getAppPreGrade().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getAppPreGrade());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getCurrency());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBEdorApp");
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
			tError.moduleName = "LOBEdorAppDBSet";
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
