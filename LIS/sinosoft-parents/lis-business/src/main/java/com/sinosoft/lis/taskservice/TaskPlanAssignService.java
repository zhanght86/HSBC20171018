package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sinosoft.lis.tb.SelAssignServiceBL;

/**
 * <p>
 * Title: WEBLOGIC重启后自动运行
 * </p>
 * <p>
 * Description: 新契约任务自动分配
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */
public class TaskPlanAssignService implements ServletContextListener {
private static Logger logger = Logger.getLogger(TaskPlanAssignService.class);
	public TaskPlanAssignService() {
	}
	//计时器
	private static Timer mServiceTimer;
	private static SelAssignServiceBL mSelAssignServiceBL;

	
	public void contextDestroyed(ServletContextEvent arg0) {
		mServiceTimer.cancel();
		logger.debug("停止自动分配服务引擎完毕!");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("开始启动自动分配服务引擎!");
		
		try {
			mServiceTimer = new Timer();
			mSelAssignServiceBL = new SelAssignServiceBL();
			//每隔10秒扫描一次.
			mServiceTimer.schedule(mSelAssignServiceBL, 1000, 10000);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
