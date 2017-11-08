/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpPolDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCGrpPolDBSet extends LCGrpPolSet
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
	public LCGrpPolDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCGrpPol");
		mflag = true;
	}

	public LCGrpPolDBSet()
	{
		db = new DBOper( "LCGrpPol" );
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
			tError.moduleName = "LCGrpPolDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCGrpPol WHERE  GrpPolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPol");
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
			tError.moduleName = "LCGrpPolDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCGrpPol SET  GrpPolNo = ? , GrpContNo = ? , GrpProposalNo = ? , PrtNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , SaleChnl = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , CustomerNo = ? , AddressNo = ? , GrpName = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , RegetDate = ? , LastEdorDate = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , BonusRate = ? , MaxMedFee = ? , OutPayFlag = ? , EmployeeRate = ? , FamilyRate = ? , SpecFlag = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , PayMode = ? , ManageFeeRate = ? , PayIntv = ? , CValiDate = ? , Peoples2 = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , State = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , Remark = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , RelaPeoples = ? , RelaMatePeoples = ? , RelaYoungPeoples = ? , RelaOtherPeoples = ? , WaitPeriod = ? , BonusFlag = ? , HealthInsurType = ? , AscriptionFlag = ? , InitRate = ? , HealthProType = ? , DistriFlag = ? , Peoples3 = ? , FeeRate = ? , RenewFlag = ? , DistriRate = ? , FixprofitRate = ? , ChargeFeeRate = ? , CommRate = ? , StandbyFlag4 = ? , Currency = ? , UintLinkValiFlag = ? , RenewCount = ? , RenewContNo = ? , InitNumPeople = ? , InitMult = ? , InitAmnt = ? , InitRiskAmnt = ? , InitPrem = ? , InitStandPrem = ? , RiskAmnt = ? , StandPrem = ? , SumNumPeople = ? , ValDateType = ? , EndDate = ? , AutoPayFlag = ? , SubFlag = ? , StopFlag = ? , KeepValueOpt = ? , Lang = ? , GradeFeeRate = ? , OtherFeeRate = ? , PricingMode = ? , ComCode = ? , ModifyOperator = ? WHERE  GrpPolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskVersion());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSaleChnl());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentCode1());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAddressNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpName());
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getSSFlag());
			}
			pstmt.setDouble(24, this.get(i).getPeakLine());
			pstmt.setDouble(25, this.get(i).getGetLimit());
			pstmt.setDouble(26, this.get(i).getGetRate());
			pstmt.setDouble(27, this.get(i).getBonusRate());
			pstmt.setDouble(28, this.get(i).getMaxMedFee());
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getOutPayFlag());
			}
			pstmt.setDouble(30, this.get(i).getEmployeeRate());
			pstmt.setDouble(31, this.get(i).getFamilyRate());
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSpecFlag());
			}
			pstmt.setInt(33, this.get(i).getExpPeoples());
			pstmt.setDouble(34, this.get(i).getExpPremium());
			pstmt.setDouble(35, this.get(i).getExpAmnt());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getPayMode());
			}
			pstmt.setDouble(37, this.get(i).getManageFeeRate());
			pstmt.setInt(38, this.get(i).getPayIntv());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(40, this.get(i).getPeoples2());
			pstmt.setDouble(41, this.get(i).getMult());
			pstmt.setDouble(42, this.get(i).getPrem());
			pstmt.setDouble(43, this.get(i).getAmnt());
			pstmt.setDouble(44, this.get(i).getSumPrem());
			pstmt.setDouble(45, this.get(i).getSumPay());
			pstmt.setDouble(46, this.get(i).getDif());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getState());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(54,null);
			} else {
				pstmt.setDate(54, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getAppFlag());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getModifyTime());
			}
			pstmt.setInt(66, this.get(i).getOnWorkPeoples());
			pstmt.setInt(67, this.get(i).getOffWorkPeoples());
			pstmt.setInt(68, this.get(i).getOtherPeoples());
			pstmt.setInt(69, this.get(i).getRelaPeoples());
			pstmt.setInt(70, this.get(i).getRelaMatePeoples());
			pstmt.setInt(71, this.get(i).getRelaYoungPeoples());
			pstmt.setInt(72, this.get(i).getRelaOtherPeoples());
			pstmt.setInt(73, this.get(i).getWaitPeriod());
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getBonusFlag());
			}
			if(this.get(i).getHealthInsurType() == null || this.get(i).getHealthInsurType().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getHealthInsurType());
			}
			if(this.get(i).getAscriptionFlag() == null || this.get(i).getAscriptionFlag().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getAscriptionFlag());
			}
			pstmt.setDouble(77, this.get(i).getInitRate());
			if(this.get(i).getHealthProType() == null || this.get(i).getHealthProType().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getHealthProType());
			}
			if(this.get(i).getDistriFlag() == null || this.get(i).getDistriFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getDistriFlag());
			}
			pstmt.setInt(80, this.get(i).getPeoples3());
			pstmt.setDouble(81, this.get(i).getFeeRate());
			if(this.get(i).getRenewFlag() == null || this.get(i).getRenewFlag().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getRenewFlag());
			}
			pstmt.setDouble(83, this.get(i).getDistriRate());
			pstmt.setDouble(84, this.get(i).getFixprofitRate());
			pstmt.setDouble(85, this.get(i).getChargeFeeRate());
			pstmt.setDouble(86, this.get(i).getCommRate());
			if(this.get(i).getStandbyFlag4() == null || this.get(i).getStandbyFlag4().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getStandbyFlag4());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getCurrency());
			}
			if(this.get(i).getUintLinkValiFlag() == null || this.get(i).getUintLinkValiFlag().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getUintLinkValiFlag());
			}
			pstmt.setInt(90, this.get(i).getRenewCount());
			if(this.get(i).getRenewContNo() == null || this.get(i).getRenewContNo().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getRenewContNo());
			}
			pstmt.setInt(92, this.get(i).getInitNumPeople());
			pstmt.setDouble(93, this.get(i).getInitMult());
			pstmt.setDouble(94, this.get(i).getInitAmnt());
			pstmt.setDouble(95, this.get(i).getInitRiskAmnt());
			pstmt.setDouble(96, this.get(i).getInitPrem());
			pstmt.setDouble(97, this.get(i).getInitStandPrem());
			pstmt.setDouble(98, this.get(i).getRiskAmnt());
			pstmt.setDouble(99, this.get(i).getStandPrem());
			pstmt.setInt(100, this.get(i).getSumNumPeople());
			if(this.get(i).getValDateType() == null || this.get(i).getValDateType().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getValDateType());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(102,null);
			} else {
				pstmt.setDate(102, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getAutoPayFlag());
			}
			if(this.get(i).getSubFlag() == null || this.get(i).getSubFlag().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getSubFlag());
			}
			if(this.get(i).getStopFlag() == null || this.get(i).getStopFlag().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getStopFlag());
			}
			if(this.get(i).getKeepValueOpt() == null || this.get(i).getKeepValueOpt().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getKeepValueOpt());
			}
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getLang());
			}
			pstmt.setDouble(108, this.get(i).getGradeFeeRate());
			pstmt.setDouble(109, this.get(i).getOtherFeeRate());
			if(this.get(i).getPricingMode() == null || this.get(i).getPricingMode().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getPricingMode());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(112,null);
			} else {
				pstmt.setString(112, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getGrpPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPol");
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
			tError.moduleName = "LCGrpPolDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCGrpPol VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskVersion());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSaleChnl());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentCode1());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAddressNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpName());
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getSSFlag());
			}
			pstmt.setDouble(24, this.get(i).getPeakLine());
			pstmt.setDouble(25, this.get(i).getGetLimit());
			pstmt.setDouble(26, this.get(i).getGetRate());
			pstmt.setDouble(27, this.get(i).getBonusRate());
			pstmt.setDouble(28, this.get(i).getMaxMedFee());
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getOutPayFlag());
			}
			pstmt.setDouble(30, this.get(i).getEmployeeRate());
			pstmt.setDouble(31, this.get(i).getFamilyRate());
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSpecFlag());
			}
			pstmt.setInt(33, this.get(i).getExpPeoples());
			pstmt.setDouble(34, this.get(i).getExpPremium());
			pstmt.setDouble(35, this.get(i).getExpAmnt());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getPayMode());
			}
			pstmt.setDouble(37, this.get(i).getManageFeeRate());
			pstmt.setInt(38, this.get(i).getPayIntv());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(40, this.get(i).getPeoples2());
			pstmt.setDouble(41, this.get(i).getMult());
			pstmt.setDouble(42, this.get(i).getPrem());
			pstmt.setDouble(43, this.get(i).getAmnt());
			pstmt.setDouble(44, this.get(i).getSumPrem());
			pstmt.setDouble(45, this.get(i).getSumPay());
			pstmt.setDouble(46, this.get(i).getDif());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getState());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(54,null);
			} else {
				pstmt.setDate(54, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getAppFlag());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getModifyTime());
			}
			pstmt.setInt(66, this.get(i).getOnWorkPeoples());
			pstmt.setInt(67, this.get(i).getOffWorkPeoples());
			pstmt.setInt(68, this.get(i).getOtherPeoples());
			pstmt.setInt(69, this.get(i).getRelaPeoples());
			pstmt.setInt(70, this.get(i).getRelaMatePeoples());
			pstmt.setInt(71, this.get(i).getRelaYoungPeoples());
			pstmt.setInt(72, this.get(i).getRelaOtherPeoples());
			pstmt.setInt(73, this.get(i).getWaitPeriod());
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getBonusFlag());
			}
			if(this.get(i).getHealthInsurType() == null || this.get(i).getHealthInsurType().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getHealthInsurType());
			}
			if(this.get(i).getAscriptionFlag() == null || this.get(i).getAscriptionFlag().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getAscriptionFlag());
			}
			pstmt.setDouble(77, this.get(i).getInitRate());
			if(this.get(i).getHealthProType() == null || this.get(i).getHealthProType().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getHealthProType());
			}
			if(this.get(i).getDistriFlag() == null || this.get(i).getDistriFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getDistriFlag());
			}
			pstmt.setInt(80, this.get(i).getPeoples3());
			pstmt.setDouble(81, this.get(i).getFeeRate());
			if(this.get(i).getRenewFlag() == null || this.get(i).getRenewFlag().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getRenewFlag());
			}
			pstmt.setDouble(83, this.get(i).getDistriRate());
			pstmt.setDouble(84, this.get(i).getFixprofitRate());
			pstmt.setDouble(85, this.get(i).getChargeFeeRate());
			pstmt.setDouble(86, this.get(i).getCommRate());
			if(this.get(i).getStandbyFlag4() == null || this.get(i).getStandbyFlag4().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getStandbyFlag4());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getCurrency());
			}
			if(this.get(i).getUintLinkValiFlag() == null || this.get(i).getUintLinkValiFlag().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getUintLinkValiFlag());
			}
			pstmt.setInt(90, this.get(i).getRenewCount());
			if(this.get(i).getRenewContNo() == null || this.get(i).getRenewContNo().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getRenewContNo());
			}
			pstmt.setInt(92, this.get(i).getInitNumPeople());
			pstmt.setDouble(93, this.get(i).getInitMult());
			pstmt.setDouble(94, this.get(i).getInitAmnt());
			pstmt.setDouble(95, this.get(i).getInitRiskAmnt());
			pstmt.setDouble(96, this.get(i).getInitPrem());
			pstmt.setDouble(97, this.get(i).getInitStandPrem());
			pstmt.setDouble(98, this.get(i).getRiskAmnt());
			pstmt.setDouble(99, this.get(i).getStandPrem());
			pstmt.setInt(100, this.get(i).getSumNumPeople());
			if(this.get(i).getValDateType() == null || this.get(i).getValDateType().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getValDateType());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(102,null);
			} else {
				pstmt.setDate(102, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getAutoPayFlag());
			}
			if(this.get(i).getSubFlag() == null || this.get(i).getSubFlag().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getSubFlag());
			}
			if(this.get(i).getStopFlag() == null || this.get(i).getStopFlag().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getStopFlag());
			}
			if(this.get(i).getKeepValueOpt() == null || this.get(i).getKeepValueOpt().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getKeepValueOpt());
			}
			if(this.get(i).getLang() == null || this.get(i).getLang().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getLang());
			}
			pstmt.setDouble(108, this.get(i).getGradeFeeRate());
			pstmt.setDouble(109, this.get(i).getOtherFeeRate());
			if(this.get(i).getPricingMode() == null || this.get(i).getPricingMode().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getPricingMode());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(112,null);
			} else {
				pstmt.setString(112, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpPol");
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
			tError.moduleName = "LCGrpPolDBSet";
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
