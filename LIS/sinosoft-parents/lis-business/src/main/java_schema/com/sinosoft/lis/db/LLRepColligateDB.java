/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLRepColligateSchema;
import com.sinosoft.lis.vschema.LLRepColligateSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLRepColligateDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLRepColligateDB extends LLRepColligateSchema
{
private static Logger logger = Logger.getLogger(LLRepColligateDB.class);

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
	public LLRepColligateDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLRepColligate" );
		mflag = true;
	}

	public LLRepColligateDB()
	{
		con = null;
		db = new DBOper( "LLRepColligate" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLRepColligateSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLRepColligateSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLRepColligate WHERE  RepID = ? AND MngCom = ? AND StartDate = ? AND EndDate = ? AND ColID = ?");
			if(this.getRepID() == null || this.getRepID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRepID());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getMngCom());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEndDate());
			}
			if(this.getColID() == null || this.getColID().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getColID());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLRepColligate");
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
		SQLString sqlObj = new SQLString("LLRepColligate");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLRepColligate SET  RepID = ? , RepName = ? , MngCom = ? , MngComName = ? , Year = ? , HalfYear = ? , Quarter = ? , StartDate = ? , EndDate = ? , ColID = ? , ColName = ? , C1 = ? , C2 = ? , C3 = ? , C4 = ? , C5 = ? , C6 = ? , C7 = ? , C8 = ? , C9 = ? , C10 = ? , C11 = ? , C12 = ? , C13 = ? , C14 = ? , C15 = ? , C16 = ? , C17 = ? , C18 = ? , C19 = ? , C20 = ? , C21 = ? , C22 = ? , C23 = ? , C24 = ? , C25 = ? , C26 = ? , C27 = ? , C28 = ? , C29 = ? , C30 = ? , C31 = ? , C32 = ? , C33 = ? , C34 = ? , C35 = ? , C36 = ? , C37 = ? , C38 = ? , C39 = ? , C40 = ? , C41 = ? , C42 = ? , C43 = ? , C44 = ? , C45 = ? , C46 = ? , C47 = ? , C48 = ? , C49 = ? , C50 = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Operator = ? , Remark = ? WHERE  RepID = ? AND MngCom = ? AND StartDate = ? AND EndDate = ? AND ColID = ?");
			if(this.getRepID() == null || this.getRepID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRepID());
			}
			if(this.getRepName() == null || this.getRepName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRepName());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getMngCom());
			}
			if(this.getMngComName() == null || this.getMngComName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getMngComName());
			}
			if(this.getYear() == null || this.getYear().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getYear());
			}
			if(this.getHalfYear() == null || this.getHalfYear().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getHalfYear());
			}
			if(this.getQuarter() == null || this.getQuarter().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getQuarter());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getEndDate());
			}
			if(this.getColID() == null || this.getColID().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getColID());
			}
			if(this.getColName() == null || this.getColName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getColName());
			}
			if(this.getC1() == null || this.getC1().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getC1());
			}
			if(this.getC2() == null || this.getC2().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getC2());
			}
			if(this.getC3() == null || this.getC3().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getC3());
			}
			if(this.getC4() == null || this.getC4().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getC4());
			}
			if(this.getC5() == null || this.getC5().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getC5());
			}
			if(this.getC6() == null || this.getC6().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getC6());
			}
			if(this.getC7() == null || this.getC7().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getC7());
			}
			if(this.getC8() == null || this.getC8().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getC8());
			}
			if(this.getC9() == null || this.getC9().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getC9());
			}
			if(this.getC10() == null || this.getC10().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getC10());
			}
			if(this.getC11() == null || this.getC11().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getC11());
			}
			if(this.getC12() == null || this.getC12().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getC12());
			}
			if(this.getC13() == null || this.getC13().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getC13());
			}
			if(this.getC14() == null || this.getC14().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getC14());
			}
			if(this.getC15() == null || this.getC15().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getC15());
			}
			if(this.getC16() == null || this.getC16().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getC16());
			}
			if(this.getC17() == null || this.getC17().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getC17());
			}
			if(this.getC18() == null || this.getC18().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getC18());
			}
			if(this.getC19() == null || this.getC19().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getC19());
			}
			if(this.getC20() == null || this.getC20().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getC20());
			}
			if(this.getC21() == null || this.getC21().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getC21());
			}
			if(this.getC22() == null || this.getC22().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getC22());
			}
			if(this.getC23() == null || this.getC23().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getC23());
			}
			if(this.getC24() == null || this.getC24().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getC24());
			}
			if(this.getC25() == null || this.getC25().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getC25());
			}
			if(this.getC26() == null || this.getC26().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getC26());
			}
			if(this.getC27() == null || this.getC27().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getC27());
			}
			if(this.getC28() == null || this.getC28().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getC28());
			}
			if(this.getC29() == null || this.getC29().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getC29());
			}
			if(this.getC30() == null || this.getC30().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getC30());
			}
			if(this.getC31() == null || this.getC31().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getC31());
			}
			if(this.getC32() == null || this.getC32().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getC32());
			}
			if(this.getC33() == null || this.getC33().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getC33());
			}
			if(this.getC34() == null || this.getC34().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getC34());
			}
			if(this.getC35() == null || this.getC35().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getC35());
			}
			if(this.getC36() == null || this.getC36().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getC36());
			}
			if(this.getC37() == null || this.getC37().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getC37());
			}
			if(this.getC38() == null || this.getC38().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getC38());
			}
			if(this.getC39() == null || this.getC39().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getC39());
			}
			if(this.getC40() == null || this.getC40().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getC40());
			}
			if(this.getC41() == null || this.getC41().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getC41());
			}
			if(this.getC42() == null || this.getC42().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getC42());
			}
			if(this.getC43() == null || this.getC43().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getC43());
			}
			if(this.getC44() == null || this.getC44().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getC44());
			}
			if(this.getC45() == null || this.getC45().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getC45());
			}
			if(this.getC46() == null || this.getC46().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getC46());
			}
			if(this.getC47() == null || this.getC47().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getC47());
			}
			if(this.getC48() == null || this.getC48().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getC48());
			}
			if(this.getC49() == null || this.getC49().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getC49());
			}
			if(this.getC50() == null || this.getC50().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getC50());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(62, 91);
			} else {
				pstmt.setDate(62, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(65, 1);
			} else {
				pstmt.setString(65, this.getModifyTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getOperator());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRemark());
			}
			// set where condition
			if(this.getRepID() == null || this.getRepID().equals("null")) {
				pstmt.setNull(68, 12);
			} else {
				pstmt.setString(68, this.getRepID());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getMngCom());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(70, 12);
			} else {
				pstmt.setString(70, this.getStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(71, 12);
			} else {
				pstmt.setString(71, this.getEndDate());
			}
			if(this.getColID() == null || this.getColID().equals("null")) {
				pstmt.setNull(72, 12);
			} else {
				pstmt.setString(72, this.getColID());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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
		SQLString sqlObj = new SQLString("LLRepColligate");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLRepColligate(RepID ,RepName ,MngCom ,MngComName ,Year ,HalfYear ,Quarter ,StartDate ,EndDate ,ColID ,ColName ,C1 ,C2 ,C3 ,C4 ,C5 ,C6 ,C7 ,C8 ,C9 ,C10 ,C11 ,C12 ,C13 ,C14 ,C15 ,C16 ,C17 ,C18 ,C19 ,C20 ,C21 ,C22 ,C23 ,C24 ,C25 ,C26 ,C27 ,C28 ,C29 ,C30 ,C31 ,C32 ,C33 ,C34 ,C35 ,C36 ,C37 ,C38 ,C39 ,C40 ,C41 ,C42 ,C43 ,C44 ,C45 ,C46 ,C47 ,C48 ,C49 ,C50 ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Operator ,Remark) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getRepID() == null || this.getRepID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRepID());
			}
			if(this.getRepName() == null || this.getRepName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getRepName());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getMngCom());
			}
			if(this.getMngComName() == null || this.getMngComName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getMngComName());
			}
			if(this.getYear() == null || this.getYear().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getYear());
			}
			if(this.getHalfYear() == null || this.getHalfYear().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getHalfYear());
			}
			if(this.getQuarter() == null || this.getQuarter().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getQuarter());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getEndDate());
			}
			if(this.getColID() == null || this.getColID().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getColID());
			}
			if(this.getColName() == null || this.getColName().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getColName());
			}
			if(this.getC1() == null || this.getC1().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getC1());
			}
			if(this.getC2() == null || this.getC2().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getC2());
			}
			if(this.getC3() == null || this.getC3().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getC3());
			}
			if(this.getC4() == null || this.getC4().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getC4());
			}
			if(this.getC5() == null || this.getC5().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getC5());
			}
			if(this.getC6() == null || this.getC6().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getC6());
			}
			if(this.getC7() == null || this.getC7().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getC7());
			}
			if(this.getC8() == null || this.getC8().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getC8());
			}
			if(this.getC9() == null || this.getC9().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getC9());
			}
			if(this.getC10() == null || this.getC10().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getC10());
			}
			if(this.getC11() == null || this.getC11().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getC11());
			}
			if(this.getC12() == null || this.getC12().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getC12());
			}
			if(this.getC13() == null || this.getC13().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getC13());
			}
			if(this.getC14() == null || this.getC14().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getC14());
			}
			if(this.getC15() == null || this.getC15().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getC15());
			}
			if(this.getC16() == null || this.getC16().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getC16());
			}
			if(this.getC17() == null || this.getC17().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getC17());
			}
			if(this.getC18() == null || this.getC18().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getC18());
			}
			if(this.getC19() == null || this.getC19().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getC19());
			}
			if(this.getC20() == null || this.getC20().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getC20());
			}
			if(this.getC21() == null || this.getC21().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getC21());
			}
			if(this.getC22() == null || this.getC22().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getC22());
			}
			if(this.getC23() == null || this.getC23().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getC23());
			}
			if(this.getC24() == null || this.getC24().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getC24());
			}
			if(this.getC25() == null || this.getC25().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getC25());
			}
			if(this.getC26() == null || this.getC26().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getC26());
			}
			if(this.getC27() == null || this.getC27().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getC27());
			}
			if(this.getC28() == null || this.getC28().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getC28());
			}
			if(this.getC29() == null || this.getC29().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getC29());
			}
			if(this.getC30() == null || this.getC30().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getC30());
			}
			if(this.getC31() == null || this.getC31().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getC31());
			}
			if(this.getC32() == null || this.getC32().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getC32());
			}
			if(this.getC33() == null || this.getC33().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getC33());
			}
			if(this.getC34() == null || this.getC34().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getC34());
			}
			if(this.getC35() == null || this.getC35().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getC35());
			}
			if(this.getC36() == null || this.getC36().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getC36());
			}
			if(this.getC37() == null || this.getC37().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getC37());
			}
			if(this.getC38() == null || this.getC38().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getC38());
			}
			if(this.getC39() == null || this.getC39().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getC39());
			}
			if(this.getC40() == null || this.getC40().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getC40());
			}
			if(this.getC41() == null || this.getC41().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getC41());
			}
			if(this.getC42() == null || this.getC42().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getC42());
			}
			if(this.getC43() == null || this.getC43().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getC43());
			}
			if(this.getC44() == null || this.getC44().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getC44());
			}
			if(this.getC45() == null || this.getC45().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getC45());
			}
			if(this.getC46() == null || this.getC46().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getC46());
			}
			if(this.getC47() == null || this.getC47().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getC47());
			}
			if(this.getC48() == null || this.getC48().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getC48());
			}
			if(this.getC49() == null || this.getC49().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getC49());
			}
			if(this.getC50() == null || this.getC50().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getC50());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(62, 91);
			} else {
				pstmt.setDate(62, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(63, 1);
			} else {
				pstmt.setString(63, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(65, 1);
			} else {
				pstmt.setString(65, this.getModifyTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getOperator());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getRemark());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLRepColligate WHERE  RepID = ? AND MngCom = ? AND StartDate = ? AND EndDate = ? AND ColID = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getRepID() == null || this.getRepID().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getRepID());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getMngCom());
			}
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getStartDate());
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEndDate());
			}
			if(this.getColID() == null || this.getColID().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getColID());
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
					tError.moduleName = "LLRepColligateDB";
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
			tError.moduleName = "LLRepColligateDB";
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

	public LLRepColligateSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLRepColligateSet aLLRepColligateSet = new LLRepColligateSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLRepColligate");
			LLRepColligateSchema aSchema = this.getSchema();
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
				LLRepColligateSchema s1 = new LLRepColligateSchema();
				s1.setSchema(rs,i);
				aLLRepColligateSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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

		return aLLRepColligateSet;

	}

	public LLRepColligateSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLRepColligateSet aLLRepColligateSet = new LLRepColligateSet();

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
				LLRepColligateSchema s1 = new LLRepColligateSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLRepColligateDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLRepColligateSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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

		return aLLRepColligateSet;
	}

	public LLRepColligateSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLRepColligateSet aLLRepColligateSet = new LLRepColligateSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLRepColligate");
			LLRepColligateSchema aSchema = this.getSchema();
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

				LLRepColligateSchema s1 = new LLRepColligateSchema();
				s1.setSchema(rs,i);
				aLLRepColligateSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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

		return aLLRepColligateSet;

	}

	public LLRepColligateSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLRepColligateSet aLLRepColligateSet = new LLRepColligateSet();

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

				LLRepColligateSchema s1 = new LLRepColligateSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLRepColligateDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLRepColligateSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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

		return aLLRepColligateSet;
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
			SQLString sqlObj = new SQLString("LLRepColligate");
			LLRepColligateSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLRepColligate " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLRepColligateDB";
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
			tError.moduleName = "LLRepColligateDB";
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
        tError.moduleName = "LLRepColligateDB";
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
        tError.moduleName = "LLRepColligateDB";
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
        tError.moduleName = "LLRepColligateDB";
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
        tError.moduleName = "LLRepColligateDB";
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
 * @return LLRepColligateSet
 */
