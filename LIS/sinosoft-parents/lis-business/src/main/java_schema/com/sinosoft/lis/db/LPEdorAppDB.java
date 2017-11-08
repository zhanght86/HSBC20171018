/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LPEdorAppDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPEdorAppDB extends LPEdorAppSchema
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
	public LPEdorAppDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LPEdorApp" );
		mflag = true;
	}

	public LPEdorAppDB()
	{
		con = null;
		db = new DBOper( "LPEdorApp" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LPEdorAppSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LPEdorAppSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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
			pstmt = con.prepareStatement("DELETE FROM LPEdorApp WHERE  EdorAcceptNo = ?");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LPEdorApp");
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
		SQLString sqlObj = new SQLString("LPEdorApp");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LPEdorApp SET  EdorAcceptNo = ? , OtherNo = ? , OtherNoType = ? , EdorAppName = ? , AppType = ? , ManageCom = ? , ChgPrem = ? , ChgAmnt = ? , ChgGetAmnt = ? , GetMoney = ? , GetInterest = ? , EdorAppDate = ? , EdorState = ? , BankCode = ? , BankAccNo = ? , AccName = ? , PostalAddress = ? , ZipCode = ? , Phone = ? , PrintFlag = ? , AppGrade = ? , UWState = ? , UWGrade = ? , UWOperator = ? , UWDate = ? , UWTime = ? , ConfOperator = ? , ConfDate = ? , ConfTime = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , ApproveFlag = ? , ApproveOperator = ? , ApproveDate = ? , ApproveTime = ? , PayGetName = ? , PersonID = ? , PayForm = ? , GetForm = ? , ApproveGrade = ? , AppPreGrade = ? , EdorConfNo = ? , BehalfName = ? , BehalfIDType = ? , BehalfIDNo = ? , BehalfPhone = ? , BehalfCode = ? , EdorAppPhone = ? , BehalfCodeCom = ? , SwitchChnlType = ? , SwitchChnlName = ? , Currency = ? , ScanManageCom = ? , ScanOperator = ? , ScanDate = ? , ScanTime = ? , AcceptOperator = ? , AcceptDate = ? , AcceptTime = ? , AppDate = ? , ReceiveDate = ? , ListCheckFlag = ? , ComCode = ? , ModifyOperator = ? , AcceptWorkdays = ? WHERE  EdorAcceptNo = ?");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOtherNoType());
			}
			if(this.getEdorAppName() == null || this.getEdorAppName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEdorAppName());
			}
			if(this.getAppType() == null || this.getAppType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAppType());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getManageCom());
			}
			pstmt.setDouble(7, this.getChgPrem());
			pstmt.setDouble(8, this.getChgAmnt());
			pstmt.setDouble(9, this.getChgGetAmnt());
			pstmt.setDouble(10, this.getGetMoney());
			pstmt.setDouble(11, this.getGetInterest());
			if(this.getEdorAppDate() == null || this.getEdorAppDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getEdorAppDate()));
			}
			if(this.getEdorState() == null || this.getEdorState().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getEdorState());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAccName());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPhone());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPrintFlag());
			}
			if(this.getAppGrade() == null || this.getAppGrade().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAppGrade());
			}
			if(this.getUWState() == null || this.getUWState().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getUWState());
			}
			if(this.getUWGrade() == null || this.getUWGrade().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getUWGrade());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getUWTime());
			}
			if(this.getConfOperator() == null || this.getConfOperator().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getConfOperator());
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getConfDate()));
			}
			if(this.getConfTime() == null || this.getConfTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getConfTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getModifyTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getApproveFlag());
			}
			if(this.getApproveOperator() == null || this.getApproveOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getApproveOperator());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getApproveTime());
			}
			if(this.getPayGetName() == null || this.getPayGetName().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getPayGetName());
			}
			if(this.getPersonID() == null || this.getPersonID().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPersonID());
			}
			if(this.getPayForm() == null || this.getPayForm().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getPayForm());
			}
			if(this.getGetForm() == null || this.getGetForm().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getGetForm());
			}
			if(this.getApproveGrade() == null || this.getApproveGrade().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getApproveGrade());
			}
			if(this.getAppPreGrade() == null || this.getAppPreGrade().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAppPreGrade());
			}
			if(this.getEdorConfNo() == null || this.getEdorConfNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getEdorConfNo());
			}
			if(this.getBehalfName() == null || this.getBehalfName().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBehalfName());
			}
			if(this.getBehalfIDType() == null || this.getBehalfIDType().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getBehalfIDType());
			}
			if(this.getBehalfIDNo() == null || this.getBehalfIDNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getBehalfIDNo());
			}
			if(this.getBehalfPhone() == null || this.getBehalfPhone().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getBehalfPhone());
			}
			if(this.getBehalfCode() == null || this.getBehalfCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getBehalfCode());
			}
			if(this.getEdorAppPhone() == null || this.getEdorAppPhone().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getEdorAppPhone());
			}
			if(this.getBehalfCodeCom() == null || this.getBehalfCodeCom().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getBehalfCodeCom());
			}
			if(this.getSwitchChnlType() == null || this.getSwitchChnlType().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getSwitchChnlType());
			}
			if(this.getSwitchChnlName() == null || this.getSwitchChnlName().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getSwitchChnlName());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getCurrency());
			}
			if(this.getScanManageCom() == null || this.getScanManageCom().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getScanManageCom());
			}
			if(this.getScanOperator() == null || this.getScanOperator().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getScanOperator());
			}
			if(this.getScanDate() == null || this.getScanDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getScanDate()));
			}
			if(this.getScanTime() == null || this.getScanTime().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getScanTime());
			}
			if(this.getAcceptOperator() == null || this.getAcceptOperator().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAcceptOperator());
			}
			if(this.getAcceptDate() == null || this.getAcceptDate().equals("null")) {
				pstmt.setNull(61, 91);
			} else {
				pstmt.setDate(61, Date.valueOf(this.getAcceptDate()));
			}
			if(this.getAcceptTime() == null || this.getAcceptTime().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAcceptTime());
			}
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(63, 91);
			} else {
				pstmt.setDate(63, Date.valueOf(this.getAppDate()));
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getReceiveDate()));
			}
			if(this.getListCheckFlag() == null || this.getListCheckFlag().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getListCheckFlag());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getModifyOperator());
			}
			pstmt.setInt(68, this.getAcceptWorkdays());
			// set where condition
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(69, 12);
			} else {
				pstmt.setString(69, this.getEdorAcceptNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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
		SQLString sqlObj = new SQLString("LPEdorApp");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LPEdorApp VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getOtherNoType());
			}
			if(this.getEdorAppName() == null || this.getEdorAppName().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getEdorAppName());
			}
			if(this.getAppType() == null || this.getAppType().equals("null")) {
				pstmt.setNull(5, 1);
			} else {
				pstmt.setString(5, this.getAppType());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getManageCom());
			}
			pstmt.setDouble(7, this.getChgPrem());
			pstmt.setDouble(8, this.getChgAmnt());
			pstmt.setDouble(9, this.getChgGetAmnt());
			pstmt.setDouble(10, this.getGetMoney());
			pstmt.setDouble(11, this.getGetInterest());
			if(this.getEdorAppDate() == null || this.getEdorAppDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getEdorAppDate()));
			}
			if(this.getEdorState() == null || this.getEdorState().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getEdorState());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAccName());
			}
			if(this.getPostalAddress() == null || this.getPostalAddress().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPostalAddress());
			}
			if(this.getZipCode() == null || this.getZipCode().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getZipCode());
			}
			if(this.getPhone() == null || this.getPhone().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPhone());
			}
			if(this.getPrintFlag() == null || this.getPrintFlag().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPrintFlag());
			}
			if(this.getAppGrade() == null || this.getAppGrade().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getAppGrade());
			}
			if(this.getUWState() == null || this.getUWState().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getUWState());
			}
			if(this.getUWGrade() == null || this.getUWGrade().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getUWGrade());
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getUWOperator());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWTime() == null || this.getUWTime().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getUWTime());
			}
			if(this.getConfOperator() == null || this.getConfOperator().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getConfOperator());
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getConfDate()));
			}
			if(this.getConfTime() == null || this.getConfTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getConfTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getModifyTime());
			}
			if(this.getApproveFlag() == null || this.getApproveFlag().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getApproveFlag());
			}
			if(this.getApproveOperator() == null || this.getApproveOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getApproveOperator());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getApproveTime());
			}
			if(this.getPayGetName() == null || this.getPayGetName().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getPayGetName());
			}
			if(this.getPersonID() == null || this.getPersonID().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPersonID());
			}
			if(this.getPayForm() == null || this.getPayForm().equals("null")) {
				pstmt.setNull(41, 1);
			} else {
				pstmt.setString(41, this.getPayForm());
			}
			if(this.getGetForm() == null || this.getGetForm().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getGetForm());
			}
			if(this.getApproveGrade() == null || this.getApproveGrade().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getApproveGrade());
			}
			if(this.getAppPreGrade() == null || this.getAppPreGrade().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAppPreGrade());
			}
			if(this.getEdorConfNo() == null || this.getEdorConfNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getEdorConfNo());
			}
			if(this.getBehalfName() == null || this.getBehalfName().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBehalfName());
			}
			if(this.getBehalfIDType() == null || this.getBehalfIDType().equals("null")) {
				pstmt.setNull(47, 1);
			} else {
				pstmt.setString(47, this.getBehalfIDType());
			}
			if(this.getBehalfIDNo() == null || this.getBehalfIDNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getBehalfIDNo());
			}
			if(this.getBehalfPhone() == null || this.getBehalfPhone().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getBehalfPhone());
			}
			if(this.getBehalfCode() == null || this.getBehalfCode().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getBehalfCode());
			}
			if(this.getEdorAppPhone() == null || this.getEdorAppPhone().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getEdorAppPhone());
			}
			if(this.getBehalfCodeCom() == null || this.getBehalfCodeCom().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getBehalfCodeCom());
			}
			if(this.getSwitchChnlType() == null || this.getSwitchChnlType().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getSwitchChnlType());
			}
			if(this.getSwitchChnlName() == null || this.getSwitchChnlName().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getSwitchChnlName());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getCurrency());
			}
			if(this.getScanManageCom() == null || this.getScanManageCom().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getScanManageCom());
			}
			if(this.getScanOperator() == null || this.getScanOperator().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getScanOperator());
			}
			if(this.getScanDate() == null || this.getScanDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getScanDate()));
			}
			if(this.getScanTime() == null || this.getScanTime().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getScanTime());
			}
			if(this.getAcceptOperator() == null || this.getAcceptOperator().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getAcceptOperator());
			}
			if(this.getAcceptDate() == null || this.getAcceptDate().equals("null")) {
				pstmt.setNull(61, 91);
			} else {
				pstmt.setDate(61, Date.valueOf(this.getAcceptDate()));
			}
			if(this.getAcceptTime() == null || this.getAcceptTime().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getAcceptTime());
			}
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(63, 91);
			} else {
				pstmt.setDate(63, Date.valueOf(this.getAppDate()));
			}
			if(this.getReceiveDate() == null || this.getReceiveDate().equals("null")) {
				pstmt.setNull(64, 91);
			} else {
				pstmt.setDate(64, Date.valueOf(this.getReceiveDate()));
			}
			if(this.getListCheckFlag() == null || this.getListCheckFlag().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getListCheckFlag());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getModifyOperator());
			}
			pstmt.setInt(68, this.getAcceptWorkdays());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LPEdorApp WHERE  EdorAcceptNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getEdorAcceptNo() == null || this.getEdorAcceptNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getEdorAcceptNo());
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
					tError.moduleName = "LPEdorAppDB";
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
			tError.moduleName = "LPEdorAppDB";
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

	public LPEdorAppSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPEdorAppSet aLPEdorAppSet = new LPEdorAppSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPEdorApp");
			LPEdorAppSchema aSchema = this.getSchema();
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
				LPEdorAppSchema s1 = new LPEdorAppSchema();
				s1.setSchema(rs,i);
				aLPEdorAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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

		return aLPEdorAppSet;
	}

	public LPEdorAppSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPEdorAppSet aLPEdorAppSet = new LPEdorAppSet();

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
				LPEdorAppSchema s1 = new LPEdorAppSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPEdorAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPEdorAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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

		return aLPEdorAppSet;
	}

	public LPEdorAppSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPEdorAppSet aLPEdorAppSet = new LPEdorAppSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LPEdorApp");
			LPEdorAppSchema aSchema = this.getSchema();
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

				LPEdorAppSchema s1 = new LPEdorAppSchema();
				s1.setSchema(rs,i);
				aLPEdorAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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

		return aLPEdorAppSet;
	}

	public LPEdorAppSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LPEdorAppSet aLPEdorAppSet = new LPEdorAppSet();

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

				LPEdorAppSchema s1 = new LPEdorAppSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPEdorAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPEdorAppSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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

		return aLPEdorAppSet;
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
			SQLString sqlObj = new SQLString("LPEdorApp");
			LPEdorAppSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LPEdorApp " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LPEdorAppDB";
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
			tError.moduleName = "LPEdorAppDB";
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
        tError.moduleName = "LPEdorAppDB";
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
        tError.moduleName = "LPEdorAppDB";
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
        tError.moduleName = "LPEdorAppDB";
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
        tError.moduleName = "LPEdorAppDB";
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
 * @return LPEdorAppSet
 */
