/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOInsuredBSchema;
import com.sinosoft.lis.vschema.LBPOInsuredBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOInsuredBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_16
 */
public class LBPOInsuredBDBSet extends LBPOInsuredBSet
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
	public LBPOInsuredBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPOInsuredB");
		mflag = true;
	}

	public LBPOInsuredBDBSet()
	{
		db = new DBOper( "LBPOInsuredB" );
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
			tError.moduleName = "LBPOInsuredBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOInsuredB WHERE  IDX = ? AND ContNo = ? AND InputNo = ? AND FillNo = ? AND InsuredNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			pstmt.setInt(3, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFillNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getInsuredNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsuredB");
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
			tError.moduleName = "LBPOInsuredBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPOInsuredB SET  IDX = ? , GrpContNo = ? , ContNo = ? , InputNo = ? , FillNo = ? , InsuredNo = ? , PrtNo = ? , AppntNo = ? , ManageCom = ? , ExecuteCom = ? , FamilyID = ? , RelationToMainInsured = ? , RelationToAppnt = ? , AddressNo = ? , SequenceNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , NativePlace = ? , Nationality = ? , RgtAddress = ? , Marriage = ? , MarriageDate = ? , Health = ? , Stature = ? , Avoirdupois = ? , Degree = ? , CreditGrade = ? , BankCode = ? , BankAccNo = ? , AccName = ? , JoinCompanyDate = ? , Position = ? , Salary = ? , OccupationType = ? , StartWorkDate = ? , OccupationCode = ? , WorkType = ? , PluralityType = ? , SmokeFlag = ? , ContPlanCode = ? , Operator = ? , InsuredStat = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , BMI = ? , InsuredPeoples = ? , License = ? , LicenseType = ? , CustomerSeqNo = ? , SocialInsuNo = ? , SignName = ? , EMail = ? , Mobile = ? , CompanyFax = ? , CompanyPhone = ? , CompanyZipCode = ? , CompanyAddress = ? , HomeFax = ? , HomePhone = ? , HomeZipCode = ? , HomeAddress = ? , Fax = ? , Phone = ? , ZipCode = ? , PostalAddress = ? , TransDate = ? , TransTime = ? , TransOperator = ? , SocialInsuFlag = ? , IDExpDate = ? WHERE  IDX = ? AND ContNo = ? AND InputNo = ? AND FillNo = ? AND InsuredNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			pstmt.setInt(4, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFillNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPrtNo());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppntNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExecuteCom());
			}
			if(this.get(i).getFamilyID() == null || this.get(i).getFamilyID().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getFamilyID());
			}
			if(this.get(i).getRelationToMainInsured() == null || this.get(i).getRelationToMainInsured().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRelationToMainInsured());
			}
			if(this.get(i).getRelationToAppnt() == null || this.get(i).getRelationToAppnt().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRelationToAppnt());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAddressNo());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getSequenceNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBirthday());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getIDNo());
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getNationality());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRgtAddress());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMarriage());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getMarriageDate());
			}
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getHealth());
			}
			if(this.get(i).getStature() == null || this.get(i).getStature().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStature());
			}
			if(this.get(i).getAvoirdupois() == null || this.get(i).getAvoirdupois().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAvoirdupois());
			}
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getDegree());
			}
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCreditGrade());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getAccName());
			}
			if(this.get(i).getJoinCompanyDate() == null || this.get(i).getJoinCompanyDate().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getJoinCompanyDate());
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getPosition());
			}
			if(this.get(i).getSalary() == null || this.get(i).getSalary().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getSalary());
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOccupationType());
			}
			if(this.get(i).getStartWorkDate() == null || this.get(i).getStartWorkDate().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getStartWorkDate());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getOccupationCode());
			}
			if(this.get(i).getWorkType() == null || this.get(i).getWorkType().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getWorkType());
			}
			if(this.get(i).getPluralityType() == null || this.get(i).getPluralityType().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPluralityType());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getContPlanCode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getOperator());
			}
			if(this.get(i).getInsuredStat() == null || this.get(i).getInsuredStat().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getInsuredStat());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMakeDate());
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getModifyDate());
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getModifyTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWCode() == null || this.get(i).getUWCode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getUWCode());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getUWDate());
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getUWTime());
			}
			if(this.get(i).getBMI() == null || this.get(i).getBMI().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getBMI());
			}
			if(this.get(i).getInsuredPeoples() == null || this.get(i).getInsuredPeoples().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getInsuredPeoples());
			}
			if(this.get(i).getLicense() == null || this.get(i).getLicense().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getLicense());
			}
			if(this.get(i).getLicenseType() == null || this.get(i).getLicenseType().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getLicenseType());
			}
			if(this.get(i).getCustomerSeqNo() == null || this.get(i).getCustomerSeqNo().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getCustomerSeqNo());
			}
			if(this.get(i).getSocialInsuNo() == null || this.get(i).getSocialInsuNo().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getSocialInsuNo());
			}
			if(this.get(i).getSignName() == null || this.get(i).getSignName().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getSignName());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getEMail());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getMobile());
			}
			if(this.get(i).getCompanyFax() == null || this.get(i).getCompanyFax().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getCompanyFax());
			}
			if(this.get(i).getCompanyPhone() == null || this.get(i).getCompanyPhone().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getCompanyPhone());
			}
			if(this.get(i).getCompanyZipCode() == null || this.get(i).getCompanyZipCode().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getCompanyZipCode());
			}
			if(this.get(i).getCompanyAddress() == null || this.get(i).getCompanyAddress().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getCompanyAddress());
			}
			if(this.get(i).getHomeFax() == null || this.get(i).getHomeFax().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getHomeFax());
			}
			if(this.get(i).getHomePhone() == null || this.get(i).getHomePhone().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getHomePhone());
			}
			if(this.get(i).getHomeZipCode() == null || this.get(i).getHomeZipCode().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getHomeZipCode());
			}
			if(this.get(i).getHomeAddress() == null || this.get(i).getHomeAddress().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getHomeAddress());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getPhone());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getZipCode());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getPostalAddress());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(75,null);
			} else {
				pstmt.setDate(75, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransTime() == null || this.get(i).getTransTime().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getTransTime());
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getTransOperator());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getIDExpDate() == null || this.get(i).getIDExpDate().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getIDExpDate());
			}
			// set where condition
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getIDX());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getContNo());
			}
			pstmt.setInt(82, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getFillNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getInsuredNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsuredB");
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
			tError.moduleName = "LBPOInsuredBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPOInsuredB VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			pstmt.setInt(4, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFillNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPrtNo());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppntNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getExecuteCom() == null || this.get(i).getExecuteCom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getExecuteCom());
			}
			if(this.get(i).getFamilyID() == null || this.get(i).getFamilyID().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getFamilyID());
			}
			if(this.get(i).getRelationToMainInsured() == null || this.get(i).getRelationToMainInsured().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getRelationToMainInsured());
			}
			if(this.get(i).getRelationToAppnt() == null || this.get(i).getRelationToAppnt().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getRelationToAppnt());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAddressNo());
			}
			if(this.get(i).getSequenceNo() == null || this.get(i).getSequenceNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getSequenceNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getBirthday());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getIDNo());
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getNationality());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRgtAddress());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMarriage());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getMarriageDate());
			}
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getHealth());
			}
			if(this.get(i).getStature() == null || this.get(i).getStature().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStature());
			}
			if(this.get(i).getAvoirdupois() == null || this.get(i).getAvoirdupois().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAvoirdupois());
			}
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getDegree());
			}
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCreditGrade());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getAccName());
			}
			if(this.get(i).getJoinCompanyDate() == null || this.get(i).getJoinCompanyDate().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getJoinCompanyDate());
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getPosition());
			}
			if(this.get(i).getSalary() == null || this.get(i).getSalary().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getSalary());
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOccupationType());
			}
			if(this.get(i).getStartWorkDate() == null || this.get(i).getStartWorkDate().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getStartWorkDate());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getOccupationCode());
			}
			if(this.get(i).getWorkType() == null || this.get(i).getWorkType().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getWorkType());
			}
			if(this.get(i).getPluralityType() == null || this.get(i).getPluralityType().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPluralityType());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getContPlanCode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getOperator());
			}
			if(this.get(i).getInsuredStat() == null || this.get(i).getInsuredStat().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getInsuredStat());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getMakeDate());
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getModifyDate());
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getModifyTime());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWCode() == null || this.get(i).getUWCode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getUWCode());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getUWDate());
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getUWTime());
			}
			if(this.get(i).getBMI() == null || this.get(i).getBMI().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getBMI());
			}
			if(this.get(i).getInsuredPeoples() == null || this.get(i).getInsuredPeoples().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getInsuredPeoples());
			}
			if(this.get(i).getLicense() == null || this.get(i).getLicense().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getLicense());
			}
			if(this.get(i).getLicenseType() == null || this.get(i).getLicenseType().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getLicenseType());
			}
			if(this.get(i).getCustomerSeqNo() == null || this.get(i).getCustomerSeqNo().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getCustomerSeqNo());
			}
			if(this.get(i).getSocialInsuNo() == null || this.get(i).getSocialInsuNo().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getSocialInsuNo());
			}
			if(this.get(i).getSignName() == null || this.get(i).getSignName().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getSignName());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getEMail());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getMobile());
			}
			if(this.get(i).getCompanyFax() == null || this.get(i).getCompanyFax().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getCompanyFax());
			}
			if(this.get(i).getCompanyPhone() == null || this.get(i).getCompanyPhone().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getCompanyPhone());
			}
			if(this.get(i).getCompanyZipCode() == null || this.get(i).getCompanyZipCode().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getCompanyZipCode());
			}
			if(this.get(i).getCompanyAddress() == null || this.get(i).getCompanyAddress().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getCompanyAddress());
			}
			if(this.get(i).getHomeFax() == null || this.get(i).getHomeFax().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getHomeFax());
			}
			if(this.get(i).getHomePhone() == null || this.get(i).getHomePhone().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getHomePhone());
			}
			if(this.get(i).getHomeZipCode() == null || this.get(i).getHomeZipCode().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getHomeZipCode());
			}
			if(this.get(i).getHomeAddress() == null || this.get(i).getHomeAddress().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getHomeAddress());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getPhone());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getZipCode());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getPostalAddress());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(75,null);
			} else {
				pstmt.setDate(75, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransTime() == null || this.get(i).getTransTime().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getTransTime());
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getTransOperator());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getIDExpDate() == null || this.get(i).getIDExpDate().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getIDExpDate());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsuredB");
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
			tError.moduleName = "LBPOInsuredBDBSet";
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
