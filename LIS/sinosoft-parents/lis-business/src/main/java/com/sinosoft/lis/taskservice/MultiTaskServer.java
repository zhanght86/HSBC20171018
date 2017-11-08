package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.net.InetAddress;

import com.sinosoft.lis.db.LDTaskParamDB;
import com.sinosoft.lis.db.LDTaskServerParamDB;
import com.sinosoft.lis.vschema.LDTaskParamSet;
import com.sinosoft.lis.vschema.LDTaskServerParamSet;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 增加多节点自动运行功能
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author ck
 * @version 1.0
 */

public class MultiTaskServer {
private static Logger logger = Logger.getLogger(MultiTaskServer.class);
	static String serverip = null;
	static String serverport = null;

	//增加服务器的系统参数配置
	static LDTaskParamSet mLDTaskParamSet = null;
	
	static String mServerType = null;
	static String mRunType = null;
	static String mDebug = null;
	static String mInvalidTime = null;
	//以下是服务节点的信息
	static LDTaskServerParamSet mLDTaskServerParamSet = null;
	static String mBakNode = null;
	static String mthreadNum = null;
	static String mMaxthreadNum = null;
	static String mPoolMode = null;
	public MultiTaskServer() {
	}

	// 得到运行应用服务器的IP地址
	public static String getServerIP() {
		/*
		 * try{ ServerIPaddress=InetAddress.getLocalHost(); logger.debug("ServerIPaddress.getHostAddress():"+ServerIPaddress.getHostAddress()); }catch(Exception ex) { ex.printStackTrace(); return null; } return ServerIPaddress.getHostName();
		 */

		if (serverip != null)
			return serverip;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			serverip = addr.getHostAddress();

		} catch (Exception ex) {
			// ex.printStackTrace();
			serverip = "127.0.0.1";
		}
		return serverip;

	}

	// 得到运行应用服务器的监听端口
	public static String getServerPort() {
		if (serverport != null)
			return serverport;
		try {
			//按照服务器的类型获取服务器的节点
			String tServerType = getServerType();
			if(tServerType.toLowerCase().equals("weblogic"))
			{
				Class clazz = Class.forName("weblogic.server.Server");
				Method m = clazz.getMethod("getConfig", null);
				Object o = m.invoke(null, null);
				Class clazzBean = Class.forName("weblogic.management.configuration.ServerMBean");
				Method bm = clazzBean.getMethod("getListenPort", null);
				Object bo = bm.invoke(o, null);
				serverport = String.valueOf(bo);
			}
			else if(tServerType.toLowerCase().equals("websphere")){
			}
			else
			{
				//其他默认为9999
				serverport  =  "9999";
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			try {
				serverport = "9999";
//				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
			} 
			
		}
		return serverport;
	}
	
	public static LDTaskParamSet getLDTaskParamSet()
	{
		if(mLDTaskParamSet==null)
		{
			mLDTaskParamSet = new LDTaskParamSet();
			LDTaskParamDB tLDTaskParamDB = new LDTaskParamDB();
			String tSQL = "select * from ldtaskparam where taskcode='000000' order by paramname ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			mLDTaskParamSet = tLDTaskParamDB.executeQuery(sqlbv);
		}
		
		return mLDTaskParamSet;
	}
	
	//获取服务器类型
	public static String getServerType()
	{
		if(mServerType==null)
		{
			LDTaskParamSet tLDTaskParamSet = getLDTaskParamSet();
			for(int i=1;i<=tLDTaskParamSet.size();i++)
			{
				String tParamName = tLDTaskParamSet.get(i).getParamName();
				String tParamValue = tLDTaskParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("servertype"))
				{
					mServerType = tParamValue;
					break;
				}
			}
		}
		
		return mServerType;
	}
	
	//获取运行类型
	public static String getRunType()
	{
		if(mRunType==null)
		{
			LDTaskParamSet tLDTaskParamSet = getLDTaskParamSet();
			for(int i=1;i<=tLDTaskParamSet.size();i++)
			{
				String tParamName = tLDTaskParamSet.get(i).getParamName();
				String tParamValue = tLDTaskParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("runtype"))
				{
					mRunType = tParamValue;
					break;
				}
			}
		}
		
		return mRunType;
	}
	
	
	//获取debug标记
	public static String getDebug()
	{
		if(mDebug==null)
		{
			LDTaskParamSet tLDTaskParamSet = getLDTaskParamSet();
			for(int i=1;i<=tLDTaskParamSet.size();i++)
			{
				String tParamName = tLDTaskParamSet.get(i).getParamName();
				String tParamValue = tLDTaskParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("debug"))
				{
					mDebug = tParamValue;
					break;
				}
			}
		}
		
		//没有描述数据,就默认返回0-不debug
		if(mDebug==null)
		{
			return "0";
		}
		return mDebug;
	}
	
	//获取mInvalidTime标记
	public static String getInvalidTime()
	{
		if(mInvalidTime==null)
		{
			LDTaskParamSet tLDTaskParamSet = getLDTaskParamSet();
			for(int i=1;i<=tLDTaskParamSet.size();i++)
			{
				String tParamName = tLDTaskParamSet.get(i).getParamName();
				String tParamValue = tLDTaskParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("invalidtime"))
				{
					mInvalidTime = tParamValue;
					break;
				}
			}
		}
		
		//没有描述数据,就默认返回-1 无限制
		if(mInvalidTime==null)
		{
			return "-1";
		}
		return mInvalidTime;
	}
	
	/////////////////////////////
	//以下是获取服务节点的参数 信息
	public static LDTaskServerParamSet getLDTaskServerParamSet()
	{
		if(mLDTaskServerParamSet==null)
		{
			mLDTaskServerParamSet = new LDTaskServerParamSet();
			String tServerIP = getServerIP();
			String tServerPort = getServerPort();
			LDTaskServerParamDB tLDTaskServerParamDB = new LDTaskServerParamDB();
			tLDTaskServerParamDB.setServerIP(tServerIP);
			tLDTaskServerParamDB.setServerPort(tServerPort);
			mLDTaskServerParamSet = tLDTaskServerParamDB.query();
		}
		
		return mLDTaskServerParamSet;
	}
	
	//服务节点初始线程数
	public static String getthreadNum()
	{
		if(mthreadNum==null)
		{
			LDTaskServerParamSet tLDTaskServerParamSet = getLDTaskServerParamSet();
			for(int i=1;i<=tLDTaskServerParamSet.size();i++)
			{
				String tParamName = tLDTaskServerParamSet.get(i).getParamName();
				String tParamValue = tLDTaskServerParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("threadnum"))
				{
					mthreadNum = tParamValue;
					break;
				}
			}
		}
		
		
		return mthreadNum;
	}
	
	//服务节点最大线程数
	public static String getMaxthreadNum()
	{
		if(mMaxthreadNum==null)
		{
			LDTaskServerParamSet tLDTaskServerParamSet = getLDTaskServerParamSet();
			for(int i=1;i<=tLDTaskServerParamSet.size();i++)
			{
				String tParamName = tLDTaskServerParamSet.get(i).getParamName();
				String tParamValue = tLDTaskServerParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("maxthreadnum"))
				{
					mMaxthreadNum = tParamValue;
					break;
				}
			}
		}
		
		
		return mMaxthreadNum;
	}
	
	//是否备份节点标记
	public static String getBakNode()
	{
		if(mBakNode==null)
		{
			LDTaskServerParamSet tLDTaskServerParamSet = getLDTaskServerParamSet();
			for(int i=1;i<=tLDTaskServerParamSet.size();i++)
			{
				String tParamName = tLDTaskServerParamSet.get(i).getParamName();
				String tParamValue = tLDTaskServerParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("baknode"))
				{
					mBakNode = tParamValue;
					break;
				}
			}
		}
		
		//没有描述数据,就默认返回N-正常节点
		if(mBakNode==null)
		{
			return "N";
		}
		return mBakNode;
	}
	
	/**
	 * 服务器线程池类型：Fix固定大小，Cache自动分配大小
	 * @return
	 */
	public static String getPoolMode() {
		if(mPoolMode==null)
		{
			LDTaskServerParamSet tLDTaskServerParamSet = getLDTaskServerParamSet();
			for(int i=1;i<=tLDTaskServerParamSet.size();i++)
			{
				String tParamName = tLDTaskServerParamSet.get(i).getParamName();
				String tParamValue = tLDTaskServerParamSet.get(i).getParamValue();
				if(tParamName!=null&&tParamName.toLowerCase().equals("poolmode"))
				{
					mPoolMode = tParamValue;
					break;
				}
			}
		}
		
		//没有描述数据,就默认返回N-正常节点
		if(mPoolMode==null)
		{
			return "Fix";
		}
		return mPoolMode;
	}

	/////////////////////////////
	//清除系统参数
	public static void refreshServerParam()
	{
		serverip = null;
		serverport  = null;
		mServerType = null;
		mRunType = null;
		mDebug = null;
		mInvalidTime = null;
		mLDTaskParamSet = null;
		
		mLDTaskServerParamSet = null;
		mBakNode = null;
		mthreadNum = null;
		mMaxthreadNum = null;
		mPoolMode = null;
	}
	
	
	public static void outPrint(String tMsg)
	{
		String tDebug = getDebug();
		if(tDebug.equals("1"))
		{
			logger.debug(tMsg);
		}
	}
	public static void main(String[] args) {
		logger.debug("1:"+MultiTaskServer.getServerType());
		logger.debug("2:"+MultiTaskServer.getServerType());
	
	}
}
