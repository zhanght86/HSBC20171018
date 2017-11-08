/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLMCaseSpotSchema;
import com.sinosoft.lis.vschema.LLMCaseSpotSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLMCaseSpotDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLMCaseSpotDBSet extends LLMCaseSpotSet
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
	public LLMCaseSpotDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLMCaseSpot");
		mflag = true;
	}

	public LLMCaseSpotDBSet()
	{
		db = new DBOper( "LLMCaseSpot" );
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
			tError.moduleName = "LLMCaseSpotDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLMCaseSpot WHERE  SpotMngCom = ? AND SpotedMngCom = ? AND SpotedPopedom = ? AND SpotOper = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSpotMngCom() == null || this.get(i).getSpotMngCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSpotMngCom());
			}
			if(this.get(i).getSpotedMngCom() == null || this.get(i).getSpotedMngCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSpotedMngCom());
			}
			if(this.get(i).getSpotedPopedom() == null || this.get(i).getSpotedPopedom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSpotedPopedom());
			}
			if(this.get(i).getSpotOper() == null || this.get(i).getSpotOper().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSpotOper());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMCaseSpot");
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
			tError.moduleName = "LLMCaseSpotDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLMCaseSpot SET  SpotMngCom = ? , Spoter = ? , SpotedMngCom = ? , SpotedPopedom = ? , SpotOper = ? , SpotFreq = ? , SpotRate = ? WHERE  SpotMngCom = ? AND SpotedMngCom = ? AND SpotedPopedom = ? AND SpotOper = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSpotMngCom() == null || this.get(i).getSpotMngCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSpotMngCom());
			}
			if(this.get(i).getSpoter() == null || this.get(i).getSpoter().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSpoter());
			}
			if(this.get(i).getSpotedMngCom() == null || this.get(i).getSpotedMngCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSpotedMngCom());
			}
			if(this.get(i).getSpotedPopedom() == null || this.get(i).getSpotedPopedom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSpotedPopedom());
			}
			if(this.get(i).getSpotOper() == null || this.get(i).getSpotOper().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSpotOper());
			}
			if(this.get(i).getSpotFreq() == null || this.get(i).getSpotFreq().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getSpotFreq());
			}
			pstmt.setDouble(7, this.get(i).getSpotRate());
			// set where condition
			if(this.get(i).getSpotMngCom() == null || this.get(i).getSpotMngCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getSpotMngCom());
			}
			if(this.get(i).getSpotedMngCom() == null || this.get(i).getSpotedMngCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSpotedMngCom());
			}
			if(this.get(i).getSpotedPopedom() == null || this.get(i).getSpotedPopedom().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSpotedPopedom());
			}
			if(this.get(i).getSpotOper() == null || this.get(i).getSpotOper().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSpotOper());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMCaseSpot");
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
			tError.moduleName = "LLMCaseSpotDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLMCaseSpot VALUES( ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSpotMngCom() == null || this.get(i).getSpotMngCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSpotMngCom());
			}
			if(this.get(i).getSpoter() == null || this.get(i).getSpoter().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSpoter());
			}
			if(this.get(i).getSpotedMngCom() == null || this.get(i).getSpotedMngCom().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getSpotedMngCom());
			}
			if(this.get(i).getSpotedPopedom() == null || this.get(i).getSpotedPopedom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSpotedPopedom());
			}
			if(this.get(i).getSpotOper() == null || this.get(i).getSpotOper().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSpotOper());
			}
			if(this.get(i).getSpotFreq() == null || this.get(i).getSpotFreq().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getSpotFreq());
			}
			pstmt.setDouble(7, this.get(i).getSpotRate());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMCaseSpot");
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
			tError.moduleName = "LLMCaseSpotDBSet";
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
