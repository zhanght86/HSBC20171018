/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPInsuredDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPInsuredDB extends LPInsuredSchema
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
	public LPInsuredDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPInsured" );
		mflag = true;
	}

	public LPInsuredDB()
	{
		con = null;
		db = new DBOper( "LPInsured" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPInsuredSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPInsuredSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPInsured WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ? AND InsuredNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuredNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPInsured");
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
		SQLString sqlObj = new SQLString("LPInsured");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPInsured SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , ContNo = ? , InsuredNo = ? , PrtNo = ? , AppntNo = ? , ManageCom = ? , ExecuteCom = ? , FamilyID = ? , RelationToMainInsured = ? , RelationToAppnt = ? , AddressNo = ? , SequenceNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , NativePlace = ? , Nationality = ? , RgtAddress = ? , Marriage = ? , MarriageDate = ? , Health = ? , Stature = ? , Avoirdupois = ? , Degree = ? , CreditGrade = ? , BankCode = ? , BankAccNo = ? , AccName = ? , JoinCompanyDate = ? , StartWorkDate = ? , Position = ? , Salary = ? , OccupationType = ? , OccupationCode = ? , WorkType = ? , PluralityType = ? , SmokeFlag = ? , ContPlanCode = ? , Operator = ? , InsuredStat = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , BMI = ? , InsuredPeoples = ? , License = ? , LicenseType = ? , CustomerSeqNo = ? , SocialInsuNo = ? , SendMsgFlag = ? , SendMailFlag = ? , JoinContDate = ? , WorkNo = ? , CertifyCode = ? , StartCode = ? , EndCode = ? , SocialInsuFlag = ? , IDExpDate = ? , GrpNo = ? , OrganComCode = ? , LastName = ? , FirstName = ? , LastNameEn = ? , FirstNameEn = ? , NameEn = ? , LastNamePY = ? , FirstNamePY = ? , Language = ? , MainCustomerNo = ? , InsuredType = ? , IsLongValid = ? , IDStartDate = ? , InsuredAppAge = ? , Seniority = ? , WorkCompName = ? , PlanCode = ? , DeathDate = ? , BlacklistFlag = ? , VIPValue = ? , SendQQFlag = ? , SendMSNFlag = ? , ServerArea = ? , SubCustomerNo = ? , Substandard = ? , Remark = ? , PakageCode = ? , Destination = ? , Purpose = ? , EmergencyContact = ? , EmerContPhone = ? , ApplytoDay = ? , MaternityFlag = ? , RescueType = ? , DeptCode = ? , SubCompanyCode = ? , InsureCode = ? , WorkAddress = ? , SocialInsuAddress = ? , OriginContNo = ? , OriginInsuredNo = ? , ComCode = ? , ModifyOperator = ? WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ? AND InsuredNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getInsuredNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtNo());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAppntNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getManageCom());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getExecuteCom());
			}
			if(this.getFamilyID() == null || this.getFamilyID().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getFamilyID());
			}
			if(this.getRelationToMainInsured() == null || this.getRelationToMainInsured().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRelationToMainInsured());
			}
			if(this.getRelationToAppnt() == null || this.getRelationToAppnt().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getRelationToAppnt());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAddressNo());
			}
			if(this.getSequenceNo() == null || this.getSequenceNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getSequenceNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getIDNo());
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getNationality());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getRgtAddress());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getHealth());
			}
			pstmt.setDouble(26, this.getStature());
			pstmt.setDouble(27, this.getAvoirdupois());
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getCreditGrade());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getAccName());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getStartWorkDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getPosition());
			}
			pstmt.setDouble(36, this.getSalary());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOccupationType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOccupationCode());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPluralityType());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getSmokeFlag());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getContPlanCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getOperator());
			}
			if(this.getInsuredStat() == null || this.getInsuredStat().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getInsuredStat());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(47, 91);
			} else {
				pstmt.setDate(47, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getModifyTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getUWTime());
			}
			pstmt.setDouble(53, this.getBMI());
			pstmt.setInt(54, this.getInsuredPeoples());
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getLicenseType());
			}
			pstmt.setInt(57, this.getCustomerSeqNo());
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSocialInsuNo());
			}
			if(this.getSendMsgFlag() == null || this.getSendMsgFlag().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getSendMsgFlag());
			}
			if(this.getSendMailFlag() == null || this.getSendMailFlag().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getSendMailFlag());
			}
			if(this.getJoinContDate() == null || this.getJoinContDate().equals("null")) {
				pstmt.setNull(61, 91);
			} else {
				pstmt.setDate(61, Date.valueOf(this.getJoinContDate()));
			}
			if(this.getWorkNo() == null || this.getWorkNo().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getWorkNo());
			}
			if(this.getCertifyCode() == null || this.getCertifyCode().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getCertifyCode());
			}
			if(this.getStartCode() == null || this.getStartCode().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getStartCode());
			}
			if(this.getEndCode() == null || this.getEndCode().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getEndCode());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(67, 91);
			} else {
				pstmt.setDate(67, Date.valueOf(this.getIDExpDate()));
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getGrpNo());
			}
			if(this.getOrganComCode() == null || this.getOrganComCode().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getOrganComCode());
			}
			if(this.getLastName() == null || this.getLastName().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getLastName());
			}
			if(this.getFirstName() == null || this.getFirstName().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getFirstName());
			}
			if(this.getLastNameEn() == null || this.getLastNameEn().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getLastNameEn());
			}
			if(this.getFirstNameEn() == null || this.getFirstNameEn().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getFirstNameEn());
			}
			if(this.getNameEn() == null || this.getNameEn().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getNameEn());
			}
			if(this.getLastNamePY() == null || this.getLastNamePY().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getLastNamePY());
			}
			if(this.getFirstNamePY() == null || this.getFirstNamePY().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getFirstNamePY());
			}
			if(this.getLanguage() == null || this.getLanguage().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getLanguage());
			}
			if(this.getMainCustomerNo() == null || this.getMainCustomerNo().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getMainCustomerNo());
			}
			if(this.getInsuredType() == null || this.getInsuredType().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getInsuredType());
			}
			if(this.getIsLongValid() == null || this.getIsLongValid().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getIsLongValid());
			}
			if(this.getIDStartDate() == null || this.getIDStartDate().equals("null")) {
				pstmt.setNull(81, 91);
			} else {
				pstmt.setDate(81, Date.valueOf(this.getIDStartDate()));
			}
			pstmt.setInt(82, this.getInsuredAppAge());
			pstmt.setDouble(83, this.getSeniority());
			if(this.getWorkCompName() == null || this.getWorkCompName().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getWorkCompName());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getPlanCode());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(86, 91);
			} else {
				pstmt.setDate(86, Date.valueOf(this.getDeathDate()));
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getBlacklistFlag());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getVIPValue());
			}
			if(this.getSendQQFlag() == null || this.getSendQQFlag().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getSendQQFlag());
			}
			if(this.getSendMSNFlag() == null || this.getSendMSNFlag().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getSendMSNFlag());
			}
			if(this.getServerArea() == null || this.getServerArea().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getServerArea());
			}
			if(this.getSubCustomerNo() == null || this.getSubCustomerNo().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getSubCustomerNo());
			}
			if(this.getSubstandard() == null || this.getSubstandard().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getSubstandard());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getRemark());
			}
			if(this.getPakageCode() == null || this.getPakageCode().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getPakageCode());
			}
			if(this.getDestination() == null || this.getDestination().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getDestination());
			}
			if(this.getPurpose() == null || this.getPurpose().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getPurpose());
			}
			if(this.getEmergencyContact() == null || this.getEmergencyContact().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getEmergencyContact());
			}
			if(this.getEmerContPhone() == null || this.getEmerContPhone().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getEmerContPhone());
			}
			if(this.getApplytoDay() == null || this.getApplytoDay().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getApplytoDay());
			}
			if(this.getMaternityFlag() == null || this.getMaternityFlag().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getMaternityFlag());
			}
			if(this.getRescueType() == null || this.getRescueType().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getRescueType());
			}
			if(this.getDeptCode() == null || this.getDeptCode().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getDeptCode());
			}
			if(this.getSubCompanyCode() == null || this.getSubCompanyCode().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getSubCompanyCode());
			}
			if(this.getInsureCode() == null || this.getInsureCode().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getInsureCode());
			}
			if(this.getWorkAddress() == null || this.getWorkAddress().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getWorkAddress());
			}
			if(this.getSocialInsuAddress() == null || this.getSocialInsuAddress().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getSocialInsuAddress());
			}
			if(this.getOriginContNo() == null || this.getOriginContNo().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getOriginContNo());
			}
			if(this.getOriginInsuredNo() == null || this.getOriginInsuredNo().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getOriginInsuredNo());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(111, 12);
			} else {
				pstmt.setString(111, this.getModifyOperator());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(112, 12);
			} else {
				pstmt.setString(112, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(113, 12);
			} else {
				pstmt.setString(113, this.getEdorType());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(114, 12);
			} else {
				pstmt.setString(114, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(115, 12);
			} else {
				pstmt.setString(115, this.getInsuredNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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
		SQLString sqlObj = new SQLString("LPInsured");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPInsured VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getInsuredNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPrtNo());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAppntNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getManageCom());
			}
			if(this.getExecuteCom() == null || this.getExecuteCom().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getExecuteCom());
			}
			if(this.getFamilyID() == null || this.getFamilyID().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getFamilyID());
			}
			if(this.getRelationToMainInsured() == null || this.getRelationToMainInsured().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRelationToMainInsured());
			}
			if(this.getRelationToAppnt() == null || this.getRelationToAppnt().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getRelationToAppnt());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAddressNo());
			}
			if(this.getSequenceNo() == null || this.getSequenceNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getSequenceNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getIDNo());
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getNationality());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getRgtAddress());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getHealth());
			}
			pstmt.setDouble(26, this.getStature());
			pstmt.setDouble(27, this.getAvoirdupois());
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getCreditGrade());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getAccName());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getStartWorkDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getPosition());
			}
			pstmt.setDouble(36, this.getSalary());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOccupationType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOccupationCode());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPluralityType());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getSmokeFlag());
			}
			if(this.getContPlanCode() == null || this.getContPlanCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getContPlanCode());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getOperator());
			}
			if(this.getInsuredStat() == null || this.getInsuredStat().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getInsuredStat());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(47, 91);
			} else {
				pstmt.setDate(47, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getModifyTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getUWTime());
			}
			pstmt.setDouble(53, this.getBMI());
			pstmt.setInt(54, this.getInsuredPeoples());
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getLicenseType());
			}
			pstmt.setInt(57, this.getCustomerSeqNo());
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSocialInsuNo());
			}
			if(this.getSendMsgFlag() == null || this.getSendMsgFlag().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getSendMsgFlag());
			}
			if(this.getSendMailFlag() == null || this.getSendMailFlag().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getSendMailFlag());
			}
			if(this.getJoinContDate() == null || this.getJoinContDate().equals("null")) {
				pstmt.setNull(61, 91);
			} else {
				pstmt.setDate(61, Date.valueOf(this.getJoinContDate()));
			}
			if(this.getWorkNo() == null || this.getWorkNo().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getWorkNo());
			}
			if(this.getCertifyCode() == null || this.getCertifyCode().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getCertifyCode());
			}
			if(this.getStartCode() == null || this.getStartCode().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getStartCode());
			}
			if(this.getEndCode() == null || this.getEndCode().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getEndCode());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(67, 91);
			} else {
				pstmt.setDate(67, Date.valueOf(this.getIDExpDate()));
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getGrpNo());
			}
			if(this.getOrganComCode() == null || this.getOrganComCode().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getOrganComCode());
			}
			if(this.getLastName() == null || this.getLastName().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getLastName());
			}
			if(this.getFirstName() == null || this.getFirstName().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getFirstName());
			}
			if(this.getLastNameEn() == null || this.getLastNameEn().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getLastNameEn());
			}
			if(this.getFirstNameEn() == null || this.getFirstNameEn().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getFirstNameEn());
			}
			if(this.getNameEn() == null || this.getNameEn().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getNameEn());
			}
			if(this.getLastNamePY() == null || this.getLastNamePY().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getLastNamePY());
			}
			if(this.getFirstNamePY() == null || this.getFirstNamePY().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getFirstNamePY());
			}
			if(this.getLanguage() == null || this.getLanguage().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getLanguage());
			}
			if(this.getMainCustomerNo() == null || this.getMainCustomerNo().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getMainCustomerNo());
			}
			if(this.getInsuredType() == null || this.getInsuredType().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getInsuredType());
			}
			if(this.getIsLongValid() == null || this.getIsLongValid().equals("null")) {
				pstmt.setNull(80, 12);
			} else {
				pstmt.setString(80, this.getIsLongValid());
			}
			if(this.getIDStartDate() == null || this.getIDStartDate().equals("null")) {
				pstmt.setNull(81, 91);
			} else {
				pstmt.setDate(81, Date.valueOf(this.getIDStartDate()));
			}
			pstmt.setInt(82, this.getInsuredAppAge());
			pstmt.setDouble(83, this.getSeniority());
			if(this.getWorkCompName() == null || this.getWorkCompName().equals("null")) {
				pstmt.setNull(84, 12);
			} else {
				pstmt.setString(84, this.getWorkCompName());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(85, 12);
			} else {
				pstmt.setString(85, this.getPlanCode());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(86, 91);
			} else {
				pstmt.setDate(86, Date.valueOf(this.getDeathDate()));
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(87, 12);
			} else {
				pstmt.setString(87, this.getBlacklistFlag());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(88, 12);
			} else {
				pstmt.setString(88, this.getVIPValue());
			}
			if(this.getSendQQFlag() == null || this.getSendQQFlag().equals("null")) {
				pstmt.setNull(89, 12);
			} else {
				pstmt.setString(89, this.getSendQQFlag());
			}
			if(this.getSendMSNFlag() == null || this.getSendMSNFlag().equals("null")) {
				pstmt.setNull(90, 12);
			} else {
				pstmt.setString(90, this.getSendMSNFlag());
			}
			if(this.getServerArea() == null || this.getServerArea().equals("null")) {
				pstmt.setNull(91, 12);
			} else {
				pstmt.setString(91, this.getServerArea());
			}
			if(this.getSubCustomerNo() == null || this.getSubCustomerNo().equals("null")) {
				pstmt.setNull(92, 12);
			} else {
				pstmt.setString(92, this.getSubCustomerNo());
			}
			if(this.getSubstandard() == null || this.getSubstandard().equals("null")) {
				pstmt.setNull(93, 12);
			} else {
				pstmt.setString(93, this.getSubstandard());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(94, 12);
			} else {
				pstmt.setString(94, this.getRemark());
			}
			if(this.getPakageCode() == null || this.getPakageCode().equals("null")) {
				pstmt.setNull(95, 12);
			} else {
				pstmt.setString(95, this.getPakageCode());
			}
			if(this.getDestination() == null || this.getDestination().equals("null")) {
				pstmt.setNull(96, 12);
			} else {
				pstmt.setString(96, this.getDestination());
			}
			if(this.getPurpose() == null || this.getPurpose().equals("null")) {
				pstmt.setNull(97, 12);
			} else {
				pstmt.setString(97, this.getPurpose());
			}
			if(this.getEmergencyContact() == null || this.getEmergencyContact().equals("null")) {
				pstmt.setNull(98, 12);
			} else {
				pstmt.setString(98, this.getEmergencyContact());
			}
			if(this.getEmerContPhone() == null || this.getEmerContPhone().equals("null")) {
				pstmt.setNull(99, 12);
			} else {
				pstmt.setString(99, this.getEmerContPhone());
			}
			if(this.getApplytoDay() == null || this.getApplytoDay().equals("null")) {
				pstmt.setNull(100, 12);
			} else {
				pstmt.setString(100, this.getApplytoDay());
			}
			if(this.getMaternityFlag() == null || this.getMaternityFlag().equals("null")) {
				pstmt.setNull(101, 12);
			} else {
				pstmt.setString(101, this.getMaternityFlag());
			}
			if(this.getRescueType() == null || this.getRescueType().equals("null")) {
				pstmt.setNull(102, 12);
			} else {
				pstmt.setString(102, this.getRescueType());
			}
			if(this.getDeptCode() == null || this.getDeptCode().equals("null")) {
				pstmt.setNull(103, 12);
			} else {
				pstmt.setString(103, this.getDeptCode());
			}
			if(this.getSubCompanyCode() == null || this.getSubCompanyCode().equals("null")) {
				pstmt.setNull(104, 12);
			} else {
				pstmt.setString(104, this.getSubCompanyCode());
			}
			if(this.getInsureCode() == null || this.getInsureCode().equals("null")) {
				pstmt.setNull(105, 12);
			} else {
				pstmt.setString(105, this.getInsureCode());
			}
			if(this.getWorkAddress() == null || this.getWorkAddress().equals("null")) {
				pstmt.setNull(106, 12);
			} else {
				pstmt.setString(106, this.getWorkAddress());
			}
			if(this.getSocialInsuAddress() == null || this.getSocialInsuAddress().equals("null")) {
				pstmt.setNull(107, 12);
			} else {
				pstmt.setString(107, this.getSocialInsuAddress());
			}
			if(this.getOriginContNo() == null || this.getOriginContNo().equals("null")) {
				pstmt.setNull(108, 12);
			} else {
				pstmt.setString(108, this.getOriginContNo());
			}
			if(this.getOriginInsuredNo() == null || this.getOriginInsuredNo().equals("null")) {
				pstmt.setNull(109, 12);
			} else {
				pstmt.setString(109, this.getOriginInsuredNo());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(110, 12);
			} else {
				pstmt.setString(110, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(111, 12);
			} else {
				pstmt.setString(111, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPInsured WHERE  EdorNo = ? AND EdorType = ? AND ContNo = ? AND InsuredNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuredNo());
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
					tError.moduleName = "LPInsuredDB";
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
			tError.moduleName = "LPInsuredDB";
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

	public LPInsuredSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPInsured");
			LPInsuredSchema aSchema = this.getSchema();
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
				LPInsuredSchema s1 = new LPInsuredSchema();
				s1.setSchema(rs,i);
				aLPInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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

		return aLPInsuredSet;
	}

	public LPInsuredSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

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
				LPInsuredSchema s1 = new LPInsuredSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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

		return aLPInsuredSet;
	}

	public LPInsuredSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPInsured");
			LPInsuredSchema aSchema = this.getSchema();
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

				LPInsuredSchema s1 = new LPInsuredSchema();
				s1.setSchema(rs,i);
				aLPInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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

		return aLPInsuredSet;
	}

	public LPInsuredSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

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

				LPInsuredSchema s1 = new LPInsuredSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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

		return aLPInsuredSet;
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
			SQLString sqlObj = new SQLString("LPInsured");
			LPInsuredSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPInsured " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPInsuredDB";
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
			tError.moduleName = "LPInsuredDB";
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
        tError.moduleName = "LPInsuredDB";
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
        tError.moduleName = "LPInsuredDB";
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
        tError.moduleName = "LPInsuredDB";
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
        tError.moduleName = "LPInsuredDB";
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
 * @return LPInsuredSet
 */
public LPInsuredSet getData()
{
    int tCount = 0;
    LPInsuredSet tLPInsuredSet = new LPInsuredSet();
    LPInsuredSchema tLPInsuredSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPInsuredDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPInsuredSchema = new LPInsuredSchema();
        tLPInsuredSchema.setSchema(mResultSet, 1);
        tLPInsuredSet.add(tLPInsuredSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPInsuredSchema = new LPInsuredSchema();
                tLPInsuredSchema.setSchema(mResultSet, 1);
                tLPInsuredSet.add(tLPInsuredSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPInsuredDB";
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
    return tLPInsuredSet;
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
            tError.moduleName = "LPInsuredDB";
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
        tError.moduleName = "LPInsuredDB";
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
            tError.moduleName = "LPInsuredDB";
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
        tError.moduleName = "LPInsuredDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPInsuredSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

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
				LPInsuredSchema s1 = new LPInsuredSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPInsuredSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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

		return aLPInsuredSet;
	}

	public LPInsuredSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

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

				LPInsuredSchema s1 = new LPInsuredSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPInsuredSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuredDB";
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

		return aLPInsuredSet; 
	}

}
