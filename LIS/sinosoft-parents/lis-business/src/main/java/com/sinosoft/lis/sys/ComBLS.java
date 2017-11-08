/*
 * <p>ClassName: ComBLS </p>
 * <p>Description: LDComBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class ComBLS {
private static Logger logger = Logger.getLogger(ComBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 传输数据类
	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public ComBLS() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();
		logger.debug("Start LDComBLS Submit...");
		if (this.mOperate.equals("INSERT||COM")) {
			if (!saveLDCom()) {
				logger.debug("Insert failed");
			}
			logger.debug("End LDComBLS Submit...");
			return false;
		}
		if (this.mOperate.equals("DELETE||COM")) {
			if (!deleteLDCom()) {
				logger.debug("delete failed");
			}
			logger.debug("End LDComBLS Submit...");
			return false;
		}
		if (this.mOperate.equals("UPDATE||COM")) {
			if (!updateLDCom()) {
				logger.debug("update failed");
			}
			logger.debug("End LDComBLS Submit...");
			return false;
		}
		logger.debug(" sucessful");

		return true;
	}

	/**
	 * 保存函数
	 */
	private boolean saveLDCom() {
		LDComSchema tLDComSchema = new LDComSchema();
		tLDComSchema = (LDComSchema) mInputData.getObjectByObjectName(
				"LDComSchema", 0);
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LDComDB tLDComDB = new LDComDB(conn);
			tLDComDB.setSchema(tLDComSchema);
			if (!tLDComDB.insert()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LDComBLS";
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
			tError.moduleName = "LDComBLS";
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

	/**
	 * 保存函数
	 */
	private boolean deleteLDCom() {
		LDComSchema tLDComSchema = new LDComSchema();
		tLDComSchema = (LDComSchema) mInputData.getObjectByObjectName(
				"LDComSchema", 0);
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LDComDB tLDComDB = new LDComDB(conn);
			tLDComDB.setSchema(tLDComSchema);
			if (!tLDComDB.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDComDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDComBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据删除失败!";
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
			tError.moduleName = "LDComBLS";
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

	/**
	 * 保存函数
	 */
	private boolean updateLDCom() {
		LDComSchema tLDComSchema = new LDComSchema();
		tLDComSchema = (LDComSchema) mInputData.getObjectByObjectName(
				"LDComSchema", 0);
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "LDComBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LDComDB tLDComDB = new LDComDB(conn);
			tLDComDB.setSchema(tLDComSchema);
			if (!tLDComDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDComDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDComBLS";
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
			tError.moduleName = "LDComBLS";
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
