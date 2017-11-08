package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LJAGetDBSet;
import com.sinosoft.lis.vdb.LJAGetTempFeeDBSet;
import com.sinosoft.lis.vdb.LJTempFeeClassDBSet;
import com.sinosoft.lis.vdb.LJTempFeeDBSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 暂交费退费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class TempFeeWithdrawBLS {
private static Logger logger = Logger.getLogger(TempFeeWithdrawBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private Connection conn = null;

	public TempFeeWithdrawBLS() {
	}

	public void setConnection(Connection c) {
		conn = c;
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 信息保存
		if (this.mOperate.equals("INSERT")) {
			tReturn = save(cInputData);
		}

		if (tReturn) {
			LJAGetSet tLJAGetSet = (LJAGetSet) cInputData
					.getObjectByObjectName("LJAGetSet", 0);
			mResult.add("0|" + tLJAGetSet.size() + "^" + tLJAGetSet.encode());
			logger.debug("Save sucessful");
		} else
			logger.debug("Save failed");

		return tReturn;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");

		if (conn == null)
			conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			conn.setAutoCommit(false);

			// 暂交费退费实付表
			logger.debug("Start 暂交费退费实付表...");
			LJAGetTempFeeDBSet tLJAGetTempFeeDBSet = new LJAGetTempFeeDBSet(
					conn);
			tLJAGetTempFeeDBSet.set((LJAGetTempFeeSet) mInputData
					.getObjectByObjectName("LJAGetTempFeeSet", 0));

			if (!tLJAGetTempFeeDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAGetTempFeeDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LJAGetTempFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "暂交费退费实付表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 暂交费表
			logger.debug("Start 暂交费表...");
			LJTempFeeDBSet tLJTempFeeDBSet = new LJTempFeeDBSet(conn);
			tLJTempFeeDBSet.set((LJTempFeeSet) mInputData
					.getObjectByObjectName("LJTempFeeSet", 0));
			if (!tLJTempFeeDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJTempFeeDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LJTempFeeBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "暂交费表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 暂交费分类表
			logger.debug("Start 暂交费分类表...");
			LJTempFeeClassDBSet tLJTempFeeClassDBSet = new LJTempFeeClassDBSet(
					conn);
			tLJTempFeeClassDBSet.set((LJTempFeeClassSet) mInputData
					.getObjectByObjectName("LJTempFeeClassSet", 0));
			if (!tLJTempFeeClassDBSet.update()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJTempFeeClassDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LJTempFeeClassBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "暂交费分类表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 实付总表
			logger.debug("Start 实付总表...");
			LJAGetDBSet tLJAGetDBSet = new LJAGetDBSet(conn);
			tLJAGetDBSet.set((LJAGetSet) mInputData.getObjectByObjectName(
					"LJAGetSet", 0));
			if (!tLJAGetDBSet.insert()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLJAGetDBSet.mErrors);
				CError tError = new CError();
				tError.moduleName = "LJAGetBLS";
				tError.functionName = "saveData";
				tError.errorMessage = "实付总表数据保存失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}
			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TempFeeWithdrawBLS";
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
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		TempFeeWithdrawBLS tempFeeWithdrawBLS1 = new TempFeeWithdrawBLS();
	}
}
