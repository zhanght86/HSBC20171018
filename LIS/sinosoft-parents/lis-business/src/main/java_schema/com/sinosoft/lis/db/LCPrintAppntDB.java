/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LCPrintAppntSchema;
import com.sinosoft.lis.vschema.LCPrintAppntSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCPrintAppntDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCPrintAppntDB extends LCPrintAppntSchema
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
	public LCPrintAppntDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCPrintAppnt" );
		mflag = true;
	}

	public LCPrintAppntDB()
	{
		con = null;
		db = new DBOper( "LCPrintAppnt" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCPrintAppntSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCPrintAppntSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCPrintAppnt WHERE  GrpPropNo = ?");
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpPropNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPrintAppnt");
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
		SQLString sqlObj = new SQLString("LCPrintAppnt");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCPrintAppnt SET  OfferListNo = ? , GrpPropNo = ? , GrpContNo = ? , CustomerNo = ? , AddressNo = ? , GrpName = ? , GrpNamePY = ? , SearchKeyWord = ? , MainBusiness = ? , Corporation = ? , CorIDType = ? , CorID = ? , CorIDExpiryDate = ? , BusinessCategory = ? , BusiCategory = ? , FoundDate = ? , SocialInsuCode = ? , GrpNature = ? , GrpIDType = ? , GrpID = ? , GrpIDExpiryDate = ? , Phone = ? , Fax = ? , EMail = ? , SumNumPeople = ? , MainContNumber = ? , RelatedContNumber = ? , OnJobNumber = ? , RetireNumber = ? , OtherNumber = ? , RgtCapital = ? , TotalAssets = ? , NetProfitRate = ? , Satrap = ? , ActuCtrl = ? , License = ? , OrganizationCode = ? , TaxCode = ? , LinkMan = ? , LinkIDType = ? , LinkID = ? , LinkIDExpiryDate = ? , LinkPhone = ? , State = ? , ValDateType = ? , ValDate = ? , InsuPeriod = ? , InsuPeriodFlag = ? , InsuYears = ? , PayMode = ? , PremMode = ? , PayIntv = ? , ChnlType = ? , SaleChnl = ? , Remark = ? , Segment1 = ? , Segment2 = ? , Segment3 = ? , AppManageCom = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  GrpPropNo = ?");
			if(this.getOfferListNo() == null || this.getOfferListNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getOfferListNo());
			}
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPropNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAddressNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGrpName());
			}
			if(this.getGrpNamePY() == null || this.getGrpNamePY().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getGrpNamePY());
			}
			if(this.getSearchKeyWord() == null || this.getSearchKeyWord().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getSearchKeyWord());
			}
			if(this.getMainBusiness() == null || this.getMainBusiness().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getMainBusiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCorporation());
			}
			if(this.getCorIDType() == null || this.getCorIDType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCorIDType());
			}
			if(this.getCorID() == null || this.getCorID().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getCorID());
			}
			if(this.getCorIDExpiryDate() == null || this.getCorIDExpiryDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getCorIDExpiryDate()));
			}
			if(this.getBusinessCategory() == null || this.getBusinessCategory().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getBusinessCategory());
			}
			if(this.getBusiCategory() == null || this.getBusiCategory().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getBusiCategory());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getFoundDate()));
			}
			if(this.getSocialInsuCode() == null || this.getSocialInsuCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getSocialInsuCode());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpNature());
			}
			if(this.getGrpIDType() == null || this.getGrpIDType().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getGrpIDType());
			}
			if(this.getGrpID() == null || this.getGrpID().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getGrpID());
			}
			if(this.getGrpIDExpiryDate() == null || this.getGrpIDExpiryDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getGrpIDExpiryDate()));
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPhone());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getFax());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getEMail());
			}
			pstmt.setInt(25, this.getSumNumPeople());
			pstmt.setInt(26, this.getMainContNumber());
			pstmt.setInt(27, this.getRelatedContNumber());
			pstmt.setInt(28, this.getOnJobNumber());
			pstmt.setInt(29, this.getRetireNumber());
			pstmt.setInt(30, this.getOtherNumber());
			pstmt.setDouble(31, this.getRgtCapital());
			pstmt.setDouble(32, this.getTotalAssets());
			pstmt.setDouble(33, this.getNetProfitRate());
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getSatrap());
			}
			if(this.getActuCtrl() == null || this.getActuCtrl().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getActuCtrl());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getLicense());
			}
			if(this.getOrganizationCode() == null || this.getOrganizationCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOrganizationCode());
			}
			if(this.getTaxCode() == null || this.getTaxCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getTaxCode());
			}
			if(this.getLinkMan() == null || this.getLinkMan().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getLinkMan());
			}
			if(this.getLinkIDType() == null || this.getLinkIDType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getLinkIDType());
			}
			if(this.getLinkID() == null || this.getLinkID().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getLinkID());
			}
			if(this.getLinkIDExpiryDate() == null || this.getLinkIDExpiryDate().equals("null")) {
				pstmt.setNull(42, 91);
			} else {
				pstmt.setDate(42, Date.valueOf(this.getLinkIDExpiryDate()));
			}
			if(this.getLinkPhone() == null || this.getLinkPhone().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getLinkPhone());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getState());
			}
			if(this.getValDateType() == null || this.getValDateType().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getValDateType());
			}
			if(this.getValDate() == null || this.getValDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getValDate()));
			}
			pstmt.setInt(47, this.getInsuPeriod());
			if(this.getInsuPeriodFlag() == null || this.getInsuPeriodFlag().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getInsuPeriodFlag());
			}
			pstmt.setInt(49, this.getInsuYears());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getPayMode());
			}
			if(this.getPremMode() == null || this.getPremMode().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getPremMode());
			}
			pstmt.setInt(52, this.getPayIntv());
			if(this.getChnlType() == null || this.getChnlType().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getChnlType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getSaleChnl());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getRemark());
			}
			if(this.getSegment1() == null || this.getSegment1().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getSegment1());
			}
			if(this.getSegment2() == null || this.getSegment2().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getSegment2());
			}
			if(this.getSegment3() == null || this.getSegment3().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSegment3());
			}
			if(this.getAppManageCom() == null || this.getAppManageCom().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAppManageCom());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(63, 91);
			} else {
				pstmt.setDate(63, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(66, 91);
			} else {
				pstmt.setDate(66, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getModifyTime());
			}
			// set where condition
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getGrpPropNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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
		SQLString sqlObj = new SQLString("LCPrintAppnt");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCPrintAppnt VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getOfferListNo() == null || this.getOfferListNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getOfferListNo());
			}
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGrpPropNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAddressNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGrpName());
			}
			if(this.getGrpNamePY() == null || this.getGrpNamePY().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getGrpNamePY());
			}
			if(this.getSearchKeyWord() == null || this.getSearchKeyWord().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getSearchKeyWord());
			}
			if(this.getMainBusiness() == null || this.getMainBusiness().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getMainBusiness());
			}
			if(this.getCorporation() == null || this.getCorporation().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCorporation());
			}
			if(this.getCorIDType() == null || this.getCorIDType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCorIDType());
			}
			if(this.getCorID() == null || this.getCorID().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getCorID());
			}
			if(this.getCorIDExpiryDate() == null || this.getCorIDExpiryDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getCorIDExpiryDate()));
			}
			if(this.getBusinessCategory() == null || this.getBusinessCategory().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getBusinessCategory());
			}
			if(this.getBusiCategory() == null || this.getBusiCategory().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getBusiCategory());
			}
			if(this.getFoundDate() == null || this.getFoundDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getFoundDate()));
			}
			if(this.getSocialInsuCode() == null || this.getSocialInsuCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getSocialInsuCode());
			}
			if(this.getGrpNature() == null || this.getGrpNature().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getGrpNature());
			}
			if(this.getGrpIDType() == null || this.getGrpIDType().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getGrpIDType());
			}
			if(this.getGrpID() == null || this.getGrpID().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getGrpID());
			}
			if(this.getGrpIDExpiryDate() == null || this.getGrpIDExpiryDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getGrpIDExpiryDate()));
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPhone());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getFax());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getEMail());
			}
			pstmt.setInt(25, this.getSumNumPeople());
			pstmt.setInt(26, this.getMainContNumber());
			pstmt.setInt(27, this.getRelatedContNumber());
			pstmt.setInt(28, this.getOnJobNumber());
			pstmt.setInt(29, this.getRetireNumber());
			pstmt.setInt(30, this.getOtherNumber());
			pstmt.setDouble(31, this.getRgtCapital());
			pstmt.setDouble(32, this.getTotalAssets());
			pstmt.setDouble(33, this.getNetProfitRate());
			if(this.getSatrap() == null || this.getSatrap().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getSatrap());
			}
			if(this.getActuCtrl() == null || this.getActuCtrl().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getActuCtrl());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getLicense());
			}
			if(this.getOrganizationCode() == null || this.getOrganizationCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOrganizationCode());
			}
			if(this.getTaxCode() == null || this.getTaxCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getTaxCode());
			}
			if(this.getLinkMan() == null || this.getLinkMan().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getLinkMan());
			}
			if(this.getLinkIDType() == null || this.getLinkIDType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getLinkIDType());
			}
			if(this.getLinkID() == null || this.getLinkID().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getLinkID());
			}
			if(this.getLinkIDExpiryDate() == null || this.getLinkIDExpiryDate().equals("null")) {
				pstmt.setNull(42, 91);
			} else {
				pstmt.setDate(42, Date.valueOf(this.getLinkIDExpiryDate()));
			}
			if(this.getLinkPhone() == null || this.getLinkPhone().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getLinkPhone());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getState());
			}
			if(this.getValDateType() == null || this.getValDateType().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getValDateType());
			}
			if(this.getValDate() == null || this.getValDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getValDate()));
			}
			pstmt.setInt(47, this.getInsuPeriod());
			if(this.getInsuPeriodFlag() == null || this.getInsuPeriodFlag().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getInsuPeriodFlag());
			}
			pstmt.setInt(49, this.getInsuYears());
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getPayMode());
			}
			if(this.getPremMode() == null || this.getPremMode().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getPremMode());
			}
			pstmt.setInt(52, this.getPayIntv());
			if(this.getChnlType() == null || this.getChnlType().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getChnlType());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getSaleChnl());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getRemark());
			}
			if(this.getSegment1() == null || this.getSegment1().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getSegment1());
			}
			if(this.getSegment2() == null || this.getSegment2().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getSegment2());
			}
			if(this.getSegment3() == null || this.getSegment3().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSegment3());
			}
			if(this.getAppManageCom() == null || this.getAppManageCom().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getAppManageCom());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(63, 91);
			} else {
				pstmt.setDate(63, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(66, 91);
			} else {
				pstmt.setDate(66, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCPrintAppnt WHERE  GrpPropNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGrpPropNo() == null || this.getGrpPropNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpPropNo());
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
					tError.moduleName = "LCPrintAppntDB";
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
			tError.moduleName = "LCPrintAppntDB";
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

	public LCPrintAppntSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCPrintAppntSet aLCPrintAppntSet = new LCPrintAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCPrintAppnt");
			LCPrintAppntSchema aSchema = this.getSchema();
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
				LCPrintAppntSchema s1 = new LCPrintAppntSchema();
				s1.setSchema(rs,i);
				aLCPrintAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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

		return aLCPrintAppntSet;
	}

	public LCPrintAppntSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCPrintAppntSet aLCPrintAppntSet = new LCPrintAppntSet();

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
				LCPrintAppntSchema s1 = new LCPrintAppntSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCPrintAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCPrintAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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

		return aLCPrintAppntSet;
	}

	public LCPrintAppntSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCPrintAppntSet aLCPrintAppntSet = new LCPrintAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCPrintAppnt");
			LCPrintAppntSchema aSchema = this.getSchema();
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

				LCPrintAppntSchema s1 = new LCPrintAppntSchema();
				s1.setSchema(rs,i);
				aLCPrintAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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

		return aLCPrintAppntSet;
	}

	public LCPrintAppntSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCPrintAppntSet aLCPrintAppntSet = new LCPrintAppntSet();

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

				LCPrintAppntSchema s1 = new LCPrintAppntSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCPrintAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCPrintAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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

		return aLCPrintAppntSet;
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
			SQLString sqlObj = new SQLString("LCPrintAppnt");
			LCPrintAppntSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCPrintAppnt " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPrintAppntDB";
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
			tError.moduleName = "LCPrintAppntDB";
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
        tError.moduleName = "LCPrintAppntDB";
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
        tError.moduleName = "LCPrintAppntDB";
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
        tError.moduleName = "LCPrintAppntDB";
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
        tError.moduleName = "LCPrintAppntDB";
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
 * @return LCPrintAppntSet
 */
