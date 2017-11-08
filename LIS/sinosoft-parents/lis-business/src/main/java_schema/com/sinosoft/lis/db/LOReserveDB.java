/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LOReserveSchema;
import com.sinosoft.lis.vschema.LOReserveSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOReserveDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOReserveDB extends LOReserveSchema
{
private static Logger logger = Logger.getLogger(LOReserveDB.class);

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
	public LOReserveDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LOReserve" );
		mflag = true;
	}

	public LOReserveDB()
	{
		con = null;
		db = new DBOper( "LOReserve" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LOReserveSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LOReserveSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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
			pstmt = con.prepareStatement("DELETE FROM LOReserve WHERE  PolNo = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOReserve");
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
		SQLString sqlObj = new SQLString("LOReserve");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LOReserve SET  ManageCom = ? , ContNo = ? , PolNo = ? , PolStatus = ? , RiskCode = ? , SaleChnl = ? , InsuredNo = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAppAge = ? , SecInsuredSex = ? , SecBirthDay = ? , SecInsuredAge = ? , CValiDate = ? , LastRevDate = ? , MakeDate = ? , Years = ? , PayYears = ? , PayIntv = ? , StandPrem = ? , Amnt = ? , InvalidDate = ? , FreeStartDate = ? , DeadDate = ? , ShouldGetMoney = ? , StopReason = ? , StopDate = ? , RevGetAge = ? , RevGetDate = ? , RevGetMoney = ? , RevGetIntv = ? , AnnMoney = ? , DeathMoney = ? , MedMoney = ? , ExpMoney = ? , TotalMoney = ? , OccPrem = ? , HealthPrem = ? , LastPayPrem = ? , YearIdx = ? , CalYear = ? , Rev3 = ? , Rev1 = ? , Rev2 = ? , Rev4 = ? , CheckFlag = ? , CalFlag = ? , ModifyDate = ? , SignDate = ? , ThisPayPrem = ? , PayToDate = ? , ManageFeeRate = ? , InsuYearFlag = ? , PayEndYearFlag = ? , SurMoney = ? , SmokingFlag = ? , OccupationType = ? , CustomGetPolDate = ? , FloatRate = ? , GroupNo = ? , GetMoney = ? , GetDutyKind = ? , AgentCom = ? , ACType = ? , EndDate = ? , FreeFlag = ? , AutoPayFlag = ? , Av_last_cal = ? , Acval_cal_date = ? , Av_BOY = ? , Av_BOM = ? , TBMoney = ? , TBMDate = ? , TransSaleChnl = ? , Cuml_PartSurrender = ? , BonusAmnt = ? , OtherPrem = ? WHERE  PolNo = ?");
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getManageCom());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getPolStatus() == null || this.getPolStatus().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolStatus());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRiskCode());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getSaleChnl());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getInsuredNo());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getInsuredBirthday()));
			}
			pstmt.setInt(10, this.getInsuredAppAge());
			if(this.getSecInsuredSex() == null || this.getSecInsuredSex().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getSecInsuredSex());
			}
			if(this.getSecBirthDay() == null || this.getSecBirthDay().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getSecBirthDay()));
			}
			pstmt.setInt(13, this.getSecInsuredAge());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getCValiDate()));
			}
			if(this.getLastRevDate() == null || this.getLastRevDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getLastRevDate()));
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getMakeDate()));
			}
			pstmt.setInt(17, this.getYears());
			pstmt.setInt(18, this.getPayYears());
			pstmt.setInt(19, this.getPayIntv());
			pstmt.setDouble(20, this.getStandPrem());
			pstmt.setDouble(21, this.getAmnt());
			if(this.getInvalidDate() == null || this.getInvalidDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getInvalidDate()));
			}
			if(this.getFreeStartDate() == null || this.getFreeStartDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getFreeStartDate()));
			}
			if(this.getDeadDate() == null || this.getDeadDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getDeadDate()));
			}
			pstmt.setDouble(25, this.getShouldGetMoney());
			if(this.getStopReason() == null || this.getStopReason().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStopReason());
			}
			if(this.getStopDate() == null || this.getStopDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getStopDate()));
			}
			pstmt.setInt(28, this.getRevGetAge());
			if(this.getRevGetDate() == null || this.getRevGetDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getRevGetDate()));
			}
			pstmt.setDouble(30, this.getRevGetMoney());
			pstmt.setInt(31, this.getRevGetIntv());
			pstmt.setDouble(32, this.getAnnMoney());
			pstmt.setDouble(33, this.getDeathMoney());
			pstmt.setDouble(34, this.getMedMoney());
			pstmt.setDouble(35, this.getExpMoney());
			pstmt.setDouble(36, this.getTotalMoney());
			pstmt.setDouble(37, this.getOccPrem());
			pstmt.setDouble(38, this.getHealthPrem());
			pstmt.setDouble(39, this.getLastPayPrem());
			pstmt.setInt(40, this.getYearIdx());
			pstmt.setInt(41, this.getCalYear());
			pstmt.setDouble(42, this.getRev3());
			pstmt.setDouble(43, this.getRev1());
			pstmt.setDouble(44, this.getRev2());
			pstmt.setDouble(45, this.getRev4());
			pstmt.setInt(46, this.getCheckFlag());
			pstmt.setInt(47, this.getCalFlag());
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getModifyDate()));
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(49, 91);
			} else {
				pstmt.setDate(49, Date.valueOf(this.getSignDate()));
			}
			pstmt.setDouble(50, this.getThisPayPrem());
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getPayToDate()));
			}
			pstmt.setDouble(52, this.getManageFeeRate());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getInsuYearFlag());
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getPayEndYearFlag());
			}
			pstmt.setDouble(55, this.getSurMoney());
			if(this.getSmokingFlag() == null || this.getSmokingFlag().equals("null")) {
				pstmt.setNull(56, 1);
			} else {
				pstmt.setString(56, this.getSmokingFlag());
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(57, 1);
			} else {
				pstmt.setString(57, this.getOccupationType());
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCustomGetPolDate()));
			}
			pstmt.setDouble(59, this.getFloatRate());
			if(this.getGroupNo() == null || this.getGroupNo().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getGroupNo());
			}
			pstmt.setDouble(61, this.getGetMoney());
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getGetDutyKind());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getAgentCom());
			}
			if(this.getACType() == null || this.getACType().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getACType());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getEndDate()));
			}
			if(this.getFreeFlag() == null || this.getFreeFlag().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getFreeFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getAutoPayFlag());
			}
			pstmt.setDouble(68, this.getAv_last_cal());
			if(this.getAcval_cal_date() == null || this.getAcval_cal_date().equals("null")) {
				pstmt.setNull(69, 91);
			} else {
				pstmt.setDate(69, Date.valueOf(this.getAcval_cal_date()));
			}
			pstmt.setDouble(70, this.getAv_BOY());
			pstmt.setDouble(71, this.getAv_BOM());
			pstmt.setDouble(72, this.getTBMoney());
			if(this.getTBMDate() == null || this.getTBMDate().equals("null")) {
				pstmt.setNull(73, 91);
			} else {
				pstmt.setDate(73, Date.valueOf(this.getTBMDate()));
			}
			if(this.getTransSaleChnl() == null || this.getTransSaleChnl().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getTransSaleChnl());
			}
			pstmt.setDouble(75, this.getCuml_PartSurrender());
			pstmt.setDouble(76, this.getBonusAmnt());
			pstmt.setDouble(77, this.getOtherPrem());
			// set where condition
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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
		SQLString sqlObj = new SQLString("LOReserve");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LOReserve(ManageCom ,ContNo ,PolNo ,PolStatus ,RiskCode ,SaleChnl ,InsuredNo ,InsuredSex ,InsuredBirthday ,InsuredAppAge ,SecInsuredSex ,SecBirthDay ,SecInsuredAge ,CValiDate ,LastRevDate ,MakeDate ,Years ,PayYears ,PayIntv ,StandPrem ,Amnt ,InvalidDate ,FreeStartDate ,DeadDate ,ShouldGetMoney ,StopReason ,StopDate ,RevGetAge ,RevGetDate ,RevGetMoney ,RevGetIntv ,AnnMoney ,DeathMoney ,MedMoney ,ExpMoney ,TotalMoney ,OccPrem ,HealthPrem ,LastPayPrem ,YearIdx ,CalYear ,Rev3 ,Rev1 ,Rev2 ,Rev4 ,CheckFlag ,CalFlag ,ModifyDate ,SignDate ,ThisPayPrem ,PayToDate ,ManageFeeRate ,InsuYearFlag ,PayEndYearFlag ,SurMoney ,SmokingFlag ,OccupationType ,CustomGetPolDate ,FloatRate ,GroupNo ,GetMoney ,GetDutyKind ,AgentCom ,ACType ,EndDate ,FreeFlag ,AutoPayFlag ,Av_last_cal ,Acval_cal_date ,Av_BOY ,Av_BOM ,TBMoney ,TBMDate ,TransSaleChnl ,Cuml_PartSurrender ,BonusAmnt ,OtherPrem) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getManageCom());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getPolStatus() == null || this.getPolStatus().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolStatus());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getRiskCode());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getSaleChnl());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getInsuredNo());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getInsuredBirthday()));
			}
			pstmt.setInt(10, this.getInsuredAppAge());
			if(this.getSecInsuredSex() == null || this.getSecInsuredSex().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getSecInsuredSex());
			}
			if(this.getSecBirthDay() == null || this.getSecBirthDay().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getSecBirthDay()));
			}
			pstmt.setInt(13, this.getSecInsuredAge());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getCValiDate()));
			}
			if(this.getLastRevDate() == null || this.getLastRevDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getLastRevDate()));
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getMakeDate()));
			}
			pstmt.setInt(17, this.getYears());
			pstmt.setInt(18, this.getPayYears());
			pstmt.setInt(19, this.getPayIntv());
			pstmt.setDouble(20, this.getStandPrem());
			pstmt.setDouble(21, this.getAmnt());
			if(this.getInvalidDate() == null || this.getInvalidDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getInvalidDate()));
			}
			if(this.getFreeStartDate() == null || this.getFreeStartDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getFreeStartDate()));
			}
			if(this.getDeadDate() == null || this.getDeadDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getDeadDate()));
			}
			pstmt.setDouble(25, this.getShouldGetMoney());
			if(this.getStopReason() == null || this.getStopReason().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStopReason());
			}
			if(this.getStopDate() == null || this.getStopDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getStopDate()));
			}
			pstmt.setInt(28, this.getRevGetAge());
			if(this.getRevGetDate() == null || this.getRevGetDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getRevGetDate()));
			}
			pstmt.setDouble(30, this.getRevGetMoney());
			pstmt.setInt(31, this.getRevGetIntv());
			pstmt.setDouble(32, this.getAnnMoney());
			pstmt.setDouble(33, this.getDeathMoney());
			pstmt.setDouble(34, this.getMedMoney());
			pstmt.setDouble(35, this.getExpMoney());
			pstmt.setDouble(36, this.getTotalMoney());
			pstmt.setDouble(37, this.getOccPrem());
			pstmt.setDouble(38, this.getHealthPrem());
			pstmt.setDouble(39, this.getLastPayPrem());
			pstmt.setInt(40, this.getYearIdx());
			pstmt.setInt(41, this.getCalYear());
			pstmt.setDouble(42, this.getRev3());
			pstmt.setDouble(43, this.getRev1());
			pstmt.setDouble(44, this.getRev2());
			pstmt.setDouble(45, this.getRev4());
			pstmt.setInt(46, this.getCheckFlag());
			pstmt.setInt(47, this.getCalFlag());
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getModifyDate()));
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(49, 91);
			} else {
				pstmt.setDate(49, Date.valueOf(this.getSignDate()));
			}
			pstmt.setDouble(50, this.getThisPayPrem());
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getPayToDate()));
			}
			pstmt.setDouble(52, this.getManageFeeRate());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(53, 1);
			} else {
				pstmt.setString(53, this.getInsuYearFlag());
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getPayEndYearFlag());
			}
			pstmt.setDouble(55, this.getSurMoney());
			if(this.getSmokingFlag() == null || this.getSmokingFlag().equals("null")) {
				pstmt.setNull(56, 1);
			} else {
				pstmt.setString(56, this.getSmokingFlag());
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(57, 1);
			} else {
				pstmt.setString(57, this.getOccupationType());
			}
			if(this.getCustomGetPolDate() == null || this.getCustomGetPolDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCustomGetPolDate()));
			}
			pstmt.setDouble(59, this.getFloatRate());
			if(this.getGroupNo() == null || this.getGroupNo().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getGroupNo());
			}
			pstmt.setDouble(61, this.getGetMoney());
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getGetDutyKind());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getAgentCom());
			}
			if(this.getACType() == null || this.getACType().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getACType());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getEndDate()));
			}
			if(this.getFreeFlag() == null || this.getFreeFlag().equals("null")) {
				pstmt.setNull(66, 1);
			} else {
				pstmt.setString(66, this.getFreeFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getAutoPayFlag());
			}
			pstmt.setDouble(68, this.getAv_last_cal());
			if(this.getAcval_cal_date() == null || this.getAcval_cal_date().equals("null")) {
				pstmt.setNull(69, 91);
			} else {
				pstmt.setDate(69, Date.valueOf(this.getAcval_cal_date()));
			}
			pstmt.setDouble(70, this.getAv_BOY());
			pstmt.setDouble(71, this.getAv_BOM());
			pstmt.setDouble(72, this.getTBMoney());
			if(this.getTBMDate() == null || this.getTBMDate().equals("null")) {
				pstmt.setNull(73, 91);
			} else {
				pstmt.setDate(73, Date.valueOf(this.getTBMDate()));
			}
			if(this.getTransSaleChnl() == null || this.getTransSaleChnl().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getTransSaleChnl());
			}
			pstmt.setDouble(75, this.getCuml_PartSurrender());
			pstmt.setDouble(76, this.getBonusAmnt());
			pstmt.setDouble(77, this.getOtherPrem());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LOReserve WHERE  PolNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
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
					tError.moduleName = "LOReserveDB";
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
			tError.moduleName = "LOReserveDB";
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

	public LOReserveSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LOReserveSet aLOReserveSet = new LOReserveSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LOReserve");
			LOReserveSchema aSchema = this.getSchema();
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
				LOReserveSchema s1 = new LOReserveSchema();
				s1.setSchema(rs,i);
				aLOReserveSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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

		return aLOReserveSet;

	}

	public LOReserveSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LOReserveSet aLOReserveSet = new LOReserveSet();

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
				LOReserveSchema s1 = new LOReserveSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOReserveDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOReserveSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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

		return aLOReserveSet;
	}

	public LOReserveSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LOReserveSet aLOReserveSet = new LOReserveSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LOReserve");
			LOReserveSchema aSchema = this.getSchema();
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

				LOReserveSchema s1 = new LOReserveSchema();
				s1.setSchema(rs,i);
				aLOReserveSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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

		return aLOReserveSet;

	}

	public LOReserveSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LOReserveSet aLOReserveSet = new LOReserveSet();

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

				LOReserveSchema s1 = new LOReserveSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOReserveDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOReserveSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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

		return aLOReserveSet;
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
			SQLString sqlObj = new SQLString("LOReserve");
			LOReserveSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LOReserve " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LOReserveDB";
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
			tError.moduleName = "LOReserveDB";
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
        tError.moduleName = "LOReserveDB";
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
        tError.moduleName = "LOReserveDB";
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
        tError.moduleName = "LOReserveDB";
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
        tError.moduleName = "LOReserveDB";
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
 * @return LOReserveSet
 */
public LOReserveSet getData()
{
    int tCount = 0;
    LOReserveSet tLOReserveSet = new LOReserveSet();
    LOReserveSchema tLOReserveSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LOReserveDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLOReserveSchema = new LOReserveSchema();
        tLOReserveSchema.setSchema(mResultSet, 1);
        tLOReserveSet.add(tLOReserveSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLOReserveSchema = new LOReserveSchema();
                tLOReserveSchema.setSchema(mResultSet, 1);
                tLOReserveSet.add(tLOReserveSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LOReserveDB";
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
    return tLOReserveSet;
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
            tError.moduleName = "LOReserveDB";
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
        tError.moduleName = "LOReserveDB";
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
            tError.moduleName = "LOReserveDB";
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
        tError.moduleName = "LOReserveDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LOReserveSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOReserveSet aLOReserveSet = new LOReserveSet();

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
				LOReserveSchema s1 = new LOReserveSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOReserveDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOReserveSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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

		return aLOReserveSet;
	}

	public LOReserveSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LOReserveSet aLOReserveSet = new LOReserveSet();

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

				LOReserveSchema s1 = new LOReserveSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LOReserveDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLOReserveSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReserveDB";
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

		return aLOReserveSet; 
	}

}
