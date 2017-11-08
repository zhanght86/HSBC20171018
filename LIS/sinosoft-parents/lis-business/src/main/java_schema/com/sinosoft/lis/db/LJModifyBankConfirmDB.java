/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LJModifyBankConfirmSchema;
import com.sinosoft.lis.vschema.LJModifyBankConfirmSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJModifyBankConfirmDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJModifyBankConfirmDB extends LJModifyBankConfirmSchema
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
	public LJModifyBankConfirmDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LJModifyBankConfirm" );
		mflag = true;
	}

	public LJModifyBankConfirmDB()
	{
		con = null;
		db = new DBOper( "LJModifyBankConfirm" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LJModifyBankConfirmSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LJModifyBankConfirmSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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
			pstmt = con.prepareStatement("DELETE FROM LJModifyBankConfirm WHERE  ApplyBatNo = ?");
			if(this.getApplyBatNo() == null || this.getApplyBatNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getApplyBatNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJModifyBankConfirm");
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
		SQLString sqlObj = new SQLString("LJModifyBankConfirm");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LJModifyBankConfirm SET  ApplyBatNo = ? , FromBussType = ? , FromBussNo = ? , PayCode = ? , MainBussNo = ? , BussType = ? , BussNo = ? , OtherBussNo = ? , CustomerNo = ? , CurHeadBankCode = ? , CurBankCode = ? , CurBankProvince = ? , CurBankCity = ? , CurBankAccNo = ? , CurAccName = ? , TarHeadBankCode = ? , TarBankCode = ? , TarBankProvince = ? , TarBankCity = ? , TarBankAccNo = ? , TarAccName = ? , Money = ? , AppOperator = ? , AppDate = ? , AppTime = ? , AppDesc = ? , ConfirmOperator = ? , ConfirmDate = ? , ConfirmTime = ? , ConfirmConclusion = ? , ConfirmDesc = ? , Remark = ? , State = ? , ManageCom = ? , ComCode = ? , MakeOperator = ? , MakeDate = ? , MakeTime = ? , ModifyOperator = ? , ModifyDate = ? , ModifyTime = ? WHERE  ApplyBatNo = ?");
			if(this.getApplyBatNo() == null || this.getApplyBatNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getApplyBatNo());
			}
			if(this.getFromBussType() == null || this.getFromBussType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getFromBussType());
			}
			if(this.getFromBussNo() == null || this.getFromBussNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFromBussNo());
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPayCode());
			}
			if(this.getMainBussNo() == null || this.getMainBussNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getMainBussNo());
			}
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBussNo());
			}
			if(this.getOtherBussNo() == null || this.getOtherBussNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getOtherBussNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCustomerNo());
			}
			if(this.getCurHeadBankCode() == null || this.getCurHeadBankCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCurHeadBankCode());
			}
			if(this.getCurBankCode() == null || this.getCurBankCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCurBankCode());
			}
			if(this.getCurBankProvince() == null || this.getCurBankProvince().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getCurBankProvince());
			}
			if(this.getCurBankCity() == null || this.getCurBankCity().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getCurBankCity());
			}
			if(this.getCurBankAccNo() == null || this.getCurBankAccNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCurBankAccNo());
			}
			if(this.getCurAccName() == null || this.getCurAccName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getCurAccName());
			}
			if(this.getTarHeadBankCode() == null || this.getTarHeadBankCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getTarHeadBankCode());
			}
			if(this.getTarBankCode() == null || this.getTarBankCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getTarBankCode());
			}
			if(this.getTarBankProvince() == null || this.getTarBankProvince().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getTarBankProvince());
			}
			if(this.getTarBankCity() == null || this.getTarBankCity().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getTarBankCity());
			}
			if(this.getTarBankAccNo() == null || this.getTarBankAccNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getTarBankAccNo());
			}
			if(this.getTarAccName() == null || this.getTarAccName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getTarAccName());
			}
			pstmt.setDouble(22, this.getMoney());
			if(this.getAppOperator() == null || this.getAppOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAppOperator());
			}
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getAppDate()));
			}
			if(this.getAppTime() == null || this.getAppTime().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAppTime());
			}
			if(this.getAppDesc() == null || this.getAppDesc().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAppDesc());
			}
			if(this.getConfirmOperator() == null || this.getConfirmOperator().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getConfirmOperator());
			}
			if(this.getConfirmDate() == null || this.getConfirmDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getConfirmDate()));
			}
			if(this.getConfirmTime() == null || this.getConfirmTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getConfirmTime());
			}
			if(this.getConfirmConclusion() == null || this.getConfirmConclusion().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getConfirmConclusion());
			}
			if(this.getConfirmDesc() == null || this.getConfirmDesc().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getConfirmDesc());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getRemark());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getState());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getModifyTime());
			}
			// set where condition
			if(this.getApplyBatNo() == null || this.getApplyBatNo().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getApplyBatNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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
		SQLString sqlObj = new SQLString("LJModifyBankConfirm");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LJModifyBankConfirm VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getApplyBatNo() == null || this.getApplyBatNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getApplyBatNo());
			}
			if(this.getFromBussType() == null || this.getFromBussType().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getFromBussType());
			}
			if(this.getFromBussNo() == null || this.getFromBussNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getFromBussNo());
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPayCode());
			}
			if(this.getMainBussNo() == null || this.getMainBussNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getMainBussNo());
			}
			if(this.getBussType() == null || this.getBussType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getBussType());
			}
			if(this.getBussNo() == null || this.getBussNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBussNo());
			}
			if(this.getOtherBussNo() == null || this.getOtherBussNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getOtherBussNo());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getCustomerNo());
			}
			if(this.getCurHeadBankCode() == null || this.getCurHeadBankCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getCurHeadBankCode());
			}
			if(this.getCurBankCode() == null || this.getCurBankCode().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCurBankCode());
			}
			if(this.getCurBankProvince() == null || this.getCurBankProvince().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getCurBankProvince());
			}
			if(this.getCurBankCity() == null || this.getCurBankCity().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getCurBankCity());
			}
			if(this.getCurBankAccNo() == null || this.getCurBankAccNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCurBankAccNo());
			}
			if(this.getCurAccName() == null || this.getCurAccName().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getCurAccName());
			}
			if(this.getTarHeadBankCode() == null || this.getTarHeadBankCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getTarHeadBankCode());
			}
			if(this.getTarBankCode() == null || this.getTarBankCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getTarBankCode());
			}
			if(this.getTarBankProvince() == null || this.getTarBankProvince().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getTarBankProvince());
			}
			if(this.getTarBankCity() == null || this.getTarBankCity().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getTarBankCity());
			}
			if(this.getTarBankAccNo() == null || this.getTarBankAccNo().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getTarBankAccNo());
			}
			if(this.getTarAccName() == null || this.getTarAccName().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getTarAccName());
			}
			pstmt.setDouble(22, this.getMoney());
			if(this.getAppOperator() == null || this.getAppOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getAppOperator());
			}
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getAppDate()));
			}
			if(this.getAppTime() == null || this.getAppTime().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getAppTime());
			}
			if(this.getAppDesc() == null || this.getAppDesc().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getAppDesc());
			}
			if(this.getConfirmOperator() == null || this.getConfirmOperator().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getConfirmOperator());
			}
			if(this.getConfirmDate() == null || this.getConfirmDate().equals("null")) {
				pstmt.setNull(28, 91);
			} else {
				pstmt.setDate(28, Date.valueOf(this.getConfirmDate()));
			}
			if(this.getConfirmTime() == null || this.getConfirmTime().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getConfirmTime());
			}
			if(this.getConfirmConclusion() == null || this.getConfirmConclusion().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getConfirmConclusion());
			}
			if(this.getConfirmDesc() == null || this.getConfirmDesc().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getConfirmDesc());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getRemark());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getState());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getManageCom());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getComCode());
			}
			if(this.getMakeOperator() == null || this.getMakeOperator().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getMakeOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(37, 91);
			} else {
				pstmt.setDate(37, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getMakeTime());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getModifyOperator());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(40, 91);
			} else {
				pstmt.setDate(40, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getModifyTime());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LJModifyBankConfirm WHERE  ApplyBatNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getApplyBatNo() == null || this.getApplyBatNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getApplyBatNo());
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
					tError.moduleName = "LJModifyBankConfirmDB";
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
			tError.moduleName = "LJModifyBankConfirmDB";
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

	public LJModifyBankConfirmSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJModifyBankConfirmSet aLJModifyBankConfirmSet = new LJModifyBankConfirmSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJModifyBankConfirm");
			LJModifyBankConfirmSchema aSchema = this.getSchema();
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
				LJModifyBankConfirmSchema s1 = new LJModifyBankConfirmSchema();
				s1.setSchema(rs,i);
				aLJModifyBankConfirmSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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

		return aLJModifyBankConfirmSet;
	}

	public LJModifyBankConfirmSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJModifyBankConfirmSet aLJModifyBankConfirmSet = new LJModifyBankConfirmSet();

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
				LJModifyBankConfirmSchema s1 = new LJModifyBankConfirmSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJModifyBankConfirmDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJModifyBankConfirmSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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

		return aLJModifyBankConfirmSet;
	}

	public LJModifyBankConfirmSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJModifyBankConfirmSet aLJModifyBankConfirmSet = new LJModifyBankConfirmSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJModifyBankConfirm");
			LJModifyBankConfirmSchema aSchema = this.getSchema();
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

				LJModifyBankConfirmSchema s1 = new LJModifyBankConfirmSchema();
				s1.setSchema(rs,i);
				aLJModifyBankConfirmSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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

		return aLJModifyBankConfirmSet;
	}

	public LJModifyBankConfirmSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJModifyBankConfirmSet aLJModifyBankConfirmSet = new LJModifyBankConfirmSet();

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

				LJModifyBankConfirmSchema s1 = new LJModifyBankConfirmSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJModifyBankConfirmDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJModifyBankConfirmSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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

		return aLJModifyBankConfirmSet;
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
			SQLString sqlObj = new SQLString("LJModifyBankConfirm");
			LJModifyBankConfirmSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LJModifyBankConfirm " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LJModifyBankConfirmDB";
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
			tError.moduleName = "LJModifyBankConfirmDB";
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
        tError.moduleName = "LJModifyBankConfirmDB";
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
        tError.moduleName = "LJModifyBankConfirmDB";
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
        tError.moduleName = "LJModifyBankConfirmDB";
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
        tError.moduleName = "LJModifyBankConfirmDB";
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
 * @return LJModifyBankConfirmSet
 */
