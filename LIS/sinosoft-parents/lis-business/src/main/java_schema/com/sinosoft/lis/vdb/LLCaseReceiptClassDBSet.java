/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLCaseReceiptClassSchema;
import com.sinosoft.lis.vschema.LLCaseReceiptClassSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLCaseReceiptClassDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCaseReceiptClassDBSet extends LLCaseReceiptClassSet
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
	public LLCaseReceiptClassDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLCaseReceiptClass");
		mflag = true;
	}

	public LLCaseReceiptClassDBSet()
	{
		db = new DBOper( "LLCaseReceiptClass" );
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
			tError.moduleName = "LLCaseReceiptClassDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLCaseReceiptClass WHERE  ID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			pstmt.setInt(1, this.get(i).getID());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCaseReceiptClass");
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
			tError.moduleName = "LLCaseReceiptClassDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLCaseReceiptClass SET  ClmNo = ? , MngCom = ? , GrpContNo = ? , InsuredNo = ? , Name = ? , ID = ? , BillNo = ? , ReceiptDate = ? , FeeItemType = ? , TotalFee = ? , StartDate = ? , EndDate = ? , ValidFlag = ? , Col1 = ? , Rate1 = ? , AdjFee1 = ? , Reason1 = ? , Col2 = ? , Rate2 = ? , AdjFee2 = ? , Reason2 = ? , Col3 = ? , Rate3 = ? , AdjFee3 = ? , Reason3 = ? , Col4 = ? , Rate4 = ? , AdjFee4 = ? , Reason4 = ? , Col5 = ? , Rate5 = ? , AdjFee5 = ? , Reason5 = ? , Col6 = ? , Rate6 = ? , AdjFee6 = ? , Reason6 = ? , Col7 = ? , Rate7 = ? , AdjFee7 = ? , Reason7 = ? , Col8 = ? , Rate8 = ? , AdjFee8 = ? , Reason8 = ? , Col9 = ? , Rate9 = ? , AdjFee9 = ? , Reason9 = ? , Col10 = ? , Rate10 = ? , AdjFee10 = ? , Reason10 = ? , Reason = ? , HospitalCode = ? , UWFlag = ? , UWDate = ? , UWOperator = ? , TotalAdjFee = ? , FeeItemCode = ? , FeeItemName = ? , ModifyDate = ? , ModifyTime = ? , LastOperator = ? , MakeDate = ? , MakeTime = ? , Operator = ? WHERE  ID = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMngCom());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInsuredNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getName());
			}
			pstmt.setInt(6, this.get(i).getID());
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBillNo());
			}
			if(this.get(i).getReceiptDate() == null || this.get(i).getReceiptDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getReceiptDate()));
			}
			if(this.get(i).getFeeItemType() == null || this.get(i).getFeeItemType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getFeeItemType());
			}
			pstmt.setDouble(10, this.get(i).getTotalFee());
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getValidFlag() == null || this.get(i).getValidFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getValidFlag());
			}
			if(this.get(i).getCol1() == null || this.get(i).getCol1().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCol1());
			}
			pstmt.setDouble(15, this.get(i).getRate1());
			pstmt.setDouble(16, this.get(i).getAdjFee1());
			if(this.get(i).getReason1() == null || this.get(i).getReason1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getReason1());
			}
			if(this.get(i).getCol2() == null || this.get(i).getCol2().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCol2());
			}
			pstmt.setDouble(19, this.get(i).getRate2());
			pstmt.setDouble(20, this.get(i).getAdjFee2());
			if(this.get(i).getReason2() == null || this.get(i).getReason2().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getReason2());
			}
			if(this.get(i).getCol3() == null || this.get(i).getCol3().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getCol3());
			}
			pstmt.setDouble(23, this.get(i).getRate3());
			pstmt.setDouble(24, this.get(i).getAdjFee3());
			if(this.get(i).getReason3() == null || this.get(i).getReason3().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getReason3());
			}
			if(this.get(i).getCol4() == null || this.get(i).getCol4().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCol4());
			}
			pstmt.setDouble(27, this.get(i).getRate4());
			pstmt.setDouble(28, this.get(i).getAdjFee4());
			if(this.get(i).getReason4() == null || this.get(i).getReason4().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getReason4());
			}
			if(this.get(i).getCol5() == null || this.get(i).getCol5().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCol5());
			}
			pstmt.setDouble(31, this.get(i).getRate5());
			pstmt.setDouble(32, this.get(i).getAdjFee5());
			if(this.get(i).getReason5() == null || this.get(i).getReason5().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getReason5());
			}
			if(this.get(i).getCol6() == null || this.get(i).getCol6().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getCol6());
			}
			pstmt.setDouble(35, this.get(i).getRate6());
			pstmt.setDouble(36, this.get(i).getAdjFee6());
			if(this.get(i).getReason6() == null || this.get(i).getReason6().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getReason6());
			}
			if(this.get(i).getCol7() == null || this.get(i).getCol7().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getCol7());
			}
			pstmt.setDouble(39, this.get(i).getRate7());
			pstmt.setDouble(40, this.get(i).getAdjFee7());
			if(this.get(i).getReason7() == null || this.get(i).getReason7().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getReason7());
			}
			if(this.get(i).getCol8() == null || this.get(i).getCol8().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCol8());
			}
			pstmt.setDouble(43, this.get(i).getRate8());
			pstmt.setDouble(44, this.get(i).getAdjFee8());
			if(this.get(i).getReason8() == null || this.get(i).getReason8().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getReason8());
			}
			if(this.get(i).getCol9() == null || this.get(i).getCol9().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getCol9());
			}
			pstmt.setDouble(47, this.get(i).getRate9());
			pstmt.setDouble(48, this.get(i).getAdjFee9());
			if(this.get(i).getReason9() == null || this.get(i).getReason9().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getReason9());
			}
			if(this.get(i).getCol10() == null || this.get(i).getCol10().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getCol10());
			}
			pstmt.setDouble(51, this.get(i).getRate10());
			pstmt.setDouble(52, this.get(i).getAdjFee10());
			if(this.get(i).getReason10() == null || this.get(i).getReason10().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getReason10());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getReason());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getHospitalCode());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getUWOperator());
			}
			pstmt.setDouble(59, this.get(i).getTotalAdjFee());
			if(this.get(i).getFeeItemCode() == null || this.get(i).getFeeItemCode().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getFeeItemCode());
			}
			if(this.get(i).getFeeItemName() == null || this.get(i).getFeeItemName().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getFeeItemName());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getModifyTime());
			}
			if(this.get(i).getLastOperator() == null || this.get(i).getLastOperator().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getLastOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getMakeTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getOperator());
			}
			// set where condition
			pstmt.setInt(68, this.get(i).getID());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCaseReceiptClass");
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
			tError.moduleName = "LLCaseReceiptClassDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLCaseReceiptClass VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getMngCom());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInsuredNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getName());
			}
			pstmt.setInt(6, this.get(i).getID());
			if(this.get(i).getBillNo() == null || this.get(i).getBillNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBillNo());
			}
			if(this.get(i).getReceiptDate() == null || this.get(i).getReceiptDate().equals("null")) {
				pstmt.setDate(8,null);
			} else {
				pstmt.setDate(8, Date.valueOf(this.get(i).getReceiptDate()));
			}
			if(this.get(i).getFeeItemType() == null || this.get(i).getFeeItemType().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getFeeItemType());
			}
			pstmt.setDouble(10, this.get(i).getTotalFee());
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getEndDate() == null || this.get(i).getEndDate().equals("null")) {
				pstmt.setDate(12,null);
			} else {
				pstmt.setDate(12, Date.valueOf(this.get(i).getEndDate()));
			}
			if(this.get(i).getValidFlag() == null || this.get(i).getValidFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getValidFlag());
			}
			if(this.get(i).getCol1() == null || this.get(i).getCol1().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getCol1());
			}
			pstmt.setDouble(15, this.get(i).getRate1());
			pstmt.setDouble(16, this.get(i).getAdjFee1());
			if(this.get(i).getReason1() == null || this.get(i).getReason1().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getReason1());
			}
			if(this.get(i).getCol2() == null || this.get(i).getCol2().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getCol2());
			}
			pstmt.setDouble(19, this.get(i).getRate2());
			pstmt.setDouble(20, this.get(i).getAdjFee2());
			if(this.get(i).getReason2() == null || this.get(i).getReason2().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getReason2());
			}
			if(this.get(i).getCol3() == null || this.get(i).getCol3().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getCol3());
			}
			pstmt.setDouble(23, this.get(i).getRate3());
			pstmt.setDouble(24, this.get(i).getAdjFee3());
			if(this.get(i).getReason3() == null || this.get(i).getReason3().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getReason3());
			}
			if(this.get(i).getCol4() == null || this.get(i).getCol4().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getCol4());
			}
			pstmt.setDouble(27, this.get(i).getRate4());
			pstmt.setDouble(28, this.get(i).getAdjFee4());
			if(this.get(i).getReason4() == null || this.get(i).getReason4().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getReason4());
			}
			if(this.get(i).getCol5() == null || this.get(i).getCol5().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCol5());
			}
			pstmt.setDouble(31, this.get(i).getRate5());
			pstmt.setDouble(32, this.get(i).getAdjFee5());
			if(this.get(i).getReason5() == null || this.get(i).getReason5().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getReason5());
			}
			if(this.get(i).getCol6() == null || this.get(i).getCol6().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getCol6());
			}
			pstmt.setDouble(35, this.get(i).getRate6());
			pstmt.setDouble(36, this.get(i).getAdjFee6());
			if(this.get(i).getReason6() == null || this.get(i).getReason6().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getReason6());
			}
			if(this.get(i).getCol7() == null || this.get(i).getCol7().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getCol7());
			}
			pstmt.setDouble(39, this.get(i).getRate7());
			pstmt.setDouble(40, this.get(i).getAdjFee7());
			if(this.get(i).getReason7() == null || this.get(i).getReason7().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getReason7());
			}
			if(this.get(i).getCol8() == null || this.get(i).getCol8().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getCol8());
			}
			pstmt.setDouble(43, this.get(i).getRate8());
			pstmt.setDouble(44, this.get(i).getAdjFee8());
			if(this.get(i).getReason8() == null || this.get(i).getReason8().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getReason8());
			}
			if(this.get(i).getCol9() == null || this.get(i).getCol9().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getCol9());
			}
			pstmt.setDouble(47, this.get(i).getRate9());
			pstmt.setDouble(48, this.get(i).getAdjFee9());
			if(this.get(i).getReason9() == null || this.get(i).getReason9().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getReason9());
			}
			if(this.get(i).getCol10() == null || this.get(i).getCol10().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getCol10());
			}
			pstmt.setDouble(51, this.get(i).getRate10());
			pstmt.setDouble(52, this.get(i).getAdjFee10());
			if(this.get(i).getReason10() == null || this.get(i).getReason10().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getReason10());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getReason());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getHospitalCode());
			}
			if(this.get(i).getUWFlag() == null || this.get(i).getUWFlag().equals("null")) {
				pstmt.setString(56,null);
			} else {
				pstmt.setString(56, this.get(i).getUWFlag());
			}
			if(this.get(i).getUWDate() == null || this.get(i).getUWDate().equals("null")) {
				pstmt.setDate(57,null);
			} else {
				pstmt.setDate(57, Date.valueOf(this.get(i).getUWDate()));
			}
			if(this.get(i).getUWOperator() == null || this.get(i).getUWOperator().equals("null")) {
				pstmt.setString(58,null);
			} else {
				pstmt.setString(58, this.get(i).getUWOperator());
			}
			pstmt.setDouble(59, this.get(i).getTotalAdjFee());
			if(this.get(i).getFeeItemCode() == null || this.get(i).getFeeItemCode().equals("null")) {
				pstmt.setString(60,null);
			} else {
				pstmt.setString(60, this.get(i).getFeeItemCode());
			}
			if(this.get(i).getFeeItemName() == null || this.get(i).getFeeItemName().equals("null")) {
				pstmt.setString(61,null);
			} else {
				pstmt.setString(61, this.get(i).getFeeItemName());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(62,null);
			} else {
				pstmt.setDate(62, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(63,null);
			} else {
				pstmt.setString(63, this.get(i).getModifyTime());
			}
			if(this.get(i).getLastOperator() == null || this.get(i).getLastOperator().equals("null")) {
				pstmt.setString(64,null);
			} else {
				pstmt.setString(64, this.get(i).getLastOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(65,null);
			} else {
				pstmt.setDate(65, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(66,null);
			} else {
				pstmt.setString(66, this.get(i).getMakeTime());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(67,null);
			} else {
				pstmt.setString(67, this.get(i).getOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLCaseReceiptClass");
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
			tError.moduleName = "LLCaseReceiptClassDBSet";
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
