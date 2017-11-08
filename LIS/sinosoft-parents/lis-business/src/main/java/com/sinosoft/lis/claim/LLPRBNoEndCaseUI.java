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
 * Description: 理赔未结案件清单打印
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhaoy,2005-9-2 17:10
 * @version 1.0
 */

public class LLPRBNoEndCaseUI {
private static Logger logger = Logger.getLogger(LLPRBNoEndCaseUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private TransferData mTransferData = new TransferData();
	String strMngCom = "";
	String strOverTime = "";
	String strWorkPool = "";

	public LLPRBNoEndCaseUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		LLPRBNoEndCaseBL tLLPRBNoEndCaseBL = new LLPRBNoEndCaseBL();

		if (tLLPRBNoEndCaseBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRBNoEndCaseBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBNoEndCaseBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRBNoEndCaseBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.strMngCom = (String) mTransferData.getValueByName("strMngCom");
		this.strOverTime = (String) mTransferData.getValueByName("strOverTime");
		this.strWorkPool = (String) mTransferData.getValueByName("strWorkPool");

		return true;
	}

	public static void main(String[] args) {

	}
}
