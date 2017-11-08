/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOPolSchema;
import com.sinosoft.lis.vschema.LBPOPolSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOPolDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPOPolDB extends LBPOPolSchema
{
private static Logger logger = Logger.getLogger(LBPOPolDB.class);

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
	public LBPOPolDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBPOPol" );
		mflag = true;
	}

	public LBPOPolDB()
	{
		con = null;
		db = new DBOper( "LBPOPol" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBPOPolSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBPOPolSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOPol WHERE  InputNo = ? AND PolNo = ? AND FillNo = ?");
			pstmt.setInt(1, this.getInputNo());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPolNo());
			}
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFillNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOPol");
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
		SQLString sqlObj = new SQLString("LBPOPol");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBPOPol SET  GrpContNo = ? , GrpPolNo = ? , ContNo = ? , InputNo = ? , PolNo = ? , FillNo = ? , ProposalNo = ? , PrtNo = ? , ContType = ? , PolTypeFlag = ? , MainPolNo = ? , MasterPolNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , SaleChnl = ? , Handler = ? , InsuredNo = ? , InsuredPeoples = ? , AppntNo = ? , CValiDate = ? , SignCom = ? , SignDate = ? , SignTime = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , GetStartDate = ? , EndDate = ? , AcciEndDate = ? , GetYearFlag = ? , GetYear = ? , PayEndYearFlag = ? , PayEndYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , GetStartType = ? , SpecifyValiDate = ? , PayMode = ? , PayLocation = ? , PayIntv = ? , PayYears = ? , Years = ? , ManageFeeRate = ? , FloatRate = ? , PremToAmnt = ? , Mult = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , LeavingMoney = ? , EndorseTimes = ? , ClaimTimes = ? , LiveTimes = ? , RenewCount = ? , LastGetDate = ? , LastLoanDate = ? , LastRegetDate = ? , LastEdorDate = ? , LastRevDate = ? , RnewFlag = ? , StopFlag = ? , ExpiryFlag = ? , AutoPayFlag = ? , InterestDifFlag = ? , SubFlag = ? , BnfFlag = ? , HealthCheckFlag = ? , ImpartFlag = ? , ReinsureFlag = ? , AgentPayFlag = ? , AgentGetFlag = ? , LiveGetMode = ? , DeadGetMode = ? , BonusGetMode = ? , BonusMan = ? , DeadFlag = ? , SmokeFlag = ? , Remark = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , PolApplyDate = ? , AppFlag = ? , PolState = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , WaitPeriod = ? , GetForm = ? , GetBankCode = ? , GetBankAccNo = ? , GetAccName = ? , KeepValueOpt = ? , PayRuleCode = ? , AscriptionRuleCode = ? , AutoPubAccFlag = ? , RiskSequence = ? , GetLimit = ? WHERE  InputNo = ? AND PolNo = ? AND FillNo = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			pstmt.setInt(4, this.getInputNo());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPolNo());
			}
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getFillNo());
			}
			if(this.getProposalNo() == null || this.getProposalNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getContType());
			}
			if(this.getPolTypeFlag() == null || this.getPolTypeFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPolTypeFlag());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getMainPolNo());
			}
			if(this.getMasterPolNo() == null || this.getMasterPolNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getMasterPolNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRiskVersion());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAgentCode1());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getHandler());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getInsuredNo());
			}
			if(this.getInsuredPeoples() == null || this.getInsuredPeoples().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getInsuredPeoples());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAppntNo());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getCValiDate());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getSignDate());
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getSignTime());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getFirstPayDate());
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getPayEndDate());
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getPaytoDate());
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getGetStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getEndDate());
			}
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAcciEndDate());
			}
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGetYearFlag());
			}
			if(this.getGetYear() == null || this.getGetYear().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getGetYear());
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getPayEndYearFlag());
			}
			if(this.getPayEndYear() == null || this.getPayEndYear().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPayEndYear());
			}
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getInsuYearFlag());
			}
			if(this.getInsuYear() == null || this.getInsuYear().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getInsuYear());
			}
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getAcciYearFlag());
			}
			if(this.getAcciYear() == null || this.getAcciYear().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAcciYear());
			}
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getGetStartType());
			}
			if(this.getSpecifyValiDate() == null || this.getSpecifyValiDate().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getSpecifyValiDate());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getPayLocation());
			}
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getPayIntv());
			}
			if(this.getPayYears() == null || this.getPayYears().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getPayYears());
			}
			if(this.getYears() == null || this.getYears().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getYears());
			}
			if(this.getManageFeeRate() == null || this.getManageFeeRate().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getManageFeeRate());
			}
			if(this.getFloatRate() == null || this.getFloatRate().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getFloatRate());
			}
			if(this.getPremToAmnt() == null || this.getPremToAmnt().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getPremToAmnt());
			}
			if(this.getMult() == null || this.getMult().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getMult());
			}
			if(this.getStandPrem() == null || this.getStandPrem().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getStandPrem());
			}
			if(this.getPrem() == null || this.getPrem().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getPrem());
			}
			if(this.getSumPrem() == null || this.getSumPrem().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSumPrem());
			}
			if(this.getAmnt() == null || this.getAmnt().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAmnt());
			}
			if(this.getRiskAmnt() == null || this.getRiskAmnt().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getRiskAmnt());
			}
			if(this.getLeavingMoney() == null || this.getLeavingMoney().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getLeavingMoney());
			}
			if(this.getEndorseTimes() == null || this.getEndorseTimes().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getEndorseTimes());
			}
			if(this.getClaimTimes() == null || this.getClaimTimes().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getClaimTimes());
			}
			if(this.getLiveTimes() == null || this.getLiveTimes().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getLiveTimes());
			}
			if(this.getRenewCount() == null || this.getRenewCount().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRenewCount());
			}
			if(this.getLastGetDate() == null || this.getLastGetDate().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getLastGetDate());
			}
			if(this.getLastLoanDate() == null || this.getLastLoanDate().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getLastLoanDate());
			}
			if(this.getLastRegetDate() == null || this.getLastRegetDate().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getLastRegetDate());
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getLastEdorDate());
			}
			if(this.getLastRevDate() == null || this.getLastRevDate().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getLastRevDate());
			}
			if(this.getRnewFlag() == null || this.getRnewFlag().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getRnewFlag());
			}
			if(this.getStopFlag() == null || this.getStopFlag().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getStopFlag());
			}
			if(this.getExpiryFlag() == null || this.getExpiryFlag().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getExpiryFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getAutoPayFlag());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getInterestDifFlag());
			}
			if(this.getSubFlag() == null || this.getSubFlag().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getSubFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getBnfFlag());
			}
			if(this.getHealthCheckFlag() == null || this.getHealthCheckFlag().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getHealthCheckFlag());
			}
			if(this.getImpartFlag() == null || this.getImpartFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getImpartFlag());
			}
			if(this.getReinsureFlag() == null || this.getReinsureFlag().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getReinsureFlag());
			}
			if(this.getAgentPayFlag() == null || this.getAgentPayFlag().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getAgentPayFlag());
			}
			if(this.getAgentGetFlag() == null || this.getAgentGetFlag().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getAgentGetFlag());
			}
			if(this.getLiveGetMode() == null || this.getLiveGetMode().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getLiveGetMode());
			}
			if(this.getDeadGetMode() == null || this.getDeadGetMode().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getDeadGetMode());
			}
			if(this.getBonusGetMode() == null || this.getBonusGetMode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getBonusGetMode());
			}
			if(this.getBonusMan() == null || this.getBonusMan().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getBonusMan());
			}
			if(this.getDeadFlag() == null || this.getDeadFlag().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getDeadFlag());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getSmokeFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getRemark());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getApproveDate());
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getUWDate());
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getUWTime());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getPolApplyDate());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getAppFlag());
			}
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getPolState());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getMakeDate());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getModifyDate());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getModifyTime());
			}
			if(this.getWaitPeriod() == null || this.getWaitPeriod().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getWaitPeriod());
			}
			if(this.getGetForm() == null || this.getGetForm().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getGetForm());
			}
			if(this.getGetBankCode() == null || this.getGetBankCode().equals("null")) {
				pstmt.setNull(111, 12);
			} else {
				pstmt.setString(111, this.getGetBankCode());
			}
			if(this.getGetBankAccNo() == null || this.getGetBankAccNo().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getGetBankAccNo());
			}
			if(this.getGetAccName() == null || this.getGetAccName().equals("null")) {
				pstmt.setNull(113, 12);
			} else {
				pstmt.setString(113, this.getGetAccName());
			}
			if(this.getKeepValueOpt() == null || this.getKeepValueOpt().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getKeepValueOpt());
			}
			if(this.getPayRuleCode() == null || this.getPayRuleCode().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getPayRuleCode());
			}
			if(this.getAscriptionRuleCode() == null || this.getAscriptionRuleCode().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getAscriptionRuleCode());
			}
			if(this.getAutoPubAccFlag() == null || this.getAutoPubAccFlag().equals("null")) {
				pstmt.setNull(117, 12);
			} else {
				pstmt.setString(117, this.getAutoPubAccFlag());
			}
			if(this.getRiskSequence() == null || this.getRiskSequence().equals("null")) {
				pstmt.setNull(118, 12);
			} else {
				pstmt.setString(118, this.getRiskSequence());
			}
			if(this.getGetLimit() == null || this.getGetLimit().equals("null")) {
				pstmt.setNull(119, 12);
			} else {
				pstmt.setString(119, this.getGetLimit());
			}
			// set where condition
			pstmt.setInt(120, this.getInputNo());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getPolNo());
			}
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(122, 12);
			} else {
				pstmt.setString(122, this.getFillNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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
		SQLString sqlObj = new SQLString("LBPOPol");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBPOPol(GrpContNo ,GrpPolNo ,ContNo ,InputNo ,PolNo ,FillNo ,ProposalNo ,PrtNo ,ContType ,PolTypeFlag ,MainPolNo ,MasterPolNo ,KindCode ,RiskCode ,RiskVersion ,ManageCom ,AgentCom ,AgentType ,AgentCode ,AgentGroup ,AgentCode1 ,SaleChnl ,Handler ,InsuredNo ,InsuredPeoples ,AppntNo ,CValiDate ,SignCom ,SignDate ,SignTime ,FirstPayDate ,PayEndDate ,PaytoDate ,GetStartDate ,EndDate ,AcciEndDate ,GetYearFlag ,GetYear ,PayEndYearFlag ,PayEndYear ,InsuYearFlag ,InsuYear ,AcciYearFlag ,AcciYear ,GetStartType ,SpecifyValiDate ,PayMode ,PayLocation ,PayIntv ,PayYears ,Years ,ManageFeeRate ,FloatRate ,PremToAmnt ,Mult ,StandPrem ,Prem ,SumPrem ,Amnt ,RiskAmnt ,LeavingMoney ,EndorseTimes ,ClaimTimes ,LiveTimes ,RenewCount ,LastGetDate ,LastLoanDate ,LastRegetDate ,LastEdorDate ,LastRevDate ,RnewFlag ,StopFlag ,ExpiryFlag ,AutoPayFlag ,InterestDifFlag ,SubFlag ,BnfFlag ,HealthCheckFlag ,ImpartFlag ,ReinsureFlag ,AgentPayFlag ,AgentGetFlag ,LiveGetMode ,DeadGetMode ,BonusGetMode ,BonusMan ,DeadFlag ,SmokeFlag ,Remark ,ApproveFlag ,ApproveCode ,ApproveDate ,ApproveTime ,UWFlag ,UWCode ,UWDate ,UWTime ,PolApplyDate ,AppFlag ,PolState ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,WaitPeriod ,GetForm ,GetBankCode ,GetBankAccNo ,GetAccName ,KeepValueOpt ,PayRuleCode ,AscriptionRuleCode ,AutoPubAccFlag ,RiskSequence ,GetLimit) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			pstmt.setInt(4, this.getInputNo());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPolNo());
			}
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getFillNo());
			}
			if(this.getProposalNo() == null || this.getProposalNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getContType());
			}
			if(this.getPolTypeFlag() == null || this.getPolTypeFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPolTypeFlag());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getMainPolNo());
			}
			if(this.getMasterPolNo() == null || this.getMasterPolNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getMasterPolNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRiskVersion());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAgentCode1());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getHandler());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getInsuredNo());
			}
			if(this.getInsuredPeoples() == null || this.getInsuredPeoples().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getInsuredPeoples());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAppntNo());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getCValiDate());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getSignDate());
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getSignTime());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getFirstPayDate());
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getPayEndDate());
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getPaytoDate());
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getGetStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getEndDate());
			}
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAcciEndDate());
			}
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGetYearFlag());
			}
			if(this.getGetYear() == null || this.getGetYear().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getGetYear());
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getPayEndYearFlag());
			}
			if(this.getPayEndYear() == null || this.getPayEndYear().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPayEndYear());
			}
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getInsuYearFlag());
			}
			if(this.getInsuYear() == null || this.getInsuYear().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getInsuYear());
			}
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getAcciYearFlag());
			}
			if(this.getAcciYear() == null || this.getAcciYear().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAcciYear());
			}
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getGetStartType());
			}
			if(this.getSpecifyValiDate() == null || this.getSpecifyValiDate().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getSpecifyValiDate());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getPayLocation());
			}
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getPayIntv());
			}
			if(this.getPayYears() == null || this.getPayYears().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getPayYears());
			}
			if(this.getYears() == null || this.getYears().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getYears());
			}
			if(this.getManageFeeRate() == null || this.getManageFeeRate().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getManageFeeRate());
			}
			if(this.getFloatRate() == null || this.getFloatRate().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getFloatRate());
			}
			if(this.getPremToAmnt() == null || this.getPremToAmnt().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getPremToAmnt());
			}
			if(this.getMult() == null || this.getMult().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getMult());
			}
			if(this.getStandPrem() == null || this.getStandPrem().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getStandPrem());
			}
			if(this.getPrem() == null || this.getPrem().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getPrem());
			}
			if(this.getSumPrem() == null || this.getSumPrem().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSumPrem());
			}
			if(this.getAmnt() == null || this.getAmnt().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAmnt());
			}
			if(this.getRiskAmnt() == null || this.getRiskAmnt().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getRiskAmnt());
			}
			if(this.getLeavingMoney() == null || this.getLeavingMoney().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getLeavingMoney());
			}
			if(this.getEndorseTimes() == null || this.getEndorseTimes().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getEndorseTimes());
			}
			if(this.getClaimTimes() == null || this.getClaimTimes().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getClaimTimes());
			}
			if(this.getLiveTimes() == null || this.getLiveTimes().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getLiveTimes());
			}
			if(this.getRenewCount() == null || this.getRenewCount().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRenewCount());
			}
			if(this.getLastGetDate() == null || this.getLastGetDate().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getLastGetDate());
			}
			if(this.getLastLoanDate() == null || this.getLastLoanDate().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getLastLoanDate());
			}
			if(this.getLastRegetDate() == null || this.getLastRegetDate().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getLastRegetDate());
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getLastEdorDate());
			}
			if(this.getLastRevDate() == null || this.getLastRevDate().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getLastRevDate());
			}
			if(this.getRnewFlag() == null || this.getRnewFlag().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getRnewFlag());
			}
			if(this.getStopFlag() == null || this.getStopFlag().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getStopFlag());
			}
			if(this.getExpiryFlag() == null || this.getExpiryFlag().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getExpiryFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getAutoPayFlag());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getInterestDifFlag());
			}
			if(this.getSubFlag() == null || this.getSubFlag().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getSubFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getBnfFlag());
			}
			if(this.getHealthCheckFlag() == null || this.getHealthCheckFlag().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getHealthCheckFlag());
			}
			if(this.getImpartFlag() == null || this.getImpartFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getImpartFlag());
			}
			if(this.getReinsureFlag() == null || this.getReinsureFlag().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getReinsureFlag());
			}
			if(this.getAgentPayFlag() == null || this.getAgentPayFlag().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getAgentPayFlag());
			}
			if(this.getAgentGetFlag() == null || this.getAgentGetFlag().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getAgentGetFlag());
			}
			if(this.getLiveGetMode() == null || this.getLiveGetMode().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getLiveGetMode());
			}
			if(this.getDeadGetMode() == null || this.getDeadGetMode().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getDeadGetMode());
			}
			if(this.getBonusGetMode() == null || this.getBonusGetMode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getBonusGetMode());
			}
			if(this.getBonusMan() == null || this.getBonusMan().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getBonusMan());
			}
			if(this.getDeadFlag() == null || this.getDeadFlag().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getDeadFlag());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getSmokeFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getRemark());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getApproveDate());
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getUWDate());
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getUWTime());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getPolApplyDate());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getAppFlag());
			}
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getPolState());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getMakeDate());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getModifyDate());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getModifyTime());
			}
			if(this.getWaitPeriod() == null || this.getWaitPeriod().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getWaitPeriod());
			}
			if(this.getGetForm() == null || this.getGetForm().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getGetForm());
			}
			if(this.getGetBankCode() == null || this.getGetBankCode().equals("null")) {
				pstmt.setNull(111, 12);
			} else {
				pstmt.setString(111, this.getGetBankCode());
			}
			if(this.getGetBankAccNo() == null || this.getGetBankAccNo().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getGetBankAccNo());
			}
			if(this.getGetAccName() == null || this.getGetAccName().equals("null")) {
				pstmt.setNull(113, 12);
			} else {
				pstmt.setString(113, this.getGetAccName());
			}
			if(this.getKeepValueOpt() == null || this.getKeepValueOpt().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getKeepValueOpt());
			}
			if(this.getPayRuleCode() == null || this.getPayRuleCode().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getPayRuleCode());
			}
			if(this.getAscriptionRuleCode() == null || this.getAscriptionRuleCode().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getAscriptionRuleCode());
			}
			if(this.getAutoPubAccFlag() == null || this.getAutoPubAccFlag().equals("null")) {
				pstmt.setNull(117, 12);
			} else {
				pstmt.setString(117, this.getAutoPubAccFlag());
			}
			if(this.getRiskSequence() == null || this.getRiskSequence().equals("null")) {
				pstmt.setNull(118, 12);
			} else {
				pstmt.setString(118, this.getRiskSequence());
			}
			if(this.getGetLimit() == null || this.getGetLimit().equals("null")) {
				pstmt.setNull(119, 12);
			} else {
				pstmt.setString(119, this.getGetLimit());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBPOPol WHERE  InputNo = ? AND PolNo = ? AND FillNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, this.getInputNo());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPolNo());
			}
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFillNo());
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
					tError.moduleName = "LBPOPolDB";
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
			tError.moduleName = "LBPOPolDB";
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

	public LBPOPolSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPOPolSet aLBPOPolSet = new LBPOPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOPol");
			LBPOPolSchema aSchema = this.getSchema();
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
				LBPOPolSchema s1 = new LBPOPolSchema();
				s1.setSchema(rs,i);
				aLBPOPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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

		return aLBPOPolSet;

	}

	public LBPOPolSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOPolSet aLBPOPolSet = new LBPOPolSet();

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
				LBPOPolSchema s1 = new LBPOPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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

		return aLBPOPolSet;
	}

	public LBPOPolSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPOPolSet aLBPOPolSet = new LBPOPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOPol");
			LBPOPolSchema aSchema = this.getSchema();
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

				LBPOPolSchema s1 = new LBPOPolSchema();
				s1.setSchema(rs,i);
				aLBPOPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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

		return aLBPOPolSet;

	}

	public LBPOPolSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOPolSet aLBPOPolSet = new LBPOPolSet();

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

				LBPOPolSchema s1 = new LBPOPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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

		return aLBPOPolSet;
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
			SQLString sqlObj = new SQLString("LBPOPol");
			LBPOPolSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBPOPol " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBPOPolDB";
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
			tError.moduleName = "LBPOPolDB";
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
        tError.moduleName = "LBPOPolDB";
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
        tError.moduleName = "LBPOPolDB";
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
        tError.moduleName = "LBPOPolDB";
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
        tError.moduleName = "LBPOPolDB";
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
 * @return LBPOPolSet
 */
public LBPOPolSet getData()
{
    int tCount = 0;
    LBPOPolSet tLBPOPolSet = new LBPOPolSet();
    LBPOPolSchema tLBPOPolSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOPolDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBPOPolSchema = new LBPOPolSchema();
        tLBPOPolSchema.setSchema(mResultSet, 1);
        tLBPOPolSet.add(tLBPOPolSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBPOPolSchema = new LBPOPolSchema();
                tLBPOPolSchema.setSchema(mResultSet, 1);
                tLBPOPolSet.add(tLBPOPolSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOPolDB";
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
    return tLBPOPolSet;
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
            tError.moduleName = "LBPOPolDB";
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
        tError.moduleName = "LBPOPolDB";
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
            tError.moduleName = "LBPOPolDB";
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
        tError.moduleName = "LBPOPolDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBPOPolSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOPolSet aLBPOPolSet = new LBPOPolSet();

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
				LBPOPolSchema s1 = new LBPOPolSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOPolSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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

		return aLBPOPolSet;
	}

	public LBPOPolSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOPolSet aLBPOPolSet = new LBPOPolSet();

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

				LBPOPolSchema s1 = new LBPOPolSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOPolSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOPolDB";
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

		return aLBPOPolSet; 
	}

}
