/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMDutyGet_LibSchema;
import com.sinosoft.lis.vschema.PD_LMDutyGet_LibSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyGet_LibDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyGet_LibDB extends PD_LMDutyGet_LibSchema
{
private static Logger logger = Logger.getLogger(PD_LMDutyGet_LibDB.class);

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
	public PD_LMDutyGet_LibDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "PD_LMDutyGet_Lib" );
		mflag = true;
	}

	public PD_LMDutyGet_LibDB()
	{
		con = null;
		db = new DBOper( "PD_LMDutyGet_Lib" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		PD_LMDutyGet_LibSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		PD_LMDutyGet_LibSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMDutyGet_Lib WHERE  GetDutyCode2 = ?");
			if(this.getGetDutyCode2() == null || this.getGetDutyCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode2());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
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
		SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE PD_LMDutyGet_Lib SET  GetDutyCode2 = ? , CalCode = ? , GetDutyName = ? , Type = ? , GetIntv = ? , DefaultVal = ? , CnterCalCode = ? , OthCalCode = ? , GetYear = ? , GetYearFlag = ? , StartDateCalRef = ? , StartDateCaLmode = ? , MinGetStartPeriod = ? , GetEndPeriod = ? , GetEndUnit = ? , EndDateCalRef = ? , EndDateCalMode = ? , MaxGetEndPeriod = ? , AddFlag = ? , SexRelaFlag = ? , UnitAppRelaFlag = ? , AddAmntFlag = ? , DiscntFlag = ? , InterestFlag = ? , ShareFlag = ? , InpFlag = ? , BnfFlag = ? , UrgeGetFlag = ? , DeadValiFlag = ? , GetInitFlag = ? , GetLimit = ? , MaxGet = ? , GetRate = ? , NeedAcc = ? , CanGet = ? , NeedCancelAcc = ? , GetType1 = ? , ZeroFlag = ? , GetType2 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  GetDutyCode2 = ?");
			if(this.getGetDutyCode2() == null || this.getGetDutyCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode2());
			}
			if(this.getCalCode() == null || this.getCalCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCalCode());
			}
			if(this.getGetDutyName() == null || this.getGetDutyName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGetDutyName());
			}
			if(this.getType() == null || this.getType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getType());
			}
			pstmt.setInt(5, this.getGetIntv());
			pstmt.setDouble(6, this.getDefaultVal());
			if(this.getCnterCalCode() == null || this.getCnterCalCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getCnterCalCode());
			}
			if(this.getOthCalCode() == null || this.getOthCalCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getOthCalCode());
			}
			pstmt.setInt(9, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getGetYearFlag());
			}
			if(this.getStartDateCalRef() == null || this.getStartDateCalRef().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getStartDateCalRef());
			}
			if(this.getStartDateCaLmode() == null || this.getStartDateCaLmode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getStartDateCaLmode());
			}
			pstmt.setInt(13, this.getMinGetStartPeriod());
			pstmt.setInt(14, this.getGetEndPeriod());
			if(this.getGetEndUnit() == null || this.getGetEndUnit().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGetEndUnit());
			}
			if(this.getEndDateCalRef() == null || this.getEndDateCalRef().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getEndDateCalRef());
			}
			if(this.getEndDateCalMode() == null || this.getEndDateCalMode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEndDateCalMode());
			}
			pstmt.setInt(18, this.getMaxGetEndPeriod());
			if(this.getAddFlag() == null || this.getAddFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAddFlag());
			}
			if(this.getSexRelaFlag() == null || this.getSexRelaFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSexRelaFlag());
			}
			if(this.getUnitAppRelaFlag() == null || this.getUnitAppRelaFlag().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getUnitAppRelaFlag());
			}
			if(this.getAddAmntFlag() == null || this.getAddAmntFlag().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAddAmntFlag());
			}
			if(this.getDiscntFlag() == null || this.getDiscntFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getDiscntFlag());
			}
			if(this.getInterestFlag() == null || this.getInterestFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getInterestFlag());
			}
			if(this.getShareFlag() == null || this.getShareFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getShareFlag());
			}
			if(this.getInpFlag() == null || this.getInpFlag().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getInpFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getBnfFlag());
			}
			if(this.getUrgeGetFlag() == null || this.getUrgeGetFlag().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getUrgeGetFlag());
			}
			if(this.getDeadValiFlag() == null || this.getDeadValiFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getDeadValiFlag());
			}
			if(this.getGetInitFlag() == null || this.getGetInitFlag().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getGetInitFlag());
			}
			pstmt.setDouble(31, this.getGetLimit());
			pstmt.setDouble(32, this.getMaxGet());
			pstmt.setDouble(33, this.getGetRate());
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getNeedAcc());
			}
			if(this.getCanGet() == null || this.getCanGet().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getCanGet());
			}
			if(this.getNeedCancelAcc() == null || this.getNeedCancelAcc().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getNeedCancelAcc());
			}
			if(this.getGetType1() == null || this.getGetType1().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGetType1());
			}
			if(this.getZeroFlag() == null || this.getZeroFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getZeroFlag());
			}
			if(this.getGetType2() == null || this.getGetType2().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getGetType2());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getStandbyflag2());
			}
			pstmt.setInt(47, this.getStandbyflag3());
			pstmt.setInt(48, this.getStandbyflag4());
			pstmt.setDouble(49, this.getStandbyflag5());
			pstmt.setDouble(50, this.getStandbyflag6());
			// set where condition
			if(this.getGetDutyCode2() == null || this.getGetDutyCode2().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getGetDutyCode2());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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
		SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO PD_LMDutyGet_Lib(GetDutyCode2 ,CalCode ,GetDutyName ,Type ,GetIntv ,DefaultVal ,CnterCalCode ,OthCalCode ,GetYear ,GetYearFlag ,StartDateCalRef ,StartDateCaLmode ,MinGetStartPeriod ,GetEndPeriod ,GetEndUnit ,EndDateCalRef ,EndDateCalMode ,MaxGetEndPeriod ,AddFlag ,SexRelaFlag ,UnitAppRelaFlag ,AddAmntFlag ,DiscntFlag ,InterestFlag ,ShareFlag ,InpFlag ,BnfFlag ,UrgeGetFlag ,DeadValiFlag ,GetInitFlag ,GetLimit ,MaxGet ,GetRate ,NeedAcc ,CanGet ,NeedCancelAcc ,GetType1 ,ZeroFlag ,GetType2 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getGetDutyCode2() == null || this.getGetDutyCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode2());
			}
			if(this.getCalCode() == null || this.getCalCode().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getCalCode());
			}
			if(this.getGetDutyName() == null || this.getGetDutyName().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGetDutyName());
			}
			if(this.getType() == null || this.getType().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getType());
			}
			pstmt.setInt(5, this.getGetIntv());
			pstmt.setDouble(6, this.getDefaultVal());
			if(this.getCnterCalCode() == null || this.getCnterCalCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getCnterCalCode());
			}
			if(this.getOthCalCode() == null || this.getOthCalCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getOthCalCode());
			}
			pstmt.setInt(9, this.getGetYear());
			if(this.getGetYearFlag() == null || this.getGetYearFlag().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getGetYearFlag());
			}
			if(this.getStartDateCalRef() == null || this.getStartDateCalRef().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getStartDateCalRef());
			}
			if(this.getStartDateCaLmode() == null || this.getStartDateCaLmode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getStartDateCaLmode());
			}
			pstmt.setInt(13, this.getMinGetStartPeriod());
			pstmt.setInt(14, this.getGetEndPeriod());
			if(this.getGetEndUnit() == null || this.getGetEndUnit().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGetEndUnit());
			}
			if(this.getEndDateCalRef() == null || this.getEndDateCalRef().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getEndDateCalRef());
			}
			if(this.getEndDateCalMode() == null || this.getEndDateCalMode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEndDateCalMode());
			}
			pstmt.setInt(18, this.getMaxGetEndPeriod());
			if(this.getAddFlag() == null || this.getAddFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAddFlag());
			}
			if(this.getSexRelaFlag() == null || this.getSexRelaFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSexRelaFlag());
			}
			if(this.getUnitAppRelaFlag() == null || this.getUnitAppRelaFlag().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getUnitAppRelaFlag());
			}
			if(this.getAddAmntFlag() == null || this.getAddAmntFlag().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAddAmntFlag());
			}
			if(this.getDiscntFlag() == null || this.getDiscntFlag().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getDiscntFlag());
			}
			if(this.getInterestFlag() == null || this.getInterestFlag().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getInterestFlag());
			}
			if(this.getShareFlag() == null || this.getShareFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getShareFlag());
			}
			if(this.getInpFlag() == null || this.getInpFlag().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getInpFlag());
			}
			if(this.getBnfFlag() == null || this.getBnfFlag().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getBnfFlag());
			}
			if(this.getUrgeGetFlag() == null || this.getUrgeGetFlag().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getUrgeGetFlag());
			}
			if(this.getDeadValiFlag() == null || this.getDeadValiFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getDeadValiFlag());
			}
			if(this.getGetInitFlag() == null || this.getGetInitFlag().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getGetInitFlag());
			}
			pstmt.setDouble(31, this.getGetLimit());
			pstmt.setDouble(32, this.getMaxGet());
			pstmt.setDouble(33, this.getGetRate());
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getNeedAcc());
			}
			if(this.getCanGet() == null || this.getCanGet().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getCanGet());
			}
			if(this.getNeedCancelAcc() == null || this.getNeedCancelAcc().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getNeedCancelAcc());
			}
			if(this.getGetType1() == null || this.getGetType1().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGetType1());
			}
			if(this.getZeroFlag() == null || this.getZeroFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getZeroFlag());
			}
			if(this.getGetType2() == null || this.getGetType2().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getGetType2());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(43, 91);
			} else {
				pstmt.setDate(43, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getModifyTime());
			}
			if(this.getStandbyflag1() == null || this.getStandbyflag1().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getStandbyflag1());
			}
			if(this.getStandbyflag2() == null || this.getStandbyflag2().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getStandbyflag2());
			}
			pstmt.setInt(47, this.getStandbyflag3());
			pstmt.setInt(48, this.getStandbyflag4());
			pstmt.setDouble(49, this.getStandbyflag5());
			pstmt.setDouble(50, this.getStandbyflag6());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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
			pstmt = con.prepareStatement("SELECT * FROM PD_LMDutyGet_Lib WHERE  GetDutyCode2 = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getGetDutyCode2() == null || this.getGetDutyCode2().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getGetDutyCode2());
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
					tError.moduleName = "PD_LMDutyGet_LibDB";
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
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

	public PD_LMDutyGet_LibSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LMDutyGet_LibSet aPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
			PD_LMDutyGet_LibSchema aSchema = this.getSchema();
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
				PD_LMDutyGet_LibSchema s1 = new PD_LMDutyGet_LibSchema();
				s1.setSchema(rs,i);
				aPD_LMDutyGet_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

		return aPD_LMDutyGet_LibSet;

	}

	public PD_LMDutyGet_LibSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyGet_LibSet aPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();

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
				PD_LMDutyGet_LibSchema s1 = new PD_LMDutyGet_LibSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyGet_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyGet_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

		return aPD_LMDutyGet_LibSet;
	}

	public PD_LMDutyGet_LibSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		PD_LMDutyGet_LibSet aPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
			PD_LMDutyGet_LibSchema aSchema = this.getSchema();
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

				PD_LMDutyGet_LibSchema s1 = new PD_LMDutyGet_LibSchema();
				s1.setSchema(rs,i);
				aPD_LMDutyGet_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

		return aPD_LMDutyGet_LibSet;

	}

	public PD_LMDutyGet_LibSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		PD_LMDutyGet_LibSet aPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();

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

				PD_LMDutyGet_LibSchema s1 = new PD_LMDutyGet_LibSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyGet_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyGet_LibSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

		return aPD_LMDutyGet_LibSet;
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
			SQLString sqlObj = new SQLString("PD_LMDutyGet_Lib");
			PD_LMDutyGet_LibSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update PD_LMDutyGet_Lib " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PD_LMDutyGet_LibDB";
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
			tError.moduleName = "PD_LMDutyGet_LibDB";
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
        tError.moduleName = "PD_LMDutyGet_LibDB";
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
        tError.moduleName = "PD_LMDutyGet_LibDB";
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
        tError.moduleName = "PD_LMDutyGet_LibDB";
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
        tError.moduleName = "PD_LMDutyGet_LibDB";
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
 * @return PD_LMDutyGet_LibSet
 */
