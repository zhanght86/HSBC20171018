package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class NewPolFeeWithdrawBLS {
private static Logger logger = Logger.getLogger(NewPolFeeWithdrawBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	public NewPolFeeWithdrawBLS() {
	}

	public static void main(String[] args) {
		NewPolFeeWithdrawBLS mNewPolFeeWithdrawBLS1 = new NewPolFeeWithdrawBLS();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData) {
		boolean tReturn = false;
		logger.debug("Start NewPolFeeWithdraw BLS Submit...");
		// 信息保存
		{
			tReturn = save(cInputData);
		}

		if (tReturn)
			logger.debug("Save sucessful");
		else
			logger.debug("Save failed");

		logger.debug("End NewPolFeeWithdraw BLS Submit...");

		return tReturn;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "NewPolFeeWithdrawBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// 个人保单表
			logger.debug("Start 个人保单表...");
			LCPolDBSet tLCPolDBSet = new LCPolDBSet(conn);
			tLCPolDBSet.set((LCPolSet) mInputData.getObjectByObjectName(
					"LCPolSet", 0));
			if (!tLCPolDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewPolFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "个人保单表数据更新失败!";
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
				tError.moduleName = "NewPolFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实付总表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			/** 其他退费实付表 */
			logger.debug("Start 实付子表...");
			LJAGetOtherDBSet tLLJAGetOtherDBSet = new LJAGetOtherDBSet(conn);
			tLLJAGetOtherDBSet.set((LJAGetOtherSet) mInputData
					.getObjectByObjectName("LJAGetOtherSet", 0));
			logger.debug("Get LJAGetOther");
			if (!tLLJAGetOtherDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLLJAGetOtherDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewPolFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实付子表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			/** 打印管理表 */
			logger.debug("Start 打印管理表...");
			LOPRTManagerDBSet tLOPRTManagerDBSet = new LOPRTManagerDBSet(conn);
			tLOPRTManagerDBSet.set((LOPRTManagerSet) mInputData
					.getObjectByObjectName("LOPRTManagerSet", 0));
			logger.debug("Get LOPRTManager");
			if (!tLOPRTManagerDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLOPRTManagerDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewPolFeeWithdrawBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "打印管理表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 调用暂交费退费提交程序
			logger.debug("Start 暂交费退费...");
			VData tempfeeVData = new VData();
			tempfeeVData = (VData) mInputData.getObjectByObjectName("VData", 0);
			if (tempfeeVData != null) {
				TempFeeWithdrawBLS tTempFeeWithdrawBLS = new TempFeeWithdrawBLS();
				tTempFeeWithdrawBLS.setConnection(conn);
				if (tTempFeeWithdrawBLS.submitData(tempfeeVData, "INSERT") == false) {
					logger.debug("暂交退费失败！原因："
							+ tTempFeeWithdrawBLS.mErrors.getFirstError());
					return false;
				}
			}
			logger.debug("End 暂交费退费...");

			conn.commit();
			conn.close();
			logger.debug("commit over");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewPolFeeWithdrawBLS";
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
}
