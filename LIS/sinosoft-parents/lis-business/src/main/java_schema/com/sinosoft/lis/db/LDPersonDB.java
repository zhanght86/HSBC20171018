/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDPersonDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LDPersonDB extends LDPersonSchema
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
	public LDPersonDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LDPerson" );
		mflag = true;
	}

	public LDPersonDB()
	{
		con = null;
		db = new DBOper( "LDPerson" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LDPersonSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LDPersonSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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
			pstmt = con.prepareStatement("DELETE FROM LDPerson WHERE  CustomerNo = ?");
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDPerson");
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
		SQLString sqlObj = new SQLString("LDPerson");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LDPerson SET  CustomerNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , Password = ? , NativePlace = ? , Nationality = ? , RgtAddress = ? , Marriage = ? , MarriageDate = ? , Health = ? , Stature = ? , Avoirdupois = ? , Degree = ? , CreditGrade = ? , OthIDType = ? , OthIDNo = ? , ICNo = ? , GrpNo = ? , JoinCompanyDate = ? , StartWorkDate = ? , Position = ? , Salary = ? , OccupationType = ? , OccupationCode = ? , WorkType = ? , PluralityType = ? , DeathDate = ? , SmokeFlag = ? , BlacklistFlag = ? , Proterty = ? , Remark = ? , State = ? , VIPValue = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , GrpName = ? , License = ? , LicenseType = ? , SocialInsuNo = ? , ReasonType = ? , SocialInsuFlag = ? , IDExpDate = ? , LastName = ? , FirstName = ? , LastNameEn = ? , FirstNameEn = ? , NameEn = ? , LastNamePY = ? , FirstNamePY = ? , Language = ? , IDValFlag = ? , IDInitiateDate = ? , FamilyDisease = ? , BMI = ? , Seniority = ? , WorkCompName = ? , WorkAddress = ? , SocialInsuAddress = ? , ManageCom = ? , ComCode = ? , ModifyOperator = ? WHERE  CustomerNo = ?");
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(4, 91);
			} else {
				pstmt.setDate(4, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIDNo());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPassword());
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getNationality());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRgtAddress());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getHealth());
			}
			pstmt.setDouble(14, this.getStature());
			pstmt.setDouble(15, this.getAvoirdupois());
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getCreditGrade());
			}
			if(this.getOthIDType() == null || this.getOthIDType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getOthIDType());
			}
			if(this.getOthIDNo() == null || this.getOthIDNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOthIDNo());
			}
			if(this.getICNo() == null || this.getICNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getICNo());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getGrpNo());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getStartWorkDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPosition());
			}
			pstmt.setDouble(25, this.getSalary());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOccupationType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getOccupationCode());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getPluralityType());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getDeathDate()));
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getSmokeFlag());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getBlacklistFlag());
			}
			if(this.getProterty() == null || this.getProterty().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getProterty());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRemark());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getState());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getVIPValue());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getModifyTime());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getGrpName());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getLicenseType());
			}
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getSocialInsuNo());
			}
			if(this.getReasonType() == null || this.getReasonType().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getReasonType());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getIDExpDate()));
			}
			if(this.getLastName() == null || this.getLastName().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getLastName());
			}
			if(this.getFirstName() == null || this.getFirstName().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getFirstName());
			}
			if(this.getLastNameEn() == null || this.getLastNameEn().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getLastNameEn());
			}
			if(this.getFirstNameEn() == null || this.getFirstNameEn().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getFirstNameEn());
			}
			if(this.getNameEn() == null || this.getNameEn().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getNameEn());
			}
			if(this.getLastNamePY() == null || this.getLastNamePY().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getLastNamePY());
			}
			if(this.getFirstNamePY() == null || this.getFirstNamePY().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getFirstNamePY());
			}
			if(this.getLanguage() == null || this.getLanguage().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getLanguage());
			}
			if(this.getIDValFlag() == null || this.getIDValFlag().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getIDValFlag());
			}
			if(this.getIDInitiateDate() == null || this.getIDInitiateDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getIDInitiateDate()));
			}
			if(this.getFamilyDisease() == null || this.getFamilyDisease().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getFamilyDisease());
			}
			pstmt.setDouble(60, this.getBMI());
			pstmt.setInt(61, this.getSeniority());
			if(this.getWorkCompName() == null || this.getWorkCompName().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getWorkCompName());
			}
			if(this.getWorkAddress() == null || this.getWorkAddress().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getWorkAddress());
			}
			if(this.getSocialInsuAddress() == null || this.getSocialInsuAddress().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getSocialInsuAddress());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getModifyOperator());
			}
			// set where condition
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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
		SQLString sqlObj = new SQLString("LDPerson");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LDPerson VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(4, 91);
			} else {
				pstmt.setDate(4, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getIDNo());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPassword());
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getNationality());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRgtAddress());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getHealth());
			}
			pstmt.setDouble(14, this.getStature());
			pstmt.setDouble(15, this.getAvoirdupois());
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getCreditGrade());
			}
			if(this.getOthIDType() == null || this.getOthIDType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getOthIDType());
			}
			if(this.getOthIDNo() == null || this.getOthIDNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getOthIDNo());
			}
			if(this.getICNo() == null || this.getICNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getICNo());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getGrpNo());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(22, 91);
			} else {
				pstmt.setDate(22, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getStartWorkDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPosition());
			}
			pstmt.setDouble(25, this.getSalary());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOccupationType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getOccupationCode());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getPluralityType());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getDeathDate()));
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getSmokeFlag());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getBlacklistFlag());
			}
			if(this.getProterty() == null || this.getProterty().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getProterty());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRemark());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getState());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getVIPValue());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getModifyTime());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getGrpName());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(43, 1);
			} else {
				pstmt.setString(43, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getLicenseType());
			}
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getSocialInsuNo());
			}
			if(this.getReasonType() == null || this.getReasonType().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getReasonType());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getIDExpDate()));
			}
			if(this.getLastName() == null || this.getLastName().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getLastName());
			}
			if(this.getFirstName() == null || this.getFirstName().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getFirstName());
			}
			if(this.getLastNameEn() == null || this.getLastNameEn().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getLastNameEn());
			}
			if(this.getFirstNameEn() == null || this.getFirstNameEn().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getFirstNameEn());
			}
			if(this.getNameEn() == null || this.getNameEn().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getNameEn());
			}
			if(this.getLastNamePY() == null || this.getLastNamePY().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getLastNamePY());
			}
			if(this.getFirstNamePY() == null || this.getFirstNamePY().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getFirstNamePY());
			}
			if(this.getLanguage() == null || this.getLanguage().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getLanguage());
			}
			if(this.getIDValFlag() == null || this.getIDValFlag().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getIDValFlag());
			}
			if(this.getIDInitiateDate() == null || this.getIDInitiateDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getIDInitiateDate()));
			}
			if(this.getFamilyDisease() == null || this.getFamilyDisease().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getFamilyDisease());
			}
			pstmt.setDouble(60, this.getBMI());
			pstmt.setInt(61, this.getSeniority());
			if(this.getWorkCompName() == null || this.getWorkCompName().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getWorkCompName());
			}
			if(this.getWorkAddress() == null || this.getWorkAddress().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getWorkAddress());
			}
			if(this.getSocialInsuAddress() == null || this.getSocialInsuAddress().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getSocialInsuAddress());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LDPerson WHERE  CustomerNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCustomerNo());
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
					tError.moduleName = "LDPersonDB";
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
			tError.moduleName = "LDPersonDB";
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

	public LDPersonSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDPersonSet aLDPersonSet = new LDPersonSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDPerson");
			LDPersonSchema aSchema = this.getSchema();
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
				LDPersonSchema s1 = new LDPersonSchema();
				s1.setSchema(rs,i);
				aLDPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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

		return aLDPersonSet;
	}

	public LDPersonSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDPersonSet aLDPersonSet = new LDPersonSet();

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
				LDPersonSchema s1 = new LDPersonSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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

		return aLDPersonSet;
	}

	public LDPersonSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDPersonSet aLDPersonSet = new LDPersonSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDPerson");
			LDPersonSchema aSchema = this.getSchema();
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

				LDPersonSchema s1 = new LDPersonSchema();
				s1.setSchema(rs,i);
				aLDPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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

		return aLDPersonSet;
	}

	public LDPersonSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDPersonSet aLDPersonSet = new LDPersonSet();

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

				LDPersonSchema s1 = new LDPersonSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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

		return aLDPersonSet;
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
			SQLString sqlObj = new SQLString("LDPerson");
			LDPersonSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LDPerson " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDPersonDB";
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
			tError.moduleName = "LDPersonDB";
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
        tError.moduleName = "LDPersonDB";
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
        tError.moduleName = "LDPersonDB";
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
        tError.moduleName = "LDPersonDB";
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
        tError.moduleName = "LDPersonDB";
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
 * @return LDPersonSet
 */
public LDPersonSet getData()
{
    int tCount = 0;
    LDPersonSet tLDPersonSet = new LDPersonSet();
    LDPersonSchema tLDPersonSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LDPersonDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLDPersonSchema = new LDPersonSchema();
        tLDPersonSchema.setSchema(mResultSet, 1);
        tLDPersonSet.add(tLDPersonSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLDPersonSchema = new LDPersonSchema();
                tLDPersonSchema.setSchema(mResultSet, 1);
                tLDPersonSet.add(tLDPersonSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LDPersonDB";
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
    return tLDPersonSet;
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
            tError.moduleName = "LDPersonDB";
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
        tError.moduleName = "LDPersonDB";
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
            tError.moduleName = "LDPersonDB";
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
        tError.moduleName = "LDPersonDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LDPersonSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDPersonSet aLDPersonSet = new LDPersonSet();

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
				LDPersonSchema s1 = new LDPersonSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPersonSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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

		return aLDPersonSet;
	}

	public LDPersonSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDPersonSet aLDPersonSet = new LDPersonSet();

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

				LDPersonSchema s1 = new LDPersonSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDPersonSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonDB";
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

		return aLDPersonSet; 
	}

}
