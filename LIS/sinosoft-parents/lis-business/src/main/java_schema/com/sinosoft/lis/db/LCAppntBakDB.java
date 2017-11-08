/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCAppntBakSchema;
import com.sinosoft.lis.vschema.LCAppntBakSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCAppntBakDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LCAppntBakDB extends LCAppntBakSchema
{
private static Logger logger = Logger.getLogger(LCAppntBakDB.class);

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
	public LCAppntBakDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCAppntBak" );
		mflag = true;
	}

	public LCAppntBakDB()
	{
		con = null;
		db = new DBOper( "LCAppntBak" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCAppntBakSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCAppntBakSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCAppntBak WHERE  ContNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCAppntBak");
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
		SQLString sqlObj = new SQLString("LCAppntBak");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCAppntBak SET  GrpContNo = ? , ContNo = ? , PrtNo = ? , AppntNo = ? , AppntGrade = ? , AppntName = ? , AppntSex = ? , AppntBirthday = ? , AppntType = ? , AddressNo = ? , IDType = ? , IDNo = ? , NativePlace = ? , Nationality = ? , RgtAddress = ? , Marriage = ? , MarriageDate = ? , Health = ? , Stature = ? , Avoirdupois = ? , Degree = ? , CreditGrade = ? , BankCode = ? , BankAccNo = ? , AccName = ? , JoinCompanyDate = ? , StartWorkDate = ? , Position = ? , Salary = ? , OccupationType = ? , OccupationCode = ? , WorkType = ? , PluralityType = ? , SmokeFlag = ? , Operator = ? , ManageCom = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BMI = ? , License = ? , LicenseType = ? , RelationToInsured = ? , SocialInsuFlag = ? , IDExpDate = ? WHERE  ContNo = ?");
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
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPrtNo());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAppntNo());
			}
			if(this.getAppntGrade() == null || this.getAppntGrade().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAppntGrade());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getAppntName());
			}
			if(this.getAppntSex() == null || this.getAppntSex().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getAppntSex());
			}
			if(this.getAppntBirthday() == null || this.getAppntBirthday().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getAppntBirthday()));
			}
			if(this.getAppntType() == null || this.getAppntType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getAppntType());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAddressNo());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIDNo());
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getNationality());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRgtAddress());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getHealth());
			}
			pstmt.setDouble(19, this.getStature());
			pstmt.setDouble(20, this.getAvoirdupois());
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getCreditGrade());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAccName());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getStartWorkDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPosition());
			}
			pstmt.setDouble(29, this.getSalary());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getOccupationType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getOccupationCode());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getPluralityType());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getSmokeFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOperator());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getManageCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getModifyTime());
			}
			pstmt.setDouble(41, this.getBMI());
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getLicenseType());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getRelationToInsured());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getIDExpDate()));
			}
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getContNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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
		SQLString sqlObj = new SQLString("LCAppntBak");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCAppntBak(GrpContNo ,ContNo ,PrtNo ,AppntNo ,AppntGrade ,AppntName ,AppntSex ,AppntBirthday ,AppntType ,AddressNo ,IDType ,IDNo ,NativePlace ,Nationality ,RgtAddress ,Marriage ,MarriageDate ,Health ,Stature ,Avoirdupois ,Degree ,CreditGrade ,BankCode ,BankAccNo ,AccName ,JoinCompanyDate ,StartWorkDate ,Position ,Salary ,OccupationType ,OccupationCode ,WorkType ,PluralityType ,SmokeFlag ,Operator ,ManageCom ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BMI ,License ,LicenseType ,RelationToInsured ,SocialInsuFlag ,IDExpDate) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPrtNo());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAppntNo());
			}
			if(this.getAppntGrade() == null || this.getAppntGrade().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAppntGrade());
			}
			if(this.getAppntName() == null || this.getAppntName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getAppntName());
			}
			if(this.getAppntSex() == null || this.getAppntSex().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getAppntSex());
			}
			if(this.getAppntBirthday() == null || this.getAppntBirthday().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getAppntBirthday()));
			}
			if(this.getAppntType() == null || this.getAppntType().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getAppntType());
			}
			if(this.getAddressNo() == null || this.getAddressNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAddressNo());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getIDNo());
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getNationality());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRgtAddress());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(16, 1);
			} else {
				pstmt.setString(16, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getHealth());
			}
			pstmt.setDouble(19, this.getStature());
			pstmt.setDouble(20, this.getAvoirdupois());
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getDegree());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getCreditGrade());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAccName());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getStartWorkDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPosition());
			}
			pstmt.setDouble(29, this.getSalary());
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getOccupationType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getOccupationCode());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getPluralityType());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getSmokeFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOperator());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getManageCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getModifyTime());
			}
			pstmt.setDouble(41, this.getBMI());
			if(this.getLicense() == null || this.getLicense().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getLicense());
			}
			if(this.getLicenseType() == null || this.getLicenseType().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getLicenseType());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getRelationToInsured());
			}
			if(this.getSocialInsuFlag() == null || this.getSocialInsuFlag().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getSocialInsuFlag());
			}
			if(this.getIDExpDate() == null || this.getIDExpDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getIDExpDate()));
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCAppntBak WHERE  ContNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
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
					tError.moduleName = "LCAppntBakDB";
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
			tError.moduleName = "LCAppntBakDB";
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

	public LCAppntBakSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCAppntBakSet aLCAppntBakSet = new LCAppntBakSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCAppntBak");
			LCAppntBakSchema aSchema = this.getSchema();
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
				LCAppntBakSchema s1 = new LCAppntBakSchema();
				s1.setSchema(rs,i);
				aLCAppntBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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

		return aLCAppntBakSet;

	}

	public LCAppntBakSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAppntBakSet aLCAppntBakSet = new LCAppntBakSet();

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
				LCAppntBakSchema s1 = new LCAppntBakSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAppntBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAppntBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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

		return aLCAppntBakSet;
	}

	public LCAppntBakSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCAppntBakSet aLCAppntBakSet = new LCAppntBakSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCAppntBak");
			LCAppntBakSchema aSchema = this.getSchema();
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

				LCAppntBakSchema s1 = new LCAppntBakSchema();
				s1.setSchema(rs,i);
				aLCAppntBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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

		return aLCAppntBakSet;

	}

	public LCAppntBakSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAppntBakSet aLCAppntBakSet = new LCAppntBakSet();

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

				LCAppntBakSchema s1 = new LCAppntBakSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAppntBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAppntBakSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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

		return aLCAppntBakSet;
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
			SQLString sqlObj = new SQLString("LCAppntBak");
			LCAppntBakSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCAppntBak " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAppntBakDB";
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
			tError.moduleName = "LCAppntBakDB";
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
        tError.moduleName = "LCAppntBakDB";
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
        tError.moduleName = "LCAppntBakDB";
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
        tError.moduleName = "LCAppntBakDB";
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
        tError.moduleName = "LCAppntBakDB";
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
 * @return LCAppntBakSet
 */
