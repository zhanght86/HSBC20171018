package com.sinosoft.lis.pubfun;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/*
 * 乐观锁信息
 */
public class OptimisticLockData {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(OptimisticLockData.class);
	private String tableName;//加锁表名
	private Map<String,String> columns = new ConcurrentHashMap<String,String>();//加锁字段名
	private LockType lockType = LockType.DEFAULT;//加锁方法  默认Select方式
	
	
	
	public LockType getLockType() {
		return lockType;
	}

	public void setSelectLockType() {
		this.lockType = LockType.SELECT;
	}

	public void setUpdateLockType() {
		this.lockType = LockType.UPDATE;
	}

	
	public OptimisticLockData() {
		super();
	}

	public OptimisticLockData(String tableName, Map<String, String> columns) {
		super();
		this.tableName = tableName;
		this.columns = columns;
	}

	/**
	 * 设置校验的列和正确的数值
	 * @param columnName
	 * @param columnValue
	 */
	public void setCheckColumnAndValue(String columnName,String columnValue){
		columns.put(columnName.toLowerCase(), columnValue);
	}
	
	public void removeColumn(String columnName){
		if(columns.containsKey(columnName)){
			columns.remove(columnName);
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}
	
	enum LockType {
		DEFAULT("1","SELECT"),
		SELECT("1","SELECT"),
		UPDATE("2","UPDATE");
		private String name;
	    private String value;

	    // 构造方法
	    private LockType(String value,String name) {
	        this.name = name;
	        this.value = value;
	    }
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
	}

}
