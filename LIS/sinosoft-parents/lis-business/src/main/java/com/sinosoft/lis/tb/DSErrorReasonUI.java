package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class DSErrorReasonUI {
private static Logger logger = Logger.getLogger(DSErrorReasonUI.class);
	
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private String mOperate;
	
	
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		DSErrorReasonBL tDSErrorReasonBL = new DSErrorReasonBL();
		logger.debug("---DSErrorReasonUI---");
		if (tDSErrorReasonBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tDSErrorReasonBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "DSErrorReasonUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据保存失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tDSErrorReasonBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}
