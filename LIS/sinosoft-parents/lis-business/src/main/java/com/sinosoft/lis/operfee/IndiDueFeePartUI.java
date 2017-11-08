package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;


/**
 * Title: Web业务系统
 * 
 * Copyright: Copyright (c) 2002
 * 
 * Company: Sinosoft
 * 
 * @author HZM
 * @version 1.0
 */

public class IndiDueFeePartUI  implements BusinessService{
private static Logger logger = Logger.getLogger(IndiDueFeePartUI.class);

	// 业务处理相关变量
	private VData mInputData = new VData();

	public CErrors mErrors = new CErrors();

	private VData mResult;

	private VData m1Result;

	public IndiDueFeePartUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start IndiDueFeePartUI.submitData...");

		mInputData = (VData) cInputData.clone();

		RnDealBLF tIndiDueFeePartBLF = new RnDealBLF();
		tIndiDueFeePartBLF.submitData(mInputData, cOperate);

		mResult = tIndiDueFeePartBLF.getResult();
		m1Result = tIndiDueFeePartBLF.getLCResult();
		

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tIndiDueFeePartBLF.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tIndiDueFeePartBLF.mErrors);
			return false;
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		logger.debug("End IndiDueFeePartUI.submitData...");
		return true;
	}

	public VData getLCResult() {
		return m1Result;
	}

	public VData getResult() {
		return m1Result;
	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String arg[]) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86110000";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StartDate", "2005-08-01");
		tTransferData.setNameAndValue("EndDate", "2005-08-29");
		tTransferData.setNameAndValue("ContNo", "230110000003184");
		VData tv = new VData();
		tv.add(tGI);
		tv.add(tTransferData);

		IndiDueFeePartUI IndiDueFeePartUI1 = new IndiDueFeePartUI();
		if (IndiDueFeePartUI1.submitData(tv, "")) {
			logger.debug("个单批处理催收完成");
		} else {
			logger.debug("个单批处理催收信息提示：");
		}
	}
}
