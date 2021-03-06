/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LOBContPlanEngineeringSchema;
import com.sinosoft.lis.vschema.LOBContPlanEngineeringSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LOBContPlanEngineeringDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LOBContPlanEngineeringDBSet extends LOBContPlanEngineeringSet
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
	public LOBContPlanEngineeringDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LOBContPlanEngineering");
		mflag = true;
	}

	public LOBContPlanEngineeringDBSet()
	{
		db = new DBOper( "LOBContPlanEngineering" );
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
			tError.moduleName = "LOBContPlanEngineeringDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LOBContPlanEngineering WHERE  BussType = ? AND BussNo = ? AND PolicyNo = ? AND SysPlanCode = ? AND PlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBussNo());
			}
			if(this.get(i).getPolicyNo() == null || this.get(i).getPolicyNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolicyNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBContPlanEngineering");
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
			tError.moduleName = "LOBContPlanEngineeringDBSet";
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
			pstmt = con.prepareStatement("UPDATE LOBContPlanEngineering SET  BussType = ? , BussNo = ? , PolicyNo = ? , PropNo = ? , SysPlanCode = ? , PlanCode = ? , EnginName = ? , EnginType = ? , EnginArea = ? , EnginCost = ? , EnginPlace = ? , EnginStartDate = ? , EnginEndDate = ? , InsurerName = ? , InsurerType = ? , ContractorName = ? , ContractorType = ? , EnginDesc = ? , EnginCondition = ? , Remark = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? , EnginDays = ? WHERE  BussType = ? AND BussNo = ? AND PolicyNo = ? AND SysPlanCode = ? AND PlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBussNo());
			}
			if(this.get(i).getPolicyNo() == null || this.get(i).getPolicyNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolicyNo());
			}
			if(this.get(i).getPropNo() == null || this.get(i).getPropNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPropNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPlanCode());
			}
			if(this.get(i).getEnginName() == null || this.get(i).getEnginName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEnginName());
			}
			if(this.get(i).getEnginType() == null || this.get(i).getEnginType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getEnginType());
			}
			pstmt.setDouble(9, this.get(i).getEnginArea());
			pstmt.setDouble(10, this.get(i).getEnginCost());
			if(this.get(i).getEnginPlace() == null || this.get(i).getEnginPlace().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getEnginPlace());
			}
			if(this.get(i).getEnginStartDate() == null || this.get(i).getEnginStartDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getEnginStartDate()));
			}
			if(this.get(i).getEnginEndDate() == null || this.get(i).getEnginEndDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getEnginEndDate()));
			}
			if(this.get(i).getInsurerName() == null || this.get(i).getInsurerName().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getInsurerName());
			}
			if(this.get(i).getInsurerType() == null || this.get(i).getInsurerType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getInsurerType());
			}
			if(this.get(i).getContractorName() == null || this.get(i).getContractorName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getContractorName());
			}
			if(this.get(i).getContractorType() == null || this.get(i).getContractorType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getContractorType());
			}
			if(this.get(i).getEnginDesc() == null || this.get(i).getEnginDesc().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEnginDesc());
			}
			if(this.get(i).getEnginCondition() == null || this.get(i).getEnginCondition().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getEnginCondition());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRemark());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getModifyTime());
			}
			if(this.get(i).getEnginDays() == null || this.get(i).getEnginDays().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getEnginDays());
			}
			// set where condition
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getBussNo());
			}
			if(this.get(i).getPolicyNo() == null || this.get(i).getPolicyNo().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getPolicyNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBContPlanEngineering");
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
			tError.moduleName = "LOBContPlanEngineeringDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LOBContPlanEngineering VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBussType());
			}
			if(this.get(i).getBussNo() == null || this.get(i).getBussNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBussNo());
			}
			if(this.get(i).getPolicyNo() == null || this.get(i).getPolicyNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolicyNo());
			}
			if(this.get(i).getPropNo() == null || this.get(i).getPropNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPropNo());
			}
			if(this.get(i).getSysPlanCode() == null || this.get(i).getSysPlanCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getSysPlanCode());
			}
			if(this.get(i).getPlanCode() == null || this.get(i).getPlanCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPlanCode());
			}
			if(this.get(i).getEnginName() == null || this.get(i).getEnginName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getEnginName());
			}
			if(this.get(i).getEnginType() == null || this.get(i).getEnginType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getEnginType());
			}
			pstmt.setDouble(9, this.get(i).getEnginArea());
			pstmt.setDouble(10, this.get(i).getEnginCost());
			if(this.get(i).getEnginPlace() == null || this.get(i).getEnginPlace().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getEnginPlace());
			}
			if(this.get(i).getEnginStartDate() == null || this.get(i).getEnginStartDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getEnginStartDate()));
			}
			if(this.get(i).getEnginEndDate() == null || this.get(i).getEnginEndDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getEnginEndDate()));
			}
			if(this.get(i).getInsurerName() == null || this.get(i).getInsurerName().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getInsurerName());
			}
			if(this.get(i).getInsurerType() == null || this.get(i).getInsurerType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getInsurerType());
			}
			if(this.get(i).getContractorName() == null || this.get(i).getContractorName().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getContractorName());
			}
			if(this.get(i).getContractorType() == null || this.get(i).getContractorType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getContractorType());
			}
			if(this.get(i).getEnginDesc() == null || this.get(i).getEnginDesc().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEnginDesc());
			}
			if(this.get(i).getEnginCondition() == null || this.get(i).getEnginCondition().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getEnginCondition());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getRemark());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getModifyTime());
			}
			if(this.get(i).getEnginDays() == null || this.get(i).getEnginDays().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getEnginDays());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LOBContPlanEngineering");
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
			tError.moduleName = "LOBContPlanEngineeringDBSet";
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
