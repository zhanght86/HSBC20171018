/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LACommisionSchema;
import com.sinosoft.lis.vschema.LACommisionSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LACommisionDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LACommisionDB extends LACommisionSchema
{
private static Logger logger = Logger.getLogger(LACommisionDB.class);

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
	public LACommisionDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LACommision" );
		mflag = true;
	}

	public LACommisionDB()
	{
		con = null;
		db = new DBOper( "LACommision" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LACommisionSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LACommisionSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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
			pstmt = con.prepareStatement("DELETE FROM LACommision WHERE  CommisionSN = ?");
			if(this.getCommisionSN() == null || this.getCommisionSN().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCommisionSN());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LACommision");
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
		SQLString sqlObj = new SQLString("LACommision");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LACommision SET  CommisionSN = ? , WageNo = ? , ContNo = ? , GrpPolNo = ? , MainPolNo = ? , PolNo = ? , ManageCom = ? , AppntNo = ? , RiskCode = ? , RiskVersion = ? , DutyCode = ? , PayPlanCode = ? , CValiDate = ? , PayIntv = ? , PayMode = ? , ReceiptNo = ? , TPayDate = ? , TEnterAccDate = ? , TConfDate = ? , TMakeDate = ? , CalcDate = ? , CommDate = ? , TransMoney = ? , TransStandMoney = ? , LastPayToDate = ? , CurPayToDate = ? , TransType = ? , TransState = ? , CommDire = ? , DirectWage = ? , AppendWage = ? , F1 = ? , F2 = ? , F3 = ? , F4 = ? , F5 = ? , K1 = ? , K2 = ? , K3 = ? , K4 = ? , K5 = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , AgentGroup1 = ? , Flag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , PayYear = ? , PayYears = ? , Years = ? , PayCount = ? , SignDate = ? , GetPolDate = ? , BranchType = ? , AgentCom = ? , AgentType = ? , BranchCode = ? , CalDate = ? , CalCount = ? , BranchAttr = ? , StandFYCRate = ? , FYCRate = ? , FYC = ? , GrpFYC = ? , DepFYC = ? , StandPrem = ? , CommCharge = ? , CommCharge1 = ? , CommCharge2 = ? , CommCharge3 = ? , CommCharge4 = ? , GrpFYCRate = ? , DepFYCRate = ? , StandPremRate = ? , PolType = ? , P1 = ? , P2 = ? , P3 = ? , P4 = ? , P5 = ? , P6 = ? , P7 = ? , P8 = ? , P9 = ? , P10 = ? , P11 = ? , P12 = ? , P13 = ? , P14 = ? , P15 = ? , MakePolDate = ? , CustomGetPolDate = ? , branchattr1 = ? , branchcode1 = ? , riskmark = ? , scanDate = ? , AgentGroup2 = ? , AgentGroup3 = ? , AgentGroup4 = ? , InsuYearFlag = ? , InsuYear = ? , GrpContNo = ? WHERE  CommisionSN = ?");
			if(this.getCommisionSN() == null || this.getCommisionSN().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCommisionSN());
			}
			if(this.getWageNo() == null || this.getWageNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getWageNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getMainPolNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPolNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getManageCom());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAppntNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRiskVersion());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPayPlanCode());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(14, this.getPayIntv());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getPayMode());
			}
			if(this.getReceiptNo() == null || this.getReceiptNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getReceiptNo());
			}
			if(this.getTPayDate() == null || this.getTPayDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getTPayDate()));
			}
			if(this.getTEnterAccDate() == null || this.getTEnterAccDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getTEnterAccDate()));
			}
			if(this.getTConfDate() == null || this.getTConfDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getTConfDate()));
			}
			if(this.getTMakeDate() == null || this.getTMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getTMakeDate()));
			}
			if(this.getCalcDate() == null || this.getCalcDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getCalcDate()));
			}
			if(this.getCommDate() == null || this.getCommDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getCommDate()));
			}
			pstmt.setDouble(23, this.getTransMoney());
			pstmt.setDouble(24, this.getTransStandMoney());
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getTransType());
			}
			if(this.getTransState() == null || this.getTransState().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getTransState());
			}
			if(this.getCommDire() == null || this.getCommDire().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getCommDire());
			}
			pstmt.setDouble(30, this.getDirectWage());
			pstmt.setDouble(31, this.getAppendWage());
			if(this.getF1() == null || this.getF1().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getF1());
			}
			if(this.getF2() == null || this.getF2().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getF2());
			}
			if(this.getF3() == null || this.getF3().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getF3());
			}
			if(this.getF4() == null || this.getF4().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getF4());
			}
			if(this.getF5() == null || this.getF5().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getF5());
			}
			pstmt.setDouble(37, this.getK1());
			pstmt.setDouble(38, this.getK2());
			pstmt.setDouble(39, this.getK3());
			pstmt.setDouble(40, this.getK4());
			pstmt.setDouble(41, this.getK5());
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAgentCode1());
			}
			if(this.getAgentGroup1() == null || this.getAgentGroup1().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getAgentGroup1());
			}
			if(this.getFlag() == null || this.getFlag().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getFlag());
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
			pstmt.setInt(52, this.getPayYear());
			pstmt.setInt(53, this.getPayYears());
			pstmt.setInt(54, this.getYears());
			pstmt.setInt(55, this.getPayCount());
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(56, 91);
			} else {
				pstmt.setDate(56, Date.valueOf(this.getSignDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getBranchType());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAgentType());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getBranchCode());
			}
			if(this.getCalDate() == null || this.getCalDate().equals("null")) {
				pstmt.setNull(62, 91);
			} else {
				pstmt.setDate(62, Date.valueOf(this.getCalDate()));
			}
			pstmt.setDouble(63, this.getCalCount());
			if(this.getBranchAttr() == null || this.getBranchAttr().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getBranchAttr());
			}
			pstmt.setDouble(65, this.getStandFYCRate());
			pstmt.setDouble(66, this.getFYCRate());
			pstmt.setDouble(67, this.getFYC());
			pstmt.setDouble(68, this.getGrpFYC());
			pstmt.setDouble(69, this.getDepFYC());
			pstmt.setDouble(70, this.getStandPrem());
			pstmt.setDouble(71, this.getCommCharge());
			pstmt.setDouble(72, this.getCommCharge1());
			pstmt.setDouble(73, this.getCommCharge2());
			pstmt.setDouble(74, this.getCommCharge3());
			pstmt.setDouble(75, this.getCommCharge4());
			pstmt.setDouble(76, this.getGrpFYCRate());
			pstmt.setDouble(77, this.getDepFYCRate());
			pstmt.setDouble(78, this.getStandPremRate());
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(79, 1);
			} else {
				pstmt.setString(79, this.getPolType());
			}
			pstmt.setDouble(80, this.getP1());
			pstmt.setDouble(81, this.getP2());
			pstmt.setDouble(82, this.getP3());
			pstmt.setDouble(83, this.getP4());
			pstmt.setDouble(84, this.getP5());
			pstmt.setDouble(85, this.getP6());
			pstmt.setDouble(86, this.getP7());
			pstmt.setDouble(87, this.getP8());
			pstmt.setDouble(88, this.getP9());
			pstmt.setDouble(89, this.getP10());
			if(this.getP11() == null || this.getP11().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getP11());
			}
			if(this.getP12() == null || this.getP12().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getP12());
			}
			if(this.getP13() == null || this.getP13().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getP13());
			}
			if(this.getP14() == null || this.getP14().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getP14());
			}
			if(this.getP15() == null || this.getP15().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getP15());
			}
			if(this.getMakePolDate() == null || this.getMakePolDate().equals("null")) {
				pstmt.setNull(95, 91);
			} else {
				pstmt.setDate(95, Date.valueOf(this.getMakePolDate()));
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(96, 91);
			} else {
				pstmt.setDate(96, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getbranchattr1() == null || this.getbranchattr1().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getbranchattr1());
			}
			if(this.getbranchcode1() == null || this.getbranchcode1().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getbranchcode1());
			}
			if(this.getriskmark() == null || this.getriskmark().equals("null")) {
				pstmt.setNull(99, 1);
			} else {
				pstmt.setString(99, this.getriskmark());
			}
			if(this.getscanDate() == null || this.getscanDate().equals("null")) {
				pstmt.setNull(100, 91);
			} else {
				pstmt.setDate(100, Date.valueOf(this.getscanDate()));
			}
			if(this.getAgentGroup2() == null || this.getAgentGroup2().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getAgentGroup2());
			}
			if(this.getAgentGroup3() == null || this.getAgentGroup3().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getAgentGroup3());
			}
			if(this.getAgentGroup4() == null || this.getAgentGroup4().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getAgentGroup4());
			}
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(104, 1);
			} else {
				pstmt.setString(104, this.getInsuYearFlag());
			}
			pstmt.setInt(105, this.getInsuYear());
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getGrpContNo());
			}
			// set where condition
			if(this.getCommisionSN() == null || this.getCommisionSN().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getCommisionSN());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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
		SQLString sqlObj = new SQLString("LACommision");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LACommision(CommisionSN ,WageNo ,ContNo ,GrpPolNo ,MainPolNo ,PolNo ,ManageCom ,AppntNo ,RiskCode ,RiskVersion ,DutyCode ,PayPlanCode ,CValiDate ,PayIntv ,PayMode ,ReceiptNo ,TPayDate ,TEnterAccDate ,TConfDate ,TMakeDate ,CalcDate ,CommDate ,TransMoney ,TransStandMoney ,LastPayToDate ,CurPayToDate ,TransType ,TransState ,CommDire ,DirectWage ,AppendWage ,F1 ,F2 ,F3 ,F4 ,F5 ,K1 ,K2 ,K3 ,K4 ,K5 ,AgentCode ,AgentGroup ,AgentCode1 ,AgentGroup1 ,Flag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,PayYear ,PayYears ,Years ,PayCount ,SignDate ,GetPolDate ,BranchType ,AgentCom ,AgentType ,BranchCode ,CalDate ,CalCount ,BranchAttr ,StandFYCRate ,FYCRate ,FYC ,GrpFYC ,DepFYC ,StandPrem ,CommCharge ,CommCharge1 ,CommCharge2 ,CommCharge3 ,CommCharge4 ,GrpFYCRate ,DepFYCRate ,StandPremRate ,PolType ,P1 ,P2 ,P3 ,P4 ,P5 ,P6 ,P7 ,P8 ,P9 ,P10 ,P11 ,P12 ,P13 ,P14 ,P15 ,MakePolDate ,CustomGetPolDate ,branchattr1 ,branchcode1 ,riskmark ,scanDate ,AgentGroup2 ,AgentGroup3 ,AgentGroup4 ,InsuYearFlag ,InsuYear ,GrpContNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getCommisionSN() == null || this.getCommisionSN().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCommisionSN());
			}
			if(this.getWageNo() == null || this.getWageNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getWageNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getMainPolNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPolNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getManageCom());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAppntNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRiskVersion());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPayPlanCode());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(14, this.getPayIntv());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getPayMode());
			}
			if(this.getReceiptNo() == null || this.getReceiptNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getReceiptNo());
			}
			if(this.getTPayDate() == null || this.getTPayDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getTPayDate()));
			}
			if(this.getTEnterAccDate() == null || this.getTEnterAccDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getTEnterAccDate()));
			}
			if(this.getTConfDate() == null || this.getTConfDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getTConfDate()));
			}
			if(this.getTMakeDate() == null || this.getTMakeDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getTMakeDate()));
			}
			if(this.getCalcDate() == null || this.getCalcDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getCalcDate()));
			}
			if(this.getCommDate() == null || this.getCommDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getCommDate()));
			}
			pstmt.setDouble(23, this.getTransMoney());
			pstmt.setDouble(24, this.getTransStandMoney());
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getTransType() == null || this.getTransType().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getTransType());
			}
			if(this.getTransState() == null || this.getTransState().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getTransState());
			}
			if(this.getCommDire() == null || this.getCommDire().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getCommDire());
			}
			pstmt.setDouble(30, this.getDirectWage());
			pstmt.setDouble(31, this.getAppendWage());
			if(this.getF1() == null || this.getF1().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getF1());
			}
			if(this.getF2() == null || this.getF2().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getF2());
			}
			if(this.getF3() == null || this.getF3().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getF3());
			}
			if(this.getF4() == null || this.getF4().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getF4());
			}
			if(this.getF5() == null || this.getF5().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getF5());
			}
			pstmt.setDouble(37, this.getK1());
			pstmt.setDouble(38, this.getK2());
			pstmt.setDouble(39, this.getK3());
			pstmt.setDouble(40, this.getK4());
			pstmt.setDouble(41, this.getK5());
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAgentCode1());
			}
			if(this.getAgentGroup1() == null || this.getAgentGroup1().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getAgentGroup1());
			}
			if(this.getFlag() == null || this.getFlag().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getFlag());
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
			pstmt.setInt(52, this.getPayYear());
			pstmt.setInt(53, this.getPayYears());
			pstmt.setInt(54, this.getYears());
			pstmt.setInt(55, this.getPayCount());
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(56, 91);
			} else {
				pstmt.setDate(56, Date.valueOf(this.getSignDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getBranchType());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAgentType());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getBranchCode());
			}
			if(this.getCalDate() == null || this.getCalDate().equals("null")) {
				pstmt.setNull(62, 91);
			} else {
				pstmt.setDate(62, Date.valueOf(this.getCalDate()));
			}
			pstmt.setDouble(63, this.getCalCount());
			if(this.getBranchAttr() == null || this.getBranchAttr().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getBranchAttr());
			}
			pstmt.setDouble(65, this.getStandFYCRate());
			pstmt.setDouble(66, this.getFYCRate());
			pstmt.setDouble(67, this.getFYC());
			pstmt.setDouble(68, this.getGrpFYC());
			pstmt.setDouble(69, this.getDepFYC());
			pstmt.setDouble(70, this.getStandPrem());
			pstmt.setDouble(71, this.getCommCharge());
			pstmt.setDouble(72, this.getCommCharge1());
			pstmt.setDouble(73, this.getCommCharge2());
			pstmt.setDouble(74, this.getCommCharge3());
			pstmt.setDouble(75, this.getCommCharge4());
			pstmt.setDouble(76, this.getGrpFYCRate());
			pstmt.setDouble(77, this.getDepFYCRate());
			pstmt.setDouble(78, this.getStandPremRate());
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(79, 1);
			} else {
				pstmt.setString(79, this.getPolType());
			}
			pstmt.setDouble(80, this.getP1());
			pstmt.setDouble(81, this.getP2());
			pstmt.setDouble(82, this.getP3());
			pstmt.setDouble(83, this.getP4());
			pstmt.setDouble(84, this.getP5());
			pstmt.setDouble(85, this.getP6());
			pstmt.setDouble(86, this.getP7());
			pstmt.setDouble(87, this.getP8());
			pstmt.setDouble(88, this.getP9());
			pstmt.setDouble(89, this.getP10());
			if(this.getP11() == null || this.getP11().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getP11());
			}
			if(this.getP12() == null || this.getP12().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getP12());
			}
			if(this.getP13() == null || this.getP13().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getP13());
			}
			if(this.getP14() == null || this.getP14().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getP14());
			}
			if(this.getP15() == null || this.getP15().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getP15());
			}
			if(this.getMakePolDate() == null || this.getMakePolDate().equals("null")) {
				pstmt.setNull(95, 91);
			} else {
				pstmt.setDate(95, Date.valueOf(this.getMakePolDate()));
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(96, 91);
			} else {
				pstmt.setDate(96, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getbranchattr1() == null || this.getbranchattr1().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getbranchattr1());
			}
			if(this.getbranchcode1() == null || this.getbranchcode1().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getbranchcode1());
			}
			if(this.getriskmark() == null || this.getriskmark().equals("null")) {
				pstmt.setNull(99, 1);
			} else {
				pstmt.setString(99, this.getriskmark());
			}
			if(this.getscanDate() == null || this.getscanDate().equals("null")) {
				pstmt.setNull(100, 91);
			} else {
				pstmt.setDate(100, Date.valueOf(this.getscanDate()));
			}
			if(this.getAgentGroup2() == null || this.getAgentGroup2().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getAgentGroup2());
			}
			if(this.getAgentGroup3() == null || this.getAgentGroup3().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getAgentGroup3());
			}
			if(this.getAgentGroup4() == null || this.getAgentGroup4().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getAgentGroup4());
			}
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(104, 1);
			} else {
				pstmt.setString(104, this.getInsuYearFlag());
			}
			pstmt.setInt(105, this.getInsuYear());
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getGrpContNo());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LACommision WHERE  CommisionSN = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getCommisionSN() == null || this.getCommisionSN().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCommisionSN());
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
					tError.moduleName = "LACommisionDB";
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
			tError.moduleName = "LACommisionDB";
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

	public LACommisionSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LACommisionSet aLACommisionSet = new LACommisionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LACommision");
			LACommisionSchema aSchema = this.getSchema();
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
				LACommisionSchema s1 = new LACommisionSchema();
				s1.setSchema(rs,i);
				aLACommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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

		return aLACommisionSet;

	}

	public LACommisionSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LACommisionSet aLACommisionSet = new LACommisionSet();

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
				LACommisionSchema s1 = new LACommisionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LACommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLACommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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

		return aLACommisionSet;
	}

	public LACommisionSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LACommisionSet aLACommisionSet = new LACommisionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LACommision");
			LACommisionSchema aSchema = this.getSchema();
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

				LACommisionSchema s1 = new LACommisionSchema();
				s1.setSchema(rs,i);
				aLACommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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

		return aLACommisionSet;

	}

	public LACommisionSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LACommisionSet aLACommisionSet = new LACommisionSet();

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

				LACommisionSchema s1 = new LACommisionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LACommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLACommisionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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

		return aLACommisionSet;
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
			SQLString sqlObj = new SQLString("LACommision");
			LACommisionSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LACommision " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LACommisionDB";
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
			tError.moduleName = "LACommisionDB";
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
        tError.moduleName = "LACommisionDB";
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
        tError.moduleName = "LACommisionDB";
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
        tError.moduleName = "LACommisionDB";
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
        tError.moduleName = "LACommisionDB";
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
 * @return LACommisionSet
 */
public LACommisionSet getData()
{
    int tCount = 0;
    LACommisionSet tLACommisionSet = new LACommisionSet();
    LACommisionSchema tLACommisionSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LACommisionDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLACommisionSchema = new LACommisionSchema();
        tLACommisionSchema.setSchema(mResultSet, 1);
        tLACommisionSet.add(tLACommisionSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLACommisionSchema = new LACommisionSchema();
                tLACommisionSchema.setSchema(mResultSet, 1);
                tLACommisionSet.add(tLACommisionSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LACommisionDB";
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
    return tLACommisionSet;
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
            tError.moduleName = "LACommisionDB";
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
        tError.moduleName = "LACommisionDB";
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
            tError.moduleName = "LACommisionDB";
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
        tError.moduleName = "LACommisionDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LACommisionSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LACommisionSet aLACommisionSet = new LACommisionSet();

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
				LACommisionSchema s1 = new LACommisionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LACommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLACommisionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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

		return aLACommisionSet;
	}

	public LACommisionSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LACommisionSet aLACommisionSet = new LACommisionSet();

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

				LACommisionSchema s1 = new LACommisionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LACommisionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLACommisionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionDB";
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

		return aLACommisionSet; 
	}

}