public LLRepColligateSet getData()
{
    int tCount = 0;
    LLRepColligateSet tLLRepColligateSet = new LLRepColligateSet();
    LLRepColligateSchema tLLRepColligateSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLRepColligateDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLRepColligateSchema = new LLRepColligateSchema();
        tLLRepColligateSchema.setSchema(mResultSet, 1);
        tLLRepColligateSet.add(tLLRepColligateSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLRepColligateSchema = new LLRepColligateSchema();
                tLLRepColligateSchema.setSchema(mResultSet, 1);
                tLLRepColligateSet.add(tLLRepColligateSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLRepColligateDB";
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
    return tLLRepColligateSet;
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
            tError.moduleName = "LLRepColligateDB";
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
        tError.moduleName = "LLRepColligateDB";
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
            tError.moduleName = "LLRepColligateDB";
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
        tError.moduleName = "LLRepColligateDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLRepColligateSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLRepColligateSet aLLRepColligateSet = new LLRepColligateSet();

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
				LLRepColligateSchema s1 = new LLRepColligateSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLRepColligateDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLRepColligateSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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

		return aLLRepColligateSet;
	}

	public LLRepColligateSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLRepColligateSet aLLRepColligateSet = new LLRepColligateSet();

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

				LLRepColligateSchema s1 = new LLRepColligateSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLRepColligateDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLRepColligateSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLRepColligateDB";
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

		return aLLRepColligateSet; 
	}

}