public PD_LMDutyGet_LibSet getData()
{
    int tCount = 0;
    PD_LMDutyGet_LibSet tPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();
    PD_LMDutyGet_LibSchema tPD_LMDutyGet_LibSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMDutyGet_LibDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tPD_LMDutyGet_LibSchema = new PD_LMDutyGet_LibSchema();
        tPD_LMDutyGet_LibSchema.setSchema(mResultSet, 1);
        tPD_LMDutyGet_LibSet.add(tPD_LMDutyGet_LibSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tPD_LMDutyGet_LibSchema = new PD_LMDutyGet_LibSchema();
                tPD_LMDutyGet_LibSchema.setSchema(mResultSet, 1);
                tPD_LMDutyGet_LibSet.add(tPD_LMDutyGet_LibSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "PD_LMDutyGet_LibDB";
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
    return tPD_LMDutyGet_LibSet;
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
            tError.moduleName = "PD_LMDutyGet_LibDB";
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
        tError.moduleName = "PD_LMDutyGet_LibDB";
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
            tError.moduleName = "PD_LMDutyGet_LibDB";
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
        tError.moduleName = "PD_LMDutyGet_LibDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public PD_LMDutyGet_LibSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LMDutyGet_LibSet aPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();

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
				PD_LMDutyGet_LibSchema s1 = new PD_LMDutyGet_LibSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyGet_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyGet_LibSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

		return aPD_LMDutyGet_LibSet;
	}

	public PD_LMDutyGet_LibSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PD_LMDutyGet_LibSet aPD_LMDutyGet_LibSet = new PD_LMDutyGet_LibSet();

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

				PD_LMDutyGet_LibSchema s1 = new PD_LMDutyGet_LibSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PD_LMDutyGet_LibDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aPD_LMDutyGet_LibSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGet_LibDB";
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

		return aPD_LMDutyGet_LibSet; 
	}

}
