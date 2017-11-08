/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LCPrintAppntSchema;
import com.sinosoft.lis.vschema.LCPrintAppntSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCPrintAppntDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCPrintAppntDBSet extends LCPrintAppntSet
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
	public LCPrintAppntDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCPrintAppnt");
		mflag = true;
	}

	public LCPrintAppntDBSet()
	{
		db = new DBOper( "LCPrintAppnt" );
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
			tError.moduleName = "LCPrintAppntDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCPrintAppnt WHERE  GrpPropNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPropNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPrintAppnt");
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
			tError.moduleName = "LCPrintAppntDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCPrintAppnt SET  OfferListNo = ? , GrpPropNo = ? , GrpContNo = ? , CustomerNo = ? , AddressNo = ? , GrpName = ? , GrpNamePY = ? , SearchKeyWord = ? , MainBusiness = ? , Corporation = ? , CorIDType = ? , CorID = ? , CorIDExpiryDate = ? , BusinessCategory = ? , BusiCategory = ? , FoundDate = ? , SocialInsuCode = ? , GrpNature = ? , GrpIDType = ? , GrpID = ? , GrpIDExpiryDate = ? , Phone = ? , Fax = ? , EMail = ? , SumNumPeople = ? , MainContNumber = ? , RelatedContNumber = ? , OnJobNumber = ? , RetireNumber = ? , OtherNumber = ? , RgtCapital = ? , TotalAssets = ? , NetProfitRate = ? , Satrap = ? , ActuCtrl = ? , License = ? , OrganizationCode = ? , TaxCode = ? , LinkMan = ? , LinkIDType = ? , LinkID = ? , LinkIDExpiryDate = ? , LinkPhone = ? , State = ? , ValDateType = ? , ValDate = ? , InsuPeriod = ? , InsuPeriodFlag = ? , InsuYears = ? , PayMode = ? , PremMode = ? , PayIntv = ? , ChnlType = ? , SaleChnl = ? , Remark = ? , Segment1 = ? , Segment2 = ? , Segment3 = ? , AppManageCom = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  GrpPropNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getOfferListNo() == null || this.get(i).getOfferListNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getOfferListNo());
			}
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAddressNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpNamePY() == null || this.get(i).getGrpNamePY().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpNamePY());
			}
			if(this.get(i).getSearchKeyWord() == null || this.get(i).getSearchKeyWord().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSearchKeyWord());
			}
			if(this.get(i).getMainBusiness() == null || this.get(i).getMainBusiness().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMainBusiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCorporation());
			}
			if(this.get(i).getCorIDType() == null || this.get(i).getCorIDType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCorIDType());
			}
			if(this.get(i).getCorID() == null || this.get(i).getCorID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCorID());
			}
			if(this.get(i).getCorIDExpiryDate() == null || this.get(i).getCorIDExpiryDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getCorIDExpiryDate()));
			}
			if(this.get(i).getBusinessCategory() == null || this.get(i).getBusinessCategory().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBusinessCategory());
			}
			if(this.get(i).getBusiCategory() == null || this.get(i).getBusiCategory().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBusiCategory());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getSocialInsuCode() == null || this.get(i).getSocialInsuCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSocialInsuCode());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGrpNature());
			}
			if(this.get(i).getGrpIDType() == null || this.get(i).getGrpIDType().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getGrpIDType());
			}
			if(this.get(i).getGrpID() == null || this.get(i).getGrpID().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getGrpID());
			}
			if(this.get(i).getGrpIDExpiryDate() == null || this.get(i).getGrpIDExpiryDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getGrpIDExpiryDate()));
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPhone());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getFax());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getEMail());
			}
			pstmt.setInt(25, this.get(i).getSumNumPeople());
			pstmt.setInt(26, this.get(i).getMainContNumber());
			pstmt.setInt(27, this.get(i).getRelatedContNumber());
			pstmt.setInt(28, this.get(i).getOnJobNumber());
			pstmt.setInt(29, this.get(i).getRetireNumber());
			pstmt.setInt(30, this.get(i).getOtherNumber());
			pstmt.setDouble(31, this.get(i).getRgtCapital());
			pstmt.setDouble(32, this.get(i).getTotalAssets());
			pstmt.setDouble(33, this.get(i).getNetProfitRate());
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getSatrap());
			}
			if(this.get(i).getActuCtrl() == null || this.get(i).getActuCtrl().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getActuCtrl());
			}
			if(this.get(i).getLicense() == null || this.get(i).getLicense().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getLicense());
			}
			if(this.get(i).getOrganizationCode() == null || this.get(i).getOrganizationCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOrganizationCode());
			}
			if(this.get(i).getTaxCode() == null || this.get(i).getTaxCode().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getTaxCode());
			}
			if(this.get(i).getLinkMan() == null || this.get(i).getLinkMan().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getLinkMan());
			}
			if(this.get(i).getLinkIDType() == null || this.get(i).getLinkIDType().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getLinkIDType());
			}
			if(this.get(i).getLinkID() == null || this.get(i).getLinkID().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getLinkID());
			}
			if(this.get(i).getLinkIDExpiryDate() == null || this.get(i).getLinkIDExpiryDate().equals("null")) {
				pstmt.setDate(42,null);
			} else {
				pstmt.setDate(42, Date.valueOf(this.get(i).getLinkIDExpiryDate()));
			}
			if(this.get(i).getLinkPhone() == null || this.get(i).getLinkPhone().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getLinkPhone());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getState());
			}
			if(this.get(i).getValDateType() == null || this.get(i).getValDateType().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getValDateType());
			}
			if(this.get(i).getValDate() == null || this.get(i).getValDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getValDate()));
			}
			pstmt.setInt(47, this.get(i).getInsuPeriod());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getInsuPeriodFlag());
			}
			pstmt.setInt(49, this.get(i).getInsuYears());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getPayMode());
			}
			if(this.get(i).getPremMode() == null || this.get(i).getPremMode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPremMode());
			}
			pstmt.setInt(52, this.get(i).getPayIntv());
			if(this.get(i).getChnlType() == null || this.get(i).getChnlType().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getChnlType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getSaleChnl());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getRemark());
			}
			if(this.get(i).getSegment1() == null || this.get(i).getSegment1().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSegment1());
			}
			if(this.get(i).getSegment2() == null || this.get(i).getSegment2().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getSegment2());
			}
			if(this.get(i).getSegment3() == null || this.get(i).getSegment3().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getSegment3());
			}
			if(this.get(i).getAppManageCom() == null || this.get(i).getAppManageCom().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAppManageCom());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(63,null);
			} else {
				pstmt.setDate(63, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(66,null);
			} else {
				pstmt.setDate(66, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getGrpPropNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPrintAppnt");
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
			tError.moduleName = "LCPrintAppntDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCPrintAppnt VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getOfferListNo() == null || this.get(i).getOfferListNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getOfferListNo());
			}
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCustomerNo());
			}
			if(this.get(i).getAddressNo() == null || this.get(i).getAddressNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAddressNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpNamePY() == null || this.get(i).getGrpNamePY().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpNamePY());
			}
			if(this.get(i).getSearchKeyWord() == null || this.get(i).getSearchKeyWord().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSearchKeyWord());
			}
			if(this.get(i).getMainBusiness() == null || this.get(i).getMainBusiness().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMainBusiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCorporation());
			}
			if(this.get(i).getCorIDType() == null || this.get(i).getCorIDType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCorIDType());
			}
			if(this.get(i).getCorID() == null || this.get(i).getCorID().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCorID());
			}
			if(this.get(i).getCorIDExpiryDate() == null || this.get(i).getCorIDExpiryDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getCorIDExpiryDate()));
			}
			if(this.get(i).getBusinessCategory() == null || this.get(i).getBusinessCategory().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBusinessCategory());
			}
			if(this.get(i).getBusiCategory() == null || this.get(i).getBusiCategory().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBusiCategory());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getSocialInsuCode() == null || this.get(i).getSocialInsuCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSocialInsuCode());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getGrpNature());
			}
			if(this.get(i).getGrpIDType() == null || this.get(i).getGrpIDType().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getGrpIDType());
			}
			if(this.get(i).getGrpID() == null || this.get(i).getGrpID().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getGrpID());
			}
			if(this.get(i).getGrpIDExpiryDate() == null || this.get(i).getGrpIDExpiryDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getGrpIDExpiryDate()));
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPhone());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getFax());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getEMail());
			}
			pstmt.setInt(25, this.get(i).getSumNumPeople());
			pstmt.setInt(26, this.get(i).getMainContNumber());
			pstmt.setInt(27, this.get(i).getRelatedContNumber());
			pstmt.setInt(28, this.get(i).getOnJobNumber());
			pstmt.setInt(29, this.get(i).getRetireNumber());
			pstmt.setInt(30, this.get(i).getOtherNumber());
			pstmt.setDouble(31, this.get(i).getRgtCapital());
			pstmt.setDouble(32, this.get(i).getTotalAssets());
			pstmt.setDouble(33, this.get(i).getNetProfitRate());
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getSatrap());
			}
			if(this.get(i).getActuCtrl() == null || this.get(i).getActuCtrl().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getActuCtrl());
			}
			if(this.get(i).getLicense() == null || this.get(i).getLicense().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getLicense());
			}
			if(this.get(i).getOrganizationCode() == null || this.get(i).getOrganizationCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOrganizationCode());
			}
			if(this.get(i).getTaxCode() == null || this.get(i).getTaxCode().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getTaxCode());
			}
			if(this.get(i).getLinkMan() == null || this.get(i).getLinkMan().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getLinkMan());
			}
			if(this.get(i).getLinkIDType() == null || this.get(i).getLinkIDType().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getLinkIDType());
			}
			if(this.get(i).getLinkID() == null || this.get(i).getLinkID().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getLinkID());
			}
			if(this.get(i).getLinkIDExpiryDate() == null || this.get(i).getLinkIDExpiryDate().equals("null")) {
				pstmt.setDate(42,null);
			} else {
				pstmt.setDate(42, Date.valueOf(this.get(i).getLinkIDExpiryDate()));
			}
			if(this.get(i).getLinkPhone() == null || this.get(i).getLinkPhone().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getLinkPhone());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getState());
			}
			if(this.get(i).getValDateType() == null || this.get(i).getValDateType().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getValDateType());
			}
			if(this.get(i).getValDate() == null || this.get(i).getValDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getValDate()));
			}
			pstmt.setInt(47, this.get(i).getInsuPeriod());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getInsuPeriodFlag());
			}
			pstmt.setInt(49, this.get(i).getInsuYears());
			if(this.get(i).getPayMode() == null || this.get(i).getPayMode().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getPayMode());
			}
			if(this.get(i).getPremMode() == null || this.get(i).getPremMode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPremMode());
			}
			pstmt.setInt(52, this.get(i).getPayIntv());
			if(this.get(i).getChnlType() == null || this.get(i).getChnlType().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getChnlType());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getSaleChnl());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getRemark());
			}
			if(this.get(i).getSegment1() == null || this.get(i).getSegment1().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSegment1());
			}
			if(this.get(i).getSegment2() == null || this.get(i).getSegment2().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getSegment2());
			}
			if(this.get(i).getSegment3() == null || this.get(i).getSegment3().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getSegment3());
			}
			if(this.get(i).getAppManageCom() == null || this.get(i).getAppManageCom().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getAppManageCom());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(63,null);
			} else {
				pstmt.setDate(63, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(66,null);
			} else {
				pstmt.setDate(66, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPrintAppnt");
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
			tError.moduleName = "LCPrintAppntDBSet";
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
