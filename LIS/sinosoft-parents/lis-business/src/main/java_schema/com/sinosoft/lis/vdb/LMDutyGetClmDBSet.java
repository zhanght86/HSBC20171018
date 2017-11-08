

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.vschema.LMDutyGetClmSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LMDutyGetClmDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyGetClmDBSet extends LMDutyGetClmSet
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
	public LMDutyGetClmDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMDutyGetClm");
		mflag = true;
	}

	public LMDutyGetClmDBSet()
	{
		db = new DBOper( "LMDutyGetClm" );
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
			tError.moduleName = "LMDutyGetClmDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LMDutyGetClm WHERE  GetDutyCode = ? AND GetDutyKind = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetClm");
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
			tError.moduleName = "LMDutyGetClmDBSet";
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
			pstmt = con.prepareStatement("UPDATE LMDutyGetClm SET  GetDutyCode = ? , GetDutyName = ? , GetDutyKind = ? , DefaultVal = ? , CalCode = ? , CnterCalCode = ? , OthCalCode = ? , InpFlag = ? , StatType = ? , MinGet = ? , AfterGet = ? , MaxGet = ? , ClaimRate = ? , ClmDayLmt = ? , SumClmDayLmt = ? , Deductible = ? , DeDuctDay = ? , ObsPeriod = ? , DeadValiFlag = ? , DeadToPValueFlag = ? , NeedReCompute = ? , CasePolType = ? , DeformityGrade = ? , FilterCalCode = ? , EffectOnMainRisk = ? , GetByHosday = ? WHERE  GetDutyCode = ? AND GetDutyKind = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyName());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyKind());
			}
			pstmt.setDouble(4, this.get(i).getDefaultVal());
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalCode());
			}
			if(this.get(i).getCnterCalCode() == null || this.get(i).getCnterCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCnterCalCode());
			}
			if(this.get(i).getOthCalCode() == null || this.get(i).getOthCalCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOthCalCode());
			}
			if(this.get(i).getInpFlag() == null || this.get(i).getInpFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInpFlag());
			}
			if(this.get(i).getStatType() == null || this.get(i).getStatType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getStatType());
			}
			pstmt.setDouble(10, this.get(i).getMinGet());
			if(this.get(i).getAfterGet() == null || this.get(i).getAfterGet().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAfterGet());
			}
			pstmt.setDouble(12, this.get(i).getMaxGet());
			pstmt.setDouble(13, this.get(i).getClaimRate());
			pstmt.setInt(14, this.get(i).getClmDayLmt());
			pstmt.setInt(15, this.get(i).getSumClmDayLmt());
			pstmt.setDouble(16, this.get(i).getDeductible());
			pstmt.setInt(17, this.get(i).getDeDuctDay());
			pstmt.setInt(18, this.get(i).getObsPeriod());
			if(this.get(i).getDeadValiFlag() == null || this.get(i).getDeadValiFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getDeadValiFlag());
			}
			if(this.get(i).getDeadToPValueFlag() == null || this.get(i).getDeadToPValueFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getDeadToPValueFlag());
			}
			if(this.get(i).getNeedReCompute() == null || this.get(i).getNeedReCompute().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getNeedReCompute());
			}
			if(this.get(i).getCasePolType() == null || this.get(i).getCasePolType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getCasePolType());
			}
			if(this.get(i).getDeformityGrade() == null || this.get(i).getDeformityGrade().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getDeformityGrade());
			}
			if(this.get(i).getFilterCalCode() == null || this.get(i).getFilterCalCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getFilterCalCode());
			}
			if(this.get(i).getEffectOnMainRisk() == null || this.get(i).getEffectOnMainRisk().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getEffectOnMainRisk());
			}
			if(this.get(i).getGetByHosday() == null || this.get(i).getGetByHosday().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getGetByHosday());
			}
			// set where condition
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetClm");
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
			tError.moduleName = "LMDutyGetClmDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LMDutyGetClm(GetDutyCode ,GetDutyName ,GetDutyKind ,DefaultVal ,CalCode ,CnterCalCode ,OthCalCode ,InpFlag ,StatType ,MinGet ,AfterGet ,MaxGet ,ClaimRate ,ClmDayLmt ,SumClmDayLmt ,Deductible ,DeDuctDay ,ObsPeriod ,DeadValiFlag ,DeadToPValueFlag ,NeedReCompute ,CasePolType ,DeformityGrade ,FilterCalCode ,EffectOnMainRisk ,GetByHosday) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGetDutyName());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyKind());
			}
			pstmt.setDouble(4, this.get(i).getDefaultVal());
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getCalCode());
			}
			if(this.get(i).getCnterCalCode() == null || this.get(i).getCnterCalCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getCnterCalCode());
			}
			if(this.get(i).getOthCalCode() == null || this.get(i).getOthCalCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOthCalCode());
			}
			if(this.get(i).getInpFlag() == null || this.get(i).getInpFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInpFlag());
			}
			if(this.get(i).getStatType() == null || this.get(i).getStatType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getStatType());
			}
			pstmt.setDouble(10, this.get(i).getMinGet());
			if(this.get(i).getAfterGet() == null || this.get(i).getAfterGet().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAfterGet());
			}
			pstmt.setDouble(12, this.get(i).getMaxGet());
			pstmt.setDouble(13, this.get(i).getClaimRate());
			pstmt.setInt(14, this.get(i).getClmDayLmt());
			pstmt.setInt(15, this.get(i).getSumClmDayLmt());
			pstmt.setDouble(16, this.get(i).getDeductible());
			pstmt.setInt(17, this.get(i).getDeDuctDay());
			pstmt.setInt(18, this.get(i).getObsPeriod());
			if(this.get(i).getDeadValiFlag() == null || this.get(i).getDeadValiFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getDeadValiFlag());
			}
			if(this.get(i).getDeadToPValueFlag() == null || this.get(i).getDeadToPValueFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getDeadToPValueFlag());
			}
			if(this.get(i).getNeedReCompute() == null || this.get(i).getNeedReCompute().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getNeedReCompute());
			}
			if(this.get(i).getCasePolType() == null || this.get(i).getCasePolType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getCasePolType());
			}
			if(this.get(i).getDeformityGrade() == null || this.get(i).getDeformityGrade().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getDeformityGrade());
			}
			if(this.get(i).getFilterCalCode() == null || this.get(i).getFilterCalCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getFilterCalCode());
			}
			if(this.get(i).getEffectOnMainRisk() == null || this.get(i).getEffectOnMainRisk().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getEffectOnMainRisk());
			}
			if(this.get(i).getGetByHosday() == null || this.get(i).getGetByHosday().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getGetByHosday());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LMDutyGetClm");
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
			tError.moduleName = "LMDutyGetClmDBSet";
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
