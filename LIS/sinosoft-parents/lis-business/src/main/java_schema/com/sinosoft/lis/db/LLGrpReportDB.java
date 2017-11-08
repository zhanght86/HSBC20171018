/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLGrpReportSchema;
import com.sinosoft.lis.vschema.LLGrpReportSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLGrpReportDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGrpReportDB extends LLGrpReportSchema
{
private static Logger logger = Logger.getLogger(LLGrpReportDB.class);

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
	public LLGrpReportDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLGrpReport" );
		mflag = true;
	}

	public LLGrpReportDB()
	{
		con = null;
		db = new DBOper( "LLGrpReport" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLGrpReportSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLGrpReportSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLGrpReport WHERE  RptNo = ?");
			if(this.getRptNo() == null || this.getRptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRptNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGrpReport");
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
		SQLString sqlObj = new SQLString("LLGrpReport");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLGrpReport SET  RptNo = ? , RgtClass = ? , RgtObj = ? , RgtObjNo = ? , RptMode = ? , Relation = ? , RptorName = ? , RptorAddress = ? , RptorPhone = ? , RptorMobile = ? , Email = ? , PostCode = ? , ReturnMode = ? , AccidentDate = ? , AccidentSite = ? , AccidentReason = ? , AccidentCourse = ? , RptDate = ? , CaseNoDate = ? , CaseEndDate = ? , RgtReason = ? , NoRgtReason = ? , Remark = ? , RgtFlag = ? , AvaiFlag = ? , AvaliReason = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , AssigneeType = ? , AssigneeCode = ? , AssigneeName = ? , AssigneeSex = ? , AssigneePhone = ? , AssigneeAddr = ? , AssigneeZip = ? , PerCount = ? WHERE  RptNo = ?");
			if(this.getRptNo() == null || this.getRptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRptNo());
			}
			if(this.getRgtClass() == null || this.getRgtClass().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRgtClass());
			}
			if(this.getRgtObj() == null || this.getRgtObj().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRgtObj());
			}
			if(this.getRgtObjNo() == null || this.getRgtObjNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRgtObjNo());
			}
			if(this.getRptMode() == null || this.getRptMode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRptMode());
			}
			if(this.getRelation() == null || this.getRelation().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRelation());
			}
			if(this.getRptorName() == null || this.getRptorName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRptorName());
			}
			if(this.getRptorAddress() == null || this.getRptorAddress().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getRptorAddress());
			}
			if(this.getRptorPhone() == null || this.getRptorPhone().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRptorPhone());
			}
			if(this.getRptorMobile() == null || this.getRptorMobile().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRptorMobile());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getEmail());
			}
			if(this.getPostCode() == null || this.getPostCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPostCode());
			}
			if(this.getReturnMode() == null || this.getReturnMode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getReturnMode());
			}
			if(this.getAccidentDate() == null || this.getAccidentDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getAccidentDate()));
			}
			if(this.getAccidentSite() == null || this.getAccidentSite().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAccidentSite());
			}
			if(this.getAccidentReason() == null || this.getAccidentReason().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAccidentReason());
			}
			if(this.getAccidentCourse() == null || this.getAccidentCourse().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAccidentCourse());
			}
			if(this.getRptDate() == null || this.getRptDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getRptDate()));
			}
			if(this.getCaseNoDate() == null || this.getCaseNoDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getCaseNoDate()));
			}
			if(this.getCaseEndDate() == null || this.getCaseEndDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getCaseEndDate()));
			}
			if(this.getRgtReason() == null || this.getRgtReason().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getRgtReason());
			}
			if(this.getNoRgtReason() == null || this.getNoRgtReason().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getNoRgtReason());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRemark());
			}
			if(this.getRgtFlag() == null || this.getRgtFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getRgtFlag());
			}
			if(this.getAvaiFlag() == null || this.getAvaiFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAvaiFlag());
			}
			if(this.getAvaliReason() == null || this.getAvaliReason().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAvaliReason());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getModifyTime());
			}
			if(this.getAssigneeType() == null || this.getAssigneeType().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getAssigneeType());
			}
			if(this.getAssigneeCode() == null || this.getAssigneeCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getAssigneeCode());
			}
			if(this.getAssigneeName() == null || this.getAssigneeName().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getAssigneeName());
			}
			if(this.getAssigneeSex() == null || this.getAssigneeSex().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAssigneeSex());
			}
			if(this.getAssigneePhone() == null || this.getAssigneePhone().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getAssigneePhone());
			}
			if(this.getAssigneeAddr() == null || this.getAssigneeAddr().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getAssigneeAddr());
			}
			if(this.getAssigneeZip() == null || this.getAssigneeZip().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getAssigneeZip());
			}
			pstmt.setInt(40, this.getPerCount());
			// set where condition
			if(this.getRptNo() == null || this.getRptNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getRptNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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
		SQLString sqlObj = new SQLString("LLGrpReport");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLGrpReport(RptNo ,RgtClass ,RgtObj ,RgtObjNo ,RptMode ,Relation ,RptorName ,RptorAddress ,RptorPhone ,RptorMobile ,Email ,PostCode ,ReturnMode ,AccidentDate ,AccidentSite ,AccidentReason ,AccidentCourse ,RptDate ,CaseNoDate ,CaseEndDate ,RgtReason ,NoRgtReason ,Remark ,RgtFlag ,AvaiFlag ,AvaliReason ,MngCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,AssigneeType ,AssigneeCode ,AssigneeName ,AssigneeSex ,AssigneePhone ,AssigneeAddr ,AssigneeZip ,PerCount) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRptNo() == null || this.getRptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRptNo());
			}
			if(this.getRgtClass() == null || this.getRgtClass().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRgtClass());
			}
			if(this.getRgtObj() == null || this.getRgtObj().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRgtObj());
			}
			if(this.getRgtObjNo() == null || this.getRgtObjNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getRgtObjNo());
			}
			if(this.getRptMode() == null || this.getRptMode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRptMode());
			}
			if(this.getRelation() == null || this.getRelation().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRelation());
			}
			if(this.getRptorName() == null || this.getRptorName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRptorName());
			}
			if(this.getRptorAddress() == null || this.getRptorAddress().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getRptorAddress());
			}
			if(this.getRptorPhone() == null || this.getRptorPhone().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRptorPhone());
			}
			if(this.getRptorMobile() == null || this.getRptorMobile().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRptorMobile());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getEmail());
			}
			if(this.getPostCode() == null || this.getPostCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPostCode());
			}
			if(this.getReturnMode() == null || this.getReturnMode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getReturnMode());
			}
			if(this.getAccidentDate() == null || this.getAccidentDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getAccidentDate()));
			}
			if(this.getAccidentSite() == null || this.getAccidentSite().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAccidentSite());
			}
			if(this.getAccidentReason() == null || this.getAccidentReason().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAccidentReason());
			}
			if(this.getAccidentCourse() == null || this.getAccidentCourse().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAccidentCourse());
			}
			if(this.getRptDate() == null || this.getRptDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getRptDate()));
			}
			if(this.getCaseNoDate() == null || this.getCaseNoDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getCaseNoDate()));
			}
			if(this.getCaseEndDate() == null || this.getCaseEndDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getCaseEndDate()));
			}
			if(this.getRgtReason() == null || this.getRgtReason().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getRgtReason());
			}
			if(this.getNoRgtReason() == null || this.getNoRgtReason().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getNoRgtReason());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getRemark());
			}
			if(this.getRgtFlag() == null || this.getRgtFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getRgtFlag());
			}
			if(this.getAvaiFlag() == null || this.getAvaiFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAvaiFlag());
			}
			if(this.getAvaliReason() == null || this.getAvaliReason().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAvaliReason());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getModifyTime());
			}
			if(this.getAssigneeType() == null || this.getAssigneeType().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getAssigneeType());
			}
			if(this.getAssigneeCode() == null || this.getAssigneeCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getAssigneeCode());
			}
			if(this.getAssigneeName() == null || this.getAssigneeName().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getAssigneeName());
			}
			if(this.getAssigneeSex() == null || this.getAssigneeSex().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAssigneeSex());
			}
			if(this.getAssigneePhone() == null || this.getAssigneePhone().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getAssigneePhone());
			}
			if(this.getAssigneeAddr() == null || this.getAssigneeAddr().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getAssigneeAddr());
			}
			if(this.getAssigneeZip() == null || this.getAssigneeZip().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getAssigneeZip());
			}
			pstmt.setInt(40, this.getPerCount());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLGrpReport WHERE  RptNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRptNo() == null || this.getRptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRptNo());
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
					tError.moduleName = "LLGrpReportDB";
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
			tError.moduleName = "LLGrpReportDB";
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

	public LLGrpReportSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLGrpReportSet aLLGrpReportSet = new LLGrpReportSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLGrpReport");
			LLGrpReportSchema aSchema = this.getSchema();
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
				LLGrpReportSchema s1 = new LLGrpReportSchema();
				s1.setSchema(rs,i);
				aLLGrpReportSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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

		return aLLGrpReportSet;

	}

	public LLGrpReportSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLGrpReportSet aLLGrpReportSet = new LLGrpReportSet();

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
				LLGrpReportSchema s1 = new LLGrpReportSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpReportDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpReportSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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

		return aLLGrpReportSet;
	}

	public LLGrpReportSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLGrpReportSet aLLGrpReportSet = new LLGrpReportSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLGrpReport");
			LLGrpReportSchema aSchema = this.getSchema();
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

				LLGrpReportSchema s1 = new LLGrpReportSchema();
				s1.setSchema(rs,i);
				aLLGrpReportSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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

		return aLLGrpReportSet;

	}

	public LLGrpReportSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLGrpReportSet aLLGrpReportSet = new LLGrpReportSet();

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

				LLGrpReportSchema s1 = new LLGrpReportSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpReportDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpReportSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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

		return aLLGrpReportSet;
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
			SQLString sqlObj = new SQLString("LLGrpReport");
			LLGrpReportSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLGrpReport " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLGrpReportDB";
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
			tError.moduleName = "LLGrpReportDB";
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
        tError.moduleName = "LLGrpReportDB";
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
        tError.moduleName = "LLGrpReportDB";
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
        tError.moduleName = "LLGrpReportDB";
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
        tError.moduleName = "LLGrpReportDB";
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
 * @return LLGrpReportSet
 */
