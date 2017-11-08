/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMClaimCtrlFeeSchema;
import com.sinosoft.lis.vschema.LMClaimCtrlFeeSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMClaimCtrlFeeDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMClaimCtrlFeeDBSet extends LMClaimCtrlFeeSet
{
private static Logger logger = Logger.getLogger(LMClaimCtrlFeeDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMClaimCtrlFeeDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMClaimCtrlFee");
		mflag = true;
	}

	public LMClaimCtrlFeeDBSet()
	{
		db = new DBOper( "LMClaimCtrlFee" );
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
			tError.moduleName = "LMClaimCtrlFeeDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMClaimCtrlFee WHERE  ClaimCtrlCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMClaimCtrlFee");
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
			tError.moduleName = "LMClaimCtrlFeeDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMClaimCtrlFee SET  ClaimCtrlCode = ? , ClmFeeMIN = ? , ClmFeeMINFlag = ? , ClmFeeInterval = ? , ClmFeeFlag = ? , CalCode2 = ? , CalResultType = ? , DefaultValue = ? , CalCtrlFlag = ? WHERE  ClaimCtrlCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}
			pstmt.setDouble(2, this.get(i).getClmFeeMIN());
			if(this.get(i).getClmFeeMINFlag() == null || this.get(i).getClmFeeMINFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClmFeeMINFlag());
			}
			pstmt.setDouble(4, this.get(i).getClmFeeInterval());
			if(this.get(i).getClmFeeFlag() == null || this.get(i).getClmFeeFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmFeeFlag());
			}
			if(this.get(i).getCalCode2() == null || this.get(i).getCalCode2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode2());
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
		SQLString sqlObj = new SQLString("LMClaimCtrlFee");
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
			tError.moduleName = "LMClaimCtrlFeeDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMClaimCtrlFee(ClaimCtrlCode ,ClmFeeMIN ,ClmFeeMINFlag ,ClmFeeInterval ,ClmFeeFlag ,CalCode2 ,CalResultType ,DefaultValue ,CalCtrlFlag) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}
			pstmt.setDouble(2, this.get(i).getClmFeeMIN());
			if(this.get(i).getClmFeeMINFlag() == null || this.get(i).getClmFeeMINFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClmFeeMINFlag());
			}
			pstmt.setDouble(4, this.get(i).getClmFeeInterval());
			if(this.get(i).getClmFeeFlag() == null || this.get(i).getClmFeeFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmFeeFlag());
			}
			if(this.get(i).getCalCode2() == null || this.get(i).getCalCode2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode2());
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
		SQLString sqlObj = new SQLString("LMClaimCtrlFee");
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
			tError.moduleName = "LMClaimCtrlFeeDBSet";
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
