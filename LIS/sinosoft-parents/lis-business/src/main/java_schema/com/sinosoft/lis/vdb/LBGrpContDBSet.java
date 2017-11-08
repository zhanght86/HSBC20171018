/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBGrpContSchema;
import com.sinosoft.lis.vschema.LBGrpContSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBGrpContDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_13
 */
public class LBGrpContDBSet extends LBGrpContSet
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
	public LBGrpContDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBGrpCont");
		mflag = true;
	}

	public LBGrpContDBSet()
	{
		db = new DBOper( "LBGrpCont" );
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
			tError.moduleName = "LBGrpContDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBGrpCont WHERE  GrpContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpCont");
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
			tError.moduleName = "LBGrpContDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBGrpCont SET  EdorNo = ? , GrpContNo = ? , ProposalGrpContNo = ? , PrtNo = ? , SaleChnl = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , Password = ? , Password2 = ? , AppntNo = ? , AddressNo = ? , Peoples2 = ? , GrpName = ? , BusinessType = ? , GrpNature = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , GrpGroupNo = ? , BankCode = ? , BankAccNo = ? , AccName = ? , DisputedFlag = ? , OutPayFlag = ? , GetPolMode = ? , Lang = ? , Currency = ? , LostTimes = ? , PrintCount = ? , RegetDate = ? , LastEdorDate = ? , LastGetDate = ? , LastLoanDate = ? , SpecFlag = ? , GrpSpec = ? , PayMode = ? , SignCom = ? , SignDate = ? , SignTime = ? , CValiDate = ? , PayIntv = ? , ManageFeeRate = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , Remark = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , InputOperator = ? , InputDate = ? , InputTime = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWOperator = ? , UWFlag = ? , UWDate = ? , UWTime = ? , AppFlag = ? , PolApplyDate = ? , CustomGetPolDate = ? , GetPolDate = ? , GetPolTime = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , EnterKind = ? , AmntGrade = ? , Peoples3 = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , RelaPeoples = ? , RelaMatePeoples = ? , RelaYoungPeoples = ? , RelaOtherPeoples = ? , FirstTrialOperator = ? , FirstTrialDate = ? , FirstTrialTime = ? , ReceiveOperator = ? , ReceiveDate = ? , ReceiveTime = ? , TempFeeNo = ? , HandlerName = ? , HandlerDate = ? , HandlerPrint = ? , AgentDate = ? , BusinessBigType = ? , MarketType = ? , ReportNo = ? , ConferNo = ? , SignReportNo = ? , AgentConferNo = ? , ContType = ? , EdorCalType = ? , ClientCare = ? , FundReason = ? , BackDateRemark = ? , ClientNeedJudge = ? , DonateContflag = ? , FundJudge = ? , ExamAndAppNo = ? , AgentCodeOper = ? , AgentCodeAssi = ? , DelayReasonCode = ? , DelayReasonDesc = ? WHERE  GrpContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSaleChnl());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentCode1());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPassword());
			}
			if(this.get(i).getPassword2() == null || this.get(i).getPassword2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPassword2());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAppntNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAddressNo());
			}
			pstmt.setInt(16, this.get(i).getPeoples2());
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpName());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getGrpNature());
			}
			pstmt.setDouble(20, this.get(i).getRgtMoney());
			pstmt.setDouble(21, this.get(i).getAsset());
			pstmt.setDouble(22, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getComAera());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getGrpGroupNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getAccName());
			}
			if(this.get(i).getDisputedFlag() == null || this.get(i).getDisputedFlag().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDisputedFlag());
			}
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOutPayFlag());
			}
			if(this.get(i).getGetPolMode() == null || this.get(i).getGetPolMode().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getGetPolMode());
			}
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getLang());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getCurrency());
			}
			pstmt.setInt(41, this.get(i).getLostTimes());
			pstmt.setInt(42, this.get(i).getPrintCount());
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(44,null);
			} else {
				pstmt.setDate(44, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getLastGetDate() == null || this.get(i).getLastGetDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getLastGetDate()));
			}
			if(this.get(i).getLastLoanDate() == null || this.get(i).getLastLoanDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getLastLoanDate()));
			}
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getSpecFlag());
			}
			if(this.get(i).getGrpSpec() == null || this.get(i).getGrpSpec().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getGrpSpec());
			}
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getPayMode());
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSignCom());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getSignTime());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(54, this.get(i).getPayIntv());
			pstmt.setDouble(55, this.get(i).getManageFeeRate());
			pstmt.setInt(56, this.get(i).getExpPeoples());
			pstmt.setDouble(57, this.get(i).getExpPremium());
			pstmt.setDouble(58, this.get(i).getExpAmnt());
			pstmt.setInt(59, this.get(i).getPeoples());
			pstmt.setDouble(60, this.get(i).getMult());
			pstmt.setDouble(61, this.get(i).getPrem());
			pstmt.setDouble(62, this.get(i).getAmnt());
			pstmt.setDouble(63, this.get(i).getSumPrem());
			pstmt.setDouble(64, this.get(i).getSumPay());
			pstmt.setDouble(65, this.get(i).getDif());
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getInputOperator() == null || this.get(i).getInputOperator().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getInputOperator());
			}
			if(this.get(i).getInputDate() == null || this.get(i).getInputDate().equals("null")) {
				pstmt.setDate(71,null);
			} else {
				pstmt.setDate(71, Date.valueOf(this.get(i).getInputDate()));
			}
			if(this.get(i).getInputTime() == null || this.get(i).getInputTime().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getInputTime());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(75,null);
			} else {
				pstmt.setDate(75, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(79,null);
			} else {
				pstmt.setDate(79, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getAppFlag());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(82,null);
			} else {
				pstmt.setDate(82, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(83,null);
			} else {
				pstmt.setDate(83, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(84,null);
			} else {
				pstmt.setDate(84, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getGetPolTime() == null || this.get(i).getGetPolTime().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getGetPolTime());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(88,null);
			} else {
				pstmt.setDate(88, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(90,null);
			} else {
				pstmt.setDate(90, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getModifyTime());
			}
			if(this.get(i).getEnterKind() == null || this.get(i).getEnterKind().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getEnterKind());
			}
			if(this.get(i).getAmntGrade() == null || this.get(i).getAmntGrade().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getAmntGrade());
			}
			pstmt.setInt(94, this.get(i).getPeoples3());
			pstmt.setInt(95, this.get(i).getOnWorkPeoples());
			pstmt.setInt(96, this.get(i).getOffWorkPeoples());
			pstmt.setInt(97, this.get(i).getOtherPeoples());
			pstmt.setInt(98, this.get(i).getRelaPeoples());
			pstmt.setInt(99, this.get(i).getRelaMatePeoples());
			pstmt.setInt(100, this.get(i).getRelaYoungPeoples());
			pstmt.setInt(101, this.get(i).getRelaOtherPeoples());
			if(this.get(i).getFirstTrialOperator() == null || this.get(i).getFirstTrialOperator().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getFirstTrialOperator());
			}
			if(this.get(i).getFirstTrialDate() == null || this.get(i).getFirstTrialDate().equals("null")) {
				pstmt.setDate(103,null);
			} else {
				pstmt.setDate(103, Date.valueOf(this.get(i).getFirstTrialDate()));
			}
			if(this.get(i).getFirstTrialTime() == null || this.get(i).getFirstTrialTime().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getFirstTrialTime());
			}
			if(this.get(i).getReceiveOperator() == null || this.get(i).getReceiveOperator().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getReceiveOperator());
			}
			if(this.get(i).getReceiveDate() == null || this.get(i).getReceiveDate().equals("null")) {
				pstmt.setDate(106,null);
			} else {
				pstmt.setDate(106, Date.valueOf(this.get(i).getReceiveDate()));
			}
			if(this.get(i).getReceiveTime() == null || this.get(i).getReceiveTime().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getReceiveTime());
			}
			if(this.get(i).getTempFeeNo() == null || this.get(i).getTempFeeNo().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getTempFeeNo());
			}
			if(this.get(i).getHandlerName() == null || this.get(i).getHandlerName().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getHandlerName());
			}
			if(this.get(i).getHandlerDate() == null || this.get(i).getHandlerDate().equals("null")) {
				pstmt.setDate(110,null);
			} else {
				pstmt.setDate(110, Date.valueOf(this.get(i).getHandlerDate()));
			}
			if(this.get(i).getHandlerPrint() == null || this.get(i).getHandlerPrint().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getHandlerPrint());
			}
			if(this.get(i).getAgentDate() == null || this.get(i).getAgentDate().equals("null")) {
				pstmt.setDate(112,null);
			} else {
				pstmt.setDate(112, Date.valueOf(this.get(i).getAgentDate()));
			}
			if(this.get(i).getBusinessBigType() == null || this.get(i).getBusinessBigType().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getBusinessBigType());
			}
			if(this.get(i).getMarketType() == null || this.get(i).getMarketType().equals("null")) {
				pstmt.setString(114,null);
			} else {
				pstmt.setString(114, this.get(i).getMarketType());
			}
			if(this.get(i).getReportNo() == null || this.get(i).getReportNo().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getReportNo());
			}
			if(this.get(i).getConferNo() == null || this.get(i).getConferNo().equals("null")) {
				pstmt.setString(116,null);
			} else {
				pstmt.setString(116, this.get(i).getConferNo());
			}
			if(this.get(i).getSignReportNo() == null || this.get(i).getSignReportNo().equals("null")) {
				pstmt.setString(117,null);
			} else {
				pstmt.setString(117, this.get(i).getSignReportNo());
			}
			if(this.get(i).getAgentConferNo() == null || this.get(i).getAgentConferNo().equals("null")) {
				pstmt.setString(118,null);
			} else {
				pstmt.setString(118, this.get(i).getAgentConferNo());
			}
			if(this.get(i).getContType() == null || this.get(i).getContType().equals("null")) {
				pstmt.setString(119,null);
			} else {
				pstmt.setString(119, this.get(i).getContType());
			}
			if(this.get(i).getEdorCalType() == null || this.get(i).getEdorCalType().equals("null")) {
				pstmt.setString(120,null);
			} else {
				pstmt.setString(120, this.get(i).getEdorCalType());
			}
			if(this.get(i).getClientCare() == null || this.get(i).getClientCare().equals("null")) {
				pstmt.setString(121,null);
			} else {
				pstmt.setString(121, this.get(i).getClientCare());
			}
			if(this.get(i).getFundReason() == null || this.get(i).getFundReason().equals("null")) {
				pstmt.setString(122,null);
			} else {
				pstmt.setString(122, this.get(i).getFundReason());
			}
			if(this.get(i).getBackDateRemark() == null || this.get(i).getBackDateRemark().equals("null")) {
				pstmt.setString(123,null);
			} else {
				pstmt.setString(123, this.get(i).getBackDateRemark());
			}
			if(this.get(i).getClientNeedJudge() == null || this.get(i).getClientNeedJudge().equals("null")) {
				pstmt.setString(124,null);
			} else {
				pstmt.setString(124, this.get(i).getClientNeedJudge());
			}
			if(this.get(i).getDonateContflag() == null || this.get(i).getDonateContflag().equals("null")) {
				pstmt.setString(125,null);
			} else {
				pstmt.setString(125, this.get(i).getDonateContflag());
			}
			if(this.get(i).getFundJudge() == null || this.get(i).getFundJudge().equals("null")) {
				pstmt.setString(126,null);
			} else {
				pstmt.setString(126, this.get(i).getFundJudge());
			}
			if(this.get(i).getExamAndAppNo() == null || this.get(i).getExamAndAppNo().equals("null")) {
				pstmt.setString(127,null);
			} else {
				pstmt.setString(127, this.get(i).getExamAndAppNo());
			}
			if(this.get(i).getAgentCodeOper() == null || this.get(i).getAgentCodeOper().equals("null")) {
				pstmt.setString(128,null);
			} else {
				pstmt.setString(128, this.get(i).getAgentCodeOper());
			}
			if(this.get(i).getAgentCodeAssi() == null || this.get(i).getAgentCodeAssi().equals("null")) {
				pstmt.setString(129,null);
			} else {
				pstmt.setString(129, this.get(i).getAgentCodeAssi());
			}
			if(this.get(i).getDelayReasonCode() == null || this.get(i).getDelayReasonCode().equals("null")) {
				pstmt.setString(130,null);
			} else {
				pstmt.setString(130, this.get(i).getDelayReasonCode());
			}
			if(this.get(i).getDelayReasonDesc() == null || this.get(i).getDelayReasonDesc().equals("null")) {
				pstmt.setString(131,null);
			} else {
				pstmt.setString(131, this.get(i).getDelayReasonDesc());
			}
			// set where condition
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(132,null);
			} else {
				pstmt.setString(132, this.get(i).getGrpContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpCont");
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
			tError.moduleName = "LBGrpContDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBGrpCont VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSaleChnl());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentCode1());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPassword());
			}
			if(this.get(i).getPassword2() == null || this.get(i).getPassword2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPassword2());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAppntNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAddressNo());
			}
			pstmt.setInt(16, this.get(i).getPeoples2());
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpName());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getGrpNature());
			}
			pstmt.setDouble(20, this.get(i).getRgtMoney());
			pstmt.setDouble(21, this.get(i).getAsset());
			pstmt.setDouble(22, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getComAera());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getGrpGroupNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getAccName());
			}
			if(this.get(i).getDisputedFlag() == null || this.get(i).getDisputedFlag().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getDisputedFlag());
			}
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOutPayFlag());
			}
			if(this.get(i).getGetPolMode() == null || this.get(i).getGetPolMode().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getGetPolMode());
			}
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getLang());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getCurrency());
			}
			pstmt.setInt(41, this.get(i).getLostTimes());
			pstmt.setInt(42, this.get(i).getPrintCount());
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(44,null);
			} else {
				pstmt.setDate(44, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getLastGetDate() == null || this.get(i).getLastGetDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getLastGetDate()));
			}
			if(this.get(i).getLastLoanDate() == null || this.get(i).getLastLoanDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getLastLoanDate()));
			}
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getSpecFlag());
			}
			if(this.get(i).getGrpSpec() == null || this.get(i).getGrpSpec().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getGrpSpec());
			}
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getPayMode());
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSignCom());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getSignTime());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(54, this.get(i).getPayIntv());
			pstmt.setDouble(55, this.get(i).getManageFeeRate());
			pstmt.setInt(56, this.get(i).getExpPeoples());
			pstmt.setDouble(57, this.get(i).getExpPremium());
			pstmt.setDouble(58, this.get(i).getExpAmnt());
			pstmt.setInt(59, this.get(i).getPeoples());
			pstmt.setDouble(60, this.get(i).getMult());
			pstmt.setDouble(61, this.get(i).getPrem());
			pstmt.setDouble(62, this.get(i).getAmnt());
			pstmt.setDouble(63, this.get(i).getSumPrem());
			pstmt.setDouble(64, this.get(i).getSumPay());
			pstmt.setDouble(65, this.get(i).getDif());
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getInputOperator() == null || this.get(i).getInputOperator().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getInputOperator());
			}
			if(this.get(i).getInputDate() == null || this.get(i).getInputDate().equals("null")) {
				pstmt.setDate(71,null);
			} else {
				pstmt.setDate(71, Date.valueOf(this.get(i).getInputDate()));
			}
			if(this.get(i).getInputTime() == null || this.get(i).getInputTime().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getInputTime());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(75,null);
			} else {
				pstmt.setDate(75, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(79,null);
			} else {
				pstmt.setDate(79, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getAppFlag());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(82,null);
			} else {
				pstmt.setDate(82, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(83,null);
			} else {
				pstmt.setDate(83, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(84,null);
			} else {
				pstmt.setDate(84, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getGetPolTime() == null || this.get(i).getGetPolTime().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getGetPolTime());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(88,null);
			} else {
				pstmt.setDate(88, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(90,null);
			} else {
				pstmt.setDate(90, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getModifyTime());
			}
			if(this.get(i).getEnterKind() == null || this.get(i).getEnterKind().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getEnterKind());
			}
			if(this.get(i).getAmntGrade() == null || this.get(i).getAmntGrade().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getAmntGrade());
			}
			pstmt.setInt(94, this.get(i).getPeoples3());
			pstmt.setInt(95, this.get(i).getOnWorkPeoples());
			pstmt.setInt(96, this.get(i).getOffWorkPeoples());
			pstmt.setInt(97, this.get(i).getOtherPeoples());
			pstmt.setInt(98, this.get(i).getRelaPeoples());
			pstmt.setInt(99, this.get(i).getRelaMatePeoples());
			pstmt.setInt(100, this.get(i).getRelaYoungPeoples());
			pstmt.setInt(101, this.get(i).getRelaOtherPeoples());
			if(this.get(i).getFirstTrialOperator() == null || this.get(i).getFirstTrialOperator().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getFirstTrialOperator());
			}
			if(this.get(i).getFirstTrialDate() == null || this.get(i).getFirstTrialDate().equals("null")) {
				pstmt.setDate(103,null);
			} else {
				pstmt.setDate(103, Date.valueOf(this.get(i).getFirstTrialDate()));
			}
			if(this.get(i).getFirstTrialTime() == null || this.get(i).getFirstTrialTime().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getFirstTrialTime());
			}
			if(this.get(i).getReceiveOperator() == null || this.get(i).getReceiveOperator().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getReceiveOperator());
			}
			if(this.get(i).getReceiveDate() == null || this.get(i).getReceiveDate().equals("null")) {
				pstmt.setDate(106,null);
			} else {
				pstmt.setDate(106, Date.valueOf(this.get(i).getReceiveDate()));
			}
			if(this.get(i).getReceiveTime() == null || this.get(i).getReceiveTime().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getReceiveTime());
			}
			if(this.get(i).getTempFeeNo() == null || this.get(i).getTempFeeNo().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getTempFeeNo());
			}
			if(this.get(i).getHandlerName() == null || this.get(i).getHandlerName().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getHandlerName());
			}
			if(this.get(i).getHandlerDate() == null || this.get(i).getHandlerDate().equals("null")) {
				pstmt.setDate(110,null);
			} else {
				pstmt.setDate(110, Date.valueOf(this.get(i).getHandlerDate()));
			}
			if(this.get(i).getHandlerPrint() == null || this.get(i).getHandlerPrint().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getHandlerPrint());
			}
			if(this.get(i).getAgentDate() == null || this.get(i).getAgentDate().equals("null")) {
				pstmt.setDate(112,null);
			} else {
				pstmt.setDate(112, Date.valueOf(this.get(i).getAgentDate()));
			}
			if(this.get(i).getBusinessBigType() == null || this.get(i).getBusinessBigType().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getBusinessBigType());
			}
			if(this.get(i).getMarketType() == null || this.get(i).getMarketType().equals("null")) {
				pstmt.setString(114,null);
			} else {
				pstmt.setString(114, this.get(i).getMarketType());
			}
			if(this.get(i).getReportNo() == null || this.get(i).getReportNo().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getReportNo());
			}
			if(this.get(i).getConferNo() == null || this.get(i).getConferNo().equals("null")) {
				pstmt.setString(116,null);
			} else {
				pstmt.setString(116, this.get(i).getConferNo());
			}
			if(this.get(i).getSignReportNo() == null || this.get(i).getSignReportNo().equals("null")) {
				pstmt.setString(117,null);
			} else {
				pstmt.setString(117, this.get(i).getSignReportNo());
			}
			if(this.get(i).getAgentConferNo() == null || this.get(i).getAgentConferNo().equals("null")) {
				pstmt.setString(118,null);
			} else {
				pstmt.setString(118, this.get(i).getAgentConferNo());
			}
			if(this.get(i).getContType() == null || this.get(i).getContType().equals("null")) {
				pstmt.setString(119,null);
			} else {
				pstmt.setString(119, this.get(i).getContType());
			}
			if(this.get(i).getEdorCalType() == null || this.get(i).getEdorCalType().equals("null")) {
				pstmt.setString(120,null);
			} else {
				pstmt.setString(120, this.get(i).getEdorCalType());
			}
			if(this.get(i).getClientCare() == null || this.get(i).getClientCare().equals("null")) {
				pstmt.setString(121,null);
			} else {
				pstmt.setString(121, this.get(i).getClientCare());
			}
			if(this.get(i).getFundReason() == null || this.get(i).getFundReason().equals("null")) {
				pstmt.setString(122,null);
			} else {
				pstmt.setString(122, this.get(i).getFundReason());
			}
			if(this.get(i).getBackDateRemark() == null || this.get(i).getBackDateRemark().equals("null")) {
				pstmt.setString(123,null);
			} else {
				pstmt.setString(123, this.get(i).getBackDateRemark());
			}
			if(this.get(i).getClientNeedJudge() == null || this.get(i).getClientNeedJudge().equals("null")) {
				pstmt.setString(124,null);
			} else {
				pstmt.setString(124, this.get(i).getClientNeedJudge());
			}
			if(this.get(i).getDonateContflag() == null || this.get(i).getDonateContflag().equals("null")) {
				pstmt.setString(125,null);
			} else {
				pstmt.setString(125, this.get(i).getDonateContflag());
			}
			if(this.get(i).getFundJudge() == null || this.get(i).getFundJudge().equals("null")) {
				pstmt.setString(126,null);
			} else {
				pstmt.setString(126, this.get(i).getFundJudge());
			}
			if(this.get(i).getExamAndAppNo() == null || this.get(i).getExamAndAppNo().equals("null")) {
				pstmt.setString(127,null);
			} else {
				pstmt.setString(127, this.get(i).getExamAndAppNo());
			}
			if(this.get(i).getAgentCodeOper() == null || this.get(i).getAgentCodeOper().equals("null")) {
				pstmt.setString(128,null);
			} else {
				pstmt.setString(128, this.get(i).getAgentCodeOper());
			}
			if(this.get(i).getAgentCodeAssi() == null || this.get(i).getAgentCodeAssi().equals("null")) {
				pstmt.setString(129,null);
			} else {
				pstmt.setString(129, this.get(i).getAgentCodeAssi());
			}
			if(this.get(i).getDelayReasonCode() == null || this.get(i).getDelayReasonCode().equals("null")) {
				pstmt.setString(130,null);
			} else {
				pstmt.setString(130, this.get(i).getDelayReasonCode());
			}
			if(this.get(i).getDelayReasonDesc() == null || this.get(i).getDelayReasonDesc().equals("null")) {
				pstmt.setString(131,null);
			} else {
				pstmt.setString(131, this.get(i).getDelayReasonDesc());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpCont");
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
			tError.moduleName = "LBGrpContDBSet";
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
