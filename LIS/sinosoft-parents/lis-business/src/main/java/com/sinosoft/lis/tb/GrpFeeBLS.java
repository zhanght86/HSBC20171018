/**
 * Copyright ? 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCGrpFeeDB;
import com.sinosoft.lis.schema.LCGrpFeeSchema;
import com.sinosoft.lis.vdb.LCGrpFeeDBSet;
import com.sinosoft.lis.vdb.LCGrpFeeParamDBSet;
import com.sinosoft.lis.vschema.LCGrpFeeParamSet;
import com.sinosoft.lis.vschema.LCGrpFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * 管理费数据提交类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 根据传入类型，执行新增、删除、修改操作
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
public class GrpFeeBLS {
private static Logger logger = Logger.getLogger(GrpFeeBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public GrpFeeBLS() {
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

		logger.debug("Start GrpFeeBLS Submit...");
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = saveGrpFee(cInputData);
		}
		if (this.mOperate.equals("DELETE||MAIN")) {
			tReturn = deleteGrpFee(cInputData);
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			tReturn = updateGrpFee(cInputData);
		}
		if (tReturn)
			logger.debug(" sucessful");
		else
			logger.debug("Save failed");
		logger.debug("End GrpFeeBLS Submit...");
		return tReturn;
	}

	/**
	 * 新增操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean saveGrpFee(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpFeeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			ExeSQL tExeSQL = new ExeSQL(conn);

			LCGrpFeeDBSet tLCGrpFeeDBSet = new LCGrpFeeDBSet(conn);
			tLCGrpFeeDBSet.set((LCGrpFeeSet) mInputData.getObjectByObjectName(
					"LCGrpFeeSet", 0));

			LCGrpFeeParamDBSet tLCGrpFeeParamDBSet = new LCGrpFeeParamDBSet(
					conn);
			tLCGrpFeeParamDBSet.set((LCGrpFeeParamSet) mInputData
					.getObjectByObjectName("LCGrpFeeParamSet", 0));

			// 删除管理费子表信息
			String tSql = "delete from LCGrpFeeParam Where GrpPolNo = '"
					+ tLCGrpFeeDBSet.get(1).getGrpPolNo()
					+ "' and RiskCode = '"
					+ tLCGrpFeeDBSet.get(1).getRiskCode() + "' and FeeCode = '"
					+ tLCGrpFeeDBSet.get(1).getFeeCode()
					+ "' and InsuAccNo = '"
					+ tLCGrpFeeDBSet.get(1).getInsuAccNo()
					+ "' and PayPlanCode = '"
					+ tLCGrpFeeDBSet.get(1).getPayPlanCode() + "'";
			tExeSQL = new ExeSQL(conn);
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "删除管理费子表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			if (!tLCGrpFeeDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpFeeDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "删除管理费主表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			if (!tLCGrpFeeDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpFeeDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "插入管理费主表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			if (!tLCGrpFeeParamDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpFeeDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "插入管理费子表信息失败!";
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
			tError.moduleName = "GrpFeeBLS";
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
	 * 删除操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean deleteGrpFee(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpFeeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 删除...");
			ExeSQL tExeSQL = new ExeSQL(conn);

			LCGrpFeeDBSet tLCGrpFeeDBSet = new LCGrpFeeDBSet(conn);
			tLCGrpFeeDBSet.set((LCGrpFeeSet) mInputData.getObjectByObjectName(
					"LCGrpFeeSet", 0));

			// 删除管理费子表信息
			String tSql = "delete from LCGrpFeeParam Where GrpPolNo = '"
					+ tLCGrpFeeDBSet.get(1).getGrpPolNo() + "' and FeeCode = '"
					+ tLCGrpFeeDBSet.get(1).getFeeCode()
					+ "' and InsuAccNo = '"
					+ tLCGrpFeeDBSet.get(1).getInsuAccNo()
					+ "' and PayPlanCode ='"
					+ tLCGrpFeeDBSet.get(1).getPayPlanCode() + "'";
			tExeSQL = new ExeSQL(conn);
			if (!tExeSQL.execUpdateSQL(tSql)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
				tError.functionName = "deleteGrpFee";
				tError.errorMessage = "删除管理费子表信息失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			// 删除主表，直接使用delet函数
			if (!tLCGrpFeeDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
				tError.functionName = "deleteGrpFee";
				tError.errorMessage = "删除管理费子表信息失败!";
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
			tError.moduleName = "GrpFeeBLS";
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
	 * 更新操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean updateGrpFee(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "GrpFeeBLS";
			tError.functionName = "updateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			LCGrpFeeDB tLCGrpFeeDB = new LCGrpFeeDB(conn);
			tLCGrpFeeDB.setSchema((LCGrpFeeSchema) mInputData
					.getObjectByObjectName("LCGrpFeeSchema", 0));
			if (!tLCGrpFeeDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpFeeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpFeeBLS";
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
			tError.moduleName = "GrpFeeBLS";
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
