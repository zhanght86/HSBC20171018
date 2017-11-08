

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.RIPolRecordBakeBSchema;
import com.sinosoft.lis.vschema.RIPolRecordBakeBSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIPolRecordBakeBDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIPolRecordBakeBDB extends RIPolRecordBakeBSchema
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
	public RIPolRecordBakeBDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "RIPolRecordBakeB" );
		mflag = true;
	}

	public RIPolRecordBakeBDB()
	{
		con = null;
		db = new DBOper( "RIPolRecordBakeB" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		RIPolRecordBakeBSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		RIPolRecordBakeBSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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
			pstmt = con.prepareStatement("DELETE FROM RIPolRecordBakeB WHERE  BakeSerialNo = ? AND EventNo = ?");
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEventNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIPolRecordBakeB");
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
		SQLString sqlObj = new SQLString("RIPolRecordBakeB");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE RIPolRecordBakeB SET  BakeSerialNo = ? , BatchNo = ? , EventNo = ? , EventType = ? , RecordType = ? , NodeState = ? , DataFlag = ? , UnionKey = ? , GrpContNo = ? , ProposalGrpContNo = ? , GrpPolNo = ? , GrpProposalNo = ? , ContNo = ? , PolNo = ? , ProposalNo = ? , ContPlanCode = ? , PlanType = ? , RiskPeriod = ? , RiskType = ? , RiskCode = ? , DutyCode = ? , Years = ? , InsuredYear = ? , SaleChnl = ? , CValiDate = ? , EndDate = ? , InsuredNo = ? , InsuredName = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAge = ? , CurrentAge = ? , IDType = ? , IDNo = ? , OccupationType = ? , HealthTime1 = ? , HealthTime2 = ? , OccupationCode = ? , SuppRiskScore = ? , SmokeFlag = ? , AdditionalRate = ? , ExtType = ? , ExtPrem = ? , BegAccountValue = ? , EndUnit = ? , EndPrice = ? , EndAccountValue = ? , FeeType = ? , FeeMoney = ? , NonCashFlag = ? , StandPrem = ? , Prem = ? , Amnt = ? , Mult = ? , Reserve = ? , APE = ? , CSV = ? , Retention = ? , Suminsured = ? , Faceamount = ? , Currency = ? , RiskAmnt = ? , PayIntv = ? , PayYears = ? , PayEndYearFlag = ? , PayEndYear = ? , GetYearFlag = ? , GetYear = ? , InsuYearFlag = ? , InsuYear = ? , AcciYearFlag = ? , AcciYear = ? , AcciEndDate = ? , GetStartDate = ? , GetStartType = ? , PeakLine = ? , GetLimit = ? , GetIntv = ? , PayNo = ? , PayCount = ? , PayMoney = ? , LastPayToDate = ? , CurPayToDate = ? , EdorNo = ? , FeeOperationType = ? , FeeFinaType = ? , ChangeRate = ? , AccCurrency = ? , AccAmnt = ? , PreStandPrem = ? , PrePrem = ? , PreRiskAmnt = ? , PreAmnt = ? , ClmNo = ? , ClmFeeOperationType = ? , ClmFeeFinaType = ? , StandGetMoney = ? , GetRate = ? , ClmGetMoney = ? , AccDate = ? , AccumulateDefNO = ? , RIContNo = ? , RIPreceptNo = ? , ReinsreFlag = ? , GetDate = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? , StandbyDouble1 = ? , StandbyDouble2 = ? , StandbyDouble3 = ? , StandbyDate1 = ? , StandbyDate2 = ? , ManageCom = ? , MakeDate = ? , MakeTime = ? , PreChRiskAmnt = ? , ChRiskAmnt = ? , PreChangeRate = ? , MainPolNo = ? , RIPreceptType = ? , BFFlag = ? , EdorPrem = ? , ChEdorPrem = ? , ChPrem = ? , Bonus1 = ? , Bonus2 = ? , Bonus3 = ? , ExtRate1 = ? , ExtRate2 = ? , ExtRate3 = ? , ExtPrem1 = ? , ExtPrem2 = ? , ExtPrem3 = ? , StandbyString4 = ? , StandbyString5 = ? , StandbyString6 = ? , StandbyString7 = ? , StandbyString8 = ? , StandbyString9 = ? , StandbyDouble4 = ? , StandbyDouble5 = ? , StandbyDouble6 = ? , StandbyDouble7 = ? , StandbyDouble8 = ? , StandbyDouble9 = ? , StandbyDate3 = ? , StandbyDate4 = ? , StandbyDate5 = ? WHERE  BakeSerialNo = ? AND EventNo = ?");
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEventNo());
			}
			if(this.getEventType() == null || this.getEventType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEventType());
			}
			if(this.getRecordType() == null || this.getRecordType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRecordType());
			}
			if(this.getNodeState() == null || this.getNodeState().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getNodeState());
			}
			if(this.getDataFlag() == null || this.getDataFlag().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDataFlag());
			}
			if(this.getUnionKey() == null || this.getUnionKey().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getUnionKey());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getGrpContNo());
			}
			if(this.getProposalGrpContNo() == null || this.getProposalGrpContNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getProposalGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getGrpPolNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getGrpProposalNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPolNo());
			}
			if(this.getProposalNo() == null || this.getProposalNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getProposalNo());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContPlanCode());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPlanType());
			}
			if(this.getRiskPeriod() == null || this.getRiskPeriod().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskPeriod());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getRiskType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getDutyCode());
			}
			pstmt.setInt(22, this.getYears());
			pstmt.setInt(23, this.getInsuredYear());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSaleChnl());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getCValiDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getEndDate()));
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getInsuredName());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getInsuredBirthday()));
			}
			pstmt.setInt(31, this.getInsuredAge());
			pstmt.setInt(32, this.getCurrentAge());
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getIDNo());
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOccupationType());
			}
			if(this.getHealthTime1() == null || this.getHealthTime1().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getHealthTime1());
			}
			if(this.getHealthTime2() == null || this.getHealthTime2().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getHealthTime2());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOccupationCode());
			}
			if(this.getSuppRiskScore() == null || this.getSuppRiskScore().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSuppRiskScore());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getSmokeFlag());
			}
			pstmt.setDouble(41, this.getAdditionalRate());
			if(this.getExtType() == null || this.getExtType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getExtType());
			}
			pstmt.setDouble(43, this.getExtPrem());
			pstmt.setDouble(44, this.getBegAccountValue());
			pstmt.setInt(45, this.getEndUnit());
			pstmt.setDouble(46, this.getEndPrice());
			pstmt.setDouble(47, this.getEndAccountValue());
			if(this.getFeeType() == null || this.getFeeType().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getFeeType());
			}
			pstmt.setDouble(49, this.getFeeMoney());
			if(this.getNonCashFlag() == null || this.getNonCashFlag().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getNonCashFlag());
			}
			pstmt.setDouble(51, this.getStandPrem());
			pstmt.setDouble(52, this.getPrem());
			pstmt.setDouble(53, this.getAmnt());
			pstmt.setDouble(54, this.getMult());
			pstmt.setDouble(55, this.getReserve());
			pstmt.setDouble(56, this.getAPE());
			pstmt.setDouble(57, this.getCSV());
			pstmt.setDouble(58, this.getRetention());
			pstmt.setDouble(59, this.getSuminsured());
			pstmt.setDouble(60, this.getFaceamount());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getCurrency());
			}
			pstmt.setDouble(62, this.getRiskAmnt());
			pstmt.setInt(63, this.getPayIntv());
			pstmt.setInt(64, this.getPayYears());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getPayEndYearFlag());
			}
			pstmt.setInt(66, this.getPayEndYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getGetYearFlag());
			}
			pstmt.setInt(68, this.getGetYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getInsuYearFlag());
			}
			pstmt.setInt(70, this.getInsuYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getAcciYearFlag());
			}
			pstmt.setInt(72, this.getAcciYear());
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(73, 91);
			} else {
				pstmt.setDate(73, Date.valueOf(this.getAcciEndDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getGetStartType());
			}
			pstmt.setDouble(76, this.getPeakLine());
			pstmt.setDouble(77, this.getGetLimit());
			pstmt.setInt(78, this.getGetIntv());
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getPayNo());
			}
			pstmt.setInt(80, this.getPayCount());
			pstmt.setDouble(81, this.getPayMoney());
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(82, 91);
			} else {
				pstmt.setDate(82, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(83, 91);
			} else {
				pstmt.setDate(83, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getEdorNo());
			}
			if(this.getFeeOperationType() == null || this.getFeeOperationType().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getFeeOperationType());
			}
			if(this.getFeeFinaType() == null || this.getFeeFinaType().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getFeeFinaType());
			}
			pstmt.setDouble(87, this.getChangeRate());
			if(this.getAccCurrency() == null || this.getAccCurrency().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getAccCurrency());
			}
			pstmt.setDouble(89, this.getAccAmnt());
			pstmt.setDouble(90, this.getPreStandPrem());
			pstmt.setDouble(91, this.getPrePrem());
			pstmt.setDouble(92, this.getPreRiskAmnt());
			pstmt.setDouble(93, this.getPreAmnt());
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getClmNo());
			}
			if(this.getClmFeeOperationType() == null || this.getClmFeeOperationType().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getClmFeeOperationType());
			}
			if(this.getClmFeeFinaType() == null || this.getClmFeeFinaType().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getClmFeeFinaType());
			}
			pstmt.setDouble(97, this.getStandGetMoney());
			pstmt.setDouble(98, this.getGetRate());
			pstmt.setDouble(99, this.getClmGetMoney());
			if(this.getAccDate() == null || this.getAccDate().equals("null")) {
				pstmt.setNull(100, 91);
			} else {
				pstmt.setDate(100, Date.valueOf(this.getAccDate()));
			}
			if(this.getAccumulateDefNO() == null || this.getAccumulateDefNO().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getAccumulateDefNO());
			}
			if(this.getRIContNo() == null || this.getRIContNo().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getRIContNo());
			}
			if(this.getRIPreceptNo() == null || this.getRIPreceptNo().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getRIPreceptNo());
			}
			if(this.getReinsreFlag() == null || this.getReinsreFlag().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getReinsreFlag());
			}
			if(this.getGetDate() == null || this.getGetDate().equals("null")) {
				pstmt.setNull(105, 91);
			} else {
				pstmt.setDate(105, Date.valueOf(this.getGetDate()));
			}
			if(this.getStandbyString1() == null || this.getStandbyString1().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getStandbyString1());
			}
			if(this.getStandbyString2() == null || this.getStandbyString2().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getStandbyString2());
			}
			if(this.getStandbyString3() == null || this.getStandbyString3().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getStandbyString3());
			}
			pstmt.setDouble(109, this.getStandbyDouble1());
			pstmt.setDouble(110, this.getStandbyDouble2());
			pstmt.setDouble(111, this.getStandbyDouble3());
			if(this.getStandbyDate1() == null || this.getStandbyDate1().equals("null")) {
				pstmt.setNull(112, 91);
			} else {
				pstmt.setDate(112, Date.valueOf(this.getStandbyDate1()));
			}
			if(this.getStandbyDate2() == null || this.getStandbyDate2().equals("null")) {
				pstmt.setNull(113, 91);
			} else {
				pstmt.setDate(113, Date.valueOf(this.getStandbyDate2()));
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getManageCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(115, 91);
			} else {
				pstmt.setDate(115, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getMakeTime());
			}
			pstmt.setDouble(117, this.getPreChRiskAmnt());
			pstmt.setDouble(118, this.getChRiskAmnt());
			pstmt.setDouble(119, this.getPreChangeRate());
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(120, 12);
			} else {
				pstmt.setString(120, this.getMainPolNo());
			}
			if(this.getRIPreceptType() == null || this.getRIPreceptType().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getRIPreceptType());
			}
			if(this.getBFFlag() == null || this.getBFFlag().equals("null")) {
				pstmt.setNull(122, 12);
			} else {
				pstmt.setString(122, this.getBFFlag());
			}
			pstmt.setDouble(123, this.getEdorPrem());
			pstmt.setDouble(124, this.getChEdorPrem());
			pstmt.setDouble(125, this.getChPrem());
			pstmt.setDouble(126, this.getBonus1());
			pstmt.setDouble(127, this.getBonus2());
			pstmt.setDouble(128, this.getBonus3());
			pstmt.setDouble(129, this.getExtRate1());
			pstmt.setDouble(130, this.getExtRate2());
			pstmt.setDouble(131, this.getExtRate3());
			pstmt.setDouble(132, this.getExtPrem1());
			pstmt.setDouble(133, this.getExtPrem2());
			pstmt.setDouble(134, this.getExtPrem3());
			if(this.getStandbyString4() == null || this.getStandbyString4().equals("null")) {
				pstmt.setNull(135, 12);
			} else {
				pstmt.setString(135, this.getStandbyString4());
			}
			if(this.getStandbyString5() == null || this.getStandbyString5().equals("null")) {
				pstmt.setNull(136, 12);
			} else {
				pstmt.setString(136, this.getStandbyString5());
			}
			if(this.getStandbyString6() == null || this.getStandbyString6().equals("null")) {
				pstmt.setNull(137, 12);
			} else {
				pstmt.setString(137, this.getStandbyString6());
			}
			if(this.getStandbyString7() == null || this.getStandbyString7().equals("null")) {
				pstmt.setNull(138, 12);
			} else {
				pstmt.setString(138, this.getStandbyString7());
			}
			if(this.getStandbyString8() == null || this.getStandbyString8().equals("null")) {
				pstmt.setNull(139, 12);
			} else {
				pstmt.setString(139, this.getStandbyString8());
			}
			if(this.getStandbyString9() == null || this.getStandbyString9().equals("null")) {
				pstmt.setNull(140, 12);
			} else {
				pstmt.setString(140, this.getStandbyString9());
			}
			pstmt.setDouble(141, this.getStandbyDouble4());
			pstmt.setDouble(142, this.getStandbyDouble5());
			pstmt.setDouble(143, this.getStandbyDouble6());
			pstmt.setDouble(144, this.getStandbyDouble7());
			pstmt.setDouble(145, this.getStandbyDouble8());
			pstmt.setDouble(146, this.getStandbyDouble9());
			if(this.getStandbyDate3() == null || this.getStandbyDate3().equals("null")) {
				pstmt.setNull(147, 91);
			} else {
				pstmt.setDate(147, Date.valueOf(this.getStandbyDate3()));
			}
			if(this.getStandbyDate4() == null || this.getStandbyDate4().equals("null")) {
				pstmt.setNull(148, 91);
			} else {
				pstmt.setDate(148, Date.valueOf(this.getStandbyDate4()));
			}
			if(this.getStandbyDate5() == null || this.getStandbyDate5().equals("null")) {
				pstmt.setNull(149, 91);
			} else {
				pstmt.setDate(149, Date.valueOf(this.getStandbyDate5()));
			}
			// set where condition
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(150, 12);
			} else {
				pstmt.setString(150, this.getBakeSerialNo());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(151, 12);
			} else {
				pstmt.setString(151, this.getEventNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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
		SQLString sqlObj = new SQLString("RIPolRecordBakeB");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO RIPolRecordBakeB(BakeSerialNo ,BatchNo ,EventNo ,EventType ,RecordType ,NodeState ,DataFlag ,UnionKey ,GrpContNo ,ProposalGrpContNo ,GrpPolNo ,GrpProposalNo ,ContNo ,PolNo ,ProposalNo ,ContPlanCode ,PlanType ,RiskPeriod ,RiskType ,RiskCode ,DutyCode ,Years ,InsuredYear ,SaleChnl ,CValiDate ,EndDate ,InsuredNo ,InsuredName ,InsuredSex ,InsuredBirthday ,InsuredAge ,CurrentAge ,IDType ,IDNo ,OccupationType ,HealthTime1 ,HealthTime2 ,OccupationCode ,SuppRiskScore ,SmokeFlag ,AdditionalRate ,ExtType ,ExtPrem ,BegAccountValue ,EndUnit ,EndPrice ,EndAccountValue ,FeeType ,FeeMoney ,NonCashFlag ,StandPrem ,Prem ,Amnt ,Mult ,Reserve ,APE ,CSV ,Retention ,Suminsured ,Faceamount ,Currency ,RiskAmnt ,PayIntv ,PayYears ,PayEndYearFlag ,PayEndYear ,GetYearFlag ,GetYear ,InsuYearFlag ,InsuYear ,AcciYearFlag ,AcciYear ,AcciEndDate ,GetStartDate ,GetStartType ,PeakLine ,GetLimit ,GetIntv ,PayNo ,PayCount ,PayMoney ,LastPayToDate ,CurPayToDate ,EdorNo ,FeeOperationType ,FeeFinaType ,ChangeRate ,AccCurrency ,AccAmnt ,PreStandPrem ,PrePrem ,PreRiskAmnt ,PreAmnt ,ClmNo ,ClmFeeOperationType ,ClmFeeFinaType ,StandGetMoney ,GetRate ,ClmGetMoney ,AccDate ,AccumulateDefNO ,RIContNo ,RIPreceptNo ,ReinsreFlag ,GetDate ,StandbyString1 ,StandbyString2 ,StandbyString3 ,StandbyDouble1 ,StandbyDouble2 ,StandbyDouble3 ,StandbyDate1 ,StandbyDate2 ,ManageCom ,MakeDate ,MakeTime ,PreChRiskAmnt ,ChRiskAmnt ,PreChangeRate ,MainPolNo ,RIPreceptType ,BFFlag ,EdorPrem ,ChEdorPrem ,ChPrem ,Bonus1 ,Bonus2 ,Bonus3 ,ExtRate1 ,ExtRate2 ,ExtRate3 ,ExtPrem1 ,ExtPrem2 ,ExtPrem3 ,StandbyString4 ,StandbyString5 ,StandbyString6 ,StandbyString7 ,StandbyString8 ,StandbyString9 ,StandbyDouble4 ,StandbyDouble5 ,StandbyDouble6 ,StandbyDouble7 ,StandbyDouble8 ,StandbyDouble9 ,StandbyDate3 ,StandbyDate4 ,StandbyDate5) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBatchNo());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getEventNo());
			}
			if(this.getEventType() == null || this.getEventType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEventType());
			}
			if(this.getRecordType() == null || this.getRecordType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRecordType());
			}
			if(this.getNodeState() == null || this.getNodeState().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getNodeState());
			}
			if(this.getDataFlag() == null || this.getDataFlag().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDataFlag());
			}
			if(this.getUnionKey() == null || this.getUnionKey().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getUnionKey());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getGrpContNo());
			}
			if(this.getProposalGrpContNo() == null || this.getProposalGrpContNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getProposalGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getGrpPolNo());
			}
			if(this.getGrpProposalNo() == null || this.getGrpProposalNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getGrpProposalNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPolNo());
			}
			if(this.getProposalNo() == null || this.getProposalNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getProposalNo());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getContPlanCode());
			}
			if(this.getPlanType() == null || this.getPlanType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPlanType());
			}
			if(this.getRiskPeriod() == null || this.getRiskPeriod().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getRiskPeriod());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getRiskType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getDutyCode());
			}
			pstmt.setInt(22, this.getYears());
			pstmt.setInt(23, this.getInsuredYear());
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSaleChnl());
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getCValiDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getEndDate()));
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getInsuredNo());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getInsuredName());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getInsuredBirthday()));
			}
			pstmt.setInt(31, this.getInsuredAge());
			pstmt.setInt(32, this.getCurrentAge());
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getIDNo());
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOccupationType());
			}
			if(this.getHealthTime1() == null || this.getHealthTime1().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getHealthTime1());
			}
			if(this.getHealthTime2() == null || this.getHealthTime2().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getHealthTime2());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOccupationCode());
			}
			if(this.getSuppRiskScore() == null || this.getSuppRiskScore().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSuppRiskScore());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getSmokeFlag());
			}
			pstmt.setDouble(41, this.getAdditionalRate());
			if(this.getExtType() == null || this.getExtType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getExtType());
			}
			pstmt.setDouble(43, this.getExtPrem());
			pstmt.setDouble(44, this.getBegAccountValue());
			pstmt.setInt(45, this.getEndUnit());
			pstmt.setDouble(46, this.getEndPrice());
			pstmt.setDouble(47, this.getEndAccountValue());
			if(this.getFeeType() == null || this.getFeeType().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getFeeType());
			}
			pstmt.setDouble(49, this.getFeeMoney());
			if(this.getNonCashFlag() == null || this.getNonCashFlag().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getNonCashFlag());
			}
			pstmt.setDouble(51, this.getStandPrem());
			pstmt.setDouble(52, this.getPrem());
			pstmt.setDouble(53, this.getAmnt());
			pstmt.setDouble(54, this.getMult());
			pstmt.setDouble(55, this.getReserve());
			pstmt.setDouble(56, this.getAPE());
			pstmt.setDouble(57, this.getCSV());
			pstmt.setDouble(58, this.getRetention());
			pstmt.setDouble(59, this.getSuminsured());
			pstmt.setDouble(60, this.getFaceamount());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getCurrency());
			}
			pstmt.setDouble(62, this.getRiskAmnt());
			pstmt.setInt(63, this.getPayIntv());
			pstmt.setInt(64, this.getPayYears());
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getPayEndYearFlag());
			}
			pstmt.setInt(66, this.getPayEndYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getGetYearFlag());
			}
			pstmt.setInt(68, this.getGetYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getInsuYearFlag());
			}
			pstmt.setInt(70, this.getInsuYear());
			if(this.getAcciYearFlag() == null || this.getAcciYearFlag().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getAcciYearFlag());
			}
			pstmt.setInt(72, this.getAcciYear());
			if(this.getAcciEndDate() == null || this.getAcciEndDate().equals("null")) {
				pstmt.setNull(73, 91);
			} else {
				pstmt.setDate(73, Date.valueOf(this.getAcciEndDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getGetStartType() == null || this.getGetStartType().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getGetStartType());
			}
			pstmt.setDouble(76, this.getPeakLine());
			pstmt.setDouble(77, this.getGetLimit());
			pstmt.setInt(78, this.getGetIntv());
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getPayNo());
			}
			pstmt.setInt(80, this.getPayCount());
			pstmt.setDouble(81, this.getPayMoney());
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(82, 91);
			} else {
				pstmt.setDate(82, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(83, 91);
			} else {
				pstmt.setDate(83, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getEdorNo());
			}
			if(this.getFeeOperationType() == null || this.getFeeOperationType().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getFeeOperationType());
			}
			if(this.getFeeFinaType() == null || this.getFeeFinaType().equals("null")) {
				pstmt.setNull(86, 12);
			} else {
				pstmt.setString(86, this.getFeeFinaType());
			}
			pstmt.setDouble(87, this.getChangeRate());
			if(this.getAccCurrency() == null || this.getAccCurrency().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getAccCurrency());
			}
			pstmt.setDouble(89, this.getAccAmnt());
			pstmt.setDouble(90, this.getPreStandPrem());
			pstmt.setDouble(91, this.getPrePrem());
			pstmt.setDouble(92, this.getPreRiskAmnt());
			pstmt.setDouble(93, this.getPreAmnt());
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getClmNo());
			}
			if(this.getClmFeeOperationType() == null || this.getClmFeeOperationType().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getClmFeeOperationType());
			}
			if(this.getClmFeeFinaType() == null || this.getClmFeeFinaType().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getClmFeeFinaType());
			}
			pstmt.setDouble(97, this.getStandGetMoney());
			pstmt.setDouble(98, this.getGetRate());
			pstmt.setDouble(99, this.getClmGetMoney());
			if(this.getAccDate() == null || this.getAccDate().equals("null")) {
				pstmt.setNull(100, 91);
			} else {
				pstmt.setDate(100, Date.valueOf(this.getAccDate()));
			}
			if(this.getAccumulateDefNO() == null || this.getAccumulateDefNO().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getAccumulateDefNO());
			}
			if(this.getRIContNo() == null || this.getRIContNo().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getRIContNo());
			}
			if(this.getRIPreceptNo() == null || this.getRIPreceptNo().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getRIPreceptNo());
			}
			if(this.getReinsreFlag() == null || this.getReinsreFlag().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getReinsreFlag());
			}
			if(this.getGetDate() == null || this.getGetDate().equals("null")) {
				pstmt.setNull(105, 91);
			} else {
				pstmt.setDate(105, Date.valueOf(this.getGetDate()));
			}
			if(this.getStandbyString1() == null || this.getStandbyString1().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getStandbyString1());
			}
			if(this.getStandbyString2() == null || this.getStandbyString2().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getStandbyString2());
			}
			if(this.getStandbyString3() == null || this.getStandbyString3().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getStandbyString3());
			}
			pstmt.setDouble(109, this.getStandbyDouble1());
			pstmt.setDouble(110, this.getStandbyDouble2());
			pstmt.setDouble(111, this.getStandbyDouble3());
			if(this.getStandbyDate1() == null || this.getStandbyDate1().equals("null")) {
				pstmt.setNull(112, 91);
			} else {
				pstmt.setDate(112, Date.valueOf(this.getStandbyDate1()));
			}
			if(this.getStandbyDate2() == null || this.getStandbyDate2().equals("null")) {
				pstmt.setNull(113, 91);
			} else {
				pstmt.setDate(113, Date.valueOf(this.getStandbyDate2()));
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getManageCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(115, 91);
			} else {
				pstmt.setDate(115, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(116, 12);
			} else {
				pstmt.setString(116, this.getMakeTime());
			}
			pstmt.setDouble(117, this.getPreChRiskAmnt());
			pstmt.setDouble(118, this.getChRiskAmnt());
			pstmt.setDouble(119, this.getPreChangeRate());
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(120, 12);
			} else {
				pstmt.setString(120, this.getMainPolNo());
			}
			if(this.getRIPreceptType() == null || this.getRIPreceptType().equals("null")) {
				pstmt.setNull(121, 12);
			} else {
				pstmt.setString(121, this.getRIPreceptType());
			}
			if(this.getBFFlag() == null || this.getBFFlag().equals("null")) {
				pstmt.setNull(122, 12);
			} else {
				pstmt.setString(122, this.getBFFlag());
			}
			pstmt.setDouble(123, this.getEdorPrem());
			pstmt.setDouble(124, this.getChEdorPrem());
			pstmt.setDouble(125, this.getChPrem());
			pstmt.setDouble(126, this.getBonus1());
			pstmt.setDouble(127, this.getBonus2());
			pstmt.setDouble(128, this.getBonus3());
			pstmt.setDouble(129, this.getExtRate1());
			pstmt.setDouble(130, this.getExtRate2());
			pstmt.setDouble(131, this.getExtRate3());
			pstmt.setDouble(132, this.getExtPrem1());
			pstmt.setDouble(133, this.getExtPrem2());
			pstmt.setDouble(134, this.getExtPrem3());
			if(this.getStandbyString4() == null || this.getStandbyString4().equals("null")) {
				pstmt.setNull(135, 12);
			} else {
				pstmt.setString(135, this.getStandbyString4());
			}
			if(this.getStandbyString5() == null || this.getStandbyString5().equals("null")) {
				pstmt.setNull(136, 12);
			} else {
				pstmt.setString(136, this.getStandbyString5());
			}
			if(this.getStandbyString6() == null || this.getStandbyString6().equals("null")) {
				pstmt.setNull(137, 12);
			} else {
				pstmt.setString(137, this.getStandbyString6());
			}
			if(this.getStandbyString7() == null || this.getStandbyString7().equals("null")) {
				pstmt.setNull(138, 12);
			} else {
				pstmt.setString(138, this.getStandbyString7());
			}
			if(this.getStandbyString8() == null || this.getStandbyString8().equals("null")) {
				pstmt.setNull(139, 12);
			} else {
				pstmt.setString(139, this.getStandbyString8());
			}
			if(this.getStandbyString9() == null || this.getStandbyString9().equals("null")) {
				pstmt.setNull(140, 12);
			} else {
				pstmt.setString(140, this.getStandbyString9());
			}
			pstmt.setDouble(141, this.getStandbyDouble4());
			pstmt.setDouble(142, this.getStandbyDouble5());
			pstmt.setDouble(143, this.getStandbyDouble6());
			pstmt.setDouble(144, this.getStandbyDouble7());
			pstmt.setDouble(145, this.getStandbyDouble8());
			pstmt.setDouble(146, this.getStandbyDouble9());
			if(this.getStandbyDate3() == null || this.getStandbyDate3().equals("null")) {
				pstmt.setNull(147, 91);
			} else {
				pstmt.setDate(147, Date.valueOf(this.getStandbyDate3()));
			}
			if(this.getStandbyDate4() == null || this.getStandbyDate4().equals("null")) {
				pstmt.setNull(148, 91);
			} else {
				pstmt.setDate(148, Date.valueOf(this.getStandbyDate4()));
			}
			if(this.getStandbyDate5() == null || this.getStandbyDate5().equals("null")) {
				pstmt.setNull(149, 91);
			} else {
				pstmt.setDate(149, Date.valueOf(this.getStandbyDate5()));
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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
			pstmt = con.prepareStatement("SELECT * FROM RIPolRecordBakeB WHERE  BakeSerialNo = ? AND EventNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBakeSerialNo() == null || this.getBakeSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBakeSerialNo());
			}
			if(this.getEventNo() == null || this.getEventNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEventNo());
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
					tError.moduleName = "RIPolRecordBakeBDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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

	public RIPolRecordBakeBSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIPolRecordBakeBSet aRIPolRecordBakeBSet = new RIPolRecordBakeBSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIPolRecordBakeB");
			RIPolRecordBakeBSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				RIPolRecordBakeBSchema s1 = new RIPolRecordBakeBSchema();
				s1.setSchema(rs,i);
				aRIPolRecordBakeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
			tError.functionName = "query";
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

		return aRIPolRecordBakeBSet;
	}

	public RIPolRecordBakeBSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIPolRecordBakeBSet aRIPolRecordBakeBSet = new RIPolRecordBakeBSet();

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
				RIPolRecordBakeBSchema s1 = new RIPolRecordBakeBSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIPolRecordBakeBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIPolRecordBakeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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

		return aRIPolRecordBakeBSet;
	}

	public RIPolRecordBakeBSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIPolRecordBakeBSet aRIPolRecordBakeBSet = new RIPolRecordBakeBSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("RIPolRecordBakeB");
			RIPolRecordBakeBSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
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

				RIPolRecordBakeBSchema s1 = new RIPolRecordBakeBSchema();
				s1.setSchema(rs,i);
				aRIPolRecordBakeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
			tError.functionName = "query";
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

		return aRIPolRecordBakeBSet;
	}

	public RIPolRecordBakeBSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		RIPolRecordBakeBSet aRIPolRecordBakeBSet = new RIPolRecordBakeBSet();

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

				RIPolRecordBakeBSchema s1 = new RIPolRecordBakeBSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "RIPolRecordBakeBDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aRIPolRecordBakeBSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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

		return aRIPolRecordBakeBSet;
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
			SQLString sqlObj = new SQLString("RIPolRecordBakeB");
			RIPolRecordBakeBSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update RIPolRecordBakeB " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "RIPolRecordBakeBDB";
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
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIPolRecordBakeBDB";
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
        tError.moduleName = "RIPolRecordBakeBDB";
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
        e.printStackTrace();
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "RIPolRecordBakeBDB";
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
        tError.moduleName = "RIPolRecordBakeBDB";
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
        tError.moduleName = "RIPolRecordBakeBDB";
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
 * @return RIPolRecordBakeBSet
 */
public RIPolRecordBakeBSet getData()
{
    int tCount = 0;
    RIPolRecordBakeBSet tRIPolRecordBakeBSet = new RIPolRecordBakeBSet();
    RIPolRecordBakeBSchema tRIPolRecordBakeBSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "RIPolRecordBakeBDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tRIPolRecordBakeBSchema = new RIPolRecordBakeBSchema();
        tRIPolRecordBakeBSchema.setSchema(mResultSet, 1);
        tRIPolRecordBakeBSet.add(tRIPolRecordBakeBSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tRIPolRecordBakeBSchema = new RIPolRecordBakeBSchema();
                tRIPolRecordBakeBSchema.setSchema(mResultSet, 1);
                tRIPolRecordBakeBSet.add(tRIPolRecordBakeBSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "RIPolRecordBakeBDB";
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
    return tRIPolRecordBakeBSet;
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
            tError.moduleName = "RIPolRecordBakeBDB";
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
        tError.moduleName = "RIPolRecordBakeBDB";
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
            tError.moduleName = "RIPolRecordBakeBDB";
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
        tError.moduleName = "RIPolRecordBakeBDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}

