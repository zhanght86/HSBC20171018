/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.Vector;

import com.sinosoft.lis.db.LDTaskParamDB;
import com.sinosoft.lis.db.LDTaskRunLogDB;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LDTaskPlanSchema;
import com.sinosoft.lis.schema.LDTaskSchema;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ck
 * @version 1.0
 */
public class TaskThreadQ implements Runnable {
private static Logger logger = Logger.getLogger(TaskThreadQ.class);
	protected HashMap mParameters = new HashMap();

	public CErrors mErrors = new CErrors();

	private String ServerInfo = "";

	protected String mContent = "";

	private LDTaskSet mLDTaskSet = new LDTaskSet();

	private String mTaskID;

	private LDTaskPlanSchema mLDTaskPlanSchema;

	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	/**
	 * 使用固定1个的线程池，方便停止进程
	 */
	private ExecutorService tPool = Executors.newFixedThreadPool(1);

	/**
	 * 队列任务的序列，主用于移除任务时
	 */
	private Vector<TaskThread> mTaskList = new Vector<TaskThread>();

	/**
	 * 进程是否结束标记
	 */
	private boolean isAlive = true;

	public TaskThreadQ() {
		LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
		tLDTaskParamDB.setTaskCode("000000");
		tLDTaskParamDB.setTaskPlanCode("000000");
		tLDTaskParamDB.setParamName("ServerType");
		if (tLDTaskParamDB.getInfo()) {
			if (tLDTaskParamDB.getParamValue() != null
					&& tLDTaskParamDB.getParamValue().toLowerCase().equals(
							"weblogic")) {
				ServerInfo = MultiTaskServer.getServerIP() + ":"
						+ String.valueOf(MultiTaskServer.getServerPort());
			} else if (tLDTaskParamDB.getParamValue() != null
					&& tLDTaskParamDB.getParamValue().toLowerCase().equals(
							"tomcat")) {
				InetAddress ServerIPaddress = null;
				try {
					ServerIPaddress = InetAddress.getLocalHost();
					MultiTaskServer
							.outPrint("ServerIPaddress.getHostAddress():"
									+ ServerIPaddress.getHostAddress());
				} catch (Exception ex) {
					ex.printStackTrace();
					ServerInfo = "";
				}
				ServerInfo = ServerIPaddress.getHostAddress();
			}
		} else {
			ServerInfo = "";
		}
	}

	public void setParameter(HashMap aParameters) {
		mParameters = aParameters;
	}

	public void setTaskSet(LDTaskSet aLDTaskSet) {

		mLDTaskSet = aLDTaskSet;
	}

	public void setTaskID(String aTaskID) {

		this.mTaskID = aTaskID;
	}

	public void setTaskPlan(LDTaskPlanSchema aLDTaskPlanSchema) {

		this.mLDTaskPlanSchema = aLDTaskPlanSchema;
	}

