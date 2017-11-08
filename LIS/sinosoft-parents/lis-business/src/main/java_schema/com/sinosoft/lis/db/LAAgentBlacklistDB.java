/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LAAgentBlacklistSchema;
import com.sinosoft.lis.vschema.LAAgentBlacklistSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LAAgentBlacklistDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAAgentBlacklistDB extends LAAgentBlacklistSchema
{
private static Logger logger = Logger.getLogger(LAAgentBlacklistDB.class);

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
	public LAAgentBlacklistDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LAAgentBlacklist" );
		mflag = true;
	}

	public LAAgentBlacklistDB()
	{
		con = null;
		db = new DBOper( "LAAgentBlacklist" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LAAgentBlacklistSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LAAgentBlacklistSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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
			pstmt = con.prepareStatement("DELETE FROM LAAgentBlacklist WHERE  BlackListCode = ?");
			if(this.getBlackListCode() == null || this.getBlackListCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBlackListCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAAgentBlacklist");
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
		SQLString sqlObj = new SQLString("LAAgentBlacklist");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LAAgentBlacklist SET  BlackListCode = ? , Name = ? , Sex = ? , Birthday = ? , NativePlace = ? , Nationality = ? , Marriage = ? , CreditGrade = ? , HomeAddressCode = ? , HomeAddress = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , BP = ? , Mobile = ? , EMail = ? , MarriageDate = ? , IDNo = ? , Source = ? , BloodType = ? , PolityVisage = ? , Degree = ? , GraduateSchool = ? , Speciality = ? , PostTitle = ? , ForeignLevel = ? , WorkAge = ? , OldCom = ? , OldOccupation = ? , HeadShip = ? , Business = ? , BlacklistReason = ? , InsurerCompany = ? , AgentName = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  BlackListCode = ?");
			if(this.getBlackListCode() == null || this.getBlackListCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBlackListCode());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(4, 91);
			} else {
				pstmt.setDate(4, Date.valueOf(this.getBirthday()));
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getNationality());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getMarriage());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getCreditGrade());
			}
			if(this.getHomeAddressCode() == null || this.getHomeAddressCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getHomeAddressCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getHomeAddress());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPhone());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getBP());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getMobile());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getEMail());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getIDNo());
			}
			if(this.getSource() == null || this.getSource().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getSource());
			}
			if(this.getBloodType() == null || this.getBloodType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBloodType());
			}
			if(this.getPolityVisage() == null || this.getPolityVisage().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPolityVisage());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getDegree());
			}
			if(this.getGraduateSchool() == null || this.getGraduateSchool().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getGraduateSchool());
			}
			if(this.getSpeciality() == null || this.getSpeciality().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSpeciality());
			}
			if(this.getPostTitle() == null || this.getPostTitle().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getPostTitle());
			}
			if(this.getForeignLevel() == null || this.getForeignLevel().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getForeignLevel());
			}
			pstmt.setInt(27, this.getWorkAge());
			if(this.getOldCom() == null || this.getOldCom().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOldCom());
			}
			if(this.getOldOccupation() == null || this.getOldOccupation().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getOldOccupation());
			}
			if(this.getHeadShip() == null || this.getHeadShip().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getHeadShip());
			}
			if(this.getBusiness() == null || this.getBusiness().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBusiness());
			}
			if(this.getBlacklistReason() == null || this.getBlacklistReason().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBlacklistReason());
			}
			if(this.getInsurerCompany() == null || this.getInsurerCompany().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getInsurerCompany());
			}
			if(this.getAgentName() == null || this.getAgentName().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getAgentName());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getModifyTime());
			}
			// set where condition
			if(this.getBlackListCode() == null || this.getBlackListCode().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getBlackListCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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
		SQLString sqlObj = new SQLString("LAAgentBlacklist");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LAAgentBlacklist(BlackListCode ,Name ,Sex ,Birthday ,NativePlace ,Nationality ,Marriage ,CreditGrade ,HomeAddressCode ,HomeAddress ,PostalAddress ,ZipCode ,Phone ,BP ,Mobile ,EMail ,MarriageDate ,IDNo ,Source ,BloodType ,PolityVisage ,Degree ,GraduateSchool ,Speciality ,PostTitle ,ForeignLevel ,WorkAge ,OldCom ,OldOccupation ,HeadShip ,Business ,BlacklistReason ,InsurerCompany ,AgentName ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBlackListCode() == null || this.getBlackListCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBlackListCode());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(3, 1);
			} else {
				pstmt.setString(3, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(4, 91);
			} else {
				pstmt.setDate(4, Date.valueOf(this.getBirthday()));
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getNationality());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getMarriage());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(8, 1);
			} else {
				pstmt.setString(8, this.getCreditGrade());
			}
			if(this.getHomeAddressCode() == null || this.getHomeAddressCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getHomeAddressCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getHomeAddress());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPhone());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getBP());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getMobile());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getEMail());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getIDNo());
			}
			if(this.getSource() == null || this.getSource().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getSource());
			}
			if(this.getBloodType() == null || this.getBloodType().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getBloodType());
			}
			if(this.getPolityVisage() == null || this.getPolityVisage().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getPolityVisage());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getDegree());
			}
			if(this.getGraduateSchool() == null || this.getGraduateSchool().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getGraduateSchool());
			}
			if(this.getSpeciality() == null || this.getSpeciality().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSpeciality());
			}
			if(this.getPostTitle() == null || this.getPostTitle().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getPostTitle());
			}
			if(this.getForeignLevel() == null || this.getForeignLevel().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getForeignLevel());
			}
			pstmt.setInt(27, this.getWorkAge());
			if(this.getOldCom() == null || this.getOldCom().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOldCom());
			}
			if(this.getOldOccupation() == null || this.getOldOccupation().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getOldOccupation());
			}
			if(this.getHeadShip() == null || this.getHeadShip().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getHeadShip());
			}
			if(this.getBusiness() == null || this.getBusiness().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBusiness());
			}
			if(this.getBlacklistReason() == null || this.getBlacklistReason().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getBlacklistReason());
			}
			if(this.getInsurerCompany() == null || this.getInsurerCompany().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getInsurerCompany());
			}
			if(this.getAgentName() == null || this.getAgentName().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getAgentName());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LAAgentBlacklist WHERE  BlackListCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBlackListCode() == null || this.getBlackListCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBlackListCode());
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
					tError.moduleName = "LAAgentBlacklistDB";
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
			tError.moduleName = "LAAgentBlacklistDB";
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

	public LAAgentBlacklistSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LAAgentBlacklistSet aLAAgentBlacklistSet = new LAAgentBlacklistSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LAAgentBlacklist");
			LAAgentBlacklistSchema aSchema = this.getSchema();
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
				LAAgentBlacklistSchema s1 = new LAAgentBlacklistSchema();
				s1.setSchema(rs,i);
				aLAAgentBlacklistSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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

		return aLAAgentBlacklistSet;

	}

	public LAAgentBlacklistSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LAAgentBlacklistSet aLAAgentBlacklistSet = new LAAgentBlacklistSet();

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
				LAAgentBlacklistSchema s1 = new LAAgentBlacklistSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentBlacklistDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentBlacklistSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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

		return aLAAgentBlacklistSet;
	}

	public LAAgentBlacklistSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LAAgentBlacklistSet aLAAgentBlacklistSet = new LAAgentBlacklistSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LAAgentBlacklist");
			LAAgentBlacklistSchema aSchema = this.getSchema();
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

				LAAgentBlacklistSchema s1 = new LAAgentBlacklistSchema();
				s1.setSchema(rs,i);
				aLAAgentBlacklistSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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

		return aLAAgentBlacklistSet;

	}

	public LAAgentBlacklistSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LAAgentBlacklistSet aLAAgentBlacklistSet = new LAAgentBlacklistSet();

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

				LAAgentBlacklistSchema s1 = new LAAgentBlacklistSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentBlacklistDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentBlacklistSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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

		return aLAAgentBlacklistSet;
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
			SQLString sqlObj = new SQLString("LAAgentBlacklist");
			LAAgentBlacklistSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LAAgentBlacklist " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LAAgentBlacklistDB";
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
			tError.moduleName = "LAAgentBlacklistDB";
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
        tError.moduleName = "LAAgentBlacklistDB";
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
        tError.moduleName = "LAAgentBlacklistDB";
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
        tError.moduleName = "LAAgentBlacklistDB";
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
        tError.moduleName = "LAAgentBlacklistDB";
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
 * @return LAAgentBlacklistSet
 */
