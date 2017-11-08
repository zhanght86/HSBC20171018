package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LDSysTraceDB;
import com.sinosoft.lis.schema.LDSysTraceSchema;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LDSysTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统保全人工核保保单申请功能部分
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

public class PRnewUWManuApplyChkBLS {
private static Logger logger = Logger.getLogger(PRnewUWManuApplyChkBLS.class);
	// 是否存在需要人工核保保单
	int merrno = 0;
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	//
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

	public PRnewUWManuApplyChkBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start PEdorUWManuApplyChkBLS Submit...");
		if (!this.saveData()) {
			return false;
		}
		logger.debug("End PEdorUWManuApplyChkBLS Submit...");

		mInputData = null;
		return true;
	}

	private boolean saveData() {

		LDSysTraceSet mLDSysTraceSet = (LDSysTraceSet) mInputData
				.getObjectByObjectName("LDSysTraceSet", 0);
		Connection conn = DBConnPool.getConnection();
		// conn = .getDefaultConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuApplyChkBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 删除部分
			int pnum = mLDSysTraceSet.size();
			if (pnum > 0) {
				for (int i = 1; i <= pnum; i++) {
					LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
					LDSysTraceDB tLDSysTraceDB = new LDSysTraceDB(conn);

					tLDSysTraceSchema = mLDSysTraceSet.get(i);

					tLDSysTraceDB.setPolNo(tLDSysTraceSchema.getPolNo());
					tLDSysTraceDB.setOperator(tLDSysTraceSchema.getOperator());
					tLDSysTraceDB.setPolState(2001);

					// 删除
					if (tLDSysTraceDB.deleteSQL() == false) {
						// @@错误处理
						this.mErrors.copyAllErrors(tLDSysTraceDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "PEdorUWManuApplyChkBLS";
						tError.functionName = "saveData";
						tError.errorMessage = "LDSysTrace表删除失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}

					// 保存
					tLDSysTraceDB = new LDSysTraceDB(conn);
					tLDSysTraceDB.setSchema(tLDSysTraceSchema);

					if (tLDSysTraceDB.insert() == false) {
						// @@错误处理
						this.mErrors.copyAllErrors(tLDSysTraceDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "PEdorUWManuApplyChkBLS";
						tError.functionName = "saveData";
						tError.errorMessage = "LDSysTrace表插入失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
			}

			conn.commit();
			conn.close();
		} // end of try
		catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuApplyChkBLS";
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
