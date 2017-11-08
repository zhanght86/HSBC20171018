/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LIAccItemSchema;
import com.sinosoft.lis.vschema.LIAccItemSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LIAccItemDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIAccItemDBSet extends LIAccItemSet
{
private static Logger logger = Logger.getLogger(LIAccItemDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LIAccItemDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LIAccItem");
		mflag = true;
	}

	public LIAccItemDBSet()
	{
		db = new DBOper( "LIAccItem" );
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
			tError.moduleName = "LIAccItemDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LIAccItem WHERE  FeeOperationType = ? AND FeeFinaType = ? AND RefeFlag = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getRefeFlag() == null || this.get(i).getRefeFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRefeFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIAccItem");
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
			tError.moduleName = "LIAccItemDBSet";
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
			pstmt = con.prepareStatement("UPDATE LIAccItem SET  FeeOperationType = ? , FeeFinaType = ? , RefeFlag = ? , AccGetItem = ? , AccPayItem = ? , RollBackFlag = ? WHERE  FeeOperationType = ? AND FeeFinaType = ? AND RefeFlag = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getRefeFlag() == null || this.get(i).getRefeFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRefeFlag());
			}
			if(this.get(i).getAccGetItem() == null || this.get(i).getAccGetItem().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccGetItem());
			}
			if(this.get(i).getAccPayItem() == null || this.get(i).getAccPayItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccPayItem());
			}
			if(this.get(i).getRollBackFlag() == null || this.get(i).getRollBackFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRollBackFlag());
			}
			// set where condition
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getRefeFlag() == null || this.get(i).getRefeFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRefeFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIAccItem");
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
			tError.moduleName = "LIAccItemDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LIAccItem(FeeOperationType ,FeeFinaType ,RefeFlag ,AccGetItem ,AccPayItem ,RollBackFlag) VALUES( ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getRefeFlag() == null || this.get(i).getRefeFlag().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRefeFlag());
			}
			if(this.get(i).getAccGetItem() == null || this.get(i).getAccGetItem().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getAccGetItem());
			}
			if(this.get(i).getAccPayItem() == null || this.get(i).getAccPayItem().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccPayItem());
			}
			if(this.get(i).getRollBackFlag() == null || this.get(i).getRollBackFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRollBackFlag());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIAccItem");
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
			tError.moduleName = "LIAccItemDBSet";
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
