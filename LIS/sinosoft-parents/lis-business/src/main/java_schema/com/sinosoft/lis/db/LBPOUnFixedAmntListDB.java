/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LBPOUnFixedAmntListSchema;
import com.sinosoft.lis.vschema.LBPOUnFixedAmntListSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOUnFixedAmntListDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LBPOUnFixedAmntListDB extends LBPOUnFixedAmntListSchema
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
	public LBPOUnFixedAmntListDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBPOUnFixedAmntList" );
		mflag = true;
	}

	public LBPOUnFixedAmntListDB()
	{
		con = null;
		db = new DBOper( "LBPOUnFixedAmntList" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBPOUnFixedAmntListSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBPOUnFixedAmntListSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOUnFixedAmntList WHERE  SerialNo = ?");
			pstmt.setInt(1, this.getSerialNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
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
		SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBPOUnFixedAmntList SET  SerialNo = ? , GrpPropNo = ? , ActivityID = ? , BatchNo = ? , OrderNo = ? , InsuredID = ? , InsuredType = ? , InsuredTypeName = ? , NumPeople = ? , RelationToMain = ? , RelationToMainName = ? , MainInsuredID = ? , InsuredName = ? , IDType = ? , IDTypeName = ? , IDNo = ? , Gender = ? , GenderName = ? , Birthday = ? , PlanCode = ? , PlanDesc = ? , RiskCode = ? , DutyCode = ? , Amnt = ? , OccupCode = ? , OccupName = ? , HeadBankCode = ? , HeadBankName = ? , AccName = ? , BankAccNo = ? , BankProvince = ? , BankProvinceName = ? , BankCity = ? , BankCityName = ? , ServerArea = ? , ServerAreaName = ? , Substandard = ? , SubstandardName = ? , SocialInsuFlag = ? , SocialInsuFlagName = ? , Position = ? , PositionName = ? , JoinCompDate = ? , Seniority = ? , Salary = ? , WorkIDNo = ? , IsLongValid = ? , IsLongValidName = ? , IDEndDate = ? , SubCompanyCode = ? , DeptCode = ? , InsureCode = ? , SubCustomerNo = ? , SubCustomerName = ? , WorkAddress = ? , SocialInsuAddress = ? , ZipCode = ? , EMail = ? , Wechat = ? , Phone = ? , Mobile = ? , Province = ? , ProvinceName = ? , City = ? , CityName = ? , County = ? , CountyName = ? , Address = ? , State = ? , Reason = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  SerialNo = ?");
			pstmt.setInt(1, this.getSerialNo());
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPropNo());
			}
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getActivityID());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatchNo());
			}
			pstmt.setInt(5, this.getOrderNo());
			if(this.getInsuredID() == null || this.getInsuredID().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredID());
			}
			if(this.getInsuredType() == null || this.getInsuredType().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getInsuredType());
			}
			if(this.getInsuredTypeName() == null || this.getInsuredTypeName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getInsuredTypeName());
			}
			if(this.getNumPeople() == null || this.getNumPeople().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getNumPeople());
			}
			if(this.getRelationToMain() == null || this.getRelationToMain().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRelationToMain());
			}
			if(this.getRelationToMainName() == null || this.getRelationToMainName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRelationToMainName());
			}
			if(this.getMainInsuredID() == null || this.getMainInsuredID().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getMainInsuredID());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getInsuredName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getIDType());
			}
			if(this.getIDTypeName() == null || this.getIDTypeName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getIDTypeName());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getIDNo());
			}
			if(this.getGender() == null || this.getGender().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getGender());
			}
			if(this.getGenderName() == null || this.getGenderName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGenderName());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBirthday());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPlanCode());
			}
			if(this.getPlanDesc() == null || this.getPlanDesc().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPlanDesc());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getDutyCode());
			}
			if(this.getAmnt() == null || this.getAmnt().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAmnt());
			}
			if(this.getOccupCode() == null || this.getOccupCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOccupCode());
			}
			if(this.getOccupName() == null || this.getOccupName().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOccupName());
			}
			if(this.getHeadBankCode() == null || this.getHeadBankCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getHeadBankCode());
			}
			if(this.getHeadBankName() == null || this.getHeadBankName().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getHeadBankName());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getAccName());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getBankAccNo());
			}
			if(this.getBankProvince() == null || this.getBankProvince().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankProvince());
			}
			if(this.getBankProvinceName() == null || this.getBankProvinceName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBankProvinceName());
			}
			if(this.getBankCity() == null || this.getBankCity().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBankCity());
			}
			if(this.getBankCityName() == null || this.getBankCityName().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getBankCityName());
			}
			if(this.getServerArea() == null || this.getServerArea().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getServerArea());
			}
			if(this.getServerAreaName() == null || this.getServerAreaName().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getServerAreaName());
			}
			if(this.getSubstandard() == null || this.getSubstandard().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getSubstandard());
			}
			if(this.getSubstandardName() == null || this.getSubstandardName().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getSubstandardName());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSocialInsuFlag());
			}
			if(this.getSocialInsuFlagName() == null || this.getSocialInsuFlagName().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getSocialInsuFlagName());
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getPosition());
			}
			if(this.getPositionName() == null || this.getPositionName().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getPositionName());
			}
			if(this.getJoinCompDate() == null || this.getJoinCompDate().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getJoinCompDate());
			}
			if(this.getSeniority() == null || this.getSeniority().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getSeniority());
			}
			if(this.getSalary() == null || this.getSalary().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getSalary());
			}
			if(this.getWorkIDNo() == null || this.getWorkIDNo().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getWorkIDNo());
			}
			if(this.getIsLongValid() == null || this.getIsLongValid().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getIsLongValid());
			}
			if(this.getIsLongValidName() == null || this.getIsLongValidName().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getIsLongValidName());
			}
			if(this.getIDEndDate() == null || this.getIDEndDate().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getIDEndDate());
			}
			if(this.getSubCompanyCode() == null || this.getSubCompanyCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getSubCompanyCode());
			}
			if(this.getDeptCode() == null || this.getDeptCode().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getDeptCode());
			}
			if(this.getInsureCode() == null || this.getInsureCode().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getInsureCode());
			}
			if(this.getSubCustomerNo() == null || this.getSubCustomerNo().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getSubCustomerNo());
			}
			if(this.getSubCustomerName() == null || this.getSubCustomerName().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getSubCustomerName());
			}
			if(this.getWorkAddress() == null || this.getWorkAddress().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getWorkAddress());
			}
			if(this.getSocialInsuAddress() == null || this.getSocialInsuAddress().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getSocialInsuAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getZipCode());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getEMail());
			}
			if(this.getWechat() == null || this.getWechat().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getWechat());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getPhone());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getMobile());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getProvince());
			}
			if(this.getProvinceName() == null || this.getProvinceName().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getProvinceName());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getCity());
			}
			if(this.getCityName() == null || this.getCityName().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getCityName());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getCounty());
			}
			if(this.getCountyName() == null || this.getCountyName().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getCountyName());
			}
			if(this.getAddress() == null || this.getAddress().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getAddress());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getState());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getReason());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(77, 91);
			} else {
				pstmt.setDate(77, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getModifyTime());
			}
			// set where condition
			pstmt.setInt(79, this.getSerialNo());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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
		SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBPOUnFixedAmntList VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			pstmt.setInt(1, this.getSerialNo());
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPropNo());
			}
			if(this.getActivityID() == null || this.getActivityID().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getActivityID());
			}
			if(this.getBatchNo() == null || this.getBatchNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatchNo());
			}
			pstmt.setInt(5, this.getOrderNo());
			if(this.getInsuredID() == null || this.getInsuredID().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredID());
			}
			if(this.getInsuredType() == null || this.getInsuredType().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getInsuredType());
			}
			if(this.getInsuredTypeName() == null || this.getInsuredTypeName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getInsuredTypeName());
			}
			if(this.getNumPeople() == null || this.getNumPeople().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getNumPeople());
			}
			if(this.getRelationToMain() == null || this.getRelationToMain().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRelationToMain());
			}
			if(this.getRelationToMainName() == null || this.getRelationToMainName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getRelationToMainName());
			}
			if(this.getMainInsuredID() == null || this.getMainInsuredID().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getMainInsuredID());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getInsuredName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getIDType());
			}
			if(this.getIDTypeName() == null || this.getIDTypeName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getIDTypeName());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getIDNo());
			}
			if(this.getGender() == null || this.getGender().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getGender());
			}
			if(this.getGenderName() == null || this.getGenderName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGenderName());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBirthday());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPlanCode());
			}
			if(this.getPlanDesc() == null || this.getPlanDesc().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPlanDesc());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getDutyCode());
			}
			if(this.getAmnt() == null || this.getAmnt().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getAmnt());
			}
			if(this.getOccupCode() == null || this.getOccupCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getOccupCode());
			}
			if(this.getOccupName() == null || this.getOccupName().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOccupName());
			}
			if(this.getHeadBankCode() == null || this.getHeadBankCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getHeadBankCode());
			}
			if(this.getHeadBankName() == null || this.getHeadBankName().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getHeadBankName());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getAccName());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getBankAccNo());
			}
			if(this.getBankProvince() == null || this.getBankProvince().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankProvince());
			}
			if(this.getBankProvinceName() == null || this.getBankProvinceName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBankProvinceName());
			}
			if(this.getBankCity() == null || this.getBankCity().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBankCity());
			}
			if(this.getBankCityName() == null || this.getBankCityName().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getBankCityName());
			}
			if(this.getServerArea() == null || this.getServerArea().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getServerArea());
			}
			if(this.getServerAreaName() == null || this.getServerAreaName().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getServerAreaName());
			}
			if(this.getSubstandard() == null || this.getSubstandard().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getSubstandard());
			}
			if(this.getSubstandardName() == null || this.getSubstandardName().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getSubstandardName());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSocialInsuFlag());
			}
			if(this.getSocialInsuFlagName() == null || this.getSocialInsuFlagName().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getSocialInsuFlagName());
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getPosition());
			}
			if(this.getPositionName() == null || this.getPositionName().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getPositionName());
			}
			if(this.getJoinCompDate() == null || this.getJoinCompDate().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getJoinCompDate());
			}
			if(this.getSeniority() == null || this.getSeniority().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getSeniority());
			}
			if(this.getSalary() == null || this.getSalary().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getSalary());
			}
			if(this.getWorkIDNo() == null || this.getWorkIDNo().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getWorkIDNo());
			}
			if(this.getIsLongValid() == null || this.getIsLongValid().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getIsLongValid());
			}
			if(this.getIsLongValidName() == null || this.getIsLongValidName().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getIsLongValidName());
			}
			if(this.getIDEndDate() == null || this.getIDEndDate().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getIDEndDate());
			}
			if(this.getSubCompanyCode() == null || this.getSubCompanyCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getSubCompanyCode());
			}
			if(this.getDeptCode() == null || this.getDeptCode().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getDeptCode());
			}
			if(this.getInsureCode() == null || this.getInsureCode().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getInsureCode());
			}
			if(this.getSubCustomerNo() == null || this.getSubCustomerNo().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getSubCustomerNo());
			}
			if(this.getSubCustomerName() == null || this.getSubCustomerName().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getSubCustomerName());
			}
			if(this.getWorkAddress() == null || this.getWorkAddress().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getWorkAddress());
			}
			if(this.getSocialInsuAddress() == null || this.getSocialInsuAddress().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getSocialInsuAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getZipCode());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getEMail());
			}
			if(this.getWechat() == null || this.getWechat().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getWechat());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getPhone());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getMobile());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getProvince());
			}
			if(this.getProvinceName() == null || this.getProvinceName().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getProvinceName());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getCity());
			}
			if(this.getCityName() == null || this.getCityName().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getCityName());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getCounty());
			}
			if(this.getCountyName() == null || this.getCountyName().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getCountyName());
			}
			if(this.getAddress() == null || this.getAddress().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getAddress());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getState());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getReason());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(74, 91);
			} else {
				pstmt.setDate(74, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(77, 91);
			} else {
				pstmt.setDate(77, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBPOUnFixedAmntList WHERE  SerialNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, this.getSerialNo());
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOUnFixedAmntListDB";
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
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

	public LBPOUnFixedAmntListSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOUnFixedAmntListSet aLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
			LBPOUnFixedAmntListSchema aSchema = this.getSchema();
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
				LBPOUnFixedAmntListSchema s1 = new LBPOUnFixedAmntListSchema();
				s1.setSchema(rs,i);
				aLBPOUnFixedAmntListSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

		return aLBPOUnFixedAmntListSet;
	}

	public LBPOUnFixedAmntListSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOUnFixedAmntListSet aLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();

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
				LBPOUnFixedAmntListSchema s1 = new LBPOUnFixedAmntListSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOUnFixedAmntListDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOUnFixedAmntListSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

		return aLBPOUnFixedAmntListSet;
	}

	public LBPOUnFixedAmntListSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOUnFixedAmntListSet aLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
			LBPOUnFixedAmntListSchema aSchema = this.getSchema();
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

				LBPOUnFixedAmntListSchema s1 = new LBPOUnFixedAmntListSchema();
				s1.setSchema(rs,i);
				aLBPOUnFixedAmntListSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

		return aLBPOUnFixedAmntListSet;
	}

	public LBPOUnFixedAmntListSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOUnFixedAmntListSet aLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();

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

				LBPOUnFixedAmntListSchema s1 = new LBPOUnFixedAmntListSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOUnFixedAmntListDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOUnFixedAmntListSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

		return aLBPOUnFixedAmntListSet;
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
			SQLString sqlObj = new SQLString("LBPOUnFixedAmntList");
			LBPOUnFixedAmntListSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBPOUnFixedAmntList " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBPOUnFixedAmntListDB";
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
			tError.moduleName = "LBPOUnFixedAmntListDB";
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
        tError.moduleName = "LBPOUnFixedAmntListDB";
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
        tError.moduleName = "LBPOUnFixedAmntListDB";
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
        tError.moduleName = "LBPOUnFixedAmntListDB";
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
        tError.moduleName = "LBPOUnFixedAmntListDB";
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
 * @return LBPOUnFixedAmntListSet
 */
