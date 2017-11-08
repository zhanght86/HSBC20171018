package com.sinosoft.service;
import org.apache.log4j.Logger;

public interface LService {
	/**调用业务逻辑处理*/
	public void doService(IMessage message);
}
