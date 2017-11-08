/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.vdb.LCContPlanDBSet;
import com.sinosoft.lis.vdb.LCContPlanDutyParamDBSet;
import com.sinosoft.lis.vdb.LCContPlanRiskDBSet;
import com.sinosoft.lis.vdb.LCGrpPolDBSet;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * 保障计划数据提交类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据传入的操作类型，进行新增、删除、修改操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class LCContPlanBLS {
private static Logger logger = Logger.getLogger(LCContPlanBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public LCContPlanBLS() {
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

		logger.debug("Start LCContPlanBLS Submit...");
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = saveLCContPlan(cInputData);
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			tReturn = deleteLCContPlan(cInputData);
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			tReturn = updateLCContPlan(cInputData);
		}
		if (tReturn) {
			logger.debug(" sucessful");
		} else {
			logger.debug("Save failed");
		}
		logger.debug("End LCContPlanBLS Submit...");
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
			tError.moduleName = "LCContPlanBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCContPlanDBSet tLCContPlanDBSet = new LCContPlanDBSet(conn);
			tLCContPlanDBSet.set((LCContPlanSet) mInputData
					.getObjectByObjectName("LCContPlanSet", 0));

			LCContPlanDB tLCContPlanDB = new LCContPlanDB(conn);
			tLCContPlanDB.setContPlanCode(tLCContPlanDBSet.get(1)
					.getContPlanCode());
			tLCContPlanDB.setGrpContNo(tLCContPlanDBSet.get(1).getGrpContNo());
			LCContPlanSet tLCContPlanSet = tLCContPlanDB.query();
			if (tLCContPlanSet.size() == 0) {
				if (!tLCContPlanDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LCContPlanBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "数据保存失败1!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			LCContPlanRiskDBSet tLCContPlanRiskDBSet = new LCContPlanRiskDBSet(
					conn);
			tLCContPlanRiskDBSet.set((LCContPlanRiskSet) mInputData
					.getObjectByObjectName("LCContPlanRiskSet", 0));
			if (!tLCContPlanRiskDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败2!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanDutyParamDBSet tLCContPlanDutyParamDBSet = new LCContPlanDutyParamDBSet(
					conn);
			tLCContPlanDutyParamDBSet.set((LCContPlanDutyParamSet) mInputData
					.getObjectByObjectName("LCContPlanDutyParamSet", 0));
			if (!tLCContPlanDutyParamDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败3!";
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
			tError.moduleName = "LCContPlanBLS";
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
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCContPlanDBSet tLCContPlanDBSet = new LCContPlanDBSet(conn);
			tLCContPlanDBSet.set((LCContPlanSet) mInputData
					.getObjectByObjectName("LCContPlanSet", 0));
			if (!tLCContPlanDBSet.delete()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败4!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanRiskDBSet tLCContPlanRiskDBSet = new LCContPlanRiskDBSet(
					conn);
			tLCContPlanRiskDBSet.set((LCContPlanRiskSet) mInputData
					.getObjectByObjectName("LCContPlanRiskSet", 0));
			if (!tLCContPlanRiskDBSet.delete()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败5!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanDutyParamDBSet tLCContPlanDutyParamDBSet = new LCContPlanDutyParamDBSet(
					conn);
			tLCContPlanDutyParamDBSet.set((LCContPlanDutyParamSet) mInputData
					.getObjectByObjectName("LCContPlanDutyParamSet", 0));
			if (!tLCContPlanDutyParamDBSet.delete()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败6!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			LCGrpPolDBSet tLCGrpPolDBSet = new LCGrpPolDBSet(conn);
			tLCGrpPolDBSet.set((LCGrpPolSet) mInputData.getObjectByObjectName(
					"LCGrpPolSet", 0));
			if (!tLCGrpPolDBSet.delete()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败6!";
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
			tError.moduleName = "LCContPlanBLS";
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
	 * 修改处理
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
			tError.moduleName = "LCContPlanBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 修改...");
			LCContPlanDBSet tLCContPlanDBSet = new LCContPlanDBSet(conn);
			tLCContPlanDBSet.set((LCContPlanSet) mInputData
					.getObjectByObjectName("LCContPlanSet", 0));
			// 先执行删除操作
			ExeSQL tExeSQL = new ExeSQL(conn);
			String tSql = "delete from LCContPlan where GrpContNo='"
					+ tLCContPlanDBSet.get(1).getGrpContNo()
					+ "' and ContPlanCode='"
					+ tLCContPlanDBSet.get(1).getContPlanCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败7!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			tSql = "delete from LCContPlanRisk where GrpContNo='"
					+ tLCContPlanDBSet.get(1).getGrpContNo()
					+ "' and ContPlanCode='"
					+ tLCContPlanDBSet.get(1).getContPlanCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败8!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			tSql = "delete from LCContPlanDutyParam where GrpContNo='"
					+ tLCContPlanDBSet.get(1).getGrpContNo()
					+ "' and ContPlanCode='"
					+ tLCContPlanDBSet.get(1).getContPlanCode() + "'";
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败9!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 删除成功，才执行新增操作，实现修改目的
			if (!tLCContPlanDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败10!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanRiskDBSet tLCContPlanRiskDBSet = new LCContPlanRiskDBSet(
					conn);
			tLCContPlanRiskDBSet.set((LCContPlanRiskSet) mInputData
					.getObjectByObjectName("LCContPlanRiskSet", 0));
			if (!tLCContPlanRiskDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败11!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LCContPlanDutyParamDBSet tLCContPlanDutyParamDBSet = new LCContPlanDutyParamDBSet(
					conn);
			tLCContPlanDutyParamDBSet.set((LCContPlanDutyParamSet) mInputData
					.getObjectByObjectName("LCContPlanDutyParamSet", 0));
			if (!tLCContPlanDutyParamDBSet.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCContPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败12!";
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
			tError.moduleName = "LCContPlanBLS";
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
