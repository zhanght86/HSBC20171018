/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJTempFeeDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJTempFeeDB extends LJTempFeeSchema
{
	private static Logger logger = Logger.getLogger(LJTempFeeDB.class);
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
	public LJTempFeeDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LJTempFee" );
		mflag = true;
	}

	public LJTempFeeDB()
	{
		con = null;
		db = new DBOper( "LJTempFee" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LJTempFeeSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LJTempFeeSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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
			pstmt = con.prepareStatement("DELETE FROM LJTempFee WHERE  TempFeeNo = ? AND TempFeeType = ? AND RiskCode = ? AND Currency = ?");
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTempFeeNo());
			}
			if(this.getTempFeeType() == null || this.getTempFeeType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, StrTool.space1(this.getTempFeeType(), 2));
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCurrency());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJTempFee");
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
		SQLString sqlObj = new SQLString("LJTempFee");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LJTempFee SET  TempFeeNo = ? , TempFeeType = ? , RiskCode = ? , PayIntv = ? , OtherNo = ? , OtherNoType = ? , PayMoney = ? , PayDate = ? , EnterAccDate = ? , ConfDate = ? , ConfMakeDate = ? , ConfMakeTime = ? , SaleChnl = ? , ManageCom = ? , PolicyCom = ? , AgentCom = ? , AgentType = ? , APPntName = ? , AgentGroup = ? , AgentCode = ? , ConfFlag = ? , SerialNo = ? , Operator = ? , State = ? , MakeTime = ? , MakeDate = ? , ModifyDate = ? , ModifyTime = ? , ContCom = ? , PayEndYear = ? , OperState = ? , TempFeeNoType = ? , StandPrem = ? , Remark = ? , Distict = ? , Department = ? , BranchCode = ? , RiskType = ? , PayYears = ? , Currency = ? , PayMode = ? , UsedMoney = ? , LockMoney = ? , AppOperator = ? , AppDate = ? , AppTime = ? , InputOperator = ? , InputDate = ? , InputTime = ? , InputConclusion = ? , InputDesc = ? , ConfirmOperator = ? , ConfirmDate = ? , ConfirmTime = ? , ConfirmConclusion = ? , ConfirmDesc = ? , CancelOperator = ? , CancelDate = ? , CancelTime = ? , CancelDesc = ? , ComCode = ? , ModifyOperator = ? WHERE  TempFeeNo = ? AND TempFeeType = ? AND RiskCode = ? AND Currency = ?");
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTempFeeNo());
			}
			if(this.getTempFeeType() == null || this.getTempFeeType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, this.getTempFeeType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			pstmt.setInt(4, this.getPayIntv());
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getOtherNoType());
			}
			pstmt.setDouble(7, this.getPayMoney());
			if(this.getPayDate() == null || this.getPayDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getPayDate()));
			}
			if(this.getEnterAccDate() == null || this.getEnterAccDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getEnterAccDate()));
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getConfDate()));
			}
			if(this.getConfMakeDate() == null || this.getConfMakeDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getConfMakeDate()));
			}
			if(this.getConfMakeTime() == null || this.getConfMakeTime().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getConfMakeTime());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getManageCom());
			}
			if(this.getPolicyCom() == null || this.getPolicyCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPolicyCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentType());
			}
			if(this.getAPPntName() == null || this.getAPPntName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAPPntName());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentGroup());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentCode());
			}
			if(this.getConfFlag() == null || this.getConfFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getConfFlag());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSerialNo());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getOperator());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getState());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getMakeTime());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate()));
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getModifyTime());
			}
			if(this.getContCom() == null || this.getContCom().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getContCom());
			}
			pstmt.setInt(30, this.getPayEndYear());
			if(this.getOperState() == null || this.getOperState().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getOperState());
			}
			if(this.getTempFeeNoType() == null || this.getTempFeeNoType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getTempFeeNoType());
			}
			pstmt.setDouble(33, this.getStandPrem());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRemark());
			}
			if(this.getDistict() == null || this.getDistict().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getDistict());
			}
			if(this.getDepartment() == null || this.getDepartment().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getDepartment());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBranchCode());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getRiskType());
			}
			pstmt.setInt(39, this.getPayYears());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getCurrency());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getPayMode());
			}
			pstmt.setDouble(42, this.getUsedMoney());
			pstmt.setDouble(43, this.getLockMoney());
			if(this.getAppOperator() == null || this.getAppOperator().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAppOperator());
			}
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getAppDate()));
			}
			if(this.getAppTime() == null || this.getAppTime().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getAppTime());
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getInputDate()));
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getInputTime());
			}
			if(this.getInputConclusion() == null || this.getInputConclusion().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getInputConclusion());
			}
			if(this.getInputDesc() == null || this.getInputDesc().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getInputDesc());
			}
			if(this.getConfirmOperator() == null || this.getConfirmOperator().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getConfirmOperator());
			}
			if(this.getConfirmDate() == null || this.getConfirmDate().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getConfirmDate()));
			}
			if(this.getConfirmTime() == null || this.getConfirmTime().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getConfirmTime());
			}
			if(this.getConfirmConclusion() == null || this.getConfirmConclusion().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getConfirmConclusion());
			}
			if(this.getConfirmDesc() == null || this.getConfirmDesc().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getConfirmDesc());
			}
			if(this.getCancelOperator() == null || this.getCancelOperator().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getCancelOperator());
			}
			if(this.getCancelDate() == null || this.getCancelDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCancelDate()));
			}
			if(this.getCancelTime() == null || this.getCancelTime().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getCancelTime());
			}
			if(this.getCancelDesc() == null || this.getCancelDesc().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getCancelDesc());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getModifyOperator());
			}
			// set where condition
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getTempFeeNo());
			}
			if(this.getTempFeeType() == null || this.getTempFeeType().equals("null")) {
				pstmt.setNull(64, 1);
			} else {
				pstmt.setString(64, StrTool.space1(this.getTempFeeType(), 2));
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(65, 12);
			} else {
				pstmt.setString(65, this.getRiskCode());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getCurrency());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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
		SQLString sqlObj = new SQLString("LJTempFee");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LJTempFee VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTempFeeNo());
			}
			if(this.getTempFeeType() == null || this.getTempFeeType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, this.getTempFeeType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			pstmt.setInt(4, this.getPayIntv());
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getOtherNoType());
			}
			pstmt.setDouble(7, this.getPayMoney());
			if(this.getPayDate() == null || this.getPayDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getPayDate()));
			}
			if(this.getEnterAccDate() == null || this.getEnterAccDate().equals("null")) {
				pstmt.setNull(9, 91);
			} else {
				pstmt.setDate(9, Date.valueOf(this.getEnterAccDate()));
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(10, 91);
			} else {
				pstmt.setDate(10, Date.valueOf(this.getConfDate()));
			}
			if(this.getConfMakeDate() == null || this.getConfMakeDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getConfMakeDate()));
			}
			if(this.getConfMakeTime() == null || this.getConfMakeTime().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getConfMakeTime());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getSaleChnl());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getManageCom());
			}
			if(this.getPolicyCom() == null || this.getPolicyCom().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getPolicyCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getAgentType());
			}
			if(this.getAPPntName() == null || this.getAPPntName().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getAPPntName());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getAgentGroup());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getAgentCode());
			}
			if(this.getConfFlag() == null || this.getConfFlag().equals("null")) {
				pstmt.setNull(21, 1);
			} else {
				pstmt.setString(21, this.getConfFlag());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getSerialNo());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getOperator());
			}
			if(this.getState() == null || this.getState().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getState());
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getMakeTime());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getMakeDate()));
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(27, 91);
			} else {
				pstmt.setDate(27, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(28, 1);
			} else {
				pstmt.setString(28, this.getModifyTime());
			}
			if(this.getContCom() == null || this.getContCom().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getContCom());
			}
			pstmt.setInt(30, this.getPayEndYear());
			if(this.getOperState() == null || this.getOperState().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getOperState());
			}
			if(this.getTempFeeNoType() == null || this.getTempFeeNoType().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getTempFeeNoType());
			}
			pstmt.setDouble(33, this.getStandPrem());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getRemark());
			}
			if(this.getDistict() == null || this.getDistict().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getDistict());
			}
			if(this.getDepartment() == null || this.getDepartment().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getDepartment());
			}
			if(this.getBranchCode() == null || this.getBranchCode().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getBranchCode());
			}
			if(this.getRiskType() == null || this.getRiskType().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getRiskType());
			}
			pstmt.setInt(39, this.getPayYears());
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getCurrency());
			}
			if(this.getPayMode() == null || this.getPayMode().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getPayMode());
			}
			pstmt.setDouble(42, this.getUsedMoney());
			pstmt.setDouble(43, this.getLockMoney());
			if(this.getAppOperator() == null || this.getAppOperator().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getAppOperator());
			}
			if(this.getAppDate() == null || this.getAppDate().equals("null")) {
				pstmt.setNull(45, 91);
			} else {
				pstmt.setDate(45, Date.valueOf(this.getAppDate()));
			}
			if(this.getAppTime() == null || this.getAppTime().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getAppTime());
			}
			if(this.getInputOperator() == null || this.getInputOperator().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getInputOperator());
			}
			if(this.getInputDate() == null || this.getInputDate().equals("null")) {
				pstmt.setNull(48, 91);
			} else {
				pstmt.setDate(48, Date.valueOf(this.getInputDate()));
			}
			if(this.getInputTime() == null || this.getInputTime().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getInputTime());
			}
			if(this.getInputConclusion() == null || this.getInputConclusion().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getInputConclusion());
			}
			if(this.getInputDesc() == null || this.getInputDesc().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getInputDesc());
			}
			if(this.getConfirmOperator() == null || this.getConfirmOperator().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getConfirmOperator());
			}
			if(this.getConfirmDate() == null || this.getConfirmDate().equals("null")) {
				pstmt.setNull(53, 91);
			} else {
				pstmt.setDate(53, Date.valueOf(this.getConfirmDate()));
			}
			if(this.getConfirmTime() == null || this.getConfirmTime().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getConfirmTime());
			}
			if(this.getConfirmConclusion() == null || this.getConfirmConclusion().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getConfirmConclusion());
			}
			if(this.getConfirmDesc() == null || this.getConfirmDesc().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getConfirmDesc());
			}
			if(this.getCancelOperator() == null || this.getCancelOperator().equals("null")) {
				pstmt.setNull(57, 12);
			} else {
				pstmt.setString(57, this.getCancelOperator());
			}
			if(this.getCancelDate() == null || this.getCancelDate().equals("null")) {
				pstmt.setNull(58, 91);
			} else {
				pstmt.setDate(58, Date.valueOf(this.getCancelDate()));
			}
			if(this.getCancelTime() == null || this.getCancelTime().equals("null")) {
				pstmt.setNull(59, 12);
			} else {
				pstmt.setString(59, this.getCancelTime());
			}
			if(this.getCancelDesc() == null || this.getCancelDesc().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getCancelDesc());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(62, 12);
			} else {
				pstmt.setString(62, this.getModifyOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LJTempFee WHERE  TempFeeNo = ? AND TempFeeType = ? AND RiskCode = ? AND Currency = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getTempFeeNo() == null || this.getTempFeeNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getTempFeeNo());
			}
			if(this.getTempFeeType() == null || this.getTempFeeType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, StrTool.space1(this.getTempFeeType(), 2));
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getRiskCode());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getCurrency());
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
					tError.moduleName = "LJTempFeeDB";
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
			tError.moduleName = "LJTempFeeDB";
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

	public LJTempFeeSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJTempFee");
			LJTempFeeSchema aSchema = this.getSchema();
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
				LJTempFeeSchema s1 = new LJTempFeeSchema();
				s1.setSchema(rs,i);
				aLJTempFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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

		return aLJTempFeeSet;
	}

	public LJTempFeeSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();

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
				LJTempFeeSchema s1 = new LJTempFeeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJTempFeeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJTempFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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

		return aLJTempFeeSet;
	}

	public LJTempFeeSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJTempFee");
			LJTempFeeSchema aSchema = this.getSchema();
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

				LJTempFeeSchema s1 = new LJTempFeeSchema();
				s1.setSchema(rs,i);
				aLJTempFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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

		return aLJTempFeeSet;
	}

	public LJTempFeeSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();

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

				LJTempFeeSchema s1 = new LJTempFeeSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJTempFeeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJTempFeeSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJTempFeeDB";
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

		return aLJTempFeeSet;
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
			SQLString sqlObj = new SQLString("LJTempFee");
			LJTempFeeSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LJTempFee " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LJTempFeeDB";
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
			tError.moduleName = "LJTempFeeDB";
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
        tError.moduleName = "LJTempFeeDB";
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
        tError.moduleName = "LJTempFeeDB";
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
        tError.moduleName = "LJTempFeeDB";
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
        tError.moduleName = "LJTempFeeDB";
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
 * @return LJTempFeeSet
 */
