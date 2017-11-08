/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaskDB;
import com.sinosoft.lis.db.LDTaskParamDB;
import com.sinosoft.lis.db.LDTaskPlanDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskParamSchema;
import com.sinosoft.lis.schema.LDTaskPlanSchema;
import com.sinosoft.lis.taskservice.crontab.CrontabParser;
import com.sinosoft.lis.vschema.LDTaskMsgItemSet;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskPlanSet;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 任务启动引擎
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class TaskServiceEngine extends TimerTask {
	private static Logger logger = Logger.getLogger(TaskServiceEngine.class);
	private static Vector mTaskWaitList; // 等待队列

	private static Vector mTaskRunList; // 运行队列

	private static Vector mTaskReadyList; // 就绪队列

	private static LDTaskPlanDB mLDTaskPlanDB;

	private static LDTaskPlanSet mLDTaskPlanSet;

	private static VData mData;

	private static MMap mMap;

	private static Object lock;

	private static boolean mDataChanged = false;

	private static SimpleDateFormat tSDF = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static CErrors mErrors = new CErrors();

	// 服务器类型 tomcat,weblogic
	private String mServerType = "";

	// 运行方式 0-单节点 1-分节点运行
	private String mRunType = "";

	/**
	 * 计划运行的线程池
	 */
	private static ExecutorService tPool = null;

	/**
	 * 线程池模式，fix和cache两种
	 */
	private String tPoolMode = "";

	/**
	 * fix模式下，线程池最小线程数
	 */
	private int mThreadNum = 5;

	/**
	 * fix模式下，线程池最大线程数
	 */
	private int mMaxThreadNum = 5;

	public TaskServiceEngine() {
		// 等待队列
		if (mTaskWaitList == null) {
			mTaskWaitList = new Vector();
		}

		// 运行队列
		if (mTaskRunList == null) {
			mTaskRunList = new Vector();
		}

		// 就绪队列
		if (mTaskReadyList == null) {
			mTaskReadyList = new Vector();
		}

		mLDTaskPlanDB = new LDTaskPlanDB();
		mData = new VData();
		mMap = new MMap();
		lock = new Object();
	}

	public SQLwithBindVariables getRunTaskPlan(String tType) {
		String RunString = "";
		LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
		tLDTaskParamDB.setTaskCode("000000");
		tLDTaskParamDB.setTaskPlanCode("000000");
		tLDTaskParamDB.setParamName("ServerType");
		if (tLDTaskParamDB.getInfo()) {
			// 不管服务器类型是什么,在初始化时,都将所有任务查询到当前的任务队列中
			// 只查询待运行的任务
			if (tType.equals("1")) {
				RunString = "select * from LDTaskPlan a where TaskPlanCode <> '000000' and runflag='1' ";
			} else if (tType.equals("2")) {
				RunString = "select * from LDTaskPlan a where TaskPlanCode <> '000000'  ";
			}
			/*
			 * if (tLDTaskParamDB.getParamValue() != null &&
			 * tLDTaskParamDB.getParamValue().toLowerCase().equals( "weblogic"))
			 * { RunString = "select * from LDTaskPlan a where TaskPlanCode <>
			 * '000000' " + "and (exists(select 1 from LDTaskParam where
			 * TaskCode=a.TaskCode and TaskPlanCode=a.TaskPlanCode and
			 * lower(ParamName) = 'serverip:port' and ParamValue = '" +
			 * MultiTaskServer.getServerIP() + ":" +
			 * MultiTaskServer.getServerPort() + "') or (not exists(select 1
			 * from LDTaskParam where taskcode=a.taskcode and
			 * TaskPlanCode=a.TaskPlanCode " + "and lower(ParamName) =
			 * 'serverip:port' ) " + "and '" + MultiTaskServer.getServerIP() +
			 * ":" + MultiTaskServer.getServerPort() + "'=(select paramvalue
			 * from LDTaskParam where TaskCode='000000' and
			 * TaskPlanCode='000000' and lower(Paramname)='serverip:port')))"; }
			 * else if (tLDTaskParamDB.getParamValue() != null &&
			 * tLDTaskParamDB.getParamValue().toLowerCase().equals( "tomcat")) {
			 * RunString = "select * from LDTaskPlan a where TaskPlanCode <>
			 * '000000' "; }
			 */
		} else {
			mErrors.addOneError(new CError("缺少服务运行参数，请查看LDTaskParam表!"));
		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(RunString);
		MultiTaskServer.outPrint("RunString:" + RunString);
		return sqlbv1;
	}

	// 扫描除启动任务的其它任务
	private boolean scanTaskPlan() {
		if (mTaskWaitList != null) {
			// MultiTaskServer.outPrint("@@@before scan
			// :"+mTaskWaitList.size());
			MultiTaskServer
					.outPrint("@@@before scan  :" + mTaskWaitList.size());

		}
		mLDTaskPlanSet = mLDTaskPlanDB.executeQuery(getRunTaskPlan("1"));

		if ((mLDTaskPlanSet != null) && (mLDTaskPlanSet.size() > 0)) {
			int n = mLDTaskPlanSet.size();
			LDTaskPlanSchema tLDTaskPlanSchema = null;

			// //首先扫描是否有任务已经被删除,但是仍存在于任务队列中,对这些数据,直接移除
			// 确定不在任务计划中的任务
			// MultiTaskServer.outPrint("开始扫描需要移除的任务....");
			MultiTaskServer.outPrint("开始扫描需要移除的任务....");
			Vector tNotExistList = new Vector();
			for (int i = 0; i < this.mTaskWaitList.size(); i++) {
				String tTaskCode = ((Task) mTaskWaitList.get(i)).getTaskID();
				boolean existFlag = false;
				for (int m = 1; m <= n; m++) {
					// MultiTaskServer.outPrint("tTaskCode:"+tTaskCode+":"+mLDTaskPlanSet.get(m).getTaskPlanCode());
					if (mLDTaskPlanSet.get(m).getTaskPlanCode()
							.equals(tTaskCode)) {
						existFlag = true;
						break;
					}
				}
				if (!existFlag) {
					// MultiTaskServer.outPrint("tTaskCode:"+tTaskCode+"不存在");
					tNotExistList.add(tTaskCode);
				}
			}
			// MultiTaskServer.outPrint("移除不存在的任务");
			for (int not = 0; not < tNotExistList.size(); not++) {
				int index = indexOf((String) tNotExistList.get(not));

				if (index < 0) {
					continue;
				}
				// MultiTaskServer.outPrint("移除:"+(String)tNotExistList.get(not));
				MultiTaskServer.outPrint("移除:"
						+ (String) tNotExistList.get(not));
				mTaskWaitList.remove(index);
			}
			// MultiTaskServer.outPrint("结束扫描需要移除的任务....");
			MultiTaskServer.outPrint("结束扫描需要移除的任务....");

			for (int i = 1; i <= n; i++) {
				tLDTaskPlanSchema = mLDTaskPlanSet.get(i);
				// tongmeng 2008-03-28 modify
				// bug 对于异常中止的自动运行,重新启动时时会导致启动失败
				tLDTaskPlanSchema.setRunState("0");

				// 找出这些任务是否在任务等待队列
				if (!isExist(tLDTaskPlanSchema.getTaskPlanCode())) {
					// 如果在等待队列中不存在相应任务代码，则将该任务加入到等待队列当中
					LDTaskDB tLDTaskDB = new LDTaskDB();
					tLDTaskDB.setTaskCode(tLDTaskPlanSchema.getTaskCode());

					String tDecideSQL = "select 1 from ldtask  where taskcode='"
							+ "?taskcode?"
							+ "' "
							+ " union "
							+ " select 2 from ldtaskgroup where taskgroupcode='"
							+ "?taskcode?" + "'";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tDecideSQL);
					sqlbv2.put("taskcode", tLDTaskPlanSchema.getTaskCode());
					String tDecideFlag = "";
					ExeSQL tExeSQL = new ExeSQL();
					tDecideFlag = tExeSQL.getOneValue(sqlbv2);
					String tTaskSQL = "";

					if (tDecideFlag == null || tDecideFlag.equals("")) {
						mErrors.addOneError(new CError("任务计划"
								+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
								+ tLDTaskPlanSchema.getTaskCode() + "不存在！"));
						// MultiTaskServer.outPrint("任务计划" +
						// tLDTaskPlanSchema.getTaskPlanCode()
						// + "中的任务" + tLDTaskPlanSchema.getTaskCode() + "不存在！");

						MultiTaskServer.outPrint("任务计划"
								+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
								+ tLDTaskPlanSchema.getTaskCode() + "不存在！");
					} else if (tDecideFlag.equals("1")) {
						tTaskSQL = "select * from ldtask where taskcode='"
								+ "?taskcode?" + "' ";
					} else if (tDecideFlag.equals("2")) {
						tTaskSQL = "select a.* from ldtask a,ldtaskgroupdetail b   "
								+ " where a.taskcode=b.taskcode and b.taskgroupcode='"
								+ "?taskcode?"
								+ "' "
								+ " order by b.taskorder ";
					}
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(tTaskSQL);
					sqlbv3.put("taskcode", tLDTaskPlanSchema.getTaskCode());
					LDTaskSet tLDTaskSet = tLDTaskDB.executeQuery(sqlbv3);

					if ((tLDTaskSet == null) || (tLDTaskSet.size() <= 0)) {
						mErrors.addOneError(new CError("获取任务计划"
								+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
								+ tLDTaskPlanSchema.getTaskCode() + "不存在！"));
						// MultiTaskServer.outPrint("任务计划" +
						// tLDTaskPlanSchema.getTaskPlanCode()
						// + "中的任务" + tLDTaskPlanSchema.getTaskCode() + "不存在！");
						MultiTaskServer.outPrint("任务计划"
								+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
								+ tLDTaskPlanSchema.getTaskCode() + "不存在！");
						return false;
					}
					try {
						Task tTask = new Task(tLDTaskSet);

						// 设置启动任务和启动时间
						tTask.SetTaskPlan(tLDTaskPlanSchema);

						// 如果没有设置启动时间，会设置默认时间为现在启动
						if (tTask.getNextRunTime().equals("")) {
							tTask.setNextRunTime(tSDF.format(Calendar
									.getInstance().getTime()));
						}

						LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
						tLDTaskParamDB.setTaskCode(tLDTaskPlanSchema
								.getTaskCode());
						tLDTaskParamDB.setTaskPlanCode(tLDTaskPlanSchema
								.getTaskPlanCode());

						LDTaskParamSet tLDTaskParamSet = tLDTaskParamDB.query();

						if ((tLDTaskParamSet != null)
								&& (tLDTaskParamSet.size() > 0)) {
							LDTaskParamSchema tLDTaskParamSchema = null;
							int m = tLDTaskParamSet.size();

							for (int j = 1; j <= m; j++) {
								tLDTaskParamSchema = tLDTaskParamSet.get(j);
								tTask.addParam(
										tLDTaskParamSchema.getParamName(),
										tLDTaskParamSchema.getParamValue());
							}
						}

						// 通过LDTaskParam判断是否可以在当前结点运行
						if (!TaskServerManager.isCurrentServerPlan(
								tLDTaskParamSet, this.mRunType)) {
							// MultiTaskServer.outPrint("TaskPlanCode:"+tLDTaskPlanSchema.getTaskPlanCode()+"不是本结点运行的任务,不加入队列");
							MultiTaskServer.outPrint("TaskPlanCode:"
									+ tLDTaskPlanSchema.getTaskPlanCode()
									+ "不是本结点运行的任务,不加入队列");
							continue;
						} else {
							// MultiTaskServer.outPrint("TaskPlanCode:"+tLDTaskPlanSchema.getTaskPlanCode()+"是本结点运行的任务,加入队列");
							MultiTaskServer.outPrint("TaskPlanCode:"
									+ tLDTaskPlanSchema.getTaskPlanCode()
									+ "是本结点运行的任务,加入队列");
						}

						// 加入等待队列
						mTaskWaitList.add(tTask);
					} catch (Exception ex) {
						mErrors.addOneError(new CError("创建任务实例失败！异常类型："
								+ ex.getClass().getName()));
						// MultiTaskServer.outPrint("创建任务实例失败！异常类型："
						// + ex.getClass().getName());
						MultiTaskServer.outPrint("创建任务实例失败！异常类型："
								+ ex.getClass().getName());

					}
				} else {
					// 如果已经是本服务器中等待的队列,需要判断结点是否已经变化.
					// 如果变化,需要移除

					LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
					tLDTaskParamDB.setTaskCode(tLDTaskPlanSchema.getTaskCode());
					tLDTaskParamDB.setTaskPlanCode(tLDTaskPlanSchema
							.getTaskPlanCode());

					LDTaskParamSet tLDTaskParamSet = tLDTaskParamDB.query();
					// 通过LDTaskParam判断是否可以在当前结点运行
					if (!TaskServerManager.isCurrentServerPlan(tLDTaskParamSet,
							this.mRunType)) {
						// MultiTaskServer.outPrint("已存在队列中
						// TaskPlanCode:"+tLDTaskPlanSchema.getTaskPlanCode()+"不是本结点运行的任务,移除");
						MultiTaskServer.outPrint("已存在队列中 TaskPlanCode:"
								+ tLDTaskPlanSchema.getTaskPlanCode()
								+ "不是本结点运行的任务,移除");
						int index = indexOf(tLDTaskPlanSchema.getTaskPlanCode());

						if (index < 0) {
							continue;
						}

						mTaskWaitList.remove(index);
					} else {
						// MultiTaskServer.outPrint("已存在队列中
						// TaskPlanCode:"+tLDTaskPlanSchema.getTaskPlanCode()+"是本结点运行的任务,不做变化");
						MultiTaskServer.outPrint("已存在队列中 TaskPlanCode:"
								+ tLDTaskPlanSchema.getTaskPlanCode()
								+ "是本结点运行的任务,不做变化");
						continue;
					}

				}
			}
		}

		if (mTaskWaitList != null) {
			// MultiTaskServer.outPrint("@@@after scan:"+mTaskWaitList.size());
			MultiTaskServer.outPrint("@@@after scan:" + mTaskWaitList.size());
		}
		return true;
	}

	// 将启动任务加入到就绪队列之中
	private boolean searchReadyTask() {
		int n = mTaskWaitList.size();
		String tCurrentTime = PubFun.getCurrentDate() + " "
				+ PubFun.getCurrentTime();

		for (int i = 0; i < n; i++) {
			Task tTask = (Task) mTaskWaitList.get(i);
			LDTaskPlanSchema tLDTaskPlanSchema = tTask.getTaskPlan();

			if (!tLDTaskPlanSchema.getRunFlag().equals("1")) {
				continue;
			}

			if (!tLDTaskPlanSchema.getRunState().equals("0")) {
				continue;
			}

			if ((tTask.getNextRunTime() == null)
					|| tTask.getNextRunTime().equals("")) {
				tLDTaskPlanSchema.setRunFlag("0");
				tLDTaskPlanSchema.setRunState("3");
				mMap.put(tLDTaskPlanSchema, "UPDATE");
				mDataChanged = true;

				continue;
			}

			// 如果当前时间大于任务启动时间；小于任务结束时间，就加入就绪队列
			// 如果任务结束时间为null，则表示结束时间为无限大
			if (tCurrentTime.compareTo(tTask.getNextRunTime()) >= 0) {
				if ((tLDTaskPlanSchema.getEndTime() != null)
						&& !tLDTaskPlanSchema.getEndTime().equals("")) {
					if (tCurrentTime.compareTo(tLDTaskPlanSchema.getEndTime()) < 0) {
						mTaskReadyList.add(tTask);
					}
				} else {
					mTaskReadyList.add(tTask);
				}
			}
		}
		// tongmeng 2008-03-04 add
		// 增加调试信息
		// MultiTaskServer.outPrint("begin print TaskReadyList ");
		for (int i = 0; i < mTaskReadyList.size(); i++) {
			Task tTask = (Task) mTaskReadyList.get(i);
			LDTaskPlanSchema tLDTaskPlanSchema = tTask.getTaskPlan();
			MultiTaskServer.outPrint("TaskReadyList TaskPlanCode:"
					+ tLDTaskPlanSchema.getTaskPlanCode() + "TaskCode:"
					+ tLDTaskPlanSchema.getTaskCode() + "CurrentTime:"
					+ tCurrentTime + "TaskRunTime:" + tTask.getNextRunTime());
		}
		// MultiTaskServer.outPrint("end print TaskReadyList ");
		return true;
	}

	/**
	 * 动态调整fixed的线程池大小
	 * 
	 * @author HuangLiang 2011-12-02
	 */
	private void fixPoolSize() {
		if ("fix".equalsIgnoreCase(tPoolMode) && tPool != null) {
			try {
				// 只在fix的时候修改线程池大小
				ThreadPoolExecutor mPool = (ThreadPoolExecutor) this.tPool;
				int i = 0;
				int j = mPool.getActiveCount();// 活动线程数
				int k = mPool.getCorePoolSize();// 允许现在线程数
				int m = mPool.getQueue().size();// 等待线程数
				if (m <= 0) {// 没有等待的线程，考虑缩小线程池
					if (j <= this.mMaxThreadNum && j >= this.mThreadNum
							&& j != k) {
						// 直接缩小到正在活动的线程数
						i = 1;
					} else if (j <= this.mThreadNum && k != this.mThreadNum) {
						// 缩小到最小线程数
						i = 2;
					}
				} else {// 有等待的线程，扩大线程池
					if (m >= (this.mMaxThreadNum - k)) {// 扩大到最大的线程数
						i = 3;
					} else {
						i = 1;
						j = m + j;// 扩大到现在等待和活动线程数之和
					}
				}
				switch (i) {
				case (1):
					mPool.setCorePoolSize(j);
					mPool.setMaximumPoolSize(j);
					break;
				case (2):
					mPool.setCorePoolSize(this.mThreadNum);
					mPool.setMaximumPoolSize(this.mThreadNum);
					break;
				case (3):
					mPool.setCorePoolSize(this.mMaxThreadNum);
					mPool.setMaximumPoolSize(this.mMaxThreadNum);
					break;
				default:
					break;
				}
				MultiTaskServer.outPrint("现在计划的线程池大小是："
						+ mPool.getCorePoolSize());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 启动任务
	private void startTask() {
		int i = 0;
		int n = mTaskRunList.size();

		for (i = n - 1; i >= 0; i--) // 将运行结束的任务线程移出运行队列
		{
			Task tTask = (Task) mTaskRunList.get(i);

			if (!tTask.isAlive()) {
				// LDTaskPlanSchema tLDTaskPlanSchema = tTask.getTaskPlan();
				// tLDTaskPlanSchema.setRunState("0");
				// mMap.put(tLDTaskPlanSchema, "UPDATE");
				mDataChanged = true;
				mTaskRunList.remove(i);
			}
		}
		MultiTaskServer.outPrint("^_^The Pool Mode :" + this.tPoolMode
				+ " ^_^The ThreadNum is :" + this.mThreadNum
				+ " ^_^The MaxiThreadNum is : " + this.mMaxThreadNum);
		n = mTaskReadyList.size();

		for (i = 0; i < n; i++) // 运行就绪队列众中的任务并添加倒运行队列中
		{
			Task tTask = (Task) mTaskReadyList.get(i);

			try {
				// 开始运行任务(具体逻辑实现)
				if (tPool == null) {
					tTask.startTask();// 不使用线程池执行的计划
				} else {
					tTask.startTask(tPool);// 使用线程池执行的计划 modify by HuangLiang
					// 2011-12-02
				}
				mTaskRunList.add(tTask);

				// tLDTaskPlanSchema.setRunState("1");
				// mMap.put(tLDTaskPlanSchema, "UPDATE");
				mDataChanged = true;

				if (!CalculateNextRunTime(tTask)) {
					mErrors.addOneError(new CError("任务" + tTask.getTaskID()
							+ "启动异常，原因： 执行时间计算异常！"));
					// MultiTaskServer.outPrint("任务" + tTask.getTaskID()
					// + "启动异常，原因： 执行时间计算异常！");
					MultiTaskServer.outPrint("任务" + tTask.getTaskID()
							+ "启动异常，原因： 执行时间计算异常！");

					continue;
				}
			} catch (Exception ex) {
				String tEx = "";

				if (ex.getClass().getName().equals("NoTaskPlanException")) {
					tEx = "无任务计划!";
				} else if (ex.getClass().getName().equals("TaskLogException")) {
					tEx = "记录日志失败!";
				} else if (ex.getClass().getName()
						.equals("IllegalThreadStateException")) {
					tEx = "任务线程已经启动!";
				} else if (ex.getClass().getName().equals("Exception")) {
					tEx = "任务线程实例创建异常";
				}

				mErrors.addOneError(new CError("任务" + tTask.getTaskID()
						+ "启动异常，原因：" + tEx));

				MultiTaskServer.outPrint("任务" + tTask.getTaskID() + "启动异常，原因："
						+ tEx);
				ex.printStackTrace();
				LDTaskPlanSchema tLDTaskPlanSchema = tTask.getTaskPlan();
				tLDTaskPlanSchema.setRunFlag("0");
				tLDTaskPlanSchema.setRunState("5");
				mMap.put(tLDTaskPlanSchema, "UPDATE");
				mDataChanged = true;
			}
		}
		// 在启动任务后可以调整线程池大小
		fixPoolSize();
		mTaskReadyList.removeAllElements();
	}

	private void stopTask() {
		int i = 0;
		int n = mTaskRunList.size();

		for (i = n - 1; i >= 0; i--) // 结束运行队列中正在运行的线程
		{
			Task tTask = (Task) mTaskRunList.get(i);
			LDTaskPlanSchema tLDTaskPlanSchema = tTask.getTaskPlan();

			if (tTask.isAlive()) {
				try {
					tTask.stopTask();
					// modify by jiaqiangli 2009-04-14
					// 是否启用标志runflag不应该清0，否则前一天晚上没运行完，次日任务计划运行不起来
					// RunState启动时是会重置成0的 see also in scanTaskPlan
					// tLDTaskPlanSchema.setRunFlag("0");
					tLDTaskPlanSchema.setRunState("4");
					// modify by jiaqiangli 2009-04-14
					mTaskRunList.remove(i);
				} catch (Exception ex) {
					String tEx = "";

					if (ex.getClass().getName().equals("SecurityException")) {
						tEx = "任务安全性限制强行终止!";
					} else if (ex.getClass().getName()
							.equals("TaskLogException")) {
						tEx = "记录日志失败!";
					}

					mErrors.addOneError(new CError("任务" + tTask.getTaskID()
							+ "停止异常，原因：" + tEx));
					// MultiTaskServer.outPrint("任务" + tTask.getTaskID() +
					// "停止异常，原因："
					// + tEx);
					MultiTaskServer.outPrint("任务" + tTask.getTaskID()
							+ "停止异常，原因：" + tEx);

				}
			} else {
				tLDTaskPlanSchema.setRunState("3");
				mTaskRunList.remove(i);
			}

			mMap.put(tLDTaskPlanSchema, "UPDATE");
			mDataChanged = true;
		}
	}

	private boolean CalculateNextRunTime(Task aTask) {
		LDTaskPlanSchema tLDTaskPlanSchema = aTask.getTaskPlan();

		// getRunFrequence()得到运行了的次数;getTimes()得到任务需要运行的次数
		if ((tLDTaskPlanSchema.getTimes() > 0)
				&& (aTask.getRunFrequence() >= tLDTaskPlanSchema.getTimes())) {
			aTask.setNextRunTime("");

			return true;
		}

		String tNextTime = tLDTaskPlanSchema.getStartTime();

		try {
			String tCurrentTime = PubFun.getCurrentDate() + " "
					+ PubFun.getCurrentTime();
			// MultiTaskServer.outPrint("tCurrentTime:" + tCurrentTime);
			MultiTaskServer.outPrint("tCurrentTime:" + tCurrentTime);
			// tongmeng 2008-08-07 add
			// 增加crontab定制
			// 需要增加启动日期和当前日期的比较
			if (tLDTaskPlanSchema.getContabConfig() != null
					&& !tLDTaskPlanSchema.getContabConfig().trim().equals("")) {
				String tContabConfig = tLDTaskPlanSchema.getContabConfig();
				String[] tConfig = tContabConfig.split("#");
				tContabConfig = "";
				for (int i = 0; i < tConfig.length; i++) {
					if (i == 0) {
						tContabConfig = tConfig[i];
					} else {
						tContabConfig = tContabConfig + " " + tConfig[i];
					}
				}
				// MultiTaskServer.outPrint("###tContabConfig:" + tContabConfig
				// + "###");
				MultiTaskServer.outPrint("###tContabConfig:" + tContabConfig
						+ "###");
				// 如果启动日期比当前日期大,则使用启动日期进行推算
				// MultiTaskServer.outPrint("################:"+tLDTaskPlanSchema.getStartTime()+":"+tCurrentTime);
				Date actDate;
				if (tLDTaskPlanSchema.getStartTime().compareTo(tCurrentTime) > 0) {
					actDate = CrontabParser.calNext(tContabConfig,
							tSDF.parse(tLDTaskPlanSchema.getStartTime()));
				} else {
					actDate = CrontabParser.calNext(tContabConfig,
							tSDF.parse(tCurrentTime));
				}
				// MultiTaskServer.outPrint("**actDate:"+actDate+"**");
				tNextTime = tSDF.format(actDate);
				// MultiTaskServer.outPrint("TaskPlanCode:"+tLDTaskPlanSchema.getTaskPlanCode()+"***mNextRunTime:"
				// + tNextTime + "***");
				MultiTaskServer.outPrint("TaskPlanCode:"
						+ tLDTaskPlanSchema.getTaskPlanCode()
						+ "***mNextRunTime:" + tNextTime + "***");
			}
			// 以下是原有日期的推算方式,暂时保留
			else {
				Date tTime = Calendar.getInstance().getTime();
				Date date = tSDF.parse(tCurrentTime);
				// MultiTaskServer.outPrint("date.getTime():" + date.getTime());
				MultiTaskServer.outPrint("date.getTime():" + date.getTime());

				// 得到运行的时间
				Date date1 = tSDF.parse(tNextTime);
				// MultiTaskServer.outPrint("date1.getTime():" +
				// date1.getTime());
				MultiTaskServer.outPrint("date1.getTime():" + date1.getTime());

				long interval = date.getTime() - date1.getTime();
				MultiTaskServer.outPrint("interval:" + interval);

				long tInterval = new Double(tLDTaskPlanSchema.getInterval())
						.longValue();

				// zzm refer to tongmeng 2007-06-20 modify
				// 修改计算年、月运行的bug
				String tMYNextRunTime = "";
				int tOldTime = 0;

				tOldTime = Integer.parseInt(tLDTaskPlanSchema.getStartTime()
						.substring(11, 13))
						* 3600
						+ Integer.parseInt(tLDTaskPlanSchema.getStartTime()
								.substring(14, 16))
						* 60
						+ Integer.parseInt(tLDTaskPlanSchema.getStartTime()
								.substring(17, 19));
				int tNewTime = 0;
				tNewTime = Integer.parseInt(tCurrentTime.substring(11, 13))
						* 3600
						+ Integer.parseInt(tCurrentTime.substring(14, 16)) * 60
						+ Integer.parseInt(tCurrentTime.substring(17, 19));
				MultiTaskServer.outPrint("tOldTime:" + tOldTime + ":tNewTime:"
						+ tNewTime);

				// 每分钟一次
				if (tLDTaskPlanSchema.getRecycleType().equals("11")) {
					tInterval = 60;
				} else if (tLDTaskPlanSchema.getRecycleType().equals("12")) {
				}

				// 每小时一次
				else if (tLDTaskPlanSchema.getRecycleType().equals("21")) {
					tInterval = 60 * 60;
				} else if (tLDTaskPlanSchema.getRecycleType().equals("22")) {
				}

				// 每日一次
				else if (tLDTaskPlanSchema.getRecycleType().equals("31")) {
					tInterval = 24 * 60 * 60;
				} else if (tLDTaskPlanSchema.getRecycleType().equals("32")) {
				}

				// 每周一次
				else if (tLDTaskPlanSchema.getRecycleType().equals("41")) {
					tInterval = 7 * 24 * 60 * 60;
				} else if (tLDTaskPlanSchema.getRecycleType().equals("42")) {
				}

				// 每月一次
				else if (tLDTaskPlanSchema.getRecycleType().equals("51")) {
					// tongmeng 2007-06-20 modify
					// 增加计算BUG
					String tIntervalSQL = "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),"
							+ "months_between(last_day(to_date(substr('"
							+ "?tCurrentTime?" + "',1,10),'yyyy-mm-dd'))+1,"
							+ "last_day(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'))+1)) from dual "
							+ " where  substr('" + "?tCurrentTime?"
							+ "',9,2)-substr('"
							+ "?StartTime?" + "',9,2)<0 "
							+ " union all "
							+ "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),"
							+ "months_between(last_day(to_date(substr('"
							+ "?tCurrentTime?" + "',1,10),'yyyy-mm-dd'))+1,"
							+ "last_day(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'))+1)) from dual "
							+ " where  substr('" + "?tCurrentTime?"
							+ "',9,2)-substr('"
							+ "?StartTime?" + "',9,2)=0 "
							+ " and " + "?tOldTime?" + ">=" + "?tNewTime?" + " "
							+ " union all "
							+ "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),"
							+ "months_between(last_day(to_date(substr('"
							+ "?tCurrentTime?" + "',1,10),'yyyy-mm-dd'))+1,"
							+ "last_day(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'))+1)+1) from dual "
							+ " where  substr('" + "?tCurrentTime?"
							+ "',9,2)-substr('"
							+ "?StartTime?" + "',9,2)=0 "
							+ " and " + "?tOldTime?" + "<" + "?tNewTime?" + " "
							+ " union all "
							+ " select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),"
							+ "months_between(last_day(to_date(substr('"
							+ "?tCurrentTime?" + "',1,10),'yyyy-mm-dd'))+1,"
							+ "last_day(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'))+1)+1) from dual "
							+ " where  substr('" + "?tCurrentTime?"
							+ "',9,2)-substr('"
							+ "?StartTime?" + "',9,2)>0 ";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(tIntervalSQL);
					sqlbv4.put("StartTime", tLDTaskPlanSchema.getStartTime());
					sqlbv4.put("tCurrentTime", tCurrentTime);
					sqlbv4.put("tOldTime", tOldTime);
					sqlbv4.put("tNewTime", tNewTime);
					MultiTaskServer.outPrint("Month tIntervalSQL:"
							+ sqlbv4);
					tMYNextRunTime = new ExeSQL().getOneValue(sqlbv4);
					tMYNextRunTime = tMYNextRunTime.substring(0, 10)
							+ tLDTaskPlanSchema.getStartTime().substring(10);
					MultiTaskServer.outPrint("tMYNextRunTime1:"
							+ tMYNextRunTime);
					tInterval = 30 * 24 * 60 * 60;
				} else if (tLDTaskPlanSchema.getRecycleType().equals("52")) {
				}

				// 每年一次
				else if (tLDTaskPlanSchema.getRecycleType().equals("61")) {
					// zzm refter to tongmeng 2007-06-20 modify
					// 修改bug
					String tIntervalSQL = "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'),"
							+ "to_date(concat(substr('"
							+ "?StartTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'))) from dual "
							+ " where months_between(to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,4),substr('"
							+ "?tCurrentTime?"
							+ "',5,6)),'yyyy-mm-dd'),"
							+ " to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,4),substr('"
							+ "?StartTime?"
							+ "',5,6)),'yyyy-mm-dd'))<0 "
							+ " union all "
							+ "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'),"
							+ "to_date(concat(substr('"
							+ "?StartTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'))) from dual "
							+ " where months_between(to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,4),substr('"
							+ "?tCurrentTime?"
							+ "',5,6)),'yyyy-mm-dd'),"
							+ " to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,4),substr('"
							+ "?StartTime?"
							+ "',5,6)),'yyyy-mm-dd'))=0 "
							+ " and "
							+ "?tOldTime?"
							+ ">"
							+ "?tNewTime?"
							+ " union all "
							+ "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'),"
							+ "to_date(concat(substr('"
							+ "?StartTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'))+12) from dual "
							+ " where months_between(to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,4),substr('"
							+ "?tCurrentTime?"
							+ "',5,6)),'yyyy-mm-dd'),"
							+ " to_date(concat(substr('"
							+ "?tCurrentTime?"
							+ "',1,4),substr('"
							+ "?StartTime?"
							+ "',5,6)),'yyyy-mm-dd'))=0 "
							+ " and "
							+ "?tOldTime?"
							+ "<="
							+ "?tNewTime?"
							+ " "
							+ " union all "
							+ "select add_months(to_date(substr('"
							+ "?StartTime?"
							+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
							+ "?tCurrentTime?" + "',1,5),'01-01'),'yyyy-mm-dd'),"
							+ "to_date(concat(substr('"
							+ "?StartTime?"
							+ "',1,5),'01-01'),'yyyy-mm-dd'))+12) from dual "
							+ " where months_between(to_date(concat(substr('"
							+ "?tCurrentTime?" + "',1,4),substr('" + "?tCurrentTime?"
							+ "',5,6)),'yyyy-mm-dd')," + " to_date(concat(substr('"
							+ "?tCurrentTime?" + "',1,4),substr('"
							+ "?StartTime?"
							+ "',5,6)),'yyyy-mm-dd'))>0 ";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(tIntervalSQL);
					sqlbv5.put("StartTime", "?tCurrentTime?");
					sqlbv5.put("tCurrentTime", tCurrentTime);
					sqlbv5.put("tOldTime", tOldTime);
					sqlbv5.put("tNewTime", tNewTime);
					MultiTaskServer.outPrint("Year tIntervalSQL:"
							+ sqlbv5);
					tMYNextRunTime = new ExeSQL().getOneValue(sqlbv5);
					tMYNextRunTime = tMYNextRunTime.substring(0, 10)
							+ tLDTaskPlanSchema.getStartTime().substring(10);
					MultiTaskServer.outPrint("tMYNextRunTime year:"
							+ tMYNextRunTime);
					tInterval = 365 * 24 * 60 * 60;
				} else if (tLDTaskPlanSchema.getRecycleType().equals("62")) {
				}

				// 一次
				else if (tLDTaskPlanSchema.getRecycleType().equals("71")) {
					aTask.setNextRunTime("");

					return true;
				}

				// 如果为多次，则取LDTaskPlan中的interval(单位为分钟)
				else if (tLDTaskPlanSchema.getRecycleType().equals("72")) {
					// add by ck
					tInterval = tLDTaskPlanSchema.getInterval() * 60;
				}

				// times表示起始时间与现在时间的间隔
				long times = interval / (tInterval * 1000);

				MultiTaskServer.outPrint("times:" + times);

				times = times + Long.parseLong("1");
				MultiTaskServer.outPrint("times:" + times);

				long a = (tInterval * 1000 * times) + date1.getTime();

				tTime.setTime(a);
				tNextTime = tSDF.format(tTime);
				// zzm refer to tongmeng 2007-06-19
				// 对于如果每月和每年运行的话，使用前面通过SQL推出的时间
				if (tLDTaskPlanSchema.getRecycleType().equals("51")
						|| tLDTaskPlanSchema.getRecycleType().equals("61")) {
					tNextTime = tSDF.format(tSDF.parse(tMYNextRunTime));
				}
				MultiTaskServer.outPrint("mNextRunTime final:" + tNextTime);

			}
			String tEndTime = tLDTaskPlanSchema.getEndTime();

			if (!tNextTime.equals("")
					&& ((tEndTime == null) || tEndTime.equals("") || ((tEndTime != null)
							&& !tEndTime.equals("") && (tNextTime
							.compareTo(tLDTaskPlanSchema.getEndTime()) < 0)))) {
				aTask.setNextRunTime(tNextTime);
			} else {
				aTask.setNextRunTime("");
			}
		} catch (Exception ex) {
			mErrors.addOneError(new CError("计算任务执行时间异常！"));
			MultiTaskServer.outPrint("计算任务执行时间异常！");

			return false;
		}

		return true;
	}

	private boolean isExist(String tTaskPlanCode) {
		int i = 0;
		int n = mTaskWaitList.size();

		// 找出该任务在任务等待队列中是否存在
		for (i = 0; i < n; i++) {
			if (tTaskPlanCode.equals(((Task) mTaskWaitList.get(i)).getTaskID())) {
				return true;
			}
		}

		return false;
	}

	private int indexOf(String tTaskPlanCode) {
		int i = 0;
		int n = mTaskWaitList.size();

		for (i = 0; i < n; i++) {
			MultiTaskServer.outPrint("mTaskWaitList："
					+ ((Task) mTaskWaitList.get(i)).getTaskID());

			if (tTaskPlanCode.equals(((Task) mTaskWaitList.get(i)).getTaskID())) {
				MultiTaskServer.outPrint("i:" + i);

				return i;
			}
		}

		return -1;
	}

	private int indexOfRunList(String tTaskPlanCode) {
		int i = 0;
		int n = mTaskRunList.size();

		for (i = 0; i < n; i++) {
			MultiTaskServer.outPrint("mTaskRunList："
					+ ((Task) mTaskRunList.get(i)).getTaskID());

			if (tTaskPlanCode.equals(((Task) mTaskRunList.get(i)).getTaskID())) {
				MultiTaskServer.outPrint("i:" + i);

				return i;
			}
		}

		return -1;
	}

	// 启动服务
	public boolean startEngine() {
		synchronized (lock) {
			mMap = new MMap();
			mErrors.clearErrors();
			mData.clear();
			initServerInfo();

			// 扫描表ldtaskplan中的任务,加入到等待队列之中
			scanTaskPlan();

			// 重启服务时，将所有的运行状态初始化
			LDTaskPlanDB nLDTaskPlanDB = new LDTaskPlanDB();
			LDTaskPlanSet nLDTaskPlanSet = nLDTaskPlanDB
					.executeQuery(getRunTaskPlan("2"));

			for (int i = 1; i <= nLDTaskPlanSet.size(); i++) {
				if (!nLDTaskPlanSet.get(i).getRunState().equals("6")) {
					nLDTaskPlanSet.get(i).setRunState("0");
				}
			}

			logger.debug("nLDTaskPlanSet.size():" + nLDTaskPlanSet.size());
			mMap.put(nLDTaskPlanSet, "UPDATE");

			// 将启动任务加入到就绪队列中
			// searchReadyTask();
			// startTask();
			// add by ck
			if (!mDataChanged) {
				MultiTaskServer.outPrint("开始更新LDTaskPlan表");
				mData.clear();
				mData.add(mMap);

				PubSubmit tPubSubmit = new PubSubmit();

				if (!tPubSubmit.submitData(mData, "")) {
					mErrors.addOneError(new CError("任务计划数据更新失败！"));
					MultiTaskServer.outPrint("任务计划数据更新失败！");

					return false;
				}

				mDataChanged = false;
			}

			return !mErrors.needDealError();
		}
	}

	public boolean stopEngine() {
		synchronized (lock) {
			mMap = new MMap();
			mErrors.clearErrors();
			mData.clear();
			stopTask();
			mTaskRunList.removeAllElements();
			mTaskReadyList.removeAllElements();
			mTaskWaitList.removeAllElements();
			// 任务备份处理
			TransTaskPlan tTransTaskPlan = new TransTaskPlan();
			// 任务全部释放
			tTransTaskPlan.submitData(new VData(), "releaseall");
			if (mDataChanged) {
				mData.clear();
				mData.add(mMap);

				PubSubmit tPubSubmit = new PubSubmit();

				if (!tPubSubmit.submitData(mData, "")) {
					mErrors.addOneError(new CError("任务计划数据更新失败！"));
					MultiTaskServer.outPrint("任务计划数据更新失败！");

					return false;
				}

				mDataChanged = false;
			}
			if (tPool != null)
				tPool.shutdown();
			return mErrors.needDealError();
		}
	}

	public boolean addTask(LDTaskPlanSchema tLDTaskPlanSchema,
			LDTaskParamSet tLDTaskParamSet, LDTaskMsgItemSet tLDTaskMsgItemSet) {
		synchronized (lock) {
			mMap = new MMap();

			if (isExist(tLDTaskPlanSchema.getTaskPlanCode())) {
				mErrors.addOneError(new CError("任务计划"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "已经存在！"));
				MultiTaskServer.outPrint("任务计划"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "已经存在！");

				return false;
			}

			String tDecideSQL = "select 1 from ldtask  where taskcode='"
					+ "?taskcode?" + "' " + " union "
					+ " select 2 from ldtaskgroup where taskgroupcode='"
					+ "?taskcode?" + "'";
			SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
			sqlbv6.sql(tDecideSQL);
			sqlbv6.put("taskcode", tLDTaskPlanSchema.getTaskCode());
			String tDecideFlag = "";
			ExeSQL tExeSQL = new ExeSQL();
			tDecideFlag = tExeSQL.getOneValue(sqlbv6);
			String tTaskSQL = "";

			if (tDecideFlag == null || tDecideFlag.equals("")) {
				mErrors.addOneError(new CError("任务计划"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
						+ tLDTaskPlanSchema.getTaskCode() + "不存在！"));
				MultiTaskServer.outPrint("任务计划"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
						+ tLDTaskPlanSchema.getTaskCode() + "不存在！");
			} else if (tDecideFlag.equals("1")) {
				tTaskSQL = "select * from ldtask where taskcode='"
						+ "?taskcode?" + "' ";
			} else if (tDecideFlag.equals("2")) {
				tTaskSQL = "select a.* from ldtask a,ldtaskgroupdetail b   "
						+ " where a.taskcode=b.taskcode and b.taskgroupcode='"
						+ "?taskcode?" + "' "
						+ " order by b.taskorder ";
			}
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tTaskSQL);
			sqlbv7.put("taskcode", tLDTaskPlanSchema.getTaskCode());
			LDTaskDB tLDTaskDB = new LDTaskDB();
			// tLDTaskDB.setTaskCode(tLDTaskPlanSchema.getTaskCode());

			LDTaskSet tLDTaskSet = tLDTaskDB.executeQuery(sqlbv7);

			if ((tLDTaskSet == null) || (tLDTaskSet.size() <= 0)) {
				mErrors.addOneError(new CError("获取任务计划"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
						+ tLDTaskPlanSchema.getTaskCode() + "不存在！"));
				MultiTaskServer.outPrint("任务计划"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "中的任务"
						+ tLDTaskPlanSchema.getTaskCode() + "不存在！");

				return false;
			}

			// String tClassName = tLDTaskSet.get(1).getTaskClass().trim();

			try {
				// Task tTask = new Task(tClassName);
				// 2011-10-17 modify
				// 支持队列批处理的调用
				Task tTask = new Task(tLDTaskSet);

				tTask.SetTaskPlan(tLDTaskPlanSchema);

				if (tTask.getNextRunTime().equals("")) {
					tTask.setNextRunTime(tSDF.format(Calendar.getInstance()
							.getTime()));
				}

				if ((tLDTaskParamSet != null) && (tLDTaskParamSet.size() > 0)) {
					LDTaskParamSchema tLDTaskParamSchema = null;
					int n = tLDTaskParamSet.size();

					for (int i = 1; i <= n; i++) {
						tLDTaskParamSchema = tLDTaskParamSet.get(i);
						tTask.addParam(tLDTaskParamSchema.getParamName(),
								tLDTaskParamSchema.getParamValue());
					}
				}

				// 判断是否本服务器的任务再添加
				if (TaskServerManager.isCurrentServerPlan(tLDTaskParamSet,
						this.mRunType)) {
					mTaskWaitList.add(tTask);
				} else {
					MultiTaskServer.outPrint("任务:"
							+ tLDTaskPlanSchema.getTaskPlanCode()
							+ "不是本节点任务,不加到TaskWaitList中");
				}

			} catch (Exception ex) {
				mErrors.addOneError(new CError("创建任务实例失败！异常类型："
						+ ex.getClass().getName()));
				MultiTaskServer.outPrint("创建任务实例失败！异常类型："
						+ ex.getClass().getName());
			}

			mMap.put(tLDTaskPlanSchema, "INSERT");

			if ((tLDTaskParamSet != null) && (tLDTaskParamSet.size() > 0)) {
				mMap.put(tLDTaskParamSet, "INSERT");
			}

			if (tLDTaskMsgItemSet != null && tLDTaskMsgItemSet.size() > 0) {
				for (int i = 1; i <= tLDTaskMsgItemSet.size(); i++) {
					tLDTaskMsgItemSet.get(i).setTaskPlanCode(
							tLDTaskPlanSchema.getTaskPlanCode());
				}
				mMap.put(tLDTaskMsgItemSet, "INSERT");
			}

			mData.clear();
			mData.add(mMap);

			PubSubmit tPubSubmit = new PubSubmit();

			return tPubSubmit.submitData(mData, "");
		}
	}

	public boolean removeTask(LDTaskPlanSchema tLDTaskPlanSchema) {
		synchronized (lock) {
			mMap = new MMap();

			int i = indexOf(tLDTaskPlanSchema.getTaskPlanCode());

			if (i < 0) {
				// 如果没在本任务队列里,就不移除了.
				// mErrors.addOneError(new CError("任务计划"
				// + tLDTaskPlanSchema.getTaskPlanCode() + "不存在！"));
				// MultiTaskServer.outPrint("任务计划" +
				// tLDTaskPlanSchema.getTaskPlanCode()
				// + "不存在！");
				//
				// return false;

			} else {
				mTaskWaitList.remove(i);
			}
			mMap.put(tLDTaskPlanSchema, "DELETE");

			LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
			tLDTaskParamDB.setTaskCode(tLDTaskPlanSchema.getTaskCode());
			tLDTaskParamDB.setTaskPlanCode(tLDTaskPlanSchema.getTaskPlanCode());

			LDTaskParamSet tLDTaskParamSet = tLDTaskParamDB.query();

			if ((tLDTaskParamSet != null) && (tLDTaskParamSet.size() > 0)) {
				mMap.put(tLDTaskParamSet, "DELETE");
			}

			String tSQL = "delete from ldtaskmsgitem where taskplancode='"
					+ "?taskplancode?" + "' ";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tSQL);
			sqlbv8.put("taskplancode", tLDTaskPlanSchema.getTaskPlanCode());
			mMap.put(sqlbv8, "DELETE");
			mData.clear();
			mData.add(mMap);

			PubSubmit tPubSubmit = new PubSubmit();

			return tPubSubmit.submitData(mData, "");
		}
	}

	public boolean activateTask(LDTaskPlanSchema tLDTaskPlanSchema) {
		synchronized (lock) {
			mMap = new MMap();
			int i = indexOf(tLDTaskPlanSchema.getTaskPlanCode());

			if (i < 0) {
				MultiTaskServer.outPrint("任务:"
						+ tLDTaskPlanSchema.getTaskPlanCode() + "不在当前任务队列中");
				// mErrors.addOneError(new CError("任务计划"
				// + tLDTaskPlanSchema.getTaskPlanCode() + "不存在！"));
				// MultiTaskServer.outPrint("任务计划" +
				// tLDTaskPlanSchema.getTaskPlanCode()
				// + "不存在！");
				//
				// return false;
			} else {

				Task tTask = (Task) mTaskWaitList.get(i);
				tTask.reset();

				if (tTask.getNextRunTime().equals("")) {
					tTask.setNextRunTime(tSDF.format(Calendar.getInstance()
							.getTime()));
				}
			}
			// tLDTaskPlanSchema = tTask.getTaskPlan();
			tLDTaskPlanSchema.setRunFlag("1");
			tLDTaskPlanSchema.setRunState("0");

			mMap.put(tLDTaskPlanSchema, "UPDATE");
			mData.clear();
			mData.add(mMap);

			PubSubmit tPubSubmit = new PubSubmit();

			return tPubSubmit.submitData(mData, "");
		}
	}

	public boolean deactivateTask(LDTaskPlanSchema tLDTaskPlanSchema) {
		synchronized (lock) {
			mMap = new MMap();
			String tTaskPlanCode = tLDTaskPlanSchema.getTaskPlanCode();
			int i = indexOf(tTaskPlanCode);

			if (i < 0) {
				MultiTaskServer.outPrint("任务:" + tTaskPlanCode + "不在当前任务队列中");
				// mErrors.addOneError(new CError("任务计划"
				// + tLDTaskPlanSchema.getTaskPlanCode() + "不存在！"));
				// MultiTaskServer.outPrint("任务计划" +
				// tLDTaskPlanSchema.getTaskPlanCode()
				// + "不存在！");
				//
				// return false;
			} else {
				Task tTask = (Task) mTaskWaitList.get(i);
				// 直接移除
				this.mTaskWaitList.remove(i);
			}
			i = indexOfRunList(tTaskPlanCode);
			// 终止正在执行的任务 add by HuangLiang 2011-12-02
			if (i < 0) {
				MultiTaskServer.outPrint("任务:" + tTaskPlanCode + "不在运行任务队列中");
			} else {
				Task tTask = (Task) mTaskRunList.get(i);
				if (tTask.isAlive()) {
					try {
						tTask.stopTask();
						// modify by jiaqiangli 2009-04-14
						// 是否启用标志runflag不应该清0，否则前一天晚上没运行完，次日任务计划运行不起来
						// RunState启动时是会重置成0的 see also in scanTaskPlan
						// tLDTaskPlanSchema.setRunFlag("0");
						tLDTaskPlanSchema.setRunState("4");
						// modify by jiaqiangli 2009-04-14
						mTaskRunList.remove(i);
					} catch (Exception ex) {
						String tEx = "";
						if (ex.getClass().getName().equals("SecurityException")) {
							tEx = "任务安全性限制强行终止!";
						} else if (ex.getClass().getName()
								.equals("TaskLogException")) {
							tEx = "记录日志失败!";
						}
						mErrors.addOneError(new CError("任务" + tTask.getTaskID()
								+ "停止异常，原因：" + tEx));

						MultiTaskServer.outPrint("任务" + tTask.getTaskID()
								+ "停止异常，原因：" + tEx);
					}
				} else {
					tLDTaskPlanSchema.setRunState("3");
					mTaskRunList.remove(i);
				}
			}
			tLDTaskPlanSchema.setRunFlag("0");

			mMap.put(tLDTaskPlanSchema, "UPDATE");
			mData.clear();
			mData.add(mMap);

			PubSubmit tPubSubmit = new PubSubmit();

			return tPubSubmit.submitData(mData, "");
		}
	}

	private void initServerInfo() {
		try {
			// 先刷新系统参数
			MultiTaskServer.refreshServerParam();
			this.mServerType = MultiTaskServer.getServerType();
			this.mRunType = MultiTaskServer.getRunType();
			// 获得线程池模式
			this.tPoolMode = MultiTaskServer.getPoolMode();
			if ("fix".equalsIgnoreCase(tPoolMode)) {
				this.mThreadNum = Integer.parseInt(MultiTaskServer
						.getthreadNum());
				this.mMaxThreadNum = Integer.parseInt(MultiTaskServer
						.getMaxthreadNum());
				if (this.mThreadNum <= 0)
					this.mThreadNum = 5;
				if (this.mMaxThreadNum <= this.mThreadNum)
					this.mMaxThreadNum = this.mThreadNum;
				this.tPool = Executors.newFixedThreadPool(this.mThreadNum);
			} else if ("cache".equalsIgnoreCase(tPoolMode)) {
				this.tPool = Executors.newCachedThreadPool();
			} else {
				this.tPool = null;
			}
			// LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
			// tLDTaskParamDB.setTaskCode("000000");
			// tLDTaskParamDB.setTaskPlanCode("000000");
			// //tLDTaskParamDB.setParamName("ServerType");
			// LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
			// tLDTaskParamSet = tLDTaskParamDB.query();
			// for(int i=1;i<=tLDTaskParamSet.size();i++)
			// {
			// String tParamName = tLDTaskParamSet.get(i).getParamName();
			// if(tParamName!=null&&tParamName.toLowerCase().equals("servertype"))
			// {
			// this.mServerType = tLDTaskParamSet.get(i).getParamValue();
			// }
			// else
			// if(tParamName!=null&&tParamName.toLowerCase().equals("runtype"))
			// {
			// this.mRunType = tLDTaskParamSet.get(i).getParamValue();
			// }
			//
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		synchronized (lock) {
			mMap = new MMap();
			mData.clear();

			// 每次都重新扫描,防止有变化的节点
			MultiTaskServer.outPrint("begin scanTaskPlan");
			scanTaskPlan();
			MultiTaskServer.outPrint("begin searchReadyTask");
			searchReadyTask();
			MultiTaskServer.outPrint("begin startTask");
			startTask();
			MultiTaskServer.outPrint("end startTask");

			// 更新活动节点服务信息
			TaskServerManager.refreshServerInfo();

			// 任务备份处理
			TransTaskPlan tTransTaskPlan = new TransTaskPlan();
			// 任务转移
			tTransTaskPlan.submitData(new VData(), "trans");
			// 任务释放
			tTransTaskPlan.submitData(new VData(), "release");

			if (mDataChanged) {
				mData.clear();
				mData.add(mMap);

				PubSubmit tPubSubmit = new PubSubmit();
				tPubSubmit.submitData(mData, "");
				// 日志处理
				// LogProcessor.dealQueue();
				mDataChanged = false;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		TaskServiceEngine mTaskServiceEngine = new TaskServiceEngine();
		mTaskServiceEngine.startEngine();
		Runnable command = new Runnable() {
			@Override
			public void run() {
				int b = 0;
				while (b < 4) {
					try {
						b++;
						System.out.print(b);
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(b);
			}
		};
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		tPool.execute(command);
		mTaskServiceEngine.stopEngine();
		mTaskServiceEngine.startEngine();
		tPool.execute(command);
		while (mTaskServiceEngine != null) {
			Thread.sleep(1000L);
		}
	}
}
