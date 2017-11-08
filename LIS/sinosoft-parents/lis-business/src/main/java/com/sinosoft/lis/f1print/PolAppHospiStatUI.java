package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
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
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class PolAppHospiStatUI {
private static Logger logger = Logger.getLogger(PolAppHospiStatUI.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public PolAppHospiStatUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		PolAppHospiStatBL tPolAppHospiStatBL = new PolAppHospiStatBL();
		if (!tPolAppHospiStatBL.submitData(cInputData, cOperate)) {
			mErrors.copyAllErrors(tPolAppHospiStatBL.mErrors);
			return false;
		} else {
			mResult = tPolAppHospiStatBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		String mOperate = "PRINT"; //

		String StartDate = "2007-3-1"; // 开始日期
		String EndDate = "2007-3-31"; // 结束日期
		String ManageType = "2";
		; // 管理机构类型
		String SalechnlType = "13"; // 销售渠道类型
		String ReportType = "App01"; // 统计报表类型

		logger.debug("****开始日期: " + StartDate);
		logger.debug("****结束日期: " + EndDate);
		logger.debug("****机构类型: " + ManageType);
		logger.debug("****销售渠道类型: " + SalechnlType);
		logger.debug("****统计报表类型: " + ReportType);
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";

		PolAppOtherStatUI tPolAppOtherStatUI = new PolAppOtherStatUI();

		VData tVData = new VData();
		VData mResult = new VData();

		tVData.addElement(StartDate);
		tVData.addElement(EndDate);
		tVData.addElement(ManageType);
		tVData.addElement(SalechnlType);
		tVData.addElement(ReportType);
		tVData.addElement(tG);
		logger.debug("Start PolAppStatUI...");
		if (!tPolAppOtherStatUI.submitData(tVData, mOperate)) {

		}
	}
}