public LJTempFeeSet getData()
{
    int tCount = 0;
    LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
    LJTempFeeSchema tLJTempFeeSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LJTempFeeDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLJTempFeeSchema = new LJTempFeeSchema();
        tLJTempFeeSchema.setSchema(mResultSet, 1);
        tLJTempFeeSet.add(tLJTempFeeSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLJTempFeeSchema = new LJTempFeeSchema();
                tLJTempFeeSchema.setSchema(mResultSet, 1);
                tLJTempFeeSet.add(tLJTempFeeSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LJTempFeeDB";
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
    return tLJTempFeeSet;
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
            tError.moduleName = "LJTempFeeDB";
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
        tError.moduleName = "LJTempFeeDB";
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
            tError.moduleName = "LJTempFeeDB";
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
        tError.moduleName = "LJTempFeeDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LJTempFeeSet executeQuery(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();

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
				LJTempFeeSchema s1 = new LJTempFeeSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJTempFeeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLJTempFeeSet.add(s1);
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
			tError.moduleName = "LJTempFeeDB";
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

		return aLJTempFeeSet;
	}

	public LJTempFeeSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();

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

				LJTempFeeSchema s1 = new LJTempFeeSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJTempFeeDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLJTempFeeSet.add(s1);
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
			tError.moduleName = "LJTempFeeDB";
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

		return aLJTempFeeSet;
	}

}