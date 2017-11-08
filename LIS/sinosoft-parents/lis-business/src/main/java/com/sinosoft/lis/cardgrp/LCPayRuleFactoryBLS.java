/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LCPayRuleFactoryDBSet;
import com.sinosoft.lis.vdb.LCPayRuleParamsDBSet;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.lis.vschema.LCPayRuleParamsSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * 保障计划要素后台提交
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据操作类型，进行新增、删除、修改操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 * 
 * @author ZHUXF
 * @version 1.0
 */
public class LCPayRuleFactoryBLS {
private static Logger logger = Logger.getLogger(LCPayRuleFactoryBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public LCPayRuleFactoryBLS() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		logger.debug("Start LCPayRuleFactoryBLS Submit...");
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = saveLCPayRule(cInputData);
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			tReturn = deleteLCPayRule(cInputData);
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			tReturn = updateLCPayRule(cInputData);
		}
		if (tReturn)
			logger.debug(" sucessful");
		else
			logger.debug("Save failed");
		logger.debug("End LCPayRuleFactoryBLS Submit...");
		return tReturn;
	}

	/**
	 * 新增处理
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean saveLCPayRule(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCPayRuleFactoryDBSet tNewLCPayRuleFactoryDBSet = new LCPayRuleFactoryDBSet(
					conn);
			// LCPayRuleFactoryDBSet tOldLCPayRuleFactoryDBSet = new
			// LCPayRuleFactoryDBSet(conn);
			// 获得删除数据set和新增数据set
			tNewLCPayRuleFactoryDBSet.set((LCPayRuleFactorySet) mInputData
					.get(1));
			// tOldLCPayRuleFactoryDBSet.set((LCPayRuleFactorySet) mInputData.
			// get(3));
			// 删除旧有数据
			// if (!tOldLCPayRuleFactoryDBSet.delete())
			// {
			// @@错误处理
			// this.mErrors.copyAllErrors(tOldLCPayRuleFactoryDBSet.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "LCPayRuleFactoryBLS";
			// tError.functionName = "saveData";
			// tError.errorMessage = "数据保存失败!";
			// this.mErrors.addOneError(tError);
			// / conn.rollback();
			// conn.close();
			// return false;
			// }
			logger.debug("--- save grppolno:"
					+ tNewLCPayRuleFactoryDBSet.get(1).getGrpPolNo());
			// 插入新增数据
			if (!tNewLCPayRuleFactoryDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tNewLCPayRuleFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCPayRuleParamsDBSet tNewLCPayRuleParamsDBSet = new LCPayRuleParamsDBSet(
					conn);
			// LCPayRuleParamsDBSet tOldLCPayRuleParamDBSet = new
			// LCPayRuleParamsDBSet(conn);
			// 获得删除数据set和新增数据set
			tNewLCPayRuleParamsDBSet
					.set((LCPayRuleParamsSet) mInputData.get(2));
			// tOldLCPayRuleParamDBSet.set((LCPayRuleParamsSet)
			// mInputData.get(4));
			// 删除旧有数据
			// if (!tOldLCPayRuleParamDBSet.delete())
			// {
			// @@错误处理
			// this.mErrors.copyAllErrors(tOldLCPayRuleParamDBSet.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "LCPayRuleFactoryBLS";
			// // tError.functionName = "saveData";
			// tError.errorMessage = "数据保存失败!";
			// this.mErrors.addOneError(tError);
			// conn.rollback();
			// conn.close();
			// return false;
			// }
			// 插入新增数据
			if (!tNewLCPayRuleParamsDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tNewLCPayRuleParamsDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			tReturn = false;
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
		}
		return tReturn;
	}

	/**
	 * 删除处理
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean deleteLCPayRule(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Del...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Del 删除...");

			LCPayRuleFactoryDBSet tOldLCPayRuleFactoryDBSet = new LCPayRuleFactoryDBSet(
					conn);
			// 获得删除数据set
			tOldLCPayRuleFactoryDBSet.set((LCPayRuleFactorySet) mInputData
					.get(3));
			// 删除旧有数据
			if (!tOldLCPayRuleFactoryDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOldLCPayRuleFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCPayRuleParamsDBSet tOldLCPayRuleParamsDBSet = new LCPayRuleParamsDBSet(
					conn);
			// 获得删除数据set
			tOldLCPayRuleParamsDBSet
					.set((LCPayRuleParamsSet) mInputData.get(4));
			// 删除旧有数据
			if (!tOldLCPayRuleParamsDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOldLCPayRuleParamsDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			tReturn = false;
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
		}
		return tReturn;
	}

	/**
	 * 保存函数
	 */
	private boolean updateLCPayRule(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 修改...");
			LCPayRuleFactoryDBSet tLCPayRuleFactoryDBSet = new LCPayRuleFactoryDBSet(
					conn);
			tLCPayRuleFactoryDBSet.set((LCPayRuleFactorySet) mInputData
					.getObjectByObjectName("LCPayRuleFactorySet", 0));
			// 先执行删除操作
			ExeSQL tExeSQL = new ExeSQL(conn);
			String tSql = "delete from LCPayRuleFactory where GrpContNo='"
					+ tLCPayRuleFactoryDBSet.get(1).getGrpContNo()
					+ "' and RiskCode='"
					+ tLCPayRuleFactoryDBSet.get(1).getRiskCode()
					+ "' and PayRuleCode='"
					+ tLCPayRuleFactoryDBSet.get(1).getPayRuleCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tSql = "delete from LCPayRuleParams where GrpContNo='"
					+ tLCPayRuleFactoryDBSet.get(1).getGrpContNo()
					+ "' and RiskCode='"
					+ tLCPayRuleFactoryDBSet.get(1).getRiskCode()
					+ "' and PayRuleCode='"
					+ tLCPayRuleFactoryDBSet.get(1).getPayRuleCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// logger.debug("-------factoryset:"+tLCPayRuleFactoryDBSet.size());
			// logger.debug("-------grppolno:"+tLCPayRuleFactoryDBSet.get(1).getGrpPolNo());
			// 删除成功，才执行新增操作，实现修改目的
			if (!tLCPayRuleFactoryDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCPayRuleParamsDBSet tLCPayRuleParamsDBSet = new LCPayRuleParamsDBSet(
					conn);
			tLCPayRuleParamsDBSet.set((LCPayRuleParamsSet) mInputData
					.getObjectByObjectName("LCPayRuleParamsSet", 0));
			// logger.debug("-------params
			// set:"+tLCPayRuleParamsDBSet.size());
			// logger.debug("--grppolno:"+tLCPayRuleParamsDBSet.get(1).getGrpPolNo());
			if (!tLCPayRuleParamsDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPayRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPayRuleFactoryBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			tReturn = false;
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
		}
		return tReturn;
	}
}
