package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LJAGetDBSet;
import com.sinosoft.lis.vdb.LJAGetTempFeeDBSet;
import com.sinosoft.lis.vdb.LJTempFeeDBSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
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
 * Description: 保险计划变更附加险中止退费处理
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

public class ChangePlanSubWithdrawBLS {
private static Logger logger = Logger.getLogger(ChangePlanSubWithdrawBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private LJAGetTempFeeSet inLJAGetTempFeeSet = new LJAGetTempFeeSet();
	private LJTempFeeSet inLJTempFeeSet = new LJTempFeeSet();
	private LJAGetSet inLJAGetSet = new LJAGetSet();
	private LJTempFeeSet inInsertLJTempFeeSet = new LJTempFeeSet();

	public ChangePlanSubWithdrawBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT||MAIN"和"INSERT||DETAIL"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 信息保存
		if (this.mOperate.equals("INSERT||MAIN")) {
			tReturn = save(cInputData);
		}

		if (tReturn)
			logger.debug("Save sucessful");
		else
			logger.debug("Save failed");

		return tReturn;
	}

	// 保存操作
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");

		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			// 生成暂交费退费实付数据
			LJAGetTempFeeDBSet tLJAGetTempFeeDBSet = new LJAGetTempFeeDBSet(
					conn);
			tLJAGetTempFeeDBSet.set(inLJAGetTempFeeSet);
			if (!tLJAGetTempFeeDBSet.insert()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LJAGetTempFee Insert Failed");
				return false;
			}
			logger.debug("End 生成暂交费退费实付数据 ...");

			// 更新暂交费表，完成退费
			LJTempFeeDBSet tLJTempFeeDBSet = new LJTempFeeDBSet(conn);
			tLJTempFeeDBSet.set(inLJTempFeeSet);
			if (!tLJTempFeeDBSet.update()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LJTempFee Insert Failed");
				return false;
			}
			logger.debug("End 更新暂交费表，完成退费 ...");

			// 生成实付总表数据
			LJAGetDBSet tLJAGetDBSet = new LJAGetDBSet(conn);
			tLJAGetDBSet.set(inLJAGetSet);
			if (!tLJAGetDBSet.insert()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LJAGet Insert Failed");
				return false;
			}
			logger.debug("End 生成实付总表数据 ...");

			// 新增暂交费表，并关联到该附险的主险
			LJTempFeeDBSet tLJTempFeeDBSet2 = new LJTempFeeDBSet(conn);
			tLJTempFeeDBSet2.set(inInsertLJTempFeeSet);
			if (!tLJTempFeeDBSet2.insert()) {
				try {
					conn.rollback();
				} catch (Exception e) {
				}
				conn.close();
				logger.debug("LJTempFee Insert Failed");
				return false;
			}
			logger.debug("End 新增暂交费表，并关联到该附险的主险 ...");

			conn.commit();
			conn.close();
			logger.debug("End Committed");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBLS";
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
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			inLJAGetTempFeeSet = (LJAGetTempFeeSet) mInputData
					.getObjectByObjectName("LJAGetTempFeeSet", 0);
			inLJTempFeeSet = (LJTempFeeSet) mInputData.getObjectByObjectName(
					"LJTempFeeSet", 1);
			inLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName(
					"LJAGetSet", 2);
			inInsertLJTempFeeSet = (LJTempFeeSet) mInputData
					.getObjectByObjectName("LJTempFeeSet", 3);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ChangePlanSubWithdrawBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 主方法，测试用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ChangePlanSubWithdrawBLS changePlanSubWithdrawBLS1 = new ChangePlanSubWithdrawBLS();
	}
}
