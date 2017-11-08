/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOInsuredSchema;
import com.sinosoft.lis.vschema.LBPOInsuredSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOInsuredDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPOInsuredDB extends LBPOInsuredSchema
{
private static Logger logger = Logger.getLogger(LBPOInsuredDB.class);

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
	public LBPOInsuredDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBPOInsured" );
		mflag = true;
	}

	public LBPOInsuredDB()
	{
		con = null;
		db = new DBOper( "LBPOInsured" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBPOInsuredSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBPOInsuredSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOInsured WHERE  ContNo = ? AND InputNo = ? AND FillNo = ? AND InsuredNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.setInt(2, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFillNo());
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
			tError.moduleName = "LBPOInsuredDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOInsured");
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
		SQLString sqlObj = new SQLString("LBPOInsured");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBPOInsured SET  GrpContNo = ? , ContNo = ? , InputNo = ? , FillNo = ? , InsuredNo = ? , PrtNo = ? , AppntNo = ? , ManageCom = ? , ExecuteCom = ? , FamilyID = ? , RelationToMainInsured = ? , RelationToAppnt = ? , AddressNo = ? , SequenceNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , NativePlace = ? , Nationality = ? , RgtAddress = ? , Marriage = ? , MarriageDate = ? , Health = ? , Stature = ? , Avoirdupois = ? , Degree = ? , CreditGrade = ? , BankCode = ? , BankAccNo = ? , AccName = ? , JoinCompanyDate = ? , Position = ? , Salary = ? , OccupationType = ? , StartWorkDate = ? , OccupationCode = ? , WorkType = ? , PluralityType = ? , SmokeFlag = ? , ContPlanCode = ? , Operator = ? , InsuredStat = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , UWFlag = ? , UWCode = ? , UWDate = ? , UWTime = ? , BMI = ? , InsuredPeoples = ? , License = ? , LicenseType = ? , CustomerSeqNo = ? , SocialInsuNo = ? , SignName = ? , EMail = ? , Mobile = ? , CompanyFax = ? , CompanyPhone = ? , CompanyZipCode = ? , CompanyAddress = ? , HomeFax = ? , HomePhone = ? , HomeZipCode = ? , HomeAddress = ? , Fax = ? , Phone = ? , ZipCode = ? , PostalAddress = ? , SocialInsuFlag = ? , IDExpDate = ? WHERE  ContNo = ? AND InputNo = ? AND FillNo = ? AND InsuredNo = ?");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			pstmt.setInt(3, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFillNo());
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
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getBirthday());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(18, 12);
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
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getMarriageDate());
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getHealth());
			}
			if(this.getStature() == null || this.getStature().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStature());
			}
			if(this.getAvoirdupois() == null || this.getAvoirdupois().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getAvoirdupois());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(29, 12);
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
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getJoinCompanyDate());
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getPosition());
			}
			if(this.getSalary() == null || this.getSalary().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getSalary());
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOccupationType());
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getStartWorkDate());
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
				pstmt.setNull(41, 12);
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
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getInsuredStat());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getMakeDate());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getModifyDate());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getModifyTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getUWDate());
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getUWTime());
			}
			if(this.getBMI() == null || this.getBMI().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getBMI());
			}
			if(this.getInsuredPeoples() == null || this.getInsuredPeoples().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getInsuredPeoples());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getLicenseType());
			}
			if(this.getCustomerSeqNo() == null || this.getCustomerSeqNo().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getCustomerSeqNo());
			}
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSocialInsuNo());
			}
			if(this.getSignName() == null || this.getSignName().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getSignName());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getEMail());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getMobile());
			}
			if(this.getCompanyFax() == null || this.getCompanyFax().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getCompanyFax());
			}
			if(this.getCompanyPhone() == null || this.getCompanyPhone().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getCompanyPhone());
			}
			if(this.getCompanyZipCode() == null || this.getCompanyZipCode().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getCompanyZipCode());
			}
			if(this.getCompanyAddress() == null || this.getCompanyAddress().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getCompanyAddress());
			}
			if(this.getHomeFax() == null || this.getHomeFax().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getHomeFax());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getHomePhone());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getHomeZipCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getHomeAddress());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getPhone());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getZipCode());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getPostalAddress());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getIDExpDate());
			}
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(76, 12);
			} else {
				pstmt.setString(76, this.getContNo());
			}
			pstmt.setInt(77, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(78, 12);
			} else {
				pstmt.setString(78, this.getFillNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getInsuredNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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
		SQLString sqlObj = new SQLString("LBPOInsured");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBPOInsured(GrpContNo ,ContNo ,InputNo ,FillNo ,InsuredNo ,PrtNo ,AppntNo ,ManageCom ,ExecuteCom ,FamilyID ,RelationToMainInsured ,RelationToAppnt ,AddressNo ,SequenceNo ,Name ,Sex ,Birthday ,IDType ,IDNo ,NativePlace ,Nationality ,RgtAddress ,Marriage ,MarriageDate ,Health ,Stature ,Avoirdupois ,Degree ,CreditGrade ,BankCode ,BankAccNo ,AccName ,JoinCompanyDate ,Position ,Salary ,OccupationType ,StartWorkDate ,OccupationCode ,WorkType ,PluralityType ,SmokeFlag ,ContPlanCode ,Operator ,InsuredStat ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,UWFlag ,UWCode ,UWDate ,UWTime ,BMI ,InsuredPeoples ,License ,LicenseType ,CustomerSeqNo ,SocialInsuNo ,SignName ,EMail ,Mobile ,CompanyFax ,CompanyPhone ,CompanyZipCode ,CompanyAddress ,HomeFax ,HomePhone ,HomeZipCode ,HomeAddress ,Fax ,Phone ,ZipCode ,PostalAddress ,SocialInsuFlag ,IDExpDate) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getContNo());
			}
			pstmt.setInt(3, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getFillNo());
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
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getBirthday());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(18, 12);
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
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getMarriageDate());
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getHealth());
			}
			if(this.getStature() == null || this.getStature().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStature());
			}
			if(this.getAvoirdupois() == null || this.getAvoirdupois().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getAvoirdupois());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(29, 12);
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
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getJoinCompanyDate());
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getPosition());
			}
			if(this.getSalary() == null || this.getSalary().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getSalary());
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOccupationType());
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getStartWorkDate());
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
				pstmt.setNull(41, 12);
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
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getInsuredStat());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getMakeDate());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getModifyDate());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getModifyTime());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getUWFlag());
			}
			if(this.getUWCode() == null || this.getUWCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getUWCode());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getUWDate());
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getUWTime());
			}
			if(this.getBMI() == null || this.getBMI().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getBMI());
			}
			if(this.getInsuredPeoples() == null || this.getInsuredPeoples().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getInsuredPeoples());
			}
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getLicenseType());
			}
			if(this.getCustomerSeqNo() == null || this.getCustomerSeqNo().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getCustomerSeqNo());
			}
			if(this.getSocialInsuNo() == null || this.getSocialInsuNo().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getSocialInsuNo());
			}
			if(this.getSignName() == null || this.getSignName().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getSignName());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getEMail());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getMobile());
			}
			if(this.getCompanyFax() == null || this.getCompanyFax().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getCompanyFax());
			}
			if(this.getCompanyPhone() == null || this.getCompanyPhone().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getCompanyPhone());
			}
			if(this.getCompanyZipCode() == null || this.getCompanyZipCode().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getCompanyZipCode());
			}
			if(this.getCompanyAddress() == null || this.getCompanyAddress().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getCompanyAddress());
			}
			if(this.getHomeFax() == null || this.getHomeFax().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getHomeFax());
			}
			if(this.getHomePhone() == null || this.getHomePhone().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getHomePhone());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getHomeZipCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getHomeAddress());
			}
			if(this.getFax() == null || this.getFax().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getFax());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getPhone());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getZipCode());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getPostalAddress());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getIDExpDate());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBPOInsured WHERE  ContNo = ? AND InputNo = ? AND FillNo = ? AND InsuredNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.setInt(2, this.getInputNo());
			if(this.getFillNo() == null || this.getFillNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFillNo());
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
					tError.moduleName = "LBPOInsuredDB";
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
			tError.moduleName = "LBPOInsuredDB";
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

	public LBPOInsuredSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPOInsuredSet aLBPOInsuredSet = new LBPOInsuredSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOInsured");
			LBPOInsuredSchema aSchema = this.getSchema();
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
				LBPOInsuredSchema s1 = new LBPOInsuredSchema();
				s1.setSchema(rs,i);
				aLBPOInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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

		return aLBPOInsuredSet;

	}

	public LBPOInsuredSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOInsuredSet aLBPOInsuredSet = new LBPOInsuredSet();

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
				LBPOInsuredSchema s1 = new LBPOInsuredSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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

		return aLBPOInsuredSet;
	}

	public LBPOInsuredSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPOInsuredSet aLBPOInsuredSet = new LBPOInsuredSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPOInsured");
			LBPOInsuredSchema aSchema = this.getSchema();
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

				LBPOInsuredSchema s1 = new LBPOInsuredSchema();
				s1.setSchema(rs,i);
				aLBPOInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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

		return aLBPOInsuredSet;

	}

	public LBPOInsuredSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPOInsuredSet aLBPOInsuredSet = new LBPOInsuredSet();

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

				LBPOInsuredSchema s1 = new LBPOInsuredSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOInsuredSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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

		return aLBPOInsuredSet;
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
			SQLString sqlObj = new SQLString("LBPOInsured");
			LBPOInsuredSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBPOInsured " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBPOInsuredDB";
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
			tError.moduleName = "LBPOInsuredDB";
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
        tError.moduleName = "LBPOInsuredDB";
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
        tError.moduleName = "LBPOInsuredDB";
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
        tError.moduleName = "LBPOInsuredDB";
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
        tError.moduleName = "LBPOInsuredDB";
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
 * @return LBPOInsuredSet
 */
