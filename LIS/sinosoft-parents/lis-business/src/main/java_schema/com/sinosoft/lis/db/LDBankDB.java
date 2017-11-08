/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.vschema.LDBankSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDBankDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDBankDB extends LDBankSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 为批量操作而准备的语句和游标对象
	 */
	private ResultSet mResultSet = null;
	private Statement mStatement = null;
	// @Constructor
	public LDBankDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LDBank" );
		mflag = true;
	}

	public LDBankDB()
	{
		con = null;
		db = new DBOper( "LDBank" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LDBankSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LDBankSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("DELETE FROM LDBank WHERE  BankCode = ?");
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBankCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDBank");
		sqlObj.setSQL(4, this);
		sqlObj.getSQL();

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDBank");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LDBank SET  BankCode = ? , BankName = ? , Operator = ? , ComCode = ? , AgentCode = ? , BankSelfCode = ? , AccName = ? , AccNo = ? , AgentPaySendF = ? , AgentPaySendPath = ? , AgentPayReceiveF = ? , AgentPaySavePath = ? , AgentPaySuccFlag = ? , AgentPayFailFlag = ? , AgentGetSendF = ? , AgentGetSendPath = ? , AgentGetReceiveF = ? , AgentGetSavePath = ? , AgentGetSuccFlag = ? , AgentGetFailFlag = ? , ChkSendF = ? , ChkSendPath = ? , ChkReceiveF = ? , ChkReceivePath = ? , ChkSuccFlag = ? , ChkFailFlag = ? , BankBackF = ? , BankBackPath = ? , AgentPayRFType = ? , AgentGetRFType = ? , BankType = ? , HeadBankCode = ? , Province = ? , City = ? , County = ? , State = ? WHERE  BankCode = ?");
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBankCode());
			}
			if(this.getBankName() == null || this.getBankName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBankName());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOperator());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getComCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAgentCode());
			}
			if(this.getBankSelfCode() == null || this.getBankSelfCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getBankSelfCode());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAccName());
			}
			if(this.getAccNo() == null || this.getAccNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccNo());
			}
			if(this.getAgentPaySendF() == null || this.getAgentPaySendF().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAgentPaySendF());
			}
			if(this.getAgentPaySendPath() == null || this.getAgentPaySendPath().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentPaySendPath());
			}
			if(this.getAgentPayReceiveF() == null || this.getAgentPayReceiveF().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentPayReceiveF());
			}
			if(this.getAgentPaySavePath() == null || this.getAgentPaySavePath().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentPaySavePath());
			}
			if(this.getAgentPaySuccFlag() == null || this.getAgentPaySuccFlag().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAgentPaySuccFlag());
			}
			if(this.getAgentPayFailFlag() == null || this.getAgentPayFailFlag().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentPayFailFlag());
			}
			if(this.getAgentGetSendF() == null || this.getAgentGetSendF().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentGetSendF());
			}
			if(this.getAgentGetSendPath() == null || this.getAgentGetSendPath().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentGetSendPath());
			}
			if(this.getAgentGetReceiveF() == null || this.getAgentGetReceiveF().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentGetReceiveF());
			}
			if(this.getAgentGetSavePath() == null || this.getAgentGetSavePath().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentGetSavePath());
			}
			if(this.getAgentGetSuccFlag() == null || this.getAgentGetSuccFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentGetSuccFlag());
			}
			if(this.getAgentGetFailFlag() == null || this.getAgentGetFailFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentGetFailFlag());
			}
			if(this.getChkSendF() == null || this.getChkSendF().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getChkSendF());
			}
			if(this.getChkSendPath() == null || this.getChkSendPath().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getChkSendPath());
			}
			if(this.getChkReceiveF() == null || this.getChkReceiveF().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getChkReceiveF());
			}
			if(this.getChkReceivePath() == null || this.getChkReceivePath().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getChkReceivePath());
			}
			if(this.getChkSuccFlag() == null || this.getChkSuccFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getChkSuccFlag());
			}
			if(this.getChkFailFlag() == null || this.getChkFailFlag().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getChkFailFlag());
			}
			if(this.getBankBackF() == null || this.getBankBackF().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getBankBackF());
			}
			if(this.getBankBackPath() == null || this.getBankBackPath().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getBankBackPath());
			}
			if(this.getAgentPayRFType() == null || this.getAgentPayRFType().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getAgentPayRFType());
			}
			if(this.getAgentGetRFType() == null || this.getAgentGetRFType().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getAgentGetRFType());
			}
			if(this.getBankType() == null || this.getBankType().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankType());
			}
			if(this.getHeadBankCode() == null || this.getHeadBankCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getHeadBankCode());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getProvince());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getCity());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getCounty());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getState());
			}
			// set where condition
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBankCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDBank");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LDBank VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBankCode());
			}
			if(this.getBankName() == null || this.getBankName().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getBankName());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOperator());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getComCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getAgentCode());
			}
			if(this.getBankSelfCode() == null || this.getBankSelfCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getBankSelfCode());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAccName());
			}
			if(this.getAccNo() == null || this.getAccNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccNo());
			}
			if(this.getAgentPaySendF() == null || this.getAgentPaySendF().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAgentPaySendF());
			}
			if(this.getAgentPaySendPath() == null || this.getAgentPaySendPath().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentPaySendPath());
			}
			if(this.getAgentPayReceiveF() == null || this.getAgentPayReceiveF().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentPayReceiveF());
			}
			if(this.getAgentPaySavePath() == null || this.getAgentPaySavePath().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getAgentPaySavePath());
			}
			if(this.getAgentPaySuccFlag() == null || this.getAgentPaySuccFlag().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAgentPaySuccFlag());
			}
			if(this.getAgentPayFailFlag() == null || this.getAgentPayFailFlag().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentPayFailFlag());
			}
			if(this.getAgentGetSendF() == null || this.getAgentGetSendF().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getAgentGetSendF());
			}
			if(this.getAgentGetSendPath() == null || this.getAgentGetSendPath().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentGetSendPath());
			}
			if(this.getAgentGetReceiveF() == null || this.getAgentGetReceiveF().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentGetReceiveF());
			}
			if(this.getAgentGetSavePath() == null || this.getAgentGetSavePath().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAgentGetSavePath());
			}
			if(this.getAgentGetSuccFlag() == null || this.getAgentGetSuccFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentGetSuccFlag());
			}
			if(this.getAgentGetFailFlag() == null || this.getAgentGetFailFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentGetFailFlag());
			}
			if(this.getChkSendF() == null || this.getChkSendF().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getChkSendF());
			}
			if(this.getChkSendPath() == null || this.getChkSendPath().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getChkSendPath());
			}
			if(this.getChkReceiveF() == null || this.getChkReceiveF().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getChkReceiveF());
			}
			if(this.getChkReceivePath() == null || this.getChkReceivePath().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getChkReceivePath());
			}
			if(this.getChkSuccFlag() == null || this.getChkSuccFlag().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getChkSuccFlag());
			}
			if(this.getChkFailFlag() == null || this.getChkFailFlag().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getChkFailFlag());
			}
			if(this.getBankBackF() == null || this.getBankBackF().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getBankBackF());
			}
			if(this.getBankBackPath() == null || this.getBankBackPath().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getBankBackPath());
			}
			if(this.getAgentPayRFType() == null || this.getAgentPayRFType().equals("null")) {
				pstmt.setNull(29, 1);
			} else {
				pstmt.setString(29, this.getAgentPayRFType());
			}
			if(this.getAgentGetRFType() == null || this.getAgentGetRFType().equals("null")) {
				pstmt.setNull(30, 1);
			} else {
				pstmt.setString(30, this.getAgentGetRFType());
			}
			if(this.getBankType() == null || this.getBankType().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankType());
			}
			if(this.getHeadBankCode() == null || this.getHeadBankCode().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getHeadBankCode());
			}
			if(this.getProvince() == null || this.getProvince().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getProvince());
			}
			if(this.getCity() == null || this.getCity().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getCity());
			}
			if(this.getCounty() == null || this.getCounty().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getCounty());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getState());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){}
		}

		return true;
	}

	public boolean getInfo()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
			pstmt = con.prepareStatement("SELECT * FROM LDBank WHERE  BankCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBankCode());
			}
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDBankDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ pstmt.close(); } catch( Exception ex1 ) {}

					if (!mflag)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if( i == 0 )
			{
				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	public LDBankSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDBankSet aLDBankSet = new LDBankSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDBank");
			LDBankSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				LDBankSchema s1 = new LDBankSchema();
				s1.setSchema(rs,i);
				aLDBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLDBankSet;
	}

	public LDBankSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDBankSet aLDBankSet = new LDBankSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LDBankSchema s1 = new LDBankSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLDBankSet;
	}

	public LDBankSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDBankSet aLDBankSet = new LDBankSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LDBank");
			LDBankSchema aSchema = this.getSchema();
			sqlObj.setSQLNew(5,aSchema);
			String sql = sqlObj.getSQL();

			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			List tBV = sqlObj.getBV();
			db.setBV(pstmt, tBV);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LDBankSchema s1 = new LDBankSchema();
				s1.setSchema(rs,i);
				aLDBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLDBankSet;
	}

	public LDBankSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LDBankSet aLDBankSet = new LDBankSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LDBankSchema s1 = new LDBankSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLDBankSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LDBank");
			LDBankSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LDBank " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDBankDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
				this.mErrors .addOneError(tError);

				if (!mflag)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (!mflag)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连接
		if (!mflag)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

