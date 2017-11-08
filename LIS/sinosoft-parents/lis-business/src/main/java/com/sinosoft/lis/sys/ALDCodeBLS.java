/*
 * <p>ClassName: OLDCodeBLS </p>
 * <p>Description: LDCodeBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2003-06-21
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class ALDCodeBLS {
private static Logger logger = Logger.getLogger(ALDCodeBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public ALDCodeBLS() {
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

		logger.debug("Start LDCodeBLS Submit...");
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = saveLDCode(cInputData);
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			tReturn = deleteLDCode(cInputData);
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			tReturn = updateLDCode(cInputData);
		}
		if (tReturn) {
			logger.debug(" sucessful");
		} else {
			logger.debug("Save failed");
		}
		logger.debug("End LDCodeBLS Submit...");
		return tReturn;
	}

	/**
	 * 保存函数
	 */
	private boolean saveLDCode(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LDCodeDB tLDCodeDB = new LDCodeDB(conn);
			tLDCodeDB.setSchema((LDCodeSchema) mInputData
					.getObjectByObjectName("LDCodeSchema", 0));
			if (!tLDCodeDB.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDCodeBLS";
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
			tError.moduleName = "LDCodeBLS";
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
	private boolean deleteLDCode(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LDCodeDB tLDCodeDB = new LDCodeDB(conn);
			tLDCodeDB.setSchema((LDCodeSchema) mInputData
					.getObjectByObjectName("LDCodeSchema", 0));
			if (!tLDCodeDB.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDCodeBLS";
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
			tError.moduleName = "LDCodeBLS";
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
	private boolean updateLDCode(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "LDCodeBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LDCodeDB tLDCodeDB = new LDCodeDB(conn);
			tLDCodeDB.setSchema((LDCodeSchema) mInputData
					.getObjectByObjectName("LDCodeSchema", 0));
			if (!tLDCodeDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDCodeBLS";
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
			tError.moduleName = "LDCodeBLS";
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
