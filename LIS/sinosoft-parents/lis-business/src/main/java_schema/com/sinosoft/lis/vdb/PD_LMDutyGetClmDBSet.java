

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMDutyGetClmSchema;
import com.sinosoft.lis.vschema.PD_LMDutyGetClmSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMDutyGetClmDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class PD_LMDutyGetClmDBSet extends PD_LMDutyGetClmSet
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
	public PD_LMDutyGetClmDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_LMDutyGetClm");
		mflag = true;
	}

	public PD_LMDutyGetClmDBSet()
	{
		db = new DBOper( "PD_LMDutyGetClm" );
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
			tError.moduleName = "PD_LMDutyGetClmDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMDutyGetClm WHERE  GetDutyCode = ? AND GetDutyKind = ?");
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
		SQLString sqlObj = new SQLString("PD_LMDutyGetClm");
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
			tError.moduleName = "PD_LMDutyGetClmDBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_LMDutyGetClm SET  GetDutyCode = ? , GetDutyKind = ? , GetDutyName = ? , CalCode = ? , Defaultval = ? , CnterCalCode = ? , OthCalCode = ? , InpFlag = ? , StatType = ? , MinGet = ? , AfterGet = ? , MaxGet = ? , ClaimRate = ? , ClmDayLmt = ? , SumClmDayLmt = ? , Deductible = ? , DeDuctDay = ? , ObsPeriod = ? , DeadValiFlag = ? , DeadToPValueFlag = ? , NeedReCompute = ? , CasePolType = ? , DeformityGrade = ? , FilterCalCode = ? , EffectOnMainRisk = ? , ExtraAmntFlag = ? , YearGetLimitFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? , GetByHosday = ? WHERE  GetDutyCode = ? AND GetDutyKind = ?");
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
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyName());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalCode());
			}
			pstmt.setDouble(5, this.get(i).getDefaultval());
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
			if(this.get(i).getExtraAmntFlag() == null || this.get(i).getExtraAmntFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getExtraAmntFlag());
			}
			if(this.get(i).getYearGetLimitFlag() == null || this.get(i).getYearGetLimitFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getYearGetLimitFlag());
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
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(35, this.get(i).getStandbyflag3());
			pstmt.setInt(36, this.get(i).getStandbyflag4());
			pstmt.setDouble(37, this.get(i).getStandbyflag5());
			pstmt.setDouble(38, this.get(i).getStandbyflag6());
			if(this.get(i).getGetByHosday() == null || this.get(i).getGetByHosday().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetByHosday());
			}
			// set where condition
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getGetDutyKind());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGetClm");
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
			tError.moduleName = "PD_LMDutyGetClmDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_LMDutyGetClm(GetDutyCode ,GetDutyKind ,GetDutyName ,CalCode ,Defaultval ,CnterCalCode ,OthCalCode ,InpFlag ,StatType ,MinGet ,AfterGet ,MaxGet ,ClaimRate ,ClmDayLmt ,SumClmDayLmt ,Deductible ,DeDuctDay ,ObsPeriod ,DeadValiFlag ,DeadToPValueFlag ,NeedReCompute ,CasePolType ,DeformityGrade ,FilterCalCode ,EffectOnMainRisk ,ExtraAmntFlag ,YearGetLimitFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6 ,GetByHosday) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.get(i).getGetDutyName() == null || this.get(i).getGetDutyName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGetDutyName());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCalCode());
			}
			pstmt.setDouble(5, this.get(i).getDefaultval());
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
			if(this.get(i).getExtraAmntFlag() == null || this.get(i).getExtraAmntFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getExtraAmntFlag());
			}
			if(this.get(i).getYearGetLimitFlag() == null || this.get(i).getYearGetLimitFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getYearGetLimitFlag());
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
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(35, this.get(i).getStandbyflag3());
			pstmt.setInt(36, this.get(i).getStandbyflag4());
			pstmt.setDouble(37, this.get(i).getStandbyflag5());
			pstmt.setDouble(38, this.get(i).getStandbyflag6());
			if(this.get(i).getGetByHosday() == null || this.get(i).getGetByHosday().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getGetByHosday());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMDutyGetClm");
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
			tError.moduleName = "PD_LMDutyGetClmDBSet";
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
