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

public class NBNodeSumLisUI {
private static Logger logger = Logger.getLogger(NBNodeSumLisUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom, strStartDate, strEndDate, strNode, strSale;

	public NBNodeSumLisUI() {
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

			NBNodeSumLisBL tNBNodeSumLisBL = new NBNodeSumLisBL();
			logger.debug("Start NBStatsLis UI Submit ...");
			if (!tNBNodeSumLisBL.submitData(vData, cOperate)) {
				if (tNBNodeSumLisBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tNBNodeSumLisBL.mErrors);
					return false;
				} else {
					buildError("submitData", "NBStatsLisBL1发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tNBNodeSumLisBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "NBNodeSumLisUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {
		vData.clear();
		vData.add(strMngCom);
		vData.add(strEndDate);
		vData.add(strSale);
		vData.add(strStartDate);
		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strEndDate = (String) cInputData.get(1);
		strSale = (String) cInputData.get(2);
		strStartDate = (String) cInputData.get(3);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "NBNodeSumLisUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
