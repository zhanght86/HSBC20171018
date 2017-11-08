package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LMCardRiskDB;
import com.sinosoft.lis.db.LMCertifyDesDB;
import com.sinosoft.lis.db.LMCertifyDesTraceDB;
import com.sinosoft.lis.schema.LMCertifyDesSchema;
import com.sinosoft.lis.vdb.LMCardRiskDBSet;
import com.sinosoft.lis.vdb.LMCertifyDesDBSet;
import com.sinosoft.lis.vdb.LMCertifyDesTraceDBSet;
import com.sinosoft.lis.vschema.LMCardRiskSet;
import com.sinosoft.lis.vschema.LMCertifyDesSet;
import com.sinosoft.lis.vschema.LMCertifyDesTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统案件－报案保存功能部分
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
 * @author 刘岩松
 * @version 1.0
 */

public class CertifyDescBLS {
private static Logger logger = Logger.getLogger(CertifyDescBLS.class);
	// 传输数据类
	private VData mInputData;

	private String mOperateType = "";

	private String mCertifyClass = "";// 记录单证类型 P or D

	private LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();

	private LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();

	private LMCertifyDesTraceSet mLMCertifyDesTraceSet = new LMCertifyDesTraceSet();

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	public CertifyDescBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		logger.debug("Start Report BLS Submit...");
		mOperateType = (String) cInputData.get(0);
		mCertifyClass = (String) cInputData.get(1);

		mLMCertifyDesSet = ((LMCertifyDesSet) cInputData.getObjectByObjectName(
				"LMCertifyDesSet", 0));

		mLMCertifyDesTraceSet = ((LMCertifyDesTraceSet) cInputData
				.getObjectByObjectName("LMCertifyDesTraceSet", 0));

		// if (mCertifyClass.equals("D")) {
		// mLMCardRiskSet = ((LMCardRiskSet) cInputData.getObjectByObjectName(
		// "LMCardRiskSet", 0));
		//		}

		if (mOperateType.equals("INSERT")) {
			if (!this.saveData())
				return false;
		}

		if (mOperateType.equals("UPDATE")) {
			if (!updateData())
				return false;
		}
		if (mOperateType.equals("DELETE")) {
			if (!deleteData())
				return false;
		}
		// logger.debug("End Report BLS Submit...");
		mInputData = null;
		return true;
	}

	private boolean saveData() {
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "CertifyDescBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			LMCertifyDesDBSet tLMCertifyDesDBSet = new LMCertifyDesDBSet(conn);
			tLMCertifyDesDBSet.set(mLMCertifyDesSet);
			if (tLMCertifyDesDBSet.insert() == false) {
				this.mErrors.copyAllErrors(tLMCertifyDesDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDescBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "保存数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LMCertifyDesTraceDB tLMCertifyDesTraceDB = new LMCertifyDesTraceDB(
					conn);
			tLMCertifyDesTraceDB.setSchema(mLMCertifyDesTraceSet.get(1));
			if (!tLMCertifyDesTraceDB.insert()) {
				this.mErrors.copyAllErrors(tLMCertifyDesTraceDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDesBLS";
				tError.functionName = "updateData";
				tError.errorMessage = "保存数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

//			if (mCertifyClass.equals("D")) {
//				LMCardRiskDBSet tLMCardRiskDBSet = new LMCardRiskDBSet(conn);
//				tLMCardRiskDBSet.set(mLMCardRiskSet);
//				if (!tLMCardRiskDBSet.insert()) {
//					CError tError = new CError();
//					tError.moduleName = "CertifyDescBLS";
//					tError.functionName = "saveData";
//					tError.errorMessage = "请您检查险种号码是否出现重复！！！";
//					this.mErrors.addOneError(tError);
//					conn.rollback();
//					conn.close();
//					return false;
//				}
//			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "CertifyDescBLS";
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

	private boolean deleteData() {
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CertifyDescBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB(conn);
			LMCertifyDesSchema tLMCertifyDesSchema = new LMCertifyDesSchema();
			tLMCertifyDesSchema.setSchema(mLMCertifyDesSet.get(1));
			tLMCertifyDesDB
					.setCertifyCode(tLMCertifyDesSchema.getCertifyCode());
			if (!tLMCertifyDesDB.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDescBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "删除失败！！！";
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
			tError.moduleName = "CertifyDescBLS";
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

	// 先进行删除，在进行新增操作
	private boolean updateData() {
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			CError tError = new CError();
			tError.moduleName = "CertifyDescBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB(conn);
			// tLMCertifyDesDB.setCertifyCode(mLMCertifyDesSet.get(1)
			// .getCertifyCode());
			// if (!tLMCertifyDesDB.delete()) {
			// this.mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "CertifyDesBLS";
			// tError.functionName = "updateData";
			// tError.errorMessage = "删除失败!";
			// this.mErrors.addOneError(tError);
			// conn.rollback();
			// conn.close();
			// return false;
			// }
			//
			// LMCertifyDesDBSet tLMCertifyDesDBSet = new
			// LMCertifyDesDBSet(conn);
			// tLMCertifyDesDBSet.add(mLMCertifyDesSet.get(1));
			// if (!tLMCertifyDesDBSet.insert()) {
			// this.mErrors.copyAllErrors(tLMCertifyDesDBSet.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "CertifyDesBLS";
			// tError.functionName = "updateData";
			// tError.errorMessage = "保存数据失败!";
			// this.mErrors.addOneError(tError);
			// conn.rollback();
			// conn.close();
			// return false;
			// }

			LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB(conn);
			tLMCertifyDesDB.setSchema(mLMCertifyDesSet.get(1));
			if (!tLMCertifyDesDB.update()) {
				this.mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDesBLS";
				tError.functionName = "updateData";
				tError.errorMessage = "更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			LMCertifyDesTraceDB tLMCertifyDesTraceDB = new LMCertifyDesTraceDB(
					conn);
			tLMCertifyDesTraceDB.setSchema(mLMCertifyDesTraceSet.get(1));
			if (!tLMCertifyDesTraceDB.insert()) {
				this.mErrors.copyAllErrors(tLMCertifyDesTraceDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CertifyDesBLS";
				tError.functionName = "updateData";
				tError.errorMessage = "保存数据失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "CaseCureBLS";
			tError.functionName = "updateData";
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
