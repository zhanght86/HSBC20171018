/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCAirPolSchema;
import com.sinosoft.lis.vschema.LCAirPolSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCAirPolDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 短期险平台接口表
 */
public class LCAirPolDB extends LCAirPolSchema
{
private static Logger logger = Logger.getLogger(LCAirPolDB.class);

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
	public LCAirPolDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCAirPol" );
		mflag = true;
	}

	public LCAirPolDB()
	{
		con = null;
		db = new DBOper( "LCAirPol" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCAirPolSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCAirPolSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCAirPol WHERE  PolNo = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCAirPol");
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
		SQLString sqlObj = new SQLString("LCAirPol");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCAirPol SET  PolNo = ? , CertifyCode = ? , ManageCom = ? , AgentCom = ? , Password = ? , InsuredName = ? , InsuredIDType = ? , InsuredIDNo = ? , RelationToInsured = ? , BnfAddress = ? , BnfName = ? , BnfZipCode = ? , BnfPhone = ? , Mult = ? , Prem = ? , Amnt = ? , PolState = ? , EmplaneDate = ? , FlightNo = ? , AgentCode = ? , AgentGroup = ? , InsuYearFlag = ? , InsuYear = ? , EndDate = ? , CValiDate = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , TSignDate = ? , RiskCode = ? , State = ? , RiskType = ? , Destination = ? , Reason = ? , InsuredSex = ? , InsuredBirthday = ? , OccupationType = ? , PrtNo = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? WHERE  PolNo = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			if(this.getCertifyCode() == null || this.getCertifyCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCertifyCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAgentCom());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPassword());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredName());
			}
			if(this.getInsuredIDType() == null || this.getInsuredIDType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getInsuredIDType());
			}
			if(this.getInsuredIDNo() == null || this.getInsuredIDNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getInsuredIDNo());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRelationToInsured());
			}
			if(this.getBnfAddress() == null || this.getBnfAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getBnfAddress());
			}
			if(this.getBnfName() == null || this.getBnfName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getBnfName());
			}
			if(this.getBnfZipCode() == null || this.getBnfZipCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getBnfZipCode());
			}
			if(this.getBnfPhone() == null || this.getBnfPhone().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getBnfPhone());
			}
			pstmt.setDouble(14, this.getMult());
			pstmt.setDouble(15, this.getPrem());
			pstmt.setDouble(16, this.getAmnt());
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getPolState());
			}
			if(this.getEmplaneDate() == null || this.getEmplaneDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getEmplaneDate()));
			}
			if(this.getFlightNo() == null || this.getFlightNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getFlightNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAgentGroup());
			}
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getInsuYearFlag());
			}
			pstmt.setInt(23, this.getInsuYear());
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getEndDate()));
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getCValiDate()));
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getModifyTime());
			}
			if(this.getTSignDate() == null || this.getTSignDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getTSignDate()));
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getRiskCode());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getState());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRiskType());
			}
			if(this.getDestination() == null || this.getDestination().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getDestination());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getReason());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getInsuredBirthday()));
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getOccupationType());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPrtNo());
			}
			if(this.getStandByFlag1() == null || this.getStandByFlag1().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getStandByFlag1());
			}
			if(this.getStandByFlag2() == null || this.getStandByFlag2().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getStandByFlag2());
			}
			if(this.getStandByFlag3() == null || this.getStandByFlag3().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getStandByFlag3());
			}
			// set where condition
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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
		SQLString sqlObj = new SQLString("LCAirPol");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCAirPol(PolNo ,CertifyCode ,ManageCom ,AgentCom ,Password ,InsuredName ,InsuredIDType ,InsuredIDNo ,RelationToInsured ,BnfAddress ,BnfName ,BnfZipCode ,BnfPhone ,Mult ,Prem ,Amnt ,PolState ,EmplaneDate ,FlightNo ,AgentCode ,AgentGroup ,InsuYearFlag ,InsuYear ,EndDate ,CValiDate ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,TSignDate ,RiskCode ,State ,RiskType ,Destination ,Reason ,InsuredSex ,InsuredBirthday ,OccupationType ,PrtNo ,StandByFlag1 ,StandByFlag2 ,StandByFlag3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			if(this.getCertifyCode() == null || this.getCertifyCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCertifyCode());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getAgentCom());
			}
			if(this.getPassword() == null || this.getPassword().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPassword());
			}
			if(this.getInsuredName() == null || this.getInsuredName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredName());
			}
			if(this.getInsuredIDType() == null || this.getInsuredIDType().equals("null")) {
				pstmt.setNull(7, 1);
			} else {
				pstmt.setString(7, this.getInsuredIDType());
			}
			if(this.getInsuredIDNo() == null || this.getInsuredIDNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getInsuredIDNo());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRelationToInsured());
			}
			if(this.getBnfAddress() == null || this.getBnfAddress().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getBnfAddress());
			}
			if(this.getBnfName() == null || this.getBnfName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getBnfName());
			}
			if(this.getBnfZipCode() == null || this.getBnfZipCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getBnfZipCode());
			}
			if(this.getBnfPhone() == null || this.getBnfPhone().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getBnfPhone());
			}
			pstmt.setDouble(14, this.getMult());
			pstmt.setDouble(15, this.getPrem());
			pstmt.setDouble(16, this.getAmnt());
			if(this.getPolState() == null || this.getPolState().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getPolState());
			}
			if(this.getEmplaneDate() == null || this.getEmplaneDate().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getEmplaneDate()));
			}
			if(this.getFlightNo() == null || this.getFlightNo().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getFlightNo());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAgentGroup());
			}
			if(this.getInsuYearFlag() == null || this.getInsuYearFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getInsuYearFlag());
			}
			pstmt.setInt(23, this.getInsuYear());
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getEndDate()));
			}
			if(this.getCValiDate() == null || this.getCValiDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getCValiDate()));
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getModifyTime());
			}
			if(this.getTSignDate() == null || this.getTSignDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getTSignDate()));
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getRiskCode());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getState());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRiskType());
			}
			if(this.getDestination() == null || this.getDestination().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getDestination());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getReason());
			}
			if(this.getInsuredSex() == null || this.getInsuredSex().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getInsuredSex());
			}
			if(this.getInsuredBirthday() == null || this.getInsuredBirthday().equals("null")) {
				pstmt.setNull(38, 91);
			} else {
				pstmt.setDate(38, Date.valueOf(this.getInsuredBirthday()));
			}
			if(this.getOccupationType() == null || this.getOccupationType().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getOccupationType());
			}
			if(this.getPrtNo() == null || this.getPrtNo().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPrtNo());
			}
			if(this.getStandByFlag1() == null || this.getStandByFlag1().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getStandByFlag1());
			}
			if(this.getStandByFlag2() == null || this.getStandByFlag2().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getStandByFlag2());
			}
			if(this.getStandByFlag3() == null || this.getStandByFlag3().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getStandByFlag3());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCAirPol WHERE  PolNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
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
					tError.moduleName = "LCAirPolDB";
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
			tError.moduleName = "LCAirPolDB";
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

	public LCAirPolSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCAirPolSet aLCAirPolSet = new LCAirPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCAirPol");
			LCAirPolSchema aSchema = this.getSchema();
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
				LCAirPolSchema s1 = new LCAirPolSchema();
				s1.setSchema(rs,i);
				aLCAirPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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

		return aLCAirPolSet;

	}

	public LCAirPolSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAirPolSet aLCAirPolSet = new LCAirPolSet();

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
				LCAirPolSchema s1 = new LCAirPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAirPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAirPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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

		return aLCAirPolSet;
	}

	public LCAirPolSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCAirPolSet aLCAirPolSet = new LCAirPolSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCAirPol");
			LCAirPolSchema aSchema = this.getSchema();
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

				LCAirPolSchema s1 = new LCAirPolSchema();
				s1.setSchema(rs,i);
				aLCAirPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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

		return aLCAirPolSet;

	}

	public LCAirPolSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAirPolSet aLCAirPolSet = new LCAirPolSet();

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

				LCAirPolSchema s1 = new LCAirPolSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAirPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAirPolSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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

		return aLCAirPolSet;
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
			SQLString sqlObj = new SQLString("LCAirPol");
			LCAirPolSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCAirPol " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAirPolDB";
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
			tError.moduleName = "LCAirPolDB";
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
        tError.moduleName = "LCAirPolDB";
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
        tError.moduleName = "LCAirPolDB";
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
        tError.moduleName = "LCAirPolDB";
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
        tError.moduleName = "LCAirPolDB";
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
 * @return LCAirPolSet
 */
