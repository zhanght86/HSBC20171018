/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import java.util.Timer;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDTaskPlanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LDTaskParamSchema;
import com.sinosoft.lis.schema.LDTaskPlanSchema;
import com.sinosoft.lis.schema.LDTaskSchema;
import com.sinosoft.lis.vschema.LDTaskMsgItemSet;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskPlanSet;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 后台任务处理主控模块
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
public class TaskService {
private static Logger logger = Logger.getLogger(TaskService.class);
	private static TaskServiceEngine mTaskServiceEngine;
	private static Timer mTaskServiceTimer;
	private static boolean mTimerRunning;
	private String mOperator;
	private LDTaskPlanSchema mLDTaskPlanSchema;
	private LDTaskParamSet mLDTaskParamSet;
	private LDTaskMsgItemSet mLDTaskMsgItemSet;
	private VData mResult = new VData();

	// private MMap mMap = new MMap();
	public CErrors mErrors = new CErrors();

	public TaskService() {
		/*
		 * if (mTaskServiceEngine == null) { mTaskServiceEngine = new
		 * TaskServiceEngine(); }
		 * 
		 * if (mTaskServiceTimer == null) { mTaskServiceTimer = new Timer(); }
		 */
	}

	/**
	 * 后台服务提交
	 * 
	 * @param aInputData
	 *            VData
	 * @param aOperate
	 *            String
	 * @return int return 0:正常 -1:异常且timer未启动 其他:部分异常，timer启动
	 */
	public int submitData(VData aInputData, String aOperate) {
		int tOperateResult = 0;
		mResult.clear();

		if (aOperate == null) {
			tOperateResult = -1;
		}

		// 启动服务
		if (aOperate.toUpperCase().equals("START")) {
			tOperateResult = serviceStart();
		} else if (aOperate.toUpperCase().equals("STOP")) {
			tOperateResult = serviceStop();
		} else if (aOperate.toUpperCase().equals("GETSTATE")) {
			tOperateResult = getServiceState();
		} else if (aOperate.toUpperCase().equals("INSERTTASK")
				|| aOperate.toUpperCase().equals("DELETETASK")) {
			TaskSet tTaskSet = new TaskSet();

			if (!tTaskSet.submitData(aInputData, aOperate)) {
				tOperateResult = -1;
			}

			//tOperateResult = 0;
		} 
		else if(aOperate.toUpperCase().equals("INSERTTASKGROUP")
				||aOperate.toUpperCase().equals("DELETETASKGROUP")
				||aOperate.toUpperCase().equals("INSERTTASKGROUPDETAIL")
				||aOperate.toUpperCase().equals("DELETETASKGROUPDETAIL")
				)
		{
			TaskGroupSet tTaskGroupSet = new TaskGroupSet();

			if (!tTaskGroupSet.submitData(aInputData, aOperate)) {
				tOperateResult = -1;
			}
			mErrors = tTaskGroupSet.mErrors;
			//tOperateResult = 0;
		}
		else {
			if (!mTimerRunning) {
				mErrors.addOneError(new CError("任务服务引擎尚未启动！"));
				tOperateResult = -1;
			} else {
				boolean tResultFlag = true;

				if (!getInputData(aInputData)) {
					tOperateResult = -1;
				} else if (aOperate.toUpperCase().equals("INSERT")) {
					tResultFlag = insertTask();
				} else if (aOperate.toUpperCase().equals("DELETE")) {
					tResultFlag = deleteTask();
				} else if (aOperate.toUpperCase().equals("ACTIVATE")) {
					tResultFlag = activateTask();
				} else if (aOperate.toUpperCase().equals("DEACTIVATE")) {
					tResultFlag = deactivateTask();
				}

				if (!tResultFlag) {
					tOperateResult = -1;
				}
			}
		}

		MultiTaskServer.outPrint("return: " + Integer.toString(tOperateResult));

		return tOperateResult;
	}

