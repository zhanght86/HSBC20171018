package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LJAPayBL;
import com.sinosoft.lis.bl.LJSPayBL;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.vdb.LCDutyDBSet;
import com.sinosoft.lis.vdb.LCInsureAccDBSet;
import com.sinosoft.lis.vdb.LCInsureAccTraceDBSet;
import com.sinosoft.lis.vdb.LCPremDBSet;
import com.sinosoft.lis.vdb.LJAPayPersonDBSet;
import com.sinosoft.lis.vdb.LJSPayPersonDBSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
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
 * @author HZM
 * @version 1.0
 */

public class AutoDueFeeMultiBLS {
private static Logger logger = Logger.getLogger(AutoDueFeeMultiBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public AutoDueFeeMultiBLS() {
	}

	public static void main(String[] args) {
		AutoDueFeeMultiBLS mAutoDueFeeMultiBLS1 = new AutoDueFeeMultiBLS();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Start IndiFinUrgeVerify BLS Submit...");
		// 信息保存
		if (this.mOperate.equals("VERIFY")) {
			tReturn = verify(cInputData);
		}

		if (tReturn)
			logger.debug("Verify sucessful");
		else
			logger.debug("Verify failed");

		logger.debug("End IndiFinUrgeVerify BLS Submit...");

		return tReturn;
	}

	// 核销事务操作
	private boolean verify(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Verify...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBLS";
			tError.functionName = "verifyData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			// 实收总表添加
			logger.debug("Start 实收总表...");
			LJAPayDB tLJAPayDB = new LJAPayDB(conn);
			tLJAPayDB.setSchema((LJAPayBL) mInputData.getObjectByObjectName(
					"LJAPayBL", 0));
			if (!tLJAPayDB.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实收总表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("实收总表添加");

			// 实收个人交费表添加
			logger.debug("Start 实收个人交费表...");
			LJAPayPersonDBSet tLJAPayPersonDBSet = new LJAPayPersonDBSet(conn);
			tLJAPayPersonDBSet.set((LJAPayPersonSet) mInputData
					.getObjectByObjectName("LJAPayPersonSet", 0));
			if (!tLJAPayPersonDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实收个人交费表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("实收个人交费表添加");

			// 个人保单表更新
			logger.debug("Start 个人保单表...");
			LCPolDB tLCPolDB = new LCPolDB(conn);
			tLCPolDB.setSchema((LCPolBL) mInputData.getObjectByObjectName(
					"LCPolBL", 0));
			if (!tLCPolDB.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "updateData";
				tError.errorMessage = "个人保单表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("个人保单表更新");

			// 保费项表更新
			logger.debug("Start 保费项表...");
			LCPremDBSet tLCPremDBSet = new LCPremDBSet(conn);
			tLCPremDBSet.set((LCPremSet) mInputData.getObjectByObjectName(
					"LCPremSet", 0));
			if (!tLCPremDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPremDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "保费项表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("保费项表添加");

			// LCDuty 保险责任表更新
			logger.debug("Start 保险责任表...");
			LCDutyDBSet tLCDutyDBSet = new LCDutyDBSet(conn);
			tLCDutyDBSet.set((LCDutySet) mInputData.getObjectByObjectName(
					"LCDutySet", 0));
			if (!tLCDutyDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "IndiNormalPayVerifyBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "保险责任表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("保险责任表添加");
			// 删除应收总表
			logger.debug("Start 删除应收总表...");
			LJSPayDB tLJSPayDB = new LJSPayDB(conn);
			tLJSPayDB.setSchema((LJSPayBL) mInputData.getObjectByObjectName(
					"LJSPayBL", 0));
			logger.debug("Delete LJSPay");
			if (!tLJSPayDB.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJSPayDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "deleteData";
				tError.errorMessage = "应收总表数据删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("删除应收总表");

			// 删除应收个人交费表
			logger.debug("Start 删除个人交费表...");
			LJSPayPersonDBSet tLJSPayPersonDBSet = new LJSPayPersonDBSet(conn);
			tLJSPayPersonDBSet.set((LJSPayPersonSet) mInputData
					.getObjectByObjectName("LJSPayPersonSet", 0));
			if (!tLJSPayPersonDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJSPayPersonDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "deleteData";
				tError.errorMessage = "应收个人交费表数据删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			logger.debug("删除应收个人交费表");

			// 删除保险帐户表
			logger.debug("Start 保险帐户表...");
			LCInsureAccDBSet tLCInsureAccDBSet = new LCInsureAccDBSet(conn);
			tLCInsureAccDBSet.set((LCInsureAccSet) mInputData
					.getObjectByObjectName("LCInsureAccSet", 0));
			if (!tLCInsureAccDBSet.delete()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "deleteData";
				tError.errorMessage = "保险帐户表数据删除失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("删除保险帐户表");

			// 插入保险帐户表
			logger.debug("Start 保险帐户表...");
			LCInsureAccDBSet pLCInsureAccDBSet = new LCInsureAccDBSet(conn);
			pLCInsureAccDBSet.set((LCInsureAccSet) mInputData
					.getObjectByObjectName("LCInsureAccSet", 0));
			if (!pLCInsureAccDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(pLCInsureAccDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "deleteData";
				tError.errorMessage = "插入保险帐户表失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("插入保险帐户表");

			// 插入保险帐户表记价履历表
			logger.debug("Start 保险帐户表记价履历表...");
			LCInsureAccTraceDBSet pLCInsureAccTraceDBSet = new LCInsureAccTraceDBSet(
					conn);
			pLCInsureAccTraceDBSet.set((LCInsureAccTraceSet) mInputData
					.getObjectByObjectName("LCInsureAccTraceSet", 0));
			if (!pLCInsureAccTraceDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(pLCInsureAccTraceDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "AutoDueFeeMultiBLS";
				tError.functionName = "deleteData";
				tError.errorMessage = "插入保险帐户表记价履历表失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			logger.debug("插入保险帐户表记价履历表");

			/*
			 * 预留 //单证回收（暂交费的回收） GlobalInput tGI = new GlobalInput();
			 * tGI=(GlobalInput)mInputData.getObjectByObjectName("GlobalInput",0);
			 * PubCertifyTakeBack tPubCertifyTakeBack=new PubCertifyTakeBack();
			 * if(!tPubCertifyTakeBack.CertifyTakeBack_A(tLJTempFeeDB.getTempFeeNo(),tLJTempFeeDB.getTempFeeNo(),tLCPolDB.getAgentCode(),"1",tGI)) {
			 * this.mErrors .copyAllErrors (tPubCertifyTakeBack.mErrors);
			 * try{conn.rollback() ;} catch(Exception e){} return false; }
			 */
			conn.commit();
			conn.close();

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoDueFeeMultiBLS";
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

	/**
	 * 
	 * @param CertifyNo
	 * @param AgentCode
	 * @param CertifyType
	 * @return
	 */
	private boolean CertifyTakeBack(String CertifyNo, String AgentCode,
			String CertifyType) {

		return true;
	}

}
