/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCContDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCContDB extends LCContSchema
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
	public LCContDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCCont" );
		mflag = true;
	}

	public LCContDB()
	{
		con = null;
		db = new DBOper( "LCCont" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCContSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCContSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCCont WHERE  ContNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCCont");
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
		SQLString sqlObj = new SQLString("LCCont");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCCont SET  GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtNo = ? , ContType = ? , FamilyType = ? , FamilyID = ? , PolType = ? , CardFlag = ? , ManageCom = ? , ExecuteCom = ? , AgentCom = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , AgentType = ? , SaleChnl = ? , Handler = ? , Password = ? , AppntNo = ? , AppntName = ? , AppntSex = ? , AppntBirthday = ? , AppntIDType = ? , AppntIDNo = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredIDType = ? , InsuredIDNo = ? , PayIntv = ? , PayMode = ? , PayLocation = ? , DisputedFlag = ? , OutPayFlag = ? , GetPolMode = ? , SignCom = ? , SignDate = ? , SignTime = ? , ConsignNo = ? , BankCode = ? , BankAccNo = ? , AccName = ? , PrintCount = ? , LostTimes = ? , Lang = ? , Currency = ? , Remark = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , Dif = ? , PaytoDate = ? , FirstPayDate = ? , CValiDate = ? , InputOperator = ? , InputDate = ? , InputTime = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , PolApplyDate = ? , GetPolDate = ? , GetPolTime = ? , CustomGetPolDate = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , FirstTrialOperator = ? , FirstTrialDate = ? , FirstTrialTime = ? , ReceiveOperator = ? , ReceiveDate = ? , ReceiveTime = ? , TempFeeNo = ? , SellType = ? , ForceUWFlag = ? , ForceUWReason = ? , NewBankCode = ? , NewBankAccNo = ? , NewAccName = ? , NewPayMode = ? , AgentBankCode = ? , BankAgent = ? , AutoPayFlag = ? , RnewFlag = ? , FamilyContNo = ? , BussFlag = ? , SignName = ? , OrganizeDate = ? , OrganizeTime = ? , NewAutoSendBankFlag = ? , AgentCodeOper = ? , AgentCodeAssi = ? , DelayReasonCode = ? , DelayReasonDesc = ? , XQremindflag = ? , OrganComCode = ? , ContFlag = ? , SaleDepart = ? , ChnlType = ? , RenewCount = ? , RenewContNo = ? , InitNumPeople = ? , InitMult = ? , InitAmnt = ? , InitRiskAmnt = ? , InitPrem = ? , InitStandPrem = ? , RiskAmnt = ? , StandPrem = ? , SumNumPeople = ? , SumPay = ? , EndDate = ? , PayEndDate = ? , AccKind = ? , SignOperator = ? , LoanStartDate = ? , LoanEndDate = ? , AgentBranchesCode = ? , NeedBillFlag = ? , ComCode = ? , ModifyOperator = ? WHERE  ContNo = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getContType());
			}
			if(this.getFamilyType() == null || this.getFamilyType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getFamilyType());
			}
			if(this.getFamilyID() == null || this.getFamilyID().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getFamilyID());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getPolType());
			}
			if(this.getCardFlag() == null || this.getCardFlag().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getCardFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getManageCom());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getExecuteCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentCom());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentCode1());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getHandler());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPassword());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAppntNo());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAppntName());
			}
			if(this.getAppntSex() == null || this.getAppntSex().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getAppntSex());
			}
			if(this.getAppntBirthday() == null || this.getAppntBirthday().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getAppntBirthday()));
			}
			if(this.getAppntIDType() == null || this.getAppntIDType().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getAppntIDType());
			}
			if(this.getAppntIDNo() == null || this.getAppntIDNo().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAppntIDNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getInsuredName());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getInsuredBirthday()));
			}
			if(this.getInsuredIDType() == null || this.getInsuredIDType().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getInsuredIDType());
			}
			if(this.getInsuredIDNo() == null || this.getInsuredIDNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getInsuredIDNo());
			}
			pstmt.setInt(32, this.getPayIntv());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getPayLocation());
			}
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getDisputedFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getOutPayFlag());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getGetPolMode());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getSignDate()));
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getSignTime());
			}
			if(this.getConsignNo() == null || this.getConsignNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getConsignNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAccName());
			}
			pstmt.setInt(45, this.getPrintCount());
			pstmt.setInt(46, this.getLostTimes());
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getCurrency());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getRemark());
			}
			pstmt.setInt(50, this.getPeoples());
			pstmt.setDouble(51, this.getMult());
			pstmt.setDouble(52, this.getPrem());
			pstmt.setDouble(53, this.getAmnt());
			pstmt.setDouble(54, this.getSumPrem());
			pstmt.setDouble(55, this.getDif());
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(56, 91);
			} else {
				pstmt.setDate(56, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCValiDate()));
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(60, 91);
			} else {
				pstmt.setDate(60, Date.valueOf(this.getInputDate()));
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getInputTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(62, 1);
			} else {
				pstmt.setString(62, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(68, 91);
			} else {
				pstmt.setDate(68, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(70, 1);
			} else {
				pstmt.setString(70, this.getAppFlag());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(72, 91);
			} else {
				pstmt.setDate(72, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getGetPolTime() == null || this.getGetPolTime().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getGetPolTime());
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getState());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(77, 91);
			} else {
				pstmt.setDate(77, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(79, 91);
			} else {
				pstmt.setDate(79, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(80, 1);
			} else {
				pstmt.setString(80, this.getModifyTime());
			}
			if(this.getFirstTrialOperator() == null || this.getFirstTrialOperator().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getFirstTrialOperator());
			}
			if(this.getFirstTrialDate() == null || this.getFirstTrialDate().equals("null")) {
				pstmt.setNull(82, 91);
			} else {
				pstmt.setDate(82, Date.valueOf(this.getFirstTrialDate()));
			}
			if(this.getFirstTrialTime() == null || this.getFirstTrialTime().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getFirstTrialTime());
			}
			if(this.getReceiveOperator() == null || this.getReceiveOperator().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getReceiveOperator());
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(85, 91);
			} else {
				pstmt.setDate(85, Date.valueOf(this.getReceiveDate()));
			}
			if(this.getReceiveTime() == null || this.getReceiveTime().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getReceiveTime());
			}
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getTempFeeNo());
			}
			if(this.getSellType() == null || this.getSellType().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getSellType());
			}
			if(this.getForceUWFlag() == null || this.getForceUWFlag().equals("null")) {
				pstmt.setNull(89, 1);
			} else {
				pstmt.setString(89, this.getForceUWFlag());
			}
			if(this.getForceUWReason() == null || this.getForceUWReason().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getForceUWReason());
			}
			if(this.getNewBankCode() == null || this.getNewBankCode().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getNewBankCode());
			}
			if(this.getNewBankAccNo() == null || this.getNewBankAccNo().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getNewBankAccNo());
			}
			if(this.getNewAccName() == null || this.getNewAccName().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getNewAccName());
			}
			if(this.getNewPayMode() == null || this.getNewPayMode().equals("null")) {
				pstmt.setNull(94, 1);
			} else {
				pstmt.setString(94, this.getNewPayMode());
			}
			if(this.getAgentBankCode() == null || this.getAgentBankCode().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getAgentBankCode());
			}
			if(this.getBankAgent() == null || this.getBankAgent().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getBankAgent());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getAutoPayFlag());
			}
			pstmt.setInt(98, this.getRnewFlag());
			if(this.getFamilyContNo() == null || this.getFamilyContNo().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getFamilyContNo());
			}
			if(this.getBussFlag() == null || this.getBussFlag().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getBussFlag());
			}
			if(this.getSignName() == null || this.getSignName().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getSignName());
			}
			if(this.getOrganizeDate() == null || this.getOrganizeDate().equals("null")) {
				pstmt.setNull(102, 91);
			} else {
				pstmt.setDate(102, Date.valueOf(this.getOrganizeDate()));
			}
			if(this.getOrganizeTime() == null || this.getOrganizeTime().equals("null")) {
				pstmt.setNull(103, 1);
			} else {
				pstmt.setString(103, this.getOrganizeTime());
			}
			if(this.getNewAutoSendBankFlag() == null || this.getNewAutoSendBankFlag().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getNewAutoSendBankFlag());
			}
			if(this.getAgentCodeOper() == null || this.getAgentCodeOper().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getAgentCodeOper());
			}
			if(this.getAgentCodeAssi() == null || this.getAgentCodeAssi().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getAgentCodeAssi());
			}
			if(this.getDelayReasonCode() == null || this.getDelayReasonCode().equals("null")) {
				pstmt.setNull(107, 1);
			} else {
				pstmt.setString(107, this.getDelayReasonCode());
			}
			if(this.getDelayReasonDesc() == null || this.getDelayReasonDesc().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getDelayReasonDesc());
			}
			if(this.getXQremindflag() == null || this.getXQremindflag().equals("null")) {
				pstmt.setNull(109, 1);
			} else {
				pstmt.setString(109, this.getXQremindflag());
			}
			if(this.getOrganComCode() == null || this.getOrganComCode().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getOrganComCode());
			}
			if(this.getContFlag() == null || this.getContFlag().equals("null")) {
				pstmt.setNull(111, 12);
			} else {
				pstmt.setString(111, this.getContFlag());
			}
			if(this.getSaleDepart() == null || this.getSaleDepart().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getSaleDepart());
			}
			if(this.getChnlType() == null || this.getChnlType().equals("null")) {
				pstmt.setNull(113, 12);
			} else {
				pstmt.setString(113, this.getChnlType());
			}
			pstmt.setInt(114, this.getRenewCount());
			if(this.getRenewContNo() == null || this.getRenewContNo().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getRenewContNo());
			}
			pstmt.setInt(116, this.getInitNumPeople());
			pstmt.setInt(117, this.getInitMult());
			pstmt.setDouble(118, this.getInitAmnt());
			pstmt.setDouble(119, this.getInitRiskAmnt());
			pstmt.setDouble(120, this.getInitPrem());
			pstmt.setDouble(121, this.getInitStandPrem());
			pstmt.setDouble(122, this.getRiskAmnt());
			pstmt.setDouble(123, this.getStandPrem());
			pstmt.setInt(124, this.getSumNumPeople());
			pstmt.setDouble(125, this.getSumPay());
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(126, 91);
			} else {
				pstmt.setDate(126, Date.valueOf(this.getEndDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(127, 91);
			} else {
				pstmt.setDate(127, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getAccKind() == null || this.getAccKind().equals("null")) {
				pstmt.setNull(128, 12);
			} else {
				pstmt.setString(128, this.getAccKind());
			}
			if(this.getSignOperator() == null || this.getSignOperator().equals("null")) {
				pstmt.setNull(129, 12);
			} else {
				pstmt.setString(129, this.getSignOperator());
			}
			if(this.getLoanStartDate() == null || this.getLoanStartDate().equals("null")) {
				pstmt.setNull(130, 91);
			} else {
				pstmt.setDate(130, Date.valueOf(this.getLoanStartDate()));
			}
			if(this.getLoanEndDate() == null || this.getLoanEndDate().equals("null")) {
				pstmt.setNull(131, 91);
			} else {
				pstmt.setDate(131, Date.valueOf(this.getLoanEndDate()));
			}
			if(this.getAgentBranchesCode() == null || this.getAgentBranchesCode().equals("null")) {
				pstmt.setNull(132, 12);
			} else {
				pstmt.setString(132, this.getAgentBranchesCode());
			}
			if(this.getNeedBillFlag() == null || this.getNeedBillFlag().equals("null")) {
				pstmt.setNull(133, 12);
			} else {
				pstmt.setString(133, this.getNeedBillFlag());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(134, 12);
			} else {
				pstmt.setString(134, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(135, 12);
			} else {
				pstmt.setString(135, this.getModifyOperator());
			}
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(136, 12);
			} else {
				pstmt.setString(136, this.getContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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
		SQLString sqlObj = new SQLString("LCCont");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCCont VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getContType());
			}
			if(this.getFamilyType() == null || this.getFamilyType().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getFamilyType());
			}
			if(this.getFamilyID() == null || this.getFamilyID().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getFamilyID());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getPolType());
			}
			if(this.getCardFlag() == null || this.getCardFlag().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getCardFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getManageCom());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getExecuteCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentCom());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentCode1());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getHandler());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPassword());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAppntNo());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAppntName());
			}
			if(this.getAppntSex() == null || this.getAppntSex().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getAppntSex());
			}
			if(this.getAppntBirthday() == null || this.getAppntBirthday().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getAppntBirthday()));
			}
			if(this.getAppntIDType() == null || this.getAppntIDType().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getAppntIDType());
			}
			if(this.getAppntIDNo() == null || this.getAppntIDNo().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAppntIDNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getInsuredName());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getInsuredBirthday()));
			}
			if(this.getInsuredIDType() == null || this.getInsuredIDType().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getInsuredIDType());
			}
			if(this.getInsuredIDNo() == null || this.getInsuredIDNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getInsuredIDNo());
			}
			pstmt.setInt(32, this.getPayIntv());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getPayLocation());
			}
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(35, 1);
			} else {
				pstmt.setString(35, this.getDisputedFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getOutPayFlag());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getGetPolMode());
			}
			if(this.getSignCom() == null || this.getSignCom().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getSignCom());
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getSignDate()));
			}
			if(this.getSignTime() == null || this.getSignTime().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getSignTime());
			}
			if(this.getConsignNo() == null || this.getConsignNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getConsignNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAccName());
			}
			pstmt.setInt(45, this.getPrintCount());
			pstmt.setInt(46, this.getLostTimes());
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getCurrency());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getRemark());
			}
			pstmt.setInt(50, this.getPeoples());
			pstmt.setDouble(51, this.getMult());
			pstmt.setDouble(52, this.getPrem());
			pstmt.setDouble(53, this.getAmnt());
			pstmt.setDouble(54, this.getSumPrem());
			pstmt.setDouble(55, this.getDif());
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(56, 91);
			} else {
				pstmt.setDate(56, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCValiDate()));
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(60, 91);
			} else {
				pstmt.setDate(60, Date.valueOf(this.getInputDate()));
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getInputTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(62, 1);
			} else {
				pstmt.setString(62, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(68, 91);
			} else {
				pstmt.setDate(68, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(70, 1);
			} else {
				pstmt.setString(70, this.getAppFlag());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getPolApplyDate()));
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(72, 91);
			} else {
				pstmt.setDate(72, Date.valueOf(this.getGetPolDate()));
			}
			if(this.getGetPolTime() == null || this.getGetPolTime().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getGetPolTime());
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getCustomGetPolDate()));
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getState());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(77, 91);
			} else {
				pstmt.setDate(77, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(79, 91);
			} else {
				pstmt.setDate(79, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(80, 1);
			} else {
				pstmt.setString(80, this.getModifyTime());
			}
			if(this.getFirstTrialOperator() == null || this.getFirstTrialOperator().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getFirstTrialOperator());
			}
			if(this.getFirstTrialDate() == null || this.getFirstTrialDate().equals("null")) {
				pstmt.setNull(82, 91);
			} else {
				pstmt.setDate(82, Date.valueOf(this.getFirstTrialDate()));
			}
			if(this.getFirstTrialTime() == null || this.getFirstTrialTime().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getFirstTrialTime());
			}
			if(this.getReceiveOperator() == null || this.getReceiveOperator().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getReceiveOperator());
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(85, 91);
			} else {
				pstmt.setDate(85, Date.valueOf(this.getReceiveDate()));
			}
			if(this.getReceiveTime() == null || this.getReceiveTime().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getReceiveTime());
			}
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getTempFeeNo());
			}
			if(this.getSellType() == null || this.getSellType().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getSellType());
			}
			if(this.getForceUWFlag() == null || this.getForceUWFlag().equals("null")) {
				pstmt.setNull(89, 1);
			} else {
				pstmt.setString(89, this.getForceUWFlag());
			}
			if(this.getForceUWReason() == null || this.getForceUWReason().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getForceUWReason());
			}
			if(this.getNewBankCode() == null || this.getNewBankCode().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getNewBankCode());
			}
			if(this.getNewBankAccNo() == null || this.getNewBankAccNo().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getNewBankAccNo());
			}
			if(this.getNewAccName() == null || this.getNewAccName().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getNewAccName());
			}
			if(this.getNewPayMode() == null || this.getNewPayMode().equals("null")) {
				pstmt.setNull(94, 1);
			} else {
				pstmt.setString(94, this.getNewPayMode());
			}
			if(this.getAgentBankCode() == null || this.getAgentBankCode().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getAgentBankCode());
			}
			if(this.getBankAgent() == null || this.getBankAgent().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getBankAgent());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getAutoPayFlag());
			}
			pstmt.setInt(98, this.getRnewFlag());
			if(this.getFamilyContNo() == null || this.getFamilyContNo().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getFamilyContNo());
			}
			if(this.getBussFlag() == null || this.getBussFlag().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getBussFlag());
			}
			if(this.getSignName() == null || this.getSignName().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getSignName());
			}
			if(this.getOrganizeDate() == null || this.getOrganizeDate().equals("null")) {
				pstmt.setNull(102, 91);
			} else {
				pstmt.setDate(102, Date.valueOf(this.getOrganizeDate()));
			}
			if(this.getOrganizeTime() == null || this.getOrganizeTime().equals("null")) {
				pstmt.setNull(103, 1);
			} else {
				pstmt.setString(103, this.getOrganizeTime());
			}
			if(this.getNewAutoSendBankFlag() == null || this.getNewAutoSendBankFlag().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getNewAutoSendBankFlag());
			}
			if(this.getAgentCodeOper() == null || this.getAgentCodeOper().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getAgentCodeOper());
			}
			if(this.getAgentCodeAssi() == null || this.getAgentCodeAssi().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getAgentCodeAssi());
			}
			if(this.getDelayReasonCode() == null || this.getDelayReasonCode().equals("null")) {
				pstmt.setNull(107, 1);
			} else {
				pstmt.setString(107, this.getDelayReasonCode());
			}
			if(this.getDelayReasonDesc() == null || this.getDelayReasonDesc().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getDelayReasonDesc());
			}
			if(this.getXQremindflag() == null || this.getXQremindflag().equals("null")) {
				pstmt.setNull(109, 1);
			} else {
				pstmt.setString(109, this.getXQremindflag());
			}
			if(this.getOrganComCode() == null || this.getOrganComCode().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getOrganComCode());
			}
			if(this.getContFlag() == null || this.getContFlag().equals("null")) {
				pstmt.setNull(111, 12);
			} else {
				pstmt.setString(111, this.getContFlag());
			}
			if(this.getSaleDepart() == null || this.getSaleDepart().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getSaleDepart());
			}
			if(this.getChnlType() == null || this.getChnlType().equals("null")) {
				pstmt.setNull(113, 12);
			} else {
				pstmt.setString(113, this.getChnlType());
			}
			pstmt.setInt(114, this.getRenewCount());
			if(this.getRenewContNo() == null || this.getRenewContNo().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getRenewContNo());
			}
			pstmt.setInt(116, this.getInitNumPeople());
			pstmt.setInt(117, this.getInitMult());
			pstmt.setDouble(118, this.getInitAmnt());
			pstmt.setDouble(119, this.getInitRiskAmnt());
			pstmt.setDouble(120, this.getInitPrem());
			pstmt.setDouble(121, this.getInitStandPrem());
			pstmt.setDouble(122, this.getRiskAmnt());
			pstmt.setDouble(123, this.getStandPrem());
			pstmt.setInt(124, this.getSumNumPeople());
			pstmt.setDouble(125, this.getSumPay());
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(126, 91);
			} else {
				pstmt.setDate(126, Date.valueOf(this.getEndDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(127, 91);
			} else {
				pstmt.setDate(127, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getAccKind() == null || this.getAccKind().equals("null")) {
				pstmt.setNull(128, 12);
			} else {
				pstmt.setString(128, this.getAccKind());
			}
			if(this.getSignOperator() == null || this.getSignOperator().equals("null")) {
				pstmt.setNull(129, 12);
			} else {
				pstmt.setString(129, this.getSignOperator());
			}
			if(this.getLoanStartDate() == null || this.getLoanStartDate().equals("null")) {
				pstmt.setNull(130, 91);
			} else {
				pstmt.setDate(130, Date.valueOf(this.getLoanStartDate()));
			}
			if(this.getLoanEndDate() == null || this.getLoanEndDate().equals("null")) {
				pstmt.setNull(131, 91);
			} else {
				pstmt.setDate(131, Date.valueOf(this.getLoanEndDate()));
			}
			if(this.getAgentBranchesCode() == null || this.getAgentBranchesCode().equals("null")) {
				pstmt.setNull(132, 12);
			} else {
				pstmt.setString(132, this.getAgentBranchesCode());
			}
			if(this.getNeedBillFlag() == null || this.getNeedBillFlag().equals("null")) {
				pstmt.setNull(133, 12);
			} else {
				pstmt.setString(133, this.getNeedBillFlag());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(134, 12);
			} else {
				pstmt.setString(134, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(135, 12);
			} else {
				pstmt.setString(135, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCCont WHERE  ContNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
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
					tError.moduleName = "LCContDB";
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
			tError.moduleName = "LCContDB";
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

	public LCContSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCContSet aLCContSet = new LCContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCCont");
			LCContSchema aSchema = this.getSchema();
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
				LCContSchema s1 = new LCContSchema();
				s1.setSchema(rs,i);
				aLCContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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

		return aLCContSet;
	}

	public LCContSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCContSet aLCContSet = new LCContSet();

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
				LCContSchema s1 = new LCContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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

		return aLCContSet;
	}

	public LCContSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCContSet aLCContSet = new LCContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCCont");
			LCContSchema aSchema = this.getSchema();
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

				LCContSchema s1 = new LCContSchema();
				s1.setSchema(rs,i);
				aLCContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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

		return aLCContSet;
	}

	public LCContSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCContSet aLCContSet = new LCContSet();

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

				LCContSchema s1 = new LCContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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

		return aLCContSet;
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
			SQLString sqlObj = new SQLString("LCCont");
			LCContSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCCont " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContDB";
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
			tError.moduleName = "LCContDB";
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
        tError.moduleName = "LCContDB";
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
        tError.moduleName = "LCContDB";
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
        tError.moduleName = "LCContDB";
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
        tError.moduleName = "LCContDB";
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
 * @return LCContSet
 */
public LCContSet getData()
{
    int tCount = 0;
    LCContSet tLCContSet = new LCContSet();
    LCContSchema tLCContSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCContDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCContSchema = new LCContSchema();
        tLCContSchema.setSchema(mResultSet, 1);
        tLCContSet.add(tLCContSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCContSchema = new LCContSchema();
                tLCContSchema.setSchema(mResultSet, 1);
                tLCContSet.add(tLCContSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCContDB";
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
    return tLCContSet;
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
            tError.moduleName = "LCContDB";
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
        tError.moduleName = "LCContDB";
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
            tError.moduleName = "LCContDB";
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
        tError.moduleName = "LCContDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCContSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCContSet aLCContSet = new LCContSet();

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
				LCContSchema s1 = new LCContSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCContSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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

		return aLCContSet;
	}

	public LCContSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCContSet aLCContSet = new LCContSet();

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

				LCContSchema s1 = new LCContSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCContSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContDB";
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

		return aLCContSet; 
	}

}
