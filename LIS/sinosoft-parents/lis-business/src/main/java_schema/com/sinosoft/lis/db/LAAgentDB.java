/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LAAgentDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAAgentDB extends LAAgentSchema
{
private static Logger logger = Logger.getLogger(LAAgentDB.class);

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
	public LAAgentDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LAAgent" );
		mflag = true;
	}

	public LAAgentDB()
	{
		con = null;
		db = new DBOper( "LAAgent" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LAAgentSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LAAgentSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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
			pstmt = con.prepareStatement("DELETE FROM LAAgent WHERE  AgentCode = ?");
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LAAgent");
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
		SQLString sqlObj = new SQLString("LAAgent");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LAAgent SET  AgentCode = ? , AgentGroup = ? , ManageCom = ? , Password = ? , EntryNo = ? , Name = ? , Sex = ? , Birthday = ? , NativePlace = ? , Nationality = ? , Marriage = ? , CreditGrade = ? , HomeAddressCode = ? , HomeAddress = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , BP = ? , Mobile = ? , EMail = ? , MarriageDate = ? , IDNo = ? , Source = ? , BloodType = ? , PolityVisage = ? , Degree = ? , GraduateSchool = ? , Speciality = ? , PostTitle = ? , ForeignLevel = ? , WorkAge = ? , OldCom = ? , OldOccupation = ? , HeadShip = ? , RecommendAgent = ? , Business = ? , SaleQuaf = ? , QuafNo = ? , QuafStartDate = ? , QuafEndDate = ? , DevNo1 = ? , DevNo2 = ? , RetainContNo = ? , AgentKind = ? , DevGrade = ? , InsideFlag = ? , FullTimeFlag = ? , NoWorkFlag = ? , TrainDate = ? , EmployDate = ? , InDueFormDate = ? , OutWorkDate = ? , RecommendNo = ? , CautionerName = ? , CautionerSex = ? , CautionerID = ? , CautionerBirthday = ? , Approver = ? , ApproveDate = ? , AssuMoney = ? , Remark = ? , AgentState = ? , QualiPassFlag = ? , SmokeFlag = ? , RgtAddress = ? , BankCode = ? , BankAccNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchType = ? , TrainPeriods = ? , BranchCode = ? , Age = ? , ChannelName = ? , IDType = ? WHERE  AgentCode = ?");
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentGroup());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getManageCom());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPassword());
			}
			if(this.getEntryNo() == null || this.getEntryNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getEntryNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getBirthday()));
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getNationality());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getMarriage());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getCreditGrade());
			}
			if(this.getHomeAddressCode() == null || this.getHomeAddressCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getHomeAddressCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getHomeAddress());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPhone());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getBP());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getMobile());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getEMail());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getIDNo());
			}
			if(this.getSource() == null || this.getSource().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getSource());
			}
			if(this.getBloodType() == null || this.getBloodType().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBloodType());
			}
			if(this.getPolityVisage() == null || this.getPolityVisage().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getPolityVisage());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getDegree());
			}
			if(this.getGraduateSchool() == null || this.getGraduateSchool().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getGraduateSchool());
			}
			if(this.getSpeciality() == null || this.getSpeciality().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getSpeciality());
			}
			if(this.getPostTitle() == null || this.getPostTitle().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getPostTitle());
			}
			if(this.getForeignLevel() == null || this.getForeignLevel().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getForeignLevel());
			}
			pstmt.setInt(31, this.getWorkAge());
			if(this.getOldCom() == null || this.getOldCom().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getOldCom());
			}
			if(this.getOldOccupation() == null || this.getOldOccupation().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getOldOccupation());
			}
			if(this.getHeadShip() == null || this.getHeadShip().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getHeadShip());
			}
			if(this.getRecommendAgent() == null || this.getRecommendAgent().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRecommendAgent());
			}
			if(this.getBusiness() == null || this.getBusiness().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getBusiness());
			}
			if(this.getSaleQuaf() == null || this.getSaleQuaf().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getSaleQuaf());
			}
			if(this.getQuafNo() == null || this.getQuafNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getQuafNo());
			}
			if(this.getQuafStartDate() == null || this.getQuafStartDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getQuafStartDate()));
			}
			if(this.getQuafEndDate() == null || this.getQuafEndDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getQuafEndDate()));
			}
			if(this.getDevNo1() == null || this.getDevNo1().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getDevNo1());
			}
			if(this.getDevNo2() == null || this.getDevNo2().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getDevNo2());
			}
			if(this.getRetainContNo() == null || this.getRetainContNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getRetainContNo());
			}
			if(this.getAgentKind() == null || this.getAgentKind().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAgentKind());
			}
			if(this.getDevGrade() == null || this.getDevGrade().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDevGrade());
			}
			if(this.getInsideFlag() == null || this.getInsideFlag().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getInsideFlag());
			}
			if(this.getFullTimeFlag() == null || this.getFullTimeFlag().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getFullTimeFlag());
			}
			if(this.getNoWorkFlag() == null || this.getNoWorkFlag().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getNoWorkFlag());
			}
			if(this.getTrainDate() == null || this.getTrainDate().equals("null")) {
				pstmt.setNull(49, 91);
			} else {
				pstmt.setDate(49, Date.valueOf(this.getTrainDate()));
			}
			if(this.getEmployDate() == null || this.getEmployDate().equals("null")) {
				pstmt.setNull(50, 91);
			} else {
				pstmt.setDate(50, Date.valueOf(this.getEmployDate()));
			}
			if(this.getInDueFormDate() == null || this.getInDueFormDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getInDueFormDate()));
			}
			if(this.getOutWorkDate() == null || this.getOutWorkDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getOutWorkDate()));
			}
			if(this.getRecommendNo() == null || this.getRecommendNo().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getRecommendNo());
			}
			if(this.getCautionerName() == null || this.getCautionerName().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getCautionerName());
			}
			if(this.getCautionerSex() == null || this.getCautionerSex().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getCautionerSex());
			}
			if(this.getCautionerID() == null || this.getCautionerID().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getCautionerID());
			}
			if(this.getCautionerBirthday() == null || this.getCautionerBirthday().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getCautionerBirthday()));
			}
			if(this.getApprover() == null || this.getApprover().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getApprover());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(59, 91);
			} else {
				pstmt.setDate(59, Date.valueOf(this.getApproveDate()));
			}
			pstmt.setDouble(60, this.getAssuMoney());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getRemark());
			}
			if(this.getAgentState() == null || this.getAgentState().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAgentState());
			}
			if(this.getQualiPassFlag() == null || this.getQualiPassFlag().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getQualiPassFlag());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, this.getSmokeFlag());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRgtAddress());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getBankAccNo());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(69, 91);
			} else {
				pstmt.setDate(69, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(70, 1);
			} else {
				pstmt.setString(70, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(72, 1);
			} else {
				pstmt.setString(72, this.getModifyTime());
			}
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getBranchType());
			}
			if(this.getTrainPeriods() == null || this.getTrainPeriods().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getTrainPeriods());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getBranchCode());
			}
			pstmt.setDouble(76, this.getAge());
			if(this.getChannelName() == null || this.getChannelName().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getChannelName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getIDType());
			}
			// set where condition
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(79, 12);
			} else {
				pstmt.setString(79, this.getAgentCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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
		SQLString sqlObj = new SQLString("LAAgent");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LAAgent(AgentCode ,AgentGroup ,ManageCom ,Password ,EntryNo ,Name ,Sex ,Birthday ,NativePlace ,Nationality ,Marriage ,CreditGrade ,HomeAddressCode ,HomeAddress ,PostalAddress ,ZipCode ,Phone ,BP ,Mobile ,EMail ,MarriageDate ,IDNo ,Source ,BloodType ,PolityVisage ,Degree ,GraduateSchool ,Speciality ,PostTitle ,ForeignLevel ,WorkAge ,OldCom ,OldOccupation ,HeadShip ,RecommendAgent ,Business ,SaleQuaf ,QuafNo ,QuafStartDate ,QuafEndDate ,DevNo1 ,DevNo2 ,RetainContNo ,AgentKind ,DevGrade ,InsideFlag ,FullTimeFlag ,NoWorkFlag ,TrainDate ,EmployDate ,InDueFormDate ,OutWorkDate ,RecommendNo ,CautionerName ,CautionerSex ,CautionerID ,CautionerBirthday ,Approver ,ApproveDate ,AssuMoney ,Remark ,AgentState ,QualiPassFlag ,SmokeFlag ,RgtAddress ,BankCode ,BankAccNo ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchType ,TrainPeriods ,BranchCode ,Age ,ChannelName ,IDType) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getAgentGroup());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getManageCom());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPassword());
			}
			if(this.getEntryNo() == null || this.getEntryNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getEntryNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getBirthday()));
			}
			if(this.getNativePlace() == null || this.getNativePlace().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getNativePlace());
			}
			if(this.getNationality() == null || this.getNationality().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getNationality());
			}
			if(this.getMarriage() == null || this.getMarriage().equals("null")) {
				pstmt.setNull(11, 1);
			} else {
				pstmt.setString(11, this.getMarriage());
			}
			if(this.getCreditGrade() == null || this.getCreditGrade().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getCreditGrade());
			}
			if(this.getHomeAddressCode() == null || this.getHomeAddressCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getHomeAddressCode());
			}
			if(this.getHomeAddress() == null || this.getHomeAddress().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getHomeAddress());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPhone());
			}
			if(this.getBP() == null || this.getBP().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getBP());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getMobile());
			}
			if(this.getEMail() == null || this.getEMail().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getEMail());
			}
			if(this.getMarriageDate() == null || this.getMarriageDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getMarriageDate()));
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getIDNo());
			}
			if(this.getSource() == null || this.getSource().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getSource());
			}
			if(this.getBloodType() == null || this.getBloodType().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getBloodType());
			}
			if(this.getPolityVisage() == null || this.getPolityVisage().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getPolityVisage());
			}
			if(this.getDegree() == null || this.getDegree().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getDegree());
			}
			if(this.getGraduateSchool() == null || this.getGraduateSchool().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getGraduateSchool());
			}
			if(this.getSpeciality() == null || this.getSpeciality().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getSpeciality());
			}
			if(this.getPostTitle() == null || this.getPostTitle().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getPostTitle());
			}
			if(this.getForeignLevel() == null || this.getForeignLevel().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getForeignLevel());
			}
			pstmt.setInt(31, this.getWorkAge());
			if(this.getOldCom() == null || this.getOldCom().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getOldCom());
			}
			if(this.getOldOccupation() == null || this.getOldOccupation().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getOldOccupation());
			}
			if(this.getHeadShip() == null || this.getHeadShip().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getHeadShip());
			}
			if(this.getRecommendAgent() == null || this.getRecommendAgent().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRecommendAgent());
			}
			if(this.getBusiness() == null || this.getBusiness().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getBusiness());
			}
			if(this.getSaleQuaf() == null || this.getSaleQuaf().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getSaleQuaf());
			}
			if(this.getQuafNo() == null || this.getQuafNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getQuafNo());
			}
			if(this.getQuafStartDate() == null || this.getQuafStartDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getQuafStartDate()));
			}
			if(this.getQuafEndDate() == null || this.getQuafEndDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getQuafEndDate()));
			}
			if(this.getDevNo1() == null || this.getDevNo1().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getDevNo1());
			}
			if(this.getDevNo2() == null || this.getDevNo2().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getDevNo2());
			}
			if(this.getRetainContNo() == null || this.getRetainContNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getRetainContNo());
			}
			if(this.getAgentKind() == null || this.getAgentKind().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAgentKind());
			}
			if(this.getDevGrade() == null || this.getDevGrade().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDevGrade());
			}
			if(this.getInsideFlag() == null || this.getInsideFlag().equals("null")) {
				pstmt.setNull(46, 1);
			} else {
				pstmt.setString(46, this.getInsideFlag());
			}
			if(this.getFullTimeFlag() == null || this.getFullTimeFlag().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getFullTimeFlag());
			}
			if(this.getNoWorkFlag() == null || this.getNoWorkFlag().equals("null")) {
				pstmt.setNull(48, 1);
			} else {
				pstmt.setString(48, this.getNoWorkFlag());
			}
			if(this.getTrainDate() == null || this.getTrainDate().equals("null")) {
				pstmt.setNull(49, 91);
			} else {
				pstmt.setDate(49, Date.valueOf(this.getTrainDate()));
			}
			if(this.getEmployDate() == null || this.getEmployDate().equals("null")) {
				pstmt.setNull(50, 91);
			} else {
				pstmt.setDate(50, Date.valueOf(this.getEmployDate()));
			}
			if(this.getInDueFormDate() == null || this.getInDueFormDate().equals("null")) {
				pstmt.setNull(51, 91);
			} else {
				pstmt.setDate(51, Date.valueOf(this.getInDueFormDate()));
			}
			if(this.getOutWorkDate() == null || this.getOutWorkDate().equals("null")) {
				pstmt.setNull(52, 91);
			} else {
				pstmt.setDate(52, Date.valueOf(this.getOutWorkDate()));
			}
			if(this.getRecommendNo() == null || this.getRecommendNo().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getRecommendNo());
			}
			if(this.getCautionerName() == null || this.getCautionerName().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getCautionerName());
			}
			if(this.getCautionerSex() == null || this.getCautionerSex().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getCautionerSex());
			}
			if(this.getCautionerID() == null || this.getCautionerID().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getCautionerID());
			}
			if(this.getCautionerBirthday() == null || this.getCautionerBirthday().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getCautionerBirthday()));
			}
			if(this.getApprover() == null || this.getApprover().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getApprover());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(59, 91);
			} else {
				pstmt.setDate(59, Date.valueOf(this.getApproveDate()));
			}
			pstmt.setDouble(60, this.getAssuMoney());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getRemark());
			}
			if(this.getAgentState() == null || this.getAgentState().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAgentState());
			}
			if(this.getQualiPassFlag() == null || this.getQualiPassFlag().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getQualiPassFlag());
			}
			if(this.getSmokeFlag() == null || this.getSmokeFlag().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, this.getSmokeFlag());
			}
			if(this.getRgtAddress() == null || this.getRgtAddress().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRgtAddress());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getBankAccNo());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(69, 91);
			} else {
				pstmt.setDate(69, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(70, 1);
			} else {
				pstmt.setString(70, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(71, 91);
			} else {
				pstmt.setDate(71, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(72, 1);
			} else {
				pstmt.setString(72, this.getModifyTime());
			}
			if(this.getBranchType() == null || this.getBranchType().equals("null")) {
				pstmt.setNull(73, 12);
			} else {
				pstmt.setString(73, this.getBranchType());
			}
			if(this.getTrainPeriods() == null || this.getTrainPeriods().equals("null")) {
				pstmt.setNull(74, 12);
			} else {
				pstmt.setString(74, this.getTrainPeriods());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(75, 12);
			} else {
				pstmt.setString(75, this.getBranchCode());
			}
			pstmt.setDouble(76, this.getAge());
			if(this.getChannelName() == null || this.getChannelName().equals("null")) {
				pstmt.setNull(77, 12);
			} else {
				pstmt.setString(77, this.getChannelName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(78, 1);
			} else {
				pstmt.setString(78, this.getIDType());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LAAgent WHERE  AgentCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getAgentCode());
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
					tError.moduleName = "LAAgentDB";
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
			tError.moduleName = "LAAgentDB";
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

	public LAAgentSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LAAgentSet aLAAgentSet = new LAAgentSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LAAgent");
			LAAgentSchema aSchema = this.getSchema();
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
				LAAgentSchema s1 = new LAAgentSchema();
				s1.setSchema(rs,i);
				aLAAgentSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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

		return aLAAgentSet;

	}

	public LAAgentSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LAAgentSet aLAAgentSet = new LAAgentSet();

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
				LAAgentSchema s1 = new LAAgentSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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

		return aLAAgentSet;
	}

	public LAAgentSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LAAgentSet aLAAgentSet = new LAAgentSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LAAgent");
			LAAgentSchema aSchema = this.getSchema();
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

				LAAgentSchema s1 = new LAAgentSchema();
				s1.setSchema(rs,i);
				aLAAgentSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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

		return aLAAgentSet;

	}

	public LAAgentSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LAAgentSet aLAAgentSet = new LAAgentSet();

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

				LAAgentSchema s1 = new LAAgentSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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

		return aLAAgentSet;
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
			SQLString sqlObj = new SQLString("LAAgent");
			LAAgentSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LAAgent " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LAAgentDB";
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
			tError.moduleName = "LAAgentDB";
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
        tError.moduleName = "LAAgentDB";
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
        tError.moduleName = "LAAgentDB";
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
        tError.moduleName = "LAAgentDB";
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
        tError.moduleName = "LAAgentDB";
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
 * @return LAAgentSet
 */
public LAAgentSet getData()
{
    int tCount = 0;
    LAAgentSet tLAAgentSet = new LAAgentSet();
    LAAgentSchema tLAAgentSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LAAgentDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLAAgentSchema = new LAAgentSchema();
        tLAAgentSchema.setSchema(mResultSet, 1);
        tLAAgentSet.add(tLAAgentSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLAAgentSchema = new LAAgentSchema();
                tLAAgentSchema.setSchema(mResultSet, 1);
                tLAAgentSet.add(tLAAgentSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LAAgentDB";
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
    return tLAAgentSet;
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
            tError.moduleName = "LAAgentDB";
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
        tError.moduleName = "LAAgentDB";
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
            tError.moduleName = "LAAgentDB";
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
        tError.moduleName = "LAAgentDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LAAgentSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LAAgentSet aLAAgentSet = new LAAgentSet();

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
				LAAgentSchema s1 = new LAAgentSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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

		return aLAAgentSet;
	}

	public LAAgentSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LAAgentSet aLAAgentSet = new LAAgentSet();

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

				LAAgentSchema s1 = new LAAgentSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LAAgentDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLAAgentSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAgentDB";
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

		return aLAAgentSet; 
	}

}
