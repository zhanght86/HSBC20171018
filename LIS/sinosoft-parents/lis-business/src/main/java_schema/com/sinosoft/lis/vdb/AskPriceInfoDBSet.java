/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.AskPriceInfoSchema;
import com.sinosoft.lis.vschema.AskPriceInfoSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: AskPriceInfoDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: Quotation System
 * @CreateDate：2010-09-04
 */
public class AskPriceInfoDBSet extends AskPriceInfoSet
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
	public AskPriceInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"AskPriceInfo");
		mflag = true;
	}

	public AskPriceInfoDBSet()
	{
		db = new DBOper( "AskPriceInfo" );
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
			tError.moduleName = "AskPriceInfoDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM AskPriceInfo WHERE  AskPriceNo = ? AND AskNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAskNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("AskPriceInfo");
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
			tError.moduleName = "AskPriceInfoDBSet";
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
			pstmt = con.prepareStatement("UPDATE AskPriceInfo SET  AskPriceNo = ? , AskNo = ? , BranchType = ? , BranchType2 = ? , ManageCom = ? , AgentCom = ? , AgentCode = ? , UnitName = ? , RegistrationTypes = ? , Incumbency = ? , IndustryType = ? , InsuranceType = ? , Note = ? , AskPriceDate = ? , StartDate = ? , StopDate = ? , Disease = ? , Dangerous = ? , Changes = ? , Health = ? , Rescue = ? , Others1 = ? , Others2 = ? , Remarks = ? , Attribute1 = ? , Attribute2 = ? , Attribute3 = ? , Attribute4 = ? , Attribute5 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , MainInsured = ? , InsuredOther1 = ? , InsuredOther2 = ? , InsuredOther3 = ? , PublicInsured = ? , Medical = ? , Sales = ? WHERE  AskPriceNo = ? AND AskNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAskNo());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBranchType());
			}
			if(this.get(i).getBranchType2() == null || this.get(i).getBranchType2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBranchType2());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentCode());
			}
			if(this.get(i).getUnitName() == null || this.get(i).getUnitName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getUnitName());
			}
			if(this.get(i).getRegistrationTypes() == null || this.get(i).getRegistrationTypes().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRegistrationTypes());
			}
			pstmt.setDouble(10, this.get(i).getIncumbency());
			if(this.get(i).getIndustryType() == null || this.get(i).getIndustryType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIndustryType());
			}
			if(this.get(i).getInsuranceType() == null || this.get(i).getInsuranceType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuranceType());
			}
			if(this.get(i).getNote() == null || this.get(i).getNote().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getNote());
			}
			if(this.get(i).getAskPriceDate() == null || this.get(i).getAskPriceDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getAskPriceDate()));
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getStopDate() == null || this.get(i).getStopDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getStopDate()));
			}
			if(this.get(i).getDisease() == null || this.get(i).getDisease().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getDisease());
			}
			if(this.get(i).getDangerous() == null || this.get(i).getDangerous().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getDangerous());
			}
			if(this.get(i).getChanges() == null || this.get(i).getChanges().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getChanges());
			}
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getHealth());
			}
			if(this.get(i).getRescue() == null || this.get(i).getRescue().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getRescue());
			}
			if(this.get(i).getOthers1() == null || this.get(i).getOthers1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOthers1());
			}
			if(this.get(i).getOthers2() == null || this.get(i).getOthers2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOthers2());
			}
			if(this.get(i).getRemarks() == null || this.get(i).getRemarks().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getRemarks());
			}
			if(this.get(i).getAttribute1() == null || this.get(i).getAttribute1().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAttribute1());
			}
			if(this.get(i).getAttribute2() == null || this.get(i).getAttribute2().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAttribute2());
			}
			if(this.get(i).getAttribute3() == null || this.get(i).getAttribute3().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAttribute3());
			}
			if(this.get(i).getAttribute4() == null || this.get(i).getAttribute4().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAttribute4());
			}
			if(this.get(i).getAttribute5() == null || this.get(i).getAttribute5().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAttribute5());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyTime());
			}
			pstmt.setDouble(35, this.get(i).getMainInsured());
			pstmt.setDouble(36, this.get(i).getInsuredOther1());
			pstmt.setDouble(37, this.get(i).getInsuredOther2());
			pstmt.setDouble(38, this.get(i).getInsuredOther3());
			if(this.get(i).getPublicInsured() == null || this.get(i).getPublicInsured().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getPublicInsured());
			}
			if(this.get(i).getMedical() == null || this.get(i).getMedical().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMedical());
			}
			if(this.get(i).getSales() == null || this.get(i).getSales().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSales());
			}
			// set where condition
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getAskNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("AskPriceInfo");
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
			tError.moduleName = "AskPriceInfoDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO AskPriceInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAskPriceNo() == null || this.get(i).getAskPriceNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAskPriceNo());
			}
			if(this.get(i).getAskNo() == null || this.get(i).getAskNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAskNo());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBranchType());
			}
			if(this.get(i).getBranchType2() == null || this.get(i).getBranchType2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBranchType2());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentCode());
			}
			if(this.get(i).getUnitName() == null || this.get(i).getUnitName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getUnitName());
			}
			if(this.get(i).getRegistrationTypes() == null || this.get(i).getRegistrationTypes().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRegistrationTypes());
			}
			pstmt.setDouble(10, this.get(i).getIncumbency());
			if(this.get(i).getIndustryType() == null || this.get(i).getIndustryType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getIndustryType());
			}
			if(this.get(i).getInsuranceType() == null || this.get(i).getInsuranceType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getInsuranceType());
			}
			if(this.get(i).getNote() == null || this.get(i).getNote().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getNote());
			}
			if(this.get(i).getAskPriceDate() == null || this.get(i).getAskPriceDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getAskPriceDate()));
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getStopDate() == null || this.get(i).getStopDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getStopDate()));
			}
			if(this.get(i).getDisease() == null || this.get(i).getDisease().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getDisease());
			}
			if(this.get(i).getDangerous() == null || this.get(i).getDangerous().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getDangerous());
			}
			if(this.get(i).getChanges() == null || this.get(i).getChanges().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getChanges());
			}
			if(this.get(i).getHealth() == null || this.get(i).getHealth().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getHealth());
			}
			if(this.get(i).getRescue() == null || this.get(i).getRescue().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getRescue());
			}
			if(this.get(i).getOthers1() == null || this.get(i).getOthers1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getOthers1());
			}
			if(this.get(i).getOthers2() == null || this.get(i).getOthers2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOthers2());
			}
			if(this.get(i).getRemarks() == null || this.get(i).getRemarks().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getRemarks());
			}
			if(this.get(i).getAttribute1() == null || this.get(i).getAttribute1().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAttribute1());
			}
			if(this.get(i).getAttribute2() == null || this.get(i).getAttribute2().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAttribute2());
			}
			if(this.get(i).getAttribute3() == null || this.get(i).getAttribute3().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getAttribute3());
			}
			if(this.get(i).getAttribute4() == null || this.get(i).getAttribute4().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getAttribute4());
			}
			if(this.get(i).getAttribute5() == null || this.get(i).getAttribute5().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAttribute5());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyTime());
			}
			pstmt.setDouble(35, this.get(i).getMainInsured());
			pstmt.setDouble(36, this.get(i).getInsuredOther1());
			pstmt.setDouble(37, this.get(i).getInsuredOther2());
			pstmt.setDouble(38, this.get(i).getInsuredOther3());
			if(this.get(i).getPublicInsured() == null || this.get(i).getPublicInsured().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getPublicInsured());
			}
			if(this.get(i).getMedical() == null || this.get(i).getMedical().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMedical());
			}
			if(this.get(i).getSales() == null || this.get(i).getSales().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSales());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("AskPriceInfo");
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
			tError.moduleName = "AskPriceInfoDBSet";
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