public LBPOUnFixedAmntListSet getData()
{
    int tCount = 0;
    LBPOUnFixedAmntListSet tLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();
    LBPOUnFixedAmntListSchema tLBPOUnFixedAmntListSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOUnFixedAmntListDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBPOUnFixedAmntListSchema = new LBPOUnFixedAmntListSchema();
        tLBPOUnFixedAmntListSchema.setSchema(mResultSet, 1);
        tLBPOUnFixedAmntListSet.add(tLBPOUnFixedAmntListSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBPOUnFixedAmntListSchema = new LBPOUnFixedAmntListSchema();
                tLBPOUnFixedAmntListSchema.setSchema(mResultSet, 1);
                tLBPOUnFixedAmntListSet.add(tLBPOUnFixedAmntListSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOUnFixedAmntListDB";
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
    return tLBPOUnFixedAmntListSet;
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
            tError.moduleName = "LBPOUnFixedAmntListDB";
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
        tError.moduleName = "LBPOUnFixedAmntListDB";
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
            tError.moduleName = "LBPOUnFixedAmntListDB";
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
        tError.moduleName = "LBPOUnFixedAmntListDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBPOUnFixedAmntListSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOUnFixedAmntListSet aLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();

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
				LBPOUnFixedAmntListSchema s1 = new LBPOUnFixedAmntListSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LBPOUnFixedAmntListDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLBPOUnFixedAmntListSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

		return aLBPOUnFixedAmntListSet;
	}

	public LBPOUnFixedAmntListSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOUnFixedAmntListSet aLBPOUnFixedAmntListSet = new LBPOUnFixedAmntListSet();

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

				LBPOUnFixedAmntListSchema s1 = new LBPOUnFixedAmntListSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LBPOUnFixedAmntListDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLBPOUnFixedAmntListSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LBPOUnFixedAmntListDB";
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

		return aLBPOUnFixedAmntListSet; 
	}

}
