/*
 * <p>ClassName: LDCode1ConfigUI </p>
 * <p>Description: LDCode1配置 LDCode1ConfigUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:

 */
package com.sinosoft.lis.config;

import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDCode1ConfigUI implements BusinessService {
	private static Logger logger = Logger.getLogger(LDCode1ConfigUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 全局数据 */
	public LDCode1ConfigUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		LDCode1ConfigBL tLDCode1ConfigBL = new LDCode1ConfigBL();

		logger.debug("Start LDCode1ConfigUI UI Submit...");
		tLDCode1ConfigBL.submitData(cInputData, mOperate);
		 logger.debug("End LDCode1ConfigUI UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDCode1ConfigBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCode1ConfigBL.mErrors);
			return false;
		}
			this.mResult.clear();
			this.mResult = tLDCode1ConfigBL.getResult();
			mInputData = null;
			return true;
	}

	public static void main(String[] args) {

	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
