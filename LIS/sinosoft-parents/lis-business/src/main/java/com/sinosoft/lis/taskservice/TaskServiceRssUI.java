package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * RSS日志用户接口类，调用逻辑处理来提供两种主要功能：生成RSS的文件，返回文件路径。
 * 该类继承了TaskThread，这样可以作为定时执行的计划，来定时更新RSS的文件
 * 
 * @author sinosoft
 * 
 */
public class TaskServiceRssUI implements BusinessService {
private static Logger logger = Logger.getLogger(TaskServiceRssUI.class);
	public CErrors mErrors = new CErrors();

	private VData mInputData = new VData();

	private VData mResult = new VData();

	/**
	 * 
	 */
	public TaskServiceRssUI() {
		// super();
		// TODO Auto-generated constructor stub
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}

	public boolean submitData(VData tVData, String tOperater) {
		// TODO Auto-generated method stub
		if (!check()) {
			return false;
		}
		if (!getInputData(tVData)) {
			return false;
		}
		if (!dealData(tOperater)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据传入操作符来生成RSS或者把RSS文件路径保存到结果中
	 * 
	 * @param tOperater
	 *            OrderRSS:订阅路径 GenerateRSS：生成
	 * @return boolean
	 */
	private boolean dealData(String tOperater) {
		try {
			if ("OrderRSS".equalsIgnoreCase(tOperater)) {
				TaskServiceRssBL tTaskServiceRssBL = new TaskServiceRssBL();
				if (!tTaskServiceRssBL.submitData(this.mInputData, "OrderRSS")) {
					this.mErrors = tTaskServiceRssBL.getErrors();
					return false;
				}
				this.mResult = tTaskServiceRssBL.getResult();
			} else if ("GenerateRSS".equalsIgnoreCase(tOperater)) {
				TaskServiceRssBL tTaskServiceRssBL = new TaskServiceRssBL();
				if (!tTaskServiceRssBL.submitData(null, "GenerateRSS")) {
					this.mErrors = tTaskServiceRssBL.getErrors();
					return false;
				}
			} else if ("AddressRSS".equalsIgnoreCase(tOperater)) {
				TaskServiceRssBL tTaskServiceRssBL = new TaskServiceRssBL();
				if (!tTaskServiceRssBL
						.submitData(this.mInputData, "AddressRSS")) {
					this.mErrors = tTaskServiceRssBL.getErrors();
					return false;
				}
				this.mResult = tTaskServiceRssBL.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData) {
		try {
			this.mInputData = cInputData;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean check() {
		return true;
	}
}
