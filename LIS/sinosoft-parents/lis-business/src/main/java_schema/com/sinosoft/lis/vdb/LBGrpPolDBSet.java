/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBGrpPolSchema;
import com.sinosoft.lis.vschema.LBGrpPolSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBGrpPolDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_14
 */
public class LBGrpPolDBSet extends LBGrpPolSet
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
	public LBGrpPolDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBGrpPol");
		mflag = true;
	}

	public LBGrpPolDBSet()
	{
		db = new DBOper( "LBGrpPol" );
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
			tError.moduleName = "LBGrpPolDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBGrpPol WHERE  GrpPolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpPol");
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
			tError.moduleName = "LBGrpPolDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBGrpPol SET  EdorNo = ? , GrpPolNo = ? , GrpContNo = ? , GrpProposalNo = ? , PrtNo = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , SaleChnl = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , AgentCode1 = ? , CustomerNo = ? , AddressNo = ? , GrpName = ? , FirstPayDate = ? , PayEndDate = ? , PaytoDate = ? , RegetDate = ? , LastEdorDate = ? , SSFlag = ? , PeakLine = ? , GetLimit = ? , GetRate = ? , BonusRate = ? , MaxMedFee = ? , OutPayFlag = ? , EmployeeRate = ? , FamilyRate = ? , SpecFlag = ? , ExpPeoples = ? , ExpPremium = ? , ExpAmnt = ? , PayMode = ? , ManageFeeRate = ? , PayIntv = ? , CValiDate = ? , Peoples2 = ? , Mult = ? , Prem = ? , Amnt = ? , SumPrem = ? , SumPay = ? , Dif = ? , State = ? , ApproveFlag = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , UWFlag = ? , UWOperator = ? , UWDate = ? , UWTime = ? , AppFlag = ? , Remark = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , RelaPeoples = ? , RelaMatePeoples = ? , RelaYoungPeoples = ? , RelaOtherPeoples = ? , WaitPeriod = ? , HealthInsurType = ? , AscriptionFlag = ? , InitRate = ? , HealthProType = ? , BonusFlag = ? , DistriFlag = ? , Peoples3 = ? , FeeRate = ? , RenewFlag = ? , DistriRate = ? , Currency = ? , UintLinkValiFlag = ? WHERE  GrpPolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPrtNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRiskVersion());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSaleChnl());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentCode1());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAddressNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGrpName());
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSSFlag());
			}
			pstmt.setDouble(25, this.get(i).getPeakLine());
			pstmt.setDouble(26, this.get(i).getGetLimit());
			pstmt.setDouble(27, this.get(i).getGetRate());
			pstmt.setDouble(28, this.get(i).getBonusRate());
			pstmt.setDouble(29, this.get(i).getMaxMedFee());
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOutPayFlag());
			}
			pstmt.setDouble(31, this.get(i).getEmployeeRate());
			pstmt.setDouble(32, this.get(i).getFamilyRate());
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getSpecFlag());
			}
			pstmt.setInt(34, this.get(i).getExpPeoples());
			pstmt.setDouble(35, this.get(i).getExpPremium());
			pstmt.setDouble(36, this.get(i).getExpAmnt());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getPayMode());
			}
			pstmt.setDouble(38, this.get(i).getManageFeeRate());
			pstmt.setInt(39, this.get(i).getPayIntv());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(41, this.get(i).getPeoples2());
			pstmt.setDouble(42, this.get(i).getMult());
			pstmt.setDouble(43, this.get(i).getPrem());
			pstmt.setDouble(44, this.get(i).getAmnt());
			pstmt.setDouble(45, this.get(i).getSumPrem());
			pstmt.setDouble(46, this.get(i).getSumPay());
			pstmt.setDouble(47, this.get(i).getDif());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getState());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(55,null);
			} else {
				pstmt.setDate(55, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAppFlag());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(63,null);
			} else {
				pstmt.setDate(63, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getModifyTime());
			}
			pstmt.setInt(67, this.get(i).getOnWorkPeoples());
			pstmt.setInt(68, this.get(i).getOffWorkPeoples());
			pstmt.setInt(69, this.get(i).getOtherPeoples());
			pstmt.setInt(70, this.get(i).getRelaPeoples());
			pstmt.setInt(71, this.get(i).getRelaMatePeoples());
			pstmt.setInt(72, this.get(i).getRelaYoungPeoples());
			pstmt.setInt(73, this.get(i).getRelaOtherPeoples());
			pstmt.setInt(74, this.get(i).getWaitPeriod());
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
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getBonusFlag());
			}
			if(this.get(i).getDistriFlag() == null || this.get(i).getDistriFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getDistriFlag());
			}
			pstmt.setInt(81, this.get(i).getPeoples3());
			pstmt.setDouble(82, this.get(i).getFeeRate());
			if(this.get(i).getRenewFlag() == null || this.get(i).getRenewFlag().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getRenewFlag());
			}
			pstmt.setDouble(84, this.get(i).getDistriRate());
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getCurrency());
			}
			if(this.get(i).getUintLinkValiFlag() == null || this.get(i).getUintLinkValiFlag().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getUintLinkValiFlag());
			}
			// set where condition
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getGrpPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpPol");
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
			tError.moduleName = "LBGrpPolDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBGrpPol VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPrtNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRiskVersion());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSaleChnl());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentGroup());
			}
			if(this.get(i).getAgentCode1() == null || this.get(i).getAgentCode1().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentCode1());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAddressNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGrpName());
			}
			if(this.get(i).getFirstPayDate() == null || this.get(i).getFirstPayDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getFirstPayDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getPaytoDate()));
			}
			if(this.get(i).getRegetDate() == null || this.get(i).getRegetDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getRegetDate()));
			}
			if(this.get(i).getLastEdorDate() == null || this.get(i).getLastEdorDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getLastEdorDate()));
			}
			if(this.get(i).getSSFlag() == null || this.get(i).getSSFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSSFlag());
			}
			pstmt.setDouble(25, this.get(i).getPeakLine());
			pstmt.setDouble(26, this.get(i).getGetLimit());
			pstmt.setDouble(27, this.get(i).getGetRate());
			pstmt.setDouble(28, this.get(i).getBonusRate());
			pstmt.setDouble(29, this.get(i).getMaxMedFee());
			if(this.get(i).getOutPayFlag() == null || this.get(i).getOutPayFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOutPayFlag());
			}
			pstmt.setDouble(31, this.get(i).getEmployeeRate());
			pstmt.setDouble(32, this.get(i).getFamilyRate());
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getSpecFlag());
			}
			pstmt.setInt(34, this.get(i).getExpPeoples());
			pstmt.setDouble(35, this.get(i).getExpPremium());
			pstmt.setDouble(36, this.get(i).getExpAmnt());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getPayMode());
			}
			pstmt.setDouble(38, this.get(i).getManageFeeRate());
			pstmt.setInt(39, this.get(i).getPayIntv());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getCValiDate()));
			}
			pstmt.setInt(41, this.get(i).getPeoples2());
			pstmt.setDouble(42, this.get(i).getMult());
			pstmt.setDouble(43, this.get(i).getPrem());
			pstmt.setDouble(44, this.get(i).getAmnt());
			pstmt.setDouble(45, this.get(i).getSumPrem());
			pstmt.setDouble(46, this.get(i).getSumPay());
			pstmt.setDouble(47, this.get(i).getDif());
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getState());
			}
			if(this.get(i).getApproveFlag() == null || this.get(i).getApproveFlag().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getApproveFlag());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getUWOperator());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(55,null);
			} else {
				pstmt.setDate(55, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getUWTime());
			}
			if(this.get(i).getAppFlag() == null || this.get(i).getAppFlag().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getAppFlag());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getRemark());
			}
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(63,null);
			} else {
				pstmt.setDate(63, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getModifyTime());
			}
			pstmt.setInt(67, this.get(i).getOnWorkPeoples());
			pstmt.setInt(68, this.get(i).getOffWorkPeoples());
			pstmt.setInt(69, this.get(i).getOtherPeoples());
			pstmt.setInt(70, this.get(i).getRelaPeoples());
			pstmt.setInt(71, this.get(i).getRelaMatePeoples());
			pstmt.setInt(72, this.get(i).getRelaYoungPeoples());
			pstmt.setInt(73, this.get(i).getRelaOtherPeoples());
			pstmt.setInt(74, this.get(i).getWaitPeriod());
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
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getBonusFlag());
			}
			if(this.get(i).getDistriFlag() == null || this.get(i).getDistriFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getDistriFlag());
			}
			pstmt.setInt(81, this.get(i).getPeoples3());
			pstmt.setDouble(82, this.get(i).getFeeRate());
			if(this.get(i).getRenewFlag() == null || this.get(i).getRenewFlag().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getRenewFlag());
			}
			pstmt.setDouble(84, this.get(i).getDistriRate());
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getCurrency());
			}
			if(this.get(i).getUintLinkValiFlag() == null || this.get(i).getUintLinkValiFlag().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getUintLinkValiFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBGrpPol");
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
			tError.moduleName = "LBGrpPolDBSet";
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
