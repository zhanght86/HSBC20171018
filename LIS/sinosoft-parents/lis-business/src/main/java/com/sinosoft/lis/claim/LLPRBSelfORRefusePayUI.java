package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author liuyin
 * @version 1.0
 */
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LLPRBSelfORRefusePayUI {
private static Logger logger = Logger.getLogger(LLPRBSelfORRefusePayUI.class);

	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private String[][] mResult;

	public LLPRBSelfORRefusePayUI() {
	}

	public boolean submitData(VData cInputData) {
		mInputData = (VData) cInputData.clone();
		LLPRBSelfORRefusePayBL tLLPRBSelfORRefusePayBL = new LLPRBSelfORRefusePayBL();
		if (!tLLPRBSelfORRefusePayBL.submitData(mInputData)) {
			this.mErrors.copyAllErrors(tLLPRBSelfORRefusePayBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLPRBSelfORRefusePayUI";
			tError.functionName = "submitData";
			tError.errorMessage = tLLPRBSelfORRefusePayBL.mErrors
					.getFirstError();
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult = tLLPRBSelfORRefusePayBL.getResult();
		return true;
	}

	public String[][] getResult() {
		return mResult;
	}
}
