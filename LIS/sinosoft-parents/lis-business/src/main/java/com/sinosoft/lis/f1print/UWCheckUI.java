package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class UWCheckUI {
private static Logger logger = Logger.getLogger(UWCheckUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// private VData mInputData = new VData();
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mRiskCode = "";
	private String mRiskFlag = "";
	private String mDay[] = null;
	private String mManageCom = "";
	private String mDefineCode = "";

	public UWCheckUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			if (!cOperate.equals("PRINTGET") && !cOperate.equals("PRINTPAY")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}
			// 得到外部传入的数据，将数据备份到本类中
			/*
			 * if (!getInputData(cInputData)) { return false; }
			 *  // 进行业务处理 if (!dealData()) { return false; }
			 *  // 准备传往后台的数据 VData vData = new VData(); if
			 * (!prepareOutputData(vData)) { return false; }
			 */
			UWCheckBL tUWCheckBL = new UWCheckBL();
			logger.debug("Start UWCheckBL Submit ...");

			if (!tUWCheckBL.submitData(cInputData, cOperate)) {
				if (tUWCheckBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tUWCheckBL.mErrors);
					return false;
				} else {
					buildError("submitData", "tUWCheckBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tUWCheckBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "tRiskClaimCheckUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mRiskCode);
			vData.add(mRiskFlag);
			vData.add(mDay);
			vData.add(mManageCom);
			vData.add(mDefineCode);
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
	private boolean getInputData(VData cInputData) { // 全局变量
		mRiskCode = (String) cInputData.get(0);
		mRiskFlag = (String) cInputData.get(1);
		mDay = (String[]) cInputData.get(2);
		mManageCom = (String) cInputData.get(3);
		mDefineCode = (String) cInputData.get(4);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mDefineCode == "") {
			buildError("mDefineCode", "没有得到足够的信息！");
			return false;
		}
		if (mManageCom == "") {
			buildError("ManageCom", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RiskClaimCheckUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		UWCheckUI UW = new UWCheckUI();
		String ttDay[] = new String[2];
		ttDay[0] = "2004-10-01";
		ttDay[1] = "2004-10-08";
		String mCOM = "86";
		String mRiskCode = "";
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "002";
		String code = "uw7";

		String mRiskFlag = "on";
		VData tVData = new VData();
		tVData.addElement(mRiskCode);
		tVData.addElement(mRiskFlag);
		tVData.addElement(ttDay);
		tVData.addElement(mCOM);
		tVData.addElement(code);
		tVData.addElement(tG);
		UW.submitData(tVData, "PRINTPAY");
	}
}
