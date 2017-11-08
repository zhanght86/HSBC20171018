/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LOReserveSchema;
import com.sinosoft.lis.vschema.LOReserveSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOReserveDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOReserveDBSet extends LOReserveSet
{
private static Logger logger = Logger.getLogger(LOReserveDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LOReserveDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LOReserve");
		mflag = true;
	}

	public LOReserveDBSet()
	{
		db = new DBOper( "LOReserve" );
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
			tError.moduleName = "LOReserveDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LOReserve WHERE  PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOReserve");
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
			tError.moduleName = "LOReserveDBSet";
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
			pstmt = con.prepareStatement("UPDATE LOReserve SET  ManageCom = ? , ContNo = ? , PolNo = ? , PolStatus = ? , RiskCode = ? , SaleChnl = ? , InsuredNo = ? , InsuredSex = ? , InsuredBirthday = ? , InsuredAppAge = ? , SecInsuredSex = ? , SecBirthDay = ? , SecInsuredAge = ? , CValiDate = ? , LastRevDate = ? , MakeDate = ? , Years = ? , PayYears = ? , PayIntv = ? , StandPrem = ? , Amnt = ? , InvalidDate = ? , FreeStartDate = ? , DeadDate = ? , ShouldGetMoney = ? , StopReason = ? , StopDate = ? , RevGetAge = ? , RevGetDate = ? , RevGetMoney = ? , RevGetIntv = ? , AnnMoney = ? , DeathMoney = ? , MedMoney = ? , ExpMoney = ? , TotalMoney = ? , OccPrem = ? , HealthPrem = ? , LastPayPrem = ? , YearIdx = ? , CalYear = ? , Rev3 = ? , Rev1 = ? , Rev2 = ? , Rev4 = ? , CheckFlag = ? , CalFlag = ? , ModifyDate = ? , SignDate = ? , ThisPayPrem = ? , PayToDate = ? , ManageFeeRate = ? , InsuYearFlag = ? , PayEndYearFlag = ? , SurMoney = ? , SmokingFlag = ? , OccupationType = ? , CustomGetPolDate = ? , FloatRate = ? , GroupNo = ? , GetMoney = ? , GetDutyKind = ? , AgentCom = ? , ACType = ? , EndDate = ? , FreeFlag = ? , AutoPayFlag = ? , Av_last_cal = ? , Acval_cal_date = ? , Av_BOY = ? , Av_BOM = ? , TBMoney = ? , TBMDate = ? , TransSaleChnl = ? , Cuml_PartSurrender = ? , BonusAmnt = ? , OtherPrem = ? WHERE  PolNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getManageCom());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getPolStatus() == null || this.get(i).getPolStatus().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolStatus());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskCode());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getSaleChnl());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(10, this.get(i).getInsuredAppAge());
			if(this.get(i).getSecInsuredSex() == null || this.get(i).getSecInsuredSex().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSecInsuredSex());
			}
			if(this.get(i).getSecBirthDay() == null || this.get(i).getSecBirthDay().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getSecBirthDay()));
			}
			pstmt.setInt(13, this.get(i).getSecInsuredAge());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getLastRevDate() == null || this.get(i).getLastRevDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getLastRevDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getMakeDate()));
			}
			pstmt.setInt(17, this.get(i).getYears());
			pstmt.setInt(18, this.get(i).getPayYears());
			pstmt.setInt(19, this.get(i).getPayIntv());
			pstmt.setDouble(20, this.get(i).getStandPrem());
			pstmt.setDouble(21, this.get(i).getAmnt());
			if(this.get(i).getInvalidDate() == null || this.get(i).getInvalidDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getInvalidDate()));
			}
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getDeadDate() == null || this.get(i).getDeadDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getDeadDate()));
			}
			pstmt.setDouble(25, this.get(i).getShouldGetMoney());
			if(this.get(i).getStopReason() == null || this.get(i).getStopReason().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStopReason());
			}
			if(this.get(i).getStopDate() == null || this.get(i).getStopDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getStopDate()));
			}
			pstmt.setInt(28, this.get(i).getRevGetAge());
			if(this.get(i).getRevGetDate() == null || this.get(i).getRevGetDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getRevGetDate()));
			}
			pstmt.setDouble(30, this.get(i).getRevGetMoney());
			pstmt.setInt(31, this.get(i).getRevGetIntv());
			pstmt.setDouble(32, this.get(i).getAnnMoney());
			pstmt.setDouble(33, this.get(i).getDeathMoney());
			pstmt.setDouble(34, this.get(i).getMedMoney());
			pstmt.setDouble(35, this.get(i).getExpMoney());
			pstmt.setDouble(36, this.get(i).getTotalMoney());
			pstmt.setDouble(37, this.get(i).getOccPrem());
			pstmt.setDouble(38, this.get(i).getHealthPrem());
			pstmt.setDouble(39, this.get(i).getLastPayPrem());
			pstmt.setInt(40, this.get(i).getYearIdx());
			pstmt.setInt(41, this.get(i).getCalYear());
			pstmt.setDouble(42, this.get(i).getRev3());
			pstmt.setDouble(43, this.get(i).getRev1());
			pstmt.setDouble(44, this.get(i).getRev2());
			pstmt.setDouble(45, this.get(i).getRev4());
			pstmt.setInt(46, this.get(i).getCheckFlag());
			pstmt.setInt(47, this.get(i).getCalFlag());
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getSignDate()));
			}
			pstmt.setDouble(50, this.get(i).getThisPayPrem());
			if(this.get(i).getPayToDate() == null || this.get(i).getPayToDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getPayToDate()));
			}
			pstmt.setDouble(52, this.get(i).getManageFeeRate());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getInsuYearFlag());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getPayEndYearFlag());
			}
			pstmt.setDouble(55, this.get(i).getSurMoney());
			if(this.get(i).getSmokingFlag() == null || this.get(i).getSmokingFlag().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSmokingFlag());
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getOccupationType());
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			pstmt.setDouble(59, this.get(i).getFloatRate());
			if(this.get(i).getGroupNo() == null || this.get(i).getGroupNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getGroupNo());
			}
			pstmt.setDouble(61, this.get(i).getGetMoney());
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAgentCom());
			}
			if(this.get(i).getACType() == null || this.get(i).getACType().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getACType());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getFreeFlag());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getAutoPayFlag());
			}
			pstmt.setDouble(68, this.get(i).getAv_last_cal());
			if(this.get(i).getAcval_cal_date() == null || this.get(i).getAcval_cal_date().equals("null")) {
				pstmt.setDate(69,null);
			} else {
				pstmt.setDate(69, Date.valueOf(this.get(i).getAcval_cal_date()));
			}
			pstmt.setDouble(70, this.get(i).getAv_BOY());
			pstmt.setDouble(71, this.get(i).getAv_BOM());
			pstmt.setDouble(72, this.get(i).getTBMoney());
			if(this.get(i).getTBMDate() == null || this.get(i).getTBMDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getTBMDate()));
			}
			if(this.get(i).getTransSaleChnl() == null || this.get(i).getTransSaleChnl().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getTransSaleChnl());
			}
			pstmt.setDouble(75, this.get(i).getCuml_PartSurrender());
			pstmt.setDouble(76, this.get(i).getBonusAmnt());
			pstmt.setDouble(77, this.get(i).getOtherPrem());
			// set where condition
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(78,null);
			} else {
				pstmt.setString(78, this.get(i).getPolNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOReserve");
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
			tError.moduleName = "LOReserveDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LOReserve(ManageCom ,ContNo ,PolNo ,PolStatus ,RiskCode ,SaleChnl ,InsuredNo ,InsuredSex ,InsuredBirthday ,InsuredAppAge ,SecInsuredSex ,SecBirthDay ,SecInsuredAge ,CValiDate ,LastRevDate ,MakeDate ,Years ,PayYears ,PayIntv ,StandPrem ,Amnt ,InvalidDate ,FreeStartDate ,DeadDate ,ShouldGetMoney ,StopReason ,StopDate ,RevGetAge ,RevGetDate ,RevGetMoney ,RevGetIntv ,AnnMoney ,DeathMoney ,MedMoney ,ExpMoney ,TotalMoney ,OccPrem ,HealthPrem ,LastPayPrem ,YearIdx ,CalYear ,Rev3 ,Rev1 ,Rev2 ,Rev4 ,CheckFlag ,CalFlag ,ModifyDate ,SignDate ,ThisPayPrem ,PayToDate ,ManageFeeRate ,InsuYearFlag ,PayEndYearFlag ,SurMoney ,SmokingFlag ,OccupationType ,CustomGetPolDate ,FloatRate ,GroupNo ,GetMoney ,GetDutyKind ,AgentCom ,ACType ,EndDate ,FreeFlag ,AutoPayFlag ,Av_last_cal ,Acval_cal_date ,Av_BOY ,Av_BOM ,TBMoney ,TBMDate ,TransSaleChnl ,Cuml_PartSurrender ,BonusAmnt ,OtherPrem) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getManageCom());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getPolStatus() == null || this.get(i).getPolStatus().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolStatus());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRiskCode());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getSaleChnl());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getInsuredNo());
			}
			if(this.get(i).getInsuredSex() == null || this.get(i).getInsuredSex().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsuredSex());
			}
			if(this.get(i).getInsuredBirthday() == null || this.get(i).getInsuredBirthday().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getInsuredBirthday()));
			}
			pstmt.setInt(10, this.get(i).getInsuredAppAge());
			if(this.get(i).getSecInsuredSex() == null || this.get(i).getSecInsuredSex().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSecInsuredSex());
			}
			if(this.get(i).getSecBirthDay() == null || this.get(i).getSecBirthDay().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getSecBirthDay()));
			}
			pstmt.setInt(13, this.get(i).getSecInsuredAge());
			if(this.get(i).getCValiDate() == null || this.get(i).getCValiDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getCValiDate()));
			}
			if(this.get(i).getLastRevDate() == null || this.get(i).getLastRevDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getLastRevDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getMakeDate()));
			}
			pstmt.setInt(17, this.get(i).getYears());
			pstmt.setInt(18, this.get(i).getPayYears());
			pstmt.setInt(19, this.get(i).getPayIntv());
			pstmt.setDouble(20, this.get(i).getStandPrem());
			pstmt.setDouble(21, this.get(i).getAmnt());
			if(this.get(i).getInvalidDate() == null || this.get(i).getInvalidDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getInvalidDate()));
			}
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getDeadDate() == null || this.get(i).getDeadDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getDeadDate()));
			}
			pstmt.setDouble(25, this.get(i).getShouldGetMoney());
			if(this.get(i).getStopReason() == null || this.get(i).getStopReason().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStopReason());
			}
			if(this.get(i).getStopDate() == null || this.get(i).getStopDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getStopDate()));
			}
			pstmt.setInt(28, this.get(i).getRevGetAge());
			if(this.get(i).getRevGetDate() == null || this.get(i).getRevGetDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getRevGetDate()));
			}
			pstmt.setDouble(30, this.get(i).getRevGetMoney());
			pstmt.setInt(31, this.get(i).getRevGetIntv());
			pstmt.setDouble(32, this.get(i).getAnnMoney());
			pstmt.setDouble(33, this.get(i).getDeathMoney());
			pstmt.setDouble(34, this.get(i).getMedMoney());
			pstmt.setDouble(35, this.get(i).getExpMoney());
			pstmt.setDouble(36, this.get(i).getTotalMoney());
			pstmt.setDouble(37, this.get(i).getOccPrem());
			pstmt.setDouble(38, this.get(i).getHealthPrem());
			pstmt.setDouble(39, this.get(i).getLastPayPrem());
			pstmt.setInt(40, this.get(i).getYearIdx());
			pstmt.setInt(41, this.get(i).getCalYear());
			pstmt.setDouble(42, this.get(i).getRev3());
			pstmt.setDouble(43, this.get(i).getRev1());
			pstmt.setDouble(44, this.get(i).getRev2());
			pstmt.setDouble(45, this.get(i).getRev4());
			pstmt.setInt(46, this.get(i).getCheckFlag());
			pstmt.setInt(47, this.get(i).getCalFlag());
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(48,null);
			} else {
				pstmt.setDate(48, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(49,null);
			} else {
				pstmt.setDate(49, Date.valueOf(this.get(i).getSignDate()));
			}
			pstmt.setDouble(50, this.get(i).getThisPayPrem());
			if(this.get(i).getPayToDate() == null || this.get(i).getPayToDate().equals("null")) {
				pstmt.setDate(51,null);
			} else {
				pstmt.setDate(51, Date.valueOf(this.get(i).getPayToDate()));
			}
			pstmt.setDouble(52, this.get(i).getManageFeeRate());
			if(this.get(i).getInsuYearFlag() == null || this.get(i).getInsuYearFlag().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getInsuYearFlag());
			}
			if(this.get(i).getPayEndYearFlag() == null || this.get(i).getPayEndYearFlag().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getPayEndYearFlag());
			}
			pstmt.setDouble(55, this.get(i).getSurMoney());
			if(this.get(i).getSmokingFlag() == null || this.get(i).getSmokingFlag().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getSmokingFlag());
			}
			if(this.get(i).getOccupationType() == null || this.get(i).getOccupationType().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getOccupationType());
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(58,null);
			} else {
				pstmt.setDate(58, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			pstmt.setDouble(59, this.get(i).getFloatRate());
			if(this.get(i).getGroupNo() == null || this.get(i).getGroupNo().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getGroupNo());
			}
			pstmt.setDouble(61, this.get(i).getGetMoney());
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(62,null);
			} else {
				pstmt.setString(62, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getAgentCom());
			}
			if(this.get(i).getACType() == null || this.get(i).getACType().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getACType());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getFreeFlag());
			}
			if(this.get(i).getAutoPayFlag() == null || this.get(i).getAutoPayFlag().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getAutoPayFlag());
			}
			pstmt.setDouble(68, this.get(i).getAv_last_cal());
			if(this.get(i).getAcval_cal_date() == null || this.get(i).getAcval_cal_date().equals("null")) {
				pstmt.setDate(69,null);
			} else {
				pstmt.setDate(69, Date.valueOf(this.get(i).getAcval_cal_date()));
			}
			pstmt.setDouble(70, this.get(i).getAv_BOY());
			pstmt.setDouble(71, this.get(i).getAv_BOM());
			pstmt.setDouble(72, this.get(i).getTBMoney());
			if(this.get(i).getTBMDate() == null || this.get(i).getTBMDate().equals("null")) {
				pstmt.setDate(73,null);
			} else {
				pstmt.setDate(73, Date.valueOf(this.get(i).getTBMDate()));
			}
			if(this.get(i).getTransSaleChnl() == null || this.get(i).getTransSaleChnl().equals("null")) {
				pstmt.setString(74,null);
			} else {
				pstmt.setString(74, this.get(i).getTransSaleChnl());
			}
			pstmt.setDouble(75, this.get(i).getCuml_PartSurrender());
			pstmt.setDouble(76, this.get(i).getBonusAmnt());
			pstmt.setDouble(77, this.get(i).getOtherPrem());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOReserve");
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
			tError.moduleName = "LOReserveDBSet";
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
