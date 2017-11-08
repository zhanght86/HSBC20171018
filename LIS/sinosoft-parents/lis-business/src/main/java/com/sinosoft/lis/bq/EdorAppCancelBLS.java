package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LBMissionDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPAppntIndDB;
import com.sinosoft.lis.db.LPAppntTraceDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPLoanDB;
import com.sinosoft.lis.db.LPMoveDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPReturnLoanDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.db.LPUWErrorDB;
import com.sinosoft.lis.db.LPUWMasterDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.db.LPUWSubDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPAppntIndSchema;
import com.sinosoft.lis.schema.LPAppntTraceSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.schema.LPMoveSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.schema.LPUWErrorSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.schema.LPUWMasterSchema;
import com.sinosoft.lis.schema.LPUWSubSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vdb.LCPolDBSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.TransferData;
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
public class EdorAppCancelBLS {
private static Logger logger = Logger.getLogger(EdorAppCancelBLS.class);
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData = new VData();

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	// 数据操作字符串
	public String mOperate;

	public EdorAppCancelBLS() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (mOperate.equals("DELETE||EDOR")) {
			logger.debug("edordeldata");

			if (!this.delEdor()) {
				return false;
			}
		}

		return true;
	}

	public boolean delEdor() {
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPolDB tLPPolDB;
		tLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
				"LPPolSchema", 0);

		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremDB tLPPremDB;
		tLPPremSchema = (LPPremSchema) mInputData.getObjectByObjectName(
				"LPPremSchema", 0);

		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		LPPersonDB tLPPersonDB;
		tLPPersonSchema = (LPPersonSchema) mInputData.getObjectByObjectName(
				"LPPersonSchema", 0);

		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutyDB tLPDutyDB;
		tLPDutySchema = (LPDutySchema) mInputData.getObjectByObjectName(
				"LPDutySchema", 0);

		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetDB tLPGetDB;
		tLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
				"LPGetSchema", 0);

		LPLoanSchema tLPLoanSchema = new LPLoanSchema();
		LPLoanDB tLPLoanDB;
		tLPLoanSchema = (LPLoanSchema) mInputData.getObjectByObjectName(
				"LPLoanSchema", 0);
		LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
		LPReturnLoanDB tLPReturnLoanDB;
		tLPReturnLoanSchema = (LPReturnLoanSchema) mInputData
				.getObjectByObjectName("LPReturnLoanSchema", 0);

		LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
		LPCustomerImpartDB tLPCustomerImpartDB;
		tLPCustomerImpartSchema = (LPCustomerImpartSchema) mInputData
				.getObjectByObjectName("LPCustomerImpartSchema", 0);

		LPAppntIndSchema tLPAppntIndSchema = new LPAppntIndSchema();
		LPAppntIndDB tLPAppntIndDB;
		tLPAppntIndSchema = (LPAppntIndSchema) mInputData
				.getObjectByObjectName("LPAppntIndSchema", 0);

		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LPInsuredDB tLPInsuredDB;
		tLPInsuredSchema = (LPInsuredSchema) mInputData.getObjectByObjectName(
				"LPInsuredSchema", 0);

		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfDB tLPBnfDB;
		tLPBnfSchema = (LPBnfSchema) mInputData.getObjectByObjectName(
				"LPBnfSchema", 0);

		LPMoveSchema tLPMoveSchema = new LPMoveSchema();
		LPMoveDB tLPMoveDB;
		tLPMoveSchema = (LPMoveSchema) mInputData.getObjectByObjectName(
				"LPMoveSchema", 0);

		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		tLPInsureAccSchema = (LPInsureAccSchema) mInputData
				.getObjectByObjectName("LPInsureAccSchema", 0);

		LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
		tLPInsureAccTraceSchema = (LPInsureAccTraceSchema) mInputData
				.getObjectByObjectName("LPInsureAccTraceSchema", 0);

		LPAppntTraceSchema tLPAppntTraceSchema = new LPAppntTraceSchema();
		tLPAppntTraceSchema = (LPAppntTraceSchema) mInputData
				.getObjectByObjectName("LPAppntTraceSchema", 0);

		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		tLJSPaySchema = (LJSPaySchema) mInputData.getObjectByObjectName(
				"LJSPaySchema", 0);

		LJSGetSchema tLJSGetSchema = new LJSGetSchema();
		tLJSGetSchema = (LJSGetSchema) mInputData.getObjectByObjectName(
				"LJSGetSchema", 0);

		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = (LJSGetEndorseSchema) mInputData
				.getObjectByObjectName("LJSGetEndorseSchema", 0);

		LPUWErrorSchema tLPUWErrorSchema = new LPUWErrorSchema();
		LPUWErrorDB tLPUWErrorDB;
		tLPUWErrorSchema = (LPUWErrorSchema) mInputData.getObjectByObjectName(
				"LPUWErrorSchema", 0);

		LPUWSubSchema tLPUWSubSchema = new LPUWSubSchema();
		LPUWSubDB tLPUWSubDB;
		tLPUWSubSchema = (LPUWSubSchema) mInputData.getObjectByObjectName(
				"LPUWSubSchema", 0);

		LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
		LPUWMasterDB tLPUWMasterDB;
		tLPUWMasterSchema = (LPUWMasterSchema) mInputData
				.getObjectByObjectName("LPUWMasterSchema", 0);

		LPUWMasterMainSchema tLPUWMasterMainSchema = new LPUWMasterMainSchema();
		LPUWMasterMainDB tLPUWMasterMainDB;
		tLPUWMasterMainSchema = (LPUWMasterMainSchema) mInputData
				.getObjectByObjectName("LPUWMasterMainSchema", 0);

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB tLPEdorItemDB;
		tLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		// 保全核保工作流相关数据表add by sxy
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionSchema = (LWMissionSchema) mInputData.getObjectByObjectName(
				"LWMissionSchema", 0);

		LBMissionSchema tLBMissionSchema = new LBMissionSchema();
		LBMissionDB tLBMissionDB = new LBMissionDB();
		tLBMissionSchema = (LBMissionSchema) mInputData.getObjectByObjectName(
				"LBMissionSchema", 0);

		// 保全工作流特约数据表add by sxy
		LPSpecSchema tLPSpecSchema = new LPSpecSchema();
		LPSpecDB tLPSpecDB = new LPSpecDB();
		tLPSpecSchema = (LPSpecSchema) mInputData.getObjectByObjectName(
				"LPSpecSchema", 0);

		// 新增附加险, add by Minim
		LCPolDBSet tLCPolDBSet;
		LCPolSet tLCPolSet = (LCPolSet) mInputData.getObjectByObjectName(
				"LCPolSet", 0);

		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);

		logger.debug("indeldata");

		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAppCancelBLS";
			tError.functionName = "delEdor";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		try {
			conn.setAutoCommit(false);
			tLPPolDB = new LPPolDB(conn);
			tLPPolDB.setSchema(tLPPolSchema);

			if (!tLPPolDB.deleteSQL()) { // @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人保单表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			// 新增附加险, add by Minim
			if ((tTransferData != null)
					&& (tLCPolSet != null)
					&& ((String) tTransferData.getValueByName("SPECIAL"))
							.equals("NS")) {
				tLCPolDBSet = new LCPolDBSet(conn);
				tLCPolDBSet.set(tLCPolSet);

				if (!tLCPolDBSet.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "EdorAppCancelBLS";
					tError.functionName = "delEdor";
					tError.errorMessage = "个人保单表删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();

					return false;
				}
			}

			tLPPremDB = new LPPremDB(conn);
			tLPPremDB.setSchema(tLPPremSchema);

			if (!tLPPremDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "保费项表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPPersonDB = new LPPersonDB(conn);
			tLPPersonDB.setSchema(tLPPersonSchema);

			if (!tLPPersonDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人信息表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPDutyDB = new LPDutyDB(conn);
			tLPDutyDB.setSchema(tLPDutySchema);

			if (!tLPDutyDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "保险责任表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPGetDB = new LPGetDB(conn);
			tLPGetDB.setSchema(tLPGetSchema);

			if (!tLPGetDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "领取项表删除失败!";
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
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "借款表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPReturnLoanDB = new LPReturnLoanDB(conn);
			tLPReturnLoanDB.setSchema(tLPReturnLoanSchema);

			if (!tLPReturnLoanDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "还款表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPCustomerImpartDB = new LPCustomerImpartDB(conn);
			tLPCustomerImpartDB.setSchema(tLPCustomerImpartSchema);

			if (!tLPCustomerImpartDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "客户告知表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPAppntIndDB = new LPAppntIndDB(conn);
			tLPAppntIndDB.setSchema(tLPAppntIndSchema);

			if (!tLPAppntIndDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "多投保人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPInsuredDB = new LPInsuredDB(conn);
			tLPInsuredDB.setSchema(tLPInsuredSchema);

			if (!tLPInsuredDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "多被保人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPBnfDB = new LPBnfDB(conn);
			tLPBnfDB.setSchema(tLPBnfSchema);

			if (!tLPBnfDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "受益人表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPMoveDB = new LPMoveDB(conn);
			tLPMoveDB.setSchema(tLPMoveSchema);

			if (!tLPMoveDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "转移表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB(conn);
			tLPInsureAccDB.setSchema(tLPInsureAccSchema);

			if (!tLPInsureAccDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "转移表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB(
					conn);
			tLPInsureAccTraceDB.setSchema(tLPInsureAccTraceSchema);

			if (!tLPInsureAccTraceDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "转移表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			LPAppntTraceDB tLPAppntTraceDB = new LPAppntTraceDB(conn);
			tLPAppntTraceDB.setSchema(tLPAppntTraceSchema);

			if (!tLPAppntTraceDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "变更投保人轨迹表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			LJSPayDB tLJSPayDB = new LJSPayDB(conn);
			tLJSPayDB.setSchema(tLJSPaySchema);

			if (!tLJSPayDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "转移表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			LJSGetDB tLJSGetDB = new LJSGetDB(conn);
			tLJSGetDB.setSchema(tLJSGetSchema);

			if (!tLJSGetDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "转移表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB(conn);
			tLJSGetEndorseDB.setSchema(tLJSGetEndorseSchema);

			if (!tLJSGetEndorseDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "转移表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPUWErrorDB = new LPUWErrorDB(conn);
			tLPUWErrorDB.setSchema(tLPUWErrorSchema);

			if (!tLPUWErrorDB.deleteSQL()) { // @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人保全核保错误信息表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPUWSubDB = new LPUWSubDB(conn);
			tLPUWSubDB.setSchema(tLPUWSubSchema);

			if (!tLPUWSubDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人保全核保轨迹表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPUWMasterDB = new LPUWMasterDB(conn);
			tLPUWMasterDB.setSchema(tLPUWMasterSchema);

			if (!tLPUWMasterDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorUWCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人保全核保最近结果表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPUWMasterMainDB = new LPUWMasterMainDB(conn);
			tLPUWMasterMainDB.setSchema(tLPUWMasterMainSchema);

			if (!tLPUWMasterMainDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorUWCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人保全核保主表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			tLPEdorItemDB = new LPEdorItemDB(conn);
			tLPEdorItemDB.setSchema(tLPEdorItemSchema);

			if (!tLPEdorItemDB.deleteSQL()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorAppCancelBLS";
				tError.functionName = "delEdor";
				tError.errorMessage = "个人批改主表删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();

				return false;
			}

			if (tLWMissionSchema != null) {
				tLWMissionDB = new LWMissionDB(conn);
				tLWMissionDB.setSchema(tLWMissionSchema);

				if (!tLWMissionDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "EdorAppCancelBLS";
					tError.functionName = "delEdor";
					tError.errorMessage = "保全核保工作流相关数据表!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();

					return false;
				}
			}

			if (tLBMissionSchema != null) {
				tLBMissionDB = new LBMissionDB(conn);
				tLBMissionDB.setSchema(tLBMissionSchema);

				if (!tLBMissionDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "EdorAppCancelBLS";
					tError.functionName = "delEdor";
					tError.errorMessage = "保全核保工作流相关数据B表!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();

					return false;
				}
			}

			// 保全工作流特约数据表
			if (tLPSpecSchema != null) {
				tLPSpecDB = new LPSpecDB(conn);
				tLPSpecDB.setSchema(tLPSpecSchema);

				if (!tLPSpecDB.deleteSQL()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "EdorAppCancelBLS";
					tError.functionName = "delEdor";
					tError.errorMessage = "保全工作流特约数据表!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();

					return false;
				}
			}

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorAppCancelBLS";
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
