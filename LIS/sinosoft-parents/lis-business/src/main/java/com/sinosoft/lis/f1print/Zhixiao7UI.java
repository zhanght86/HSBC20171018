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

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class Zhixiao7UI {
private static Logger logger = Logger.getLogger(Zhixiao7UI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	// private String strAgentCode;
	private String strStartDate;

	// private String strFlag;
	private String strEndDate;
	private String strAgentGroup;
	private String strSaleChnl;

	public Zhixiao7UI() {
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

			Zhixiao7BL tZhixiao7BL = new Zhixiao7BL();
			logger.debug("Start tZhixiao7 UI Submit ...");
			if (!tZhixiao7BL.submitData(vData, cOperate)) {
				if (tZhixiao7BL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tZhixiao7BL.mErrors);
					return false;
				} else {
					buildError("submitData", "Zhixiao7BL发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tZhixiao7BL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "tZhixiao7UI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strStartDate);

		vData.add(strEndDate);
		vData.add(strSaleChnl);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strStartDate = (String) cInputData.get(1);
		strEndDate = (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(3);

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "tZhixiao7UI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
