/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LACommisionSchema;
import com.sinosoft.lis.vschema.LACommisionSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LACommisionDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LACommisionDBSet extends LACommisionSet
{
private static Logger logger = Logger.getLogger(LACommisionDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LACommisionDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LACommision");
		mflag = true;
	}

	public LACommisionDBSet()
	{
		db = new DBOper( "LACommision" );
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
			tError.moduleName = "LACommisionDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LACommision WHERE  CommisionSN = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCommisionSN() == null || this.get(i).getCommisionSN().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCommisionSN());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LACommision");
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
			tError.moduleName = "LACommisionDBSet";
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
			pstmt = con.prepareStatement("UPDATE LACommision SET  CommisionSN = ? , WageNo = ? , ContNo = ? , GrpPolNo = ? , MainPolNo = ? , PolNo = ? , ManageCom = ? , AppntNo = ? , RiskCode = ? , RiskVersion = ? , DutyCode = ? , PayPlanCode = ? , CValiDate = ? , PayIntv = ? , PayMode = ? , ReceiptNo = ? , TPayDate = ? , TEnterAccDate = ? , TConfDate = ? , TMakeDate = ? , CalcDate = ? , CommDate = ? , TransMoney = ? , TransStandMoney = ? , LastPayToDate = ? , CurPayToDate = ? , TransType = ? , TransState = ? , CommDire = ? , DirectWage = ? , AppendWage = ? , F1 = ? , F2 = ? , F3 = ? , F4 = ? , F5 = ? , K1 = ? , K2 = ? , K3 = ? , K4 = ? , K5 = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , AgentGroup1 = ? , Flag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , PayYear = ? , PayYears = ? , Years = ? , PayCount = ? , SignDate = ? , GetPolDate = ? , BranchType = ? , AgentCom = ? , AgentType = ? , BranchCode = ? , CalDate = ? , CalCount = ? , BranchAttr = ? , StandFYCRate = ? , FYCRate = ? , FYC = ? , GrpFYC = ? , DepFYC = ? , StandPrem = ? , CommCharge = ? , CommCharge1 = ? , CommCharge2 = ? , CommCharge3 = ? , CommCharge4 = ? , GrpFYCRate = ? , DepFYCRate = ? , StandPremRate = ? , PolType = ? , P1 = ? , P2 = ? , P3 = ? , P4 = ? , P5 = ? , P6 = ? , P7 = ? , P8 = ? , P9 = ? , P10 = ? , P11 = ? , P12 = ? , P13 = ? , P14 = ? , P15 = ? , MakePolDate = ? , CustomGetPolDate = ? , branchattr1 = ? , branchcode1 = ? , riskmark = ? , scanDate = ? , AgentGroup2 = ? , AgentGroup3 = ? , AgentGroup4 = ? , InsuYearFlag = ? , InsuYear = ? , GrpContNo = ? WHERE  CommisionSN = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCommisionSN() == null || this.get(i).getCommisionSN().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCommisionSN());
			}
			if(this.get(i).getWageNo() == null || this.get(i).getWageNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getWageNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getMainPolNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPolNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getManageCom());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppntNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskVersion());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDutyCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(14, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPayMode());
			}
			if(this.get(i).getReceiptNo() == null || this.get(i).getReceiptNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getReceiptNo());
			}
			if(this.get(i).getTPayDate() == null || this.get(i).getTPayDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getTPayDate()));
			}
			if(this.get(i).getTEnterAccDate() == null || this.get(i).getTEnterAccDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getTEnterAccDate()));
			}
			if(this.get(i).getTConfDate() == null || this.get(i).getTConfDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getTConfDate()));
			}
			if(this.get(i).getTMakeDate() == null || this.get(i).getTMakeDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getTMakeDate()));
			}
			if(this.get(i).getCalcDate() == null || this.get(i).getCalcDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getCalcDate()));
			}
			if(this.get(i).getCommDate() == null || this.get(i).getCommDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getCommDate()));
			}
			pstmt.setDouble(23, this.get(i).getTransMoney());
			pstmt.setDouble(24, this.get(i).getTransStandMoney());
			if(this.get(i).getLastPayToDate() == null || this.get(i).getLastPayToDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getLastPayToDate()));
			}
			if(this.get(i).getCurPayToDate() == null || this.get(i).getCurPayToDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getCurPayToDate()));
			}
			if(this.get(i).getTransType() == null || this.get(i).getTransType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getTransType());
			}
			if(this.get(i).getTransState() == null || this.get(i).getTransState().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getTransState());
			}
			if(this.get(i).getCommDire() == null || this.get(i).getCommDire().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCommDire());
			}
			pstmt.setDouble(30, this.get(i).getDirectWage());
			pstmt.setDouble(31, this.get(i).getAppendWage());
			if(this.get(i).getF1() == null || this.get(i).getF1().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getF1());
			}
			if(this.get(i).getF2() == null || this.get(i).getF2().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getF2());
			}
			if(this.get(i).getF3() == null || this.get(i).getF3().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getF3());
			}
			if(this.get(i).getF4() == null || this.get(i).getF4().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getF4());
			}
			if(this.get(i).getF5() == null || this.get(i).getF5().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getF5());
			}
			pstmt.setDouble(37, this.get(i).getK1());
			pstmt.setDouble(38, this.get(i).getK2());
			pstmt.setDouble(39, this.get(i).getK3());
			pstmt.setDouble(40, this.get(i).getK4());
			pstmt.setDouble(41, this.get(i).getK5());
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getAgentCode1());
			}
			if(this.get(i).getAgentGroup1() == null || this.get(i).getAgentGroup1().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getAgentGroup1());
			}
			if(this.get(i).getFlag() == null || this.get(i).getFlag().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getFlag());
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
			pstmt.setInt(52, this.get(i).getPayYear());
			pstmt.setInt(53, this.get(i).getPayYears());
			pstmt.setInt(54, this.get(i).getYears());
			pstmt.setInt(55, this.get(i).getPayCount());
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(56,null);
			} else {
				pstmt.setDate(56, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getBranchType());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getAgentType());
			}
			if(this.get(i).getBranchCode() == null || this.get(i).getBranchCode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getBranchCode());
			}
			if(this.get(i).getCalDate() == null || this.get(i).getCalDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getCalDate()));
			}
			pstmt.setDouble(63, this.get(i).getCalCount());
			if(this.get(i).getBranchAttr() == null || this.get(i).getBranchAttr().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getBranchAttr());
			}
			pstmt.setDouble(65, this.get(i).getStandFYCRate());
			pstmt.setDouble(66, this.get(i).getFYCRate());
			pstmt.setDouble(67, this.get(i).getFYC());
			pstmt.setDouble(68, this.get(i).getGrpFYC());
			pstmt.setDouble(69, this.get(i).getDepFYC());
			pstmt.setDouble(70, this.get(i).getStandPrem());
			pstmt.setDouble(71, this.get(i).getCommCharge());
			pstmt.setDouble(72, this.get(i).getCommCharge1());
			pstmt.setDouble(73, this.get(i).getCommCharge2());
			pstmt.setDouble(74, this.get(i).getCommCharge3());
			pstmt.setDouble(75, this.get(i).getCommCharge4());
			pstmt.setDouble(76, this.get(i).getGrpFYCRate());
			pstmt.setDouble(77, this.get(i).getDepFYCRate());
			pstmt.setDouble(78, this.get(i).getStandPremRate());
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getPolType());
			}
			pstmt.setDouble(80, this.get(i).getP1());
			pstmt.setDouble(81, this.get(i).getP2());
			pstmt.setDouble(82, this.get(i).getP3());
			pstmt.setDouble(83, this.get(i).getP4());
			pstmt.setDouble(84, this.get(i).getP5());
			pstmt.setDouble(85, this.get(i).getP6());
			pstmt.setDouble(86, this.get(i).getP7());
			pstmt.setDouble(87, this.get(i).getP8());
			pstmt.setDouble(88, this.get(i).getP9());
			pstmt.setDouble(89, this.get(i).getP10());
			if(this.get(i).getP11() == null || this.get(i).getP11().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getP11());
			}
			if(this.get(i).getP12() == null || this.get(i).getP12().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getP12());
			}
			if(this.get(i).getP13() == null || this.get(i).getP13().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getP13());
			}
			if(this.get(i).getP14() == null || this.get(i).getP14().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getP14());
			}
			if(this.get(i).getP15() == null || this.get(i).getP15().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getP15());
			}
			if(this.get(i).getMakePolDate() == null || this.get(i).getMakePolDate().equals("null")) {
				pstmt.setDate(95,null);
			} else {
				pstmt.setDate(95, Date.valueOf(this.get(i).getMakePolDate()));
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(96,null);
			} else {
				pstmt.setDate(96, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getbranchattr1() == null || this.get(i).getbranchattr1().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getbranchattr1());
			}
			if(this.get(i).getbranchcode1() == null || this.get(i).getbranchcode1().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getbranchcode1());
			}
			if(this.get(i).getriskmark() == null || this.get(i).getriskmark().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getriskmark());
			}
			if(this.get(i).getscanDate() == null || this.get(i).getscanDate().equals("null")) {
				pstmt.setDate(100,null);
			} else {
				pstmt.setDate(100, Date.valueOf(this.get(i).getscanDate()));
			}
			if(this.get(i).getAgentGroup2() == null || this.get(i).getAgentGroup2().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getAgentGroup2());
			}
			if(this.get(i).getAgentGroup3() == null || this.get(i).getAgentGroup3().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getAgentGroup3());
			}
			if(this.get(i).getAgentGroup4() == null || this.get(i).getAgentGroup4().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getAgentGroup4());
			}
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(105, this.get(i).getInsuYear());
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getGrpContNo());
			}
			// set where condition
			if(this.get(i).getCommisionSN() == null || this.get(i).getCommisionSN().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getCommisionSN());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LACommision");
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
			tError.moduleName = "LACommisionDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LACommision(CommisionSN ,WageNo ,ContNo ,GrpPolNo ,MainPolNo ,PolNo ,ManageCom ,AppntNo ,RiskCode ,RiskVersion ,DutyCode ,PayPlanCode ,CValiDate ,PayIntv ,PayMode ,ReceiptNo ,TPayDate ,TEnterAccDate ,TConfDate ,TMakeDate ,CalcDate ,CommDate ,TransMoney ,TransStandMoney ,LastPayToDate ,CurPayToDate ,TransType ,TransState ,CommDire ,DirectWage ,AppendWage ,F1 ,F2 ,F3 ,F4 ,F5 ,K1 ,K2 ,K3 ,K4 ,K5 ,AgentCode ,AgentGroup ,AgentCode1 ,AgentGroup1 ,Flag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,PayYear ,PayYears ,Years ,PayCount ,SignDate ,GetPolDate ,BranchType ,AgentCom ,AgentType ,BranchCode ,CalDate ,CalCount ,BranchAttr ,StandFYCRate ,FYCRate ,FYC ,GrpFYC ,DepFYC ,StandPrem ,CommCharge ,CommCharge1 ,CommCharge2 ,CommCharge3 ,CommCharge4 ,GrpFYCRate ,DepFYCRate ,StandPremRate ,PolType ,P1 ,P2 ,P3 ,P4 ,P5 ,P6 ,P7 ,P8 ,P9 ,P10 ,P11 ,P12 ,P13 ,P14 ,P15 ,MakePolDate ,CustomGetPolDate ,branchattr1 ,branchcode1 ,riskmark ,scanDate ,AgentGroup2 ,AgentGroup3 ,AgentGroup4 ,InsuYearFlag ,InsuYear ,GrpContNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCommisionSN() == null || this.get(i).getCommisionSN().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCommisionSN());
			}
			if(this.get(i).getWageNo() == null || this.get(i).getWageNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getWageNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getMainPolNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPolNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getManageCom());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppntNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskVersion());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDutyCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(14, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPayMode());
			}
			if(this.get(i).getReceiptNo() == null || this.get(i).getReceiptNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getReceiptNo());
			}
			if(this.get(i).getTPayDate() == null || this.get(i).getTPayDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getTPayDate()));
			}
			if(this.get(i).getTEnterAccDate() == null || this.get(i).getTEnterAccDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getTEnterAccDate()));
			}
			if(this.get(i).getTConfDate() == null || this.get(i).getTConfDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getTConfDate()));
			}
			if(this.get(i).getTMakeDate() == null || this.get(i).getTMakeDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getTMakeDate()));
			}
			if(this.get(i).getCalcDate() == null || this.get(i).getCalcDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getCalcDate()));
			}
			if(this.get(i).getCommDate() == null || this.get(i).getCommDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getCommDate()));
			}
			pstmt.setDouble(23, this.get(i).getTransMoney());
			pstmt.setDouble(24, this.get(i).getTransStandMoney());
			if(this.get(i).getLastPayToDate() == null || this.get(i).getLastPayToDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getLastPayToDate()));
			}
			if(this.get(i).getCurPayToDate() == null || this.get(i).getCurPayToDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getCurPayToDate()));
			}
			if(this.get(i).getTransType() == null || this.get(i).getTransType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getTransType());
			}
			if(this.get(i).getTransState() == null || this.get(i).getTransState().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getTransState());
			}
			if(this.get(i).getCommDire() == null || this.get(i).getCommDire().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCommDire());
			}
			pstmt.setDouble(30, this.get(i).getDirectWage());
			pstmt.setDouble(31, this.get(i).getAppendWage());
			if(this.get(i).getF1() == null || this.get(i).getF1().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getF1());
			}
			if(this.get(i).getF2() == null || this.get(i).getF2().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getF2());
			}
			if(this.get(i).getF3() == null || this.get(i).getF3().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getF3());
			}
			if(this.get(i).getF4() == null || this.get(i).getF4().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getF4());
			}
			if(this.get(i).getF5() == null || this.get(i).getF5().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getF5());
			}
			pstmt.setDouble(37, this.get(i).getK1());
			pstmt.setDouble(38, this.get(i).getK2());
			pstmt.setDouble(39, this.get(i).getK3());
			pstmt.setDouble(40, this.get(i).getK4());
			pstmt.setDouble(41, this.get(i).getK5());
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getAgentCode1());
			}
			if(this.get(i).getAgentGroup1() == null || this.get(i).getAgentGroup1().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getAgentGroup1());
			}
			if(this.get(i).getFlag() == null || this.get(i).getFlag().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getFlag());
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
			pstmt.setInt(52, this.get(i).getPayYear());
			pstmt.setInt(53, this.get(i).getPayYears());
			pstmt.setInt(54, this.get(i).getYears());
			pstmt.setInt(55, this.get(i).getPayCount());
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(56,null);
			} else {
				pstmt.setDate(56, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getBranchType());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getAgentType());
			}
			if(this.get(i).getBranchCode() == null || this.get(i).getBranchCode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getBranchCode());
			}
			if(this.get(i).getCalDate() == null || this.get(i).getCalDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getCalDate()));
			}
			pstmt.setDouble(63, this.get(i).getCalCount());
			if(this.get(i).getBranchAttr() == null || this.get(i).getBranchAttr().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getBranchAttr());
			}
			pstmt.setDouble(65, this.get(i).getStandFYCRate());
			pstmt.setDouble(66, this.get(i).getFYCRate());
			pstmt.setDouble(67, this.get(i).getFYC());
			pstmt.setDouble(68, this.get(i).getGrpFYC());
			pstmt.setDouble(69, this.get(i).getDepFYC());
			pstmt.setDouble(70, this.get(i).getStandPrem());
			pstmt.setDouble(71, this.get(i).getCommCharge());
			pstmt.setDouble(72, this.get(i).getCommCharge1());
			pstmt.setDouble(73, this.get(i).getCommCharge2());
			pstmt.setDouble(74, this.get(i).getCommCharge3());
			pstmt.setDouble(75, this.get(i).getCommCharge4());
			pstmt.setDouble(76, this.get(i).getGrpFYCRate());
			pstmt.setDouble(77, this.get(i).getDepFYCRate());
			pstmt.setDouble(78, this.get(i).getStandPremRate());
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getPolType());
			}
			pstmt.setDouble(80, this.get(i).getP1());
			pstmt.setDouble(81, this.get(i).getP2());
			pstmt.setDouble(82, this.get(i).getP3());
			pstmt.setDouble(83, this.get(i).getP4());
			pstmt.setDouble(84, this.get(i).getP5());
			pstmt.setDouble(85, this.get(i).getP6());
			pstmt.setDouble(86, this.get(i).getP7());
			pstmt.setDouble(87, this.get(i).getP8());
			pstmt.setDouble(88, this.get(i).getP9());
			pstmt.setDouble(89, this.get(i).getP10());
			if(this.get(i).getP11() == null || this.get(i).getP11().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getP11());
			}
			if(this.get(i).getP12() == null || this.get(i).getP12().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getP12());
			}
			if(this.get(i).getP13() == null || this.get(i).getP13().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getP13());
			}
			if(this.get(i).getP14() == null || this.get(i).getP14().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getP14());
			}
			if(this.get(i).getP15() == null || this.get(i).getP15().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getP15());
			}
			if(this.get(i).getMakePolDate() == null || this.get(i).getMakePolDate().equals("null")) {
				pstmt.setDate(95,null);
			} else {
				pstmt.setDate(95, Date.valueOf(this.get(i).getMakePolDate()));
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(96,null);
			} else {
				pstmt.setDate(96, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getbranchattr1() == null || this.get(i).getbranchattr1().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getbranchattr1());
			}
			if(this.get(i).getbranchcode1() == null || this.get(i).getbranchcode1().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getbranchcode1());
			}
			if(this.get(i).getriskmark() == null || this.get(i).getriskmark().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getriskmark());
			}
			if(this.get(i).getscanDate() == null || this.get(i).getscanDate().equals("null")) {
				pstmt.setDate(100,null);
			} else {
				pstmt.setDate(100, Date.valueOf(this.get(i).getscanDate()));
			}
			if(this.get(i).getAgentGroup2() == null || this.get(i).getAgentGroup2().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getAgentGroup2());
			}
			if(this.get(i).getAgentGroup3() == null || this.get(i).getAgentGroup3().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getAgentGroup3());
			}
			if(this.get(i).getAgentGroup4() == null || this.get(i).getAgentGroup4().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getAgentGroup4());
			}
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(105, this.get(i).getInsuYear());
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getGrpContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LACommision");
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
			tError.moduleName = "LACommisionDBSet";
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
