/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LSQuotOfferPlanSchema;
import com.sinosoft.lis.vschema.LSQuotOfferPlanSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LSQuotOfferPlanDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotOfferPlanDBSet extends LSQuotOfferPlanSet
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
	public LSQuotOfferPlanDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LSQuotOfferPlan");
		mflag = true;
	}

	public LSQuotOfferPlanDBSet()
	{
		db = new DBOper( "LSQuotOfferPlan" );
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
			tError.moduleName = "LSQuotOfferPlanDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LSQuotOfferPlan WHERE  OfferListNo = ? AND SysPlanCode = ? AND PlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getOfferListNo() == null || this.get(i).getOfferListNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getOfferListNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotOfferPlan");
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
			tError.moduleName = "LSQuotOfferPlanDBSet";
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
			pstmt = con.prepareStatement("UPDATE LSQuotOfferPlan SET  OfferListNo = ? , SysPlanCode = ? , PlanCode = ? , PlanDesc = ? , PlanType = ? , PlanFlag = ? , PremCalType = ? , InsuPeriod = ? , InsuPeriodFlag = ? , OccupTypeFlag = ? , MinOccupType = ? , MaxOccupType = ? , OccupType = ? , OccupMidType = ? , OccupCode = ? , MinAge = ? , MaxAge = ? , AvgAge = ? , NumPeople = ? , SocialInsuRate = ? , MaleRate = ? , FemaleRate = ? , RetireRate = ? , PremMode = ? , EnterpriseRate = ? , MinSalary = ? , MaxSalary = ? , AvgSalary = ? , OtherDesc = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? , OccupRate = ? , CombiCode = ? , CombiRate = ? , CombiMult = ? WHERE  OfferListNo = ? AND SysPlanCode = ? AND PlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getOfferListNo() == null || this.get(i).getOfferListNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getOfferListNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPlanCode());
			}
			if(this.get(i).getPlanDesc() == null || this.get(i).getPlanDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPlanDesc());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPlanType());
			}
			if(this.get(i).getPlanFlag() == null || this.get(i).getPlanFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPlanFlag());
			}
			if(this.get(i).getPremCalType() == null || this.get(i).getPremCalType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPremCalType());
			}
			pstmt.setInt(8, this.get(i).getInsuPeriod());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getInsuPeriodFlag());
			}
			if(this.get(i).getOccupTypeFlag() == null || this.get(i).getOccupTypeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOccupTypeFlag());
			}
			if(this.get(i).getMinOccupType() == null || this.get(i).getMinOccupType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMinOccupType());
			}
			if(this.get(i).getMaxOccupType() == null || this.get(i).getMaxOccupType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMaxOccupType());
			}
			if(this.get(i).getOccupType() == null || this.get(i).getOccupType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOccupType());
			}
			if(this.get(i).getOccupMidType() == null || this.get(i).getOccupMidType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getOccupMidType());
			}
			if(this.get(i).getOccupCode() == null || this.get(i).getOccupCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOccupCode());
			}
			pstmt.setInt(16, this.get(i).getMinAge());
			pstmt.setInt(17, this.get(i).getMaxAge());
			pstmt.setInt(18, this.get(i).getAvgAge());
			pstmt.setInt(19, this.get(i).getNumPeople());
			pstmt.setDouble(20, this.get(i).getSocialInsuRate());
			pstmt.setDouble(21, this.get(i).getMaleRate());
			pstmt.setDouble(22, this.get(i).getFemaleRate());
			pstmt.setDouble(23, this.get(i).getRetireRate());
			if(this.get(i).getPremMode() == null || this.get(i).getPremMode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPremMode());
			}
			pstmt.setDouble(25, this.get(i).getEnterpriseRate());
			pstmt.setDouble(26, this.get(i).getMinSalary());
			pstmt.setDouble(27, this.get(i).getMaxSalary());
			pstmt.setDouble(28, this.get(i).getAvgSalary());
			if(this.get(i).getOtherDesc() == null || this.get(i).getOtherDesc().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getOtherDesc());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getModifyTime());
			}
			if(this.get(i).getOccupRate() == null || this.get(i).getOccupRate().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOccupRate());
			}
			if(this.get(i).getCombiCode() == null || this.get(i).getCombiCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getCombiCode());
			}
			if(this.get(i).getCombiRate() == null || this.get(i).getCombiRate().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getCombiRate());
			}
			pstmt.setInt(39, this.get(i).getCombiMult());
			// set where condition
			if(this.get(i).getOfferListNo() == null || this.get(i).getOfferListNo().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getOfferListNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotOfferPlan");
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
			tError.moduleName = "LSQuotOfferPlanDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LSQuotOfferPlan VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getOfferListNo() == null || this.get(i).getOfferListNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getOfferListNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPlanCode());
			}
			if(this.get(i).getPlanDesc() == null || this.get(i).getPlanDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPlanDesc());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPlanType());
			}
			if(this.get(i).getPlanFlag() == null || this.get(i).getPlanFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPlanFlag());
			}
			if(this.get(i).getPremCalType() == null || this.get(i).getPremCalType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPremCalType());
			}
			pstmt.setInt(8, this.get(i).getInsuPeriod());
			if(this.get(i).getInsuPeriodFlag() == null || this.get(i).getInsuPeriodFlag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getInsuPeriodFlag());
			}
			if(this.get(i).getOccupTypeFlag() == null || this.get(i).getOccupTypeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOccupTypeFlag());
			}
			if(this.get(i).getMinOccupType() == null || this.get(i).getMinOccupType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMinOccupType());
			}
			if(this.get(i).getMaxOccupType() == null || this.get(i).getMaxOccupType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getMaxOccupType());
			}
			if(this.get(i).getOccupType() == null || this.get(i).getOccupType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOccupType());
			}
			if(this.get(i).getOccupMidType() == null || this.get(i).getOccupMidType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getOccupMidType());
			}
			if(this.get(i).getOccupCode() == null || this.get(i).getOccupCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOccupCode());
			}
			pstmt.setInt(16, this.get(i).getMinAge());
			pstmt.setInt(17, this.get(i).getMaxAge());
			pstmt.setInt(18, this.get(i).getAvgAge());
			pstmt.setInt(19, this.get(i).getNumPeople());
			pstmt.setDouble(20, this.get(i).getSocialInsuRate());
			pstmt.setDouble(21, this.get(i).getMaleRate());
			pstmt.setDouble(22, this.get(i).getFemaleRate());
			pstmt.setDouble(23, this.get(i).getRetireRate());
			if(this.get(i).getPremMode() == null || this.get(i).getPremMode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPremMode());
			}
			pstmt.setDouble(25, this.get(i).getEnterpriseRate());
			pstmt.setDouble(26, this.get(i).getMinSalary());
			pstmt.setDouble(27, this.get(i).getMaxSalary());
			pstmt.setDouble(28, this.get(i).getAvgSalary());
			if(this.get(i).getOtherDesc() == null || this.get(i).getOtherDesc().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getOtherDesc());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getModifyTime());
			}
			if(this.get(i).getOccupRate() == null || this.get(i).getOccupRate().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOccupRate());
			}
			if(this.get(i).getCombiCode() == null || this.get(i).getCombiCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getCombiCode());
			}
			if(this.get(i).getCombiRate() == null || this.get(i).getCombiRate().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getCombiRate());
			}
			pstmt.setInt(39, this.get(i).getCombiMult());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotOfferPlan");
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
			tError.moduleName = "LSQuotOfferPlanDBSet";
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
