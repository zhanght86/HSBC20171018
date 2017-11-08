package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class GrpAppModifyMakeSureBLS {
private static Logger logger = Logger.getLogger(GrpAppModifyMakeSureBLS.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GrpAppModifyMakeSureBLS() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 保存数据
		if (this.saveData() == false)
			return false;

		// 释放数据容器
		mInputData = null;

		return true;
	}

	/**
	 * 保存数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean saveData() {
		// 分解传入数据容器中的数据
		LCGrpContSchema mLCGrpContSchema = (LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0);

		// 建立数据连接
		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpAppModifyMakeSureBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 修改团体投保单
			ExeSQL tExeSQL = new ExeSQL(conn);
			String sql = "update LCGrpCont " + "set InputOperator = '"
					+ mLCGrpContSchema.getInputOperator() + "', "
					+ "ApproveFlag = '" + mLCGrpContSchema.getApproveFlag()
					+ "', " + "UWFlag = '" + mLCGrpContSchema.getUWFlag()
					+ "'," + "ModifyDate = '"
					+ mLCGrpContSchema.getModifyDate() + "', "
					+ "ModifyTime = '" + mLCGrpContSchema.getModifyTime()
					+ "' " + "where ProposalGrpContNo = '"
					+ mLCGrpContSchema.getProposalGrpContNo() + "'"
					+ "and appflag = '0'" + "and approveflag = '1'";
			if (tExeSQL.execUpdateSQL(sql) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpAppModifyMakeSureBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCGrpContDBSet修改失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			// 修改团体单中险种单
			tExeSQL = new ExeSQL(conn);
			sql = "update LCGrpPol " + "set Operator = '"
					+ mLCGrpContSchema.getInputOperator() + "', "
					+ "ApproveFlag = '" + mLCGrpContSchema.getApproveFlag()
					+ "', " + "ModifyDate = '"
					+ mLCGrpContSchema.getModifyDate() + "', "
					+ "ModifyTime = '" + mLCGrpContSchema.getModifyTime()
					+ "' " + "where GrpContNo = '"
					+ mLCGrpContSchema.getProposalGrpContNo() + "'"
					// + "and riskcode in
					// (select riskcode from
					// lmriskapp where
					// subriskflag='S')"
					+ "and appflag = '0'" + "and approveflag = '1'";
			if (tExeSQL.execUpdateSQL(sql) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpPolApproveBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCGrpContDBSet修改失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			// 修改团体单中个单险种单
			tExeSQL = new ExeSQL(conn);
			sql = "update LCPol " + "set Operator = '"
					+ mLCGrpContSchema.getInputOperator() + "', "
					+ "ApproveFlag = '" + mLCGrpContSchema.getApproveFlag()
					+ "', " + "ModifyDate = '"
					+ mLCGrpContSchema.getModifyDate() + "', "
					+ "ModifyTime = '" + mLCGrpContSchema.getModifyTime()
					+ "' " + "where GrpContNo = '"
					+ mLCGrpContSchema.getProposalGrpContNo() + "'"
					// + "and riskcode in
					// (select riskcode from
					// lmriskapp where
					// subriskflag='S')"
					+ "and appflag = '0'" + "and approveflag = '1'";
			if (tExeSQL.execUpdateSQL(sql) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpPolApproveBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCGrpContDBSet修改失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 修改团体单中个人投保单
			tExeSQL = new ExeSQL(conn);
			sql = "update LCCont " + "set InputOperator = '"
					+ mLCGrpContSchema.getInputOperator() + "', "
					+ "ApproveFlag = '" + mLCGrpContSchema.getApproveFlag()
					+ "', " + "UWFlag = '" + mLCGrpContSchema.getUWFlag()
					+ "'," + "ModifyDate = '"
					+ mLCGrpContSchema.getModifyDate() + "', "
					+ "ModifyTime = '" + mLCGrpContSchema.getModifyTime()
					+ "' " + "where GrpContNo = '"
					+ mLCGrpContSchema.getProposalGrpContNo() + "'"
					+ "and appflag = '0'" + "and approveflag = '1'";
			if (tExeSQL.execUpdateSQL(sql) == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpAppModifyMakeSureBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCGrpContDBSet修改失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} // end of try
		catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpAppModifyMakeSureBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}
}
