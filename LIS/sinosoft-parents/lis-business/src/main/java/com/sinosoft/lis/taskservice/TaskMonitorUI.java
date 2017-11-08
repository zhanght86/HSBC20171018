/**
 * Copyright (c) 2011 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;

import org.apache.log4j.Logger;

import com.sinosoft.lis.config.TaskCachedService;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 批处理任务进度监听用户接口类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HuangLiang
 * @version 1.0
 */
public class TaskMonitorUI implements BusinessService {
	private static Logger logger = Logger.getLogger(TaskMonitorUI.class);

	// private static TaskServiceEngine mTaskServiceEngine;
	// private static Timer mTaskServiceTimer;
	// private static boolean mTimerRunning;
	// private String mOperator;
	private VData mResult = new VData();

	// private MMap mMap = new MMap();
	public CErrors mErrors = new CErrors();

	public TaskMonitorUI() {
	}

	/**
	 * 后台服务提交
	 * 
	 * @param aInputData
	 *            VData
	 * @param aOperate
	 *            String
	 * @return boolean return
	 */
	@SuppressWarnings("unchecked")
	public boolean submitData(VData aInputData, String aOperate) {
		mResult.clear();
		TaskCachedService tCachedTaskService = TaskCachedService.getInstance();
		if ("action".equalsIgnoreCase(aOperate)) {
			VData a = (VData) aInputData.get(1);
			VData t = new VData();
			int size = a.size();
			for (int i = 0; i < size; i++) {
				t.add((String) tCachedTaskService.getTaskServiceState(a.get(i)
						.toString()));
			}
			this.mResult.add(t);
		} else if ("initial".equalsIgnoreCase(aOperate)) {
			VData a = (VData) aInputData.get(1);
			for (int i = 0; i < a.size(); i++) {
				tCachedTaskService.setTaskServiceState(a.get(i).toString(), "NOFOUND");
			}
		}
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	/**
	 * 
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
	}
}
