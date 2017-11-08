

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.vschema.RIPolRecordSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIPolRecordDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIPolRecordDBSet extends RIPolRecordSet
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
	public RIPolRecordDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIPolRecord");
		mflag = true;
	}

	public RIPolRecordDBSet()
	{
		db = new DBOper( "RIPolRecord" );
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
			tError.moduleName = "RIPolRecordDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIPolRecord WHERE  EventNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEventNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPolRecord");
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
			tError.moduleName = "RIPolRecordDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIPolRecord SET  BatchNo = ? , EventNo = ? , EventType = ? , RecordType = ? , NodeState = ? , DataFlag = ? , UnionKey = ? , GrpContNo = ? , ProposalGrpContNo = ? , GrpPolNo = ? , GrpProposalNo = ? , ContNo = ? , PolNo = ? , ProposalNo = ? , ContPlanCode = ? , PlanType = ? , RiskPeriod = ? , RiskType = ? , RiskCode = ? , DutyCode = ? , Years = ? , InsuredYear = ? , SaleChnl = ? , CValiDate = ? , EndDate = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAge = ? , CurrentAge = ? , IDType = ? , IDNo = ? , OccupationType = ? , HealthTime1 = ? , HealthTime2 = ? , OccupationCode = ? , SuppRiskScore = ? , SmokeFlag = ? , AdditionalRate = ? , ExtType = ? , ExtPrem = ? , BegAccountValue = ? , EndUnit = ? , EndPrice = ? , EndAccountValue = ? , FeeType = ? , FeeMoney = ? , NonCashFlag = ? , StandPrem = ? , Prem = ? , Amnt = ? , Mult = ? , Reserve = ? , APE = ? , CSV = ? , Retention = ? , Suminsured = ? , Faceamount = ? , Currency = ? , RiskAmnt = ? , PayIntv = ? , PayYears = ? , PayEndYearFlag = ? , PayEndYear = ? , GetYearFlag = ? , GetYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , AcciEndDate = ? , GetStartDate = ? , GetStartType = ? , PeakLine = ? , GetLimit = ? , GetIntv = ? , PayNo = ? , PayCount = ? , PayMoney = ? , LastPayToDate = ? , CurPayToDate = ? , EdorNo = ? , FeeOperationType = ? , FeeFinaType = ? , ChangeRate = ? , AccCurrency = ? , AccAmnt = ? , PreStandPrem = ? , PrePrem = ? , PreRiskAmnt = ? , PreAmnt = ? , ClmNo = ? , ClmFeeOperationType = ? , ClmFeeFinaType = ? , StandGetMoney = ? , GetRate = ? , ClmGetMoney = ? , AccDate = ? , AccumulateDefNO = ? , RIContNo = ? , RIPreceptNo = ? , ReinsreFlag = ? , GetDate = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? , StandbyDouble1 = ? , StandbyDouble2 = ? , StandbyDouble3 = ? , StandbyDate1 = ? , StandbyDate2 = ? , ManageCom = ? , MakeDate = ? , MakeTime = ? , PreChRiskAmnt = ? , ChRiskAmnt = ? , PreChangeRate = ? , MainPolNo = ? , RIPreceptType = ? , BFFlag = ? , EdorPrem = ? , ChEdorPrem = ? , ChPrem = ? , Bonus1 = ? , Bonus2 = ? , Bonus3 = ? , ExtRate1 = ? , ExtRate2 = ? , ExtRate3 = ? , ExtPrem1 = ? , ExtPrem2 = ? , ExtPrem3 = ? , StandbyString4 = ? , StandbyString5 = ? , StandbyString6 = ? , StandbyString7 = ? , StandbyString8 = ? , StandbyString9 = ? , StandbyDouble4 = ? , StandbyDouble5 = ? , StandbyDouble6 = ? , StandbyDouble7 = ? , StandbyDouble8 = ? , StandbyDouble9 = ? , StandbyDate3 = ? , StandbyDate4 = ? , StandbyDate5 = ? WHERE  EventNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo());
			}
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEventNo());
			}
			if(this.get(i).getEventType() == null || this.get(i).getEventType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEventType());
			}
			if(this.get(i).getRecordType() == null || this.get(i).getRecordType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRecordType());
			}
			if(this.get(i).getNodeState() == null || this.get(i).getNodeState().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getNodeState());
			}
			if(this.get(i).getDataFlag() == null || this.get(i).getDataFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDataFlag());
			}
			if(this.get(i).getUnionKey() == null || this.get(i).getUnionKey().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getUnionKey());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPolNo());
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getProposalNo());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getContPlanCode());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getPlanType());
			}
			if(this.get(i).getRiskPeriod() == null || this.get(i).getRiskPeriod().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRiskPeriod());
			}
			if(this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getDutyCode());
			}
			pstmt.setInt(21, this.get(i).getYears());
			pstmt.setInt(22, this.get(i).getInsuredYear());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getSaleChnl());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(30, this.get(i).getInsuredAge());
			pstmt.setInt(31, this.get(i).getCurrentAge());
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getIDNo());
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOccupationType());
			}
			if(this.get(i).getHealthTime1() == null || this.get(i).getHealthTime1().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getHealthTime1());
			}
			if(this.get(i).getHealthTime2() == null || this.get(i).getHealthTime2().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getHealthTime2());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOccupationCode());
			}
			if(this.get(i).getSuppRiskScore() == null || this.get(i).getSuppRiskScore().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSuppRiskScore());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSmokeFlag());
			}
			pstmt.setDouble(40, this.get(i).getAdditionalRate());
			if(this.get(i).getExtType() == null || this.get(i).getExtType().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getExtType());
			}
			pstmt.setDouble(42, this.get(i).getExtPrem());
			pstmt.setDouble(43, this.get(i).getBegAccountValue());
			pstmt.setInt(44, this.get(i).getEndUnit());
			pstmt.setDouble(45, this.get(i).getEndPrice());
			pstmt.setDouble(46, this.get(i).getEndAccountValue());
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getFeeType());
			}
			pstmt.setDouble(48, this.get(i).getFeeMoney());
			if(this.get(i).getNonCashFlag() == null || this.get(i).getNonCashFlag().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getNonCashFlag());
			}
			pstmt.setDouble(50, this.get(i).getStandPrem());
			pstmt.setDouble(51, this.get(i).getPrem());
			pstmt.setDouble(52, this.get(i).getAmnt());
			pstmt.setDouble(53, this.get(i).getMult());
			pstmt.setDouble(54, this.get(i).getReserve());
			pstmt.setDouble(55, this.get(i).getAPE());
			pstmt.setDouble(56, this.get(i).getCSV());
			pstmt.setDouble(57, this.get(i).getRetention());
			pstmt.setDouble(58, this.get(i).getSuminsured());
			pstmt.setDouble(59, this.get(i).getFaceamount());
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getCurrency());
			}
			pstmt.setDouble(61, this.get(i).getRiskAmnt());
			pstmt.setInt(62, this.get(i).getPayIntv());
			pstmt.setInt(63, this.get(i).getPayYears());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(65, this.get(i).getPayEndYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(67, this.get(i).getGetYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(69, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(71, this.get(i).getAcciYear());
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(72,null);
			} else {
				pstmt.setDate(72, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getGetStartType());
			}
			pstmt.setDouble(75, this.get(i).getPeakLine());
			pstmt.setDouble(76, this.get(i).getGetLimit());
			pstmt.setInt(77, this.get(i).getGetIntv());
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getPayNo());
			}
			pstmt.setInt(79, this.get(i).getPayCount());
			pstmt.setDouble(80, this.get(i).getPayMoney());
			if(this.get(i).getLastPayToDate() == null || this.get(i).getLastPayToDate().equals("null")) {
				pstmt.setDate(81,null);
			} else {
				pstmt.setDate(81, Date.valueOf(this.get(i).getLastPayToDate()));
			}
			if(this.get(i).getCurPayToDate() == null || this.get(i).getCurPayToDate().equals("null")) {
				pstmt.setDate(82,null);
			} else {
				pstmt.setDate(82, Date.valueOf(this.get(i).getCurPayToDate()));
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getEdorNo());
			}
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getFeeFinaType());
			}
			pstmt.setDouble(86, this.get(i).getChangeRate());
			if(this.get(i).getAccCurrency() == null || this.get(i).getAccCurrency().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getAccCurrency());
			}
			pstmt.setDouble(88, this.get(i).getAccAmnt());
			pstmt.setDouble(89, this.get(i).getPreStandPrem());
			pstmt.setDouble(90, this.get(i).getPrePrem());
			pstmt.setDouble(91, this.get(i).getPreRiskAmnt());
			pstmt.setDouble(92, this.get(i).getPreAmnt());
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getClmNo());
			}
			if(this.get(i).getClmFeeOperationType() == null || this.get(i).getClmFeeOperationType().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getClmFeeOperationType());
			}
			if(this.get(i).getClmFeeFinaType() == null || this.get(i).getClmFeeFinaType().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getClmFeeFinaType());
			}
			pstmt.setDouble(96, this.get(i).getStandGetMoney());
			pstmt.setDouble(97, this.get(i).getGetRate());
			pstmt.setDouble(98, this.get(i).getClmGetMoney());
			if(this.get(i).getAccDate() == null || this.get(i).getAccDate().equals("null")) {
				pstmt.setDate(99,null);
			} else {
				pstmt.setDate(99, Date.valueOf(this.get(i).getAccDate()));
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getRIContNo() == null || this.get(i).getRIContNo().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getRIContNo());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getRIPreceptNo());
			}
			if(this.get(i).getReinsreFlag() == null || this.get(i).getReinsreFlag().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getReinsreFlag());
			}
			if(this.get(i).getGetDate() == null || this.get(i).getGetDate().equals("null")) {
				pstmt.setDate(104,null);
			} else {
				pstmt.setDate(104, Date.valueOf(this.get(i).getGetDate()));
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getStandbyString3());
			}
			pstmt.setDouble(108, this.get(i).getStandbyDouble1());
			pstmt.setDouble(109, this.get(i).getStandbyDouble2());
			pstmt.setDouble(110, this.get(i).getStandbyDouble3());
			if(this.get(i).getStandbyDate1() == null || this.get(i).getStandbyDate1().equals("null")) {
				pstmt.setDate(111,null);
			} else {
				pstmt.setDate(111, Date.valueOf(this.get(i).getStandbyDate1()));
			}
			if(this.get(i).getStandbyDate2() == null || this.get(i).getStandbyDate2().equals("null")) {
				pstmt.setDate(112,null);
			} else {
				pstmt.setDate(112, Date.valueOf(this.get(i).getStandbyDate2()));
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getManageCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(114,null);
			} else {
				pstmt.setDate(114, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getMakeTime());
			}
			pstmt.setDouble(116, this.get(i).getPreChRiskAmnt());
			pstmt.setDouble(117, this.get(i).getChRiskAmnt());
			pstmt.setDouble(118, this.get(i).getPreChangeRate());
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(119,null);
			} else {
				pstmt.setString(119, this.get(i).getMainPolNo());
			}
			if(this.get(i).getRIPreceptType() == null || this.get(i).getRIPreceptType().equals("null")) {
				pstmt.setString(120,null);
			} else {
				pstmt.setString(120, this.get(i).getRIPreceptType());
			}
			if(this.get(i).getBFFlag() == null || this.get(i).getBFFlag().equals("null")) {
				pstmt.setString(121,null);
			} else {
				pstmt.setString(121, this.get(i).getBFFlag());
			}
			pstmt.setDouble(122, this.get(i).getEdorPrem());
			pstmt.setDouble(123, this.get(i).getChEdorPrem());
			pstmt.setDouble(124, this.get(i).getChPrem());
			pstmt.setDouble(125, this.get(i).getBonus1());
			pstmt.setDouble(126, this.get(i).getBonus2());
			pstmt.setDouble(127, this.get(i).getBonus3());
			pstmt.setDouble(128, this.get(i).getExtRate1());
			pstmt.setDouble(129, this.get(i).getExtRate2());
			pstmt.setDouble(130, this.get(i).getExtRate3());
			pstmt.setDouble(131, this.get(i).getExtPrem1());
			pstmt.setDouble(132, this.get(i).getExtPrem2());
			pstmt.setDouble(133, this.get(i).getExtPrem3());
			if(this.get(i).getStandbyString4() == null || this.get(i).getStandbyString4().equals("null")) {
				pstmt.setString(134,null);
			} else {
				pstmt.setString(134, this.get(i).getStandbyString4());
			}
			if(this.get(i).getStandbyString5() == null || this.get(i).getStandbyString5().equals("null")) {
				pstmt.setString(135,null);
			} else {
				pstmt.setString(135, this.get(i).getStandbyString5());
			}
			if(this.get(i).getStandbyString6() == null || this.get(i).getStandbyString6().equals("null")) {
				pstmt.setString(136,null);
			} else {
				pstmt.setString(136, this.get(i).getStandbyString6());
			}
			if(this.get(i).getStandbyString7() == null || this.get(i).getStandbyString7().equals("null")) {
				pstmt.setString(137,null);
			} else {
				pstmt.setString(137, this.get(i).getStandbyString7());
			}
			if(this.get(i).getStandbyString8() == null || this.get(i).getStandbyString8().equals("null")) {
				pstmt.setString(138,null);
			} else {
				pstmt.setString(138, this.get(i).getStandbyString8());
			}
			if(this.get(i).getStandbyString9() == null || this.get(i).getStandbyString9().equals("null")) {
				pstmt.setString(139,null);
			} else {
				pstmt.setString(139, this.get(i).getStandbyString9());
			}
			pstmt.setDouble(140, this.get(i).getStandbyDouble4());
			pstmt.setDouble(141, this.get(i).getStandbyDouble5());
			pstmt.setDouble(142, this.get(i).getStandbyDouble6());
			pstmt.setDouble(143, this.get(i).getStandbyDouble7());
			pstmt.setDouble(144, this.get(i).getStandbyDouble8());
			pstmt.setDouble(145, this.get(i).getStandbyDouble9());
			if(this.get(i).getStandbyDate3() == null || this.get(i).getStandbyDate3().equals("null")) {
				pstmt.setDate(146,null);
			} else {
				pstmt.setDate(146, Date.valueOf(this.get(i).getStandbyDate3()));
			}
			if(this.get(i).getStandbyDate4() == null || this.get(i).getStandbyDate4().equals("null")) {
				pstmt.setDate(147,null);
			} else {
				pstmt.setDate(147, Date.valueOf(this.get(i).getStandbyDate4()));
			}
			if(this.get(i).getStandbyDate5() == null || this.get(i).getStandbyDate5().equals("null")) {
				pstmt.setDate(148,null);
			} else {
				pstmt.setDate(148, Date.valueOf(this.get(i).getStandbyDate5()));
			}
			// set where condition
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(149,null);
			} else {
				pstmt.setString(149, this.get(i).getEventNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPolRecord");
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
			tError.moduleName = "RIPolRecordDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIPolRecord(BatchNo ,EventNo ,EventType ,RecordType ,NodeState ,DataFlag ,UnionKey ,GrpContNo ,ProposalGrpContNo ,GrpPolNo ,GrpProposalNo ,ContNo ,PolNo ,ProposalNo ,ContPlanCode ,PlanType ,RiskPeriod ,RiskType ,RiskCode ,DutyCode ,Years ,InsuredYear ,SaleChnl ,CValiDate ,EndDate ,InsuredNo ,InsuredName ,InsuredSex ,InsuredBirthday ,InsuredAge ,CurrentAge ,IDType ,IDNo ,OccupationType ,HealthTime1 ,HealthTime2 ,OccupationCode ,SuppRiskScore ,SmokeFlag ,AdditionalRate ,ExtType ,ExtPrem ,BegAccountValue ,EndUnit ,EndPrice ,EndAccountValue ,FeeType ,FeeMoney ,NonCashFlag ,StandPrem ,Prem ,Amnt ,Mult ,Reserve ,APE ,CSV ,Retention ,Suminsured ,Faceamount ,Currency ,RiskAmnt ,PayIntv ,PayYears ,PayEndYearFlag ,PayEndYear ,GetYearFlag ,GetYear ,InsuYearFlag ,InsuYear ,AcciYearFlag ,AcciYear ,AcciEndDate ,GetStartDate ,GetStartType ,PeakLine ,GetLimit ,GetIntv ,PayNo ,PayCount ,PayMoney ,LastPayToDate ,CurPayToDate ,EdorNo ,FeeOperationType ,FeeFinaType ,ChangeRate ,AccCurrency ,AccAmnt ,PreStandPrem ,PrePrem ,PreRiskAmnt ,PreAmnt ,ClmNo ,ClmFeeOperationType ,ClmFeeFinaType ,StandGetMoney ,GetRate ,ClmGetMoney ,AccDate ,AccumulateDefNO ,RIContNo ,RIPreceptNo ,ReinsreFlag ,GetDate ,StandbyString1 ,StandbyString2 ,StandbyString3 ,StandbyDouble1 ,StandbyDouble2 ,StandbyDouble3 ,StandbyDate1 ,StandbyDate2 ,ManageCom ,MakeDate ,MakeTime ,PreChRiskAmnt ,ChRiskAmnt ,PreChangeRate ,MainPolNo ,RIPreceptType ,BFFlag ,EdorPrem ,ChEdorPrem ,ChPrem ,Bonus1 ,Bonus2 ,Bonus3 ,ExtRate1 ,ExtRate2 ,ExtRate3 ,ExtPrem1 ,ExtPrem2 ,ExtPrem3 ,StandbyString4 ,StandbyString5 ,StandbyString6 ,StandbyString7 ,StandbyString8 ,StandbyString9 ,StandbyDouble4 ,StandbyDouble5 ,StandbyDouble6 ,StandbyDouble7 ,StandbyDouble8 ,StandbyDouble9 ,StandbyDate3 ,StandbyDate4 ,StandbyDate5) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo());
			}
			if(this.get(i).getEventNo() == null || this.get(i).getEventNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEventNo());
			}
			if(this.get(i).getEventType() == null || this.get(i).getEventType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEventType());
			}
			if(this.get(i).getRecordType() == null || this.get(i).getRecordType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRecordType());
			}
			if(this.get(i).getNodeState() == null || this.get(i).getNodeState().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getNodeState());
			}
			if(this.get(i).getDataFlag() == null || this.get(i).getDataFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDataFlag());
			}
			if(this.get(i).getUnionKey() == null || this.get(i).getUnionKey().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getUnionKey());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getGrpProposalNo() == null || this.get(i).getGrpProposalNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getGrpProposalNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPolNo());
			}
			if(this.get(i).getProposalNo() == null || this.get(i).getProposalNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getProposalNo());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getContPlanCode());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getPlanType());
			}
			if(this.get(i).getRiskPeriod() == null || this.get(i).getRiskPeriod().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRiskPeriod());
			}
			if(this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getDutyCode());
			}
			pstmt.setInt(21, this.get(i).getYears());
			pstmt.setInt(22, this.get(i).getInsuredYear());
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getSaleChnl());
			}
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(30, this.get(i).getInsuredAge());
			pstmt.setInt(31, this.get(i).getCurrentAge());
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getIDNo());
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOccupationType());
			}
			if(this.get(i).getHealthTime1() == null || this.get(i).getHealthTime1().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getHealthTime1());
			}
			if(this.get(i).getHealthTime2() == null || this.get(i).getHealthTime2().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getHealthTime2());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOccupationCode());
			}
			if(this.get(i).getSuppRiskScore() == null || this.get(i).getSuppRiskScore().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSuppRiskScore());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSmokeFlag());
			}
			pstmt.setDouble(40, this.get(i).getAdditionalRate());
			if(this.get(i).getExtType() == null || this.get(i).getExtType().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getExtType());
			}
			pstmt.setDouble(42, this.get(i).getExtPrem());
			pstmt.setDouble(43, this.get(i).getBegAccountValue());
			pstmt.setInt(44, this.get(i).getEndUnit());
			pstmt.setDouble(45, this.get(i).getEndPrice());
			pstmt.setDouble(46, this.get(i).getEndAccountValue());
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getFeeType());
			}
			pstmt.setDouble(48, this.get(i).getFeeMoney());
			if(this.get(i).getNonCashFlag() == null || this.get(i).getNonCashFlag().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getNonCashFlag());
			}
			pstmt.setDouble(50, this.get(i).getStandPrem());
			pstmt.setDouble(51, this.get(i).getPrem());
			pstmt.setDouble(52, this.get(i).getAmnt());
			pstmt.setDouble(53, this.get(i).getMult());
			pstmt.setDouble(54, this.get(i).getReserve());
			pstmt.setDouble(55, this.get(i).getAPE());
			pstmt.setDouble(56, this.get(i).getCSV());
			pstmt.setDouble(57, this.get(i).getRetention());
			pstmt.setDouble(58, this.get(i).getSuminsured());
			pstmt.setDouble(59, this.get(i).getFaceamount());
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getCurrency());
			}
			pstmt.setDouble(61, this.get(i).getRiskAmnt());
			pstmt.setInt(62, this.get(i).getPayIntv());
			pstmt.setInt(63, this.get(i).getPayYears());
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(65, this.get(i).getPayEndYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getGetYearFlag());
			}
			pstmt.setInt(67, this.get(i).getGetYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getInsuYearFlag());
			}
			pstmt.setInt(69, this.get(i).getInsuYear());
			if(this.get(i).getAcciYearFlag() == null || this.get(i).getAcciYearFlag().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getAcciYearFlag());
			}
			pstmt.setInt(71, this.get(i).getAcciYear());
			if(this.get(i).getAcciEndDate() == null || this.get(i).getAcciEndDate().equals("null")) {
				pstmt.setDate(72,null);
			} else {
				pstmt.setDate(72, Date.valueOf(this.get(i).getAcciEndDate()));
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getGetStartDate()));
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getGetStartType());
			}
			pstmt.setDouble(75, this.get(i).getPeakLine());
			pstmt.setDouble(76, this.get(i).getGetLimit());
			pstmt.setInt(77, this.get(i).getGetIntv());
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getPayNo());
			}
			pstmt.setInt(79, this.get(i).getPayCount());
			pstmt.setDouble(80, this.get(i).getPayMoney());
			if(this.get(i).getLastPayToDate() == null || this.get(i).getLastPayToDate().equals("null")) {
				pstmt.setDate(81,null);
			} else {
				pstmt.setDate(81, Date.valueOf(this.get(i).getLastPayToDate()));
			}
			if(this.get(i).getCurPayToDate() == null || this.get(i).getCurPayToDate().equals("null")) {
				pstmt.setDate(82,null);
			} else {
				pstmt.setDate(82, Date.valueOf(this.get(i).getCurPayToDate()));
			}
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getEdorNo());
			}
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getFeeFinaType());
			}
			pstmt.setDouble(86, this.get(i).getChangeRate());
			if(this.get(i).getAccCurrency() == null || this.get(i).getAccCurrency().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getAccCurrency());
			}
			pstmt.setDouble(88, this.get(i).getAccAmnt());
			pstmt.setDouble(89, this.get(i).getPreStandPrem());
			pstmt.setDouble(90, this.get(i).getPrePrem());
			pstmt.setDouble(91, this.get(i).getPreRiskAmnt());
			pstmt.setDouble(92, this.get(i).getPreAmnt());
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getClmNo());
			}
			if(this.get(i).getClmFeeOperationType() == null || this.get(i).getClmFeeOperationType().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getClmFeeOperationType());
			}
			if(this.get(i).getClmFeeFinaType() == null || this.get(i).getClmFeeFinaType().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getClmFeeFinaType());
			}
			pstmt.setDouble(96, this.get(i).getStandGetMoney());
			pstmt.setDouble(97, this.get(i).getGetRate());
			pstmt.setDouble(98, this.get(i).getClmGetMoney());
			if(this.get(i).getAccDate() == null || this.get(i).getAccDate().equals("null")) {
				pstmt.setDate(99,null);
			} else {
				pstmt.setDate(99, Date.valueOf(this.get(i).getAccDate()));
			}
			if(this.get(i).getAccumulateDefNO() == null || this.get(i).getAccumulateDefNO().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getAccumulateDefNO());
			}
			if(this.get(i).getRIContNo() == null || this.get(i).getRIContNo().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getRIContNo());
			}
			if(this.get(i).getRIPreceptNo() == null || this.get(i).getRIPreceptNo().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getRIPreceptNo());
			}
			if(this.get(i).getReinsreFlag() == null || this.get(i).getReinsreFlag().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getReinsreFlag());
			}
			if(this.get(i).getGetDate() == null || this.get(i).getGetDate().equals("null")) {
				pstmt.setDate(104,null);
			} else {
				pstmt.setDate(104, Date.valueOf(this.get(i).getGetDate()));
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getStandbyString3());
			}
			pstmt.setDouble(108, this.get(i).getStandbyDouble1());
			pstmt.setDouble(109, this.get(i).getStandbyDouble2());
			pstmt.setDouble(110, this.get(i).getStandbyDouble3());
			if(this.get(i).getStandbyDate1() == null || this.get(i).getStandbyDate1().equals("null")) {
				pstmt.setDate(111,null);
			} else {
				pstmt.setDate(111, Date.valueOf(this.get(i).getStandbyDate1()));
			}
			if(this.get(i).getStandbyDate2() == null || this.get(i).getStandbyDate2().equals("null")) {
				pstmt.setDate(112,null);
			} else {
				pstmt.setDate(112, Date.valueOf(this.get(i).getStandbyDate2()));
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(113,null);
			} else {
				pstmt.setString(113, this.get(i).getManageCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(114,null);
			} else {
				pstmt.setDate(114, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(115,null);
			} else {
				pstmt.setString(115, this.get(i).getMakeTime());
			}
			pstmt.setDouble(116, this.get(i).getPreChRiskAmnt());
			pstmt.setDouble(117, this.get(i).getChRiskAmnt());
			pstmt.setDouble(118, this.get(i).getPreChangeRate());
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(119,null);
			} else {
				pstmt.setString(119, this.get(i).getMainPolNo());
			}
			if(this.get(i).getRIPreceptType() == null || this.get(i).getRIPreceptType().equals("null")) {
				pstmt.setString(120,null);
			} else {
				pstmt.setString(120, this.get(i).getRIPreceptType());
			}
			if(this.get(i).getBFFlag() == null || this.get(i).getBFFlag().equals("null")) {
				pstmt.setString(121,null);
			} else {
				pstmt.setString(121, this.get(i).getBFFlag());
			}
			pstmt.setDouble(122, this.get(i).getEdorPrem());
			pstmt.setDouble(123, this.get(i).getChEdorPrem());
			pstmt.setDouble(124, this.get(i).getChPrem());
			pstmt.setDouble(125, this.get(i).getBonus1());
			pstmt.setDouble(126, this.get(i).getBonus2());
			pstmt.setDouble(127, this.get(i).getBonus3());
			pstmt.setDouble(128, this.get(i).getExtRate1());
			pstmt.setDouble(129, this.get(i).getExtRate2());
			pstmt.setDouble(130, this.get(i).getExtRate3());
			pstmt.setDouble(131, this.get(i).getExtPrem1());
			pstmt.setDouble(132, this.get(i).getExtPrem2());
			pstmt.setDouble(133, this.get(i).getExtPrem3());
			if(this.get(i).getStandbyString4() == null || this.get(i).getStandbyString4().equals("null")) {
				pstmt.setString(134,null);
			} else {
				pstmt.setString(134, this.get(i).getStandbyString4());
			}
			if(this.get(i).getStandbyString5() == null || this.get(i).getStandbyString5().equals("null")) {
				pstmt.setString(135,null);
			} else {
				pstmt.setString(135, this.get(i).getStandbyString5());
			}
			if(this.get(i).getStandbyString6() == null || this.get(i).getStandbyString6().equals("null")) {
				pstmt.setString(136,null);
			} else {
				pstmt.setString(136, this.get(i).getStandbyString6());
			}
			if(this.get(i).getStandbyString7() == null || this.get(i).getStandbyString7().equals("null")) {
				pstmt.setString(137,null);
			} else {
				pstmt.setString(137, this.get(i).getStandbyString7());
			}
			if(this.get(i).getStandbyString8() == null || this.get(i).getStandbyString8().equals("null")) {
				pstmt.setString(138,null);
			} else {
				pstmt.setString(138, this.get(i).getStandbyString8());
			}
			if(this.get(i).getStandbyString9() == null || this.get(i).getStandbyString9().equals("null")) {
				pstmt.setString(139,null);
			} else {
				pstmt.setString(139, this.get(i).getStandbyString9());
			}
			pstmt.setDouble(140, this.get(i).getStandbyDouble4());
			pstmt.setDouble(141, this.get(i).getStandbyDouble5());
			pstmt.setDouble(142, this.get(i).getStandbyDouble6());
			pstmt.setDouble(143, this.get(i).getStandbyDouble7());
			pstmt.setDouble(144, this.get(i).getStandbyDouble8());
			pstmt.setDouble(145, this.get(i).getStandbyDouble9());
			if(this.get(i).getStandbyDate3() == null || this.get(i).getStandbyDate3().equals("null")) {
				pstmt.setDate(146,null);
			} else {
				pstmt.setDate(146, Date.valueOf(this.get(i).getStandbyDate3()));
			}
			if(this.get(i).getStandbyDate4() == null || this.get(i).getStandbyDate4().equals("null")) {
				pstmt.setDate(147,null);
			} else {
				pstmt.setDate(147, Date.valueOf(this.get(i).getStandbyDate4()));
			}
			if(this.get(i).getStandbyDate5() == null || this.get(i).getStandbyDate5().equals("null")) {
				pstmt.setDate(148,null);
			} else {
				pstmt.setDate(148, Date.valueOf(this.get(i).getStandbyDate5()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPolRecord");
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
			tError.moduleName = "RIPolRecordDBSet";
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

