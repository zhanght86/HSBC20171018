/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLBBnfGatherSchema;
import com.sinosoft.lis.vschema.LLBBnfGatherSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLBBnfGatherDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LLBBnfGatherDB extends LLBBnfGatherSchema
{
private static Logger logger = Logger.getLogger(LLBBnfGatherDB.class);

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
	public LLBBnfGatherDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLBBnfGather" );
		mflag = true;
	}

	public LLBBnfGatherDB()
	{
		con = null;
		db = new DBOper( "LLBBnfGather" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLBBnfGatherSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLBBnfGatherSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLBBnfGather WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND BatNo = ? AND BnfKind = ? AND InsuredNo = ? AND BnfNo = ? AND Currency = ?");
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackNo());
			}
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCaseNo());
			}
			if(this.getBatNo() == null || this.getBatNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatNo());
			}
			if(this.getBnfKind() == null || this.getBnfKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getBnfKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredNo());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBnfNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCurrency());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLBBnfGather");
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
		SQLString sqlObj = new SQLString("LLBBnfGather");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLBBnfGather SET  BackNo = ? , ClmNo = ? , CaseNo = ? , BatNo = ? , BnfKind = ? , InsuredNo = ? , BnfNo = ? , BnfType = ? , BnfGrade = ? , RelationToInsured = ? , CustomerNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , RelationToPayee = ? , PayeeNo = ? , PayeeName = ? , PayeeSex = ? , PayeeBirthday = ? , PayeeIDType = ? , NBPolNo = ? , PayeeIDNo = ? , BnfLot = ? , GetMoney = ? , CaseGetMode = ? , CasePayMode = ? , CasePayFlag = ? , BankCode = ? , BankAccNo = ? , AccName = ? , ModiReasonCode = ? , ModiReasonDesc = ? , OBankCode = ? , OBankAccNo = ? , OAccName = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , OtherNo = ? , OtherNoType = ? , Currency = ? WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND BatNo = ? AND BnfKind = ? AND InsuredNo = ? AND BnfNo = ? AND Currency = ?");
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackNo());
			}
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCaseNo());
			}
			if(this.getBatNo() == null || this.getBatNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatNo());
			}
			if(this.getBnfKind() == null || this.getBnfKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getBnfKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredNo());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBnfNo());
			}
			if(this.getBnfType() == null || this.getBnfType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getBnfType());
			}
			if(this.getBnfGrade() == null || this.getBnfGrade().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getBnfGrade());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRelationToInsured());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getIDNo());
			}
			if(this.getRelationToPayee() == null || this.getRelationToPayee().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getRelationToPayee());
			}
			if(this.getPayeeNo() == null || this.getPayeeNo().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getPayeeNo());
			}
			if(this.getPayeeName() == null || this.getPayeeName().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPayeeName());
			}
			if(this.getPayeeSex() == null || this.getPayeeSex().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPayeeSex());
			}
			if(this.getPayeeBirthday() == null || this.getPayeeBirthday().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getPayeeBirthday()));
			}
			if(this.getPayeeIDType() == null || this.getPayeeIDType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPayeeIDType());
			}
			if(this.getNBPolNo() == null || this.getNBPolNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getNBPolNo());
			}
			if(this.getPayeeIDNo() == null || this.getPayeeIDNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPayeeIDNo());
			}
			pstmt.setDouble(25, this.getBnfLot());
			pstmt.setDouble(26, this.getGetMoney());
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getCaseGetMode());
			}
			if(this.getCasePayMode() == null || this.getCasePayMode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCasePayMode());
			}
			if(this.getCasePayFlag() == null || this.getCasePayFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getCasePayFlag());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getAccName());
			}
			if(this.getModiReasonCode() == null || this.getModiReasonCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getModiReasonCode());
			}
			if(this.getModiReasonDesc() == null || this.getModiReasonDesc().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getModiReasonDesc());
			}
			if(this.getOBankCode() == null || this.getOBankCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOBankCode());
			}
			if(this.getOBankAccNo() == null || this.getOBankAccNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOBankAccNo());
			}
			if(this.getOAccName() == null || this.getOAccName().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOAccName());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getModifyTime());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getOtherNoType());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getCurrency());
			}
			// set where condition
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(46, 12);
			} else {
				pstmt.setString(46, this.getBackNo());
			}
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(47, 12);
			} else {
				pstmt.setString(47, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(48, 12);
			} else {
				pstmt.setString(48, this.getCaseNo());
			}
			if(this.getBatNo() == null || this.getBatNo().equals("null")) {
				pstmt.setNull(49, 12);
			} else {
				pstmt.setString(49, this.getBatNo());
			}
			if(this.getBnfKind() == null || this.getBnfKind().equals("null")) {
				pstmt.setNull(50, 12);
			} else {
				pstmt.setString(50, this.getBnfKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(51, 12);
			} else {
				pstmt.setString(51, this.getInsuredNo());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(52, 12);
			} else {
				pstmt.setString(52, this.getBnfNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(53, 12);
			} else {
				pstmt.setString(53, this.getCurrency());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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
		SQLString sqlObj = new SQLString("LLBBnfGather");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLBBnfGather(BackNo ,ClmNo ,CaseNo ,BatNo ,BnfKind ,InsuredNo ,BnfNo ,BnfType ,BnfGrade ,RelationToInsured ,CustomerNo ,Name ,Sex ,Birthday ,IDType ,IDNo ,RelationToPayee ,PayeeNo ,PayeeName ,PayeeSex ,PayeeBirthday ,PayeeIDType ,NBPolNo ,PayeeIDNo ,BnfLot ,GetMoney ,CaseGetMode ,CasePayMode ,CasePayFlag ,BankCode ,BankAccNo ,AccName ,ModiReasonCode ,ModiReasonDesc ,OBankCode ,OBankAccNo ,OAccName ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,OtherNo ,OtherNoType ,Currency) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackNo());
			}
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCaseNo());
			}
			if(this.getBatNo() == null || this.getBatNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatNo());
			}
			if(this.getBnfKind() == null || this.getBnfKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getBnfKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredNo());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBnfNo());
			}
			if(this.getBnfType() == null || this.getBnfType().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getBnfType());
			}
			if(this.getBnfGrade() == null || this.getBnfGrade().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getBnfGrade());
			}
			if(this.getRelationToInsured() == null || this.getRelationToInsured().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getRelationToInsured());
			}
			if(this.getCustomerNo() == null || this.getCustomerNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCustomerNo());
			}
			if(this.getName() == null || this.getName().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getName());
			}
			if(this.getSex() == null || this.getSex().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getSex());
			}
			if(this.getBirthday() == null || this.getBirthday().equals("null")) {
				pstmt.setNull(14, 91);
			} else {
				pstmt.setDate(14, Date.valueOf(this.getBirthday()));
			}
			if(this.getIDType() == null || this.getIDType().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getIDType());
			}
			if(this.getIDNo() == null || this.getIDNo().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getIDNo());
			}
			if(this.getRelationToPayee() == null || this.getRelationToPayee().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getRelationToPayee());
			}
			if(this.getPayeeNo() == null || this.getPayeeNo().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getPayeeNo());
			}
			if(this.getPayeeName() == null || this.getPayeeName().equals("null")) {
				pstmt.setNull(19, 12);
			} else {
				pstmt.setString(19, this.getPayeeName());
			}
			if(this.getPayeeSex() == null || this.getPayeeSex().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getPayeeSex());
			}
			if(this.getPayeeBirthday() == null || this.getPayeeBirthday().equals("null")) {
				pstmt.setNull(21, 91);
			} else {
				pstmt.setDate(21, Date.valueOf(this.getPayeeBirthday()));
			}
			if(this.getPayeeIDType() == null || this.getPayeeIDType().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getPayeeIDType());
			}
			if(this.getNBPolNo() == null || this.getNBPolNo().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getNBPolNo());
			}
			if(this.getPayeeIDNo() == null || this.getPayeeIDNo().equals("null")) {
				pstmt.setNull(24, 12);
			} else {
				pstmt.setString(24, this.getPayeeIDNo());
			}
			pstmt.setDouble(25, this.getBnfLot());
			pstmt.setDouble(26, this.getGetMoney());
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(27, 12);
			} else {
				pstmt.setString(27, this.getCaseGetMode());
			}
			if(this.getCasePayMode() == null || this.getCasePayMode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCasePayMode());
			}
			if(this.getCasePayFlag() == null || this.getCasePayFlag().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getCasePayFlag());
			}
			if(this.getBankCode() == null || this.getBankCode().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getBankCode());
			}
			if(this.getBankAccNo() == null || this.getBankAccNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getBankAccNo());
			}
			if(this.getAccName() == null || this.getAccName().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getAccName());
			}
			if(this.getModiReasonCode() == null || this.getModiReasonCode().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getModiReasonCode());
			}
			if(this.getModiReasonDesc() == null || this.getModiReasonDesc().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getModiReasonDesc());
			}
			if(this.getOBankCode() == null || this.getOBankCode().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getOBankCode());
			}
			if(this.getOBankAccNo() == null || this.getOBankAccNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getOBankAccNo());
			}
			if(this.getOAccName() == null || this.getOAccName().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getOAccName());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(39, 91);
			} else {
				pstmt.setDate(39, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(40, 1);
			} else {
				pstmt.setString(40, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(41, 91);
			} else {
				pstmt.setDate(41, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(42, 1);
			} else {
				pstmt.setString(42, this.getModifyTime());
			}
			if(this.getOtherNo() == null || this.getOtherNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getOtherNo());
			}
			if(this.getOtherNoType() == null || this.getOtherNoType().equals("null")) {
				pstmt.setNull(44, 12);
			} else {
				pstmt.setString(44, this.getOtherNoType());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(45, 12);
			} else {
				pstmt.setString(45, this.getCurrency());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLBBnfGather WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND BatNo = ? AND BnfKind = ? AND InsuredNo = ? AND BnfNo = ? AND Currency = ?", 
				ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(1, 12);
			} else {
				pstmt.setString(1, this.getBackNo());
			}
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(2, 12);
			} else {
				pstmt.setString(2, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(3, 12);
			} else {
				pstmt.setString(3, this.getCaseNo());
			}
			if(this.getBatNo() == null || this.getBatNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getBatNo());
			}
			if(this.getBnfKind() == null || this.getBnfKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getBnfKind());
			}
			if(this.getInsuredNo() == null || this.getInsuredNo().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getInsuredNo());
			}
			if(this.getBnfNo() == null || this.getBnfNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getBnfNo());
			}
			if(this.getCurrency() == null || this.getCurrency().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getCurrency());
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
					tError.moduleName = "LLBBnfGatherDB";
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
			tError.moduleName = "LLBBnfGatherDB";
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

	public LLBBnfGatherSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLBBnfGatherSet aLLBBnfGatherSet = new LLBBnfGatherSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLBBnfGather");
			LLBBnfGatherSchema aSchema = this.getSchema();
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
				LLBBnfGatherSchema s1 = new LLBBnfGatherSchema();
				s1.setSchema(rs,i);
				aLLBBnfGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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

		return aLLBBnfGatherSet;

	}

	public LLBBnfGatherSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLBBnfGatherSet aLLBBnfGatherSet = new LLBBnfGatherSet();

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
				LLBBnfGatherSchema s1 = new LLBBnfGatherSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBBnfGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBBnfGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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

		return aLLBBnfGatherSet;
	}

	public LLBBnfGatherSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLBBnfGatherSet aLLBBnfGatherSet = new LLBBnfGatherSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLBBnfGather");
			LLBBnfGatherSchema aSchema = this.getSchema();
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

				LLBBnfGatherSchema s1 = new LLBBnfGatherSchema();
				s1.setSchema(rs,i);
				aLLBBnfGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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

		return aLLBBnfGatherSet;

	}

	public LLBBnfGatherSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLBBnfGatherSet aLLBBnfGatherSet = new LLBBnfGatherSet();

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

				LLBBnfGatherSchema s1 = new LLBBnfGatherSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBBnfGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBBnfGatherSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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

		return aLLBBnfGatherSet;
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
			SQLString sqlObj = new SQLString("LLBBnfGather");
			LLBBnfGatherSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLBBnfGather " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLBBnfGatherDB";
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
			tError.moduleName = "LLBBnfGatherDB";
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
        tError.moduleName = "LLBBnfGatherDB";
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
        tError.moduleName = "LLBBnfGatherDB";
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
        tError.moduleName = "LLBBnfGatherDB";
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
        tError.moduleName = "LLBBnfGatherDB";
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
 * @return LLBBnfGatherSet
 */
