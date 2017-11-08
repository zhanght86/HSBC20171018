/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.TempLAAgentSchema;
import com.sinosoft.lis.vschema.TempLAAgentSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: TempLAAgentDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class TempLAAgentDBSet extends TempLAAgentSet
{
private static Logger logger = Logger.getLogger(TempLAAgentDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public TempLAAgentDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"TempLAAgent");
		mflag = true;
	}

	public TempLAAgentDBSet()
	{
		db = new DBOper( "TempLAAgent" );
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
			tError.moduleName = "TempLAAgentDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM TempLAAgent WHERE  EdorNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("TempLAAgent");
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
			tError.moduleName = "TempLAAgentDBSet";
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
			pstmt = con.prepareStatement("UPDATE TempLAAgent SET  EdorNo = ? , AgentCode = ? , AgentGroup = ? , ManageCom = ? , Password = ? , EntryNo = ? , Name = ? , Sex = ? , Birthday = ? , NativePlace = ? , Nationality = ? , Marriage = ? , CreditGrade = ? , HomeAddressCode = ? , HomeAddress = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , BP = ? , Mobile = ? , EMail = ? , MarriageDate = ? , IDNo = ? , Source = ? , BloodType = ? , PolityVisage = ? , Degree = ? , GraduateSchool = ? , Speciality = ? , PostTitle = ? , ForeignLevel = ? , WorkAge = ? , OldCom = ? , OldOccupation = ? , HeadShip = ? , RecommendAgent = ? , Business = ? , SaleQuaf = ? , QuafNo = ? , QuafStartDate = ? , QuafEndDate = ? , DevNo1 = ? , DevNo2 = ? , RetainContNo = ? , AgentKind = ? , DevGrade = ? , InsideFlag = ? , FullTimeFlag = ? , NoWorkFlag = ? , TrainDate = ? , EmployDate = ? , InDueFormDate = ? , OutWorkDate = ? , RecommendNo = ? , CautionerName = ? , CautionerSex = ? , CautionerID = ? , CautionerBirthday = ? , Approver = ? , ApproveDate = ? , AssuMoney = ? , Remark = ? , AgentState = ? , QualiPassFlag = ? , SmokeFlag = ? , RgtAddress = ? , BankCode = ? , BankAccNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , BranchType = ? , TrainPeriods = ? , BranchCode = ? , Age = ? , ChannelName = ? , IDType = ? , ConFFlag = ? , UpAgent = ? , AgentGrade = ? , TreeBranchCode = ? WHERE  EdorNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentGroup());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPassword());
			}
			if(this.get(i).getEntryNo() == null || this.get(i).getEntryNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getEntryNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNationality());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMarriage());
			}
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCreditGrade());
			}
			if(this.get(i).getHomeAddressCode() == null || this.get(i).getHomeAddressCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getHomeAddressCode());
			}
			if(this.get(i).getHomeAddress() == null || this.get(i).getHomeAddress().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getHomeAddress());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPhone());
			}
			if(this.get(i).getBP() == null || this.get(i).getBP().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBP());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMobile());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getEMail());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getMarriageDate()));
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getIDNo());
			}
			if(this.get(i).getSource() == null || this.get(i).getSource().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSource());
			}
			if(this.get(i).getBloodType() == null || this.get(i).getBloodType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getBloodType());
			}
			if(this.get(i).getPolityVisage() == null || this.get(i).getPolityVisage().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPolityVisage());
			}
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getDegree());
			}
			if(this.get(i).getGraduateSchool() == null || this.get(i).getGraduateSchool().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getGraduateSchool());
			}
			if(this.get(i).getSpeciality() == null || this.get(i).getSpeciality().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getSpeciality());
			}
			if(this.get(i).getPostTitle() == null || this.get(i).getPostTitle().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getPostTitle());
			}
			if(this.get(i).getForeignLevel() == null || this.get(i).getForeignLevel().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getForeignLevel());
			}
			pstmt.setInt(32, this.get(i).getWorkAge());
			if(this.get(i).getOldCom() == null || this.get(i).getOldCom().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getOldCom());
			}
			if(this.get(i).getOldOccupation() == null || this.get(i).getOldOccupation().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOldOccupation());
			}
			if(this.get(i).getHeadShip() == null || this.get(i).getHeadShip().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getHeadShip());
			}
			if(this.get(i).getRecommendAgent() == null || this.get(i).getRecommendAgent().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getRecommendAgent());
			}
			if(this.get(i).getBusiness() == null || this.get(i).getBusiness().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBusiness());
			}
			if(this.get(i).getSaleQuaf() == null || this.get(i).getSaleQuaf().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSaleQuaf());
			}
			if(this.get(i).getQuafNo() == null || this.get(i).getQuafNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getQuafNo());
			}
			if(this.get(i).getQuafStartDate() == null || this.get(i).getQuafStartDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getQuafStartDate()));
			}
			if(this.get(i).getQuafEndDate() == null || this.get(i).getQuafEndDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getQuafEndDate()));
			}
			if(this.get(i).getDevNo1() == null || this.get(i).getDevNo1().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getDevNo1());
			}
			if(this.get(i).getDevNo2() == null || this.get(i).getDevNo2().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getDevNo2());
			}
			if(this.get(i).getRetainContNo() == null || this.get(i).getRetainContNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getRetainContNo());
			}
			if(this.get(i).getAgentKind() == null || this.get(i).getAgentKind().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getAgentKind());
			}
			if(this.get(i).getDevGrade() == null || this.get(i).getDevGrade().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getDevGrade());
			}
			if(this.get(i).getInsideFlag() == null || this.get(i).getInsideFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getInsideFlag());
			}
			if(this.get(i).getFullTimeFlag() == null || this.get(i).getFullTimeFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getFullTimeFlag());
			}
			if(this.get(i).getNoWorkFlag() == null || this.get(i).getNoWorkFlag().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getNoWorkFlag());
			}
			if(this.get(i).getTrainDate() == null || this.get(i).getTrainDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getTrainDate()));
			}
			if(this.get(i).getEmployDate() == null || this.get(i).getEmployDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getEmployDate()));
			}
			if(this.get(i).getInDueFormDate() == null || this.get(i).getInDueFormDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getInDueFormDate()));
			}
			if(this.get(i).getOutWorkDate() == null || this.get(i).getOutWorkDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getOutWorkDate()));
			}
			if(this.get(i).getRecommendNo() == null || this.get(i).getRecommendNo().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getRecommendNo());
			}
			if(this.get(i).getCautionerName() == null || this.get(i).getCautionerName().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getCautionerName());
			}
			if(this.get(i).getCautionerSex() == null || this.get(i).getCautionerSex().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getCautionerSex());
			}
			if(this.get(i).getCautionerID() == null || this.get(i).getCautionerID().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getCautionerID());
			}
			if(this.get(i).getCautionerBirthday() == null || this.get(i).getCautionerBirthday().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getCautionerBirthday()));
			}
			if(this.get(i).getApprover() == null || this.get(i).getApprover().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getApprover());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(60,null);
			} else {
				pstmt.setDate(60, Date.valueOf(this.get(i).getApproveDate()));
			}
			pstmt.setDouble(61, this.get(i).getAssuMoney());
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getRemark());
			}
			if(this.get(i).getAgentState() == null || this.get(i).getAgentState().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAgentState());
			}
			if(this.get(i).getQualiPassFlag() == null || this.get(i).getQualiPassFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getQualiPassFlag());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRgtAddress());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getBankAccNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(72,null);
			} else {
				pstmt.setDate(72, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getBranchType());
			}
			if(this.get(i).getTrainPeriods() == null || this.get(i).getTrainPeriods().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getTrainPeriods());
			}
			if(this.get(i).getBranchCode() == null || this.get(i).getBranchCode().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getBranchCode());
			}
			pstmt.setDouble(77, this.get(i).getAge());
			if(this.get(i).getChannelName() == null || this.get(i).getChannelName().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getChannelName());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getIDType());
			}
			if(this.get(i).getConFFlag() == null || this.get(i).getConFFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getConFFlag());
			}
			if(this.get(i).getUpAgent() == null || this.get(i).getUpAgent().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getUpAgent());
			}
			if(this.get(i).getAgentGrade() == null || this.get(i).getAgentGrade().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getAgentGrade());
			}
			if(this.get(i).getTreeBranchCode() == null || this.get(i).getTreeBranchCode().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getTreeBranchCode());
			}
			// set where condition
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(84,null);
			} else {
				pstmt.setString(84, this.get(i).getEdorNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("TempLAAgent");
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
			tError.moduleName = "TempLAAgentDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO TempLAAgent(EdorNo ,AgentCode ,AgentGroup ,ManageCom ,Password ,EntryNo ,Name ,Sex ,Birthday ,NativePlace ,Nationality ,Marriage ,CreditGrade ,HomeAddressCode ,HomeAddress ,PostalAddress ,ZipCode ,Phone ,BP ,Mobile ,EMail ,MarriageDate ,IDNo ,Source ,BloodType ,PolityVisage ,Degree ,GraduateSchool ,Speciality ,PostTitle ,ForeignLevel ,WorkAge ,OldCom ,OldOccupation ,HeadShip ,RecommendAgent ,Business ,SaleQuaf ,QuafNo ,QuafStartDate ,QuafEndDate ,DevNo1 ,DevNo2 ,RetainContNo ,AgentKind ,DevGrade ,InsideFlag ,FullTimeFlag ,NoWorkFlag ,TrainDate ,EmployDate ,InDueFormDate ,OutWorkDate ,RecommendNo ,CautionerName ,CautionerSex ,CautionerID ,CautionerBirthday ,Approver ,ApproveDate ,AssuMoney ,Remark ,AgentState ,QualiPassFlag ,SmokeFlag ,RgtAddress ,BankCode ,BankAccNo ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,BranchType ,TrainPeriods ,BranchCode ,Age ,ChannelName ,IDType ,ConFFlag ,UpAgent ,AgentGrade ,TreeBranchCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAgentGroup());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPassword());
			}
			if(this.get(i).getEntryNo() == null || this.get(i).getEntryNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getEntryNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getNativePlace() == null || this.get(i).getNativePlace().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getNativePlace());
			}
			if(this.get(i).getNationality() == null || this.get(i).getNationality().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNationality());
			}
			if(this.get(i).getMarriage() == null || this.get(i).getMarriage().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMarriage());
			}
			if(this.get(i).getCreditGrade() == null || this.get(i).getCreditGrade().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCreditGrade());
			}
			if(this.get(i).getHomeAddressCode() == null || this.get(i).getHomeAddressCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getHomeAddressCode());
			}
			if(this.get(i).getHomeAddress() == null || this.get(i).getHomeAddress().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getHomeAddress());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getZipCode());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPhone());
			}
			if(this.get(i).getBP() == null || this.get(i).getBP().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBP());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getMobile());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getEMail());
			}
			if(this.get(i).getMarriageDate() == null || this.get(i).getMarriageDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getMarriageDate()));
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getIDNo());
			}
			if(this.get(i).getSource() == null || this.get(i).getSource().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSource());
			}
			if(this.get(i).getBloodType() == null || this.get(i).getBloodType().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getBloodType());
			}
			if(this.get(i).getPolityVisage() == null || this.get(i).getPolityVisage().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getPolityVisage());
			}
			if(this.get(i).getDegree() == null || this.get(i).getDegree().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getDegree());
			}
			if(this.get(i).getGraduateSchool() == null || this.get(i).getGraduateSchool().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getGraduateSchool());
			}
			if(this.get(i).getSpeciality() == null || this.get(i).getSpeciality().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getSpeciality());
			}
			if(this.get(i).getPostTitle() == null || this.get(i).getPostTitle().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getPostTitle());
			}
			if(this.get(i).getForeignLevel() == null || this.get(i).getForeignLevel().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getForeignLevel());
			}
			pstmt.setInt(32, this.get(i).getWorkAge());
			if(this.get(i).getOldCom() == null || this.get(i).getOldCom().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getOldCom());
			}
			if(this.get(i).getOldOccupation() == null || this.get(i).getOldOccupation().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOldOccupation());
			}
			if(this.get(i).getHeadShip() == null || this.get(i).getHeadShip().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getHeadShip());
			}
			if(this.get(i).getRecommendAgent() == null || this.get(i).getRecommendAgent().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getRecommendAgent());
			}
			if(this.get(i).getBusiness() == null || this.get(i).getBusiness().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBusiness());
			}
			if(this.get(i).getSaleQuaf() == null || this.get(i).getSaleQuaf().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSaleQuaf());
			}
			if(this.get(i).getQuafNo() == null || this.get(i).getQuafNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getQuafNo());
			}
			if(this.get(i).getQuafStartDate() == null || this.get(i).getQuafStartDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getQuafStartDate()));
			}
			if(this.get(i).getQuafEndDate() == null || this.get(i).getQuafEndDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getQuafEndDate()));
			}
			if(this.get(i).getDevNo1() == null || this.get(i).getDevNo1().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getDevNo1());
			}
			if(this.get(i).getDevNo2() == null || this.get(i).getDevNo2().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getDevNo2());
			}
			if(this.get(i).getRetainContNo() == null || this.get(i).getRetainContNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getRetainContNo());
			}
			if(this.get(i).getAgentKind() == null || this.get(i).getAgentKind().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getAgentKind());
			}
			if(this.get(i).getDevGrade() == null || this.get(i).getDevGrade().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getDevGrade());
			}
			if(this.get(i).getInsideFlag() == null || this.get(i).getInsideFlag().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getInsideFlag());
			}
			if(this.get(i).getFullTimeFlag() == null || this.get(i).getFullTimeFlag().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getFullTimeFlag());
			}
			if(this.get(i).getNoWorkFlag() == null || this.get(i).getNoWorkFlag().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getNoWorkFlag());
			}
			if(this.get(i).getTrainDate() == null || this.get(i).getTrainDate().equals("null")) {
				pstmt.setDate(50,null);
			} else {
				pstmt.setDate(50, Date.valueOf(this.get(i).getTrainDate()));
			}
			if(this.get(i).getEmployDate() == null || this.get(i).getEmployDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getEmployDate()));
			}
			if(this.get(i).getInDueFormDate() == null || this.get(i).getInDueFormDate().equals("null")) {
				pstmt.setDate(52,null);
			} else {
				pstmt.setDate(52, Date.valueOf(this.get(i).getInDueFormDate()));
			}
			if(this.get(i).getOutWorkDate() == null || this.get(i).getOutWorkDate().equals("null")) {
				pstmt.setDate(53,null);
			} else {
				pstmt.setDate(53, Date.valueOf(this.get(i).getOutWorkDate()));
			}
			if(this.get(i).getRecommendNo() == null || this.get(i).getRecommendNo().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getRecommendNo());
			}
			if(this.get(i).getCautionerName() == null || this.get(i).getCautionerName().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getCautionerName());
			}
			if(this.get(i).getCautionerSex() == null || this.get(i).getCautionerSex().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getCautionerSex());
			}
			if(this.get(i).getCautionerID() == null || this.get(i).getCautionerID().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getCautionerID());
			}
			if(this.get(i).getCautionerBirthday() == null || this.get(i).getCautionerBirthday().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getCautionerBirthday()));
			}
			if(this.get(i).getApprover() == null || this.get(i).getApprover().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getApprover());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(60,null);
			} else {
				pstmt.setDate(60, Date.valueOf(this.get(i).getApproveDate()));
			}
			pstmt.setDouble(61, this.get(i).getAssuMoney());
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getRemark());
			}
			if(this.get(i).getAgentState() == null || this.get(i).getAgentState().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAgentState());
			}
			if(this.get(i).getQualiPassFlag() == null || this.get(i).getQualiPassFlag().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getQualiPassFlag());
			}
			if(this.get(i).getSmokeFlag() == null || this.get(i).getSmokeFlag().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getSmokeFlag());
			}
			if(this.get(i).getRgtAddress() == null || this.get(i).getRgtAddress().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getRgtAddress());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getBankAccNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(70,null);
			} else {
				pstmt.setDate(70, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(72,null);
			} else {
				pstmt.setDate(72, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(73,null);
			} else {
				pstmt.setString(73, this.get(i).getModifyTime());
			}
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getBranchType());
			}
			if(this.get(i).getTrainPeriods() == null || this.get(i).getTrainPeriods().equals("null")) {
				pstmt.setString(75,null);
			} else {
				pstmt.setString(75, this.get(i).getTrainPeriods());
			}
			if(this.get(i).getBranchCode() == null || this.get(i).getBranchCode().equals("null")) {
				pstmt.setString(76,null);
			} else {
				pstmt.setString(76, this.get(i).getBranchCode());
			}
			pstmt.setDouble(77, this.get(i).getAge());
			if(this.get(i).getChannelName() == null || this.get(i).getChannelName().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getChannelName());
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(79,null);
			} else {
				pstmt.setString(79, this.get(i).getIDType());
			}
			if(this.get(i).getConFFlag() == null || this.get(i).getConFFlag().equals("null")) {
				pstmt.setString(80,null);
			} else {
				pstmt.setString(80, this.get(i).getConFFlag());
			}
			if(this.get(i).getUpAgent() == null || this.get(i).getUpAgent().equals("null")) {
				pstmt.setString(81,null);
			} else {
				pstmt.setString(81, this.get(i).getUpAgent());
			}
			if(this.get(i).getAgentGrade() == null || this.get(i).getAgentGrade().equals("null")) {
				pstmt.setString(82,null);
			} else {
				pstmt.setString(82, this.get(i).getAgentGrade());
			}
			if(this.get(i).getTreeBranchCode() == null || this.get(i).getTreeBranchCode().equals("null")) {
				pstmt.setString(83,null);
			} else {
				pstmt.setString(83, this.get(i).getTreeBranchCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("TempLAAgent");
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
			tError.moduleName = "TempLAAgentDBSet";
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
