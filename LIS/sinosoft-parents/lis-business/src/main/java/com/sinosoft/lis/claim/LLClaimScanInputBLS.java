/*
 * <p>ClassName: LLClaimScanInputBLS </p>
 * <p>Description: LLClaimScanInputBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证补扫
 * @CreateDate：2005-08-28
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.Es_IssueDocDB;
import com.sinosoft.lis.schema.Es_IssueDocSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class LLClaimScanInputBLS {
private static Logger logger = Logger.getLogger(LLClaimScanInputBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	private String mOperate;

	public LLClaimScanInputBLS() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Start LLClaimScanInputBLS Submit...");
		if (this.mOperate.equals("INSERT")) {
			tReturn = saveEs_IssueDoc(cInputData);
		}
		/*
		 * if (this.mOperate.equals("DELETE||MAIN"))
		 * {tReturn=deleteLATrain(cInputData);} if
		 * (this.mOperate.equals("UPDATE||MAIN"))
		 * {tReturn=updateLATrain(cInputData);}
		 */
		if (tReturn)
			logger.debug(" sucessful");
		else
			logger.debug("Save failed");
		logger.debug("End LLClaimScanInputBLS Submit...");
		return tReturn;
	}

	/**
	 * 保存函数
	 */
	private boolean saveEs_IssueDoc(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn;
		conn = null;
		conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start 保存...");
			Es_IssueDocDB tEs_IssueDocDB = new Es_IssueDocDB(conn);
			tEs_IssueDocDB.setSchema((Es_IssueDocSchema) mInputData
					.getObjectByObjectName("Es_IssueDocSchema", 0));
			if (!tEs_IssueDocDB.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tEs_IssueDocDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimScanInputBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimScanInputBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			tReturn = false;
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			;
		}
		try {
			conn.close();
		} catch (Exception e) {
		}
		;
		return tReturn;
	}

	/**
	 * 保存函数
	 */
	/*
	 * private boolean deleteLATrain(VData mInputData) { boolean tReturn =true;
	 * logger.debug("Start Save..."); Connection conn; conn=null;
	 * conn=DBConnPool.getConnection(); if (conn==null) { // @@错误处理 CError
	 * tError = new CError(); tError.moduleName = "ALATrainBLS";
	 * tError.functionName = "saveData"; tError.errorMessage = "数据库连接失败!";
	 * this.mErrors .addOneError(tError) ; return false; } try{
	 * conn.setAutoCommit(false); logger.debug("Start 保存..."); LATrainDB
	 * tLATrainDB=new LATrainDB(conn);
	 * tLATrainDB.setSchema((LATrainSchema)mInputData.getObjectByObjectName("LATrainSchema",0));
	 * if (!tLATrainDB.delete()) { // @@错误处理
	 * this.mErrors.copyAllErrors(tLATrainDB.mErrors); CError tError = new
	 * CError(); tError.moduleName = "ALATrainBLS"; tError.functionName =
	 * "saveData"; tError.errorMessage = "数据删除失败!"; this.mErrors
	 * .addOneError(tError) ; conn.rollback(); conn.close(); return false; }
	 * conn.commit() ; conn.close(); } catch (Exception ex) { // @@错误处理 CError
	 * tError =new CError(); tError.moduleName="ALATrainBLS";
	 * tError.functionName="submitData"; tError.errorMessage=ex.toString();
	 * this.mErrors .addOneError(tError); tReturn=false; try{conn.rollback() ;}
	 * catch(Exception e){} } try{conn.close();}catch(Exception e){}; return
	 * tReturn; }
	 * 
	 * 
	 * /** 保存函数
	 */
	/*
	 * private boolean updateLATrain(VData mInputData) { boolean tReturn =true;
	 * logger.debug("Start Save..."); Connection conn; conn=null;
	 * conn=DBConnPool.getConnection(); if (conn==null) { CError tError = new
	 * CError(); tError.moduleName = "ALATrainBLS"; tError.functionName =
	 * "updateData"; tError.errorMessage = "数据库连接失败!"; this.mErrors
	 * .addOneError(tError) ; return false; } try{ conn.setAutoCommit(false);
	 * logger.debug("Start 保存..."); LATrainDB tLATrainDB=new
	 * LATrainDB(conn);
	 * tLATrainDB.setSchema((LATrainSchema)mInputData.getObjectByObjectName("LATrainSchema",0));
	 * if (!tLATrainDB.update()) { // @@错误处理
	 * this.mErrors.copyAllErrors(tLATrainDB.mErrors); CError tError = new
	 * CError(); tError.moduleName = "ALATrainBLS"; tError.functionName =
	 * "saveData"; tError.errorMessage = "数据保存失败!"; this.mErrors
	 * .addOneError(tError) ; conn.rollback(); conn.close(); return false; }
	 * conn.commit() ; conn.close(); } catch (Exception ex) { // @@错误处理 CError
	 * tError =new CError(); tError.moduleName="ALATrainBLS";
	 * tError.functionName="submitData"; tError.errorMessage=ex.toString();
	 * this.mErrors .addOneError(tError); tReturn=false; try{conn.rollback() ;}
	 * catch(Exception e){} } try{conn.close();}catch(Exception e){}; return
	 * tReturn; }
	 */
}
