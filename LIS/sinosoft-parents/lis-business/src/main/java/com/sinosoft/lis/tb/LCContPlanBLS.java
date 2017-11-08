/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.vdb.LCContPlanDBSet;
import com.sinosoft.lis.vdb.LCContPlanDutyParamDBSet;
import com.sinosoft.lis.vdb.LCContPlanRiskDBSet;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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
		LCContPlanSet mLCContPlanSet = new LCContPlanSet();
		LCContPlanRiskSet mLCContPlanRiskSet = new LCContPlanRiskSet();
		LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();

		mLCContPlanSet = (LCContPlanSet) mInputData.getObjectByObjectName(
				"LCContPlanSet", 0);
		mLCContPlanRiskSet = (LCContPlanRiskSet) mInputData
				.getObjectByObjectName("LCContPlanRiskSet", 0);
		mLCContPlanDutyParamSet = (LCContPlanDutyParamSet) mInputData
				.getObjectByObjectName("LCContPlanDutyParamSet", 0);

		logger.debug("mLCContPlanSet.size()=" + mLCContPlanSet.size());
		logger.debug("mLCContPlanRiskSet.size()="
				+ mLCContPlanRiskSet.size());
		logger.debug("mLCContPlanDutyParamSet.size()="
				+ mLCContPlanDutyParamSet.size());

		for (int i = 1; i <= mLCContPlanSet.size(); i++) {
			boolean tDel = true;

			// ------------------------------------------Beg
			// add by:chenrong
			// add date:2006.6.14
			// 如果默认险种计划还存在其它险种信息，则不删除默认险种计划
			if (mLCContPlanSet.get(i).getContPlanCode().compareTo("00") == 0
					&& this.mOperate.compareTo("DELETE||MAIN") == 0) {
				String tSql = "select count(1) from LCContPlanRisk a where a.GrpContNo='"
						+ "?GrpContNo?"
						+ "'"
						+ " and a.RiskCode not in ('"
						+ "?RiskCode?" + "')";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("GrpContNo", mLCContPlanSet.get(i).getGrpContNo());
				sqlbv.put("RiskCode", mLCContPlanDutyParamSet.get(1).getRiskCode());
				ExeSQL texeSQL = new ExeSQL();
				SSRS ssrs = texeSQL.execSQL(sqlbv);
				if (ssrs.GetText(1, 1).compareTo("0") > 0) {
					tDel = false;
				}
			}
			if (tDel) {
				String sql = "delete from lccontplan where grpcontno='"
						+ "?grpcontno?"
						+ "' and contplancode='"
						+ "?RiskCode?"
						+ "' and plantype='"
						+ "?plantype?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("grpcontno", mLCContPlanSet.get(i).getGrpContNo());
				sqlbv1.put("RiskCode", mLCContPlanSet.get(i).getContPlanCode());
				sqlbv1.put("plantype", mLCContPlanSet.get(i).getPlanType());
				ExeSQL tExeSQL = new ExeSQL();
				tExeSQL.execUpdateSQL(sqlbv1);
			}
			// -----------------------------------------End
		}

		for (int j = 1; j <= mLCContPlanRiskSet.size(); j++) {
			String sql = "DELETE FROM LCContPlanRisk WHERE  ProposalGrpContNo ='"
					+ "?ProposalGrpContNo?"
					+ "' AND MainRiskCode = '"
					+ "?MainRiskCode?"
					+ "' AND RiskCode = '"
					+ "?RiskCode?"
					+ "' AND ContPlanCode = '"
					+ "?ContPlanCode?"
					+ "' AND PlanType ='"
					+ "?PlanType?" + "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("ProposalGrpContNo", mLCContPlanRiskSet.get(j).getProposalGrpContNo());
			sqlbv2.put("MainRiskCode", mLCContPlanRiskSet.get(j).getMainRiskCode());
			sqlbv2.put("RiskCode", mLCContPlanRiskSet.get(j).getRiskCode());
			sqlbv2.put("ContPlanCode", mLCContPlanRiskSet.get(j).getContPlanCode());
			sqlbv2.put("PlanType", mLCContPlanRiskSet.get(j).getPlanType());
			ExeSQL tExeSQL = new ExeSQL();
			tExeSQL.execUpdateSQL(sqlbv2);
		}

		for (int m = 1; m <= mLCContPlanDutyParamSet.size(); m++) {
			String sql = "DELETE FROM LCContPlanDutyParam WHERE  GrpContNo ='"
					+ "?GrpContNo?"
					+ "' AND MainRiskCode ='"
					+ "?MainRiskCode?"
					+ "' AND RiskCode ='"
					+ "?RiskCode?"
					+ "' AND ContPlanCode = '"
					+ "?ContPlanCode?"
					+ "' AND DutyCode = '"
					+ "?DutyCode?"
					+ "' AND CalFactor = '"
					+ "?CalFactor?"
					+ "' AND PlanType = '"
					+ "?PlanType?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("GrpContNo", mLCContPlanDutyParamSet.get(m).getGrpContNo());
			sqlbv3.put("MainRiskCode", mLCContPlanDutyParamSet.get(m).getMainRiskCode());
			sqlbv3.put("RiskCode", mLCContPlanDutyParamSet.get(m).getRiskCode());
			sqlbv3.put("ContPlanCode", mLCContPlanDutyParamSet.get(m).getContPlanCode());
			sqlbv3.put("DutyCode", mLCContPlanDutyParamSet.get(m).getDutyCode());
			sqlbv3.put("CalFactor", mLCContPlanDutyParamSet.get(m).getCalFactor());
			sqlbv3.put("PlanType", mLCContPlanDutyParamSet.get(m).getPlanType());
			ExeSQL tExeSQL = new ExeSQL();
			tExeSQL.execUpdateSQL(sqlbv3);
		}

		/*
		 * logger.debug("Start Save..."); Connection conn; conn = null;
		 * conn = DBConnPool.getConnection(); if (conn == null) { // @@错误处理
		 * CError tError = new CError(); tError.moduleName = "LCContPlanBLS";
		 * tError.functionName = "saveData"; tError.errorMessage = "数据库连接失败!";
		 * this.mErrors.addOneError(tError); return false; } try {
		 * conn.setAutoCommit(false); logger.debug("Start 保存...");
		 * LCContPlanDBSet tLCContPlanDBSet = new LCContPlanDBSet(conn);
		 * tLCContPlanDBSet.set((LCContPlanSet) mInputData.
		 * getObjectByObjectName( "LCContPlanSet", 0)); if
		 * (!tLCContPlanDBSet.delete()) { // @@错误处理 CError tError = new
		 * CError(); tError.moduleName = "LCContPlanBLS"; tError.functionName =
		 * "saveData"; tError.errorMessage = "数据保存失败4!";
		 * this.mErrors.addOneError(tError); conn.rollback(); conn.close();
		 * return false; }
		 * 
		 * LCContPlanRiskDBSet tLCContPlanRiskDBSet = new LCContPlanRiskDBSet(
		 * conn); tLCContPlanRiskDBSet.set((LCContPlanRiskSet) mInputData.
		 * getObjectByObjectName( "LCContPlanRiskSet", 0)); if
		 * (!tLCContPlanRiskDBSet.delete()) { // @@错误处理 CError tError = new
		 * CError(); tError.moduleName = "LCContPlanBLS"; tError.functionName =
		 * "saveData"; tError.errorMessage = "数据保存失败5!";
		 * this.mErrors.addOneError(tError); conn.rollback(); conn.close();
		 * return false; }
		 * 
		 * LCContPlanDutyParamDBSet tLCContPlanDutyParamDBSet = new
		 * LCContPlanDutyParamDBSet(conn);
		 * tLCContPlanDutyParamDBSet.set((LCContPlanDutyParamSet) mInputData.
		 * getObjectByObjectName( "LCContPlanDutyParamSet", 0)); if
		 * (!tLCContPlanDutyParamDBSet.delete()) { // @@错误处理 CError tError = new
		 * CError(); tError.moduleName = "LCContPlanBLS"; tError.functionName =
		 * "saveData"; tError.errorMessage = "数据保存失败6!";
		 * this.mErrors.addOneError(tError); conn.rollback(); conn.close();
		 * return false; } conn.commit(); conn.close(); } catch (Exception ex) { //
		 * @@错误处理 CError tError = new CError(); tError.moduleName =
		 * "LCContPlanBLS"; tError.functionName = "submitData";
		 * tError.errorMessage = ex.toString();
		 * this.mErrors.addOneError(tError); tReturn = false; try {
		 * conn.rollback(); conn.close(); } catch (Exception e) {} }
		 */
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
			LCContPlanRiskDBSet tLCContPlanRiskDBSet = new LCContPlanRiskDBSet(
					conn);
			tLCContPlanRiskDBSet.set((LCContPlanRiskSet) mInputData
					.getObjectByObjectName("LCContPlanRiskSet", 0));

			// 先执行删除操作
			ExeSQL tExeSQL = new ExeSQL(conn);
			String tSql = "delete from LCContPlan where GrpContNo='"
					+ "?GrpContNo?"
					+ "' and ContPlanCode='"
					+ "?ContPlanCode?" + "'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("GrpContNo", tLCContPlanDBSet.get(1).getGrpContNo());
			sqlbv4.put("ContPlanCode", tLCContPlanDBSet.get(1).getContPlanCode());
			if (!tExeSQL.execUpdateSQL(sqlbv4)) {
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
			if (tLCContPlanDBSet.get(1).getContPlanCode().equals("00")) {
				tSql = "delete from LCContPlanRisk where GrpContNo='"
						+ "?GrpContNo?"
						+ "' and ContPlanCode='"
						+ "?ContPlanCode?"
						+ "' and RiskCode='"
						+ "?RiskCode?" + "'";
			} else {
				tSql = "delete from LCContPlanRisk where GrpContNo='"
						+ "?GrpContNo?"
						+ "' and ContPlanCode='"
						+ "?ContPlanCode?" + "'";
			}
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tSql);
			sqlbv5.put("GrpContNo", tLCContPlanDBSet.get(1).getGrpContNo());
			sqlbv5.put("ContPlanCode", tLCContPlanDBSet.get(1).getContPlanCode());
			sqlbv5.put("RiskCode", tLCContPlanRiskDBSet.get(1).getRiskCode());
			if (!tExeSQL.execUpdateSQL(sqlbv5)) {
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
			if (tLCContPlanDBSet.get(1).getContPlanCode().equals("00")) {
				tSql = "delete from LCContPlanDutyParam where GrpContNo='"
						+ "?GrpContNo?"
						+ "' and ContPlanCode='"
						+ "?ContPlanCode?"
						+ "' and RiskCode='"
						+ "?RiskCode?" + "'";
			} else {
				tSql = "delete from LCContPlanDutyParam where GrpContNo='"
						+ "?GrpContNo?"
						+ "' and ContPlanCode='"
						+ "?ContPlanCode?" + "'";
			}
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tSql);
			sqlbv6.put("GrpContNo", tLCContPlanDBSet.get(1).getGrpContNo());
			sqlbv6.put("ContPlanCode", tLCContPlanDBSet.get(1).getContPlanCode());
			sqlbv6.put("RiskCode", tLCContPlanRiskDBSet.get(1).getRiskCode());
			if (!tExeSQL.execUpdateSQL(sqlbv6)) {
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
