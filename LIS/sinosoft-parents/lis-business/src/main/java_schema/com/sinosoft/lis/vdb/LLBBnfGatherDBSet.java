/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLBBnfGatherSchema;
import com.sinosoft.lis.vschema.LLBBnfGatherSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLBBnfGatherDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LLBBnfGatherDBSet extends LLBBnfGatherSet
{
private static Logger logger = Logger.getLogger(LLBBnfGatherDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LLBBnfGatherDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LLBBnfGather");
		mflag = true;
	}

	public LLBBnfGatherDBSet()
	{
		db = new DBOper( "LLBBnfGather" );
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
			tError.moduleName = "LLBBnfGatherDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LLBBnfGather WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND BatNo = ? AND BnfKind = ? AND InsuredNo = ? AND BnfNo = ? AND Currency = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBackNo());
			}
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getBatNo() == null || this.get(i).getBatNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatNo());
			}
			if(this.get(i).getBnfKind() == null || this.get(i).getBnfKind().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBnfKind());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfNo() == null || this.get(i).getBnfNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBnfNo());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getCurrency());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBBnfGather");
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
			tError.moduleName = "LLBBnfGatherDBSet";
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
			pstmt = con.prepareStatement("UPDATE LLBBnfGather SET  BackNo = ? , ClmNo = ? , CaseNo = ? , BatNo = ? , BnfKind = ? , InsuredNo = ? , BnfNo = ? , BnfType = ? , BnfGrade = ? , RelationToInsured = ? , CustomerNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , RelationToPayee = ? , PayeeNo = ? , PayeeName = ? , PayeeSex = ? , PayeeBirthday = ? , PayeeIDType = ? , NBPolNo = ? , PayeeIDNo = ? , BnfLot = ? , GetMoney = ? , CaseGetMode = ? , CasePayMode = ? , CasePayFlag = ? , BankCode = ? , BankAccNo = ? , AccName = ? , ModiReasonCode = ? , ModiReasonDesc = ? , OBankCode = ? , OBankAccNo = ? , OAccName = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , OtherNo = ? , OtherNoType = ? , Currency = ? WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND BatNo = ? AND BnfKind = ? AND InsuredNo = ? AND BnfNo = ? AND Currency = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBackNo());
			}
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getBatNo() == null || this.get(i).getBatNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatNo());
			}
			if(this.get(i).getBnfKind() == null || this.get(i).getBnfKind().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBnfKind());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfNo() == null || this.get(i).getBnfNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBnfNo());
			}
			if(this.get(i).getBnfType() == null || this.get(i).getBnfType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBnfType());
			}
			if(this.get(i).getBnfGrade() == null || this.get(i).getBnfGrade().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBnfGrade());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCustomerNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIDNo());
			}
			if(this.get(i).getRelationToPayee() == null || this.get(i).getRelationToPayee().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRelationToPayee());
			}
			if(this.get(i).getPayeeNo() == null || this.get(i).getPayeeNo().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayeeNo());
			}
			if(this.get(i).getPayeeName() == null || this.get(i).getPayeeName().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPayeeName());
			}
			if(this.get(i).getPayeeSex() == null || this.get(i).getPayeeSex().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPayeeSex());
			}
			if(this.get(i).getPayeeBirthday() == null || this.get(i).getPayeeBirthday().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getPayeeBirthday()));
			}
			if(this.get(i).getPayeeIDType() == null || this.get(i).getPayeeIDType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPayeeIDType());
			}
			if(this.get(i).getNBPolNo() == null || this.get(i).getNBPolNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getNBPolNo());
			}
			if(this.get(i).getPayeeIDNo() == null || this.get(i).getPayeeIDNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPayeeIDNo());
			}
			pstmt.setDouble(25, this.get(i).getBnfLot());
			pstmt.setDouble(26, this.get(i).getGetMoney());
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getCasePayMode() == null || this.get(i).getCasePayMode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getCasePayMode());
			}
			if(this.get(i).getCasePayFlag() == null || this.get(i).getCasePayFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCasePayFlag());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getAccName());
			}
			if(this.get(i).getModiReasonCode() == null || this.get(i).getModiReasonCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModiReasonCode());
			}
			if(this.get(i).getModiReasonDesc() == null || this.get(i).getModiReasonDesc().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModiReasonDesc());
			}
			if(this.get(i).getOBankCode() == null || this.get(i).getOBankCode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getOBankCode());
			}
			if(this.get(i).getOBankAccNo() == null || this.get(i).getOBankAccNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOBankAccNo());
			}
			if(this.get(i).getOAccName() == null || this.get(i).getOAccName().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOAccName());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getModifyTime());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getOtherNoType());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getCurrency());
			}
			// set where condition
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(46,null);
			} else {
				pstmt.setString(46, this.get(i).getBackNo());
			}
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(47,null);
			} else {
				pstmt.setString(47, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(48,null);
			} else {
				pstmt.setString(48, this.get(i).getCaseNo());
			}
			if(this.get(i).getBatNo() == null || this.get(i).getBatNo().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getBatNo());
			}
			if(this.get(i).getBnfKind() == null || this.get(i).getBnfKind().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getBnfKind());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfNo() == null || this.get(i).getBnfNo().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getBnfNo());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getCurrency());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBBnfGather");
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
			tError.moduleName = "LLBBnfGatherDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LLBBnfGather(BackNo ,ClmNo ,CaseNo ,BatNo ,BnfKind ,InsuredNo ,BnfNo ,BnfType ,BnfGrade ,RelationToInsured ,CustomerNo ,Name ,Sex ,Birthday ,IDType ,IDNo ,RelationToPayee ,PayeeNo ,PayeeName ,PayeeSex ,PayeeBirthday ,PayeeIDType ,NBPolNo ,PayeeIDNo ,BnfLot ,GetMoney ,CaseGetMode ,CasePayMode ,CasePayFlag ,BankCode ,BankAccNo ,AccName ,ModiReasonCode ,ModiReasonDesc ,OBankCode ,OBankAccNo ,OAccName ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,OtherNo ,OtherNoType ,Currency) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBackNo() == null || this.get(i).getBackNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBackNo());
			}
			if(this.get(i).getClmNo() == null || this.get(i).getClmNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getClmNo());
			}
			if(this.get(i).getCaseNo() == null || this.get(i).getCaseNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getCaseNo());
			}
			if(this.get(i).getBatNo() == null || this.get(i).getBatNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getBatNo());
			}
			if(this.get(i).getBnfKind() == null || this.get(i).getBnfKind().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBnfKind());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfNo() == null || this.get(i).getBnfNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBnfNo());
			}
			if(this.get(i).getBnfType() == null || this.get(i).getBnfType().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getBnfType());
			}
			if(this.get(i).getBnfGrade() == null || this.get(i).getBnfGrade().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getBnfGrade());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRelationToInsured());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getCustomerNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getIDNo());
			}
			if(this.get(i).getRelationToPayee() == null || this.get(i).getRelationToPayee().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getRelationToPayee());
			}
			if(this.get(i).getPayeeNo() == null || this.get(i).getPayeeNo().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayeeNo());
			}
			if(this.get(i).getPayeeName() == null || this.get(i).getPayeeName().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPayeeName());
			}
			if(this.get(i).getPayeeSex() == null || this.get(i).getPayeeSex().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getPayeeSex());
			}
			if(this.get(i).getPayeeBirthday() == null || this.get(i).getPayeeBirthday().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getPayeeBirthday()));
			}
			if(this.get(i).getPayeeIDType() == null || this.get(i).getPayeeIDType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPayeeIDType());
			}
			if(this.get(i).getNBPolNo() == null || this.get(i).getNBPolNo().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getNBPolNo());
			}
			if(this.get(i).getPayeeIDNo() == null || this.get(i).getPayeeIDNo().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getPayeeIDNo());
			}
			pstmt.setDouble(25, this.get(i).getBnfLot());
			pstmt.setDouble(26, this.get(i).getGetMoney());
			if(this.get(i).getCaseGetMode() == null || this.get(i).getCaseGetMode().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getCaseGetMode());
			}
			if(this.get(i).getCasePayMode() == null || this.get(i).getCasePayMode().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getCasePayMode());
			}
			if(this.get(i).getCasePayFlag() == null || this.get(i).getCasePayFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCasePayFlag());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getAccName());
			}
			if(this.get(i).getModiReasonCode() == null || this.get(i).getModiReasonCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getModiReasonCode());
			}
			if(this.get(i).getModiReasonDesc() == null || this.get(i).getModiReasonDesc().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModiReasonDesc());
			}
			if(this.get(i).getOBankCode() == null || this.get(i).getOBankCode().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getOBankCode());
			}
			if(this.get(i).getOBankAccNo() == null || this.get(i).getOBankAccNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getOBankAccNo());
			}
			if(this.get(i).getOAccName() == null || this.get(i).getOAccName().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOAccName());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(39,null);
			} else {
				pstmt.setDate(39, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(41,null);
			} else {
				pstmt.setDate(41, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getModifyTime());
			}
			if(this.get(i).getOtherNo() == null || this.get(i).getOtherNo().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getOtherNo());
			}
			if(this.get(i).getOtherNoType() == null || this.get(i).getOtherNoType().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getOtherNoType());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getCurrency());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBBnfGather");
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
			tError.moduleName = "LLBBnfGatherDBSet";
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
