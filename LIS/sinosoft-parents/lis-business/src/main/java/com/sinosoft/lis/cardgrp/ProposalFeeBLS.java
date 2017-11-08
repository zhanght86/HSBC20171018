package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vdb.LJTempFeeDBSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
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

public class ProposalFeeBLS {
private static Logger logger = Logger.getLogger(ProposalFeeBLS.class);
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	public ProposalFeeBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start ProposalFee BLS Submit...");
		if (!this.saveData())
			return false;
		logger.debug("End ProposalFee BLS Submit...");

		mInputData = null;
		return true;
	}

	private boolean saveData() {
		LJTempFeeSet mLJTempFeeSet = (LJTempFeeSet) mInputData
				.getObjectByObjectName("LJTempFeeSet", 0);
		String tPolNo = mLJTempFeeSet.get(1).getOtherNo();

		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalFeeBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 先把原来关联的部分取消关联
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB(conn);
			tLJTempFeeDB.setOtherNo(tPolNo);
			tLJTempFeeDB.setOtherNoType("0");
			tLJTempFeeDB.setTempFeeType("1");
			tLJTempFeeDB.setConfFlag("0");

			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();
			if (tLJTempFeeDB.mErrors.needDealError() == true) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据库操作失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			int n = tLJTempFeeSet.size();
			for (int i = 1; i <= n; i++) {
				LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);

				tLJTempFeeSchema.setOtherNo("");
				tLJTempFeeSchema.setOtherNoType("");

				tLJTempFeeSet.set(i, tLJTempFeeSchema);
			}
			LJTempFeeDBSet tLJTempFeeDBSet = new LJTempFeeDBSet(conn);
			tLJTempFeeDBSet.set(tLJTempFeeSet);
			if (tLJTempFeeDBSet.update() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJTempFeeDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "ProposalFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "更新原数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 保存现在的关联
			if (!mLJTempFeeSet.get(1).getTempFeeNo().trim().equals("NULL")) {
				LJTempFeeDBSet mLJTempFeeDBSet = new LJTempFeeDBSet(conn);
				mLJTempFeeDBSet.set(mLJTempFeeSet);
				if (mLJTempFeeDBSet.update() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(mLJTempFeeDBSet.mErrors);
					CError tError = new CError();
					tError.moduleName = "ProposalFeeBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "保存数据失败!";
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
			tError.moduleName = "ProposalFeeBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

}
