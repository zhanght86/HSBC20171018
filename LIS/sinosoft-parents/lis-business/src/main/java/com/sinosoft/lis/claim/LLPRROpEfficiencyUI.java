package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 统计报表打印：操作人员时效统计
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2005-09-26
 * @version 1.0
 */

public class LLPRROpEfficiencyUI {
private static Logger logger = Logger.getLogger(LLPRROpEfficiencyUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */

	public LLPRROpEfficiencyUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRROpEfficiencyBL tLLPRROpEfficiencyBL = new LLPRROpEfficiencyBL();

		if (tLLPRROpEfficiencyBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRROpEfficiencyBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRROpEfficiencyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRROpEfficiencyBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ClmNo","90000000737");
		// tTransferData.setNameAndValue("CustNo","0000535120");
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		//
		// LLPRTPostilContUI tLLPRTPostilContUI = new
		// LLPRTPostilContUI();
		//
		// tLLPRTPostilContUI.submitData(tVData, "");
	}
}
