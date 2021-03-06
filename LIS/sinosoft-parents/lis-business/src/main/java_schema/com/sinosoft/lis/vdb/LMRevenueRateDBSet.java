/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LMRevenueRateSchema;
import com.sinosoft.lis.vschema.LMRevenueRateSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMRevenueRateDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRevenueRateDBSet extends LMRevenueRateSet
{
private static Logger logger = Logger.getLogger(LMRevenueRateDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LMRevenueRateDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMRevenueRate");
		mflag = true;
	}

	public LMRevenueRateDBSet()
	{
		db = new DBOper( "LMRevenueRate" );
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
			tError.moduleName = "LMRevenueRateDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMRevenueRate WHERE  RiskCode = ? AND RateStartDate = ? AND RateType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRateStartDate() == null || this.get(i).getRateStartDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getRateStartDate()));
			}
			if(this.get(i).getRateType() == null || this.get(i).getRateType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRateType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRevenueRate");
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
			tError.moduleName = "LMRevenueRateDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMRevenueRate SET  RiskCode = ? , RateStartDate = ? , RateEndDate = ? , RateType = ? , Rate = ? , Operator = ? , MakeDate = ? , MakeTime = ? WHERE  RiskCode = ? AND RateStartDate = ? AND RateType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRateStartDate() == null || this.get(i).getRateStartDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getRateStartDate()));
			}
			if(this.get(i).getRateEndDate() == null || this.get(i).getRateEndDate().equals("null")) {
				pstmt.setDate(3,null);
			} else {
				pstmt.setDate(3, Date.valueOf(this.get(i).getRateEndDate()));
			}
			if(this.get(i).getRateType() == null || this.get(i).getRateType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRateType());
			}
			pstmt.setDouble(5, this.get(i).getRate());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMakeTime());
			}
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskCode());
			}
			if(this.get(i).getRateStartDate() == null || this.get(i).getRateStartDate().equals("null")) {
				pstmt.setDate(10,null);
			} else {
				pstmt.setDate(10, Date.valueOf(this.get(i).getRateStartDate()));
			}
			if(this.get(i).getRateType() == null || this.get(i).getRateType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getRateType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRevenueRate");
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
			tError.moduleName = "LMRevenueRateDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMRevenueRate(RiskCode ,RateStartDate ,RateEndDate ,RateType ,Rate ,Operator ,MakeDate ,MakeTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			if(this.get(i).getRateStartDate() == null || this.get(i).getRateStartDate().equals("null")) {
				pstmt.setDate(2,null);
			} else {
				pstmt.setDate(2, Date.valueOf(this.get(i).getRateStartDate()));
			}
			if(this.get(i).getRateEndDate() == null || this.get(i).getRateEndDate().equals("null")) {
				pstmt.setDate(3,null);
			} else {
				pstmt.setDate(3, Date.valueOf(this.get(i).getRateEndDate()));
			}
			if(this.get(i).getRateType() == null || this.get(i).getRateType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRateType());
			}
			pstmt.setDouble(5, this.get(i).getRate());
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getMakeTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMRevenueRate");
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
			tError.moduleName = "LMRevenueRateDBSet";
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
