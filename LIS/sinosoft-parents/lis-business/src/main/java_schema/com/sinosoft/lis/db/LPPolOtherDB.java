/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LPPolOtherSchema;
import com.sinosoft.lis.vschema.LPPolOtherSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPPolOtherDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPPolOtherDB extends LPPolOtherSchema
{
private static Logger logger = Logger.getLogger(LPPolOtherDB.class);

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
	public LPPolOtherDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPPolOther" );
		mflag = true;
	}

	public LPPolOtherDB()
	{
		con = null;
		db = new DBOper( "LPPolOther" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPPolOtherSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPPolOtherSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPPolOther WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ? AND GrpPolNo = ? AND ContNo = ? AND PolNo = ? AND DutyCode = ?");
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
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPolNo());
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
			tError.moduleName = "LPPolOtherDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPPolOther");
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
		SQLString sqlObj = new SQLString("LPPolOther");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPPolOther SET  EdorNo = ? , EdorType = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , PolNo = ? , DutyCode = ? , P1 = ? , P2 = ? , P3 = ? , P4 = ? , P5 = ? , P6 = ? , P7 = ? , P8 = ? , P9 = ? , P10 = ? , P11 = ? , P12 = ? , P13 = ? , P14 = ? , P15 = ? , P16 = ? , P17 = ? , P18 = ? , P19 = ? , P20 = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ? AND GrpPolNo = ? AND ContNo = ? AND PolNo = ? AND DutyCode = ?");
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
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDutyCode());
			}
			if(this.getP1() == null || this.getP1().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getP1());
			}
			if(this.getP2() == null || this.getP2().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getP2());
			}
			if(this.getP3() == null || this.getP3().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getP3());
			}
			if(this.getP4() == null || this.getP4().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getP4());
			}
			if(this.getP5() == null || this.getP5().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getP5());
			}
			if(this.getP6() == null || this.getP6().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getP6());
			}
			if(this.getP7() == null || this.getP7().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getP7());
			}
			if(this.getP8() == null || this.getP8().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getP8());
			}
			if(this.getP9() == null || this.getP9().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getP9());
			}
			if(this.getP10() == null || this.getP10().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getP10());
			}
			if(this.getP11() == null || this.getP11().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getP11());
			}
			if(this.getP12() == null || this.getP12().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getP12());
			}
			if(this.getP13() == null || this.getP13().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getP13());
			}
			if(this.getP14() == null || this.getP14().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getP14());
			}
			if(this.getP15() == null || this.getP15().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getP15());
			}
			if(this.getP16() == null || this.getP16().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getP16());
			}
			if(this.getP17() == null || this.getP17().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getP17());
			}
			if(this.getP18() == null || this.getP18().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getP18());
			}
			if(this.getP19() == null || this.getP19().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getP19());
			}
			if(this.getP20() == null || this.getP20().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getP20());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getModifyTime());
			}
			// set where condition
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getEdorNo());
			}
			if(this.getEdorType() == null || this.getEdorType().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getEdorType());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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
		SQLString sqlObj = new SQLString("LPPolOther");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPPolOther(EdorNo ,EdorType ,GrpContNo ,GrpPolNo ,ContNo ,PolNo ,DutyCode ,P1 ,P2 ,P3 ,P4 ,P5 ,P6 ,P7 ,P8 ,P9 ,P10 ,P11 ,P12 ,P13 ,P14 ,P15 ,P16 ,P17 ,P18 ,P19 ,P20 ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getDutyCode());
			}
			if(this.getP1() == null || this.getP1().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getP1());
			}
			if(this.getP2() == null || this.getP2().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getP2());
			}
			if(this.getP3() == null || this.getP3().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getP3());
			}
			if(this.getP4() == null || this.getP4().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getP4());
			}
			if(this.getP5() == null || this.getP5().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getP5());
			}
			if(this.getP6() == null || this.getP6().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getP6());
			}
			if(this.getP7() == null || this.getP7().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getP7());
			}
			if(this.getP8() == null || this.getP8().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getP8());
			}
			if(this.getP9() == null || this.getP9().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getP9());
			}
			if(this.getP10() == null || this.getP10().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getP10());
			}
			if(this.getP11() == null || this.getP11().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getP11());
			}
			if(this.getP12() == null || this.getP12().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getP12());
			}
			if(this.getP13() == null || this.getP13().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getP13());
			}
			if(this.getP14() == null || this.getP14().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getP14());
			}
			if(this.getP15() == null || this.getP15().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getP15());
			}
			if(this.getP16() == null || this.getP16().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getP16());
			}
			if(this.getP17() == null || this.getP17().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getP17());
			}
			if(this.getP18() == null || this.getP18().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getP18());
			}
			if(this.getP19() == null || this.getP19().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getP19());
			}
			if(this.getP20() == null || this.getP20().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getP20());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(32, 1);
			} else {
				pstmt.setString(32, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPPolOther WHERE  EdorNo = ? AND EdorType = ? AND GrpContNo = ? AND GrpPolNo = ? AND ContNo = ? AND PolNo = ? AND DutyCode = ?", 
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
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPolNo());
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
					tError.moduleName = "LPPolOtherDB";
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
			tError.moduleName = "LPPolOtherDB";
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

	public LPPolOtherSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPPolOther");
			LPPolOtherSchema aSchema = this.getSchema();
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
				LPPolOtherSchema s1 = new LPPolOtherSchema();
				s1.setSchema(rs,i);
				aLPPolOtherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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

		return aLPPolOtherSet;

	}

	public LPPolOtherSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();

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
				LPPolOtherSchema s1 = new LPPolOtherSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPolOtherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPolOtherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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

		return aLPPolOtherSet;
	}

	public LPPolOtherSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPPolOther");
			LPPolOtherSchema aSchema = this.getSchema();
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

				LPPolOtherSchema s1 = new LPPolOtherSchema();
				s1.setSchema(rs,i);
				aLPPolOtherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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

		return aLPPolOtherSet;

	}

	public LPPolOtherSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();

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

				LPPolOtherSchema s1 = new LPPolOtherSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPolOtherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPolOtherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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

		return aLPPolOtherSet;
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
			SQLString sqlObj = new SQLString("LPPolOther");
			LPPolOtherSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPPolOther " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPPolOtherDB";
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
			tError.moduleName = "LPPolOtherDB";
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
        tError.moduleName = "LPPolOtherDB";
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
        tError.moduleName = "LPPolOtherDB";
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
        tError.moduleName = "LPPolOtherDB";
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
        tError.moduleName = "LPPolOtherDB";
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
 * @return LPPolOtherSet
 */
