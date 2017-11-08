/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.vschema.LCGrpAppntSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpAppntDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCGrpAppntDB extends LCGrpAppntSchema
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
	public LCGrpAppntDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCGrpAppnt" );
		mflag = true;
	}

	public LCGrpAppntDB()
	{
		con = null;
		db = new DBOper( "LCGrpAppnt" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCGrpAppntSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCGrpAppntSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCGrpAppnt WHERE  GrpContNo = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpAppnt");
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
		SQLString sqlObj = new SQLString("LCGrpAppnt");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCGrpAppnt SET  GrpContNo = ? , CustomerNo = ? , PrtNo = ? , AddressNo = ? , AppntGrade = ? , Name = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , Password = ? , State = ? , AppntType = ? , RelationToInsured = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , GrpNamePY = ? , SearchKeyWord = ? , BusinessCategory = ? , BusiCategory = ? , GrpNature = ? , SumNumPeople = ? , MainBusiness = ? , Corporation = ? , CorIDType = ? , CorID = ? , CorIDExpiryDate = ? , OnJobNumber = ? , RetireNumber = ? , OtherNumber = ? , RgtCapital = ? , TotalAssets = ? , NetProfitRate = ? , Satrap = ? , ActuCtrl = ? , License = ? , SocialInsuCode = ? , OrganizationCode = ? , TaxCode = ? , Fax = ? , EMail = ? , FoundDate = ? , BlacklistFlag = ? , VIPValue = ? , LevelCode = ? , UpCustoemrNo = ? , Remark = ? , Segment1 = ? , Segment2 = ? , Segment3 = ? , ManageCom = ? , ComCode = ? , ModifyOperator = ? WHERE  GrpContNo = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCustomerNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPrtNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAddressNo());
			}
			if(this.getAppntGrade() == null || this.getAppntGrade().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAppntGrade());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getName());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getPhone());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPassword());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getState());
			}
			if(this.getAppntType() == null || this.getAppntType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getAppntType());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getRelationToInsured());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getModifyTime());
			}
			if(this.getGrpNamePY() == null || this.getGrpNamePY().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getGrpNamePY());
			}
			if(this.getSearchKeyWord() == null || this.getSearchKeyWord().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSearchKeyWord());
			}
			if(this.getBusinessCategory() == null || this.getBusinessCategory().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getBusinessCategory());
			}
			if(this.getBusiCategory() == null || this.getBusiCategory().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getBusiCategory());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getGrpNature());
			}
			pstmt.setInt(24, this.getSumNumPeople());
			if(this.getMainBusiness() == null || this.getMainBusiness().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getMainBusiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCorporation());
			}
			if(this.getCorIDType() == null || this.getCorIDType().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getCorIDType());
			}
			if(this.getCorID() == null || this.getCorID().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCorID());
			}
			if(this.getCorIDExpiryDate() == null || this.getCorIDExpiryDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getCorIDExpiryDate()));
			}
			pstmt.setInt(30, this.getOnJobNumber());
			pstmt.setInt(31, this.getRetireNumber());
			pstmt.setInt(32, this.getOtherNumber());
			pstmt.setDouble(33, this.getRgtCapital());
			pstmt.setDouble(34, this.getTotalAssets());
			pstmt.setDouble(35, this.getNetProfitRate());
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getSatrap());
			}
			if(this.getActuCtrl() == null || this.getActuCtrl().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getActuCtrl());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getLicense());
			}
			if(this.getSocialInsuCode() == null || this.getSocialInsuCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSocialInsuCode());
			}
			if(this.getOrganizationCode() == null || this.getOrganizationCode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getOrganizationCode());
			}
			if(this.getTaxCode() == null || this.getTaxCode().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getTaxCode());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getFax());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getBlacklistFlag());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getVIPValue());
			}
			if(this.getLevelCode() == null || this.getLevelCode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getLevelCode());
			}
			if(this.getUpCustoemrNo() == null || this.getUpCustoemrNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getUpCustoemrNo());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getRemark());
			}
			if(this.getSegment1() == null || this.getSegment1().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getSegment1());
			}
			if(this.getSegment2() == null || this.getSegment2().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getSegment2());
			}
			if(this.getSegment3() == null || this.getSegment3().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getSegment3());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getModifyOperator());
			}
			// set where condition
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getGrpContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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
		SQLString sqlObj = new SQLString("LCGrpAppnt");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCGrpAppnt VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCustomerNo());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPrtNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAddressNo());
			}
			if(this.getAppntGrade() == null || this.getAppntGrade().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAppntGrade());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getName());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getPhone());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getPassword());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getState());
			}
			if(this.getAppntType() == null || this.getAppntType().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getAppntType());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getRelationToInsured());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(15, 91);
			} else {
				pstmt.setDate(15, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getModifyTime());
			}
			if(this.getGrpNamePY() == null || this.getGrpNamePY().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getGrpNamePY());
			}
			if(this.getSearchKeyWord() == null || this.getSearchKeyWord().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSearchKeyWord());
			}
			if(this.getBusinessCategory() == null || this.getBusinessCategory().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getBusinessCategory());
			}
			if(this.getBusiCategory() == null || this.getBusiCategory().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getBusiCategory());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getGrpNature());
			}
			pstmt.setInt(24, this.getSumNumPeople());
			if(this.getMainBusiness() == null || this.getMainBusiness().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getMainBusiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCorporation());
			}
			if(this.getCorIDType() == null || this.getCorIDType().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getCorIDType());
			}
			if(this.getCorID() == null || this.getCorID().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCorID());
			}
			if(this.getCorIDExpiryDate() == null || this.getCorIDExpiryDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getCorIDExpiryDate()));
			}
			pstmt.setInt(30, this.getOnJobNumber());
			pstmt.setInt(31, this.getRetireNumber());
			pstmt.setInt(32, this.getOtherNumber());
			pstmt.setDouble(33, this.getRgtCapital());
			pstmt.setDouble(34, this.getTotalAssets());
			pstmt.setDouble(35, this.getNetProfitRate());
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getSatrap());
			}
			if(this.getActuCtrl() == null || this.getActuCtrl().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getActuCtrl());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getLicense());
			}
			if(this.getSocialInsuCode() == null || this.getSocialInsuCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSocialInsuCode());
			}
			if(this.getOrganizationCode() == null || this.getOrganizationCode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getOrganizationCode());
			}
			if(this.getTaxCode() == null || this.getTaxCode().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getTaxCode());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getFax());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getBlacklistFlag());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getVIPValue());
			}
			if(this.getLevelCode() == null || this.getLevelCode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getLevelCode());
			}
			if(this.getUpCustoemrNo() == null || this.getUpCustoemrNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getUpCustoemrNo());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getRemark());
			}
			if(this.getSegment1() == null || this.getSegment1().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getSegment1());
			}
			if(this.getSegment2() == null || this.getSegment2().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getSegment2());
			}
			if(this.getSegment3() == null || this.getSegment3().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getSegment3());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCGrpAppnt WHERE  GrpContNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
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
					tError.moduleName = "LCGrpAppntDB";
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
			tError.moduleName = "LCGrpAppntDB";
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

	public LCGrpAppntSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGrpAppnt");
			LCGrpAppntSchema aSchema = this.getSchema();
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
				LCGrpAppntSchema s1 = new LCGrpAppntSchema();
				s1.setSchema(rs,i);
				aLCGrpAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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

		return aLCGrpAppntSet;
	}

	public LCGrpAppntSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();

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
				LCGrpAppntSchema s1 = new LCGrpAppntSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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

		return aLCGrpAppntSet;
	}

	public LCGrpAppntSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGrpAppnt");
			LCGrpAppntSchema aSchema = this.getSchema();
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

				LCGrpAppntSchema s1 = new LCGrpAppntSchema();
				s1.setSchema(rs,i);
				aLCGrpAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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

		return aLCGrpAppntSet;
	}

	public LCGrpAppntSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();

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

				LCGrpAppntSchema s1 = new LCGrpAppntSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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

		return aLCGrpAppntSet;
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
			SQLString sqlObj = new SQLString("LCGrpAppnt");
			LCGrpAppntSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCGrpAppnt " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpAppntDB";
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
			tError.moduleName = "LCGrpAppntDB";
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
        tError.moduleName = "LCGrpAppntDB";
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
        tError.moduleName = "LCGrpAppntDB";
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
        tError.moduleName = "LCGrpAppntDB";
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
        tError.moduleName = "LCGrpAppntDB";
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
 * @return LCGrpAppntSet
 */