	private int serviceStart() {
		int tResult = 0;

		if (mTimerRunning) {
			mErrors.addOneError(new CError("任务引擎已经启动!"));

			return -2;
		}

		LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();

		// 查询要启动的引擎信息
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql("select * from LDTaskPlan where TaskPlanCode = '000000'");
		LDTaskPlanSet tLDTaskPlanSet = tLDTaskPlanDB.executeQuery(sqlbv1);

		if ((tLDTaskPlanSet == null) || (tLDTaskPlanSet.size() <= 0)) {
			mErrors.copyAllErrors(tLDTaskPlanDB.mErrors);
			mErrors.addOneError(new CError("无法取得任务引擎信息!"));
			tResult = -1;
		}

		MultiTaskServer.outPrint("取得任务引擎信息!");

		LDTaskPlanSchema tLDTaskPlanSchema = tLDTaskPlanSet.get(1);

		try {
			//初始化服务结点信息
			if(!TaskServerManager.initCurrentServerInfo())
			{
				mErrors.addOneError(new CError("启动失败,原因是:初始化服务结点信息出现异常！"));
				tResult = 1;
			}
			
			mTaskServiceEngine = new TaskServiceEngine();

			if (!mTaskServiceEngine.startEngine()) {
				mErrors.copyAllErrors(TaskServiceEngine.mErrors);
				mErrors.addOneError(new CError("任务引擎启动中出现异常，部分任务未能启动！"));
				tResult = 1;
			}

			long l = new Double(tLDTaskPlanSchema.getInterval()).longValue();
			mTaskServiceTimer = new Timer();

			// 该方法会在指定的延时后执行任务，并且在设定的周期定时执行任务
			// 就是说，延迟1ms，每隔1ms执行
			// mTaskServiceTimer.schedule(mTaskServiceEngine, l, l);
			// 设定1秒后开始启动监听，每隔10秒执行一次run()操作。
			mTaskServiceTimer.schedule(mTaskServiceEngine, 1000, 10000);
			mTimerRunning = true;
			MultiTaskServer.outPrint("后台任务服务引擎启动！");
		} catch (IllegalArgumentException iex) {
			mErrors.addOneError(new CError("间隔时间时间有误!"));
			tResult = -1;
		} catch (Exception ex) {
			mErrors.addOneError(new CError("任务起动异常!"));
			tResult = -1;
		}

		return tResult;
	}

	// 停止服务
	private int serviceStop() {
		int tResult = 0;

		// 如果Timer序列中没有要执行的任务则不必执行cancel，否则会出错。
		if (mTimerRunning) {
			mTaskServiceTimer.cancel();
		}

		if (!mTimerRunning) {
			mErrors.addOneError(new CError("任务引擎尚未启动!"));

			tResult = -1;
		} else if (!mTaskServiceEngine.stopEngine()) {
			mErrors.copyAllErrors(TaskServiceEngine.mErrors);
			mErrors.addOneError(new CError("任务引擎终止中出现异常，部分任务未能终止！"));
			tResult = 1;
			mTaskServiceEngine = null;
			mTaskServiceTimer = null;
			mTimerRunning = false;
		}
		
		//将服务结点信息设置为失效状态
		try {
			TaskServerManager.invalidServerInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
		}

		MultiTaskServer.outPrint("后台任务服务引擎停止！");

		return tResult;
	}

	private int getServiceState() {
		if (mTimerRunning) {
			mResult.add(new String("RUNNING"));
		} else {
			mResult.add(new String("SLEEPING"));
		}

		return 0;
	}

