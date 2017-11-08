/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LQDutySchema;
import com.sinosoft.lis.vschema.LQDutySet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LQDutyDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LQDutyDBSet extends LQDutySet
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
	public LQDutyDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LQDuty");
		mflag = true;
	}

	public LQDutyDBSet()
	{
		db = new DBOper( "LQDuty" );
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
			tError.moduleName = "LQDutyDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LQDuty WHERE  AskPriceNo = ? AND AskNo = ? AND ClassNo = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAskNo());
			}
			if(this.get(i).getClassNo() == null || this.get(i).getClassNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClassNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LQDuty");
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
			tError.moduleName = "LQDutyDBSet";
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
			pstmt = con.prepareStatement("UPDATE LQDuty SET  AskPriceNo = ? , AskNo = ? , ClassNo = ? , DutyCode = ? , Mult = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , PayIntv = ? , PayYears = ? , Years = ? , FloatRate = ? , FirstPayDate = ? , FirstMonth = ? , PaytoDate = ? , PayEndDate = ? , PayEndYearFlag = ? , PayEndYear = ? , GetYearFlag = ? , GetYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , EndDate = ? , AcciEndDate = ? , FreeFlag = ? , FreeRate = ? , FreeStartDate = ? , FreeEndDate = ? , GetStartDate = ? , GetStartType = ? , LiveGetMode = ? , DeadGetMode = ? , BonusGetMode = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , CalRule = ? , PremToAmnt = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , CValiDate = ? , GetIntv = ? , AscriptionRuleCode = ? , PayRuleCode = ? , Currency = ? WHERE  AskPriceNo = ? AND AskNo = ? AND ClassNo = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAskNo());
			}
			if(this.get(i).getClassNo() == null || this.get(i).getClassNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClassNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDutyCode());
			}
			pstmt.setDouble(5, this.get(i).getMult());
			pstmt.setDouble(6, this.get(i).getStandPrem());
			pstmt.setDouble(7, this.get(i).getPrem());
			pstmt.setDouble(8, this.get(i).getSumPrem());
			pstmt.setDouble(9, this.get(i).getAmnt());
			pstmt.setDouble(10, this.get(i).getRiskAmnt());
			pstmt.setInt(11, this.get(i).getPayIntv());
			pstmt.setInt(12, this.get(i).getPayYears());
			pstmt.setInt(13, this.get(i).getYears());
			pstmt.setDouble(14, this.get(i).getFloatRate());
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			pstmt.setInt(16, this.get(i).getFirstMonth());
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(20, this.get(i).getPayEndYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(22, this.get(i).getGetYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(24, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(26, this.get(i).getAcciYear());
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFreeFlag());
			}
			pstmt.setDouble(30, this.get(i).getFreeRate());
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getFreeEndDate() == null || this.get(i).getFreeEndDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getFreeEndDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getGetStartType());
			}
			if(this.get(i).getLiveGetMode() == null || this.get(i).getLiveGetMode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getLiveGetMode());
			}
			if(this.get(i).getDeadGetMode() == null || this.get(i).getDeadGetMode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDeadGetMode());
			}
			if(this.get(i).getBonusGetMode() == null || this.get(i).getBonusGetMode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBonusGetMode());
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSSFlag());
			}
			pstmt.setDouble(39, this.get(i).getPeakLine());
			pstmt.setDouble(40, this.get(i).getGetLimit());
			pstmt.setDouble(41, this.get(i).getGetRate());
			if(this.get(i).getCalRule() == null || this.get(i).getCalRule().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCalRule());
			}
			if(this.get(i).getPremToAmnt() == null || this.get(i).getPremToAmnt().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPremToAmnt());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getModifyTime());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(53, this.get(i).getGetIntv());
			if(this.get(i).getAscriptionRuleCode() == null || this.get(i).getAscriptionRuleCode().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getAscriptionRuleCode());
			}
			if(this.get(i).getPayRuleCode() == null || this.get(i).getPayRuleCode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getPayRuleCode());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getCurrency());
			}
			// set where condition
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getAskNo());
			}
			if(this.get(i).getClassNo() == null || this.get(i).getClassNo().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getClassNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LQDuty");
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
			tError.moduleName = "LQDutyDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LQDuty VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAskNo());
			}
			if(this.get(i).getClassNo() == null || this.get(i).getClassNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClassNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDutyCode());
			}
			pstmt.setDouble(5, this.get(i).getMult());
			pstmt.setDouble(6, this.get(i).getStandPrem());
			pstmt.setDouble(7, this.get(i).getPrem());
			pstmt.setDouble(8, this.get(i).getSumPrem());
			pstmt.setDouble(9, this.get(i).getAmnt());
			pstmt.setDouble(10, this.get(i).getRiskAmnt());
			pstmt.setInt(11, this.get(i).getPayIntv());
			pstmt.setInt(12, this.get(i).getPayYears());
			pstmt.setInt(13, this.get(i).getYears());
			pstmt.setDouble(14, this.get(i).getFloatRate());
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			pstmt.setInt(16, this.get(i).getFirstMonth());
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(20, this.get(i).getPayEndYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(22, this.get(i).getGetYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(24, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(26, this.get(i).getAcciYear());
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFreeFlag());
			}
			pstmt.setDouble(30, this.get(i).getFreeRate());
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getFreeEndDate() == null || this.get(i).getFreeEndDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getFreeEndDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getGetStartType());
			}
			if(this.get(i).getLiveGetMode() == null || this.get(i).getLiveGetMode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getLiveGetMode());
			}
			if(this.get(i).getDeadGetMode() == null || this.get(i).getDeadGetMode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDeadGetMode());
			}
			if(this.get(i).getBonusGetMode() == null || this.get(i).getBonusGetMode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBonusGetMode());
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSSFlag());
			}
			pstmt.setDouble(39, this.get(i).getPeakLine());
			pstmt.setDouble(40, this.get(i).getGetLimit());
			pstmt.setDouble(41, this.get(i).getGetRate());
			if(this.get(i).getCalRule() == null || this.get(i).getCalRule().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCalRule());
			}
			if(this.get(i).getPremToAmnt() == null || this.get(i).getPremToAmnt().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPremToAmnt());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getModifyTime());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(53, this.get(i).getGetIntv());
			if(this.get(i).getAscriptionRuleCode() == null || this.get(i).getAscriptionRuleCode().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getAscriptionRuleCode());
			}
			if(this.get(i).getPayRuleCode() == null || this.get(i).getPayRuleCode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getPayRuleCode());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getCurrency());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LQDuty");
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
			tError.moduleName = "LQDutyDBSet";
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