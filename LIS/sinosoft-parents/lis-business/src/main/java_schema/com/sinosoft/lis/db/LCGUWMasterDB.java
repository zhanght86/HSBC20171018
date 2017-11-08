/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGUWMasterDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCGUWMasterDB extends LCGUWMasterSchema
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
	public LCGUWMasterDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCGUWMaster" );
		mflag = true;
	}

	public LCGUWMasterDB()
	{
		con = null;
		db = new DBOper( "LCGUWMaster" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCGUWMasterSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCGUWMasterSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCGUWMaster WHERE  GrpProposalNo = ?");
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpProposalNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGUWMaster");
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
		SQLString sqlObj = new SQLString("LCGUWMaster");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCGUWMaster SET  GrpContNo = ? , ProposalGrpContNo = ? , GrpPolNo = ? , GrpProposalNo = ? , UWNo = ? , InsuredNo = ? , InsuredName = ? , AppntNo = ? , AppntName = ? , AgentCode = ? , AgentGroup = ? , PassFlag = ? , UWGrade = ? , AppGrade = ? , PostponeDay = ? , PostponeDate = ? , AutoUWFlag = ? , State = ? , ManageCom = ? , UWIdea = ? , UpReportContent = ? , Operator = ? , HealthFlag = ? , QuesFlag = ? , SpecFlag = ? , AddPremFlag = ? , AddPremReason = ? , ReportFlag = ? , PrintFlag = ? , PrintFlag2 = ? , ChangePolFlag = ? , ChangePolReason = ? , SpecReason = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , UpReport = ? , AutoUWDate = ? , UWConclu = ? , UWDate = ? , ComCode = ? , ModifyOperator = ? WHERE  GrpProposalNo = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getProposalGrpContNo() == null || this.getProposalGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getProposalGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpPolNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpProposalNo());
			}
			pstmt.setInt(5, this.getUWNo());
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getInsuredName());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAppntNo());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAppntName());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentGroup());
			}
			if(this.getPassFlag() == null || this.getPassFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPassFlag());
			}
			if(this.getUWGrade() == null || this.getUWGrade().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getUWGrade());
			}
			if(this.getAppGrade() == null || this.getAppGrade().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAppGrade());
			}
			if(this.getPostponeDay() == null || this.getPostponeDay().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPostponeDay());
			}
			if(this.getPostponeDate() == null || this.getPostponeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getPostponeDate()));
			}
			if(this.getAutoUWFlag() == null || this.getAutoUWFlag().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getAutoUWFlag());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getState());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getManageCom());
			}
			if(this.getUWIdea() == null || this.getUWIdea().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getUWIdea());
			}
			if(this.getUpReportContent() == null || this.getUpReportContent().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getUpReportContent());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getOperator());
			}
			if(this.getHealthFlag() == null || this.getHealthFlag().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getHealthFlag());
			}
			if(this.getQuesFlag() == null || this.getQuesFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getQuesFlag());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getSpecFlag());
			}
			if(this.getAddPremFlag() == null || this.getAddPremFlag().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getAddPremFlag());
			}
			if(this.getAddPremReason() == null || this.getAddPremReason().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getAddPremReason());
			}
			if(this.getReportFlag() == null || this.getReportFlag().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getReportFlag());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getPrintFlag());
			}
			if(this.getPrintFlag2() == null || this.getPrintFlag2().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getPrintFlag2());
			}
			if(this.getChangePolFlag() == null || this.getChangePolFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getChangePolFlag());
			}
			if(this.getChangePolReason() == null || this.getChangePolReason().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getChangePolReason());
			}
			if(this.getSpecReason() == null || this.getSpecReason().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getSpecReason());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getModifyTime());
			}
			if(this.getUpReport() == null || this.getUpReport().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getUpReport());
			}
			if(this.getAutoUWDate() == null || this.getAutoUWDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getAutoUWDate()));
			}
			if(this.getUWConclu() == null || this.getUWConclu().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getUWConclu());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getUWDate()));
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getModifyOperator());
			}
			// set where condition
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getGrpProposalNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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
		SQLString sqlObj = new SQLString("LCGUWMaster");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCGUWMaster VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getProposalGrpContNo() == null || this.getProposalGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getProposalGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpPolNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpProposalNo());
			}
			pstmt.setInt(5, this.getUWNo());
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getInsuredName());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAppntNo());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAppntName());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentGroup());
			}
			if(this.getPassFlag() == null || this.getPassFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPassFlag());
			}
			if(this.getUWGrade() == null || this.getUWGrade().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getUWGrade());
			}
			if(this.getAppGrade() == null || this.getAppGrade().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAppGrade());
			}
			if(this.getPostponeDay() == null || this.getPostponeDay().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPostponeDay());
			}
			if(this.getPostponeDate() == null || this.getPostponeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getPostponeDate()));
			}
			if(this.getAutoUWFlag() == null || this.getAutoUWFlag().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getAutoUWFlag());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getState());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getManageCom());
			}
			if(this.getUWIdea() == null || this.getUWIdea().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getUWIdea());
			}
			if(this.getUpReportContent() == null || this.getUpReportContent().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getUpReportContent());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getOperator());
			}
			if(this.getHealthFlag() == null || this.getHealthFlag().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getHealthFlag());
			}
			if(this.getQuesFlag() == null || this.getQuesFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getQuesFlag());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getSpecFlag());
			}
			if(this.getAddPremFlag() == null || this.getAddPremFlag().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getAddPremFlag());
			}
			if(this.getAddPremReason() == null || this.getAddPremReason().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getAddPremReason());
			}
			if(this.getReportFlag() == null || this.getReportFlag().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getReportFlag());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getPrintFlag());
			}
			if(this.getPrintFlag2() == null || this.getPrintFlag2().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getPrintFlag2());
			}
			if(this.getChangePolFlag() == null || this.getChangePolFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getChangePolFlag());
			}
			if(this.getChangePolReason() == null || this.getChangePolReason().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getChangePolReason());
			}
			if(this.getSpecReason() == null || this.getSpecReason().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getSpecReason());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getModifyTime());
			}
			if(this.getUpReport() == null || this.getUpReport().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getUpReport());
			}
			if(this.getAutoUWDate() == null || this.getAutoUWDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getAutoUWDate()));
			}
			if(this.getUWConclu() == null || this.getUWConclu().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getUWConclu());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getUWDate()));
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCGUWMaster WHERE  GrpProposalNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpProposalNo());
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
					tError.moduleName = "LCGUWMasterDB";
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
			tError.moduleName = "LCGUWMasterDB";
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

	public LCGUWMasterSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGUWMasterSet aLCGUWMasterSet = new LCGUWMasterSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGUWMaster");
			LCGUWMasterSchema aSchema = this.getSchema();
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
				LCGUWMasterSchema s1 = new LCGUWMasterSchema();
				s1.setSchema(rs,i);
				aLCGUWMasterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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

		return aLCGUWMasterSet;
	}

	public LCGUWMasterSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGUWMasterSet aLCGUWMasterSet = new LCGUWMasterSet();

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
				LCGUWMasterSchema s1 = new LCGUWMasterSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGUWMasterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGUWMasterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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

		return aLCGUWMasterSet;
	}

	public LCGUWMasterSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGUWMasterSet aLCGUWMasterSet = new LCGUWMasterSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGUWMaster");
			LCGUWMasterSchema aSchema = this.getSchema();
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

				LCGUWMasterSchema s1 = new LCGUWMasterSchema();
				s1.setSchema(rs,i);
				aLCGUWMasterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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

		return aLCGUWMasterSet;
	}

	public LCGUWMasterSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGUWMasterSet aLCGUWMasterSet = new LCGUWMasterSet();

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

				LCGUWMasterSchema s1 = new LCGUWMasterSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGUWMasterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGUWMasterSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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

		return aLCGUWMasterSet;
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
			SQLString sqlObj = new SQLString("LCGUWMaster");
			LCGUWMasterSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCGUWMaster " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGUWMasterDB";
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
			tError.moduleName = "LCGUWMasterDB";
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
        tError.moduleName = "LCGUWMasterDB";
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
        tError.moduleName = "LCGUWMasterDB";
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
        tError.moduleName = "LCGUWMasterDB";
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
        tError.moduleName = "LCGUWMasterDB";
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
 * @return LCGUWMasterSet
 */
