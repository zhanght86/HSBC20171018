/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLMainAskSchema;
import com.sinosoft.lis.vschema.LLMainAskSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLMainAskDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLMainAskDBSet extends LLMainAskSet
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
	public LLMainAskDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLMainAsk");
		mflag = true;
	}

	public LLMainAskDBSet()
	{
		db = new DBOper( "LLMainAsk" );
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
			tError.moduleName = "LLMainAskDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLMainAsk WHERE  LogNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getLogNo() == null || this.get(i).getLogNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getLogNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMainAsk");
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
			tError.moduleName = "LLMainAskDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLMainAsk SET  LogNo = ? , LogState = ? , AskType = ? , OtherNo = ? , OtherNoType = ? , AskMode = ? , LogerNo = ? , LogName = ? , LogComp = ? , LogCompNo = ? , LogDate = ? , LogTime = ? , Phone = ? , Mobile = ? , PostCode = ? , AskAddress = ? , Email = ? , AnswerType = ? , AnswerMode = ? , SendFlag = ? , SendDate = ? , SwitchCom = ? , SwitchDate = ? , SwitchTime = ? , Despatcher = ? , CNSDate = ? , ExpectRevertDate = ? , ExpectRevertTime = ? , ExpertFlag = ? , ReplyFDate = ? , DealFDate = ? , CNSOperator = ? , RevertPeriod = ? , DealPeriod = ? , Remark = ? , AvaiFlag = ? , NotAvaliReason = ? , Operator = ? , MngCom = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? WHERE  LogNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getLogNo() == null || this.get(i).getLogNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getLogNo());
			}
			if(this.get(i).getLogState() == null || this.get(i).getLogState().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getLogState());
			}
			if(this.get(i).getAskType() == null || this.get(i).getAskType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAskType());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOtherNoType());
			}
			if(this.get(i).getAskMode() == null || this.get(i).getAskMode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAskMode());
			}
			if(this.get(i).getLogerNo() == null || this.get(i).getLogerNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getLogerNo());
			}
			if(this.get(i).getLogName() == null || this.get(i).getLogName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getLogName());
			}
			if(this.get(i).getLogComp() == null || this.get(i).getLogComp().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getLogComp());
			}
			if(this.get(i).getLogCompNo() == null || this.get(i).getLogCompNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getLogCompNo());
			}
			if(this.get(i).getLogDate() == null || this.get(i).getLogDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getLogDate()));
			}
			if(this.get(i).getLogTime() == null || this.get(i).getLogTime().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getLogTime());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPhone());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMobile());
			}
			if(this.get(i).getPostCode() == null || this.get(i).getPostCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPostCode());
			}
			if(this.get(i).getAskAddress() == null || this.get(i).getAskAddress().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAskAddress());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getEmail());
			}
			if(this.get(i).getAnswerType() == null || this.get(i).getAnswerType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAnswerType());
			}
			if(this.get(i).getAnswerMode() == null || this.get(i).getAnswerMode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAnswerMode());
			}
			if(this.get(i).getSendFlag() == null || this.get(i).getSendFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSendFlag());
			}
			if(this.get(i).getSendDate() == null || this.get(i).getSendDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getSendDate()));
			}
			if(this.get(i).getSwitchCom() == null || this.get(i).getSwitchCom().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getSwitchCom());
			}
			if(this.get(i).getSwitchDate() == null || this.get(i).getSwitchDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getSwitchDate()));
			}
			if(this.get(i).getSwitchTime() == null || this.get(i).getSwitchTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSwitchTime());
			}
			if(this.get(i).getDespatcher() == null || this.get(i).getDespatcher().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getDespatcher());
			}
			if(this.get(i).getCNSDate() == null || this.get(i).getCNSDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getCNSDate()));
			}
			if(this.get(i).getExpectRevertDate() == null || this.get(i).getExpectRevertDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getExpectRevertDate()));
			}
			if(this.get(i).getExpectRevertTime() == null || this.get(i).getExpectRevertTime().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getExpectRevertTime());
			}
			if(this.get(i).getExpertFlag() == null || this.get(i).getExpertFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getExpertFlag());
			}
			if(this.get(i).getReplyFDate() == null || this.get(i).getReplyFDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getReplyFDate()));
			}
			if(this.get(i).getDealFDate() == null || this.get(i).getDealFDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getDealFDate()));
			}
			if(this.get(i).getCNSOperator() == null || this.get(i).getCNSOperator().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getCNSOperator());
			}
			pstmt.setInt(33, this.get(i).getRevertPeriod());
			pstmt.setInt(34, this.get(i).getDealPeriod());
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getRemark());
			}
			if(this.get(i).getAvaiFlag() == null || this.get(i).getAvaiFlag().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAvaiFlag());
			}
			if(this.get(i).getNotAvaliReason() == null || this.get(i).getNotAvaliReason().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getNotAvaliReason());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOperator());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getMngCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(42,null);
			} else {
				pstmt.setDate(42, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getLogNo() == null || this.get(i).getLogNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getLogNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMainAsk");
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
			tError.moduleName = "LLMainAskDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLMainAsk VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getLogNo() == null || this.get(i).getLogNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getLogNo());
			}
			if(this.get(i).getLogState() == null || this.get(i).getLogState().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getLogState());
			}
			if(this.get(i).getAskType() == null || this.get(i).getAskType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAskType());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getOtherNoType());
			}
			if(this.get(i).getAskMode() == null || this.get(i).getAskMode().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAskMode());
			}
			if(this.get(i).getLogerNo() == null || this.get(i).getLogerNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getLogerNo());
			}
			if(this.get(i).getLogName() == null || this.get(i).getLogName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getLogName());
			}
			if(this.get(i).getLogComp() == null || this.get(i).getLogComp().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getLogComp());
			}
			if(this.get(i).getLogCompNo() == null || this.get(i).getLogCompNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getLogCompNo());
			}
			if(this.get(i).getLogDate() == null || this.get(i).getLogDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getLogDate()));
			}
			if(this.get(i).getLogTime() == null || this.get(i).getLogTime().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getLogTime());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getPhone());
			}
			if(this.get(i).getMobile() == null || this.get(i).getMobile().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getMobile());
			}
			if(this.get(i).getPostCode() == null || this.get(i).getPostCode().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPostCode());
			}
			if(this.get(i).getAskAddress() == null || this.get(i).getAskAddress().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAskAddress());
			}
			if(this.get(i).getEmail() == null || this.get(i).getEmail().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getEmail());
			}
			if(this.get(i).getAnswerType() == null || this.get(i).getAnswerType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getAnswerType());
			}
			if(this.get(i).getAnswerMode() == null || this.get(i).getAnswerMode().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAnswerMode());
			}
			if(this.get(i).getSendFlag() == null || this.get(i).getSendFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getSendFlag());
			}
			if(this.get(i).getSendDate() == null || this.get(i).getSendDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getSendDate()));
			}
			if(this.get(i).getSwitchCom() == null || this.get(i).getSwitchCom().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getSwitchCom());
			}
			if(this.get(i).getSwitchDate() == null || this.get(i).getSwitchDate().equals("null")) {
				pstmt.setDate(23,null);
			} else {
				pstmt.setDate(23, Date.valueOf(this.get(i).getSwitchDate()));
			}
			if(this.get(i).getSwitchTime() == null || this.get(i).getSwitchTime().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getSwitchTime());
			}
			if(this.get(i).getDespatcher() == null || this.get(i).getDespatcher().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getDespatcher());
			}
			if(this.get(i).getCNSDate() == null || this.get(i).getCNSDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getCNSDate()));
			}
			if(this.get(i).getExpectRevertDate() == null || this.get(i).getExpectRevertDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getExpectRevertDate()));
			}
			if(this.get(i).getExpectRevertTime() == null || this.get(i).getExpectRevertTime().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getExpectRevertTime());
			}
			if(this.get(i).getExpertFlag() == null || this.get(i).getExpertFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getExpertFlag());
			}
			if(this.get(i).getReplyFDate() == null || this.get(i).getReplyFDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getReplyFDate()));
			}
			if(this.get(i).getDealFDate() == null || this.get(i).getDealFDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getDealFDate()));
			}
			if(this.get(i).getCNSOperator() == null || this.get(i).getCNSOperator().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getCNSOperator());
			}
			pstmt.setInt(33, this.get(i).getRevertPeriod());
			pstmt.setInt(34, this.get(i).getDealPeriod());
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getRemark());
			}
			if(this.get(i).getAvaiFlag() == null || this.get(i).getAvaiFlag().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getAvaiFlag());
			}
			if(this.get(i).getNotAvaliReason() == null || this.get(i).getNotAvaliReason().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getNotAvaliReason());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOperator());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getMngCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(40,null);
			} else {
				pstmt.setDate(40, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(42,null);
			} else {
				pstmt.setDate(42, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLMainAsk");
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
			tError.moduleName = "LLMainAskDBSet";
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
