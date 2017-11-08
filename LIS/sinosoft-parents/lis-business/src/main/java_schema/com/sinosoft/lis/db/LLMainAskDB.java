/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LLMainAskSchema;
import com.sinosoft.lis.vschema.LLMainAskSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLMainAskDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLMainAskDB extends LLMainAskSchema
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
	public LLMainAskDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLMainAsk" );
		mflag = true;
	}

	public LLMainAskDB()
	{
		con = null;
		db = new DBOper( "LLMainAsk" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLMainAskSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLMainAskSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLMainAsk WHERE  LogNo = ?");
			if(this.getLogNo() == null || this.getLogNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getLogNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMainAsk");
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
		SQLString sqlObj = new SQLString("LLMainAsk");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLMainAsk SET  LogNo = ? , LogState = ? , AskType = ? , OtherNo = ? , OtherNoType = ? , AskMode = ? , LogerNo = ? , LogName = ? , LogComp = ? , LogCompNo = ? , LogDate = ? , LogTime = ? , Phone = ? , Mobile = ? , PostCode = ? , AskAddress = ? , Email = ? , AnswerType = ? , AnswerMode = ? , SendFlag = ? , SendDate = ? , SwitchCom = ? , SwitchDate = ? , SwitchTime = ? , Despatcher = ? , CNSDate = ? , ExpectRevertDate = ? , ExpectRevertTime = ? , ExpertFlag = ? , ReplyFDate = ? , DealFDate = ? , CNSOperator = ? , RevertPeriod = ? , DealPeriod = ? , Remark = ? , AvaiFlag = ? , NotAvaliReason = ? , Operator = ? , MngCom = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  LogNo = ?");
			if(this.getLogNo() == null || this.getLogNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getLogNo());
			}
			if(this.getLogState() == null || this.getLogState().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getLogState());
			}
			if(this.getAskType() == null || this.getAskType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAskType());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getOtherNoType());
			}
			if(this.getAskMode() == null || this.getAskMode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getAskMode());
			}
			if(this.getLogerNo() == null || this.getLogerNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getLogerNo());
			}
			if(this.getLogName() == null || this.getLogName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getLogName());
			}
			if(this.getLogComp() == null || this.getLogComp().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getLogComp());
			}
			if(this.getLogCompNo() == null || this.getLogCompNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getLogCompNo());
			}
			if(this.getLogDate() == null || this.getLogDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getLogDate()));
			}
			if(this.getLogTime() == null || this.getLogTime().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getLogTime());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPhone());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getMobile());
			}
			if(this.getPostCode() == null || this.getPostCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPostCode());
			}
			if(this.getAskAddress() == null || this.getAskAddress().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAskAddress());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEmail());
			}
			if(this.getAnswerType() == null || this.getAnswerType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAnswerType());
			}
			if(this.getAnswerMode() == null || this.getAnswerMode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAnswerMode());
			}
			if(this.getSendFlag() == null || this.getSendFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSendFlag());
			}
			if(this.getSendDate() == null || this.getSendDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getSendDate()));
			}
			if(this.getSwitchCom() == null || this.getSwitchCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSwitchCom());
			}
			if(this.getSwitchDate() == null || this.getSwitchDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getSwitchDate()));
			}
			if(this.getSwitchTime() == null || this.getSwitchTime().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSwitchTime());
			}
			if(this.getDespatcher() == null || this.getDespatcher().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDespatcher());
			}
			if(this.getCNSDate() == null || this.getCNSDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getCNSDate()));
			}
			if(this.getExpectRevertDate() == null || this.getExpectRevertDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getExpectRevertDate()));
			}
			if(this.getExpectRevertTime() == null || this.getExpectRevertTime().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getExpectRevertTime());
			}
			if(this.getExpertFlag() == null || this.getExpertFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getExpertFlag());
			}
			if(this.getReplyFDate() == null || this.getReplyFDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getReplyFDate()));
			}
			if(this.getDealFDate() == null || this.getDealFDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getDealFDate()));
			}
			if(this.getCNSOperator() == null || this.getCNSOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getCNSOperator());
			}
			pstmt.setInt(33, this.getRevertPeriod());
			pstmt.setInt(34, this.getDealPeriod());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRemark());
			}
			if(this.getAvaiFlag() == null || this.getAvaiFlag().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAvaiFlag());
			}
			if(this.getNotAvaliReason() == null || this.getNotAvaliReason().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getNotAvaliReason());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOperator());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getMngCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(42, 91);
			} else {
				pstmt.setDate(42, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getModifyTime());
			}
			// set where condition
			if(this.getLogNo() == null || this.getLogNo().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getLogNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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
		SQLString sqlObj = new SQLString("LLMainAsk");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLMainAsk VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getLogNo() == null || this.getLogNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getLogNo());
			}
			if(this.getLogState() == null || this.getLogState().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getLogState());
			}
			if(this.getAskType() == null || this.getAskType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAskType());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getOtherNoType());
			}
			if(this.getAskMode() == null || this.getAskMode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getAskMode());
			}
			if(this.getLogerNo() == null || this.getLogerNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getLogerNo());
			}
			if(this.getLogName() == null || this.getLogName().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getLogName());
			}
			if(this.getLogComp() == null || this.getLogComp().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getLogComp());
			}
			if(this.getLogCompNo() == null || this.getLogCompNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getLogCompNo());
			}
			if(this.getLogDate() == null || this.getLogDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getLogDate()));
			}
			if(this.getLogTime() == null || this.getLogTime().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getLogTime());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getPhone());
			}
			if(this.getMobile() == null || this.getMobile().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getMobile());
			}
			if(this.getPostCode() == null || this.getPostCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPostCode());
			}
			if(this.getAskAddress() == null || this.getAskAddress().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAskAddress());
			}
			if(this.getEmail() == null || this.getEmail().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getEmail());
			}
			if(this.getAnswerType() == null || this.getAnswerType().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAnswerType());
			}
			if(this.getAnswerMode() == null || this.getAnswerMode().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAnswerMode());
			}
			if(this.getSendFlag() == null || this.getSendFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getSendFlag());
			}
			if(this.getSendDate() == null || this.getSendDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getSendDate()));
			}
			if(this.getSwitchCom() == null || this.getSwitchCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSwitchCom());
			}
			if(this.getSwitchDate() == null || this.getSwitchDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getSwitchDate()));
			}
			if(this.getSwitchTime() == null || this.getSwitchTime().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getSwitchTime());
			}
			if(this.getDespatcher() == null || this.getDespatcher().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getDespatcher());
			}
			if(this.getCNSDate() == null || this.getCNSDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getCNSDate()));
			}
			if(this.getExpectRevertDate() == null || this.getExpectRevertDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getExpectRevertDate()));
			}
			if(this.getExpectRevertTime() == null || this.getExpectRevertTime().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getExpectRevertTime());
			}
			if(this.getExpertFlag() == null || this.getExpertFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getExpertFlag());
			}
			if(this.getReplyFDate() == null || this.getReplyFDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getReplyFDate()));
			}
			if(this.getDealFDate() == null || this.getDealFDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getDealFDate()));
			}
			if(this.getCNSOperator() == null || this.getCNSOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getCNSOperator());
			}
			pstmt.setInt(33, this.getRevertPeriod());
			pstmt.setInt(34, this.getDealPeriod());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getRemark());
			}
			if(this.getAvaiFlag() == null || this.getAvaiFlag().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAvaiFlag());
			}
			if(this.getNotAvaliReason() == null || this.getNotAvaliReason().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getNotAvaliReason());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOperator());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getMngCom());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(42, 91);
			} else {
				pstmt.setDate(42, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLMainAsk WHERE  LogNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getLogNo() == null || this.getLogNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getLogNo());
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
					tError.moduleName = "LLMainAskDB";
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
			tError.moduleName = "LLMainAskDB";
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

	public LLMainAskSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLMainAskSet aLLMainAskSet = new LLMainAskSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLMainAsk");
			LLMainAskSchema aSchema = this.getSchema();
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
				LLMainAskSchema s1 = new LLMainAskSchema();
				s1.setSchema(rs,i);
				aLLMainAskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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

		return aLLMainAskSet;
	}

	public LLMainAskSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLMainAskSet aLLMainAskSet = new LLMainAskSet();

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
				LLMainAskSchema s1 = new LLMainAskSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLMainAskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLMainAskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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

		return aLLMainAskSet;
	}

	public LLMainAskSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLMainAskSet aLLMainAskSet = new LLMainAskSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLMainAsk");
			LLMainAskSchema aSchema = this.getSchema();
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

				LLMainAskSchema s1 = new LLMainAskSchema();
				s1.setSchema(rs,i);
				aLLMainAskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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

		return aLLMainAskSet;
	}

	public LLMainAskSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLMainAskSet aLLMainAskSet = new LLMainAskSet();

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

				LLMainAskSchema s1 = new LLMainAskSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLMainAskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLMainAskSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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

		return aLLMainAskSet;
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
			SQLString sqlObj = new SQLString("LLMainAsk");
			LLMainAskSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLMainAsk " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLMainAskDB";
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
			tError.moduleName = "LLMainAskDB";
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
        tError.moduleName = "LLMainAskDB";
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
        tError.moduleName = "LLMainAskDB";
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
        tError.moduleName = "LLMainAskDB";
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
        tError.moduleName = "LLMainAskDB";
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
 * @return LLMainAskSet
 */
