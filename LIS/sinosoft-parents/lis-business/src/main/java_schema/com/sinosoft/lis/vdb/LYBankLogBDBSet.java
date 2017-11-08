/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LYBankLogBSchema;
import com.sinosoft.lis.vschema.LYBankLogBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LYBankLogBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYBankLogBDBSet extends LYBankLogBSet
{
private static Logger logger = Logger.getLogger(LYBankLogBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LYBankLogBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LYBankLogB");
		mflag = true;
	}

	public LYBankLogBDBSet()
	{
		db = new DBOper( "LYBankLogB" );
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
			tError.moduleName = "LYBankLogBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LYBankLogB WHERE  BackUpNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBackUpNo() == null || this.get(i).getBackUpNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBackUpNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LYBankLogB");
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
			tError.moduleName = "LYBankLogBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LYBankLogB SET  BackUpNo = ? , SerialNo = ? , BankCode = ? , LogType = ? , StartDate = ? , MakeDate = ? , OutFile = ? , InFile = ? , SendDate = ? , SendOperator = ? , ReturnDate = ? , ReturnOperator = ? , TransDate = ? , TransOperator = ? , TotalMoney = ? , TotalNum = ? , AccTotalMoney = ? , BankSuccMoney = ? , BankSuccNum = ? , SuccMoney = ? , SuccNum = ? , DealState = ? , OthFlag = ? , ComCode = ? , OutFileB = ? , SaleChnl = ? , OperationType = ? , FileOperateDate = ? , FileOperateTime = ? , BackReason = ? , BackOperator = ? , BackCom = ? , ModifyDate = ? , ModifyTime = ? WHERE  BackUpNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBackUpNo() == null || this.get(i).getBackUpNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBackUpNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSerialNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBankCode());
			}
			if(this.get(i).getLogType() == null || this.get(i).getLogType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getLogType());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(5,null);
			} else {
				pstmt.setDate(5, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getOutFile() == null || this.get(i).getOutFile().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOutFile());
			}
			if(this.get(i).getInFile() == null || this.get(i).getInFile().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInFile());
			}
			if(this.get(i).getSendDate() == null || this.get(i).getSendDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getSendDate()));
			}
			if(this.get(i).getSendOperator() == null || this.get(i).getSendOperator().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSendOperator());
			}
			if(this.get(i).getReturnDate() == null || this.get(i).getReturnDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getReturnDate()));
			}
			if(this.get(i).getReturnOperator() == null || this.get(i).getReturnOperator().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getReturnOperator());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getTransOperator());
			}
			pstmt.setDouble(15, this.get(i).getTotalMoney());
			pstmt.setInt(16, this.get(i).getTotalNum());
			pstmt.setDouble(17, this.get(i).getAccTotalMoney());
			pstmt.setDouble(18, this.get(i).getBankSuccMoney());
			pstmt.setInt(19, this.get(i).getBankSuccNum());
			pstmt.setDouble(20, this.get(i).getSuccMoney());
			pstmt.setInt(21, this.get(i).getSuccNum());
			if(this.get(i).getDealState() == null || this.get(i).getDealState().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getDealState());
			}
			if(this.get(i).getOthFlag() == null || this.get(i).getOthFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOthFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getComCode());
			}
			if(this.get(i).getOutFileB() == null || this.get(i).getOutFileB().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOutFileB());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSaleChnl());
			}
			if(this.get(i).getOperationType() == null || this.get(i).getOperationType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getOperationType());
			}
			if(this.get(i).getFileOperateDate() == null || this.get(i).getFileOperateDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getFileOperateDate()));
			}
			if(this.get(i).getFileOperateTime() == null || this.get(i).getFileOperateTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFileOperateTime());
			}
			if(this.get(i).getBackReason() == null || this.get(i).getBackReason().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBackReason());
			}
			if(this.get(i).getBackOperator() == null || this.get(i).getBackOperator().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBackOperator());
			}
			if(this.get(i).getBackCom() == null || this.get(i).getBackCom().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBackCom());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyTime());
			}
			// set where condition
			if(this.get(i).getBackUpNo() == null || this.get(i).getBackUpNo().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getBackUpNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LYBankLogB");
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
			tError.moduleName = "LYBankLogBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LYBankLogB(BackUpNo ,SerialNo ,BankCode ,LogType ,StartDate ,MakeDate ,OutFile ,InFile ,SendDate ,SendOperator ,ReturnDate ,ReturnOperator ,TransDate ,TransOperator ,TotalMoney ,TotalNum ,AccTotalMoney ,BankSuccMoney ,BankSuccNum ,SuccMoney ,SuccNum ,DealState ,OthFlag ,ComCode ,OutFileB ,SaleChnl ,OperationType ,FileOperateDate ,FileOperateTime ,BackReason ,BackOperator ,BackCom ,ModifyDate ,ModifyTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getBackUpNo() == null || this.get(i).getBackUpNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getBackUpNo());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSerialNo());
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getBankCode());
			}
			if(this.get(i).getLogType() == null || this.get(i).getLogType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getLogType());
			}
			if(this.get(i).getStartDate() == null || this.get(i).getStartDate().equals("null")) {
				pstmt.setDate(5,null);
			} else {
				pstmt.setDate(5, Date.valueOf(this.get(i).getStartDate()));
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(6,null);
			} else {
				pstmt.setDate(6, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getOutFile() == null || this.get(i).getOutFile().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getOutFile());
			}
			if(this.get(i).getInFile() == null || this.get(i).getInFile().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getInFile());
			}
			if(this.get(i).getSendDate() == null || this.get(i).getSendDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getSendDate()));
			}
			if(this.get(i).getSendOperator() == null || this.get(i).getSendOperator().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getSendOperator());
			}
			if(this.get(i).getReturnDate() == null || this.get(i).getReturnDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getReturnDate()));
			}
			if(this.get(i).getReturnOperator() == null || this.get(i).getReturnOperator().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getReturnOperator());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getTransOperator());
			}
			pstmt.setDouble(15, this.get(i).getTotalMoney());
			pstmt.setInt(16, this.get(i).getTotalNum());
			pstmt.setDouble(17, this.get(i).getAccTotalMoney());
			pstmt.setDouble(18, this.get(i).getBankSuccMoney());
			pstmt.setInt(19, this.get(i).getBankSuccNum());
			pstmt.setDouble(20, this.get(i).getSuccMoney());
			pstmt.setInt(21, this.get(i).getSuccNum());
			if(this.get(i).getDealState() == null || this.get(i).getDealState().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getDealState());
			}
			if(this.get(i).getOthFlag() == null || this.get(i).getOthFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOthFlag());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getComCode());
			}
			if(this.get(i).getOutFileB() == null || this.get(i).getOutFileB().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getOutFileB());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSaleChnl());
			}
			if(this.get(i).getOperationType() == null || this.get(i).getOperationType().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getOperationType());
			}
			if(this.get(i).getFileOperateDate() == null || this.get(i).getFileOperateDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getFileOperateDate()));
			}
			if(this.get(i).getFileOperateTime() == null || this.get(i).getFileOperateTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getFileOperateTime());
			}
			if(this.get(i).getBackReason() == null || this.get(i).getBackReason().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getBackReason());
			}
			if(this.get(i).getBackOperator() == null || this.get(i).getBackOperator().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getBackOperator());
			}
			if(this.get(i).getBackCom() == null || this.get(i).getBackCom().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getBackCom());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(33,null);
			} else {
				pstmt.setDate(33, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getModifyTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LYBankLogB");
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
			tError.moduleName = "LYBankLogBDBSet";
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
