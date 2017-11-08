/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCPersonAgeDisItemSchema;
import com.sinosoft.lis.vschema.LCPersonAgeDisItemSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCPersonAgeDisItemDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCPersonAgeDisItemDBSet extends LCPersonAgeDisItemSet
{
private static Logger logger = Logger.getLogger(LCPersonAgeDisItemDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCPersonAgeDisItemDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCPersonAgeDisItem");
		mflag = true;
	}

	public LCPersonAgeDisItemDBSet()
	{
		db = new DBOper( "LCPersonAgeDisItem" );
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
			tError.moduleName = "LCPersonAgeDisItemDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCPersonAgeDisItem WHERE  SerialNo = ? AND StartAge = ? AND EndAge = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			pstmt.setInt(2, this.get(i).getStartAge());
			pstmt.setInt(3, this.get(i).getEndAge());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPersonAgeDisItem");
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
			tError.moduleName = "LCPersonAgeDisItemDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCPersonAgeDisItem SET  SerialNo = ? , GrpContNo = ? , PrtNo = ? , StartAge = ? , EndAge = ? , MaleCount = ? , FemalCount = ? , OnWorkMCount = ? , OnWorkFCount = ? , OffWorkMCount = ? , OffWorkFCount = ? , MateMCount = ? , MateFCount = ? , YoungMCount = ? , YoungFCount = ? , OtherMCount = ? , OtherFCount = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  SerialNo = ? AND StartAge = ? AND EndAge = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPrtNo());
			}
			pstmt.setInt(4, this.get(i).getStartAge());
			pstmt.setInt(5, this.get(i).getEndAge());
			pstmt.setInt(6, this.get(i).getMaleCount());
			pstmt.setInt(7, this.get(i).getFemalCount());
			pstmt.setInt(8, this.get(i).getOnWorkMCount());
			pstmt.setInt(9, this.get(i).getOnWorkFCount());
			pstmt.setInt(10, this.get(i).getOffWorkMCount());
			pstmt.setInt(11, this.get(i).getOffWorkFCount());
			pstmt.setInt(12, this.get(i).getMateMCount());
			pstmt.setInt(13, this.get(i).getMateFCount());
			pstmt.setInt(14, this.get(i).getYoungMCount());
			pstmt.setInt(15, this.get(i).getYoungFCount());
			pstmt.setInt(16, this.get(i).getOtherMCount());
			pstmt.setInt(17, this.get(i).getOtherFCount());
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getSerialNo());
			}
			pstmt.setInt(23, this.get(i).getStartAge());
			pstmt.setInt(24, this.get(i).getEndAge());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPersonAgeDisItem");
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
			tError.moduleName = "LCPersonAgeDisItemDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCPersonAgeDisItem(SerialNo ,GrpContNo ,PrtNo ,StartAge ,EndAge ,MaleCount ,FemalCount ,OnWorkMCount ,OnWorkFCount ,OffWorkMCount ,OffWorkFCount ,MateMCount ,MateFCount ,YoungMCount ,YoungFCount ,OtherMCount ,OtherFCount ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPrtNo());
			}
			pstmt.setInt(4, this.get(i).getStartAge());
			pstmt.setInt(5, this.get(i).getEndAge());
			pstmt.setInt(6, this.get(i).getMaleCount());
			pstmt.setInt(7, this.get(i).getFemalCount());
			pstmt.setInt(8, this.get(i).getOnWorkMCount());
			pstmt.setInt(9, this.get(i).getOnWorkFCount());
			pstmt.setInt(10, this.get(i).getOffWorkMCount());
			pstmt.setInt(11, this.get(i).getOffWorkFCount());
			pstmt.setInt(12, this.get(i).getMateMCount());
			pstmt.setInt(13, this.get(i).getMateFCount());
			pstmt.setInt(14, this.get(i).getYoungMCount());
			pstmt.setInt(15, this.get(i).getYoungFCount());
			pstmt.setInt(16, this.get(i).getOtherMCount());
			pstmt.setInt(17, this.get(i).getOtherFCount());
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCPersonAgeDisItem");
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
			tError.moduleName = "LCPersonAgeDisItemDBSet";
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
