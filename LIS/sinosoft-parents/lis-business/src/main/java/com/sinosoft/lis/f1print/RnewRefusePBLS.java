package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author zz
 * @version 1.0
 */
import java.sql.Connection;

import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class RnewRefusePBLS {
private static Logger logger = Logger.getLogger(RnewRefusePBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private VData mResult = new VData();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public RnewRefusePBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Start RANF1PBLS Submit...");

		tReturn = save(cInputData);

		if (tReturn)
			logger.debug("Save sucessful");
		else
			logger.debug("Save failed");
		logger.debug("End RANF1PBLS Submit...");

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
			tError.moduleName = "RANF1PBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// 核保通知书(update)
			logger.debug("Start 首期通知书...");

			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB(conn);
			tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
					.getObjectByObjectName("LOPRTManagerSchema", 0));
			if (!tLOPRTManagerDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "RANF1PBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "首期通知书数据更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
			logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FirstPayF1pBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			tReturn = false;
		}

		return tReturn;
	}

}
