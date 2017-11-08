package com.sinosoft.service;
import org.apache.log4j.Logger;

import java.util.Hashtable;
import java.util.Map;

import com.sinosoft.service.exception.ServiceException;

/**
 * 业务逻辑工厂 通过XML对应描述 调用相应的业务逻辑
 *  * <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 * */
public class BusinessFactory {
private static Logger logger = Logger.getLogger(BusinessFactory.class);
	private static BusinessFactory businessFactory;
	/**Service容器*/
	private Map services = new Hashtable();
	private BusinessFactory()
	{
	}
	/**添加Service*/
	public void addService(Service service)
	{
		services.put(service.getServiceName(), service);
	}
	/**移除Service*/
	public void removeService(Service service)
	{
		services.remove(service.getServiceName());
	}
	/**单态*/
	public static synchronized BusinessFactory getFactory()
	{
		if(businessFactory==null)
		{
			businessFactory = new BusinessFactory();
		}
		return businessFactory;
	}
	/**工厂方法,获取对应服务信息*/
	public Service getService(String serviceName)
	{
		Service tService;
		String des = serviceName;
		if(des==null || des.equals(""))
		{
			throw new ServiceException("没有传入调用服务名称信息");
		}		
		tService = (Service)services.get(des);
		if(tService == null)
		{
			throw new ServiceException("没有对应"+des+"的服务信息");
		}
		return tService;		
	}
}
