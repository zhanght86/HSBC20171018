/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LCGrpContDBSet;
import com.sinosoft.lis.vdb.LCGrpPolDBSet;
import com.sinosoft.lis.vdb.LJAGetDBSet;
import com.sinosoft.lis.vdb.LJAGetOtherDBSet;
import com.sinosoft.lis.vdb.LOPRTManagerDBSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis_New
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Sinosoft
 * @version 1.0
 */

public class NewGrpContFeeWithdrawBLS {
private static Logger logger = Logger.getLogger(NewGrpContFeeWithdrawBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	// private String mOperate;
	public NewGrpContFeeWithdrawBLS() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData) {
		boolean tReturn = false;
		logger.debug("Start NewContFeeWithdraw BLS Submit...");
		// 信息保存
		{
			tReturn = save(cInputData);
		}

		if (tReturn)
			logger.debug("Save sucessful");
		else
			logger.debug("Save failed");

		logger.debug("End NewContFeeWithdraw BLS Submit...");

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
			tError.moduleName = "NewGrpPolFeeWithdrawBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// 集体保单表
			logger.debug("Start 集体保单表...");
			LCGrpPolDBSet tLCGrpPolDBSet = new LCGrpPolDBSet(conn);
			tLCGrpPolDBSet.set((LCGrpPolSet) mInputData.getObjectByObjectName(
					"LCGrpPolSet", 0));
			if (!tLCGrpPolDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewGrpContFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "集体保单表数据更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			// 集体合同表
			LCGrpContDBSet tLCGrpContDBSet = new LCGrpContDBSet(conn);
			tLCGrpContDBSet.set((LCGrpContSet) mInputData
					.getObjectByObjectName("LCGrpContSet", 0));
			if (!tLCGrpContDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewGrpContFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "集体保单表数据更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			/** 实付总表 */
			logger.debug("Start 实付总表...");
			LJAGetDBSet tLJAGetDBSet = new LJAGetDBSet(conn);
			tLJAGetDBSet.set((LJAGetSet) mInputData.getObjectByObjectName(
					"LJAGetSet", 0));
			logger.debug("Get LJAGet");
			if (!tLJAGetDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAGetDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewGrpPolFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实付总表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			/** 实付总表 */
			logger.debug("Start 实付子表...");
			LJAGetOtherDBSet tLLJAGetOtherDBSet = new LJAGetOtherDBSet(conn);
			tLLJAGetOtherDBSet.set((LJAGetOtherSet) mInputData
					.getObjectByObjectName("LJAGetOtherSet", 0));
			logger.debug("Get LJAGetOther");
			if (!tLLJAGetOtherDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLLJAGetOtherDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewGrpContFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实付子表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 打印管理表
			logger.debug("Start 打印管理表...");
			LOPRTManagerDBSet tLOPRTManagerDBSet = new LOPRTManagerDBSet(conn);
			tLOPRTManagerDBSet.set((LOPRTManagerSet) mInputData
					.getObjectByObjectName("LOPRTManagerSet", 0));
			logger.debug("Get LOPRTManager");
			if (!tLOPRTManagerDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLOPRTManagerDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewGrpContFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "打印管理表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("commit over");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewGrpContFeeWithdrawBLS";
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
		NewGrpPolFeeWithdrawBLS mNewGrpPolFeeWithdrawBLS1 = new NewGrpPolFeeWithdrawBLS();
	}
}
