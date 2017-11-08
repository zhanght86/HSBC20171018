package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:投保功能类（界面输入）
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class ChangePlanBLS {
private static Logger logger = Logger.getLogger(ChangePlanBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public ChangePlanBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 信息保存
		tReturn = save(cInputData);

		if (tReturn) {
			logger.debug("Save sucessful");
		} else {
			logger.debug("Save failed");
		}

		return tReturn;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");

		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			// LCAddPol表
			logger.debug("Start LCAddPol表...");
			LCPolDB tLCPolDB = new LCPolDB(conn);
			tLCPolDB.setSchema((LCPolSchema) mInputData.getObjectByObjectName(
					"LCPolSchema", 0));

			if (!tLCPolDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "ChangePlanBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCAddPol表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			tReturn = false;
		}
		return tReturn;
	}

	public static void main(String[] args) {
		ChangePlanBLS ChangePlanBLS1 = new ChangePlanBLS();
	}
}