public LLMainAskSet getData()
{
    int tCount = 0;
    LLMainAskSet tLLMainAskSet = new LLMainAskSet();
    LLMainAskSchema tLLMainAskSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLMainAskDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLMainAskSchema = new LLMainAskSchema();
        tLLMainAskSchema.setSchema(mResultSet, 1);
        tLLMainAskSet.add(tLLMainAskSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLMainAskSchema = new LLMainAskSchema();
                tLLMainAskSchema.setSchema(mResultSet, 1);
                tLLMainAskSet.add(tLLMainAskSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLMainAskDB";
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
    return tLLMainAskSet;
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
            tError.moduleName = "LLMainAskDB";
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
        tError.moduleName = "LLMainAskDB";
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
            tError.moduleName = "LLMainAskDB";
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
        tError.moduleName = "LLMainAskDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLMainAskSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLMainAskSet aLLMainAskSet = new LLMainAskSet();

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
				LLMainAskSchema s1 = new LLMainAskSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLMainAskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLMainAskSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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

		return aLLMainAskSet;
	}

	public LLMainAskSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLMainAskSet aLLMainAskSet = new LLMainAskSet();

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

				LLMainAskSchema s1 = new LLMainAskSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLMainAskDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLMainAskSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMainAskDB";
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

		return aLLMainAskSet; 
	}

}
