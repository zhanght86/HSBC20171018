/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.FIDataForDealDistilledInfoSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataForDealDistilledInfoDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataForDealDistilledInfoDBSet extends FIDataForDealDistilledInfoSet
{
private static Logger logger = Logger.getLogger(FIDataForDealDistilledInfoDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public FIDataForDealDistilledInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FIDataForDealDistilledInfo");
		mflag = true;
	}

	public FIDataForDealDistilledInfoDBSet()
	{
		db = new DBOper( "FIDataForDealDistilledInfo" );
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
			tError.moduleName = "FIDataForDealDistilledInfoDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FIDataForDealDistilledInfo WHERE  AcquisitionID = ? AND KeyUnionValue = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAcquisitionID() == null || this.get(i).getAcquisitionID().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAcquisitionID().trim());
			}
			if(this.get(i).getKeyUnionValue() == null || this.get(i).getKeyUnionValue().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getKeyUnionValue().trim());
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
			tError.moduleName = "FIDataForDealDistilledInfoDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataForDealDistilledInfo");
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
			pstmt = con.prepareStatement("UPDATE FIDataForDealDistilledInfo SET  BatchNo = ? , AcquisitionID = ? , KeyUnionValue = ? , SerialNo = ? , DataType = ? , BusinessNo = ? , BussDate = ? , MakeDate = ? , MakeTime = ? WHERE  AcquisitionID = ? AND KeyUnionValue = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getAcquisitionID() == null || this.get(i).getAcquisitionID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAcquisitionID().trim());
			}
			if(this.get(i).getKeyUnionValue() == null || this.get(i).getKeyUnionValue().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getKeyUnionValue().trim());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSerialNo().trim());
			}
			if(this.get(i).getDataType() == null || this.get(i).getDataType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDataType().trim());
			}
			if(this.get(i).getBusinessNo() == null || this.get(i).getBusinessNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBusinessNo().trim());
			}
			if(this.get(i).getBussDate() == null || this.get(i).getBussDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getBussDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMakeTime().trim());
			}
			// set where condition
			if(this.get(i).getAcquisitionID() == null || this.get(i).getAcquisitionID().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAcquisitionID().trim());
			}
			if(this.get(i).getKeyUnionValue() == null || this.get(i).getKeyUnionValue().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getKeyUnionValue().trim());
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
			tError.moduleName = "FIDataForDealDistilledInfoDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataForDealDistilledInfo");
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
			pstmt = con.prepareStatement("INSERT INTO FIDataForDealDistilledInfo VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBatchNo() == null || this.get(i).getBatchNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBatchNo().trim());
			}
			if(this.get(i).getAcquisitionID() == null || this.get(i).getAcquisitionID().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAcquisitionID().trim());
			}
			if(this.get(i).getKeyUnionValue() == null || this.get(i).getKeyUnionValue().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getKeyUnionValue().trim());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSerialNo().trim());
			}
			if(this.get(i).getDataType() == null || this.get(i).getDataType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDataType().trim());
			}
			if(this.get(i).getBusinessNo() == null || this.get(i).getBusinessNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBusinessNo().trim());
			}
			if(this.get(i).getBussDate() == null || this.get(i).getBussDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getBussDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getMakeTime().trim());
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
			tError.moduleName = "FIDataForDealDistilledInfoDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("FIDataForDealDistilledInfo");
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
