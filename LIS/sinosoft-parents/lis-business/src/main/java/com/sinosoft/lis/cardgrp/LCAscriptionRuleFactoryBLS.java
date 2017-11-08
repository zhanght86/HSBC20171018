/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LCAscriptionRuleFactoryDBSet;
import com.sinosoft.lis.vdb.LCAscriptionRuleParamsDBSet;
import com.sinosoft.lis.vschema.LCAscriptionRuleFactorySet;
import com.sinosoft.lis.vschema.LCAscriptionRuleParamsSet;
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
public class LCAscriptionRuleFactoryBLS {
private static Logger logger = Logger.getLogger(LCAscriptionRuleFactoryBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public LCAscriptionRuleFactoryBLS() {
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

		logger.debug("Start LCAscriptionRuleFactoryBLS Submit...");
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = saveLCAscriptionRule(cInputData);
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			tReturn = deleteLCAscriptionRule(cInputData);
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			tReturn = updateLCAscriptionRule(cInputData);
		}
		if (tReturn)
			logger.debug(" sucessful");
		else
			logger.debug("Save failed");
		logger.debug("End LCAscriptionRuleFactoryBLS Submit...");
		return tReturn;
	}

	/**
	 * 新增处理
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean saveLCAscriptionRule(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCAscriptionRuleFactoryDBSet tNewLCAscriptionRuleFactoryDBSet = new LCAscriptionRuleFactoryDBSet(
					conn);
			// LCAscriptionRuleFactoryDBSet tOldLCAscriptionRuleFactoryDBSet =
			// new
			// LCAscriptionRuleFactoryDBSet(conn);
			// 获得删除数据set和新增数据set
			tNewLCAscriptionRuleFactoryDBSet
					.set((LCAscriptionRuleFactorySet) mInputData.get(1));
			// tOldLCAscriptionRuleFactoryDBSet.set((LCAscriptionRuleFactorySet)
			// mInputData.
			// get(3));
			// 删除旧有数据
			// if (!tOldLCAscriptionRuleFactoryDBSet.delete())
			// {
			// @@错误处理
			// this.mErrors.copyAllErrors(tOldLCAscriptionRuleFactoryDBSet.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "LCAscriptionRuleFactoryBLS";
			// tError.functionName = "saveData";
			// tError.errorMessage = "数据保存失败!";
			// this.mErrors.addOneError(tError);
			// / conn.rollback();
			// conn.close();
			// return false;
			// }
			// 插入新增数据
			if (!tNewLCAscriptionRuleFactoryDBSet.insert()) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tNewLCAscriptionRuleFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCAscriptionRuleParamsDBSet tNewLCAscriptionRuleParamsDBSet = new LCAscriptionRuleParamsDBSet(
					conn);
			// LCAscriptionRuleParamsDBSet tOldLCAscriptionRuleParamDBSet = new
			// LCAscriptionRuleParamsDBSet(conn);
			// 获得删除数据set和新增数据set
			tNewLCAscriptionRuleParamsDBSet
					.set((LCAscriptionRuleParamsSet) mInputData.get(2));
			// tOldLCAscriptionRuleParamDBSet.set((LCAscriptionRuleParamsSet)
			// mInputData.get(4));
			// 删除旧有数据
			// if (!tOldLCAscriptionRuleParamDBSet.delete())
			// {
			// @@错误处理
			// this.mErrors.copyAllErrors(tOldLCAscriptionRuleParamDBSet.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "LCAscriptionRuleFactoryBLS";
			// // tError.functionName = "saveData";
			// tError.errorMessage = "数据保存失败!";
			// this.mErrors.addOneError(tError);
			// conn.rollback();
			// conn.close();
			// return false;
			// }
			// 插入新增数据
			if (!tNewLCAscriptionRuleParamsDBSet.insert()) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tNewLCAscriptionRuleParamsDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
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
			tError.moduleName = "LCAscriptionRuleFactoryBLS";
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
	private boolean deleteLCAscriptionRule(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Del...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Del 删除...");

			LCAscriptionRuleFactoryDBSet tOldLCAscriptionRuleFactoryDBSet = new LCAscriptionRuleFactoryDBSet(
					conn);
			// 获得删除数据set
			tOldLCAscriptionRuleFactoryDBSet
					.set((LCAscriptionRuleFactorySet) mInputData.get(3));
			logger.debug("---old ascriptiont set.size:"
					+ tOldLCAscriptionRuleFactoryDBSet.size());
			logger.debug("tOldLCAscriptionRuleFactoryDBSet:"
					+ tOldLCAscriptionRuleFactoryDBSet.encode());
			// 删除旧有数据
			if (!tOldLCAscriptionRuleFactoryDBSet.delete()) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tOldLCAscriptionRuleFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCAscriptionRuleParamsDBSet tOldLCAscriptionRuleParamsDBSet = new LCAscriptionRuleParamsDBSet(
					conn);
			// 获得删除数据set
			tOldLCAscriptionRuleParamsDBSet
					.set((LCAscriptionRuleParamsSet) mInputData.get(4));
			// 删除旧有数据
			if (!tOldLCAscriptionRuleParamsDBSet.delete()) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tOldLCAscriptionRuleParamsDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
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
			tError.moduleName = "LCAscriptionRuleFactoryBLS";
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
	private boolean updateLCAscriptionRule(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactoryBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 修改...");
			LCAscriptionRuleFactoryDBSet tLCAscriptionRuleFactoryDBSet = new LCAscriptionRuleFactoryDBSet(
					conn);
			tLCAscriptionRuleFactoryDBSet
					.set((LCAscriptionRuleFactorySet) mInputData
							.getObjectByObjectName(
									"LCAscriptionRuleFactorySet", 0));
			// 先执行删除操作
			ExeSQL tExeSQL = new ExeSQL(conn);
			String tSql = "delete from LCAscriptionRuleFactory where GrpContNo='"
					+ tLCAscriptionRuleFactoryDBSet.get(1).getGrpContNo()
					+ "' and RiskCode='"
					+ tLCAscriptionRuleFactoryDBSet.get(1).getRiskCode()
					+ "' and AscriptionRuleCode='"
					+ tLCAscriptionRuleFactoryDBSet.get(1)
							.getAscriptionRuleCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tSql = "delete from LCAscriptionRuleParams where GrpContNo='"
					+ tLCAscriptionRuleFactoryDBSet.get(1).getGrpContNo()
					+ "' and RiskCode='"
					+ tLCAscriptionRuleFactoryDBSet.get(1).getRiskCode()
					+ "' and AscriptionRuleCode='"
					+ tLCAscriptionRuleFactoryDBSet.get(1)
							.getAscriptionRuleCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// logger.debug("-------factoryset:"+tLCAscriptionRuleFactoryDBSet.size());
			// logger.debug("-------grppolno:"+tLCAscriptionRuleFactoryDBSet.get(1).getGrpPolNo());
			// 删除成功，才执行新增操作，实现修改目的
			if (!tLCAscriptionRuleFactoryDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCAscriptionRuleParamsDBSet tLCAscriptionRuleParamsDBSet = new LCAscriptionRuleParamsDBSet(
					conn);
			tLCAscriptionRuleParamsDBSet
					.set((LCAscriptionRuleParamsSet) mInputData
							.getObjectByObjectName("LCAscriptionRuleParamsSet",
									0));
			// logger.debug("-------params
			// set:"+tLCAscriptionRuleParamsDBSet.size());
			// logger.debug("--grppolno:"+tLCAscriptionRuleParamsDBSet.get(1).getGrpPolNo());
			if (!tLCAscriptionRuleParamsDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAscriptionRuleFactoryBLS";
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
			tError.moduleName = "LCAscriptionRuleFactoryBLS";
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
