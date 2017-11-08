package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class NBrealtimeCBLisUI {
private static Logger logger = Logger.getLogger(NBrealtimeCBLisUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String ManageCom = "";
	private String StartDate = "";
	private String BranchCode = "";
	private String EndDate = "";
	private String accType = "";

	public NBrealtimeCBLisUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
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

		NBrealtimeCBLisBL tNBrealtimeCBLisBL = new NBrealtimeCBLisBL();
		logger.debug("Start BusiUI Submit ...");

		if (!tNBrealtimeCBLisBL.submitData(vData, cOperate)) {
			if (tNBrealtimeCBLisBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tNBrealtimeCBLisBL.mErrors);
				return false;
			} else {
				buildError("submitData", "tNBrealtimeCBLisBL发生错误，但是没有提供详细的出错信息");
				return false;
			}
		} else {
			mResult = tNBrealtimeCBLisBL.getResult();
			return true;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.addElement(ManageCom);
			vData.addElement(StartDate);
			vData.addElement(BranchCode);
			vData.add(EndDate);
			vData.add(accType);
			vData.add(mGlobalInput);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
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
		// 全局变量
		ManageCom = (String) cInputData.get(0);
		StartDate = (String) cInputData.get(1);
		BranchCode = (String) cInputData.get(2);
		logger.debug(BranchCode);
		EndDate = (String) cInputData.get(3);
		accType = (String) cInputData.get(4);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 5));

		logger.debug("isaofaos");
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "AgentTempUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		String ManageCom = "8632";
		String StartDate = "2006-09-01";
		String BranchCode = "";
		String EndDate = "2006-09-08";
		String type = "02";

		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "liuyy";
		tG.ManageCom = "86";
		tVData.addElement(ManageCom);
		tVData.addElement(StartDate);
		tVData.addElement(BranchCode);
		tVData.addElement(EndDate);
		tVData.addElement(type);
		tVData.addElement(tG);

		NBrealtimeCBLisUI UI = new NBrealtimeCBLisUI();
		if (!UI.submitData(tVData, "")) {
			if (UI.mErrors.needDealError()) {
				logger.debug(UI.mErrors.getFirstError());
			} else {
				logger.debug("UI发生错误，但是没有提供详细的出错信息");
			}
		} else {
			VData vData = UI.getResult();
			logger.debug("已经接收了数据!!!");
		}
	}
}
