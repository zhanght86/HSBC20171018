package com.sinosoft.lis.easyscan.service;
import org.apache.log4j.Logger;

import com.sinosoft.lis.easyscan.EasyScanService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class UnSupportBeforeScanService implements EasyScanService{
private static Logger logger = Logger.getLogger(UnSupportBeforeScanService.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	/** 数据操作字符串 */
	private String DocType;
	private String UFlag = "0";

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	
	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData inputData, String operate) {
		CError.buildErr(this, "不支持事前扫描");
		return false;
	}

}
