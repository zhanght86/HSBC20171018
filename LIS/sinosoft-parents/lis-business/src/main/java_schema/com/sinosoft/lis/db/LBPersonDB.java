/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPersonSchema;
import com.sinosoft.lis.vschema.LBPersonSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPersonDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LBPersonDB extends LBPersonSchema
{
private static Logger logger = Logger.getLogger(LBPersonDB.class);

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
	public LBPersonDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBPerson" );
		mflag = true;
	}

	public LBPersonDB()
	{
		con = null;
		db = new DBOper( "LBPerson" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LBPersonSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LBPersonSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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
			pstmt = con.prepareStatement("DELETE FROM LBPerson WHERE  CustomerNo = ?");
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
			tError.moduleName = "LBPersonDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPerson");
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
		SQLString sqlObj = new SQLString("LBPerson");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LBPerson SET  EdorNo = ? , CustomerNo = ? , Password = ? , Name = ? , Sex = ? , Birthday = ? , NativePlace = ? , Nationality = ? , Marriage = ? , MarriageDate = ? , OccupationType = ? , StartWorkDate = ? , Salary = ? , Health = ? , Stature = ? , Avoirdupois = ? , CreditGrade = ? , IDType = ? , Proterty = ? , IDNo = ? , OthIDType = ? , OthIDNo = ? , ICNo = ? , HomeAddressCode = ? , HomeAddress = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , BP = ? , Mobile = ? , EMail = ? , BankCode = ? , BankAccNo = ? , JoinCompanyDate = ? , Position = ? , GrpNo = ? , GrpName = ? , GrpPhone = ? , GrpAddressCode = ? , GrpAddress = ? , DeathDate = ? , Remark = ? , State = ? , BlacklistFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , WorkType = ? , PluralityType = ? , OccupationCode = ? , Degree = ? , GrpZipCode = ? , SmokeFlag = ? , RgtAddress = ? , HomeZipCode = ? , Phone2 = ? WHERE  CustomerNo = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCustomerNo());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPassword());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(6, 91);
			} else {
				pstmt.setDate(6, Date.valueOf(this.getBirthday()));
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getNationality());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getOccupationType());
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getStartWorkDate()));
			}
			pstmt.setDouble(13, this.getSalary());
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getHealth());
			}
			pstmt.setDouble(15, this.getStature());
			pstmt.setDouble(16, this.getAvoirdupois());
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getCreditGrade());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getIDType());
			}
			if(this.getProterty() == null || this.getProterty().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getProterty());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getIDNo());
			}
			if(this.getOthIDType() == null || this.getOthIDType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getOthIDType());
			}
			if(this.getOthIDNo() == null || this.getOthIDNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getOthIDNo());
			}
			if(this.getICNo() == null || this.getICNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getICNo());
			}
			if(this.getHomeAddressCode() == null || this.getHomeAddressCode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getHomeAddressCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getHomeAddress());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPhone());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getBP());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getMobile());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getEMail());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBankAccNo());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getPosition());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getGrpNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGrpName());
			}
			if(this.getGrpPhone() == null || this.getGrpPhone().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getGrpPhone());
			}
			if(this.getGrpAddressCode() == null || this.getGrpAddressCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getGrpAddressCode());
			}
			if(this.getGrpAddress() == null || this.getGrpAddress().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getGrpAddress());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getDeathDate()));
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getRemark());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getState());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getBlacklistFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getModifyTime());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getPluralityType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getOccupationCode());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getDegree());
			}
			if(this.getGrpZipCode() == null || this.getGrpZipCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getGrpZipCode());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getSmokeFlag());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getRgtAddress());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getHomeZipCode());
			}
			if(this.getPhone2() == null || this.getPhone2().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getPhone2());
			}
			// set where condition
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getCustomerNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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
		SQLString sqlObj = new SQLString("LBPerson");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LBPerson(EdorNo ,CustomerNo ,Password ,Name ,Sex ,Birthday ,NativePlace ,Nationality ,Marriage ,MarriageDate ,OccupationType ,StartWorkDate ,Salary ,Health ,Stature ,Avoirdupois ,CreditGrade ,IDType ,Proterty ,IDNo ,OthIDType ,OthIDNo ,ICNo ,HomeAddressCode ,HomeAddress ,PostalAddress ,ZipCode ,Phone ,BP ,Mobile ,EMail ,BankCode ,BankAccNo ,JoinCompanyDate ,Position ,GrpNo ,GrpName ,GrpPhone ,GrpAddressCode ,GrpAddress ,DeathDate ,Remark ,State ,BlacklistFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,WorkType ,PluralityType ,OccupationCode ,Degree ,GrpZipCode ,SmokeFlag ,RgtAddress ,HomeZipCode ,Phone2) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCustomerNo());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPassword());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(6, 91);
			} else {
				pstmt.setDate(6, Date.valueOf(this.getBirthday()));
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getNationality());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(9, 1);
			} else {
				pstmt.setString(9, this.getMarriage());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getOccupationType());
			}
			if(this.getStartWorkDate() == null || this.getStartWorkDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getStartWorkDate()));
			}
			pstmt.setDouble(13, this.getSalary());
			if(this.getHealth() == null || this.getHealth().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getHealth());
			}
			pstmt.setDouble(15, this.getStature());
			pstmt.setDouble(16, this.getAvoirdupois());
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getCreditGrade());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getIDType());
			}
			if(this.getProterty() == null || this.getProterty().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getProterty());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getIDNo());
			}
			if(this.getOthIDType() == null || this.getOthIDType().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getOthIDType());
			}
			if(this.getOthIDNo() == null || this.getOthIDNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getOthIDNo());
			}
			if(this.getICNo() == null || this.getICNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getICNo());
			}
			if(this.getHomeAddressCode() == null || this.getHomeAddressCode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getHomeAddressCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getHomeAddress());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getPhone());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getBP());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getMobile());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getEMail());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBankAccNo());
			}
			if(this.getJoinCompanyDate() == null || this.getJoinCompanyDate().equals("null")) {
				pstmt.setNull(34, 91);
			} else {
				pstmt.setDate(34, Date.valueOf(this.getJoinCompanyDate()));
			}
			if(this.getPosition() == null || this.getPosition().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getPosition());
			}
			if(this.getGrpNo() == null || this.getGrpNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getGrpNo());
			}
			if(this.getGrpName() == null || this.getGrpName().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGrpName());
			}
			if(this.getGrpPhone() == null || this.getGrpPhone().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getGrpPhone());
			}
			if(this.getGrpAddressCode() == null || this.getGrpAddressCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getGrpAddressCode());
			}
			if(this.getGrpAddress() == null || this.getGrpAddress().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getGrpAddress());
			}
			if(this.getDeathDate() == null || this.getDeathDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getDeathDate()));
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getRemark());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getState());
			}
			if(this.getBlacklistFlag() == null || this.getBlacklistFlag().equals("null")) {
				pstmt.setNull(44, 1);
			} else {
				pstmt.setString(44, this.getBlacklistFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(46, 91);
			} else {
				pstmt.setDate(46, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(49, 1);
			} else {
				pstmt.setString(49, this.getModifyTime());
			}
			if(this.getWorkType() == null || this.getWorkType().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getWorkType());
			}
			if(this.getPluralityType() == null || this.getPluralityType().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getPluralityType());
			}
			if(this.getOccupationCode() == null || this.getOccupationCode().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getOccupationCode());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getDegree());
			}
			if(this.getGrpZipCode() == null || this.getGrpZipCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getGrpZipCode());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(55, 1);
			} else {
				pstmt.setString(55, this.getSmokeFlag());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getRgtAddress());
			}
			if(this.getHomeZipCode() == null || this.getHomeZipCode().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getHomeZipCode());
			}
			if(this.getPhone2() == null || this.getPhone2().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getPhone2());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LBPerson WHERE  CustomerNo = ?", 
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
					tError.moduleName = "LBPersonDB";
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
			tError.moduleName = "LBPersonDB";
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

	public LBPersonSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPersonSet aLBPersonSet = new LBPersonSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPerson");
			LBPersonSchema aSchema = this.getSchema();
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
				LBPersonSchema s1 = new LBPersonSchema();
				s1.setSchema(rs,i);
				aLBPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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

		return aLBPersonSet;

	}

	public LBPersonSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPersonSet aLBPersonSet = new LBPersonSet();

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
				LBPersonSchema s1 = new LBPersonSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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

		return aLBPersonSet;
	}

	public LBPersonSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LBPersonSet aLBPersonSet = new LBPersonSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LBPerson");
			LBPersonSchema aSchema = this.getSchema();
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

				LBPersonSchema s1 = new LBPersonSchema();
				s1.setSchema(rs,i);
				aLBPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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

		return aLBPersonSet;

	}

	public LBPersonSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBPersonSet aLBPersonSet = new LBPersonSet();

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

				LBPersonSchema s1 = new LBPersonSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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

		return aLBPersonSet;
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
			SQLString sqlObj = new SQLString("LBPerson");
			LBPersonSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBPerson " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LBPersonDB";
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
			tError.moduleName = "LBPersonDB";
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
        tError.moduleName = "LBPersonDB";
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
        tError.moduleName = "LBPersonDB";
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
        tError.moduleName = "LBPersonDB";
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
        tError.moduleName = "LBPersonDB";
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
 * @return LBPersonSet
 */
public LBPersonSet getData()
{
    int tCount = 0;
    LBPersonSet tLBPersonSet = new LBPersonSet();
    LBPersonSchema tLBPersonSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LBPersonDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLBPersonSchema = new LBPersonSchema();
        tLBPersonSchema.setSchema(mResultSet, 1);
        tLBPersonSet.add(tLBPersonSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLBPersonSchema = new LBPersonSchema();
                tLBPersonSchema.setSchema(mResultSet, 1);
                tLBPersonSet.add(tLBPersonSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LBPersonDB";
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
    return tLBPersonSet;
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
            tError.moduleName = "LBPersonDB";
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
        tError.moduleName = "LBPersonDB";
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
            tError.moduleName = "LBPersonDB";
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
        tError.moduleName = "LBPersonDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LBPersonSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPersonSet aLBPersonSet = new LBPersonSet();

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
				LBPersonSchema s1 = new LBPersonSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPersonSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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

		return aLBPersonSet;
	}

	public LBPersonSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LBPersonSet aLBPersonSet = new LBPersonSet();

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

				LBPersonSchema s1 = new LBPersonSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LBPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLBPersonSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPersonDB";
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

		return aLBPersonSet; 
	}

}
