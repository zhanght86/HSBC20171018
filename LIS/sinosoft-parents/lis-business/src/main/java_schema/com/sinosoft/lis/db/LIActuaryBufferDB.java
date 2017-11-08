/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LIActuaryBufferSchema;
import com.sinosoft.lis.vschema.LIActuaryBufferSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LIActuaryBufferDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIActuaryBufferDB extends LIActuaryBufferSchema
{
private static Logger logger = Logger.getLogger(LIActuaryBufferDB.class);

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
	public LIActuaryBufferDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LIActuaryBuffer" );
		mflag = true;
	}

	public LIActuaryBufferDB()
	{
		con = null;
		db = new DBOper( "LIActuaryBuffer" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LIActuaryBufferSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LIActuaryBufferSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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
			pstmt = con.prepareStatement("DELETE FROM LIActuaryBuffer WHERE  ContNo = ? AND GrpContNo = ? AND RiskCode = ? AND PeopleNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getPeopleNo() == null || this.getPeopleNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPeopleNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIActuaryBuffer");
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
		SQLString sqlObj = new SQLString("LIActuaryBuffer");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LIActuaryBuffer SET  ContNo = ? , GrpContNo = ? , RiskCode = ? , MainFlag = ? , ContType = ? , PeopleNo = ? , MakeDate = ? , CalDate = ? , DestRate = ? , ForeFeeRate = ? , PolState = ? , InterestDifFlag = ? , LoanFlag = ? , LoanInterest = ? , FreeFlag = ? , AutoPayFlag = ? , AutoPayInterest = ? , DeathSolicitude = ? , InOutFlag = ? , InOutAmnt = ? , ContPlanCode = ? , SubMangeCom = ? , CenterManageCom = ? , BranchManageCom = ? , PolYearCount = ? , PolMonthCount = ? , PolDayCount = ? , CValiDate = ? , SignDate = ? , PayToDate = ? , PayIntv = ? , PayYears = ? , PayCount = ? , IfThisTimePay = ? , Prem = ? , Mult = ? , CvalidAmnt = ? , Amnt = ? , TotalBonus = ? , HealthPrem = ? , OccPrem = ? , RatedPrem = ? , PolStrDate = ? , PolEndDate = ? , PoLYears = ? , GetStartAge = ? , GetIntv = ? , GetCriterion = ? , GetTimes = ? , GetYearsNum = ? , FirstName = ? , FirstAge = ? , FirstBirthday = ? , FirstSex = ? , FirstIdNo = ? , FirstOccupationType = ? , FirstInsuredNo = ? , SecondName = ? , SecondAge = ? , SecondBirthday = ? , SecondSex = ? , SecondIdNo = ? , SecondInsuredNo = ? , SecondOccupationType = ? , CashValue = ? , CustomerSerNum = ? , ErrorFlag = ? , PolNo = ? , MainPolNo = ? , ManageCom = ? , PayEndYearFlag = ? , InsuYear = ? , InsuYearFlag = ? , GetStartDate = ? , GetYear = ? , GetYearFlag = ? , SequenceNo = ? WHERE  ContNo = ? AND GrpContNo = ? AND RiskCode = ? AND PeopleNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getMainFlag() == null || this.getMainFlag().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getMainFlag());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getContType());
			}
			if(this.getPeopleNo() == null || this.getPeopleNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPeopleNo());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getMakeDate()));
			}
			if(this.getCalDate() == null || this.getCalDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getCalDate()));
			}
			pstmt.setDouble(9, this.getDestRate());
			pstmt.setDouble(10, this.getForeFeeRate());
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getPolState());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getInterestDifFlag());
			}
			if(this.getLoanFlag() == null || this.getLoanFlag().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getLoanFlag());
			}
			pstmt.setDouble(14, this.getLoanInterest());
			if(this.getFreeFlag() == null || this.getFreeFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getFreeFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getAutoPayFlag());
			}
			pstmt.setDouble(17, this.getAutoPayInterest());
			if(this.getDeathSolicitude() == null || this.getDeathSolicitude().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getDeathSolicitude());
			}
			if(this.getInOutFlag() == null || this.getInOutFlag().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getInOutFlag());
			}
			pstmt.setInt(20, this.getInOutAmnt());
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getContPlanCode());
			}
			if(this.getSubMangeCom() == null || this.getSubMangeCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSubMangeCom());
			}
			if(this.getCenterManageCom() == null || this.getCenterManageCom().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getCenterManageCom());
			}
			if(this.getBranchManageCom() == null || this.getBranchManageCom().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBranchManageCom());
			}
			pstmt.setInt(25, this.getPolYearCount());
			pstmt.setInt(26, this.getPolMonthCount());
			pstmt.setInt(27, this.getPolDayCount());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getCValiDate()));
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getSignDate()));
			}
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getPayToDate()));
			}
			pstmt.setInt(31, this.getPayIntv());
			pstmt.setInt(32, this.getPayYears());
			pstmt.setInt(33, this.getPayCount());
			if(this.getIfThisTimePay() == null || this.getIfThisTimePay().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getIfThisTimePay());
			}
			pstmt.setDouble(35, this.getPrem());
			pstmt.setDouble(36, this.getMult());
			pstmt.setInt(37, this.getCvalidAmnt());
			pstmt.setDouble(38, this.getAmnt());
			pstmt.setDouble(39, this.getTotalBonus());
			pstmt.setDouble(40, this.getHealthPrem());
			pstmt.setDouble(41, this.getOccPrem());
			pstmt.setDouble(42, this.getRatedPrem());
			if(this.getPolStrDate() == null || this.getPolStrDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getPolStrDate()));
			}
			if(this.getPolEndDate() == null || this.getPolEndDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getPolEndDate()));
			}
			pstmt.setInt(45, this.getPoLYears());
			pstmt.setInt(46, this.getGetStartAge());
			if(this.getGetIntv() == null || this.getGetIntv().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getGetIntv());
			}
			pstmt.setDouble(48, this.getGetCriterion());
			pstmt.setInt(49, this.getGetTimes());
			pstmt.setInt(50, this.getGetYearsNum());
			if(this.getFirstName() == null || this.getFirstName().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getFirstName());
			}
			pstmt.setInt(52, this.getFirstAge());
			if(this.getFirstBirthday() == null || this.getFirstBirthday().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getFirstBirthday()));
			}
			if(this.getFirstSex() == null || this.getFirstSex().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getFirstSex());
			}
			if(this.getFirstIdNo() == null || this.getFirstIdNo().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getFirstIdNo());
			}
			if(this.getFirstOccupationType() == null || this.getFirstOccupationType().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getFirstOccupationType());
			}
			if(this.getFirstInsuredNo() == null || this.getFirstInsuredNo().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getFirstInsuredNo());
			}
			if(this.getSecondName() == null || this.getSecondName().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSecondName());
			}
			pstmt.setInt(59, this.getSecondAge());
			if(this.getSecondBirthday() == null || this.getSecondBirthday().equals("null")) {
				pstmt.setNull(60, 91);
			} else {
				pstmt.setDate(60, Date.valueOf(this.getSecondBirthday()));
			}
			if(this.getSecondSex() == null || this.getSecondSex().equals("null")) {
				pstmt.setNull(61, 1);
			} else {
				pstmt.setString(61, this.getSecondSex());
			}
			if(this.getSecondIdNo() == null || this.getSecondIdNo().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getSecondIdNo());
			}
			if(this.getSecondInsuredNo() == null || this.getSecondInsuredNo().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getSecondInsuredNo());
			}
			if(this.getSecondOccupationType() == null || this.getSecondOccupationType().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getSecondOccupationType());
			}
			pstmt.setDouble(65, this.getCashValue());
			pstmt.setInt(66, this.getCustomerSerNum());
			pstmt.setInt(67, this.getErrorFlag());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getPolNo());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getMainPolNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getManageCom());
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(71, 1);
			} else {
				pstmt.setString(71, this.getPayEndYearFlag());
			}
			pstmt.setInt(72, this.getInsuYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(73, 1);
			} else {
				pstmt.setString(73, this.getInsuYearFlag());
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getGetStartDate()));
			}
			pstmt.setInt(75, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(76, 1);
			} else {
				pstmt.setString(76, this.getGetYearFlag());
			}
			if(this.getSequenceNo() == null || this.getSequenceNo().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getSequenceNo());
			}
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getContNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getGrpContNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getRiskCode());
			}
			if(this.getPeopleNo() == null || this.getPeopleNo().equals("null")) {
				pstmt.setNull(81, 12);
			} else {
				pstmt.setString(81, this.getPeopleNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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
		SQLString sqlObj = new SQLString("LIActuaryBuffer");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LIActuaryBuffer(ContNo ,GrpContNo ,RiskCode ,MainFlag ,ContType ,PeopleNo ,MakeDate ,CalDate ,DestRate ,ForeFeeRate ,PolState ,InterestDifFlag ,LoanFlag ,LoanInterest ,FreeFlag ,AutoPayFlag ,AutoPayInterest ,DeathSolicitude ,InOutFlag ,InOutAmnt ,ContPlanCode ,SubMangeCom ,CenterManageCom ,BranchManageCom ,PolYearCount ,PolMonthCount ,PolDayCount ,CValiDate ,SignDate ,PayToDate ,PayIntv ,PayYears ,PayCount ,IfThisTimePay ,Prem ,Mult ,CvalidAmnt ,Amnt ,TotalBonus ,HealthPrem ,OccPrem ,RatedPrem ,PolStrDate ,PolEndDate ,PoLYears ,GetStartAge ,GetIntv ,GetCriterion ,GetTimes ,GetYearsNum ,FirstName ,FirstAge ,FirstBirthday ,FirstSex ,FirstIdNo ,FirstOccupationType ,FirstInsuredNo ,SecondName ,SecondAge ,SecondBirthday ,SecondSex ,SecondIdNo ,SecondInsuredNo ,SecondOccupationType ,CashValue ,CustomerSerNum ,ErrorFlag ,PolNo ,MainPolNo ,ManageCom ,PayEndYearFlag ,InsuYear ,InsuYearFlag ,GetStartDate ,GetYear ,GetYearFlag ,SequenceNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getMainFlag() == null || this.getMainFlag().equals("null")) {
				pstmt.setNull(4, 1);
			} else {
				pstmt.setString(4, this.getMainFlag());
			}
			if(this.getContType() == null || this.getContType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getContType());
			}
			if(this.getPeopleNo() == null || this.getPeopleNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPeopleNo());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getMakeDate()));
			}
			if(this.getCalDate() == null || this.getCalDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getCalDate()));
			}
			pstmt.setDouble(9, this.getDestRate());
			pstmt.setDouble(10, this.getForeFeeRate());
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getPolState());
			}
			if(this.getInterestDifFlag() == null || this.getInterestDifFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getInterestDifFlag());
			}
			if(this.getLoanFlag() == null || this.getLoanFlag().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getLoanFlag());
			}
			pstmt.setDouble(14, this.getLoanInterest());
			if(this.getFreeFlag() == null || this.getFreeFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getFreeFlag());
			}
			if(this.getAutoPayFlag() == null || this.getAutoPayFlag().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getAutoPayFlag());
			}
			pstmt.setDouble(17, this.getAutoPayInterest());
			if(this.getDeathSolicitude() == null || this.getDeathSolicitude().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getDeathSolicitude());
			}
			if(this.getInOutFlag() == null || this.getInOutFlag().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getInOutFlag());
			}
			pstmt.setInt(20, this.getInOutAmnt());
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getContPlanCode());
			}
			if(this.getSubMangeCom() == null || this.getSubMangeCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSubMangeCom());
			}
			if(this.getCenterManageCom() == null || this.getCenterManageCom().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getCenterManageCom());
			}
			if(this.getBranchManageCom() == null || this.getBranchManageCom().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBranchManageCom());
			}
			pstmt.setInt(25, this.getPolYearCount());
			pstmt.setInt(26, this.getPolMonthCount());
			pstmt.setInt(27, this.getPolDayCount());
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getCValiDate()));
			}
			if(this.getSignDate() == null || this.getSignDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getSignDate()));
			}
			if(this.getPayToDate() == null || this.getPayToDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getPayToDate()));
			}
			pstmt.setInt(31, this.getPayIntv());
			pstmt.setInt(32, this.getPayYears());
			pstmt.setInt(33, this.getPayCount());
			if(this.getIfThisTimePay() == null || this.getIfThisTimePay().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getIfThisTimePay());
			}
			pstmt.setDouble(35, this.getPrem());
			pstmt.setDouble(36, this.getMult());
			pstmt.setInt(37, this.getCvalidAmnt());
			pstmt.setDouble(38, this.getAmnt());
			pstmt.setDouble(39, this.getTotalBonus());
			pstmt.setDouble(40, this.getHealthPrem());
			pstmt.setDouble(41, this.getOccPrem());
			pstmt.setDouble(42, this.getRatedPrem());
			if(this.getPolStrDate() == null || this.getPolStrDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getPolStrDate()));
			}
			if(this.getPolEndDate() == null || this.getPolEndDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getPolEndDate()));
			}
			pstmt.setInt(45, this.getPoLYears());
			pstmt.setInt(46, this.getGetStartAge());
			if(this.getGetIntv() == null || this.getGetIntv().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getGetIntv());
			}
			pstmt.setDouble(48, this.getGetCriterion());
			pstmt.setInt(49, this.getGetTimes());
			pstmt.setInt(50, this.getGetYearsNum());
			if(this.getFirstName() == null || this.getFirstName().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getFirstName());
			}
			pstmt.setInt(52, this.getFirstAge());
			if(this.getFirstBirthday() == null || this.getFirstBirthday().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getFirstBirthday()));
			}
			if(this.getFirstSex() == null || this.getFirstSex().equals("null")) {
				pstmt.setNull(54, 1);
			} else {
				pstmt.setString(54, this.getFirstSex());
			}
			if(this.getFirstIdNo() == null || this.getFirstIdNo().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getFirstIdNo());
			}
			if(this.getFirstOccupationType() == null || this.getFirstOccupationType().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getFirstOccupationType());
			}
			if(this.getFirstInsuredNo() == null || this.getFirstInsuredNo().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getFirstInsuredNo());
			}
			if(this.getSecondName() == null || this.getSecondName().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSecondName());
			}
			pstmt.setInt(59, this.getSecondAge());
			if(this.getSecondBirthday() == null || this.getSecondBirthday().equals("null")) {
				pstmt.setNull(60, 91);
			} else {
				pstmt.setDate(60, Date.valueOf(this.getSecondBirthday()));
			}
			if(this.getSecondSex() == null || this.getSecondSex().equals("null")) {
				pstmt.setNull(61, 1);
			} else {
				pstmt.setString(61, this.getSecondSex());
			}
			if(this.getSecondIdNo() == null || this.getSecondIdNo().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getSecondIdNo());
			}
			if(this.getSecondInsuredNo() == null || this.getSecondInsuredNo().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getSecondInsuredNo());
			}
			if(this.getSecondOccupationType() == null || this.getSecondOccupationType().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getSecondOccupationType());
			}
			pstmt.setDouble(65, this.getCashValue());
			pstmt.setInt(66, this.getCustomerSerNum());
			pstmt.setInt(67, this.getErrorFlag());
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getPolNo());
			}
			if(this.getMainPolNo() == null || this.getMainPolNo().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getMainPolNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getManageCom());
			}
			if(this.getPayEndYearFlag() == null || this.getPayEndYearFlag().equals("null")) {
				pstmt.setNull(71, 1);
			} else {
				pstmt.setString(71, this.getPayEndYearFlag());
			}
			pstmt.setInt(72, this.getInsuYear());
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(73, 1);
			} else {
				pstmt.setString(73, this.getInsuYearFlag());
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getGetStartDate()));
			}
			pstmt.setInt(75, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(76, 1);
			} else {
				pstmt.setString(76, this.getGetYearFlag());
			}
			if(this.getSequenceNo() == null || this.getSequenceNo().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getSequenceNo());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LIActuaryBuffer WHERE  ContNo = ? AND GrpContNo = ? AND RiskCode = ? AND PeopleNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpContNo());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getPeopleNo() == null || this.getPeopleNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPeopleNo());
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
					tError.moduleName = "LIActuaryBufferDB";
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
			tError.moduleName = "LIActuaryBufferDB";
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

	public LIActuaryBufferSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LIActuaryBufferSet aLIActuaryBufferSet = new LIActuaryBufferSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LIActuaryBuffer");
			LIActuaryBufferSchema aSchema = this.getSchema();
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
				LIActuaryBufferSchema s1 = new LIActuaryBufferSchema();
				s1.setSchema(rs,i);
				aLIActuaryBufferSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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

		return aLIActuaryBufferSet;

	}

	public LIActuaryBufferSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LIActuaryBufferSet aLIActuaryBufferSet = new LIActuaryBufferSet();

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
				LIActuaryBufferSchema s1 = new LIActuaryBufferSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LIActuaryBufferDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLIActuaryBufferSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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

		return aLIActuaryBufferSet;
	}

	public LIActuaryBufferSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LIActuaryBufferSet aLIActuaryBufferSet = new LIActuaryBufferSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LIActuaryBuffer");
			LIActuaryBufferSchema aSchema = this.getSchema();
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

				LIActuaryBufferSchema s1 = new LIActuaryBufferSchema();
				s1.setSchema(rs,i);
				aLIActuaryBufferSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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

		return aLIActuaryBufferSet;

	}

	public LIActuaryBufferSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LIActuaryBufferSet aLIActuaryBufferSet = new LIActuaryBufferSet();

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

				LIActuaryBufferSchema s1 = new LIActuaryBufferSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LIActuaryBufferDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLIActuaryBufferSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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

		return aLIActuaryBufferSet;
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
			SQLString sqlObj = new SQLString("LIActuaryBuffer");
			LIActuaryBufferSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LIActuaryBuffer " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LIActuaryBufferDB";
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
			tError.moduleName = "LIActuaryBufferDB";
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
        tError.moduleName = "LIActuaryBufferDB";
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
        tError.moduleName = "LIActuaryBufferDB";
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
        tError.moduleName = "LIActuaryBufferDB";
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
        tError.moduleName = "LIActuaryBufferDB";
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
 * @return LIActuaryBufferSet
 */
