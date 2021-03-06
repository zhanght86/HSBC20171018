/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LJPremMatchLogSchema;
import com.sinosoft.lis.vschema.LJPremMatchLogSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJPremMatchLogDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPremMatchLogDBSet extends LJPremMatchLogSet
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
	public LJPremMatchLogDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LJPremMatchLog");
		mflag = true;
	}

	public LJPremMatchLogDBSet()
	{
		db = new DBOper( "LJPremMatchLog" );
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
			tError.moduleName = "LJPremMatchLogDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LJPremMatchLog WHERE  MatchSerialNo = ? AND MatchNode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getMatchSerialNo() == null || this.get(i).getMatchSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getMatchSerialNo());
			}
			if(this.get(i).getMatchNode() == null || this.get(i).getMatchNode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMatchNode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJPremMatchLog");
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
			tError.moduleName = "LJPremMatchLogDBSet";
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
			pstmt = con.prepareStatement("UPDATE LJPremMatchLog SET  MatchSerialNo = ? , MatchNode = ? , MatchType = ? , GrpName = ? , SumMoney = ? , HisOutPayMoney = ? , DuePayMoney = ? , CurOutPayMoney = ? , MatchDate = ? , MatchTime = ? , CheckFlag = ? , State = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  MatchSerialNo = ? AND MatchNode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getMatchSerialNo() == null || this.get(i).getMatchSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getMatchSerialNo());
			}
			if(this.get(i).getMatchNode() == null || this.get(i).getMatchNode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMatchNode());
			}
			if(this.get(i).getMatchType() == null || this.get(i).getMatchType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMatchType());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpName());
			}
			pstmt.setDouble(5, this.get(i).getSumMoney());
			pstmt.setDouble(6, this.get(i).getHisOutPayMoney());
			pstmt.setDouble(7, this.get(i).getDuePayMoney());
			pstmt.setDouble(8, this.get(i).getCurOutPayMoney());
			if(this.get(i).getMatchDate() == null || this.get(i).getMatchDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMatchDate()));
			}
			if(this.get(i).getMatchTime() == null || this.get(i).getMatchTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMatchTime());
			}
			if(this.get(i).getCheckFlag() == null || this.get(i).getCheckFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCheckFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getMatchSerialNo() == null || this.get(i).getMatchSerialNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMatchSerialNo());
			}
			if(this.get(i).getMatchNode() == null || this.get(i).getMatchNode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMatchNode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJPremMatchLog");
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
			tError.moduleName = "LJPremMatchLogDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LJPremMatchLog VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getMatchSerialNo() == null || this.get(i).getMatchSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getMatchSerialNo());
			}
			if(this.get(i).getMatchNode() == null || this.get(i).getMatchNode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMatchNode());
			}
			if(this.get(i).getMatchType() == null || this.get(i).getMatchType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMatchType());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpName());
			}
			pstmt.setDouble(5, this.get(i).getSumMoney());
			pstmt.setDouble(6, this.get(i).getHisOutPayMoney());
			pstmt.setDouble(7, this.get(i).getDuePayMoney());
			pstmt.setDouble(8, this.get(i).getCurOutPayMoney());
			if(this.get(i).getMatchDate() == null || this.get(i).getMatchDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMatchDate()));
			}
			if(this.get(i).getMatchTime() == null || this.get(i).getMatchTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMatchTime());
			}
			if(this.get(i).getCheckFlag() == null || this.get(i).getCheckFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCheckFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getManageCom());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getComCode());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJPremMatchLog");
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
			tError.moduleName = "LJPremMatchLogDBSet";
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