public LCGrpAppntSet getData()
{
    int tCount = 0;
    LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
    LCGrpAppntSchema tLCGrpAppntSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCGrpAppntDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCGrpAppntSchema = new LCGrpAppntSchema();
        tLCGrpAppntSchema.setSchema(mResultSet, 1);
        tLCGrpAppntSet.add(tLCGrpAppntSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCGrpAppntSchema = new LCGrpAppntSchema();
                tLCGrpAppntSchema.setSchema(mResultSet, 1);
                tLCGrpAppntSet.add(tLCGrpAppntSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCGrpAppntDB";
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
    return tLCGrpAppntSet;
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
            tError.moduleName = "LCGrpAppntDB";
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
        tError.moduleName = "LCGrpAppntDB";
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
            tError.moduleName = "LCGrpAppntDB";
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
        tError.moduleName = "LCGrpAppntDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCGrpAppntSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();

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
				LCGrpAppntSchema s1 = new LCGrpAppntSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpAppntSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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

		return aLCGrpAppntSet;
	}

	public LCGrpAppntSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpAppntSet aLCGrpAppntSet = new LCGrpAppntSet();

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

				LCGrpAppntSchema s1 = new LCGrpAppntSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpAppntSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAppntDB";
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

		return aLCGrpAppntSet; 
	}

}
