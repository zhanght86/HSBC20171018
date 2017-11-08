/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 配合自动BLS的Map类，暂不支持Remove方法
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author Minim
 * @version 1.0
 */
public class MMap
{
private static Logger logger = Logger.getLogger(MMap.class);

	/** 数据的容器 Map Vector */
	private HashMap mapV = null;

	/** 排序的容器 Map Order */
	private HashMap mapO = null;

	private int mRepeatCount = 0; // 未解决put方法引起的索引冲突问题，增加本字段用来记录重复次数

	//数据提交前增加数据校验
	private OptimisticLockData mOptimisticLockData = new OptimisticLockData();
	//private OptimisticLockDataCheck mOptimisticLockDataCheck = new OptimisticLockDataCheck();
	//public set
	
	
	/**
	 * 数据校验
	 * @return
	 */
	//public boolean checkLockData(){
	//	mOptimisticLockDataCheck.setmOptimisticLockData(mOptimisticLockData);
	//	return mOptimisticLockDataCheck.checkLock();
	//}
	
	/**
	 * 设置数据校验信息
	 * @param tableName
	 * @param checkColAndValue
	 */
	public void setDataCheck(String tableName,Map<String,String> checkColAndValue){
		mOptimisticLockData.setTableName(tableName);
		mOptimisticLockData.setColumns(checkColAndValue);
		
	}
	public OptimisticLockData getmOptimisticLockData() {
		return mOptimisticLockData;
	}
	public void setmOptimisticLockData(OptimisticLockData mOptimisticLockData) {
		this.mOptimisticLockData = mOptimisticLockData;
	}
	/**
	 * 构造函数
	 */
	public MMap()
	{
		mapV = new HashMap();
		mapO = new HashMap();
		mOptimisticLockData = new OptimisticLockData();
	}

	/**
	 * 建立键－值对，序号从1开始 增加了对象索引冲突的处理： 1、对象是String，在字符串末添加与重复次数等量的空格，生成新的String对象
	 * 2、对象是Schema，用clone得到的对象替换原对象
	 * 3、对象是SchemaSet，生成新的SchemaSet对象，并将原SchemaSet中的Schema存储到新SchemaSet
	 * @param key Object
	 * @param value Object
	 */
	public void put(Object key, Object value)
	{
		if (key == null || value == null)
			return;
		
		logger.debug("key:"+key);
		if (mapV.containsKey(key))
		{
			mRepeatCount++; // 记录key重复的次数
			String className = key.getClass().getName();

			if (className.endsWith("String"))
			{

				String keyStr = (String) key;
				for (int i = 0; i < mRepeatCount; i++)
				{
					keyStr += " ";
				}
				key = keyStr;
			}
			else if (className.endsWith("Schema"))
			{
				key = cloneSchema((Schema) key);
			}
			else if (className.endsWith("Set"))
			{
				key = cloneSchemSet((SchemaSet) key);
			}
		}

		logger.debug("key1:"+key);
		mapV.put(key, value);
		mapO.put(String.valueOf(mapV.size()), key);
	}

	/**
	 * 得到cOldSchemaSet的克隆对象，若发生异常无法克隆，则返回原对象
	 * 使用java反射机制，生成cOldSchemaSet类类型的新SchemaSet对象newSchemaSet，
	 * 并将cOldSchemaSet中的每个Schema对象克隆到newSchemaSet
	 * 这样可以做到新SchemaSet的索引与原SchemaSet不一样
	 * @param cOldSchemaSet SchemaSet
	 * @return SchemaSet
	 */
	private SchemaSet cloneSchemSet(SchemaSet cOldSchemaSet)
	{
		String schemaSetName = cOldSchemaSet.getClass().getName();
		try
		{
			Class schemaSetClass = Class.forName(schemaSetName);
			SchemaSet newSchemaSet = (SchemaSet) schemaSetClass.newInstance();
			newSchemaSet.add(cOldSchemaSet);
			return newSchemaSet;
		}
		catch (ClassNotFoundException ex)
		{
			logger.debug("没有找到类" + schemaSetName);
			ex.printStackTrace();
			return cOldSchemaSet;
		}
		catch (IllegalAccessException ex)
		{
			logger.debug("无法Clone Schema");
			ex.printStackTrace();
			return cOldSchemaSet;
		}
		catch (InstantiationException ex)
		{
			logger.debug("无法Clone Schema");
			ex.printStackTrace();
			return cOldSchemaSet;
		}
	}

	/**
	 * cloneSchema 得到cOldSchema的克隆对象，若发生异常无法克隆，则返回原对象
	 * 使用java反射机制，调用Schema的getSchema方法，生成一个新的Schema对象，
	 * 这样可以做到新Schema的索引与原Schema的不一样
	 * @param cOldSchema Schema：需要克隆的对象
	 * @return Schema
	 */
	private Schema cloneSchema(Schema cOldSchema)
	{
		String methodName = "getSchema";
		Class[] paramType = new Class[0];
		Method method = null;

		try
		{
			method = cOldSchema.getClass().getMethod(methodName, paramType);
			Object[] args = new Object[0];
			return (Schema) method.invoke(cOldSchema, args);
		}
		catch (NoSuchMethodException ex)
		{
			logger.debug("没有找到getSchema方法");
			ex.printStackTrace();
			return cOldSchema;
		}
		catch (InvocationTargetException ex)
		{
			logger.debug("无法Clone Schema");
			ex.printStackTrace();
			return cOldSchema;
		}
		catch (IllegalAccessException ex)
		{
			logger.debug("无法Clone Schema");
			ex.printStackTrace();
			return cOldSchema;
		}
	}