	private boolean getInputData(VData aInputData) {
		GlobalInput tGI = (GlobalInput) aInputData.getObjectByObjectName(
				"GlobalInput", 0);
		LDTaskPlanSet tLDTaskPlanSet = (LDTaskPlanSet) aInputData
				.getObjectByObjectName("LDTaskPlanSet", 0);
		LDTaskParamSet tLDTaskParamSet = (LDTaskParamSet) aInputData
				.getObjectByObjectName("LDTaskParamSet", 0);
		LDTaskMsgItemSet tLDTaskMsgItemSet = (LDTaskMsgItemSet) aInputData
				.getObjectByObjectName("LDTaskMsgItemSet", 0);
		
		if ((tGI == null) || (tLDTaskPlanSet == null)
				|| (tLDTaskPlanSet.size() <= 0)) {
			mErrors.addOneError(new CError("传入数据不完全！"));

			return false;
		}

		mLDTaskParamSet = tLDTaskParamSet;
		mLDTaskPlanSchema = tLDTaskPlanSet.get(1);
		this.mLDTaskMsgItemSet = tLDTaskMsgItemSet;
		mOperator = tGI.Operator;

		return true;
	}

	/**
	 * 增加任务操作,增加的任务编码是找到最大的编码然后加1
	 * 
	 * @return boolean
	 */
	private boolean insertTask() {
		if ((mLDTaskPlanSchema.getTaskCode() == null)
				|| mLDTaskPlanSchema.getTaskCode().equals("")) {
			mErrors.addOneError(new CError("缺少任务信息!"));

			return false;
		}

		int tCodeInt = 0;
		//直接改成用PunFun1 创建流水号 
		String tCodeString = "000000";
		/*
		LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
		LDTaskPlanSet tLDTaskPlanSet = tLDTaskPlanDB
				.executeQuery("select * from LDTaskPlan where TaskPlanCode = (select MAX(TaskPlanCode) from LDTaskPlan)"); // 取最大序列号

		if ((tLDTaskPlanSet != null) && (tLDTaskPlanSet.size() > 0)) {
			tCodeString = tLDTaskPlanSet.get(1).getTaskPlanCode();
		}

		try {
			tCodeInt = Integer.parseInt(tCodeString);
			tCodeInt++;
			tCodeString = Integer.toString(tCodeInt);
			tCodeString = "000000".substring(1, 6 - tCodeString.length() + 1)
					+ tCodeString; // 生成新任务计划编码
		} catch (Exception ex) {
			return false;
		}
		*/
		tCodeString = PubFun1.CreateMaxNo("TaskPlanCode", 6);
		mLDTaskPlanSchema.setTaskPlanCode(tCodeString);

		if ((mLDTaskPlanSchema.getRunFlag() == null)
				|| mLDTaskPlanSchema.getRunFlag().equals("")) {
			mLDTaskPlanSchema.setRunFlag("1"); // 未指明是否启动时默认为启动
		}

		mLDTaskPlanSchema.setRunState("0"); // 初始状态为等待
		mLDTaskPlanSchema.setOperator(mOperator);
		mLDTaskPlanSchema.setMakeDate(PubFun.getCurrentDate());
		mLDTaskPlanSchema.setMakeTime(PubFun.getCurrentTime());
		mLDTaskPlanSchema.setModifyDate(PubFun.getCurrentDate());
		mLDTaskPlanSchema.setModifyTime(PubFun.getCurrentTime());

		if (mLDTaskParamSet != null) // 如果有参数
		{
			int n = mLDTaskParamSet.size();
			//针对服务结点信息单独处理下

			for (int i = 1; i <= n; i++) {
				LDTaskParamSchema tLDTaskParamSchema = mLDTaskParamSet.get(i);
				if(tLDTaskParamSchema.getParamName()!=null&&tLDTaskParamSchema.getParamName().toLowerCase().equals("serverip:port"))
				{
					if(tLDTaskParamSchema.getParamValue()==null||tLDTaskParamSchema.getParamValue().equals(""))
					{
						String ServerInfo="";
						//如果为空,默认设置成在本机运行
						MultiTaskServer tMultiTaskServer=new MultiTaskServer(); //Server
			        	try{
			           	 ServerInfo=tMultiTaskServer.getServerIP()+":"+String.valueOf(tMultiTaskServer.getServerPort()); //Server
			          	}catch(Exception ex)
			         	{
			           		ex.printStackTrace();
			            	ServerInfo="";
			          	}
			          	tLDTaskParamSchema.setParamValue(ServerInfo);
					}
				}
				if ((tLDTaskParamSchema.getParamName() == null)
						|| tLDTaskParamSchema.getParamName().equals("")
						|| (tLDTaskParamSchema.getParamValue() == null)
						|| tLDTaskParamSchema.getParamValue().equals("")) {
					mErrors.addOneError(new CError("参数信息不完全!"));

					return false;
				}

				for (int j = 1; (j != i) && (j <= n); j++) {
					if (tLDTaskParamSchema.getParamName().equals(
							mLDTaskParamSet.get(j).getParamName())) // 判断参数名是否有重复
					{
						mErrors.addOneError(new CError("参数名重复!"));

						return false;
					}
				}

				tLDTaskParamSchema.setTaskPlanCode(mLDTaskPlanSchema
						.getTaskPlanCode());
				tLDTaskParamSchema.setTaskCode(mLDTaskPlanSchema.getTaskCode());
				tLDTaskParamSchema.setOperator(mOperator);
				tLDTaskParamSchema.setMakeDate(PubFun.getCurrentDate());
				tLDTaskParamSchema.setMakeTime(PubFun.getCurrentTime());
				tLDTaskParamSchema.setModifyDate(PubFun.getCurrentDate());
				tLDTaskParamSchema.setModifyTime(PubFun.getCurrentTime());
			}
		}

		//
		if(!mTaskServiceEngine.addTask(mLDTaskPlanSchema, mLDTaskParamSet,this.mLDTaskMsgItemSet))
		{
			this.mErrors.addOneError(mTaskServiceEngine.mErrors.getLastError());
			return false;
		}
		
		return true;
	}

