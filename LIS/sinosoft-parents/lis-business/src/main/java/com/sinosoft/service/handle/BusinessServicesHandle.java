package com.sinosoft.service.handle;
import org.apache.log4j.Logger;

import com.sinosoft.service.Handle;
import com.sinosoft.service.IMessage;
import com.sinosoft.service.LocalService;
import com.sinosoft.service.Service;
import com.sinosoft.utility.TransferData;
/**
 * 全局业効处理与实际业务Service的对掿通过工厂方法查找相应service并调用夐琿
 *  * <p>Description: 转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 * */
public class BusinessServicesHandle implements Handle{
private static Logger logger = Logger.getLogger(BusinessServicesHandle.class);
	private LocalService localService;
	
	public BusinessServicesHandle(LocalService localService)
	{
		this.localService = localService;
	}
	public void init(TransferData transfer) {		
	}

	public void invoke(IMessage message) {
		Handle tHandle;
		Service tService;
		/**桓[ͮ包请求获取对应服努*/
		tService = localService.getService(message.getBusiName());
		/**传给service容器处理*/
		tHandle = tService.getHandle();
		tHandle.invoke(message);
	}	
}