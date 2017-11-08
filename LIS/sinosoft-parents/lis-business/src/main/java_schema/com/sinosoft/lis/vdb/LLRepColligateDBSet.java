/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLRepColligateSchema;
import com.sinosoft.lis.vschema.LLRepColligateSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLRepColligateDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLRepColligateDBSet extends LLRepColligateSet
{
private static Logger logger = Logger.getLogger(LLRepColligateDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLRepColligateDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLRepColligate");
		mflag = true;
	}

	public LLRepColligateDBSet()
	{
		db = new DBOper( "LLRepColligate" );
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
			tError.moduleName = "LLRepColligateDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLRepColligate WHERE  RepID = ? AND MngCom = ? AND StartDate = ? AND EndDate = ? AND ColID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRepID() == null || this.get(i).getRepID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRepID());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMngCom());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getStartDate());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getEndDate());
			}
			if(this.get(i).getColID() == null || this.get(i).getColID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getColID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLRepColligate");
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
			tError.moduleName = "LLRepColligateDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLRepColligate SET  RepID = ? , RepName = ? , MngCom = ? , MngComName = ? , Year = ? , HalfYear = ? , Quarter = ? , StartDate = ? , EndDate = ? , ColID = ? , ColName = ? , C1 = ? , C2 = ? , C3 = ? , C4 = ? , C5 = ? , C6 = ? , C7 = ? , C8 = ? , C9 = ? , C10 = ? , C11 = ? , C12 = ? , C13 = ? , C14 = ? , C15 = ? , C16 = ? , C17 = ? , C18 = ? , C19 = ? , C20 = ? , C21 = ? , C22 = ? , C23 = ? , C24 = ? , C25 = ? , C26 = ? , C27 = ? , C28 = ? , C29 = ? , C30 = ? , C31 = ? , C32 = ? , C33 = ? , C34 = ? , C35 = ? , C36 = ? , C37 = ? , C38 = ? , C39 = ? , C40 = ? , C41 = ? , C42 = ? , C43 = ? , C44 = ? , C45 = ? , C46 = ? , C47 = ? , C48 = ? , C49 = ? , C50 = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Operator = ? , Remark = ? WHERE  RepID = ? AND MngCom = ? AND StartDate = ? AND EndDate = ? AND ColID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRepID() == null || this.get(i).getRepID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRepID());
			}
			if(this.get(i).getRepName() == null || this.get(i).getRepName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRepName());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMngCom());
			}
			if(this.get(i).getMngComName() == null || this.get(i).getMngComName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMngComName());
			}
			if(this.get(i).getYear() == null || this.get(i).getYear().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getYear());
			}
			if(this.get(i).getHalfYear() == null || this.get(i).getHalfYear().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getHalfYear());
			}
			if(this.get(i).getQuarter() == null || this.get(i).getQuarter().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getQuarter());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getStartDate());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEndDate());
			}
			if(this.get(i).getColID() == null || this.get(i).getColID().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getColID());
			}
			if(this.get(i).getColName() == null || this.get(i).getColName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getColName());
			}
			if(this.get(i).getC1() == null || this.get(i).getC1().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getC1());
			}
			if(this.get(i).getC2() == null || this.get(i).getC2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getC2());
			}
			if(this.get(i).getC3() == null || this.get(i).getC3().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getC3());
			}
			if(this.get(i).getC4() == null || this.get(i).getC4().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getC4());
			}
			if(this.get(i).getC5() == null || this.get(i).getC5().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getC5());
			}
			if(this.get(i).getC6() == null || this.get(i).getC6().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getC6());
			}
			if(this.get(i).getC7() == null || this.get(i).getC7().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getC7());
			}
			if(this.get(i).getC8() == null || this.get(i).getC8().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getC8());
			}
			if(this.get(i).getC9() == null || this.get(i).getC9().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getC9());
			}
			if(this.get(i).getC10() == null || this.get(i).getC10().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getC10());
			}
			if(this.get(i).getC11() == null || this.get(i).getC11().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getC11());
			}
			if(this.get(i).getC12() == null || this.get(i).getC12().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getC12());
			}
			if(this.get(i).getC13() == null || this.get(i).getC13().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getC13());
			}
			if(this.get(i).getC14() == null || this.get(i).getC14().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getC14());
			}
			if(this.get(i).getC15() == null || this.get(i).getC15().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getC15());
			}
			if(this.get(i).getC16() == null || this.get(i).getC16().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getC16());
			}
			if(this.get(i).getC17() == null || this.get(i).getC17().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getC17());
			}
			if(this.get(i).getC18() == null || this.get(i).getC18().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getC18());
			}
			if(this.get(i).getC19() == null || this.get(i).getC19().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getC19());
			}
			if(this.get(i).getC20() == null || this.get(i).getC20().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getC20());
			}
			if(this.get(i).getC21() == null || this.get(i).getC21().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getC21());
			}
			if(this.get(i).getC22() == null || this.get(i).getC22().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getC22());
			}
			if(this.get(i).getC23() == null || this.get(i).getC23().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getC23());
			}
			if(this.get(i).getC24() == null || this.get(i).getC24().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getC24());
			}
			if(this.get(i).getC25() == null || this.get(i).getC25().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getC25());
			}
			if(this.get(i).getC26() == null || this.get(i).getC26().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getC26());
			}
			if(this.get(i).getC27() == null || this.get(i).getC27().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getC27());
			}
			if(this.get(i).getC28() == null || this.get(i).getC28().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getC28());
			}
			if(this.get(i).getC29() == null || this.get(i).getC29().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getC29());
			}
			if(this.get(i).getC30() == null || this.get(i).getC30().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getC30());
			}
			if(this.get(i).getC31() == null || this.get(i).getC31().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getC31());
			}
			if(this.get(i).getC32() == null || this.get(i).getC32().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getC32());
			}
			if(this.get(i).getC33() == null || this.get(i).getC33().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getC33());
			}
			if(this.get(i).getC34() == null || this.get(i).getC34().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getC34());
			}
			if(this.get(i).getC35() == null || this.get(i).getC35().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getC35());
			}
			if(this.get(i).getC36() == null || this.get(i).getC36().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getC36());
			}
			if(this.get(i).getC37() == null || this.get(i).getC37().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getC37());
			}
			if(this.get(i).getC38() == null || this.get(i).getC38().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getC38());
			}
			if(this.get(i).getC39() == null || this.get(i).getC39().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getC39());
			}
			if(this.get(i).getC40() == null || this.get(i).getC40().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getC40());
			}
			if(this.get(i).getC41() == null || this.get(i).getC41().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getC41());
			}
			if(this.get(i).getC42() == null || this.get(i).getC42().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getC42());
			}
			if(this.get(i).getC43() == null || this.get(i).getC43().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getC43());
			}
			if(this.get(i).getC44() == null || this.get(i).getC44().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getC44());
			}
			if(this.get(i).getC45() == null || this.get(i).getC45().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getC45());
			}
			if(this.get(i).getC46() == null || this.get(i).getC46().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getC46());
			}
			if(this.get(i).getC47() == null || this.get(i).getC47().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getC47());
			}
			if(this.get(i).getC48() == null || this.get(i).getC48().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getC48());
			}
			if(this.get(i).getC49() == null || this.get(i).getC49().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getC49());
			}
			if(this.get(i).getC50() == null || this.get(i).getC50().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getC50());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getModifyTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getOperator());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRemark());
			}
			// set where condition
			if(this.get(i).getRepID() == null || this.get(i).getRepID().equals("null")) {
				pstmt.setString(68,null);
			} else {
				pstmt.setString(68, this.get(i).getRepID());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(69,null);
			} else {
				pstmt.setString(69, this.get(i).getMngCom());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setString(70,null);
			} else {
				pstmt.setString(70, this.get(i).getStartDate());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setString(71,null);
			} else {
				pstmt.setString(71, this.get(i).getEndDate());
			}
			if(this.get(i).getColID() == null || this.get(i).getColID().equals("null")) {
				pstmt.setString(72,null);
			} else {
				pstmt.setString(72, this.get(i).getColID());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLRepColligate");
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
			tError.moduleName = "LLRepColligateDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLRepColligate(RepID ,RepName ,MngCom ,MngComName ,Year ,HalfYear ,Quarter ,StartDate ,EndDate ,ColID ,ColName ,C1 ,C2 ,C3 ,C4 ,C5 ,C6 ,C7 ,C8 ,C9 ,C10 ,C11 ,C12 ,C13 ,C14 ,C15 ,C16 ,C17 ,C18 ,C19 ,C20 ,C21 ,C22 ,C23 ,C24 ,C25 ,C26 ,C27 ,C28 ,C29 ,C30 ,C31 ,C32 ,C33 ,C34 ,C35 ,C36 ,C37 ,C38 ,C39 ,C40 ,C41 ,C42 ,C43 ,C44 ,C45 ,C46 ,C47 ,C48 ,C49 ,C50 ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Operator ,Remark) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRepID() == null || this.get(i).getRepID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRepID());
			}
			if(this.get(i).getRepName() == null || this.get(i).getRepName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRepName());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMngCom());
			}
			if(this.get(i).getMngComName() == null || this.get(i).getMngComName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMngComName());
			}
			if(this.get(i).getYear() == null || this.get(i).getYear().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getYear());
			}
			if(this.get(i).getHalfYear() == null || this.get(i).getHalfYear().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getHalfYear());
			}
			if(this.get(i).getQuarter() == null || this.get(i).getQuarter().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getQuarter());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getStartDate());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEndDate());
			}
			if(this.get(i).getColID() == null || this.get(i).getColID().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getColID());
			}
			if(this.get(i).getColName() == null || this.get(i).getColName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getColName());
			}
			if(this.get(i).getC1() == null || this.get(i).getC1().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getC1());
			}
			if(this.get(i).getC2() == null || this.get(i).getC2().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getC2());
			}
			if(this.get(i).getC3() == null || this.get(i).getC3().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getC3());
			}
			if(this.get(i).getC4() == null || this.get(i).getC4().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getC4());
			}
			if(this.get(i).getC5() == null || this.get(i).getC5().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getC5());
			}
			if(this.get(i).getC6() == null || this.get(i).getC6().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getC6());
			}
			if(this.get(i).getC7() == null || this.get(i).getC7().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getC7());
			}
			if(this.get(i).getC8() == null || this.get(i).getC8().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getC8());
			}
			if(this.get(i).getC9() == null || this.get(i).getC9().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getC9());
			}
			if(this.get(i).getC10() == null || this.get(i).getC10().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getC10());
			}
			if(this.get(i).getC11() == null || this.get(i).getC11().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getC11());
			}
			if(this.get(i).getC12() == null || this.get(i).getC12().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getC12());
			}
			if(this.get(i).getC13() == null || this.get(i).getC13().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getC13());
			}
			if(this.get(i).getC14() == null || this.get(i).getC14().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getC14());
			}
			if(this.get(i).getC15() == null || this.get(i).getC15().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getC15());
			}
			if(this.get(i).getC16() == null || this.get(i).getC16().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getC16());
			}
			if(this.get(i).getC17() == null || this.get(i).getC17().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getC17());
			}
			if(this.get(i).getC18() == null || this.get(i).getC18().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getC18());
			}
			if(this.get(i).getC19() == null || this.get(i).getC19().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getC19());
			}
			if(this.get(i).getC20() == null || this.get(i).getC20().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getC20());
			}
			if(this.get(i).getC21() == null || this.get(i).getC21().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getC21());
			}
			if(this.get(i).getC22() == null || this.get(i).getC22().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getC22());
			}
			if(this.get(i).getC23() == null || this.get(i).getC23().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getC23());
			}
			if(this.get(i).getC24() == null || this.get(i).getC24().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getC24());
			}
			if(this.get(i).getC25() == null || this.get(i).getC25().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getC25());
			}
			if(this.get(i).getC26() == null || this.get(i).getC26().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getC26());
			}
			if(this.get(i).getC27() == null || this.get(i).getC27().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getC27());
			}
			if(this.get(i).getC28() == null || this.get(i).getC28().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getC28());
			}
			if(this.get(i).getC29() == null || this.get(i).getC29().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getC29());
			}
			if(this.get(i).getC30() == null || this.get(i).getC30().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getC30());
			}
			if(this.get(i).getC31() == null || this.get(i).getC31().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getC31());
			}
			if(this.get(i).getC32() == null || this.get(i).getC32().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getC32());
			}
			if(this.get(i).getC33() == null || this.get(i).getC33().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getC33());
			}
			if(this.get(i).getC34() == null || this.get(i).getC34().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getC34());
			}
			if(this.get(i).getC35() == null || this.get(i).getC35().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getC35());
			}
			if(this.get(i).getC36() == null || this.get(i).getC36().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getC36());
			}
			if(this.get(i).getC37() == null || this.get(i).getC37().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getC37());
			}
			if(this.get(i).getC38() == null || this.get(i).getC38().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getC38());
			}
			if(this.get(i).getC39() == null || this.get(i).getC39().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getC39());
			}
			if(this.get(i).getC40() == null || this.get(i).getC40().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getC40());
			}
			if(this.get(i).getC41() == null || this.get(i).getC41().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getC41());
			}
			if(this.get(i).getC42() == null || this.get(i).getC42().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getC42());
			}
			if(this.get(i).getC43() == null || this.get(i).getC43().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getC43());
			}
			if(this.get(i).getC44() == null || this.get(i).getC44().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getC44());
			}
			if(this.get(i).getC45() == null || this.get(i).getC45().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getC45());
			}
			if(this.get(i).getC46() == null || this.get(i).getC46().equals("null")) {
				pstmt.setString(57,null);
			} else {
				pstmt.setString(57, this.get(i).getC46());
			}
			if(this.get(i).getC47() == null || this.get(i).getC47().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getC47());
			}
			if(this.get(i).getC48() == null || this.get(i).getC48().equals("null")) {
				pstmt.setString(59,null);
			} else {
				pstmt.setString(59, this.get(i).getC48());
			}
			if(this.get(i).getC49() == null || this.get(i).getC49().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getC49());
			}
			if(this.get(i).getC50() == null || this.get(i).getC50().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getC50());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(64,null);
			} else {
				pstmt.setDate(64, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(65,null);
			} else {
				pstmt.setString(65, this.get(i).getModifyTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getOperator());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getRemark());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLRepColligate");
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
			tError.moduleName = "LLRepColligateDBSet";
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
