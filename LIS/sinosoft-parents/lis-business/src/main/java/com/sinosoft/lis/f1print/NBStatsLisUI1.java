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

public class NBStatsLisUI1 {
private static Logger logger = Logger.getLogger(NBStatsLisUI1.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom, strStartDate, strEndDate, strNode, strSaleCh;

	public NBStatsLisUI1() {
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

			NBStatsLisBL1 tNBStatsLisBL1 = new NBStatsLisBL1();
			logger.debug("Start NBStatsLis UI Submit ...");
			if (!tNBStatsLisBL1.submitData(vData, cOperate)) {
				if (tNBStatsLisBL1.mErrors.needDealError()) {
					mErrors.copyAllErrors(tNBStatsLisBL1.mErrors);
					return false;
				} else {
					buildError("submitData", "NBStatsLisBL1发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tNBStatsLisBL1.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "NBStatsLisUI1";
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
		vData.add(strNode);
		vData.add(strSaleCh);
		vData.add(strStartDate);
		return true;
	}

	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strEndDate = (String) cInputData.get(1);
		strNode = (String) cInputData.get(2);
		strSaleCh = (String) cInputData.get(3);
		strStartDate = (String) cInputData.get(4);
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "NBStatsLisUI1";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
