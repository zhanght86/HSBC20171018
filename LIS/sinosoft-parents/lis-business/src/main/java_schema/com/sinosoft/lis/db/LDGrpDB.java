/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDGrpDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LDGrpDB extends LDGrpSchema
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
	public LDGrpDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LDGrp" );
		mflag = true;
	}

	public LDGrpDB()
	{
		con = null;
		db = new DBOper( "LDGrp" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LDGrpSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LDGrpSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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
			pstmt = con.prepareStatement("DELETE FROM LDGrp WHERE  CustomerNo = ?");
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
			tError.moduleName = "LDGrpDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDGrp");
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
		SQLString sqlObj = new SQLString("LDGrp");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LDGrp SET  CustomerNo = ? , Password = ? , GrpName = ? , BusinessType = ? , GrpNature = ? , Peoples = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , BankCode = ? , BankAccNo = ? , GrpGroupNo = ? , BlacklistFlag = ? , State = ? , Remark = ? , VIPValue = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , SubCompanyFlag = ? , SupCustoemrNo = ? , LevelCode = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , BusinessBigType = ? , SocialInsuNo = ? , BlackListReason = ? , GrpNamePY = ? , SearchKeyWord = ? , CorIDType = ? , CorID = ? , CorIDExpiryDate = ? , ActuCtrl = ? , License = ? , OrganizationCode = ? , TaxCode = ? , ManageCom = ? , ComCode = ? , ModifyOperator = ? WHERE  CustomerNo = ?");
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCustomerNo());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPassword());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpName());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGrpNature());
			}
			pstmt.setInt(6, this.getPeoples());
			pstmt.setDouble(7, this.getRgtMoney());
			pstmt.setDouble(8, this.getAsset());
			pstmt.setDouble(9, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getComAera());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBankAccNo());
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getGrpGroupNo());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getBlacklistFlag());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getState());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getRemark());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getVIPValue());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getModifyTime());
			}
			if(this.getSubCompanyFlag() == null || this.getSubCompanyFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getSubCompanyFlag());
			}
			if(this.getSupCustoemrNo() == null || this.getSupCustoemrNo().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getSupCustoemrNo());
			}
			if(this.getLevelCode() == null || this.getLevelCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getLevelCode());
			}
			pstmt.setInt(34, this.getOnWorkPeoples());
			pstmt.setInt(35, this.getOffWorkPeoples());
			pstmt.setInt(36, this.getOtherPeoples());
			if(this.getBusinessBigType() == null || this.getBusinessBigType().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBusinessBigType());
			}
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getSocialInsuNo());
			}
			if(this.getBlackListReason() == null || this.getBlackListReason().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getBlackListReason());
			}
			if(this.getGrpNamePY() == null || this.getGrpNamePY().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getGrpNamePY());
			}
			if(this.getSearchKeyWord() == null || this.getSearchKeyWord().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getSearchKeyWord());
			}
			if(this.getCorIDType() == null || this.getCorIDType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCorIDType());
			}
			if(this.getCorID() == null || this.getCorID().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getCorID());
			}
			if(this.getCorIDExpiryDate() == null || this.getCorIDExpiryDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getCorIDExpiryDate()));
			}
			if(this.getActuCtrl() == null || this.getActuCtrl().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getActuCtrl());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getLicense());
			}
			if(this.getOrganizationCode() == null || this.getOrganizationCode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getOrganizationCode());
			}
			if(this.getTaxCode() == null || this.getTaxCode().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getTaxCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getModifyOperator());
			}
			// set where condition
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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
		SQLString sqlObj = new SQLString("LDGrp");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LDGrp VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getCustomerNo());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getPassword());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpName());
			}
			if(this.getBusinessType() == null || this.getBusinessType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBusinessType());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGrpNature());
			}
			pstmt.setInt(6, this.getPeoples());
			pstmt.setDouble(7, this.getRgtMoney());
			pstmt.setDouble(8, this.getAsset());
			pstmt.setDouble(9, this.getNetProfitRate());
			if(this.getMainBussiness() == null || this.getMainBussiness().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getMainBussiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCorporation());
			}
			if(this.getComAera() == null || this.getComAera().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getComAera());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPhone());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getGetFlag());
			}
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getSatrap());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEMail());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getFoundDate()));
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBankAccNo());
			}
			if(this.getGrpGroupNo() == null || this.getGrpGroupNo().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getGrpGroupNo());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getBlacklistFlag());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getState());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getRemark());
			}
			if(this.getVIPValue() == null || this.getVIPValue().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getVIPValue());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getModifyTime());
			}
			if(this.getSubCompanyFlag() == null || this.getSubCompanyFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getSubCompanyFlag());
			}
			if(this.getSupCustoemrNo() == null || this.getSupCustoemrNo().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getSupCustoemrNo());
			}
			if(this.getLevelCode() == null || this.getLevelCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getLevelCode());
			}
			pstmt.setInt(34, this.getOnWorkPeoples());
			pstmt.setInt(35, this.getOffWorkPeoples());
			pstmt.setInt(36, this.getOtherPeoples());
			if(this.getBusinessBigType() == null || this.getBusinessBigType().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBusinessBigType());
			}
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getSocialInsuNo());
			}
			if(this.getBlackListReason() == null || this.getBlackListReason().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getBlackListReason());
			}
			if(this.getGrpNamePY() == null || this.getGrpNamePY().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getGrpNamePY());
			}
			if(this.getSearchKeyWord() == null || this.getSearchKeyWord().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getSearchKeyWord());
			}
			if(this.getCorIDType() == null || this.getCorIDType().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCorIDType());
			}
			if(this.getCorID() == null || this.getCorID().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getCorID());
			}
			if(this.getCorIDExpiryDate() == null || this.getCorIDExpiryDate().equals("null")) {
				pstmt.setNull(44, 91);
			} else {
				pstmt.setDate(44, Date.valueOf(this.getCorIDExpiryDate()));
			}
			if(this.getActuCtrl() == null || this.getActuCtrl().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getActuCtrl());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getLicense());
			}
			if(this.getOrganizationCode() == null || this.getOrganizationCode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getOrganizationCode());
			}
			if(this.getTaxCode() == null || this.getTaxCode().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getTaxCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LDGrp WHERE  CustomerNo = ?", 
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
					tError.moduleName = "LDGrpDB";
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
			tError.moduleName = "LDGrpDB";
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

	public LDGrpSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDGrpSet aLDGrpSet = new LDGrpSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDGrp");
			LDGrpSchema aSchema = this.getSchema();
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
				LDGrpSchema s1 = new LDGrpSchema();
				s1.setSchema(rs,i);
				aLDGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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

		return aLDGrpSet;
	}

	public LDGrpSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDGrpSet aLDGrpSet = new LDGrpSet();

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
				LDGrpSchema s1 = new LDGrpSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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

		return aLDGrpSet;
	}

	public LDGrpSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDGrpSet aLDGrpSet = new LDGrpSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDGrp");
			LDGrpSchema aSchema = this.getSchema();
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

				LDGrpSchema s1 = new LDGrpSchema();
				s1.setSchema(rs,i);
				aLDGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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

		return aLDGrpSet;
	}

	public LDGrpSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDGrpSet aLDGrpSet = new LDGrpSet();

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

				LDGrpSchema s1 = new LDGrpSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDGrpSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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

		return aLDGrpSet;
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
			SQLString sqlObj = new SQLString("LDGrp");
			LDGrpSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LDGrp " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDGrpDB";
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
			tError.moduleName = "LDGrpDB";
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
        tError.moduleName = "LDGrpDB";
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
        tError.moduleName = "LDGrpDB";
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
        tError.moduleName = "LDGrpDB";
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
        tError.moduleName = "LDGrpDB";
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
 * @return LDGrpSet
 */
public LDGrpSet getData()
{
    int tCount = 0;
    LDGrpSet tLDGrpSet = new LDGrpSet();
    LDGrpSchema tLDGrpSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LDGrpDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLDGrpSchema = new LDGrpSchema();
        tLDGrpSchema.setSchema(mResultSet, 1);
        tLDGrpSet.add(tLDGrpSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLDGrpSchema = new LDGrpSchema();
                tLDGrpSchema.setSchema(mResultSet, 1);
                tLDGrpSet.add(tLDGrpSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LDGrpDB";
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
    return tLDGrpSet;
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
            tError.moduleName = "LDGrpDB";
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
        tError.moduleName = "LDGrpDB";
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
            tError.moduleName = "LDGrpDB";
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
        tError.moduleName = "LDGrpDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LDGrpSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDGrpSet aLDGrpSet = new LDGrpSet();

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
				LDGrpSchema s1 = new LDGrpSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDGrpSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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

		return aLDGrpSet;
	}

	public LDGrpSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDGrpSet aLDGrpSet = new LDGrpSet();

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

				LDGrpSchema s1 = new LDGrpSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDGrpDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDGrpSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpDB";
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

		return aLDGrpSet; 
	}

}
