package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LPAppntGrpDB;
import com.sinosoft.lis.db.LPGUWErrorDB;
import com.sinosoft.lis.db.LPGUWMasterDB;
import com.sinosoft.lis.db.LPGUWSubDB;
import com.sinosoft.lis.db.LPGrpDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPLoanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPAppntGrpSchema;
import com.sinosoft.lis.schema.LPGUWErrorSchema;
import com.sinosoft.lis.schema.LPGUWMasterSchema;
import com.sinosoft.lis.schema.LPGUWSubSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPGrpSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.tb.GroupPolBL;
import com.sinosoft.lis.tb.ProposalBL;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保全删除数据库操作类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft< /p>
 * 
 * @author lh
 * @version 1.0
 */

public class GrpEdorAppCancelBLS {
private static Logger logger = Logger.getLogger(GrpEdorAppCancelBLS.class);
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData = new VData();
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	// 数据操作字符串
	public String mOperate;

	public GrpEdorAppCancelBLS() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (mOperate.equals("DELETE||EDOR")) {
			if (!this.delEdor())
				return false;
		}
		return true;
	}

	public boolean delEdor() {
		LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
		LPGrpPolDB tLPGrpPolDB;
		tLPGrpPolSchema = (LPGrpPolSchema) mInputData.getObjectByObjectName(
				"LPGrpPolSchema", 0);

		LPGrpSchema tLPGrpSchema = new LPGrpSchema();
		LPGrpDB tLPGrpDB;
		tLPGrpSchema = (LPGrpSchema) mInputData.getObjectByObjectName(
				"LPGrpSchema", 0);

		LPAppntGrpSchema tLPAppntGrpSchema = new LPAppntGrpSchema();
		LPAppntGrpDB tLPAppntGrpDB;
		tLPAppntGrpSchema = (LPAppntGrpSchema) mInputData
				.getObjectByObjectName("LPAppntGrpSchema", 0);

		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccDB tLPInsureAccDB;
		tLPInsureAccSchema = (LPInsureAccSchema) mInputData
				.getObjectByObjectName("LPInsureAccSchema", 0);

		LPLoanSchema tLPLoanSchema = new LPLoanSchema();
		LPLoanDB tLPLoanDB;
		tLPLoanSchema = (LPLoanSchema) mInputData.getObjectByObjectName(
				"LPLoanSchema", 0);

		LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
		LPInsureAccTraceDB tLPInsureAccTraceDB;
		tLPInsureAccTraceSchema = (LPInsureAccTraceSchema) mInputData
				.getObjectByObjectName("LPInsureAccTraceSchema", 0);

		LPGUWErrorSchema tLPGUWErrorSchema = new LPGUWErrorSchema();
		LPGUWErrorDB tLPGUWErrorDB;
		tLPGUWErrorSchema = (LPGUWErrorSchema) mInputData
				.getObjectByObjectName("LPGUWErrorSchema", 0);
		if (tLPGUWErrorSchema == null)
			logger.debug("null");

		LPGUWSubSchema tLPGUWSubSchema = new LPGUWSubSchema();
		LPGUWSubDB tLPGUWSubDB;
		tLPGUWSubSchema = (LPGUWSubSchema) mInputData.getObjectByObjectName(
				"LPGUWSubSchema", 0);

		LPGUWMasterSchema tLPGUWMasterSchema = new LPGUWMasterSchema();
		LPGUWMasterDB tLPGUWMasterDB;
		tLPGUWMasterSchema = (LPGUWMasterSchema) mInputData
				.getObjectByObjectName("LPGUWMasterSchema", 0);

		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemDB tLPGrpEdorItemDB;
		tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);

		// 新保加人, add by Minim
		LCPolSet tLCPolSet = (LCPolSet) mInputData.getObjectByObjectName(
				"LCPolSet", 0);

		if (tLPGrpEdorItemSchema.getEdorType().equals("NI")
				&& tLCPolSet != null) {
			logger.debug("开始撤销新保加人...");

			LCPolSchema tLCPolSchema;
			VData tVData = new VData();
			ProposalBL tProposalBL = new ProposalBL();
			GlobalInput tGlobalInput = new GlobalInput();

			for (int i = 0; i < tLCPolSet.size(); i++) {
				tLCPolSchema = tLCPolSet.get(i + 1);
				// 准备传输数据 VData
				tVData.clear();
				tVData.addElement(tLCPolSchema);
				tVData.addElement(tGlobalInput);

				logger.debug("start ProposalBL...");
				if (!tProposalBL.submitData(tVData, "DELETE||PROPOSAL")) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "GrpEdorAppCancelBLS";
					tError.functionName = "delEdor";
					tError.errorMessage = "新保加人删除保单失败，保单号为：("
							+ tLCPolSchema.getPolNo() + ")!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}

		// 新增附加险, add by Minim at 2004-2-5
		LCGrpPolSet tLCGrpPolSet = (LCGrpPolSet) mInputData
				.getObjectByObjectName("LCGrpPolSet", 0);

		if (tLPGrpEdorItemSchema.getEdorType().equals("NS")
				&& tLCGrpPolSet != null) {
			logger.debug("开始撤销新增附加险...");

			LCGrpPolSchema tLCGrpPolSchema;
			VData tVData = new VData();
			GroupPolBL tGroupPolBL = new GroupPolBL();
			GlobalInput tGlobalInput = new GlobalInput();

			for (int i = 0; i < tLCGrpPolSet.size(); i++) {
				tLCGrpPolSchema = tLCGrpPolSet.get(i + 1);
				// 准备传输数据 VData
				tVData.clear();
				tVData.addElement(tLCGrpPolSchema);
				tVData.addElement(tGlobalInput);

				logger.debug("start GroupPolBL...");
				if (!tGroupPolBL.submitData(tVData, "DELETE||GROUPPOL")) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "GrpEdorAppCancelBLS";
					tError.functionName = "delEdor";
					tError.errorMessage = "新增附加险删除保单失败，保单号为：("
							+ tLCGrpPolSchema.getGrpPolNo() + ")!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}

		Connection conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpEdorAppCancelBLS";
			tError.functionName = "delEdor";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			tLPGrpPolDB = new LPGrpPolDB(conn);
			tLPGrpPolDB.setSchema(tLPGrpPolSchema);
			if (!tLPGrpPolDB.delete()) {// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "集体保单表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPGrpDB = new LPGrpDB(conn);
			tLPGrpDB.setSchema(tLPGrpSchema);
			if (!tLPGrpDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "集体信息表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPAppntGrpDB = new LPAppntGrpDB(conn);
			tLPAppntGrpDB.setSchema(tLPAppntGrpSchema);
			if (!tLPAppntGrpDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "多投保人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPInsureAccDB = new LPInsureAccDB(conn);
			tLPInsureAccDB.setSchema(tLPInsureAccSchema);
			if (!tLPInsureAccDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "tLPInsureAccDB多投保人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPLoanDB = new LPLoanDB(conn);
			tLPLoanDB.setSchema(tLPLoanSchema);
			if (!tLPLoanDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "tLPLoanDB多投保人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPInsureAccTraceDB = new LPInsureAccTraceDB(conn);
			tLPInsureAccTraceDB.setSchema(tLPInsureAccTraceSchema);
			if (!tLPInsureAccTraceDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "tLPInsureAccTraceDB多投保人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			logger.debug("---------UW del");
			tLPGUWErrorDB = new LPGUWErrorDB(conn);
			tLPGUWErrorDB.setSchema(tLPGUWErrorSchema);

			if (!tLPGUWErrorDB.deleteSQL()) {// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人保全核保错误信息表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPGUWSubDB = new LPGUWSubDB(conn);
			tLPGUWSubDB.setSchema(tLPGUWSubSchema);
			if (!tLPGUWSubDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "集体保全核保轨迹表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPGUWMasterDB = new LPGUWMasterDB(conn);
			tLPGUWMasterDB.setSchema(tLPGUWMasterSchema);
			if (!tLPGUWMasterDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorUWCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "集体保全核保最近结果表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			tLPGrpEdorItemDB = new LPGrpEdorItemDB(conn);
			tLPGrpEdorItemDB.setSchema(tLPGrpEdorItemSchema);
			if (!tLPGrpEdorItemDB.delete()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "GrpEdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "集体批改主表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpEdorAppCancelBLS";
			tError.functionName = "delData";
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
