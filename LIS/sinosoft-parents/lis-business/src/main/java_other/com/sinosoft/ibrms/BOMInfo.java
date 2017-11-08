package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sinosoft.ibrms.bom.AbstractBOM;

/**
 * 该类获取bom中某个条目对应的类型。不是从数据库中获取，而是通过反射获取返回的参数类型
 * 该类采用单实例模式。并且采用缓存模式
 */
public class BOMInfo {
private static Logger logger = Logger.getLogger(BOMInfo.class);
	
	public static final String STRING = "string";
	public static final String NUMBER = "number";
	public static final String DATE   = "date";
	public static final String UNKNOWN   = "unknow";
	
	Map mapBoms = new HashMap();
//	Map mapFields = new HashMap();
	private BOMInfo(){};
	
	private static BOMInfo bomInfo = new BOMInfo();
	public static BOMInfo getInstance(){
		return bomInfo;
	}
	
	public synchronized String getBomItemType(String bomName,String itemName,AbstractBOM bom){
		Map mapItems = (Map)mapBoms.get(bomName);
		if(mapItems==null){
			mapItems = new HashMap();
			mapBoms.put(bomName, mapItems);
		}
		String type = (String)mapItems.get(itemName);
		if(type == null)
		{
			if(bom == null)
				return UNKNOWN;
			try {
				Method method = bom.getClass().getMethod("get"+itemName, null);
				Class clazz = method.getReturnType();
				if(clazz.equals(String.class))
					type = STRING;
				else if(clazz.equals(Double.class))
					type = NUMBER;
				else if(clazz.equals(Date.class))
					 type = DATE;
				else 
					type = method.getName();
				mapItems.put(itemName, type);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return UNKNOWN;
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return UNKNOWN;
			}
		}
		return type;
		
		
		
	}
	

}
