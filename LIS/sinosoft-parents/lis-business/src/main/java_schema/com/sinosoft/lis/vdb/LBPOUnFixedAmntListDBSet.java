/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOUnFixedAmntListSchema;
import com.sinosoft.lis.vschema.LBPOUnFixedAmntListSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOUnFixedAmntListDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LBPOUnFixedAmntListDBSet extends LBPOUnFixedAmntListSet
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
	public LBPOUnFixedAmntListDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPOUnFixedAmntList");
		mflag = true;
	}

	public LBPOUnFixedAmntListDBSet()
	{
		db = new DBOper( "LBPOUnFixedAmntList" );
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
			tError.moduleName = "LBPOUnFixedAmntListDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOUnFixedAmntList WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
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
			tError.moduleName = "LBPOUnFixedAmntListDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPOUnFixedAmntList SET  SerialNo = ? , GrpPropNo = ? , ActivityID = ? , BatchNo = ? , OrderNo = ? , InsuredID = ? , InsuredType = ? , InsuredTypeName = ? , NumPeople = ? , RelationToMain = ? , RelationToMainName = ? , MainInsuredID = ? , InsuredName = ? , IDType = ? , IDTypeName = ? , IDNo = ? , Gender = ? , GenderName = ? , Birthday = ? , PlanCode = ? , PlanDesc = ? , RiskCode = ? , DutyCode = ? , Amnt = ? , OccupCode = ? , OccupName = ? , HeadBankCode = ? , HeadBankName = ? , AccName = ? , BankAccNo = ? , BankProvince = ? , BankProvinceName = ? , BankCity = ? , BankCityName = ? , ServerArea = ? , ServerAreaName = ? , Substandard = ? , SubstandardName = ? , SocialInsuFlag = ? , SocialInsuFlagName = ? , Position = ? , PositionName = ? , JoinCompDate = ? , Seniority = ? , Salary = ? , WorkIDNo = ? , IsLongValid = ? , IsLongValidName = ? , IDEndDate = ? , SubCompanyCode = ? , DeptCode = ? , InsureCode = ? , SubCustomerNo = ? , SubCustomerName = ? , WorkAddress = ? , SocialInsuAddress = ? , ZipCode = ? , EMail = ? , Wechat = ? , Phone = ? , Mobile = ? , Province = ? , ProvinceName = ? , City = ? , CityName = ? , County = ? , CountyName = ? , Address = ? , State = ? , Reason = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getActivityID());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatchNo());
			}
			pstmt.setInt(5, this.get(i).getOrderNo());
			if(this.get(i).getInsuredID() == null || this.get(i).getInsuredID().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredID());
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getInsuredType());
			}
			if(this.get(i).getInsuredTypeName() == null || this.get(i).getInsuredTypeName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsuredTypeName());
			}
			if(this.get(i).getNumPeople() == null || this.get(i).getNumPeople().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getNumPeople());
			}
			if(this.get(i).getRelationToMain() == null || this.get(i).getRelationToMain().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRelationToMain());
			}
			if(this.get(i).getRelationToMainName() == null || this.get(i).getRelationToMainName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRelationToMainName());
			}
			if(this.get(i).getMainInsuredID() == null || this.get(i).getMainInsuredID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMainInsuredID());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsuredName());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIDType());
			}
			if(this.get(i).getIDTypeName() == null || this.get(i).getIDTypeName().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getIDTypeName());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIDNo());
			}
			if(this.get(i).getGender() == null || this.get(i).getGender().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGender());
			}
			if(this.get(i).getGenderName() == null || this.get(i).getGenderName().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGenderName());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBirthday());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPlanCode());
			}
			if(this.get(i).getPlanDesc() == null || this.get(i).getPlanDesc().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPlanDesc());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getDutyCode());
			}
			if(this.get(i).getAmnt() == null || this.get(i).getAmnt().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAmnt());
			}
			if(this.get(i).getOccupCode() == null || this.get(i).getOccupCode().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOccupCode());
			}
			if(this.get(i).getOccupName() == null || this.get(i).getOccupName().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getOccupName());
			}
			if(this.get(i).getHeadBankCode() == null || this.get(i).getHeadBankCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getHeadBankCode());
			}
			if(this.get(i).getHeadBankName() == null || this.get(i).getHeadBankName().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getHeadBankName());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAccName());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankProvince() == null || this.get(i).getBankProvince().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankProvince());
			}
			if(this.get(i).getBankProvinceName() == null || this.get(i).getBankProvinceName().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBankProvinceName());
			}
			if(this.get(i).getBankCity() == null || this.get(i).getBankCity().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBankCity());
			}
			if(this.get(i).getBankCityName() == null || this.get(i).getBankCityName().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBankCityName());
			}
			if(this.get(i).getServerArea() == null || this.get(i).getServerArea().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getServerArea());
			}
			if(this.get(i).getServerAreaName() == null || this.get(i).getServerAreaName().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getServerAreaName());
			}
			if(this.get(i).getSubstandard() == null || this.get(i).getSubstandard().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSubstandard());
			}
			if(this.get(i).getSubstandardName() == null || this.get(i).getSubstandardName().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSubstandardName());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getSocialInsuFlagName() == null || this.get(i).getSocialInsuFlagName().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getSocialInsuFlagName());
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPosition());
			}
			if(this.get(i).getPositionName() == null || this.get(i).getPositionName().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPositionName());
			}
			if(this.get(i).getJoinCompDate() == null || this.get(i).getJoinCompDate().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getJoinCompDate());
			}
			if(this.get(i).getSeniority() == null || this.get(i).getSeniority().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getSeniority());
			}
			if(this.get(i).getSalary() == null || this.get(i).getSalary().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getSalary());
			}
			if(this.get(i).getWorkIDNo() == null || this.get(i).getWorkIDNo().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getWorkIDNo());
			}
			if(this.get(i).getIsLongValid() == null || this.get(i).getIsLongValid().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getIsLongValid());
			}
			if(this.get(i).getIsLongValidName() == null || this.get(i).getIsLongValidName().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getIsLongValidName());
			}
			if(this.get(i).getIDEndDate() == null || this.get(i).getIDEndDate().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getIDEndDate());
			}
			if(this.get(i).getSubCompanyCode() == null || this.get(i).getSubCompanyCode().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSubCompanyCode());
			}
			if(this.get(i).getDeptCode() == null || this.get(i).getDeptCode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getDeptCode());
			}
			if(this.get(i).getInsureCode() == null || this.get(i).getInsureCode().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getInsureCode());
			}
			if(this.get(i).getSubCustomerNo() == null || this.get(i).getSubCustomerNo().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getSubCustomerNo());
			}
			if(this.get(i).getSubCustomerName() == null || this.get(i).getSubCustomerName().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getSubCustomerName());
			}
			if(this.get(i).getWorkAddress() == null || this.get(i).getWorkAddress().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getWorkAddress());
			}
			if(this.get(i).getSocialInsuAddress() == null || this.get(i).getSocialInsuAddress().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSocialInsuAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getZipCode());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getEMail());
			}
			if(this.get(i).getWechat() == null || this.get(i).getWechat().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getWechat());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getPhone());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getMobile());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getProvince());
			}
			if(this.get(i).getProvinceName() == null || this.get(i).getProvinceName().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getProvinceName());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getCity());
			}
			if(this.get(i).getCityName() == null || this.get(i).getCityName().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getCityName());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getCounty());
			}
			if(this.get(i).getCountyName() == null || this.get(i).getCountyName().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getCountyName());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getAddress());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getState());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getReason());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(77,null);
			} else {
				pstmt.setDate(77, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getModifyTime());
			}
			// set where condition
			pstmt.setInt(79, this.get(i).getSerialNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
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
			tError.moduleName = "LBPOUnFixedAmntListDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPOUnFixedAmntList VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getSerialNo());
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getActivityID() == null || this.get(i).getActivityID().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getActivityID());
			}
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatchNo());
			}
			pstmt.setInt(5, this.get(i).getOrderNo());
			if(this.get(i).getInsuredID() == null || this.get(i).getInsuredID().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredID());
			}
			if(this.get(i).getInsuredType() == null || this.get(i).getInsuredType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getInsuredType());
			}
			if(this.get(i).getInsuredTypeName() == null || this.get(i).getInsuredTypeName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsuredTypeName());
			}
			if(this.get(i).getNumPeople() == null || this.get(i).getNumPeople().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getNumPeople());
			}
			if(this.get(i).getRelationToMain() == null || this.get(i).getRelationToMain().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRelationToMain());
			}
			if(this.get(i).getRelationToMainName() == null || this.get(i).getRelationToMainName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRelationToMainName());
			}
			if(this.get(i).getMainInsuredID() == null || this.get(i).getMainInsuredID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMainInsuredID());
			}
			if(this.get(i).getInsuredName() == null || this.get(i).getInsuredName().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInsuredName());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIDType());
			}
			if(this.get(i).getIDTypeName() == null || this.get(i).getIDTypeName().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getIDTypeName());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIDNo());
			}
			if(this.get(i).getGender() == null || this.get(i).getGender().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGender());
			}
			if(this.get(i).getGenderName() == null || this.get(i).getGenderName().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGenderName());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBirthday());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPlanCode());
			}
			if(this.get(i).getPlanDesc() == null || this.get(i).getPlanDesc().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPlanDesc());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getDutyCode());
			}
			if(this.get(i).getAmnt() == null || this.get(i).getAmnt().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAmnt());
			}
			if(this.get(i).getOccupCode() == null || this.get(i).getOccupCode().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOccupCode());
			}
			if(this.get(i).getOccupName() == null || this.get(i).getOccupName().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getOccupName());
			}
			if(this.get(i).getHeadBankCode() == null || this.get(i).getHeadBankCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getHeadBankCode());
			}
			if(this.get(i).getHeadBankName() == null || this.get(i).getHeadBankName().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getHeadBankName());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAccName());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBankAccNo());
			}
			if(this.get(i).getBankProvince() == null || this.get(i).getBankProvince().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankProvince());
			}
			if(this.get(i).getBankProvinceName() == null || this.get(i).getBankProvinceName().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBankProvinceName());
			}
			if(this.get(i).getBankCity() == null || this.get(i).getBankCity().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBankCity());
			}
			if(this.get(i).getBankCityName() == null || this.get(i).getBankCityName().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBankCityName());
			}
			if(this.get(i).getServerArea() == null || this.get(i).getServerArea().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getServerArea());
			}
			if(this.get(i).getServerAreaName() == null || this.get(i).getServerAreaName().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getServerAreaName());
			}
			if(this.get(i).getSubstandard() == null || this.get(i).getSubstandard().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSubstandard());
			}
			if(this.get(i).getSubstandardName() == null || this.get(i).getSubstandardName().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSubstandardName());
			}
			if(this.get(i).getSocialInsuFlag() == null || this.get(i).getSocialInsuFlag().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSocialInsuFlag());
			}
			if(this.get(i).getSocialInsuFlagName() == null || this.get(i).getSocialInsuFlagName().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getSocialInsuFlagName());
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPosition());
			}
			if(this.get(i).getPositionName() == null || this.get(i).getPositionName().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPositionName());
			}
			if(this.get(i).getJoinCompDate() == null || this.get(i).getJoinCompDate().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getJoinCompDate());
			}
			if(this.get(i).getSeniority() == null || this.get(i).getSeniority().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getSeniority());
			}
			if(this.get(i).getSalary() == null || this.get(i).getSalary().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getSalary());
			}
			if(this.get(i).getWorkIDNo() == null || this.get(i).getWorkIDNo().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getWorkIDNo());
			}
			if(this.get(i).getIsLongValid() == null || this.get(i).getIsLongValid().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getIsLongValid());
			}
			if(this.get(i).getIsLongValidName() == null || this.get(i).getIsLongValidName().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getIsLongValidName());
			}
			if(this.get(i).getIDEndDate() == null || this.get(i).getIDEndDate().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getIDEndDate());
			}
			if(this.get(i).getSubCompanyCode() == null || this.get(i).getSubCompanyCode().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getSubCompanyCode());
			}
			if(this.get(i).getDeptCode() == null || this.get(i).getDeptCode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getDeptCode());
			}
			if(this.get(i).getInsureCode() == null || this.get(i).getInsureCode().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getInsureCode());
			}
			if(this.get(i).getSubCustomerNo() == null || this.get(i).getSubCustomerNo().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getSubCustomerNo());
			}
			if(this.get(i).getSubCustomerName() == null || this.get(i).getSubCustomerName().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getSubCustomerName());
			}
			if(this.get(i).getWorkAddress() == null || this.get(i).getWorkAddress().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getWorkAddress());
			}
			if(this.get(i).getSocialInsuAddress() == null || this.get(i).getSocialInsuAddress().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSocialInsuAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getZipCode());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getEMail());
			}
			if(this.get(i).getWechat() == null || this.get(i).getWechat().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getWechat());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getPhone());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getMobile());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getProvince());
			}
			if(this.get(i).getProvinceName() == null || this.get(i).getProvinceName().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getProvinceName());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getCity());
			}
			if(this.get(i).getCityName() == null || this.get(i).getCityName().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getCityName());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getCounty());
			}
			if(this.get(i).getCountyName() == null || this.get(i).getCountyName().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getCountyName());
			}
			if(this.get(i).getAddress() == null || this.get(i).getAddress().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getAddress());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getState());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getReason());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(74,null);
			} else {
				pstmt.setDate(74, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(77,null);
			} else {
				pstmt.setDate(77, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
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
			tError.moduleName = "LBPOUnFixedAmntListDBSet";
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
