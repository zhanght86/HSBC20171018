/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.LJSPayContSchema;
import com.sinosoft.lis.vschema.LJSPayContSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJSPayContDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJSPayContDB extends LJSPayContSchema
{
	private static Logger logger = Logger.getLogger(LJSPayContDB.class);
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
	public LJSPayContDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LJSPayCont" );
		mflag = true;
	}

	public LJSPayContDB()
	{
		con = null;
		db = new DBOper( "LJSPayCont" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LJSPayContSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LJSPayContSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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
			pstmt = con.prepareStatement("DELETE FROM LJSPayCont WHERE  ContNo = ? AND GetNoticeNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGetNoticeNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJSPayCont");
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
		SQLString sqlObj = new SQLString("LJSPayCont");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LJSPayCont SET  ContNo = ? , PayCount = ? , AppntNo = ? , GetNoticeNo = ? , SumDuePayMoney = ? , SumActuPayMoney = ? , PayIntv = ? , PayDate = ? , PayType = ? , LastPayToDate = ? , CurPayToDate = ? , ApproveCode = ? , ApproveDate = ? , SerialNo = ? , InputFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , RiskCode = ? , AgentCode = ? , AgentGroup = ? , PayTypeFlag = ? , GrpContNo = ? , EnterAccDate = ? , ConfDate = ? , CustomerName = ? , IDType = ? , IDNo = ? , BankCode = ? , BankAccNo = ? , AccName = ? , BankOnTheWayFlag = ? , BankSuccFlag = ? , SendBankCount = ? , State = ? , SendRelaNo = ? , ComCode = ? , ModifyOperator = ? , NetAmount = ? , TaxAmount = ? , Tax = ? WHERE  ContNo = ? AND GetNoticeNo = ?");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.setInt(2, this.getPayCount());
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAppntNo());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGetNoticeNo());
			}
			pstmt.setDouble(5, this.getSumDuePayMoney());
			pstmt.setDouble(6, this.getSumActuPayMoney());
			pstmt.setInt(7, this.getPayIntv());
			if(this.getPayDate() == null || this.getPayDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getPayDate()));
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getPayType());
			}
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getApproveDate()));
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getSerialNo());
			}
			if(this.getInputFlag() == null || this.getInputFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getInputFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getModifyTime());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAgentType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getRiskCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAgentGroup());
			}
			if(this.getPayTypeFlag() == null || this.getPayTypeFlag().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getPayTypeFlag());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getGrpContNo());
			}
			if(this.getEnterAccDate() == null || this.getEnterAccDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getEnterAccDate()));
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getConfDate()));
			}
			if(this.getCustomerName() == null || this.getCustomerName().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getCustomerName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getIDNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAccName());
			}
			if(this.getBankOnTheWayFlag() == null || this.getBankOnTheWayFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBankOnTheWayFlag());
			}
			if(this.getBankSuccFlag() == null || this.getBankSuccFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getBankSuccFlag());
			}
			pstmt.setInt(39, this.getSendBankCount());
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getState());
			}
			if(this.getSendRelaNo() == null || this.getSendRelaNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getSendRelaNo());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getModifyOperator());
			}
			pstmt.setDouble(44, this.getNetAmount());
			pstmt.setDouble(45, this.getTaxAmount());
			pstmt.setDouble(46, this.getTax());
			// set where condition
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getContNo());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getGetNoticeNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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
		SQLString sqlObj = new SQLString("LJSPayCont");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LJSPayCont VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			pstmt.setInt(2, this.getPayCount());
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getAppntNo());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGetNoticeNo());
			}
			pstmt.setDouble(5, this.getSumDuePayMoney());
			pstmt.setDouble(6, this.getSumActuPayMoney());
			pstmt.setInt(7, this.getPayIntv());
			if(this.getPayDate() == null || this.getPayDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getPayDate()));
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getPayType());
			}
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(13, 91);
			} else {
				pstmt.setDate(13, Date.valueOf(this.getApproveDate()));
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getSerialNo());
			}
			if(this.getInputFlag() == null || this.getInputFlag().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getInputFlag());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(18, 1);
			} else {
				pstmt.setString(18, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(19, 91);
			} else {
				pstmt.setDate(19, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getModifyTime());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAgentType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getRiskCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAgentGroup());
			}
			if(this.getPayTypeFlag() == null || this.getPayTypeFlag().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getPayTypeFlag());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getGrpContNo());
			}
			if(this.getEnterAccDate() == null || this.getEnterAccDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getEnterAccDate()));
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getConfDate()));
			}
			if(this.getCustomerName() == null || this.getCustomerName().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getCustomerName());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getIDNo());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getAccName());
			}
			if(this.getBankOnTheWayFlag() == null || this.getBankOnTheWayFlag().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBankOnTheWayFlag());
			}
			if(this.getBankSuccFlag() == null || this.getBankSuccFlag().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getBankSuccFlag());
			}
			pstmt.setInt(39, this.getSendBankCount());
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getState());
			}
			if(this.getSendRelaNo() == null || this.getSendRelaNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getSendRelaNo());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getModifyOperator());
			}
			pstmt.setDouble(44, this.getNetAmount());
			pstmt.setDouble(45, this.getTaxAmount());
			pstmt.setDouble(46, this.getTax());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LJSPayCont WHERE  ContNo = ? AND GetNoticeNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getContNo());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getGetNoticeNo());
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
					tError.moduleName = "LJSPayContDB";
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
			tError.moduleName = "LJSPayContDB";
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

	public LJSPayContSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJSPayContSet aLJSPayContSet = new LJSPayContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJSPayCont");
			LJSPayContSchema aSchema = this.getSchema();
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
				LJSPayContSchema s1 = new LJSPayContSchema();
				s1.setSchema(rs,i);
				aLJSPayContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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

		return aLJSPayContSet;
	}

	public LJSPayContSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJSPayContSet aLJSPayContSet = new LJSPayContSet();

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
				LJSPayContSchema s1 = new LJSPayContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJSPayContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJSPayContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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

		return aLJSPayContSet;
	}

	public LJSPayContSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJSPayContSet aLJSPayContSet = new LJSPayContSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJSPayCont");
			LJSPayContSchema aSchema = this.getSchema();
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

				LJSPayContSchema s1 = new LJSPayContSchema();
				s1.setSchema(rs,i);
				aLJSPayContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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

		return aLJSPayContSet;
	}

	public LJSPayContSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJSPayContSet aLJSPayContSet = new LJSPayContSet();

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

				LJSPayContSchema s1 = new LJSPayContSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJSPayContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJSPayContSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
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

		return aLJSPayContSet;
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
			SQLString sqlObj = new SQLString("LJSPayCont");
			LJSPayContSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LJSPayCont " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LJSPayContDB";
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
			tError.moduleName = "LJSPayContDB";
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
        tError.moduleName = "LJSPayContDB";
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
        tError.moduleName = "LJSPayContDB";
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
        tError.moduleName = "LJSPayContDB";
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
        tError.moduleName = "LJSPayContDB";
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
 * @return LJSPayContSet
 */
