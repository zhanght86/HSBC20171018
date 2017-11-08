/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMDutyGetClmCalSchema;
import com.sinosoft.lis.vschema.LMDutyGetClmCalSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMDutyGetClmCalDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyGetClmCalDBSet extends LMDutyGetClmCalSet
{
private static Logger logger = Logger.getLogger(LMDutyGetClmCalDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMDutyGetClmCalDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMDutyGetClmCal");
		mflag = true;
	}

	public LMDutyGetClmCalDBSet()
	{
		db = new DBOper( "LMDutyGetClmCal" );
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
			tError.moduleName = "LMDutyGetClmCalDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMDutyGetClmCal WHERE  GetDutyCode = ? AND GetDutyKind = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetClmCal");
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
			tError.moduleName = "LMDutyGetClmCalDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMDutyGetClmCal SET  GetDutyCode = ? , GetDutyName = ? , GetDutyKind = ? , CalCode1 = ? , CalCode2 = ? , CalCode3 = ? , CalCode4 = ? , CalCode5 = ? , CalCode6 = ? , CalCode7 = ? , CalCode8 = ? , CalCode9 = ? , CalCode10 = ? WHERE  GetDutyCode = ? AND GetDutyKind = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyName());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getCalCode1() == null || this.get(i).getCalCode1().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalCode1());
			}
			if(this.get(i).getCalCode2() == null || this.get(i).getCalCode2().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalCode2());
			}
			if(this.get(i).getCalCode3() == null || this.get(i).getCalCode3().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode3());
			}
			if(this.get(i).getCalCode4() == null || this.get(i).getCalCode4().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCalCode4());
			}
			if(this.get(i).getCalCode5() == null || this.get(i).getCalCode5().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCalCode5());
			}
			if(this.get(i).getCalCode6() == null || this.get(i).getCalCode6().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCalCode6());
			}
			if(this.get(i).getCalCode7() == null || this.get(i).getCalCode7().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCalCode7());
			}
			if(this.get(i).getCalCode8() == null || this.get(i).getCalCode8().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalCode8());
			}
			if(this.get(i).getCalCode9() == null || this.get(i).getCalCode9().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCalCode9());
			}
			if(this.get(i).getCalCode10() == null || this.get(i).getCalCode10().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalCode10());
			}
			// set where condition
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetClmCal");
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
			tError.moduleName = "LMDutyGetClmCalDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMDutyGetClmCal(GetDutyCode ,GetDutyName ,GetDutyKind ,CalCode1 ,CalCode2 ,CalCode3 ,CalCode4 ,CalCode5 ,CalCode6 ,CalCode7 ,CalCode8 ,CalCode9 ,CalCode10) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyName());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getCalCode1() == null || this.get(i).getCalCode1().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalCode1());
			}
			if(this.get(i).getCalCode2() == null || this.get(i).getCalCode2().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalCode2());
			}
			if(this.get(i).getCalCode3() == null || this.get(i).getCalCode3().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalCode3());
			}
			if(this.get(i).getCalCode4() == null || this.get(i).getCalCode4().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCalCode4());
			}
			if(this.get(i).getCalCode5() == null || this.get(i).getCalCode5().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCalCode5());
			}
			if(this.get(i).getCalCode6() == null || this.get(i).getCalCode6().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCalCode6());
			}
			if(this.get(i).getCalCode7() == null || this.get(i).getCalCode7().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCalCode7());
			}
			if(this.get(i).getCalCode8() == null || this.get(i).getCalCode8().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalCode8());
			}
			if(this.get(i).getCalCode9() == null || this.get(i).getCalCode9().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCalCode9());
			}
			if(this.get(i).getCalCode10() == null || this.get(i).getCalCode10().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalCode10());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetClmCal");
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
			tError.moduleName = "LMDutyGetClmCalDBSet";
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
