/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LLFeeMainSchema;
import com.sinosoft.lis.vschema.LLFeeMainSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLFeeMainDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLFeeMainDBSet extends LLFeeMainSet
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
	public LLFeeMainDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLFeeMain");
		mflag = true;
	}

	public LLFeeMainDBSet()
	{
		db = new DBOper( "LLFeeMain" );
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
			tError.moduleName = "LLFeeMainDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLFeeMain WHERE  ClmNo = ? AND CaseNo = ? AND MainFeeNo = ? AND CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getMainFeeNo() == null || this.get(i).getMainFeeNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMainFeeNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLFeeMain");
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
			tError.moduleName = "LLFeeMainDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLFeeMain SET  ClmNo = ? , CaseNo = ? , MainFeeNo = ? , CaseRelaNo = ? , RgtNo = ? , AffixNo = ? , SerialNo = ? , CustomerNo = ? , CustomerName = ? , CustomerSex = ? , HospitalCode = ? , HospitalName = ? , HospitalGrade = ? , DutyKind = ? , ReceiptNo = ? , FeeType = ? , FeeAtti = ? , FeeAffixType = ? , FeeDate = ? , HospStartDate = ? , HospEndDate = ? , RealHospDate = ? , SeriousWard = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , Currency = ? , BillMoney = ? , RefuseMoney = ? , AdjSum = ? , DeductDesc = ? , Remark = ? , OtherExplain = ? , ECMNO = ? , AccResult1 = ? , AccResult2 = ? , ComCode = ? , ModifyOperator = ? WHERE  ClmNo = ? AND CaseNo = ? AND MainFeeNo = ? AND CustomerNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getMainFeeNo() == null || this.get(i).getMainFeeNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMainFeeNo());
			}
			if(this.get(i).getCaseRelaNo() == null || this.get(i).getCaseRelaNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCaseRelaNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRgtNo());
			}
			if(this.get(i).getAffixNo() == null || this.get(i).getAffixNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAffixNo());
			}
			pstmt.setInt(7, this.get(i).getSerialNo());
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerName() == null || this.get(i).getCustomerName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCustomerName());
			}
			if(this.get(i).getCustomerSex() == null || this.get(i).getCustomerSex().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCustomerSex());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getHospitalCode());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getHospitalName());
			}
			if(this.get(i).getHospitalGrade() == null || this.get(i).getHospitalGrade().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getHospitalGrade());
			}
			if(this.get(i).getDutyKind() == null || this.get(i).getDutyKind().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getDutyKind());
			}
			if(this.get(i).getReceiptNo() == null || this.get(i).getReceiptNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getReceiptNo());
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getFeeType());
			}
			if(this.get(i).getFeeAtti() == null || this.get(i).getFeeAtti().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getFeeAtti());
			}
			if(this.get(i).getFeeAffixType() == null || this.get(i).getFeeAffixType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getFeeAffixType());
			}
			if(this.get(i).getFeeDate() == null || this.get(i).getFeeDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getFeeDate()));
			}
			if(this.get(i).getHospStartDate() == null || this.get(i).getHospStartDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getHospStartDate()));
			}
			if(this.get(i).getHospEndDate() == null || this.get(i).getHospEndDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getHospEndDate()));
			}
			pstmt.setInt(22, this.get(i).getRealHospDate());
			pstmt.setInt(23, this.get(i).getSeriousWard());
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getModifyTime());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCurrency());
			}
			pstmt.setDouble(31, this.get(i).getBillMoney());
			pstmt.setDouble(32, this.get(i).getRefuseMoney());
			pstmt.setDouble(33, this.get(i).getAdjSum());
			if(this.get(i).getDeductDesc() == null || this.get(i).getDeductDesc().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getDeductDesc());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getRemark());
			}
			if(this.get(i).getOtherExplain() == null || this.get(i).getOtherExplain().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOtherExplain());
			}
			if(this.get(i).getECMNO() == null || this.get(i).getECMNO().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getECMNO());
			}
			if(this.get(i).getAccResult1() == null || this.get(i).getAccResult1().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAccResult1());
			}
			if(this.get(i).getAccResult2() == null || this.get(i).getAccResult2().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getAccResult2());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getModifyOperator());
			}
			// set where condition
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getCaseNo());
			}
			if(this.get(i).getMainFeeNo() == null || this.get(i).getMainFeeNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getMainFeeNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getCustomerNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLFeeMain");
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
			tError.moduleName = "LLFeeMainDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLFeeMain VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCaseNo());
			}
			if(this.get(i).getMainFeeNo() == null || this.get(i).getMainFeeNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getMainFeeNo());
			}
			if(this.get(i).getCaseRelaNo() == null || this.get(i).getCaseRelaNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getCaseRelaNo());
			}
			if(this.get(i).getRgtNo() == null || this.get(i).getRgtNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getRgtNo());
			}
			if(this.get(i).getAffixNo() == null || this.get(i).getAffixNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAffixNo());
			}
			pstmt.setInt(7, this.get(i).getSerialNo());
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerName() == null || this.get(i).getCustomerName().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getCustomerName());
			}
			if(this.get(i).getCustomerSex() == null || this.get(i).getCustomerSex().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCustomerSex());
			}
			if(this.get(i).getHospitalCode() == null || this.get(i).getHospitalCode().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getHospitalCode());
			}
			if(this.get(i).getHospitalName() == null || this.get(i).getHospitalName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getHospitalName());
			}
			if(this.get(i).getHospitalGrade() == null || this.get(i).getHospitalGrade().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getHospitalGrade());
			}
			if(this.get(i).getDutyKind() == null || this.get(i).getDutyKind().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getDutyKind());
			}
			if(this.get(i).getReceiptNo() == null || this.get(i).getReceiptNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getReceiptNo());
			}
			if(this.get(i).getFeeType() == null || this.get(i).getFeeType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getFeeType());
			}
			if(this.get(i).getFeeAtti() == null || this.get(i).getFeeAtti().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getFeeAtti());
			}
			if(this.get(i).getFeeAffixType() == null || this.get(i).getFeeAffixType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getFeeAffixType());
			}
			if(this.get(i).getFeeDate() == null || this.get(i).getFeeDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getFeeDate()));
			}
			if(this.get(i).getHospStartDate() == null || this.get(i).getHospStartDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getHospStartDate()));
			}
			if(this.get(i).getHospEndDate() == null || this.get(i).getHospEndDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getHospEndDate()));
			}
			pstmt.setInt(22, this.get(i).getRealHospDate());
			pstmt.setInt(23, this.get(i).getSeriousWard());
			if(this.get(i).getMngCom() == null || this.get(i).getMngCom().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getMngCom());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getModifyTime());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getCurrency());
			}
			pstmt.setDouble(31, this.get(i).getBillMoney());
			pstmt.setDouble(32, this.get(i).getRefuseMoney());
			pstmt.setDouble(33, this.get(i).getAdjSum());
			if(this.get(i).getDeductDesc() == null || this.get(i).getDeductDesc().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getDeductDesc());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getRemark());
			}
			if(this.get(i).getOtherExplain() == null || this.get(i).getOtherExplain().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOtherExplain());
			}
			if(this.get(i).getECMNO() == null || this.get(i).getECMNO().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getECMNO());
			}
			if(this.get(i).getAccResult1() == null || this.get(i).getAccResult1().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getAccResult1());
			}
			if(this.get(i).getAccResult2() == null || this.get(i).getAccResult2().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getAccResult2());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getModifyOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLFeeMain");
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
			tError.moduleName = "LLFeeMainDBSet";
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
