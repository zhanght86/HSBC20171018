/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LATreeBSchema;
import com.sinosoft.lis.vschema.LATreeBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LATreeBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LATreeBDBSet extends LATreeBSet
{
private static Logger logger = Logger.getLogger(LATreeBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LATreeBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LATreeB");
		mflag = true;
	}

	public LATreeBDBSet()
	{
		db = new DBOper( "LATreeB" );
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
			tError.moduleName = "LATreeBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LATreeB WHERE  AgentCode = ? AND EdorNO = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAgentCode());
			}
			if(this.get(i).getEdorNO() == null || this.get(i).getEdorNO().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorNO());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LATreeB");
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
			tError.moduleName = "LATreeBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LATreeB SET  AgentCode = ? , AgentGroup = ? , ManageCom = ? , AgentSeries = ? , AgentGrade = ? , AgentLastSeries = ? , AgentLastGrade = ? , IntroAgency = ? , UpAgent = ? , OthUpAgent = ? , IntroBreakFlag = ? , IntroCommStart = ? , IntroCommEnd = ? , EduManager = ? , RearBreakFlag = ? , RearCommStart = ? , RearCommEnd = ? , AscriptSeries = ? , OldStartDate = ? , OldEndDate = ? , StartDate = ? , AstartDate = ? , AssessType = ? , State = ? , Operator2 = ? , MakeDate2 = ? , MakeTime2 = ? , ModifyDate2 = ? , ModifyTime2 = ? , RemoveType = ? , EdorNO = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchCode = ? , IndexCalNo = ? , LastAgentGrade1 = ? , AgentGrade1 = ? , EmployGrade = ? WHERE  AgentCode = ? AND EdorNO = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentGroup());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentSeries() == null || this.get(i).getAgentSeries().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAgentSeries());
			}
			if(this.get(i).getAgentGrade() == null || this.get(i).getAgentGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentGrade());
			}
			if(this.get(i).getAgentLastSeries() == null || this.get(i).getAgentLastSeries().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentLastSeries());
			}
			if(this.get(i).getAgentLastGrade() == null || this.get(i).getAgentLastGrade().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentLastGrade());
			}
			if(this.get(i).getIntroAgency() == null || this.get(i).getIntroAgency().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getIntroAgency());
			}
			if(this.get(i).getUpAgent() == null || this.get(i).getUpAgent().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getUpAgent());
			}
			if(this.get(i).getOthUpAgent() == null || this.get(i).getOthUpAgent().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOthUpAgent());
			}
			if(this.get(i).getIntroBreakFlag() == null || this.get(i).getIntroBreakFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIntroBreakFlag());
			}
			if(this.get(i).getIntroCommStart() == null || this.get(i).getIntroCommStart().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getIntroCommStart()));
			}
			if(this.get(i).getIntroCommEnd() == null || this.get(i).getIntroCommEnd().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getIntroCommEnd()));
			}
			if(this.get(i).getEduManager() == null || this.get(i).getEduManager().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getEduManager());
			}
			if(this.get(i).getRearBreakFlag() == null || this.get(i).getRearBreakFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRearBreakFlag());
			}
			if(this.get(i).getRearCommStart() == null || this.get(i).getRearCommStart().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getRearCommStart()));
			}
			if(this.get(i).getRearCommEnd() == null || this.get(i).getRearCommEnd().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getRearCommEnd()));
			}
			if(this.get(i).getAscriptSeries() == null || this.get(i).getAscriptSeries().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAscriptSeries());
			}
			if(this.get(i).getOldStartDate() == null || this.get(i).getOldStartDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getOldStartDate()));
			}
			if(this.get(i).getOldEndDate() == null || this.get(i).getOldEndDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getOldEndDate()));
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getAstartDate() == null || this.get(i).getAstartDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getAstartDate()));
			}
			if(this.get(i).getAssessType() == null || this.get(i).getAssessType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAssessType());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getState());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOperator2());
			}
			if(this.get(i).getMakeDate2() == null || this.get(i).getMakeDate2().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getMakeDate2()));
			}
			if(this.get(i).getMakeTime2() == null || this.get(i).getMakeTime2().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getMakeTime2());
			}
			if(this.get(i).getModifyDate2() == null || this.get(i).getModifyDate2().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getModifyDate2()));
			}
			if(this.get(i).getModifyTime2() == null || this.get(i).getModifyTime2().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getModifyTime2());
			}
			if(this.get(i).getRemoveType() == null || this.get(i).getRemoveType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getRemoveType());
			}
			if(this.get(i).getEdorNO() == null || this.get(i).getEdorNO().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getEdorNO());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchCode() == null || this.get(i).getBranchCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBranchCode());
			}
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getIndexCalNo());
			}
			if(this.get(i).getLastAgentGrade1() == null || this.get(i).getLastAgentGrade1().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getLastAgentGrade1());
			}
			if(this.get(i).getAgentGrade1() == null || this.get(i).getAgentGrade1().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getAgentGrade1());
			}
			if(this.get(i).getEmployGrade() == null || this.get(i).getEmployGrade().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getEmployGrade());
			}
			// set where condition
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getAgentCode());
			}
			if(this.get(i).getEdorNO() == null || this.get(i).getEdorNO().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getEdorNO());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LATreeB");
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
			tError.moduleName = "LATreeBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LATreeB(AgentCode ,AgentGroup ,ManageCom ,AgentSeries ,AgentGrade ,AgentLastSeries ,AgentLastGrade ,IntroAgency ,UpAgent ,OthUpAgent ,IntroBreakFlag ,IntroCommStart ,IntroCommEnd ,EduManager ,RearBreakFlag ,RearCommStart ,RearCommEnd ,AscriptSeries ,OldStartDate ,OldEndDate ,StartDate ,AstartDate ,AssessType ,State ,Operator2 ,MakeDate2 ,MakeTime2 ,ModifyDate2 ,ModifyTime2 ,RemoveType ,EdorNO ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchCode ,IndexCalNo ,LastAgentGrade1 ,AgentGrade1 ,EmployGrade) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentGroup());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentSeries() == null || this.get(i).getAgentSeries().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAgentSeries());
			}
			if(this.get(i).getAgentGrade() == null || this.get(i).getAgentGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentGrade());
			}
			if(this.get(i).getAgentLastSeries() == null || this.get(i).getAgentLastSeries().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentLastSeries());
			}
			if(this.get(i).getAgentLastGrade() == null || this.get(i).getAgentLastGrade().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentLastGrade());
			}
			if(this.get(i).getIntroAgency() == null || this.get(i).getIntroAgency().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getIntroAgency());
			}
			if(this.get(i).getUpAgent() == null || this.get(i).getUpAgent().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getUpAgent());
			}
			if(this.get(i).getOthUpAgent() == null || this.get(i).getOthUpAgent().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOthUpAgent());
			}
			if(this.get(i).getIntroBreakFlag() == null || this.get(i).getIntroBreakFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIntroBreakFlag());
			}
			if(this.get(i).getIntroCommStart() == null || this.get(i).getIntroCommStart().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getIntroCommStart()));
			}
			if(this.get(i).getIntroCommEnd() == null || this.get(i).getIntroCommEnd().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getIntroCommEnd()));
			}
			if(this.get(i).getEduManager() == null || this.get(i).getEduManager().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getEduManager());
			}
			if(this.get(i).getRearBreakFlag() == null || this.get(i).getRearBreakFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getRearBreakFlag());
			}
			if(this.get(i).getRearCommStart() == null || this.get(i).getRearCommStart().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getRearCommStart()));
			}
			if(this.get(i).getRearCommEnd() == null || this.get(i).getRearCommEnd().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getRearCommEnd()));
			}
			if(this.get(i).getAscriptSeries() == null || this.get(i).getAscriptSeries().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAscriptSeries());
			}
			if(this.get(i).getOldStartDate() == null || this.get(i).getOldStartDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getOldStartDate()));
			}
			if(this.get(i).getOldEndDate() == null || this.get(i).getOldEndDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getOldEndDate()));
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getAstartDate() == null || this.get(i).getAstartDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getAstartDate()));
			}
			if(this.get(i).getAssessType() == null || this.get(i).getAssessType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAssessType());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getState());
			}
			if(this.get(i).getOperator2() == null || this.get(i).getOperator2().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOperator2());
			}
			if(this.get(i).getMakeDate2() == null || this.get(i).getMakeDate2().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getMakeDate2()));
			}
			if(this.get(i).getMakeTime2() == null || this.get(i).getMakeTime2().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getMakeTime2());
			}
			if(this.get(i).getModifyDate2() == null || this.get(i).getModifyDate2().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getModifyDate2()));
			}
			if(this.get(i).getModifyTime2() == null || this.get(i).getModifyTime2().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getModifyTime2());
			}
			if(this.get(i).getRemoveType() == null || this.get(i).getRemoveType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getRemoveType());
			}
			if(this.get(i).getEdorNO() == null || this.get(i).getEdorNO().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getEdorNO());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchCode() == null || this.get(i).getBranchCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBranchCode());
			}
			if(this.get(i).getIndexCalNo() == null || this.get(i).getIndexCalNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getIndexCalNo());
			}
			if(this.get(i).getLastAgentGrade1() == null || this.get(i).getLastAgentGrade1().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getLastAgentGrade1());
			}
			if(this.get(i).getAgentGrade1() == null || this.get(i).getAgentGrade1().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getAgentGrade1());
			}
			if(this.get(i).getEmployGrade() == null || this.get(i).getEmployGrade().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getEmployGrade());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LATreeB");
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
			tError.moduleName = "LATreeBDBSet";
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
