/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LIItemElemSchema;
import com.sinosoft.lis.vschema.LIItemElemSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LIItemElemDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIItemElemDBSet extends LIItemElemSet
{
private static Logger logger = Logger.getLogger(LIItemElemDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LIItemElemDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LIItemElem");
		mflag = true;
	}

	public LIItemElemDBSet()
	{
		db = new DBOper( "LIItemElem" );
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
			tError.moduleName = "LIItemElemDBSet";
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
			pstmt = con.prepareStatement("DELETE FROM LIItemElem WHERE  AccCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAccCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIItemElem");
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
			tError.moduleName = "LIItemElemDBSet";
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
			pstmt = con.prepareStatement("UPDATE LIItemElem SET  AccType = ? , AccCode = ? , AccName = ? , PayWayFlag = ? , AccSumFlag = ? , DepartmentFlag = ? , SellWayFlag = ? , RiskKind1Flag = ? , RiskKind2Flag = ? , RiskCodeFlag = ? , PayKindFlag = ? , PayPrdFlag = ? , ContNoFlag = ? , BankCodeFlag = ? , AgenComFlag = ? , AmneNoFlag = ? , GropNameFlag = ? , StandPremFlag = ? , AbstrFlag = ? , ComCodeFlag = ? , AccUseFlag = ? , PayModeFlag = ? , CheckNoFlag = ? , ROLLBACKFLAG = ? WHERE  AccCode = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAccType() == null || this.get(i).getAccType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAccType());
			}
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAccCode());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccName());
			}
			if(this.get(i).getPayWayFlag() == null || this.get(i).getPayWayFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayWayFlag());
			}
			if(this.get(i).getAccSumFlag() == null || this.get(i).getAccSumFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccSumFlag());
			}
			if(this.get(i).getDepartmentFlag() == null || this.get(i).getDepartmentFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDepartmentFlag());
			}
			if(this.get(i).getSellWayFlag() == null || this.get(i).getSellWayFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSellWayFlag());
			}
			if(this.get(i).getRiskKind1Flag() == null || this.get(i).getRiskKind1Flag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRiskKind1Flag());
			}
			if(this.get(i).getRiskKind2Flag() == null || this.get(i).getRiskKind2Flag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskKind2Flag());
			}
			if(this.get(i).getRiskCodeFlag() == null || this.get(i).getRiskCodeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCodeFlag());
			}
			if(this.get(i).getPayKindFlag() == null || this.get(i).getPayKindFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPayKindFlag());
			}
			if(this.get(i).getPayPrdFlag() == null || this.get(i).getPayPrdFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPayPrdFlag());
			}
			if(this.get(i).getContNoFlag() == null || this.get(i).getContNoFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getContNoFlag());
			}
			if(this.get(i).getBankCodeFlag() == null || this.get(i).getBankCodeFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBankCodeFlag());
			}
			if(this.get(i).getAgenComFlag() == null || this.get(i).getAgenComFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgenComFlag());
			}
			if(this.get(i).getAmneNoFlag() == null || this.get(i).getAmneNoFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAmneNoFlag());
			}
			if(this.get(i).getGropNameFlag() == null || this.get(i).getGropNameFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGropNameFlag());
			}
			if(this.get(i).getStandPremFlag() == null || this.get(i).getStandPremFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandPremFlag());
			}
			if(this.get(i).getAbstrFlag() == null || this.get(i).getAbstrFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAbstrFlag());
			}
			if(this.get(i).getComCodeFlag() == null || this.get(i).getComCodeFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getComCodeFlag());
			}
			if(this.get(i).getAccUseFlag() == null || this.get(i).getAccUseFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAccUseFlag());
			}
			if(this.get(i).getPayModeFlag() == null || this.get(i).getPayModeFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPayModeFlag());
			}
			if(this.get(i).getCheckNoFlag() == null || this.get(i).getCheckNoFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getCheckNoFlag());
			}
			if(this.get(i).getROLLBACKFLAG() == null || this.get(i).getROLLBACKFLAG().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getROLLBACKFLAG());
			}
			// set where condition
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(25,null);
			} else {
				pstmt.setString(25, this.get(i).getAccCode());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIItemElem");
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
			tError.moduleName = "LIItemElemDBSet";
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
			pstmt = con.prepareStatement("INSERT INTO LIItemElem(AccType ,AccCode ,AccName ,PayWayFlag ,AccSumFlag ,DepartmentFlag ,SellWayFlag ,RiskKind1Flag ,RiskKind2Flag ,RiskCodeFlag ,PayKindFlag ,PayPrdFlag ,ContNoFlag ,BankCodeFlag ,AgenComFlag ,AmneNoFlag ,GropNameFlag ,StandPremFlag ,AbstrFlag ,ComCodeFlag ,AccUseFlag ,PayModeFlag ,CheckNoFlag ,ROLLBACKFLAG) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getAccType() == null || this.get(i).getAccType().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getAccType());
			}
			if(this.get(i).getAccCode() == null || this.get(i).getAccCode().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getAccCode());
			}
			if(this.get(i).getAccName() == null || this.get(i).getAccName().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getAccName());
			}
			if(this.get(i).getPayWayFlag() == null || this.get(i).getPayWayFlag().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPayWayFlag());
			}
			if(this.get(i).getAccSumFlag() == null || this.get(i).getAccSumFlag().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAccSumFlag());
			}
			if(this.get(i).getDepartmentFlag() == null || this.get(i).getDepartmentFlag().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getDepartmentFlag());
			}
			if(this.get(i).getSellWayFlag() == null || this.get(i).getSellWayFlag().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getSellWayFlag());
			}
			if(this.get(i).getRiskKind1Flag() == null || this.get(i).getRiskKind1Flag().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getRiskKind1Flag());
			}
			if(this.get(i).getRiskKind2Flag() == null || this.get(i).getRiskKind2Flag().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getRiskKind2Flag());
			}
			if(this.get(i).getRiskCodeFlag() == null || this.get(i).getRiskCodeFlag().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getRiskCodeFlag());
			}
			if(this.get(i).getPayKindFlag() == null || this.get(i).getPayKindFlag().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getPayKindFlag());
			}
			if(this.get(i).getPayPrdFlag() == null || this.get(i).getPayPrdFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getPayPrdFlag());
			}
			if(this.get(i).getContNoFlag() == null || this.get(i).getContNoFlag().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getContNoFlag());
			}
			if(this.get(i).getBankCodeFlag() == null || this.get(i).getBankCodeFlag().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getBankCodeFlag());
			}
			if(this.get(i).getAgenComFlag() == null || this.get(i).getAgenComFlag().equals("null")) {
				pstmt.setString(15,null);
			} else {
				pstmt.setString(15, this.get(i).getAgenComFlag());
			}
			if(this.get(i).getAmneNoFlag() == null || this.get(i).getAmneNoFlag().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getAmneNoFlag());
			}
			if(this.get(i).getGropNameFlag() == null || this.get(i).getGropNameFlag().equals("null")) {
				pstmt.setString(17,null);
			} else {
				pstmt.setString(17, this.get(i).getGropNameFlag());
			}
			if(this.get(i).getStandPremFlag() == null || this.get(i).getStandPremFlag().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getStandPremFlag());
			}
			if(this.get(i).getAbstrFlag() == null || this.get(i).getAbstrFlag().equals("null")) {
				pstmt.setString(19,null);
			} else {
				pstmt.setString(19, this.get(i).getAbstrFlag());
			}
			if(this.get(i).getComCodeFlag() == null || this.get(i).getComCodeFlag().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getComCodeFlag());
			}
			if(this.get(i).getAccUseFlag() == null || this.get(i).getAccUseFlag().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getAccUseFlag());
			}
			if(this.get(i).getPayModeFlag() == null || this.get(i).getPayModeFlag().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getPayModeFlag());
			}
			if(this.get(i).getCheckNoFlag() == null || this.get(i).getCheckNoFlag().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getCheckNoFlag());
			}
			if(this.get(i).getROLLBACKFLAG() == null || this.get(i).getROLLBACKFLAG().equals("null")) {
				pstmt.setString(24,null);
			} else {
				pstmt.setString(24, this.get(i).getROLLBACKFLAG());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LIItemElem");
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
			tError.moduleName = "LIItemElemDBSet";
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
