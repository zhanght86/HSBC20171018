/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOEdorInsuredListSchema;
import com.sinosoft.lis.vschema.LBPOEdorInsuredListSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOEdorInsuredListDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LBPOEdorInsuredListDBSet extends LBPOEdorInsuredListSet
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
	public LBPOEdorInsuredListDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPOEdorInsuredList");
		mflag = true;
	}

	public LBPOEdorInsuredListDBSet()
	{
		db = new DBOper( "LBPOEdorInsuredList" );
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
			tError.moduleName = "LBPOEdorInsuredListDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOEdorInsuredList WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOEdorInsuredList");
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
			tError.moduleName = "LBPOEdorInsuredListDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPOEdorInsuredList SET  SerialNo = ? , EdorAppNo = ? , GrpContNo = ? , ActivityID = ? , BatchNo = ? , OrderNo = ? , EdorNo = ? , ContNo = ? , InsuredNo = ? , GroupNo = ? , GroupOrderNo = ? , EdorType = ? , InsuredID = ? , InsuredType = ? , InsuredTypeName = ? , NumPeople = ? , RelationToMain = ? , RelationToMainName = ? , MainInsuredID = ? , MainInsuredName = ? , MainInsuredIDNo = ? , OldInsuredName = ? , OldInsuredIDNo = ? , InsuredName = ? , InsuredIDType = ? , InsuredIDTypeName = ? , InsuredIDNo = ? , InsuredGender = ? , InsuredGenderName = ? , InsuredBirthday = ? , EdorValDate = ? , PlanCode = ? , PlanDesc = ? , OccupCode = ? , OccupName = ? , HeadBankCode = ? , HeadBankName = ? , AccName = ? , BankAccNo = ? , BankProvince = ? , BankProvinceName = ? , BankCity = ? , BankCityName = ? , ServerArea = ? , ServerAreaName = ? , Substandard = ? , SubstandardName = ? , SocialInsuFlag = ? , SocialInsuFlagName = ? , Position = ? , PositionName = ? , JoinCompDate = ? , Seniority = ? , Salary = ? , GetYear = ? , GetStartType = ? , GetStartTypeName = ? , InAmount = ? , OutAmount = ? , ExpiryGetMode = ? , ExpiryGetModeName = ? , ExpiryPayMode = ? , ExpiryPayModeName = ? , TransMode = ? , TransModeName = ? , TransAmount = ? , AnnuityGetMode = ? , AnnuityGetModeName = ? , WorkIDNo = ? , IsLongValid = ? , IsLongValidName = ? , IDEndDate = ? , SubCompanyCode = ? , DeptCode = ? , InsureCode = ? , SubCustomerNo = ? , SubCustomerName = ? , WorkAddress = ? , SocialInsuAddress = ? , ZipCode = ? , EMail = ? , Wechat = ? , Phone = ? , Mobile = ? , Province = ? , ProvinceName = ? , City = ? , CityName = ? , County = ? , CountyName = ? , Address = ? , OriginContNo = ? , OriginInsuredNo = ? , State = ? , Reason = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());
			if(this.get(i).getEdorAppNo() == null || this.get(i).getEdorAppNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorAppNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getActivityID());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBatchNo());
			}
			pstmt.setInt(6, this.get(i).getOrderNo());
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEdorNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getInsuredNo());
			}
			pstmt.setInt(10, this.get(i).getGroupNo());
			pstmt.setInt(11, this.get(i).getGroupOrderNo());
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getEdorType());
			}
			if(this.get(i).getInsuredID() == null || this.get(i).getInsuredID().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsuredID());
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getInsuredType());
			}
			if(this.get(i).getInsuredTypeName() == null || this.get(i).getInsuredTypeName().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getInsuredTypeName());
			}
			if(this.get(i).getNumPeople() == null || this.get(i).getNumPeople().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getNumPeople());
			}
			if(this.get(i).getRelationToMain() == null || this.get(i).getRelationToMain().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRelationToMain());
			}
			if(this.get(i).getRelationToMainName() == null || this.get(i).getRelationToMainName().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRelationToMainName());
			}
			if(this.get(i).getMainInsuredID() == null || this.get(i).getMainInsuredID().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getMainInsuredID());
			}
			if(this.get(i).getMainInsuredName() == null || this.get(i).getMainInsuredName().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMainInsuredName());
			}
			if(this.get(i).getMainInsuredIDNo() == null || this.get(i).getMainInsuredIDNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMainInsuredIDNo());
			}
			if(this.get(i).getOldInsuredName() == null || this.get(i).getOldInsuredName().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOldInsuredName());
			}
			if(this.get(i).getOldInsuredIDNo() == null || this.get(i).getOldInsuredIDNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOldInsuredIDNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredIDType() == null || this.get(i).getInsuredIDType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getInsuredIDType());
			}
			if(this.get(i).getInsuredIDTypeName() == null || this.get(i).getInsuredIDTypeName().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getInsuredIDTypeName());
			}
			if(this.get(i).getInsuredIDNo() == null || this.get(i).getInsuredIDNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getInsuredIDNo());
			}
			if(this.get(i).getInsuredGender() == null || this.get(i).getInsuredGender().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getInsuredGender());
			}
			if(this.get(i).getInsuredGenderName() == null || this.get(i).getInsuredGenderName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getInsuredGenderName());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getInsuredBirthday());
			}
			if(this.get(i).getEdorValDate() == null || this.get(i).getEdorValDate().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getEdorValDate());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPlanCode());
			}
			if(this.get(i).getPlanDesc() == null || this.get(i).getPlanDesc().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getPlanDesc());
			}
			if(this.get(i).getOccupCode() == null || this.get(i).getOccupCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOccupCode());
			}
			if(this.get(i).getOccupName() == null || this.get(i).getOccupName().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getOccupName());
			}
			if(this.get(i).getHeadBankCode() == null || this.get(i).getHeadBankCode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getHeadBankCode());
			}
			if(this.get(i).getHeadBankName() == null || this.get(i).getHeadBankName().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getHeadBankName());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAccName());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankProvince() == null || this.get(i).getBankProvince().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getBankProvince());
			}
			if(this.get(i).getBankProvinceName() == null || this.get(i).getBankProvinceName().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getBankProvinceName());
			}
			if(this.get(i).getBankCity() == null || this.get(i).getBankCity().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getBankCity());
			}
			if(this.get(i).getBankCityName() == null || this.get(i).getBankCityName().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getBankCityName());
			}
			if(this.get(i).getServerArea() == null || this.get(i).getServerArea().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getServerArea());
			}
			if(this.get(i).getServerAreaName() == null || this.get(i).getServerAreaName().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getServerAreaName());
			}
			if(this.get(i).getSubstandard() == null || this.get(i).getSubstandard().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getSubstandard());
			}
			if(this.get(i).getSubstandardName() == null || this.get(i).getSubstandardName().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getSubstandardName());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getSocialInsuFlagName() == null || this.get(i).getSocialInsuFlagName().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getSocialInsuFlagName());
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getPosition());
			}
			if(this.get(i).getPositionName() == null || this.get(i).getPositionName().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPositionName());
			}
			if(this.get(i).getJoinCompDate() == null || this.get(i).getJoinCompDate().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getJoinCompDate());
			}
			if(this.get(i).getSeniority() == null || this.get(i).getSeniority().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getSeniority());
			}
			if(this.get(i).getSalary() == null || this.get(i).getSalary().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getSalary());
			}
			if(this.get(i).getGetYear() == null || this.get(i).getGetYear().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getGetYear());
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getGetStartType());
			}
			if(this.get(i).getGetStartTypeName() == null || this.get(i).getGetStartTypeName().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getGetStartTypeName());
			}
			if(this.get(i).getInAmount() == null || this.get(i).getInAmount().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getInAmount());
			}
			if(this.get(i).getOutAmount() == null || this.get(i).getOutAmount().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getOutAmount());
			}
			if(this.get(i).getExpiryGetMode() == null || this.get(i).getExpiryGetMode().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getExpiryGetMode());
			}
			if(this.get(i).getExpiryGetModeName() == null || this.get(i).getExpiryGetModeName().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getExpiryGetModeName());
			}
			if(this.get(i).getExpiryPayMode() == null || this.get(i).getExpiryPayMode().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getExpiryPayMode());
			}
			if(this.get(i).getExpiryPayModeName() == null || this.get(i).getExpiryPayModeName().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getExpiryPayModeName());
			}
			if(this.get(i).getTransMode() == null || this.get(i).getTransMode().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getTransMode());
			}
			if(this.get(i).getTransModeName() == null || this.get(i).getTransModeName().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getTransModeName());
			}
			if(this.get(i).getTransAmount() == null || this.get(i).getTransAmount().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getTransAmount());
			}
			if(this.get(i).getAnnuityGetMode() == null || this.get(i).getAnnuityGetMode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getAnnuityGetMode());
			}
			if(this.get(i).getAnnuityGetModeName() == null || this.get(i).getAnnuityGetModeName().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getAnnuityGetModeName());
			}
			if(this.get(i).getWorkIDNo() == null || this.get(i).getWorkIDNo().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getWorkIDNo());
			}
			if(this.get(i).getIsLongValid() == null || this.get(i).getIsLongValid().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getIsLongValid());
			}
			if(this.get(i).getIsLongValidName() == null || this.get(i).getIsLongValidName().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getIsLongValidName());
			}
			if(this.get(i).getIDEndDate() == null || this.get(i).getIDEndDate().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getIDEndDate());
			}
			if(this.get(i).getSubCompanyCode() == null || this.get(i).getSubCompanyCode().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getSubCompanyCode());
			}
			if(this.get(i).getDeptCode() == null || this.get(i).getDeptCode().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getDeptCode());
			}
			if(this.get(i).getInsureCode() == null || this.get(i).getInsureCode().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getInsureCode());
			}
			if(this.get(i).getSubCustomerNo() == null || this.get(i).getSubCustomerNo().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getSubCustomerNo());
			}
			if(this.get(i).getSubCustomerName() == null || this.get(i).getSubCustomerName().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getSubCustomerName());
			}
			if(this.get(i).getWorkAddress() == null || this.get(i).getWorkAddress().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getWorkAddress());
			}
			if(this.get(i).getSocialInsuAddress() == null || this.get(i).getSocialInsuAddress().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getSocialInsuAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getZipCode());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getEMail());
			}
			if(this.get(i).getWechat() == null || this.get(i).getWechat().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getWechat());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getPhone());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getMobile());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getProvince());
			}
			if(this.get(i).getProvinceName() == null || this.get(i).getProvinceName().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getProvinceName());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getCity());
			}
			if(this.get(i).getCityName() == null || this.get(i).getCityName().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getCityName());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getCounty());
			}
			if(this.get(i).getCountyName() == null || this.get(i).getCountyName().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getCountyName());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getAddress());
			}
			if(this.get(i).getOriginContNo() == null || this.get(i).getOriginContNo().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getOriginContNo());
			}
			if(this.get(i).getOriginInsuredNo() == null || this.get(i).getOriginInsuredNo().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getOriginInsuredNo());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getState());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getReason());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(99,null);
			} else {
				pstmt.setDate(99, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(102,null);
			} else {
				pstmt.setDate(102, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getModifyTime());
			}
			// set where condition
			pstmt.setInt(104, this.get(i).getSerialNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOEdorInsuredList");
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
			tError.moduleName = "LBPOEdorInsuredListDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPOEdorInsuredList VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());
			if(this.get(i).getEdorAppNo() == null || this.get(i).getEdorAppNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorAppNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getActivityID());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBatchNo());
			}
			pstmt.setInt(6, this.get(i).getOrderNo());
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEdorNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getInsuredNo());
			}
			pstmt.setInt(10, this.get(i).getGroupNo());
			pstmt.setInt(11, this.get(i).getGroupOrderNo());
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getEdorType());
			}
			if(this.get(i).getInsuredID() == null || this.get(i).getInsuredID().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsuredID());
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getInsuredType());
			}
			if(this.get(i).getInsuredTypeName() == null || this.get(i).getInsuredTypeName().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getInsuredTypeName());
			}
			if(this.get(i).getNumPeople() == null || this.get(i).getNumPeople().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getNumPeople());
			}
			if(this.get(i).getRelationToMain() == null || this.get(i).getRelationToMain().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRelationToMain());
			}
			if(this.get(i).getRelationToMainName() == null || this.get(i).getRelationToMainName().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRelationToMainName());
			}
			if(this.get(i).getMainInsuredID() == null || this.get(i).getMainInsuredID().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getMainInsuredID());
			}
			if(this.get(i).getMainInsuredName() == null || this.get(i).getMainInsuredName().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMainInsuredName());
			}
			if(this.get(i).getMainInsuredIDNo() == null || this.get(i).getMainInsuredIDNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMainInsuredIDNo());
			}
			if(this.get(i).getOldInsuredName() == null || this.get(i).getOldInsuredName().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOldInsuredName());
			}
			if(this.get(i).getOldInsuredIDNo() == null || this.get(i).getOldInsuredIDNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOldInsuredIDNo());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getInsuredName());
			}
			if(this.get(i).getInsuredIDType() == null || this.get(i).getInsuredIDType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getInsuredIDType());
			}
			if(this.get(i).getInsuredIDTypeName() == null || this.get(i).getInsuredIDTypeName().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getInsuredIDTypeName());
			}
			if(this.get(i).getInsuredIDNo() == null || this.get(i).getInsuredIDNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getInsuredIDNo());
			}
			if(this.get(i).getInsuredGender() == null || this.get(i).getInsuredGender().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getInsuredGender());
			}
			if(this.get(i).getInsuredGenderName() == null || this.get(i).getInsuredGenderName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getInsuredGenderName());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getInsuredBirthday());
			}
			if(this.get(i).getEdorValDate() == null || this.get(i).getEdorValDate().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getEdorValDate());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPlanCode());
			}
			if(this.get(i).getPlanDesc() == null || this.get(i).getPlanDesc().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getPlanDesc());
			}
			if(this.get(i).getOccupCode() == null || this.get(i).getOccupCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOccupCode());
			}
			if(this.get(i).getOccupName() == null || this.get(i).getOccupName().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getOccupName());
			}
			if(this.get(i).getHeadBankCode() == null || this.get(i).getHeadBankCode().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getHeadBankCode());
			}
			if(this.get(i).getHeadBankName() == null || this.get(i).getHeadBankName().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getHeadBankName());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAccName());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankProvince() == null || this.get(i).getBankProvince().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getBankProvince());
			}
			if(this.get(i).getBankProvinceName() == null || this.get(i).getBankProvinceName().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getBankProvinceName());
			}
			if(this.get(i).getBankCity() == null || this.get(i).getBankCity().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getBankCity());
			}
			if(this.get(i).getBankCityName() == null || this.get(i).getBankCityName().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getBankCityName());
			}
			if(this.get(i).getServerArea() == null || this.get(i).getServerArea().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getServerArea());
			}
			if(this.get(i).getServerAreaName() == null || this.get(i).getServerAreaName().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getServerAreaName());
			}
			if(this.get(i).getSubstandard() == null || this.get(i).getSubstandard().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getSubstandard());
			}
			if(this.get(i).getSubstandardName() == null || this.get(i).getSubstandardName().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getSubstandardName());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getSocialInsuFlagName() == null || this.get(i).getSocialInsuFlagName().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getSocialInsuFlagName());
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getPosition());
			}
			if(this.get(i).getPositionName() == null || this.get(i).getPositionName().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPositionName());
			}
			if(this.get(i).getJoinCompDate() == null || this.get(i).getJoinCompDate().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getJoinCompDate());
			}
			if(this.get(i).getSeniority() == null || this.get(i).getSeniority().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getSeniority());
			}
			if(this.get(i).getSalary() == null || this.get(i).getSalary().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getSalary());
			}
			if(this.get(i).getGetYear() == null || this.get(i).getGetYear().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getGetYear());
			}
			if(this.get(i).getGetStartType() == null || this.get(i).getGetStartType().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getGetStartType());
			}
			if(this.get(i).getGetStartTypeName() == null || this.get(i).getGetStartTypeName().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getGetStartTypeName());
			}
			if(this.get(i).getInAmount() == null || this.get(i).getInAmount().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getInAmount());
			}
			if(this.get(i).getOutAmount() == null || this.get(i).getOutAmount().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getOutAmount());
			}
			if(this.get(i).getExpiryGetMode() == null || this.get(i).getExpiryGetMode().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getExpiryGetMode());
			}
			if(this.get(i).getExpiryGetModeName() == null || this.get(i).getExpiryGetModeName().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getExpiryGetModeName());
			}
			if(this.get(i).getExpiryPayMode() == null || this.get(i).getExpiryPayMode().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getExpiryPayMode());
			}
			if(this.get(i).getExpiryPayModeName() == null || this.get(i).getExpiryPayModeName().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getExpiryPayModeName());
			}
			if(this.get(i).getTransMode() == null || this.get(i).getTransMode().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getTransMode());
			}
			if(this.get(i).getTransModeName() == null || this.get(i).getTransModeName().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getTransModeName());
			}
			if(this.get(i).getTransAmount() == null || this.get(i).getTransAmount().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getTransAmount());
			}
			if(this.get(i).getAnnuityGetMode() == null || this.get(i).getAnnuityGetMode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getAnnuityGetMode());
			}
			if(this.get(i).getAnnuityGetModeName() == null || this.get(i).getAnnuityGetModeName().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getAnnuityGetModeName());
			}
			if(this.get(i).getWorkIDNo() == null || this.get(i).getWorkIDNo().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getWorkIDNo());
			}
			if(this.get(i).getIsLongValid() == null || this.get(i).getIsLongValid().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getIsLongValid());
			}
			if(this.get(i).getIsLongValidName() == null || this.get(i).getIsLongValidName().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getIsLongValidName());
			}
			if(this.get(i).getIDEndDate() == null || this.get(i).getIDEndDate().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getIDEndDate());
			}
			if(this.get(i).getSubCompanyCode() == null || this.get(i).getSubCompanyCode().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getSubCompanyCode());
			}
			if(this.get(i).getDeptCode() == null || this.get(i).getDeptCode().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getDeptCode());
			}
			if(this.get(i).getInsureCode() == null || this.get(i).getInsureCode().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getInsureCode());
			}
			if(this.get(i).getSubCustomerNo() == null || this.get(i).getSubCustomerNo().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getSubCustomerNo());
			}
			if(this.get(i).getSubCustomerName() == null || this.get(i).getSubCustomerName().equals("null")) {
				pstmt.setString(77,null);
			} else {
				pstmt.setString(77, this.get(i).getSubCustomerName());
			}
			if(this.get(i).getWorkAddress() == null || this.get(i).getWorkAddress().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getWorkAddress());
			}
			if(this.get(i).getSocialInsuAddress() == null || this.get(i).getSocialInsuAddress().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getSocialInsuAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getZipCode());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getEMail());
			}
			if(this.get(i).getWechat() == null || this.get(i).getWechat().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getWechat());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getPhone());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getMobile());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(85,null);
			} else {
				pstmt.setString(85, this.get(i).getProvince());
			}
			if(this.get(i).getProvinceName() == null || this.get(i).getProvinceName().equals("null")) {
				pstmt.setString(86,null);
			} else {
				pstmt.setString(86, this.get(i).getProvinceName());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(87,null);
			} else {
				pstmt.setString(87, this.get(i).getCity());
			}
			if(this.get(i).getCityName() == null || this.get(i).getCityName().equals("null")) {
				pstmt.setString(88,null);
			} else {
				pstmt.setString(88, this.get(i).getCityName());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(89,null);
			} else {
				pstmt.setString(89, this.get(i).getCounty());
			}
			if(this.get(i).getCountyName() == null || this.get(i).getCountyName().equals("null")) {
				pstmt.setString(90,null);
			} else {
				pstmt.setString(90, this.get(i).getCountyName());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(91,null);
			} else {
				pstmt.setString(91, this.get(i).getAddress());
			}
			if(this.get(i).getOriginContNo() == null || this.get(i).getOriginContNo().equals("null")) {
				pstmt.setString(92,null);
			} else {
				pstmt.setString(92, this.get(i).getOriginContNo());
			}
			if(this.get(i).getOriginInsuredNo() == null || this.get(i).getOriginInsuredNo().equals("null")) {
				pstmt.setString(93,null);
			} else {
				pstmt.setString(93, this.get(i).getOriginInsuredNo());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(94,null);
			} else {
				pstmt.setString(94, this.get(i).getState());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(95,null);
			} else {
				pstmt.setString(95, this.get(i).getReason());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(96,null);
			} else {
				pstmt.setString(96, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(97,null);
			} else {
				pstmt.setString(97, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(98,null);
			} else {
				pstmt.setString(98, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(99,null);
			} else {
				pstmt.setDate(99, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(100,null);
			} else {
				pstmt.setString(100, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(101,null);
			} else {
				pstmt.setString(101, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(102,null);
			} else {
				pstmt.setDate(102, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(103,null);
			} else {
				pstmt.setString(103, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOEdorInsuredList");
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
			tError.moduleName = "LBPOEdorInsuredListDBSet";
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
