/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LYReturnFromBankSchema;
import com.sinosoft.lis.vschema.LYReturnFromBankSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LYReturnFromBankDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYReturnFromBankDB extends LYReturnFromBankSchema
{
private static Logger logger = Logger.getLogger(LYReturnFromBankDB.class);

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
	public LYReturnFromBankDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LYReturnFromBank" );
		mflag = true;
	}

	public LYReturnFromBankDB()
	{
		con = null;
		db = new DBOper( "LYReturnFromBank" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LYReturnFromBankSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LYReturnFromBankSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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
			pstmt = con.prepareStatement("DELETE FROM LYReturnFromBank WHERE  SerialNo = ? AND DealType = ? AND PayCode = ? AND PolNo = ?");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getDealType() == null || this.getDealType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, StrTool.space1(this.getDealType(), 1));
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayCode());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LYReturnFromBank");
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
		SQLString sqlObj = new SQLString("LYReturnFromBank");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LYReturnFromBank SET  SerialNo = ? , DealType = ? , DataType = ? , PayCode = ? , PayType = ? , Name = ? , BankCode = ? , AccType = ? , AccName = ? , AccNo = ? , PolNo = ? , NoType = ? , ComCode = ? , AgentCode = ? , PayMoney = ? , SendDate = ? , BankDealDate = ? , BankDealTime = ? , BankSuccFlag = ? , VertifyFlag = ? , Remark = ? , ConvertFlag = ? , DoType = ? , ModifyDate = ? , ModifyTime = ? , RiskCode = ? , SerNo = ? , BankName = ? , InBankCode = ? , InBankName = ? , InAccType = ? , InAccName = ? , InAccNo = ? , IDType = ? , IDNo = ? , StandByFlag1 = ? , StandByFlag2 = ? , StandByFlag3 = ? , StandByFlag4 = ? , StandByFlag5 = ? WHERE  SerialNo = ? AND DealType = ? AND PayCode = ? AND PolNo = ?");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getDealType() == null || this.getDealType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, this.getDealType());
			}
			if(this.getDataType() == null || this.getDataType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDataType());
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPayCode());
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPayType());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getName());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBankCode());
			}
			if(this.getAccType() == null || this.getAccType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccType());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAccName());
			}
			if(this.getAccNo() == null || this.getAccNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAccNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getPolNo());
			}
			if(this.getNoType() == null || this.getNoType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getNoType());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getComCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentCode());
			}
			pstmt.setDouble(15, this.getPayMoney());
			if(this.getSendDate() == null || this.getSendDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getSendDate()));
			}
			if(this.getBankDealDate() == null || this.getBankDealDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getBankDealDate()));
			}
			if(this.getBankDealTime() == null || this.getBankDealTime().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getBankDealTime()));
			}
			if(this.getBankSuccFlag() == null || this.getBankSuccFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBankSuccFlag());
			}
			if(this.getVertifyFlag() == null || this.getVertifyFlag().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getVertifyFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getRemark());
			}
			if(this.getConvertFlag() == null || this.getConvertFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getConvertFlag());
			}
			if(this.getDoType() == null || this.getDoType().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getDoType());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getModifyTime());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getRiskCode());
			}
			pstmt.setInt(27, this.getSerNo());
			if(this.getBankName() == null || this.getBankName().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getBankName());
			}
			if(this.getInBankCode() == null || this.getInBankCode().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getInBankCode());
			}
			if(this.getInBankName() == null || this.getInBankName().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getInBankName());
			}
			if(this.getInAccType() == null || this.getInAccType().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getInAccType());
			}
			if(this.getInAccName() == null || this.getInAccName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getInAccName());
			}
			if(this.getInAccNo() == null || this.getInAccNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getInAccNo());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getIDNo());
			}
			if(this.getStandByFlag1() == null || this.getStandByFlag1().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getStandByFlag1());
			}
			if(this.getStandByFlag2() == null || this.getStandByFlag2().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getStandByFlag2());
			}
			if(this.getStandByFlag3() == null || this.getStandByFlag3().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getStandByFlag3());
			}
			if(this.getStandByFlag4() == null || this.getStandByFlag4().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getStandByFlag4());
			}
			if(this.getStandByFlag5() == null || this.getStandByFlag5().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getStandByFlag5());
			}
			// set where condition
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getSerialNo());
			}
			if(this.getDealType() == null || this.getDealType().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, StrTool.space1(this.getDealType(), 1));
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getPayCode());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getPolNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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
		SQLString sqlObj = new SQLString("LYReturnFromBank");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LYReturnFromBank(SerialNo ,DealType ,DataType ,PayCode ,PayType ,Name ,BankCode ,AccType ,AccName ,AccNo ,PolNo ,NoType ,ComCode ,AgentCode ,PayMoney ,SendDate ,BankDealDate ,BankDealTime ,BankSuccFlag ,VertifyFlag ,Remark ,ConvertFlag ,DoType ,ModifyDate ,ModifyTime ,RiskCode ,SerNo ,BankName ,InBankCode ,InBankName ,InAccType ,InAccName ,InAccNo ,IDType ,IDNo ,StandByFlag1 ,StandByFlag2 ,StandByFlag3 ,StandByFlag4 ,StandByFlag5) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getDealType() == null || this.getDealType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, this.getDealType());
			}
			if(this.getDataType() == null || this.getDataType().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getDataType());
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPayCode());
			}
			if(this.getPayType() == null || this.getPayType().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getPayType());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getName());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBankCode());
			}
			if(this.getAccType() == null || this.getAccType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getAccType());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getAccName());
			}
			if(this.getAccNo() == null || this.getAccNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getAccNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getPolNo());
			}
			if(this.getNoType() == null || this.getNoType().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getNoType());
			}
			if(this.getComCode() == null || this.getComCode().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getComCode());
			}
			if(this.getAgentCode() == null || this.getAgentCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getAgentCode());
			}
			pstmt.setDouble(15, this.getPayMoney());
			if(this.getSendDate() == null || this.getSendDate().equals("null")) {
				pstmt.setNull(16, 91);
			} else {
				pstmt.setDate(16, Date.valueOf(this.getSendDate()));
			}
			if(this.getBankDealDate() == null || this.getBankDealDate().equals("null")) {
				pstmt.setNull(17, 91);
			} else {
				pstmt.setDate(17, Date.valueOf(this.getBankDealDate()));
			}
			if(this.getBankDealTime() == null || this.getBankDealTime().equals("null")) {
				pstmt.setNull(18, 91);
			} else {
				pstmt.setDate(18, Date.valueOf(this.getBankDealTime()));
			}
			if(this.getBankSuccFlag() == null || this.getBankSuccFlag().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getBankSuccFlag());
			}
			if(this.getVertifyFlag() == null || this.getVertifyFlag().equals("null")) {
				pstmt.setNull(20, 1);
			} else {
				pstmt.setString(20, this.getVertifyFlag());
			}
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getRemark());
			}
			if(this.getConvertFlag() == null || this.getConvertFlag().equals("null")) {
				pstmt.setNull(22, 1);
			} else {
				pstmt.setString(22, this.getConvertFlag());
			}
			if(this.getDoType() == null || this.getDoType().equals("null")) {
				pstmt.setNull(23, 1);
			} else {
				pstmt.setString(23, this.getDoType());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getModifyTime());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getRiskCode());
			}
			pstmt.setInt(27, this.getSerNo());
			if(this.getBankName() == null || this.getBankName().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getBankName());
			}
			if(this.getInBankCode() == null || this.getInBankCode().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getInBankCode());
			}
			if(this.getInBankName() == null || this.getInBankName().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getInBankName());
			}
			if(this.getInAccType() == null || this.getInAccType().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getInAccType());
			}
			if(this.getInAccName() == null || this.getInAccName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getInAccName());
			}
			if(this.getInAccNo() == null || this.getInAccNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getInAccNo());
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(34, 1);
			} else {
				pstmt.setString(34, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getIDNo());
			}
			if(this.getStandByFlag1() == null || this.getStandByFlag1().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getStandByFlag1());
			}
			if(this.getStandByFlag2() == null || this.getStandByFlag2().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getStandByFlag2());
			}
			if(this.getStandByFlag3() == null || this.getStandByFlag3().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getStandByFlag3());
			}
			if(this.getStandByFlag4() == null || this.getStandByFlag4().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getStandByFlag4());
			}
			if(this.getStandByFlag5() == null || this.getStandByFlag5().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getStandByFlag5());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LYReturnFromBank WHERE  SerialNo = ? AND DealType = ? AND PayCode = ? AND PolNo = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getSerialNo());
			}
			if(this.getDealType() == null || this.getDealType().equals("null")) {
				pstmt.setNull(2, 1);
			} else {
				pstmt.setString(2, StrTool.space1(this.getDealType(), 1));
			}
			if(this.getPayCode() == null || this.getPayCode().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getPayCode());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolNo());
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
					tError.moduleName = "LYReturnFromBankDB";
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
			tError.moduleName = "LYReturnFromBankDB";
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

	public LYReturnFromBankSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LYReturnFromBankSet aLYReturnFromBankSet = new LYReturnFromBankSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LYReturnFromBank");
			LYReturnFromBankSchema aSchema = this.getSchema();
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
				LYReturnFromBankSchema s1 = new LYReturnFromBankSchema();
				s1.setSchema(rs,i);
				aLYReturnFromBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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

		return aLYReturnFromBankSet;

	}

	public LYReturnFromBankSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LYReturnFromBankSet aLYReturnFromBankSet = new LYReturnFromBankSet();

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
				LYReturnFromBankSchema s1 = new LYReturnFromBankSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LYReturnFromBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLYReturnFromBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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

		return aLYReturnFromBankSet;
	}

	public LYReturnFromBankSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LYReturnFromBankSet aLYReturnFromBankSet = new LYReturnFromBankSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LYReturnFromBank");
			LYReturnFromBankSchema aSchema = this.getSchema();
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

				LYReturnFromBankSchema s1 = new LYReturnFromBankSchema();
				s1.setSchema(rs,i);
				aLYReturnFromBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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

		return aLYReturnFromBankSet;

	}

	public LYReturnFromBankSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LYReturnFromBankSet aLYReturnFromBankSet = new LYReturnFromBankSet();

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

				LYReturnFromBankSchema s1 = new LYReturnFromBankSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LYReturnFromBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLYReturnFromBankSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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

		return aLYReturnFromBankSet;
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
			SQLString sqlObj = new SQLString("LYReturnFromBank");
			LYReturnFromBankSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LYReturnFromBank " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LYReturnFromBankDB";
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
			tError.moduleName = "LYReturnFromBankDB";
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
        tError.moduleName = "LYReturnFromBankDB";
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
        tError.moduleName = "LYReturnFromBankDB";
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
        tError.moduleName = "LYReturnFromBankDB";
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
        tError.moduleName = "LYReturnFromBankDB";
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
 * @return LYReturnFromBankSet
 */
