/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LZCardTempSchema;
import com.sinosoft.lis.vschema.LZCardTempSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LZCardTempDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardTempDBSet extends LZCardTempSet
{
private static Logger logger = Logger.getLogger(LZCardTempDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LZCardTempDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LZCardTemp");
		mflag = true;
	}

	public LZCardTempDBSet()
	{
		db = new DBOper( "LZCardTemp" );
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
			tError.moduleName = "LZCardTempDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LZCardTemp WHERE  CertifyCode = ? AND SubCode = ? AND RiskCode = ? AND RiskVersion = ? AND StartNo = ? AND EndNo = ? AND SendOutCom = ? AND ReceiveCom = ? AND MakeDate = ? AND MakeTime = ? AND QueryOper = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getSubCode() == null || this.get(i).getSubCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSubCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRiskVersion());
			}
			if(this.get(i).getStartNo() == null || this.get(i).getStartNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getStartNo());
			}
			if(this.get(i).getEndNo() == null || this.get(i).getEndNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getEndNo());
			}
			if(this.get(i).getSendOutCom() == null || this.get(i).getSendOutCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSendOutCom());
			}
			if(this.get(i).getReceiveCom() == null || this.get(i).getReceiveCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReceiveCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(9,null);
			} else {
				pstmt.setDate(9, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, StrTool.space1(this.get(i).getMakeTime(), 8));
			}
			if(this.get(i).getQueryOper() == null || this.get(i).getQueryOper().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getQueryOper());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LZCardTemp");
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
			tError.moduleName = "LZCardTempDBSet";
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
			pstmt = con.prepareStatement("UPDATE LZCardTemp SET  CertifyCode = ? , SubCode = ? , RiskCode = ? , RiskVersion = ? , StartNo = ? , EndNo = ? , SendOutCom = ? , ReceiveCom = ? , SumCount = ? , Prem = ? , Amnt = ? , Handler = ? , HandleDate = ? , InvaliDate = ? , TakeBackNo = ? , SaleChnl = ? , StateFlag = ? , OperateFlag = ? , PayFlag = ? , EnterAccFlag = ? , Reason = ? , State = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , QueryOper = ? WHERE  CertifyCode = ? AND SubCode = ? AND RiskCode = ? AND RiskVersion = ? AND StartNo = ? AND EndNo = ? AND SendOutCom = ? AND ReceiveCom = ? AND MakeDate = ? AND MakeTime = ? AND QueryOper = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getSubCode() == null || this.get(i).getSubCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSubCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRiskVersion());
			}
			if(this.get(i).getStartNo() == null || this.get(i).getStartNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getStartNo());
			}
			if(this.get(i).getEndNo() == null || this.get(i).getEndNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getEndNo());
			}
			if(this.get(i).getSendOutCom() == null || this.get(i).getSendOutCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSendOutCom());
			}
			if(this.get(i).getReceiveCom() == null || this.get(i).getReceiveCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReceiveCom());
			}
			pstmt.setInt(9, this.get(i).getSumCount());
			pstmt.setDouble(10, this.get(i).getPrem());
			pstmt.setDouble(11, this.get(i).getAmnt());
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getHandler());
			}
			if(this.get(i).getHandleDate() == null || this.get(i).getHandleDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getHandleDate()));
			}
			if(this.get(i).getInvaliDate() == null || this.get(i).getInvaliDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getInvaliDate()));
			}
			if(this.get(i).getTakeBackNo() == null || this.get(i).getTakeBackNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getTakeBackNo());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSaleChnl());
			}
			if(this.get(i).getStateFlag() == null || this.get(i).getStateFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStateFlag());
			}
			if(this.get(i).getOperateFlag() == null || this.get(i).getOperateFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getOperateFlag());
			}
			if(this.get(i).getPayFlag() == null || this.get(i).getPayFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPayFlag());
			}
			if(this.get(i).getEnterAccFlag() == null || this.get(i).getEnterAccFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getEnterAccFlag());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getReason());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getModifyTime());
			}
			if(this.get(i).getQueryOper() == null || this.get(i).getQueryOper().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getQueryOper());
			}
			// set where condition
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getCertifyCode());
			}
			if(this.get(i).getSubCode() == null || this.get(i).getSubCode().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getSubCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getRiskVersion());
			}
			if(this.get(i).getStartNo() == null || this.get(i).getStartNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getStartNo());
			}
			if(this.get(i).getEndNo() == null || this.get(i).getEndNo().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getEndNo());
			}
			if(this.get(i).getSendOutCom() == null || this.get(i).getSendOutCom().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getSendOutCom());
			}
			if(this.get(i).getReceiveCom() == null || this.get(i).getReceiveCom().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getReceiveCom());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(37,null);
			} else {
				pstmt.setDate(37, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, StrTool.space1(this.get(i).getMakeTime(), 8));
			}
			if(this.get(i).getQueryOper() == null || this.get(i).getQueryOper().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getQueryOper());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LZCardTemp");
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
			tError.moduleName = "LZCardTempDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LZCardTemp(CertifyCode ,SubCode ,RiskCode ,RiskVersion ,StartNo ,EndNo ,SendOutCom ,ReceiveCom ,SumCount ,Prem ,Amnt ,Handler ,HandleDate ,InvaliDate ,TakeBackNo ,SaleChnl ,StateFlag ,OperateFlag ,PayFlag ,EnterAccFlag ,Reason ,State ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,QueryOper) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getCertifyCode() == null || this.get(i).getCertifyCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getCertifyCode());
			}
			if(this.get(i).getSubCode() == null || this.get(i).getSubCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getSubCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getRiskVersion());
			}
			if(this.get(i).getStartNo() == null || this.get(i).getStartNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getStartNo());
			}
			if(this.get(i).getEndNo() == null || this.get(i).getEndNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getEndNo());
			}
			if(this.get(i).getSendOutCom() == null || this.get(i).getSendOutCom().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSendOutCom());
			}
			if(this.get(i).getReceiveCom() == null || this.get(i).getReceiveCom().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getReceiveCom());
			}
			pstmt.setInt(9, this.get(i).getSumCount());
			pstmt.setDouble(10, this.get(i).getPrem());
			pstmt.setDouble(11, this.get(i).getAmnt());
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getHandler());
			}
			if(this.get(i).getHandleDate() == null || this.get(i).getHandleDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getHandleDate()));
			}
			if(this.get(i).getInvaliDate() == null || this.get(i).getInvaliDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getInvaliDate()));
			}
			if(this.get(i).getTakeBackNo() == null || this.get(i).getTakeBackNo().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getTakeBackNo());
			}
			if(this.get(i).getSaleChnl() == null || this.get(i).getSaleChnl().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getSaleChnl());
			}
			if(this.get(i).getStateFlag() == null || this.get(i).getStateFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getStateFlag());
			}
			if(this.get(i).getOperateFlag() == null || this.get(i).getOperateFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getOperateFlag());
			}
			if(this.get(i).getPayFlag() == null || this.get(i).getPayFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getPayFlag());
			}
			if(this.get(i).getEnterAccFlag() == null || this.get(i).getEnterAccFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getEnterAccFlag());
			}
			if(this.get(i).getReason() == null || this.get(i).getReason().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getReason());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getState());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(26,null);
			} else {
				pstmt.setDate(26, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getModifyTime());
			}
			if(this.get(i).getQueryOper() == null || this.get(i).getQueryOper().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getQueryOper());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LZCardTemp");
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
			tError.moduleName = "LZCardTempDBSet";
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
