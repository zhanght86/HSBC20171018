/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCGrpGenaltuaitionSchema;
import com.sinosoft.lis.vschema.LCGrpGenaltuaitionSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpGenaltuaitionDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 核保测算
 */
public class LCGrpGenaltuaitionDB extends LCGrpGenaltuaitionSchema
{
private static Logger logger = Logger.getLogger(LCGrpGenaltuaitionDB.class);

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
	public LCGrpGenaltuaitionDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCGrpGenaltuaition" );
		mflag = true;
	}

	public LCGrpGenaltuaitionDB()
	{
		con = null;
		db = new DBOper( "LCGrpGenaltuaition" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LCGrpGenaltuaitionSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LCGrpGenaltuaitionSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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
			pstmt = con.prepareStatement("DELETE FROM LCGrpGenaltuaition WHERE  BudgetNo = ? AND OtherNo = ? AND OtherNoType = ?");
			if(this.getBudgetNo() == null || this.getBudgetNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBudgetNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOtherNoType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
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
		SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LCGrpGenaltuaition SET  BudgetNo = ? , OtherNo = ? , OtherNoType = ? , ExpPeoples = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , StandbyFlag4 = ? , StandbyFlag5 = ? , StandbyFlag6 = ? , StandbyFlag7 = ? , StandbyFlag8 = ? , StandbyFlag9 = ? , StandbyFlag10 = ? , StandbyFlag11 = ? , StandbyFlag12 = ? , StandbyFlag13 = ? , StandbyFlag14 = ? , StandbyFlag15 = ? , StandbyFlag16 = ? , StandbyFlag17 = ? , StandbyFlag18 = ? , StandbyFlag19 = ? , StandbyFlag20 = ? , StandbyFlag21 = ? , StandbyFlag22 = ? , StandbyFlag23 = ? , StandbyFlag24 = ? , Remark = ? , Makedate = ? , Maketime = ? , ModifyDate = ? , ModifyTime = ? , Operator = ? WHERE  BudgetNo = ? AND OtherNo = ? AND OtherNoType = ?");
			if(this.getBudgetNo() == null || this.getBudgetNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBudgetNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOtherNoType());
			}
			pstmt.setInt(4, this.getExpPeoples());
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getStandbyFlag3());
			}
			if(this.getStandbyFlag4() == null || this.getStandbyFlag4().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getStandbyFlag4());
			}
			if(this.getStandbyFlag5() == null || this.getStandbyFlag5().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getStandbyFlag5());
			}
			if(this.getStandbyFlag6() == null || this.getStandbyFlag6().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getStandbyFlag6());
			}
			if(this.getStandbyFlag7() == null || this.getStandbyFlag7().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getStandbyFlag7());
			}
			if(this.getStandbyFlag8() == null || this.getStandbyFlag8().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getStandbyFlag8());
			}
			if(this.getStandbyFlag9() == null || this.getStandbyFlag9().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStandbyFlag9());
			}
			if(this.getStandbyFlag10() == null || this.getStandbyFlag10().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getStandbyFlag10());
			}
			if(this.getStandbyFlag11() == null || this.getStandbyFlag11().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getStandbyFlag11());
			}
			if(this.getStandbyFlag12() == null || this.getStandbyFlag12().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getStandbyFlag12());
			}
			if(this.getStandbyFlag13() == null || this.getStandbyFlag13().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getStandbyFlag13());
			}
			if(this.getStandbyFlag14() == null || this.getStandbyFlag14().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getStandbyFlag14());
			}
			if(this.getStandbyFlag15() == null || this.getStandbyFlag15().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStandbyFlag15());
			}
			if(this.getStandbyFlag16() == null || this.getStandbyFlag16().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandbyFlag16());
			}
			if(this.getStandbyFlag17() == null || this.getStandbyFlag17().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandbyFlag17());
			}
			if(this.getStandbyFlag18() == null || this.getStandbyFlag18().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getStandbyFlag18());
			}
			if(this.getStandbyFlag19() == null || this.getStandbyFlag19().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getStandbyFlag19());
			}
			if(this.getStandbyFlag20() == null || this.getStandbyFlag20().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getStandbyFlag20());
			}
			if(this.getStandbyFlag21() == null || this.getStandbyFlag21().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStandbyFlag21());
			}
			if(this.getStandbyFlag22() == null || this.getStandbyFlag22().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStandbyFlag22());
			}
			if(this.getStandbyFlag23() == null || this.getStandbyFlag23().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStandbyFlag23());
			}
			if(this.getStandbyFlag24() == null || this.getStandbyFlag24().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStandbyFlag24());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getRemark());
			}
			if(this.getMakedate() == null || this.getMakedate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getMakedate()));
			}
			if(this.getMaketime() == null || this.getMaketime().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getMaketime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getModifyTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getOperator());
			}
			// set where condition
			if(this.getBudgetNo() == null || this.getBudgetNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getBudgetNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOtherNoType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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
		SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LCGrpGenaltuaition(BudgetNo ,OtherNo ,OtherNoType ,ExpPeoples ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,StandbyFlag4 ,StandbyFlag5 ,StandbyFlag6 ,StandbyFlag7 ,StandbyFlag8 ,StandbyFlag9 ,StandbyFlag10 ,StandbyFlag11 ,StandbyFlag12 ,StandbyFlag13 ,StandbyFlag14 ,StandbyFlag15 ,StandbyFlag16 ,StandbyFlag17 ,StandbyFlag18 ,StandbyFlag19 ,StandbyFlag20 ,StandbyFlag21 ,StandbyFlag22 ,StandbyFlag23 ,StandbyFlag24 ,Remark ,Makedate ,Maketime ,ModifyDate ,ModifyTime ,Operator) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBudgetNo() == null || this.getBudgetNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBudgetNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOtherNoType());
			}
			pstmt.setInt(4, this.getExpPeoples());
			if(this.getStandbyFlag1() == null || this.getStandbyFlag1().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getStandbyFlag1());
			}
			if(this.getStandbyFlag2() == null || this.getStandbyFlag2().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getStandbyFlag2());
			}
			if(this.getStandbyFlag3() == null || this.getStandbyFlag3().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getStandbyFlag3());
			}
			if(this.getStandbyFlag4() == null || this.getStandbyFlag4().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getStandbyFlag4());
			}
			if(this.getStandbyFlag5() == null || this.getStandbyFlag5().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getStandbyFlag5());
			}
			if(this.getStandbyFlag6() == null || this.getStandbyFlag6().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getStandbyFlag6());
			}
			if(this.getStandbyFlag7() == null || this.getStandbyFlag7().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getStandbyFlag7());
			}
			if(this.getStandbyFlag8() == null || this.getStandbyFlag8().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getStandbyFlag8());
			}
			if(this.getStandbyFlag9() == null || this.getStandbyFlag9().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getStandbyFlag9());
			}
			if(this.getStandbyFlag10() == null || this.getStandbyFlag10().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getStandbyFlag10());
			}
			if(this.getStandbyFlag11() == null || this.getStandbyFlag11().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getStandbyFlag11());
			}
			if(this.getStandbyFlag12() == null || this.getStandbyFlag12().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getStandbyFlag12());
			}
			if(this.getStandbyFlag13() == null || this.getStandbyFlag13().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getStandbyFlag13());
			}
			if(this.getStandbyFlag14() == null || this.getStandbyFlag14().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getStandbyFlag14());
			}
			if(this.getStandbyFlag15() == null || this.getStandbyFlag15().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getStandbyFlag15());
			}
			if(this.getStandbyFlag16() == null || this.getStandbyFlag16().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getStandbyFlag16());
			}
			if(this.getStandbyFlag17() == null || this.getStandbyFlag17().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getStandbyFlag17());
			}
			if(this.getStandbyFlag18() == null || this.getStandbyFlag18().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getStandbyFlag18());
			}
			if(this.getStandbyFlag19() == null || this.getStandbyFlag19().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getStandbyFlag19());
			}
			if(this.getStandbyFlag20() == null || this.getStandbyFlag20().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getStandbyFlag20());
			}
			if(this.getStandbyFlag21() == null || this.getStandbyFlag21().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getStandbyFlag21());
			}
			if(this.getStandbyFlag22() == null || this.getStandbyFlag22().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getStandbyFlag22());
			}
			if(this.getStandbyFlag23() == null || this.getStandbyFlag23().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getStandbyFlag23());
			}
			if(this.getStandbyFlag24() == null || this.getStandbyFlag24().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getStandbyFlag24());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getRemark());
			}
			if(this.getMakedate() == null || this.getMakedate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getMakedate()));
			}
			if(this.getMaketime() == null || this.getMaketime().equals("null")) {
				pstmt.setNull(31, 1);
			} else {
				pstmt.setString(31, this.getMaketime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(33, 1);
			} else {
				pstmt.setString(33, this.getModifyTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LCGrpGenaltuaition WHERE  BudgetNo = ? AND OtherNo = ? AND OtherNoType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBudgetNo() == null || this.getBudgetNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBudgetNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOtherNoType());
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
					tError.moduleName = "LCGrpGenaltuaitionDB";
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
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

	public LCGrpGenaltuaitionSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCGrpGenaltuaitionSet aLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
			LCGrpGenaltuaitionSchema aSchema = this.getSchema();
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
				LCGrpGenaltuaitionSchema s1 = new LCGrpGenaltuaitionSchema();
				s1.setSchema(rs,i);
				aLCGrpGenaltuaitionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

		return aLCGrpGenaltuaitionSet;

	}

	public LCGrpGenaltuaitionSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGrpGenaltuaitionSet aLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();

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
				LCGrpGenaltuaitionSchema s1 = new LCGrpGenaltuaitionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpGenaltuaitionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpGenaltuaitionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

		return aLCGrpGenaltuaitionSet;
	}

	public LCGrpGenaltuaitionSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LCGrpGenaltuaitionSet aLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
			LCGrpGenaltuaitionSchema aSchema = this.getSchema();
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

				LCGrpGenaltuaitionSchema s1 = new LCGrpGenaltuaitionSchema();
				s1.setSchema(rs,i);
				aLCGrpGenaltuaitionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

		return aLCGrpGenaltuaitionSet;

	}

	public LCGrpGenaltuaitionSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCGrpGenaltuaitionSet aLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();

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

				LCGrpGenaltuaitionSchema s1 = new LCGrpGenaltuaitionSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpGenaltuaitionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpGenaltuaitionSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

		return aLCGrpGenaltuaitionSet;
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
			SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
			LCGrpGenaltuaitionSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCGrpGenaltuaition " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpGenaltuaitionDB";
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
			tError.moduleName = "LCGrpGenaltuaitionDB";
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
        tError.moduleName = "LCGrpGenaltuaitionDB";
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
        tError.moduleName = "LCGrpGenaltuaitionDB";
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
        tError.moduleName = "LCGrpGenaltuaitionDB";
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
        tError.moduleName = "LCGrpGenaltuaitionDB";
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
 * @return LCGrpGenaltuaitionSet
 */
