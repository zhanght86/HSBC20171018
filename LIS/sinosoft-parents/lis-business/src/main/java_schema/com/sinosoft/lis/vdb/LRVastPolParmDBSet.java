/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LRVastPolParmSchema;
import com.sinosoft.lis.vschema.LRVastPolParmSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LRVastPolParmDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRVastPolParmDBSet extends LRVastPolParmSet
{
private static Logger logger = Logger.getLogger(LRVastPolParmDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LRVastPolParmDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LRVastPolParm");
		mflag = true;
	}

	public LRVastPolParmDBSet()
	{
		db = new DBOper( "LRVastPolParm" );
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
			tError.moduleName = "LRVastPolParmDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LRVastPolParm WHERE  ReinsurCom = ? AND ContYear = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getReinsurCom() == null || this.get(i).getReinsurCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getReinsurCom());
			}
			pstmt.setInt(2, this.get(i).getContYear());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRVastPolParm");
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
			tError.moduleName = "LRVastPolParmDBSet";
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
			pstmt = con.prepareStatement("UPDATE LRVastPolParm SET  ReinsurCom = ? , ContYear = ? , ReinsurFeeRate1 = ? , ReinsurFeeRate2 = ? , PrePayFee = ? , MinReinsureFee = ? , AmntPerAccident = ? , MaxAmntPerAccident = ? , MinCasePeople = ? , MaxDutyLine = ? WHERE  ReinsurCom = ? AND ContYear = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getReinsurCom() == null || this.get(i).getReinsurCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getReinsurCom());
			}
			pstmt.setInt(2, this.get(i).getContYear());
			pstmt.setDouble(3, this.get(i).getReinsurFeeRate1());
			pstmt.setDouble(4, this.get(i).getReinsurFeeRate2());
			pstmt.setDouble(5, this.get(i).getPrePayFee());
			pstmt.setDouble(6, this.get(i).getMinReinsureFee());
			pstmt.setDouble(7, this.get(i).getAmntPerAccident());
			pstmt.setDouble(8, this.get(i).getMaxAmntPerAccident());
			pstmt.setInt(9, this.get(i).getMinCasePeople());
			pstmt.setDouble(10, this.get(i).getMaxDutyLine());
			// set where condition
			if(this.get(i).getReinsurCom() == null || this.get(i).getReinsurCom().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getReinsurCom());
			}
			pstmt.setInt(12, this.get(i).getContYear());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRVastPolParm");
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
			tError.moduleName = "LRVastPolParmDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LRVastPolParm(ReinsurCom ,ContYear ,ReinsurFeeRate1 ,ReinsurFeeRate2 ,PrePayFee ,MinReinsureFee ,AmntPerAccident ,MaxAmntPerAccident ,MinCasePeople ,MaxDutyLine) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getReinsurCom() == null || this.get(i).getReinsurCom().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getReinsurCom());
			}
			pstmt.setInt(2, this.get(i).getContYear());
			pstmt.setDouble(3, this.get(i).getReinsurFeeRate1());
			pstmt.setDouble(4, this.get(i).getReinsurFeeRate2());
			pstmt.setDouble(5, this.get(i).getPrePayFee());
			pstmt.setDouble(6, this.get(i).getMinReinsureFee());
			pstmt.setDouble(7, this.get(i).getAmntPerAccident());
			pstmt.setDouble(8, this.get(i).getMaxAmntPerAccident());
			pstmt.setInt(9, this.get(i).getMinCasePeople());
			pstmt.setDouble(10, this.get(i).getMaxDutyLine());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LRVastPolParm");
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
			tError.moduleName = "LRVastPolParmDBSet";
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
