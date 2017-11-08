package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
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
 * @author unascribed
 * @version 1.0
 */

public class GrpUWSetSpecialFlagBLS {
private static Logger logger = Logger.getLogger(GrpUWSetSpecialFlagBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpUWSetSpecialFlagBLS() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		logger.debug("Start GrpFeeBackBLS Submit...");
		if (!saveData()) {
			return false;
		}
		logger.debug("End GrpFeeBackBLS Submit...");

		return true;
	}

	private boolean saveData() {

		LCGrpPolSet mLCGrpPolSet = (LCGrpPolSet) mInputData
				.getObjectByObjectName("LCGrpPolSet", 0);

		try {
			Connection conn = DBConnPool.getConnection();
			// conn = .getDefaultConnection();
			if (conn == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpUWSetSpecialFlagBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;

			}

			conn.setAutoCommit(false);

			logger.debug("---In GrpUWSetSpecialFlagBLS ---"
					+ mLCGrpPolSet.size());

			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);
			// 修改部分
			if (mLCGrpPolSet != null) {
				for (int i = 1; i <= mLCGrpPolSet.size(); i++) {

					logger.debug("---In GrpUWSetSpecialFlagBLS ---" + i);
					tLCGrpPolDB.setSchema(mLCGrpPolSet.get(i));
					if (tLCGrpPolDB.update() == false) {
						// @@错误处理
						this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "GrpUWSetSpecialFlagBLS";
						tError.functionName = "saveData";
						tError.errorMessage = "数据更新失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
						// continue;
					}
				}

			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpUWSetSpecialFlagBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据传输失败!";
				this.mErrors.addOneError(tError);

			}

			logger.debug("-----------UpDate-------------");

			conn.commit();
			conn.close();

		} // end of try
		catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpUWSetSpecialFlagBLS";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