public LLGrpReportSet getData()
{
    int tCount = 0;
    LLGrpReportSet tLLGrpReportSet = new LLGrpReportSet();
    LLGrpReportSchema tLLGrpReportSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpReportDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLGrpReportSchema = new LLGrpReportSchema();
        tLLGrpReportSchema.setSchema(mResultSet, 1);
        tLLGrpReportSet.add(tLLGrpReportSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLGrpReportSchema = new LLGrpReportSchema();
                tLLGrpReportSchema.setSchema(mResultSet, 1);
                tLLGrpReportSet.add(tLLGrpReportSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLGrpReportDB";
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
    return tLLGrpReportSet;
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
            tError.moduleName = "LLGrpReportDB";
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
        tError.moduleName = "LLGrpReportDB";
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
            tError.moduleName = "LLGrpReportDB";
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
        tError.moduleName = "LLGrpReportDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLGrpReportSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGrpReportSet aLLGrpReportSet = new LLGrpReportSet();

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
				LLGrpReportSchema s1 = new LLGrpReportSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpReportDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpReportSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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

		return aLLGrpReportSet;
	}

	public LLGrpReportSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGrpReportSet aLLGrpReportSet = new LLGrpReportSet();

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

				LLGrpReportSchema s1 = new LLGrpReportSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGrpReportDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGrpReportSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGrpReportDB";
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

		return aLLGrpReportSet; 
	}

}