public LJSPayContSet getData()
{
    int tCount = 0;
    LJSPayContSet tLJSPayContSet = new LJSPayContSet();
    LJSPayContSchema tLJSPayContSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LJSPayContDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLJSPayContSchema = new LJSPayContSchema();
        tLJSPayContSchema.setSchema(mResultSet, 1);
        tLJSPayContSet.add(tLJSPayContSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLJSPayContSchema = new LJSPayContSchema();
                tLJSPayContSchema.setSchema(mResultSet, 1);
                tLJSPayContSet.add(tLJSPayContSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LJSPayContDB";
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
    return tLJSPayContSet;
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
            tError.moduleName = "LJSPayContDB";
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
        tError.moduleName = "LJSPayContDB";
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
            tError.moduleName = "LJSPayContDB";
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
        tError.moduleName = "LJSPayContDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LJSPayContSet executeQuery(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LJSPayContSet aLJSPayContSet = new LJSPayContSet();

		if (!mflag) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);

			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;
				LJSPayContSchema s1 = new LJSPayContSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJSPayContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLJSPayContSet.add(s1);
			}
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close();
			} catch (Exception ex1) {
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try {
				rs.close();
			} catch (Exception ex2) {
			}
			try {
				pstmt.close();
			} catch (Exception ex3) {
			}

			if (!mflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
		}

		if (!mflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

		return aLJSPayContSet;
	}

	public LJSPayContSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LJSPayContSet aLJSPayContSet = new LJSPayContSet();

		if (!mflag) {
			con = DBConnPool.getConnection();
		}

		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sqlbv.sql()),ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			sqlbv.setParameters(pstmt);

			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				i++;

				if (i < nStart) {
					continue;
				}

				if (i >= nStart + nCount) {
					break;
				}

				LJSPayContSchema s1 = new LJSPayContSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJSPayContDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLJSPayContSet.add(s1);
			}
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				pstmt.close(); 
			} catch (Exception ex1) {
			}
		}
		catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJSPayContDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);

			try {
				rs.close(); 
			} catch (Exception ex2) {
			}
			try {
				pstmt.close();
			} catch (Exception ex3) {
			}

			if (!mflag) {
				try {
					con.close();
				} catch (Exception et) {
				}
			}
		}

		if (!mflag) {
			try {
				con.close();
			} catch (Exception e) {
			}
		}

		return aLJSPayContSet;
	}

}
