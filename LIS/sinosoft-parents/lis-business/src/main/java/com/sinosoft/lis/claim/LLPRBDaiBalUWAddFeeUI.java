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
 * Description: 二次核保加费日结清单
 * </p>
 * 统计界面：统计机构，实收起期，实收止期，（财务科目），排序选项 排序：1.审批通过日期＋赔案号 2.险种号＋赔案号
 * 3.科目＋险种＋赔案号（由于财务科目选项为空时，不统计二次核保加费日结日结，所以按2与3排序相同） 表尾：各项数据的合计
 * <p>
 * Copyright : Copyright (c) 2002
 * </p>
 * <p>
 * Company :
 * </p>
 * 
 * @author : zhaorx,2006-02-24
 * @version : 1.0
 */

public class LLPRBDaiBalUWAddFeeUI {
private static Logger logger = Logger.getLogger(LLPRBDaiBalUWAddFeeUI.class);
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

	private String mManageCom = ""; // 统计机构
	private String mStartDate = ""; // 实收起期
	private String mEndDate = ""; // 实收止期
	private String mFinDayType = ""; // 财务科目(仅校验页面上是否所选为：14--二次核保加费日结，数据处理中并未用到)
	private String mOrderBy = ""; // 排序选项

	public LLPRBDaiBalUWAddFeeUI() {
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

		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 实收起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 实收止期
		mFinDayType = (String) mTransferData.getValueByName("FinDayType");// 财务科目
		mOrderBy = (String) mTransferData.getValueByName("StatiOpti"); // 排序选项
		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取用户登录信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mStartDate == null || mEndDate == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取“实收起期”或 “实收止期”信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mManageCom == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取统计机构信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mFinDayType == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取财务科目信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mOrderBy == null) {
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFeeBL";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取排序选项信息失败!";
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

		LLPRBDaiBalUWAddFeeBL tLLPRBDaiBalUWAddFeeBL = new LLPRBDaiBalUWAddFeeBL();
		if (tLLPRBDaiBalUWAddFeeBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBDaiBalUWAddFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFee";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBDaiBalUWAddFeeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}
