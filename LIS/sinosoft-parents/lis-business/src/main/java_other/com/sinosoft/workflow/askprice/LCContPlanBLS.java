/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.workflow.askprice;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LQBenefitDB;
import com.sinosoft.lis.vdb.LQBenefitDBSet;
import com.sinosoft.lis.vdb.LQBenefitDutyParamDBSet;
import com.sinosoft.lis.vdb.LQBenefitToRiskDBSet;
import com.sinosoft.lis.vdb.AskPriceRadioDBSet;
import com.sinosoft.lis.vschema.LQBenefitDutyParamSet;
import com.sinosoft.lis.vschema.LQBenefitToRiskSet;
import com.sinosoft.lis.vschema.LQBenefitSet;
import com.sinosoft.lis.vschema.AskPriceRadioSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
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
			LQBenefitDBSet tLQBenefitDBSet = new LQBenefitDBSet(conn);
			tLQBenefitDBSet.set((LQBenefitSet) mInputData
					.getObjectByObjectName("LQBenefitSet", 0));

			LQBenefitDB tLQBenefitDB = new LQBenefitDB(conn);
			tLQBenefitDB.setContPlanCode(tLQBenefitDBSet.get(1)
					.getContPlanCode());
			tLQBenefitDB.setAskPriceNo(tLQBenefitDBSet.get(1).getAskPriceNo());
			tLQBenefitDB.setAskNo(tLQBenefitDBSet.get(1).getAskNo());
			LQBenefitSet tLQBenefitSet = tLQBenefitDB.query();
			if (tLQBenefitSet.size() == 0) {
				if (!tLQBenefitDBSet.insert()) {
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

			LQBenefitToRiskDBSet tLQBenefitToRiskDBSet = new LQBenefitToRiskDBSet(
					conn);
			tLQBenefitToRiskDBSet.set((LQBenefitToRiskSet) mInputData
					.getObjectByObjectName("LQBenefitToRiskSet", 0));
			if (!tLQBenefitToRiskDBSet.insert()) {
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

			LQBenefitDutyParamDBSet tLQBenefitDutyParamDBSet = new LQBenefitDutyParamDBSet(
					conn);
			tLQBenefitDutyParamDBSet.set((LQBenefitDutyParamSet) mInputData
					.getObjectByObjectName("LQBenefitDutyParamSet", 0));
			if (!tLQBenefitDutyParamDBSet.insert()) {
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
			LQBenefitDBSet tLQBenefitDBSet = new LQBenefitDBSet(conn);
			tLQBenefitDBSet.set((LQBenefitSet) mInputData
					.getObjectByObjectName("LQBenefitSet", 0));
			if (!tLQBenefitDBSet.delete()) {
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

			LQBenefitToRiskDBSet tLQBenefitToRiskDBSet = new LQBenefitToRiskDBSet(
					conn);
			tLQBenefitToRiskDBSet.set((LQBenefitToRiskSet) mInputData
					.getObjectByObjectName("LQBenefitToRiskSet", 0));
			if (!tLQBenefitToRiskDBSet.delete()) {
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

			LQBenefitDutyParamDBSet tLQBenefitDutyParamDBSet = new LQBenefitDutyParamDBSet(
					conn);
			tLQBenefitDutyParamDBSet.set((LQBenefitDutyParamSet) mInputData
					.getObjectByObjectName("LQBenefitDutyParamSet", 0));
			if (!tLQBenefitDutyParamDBSet.delete()) {
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
			AskPriceRadioDBSet tAskPriceRadioDBSet = new AskPriceRadioDBSet(conn);
			tAskPriceRadioDBSet.set((AskPriceRadioSet) mInputData.getObjectByObjectName(
					"AskPriceRadioSet", 0));
			if (!tAskPriceRadioDBSet.delete()) {
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
			LQBenefitDBSet tLQBenefitDBSet = new LQBenefitDBSet(conn);
			tLQBenefitDBSet.set((LQBenefitSet) mInputData
					.getObjectByObjectName("LQBenefitSet", 0));
			// 先执行删除操作
			ExeSQL tExeSQL = new ExeSQL(conn);
			String tSql = "delete from LQBenefit where askpriceno='"
					+ "?askpriceno?"
					+ "' and askno ='"+"?askno?" 
					+ "' and ContPlanCode='"
					+ "?ContPlanCode?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("askpriceno",tLQBenefitDBSet.get(1).getAskPriceNo());
			sqlbv1.put("askno",tLQBenefitDBSet.get(1).getAskNo());
			sqlbv1.put("ContPlanCode",tLQBenefitDBSet.get(1).getContPlanCode());
			if (!tExeSQL.execUpdateSQL(sqlbv1)) {
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
			tSql = "delete from lqbenefittorisk where askpriceno='"
					+ "?askpriceno?"
					+ "' and askno ='"+"?askno?" 
					+ "' and ContPlanCode='"
					+ "?ContPlanCode?"+ "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("askpriceno",tLQBenefitDBSet.get(1).getAskPriceNo());
			sqlbv2.put("askno",tLQBenefitDBSet.get(1).getAskNo());
			sqlbv2.put("ContPlanCode",tLQBenefitDBSet.get(1).getContPlanCode());
			if (!tExeSQL.execUpdateSQL(sqlbv2)) {
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
			tSql = "delete from lqbenefitdutyparam where askpriceno='"
					+ "?askpriceno?"
					+ "' and askno ='"+"?askno?"+"'"
					+ " and ContPlanCode='"
					+ "?ContPlanCode?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("askpriceno",tLQBenefitDBSet.get(1).getAskPriceNo());
			sqlbv3.put("askno",tLQBenefitDBSet.get(1).getAskNo());
			sqlbv3.put("ContPlanCode",tLQBenefitDBSet.get(1).getContPlanCode());
			if (!tExeSQL.execUpdateSQL(sqlbv3)) {
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
			if (!tLQBenefitDBSet.insert()) {
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

			LQBenefitToRiskDBSet tLQBenefitToRiskDBSet = new LQBenefitToRiskDBSet(
					conn);
			tLQBenefitToRiskDBSet.set((LQBenefitToRiskSet) mInputData
					.getObjectByObjectName("LQBenefitToRiskSet", 0));
			if (!tLQBenefitToRiskDBSet.insert()) {
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

			LQBenefitDutyParamDBSet tLQBenefitDutyParamDBSet = new LQBenefitDutyParamDBSet(
					conn);
			tLQBenefitDutyParamDBSet.set((LQBenefitDutyParamSet) mInputData
					.getObjectByObjectName("LQBenefitDutyParamSet", 0));
			if (!tLQBenefitDutyParamDBSet.insert()) {
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
