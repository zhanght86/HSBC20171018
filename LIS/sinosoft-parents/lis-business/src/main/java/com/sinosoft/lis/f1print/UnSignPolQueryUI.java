package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
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
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class UnSignPolQueryUI implements BusinessService {
private static Logger logger = Logger.getLogger(UnSignPolQueryUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String strMngCom;
	private String strScanType;
	private String strPrtNo;
	private String strStartDate;
	private String strEndDate;
	private String strPrintDate;
	private GlobalInput mGlobalInput = new GlobalInput();

	public UnSignPolQueryUI() {
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

			UnSignPolQueryBL tUnSignPolQueryBL = new UnSignPolQueryBL();
			logger.debug("Start GrpNoticeListUI Submit ...");
			if (!tUnSignPolQueryBL.submitData(cInputData, cOperate)) {
				if (tUnSignPolQueryBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tUnSignPolQueryBL.mErrors);
					return false;
				} else {
					buildError("submitData", "发生错误，但是没有提供详细的出错信息");
					return false;
				}
			} else {
				mResult = tUnSignPolQueryBL.getResult();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError cError = new CError();
			cError.moduleName = "GrpNoticeListUI";
			cError.functionName = "submit";
			cError.errorMessage = e.toString();
			mErrors.addOneError(cError);
			return false;
		}
	}

	private boolean prepareOutputData(VData vData) {

		return true;
	}

	private boolean getInputData(VData cInputData) {

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpNoticeListUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}

	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
