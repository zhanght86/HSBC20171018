package com.sinosoft.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.config.TaskCachedService;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.BlockingQueue;
import java.util.List;

public class ServiceA {
private static Logger logger = Logger.getLogger(ServiceA.class);

	private ServiceEngineA mServiceEngineA = null;

	private Timer mServiceTimer = null;

	/** 线程池初始化对象名 */
	private String mCovertClassName = "";

	/** 等待任务执行队列 */
	private Vector mTaskWaitList = null;

	/** 运行中启动线程数量 */
	private int mthreadnum;

	/** 循环探测间隔时间 单位为毫秒 */
	private int mperiod;

	/** 使用jdk1.5自带线程池标记，true时为使用，false时为不使用 */
	private boolean mConcurrentFlag = true;

	/**
	 * 正在执行任务的ID(或者队列ID)，作为hash的key
	 * 
	 */
	private String mCachedStrPK = "";

	/**
	 * 正在执行任务的ID
	 * 
	 */
	private String mTaskCode = "";

	/**
	 * 已执行完的任务数
	 */
	private long mCompletedTaskNum;

	/**
	 * 总任务数
	 */
	private long mTotalTaskNum;

	/**
	 * 初始化线程池：提供启动和停止服务
	 */
	private ExecutorService pool = null;

	public ServiceResult mServiceResult = new ServiceResult();

	/**
	 * @param covertclassname
	 *            线程池初始化对象名
	 * @param tTaskWaitList
	 *            等待任务执行队列
	 * @param threadnum
	 *            启动线程数量
	 * @param tperiod
	 *            线程池满后等待时间单位为毫秒
	 */
	public ServiceA(String covertclassname, Vector tTaskWaitList,
			int threadnum, int tperiod) {
		this.mCovertClassName = covertclassname;
		this.mTaskWaitList = (Vector) tTaskWaitList.clone();
		this.mthreadnum = threadnum;
		this.mperiod = tperiod;
	}

	/**
	 * @param covertclassname
	 *            线程池初始化对象名
	 * @param tTaskWaitList
	 *            等待任务执行队列
	 * @param threadnum
	 *            启动线程数量 默认线程池满后等待时间为10毫秒
	 */
	public ServiceA(String covertclassname, Vector tTaskWaitList, int threadnum) {
		this.mCovertClassName = covertclassname;
		this.mTaskWaitList = (Vector) tTaskWaitList.clone();
		this.mthreadnum = threadnum;
		this.mperiod = 10; // 默认为1000毫秒循环一次
	}

