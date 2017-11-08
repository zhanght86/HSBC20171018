/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLClaimDetailDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_7
 */
public class LLClaimDetailDB extends LLClaimDetailSchema
{
	private static Logger logger = Logger.getLogger(LLClaimDetailDB.class);
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
	public LLClaimDetailDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLClaimDetail" );
		mflag = true;
	}

	public LLClaimDetailDB()
	{
		con = null;
		db = new DBOper( "LLClaimDetail" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLClaimDetailSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLClaimDetailSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLClaimDetail WHERE  ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND CaseRelaNo = ?");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCaseRelaNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaimDetail");
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
		SQLString sqlObj = new SQLString("LLClaimDetail");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLClaimDetail SET  ClmNo = ? , CaseNo = ? , PolNo = ? , PolSort = ? , PolType = ? , DutyCode = ? , GetDutyKind = ? , GetDutyCode = ? , CaseRelaNo = ? , DefoType = ? , RgtNo = ? , DeclineNo = ? , StatType = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , KindCode = ? , RiskCode = ? , RiskVer = ? , PolMngCom = ? , SaleChnl = ? , AgentCode = ? , AgentGroup = ? , TabFeeMoney = ? , ClaimMoney = ? , DeclineAmnt = ? , OverAmnt = ? , StandPay = ? , RealPay = ? , AdjReason = ? , AdjRemark = ? , PreGiveAmnt = ? , SelfGiveAmnt = ? , RefuseAmnt = ? , OtherAmnt = ? , OutDutyAmnt = ? , OutDutyRate = ? , ApproveAmnt = ? , ApproveCode = ? , AgreeAmnt = ? , AgreeCode = ? , GiveType = ? , GiveTypeDesc = ? , GiveReason = ? , GiveReasonDesc = ? , SpecialRemark = ? , PrepayFlag = ? , PrepaySum = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Amnt = ? , YearBonus = ? , EndBonus = ? , GracePeriod = ? , CasePolType = ? , CustomerNo = ? , AcctFlag = ? , PosFlag = ? , PosEdorNo = ? , CValiDate = ? , EffectOnMainRisk = ? , RiskCalCode = ? , AddAmnt = ? , NBPolNo = ? , PosEdorType = ? , RiskType = ? , PopedomPay = ? , InsFeePay = ? , OperState = ? , Currency = ? , BussType = ? , BussNo = ? , IsPublicAmnt = ? , PublicRiskCode = ? , PublicDutyCode = ? , PublicAmnt = ? , Salary = ? , GiveClass = ? , IndividualPayBUsedLimit = ? , SelfCalculateUsedLimint = ? , DentistryPay = ? , PhysicalPay = ? , ComCode = ? , ModifyOperator = ? WHERE  ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND CaseRelaNo = ?");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getPolSort() == null || this.getPolSort().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolSort());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPolType());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getDutyCode());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getGetDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCaseRelaNo());
			}
			if(this.getDefoType() == null || this.getDefoType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getDefoType());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRgtNo());
			}
			if(this.getDeclineNo() == null || this.getDeclineNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getDeclineNo());
			}
			if(this.getStatType() == null || this.getStatType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStatType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getRiskVer());
			}
			if(this.getPolMngCom() == null || this.getPolMngCom().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPolMngCom());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getSaleChnl());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAgentGroup());
			}
			pstmt.setDouble(24, this.getTabFeeMoney());
			pstmt.setDouble(25, this.getClaimMoney());
			pstmt.setDouble(26, this.getDeclineAmnt());
			pstmt.setDouble(27, this.getOverAmnt());
			pstmt.setDouble(28, this.getStandPay());
			pstmt.setDouble(29, this.getRealPay());
			if(this.getAdjReason() == null || this.getAdjReason().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getAdjReason());
			}
			if(this.getAdjRemark() == null || this.getAdjRemark().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getAdjRemark());
			}
			pstmt.setDouble(32, this.getPreGiveAmnt());
			pstmt.setDouble(33, this.getSelfGiveAmnt());
			pstmt.setDouble(34, this.getRefuseAmnt());
			pstmt.setDouble(35, this.getOtherAmnt());
			pstmt.setDouble(36, this.getOutDutyAmnt());
			pstmt.setDouble(37, this.getOutDutyRate());
			pstmt.setDouble(38, this.getApproveAmnt());
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getApproveCode());
			}
			pstmt.setDouble(40, this.getAgreeAmnt());
			if(this.getAgreeCode() == null || this.getAgreeCode().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getAgreeCode());
			}
			if(this.getGiveType() == null || this.getGiveType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getGiveType());
			}
			if(this.getGiveTypeDesc() == null || this.getGiveTypeDesc().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getGiveTypeDesc());
			}
			if(this.getGiveReason() == null || this.getGiveReason().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getGiveReason());
			}
			if(this.getGiveReasonDesc() == null || this.getGiveReasonDesc().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getGiveReasonDesc());
			}
			if(this.getSpecialRemark() == null || this.getSpecialRemark().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getSpecialRemark());
			}
			if(this.getPrepayFlag() == null || this.getPrepayFlag().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getPrepayFlag());
			}
			pstmt.setDouble(48, this.getPrepaySum());
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(52, 1);
			} else {
				pstmt.setString(52, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getModifyTime());
			}
			pstmt.setDouble(55, this.getAmnt());
			pstmt.setDouble(56, this.getYearBonus());
			pstmt.setDouble(57, this.getEndBonus());
			pstmt.setInt(58, this.getGracePeriod());
			if(this.getCasePolType() == null || this.getCasePolType().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getCasePolType());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getCustomerNo());
			}
			if(this.getAcctFlag() == null || this.getAcctFlag().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getAcctFlag());
			}
			if(this.getPosFlag() == null || this.getPosFlag().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getPosFlag());
			}
			if(this.getPosEdorNo() == null || this.getPosEdorNo().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getPosEdorNo());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getCValiDate()));
			}
			if(this.getEffectOnMainRisk() == null || this.getEffectOnMainRisk().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getEffectOnMainRisk());
			}
			if(this.getRiskCalCode() == null || this.getRiskCalCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getRiskCalCode());
			}
			pstmt.setDouble(67, this.getAddAmnt());
			if(this.getNBPolNo() == null || this.getNBPolNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getNBPolNo());
			}
			if(this.getPosEdorType() == null || this.getPosEdorType().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getPosEdorType());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getRiskType());
			}
			pstmt.setDouble(71, this.getPopedomPay());
			pstmt.setDouble(72, this.getInsFeePay());
			if(this.getOperState() == null || this.getOperState().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getOperState());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getCurrency());
			}
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getBussNo());
			}
			if(this.getIsPublicAmnt() == null || this.getIsPublicAmnt().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getIsPublicAmnt());
			}
			if(this.getPublicRiskCode() == null || this.getPublicRiskCode().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getPublicRiskCode());
			}
			if(this.getPublicDutyCode() == null || this.getPublicDutyCode().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getPublicDutyCode());
			}
			pstmt.setDouble(80, this.getPublicAmnt());
			pstmt.setDouble(81, this.getSalary());
			if(this.getGiveClass() == null || this.getGiveClass().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getGiveClass());
			}
			if(this.getIndividualPayBUsedLimit() == null || this.getIndividualPayBUsedLimit().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getIndividualPayBUsedLimit());
			}
			if(this.getSelfCalculateUsedLimint() == null || this.getSelfCalculateUsedLimint().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getSelfCalculateUsedLimint());
			}
			if(this.getDentistryPay() == null || this.getDentistryPay().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getDentistryPay());
			}
			if(this.getPhysicalPay() == null || this.getPhysicalPay().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getPhysicalPay());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getModifyOperator());
			}
			// set where condition
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getCaseNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getGetDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getCaseRelaNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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
		SQLString sqlObj = new SQLString("LLClaimDetail");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLClaimDetail VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getPolSort() == null || this.getPolSort().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolSort());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPolType());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getDutyCode());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getGetDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCaseRelaNo());
			}
			if(this.getDefoType() == null || this.getDefoType().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getDefoType());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRgtNo());
			}
			if(this.getDeclineNo() == null || this.getDeclineNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getDeclineNo());
			}
			if(this.getStatType() == null || this.getStatType().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStatType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getRiskVer());
			}
			if(this.getPolMngCom() == null || this.getPolMngCom().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPolMngCom());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getSaleChnl());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAgentGroup());
			}
			pstmt.setDouble(24, this.getTabFeeMoney());
			pstmt.setDouble(25, this.getClaimMoney());
			pstmt.setDouble(26, this.getDeclineAmnt());
			pstmt.setDouble(27, this.getOverAmnt());
			pstmt.setDouble(28, this.getStandPay());
			pstmt.setDouble(29, this.getRealPay());
			if(this.getAdjReason() == null || this.getAdjReason().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getAdjReason());
			}
			if(this.getAdjRemark() == null || this.getAdjRemark().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getAdjRemark());
			}
			pstmt.setDouble(32, this.getPreGiveAmnt());
			pstmt.setDouble(33, this.getSelfGiveAmnt());
			pstmt.setDouble(34, this.getRefuseAmnt());
			pstmt.setDouble(35, this.getOtherAmnt());
			pstmt.setDouble(36, this.getOutDutyAmnt());
			pstmt.setDouble(37, this.getOutDutyRate());
			pstmt.setDouble(38, this.getApproveAmnt());
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getApproveCode());
			}
			pstmt.setDouble(40, this.getAgreeAmnt());
			if(this.getAgreeCode() == null || this.getAgreeCode().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getAgreeCode());
			}
			if(this.getGiveType() == null || this.getGiveType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getGiveType());
			}
			if(this.getGiveTypeDesc() == null || this.getGiveTypeDesc().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getGiveTypeDesc());
			}
			if(this.getGiveReason() == null || this.getGiveReason().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getGiveReason());
			}
			if(this.getGiveReasonDesc() == null || this.getGiveReasonDesc().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getGiveReasonDesc());
			}
			if(this.getSpecialRemark() == null || this.getSpecialRemark().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getSpecialRemark());
			}
			if(this.getPrepayFlag() == null || this.getPrepayFlag().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getPrepayFlag());
			}
			pstmt.setDouble(48, this.getPrepaySum());
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(52, 1);
			} else {
				pstmt.setString(52, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getModifyTime());
			}
			pstmt.setDouble(55, this.getAmnt());
			pstmt.setDouble(56, this.getYearBonus());
			pstmt.setDouble(57, this.getEndBonus());
			pstmt.setInt(58, this.getGracePeriod());
			if(this.getCasePolType() == null || this.getCasePolType().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getCasePolType());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getCustomerNo());
			}
			if(this.getAcctFlag() == null || this.getAcctFlag().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getAcctFlag());
			}
			if(this.getPosFlag() == null || this.getPosFlag().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getPosFlag());
			}
			if(this.getPosEdorNo() == null || this.getPosEdorNo().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getPosEdorNo());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getCValiDate()));
			}
			if(this.getEffectOnMainRisk() == null || this.getEffectOnMainRisk().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getEffectOnMainRisk());
			}
			if(this.getRiskCalCode() == null || this.getRiskCalCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getRiskCalCode());
			}
			pstmt.setDouble(67, this.getAddAmnt());
			if(this.getNBPolNo() == null || this.getNBPolNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getNBPolNo());
			}
			if(this.getPosEdorType() == null || this.getPosEdorType().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getPosEdorType());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getRiskType());
			}
			pstmt.setDouble(71, this.getPopedomPay());
			pstmt.setDouble(72, this.getInsFeePay());
			if(this.getOperState() == null || this.getOperState().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getOperState());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getCurrency());
			}
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getBussNo());
			}
			if(this.getIsPublicAmnt() == null || this.getIsPublicAmnt().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getIsPublicAmnt());
			}
			if(this.getPublicRiskCode() == null || this.getPublicRiskCode().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getPublicRiskCode());
			}
			if(this.getPublicDutyCode() == null || this.getPublicDutyCode().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getPublicDutyCode());
			}
			pstmt.setDouble(80, this.getPublicAmnt());
			pstmt.setDouble(81, this.getSalary());
			if(this.getGiveClass() == null || this.getGiveClass().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getGiveClass());
			}
			if(this.getIndividualPayBUsedLimit() == null || this.getIndividualPayBUsedLimit().equals("null")) {
				pstmt.setNull(83, 12);
			} else {
				pstmt.setString(83, this.getIndividualPayBUsedLimit());
			}
			if(this.getSelfCalculateUsedLimint() == null || this.getSelfCalculateUsedLimint().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getSelfCalculateUsedLimint());
			}
			if(this.getDentistryPay() == null || this.getDentistryPay().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getDentistryPay());
			}
			if(this.getPhysicalPay() == null || this.getPhysicalPay().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getPhysicalPay());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLClaimDetail WHERE  ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND CaseRelaNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCaseNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getCaseRelaNo());
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
					tError.moduleName = "LLClaimDetailDB";
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
			tError.moduleName = "LLClaimDetailDB";
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

	public LLClaimDetailSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLClaimDetailSet aLLClaimDetailSet = new LLClaimDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLClaimDetail");
			LLClaimDetailSchema aSchema = this.getSchema();
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
				LLClaimDetailSchema s1 = new LLClaimDetailSchema();
				s1.setSchema(rs,i);
				aLLClaimDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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

		return aLLClaimDetailSet;
	}

	public LLClaimDetailSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLClaimDetailSet aLLClaimDetailSet = new LLClaimDetailSet();

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
				LLClaimDetailSchema s1 = new LLClaimDetailSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLClaimDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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

		return aLLClaimDetailSet;
	}

	public LLClaimDetailSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLClaimDetailSet aLLClaimDetailSet = new LLClaimDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLClaimDetail");
			LLClaimDetailSchema aSchema = this.getSchema();
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

				LLClaimDetailSchema s1 = new LLClaimDetailSchema();
				s1.setSchema(rs,i);
				aLLClaimDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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

		return aLLClaimDetailSet;
	}

	public LLClaimDetailSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLClaimDetailSet aLLClaimDetailSet = new LLClaimDetailSet();

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

				LLClaimDetailSchema s1 = new LLClaimDetailSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLClaimDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDetailDB";
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

		return aLLClaimDetailSet;
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
			SQLString sqlObj = new SQLString("LLClaimDetail");
			LLClaimDetailSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLClaimDetail " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLClaimDetailDB";
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
			tError.moduleName = "LLClaimDetailDB";
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
        tError.moduleName = "LLClaimDetailDB";
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
        tError.moduleName = "LLClaimDetailDB";
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
        tError.moduleName = "LLClaimDetailDB";
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
        tError.moduleName = "LLClaimDetailDB";
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
 * @return LLClaimDetailSet
 */
