package com.sinosoft.lis.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CacheUtil;

/**
 * <p>
 * Title: 正在执行的多线程任务相关信息缓存类
 * </p>
 * <p>
 * Description: 缓存任务进度监控程序中用到的任务相关信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author HuangLiang
 * 
 * @version 1.0
 * @since 2011-11-24
 */
public class TaskCachedService extends CacheService {
	private static Logger logger = Logger.getLogger(TaskCachedService.class);

	public CErrors mErrors = new CErrors(); // 错误信息

	public final static String DEFAULT_CACHENAME = "taskMonitorCache";

	private final static String DEFAULT_CACHEKEY = "TaskMonitor";

	private static String NOTFOUND = "NOFOUND";

	private static TaskCachedService mCachedTaskService = null;

	private TaskCachedService() {
		setCacheName(DEFAULT_CACHENAME);
		initCache();
	}

	/**
	 * 返回缓存对象
	 * 
	 * @return 缓存对象
	 */
	public synchronized static TaskCachedService getInstance() {
		if (mCachedTaskService == null) {
			mCachedTaskService = new TaskCachedService();
		}
		mCachedTaskService.mErrors.clearErrors();
		return mCachedTaskService;
	}

	/**
	 * 返回正在执行的多线程任务信息
	 * 
	 * @see setTaskServiceState(String mTaskServiceCode, String
	 *      mTaskServiceState)
	 * @param mTaskServiceCode
	 *            任务ID
	 * @return String 返回已执行任务数和总任务数
	 */
	public String getTaskServiceState(String mTaskServiceCode) {
		HashMap<String, String> tHashMap = getCache();
		String tStrPK = mTaskServiceCode;
		String tObject = "";
		try {
			if (tHashMap != null)
				tObject = tHashMap.get(tStrPK);
			if (tObject == null) {
				tObject = NOTFOUND;
			}
		} catch (Exception e) {
			CError cError = new CError();
			CError.buildErr(cError, "读取任务进度出错！");
			this.mErrors.addOneError(cError);
			e.printStackTrace();
		}
		return tObject;
	}

	/**
	 * 设置正在执行的多线程任务信息，为了避免出现数据丢失，加上synchronized。
	 * 由于记录进度的机制（覆盖前一个值），不需考虑多服务器访问时会丢失值的问题
	 * 
	 * @see getTaskServiceState(String mTaskServiceCode)
	 * @param mTaskServiceCode
	 *            任务ID
	 * @param mTaskServiceState
	 *            任务状态
	 * @return true保存成功，false保存失败
	 */
	public synchronized boolean setTaskServiceState(String mTaskServiceCode,
			String mTaskServiceState) {
		HashMap<String, String> tHashMap = getCache();
		String tStrPK = mTaskServiceCode;
		String tStrV = mTaskServiceState;
		try {
			tHashMap.put(tStrPK, tStrV);
			setCache(tHashMap);
		} catch (Exception e) {
			CError cError = new CError();
			CError.buildErr(cError, "记录任务进度出错！");
			this.mErrors.addOneError(cError);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 访问缓存服务器，获取HashMap
	 * 
	 * @return
	 */
	private HashMap<String, String> getCache() {
		Serializable obj = getCacheObjectByKey(DEFAULT_CACHEKEY);
		if (obj != null && obj instanceof HashMap<?, ?>) {
			return ((HashMap<String, String>) obj);
		}
		return null;
	}

	/**
	 * 获取所有任务的遍历
	 * 
	 * @return
	 */
	public Iterator<String> getAllTaskServiceState() {
		HashMap<String, String> tHashMap = getCache();
		return tHashMap.keySet().iterator();
	}

	/**
	 * 访问缓存服务器，记录HashMap
	 * 
	 * @param mHashTask
	 */
	private void setCache(HashMap mHashTask) {
		CacheUtil.putObjectCached(getCacheName(), DEFAULT_CACHEKEY, mHashTask);
	}

	public void initCache() {
		if (getCache() == null) {
			logger.debug("初始化记录任务进度缓存");
			CacheUtil.putObjectCached(getCacheName(), DEFAULT_CACHEKEY,
					new HashMap<String, String>());
		}
	}
}
