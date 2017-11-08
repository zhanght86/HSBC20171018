/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LDGrpBlackListTraceSchema;
import com.sinosoft.lis.vschema.LDGrpBlackListTraceSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LDGrpBlackListTraceDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LDGrpBlackListTraceDBSet extends LDGrpBlackListTraceSet
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
	public LDGrpBlackListTraceDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LDGrpBlackListTrace");
		mflag = true;
	}

	public LDGrpBlackListTraceDBSet()
	{
		db = new DBOper( "LDGrpBlackListTrace" );
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
			tError.moduleName = "LDGrpBlackListTraceDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LDGrpBlackListTrace WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDGrpBlackListTrace");
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
			tError.moduleName = "LDGrpBlackListTraceDBSet";
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
			pstmt = con.prepareStatement("UPDATE LDGrpBlackListTrace SET  SerialNo = ? , CustomerNo = ? , Password = ? , GrpName = ? , BusinessType = ? , GrpNature = ? , Peoples = ? , RgtMoney = ? , Asset = ? , NetProfitRate = ? , MainBussiness = ? , Corporation = ? , ComAera = ? , Fax = ? , Phone = ? , GetFlag = ? , Satrap = ? , EMail = ? , FoundDate = ? , BankCode = ? , BankAccNo = ? , GrpGroupNo = ? , BlacklistFlag = ? , State = ? , Remark = ? , VIPValue = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , SubCompanyFlag = ? , SupCustoemrNo = ? , LevelCode = ? , OnWorkPeoples = ? , OffWorkPeoples = ? , OtherPeoples = ? , BusinessBigType = ? , SocialInsuNo = ? , BlackListReason = ? WHERE  SerialNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerNo());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPassword());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpName());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpNature());
			}
			pstmt.setInt(7, this.get(i).getPeoples());
			pstmt.setDouble(8, this.get(i).getRgtMoney());
			pstmt.setDouble(9, this.get(i).getAsset());
			pstmt.setDouble(10, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getComAera());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getBankAccNo());
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getGrpGroupNo());
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getState());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getRemark());
			}
			if(this.get(i).getVIPValue() == null || this.get(i).getVIPValue().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getVIPValue());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getModifyTime());
			}
			if(this.get(i).getSubCompanyFlag() == null || this.get(i).getSubCompanyFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSubCompanyFlag());
			}
			if(this.get(i).getSupCustoemrNo() == null || this.get(i).getSupCustoemrNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getSupCustoemrNo());
			}
			if(this.get(i).getLevelCode() == null || this.get(i).getLevelCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getLevelCode());
			}
			pstmt.setInt(35, this.get(i).getOnWorkPeoples());
			pstmt.setInt(36, this.get(i).getOffWorkPeoples());
			pstmt.setInt(37, this.get(i).getOtherPeoples());
			if(this.get(i).getBusinessBigType() == null || this.get(i).getBusinessBigType().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getBusinessBigType());
			}
			if(this.get(i).getSocialInsuNo() == null || this.get(i).getSocialInsuNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSocialInsuNo());
			}
			if(this.get(i).getBlackListReason() == null || this.get(i).getBlackListReason().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getBlackListReason());
			}
			// set where condition
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(41,null);
			} else {
				pstmt.setString(41, this.get(i).getSerialNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDGrpBlackListTrace");
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
			tError.moduleName = "LDGrpBlackListTraceDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LDGrpBlackListTrace VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getSerialNo() == null || this.get(i).getSerialNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getSerialNo());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getCustomerNo());
			}
			if(this.get(i).getPassword() == null || this.get(i).getPassword().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getPassword());
			}
			if(this.get(i).getGrpName() == null || this.get(i).getGrpName().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getGrpName());
			}
			if(this.get(i).getBusinessType() == null || this.get(i).getBusinessType().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getBusinessType());
			}
			if(this.get(i).getGrpNature() == null || this.get(i).getGrpNature().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getGrpNature());
			}
			pstmt.setInt(7, this.get(i).getPeoples());
			pstmt.setDouble(8, this.get(i).getRgtMoney());
			pstmt.setDouble(9, this.get(i).getAsset());
			pstmt.setDouble(10, this.get(i).getNetProfitRate());
			if(this.get(i).getMainBussiness() == null || this.get(i).getMainBussiness().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getMainBussiness());
			}
			if(this.get(i).getCorporation() == null || this.get(i).getCorporation().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCorporation());
			}
			if(this.get(i).getComAera() == null || this.get(i).getComAera().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getComAera());
			}
			if(this.get(i).getFax() == null || this.get(i).getFax().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getFax());
			}
			if(this.get(i).getPhone() == null || this.get(i).getPhone().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPhone());
			}
			if(this.get(i).getGetFlag() == null || this.get(i).getGetFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getGetFlag());
			}
			if(this.get(i).getSatrap() == null || this.get(i).getSatrap().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getSatrap());
			}
			if(this.get(i).getEMail() == null || this.get(i).getEMail().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getEMail());
			}
			if(this.get(i).getFoundDate() == null || this.get(i).getFoundDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getFoundDate()));
			}
			if(this.get(i).getBankCode() == null || this.get(i).getBankCode().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getBankCode());
			}
			if(this.get(i).getBankAccNo() == null || this.get(i).getBankAccNo().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getBankAccNo());
			}
			if(this.get(i).getGrpGroupNo() == null || this.get(i).getGrpGroupNo().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getGrpGroupNo());
			}
			if(this.get(i).getBlacklistFlag() == null || this.get(i).getBlacklistFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getBlacklistFlag());
			}
			if(this.get(i).getState() == null || this.get(i).getState().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getState());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getRemark());
			}
			if(this.get(i).getVIPValue() == null || this.get(i).getVIPValue().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getVIPValue());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(28,null);
			} else {
				pstmt.setDate(28, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(30,null);
			} else {
				pstmt.setDate(30, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getModifyTime());
			}
			if(this.get(i).getSubCompanyFlag() == null || this.get(i).getSubCompanyFlag().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getSubCompanyFlag());
			}
			if(this.get(i).getSupCustoemrNo() == null || this.get(i).getSupCustoemrNo().equals("null")) {
				pstmt.setString(33,null);
			} else {
				pstmt.setString(33, this.get(i).getSupCustoemrNo());
			}
			if(this.get(i).getLevelCode() == null || this.get(i).getLevelCode().equals("null")) {
				pstmt.setString(34,null);
			} else {
				pstmt.setString(34, this.get(i).getLevelCode());
			}
			pstmt.setInt(35, this.get(i).getOnWorkPeoples());
			pstmt.setInt(36, this.get(i).getOffWorkPeoples());
			pstmt.setInt(37, this.get(i).getOtherPeoples());
			if(this.get(i).getBusinessBigType() == null || this.get(i).getBusinessBigType().equals("null")) {
				pstmt.setString(38,null);
			} else {
				pstmt.setString(38, this.get(i).getBusinessBigType());
			}
			if(this.get(i).getSocialInsuNo() == null || this.get(i).getSocialInsuNo().equals("null")) {
				pstmt.setString(39,null);
			} else {
				pstmt.setString(39, this.get(i).getSocialInsuNo());
			}
			if(this.get(i).getBlackListReason() == null || this.get(i).getBlackListReason().equals("null")) {
				pstmt.setString(40,null);
			} else {
				pstmt.setString(40, this.get(i).getBlackListReason());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LDGrpBlackListTrace");
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
			tError.moduleName = "LDGrpBlackListTraceDBSet";
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
