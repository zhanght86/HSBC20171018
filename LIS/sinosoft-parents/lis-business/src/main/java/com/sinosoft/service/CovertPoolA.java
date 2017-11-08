package com.sinosoft.service;
import org.apache.log4j.Logger;


public class CovertPoolA {
private static Logger logger = Logger.getLogger(CovertPoolA.class);

	private String mClassName = "";
	private CovBase[] CovBaseList;
	private ServiceSemaphore mServiceSemaphore = new ServiceSemaphore();
	private int mCount;

	public CovertPoolA(String classname,int threadnum) {
		mClassName = classname;
		mCount= threadnum;
		init();
	}

	private boolean init() {
		try {
			CovBaseList = new CovBase[mCount];
			for (int nIndex = 0; nIndex < mCount; nIndex++) {
				CovBaseList[nIndex] = (CovBase) Class.forName(
						this.mClassName).newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public CovBase getCovert() {

//		if(!init())
//		{
//			logger.debug("init CovertPoolA error:classname:"+mClassName);
//			return null;
//		}

		try {
			mServiceSemaphore.Lock();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		for (int nIndex = 0; nIndex < mCount; nIndex++) {
			CovBase tCovBase = CovBaseList[nIndex];
			if (tCovBase.getInUse()) {
				continue;
			}
			tCovBase.setInUse();
			mServiceSemaphore.UnLock();
			return tCovBase;
		}
		mServiceSemaphore.UnLock();
		return null;
	}
	/*
	 * 检查该线程池所有线程是否全部处于空闲
	 */
	public boolean testPool()
	{
		for (int nIndex = 0; nIndex < mCount; nIndex++) {
			CovBase tCovBase = CovBaseList[nIndex];
			if (tCovBase.getInUse()) {
				return false;
			}
		}
		return true;
	}

}
