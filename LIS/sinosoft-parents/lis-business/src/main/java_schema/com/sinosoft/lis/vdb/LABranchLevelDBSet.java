/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LABranchLevelSchema;
import com.sinosoft.lis.vschema.LABranchLevelSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LABranchLevelDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LABranchLevelDBSet extends LABranchLevelSet
{
private static Logger logger = Logger.getLogger(LABranchLevelDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LABranchLevelDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LABranchLevel");
		mflag = true;
	}

	public LABranchLevelDBSet()
	{
		db = new DBOper( "LABranchLevel" );
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
			tError.moduleName = "LABranchLevelDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LABranchLevel WHERE  BranchLevelCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBranchLevelCode() == null || this.get(i).getBranchLevelCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBranchLevelCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LABranchLevel");
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
			tError.moduleName = "LABranchLevelDBSet";
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
			pstmt = con.prepareStatement("UPDATE LABranchLevel SET  BranchLevelCode = ? , BranchLevelName = ? , BranchLevelType = ? , BranchLevelID = ? , BranchType = ? , BranchProperty = ? , AgentKind = ? , SubjectProperty = ? , BranchLevelProperty1 = ? , BranchLevelProperty2 = ? , BranchLevelProperty3 = ? , BranchLevelProperty4 = ? , BranchLevelProperty5 = ? , Noti = ? WHERE  BranchLevelCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBranchLevelCode() == null || this.get(i).getBranchLevelCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBranchLevelCode());
			}
			if(this.get(i).getBranchLevelName() == null || this.get(i).getBranchLevelName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBranchLevelName());
			}
			if(this.get(i).getBranchLevelType() == null || this.get(i).getBranchLevelType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBranchLevelType());
			}
			pstmt.setInt(4, this.get(i).getBranchLevelID());
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBranchType());
			}
			if(this.get(i).getBranchProperty() == null || this.get(i).getBranchProperty().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBranchProperty());
			}
			if(this.get(i).getAgentKind() == null || this.get(i).getAgentKind().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentKind());
			}
			if(this.get(i).getSubjectProperty() == null || this.get(i).getSubjectProperty().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSubjectProperty());
			}
			if(this.get(i).getBranchLevelProperty1() == null || this.get(i).getBranchLevelProperty1().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBranchLevelProperty1());
			}
			if(this.get(i).getBranchLevelProperty2() == null || this.get(i).getBranchLevelProperty2().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBranchLevelProperty2());
			}
			if(this.get(i).getBranchLevelProperty3() == null || this.get(i).getBranchLevelProperty3().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBranchLevelProperty3());
			}
			if(this.get(i).getBranchLevelProperty4() == null || this.get(i).getBranchLevelProperty4().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBranchLevelProperty4());
			}
			if(this.get(i).getBranchLevelProperty5() == null || this.get(i).getBranchLevelProperty5().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getBranchLevelProperty5());
			}
			if(this.get(i).getNoti() == null || this.get(i).getNoti().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getNoti());
			}
			// set where condition
			if(this.get(i).getBranchLevelCode() == null || this.get(i).getBranchLevelCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBranchLevelCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LABranchLevel");
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
			tError.moduleName = "LABranchLevelDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LABranchLevel(BranchLevelCode ,BranchLevelName ,BranchLevelType ,BranchLevelID ,BranchType ,BranchProperty ,AgentKind ,SubjectProperty ,BranchLevelProperty1 ,BranchLevelProperty2 ,BranchLevelProperty3 ,BranchLevelProperty4 ,BranchLevelProperty5 ,Noti) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBranchLevelCode() == null || this.get(i).getBranchLevelCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBranchLevelCode());
			}
			if(this.get(i).getBranchLevelName() == null || this.get(i).getBranchLevelName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBranchLevelName());
			}
			if(this.get(i).getBranchLevelType() == null || this.get(i).getBranchLevelType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBranchLevelType());
			}
			pstmt.setInt(4, this.get(i).getBranchLevelID());
			if(this.get(i).getBranchType() == null || this.get(i).getBranchType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBranchType());
			}
			if(this.get(i).getBranchProperty() == null || this.get(i).getBranchProperty().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBranchProperty());
			}
			if(this.get(i).getAgentKind() == null || this.get(i).getAgentKind().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAgentKind());
			}
			if(this.get(i).getSubjectProperty() == null || this.get(i).getSubjectProperty().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSubjectProperty());
			}
			if(this.get(i).getBranchLevelProperty1() == null || this.get(i).getBranchLevelProperty1().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBranchLevelProperty1());
			}
			if(this.get(i).getBranchLevelProperty2() == null || this.get(i).getBranchLevelProperty2().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getBranchLevelProperty2());
			}
			if(this.get(i).getBranchLevelProperty3() == null || this.get(i).getBranchLevelProperty3().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getBranchLevelProperty3());
			}
			if(this.get(i).getBranchLevelProperty4() == null || this.get(i).getBranchLevelProperty4().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getBranchLevelProperty4());
			}
			if(this.get(i).getBranchLevelProperty5() == null || this.get(i).getBranchLevelProperty5().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getBranchLevelProperty5());
			}
			if(this.get(i).getNoti() == null || this.get(i).getNoti().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getNoti());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LABranchLevel");
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
			tError.moduleName = "LABranchLevelDBSet";
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
