

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.reinsure;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LRPolDBSet;
import com.sinosoft.lis.vschema.LRPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统投保暂交费功能部分
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author YT
 * @version 1.0
 */

public class CalPolRetentBLS {
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	public CalPolRetentBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		System.out.println("Start tCalPolRetentBLS BLS Submit...");

		if (!this.saveData()) {
			return false;
		}

		System.out.println("End tCalPolRetentBLS BLS Submit...");
		mInputData = null;
		return true;
	}

	private boolean saveData() {
		LRPolSet mLRPolSet = new LRPolSet();
		mLRPolSet = (LRPolSet) mInputData.getObjectByObjectName("LRPolSet", 0);
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CalLegalRetentBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			LRPolDBSet mLRPolDBSet = new LRPolDBSet(conn);
			mLRPolDBSet.set(mLRPolSet);
			if (mLRPolDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(mLRPolDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "CalLegalRetentBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "保存数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} // end of try
		catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CalLegalRetentBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			}

			catch (Exception e) {
			}
			return false;
		}
		return true;
	}
}
