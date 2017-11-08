/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJAPayPersonDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJAPayPersonDB extends LJAPayPersonSchema
{
	private static Logger logger = Logger.getLogger(LJAPayPersonDB.class);
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
	public LJAPayPersonDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LJAPayPerson" );
		mflag = true;
	}

	public LJAPayPersonDB()
	{
		con = null;
		db = new DBOper( "LJAPayPerson" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LJAPayPersonSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LJAPayPersonSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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
			pstmt = con.prepareStatement("DELETE FROM LJAPayPerson WHERE  PolNo = ? AND PayCount = ? AND PayNo = ? AND DutyCode = ? AND PayPlanCode = ? AND PayType = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.setInt(2, this.getPayCount());
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPayPlanCode());
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAPayPerson");
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
		SQLString sqlObj = new SQLString("LJAPayPerson");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LJAPayPerson SET  PolNo = ? , PayCount = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , RiskCode = ? , AgentCode = ? , AgentGroup = ? , PayTypeFlag = ? , AppntNo = ? , PayNo = ? , PayAimClass = ? , DutyCode = ? , PayPlanCode = ? , SumDuePayMoney = ? , SumActuPayMoney = ? , PayIntv = ? , PayDate = ? , PayType = ? , EnterAccDate = ? , ConfDate = ? , LastPayToDate = ? , CurPayToDate = ? , InInsuAccState = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , SerialNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , GetNoticeNo = ? , ModifyDate = ? , ModifyTime = ? , MainPolYear = ? , EdorNo = ? , Currency = ? , EndorsementNo = ? , GetFlag = ? , ComCode = ? , ModifyOperator = ? , NetAmount = ? , TaxAmount = ? , Tax = ? WHERE  PolNo = ? AND PayCount = ? AND PayNo = ? AND DutyCode = ? AND PayPlanCode = ? AND PayType = ?");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.setInt(2, this.getPayCount());
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRiskCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentGroup());
			}
			if(this.getPayTypeFlag() == null || this.getPayTypeFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPayTypeFlag());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAppntNo());
			}
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPayNo());
			}
			if(this.getPayAimClass() == null || this.getPayAimClass().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getPayAimClass());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPayPlanCode());
			}
			pstmt.setDouble(18, this.getSumDuePayMoney());
			pstmt.setDouble(19, this.getSumActuPayMoney());
			pstmt.setInt(20, this.getPayIntv());
			if(this.getPayDate() == null || this.getPayDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getPayDate()));
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPayType());
			}
			if(this.getEnterAccDate() == null || this.getEnterAccDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getEnterAccDate()));
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getConfDate()));
			}
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getInInsuAccState() == null || this.getInInsuAccState().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getInInsuAccState());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getApproveTime());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getSerialNo());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getMakeTime());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getGetNoticeNo());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getModifyTime());
			}
			pstmt.setInt(38, this.getMainPolYear());
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getEdorNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getCurrency());
			}
			if(this.getEndorsementNo() == null || this.getEndorsementNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getEndorsementNo());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getGetFlag());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getModifyOperator());
			}
			pstmt.setDouble(45, this.getNetAmount());
			pstmt.setDouble(46, this.getTaxAmount());
			pstmt.setDouble(47, this.getTax());
			// set where condition
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getPolNo());
			}
			pstmt.setInt(49, this.getPayCount());
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getPayNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getPayPlanCode());
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getPayType());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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
		SQLString sqlObj = new SQLString("LJAPayPerson");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LJAPayPerson VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.setInt(2, this.getPayCount());
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getContNo());
			}
			if(this.getManageCom() == null || this.getManageCom().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getManageCom());
			}
			if(this.getAgentCom() == null || this.getAgentCom().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getAgentCom());
			}
			if(this.getAgentType() == null || this.getAgentType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAgentType());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRiskCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAgentCode());
			}
			if(this.getAgentGroup() == null || this.getAgentGroup().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getAgentGroup());
			}
			if(this.getPayTypeFlag() == null || this.getPayTypeFlag().equals("null")) {
				pstmt.setNull(12, 1);
			} else {
				pstmt.setString(12, this.getPayTypeFlag());
			}
			if(this.getAppntNo() == null || this.getAppntNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getAppntNo());
			}
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getPayNo());
			}
			if(this.getPayAimClass() == null || this.getPayAimClass().equals("null")) {
				pstmt.setNull(15, 1);
			} else {
				pstmt.setString(15, this.getPayAimClass());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPayPlanCode());
			}
			pstmt.setDouble(18, this.getSumDuePayMoney());
			pstmt.setDouble(19, this.getSumActuPayMoney());
			pstmt.setInt(20, this.getPayIntv());
			if(this.getPayDate() == null || this.getPayDate().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getPayDate()));
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPayType());
			}
			if(this.getEnterAccDate() == null || this.getEnterAccDate().equals("null")) {
				pstmt.setNull(23, 91);
			} else {
				pstmt.setDate(23, Date.valueOf(this.getEnterAccDate()));
			}
			if(this.getConfDate() == null || this.getConfDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getConfDate()));
			}
			if(this.getLastPayToDate() == null || this.getLastPayToDate().equals("null")) {
				pstmt.setNull(25, 91);
			} else {
				pstmt.setDate(25, Date.valueOf(this.getLastPayToDate()));
			}
			if(this.getCurPayToDate() == null || this.getCurPayToDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getCurPayToDate()));
			}
			if(this.getInInsuAccState() == null || this.getInInsuAccState().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getInInsuAccState());
			}
			if(this.getApproveCode() == null || this.getApproveCode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getApproveCode());
			}
			if(this.getApproveDate() == null || this.getApproveDate().equals("null")) {
				pstmt.setNull(29, 91);
			} else {
				pstmt.setDate(29, Date.valueOf(this.getApproveDate()));
			}
			if(this.getApproveTime() == null || this.getApproveTime().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getApproveTime());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getSerialNo());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(33, 91);
			} else {
				pstmt.setDate(33, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getMakeTime());
			}
			if(this.getGetNoticeNo() == null || this.getGetNoticeNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getGetNoticeNo());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(36, 91);
			} else {
				pstmt.setDate(36, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(37, 1);
			} else {
				pstmt.setString(37, this.getModifyTime());
			}
			pstmt.setInt(38, this.getMainPolYear());
			if(this.getEdorNo() == null || this.getEdorNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getEdorNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getCurrency());
			}
			if(this.getEndorsementNo() == null || this.getEndorsementNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getEndorsementNo());
			}
			if(this.getGetFlag() == null || this.getGetFlag().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getGetFlag());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getComCode());
			}
			if(this.getModifyOperator() == null || this.getModifyOperator().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getModifyOperator());
			}
			pstmt.setDouble(45, this.getNetAmount());
			pstmt.setDouble(46, this.getTaxAmount());
			pstmt.setDouble(47, this.getTax());
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LJAPayPerson WHERE  PolNo = ? AND PayCount = ? AND PayNo = ? AND DutyCode = ? AND PayPlanCode = ? AND PayType = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getPolNo());
			}
			pstmt.setInt(2, this.getPayCount());
			if(this.getPayNo() == null || this.getPayNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getDutyCode());
			}
			if(this.getPayPlanCode() == null || this.getPayPlanCode().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPayPlanCode());
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getPayType());
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
					tError.moduleName = "LJAPayPersonDB";
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
			tError.moduleName = "LJAPayPersonDB";
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

	public LJAPayPersonSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJAPayPerson");
			LJAPayPersonSchema aSchema = this.getSchema();
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
				LJAPayPersonSchema s1 = new LJAPayPersonSchema();
				s1.setSchema(rs,i);
				aLJAPayPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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

		return aLJAPayPersonSet;
	}

	public LJAPayPersonSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();

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
				LJAPayPersonSchema s1 = new LJAPayPersonSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJAPayPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJAPayPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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

		return aLJAPayPersonSet;
	}

	public LJAPayPersonSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LJAPayPerson");
			LJAPayPersonSchema aSchema = this.getSchema();
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

				LJAPayPersonSchema s1 = new LJAPayPersonSchema();
				s1.setSchema(rs,i);
				aLJAPayPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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

		return aLJAPayPersonSet;
	}

	public LJAPayPersonSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();

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

				LJAPayPersonSchema s1 = new LJAPayPersonSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJAPayPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLJAPayPersonSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJAPayPersonDB";
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

		return aLJAPayPersonSet;
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
			SQLString sqlObj = new SQLString("LJAPayPerson");
			LJAPayPersonSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LJAPayPerson " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LJAPayPersonDB";
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
			tError.moduleName = "LJAPayPersonDB";
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
        tError.moduleName = "LJAPayPersonDB";
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
        tError.moduleName = "LJAPayPersonDB";
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
        tError.moduleName = "LJAPayPersonDB";
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
        tError.moduleName = "LJAPayPersonDB";
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
 * @return LJAPayPersonSet
 */
