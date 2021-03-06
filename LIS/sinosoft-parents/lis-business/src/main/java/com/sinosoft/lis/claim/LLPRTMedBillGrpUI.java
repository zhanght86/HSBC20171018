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
 * Description: 医疗理赔给付清单[团体]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaorx 2006-12-05
 * @version 1.0
 */

public class LLPRTMedBillGrpUI {
private static Logger logger = Logger.getLogger(LLPRTMedBillGrpUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */

	public LLPRTMedBillGrpUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRTMedBillGrpBL tLLPRTMedBillGrpBL = new LLPRTMedBillGrpBL();

		if (tLLPRTMedBillGrpBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRTMedBillGrpBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRTMedBillGrpBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("ClmNo","90000002042");
		// tTransferData.setNameAndValue("CustNo","0000550940");
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		//
		// LLPRTMedBillPerUI tLLPRTMedBillPerUI = new
		// LLPRTMedBillPerUI();
		//
		// tLLPRTMedBillPerUI.submitData(tVData, "");
	}
}
