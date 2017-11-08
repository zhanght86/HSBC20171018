/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LBPOCustomerImpartBSchema;
import com.sinosoft.lis.vschema.LBPOCustomerImpartBSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LBPOCustomerImpartBDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LBPOCustomerImpartBDBSet extends LBPOCustomerImpartBSet
{
private static Logger logger = Logger.getLogger(LBPOCustomerImpartBDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LBPOCustomerImpartBDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LBPOCustomerImpartB");
		mflag = true;
	}

	public LBPOCustomerImpartBDBSet()
	{
		db = new DBOper( "LBPOCustomerImpartB" );
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
			tError.moduleName = "LBPOCustomerImpartBDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LBPOCustomerImpartB WHERE  IDX = ? AND GrpContNo = ? AND InputNo = ? AND FillNo = ? AND ProposalContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			pstmt.setInt(3, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFillNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getProposalContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOCustomerImpartB");
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
			tError.moduleName = "LBPOCustomerImpartBDBSet";
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
			pstmt = con.prepareStatement("UPDATE LBPOCustomerImpartB SET  IDX = ? , GrpContNo = ? , InputNo = ? , FillNo = ? , ContNo = ? , ProposalContNo = ? , PrtNo = ? , ImpartCode = ? , ImpartVer = ? , ImpartContent = ? , ImpartParamModle = ? , CustomerNo = ? , CustomerNoType = ? , UWClaimFlg = ? , PrtFlag = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , PatchNo = ? , Insured1 = ? , Insured2 = ? , insured3 = ? , TransDate = ? , TransTime = ? , TransOperator = ? WHERE  IDX = ? AND GrpContNo = ? AND InputNo = ? AND FillNo = ? AND ProposalContNo = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			pstmt.setInt(3, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFillNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getProposalContNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPrtNo());
			}
			if(this.get(i).getImpartCode() == null || this.get(i).getImpartCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getImpartCode());
			}
			if(this.get(i).getImpartVer() == null || this.get(i).getImpartVer().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getImpartVer());
			}
			if(this.get(i).getImpartContent() == null || this.get(i).getImpartContent().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getImpartContent());
			}
			if(this.get(i).getImpartParamModle() == null || this.get(i).getImpartParamModle().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getImpartParamModle());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerNoType() == null || this.get(i).getCustomerNoType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCustomerNoType());
			}
			if(this.get(i).getUWClaimFlg() == null || this.get(i).getUWClaimFlg().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUWClaimFlg());
			}
			if(this.get(i).getPrtFlag() == null || this.get(i).getPrtFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPrtFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMakeDate());
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getModifyDate());
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			pstmt.setInt(21, this.get(i).getPatchNo());
			if(this.get(i).getInsured1() == null || this.get(i).getInsured1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getInsured1());
			}
			if(this.get(i).getInsured2() == null || this.get(i).getInsured2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getInsured2());
			}
			if(this.get(i).getinsured3() == null || this.get(i).getinsured3().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getinsured3());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransTime() == null || this.get(i).getTransTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getTransTime());
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getTransOperator());
			}
			// set where condition
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getIDX());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(29,null);
			} else {
				pstmt.setString(29, this.get(i).getGrpContNo());
			}
			pstmt.setInt(30, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(31,null);
			} else {
				pstmt.setString(31, this.get(i).getFillNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(32,null);
			} else {
				pstmt.setString(32, this.get(i).getProposalContNo());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOCustomerImpartB");
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
			tError.moduleName = "LBPOCustomerImpartBDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LBPOCustomerImpartB(IDX ,GrpContNo ,InputNo ,FillNo ,ContNo ,ProposalContNo ,PrtNo ,ImpartCode ,ImpartVer ,ImpartContent ,ImpartParamModle ,CustomerNo ,CustomerNoType ,UWClaimFlg ,PrtFlag ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,PatchNo ,Insured1 ,Insured2 ,insured3 ,TransDate ,TransTime ,TransOperator) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getIDX() == null || this.get(i).getIDX().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getIDX());
			}
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getGrpContNo());
			}
			pstmt.setInt(3, this.get(i).getInputNo());
			if(this.get(i).getFillNo() == null || this.get(i).getFillNo().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getFillNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getContNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getProposalContNo());
			}
			if(this.get(i).getPrtNo() == null || this.get(i).getPrtNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getPrtNo());
			}
			if(this.get(i).getImpartCode() == null || this.get(i).getImpartCode().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getImpartCode());
			}
			if(this.get(i).getImpartVer() == null || this.get(i).getImpartVer().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getImpartVer());
			}
			if(this.get(i).getImpartContent() == null || this.get(i).getImpartContent().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getImpartContent());
			}
			if(this.get(i).getImpartParamModle() == null || this.get(i).getImpartParamModle().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getImpartParamModle());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getCustomerNo());
			}
			if(this.get(i).getCustomerNoType() == null || this.get(i).getCustomerNoType().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getCustomerNoType());
			}
			if(this.get(i).getUWClaimFlg() == null || this.get(i).getUWClaimFlg().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getUWClaimFlg());
			}
			if(this.get(i).getPrtFlag() == null || this.get(i).getPrtFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getPrtFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getOperator());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getMakeDate());
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getModifyDate());
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			pstmt.setInt(21, this.get(i).getPatchNo());
			if(this.get(i).getInsured1() == null || this.get(i).getInsured1().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getInsured1());
			}
			if(this.get(i).getInsured2() == null || this.get(i).getInsured2().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getInsured2());
			}
			if(this.get(i).getinsured3() == null || this.get(i).getinsured3().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getinsured3());
			}
			if(this.get(i).getTransDate() == null || this.get(i).getTransDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getTransDate()));
			}
			if(this.get(i).getTransTime() == null || this.get(i).getTransTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getTransTime());
			}
			if(this.get(i).getTransOperator() == null || this.get(i).getTransOperator().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getTransOperator());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LBPOCustomerImpartB");
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
			tError.moduleName = "LBPOCustomerImpartBDBSet";
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
