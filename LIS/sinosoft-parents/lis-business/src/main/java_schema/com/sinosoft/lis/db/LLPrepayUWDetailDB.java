/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.db;
import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LLPrepayUWDetailSchema;
import com.sinosoft.lis.vschema.LLPrepayUWDetailSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LLPrepayUWDetailDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLPrepayUWDetailDB extends LLPrepayUWDetailSchema
{
private static Logger logger = Logger.getLogger(LLPrepayUWDetailDB.class);

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
	public LLPrepayUWDetailDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LLPrepayUWDetail" );
		mflag = true;
	}

	public LLPrepayUWDetailDB()
	{
		con = null;
		db = new DBOper( "LLPrepayUWDetail" );
		mflag = false;
	}

	// @Method
	public boolean deleteSQL()
	{
		LLPrepayUWDetailSchema tSchema = this.getSchema();
		if (db.deleteSQL(tSchema))
		{
		     return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

	public int getCount()
	{
		LLPrepayUWDetailSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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
			pstmt = con.prepareStatement("DELETE FROM LLPrepayUWDetail WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND SerialNo = ? AND PrepayNo = ? AND RgtNo = ? AND DutyCode = ? AND CaseRelaNo = ?");
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
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGetDutyCode());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSerialNo());
			}
			if(this.getPrepayNo() == null || this.getPrepayNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPrepayNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRgtNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCaseRelaNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

		// only for debug purpose
		SQLString sqlObj = new SQLString("LLPrepayUWDetail");
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
		SQLString sqlObj = new SQLString("LLPrepayUWDetail");
		sqlObj.setSQL(2, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("UPDATE LLPrepayUWDetail SET  BackNo = ? , ClmNo = ? , CaseNo = ? , PolNo = ? , GetDutyKind = ? , GetDutyCode = ? , SerialNo = ? , PrepayNo = ? , RgtNo = ? , DeclineNo = ? , GrpContNo = ? , GrpPolNo = ? , ContNo = ? , KindCode = ? , RiskCode = ? , RiskVer = ? , PolMngCom = ? , SaleChnl = ? , PrepaySum = ? , Remark = ? , DutyCode = ? , MngCom = ? , Operator = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , CaseGetMode = ? , CasePayMode = ? , CasePayFlag = ? , NBPolNo = ? , CaseRelaNo = ? WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND SerialNo = ? AND PrepayNo = ? AND RgtNo = ? AND DutyCode = ? AND CaseRelaNo = ?");
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
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGetDutyCode());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSerialNo());
			}
			if(this.getPrepayNo() == null || this.getPrepayNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPrepayNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRgtNo());
			}
			if(this.getDeclineNo() == null || this.getDeclineNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getDeclineNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getContNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getRiskVer());
			}
			if(this.getPolMngCom() == null || this.getPolMngCom().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPolMngCom());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getSaleChnl());
			}
			pstmt.setDouble(19, this.getPrepaySum());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getRemark());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getDutyCode());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getModifyTime());
			}
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCaseGetMode());
			}
			if(this.getCasePayMode() == null || this.getCasePayMode().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getCasePayMode());
			}
			if(this.getCasePayFlag() == null || this.getCasePayFlag().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getCasePayFlag());
			}
			if(this.getNBPolNo() == null || this.getNBPolNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getNBPolNo());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getCaseRelaNo());
			}
			// set where condition
			if(this.getBackNo() == null || this.getBackNo().equals("null")) {
				pstmt.setNull(33, 12);
			} else {
				pstmt.setString(33, this.getBackNo());
			}
			if(this.getClmNo() == null || this.getClmNo().equals("null")) {
				pstmt.setNull(34, 12);
			} else {
				pstmt.setString(34, this.getClmNo());
			}
			if(this.getCaseNo() == null || this.getCaseNo().equals("null")) {
				pstmt.setNull(35, 12);
			} else {
				pstmt.setString(35, this.getCaseNo());
			}
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(36, 12);
			} else {
				pstmt.setString(36, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(37, 12);
			} else {
				pstmt.setString(37, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(38, 12);
			} else {
				pstmt.setString(38, this.getGetDutyCode());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(39, 12);
			} else {
				pstmt.setString(39, this.getSerialNo());
			}
			if(this.getPrepayNo() == null || this.getPrepayNo().equals("null")) {
				pstmt.setNull(40, 12);
			} else {
				pstmt.setString(40, this.getPrepayNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(41, 12);
			} else {
				pstmt.setString(41, this.getRgtNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(42, 12);
			} else {
				pstmt.setString(42, this.getDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(43, 12);
			} else {
				pstmt.setString(43, this.getCaseRelaNo());
			}
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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
		SQLString sqlObj = new SQLString("LLPrepayUWDetail");
		sqlObj.setSQL(1, this);
		sqlObj.getSQL();

		try
		{
			pstmt = con.prepareStatement("INSERT INTO LLPrepayUWDetail(BackNo ,ClmNo ,CaseNo ,PolNo ,GetDutyKind ,GetDutyCode ,SerialNo ,PrepayNo ,RgtNo ,DeclineNo ,GrpContNo ,GrpPolNo ,ContNo ,KindCode ,RiskCode ,RiskVer ,PolMngCom ,SaleChnl ,PrepaySum ,Remark ,DutyCode ,MngCom ,Operator ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,CaseGetMode ,CasePayMode ,CasePayFlag ,NBPolNo ,CaseRelaNo) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
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
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGetDutyCode());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSerialNo());
			}
			if(this.getPrepayNo() == null || this.getPrepayNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPrepayNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRgtNo());
			}
			if(this.getDeclineNo() == null || this.getDeclineNo().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getDeclineNo());
			}
			if(this.getGrpContNo() == null || this.getGrpContNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getGrpContNo());
			}
			if(this.getGrpPolNo() == null || this.getGrpPolNo().equals("null")) {
				pstmt.setNull(12, 12);
			} else {
				pstmt.setString(12, this.getGrpPolNo());
			}
			if(this.getContNo() == null || this.getContNo().equals("null")) {
				pstmt.setNull(13, 12);
			} else {
				pstmt.setString(13, this.getContNo());
			}
			if(this.getKindCode() == null || this.getKindCode().equals("null")) {
				pstmt.setNull(14, 12);
			} else {
				pstmt.setString(14, this.getKindCode());
			}
			if(this.getRiskCode() == null || this.getRiskCode().equals("null")) {
				pstmt.setNull(15, 12);
			} else {
				pstmt.setString(15, this.getRiskCode());
			}
			if(this.getRiskVer() == null || this.getRiskVer().equals("null")) {
				pstmt.setNull(16, 12);
			} else {
				pstmt.setString(16, this.getRiskVer());
			}
			if(this.getPolMngCom() == null || this.getPolMngCom().equals("null")) {
				pstmt.setNull(17, 12);
			} else {
				pstmt.setString(17, this.getPolMngCom());
			}
			if(this.getSaleChnl() == null || this.getSaleChnl().equals("null")) {
				pstmt.setNull(18, 12);
			} else {
				pstmt.setString(18, this.getSaleChnl());
			}
			pstmt.setDouble(19, this.getPrepaySum());
			if(this.getRemark() == null || this.getRemark().equals("null")) {
				pstmt.setNull(20, 12);
			} else {
				pstmt.setString(20, this.getRemark());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(21, 12);
			} else {
				pstmt.setString(21, this.getDutyCode());
			}
			if(this.getMngCom() == null || this.getMngCom().equals("null")) {
				pstmt.setNull(22, 12);
			} else {
				pstmt.setString(22, this.getMngCom());
			}
			if(this.getOperator() == null || this.getOperator().equals("null")) {
				pstmt.setNull(23, 12);
			} else {
				pstmt.setString(23, this.getOperator());
			}
			if(this.getMakeDate() == null || this.getMakeDate().equals("null")) {
				pstmt.setNull(24, 91);
			} else {
				pstmt.setDate(24, Date.valueOf(this.getMakeDate()));
			}
			if(this.getMakeTime() == null || this.getMakeTime().equals("null")) {
				pstmt.setNull(25, 1);
			} else {
				pstmt.setString(25, this.getMakeTime());
			}
			if(this.getModifyDate() == null || this.getModifyDate().equals("null")) {
				pstmt.setNull(26, 91);
			} else {
				pstmt.setDate(26, Date.valueOf(this.getModifyDate()));
			}
			if(this.getModifyTime() == null || this.getModifyTime().equals("null")) {
				pstmt.setNull(27, 1);
			} else {
				pstmt.setString(27, this.getModifyTime());
			}
			if(this.getCaseGetMode() == null || this.getCaseGetMode().equals("null")) {
				pstmt.setNull(28, 12);
			} else {
				pstmt.setString(28, this.getCaseGetMode());
			}
			if(this.getCasePayMode() == null || this.getCasePayMode().equals("null")) {
				pstmt.setNull(29, 12);
			} else {
				pstmt.setString(29, this.getCasePayMode());
			}
			if(this.getCasePayFlag() == null || this.getCasePayFlag().equals("null")) {
				pstmt.setNull(30, 12);
			} else {
				pstmt.setString(30, this.getCasePayFlag());
			}
			if(this.getNBPolNo() == null || this.getNBPolNo().equals("null")) {
				pstmt.setNull(31, 12);
			} else {
				pstmt.setString(31, this.getNBPolNo());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(32, 12);
			} else {
				pstmt.setString(32, this.getCaseRelaNo());
			}
			// execute sql
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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
			pstmt = con.prepareStatement("SELECT * FROM LLPrepayUWDetail WHERE  BackNo = ? AND ClmNo = ? AND CaseNo = ? AND PolNo = ? AND GetDutyKind = ? AND GetDutyCode = ? AND SerialNo = ? AND PrepayNo = ? AND RgtNo = ? AND DutyCode = ? AND CaseRelaNo = ?", 
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
			if(this.getPolNo() == null || this.getPolNo().equals("null")) {
				pstmt.setNull(4, 12);
			} else {
				pstmt.setString(4, this.getPolNo());
			}
			if(this.getGetDutyKind() == null || this.getGetDutyKind().equals("null")) {
				pstmt.setNull(5, 12);
			} else {
				pstmt.setString(5, this.getGetDutyKind());
			}
			if(this.getGetDutyCode() == null || this.getGetDutyCode().equals("null")) {
				pstmt.setNull(6, 12);
			} else {
				pstmt.setString(6, this.getGetDutyCode());
			}
			if(this.getSerialNo() == null || this.getSerialNo().equals("null")) {
				pstmt.setNull(7, 12);
			} else {
				pstmt.setString(7, this.getSerialNo());
			}
			if(this.getPrepayNo() == null || this.getPrepayNo().equals("null")) {
				pstmt.setNull(8, 12);
			} else {
				pstmt.setString(8, this.getPrepayNo());
			}
			if(this.getRgtNo() == null || this.getRgtNo().equals("null")) {
				pstmt.setNull(9, 12);
			} else {
				pstmt.setString(9, this.getRgtNo());
			}
			if(this.getDutyCode() == null || this.getDutyCode().equals("null")) {
				pstmt.setNull(10, 12);
			} else {
				pstmt.setString(10, this.getDutyCode());
			}
			if(this.getCaseRelaNo() == null || this.getCaseRelaNo().equals("null")) {
				pstmt.setNull(11, 12);
			} else {
				pstmt.setString(11, this.getCaseRelaNo());
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
					tError.moduleName = "LLPrepayUWDetailDB";
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
			tError.moduleName = "LLPrepayUWDetailDB";
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

	public LLPrepayUWDetailSet query()
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLPrepayUWDetailSet aLLPrepayUWDetailSet = new LLPrepayUWDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLPrepayUWDetail");
			LLPrepayUWDetailSchema aSchema = this.getSchema();
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
				LLPrepayUWDetailSchema s1 = new LLPrepayUWDetailSchema();
				s1.setSchema(rs,i);
				aLLPrepayUWDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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

		return aLLPrepayUWDetailSet;

	}

	public LLPrepayUWDetailSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLPrepayUWDetailSet aLLPrepayUWDetailSet = new LLPrepayUWDetailSet();

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
				LLPrepayUWDetailSchema s1 = new LLPrepayUWDetailSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLPrepayUWDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLPrepayUWDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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

		return aLLPrepayUWDetailSet;
	}

	public LLPrepayUWDetailSet query(int nStart, int nCount)
	{
		PreparedStatement pstmt = null;
 		ResultSet rs = null;
		LLPrepayUWDetailSet aLLPrepayUWDetailSet = new LLPrepayUWDetailSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			SQLString sqlObj = new SQLString("LLPrepayUWDetail");
			LLPrepayUWDetailSchema aSchema = this.getSchema();
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

				LLPrepayUWDetailSchema s1 = new LLPrepayUWDetailSchema();
				s1.setSchema(rs,i);
				aLLPrepayUWDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ pstmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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

		return aLLPrepayUWDetailSet;

	}

	public LLPrepayUWDetailSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LLPrepayUWDetailSet aLLPrepayUWDetailSet = new LLPrepayUWDetailSet();

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

				LLPrepayUWDetailSchema s1 = new LLPrepayUWDetailSchema();
				if (!s1.setSchema(rs,i))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLPrepayUWDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLPrepayUWDetailSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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

		return aLLPrepayUWDetailSet;
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
			SQLString sqlObj = new SQLString("LLPrepayUWDetail");
			LLPrepayUWDetailSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LLPrepayUWDetail " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LLPrepayUWDetailDB";
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
			tError.moduleName = "LLPrepayUWDetailDB";
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
        tError.moduleName = "LLPrepayUWDetailDB";
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
        tError.moduleName = "LLPrepayUWDetailDB";
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
        tError.moduleName = "LLPrepayUWDetailDB";
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
        tError.moduleName = "LLPrepayUWDetailDB";
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
 * @return LLPrepayUWDetailSet
 */
public LLPrepayUWDetailSet getData()
{
    int tCount = 0;
    LLPrepayUWDetailSet tLLPrepayUWDetailSet = new LLPrepayUWDetailSet();
    LLPrepayUWDetailSchema tLLPrepayUWDetailSchema = null;
    if (null == mResultSet)
    {
        CError tError = new CError();
        tError.moduleName = "LLPrepayUWDetailDB";
        tError.functionName = "getData";
        tError.errorMessage = "数据集为空，请先准备数据集！";
        this.mErrors.addOneError(tError);
        return null;
    }
    try
    {
        tCount = 1;
        tLLPrepayUWDetailSchema = new LLPrepayUWDetailSchema();
        tLLPrepayUWDetailSchema.setSchema(mResultSet, 1);
        tLLPrepayUWDetailSet.add(tLLPrepayUWDetailSchema);
        //注意mResultSet.next()的作用
        while (tCount++ < SysConst.FETCHCOUNT)
        {
            if (mResultSet.next())
            {
                tLLPrepayUWDetailSchema = new LLPrepayUWDetailSchema();
                tLLPrepayUWDetailSchema.setSchema(mResultSet, 1);
                tLLPrepayUWDetailSet.add(tLLPrepayUWDetailSchema);
            }
        }
    }
    catch (Exception ex)
    {
        CError tError = new CError();
        tError.moduleName = "LLPrepayUWDetailDB";
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
    return tLLPrepayUWDetailSet;
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
            tError.moduleName = "LLPrepayUWDetailDB";
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
        tError.moduleName = "LLPrepayUWDetailDB";
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
            tError.moduleName = "LLPrepayUWDetailDB";
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
        tError.moduleName = "LLPrepayUWDetailDB";
        tError.functionName = "closeData";
        tError.errorMessage = ex3.toString();
        this.mErrors.addOneError(tError);
        flag = false;
    }
    return flag;
}

	public LLPrepayUWDetailSet executeQuery(SQLwithBindVariables sqlbv) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLPrepayUWDetailSet aLLPrepayUWDetailSet = new LLPrepayUWDetailSet();

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
				LLPrepayUWDetailSchema s1 = new LLPrepayUWDetailSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLPrepayUWDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLPrepayUWDetailSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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

		return aLLPrepayUWDetailSet;
	}

	public LLPrepayUWDetailSet executeQuery(SQLwithBindVariables sqlbv, int nStart, int nCount) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LLPrepayUWDetailSet aLLPrepayUWDetailSet = new LLPrepayUWDetailSet();

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

				LLPrepayUWDetailSchema s1 = new LLPrepayUWDetailSchema();
				if (!s1.setSchema(rs,i)) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLPrepayUWDetailDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aLLPrepayUWDetailSet.add(s1);
			}
			try{ rs.close(); } catch(Exception ex) {}
			try{ pstmt.close(); } catch(Exception ex1) {}
		}
		catch(Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayUWDetailDB";
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

		return aLLPrepayUWDetailSet; 
	}

}
