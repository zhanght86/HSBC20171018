/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.LBGrpPolSchema;
import com.sinosoft.lis.vschema.LBGrpPolSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBGrpPolDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_14
 */
public class LBGrpPolDB extends LBGrpPolSchema
{
	private static Logger logger = Logger.getLogger(LBGrpPolDB.class);
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
	public LBGrpPolDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBGrpPol" );
		mflag = true;
	}

	public LBGrpPolDB()
	{
		con = null;
		db = new DBOper( "LBGrpPol" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBGrpPolSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBGrpPolSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBGrpPol WHERE  GrpPolNo = ?");
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpPol");
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
		SQLString sqlObj = new SQLString("LBGrpPol");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBGrpPol SET  EdorNo = ? , GrpPolNo = ? , GrpContNo = ? , GrpProposalNo = ? , PrtNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , SaleChnl = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , CustomerNo = ? , AddressNo = ? , GrpName = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , RegetDate = ? , LastEdorDate = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , BonusRate = ? , MaxMedFee = ? , OutPayFlag = ? , EmployeeRate = ? , FamilyRate = ? , SpecFlag = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , PayMode = ? , ManageFeeRate = ? , PayIntv = ? , CValiDate = ? , Peoples2 = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , State = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , Remark = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , RelaPeoples = ? , RelaMatePeoples = ? , RelaYoungPeoples = ? , RelaOtherPeoples = ? , WaitPeriod = ? , HealthInsurType = ? , AscriptionFlag = ? , InitRate = ? , HealthProType = ? , BonusFlag = ? , DistriFlag = ? , Peoples3 = ? , FeeRate = ? , RenewFlag = ? , DistriRate = ? , Currency = ? , UintLinkValiFlag = ? WHERE  GrpPolNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPolNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPrtNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getRiskVersion());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentType());
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
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getCustomerNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAddressNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpName());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getSSFlag());
			}
			pstmt.setDouble(25, this.getPeakLine());
			pstmt.setDouble(26, this.getGetLimit());
			pstmt.setDouble(27, this.getGetRate());
			pstmt.setDouble(28, this.getBonusRate());
			pstmt.setDouble(29, this.getMaxMedFee());
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getOutPayFlag());
			}
			pstmt.setDouble(31, this.getEmployeeRate());
			pstmt.setDouble(32, this.getFamilyRate());
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getSpecFlag());
			}
			pstmt.setInt(34, this.getExpPeoples());
			pstmt.setDouble(35, this.getExpPremium());
			pstmt.setDouble(36, this.getExpAmnt());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getPayMode());
			}
			pstmt.setDouble(38, this.getManageFeeRate());
			pstmt.setInt(39, this.getPayIntv());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(41, this.getPeoples2());
			pstmt.setDouble(42, this.getMult());
			pstmt.setDouble(43, this.getPrem());
			pstmt.setDouble(44, this.getAmnt());
			pstmt.setDouble(45, this.getSumPrem());
			pstmt.setDouble(46, this.getSumPay());
			pstmt.setDouble(47, this.getDif());
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getState());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(55, 91);
			} else {
				pstmt.setDate(55, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(57, 1);
			} else {
				pstmt.setString(57, this.getAppFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getRemark());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(63, 91);
			} else {
				pstmt.setDate(63, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getModifyTime());
			}
			pstmt.setInt(67, this.getOnWorkPeoples());
			pstmt.setInt(68, this.getOffWorkPeoples());
			pstmt.setInt(69, this.getOtherPeoples());
			pstmt.setInt(70, this.getRelaPeoples());
			pstmt.setInt(71, this.getRelaMatePeoples());
			pstmt.setInt(72, this.getRelaYoungPeoples());
			pstmt.setInt(73, this.getRelaOtherPeoples());
			pstmt.setInt(74, this.getWaitPeriod());
			if(this.getHealthInsurType() == null || this.getHealthInsurType().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getHealthInsurType());
			}
			if(this.getAscriptionFlag() == null || this.getAscriptionFlag().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getAscriptionFlag());
			}
			pstmt.setDouble(77, this.getInitRate());
			if(this.getHealthProType() == null || this.getHealthProType().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getHealthProType());
			}
			if(this.getBonusFlag() == null || this.getBonusFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getBonusFlag());
			}
			if(this.getDistriFlag() == null || this.getDistriFlag().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getDistriFlag());
			}
			pstmt.setInt(81, this.getPeoples3());
			pstmt.setDouble(82, this.getFeeRate());
			if(this.getRenewFlag() == null || this.getRenewFlag().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getRenewFlag());
			}
			pstmt.setDouble(84, this.getDistriRate());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getCurrency());
			}
			if(this.getUintLinkValiFlag() == null || this.getUintLinkValiFlag().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getUintLinkValiFlag());
			}
			// set where condition
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getGrpPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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
		SQLString sqlObj = new SQLString("LBGrpPol");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBGrpPol VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPolNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpProposalNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPrtNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRiskCode());
			}
			if(this.getRiskVersion() == null || this.getRiskVersion().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getRiskVersion());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentType());
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
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getCustomerNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAddressNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpName());
			}
			if(this.getFirstPayDate() == null || this.getFirstPayDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getFirstPayDate()));
			}
			if(this.getPayEndDate() == null || this.getPayEndDate().equals("null")) {
				pstmt.setNull(20, 91);
			} else {
				pstmt.setDate(20, Date.valueOf(this.getPayEndDate()));
			}
			if(this.getPaytoDate() == null || this.getPaytoDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getPaytoDate()));
			}
			if(this.getRegetDate() == null || this.getRegetDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getRegetDate()));
			}
			if(this.getLastEdorDate() == null || this.getLastEdorDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getLastEdorDate()));
			}
			if(this.getSSFlag() == null || this.getSSFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getSSFlag());
			}
			pstmt.setDouble(25, this.getPeakLine());
			pstmt.setDouble(26, this.getGetLimit());
			pstmt.setDouble(27, this.getGetRate());
			pstmt.setDouble(28, this.getBonusRate());
			pstmt.setDouble(29, this.getMaxMedFee());
			if(this.getOutPayFlag() == null || this.getOutPayFlag().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getOutPayFlag());
			}
			pstmt.setDouble(31, this.getEmployeeRate());
			pstmt.setDouble(32, this.getFamilyRate());
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getSpecFlag());
			}
			pstmt.setInt(34, this.getExpPeoples());
			pstmt.setDouble(35, this.getExpPremium());
			pstmt.setDouble(36, this.getExpAmnt());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getPayMode());
			}
			pstmt.setDouble(38, this.getManageFeeRate());
			pstmt.setInt(39, this.getPayIntv());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getCValiDate()));
			}
			pstmt.setInt(41, this.getPeoples2());
			pstmt.setDouble(42, this.getMult());
			pstmt.setDouble(43, this.getPrem());
			pstmt.setDouble(44, this.getAmnt());
			pstmt.setDouble(45, this.getSumPrem());
			pstmt.setDouble(46, this.getSumPay());
			pstmt.setDouble(47, this.getDif());
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getState());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getApproveFlag());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getApproveTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getUWFlag());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(55, 91);
			} else {
				pstmt.setDate(55, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getUWTime());
			}
			if(this.getAppFlag() == null || this.getAppFlag().equals("null")) {
				pstmt.setNull(57, 1);
			} else {
				pstmt.setString(57, this.getAppFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getRemark());
			}
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getStandbyFlag3());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(63, 91);
			} else {
				pstmt.setDate(63, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getModifyTime());
			}
			pstmt.setInt(67, this.getOnWorkPeoples());
			pstmt.setInt(68, this.getOffWorkPeoples());
			pstmt.setInt(69, this.getOtherPeoples());
			pstmt.setInt(70, this.getRelaPeoples());
			pstmt.setInt(71, this.getRelaMatePeoples());
			pstmt.setInt(72, this.getRelaYoungPeoples());
			pstmt.setInt(73, this.getRelaOtherPeoples());
			pstmt.setInt(74, this.getWaitPeriod());
			if(this.getHealthInsurType() == null || this.getHealthInsurType().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getHealthInsurType());
			}
			if(this.getAscriptionFlag() == null || this.getAscriptionFlag().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getAscriptionFlag());
			}
			pstmt.setDouble(77, this.getInitRate());
			if(this.getHealthProType() == null || this.getHealthProType().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getHealthProType());
			}
			if(this.getBonusFlag() == null || this.getBonusFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getBonusFlag());
			}
			if(this.getDistriFlag() == null || this.getDistriFlag().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getDistriFlag());
			}
			pstmt.setInt(81, this.getPeoples3());
			pstmt.setDouble(82, this.getFeeRate());
			if(this.getRenewFlag() == null || this.getRenewFlag().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getRenewFlag());
			}
			pstmt.setDouble(84, this.getDistriRate());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getCurrency());
			}
			if(this.getUintLinkValiFlag() == null || this.getUintLinkValiFlag().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getUintLinkValiFlag());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBGrpPol WHERE  GrpPolNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpPolNo());
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
					tError.moduleName = "LBGrpPolDB";
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
			tError.moduleName = "LBGrpPolDB";
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

	public LBGrpPolSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBGrpPol");
			LBGrpPolSchema aSchema = this.getSchema();
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
				LBGrpPolSchema s1 = new LBGrpPolSchema();
				s1.setSchema(rs,i);
				aLBGrpPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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

		return aLBGrpPolSet;
	}

	public LBGrpPolSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();

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
				LBGrpPolSchema s1 = new LBGrpPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBGrpPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBGrpPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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

		return aLBGrpPolSet;
	}

	public LBGrpPolSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBGrpPol");
			LBGrpPolSchema aSchema = this.getSchema();
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

				LBGrpPolSchema s1 = new LBGrpPolSchema();
				s1.setSchema(rs,i);
				aLBGrpPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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

		return aLBGrpPolSet;
	}

	public LBGrpPolSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();

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

				LBGrpPolSchema s1 = new LBGrpPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBGrpPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBGrpPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
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

		return aLBGrpPolSet;
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
			SQLString sqlObj = new SQLString("LBGrpPol");
			LBGrpPolSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBGrpPol " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBGrpPolDB";
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
			tError.moduleName = "LBGrpPolDB";
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
        tError.moduleName = "LBGrpPolDB";
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
        tError.moduleName = "LBGrpPolDB";
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
        tError.moduleName = "LBGrpPolDB";
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
        tError.moduleName = "LBGrpPolDB";
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
 * @return LBGrpPolSet
 */
