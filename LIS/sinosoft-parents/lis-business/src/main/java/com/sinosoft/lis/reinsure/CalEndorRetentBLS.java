

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.reinsure;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LREdorMainDBSet;
import com.sinosoft.lis.vschema.LREdorMainSet;
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

public class CalEndorRetentBLS {
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	public CalEndorRetentBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		System.out.println("Start CalEndorRetentBLS BLS Submit...");

		if (!this.saveData()) {
			return false;
		}

		System.out.println("End CalEndorRetentBLS BLS Submit...");
		mInputData = null;
		return true;
	}

	private boolean saveData() {
		LREdorMainSet tLREdorMainSet = new LREdorMainSet();
		LRPolSet tLRPolSet = new LRPolSet();
		tLREdorMainSet = (LREdorMainSet) mInputData.getObjectByObjectName(
				"LREdorMainSet", 0);
		tLRPolSet = (LRPolSet) mInputData.getObjectByObjectName("LRPolSet", 0);
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CalEndorRetentBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);
			LREdorMainDBSet mLREdorMainDBSet = new LREdorMainDBSet(conn);
			mLREdorMainDBSet.set(tLREdorMainSet);
			if (mLREdorMainDBSet.insert() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(mLREdorMainDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "CalEndorRetentBLS";
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
			tError.moduleName = "CalEdorRetentBLS";
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
