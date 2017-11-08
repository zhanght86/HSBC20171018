/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBExRateSchema;
import com.sinosoft.lis.vschema.LBExRateSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBExRateDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造基础表
 */
public class LBExRateDBSet extends LBExRateSet
{
private static Logger logger = Logger.getLogger(LBExRateDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBExRateDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBExRate");
		mflag = true;
	}

	public LBExRateDBSet()
	{
		db = new DBOper( "LBExRate" );
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
			tError.moduleName = "LBExRateDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBExRate WHERE  CurrCode = ? AND Per = ? AND DestCode = ? AND StartDate = ? AND StartTime = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCurrCode() == null || this.get(i).getCurrCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCurrCode());
			}
			pstmt.setInt(2, this.get(i).getPer());
			if(this.get(i).getDestCode() == null || this.get(i).getDestCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDestCode());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getStartTime() == null || this.get(i).getStartTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getStartTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBExRate");
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
			tError.moduleName = "LBExRateDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBExRate SET  CurrCode = ? , Per = ? , DestCode = ? , ExchBid = ? , ExchOffer = ? , CashBid = ? , CashOffer = ? , Middle = ? , StartDate = ? , StartTime = ? , EndDate = ? , EndTime = ? WHERE  CurrCode = ? AND Per = ? AND DestCode = ? AND StartDate = ? AND StartTime = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCurrCode() == null || this.get(i).getCurrCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCurrCode());
			}
			pstmt.setInt(2, this.get(i).getPer());
			if(this.get(i).getDestCode() == null || this.get(i).getDestCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDestCode());
			}
			pstmt.setDouble(4, this.get(i).getExchBid());
			pstmt.setDouble(5, this.get(i).getExchOffer());
			pstmt.setDouble(6, this.get(i).getCashBid());
			pstmt.setDouble(7, this.get(i).getCashOffer());
			pstmt.setDouble(8, this.get(i).getMiddle());
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getStartTime() == null || this.get(i).getStartTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getStartTime());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getEndTime() == null || this.get(i).getEndTime().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getEndTime());
			}
			// set where condition
			if(this.get(i).getCurrCode() == null || this.get(i).getCurrCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCurrCode());
			}
			pstmt.setInt(14, this.get(i).getPer());
			if(this.get(i).getDestCode() == null || this.get(i).getDestCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getDestCode());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getStartTime() == null || this.get(i).getStartTime().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStartTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBExRate");
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
			tError.moduleName = "LBExRateDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBExRate(CurrCode ,Per ,DestCode ,ExchBid ,ExchOffer ,CashBid ,CashOffer ,Middle ,StartDate ,StartTime ,EndDate ,EndTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCurrCode() == null || this.get(i).getCurrCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCurrCode());
			}
			pstmt.setInt(2, this.get(i).getPer());
			if(this.get(i).getDestCode() == null || this.get(i).getDestCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDestCode());
			}
			pstmt.setDouble(4, this.get(i).getExchBid());
			pstmt.setDouble(5, this.get(i).getExchOffer());
			pstmt.setDouble(6, this.get(i).getCashBid());
			pstmt.setDouble(7, this.get(i).getCashOffer());
			pstmt.setDouble(8, this.get(i).getMiddle());
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getStartTime() == null || this.get(i).getStartTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getStartTime());
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getEndTime() == null || this.get(i).getEndTime().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getEndTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBExRate");
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
			tError.moduleName = "LBExRateDBSet";
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
