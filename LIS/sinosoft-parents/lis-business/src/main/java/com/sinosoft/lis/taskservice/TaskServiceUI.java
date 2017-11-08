/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import java.util.Timer;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDTaskParamSchema;
import com.sinosoft.lis.schema.LDTaskPlanSchema;
import com.sinosoft.lis.schema.LDTaskSchema;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskPlanSet;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
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
public class TaskServiceUI implements BusinessService{
private static Logger logger = Logger.getLogger(TaskServiceUI.class);
	private static TaskServiceEngine mTaskServiceEngine;
	private static Timer mTaskServiceTimer;
	private static boolean mTimerRunning;
	private String mOperator;
	private LDTaskPlanSchema mLDTaskPlanSchema;
	private LDTaskParamSet mLDTaskParamSet;
	private VData mResult = new VData();

	// private MMap mMap = new MMap();
	public CErrors mErrors = new CErrors();

	public TaskServiceUI() {
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
	public boolean submitData(VData aInputData, String aOperate) {
		int tOperateResult = 0;
		mResult.clear();

		if (aOperate == null) {
			tOperateResult = -1;
		}
		if(aOperate.toUpperCase().equals("START")
			||aOperate.toUpperCase().equals("STOP")
			||aOperate.toUpperCase().equals("GETSTATE")
			||aOperate.toUpperCase().equals("INSERTTASK")
			||aOperate.toUpperCase().equals("DELETETASK")
			||aOperate.toUpperCase().equals("INSERT")
			||aOperate.toUpperCase().equals("DELETE")
			||aOperate.toUpperCase().equals("ACTIVATE")
			||aOperate.toUpperCase().equals("DEACTIVATE")
		)
		{
			TaskService tTaskService = new TaskService();
			tOperateResult = tTaskService.submitData(aInputData, aOperate);
			this.mErrors = tTaskService.mErrors;
		
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
		else if(aOperate.toUpperCase().equals("INSERTUSERTAB")
				||aOperate.toUpperCase().equals("DELETEUSERTAB")
				)
		{
			TaskTabSet tTaskTabSet = new TaskTabSet();

			if (!tTaskTabSet.submitData(aInputData, aOperate)) {
				tOperateResult = -1;
			}
			mErrors = tTaskTabSet.mErrors;
			//tOperateResult = 0;
		}
		else if(aOperate.toUpperCase().equals("SERVERCONFIG")
				||aOperate.toUpperCase().equals("TASKSERVERCONFIG"))
		{
			TaskServerConfig tTaskServerConfig = new TaskServerConfig();

			if (!tTaskServerConfig.submitData(aInputData, aOperate)) {
				tOperateResult = -1;
			}
			mErrors = tTaskServerConfig.mErrors;
		}
		
			
		
		if(tOperateResult<0)
		{
			return false;
		}
		else
		{
			return true;
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
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
