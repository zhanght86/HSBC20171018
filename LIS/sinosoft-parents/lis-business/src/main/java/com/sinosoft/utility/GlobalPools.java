package com.sinosoft.utility;
import org.apache.log4j.Logger;

/*
 * <p>ClassName: GlobalPools </p>
 * <p>Description: 线程号与连接池映射缓存类 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @author tongmeng
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

public final class GlobalPools {
private static Logger logger = Logger.getLogger(GlobalPools.class);

	private static GlobalPools mGlobalPools = null;
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息
	/* 保存线程号和连接池名映射 */
	private Hashtable mGlobalTable = new Hashtable();
	// public boolean mLoadPropertyFlag = false;
	/* 配置文件是否加载成功标志 */
	public boolean mLoadSuccFlag = false;
	/* 保存全局的properties */
	private static Properties ruleproperties = null;

	private GlobalPools() {
	}

	public static GlobalPools getInstance() {
		if (mGlobalPools == null) {
			mGlobalPools = new GlobalPools();
		}

		mGlobalPools.mErrors.clearErrors();
		return mGlobalPools;
	}

	/**
	 * 通过线程ID获得需调用连接池名
	 * 
	 * @param tThreadId
	 * @return
	 */
	public String getPoolNameByThreadId(String tThreadId) {
		return this.mGlobalTable.get(tThreadId) == null ? ""
				: (String) this.mGlobalTable.get(tThreadId);

	}

	/**
	 * 保存当前线程ID和连接池名
	 * 
	 * @param tThreadId
	 * @param tPoolType
	 */
	public void setPoolNameByThreadId(String tThreadId, String tPoolType) {
		this.mGlobalTable.put(tThreadId, tPoolType);
	}

	/**
	 * 保存当前线程ID和连接池名
	 * 
	 * @param tPoolName
	 */
	public void setPoolName(String tPoolName) {
		// jdk1.5一下不支持getId()
		// String tThreadID = String.valueOf(Thread.currentThread().getId());
		String tThreadID = String.valueOf(Thread.currentThread().getName());

		if (this.mGlobalTable.containsKey(tThreadID)) {
			this.mGlobalTable.remove(tThreadID);
		}
		this.mGlobalTable.put(tThreadID, tPoolName);
	}

	/**
	 * 初始化Properties
	 */
	public synchronized void initProperties() {
		// 如果为空才重新加载.
		// 防止自动运行通过界面重启对ruleproperties有影响.
		if (ruleproperties == null) {

			Date start = new Date();
			Properties prop = new Properties();

			InputStream is = GlobalPools.class
					.getResourceAsStream("/dbrule.properties");

			ruleproperties = prop;
			try {
				if (is == null) {
					logger.debug("读取配置文件失败");
				}
				if (is != null) {
					logger.debug("begin load dbrule.properties..");
					prop.load(is);
					logger.debug("end load dbrule.properties..");
					this.mLoadSuccFlag = true;
				}
				Date end = new Date();
				logger.debug("GlobalPools load properties:\t"
						+ (end.getTime() - start.getTime()) + "\t:" + " used ");

			} catch (Exception e) {
				e.printStackTrace();
				ruleproperties = null;
				this.mLoadSuccFlag = false;
				CError.buildErr(this, "配置文件加载错误!");
			} finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 刷新ruleproperties
	 */
	public synchronized void refreshRuleProperties() {
		this.ruleproperties.clear();
		this.ruleproperties = null;
	}

	/**
	 * 返回Properties
	 * 
	 * @return
	 */
	public Properties getRuleProperties() {
		// 如果ruleproperties为空,说明配置文件加载失败,重新加载.
		if (ruleproperties == null) {
			initProperties();
		}
		return ruleproperties;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