public LCAppntBakSet getData()
{
    int tCount = 0;
    LCAppntBakSet tLCAppntBakSet = new LCAppntBakSet();
    LCAppntBakSchema tLCAppntBakSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCAppntBakDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCAppntBakSchema = new LCAppntBakSchema();
        tLCAppntBakSchema.setSchema(mResultSet, 1);
        tLCAppntBakSet.add(tLCAppntBakSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCAppntBakSchema = new LCAppntBakSchema();
                tLCAppntBakSchema.setSchema(mResultSet, 1);
                tLCAppntBakSet.add(tLCAppntBakSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCAppntBakDB";
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
    return tLCAppntBakSet;
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
            tError.moduleName = "LCAppntBakDB";
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
        tError.moduleName = "LCAppntBakDB";
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
            tError.moduleName = "LCAppntBakDB";
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
        tError.moduleName = "LCAppntBakDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCAppntBakSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCAppntBakSet aLCAppntBakSet = new LCAppntBakSet();

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
				LCAppntBakSchema s1 = new LCAppntBakSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAppntBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAppntBakSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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

		return aLCAppntBakSet;
	}

	public LCAppntBakSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCAppntBakSet aLCAppntBakSet = new LCAppntBakSet();

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

				LCAppntBakSchema s1 = new LCAppntBakSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAppntBakDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAppntBakSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAppntBakDB";
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

		return aLCAppntBakSet; 
	}

}
