

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIItemCalDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIItemCalDBSet extends RIItemCalSet
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
	public RIItemCalDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIItemCal");
		mflag = true;
	}

	public RIItemCalDBSet()
	{
		db = new DBOper( "RIItemCal" );
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
			tError.moduleName = "RIItemCalDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIItemCal WHERE  ArithmeticID = ? AND ArithmeticType = ? AND CalItemOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getArithmeticID());
			}
			if(this.get(i).getArithmeticType() == null || this.get(i).getArithmeticType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getArithmeticType());
			}
			pstmt.setInt(3, this.get(i).getCalItemOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIItemCal");
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
			tError.moduleName = "RIItemCalDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIItemCal SET  ArithmeticDefID = ? , ArithmeticID = ? , ArithmeticName = ? , ArithmeticType = ? , CalItemID = ? , CalItemName = ? , CalItemOrder = ? , CalItemType = ? , ItemCalType = ? , DoubleValue = ? , CalClass = ? , CalSQL = ? , TarGetClumn = ? , TestCal = ? , ReMark = ? , StandbyString1 = ? , StandbyString2 = ? , StandbyString3 = ? , CalSQL2 = ? , CalSQL3 = ? WHERE  ArithmeticID = ? AND ArithmeticType = ? AND CalItemOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getArithmeticDefID() == null || this.get(i).getArithmeticDefID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getArithmeticDefID());
			}
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getArithmeticID());
			}
			if(this.get(i).getArithmeticName() == null || this.get(i).getArithmeticName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getArithmeticName());
			}
			if(this.get(i).getArithmeticType() == null || this.get(i).getArithmeticType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getArithmeticType());
			}
			if(this.get(i).getCalItemID() == null || this.get(i).getCalItemID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalItemID());
			}
			if(this.get(i).getCalItemName() == null || this.get(i).getCalItemName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalItemName());
			}
			pstmt.setInt(7, this.get(i).getCalItemOrder());
			if(this.get(i).getCalItemType() == null || this.get(i).getCalItemType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCalItemType());
			}
			if(this.get(i).getItemCalType() == null || this.get(i).getItemCalType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getItemCalType());
			}
			pstmt.setDouble(10, this.get(i).getDoubleValue());
			if(this.get(i).getCalClass() == null || this.get(i).getCalClass().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalClass());
			}
			if(this.get(i).getCalSQL() == null || this.get(i).getCalSQL().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCalSQL());
			}
			if(this.get(i).getTarGetClumn() == null || this.get(i).getTarGetClumn().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getTarGetClumn());
			}
			if(this.get(i).getTestCal() == null || this.get(i).getTestCal().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getTestCal());
			}
			if(this.get(i).getReMark() == null || this.get(i).getReMark().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getReMark());
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandbyString3());
			}
			if(this.get(i).getCalSQL2() == null || this.get(i).getCalSQL2().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCalSQL2());
			}
			if(this.get(i).getCalSQL3() == null || this.get(i).getCalSQL3().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCalSQL3());
			}
			// set where condition
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getArithmeticID());
			}
			if(this.get(i).getArithmeticType() == null || this.get(i).getArithmeticType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getArithmeticType());
			}
			pstmt.setInt(23, this.get(i).getCalItemOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIItemCal");
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
			tError.moduleName = "RIItemCalDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIItemCal(ArithmeticDefID ,ArithmeticID ,ArithmeticName ,ArithmeticType ,CalItemID ,CalItemName ,CalItemOrder ,CalItemType ,ItemCalType ,DoubleValue ,CalClass ,CalSQL ,TarGetClumn ,TestCal ,ReMark ,StandbyString1 ,StandbyString2 ,StandbyString3 ,CalSQL2 ,CalSQL3) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getArithmeticDefID() == null || this.get(i).getArithmeticDefID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getArithmeticDefID());
			}
			if(this.get(i).getArithmeticID() == null || this.get(i).getArithmeticID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getArithmeticID());
			}
			if(this.get(i).getArithmeticName() == null || this.get(i).getArithmeticName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getArithmeticName());
			}
			if(this.get(i).getArithmeticType() == null || this.get(i).getArithmeticType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getArithmeticType());
			}
			if(this.get(i).getCalItemID() == null || this.get(i).getCalItemID().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalItemID());
			}
			if(this.get(i).getCalItemName() == null || this.get(i).getCalItemName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalItemName());
			}
			pstmt.setInt(7, this.get(i).getCalItemOrder());
			if(this.get(i).getCalItemType() == null || this.get(i).getCalItemType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCalItemType());
			}
			if(this.get(i).getItemCalType() == null || this.get(i).getItemCalType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getItemCalType());
			}
			pstmt.setDouble(10, this.get(i).getDoubleValue());
			if(this.get(i).getCalClass() == null || this.get(i).getCalClass().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalClass());
			}
			if(this.get(i).getCalSQL() == null || this.get(i).getCalSQL().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCalSQL());
			}
			if(this.get(i).getTarGetClumn() == null || this.get(i).getTarGetClumn().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getTarGetClumn());
			}
			if(this.get(i).getTestCal() == null || this.get(i).getTestCal().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getTestCal());
			}
			if(this.get(i).getReMark() == null || this.get(i).getReMark().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getReMark());
			}
			if(this.get(i).getStandbyString1() == null || this.get(i).getStandbyString1().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getStandbyString1());
			}
			if(this.get(i).getStandbyString2() == null || this.get(i).getStandbyString2().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStandbyString2());
			}
			if(this.get(i).getStandbyString3() == null || this.get(i).getStandbyString3().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandbyString3());
			}
			if(this.get(i).getCalSQL2() == null || this.get(i).getCalSQL2().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCalSQL2());
			}
			if(this.get(i).getCalSQL3() == null || this.get(i).getCalSQL3().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCalSQL3());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIItemCal");
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
			tError.moduleName = "RIItemCalDBSet";
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
