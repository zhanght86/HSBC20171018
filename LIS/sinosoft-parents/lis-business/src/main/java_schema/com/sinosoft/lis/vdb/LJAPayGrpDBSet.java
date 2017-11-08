/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJAPayGrpDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class LJAPayGrpDBSet extends LJAPayGrpSet
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
	public LJAPayGrpDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LJAPayGrp");
		mflag = true;
	}

	public LJAPayGrpDBSet()
	{
		db = new DBOper( "LJAPayGrp" );
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
			tError.moduleName = "LJAPayGrpDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LJAPayGrp WHERE  GrpPolNo = ? AND PayCount = ? AND PayNo = ? AND PayType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			pstmt.setInt(2, this.get(i).getPayCount());
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPayNo());
			}
			if(this.get(i).getPayType() == null || this.get(i).getPayType().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAPayGrp");
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
			tError.moduleName = "LJAPayGrpDBSet";
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
			pstmt = con.prepareStatement("UPDATE LJAPayGrp SET  GrpPolNo = ? , PayCount = ? , GrpContNo = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , RiskCode = ? , AgentCode = ? , AgentGroup = ? , PayTypeFlag = ? , AppntNo = ? , PayNo = ? , EndorsementNo = ? , SumDuePayMoney = ? , SumActuPayMoney = ? , PayIntv = ? , PayDate = ? , PayType = ? , EnterAccDate = ? , ConfDate = ? , LastPayToDate = ? , CurPayToDate = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , SerialNo = ? , GetNoticeNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , FeeMoney = ? , OperState = ? , Currency = ? , BussType = ? , ComCode = ? , ModifyOperator = ? , NetAmount = ? , TaxAmount = ? , Tax = ? WHERE  GrpPolNo = ? AND PayCount = ? AND PayNo = ? AND PayType = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			pstmt.setInt(2, this.get(i).getPayCount());
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAgentGroup());
			}
			if(this.get(i).getPayTypeFlag() == null || this.get(i).getPayTypeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPayTypeFlag());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAppntNo());
			}
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPayNo());
			}
			if(this.get(i).getEndorsementNo() == null || this.get(i).getEndorsementNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getEndorsementNo());
			}
			pstmt.setDouble(14, this.get(i).getSumDuePayMoney());
			pstmt.setDouble(15, this.get(i).getSumActuPayMoney());
			pstmt.setInt(16, this.get(i).getPayIntv());
			if(this.get(i).getPayDate() == null || this.get(i).getPayDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getPayDate()));
			}
			if(this.get(i).getPayType() == null || this.get(i).getPayType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayType());
			}
			if(this.get(i).getEnterAccDate() == null || this.get(i).getEnterAccDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getEnterAccDate()));
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getLastPayToDate() == null || this.get(i).getLastPayToDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getLastPayToDate()));
			}
			if(this.get(i).getCurPayToDate() == null || this.get(i).getCurPayToDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getCurPayToDate()));
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getApproveTime());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSerialNo());
			}
			if(this.get(i).getGetNoticeNo() == null || this.get(i).getGetNoticeNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getGetNoticeNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getModifyTime());
			}
			pstmt.setDouble(33, this.get(i).getFeeMoney());
			if(this.get(i).getOperState() == null || this.get(i).getOperState().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOperState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCurrency());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getBussType());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getModifyOperator());
			}
			pstmt.setDouble(39, this.get(i).getNetAmount());
			pstmt.setDouble(40, this.get(i).getTaxAmount());
			pstmt.setDouble(41, this.get(i).getTax());
			// set where condition
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(42,null);
			} else {
				pstmt.setString(42, this.get(i).getGrpPolNo());
			}
			pstmt.setInt(43, this.get(i).getPayCount());
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getPayNo());
			}
			if(this.get(i).getPayType() == null || this.get(i).getPayType().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getPayType());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAPayGrp");
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
			tError.moduleName = "LJAPayGrpDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LJAPayGrp VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpPolNo());
			}
			pstmt.setInt(2, this.get(i).getPayCount());
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getGrpContNo());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAgentType());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getRiskCode());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAgentGroup());
			}
			if(this.get(i).getPayTypeFlag() == null || this.get(i).getPayTypeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getPayTypeFlag());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getAppntNo());
			}
			if(this.get(i).getPayNo() == null || this.get(i).getPayNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPayNo());
			}
			if(this.get(i).getEndorsementNo() == null || this.get(i).getEndorsementNo().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getEndorsementNo());
			}
			pstmt.setDouble(14, this.get(i).getSumDuePayMoney());
			pstmt.setDouble(15, this.get(i).getSumActuPayMoney());
			pstmt.setInt(16, this.get(i).getPayIntv());
			if(this.get(i).getPayDate() == null || this.get(i).getPayDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getPayDate()));
			}
			if(this.get(i).getPayType() == null || this.get(i).getPayType().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getPayType());
			}
			if(this.get(i).getEnterAccDate() == null || this.get(i).getEnterAccDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getEnterAccDate()));
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(20,null);
			} else {
				pstmt.setDate(20, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getLastPayToDate() == null || this.get(i).getLastPayToDate().equals("null")) {
				pstmt.setDate(21,null);
			} else {
				pstmt.setDate(21, Date.valueOf(this.get(i).getLastPayToDate()));
			}
			if(this.get(i).getCurPayToDate() == null || this.get(i).getCurPayToDate().equals("null")) {
				pstmt.setDate(22,null);
			} else {
				pstmt.setDate(22, Date.valueOf(this.get(i).getCurPayToDate()));
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(24,null);
			} else {
				pstmt.setDate(24, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getApproveTime());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSerialNo());
			}
			if(this.get(i).getGetNoticeNo() == null || this.get(i).getGetNoticeNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getGetNoticeNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(29,null);
			} else {
				pstmt.setDate(29, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(31,null);
			} else {
				pstmt.setDate(31, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getModifyTime());
			}
			pstmt.setDouble(33, this.get(i).getFeeMoney());
			if(this.get(i).getOperState() == null || this.get(i).getOperState().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getOperState());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getCurrency());
			}
			if(this.get(i).getBussType() == null || this.get(i).getBussType().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getBussType());
			}
			if(this.get(i).getComCode() == null || this.get(i).getComCode().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getComCode());
			}
			if(this.get(i).getModifyOperator() == null || this.get(i).getModifyOperator().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getModifyOperator());
			}
			pstmt.setDouble(39, this.get(i).getNetAmount());
			pstmt.setDouble(40, this.get(i).getTaxAmount());
			pstmt.setDouble(41, this.get(i).getTax());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAPayGrp");
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
			tError.moduleName = "LJAPayGrpDBSet";
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