public LYReturnFromBankSet getData()
{
    int tCount = 0;
    LYReturnFromBankSet tLYReturnFromBankSet = new LYReturnFromBankSet();
    LYReturnFromBankSchema tLYReturnFromBankSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LYReturnFromBankDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLYReturnFromBankSchema = new LYReturnFromBankSchema();
        tLYReturnFromBankSchema.setSchema(mResultSet, 1);
        tLYReturnFromBankSet.add(tLYReturnFromBankSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLYReturnFromBankSchema = new LYReturnFromBankSchema();
                tLYReturnFromBankSchema.setSchema(mResultSet, 1);
                tLYReturnFromBankSet.add(tLYReturnFromBankSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LYReturnFromBankDB";
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
    return tLYReturnFromBankSet;
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
            tError.moduleName = "LYReturnFromBankDB";
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
        tError.moduleName = "LYReturnFromBankDB";
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
            tError.moduleName = "LYReturnFromBankDB";
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
        tError.moduleName = "LYReturnFromBankDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LYReturnFromBankSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LYReturnFromBankSet aLYReturnFromBankSet = new LYReturnFromBankSet();

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
				LYReturnFromBankSchema s1 = new LYReturnFromBankSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LYReturnFromBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLYReturnFromBankSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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

		return aLYReturnFromBankSet;
	}

	public LYReturnFromBankSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LYReturnFromBankSet aLYReturnFromBankSet = new LYReturnFromBankSet();

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

				LYReturnFromBankSchema s1 = new LYReturnFromBankSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LYReturnFromBankDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLYReturnFromBankSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYReturnFromBankDB";
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

		return aLYReturnFromBankSet; 
	}

}
