package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sinosoft.utility.GlobalPools;

/**
 * <p>
 * Title: WEBLOGIC重启后自动运行
 * </p>
 * <p>
 * Description: 多数据库连接池配置文件自动加载类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */
public class MultDBStartService implements ServletContextListener {
private static Logger logger = Logger.getLogger(MultDBStartService.class);
	private GlobalPools mGlobalPools = GlobalPools.getInstance();

	public MultDBStartService() {
	}


	public void contextDestroyed(ServletContextEvent arg0) {
		mGlobalPools.refreshRuleProperties();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("开始自动加载多连接池配置!");
		// 自动加载多连接池配置
		logger.debug("begin ....");
		mGlobalPools.initProperties();
	}
}
