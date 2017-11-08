/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;

import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMRiskAppDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: LMRiskApp
 */
public class LMRiskAppDB extends LMRiskAppSchema
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
	public LMRiskAppDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LMRiskApp" );
		mflag = true;
	}

	public LMRiskAppDB()
	{
		con = null;
		db = new DBOper( "LMRiskApp" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LMRiskAppSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LMRiskAppSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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
			pstmt = con.prepareStatement("DELETE FROM LMRiskApp WHERE  RiskCode = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRiskApp");
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
		SQLString sqlObj = new SQLString("LMRiskApp");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LMRiskApp SET  RiskCode = ? , RiskVer = ? , RiskName = ? , KindCode = ? , RiskType = ? , RiskType1 = ? , RiskType2 = ? , RiskProp = ? , RiskPeriod = ? , RiskTypeDetail = ? , RiskFlag = ? , PolType = ? , InvestFlag = ? , BonusFlag = ? , BonusMode = ? , ListFlag = ? , SubRiskFlag = ? , CalDigital = ? , CalChoMode = ? , RiskAmntMult = ? , InsuPeriodFlag = ? , MaxEndPeriod = ? , AgeLmt = ? , SignDateCalMode = ? , ProtocolFlag = ? , GetChgFlag = ? , ProtocolPayFlag = ? , EnsuPlanFlag = ? , EnsuPlanAdjFlag = ? , StartDate = ? , EndDate = ? , MinAppntAge = ? , MaxAppntAge = ? , MaxInsuredAge = ? , MinInsuredAge = ? , AppInterest = ? , AppPremRate = ? , InsuredFlag = ? , ShareFlag = ? , BnfFlag = ? , TempPayFlag = ? , InpPayPlan = ? , ImpartFlag = ? , InsuExpeFlag = ? , LoanFlag = ? , MortagageFlag = ? , IDifReturnFlag = ? , CutAmntStopPay = ? , RinsRate = ? , SaleFlag = ? , FileAppFlag = ? , MngCom = ? , AutoPayFlag = ? , NeedPrintHospital = ? , NeedPrintGet = ? , RiskType3 = ? , RiskType4 = ? , RiskType5 = ? , NotPrintPol = ? , NeedGetPolDate = ? , NeedReReadBank = ? , SpecFlag = ? , InterestDifFlag = ? , RiskTypeAcc = ? , RiskPeriodAcc = ? , RiskType7 = ? , RiskType6 = ? , HealthType = ? , CancleForeGetSpecFlag = ? , RiskType8 = ? , SignDateCalMode2 = ? , SignDateCalMode3 = ? , RiskType9 = ? , AutoPayType = ? , AutoETIFlag = ? , AutoETIType = ? , AutoCTFlag = ? , NonParFlag = ? , BackDateFlag = ? , HoneymoonFlag = ? , NLGFlag = ? WHERE  RiskCode = ?");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskVer());
			}
			if(this.getRiskName() == null || this.getRiskName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskName());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getKindCode());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getRiskType());
			}
			if(this.getRiskType1() == null || this.getRiskType1().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getRiskType1());
			}
			if(this.getRiskType2() == null || this.getRiskType2().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getRiskType2());
			}
			if(this.getRiskProp() == null || this.getRiskProp().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getRiskProp());
			}
			if(this.getRiskPeriod() == null || this.getRiskPeriod().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getRiskPeriod());
			}
			if(this.getRiskTypeDetail() == null || this.getRiskTypeDetail().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRiskTypeDetail());
			}
			if(this.getRiskFlag() == null || this.getRiskFlag().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getRiskFlag());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPolType());
			}
			if(this.getInvestFlag() == null || this.getInvestFlag().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getInvestFlag());
			}
			if(this.getBonusFlag() == null || this.getBonusFlag().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getBonusFlag());
			}
			if(this.getBonusMode() == null || this.getBonusMode().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getBonusMode());
			}
			if(this.getListFlag() == null || this.getListFlag().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getListFlag());
			}
			if(this.getSubRiskFlag() == null || this.getSubRiskFlag().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getSubRiskFlag());
			}
			pstmt.setInt(18, this.getCalDigital());
			if(this.getCalChoMode() == null || this.getCalChoMode().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getCalChoMode());
			}
			pstmt.setInt(20, this.getRiskAmntMult());
			if(this.getInsuPeriodFlag() == null || this.getInsuPeriodFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getInsuPeriodFlag());
			}
			pstmt.setInt(22, this.getMaxEndPeriod());
			pstmt.setInt(23, this.getAgeLmt());
			pstmt.setInt(24, this.getSignDateCalMode());
			if(this.getProtocolFlag() == null || this.getProtocolFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getProtocolFlag());
			}
			if(this.getGetChgFlag() == null || this.getGetChgFlag().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getGetChgFlag());
			}
			if(this.getProtocolPayFlag() == null || this.getProtocolPayFlag().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getProtocolPayFlag());
			}
			if(this.getEnsuPlanFlag() == null || this.getEnsuPlanFlag().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getEnsuPlanFlag());
			}
			if(this.getEnsuPlanAdjFlag() == null || this.getEnsuPlanAdjFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getEnsuPlanAdjFlag());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getEndDate()));
			}
			pstmt.setInt(32, this.getMinAppntAge());
			pstmt.setInt(33, this.getMaxAppntAge());
			pstmt.setInt(34, this.getMaxInsuredAge());
			pstmt.setInt(35, this.getMinInsuredAge());
			pstmt.setDouble(36, this.getAppInterest());
			pstmt.setDouble(37, this.getAppPremRate());
			if(this.getInsuredFlag() == null || this.getInsuredFlag().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getInsuredFlag());
			}
			if(this.getShareFlag() == null || this.getShareFlag().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getShareFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getBnfFlag());
			}
			if(this.getTempPayFlag() == null || this.getTempPayFlag().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getTempPayFlag());
			}
			if(this.getInpPayPlan() == null || this.getInpPayPlan().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getInpPayPlan());
			}
			if(this.getImpartFlag() == null || this.getImpartFlag().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getImpartFlag());
			}
			if(this.getInsuExpeFlag() == null || this.getInsuExpeFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getInsuExpeFlag());
			}
			if(this.getLoanFlag() == null || this.getLoanFlag().equals("null")) {
				pstmt.setNull(45, 1);
			} else {
				pstmt.setString(45, this.getLoanFlag());
			}
			if(this.getMortagageFlag() == null || this.getMortagageFlag().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getMortagageFlag());
			}
			if(this.getIDifReturnFlag() == null || this.getIDifReturnFlag().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getIDifReturnFlag());
			}
			if(this.getCutAmntStopPay() == null || this.getCutAmntStopPay().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getCutAmntStopPay());
			}
			pstmt.setDouble(49, this.getRinsRate());
			if(this.getSaleFlag() == null || this.getSaleFlag().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getSaleFlag());
			}
			if(this.getFileAppFlag() == null || this.getFileAppFlag().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getFileAppFlag());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getMngCom());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getAutoPayFlag());
			}
			if(this.getNeedPrintHospital() == null || this.getNeedPrintHospital().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getNeedPrintHospital());
			}
			if(this.getNeedPrintGet() == null || this.getNeedPrintGet().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getNeedPrintGet());
			}
			if(this.getRiskType3() == null || this.getRiskType3().equals("null")) {
				pstmt.setNull(56, 1);
			} else {
				pstmt.setString(56, this.getRiskType3());
			}
			if(this.getRiskType4() == null || this.getRiskType4().equals("null")) {
				pstmt.setNull(57, 1);
			} else {
				pstmt.setString(57, this.getRiskType4());
			}
			if(this.getRiskType5() == null || this.getRiskType5().equals("null")) {
				pstmt.setNull(58, 1);
			} else {
				pstmt.setString(58, this.getRiskType5());
			}
			if(this.getNotPrintPol() == null || this.getNotPrintPol().equals("null")) {
				pstmt.setNull(59, 1);
			} else {
				pstmt.setString(59, this.getNotPrintPol());
			}
			if(this.getNeedGetPolDate() == null || this.getNeedGetPolDate().equals("null")) {
				pstmt.setNull(60, 1);
			} else {
				pstmt.setString(60, this.getNeedGetPolDate());
			}
			if(this.getNeedReReadBank() == null || this.getNeedReReadBank().equals("null")) {
				pstmt.setNull(61, 1);
			} else {
				pstmt.setString(61, this.getNeedReReadBank());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getSpecFlag());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getInterestDifFlag());
			}
			if(this.getRiskTypeAcc() == null || this.getRiskTypeAcc().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getRiskTypeAcc());
			}
			if(this.getRiskPeriodAcc() == null || this.getRiskPeriodAcc().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRiskPeriodAcc());
			}
			if(this.getRiskType7() == null || this.getRiskType7().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getRiskType7());
			}
			if(this.getRiskType6() == null || this.getRiskType6().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRiskType6());
			}
			if(this.getHealthType() == null || this.getHealthType().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getHealthType());
			}
			if(this.getCancleForeGetSpecFlag() == null || this.getCancleForeGetSpecFlag().equals("null")) {
				pstmt.setNull(69, 1);
			} else {
				pstmt.setString(69, this.getCancleForeGetSpecFlag());
			}
			if(this.getRiskType8() == null || this.getRiskType8().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getRiskType8());
			}
			pstmt.setInt(71, this.getSignDateCalMode2());
			pstmt.setInt(72, this.getSignDateCalMode3());
			if(this.getRiskType9() == null || this.getRiskType9().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getRiskType9());
			}
			if(this.getAutoPayType() == null || this.getAutoPayType().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getAutoPayType());
			}
			if(this.getAutoETIFlag() == null || this.getAutoETIFlag().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getAutoETIFlag());
			}
			if(this.getAutoETIType() == null || this.getAutoETIType().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getAutoETIType());
			}
			if(this.getAutoCTFlag() == null || this.getAutoCTFlag().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getAutoCTFlag());
			}
			if(this.getNonParFlag() == null || this.getNonParFlag().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getNonParFlag());
			}
			if(this.getBackDateFlag() == null || this.getBackDateFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getBackDateFlag());
			}
			if(this.getHoneymoonFlag() == null || this.getHoneymoonFlag().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getHoneymoonFlag());
			}
			if(this.getNLGFlag() == null || this.getNLGFlag().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getNLGFlag());
			}
			// set where condition
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(82, 12);
			} else {
				pstmt.setString(82, this.getRiskCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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
		SQLString sqlObj = new SQLString("LMRiskApp");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LMRiskApp(RiskCode ,RiskVer ,RiskName ,KindCode ,RiskType ,RiskType1 ,RiskType2 ,RiskProp ,RiskPeriod ,RiskTypeDetail ,RiskFlag ,PolType ,InvestFlag ,BonusFlag ,BonusMode ,ListFlag ,SubRiskFlag ,CalDigital ,CalChoMode ,RiskAmntMult ,InsuPeriodFlag ,MaxEndPeriod ,AgeLmt ,SignDateCalMode ,ProtocolFlag ,GetChgFlag ,ProtocolPayFlag ,EnsuPlanFlag ,EnsuPlanAdjFlag ,StartDate ,EndDate ,MinAppntAge ,MaxAppntAge ,MaxInsuredAge ,MinInsuredAge ,AppInterest ,AppPremRate ,InsuredFlag ,ShareFlag ,BnfFlag ,TempPayFlag ,InpPayPlan ,ImpartFlag ,InsuExpeFlag ,LoanFlag ,MortagageFlag ,IDifReturnFlag ,CutAmntStopPay ,RinsRate ,SaleFlag ,FileAppFlag ,MngCom ,AutoPayFlag ,NeedPrintHospital ,NeedPrintGet ,RiskType3 ,RiskType4 ,RiskType5 ,NotPrintPol ,NeedGetPolDate ,NeedReReadBank ,SpecFlag ,InterestDifFlag ,RiskTypeAcc ,RiskPeriodAcc ,RiskType7 ,RiskType6 ,HealthType ,CancleForeGetSpecFlag ,RiskType8 ,SignDateCalMode2 ,SignDateCalMode3 ,RiskType9 ,AutoPayType ,AutoETIFlag ,AutoETIType ,AutoCTFlag ,NonParFlag ,BackDateFlag ,HoneymoonFlag ,NLGFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRiskVer());
			}
			if(this.getRiskName() == null || this.getRiskName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskName());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getKindCode());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getRiskType());
			}
			if(this.getRiskType1() == null || this.getRiskType1().equals("null")) {
				pstmt.setNull(6, 1);
			} else {
				pstmt.setString(6, this.getRiskType1());
			}
			if(this.getRiskType2() == null || this.getRiskType2().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getRiskType2());
			}
			if(this.getRiskProp() == null || this.getRiskProp().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getRiskProp());
			}
			if(this.getRiskPeriod() == null || this.getRiskPeriod().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getRiskPeriod());
			}
			if(this.getRiskTypeDetail() == null || this.getRiskTypeDetail().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRiskTypeDetail());
			}
			if(this.getRiskFlag() == null || this.getRiskFlag().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getRiskFlag());
			}
			if(this.getPolType() == null || this.getPolType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPolType());
			}
			if(this.getInvestFlag() == null || this.getInvestFlag().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getInvestFlag());
			}
			if(this.getBonusFlag() == null || this.getBonusFlag().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getBonusFlag());
			}
			if(this.getBonusMode() == null || this.getBonusMode().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getBonusMode());
			}
			if(this.getListFlag() == null || this.getListFlag().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getListFlag());
			}
			if(this.getSubRiskFlag() == null || this.getSubRiskFlag().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getSubRiskFlag());
			}
			pstmt.setInt(18, this.getCalDigital());
			if(this.getCalChoMode() == null || this.getCalChoMode().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getCalChoMode());
			}
			pstmt.setInt(20, this.getRiskAmntMult());
			if(this.getInsuPeriodFlag() == null || this.getInsuPeriodFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getInsuPeriodFlag());
			}
			pstmt.setInt(22, this.getMaxEndPeriod());
			pstmt.setInt(23, this.getAgeLmt());
			pstmt.setInt(24, this.getSignDateCalMode());
			if(this.getProtocolFlag() == null || this.getProtocolFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getProtocolFlag());
			}
			if(this.getGetChgFlag() == null || this.getGetChgFlag().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getGetChgFlag());
			}
			if(this.getProtocolPayFlag() == null || this.getProtocolPayFlag().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getProtocolPayFlag());
			}
			if(this.getEnsuPlanFlag() == null || this.getEnsuPlanFlag().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getEnsuPlanFlag());
			}
			if(this.getEnsuPlanAdjFlag() == null || this.getEnsuPlanAdjFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getEnsuPlanAdjFlag());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getEndDate()));
			}
			pstmt.setInt(32, this.getMinAppntAge());
			pstmt.setInt(33, this.getMaxAppntAge());
			pstmt.setInt(34, this.getMaxInsuredAge());
			pstmt.setInt(35, this.getMinInsuredAge());
			pstmt.setDouble(36, this.getAppInterest());
			pstmt.setDouble(37, this.getAppPremRate());
			if(this.getInsuredFlag() == null || this.getInsuredFlag().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getInsuredFlag());
			}
			if(this.getShareFlag() == null || this.getShareFlag().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getShareFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getBnfFlag());
			}
			if(this.getTempPayFlag() == null || this.getTempPayFlag().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getTempPayFlag());
			}
			if(this.getInpPayPlan() == null || this.getInpPayPlan().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getInpPayPlan());
			}
			if(this.getImpartFlag() == null || this.getImpartFlag().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getImpartFlag());
			}
			if(this.getInsuExpeFlag() == null || this.getInsuExpeFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getInsuExpeFlag());
			}
			if(this.getLoanFlag() == null || this.getLoanFlag().equals("null")) {
				pstmt.setNull(45, 1);
			} else {
				pstmt.setString(45, this.getLoanFlag());
			}
			if(this.getMortagageFlag() == null || this.getMortagageFlag().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getMortagageFlag());
			}
			if(this.getIDifReturnFlag() == null || this.getIDifReturnFlag().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getIDifReturnFlag());
			}
			if(this.getCutAmntStopPay() == null || this.getCutAmntStopPay().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getCutAmntStopPay());
			}
			pstmt.setDouble(49, this.getRinsRate());
			if(this.getSaleFlag() == null || this.getSaleFlag().equals("null")) {
				pstmt.setNull(50, 1);
			} else {
				pstmt.setString(50, this.getSaleFlag());
			}
			if(this.getFileAppFlag() == null || this.getFileAppFlag().equals("null")) {
				pstmt.setNull(51, 1);
			} else {
				pstmt.setString(51, this.getFileAppFlag());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getMngCom());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getAutoPayFlag());
			}
			if(this.getNeedPrintHospital() == null || this.getNeedPrintHospital().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getNeedPrintHospital());
			}
			if(this.getNeedPrintGet() == null || this.getNeedPrintGet().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getNeedPrintGet());
			}
			if(this.getRiskType3() == null || this.getRiskType3().equals("null")) {
				pstmt.setNull(56, 1);
			} else {
				pstmt.setString(56, this.getRiskType3());
			}
			if(this.getRiskType4() == null || this.getRiskType4().equals("null")) {
				pstmt.setNull(57, 1);
			} else {
				pstmt.setString(57, this.getRiskType4());
			}
			if(this.getRiskType5() == null || this.getRiskType5().equals("null")) {
				pstmt.setNull(58, 1);
			} else {
				pstmt.setString(58, this.getRiskType5());
			}
			if(this.getNotPrintPol() == null || this.getNotPrintPol().equals("null")) {
				pstmt.setNull(59, 1);
			} else {
				pstmt.setString(59, this.getNotPrintPol());
			}
			if(this.getNeedGetPolDate() == null || this.getNeedGetPolDate().equals("null")) {
				pstmt.setNull(60, 1);
			} else {
				pstmt.setString(60, this.getNeedGetPolDate());
			}
			if(this.getNeedReReadBank() == null || this.getNeedReReadBank().equals("null")) {
				pstmt.setNull(61, 1);
			} else {
				pstmt.setString(61, this.getNeedReReadBank());
			}
			if(this.getSpecFlag() == null || this.getSpecFlag().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getSpecFlag());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getInterestDifFlag());
			}
			if(this.getRiskTypeAcc() == null || this.getRiskTypeAcc().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getRiskTypeAcc());
			}
			if(this.getRiskPeriodAcc() == null || this.getRiskPeriodAcc().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRiskPeriodAcc());
			}
			if(this.getRiskType7() == null || this.getRiskType7().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getRiskType7());
			}
			if(this.getRiskType6() == null || this.getRiskType6().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRiskType6());
			}
			if(this.getHealthType() == null || this.getHealthType().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getHealthType());
			}
			if(this.getCancleForeGetSpecFlag() == null || this.getCancleForeGetSpecFlag().equals("null")) {
				pstmt.setNull(69, 1);
			} else {
				pstmt.setString(69, this.getCancleForeGetSpecFlag());
			}
			if(this.getRiskType8() == null || this.getRiskType8().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getRiskType8());
			}
			pstmt.setInt(71, this.getSignDateCalMode2());
			pstmt.setInt(72, this.getSignDateCalMode3());
			if(this.getRiskType9() == null || this.getRiskType9().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getRiskType9());
			}
			if(this.getAutoPayType() == null || this.getAutoPayType().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getAutoPayType());
			}
			if(this.getAutoETIFlag() == null || this.getAutoETIFlag().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getAutoETIFlag());
			}
			if(this.getAutoETIType() == null || this.getAutoETIType().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getAutoETIType());
			}
			if(this.getAutoCTFlag() == null || this.getAutoCTFlag().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getAutoCTFlag());
			}
			if(this.getNonParFlag() == null || this.getNonParFlag().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getNonParFlag());
			}
			if(this.getBackDateFlag() == null || this.getBackDateFlag().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getBackDateFlag());
			}
			if(this.getHoneymoonFlag() == null || this.getHoneymoonFlag().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getHoneymoonFlag());
			}
			if(this.getNLGFlag() == null || this.getNLGFlag().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getNLGFlag());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LMRiskApp WHERE  RiskCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRiskCode());
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
					tError.moduleName = "LMRiskAppDB";
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
			tError.moduleName = "LMRiskAppDB";
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

	public LMRiskAppSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMRiskAppSet aLMRiskAppSet = new LMRiskAppSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LMRiskApp");
			LMRiskAppSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				LMRiskAppSchema s1 = new LMRiskAppSchema();
				s1.setSchema(rs,i);
				aLMRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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

		return aLMRiskAppSet;
	}

	public LMRiskAppSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LMRiskAppSet aLMRiskAppSet = new LMRiskAppSet();

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
				LMRiskAppSchema s1 = new LMRiskAppSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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

		return aLMRiskAppSet;
	}
	
	public LMRiskAppSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMRiskAppSet aLMRiskAppSet = new LMRiskAppSet();

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
				LMRiskAppSchema s1 = new LMRiskAppSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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

		return aLMRiskAppSet;
	}

	public LMRiskAppSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMRiskAppSet aLMRiskAppSet = new LMRiskAppSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LMRiskApp");
			LMRiskAppSchema aSchema = this.getSchema();
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

				LMRiskAppSchema s1 = new LMRiskAppSchema();
				s1.setSchema(rs,i);
				aLMRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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

		return aLMRiskAppSet;
	}

	public LMRiskAppSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LMRiskAppSet aLMRiskAppSet = new LMRiskAppSet();

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

				LMRiskAppSchema s1 = new LMRiskAppSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LMRiskAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLMRiskAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			e.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskAppDB";
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

		return aLMRiskAppSet;
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
			SQLString sqlObj = new SQLString("LMRiskApp");
			LMRiskAppSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LMRiskApp " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LMRiskAppDB";
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
			tError.moduleName = "LMRiskAppDB";
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
        tError.moduleName = "LMRiskAppDB";
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
        tError.moduleName = "LMRiskAppDB";
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
        tError.moduleName = "LMRiskAppDB";
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
        tError.moduleName = "LMRiskAppDB";
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
 * @return LMRiskAppSet
 */
public LMRiskAppSet getData()
{
    int tCount = 0;
    LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
    LMRiskAppSchema tLMRiskAppSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LMRiskAppDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLMRiskAppSchema = new LMRiskAppSchema();
        tLMRiskAppSchema.setSchema(mResultSet, 1);
        tLMRiskAppSet.add(tLMRiskAppSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLMRiskAppSchema = new LMRiskAppSchema();
                tLMRiskAppSchema.setSchema(mResultSet, 1);
                tLMRiskAppSet.add(tLMRiskAppSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LMRiskAppDB";
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
    return tLMRiskAppSet;
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
            tError.moduleName = "LMRiskAppDB";
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
        tError.moduleName = "LMRiskAppDB";
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
            tError.moduleName = "LMRiskAppDB";
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
        tError.moduleName = "LMRiskAppDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
