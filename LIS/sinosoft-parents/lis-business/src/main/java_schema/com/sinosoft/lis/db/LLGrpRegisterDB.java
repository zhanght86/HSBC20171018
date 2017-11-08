/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LLGrpRegisterSchema;
import com.sinosoft.lis.vschema.LLGrpRegisterSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLGrpRegisterDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGrpRegisterDB extends LLGrpRegisterSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public LLGrpRegisterDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLGrpRegister" );
		mflag = true;
	}

	public LLGrpRegisterDB()
	{
		con = null;
		db = new DBOper( "LLGrpRegister" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLGrpRegisterSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLGrpRegisterSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM LLGrpRegister WHERE  RgtNo = ?");
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRgtNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpRegister");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpRegister");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLGrpRegister SET  RgtNo = ? , RgtState = ? , RgtClass = ? , RgtObj = ? , RgtObjNo = ? , RgtType = ? , AgentCode = ? , AgentGroup = ? , ApplyerType = ? , IDType = ? , IDNo = ? , RgtantName = ? , RgtantSex = ? , Relation = ? , RgtantAddress = ? , RgtantPhone = ? , RgtantMobile = ? , Email = ? , PostCode = ? , CustomerNo = ? , GrpName = ? , RgtDate = ? , AccidentSite = ? , AccidentReason = ? , AccidentCourse = ? , AccStartDate = ? , AccidentDate = ? , RgtReason = ? , AppPeoples = ? , AppAmnt = ? , GetMode = ? , GetIntv = ? , CaseGetMode = ? , ReturnMode = ? , Remark = ? , Handler = ? , TogetherFlag = ? , RptFlag = ? , CalFlag = ? , UWFlag = ? , DeclineFlag = ? , EndCaseFlag = ? , EndCaseDate = ? , MngCom = ? , ClmState = ? , BankCode = ? , BankAccNo = ? , AccName = ? , Handler1 = ? , Handler1Phone = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , RgtConclusion = ? , NoRgtReason = ? , AssigneeType = ? , AssigneeCode = ? , AssigneeName = ? , AssigneeSex = ? , AssigneePhone = ? , AssigneeAddr = ? , AssigneeZip = ? , BeAdjSum = ? , FeeInputFlag = ? , Recipients = ? , ReciName = ? , ReciRela = ? , ReciAddress = ? , ReciDetails = ? , ReciPhone = ? , ReciMobile = ? , ReciZip = ? , ReciSex = ? , ReciEmail = ? , PerCount = ? , AppDate = ? , AcceptConfOperator = ? , AcceptConfDate = ? , AcceptOperator = ? , AcceptWorkdays = ? , PrintState = ? , UnAcceptPeoples = ? , ComCode = ? , ModifyOperator = ? WHERE  RgtNo = ?");
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRgtNo());
			}
			if(this.getRgtState() == null || this.getRgtState().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRgtState());
			}
			if(this.getRgtClass() == null || this.getRgtClass().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRgtClass());
			}
			if(this.getRgtObj() == null || this.getRgtObj().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRgtObj());
			}
			if(this.getRgtObjNo() == null || this.getRgtObjNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRgtObjNo());
			}
			if(this.getRgtType() == null || this.getRgtType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRgtType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentGroup());
			}
			if(this.getApplyerType() == null || this.getApplyerType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getApplyerType());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getIDNo());
			}
			if(this.getRgtantName() == null || this.getRgtantName().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getRgtantName());
			}
			if(this.getRgtantSex() == null || this.getRgtantSex().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getRgtantSex());
			}
			if(this.getRelation() == null || this.getRelation().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getRelation());
			}
			if(this.getRgtantAddress() == null || this.getRgtantAddress().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRgtantAddress());
			}
			if(this.getRgtantPhone() == null || this.getRgtantPhone().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getRgtantPhone());
			}
			if(this.getRgtantMobile() == null || this.getRgtantMobile().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getRgtantMobile());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getEmail());
			}
			if(this.getPostCode() == null || this.getPostCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPostCode());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getCustomerNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getGrpName());
			}
			if(this.getRgtDate() == null || this.getRgtDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getRgtDate()));
			}
			if(this.getAccidentSite() == null || this.getAccidentSite().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAccidentSite());
			}
			if(this.getAccidentReason() == null || this.getAccidentReason().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAccidentReason());
			}
			if(this.getAccidentCourse() == null || this.getAccidentCourse().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAccidentCourse());
			}
			if(this.getAccStartDate() == null || this.getAccStartDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getAccStartDate()));
			}
			if(this.getAccidentDate() == null || this.getAccidentDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getAccidentDate()));
			}
			if(this.getRgtReason() == null || this.getRgtReason().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getRgtReason());
			}
			pstmt.setInt(29, this.getAppPeoples());
			pstmt.setDouble(30, this.getAppAmnt());
			if(this.getGetMode() == null || this.getGetMode().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getGetMode());
			}
			pstmt.setInt(32, this.getGetIntv());
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getCaseGetMode());
			}
			if(this.getReturnMode() == null || this.getReturnMode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getReturnMode());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRemark());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getHandler());
			}
			if(this.getTogetherFlag() == null || this.getTogetherFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getTogetherFlag());
			}
			if(this.getRptFlag() == null || this.getRptFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getRptFlag());
			}
			if(this.getCalFlag() == null || this.getCalFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getCalFlag());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getUWFlag());
			}
			if(this.getDeclineFlag() == null || this.getDeclineFlag().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getDeclineFlag());
			}
			if(this.getEndCaseFlag() == null || this.getEndCaseFlag().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getEndCaseFlag());
			}
			if(this.getEndCaseDate() == null || this.getEndCaseDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getEndCaseDate()));
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getMngCom());
			}
			if(this.getClmState() == null || this.getClmState().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getClmState());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getAccName());
			}
			if(this.getHandler1() == null || this.getHandler1().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getHandler1());
			}
			if(this.getHandler1Phone() == null || this.getHandler1Phone().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getHandler1Phone());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(54, 91);
			} else {
				pstmt.setDate(54, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getModifyTime());
			}
			if(this.getRgtConclusion() == null || this.getRgtConclusion().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getRgtConclusion());
			}
			if(this.getNoRgtReason() == null || this.getNoRgtReason().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getNoRgtReason());
			}
			if(this.getAssigneeType() == null || this.getAssigneeType().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getAssigneeType());
			}
			if(this.getAssigneeCode() == null || this.getAssigneeCode().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAssigneeCode());
			}
			if(this.getAssigneeName() == null || this.getAssigneeName().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAssigneeName());
			}
			if(this.getAssigneeSex() == null || this.getAssigneeSex().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getAssigneeSex());
			}
			if(this.getAssigneePhone() == null || this.getAssigneePhone().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAssigneePhone());
			}
			if(this.getAssigneeAddr() == null || this.getAssigneeAddr().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getAssigneeAddr());
			}
			if(this.getAssigneeZip() == null || this.getAssigneeZip().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getAssigneeZip());
			}
			pstmt.setDouble(65, this.getBeAdjSum());
			if(this.getFeeInputFlag() == null || this.getFeeInputFlag().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getFeeInputFlag());
			}
			if(this.getRecipients() == null || this.getRecipients().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRecipients());
			}
			if(this.getReciName() == null || this.getReciName().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getReciName());
			}
			if(this.getReciRela() == null || this.getReciRela().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getReciRela());
			}
			if(this.getReciAddress() == null || this.getReciAddress().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getReciAddress());
			}
			if(this.getReciDetails() == null || this.getReciDetails().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getReciDetails());
			}
			if(this.getReciPhone() == null || this.getReciPhone().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getReciPhone());
			}
			if(this.getReciMobile() == null || this.getReciMobile().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getReciMobile());
			}
			if(this.getReciZip() == null || this.getReciZip().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getReciZip());
			}
			if(this.getReciSex() == null || this.getReciSex().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getReciSex());
			}
			if(this.getReciEmail() == null || this.getReciEmail().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getReciEmail());
			}
			pstmt.setInt(77, this.getPerCount());
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(78, 91);
			} else {
				pstmt.setDate(78, Date.valueOf(this.getAppDate()));
			}
			if(this.getAcceptConfOperator() == null || this.getAcceptConfOperator().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getAcceptConfOperator());
			}
			if(this.getAcceptConfDate() == null || this.getAcceptConfDate().equals("null")) {
				pstmt.setNull(80, 91);
			} else {
				pstmt.setDate(80, Date.valueOf(this.getAcceptConfDate()));
			}
			if(this.getAcceptOperator() == null || this.getAcceptOperator().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getAcceptOperator());
			}
			pstmt.setInt(82, this.getAcceptWorkdays());
			if(this.getPrintState() == null || this.getPrintState().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getPrintState());
			}
			pstmt.setInt(84, this.getUnAcceptPeoples());
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getModifyOperator());
			}
			// set where condition
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getRgtNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpRegister");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLGrpRegister VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRgtNo());
			}
			if(this.getRgtState() == null || this.getRgtState().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRgtState());
			}
			if(this.getRgtClass() == null || this.getRgtClass().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRgtClass());
			}
			if(this.getRgtObj() == null || this.getRgtObj().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRgtObj());
			}
			if(this.getRgtObjNo() == null || this.getRgtObjNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRgtObjNo());
			}
			if(this.getRgtType() == null || this.getRgtType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRgtType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentGroup());
			}
			if(this.getApplyerType() == null || this.getApplyerType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getApplyerType());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getIDNo());
			}
			if(this.getRgtantName() == null || this.getRgtantName().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getRgtantName());
			}
			if(this.getRgtantSex() == null || this.getRgtantSex().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getRgtantSex());
			}
			if(this.getRelation() == null || this.getRelation().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getRelation());
			}
			if(this.getRgtantAddress() == null || this.getRgtantAddress().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRgtantAddress());
			}
			if(this.getRgtantPhone() == null || this.getRgtantPhone().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getRgtantPhone());
			}
			if(this.getRgtantMobile() == null || this.getRgtantMobile().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getRgtantMobile());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getEmail());
			}
			if(this.getPostCode() == null || this.getPostCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPostCode());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getCustomerNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getGrpName());
			}
			if(this.getRgtDate() == null || this.getRgtDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getRgtDate()));
			}
			if(this.getAccidentSite() == null || this.getAccidentSite().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAccidentSite());
			}
			if(this.getAccidentReason() == null || this.getAccidentReason().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAccidentReason());
			}
			if(this.getAccidentCourse() == null || this.getAccidentCourse().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAccidentCourse());
			}
			if(this.getAccStartDate() == null || this.getAccStartDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getAccStartDate()));
			}
			if(this.getAccidentDate() == null || this.getAccidentDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getAccidentDate()));
			}
			if(this.getRgtReason() == null || this.getRgtReason().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getRgtReason());
			}
			pstmt.setInt(29, this.getAppPeoples());
			pstmt.setDouble(30, this.getAppAmnt());
			if(this.getGetMode() == null || this.getGetMode().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getGetMode());
			}
			pstmt.setInt(32, this.getGetIntv());
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getCaseGetMode());
			}
			if(this.getReturnMode() == null || this.getReturnMode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getReturnMode());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRemark());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getHandler());
			}
			if(this.getTogetherFlag() == null || this.getTogetherFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getTogetherFlag());
			}
			if(this.getRptFlag() == null || this.getRptFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getRptFlag());
			}
			if(this.getCalFlag() == null || this.getCalFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getCalFlag());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getUWFlag());
			}
			if(this.getDeclineFlag() == null || this.getDeclineFlag().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getDeclineFlag());
			}
			if(this.getEndCaseFlag() == null || this.getEndCaseFlag().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getEndCaseFlag());
			}
			if(this.getEndCaseDate() == null || this.getEndCaseDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getEndCaseDate()));
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getMngCom());
			}
			if(this.getClmState() == null || this.getClmState().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getClmState());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getAccName());
			}
			if(this.getHandler1() == null || this.getHandler1().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getHandler1());
			}
			if(this.getHandler1Phone() == null || this.getHandler1Phone().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getHandler1Phone());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(54, 91);
			} else {
				pstmt.setDate(54, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getModifyTime());
			}
			if(this.getRgtConclusion() == null || this.getRgtConclusion().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getRgtConclusion());
			}
			if(this.getNoRgtReason() == null || this.getNoRgtReason().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getNoRgtReason());
			}
			if(this.getAssigneeType() == null || this.getAssigneeType().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getAssigneeType());
			}
			if(this.getAssigneeCode() == null || this.getAssigneeCode().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAssigneeCode());
			}
			if(this.getAssigneeName() == null || this.getAssigneeName().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAssigneeName());
			}
			if(this.getAssigneeSex() == null || this.getAssigneeSex().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getAssigneeSex());
			}
			if(this.getAssigneePhone() == null || this.getAssigneePhone().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAssigneePhone());
			}
			if(this.getAssigneeAddr() == null || this.getAssigneeAddr().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getAssigneeAddr());
			}
			if(this.getAssigneeZip() == null || this.getAssigneeZip().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getAssigneeZip());
			}
			pstmt.setDouble(65, this.getBeAdjSum());
			if(this.getFeeInputFlag() == null || this.getFeeInputFlag().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getFeeInputFlag());
			}
			if(this.getRecipients() == null || this.getRecipients().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRecipients());
			}
			if(this.getReciName() == null || this.getReciName().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getReciName());
			}
			if(this.getReciRela() == null || this.getReciRela().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getReciRela());
			}
			if(this.getReciAddress() == null || this.getReciAddress().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getReciAddress());
			}
			if(this.getReciDetails() == null || this.getReciDetails().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getReciDetails());
			}
			if(this.getReciPhone() == null || this.getReciPhone().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getReciPhone());
			}
			if(this.getReciMobile() == null || this.getReciMobile().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getReciMobile());
			}
			if(this.getReciZip() == null || this.getReciZip().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getReciZip());
			}
			if(this.getReciSex() == null || this.getReciSex().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getReciSex());
			}
			if(this.getReciEmail() == null || this.getReciEmail().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getReciEmail());
			}
			pstmt.setInt(77, this.getPerCount());
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(78, 91);
			} else {
				pstmt.setDate(78, Date.valueOf(this.getAppDate()));
			}
			if(this.getAcceptConfOperator() == null || this.getAcceptConfOperator().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getAcceptConfOperator());
			}
			if(this.getAcceptConfDate() == null || this.getAcceptConfDate().equals("null")) {
				pstmt.setNull(80, 91);
			} else {
				pstmt.setDate(80, Date.valueOf(this.getAcceptConfDate()));
			}
			if(this.getAcceptOperator() == null || this.getAcceptOperator().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getAcceptOperator());
			}
			pstmt.setInt(82, this.getAcceptWorkdays());
			if(this.getPrintState() == null || this.getPrintState().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getPrintState());
			}
			pstmt.setInt(84, this.getUnAcceptPeoples());
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM LLGrpRegister WHERE  RgtNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRgtNo());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpRegisterDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public LLGrpRegisterSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGrpRegisterSet aLLGrpRegisterSet = new LLGrpRegisterSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLGrpRegister");
			LLGrpRegisterSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LLGrpRegisterSchema s1 = new LLGrpRegisterSchema();
				s1.setSchema(rs,i);
				aLLGrpRegisterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLLGrpRegisterSet;
	}

	public LLGrpRegisterSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLGrpRegisterSet aLLGrpRegisterSet = new LLGrpRegisterSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LLGrpRegisterSchema s1 = new LLGrpRegisterSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpRegisterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpRegisterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLLGrpRegisterSet;
	}

	public LLGrpRegisterSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGrpRegisterSet aLLGrpRegisterSet = new LLGrpRegisterSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLGrpRegister");
			LLGrpRegisterSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LLGrpRegisterSchema s1 = new LLGrpRegisterSchema();
				s1.setSchema(rs,i);
				aLLGrpRegisterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLLGrpRegisterSet;
	}

	public LLGrpRegisterSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLGrpRegisterSet aLLGrpRegisterSet = new LLGrpRegisterSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LLGrpRegisterSchema s1 = new LLGrpRegisterSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpRegisterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpRegisterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLLGrpRegisterSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LLGrpRegister");
			LLGrpRegisterSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLGrpRegister " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLGrpRegisterDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return LLGrpRegisterSet
 */
