/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.PD_LMClaimCtrlSchema;
import com.sinosoft.lis.vschema.PD_LMClaimCtrlSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: PD_LMClaimCtrlDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMClaimCtrlDBSet extends PD_LMClaimCtrlSet
{
private static Logger logger = Logger.getLogger(PD_LMClaimCtrlDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public PD_LMClaimCtrlDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PD_LMClaimCtrl");
		mflag = true;
	}

	public PD_LMClaimCtrlDBSet()
	{
		db = new DBOper( "PD_LMClaimCtrl" );
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
			tError.moduleName = "PD_LMClaimCtrlDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM PD_LMClaimCtrl WHERE  ClaimCtrlCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMClaimCtrl");
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
			tError.moduleName = "PD_LMClaimCtrlDBSet";
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
			pstmt = con.prepareStatement("UPDATE PD_LMClaimCtrl SET  ClaimCtrlCode = ? , ClaimCtrlName = ? , ClaimCtrlType = ? , ClaimEngineDesc = ? , PeriodFlag = ? , DefPeriodFlag = ? , FamilyFlag = ? , InsPeriodFlag = ? , ClmPeriodMAX = ? , ClmPeriodMAXFlag = ? , ClmPeriodMAXCtrl = ? , ClmPeriodMIN = ? , ClmPeriodMINFlag = ? , ClmPeriodMINCtrl = ? , CalCode = ? , CalResultType = ? , DefaultValue = ? , CalCtrlFlag = ? , FeeCalCode = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Standbyflag1 = ? , Standbyflag2 = ? , Standbyflag3 = ? , Standbyflag4 = ? , Standbyflag5 = ? , Standbyflag6 = ? WHERE  ClaimCtrlCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}
			if(this.get(i).getClaimCtrlName() == null || this.get(i).getClaimCtrlName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getClaimCtrlName());
			}
			if(this.get(i).getClaimCtrlType() == null || this.get(i).getClaimCtrlType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClaimCtrlType());
			}
			if(this.get(i).getClaimEngineDesc() == null || this.get(i).getClaimEngineDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getClaimEngineDesc());
			}
			if(this.get(i).getPeriodFlag() == null || this.get(i).getPeriodFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPeriodFlag());
			}
			if(this.get(i).getDefPeriodFlag() == null || this.get(i).getDefPeriodFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDefPeriodFlag());
			}
			if(this.get(i).getFamilyFlag() == null || this.get(i).getFamilyFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFamilyFlag());
			}
			if(this.get(i).getInsPeriodFlag() == null || this.get(i).getInsPeriodFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsPeriodFlag());
			}
			pstmt.setInt(9, this.get(i).getClmPeriodMAX());
			if(this.get(i).getClmPeriodMAXFlag() == null || this.get(i).getClmPeriodMAXFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getClmPeriodMAXFlag());
			}
			if(this.get(i).getClmPeriodMAXCtrl() == null || this.get(i).getClmPeriodMAXCtrl().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getClmPeriodMAXCtrl());
			}
			pstmt.setInt(12, this.get(i).getClmPeriodMIN());
			if(this.get(i).getClmPeriodMINFlag() == null || this.get(i).getClmPeriodMINFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getClmPeriodMINFlag());
			}
			if(this.get(i).getClmPeriodMINCtrl() == null || this.get(i).getClmPeriodMINCtrl().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getClmPeriodMINCtrl());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCalCode());
			}
			if(this.get(i).getCalResultType() == null || this.get(i).getCalResultType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCalResultType());
			}
			pstmt.setDouble(17, this.get(i).getDefaultValue());
			if(this.get(i).getCalCtrlFlag() == null || this.get(i).getCalCtrlFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCalCtrlFlag());
			}
			if(this.get(i).getFeeCalCode() == null || this.get(i).getFeeCalCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getFeeCalCode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(27, this.get(i).getStandbyflag3());
			pstmt.setInt(28, this.get(i).getStandbyflag4());
			pstmt.setDouble(29, this.get(i).getStandbyflag5());
			pstmt.setDouble(30, this.get(i).getStandbyflag6());
			// set where condition
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getClaimCtrlCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMClaimCtrl");
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
			tError.moduleName = "PD_LMClaimCtrlDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO PD_LMClaimCtrl(ClaimCtrlCode ,ClaimCtrlName ,ClaimCtrlType ,ClaimEngineDesc ,PeriodFlag ,DefPeriodFlag ,FamilyFlag ,InsPeriodFlag ,ClmPeriodMAX ,ClmPeriodMAXFlag ,ClmPeriodMAXCtrl ,ClmPeriodMIN ,ClmPeriodMINFlag ,ClmPeriodMINCtrl ,CalCode ,CalResultType ,DefaultValue ,CalCtrlFlag ,FeeCalCode ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,Standbyflag1 ,Standbyflag2 ,Standbyflag3 ,Standbyflag4 ,Standbyflag5 ,Standbyflag6) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClaimCtrlCode() == null || this.get(i).getClaimCtrlCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClaimCtrlCode());
			}
			if(this.get(i).getClaimCtrlName() == null || this.get(i).getClaimCtrlName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getClaimCtrlName());
			}
			if(this.get(i).getClaimCtrlType() == null || this.get(i).getClaimCtrlType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getClaimCtrlType());
			}
			if(this.get(i).getClaimEngineDesc() == null || this.get(i).getClaimEngineDesc().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getClaimEngineDesc());
			}
			if(this.get(i).getPeriodFlag() == null || this.get(i).getPeriodFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getPeriodFlag());
			}
			if(this.get(i).getDefPeriodFlag() == null || this.get(i).getDefPeriodFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDefPeriodFlag());
			}
			if(this.get(i).getFamilyFlag() == null || this.get(i).getFamilyFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getFamilyFlag());
			}
			if(this.get(i).getInsPeriodFlag() == null || this.get(i).getInsPeriodFlag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInsPeriodFlag());
			}
			pstmt.setInt(9, this.get(i).getClmPeriodMAX());
			if(this.get(i).getClmPeriodMAXFlag() == null || this.get(i).getClmPeriodMAXFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getClmPeriodMAXFlag());
			}
			if(this.get(i).getClmPeriodMAXCtrl() == null || this.get(i).getClmPeriodMAXCtrl().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getClmPeriodMAXCtrl());
			}
			pstmt.setInt(12, this.get(i).getClmPeriodMIN());
			if(this.get(i).getClmPeriodMINFlag() == null || this.get(i).getClmPeriodMINFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getClmPeriodMINFlag());
			}
			if(this.get(i).getClmPeriodMINCtrl() == null || this.get(i).getClmPeriodMINCtrl().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getClmPeriodMINCtrl());
			}
			if(this.get(i).getCalCode() == null || this.get(i).getCalCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getCalCode());
			}
			if(this.get(i).getCalResultType() == null || this.get(i).getCalResultType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCalResultType());
			}
			pstmt.setDouble(17, this.get(i).getDefaultValue());
			if(this.get(i).getCalCtrlFlag() == null || this.get(i).getCalCtrlFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCalCtrlFlag());
			}
			if(this.get(i).getFeeCalCode() == null || this.get(i).getFeeCalCode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getFeeCalCode());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getModifyTime());
			}
			if(this.get(i).getStandbyflag1() == null || this.get(i).getStandbyflag1().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getStandbyflag1());
			}
			if(this.get(i).getStandbyflag2() == null || this.get(i).getStandbyflag2().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getStandbyflag2());
			}
			pstmt.setInt(27, this.get(i).getStandbyflag3());
			pstmt.setInt(28, this.get(i).getStandbyflag4());
			pstmt.setDouble(29, this.get(i).getStandbyflag5());
			pstmt.setDouble(30, this.get(i).getStandbyflag6());

		// only for debug purpose
		SQLString sqlObj = new SQLString("PD_LMClaimCtrl");
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
			tError.moduleName = "PD_LMClaimCtrlDBSet";
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