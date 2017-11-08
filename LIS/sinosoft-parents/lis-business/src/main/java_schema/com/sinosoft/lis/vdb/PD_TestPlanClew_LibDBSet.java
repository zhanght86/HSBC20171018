/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.vschema.PD_TestPlanClew_LibSet;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: PD_TestPlanClew_LibDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 浜у搧瀹氫箟鏂板姞琛?
 * @CreateDate：2010-10-14
 */
public class PD_TestPlanClew_LibDBSet extends PD_TestPlanClew_LibSet
{
private static Logger logger = Logger.getLogger(PD_TestPlanClew_LibDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public PD_TestPlanClew_LibDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_TestPlanClew_Lib");
		mflag = true;
	}

	public PD_TestPlanClew_LibDBSet()
	{
		db = new DBOper( "PD_TestPlanClew_Lib" );
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
			tError.moduleName = "PD_TestPlanClew_LibDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_TestPlanClew_Lib WHERE  ClewContentCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClewContentCode() == null || this.get(i).getClewContentCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClewContentCode().trim());
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
			tError.moduleName = "PD_TestPlanClew_LibDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_TestPlanClew_Lib");
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
			pstmt = con.prepareStatement("UPDATE PD_TestPlanClew_Lib SET  TESTPLANKIND = ? , TESTPLANRISKKIND = ? , ClewContentCode = ? , ClewContent = ? , Remark = ? , OPERATOR = ? , MAKEDATE = ? , MAKETIME = ? , MODIFYDATE = ? , MODIFYTIME = ? , STANDBYFLAG1 = ? , STANDBYFLAG2 = ? , STANDBYFLAG3 = ? , STANDBYFLAG4 = ? , STANDBYFLAG5 = ? , STANDBYFLAG6 = ? WHERE  ClewContentCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTESTPLANKIND() == null || this.get(i).getTESTPLANKIND().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTESTPLANKIND());
			}
			if(this.get(i).getTESTPLANRISKKIND() == null || this.get(i).getTESTPLANRISKKIND().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTESTPLANRISKKIND());
			}
			if(this.get(i).getClewContentCode() == null || this.get(i).getClewContentCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClewContentCode().trim());
			}
			if(this.get(i).getClewContent() == null || this.get(i).getClewContent().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getClewContent().trim());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRemark().trim());
			}
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOPERATOR().trim());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMAKETIME().trim());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMODIFYTIME().trim());
			}
			if(this.get(i).getSTANDBYFLAG1() == null || this.get(i).getSTANDBYFLAG1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSTANDBYFLAG1().trim());
			}
			if(this.get(i).getSTANDBYFLAG2() == null || this.get(i).getSTANDBYFLAG2().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSTANDBYFLAG2().trim());
			}
			pstmt.setDouble(13, this.get(i).getSTANDBYFLAG3());
			pstmt.setDouble(14, this.get(i).getSTANDBYFLAG4());
			pstmt.setDouble(15, this.get(i).getSTANDBYFLAG5());
			pstmt.setDouble(16, this.get(i).getSTANDBYFLAG6());
			// set where condition
			if(this.get(i).getClewContentCode() == null || this.get(i).getClewContentCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getClewContentCode().trim());
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
			tError.moduleName = "PD_TestPlanClew_LibDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_TestPlanClew_Lib");
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
			pstmt = con.prepareStatement("INSERT INTO PD_TestPlanClew_Lib VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getTESTPLANKIND() == null || this.get(i).getTESTPLANKIND().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getTESTPLANKIND());
			}
			if(this.get(i).getTESTPLANRISKKIND() == null || this.get(i).getTESTPLANRISKKIND().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getTESTPLANRISKKIND());
			}
			if(this.get(i).getClewContentCode() == null || this.get(i).getClewContentCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClewContentCode().trim());
			}
			if(this.get(i).getClewContent() == null || this.get(i).getClewContent().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getClewContent().trim());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRemark().trim());
			}
			if(this.get(i).getOPERATOR() == null || this.get(i).getOPERATOR().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOPERATOR().trim());
			}
			if(this.get(i).getMAKEDATE() == null || this.get(i).getMAKEDATE().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMAKEDATE()));
			}
			if(this.get(i).getMAKETIME() == null || this.get(i).getMAKETIME().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMAKETIME().trim());
			}
			if(this.get(i).getMODIFYDATE() == null || this.get(i).getMODIFYDATE().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMODIFYDATE()));
			}
			if(this.get(i).getMODIFYTIME() == null || this.get(i).getMODIFYTIME().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getMODIFYTIME().trim());
			}
			if(this.get(i).getSTANDBYFLAG1() == null || this.get(i).getSTANDBYFLAG1().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSTANDBYFLAG1().trim());
			}
			if(this.get(i).getSTANDBYFLAG2() == null || this.get(i).getSTANDBYFLAG2().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSTANDBYFLAG2().trim());
			}
			pstmt.setDouble(13, this.get(i).getSTANDBYFLAG3());
			pstmt.setDouble(14, this.get(i).getSTANDBYFLAG4());
			pstmt.setDouble(15, this.get(i).getSTANDBYFLAG5());
			pstmt.setDouble(16, this.get(i).getSTANDBYFLAG6());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "PD_TestPlanClew_LibDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

            int tCount = this.size();
            for (int i = 1; i <= tCount; i++)
            {
		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_TestPlanClew_Lib");
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
