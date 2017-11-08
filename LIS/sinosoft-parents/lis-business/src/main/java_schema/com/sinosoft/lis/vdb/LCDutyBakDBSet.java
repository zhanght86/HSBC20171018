/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCDutyBakSchema;
import com.sinosoft.lis.vschema.LCDutyBakSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCDutyBakDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCDutyBakDBSet extends LCDutyBakSet
{
private static Logger logger = Logger.getLogger(LCDutyBakDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCDutyBakDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCDutyBak");
		mflag = true;
	}

	public LCDutyBakDBSet()
	{
		db = new DBOper( "LCDutyBak" );
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
			tError.moduleName = "LCDutyBakDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCDutyBak WHERE  PolNo = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCDutyBak");
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
			tError.moduleName = "LCDutyBakDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCDutyBak SET  PolNo = ? , DutyCode = ? , ContNo = ? , Mult = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , PayIntv = ? , PayYears = ? , Years = ? , FloatRate = ? , FirstPayDate = ? , FirstMonth = ? , PaytoDate = ? , PayEndDate = ? , PayEndYearFlag = ? , PayEndYear = ? , GetYearFlag = ? , GetYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , EndDate = ? , AcciEndDate = ? , FreeFlag = ? , FreeRate = ? , FreeStartDate = ? , FreeEndDate = ? , GetStartDate = ? , GetStartType = ? , LiveGetMode = ? , DeadGetMode = ? , BonusGetMode = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , CalRule = ? , PremToAmnt = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , CValiDate = ? , GetIntv = ? , AscriptionRuleCode = ? , PayRuleCode = ? WHERE  PolNo = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			pstmt.setDouble(4, this.get(i).getMult());
			pstmt.setDouble(5, this.get(i).getStandPrem());
			pstmt.setDouble(6, this.get(i).getPrem());
			pstmt.setDouble(7, this.get(i).getSumPrem());
			pstmt.setDouble(8, this.get(i).getAmnt());
			pstmt.setDouble(9, this.get(i).getRiskAmnt());
			pstmt.setInt(10, this.get(i).getPayIntv());
			pstmt.setInt(11, this.get(i).getPayYears());
			pstmt.setInt(12, this.get(i).getYears());
			pstmt.setDouble(13, this.get(i).getFloatRate());
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			pstmt.setInt(15, this.get(i).getFirstMonth());
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(19, this.get(i).getPayEndYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(21, this.get(i).getGetYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(23, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(25, this.get(i).getAcciYear());
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFreeFlag());
			}
			pstmt.setDouble(29, this.get(i).getFreeRate());
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getFreeEndDate() == null || this.get(i).getFreeEndDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getFreeEndDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getGetStartType());
			}
			if(this.get(i).getLiveGetMode() == null || this.get(i).getLiveGetMode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getLiveGetMode());
			}
			if(this.get(i).getDeadGetMode() == null || this.get(i).getDeadGetMode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getDeadGetMode());
			}
			if(this.get(i).getBonusGetMode() == null || this.get(i).getBonusGetMode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getBonusGetMode());
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSSFlag());
			}
			pstmt.setDouble(38, this.get(i).getPeakLine());
			pstmt.setDouble(39, this.get(i).getGetLimit());
			pstmt.setDouble(40, this.get(i).getGetRate());
			if(this.get(i).getCalRule() == null || this.get(i).getCalRule().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getCalRule());
			}
			if(this.get(i).getPremToAmnt() == null || this.get(i).getPremToAmnt().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPremToAmnt());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(47,null);
			} else {
				pstmt.setDate(47, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getModifyTime());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(52, this.get(i).getGetIntv());
			if(this.get(i).getAscriptionRuleCode() == null || this.get(i).getAscriptionRuleCode().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getAscriptionRuleCode());
			}
			if(this.get(i).getPayRuleCode() == null || this.get(i).getPayRuleCode().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getPayRuleCode());
			}
			// set where condition
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCDutyBak");
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
			tError.moduleName = "LCDutyBakDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCDutyBak(PolNo ,DutyCode ,ContNo ,Mult ,StandPrem ,Prem ,SumPrem ,Amnt ,RiskAmnt ,PayIntv ,PayYears ,Years ,FloatRate ,FirstPayDate ,FirstMonth ,PaytoDate ,PayEndDate ,PayEndYearFlag ,PayEndYear ,GetYearFlag ,GetYear ,InsuYearFlag ,InsuYear ,AcciYearFlag ,AcciYear ,EndDate ,AcciEndDate ,FreeFlag ,FreeRate ,FreeStartDate ,FreeEndDate ,GetStartDate ,GetStartType ,LiveGetMode ,DeadGetMode ,BonusGetMode ,SSFlag ,PeakLine ,GetLimit ,GetRate ,CalRule ,PremToAmnt ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,CValiDate ,GetIntv ,AscriptionRuleCode ,PayRuleCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			pstmt.setDouble(4, this.get(i).getMult());
			pstmt.setDouble(5, this.get(i).getStandPrem());
			pstmt.setDouble(6, this.get(i).getPrem());
			pstmt.setDouble(7, this.get(i).getSumPrem());
			pstmt.setDouble(8, this.get(i).getAmnt());
			pstmt.setDouble(9, this.get(i).getRiskAmnt());
			pstmt.setInt(10, this.get(i).getPayIntv());
			pstmt.setInt(11, this.get(i).getPayYears());
			pstmt.setInt(12, this.get(i).getYears());
			pstmt.setDouble(13, this.get(i).getFloatRate());
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			pstmt.setInt(15, this.get(i).getFirstMonth());
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(19, this.get(i).getPayEndYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(21, this.get(i).getGetYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(23, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(25, this.get(i).getAcciYear());
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getFreeFlag());
			}
			pstmt.setDouble(29, this.get(i).getFreeRate());
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getFreeEndDate() == null || this.get(i).getFreeEndDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getFreeEndDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getGetStartType());
			}
			if(this.get(i).getLiveGetMode() == null || this.get(i).getLiveGetMode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getLiveGetMode());
			}
			if(this.get(i).getDeadGetMode() == null || this.get(i).getDeadGetMode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getDeadGetMode());
			}
			if(this.get(i).getBonusGetMode() == null || this.get(i).getBonusGetMode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getBonusGetMode());
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSSFlag());
			}
			pstmt.setDouble(38, this.get(i).getPeakLine());
			pstmt.setDouble(39, this.get(i).getGetLimit());
			pstmt.setDouble(40, this.get(i).getGetRate());
			if(this.get(i).getCalRule() == null || this.get(i).getCalRule().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getCalRule());
			}
			if(this.get(i).getPremToAmnt() == null || this.get(i).getPremToAmnt().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPremToAmnt());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(47,null);
			} else {
				pstmt.setDate(47, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getModifyTime());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(52, this.get(i).getGetIntv());
			if(this.get(i).getAscriptionRuleCode() == null || this.get(i).getAscriptionRuleCode().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getAscriptionRuleCode());
			}
			if(this.get(i).getPayRuleCode() == null || this.get(i).getPayRuleCode().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getPayRuleCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCDutyBak");
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
			tError.moduleName = "LCDutyBakDBSet";
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
