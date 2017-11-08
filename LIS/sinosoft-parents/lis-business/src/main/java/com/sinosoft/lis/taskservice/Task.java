/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sinosoft.lis.db.LDTaskRunLogDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskPlanSchema;
import com.sinosoft.lis.schema.LDTaskRunLogSchema;
import com.sinosoft.lis.taskservice.crontab.CrontabParser;
import com.sinosoft.lis.vschema.LDTaskRunLogSet;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 任务基类
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
public class Task // extends Thread
{
private static Logger logger = Logger.getLogger(Task.class);

	private static int mSerialNo;

	private static final int THREAD_READY = 0;

	private static final int THREAD_RUNNING = 1;

	private static final int THREAD_SUSPEND = 2;

	private static final int THREAD_RESUME = 3;

	private static final int THREAD_TERMINATE = 4;

	private Thread mTaskThread;

	private String mThreadName;

	private String mTaskID;

	private LDTaskPlanSchema mLDTaskPlanSchema;

	private LDTaskRunLogSchema mLDTaskRunLogSchema;

	private String mRunTime;

	private String mNextRunTime;

	private int mRunFrequence;

	private int mState;

	private String mTaskResult;

	private int mThreadState; // 任务状态，在重载run()时根据状态进行线程的相应处理

	protected HashMap Parameters;

	private SimpleDateFormat tSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ExeSQL tExeSQL = new ExeSQL();

	private LDTaskSet mLDTaskSet = new LDTaskSet();

	private int RunDays = 0;

	private TaskThread tTaskThread;// 单任务全局变量

	private TaskThreadQ tTaskThreadQ;// 任务队列全局变量

	/**
	 * 为了判断计划的线程是否alive，使用线程池全局变量（TaskServiceEngine传来的）
	 */
	private ExecutorService mPool = null;

	/**
	 * 
	 */
	private boolean mPoolFlag = false;

	public Task(String aThreadName) {
		mThreadName = aThreadName;
		mLDTaskRunLogSchema = new LDTaskRunLogSchema();
		Parameters = new HashMap();
		mRunTime = "";
		mRunFrequence = 0;
		mState = 0;
		mTaskResult = "";
		mSerialNo = 0;
		mThreadState = THREAD_READY;
	}

	public Task(LDTaskSet aTaskSet) {
		mLDTaskSet = aTaskSet;
		if (mLDTaskSet.size() == 1) {
			mThreadName = mLDTaskSet.get(1).getTaskClass();
		} else {
			mThreadName = "";
		}
		// mThreadName = aThreadName;
		mLDTaskRunLogSchema = new LDTaskRunLogSchema();
		Parameters = new HashMap();
		mRunTime = "";
		mRunFrequence = 0;
		mState = 0;
		mTaskResult = "";
		mSerialNo = 0;
		mThreadState = THREAD_READY;
	}

	public String getTaskID() {
		return mTaskID;
	}

	public LDTaskPlanSchema getTaskPlan() {
		return mLDTaskPlanSchema;
	}

