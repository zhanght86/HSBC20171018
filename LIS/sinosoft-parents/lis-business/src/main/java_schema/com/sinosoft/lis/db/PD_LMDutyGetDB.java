

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMDutyGetSchema;
import com.sinosoft.lis.vschema.PD_LMDutyGetSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyGetDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_3
 */
public class PD_LMDutyGetDB extends PD_LMDutyGetSchema
{
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
	public PD_LMDutyGetDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMDutyGet" );
		mflag = true;
	}

	public PD_LMDutyGetDB()
	{
		con = null;
		db = new DBOper( "PD_LMDutyGet" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMDutyGetSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMDutyGetSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMDutyGet WHERE  GetDutyCode = ?");
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGet");
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
		SQLString sqlObj = new SQLString("PD_LMDutyGet");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMDutyGet SET  GetDutyCode = ? , CalCode = ? , DutyCode = ? , GetDutyName = ? , GetDutyKind = ? , Type = ? , GetIntv = ? , DefaultVal = ? , CnterCalCode = ? , OthCalCode = ? , GetYear = ? , GetYearFlag = ? , StartDateCalRef = ? , StartDateCalMode = ? , MinGetStartPeriod = ? , GetEndPeriod = ? , GetEndUnit = ? , EndDateCalRef = ? , EndDateCalMode = ? , MaxGetEndPeriod = ? , AddFlag = ? , SexRelaFlag = ? , UnitAppRelaFlag = ? , AddAmntFlag = ? , DiscntFlag = ? , InterestFlag = ? , ShareFlag = ? , InpFlag = ? , BnfFlag = ? , UrgeGetFlag = ? , DeadValiFlag = ? , GetInitFlag = ? , GetLimit = ? , MaxGet = ? , GetRate = ? , NeedAcc = ? , CanGet = ? , NeedCancelAcc = ? , GetType1 = ? , ZeroFlag = ? , GetType2 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , GetType3 = ? , PCalCode = ? , RCalAmntFlag = ? , RCalAmntCode = ? WHERE  GetDutyCode = ?");
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode());
			}
			if(this.getCalCode() == null || this.getCalCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCalCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDutyCode());
			}
			if(this.getGetDutyName() == null || this.getGetDutyName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGetDutyName());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyKind());
			}
			if(this.getType() == null || this.getType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getType());
			}
			pstmt.setInt(7, this.getGetIntv());
			pstmt.setDouble(8, this.getDefaultVal());
			if(this.getCnterCalCode() == null || this.getCnterCalCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCnterCalCode());
			}
			if(this.getOthCalCode() == null || this.getOthCalCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getOthCalCode());
			}
			pstmt.setInt(11, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getGetYearFlag());
			}
			if(this.getStartDateCalRef() == null || this.getStartDateCalRef().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getStartDateCalRef());
			}
			if(this.getStartDateCalMode() == null || this.getStartDateCalMode().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getStartDateCalMode());
			}
			pstmt.setInt(15, this.getMinGetStartPeriod());
			pstmt.setInt(16, this.getGetEndPeriod());
			if(this.getGetEndUnit() == null || this.getGetEndUnit().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getGetEndUnit());
			}
			if(this.getEndDateCalRef() == null || this.getEndDateCalRef().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getEndDateCalRef());
			}
			if(this.getEndDateCalMode() == null || this.getEndDateCalMode().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getEndDateCalMode());
			}
			pstmt.setInt(20, this.getMaxGetEndPeriod());
			if(this.getAddFlag() == null || this.getAddFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getAddFlag());
			}
			if(this.getSexRelaFlag() == null || this.getSexRelaFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getSexRelaFlag());
			}
			if(this.getUnitAppRelaFlag() == null || this.getUnitAppRelaFlag().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getUnitAppRelaFlag());
			}
			if(this.getAddAmntFlag() == null || this.getAddAmntFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getAddAmntFlag());
			}
			if(this.getDiscntFlag() == null || this.getDiscntFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getDiscntFlag());
			}
			if(this.getInterestFlag() == null || this.getInterestFlag().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getInterestFlag());
			}
			if(this.getShareFlag() == null || this.getShareFlag().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getShareFlag());
			}
			if(this.getInpFlag() == null || this.getInpFlag().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getInpFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getBnfFlag());
			}
			if(this.getUrgeGetFlag() == null || this.getUrgeGetFlag().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getUrgeGetFlag());
			}
			if(this.getDeadValiFlag() == null || this.getDeadValiFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getDeadValiFlag());
			}
			if(this.getGetInitFlag() == null || this.getGetInitFlag().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getGetInitFlag());
			}
			pstmt.setDouble(33, this.getGetLimit());
			pstmt.setDouble(34, this.getMaxGet());
			pstmt.setDouble(35, this.getGetRate());
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getNeedAcc());
			}
			if(this.getCanGet() == null || this.getCanGet().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getCanGet());
			}
			if(this.getNeedCancelAcc() == null || this.getNeedCancelAcc().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getNeedCancelAcc());
			}
			if(this.getGetType1() == null || this.getGetType1().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getGetType1());
			}
			if(this.getZeroFlag() == null || this.getZeroFlag().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getZeroFlag());
			}
			if(this.getGetType2() == null || this.getGetType2().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getGetType2());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getStandbyflag2());
			}
			pstmt.setInt(49, this.getStandbyflag3());
			pstmt.setInt(50, this.getStandbyflag4());
			pstmt.setDouble(51, this.getStandbyflag5());
			pstmt.setDouble(52, this.getStandbyflag6());
			if(this.getGetType3() == null || this.getGetType3().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getGetType3());
			}
			if(this.getPCalCode() == null || this.getPCalCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getPCalCode());
			}
			if(this.getRCalAmntFlag() == null || this.getRCalAmntFlag().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getRCalAmntFlag());
			}
			if(this.getRCalAmntCode() == null || this.getRCalAmntCode().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getRCalAmntCode());
			}
			// set where condition
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getGetDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
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
		SQLString sqlObj = new SQLString("PD_LMDutyGet");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMDutyGet(GetDutyCode ,CalCode ,DutyCode ,GetDutyName ,GetDutyKind ,Type ,GetIntv ,DefaultVal ,CnterCalCode ,OthCalCode ,GetYear ,GetYearFlag ,StartDateCalRef ,StartDateCalMode ,MinGetStartPeriod ,GetEndPeriod ,GetEndUnit ,EndDateCalRef ,EndDateCalMode ,MaxGetEndPeriod ,AddFlag ,SexRelaFlag ,UnitAppRelaFlag ,AddAmntFlag ,DiscntFlag ,InterestFlag ,ShareFlag ,InpFlag ,BnfFlag ,UrgeGetFlag ,DeadValiFlag ,GetInitFlag ,GetLimit ,MaxGet ,GetRate ,NeedAcc ,CanGet ,NeedCancelAcc ,GetType1 ,ZeroFlag ,GetType2 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6 ,GetType3 ,PCalCode ,RCalAmntFlag ,RCalAmntCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode());
			}
			if(this.getCalCode() == null || this.getCalCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCalCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDutyCode());
			}
			if(this.getGetDutyName() == null || this.getGetDutyName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGetDutyName());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyKind());
			}
			if(this.getType() == null || this.getType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getType());
			}
			pstmt.setInt(7, this.getGetIntv());
			pstmt.setDouble(8, this.getDefaultVal());
			if(this.getCnterCalCode() == null || this.getCnterCalCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCnterCalCode());
			}
			if(this.getOthCalCode() == null || this.getOthCalCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getOthCalCode());
			}
			pstmt.setInt(11, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getGetYearFlag());
			}
			if(this.getStartDateCalRef() == null || this.getStartDateCalRef().equals("null")) {
				pstmt.setNull(13, 1);
			} else {
				pstmt.setString(13, this.getStartDateCalRef());
			}
			if(this.getStartDateCalMode() == null || this.getStartDateCalMode().equals("null")) {
				pstmt.setNull(14, 1);
			} else {
				pstmt.setString(14, this.getStartDateCalMode());
			}
			pstmt.setInt(15, this.getMinGetStartPeriod());
			pstmt.setInt(16, this.getGetEndPeriod());
			if(this.getGetEndUnit() == null || this.getGetEndUnit().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getGetEndUnit());
			}
			if(this.getEndDateCalRef() == null || this.getEndDateCalRef().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getEndDateCalRef());
			}
			if(this.getEndDateCalMode() == null || this.getEndDateCalMode().equals("null")) {
				pstmt.setNull(19, 1);
			} else {
				pstmt.setString(19, this.getEndDateCalMode());
			}
			pstmt.setInt(20, this.getMaxGetEndPeriod());
			if(this.getAddFlag() == null || this.getAddFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getAddFlag());
			}
			if(this.getSexRelaFlag() == null || this.getSexRelaFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getSexRelaFlag());
			}
			if(this.getUnitAppRelaFlag() == null || this.getUnitAppRelaFlag().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getUnitAppRelaFlag());
			}
			if(this.getAddAmntFlag() == null || this.getAddAmntFlag().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getAddAmntFlag());
			}
			if(this.getDiscntFlag() == null || this.getDiscntFlag().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getDiscntFlag());
			}
			if(this.getInterestFlag() == null || this.getInterestFlag().equals("null")) {
				pstmt.setNull(26, 1);
			} else {
				pstmt.setString(26, this.getInterestFlag());
			}
			if(this.getShareFlag() == null || this.getShareFlag().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getShareFlag());
			}
			if(this.getInpFlag() == null || this.getInpFlag().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getInpFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getBnfFlag());
			}
			if(this.getUrgeGetFlag() == null || this.getUrgeGetFlag().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getUrgeGetFlag());
			}
			if(this.getDeadValiFlag() == null || this.getDeadValiFlag().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getDeadValiFlag());
			}
			if(this.getGetInitFlag() == null || this.getGetInitFlag().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getGetInitFlag());
			}
			pstmt.setDouble(33, this.getGetLimit());
			pstmt.setDouble(34, this.getMaxGet());
			pstmt.setDouble(35, this.getGetRate());
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(36, 1);
			} else {
				pstmt.setString(36, this.getNeedAcc());
			}
			if(this.getCanGet() == null || this.getCanGet().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getCanGet());
			}
			if(this.getNeedCancelAcc() == null || this.getNeedCancelAcc().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getNeedCancelAcc());
			}
			if(this.getGetType1() == null || this.getGetType1().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getGetType1());
			}
			if(this.getZeroFlag() == null || this.getZeroFlag().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getZeroFlag());
			}
			if(this.getGetType2() == null || this.getGetType2().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getGetType2());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getStandbyflag2());
			}
			pstmt.setInt(49, this.getStandbyflag3());
			pstmt.setInt(50, this.getStandbyflag4());
			pstmt.setDouble(51, this.getStandbyflag5());
			pstmt.setDouble(52, this.getStandbyflag6());
			if(this.getGetType3() == null || this.getGetType3().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getGetType3());
			}
			if(this.getPCalCode() == null || this.getPCalCode().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getPCalCode());
			}
			if(this.getRCalAmntFlag() == null || this.getRCalAmntFlag().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getRCalAmntFlag());
			}
			if(this.getRCalAmntCode() == null || this.getRCalAmntCode().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getRCalAmntCode());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMDutyGet WHERE  GetDutyCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode());
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
					tError.moduleName = "PD_LMDutyGetDB";
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
			tError.moduleName = "PD_LMDutyGetDB";
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

	public PD_LMDutyGetSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyGetSet aPD_LMDutyGetSet = new PD_LMDutyGetSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMDutyGet");
			PD_LMDutyGetSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				PD_LMDutyGetSchema s1 = new PD_LMDutyGetSchema();
				s1.setSchema(rs,i);
				aPD_LMDutyGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
			tError.functionName = "query";
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

		return aPD_LMDutyGetSet;
	}

	public PD_LMDutyGetSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyGetSet aPD_LMDutyGetSet = new PD_LMDutyGetSet();

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
				PD_LMDutyGetSchema s1 = new PD_LMDutyGetSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyGetDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
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

		return aPD_LMDutyGetSet;
	}

	public PD_LMDutyGetSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyGetSet aPD_LMDutyGetSet = new PD_LMDutyGetSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("PD_LMDutyGet");
			PD_LMDutyGetSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
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

				PD_LMDutyGetSchema s1 = new PD_LMDutyGetSchema();
				s1.setSchema(rs,i);
				aPD_LMDutyGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
			tError.functionName = "query";
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

		return aPD_LMDutyGetSet;
	}

	public PD_LMDutyGetSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyGetSet aPD_LMDutyGetSet = new PD_LMDutyGetSet();

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

				PD_LMDutyGetSchema s1 = new PD_LMDutyGetSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyGetDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetDB";
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

		return aPD_LMDutyGetSet;
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
			SQLString sqlObj = new SQLString("PD_LMDutyGet");
			PD_LMDutyGetSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMDutyGet " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMDutyGetDB";
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
			tError.moduleName = "PD_LMDutyGetDB";
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
        tError.moduleName = "PD_LMDutyGetDB";
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
        tError.moduleName = "PD_LMDutyGetDB";
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
        tError.moduleName = "PD_LMDutyGetDB";
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
        tError.moduleName = "PD_LMDutyGetDB";
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
 * @return PD_LMDutyGetSet
 */
public PD_LMDutyGetSet getData()
{
    int tCount = 0;
    PD_LMDutyGetSet tPD_LMDutyGetSet = new PD_LMDutyGetSet();
    PD_LMDutyGetSchema tPD_LMDutyGetSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMDutyGetDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMDutyGetSchema = new PD_LMDutyGetSchema();
        tPD_LMDutyGetSchema.setSchema(mResultSet, 1);
        tPD_LMDutyGetSet.add(tPD_LMDutyGetSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMDutyGetSchema = new PD_LMDutyGetSchema();
                tPD_LMDutyGetSchema.setSchema(mResultSet, 1);
                tPD_LMDutyGetSet.add(tPD_LMDutyGetSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMDutyGetDB";
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
    return tPD_LMDutyGetSet;
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
            tError.moduleName = "PD_LMDutyGetDB";
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
        tError.moduleName = "PD_LMDutyGetDB";
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
            tError.moduleName = "PD_LMDutyGetDB";
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
        tError.moduleName = "PD_LMDutyGetDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}
}