public LCAirPolSet getData()
{
    int tCount = 0;
    LCAirPolSet tLCAirPolSet = new LCAirPolSet();
    LCAirPolSchema tLCAirPolSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCAirPolDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCAirPolSchema = new LCAirPolSchema();
        tLCAirPolSchema.setSchema(mResultSet, 1);
        tLCAirPolSet.add(tLCAirPolSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCAirPolSchema = new LCAirPolSchema();
                tLCAirPolSchema.setSchema(mResultSet, 1);
                tLCAirPolSet.add(tLCAirPolSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCAirPolDB";
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
    return tLCAirPolSet;
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
            tError.moduleName = "LCAirPolDB";
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
        tError.moduleName = "LCAirPolDB";
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
            tError.moduleName = "LCAirPolDB";
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
        tError.moduleName = "LCAirPolDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCAirPolSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCAirPolSet aLCAirPolSet = new LCAirPolSet();

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
				LCAirPolSchema s1 = new LCAirPolSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAirPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAirPolSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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

		return aLCAirPolSet;
	}

	public LCAirPolSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCAirPolSet aLCAirPolSet = new LCAirPolSet();

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

				LCAirPolSchema s1 = new LCAirPolSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCAirPolDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCAirPolSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolDB";
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

		return aLCAirPolSet; 
	}

}
