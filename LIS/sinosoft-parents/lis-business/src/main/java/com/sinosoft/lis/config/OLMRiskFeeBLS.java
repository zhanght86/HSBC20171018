/*
 * <p>ClassName: OLMRiskFeeBLS </p>
 * <p>Description: OLMRiskFeeBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-03-22 16:52:11
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class OLMRiskFeeBLS {
private static Logger logger = Logger.getLogger(OLMRiskFeeBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 传输数据类
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	public OLMRiskFeeBLS() {
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
		if (this.mOperate.equals("INSERT||MAIN")) {
			if (!saveLMRiskFee())
				return false;
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			if (!deleteLMRiskFee())
				return false;
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			if (!updateLMRiskFee())
				return false;
		}
		return true;
	}

	/**
	 * 保存函数
	 */
	private boolean saveLMRiskFee() {
		LMRiskFeeSchema tLMRiskFeeSchema = new LMRiskFeeSchema();
		tLMRiskFeeSchema = (LMRiskFeeSchema) mInputData.getObjectByObjectName(
				"LMRiskFeeSchema", 0);
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLMRiskFeeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB(conn);
			tLMRiskFeeDB.setSchema(tLMRiskFeeSchema);
			if (!tLMRiskFeeDB.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLMRiskFeeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "OLMRiskFeeBLS";
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
			tError.moduleName = "OLMRiskFeeBLS";
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
	private boolean deleteLMRiskFee() {
		LMRiskFeeSchema tLMRiskFeeSchema = new LMRiskFeeSchema();
		tLMRiskFeeSchema = (LMRiskFeeSchema) mInputData.getObjectByObjectName(
				"LMRiskFeeSchema", 0);
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLMRiskFeeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB(conn);
			tLMRiskFeeDB.setSchema(tLMRiskFeeSchema);
			if (!tLMRiskFeeDB.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLMRiskFeeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "OLMRiskFeeBLS";
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
			tError.moduleName = "OLMRiskFeeBLS";
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
	private boolean updateLMRiskFee() {
		LMRiskFeeSchema tLMRiskFeeSchema = new LMRiskFeeSchema();
		tLMRiskFeeSchema = (LMRiskFeeSchema) mInputData.getObjectByObjectName(
				"LMRiskFeeSchema", 0);
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "OLMRiskFeeBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LMRiskFeeDB tLMRiskFeeDB = new LMRiskFeeDB(conn);
			tLMRiskFeeDB.setSchema(tLMRiskFeeSchema);
			if (!tLMRiskFeeDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLMRiskFeeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "OLMRiskFeeBLS";
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
			tError.moduleName = "OLMRiskFeeBLS";
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
