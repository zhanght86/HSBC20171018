package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

/*
 * <p>ClassName: GlobalPools </p>
 * <p>Description: 线程号与连接池映射缓存类 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @author tongmeng
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import bsh.This;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.GlobalPools;

public final class GlobalCheckSpot {
private static Logger logger = Logger.getLogger(GlobalCheckSpot.class);

	private static GlobalCheckSpot mGlobalCheckSpot = null;
	// @Field
	public CErrors mErrors = new CErrors(); // 错误信息
	/* 保存线程号和连接池名映射 */
	//private Hashtable mGlobalTable = new Hashtable();
	private int mCurrentCheckNum = 0;
	// public boolean mLoadPropertyFlag = false;
	/* 配置文件是否加载成功标志 */
	public boolean mLoadSuccFlag = false;
	/* 保存全局的properties */
	private static Properties ruleproperties = null;

	private GlobalCheckSpot() {
	}

	public static GlobalCheckSpot getInstance() {
		if (mGlobalCheckSpot == null) {
			logger.debug("*******************************new class*****************************");
			mGlobalCheckSpot = new GlobalCheckSpot();
		}

		mGlobalCheckSpot.mErrors.clearErrors();
		return mGlobalCheckSpot;
	}

	/**
	 * 返回当前已经抽检数
	 * @return
	 */
	public synchronized int getCurrentCheckNum() {
		return this.mCurrentCheckNum;
	}
	/**
	 * 当前抽检数 + 1
	 */
	
	public synchronized void setCurrentCheckNum() {
		this.mCurrentCheckNum = mCurrentCheckNum + 1;
	}
	
	/**
	 * 设置初始抽检数
	 * @param checkMum
	 */
	public synchronized void initCheckNum(int checkMum) {
		this.mCurrentCheckNum = checkMum;
	}	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
