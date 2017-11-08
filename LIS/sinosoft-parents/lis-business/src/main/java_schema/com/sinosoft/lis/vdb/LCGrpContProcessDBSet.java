/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LCGrpContProcessSchema;
import com.sinosoft.lis.vschema.LCGrpContProcessSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCGrpContProcessDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新单管理
 */
public class LCGrpContProcessDBSet extends LCGrpContProcessSet
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
	public LCGrpContProcessDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCGrpContProcess");
		mflag = true;
	}

	public LCGrpContProcessDBSet()
	{
		db = new DBOper( "LCGrpContProcess" );
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
			tError.moduleName = "LCGrpContProcessDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LCGrpContProcess WHERE  GrpPropNo = ? AND GrpContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpContProcess");
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
			tError.moduleName = "LCGrpContProcessDBSet";
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
			pstmt = con.prepareStatement("UPDATE LCGrpContProcess SET  GrpPropNo = ? , GrpContNo = ? , ScanOperator = ? , ScanDate = ? , ScanTime = ? , Input1Operator = ? , Input1Date = ? , Input1Time = ? , Out1Date = ? , Out1Time = ? , Input2Operator = ? , Input2Date = ? , Input2Time = ? , Out2Date = ? , Out2Time = ? , Input3Operator = ? , Input3Date = ? , Input3Time = ? , Out3Date = ? , Out3Time = ? , ApproveOperator = ? , ApproveDate = ? , ApproveTime = ? , UWOperator = ? , UWDate = ? , UWTime = ? , UWFlag = ? , BackUWOperator = ? , BackUWDate = ? , BackUWTime = ? , GetPolDate = ? , GetPolTime = ? , CustomGetPolDate = ? , CustomGetPolTime = ? , ConfDate = ? , ConfOperator = ? , SignOperator = ? , SignDate = ? , SignTime = ? , AcceptWorkdays = ? WHERE  GrpPropNo = ? AND GrpContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getScanOperator() == null || this.get(i).getScanOperator().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getScanOperator());
			}
			if(this.get(i).getScanDate() == null || this.get(i).getScanDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getScanDate()));
			}
			if(this.get(i).getScanTime() == null || this.get(i).getScanTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getScanTime());
			}
			if(this.get(i).getInput1Operator() == null || this.get(i).getInput1Operator().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInput1Operator());
			}
			if(this.get(i).getInput1Date() == null || this.get(i).getInput1Date().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getInput1Date()));
			}
			if(this.get(i).getInput1Time() == null || this.get(i).getInput1Time().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInput1Time());
			}
			if(this.get(i).getOut1Date() == null || this.get(i).getOut1Date().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getOut1Date()));
			}
			if(this.get(i).getOut1Time() == null || this.get(i).getOut1Time().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOut1Time());
			}
			if(this.get(i).getInput2Operator() == null || this.get(i).getInput2Operator().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInput2Operator());
			}
			if(this.get(i).getInput2Date() == null || this.get(i).getInput2Date().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getInput2Date()));
			}
			if(this.get(i).getInput2Time() == null || this.get(i).getInput2Time().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInput2Time());
			}
			if(this.get(i).getOut2Date() == null || this.get(i).getOut2Date().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getOut2Date()));
			}
			if(this.get(i).getOut2Time() == null || this.get(i).getOut2Time().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOut2Time());
			}
			if(this.get(i).getInput3Operator() == null || this.get(i).getInput3Operator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getInput3Operator());
			}
			if(this.get(i).getInput3Date() == null || this.get(i).getInput3Date().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getInput3Date()));
			}
			if(this.get(i).getInput3Time() == null || this.get(i).getInput3Time().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getInput3Time());
			}
			if(this.get(i).getOut3Date() == null || this.get(i).getOut3Date().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getOut3Date()));
			}
			if(this.get(i).getOut3Time() == null || this.get(i).getOut3Time().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOut3Time());
			}
			if(this.get(i).getApproveOperator() == null || this.get(i).getApproveOperator().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getApproveOperator());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getUWOperator());
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
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getUWFlag());
			}
			if(this.get(i).getBackUWOperator() == null || this.get(i).getBackUWOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBackUWOperator());
			}
			if(this.get(i).getBackUWDate() == null || this.get(i).getBackUWDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getBackUWDate()));
			}
			if(this.get(i).getBackUWTime() == null || this.get(i).getBackUWTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBackUWTime());
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getGetPolTime() == null || this.get(i).getGetPolTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getGetPolTime());
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getCustomGetPolTime() == null || this.get(i).getCustomGetPolTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getCustomGetPolTime());
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getConfOperator() == null || this.get(i).getConfOperator().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getConfOperator());
			}
			if(this.get(i).getSignOperator() == null || this.get(i).getSignOperator().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSignOperator());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSignTime());
			}
			pstmt.setInt(40, this.get(i).getAcceptWorkdays());
			// set where condition
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getGrpContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpContProcess");
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
			tError.moduleName = "LCGrpContProcessDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LCGrpContProcess VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPropNo() == null || this.get(i).getGrpPropNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPropNo());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			if(this.get(i).getScanOperator() == null || this.get(i).getScanOperator().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getScanOperator());
			}
			if(this.get(i).getScanDate() == null || this.get(i).getScanDate().equals("null")) {
				pstmt.setDate(4,null);
			} else {
				pstmt.setDate(4, Date.valueOf(this.get(i).getScanDate()));
			}
			if(this.get(i).getScanTime() == null || this.get(i).getScanTime().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getScanTime());
			}
			if(this.get(i).getInput1Operator() == null || this.get(i).getInput1Operator().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInput1Operator());
			}
			if(this.get(i).getInput1Date() == null || this.get(i).getInput1Date().equals("null")) {
				pstmt.setDate(7,null);
			} else {
				pstmt.setDate(7, Date.valueOf(this.get(i).getInput1Date()));
			}
			if(this.get(i).getInput1Time() == null || this.get(i).getInput1Time().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInput1Time());
			}
			if(this.get(i).getOut1Date() == null || this.get(i).getOut1Date().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getOut1Date()));
			}
			if(this.get(i).getOut1Time() == null || this.get(i).getOut1Time().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getOut1Time());
			}
			if(this.get(i).getInput2Operator() == null || this.get(i).getInput2Operator().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getInput2Operator());
			}
			if(this.get(i).getInput2Date() == null || this.get(i).getInput2Date().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getInput2Date()));
			}
			if(this.get(i).getInput2Time() == null || this.get(i).getInput2Time().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getInput2Time());
			}
			if(this.get(i).getOut2Date() == null || this.get(i).getOut2Date().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getOut2Date()));
			}
			if(this.get(i).getOut2Time() == null || this.get(i).getOut2Time().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getOut2Time());
			}
			if(this.get(i).getInput3Operator() == null || this.get(i).getInput3Operator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getInput3Operator());
			}
			if(this.get(i).getInput3Date() == null || this.get(i).getInput3Date().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getInput3Date()));
			}
			if(this.get(i).getInput3Time() == null || this.get(i).getInput3Time().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getInput3Time());
			}
			if(this.get(i).getOut3Date() == null || this.get(i).getOut3Date().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getOut3Date()));
			}
			if(this.get(i).getOut3Time() == null || this.get(i).getOut3Time().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getOut3Time());
			}
			if(this.get(i).getApproveOperator() == null || this.get(i).getApproveOperator().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getApproveOperator());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getApproveTime());
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getUWOperator());
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
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getUWFlag());
			}
			if(this.get(i).getBackUWOperator() == null || this.get(i).getBackUWOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBackUWOperator());
			}
			if(this.get(i).getBackUWDate() == null || this.get(i).getBackUWDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getBackUWDate()));
			}
			if(this.get(i).getBackUWTime() == null || this.get(i).getBackUWTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBackUWTime());
			}
			if(this.get(i).getGetPolDate() == null || this.get(i).getGetPolDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getGetPolDate()));
			}
			if(this.get(i).getGetPolTime() == null || this.get(i).getGetPolTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getGetPolTime());
			}
			if(this.get(i).getCustomGetPolDate() == null || this.get(i).getCustomGetPolDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getCustomGetPolDate()));
			}
			if(this.get(i).getCustomGetPolTime() == null || this.get(i).getCustomGetPolTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getCustomGetPolTime());
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(35,null);
			} else {
				pstmt.setDate(35, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getConfOperator() == null || this.get(i).getConfOperator().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getConfOperator());
			}
			if(this.get(i).getSignOperator() == null || this.get(i).getSignOperator().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getSignOperator());
			}
			if(this.get(i).getSignDate() == null || this.get(i).getSignDate().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getSignDate()));
			}
			if(this.get(i).getSignTime() == null || this.get(i).getSignTime().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSignTime());
			}
			pstmt.setInt(40, this.get(i).getAcceptWorkdays());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCGrpContProcess");
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
			tError.moduleName = "LCGrpContProcessDBSet";
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
