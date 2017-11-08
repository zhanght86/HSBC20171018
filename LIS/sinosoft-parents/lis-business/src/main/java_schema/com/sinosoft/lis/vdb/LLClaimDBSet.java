/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLClaimDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimDBSet extends LLClaimSet
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
	public LLClaimDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLClaim");
		mflag = true;
	}

	public LLClaimDBSet()
	{
		db = new DBOper( "LLClaim" );
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
			tError.moduleName = "LLClaimDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLClaim WHERE  ClmNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaim");
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
			tError.moduleName = "LLClaimDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLClaim SET  ClmNo = ? , RgtNo = ? , CaseNo = ? , GetDutyKind = ? , ClmState = ? , StandPay = ? , BeforePay = ? , BalancePay = ? , RealPay = ? , DeclinePay = ? , GiveType = ? , GiveTypeDesc = ? , ClmUWer = ? , DeclineNo = ? , EndCaseDate = ? , CasePayType = ? , CheckType = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ConBalFlag = ? , ConDealFlag = ? , BackFlag = ? , BackNo = ? , EndCaseFlag = ? , Currency = ? , AuditPopedom = ? , UWState = ? , CalFlag = ? , ComCode = ? , ModifyOperator = ? WHERE  ClmNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getClmState() == null || this.get(i).getClmState().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmState());
			}
			pstmt.setDouble(6, this.get(i).getStandPay());
			pstmt.setDouble(7, this.get(i).getBeforePay());
			pstmt.setDouble(8, this.get(i).getBalancePay());
			pstmt.setDouble(9, this.get(i).getRealPay());
			pstmt.setDouble(10, this.get(i).getDeclinePay());
			if(this.get(i).getGiveType() == null || this.get(i).getGiveType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getGiveType());
			}
			if(this.get(i).getGiveTypeDesc() == null || this.get(i).getGiveTypeDesc().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getGiveTypeDesc());
			}
			if(this.get(i).getClmUWer() == null || this.get(i).getClmUWer().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getClmUWer());
			}
			if(this.get(i).getDeclineNo() == null || this.get(i).getDeclineNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getDeclineNo());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getCasePayType() == null || this.get(i).getCasePayType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCasePayType());
			}
			if(this.get(i).getCheckType() == null || this.get(i).getCheckType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCheckType());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getModifyTime());
			}
			if(this.get(i).getConBalFlag() == null || this.get(i).getConBalFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getConBalFlag());
			}
			if(this.get(i).getConDealFlag() == null || this.get(i).getConDealFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getConDealFlag());
			}
			if(this.get(i).getBackFlag() == null || this.get(i).getBackFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getBackFlag());
			}
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getBackNo());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCurrency());
			}
			if(this.get(i).getAuditPopedom() == null || this.get(i).getAuditPopedom().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAuditPopedom());
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getUWState());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getCalFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getClmNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaim");
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
			tError.moduleName = "LLClaimDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLClaim VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getRgtNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getClmState() == null || this.get(i).getClmState().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getClmState());
			}
			pstmt.setDouble(6, this.get(i).getStandPay());
			pstmt.setDouble(7, this.get(i).getBeforePay());
			pstmt.setDouble(8, this.get(i).getBalancePay());
			pstmt.setDouble(9, this.get(i).getRealPay());
			pstmt.setDouble(10, this.get(i).getDeclinePay());
			if(this.get(i).getGiveType() == null || this.get(i).getGiveType().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getGiveType());
			}
			if(this.get(i).getGiveTypeDesc() == null || this.get(i).getGiveTypeDesc().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getGiveTypeDesc());
			}
			if(this.get(i).getClmUWer() == null || this.get(i).getClmUWer().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getClmUWer());
			}
			if(this.get(i).getDeclineNo() == null || this.get(i).getDeclineNo().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getDeclineNo());
			}
			if(this.get(i).getEndCaseDate() == null || this.get(i).getEndCaseDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getEndCaseDate()));
			}
			if(this.get(i).getCasePayType() == null || this.get(i).getCasePayType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getCasePayType());
			}
			if(this.get(i).getCheckType() == null || this.get(i).getCheckType().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getCheckType());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getModifyTime());
			}
			if(this.get(i).getConBalFlag() == null || this.get(i).getConBalFlag().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getConBalFlag());
			}
			if(this.get(i).getConDealFlag() == null || this.get(i).getConDealFlag().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getConDealFlag());
			}
			if(this.get(i).getBackFlag() == null || this.get(i).getBackFlag().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getBackFlag());
			}
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getBackNo());
			}
			if(this.get(i).getEndCaseFlag() == null || this.get(i).getEndCaseFlag().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getEndCaseFlag());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCurrency());
			}
			if(this.get(i).getAuditPopedom() == null || this.get(i).getAuditPopedom().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getAuditPopedom());
			}
			if(this.get(i).getUWState() == null || this.get(i).getUWState().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getUWState());
			}
			if(this.get(i).getCalFlag() == null || this.get(i).getCalFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getCalFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLClaim");
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
			tError.moduleName = "LLClaimDBSet";
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