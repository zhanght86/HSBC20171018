/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMClaimCtrlPeriodSchema;
import com.sinosoft.lis.vschema.LMClaimCtrlPeriodSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMClaimCtrlPeriodDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMClaimCtrlPeriodDBSet extends LMClaimCtrlPeriodSet
{
private static Logger logger = Logger.getLogger(LMClaimCtrlPeriodDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMClaimCtrlPeriodDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMClaimCtrlPeriod");
		mflag = true;
	}

	public LMClaimCtrlPeriodDBSet()
	{
		db = new DBOper( "LMClaimCtrlPeriod" );
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
			tError.moduleName = "LMClaimCtrlPeriodDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMClaimCtrlPeriod WHERE  ClaimCtrlCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMClaimCtrlPeriod");
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
			tError.moduleName = "LMClaimCtrlPeriodDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMClaimCtrlPeriod SET  ClaimCtrlCode = ? , ClmPeriodStart = ? , ClmPeriodStartFlag = ? , ClmPeriodInterval = ? , ClmPeriodFlag = ? , CalCode = ? , CalResultType = ? , DefaultValue = ? , CalCtrlFlag = ? WHERE  ClaimCtrlCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}
			pstmt.setInt(2, this.get(i).getClmPeriodStart());
			if(this.get(i).getClmPeriodStartFlag() == null || this.get(i).getClmPeriodStartFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClmPeriodStartFlag());
			}
			pstmt.setInt(4, this.get(i).getClmPeriodInterval());
			if(this.get(i).getClmPeriodFlag() == null || this.get(i).getClmPeriodFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmPeriodFlag());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode());
			}
			if(this.get(i).getCalResultType() == null || this.get(i).getCalResultType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCalResultType());
			}
			pstmt.setDouble(8, this.get(i).getDefaultValue());
			if(this.get(i).getCalCtrlFlag() == null || this.get(i).getCalCtrlFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCalCtrlFlag());
			}
			// set where condition
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getClaimCtrlCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMClaimCtrlPeriod");
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
			tError.moduleName = "LMClaimCtrlPeriodDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMClaimCtrlPeriod(ClaimCtrlCode ,ClmPeriodStart ,ClmPeriodStartFlag ,ClmPeriodInterval ,ClmPeriodFlag ,CalCode ,CalResultType ,DefaultValue ,CalCtrlFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}
			pstmt.setInt(2, this.get(i).getClmPeriodStart());
			if(this.get(i).getClmPeriodStartFlag() == null || this.get(i).getClmPeriodStartFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClmPeriodStartFlag());
			}
			pstmt.setInt(4, this.get(i).getClmPeriodInterval());
			if(this.get(i).getClmPeriodFlag() == null || this.get(i).getClmPeriodFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmPeriodFlag());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode());
			}
			if(this.get(i).getCalResultType() == null || this.get(i).getCalResultType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCalResultType());
			}
			pstmt.setDouble(8, this.get(i).getDefaultValue());
			if(this.get(i).getCalCtrlFlag() == null || this.get(i).getCalCtrlFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCalCtrlFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMClaimCtrlPeriod");
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
			tError.moduleName = "LMClaimCtrlPeriodDBSet";
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
