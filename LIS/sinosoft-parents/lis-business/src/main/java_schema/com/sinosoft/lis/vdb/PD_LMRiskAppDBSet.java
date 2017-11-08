/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMRiskAppSchema;
import com.sinosoft.lis.vschema.PD_LMRiskAppSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMRiskAppDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PD_LMRiskApp
 */
public class PD_LMRiskAppDBSet extends PD_LMRiskAppSet
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
	public PD_LMRiskAppDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_LMRiskApp");
		mflag = true;
	}

	public PD_LMRiskAppDBSet()
	{
		db = new DBOper( "PD_LMRiskApp" );
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
			tError.moduleName = "PD_LMRiskAppDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMRiskApp WHERE  RiskCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMRiskApp");
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
			tError.moduleName = "PD_LMRiskAppDBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_LMRiskApp SET  RiskCode = ? , RiskVer = ? , RiskName = ? , KindCode = ? , RiskType = ? , RiskType1 = ? , RiskType2 = ? , RiskProp = ? , RiskPeriod = ? , RiskTypeDetail = ? , RiskFlag = ? , PolType = ? , InvestFlag = ? , BonusFlag = ? , BonusMode = ? , ListFlag = ? , SubRiskFlag = ? , CalDigital = ? , CalChoMode = ? , RiskAmntMult = ? , InsuPeriodFlag = ? , MaxEndPeriod = ? , AgeLmt = ? , SignDateCalMode = ? , ProtocolFlag = ? , GetChgFlag = ? , ProtocolPayFlag = ? , EnsuPlanFlag = ? , EnsuPlanAdjFlag = ? , StartDate = ? , EndDate = ? , MinAppntAge = ? , MaxAppntAge = ? , MaxInsuredAge = ? , MinInsuredAge = ? , AppInterest = ? , AppPremRate = ? , InsuredFlag = ? , ShareFlag = ? , BnfFlag = ? , TempPayFlag = ? , InpPayPlan = ? , ImpartFlag = ? , InsuExpeFlag = ? , LoanFlag = ? , MortagageFlag = ? , IDifReturnFlag = ? , CutAmntStopPay = ? , RinsRate = ? , SaleFlag = ? , FileAppFlag = ? , MngCom = ? , AutoPayFlag = ? , NeedPrintHospital = ? , NeedPrintGet = ? , RiskType3 = ? , RiskType4 = ? , RiskType5 = ? , NotPrintPol = ? , NeedGetPolDate = ? , NeedReReadBank = ? , SpecFlag = ? , InterestDifFlag = ? , RiskType6 = ? , RiskType7 = ? , RiskType8 = ? , RiskType9 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , CancleForeGetSpecFlag = ? , SignDateCalMode2 = ? , SignDateCalMode3 = ? , HealthType = ? , RiskTypeAcc = ? , RiskPeriodAcc = ? , AutoPayType = ? , AutoETIFlag = ? , AutoETIType = ? , AutoCTFlag = ? , NonParFlag = ? , BackDateFlag = ? , HoneymoonFlag = ? , NLGFlag = ? WHERE  RiskCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskVer());
			}
			if(this.get(i).getRiskName() == null || this.get(i).getRiskName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskName());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskType());
			}
			if(this.get(i).getRiskType1() == null || this.get(i).getRiskType1().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskType1());
			}
			if(this.get(i).getRiskType2() == null || this.get(i).getRiskType2().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskType2());
			}
			if(this.get(i).getRiskProp() == null || this.get(i).getRiskProp().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRiskProp());
			}
			if(this.get(i).getRiskPeriod() == null || this.get(i).getRiskPeriod().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskPeriod());
			}
			if(this.get(i).getRiskTypeDetail() == null || this.get(i).getRiskTypeDetail().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskTypeDetail());
			}
			if(this.get(i).getRiskFlag() == null || this.get(i).getRiskFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRiskFlag());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPolType());
			}
			if(this.get(i).getInvestFlag() == null || this.get(i).getInvestFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInvestFlag());
			}
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBonusFlag());
			}
			if(this.get(i).getBonusMode() == null || this.get(i).getBonusMode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBonusMode());
			}
			if(this.get(i).getListFlag() == null || this.get(i).getListFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getListFlag());
			}
			if(this.get(i).getSubRiskFlag() == null || this.get(i).getSubRiskFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSubRiskFlag());
			}
			pstmt.setInt(18, this.get(i).getCalDigital());
			if(this.get(i).getCalChoMode() == null || this.get(i).getCalChoMode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCalChoMode());
			}
			pstmt.setInt(20, this.get(i).getRiskAmntMult());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getInsuPeriodFlag());
			}
			pstmt.setInt(22, this.get(i).getMaxEndPeriod());
			pstmt.setInt(23, this.get(i).getAgeLmt());
			pstmt.setInt(24, this.get(i).getSignDateCalMode());
			if(this.get(i).getProtocolFlag() == null || this.get(i).getProtocolFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getProtocolFlag());
			}
			if(this.get(i).getGetChgFlag() == null || this.get(i).getGetChgFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getGetChgFlag());
			}
			if(this.get(i).getProtocolPayFlag() == null || this.get(i).getProtocolPayFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getProtocolPayFlag());
			}
			if(this.get(i).getEnsuPlanFlag() == null || this.get(i).getEnsuPlanFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getEnsuPlanFlag());
			}
			if(this.get(i).getEnsuPlanAdjFlag() == null || this.get(i).getEnsuPlanAdjFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getEnsuPlanAdjFlag());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getEndDate()));
			}
			pstmt.setInt(32, this.get(i).getMinAppntAge());
			pstmt.setInt(33, this.get(i).getMaxAppntAge());
			pstmt.setInt(34, this.get(i).getMaxInsuredAge());
			pstmt.setInt(35, this.get(i).getMinInsuredAge());
			pstmt.setDouble(36, this.get(i).getAppInterest());
			pstmt.setDouble(37, this.get(i).getAppPremRate());
			if(this.get(i).getInsuredFlag() == null || this.get(i).getInsuredFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getInsuredFlag());
			}
			if(this.get(i).getShareFlag() == null || this.get(i).getShareFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getShareFlag());
			}
			if(this.get(i).getBnfFlag() == null || this.get(i).getBnfFlag().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getBnfFlag());
			}
			if(this.get(i).getTempPayFlag() == null || this.get(i).getTempPayFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getTempPayFlag());
			}
			if(this.get(i).getInpPayPlan() == null || this.get(i).getInpPayPlan().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getInpPayPlan());
			}
			if(this.get(i).getImpartFlag() == null || this.get(i).getImpartFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getImpartFlag());
			}
			if(this.get(i).getInsuExpeFlag() == null || this.get(i).getInsuExpeFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getInsuExpeFlag());
			}
			if(this.get(i).getLoanFlag() == null || this.get(i).getLoanFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getLoanFlag());
			}
			if(this.get(i).getMortagageFlag() == null || this.get(i).getMortagageFlag().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMortagageFlag());
			}
			if(this.get(i).getIDifReturnFlag() == null || this.get(i).getIDifReturnFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getIDifReturnFlag());
			}
			if(this.get(i).getCutAmntStopPay() == null || this.get(i).getCutAmntStopPay().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getCutAmntStopPay());
			}
			pstmt.setDouble(49, this.get(i).getRinsRate());
			if(this.get(i).getSaleFlag() == null || this.get(i).getSaleFlag().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSaleFlag());
			}
			if(this.get(i).getFileAppFlag() == null || this.get(i).getFileAppFlag().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getFileAppFlag());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getMngCom());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getAutoPayFlag());
			}
			if(this.get(i).getNeedPrintHospital() == null || this.get(i).getNeedPrintHospital().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getNeedPrintHospital());
			}
			if(this.get(i).getNeedPrintGet() == null || this.get(i).getNeedPrintGet().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getNeedPrintGet());
			}
			if(this.get(i).getRiskType3() == null || this.get(i).getRiskType3().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getRiskType3());
			}
			if(this.get(i).getRiskType4() == null || this.get(i).getRiskType4().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getRiskType4());
			}
			if(this.get(i).getRiskType5() == null || this.get(i).getRiskType5().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getRiskType5());
			}
			if(this.get(i).getNotPrintPol() == null || this.get(i).getNotPrintPol().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getNotPrintPol());
			}
			if(this.get(i).getNeedGetPolDate() == null || this.get(i).getNeedGetPolDate().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getNeedGetPolDate());
			}
			if(this.get(i).getNeedReReadBank() == null || this.get(i).getNeedReReadBank().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getNeedReReadBank());
			}
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getSpecFlag());
			}
			if(this.get(i).getInterestDifFlag() == null || this.get(i).getInterestDifFlag().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getInterestDifFlag());
			}
			if(this.get(i).getRiskType6() == null || this.get(i).getRiskType6().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getRiskType6());
			}
			if(this.get(i).getRiskType7() == null || this.get(i).getRiskType7().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getRiskType7());
			}
			if(this.get(i).getRiskType8() == null || this.get(i).getRiskType8().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRiskType8());
			}
			if(this.get(i).getRiskType9() == null || this.get(i).getRiskType9().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRiskType9());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(69,null);
			} else {
				pstmt.setDate(69, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(71,null);
			} else {
				pstmt.setDate(71, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(75, this.get(i).getStandbyflag3());
			pstmt.setInt(76, this.get(i).getStandbyflag4());
			pstmt.setDouble(77, this.get(i).getStandbyflag5());
			pstmt.setDouble(78, this.get(i).getStandbyflag6());
			if(this.get(i).getCancleForeGetSpecFlag() == null || this.get(i).getCancleForeGetSpecFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getCancleForeGetSpecFlag());
			}
			pstmt.setInt(80, this.get(i).getSignDateCalMode2());
			pstmt.setInt(81, this.get(i).getSignDateCalMode3());
			if(this.get(i).getHealthType() == null || this.get(i).getHealthType().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getHealthType());
			}
			if(this.get(i).getRiskTypeAcc() == null || this.get(i).getRiskTypeAcc().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getRiskTypeAcc());
			}
			if(this.get(i).getRiskPeriodAcc() == null || this.get(i).getRiskPeriodAcc().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getRiskPeriodAcc());
			}
			if(this.get(i).getAutoPayType() == null || this.get(i).getAutoPayType().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getAutoPayType());
			}
			if(this.get(i).getAutoETIFlag() == null || this.get(i).getAutoETIFlag().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getAutoETIFlag());
			}
			if(this.get(i).getAutoETIType() == null || this.get(i).getAutoETIType().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getAutoETIType());
			}
			if(this.get(i).getAutoCTFlag() == null || this.get(i).getAutoCTFlag().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getAutoCTFlag());
			}
			if(this.get(i).getNonParFlag() == null || this.get(i).getNonParFlag().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getNonParFlag());
			}
			if(this.get(i).getBackDateFlag() == null || this.get(i).getBackDateFlag().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getBackDateFlag());
			}
			if(this.get(i).getHoneymoonFlag() == null || this.get(i).getHoneymoonFlag().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getHoneymoonFlag());
			}
			if(this.get(i).getNLGFlag() == null || this.get(i).getNLGFlag().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getNLGFlag());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getRiskCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMRiskApp");
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
			tError.moduleName = "PD_LMRiskAppDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_LMRiskApp(RiskCode ,RiskVer ,RiskName ,KindCode ,RiskType ,RiskType1 ,RiskType2 ,RiskProp ,RiskPeriod ,RiskTypeDetail ,RiskFlag ,PolType ,InvestFlag ,BonusFlag ,BonusMode ,ListFlag ,SubRiskFlag ,CalDigital ,CalChoMode ,RiskAmntMult ,InsuPeriodFlag ,MaxEndPeriod ,AgeLmt ,SignDateCalMode ,ProtocolFlag ,GetChgFlag ,ProtocolPayFlag ,EnsuPlanFlag ,EnsuPlanAdjFlag ,StartDate ,EndDate ,MinAppntAge ,MaxAppntAge ,MaxInsuredAge ,MinInsuredAge ,AppInterest ,AppPremRate ,InsuredFlag ,ShareFlag ,BnfFlag ,TempPayFlag ,InpPayPlan ,ImpartFlag ,InsuExpeFlag ,LoanFlag ,MortagageFlag ,IDifReturnFlag ,CutAmntStopPay ,RinsRate ,SaleFlag ,FileAppFlag ,MngCom ,AutoPayFlag ,NeedPrintHospital ,NeedPrintGet ,RiskType3 ,RiskType4 ,RiskType5 ,NotPrintPol ,NeedGetPolDate ,NeedReReadBank ,SpecFlag ,InterestDifFlag ,RiskType6 ,RiskType7 ,RiskType8 ,RiskType9 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6 ,CancleForeGetSpecFlag ,SignDateCalMode2 ,SignDateCalMode3 ,HealthType ,RiskTypeAcc ,RiskPeriodAcc ,AutoPayType ,AutoETIFlag ,AutoETIType ,AutoCTFlag ,NonParFlag ,BackDateFlag ,HoneymoonFlag ,NLGFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVer() == null || this.get(i).getRiskVer().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRiskVer());
			}
			if(this.get(i).getRiskName() == null || this.get(i).getRiskName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskName());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskType() == null || this.get(i).getRiskType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskType());
			}
			if(this.get(i).getRiskType1() == null || this.get(i).getRiskType1().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskType1());
			}
			if(this.get(i).getRiskType2() == null || this.get(i).getRiskType2().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskType2());
			}
			if(this.get(i).getRiskProp() == null || this.get(i).getRiskProp().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRiskProp());
			}
			if(this.get(i).getRiskPeriod() == null || this.get(i).getRiskPeriod().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskPeriod());
			}
			if(this.get(i).getRiskTypeDetail() == null || this.get(i).getRiskTypeDetail().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskTypeDetail());
			}
			if(this.get(i).getRiskFlag() == null || this.get(i).getRiskFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRiskFlag());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPolType());
			}
			if(this.get(i).getInvestFlag() == null || this.get(i).getInvestFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInvestFlag());
			}
			if(this.get(i).getBonusFlag() == null || this.get(i).getBonusFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBonusFlag());
			}
			if(this.get(i).getBonusMode() == null || this.get(i).getBonusMode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBonusMode());
			}
			if(this.get(i).getListFlag() == null || this.get(i).getListFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getListFlag());
			}
			if(this.get(i).getSubRiskFlag() == null || this.get(i).getSubRiskFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSubRiskFlag());
			}
			pstmt.setInt(18, this.get(i).getCalDigital());
			if(this.get(i).getCalChoMode() == null || this.get(i).getCalChoMode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCalChoMode());
			}
			pstmt.setInt(20, this.get(i).getRiskAmntMult());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getInsuPeriodFlag());
			}
			pstmt.setInt(22, this.get(i).getMaxEndPeriod());
			pstmt.setInt(23, this.get(i).getAgeLmt());
			pstmt.setInt(24, this.get(i).getSignDateCalMode());
			if(this.get(i).getProtocolFlag() == null || this.get(i).getProtocolFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getProtocolFlag());
			}
			if(this.get(i).getGetChgFlag() == null || this.get(i).getGetChgFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getGetChgFlag());
			}
			if(this.get(i).getProtocolPayFlag() == null || this.get(i).getProtocolPayFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getProtocolPayFlag());
			}
			if(this.get(i).getEnsuPlanFlag() == null || this.get(i).getEnsuPlanFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getEnsuPlanFlag());
			}
			if(this.get(i).getEnsuPlanAdjFlag() == null || this.get(i).getEnsuPlanAdjFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getEnsuPlanAdjFlag());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getEndDate()));
			}
			pstmt.setInt(32, this.get(i).getMinAppntAge());
			pstmt.setInt(33, this.get(i).getMaxAppntAge());
			pstmt.setInt(34, this.get(i).getMaxInsuredAge());
			pstmt.setInt(35, this.get(i).getMinInsuredAge());
			pstmt.setDouble(36, this.get(i).getAppInterest());
			pstmt.setDouble(37, this.get(i).getAppPremRate());
			if(this.get(i).getInsuredFlag() == null || this.get(i).getInsuredFlag().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getInsuredFlag());
			}
			if(this.get(i).getShareFlag() == null || this.get(i).getShareFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getShareFlag());
			}
			if(this.get(i).getBnfFlag() == null || this.get(i).getBnfFlag().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getBnfFlag());
			}
			if(this.get(i).getTempPayFlag() == null || this.get(i).getTempPayFlag().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getTempPayFlag());
			}
			if(this.get(i).getInpPayPlan() == null || this.get(i).getInpPayPlan().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getInpPayPlan());
			}
			if(this.get(i).getImpartFlag() == null || this.get(i).getImpartFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getImpartFlag());
			}
			if(this.get(i).getInsuExpeFlag() == null || this.get(i).getInsuExpeFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getInsuExpeFlag());
			}
			if(this.get(i).getLoanFlag() == null || this.get(i).getLoanFlag().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getLoanFlag());
			}
			if(this.get(i).getMortagageFlag() == null || this.get(i).getMortagageFlag().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMortagageFlag());
			}
			if(this.get(i).getIDifReturnFlag() == null || this.get(i).getIDifReturnFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getIDifReturnFlag());
			}
			if(this.get(i).getCutAmntStopPay() == null || this.get(i).getCutAmntStopPay().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getCutAmntStopPay());
			}
			pstmt.setDouble(49, this.get(i).getRinsRate());
			if(this.get(i).getSaleFlag() == null || this.get(i).getSaleFlag().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSaleFlag());
			}
			if(this.get(i).getFileAppFlag() == null || this.get(i).getFileAppFlag().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getFileAppFlag());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getMngCom());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getAutoPayFlag());
			}
			if(this.get(i).getNeedPrintHospital() == null || this.get(i).getNeedPrintHospital().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getNeedPrintHospital());
			}
			if(this.get(i).getNeedPrintGet() == null || this.get(i).getNeedPrintGet().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getNeedPrintGet());
			}
			if(this.get(i).getRiskType3() == null || this.get(i).getRiskType3().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getRiskType3());
			}
			if(this.get(i).getRiskType4() == null || this.get(i).getRiskType4().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getRiskType4());
			}
			if(this.get(i).getRiskType5() == null || this.get(i).getRiskType5().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getRiskType5());
			}
			if(this.get(i).getNotPrintPol() == null || this.get(i).getNotPrintPol().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getNotPrintPol());
			}
			if(this.get(i).getNeedGetPolDate() == null || this.get(i).getNeedGetPolDate().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getNeedGetPolDate());
			}
			if(this.get(i).getNeedReReadBank() == null || this.get(i).getNeedReReadBank().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getNeedReReadBank());
			}
			if(this.get(i).getSpecFlag() == null || this.get(i).getSpecFlag().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getSpecFlag());
			}
			if(this.get(i).getInterestDifFlag() == null || this.get(i).getInterestDifFlag().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getInterestDifFlag());
			}
			if(this.get(i).getRiskType6() == null || this.get(i).getRiskType6().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getRiskType6());
			}
			if(this.get(i).getRiskType7() == null || this.get(i).getRiskType7().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getRiskType7());
			}
			if(this.get(i).getRiskType8() == null || this.get(i).getRiskType8().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRiskType8());
			}
			if(this.get(i).getRiskType9() == null || this.get(i).getRiskType9().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRiskType9());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(69,null);
			} else {
				pstmt.setDate(69, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(71,null);
			} else {
				pstmt.setDate(71, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(75, this.get(i).getStandbyflag3());
			pstmt.setInt(76, this.get(i).getStandbyflag4());
			pstmt.setDouble(77, this.get(i).getStandbyflag5());
			pstmt.setDouble(78, this.get(i).getStandbyflag6());
			if(this.get(i).getCancleForeGetSpecFlag() == null || this.get(i).getCancleForeGetSpecFlag().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getCancleForeGetSpecFlag());
			}
			pstmt.setInt(80, this.get(i).getSignDateCalMode2());
			pstmt.setInt(81, this.get(i).getSignDateCalMode3());
			if(this.get(i).getHealthType() == null || this.get(i).getHealthType().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getHealthType());
			}
			if(this.get(i).getRiskTypeAcc() == null || this.get(i).getRiskTypeAcc().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getRiskTypeAcc());
			}
			if(this.get(i).getRiskPeriodAcc() == null || this.get(i).getRiskPeriodAcc().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getRiskPeriodAcc());
			}
			if(this.get(i).getAutoPayType() == null || this.get(i).getAutoPayType().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getAutoPayType());
			}
			if(this.get(i).getAutoETIFlag() == null || this.get(i).getAutoETIFlag().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getAutoETIFlag());
			}
			if(this.get(i).getAutoETIType() == null || this.get(i).getAutoETIType().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getAutoETIType());
			}
			if(this.get(i).getAutoCTFlag() == null || this.get(i).getAutoCTFlag().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getAutoCTFlag());
			}
			if(this.get(i).getNonParFlag() == null || this.get(i).getNonParFlag().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getNonParFlag());
			}
			if(this.get(i).getBackDateFlag() == null || this.get(i).getBackDateFlag().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getBackDateFlag());
			}
			if(this.get(i).getHoneymoonFlag() == null || this.get(i).getHoneymoonFlag().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getHoneymoonFlag());
			}
			if(this.get(i).getNLGFlag() == null || this.get(i).getNLGFlag().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getNLGFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMRiskApp");
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
			tError.moduleName = "PD_LMRiskAppDBSet";
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
