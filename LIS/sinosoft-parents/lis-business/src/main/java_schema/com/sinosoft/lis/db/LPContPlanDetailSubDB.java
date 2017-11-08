/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LPContPlanDetailSubSchema;
import com.sinosoft.lis.vschema.LPContPlanDetailSubSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPContPlanDetailSubDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPContPlanDetailSubDB extends LPContPlanDetailSubSchema
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
	public LPContPlanDetailSubDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPContPlanDetailSub" );
		mflag = true;
	}

	public LPContPlanDetailSubDB()
	{
		con = null;
		db = new DBOper( "LPContPlanDetailSub" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPContPlanDetailSubSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPContPlanDetailSubSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPContPlanDetailSub WHERE  EdorNo = ? AND EdorType = ? AND PolicyNo = ? AND SysPlanCode = ? AND PlanCode = ? AND RiskCode = ? AND DutyCode = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolicyNo());
			}
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPlanCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPContPlanDetailSub");
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
		SQLString sqlObj = new SQLString("LPContPlanDetailSub");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPContPlanDetailSub SET  EdorNo = ? , EdorType = ? , PolicyNo = ? , PropNo = ? , SysPlanCode = ? , PlanCode = ? , RiskCode = ? , DutyCode = ? , P1 = ? , P2 = ? , P3 = ? , P4 = ? , P5 = ? , P6 = ? , P7 = ? , P8 = ? , P9 = ? , P10 = ? , P11 = ? , P12 = ? , P13 = ? , P14 = ? , P15 = ? , P16 = ? , P17 = ? , P18 = ? , P19 = ? , P20 = ? , P21 = ? , P22 = ? , P23 = ? , P24 = ? , P25 = ? , P26 = ? , P27 = ? , P28 = ? , P29 = ? , P30 = ? WHERE  EdorNo = ? AND EdorType = ? AND PolicyNo = ? AND SysPlanCode = ? AND PlanCode = ? AND RiskCode = ? AND DutyCode = ?");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolicyNo());
			}
			if(this.getPropNo() == null || this.getPropNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPropNo());
			}
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPlanCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getDutyCode());
			}
			if(this.getP1() == null || this.getP1().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getP1());
			}
			if(this.getP2() == null || this.getP2().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getP2());
			}
			if(this.getP3() == null || this.getP3().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getP3());
			}
			if(this.getP4() == null || this.getP4().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getP4());
			}
			if(this.getP5() == null || this.getP5().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getP5());
			}
			if(this.getP6() == null || this.getP6().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getP6());
			}
			if(this.getP7() == null || this.getP7().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getP7());
			}
			if(this.getP8() == null || this.getP8().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getP8());
			}
			if(this.getP9() == null || this.getP9().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getP9());
			}
			if(this.getP10() == null || this.getP10().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getP10());
			}
			if(this.getP11() == null || this.getP11().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getP11());
			}
			if(this.getP12() == null || this.getP12().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getP12());
			}
			if(this.getP13() == null || this.getP13().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getP13());
			}
			if(this.getP14() == null || this.getP14().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getP14());
			}
			if(this.getP15() == null || this.getP15().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getP15());
			}
			if(this.getP16() == null || this.getP16().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getP16());
			}
			if(this.getP17() == null || this.getP17().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getP17());
			}
			if(this.getP18() == null || this.getP18().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getP18());
			}
			if(this.getP19() == null || this.getP19().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getP19());
			}
			if(this.getP20() == null || this.getP20().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getP20());
			}
			if(this.getP21() == null || this.getP21().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getP21());
			}
			if(this.getP22() == null || this.getP22().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getP22());
			}
			if(this.getP23() == null || this.getP23().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getP23());
			}
			if(this.getP24() == null || this.getP24().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getP24());
			}
			if(this.getP25() == null || this.getP25().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getP25());
			}
			if(this.getP26() == null || this.getP26().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getP26());
			}
			if(this.getP27() == null || this.getP27().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getP27());
			}
			if(this.getP28() == null || this.getP28().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getP28());
			}
			if(this.getP29() == null || this.getP29().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getP29());
			}
			if(this.getP30() == null || this.getP30().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getP30());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getEdorType());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getPolicyNo());
			}
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getPlanCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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
		SQLString sqlObj = new SQLString("LPContPlanDetailSub");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPContPlanDetailSub VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolicyNo());
			}
			if(this.getPropNo() == null || this.getPropNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPropNo());
			}
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPlanCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getDutyCode());
			}
			if(this.getP1() == null || this.getP1().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getP1());
			}
			if(this.getP2() == null || this.getP2().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getP2());
			}
			if(this.getP3() == null || this.getP3().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getP3());
			}
			if(this.getP4() == null || this.getP4().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getP4());
			}
			if(this.getP5() == null || this.getP5().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getP5());
			}
			if(this.getP6() == null || this.getP6().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getP6());
			}
			if(this.getP7() == null || this.getP7().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getP7());
			}
			if(this.getP8() == null || this.getP8().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getP8());
			}
			if(this.getP9() == null || this.getP9().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getP9());
			}
			if(this.getP10() == null || this.getP10().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getP10());
			}
			if(this.getP11() == null || this.getP11().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getP11());
			}
			if(this.getP12() == null || this.getP12().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getP12());
			}
			if(this.getP13() == null || this.getP13().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getP13());
			}
			if(this.getP14() == null || this.getP14().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getP14());
			}
			if(this.getP15() == null || this.getP15().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getP15());
			}
			if(this.getP16() == null || this.getP16().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getP16());
			}
			if(this.getP17() == null || this.getP17().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getP17());
			}
			if(this.getP18() == null || this.getP18().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getP18());
			}
			if(this.getP19() == null || this.getP19().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getP19());
			}
			if(this.getP20() == null || this.getP20().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getP20());
			}
			if(this.getP21() == null || this.getP21().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getP21());
			}
			if(this.getP22() == null || this.getP22().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getP22());
			}
			if(this.getP23() == null || this.getP23().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getP23());
			}
			if(this.getP24() == null || this.getP24().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getP24());
			}
			if(this.getP25() == null || this.getP25().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getP25());
			}
			if(this.getP26() == null || this.getP26().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getP26());
			}
			if(this.getP27() == null || this.getP27().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getP27());
			}
			if(this.getP28() == null || this.getP28().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getP28());
			}
			if(this.getP29() == null || this.getP29().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getP29());
			}
			if(this.getP30() == null || this.getP30().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getP30());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPContPlanDetailSub WHERE  EdorNo = ? AND EdorType = ? AND PolicyNo = ? AND SysPlanCode = ? AND PlanCode = ? AND RiskCode = ? AND DutyCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getEdorType());
			}
			if(this.getPolicyNo() == null || this.getPolicyNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolicyNo());
			}
			if(this.getSysPlanCode() == null || this.getSysPlanCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getSysPlanCode());
			}
			if(this.getPlanCode() == null || this.getPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPlanCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getRiskCode());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDutyCode());
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
					tError.moduleName = "LPContPlanDetailSubDB";
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
			tError.moduleName = "LPContPlanDetailSubDB";
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

	public LPContPlanDetailSubSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPContPlanDetailSubSet aLPContPlanDetailSubSet = new LPContPlanDetailSubSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPContPlanDetailSub");
			LPContPlanDetailSubSchema aSchema = this.getSchema();
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
				LPContPlanDetailSubSchema s1 = new LPContPlanDetailSubSchema();
				s1.setSchema(rs,i);
				aLPContPlanDetailSubSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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

		return aLPContPlanDetailSubSet;
	}

	public LPContPlanDetailSubSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPContPlanDetailSubSet aLPContPlanDetailSubSet = new LPContPlanDetailSubSet();

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
				LPContPlanDetailSubSchema s1 = new LPContPlanDetailSubSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPContPlanDetailSubDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPContPlanDetailSubSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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

		return aLPContPlanDetailSubSet;
	}

	public LPContPlanDetailSubSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPContPlanDetailSubSet aLPContPlanDetailSubSet = new LPContPlanDetailSubSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPContPlanDetailSub");
			LPContPlanDetailSubSchema aSchema = this.getSchema();
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

				LPContPlanDetailSubSchema s1 = new LPContPlanDetailSubSchema();
				s1.setSchema(rs,i);
				aLPContPlanDetailSubSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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

		return aLPContPlanDetailSubSet;
	}

	public LPContPlanDetailSubSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPContPlanDetailSubSet aLPContPlanDetailSubSet = new LPContPlanDetailSubSet();

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

				LPContPlanDetailSubSchema s1 = new LPContPlanDetailSubSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPContPlanDetailSubDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPContPlanDetailSubSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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

		return aLPContPlanDetailSubSet;
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
			SQLString sqlObj = new SQLString("LPContPlanDetailSub");
			LPContPlanDetailSubSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPContPlanDetailSub " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPContPlanDetailSubDB";
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
			tError.moduleName = "LPContPlanDetailSubDB";
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
        tError.moduleName = "LPContPlanDetailSubDB";
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
        tError.moduleName = "LPContPlanDetailSubDB";
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
        tError.moduleName = "LPContPlanDetailSubDB";
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
        tError.moduleName = "LPContPlanDetailSubDB";
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
 * @return LPContPlanDetailSubSet
 */
