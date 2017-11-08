package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Vector;


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.taskservice.MultiTaskServer;

public class TestTask extends TaskThread {
private static Logger logger = Logger.getLogger(TestTask.class);
	public CErrors mErrors = new CErrors();

	public TestTask() {
	}

	public boolean dealMain() {
		try {

			GlobalInput tG = new GlobalInput();
			tG.ComCode = "86";
			tG.Operator = "AUTO";
			tG.ManageCom = "86";
			tG.LogID[0] = "TASK" + (String) mParameters.get("TaskCode");
			tG.LogID[1] = (String) mParameters.get("SerialNo");

			// 多线程执行
			Vector tTaskList = new Vector();
			// 准备多线程任务
			String tServerIP = MultiTaskServer.getServerIP();//业务数据逻辑：服务器IP，写入表
			String tServerPort =  MultiTaskServer.getServerPort();//业务数据逻辑：服务器端口Port，写入表
			String tServerType = MultiTaskServer.getServerType();//业务数据逻辑：服务器类型，暂不写入表
			logger.debug(tServerIP);
			logger.debug(tServerPort);
			logger.debug(tServerType);
			String testParam1 = (String) mParameters.get("testParam1");//外部传入“其他参数”，可用于业务数据，也可用于其他设置

			// 日志监控,状态测试
			PubFun.logState(tG, this.getClass().getSimpleName() + "2", this
					.getClass().getSimpleName()+"5", "0");
			for (int i = 1; i <= 30; i++) {
				// 设置任务队列
				VData mInputData = new VData();
				TransferData mTransferData = new TransferData();

				mTransferData.setNameAndValue("TaskListNo", String.valueOf(i));// 日志监控数据：任务编号
				mTransferData.setNameAndValue("GlobalInput", tG);// 日志监控数据：全局变量
				
				mTransferData.setNameAndValue("ActivityID", String.valueOf(i));// 业务逻辑数据
				mTransferData.setNameAndValue("ServerIP", tServerIP);//业务逻辑数据
				mTransferData.setNameAndValue("ServerPort", tServerPort);//业务逻辑数据
				mTransferData.setNameAndValue("ServerType", tServerType);//业务逻辑数据
//				mTransferData.setNameAndValue("ServerIP", tServerIP);//业务逻辑数据
				
				mInputData.add(mTransferData);
				tTaskList.add(mInputData);
				// 日志监控,过程监控
				// PubFun.logTrack (tG,"KeyNo"+i,"开始处理KeyNo"+i);//在执行任务前写入
				// 日志监控,结果监控
				// PubFun.logResult(tG,"KeyNo"+i,"处理结果是KeyNo"+i);//在执行任务后写入
			}

			// 任务信息
			String TaskCode = (String) this.mParameters.get("TaskGroupCode")
					+ ":" + (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			Date start = new Date();
			// 初始化多线程任务
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.taskservice.taskinstance.TestTaskMthread",
					tTaskList, 10, 10, TaskCode);
			// 启动任务
			this.mServiceA.start();
			Date end = new Date();
			String finalTime = String.valueOf(end.getTime() - start.getTime());
			logger.debug("final:" + finalTime + "TaskCode" + TaskCode);
			// this.startService();
			// 日志监控,性能监控
			PubFun.logPerformance(tG, "TestPerformance", "总共处理100单,花费"
					+ finalTime + "秒", "0");
			// 日志监控,状态测试
			PubFun.logState(tG, this.getClass().getSimpleName() + "2", this
					.getClass().getSimpleName()+"3", "1");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		} finally {
		}
	}

	public static void main(String[] args) {
		TestTask tUWAutoMThread = new TestTask();
		tUWAutoMThread.dealMain();
	}
}