	/**
	 * @param covertClassName
	 * @param tTaskWaitList
	 * @param mthreadnum
	 * @param mperiod
	 * @param cachedStrPK
	 *            需要监控任务计划进度时，传入（队列ID：任务ID）
	 */
	public ServiceA(String covertClassName, Vector tTaskWaitList,
			int mthreadnum, int mperiod, String mCachedStrPK) {
		mCovertClassName = covertClassName;
		mTaskWaitList = (Vector) tTaskWaitList.clone();
		this.mthreadnum = mthreadnum;
		this.mperiod = mperiod;
		setCachedStrPK(mCachedStrPK);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public void start() {
		try {
			// 设置监听数据
			// if (this.mCachedStrPK == "")this.mCachedStrPK =
			// this.mCovertClassName;// 任务id
			this.mTotalTaskNum = this.mTaskWaitList.size();// 总任务数
			logger.debug("#_#^_^Start+_+Thread^_^" + "mCachedStrPK is "
					+ mCachedStrPK + "^_^mClassName:" + mCovertClassName
					+ "^_^mthreadnum:" + mthreadnum + "^_^mTotalTaskNum:"
					+ mTotalTaskNum + "^_^#_#");
			if (mConcurrentFlag) {
				// 使用jdk自带线程池包concurrent
				pool = Executors.newFixedThreadPool(this.mthreadnum);
				// 遍历任务，生成相应线程，加入线程池
				Iterator tIterator = this.mTaskWaitList.iterator();
				while (tIterator.hasNext()) {
					Object tObject = tIterator.next();
					CovBase tCovBase = (CovBase) Class.forName(
							this.mCovertClassName).newInstance();
					tCovBase.setObject(tObject);
					tCovBase.setServiceResult(this.mServiceResult);
					pool.execute(tCovBase);
				}
				pool.shutdown();
				ThreadPoolExecutor tPool = (ThreadPoolExecutor) pool;
				// 判断任务是否结束
				int tSecond = 10;// 隔一段时间监听
				while (!pool.isTerminated()) {
					if (tPool.getCompletedTaskCount() > this.mCompletedTaskNum) {
						this.mCompletedTaskNum = tPool.getCompletedTaskCount();
						if (this.mCachedStrPK != "")
							this.setTaskServiceState(this.mCachedStrPK,
									this.mCompletedTaskNum, this.mTotalTaskNum);
					} else {
						// 等待时间延长
						if (tSecond < 100)
							tSecond++;
						else if (tSecond < 1000)
							tSecond += 10;
						else if (tSecond < 10000)
							tSecond += 100;
						else if (tSecond < 60000)
							tSecond += 1000;
					}
					// test
					// if(tSecond>30){stop();break;}
					Thread.sleep(tSecond);
				}
				// while (!pool.isTerminated()) {}
				this.mCompletedTaskNum = tPool.getCompletedTaskCount();
				logger.debug("");
				if (this.mCachedStrPK != "")
					this.setTaskServiceState(this.mCachedStrPK,
							this.mCompletedTaskNum, this.mTotalTaskNum);
			} else {
				mServiceTimer = new Timer();
				mServiceEngineA = new ServiceEngineA(this.mCovertClassName,
						this.mthreadnum, mServiceTimer);
				mServiceEngineA.startEngine(this.mTaskWaitList);
				mServiceTimer.schedule(mServiceEngineA, 1000, 1000 * mperiod);

				// 判断队列是否全部被执行
				while (mTaskWaitList.size() > 0) {
					try {
						// logger.debug("in
						// mTaskWaitList.size:"+mTaskWaitList.size());
						this.mCompletedTaskNum = this.mTotalTaskNum
								- mTaskWaitList.size();
						if (this.mCachedStrPK != "")
							this.setTaskServiceState(this.mCachedStrPK,
									this.mCompletedTaskNum, this.mTotalTaskNum);
						Thread.sleep(10000);
						// logger.debug("sleep1");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// logger.debug("out
				// mTaskWaitList.size:"+mTaskWaitList.size());
				// 如果队列被执行，在判断线程池时候全部执行完毕，完毕才退出，否则继续等待
				while (!mServiceEngineA.getPoolNotInUse()) {
					try {
						// logger.debug("mServiceEngineA.getPoolNotInUse()+busy!");
						Thread.sleep(5000);
						// logger.debug("sleep2");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				this.mCompletedTaskNum = this.mTotalTaskNum
						- mTaskWaitList.size();
				if (this.mCachedStrPK != "")
					this.setTaskServiceState(this.mCachedStrPK,
							this.mCompletedTaskNum, this.mTotalTaskNum);
			}
			logger.debug("#_#^_^End+_+Thread^_^" + "mCachedStrPK is "
					+ mCachedStrPK + "^_^final_Completed:"
					+ this.mCompletedTaskNum + "^_^mthreadnum:" + mthreadnum
					+ "^_^mTotalTaskNum:" + mTotalTaskNum + "^_^mClassName:"
					+ mCovertClassName + "^_^#_#");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止正在运行的线程池(由于pool自带的shutdownNow方法会导致sleep的线程被终止的错误，不用它)
	 * 获得所有block的队列，将其从队列中移除即可。
	 * 
	 */
	public void stopTask() {
		if (mConcurrentFlag) {
			// JDK线程池
			if (pool != null) {
				try {
					ThreadPoolExecutor tPool = (ThreadPoolExecutor) pool;
					// 获得BlockingQueue队列，从线程池中移除
					BlockingQueue<Runnable> t = tPool.getQueue();
					int i = 0;
					//移除等待的队列任务，类型要和加入的类型一致
					for (i = 0; i < t.size(); i++)
						tPool.remove((CovBase)t.poll());
					logger.debug("^0^~BLOCKING QUEUE HAS BEEN REMOVED!^_^ABOUT "
									+ i + "HAS BEEN REMOVED!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			// 自设计线程池
		}
	}

	/**
	 * 设置正在执行任务的信息
	 * 
	 * @param mStrPK
	 * @param mCompletedTaskNum
	 * @param mTotalTaskNum
	 * @return
	 */
	private boolean setTaskServiceState(String mStrPK, long mCompletedTaskNum,
			long mTotalTaskNum) {
		TaskCachedService tCachedTaskService = TaskCachedService.getInstance();
		String t = mCompletedTaskNum + "/" + this.mTaskWaitList.size();
		String tTaskCode = (String) mStrPK.split(":")[1].trim();// 取出任务ID或者队列ID
		if (!this.mTaskCode.equalsIgnoreCase(tTaskCode)) {
			t = this.mTaskCode + ":" + t;// 任务队列执行时，增加显示正在执行的任务ID
		}
		return tCachedTaskService.setTaskServiceState(mStrPK, t);
	}

	/**
	 * 传入正在执行的任务信息（任务队列ID：任务ID：任务计划ID），供监听使用
	 * 
	 * @param mStrPK
	 * @return
	 */
	private void setCachedStrPK(String mStrPK) {
		String t[] = mStrPK.split(":");
		this.mTaskCode = t[1].trim();// 任务ID
		if (t[0].compareToIgnoreCase("AAAAAA") == 0) {
			this.mCachedStrPK = t[2].trim() + ":" + t[1].trim();// 单个任务以“任务计划ID:任务ID”为hash的key
		} else {
			this.mCachedStrPK = t[2].trim() + ":" + t[0].trim();// 任务队列以“任务计划ID:队列ID”为hash的key
		}
		if (this.mCachedStrPK == "null")
			this.mCachedStrPK = "";
	}

	public Map getResultList() {
		if (mConcurrentFlag)
			return this.mServiceResult.getReturnList();
		else
			return this.mServiceEngineA.mServiceResult.getReturnList();
	}

	public void startNew() {
		try {
			logger.debug("mClassName:" + mCovertClassName);
			mServiceTimer = new Timer();
			mServiceEngineA = new ServiceEngineA(this.mCovertClassName,
					this.mthreadnum, mServiceTimer);
			mServiceEngineA.startEngine(this.mTaskWaitList);

			mServiceTimer.schedule(mServiceEngineA, 1000, 1000 * mperiod);

			synchronized (mServiceEngineA) {
				while (!(mTaskWaitList.size() <= 0 && mServiceEngineA
						.getPoolNotInUse())) {
					try {
						mServiceEngineA.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
