/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LICodeMappSchema;
import com.sinosoft.lis.vschema.LICodeMappSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LICodeMappDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LICodeMappDBSet extends LICodeMappSet
{
private static Logger logger = Logger.getLogger(LICodeMappDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LICodeMappDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LICodeMapp");
		mflag = true;
	}

	public LICodeMappDBSet()
	{
		db = new DBOper( "LICodeMapp" );
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
			tError.moduleName = "LICodeMappDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LICodeMapp WHERE  CodeType = ? AND SystemCode = ? AND AccCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCodeType());
			}
			if(this.get(i).getSystemCode() == null || this.get(i).getSystemCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSystemCode());
			}
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LICodeMapp");
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
			tError.moduleName = "LICodeMappDBSet";
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
			pstmt = con.prepareStatement("UPDATE LICodeMapp SET  CodeType = ? , CodeTypename = ? , SystemCode = ? , SCodeName = ? , AccCode = ? , ACodeName2 = ? , memo1 = ? , memo2 = ? WHERE  CodeType = ? AND SystemCode = ? AND AccCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCodeType());
			}
			if(this.get(i).getCodeTypename() == null || this.get(i).getCodeTypename().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCodeTypename());
			}
			if(this.get(i).getSystemCode() == null || this.get(i).getSystemCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSystemCode());
			}
			if(this.get(i).getSCodeName() == null || this.get(i).getSCodeName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSCodeName());
			}
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccCode());
			}
			if(this.get(i).getACodeName2() == null || this.get(i).getACodeName2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getACodeName2());
			}
			if(this.get(i).getmemo1() == null || this.get(i).getmemo1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getmemo1());
			}
			if(this.get(i).getmemo2() == null || this.get(i).getmemo2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getmemo2());
			}
			// set where condition
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCodeType());
			}
			if(this.get(i).getSystemCode() == null || this.get(i).getSystemCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSystemCode());
			}
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAccCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LICodeMapp");
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
			tError.moduleName = "LICodeMappDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LICodeMapp(CodeType ,CodeTypename ,SystemCode ,SCodeName ,AccCode ,ACodeName2 ,memo1 ,memo2) VALUES( ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCodeType() == null || this.get(i).getCodeType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCodeType());
			}
			if(this.get(i).getCodeTypename() == null || this.get(i).getCodeTypename().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCodeTypename());
			}
			if(this.get(i).getSystemCode() == null || this.get(i).getSystemCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSystemCode());
			}
			if(this.get(i).getSCodeName() == null || this.get(i).getSCodeName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSCodeName());
			}
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccCode());
			}
			if(this.get(i).getACodeName2() == null || this.get(i).getACodeName2().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getACodeName2());
			}
			if(this.get(i).getmemo1() == null || this.get(i).getmemo1().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getmemo1());
			}
			if(this.get(i).getmemo2() == null || this.get(i).getmemo2().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getmemo2());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LICodeMapp");
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
			tError.moduleName = "LICodeMappDBSet";
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
