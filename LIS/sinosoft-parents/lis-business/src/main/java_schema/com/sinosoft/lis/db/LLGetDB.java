/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LLGetSchema;
import com.sinosoft.lis.vschema.LLGetSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLGetDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLGetDB extends LLGetSchema
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
	public LLGetDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLGet" );
		mflag = true;
	}

	public LLGetDB()
	{
		con = null;
		db = new DBOper( "LLGet" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLGetSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLGetSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLGet WHERE  ClmNo = ? AND OperationType = ? AND PolNo = ? AND DutyCode = ? AND GetDutyCode = ?");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getOperationType() == null || this.getOperationType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOperationType());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getDutyCode());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLGet");
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
		SQLString sqlObj = new SQLString("LLGet");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLGet SET  ClmNo = ? , OperationType = ? , BnfNo = ? , CustomerNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , GrpContNo = ? , ContNo = ? , PolNo = ? , DutyCode = ? , GetDutyCode = ? , GetDutyKind = ? , InsuredNo = ? , GetMode = ? , GetLimit = ? , GetRate = ? , UrgeGetFlag = ? , LiveGetType = ? , AddRate = ? , CanGet = ? , NeedAcc = ? , NeedCancelAcc = ? , StandMoney = ? , ActuGet = ? , SumMoney = ? , GetIntv = ? , GettoDate = ? , GetStartDate = ? , GetEndDate = ? , BalaDate = ? , State = ? , ManageCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyTime = ? , ModifyDate = ? , GetEndState = ? , Currency = ? WHERE  ClmNo = ? AND OperationType = ? AND PolNo = ? AND DutyCode = ? AND GetDutyCode = ?");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getOperationType() == null || this.getOperationType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOperationType());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBnfNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getIDNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getDutyCode());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getGetDutyCode());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGetDutyKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getInsuredNo());
			}
			if(this.getGetMode() == null || this.getGetMode().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getGetMode());
			}
			pstmt.setDouble(18, this.getGetLimit());
			pstmt.setDouble(19, this.getGetRate());
			if(this.getUrgeGetFlag() == null || this.getUrgeGetFlag().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getUrgeGetFlag());
			}
			if(this.getLiveGetType() == null || this.getLiveGetType().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getLiveGetType());
			}
			pstmt.setDouble(22, this.getAddRate());
			if(this.getCanGet() == null || this.getCanGet().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getCanGet());
			}
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getNeedAcc());
			}
			if(this.getNeedCancelAcc() == null || this.getNeedCancelAcc().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getNeedCancelAcc());
			}
			pstmt.setDouble(26, this.getStandMoney());
			pstmt.setDouble(27, this.getActuGet());
			pstmt.setDouble(28, this.getSumMoney());
			pstmt.setInt(29, this.getGetIntv());
			if(this.getGettoDate() == null || this.getGettoDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getGettoDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getGetEndDate() == null || this.getGetEndDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getGetEndDate()));
			}
			if(this.getBalaDate() == null || this.getBalaDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getBalaDate()));
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getState());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getManageCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getMakeTime());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getModifyTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getModifyDate()));
			}
			if(this.getGetEndState() == null || this.getGetEndState().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getGetEndState());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCurrency());
			}
			// set where condition
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getClmNo());
			}
			if(this.getOperationType() == null || this.getOperationType().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getOperationType());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getDutyCode());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getGetDutyCode());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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
		SQLString sqlObj = new SQLString("LLGet");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLGet VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getOperationType() == null || this.getOperationType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOperationType());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getBnfNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(7, 91);
			} else {
				pstmt.setDate(7, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getIDNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getGrpContNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getContNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getDutyCode());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getGetDutyCode());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getGetDutyKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getInsuredNo());
			}
			if(this.getGetMode() == null || this.getGetMode().equals("null")) {
				pstmt.setNull(17, 1);
			} else {
				pstmt.setString(17, this.getGetMode());
			}
			pstmt.setDouble(18, this.getGetLimit());
			pstmt.setDouble(19, this.getGetRate());
			if(this.getUrgeGetFlag() == null || this.getUrgeGetFlag().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getUrgeGetFlag());
			}
			if(this.getLiveGetType() == null || this.getLiveGetType().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getLiveGetType());
			}
			pstmt.setDouble(22, this.getAddRate());
			if(this.getCanGet() == null || this.getCanGet().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getCanGet());
			}
			if(this.getNeedAcc() == null || this.getNeedAcc().equals("null")) {
				pstmt.setNull(24, 1);
			} else {
				pstmt.setString(24, this.getNeedAcc());
			}
			if(this.getNeedCancelAcc() == null || this.getNeedCancelAcc().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getNeedCancelAcc());
			}
			pstmt.setDouble(26, this.getStandMoney());
			pstmt.setDouble(27, this.getActuGet());
			pstmt.setDouble(28, this.getSumMoney());
			pstmt.setInt(29, this.getGetIntv());
			if(this.getGettoDate() == null || this.getGettoDate().equals("null")) {
				pstmt.setNull(30, 91);
			} else {
				pstmt.setDate(30, Date.valueOf(this.getGettoDate()));
			}
			if(this.getGetStartDate() == null || this.getGetStartDate().equals("null")) {
				pstmt.setNull(31, 91);
			} else {
				pstmt.setDate(31, Date.valueOf(this.getGetStartDate()));
			}
			if(this.getGetEndDate() == null || this.getGetEndDate().equals("null")) {
				pstmt.setNull(32, 91);
			} else {
				pstmt.setDate(32, Date.valueOf(this.getGetEndDate()));
			}
			if(this.getBalaDate() == null || this.getBalaDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getBalaDate()));
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getState());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getManageCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(38, 1);
			} else {
				pstmt.setString(38, this.getMakeTime());
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(39, 1);
			} else {
				pstmt.setString(39, this.getModifyTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getModifyDate()));
			}
			if(this.getGetEndState() == null || this.getGetEndState().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getGetEndState());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCurrency());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLGet WHERE  ClmNo = ? AND OperationType = ? AND PolNo = ? AND DutyCode = ? AND GetDutyCode = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getOperationType() == null || this.getOperationType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getOperationType());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPolNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getDutyCode());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyCode());
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
					tError.moduleName = "LLGetDB";
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
			tError.moduleName = "LLGetDB";
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

	public LLGetSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGetSet aLLGetSet = new LLGetSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLGet");
			LLGetSchema aSchema = this.getSchema();
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
				LLGetSchema s1 = new LLGetSchema();
				s1.setSchema(rs,i);
				aLLGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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

		return aLLGetSet;
	}

	public LLGetSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLGetSet aLLGetSet = new LLGetSet();

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
				LLGetSchema s1 = new LLGetSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGetDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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

		return aLLGetSet;
	}

	public LLGetSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGetSet aLLGetSet = new LLGetSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLGet");
			LLGetSchema aSchema = this.getSchema();
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

				LLGetSchema s1 = new LLGetSchema();
				s1.setSchema(rs,i);
				aLLGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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

		return aLLGetSet;
	}

	public LLGetSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLGetSet aLLGetSet = new LLGetSet();

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

				LLGetSchema s1 = new LLGetSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGetDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGetSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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

		return aLLGetSet;
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
			SQLString sqlObj = new SQLString("LLGet");
			LLGetSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLGet " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLGetDB";
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
			tError.moduleName = "LLGetDB";
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
        tError.moduleName = "LLGetDB";
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
        tError.moduleName = "LLGetDB";
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
        tError.moduleName = "LLGetDB";
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
        tError.moduleName = "LLGetDB";
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
 * @return LLGetSet
 */
public LLGetSet getData()
{
    int tCount = 0;
    LLGetSet tLLGetSet = new LLGetSet();
    LLGetSchema tLLGetSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLGetDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLGetSchema = new LLGetSchema();
        tLLGetSchema.setSchema(mResultSet, 1);
        tLLGetSet.add(tLLGetSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLGetSchema = new LLGetSchema();
                tLLGetSchema.setSchema(mResultSet, 1);
                tLLGetSet.add(tLLGetSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLGetDB";
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
    return tLLGetSet;
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
            tError.moduleName = "LLGetDB";
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
        tError.moduleName = "LLGetDB";
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
            tError.moduleName = "LLGetDB";
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
        tError.moduleName = "LLGetDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLGetSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGetSet aLLGetSet = new LLGetSet();

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
				LLGetSchema s1 = new LLGetSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGetDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGetSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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

		return aLLGetSet;
	}

	public LLGetSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLGetSet aLLGetSet = new LLGetSet();

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

				LLGetSchema s1 = new LLGetSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLGetDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLGetSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLGetDB";
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

		return aLLGetSet; 
	}

}
