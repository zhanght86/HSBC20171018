/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPersonSchema;
import com.sinosoft.lis.vschema.LBPersonSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPersonDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LBPersonDBSet extends LBPersonSet
{
private static Logger logger = Logger.getLogger(LBPersonDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBPersonDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPerson");
		mflag = true;
	}

	public LBPersonDBSet()
	{
		db = new DBOper( "LBPerson" );
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
			tError.moduleName = "LBPersonDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPerson WHERE  CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPerson");
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
			tError.moduleName = "LBPersonDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPerson SET  EdorNo = ? , CustomerNo = ? , Password = ? , Name = ? , Sex = ? , Birthday = ? , NativePlace = ? , Nationality = ? , Marriage = ? , MarriageDate = ? , OccupationType = ? , StartWorkDate = ? , Salary = ? , Health = ? , Stature = ? , Avoirdupois = ? , CreditGrade = ? , IDType = ? , Proterty = ? , IDNo = ? , OthIDType = ? , OthIDNo = ? , ICNo = ? , HomeAddressCode = ? , HomeAddress = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , BP = ? , Mobile = ? , EMail = ? , BankCode = ? , BankAccNo = ? , JoinCompanyDate = ? , Position = ? , GrpNo = ? , GrpName = ? , GrpPhone = ? , GrpAddressCode = ? , GrpAddress = ? , DeathDate = ? , Remark = ? , State = ? , BlacklistFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , WorkType = ? , PluralityType = ? , OccupationCode = ? , Degree = ? , GrpZipCode = ? , SmokeFlag = ? , RgtAddress = ? , HomeZipCode = ? , Phone2 = ? WHERE  CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerNo());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPassword());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getNationality());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMarriage());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMarriageDate()));
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getOccupationType());
			}
			if(this.get(i).getStartWorkDate() == null || this.get(i).getStartWorkDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getStartWorkDate()));
			}
			pstmt.setDouble(13, this.get(i).getSalary());
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getHealth());
			}
			pstmt.setDouble(15, this.get(i).getStature());
			pstmt.setDouble(16, this.get(i).getAvoirdupois());
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCreditGrade());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getIDType());
			}
			if(this.get(i).getProterty() == null || this.get(i).getProterty().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getProterty());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getIDNo());
			}
			if(this.get(i).getOthIDType() == null || this.get(i).getOthIDType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getOthIDType());
			}
			if(this.get(i).getOthIDNo() == null || this.get(i).getOthIDNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOthIDNo());
			}
			if(this.get(i).getICNo() == null || this.get(i).getICNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getICNo());
			}
			if(this.get(i).getHomeAddressCode() == null || this.get(i).getHomeAddressCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getHomeAddressCode());
			}
			if(this.get(i).getHomeAddress() == null || this.get(i).getHomeAddress().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getHomeAddress());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPhone());
			}
			if(this.get(i).getBP() == null || this.get(i).getBP().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getBP());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMobile());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getEMail());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBankAccNo());
			}
			if(this.get(i).getJoinCompanyDate() == null || this.get(i).getJoinCompanyDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getJoinCompanyDate()));
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getPosition());
			}
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getGrpNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpPhone() == null || this.get(i).getGrpPhone().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getGrpPhone());
			}
			if(this.get(i).getGrpAddressCode() == null || this.get(i).getGrpAddressCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGrpAddressCode());
			}
			if(this.get(i).getGrpAddress() == null || this.get(i).getGrpAddress().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGrpAddress());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getRemark());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getState());
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getModifyTime());
			}
			if(this.get(i).getWorkType() == null || this.get(i).getWorkType().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getWorkType());
			}
			if(this.get(i).getPluralityType() == null || this.get(i).getPluralityType().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPluralityType());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getOccupationCode());
			}
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getDegree());
			}
			if(this.get(i).getGrpZipCode() == null || this.get(i).getGrpZipCode().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getGrpZipCode());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getRgtAddress());
			}
			if(this.get(i).getHomeZipCode() == null || this.get(i).getHomeZipCode().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getHomeZipCode());
			}
			if(this.get(i).getPhone2() == null || this.get(i).getPhone2().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getPhone2());
			}
			// set where condition
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPerson");
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
			tError.moduleName = "LBPersonDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPerson(EdorNo ,CustomerNo ,Password ,Name ,Sex ,Birthday ,NativePlace ,Nationality ,Marriage ,MarriageDate ,OccupationType ,StartWorkDate ,Salary ,Health ,Stature ,Avoirdupois ,CreditGrade ,IDType ,Proterty ,IDNo ,OthIDType ,OthIDNo ,ICNo ,HomeAddressCode ,HomeAddress ,PostalAddress ,ZipCode ,Phone ,BP ,Mobile ,EMail ,BankCode ,BankAccNo ,JoinCompanyDate ,Position ,GrpNo ,GrpName ,GrpPhone ,GrpAddressCode ,GrpAddress ,DeathDate ,Remark ,State ,BlacklistFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,WorkType ,PluralityType ,OccupationCode ,Degree ,GrpZipCode ,SmokeFlag ,RgtAddress ,HomeZipCode ,Phone2) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerNo());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPassword());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getNationality());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMarriage());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getMarriageDate()));
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getOccupationType());
			}
			if(this.get(i).getStartWorkDate() == null || this.get(i).getStartWorkDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getStartWorkDate()));
			}
			pstmt.setDouble(13, this.get(i).getSalary());
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getHealth());
			}
			pstmt.setDouble(15, this.get(i).getStature());
			pstmt.setDouble(16, this.get(i).getAvoirdupois());
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCreditGrade());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getIDType());
			}
			if(this.get(i).getProterty() == null || this.get(i).getProterty().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getProterty());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getIDNo());
			}
			if(this.get(i).getOthIDType() == null || this.get(i).getOthIDType().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getOthIDType());
			}
			if(this.get(i).getOthIDNo() == null || this.get(i).getOthIDNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOthIDNo());
			}
			if(this.get(i).getICNo() == null || this.get(i).getICNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getICNo());
			}
			if(this.get(i).getHomeAddressCode() == null || this.get(i).getHomeAddressCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getHomeAddressCode());
			}
			if(this.get(i).getHomeAddress() == null || this.get(i).getHomeAddress().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getHomeAddress());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPhone());
			}
			if(this.get(i).getBP() == null || this.get(i).getBP().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getBP());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMobile());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getEMail());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBankAccNo());
			}
			if(this.get(i).getJoinCompanyDate() == null || this.get(i).getJoinCompanyDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getJoinCompanyDate()));
			}
			if(this.get(i).getPosition() == null || this.get(i).getPosition().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getPosition());
			}
			if(this.get(i).getGrpNo() == null || this.get(i).getGrpNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getGrpNo());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getGrpName());
			}
			if(this.get(i).getGrpPhone() == null || this.get(i).getGrpPhone().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getGrpPhone());
			}
			if(this.get(i).getGrpAddressCode() == null || this.get(i).getGrpAddressCode().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGrpAddressCode());
			}
			if(this.get(i).getGrpAddress() == null || this.get(i).getGrpAddress().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGrpAddress());
			}
			if(this.get(i).getDeathDate() == null || this.get(i).getDeathDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getDeathDate()));
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getRemark());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getState());
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getModifyTime());
			}
			if(this.get(i).getWorkType() == null || this.get(i).getWorkType().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getWorkType());
			}
			if(this.get(i).getPluralityType() == null || this.get(i).getPluralityType().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getPluralityType());
			}
			if(this.get(i).getOccupationCode() == null || this.get(i).getOccupationCode().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getOccupationCode());
			}
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getDegree());
			}
			if(this.get(i).getGrpZipCode() == null || this.get(i).getGrpZipCode().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getGrpZipCode());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getRgtAddress());
			}
			if(this.get(i).getHomeZipCode() == null || this.get(i).getHomeZipCode().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getHomeZipCode());
			}
			if(this.get(i).getPhone2() == null || this.get(i).getPhone2().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getPhone2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPerson");
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
			tError.moduleName = "LBPersonDBSet";
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
