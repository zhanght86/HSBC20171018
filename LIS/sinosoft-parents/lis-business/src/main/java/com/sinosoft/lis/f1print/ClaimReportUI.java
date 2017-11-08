/**
 * <p>Title: 个险理赔报表打印</p>
 * <p>Description: 个险理赔报表打印</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author mw
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class ClaimReportUI {
private static Logger logger = Logger.getLogger(ClaimReportUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public ClaimReportUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {

			ClaimReportBL tClaimReportBL = new ClaimReportBL();
			logger.debug("Start RiskClaimCheckBL Submit ...");

			if (!tClaimReportBL.submitData(cInputData, cOperate)) {
				if (tClaimReportBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tClaimReportBL.mErrors);
					return false;
				} else {
					buildError("submitData", "报表打印发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tClaimReportBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			buildError("submitData", e.toString());
			return false;
		}
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
		ClaimReportUI RC = new ClaimReportUI();
		String ttDay[] = new String[2];
		ttDay[0] = "2004-08-26";
		ttDay[1] = "2004-09-25";
		String mCOM = "86";

		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		String code = "claim1";

		VData tVData = new VData();
		tVData.addElement(ttDay);
		tVData.addElement(mCOM);
		tVData.addElement(code);
		tVData.addElement(tG);
		RC.submitData(tVData, "PRINTPAY");
	}
}