public LLClaimDetailSet getData()
{
    int tCount = 0;
    LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
    LLClaimDetailSchema tLLClaimDetailSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLClaimDetailDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLClaimDetailSchema = new LLClaimDetailSchema();
        tLLClaimDetailSchema.setSchema(mResultSet, 1);
        tLLClaimDetailSet.add(tLLClaimDetailSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLClaimDetailSchema = new LLClaimDetailSchema();
                tLLClaimDetailSchema.setSchema(mResultSet, 1);
                tLLClaimDetailSet.add(tLLClaimDetailSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLClaimDetailDB";
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
    return tLLClaimDetailSet;
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
            tError.moduleName = "LLClaimDetailDB";
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
        tError.moduleName = "LLClaimDetailDB";
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
            tError.moduleName = "LLClaimDetailDB";
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
        tError.moduleName = "LLClaimDetailDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLClaimDetailSet executeQuery(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LLClaimDetailSet aLLClaimDetailSet = new LLClaimDetailSet();

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
				LLClaimDetailSchema s1 = new LLClaimDetailSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLLClaimDetailSet.add(s1);
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
			tError.moduleName = "LLClaimDetailDB";
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

		return aLLClaimDetailSet;
	}

	public LLClaimDetailSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LLClaimDetailSet aLLClaimDetailSet = new LLClaimDetailSet();

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

				LLClaimDetailSchema s1 = new LLClaimDetailSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLLClaimDetailSet.add(s1);
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
			tError.moduleName = "LLClaimDetailDB";
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

		return aLLClaimDetailSet;
	}

}
