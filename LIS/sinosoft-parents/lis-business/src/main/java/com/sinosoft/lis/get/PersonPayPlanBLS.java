package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LJSGetDBSet;
import com.sinosoft.lis.vdb.LJSGetDrawDBSet;
import com.sinosoft.lis.vdb.LOPRTManagerDBSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单催付处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ck
 * @version 1.0
 */
public class PersonPayPlanBLS {
private static Logger logger = Logger.getLogger(PersonPayPlanBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public PersonPayPlanBLS() {
	}

	public static void main(String[] args) {
		PersonPayPlanBLS tPersonPayPlanBLS = new PersonPayPlanBLS();
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		logger.debug("Start LFGetPay BLS Submit...");

		if (!savePersonPayPlan(cInputData))
			return false;

		return true;
	}

	/**
	 * 生存领取的数据的保存函数
	 */
	private boolean savePersonPayPlan(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start PersonPayPlanBLS Save...");
		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonPayPlanBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 开始生成计划...");

			LJSGetDBSet tLJSGetDBSet = new LJSGetDBSet(conn);
			tLJSGetDBSet.set((LJSGetSet) mInputData.getObjectByObjectName(
					"LJSGetSet", 0));
			logger.debug("Get LJSGet");
			logger.debug("LJSGetDBSet size :" + tLJSGetDBSet.size());
			if (!tLJSGetDBSet.insert()) {
				// @@错误处理
				logger.debug("======ERROR==22=" + tLJSGetDBSet.mErrors);
				this.mErrors.copyAllErrors(tLJSGetDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "PersonPayPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "应付数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LJSGetDrawDBSet tLJSGetDrawDBSet = new LJSGetDrawDBSet(conn);
			tLJSGetDrawDBSet.set((LJSGetDrawSet) mInputData
					.getObjectByObjectName("LJSGetDrawSet", 0));
			logger.debug("LJSGetDrawDBSet size :"
					+ tLJSGetDrawDBSet.size());
			if (!tLJSGetDrawDBSet.insert()) {
				// @@错误处理
				logger.debug("======ERROR==33="
						+ tLJSGetDrawDBSet.mErrors);
				this.mErrors.copyAllErrors(tLJSGetDrawDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "PersonPayPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "应付明细数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// tLOPRTManagerSet
			LOPRTManagerDBSet tLOPRTManagerDBSet = new LOPRTManagerDBSet(conn);
			tLOPRTManagerDBSet.set((LOPRTManagerSet) mInputData
					.getObjectByObjectName("LOPRTManagerSet", 0));
			logger.debug("tLOPRTManagerDBSet size :"
					+ tLOPRTManagerDBSet.size());
			if (!tLOPRTManagerDBSet.insert()) {
				// @@错误处理
				logger.debug("======ERROR==33="
						+ tLOPRTManagerDBSet.mErrors);
				this.mErrors.copyAllErrors(tLOPRTManagerDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "PersonPayPlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "打印管理表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("8888");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PersonPayPlanBLS";
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
