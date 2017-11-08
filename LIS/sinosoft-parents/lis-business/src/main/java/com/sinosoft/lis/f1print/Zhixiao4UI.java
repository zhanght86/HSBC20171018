package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘岩松
 * @version 1.0
 * @date 2003-04-16
 * @function print notice bill User Interface Layer
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class Zhixiao4UI {
private static Logger logger = Logger.getLogger(Zhixiao4UI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strManageCom;
	private String strPrtDate;

	public Zhixiao4UI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 只对打印操作进行支持
			if (!cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			if (!getInputData(cInputData)) {
				return false;
			}
			VData vData = new VData();
			if (!prepareOutputData(vData)) {
				return false;
			}

			Zhixiao4BL tZhixiao4BL = new Zhixiao4BL();
			logger.debug("Start Zhixiao4UI Submit ...");
			if (!tZhixiao4BL.submitData(cInputData, cOperate)) {
				if (tZhixiao4BL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tZhixiao4BL.mErrors);
					return false;
				} else {
					buildError("submitData", "SignfailBL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tZhixiao4BL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "Zhixiao4";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strManageCom);
		logger.debug("UI prepareOutputData管理机构：" + strManageCom);
		vData.add(strPrtDate);
		logger.debug("UI prepareOutputData打印日期：" + strPrtDate);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strManageCom = (String) cInputData.get(0);
		logger.debug("UI getInputData管理机构：" + strManageCom);
		strPrtDate = (String) cInputData.get(1);
		logger.debug("UI getInputData打印日期：" + strPrtDate);
		return true;

	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "Zhixiao4UI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String ManageCom = "86210000";
		String PrtDate = "";
		VData tVData = new VData();
		GlobalInput tg = new GlobalInput();
		tg.ManageCom = "86210000";
		tg.ComCode = "86510000";
		tg.Operator = "110001";
		String strSaleChnl = "1";
		tVData.addElement(ManageCom);
		// tVData.addElement(PrtDate);
		tVData.addElement(strSaleChnl);
		// tVData.addElement("8300090000000021");

		Zhixiao4UI tZhixiao4UI = new Zhixiao4UI();
		tZhixiao4UI.submitData(tVData, "PRINT");
	}
}
