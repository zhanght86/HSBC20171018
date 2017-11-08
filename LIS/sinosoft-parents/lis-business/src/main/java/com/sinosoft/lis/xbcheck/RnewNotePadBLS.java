package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.RnewNotePadDBSet;
import com.sinosoft.lis.vschema.RnewNotePadSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class RnewNotePadBLS {
private static Logger logger = Logger.getLogger(RnewNotePadBLS.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 传入的业务数据 */
	private RnewNotePadSet inRnewNotePadSet = new RnewNotePadSet();

	public RnewNotePadBLS() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
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
			inRnewNotePadSet = (RnewNotePadSet) mInputData.getObjectByObjectName(
					"RnewNotePadSet", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "UWNotePadBLS";
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
			tError.moduleName = "UWNotePadBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			if (mOperate.equals("INSERT")) {
				// 新增核保记事本
				RnewNotePadDBSet tRnewNotePadDBSet = new RnewNotePadDBSet(conn);
				tRnewNotePadDBSet.set(inRnewNotePadSet);

				if (!tRnewNotePadDBSet.insert()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}

					conn.close();
					logger.debug("RnewNotePad Insert Failed");

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
			tError.moduleName = "UWNotePadBLS";
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
		RnewNotePadBLS UWNotePadBLS1 = new RnewNotePadBLS();
	}
}
