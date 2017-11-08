/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBDutySchema;
import com.sinosoft.lis.vschema.LBDutySet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBDutyDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LBDutyDB extends LBDutySchema
{
private static Logger logger = Logger.getLogger(LBDutyDB.class);

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
	public LBDutyDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBDuty" );
		mflag = true;
	}

	public LBDutyDB()
	{
		con = null;
		db = new DBOper( "LBDuty" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBDutySchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBDutySchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBDuty WHERE  EdorNo = ? AND PolNo = ? AND DutyCode = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBDuty");
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
		SQLString sqlObj = new SQLString("LBDuty");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBDuty SET  EdorNo = ? , PolNo = ? , DutyCode = ? , ContNo = ? , Mult = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , PayIntv = ? , PayYears = ? , Years = ? , FloatRate = ? , FirstPayDate = ? , FirstMonth = ? , PaytoDate = ? , PayEndDate = ? , PayEndYearFlag = ? , PayEndYear = ? , GetYearFlag = ? , GetYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , EndDate = ? , AcciEndDate = ? , FreeFlag = ? , FreeRate = ? , FreeStartDate = ? , FreeEndDate = ? , GetStartDate = ? , GetStartType = ? , LiveGetMode = ? , DeadGetMode = ? , BonusGetMode = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , CalRule = ? , PremToAmnt = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , CValiDate = ? , GetIntv = ? , AscriptionRuleCode = ? , PayRuleCode = ? , Currency = ? WHERE  EdorNo = ? AND PolNo = ? AND DutyCode = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDutyCode());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			pstmt.setDouble(5, this.getMult());
			pstmt.setDouble(6, this.getStandPrem());
			pstmt.setDouble(7, this.getPrem());
			pstmt.setDouble(8, this.getSumPrem());
			pstmt.setDouble(9, this.getAmnt());
			pstmt.setDouble(10, this.getRiskAmnt());
			pstmt.setInt(11, this.getPayIntv());
			pstmt.setInt(12, this.getPayYears());
			pstmt.setInt(13, this.getYears());
			pstmt.setDouble(14, this.getFloatRate());
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getFirstPayDate()));
			}
			pstmt.setInt(16, this.getFirstMonth());
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getPayEndYearFlag());
			}
			pstmt.setInt(20, this.getPayEndYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getGetYearFlag());
			}
			pstmt.setInt(22, this.getGetYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getInsuYearFlag());
			}
			pstmt.setInt(24, this.getInsuYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getAcciYearFlag());
			}
			pstmt.setInt(26, this.getAcciYear());
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getEndDate()));
			}
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getAcciEndDate()));
			}
			if(this.getFreeFlag() == null || this.getFreeFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getFreeFlag());
			}
			pstmt.setDouble(30, this.getFreeRate());
			if(this.getFreeStartDate() == null || this.getFreeStartDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getFreeStartDate()));
			}
			if(this.getFreeEndDate() == null || this.getFreeEndDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getFreeEndDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getGetStartType());
			}
			if(this.getLiveGetMode() == null || this.getLiveGetMode().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getLiveGetMode());
			}
			if(this.getDeadGetMode() == null || this.getDeadGetMode().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getDeadGetMode());
			}
			if(this.getBonusGetMode() == null || this.getBonusGetMode().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getBonusGetMode());
			}
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getSSFlag());
			}
			pstmt.setDouble(39, this.getPeakLine());
			pstmt.setDouble(40, this.getGetLimit());
			pstmt.setDouble(41, this.getGetRate());
			if(this.getCalRule() == null || this.getCalRule().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getCalRule());
			}
			if(this.getPremToAmnt() == null || this.getPremToAmnt().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getPremToAmnt());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(50, 91);
			} else {
				pstmt.setDate(50, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getModifyTime());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(53, this.getGetIntv());
			if(this.getAscriptionRuleCode() == null || this.getAscriptionRuleCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getAscriptionRuleCode());
			}
			if(this.getPayRuleCode() == null || this.getPayRuleCode().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getPayRuleCode());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getCurrency());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getEdorNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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
		SQLString sqlObj = new SQLString("LBDuty");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBDuty(EdorNo ,PolNo ,DutyCode ,ContNo ,Mult ,StandPrem ,Prem ,SumPrem ,Amnt ,RiskAmnt ,PayIntv ,PayYears ,Years ,FloatRate ,FirstPayDate ,FirstMonth ,PaytoDate ,PayEndDate ,PayEndYearFlag ,PayEndYear ,GetYearFlag ,GetYear ,InsuYearFlag ,InsuYear ,AcciYearFlag ,AcciYear ,EndDate ,AcciEndDate ,FreeFlag ,FreeRate ,FreeStartDate ,FreeEndDate ,GetStartDate ,GetStartType ,LiveGetMode ,DeadGetMode ,BonusGetMode ,SSFlag ,PeakLine ,GetLimit ,GetRate ,CalRule ,PremToAmnt ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,CValiDate ,GetIntv ,AscriptionRuleCode ,PayRuleCode ,Currency) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDutyCode());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			pstmt.setDouble(5, this.getMult());
			pstmt.setDouble(6, this.getStandPrem());
			pstmt.setDouble(7, this.getPrem());
			pstmt.setDouble(8, this.getSumPrem());
			pstmt.setDouble(9, this.getAmnt());
			pstmt.setDouble(10, this.getRiskAmnt());
			pstmt.setInt(11, this.getPayIntv());
			pstmt.setInt(12, this.getPayYears());
			pstmt.setInt(13, this.getYears());
			pstmt.setDouble(14, this.getFloatRate());
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getFirstPayDate()));
			}
			pstmt.setInt(16, this.getFirstMonth());
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getPayEndYearFlag());
			}
			pstmt.setInt(20, this.getPayEndYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getGetYearFlag());
			}
			pstmt.setInt(22, this.getGetYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getInsuYearFlag());
			}
			pstmt.setInt(24, this.getInsuYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getAcciYearFlag());
			}
			pstmt.setInt(26, this.getAcciYear());
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getEndDate()));
			}
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getAcciEndDate()));
			}
			if(this.getFreeFlag() == null || this.getFreeFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getFreeFlag());
			}
			pstmt.setDouble(30, this.getFreeRate());
			if(this.getFreeStartDate() == null || this.getFreeStartDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getFreeStartDate()));
			}
			if(this.getFreeEndDate() == null || this.getFreeEndDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getFreeEndDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getGetStartType());
			}
			if(this.getLiveGetMode() == null || this.getLiveGetMode().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getLiveGetMode());
			}
			if(this.getDeadGetMode() == null || this.getDeadGetMode().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getDeadGetMode());
			}
			if(this.getBonusGetMode() == null || this.getBonusGetMode().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getBonusGetMode());
			}
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getSSFlag());
			}
			pstmt.setDouble(39, this.getPeakLine());
			pstmt.setDouble(40, this.getGetLimit());
			pstmt.setDouble(41, this.getGetRate());
			if(this.getCalRule() == null || this.getCalRule().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getCalRule());
			}
			if(this.getPremToAmnt() == null || this.getPremToAmnt().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getPremToAmnt());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(50, 91);
			} else {
				pstmt.setDate(50, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getModifyTime());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(53, this.getGetIntv());
			if(this.getAscriptionRuleCode() == null || this.getAscriptionRuleCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getAscriptionRuleCode());
			}
			if(this.getPayRuleCode() == null || this.getPayRuleCode().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getPayRuleCode());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getCurrency());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBDuty WHERE  EdorNo = ? AND PolNo = ? AND DutyCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDutyCode());
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
					tError.moduleName = "LBDutyDB";
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
			tError.moduleName = "LBDutyDB";
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

	public LBDutySet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBDutySet aLBDutySet = new LBDutySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBDuty");
			LBDutySchema aSchema = this.getSchema();
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
				LBDutySchema s1 = new LBDutySchema();
				s1.setSchema(rs,i);
				aLBDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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

		return aLBDutySet;

	}

	public LBDutySet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBDutySet aLBDutySet = new LBDutySet();

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
				LBDutySchema s1 = new LBDutySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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

		return aLBDutySet;
	}

	public LBDutySet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBDutySet aLBDutySet = new LBDutySet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBDuty");
			LBDutySchema aSchema = this.getSchema();
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

				LBDutySchema s1 = new LBDutySchema();
				s1.setSchema(rs,i);
				aLBDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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

		return aLBDutySet;

	}

	public LBDutySet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBDutySet aLBDutySet = new LBDutySet();

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

				LBDutySchema s1 = new LBDutySchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBDutySet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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

		return aLBDutySet;
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
			SQLString sqlObj = new SQLString("LBDuty");
			LBDutySchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBDuty " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBDutyDB";
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
			tError.moduleName = "LBDutyDB";
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
        tError.moduleName = "LBDutyDB";
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
        tError.moduleName = "LBDutyDB";
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
        tError.moduleName = "LBDutyDB";
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
        tError.moduleName = "LBDutyDB";
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
 * @return LBDutySet
 */
public LBDutySet getData()
{
    int tCount = 0;
    LBDutySet tLBDutySet = new LBDutySet();
    LBDutySchema tLBDutySchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBDutyDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBDutySchema = new LBDutySchema();
        tLBDutySchema.setSchema(mResultSet, 1);
        tLBDutySet.add(tLBDutySchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBDutySchema = new LBDutySchema();
                tLBDutySchema.setSchema(mResultSet, 1);
                tLBDutySet.add(tLBDutySchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBDutyDB";
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
    return tLBDutySet;
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
            tError.moduleName = "LBDutyDB";
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
        tError.moduleName = "LBDutyDB";
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
            tError.moduleName = "LBDutyDB";
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
        tError.moduleName = "LBDutyDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBDutySet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBDutySet aLBDutySet = new LBDutySet();

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
				LBDutySchema s1 = new LBDutySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBDutySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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

		return aLBDutySet;
	}

	public LBDutySet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBDutySet aLBDutySet = new LBDutySet();

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

				LBDutySchema s1 = new LBDutySchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBDutyDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBDutySet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBDutyDB";
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

		return aLBDutySet; 
	}

}