public LIActuaryBufferSet getData()
{
    int tCount = 0;
    LIActuaryBufferSet tLIActuaryBufferSet = new LIActuaryBufferSet();
    LIActuaryBufferSchema tLIActuaryBufferSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LIActuaryBufferDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLIActuaryBufferSchema = new LIActuaryBufferSchema();
        tLIActuaryBufferSchema.setSchema(mResultSet, 1);
        tLIActuaryBufferSet.add(tLIActuaryBufferSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLIActuaryBufferSchema = new LIActuaryBufferSchema();
                tLIActuaryBufferSchema.setSchema(mResultSet, 1);
                tLIActuaryBufferSet.add(tLIActuaryBufferSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LIActuaryBufferDB";
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
    return tLIActuaryBufferSet;
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
            tError.moduleName = "LIActuaryBufferDB";
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
        tError.moduleName = "LIActuaryBufferDB";
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
            tError.moduleName = "LIActuaryBufferDB";
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
        tError.moduleName = "LIActuaryBufferDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LIActuaryBufferSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LIActuaryBufferSet aLIActuaryBufferSet = new LIActuaryBufferSet();

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
				LIActuaryBufferSchema s1 = new LIActuaryBufferSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LIActuaryBufferDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLIActuaryBufferSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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

		return aLIActuaryBufferSet;
	}

	public LIActuaryBufferSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LIActuaryBufferSet aLIActuaryBufferSet = new LIActuaryBufferSet();

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

				LIActuaryBufferSchema s1 = new LIActuaryBufferSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LIActuaryBufferDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLIActuaryBufferSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIActuaryBufferDB";
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

		return aLIActuaryBufferSet; 
	}

}
