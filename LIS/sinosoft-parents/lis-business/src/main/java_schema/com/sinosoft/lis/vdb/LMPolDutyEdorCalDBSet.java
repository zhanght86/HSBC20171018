/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMPolDutyEdorCalSchema;
import com.sinosoft.lis.vschema.LMPolDutyEdorCalSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMPolDutyEdorCalDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMPolDutyEdorCalDBSet extends LMPolDutyEdorCalSet
{
private static Logger logger = Logger.getLogger(LMPolDutyEdorCalDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMPolDutyEdorCalDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMPolDutyEdorCal");
		mflag = true;
	}

	public LMPolDutyEdorCalDBSet()
	{
		db = new DBOper( "LMPolDutyEdorCal" );
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
			tError.moduleName = "LMPolDutyEdorCalDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMPolDutyEdorCal WHERE  RiskCode = ? AND DutyCode = ? AND EdorType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMPolDutyEdorCal");
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
			tError.moduleName = "LMPolDutyEdorCalDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMPolDutyEdorCal SET  RiskCode = ? , DutyCode = ? , EdorType = ? , CalMode = ? , ChgPremCalCode = ? , InterestCalCode = ? WHERE  RiskCode = ? AND DutyCode = ? AND EdorType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}
			if(this.get(i).getCalMode() == null || this.get(i).getCalMode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalMode());
			}
			if(this.get(i).getChgPremCalCode() == null || this.get(i).getChgPremCalCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getChgPremCalCode());
			}
			if(this.get(i).getInterestCalCode() == null || this.get(i).getInterestCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInterestCalCode());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getDutyCode());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getEdorType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMPolDutyEdorCal");
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
			tError.moduleName = "LMPolDutyEdorCalDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMPolDutyEdorCal(RiskCode ,DutyCode ,EdorType ,CalMode ,ChgPremCalCode ,InterestCalCode) VALUES( ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getDutyCode());
			}
			if(this.get(i).getEdorType() == null || this.get(i).getEdorType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getEdorType());
			}
			if(this.get(i).getCalMode() == null || this.get(i).getCalMode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalMode());
			}
			if(this.get(i).getChgPremCalCode() == null || this.get(i).getChgPremCalCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getChgPremCalCode());
			}
			if(this.get(i).getInterestCalCode() == null || this.get(i).getInterestCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInterestCalCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMPolDutyEdorCal");
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
			tError.moduleName = "LMPolDutyEdorCalDBSet";
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
