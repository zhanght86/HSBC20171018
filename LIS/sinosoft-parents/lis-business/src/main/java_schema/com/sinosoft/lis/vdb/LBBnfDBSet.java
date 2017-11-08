/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBBnfSchema;
import com.sinosoft.lis.vschema.LBBnfSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBBnfDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LBBnfDBSet extends LBBnfSet
{
private static Logger logger = Logger.getLogger(LBBnfDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBBnfDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBBnf");
		mflag = true;
	}

	public LBBnfDBSet()
	{
		db = new DBOper( "LBBnf" );
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
			tError.moduleName = "LBBnfDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBBnf WHERE  PolNo = ? AND InsuredNo = ? AND BnfType = ? AND BnfNo = ? AND BnfGrade = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getPolNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfType() == null || this.get(i).getBnfType().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, StrTool.space1(this.get(i).getBnfType(), 1));
			}
			pstmt.setInt(4, this.get(i).getBnfNo());
			if(this.get(i).getBnfGrade() == null || this.get(i).getBnfGrade().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, StrTool.space1(this.get(i).getBnfGrade(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBBnf");
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
			tError.moduleName = "LBBnfDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBBnf SET  EdorNo = ? , ContNo = ? , PolNo = ? , InsuredNo = ? , BnfType = ? , BnfNo = ? , BnfGrade = ? , RelationToInsured = ? , BnfLot = ? , CustomerNo = ? , Name = ? , Sex = ? , Birthday = ? , IDType = ? , IDNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , PostalAddress = ? , ZipCode = ? , Remark = ? , BankCode = ? , BankAccNo = ? , AccName = ? , IDExpDate = ? WHERE  PolNo = ? AND InsuredNo = ? AND BnfType = ? AND BnfNo = ? AND BnfGrade = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfType() == null || this.get(i).getBnfType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBnfType());
			}
			pstmt.setInt(6, this.get(i).getBnfNo());
			if(this.get(i).getBnfGrade() == null || this.get(i).getBnfGrade().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBnfGrade());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRelationToInsured());
			}
			pstmt.setDouble(9, this.get(i).getBnfLot());
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCustomerNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getIDNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getZipCode());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRemark());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAccName());
			}
			if(this.get(i).getIDExpDate() == null || this.get(i).getIDExpDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getIDExpDate()));
			}
			// set where condition
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPolNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfType() == null || this.get(i).getBnfType().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, StrTool.space1(this.get(i).getBnfType(), 1));
			}
			pstmt.setInt(31, this.get(i).getBnfNo());
			if(this.get(i).getBnfGrade() == null || this.get(i).getBnfGrade().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, StrTool.space1(this.get(i).getBnfGrade(), 1));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBBnf");
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
			tError.moduleName = "LBBnfDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBBnf(EdorNo ,ContNo ,PolNo ,InsuredNo ,BnfType ,BnfNo ,BnfGrade ,RelationToInsured ,BnfLot ,CustomerNo ,Name ,Sex ,Birthday ,IDType ,IDNo ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,PostalAddress ,ZipCode ,Remark ,BankCode ,BankAccNo ,AccName ,IDExpDate) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getEdorNo() == null || this.get(i).getEdorNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getEdorNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPolNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getInsuredNo());
			}
			if(this.get(i).getBnfType() == null || this.get(i).getBnfType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBnfType());
			}
			pstmt.setInt(6, this.get(i).getBnfNo());
			if(this.get(i).getBnfGrade() == null || this.get(i).getBnfGrade().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getBnfGrade());
			}
			if(this.get(i).getRelationToInsured() == null || this.get(i).getRelationToInsured().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRelationToInsured());
			}
			pstmt.setDouble(9, this.get(i).getBnfLot());
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getCustomerNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getName());
			}
			if(this.get(i).getSex() == null || this.get(i).getSex().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getSex());
			}
			if(this.get(i).getBirthday() == null || this.get(i).getBirthday().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getBirthday()));
			}
			if(this.get(i).getIDType() == null || this.get(i).getIDType().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getIDType());
			}
			if(this.get(i).getIDNo() == null || this.get(i).getIDNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getIDNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getPostalAddress() == null || this.get(i).getPostalAddress().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getPostalAddress());
			}
			if(this.get(i).getZipCode() == null || this.get(i).getZipCode().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getZipCode());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRemark());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getBankAccNo());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getAccName());
			}
			if(this.get(i).getIDExpDate() == null || this.get(i).getIDExpDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getIDExpDate()));
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBBnf");
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
			tError.moduleName = "LBBnfDBSet";
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
