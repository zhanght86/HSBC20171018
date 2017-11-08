package com.sinosoft.service;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;

import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.service.handle.BusinessHandle;
import com.sinosoft.service.handle.BusinessServicesHandle;
import com.sinosoft.service.handle.ServiceHandle;
import com.sinosoft.service.handle.SortHandle;
import com.sinosoft.utility.TransferData;

/**
 * 配置文档解析,初始化整个service,核心组件
 *  * <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 * */
public class ServiceParse {
private static Logger logger = Logger.getLogger(ServiceParse.class);
	/**配置文件名*/
	public static final String CONFILE = "Engine.xml";
	/**LocalService节点*/
	public static final String GLOBALHANDLE = "GlobalHandle";
	/**Service节点*/
	public static final String SERVICE = "Service";
	/**前置处理*/
	public static final String REQUESTHANDLE = "RequestHandle";
	/**后置处理*/
	public static final String RESPONSEHANDLE = "ResponseHandle";
	/**处理描述*/
	public static final String HANDLE = "Handle";
	/**对应Service名称*/
	public static final String NAME = "name";
	/**作用域*/
	public static final String SCOPE = "scope";
	/**对应Class*/
	public static final String CLASSNAME = "type";
	/**参数描述*/
	public static final String INITPARAM = "init-param";
	/**参数名称*/
	public static final String PARAMNAME = "param-name";
	/**参数值*/
	public static final String PARAMVALUE = "param-value";
	
	private DOMBuilder dBuilder = new DOMBuilder();
	private Document doc ;
	private LocalService localService ;
	
	public static String className = "com.sinosoft.service.ServiceParse";
	/**log日志*/
	public static Log log = LogFactory.getLog(className);
	
	public ServiceParse(LocalService tLocalService)
	{
		this.localService = tLocalService;
	}
	/**解析XML,构建LocalService,Service*/
	public void parses()
	{
		/**获取配置文件*/
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(CONFILE); 
		if(is==null)
		{
			String msg = "配置文件" + CONFILE + "不存在,请核实";
			log.fatal(msg);
	
			throw new ServiceException(msg);
		}
		try
		{
			doc = dBuilder.build(is);
		}
		catch(JDOMException ex)
		{
			String msg = "JDOM解析失败";
			log.fatal(msg);
			ex.printStackTrace();
			throw new ServiceException(msg,ex);
		}
		Element root = doc.getRootElement();
		// 初始化
		Element tEle = root.getChild(GLOBALHANDLE);		
		Handle tGlobalHandle = getServiceHandle(tEle);
		localService.setGlobalHandle(tGlobalHandle);
		// 初始化Service
		List tServiceList = root.getChildren(SERVICE);
		parseServices(tServiceList);		
	}
	
	/**解析Services并生成工厂*/
	private void parseServices(List tServiceList)
	{		
		Iterator tServiceIter = tServiceList.iterator();
		while(tServiceIter.hasNext())
		{
			Element tEle = (Element)tServiceIter.next();	
			Service service = new Service();
			Handle serviceHandle = new ServiceHandle();								
			String name = tEle.getAttributeValue(NAME);
			String classname = tEle.getAttributeValue(CLASSNAME);
			String scope =  tEle.getAttributeValue(SCOPE);	
			service.setClassName(classname);
			service.setServiceName(name);
			service.setScope(scope);
			//service.setRoles(roles);
			/**初始化Service前置处理*/
			SortHandle tPreHandle = getSortHandle(tEle,REQUESTHANDLE);
			/**初始化Service后置处理*/
			SortHandle tAftHandle =  getSortHandle(tEle,RESPONSEHANDLE);					
			/**添加service核心处理*/
			Handle tHandle = new BusinessHandle(service);			
			/**初始化service*/
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue(ServiceHandle.PREHANDLE, tPreHandle);
			tTransferData.setNameAndValue(ServiceHandle.AFTHANDLE, tAftHandle);
			tTransferData.setNameAndValue(ServiceHandle.HANDLE, tHandle);
			serviceHandle.init(tTransferData);	
			service.setHandle(serviceHandle);
			localService.addService(service);
		}
	}
		
	/**获取lcoalservice的处理句柄*/
	private Handle getServiceHandle(Element tEle)
	{	
		Handle globalHandle = new ServiceHandle();			
		/**获取Global前置处理描述*/
		SortHandle tPreHandle = getSortHandle(tEle,REQUESTHANDLE);				
		/**获取Global后置处理描述*/
		SortHandle tAftHandle = getSortHandle(tEle,RESPONSEHANDLE);							
		//添加global核心处理方法
		Handle tHandle = new BusinessServicesHandle(localService);		
		//初始化global处理集合
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue(ServiceHandle.PREHANDLE, tPreHandle);
		tTransferData.setNameAndValue(ServiceHandle.AFTHANDLE, tAftHandle);
		tTransferData.setNameAndValue(ServiceHandle.HANDLE, tHandle);
		globalHandle.init(tTransferData);		
		return globalHandle;
	}
	
	/**获取SortHandle*/
	private SortHandle getSortHandle(Element tEle,String type)
	{
		SortHandle tSortHandle = new SortHandle();
		List tList = tEle.getChildren(type);
		Iterator tIterator = tList.iterator();
		while (tIterator.hasNext()) {
			Element tResponseEle = (Element) tIterator.next();
			List tHandleList = tResponseEle.getChildren(HANDLE);
			Iterator tHandleIter = tHandleList.iterator();
			while (tHandleIter.hasNext()) {
				Element tHandleEle = (Element) tHandleIter.next();
				Handle tHandle = getHandle(tHandleEle);
				if(tHandle!=null)
					tSortHandle.add(tHandle);
			}
		}
		return tSortHandle;
	}
	/**获取描述的handle并做初始化处理*/
	private Handle getHandle(Element tEle)
	{
		Element tHandleEle = tEle;
		Handle tHandle;
		TransferData tTransfer = new TransferData();
		String className = tHandleEle.getAttributeValue(CLASSNAME);
		try {
			/**直接实例化*/
			tHandle = (Handle) Class.forName(className).newInstance();
			/**获取参数*/
			List paraList = tEle.getChildren(INITPARAM);
			Iterator paraIter = paraList.iterator();
			while (paraIter.hasNext()) {
				Element paraEle = (Element) paraIter.next();
				Element nameEle = paraEle.getChild(PARAMNAME);
				Element valueEle = paraEle.getChild(PARAMVALUE);
				String name = "";
				String value = "";
				if (nameEle != null) {
					name = nameEle.getTextTrim();
				}
				if (valueEle != null) {
					value = valueEle.getTextTrim();
				}
				tTransfer.setNameAndValue(name, value);
			}
			/**这里做初始化*/
			tHandle.init(tTransfer);
		} catch (Exception ex) {
			String msg = "添加" + className + "类失败!\n异常:" + ex.getMessage();
			ex.printStackTrace();
			log.error(msg);
			return null;
		}
		return tHandle;
	}
}
