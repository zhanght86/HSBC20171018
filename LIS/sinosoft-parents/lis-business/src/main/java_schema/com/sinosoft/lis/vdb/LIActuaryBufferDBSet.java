/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LIActuaryBufferSchema;
import com.sinosoft.lis.vschema.LIActuaryBufferSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LIActuaryBufferDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIActuaryBufferDBSet extends LIActuaryBufferSet
{
private static Logger logger = Logger.getLogger(LIActuaryBufferDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LIActuaryBufferDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LIActuaryBuffer");
		mflag = true;
	}

	public LIActuaryBufferDBSet()
	{
		db = new DBOper( "LIActuaryBuffer" );
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
			tError.moduleName = "LIActuaryBufferDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LIActuaryBuffer WHERE  ContNo = ? AND GrpContNo = ? AND RiskCode = ? AND PeopleNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getContNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getPeopleNo() == null || this.get(i).getPeopleNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPeopleNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIActuaryBuffer");
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
			tError.moduleName = "LIActuaryBufferDBSet";
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
			pstmt = con.prepareStatement("UPDATE LIActuaryBuffer SET  ContNo = ? , GrpContNo = ? , RiskCode = ? , MainFlag = ? , ContType = ? , PeopleNo = ? , MakeDate = ? , CalDate = ? , DestRate = ? , ForeFeeRate = ? , PolState = ? , InterestDifFlag = ? , LoanFlag = ? , LoanInterest = ? , FreeFlag = ? , AutoPayFlag = ? , AutoPayInterest = ? , DeathSolicitude = ? , InOutFlag = ? , InOutAmnt = ? , ContPlanCode = ? , SubMangeCom = ? , CenterManageCom = ? , BranchManageCom = ? , PolYearCount = ? , PolMonthCount = ? , PolDayCount = ? , CValiDate = ? , SignDate = ? , PayToDate = ? , PayIntv = ? , PayYears = ? , PayCount = ? , IfThisTimePay = ? , Prem = ? , Mult = ? , CvalidAmnt = ? , Amnt = ? , TotalBonus = ? , HealthPrem = ? , OccPrem = ? , RatedPrem = ? , PolStrDate = ? , PolEndDate = ? , PoLYears = ? , GetStartAge = ? , GetIntv = ? , GetCriterion = ? , GetTimes = ? , GetYearsNum = ? , FirstName = ? , FirstAge = ? , FirstBirthday = ? , FirstSex = ? , FirstIdNo = ? , FirstOccupationType = ? , FirstInsuredNo = ? , SecondName = ? , SecondAge = ? , SecondBirthday = ? , SecondSex = ? , SecondIdNo = ? , SecondInsuredNo = ? , SecondOccupationType = ? , CashValue = ? , CustomerSerNum = ? , ErrorFlag = ? , PolNo = ? , MainPolNo = ? , ManageCom = ? , PayEndYearFlag = ? , InsuYear = ? , InsuYearFlag = ? , GetStartDate = ? , GetYear = ? , GetYearFlag = ? , SequenceNo = ? WHERE  ContNo = ? AND GrpContNo = ? AND RiskCode = ? AND PeopleNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getContNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getMainFlag() == null || this.get(i).getMainFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainFlag());
			}
			if(this.get(i).getContType() == null || this.get(i).getContType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContType());
			}
			if(this.get(i).getPeopleNo() == null || this.get(i).getPeopleNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPeopleNo());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getCalDate() == null || this.get(i).getCalDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getCalDate()));
			}
			pstmt.setDouble(9, this.get(i).getDestRate());
			pstmt.setDouble(10, this.get(i).getForeFeeRate());
			if(this.get(i).getPolState() == null || this.get(i).getPolState().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPolState());
			}
			if(this.get(i).getInterestDifFlag() == null || this.get(i).getInterestDifFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInterestDifFlag());
			}
			if(this.get(i).getLoanFlag() == null || this.get(i).getLoanFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getLoanFlag());
			}
			pstmt.setDouble(14, this.get(i).getLoanInterest());
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getFreeFlag());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAutoPayFlag());
			}
			pstmt.setDouble(17, this.get(i).getAutoPayInterest());
			if(this.get(i).getDeathSolicitude() == null || this.get(i).getDeathSolicitude().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getDeathSolicitude());
			}
			if(this.get(i).getInOutFlag() == null || this.get(i).getInOutFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getInOutFlag());
			}
			pstmt.setInt(20, this.get(i).getInOutAmnt());
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getContPlanCode());
			}
			if(this.get(i).getSubMangeCom() == null || this.get(i).getSubMangeCom().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getSubMangeCom());
			}
			if(this.get(i).getCenterManageCom() == null || this.get(i).getCenterManageCom().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getCenterManageCom());
			}
			if(this.get(i).getBranchManageCom() == null || this.get(i).getBranchManageCom().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getBranchManageCom());
			}
			pstmt.setInt(25, this.get(i).getPolYearCount());
			pstmt.setInt(26, this.get(i).getPolMonthCount());
			pstmt.setInt(27, this.get(i).getPolDayCount());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getPayToDate() == null || this.get(i).getPayToDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getPayToDate()));
			}
			pstmt.setInt(31, this.get(i).getPayIntv());
			pstmt.setInt(32, this.get(i).getPayYears());
			pstmt.setInt(33, this.get(i).getPayCount());
			if(this.get(i).getIfThisTimePay() == null || this.get(i).getIfThisTimePay().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getIfThisTimePay());
			}
			pstmt.setDouble(35, this.get(i).getPrem());
			pstmt.setDouble(36, this.get(i).getMult());
			pstmt.setInt(37, this.get(i).getCvalidAmnt());
			pstmt.setDouble(38, this.get(i).getAmnt());
			pstmt.setDouble(39, this.get(i).getTotalBonus());
			pstmt.setDouble(40, this.get(i).getHealthPrem());
			pstmt.setDouble(41, this.get(i).getOccPrem());
			pstmt.setDouble(42, this.get(i).getRatedPrem());
			if(this.get(i).getPolStrDate() == null || this.get(i).getPolStrDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getPolStrDate()));
			}
			if(this.get(i).getPolEndDate() == null || this.get(i).getPolEndDate().equals("null")) {
				pstmt.setDate(44,null);
			} else {
				pstmt.setDate(44, Date.valueOf(this.get(i).getPolEndDate()));
			}
			pstmt.setInt(45, this.get(i).getPoLYears());
			pstmt.setInt(46, this.get(i).getGetStartAge());
			if(this.get(i).getGetIntv() == null || this.get(i).getGetIntv().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getGetIntv());
			}
			pstmt.setDouble(48, this.get(i).getGetCriterion());
			pstmt.setInt(49, this.get(i).getGetTimes());
			pstmt.setInt(50, this.get(i).getGetYearsNum());
			if(this.get(i).getFirstName() == null || this.get(i).getFirstName().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getFirstName());
			}
			pstmt.setInt(52, this.get(i).getFirstAge());
			if(this.get(i).getFirstBirthday() == null || this.get(i).getFirstBirthday().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getFirstBirthday()));
			}
			if(this.get(i).getFirstSex() == null || this.get(i).getFirstSex().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getFirstSex());
			}
			if(this.get(i).getFirstIdNo() == null || this.get(i).getFirstIdNo().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getFirstIdNo());
			}
			if(this.get(i).getFirstOccupationType() == null || this.get(i).getFirstOccupationType().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getFirstOccupationType());
			}
			if(this.get(i).getFirstInsuredNo() == null || this.get(i).getFirstInsuredNo().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getFirstInsuredNo());
			}
			if(this.get(i).getSecondName() == null || this.get(i).getSecondName().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getSecondName());
			}
			pstmt.setInt(59, this.get(i).getSecondAge());
			if(this.get(i).getSecondBirthday() == null || this.get(i).getSecondBirthday().equals("null")) {
				pstmt.setDate(60,null);
			} else {
				pstmt.setDate(60, Date.valueOf(this.get(i).getSecondBirthday()));
			}
			if(this.get(i).getSecondSex() == null || this.get(i).getSecondSex().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getSecondSex());
			}
			if(this.get(i).getSecondIdNo() == null || this.get(i).getSecondIdNo().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getSecondIdNo());
			}
			if(this.get(i).getSecondInsuredNo() == null || this.get(i).getSecondInsuredNo().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getSecondInsuredNo());
			}
			if(this.get(i).getSecondOccupationType() == null || this.get(i).getSecondOccupationType().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getSecondOccupationType());
			}
			pstmt.setDouble(65, this.get(i).getCashValue());
			pstmt.setInt(66, this.get(i).getCustomerSerNum());
			pstmt.setInt(67, this.get(i).getErrorFlag());
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getPolNo());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getMainPolNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getManageCom());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(72, this.get(i).getInsuYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getInsuYearFlag());
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getGetStartDate()));
			}
			pstmt.setInt(75, this.get(i).getGetYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getGetYearFlag());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getSequenceNo());
			}
			// set where condition
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getContNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getGrpContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getRiskCode());
			}
			if(this.get(i).getPeopleNo() == null || this.get(i).getPeopleNo().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getPeopleNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIActuaryBuffer");
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
			tError.moduleName = "LIActuaryBufferDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LIActuaryBuffer(ContNo ,GrpContNo ,RiskCode ,MainFlag ,ContType ,PeopleNo ,MakeDate ,CalDate ,DestRate ,ForeFeeRate ,PolState ,InterestDifFlag ,LoanFlag ,LoanInterest ,FreeFlag ,AutoPayFlag ,AutoPayInterest ,DeathSolicitude ,InOutFlag ,InOutAmnt ,ContPlanCode ,SubMangeCom ,CenterManageCom ,BranchManageCom ,PolYearCount ,PolMonthCount ,PolDayCount ,CValiDate ,SignDate ,PayToDate ,PayIntv ,PayYears ,PayCount ,IfThisTimePay ,Prem ,Mult ,CvalidAmnt ,Amnt ,TotalBonus ,HealthPrem ,OccPrem ,RatedPrem ,PolStrDate ,PolEndDate ,PoLYears ,GetStartAge ,GetIntv ,GetCriterion ,GetTimes ,GetYearsNum ,FirstName ,FirstAge ,FirstBirthday ,FirstSex ,FirstIdNo ,FirstOccupationType ,FirstInsuredNo ,SecondName ,SecondAge ,SecondBirthday ,SecondSex ,SecondIdNo ,SecondInsuredNo ,SecondOccupationType ,CashValue ,CustomerSerNum ,ErrorFlag ,PolNo ,MainPolNo ,ManageCom ,PayEndYearFlag ,InsuYear ,InsuYearFlag ,GetStartDate ,GetYear ,GetYearFlag ,SequenceNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getContNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getMainFlag() == null || this.get(i).getMainFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainFlag());
			}
			if(this.get(i).getContType() == null || this.get(i).getContType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContType());
			}
			if(this.get(i).getPeopleNo() == null || this.get(i).getPeopleNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPeopleNo());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getCalDate() == null || this.get(i).getCalDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getCalDate()));
			}
			pstmt.setDouble(9, this.get(i).getDestRate());
			pstmt.setDouble(10, this.get(i).getForeFeeRate());
			if(this.get(i).getPolState() == null || this.get(i).getPolState().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPolState());
			}
			if(this.get(i).getInterestDifFlag() == null || this.get(i).getInterestDifFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInterestDifFlag());
			}
			if(this.get(i).getLoanFlag() == null || this.get(i).getLoanFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getLoanFlag());
			}
			pstmt.setDouble(14, this.get(i).getLoanInterest());
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getFreeFlag());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAutoPayFlag());
			}
			pstmt.setDouble(17, this.get(i).getAutoPayInterest());
			if(this.get(i).getDeathSolicitude() == null || this.get(i).getDeathSolicitude().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getDeathSolicitude());
			}
			if(this.get(i).getInOutFlag() == null || this.get(i).getInOutFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getInOutFlag());
			}
			pstmt.setInt(20, this.get(i).getInOutAmnt());
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getContPlanCode());
			}
			if(this.get(i).getSubMangeCom() == null || this.get(i).getSubMangeCom().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getSubMangeCom());
			}
			if(this.get(i).getCenterManageCom() == null || this.get(i).getCenterManageCom().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getCenterManageCom());
			}
			if(this.get(i).getBranchManageCom() == null || this.get(i).getBranchManageCom().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getBranchManageCom());
			}
			pstmt.setInt(25, this.get(i).getPolYearCount());
			pstmt.setInt(26, this.get(i).getPolMonthCount());
			pstmt.setInt(27, this.get(i).getPolDayCount());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getPayToDate() == null || this.get(i).getPayToDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getPayToDate()));
			}
			pstmt.setInt(31, this.get(i).getPayIntv());
			pstmt.setInt(32, this.get(i).getPayYears());
			pstmt.setInt(33, this.get(i).getPayCount());
			if(this.get(i).getIfThisTimePay() == null || this.get(i).getIfThisTimePay().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getIfThisTimePay());
			}
			pstmt.setDouble(35, this.get(i).getPrem());
			pstmt.setDouble(36, this.get(i).getMult());
			pstmt.setInt(37, this.get(i).getCvalidAmnt());
			pstmt.setDouble(38, this.get(i).getAmnt());
			pstmt.setDouble(39, this.get(i).getTotalBonus());
			pstmt.setDouble(40, this.get(i).getHealthPrem());
			pstmt.setDouble(41, this.get(i).getOccPrem());
			pstmt.setDouble(42, this.get(i).getRatedPrem());
			if(this.get(i).getPolStrDate() == null || this.get(i).getPolStrDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getPolStrDate()));
			}
			if(this.get(i).getPolEndDate() == null || this.get(i).getPolEndDate().equals("null")) {
				pstmt.setDate(44,null);
			} else {
				pstmt.setDate(44, Date.valueOf(this.get(i).getPolEndDate()));
			}
			pstmt.setInt(45, this.get(i).getPoLYears());
			pstmt.setInt(46, this.get(i).getGetStartAge());
			if(this.get(i).getGetIntv() == null || this.get(i).getGetIntv().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getGetIntv());
			}
			pstmt.setDouble(48, this.get(i).getGetCriterion());
			pstmt.setInt(49, this.get(i).getGetTimes());
			pstmt.setInt(50, this.get(i).getGetYearsNum());
			if(this.get(i).getFirstName() == null || this.get(i).getFirstName().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getFirstName());
			}
			pstmt.setInt(52, this.get(i).getFirstAge());
			if(this.get(i).getFirstBirthday() == null || this.get(i).getFirstBirthday().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getFirstBirthday()));
			}
			if(this.get(i).getFirstSex() == null || this.get(i).getFirstSex().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getFirstSex());
			}
			if(this.get(i).getFirstIdNo() == null || this.get(i).getFirstIdNo().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getFirstIdNo());
			}
			if(this.get(i).getFirstOccupationType() == null || this.get(i).getFirstOccupationType().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getFirstOccupationType());
			}
			if(this.get(i).getFirstInsuredNo() == null || this.get(i).getFirstInsuredNo().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getFirstInsuredNo());
			}
			if(this.get(i).getSecondName() == null || this.get(i).getSecondName().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getSecondName());
			}
			pstmt.setInt(59, this.get(i).getSecondAge());
			if(this.get(i).getSecondBirthday() == null || this.get(i).getSecondBirthday().equals("null")) {
				pstmt.setDate(60,null);
			} else {
				pstmt.setDate(60, Date.valueOf(this.get(i).getSecondBirthday()));
			}
			if(this.get(i).getSecondSex() == null || this.get(i).getSecondSex().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getSecondSex());
			}
			if(this.get(i).getSecondIdNo() == null || this.get(i).getSecondIdNo().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getSecondIdNo());
			}
			if(this.get(i).getSecondInsuredNo() == null || this.get(i).getSecondInsuredNo().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getSecondInsuredNo());
			}
			if(this.get(i).getSecondOccupationType() == null || this.get(i).getSecondOccupationType().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getSecondOccupationType());
			}
			pstmt.setDouble(65, this.get(i).getCashValue());
			pstmt.setInt(66, this.get(i).getCustomerSerNum());
			pstmt.setInt(67, this.get(i).getErrorFlag());
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getPolNo());
			}
			if(this.get(i).getMainPolNo() == null || this.get(i).getMainPolNo().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getMainPolNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getManageCom());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getPayEndYearFlag());
			}
			pstmt.setInt(72, this.get(i).getInsuYear());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getInsuYearFlag());
			}
			if(this.get(i).getGetStartDate() == null || this.get(i).getGetStartDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getGetStartDate()));
			}
			pstmt.setInt(75, this.get(i).getGetYear());
			if(this.get(i).getGetYearFlag() == null || this.get(i).getGetYearFlag().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getGetYearFlag());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getSequenceNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIActuaryBuffer");
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
			tError.moduleName = "LIActuaryBufferDBSet";
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