public LPEdorAppSet getData()
{
    int tCount = 0;
    LPEdorAppSet tLPEdorAppSet = new LPEdorAppSet();
    LPEdorAppSchema tLPEdorAppSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LPEdorAppDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLPEdorAppSchema = new LPEdorAppSchema();
        tLPEdorAppSchema.setSchema(mResultSet, 1);
        tLPEdorAppSet.add(tLPEdorAppSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLPEdorAppSchema = new LPEdorAppSchema();
                tLPEdorAppSchema.setSchema(mResultSet, 1);
                tLPEdorAppSet.add(tLPEdorAppSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LPEdorAppDB";
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
    return tLPEdorAppSet;
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
            tError.moduleName = "LPEdorAppDB";
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
        tError.moduleName = "LPEdorAppDB";
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
            tError.moduleName = "LPEdorAppDB";
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
        tError.moduleName = "LPEdorAppDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LPEdorAppSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPEdorAppSet aLPEdorAppSet = new LPEdorAppSet();

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
				LPEdorAppSchema s1 = new LPEdorAppSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPEdorAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPEdorAppSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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

		return aLPEdorAppSet;
	}

	public LPEdorAppSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LPEdorAppSet aLPEdorAppSet = new LPEdorAppSet();

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

				LPEdorAppSchema s1 = new LPEdorAppSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPEdorAppDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLPEdorAppSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPEdorAppDB";
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

		return aLPEdorAppSet; 
	}

}
