package com.sinosoft.lis.pubfun;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class OptimisticLockDataCheck {
	private static Logger logger = Logger
			.getLogger(OptimisticLockDataCheck.class);
	private OptimisticLockData mOptimisticLockData = new OptimisticLockData();
	private StackTraceElement[] stackElements = null;

	// 是否绑定变量模式执行校验，true使用绑定变量方式，false使用传统拼接SQL方式
	private static final boolean BVFLAG = false;
	private static OptimisticLockCache mOptimisticLockCache = OptimisticLockCache.getInstance();
	
	
	private Connection conn = null;
	
	public static void setCheckClass(String className){
		mOptimisticLockCache.setCheckClass(className);
	}
	
	
	public Connection getConn() {
		return conn;
	}


	public void setConn(Connection conn) {
		this.conn = conn;
	}


	/**
	 * 判断锁表信息是否初始化完毕
	 */
	private boolean isInit() {
		if (mOptimisticLockData.getColumns().keySet().size() <= 0
				|| mOptimisticLockData.getTableName() == null
				|| mOptimisticLockData.getTableName().equals("")
				|| mOptimisticLockData.getLockType() == null
				||this.getConn()==null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否执行数据校验 只有批处理引擎的实例，才会调用锁校验
	 */
	private boolean getCheckFlag() {

		stackElements = Thread.currentThread().getStackTrace();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				String tClassName = stackElements[i].getClassName();
				String tMethodName = stackElements[i].getMethodName();
				 logger.info("tClassName:"+tClassName + "    :tMethodName:"+tMethodName);
				if (tClassName.indexOf("com.sinosoft.lis.taskservice.taskinstance") != -1||mOptimisticLockCache.isCheckClass(tClassName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 构建加锁校验SQL-非绑定变量模式
	 * 
	 * @return
	 */
	private String buildLockSql() {
		String lockSql = "";
		String fieldSql = "";
		if (("1").equals(mOptimisticLockData.getLockType().getValue())) {
			lockSql = "select 1 from %1$s where 1=1 %2$s";
			lockSql = lockSql + " for update";//增加行级索
			for (String column : mOptimisticLockData.getColumns().keySet()) {
				fieldSql = fieldSql + " and " + column + "='"
						+ mOptimisticLockData.getColumns().get(column) + "'";
			}
			lockSql = String.format(lockSql,
					mOptimisticLockData.getTableName(), fieldSql);
		}
		logger.info("lockSql:" + lockSql);
		return lockSql;
	}

	/**
	 * 构建加锁校验SQL-绑定变量模式
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private VData buildLockSqlBV() {
		VData bvVData = new VData();
		String lockSql = "";
		String fieldSql = "";
		TransferData valueTransfer = new TransferData();
		if (("1").equals(mOptimisticLockData.getLockType().getValue())) {
			lockSql = "select 1 from %1$s where 1=1 %2$s";
			lockSql = lockSql + " for update";//增加行级索
			int i = 0;
			for (String column : mOptimisticLockData.getColumns().keySet()) {
				fieldSql = fieldSql + " and " + column + "=?";
				valueTransfer.setNameAndValue(String.valueOf(i), "String:"
						+ mOptimisticLockData.getColumns().get(column));
				i++;
			}

			lockSql = String.format(lockSql,
					mOptimisticLockData.getTableName(), fieldSql);
			bvVData.add(lockSql);
			bvVData.add(valueTransfer);
		}
		logger.info("lockSql:" + lockSql);
		return bvVData;
	}

	/**
	 * 加锁校验
	 */
	public boolean checkLock() {
		
		if (!getCheckFlag()) {
			logger.info("不做校验");
			return true;
		}
		logger.info("开始校验");

		// 如果校验数据没有初始化，直接返回错误
		if (!this.isInit()) {
			return false;
		}
		
		if(conn==null){
			logger.info("连接设置有问题，不做校验");
			return false;
		}
		ExeSQL exeSQL = new ExeSQL(conn);
		SSRS tSSRS = new SSRS();
		if (!BVFLAG) {
			// 非绑定变量方式
			String checkSql = buildLockSql();
			SQLwithBindVariables sqlbva = new SQLwithBindVariables();
			sqlbva.sql(checkSql);
			tSSRS = exeSQL.execSQL(sqlbva);
		} else {
			VData bvData = buildLockSqlBV();
			tSSRS = exeSQL.execSQL(bvData);
		}

		if (tSSRS == null || tSSRS.getMaxRow() != 1) {
			// 不是唯一结果返回错误
			return false;
		}

		return true;
	}

	public OptimisticLockData getmOptimisticLockData() {
		return mOptimisticLockData;
	}

	public void setmOptimisticLockData(OptimisticLockData mOptimisticLockData) {
		this.mOptimisticLockData = mOptimisticLockData;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
