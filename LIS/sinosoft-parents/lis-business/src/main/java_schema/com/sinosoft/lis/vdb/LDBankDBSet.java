/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.vschema.LDBankSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDBankDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDBankDBSet extends LDBankSet
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
	public LDBankDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDBank");
		mflag = true;
	}

	public LDBankDBSet()
	{
		db = new DBOper( "LDBank" );
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
			tError.moduleName = "LDBankDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDBank WHERE  BankCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBankCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDBank");
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
			tError.moduleName = "LDBankDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDBank SET  BankCode = ? , BankName = ? , Operator = ? , ComCode = ? , AgentCode = ? , BankSelfCode = ? , AccName = ? , AccNo = ? , AgentPaySendF = ? , AgentPaySendPath = ? , AgentPayReceiveF = ? , AgentPaySavePath = ? , AgentPaySuccFlag = ? , AgentPayFailFlag = ? , AgentGetSendF = ? , AgentGetSendPath = ? , AgentGetReceiveF = ? , AgentGetSavePath = ? , AgentGetSuccFlag = ? , AgentGetFailFlag = ? , ChkSendF = ? , ChkSendPath = ? , ChkReceiveF = ? , ChkReceivePath = ? , ChkSuccFlag = ? , ChkFailFlag = ? , BankBackF = ? , BankBackPath = ? , AgentPayRFType = ? , AgentGetRFType = ? , BankType = ? , HeadBankCode = ? , Province = ? , City = ? , County = ? , State = ? WHERE  BankCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBankCode());
			}
			if(this.get(i).getBankName() == null || this.get(i).getBankName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBankName());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOperator());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getComCode());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentCode());
			}
			if(this.get(i).getBankSelfCode() == null || this.get(i).getBankSelfCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBankSelfCode());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAccName());
			}
			if(this.get(i).getAccNo() == null || this.get(i).getAccNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccNo());
			}
			if(this.get(i).getAgentPaySendF() == null || this.get(i).getAgentPaySendF().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAgentPaySendF());
			}
			if(this.get(i).getAgentPaySendPath() == null || this.get(i).getAgentPaySendPath().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentPaySendPath());
			}
			if(this.get(i).getAgentPayReceiveF() == null || this.get(i).getAgentPayReceiveF().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentPayReceiveF());
			}
			if(this.get(i).getAgentPaySavePath() == null || this.get(i).getAgentPaySavePath().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAgentPaySavePath());
			}
			if(this.get(i).getAgentPaySuccFlag() == null || this.get(i).getAgentPaySuccFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getAgentPaySuccFlag());
			}
			if(this.get(i).getAgentPayFailFlag() == null || this.get(i).getAgentPayFailFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentPayFailFlag());
			}
			if(this.get(i).getAgentGetSendF() == null || this.get(i).getAgentGetSendF().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentGetSendF());
			}
			if(this.get(i).getAgentGetSendPath() == null || this.get(i).getAgentGetSendPath().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentGetSendPath());
			}
			if(this.get(i).getAgentGetReceiveF() == null || this.get(i).getAgentGetReceiveF().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAgentGetReceiveF());
			}
			if(this.get(i).getAgentGetSavePath() == null || this.get(i).getAgentGetSavePath().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentGetSavePath());
			}
			if(this.get(i).getAgentGetSuccFlag() == null || this.get(i).getAgentGetSuccFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAgentGetSuccFlag());
			}
			if(this.get(i).getAgentGetFailFlag() == null || this.get(i).getAgentGetFailFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getAgentGetFailFlag());
			}
			if(this.get(i).getChkSendF() == null || this.get(i).getChkSendF().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getChkSendF());
			}
			if(this.get(i).getChkSendPath() == null || this.get(i).getChkSendPath().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getChkSendPath());
			}
			if(this.get(i).getChkReceiveF() == null || this.get(i).getChkReceiveF().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getChkReceiveF());
			}
			if(this.get(i).getChkReceivePath() == null || this.get(i).getChkReceivePath().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getChkReceivePath());
			}
			if(this.get(i).getChkSuccFlag() == null || this.get(i).getChkSuccFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getChkSuccFlag());
			}
			if(this.get(i).getChkFailFlag() == null || this.get(i).getChkFailFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getChkFailFlag());
			}
			if(this.get(i).getBankBackF() == null || this.get(i).getBankBackF().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getBankBackF());
			}
			if(this.get(i).getBankBackPath() == null || this.get(i).getBankBackPath().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBankBackPath());
			}
			if(this.get(i).getAgentPayRFType() == null || this.get(i).getAgentPayRFType().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAgentPayRFType());
			}
			if(this.get(i).getAgentGetRFType() == null || this.get(i).getAgentGetRFType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAgentGetRFType());
			}
			if(this.get(i).getBankType() == null || this.get(i).getBankType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankType());
			}
			if(this.get(i).getHeadBankCode() == null || this.get(i).getHeadBankCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getHeadBankCode());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getProvince());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getCity());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCounty());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getState());
			}
			// set where condition
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getBankCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDBank");
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
			tError.moduleName = "LDBankDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDBank VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBankCode());
			}
			if(this.get(i).getBankName() == null || this.get(i).getBankName().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getBankName());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getOperator());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getComCode());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentCode());
			}
			if(this.get(i).getBankSelfCode() == null || this.get(i).getBankSelfCode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getBankSelfCode());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getAccName());
			}
			if(this.get(i).getAccNo() == null || this.get(i).getAccNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAccNo());
			}
			if(this.get(i).getAgentPaySendF() == null || this.get(i).getAgentPaySendF().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAgentPaySendF());
			}
			if(this.get(i).getAgentPaySendPath() == null || this.get(i).getAgentPaySendPath().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getAgentPaySendPath());
			}
			if(this.get(i).getAgentPayReceiveF() == null || this.get(i).getAgentPayReceiveF().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAgentPayReceiveF());
			}
			if(this.get(i).getAgentPaySavePath() == null || this.get(i).getAgentPaySavePath().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getAgentPaySavePath());
			}
			if(this.get(i).getAgentPaySuccFlag() == null || this.get(i).getAgentPaySuccFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getAgentPaySuccFlag());
			}
			if(this.get(i).getAgentPayFailFlag() == null || this.get(i).getAgentPayFailFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getAgentPayFailFlag());
			}
			if(this.get(i).getAgentGetSendF() == null || this.get(i).getAgentGetSendF().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgentGetSendF());
			}
			if(this.get(i).getAgentGetSendPath() == null || this.get(i).getAgentGetSendPath().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAgentGetSendPath());
			}
			if(this.get(i).getAgentGetReceiveF() == null || this.get(i).getAgentGetReceiveF().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getAgentGetReceiveF());
			}
			if(this.get(i).getAgentGetSavePath() == null || this.get(i).getAgentGetSavePath().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAgentGetSavePath());
			}
			if(this.get(i).getAgentGetSuccFlag() == null || this.get(i).getAgentGetSuccFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAgentGetSuccFlag());
			}
			if(this.get(i).getAgentGetFailFlag() == null || this.get(i).getAgentGetFailFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getAgentGetFailFlag());
			}
			if(this.get(i).getChkSendF() == null || this.get(i).getChkSendF().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getChkSendF());
			}
			if(this.get(i).getChkSendPath() == null || this.get(i).getChkSendPath().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getChkSendPath());
			}
			if(this.get(i).getChkReceiveF() == null || this.get(i).getChkReceiveF().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getChkReceiveF());
			}
			if(this.get(i).getChkReceivePath() == null || this.get(i).getChkReceivePath().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getChkReceivePath());
			}
			if(this.get(i).getChkSuccFlag() == null || this.get(i).getChkSuccFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getChkSuccFlag());
			}
			if(this.get(i).getChkFailFlag() == null || this.get(i).getChkFailFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getChkFailFlag());
			}
			if(this.get(i).getBankBackF() == null || this.get(i).getBankBackF().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getBankBackF());
			}
			if(this.get(i).getBankBackPath() == null || this.get(i).getBankBackPath().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getBankBackPath());
			}
			if(this.get(i).getAgentPayRFType() == null || this.get(i).getAgentPayRFType().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getAgentPayRFType());
			}
			if(this.get(i).getAgentGetRFType() == null || this.get(i).getAgentGetRFType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAgentGetRFType());
			}
			if(this.get(i).getBankType() == null || this.get(i).getBankType().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankType());
			}
			if(this.get(i).getHeadBankCode() == null || this.get(i).getHeadBankCode().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getHeadBankCode());
			}
			if(this.get(i).getProvince() == null || this.get(i).getProvince().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getProvince());
			}
			if(this.get(i).getCity() == null || this.get(i).getCity().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getCity());
			}
			if(this.get(i).getCounty() == null || this.get(i).getCounty().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCounty());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getState());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDBank");
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
			tError.moduleName = "LDBankDBSet";
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
