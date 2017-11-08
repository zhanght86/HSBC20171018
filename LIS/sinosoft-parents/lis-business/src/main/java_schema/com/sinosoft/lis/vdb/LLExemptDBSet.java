/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLExemptSchema;
import com.sinosoft.lis.vschema.LLExemptSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLExemptDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLExemptDBSet extends LLExemptSet
{
private static Logger logger = Logger.getLogger(LLExemptDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLExemptDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLExempt");
		mflag = true;
	}

	public LLExemptDBSet()
	{
		db = new DBOper( "LLExempt" );
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
			tError.moduleName = "LLExemptDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLExempt WHERE  ClmNo = ? AND PolNo = ? AND DutyCode = ? AND PayPlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDutyCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLExempt");
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
			tError.moduleName = "LLExemptDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLExempt SET  ClmNo = ? , GrpContNo = ? , ContNo = ? , PolNo = ? , DutyCode = ? , PayPlanCode = ? , PayPlanType = ? , AppntType = ? , AppntNo = ? , UrgePayFlag = ? , NeedAcc = ? , PayTimes = ? , Rate = ? , PayStartDate = ? , PayEndDate = ? , PaytoDate = ? , PayIntv = ? , StandPrem = ? , Prem = ? , SumPrem = ? , SuppRiskScore = ? , FreeFlag = ? , FreeRate = ? , FreeStartDate = ? , FreeEndDate = ? , State = ? , ManageCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , AddFeeDirect = ? , ExemptPeriod = ? , NBPolNo = ? , ExemptReason = ? , ExemptDesc = ? WHERE  ClmNo = ? AND PolNo = ? AND DutyCode = ? AND PayPlanCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDutyCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getPayPlanType() == null || this.get(i).getPayPlanType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPayPlanType());
			}
			if(this.get(i).getAppntType() == null || this.get(i).getAppntType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppntType());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAppntNo());
			}
			if(this.get(i).getUrgePayFlag() == null || this.get(i).getUrgePayFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getUrgePayFlag());
			}
			if(this.get(i).getNeedAcc() == null || this.get(i).getNeedAcc().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNeedAcc());
			}
			pstmt.setInt(12, this.get(i).getPayTimes());
			pstmt.setDouble(13, this.get(i).getRate());
			if(this.get(i).getPayStartDate() == null || this.get(i).getPayStartDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getPayStartDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getPaytoDate()));
			}
			pstmt.setInt(17, this.get(i).getPayIntv());
			pstmt.setDouble(18, this.get(i).getStandPrem());
			pstmt.setDouble(19, this.get(i).getPrem());
			pstmt.setDouble(20, this.get(i).getSumPrem());
			pstmt.setDouble(21, this.get(i).getSuppRiskScore());
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getFreeFlag());
			}
			pstmt.setDouble(23, this.get(i).getFreeRate());
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getFreeEndDate() == null || this.get(i).getFreeEndDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getFreeEndDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getManageCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getModifyTime());
			}
			if(this.get(i).getAddFeeDirect() == null || this.get(i).getAddFeeDirect().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getAddFeeDirect());
			}
			if(this.get(i).getExemptPeriod() == null || this.get(i).getExemptPeriod().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getExemptPeriod());
			}
			if(this.get(i).getNBPolNo() == null || this.get(i).getNBPolNo().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getNBPolNo());
			}
			if(this.get(i).getExemptReason() == null || this.get(i).getExemptReason().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getExemptReason());
			}
			if(this.get(i).getExemptDesc() == null || this.get(i).getExemptDesc().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getExemptDesc());
			}
			// set where condition
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getClmNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getDutyCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getPayPlanCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLExempt");
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
			tError.moduleName = "LLExemptDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLExempt(ClmNo ,GrpContNo ,ContNo ,PolNo ,DutyCode ,PayPlanCode ,PayPlanType ,AppntType ,AppntNo ,UrgePayFlag ,NeedAcc ,PayTimes ,Rate ,PayStartDate ,PayEndDate ,PaytoDate ,PayIntv ,StandPrem ,Prem ,SumPrem ,SuppRiskScore ,FreeFlag ,FreeRate ,FreeStartDate ,FreeEndDate ,State ,ManageCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,AddFeeDirect ,ExemptPeriod ,NBPolNo ,ExemptReason ,ExemptDesc) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getDutyCode());
			}
			if(this.get(i).getPayPlanCode() == null || this.get(i).getPayPlanCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getPayPlanCode());
			}
			if(this.get(i).getPayPlanType() == null || this.get(i).getPayPlanType().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPayPlanType());
			}
			if(this.get(i).getAppntType() == null || this.get(i).getAppntType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAppntType());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAppntNo());
			}
			if(this.get(i).getUrgePayFlag() == null || this.get(i).getUrgePayFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getUrgePayFlag());
			}
			if(this.get(i).getNeedAcc() == null || this.get(i).getNeedAcc().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getNeedAcc());
			}
			pstmt.setInt(12, this.get(i).getPayTimes());
			pstmt.setDouble(13, this.get(i).getRate());
			if(this.get(i).getPayStartDate() == null || this.get(i).getPayStartDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getPayStartDate()));
			}
			if(this.get(i).getPayEndDate() == null || this.get(i).getPayEndDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getPayEndDate()));
			}
			if(this.get(i).getPaytoDate() == null || this.get(i).getPaytoDate().equals("null")) {
				pstmt.setDate(16,null);
			} else {
				pstmt.setDate(16, Date.valueOf(this.get(i).getPaytoDate()));
			}
			pstmt.setInt(17, this.get(i).getPayIntv());
			pstmt.setDouble(18, this.get(i).getStandPrem());
			pstmt.setDouble(19, this.get(i).getPrem());
			pstmt.setDouble(20, this.get(i).getSumPrem());
			pstmt.setDouble(21, this.get(i).getSuppRiskScore());
			if(this.get(i).getFreeFlag() == null || this.get(i).getFreeFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getFreeFlag());
			}
			pstmt.setDouble(23, this.get(i).getFreeRate());
			if(this.get(i).getFreeStartDate() == null || this.get(i).getFreeStartDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getFreeStartDate()));
			}
			if(this.get(i).getFreeEndDate() == null || this.get(i).getFreeEndDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getFreeEndDate()));
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getState());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getManageCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getModifyTime());
			}
			if(this.get(i).getAddFeeDirect() == null || this.get(i).getAddFeeDirect().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getAddFeeDirect());
			}
			if(this.get(i).getExemptPeriod() == null || this.get(i).getExemptPeriod().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getExemptPeriod());
			}
			if(this.get(i).getNBPolNo() == null || this.get(i).getNBPolNo().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getNBPolNo());
			}
			if(this.get(i).getExemptReason() == null || this.get(i).getExemptReason().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getExemptReason());
			}
			if(this.get(i).getExemptDesc() == null || this.get(i).getExemptDesc().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getExemptDesc());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLExempt");
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
			tError.moduleName = "LLExemptDBSet";
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
