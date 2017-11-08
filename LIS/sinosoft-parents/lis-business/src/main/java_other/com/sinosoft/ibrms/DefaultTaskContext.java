package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DefaultTaskContext implements TaskContext {
private static Logger logger = Logger.getLogger(DefaultTaskContext.class);
	private Map map = new HashMap();

	public Object getAttribute(String key) {
		// TODO Auto-generated method stub
		return map.get(key);
	}

	public void setAttribute(String key, Object value) {
		// TODO Auto-generated method stub
       map.put(key, value);
	}

}
