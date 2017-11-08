package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 无名单补名单签单核保、财务处理</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpAddNameSignBLS {
private static Logger logger = Logger.getLogger(GrpAddNameSignBLS.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 数据库连接 * */
	private Connection conn = null;

	public GrpAddNameSignBLS() {
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
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 保存信息
		if (!saveData())
			return false;
		logger.debug("---End saveData---");

		return true;
	}

	/**
	 * 共享数据库连接，需要统一事务时使用
	 * 
	 * @param c
	 */
	public void setConnection(Connection c) {
		this.conn = c;
	}

	/**
	 * 数据库操作
	 * 
	 * @return: boolean
	 */
	private boolean saveData() {
		logger.debug("---Start Save---");

		// 建立数据库连接
		if (conn == null)
			conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			String action = ""; // 操作方式，INSERT\UPDATE\DELETE
			String className = ""; // 类名
			Object o = null; // Schema或Set对象
			Object DBObject = null; // DB或DBSet对象
			Method m = null; // 方法
			Constructor constructor = null; // 构造函数
			Class[] parameterC = new Class[1]; // 调用方法的参数数组
			Object[] parameterO = new Object[1]; // 调用方法的对象数组

			// 通过TransferData来传递每个Schema或Set的数据库操作方式，约定使用
			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);

			for (int i = 0; i < mInputData.size(); i++) {
				// 获取操作对象Schema或Set
				o = mInputData.get(i);

				// 获取操作方式
				// logger.debug("Name:" +
				// mInputData.get(i).getClass().getName());
				// logger.debug("Object String:" +
				// mInputData.get(i).toString());
				action = (String) tTransferData.getValueByName(mInputData
						.get(i).toString());

				if (action == null)
					continue;
				logger.debug("\n" + o.getClass().getName()
						+ " Operate DB: " + action);

				// 构造相应的DB类名
				className = o.getClass().getName();
				if (className.endsWith("Schema")) {
					className = "com.sinosoft.lis.db."
							+ className.substring(
									className.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DB";
				} else if (className.endsWith("Set")) {
					className = "com.sinosoft.lis.vdb."
							+ className.substring(
									className.lastIndexOf(".") + 1, className
											.lastIndexOf("S")) + "DBSet";
				}
				Class DBClass = Class.forName(className);

				// 选择构造函数，构造相同事务的DB或DBSet对象
				parameterC[0] = Connection.class;
				constructor = DBClass.getConstructor(parameterC);
				parameterO[0] = conn;
				DBObject = constructor.newInstance(parameterO);

				// 给DB对象付值，将传入的Schema或Set对象的内容复制到DB中
				parameterC[0] = o.getClass();
				// 使用try模块来控制程序流程会降低效率
				// try {
				// m = DBObject.getClass().getMethod("setSchema", parameterC);
				// }
				// catch (Exception ex) {
				// m = DBObject.getClass().getMethod("set", parameterC);
				// }
				if (o.getClass().getName().endsWith("Schema")) {
					m = DBObject.getClass().getMethod("setSchema", parameterC);
				} else if (o.getClass().getName().endsWith("Set")) {
					m = DBObject.getClass().getMethod("set", parameterC);
				}
				parameterO[0] = o;
				m.invoke(DBObject, parameterO);

				// 进行数据库操作
				if (action.equals("INSERT")) {
					m = DBObject.getClass().getMethod("insert", null);
					Boolean b = (Boolean) m.invoke(DBObject, null);

					if (!b.booleanValue()) {
						try {
							conn.rollback();
						} catch (Exception e) {
						}
						conn.close();
						logger.debug(DBObject.getClass().getName() + " "
								+ action + " Failed");
						return false;
					}
				} else if (action.equals("UPDATE")) {
					m = DBObject.getClass().getMethod("update", null);
					Boolean b = (Boolean) m.invoke(DBObject, null);

					if (!b.booleanValue()) {
						try {
							conn.rollback();
						} catch (Exception e) {
						}
						conn.close();
						logger.debug(DBObject.getClass().getName() + " "
								+ action + " Failed");
						return false;
					}
				} else if (action.equals("DELETE")) {
					m = DBObject.getClass().getMethod("delete", null);
					Boolean b = (Boolean) m.invoke(DBObject, null);

					if (!b.booleanValue()) {
						try {
							conn.rollback();
						} catch (Exception e) {
						}
						conn.close();
						logger.debug(DBObject.getClass().getName() + " "
								+ action + " Failed");
						return false;
					}
				}
			}

			conn.commit();
			conn.close();
			logger.debug("---End Committed---");
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpAddNameSignBLS";
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
		GrpAddNameSignBLS GrpAddNameSignBLS1 = new GrpAddNameSignBLS();
	}
}
