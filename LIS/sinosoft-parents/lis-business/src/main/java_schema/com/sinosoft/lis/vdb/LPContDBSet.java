/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPContDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPContDBSet extends LPContSet
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
	public LPContDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LPCont");
		mflag = true;
	}

	public LPContDBSet()
	{
		db = new DBOper( "LPCont" );
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
			tError.moduleName = "LPContDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LPCont WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCont");
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
			tError.moduleName = "LPContDBSet";
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
			pstmt = con.prepareStatement("UPDATE LPCont SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtNo = ? , ContType = ? , FamilyType = ? , FamilyID = ? , PolType = ? , CardFlag = ? , ManageCom = ? , ExecuteCom = ? , AgentCom = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , AgentType = ? , SaleChnl = ? , Handler = ? , Password = ? , AppntNo = ? , AppntName = ? , AppntSex = ? , AppntBirthday = ? , AppntIDType = ? , AppntIDNo = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredIDType = ? , InsuredIDNo = ? , PayIntv = ? , PayMode = ? , PayLocation = ? , DisputedFlag = ? , OutPayFlag = ? , GetPolMode = ? , SignCom = ? , SignDate = ? , SignTime = ? , ConsignNo = ? , BankCode = ? , BankAccNo = ? , AccName = ? , PrintCount = ? , LostTimes = ? , Lang = ? , Currency = ? , Remark = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , Dif = ? , PaytoDate = ? , FirstPayDate = ? , CValiDate = ? , InputOperator = ? , InputDate = ? , InputTime = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , PolApplyDate = ? , GetPolDate = ? , GetPolTime = ? , CustomGetPolDate = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , FirstTrialOperator = ? , FirstTrialDate = ? , FirstTrialTime = ? , ReceiveOperator = ? , ReceiveDate = ? , ReceiveTime = ? , TempFeeNo = ? , SellType = ? , ForceUWFlag = ? , ForceUWReason = ? , NewBankCode = ? , NewBankAccNo = ? , NewAccName = ? , NewPayMode = ? , AgentBankCode = ? , BankAgent = ? , AutoPayFlag = ? , RnewFlag = ? , FamilyContNo = ? , BussFlag = ? , SignName = ? , OrganizeDate = ? , OrganizeTime = ? , NewAutoSendBankFlag = ? , AgentCodeOper = ? , AgentCodeAssi = ? , DelayReasonCode = ? , DelayReasonDesc = ? , XQremindflag = ? , OrganComCode = ? , ContFlag = ? , SaleDepart = ? , ChnlType = ? , RenewCount = ? , RenewContNo = ? , InitNumPeople = ? , InitMult = ? , InitAmnt = ? , InitRiskAmnt = ? , InitPrem = ? , InitStandPrem = ? , RiskAmnt = ? , StandPrem = ? , SumNumPeople = ? , SumPay = ? , EndDate = ? , PayEndDate = ? , AccKind = ? , SignOperator = ? , LoanStartDate = ? , LoanEndDate = ? , AgentBranchesCode = ? , NeedBillFlag = ? , ComCode = ? , ModifyOperator = ? WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getContNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getProposalContNo());
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
			if(this.get(i).getFamilyType() == null || this.get(i).getFamilyType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFamilyType());
			}
			if(this.get(i).getFamilyID() == null || this.get(i).getFamilyID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getFamilyID());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPolType());
			}
			if(this.get(i).getCardFlag() == null || this.get(i).getCardFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCardFlag());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getManageCom());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getExecuteCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAgentCode1());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getSaleChnl());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getHandler());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPassword());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAppntName());
			}
			if(this.get(i).getAppntSex() == null || this.get(i).getAppntSex().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAppntSex());
			}
			if(this.get(i).getAppntBirthday() == null || this.get(i).getAppntBirthday().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getAppntBirthday()));
			}
			if(this.get(i).getAppntIDType() == null || this.get(i).getAppntIDType().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAppntIDType());
			}
			if(this.get(i).getAppntIDNo() == null || this.get(i).getAppntIDNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAppntIDNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			if(this.get(i).getInsuredIDType() == null || this.get(i).getInsuredIDType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getInsuredIDType());
			}
			if(this.get(i).getInsuredIDNo() == null || this.get(i).getInsuredIDNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getInsuredIDNo());
			}
			pstmt.setInt(34, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getPayMode());
			}
			if(this.get(i).getPayLocation() == null || this.get(i).getPayLocation().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getPayLocation());
			}
			if(this.get(i).getDisputedFlag() == null || this.get(i).getDisputedFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getDisputedFlag());
			}
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOutPayFlag());
			}
			if(this.get(i).getGetPolMode() == null || this.get(i).getGetPolMode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetPolMode());
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getSignCom());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getSignTime());
			}
			if(this.get(i).getConsignNo() == null || this.get(i).getConsignNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getConsignNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getAccName());
			}
			pstmt.setInt(47, this.get(i).getPrintCount());
			pstmt.setInt(48, this.get(i).getLostTimes());
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getLang());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getCurrency());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getRemark());
			}
			pstmt.setInt(52, this.get(i).getPeoples());
			pstmt.setDouble(53, this.get(i).getMult());
			pstmt.setDouble(54, this.get(i).getPrem());
			pstmt.setDouble(55, this.get(i).getAmnt());
			pstmt.setDouble(56, this.get(i).getSumPrem());
			pstmt.setDouble(57, this.get(i).getDif());
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(59,null);
			} else {
				pstmt.setDate(59, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(60,null);
			} else {
				pstmt.setDate(60, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getInputOperator() == null || this.get(i).getInputOperator().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getInputOperator());
			}
			if(this.get(i).getInputDate() == null || this.get(i).getInputDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getInputDate()));
			}
			if(this.get(i).getInputTime() == null || this.get(i).getInputTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getInputTime());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(66,null);
			} else {
				pstmt.setDate(66, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getAppFlag());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getGetPolTime() == null || this.get(i).getGetPolTime().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getGetPolTime());
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(76,null);
			} else {
				pstmt.setDate(76, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(79,null);
			} else {
				pstmt.setDate(79, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(81,null);
			} else {
				pstmt.setDate(81, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getModifyTime());
			}
			if(this.get(i).getFirstTrialOperator() == null || this.get(i).getFirstTrialOperator().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getFirstTrialOperator());
			}
			if(this.get(i).getFirstTrialDate() == null || this.get(i).getFirstTrialDate().equals("null")) {
				pstmt.setDate(84,null);
			} else {
				pstmt.setDate(84, Date.valueOf(this.get(i).getFirstTrialDate()));
			}
			if(this.get(i).getFirstTrialTime() == null || this.get(i).getFirstTrialTime().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getFirstTrialTime());
			}
			if(this.get(i).getReceiveOperator() == null || this.get(i).getReceiveOperator().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getReceiveOperator());
			}
			if(this.get(i).getReceiveDate() == null || this.get(i).getReceiveDate().equals("null")) {
				pstmt.setDate(87,null);
			} else {
				pstmt.setDate(87, Date.valueOf(this.get(i).getReceiveDate()));
			}
			if(this.get(i).getReceiveTime() == null || this.get(i).getReceiveTime().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getReceiveTime());
			}
			if(this.get(i).getTempFeeNo() == null || this.get(i).getTempFeeNo().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getTempFeeNo());
			}
			if(this.get(i).getSellType() == null || this.get(i).getSellType().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getSellType());
			}
			if(this.get(i).getForceUWFlag() == null || this.get(i).getForceUWFlag().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getForceUWFlag());
			}
			if(this.get(i).getForceUWReason() == null || this.get(i).getForceUWReason().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getForceUWReason());
			}
			if(this.get(i).getNewBankCode() == null || this.get(i).getNewBankCode().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getNewBankCode());
			}
			if(this.get(i).getNewBankAccNo() == null || this.get(i).getNewBankAccNo().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getNewBankAccNo());
			}
			if(this.get(i).getNewAccName() == null || this.get(i).getNewAccName().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getNewAccName());
			}
			if(this.get(i).getNewPayMode() == null || this.get(i).getNewPayMode().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getNewPayMode());
			}
			if(this.get(i).getAgentBankCode() == null || this.get(i).getAgentBankCode().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getAgentBankCode());
			}
			if(this.get(i).getBankAgent() == null || this.get(i).getBankAgent().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getBankAgent());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getAutoPayFlag());
			}
			pstmt.setInt(100, this.get(i).getRnewFlag());
			if(this.get(i).getFamilyContNo() == null || this.get(i).getFamilyContNo().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getFamilyContNo());
			}
			if(this.get(i).getBussFlag() == null || this.get(i).getBussFlag().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getBussFlag());
			}
			if(this.get(i).getSignName() == null || this.get(i).getSignName().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getSignName());
			}
			if(this.get(i).getOrganizeDate() == null || this.get(i).getOrganizeDate().equals("null")) {
				pstmt.setDate(104,null);
			} else {
				pstmt.setDate(104, Date.valueOf(this.get(i).getOrganizeDate()));
			}
			if(this.get(i).getOrganizeTime() == null || this.get(i).getOrganizeTime().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getOrganizeTime());
			}
			if(this.get(i).getNewAutoSendBankFlag() == null || this.get(i).getNewAutoSendBankFlag().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getNewAutoSendBankFlag());
			}
			if(this.get(i).getAgentCodeOper() == null || this.get(i).getAgentCodeOper().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getAgentCodeOper());
			}
			if(this.get(i).getAgentCodeAssi() == null || this.get(i).getAgentCodeAssi().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getAgentCodeAssi());
			}
			if(this.get(i).getDelayReasonCode() == null || this.get(i).getDelayReasonCode().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getDelayReasonCode());
			}
			if(this.get(i).getDelayReasonDesc() == null || this.get(i).getDelayReasonDesc().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getDelayReasonDesc());
			}
			if(this.get(i).getXQremindflag() == null || this.get(i).getXQremindflag().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getXQremindflag());
			}
			if(this.get(i).getOrganComCode() == null || this.get(i).getOrganComCode().equals("null")) {
				pstmt.setString(112,null);
			} else {
				pstmt.setString(112, this.get(i).getOrganComCode());
			}
			if(this.get(i).getContFlag() == null || this.get(i).getContFlag().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getContFlag());
			}
			if(this.get(i).getSaleDepart() == null || this.get(i).getSaleDepart().equals("null")) {
				pstmt.setString(114,null);
			} else {
				pstmt.setString(114, this.get(i).getSaleDepart());
			}
			if(this.get(i).getChnlType() == null || this.get(i).getChnlType().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getChnlType());
			}
			pstmt.setInt(116, this.get(i).getRenewCount());
			if(this.get(i).getRenewContNo() == null || this.get(i).getRenewContNo().equals("null")) {
				pstmt.setString(117,null);
			} else {
				pstmt.setString(117, this.get(i).getRenewContNo());
			}
			pstmt.setInt(118, this.get(i).getInitNumPeople());
			pstmt.setInt(119, this.get(i).getInitMult());
			pstmt.setDouble(120, this.get(i).getInitAmnt());
			pstmt.setDouble(121, this.get(i).getInitRiskAmnt());
			pstmt.setDouble(122, this.get(i).getInitPrem());
			pstmt.setDouble(123, this.get(i).getInitStandPrem());
			pstmt.setDouble(124, this.get(i).getRiskAmnt());
			pstmt.setDouble(125, this.get(i).getStandPrem());
			pstmt.setInt(126, this.get(i).getSumNumPeople());
			pstmt.setDouble(127, this.get(i).getSumPay());
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(128,null);
			} else {
				pstmt.setDate(128, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(129,null);
			} else {
				pstmt.setDate(129, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getAccKind() == null || this.get(i).getAccKind().equals("null")) {
				pstmt.setString(130,null);
			} else {
				pstmt.setString(130, this.get(i).getAccKind());
			}
			if(this.get(i).getSignOperator() == null || this.get(i).getSignOperator().equals("null")) {
				pstmt.setString(131,null);
			} else {
				pstmt.setString(131, this.get(i).getSignOperator());
			}
			if(this.get(i).getLoanStartDate() == null || this.get(i).getLoanStartDate().equals("null")) {
				pstmt.setDate(132,null);
			} else {
				pstmt.setDate(132, Date.valueOf(this.get(i).getLoanStartDate()));
			}
			if(this.get(i).getLoanEndDate() == null || this.get(i).getLoanEndDate().equals("null")) {
				pstmt.setDate(133,null);
			} else {
				pstmt.setDate(133, Date.valueOf(this.get(i).getLoanEndDate()));
			}
			if(this.get(i).getAgentBranchesCode() == null || this.get(i).getAgentBranchesCode().equals("null")) {
				pstmt.setString(134,null);
			} else {
				pstmt.setString(134, this.get(i).getAgentBranchesCode());
			}
			if(this.get(i).getNeedBillFlag() == null || this.get(i).getNeedBillFlag().equals("null")) {
				pstmt.setString(135,null);
			} else {
				pstmt.setString(135, this.get(i).getNeedBillFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(136,null);
			} else {
				pstmt.setString(136, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(137,null);
			} else {
				pstmt.setString(137, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(138,null);
			} else {
				pstmt.setString(138, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(139,null);
			} else {
				pstmt.setString(139, this.get(i).getEdorType());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(140,null);
			} else {
				pstmt.setString(140, this.get(i).getContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCont");
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
			tError.moduleName = "LPContDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LPCont VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getContNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getProposalContNo());
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
			if(this.get(i).getFamilyType() == null || this.get(i).getFamilyType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFamilyType());
			}
			if(this.get(i).getFamilyID() == null || this.get(i).getFamilyID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getFamilyID());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPolType());
			}
			if(this.get(i).getCardFlag() == null || this.get(i).getCardFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCardFlag());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getManageCom());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getExecuteCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAgentCode1());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getSaleChnl());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getHandler());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPassword());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAppntName());
			}
			if(this.get(i).getAppntSex() == null || this.get(i).getAppntSex().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAppntSex());
			}
			if(this.get(i).getAppntBirthday() == null || this.get(i).getAppntBirthday().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getAppntBirthday()));
			}
			if(this.get(i).getAppntIDType() == null || this.get(i).getAppntIDType().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAppntIDType());
			}
			if(this.get(i).getAppntIDNo() == null || this.get(i).getAppntIDNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAppntIDNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			if(this.get(i).getInsuredIDType() == null || this.get(i).getInsuredIDType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getInsuredIDType());
			}
			if(this.get(i).getInsuredIDNo() == null || this.get(i).getInsuredIDNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getInsuredIDNo());
			}
			pstmt.setInt(34, this.get(i).getPayIntv());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getPayMode());
			}
			if(this.get(i).getPayLocation() == null || this.get(i).getPayLocation().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getPayLocation());
			}
			if(this.get(i).getDisputedFlag() == null || this.get(i).getDisputedFlag().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getDisputedFlag());
			}
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOutPayFlag());
			}
			if(this.get(i).getGetPolMode() == null || this.get(i).getGetPolMode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetPolMode());
			}
			if(this.get(i).getSignCom() == null || this.get(i).getSignCom().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getSignCom());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getSignTime());
			}
			if(this.get(i).getConsignNo() == null || this.get(i).getConsignNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getConsignNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getAccName());
			}
			pstmt.setInt(47, this.get(i).getPrintCount());
			pstmt.setInt(48, this.get(i).getLostTimes());
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getLang());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getCurrency());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getRemark());
			}
			pstmt.setInt(52, this.get(i).getPeoples());
			pstmt.setDouble(53, this.get(i).getMult());
			pstmt.setDouble(54, this.get(i).getPrem());
			pstmt.setDouble(55, this.get(i).getAmnt());
			pstmt.setDouble(56, this.get(i).getSumPrem());
			pstmt.setDouble(57, this.get(i).getDif());
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(59,null);
			} else {
				pstmt.setDate(59, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(60,null);
			} else {
				pstmt.setDate(60, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getInputOperator() == null || this.get(i).getInputOperator().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getInputOperator());
			}
			if(this.get(i).getInputDate() == null || this.get(i).getInputDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getInputDate()));
			}
			if(this.get(i).getInputTime() == null || this.get(i).getInputTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getInputTime());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(66,null);
			} else {
				pstmt.setDate(66, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getAppFlag());
			}
			if(this.get(i).getPolApplyDate() == null || this.get(i).getPolApplyDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getPolApplyDate()));
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getGetPolTime() == null || this.get(i).getGetPolTime().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getGetPolTime());
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(76,null);
			} else {
				pstmt.setDate(76, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(79,null);
			} else {
				pstmt.setDate(79, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(81,null);
			} else {
				pstmt.setDate(81, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getModifyTime());
			}
			if(this.get(i).getFirstTrialOperator() == null || this.get(i).getFirstTrialOperator().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getFirstTrialOperator());
			}
			if(this.get(i).getFirstTrialDate() == null || this.get(i).getFirstTrialDate().equals("null")) {
				pstmt.setDate(84,null);
			} else {
				pstmt.setDate(84, Date.valueOf(this.get(i).getFirstTrialDate()));
			}
			if(this.get(i).getFirstTrialTime() == null || this.get(i).getFirstTrialTime().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getFirstTrialTime());
			}
			if(this.get(i).getReceiveOperator() == null || this.get(i).getReceiveOperator().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getReceiveOperator());
			}
			if(this.get(i).getReceiveDate() == null || this.get(i).getReceiveDate().equals("null")) {
				pstmt.setDate(87,null);
			} else {
				pstmt.setDate(87, Date.valueOf(this.get(i).getReceiveDate()));
			}
			if(this.get(i).getReceiveTime() == null || this.get(i).getReceiveTime().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getReceiveTime());
			}
			if(this.get(i).getTempFeeNo() == null || this.get(i).getTempFeeNo().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getTempFeeNo());
			}
			if(this.get(i).getSellType() == null || this.get(i).getSellType().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getSellType());
			}
			if(this.get(i).getForceUWFlag() == null || this.get(i).getForceUWFlag().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getForceUWFlag());
			}
			if(this.get(i).getForceUWReason() == null || this.get(i).getForceUWReason().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getForceUWReason());
			}
			if(this.get(i).getNewBankCode() == null || this.get(i).getNewBankCode().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getNewBankCode());
			}
			if(this.get(i).getNewBankAccNo() == null || this.get(i).getNewBankAccNo().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getNewBankAccNo());
			}
			if(this.get(i).getNewAccName() == null || this.get(i).getNewAccName().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getNewAccName());
			}
			if(this.get(i).getNewPayMode() == null || this.get(i).getNewPayMode().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getNewPayMode());
			}
			if(this.get(i).getAgentBankCode() == null || this.get(i).getAgentBankCode().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getAgentBankCode());
			}
			if(this.get(i).getBankAgent() == null || this.get(i).getBankAgent().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getBankAgent());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getAutoPayFlag());
			}
			pstmt.setInt(100, this.get(i).getRnewFlag());
			if(this.get(i).getFamilyContNo() == null || this.get(i).getFamilyContNo().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getFamilyContNo());
			}
			if(this.get(i).getBussFlag() == null || this.get(i).getBussFlag().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getBussFlag());
			}
			if(this.get(i).getSignName() == null || this.get(i).getSignName().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getSignName());
			}
			if(this.get(i).getOrganizeDate() == null || this.get(i).getOrganizeDate().equals("null")) {
				pstmt.setDate(104,null);
			} else {
				pstmt.setDate(104, Date.valueOf(this.get(i).getOrganizeDate()));
			}
			if(this.get(i).getOrganizeTime() == null || this.get(i).getOrganizeTime().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getOrganizeTime());
			}
			if(this.get(i).getNewAutoSendBankFlag() == null || this.get(i).getNewAutoSendBankFlag().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getNewAutoSendBankFlag());
			}
			if(this.get(i).getAgentCodeOper() == null || this.get(i).getAgentCodeOper().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getAgentCodeOper());
			}
			if(this.get(i).getAgentCodeAssi() == null || this.get(i).getAgentCodeAssi().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getAgentCodeAssi());
			}
			if(this.get(i).getDelayReasonCode() == null || this.get(i).getDelayReasonCode().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getDelayReasonCode());
			}
			if(this.get(i).getDelayReasonDesc() == null || this.get(i).getDelayReasonDesc().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getDelayReasonDesc());
			}
			if(this.get(i).getXQremindflag() == null || this.get(i).getXQremindflag().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getXQremindflag());
			}
			if(this.get(i).getOrganComCode() == null || this.get(i).getOrganComCode().equals("null")) {
				pstmt.setString(112,null);
			} else {
				pstmt.setString(112, this.get(i).getOrganComCode());
			}
			if(this.get(i).getContFlag() == null || this.get(i).getContFlag().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getContFlag());
			}
			if(this.get(i).getSaleDepart() == null || this.get(i).getSaleDepart().equals("null")) {
				pstmt.setString(114,null);
			} else {
				pstmt.setString(114, this.get(i).getSaleDepart());
			}
			if(this.get(i).getChnlType() == null || this.get(i).getChnlType().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getChnlType());
			}
			pstmt.setInt(116, this.get(i).getRenewCount());
			if(this.get(i).getRenewContNo() == null || this.get(i).getRenewContNo().equals("null")) {
				pstmt.setString(117,null);
			} else {
				pstmt.setString(117, this.get(i).getRenewContNo());
			}
			pstmt.setInt(118, this.get(i).getInitNumPeople());
			pstmt.setInt(119, this.get(i).getInitMult());
			pstmt.setDouble(120, this.get(i).getInitAmnt());
			pstmt.setDouble(121, this.get(i).getInitRiskAmnt());
			pstmt.setDouble(122, this.get(i).getInitPrem());
			pstmt.setDouble(123, this.get(i).getInitStandPrem());
			pstmt.setDouble(124, this.get(i).getRiskAmnt());
			pstmt.setDouble(125, this.get(i).getStandPrem());
			pstmt.setInt(126, this.get(i).getSumNumPeople());
			pstmt.setDouble(127, this.get(i).getSumPay());
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(128,null);
			} else {
				pstmt.setDate(128, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(129,null);
			} else {
				pstmt.setDate(129, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getAccKind() == null || this.get(i).getAccKind().equals("null")) {
				pstmt.setString(130,null);
			} else {
				pstmt.setString(130, this.get(i).getAccKind());
			}
			if(this.get(i).getSignOperator() == null || this.get(i).getSignOperator().equals("null")) {
				pstmt.setString(131,null);
			} else {
				pstmt.setString(131, this.get(i).getSignOperator());
			}
			if(this.get(i).getLoanStartDate() == null || this.get(i).getLoanStartDate().equals("null")) {
				pstmt.setDate(132,null);
			} else {
				pstmt.setDate(132, Date.valueOf(this.get(i).getLoanStartDate()));
			}
			if(this.get(i).getLoanEndDate() == null || this.get(i).getLoanEndDate().equals("null")) {
				pstmt.setDate(133,null);
			} else {
				pstmt.setDate(133, Date.valueOf(this.get(i).getLoanEndDate()));
			}
			if(this.get(i).getAgentBranchesCode() == null || this.get(i).getAgentBranchesCode().equals("null")) {
				pstmt.setString(134,null);
			} else {
				pstmt.setString(134, this.get(i).getAgentBranchesCode());
			}
			if(this.get(i).getNeedBillFlag() == null || this.get(i).getNeedBillFlag().equals("null")) {
				pstmt.setString(135,null);
			} else {
				pstmt.setString(135, this.get(i).getNeedBillFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(136,null);
			} else {
				pstmt.setString(136, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(137,null);
			} else {
				pstmt.setString(137, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPCont");
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
			tError.moduleName = "LPContDBSet";
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
