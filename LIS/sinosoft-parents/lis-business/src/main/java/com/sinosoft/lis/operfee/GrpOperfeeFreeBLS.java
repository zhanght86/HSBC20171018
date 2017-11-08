package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LCContHangUpStateDBSet;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
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
 * @author WL
 * @version 1.0
 */

public class GrpOperfeeFreeBLS {
private static Logger logger = Logger.getLogger(GrpOperfeeFreeBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpOperfeeFreeBLS() {
	}

	public static void main(String[] args) {
		GrpOperfeeFreeBLS mGrpOperfeeFreeBLS = new GrpOperfeeFreeBLS();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Start GrpOperfeeFree BLS Submit...");
		// 信息保存
		if (this.mOperate.equals("UPDATE")) {
			tReturn = updateData(cInputData);
		}

		if (tReturn)
			logger.debug("UPDATE sucessful");
		else
			logger.debug("UPDATE failed");

		logger.debug("End GrpOperfeeFree BLS Submit...");

		return tReturn;
	}

	// 核销事务操作
	private boolean updateData(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Update...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpOperfeeFreeBLS";
			tError.functionName = "UpdateData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// 更新应收个人交费表
			logger.debug("Start 更新保单挂起状态表...");
			LCContHangUpStateDBSet tLCContHangUpStateUpdateDBSet = new LCContHangUpStateDBSet(
					conn);
			tLCContHangUpStateUpdateDBSet.set((LCContHangUpStateSet) mInputData
					.getObject(0));
			if (!tLCContHangUpStateUpdateDBSet.update()) {
				// @@错误处理
				this.mErrors
						.copyAllErrors(tLCContHangUpStateUpdateDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "GrpOperfeeFreeBLS";
				tError.functionName = "deleteData";
				tError.errorMessage = "保单挂起状态表数据更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("更新保单挂起状态表");

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpOperfeeFreeBLS";
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
