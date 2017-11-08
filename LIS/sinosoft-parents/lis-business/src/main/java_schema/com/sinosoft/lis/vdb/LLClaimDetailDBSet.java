/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLClaimDetailDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_7
 */
public class LLClaimDetailDBSet extends LLClaimDetailSet
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
	public LLClaimDetailDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLClaimDetail");
		mflag = true;
	}

	public LLClaimDetailDBSet()
	{
		db = new DBOper( "LLClaimDetail" );
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
			tError.moduleName = "LLClaimDetailDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLClaimDetail WHERE  ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND CaseRelaNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getCaseRelaNo() == null || this.get(i).getCaseRelaNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCaseRelaNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimDetail");
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
			tError.moduleName = "LLClaimDetailDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLClaimDetail SET  ClmNo = ? , CaseNo = ? , PolNo = ? , PolSort = ? , PolType = ? , DutyCode = ? , GetDutyKind = ? , GetDutyCode = ? , CaseRelaNo = ? , DefoType = ? , RgtNo = ? , DeclineNo = ? , StatType = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , KindCode = ? , RiskCode = ? , RiskVer = ? , PolMngCom = ? , SaleChnl = ? , AgentCode = ? , AgentGroup = ? , TabFeeMoney = ? , ClaimMoney = ? , DeclineAmnt = ? , OverAmnt = ? , StandPay = ? , RealPay = ? , AdjReason = ? , AdjRemark = ? , PreGiveAmnt = ? , SelfGiveAmnt = ? , RefuseAmnt = ? , OtherAmnt = ? , OutDutyAmnt = ? , OutDutyRate = ? , ApproveAmnt = ? , ApproveCode = ? , AgreeAmnt = ? , AgreeCode = ? , GiveType = ? , GiveTypeDesc = ? , GiveReason = ? , GiveReasonDesc = ? , SpecialRemark = ? , PrepayFlag = ? , PrepaySum = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Amnt = ? , YearBonus = ? , EndBonus = ? , GracePeriod = ? , CasePolType = ? , CustomerNo = ? , AcctFlag = ? , PosFlag = ? , PosEdorNo = ? , CValiDate = ? , EffectOnMainRisk = ? , RiskCalCode = ? , AddAmnt = ? , NBPolNo = ? , PosEdorType = ? , RiskType = ? , PopedomPay = ? , InsFeePay = ? , OperState = ? , Currency = ? , BussType = ? , BussNo = ? , IsPublicAmnt = ? , PublicRiskCode = ? , PublicDutyCode = ? , PublicAmnt = ? , Salary = ? , GiveClass = ? , IndividualPayBUsedLimit = ? , SelfCalculateUsedLimint = ? , DentistryPay = ? , PhysicalPay = ? , ComCode = ? , ModifyOperator = ? WHERE  ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND CaseRelaNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getPolSort() == null || this.get(i).getPolSort().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolSort());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPolType());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getCaseRelaNo() == null || this.get(i).getCaseRelaNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCaseRelaNo());
			}
			if(this.get(i).getDefoType() == null || this.get(i).getDefoType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDefoType());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRgtNo());
			}
			if(this.get(i).getDeclineNo() == null || this.get(i).getDeclineNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getDeclineNo());
			}
			if(this.get(i).getStatType() == null || this.get(i).getStatType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStatType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getContNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskVer());
			}
			if(this.get(i).getPolMngCom() == null || this.get(i).getPolMngCom().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPolMngCom());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getSaleChnl());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAgentGroup());
			}
			pstmt.setDouble(24, this.get(i).getTabFeeMoney());
			pstmt.setDouble(25, this.get(i).getClaimMoney());
			pstmt.setDouble(26, this.get(i).getDeclineAmnt());
			pstmt.setDouble(27, this.get(i).getOverAmnt());
			pstmt.setDouble(28, this.get(i).getStandPay());
			pstmt.setDouble(29, this.get(i).getRealPay());
			if(this.get(i).getAdjReason() == null || this.get(i).getAdjReason().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAdjReason());
			}
			if(this.get(i).getAdjRemark() == null || this.get(i).getAdjRemark().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getAdjRemark());
			}
			pstmt.setDouble(32, this.get(i).getPreGiveAmnt());
			pstmt.setDouble(33, this.get(i).getSelfGiveAmnt());
			pstmt.setDouble(34, this.get(i).getRefuseAmnt());
			pstmt.setDouble(35, this.get(i).getOtherAmnt());
			pstmt.setDouble(36, this.get(i).getOutDutyAmnt());
			pstmt.setDouble(37, this.get(i).getOutDutyRate());
			pstmt.setDouble(38, this.get(i).getApproveAmnt());
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getApproveCode());
			}
			pstmt.setDouble(40, this.get(i).getAgreeAmnt());
			if(this.get(i).getAgreeCode() == null || this.get(i).getAgreeCode().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getAgreeCode());
			}
			if(this.get(i).getGiveType() == null || this.get(i).getGiveType().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getGiveType());
			}
			if(this.get(i).getGiveTypeDesc() == null || this.get(i).getGiveTypeDesc().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getGiveTypeDesc());
			}
			if(this.get(i).getGiveReason() == null || this.get(i).getGiveReason().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getGiveReason());
			}
			if(this.get(i).getGiveReasonDesc() == null || this.get(i).getGiveReasonDesc().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getGiveReasonDesc());
			}
			if(this.get(i).getSpecialRemark() == null || this.get(i).getSpecialRemark().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getSpecialRemark());
			}
			if(this.get(i).getPrepayFlag() == null || this.get(i).getPrepayFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getPrepayFlag());
			}
			pstmt.setDouble(48, this.get(i).getPrepaySum());
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getModifyTime());
			}
			pstmt.setDouble(55, this.get(i).getAmnt());
			pstmt.setDouble(56, this.get(i).getYearBonus());
			pstmt.setDouble(57, this.get(i).getEndBonus());
			pstmt.setInt(58, this.get(i).getGracePeriod());
			if(this.get(i).getCasePolType() == null || this.get(i).getCasePolType().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getCasePolType());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAcctFlag() == null || this.get(i).getAcctFlag().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getAcctFlag());
			}
			if(this.get(i).getPosFlag() == null || this.get(i).getPosFlag().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getPosFlag());
			}
			if(this.get(i).getPosEdorNo() == null || this.get(i).getPosEdorNo().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getPosEdorNo());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getEffectOnMainRisk() == null || this.get(i).getEffectOnMainRisk().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getEffectOnMainRisk());
			}
			if(this.get(i).getRiskCalCode() == null || this.get(i).getRiskCalCode().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRiskCalCode());
			}
			pstmt.setDouble(67, this.get(i).getAddAmnt());
			if(this.get(i).getNBPolNo() == null || this.get(i).getNBPolNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getNBPolNo());
			}
			if(this.get(i).getPosEdorType() == null || this.get(i).getPosEdorType().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getPosEdorType());
			}
			if(this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getRiskType());
			}
			pstmt.setDouble(71, this.get(i).getPopedomPay());
			pstmt.setDouble(72, this.get(i).getInsFeePay());
			if(this.get(i).getOperState() == null || this.get(i).getOperState().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getOperState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getCurrency());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getBussNo());
			}
			if(this.get(i).getIsPublicAmnt() == null || this.get(i).getIsPublicAmnt().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getIsPublicAmnt());
			}
			if(this.get(i).getPublicRiskCode() == null || this.get(i).getPublicRiskCode().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getPublicRiskCode());
			}
			if(this.get(i).getPublicDutyCode() == null || this.get(i).getPublicDutyCode().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getPublicDutyCode());
			}
			pstmt.setDouble(80, this.get(i).getPublicAmnt());
			pstmt.setDouble(81, this.get(i).getSalary());
			if(this.get(i).getGiveClass() == null || this.get(i).getGiveClass().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getGiveClass());
			}
			if(this.get(i).getIndividualPayBUsedLimit() == null || this.get(i).getIndividualPayBUsedLimit().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getIndividualPayBUsedLimit());
			}
			if(this.get(i).getSelfCalculateUsedLimint() == null || this.get(i).getSelfCalculateUsedLimint().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getSelfCalculateUsedLimint());
			}
			if(this.get(i).getDentistryPay() == null || this.get(i).getDentistryPay().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getDentistryPay());
			}
			if(this.get(i).getPhysicalPay() == null || this.get(i).getPhysicalPay().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getPhysicalPay());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getCaseNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getPolNo());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getCaseRelaNo() == null || this.get(i).getCaseRelaNo().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getCaseRelaNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimDetail");
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
			tError.moduleName = "LLClaimDetailDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLClaimDetail VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getPolSort() == null || this.get(i).getPolSort().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolSort());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPolType());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getCaseRelaNo() == null || this.get(i).getCaseRelaNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCaseRelaNo());
			}
			if(this.get(i).getDefoType() == null || this.get(i).getDefoType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDefoType());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRgtNo());
			}
			if(this.get(i).getDeclineNo() == null || this.get(i).getDeclineNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getDeclineNo());
			}
			if(this.get(i).getStatType() == null || this.get(i).getStatType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStatType());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getContNo());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskVer());
			}
			if(this.get(i).getPolMngCom() == null || this.get(i).getPolMngCom().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPolMngCom());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getSaleChnl());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAgentGroup());
			}
			pstmt.setDouble(24, this.get(i).getTabFeeMoney());
			pstmt.setDouble(25, this.get(i).getClaimMoney());
			pstmt.setDouble(26, this.get(i).getDeclineAmnt());
			pstmt.setDouble(27, this.get(i).getOverAmnt());
			pstmt.setDouble(28, this.get(i).getStandPay());
			pstmt.setDouble(29, this.get(i).getRealPay());
			if(this.get(i).getAdjReason() == null || this.get(i).getAdjReason().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAdjReason());
			}
			if(this.get(i).getAdjRemark() == null || this.get(i).getAdjRemark().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getAdjRemark());
			}
			pstmt.setDouble(32, this.get(i).getPreGiveAmnt());
			pstmt.setDouble(33, this.get(i).getSelfGiveAmnt());
			pstmt.setDouble(34, this.get(i).getRefuseAmnt());
			pstmt.setDouble(35, this.get(i).getOtherAmnt());
			pstmt.setDouble(36, this.get(i).getOutDutyAmnt());
			pstmt.setDouble(37, this.get(i).getOutDutyRate());
			pstmt.setDouble(38, this.get(i).getApproveAmnt());
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getApproveCode());
			}
			pstmt.setDouble(40, this.get(i).getAgreeAmnt());
			if(this.get(i).getAgreeCode() == null || this.get(i).getAgreeCode().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getAgreeCode());
			}
			if(this.get(i).getGiveType() == null || this.get(i).getGiveType().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getGiveType());
			}
			if(this.get(i).getGiveTypeDesc() == null || this.get(i).getGiveTypeDesc().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getGiveTypeDesc());
			}
			if(this.get(i).getGiveReason() == null || this.get(i).getGiveReason().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getGiveReason());
			}
			if(this.get(i).getGiveReasonDesc() == null || this.get(i).getGiveReasonDesc().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getGiveReasonDesc());
			}
			if(this.get(i).getSpecialRemark() == null || this.get(i).getSpecialRemark().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getSpecialRemark());
			}
			if(this.get(i).getPrepayFlag() == null || this.get(i).getPrepayFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getPrepayFlag());
			}
			pstmt.setDouble(48, this.get(i).getPrepaySum());
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getModifyTime());
			}
			pstmt.setDouble(55, this.get(i).getAmnt());
			pstmt.setDouble(56, this.get(i).getYearBonus());
			pstmt.setDouble(57, this.get(i).getEndBonus());
			pstmt.setInt(58, this.get(i).getGracePeriod());
			if(this.get(i).getCasePolType() == null || this.get(i).getCasePolType().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getCasePolType());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAcctFlag() == null || this.get(i).getAcctFlag().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getAcctFlag());
			}
			if(this.get(i).getPosFlag() == null || this.get(i).getPosFlag().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getPosFlag());
			}
			if(this.get(i).getPosEdorNo() == null || this.get(i).getPosEdorNo().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getPosEdorNo());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getEffectOnMainRisk() == null || this.get(i).getEffectOnMainRisk().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getEffectOnMainRisk());
			}
			if(this.get(i).getRiskCalCode() == null || this.get(i).getRiskCalCode().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRiskCalCode());
			}
			pstmt.setDouble(67, this.get(i).getAddAmnt());
			if(this.get(i).getNBPolNo() == null || this.get(i).getNBPolNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getNBPolNo());
			}
			if(this.get(i).getPosEdorType() == null || this.get(i).getPosEdorType().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getPosEdorType());
			}
			if(this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getRiskType());
			}
			pstmt.setDouble(71, this.get(i).getPopedomPay());
			pstmt.setDouble(72, this.get(i).getInsFeePay());
			if(this.get(i).getOperState() == null || this.get(i).getOperState().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getOperState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getCurrency());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getBussNo());
			}
			if(this.get(i).getIsPublicAmnt() == null || this.get(i).getIsPublicAmnt().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getIsPublicAmnt());
			}
			if(this.get(i).getPublicRiskCode() == null || this.get(i).getPublicRiskCode().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getPublicRiskCode());
			}
			if(this.get(i).getPublicDutyCode() == null || this.get(i).getPublicDutyCode().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getPublicDutyCode());
			}
			pstmt.setDouble(80, this.get(i).getPublicAmnt());
			pstmt.setDouble(81, this.get(i).getSalary());
			if(this.get(i).getGiveClass() == null || this.get(i).getGiveClass().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getGiveClass());
			}
			if(this.get(i).getIndividualPayBUsedLimit() == null || this.get(i).getIndividualPayBUsedLimit().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getIndividualPayBUsedLimit());
			}
			if(this.get(i).getSelfCalculateUsedLimint() == null || this.get(i).getSelfCalculateUsedLimint().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getSelfCalculateUsedLimint());
			}
			if(this.get(i).getDentistryPay() == null || this.get(i).getDentistryPay().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getDentistryPay());
			}
			if(this.get(i).getPhysicalPay() == null || this.get(i).getPhysicalPay().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getPhysicalPay());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimDetail");
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
			tError.moduleName = "LLClaimDetailDBSet";
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
