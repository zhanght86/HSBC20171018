package com.sinosoft.service;
import org.apache.log4j.Logger;

public class Service{
private static Logger logger = Logger.getLogger(Service.class);
	/**对应业务处理类名称*/
	private String className;
	/**公布服务名称*/
	private String sverviceName;
	/**业务处理作用域*/
	private String scope;
	/**权限描述*/
	private String[] roles;
	/**业务处理*/
	private Handle serviceHandle;
	
	public void setClassName(String className)
	{
		this.className = className;
	}
	public String getClassName()
	{
		return className;
	}
	
	public void setServiceName(String serviceName)
	{
		this.sverviceName = serviceName;
	}
	public String getServiceName()
	{
		return this.sverviceName;
	}
	
	public void setScope(String scope)
	{
		this.scope = scope;
	}
	public String getScope()
	{
		return this.scope;
	}
	
	public void setRoles(String[] roles)
	{
		this.roles = roles;
	}
	public String[] getRoles()
	{
		return this.roles;
	}
	
	public void setHandle(Handle handle)
	{
		this.serviceHandle = handle;
	}
	/**获取业务处理流*/
	public Handle getHandle() {
		return serviceHandle;
	}

}
