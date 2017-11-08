package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 理赔险种类别给付统计报表打印
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaoy,2005-9-13 9:43
 * @version 1.0
 */

public class LLPRRClaimTypePayUI {
private static Logger logger = Logger.getLogger(LLPRRClaimTypePayUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private TransferData mTransferData = new TransferData();

	public LLPRRClaimTypePayUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		LLPRRClaimTypePayBL tLLPRRShortPayRateBL = new LLPRRClaimTypePayBL();

		if (tLLPRRShortPayRateBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRRShortPayRateBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRRRiskTypePayBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRRShortPayRateBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean getInputData(VData cInputData, String cOperate) {
		return true;
	}

	public static void main(String[] args) {

	}
}
