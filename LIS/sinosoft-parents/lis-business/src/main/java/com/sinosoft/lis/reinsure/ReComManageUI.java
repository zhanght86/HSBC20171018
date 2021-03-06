

/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

/*
 * <p>ClassName: ReComManageUI </p>
 * <p>Description: ReComManageUI类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ReComManageUI implements BusinessService {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String strOperate;

	// 业务处理相关变量
	/** 全局数据 */

	public ReComManageUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;
		ReComManageBL tReComManageBL = new ReComManageBL();
		if (!tReComManageBL.submitData(cInputData, cOperate)) {
			if (tReComManageBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tReComManageBL.mErrors);
			} else {
				buildError("submitData", "ReComManageBL发生错误，但是没有提供详细信息！");
			}
			return false;
		}
		mResult = tReComManageBL.getResult();
		return true;
	}

	public static void main(String[] args) {
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CardPlanUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