	public void run() {
		try {
			String tKeyNo = (String) this.mParameters.get("TaskGroupCode");
			if (!mPubLock.lock(tKeyNo, "LD0001")) {
				CError.buildErr(this, mPubLock.mErrors.getLastError());
				// 任务被锁定，记录相关运行日志
				LDTaskRunLogDB tLDTaskRunLogDB = new LDTaskRunLogDB();
				String SerialNo = PubFun1.CreateMaxNo("SERIALNO", 10);
				tLDTaskRunLogDB.setSerialNo(SerialNo);
				String tTaskCode = "";
				if (mLDTaskSet.size() > 0) {
					LDTaskSchema tLDTaskSchema = mLDTaskSet.get(1);
					tTaskCode = tLDTaskSchema.getTaskCode();
				}
				tLDTaskRunLogDB.setTaskCode(tTaskCode);
				tLDTaskRunLogDB.setTaskPlanCode((String) mParameters
						.get("TaskPlanCode"));
				// TaskGroupCode
				tLDTaskRunLogDB.setTaskGroupCode((String) mParameters
						.get("TaskGroupCode"));
				tLDTaskRunLogDB.setServerInfo(MultiTaskServer.getServerIP()+":"+MultiTaskServer.getServerPort());
				tLDTaskRunLogDB.setExecuteDate(PubFun.getCurrentDate());
				tLDTaskRunLogDB.setExecuteTime(PubFun.getCurrentTime());
				String tExecuteState = "0";
				tLDTaskRunLogDB.setExecuteState(tExecuteState);
				String tExecuteResult = "任务:" + tTaskCode + "上次业务逻辑尚未结束，本次不执行！";
				tLDTaskRunLogDB.setExecuteResult(tExecuteResult);
				tLDTaskRunLogDB.insert();
			} else {
				this.dealMain();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.mPubLock.unLock();
			this.isAlive = false;// 进程结束标记，放在run的最后，希望以后能改进，使用更安全的方法判断
		}
	}

	public boolean dealMain() {
		for (int i = 1; i <= this.mLDTaskSet.size(); i++) {
			try {
				LDTaskSchema tLDTaskSchema = new LDTaskSchema();
				tLDTaskSchema = mLDTaskSet.get(i);

				Class tTaskClass = Class
						.forName("com.sinosoft.lis.taskservice.taskinstance."
								+ tLDTaskSchema.getTaskClass());

				// 开启一个线程处理任务
				TaskThread tTaskThread = (TaskThread) tTaskClass.newInstance();

				// add by ck(传入参数)
				// tongmeng 2008-05-22 modify
				// 修改传参没有把数据库的参数传到实例中的问题
				// HashMap tHashMap = new HashMap();
				// this.mParameters.put("TaskPlanCode", mTaskID);
				// 不使用全局变量mParameters，会使所有任务ID都一样
				HashMap tParameters = new HashMap();
				tParameters = (HashMap) mParameters.clone();
				tParameters.put("TaskCode", tLDTaskSchema.getTaskCode());
				// this.mParameters.put("TaskCode",
				// tLDTaskSchema.getTaskCode());
				MultiTaskServer.outPrint("###################:"
						+ (String) mParameters.get("TaskGroupCode"));
				// this.mParameters.put("TaskGroupCode",
				// mLDTaskPlanSchema.getTaskCode());
				MultiTaskServer.outPrint("mLDTaskPlanSchema.getTaskCode():"
						+ mLDTaskPlanSchema.getTaskCode() + ":"
						+ tLDTaskSchema.getTaskCode());
				// tTaskThread.setParameter(mParameters);
				tTaskThread.setParameter(tParameters);

				// 增加任务队列，用于停止正在运行的任务
				this.mTaskList.add(tTaskThread);

				// MultiTaskServer.outPrint("任务计划" + mTaskID + "运行！ 运行次数：" +
				// mRunFrequence
				// + " 运行时间：" + mRunTime);

				// 使用单线程的线程池来运行 modify by HuangLiang 2011-11-30
				tPool.execute(tTaskThread);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tPool.shutdown();
		// TimeUnit tTimeUnit =TimeUnit.SECONDS;
		while (!tPool.isTerminated()) {
			try {
				Thread.sleep(1000);
				// tPool.awaitTermination(1, tTimeUnit);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try {
			// 更新任务转移记录
			TransTaskPlan.completeTaskPlan((String) mParameters
					.get("TaskPlanCode"), "G");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 获得从执行结果，返回页面
	 */
	public String getContent() {
		return mContent;
	}

	/**
	 * 停止正在执行的队列任务，先将队列任务的剩下任务从线程池中移除，再停止当前执行任务所启动的线程池
	 * 
	 * @param tStopType
	 *            为不同的停止需求预留参数
	 * @author HuangLiang
	 */
	public void stopTask(String tStopType) {
		try {
			ThreadPoolExecutor mPool = (ThreadPoolExecutor) tPool;
			int j = (int) mPool.getCompletedTaskCount();// 获取已执行的任务数，进而得知目前运行的任务是第几个
			for (int i = this.mTaskList.size() - 1; i > j; i--) {
				// 先移除后停止，保证前一个任务停止前移除完所有任务，否则会出错，从后往前移除，减少出错
				mPool.remove(this.mTaskList.get(i));
			}
			MultiTaskServer
					.outPrint("STOPING TASKQUEUE ....RUNNING THREAD IS NO." + j
							+ " AND REMOVING REMAINED　THREADS!");
			if (!mPool.isShutdown())
				mPool.shutdown();// 如果没关闭线程池，再关闭一次
			// 为避免任务恰好没有启动多线程时（此时停止无效）调用了停止多线程，反复停止
			while (!mPool.isTerminated()) {
				// 获得对应线程的对象指针，调用stopTask方法（继承自TaskThread类）
				this.mTaskList.get((int) mPool.getCompletedTaskCount())
						.stopTask();// 停止当前执行任务所启动的线程池
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回进程是否还在运行，add by HuangLiang 2011-12-02
	 * 
	 * @return true，进程还在运行
	 */
	public boolean isAlive() {
		return this.isAlive;
	}
}
