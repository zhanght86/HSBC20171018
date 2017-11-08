package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证打印：批单[团体]-理赔给付批注 -- PCT002,PdLpjfpzTtC00020.vts
 * </p>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhaocx 20061206
 * @version 1.0
 */

public class LLPRTPostilGrpUI {
private static Logger logger = Logger.getLogger(LLPRTPostilGrpUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */

	public LLPRTPostilGrpUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLPRTPostilGrpBL tLLPRTPostilGrpBL = new LLPRTPostilGrpBL();

		if (tLLPRTPostilGrpBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRTPostilGrpBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRTPostilGrpBL.getResult();
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
		// LLPRTPostilGrpUI tLLPRTPostilGrpUI = new
		// LLPRTPostilGrpUI();
		//
		// tLLPRTPostilGrpUI.submitData(tVData, "");
	}
}