	public void SetTaskPlan(LDTaskPlanSchema aLDTaskPlanSchema) {
		mLDTaskPlanSchema = aLDTaskPlanSchema;

		if (mLDTaskPlanSchema != null) {
			mTaskID = mLDTaskPlanSchema.getTaskPlanCode();
			mNextRunTime = mLDTaskPlanSchema.getStartTime();

			// add by ck(增加定时功能)
			String tCurrentTime = PubFun.getCurrentDate() + " "
					+ PubFun.getCurrentTime();
			MultiTaskServer.outPrint("tCurrentTime:" + tCurrentTime);

			try {

				// tongmeng 2008-08-07 modify
				// 支持crontab方式定制
				if (mLDTaskPlanSchema.getContabConfig() != null
						&& !mLDTaskPlanSchema.getContabConfig().trim().equals(
								"")) {
					String tContabConfig = mLDTaskPlanSchema.getContabConfig();
					String[] tConfig = tContabConfig.split("#");
					tContabConfig = "";
					for (int i = 0; i < tConfig.length; i++) {
						if (i == 0) {
							tContabConfig = tConfig[i];
						} else {
							tContabConfig = tContabConfig + " " + tConfig[i];
						}
					}
					MultiTaskServer.outPrint("##tContabConfig:" + tContabConfig
							+ "##");
					// 如果启动日期比当前日期大,则使用启动日期进行推算
					// MultiTaskServer.outPrint("################:"+mLDTaskPlanSchema.getStartTime()+":"+tCurrentTime);
					Date actDate;
					if (mLDTaskPlanSchema.getStartTime()
							.compareTo(tCurrentTime) > 0) {
						actDate = CrontabParser.calNext(tContabConfig, tSDF
								.parse(mLDTaskPlanSchema.getStartTime()));
					} else {
						actDate = CrontabParser.calNext(tContabConfig, tSDF
								.parse(tCurrentTime));
					}
					// MultiTaskServer.outPrint("**actDate:"+actDate+"**");
					// Date actDate = CrontabParser.calNext(tContabConfig, tSDF
					// .parse(tCurrentTime));
					// MultiTaskServer.outPrint("**actDate:"+actDate+"**");
					mNextRunTime = tSDF.format(actDate);
					MultiTaskServer.outPrint("TaskPlanCode:"
							+ mLDTaskPlanSchema.getTaskPlanCode()
							+ "**mNextRunTime:" + mNextRunTime + "**");
				} else {
					// 得到现在的时间
					Date date = tSDF.parse(tCurrentTime);
					MultiTaskServer.outPrint("date.getTime():" + date.getTime());

					// 得到运行的时间
					Date date1 = tSDF.parse(mNextRunTime);
					MultiTaskServer.outPrint("date1.getTime():" + date1.getTime());

					long interval = date.getTime() - date1.getTime();
					MultiTaskServer.outPrint("interval:" + interval);

					// tongmeng 2007-06-20 modify
					// 修改计算年、月运行的bug
					String tMYNextRunTime = "";
					long tInterval = new Double(mLDTaskPlanSchema.getInterval())
							.longValue();
					int tOldTime = 0;

					tOldTime = Integer.parseInt(mLDTaskPlanSchema
							.getStartTime().substring(11, 13))
							* 3600
							+ Integer.parseInt(mLDTaskPlanSchema.getStartTime()
									.substring(14, 16))
							* 60
							+ Integer.parseInt(mLDTaskPlanSchema.getStartTime()
									.substring(17, 19));
					int tNewTime = 0;
					tNewTime = Integer.parseInt(tCurrentTime.substring(11, 13))
							* 3600
							+ Integer.parseInt(tCurrentTime.substring(14, 16))
							* 60
							+ Integer.parseInt(tCurrentTime.substring(17, 19));
					MultiTaskServer.outPrint("tOldTime:" + tOldTime + ":tNewTime:"
							+ tNewTime);
					// 每分钟一次
					if (mLDTaskPlanSchema.getRecycleType().equals("11")) {
						tInterval = 60;
					} else if (mLDTaskPlanSchema.getRecycleType().equals("12")) {
					}

					// 每小时一次
					else if (mLDTaskPlanSchema.getRecycleType().equals("21")) {
						tInterval = 60 * 60;
					} else if (mLDTaskPlanSchema.getRecycleType().equals("22")) {
					}

					// 每日一次
					else if (mLDTaskPlanSchema.getRecycleType().equals("31")) {
						tInterval = 24 * 60 * 60;
					} else if (mLDTaskPlanSchema.getRecycleType().equals("32")) {
					}

					// 每周一次
					else if (mLDTaskPlanSchema.getRecycleType().equals("41")) {
						tInterval = 7 * 24 * 60 * 60;
					} else if (mLDTaskPlanSchema.getRecycleType().equals("42")) {
					}

					// 每月一次
					else if (mLDTaskPlanSchema.getRecycleType().equals("51")) {
						// tongmeng 2007-06-20 modify
						// 增加计算BUG
						String tIntervalSQL = "select add_months(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd'),"
								+ "months_between(ADDDATE(LAST_DAY(to_date(substr('"
								+ "?tCurrentTime?"
								+ "',1,10),'yyyy-mm-dd')),1),"
								+ "ADDDATE(LAST_DAY(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd')),1))) from dual "
								+ " where substr('"
								+ "?tCurrentTime?"
								+ "',9,2)-substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',9,2)<0 "
								+ " union all "
								+ "select add_months(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd'),"
								+ "months_between(ADDDATE(LAST_DAY(to_date(substr('"
								+ "?tCurrentTime?"
								+ "',1,10),'yyyy-mm-dd')),1),"
								+ "ADDDATE(LAST_DAY(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd')),1))) from dual "
								+ " where  substr('"
								+ "?tCurrentTime?"
								+ "',9,2)-substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',9,2)=0 "
								+ " and "
								+ "?tOldTime?"
								+ ">="
								+ "?tNewTime?"
								+ " "
								+ " union all "
								+ "select add_months(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd'),"
								+ "months_between(ADDDATE(LAST_DAY(to_date(substr('"
								+ "?tCurrentTime?"
								+ "',1,10),'yyyy-mm-dd')),1),"
								+ "ADDDATE(LAST_DAY(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd')),1))+1) from dual "
								+ " where  substr('"
								+ "?tCurrentTime?"
								+ "',9,2)-substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',9,2)=0 "
								+ " and "
								+ "?tOldTime?"
								+ "<"
								+ "?tNewTime?"
								+ " "
								+ " union all "
								+ " select add_months(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd'),"
								+ "months_between(ADDDATE(LAST_DAY(to_date(substr('"
								+ "?tCurrentTime?"
								+ "',1,10),'yyyy-mm-dd')),1),"
								+ "ADDDATE(LAST_DAY(to_date(substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',1,10),'yyyy-mm-dd')),1))+1) from dual "
								+ " where  substr('"
								+ "?tCurrentTime?"
								+ "',9,2)-substr('"
								+ "?mLDTaskPlanSchema?"
								+ "',9,2)>0 ";
						SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
						sqlbv1.sql(tIntervalSQL);
						sqlbv1.put("mLDTaskPlanSchema", mLDTaskPlanSchema.getStartTime());
						sqlbv1.put("tCurrentTime", tCurrentTime);
						sqlbv1.put("tNewTime", tNewTime);
						sqlbv1.put("tOldTime", tOldTime);
					
						logger.debug("Month tIntervalSQL:" + tIntervalSQL);
						tMYNextRunTime = tExeSQL.getOneValue(sqlbv1);
						tMYNextRunTime = tMYNextRunTime.substring(0, 10)
								+ mLDTaskPlanSchema.getStartTime()
										.substring(10);
						MultiTaskServer.outPrint("tMYNextRunTime1:" + tMYNextRunTime);
						tInterval = 30 * 24 * 60 * 60;
					} else if (mLDTaskPlanSchema.getRecycleType().equals("52")) {
					}

					// 每年一次
					else if (mLDTaskPlanSchema.getRecycleType().equals("61")) {
						// tongmeng 2007-06-20 modify
						// 修改bug
						String tIntervalSQL = "select add_months(to_date(substr('"
								+ "?mLDTaskP?"
								+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'),"
								+ "to_date(concat(substr('"
								+ "?mLDTaskP?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'))) from dual "
								+ " where months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?Time?"
								+ "',5,6)),'yyyy-mm-dd'),"
								+ " to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?mLDTaskP?"
								+ "',5,6)),'yyyy-mm-dd'))<0 "
								+ " union all "
								+ "select add_months(to_date(substr('"
								+ "?mLDTaskP?"
								+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'),"
								+ "to_date(concat(substr('"
								+ "?mLDTaskP?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'))) from dual "
								+ " where months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?Time?"
								+ "',5,6)),'yyyy-mm-dd'),"
								+ " to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?mLDTaskP?"
								+ "',5,6)),'yyyy-mm-dd'))=0 "
								+ " and "
								+ "?tOld?"
								+ ">"
								+ "?tNew?"
								+ " union all "
								+ "select add_months(to_date(substr('"
								+ "?mLDTaskP?"
								+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'),"
								+ "to_date(concat(substr('"
								+ "?mLDTaskP?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'))+12) from dual "
								+ " where months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?Time?"
								+ "',5,6)),'yyyy-mm-dd'),"
								+ " to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?mLDTaskP?"
								+ "',5,6)),'yyyy-mm-dd'))=0 "
								+ " and "
								+ "?tOld?"
								+ "<="
								+ "?tNew?"
								+ " "
								+ " union all "
								+ "select add_months(to_date(substr('"
								+ "?mLDTaskP?"
								+ "',1,10),'yyyy-mm-dd'),months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'),"
								+ "to_date(concat(substr('"
								+ "?mLDTaskP?"
								+ "',1,5),'01-01'),'yyyy-mm-dd'))+12) from dual "
								+ " where months_between(to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?Time?"
								+ "',5,6)),'yyyy-mm-dd'),"
								+ " to_date(concat(substr('"
								+ "?Time?"
								+ "',1,4),substr('"
								+ "?mLDTaskP?"
								+ "',5,6)),'yyyy-mm-dd'))>0 ";
						SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
						sqlbv2.sql(tIntervalSQL);
						sqlbv2.put("mLDTaskP", mLDTaskPlanSchema.getStartTime());
						sqlbv2.put("Time", tCurrentTime);
						sqlbv2.put("tNew", tNewTime);
						sqlbv2.put("tOld", tOldTime);
					
						MultiTaskServer.outPrint("Year tIntervalSQL:" + tIntervalSQL);
						tMYNextRunTime = tExeSQL.getOneValue(sqlbv2);
						tMYNextRunTime = tMYNextRunTime.substring(0, 10)
								+ mLDTaskPlanSchema.getStartTime()
										.substring(10);
						MultiTaskServer.outPrint("tMYNextRunTime year:"
								+ tMYNextRunTime);
						tInterval = 365 * 24 * 60 * 60;
					} else if (mLDTaskPlanSchema.getRecycleType().equals("62")) {
					}

					// 一次
					else if (mLDTaskPlanSchema.getRecycleType().equals("71")) {
						this.setNextRunTime("");
					}

					// 如果为多次，则取LDTaskPlan中的interval(单位为分钟)
					else if (mLDTaskPlanSchema.getRecycleType().equals("72")) {
						// add by ck
						tInterval = mLDTaskPlanSchema.getInterval() * 60;
					}

					// times表示起始时间与现在时间的间隔
					long times = interval / (tInterval * 1000);
					MultiTaskServer.outPrint("times:" + times);

					times = times + Long.parseLong("1");
					MultiTaskServer.outPrint("times:" + times);

					long a = (tInterval * 1000 * times) + date1.getTime();
					Date tTime = Calendar.getInstance().getTime();

					// 初始化开始时间
					tTime.setTime(date1.getTime());
					mNextRunTime = tSDF.format(tTime);
					MultiTaskServer.outPrint("mNextRunTime:" + mNextRunTime);

					// 下次运行时间
					if (date.getTime() > date1.getTime()) {
						tTime.setTime(a);
						mNextRunTime = tSDF.format(tTime);
						MultiTaskServer.outPrint("mNextRunTime 1:" + mNextRunTime);
						// tongmeng 2007-06-19
						// 对于如果每月和每年运行的话，使用前面通过SQL推出的时间
						if (mLDTaskPlanSchema.getRecycleType().equals("51")
								|| mLDTaskPlanSchema.getRecycleType().equals(
										"61")) {
							mNextRunTime = tSDF.format(tSDF
									.parse(tMYNextRunTime));
						}
						logger.debug("mNextRunTime final:" + mNextRunTime);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}

			if (mNextRunTime == null) {
				mNextRunTime = "";
			}
		}
	}

	public String getNextRunTime() {
		return mNextRunTime;
	}

	public boolean setNextRunTime(String aNextRunTime) {
		mRunTime = mNextRunTime;
		mNextRunTime = aNextRunTime;

		if (mNextRunTime == null) {
			mNextRunTime = "";
		}

		MultiTaskServer.outPrint("Task " + mTaskID + " Set NextRunTime: "
				+ aNextRunTime);

		return true;
	}

	public long getRunFrequence() {
		return mRunFrequence;
	}

	public void reset() {
		mRunTime = "";
		mRunFrequence = 0;
		mState = 0;
		mTaskResult = "";
		mThreadState = THREAD_READY;
	}

	/**
	 * 
	 * @throws IllegalThreadStateException
	 * @throws NoTaskPlanException
	 * @throws TaskLogException
	 * @throws Exception
	 */
	public void startTask() throws IllegalThreadStateException,
			NoTaskPlanException, TaskLogException, Exception {
		if (mLDTaskPlanSchema == null) {
			MultiTaskServer.outPrint("Task " + mTaskID
					+ " Error Occur: stopTask NoTaskPlanException");
			throw new NoTaskPlanException();
		}

		mThreadState = THREAD_RUNNING;
		mRunFrequence++;

		// 更新任务日志表中的日志
		if (!logTask("1")) {
			throw new TaskLogException();
		}

		// 2011-10-17 modify
		// 队列的处理
		if (this.mLDTaskSet.size() == 1) {

			try {
				Class tTaskClass = Class
						.forName("com.sinosoft.lis.taskservice.taskinstance."
								+ mThreadName);

				// 开启一个线程处理任务
				tTaskThread = (TaskThread) tTaskClass.newInstance();

				// add by ck(传入参数)
				// tongmeng 2008-05-22 modify
				// 修改传参没有把数据库的参数传到实例中的问题
				// HashMap tHashMap = new HashMap();
				this.Parameters.put("TaskPlanCode", mTaskID);
				this.Parameters
						.put("TaskCode", mLDTaskPlanSchema.getTaskCode());
				this.Parameters.put("TaskGroupCode", "AAAAAA");
				// 日志监控增加
				this.Parameters.put("SerialNo", this.mSerialNo);
				MultiTaskServer.outPrint("mLDTaskPlanSchema.getTaskCode():"
						+ mLDTaskPlanSchema.getTaskCode());
				tTaskThread.setParameter(Parameters);

				mTaskThread = new Thread(tTaskThread);
				MultiTaskServer.outPrint("任务计划" + mTaskID + "运行！ 运行次数："
						+ mRunFrequence + " 运行时间：" + mRunTime);
				mTaskThread.start();
			} catch (IllegalThreadStateException ex) {
				throw new IllegalThreadStateException();
			} catch (Exception ex) {
				throw new Exception();
			}
		} else {
			try {

				tTaskThreadQ = new TaskThreadQ();
				tTaskThreadQ.setTaskSet(this.mLDTaskSet);
				tTaskThreadQ.setTaskID(this.mTaskID);
				tTaskThreadQ.setTaskPlan(this.mLDTaskPlanSchema);
				tTaskThreadQ.setParameter(Parameters);
				this.Parameters.put("TaskPlanCode", mTaskID);
				// this.Parameters.put("TaskCode","AAAAAA");
				this.Parameters.put("TaskGroupCode", mLDTaskPlanSchema
						.getTaskCode());
				// 日志监控增加
				this.Parameters.put("SerialNo", this.mSerialNo);
				MultiTaskServer.outPrint("mLDTaskPlanSchema.getTaskCode():"
						+ mLDTaskPlanSchema.getTaskCode());
				mTaskThread = new Thread(tTaskThreadQ);
				MultiTaskServer.outPrint("任务计划" + mTaskID + "运行！ 运行次数："
						+ mRunFrequence + " 运行时间：" + mRunTime);
				mTaskThread.start();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param tPool
	 *            传入公用线程池
	 * @throws IllegalThreadStateException
	 * @throws NoTaskPlanException
	 * @throws TaskLogException
	 * @throws Exception
	 */
	public void startTask(ExecutorService tPool)
			throws IllegalThreadStateException, NoTaskPlanException,
			TaskLogException, Exception {
		// 给线程池的全局指针赋值
		this.mPool = tPool;
		// 使用线程池，修改标记
		this.mPoolFlag = true;

		if (mLDTaskPlanSchema == null) {
			MultiTaskServer.outPrint("Task " + mTaskID
					+ " Error Occur: stopTask NoTaskPlanException");
			throw new NoTaskPlanException();
		}

		mThreadState = THREAD_RUNNING;
		mRunFrequence++;

		// 更新任务日志表中的日志
		if (!logTask("1")) {
			throw new TaskLogException();
		}

		// 2011-10-17 modify
		// 队列的处理
		if (this.mLDTaskSet.size() == 1) {

			try {
				Class tTaskClass = Class
						.forName("com.sinosoft.lis.taskservice.taskinstance."
								+ mThreadName);

				// 开启一个线程处理任务
				tTaskThread = (TaskThread) tTaskClass.newInstance();

				// add by ck(传入参数)
				// tongmeng 2008-05-22 modify
				// 修改传参没有把数据库的参数传到实例中的问题
				// HashMap tHashMap = new HashMap();
				this.Parameters.put("TaskPlanCode", mTaskID);
				this.Parameters
						.put("TaskCode", mLDTaskPlanSchema.getTaskCode());
				this.Parameters.put("TaskGroupCode", "AAAAAA");
				// 日志监控增加
				this.Parameters.put("SerialNo", this.mSerialNo);
				MultiTaskServer.outPrint("mLDTaskPlanSchema.getTaskCode():"
						+ mLDTaskPlanSchema.getTaskCode());
				tTaskThread.setParameter(Parameters);

				mTaskThread = new Thread(tTaskThread);
				MultiTaskServer.outPrint("任务计划" + mTaskID + "运行！ 运行次数："
						+ mRunFrequence + " 运行时间：" + mRunTime);
				tPool.execute(mTaskThread);// 使用线程池 modify by HuangLiang
				// 2011-11-30
				// mTaskThread.start();
			} catch (IllegalThreadStateException ex) {
				throw new IllegalThreadStateException();
			} catch (Exception ex) {
				throw new Exception();
			}
		} else {
			try {

				tTaskThreadQ = new TaskThreadQ();
				tTaskThreadQ.setTaskSet(this.mLDTaskSet);
				tTaskThreadQ.setTaskID(this.mTaskID);
				tTaskThreadQ.setTaskPlan(this.mLDTaskPlanSchema);
				tTaskThreadQ.setParameter(Parameters);
				this.Parameters.put("TaskPlanCode", mTaskID);
				// this.Parameters.put("TaskCode","AAAAAA");
				this.Parameters.put("TaskGroupCode", mLDTaskPlanSchema
						.getTaskCode());
				// 日志监控增加
				this.Parameters.put("SerialNo", this.mSerialNo);
				MultiTaskServer.outPrint("mLDTaskPlanSchema.getTaskCode():"
						+ mLDTaskPlanSchema.getTaskCode());
				mTaskThread = new Thread(tTaskThreadQ);
				MultiTaskServer.outPrint("任务计划" + mTaskID + "运行！ 运行次数："
						+ mRunFrequence + " 运行时间：" + mRunTime);
				tPool.execute(mTaskThread);// 使用线程池 modify by HuangLiang
				// 2011-11-30
				// mTaskThread.start();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param aSeconds
	 *            long
	 * @throws InterruptedException
	 * @throws TaskLogException
	 */
	public void suspendTask(long aSeconds) throws InterruptedException,
			TaskLogException {
		mThreadState = THREAD_SUSPEND;

		try {
			Thread.sleep(aSeconds * 1000);
		} catch (InterruptedException ex) {
			throw new InterruptedException();
		}

		mThreadState = THREAD_RUNNING;
	}

	/**
	 * 
	 * @throws SecurityException
	 * @throws TaskLogException
	 */
	public void stopTask() throws SecurityException, TaskLogException {
		mThreadState = THREAD_TERMINATE;

		MultiTaskServer.outPrint("STOP!!!!!!!!!!!!!!!!!!!");
		if (!this.mPoolFlag) {
			// 未使用线程池
			if (mTaskThread.isAlive()) {
				try {
					// 停止多线程队列 add by HuangLiang 2011-11-30
					logger.debug("#_#+_+Stoping Task Pool+_+^_^+_+mTaskID："
									+ this.mTaskID + "+_+#_#");
					if (this.mLDTaskSet.size() == 1) {

						this.tTaskThread.stopTask();
					} else {
						this.tTaskThreadQ.stopTask("1");
					}
				} catch (SecurityException ex) {
					MultiTaskServer.outPrint("Task " + mTaskID
							+ " Exception Occur: stopTask SecurityException");
					throw new SecurityException();
				}
			}
		} else {
			// 使用线程池
			try {
				// 停止多线程队列 add by HuangLiang 2011-11-30
				MultiTaskServer.outPrint("#_#+_+Stoping Task Pool+_+^_^+_+mTaskID："
						+ this.mTaskID + "+_+#_#");
				if (this.mLDTaskSet.size() == 1) {
					if (this.tTaskThread.isAlive())
						this.tTaskThread.stopTask();
				} else {
					if (this.tTaskThreadQ.isAlive())
						this.tTaskThreadQ.stopTask("1");
				}
			} catch (SecurityException ex) {
				MultiTaskServer.outPrint("Task " + mTaskID
						+ " Exception Occur: stopTask SecurityException");
				throw new SecurityException();
			}
		}
		if (!logTask("4")) {
			throw new TaskLogException();
		}
	}

	/**
	 * 返回计划的线程是否还在运行，modify by HuangLiang 2011-12-02
	 * 
	 * @return boolean
	 */
	public boolean isAlive() {
		if (!this.mPoolFlag) {
			// 未使用线程池
			return mTaskThread.isAlive();
		} else {
			// 使用了线程池
			if (this.mLDTaskSet.size() == 1) {
				return this.tTaskThread.isAlive();
			} else {
				return this.tTaskThreadQ.isAlive();
			}
		}
	}

	/**
	 * 
	 * @param aState
	 *            String
	 * @return boolean
	 */
	private boolean logTask(String aState) {
		VData tData = new VData();
		MMap tMap = new MMap();

		if (mSerialNo == 0) {
			LDTaskRunLogDB tLDTaskRunLogDB = new LDTaskRunLogDB();
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql("select * from LDTaskRunLog where SerialNo = (select MAX(SerialNo) from LDTaskRunLog)");
			LDTaskRunLogSet tLDTaskRunLogSet = tLDTaskRunLogDB
					.executeQuery(sqlbv3);

			if (tLDTaskRunLogDB.mErrors.needDealError()
					|| (tLDTaskRunLogSet.size() > 1)) {
				MultiTaskServer.outPrint("Task " + mTaskID
						+ " Error Occur: logTask Fail");

				return false;
			} else if ((tLDTaskRunLogSet == null)
					|| (tLDTaskRunLogSet.size() == 0)) {
				mSerialNo = 0;
			} else {
				mSerialNo = tLDTaskRunLogSet.get(1).getSerialNo();
			}
		}

		mLDTaskRunLogSchema.setExecuteState(aState);

		if (aState.equals("1")) // 1:任务启动
		{
			mSerialNo++;

			// mLDTaskRunLogSchema.setSerialNo(mSerialNo);
			mLDTaskRunLogSchema.setSerialNo(mSerialNo);
			mLDTaskRunLogSchema.setTaskCode(mLDTaskPlanSchema.getTaskCode());
			mLDTaskRunLogSchema.setTaskPlanCode(mLDTaskPlanSchema
					.getTaskPlanCode());
			mLDTaskRunLogSchema.setExecuteDate(PubFun.getCurrentDate());
			mLDTaskRunLogSchema.setExecuteTime(PubFun.getCurrentTime());

			// mLDTaskRunLogSchema.setExecuteFrequence(mRunFrequence);
			mLDTaskRunLogSchema.setExecuteFrequence(mRunFrequence);

			// tMap.put(mLDTaskRunLogSchema, "INSERT");
		}

		if (aState.equals("2")) // 2:暂停
		{
			// tMap.put(mLDTaskRunLogSchema, "UPDATE");
		}

		if (aState.equals("3") || aState.equals("4") || aState.equals("5")) // 3:正常终止
		// 4:强行终止
		// 5:异常终止
		{
			mLDTaskRunLogSchema.setFinishDate(PubFun.getCurrentDate());
			mLDTaskRunLogSchema.setFinishTime(PubFun.getCurrentTime());

			// tMap.put(mLDTaskRunLogSchema, "UPDATE");
		}

		tData.add(tMap);

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(tData, "")) {
			MultiTaskServer.outPrint("Task " + mTaskID
					+ " Error Occur: logTask Data Submit Fail");

			return false;
		}

		return true;
	}

	public void addParam(String ParamName, String ParamValue) {
		Parameters.put(ParamName, ParamValue);
	}
}
