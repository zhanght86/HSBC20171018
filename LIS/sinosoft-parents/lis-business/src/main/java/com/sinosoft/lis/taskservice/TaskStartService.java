package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskPlanSet;
import com.sinosoft.lis.vschema.LDTaskSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: WEBLOGIC重启后自动运行
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author CK
 * @version 1.0
 */
public class TaskStartService implements ServletContextListener {
private static Logger logger = Logger.getLogger(TaskStartService.class);
	public TaskStartService() {
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		MultiTaskServer.outPrint("开始停止自动运行服务引擎!");

		CErrors mErrors = new CErrors();

		GlobalInput tG = new GlobalInput();
		tG.Operator = "AUTO";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		LDTaskPlanSet tLDTaskPlanSet = new LDTaskPlanSet();
		LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
		LDTaskSet tLDTaskSet = new LDTaskSet();

		VData tData = new VData();
		tData.add(tG);
		tData.add(tLDTaskPlanSet);
		tData.add(tLDTaskParamSet);
		tData.add(tLDTaskSet);

		TaskService tTaskService = new TaskService();
		try {
			if (tTaskService.submitData(tData, "STOP") < 0) {
				MultiTaskServer.outPrint("控制台停止失败!");
				CError tError = new CError();
				tError.moduleName = "TaskStartService";
				tError.functionName = "submitData";
				tError.errorMessage = "服务停止失败!";
				mErrors.addOneError(tError);
			} else {
				MultiTaskServer.outPrint("-------自动运行服务控制台停止了-------");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		MultiTaskServer.outPrint("开始启动自动运行服务引擎!");

		CErrors mErrors = new CErrors();

		GlobalInput tG = new GlobalInput();
		tG.Operator = "AUTO";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		LDTaskPlanSet tLDTaskPlanSet = new LDTaskPlanSet();
		LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
		LDTaskSet tLDTaskSet = new LDTaskSet();

		VData tData = new VData();
		tData.add(tG);
		tData.add(tLDTaskPlanSet);
		tData.add(tLDTaskParamSet);
		tData.add(tLDTaskSet);

		TaskService tTaskService = new TaskService();
		try {
			if (tTaskService.submitData(tData, "START") < 0) {
				MultiTaskServer.outPrint("控制台启动失败!");
				CError tError = new CError();
				tError.moduleName = "TaskStartService";
				tError.functionName = "submitData";
				tError.errorMessage = "服务启动失败!";
				mErrors.addOneError(tError);
			} else {
				MultiTaskServer.outPrint("-------自动运行服务控制台启动-------");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
