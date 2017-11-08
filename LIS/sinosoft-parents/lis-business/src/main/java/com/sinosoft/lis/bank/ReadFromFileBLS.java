package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LYBankLogDBSet;
import com.sinosoft.lis.vdb.LYReturnFromBankDBSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYReturnFromBankSet;
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
 * Description: 银行文件转换到数据模块
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

public class ReadFromFileBLS {
private static Logger logger = Logger.getLogger(ReadFromFileBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务数据
	private LYReturnFromBankSet inLYReturnFromBankSet = new LYReturnFromBankSet();
	private LYBankLogSet inLYBankLogSet = new LYBankLogSet();
	private TransferData inTransferData = new TransferData();

	public ReadFromFileBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"READ"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;

		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// logger.debug("---End getInputData---");

		// 信息保存
		if (this.mOperate.equals("READ")) {
			tReturn = save(cInputData);
		}

		if (tReturn) {
			logger.debug("Save sucessful");
		} else {
			logger.debug("Save failed");
		}

		return tReturn;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLYReturnFromBankSet = (LYReturnFromBankSet) mInputData
					.getObjectByObjectName("LYReturnFromBankSet", 0);
			inLYBankLogSet = (LYBankLogSet) mInputData.getObjectByObjectName(
					"LYBankLogSet", 0);
			inTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReadFromFileBLS";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		int i;
		boolean tReturn = true;
		logger.debug("Start Save...");

		// 建立数据库连接
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReadFromFileBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			// 生成银行返回数据，插入银行返回盘记录表（LYReturnFromBank）
			// logger.debug("inLYReturnFromBank:" +
			// inLYReturnFromBank.size() + inLYReturnFromBank.encode());
			LYReturnFromBankDBSet tLYReturnFromBankDBSet = new LYReturnFromBankDBSet(
					conn);
			tLYReturnFromBankDBSet.set(inLYReturnFromBankSet);
			if (!tLYReturnFromBankDBSet.insert()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LYReturnFromBank Insert Failed");
				return false;
			}

			// 记录银行操作日志
			LYBankLogDBSet tLYBankLogDBSet = new LYBankLogDBSet(conn);
			tLYBankLogDBSet.set(inLYBankLogSet);
			if (!tLYBankLogDBSet.update()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LYBankLog Insert Failed");
				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ReadFromFileBLS";
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

	public static void main(String[] args) {
		ReadFromFileBLS writeToFileBLS1 = new ReadFromFileBLS();
	}
}
