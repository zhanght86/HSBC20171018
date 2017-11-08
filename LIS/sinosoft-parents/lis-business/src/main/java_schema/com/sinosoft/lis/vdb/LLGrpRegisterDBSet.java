/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLGrpRegisterSchema;
import com.sinosoft.lis.vschema.LLGrpRegisterSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLGrpRegisterDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGrpRegisterDBSet extends LLGrpRegisterSet
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
	public LLGrpRegisterDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLGrpRegister");
		mflag = true;
	}

	public LLGrpRegisterDBSet()
	{
		db = new DBOper( "LLGrpRegister" );
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
			tError.moduleName = "LLGrpRegisterDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLGrpRegister WHERE  RgtNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRgtNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpRegister");
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
			tError.moduleName = "LLGrpRegisterDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLGrpRegister SET  RgtNo = ? , RgtState = ? , RgtClass = ? , RgtObj = ? , RgtObjNo = ? , RgtType = ? , AgentCode = ? , AgentGroup = ? , ApplyerType = ? , IDType = ? , IDNo = ? , RgtantName = ? , RgtantSex = ? , Relation = ? , RgtantAddress = ? , RgtantPhone = ? , RgtantMobile = ? , Email = ? , PostCode = ? , CustomerNo = ? , GrpName = ? , RgtDate = ? , AccidentSite = ? , AccidentReason = ? , AccidentCourse = ? , AccStartDate = ? , AccidentDate = ? , RgtReason = ? , AppPeoples = ? , AppAmnt = ? , GetMode = ? , GetIntv = ? , CaseGetMode = ? , ReturnMode = ? , Remark = ? , Handler = ? , TogetherFlag = ? , RptFlag = ? , CalFlag = ? , UWFlag = ? , DeclineFlag = ? , EndCaseFlag = ? , EndCaseDate = ? , MngCom = ? , ClmState = ? , BankCode = ? , BankAccNo = ? , AccName = ? , Handler1 = ? , Handler1Phone = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , RgtConclusion = ? , NoRgtReason = ? , AssigneeType = ? , AssigneeCode = ? , AssigneeName = ? , AssigneeSex = ? , AssigneePhone = ? , AssigneeAddr = ? , AssigneeZip = ? , BeAdjSum = ? , FeeInputFlag = ? , Recipients = ? , ReciName = ? , ReciRela = ? , ReciAddress = ? , ReciDetails = ? , ReciPhone = ? , ReciMobile = ? , ReciZip = ? , ReciSex = ? , ReciEmail = ? , PerCount = ? , AppDate = ? , AcceptConfOperator = ? , AcceptConfDate = ? , AcceptOperator = ? , AcceptWorkdays = ? , PrintState = ? , UnAcceptPeoples = ? , ComCode = ? , ModifyOperator = ? WHERE  RgtNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRgtNo());
			}
			if(this.get(i).getRgtState() == null || this.get(i).getRgtState().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtState());
			}
			if(this.get(i).getRgtClass() == null || this.get(i).getRgtClass().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtClass());
			}
			if(this.get(i).getRgtObj() == null || this.get(i).getRgtObj().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtObj());
			}
			if(this.get(i).getRgtObjNo() == null || this.get(i).getRgtObjNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRgtObjNo());
			}
			if(this.get(i).getRgtType() == null || this.get(i).getRgtType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRgtType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAgentGroup());
			}
			if(this.get(i).getApplyerType() == null || this.get(i).getApplyerType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getApplyerType());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIDNo());
			}
			if(this.get(i).getRgtantName() == null || this.get(i).getRgtantName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRgtantName());
			}
			if(this.get(i).getRgtantSex() == null || this.get(i).getRgtantSex().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRgtantSex());
			}
			if(this.get(i).getRelation() == null || this.get(i).getRelation().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRelation());
			}
			if(this.get(i).getRgtantAddress() == null || this.get(i).getRgtantAddress().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRgtantAddress());
			}
			if(this.get(i).getRgtantPhone() == null || this.get(i).getRgtantPhone().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getRgtantPhone());
			}
			if(this.get(i).getRgtantMobile() == null || this.get(i).getRgtantMobile().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRgtantMobile());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEmail());
			}
			if(this.get(i).getPostCode() == null || this.get(i).getPostCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPostCode());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCustomerNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGrpName());
			}
			if(this.get(i).getRgtDate() == null || this.get(i).getRgtDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getRgtDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAccidentSite());
			}
			if(this.get(i).getAccidentReason() == null || this.get(i).getAccidentReason().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAccidentReason());
			}
			if(this.get(i).getAccidentCourse() == null || this.get(i).getAccidentCourse().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAccidentCourse());
			}
			if(this.get(i).getAccStartDate() == null || this.get(i).getAccStartDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getAccStartDate()));
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getRgtReason() == null || this.get(i).getRgtReason().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getRgtReason());
			}
			pstmt.setInt(29, this.get(i).getAppPeoples());
			pstmt.setDouble(30, this.get(i).getAppAmnt());
			if(this.get(i).getGetMode() == null || this.get(i).getGetMode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getGetMode());
			}
			pstmt.setInt(32, this.get(i).getGetIntv());
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getReturnMode() == null || this.get(i).getReturnMode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getReturnMode());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getRemark());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getHandler());
			}
			if(this.get(i).getTogetherFlag() == null || this.get(i).getTogetherFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getTogetherFlag());
			}
			if(this.get(i).getRptFlag() == null || this.get(i).getRptFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getRptFlag());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getCalFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getUWFlag());
			}
			if(this.get(i).getDeclineFlag() == null || this.get(i).getDeclineFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getDeclineFlag());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMngCom());
			}
			if(this.get(i).getClmState() == null || this.get(i).getClmState().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getClmState());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getAccName());
			}
			if(this.get(i).getHandler1() == null || this.get(i).getHandler1().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getHandler1());
			}
			if(this.get(i).getHandler1Phone() == null || this.get(i).getHandler1Phone().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getHandler1Phone());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(54,null);
			} else {
				pstmt.setDate(54, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getModifyTime());
			}
			if(this.get(i).getRgtConclusion() == null || this.get(i).getRgtConclusion().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getRgtConclusion());
			}
			if(this.get(i).getNoRgtReason() == null || this.get(i).getNoRgtReason().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getNoRgtReason());
			}
			if(this.get(i).getAssigneeType() == null || this.get(i).getAssigneeType().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getAssigneeType());
			}
			if(this.get(i).getAssigneeCode() == null || this.get(i).getAssigneeCode().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAssigneeCode());
			}
			if(this.get(i).getAssigneeName() == null || this.get(i).getAssigneeName().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getAssigneeName());
			}
			if(this.get(i).getAssigneeSex() == null || this.get(i).getAssigneeSex().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getAssigneeSex());
			}
			if(this.get(i).getAssigneePhone() == null || this.get(i).getAssigneePhone().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getAssigneePhone());
			}
			if(this.get(i).getAssigneeAddr() == null || this.get(i).getAssigneeAddr().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAssigneeAddr());
			}
			if(this.get(i).getAssigneeZip() == null || this.get(i).getAssigneeZip().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getAssigneeZip());
			}
			pstmt.setDouble(65, this.get(i).getBeAdjSum());
			if(this.get(i).getFeeInputFlag() == null || this.get(i).getFeeInputFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getFeeInputFlag());
			}
			if(this.get(i).getRecipients() == null || this.get(i).getRecipients().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRecipients());
			}
			if(this.get(i).getReciName() == null || this.get(i).getReciName().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getReciName());
			}
			if(this.get(i).getReciRela() == null || this.get(i).getReciRela().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getReciRela());
			}
			if(this.get(i).getReciAddress() == null || this.get(i).getReciAddress().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getReciAddress());
			}
			if(this.get(i).getReciDetails() == null || this.get(i).getReciDetails().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getReciDetails());
			}
			if(this.get(i).getReciPhone() == null || this.get(i).getReciPhone().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getReciPhone());
			}
			if(this.get(i).getReciMobile() == null || this.get(i).getReciMobile().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getReciMobile());
			}
			if(this.get(i).getReciZip() == null || this.get(i).getReciZip().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getReciZip());
			}
			if(this.get(i).getReciSex() == null || this.get(i).getReciSex().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getReciSex());
			}
			if(this.get(i).getReciEmail() == null || this.get(i).getReciEmail().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getReciEmail());
			}
			pstmt.setInt(77, this.get(i).getPerCount());
			if(this.get(i).getAppDate() == null || this.get(i).getAppDate().equals("null")) {
				pstmt.setDate(78,null);
			} else {
				pstmt.setDate(78, Date.valueOf(this.get(i).getAppDate()));
			}
			if(this.get(i).getAcceptConfOperator() == null || this.get(i).getAcceptConfOperator().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getAcceptConfOperator());
			}
			if(this.get(i).getAcceptConfDate() == null || this.get(i).getAcceptConfDate().equals("null")) {
				pstmt.setDate(80,null);
			} else {
				pstmt.setDate(80, Date.valueOf(this.get(i).getAcceptConfDate()));
			}
			if(this.get(i).getAcceptOperator() == null || this.get(i).getAcceptOperator().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getAcceptOperator());
			}
			pstmt.setInt(82, this.get(i).getAcceptWorkdays());
			if(this.get(i).getPrintState() == null || this.get(i).getPrintState().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getPrintState());
			}
			pstmt.setInt(84, this.get(i).getUnAcceptPeoples());
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getRgtNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpRegister");
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
			tError.moduleName = "LLGrpRegisterDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLGrpRegister VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRgtNo());
			}
			if(this.get(i).getRgtState() == null || this.get(i).getRgtState().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtState());
			}
			if(this.get(i).getRgtClass() == null || this.get(i).getRgtClass().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRgtClass());
			}
			if(this.get(i).getRgtObj() == null || this.get(i).getRgtObj().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRgtObj());
			}
			if(this.get(i).getRgtObjNo() == null || this.get(i).getRgtObjNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRgtObjNo());
			}
			if(this.get(i).getRgtType() == null || this.get(i).getRgtType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRgtType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAgentGroup());
			}
			if(this.get(i).getApplyerType() == null || this.get(i).getApplyerType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getApplyerType());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIDNo());
			}
			if(this.get(i).getRgtantName() == null || this.get(i).getRgtantName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRgtantName());
			}
			if(this.get(i).getRgtantSex() == null || this.get(i).getRgtantSex().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRgtantSex());
			}
			if(this.get(i).getRelation() == null || this.get(i).getRelation().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRelation());
			}
			if(this.get(i).getRgtantAddress() == null || this.get(i).getRgtantAddress().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRgtantAddress());
			}
			if(this.get(i).getRgtantPhone() == null || this.get(i).getRgtantPhone().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getRgtantPhone());
			}
			if(this.get(i).getRgtantMobile() == null || this.get(i).getRgtantMobile().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRgtantMobile());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEmail());
			}
			if(this.get(i).getPostCode() == null || this.get(i).getPostCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPostCode());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCustomerNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGrpName());
			}
			if(this.get(i).getRgtDate() == null || this.get(i).getRgtDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getRgtDate()));
			}
			if(this.get(i).getAccidentSite() == null || this.get(i).getAccidentSite().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAccidentSite());
			}
			if(this.get(i).getAccidentReason() == null || this.get(i).getAccidentReason().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAccidentReason());
			}
			if(this.get(i).getAccidentCourse() == null || this.get(i).getAccidentCourse().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAccidentCourse());
			}
			if(this.get(i).getAccStartDate() == null || this.get(i).getAccStartDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getAccStartDate()));
			}
			if(this.get(i).getAccidentDate() == null || this.get(i).getAccidentDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getAccidentDate()));
			}
			if(this.get(i).getRgtReason() == null || this.get(i).getRgtReason().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getRgtReason());
			}
			pstmt.setInt(29, this.get(i).getAppPeoples());
			pstmt.setDouble(30, this.get(i).getAppAmnt());
			if(this.get(i).getGetMode() == null || this.get(i).getGetMode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getGetMode());
			}
			pstmt.setInt(32, this.get(i).getGetIntv());
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getReturnMode() == null || this.get(i).getReturnMode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getReturnMode());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getRemark());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getHandler());
			}
			if(this.get(i).getTogetherFlag() == null || this.get(i).getTogetherFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getTogetherFlag());
			}
			if(this.get(i).getRptFlag() == null || this.get(i).getRptFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getRptFlag());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getCalFlag());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getUWFlag());
			}
			if(this.get(i).getDeclineFlag() == null || this.get(i).getDeclineFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getDeclineFlag());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMngCom());
			}
			if(this.get(i).getClmState() == null || this.get(i).getClmState().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getClmState());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getAccName());
			}
			if(this.get(i).getHandler1() == null || this.get(i).getHandler1().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getHandler1());
			}
			if(this.get(i).getHandler1Phone() == null || this.get(i).getHandler1Phone().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getHandler1Phone());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(54,null);
			} else {
				pstmt.setDate(54, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getModifyTime());
			}
			if(this.get(i).getRgtConclusion() == null || this.get(i).getRgtConclusion().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getRgtConclusion());
			}
			if(this.get(i).getNoRgtReason() == null || this.get(i).getNoRgtReason().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getNoRgtReason());
			}
			if(this.get(i).getAssigneeType() == null || this.get(i).getAssigneeType().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getAssigneeType());
			}
			if(this.get(i).getAssigneeCode() == null || this.get(i).getAssigneeCode().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAssigneeCode());
			}
			if(this.get(i).getAssigneeName() == null || this.get(i).getAssigneeName().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getAssigneeName());
			}
			if(this.get(i).getAssigneeSex() == null || this.get(i).getAssigneeSex().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getAssigneeSex());
			}
			if(this.get(i).getAssigneePhone() == null || this.get(i).getAssigneePhone().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getAssigneePhone());
			}
			if(this.get(i).getAssigneeAddr() == null || this.get(i).getAssigneeAddr().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAssigneeAddr());
			}
			if(this.get(i).getAssigneeZip() == null || this.get(i).getAssigneeZip().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getAssigneeZip());
			}
			pstmt.setDouble(65, this.get(i).getBeAdjSum());
			if(this.get(i).getFeeInputFlag() == null || this.get(i).getFeeInputFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getFeeInputFlag());
			}
			if(this.get(i).getRecipients() == null || this.get(i).getRecipients().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRecipients());
			}
			if(this.get(i).getReciName() == null || this.get(i).getReciName().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getReciName());
			}
			if(this.get(i).getReciRela() == null || this.get(i).getReciRela().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getReciRela());
			}
			if(this.get(i).getReciAddress() == null || this.get(i).getReciAddress().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getReciAddress());
			}
			if(this.get(i).getReciDetails() == null || this.get(i).getReciDetails().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getReciDetails());
			}
			if(this.get(i).getReciPhone() == null || this.get(i).getReciPhone().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getReciPhone());
			}
			if(this.get(i).getReciMobile() == null || this.get(i).getReciMobile().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getReciMobile());
			}
			if(this.get(i).getReciZip() == null || this.get(i).getReciZip().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getReciZip());
			}
			if(this.get(i).getReciSex() == null || this.get(i).getReciSex().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getReciSex());
			}
			if(this.get(i).getReciEmail() == null || this.get(i).getReciEmail().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getReciEmail());
			}
			pstmt.setInt(77, this.get(i).getPerCount());
			if(this.get(i).getAppDate() == null || this.get(i).getAppDate().equals("null")) {
				pstmt.setDate(78,null);
			} else {
				pstmt.setDate(78, Date.valueOf(this.get(i).getAppDate()));
			}
			if(this.get(i).getAcceptConfOperator() == null || this.get(i).getAcceptConfOperator().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getAcceptConfOperator());
			}
			if(this.get(i).getAcceptConfDate() == null || this.get(i).getAcceptConfDate().equals("null")) {
				pstmt.setDate(80,null);
			} else {
				pstmt.setDate(80, Date.valueOf(this.get(i).getAcceptConfDate()));
			}
			if(this.get(i).getAcceptOperator() == null || this.get(i).getAcceptOperator().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getAcceptOperator());
			}
			pstmt.setInt(82, this.get(i).getAcceptWorkdays());
			if(this.get(i).getPrintState() == null || this.get(i).getPrintState().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getPrintState());
			}
			pstmt.setInt(84, this.get(i).getUnAcceptPeoples());
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpRegister");
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
			tError.moduleName = "LLGrpRegisterDBSet";
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