/**
 * 准备数据查询条件
 * @param strSQL String
 * @return boolean
 */
public boolean prepareData(String strSQL)
{
    if (mResultSet != null)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "prepareData";
        tError.errorMessage = "数据集非空，程序在准备数据集之后，没有关闭！";
        this.mErrors.addOneError(tError);
        return false;
    }

    if (!mflag)
    {
        con = DBConnPool.getConnection();
    }
    try
    {
        mStatement = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        mResultSet = mStatement.executeQuery(StrTool.GBKToUnicode(strSQL));
    }
    catch (Exception e)
    {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "prepareData";
        tError.errorMessage = e.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }

    if (!mflag)
    {
        try
        {
            con.close();
        }
        catch (Exception e)
        {}
    }
    return true;
}

/**
 * 获取数据集
 * @return boolean
 */
public boolean hasMoreData()
{
    boolean flag = true;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return false;
    }
    try
    {
        flag = mResultSet.next();
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "hasMoreData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return false;
    }
    return flag;
}
/**
 * 获取定量数据
 * @return LDBankSet
 */
public LDBankSet getData()
{
    int tCount = 0;
    LDBankSet tLDBankSet = new LDBankSet();
    LDBankSchema tLDBankSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLDBankSchema = new LDBankSchema();
        tLDBankSchema.setSchema(mResultSet, 1);
        tLDBankSet.add(tLDBankSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLDBankSchema = new LDBankSchema();
                tLDBankSchema.setSchema(mResultSet, 1);
                tLDBankSet.add(tLDBankSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "getData";
        tError.errorMessage = ex.toString();
        this.mErrors.addOneError(tError);
        try
        {
            mResultSet.close();
            mResultSet = null;
        }
        catch (Exception ex2)
        {}
        try
        {
            mStatement.close();
            mStatement = null;
        }
        catch (Exception ex3)
        {}
        if (!mflag)
        {
            try
            {
                con.close();
            }
            catch (Exception et)
            {}
        }
        return null;
    }
    return tLDBankSet;
}
/**
 * 关闭数据集
 * @return boolean
 */
public boolean closeData()
{
    boolean flag = true;
    try
    {
        if (null == mResultSet)
        {
            CError tError = new CError();
            tError.moduleName = "LDBankDB";
            tError.functionName = "closeData";
            tError.errorMessage = "数据集已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mResultSet.close();
            mResultSet = null;
        }
    }
    catch (Exception ex2)
    {
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex2.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    try
    {
        if (null == mStatement)
        {
            CError tError = new CError();
            tError.moduleName = "LDBankDB";
            tError.functionName = "closeData";
            tError.errorMessage = "语句已经关闭了！";
            this.mErrors.addOneError(tError);
            flag = false;
        }
        else
        {
            mStatement.close();
            mStatement = null;
        }
    }
    catch (Exception ex3)
    {
        CError tError = new CError();
        tError.moduleName = "LDBankDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LDBankSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDBankSet aLDBankSet = new LDBankSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LDBankSchema s1 = new LDBankSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDBankSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch(Exception ex2) {}
			try{ pstmt.close(); } catch(Exception ex3) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e) {}
		}

		return aLDBankSet;
	}

	public LDBankSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LDBankSet aLDBankSet = new LDBankSet();

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break; 
				}

				LDBankSchema s1 = new LDBankSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LDBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLDBankSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDBankDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ pstmt.close(); } catch( Exception ex3 ) {}

			if (!mflag) {
				try {
					con.close();
				}
				catch(Exception et){}
			}
		}

		if (!mflag) {
			try {
				con.close();
			}
			catch(Exception e){}
		}

		return aLDBankSet; 
	}

}