public LCPrintAppntSet getData()
{
    int tCount = 0;
    LCPrintAppntSet tLCPrintAppntSet = new LCPrintAppntSet();
    LCPrintAppntSchema tLCPrintAppntSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCPrintAppntDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCPrintAppntSchema = new LCPrintAppntSchema();
        tLCPrintAppntSchema.setSchema(mResultSet, 1);
        tLCPrintAppntSet.add(tLCPrintAppntSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCPrintAppntSchema = new LCPrintAppntSchema();
                tLCPrintAppntSchema.setSchema(mResultSet, 1);
                tLCPrintAppntSet.add(tLCPrintAppntSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCPrintAppntDB";
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
    return tLCPrintAppntSet;
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
            tError.moduleName = "LCPrintAppntDB";
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
        tError.moduleName = "LCPrintAppntDB";
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
            tError.moduleName = "LCPrintAppntDB";
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
        tError.moduleName = "LCPrintAppntDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCPrintAppntSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCPrintAppntSet aLCPrintAppntSet = new LCPrintAppntSet();

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
				LCPrintAppntSchema s1 = new LCPrintAppntSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCPrintAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCPrintAppntSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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

		return aLCPrintAppntSet;
	}

	public LCPrintAppntSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCPrintAppntSet aLCPrintAppntSet = new LCPrintAppntSet();

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

				LCPrintAppntSchema s1 = new LCPrintAppntSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCPrintAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCPrintAppntSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAppntDB";
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

		return aLCPrintAppntSet; 
	}

}
