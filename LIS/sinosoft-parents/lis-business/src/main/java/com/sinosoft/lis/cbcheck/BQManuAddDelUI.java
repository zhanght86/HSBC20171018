package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class BQManuAddDelUI {
private static Logger logger = Logger.getLogger(BQManuAddDelUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public BQManuAddDelUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		BQManuAddDelBL tBQManuAddDelBL = new BQManuAddDelBL();

		logger.debug("---UWManuAddChkUI BEGIN---");
		if (tBQManuAddDelBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tBQManuAddDelBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuAddChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			this.mErrors.copyAllErrors(tBQManuAddDelBL.mErrors);
		}
		return true;
	}
}