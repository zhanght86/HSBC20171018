package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 付费方式修改功能</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

import java.sql.Connection;

import com.sinosoft.lis.vdb.LBAGetForPayModeDBSet;
import com.sinosoft.lis.vdb.LJAGetDBSet;
import com.sinosoft.lis.vschema.LBAGetForPayModeSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

public class ModifyLJAGetBLS {
private static Logger logger = Logger.getLogger(ModifyLJAGetBLS.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 传入的业务数据 */
	private LJAGetSet inLJAGetSet = new LJAGetSet();
	private LBAGetForPayModeSet inLBAGetForPayModeSet = new LBAGetForPayModeSet();

	public ModifyLJAGetBLS() {
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
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 保存信息
		if (!saveData())
			return false;
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
			inLJAGetSet = (LJAGetSet) mInputData.getObjectByObjectName(
					"LJAGetSet", 0);
			inLBAGetForPayModeSet = (LBAGetForPayModeSet) mInputData
					.getObjectByObjectName("LBAGetForPayModeSet", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "ModifyLJAGetBLS";
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
			tError.moduleName = "ModifyLJAGetBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 开始事务，锁表
			conn.setAutoCommit(false);

			if (mOperate.equals("INSERT")) {
				// 更新实付总表
				// logger.debug("inLJAGetSet:" + inLJAGetSet.size() + " :
				// " + inLJAGetSet.encode());
				LJAGetDBSet tLJAGetDBSet = new LJAGetDBSet(conn);
				tLJAGetDBSet.set(inLJAGetSet);
				if (!tLJAGetDBSet.update()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LJAGet Update Failed");
					return false;
				}

				// 新增实付总表备份表
				// logger.debug("inLBAGetForPayModeSet:" +
				// inLBAGetForPayModeSet.size() + " : " +
				// inLBAGetForPayModeSet.encode());
				LBAGetForPayModeDBSet tLBAGetForPayModeDBSet = new LBAGetForPayModeDBSet(
						conn);
				tLBAGetForPayModeDBSet.set(inLBAGetForPayModeSet);
				if (!tLBAGetForPayModeDBSet.insert()) {
					try {
						conn.rollback();
					} catch (Exception e) {
					}
					conn.close();
					logger.debug("LBAGetForPayMode Insert Failed");
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
			tError.moduleName = "ModifyLJAGetBLS";
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
		ModifyLJAGetBLS ModifyLJAGetBLS1 = new ModifyLJAGetBLS();
	}
}
