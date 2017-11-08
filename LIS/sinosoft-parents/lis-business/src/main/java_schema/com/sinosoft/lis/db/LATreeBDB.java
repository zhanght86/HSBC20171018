/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LATreeBSchema;
import com.sinosoft.lis.vschema.LATreeBSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LATreeBDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LATreeBDB extends LATreeBSchema
{
private static Logger logger = Logger.getLogger(LATreeBDB.class);

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
	public LATreeBDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LATreeB" );
		mflag = true;
	}

	public LATreeBDB()
	{
		con = null;
		db = new DBOper( "LATreeB" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LATreeBSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LATreeBSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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
			pstmt = con.prepareStatement("DELETE FROM LATreeB WHERE  AgentCode = ? AND EdorNO = ?");
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			if(this.getEdorNO() == null || this.getEdorNO().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNO());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LATreeB");
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
		SQLString sqlObj = new SQLString("LATreeB");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LATreeB SET  AgentCode = ? , AgentGroup = ? , ManageCom = ? , AgentSeries = ? , AgentGrade = ? , AgentLastSeries = ? , AgentLastGrade = ? , IntroAgency = ? , UpAgent = ? , OthUpAgent = ? , IntroBreakFlag = ? , IntroCommStart = ? , IntroCommEnd = ? , EduManager = ? , RearBreakFlag = ? , RearCommStart = ? , RearCommEnd = ? , AscriptSeries = ? , OldStartDate = ? , OldEndDate = ? , StartDate = ? , AstartDate = ? , AssessType = ? , State = ? , Operator2 = ? , MakeDate2 = ? , MakeTime2 = ? , ModifyDate2 = ? , ModifyTime2 = ? , RemoveType = ? , EdorNO = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchCode = ? , IndexCalNo = ? , LastAgentGrade1 = ? , AgentGrade1 = ? , EmployGrade = ? WHERE  AgentCode = ? AND EdorNO = ?");
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentGroup());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getManageCom());
			}
			if(this.getAgentSeries() == null || this.getAgentSeries().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAgentSeries());
			}
			if(this.getAgentGrade() == null || this.getAgentGrade().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAgentGrade());
			}
			if(this.getAgentLastSeries() == null || this.getAgentLastSeries().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getAgentLastSeries());
			}
			if(this.getAgentLastGrade() == null || this.getAgentLastGrade().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAgentLastGrade());
			}
			if(this.getIntroAgency() == null || this.getIntroAgency().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getIntroAgency());
			}
			if(this.getUpAgent() == null || this.getUpAgent().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getUpAgent());
			}
			if(this.getOthUpAgent() == null || this.getOthUpAgent().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getOthUpAgent());
			}
			if(this.getIntroBreakFlag() == null || this.getIntroBreakFlag().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getIntroBreakFlag());
			}
			if(this.getIntroCommStart() == null || this.getIntroCommStart().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getIntroCommStart()));
			}
			if(this.getIntroCommEnd() == null || this.getIntroCommEnd().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getIntroCommEnd()));
			}
			if(this.getEduManager() == null || this.getEduManager().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getEduManager());
			}
			if(this.getRearBreakFlag() == null || this.getRearBreakFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getRearBreakFlag());
			}
			if(this.getRearCommStart() == null || this.getRearCommStart().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getRearCommStart()));
			}
			if(this.getRearCommEnd() == null || this.getRearCommEnd().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getRearCommEnd()));
			}
			if(this.getAscriptSeries() == null || this.getAscriptSeries().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAscriptSeries());
			}
			if(this.getOldStartDate() == null || this.getOldStartDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getOldStartDate()));
			}
			if(this.getOldEndDate() == null || this.getOldEndDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getOldEndDate()));
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getStartDate()));
			}
			if(this.getAstartDate() == null || this.getAstartDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getAstartDate()));
			}
			if(this.getAssessType() == null || this.getAssessType().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getAssessType());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getState());
			}
			if(this.getOperator2() == null || this.getOperator2().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOperator2());
			}
			if(this.getMakeDate2() == null || this.getMakeDate2().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate2()));
			}
			if(this.getMakeTime2() == null || this.getMakeTime2().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMakeTime2());
			}
			if(this.getModifyDate2() == null || this.getModifyDate2().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getModifyDate2()));
			}
			if(this.getModifyTime2() == null || this.getModifyTime2().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getModifyTime2());
			}
			if(this.getRemoveType() == null || this.getRemoveType().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getRemoveType());
			}
			if(this.getEdorNO() == null || this.getEdorNO().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getEdorNO());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(35, 91);
			} else {
				pstmt.setDate(35, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getModifyTime());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBranchCode());
			}
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getIndexCalNo());
			}
			if(this.getLastAgentGrade1() == null || this.getLastAgentGrade1().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getLastAgentGrade1());
			}
			if(this.getAgentGrade1() == null || this.getAgentGrade1().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getAgentGrade1());
			}
			if(this.getEmployGrade() == null || this.getEmployGrade().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getEmployGrade());
			}
			// set where condition
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getAgentCode());
			}
			if(this.getEdorNO() == null || this.getEdorNO().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getEdorNO());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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
		SQLString sqlObj = new SQLString("LATreeB");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LATreeB(AgentCode ,AgentGroup ,ManageCom ,AgentSeries ,AgentGrade ,AgentLastSeries ,AgentLastGrade ,IntroAgency ,UpAgent ,OthUpAgent ,IntroBreakFlag ,IntroCommStart ,IntroCommEnd ,EduManager ,RearBreakFlag ,RearCommStart ,RearCommEnd ,AscriptSeries ,OldStartDate ,OldEndDate ,StartDate ,AstartDate ,AssessType ,State ,Operator2 ,MakeDate2 ,MakeTime2 ,ModifyDate2 ,ModifyTime2 ,RemoveType ,EdorNO ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchCode ,IndexCalNo ,LastAgentGrade1 ,AgentGrade1 ,EmployGrade) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentGroup());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getManageCom());
			}
			if(this.getAgentSeries() == null || this.getAgentSeries().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAgentSeries());
			}
			if(this.getAgentGrade() == null || this.getAgentGrade().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAgentGrade());
			}
			if(this.getAgentLastSeries() == null || this.getAgentLastSeries().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getAgentLastSeries());
			}
			if(this.getAgentLastGrade() == null || this.getAgentLastGrade().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAgentLastGrade());
			}
			if(this.getIntroAgency() == null || this.getIntroAgency().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getIntroAgency());
			}
			if(this.getUpAgent() == null || this.getUpAgent().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getUpAgent());
			}
			if(this.getOthUpAgent() == null || this.getOthUpAgent().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getOthUpAgent());
			}
			if(this.getIntroBreakFlag() == null || this.getIntroBreakFlag().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getIntroBreakFlag());
			}
			if(this.getIntroCommStart() == null || this.getIntroCommStart().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getIntroCommStart()));
			}
			if(this.getIntroCommEnd() == null || this.getIntroCommEnd().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getIntroCommEnd()));
			}
			if(this.getEduManager() == null || this.getEduManager().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getEduManager());
			}
			if(this.getRearBreakFlag() == null || this.getRearBreakFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getRearBreakFlag());
			}
			if(this.getRearCommStart() == null || this.getRearCommStart().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getRearCommStart()));
			}
			if(this.getRearCommEnd() == null || this.getRearCommEnd().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getRearCommEnd()));
			}
			if(this.getAscriptSeries() == null || this.getAscriptSeries().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAscriptSeries());
			}
			if(this.getOldStartDate() == null || this.getOldStartDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getOldStartDate()));
			}
			if(this.getOldEndDate() == null || this.getOldEndDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getOldEndDate()));
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getStartDate()));
			}
			if(this.getAstartDate() == null || this.getAstartDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getAstartDate()));
			}
			if(this.getAssessType() == null || this.getAssessType().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getAssessType());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getState());
			}
			if(this.getOperator2() == null || this.getOperator2().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOperator2());
			}
			if(this.getMakeDate2() == null || this.getMakeDate2().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate2()));
			}
			if(this.getMakeTime2() == null || this.getMakeTime2().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getMakeTime2());
			}
			if(this.getModifyDate2() == null || this.getModifyDate2().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getModifyDate2()));
			}
			if(this.getModifyTime2() == null || this.getModifyTime2().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getModifyTime2());
			}
			if(this.getRemoveType() == null || this.getRemoveType().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getRemoveType());
			}
			if(this.getEdorNO() == null || this.getEdorNO().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getEdorNO());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(35, 91);
			} else {
				pstmt.setDate(35, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getModifyTime());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBranchCode());
			}
			if(this.getIndexCalNo() == null || this.getIndexCalNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getIndexCalNo());
			}
			if(this.getLastAgentGrade1() == null || this.getLastAgentGrade1().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getLastAgentGrade1());
			}
			if(this.getAgentGrade1() == null || this.getAgentGrade1().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getAgentGrade1());
			}
			if(this.getEmployGrade() == null || this.getEmployGrade().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getEmployGrade());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LATreeB WHERE  AgentCode = ? AND EdorNO = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			if(this.getEdorNO() == null || this.getEdorNO().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorNO());
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
					tError.moduleName = "LATreeBDB";
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
			tError.moduleName = "LATreeBDB";
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

	public LATreeBSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LATreeBSet aLATreeBSet = new LATreeBSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LATreeB");
			LATreeBSchema aSchema = this.getSchema();
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
				LATreeBSchema s1 = new LATreeBSchema();
				s1.setSchema(rs,i);
				aLATreeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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

		return aLATreeBSet;

	}

	public LATreeBSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LATreeBSet aLATreeBSet = new LATreeBSet();

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
				LATreeBSchema s1 = new LATreeBSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LATreeBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLATreeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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

		return aLATreeBSet;
	}

	public LATreeBSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LATreeBSet aLATreeBSet = new LATreeBSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LATreeB");
			LATreeBSchema aSchema = this.getSchema();
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

				LATreeBSchema s1 = new LATreeBSchema();
				s1.setSchema(rs,i);
				aLATreeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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

		return aLATreeBSet;

	}

	public LATreeBSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LATreeBSet aLATreeBSet = new LATreeBSet();

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

				LATreeBSchema s1 = new LATreeBSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LATreeBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLATreeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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

		return aLATreeBSet;
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
			SQLString sqlObj = new SQLString("LATreeB");
			LATreeBSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LATreeB " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LATreeBDB";
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
			tError.moduleName = "LATreeBDB";
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
        tError.moduleName = "LATreeBDB";
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
        tError.moduleName = "LATreeBDB";
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
        tError.moduleName = "LATreeBDB";
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
        tError.moduleName = "LATreeBDB";
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
 * @return LATreeBSet
 */
public LATreeBSet getData()
{
    int tCount = 0;
    LATreeBSet tLATreeBSet = new LATreeBSet();
    LATreeBSchema tLATreeBSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LATreeBDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLATreeBSchema = new LATreeBSchema();
        tLATreeBSchema.setSchema(mResultSet, 1);
        tLATreeBSet.add(tLATreeBSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLATreeBSchema = new LATreeBSchema();
                tLATreeBSchema.setSchema(mResultSet, 1);
                tLATreeBSet.add(tLATreeBSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LATreeBDB";
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
    return tLATreeBSet;
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
            tError.moduleName = "LATreeBDB";
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
        tError.moduleName = "LATreeBDB";
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
            tError.moduleName = "LATreeBDB";
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
        tError.moduleName = "LATreeBDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LATreeBSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LATreeBSet aLATreeBSet = new LATreeBSet();

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
				LATreeBSchema s1 = new LATreeBSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LATreeBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLATreeBSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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

		return aLATreeBSet;
	}

	public LATreeBSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LATreeBSet aLATreeBSet = new LATreeBSet();

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

				LATreeBSchema s1 = new LATreeBSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LATreeBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLATreeBSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATreeBDB";
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

		return aLATreeBSet; 
	}

}