public LLGrpRegisterSet getData()
{
    int tCount = 0;
    LLGrpRegisterSet tLLGrpRegisterSet = new LLGrpRegisterSet();
    LLGrpRegisterSchema tLLGrpRegisterSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLGrpRegisterSchema = new LLGrpRegisterSchema();
        tLLGrpRegisterSchema.setSchema(mResultSet, 1);
        tLLGrpRegisterSet.add(tLLGrpRegisterSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLGrpRegisterSchema = new LLGrpRegisterSchema();
                tLLGrpRegisterSchema.setSchema(mResultSet, 1);
                tLLGrpRegisterSet.add(tLLGrpRegisterSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tLLGrpRegisterSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LLGrpRegisterDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "LLGrpRegisterDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpRegisterDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLGrpRegisterSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGrpRegisterSet aLLGrpRegisterSet = new LLGrpRegisterSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LLGrpRegisterSchema s1 = new LLGrpRegisterSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpRegisterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpRegisterSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLLGrpRegisterSet;
	}

	public LLGrpRegisterSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGrpRegisterSet aLLGrpRegisterSet = new LLGrpRegisterSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LLGrpRegisterSchema s1 = new LLGrpRegisterSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpRegisterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpRegisterSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpRegisterDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLLGrpRegisterSet; 
	}

}
