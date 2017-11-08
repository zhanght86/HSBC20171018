/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPolDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LBPolDB extends LBPolSchema
{
private static Logger logger = Logger.getLogger(LBPolDB.class);

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
	public LBPolDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBPol" );
		mflag = true;
	}

	public LBPolDB()
	{
		con = null;
		db = new DBOper( "LBPol" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBPolSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBPolSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBPol WHERE  EdorNo = ? AND PolNo = ?");
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
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPol");
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
		SQLString sqlObj = new SQLString("LBPol");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBPol SET  EdorNo = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , PolNo = ? , ProposalNo = ? , PrtNo = ? , ContType = ? , PolTypeFlag = ? , MainPolNo = ? , MasterPolNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , SaleChnl = ? , Handler = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAppAge = ? , InsuredPeoples = ? , OccupationType = ? , AppntNo = ? , AppntName = ? , CValiDate = ? , SignCom = ? , SignDate = ? , SignTime = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , GetStartDate = ? , EndDate = ? , AcciEndDate = ? , GetYearFlag = ? , GetYear = ? , PayEndYearFlag = ? , PayEndYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , GetStartType = ? , SpecifyValiDate = ? , PayMode = ? , PayLocation = ? , PayIntv = ? , PayYears = ? , Years = ? , ManageFeeRate = ? , FloatRate = ? , PremToAmnt = ? , Mult = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , LeavingMoney = ? , EndorseTimes = ? , ClaimTimes = ? , LiveTimes = ? , RenewCount = ? , LastGetDate = ? , LastLoanDate = ? , LastRegetDate = ? , LastEdorDate = ? , LastRevDate = ? , RnewFlag = ? , StopFlag = ? , ExpiryFlag = ? , AutoPayFlag = ? , InterestDifFlag = ? , SubFlag = ? , BnfFlag = ? , HealthCheckFlag = ? , ImpartFlag = ? , ReinsureFlag = ? , AgentPayFlag = ? , AgentGetFlag = ? , LiveGetMode = ? , DeadGetMode = ? , BonusGetMode = ? , BonusMan = ? , DeadFlag = ? , SmokeFlag = ? , Remark = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , PolApplyDate = ? , AppFlag = ? , PolState = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , WaitPeriod = ? , GetForm = ? , GetBankCode = ? , GetBankAccNo = ? , GetAccName = ? , KeepValueOpt = ? , PayRuleCode = ? , AscriptionRuleCode = ? , AutoPubAccFlag = ? , AscriptionFlag = ? , PCValiDate = ? , RiskSequence = ? , Currency = ? , InvestRuleCode = ? , UintLinkValiFlag = ? WHERE  EdorNo = ? AND PolNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPolNo());
			}
			if(this.getProposalNo() == null || this.getProposalNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getContType());
			}
			if(this.getPolTypeFlag() == null || this.getPolTypeFlag().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getPolTypeFlag());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getMainPolNo());
			}
			if(this.getMasterPolNo() == null || this.getMasterPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getMasterPolNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getRiskVersion());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentCode1());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getHandler());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getInsuredName());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getInsuredBirthday()));
			}
			pstmt.setInt(27, this.getInsuredAppAge());
			pstmt.setInt(28, this.getInsuredPeoples());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getOccupationType());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getAppntNo());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getAppntName());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getCValiDate()));
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getSignDate()));
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getSignTime());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getEndDate()));
			}
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getAcciEndDate()));
			}
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getGetYearFlag());
			}
			pstmt.setInt(43, this.getGetYear());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getPayEndYearFlag());
			}
			pstmt.setInt(45, this.getPayEndYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getInsuYearFlag());
			}
			pstmt.setInt(47, this.getInsuYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getAcciYearFlag());
			}
			pstmt.setInt(49, this.getAcciYear());
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getGetStartType());
			}
			if(this.getSpecifyValiDate() == null || this.getSpecifyValiDate().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getSpecifyValiDate());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(52, 1);
			} else {
				pstmt.setString(52, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getPayLocation());
			}
			pstmt.setInt(54, this.getPayIntv());
			pstmt.setInt(55, this.getPayYears());
			pstmt.setInt(56, this.getYears());
			pstmt.setDouble(57, this.getManageFeeRate());
			pstmt.setDouble(58, this.getFloatRate());
			if(this.getPremToAmnt() == null || this.getPremToAmnt().equals("null")) {
				pstmt.setNull(59, 1);
			} else {
				pstmt.setString(59, this.getPremToAmnt());
			}
			pstmt.setDouble(60, this.getMult());
			pstmt.setDouble(61, this.getStandPrem());
			pstmt.setDouble(62, this.getPrem());
			pstmt.setDouble(63, this.getSumPrem());
			pstmt.setDouble(64, this.getAmnt());
			pstmt.setDouble(65, this.getRiskAmnt());
			pstmt.setDouble(66, this.getLeavingMoney());
			pstmt.setInt(67, this.getEndorseTimes());
			pstmt.setInt(68, this.getClaimTimes());
			pstmt.setInt(69, this.getLiveTimes());
			pstmt.setInt(70, this.getRenewCount());
			if(this.getLastGetDate() == null || this.getLastGetDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getLastGetDate()));
			}
			if(this.getLastLoanDate() == null || this.getLastLoanDate().equals("null")) {
				pstmt.setNull(72, 91);
			} else {
				pstmt.setDate(72, Date.valueOf(this.getLastLoanDate()));
			}
			if(this.getLastRegetDate() == null || this.getLastRegetDate().equals("null")) {
				pstmt.setNull(73, 91);
			} else {
				pstmt.setDate(73, Date.valueOf(this.getLastRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getLastRevDate() == null || this.getLastRevDate().equals("null")) {
				pstmt.setNull(75, 91);
			} else {
				pstmt.setDate(75, Date.valueOf(this.getLastRevDate()));
			}
			pstmt.setInt(76, this.getRnewFlag());
			if(this.getStopFlag() == null || this.getStopFlag().equals("null")) {
				pstmt.setNull(77, 1);
			} else {
				pstmt.setString(77, this.getStopFlag());
			}
			if(this.getExpiryFlag() == null || this.getExpiryFlag().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getExpiryFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(79, 1);
			} else {
				pstmt.setString(79, this.getAutoPayFlag());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(80, 1);
			} else {
				pstmt.setString(80, this.getInterestDifFlag());
			}
			if(this.getSubFlag() == null || this.getSubFlag().equals("null")) {
				pstmt.setNull(81, 1);
			} else {
				pstmt.setString(81, this.getSubFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(82, 1);
			} else {
				pstmt.setString(82, this.getBnfFlag());
			}
			if(this.getHealthCheckFlag() == null || this.getHealthCheckFlag().equals("null")) {
				pstmt.setNull(83, 1);
			} else {
				pstmt.setString(83, this.getHealthCheckFlag());
			}
			if(this.getImpartFlag() == null || this.getImpartFlag().equals("null")) {
				pstmt.setNull(84, 1);
			} else {
				pstmt.setString(84, this.getImpartFlag());
			}
			if(this.getReinsureFlag() == null || this.getReinsureFlag().equals("null")) {
				pstmt.setNull(85, 1);
			} else {
				pstmt.setString(85, this.getReinsureFlag());
			}
			if(this.getAgentPayFlag() == null || this.getAgentPayFlag().equals("null")) {
				pstmt.setNull(86, 1);
			} else {
				pstmt.setString(86, this.getAgentPayFlag());
			}
			if(this.getAgentGetFlag() == null || this.getAgentGetFlag().equals("null")) {
				pstmt.setNull(87, 1);
			} else {
				pstmt.setString(87, this.getAgentGetFlag());
			}
			if(this.getLiveGetMode() == null || this.getLiveGetMode().equals("null")) {
				pstmt.setNull(88, 1);
			} else {
				pstmt.setString(88, this.getLiveGetMode());
			}
			if(this.getDeadGetMode() == null || this.getDeadGetMode().equals("null")) {
				pstmt.setNull(89, 1);
			} else {
				pstmt.setString(89, this.getDeadGetMode());
			}
			if(this.getBonusGetMode() == null || this.getBonusGetMode().equals("null")) {
				pstmt.setNull(90, 1);
			} else {
				pstmt.setString(90, this.getBonusGetMode());
			}
			if(this.getBonusMan() == null || this.getBonusMan().equals("null")) {
				pstmt.setNull(91, 1);
			} else {
				pstmt.setString(91, this.getBonusMan());
			}
			if(this.getDeadFlag() == null || this.getDeadFlag().equals("null")) {
				pstmt.setNull(92, 1);
			} else {
				pstmt.setString(92, this.getDeadFlag());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(93, 1);
			} else {
				pstmt.setString(93, this.getSmokeFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getRemark());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(95, 1);
			} else {
				pstmt.setString(95, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(97, 91);
			} else {
				pstmt.setDate(97, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(99, 1);
			} else {
				pstmt.setString(99, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(101, 91);
			} else {
				pstmt.setDate(101, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getUWTime());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(103, 91);
			} else {
				pstmt.setDate(103, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(104, 1);
			} else {
				pstmt.setString(104, this.getAppFlag());
			}
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getPolState());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(110, 91);
			} else {
				pstmt.setDate(110, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(111, 1);
			} else {
				pstmt.setString(111, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(112, 91);
			} else {
				pstmt.setDate(112, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(113, 1);
			} else {
				pstmt.setString(113, this.getModifyTime());
			}
			pstmt.setInt(114, this.getWaitPeriod());
			if(this.getGetForm() == null || this.getGetForm().equals("null")) {
				pstmt.setNull(115, 1);
			} else {
				pstmt.setString(115, this.getGetForm());
			}
			if(this.getGetBankCode() == null || this.getGetBankCode().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getGetBankCode());
			}
			if(this.getGetBankAccNo() == null || this.getGetBankAccNo().equals("null")) {
				pstmt.setNull(117, 12);
			} else {
				pstmt.setString(117, this.getGetBankAccNo());
			}
			if(this.getGetAccName() == null || this.getGetAccName().equals("null")) {
				pstmt.setNull(118, 12);
			} else {
				pstmt.setString(118, this.getGetAccName());
			}
			if(this.getKeepValueOpt() == null || this.getKeepValueOpt().equals("null")) {
				pstmt.setNull(119, 1);
			} else {
				pstmt.setString(119, this.getKeepValueOpt());
			}
			if(this.getPayRuleCode() == null || this.getPayRuleCode().equals("null")) {
				pstmt.setNull(120, 12);
			} else {
				pstmt.setString(120, this.getPayRuleCode());
			}
			if(this.getAscriptionRuleCode() == null || this.getAscriptionRuleCode().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getAscriptionRuleCode());
			}
			if(this.getAutoPubAccFlag() == null || this.getAutoPubAccFlag().equals("null")) {
				pstmt.setNull(122, 1);
			} else {
				pstmt.setString(122, this.getAutoPubAccFlag());
			}
			if(this.getAscriptionFlag() == null || this.getAscriptionFlag().equals("null")) {
				pstmt.setNull(123, 12);
			} else {
				pstmt.setString(123, this.getAscriptionFlag());
			}
			if(this.getPCValiDate() == null || this.getPCValiDate().equals("null")) {
				pstmt.setNull(124, 91);
			} else {
				pstmt.setDate(124, Date.valueOf(this.getPCValiDate()));
			}
			if(this.getRiskSequence() == null || this.getRiskSequence().equals("null")) {
				pstmt.setNull(125, 12);
			} else {
				pstmt.setString(125, this.getRiskSequence());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(126, 12);
			} else {
				pstmt.setString(126, this.getCurrency());
			}
			if(this.getInvestRuleCode() == null || this.getInvestRuleCode().equals("null")) {
				pstmt.setNull(127, 12);
			} else {
				pstmt.setString(127, this.getInvestRuleCode());
			}
			if(this.getUintLinkValiFlag() == null || this.getUintLinkValiFlag().equals("null")) {
				pstmt.setNull(128, 12);
			} else {
				pstmt.setString(128, this.getUintLinkValiFlag());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(129, 12);
			} else {
				pstmt.setString(129, this.getEdorNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(130, 12);
			} else {
				pstmt.setString(130, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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
		SQLString sqlObj = new SQLString("LBPol");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBPol VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPolNo());
			}
			if(this.getProposalNo() == null || this.getProposalNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getContType());
			}
			if(this.getPolTypeFlag() == null || this.getPolTypeFlag().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getPolTypeFlag());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getMainPolNo());
			}
			if(this.getMasterPolNo() == null || this.getMasterPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getMasterPolNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getRiskVersion());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentCode1());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getHandler());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getInsuredName());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getInsuredBirthday()));
			}
			pstmt.setInt(27, this.getInsuredAppAge());
			pstmt.setInt(28, this.getInsuredPeoples());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getOccupationType());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getAppntNo());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getAppntName());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getCValiDate()));
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getSignDate()));
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getSignTime());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getEndDate()));
			}
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getAcciEndDate()));
			}
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getGetYearFlag());
			}
			pstmt.setInt(43, this.getGetYear());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getPayEndYearFlag());
			}
			pstmt.setInt(45, this.getPayEndYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getInsuYearFlag());
			}
			pstmt.setInt(47, this.getInsuYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getAcciYearFlag());
			}
			pstmt.setInt(49, this.getAcciYear());
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getGetStartType());
			}
			if(this.getSpecifyValiDate() == null || this.getSpecifyValiDate().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getSpecifyValiDate());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(52, 1);
			} else {
				pstmt.setString(52, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getPayLocation());
			}
			pstmt.setInt(54, this.getPayIntv());
			pstmt.setInt(55, this.getPayYears());
			pstmt.setInt(56, this.getYears());
			pstmt.setDouble(57, this.getManageFeeRate());
			pstmt.setDouble(58, this.getFloatRate());
			if(this.getPremToAmnt() == null || this.getPremToAmnt().equals("null")) {
				pstmt.setNull(59, 1);
			} else {
				pstmt.setString(59, this.getPremToAmnt());
			}
			pstmt.setDouble(60, this.getMult());
			pstmt.setDouble(61, this.getStandPrem());
			pstmt.setDouble(62, this.getPrem());
			pstmt.setDouble(63, this.getSumPrem());
			pstmt.setDouble(64, this.getAmnt());
			pstmt.setDouble(65, this.getRiskAmnt());
			pstmt.setDouble(66, this.getLeavingMoney());
			pstmt.setInt(67, this.getEndorseTimes());
			pstmt.setInt(68, this.getClaimTimes());
			pstmt.setInt(69, this.getLiveTimes());
			pstmt.setInt(70, this.getRenewCount());
			if(this.getLastGetDate() == null || this.getLastGetDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getLastGetDate()));
			}
			if(this.getLastLoanDate() == null || this.getLastLoanDate().equals("null")) {
				pstmt.setNull(72, 91);
			} else {
				pstmt.setDate(72, Date.valueOf(this.getLastLoanDate()));
			}
			if(this.getLastRegetDate() == null || this.getLastRegetDate().equals("null")) {
				pstmt.setNull(73, 91);
			} else {
				pstmt.setDate(73, Date.valueOf(this.getLastRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getLastRevDate() == null || this.getLastRevDate().equals("null")) {
				pstmt.setNull(75, 91);
			} else {
				pstmt.setDate(75, Date.valueOf(this.getLastRevDate()));
			}
			pstmt.setInt(76, this.getRnewFlag());
			if(this.getStopFlag() == null || this.getStopFlag().equals("null")) {
				pstmt.setNull(77, 1);
			} else {
				pstmt.setString(77, this.getStopFlag());
			}
			if(this.getExpiryFlag() == null || this.getExpiryFlag().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getExpiryFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(79, 1);
			} else {
				pstmt.setString(79, this.getAutoPayFlag());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(80, 1);
			} else {
				pstmt.setString(80, this.getInterestDifFlag());
			}
			if(this.getSubFlag() == null || this.getSubFlag().equals("null")) {
				pstmt.setNull(81, 1);
			} else {
				pstmt.setString(81, this.getSubFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(82, 1);
			} else {
				pstmt.setString(82, this.getBnfFlag());
			}
			if(this.getHealthCheckFlag() == null || this.getHealthCheckFlag().equals("null")) {
				pstmt.setNull(83, 1);
			} else {
				pstmt.setString(83, this.getHealthCheckFlag());
			}
			if(this.getImpartFlag() == null || this.getImpartFlag().equals("null")) {
				pstmt.setNull(84, 1);
			} else {
				pstmt.setString(84, this.getImpartFlag());
			}
			if(this.getReinsureFlag() == null || this.getReinsureFlag().equals("null")) {
				pstmt.setNull(85, 1);
			} else {
				pstmt.setString(85, this.getReinsureFlag());
			}
			if(this.getAgentPayFlag() == null || this.getAgentPayFlag().equals("null")) {
				pstmt.setNull(86, 1);
			} else {
				pstmt.setString(86, this.getAgentPayFlag());
			}
			if(this.getAgentGetFlag() == null || this.getAgentGetFlag().equals("null")) {
				pstmt.setNull(87, 1);
			} else {
				pstmt.setString(87, this.getAgentGetFlag());
			}
			if(this.getLiveGetMode() == null || this.getLiveGetMode().equals("null")) {
				pstmt.setNull(88, 1);
			} else {
				pstmt.setString(88, this.getLiveGetMode());
			}
			if(this.getDeadGetMode() == null || this.getDeadGetMode().equals("null")) {
				pstmt.setNull(89, 1);
			} else {
				pstmt.setString(89, this.getDeadGetMode());
			}
			if(this.getBonusGetMode() == null || this.getBonusGetMode().equals("null")) {
				pstmt.setNull(90, 1);
			} else {
				pstmt.setString(90, this.getBonusGetMode());
			}
			if(this.getBonusMan() == null || this.getBonusMan().equals("null")) {
				pstmt.setNull(91, 1);
			} else {
				pstmt.setString(91, this.getBonusMan());
			}
			if(this.getDeadFlag() == null || this.getDeadFlag().equals("null")) {
				pstmt.setNull(92, 1);
			} else {
				pstmt.setString(92, this.getDeadFlag());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(93, 1);
			} else {
				pstmt.setString(93, this.getSmokeFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getRemark());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(95, 1);
			} else {
				pstmt.setString(95, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(97, 91);
			} else {
				pstmt.setDate(97, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(99, 1);
			} else {
				pstmt.setString(99, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(101, 91);
			} else {
				pstmt.setDate(101, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getUWTime());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(103, 91);
			} else {
				pstmt.setDate(103, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(104, 1);
			} else {
				pstmt.setString(104, this.getAppFlag());
			}
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getPolState());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(110, 91);
			} else {
				pstmt.setDate(110, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(111, 1);
			} else {
				pstmt.setString(111, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(112, 91);
			} else {
				pstmt.setDate(112, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(113, 1);
			} else {
				pstmt.setString(113, this.getModifyTime());
			}
			pstmt.setInt(114, this.getWaitPeriod());
			if(this.getGetForm() == null || this.getGetForm().equals("null")) {
				pstmt.setNull(115, 1);
			} else {
				pstmt.setString(115, this.getGetForm());
			}
			if(this.getGetBankCode() == null || this.getGetBankCode().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getGetBankCode());
			}
			if(this.getGetBankAccNo() == null || this.getGetBankAccNo().equals("null")) {
				pstmt.setNull(117, 12);
			} else {
				pstmt.setString(117, this.getGetBankAccNo());
			}
			if(this.getGetAccName() == null || this.getGetAccName().equals("null")) {
				pstmt.setNull(118, 12);
			} else {
				pstmt.setString(118, this.getGetAccName());
			}
			if(this.getKeepValueOpt() == null || this.getKeepValueOpt().equals("null")) {
				pstmt.setNull(119, 1);
			} else {
				pstmt.setString(119, this.getKeepValueOpt());
			}
			if(this.getPayRuleCode() == null || this.getPayRuleCode().equals("null")) {
				pstmt.setNull(120, 12);
			} else {
				pstmt.setString(120, this.getPayRuleCode());
			}
			if(this.getAscriptionRuleCode() == null || this.getAscriptionRuleCode().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getAscriptionRuleCode());
			}
			if(this.getAutoPubAccFlag() == null || this.getAutoPubAccFlag().equals("null")) {
				pstmt.setNull(122, 1);
			} else {
				pstmt.setString(122, this.getAutoPubAccFlag());
			}
			if(this.getAscriptionFlag() == null || this.getAscriptionFlag().equals("null")) {
				pstmt.setNull(123, 12);
			} else {
				pstmt.setString(123, this.getAscriptionFlag());
			}
			if(this.getPCValiDate() == null || this.getPCValiDate().equals("null")) {
				pstmt.setNull(124, 91);
			} else {
				pstmt.setDate(124, Date.valueOf(this.getPCValiDate()));
			}
			if(this.getRiskSequence() == null || this.getRiskSequence().equals("null")) {
				pstmt.setNull(125, 12);
			} else {
				pstmt.setString(125, this.getRiskSequence());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(126, 12);
			} else {
				pstmt.setString(126, this.getCurrency());
			}
			if(this.getInvestRuleCode() == null || this.getInvestRuleCode().equals("null")) {
				pstmt.setNull(127, 12);
			} else {
				pstmt.setString(127, this.getInvestRuleCode());
			}
			if(this.getUintLinkValiFlag() == null || this.getUintLinkValiFlag().equals("null")) {
				pstmt.setNull(128, 12);
			} else {
				pstmt.setString(128, this.getUintLinkValiFlag());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBPol WHERE  EdorNo = ? AND PolNo = ?", 
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
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPolDB";
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
			tError.moduleName = "LBPolDB";
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

	public LBPolSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPolSet aLBPolSet = new LBPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPol");
			LBPolSchema aSchema = this.getSchema();
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
				LBPolSchema s1 = new LBPolSchema();
				s1.setSchema(rs,i);
				aLBPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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

		return aLBPolSet;

	}

	public LBPolSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPolSet aLBPolSet = new LBPolSet();

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
				LBPolSchema s1 = new LBPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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

		return aLBPolSet;
	}

	public LBPolSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPolSet aLBPolSet = new LBPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPol");
			LBPolSchema aSchema = this.getSchema();
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

				LBPolSchema s1 = new LBPolSchema();
				s1.setSchema(rs,i);
				aLBPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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

		return aLBPolSet;

	}

	public LBPolSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPolSet aLBPolSet = new LBPolSet();

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

				LBPolSchema s1 = new LBPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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

		return aLBPolSet;
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
			SQLString sqlObj = new SQLString("LBPol");
			LBPolSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBPol " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBPolDB";
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
			tError.moduleName = "LBPolDB";
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
        tError.moduleName = "LBPolDB";
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
        tError.moduleName = "LBPolDB";
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
        tError.moduleName = "LBPolDB";
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
        tError.moduleName = "LBPolDB";
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
 * @return LBPolSet
 */
public LBPolSet getData()
{
    int tCount = 0;
    LBPolSet tLBPolSet = new LBPolSet();
    LBPolSchema tLBPolSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBPolDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBPolSchema = new LBPolSchema();
        tLBPolSchema.setSchema(mResultSet, 1);
        tLBPolSet.add(tLBPolSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBPolSchema = new LBPolSchema();
                tLBPolSchema.setSchema(mResultSet, 1);
                tLBPolSet.add(tLBPolSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBPolDB";
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
    return tLBPolSet;
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
            tError.moduleName = "LBPolDB";
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
        tError.moduleName = "LBPolDB";
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
            tError.moduleName = "LBPolDB";
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
        tError.moduleName = "LBPolDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBPolSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPolSet aLBPolSet = new LBPolSet();

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
				LBPolSchema s1 = new LBPolSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPolSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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

		return aLBPolSet;
	}

	public LBPolSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPolSet aLBPolSet = new LBPolSet();

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

				LBPolSchema s1 = new LBPolSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPolSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPolDB";
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

		return aLBPolSet; 
	}

}
