/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLGrpReportSchema;
import com.sinosoft.lis.vschema.LLGrpReportSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLGrpReportDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGrpReportDBSet extends LLGrpReportSet
{
private static Logger logger = Logger.getLogger(LLGrpReportDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLGrpReportDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLGrpReport");
		mflag = true;
	}

	public LLGrpReportDBSet()
	{
		db = new DBOper( "LLGrpReport" );
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
			tError.moduleName = "LLGrpReportDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLGrpReport WHERE  RptNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRptNo() == null || this.get(i).getRptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRptNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpReport");
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
			tError.moduleName = "LLGrpReportDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLGrpReport SET  RptNo = ? , RgtClass = ? , RgtObj = ? , RgtObjNo = ? , RptMode = ? , Relation = ? , RptorName = ? , RptorAddress = ? , RptorPhone = ? , RptorMobile = ? , Email = ? , PostCode = ? , ReturnMode = ? , AccidentDate = ? , AccidentSite = ? , AccidentReason = ? , AccidentCourse = ? , RptDate = ? , CaseNoDate = ? , CaseEndDate = ? , RgtReason = ? , NoRgtReason = ? , Remark = ? , RgtFlag = ? , AvaiFlag = ? , AvaliReason = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , AssigneeType = ? , AssigneeCode = ? , AssigneeName = ? , AssigneeSex = ? , AssigneePhone = ? , AssigneeAddr = ? , AssigneeZip = ? , PerCount = ? WHERE  RptNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRptNo() == null || this.get(i).getRptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRptNo());
			}
			if(this.get(i).getRgtClass() == null || this.get(i).getRgtClass().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtClass());
			}
			if(this.get(i).getRgtObj() == null || this.get(i).getRgtObj().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtObj());
			}
			if(this.get(i).getRgtObjNo() == null || this.get(i).getRgtObjNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtObjNo());
			}
			if(this.get(i).getRptMode() == null || this.get(i).getRptMode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRptMode());
			}
			if(this.get(i).getRelation() == null || this.get(i).getRelation().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRelation());
			}
			if(this.get(i).getRptorName() == null || this.get(i).getRptorName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRptorName());
			}
			if(this.get(i).getRptorAddress() == null || this.get(i).getRptorAddress().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRptorAddress());
			}
			if(this.get(i).getRptorPhone() == null || this.get(i).getRptorPhone().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRptorPhone());
			}
			if(this.get(i).getRptorMobile() == null || this.get(i).getRptorMobile().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRptorMobile());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getEmail());
			}
			if(this.get(i).getPostCode() == null || this.get(i).getPostCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPostCode());
			}
			if(this.get(i).getReturnMode() == null || this.get(i).getReturnMode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getReturnMode());
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAccidentSite());
			}
			if(this.get(i).getAccidentReason() == null || this.get(i).getAccidentReason().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAccidentReason());
			}
			if(this.get(i).getAccidentCourse() == null || this.get(i).getAccidentCourse().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAccidentCourse());
			}
			if(this.get(i).getRptDate() == null || this.get(i).getRptDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getRptDate()));
			}
			if(this.get(i).getCaseNoDate() == null || this.get(i).getCaseNoDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getCaseNoDate()));
			}
			if(this.get(i).getCaseEndDate() == null || this.get(i).getCaseEndDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getCaseEndDate()));
			}
			if(this.get(i).getRgtReason() == null || this.get(i).getRgtReason().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getRgtReason());
			}
			if(this.get(i).getNoRgtReason() == null || this.get(i).getNoRgtReason().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getNoRgtReason());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRemark());
			}
			if(this.get(i).getRgtFlag() == null || this.get(i).getRgtFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getRgtFlag());
			}
			if(this.get(i).getAvaiFlag() == null || this.get(i).getAvaiFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAvaiFlag());
			}
			if(this.get(i).getAvaliReason() == null || this.get(i).getAvaliReason().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAvaliReason());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getModifyTime());
			}
			if(this.get(i).getAssigneeType() == null || this.get(i).getAssigneeType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getAssigneeType());
			}
			if(this.get(i).getAssigneeCode() == null || this.get(i).getAssigneeCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getAssigneeCode());
			}
			if(this.get(i).getAssigneeName() == null || this.get(i).getAssigneeName().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getAssigneeName());
			}
			if(this.get(i).getAssigneeSex() == null || this.get(i).getAssigneeSex().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAssigneeSex());
			}
			if(this.get(i).getAssigneePhone() == null || this.get(i).getAssigneePhone().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAssigneePhone());
			}
			if(this.get(i).getAssigneeAddr() == null || this.get(i).getAssigneeAddr().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAssigneeAddr());
			}
			if(this.get(i).getAssigneeZip() == null || this.get(i).getAssigneeZip().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getAssigneeZip());
			}
			pstmt.setInt(40, this.get(i).getPerCount());
			// set where condition
			if(this.get(i).getRptNo() == null || this.get(i).getRptNo().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getRptNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpReport");
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
			tError.moduleName = "LLGrpReportDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLGrpReport(RptNo ,RgtClass ,RgtObj ,RgtObjNo ,RptMode ,Relation ,RptorName ,RptorAddress ,RptorPhone ,RptorMobile ,Email ,PostCode ,ReturnMode ,AccidentDate ,AccidentSite ,AccidentReason ,AccidentCourse ,RptDate ,CaseNoDate ,CaseEndDate ,RgtReason ,NoRgtReason ,Remark ,RgtFlag ,AvaiFlag ,AvaliReason ,MngCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,AssigneeType ,AssigneeCode ,AssigneeName ,AssigneeSex ,AssigneePhone ,AssigneeAddr ,AssigneeZip ,PerCount) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRptNo() == null || this.get(i).getRptNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRptNo());
			}
			if(this.get(i).getRgtClass() == null || this.get(i).getRgtClass().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtClass());
			}
			if(this.get(i).getRgtObj() == null || this.get(i).getRgtObj().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtObj());
			}
			if(this.get(i).getRgtObjNo() == null || this.get(i).getRgtObjNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtObjNo());
			}
			if(this.get(i).getRptMode() == null || this.get(i).getRptMode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRptMode());
			}
			if(this.get(i).getRelation() == null || this.get(i).getRelation().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRelation());
			}
			if(this.get(i).getRptorName() == null || this.get(i).getRptorName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRptorName());
			}
			if(this.get(i).getRptorAddress() == null || this.get(i).getRptorAddress().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRptorAddress());
			}
			if(this.get(i).getRptorPhone() == null || this.get(i).getRptorPhone().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRptorPhone());
			}
			if(this.get(i).getRptorMobile() == null || this.get(i).getRptorMobile().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRptorMobile());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getEmail());
			}
			if(this.get(i).getPostCode() == null || this.get(i).getPostCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPostCode());
			}
			if(this.get(i).getReturnMode() == null || this.get(i).getReturnMode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getReturnMode());
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAccidentSite());
			}
			if(this.get(i).getAccidentReason() == null || this.get(i).getAccidentReason().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAccidentReason());
			}
			if(this.get(i).getAccidentCourse() == null || this.get(i).getAccidentCourse().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAccidentCourse());
			}
			if(this.get(i).getRptDate() == null || this.get(i).getRptDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getRptDate()));
			}
			if(this.get(i).getCaseNoDate() == null || this.get(i).getCaseNoDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getCaseNoDate()));
			}
			if(this.get(i).getCaseEndDate() == null || this.get(i).getCaseEndDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getCaseEndDate()));
			}
			if(this.get(i).getRgtReason() == null || this.get(i).getRgtReason().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getRgtReason());
			}
			if(this.get(i).getNoRgtReason() == null || this.get(i).getNoRgtReason().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getNoRgtReason());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRemark());
			}
			if(this.get(i).getRgtFlag() == null || this.get(i).getRgtFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getRgtFlag());
			}
			if(this.get(i).getAvaiFlag() == null || this.get(i).getAvaiFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAvaiFlag());
			}
			if(this.get(i).getAvaliReason() == null || this.get(i).getAvaliReason().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAvaliReason());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getModifyTime());
			}
			if(this.get(i).getAssigneeType() == null || this.get(i).getAssigneeType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getAssigneeType());
			}
			if(this.get(i).getAssigneeCode() == null || this.get(i).getAssigneeCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getAssigneeCode());
			}
			if(this.get(i).getAssigneeName() == null || this.get(i).getAssigneeName().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getAssigneeName());
			}
			if(this.get(i).getAssigneeSex() == null || this.get(i).getAssigneeSex().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAssigneeSex());
			}
			if(this.get(i).getAssigneePhone() == null || this.get(i).getAssigneePhone().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getAssigneePhone());
			}
			if(this.get(i).getAssigneeAddr() == null || this.get(i).getAssigneeAddr().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAssigneeAddr());
			}
			if(this.get(i).getAssigneeZip() == null || this.get(i).getAssigneeZip().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getAssigneeZip());
			}
			pstmt.setInt(40, this.get(i).getPerCount());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpReport");
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
			tError.moduleName = "LLGrpReportDBSet";
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