public LJModifyBankConfirmSet getData()
{
    int tCount = 0;
    LJModifyBankConfirmSet tLJModifyBankConfirmSet = new LJModifyBankConfirmSet();
    LJModifyBankConfirmSchema tLJModifyBankConfirmSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LJModifyBankConfirmDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLJModifyBankConfirmSchema = new LJModifyBankConfirmSchema();
        tLJModifyBankConfirmSchema.setSchema(mResultSet, 1);
        tLJModifyBankConfirmSet.add(tLJModifyBankConfirmSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLJModifyBankConfirmSchema = new LJModifyBankConfirmSchema();
                tLJModifyBankConfirmSchema.setSchema(mResultSet, 1);
                tLJModifyBankConfirmSet.add(tLJModifyBankConfirmSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LJModifyBankConfirmDB";
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
    return tLJModifyBankConfirmSet;
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
            tError.moduleName = "LJModifyBankConfirmDB";
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
        tError.moduleName = "LJModifyBankConfirmDB";
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
            tError.moduleName = "LJModifyBankConfirmDB";
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
        tError.moduleName = "LJModifyBankConfirmDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LJModifyBankConfirmSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJModifyBankConfirmSet aLJModifyBankConfirmSet = new LJModifyBankConfirmSet();

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
				LJModifyBankConfirmSchema s1 = new LJModifyBankConfirmSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LJModifyBankConfirmDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLJModifyBankConfirmSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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

		return aLJModifyBankConfirmSet;
	}

	public LJModifyBankConfirmSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJModifyBankConfirmSet aLJModifyBankConfirmSet = new LJModifyBankConfirmSet();

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

				LJModifyBankConfirmSchema s1 = new LJModifyBankConfirmSchema();
				if (!s1.setSchema(rs,i)) {
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LJModifyBankConfirmDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLJModifyBankConfirmSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LJModifyBankConfirmDB";
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

		return aLJModifyBankConfirmSet; 
	}

}
