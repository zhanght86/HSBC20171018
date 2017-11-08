package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * Title: 团险理赔打印
 * 
 * Description: 团险理赔打印
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company:sinosoft
 * 
 * @author mw,2009-04-14
 */

public class LLGrpClaimPrtUI {
private static Logger logger = Logger.getLogger(LLGrpClaimPrtUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	public LLGrpClaimPrtUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLGrpClaimPrtBL tLLGrpClaimPrtBL = new LLGrpClaimPrtBL();

		if (tLLGrpClaimPrtBL.submitData(cInputData, cOperate) == false) {
			this.mErrors.copyAllErrors(tLLGrpClaimPrtBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPrtBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mResult = tLLGrpClaimPrtBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}
