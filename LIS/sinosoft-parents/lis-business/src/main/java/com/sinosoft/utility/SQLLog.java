package com.sinosoft.utility;

import org.apache.log4j.Logger;

/**
 * 统计ExeSQL执行的 SQL和长度
 * @author Administrator
 *
 */
public class SQLLog {
	private static Logger logger = Logger.getLogger(SQLLog.class);
	private String mSQL;
	public static boolean mSQLBreakFlag = false; //是否中断SQL执行标记, 如果为true,并且SQL超出长度限制,中断SQL执行
	public static int mSQLLengthLimit = 4000; //SQL长度限制校验,如果执行的SQL长度超过该限制,则在后台输出SQL的长度和SQL内容
	
	
	public static int mSQLMaxRows = 10000;//SQL查询结果最大数
	public static boolean mSQLRowsLimitFlag = false;//SQL条数校验,如果SQL结果数超出限制,并且该标记为true,则中断执行SQL
	
	public SQLLog() {
		// TODO Auto-generated constructor stub
	}

	public SQLLog(String tSQL) {
		// TODO Auto-generated constructor stub
		this.mSQL = tSQL;
	}
	
	public boolean checkSQL()
	{
		int tSQLLength = this.mSQL.length();
		//System.out.println("tSQLLength:"+tSQLLength+":mSQLLengthLimit:"+mSQLLengthLimit);
		if(tSQLLength>mSQLLengthLimit)
		{
			//如果长度超出可执行SQL长度的限制,输入日志
			logger.debug("Current SQL Length:"+tSQLLength+": SQL:"+this.mSQL);
			if(mSQLBreakFlag)
			{
				//如果限制超出长度的SQL执行.则抛出异常,同时,返回false;
				try {
					logger.error("Since the SQL sentence is too long, system will interrupt excution :"+this.mSQL);
					//throw new Exception("Since the SQL sentence is too long, system will interrupt excution :"+this.mSQL);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
				return false;
			}
		}
		return true;
	}
	
	public boolean checkSQLCondition()
	{
//		String tSQL = this.mSQL;
//		String tCheckContion = " where ";
//		int tIndex = tSQL.indexOf(tCheckContion);
//		System.out.println("tIndex:"+tIndex);
//		
//		if(tSQL.toLowerCase().indexOf(" where ")==-1
//				||tSQL.toLowerCase().indexOf("where")==-1)
//		{
//			logger.error("Since SQL sentence has no query condition, system will interrupt excution :"+this.mSQL);
//			try {
//				//throw new Exception("Since SQL sentence has no query condition, system will interrupt excution :"+this.mSQL);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//			}
//			return false;
//		}
//		else
//		{
//			tSQL = tSQL.substring(tIndex+tCheckContion.length());
//			if(tSQL.trim().equals(""))
//			{
//				logger.error("Since SQL sentence has no query condition, system will interrupt excution :"+this.mSQL);
//				try {
//					//throw new Exception("Since SQL sentence has no query condition, system will interrupt excution :"+this.mSQL);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				return false;
//			}
//		}
		
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExeSQL tExeSQL = new ExeSQL();
		String tTestSQL = "select 1 from dual where 1";
		tExeSQL.execUpdateSQL(tTestSQL);
	}

}