	/**
	 * 获取键－值Set
	 * @return Set
	 */
	public Set keySet()
	{
		return mapV.keySet();
	}

	/**
	 * 根据键获取值
	 * @param key Object
	 * @return Object
	 */
	public Object get(Object key)
	{
		return mapV.get(key);
	}

	/**
	 * 获取排序Map
	 * @return HashMap
	 */
	public HashMap getOrder()
	{
		return mapO;
	}

	/**
	 * 通过序号获取键，序号即插入顺序，从1开始
	 * @param order String
	 * @return Object
	 */
	public Object getKeyByOrder(String order)
	{
		return mapO.get(order);
	}

	/**
	 * 添加一个MMap
	 * @param srcMap MMap
	 */
	public void add(MMap srcMap)
	{
		if (srcMap == null)
			return;
		int tSize = srcMap.keySet().size();
		for (int i = 0; i < tSize; i++)
		{
			Object key = srcMap.getKeyByOrder(String.valueOf(i + 1));
			this.put(key, srcMap.get(key));
		}
	}

	/**
	 * 根据类名获取对象
	 * @param cObjectName String
	 * @param cStartPos int
	 * @return Object
	 */
	public Object getObjectByObjectName(String cObjectName, int cStartPos)
	{
		int i = 0, iMax = 0;
		String tStr1 = "", tStr2 = "";
		Object tReturn = null;
		if (cStartPos < 0)
		{
			cStartPos = 0;
		}
		iMax = this.keySet().size();
		try
		{
			for (i = cStartPos; i < iMax; i++)
			{
				if (this.getOrder().get(String.valueOf(i + 1)) == null)
				{
					continue;
				}
				tStr1 = this.getOrder().get(String.valueOf(i + 1)).getClass().getName().toUpperCase();
				tStr2 = cObjectName.toUpperCase();
				if (tStr1.equals(tStr2) || this.getLastWord(tStr1, ".").equals(tStr2))
				{
					tReturn = this.getOrder().get(String.valueOf(i + 1));
					break;
				}
			}
		}
		catch (Exception ex)
		{
			tReturn = null;
		}
		return tReturn;
	}

	/**
	 * 根据类名获取所有对象
	 * @param cObjectName String
	 * @param cStartPos int
	 * @return Object
	 */
	public Object[] getAllObjectByObjectName(String cObjectName, int cStartPos)
	{
		int i = 0, iMax = 0;
		String tStr1 = "", tStr2 = "";
		ArrayList tReturn = new ArrayList();
		if (cStartPos < 0)
		{
			cStartPos = 0;
		}
		iMax = this.keySet().size();
		try
		{
			for (i = cStartPos; i < iMax; i++)
			{
				if (this.getOrder().get(String.valueOf(i + 1)) == null)
				{
					continue;
				}
				tStr1 = this.getOrder().get(String.valueOf(i + 1)).getClass().getName().toUpperCase();
				tStr2 = cObjectName.toUpperCase();
				if (tStr1.equals(tStr2) || this.getLastWord(tStr1, ".").equals(tStr2))
				{
					tReturn.add(this.getOrder().get(String.valueOf(i + 1)));
				}
			}
		}
		catch (Exception ex)
		{
			tReturn = null;
		}
		return tReturn.toArray();

	}

	// 得到字符串中的最后一个以splitChar分割的单词
	public String getLastWord(String cStr, String splitStr)
	{
		String tReturn;
		int tIndex = -1, tIndexOld = -1;
		tReturn = cStr;
		try
		{
			while (true)
			{
				tIndex = tReturn.indexOf(splitStr, tIndex + 1);
				if (tIndex > 0)
				{
					tIndexOld = tIndex;
				}
				else
				{
					break;
				}
			}
			if (tIndexOld > 0)
			{
				tReturn = cStr.substring(tIndexOld + 1, cStr.length());
			}
			else
			{
				tReturn = cStr;
			}
		}
		catch (Exception ex)
		{
			tReturn = "";
		}
		return tReturn;
	}

	public int size()
	{
		return this.keySet().size();
	}

	public static void main(String[] args)
	{
		
	 MMap amap = new MMap();
	 amap.put("key1", "value1");
	 amap.put("key1", "value2");
	 LCContSchema t = new LCContSchema();
	 t.setContNo("1");
	// amap.put("key1", "value1");
	// amap.put("key1", "value2");
	 amap.put(t, "t1");
	 t = new LCContSchema();
	 t.setContNo("2");
	 amap.put(t, "t1");
	 logger.debug("amap.keySet().size():"+amap.keySet().size());
	 for (int i = 0; i < amap.keySet().size(); i++)
	 {
		 Object key = amap.getKeyByOrder(String.valueOf(i + 1));
		 if(key instanceof LCContSchema)
		 {
			 logger.debug("***:" +((LCContSchema)key).getContNo());
		 }
		 else
		 {	 
			 logger.debug("***1:"+(key).toString());
		 }
	 }
	// amap.put("key1", "value1");
	// amap.put("key2", "value2");
	// String a = (String) amap.getKeyByOrder("1");
	// logger.debug("a" + a);
	// MMap bmap = new MMap();
	// bmap.put("keyb1", "valueb1");
	// bmap.put("keyb2", "valueb2");
	// amap.add(bmap);
	// for (int i = 0; i < amap.keySet().size(); i++)
	// {
	// Object key = amap.getKeyByOrder(String.valueOf(i + 1));
	// logger.debug(amap.get(key).toString());
	// }
	}
}