public LBGrpPolSet getData()
{
    int tCount = 0;
    LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();
    LBGrpPolSchema tLBGrpPolSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBGrpPolDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBGrpPolSchema = new LBGrpPolSchema();
        tLBGrpPolSchema.setSchema(mResultSet, 1);
        tLBGrpPolSet.add(tLBGrpPolSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBGrpPolSchema = new LBGrpPolSchema();
                tLBGrpPolSchema.setSchema(mResultSet, 1);
                tLBGrpPolSet.add(tLBGrpPolSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBGrpPolDB";
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
    return tLBGrpPolSet;
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
            tError.moduleName = "LBGrpPolDB";
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
        tError.moduleName = "LBGrpPolDB";
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
            tError.moduleName = "LBGrpPolDB";
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
        tError.moduleName = "LBGrpPolDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBGrpPolSet executeQuery(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();

		if (!mflag) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);

			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LBGrpPolSchema s1 = new LBGrpPolSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBGrpPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLBGrpPolSet.add(s1);
			}
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex1) {
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try {
				rs.close();
			} catch (Exception ex2) {
			}
			try {
				pstmt.close();
			} catch (Exception ex3) {
			}

			if (!mflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
		}

		if (!mflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

		return aLBGrpPolSet;
	}

	public LBGrpPolSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LBGrpPolSet aLBGrpPolSet = new LBGrpPolSet();

		if (!mflag) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);

			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if (i < nStart) {
					continue;
				}

				if (i >= nStart + nCount) {
					break;
				}

				LBGrpPolSchema s1 = new LBGrpPolSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBGrpPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLBGrpPolSet.add(s1);
			}
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close(); 
			} catch (Exception ex1) {
			}
		}
		catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBGrpPolDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try {
				rs.close(); 
			} catch (Exception ex2) {
			}
			try {
				pstmt.close();
			} catch (Exception ex3) {
			}

			if (!mflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
		}

		if (!mflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

		return aLBGrpPolSet;
	}

}
