/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCInsuredDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCInsuredDBSet extends LCInsuredSet
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
	public LCInsuredDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCInsured");
		mflag = true;
	}

	public LCInsuredDBSet()
	{
		db = new DBOper( "LCInsured" );
		con = db.getConnection();
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
			tError.moduleName = "LCInsuredDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCInsured WHERE  ContNo = ? AND InsuredNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getInsuredNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCInsured");
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
			tError.moduleName = "LCInsuredDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCInsured SET  GrpContNo = ? , ContNo = ? , InsuredNo = ? , PrtNo = ? , AppntNo = ? , ManageCom = ? , ExecuteCom = ? , FamilyID = ? , RelationToMainInsured = ? , RelationToAppnt = ? , AddressNo = ? , SequenceNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , NativePlace = ? , Nationality = ? , RgtAddress = ? , Marriage = ? , MarriageDate = ? , Health = ? , Stature = ? , Avoirdupois = ? , Degree = ? , CreditGrade = ? , BankCode = ? , BankAccNo = ? , AccName = ? , JoinCompanyDate = ? , StartWorkDate = ? , Position = ? , Salary = ? , OccupationType = ? , OccupationCode = ? , WorkType = ? , PluralityType = ? , SmokeFlag = ? , ContPlanCode = ? , Operator = ? , InsuredStat = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , BMI = ? , InsuredPeoples = ? , License = ? , LicenseType = ? , CustomerSeqNo = ? , SocialInsuNo = ? , SendMsgFlag = ? , SendMailFlag = ? , JoinContDate = ? , WorkNo = ? , CertifyCode = ? , StartCode = ? , EndCode = ? , SocialInsuFlag = ? , IDExpDate = ? , GrpNo = ? , OrganComCode = ? , LastName = ? , FirstName = ? , LastNameEn = ? , FirstNameEn = ? , NameEn = ? , LastNamePY = ? , FirstNamePY = ? , Language = ? , MainCustomerNo = ? , InsuredType = ? , IsLongValid = ? , IDStartDate = ? , InsuredAppAge = ? , Seniority = ? , WorkCompName = ? , PlanCode = ? , DeathDate = ? , BlacklistFlag = ? , VIPValue = ? , SendQQFlag = ? , SendMSNFlag = ? , ServerArea = ? , SubCustomerNo = ? , Substandard = ? , Remark = ? , PakageCode = ? , Destination = ? , Purpose = ? , EmergencyContact = ? , EmerContPhone = ? , ApplytoDay = ? , MaternityFlag = ? , RescueType = ? , DeptCode = ? , SubCompanyCode = ? , InsureCode = ? , WorkAddress = ? , SocialInsuAddress = ? , OriginContNo = ? , OriginInsuredNo = ? , ComCode = ? , ModifyOperator = ? WHERE  ContNo = ? AND InsuredNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppntNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getExecuteCom());
			}
			if(this.get(i).getFamilyID() == null || this.get(i).getFamilyID().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFamilyID());
			}
			if(this.get(i).getRelationToMainInsured() == null || this.get(i).getRelationToMainInsured().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRelationToMainInsured());
			}
			if(this.get(i).getRelationToAppnt() == null || this.get(i).getRelationToAppnt().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRelationToAppnt());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAddressNo());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSequenceNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getIDNo());
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getNationality());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRgtAddress());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMarriage());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getMarriageDate()));
			}
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getHealth());
			}
			pstmt.setDouble(24, this.get(i).getStature());
			pstmt.setDouble(25, this.get(i).getAvoirdupois());
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getDegree());
			}
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCreditGrade());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAccName());
			}
			if(this.get(i).getJoinCompanyDate() == null || this.get(i).getJoinCompanyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getJoinCompanyDate()));
			}
			if(this.get(i).getStartWorkDate() == null || this.get(i).getStartWorkDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getStartWorkDate()));
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getPosition());
			}
			pstmt.setDouble(34, this.get(i).getSalary());
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getOccupationType());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOccupationCode());
			}
			if(this.get(i).getWorkType() == null || this.get(i).getWorkType().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getWorkType());
			}
			if(this.get(i).getPluralityType() == null || this.get(i).getPluralityType().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getPluralityType());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getContPlanCode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getOperator());
			}
			if(this.get(i).getInsuredStat() == null || this.get(i).getInsuredStat().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getInsuredStat());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getModifyTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWCode() == null || this.get(i).getUWCode().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getUWCode());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getUWTime());
			}
			pstmt.setDouble(51, this.get(i).getBMI());
			pstmt.setInt(52, this.get(i).getInsuredPeoples());
			if(this.get(i).getLicense() == null || this.get(i).getLicense().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getLicense());
			}
			if(this.get(i).getLicenseType() == null || this.get(i).getLicenseType().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getLicenseType());
			}
			pstmt.setInt(55, this.get(i).getCustomerSeqNo());
			if(this.get(i).getSocialInsuNo() == null || this.get(i).getSocialInsuNo().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSocialInsuNo());
			}
			if(this.get(i).getSendMsgFlag() == null || this.get(i).getSendMsgFlag().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getSendMsgFlag());
			}
			if(this.get(i).getSendMailFlag() == null || this.get(i).getSendMailFlag().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getSendMailFlag());
			}
			if(this.get(i).getJoinContDate() == null || this.get(i).getJoinContDate().equals("null")) {
				pstmt.setDate(59,null);
			} else {
				pstmt.setDate(59, Date.valueOf(this.get(i).getJoinContDate()));
			}
			if(this.get(i).getWorkNo() == null || this.get(i).getWorkNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getWorkNo());
			}
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getCertifyCode());
			}
			if(this.get(i).getStartCode() == null || this.get(i).getStartCode().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getStartCode());
			}
			if(this.get(i).getEndCode() == null || this.get(i).getEndCode().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getEndCode());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getIDExpDate() == null || this.get(i).getIDExpDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getIDExpDate()));
			}
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getGrpNo());
			}
			if(this.get(i).getOrganComCode() == null || this.get(i).getOrganComCode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getOrganComCode());
			}
			if(this.get(i).getLastName() == null || this.get(i).getLastName().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getLastName());
			}
			if(this.get(i).getFirstName() == null || this.get(i).getFirstName().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getFirstName());
			}
			if(this.get(i).getLastNameEn() == null || this.get(i).getLastNameEn().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getLastNameEn());
			}
			if(this.get(i).getFirstNameEn() == null || this.get(i).getFirstNameEn().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getFirstNameEn());
			}
			if(this.get(i).getNameEn() == null || this.get(i).getNameEn().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getNameEn());
			}
			if(this.get(i).getLastNamePY() == null || this.get(i).getLastNamePY().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getLastNamePY());
			}
			if(this.get(i).getFirstNamePY() == null || this.get(i).getFirstNamePY().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getFirstNamePY());
			}
			if(this.get(i).getLanguage() == null || this.get(i).getLanguage().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getLanguage());
			}
			if(this.get(i).getMainCustomerNo() == null || this.get(i).getMainCustomerNo().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getMainCustomerNo());
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getInsuredType());
			}
			if(this.get(i).getIsLongValid() == null || this.get(i).getIsLongValid().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getIsLongValid());
			}
			if(this.get(i).getIDStartDate() == null || this.get(i).getIDStartDate().equals("null")) {
				pstmt.setDate(79,null);
			} else {
				pstmt.setDate(79, Date.valueOf(this.get(i).getIDStartDate()));
			}
			pstmt.setInt(80, this.get(i).getInsuredAppAge());
			pstmt.setDouble(81, this.get(i).getSeniority());
			if(this.get(i).getWorkCompName() == null || this.get(i).getWorkCompName().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getWorkCompName());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getPlanCode());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(84,null);
			} else {
				pstmt.setDate(84, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getVIPValue() == null || this.get(i).getVIPValue().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getVIPValue());
			}
			if(this.get(i).getSendQQFlag() == null || this.get(i).getSendQQFlag().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getSendQQFlag());
			}
			if(this.get(i).getSendMSNFlag() == null || this.get(i).getSendMSNFlag().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getSendMSNFlag());
			}
			if(this.get(i).getServerArea() == null || this.get(i).getServerArea().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getServerArea());
			}
			if(this.get(i).getSubCustomerNo() == null || this.get(i).getSubCustomerNo().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getSubCustomerNo());
			}
			if(this.get(i).getSubstandard() == null || this.get(i).getSubstandard().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getSubstandard());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getRemark());
			}
			if(this.get(i).getPakageCode() == null || this.get(i).getPakageCode().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getPakageCode());
			}
			if(this.get(i).getDestination() == null || this.get(i).getDestination().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getDestination());
			}
			if(this.get(i).getPurpose() == null || this.get(i).getPurpose().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getPurpose());
			}
			if(this.get(i).getEmergencyContact() == null || this.get(i).getEmergencyContact().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getEmergencyContact());
			}
			if(this.get(i).getEmerContPhone() == null || this.get(i).getEmerContPhone().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getEmerContPhone());
			}
			if(this.get(i).getApplytoDay() == null || this.get(i).getApplytoDay().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getApplytoDay());
			}
			if(this.get(i).getMaternityFlag() == null || this.get(i).getMaternityFlag().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getMaternityFlag());
			}
			if(this.get(i).getRescueType() == null || this.get(i).getRescueType().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getRescueType());
			}
			if(this.get(i).getDeptCode() == null || this.get(i).getDeptCode().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getDeptCode());
			}
			if(this.get(i).getSubCompanyCode() == null || this.get(i).getSubCompanyCode().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getSubCompanyCode());
			}
			if(this.get(i).getInsureCode() == null || this.get(i).getInsureCode().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getInsureCode());
			}
			if(this.get(i).getWorkAddress() == null || this.get(i).getWorkAddress().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getWorkAddress());
			}
			if(this.get(i).getSocialInsuAddress() == null || this.get(i).getSocialInsuAddress().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getSocialInsuAddress());
			}
			if(this.get(i).getOriginContNo() == null || this.get(i).getOriginContNo().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getOriginContNo());
			}
			if(this.get(i).getOriginInsuredNo() == null || this.get(i).getOriginInsuredNo().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getOriginInsuredNo());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(110,null);
			} else {
				pstmt.setString(110, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(111,null);
			} else {
				pstmt.setString(111, this.get(i).getInsuredNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCInsured");
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
			tError.moduleName = "LCInsuredDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCInsured VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtNo());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppntNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getManageCom());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getExecuteCom());
			}
			if(this.get(i).getFamilyID() == null || this.get(i).getFamilyID().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFamilyID());
			}
			if(this.get(i).getRelationToMainInsured() == null || this.get(i).getRelationToMainInsured().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRelationToMainInsured());
			}
			if(this.get(i).getRelationToAppnt() == null || this.get(i).getRelationToAppnt().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRelationToAppnt());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAddressNo());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSequenceNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getIDNo());
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getNationality());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRgtAddress());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMarriage());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getMarriageDate()));
			}
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getHealth());
			}
			pstmt.setDouble(24, this.get(i).getStature());
			pstmt.setDouble(25, this.get(i).getAvoirdupois());
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getDegree());
			}
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCreditGrade());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAccName());
			}
			if(this.get(i).getJoinCompanyDate() == null || this.get(i).getJoinCompanyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getJoinCompanyDate()));
			}
			if(this.get(i).getStartWorkDate() == null || this.get(i).getStartWorkDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getStartWorkDate()));
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getPosition());
			}
			pstmt.setDouble(34, this.get(i).getSalary());
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getOccupationType());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOccupationCode());
			}
			if(this.get(i).getWorkType() == null || this.get(i).getWorkType().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getWorkType());
			}
			if(this.get(i).getPluralityType() == null || this.get(i).getPluralityType().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getPluralityType());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getContPlanCode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getOperator());
			}
			if(this.get(i).getInsuredStat() == null || this.get(i).getInsuredStat().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getInsuredStat());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(45,null);
			} else {
				pstmt.setDate(45, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getModifyTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWCode() == null || this.get(i).getUWCode().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getUWCode());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getUWTime());
			}
			pstmt.setDouble(51, this.get(i).getBMI());
			pstmt.setInt(52, this.get(i).getInsuredPeoples());
			if(this.get(i).getLicense() == null || this.get(i).getLicense().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getLicense());
			}
			if(this.get(i).getLicenseType() == null || this.get(i).getLicenseType().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getLicenseType());
			}
			pstmt.setInt(55, this.get(i).getCustomerSeqNo());
			if(this.get(i).getSocialInsuNo() == null || this.get(i).getSocialInsuNo().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSocialInsuNo());
			}
			if(this.get(i).getSendMsgFlag() == null || this.get(i).getSendMsgFlag().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getSendMsgFlag());
			}
			if(this.get(i).getSendMailFlag() == null || this.get(i).getSendMailFlag().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getSendMailFlag());
			}
			if(this.get(i).getJoinContDate() == null || this.get(i).getJoinContDate().equals("null")) {
				pstmt.setDate(59,null);
			} else {
				pstmt.setDate(59, Date.valueOf(this.get(i).getJoinContDate()));
			}
			if(this.get(i).getWorkNo() == null || this.get(i).getWorkNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getWorkNo());
			}
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getCertifyCode());
			}
			if(this.get(i).getStartCode() == null || this.get(i).getStartCode().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getStartCode());
			}
			if(this.get(i).getEndCode() == null || this.get(i).getEndCode().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getEndCode());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getIDExpDate() == null || this.get(i).getIDExpDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getIDExpDate()));
			}
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getGrpNo());
			}
			if(this.get(i).getOrganComCode() == null || this.get(i).getOrganComCode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getOrganComCode());
			}
			if(this.get(i).getLastName() == null || this.get(i).getLastName().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getLastName());
			}
			if(this.get(i).getFirstName() == null || this.get(i).getFirstName().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getFirstName());
			}
			if(this.get(i).getLastNameEn() == null || this.get(i).getLastNameEn().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getLastNameEn());
			}
			if(this.get(i).getFirstNameEn() == null || this.get(i).getFirstNameEn().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getFirstNameEn());
			}
			if(this.get(i).getNameEn() == null || this.get(i).getNameEn().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getNameEn());
			}
			if(this.get(i).getLastNamePY() == null || this.get(i).getLastNamePY().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getLastNamePY());
			}
			if(this.get(i).getFirstNamePY() == null || this.get(i).getFirstNamePY().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getFirstNamePY());
			}
			if(this.get(i).getLanguage() == null || this.get(i).getLanguage().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getLanguage());
			}
			if(this.get(i).getMainCustomerNo() == null || this.get(i).getMainCustomerNo().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getMainCustomerNo());
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getInsuredType());
			}
			if(this.get(i).getIsLongValid() == null || this.get(i).getIsLongValid().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getIsLongValid());
			}
			if(this.get(i).getIDStartDate() == null || this.get(i).getIDStartDate().equals("null")) {
				pstmt.setDate(79,null);
			} else {
				pstmt.setDate(79, Date.valueOf(this.get(i).getIDStartDate()));
			}
			pstmt.setInt(80, this.get(i).getInsuredAppAge());
			pstmt.setDouble(81, this.get(i).getSeniority());
			if(this.get(i).getWorkCompName() == null || this.get(i).getWorkCompName().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getWorkCompName());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getPlanCode());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(84,null);
			} else {
				pstmt.setDate(84, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getVIPValue() == null || this.get(i).getVIPValue().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getVIPValue());
			}
			if(this.get(i).getSendQQFlag() == null || this.get(i).getSendQQFlag().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getSendQQFlag());
			}
			if(this.get(i).getSendMSNFlag() == null || this.get(i).getSendMSNFlag().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getSendMSNFlag());
			}
			if(this.get(i).getServerArea() == null || this.get(i).getServerArea().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getServerArea());
			}
			if(this.get(i).getSubCustomerNo() == null || this.get(i).getSubCustomerNo().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getSubCustomerNo());
			}
			if(this.get(i).getSubstandard() == null || this.get(i).getSubstandard().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getSubstandard());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getRemark());
			}
			if(this.get(i).getPakageCode() == null || this.get(i).getPakageCode().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getPakageCode());
			}
			if(this.get(i).getDestination() == null || this.get(i).getDestination().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getDestination());
			}
			if(this.get(i).getPurpose() == null || this.get(i).getPurpose().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getPurpose());
			}
			if(this.get(i).getEmergencyContact() == null || this.get(i).getEmergencyContact().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getEmergencyContact());
			}
			if(this.get(i).getEmerContPhone() == null || this.get(i).getEmerContPhone().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getEmerContPhone());
			}
			if(this.get(i).getApplytoDay() == null || this.get(i).getApplytoDay().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getApplytoDay());
			}
			if(this.get(i).getMaternityFlag() == null || this.get(i).getMaternityFlag().equals("null")) {
				pstmt.setString(99,null);
			} else {
				pstmt.setString(99, this.get(i).getMaternityFlag());
			}
			if(this.get(i).getRescueType() == null || this.get(i).getRescueType().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getRescueType());
			}
			if(this.get(i).getDeptCode() == null || this.get(i).getDeptCode().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getDeptCode());
			}
			if(this.get(i).getSubCompanyCode() == null || this.get(i).getSubCompanyCode().equals("null")) {
				pstmt.setString(102,null);
			} else {
				pstmt.setString(102, this.get(i).getSubCompanyCode());
			}
			if(this.get(i).getInsureCode() == null || this.get(i).getInsureCode().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getInsureCode());
			}
			if(this.get(i).getWorkAddress() == null || this.get(i).getWorkAddress().equals("null")) {
				pstmt.setString(104,null);
			} else {
				pstmt.setString(104, this.get(i).getWorkAddress());
			}
			if(this.get(i).getSocialInsuAddress() == null || this.get(i).getSocialInsuAddress().equals("null")) {
				pstmt.setString(105,null);
			} else {
				pstmt.setString(105, this.get(i).getSocialInsuAddress());
			}
			if(this.get(i).getOriginContNo() == null || this.get(i).getOriginContNo().equals("null")) {
				pstmt.setString(106,null);
			} else {
				pstmt.setString(106, this.get(i).getOriginContNo());
			}
			if(this.get(i).getOriginInsuredNo() == null || this.get(i).getOriginInsuredNo().equals("null")) {
				pstmt.setString(107,null);
			} else {
				pstmt.setString(107, this.get(i).getOriginInsuredNo());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(108,null);
			} else {
				pstmt.setString(108, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(109,null);
			} else {
				pstmt.setString(109, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCInsured");
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
			tError.moduleName = "LCInsuredDBSet";
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
