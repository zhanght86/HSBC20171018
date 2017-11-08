/*
 * <p>ClassName: LDSysVarConfigUI </p>
 * <p>Description: 系统变量配置 LDSysVarConfigUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:

 */
package com.sinosoft.lis.config;

import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDSysVarConfigUI implements BusinessService {
	private static Logger logger = Logger.getLogger(LDSysVarConfigUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 全局数据 */
	public LDSysVarConfigUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		LDSysVarConfigBL tLDSysVarConfigBL = new LDSysVarConfigBL();

		logger.debug("Start LDSysVarConfigUI UI Submit...");
		tLDSysVarConfigBL.submitData(cInputData, mOperate);
		 logger.debug("End LDSysVarConfigUI UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDSysVarConfigBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDSysVarConfigBL.mErrors);
			return false;
		}
			this.mResult.clear();
			this.mResult = tLDSysVarConfigBL.getResult();
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