public LJAPayPersonSet getData()
{
    int tCount = 0;
    LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
    LJAPayPersonSchema tLJAPayPersonSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LJAPayPersonDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLJAPayPersonSchema = new LJAPayPersonSchema();
        tLJAPayPersonSchema.setSchema(mResultSet, 1);
        tLJAPayPersonSet.add(tLJAPayPersonSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLJAPayPersonSchema = new LJAPayPersonSchema();
                tLJAPayPersonSchema.setSchema(mResultSet, 1);
                tLJAPayPersonSet.add(tLJAPayPersonSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LJAPayPersonDB";
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
    return tLJAPayPersonSet;
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
            tError.moduleName = "LJAPayPersonDB";
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
        tError.moduleName = "LJAPayPersonDB";
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
            tError.moduleName = "LJAPayPersonDB";
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
        tError.moduleName = "LJAPayPersonDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LJAPayPersonSet executeQuery(SQLwithBindVariables sqlbv) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();

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
				LJAPayPersonSchema s1 = new LJAPayPersonSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJAPayPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLJAPayPersonSet.add(s1);
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
			tError.moduleName = "LJAPayPersonDB";
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

		return aLJAPayPersonSet;
	}

	public LJAPayPersonSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		logger.debug("ExecSQLwithBV SQL: " + sqlbv.originalSql());
		for(String key : sqlbv.variableKey())
			logger.debug("ExecSQLwithBV Variables: "+ key+"="+sqlbv.variableValue(key));
		LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();

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

				LJAPayPersonSchema s1 = new LJAPayPersonSchema();
				if (!s1.setSchema(rs, i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJAPayPersonDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors.addOneError(tError);
				}
				aLJAPayPersonSet.add(s1);
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
			tError.moduleName = "LJAPayPersonDB";
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

		return aLJAPayPersonSet;
	}

}
