package com.sinosoft.service.handle;
import org.apache.log4j.Logger;

import com.sinosoft.service.Handle;
import com.sinosoft.service.IMessage;
import com.sinosoft.utility.TransferData;

/**
 * <p>Title: 集合处理方法,对应前置处理,核心处理,后置处理</p>
 *
 * <p>Description: ejb转发设计</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 */
public class ServiceHandle implements Handle{
private static Logger logger = Logger.getLogger(ServiceHandle.class);
	public static final String PREHANDLE = "PreHandle";
	public static final String AFTHANDLE = "AftHandle";
	public static final String HANDLE = "Handle";
    private SortHandle preHandle;
    private SortHandle aftHandle;
    private Handle handle;
    public ServiceHandle() {
    }
	public void init(TransferData transfer) {
		preHandle = (SortHandle)transfer.getValueByName(PREHANDLE);
		aftHandle = (SortHandle)transfer.getValueByName(AFTHANDLE);
		handle = (Handle)transfer.getValueByName(HANDLE);
	}

	public void invoke(IMessage message) {
		/**执行前置*/
		preHandle.invoke(message);
		/**执行核心处理*/
		handle.invoke(message);
		/**执行后置*/
		aftHandle.invoke(message);
	}
}
