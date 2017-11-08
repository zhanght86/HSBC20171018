package com.sinosoft.service;
import org.apache.log4j.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.utility.CErrors;

/**
 * <p>Title: 核心转发程序</p>
 *
 * <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 */
public class LocalService implements LService{
private static Logger logger = Logger.getLogger(LocalService.class);
	private static LocalService localService;
    private Handle handle ;
    /**是否初始化成功标志*/
    private static boolean initflag = false;
    private String info;
    /**service容器*/
    private BusinessFactory businessFactory = BusinessFactory.getFactory();
    public static String classinfo = "com.sinosoft.service.LocalService";
    public static Log log = LogFactory.getLog(classinfo);
    
    /**添加核心全局处理方法*/
    public void setGlobalHandle(Handle handle)
    {
    	this.handle = handle;
    }
    /**添加Service服务*/
    public void addService(Service service)
    {
    	this.businessFactory.addService(service);
    }
    /**移除service服务*/
    public void removeService(Service service)
    {
    	this.businessFactory.removeService(service);
    }
    /**获取服务名对应的服务容器*/
    public Service getService(String serviceName)
    {
    	return this.businessFactory.getService(serviceName);
    }
    /**构造函数*/
    private LocalService() {
    	init();
    }
    
    /**初始化  通过XML描述 初始化所有相关Handle*/
    private void init()
    {
    	try
    	{
			ServiceParse tServiceParse = new ServiceParse(this);
			tServiceParse.parses();
			initflag = true;
			log.debug("初始化成功!");
		}
    	catch(ServiceException ex)
    	{
    		initflag = false;
    		info = ex.getMessage();
    		log.error(info);
    	}
    	catch(Exception ex)
    	{
    		initflag = false;
    		info = ex.getMessage();
    		log.error(info);
    	}
    }
    
    /**获取转发服务处理实现*/
    public static synchronized LService getLocalService()
    {
    	if(!initflag)
    	{
    		localService = new LocalService();
    	}
    	return localService;
    }
    
    /**实际处理调用*/
    public void doService(IMessage message)
    {
    	try
    	{
    		if(!initflag)
    		{
    			log.error("核心类未成功初始化");
    			throw new ServiceException(info);   			
    		}
    		handle.invoke(message);
    	}
    	catch(ServiceException ex)
    	{
    		
    		message.setSuccess(false);
    		CErrors tCError = message.getCErrors();
    		if(tCError==null)
    		{
    			tCError = new CErrors();
    		}
    		tCError.addOneError(ex.getMessage());
    		message.setCErrors(tCError);
    	}
    	catch(Exception ex)
    	{
    		message.setSuccess(false);
    		CErrors tCError = message.getCErrors();
    		if(tCError==null)
    		{
    			tCError = new CErrors();
    		}
    		tCError.addOneError(ex.getMessage());
    		message.setCErrors(tCError);
    	}
    }
}