public LBPOInsuredSet getData()
{
    int tCount = 0;
    LBPOInsuredSet tLBPOInsuredSet = new LBPOInsuredSet();
    LBPOInsuredSchema tLBPOInsuredSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOInsuredDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBPOInsuredSchema = new LBPOInsuredSchema();
        tLBPOInsuredSchema.setSchema(mResultSet, 1);
        tLBPOInsuredSet.add(tLBPOInsuredSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBPOInsuredSchema = new LBPOInsuredSchema();
                tLBPOInsuredSchema.setSchema(mResultSet, 1);
                tLBPOInsuredSet.add(tLBPOInsuredSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBPOInsuredDB";
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
    return tLBPOInsuredSet;
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
            tError.moduleName = "LBPOInsuredDB";
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
        tError.moduleName = "LBPOInsuredDB";
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
            tError.moduleName = "LBPOInsuredDB";
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
        tError.moduleName = "LBPOInsuredDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBPOInsuredSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOInsuredSet aLBPOInsuredSet = new LBPOInsuredSet();

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
				LBPOInsuredSchema s1 = new LBPOInsuredSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOInsuredSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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

		return aLBPOInsuredSet;
	}

	public LBPOInsuredSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPOInsuredSet aLBPOInsuredSet = new LBPOInsuredSet();

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

				LBPOInsuredSchema s1 = new LBPOInsuredSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPOInsuredDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPOInsuredSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOInsuredDB";
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

		return aLBPOInsuredSet; 
	}

}
