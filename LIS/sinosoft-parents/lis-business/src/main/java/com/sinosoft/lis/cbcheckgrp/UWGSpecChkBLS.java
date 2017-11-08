package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保团体人工核保条件承保录入
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

public class UWGSpecChkBLS {
private static Logger logger = Logger.getLogger(UWGSpecChkBLS.class);
	// 是否存在需要人工核保保单
	int merrno = 0;
	// 传输数据类
	private VData mInputData;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	//
	// private LCUWErrorDB mLCUWErrorDB = new LCUWErrorDB(conn);
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

	public UWGSpecChkBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start UWGSpecChkBLS Submit...");
		if (!this.saveData()) {
			return false;
		}
		logger.debug("End UWGSpecChkBLS Submit...");

		mInputData = null;
		return true;
	}

	private boolean saveData() {

		LCSpecSet mLCSpecSet = (LCSpecSet) mInputData.getObjectByObjectName(
				"LCSpecSet", 0);
		LCGUWMasterSet mLCGUWMasterSet = (LCGUWMasterSet) mInputData
				.getObjectByObjectName("LCGUWMasterSet", 0);
		LCGUWSubSet mLCGUWSubSet = (LCGUWSubSet) mInputData
				.getObjectByObjectName("LCGUWSubSet", 0);
		LCPolSet mLCPolSet = (LCPolSet) mInputData.getObjectByObjectName(
				"LCPolSet", 0);
		LCGrpPolSet mLCGrpPolSet = (LCGrpPolSet) mInputData
				.getObjectByObjectName("LCGrpPolSet", 0);
		LCPremSet mLCPremSet = (LCPremSet) mInputData.getObjectByObjectName(
				"LCPremSet", 0);
		LCDutySet mLCDutySet = (LCDutySet) mInputData.getObjectByObjectName(
				"LCDutySet", 0);
		LCUWMasterSet mLCUWMasterSet = (LCUWMasterSet) mInputData
				.getObjectByObjectName("LCUWMasterSet", 0);
		LCUWSubSet mLCUWSubSet = (LCUWSubSet) mInputData.getObjectByObjectName(
				"LCUWSubSet", 0);

		try {
			Connection conn = DBConnPool.getConnection();

			if (conn == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "UWGSpecChkBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据库连接失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			conn.setAutoCommit(false);

			// 核保主表
			if (mLCGUWMasterSet.size() > 0) {
				LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
				LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB(conn);

				tLCGUWMasterSchema = mLCGUWMasterSet.get(1);

				tLCGUWMasterDB.setSchema(tLCGUWMasterSchema);
				if (tLCGUWMasterDB.update() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWGSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "核保主表保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 核保轨迹表
			if (mLCGUWSubSet.size() > 0) {
				LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
				LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB(conn);

				tLCGUWSubSchema = mLCGUWSubSet.get(1);

				tLCGUWSubDB.setSchema(tLCGUWSubSchema);
				if (tLCGUWSubDB.insert() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWGSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "核保轨迹表保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 特别约定表
			if (mLCSpecSet.size() > 0) {
				for (int j = 1; j <= mLCSpecSet.size(); j++) {
					LCSpecSchema tLCSpecSchema = new LCSpecSchema();
					tLCSpecSchema = mLCSpecSet.get(j);
					LCSpecDB tLCSpecDB = new LCSpecDB(conn);
					tLCSpecDB.setSchema(tLCSpecSchema);
					if (tLCSpecDB.insert() == false) {
						// @@错误处理
						this.mErrors.copyAllErrors(tLCSpecDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "UWGSpecChkBLS";
						tError.functionName = "saveData";
						tError.errorMessage = "特别约定表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}

			} // end of for

			// 个人保单表
			if (mLCPolSet.size() > 0) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				LCPolDB tLCPolDB = new LCPolDB(conn);

				tLCPolSchema = mLCPolSet.get(1);
				tLCPolDB.setSchema(tLCPolSchema);
				if (tLCPolDB.update() == false) {
					this.mErrors.copyAllErrors(tLCPolDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "个人保单表删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;

				}
			}

			// 集体保单表
			if (mLCGrpPolSet.size() > 0) {
				LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB(conn);

				tLCGrpPolSchema = mLCGrpPolSet.get(1);
				tLCGrpPolDB.setSchema(tLCGrpPolSchema);
				if (tLCGrpPolDB.update() == false) {
					this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "集体保单表删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;

				}
			}

			// 保费项目表
			if (mLCPremSet.size() > 0) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				LCPremDB tLCPremDB = new LCPremDB(conn);

				tLCPremSchema = mLCPremSet.get(1);
				tLCPremDB.setPolNo(tLCPremSchema.getPolNo());

				if (tLCPremDB.deleteSQL() == false) {
					this.mErrors.copyAllErrors(tLCPremDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "保费项目表删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				for (int i = 1; i <= mLCPremSet.size(); i++) {
					tLCPremSchema = new LCPremSchema();
					tLCPremDB = new LCPremDB(conn);

					tLCPremSchema = mLCPremSet.get(i);
					tLCPremDB.setSchema(tLCPremSchema);

					if (tLCPremDB.insert() == false) {
						this.mErrors.copyAllErrors(tLCPremDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "UWSpecChkBLS";
						tError.functionName = "saveData";
						tError.errorMessage = "保费项目表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
			}
			// 保险责任表
			if (mLCDutySet.size() > 0) {
				for (int i = 1; i <= mLCDutySet.size(); i++) {
					LCDutySchema tLCDutySchema = new LCDutySchema();
					LCDutyDB tLCDutyDB = new LCDutyDB(conn);

					tLCDutySchema = mLCDutySet.get(i);
					tLCDutyDB.setSchema(tLCDutySchema);

					if (tLCDutyDB.update() == false) {
						this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "UWSpecChkBLS";
						tError.functionName = "saveData";
						tError.errorMessage = "保费责任表保存失败!";
						this.mErrors.addOneError(tError);
						conn.rollback();
						conn.close();
						return false;
					}
				}
			}

			// 个人核保主表
			if (mLCUWMasterSet.size() > 0) {
				LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
				LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB(conn);

				tLCUWMasterSchema = mLCUWMasterSet.get(1);

				tLCUWMasterDB.setSchema(tLCUWMasterSchema);
				if (tLCUWMasterDB.update() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "核保主表保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 个人核保轨迹表
			if (mLCUWSubSet.size() > 0) {
				LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
				LCUWSubDB tLCUWSubDB = new LCUWSubDB(conn);

				tLCUWSubSchema = mLCUWSubSet.get(1);

				tLCUWSubDB.setSchema(tLCUWSubSchema);
				if (tLCUWSubDB.insert() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWSpecChkBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "个人核保轨迹表保存失败!";
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
			tError.moduleName = "UWGSpecChkBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			// try{conn.rollback() ;} catch(Exception e){}
			return false;
		}

		return true;
	}

}
