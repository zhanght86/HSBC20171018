/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMCertifySubSchema;
import com.sinosoft.lis.vschema.LMCertifySubSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMCertifySubDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LMCertifySubDBSet extends LMCertifySubSet
{
private static Logger logger = Logger.getLogger(LMCertifySubDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMCertifySubDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMCertifySub");
		mflag = true;
	}

	public LMCertifySubDBSet()
	{
		db = new DBOper( "LMCertifySub" );
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
			tError.moduleName = "LMCertifySubDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMCertifySub WHERE  CertifyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMCertifySub");
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
			tError.moduleName = "LMCertifySubDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMCertifySub SET  CertifyCode = ? , CertifyClass = ? , DBServer = ? , DBName = ? , TableName = ? , FieldName = ? , BeforeSQL = ? , AfterSQL = ? , ErrorMessage = ? , AutoTakeBack = ? , InterfaceClass = ? , AutoSend = ? WHERE  CertifyCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getCertifyClass() == null || this.get(i).getCertifyClass().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertifyClass());
			}
			if(this.get(i).getDBServer() == null || this.get(i).getDBServer().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDBServer());
			}
			if(this.get(i).getDBName() == null || this.get(i).getDBName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDBName());
			}
			if(this.get(i).getTableName() == null || this.get(i).getTableName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getTableName());
			}
			if(this.get(i).getFieldName() == null || this.get(i).getFieldName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFieldName());
			}
			if(this.get(i).getBeforeSQL() == null || this.get(i).getBeforeSQL().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBeforeSQL());
			}
			if(this.get(i).getAfterSQL() == null || this.get(i).getAfterSQL().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAfterSQL());
			}
			if(this.get(i).getErrorMessage() == null || this.get(i).getErrorMessage().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getErrorMessage());
			}
			if(this.get(i).getAutoTakeBack() == null || this.get(i).getAutoTakeBack().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAutoTakeBack());
			}
			if(this.get(i).getInterfaceClass() == null || this.get(i).getInterfaceClass().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInterfaceClass());
			}
			if(this.get(i).getAutoSend() == null || this.get(i).getAutoSend().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAutoSend());
			}
			// set where condition
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCertifyCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMCertifySub");
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
			tError.moduleName = "LMCertifySubDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMCertifySub(CertifyCode ,CertifyClass ,DBServer ,DBName ,TableName ,FieldName ,BeforeSQL ,AfterSQL ,ErrorMessage ,AutoTakeBack ,InterfaceClass ,AutoSend) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getCertifyClass() == null || this.get(i).getCertifyClass().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertifyClass());
			}
			if(this.get(i).getDBServer() == null || this.get(i).getDBServer().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDBServer());
			}
			if(this.get(i).getDBName() == null || this.get(i).getDBName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDBName());
			}
			if(this.get(i).getTableName() == null || this.get(i).getTableName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getTableName());
			}
			if(this.get(i).getFieldName() == null || this.get(i).getFieldName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFieldName());
			}
			if(this.get(i).getBeforeSQL() == null || this.get(i).getBeforeSQL().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBeforeSQL());
			}
			if(this.get(i).getAfterSQL() == null || this.get(i).getAfterSQL().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAfterSQL());
			}
			if(this.get(i).getErrorMessage() == null || this.get(i).getErrorMessage().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getErrorMessage());
			}
			if(this.get(i).getAutoTakeBack() == null || this.get(i).getAutoTakeBack().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAutoTakeBack());
			}
			if(this.get(i).getInterfaceClass() == null || this.get(i).getInterfaceClass().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInterfaceClass());
			}
			if(this.get(i).getAutoSend() == null || this.get(i).getAutoSend().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAutoSend());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMCertifySub");
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
			tError.moduleName = "LMCertifySubDBSet";
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
