package com.sinosoft.workflow.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimInqConclusionBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 调查结论《赔案层面》处理信息提交服务类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */

public class LLClaimInqConclusionAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(LLClaimInqConclusionAfterInitService.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 提交数据容器 */
	private MMap map = new MMap();
	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String transact;

	public LLClaimInqConclusionAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;
		logger.debug("LLClaimInqConclusionAfterInitService----getInputData successful!");

		// 校验传入数据
		if (!checkData())
			return false;
		logger.debug("LLClaimInqConclusionAfterInitService----Start dealData...");
		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("LLClaimInqConclusionAfterInitService----dealData successful!");
		if (!prepareOutputData())
			return false;
		logger.debug("LLClaimInqConclusionAfterInitService----Start  Submit...");
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		logger.debug("----------LLClaimInqConclusionAfterInitService checkData BEGIN----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		logger.debug("----------LLClaimInqConclusionAfterInitService getInputData BEGIN----------");

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		mOperate = cOperate;

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mSubMissionID == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，调用BL进行逻辑处理，返回处理完数据 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("----------LLClaimInqConclusionAfterInitService dealData BEGIN----------");
		boolean tReturn = false;
		transact = (String) mTransferData.getValueByName("transact"); // 传送动作
		logger.debug("----------LLClaimInqConclusionAfterInitService transact----------"
						+ transact);

		LLClaimInqConclusionBL tLLClaimInqConclusionBL = new LLClaimInqConclusionBL();
		if (!tLLClaimInqConclusionBL.submitData(mInputData, transact)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLClaimInqConclusionBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimInqConclusionAfterInitService";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			mInputData = null;
			tReturn = false;
		} else {
			mInputData = null;
			mInputData = tLLClaimInqConclusionBL.getResult();
			logger.debug("-----start LLClaimInqConclusionAfterInitService getData from BL");
			map = (MMap) mInputData.getObjectByObjectName("MMap", 0);
			tReturn = true;
		}
		return tReturn;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
