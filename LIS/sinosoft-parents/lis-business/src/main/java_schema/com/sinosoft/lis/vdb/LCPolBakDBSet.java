/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCPolBakSchema;
import com.sinosoft.lis.vschema.LCPolBakSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCPolBakDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCPolBakDBSet extends LCPolBakSet
{
private static Logger logger = Logger.getLogger(LCPolBakDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCPolBakDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCPolBak");
		mflag = true;
	}

	public LCPolBakDBSet()
	{
		db = new DBOper( "LCPolBak" );
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
			tError.moduleName = "LCPolBakDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCPolBak WHERE  PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPolBak");
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
			tError.moduleName = "LCPolBakDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCPolBak SET  GrpContNo = ? , GrpPolNo = ? , ContNo = ? , PolNo = ? , ProposalNo = ? , PrtNo = ? , ContType = ? , PolTypeFlag = ? , MainPolNo = ? , MasterPolNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , SaleChnl = ? , Handler = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAppAge = ? , InsuredPeoples = ? , OccupationType = ? , AppntNo = ? , AppntName = ? , CValiDate = ? , SignCom = ? , SignDate = ? , SignTime = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , GetStartDate = ? , EndDate = ? , AcciEndDate = ? , GetYearFlag = ? , GetYear = ? , PayEndYearFlag = ? , PayEndYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , GetStartType = ? , SpecifyValiDate = ? , PayMode = ? , PayLocation = ? , PayIntv = ? , PayYears = ? , Years = ? , ManageFeeRate = ? , FloatRate = ? , PremToAmnt = ? , Mult = ? , StandPrem = ? , Prem = ? , SumPrem = ? , Amnt = ? , RiskAmnt = ? , LeavingMoney = ? , EndorseTimes = ? , ClaimTimes = ? , LiveTimes = ? , RenewCount = ? , LastGetDate = ? , LastLoanDate = ? , LastRegetDate = ? , LastEdorDate = ? , LastRevDate = ? , RnewFlag = ? , StopFlag = ? , ExpiryFlag = ? , AutoPayFlag = ? , InterestDifFlag = ? , SubFlag = ? , BnfFlag = ? , HealthCheckFlag = ? , ImpartFlag = ? , ReinsureFlag = ? , AgentPayFlag = ? , AgentGetFlag = ? , LiveGetMode = ? , DeadGetMode = ? , BonusGetMode = ? , BonusMan = ? , DeadFlag = ? , SmokeFlag = ? , Remark = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , PolApplyDate = ? , AppFlag = ? , PolState = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , WaitPeriod = ? , GetForm = ? , GetBankCode = ? , GetBankAccNo = ? , GetAccName = ? , KeepValueOpt = ? , PayRuleCode = ? , AscriptionRuleCode = ? , AscriptionFlag = ? , PCValiDate = ? , AutoPubAccFlag = ? , RiskSequence = ? , CancleForegetSpecFlag = ? , TakeDate = ? , TakeTime = ? , AirNo = ? , TicketNo = ? , SeatNo = ? , InputPrem = ? WHERE  PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolNo());
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPrtNo());
			}
			if(this.get(i).getContType() == null || this.get(i).getContType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getContType());
			}
			if(this.get(i).getPolTypeFlag() == null || this.get(i).getPolTypeFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getPolTypeFlag());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMainPolNo());
			}
			if(this.get(i).getMasterPolNo() == null || this.get(i).getMasterPolNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMasterPolNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRiskVersion());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAgentCode1());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSaleChnl());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getHandler());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(26, this.get(i).getInsuredAppAge());
			pstmt.setInt(27, this.get(i).getInsuredPeoples());
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOccupationType());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAppntName());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSignCom());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getSignTime());
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(42, this.get(i).getGetYear());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(44, this.get(i).getPayEndYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(46, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(48, this.get(i).getAcciYear());
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getGetStartType());
			}
			if(this.get(i).getSpecifyValiDate() == null || this.get(i).getSpecifyValiDate().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSpecifyValiDate());
			}
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPayMode());
			}
			if(this.get(i).getPayLocation() == null || this.get(i).getPayLocation().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getPayLocation());
			}
			pstmt.setInt(53, this.get(i).getPayIntv());
			pstmt.setInt(54, this.get(i).getPayYears());
			pstmt.setInt(55, this.get(i).getYears());
			pstmt.setDouble(56, this.get(i).getManageFeeRate());
			pstmt.setDouble(57, this.get(i).getFloatRate());
			if(this.get(i).getPremToAmnt() == null || this.get(i).getPremToAmnt().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getPremToAmnt());
			}
			pstmt.setDouble(59, this.get(i).getMult());
			pstmt.setDouble(60, this.get(i).getStandPrem());
			pstmt.setDouble(61, this.get(i).getPrem());
			pstmt.setDouble(62, this.get(i).getSumPrem());
			pstmt.setDouble(63, this.get(i).getAmnt());
			pstmt.setDouble(64, this.get(i).getRiskAmnt());
			pstmt.setDouble(65, this.get(i).getLeavingMoney());
			pstmt.setInt(66, this.get(i).getEndorseTimes());
			pstmt.setInt(67, this.get(i).getClaimTimes());
			pstmt.setInt(68, this.get(i).getLiveTimes());
			pstmt.setInt(69, this.get(i).getRenewCount());
			if(this.get(i).getLastGetDate() == null || this.get(i).getLastGetDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getLastGetDate()));
			}
			if(this.get(i).getLastLoanDate() == null || this.get(i).getLastLoanDate().equals("null")) {
				pstmt.setDate(71,null);
			} else {
				pstmt.setDate(71, Date.valueOf(this.get(i).getLastLoanDate()));
			}
			if(this.get(i).getLastRegetDate() == null || this.get(i).getLastRegetDate().equals("null")) {
				pstmt.setDate(72,null);
			} else {
				pstmt.setDate(72, Date.valueOf(this.get(i).getLastRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getLastRevDate() == null || this.get(i).getLastRevDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getLastRevDate()));
			}
			pstmt.setInt(75, this.get(i).getRnewFlag());
			if(this.get(i).getStopFlag() == null || this.get(i).getStopFlag().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getStopFlag());
			}
			if(this.get(i).getExpiryFlag() == null || this.get(i).getExpiryFlag().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getExpiryFlag());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getAutoPayFlag());
			}
			if(this.get(i).getInterestDifFlag() == null || this.get(i).getInterestDifFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getInterestDifFlag());
			}
			if(this.get(i).getSubFlag() == null || this.get(i).getSubFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getSubFlag());
			}
			if(this.get(i).getBnfFlag() == null || this.get(i).getBnfFlag().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getBnfFlag());
			}
			if(this.get(i).getHealthCheckFlag() == null || this.get(i).getHealthCheckFlag().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getHealthCheckFlag());
			}
			if(this.get(i).getImpartFlag() == null || this.get(i).getImpartFlag().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getImpartFlag());
			}
			if(this.get(i).getReinsureFlag() == null || this.get(i).getReinsureFlag().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getReinsureFlag());
			}
			if(this.get(i).getAgentPayFlag() == null || this.get(i).getAgentPayFlag().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getAgentPayFlag());
			}
			if(this.get(i).getAgentGetFlag() == null || this.get(i).getAgentGetFlag().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getAgentGetFlag());
			}
			if(this.get(i).getLiveGetMode() == null || this.get(i).getLiveGetMode().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getLiveGetMode());
			}
			if(this.get(i).getDeadGetMode() == null || this.get(i).getDeadGetMode().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getDeadGetMode());
			}
			if(this.get(i).getBonusGetMode() == null || this.get(i).getBonusGetMode().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getBonusGetMode());
			}
			if(this.get(i).getBonusMan() == null || this.get(i).getBonusMan().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getBonusMan());
			}
			if(this.get(i).getDeadFlag() == null || this.get(i).getDeadFlag().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getDeadFlag());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getRemark());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(96,null);
			} else {
				pstmt.setDate(96, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWCode() == null || this.get(i).getUWCode().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getUWCode());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(100,null);
			} else {
				pstmt.setDate(100, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getUWTime());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(102,null);
			} else {
				pstmt.setDate(102, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getAppFlag());
			}
			if(this.get(i).getPolState() == null || this.get(i).getPolState().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getPolState());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(109,null);
			} else {
				pstmt.setDate(109, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(111,null);
			} else {
				pstmt.setDate(111, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(112,null);
			} else {
				pstmt.setString(112, this.get(i).getModifyTime());
			}
			pstmt.setInt(113, this.get(i).getWaitPeriod());
			if(this.get(i).getGetForm() == null || this.get(i).getGetForm().equals("null")) {
				pstmt.setString(114,null);
			} else {
				pstmt.setString(114, this.get(i).getGetForm());
			}
			if(this.get(i).getGetBankCode() == null || this.get(i).getGetBankCode().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getGetBankCode());
			}
			if(this.get(i).getGetBankAccNo() == null || this.get(i).getGetBankAccNo().equals("null")) {
				pstmt.setString(116,null);
			} else {
				pstmt.setString(116, this.get(i).getGetBankAccNo());
			}
			if(this.get(i).getGetAccName() == null || this.get(i).getGetAccName().equals("null")) {
				pstmt.setString(117,null);
			} else {
				pstmt.setString(117, this.get(i).getGetAccName());
			}
			if(this.get(i).getKeepValueOpt() == null || this.get(i).getKeepValueOpt().equals("null")) {
				pstmt.setString(118,null);
			} else {
				pstmt.setString(118, this.get(i).getKeepValueOpt());
			}
			if(this.get(i).getPayRuleCode() == null || this.get(i).getPayRuleCode().equals("null")) {
				pstmt.setString(119,null);
			} else {
				pstmt.setString(119, this.get(i).getPayRuleCode());
			}
			if(this.get(i).getAscriptionRuleCode() == null || this.get(i).getAscriptionRuleCode().equals("null")) {
				pstmt.setString(120,null);
			} else {
				pstmt.setString(120, this.get(i).getAscriptionRuleCode());
			}
			if(this.get(i).getAscriptionFlag() == null || this.get(i).getAscriptionFlag().equals("null")) {
				pstmt.setString(121,null);
			} else {
				pstmt.setString(121, this.get(i).getAscriptionFlag());
			}
			if(this.get(i).getPCValiDate() == null || this.get(i).getPCValiDate().equals("null")) {
				pstmt.setDate(122,null);
			} else {
				pstmt.setDate(122, Date.valueOf(this.get(i).getPCValiDate()));
			}
			if(this.get(i).getAutoPubAccFlag() == null || this.get(i).getAutoPubAccFlag().equals("null")) {
				pstmt.setString(123,null);
			} else {
				pstmt.setString(123, this.get(i).getAutoPubAccFlag());
			}
			if(this.get(i).getRiskSequence() == null || this.get(i).getRiskSequence().equals("null")) {
				pstmt.setString(124,null);
			} else {
				pstmt.setString(124, this.get(i).getRiskSequence());
			}
			if(this.get(i).getCancleForegetSpecFlag() == null || this.get(i).getCancleForegetSpecFlag().equals("null")) {
				pstmt.setString(125,null);
			} else {
				pstmt.setString(125, this.get(i).getCancleForegetSpecFlag());
			}
			if(this.get(i).getTakeDate() == null || this.get(i).getTakeDate().equals("null")) {
				pstmt.setDate(126,null);
			} else {
				pstmt.setDate(126, Date.valueOf(this.get(i).getTakeDate()));
			}
			if(this.get(i).getTakeTime() == null || this.get(i).getTakeTime().equals("null")) {
				pstmt.setString(127,null);
			} else {
				pstmt.setString(127, this.get(i).getTakeTime());
			}
			if(this.get(i).getAirNo() == null || this.get(i).getAirNo().equals("null")) {
				pstmt.setString(128,null);
			} else {
				pstmt.setString(128, this.get(i).getAirNo());
			}
			if(this.get(i).getTicketNo() == null || this.get(i).getTicketNo().equals("null")) {
				pstmt.setString(129,null);
			} else {
				pstmt.setString(129, this.get(i).getTicketNo());
			}
			if(this.get(i).getSeatNo() == null || this.get(i).getSeatNo().equals("null")) {
				pstmt.setString(130,null);
			} else {
				pstmt.setString(130, this.get(i).getSeatNo());
			}
			pstmt.setDouble(131, this.get(i).getInputPrem());
			// set where condition
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(132,null);
			} else {
				pstmt.setString(132, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPolBak");
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
			tError.moduleName = "LCPolBakDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCPolBak(GrpContNo ,GrpPolNo ,ContNo ,PolNo ,ProposalNo ,PrtNo ,ContType ,PolTypeFlag ,MainPolNo ,MasterPolNo ,KindCode ,RiskCode ,RiskVersion ,ManageCom ,AgentCom ,AgentType ,AgentCode ,AgentGroup ,AgentCode1 ,SaleChnl ,Handler ,InsuredNo ,InsuredName ,InsuredSex ,InsuredBirthday ,InsuredAppAge ,InsuredPeoples ,OccupationType ,AppntNo ,AppntName ,CValiDate ,SignCom ,SignDate ,SignTime ,FirstPayDate ,PayEndDate ,PaytoDate ,GetStartDate ,EndDate ,AcciEndDate ,GetYearFlag ,GetYear ,PayEndYearFlag ,PayEndYear ,InsuYearFlag ,InsuYear ,AcciYearFlag ,AcciYear ,GetStartType ,SpecifyValiDate ,PayMode ,PayLocation ,PayIntv ,PayYears ,Years ,ManageFeeRate ,FloatRate ,PremToAmnt ,Mult ,StandPrem ,Prem ,SumPrem ,Amnt ,RiskAmnt ,LeavingMoney ,EndorseTimes ,ClaimTimes ,LiveTimes ,RenewCount ,LastGetDate ,LastLoanDate ,LastRegetDate ,LastEdorDate ,LastRevDate ,RnewFlag ,StopFlag ,ExpiryFlag ,AutoPayFlag ,InterestDifFlag ,SubFlag ,BnfFlag ,HealthCheckFlag ,ImpartFlag ,ReinsureFlag ,AgentPayFlag ,AgentGetFlag ,LiveGetMode ,DeadGetMode ,BonusGetMode ,BonusMan ,DeadFlag ,SmokeFlag ,Remark ,ApproveFlag ,ApproveCode ,ApproveDate ,ApproveTime ,UWFlag ,UWCode ,UWDate ,UWTime ,PolApplyDate ,AppFlag ,PolState ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,WaitPeriod ,GetForm ,GetBankCode ,GetBankAccNo ,GetAccName ,KeepValueOpt ,PayRuleCode ,AscriptionRuleCode ,AscriptionFlag ,PCValiDate ,AutoPubAccFlag ,RiskSequence ,CancleForegetSpecFlag ,TakeDate ,TakeTime ,AirNo ,TicketNo ,SeatNo ,InputPrem) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolNo());
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPrtNo());
			}
			if(this.get(i).getContType() == null || this.get(i).getContType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getContType());
			}
			if(this.get(i).getPolTypeFlag() == null || this.get(i).getPolTypeFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getPolTypeFlag());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMainPolNo());
			}
			if(this.get(i).getMasterPolNo() == null || this.get(i).getMasterPolNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMasterPolNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRiskVersion());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAgentCode1());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSaleChnl());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getHandler());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(26, this.get(i).getInsuredAppAge());
			pstmt.setInt(27, this.get(i).getInsuredPeoples());
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOccupationType());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAppntName());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSignCom());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getSignTime());
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(36,null);
			} else {
				pstmt.setDate(36, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(42, this.get(i).getGetYear());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(44, this.get(i).getPayEndYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(46, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(48, this.get(i).getAcciYear());
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getGetStartType());
			}
			if(this.get(i).getSpecifyValiDate() == null || this.get(i).getSpecifyValiDate().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSpecifyValiDate());
			}
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPayMode());
			}
			if(this.get(i).getPayLocation() == null || this.get(i).getPayLocation().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getPayLocation());
			}
			pstmt.setInt(53, this.get(i).getPayIntv());
			pstmt.setInt(54, this.get(i).getPayYears());
			pstmt.setInt(55, this.get(i).getYears());
			pstmt.setDouble(56, this.get(i).getManageFeeRate());
			pstmt.setDouble(57, this.get(i).getFloatRate());
			if(this.get(i).getPremToAmnt() == null || this.get(i).getPremToAmnt().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getPremToAmnt());
			}
			pstmt.setDouble(59, this.get(i).getMult());
			pstmt.setDouble(60, this.get(i).getStandPrem());
			pstmt.setDouble(61, this.get(i).getPrem());
			pstmt.setDouble(62, this.get(i).getSumPrem());
			pstmt.setDouble(63, this.get(i).getAmnt());
			pstmt.setDouble(64, this.get(i).getRiskAmnt());
			pstmt.setDouble(65, this.get(i).getLeavingMoney());
			pstmt.setInt(66, this.get(i).getEndorseTimes());
			pstmt.setInt(67, this.get(i).getClaimTimes());
			pstmt.setInt(68, this.get(i).getLiveTimes());
			pstmt.setInt(69, this.get(i).getRenewCount());
			if(this.get(i).getLastGetDate() == null || this.get(i).getLastGetDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getLastGetDate()));
			}
			if(this.get(i).getLastLoanDate() == null || this.get(i).getLastLoanDate().equals("null")) {
				pstmt.setDate(71,null);
			} else {
				pstmt.setDate(71, Date.valueOf(this.get(i).getLastLoanDate()));
			}
			if(this.get(i).getLastRegetDate() == null || this.get(i).getLastRegetDate().equals("null")) {
				pstmt.setDate(72,null);
			} else {
				pstmt.setDate(72, Date.valueOf(this.get(i).getLastRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getLastRevDate() == null || this.get(i).getLastRevDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getLastRevDate()));
			}
			pstmt.setInt(75, this.get(i).getRnewFlag());
			if(this.get(i).getStopFlag() == null || this.get(i).getStopFlag().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getStopFlag());
			}
			if(this.get(i).getExpiryFlag() == null || this.get(i).getExpiryFlag().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getExpiryFlag());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getAutoPayFlag());
			}
			if(this.get(i).getInterestDifFlag() == null || this.get(i).getInterestDifFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getInterestDifFlag());
			}
			if(this.get(i).getSubFlag() == null || this.get(i).getSubFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getSubFlag());
			}
			if(this.get(i).getBnfFlag() == null || this.get(i).getBnfFlag().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getBnfFlag());
			}
			if(this.get(i).getHealthCheckFlag() == null || this.get(i).getHealthCheckFlag().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getHealthCheckFlag());
			}
			if(this.get(i).getImpartFlag() == null || this.get(i).getImpartFlag().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getImpartFlag());
			}
			if(this.get(i).getReinsureFlag() == null || this.get(i).getReinsureFlag().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getReinsureFlag());
			}
			if(this.get(i).getAgentPayFlag() == null || this.get(i).getAgentPayFlag().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getAgentPayFlag());
			}
			if(this.get(i).getAgentGetFlag() == null || this.get(i).getAgentGetFlag().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getAgentGetFlag());
			}
			if(this.get(i).getLiveGetMode() == null || this.get(i).getLiveGetMode().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getLiveGetMode());
			}
			if(this.get(i).getDeadGetMode() == null || this.get(i).getDeadGetMode().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getDeadGetMode());
			}
			if(this.get(i).getBonusGetMode() == null || this.get(i).getBonusGetMode().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getBonusGetMode());
			}
			if(this.get(i).getBonusMan() == null || this.get(i).getBonusMan().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getBonusMan());
			}
			if(this.get(i).getDeadFlag() == null || this.get(i).getDeadFlag().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getDeadFlag());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getRemark());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(96,null);
			} else {
				pstmt.setDate(96, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWCode() == null || this.get(i).getUWCode().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getUWCode());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(100,null);
			} else {
				pstmt.setDate(100, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getUWTime());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(102,null);
			} else {
				pstmt.setDate(102, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getAppFlag());
			}
			if(this.get(i).getPolState() == null || this.get(i).getPolState().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getPolState());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(109,null);
			} else {
				pstmt.setDate(109, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(111,null);
			} else {
				pstmt.setDate(111, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(112,null);
			} else {
				pstmt.setString(112, this.get(i).getModifyTime());
			}
			pstmt.setInt(113, this.get(i).getWaitPeriod());
			if(this.get(i).getGetForm() == null || this.get(i).getGetForm().equals("null")) {
				pstmt.setString(114,null);
			} else {
				pstmt.setString(114, this.get(i).getGetForm());
			}
			if(this.get(i).getGetBankCode() == null || this.get(i).getGetBankCode().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getGetBankCode());
			}
			if(this.get(i).getGetBankAccNo() == null || this.get(i).getGetBankAccNo().equals("null")) {
				pstmt.setString(116,null);
			} else {
				pstmt.setString(116, this.get(i).getGetBankAccNo());
			}
			if(this.get(i).getGetAccName() == null || this.get(i).getGetAccName().equals("null")) {
				pstmt.setString(117,null);
			} else {
				pstmt.setString(117, this.get(i).getGetAccName());
			}
			if(this.get(i).getKeepValueOpt() == null || this.get(i).getKeepValueOpt().equals("null")) {
				pstmt.setString(118,null);
			} else {
				pstmt.setString(118, this.get(i).getKeepValueOpt());
			}
			if(this.get(i).getPayRuleCode() == null || this.get(i).getPayRuleCode().equals("null")) {
				pstmt.setString(119,null);
			} else {
				pstmt.setString(119, this.get(i).getPayRuleCode());
			}
			if(this.get(i).getAscriptionRuleCode() == null || this.get(i).getAscriptionRuleCode().equals("null")) {
				pstmt.setString(120,null);
			} else {
				pstmt.setString(120, this.get(i).getAscriptionRuleCode());
			}
			if(this.get(i).getAscriptionFlag() == null || this.get(i).getAscriptionFlag().equals("null")) {
				pstmt.setString(121,null);
			} else {
				pstmt.setString(121, this.get(i).getAscriptionFlag());
			}
			if(this.get(i).getPCValiDate() == null || this.get(i).getPCValiDate().equals("null")) {
				pstmt.setDate(122,null);
			} else {
				pstmt.setDate(122, Date.valueOf(this.get(i).getPCValiDate()));
			}
			if(this.get(i).getAutoPubAccFlag() == null || this.get(i).getAutoPubAccFlag().equals("null")) {
				pstmt.setString(123,null);
			} else {
				pstmt.setString(123, this.get(i).getAutoPubAccFlag());
			}
			if(this.get(i).getRiskSequence() == null || this.get(i).getRiskSequence().equals("null")) {
				pstmt.setString(124,null);
			} else {
				pstmt.setString(124, this.get(i).getRiskSequence());
			}
			if(this.get(i).getCancleForegetSpecFlag() == null || this.get(i).getCancleForegetSpecFlag().equals("null")) {
				pstmt.setString(125,null);
			} else {
				pstmt.setString(125, this.get(i).getCancleForegetSpecFlag());
			}
			if(this.get(i).getTakeDate() == null || this.get(i).getTakeDate().equals("null")) {
				pstmt.setDate(126,null);
			} else {
				pstmt.setDate(126, Date.valueOf(this.get(i).getTakeDate()));
			}
			if(this.get(i).getTakeTime() == null || this.get(i).getTakeTime().equals("null")) {
				pstmt.setString(127,null);
			} else {
				pstmt.setString(127, this.get(i).getTakeTime());
			}
			if(this.get(i).getAirNo() == null || this.get(i).getAirNo().equals("null")) {
				pstmt.setString(128,null);
			} else {
				pstmt.setString(128, this.get(i).getAirNo());
			}
			if(this.get(i).getTicketNo() == null || this.get(i).getTicketNo().equals("null")) {
				pstmt.setString(129,null);
			} else {
				pstmt.setString(129, this.get(i).getTicketNo());
			}
			if(this.get(i).getSeatNo() == null || this.get(i).getSeatNo().equals("null")) {
				pstmt.setString(130,null);
			} else {
				pstmt.setString(130, this.get(i).getSeatNo());
			}
			pstmt.setDouble(131, this.get(i).getInputPrem());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPolBak");
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
			tError.moduleName = "LCPolBakDBSet";
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
