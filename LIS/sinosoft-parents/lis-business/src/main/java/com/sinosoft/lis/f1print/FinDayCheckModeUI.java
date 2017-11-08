package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class FinDayCheckModeUI {
private static Logger logger = Logger.getLogger(FinDayCheckModeUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// private VData mInputData = new VData();
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mDay[] = null;

	public FinDayCheckModeUI() {
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
			if (!getInputData(cInputData)) {
				return false;
			}

			// 准备传往后台的数据
			VData vData = new VData();

			if (!prepareOutputData(vData)) {
				return false;
			}

			FinDayCheckModeBL tFinDayCheckModeBL = new FinDayCheckModeBL();
			logger.debug("Start FinDayCheckModeBL Submit ...");

			if (!tFinDayCheckModeBL.submitData(vData, cOperate)) {
				if (tFinDayCheckModeBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tFinDayCheckModeBL.mErrors);
					return false;
				} else {
					buildError("submitData",
							"tFinDayCheckModeBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tFinDayCheckModeBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "FinDayCheckModeUI";
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

			vData.add(mDay);
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

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mDay = (String[]) cInputData.get(0);
		// mDay=(String[])cInputData.getObjectByObjectName("String[]",0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		logger.debug("start");
		logger.debug("end");
		logger.debug(mGlobalInput.Operator);
		logger.debug(mGlobalInput.ManageCom);

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "FinDayCheckModeUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		FinDayCheckModeUI fin = new FinDayCheckModeUI();
		String ttDay[] = new String[2];
		ttDay[0] = "2003-05-01";
		ttDay[1] = "2003-05-26";
		VData tVData = new VData();
		tVData.addElement(ttDay);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		tVData.addElement(tG);
		fin.submitData(tVData, "PRINTPAY");
	}
}
