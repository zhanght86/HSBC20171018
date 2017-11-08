package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 删除发送银行数据和暂交费数据</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import java.sql.Connection;

import com.sinosoft.lis.vdb.LBTempFeeClassDBSet;
import com.sinosoft.lis.vdb.LBTempFeeDBSet;
import com.sinosoft.lis.vdb.LJSPayDBSet;
import com.sinosoft.lis.vdb.LJTempFeeClassDBSet;
import com.sinosoft.lis.vdb.LJTempFeeDBSet;
import com.sinosoft.lis.vschema.LBTempFeeClassSet;
import com.sinosoft.lis.vschema.LBTempFeeSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class DeleteSendToBankBLS {
private static Logger logger = Logger.getLogger(DeleteSendToBankBLS.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 传入的业务数据 */
	private LJSPaySet inLJSPaySet = new LJSPaySet();
	private LJTempFeeSet inLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet inLJTempFeeClassSet = new LJTempFeeClassSet();
	private LBTempFeeSet inLBTempFeeSet = new LBTempFeeSet();
	private LBTempFeeClassSet inLBTempFeeClassSet = new LBTempFeeClassSet();

	public DeleteSendToBankBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"DELETE"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 保存信息
		if (!saveData()) {
			return false;
		}
		logger.debug("---End saveData---");

		return true;
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
			inLJTempFeeSet = (LJTempFeeSet) mInputData.getObjectByObjectName(
					"LJTempFeeSet", 0);
			inLJTempFeeClassSet = (LJTempFeeClassSet) mInputData
					.getObjectByObjectName("LJTempFeeClassSet", 0);
			inLBTempFeeSet = (LBTempFeeSet) mInputData.getObjectByObjectName(
					"LBTempFeeSet", 0);
			inLBTempFeeClassSet = (LBTempFeeClassSet) mInputData
					.getObjectByObjectName("LBTempFeeClassSet", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "DeleteSendToBankBLS";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据库操作
	 * 
	 * @return: boolean
	 */
	private boolean saveData() {
		logger.debug("---Start Save---");

		// 建立数据库连接
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "DeleteSendToBankBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			if (mOperate.equals("DELETE")) {
				// 删除暂交费分类表
				// logger.debug("inLJTempFeeClassSet:" +
				// inLJTempFeeClassSet.size() + " : " +
				// inLJTempFeeClassSet.encode());
				LJTempFeeClassDBSet tLJTempFeeClassDBSet = new LJTempFeeClassDBSet(
						conn);
				tLJTempFeeClassDBSet.set(inLJTempFeeClassSet);
				if (!tLJTempFeeClassDBSet.delete()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LJTempFeeClass Delete Failed");
					return false;
				}

				// 删除应收总表
				// logger.debug("inLJSPaySet:" + inLJSPaySet.size() + " :
				// " + inLJSPaySet.encode());
				LJSPayDBSet tLJSPayDBSet = new LJSPayDBSet(conn);
				tLJSPayDBSet.set(inLJSPaySet);
				if (!tLJSPayDBSet.delete()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LJSPay Delete Failed");
					return false;
				}

				// 删除暂交费表
				// logger.debug("inLJTempFeeSet:" + inLJTempFeeSet.size()
				// + " : " + inLJTempFeeSet.encode());
				LJTempFeeDBSet tLJTempFeeDBSet = new LJTempFeeDBSet(conn);
				tLJTempFeeDBSet.set(inLJTempFeeSet);
				if (!tLJTempFeeDBSet.delete()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LJTempFee Delete Failed");
					return false;
				}

				// 插入暂交费备份表
				// logger.debug("inLBTempFeeSet:" + inLBTempFeeSet.size()
				// + " : " + inLBTempFeeSet.encode());
				LBTempFeeDBSet tLBTempFeeDBSet = new LBTempFeeDBSet(conn);
				tLBTempFeeDBSet.set(inLBTempFeeSet);
				if (!tLBTempFeeDBSet.insert()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LBTempFee Insert Failed");
					return false;
				}

				// 插入暂交费分类备份表
				// logger.debug("inLBTempFeeClassSet:" +
				// inLBTempFeeClassSet.size() + " : " +
				// inLBTempFeeClassSet.encode());
				LBTempFeeClassDBSet tLBTempFeeClassDBSet = new LBTempFeeClassDBSet(
						conn);
				tLBTempFeeClassDBSet.set(inLBTempFeeClassSet);
				if (!tLBTempFeeClassDBSet.insert()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LBTempFeeClass Insert Failed");
					return false;
				}
			} else if (mOperate.equals("UPDATE")) {
				// 更新应收总表
				// logger.debug("inLJSPaySet:" + inLJSPaySet.size() + " :
				// " + inLJSPaySet.encode());
				LJSPayDBSet tLJSPayDBSet = new LJSPayDBSet(conn);
				tLJSPayDBSet.set(inLJSPaySet);
				if (!tLJSPayDBSet.update()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LJSPay Update Failed");
					return false;
				}
			}

			conn.commit();
			conn.close();
			logger.debug("---End Committed---");
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "DeleteSendToBankBLS";
			tError.functionName = "submitData";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
			} catch (Exception ex) {
			}
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		DeleteSendToBankBLS DeleteSendToBankBLS1 = new DeleteSendToBankBLS();
	}
}
