/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LDMthMidRateSchema;
import com.sinosoft.lis.vschema.LDMthMidRateSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDMthMidRateDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造基础表
 */
public class LDMthMidRateDBSet extends LDMthMidRateSet
{
private static Logger logger = Logger.getLogger(LDMthMidRateDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LDMthMidRateDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDMthMidRate");
		mflag = true;
	}

	public LDMthMidRateDBSet()
	{
		db = new DBOper( "LDMthMidRate" );
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
			tError.moduleName = "LDMthMidRateDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDMthMidRate WHERE  CurrCode = ? AND Per = ? AND DestCode = ? AND ValidYear = ? AND ValidMonth = ?");
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
			if(this.get(i).getValidYear() == null || this.get(i).getValidYear().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getValidYear());
			}
			if(this.get(i).getValidMonth() == null || this.get(i).getValidMonth().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getValidMonth());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDMthMidRate");
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
			tError.moduleName = "LDMthMidRateDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDMthMidRate SET  CurrCode = ? , Per = ? , DestCode = ? , Average = ? , ValidYear = ? , ValidMonth = ? WHERE  CurrCode = ? AND Per = ? AND DestCode = ? AND ValidYear = ? AND ValidMonth = ?");
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
			pstmt.setDouble(4, this.get(i).getAverage());
			if(this.get(i).getValidYear() == null || this.get(i).getValidYear().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getValidYear());
			}
			if(this.get(i).getValidMonth() == null || this.get(i).getValidMonth().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getValidMonth());
			}
			// set where condition
			if(this.get(i).getCurrCode() == null || this.get(i).getCurrCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCurrCode());
			}
			pstmt.setInt(8, this.get(i).getPer());
			if(this.get(i).getDestCode() == null || this.get(i).getDestCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getDestCode());
			}
			if(this.get(i).getValidYear() == null || this.get(i).getValidYear().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getValidYear());
			}
			if(this.get(i).getValidMonth() == null || this.get(i).getValidMonth().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getValidMonth());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDMthMidRate");
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
			tError.moduleName = "LDMthMidRateDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDMthMidRate(CurrCode ,Per ,DestCode ,Average ,ValidYear ,ValidMonth) VALUES( ? , ? , ? , ? , ? , ?)");
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
			pstmt.setDouble(4, this.get(i).getAverage());
			if(this.get(i).getValidYear() == null || this.get(i).getValidYear().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getValidYear());
			}
			if(this.get(i).getValidMonth() == null || this.get(i).getValidMonth().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getValidMonth());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDMthMidRate");
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
			tError.moduleName = "LDMthMidRateDBSet";
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
