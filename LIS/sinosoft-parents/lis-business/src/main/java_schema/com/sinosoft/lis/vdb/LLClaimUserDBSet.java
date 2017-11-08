/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.vschema.LLClaimUserSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLClaimUserDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUserDBSet extends LLClaimUserSet
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
	public LLClaimUserDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLClaimUser");
		mflag = true;
	}

	public LLClaimUserDBSet()
	{
		db = new DBOper( "LLClaimUser" );
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
			tError.moduleName = "LLClaimUserDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLClaimUser WHERE  UserCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getUserCode() == null || this.get(i).getUserCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getUserCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimUser");
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
			tError.moduleName = "LLClaimUserDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLClaimUser SET  UserCode = ? , UserName = ? , ComCode = ? , JobCategory = ? , UpUserCode = ? , HandleFlag = ? , ClaimDeal = ? , ClaimQuery = ? , ReportFlag = ? , RegisterFlag = ? , ClaimFlag = ? , ClaimPopedom = ? , CheckFlag = ? , UWFlag = ? , SubmitFlag = ? , SurveyFlag = ? , PayFlag = ? , CheckLevel = ? , UWLevel = ? , StateFlag = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModiftTime = ? , Operator = ? , ManageCom = ? , Reason = ? , Remark = ? , RgtFlag = ? , RelPhone = ? , RelDept = ? , PrepayFlag = ? , SimpleFlag = ? , ExemptFlag = ? , CertifyScan = ? , TaskAssign = ? , Email = ? , ModifyOperator = ? WHERE  UserCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getUserCode() == null || this.get(i).getUserCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getUserCode());
			}
			if(this.get(i).getUserName() == null || this.get(i).getUserName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getUserName());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getComCode());
			}
			if(this.get(i).getJobCategory() == null || this.get(i).getJobCategory().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getJobCategory());
			}
			if(this.get(i).getUpUserCode() == null || this.get(i).getUpUserCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getUpUserCode());
			}
			if(this.get(i).getHandleFlag() == null || this.get(i).getHandleFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getHandleFlag());
			}
			if(this.get(i).getClaimDeal() == null || this.get(i).getClaimDeal().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getClaimDeal());
			}
			if(this.get(i).getClaimQuery() == null || this.get(i).getClaimQuery().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getClaimQuery());
			}
			if(this.get(i).getReportFlag() == null || this.get(i).getReportFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getReportFlag());
			}
			if(this.get(i).getRegisterFlag() == null || this.get(i).getRegisterFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRegisterFlag());
			}
			if(this.get(i).getClaimFlag() == null || this.get(i).getClaimFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getClaimFlag());
			}
			if(this.get(i).getClaimPopedom() == null || this.get(i).getClaimPopedom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getClaimPopedom());
			}
			if(this.get(i).getCheckFlag() == null || this.get(i).getCheckFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCheckFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUWFlag());
			}
			if(this.get(i).getSubmitFlag() == null || this.get(i).getSubmitFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getSubmitFlag());
			}
			if(this.get(i).getSurveyFlag() == null || this.get(i).getSurveyFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSurveyFlag());
			}
			if(this.get(i).getPayFlag() == null || this.get(i).getPayFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getPayFlag());
			}
			if(this.get(i).getCheckLevel() == null || this.get(i).getCheckLevel().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCheckLevel());
			}
			if(this.get(i).getUWLevel() == null || this.get(i).getUWLevel().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getUWLevel());
			}
			if(this.get(i).getStateFlag() == null || this.get(i).getStateFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStateFlag());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModiftTime() == null || this.get(i).getModiftTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModiftTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOperator());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getManageCom());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getReason());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getRemark());
			}
			if(this.get(i).getRgtFlag() == null || this.get(i).getRgtFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getRgtFlag());
			}
			if(this.get(i).getRelPhone() == null || this.get(i).getRelPhone().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getRelPhone());
			}
			if(this.get(i).getRelDept() == null || this.get(i).getRelDept().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getRelDept());
			}
			if(this.get(i).getPrepayFlag() == null || this.get(i).getPrepayFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPrepayFlag());
			}
			if(this.get(i).getSimpleFlag() == null || this.get(i).getSimpleFlag().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getSimpleFlag());
			}
			if(this.get(i).getExemptFlag() == null || this.get(i).getExemptFlag().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getExemptFlag());
			}
			if(this.get(i).getCertifyScan() == null || this.get(i).getCertifyScan().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCertifyScan());
			}
			if(this.get(i).getTaskAssign() == null || this.get(i).getTaskAssign().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getTaskAssign());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getEmail());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getUserCode() == null || this.get(i).getUserCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getUserCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimUser");
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
			tError.moduleName = "LLClaimUserDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLClaimUser VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getUserCode() == null || this.get(i).getUserCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getUserCode());
			}
			if(this.get(i).getUserName() == null || this.get(i).getUserName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getUserName());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getComCode());
			}
			if(this.get(i).getJobCategory() == null || this.get(i).getJobCategory().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getJobCategory());
			}
			if(this.get(i).getUpUserCode() == null || this.get(i).getUpUserCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getUpUserCode());
			}
			if(this.get(i).getHandleFlag() == null || this.get(i).getHandleFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getHandleFlag());
			}
			if(this.get(i).getClaimDeal() == null || this.get(i).getClaimDeal().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getClaimDeal());
			}
			if(this.get(i).getClaimQuery() == null || this.get(i).getClaimQuery().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getClaimQuery());
			}
			if(this.get(i).getReportFlag() == null || this.get(i).getReportFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getReportFlag());
			}
			if(this.get(i).getRegisterFlag() == null || this.get(i).getRegisterFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRegisterFlag());
			}
			if(this.get(i).getClaimFlag() == null || this.get(i).getClaimFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getClaimFlag());
			}
			if(this.get(i).getClaimPopedom() == null || this.get(i).getClaimPopedom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getClaimPopedom());
			}
			if(this.get(i).getCheckFlag() == null || this.get(i).getCheckFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCheckFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUWFlag());
			}
			if(this.get(i).getSubmitFlag() == null || this.get(i).getSubmitFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getSubmitFlag());
			}
			if(this.get(i).getSurveyFlag() == null || this.get(i).getSurveyFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSurveyFlag());
			}
			if(this.get(i).getPayFlag() == null || this.get(i).getPayFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getPayFlag());
			}
			if(this.get(i).getCheckLevel() == null || this.get(i).getCheckLevel().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCheckLevel());
			}
			if(this.get(i).getUWLevel() == null || this.get(i).getUWLevel().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getUWLevel());
			}
			if(this.get(i).getStateFlag() == null || this.get(i).getStateFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStateFlag());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModiftTime() == null || this.get(i).getModiftTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModiftTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOperator());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getManageCom());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getReason());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getRemark());
			}
			if(this.get(i).getRgtFlag() == null || this.get(i).getRgtFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getRgtFlag());
			}
			if(this.get(i).getRelPhone() == null || this.get(i).getRelPhone().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getRelPhone());
			}
			if(this.get(i).getRelDept() == null || this.get(i).getRelDept().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getRelDept());
			}
			if(this.get(i).getPrepayFlag() == null || this.get(i).getPrepayFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPrepayFlag());
			}
			if(this.get(i).getSimpleFlag() == null || this.get(i).getSimpleFlag().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getSimpleFlag());
			}
			if(this.get(i).getExemptFlag() == null || this.get(i).getExemptFlag().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getExemptFlag());
			}
			if(this.get(i).getCertifyScan() == null || this.get(i).getCertifyScan().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCertifyScan());
			}
			if(this.get(i).getTaskAssign() == null || this.get(i).getTaskAssign().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getTaskAssign());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getEmail());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimUser");
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
			tError.moduleName = "LLClaimUserDBSet";
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
