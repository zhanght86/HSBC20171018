/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LPDutySubSchema;
import com.sinosoft.lis.vschema.LPDutySubSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPDutySubDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LPDutySubDBSet extends LPDutySubSet
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
	public LPDutySubDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LPDutySub");
		mflag = true;
	}

	public LPDutySubDBSet()
	{
		db = new DBOper( "LPDutySub" );
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
			tError.moduleName = "LPDutySubDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LPDutySub WHERE  EdorNo = ? AND EdorType = ? AND BussType = ? AND BussNo = ? AND PolNo = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBussNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPDutySub");
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
			tError.moduleName = "LPDutySubDBSet";
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
			pstmt = con.prepareStatement("UPDATE LPDutySub SET  EdorNo = ? , EdorType = ? , BussType = ? , BussNo = ? , ContNo = ? , PolNo = ? , OtherNo = ? , SysPlanCode = ? , PlanCode = ? , RiskCode = ? , DutyCode = ? , P1 = ? , P2 = ? , P3 = ? , P4 = ? , P5 = ? , P6 = ? , P7 = ? , P8 = ? , P9 = ? , P10 = ? , P11 = ? , P12 = ? , P13 = ? , P14 = ? , P15 = ? , P16 = ? , P17 = ? , P18 = ? , P19 = ? , P20 = ? , P21 = ? , P22 = ? , P23 = ? , P24 = ? , P25 = ? , P26 = ? , P27 = ? , P28 = ? , P29 = ? , P30 = ? WHERE  EdorNo = ? AND EdorType = ? AND BussType = ? AND BussNo = ? AND PolNo = ? AND DutyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBussNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPolNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOtherNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPlanCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDutyCode());
			}
			if(this.get(i).getP1() == null || this.get(i).getP1().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getP1());
			}
			if(this.get(i).getP2() == null || this.get(i).getP2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getP2());
			}
			if(this.get(i).getP3() == null || this.get(i).getP3().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getP3());
			}
			if(this.get(i).getP4() == null || this.get(i).getP4().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getP4());
			}
			if(this.get(i).getP5() == null || this.get(i).getP5().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getP5());
			}
			if(this.get(i).getP6() == null || this.get(i).getP6().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getP6());
			}
			if(this.get(i).getP7() == null || this.get(i).getP7().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getP7());
			}
			if(this.get(i).getP8() == null || this.get(i).getP8().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getP8());
			}
			if(this.get(i).getP9() == null || this.get(i).getP9().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getP9());
			}
			if(this.get(i).getP10() == null || this.get(i).getP10().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getP10());
			}
			if(this.get(i).getP11() == null || this.get(i).getP11().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getP11());
			}
			if(this.get(i).getP12() == null || this.get(i).getP12().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getP12());
			}
			if(this.get(i).getP13() == null || this.get(i).getP13().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getP13());
			}
			if(this.get(i).getP14() == null || this.get(i).getP14().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getP14());
			}
			if(this.get(i).getP15() == null || this.get(i).getP15().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getP15());
			}
			if(this.get(i).getP16() == null || this.get(i).getP16().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getP16());
			}
			if(this.get(i).getP17() == null || this.get(i).getP17().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getP17());
			}
			if(this.get(i).getP18() == null || this.get(i).getP18().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getP18());
			}
			if(this.get(i).getP19() == null || this.get(i).getP19().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getP19());
			}
			if(this.get(i).getP20() == null || this.get(i).getP20().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getP20());
			}
			if(this.get(i).getP21() == null || this.get(i).getP21().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getP21());
			}
			if(this.get(i).getP22() == null || this.get(i).getP22().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getP22());
			}
			if(this.get(i).getP23() == null || this.get(i).getP23().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getP23());
			}
			if(this.get(i).getP24() == null || this.get(i).getP24().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getP24());
			}
			if(this.get(i).getP25() == null || this.get(i).getP25().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getP25());
			}
			if(this.get(i).getP26() == null || this.get(i).getP26().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getP26());
			}
			if(this.get(i).getP27() == null || this.get(i).getP27().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getP27());
			}
			if(this.get(i).getP28() == null || this.get(i).getP28().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getP28());
			}
			if(this.get(i).getP29() == null || this.get(i).getP29().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getP29());
			}
			if(this.get(i).getP30() == null || this.get(i).getP30().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getP30());
			}
			// set where condition
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getEdorType());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getBussNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getDutyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPDutySub");
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
			tError.moduleName = "LPDutySubDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LPDutySub VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getEdorType());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBussNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPolNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOtherNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getPlanCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getDutyCode());
			}
			if(this.get(i).getP1() == null || this.get(i).getP1().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getP1());
			}
			if(this.get(i).getP2() == null || this.get(i).getP2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getP2());
			}
			if(this.get(i).getP3() == null || this.get(i).getP3().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getP3());
			}
			if(this.get(i).getP4() == null || this.get(i).getP4().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getP4());
			}
			if(this.get(i).getP5() == null || this.get(i).getP5().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getP5());
			}
			if(this.get(i).getP6() == null || this.get(i).getP6().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getP6());
			}
			if(this.get(i).getP7() == null || this.get(i).getP7().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getP7());
			}
			if(this.get(i).getP8() == null || this.get(i).getP8().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getP8());
			}
			if(this.get(i).getP9() == null || this.get(i).getP9().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getP9());
			}
			if(this.get(i).getP10() == null || this.get(i).getP10().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getP10());
			}
			if(this.get(i).getP11() == null || this.get(i).getP11().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getP11());
			}
			if(this.get(i).getP12() == null || this.get(i).getP12().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getP12());
			}
			if(this.get(i).getP13() == null || this.get(i).getP13().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getP13());
			}
			if(this.get(i).getP14() == null || this.get(i).getP14().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getP14());
			}
			if(this.get(i).getP15() == null || this.get(i).getP15().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getP15());
			}
			if(this.get(i).getP16() == null || this.get(i).getP16().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getP16());
			}
			if(this.get(i).getP17() == null || this.get(i).getP17().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getP17());
			}
			if(this.get(i).getP18() == null || this.get(i).getP18().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getP18());
			}
			if(this.get(i).getP19() == null || this.get(i).getP19().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getP19());
			}
			if(this.get(i).getP20() == null || this.get(i).getP20().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getP20());
			}
			if(this.get(i).getP21() == null || this.get(i).getP21().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getP21());
			}
			if(this.get(i).getP22() == null || this.get(i).getP22().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getP22());
			}
			if(this.get(i).getP23() == null || this.get(i).getP23().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getP23());
			}
			if(this.get(i).getP24() == null || this.get(i).getP24().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getP24());
			}
			if(this.get(i).getP25() == null || this.get(i).getP25().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getP25());
			}
			if(this.get(i).getP26() == null || this.get(i).getP26().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getP26());
			}
			if(this.get(i).getP27() == null || this.get(i).getP27().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getP27());
			}
			if(this.get(i).getP28() == null || this.get(i).getP28().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getP28());
			}
			if(this.get(i).getP29() == null || this.get(i).getP29().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getP29());
			}
			if(this.get(i).getP30() == null || this.get(i).getP30().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getP30());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPDutySub");
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
			tError.moduleName = "LPDutySubDBSet";
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
