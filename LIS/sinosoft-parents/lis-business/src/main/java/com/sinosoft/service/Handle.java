package com.sinosoft.service;
import org.apache.log4j.Logger;

import com.sinosoft.service.IMessage;
import com.sinosoft.utility.TransferData;

/**
 * <p>Title: 处理方法接口,所有处理都实现该接口</p>
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
public interface Handle {
	/**用于初始化,初始化时会自动调用*/
	public void init(TransferData tTransfer);
	/**处理调用*/
    public void invoke(IMessage message);
}
