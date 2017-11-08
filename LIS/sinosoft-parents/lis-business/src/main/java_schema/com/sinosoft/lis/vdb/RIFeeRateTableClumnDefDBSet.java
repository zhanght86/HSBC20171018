

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIFeeRateTableClumnDefSchema;
import com.sinosoft.lis.vschema.RIFeeRateTableClumnDefSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIFeeRateTableClumnDefDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIFeeRateTableClumnDefDBSet extends RIFeeRateTableClumnDefSet
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
	public RIFeeRateTableClumnDefDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIFeeRateTableClumnDef");
		mflag = true;
	}

	public RIFeeRateTableClumnDefDBSet()
	{
		db = new DBOper( "RIFeeRateTableClumnDef" );
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
			tError.moduleName = "RIFeeRateTableClumnDefDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIFeeRateTableClumnDef WHERE  FeeTableNo = ? AND FeeClumnNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeTableNo() == null || this.get(i).getFeeTableNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeTableNo());
			}
			pstmt.setInt(2, this.get(i).getFeeClumnNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIFeeRateTableClumnDef");
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
			tError.moduleName = "RIFeeRateTableClumnDefDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIFeeRateTableClumnDef SET  FeeTableNo = ? , FeeTableName = ? , FeeClumnNo = ? , FeeClumnName = ? , FeeClumnDataCode = ? , FeeClumnType = ? , TagetClumn = ? , TagetULClumn = ? , TagetDLClumn = ? WHERE  FeeTableNo = ? AND FeeClumnNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeTableNo() == null || this.get(i).getFeeTableNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeTableNo());
			}
			if(this.get(i).getFeeTableName() == null || this.get(i).getFeeTableName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeTableName());
			}
			pstmt.setInt(3, this.get(i).getFeeClumnNo());
			if(this.get(i).getFeeClumnName() == null || this.get(i).getFeeClumnName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFeeClumnName());
			}
			if(this.get(i).getFeeClumnDataCode() == null || this.get(i).getFeeClumnDataCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFeeClumnDataCode());
			}
			if(this.get(i).getFeeClumnType() == null || this.get(i).getFeeClumnType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFeeClumnType());
			}
			if(this.get(i).getTagetClumn() == null || this.get(i).getTagetClumn().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getTagetClumn());
			}
			if(this.get(i).getTagetULClumn() == null || this.get(i).getTagetULClumn().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getTagetULClumn());
			}
			if(this.get(i).getTagetDLClumn() == null || this.get(i).getTagetDLClumn().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getTagetDLClumn());
			}
			// set where condition
			if(this.get(i).getFeeTableNo() == null || this.get(i).getFeeTableNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getFeeTableNo());
			}
			pstmt.setInt(11, this.get(i).getFeeClumnNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIFeeRateTableClumnDef");
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
			tError.moduleName = "RIFeeRateTableClumnDefDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIFeeRateTableClumnDef(FeeTableNo ,FeeTableName ,FeeClumnNo ,FeeClumnName ,FeeClumnDataCode ,FeeClumnType ,TagetClumn ,TagetULClumn ,TagetDLClumn) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeTableNo() == null || this.get(i).getFeeTableNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeTableNo());
			}
			if(this.get(i).getFeeTableName() == null || this.get(i).getFeeTableName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeTableName());
			}
			pstmt.setInt(3, this.get(i).getFeeClumnNo());
			if(this.get(i).getFeeClumnName() == null || this.get(i).getFeeClumnName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFeeClumnName());
			}
			if(this.get(i).getFeeClumnDataCode() == null || this.get(i).getFeeClumnDataCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getFeeClumnDataCode());
			}
			if(this.get(i).getFeeClumnType() == null || this.get(i).getFeeClumnType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFeeClumnType());
			}
			if(this.get(i).getTagetClumn() == null || this.get(i).getTagetClumn().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getTagetClumn());
			}
			if(this.get(i).getTagetULClumn() == null || this.get(i).getTagetULClumn().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getTagetULClumn());
			}
			if(this.get(i).getTagetDLClumn() == null || this.get(i).getTagetDLClumn().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getTagetDLClumn());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIFeeRateTableClumnDef");
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
			tError.moduleName = "RIFeeRateTableClumnDefDBSet";
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