public LAAgentBlacklistSet getData()
{
    int tCount = 0;
    LAAgentBlacklistSet tLAAgentBlacklistSet = new LAAgentBlacklistSet();
    LAAgentBlacklistSchema tLAAgentBlacklistSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LAAgentBlacklistDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLAAgentBlacklistSchema = new LAAgentBlacklistSchema();
        tLAAgentBlacklistSchema.setSchema(mResultSet, 1);
        tLAAgentBlacklistSet.add(tLAAgentBlacklistSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLAAgentBlacklistSchema = new LAAgentBlacklistSchema();
                tLAAgentBlacklistSchema.setSchema(mResultSet, 1);
                tLAAgentBlacklistSet.add(tLAAgentBlacklistSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LAAgentBlacklistDB";
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
    return tLAAgentBlacklistSet;
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
            tError.moduleName = "LAAgentBlacklistDB";
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
        tError.moduleName = "LAAgentBlacklistDB";
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
            tError.moduleName = "LAAgentBlacklistDB";
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
        tError.moduleName = "LAAgentBlacklistDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LAAgentBlacklistSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LAAgentBlacklistSet aLAAgentBlacklistSet = new LAAgentBlacklistSet();

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
				LAAgentBlacklistSchema s1 = new LAAgentBlacklistSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentBlacklistDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentBlacklistSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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

		return aLAAgentBlacklistSet;
	}

	public LAAgentBlacklistSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LAAgentBlacklistSet aLAAgentBlacklistSet = new LAAgentBlacklistSet();

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

				LAAgentBlacklistSchema s1 = new LAAgentBlacklistSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentBlacklistDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentBlacklistSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentBlacklistDB";
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

		return aLAAgentBlacklistSet; 
	}

}
