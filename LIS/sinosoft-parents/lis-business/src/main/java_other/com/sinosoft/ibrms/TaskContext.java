package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

public interface TaskContext {
	public Object getAttribute(String key);
	public void   setAttribute(String key,Object value);

}
