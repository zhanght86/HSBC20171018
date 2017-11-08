package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import com.sinosoft.lis.vdb.LJSPayDBSet;
import com.sinosoft.lis.vdb.LYBankLogDBSet;
import com.sinosoft.lis.vdb.LYSendToBankDBSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 业务数据转换到银行系统
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

public class GetSendToBankBLS {
private static Logger logger = Logger.getLogger(GetSendToBankBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务数据
	private LJSPaySet inLJSPaySet = new LJSPaySet();
	private LYSendToBankSet inLYSendToBankSet = new LYSendToBankSet();
	private LYBankLogSet inLYBankLogSet = new LYBankLogSet();

	public GetSendToBankBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
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
		if (this.mOperate.equals("GETMONEY")
				|| this.mOperate.equals("GETMONEY1")) {
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
			inLJSPaySet = (LJSPaySet) mInputData.getObjectByObjectName(
					"LJSPaySet", 0);
			inLYSendToBankSet = (LYSendToBankSet) mInputData
					.getObjectByObjectName("LYSendToBankSet", 0);
			inLYBankLogSet = (LYBankLogSet) mInputData.getObjectByObjectName(
					"LYBankLogSet", 0);
			// mStringSql = (String) mInputData.getObjectByObjectName(
			// "String", 1);
			// logger.debug(":::::::::::::"+mStringSql+":::::::::::::::");
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankBLS";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		logger.debug("Start Save...");

		// 建立数据库连接
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);
			// 生成送银行数据，插入送银行盘记录表（LYSendToBank）
			// logger.debug("inLYSendToBankSet:" + inLYSendToBankSet.size() +
			// inLYSendToBankSet.encode());
			LYSendToBankDBSet tLYSendToBankDBSet = new LYSendToBankDBSet(conn);
			tLYSendToBankDBSet.set(inLYSendToBankSet);
			if (!tLYSendToBankDBSet.insert()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				CError.buildErr(this, "LYSendToBank Insert Failed");
				return false;
			}

			// 用来判断是否数据中已经有参加置返盘的数据。（防止第二次点击生成文件）

			String tSql = "select * from lysendtobank a, ljspay b where a.serialNo= '"
					+ "?serialNo?"
					+ "' and a.paycode = b.getnoticeno and b.bankonthewayflag = '1'";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(tSql);
            sqlbv1.put("serialNo", inLYSendToBankSet.get(1).getSerialNo());
			ExeSQL tjExeSQL = new ExeSQL(conn);
			SSRS tjSSRS = new SSRS();
			tjSSRS = tjExeSQL.execSQL(sqlbv1);
			logger.debug("别被的操作挂起的保单为" + tjSSRS.getMaxRow());
			if (tjSSRS.getMaxRow() > 0) {
				conn.rollback();
				conn.close();
				CError.buildErr(this, "由于您的重复选择。您现在所选的条件已经有批次在置盘，请稍候去生成文件！");
				return false;

			}

			// 修改总应收表的银行在途标志
			// logger.debug("inLJSPaySet:" + inLJSPaySet.size());
			LJSPayDBSet tLJSPayDBSet = new LJSPayDBSet(conn);
			tLJSPayDBSet.set(inLJSPaySet);
			if (!tLJSPayDBSet.update()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				CError.buildErr(this, "LJSPay Update Failed");
				return false;
			}

			// 记录银行业务日志，插入新日志数据
			LYBankLogDBSet tLYBankLogDBSet = new LYBankLogDBSet(conn);
			tLYBankLogDBSet.set(inLYBankLogSet);
			if (!tLYBankLogDBSet.insert()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				CError.buildErr(this,"LYBankLog Insert Failed");
				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
			CError.buildErr(this, ex.getMessage());
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		GetSendToBankBLS getSendToBankBLS1 = new GetSendToBankBLS();
	}
}
