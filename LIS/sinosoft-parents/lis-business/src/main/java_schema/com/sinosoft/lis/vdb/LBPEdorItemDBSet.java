/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBPEdorItemSchema;
import com.sinosoft.lis.vschema.LBPEdorItemSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPEdorItemDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LBPEdorItemDBSet extends LBPEdorItemSet
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
	public LBPEdorItemDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPEdorItem");
		mflag = true;
	}

	public LBPEdorItemDBSet()
	{
		db = new DBOper( "LBPEdorItem" );
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
			tError.moduleName = "LBPEdorItemDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPEdorItem WHERE  EdorAcceptNo = ? AND EdorNo = ? AND EdorType = ? AND ContNo = ? AND InsuredNo = ? AND PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPEdorItem");
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
			tError.moduleName = "LBPEdorItemDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPEdorItem SET  EdorAcceptNo = ? , EdorNo = ? , EdorAppNo = ? , EdorType = ? , DisplayType = ? , GrpContNo = ? , ContNo = ? , InsuredNo = ? , PolNo = ? , ManageCom = ? , EdorValiDate = ? , EdorAppDate = ? , EdorState = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , ChgPrem = ? , ChgAmnt = ? , GetMoney = ? , GetInterest = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , AppReason = ? , EdorReasonCode = ? , EdorReason = ? , ApproveFlag = ? , ApproveOperator = ? , ApproveDate = ? , ApproveTime = ? , Standbyflag1 = ? , standbyflag2 = ? , standbyflag3 = ? , EdorTypeCal = ? WHERE  EdorAcceptNo = ? AND EdorNo = ? AND EdorType = ? AND ContNo = ? AND InsuredNo = ? AND PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorAppNo() == null || this.get(i).getEdorAppNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorAppNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEdorType());
			}
			if(this.get(i).getDisplayType() == null || this.get(i).getDisplayType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDisplayType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPolNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getManageCom());
			}
			if(this.get(i).getEdorValiDate() == null || this.get(i).getEdorValiDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getEdorValiDate()));
			}
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
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getUWTime());
			}
			pstmt.setDouble(18, this.get(i).getChgPrem());
			pstmt.setDouble(19, this.get(i).getChgAmnt());
			pstmt.setDouble(20, this.get(i).getGetMoney());
			pstmt.setDouble(21, this.get(i).getGetInterest());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getModifyTime());
			}
			if(this.get(i).getAppReason() == null || this.get(i).getAppReason().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAppReason());
			}
			if(this.get(i).getEdorReasonCode() == null || this.get(i).getEdorReasonCode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getEdorReasonCode());
			}
			if(this.get(i).getEdorReason() == null || this.get(i).getEdorReason().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getEdorReason());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveOperator() == null || this.get(i).getApproveOperator().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getApproveOperator());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getApproveTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getstandbyflag2() == null || this.get(i).getstandbyflag2().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getstandbyflag2());
			}
			if(this.get(i).getstandbyflag3() == null || this.get(i).getstandbyflag3().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getstandbyflag3());
			}
			if(this.get(i).getEdorTypeCal() == null || this.get(i).getEdorTypeCal().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getEdorTypeCal());
			}
			// set where condition
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getEdorType());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPEdorItem");
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
			tError.moduleName = "LBPEdorItemDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPEdorItem VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorAcceptNo() == null || this.get(i).getEdorAcceptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorAcceptNo());
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorAppNo() == null || this.get(i).getEdorAppNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorAppNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEdorType());
			}
			if(this.get(i).getDisplayType() == null || this.get(i).getDisplayType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDisplayType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPolNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getManageCom());
			}
			if(this.get(i).getEdorValiDate() == null || this.get(i).getEdorValiDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getEdorValiDate()));
			}
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
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getUWTime());
			}
			pstmt.setDouble(18, this.get(i).getChgPrem());
			pstmt.setDouble(19, this.get(i).getChgAmnt());
			pstmt.setDouble(20, this.get(i).getGetMoney());
			pstmt.setDouble(21, this.get(i).getGetInterest());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getModifyTime());
			}
			if(this.get(i).getAppReason() == null || this.get(i).getAppReason().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAppReason());
			}
			if(this.get(i).getEdorReasonCode() == null || this.get(i).getEdorReasonCode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getEdorReasonCode());
			}
			if(this.get(i).getEdorReason() == null || this.get(i).getEdorReason().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getEdorReason());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveOperator() == null || this.get(i).getApproveOperator().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getApproveOperator());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getApproveTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getstandbyflag2() == null || this.get(i).getstandbyflag2().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getstandbyflag2());
			}
			if(this.get(i).getstandbyflag3() == null || this.get(i).getstandbyflag3().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getstandbyflag3());
			}
			if(this.get(i).getEdorTypeCal() == null || this.get(i).getEdorTypeCal().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getEdorTypeCal());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPEdorItem");
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
			tError.moduleName = "LBPEdorItemDBSet";
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
