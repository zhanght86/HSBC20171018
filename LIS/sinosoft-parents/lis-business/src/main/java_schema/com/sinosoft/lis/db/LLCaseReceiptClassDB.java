/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sinosoft.lis.schema.LLCaseReceiptClassSchema;
import com.sinosoft.lis.vschema.LLCaseReceiptClassSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLCaseReceiptClassDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCaseReceiptClassDB extends LLCaseReceiptClassSchema
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
	public LLCaseReceiptClassDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLCaseReceiptClass" );
		mflag = true;
	}

	public LLCaseReceiptClassDB()
	{
		con = null;
		db = new DBOper( "LLCaseReceiptClass" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLCaseReceiptClassSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLCaseReceiptClassSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLCaseReceiptClass WHERE  ID = ?");
			pstmt.setInt(1, this.getID());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCaseReceiptClass");
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
		SQLString sqlObj = new SQLString("LLCaseReceiptClass");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLCaseReceiptClass SET  ClmNo = ? , MngCom = ? , GrpContNo = ? , InsuredNo = ? , Name = ? , ID = ? , BillNo = ? , ReceiptDate = ? , FeeItemType = ? , TotalFee = ? , StartDate = ? , EndDate = ? , ValidFlag = ? , Col1 = ? , Rate1 = ? , AdjFee1 = ? , Reason1 = ? , Col2 = ? , Rate2 = ? , AdjFee2 = ? , Reason2 = ? , Col3 = ? , Rate3 = ? , AdjFee3 = ? , Reason3 = ? , Col4 = ? , Rate4 = ? , AdjFee4 = ? , Reason4 = ? , Col5 = ? , Rate5 = ? , AdjFee5 = ? , Reason5 = ? , Col6 = ? , Rate6 = ? , AdjFee6 = ? , Reason6 = ? , Col7 = ? , Rate7 = ? , AdjFee7 = ? , Reason7 = ? , Col8 = ? , Rate8 = ? , AdjFee8 = ? , Reason8 = ? , Col9 = ? , Rate9 = ? , AdjFee9 = ? , Reason9 = ? , Col10 = ? , Rate10 = ? , AdjFee10 = ? , Reason10 = ? , Reason = ? , HospitalCode = ? , UWFlag = ? , UWDate = ? , UWOperator = ? , TotalAdjFee = ? , FeeItemCode = ? , FeeItemName = ? , ModifyDate = ? , ModifyTime = ? , LastOperator = ? , MakeDate = ? , MakeTime = ? , Operator = ? WHERE  ID = ?");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getMngCom());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuredNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getName());
			}
			pstmt.setInt(6, this.getID());
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBillNo());
			}
			if(this.getReceiptDate() == null || this.getReceiptDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getReceiptDate()));
			}
			if(this.getFeeItemType() == null || this.getFeeItemType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getFeeItemType());
			}
			pstmt.setDouble(10, this.getTotalFee());
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getEndDate()));
			}
			if(this.getValidFlag() == null || this.getValidFlag().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getValidFlag());
			}
			if(this.getCol1() == null || this.getCol1().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCol1());
			}
			pstmt.setDouble(15, this.getRate1());
			pstmt.setDouble(16, this.getAdjFee1());
			if(this.getReason1() == null || this.getReason1().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getReason1());
			}
			if(this.getCol2() == null || this.getCol2().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCol2());
			}
			pstmt.setDouble(19, this.getRate2());
			pstmt.setDouble(20, this.getAdjFee2());
			if(this.getReason2() == null || this.getReason2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getReason2());
			}
			if(this.getCol3() == null || this.getCol3().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getCol3());
			}
			pstmt.setDouble(23, this.getRate3());
			pstmt.setDouble(24, this.getAdjFee3());
			if(this.getReason3() == null || this.getReason3().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getReason3());
			}
			if(this.getCol4() == null || this.getCol4().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCol4());
			}
			pstmt.setDouble(27, this.getRate4());
			pstmt.setDouble(28, this.getAdjFee4());
			if(this.getReason4() == null || this.getReason4().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getReason4());
			}
			if(this.getCol5() == null || this.getCol5().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getCol5());
			}
			pstmt.setDouble(31, this.getRate5());
			pstmt.setDouble(32, this.getAdjFee5());
			if(this.getReason5() == null || this.getReason5().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getReason5());
			}
			if(this.getCol6() == null || this.getCol6().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getCol6());
			}
			pstmt.setDouble(35, this.getRate6());
			pstmt.setDouble(36, this.getAdjFee6());
			if(this.getReason6() == null || this.getReason6().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getReason6());
			}
			if(this.getCol7() == null || this.getCol7().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getCol7());
			}
			pstmt.setDouble(39, this.getRate7());
			pstmt.setDouble(40, this.getAdjFee7());
			if(this.getReason7() == null || this.getReason7().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getReason7());
			}
			if(this.getCol8() == null || this.getCol8().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCol8());
			}
			pstmt.setDouble(43, this.getRate8());
			pstmt.setDouble(44, this.getAdjFee8());
			if(this.getReason8() == null || this.getReason8().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getReason8());
			}
			if(this.getCol9() == null || this.getCol9().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getCol9());
			}
			pstmt.setDouble(47, this.getRate9());
			pstmt.setDouble(48, this.getAdjFee9());
			if(this.getReason9() == null || this.getReason9().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getReason9());
			}
			if(this.getCol10() == null || this.getCol10().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getCol10());
			}
			pstmt.setDouble(51, this.getRate10());
			pstmt.setDouble(52, this.getAdjFee10());
			if(this.getReason10() == null || this.getReason10().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getReason10());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getReason());
			}
			if(this.getHospitalCode() == null || this.getHospitalCode().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getHospitalCode());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getUWFlag());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getUWOperator());
			}
			pstmt.setDouble(59, this.getTotalAdjFee());
			if(this.getFeeItemCode() == null || this.getFeeItemCode().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getFeeItemCode());
			}
			if(this.getFeeItemName() == null || this.getFeeItemName().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getFeeItemName());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(62, 91);
			} else {
				pstmt.setDate(62, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getModifyTime());
			}
			if(this.getLastOperator() == null || this.getLastOperator().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getLastOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getMakeTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getOperator());
			}
			// set where condition
			pstmt.setInt(68, this.getID());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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
		SQLString sqlObj = new SQLString("LLCaseReceiptClass");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLCaseReceiptClass VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getClmNo());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getMngCom());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getGrpContNo());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getInsuredNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getName());
			}
			pstmt.setInt(6, this.getID());
			if(this.getBillNo() == null || this.getBillNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBillNo());
			}
			if(this.getReceiptDate() == null || this.getReceiptDate().equals("null")) {
				pstmt.setNull(8, 91);
			} else {
				pstmt.setDate(8, Date.valueOf(this.getReceiptDate()));
			}
			if(this.getFeeItemType() == null || this.getFeeItemType().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getFeeItemType());
			}
			pstmt.setDouble(10, this.getTotalFee());
			if(this.getStartDate() == null || this.getStartDate().equals("null")) {
				pstmt.setNull(11, 91);
			} else {
				pstmt.setDate(11, Date.valueOf(this.getStartDate()));
			}
			if(this.getEndDate() == null || this.getEndDate().equals("null")) {
				pstmt.setNull(12, 91);
			} else {
				pstmt.setDate(12, Date.valueOf(this.getEndDate()));
			}
			if(this.getValidFlag() == null || this.getValidFlag().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getValidFlag());
			}
			if(this.getCol1() == null || this.getCol1().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getCol1());
			}
			pstmt.setDouble(15, this.getRate1());
			pstmt.setDouble(16, this.getAdjFee1());
			if(this.getReason1() == null || this.getReason1().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getReason1());
			}
			if(this.getCol2() == null || this.getCol2().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getCol2());
			}
			pstmt.setDouble(19, this.getRate2());
			pstmt.setDouble(20, this.getAdjFee2());
			if(this.getReason2() == null || this.getReason2().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getReason2());
			}
			if(this.getCol3() == null || this.getCol3().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getCol3());
			}
			pstmt.setDouble(23, this.getRate3());
			pstmt.setDouble(24, this.getAdjFee3());
			if(this.getReason3() == null || this.getReason3().equals("null")) {
				pstmt.setNull(25, 12);
			} else {
				pstmt.setString(25, this.getReason3());
			}
			if(this.getCol4() == null || this.getCol4().equals("null")) {
				pstmt.setNull(26, 12);
			} else {
				pstmt.setString(26, this.getCol4());
			}
			pstmt.setDouble(27, this.getRate4());
			pstmt.setDouble(28, this.getAdjFee4());
			if(this.getReason4() == null || this.getReason4().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getReason4());
			}
			if(this.getCol5() == null || this.getCol5().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getCol5());
			}
			pstmt.setDouble(31, this.getRate5());
			pstmt.setDouble(32, this.getAdjFee5());
			if(this.getReason5() == null || this.getReason5().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getReason5());
			}
			if(this.getCol6() == null || this.getCol6().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getCol6());
			}
			pstmt.setDouble(35, this.getRate6());
			pstmt.setDouble(36, this.getAdjFee6());
			if(this.getReason6() == null || this.getReason6().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getReason6());
			}
			if(this.getCol7() == null || this.getCol7().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getCol7());
			}
			pstmt.setDouble(39, this.getRate7());
			pstmt.setDouble(40, this.getAdjFee7());
			if(this.getReason7() == null || this.getReason7().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getReason7());
			}
			if(this.getCol8() == null || this.getCol8().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getCol8());
			}
			pstmt.setDouble(43, this.getRate8());
			pstmt.setDouble(44, this.getAdjFee8());
			if(this.getReason8() == null || this.getReason8().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getReason8());
			}
			if(this.getCol9() == null || this.getCol9().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getCol9());
			}
			pstmt.setDouble(47, this.getRate9());
			pstmt.setDouble(48, this.getAdjFee9());
			if(this.getReason9() == null || this.getReason9().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getReason9());
			}
			if(this.getCol10() == null || this.getCol10().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getCol10());
			}
			pstmt.setDouble(51, this.getRate10());
			pstmt.setDouble(52, this.getAdjFee10());
			if(this.getReason10() == null || this.getReason10().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getReason10());
			}
			if(this.getReason() == null || this.getReason().equals("null")) {
				pstmt.setNull(54, 12);
			} else {
				pstmt.setString(54, this.getReason());
			}
			if(this.getHospitalCode() == null || this.getHospitalCode().equals("null")) {
				pstmt.setNull(55, 12);
			} else {
				pstmt.setString(55, this.getHospitalCode());
			}
			if(this.getUWFlag() == null || this.getUWFlag().equals("null")) {
				pstmt.setNull(56, 12);
			} else {
				pstmt.setString(56, this.getUWFlag());
			}
			if(this.getUWDate() == null || this.getUWDate().equals("null")) {
				pstmt.setNull(57, 91);
			} else {
				pstmt.setDate(57, Date.valueOf(this.getUWDate()));
			}
			if(this.getUWOperator() == null || this.getUWOperator().equals("null")) {
				pstmt.setNull(58, 12);
			} else {
				pstmt.setString(58, this.getUWOperator());
			}
			pstmt.setDouble(59, this.getTotalAdjFee());
			if(this.getFeeItemCode() == null || this.getFeeItemCode().equals("null")) {
				pstmt.setNull(60, 12);
			} else {
				pstmt.setString(60, this.getFeeItemCode());
			}
			if(this.getFeeItemName() == null || this.getFeeItemName().equals("null")) {
				pstmt.setNull(61, 12);
			} else {
				pstmt.setString(61, this.getFeeItemName());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(62, 91);
			} else {
				pstmt.setDate(62, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(63, 12);
			} else {
				pstmt.setString(63, this.getModifyTime());
			}
			if(this.getLastOperator() == null || this.getLastOperator().equals("null")) {
				pstmt.setNull(64, 12);
			} else {
				pstmt.setString(64, this.getLastOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(65, 91);
			} else {
				pstmt.setDate(65, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(66, 12);
			} else {
				pstmt.setString(66, this.getMakeTime());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(67, 12);
			} else {
				pstmt.setString(67, this.getOperator());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLCaseReceiptClass WHERE  ID = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, this.getID());
			rs = pstmt.executeQuery();
			int i = 0;
			while (rs.next())
			{
				i++;
				if (!this.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLCaseReceiptClassDB";
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
			tError.moduleName = "LLCaseReceiptClassDB";
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

	public LLCaseReceiptClassSet query()
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLCaseReceiptClassSet aLLCaseReceiptClassSet = new LLCaseReceiptClassSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLCaseReceiptClass");
			LLCaseReceiptClassSchema aSchema = this.getSchema();
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
				LLCaseReceiptClassSchema s1 = new LLCaseReceiptClassSchema();
				s1.setSchema(rs,i);
				aLLCaseReceiptClassSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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

		return aLLCaseReceiptClassSet;
	}

	public LLCaseReceiptClassSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLCaseReceiptClassSet aLLCaseReceiptClassSet = new LLCaseReceiptClassSet();

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
				LLCaseReceiptClassSchema s1 = new LLCaseReceiptClassSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLCaseReceiptClassDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLCaseReceiptClassSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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

		return aLLCaseReceiptClassSet;
	}

	public LLCaseReceiptClassSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLCaseReceiptClassSet aLLCaseReceiptClassSet = new LLCaseReceiptClassSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLCaseReceiptClass");
			LLCaseReceiptClassSchema aSchema = this.getSchema();
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

				LLCaseReceiptClassSchema s1 = new LLCaseReceiptClassSchema();
				s1.setSchema(rs,i);
				aLLCaseReceiptClassSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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

		return aLLCaseReceiptClassSet;
	}

	public LLCaseReceiptClassSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLCaseReceiptClassSet aLLCaseReceiptClassSet = new LLCaseReceiptClassSet();

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

				LLCaseReceiptClassSchema s1 = new LLCaseReceiptClassSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLCaseReceiptClassDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLCaseReceiptClassSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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

		return aLLCaseReceiptClassSet;
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
			SQLString sqlObj = new SQLString("LLCaseReceiptClass");
			LLCaseReceiptClassSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLCaseReceiptClass " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLCaseReceiptClassDB";
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
			tError.moduleName = "LLCaseReceiptClassDB";
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
        tError.moduleName = "LLCaseReceiptClassDB";
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
        tError.moduleName = "LLCaseReceiptClassDB";
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
        tError.moduleName = "LLCaseReceiptClassDB";
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
        tError.moduleName = "LLCaseReceiptClassDB";
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
 * @return LLCaseReceiptClassSet
 */
public LLCaseReceiptClassSet getData()
{
    int tCount = 0;
    LLCaseReceiptClassSet tLLCaseReceiptClassSet = new LLCaseReceiptClassSet();
    LLCaseReceiptClassSchema tLLCaseReceiptClassSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLCaseReceiptClassDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLCaseReceiptClassSchema = new LLCaseReceiptClassSchema();
        tLLCaseReceiptClassSchema.setSchema(mResultSet, 1);
        tLLCaseReceiptClassSet.add(tLLCaseReceiptClassSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLCaseReceiptClassSchema = new LLCaseReceiptClassSchema();
                tLLCaseReceiptClassSchema.setSchema(mResultSet, 1);
                tLLCaseReceiptClassSet.add(tLLCaseReceiptClassSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLCaseReceiptClassDB";
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
    return tLLCaseReceiptClassSet;
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
            tError.moduleName = "LLCaseReceiptClassDB";
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
        tError.moduleName = "LLCaseReceiptClassDB";
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
            tError.moduleName = "LLCaseReceiptClassDB";
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
        tError.moduleName = "LLCaseReceiptClassDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLCaseReceiptClassSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLCaseReceiptClassSet aLLCaseReceiptClassSet = new LLCaseReceiptClassSet();

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
				LLCaseReceiptClassSchema s1 = new LLCaseReceiptClassSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLCaseReceiptClassDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLCaseReceiptClassSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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

		return aLLCaseReceiptClassSet;
	}

	public LLCaseReceiptClassSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLCaseReceiptClassSet aLLCaseReceiptClassSet = new LLCaseReceiptClassSet();

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

				LLCaseReceiptClassSchema s1 = new LLCaseReceiptClassSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLCaseReceiptClassDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLCaseReceiptClassSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassDB";
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

		return aLLCaseReceiptClassSet; 
	}

}
