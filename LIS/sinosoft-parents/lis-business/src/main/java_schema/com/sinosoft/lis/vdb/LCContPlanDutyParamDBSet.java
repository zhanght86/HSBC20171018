/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCContPlanDutyParamSchema;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCContPlanDutyParamDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCContPlanDutyParamDBSet extends LCContPlanDutyParamSet
{
private static Logger logger = Logger.getLogger(LCContPlanDutyParamDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCContPlanDutyParamDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCContPlanDutyParam");
		mflag = true;
	}

	public LCContPlanDutyParamDBSet()
	{
		db = new DBOper( "LCContPlanDutyParam" );
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
			tError.moduleName = "LCContPlanDutyParamDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCContPlanDutyParam WHERE  GrpContNo = ? AND MainRiskCode = ? AND RiskCode = ? AND ContPlanCode = ? AND DutyCode = ? AND CalFactor = ? AND PlanType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getMainRiskCode() == null || this.get(i).getMainRiskCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMainRiskCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getContPlanCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDutyCode());
			}
			if(this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCalFactor());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, StrTool.space1(this.get(i).getPlanType(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCContPlanDutyParam");
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
			tError.moduleName = "LCContPlanDutyParamDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCContPlanDutyParam SET  GrpContNo = ? , ProposalGrpContNo = ? , GrpPolNo = ? , MainRiskCode = ? , MainRiskVersion = ? , RiskCode = ? , RiskVersion = ? , ContPlanCode = ? , ContPlanName = ? , DutyCode = ? , CalFactor = ? , CalFactorType = ? , CalFactorValue = ? , Remark = ? , PlanType = ? , SysContPlanCode = ? WHERE  GrpContNo = ? AND MainRiskCode = ? AND RiskCode = ? AND ContPlanCode = ? AND DutyCode = ? AND CalFactor = ? AND PlanType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getMainRiskCode() == null || this.get(i).getMainRiskCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainRiskCode());
			}
			if(this.get(i).getMainRiskVersion() == null || this.get(i).getMainRiskVersion().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getMainRiskVersion());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskVersion());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getContPlanCode());
			}
			if(this.get(i).getContPlanName() == null || this.get(i).getContPlanName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getContPlanName());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDutyCode());
			}
			if(this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalFactor());
			}
			if(this.get(i).getCalFactorType() == null || this.get(i).getCalFactorType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCalFactorType());
			}
			if(this.get(i).getCalFactorValue() == null || this.get(i).getCalFactorValue().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalFactorValue());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRemark());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPlanType());
			}
			if(this.get(i).getSysContPlanCode() == null || this.get(i).getSysContPlanCode().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSysContPlanCode());
			}
			// set where condition
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGrpContNo());
			}
			if(this.get(i).getMainRiskCode() == null || this.get(i).getMainRiskCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMainRiskCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskCode());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getContPlanCode());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getDutyCode());
			}
			if(this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getCalFactor());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, StrTool.space1(this.get(i).getPlanType(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCContPlanDutyParam");
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
			tError.moduleName = "LCContPlanDutyParamDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCContPlanDutyParam(GrpContNo ,ProposalGrpContNo ,GrpPolNo ,MainRiskCode ,MainRiskVersion ,RiskCode ,RiskVersion ,ContPlanCode ,ContPlanName ,DutyCode ,CalFactor ,CalFactorType ,CalFactorValue ,Remark ,PlanType ,SysContPlanCode) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getProposalGrpContNo() == null || this.get(i).getProposalGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getProposalGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getMainRiskCode() == null || this.get(i).getMainRiskCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getMainRiskCode());
			}
			if(this.get(i).getMainRiskVersion() == null || this.get(i).getMainRiskVersion().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getMainRiskVersion());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskVersion());
			}
			if(this.get(i).getContPlanCode() == null || this.get(i).getContPlanCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getContPlanCode());
			}
			if(this.get(i).getContPlanName() == null || this.get(i).getContPlanName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getContPlanName());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getDutyCode());
			}
			if(this.get(i).getCalFactor() == null || this.get(i).getCalFactor().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCalFactor());
			}
			if(this.get(i).getCalFactorType() == null || this.get(i).getCalFactorType().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCalFactorType());
			}
			if(this.get(i).getCalFactorValue() == null || this.get(i).getCalFactorValue().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCalFactorValue());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getRemark());
			}
			if(this.get(i).getPlanType() == null || this.get(i).getPlanType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPlanType());
			}
			if(this.get(i).getSysContPlanCode() == null || this.get(i).getSysContPlanCode().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSysContPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCContPlanDutyParam");
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
			tError.moduleName = "LCContPlanDutyParamDBSet";
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
