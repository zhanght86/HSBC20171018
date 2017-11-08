/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.vschema.LLClaimUWMainSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLClaimUWMainDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimUWMainDBSet extends LLClaimUWMainSet
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
	public LLClaimUWMainDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLClaimUWMain");
		mflag = true;
	}

	public LLClaimUWMainDBSet()
	{
		db = new DBOper( "LLClaimUWMain" );
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
			tError.moduleName = "LLClaimUWMainDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLClaimUWMain WHERE  ClmNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimUWMain");
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
			tError.moduleName = "LLClaimUWMainDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLClaimUWMain SET  ClmNo = ? , RgtNo = ? , CaseNo = ? , ClmUWer = ? , ClmUWGrade = ? , ClmDecision = ? , AppGrade = ? , AppClmUWer = ? , AppActionType = ? , Remark1 = ? , Remark2 = ? , checkDecision1 = ? , checkDecision2 = ? , Remark = ? , Operator = ? , MngCom = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , CheckType = ? , AppPhase = ? , AuditConclusion = ? , AuditNoPassReason = ? , AuditNoPassDesc = ? , AuditIdea = ? , AuditLevel = ? , AuditPer = ? , AuditDate = ? , ExamConclusion = ? , ExamNoPassReason = ? , ExamNoPassDesc = ? , ExamIdea = ? , ExamLevel = ? , ExamPer = ? , ExamDate = ? , SpecialRemark = ? , ExamCom = ? , DSClaimFlag = ? , ComCode = ? , ModifyOperator = ? WHERE  ClmNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getClmUWer() == null || this.get(i).getClmUWer().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getClmUWer());
			}
			if(this.get(i).getClmUWGrade() == null || this.get(i).getClmUWGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmUWGrade());
			}
			if(this.get(i).getClmDecision() == null || this.get(i).getClmDecision().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getClmDecision());
			}
			if(this.get(i).getAppGrade() == null || this.get(i).getAppGrade().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAppGrade());
			}
			if(this.get(i).getAppClmUWer() == null || this.get(i).getAppClmUWer().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppClmUWer());
			}
			if(this.get(i).getAppActionType() == null || this.get(i).getAppActionType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAppActionType());
			}
			if(this.get(i).getRemark1() == null || this.get(i).getRemark1().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRemark1());
			}
			if(this.get(i).getRemark2() == null || this.get(i).getRemark2().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRemark2());
			}
			if(this.get(i).getcheckDecision1() == null || this.get(i).getcheckDecision1().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getcheckDecision1());
			}
			if(this.get(i).getcheckDecision2() == null || this.get(i).getcheckDecision2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getcheckDecision2());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRemark());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOperator());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMngCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getCheckType() == null || this.get(i).getCheckType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getCheckType());
			}
			if(this.get(i).getAppPhase() == null || this.get(i).getAppPhase().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAppPhase());
			}
			if(this.get(i).getAuditConclusion() == null || this.get(i).getAuditConclusion().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAuditConclusion());
			}
			if(this.get(i).getAuditNoPassReason() == null || this.get(i).getAuditNoPassReason().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAuditNoPassReason());
			}
			if(this.get(i).getAuditNoPassDesc() == null || this.get(i).getAuditNoPassDesc().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAuditNoPassDesc());
			}
			if(this.get(i).getAuditIdea() == null || this.get(i).getAuditIdea().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAuditIdea());
			}
			if(this.get(i).getAuditLevel() == null || this.get(i).getAuditLevel().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAuditLevel());
			}
			if(this.get(i).getAuditPer() == null || this.get(i).getAuditPer().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAuditPer());
			}
			if(this.get(i).getAuditDate() == null || this.get(i).getAuditDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getAuditDate()));
			}
			if(this.get(i).getExamConclusion() == null || this.get(i).getExamConclusion().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getExamConclusion());
			}
			if(this.get(i).getExamNoPassReason() == null || this.get(i).getExamNoPassReason().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getExamNoPassReason());
			}
			if(this.get(i).getExamNoPassDesc() == null || this.get(i).getExamNoPassDesc().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getExamNoPassDesc());
			}
			if(this.get(i).getExamIdea() == null || this.get(i).getExamIdea().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getExamIdea());
			}
			if(this.get(i).getExamLevel() == null || this.get(i).getExamLevel().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getExamLevel());
			}
			if(this.get(i).getExamPer() == null || this.get(i).getExamPer().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getExamPer());
			}
			if(this.get(i).getExamDate() == null || this.get(i).getExamDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getExamDate()));
			}
			if(this.get(i).getSpecialRemark() == null || this.get(i).getSpecialRemark().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSpecialRemark());
			}
			if(this.get(i).getExamCom() == null || this.get(i).getExamCom().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getExamCom());
			}
			if(this.get(i).getDSClaimFlag() == null || this.get(i).getDSClaimFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getDSClaimFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getClmNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimUWMain");
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
			tError.moduleName = "LLClaimUWMainDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLClaimUWMain VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getClmUWer() == null || this.get(i).getClmUWer().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getClmUWer());
			}
			if(this.get(i).getClmUWGrade() == null || this.get(i).getClmUWGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmUWGrade());
			}
			if(this.get(i).getClmDecision() == null || this.get(i).getClmDecision().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getClmDecision());
			}
			if(this.get(i).getAppGrade() == null || this.get(i).getAppGrade().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAppGrade());
			}
			if(this.get(i).getAppClmUWer() == null || this.get(i).getAppClmUWer().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppClmUWer());
			}
			if(this.get(i).getAppActionType() == null || this.get(i).getAppActionType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAppActionType());
			}
			if(this.get(i).getRemark1() == null || this.get(i).getRemark1().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRemark1());
			}
			if(this.get(i).getRemark2() == null || this.get(i).getRemark2().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRemark2());
			}
			if(this.get(i).getcheckDecision1() == null || this.get(i).getcheckDecision1().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getcheckDecision1());
			}
			if(this.get(i).getcheckDecision2() == null || this.get(i).getcheckDecision2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getcheckDecision2());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRemark());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOperator());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getMngCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getCheckType() == null || this.get(i).getCheckType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getCheckType());
			}
			if(this.get(i).getAppPhase() == null || this.get(i).getAppPhase().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAppPhase());
			}
			if(this.get(i).getAuditConclusion() == null || this.get(i).getAuditConclusion().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAuditConclusion());
			}
			if(this.get(i).getAuditNoPassReason() == null || this.get(i).getAuditNoPassReason().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAuditNoPassReason());
			}
			if(this.get(i).getAuditNoPassDesc() == null || this.get(i).getAuditNoPassDesc().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAuditNoPassDesc());
			}
			if(this.get(i).getAuditIdea() == null || this.get(i).getAuditIdea().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAuditIdea());
			}
			if(this.get(i).getAuditLevel() == null || this.get(i).getAuditLevel().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAuditLevel());
			}
			if(this.get(i).getAuditPer() == null || this.get(i).getAuditPer().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAuditPer());
			}
			if(this.get(i).getAuditDate() == null || this.get(i).getAuditDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getAuditDate()));
			}
			if(this.get(i).getExamConclusion() == null || this.get(i).getExamConclusion().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getExamConclusion());
			}
			if(this.get(i).getExamNoPassReason() == null || this.get(i).getExamNoPassReason().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getExamNoPassReason());
			}
			if(this.get(i).getExamNoPassDesc() == null || this.get(i).getExamNoPassDesc().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getExamNoPassDesc());
			}
			if(this.get(i).getExamIdea() == null || this.get(i).getExamIdea().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getExamIdea());
			}
			if(this.get(i).getExamLevel() == null || this.get(i).getExamLevel().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getExamLevel());
			}
			if(this.get(i).getExamPer() == null || this.get(i).getExamPer().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getExamPer());
			}
			if(this.get(i).getExamDate() == null || this.get(i).getExamDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getExamDate()));
			}
			if(this.get(i).getSpecialRemark() == null || this.get(i).getSpecialRemark().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSpecialRemark());
			}
			if(this.get(i).getExamCom() == null || this.get(i).getExamCom().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getExamCom());
			}
			if(this.get(i).getDSClaimFlag() == null || this.get(i).getDSClaimFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getDSClaimFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimUWMain");
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
			tError.moduleName = "LLClaimUWMainDBSet";
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
