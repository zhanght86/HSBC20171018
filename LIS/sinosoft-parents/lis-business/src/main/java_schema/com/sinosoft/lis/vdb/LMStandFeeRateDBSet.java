/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMStandFeeRateSchema;
import com.sinosoft.lis.vschema.LMStandFeeRateSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMStandFeeRateDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LMStandFeeRateDBSet extends LMStandFeeRateSet
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
	public LMStandFeeRateDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMStandFeeRate");
		mflag = true;
	}

	public LMStandFeeRateDBSet()
	{
		db = new DBOper( "LMStandFeeRate" );
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
			tError.moduleName = "LMStandFeeRateDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMStandFeeRate WHERE  RiskCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMStandFeeRate");
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
			tError.moduleName = "LMStandFeeRateDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMStandFeeRate SET  RiskCode = ? , DirSellCommRate = ? , AgencyCommRate = ? , BankCommRate = ? , AgentCommRate = ? , SpecialistCommRate = ? , AgencyChargeRate = ? , BankChargeRate = ? , BusiFeeRate = ? , PreLossRatio = ? , PoolRate = ? , TaxFeeRate = ? , ManageFeeRate = ? WHERE  RiskCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			pstmt.setDouble(2, this.get(i).getDirSellCommRate());
			pstmt.setDouble(3, this.get(i).getAgencyCommRate());
			pstmt.setDouble(4, this.get(i).getBankCommRate());
			pstmt.setDouble(5, this.get(i).getAgentCommRate());
			pstmt.setDouble(6, this.get(i).getSpecialistCommRate());
			pstmt.setDouble(7, this.get(i).getAgencyChargeRate());
			pstmt.setDouble(8, this.get(i).getBankChargeRate());
			pstmt.setDouble(9, this.get(i).getBusiFeeRate());
			pstmt.setDouble(10, this.get(i).getPreLossRatio());
			pstmt.setDouble(11, this.get(i).getPoolRate());
			pstmt.setDouble(12, this.get(i).getTaxFeeRate());
			pstmt.setDouble(13, this.get(i).getManageFeeRate());
			// set where condition
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRiskCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMStandFeeRate");
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
			tError.moduleName = "LMStandFeeRateDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMStandFeeRate VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getRiskCode());
			}
			pstmt.setDouble(2, this.get(i).getDirSellCommRate());
			pstmt.setDouble(3, this.get(i).getAgencyCommRate());
			pstmt.setDouble(4, this.get(i).getBankCommRate());
			pstmt.setDouble(5, this.get(i).getAgentCommRate());
			pstmt.setDouble(6, this.get(i).getSpecialistCommRate());
			pstmt.setDouble(7, this.get(i).getAgencyChargeRate());
			pstmt.setDouble(8, this.get(i).getBankChargeRate());
			pstmt.setDouble(9, this.get(i).getBusiFeeRate());
			pstmt.setDouble(10, this.get(i).getPreLossRatio());
			pstmt.setDouble(11, this.get(i).getPoolRate());
			pstmt.setDouble(12, this.get(i).getTaxFeeRate());
			pstmt.setDouble(13, this.get(i).getManageFeeRate());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMStandFeeRate");
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
			tError.moduleName = "LMStandFeeRateDBSet";
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
