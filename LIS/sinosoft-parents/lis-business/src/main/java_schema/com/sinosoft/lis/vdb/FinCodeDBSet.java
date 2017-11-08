/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.FinCodeSchema;
import com.sinosoft.lis.vschema.FinCodeSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: FinCodeDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class FinCodeDBSet extends FinCodeSet
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
	public FinCodeDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FinCode");
		mflag = true;
	}

	public FinCodeDBSet()
	{
		db = new DBOper( "FinCode" );
		con = db.getConnection();
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
			tError.moduleName = "FinCodeDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM FinCode WHERE  CodeType = ? AND Code1 = ? AND Code2 = ? AND Code3 = ? AND Code4 = ? AND Code5 = ? AND Code6 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCodeType());
			}
			if(this.get(i).getCode1() == null || this.get(i).getCode1().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCode1());
			}
			if(this.get(i).getCode2() == null || this.get(i).getCode2().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCode2());
			}
			if(this.get(i).getCode3() == null || this.get(i).getCode3().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCode3());
			}
			if(this.get(i).getCode4() == null || this.get(i).getCode4().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCode4());
			}
			if(this.get(i).getCode5() == null || this.get(i).getCode5().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCode5());
			}
			if(this.get(i).getCode6() == null || this.get(i).getCode6().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCode6());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("FinCode");
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
			tError.moduleName = "FinCodeDBSet";
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
			pstmt = con.prepareStatement("UPDATE FinCode SET  CodeType = ? , Code1 = ? , CodeName1 = ? , Code2 = ? , CodeName2 = ? , Code3 = ? , CodeName3 = ? , Code4 = ? , CodeName4 = ? , Code5 = ? , CodeName5 = ? , Code6 = ? , CodeName6 = ? WHERE  CodeType = ? AND Code1 = ? AND Code2 = ? AND Code3 = ? AND Code4 = ? AND Code5 = ? AND Code6 = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCodeType());
			}
			if(this.get(i).getCode1() == null || this.get(i).getCode1().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCode1());
			}
			if(this.get(i).getCodeName1() == null || this.get(i).getCodeName1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCodeName1());
			}
			if(this.get(i).getCode2() == null || this.get(i).getCode2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCode2());
			}
			if(this.get(i).getCodeName2() == null || this.get(i).getCodeName2().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCodeName2());
			}
			if(this.get(i).getCode3() == null || this.get(i).getCode3().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCode3());
			}
			if(this.get(i).getCodeName3() == null || this.get(i).getCodeName3().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCodeName3());
			}
			if(this.get(i).getCode4() == null || this.get(i).getCode4().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCode4());
			}
			if(this.get(i).getCodeName4() == null || this.get(i).getCodeName4().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCodeName4());
			}
			if(this.get(i).getCode5() == null || this.get(i).getCode5().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCode5());
			}
			if(this.get(i).getCodeName5() == null || this.get(i).getCodeName5().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCodeName5());
			}
			if(this.get(i).getCode6() == null || this.get(i).getCode6().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCode6());
			}
			if(this.get(i).getCodeName6() == null || this.get(i).getCodeName6().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCodeName6());
			}
			// set where condition
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCodeType());
			}
			if(this.get(i).getCode1() == null || this.get(i).getCode1().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCode1());
			}
			if(this.get(i).getCode2() == null || this.get(i).getCode2().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCode2());
			}
			if(this.get(i).getCode3() == null || this.get(i).getCode3().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCode3());
			}
			if(this.get(i).getCode4() == null || this.get(i).getCode4().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCode4());
			}
			if(this.get(i).getCode5() == null || this.get(i).getCode5().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getCode5());
			}
			if(this.get(i).getCode6() == null || this.get(i).getCode6().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getCode6());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("FinCode");
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
			tError.moduleName = "FinCodeDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO FinCode VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCodeType());
			}
			if(this.get(i).getCode1() == null || this.get(i).getCode1().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCode1());
			}
			if(this.get(i).getCodeName1() == null || this.get(i).getCodeName1().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCodeName1());
			}
			if(this.get(i).getCode2() == null || this.get(i).getCode2().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCode2());
			}
			if(this.get(i).getCodeName2() == null || this.get(i).getCodeName2().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCodeName2());
			}
			if(this.get(i).getCode3() == null || this.get(i).getCode3().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCode3());
			}
			if(this.get(i).getCodeName3() == null || this.get(i).getCodeName3().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCodeName3());
			}
			if(this.get(i).getCode4() == null || this.get(i).getCode4().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCode4());
			}
			if(this.get(i).getCodeName4() == null || this.get(i).getCodeName4().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCodeName4());
			}
			if(this.get(i).getCode5() == null || this.get(i).getCode5().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCode5());
			}
			if(this.get(i).getCodeName5() == null || this.get(i).getCodeName5().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCodeName5());
			}
			if(this.get(i).getCode6() == null || this.get(i).getCode6().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCode6());
			}
			if(this.get(i).getCodeName6() == null || this.get(i).getCodeName6().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCodeName6());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("FinCode");
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
			tError.moduleName = "FinCodeDBSet";
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
