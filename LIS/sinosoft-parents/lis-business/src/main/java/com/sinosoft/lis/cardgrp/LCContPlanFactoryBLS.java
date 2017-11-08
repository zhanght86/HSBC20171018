/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.vdb.LCContPlanFactoryDBSet;
import com.sinosoft.lis.vdb.LCContPlanParamDBSet;
import com.sinosoft.lis.vschema.LCContPlanFactorySet;
import com.sinosoft.lis.vschema.LCContPlanParamSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
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
public class LCContPlanFactoryBLS {
private static Logger logger = Logger.getLogger(LCContPlanFactoryBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public LCContPlanFactoryBLS() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		logger.debug("Start LCContPlanFactoryBLS Submit...");
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = saveLCContPlan(cInputData);
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			tReturn = deleteLCContPlan(cInputData);
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			tReturn = updateLCContPlan(cInputData);
		}
		if (tReturn)
			logger.debug(" sucessful");
		else
			logger.debug("Save failed");
		logger.debug("End LCContPlanFactoryBLS Submit...");
		return tReturn;
	}

	/**
	 * 新增处理
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean saveLCContPlan(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCContPlanFactoryDBSet tNewLCContPlanFactoryDBSet = new LCContPlanFactoryDBSet(
					conn);
			LCContPlanFactoryDBSet tOldLCContPlanFactoryDBSet = new LCContPlanFactoryDBSet(
					conn);
			// 获得删除数据set和新增数据set
			tNewLCContPlanFactoryDBSet.set((LCContPlanFactorySet) mInputData
					.get(1));
			tOldLCContPlanFactoryDBSet.set((LCContPlanFactorySet) mInputData
					.get(3));
			// 删除旧有数据
			if (!tOldLCContPlanFactoryDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOldLCContPlanFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			// 插入新增数据
			if (!tNewLCContPlanFactoryDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tNewLCContPlanFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanParamDBSet tNewLCContPlanParamDBSet = new LCContPlanParamDBSet(
					conn);
			LCContPlanParamDBSet tOldLCContPlanParamDBSet = new LCContPlanParamDBSet(
					conn);
			// 获得删除数据set和新增数据set
			tNewLCContPlanParamDBSet
					.set((LCContPlanParamSet) mInputData.get(2));
			tOldLCContPlanParamDBSet
					.set((LCContPlanParamSet) mInputData.get(4));
			// 删除旧有数据
			if (!tOldLCContPlanParamDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOldLCContPlanParamDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			// 插入新增数据
			if (!tNewLCContPlanParamDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tNewLCContPlanParamDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
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
			tError.moduleName = "LCContPlanFactoryBLS";
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
	private boolean deleteLCContPlan(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Del...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Del 删除...");

			LCContPlanFactoryDBSet tOldLCContPlanFactoryDBSet = new LCContPlanFactoryDBSet(
					conn);
			// 获得删除数据set
			tOldLCContPlanFactoryDBSet.set((LCContPlanFactorySet) mInputData
					.get(3));
			// 删除旧有数据
			if (!tOldLCContPlanFactoryDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOldLCContPlanFactoryDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanParamDBSet tOldLCContPlanParamDBSet = new LCContPlanParamDBSet(
					conn);
			// 获得删除数据set
			tOldLCContPlanParamDBSet
					.set((LCContPlanParamSet) mInputData.get(4));
			// 删除旧有数据
			if (!tOldLCContPlanParamDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tOldLCContPlanParamDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
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
			tError.moduleName = "LCContPlanFactoryBLS";
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
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean updateLCContPlan(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "LCContPlanFactoryBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCContPlanDB tLCContPlanDB = new LCContPlanDB(conn);
			tLCContPlanDB.setSchema((LCContPlanSchema) mInputData
					.getObjectByObjectName("LCContPlanSchema", 0));
			if (!tLCContPlanDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCContPlanDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCContPlanFactoryBLS";
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
			tError.moduleName = "LCContPlanFactoryBLS";
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
