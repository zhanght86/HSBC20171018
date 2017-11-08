

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.RIBsnsBillRelaSchema;
import com.sinosoft.lis.vschema.RIBsnsBillRelaSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: RIBsnsBillRelaDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBsnsBillRelaDBSet extends RIBsnsBillRelaSet
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
	public RIBsnsBillRelaDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"RIBsnsBillRela");
		mflag = true;
	}

	public RIBsnsBillRelaDBSet()
	{
		db = new DBOper( "RIBsnsBillRela" );
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
			tError.moduleName = "RIBsnsBillRelaDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM RIBsnsBillRela WHERE  BillNo = ? AND CalOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBillNo());
			}
			pstmt.setInt(2, this.get(i).getCalOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBsnsBillRela");
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
			tError.moduleName = "RIBsnsBillRelaDBSet";
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
			pstmt = con.prepareStatement("UPDATE RIBsnsBillRela SET  BillNo = ? , CalOrder = ? , FeeCode = ? , FeeName = ? , ReComCode = ? , DebCre = ? , Details = ? , DetailsName = ? , InputType = ? , BillItemType = ? , ItemCalType = ? , DoubleValue = ? , CalClass = ? , CalSQL = ? WHERE  BillNo = ? AND CalOrder = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBillNo());
			}
			pstmt.setInt(2, this.get(i).getCalOrder());
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getFeeCode());
			}
			if(this.get(i).getFeeName() == null || this.get(i).getFeeName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFeeName());
			}
			if(this.get(i).getReComCode() == null || this.get(i).getReComCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getReComCode());
			}
			if(this.get(i).getDebCre() == null || this.get(i).getDebCre().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDebCre());
			}
			if(this.get(i).getDetails() == null || this.get(i).getDetails().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDetails());
			}
			if(this.get(i).getDetailsName() == null || this.get(i).getDetailsName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDetailsName());
			}
			if(this.get(i).getInputType() == null || this.get(i).getInputType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getInputType());
			}
			if(this.get(i).getBillItemType() == null || this.get(i).getBillItemType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBillItemType());
			}
			if(this.get(i).getItemCalType() == null || this.get(i).getItemCalType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getItemCalType());
			}
			pstmt.setDouble(12, this.get(i).getDoubleValue());
			if(this.get(i).getCalClass() == null || this.get(i).getCalClass().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalClass());
			}
			if(this.get(i).getCalSQL() == null || this.get(i).getCalSQL().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCalSQL());
			}
			// set where condition
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBillNo());
			}
			pstmt.setInt(16, this.get(i).getCalOrder());

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBsnsBillRela");
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
			tError.moduleName = "RIBsnsBillRelaDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO RIBsnsBillRela(BillNo ,CalOrder ,FeeCode ,FeeName ,ReComCode ,DebCre ,Details ,DetailsName ,InputType ,BillItemType ,ItemCalType ,DoubleValue ,CalClass ,CalSQL) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBillNo());
			}
			pstmt.setInt(2, this.get(i).getCalOrder());
			if(this.get(i).getFeeCode() == null || this.get(i).getFeeCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getFeeCode());
			}
			if(this.get(i).getFeeName() == null || this.get(i).getFeeName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFeeName());
			}
			if(this.get(i).getReComCode() == null || this.get(i).getReComCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getReComCode());
			}
			if(this.get(i).getDebCre() == null || this.get(i).getDebCre().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDebCre());
			}
			if(this.get(i).getDetails() == null || this.get(i).getDetails().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getDetails());
			}
			if(this.get(i).getDetailsName() == null || this.get(i).getDetailsName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDetailsName());
			}
			if(this.get(i).getInputType() == null || this.get(i).getInputType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getInputType());
			}
			if(this.get(i).getBillItemType() == null || this.get(i).getBillItemType().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBillItemType());
			}
			if(this.get(i).getItemCalType() == null || this.get(i).getItemCalType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getItemCalType());
			}
			pstmt.setDouble(12, this.get(i).getDoubleValue());
			if(this.get(i).getCalClass() == null || this.get(i).getCalClass().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalClass());
			}
			if(this.get(i).getCalSQL() == null || this.get(i).getCalSQL().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCalSQL());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("RIBsnsBillRela");
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
			tError.moduleName = "RIBsnsBillRelaDBSet";
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