public LPContPlanDetailSubSet getData()
{
    int tCount = 0;
    LPContPlanDetailSubSet tLPContPlanDetailSubSet = new LPContPlanDetailSubSet();
    LPContPlanDetailSubSchema tLPContPlanDetailSubSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPContPlanDetailSubDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPContPlanDetailSubSchema = new LPContPlanDetailSubSchema();
        tLPContPlanDetailSubSchema.setSchema(mResultSet, 1);
        tLPContPlanDetailSubSet.add(tLPContPlanDetailSubSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPContPlanDetailSubSchema = new LPContPlanDetailSubSchema();
                tLPContPlanDetailSubSchema.setSchema(mResultSet, 1);
                tLPContPlanDetailSubSet.add(tLPContPlanDetailSubSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPContPlanDetailSubDB";
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
    return tLPContPlanDetailSubSet;
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
            tError.moduleName = "LPContPlanDetailSubDB";
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
        tError.moduleName = "LPContPlanDetailSubDB";
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
            tError.moduleName = "LPContPlanDetailSubDB";
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
        tError.moduleName = "LPContPlanDetailSubDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPContPlanDetailSubSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPContPlanDetailSubSet aLPContPlanDetailSubSet = new LPContPlanDetailSubSet();

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
				LPContPlanDetailSubSchema s1 = new LPContPlanDetailSubSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LPContPlanDetailSubDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLPContPlanDetailSubSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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

		return aLPContPlanDetailSubSet;
	}

	public LPContPlanDetailSubSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPContPlanDetailSubSet aLPContPlanDetailSubSet = new LPContPlanDetailSubSet();

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

				LPContPlanDetailSubSchema s1 = new LPContPlanDetailSubSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LPContPlanDetailSubDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLPContPlanDetailSubSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LPContPlanDetailSubDB";
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

		return aLPContPlanDetailSubSet; 
	}

}