public LCGUWMasterSet getData()
{
    int tCount = 0;
    LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
    LCGUWMasterSchema tLCGUWMasterSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCGUWMasterDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCGUWMasterSchema = new LCGUWMasterSchema();
        tLCGUWMasterSchema.setSchema(mResultSet, 1);
        tLCGUWMasterSet.add(tLCGUWMasterSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCGUWMasterSchema = new LCGUWMasterSchema();
                tLCGUWMasterSchema.setSchema(mResultSet, 1);
                tLCGUWMasterSet.add(tLCGUWMasterSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCGUWMasterDB";
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
    return tLCGUWMasterSet;
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
            tError.moduleName = "LCGUWMasterDB";
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
        tError.moduleName = "LCGUWMasterDB";
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
            tError.moduleName = "LCGUWMasterDB";
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
        tError.moduleName = "LCGUWMasterDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCGUWMasterSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGUWMasterSet aLCGUWMasterSet = new LCGUWMasterSet();

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
				LCGUWMasterSchema s1 = new LCGUWMasterSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGUWMasterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGUWMasterSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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

		return aLCGUWMasterSet;
	}

	public LCGUWMasterSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGUWMasterSet aLCGUWMasterSet = new LCGUWMasterSet();

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

				LCGUWMasterSchema s1 = new LCGUWMasterSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGUWMasterDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGUWMasterSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGUWMasterDB";
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

		return aLCGUWMasterSet; 
	}

}
