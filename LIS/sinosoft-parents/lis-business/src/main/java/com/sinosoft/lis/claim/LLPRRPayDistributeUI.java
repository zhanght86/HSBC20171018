package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 给付金额分布统计
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司、用户）、机构统计范围选项（总公司、某分公司、某中支）
 * 统计时间段、理赔类型（单个理赔类型、全部理赔类型）、统计层面为赔案层面 表头：报表名称、统计条件、统计日期
 * 数据项：机构（或用户）、0-500,500-1000,1000-5000,5000-1万,1万-3万,3万-5万,5万-1万和10万以上
 * 共8个金额分布区间的给付数和给付金额（区间为自定义结果） 算法：按照选择的条件统计各项数据，统计口径为结案日期 排序：机构（或用户） 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaorx,2005-10-04 9:32
 * @version 1.0
 */

public class LLPRRPayDistributeUI {
private static Logger logger = Logger.getLogger(LLPRRPayDistributeUI.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private GlobalInput mG = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mLevel = ""; // 机构层面选项
	private String mManageCom = ""; // 机构统计范围选项
	private String mClaimType = ""; // 理赔类型
	private String mNCLType = ""; // 申请类型

	public LLPRRPayDistributeUI() {
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mInputData = cInputData;
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		mClaimType = (String) mTransferData.getValueByName("ClaimType"); // 理赔类型
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取用户登录信息失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (mStartDate == null || mEndDate == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取“统计起期”或 “统计止期”信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mManageCom == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取统计机构信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLevel == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取统计层面信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mNCLType == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRRInvestFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取申请类型信息失败!";
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}

		LLPRRPayDistributeBL tLLPRRPayDistributeBL = new LLPRRPayDistributeBL();
		if (tLLPRRPayDistributeBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRRPayDistributeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRRPayDistribute";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRRPayDistributeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}
