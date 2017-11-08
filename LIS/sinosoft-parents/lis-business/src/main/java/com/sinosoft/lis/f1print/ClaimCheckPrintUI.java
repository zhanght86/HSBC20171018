/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
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
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2002
 * </p>
 * <p>
 * Company : sinosoft
 * </p>
 * 
 * @author : zhaorx 2006-03-09
 * @version : 0.1
 */
public class ClaimCheckPrintUI {
private static Logger logger = Logger.getLogger(ClaimCheckPrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private GlobalInput mG = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private String mClmNo = ""; // 赔案号

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public ClaimCheckPrintUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备传往后台的数据
		VData vData = new VData();
		if (!prepareOutputData(vData)) {
			return false;
		}
		ClaimCheckPrintBL tClaimCheckPrintBL = new ClaimCheckPrintBL();
		logger.debug("Start ClaimCheckPrint UI Submit ...");
		if (!tClaimCheckPrintBL.submitData(vData, cOperate)) {
			if (tClaimCheckPrintBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tClaimCheckPrintBL.mErrors);
				return false;
			} else {
				buildError("submitData", "ClaimCheckPrintBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tClaimCheckPrintBL.getResult();
			return true;
		}
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
		mClmNo = (String) mTransferData.getValueByName("ABClmNo"); // 赔案号
		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		if (mG == null) {
			CError tError = new CError();
			tError.moduleName = "ClaimCheckPrintUI";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取用户登录信息失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (mClmNo == null || mClmNo == "") {
			CError tError = new CError();
			tError.moduleName = "ClaimCheckPrintUI";
			tError.functionName = "checkInputData";
			tError.errorMessage = "获取赔案号信息失败!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("UI@@@@@@@" + mClmNo);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.add(mGlobalInput);
			vData.add(mTransferData);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ClaimCheckPrintUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试主函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ABClmNo", "90000020985"); // 赔案号

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		ClaimCheckPrintBL tClaimCheckPrintBL = new ClaimCheckPrintBL();
		if (tClaimCheckPrintBL.submitData(tVData, "") == false) {
			logger.debug("-------收付费--收 费--理赔二核收费发票打印（ClaimCheckPrintUI）---失败！----");
		}
		int n = tClaimCheckPrintBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tClaimCheckPrintBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
