/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FICertificateTypeDefSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FICertificateTypeDefDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FICertificateTypeDefDBSet extends FICertificateTypeDefSet
{
private static Logger logger = Logger.getLogger(FICertificateTypeDefDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FICertificateTypeDefDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FICertificateTypeDef");
		mflag = true;
	}

	public FICertificateTypeDefDBSet()
	{
		db = new DBOper( "FICertificateTypeDef" );
		con = null;
		mflag = false;
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
			tError.moduleName = "FICertificateTypeDefDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FICertificateTypeDef WHERE  VersionNo = ? AND CertificateID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getVersionNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertificateID().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FICertificateTypeDef");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();
            }

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
			pstmt = con.prepareStatement("UPDATE FICertificateTypeDef SET  VersionNo = ? , CertificateID = ? , CertificateName = ? , DetailIndexID = ? , DetailIndexName = ? , AcquisitionType = ? , Remark = ? WHERE  VersionNo = ? AND CertificateID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getVersionNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getCertificateName() == null || this.get(i).getCertificateName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCertificateName().trim());
			}
			if(this.get(i).getDetailIndexID() == null || this.get(i).getDetailIndexID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDetailIndexID().trim());
			}
			if(this.get(i).getDetailIndexName() == null || this.get(i).getDetailIndexName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDetailIndexName().trim());
			}
			if(this.get(i).getAcquisitionType() == null || this.get(i).getAcquisitionType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAcquisitionType().trim());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRemark().trim());
			}
			// set where condition
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getVersionNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCertificateID().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FICertificateTypeDef");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();
            }

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
			pstmt = con.prepareStatement("INSERT INTO FICertificateTypeDef VALUES( ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getVersionNo() == null || this.get(i).getVersionNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getVersionNo().trim());
			}
			if(this.get(i).getCertificateID() == null || this.get(i).getCertificateID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCertificateID().trim());
			}
			if(this.get(i).getCertificateName() == null || this.get(i).getCertificateName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCertificateName().trim());
			}
			if(this.get(i).getDetailIndexID() == null || this.get(i).getDetailIndexID().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getDetailIndexID().trim());
			}
			if(this.get(i).getDetailIndexName() == null || this.get(i).getDetailIndexName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDetailIndexName().trim());
			}
			if(this.get(i).getAcquisitionType() == null || this.get(i).getAcquisitionType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAcquisitionType().trim());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRemark().trim());
			}
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FICertificateTypeDef");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();
            }

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