	/**
	 * 删除任务操作
	 * 
	 * @return boolean
	 */
	private boolean deleteTask() {
		LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
		tLDTaskPlanDB.setTaskPlanCode(mLDTaskPlanSchema.getTaskPlanCode());

		LDTaskPlanSet tLDTaskPlanSet = tLDTaskPlanDB.query();

		if ((tLDTaskPlanSet == null) || (tLDTaskPlanSet.size() <= 0)) {
			mErrors.addOneError(new CError("未查到相应的任务计划！"));

			return false;
		}

		mLDTaskPlanSchema = tLDTaskPlanSet.get(1);

		return mTaskServiceEngine.removeTask(mLDTaskPlanSchema);
	}

	private boolean activateTask() {
		LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
		tLDTaskPlanDB.setTaskPlanCode(mLDTaskPlanSchema.getTaskPlanCode());

		LDTaskPlanSet tLDTaskPlanSet = tLDTaskPlanDB.query();

		if ((tLDTaskPlanSet == null) || (tLDTaskPlanSet.size() <= 0)) {
			mErrors.addOneError(new CError("未查到相应的任务计划！"));

			return false;
		}

		mLDTaskPlanSchema = tLDTaskPlanSet.get(1);
		mLDTaskPlanSchema.setRunFlag("1");

		return mTaskServiceEngine.activateTask(mLDTaskPlanSchema);
	}

