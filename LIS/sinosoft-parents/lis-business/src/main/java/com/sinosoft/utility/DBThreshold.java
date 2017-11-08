package com.sinosoft.utility;
import org.apache.log4j.Logger;

import org.apache.log4j.Logger;

/**
 * <p>
 * Description: 结果集记录限制工具类
 * </p>
 * 在生产环境下，经常会出现对于大表只有很少查询条件或者根本没有查询条件的SQL语句。
 * 直接的后果就是生产环境经常出现内存溢出甚至服务停机。这个类的作用就是将这样的 查询语句都找出来。
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @2006-05-15 Kevin
 * @version 1.0
 */
public class DBThreshold {
private static Logger logger = Logger.getLogger(DBThreshold.class);
	//private static int THRESHOLD = 10000;

	private int m_count = 0;
	private int m_threshold = 0;
	private String m_strSQL = null;
	
	private boolean SQLRowsLimitFlag = false;

	public DBThreshold() {
		this.m_count = 0;
		//this.m_threshold = DBThreshold.THRESHOLD;
		this.m_threshold = SQLLog.mSQLMaxRows;
		this.SQLRowsLimitFlag = SQLLog.mSQLRowsLimitFlag;
		//m_log = Logger.getLogger(DBThreshold.class);
	}

	public DBThreshold(int threshold) {
		this.m_count = 0;
		this.m_threshold = threshold;
		this.SQLRowsLimitFlag = SQLLog.mSQLRowsLimitFlag;
		//m_log = Logger.getLogger(DBThreshold.class);
	}

	public void setSQL(String strSQL) {
		if (strSQL != null) {
			this.m_strSQL = strSQL;
		}
	}

	public void reset() {
		this.m_count = 0;
	}

	public boolean increase() {
		if (this.m_count >= 0) {
			this.m_count++;
		}

		if (this.m_count > this.m_threshold) {
			try {
				
				logger.debug("Since too many SQL results, system will interrupt excution :"+this.m_strSQL);
				
				if(this.SQLRowsLimitFlag)
				{
					
					//throw new Exception("Since too many SQL results, system will interrupt excution :"+this.m_strSQL);
					//return false;
					logger.error("Since too many SQL results, system will interrupt excution :"+this.m_strSQL);
					return false;
				}
				
			} catch (Exception ex) {
				
			}finally{
				this.m_count = -1;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		DBThreshold dbthreshold = new DBThreshold(2);

		dbthreshold.increase();
		dbthreshold.increase();
		dbthreshold.increase();
	}
}
