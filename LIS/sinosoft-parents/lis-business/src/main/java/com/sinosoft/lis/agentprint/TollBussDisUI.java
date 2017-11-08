package com.sinosoft.lis.agentprint;
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

public class TollBussDisUI {
private static Logger logger = Logger.getLogger(TollBussDisUI.class);

	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();

	public TollBussDisUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		TollBussDisBL tTollBussDisBL = new TollBussDisBL();
		if (!tTollBussDisBL.submitData(mInputData, "Print")) {
			this.mErrors.copyAllErrors(tTollBussDisBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "TollBussDisUI";
			tError.functionName = "submitData";
			tError.errorMessage = tTollBussDisBL.mErrors.getFirstError();
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult = tTollBussDisBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
