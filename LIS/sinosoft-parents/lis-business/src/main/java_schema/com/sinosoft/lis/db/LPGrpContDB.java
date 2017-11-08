/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPGrpContDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPGrpContDB extends LPGrpContSchema
{
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
	public LPGrpContDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPGrpCont" );
		mflag = true;
	}

	public LPGrpContDB()
	{
		con = null;
		db = new DBOper( "LPGrpCont" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPGrpContSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPGrpContSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPGrpCont WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPGrpCont");
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
		SQLString sqlObj = new SQLString("LPGrpCont");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPGrpCont SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , ProposalGrpContNo = ? , PrtNo = ? , SaleChnl = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , Password = ? , Password2 = ? , AppntNo = ? , AddressNo = ? , Peoples2 = ? , GrpName = ? , BusinessType = ? , GrpNature = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , GrpGroupNo = ? , BankCode = ? , BankAccNo = ? , AccName = ? , DisputedFlag = ? , OutPayFlag = ? , GetPolMode = ? , Lang = ? , Currency = ? , LostTimes = ? , PrintCount = ? , RegetDate = ? , LastEdorDate = ? , LastGetDate = ? , LastLoanDate = ? , SpecFlag = ? , GrpSpec = ? , PayMode = ? , SignCom = ? , SignDate = ? , SignTime = ? , CValiDate = ? , PayIntv = ? , ManageFeeRate = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , Remark = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , InputOperator = ? , InputDate = ? , InputTime = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWOperator = ? , UWFlag = ? , UWDate = ? , UWTime = ? , AppFlag = ? , PolApplyDate = ? , CustomGetPolDate = ? , GetPolDate = ? , GetPolTime = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , EnterKind = ? , AmntGrade = ? , Peoples3 = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , RelaPeoples = ? , RelaMatePeoples = ? , RelaYoungPeoples = ? , RelaOtherPeoples = ? , FirstTrialOperator = ? , FirstTrialDate = ? , FirstTrialTime = ? , ReceiveOperator = ? , ReceiveDate = ? , ReceiveTime = ? , TempFeeNo = ? , HandlerName = ? , HandlerDate = ? , HandlerPrint = ? , AgentDate = ? , BusinessBigType = ? , MarketType = ? , ReportNo = ? , ConferNo = ? , SignReportNo = ? , AgentConferNo = ? , ContType = ? , EdorCalType = ? , ClientCare = ? , FundReason = ? , BackDateRemark = ? , ClientNeedJudge = ? , DonateContflag = ? , FundJudge = ? , ExamAndAppNo = ? , EdorTransPercent = ? , CardFlag = ? , AgentCodeOper = ? , AgentCodeAssi = ? , DelayReasonCode = ? , DelayReasonDesc = ? , ContPlanFlag = ? , FYCRATE = ? , ContFlag = ? , SaleDepart = ? , ChnlType = ? , AgentBranchesCode = ? , ProjectType = ? , RenewFlag = ? , RenewCount = ? , RenewContNo = ? , InitNumPeople = ? , InitMult = ? , InitAmnt = ? , InitRiskAmnt = ? , InitPrem = ? , InitStandPrem = ? , RiskAmnt = ? , StandPrem = ? , SumNumPeople = ? , InsuYear = ? , InsuYearFlag = ? , Years = ? , PayEndYear = ? , PayEndYearFlag = ? , PayYears = ? , ValDateType = ? , EndDate = ? , FirstPayDate = ? , PayEndDate = ? , PayToDate = ? , AutoPayFlag = ? , SignOperator = ? , Coinsurance = ? , AmntShareRate = ? , PremShareRate = ? , ScanCom = ? , ComCode = ? , ModifyOperator = ? WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getProposalGrpContNo() == null || this.getProposalGrpContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getProposalGrpContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPrtNo());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentCode1());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPassword());
			}
			if(this.getPassword2() == null || this.getPassword2().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPassword2());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAppntNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAddressNo());
			}
			pstmt.setInt(17, this.getPeoples2());
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpName());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getGrpNature());
			}
			pstmt.setDouble(21, this.getRgtMoney());
			pstmt.setDouble(22, this.getAsset());
			pstmt.setDouble(23, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getComAera());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getFoundDate()));
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getGrpGroupNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAccName());
			}
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getDisputedFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getOutPayFlag());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getGetPolMode());
			}
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getCurrency());
			}
			pstmt.setInt(42, this.getLostTimes());
			pstmt.setInt(43, this.getPrintCount());
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getLastGetDate() == null || this.getLastGetDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getLastGetDate()));
			}
			if(this.getLastLoanDate() == null || this.getLastLoanDate().equals("null")) {
				pstmt.setNull(47, 91);
			} else {
				pstmt.setDate(47, Date.valueOf(this.getLastLoanDate()));
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getSpecFlag());
			}
			if(this.getGrpSpec() == null || this.getGrpSpec().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getGrpSpec());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getPayMode());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getSignDate()));
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getSignTime());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(54, 91);
			} else {
				pstmt.setDate(54, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(55, this.getPayIntv());
			pstmt.setDouble(56, this.getManageFeeRate());
			pstmt.setInt(57, this.getExpPeoples());
			pstmt.setDouble(58, this.getExpPremium());
			pstmt.setDouble(59, this.getExpAmnt());
			pstmt.setInt(60, this.getPeoples());
			pstmt.setDouble(61, this.getMult());
			pstmt.setDouble(62, this.getPrem());
			pstmt.setDouble(63, this.getAmnt());
			pstmt.setDouble(64, this.getSumPrem());
			pstmt.setDouble(65, this.getSumPay());
			pstmt.setDouble(66, this.getDif());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRemark());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getStandbyFlag3());
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(72, 91);
			} else {
				pstmt.setDate(72, Date.valueOf(this.getInputDate()));
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getInputTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(74, 1);
			} else {
				pstmt.setString(74, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(76, 91);
			} else {
				pstmt.setDate(76, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getApproveTime());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getUWOperator());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(79, 1);
			} else {
				pstmt.setString(79, this.getUWFlag());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(80, 91);
			} else {
				pstmt.setDate(80, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(82, 1);
			} else {
				pstmt.setString(82, this.getAppFlag());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(83, 91);
			} else {
				pstmt.setDate(83, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(84, 91);
			} else {
				pstmt.setDate(84, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(85, 91);
			} else {
				pstmt.setDate(85, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getGetPolTime() == null || this.getGetPolTime().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getGetPolTime());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getState());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(89, 91);
			} else {
				pstmt.setDate(89, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(90, 1);
			} else {
				pstmt.setString(90, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(91, 91);
			} else {
				pstmt.setDate(91, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(92, 1);
			} else {
				pstmt.setString(92, this.getModifyTime());
			}
			if(this.getEnterKind() == null || this.getEnterKind().equals("null")) {
				pstmt.setNull(93, 1);
			} else {
				pstmt.setString(93, this.getEnterKind());
			}
			if(this.getAmntGrade() == null || this.getAmntGrade().equals("null")) {
				pstmt.setNull(94, 1);
			} else {
				pstmt.setString(94, this.getAmntGrade());
			}
			pstmt.setInt(95, this.getPeoples3());
			pstmt.setInt(96, this.getOnWorkPeoples());
			pstmt.setInt(97, this.getOffWorkPeoples());
			pstmt.setInt(98, this.getOtherPeoples());
			pstmt.setInt(99, this.getRelaPeoples());
			pstmt.setInt(100, this.getRelaMatePeoples());
			pstmt.setInt(101, this.getRelaYoungPeoples());
			pstmt.setInt(102, this.getRelaOtherPeoples());
			if(this.getFirstTrialOperator() == null || this.getFirstTrialOperator().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getFirstTrialOperator());
			}
			if(this.getFirstTrialDate() == null || this.getFirstTrialDate().equals("null")) {
				pstmt.setNull(104, 91);
			} else {
				pstmt.setDate(104, Date.valueOf(this.getFirstTrialDate()));
			}
			if(this.getFirstTrialTime() == null || this.getFirstTrialTime().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getFirstTrialTime());
			}
			if(this.getReceiveOperator() == null || this.getReceiveOperator().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getReceiveOperator());
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(107, 91);
			} else {
				pstmt.setDate(107, Date.valueOf(this.getReceiveDate()));
			}
			if(this.getReceiveTime() == null || this.getReceiveTime().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getReceiveTime());
			}
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getTempFeeNo());
			}
			if(this.getHandlerName() == null || this.getHandlerName().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getHandlerName());
			}
			if(this.getHandlerDate() == null || this.getHandlerDate().equals("null")) {
				pstmt.setNull(111, 91);
			} else {
				pstmt.setDate(111, Date.valueOf(this.getHandlerDate()));
			}
			if(this.getHandlerPrint() == null || this.getHandlerPrint().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getHandlerPrint());
			}
			if(this.getAgentDate() == null || this.getAgentDate().equals("null")) {
				pstmt.setNull(113, 91);
			} else {
				pstmt.setDate(113, Date.valueOf(this.getAgentDate()));
			}
			if(this.getBusinessBigType() == null || this.getBusinessBigType().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getBusinessBigType());
			}
			if(this.getMarketType() == null || this.getMarketType().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getMarketType());
			}
			if(this.getReportNo() == null || this.getReportNo().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getReportNo());
			}
			if(this.getConferNo() == null || this.getConferNo().equals("null")) {
				pstmt.setNull(117, 12);
			} else {
				pstmt.setString(117, this.getConferNo());
			}
			if(this.getSignReportNo() == null || this.getSignReportNo().equals("null")) {
				pstmt.setNull(118, 12);
			} else {
				pstmt.setString(118, this.getSignReportNo());
			}
			if(this.getAgentConferNo() == null || this.getAgentConferNo().equals("null")) {
				pstmt.setNull(119, 12);
			} else {
				pstmt.setString(119, this.getAgentConferNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(120, 12);
			} else {
				pstmt.setString(120, this.getContType());
			}
			if(this.getEdorCalType() == null || this.getEdorCalType().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getEdorCalType());
			}
			if(this.getClientCare() == null || this.getClientCare().equals("null")) {
				pstmt.setNull(122, 12);
			} else {
				pstmt.setString(122, this.getClientCare());
			}
			if(this.getFundReason() == null || this.getFundReason().equals("null")) {
				pstmt.setNull(123, 12);
			} else {
				pstmt.setString(123, this.getFundReason());
			}
			if(this.getBackDateRemark() == null || this.getBackDateRemark().equals("null")) {
				pstmt.setNull(124, 12);
			} else {
				pstmt.setString(124, this.getBackDateRemark());
			}
			if(this.getClientNeedJudge() == null || this.getClientNeedJudge().equals("null")) {
				pstmt.setNull(125, 12);
			} else {
				pstmt.setString(125, this.getClientNeedJudge());
			}
			if(this.getDonateContflag() == null || this.getDonateContflag().equals("null")) {
				pstmt.setNull(126, 12);
			} else {
				pstmt.setString(126, this.getDonateContflag());
			}
			if(this.getFundJudge() == null || this.getFundJudge().equals("null")) {
				pstmt.setNull(127, 12);
			} else {
				pstmt.setString(127, this.getFundJudge());
			}
			if(this.getExamAndAppNo() == null || this.getExamAndAppNo().equals("null")) {
				pstmt.setNull(128, 12);
			} else {
				pstmt.setString(128, this.getExamAndAppNo());
			}
			pstmt.setDouble(129, this.getEdorTransPercent());
			if(this.getCardFlag() == null || this.getCardFlag().equals("null")) {
				pstmt.setNull(130, 1);
			} else {
				pstmt.setString(130, this.getCardFlag());
			}
			if(this.getAgentCodeOper() == null || this.getAgentCodeOper().equals("null")) {
				pstmt.setNull(131, 12);
			} else {
				pstmt.setString(131, this.getAgentCodeOper());
			}
			if(this.getAgentCodeAssi() == null || this.getAgentCodeAssi().equals("null")) {
				pstmt.setNull(132, 12);
			} else {
				pstmt.setString(132, this.getAgentCodeAssi());
			}
			if(this.getDelayReasonCode() == null || this.getDelayReasonCode().equals("null")) {
				pstmt.setNull(133, 1);
			} else {
				pstmt.setString(133, this.getDelayReasonCode());
			}
			if(this.getDelayReasonDesc() == null || this.getDelayReasonDesc().equals("null")) {
				pstmt.setNull(134, 12);
			} else {
				pstmt.setString(134, this.getDelayReasonDesc());
			}
			if(this.getContPlanFlag() == null || this.getContPlanFlag().equals("null")) {
				pstmt.setNull(135, 1);
			} else {
				pstmt.setString(135, this.getContPlanFlag());
			}
			pstmt.setDouble(136, this.getFYCRATE());
			if(this.getContFlag() == null || this.getContFlag().equals("null")) {
				pstmt.setNull(137, 12);
			} else {
				pstmt.setString(137, this.getContFlag());
			}
			if(this.getSaleDepart() == null || this.getSaleDepart().equals("null")) {
				pstmt.setNull(138, 12);
			} else {
				pstmt.setString(138, this.getSaleDepart());
			}
			if(this.getChnlType() == null || this.getChnlType().equals("null")) {
				pstmt.setNull(139, 12);
			} else {
				pstmt.setString(139, this.getChnlType());
			}
			if(this.getAgentBranchesCode() == null || this.getAgentBranchesCode().equals("null")) {
				pstmt.setNull(140, 12);
			} else {
				pstmt.setString(140, this.getAgentBranchesCode());
			}
			if(this.getProjectType() == null || this.getProjectType().equals("null")) {
				pstmt.setNull(141, 12);
			} else {
				pstmt.setString(141, this.getProjectType());
			}
			if(this.getRenewFlag() == null || this.getRenewFlag().equals("null")) {
				pstmt.setNull(142, 12);
			} else {
				pstmt.setString(142, this.getRenewFlag());
			}
			pstmt.setInt(143, this.getRenewCount());
			if(this.getRenewContNo() == null || this.getRenewContNo().equals("null")) {
				pstmt.setNull(144, 12);
			} else {
				pstmt.setString(144, this.getRenewContNo());
			}
			pstmt.setInt(145, this.getInitNumPeople());
			pstmt.setInt(146, this.getInitMult());
			pstmt.setDouble(147, this.getInitAmnt());
			pstmt.setDouble(148, this.getInitRiskAmnt());
			pstmt.setDouble(149, this.getInitPrem());
			pstmt.setDouble(150, this.getInitStandPrem());
			pstmt.setDouble(151, this.getRiskAmnt());
			pstmt.setDouble(152, this.getStandPrem());
			pstmt.setInt(153, this.getSumNumPeople());
			pstmt.setInt(154, this.getInsuYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(155, 12);
			} else {
				pstmt.setString(155, this.getInsuYearFlag());
			}
			pstmt.setInt(156, this.getYears());
			pstmt.setInt(157, this.getPayEndYear());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(158, 12);
			} else {
				pstmt.setString(158, this.getPayEndYearFlag());
			}
			pstmt.setInt(159, this.getPayYears());
			if(this.getValDateType() == null || this.getValDateType().equals("null")) {
				pstmt.setNull(160, 12);
			} else {
				pstmt.setString(160, this.getValDateType());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(161, 91);
			} else {
				pstmt.setDate(161, Date.valueOf(this.getEndDate()));
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(162, 91);
			} else {
				pstmt.setDate(162, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(163, 91);
			} else {
				pstmt.setDate(163, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(164, 91);
			} else {
				pstmt.setDate(164, Date.valueOf(this.getPayToDate()));
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(165, 12);
			} else {
				pstmt.setString(165, this.getAutoPayFlag());
			}
			if(this.getSignOperator() == null || this.getSignOperator().equals("null")) {
				pstmt.setNull(166, 12);
			} else {
				pstmt.setString(166, this.getSignOperator());
			}
			if(this.getCoinsurance() == null || this.getCoinsurance().equals("null")) {
				pstmt.setNull(167, 12);
			} else {
				pstmt.setString(167, this.getCoinsurance());
			}
			pstmt.setDouble(168, this.getAmntShareRate());
			pstmt.setDouble(169, this.getPremShareRate());
			if(this.getScanCom() == null || this.getScanCom().equals("null")) {
				pstmt.setNull(170, 12);
			} else {
				pstmt.setString(170, this.getScanCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(171, 12);
			} else {
				pstmt.setString(171, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(172, 12);
			} else {
				pstmt.setString(172, this.getModifyOperator());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(173, 12);
			} else {
				pstmt.setString(173, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(174, 12);
			} else {
				pstmt.setString(174, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(175, 12);
			} else {
				pstmt.setString(175, this.getGrpContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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
		SQLString sqlObj = new SQLString("LPGrpCont");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPGrpCont VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getProposalGrpContNo() == null || this.getProposalGrpContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getProposalGrpContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPrtNo());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAgentType());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentCode1());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPassword());
			}
			if(this.getPassword2() == null || this.getPassword2().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPassword2());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAppntNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAddressNo());
			}
			pstmt.setInt(17, this.getPeoples2());
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpName());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getGrpNature());
			}
			pstmt.setDouble(21, this.getRgtMoney());
			pstmt.setDouble(22, this.getAsset());
			pstmt.setDouble(23, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getComAera());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getFoundDate()));
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getGrpGroupNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAccName());
			}
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getDisputedFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getOutPayFlag());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getGetPolMode());
			}
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getCurrency());
			}
			pstmt.setInt(42, this.getLostTimes());
			pstmt.setInt(43, this.getPrintCount());
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getLastGetDate() == null || this.getLastGetDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getLastGetDate()));
			}
			if(this.getLastLoanDate() == null || this.getLastLoanDate().equals("null")) {
				pstmt.setNull(47, 91);
			} else {
				pstmt.setDate(47, Date.valueOf(this.getLastLoanDate()));
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getSpecFlag());
			}
			if(this.getGrpSpec() == null || this.getGrpSpec().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getGrpSpec());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getPayMode());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getSignDate()));
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getSignTime());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(54, 91);
			} else {
				pstmt.setDate(54, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(55, this.getPayIntv());
			pstmt.setDouble(56, this.getManageFeeRate());
			pstmt.setInt(57, this.getExpPeoples());
			pstmt.setDouble(58, this.getExpPremium());
			pstmt.setDouble(59, this.getExpAmnt());
			pstmt.setInt(60, this.getPeoples());
			pstmt.setDouble(61, this.getMult());
			pstmt.setDouble(62, this.getPrem());
			pstmt.setDouble(63, this.getAmnt());
			pstmt.setDouble(64, this.getSumPrem());
			pstmt.setDouble(65, this.getSumPay());
			pstmt.setDouble(66, this.getDif());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRemark());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getStandbyFlag3());
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(72, 91);
			} else {
				pstmt.setDate(72, Date.valueOf(this.getInputDate()));
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getInputTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(74, 1);
			} else {
				pstmt.setString(74, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(76, 91);
			} else {
				pstmt.setDate(76, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getApproveTime());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getUWOperator());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(79, 1);
			} else {
				pstmt.setString(79, this.getUWFlag());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(80, 91);
			} else {
				pstmt.setDate(80, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(82, 1);
			} else {
				pstmt.setString(82, this.getAppFlag());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(83, 91);
			} else {
				pstmt.setDate(83, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(84, 91);
			} else {
				pstmt.setDate(84, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(85, 91);
			} else {
				pstmt.setDate(85, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getGetPolTime() == null || this.getGetPolTime().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getGetPolTime());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getState());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(89, 91);
			} else {
				pstmt.setDate(89, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(90, 1);
			} else {
				pstmt.setString(90, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(91, 91);
			} else {
				pstmt.setDate(91, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(92, 1);
			} else {
				pstmt.setString(92, this.getModifyTime());
			}
			if(this.getEnterKind() == null || this.getEnterKind().equals("null")) {
				pstmt.setNull(93, 1);
			} else {
				pstmt.setString(93, this.getEnterKind());
			}
			if(this.getAmntGrade() == null || this.getAmntGrade().equals("null")) {
				pstmt.setNull(94, 1);
			} else {
				pstmt.setString(94, this.getAmntGrade());
			}
			pstmt.setInt(95, this.getPeoples3());
			pstmt.setInt(96, this.getOnWorkPeoples());
			pstmt.setInt(97, this.getOffWorkPeoples());
			pstmt.setInt(98, this.getOtherPeoples());
			pstmt.setInt(99, this.getRelaPeoples());
			pstmt.setInt(100, this.getRelaMatePeoples());
			pstmt.setInt(101, this.getRelaYoungPeoples());
			pstmt.setInt(102, this.getRelaOtherPeoples());
			if(this.getFirstTrialOperator() == null || this.getFirstTrialOperator().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getFirstTrialOperator());
			}
			if(this.getFirstTrialDate() == null || this.getFirstTrialDate().equals("null")) {
				pstmt.setNull(104, 91);
			} else {
				pstmt.setDate(104, Date.valueOf(this.getFirstTrialDate()));
			}
			if(this.getFirstTrialTime() == null || this.getFirstTrialTime().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getFirstTrialTime());
			}
			if(this.getReceiveOperator() == null || this.getReceiveOperator().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getReceiveOperator());
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(107, 91);
			} else {
				pstmt.setDate(107, Date.valueOf(this.getReceiveDate()));
			}
			if(this.getReceiveTime() == null || this.getReceiveTime().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getReceiveTime());
			}
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getTempFeeNo());
			}
			if(this.getHandlerName() == null || this.getHandlerName().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getHandlerName());
			}
			if(this.getHandlerDate() == null || this.getHandlerDate().equals("null")) {
				pstmt.setNull(111, 91);
			} else {
				pstmt.setDate(111, Date.valueOf(this.getHandlerDate()));
			}
			if(this.getHandlerPrint() == null || this.getHandlerPrint().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getHandlerPrint());
			}
			if(this.getAgentDate() == null || this.getAgentDate().equals("null")) {
				pstmt.setNull(113, 91);
			} else {
				pstmt.setDate(113, Date.valueOf(this.getAgentDate()));
			}
			if(this.getBusinessBigType() == null || this.getBusinessBigType().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getBusinessBigType());
			}
			if(this.getMarketType() == null || this.getMarketType().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getMarketType());
			}
			if(this.getReportNo() == null || this.getReportNo().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getReportNo());
			}
			if(this.getConferNo() == null || this.getConferNo().equals("null")) {
				pstmt.setNull(117, 12);
			} else {
				pstmt.setString(117, this.getConferNo());
			}
			if(this.getSignReportNo() == null || this.getSignReportNo().equals("null")) {
				pstmt.setNull(118, 12);
			} else {
				pstmt.setString(118, this.getSignReportNo());
			}
			if(this.getAgentConferNo() == null || this.getAgentConferNo().equals("null")) {
				pstmt.setNull(119, 12);
			} else {
				pstmt.setString(119, this.getAgentConferNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(120, 12);
			} else {
				pstmt.setString(120, this.getContType());
			}
			if(this.getEdorCalType() == null || this.getEdorCalType().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getEdorCalType());
			}
			if(this.getClientCare() == null || this.getClientCare().equals("null")) {
				pstmt.setNull(122, 12);
			} else {
				pstmt.setString(122, this.getClientCare());
			}
			if(this.getFundReason() == null || this.getFundReason().equals("null")) {
				pstmt.setNull(123, 12);
			} else {
				pstmt.setString(123, this.getFundReason());
			}
			if(this.getBackDateRemark() == null || this.getBackDateRemark().equals("null")) {
				pstmt.setNull(124, 12);
			} else {
				pstmt.setString(124, this.getBackDateRemark());
			}
			if(this.getClientNeedJudge() == null || this.getClientNeedJudge().equals("null")) {
				pstmt.setNull(125, 12);
			} else {
				pstmt.setString(125, this.getClientNeedJudge());
			}
			if(this.getDonateContflag() == null || this.getDonateContflag().equals("null")) {
				pstmt.setNull(126, 12);
			} else {
				pstmt.setString(126, this.getDonateContflag());
			}
			if(this.getFundJudge() == null || this.getFundJudge().equals("null")) {
				pstmt.setNull(127, 12);
			} else {
				pstmt.setString(127, this.getFundJudge());
			}
			if(this.getExamAndAppNo() == null || this.getExamAndAppNo().equals("null")) {
				pstmt.setNull(128, 12);
			} else {
				pstmt.setString(128, this.getExamAndAppNo());
			}
			pstmt.setDouble(129, this.getEdorTransPercent());
			if(this.getCardFlag() == null || this.getCardFlag().equals("null")) {
				pstmt.setNull(130, 1);
			} else {
				pstmt.setString(130, this.getCardFlag());
			}
			if(this.getAgentCodeOper() == null || this.getAgentCodeOper().equals("null")) {
				pstmt.setNull(131, 12);
			} else {
				pstmt.setString(131, this.getAgentCodeOper());
			}
			if(this.getAgentCodeAssi() == null || this.getAgentCodeAssi().equals("null")) {
				pstmt.setNull(132, 12);
			} else {
				pstmt.setString(132, this.getAgentCodeAssi());
			}
			if(this.getDelayReasonCode() == null || this.getDelayReasonCode().equals("null")) {
				pstmt.setNull(133, 1);
			} else {
				pstmt.setString(133, this.getDelayReasonCode());
			}
			if(this.getDelayReasonDesc() == null || this.getDelayReasonDesc().equals("null")) {
				pstmt.setNull(134, 12);
			} else {
				pstmt.setString(134, this.getDelayReasonDesc());
			}
			if(this.getContPlanFlag() == null || this.getContPlanFlag().equals("null")) {
				pstmt.setNull(135, 1);
			} else {
				pstmt.setString(135, this.getContPlanFlag());
			}
			pstmt.setDouble(136, this.getFYCRATE());
			if(this.getContFlag() == null || this.getContFlag().equals("null")) {
				pstmt.setNull(137, 12);
			} else {
				pstmt.setString(137, this.getContFlag());
			}
			if(this.getSaleDepart() == null || this.getSaleDepart().equals("null")) {
				pstmt.setNull(138, 12);
			} else {
				pstmt.setString(138, this.getSaleDepart());
			}
			if(this.getChnlType() == null || this.getChnlType().equals("null")) {
				pstmt.setNull(139, 12);
			} else {
				pstmt.setString(139, this.getChnlType());
			}
			if(this.getAgentBranchesCode() == null || this.getAgentBranchesCode().equals("null")) {
				pstmt.setNull(140, 12);
			} else {
				pstmt.setString(140, this.getAgentBranchesCode());
			}
			if(this.getProjectType() == null || this.getProjectType().equals("null")) {
				pstmt.setNull(141, 12);
			} else {
				pstmt.setString(141, this.getProjectType());
			}
			if(this.getRenewFlag() == null || this.getRenewFlag().equals("null")) {
				pstmt.setNull(142, 12);
			} else {
				pstmt.setString(142, this.getRenewFlag());
			}
			pstmt.setInt(143, this.getRenewCount());
			if(this.getRenewContNo() == null || this.getRenewContNo().equals("null")) {
				pstmt.setNull(144, 12);
			} else {
				pstmt.setString(144, this.getRenewContNo());
			}
			pstmt.setInt(145, this.getInitNumPeople());
			pstmt.setInt(146, this.getInitMult());
			pstmt.setDouble(147, this.getInitAmnt());
			pstmt.setDouble(148, this.getInitRiskAmnt());
			pstmt.setDouble(149, this.getInitPrem());
			pstmt.setDouble(150, this.getInitStandPrem());
			pstmt.setDouble(151, this.getRiskAmnt());
			pstmt.setDouble(152, this.getStandPrem());
			pstmt.setInt(153, this.getSumNumPeople());
			pstmt.setInt(154, this.getInsuYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(155, 12);
			} else {
				pstmt.setString(155, this.getInsuYearFlag());
			}
			pstmt.setInt(156, this.getYears());
			pstmt.setInt(157, this.getPayEndYear());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(158, 12);
			} else {
				pstmt.setString(158, this.getPayEndYearFlag());
			}
			pstmt.setInt(159, this.getPayYears());
			if(this.getValDateType() == null || this.getValDateType().equals("null")) {
				pstmt.setNull(160, 12);
			} else {
				pstmt.setString(160, this.getValDateType());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(161, 91);
			} else {
				pstmt.setDate(161, Date.valueOf(this.getEndDate()));
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(162, 91);
			} else {
				pstmt.setDate(162, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(163, 91);
			} else {
				pstmt.setDate(163, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(164, 91);
			} else {
				pstmt.setDate(164, Date.valueOf(this.getPayToDate()));
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(165, 12);
			} else {
				pstmt.setString(165, this.getAutoPayFlag());
			}
			if(this.getSignOperator() == null || this.getSignOperator().equals("null")) {
				pstmt.setNull(166, 12);
			} else {
				pstmt.setString(166, this.getSignOperator());
			}
			if(this.getCoinsurance() == null || this.getCoinsurance().equals("null")) {
				pstmt.setNull(167, 12);
			} else {
				pstmt.setString(167, this.getCoinsurance());
			}
			pstmt.setDouble(168, this.getAmntShareRate());
			pstmt.setDouble(169, this.getPremShareRate());
			if(this.getScanCom() == null || this.getScanCom().equals("null")) {
				pstmt.setNull(170, 12);
			} else {
				pstmt.setString(170, this.getScanCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(171, 12);
			} else {
				pstmt.setString(171, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(172, 12);
			} else {
				pstmt.setString(172, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPGrpCont WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
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
					tError.moduleName = "LPGrpContDB";
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
			tError.moduleName = "LPGrpContDB";
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

	public LPGrpContSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPGrpCont");
			LPGrpContSchema aSchema = this.getSchema();
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
				LPGrpContSchema s1 = new LPGrpContSchema();
				s1.setSchema(rs,i);
				aLPGrpContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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

		return aLPGrpContSet;
	}

	public LPGrpContSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();

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
				LPGrpContSchema s1 = new LPGrpContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGrpContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGrpContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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

		return aLPGrpContSet;
	}

	public LPGrpContSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPGrpCont");
			LPGrpContSchema aSchema = this.getSchema();
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

				LPGrpContSchema s1 = new LPGrpContSchema();
				s1.setSchema(rs,i);
				aLPGrpContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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

		return aLPGrpContSet;
	}

	public LPGrpContSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();

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

				LPGrpContSchema s1 = new LPGrpContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGrpContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGrpContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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

		return aLPGrpContSet;
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
			SQLString sqlObj = new SQLString("LPGrpCont");
			LPGrpContSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPGrpCont " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPGrpContDB";
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
			tError.moduleName = "LPGrpContDB";
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
        tError.moduleName = "LPGrpContDB";
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
        tError.moduleName = "LPGrpContDB";
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
        tError.moduleName = "LPGrpContDB";
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
        tError.moduleName = "LPGrpContDB";
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
 * @return LPGrpContSet
 */
public LPGrpContSet getData()
{
    int tCount = 0;
    LPGrpContSet tLPGrpContSet = new LPGrpContSet();
    LPGrpContSchema tLPGrpContSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPGrpContDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPGrpContSchema = new LPGrpContSchema();
        tLPGrpContSchema.setSchema(mResultSet, 1);
        tLPGrpContSet.add(tLPGrpContSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPGrpContSchema = new LPGrpContSchema();
                tLPGrpContSchema.setSchema(mResultSet, 1);
                tLPGrpContSet.add(tLPGrpContSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPGrpContDB";
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
    return tLPGrpContSet;
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
            tError.moduleName = "LPGrpContDB";
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
        tError.moduleName = "LPGrpContDB";
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
            tError.moduleName = "LPGrpContDB";
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
        tError.moduleName = "LPGrpContDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPGrpContSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();

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
				LPGrpContSchema s1 = new LPGrpContSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGrpContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGrpContSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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

		return aLPGrpContSet;
	}

	public LPGrpContSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPGrpContSet aLPGrpContSet = new LPGrpContSet();

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

				LPGrpContSchema s1 = new LPGrpContSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPGrpContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPGrpContSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGrpContDB";
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

		return aLPGrpContSet; 
	}

}
