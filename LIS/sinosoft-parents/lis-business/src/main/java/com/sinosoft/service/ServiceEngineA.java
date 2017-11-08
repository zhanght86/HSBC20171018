package com.sinosoft.service;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class ServiceEngineA extends TimerTask {
private static Logger logger = Logger.getLogger(ServiceEngineA.class);
	private Vector mTaskWaitList = new Vector();
	private CovertPoolA mCovertPoolA = null;
	private HashMap mHashMap = new HashMap();
	private int runcount = 0;
	private Timer mTimer;
	public ServiceResult mServiceResult = new ServiceResult();
	// 同时初始化引擎的工作池
	public ServiceEngineA(String classname, int threadnum, Timer tTimer) {
		mCovertPoolA = new CovertPoolA(classname, threadnum);
		mTimer = tTimer;
	}

	public synchronized void run() {
		// TODO Auto-generated method stub

		int count = mTaskWaitList.size();
		Iterator tIterator = mTaskWaitList.iterator();
		boolean flag = false;
		runcount++;
//		logger.debug("当前是第" + "runcount" + "次探测!  runcount test:"
//				+ runcount);
		if (count > 0) {

			// for (int i = 0; i < count; i++) {
			// if (!mHashMap.containsKey(mTaskWaitList.get(i))) {
			// logger.debug("dangqianjigou:"+mTaskWaitList.get(i));
			// flag = true; // 存在未处理的任务就修改为false;
			// break;
			// }
			// }
			while (tIterator.hasNext()) {
				if (!mHashMap.containsKey(tIterator.next())) {
					//logger.debug("not in hashmap");
					flag = true;// 存在未处理的任务就修改为false;
					break;
				} else {
					tIterator.remove();
				}
			}
		}
		if (flag) {
			tIterator = mTaskWaitList.iterator();
			while (tIterator.hasNext()) {
				Object tObject = tIterator.next();
				if (mHashMap.containsKey(tObject)) {
					logger.debug("该任务已经被执行!:wkwkwkwkw:"
							+ tObject.toString());
					tIterator.remove();
					continue;
				}
				//logger.debug("get pool:");
				CovBase tCovBase = mCovertPoolA.getCovert();
				if (tCovBase == null) {
//					logger.debug("没有空闲!weikaiweikai!");
					break;
				}

//				VData tVData = new VData();
//				tVData.add(tObject);
				//logger.debug("tObject:" + tObject.toString());
				
				tCovBase.setObject(tObject);
				tCovBase.setServiceResult(this.mServiceResult);
				new Thread(tCovBase).start();
				mHashMap.put(tObject, "");
				tIterator.remove();
			}
		} else {
			logger.debug("mTimer.cancel");
			this.notify();
			mTimer.cancel(); // 不存在未处理的任务 停止监听
		}
	}

	public void startEngine(Vector tTaskWaitList) {
		this.mTaskWaitList = tTaskWaitList;
	}
	public boolean getPoolNotInUse()
	{
		return mCovertPoolA.testPool(); 
	}
	
}