public LLBBnfGatherSet getData()
{
    int tCount = 0;
    LLBBnfGatherSet tLLBBnfGatherSet = new LLBBnfGatherSet();
    LLBBnfGatherSchema tLLBBnfGatherSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLBBnfGatherDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLBBnfGatherSchema = new LLBBnfGatherSchema();
        tLLBBnfGatherSchema.setSchema(mResultSet, 1);
        tLLBBnfGatherSet.add(tLLBBnfGatherSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLBBnfGatherSchema = new LLBBnfGatherSchema();
                tLLBBnfGatherSchema.setSchema(mResultSet, 1);
                tLLBBnfGatherSet.add(tLLBBnfGatherSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLBBnfGatherDB";
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
    return tLLBBnfGatherSet;
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
            tError.moduleName = "LLBBnfGatherDB";
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
        tError.moduleName = "LLBBnfGatherDB";
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
            tError.moduleName = "LLBBnfGatherDB";
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
        tError.moduleName = "LLBBnfGatherDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLBBnfGatherSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLBBnfGatherSet aLLBBnfGatherSet = new LLBBnfGatherSet();

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
				LLBBnfGatherSchema s1 = new LLBBnfGatherSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBBnfGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBBnfGatherSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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

		return aLLBBnfGatherSet;
	}

	public LLBBnfGatherSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLBBnfGatherSet aLLBBnfGatherSet = new LLBBnfGatherSet();

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

				LLBBnfGatherSchema s1 = new LLBBnfGatherSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLBBnfGatherDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLBBnfGatherSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBBnfGatherDB";
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

		return aLLBBnfGatherSet; 
	}

}