public LCGrpGenaltuaitionSet getData()
{
    int tCount = 0;
    LCGrpGenaltuaitionSet tLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();
    LCGrpGenaltuaitionSchema tLCGrpGenaltuaitionSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LCGrpGenaltuaitionDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLCGrpGenaltuaitionSchema = new LCGrpGenaltuaitionSchema();
        tLCGrpGenaltuaitionSchema.setSchema(mResultSet, 1);
        tLCGrpGenaltuaitionSet.add(tLCGrpGenaltuaitionSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLCGrpGenaltuaitionSchema = new LCGrpGenaltuaitionSchema();
                tLCGrpGenaltuaitionSchema.setSchema(mResultSet, 1);
                tLCGrpGenaltuaitionSet.add(tLCGrpGenaltuaitionSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LCGrpGenaltuaitionDB";
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
    return tLCGrpGenaltuaitionSet;
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
            tError.moduleName = "LCGrpGenaltuaitionDB";
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
        tError.moduleName = "LCGrpGenaltuaitionDB";
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
            tError.moduleName = "LCGrpGenaltuaitionDB";
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
        tError.moduleName = "LCGrpGenaltuaitionDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LCGrpGenaltuaitionSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpGenaltuaitionSet aLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();

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
				LCGrpGenaltuaitionSchema s1 = new LCGrpGenaltuaitionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpGenaltuaitionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpGenaltuaitionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

		return aLCGrpGenaltuaitionSet;
	}

	public LCGrpGenaltuaitionSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LCGrpGenaltuaitionSet aLCGrpGenaltuaitionSet = new LCGrpGenaltuaitionSet();

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

				LCGrpGenaltuaitionSchema s1 = new LCGrpGenaltuaitionSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCGrpGenaltuaitionDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLCGrpGenaltuaitionSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionDB";
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

		return aLCGrpGenaltuaitionSet; 
	}

}