public LPPolOtherSet getData()
{
    int tCount = 0;
    LPPolOtherSet tLPPolOtherSet = new LPPolOtherSet();
    LPPolOtherSchema tLPPolOtherSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPPolOtherDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPPolOtherSchema = new LPPolOtherSchema();
        tLPPolOtherSchema.setSchema(mResultSet, 1);
        tLPPolOtherSet.add(tLPPolOtherSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPPolOtherSchema = new LPPolOtherSchema();
                tLPPolOtherSchema.setSchema(mResultSet, 1);
                tLPPolOtherSet.add(tLPPolOtherSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPPolOtherDB";
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
    return tLPPolOtherSet;
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
            tError.moduleName = "LPPolOtherDB";
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
        tError.moduleName = "LPPolOtherDB";
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
            tError.moduleName = "LPPolOtherDB";
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
        tError.moduleName = "LPPolOtherDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPPolOtherSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();

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
				LPPolOtherSchema s1 = new LPPolOtherSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPolOtherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPolOtherSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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

		return aLPPolOtherSet;
	}

	public LPPolOtherSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPPolOtherSet aLPPolOtherSet = new LPPolOtherSet();

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

				LPPolOtherSchema s1 = new LPPolOtherSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPPolOtherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPPolOtherSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPPolOtherDB";
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

		return aLPPolOtherSet; 
	}

}
