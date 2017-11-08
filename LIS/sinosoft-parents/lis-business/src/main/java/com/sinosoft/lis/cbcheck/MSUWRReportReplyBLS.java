package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统个人单人工核保生调回复部分
 * </p>
 * <p>
 * Description: 数据库功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author WHN
 * @version 1.0
 */

public class MSUWRReportReplyBLS {
private static Logger logger = Logger.getLogger(MSUWRReportReplyBLS.class);
	// 是否存在需要人工核保保单
	int merrno = 0;
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

	public MSUWRReportReplyBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start UWRReportReplyBLS Submit...");
		if (!this.saveData())
			return false;
		logger.debug("End UWRReportReplyBLS Submit...");

		mInputData = null;
		return true;
	}

	private boolean saveData() {

		LCRReportSchema mLCRReportSchema = (LCRReportSchema) mInputData
				.getObjectByObjectName("LCRReportSchema", 0);
		LCCUWMasterSchema mLCCUWMasterSchema = (LCCUWMasterSchema) mInputData
				.getObjectByObjectName("LCCUWMasterSchema", 0);
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 保存部分
			// 保单
			LCRReportDB tLCRReportDB = new LCRReportDB(conn);
			tLCRReportDB.setSchema(mLCRReportSchema);
			if (tLCRReportDB.update() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCRReportDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWRReportReplyBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "LCRReport表修改失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			logger.debug("-----------EEE--------------");
			if (mLCCUWMasterSchema != null) {
				LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB(conn);
				tLCCUWMasterDB.setSchema(mLCCUWMasterSchema);
				if (tLCCUWMasterDB.update() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWRReportReplyBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "LCUWMaster表更新失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			conn.commit();
			conn.close();
		} // end of try
		catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			return false;
		}

		return true;
	}
}
