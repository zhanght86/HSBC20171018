/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCGrpGenaltuaitionSchema;
import com.sinosoft.lis.vschema.LCGrpGenaltuaitionSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpGenaltuaitionDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 核保测算
 */
public class LCGrpGenaltuaitionDBSet extends LCGrpGenaltuaitionSet
{
private static Logger logger = Logger.getLogger(LCGrpGenaltuaitionDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCGrpGenaltuaitionDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCGrpGenaltuaition");
		mflag = true;
	}

	public LCGrpGenaltuaitionDBSet()
	{
		db = new DBOper( "LCGrpGenaltuaition" );
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
			tError.moduleName = "LCGrpGenaltuaitionDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCGrpGenaltuaition WHERE  BudgetNo = ? AND OtherNo = ? AND OtherNoType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBudgetNo() == null || this.get(i).getBudgetNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBudgetNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
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
			tError.moduleName = "LCGrpGenaltuaitionDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCGrpGenaltuaition SET  BudgetNo = ? , OtherNo = ? , OtherNoType = ? , ExpPeoples = ? , StandbyFlag1 = ? , StandbyFlag2 = ? , StandbyFlag3 = ? , StandbyFlag4 = ? , StandbyFlag5 = ? , StandbyFlag6 = ? , StandbyFlag7 = ? , StandbyFlag8 = ? , StandbyFlag9 = ? , StandbyFlag10 = ? , StandbyFlag11 = ? , StandbyFlag12 = ? , StandbyFlag13 = ? , StandbyFlag14 = ? , StandbyFlag15 = ? , StandbyFlag16 = ? , StandbyFlag17 = ? , StandbyFlag18 = ? , StandbyFlag19 = ? , StandbyFlag20 = ? , StandbyFlag21 = ? , StandbyFlag22 = ? , StandbyFlag23 = ? , StandbyFlag24 = ? , Remark = ? , Makedate = ? , Maketime = ? , ModifyDate = ? , ModifyTime = ? , Operator = ? WHERE  BudgetNo = ? AND OtherNo = ? AND OtherNoType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBudgetNo() == null || this.get(i).getBudgetNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBudgetNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}
			pstmt.setInt(4, this.get(i).getExpPeoples());
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getStandbyFlag4() == null || this.get(i).getStandbyFlag4().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getStandbyFlag4());
			}
			if(this.get(i).getStandbyFlag5() == null || this.get(i).getStandbyFlag5().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getStandbyFlag5());
			}
			if(this.get(i).getStandbyFlag6() == null || this.get(i).getStandbyFlag6().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getStandbyFlag6());
			}
			if(this.get(i).getStandbyFlag7() == null || this.get(i).getStandbyFlag7().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStandbyFlag7());
			}
			if(this.get(i).getStandbyFlag8() == null || this.get(i).getStandbyFlag8().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStandbyFlag8());
			}
			if(this.get(i).getStandbyFlag9() == null || this.get(i).getStandbyFlag9().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStandbyFlag9());
			}
			if(this.get(i).getStandbyFlag10() == null || this.get(i).getStandbyFlag10().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getStandbyFlag10());
			}
			if(this.get(i).getStandbyFlag11() == null || this.get(i).getStandbyFlag11().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getStandbyFlag11());
			}
			if(this.get(i).getStandbyFlag12() == null || this.get(i).getStandbyFlag12().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStandbyFlag12());
			}
			if(this.get(i).getStandbyFlag13() == null || this.get(i).getStandbyFlag13().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyFlag13());
			}
			if(this.get(i).getStandbyFlag14() == null || this.get(i).getStandbyFlag14().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandbyFlag14());
			}
			if(this.get(i).getStandbyFlag15() == null || this.get(i).getStandbyFlag15().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getStandbyFlag15());
			}
			if(this.get(i).getStandbyFlag16() == null || this.get(i).getStandbyFlag16().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStandbyFlag16());
			}
			if(this.get(i).getStandbyFlag17() == null || this.get(i).getStandbyFlag17().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandbyFlag17());
			}
			if(this.get(i).getStandbyFlag18() == null || this.get(i).getStandbyFlag18().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandbyFlag18());
			}
			if(this.get(i).getStandbyFlag19() == null || this.get(i).getStandbyFlag19().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStandbyFlag19());
			}
			if(this.get(i).getStandbyFlag20() == null || this.get(i).getStandbyFlag20().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandbyFlag20());
			}
			if(this.get(i).getStandbyFlag21() == null || this.get(i).getStandbyFlag21().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandbyFlag21());
			}
			if(this.get(i).getStandbyFlag22() == null || this.get(i).getStandbyFlag22().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandbyFlag22());
			}
			if(this.get(i).getStandbyFlag23() == null || this.get(i).getStandbyFlag23().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStandbyFlag23());
			}
			if(this.get(i).getStandbyFlag24() == null || this.get(i).getStandbyFlag24().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStandbyFlag24());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getRemark());
			}
			if(this.get(i).getMakedate() == null || this.get(i).getMakedate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getMakedate()));
			}
			if(this.get(i).getMaketime() == null || this.get(i).getMaketime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getMaketime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOperator());
			}
			// set where condition
			if(this.get(i).getBudgetNo() == null || this.get(i).getBudgetNo().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getBudgetNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOtherNoType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
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
			tError.moduleName = "LCGrpGenaltuaitionDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCGrpGenaltuaition(BudgetNo ,OtherNo ,OtherNoType ,ExpPeoples ,StandbyFlag1 ,StandbyFlag2 ,StandbyFlag3 ,StandbyFlag4 ,StandbyFlag5 ,StandbyFlag6 ,StandbyFlag7 ,StandbyFlag8 ,StandbyFlag9 ,StandbyFlag10 ,StandbyFlag11 ,StandbyFlag12 ,StandbyFlag13 ,StandbyFlag14 ,StandbyFlag15 ,StandbyFlag16 ,StandbyFlag17 ,StandbyFlag18 ,StandbyFlag19 ,StandbyFlag20 ,StandbyFlag21 ,StandbyFlag22 ,StandbyFlag23 ,StandbyFlag24 ,Remark ,Makedate ,Maketime ,ModifyDate ,ModifyTime ,Operator) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBudgetNo() == null || this.get(i).getBudgetNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBudgetNo());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOtherNoType());
			}
			pstmt.setInt(4, this.get(i).getExpPeoples());
			if(this.get(i).getStandbyFlag1() == null || this.get(i).getStandbyFlag1().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getStandbyFlag1());
			}
			if(this.get(i).getStandbyFlag2() == null || this.get(i).getStandbyFlag2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getStandbyFlag2());
			}
			if(this.get(i).getStandbyFlag3() == null || this.get(i).getStandbyFlag3().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getStandbyFlag3());
			}
			if(this.get(i).getStandbyFlag4() == null || this.get(i).getStandbyFlag4().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getStandbyFlag4());
			}
			if(this.get(i).getStandbyFlag5() == null || this.get(i).getStandbyFlag5().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getStandbyFlag5());
			}
			if(this.get(i).getStandbyFlag6() == null || this.get(i).getStandbyFlag6().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getStandbyFlag6());
			}
			if(this.get(i).getStandbyFlag7() == null || this.get(i).getStandbyFlag7().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getStandbyFlag7());
			}
			if(this.get(i).getStandbyFlag8() == null || this.get(i).getStandbyFlag8().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getStandbyFlag8());
			}
			if(this.get(i).getStandbyFlag9() == null || this.get(i).getStandbyFlag9().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getStandbyFlag9());
			}
			if(this.get(i).getStandbyFlag10() == null || this.get(i).getStandbyFlag10().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getStandbyFlag10());
			}
			if(this.get(i).getStandbyFlag11() == null || this.get(i).getStandbyFlag11().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getStandbyFlag11());
			}
			if(this.get(i).getStandbyFlag12() == null || this.get(i).getStandbyFlag12().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStandbyFlag12());
			}
			if(this.get(i).getStandbyFlag13() == null || this.get(i).getStandbyFlag13().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyFlag13());
			}
			if(this.get(i).getStandbyFlag14() == null || this.get(i).getStandbyFlag14().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandbyFlag14());
			}
			if(this.get(i).getStandbyFlag15() == null || this.get(i).getStandbyFlag15().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getStandbyFlag15());
			}
			if(this.get(i).getStandbyFlag16() == null || this.get(i).getStandbyFlag16().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getStandbyFlag16());
			}
			if(this.get(i).getStandbyFlag17() == null || this.get(i).getStandbyFlag17().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getStandbyFlag17());
			}
			if(this.get(i).getStandbyFlag18() == null || this.get(i).getStandbyFlag18().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getStandbyFlag18());
			}
			if(this.get(i).getStandbyFlag19() == null || this.get(i).getStandbyFlag19().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getStandbyFlag19());
			}
			if(this.get(i).getStandbyFlag20() == null || this.get(i).getStandbyFlag20().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getStandbyFlag20());
			}
			if(this.get(i).getStandbyFlag21() == null || this.get(i).getStandbyFlag21().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandbyFlag21());
			}
			if(this.get(i).getStandbyFlag22() == null || this.get(i).getStandbyFlag22().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandbyFlag22());
			}
			if(this.get(i).getStandbyFlag23() == null || this.get(i).getStandbyFlag23().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getStandbyFlag23());
			}
			if(this.get(i).getStandbyFlag24() == null || this.get(i).getStandbyFlag24().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getStandbyFlag24());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getRemark());
			}
			if(this.get(i).getMakedate() == null || this.get(i).getMakedate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getMakedate()));
			}
			if(this.get(i).getMaketime() == null || this.get(i).getMaketime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getMaketime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(32,null);
			} else {
				pstmt.setDate(32, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpGenaltuaition");
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
			tError.moduleName = "LCGrpGenaltuaitionDBSet";
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