	private boolean deactivateTask() {
		LDTaskPlanDB tLDTaskPlanDB = new LDTaskPlanDB();
		tLDTaskPlanDB.setTaskPlanCode(mLDTaskPlanSchema.getTaskPlanCode());

		LDTaskPlanSet tLDTaskPlanSet = tLDTaskPlanDB.query();

		if ((tLDTaskPlanSet == null) || (tLDTaskPlanSet.size() <= 0)) {
			mErrors.addOneError(new CError("未查到相应的任务计划！"));

			return false;
		}

		mLDTaskPlanSchema = tLDTaskPlanSet.get(1);
		mLDTaskPlanSchema.setRunFlag("0");

		return mTaskServiceEngine.deactivateTask(mLDTaskPlanSchema);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		TaskService taskService = new TaskService();
		VData tData = new VData();
		taskService.submitData(tData, "START");

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ComCode = "86";
		tGI.ManageCom = "86";

		LDTaskPlanSchema tLDTaskPlanSchema = new LDTaskPlanSchema();
		LDTaskPlanSet tLDTaskPlanSet = new LDTaskPlanSet();
		LDTaskParamSchema tLDTaskParamSchema;
		LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
		LDTaskSchema tLDTaskSchema = new LDTaskSchema();
		LDTaskSet tLDTaskSet = new LDTaskSet();

		tLDTaskPlanSchema.setTaskCode("000002");
		tLDTaskPlanSchema.setRunFlag("0");
		tLDTaskPlanSchema.setRunState("0");
		tLDTaskPlanSchema.setRecycleType("72");
		tLDTaskPlanSchema.setInterval(5000);
		tLDTaskPlanSet.add(tLDTaskPlanSchema);
		tLDTaskParamSchema = new LDTaskParamSchema();

		tLDTaskParamSchema.setParamName("P1");
		tLDTaskParamSchema.setParamValue("V1");
		tLDTaskParamSet.add(tLDTaskParamSchema);
		tLDTaskParamSchema.setParamName("P2");
		tLDTaskParamSchema.setParamValue("V2");
		tLDTaskParamSet.add(tLDTaskParamSchema);

		tData.clear();
		tData.add(tGI);
		tData.add(tLDTaskPlanSet);
		tData.add(tLDTaskParamSet);

		// taskService.submitData(tData, "INSERT");
		tLDTaskSchema.setTaskCode("000001");
		tLDTaskSchema.setTaskDescribe("task 001");
		tLDTaskSchema.setTaskClass("TestTask");
		tLDTaskSet.add(tLDTaskSchema);
		tData.clear();
		tData.add(tGI);
		tData.add(tLDTaskPlanSet);
		tData.add(tLDTaskParamSet);
		tData.add(tLDTaskSet);

		// taskService.submitData(tData, "INSERTTASK");
		try {
			// Thread.sleep(5000);
		} catch (Exception ex) {
		}

		tLDTaskPlanSchema = new LDTaskPlanSchema();
		tLDTaskPlanSet = new LDTaskPlanSet();
		tLDTaskPlanSchema.setTaskPlanCode("000003");
		tLDTaskPlanSet.add(tLDTaskPlanSchema);

		tData.clear();
		tData.add(tGI);
		tData.add(tLDTaskPlanSet);
		tData.add(tLDTaskParamSet);

		taskService.submitData(tData, "ACTIVATE");

		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
		}

		taskService.submitData(tData, "ACTIVATE");

		tLDTaskPlanSchema = new LDTaskPlanSchema();
		tLDTaskPlanSet = new LDTaskPlanSet();
		tLDTaskPlanSchema.setTaskPlanCode("000001");
		tLDTaskPlanSet.add(tLDTaskPlanSchema);

		tData.clear();
		tData.add(tGI);
		tData.add(tLDTaskPlanSet);

		// taskService.submitData(tData, "DEACTIVATE");
		try {
			Thread.sleep(2000);
		} catch (Exception ex) {
		}

		tLDTaskPlanSchema = new LDTaskPlanSchema();
		tLDTaskPlanSet = new LDTaskPlanSet();
		tLDTaskPlanSchema.setTaskPlanCode("000003");
		tLDTaskPlanSet.add(tLDTaskPlanSchema);

		tData.clear();
		tData.add(tGI);
		tData.add(tLDTaskPlanSet);

		// taskService.submitData(tData, "DELETE");
		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
		}

		tData.clear();
		taskService.submitData(tData, "STOP");
	}
}
