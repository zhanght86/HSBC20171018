/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.vschema.LBPOContSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOContDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPOContDB extends LBPOContSchema
{
private static Logger logger = Logger.getLogger(LBPOContDB.class);

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
	public LBPOContDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBPOCont" );
		mflag = true;
	}

	public LBPOContDB()
	{
		con = null;
		db = new DBOper( "LBPOCont" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBPOContSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBPOContSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOCont WHERE  ContNo = ? AND InputNo = ? AND FillNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.setInt(2, this.getInputNo());
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
			tError.moduleName = "LBPOContDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOCont");
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
		SQLString sqlObj = new SQLString("LBPOCont");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBPOCont SET  GrpContNo = ? , ContNo = ? , InputNo = ? , FillNo = ? , ProposalContNo = ? , PrtNo = ? , ContType = ? , FamilyType = ? , FamilyID = ? , PolType = ? , CardFlag = ? , ManageCom = ? , ExecuteCom = ? , AgentCom = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , AgentType = ? , SaleChnl = ? , Handler = ? , Password = ? , PayIntv = ? , PayMode = ? , PayLocation = ? , DisputedFlag = ? , OutPayFlag = ? , GetPolMode = ? , SignCom = ? , SignDate = ? , SignTime = ? , ConsignNo = ? , BankCode = ? , BankAccNo = ? , AccName = ? , PrintCount = ? , LostTimes = ? , Lang = ? , Currency = ? , Remark = ? , Peoples = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , Dif = ? , PaytoDate = ? , FirstPayDate = ? , CValiDate = ? , InputOperator = ? , InputDate = ? , InputTime = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , PolApplyDate = ? , GetPolDate = ? , GetPolTime = ? , CustomGetPolDate = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , FirstTrialOperator = ? , FirstTrialDate = ? , FirstTrialTime = ? , ReceiveOperator = ? , ReceiveDate = ? , ReceiveTime = ? , TempFeeNo = ? , SellType = ? , ForceUWFlag = ? , ForceUWReason = ? , NewBankCode = ? , NewBankAccNo = ? , NewAccName = ? , NewPayMode = ? , AgentBankCode = ? , BankAgent = ? , AutoPayFlag = ? , RnewFlag = ? , AgentName = ? , AgentComName = ? , SignAgentName = ? , AgentSignDate = ? , ReleaseFlag = ? , AppSignName = ? , InsSignName2 = ? , ImpartRemark = ? WHERE  ContNo = ? AND InputNo = ? AND FillNo = ?");
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
			pstmt.setInt(3, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFillNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getContType());
			}
			if(this.getFamilyType() == null || this.getFamilyType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFamilyType());
			}
			if(this.getFamilyID() == null || this.getFamilyID().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getFamilyID());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPolType());
			}
			if(this.getCardFlag() == null || this.getCardFlag().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCardFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getManageCom());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getExecuteCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentCom());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCode1());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getHandler());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPassword());
			}
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPayIntv());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPayLocation());
			}
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDisputedFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOutPayFlag());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getGetPolMode());
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
			if(this.getConsignNo() == null || this.getConsignNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getConsignNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getAccName());
			}
			if(this.getPrintCount() == null || this.getPrintCount().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getPrintCount());
			}
			if(this.getLostTimes() == null || this.getLostTimes().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getLostTimes());
			}
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getCurrency());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getRemark());
			}
			if(this.getPeoples() == null || this.getPeoples().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPeoples());
			}
			if(this.getMult() == null || this.getMult().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getMult());
			}
			if(this.getPrem() == null || this.getPrem().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getPrem());
			}
			if(this.getAmnt() == null || this.getAmnt().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getAmnt());
			}
			if(this.getSumPrem() == null || this.getSumPrem().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getSumPrem());
			}
			if(this.getDif() == null || this.getDif().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDif());
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getPaytoDate());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getFirstPayDate());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getCValiDate());
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getInputDate());
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getInputTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getApproveDate());
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getUWDate());
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAppFlag());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getPolApplyDate());
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getGetPolDate());
			}
			if(this.getGetPolTime() == null || this.getGetPolTime().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getGetPolTime());
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getCustomGetPolDate());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getState());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getMakeDate());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getModifyDate());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getModifyTime());
			}
			if(this.getFirstTrialOperator() == null || this.getFirstTrialOperator().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getFirstTrialOperator());
			}
			if(this.getFirstTrialDate() == null || this.getFirstTrialDate().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getFirstTrialDate());
			}
			if(this.getFirstTrialTime() == null || this.getFirstTrialTime().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getFirstTrialTime());
			}
			if(this.getReceiveOperator() == null || this.getReceiveOperator().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getReceiveOperator());
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getReceiveDate());
			}
			if(this.getReceiveTime() == null || this.getReceiveTime().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getReceiveTime());
			}
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getTempFeeNo());
			}
			if(this.getSellType() == null || this.getSellType().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getSellType());
			}
			if(this.getForceUWFlag() == null || this.getForceUWFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getForceUWFlag());
			}
			if(this.getForceUWReason() == null || this.getForceUWReason().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getForceUWReason());
			}
			if(this.getNewBankCode() == null || this.getNewBankCode().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getNewBankCode());
			}
			if(this.getNewBankAccNo() == null || this.getNewBankAccNo().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getNewBankAccNo());
			}
			if(this.getNewAccName() == null || this.getNewAccName().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getNewAccName());
			}
			if(this.getNewPayMode() == null || this.getNewPayMode().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getNewPayMode());
			}
			if(this.getAgentBankCode() == null || this.getAgentBankCode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getAgentBankCode());
			}
			if(this.getBankAgent() == null || this.getBankAgent().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getBankAgent());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getAutoPayFlag());
			}
			if(this.getRnewFlag() == null || this.getRnewFlag().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getRnewFlag());
			}
			if(this.getAgentName() == null || this.getAgentName().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getAgentName());
			}
			if(this.getAgentComName() == null || this.getAgentComName().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getAgentComName());
			}
			if(this.getSignAgentName() == null || this.getSignAgentName().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getSignAgentName());
			}
			if(this.getAgentSignDate() == null || this.getAgentSignDate().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getAgentSignDate());
			}
			if(this.getReleaseFlag() == null || this.getReleaseFlag().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getReleaseFlag());
			}
			if(this.getAppSignName() == null || this.getAppSignName().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getAppSignName());
			}
			if(this.getInsSignName2() == null || this.getInsSignName2().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getInsSignName2());
			}
			if(this.getImpartRemark() == null || this.getImpartRemark().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getImpartRemark());
			}
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getContNo());
			}
			pstmt.setInt(98, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getFillNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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
		SQLString sqlObj = new SQLString("LBPOCont");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBPOCont(GrpContNo ,ContNo ,InputNo ,FillNo ,ProposalContNo ,PrtNo ,ContType ,FamilyType ,FamilyID ,PolType ,CardFlag ,ManageCom ,ExecuteCom ,AgentCom ,AgentCode ,AgentGroup ,AgentCode1 ,AgentType ,SaleChnl ,Handler ,Password ,PayIntv ,PayMode ,PayLocation ,DisputedFlag ,OutPayFlag ,GetPolMode ,SignCom ,SignDate ,SignTime ,ConsignNo ,BankCode ,BankAccNo ,AccName ,PrintCount ,LostTimes ,Lang ,Currency ,Remark ,Peoples ,Mult ,Prem ,Amnt ,SumPrem ,Dif ,PaytoDate ,FirstPayDate ,CValiDate ,InputOperator ,InputDate ,InputTime ,ApproveFlag ,ApproveCode ,ApproveDate ,ApproveTime ,UWFlag ,UWOperator ,UWDate ,UWTime ,AppFlag ,PolApplyDate ,GetPolDate ,GetPolTime ,CustomGetPolDate ,State ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,FirstTrialOperator ,FirstTrialDate ,FirstTrialTime ,ReceiveOperator ,ReceiveDate ,ReceiveTime ,TempFeeNo ,SellType ,ForceUWFlag ,ForceUWReason ,NewBankCode ,NewBankAccNo ,NewAccName ,NewPayMode ,AgentBankCode ,BankAgent ,AutoPayFlag ,RnewFlag ,AgentName ,AgentComName ,SignAgentName ,AgentSignDate ,ReleaseFlag ,AppSignName ,InsSignName2 ,ImpartRemark) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			pstmt.setInt(3, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFillNo());
			}
			if(this.getProposalContNo() == null || this.getProposalContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getProposalContNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtNo());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getContType());
			}
			if(this.getFamilyType() == null || this.getFamilyType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getFamilyType());
			}
			if(this.getFamilyID() == null || this.getFamilyID().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getFamilyID());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPolType());
			}
			if(this.getCardFlag() == null || this.getCardFlag().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCardFlag());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getManageCom());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getExecuteCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentCom());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentGroup());
			}
			if(this.getAgentCode1() == null || this.getAgentCode1().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentCode1());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getSaleChnl());
			}
			if(this.getHandler() == null || this.getHandler().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getHandler());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPassword());
			}
			if(this.getPayIntv() == null || this.getPayIntv().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPayIntv());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getPayMode());
			}
			if(this.getPayLocation() == null || this.getPayLocation().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPayLocation());
			}
			if(this.getDisputedFlag() == null || this.getDisputedFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDisputedFlag());
			}
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOutPayFlag());
			}
			if(this.getGetPolMode() == null || this.getGetPolMode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getGetPolMode());
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
			if(this.getConsignNo() == null || this.getConsignNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getConsignNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getAccName());
			}
			if(this.getPrintCount() == null || this.getPrintCount().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getPrintCount());
			}
			if(this.getLostTimes() == null || this.getLostTimes().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getLostTimes());
			}
			if(this.getLang() == null || this.getLang().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getLang());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getCurrency());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getRemark());
			}
			if(this.getPeoples() == null || this.getPeoples().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPeoples());
			}
			if(this.getMult() == null || this.getMult().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getMult());
			}
			if(this.getPrem() == null || this.getPrem().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getPrem());
			}
			if(this.getAmnt() == null || this.getAmnt().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getAmnt());
			}
			if(this.getSumPrem() == null || this.getSumPrem().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getSumPrem());
			}
			if(this.getDif() == null || this.getDif().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDif());
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getPaytoDate());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getFirstPayDate());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getCValiDate());
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getInputDate());
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getInputTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getApproveDate());
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getUWDate());
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAppFlag());
			}
			if(this.getPolApplyDate() == null || this.getPolApplyDate().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getPolApplyDate());
			}
			if(this.getGetPolDate() == null || this.getGetPolDate().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getGetPolDate());
			}
			if(this.getGetPolTime() == null || this.getGetPolTime().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getGetPolTime());
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getCustomGetPolDate());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getState());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getMakeDate());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getModifyDate());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getModifyTime());
			}
			if(this.getFirstTrialOperator() == null || this.getFirstTrialOperator().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getFirstTrialOperator());
			}
			if(this.getFirstTrialDate() == null || this.getFirstTrialDate().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getFirstTrialDate());
			}
			if(this.getFirstTrialTime() == null || this.getFirstTrialTime().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getFirstTrialTime());
			}
			if(this.getReceiveOperator() == null || this.getReceiveOperator().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getReceiveOperator());
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getReceiveDate());
			}
			if(this.getReceiveTime() == null || this.getReceiveTime().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getReceiveTime());
			}
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getTempFeeNo());
			}
			if(this.getSellType() == null || this.getSellType().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getSellType());
			}
			if(this.getForceUWFlag() == null || this.getForceUWFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getForceUWFlag());
			}
			if(this.getForceUWReason() == null || this.getForceUWReason().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getForceUWReason());
			}
			if(this.getNewBankCode() == null || this.getNewBankCode().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getNewBankCode());
			}
			if(this.getNewBankAccNo() == null || this.getNewBankAccNo().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getNewBankAccNo());
			}
			if(this.getNewAccName() == null || this.getNewAccName().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getNewAccName());
			}
			if(this.getNewPayMode() == null || this.getNewPayMode().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getNewPayMode());
			}
			if(this.getAgentBankCode() == null || this.getAgentBankCode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getAgentBankCode());
			}
			if(this.getBankAgent() == null || this.getBankAgent().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getBankAgent());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getAutoPayFlag());
			}
			if(this.getRnewFlag() == null || this.getRnewFlag().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getRnewFlag());
			}
			if(this.getAgentName() == null || this.getAgentName().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getAgentName());
			}
			if(this.getAgentComName() == null || this.getAgentComName().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getAgentComName());
			}
			if(this.getSignAgentName() == null || this.getSignAgentName().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getSignAgentName());
			}
			if(this.getAgentSignDate() == null || this.getAgentSignDate().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getAgentSignDate());
			}
			if(this.getReleaseFlag() == null || this.getReleaseFlag().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getReleaseFlag());
			}
			if(this.getAppSignName() == null || this.getAppSignName().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getAppSignName());
			}
			if(this.getInsSignName2() == null || this.getInsSignName2().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getInsSignName2());
			}
			if(this.getImpartRemark() == null || this.getImpartRemark().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getImpartRemark());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBPOCont WHERE  ContNo = ? AND InputNo = ? AND FillNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.setInt(2, this.getInputNo());
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
					tError.moduleName = "LBPOContDB";
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
			tError.moduleName = "LBPOContDB";
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

	public LBPOContSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPOContSet aLBPOContSet = new LBPOContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOCont");
			LBPOContSchema aSchema = this.getSchema();
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
				LBPOContSchema s1 = new LBPOContSchema();
				s1.setSchema(rs,i);
				aLBPOContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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

		return aLBPOContSet;

	}

	public LBPOContSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOContSet aLBPOContSet = new LBPOContSet();

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
				LBPOContSchema s1 = new LBPOContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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

		return aLBPOContSet;
	}

	public LBPOContSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPOContSet aLBPOContSet = new LBPOContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOCont");
			LBPOContSchema aSchema = this.getSchema();
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

				LBPOContSchema s1 = new LBPOContSchema();
				s1.setSchema(rs,i);
				aLBPOContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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

		return aLBPOContSet;

	}

	public LBPOContSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOContSet aLBPOContSet = new LBPOContSet();

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

				LBPOContSchema s1 = new LBPOContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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

		return aLBPOContSet;
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
			SQLString sqlObj = new SQLString("LBPOCont");
			LBPOContSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBPOCont " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBPOContDB";
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
			tError.moduleName = "LBPOContDB";
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
        tError.moduleName = "LBPOContDB";
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
        tError.moduleName = "LBPOContDB";
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
        tError.moduleName = "LBPOContDB";
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
        tError.moduleName = "LBPOContDB";
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
 * @return LBPOContSet
 */
public LBPOContSet getData()
{
    int tCount = 0;
    LBPOContSet tLBPOContSet = new LBPOContSet();
    LBPOContSchema tLBPOContSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOContDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBPOContSchema = new LBPOContSchema();
        tLBPOContSchema.setSchema(mResultSet, 1);
        tLBPOContSet.add(tLBPOContSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBPOContSchema = new LBPOContSchema();
                tLBPOContSchema.setSchema(mResultSet, 1);
                tLBPOContSet.add(tLBPOContSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOContDB";
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
    return tLBPOContSet;
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
            tError.moduleName = "LBPOContDB";
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
        tError.moduleName = "LBPOContDB";
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
            tError.moduleName = "LBPOContDB";
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
        tError.moduleName = "LBPOContDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBPOContSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOContSet aLBPOContSet = new LBPOContSet();

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
				LBPOContSchema s1 = new LBPOContSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOContSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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

		return aLBPOContSet;
	}

	public LBPOContSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOContSet aLBPOContSet = new LBPOContSet();

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

				LBPOContSchema s1 = new LBPOContSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOContSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOContDB";
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

		return aLBPOContSet; 
	}

}
