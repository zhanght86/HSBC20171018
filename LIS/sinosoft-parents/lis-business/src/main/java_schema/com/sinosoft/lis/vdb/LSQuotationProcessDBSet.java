/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LSQuotationProcessSchema;
import com.sinosoft.lis.vschema.LSQuotationProcessSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LSQuotationProcessDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotationProcessDBSet extends LSQuotationProcessSet
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
	public LSQuotationProcessDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LSQuotationProcess");
		mflag = true;
	}

	public LSQuotationProcessDBSet()
	{
		db = new DBOper( "LSQuotationProcess" );
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
			tError.moduleName = "LSQuotationProcessDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LSQuotationProcess WHERE  QuotNo = ? AND QuotBatNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotationProcess");
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
			tError.moduleName = "LSQuotationProcessDBSet";
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
			pstmt = con.prepareStatement("UPDATE LSQuotationProcess SET  QuotNo = ? , QuotBatNo = ? , AppOper = ? , AppDate = ? , AppTime = ? , InputOper = ? , InputDate = ? , InputTime = ? , SubUWOpinion = ? , SubUWConclu = ? , SubUWOper = ? , SubUWDate = ? , SubUWTime = ? , BranchUWOpinion = ? , BranchUWConclu = ? , BranchUrgentFlag = ? , BranchUWOper = ? , BranchUWDate = ? , BranchUWTime = ? , SysUWLevel = ? , ReinsArrange = ? , UWOpinion = ? , UWConclu = ? , UWOper = ? , UWDate = ? , UWTime = ? , UWManagerOpinion = ? , UWManagerConclu = ? , UWManager = ? , UWManagerDate = ? , UWManagerTime = ? , BranchFinalOpinion = ? , BranchFinalConclu = ? , BranchFinalOper = ? , BranchFinalDate = ? , BranchFinalTime = ? , SubFinalOpinion = ? , SubFinalConclu = ? , SubFinalOper = ? , SubFinalDate = ? , SubFinalTime = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  QuotNo = ? AND QuotBatNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getAppOper() == null || this.get(i).getAppOper().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAppOper());
			}
			if(this.get(i).getAppDate() == null || this.get(i).getAppDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getAppDate()));
			}
			if(this.get(i).getAppTime() == null || this.get(i).getAppTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppTime());
			}
			if(this.get(i).getInputOper() == null || this.get(i).getInputOper().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInputOper());
			}
			if(this.get(i).getInputDate() == null || this.get(i).getInputDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getInputDate()));
			}
			if(this.get(i).getInputTime() == null || this.get(i).getInputTime().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInputTime());
			}
			if(this.get(i).getSubUWOpinion() == null || this.get(i).getSubUWOpinion().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSubUWOpinion());
			}
			if(this.get(i).getSubUWConclu() == null || this.get(i).getSubUWConclu().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSubUWConclu());
			}
			if(this.get(i).getSubUWOper() == null || this.get(i).getSubUWOper().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSubUWOper());
			}
			if(this.get(i).getSubUWDate() == null || this.get(i).getSubUWDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getSubUWDate()));
			}
			if(this.get(i).getSubUWTime() == null || this.get(i).getSubUWTime().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getSubUWTime());
			}
			if(this.get(i).getBranchUWOpinion() == null || this.get(i).getBranchUWOpinion().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBranchUWOpinion());
			}
			if(this.get(i).getBranchUWConclu() == null || this.get(i).getBranchUWConclu().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBranchUWConclu());
			}
			if(this.get(i).getBranchUrgentFlag() == null || this.get(i).getBranchUrgentFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBranchUrgentFlag());
			}
			if(this.get(i).getBranchUWOper() == null || this.get(i).getBranchUWOper().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getBranchUWOper());
			}
			if(this.get(i).getBranchUWDate() == null || this.get(i).getBranchUWDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getBranchUWDate()));
			}
			if(this.get(i).getBranchUWTime() == null || this.get(i).getBranchUWTime().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBranchUWTime());
			}
			if(this.get(i).getSysUWLevel() == null || this.get(i).getSysUWLevel().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSysUWLevel());
			}
			if(this.get(i).getReinsArrange() == null || this.get(i).getReinsArrange().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getReinsArrange());
			}
			if(this.get(i).getUWOpinion() == null || this.get(i).getUWOpinion().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUWOpinion());
			}
			if(this.get(i).getUWConclu() == null || this.get(i).getUWConclu().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getUWConclu());
			}
			if(this.get(i).getUWOper() == null || this.get(i).getUWOper().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getUWOper());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getUWTime());
			}
			if(this.get(i).getUWManagerOpinion() == null || this.get(i).getUWManagerOpinion().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getUWManagerOpinion());
			}
			if(this.get(i).getUWManagerConclu() == null || this.get(i).getUWManagerConclu().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getUWManagerConclu());
			}
			if(this.get(i).getUWManager() == null || this.get(i).getUWManager().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getUWManager());
			}
			if(this.get(i).getUWManagerDate() == null || this.get(i).getUWManagerDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getUWManagerDate()));
			}
			if(this.get(i).getUWManagerTime() == null || this.get(i).getUWManagerTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getUWManagerTime());
			}
			if(this.get(i).getBranchFinalOpinion() == null || this.get(i).getBranchFinalOpinion().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBranchFinalOpinion());
			}
			if(this.get(i).getBranchFinalConclu() == null || this.get(i).getBranchFinalConclu().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBranchFinalConclu());
			}
			if(this.get(i).getBranchFinalOper() == null || this.get(i).getBranchFinalOper().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBranchFinalOper());
			}
			if(this.get(i).getBranchFinalDate() == null || this.get(i).getBranchFinalDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getBranchFinalDate()));
			}
			if(this.get(i).getBranchFinalTime() == null || this.get(i).getBranchFinalTime().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getBranchFinalTime());
			}
			if(this.get(i).getSubFinalOpinion() == null || this.get(i).getSubFinalOpinion().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSubFinalOpinion());
			}
			if(this.get(i).getSubFinalConclu() == null || this.get(i).getSubFinalConclu().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSubFinalConclu());
			}
			if(this.get(i).getSubFinalOper() == null || this.get(i).getSubFinalOper().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSubFinalOper());
			}
			if(this.get(i).getSubFinalDate() == null || this.get(i).getSubFinalDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getSubFinalDate()));
			}
			if(this.get(i).getSubFinalTime() == null || this.get(i).getSubFinalTime().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSubFinalTime());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getQuotNo());
			}
			pstmt.setInt(49, this.get(i).getQuotBatNo());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotationProcess");
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
			tError.moduleName = "LSQuotationProcessDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LSQuotationProcess VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getQuotNo() == null || this.get(i).getQuotNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getQuotNo());
			}
			pstmt.setInt(2, this.get(i).getQuotBatNo());
			if(this.get(i).getAppOper() == null || this.get(i).getAppOper().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAppOper());
			}
			if(this.get(i).getAppDate() == null || this.get(i).getAppDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getAppDate()));
			}
			if(this.get(i).getAppTime() == null || this.get(i).getAppTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppTime());
			}
			if(this.get(i).getInputOper() == null || this.get(i).getInputOper().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInputOper());
			}
			if(this.get(i).getInputDate() == null || this.get(i).getInputDate().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getInputDate()));
			}
			if(this.get(i).getInputTime() == null || this.get(i).getInputTime().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInputTime());
			}
			if(this.get(i).getSubUWOpinion() == null || this.get(i).getSubUWOpinion().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getSubUWOpinion());
			}
			if(this.get(i).getSubUWConclu() == null || this.get(i).getSubUWConclu().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSubUWConclu());
			}
			if(this.get(i).getSubUWOper() == null || this.get(i).getSubUWOper().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getSubUWOper());
			}
			if(this.get(i).getSubUWDate() == null || this.get(i).getSubUWDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getSubUWDate()));
			}
			if(this.get(i).getSubUWTime() == null || this.get(i).getSubUWTime().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getSubUWTime());
			}
			if(this.get(i).getBranchUWOpinion() == null || this.get(i).getBranchUWOpinion().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBranchUWOpinion());
			}
			if(this.get(i).getBranchUWConclu() == null || this.get(i).getBranchUWConclu().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getBranchUWConclu());
			}
			if(this.get(i).getBranchUrgentFlag() == null || this.get(i).getBranchUrgentFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getBranchUrgentFlag());
			}
			if(this.get(i).getBranchUWOper() == null || this.get(i).getBranchUWOper().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getBranchUWOper());
			}
			if(this.get(i).getBranchUWDate() == null || this.get(i).getBranchUWDate().equals("null")) {
				pstmt.setDate(18,null);
			} else {
				pstmt.setDate(18, Date.valueOf(this.get(i).getBranchUWDate()));
			}
			if(this.get(i).getBranchUWTime() == null || this.get(i).getBranchUWTime().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getBranchUWTime());
			}
			if(this.get(i).getSysUWLevel() == null || this.get(i).getSysUWLevel().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSysUWLevel());
			}
			if(this.get(i).getReinsArrange() == null || this.get(i).getReinsArrange().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getReinsArrange());
			}
			if(this.get(i).getUWOpinion() == null || this.get(i).getUWOpinion().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getUWOpinion());
			}
			if(this.get(i).getUWConclu() == null || this.get(i).getUWConclu().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getUWConclu());
			}
			if(this.get(i).getUWOper() == null || this.get(i).getUWOper().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getUWOper());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWTime() == null || this.get(i).getUWTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getUWTime());
			}
			if(this.get(i).getUWManagerOpinion() == null || this.get(i).getUWManagerOpinion().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getUWManagerOpinion());
			}
			if(this.get(i).getUWManagerConclu() == null || this.get(i).getUWManagerConclu().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getUWManagerConclu());
			}
			if(this.get(i).getUWManager() == null || this.get(i).getUWManager().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getUWManager());
			}
			if(this.get(i).getUWManagerDate() == null || this.get(i).getUWManagerDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getUWManagerDate()));
			}
			if(this.get(i).getUWManagerTime() == null || this.get(i).getUWManagerTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getUWManagerTime());
			}
			if(this.get(i).getBranchFinalOpinion() == null || this.get(i).getBranchFinalOpinion().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBranchFinalOpinion());
			}
			if(this.get(i).getBranchFinalConclu() == null || this.get(i).getBranchFinalConclu().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getBranchFinalConclu());
			}
			if(this.get(i).getBranchFinalOper() == null || this.get(i).getBranchFinalOper().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getBranchFinalOper());
			}
			if(this.get(i).getBranchFinalDate() == null || this.get(i).getBranchFinalDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getBranchFinalDate()));
			}
			if(this.get(i).getBranchFinalTime() == null || this.get(i).getBranchFinalTime().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getBranchFinalTime());
			}
			if(this.get(i).getSubFinalOpinion() == null || this.get(i).getSubFinalOpinion().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSubFinalOpinion());
			}
			if(this.get(i).getSubFinalConclu() == null || this.get(i).getSubFinalConclu().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getSubFinalConclu());
			}
			if(this.get(i).getSubFinalOper() == null || this.get(i).getSubFinalOper().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSubFinalOper());
			}
			if(this.get(i).getSubFinalDate() == null || this.get(i).getSubFinalDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getSubFinalDate()));
			}
			if(this.get(i).getSubFinalTime() == null || this.get(i).getSubFinalTime().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSubFinalTime());
			}
			if(this.get(i).getMakeOperator() == null || this.get(i).getMakeOperator().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getMakeOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(43,null);
			} else {
				pstmt.setDate(43, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getModifyOperator());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(46,null);
			} else {
				pstmt.setDate(46, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LSQuotationProcess");
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
			tError.moduleName = "LSQuotationProcessDBSet";
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
