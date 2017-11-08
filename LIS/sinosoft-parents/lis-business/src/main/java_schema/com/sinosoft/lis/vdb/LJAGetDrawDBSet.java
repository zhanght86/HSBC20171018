/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LJAGetDrawDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务付费
 */
public class LJAGetDrawDBSet extends LJAGetDrawSet
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
	public LJAGetDrawDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LJAGetDraw");
		mflag = true;
	}

	public LJAGetDrawDBSet()
	{
		db = new DBOper( "LJAGetDraw" );
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
			tError.moduleName = "LJAGetDrawDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LJAGetDraw WHERE  ActuGetNo = ? AND PolNo = ? AND DutyCode = ? AND GetDutyKind = ? AND GetDutyCode = ? AND FeeFinaType = ? AND GetNoticeNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActuGetNo() == null || this.get(i).getActuGetNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActuGetNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getGetNoticeNo() == null || this.get(i).getGetNoticeNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGetNoticeNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAGetDraw");
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
			tError.moduleName = "LJAGetDrawDBSet";
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
			pstmt = con.prepareStatement("UPDATE LJAGetDraw SET  ActuGetNo = ? , PolNo = ? , DutyCode = ? , GetDutyKind = ? , GetDutyCode = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , AppntNo = ? , InsuredNo = ? , GetDate = ? , GetMoney = ? , EnterAccDate = ? , ConfDate = ? , FeeOperationType = ? , FeeFinaType = ? , KindCode = ? , RiskCode = ? , RiskVersion = ? , ManageCom = ? , AgentCom = ? , AgentType = ? , AgentCode = ? , AgentGroup = ? , GrpName = ? , Handler = ? , LastGettoDate = ? , CurGetToDate = ? , GetmFirstFlag = ? , GetChannelFlag = ? , DestrayFlag = ? , PolType = ? , ApproveCode = ? , ApproveDate = ? , ApproveTime = ? , SerialNo = ? , Operator = ? , MakeDate = ? , MakeTime = ? , GetNoticeNo = ? , ModifyDate = ? , ModifyTime = ? , RReportFlag = ? , ComeFlag = ? , Currency = ? , NetAmount = ? , TaxAmount = ? , Tax = ? WHERE  ActuGetNo = ? AND PolNo = ? AND DutyCode = ? AND GetDutyKind = ? AND GetDutyCode = ? AND FeeFinaType = ? AND GetNoticeNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActuGetNo() == null || this.get(i).getActuGetNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActuGetNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getContNo());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAppntNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getInsuredNo());
			}
			if(this.get(i).getGetDate() == null || this.get(i).getGetDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getGetDate()));
			}
			pstmt.setDouble(12, this.get(i).getGetMoney());
			if(this.get(i).getEnterAccDate() == null || this.get(i).getEnterAccDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getEnterAccDate()));
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskVersion());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAgentGroup());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getGrpName());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getHandler());
			}
			if(this.get(i).getLastGettoDate() == null || this.get(i).getLastGettoDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getLastGettoDate()));
			}
			if(this.get(i).getCurGetToDate() == null || this.get(i).getCurGetToDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getCurGetToDate()));
			}
			if(this.get(i).getGetmFirstFlag() == null || this.get(i).getGetmFirstFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getGetmFirstFlag());
			}
			if(this.get(i).getGetChannelFlag() == null || this.get(i).getGetChannelFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getGetChannelFlag());
			}
			if(this.get(i).getDestrayFlag() == null || this.get(i).getDestrayFlag().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDestrayFlag());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPolType());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getApproveTime());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getSerialNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getMakeTime());
			}
			if(this.get(i).getGetNoticeNo() == null || this.get(i).getGetNoticeNo().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGetNoticeNo());
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
			if(this.get(i).getRReportFlag() == null || this.get(i).getRReportFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getRReportFlag());
			}
			if(this.get(i).getComeFlag() == null || this.get(i).getComeFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getComeFlag());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getCurrency());
			}
			pstmt.setDouble(46, this.get(i).getNetAmount());
			pstmt.setDouble(47, this.get(i).getTaxAmount());
			pstmt.setDouble(48, this.get(i).getTax());
			// set where condition
			if(this.get(i).getActuGetNo() == null || this.get(i).getActuGetNo().equals("null")) {
				pstmt.setString(49,null);
			} else {
				pstmt.setString(49, this.get(i).getActuGetNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(50,null);
			} else {
				pstmt.setString(50, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(51,null);
			} else {
				pstmt.setString(51, this.get(i).getDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(52,null);
			} else {
				pstmt.setString(52, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(53,null);
			} else {
				pstmt.setString(53, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(54,null);
			} else {
				pstmt.setString(54, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getGetNoticeNo() == null || this.get(i).getGetNoticeNo().equals("null")) {
				pstmt.setString(55,null);
			} else {
				pstmt.setString(55, this.get(i).getGetNoticeNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAGetDraw");
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
			tError.moduleName = "LJAGetDrawDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LJAGetDraw VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getActuGetNo() == null || this.get(i).getActuGetNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getActuGetNo());
			}
			if(this.get(i).getPolNo() == null || this.get(i).getPolNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPolNo());
			}
			if(this.get(i).getDutyCode() == null || this.get(i).getDutyCode().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getDutyCode());
			}
			if(this.get(i).getGetDutyKind() == null || this.get(i).getGetDutyKind().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGetDutyKind());
			}
			if(this.get(i).getGetDutyCode() == null || this.get(i).getGetDutyCode().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getGetDutyCode());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpContNo());
			}
			if(this.get(i).getGrpPolNo() == null || this.get(i).getGrpPolNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getGrpPolNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getContNo());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getAppntNo());
			}
			if(this.get(i).getInsuredNo() == null || this.get(i).getInsuredNo().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getInsuredNo());
			}
			if(this.get(i).getGetDate() == null || this.get(i).getGetDate().equals("null")) {
				pstmt.setDate(11,null);
			} else {
				pstmt.setDate(11, Date.valueOf(this.get(i).getGetDate()));
			}
			pstmt.setDouble(12, this.get(i).getGetMoney());
			if(this.get(i).getEnterAccDate() == null || this.get(i).getEnterAccDate().equals("null")) {
				pstmt.setDate(13,null);
			} else {
				pstmt.setDate(13, Date.valueOf(this.get(i).getEnterAccDate()));
			}
			if(this.get(i).getConfDate() == null || this.get(i).getConfDate().equals("null")) {
				pstmt.setDate(14,null);
			} else {
				pstmt.setDate(14, Date.valueOf(this.get(i).getConfDate()));
			}
			if(this.get(i).getFeeOperationType() == null || this.get(i).getFeeOperationType().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getFeeOperationType());
			}
			if(this.get(i).getFeeFinaType() == null || this.get(i).getFeeFinaType().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getFeeFinaType());
			}
			if(this.get(i).getKindCode() == null || this.get(i).getKindCode().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getKindCode());
			}
			if(this.get(i).getRiskCode() == null || this.get(i).getRiskCode().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getRiskCode());
			}
			if(this.get(i).getRiskVersion() == null || this.get(i).getRiskVersion().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getRiskVersion());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getManageCom());
			}
			if(this.get(i).getAgentCom() == null || this.get(i).getAgentCom().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAgentCom());
			}
			if(this.get(i).getAgentType() == null || this.get(i).getAgentType().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getAgentType());
			}
			if(this.get(i).getAgentCode() == null || this.get(i).getAgentCode().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getAgentCode());
			}
			if(this.get(i).getAgentGroup() == null || this.get(i).getAgentGroup().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getAgentGroup());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getGrpName());
			}
			if(this.get(i).getHandler() == null || this.get(i).getHandler().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getHandler());
			}
			if(this.get(i).getLastGettoDate() == null || this.get(i).getLastGettoDate().equals("null")) {
				pstmt.setDate(27,null);
			} else {
				pstmt.setDate(27, Date.valueOf(this.get(i).getLastGettoDate()));
			}
			if(this.get(i).getCurGetToDate() == null || this.get(i).getCurGetToDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getCurGetToDate()));
			}
			if(this.get(i).getGetmFirstFlag() == null || this.get(i).getGetmFirstFlag().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getGetmFirstFlag());
			}
			if(this.get(i).getGetChannelFlag() == null || this.get(i).getGetChannelFlag().equals("null")) {
				pstmt.setString(30,null);
			} else {
				pstmt.setString(30, this.get(i).getGetChannelFlag());
			}
			if(this.get(i).getDestrayFlag() == null || this.get(i).getDestrayFlag().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getDestrayFlag());
			}
			if(this.get(i).getPolType() == null || this.get(i).getPolType().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getPolType());
			}
			if(this.get(i).getApproveCode() == null || this.get(i).getApproveCode().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getApproveCode());
			}
			if(this.get(i).getApproveDate() == null || this.get(i).getApproveDate().equals("null")) {
				pstmt.setDate(34,null);
			} else {
				pstmt.setDate(34, Date.valueOf(this.get(i).getApproveDate()));
			}
			if(this.get(i).getApproveTime() == null || this.get(i).getApproveTime().equals("null")) {
				pstmt.setString(35,null);
			} else {
				pstmt.setString(35, this.get(i).getApproveTime());
			}
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(36,null);
			} else {
				pstmt.setString(36, this.get(i).getSerialNo());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(37,null);
			} else {
				pstmt.setString(37, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(38,null);
			} else {
				pstmt.setDate(38, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getMakeTime());
			}
			if(this.get(i).getGetNoticeNo() == null || this.get(i).getGetNoticeNo().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getGetNoticeNo());
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
			if(this.get(i).getRReportFlag() == null || this.get(i).getRReportFlag().equals("null")) {
				pstmt.setString(43,null);
			} else {
				pstmt.setString(43, this.get(i).getRReportFlag());
			}
			if(this.get(i).getComeFlag() == null || this.get(i).getComeFlag().equals("null")) {
				pstmt.setString(44,null);
			} else {
				pstmt.setString(44, this.get(i).getComeFlag());
			}
			if(this.get(i).getCurrency() == null || this.get(i).getCurrency().equals("null")) {
				pstmt.setString(45,null);
			} else {
				pstmt.setString(45, this.get(i).getCurrency());
			}
			pstmt.setDouble(46, this.get(i).getNetAmount());
			pstmt.setDouble(47, this.get(i).getTaxAmount());
			pstmt.setDouble(48, this.get(i).getTax());

		// only for debug purpose
		SQLString sqlObj = new SQLString("LJAGetDraw");
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
			tError.moduleName = "LJAGetDrawDBSet";
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
