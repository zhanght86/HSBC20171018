package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LJSPayDBSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

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

public class NewIndiDueFeeMultiBLS {
private static Logger logger = Logger.getLogger(NewIndiDueFeeMultiBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public NewIndiDueFeeMultiBLS() {
	}

	public static void main(String[] args) {
		NewIndiDueFeeMultiBLS mNewIndiDueFeeMultiBLS1 = new NewIndiDueFeeMultiBLS();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Start NewIndiDueFeeMulti BLS Submit...");
		// 信息保存
		if (this.mOperate.equals("INSERT")) {
			tReturn = save(cInputData);
		}

		if (tReturn)
			logger.debug("Save sucessful");
		else
			logger.debug("Save failed");

		logger.debug("End NewIndiDueFeeMulti BLS Submit...");

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
			tError.moduleName = "NewIndiDueFeeMultiBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			/** 应收总表 */

			logger.debug("Start 应收总表...");
			LJSPayDBSet tLJSPayDBSet = new LJSPayDBSet(conn);
			tLJSPayDBSet.set((LJSPaySet) mInputData.getObjectByObjectName(
					"LJSPaySet", 0));
			logger.debug("Get LJSPay");
			if (!tLJSPayDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJSPayDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "NewIndiDueFeeMultiBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "应收总表数据保存失败!";
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
			tError.moduleName = "NewIndiDueFeeMultiBLS";
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
